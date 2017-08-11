package com.chris.pkg.manager.bean.android.resource;

import com.chris.pkg.manager.bean.android.resource.bean.ResChunkTable;
import com.chris.pkg.manager.bean.android.resource.bean.chunk.header.ResChunkHeader;
import com.chris.pkg.manager.bean.android.resource.bean.chunk.header.ResTableHeader;
import com.chris.pkg.manager.bean.android.resource.bean.chunk.pkg.ResTablePackage;
import com.chris.pkg.manager.bean.android.resource.bean.chunk.spec.ResTableTypeSpec;
import com.chris.pkg.manager.bean.android.resource.bean.chunk.string.ResStringPoolHeader;
import com.chris.pkg.manager.bean.android.resource.bean.chunk.string.ResStringPoolRef;
import com.chris.pkg.manager.bean.android.resource.bean.chunk.type.ResTableConfig;
import com.chris.pkg.manager.bean.android.resource.bean.chunk.type.ResTableEntry;
import com.chris.pkg.manager.bean.android.resource.bean.chunk.type.ResTableMap;
import com.chris.pkg.manager.bean.android.resource.bean.chunk.type.ResTableMapEntry;
import com.chris.pkg.manager.bean.android.resource.bean.chunk.type.ResTableRef;
import com.chris.pkg.manager.bean.android.resource.bean.chunk.type.ResTableType;
import com.chris.pkg.manager.bean.android.resource.bean.chunk.type.ResValue;
import com.chris.pkg.manager.bean.io.Reader;

import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.List;

public class ResourceParser {
    private final int CHUNK_TYPE = 513;
    private final int CHUNK_TYPE_SPEC = 514;
    private Reader reader = null;
    private ResChunkTable table = null;
    private ResTablePackage curPkg = null;
    private int specId = 0;

    public ResourceParser(File manifest) {
        try {
            this.reader = new Reader(new FileInputStream(manifest), false);
            parseResTable();
        } catch (Exception e) {
            e.printStackTrace();
        }
        this.reader = null;
    }

    public String valueByResId(String resId) {
        for (int i = 0; i < this.table.getPkg().length; i++) {
            this.curPkg = this.table.getPkg()[i];
            List<Object> list = this.curPkg.getResTypes();
            if ((list != null) && (list.size() > 0)) {
                for (int j = 0; j < list.size(); j++) {
                    Object object = list.get(j);
                    if ((object instanceof ResTableType)) {
                        ResTableType type = (ResTableType) object;
                        int listIndex = 0;
                        for (int k = 0; k < type.getEntryCount(); k++) {
                            if (type.getEntries()[k] != -1) {
                                String rid = String.format("@%08X", getResId(k));
                                if (rid.equals(resId)) {
                                    ResValue value = (ResValue) type.getTypeEntries().get(listIndex);
                                    return value.getTypeData(this.table.getStringPool().getStringPool());
                                }
                                listIndex++;
                            }
                        }
                    } else {
                        this.specId = ((ResTableTypeSpec) object).getId();
                    }
                }
            }
        }
        return "";
    }

    private void parseResTable() {
        this.table = new ResChunkTable();
        parseResTableHeader();
        parseResPkg();
    }

    private ResChunkHeader getResChunkHeader() {
        ResChunkHeader header = new ResChunkHeader();
        header.setType((short) this.reader.readShort());
        header.setHeaderSize((short) this.reader.readShort());
        header.setSize(this.reader.readInt());
        return header;
    }

    private int[] getOffsets(int[] offsets, int end) {
        int[] offset = new int[offsets.length + 1];
        System.arraycopy(offsets, 0, offset, 0, offsets.length);
        offset[offsets.length] = end;
        return offset;
    }

    private void parseResTableHeader() {
        ResTableHeader header = new ResTableHeader();
        header.setHeader(getResChunkHeader());
        header.setPackageCount(this.reader.readInt());
        this.table.setHeader(header);
        this.table.setStringPool(parseResStringPool());
    }

    private ResStringPoolHeader parseResStringPool() {
        ResStringPoolHeader pool = new ResStringPoolHeader();
        pool.setHeader(getResChunkHeader());
        pool.setStringCount(this.reader.readInt());
        pool.setStyleCount(this.reader.readInt());
        pool.setFlags(this.reader.readInt());
        pool.setStringsStart(this.reader.readInt());
        pool.setStylesStart(this.reader.readInt());
        pool.setStringOffsets(this.reader.readIntArray(pool.getStringCount()));
        pool.setStyleOffsets(this.reader.readIntArray(pool.getStyleCount()));

        int size = (pool.getStyleCount() == 0 ? pool.getHeader().getSize() : pool.getStylesStart()) - pool.getStringsStart();
        pool.setStringPool(this.reader.readUtf8StringArray(getOffsets(pool.getStringOffsets(), size)));
        if (pool.getStyleCount() > 0) {
            size = pool.getHeader().getSize() - pool.getStylesStart();
            pool.setStylePool(this.reader.readUtf8StringArray(getOffsets(pool.getStyleOffsets(), size)));
        }
        return pool;
    }

    private void parseResPkg() {
        ResTablePackage[] pkgs = new ResTablePackage[this.table.getHeader().getPackageCount()];
        for (int i = 0; i < pkgs.length; i++) {
            pkgs[i] = new ResTablePackage();
            pkgs[i].setHeader(getResChunkHeader());
            pkgs[i].setId(this.reader.readInt());
            pkgs[i].setName(this.reader.readCharArray(pkgs[i].getName().length));
            pkgs[i].setTypeStrings(this.reader.readInt());
            pkgs[i].setLastPublicType(this.reader.readInt());
            pkgs[i].setKeyStrings(this.reader.readInt());
            pkgs[i].setLastPublicKey(this.reader.readInt());
            pkgs[i].setSkip(this.reader.readInt());
            pkgs[i].setTypeStringPool(parseResStringPool());
            pkgs[i].setKeyStringPool(parseResStringPool());

            this.curPkg = pkgs[i];
            pkgs[i].setResTypes(parseResType());
        }
        this.table.setPkg(pkgs);
    }

    private int getResId(int entryId) {
        return this.curPkg.getId() << 24 | (this.specId & 0xFF) << 16 | entryId & 0xFFFF;
    }

    private String getPkgTypeString(int index) {
        return this.curPkg.getTypeStringPool().getStringPool()[index];
    }

    private String getPkgKeyString(int index) {
        return this.curPkg.getKeyStringPool().getStringPool()[index];
    }

    private List<Object> parseResType() {
        List<Object> resTypeList = new ArrayList<>();
        for (; ; ) {
            ResChunkHeader header = getResChunkHeader();
            if (header.getSize() == -1) {
                break;
            }
            if (header.getType() == CHUNK_TYPE_SPEC) {
                resTypeList.add(parseResTypeSpec(header));
            } else if (header.getType() == CHUNK_TYPE) {
                resTypeList.add(parseResTypeInfo(header));
            }
        }
        return resTypeList;
    }

    private ResTableTypeSpec parseResTypeSpec(ResChunkHeader header) {
        ResTableTypeSpec spec = new ResTableTypeSpec();
        spec.setHeader(header);
        spec.setId((byte) this.reader.readByte());
        this.specId = spec.getId();

        spec.setRes0((byte) this.reader.readByte());
        spec.setRes1((short) this.reader.readShort());
        spec.setEntryCount(this.reader.readInt());
        spec.setEntries(this.reader.readIntArray(spec.getEntryCount()));
        return spec;
    }

    private ResTableType parseResTypeInfo(ResChunkHeader header) {
        ResTableType type = new ResTableType();
        type.setHeader(header);
        type.setId((byte) this.reader.readByte());

        type.setRes0((byte) this.reader.readByte());
        type.setRes1((short) this.reader.readShort());
        type.setEntryCount(this.reader.readInt());
        type.setEntriesStart(this.reader.readInt());
        type.setConfig(parseResTableConfig());
        type.setEntries(this.reader.readIntArray(type.getEntryCount()));

        List<Object> types = new ArrayList<>();
        for (int i = 0; i < type.getEntryCount(); i++) {
            if (type.getEntries()[i] != -1) {
                ResTableEntry entry = parseResTableEntry();
                if (entry.getFlags() == 1) {
                    ResTableMapEntry mapEntry = parseResTableMapEntry(entry);
                    List<ResTableMap> list = new ArrayList<>(mapEntry.getCount());
                    for (int j = 0; j < mapEntry.getCount(); j++) {
                        list.add(parseResTableMap());
                    }
                    types.add(list);
                } else {
                    types.add(parseResValue());
                }
            }
        }
        type.setTypeEntries(types);
        return type;
    }

    private ResTableConfig parseResTableConfig() {
        ResTableConfig config = new ResTableConfig();
        byte[] byteConfig = this.reader.readByteArray(48);

        config.setSize(this.reader.bytesToInt(byteConfig, 0, 4));

        config.setMcc((short) this.reader.bytesToInt(byteConfig, 4, 2));
        config.setMcc((short) this.reader.bytesToInt(byteConfig, 6, 2));
        config.setImsi(this.reader.bytesToInt(byteConfig, 4, 4));

        config.setLanguage(this.reader.getBytes(byteConfig, 8, 2));
        config.setCountry(this.reader.getBytes(byteConfig, 10, 2));
        config.setLocale(this.reader.bytesToInt(byteConfig, 8, 4));

        config.setOrientation((byte) this.reader.bytesToInt(byteConfig, 12, 1));
        config.setTouchscreen((byte) this.reader.bytesToInt(byteConfig, 13, 1));
        config.setDensity((short) this.reader.bytesToInt(byteConfig, 14, 2));
        config.setScreenType(this.reader.bytesToInt(byteConfig, 12, 4));

        config.setKeyboard((byte) this.reader.bytesToInt(byteConfig, 16, 1));
        config.setNavigation((byte) this.reader.bytesToInt(byteConfig, 17, 1));
        config.setInputFlags((byte) this.reader.bytesToInt(byteConfig, 18, 1));
        config.setInputPad0((byte) this.reader.bytesToInt(byteConfig, 19, 1));
        config.setInput(this.reader.bytesToInt(byteConfig, 16, 4));

        config.setScreenWidth((short) this.reader.bytesToInt(byteConfig, 20, 2));
        config.setScreenHeight((short) this.reader.bytesToInt(byteConfig, 22, 2));
        config.setScreenSize(this.reader.bytesToInt(byteConfig, 20, 4));

        config.setSdkVersion((short) this.reader.bytesToInt(byteConfig, 24, 2));
        config.setMinorVersion((short) this.reader.bytesToInt(byteConfig, 26, 2));
        config.setVersion(this.reader.bytesToInt(byteConfig, 24, 4));

        config.setScreenLayout((byte) this.reader.bytesToInt(byteConfig, 28, 1));
        config.setUiMode((byte) this.reader.bytesToInt(byteConfig, 29, 1));
        config.setSmallestScreenWidthDp((short) this.reader.bytesToInt(byteConfig, 30, 2));
        config.setScreenConfig(this.reader.bytesToInt(byteConfig, 28, 4));

        config.setScreenWidthDp((short) this.reader.bytesToInt(byteConfig, 32, 2));
        config.setScreenHeightDp((short) this.reader.bytesToInt(byteConfig, 34, 2));
        config.setScreenSizeDp(this.reader.bytesToInt(byteConfig, 32, 4));

        config.setLocaleScript(this.reader.getBytes(byteConfig, 36, 4));

        config.setLocaleVariant(this.reader.getBytes(byteConfig, 40, 8));

        return config;
    }

    private ResTableEntry parseResTableEntry() {
        ResTableEntry entry = new ResTableEntry();
        entry.setSize((short) this.reader.readShort());
        entry.setFlags((short) this.reader.readShort());
        entry.setRef(parseResStringPoolRef());
        return entry;
    }

    private ResStringPoolRef parseResStringPoolRef() {
        ResStringPoolRef ref = new ResStringPoolRef();
        ref.setIndex(this.reader.readInt());
        return ref;
    }

    private ResTableMapEntry parseResTableMapEntry(ResTableEntry resTableEntry) {
        ResTableMapEntry entry = new ResTableMapEntry();
        entry.setSize(resTableEntry.getSize());
        entry.setFlags(resTableEntry.getFlags());
        entry.setRef(resTableEntry.getRef());

        ResTableRef parent = new ResTableRef();
        parent.setIdent(this.reader.readInt());
        entry.setParent(parent);
        entry.setCount(this.reader.readInt());

        return entry;
    }

    private ResTableMap parseResTableMap() {
        ResTableMap map = new ResTableMap();
        map.setName(parseResTableRef());
        map.setValue(parseResValue());
        return map;
    }

    private ResTableRef parseResTableRef() {
        ResTableRef ref = new ResTableRef();
        ref.setIdent(this.reader.readInt());
        return ref;
    }

    private ResValue parseResValue() {
        ResValue value = new ResValue();
        value.setSize((short) this.reader.readShort());
        value.setRes0((byte) this.reader.readByte());
        value.setDataType((byte) this.reader.readByte());
        value.setData(this.reader.readInt());

        return value;
    }
}
