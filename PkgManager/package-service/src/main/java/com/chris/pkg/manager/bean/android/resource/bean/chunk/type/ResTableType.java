package com.chris.pkg.manager.bean.android.resource.bean.chunk.type;

import com.chris.pkg.manager.bean.android.resource.bean.chunk.header.ResChunkHeader;

import java.io.Serializable;
import java.util.List;

public class ResTableType implements Serializable {
    private static final int NO_ENTRY = -1;
    private ResChunkHeader header = null;
    private byte id = 0;
    private byte res0 = 0;
    private short res1 = 0;
    private int entryCount = 0;
    private int entriesStart = 0;
    private ResTableConfig config = null;
    private int[] entries = null;
    private List<Object> typeEntries = null;

    public ResChunkHeader getHeader() {
        return this.header;
    }

    public void setHeader(ResChunkHeader header) {
        this.header = header;
    }

    public byte getId() {
        return this.id;
    }

    public void setId(byte id) {
        this.id = id;
    }

    public byte getRes0() {
        return this.res0;
    }

    public void setRes0(byte res0) {
        this.res0 = res0;
    }

    public short getRes1() {
        return this.res1;
    }

    public void setRes1(short res1) {
        this.res1 = res1;
    }

    public int getEntryCount() {
        return this.entryCount;
    }

    public void setEntryCount(int entryCount) {
        this.entryCount = entryCount;
    }

    public int getEntriesStart() {
        return this.entriesStart;
    }

    public void setEntriesStart(int entriesStart) {
        this.entriesStart = entriesStart;
    }

    public ResTableConfig getConfig() {
        return this.config;
    }

    public void setConfig(ResTableConfig config) {
        this.config = config;
    }

    public int[] getEntries() {
        return this.entries;
    }

    public void setEntries(int[] entries) {
        this.entries = entries;
    }

    public List<Object> getTypeEntries() {
        return this.typeEntries;
    }

    public void setTypeEntries(List<Object> typeEntries) {
        this.typeEntries = typeEntries;
    }
}
