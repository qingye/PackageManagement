package com.chris.pkg.manager.bean.android.resource.bean.chunk.spec;

import com.chris.pkg.manager.bean.android.resource.bean.chunk.header.ResChunkHeader;

import java.io.Serializable;

public class ResTableTypeSpec implements Serializable {
    public static final int SPEC_PUBLIC = 0x40000000;
    private ResChunkHeader header = null;
    private byte id = 0;
    private byte res0 = 0;
    private short res1 = 0;
    private int entryCount = 0;
    private int[] entries = null;

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

    public int[] getEntries() {
        return this.entries;
    }

    public void setEntries(int[] entries) {
        this.entries = entries;
    }
}
