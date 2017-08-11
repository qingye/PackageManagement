package com.chris.pkg.manager.bean.android.manifest.bean.chunk.xmlcontent;

import com.chris.pkg.manager.bean.android.manifest.bean.chunk.base.BaseChunk;

public class XmlContentChunk extends BaseChunk {
    private int lineNumber = 0;
    private int unknown = 0;

    public int getLineNumber() {
        return this.lineNumber;
    }

    public void setLineNumber(int lineNumber) {
        this.lineNumber = lineNumber;
    }

    public int getUnknown() {
        return this.unknown;
    }

    public void setUnknown(int unknown) {
        this.unknown = unknown;
    }
}
