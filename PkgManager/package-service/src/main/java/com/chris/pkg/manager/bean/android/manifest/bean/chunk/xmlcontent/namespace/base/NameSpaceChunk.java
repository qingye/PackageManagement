package com.chris.pkg.manager.bean.android.manifest.bean.chunk.xmlcontent.namespace.base;

import com.chris.pkg.manager.bean.android.manifest.bean.chunk.xmlcontent.XmlContentChunk;

public class NameSpaceChunk extends XmlContentChunk {
    private int prefix = 0;
    private int uri = 0;

    public int getPrefix() {
        return this.prefix;
    }

    public void setPrefix(int prefix) {
        this.prefix = prefix;
    }

    public int getUri() {
        return this.uri;
    }

    public void setUri(int uri) {
        this.uri = uri;
    }
}
