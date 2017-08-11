package com.chris.pkg.manager.bean.android.resource.bean.chunk.header;

import java.io.Serializable;

public class ResChunkHeader implements Serializable {
    private short type = 0;
    private short headerSize = 0;
    private int size = 0;

    public short getType() {
        return this.type;
    }

    public void setType(short type) {
        this.type = type;
    }

    public short getHeaderSize() {
        return this.headerSize;
    }

    public void setHeaderSize(short headerSize) {
        this.headerSize = headerSize;
    }

    public int getSize() {
        return this.size;
    }

    public void setSize(int size) {
        this.size = size;
    }
}
