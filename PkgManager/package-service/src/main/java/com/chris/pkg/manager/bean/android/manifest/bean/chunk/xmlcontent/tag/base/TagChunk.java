package com.chris.pkg.manager.bean.android.manifest.bean.chunk.xmlcontent.tag.base;

import com.chris.pkg.manager.bean.android.manifest.bean.chunk.xmlcontent.XmlContentChunk;

public class TagChunk extends XmlContentChunk {
    private int namespaceUri = 0;
    private int name = 0;

    public int getNamespaceUri() {
        return this.namespaceUri;
    }

    public void setNamespaceUri(int namespaceUri) {
        this.namespaceUri = namespaceUri;
    }

    public int getName() {
        return this.name;
    }

    public void setName(int name) {
        this.name = name;
    }
}
