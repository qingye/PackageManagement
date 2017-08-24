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
    private StringBuilder sb = null;

    public ResourceParser(File manifest) {
        try {
            reader = new Reader(new FileInputStream(manifest), false);
            sb = new StringBuilder();
            parseResTable();
        } catch (Exception e) {
            e.printStackTrace();
        }
//        FileUtil.write("a.txt", sb.toString().getBytes());
        reader = null;
        sb = null;
    }

    public String valueByResId(String resId) {
        for (int i = 0; i < table.getPkg().length; i++) {
            curPkg = table.getPkg()[i];
            List<Object> list = curPkg.getResTypes();
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
                                    return value.getTypeData(table.getStringPool().getStringPool());
                                }
                                listIndex++;
                            }
                        }
                    } else {
                        specId = ((ResTableTypeSpec) object).getId();
                        System.out.println("pkg.id = " + curPkg.getId() + ", specId = " + specId);
                    }
                }
            }
        }
        return "";
    }

    private void parseResTable() {
        table = new ResChunkTable();
        parseResTableHeader();
        parseResPkg();
    }

    private ResChunkHeader getResChunkHeader() {
        ResChunkHeader header = new ResChunkHeader();
        header.setType((short) reader.readShort());
        header.setHeaderSize((short) reader.readShort());
        header.setSize(reader.readInt());
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
        header.setPackageCount(reader.readInt());
        table.setHeader(header);
        table.setStringPool(parseResStringPool());
    }

    private ResStringPoolHeader parseResStringPool() {
        ResStringPoolHeader pool = new ResStringPoolHeader();
        pool.setHeader(getResChunkHeader());
        pool.setStringCount(reader.readInt());
        pool.setStyleCount(reader.readInt());
        pool.setFlags(reader.readInt());
        pool.setStringsStart(reader.readInt());
        pool.setStylesStart(reader.readInt());
        pool.setStringOffsets(reader.readIntArray(pool.getStringCount()));
        pool.setStyleOffsets(reader.readIntArray(pool.getStyleCount()));

        int size = (pool.getStyleCount() == 0 ? pool.getHeader().getSize() : pool.getStylesStart()) - pool.getStringsStart();
        pool.setStringPool(reader.readUtf8StringArray(getOffsets(pool.getStringOffsets(), size)));
        if (pool.getStyleCount() > 0) {
            size = pool.getHeader().getSize() - pool.getStylesStart();
            pool.setStylePool(reader.readUtf8StringArray(getOffsets(pool.getStyleOffsets(), size)));
        }
        return pool;
    }

    private void parseResPkg() {
        ResTablePackage[] pkgs = new ResTablePackage[table.getHeader().getPackageCount()];
        for (int i = 0; i < pkgs.length; i++) {
            pkgs[i] = new ResTablePackage();
            pkgs[i].setHeader(getResChunkHeader());
            pkgs[i].setId(reader.readInt());
            pkgs[i].setName(reader.readCharArray(pkgs[i].getName().length));
            pkgs[i].setTypeStrings(reader.readInt());
            pkgs[i].setLastPublicType(reader.readInt());
            pkgs[i].setKeyStrings(reader.readInt());
            pkgs[i].setLastPublicKey(reader.readInt());
            pkgs[i].setSkip(reader.readInt());
            pkgs[i].setTypeStringPool(parseResStringPool());
            pkgs[i].setKeyStringPool(parseResStringPool());

            curPkg = pkgs[i];
            pkgs[i].setResTypes(parseResType());
        }
        table.setPkg(pkgs);
    }

    private int getResId(int entryId) {
        return curPkg.getId() << 24 | (specId & 0xFF) << 16 | entryId & 0xFFFF;
    }

    private String getPkgTypeString(int index) {
        return curPkg.getTypeStringPool().getStringPool()[index];
    }

    private String getPkgKeyString(int index) {
        return curPkg.getKeyStringPool().getStringPool()[index];
    }

    private List<Object> parseResType() {
        List<Object> resTypeList = new ArrayList<>();
        while (true) {
            ResChunkHeader header = getResChunkHeader();
            if (header.getSize() == -1) {
                break;
            }
            sb.append("[parseResType] ========> resType = ").append(header.getType()).append("\n");

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
        spec.setId((byte) reader.readByte());
        this.specId = spec.getId();
        sb.append("[parseResTypeSpec] specId = ").append(specId).append("\n");

        spec.setRes0((byte) reader.readByte());
        spec.setRes1((short) reader.readShort());
        spec.setEntryCount(reader.readInt());
        spec.setEntries(reader.readIntArray(spec.getEntryCount()));
        return spec;
    }

    private ResTableType parseResTypeInfo(ResChunkHeader header) {
        reader.mark();
        sb.append("--> parseResTypeInfo.begin.size = ");

        ResTableType type = new ResTableType();
        type.setHeader(header);
        type.setId((byte) reader.readByte());
        sb.append(type.getHeader().getSize()).append("\n");

        type.setRes0((byte) reader.readByte());
        type.setRes1((short) reader.readShort());
        type.setEntryCount(reader.readInt());
        type.setEntriesStart(reader.readInt());
        type.setConfig(parseResTableConfig());
        type.setEntries(reader.readIntArray(type.getEntryCount()));

        List<Object> types = new ArrayList<>();
        for (int i = 0; i < type.getEntryCount(); i++) {
            if (type.getEntries()[i] != -1) {

                sb.append("[").append(i).append("]");
                ResTableEntry entry = parseResTableEntry();

                if (entry.getFlags() == 1) {
                    sb.append("---------------------- ResTableMapEntry").append("\n");
                } else {
                    sb.append("---------------------- ResValue").append("\n");
                }

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
            sb.append("}").append("\n");
        }
        type.setTypeEntries(types);

        sb.append("--> end.consume = ").append(reader.totalConsume()).append("\n");
        return type;
    }

    private ResTableConfig parseResTableConfig() {
        ResTableConfig config = new ResTableConfig();
        byte[] byteConfig = reader.readByteArray(48);

        config.setSize(reader.bytesToInt(byteConfig, 0, 4));

        config.setMcc((short) reader.bytesToInt(byteConfig, 4, 2));
        config.setMcc((short) reader.bytesToInt(byteConfig, 6, 2));
        config.setImsi(reader.bytesToInt(byteConfig, 4, 4));

        config.setLanguage(reader.getBytes(byteConfig, 8, 2));
        config.setCountry(reader.getBytes(byteConfig, 10, 2));
        config.setLocale(reader.bytesToInt(byteConfig, 8, 4));

        config.setOrientation((byte) reader.bytesToInt(byteConfig, 12, 1));
        config.setTouchscreen((byte) reader.bytesToInt(byteConfig, 13, 1));
        config.setDensity((short) reader.bytesToInt(byteConfig, 14, 2));
        config.setScreenType(reader.bytesToInt(byteConfig, 12, 4));

        config.setKeyboard((byte) reader.bytesToInt(byteConfig, 16, 1));
        config.setNavigation((byte) reader.bytesToInt(byteConfig, 17, 1));
        config.setInputFlags((byte) reader.bytesToInt(byteConfig, 18, 1));
        config.setInputPad0((byte) reader.bytesToInt(byteConfig, 19, 1));
        config.setInput(reader.bytesToInt(byteConfig, 16, 4));

        config.setScreenWidth((short) reader.bytesToInt(byteConfig, 20, 2));
        config.setScreenHeight((short) reader.bytesToInt(byteConfig, 22, 2));
        config.setScreenSize(reader.bytesToInt(byteConfig, 20, 4));

        config.setSdkVersion((short) reader.bytesToInt(byteConfig, 24, 2));
        config.setMinorVersion((short) reader.bytesToInt(byteConfig, 26, 2));
        config.setVersion(reader.bytesToInt(byteConfig, 24, 4));

        config.setScreenLayout((byte) reader.bytesToInt(byteConfig, 28, 1));
        config.setUiMode((byte) reader.bytesToInt(byteConfig, 29, 1));
        config.setSmallestScreenWidthDp((short) reader.bytesToInt(byteConfig, 30, 2));
        config.setScreenConfig(reader.bytesToInt(byteConfig, 28, 4));

        config.setScreenWidthDp((short) reader.bytesToInt(byteConfig, 32, 2));
        config.setScreenHeightDp((short) reader.bytesToInt(byteConfig, 34, 2));
        config.setScreenSizeDp(reader.bytesToInt(byteConfig, 32, 4));

        config.setLocaleScript(reader.getBytes(byteConfig, 36, 4));

        config.setLocaleVariant(reader.getBytes(byteConfig, 40, 8));

        // 这里，真实的大小可能会大于48个字节，因此需要消耗掉
        reader.readByteArray(config.getSize() - 48);
        return config;
    }

    private ResTableEntry parseResTableEntry() {
        sb.append("======== [parseResTableEntry] ========\n{").append("\n");
        ResTableEntry entry = new ResTableEntry();
        entry.setSize((short) reader.readShort());
        entry.setFlags((short) reader.readShort());
        sb.append(short2hex(entry.getSize())).append(" ").append(short2hex(entry.getFlags())).append(" ");

        entry.setRef(parseResStringPoolRef());
        return entry;
    }

    private ResStringPoolRef parseResStringPoolRef() {
        ResStringPoolRef ref = new ResStringPoolRef();
        ref.setIndex(reader.readInt());
        sb.append(int2hex(ref.getIndex())).append(" [index = ").append(ref.getIndex()).append("]").append("\n");
        return ref;
    }

    private ResTableMapEntry parseResTableMapEntry(ResTableEntry resTableEntry) {
        ResTableMapEntry entry = new ResTableMapEntry();
        entry.setSize(resTableEntry.getSize());
        entry.setFlags(resTableEntry.getFlags());
        entry.setRef(resTableEntry.getRef());

        ResTableRef parent = new ResTableRef();
        parent.setIdent(reader.readInt());
        entry.setParent(parent);
        entry.setCount(reader.readInt());

        sb.append(int2hex(parent.getIdent())).append(" ").append(int2hex(entry.getCount())).append(" [count = ").append(entry.getCount()).append("]").append("\n");
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
        ref.setIdent(reader.readInt());

        sb.append(int2hex(ref.getIdent())).append(" ");
        return ref;
    }

    private ResValue parseResValue() {
        ResValue value = new ResValue();
        value.setSize((short) reader.readShort());
        value.setRes0((byte) reader.readByte());
        value.setDataType((byte) reader.readByte());
        value.setData(reader.readInt());

        sb.append(short2hex(value.getSize())).append(" ").append(byte2hex(value.getRes0())).append(byte2hex(value.getDataType())).append(" ").append(int2hex(value.getData())).append("\n");
        return value;
    }

    private String byte2hex(byte b) {
        return String.format("%02X", b);
    }

    private String short2hex(short s) {
        return byte2hex((byte) s) + byte2hex((byte) (s >> 8));
    }

    private String int2hex(int i) {
        return short2hex((short) i) + " " + short2hex((short) (i >> 16));
    }
}
