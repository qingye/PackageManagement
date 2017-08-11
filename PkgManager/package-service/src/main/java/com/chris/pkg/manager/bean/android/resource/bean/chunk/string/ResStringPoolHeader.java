package com.chris.pkg.manager.bean.android.resource.bean.chunk.string;

import com.chris.pkg.manager.bean.android.resource.bean.chunk.header.ResChunkHeader;

import java.io.Serializable;

public class ResStringPoolHeader implements Serializable {
    public static final int SORTED_FLAG = 1;
    public static final int UTF8_FLAG = 256;
    private ResChunkHeader header = null;
    private int stringCount = 0;
    private int styleCount = 0;
    private int flags = 0;
    private int stringsStart = 0;
    private int stylesStart = 0;
    private int[] stringOffsets = null;
    private int[] styleOffsets = null;
    private String[] stringPool = null;
    private String[] stylePool = null;

    public ResChunkHeader getHeader() {
        return this.header;
    }

    public void setHeader(ResChunkHeader header) {
        this.header = header;
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

    public int getFlags() {
        return this.flags;
    }

    public void setFlags(int flags) {
        this.flags = flags;
    }

    public int getStringsStart() {
        return this.stringsStart;
    }

    public void setStringsStart(int stringsStart) {
        this.stringsStart = stringsStart;
    }

    public int getStylesStart() {
        return this.stylesStart;
    }

    public void setStylesStart(int stylesStart) {
        this.stylesStart = stylesStart;
    }

    public int[] getStringOffsets() {
        return this.stringOffsets;
    }

    public void setStringOffsets(int[] stringOffsets) {
        this.stringOffsets = stringOffsets;
    }

    public int[] getStyleOffsets() {
        return this.styleOffsets;
    }

    public void setStyleOffsets(int[] styleOffsets) {
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
