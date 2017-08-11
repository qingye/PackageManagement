package com.chris.pkg.manager.bean.android.manifest.bean.chunk.string;

import java.io.Serializable;

public class StringChunk implements Serializable {
    private int chunkType = 0;
    private int chunkSize = 0;
    private int stringCount = 0;
    private int styleCount = 0;
    private int unknown = 0;
    private int stringPoolOffset = 0;
    private int stylePoolOffset = 0;
    private byte[] stringOffsets = null;
    private byte[] styleOffsets = null;
    private String[] stringPool = null;
    private String[] stylePool = null;

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

    public int getStringCount() {
        return this.stringCount;
    }

    public void setStringCount(int stringCount) {
        this.stringCount = stringCount;
    }

    public int getStyleCount() {
        return this.styleCount;
    }

    public void setStyleCount(int styleCount) {
        this.styleCount = styleCount;
    }

    public int getUnknown() {
        return this.unknown;
    }

    public void setUnknown(int unknown) {
        this.unknown = unknown;
    }

    public int getStringPoolOffset() {
        return this.stringPoolOffset;
    }

    public void setStringPoolOffset(int stringPoolOffset) {
        this.stringPoolOffset = stringPoolOffset;
    }

    public int getStylePoolOffset() {
        return this.stylePoolOffset;
    }

    public void setStylePoolOffset(int stylePoolOffset) {
        this.stylePoolOffset = stylePoolOffset;
    }

    public byte[] getStringOffsets() {
        return this.stringOffsets;
    }

    public void setStringOffsets(byte[] stringOffsets) {
        this.stringOffsets = stringOffsets;
    }

    public byte[] getStyleOffsets() {
        return this.styleOffsets;
    }

    public void setStyleOffsets(byte[] styleOffsets) {
        this.styleOffsets = styleOffsets;
    }

    public String[] getStringPool() {
        return this.stringPool;
    }

    public void setStringPool(String[] stringPool) {
        this.stringPool = stringPool;
    }

    public String[] getStylePool() {
        return this.stylePool;
    }

    public void setStylePool(String[] stylePool) {
        this.stylePool = stylePool;
    }
}
