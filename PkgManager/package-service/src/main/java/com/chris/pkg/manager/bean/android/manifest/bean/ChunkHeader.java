package com.chris.pkg.manager.bean.android.manifest.bean;

import com.chris.pkg.manager.bean.android.manifest.bean.chunk.resourceid.ResourceIdChunk;
import com.chris.pkg.manager.bean.android.manifest.bean.chunk.string.StringChunk;
import com.chris.pkg.manager.bean.android.manifest.bean.chunk.xmlcontent.XmlContentChunk;

import java.io.Serializable;
import java.util.List;

public class ChunkHeader implements Serializable {
    private int magicNumber = 0;
    private int fileSize = 0;
    private StringChunk stringChunk = null;
    private ResourceIdChunk resourceIdChunk = null;
    private List<XmlContentChunk> xmlContentChunks = null;

    public int getMagicNumber() {
        return this.magicNumber;
    }

    public void setMagicNumber(int magicNumber) {
        this.magicNumber = magicNumber;
    }

    public int getFileSize() {
        return this.fileSize;
    }

    public void setFileSize(int fileSize) {
        this.fileSize = fileSize;
    }

    public StringChunk getStringChunk() {
        return this.stringChunk;
    }

    public void setStringChunk(StringChunk stringChunk) {
        this.stringChunk = stringChunk;
    }

    public ResourceIdChunk getResourceIdChunk() {
        return this.resourceIdChunk;
    }

    public void setResourceIdChunk(ResourceIdChunk resourceIdChunk) {
        this.resourceIdChunk = resourceIdChunk;
    }

    public List<XmlContentChunk> getXmlContentChunks() {
        return this.xmlContentChunks;
    }

    public void setXmlContentChunks(List<XmlContentChunk> xmlContentChunks) {
        this.xmlContentChunks = xmlContentChunks;
    }
}
