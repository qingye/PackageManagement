package com.chris.pkg.manager.bean.android.manifest.bean.chunk.xmlcontent.text;

import com.chris.pkg.manager.bean.android.manifest.bean.chunk.xmlcontent.XmlContentChunk;

public class TextChunk extends XmlContentChunk {
    private int name = 0;
    private int[] unknowns = null;

    public int getName() {
        return this.name;
    }

    public void setName(int name) {
        this.name = name;
    }

    public int[] getUnknowns() {
        return this.unknowns;
    }

    public void setUnknowns(int[] unknowns) {
        this.unknowns = unknowns;
    }
}
