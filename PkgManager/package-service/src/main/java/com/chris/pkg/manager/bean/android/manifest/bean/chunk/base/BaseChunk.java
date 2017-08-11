package com.chris.pkg.manager.bean.android.manifest.bean.chunk.base;

import java.io.Serializable;

public class BaseChunk implements Serializable {
    private int chunkType = 0;
    private int chunkSize = 0;

    public int getChunkType() {
        return this.chunkType;
    }

    public void setChunkType(int chunkType) {
        this.chunkType = chunkType;
    }

    public int getChunkSize() {
        return this.chunkSize;
    }

    public void setChunkSize(int chunkSize) {
        this.chunkSize = chunkSize;
    }
}
