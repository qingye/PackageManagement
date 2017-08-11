package com.chris.pkg.manager.bean.android.manifest.bean.chunk.xmlcontent.tag;

import com.chris.pkg.manager.bean.android.manifest.bean.chunk.xmlcontent.tag.base.TagChunk;

public class StartTagChunk extends TagChunk {
    private int flags = 0;
    private int attributeCount = 0;
    private int classAttribute = 0;
    private int[][] attributes = (int[][]) null;

    public int getFlags() {
        return this.flags;
    }

    public void setFlags(int flags) {
        this.flags = flags;
    }

    public int getAttributeCount() {
        return this.attributeCount;
    }

    public void setAttributeCount(int attributeCount) {
        this.attributeCount = attributeCount;
    }

    public int getClassAttribute() {
        return this.classAttribute;
    }

    public void setClassAttribute(int classAttribute) {
        this.classAttribute = classAttribute;
    }

    public int[][] getAttributes() {
        return this.attributes;
    }

    public void setAttributes(int[][] attributes) {
        this.attributes = attributes;
    }
}
