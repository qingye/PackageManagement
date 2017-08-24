package com.chris.pkg.manager.bean.android.manifest;

import com.chris.pkg.manager.bean.android.manifest.bean.ChunkHeader;
import com.chris.pkg.manager.bean.android.manifest.bean.chunk.resourceid.ResourceIdChunk;
import com.chris.pkg.manager.bean.android.manifest.bean.chunk.string.StringChunk;
import com.chris.pkg.manager.bean.android.manifest.bean.chunk.xmlcontent.XmlContentChunk;
import com.chris.pkg.manager.bean.android.manifest.bean.chunk.xmlcontent.namespace.base.NameSpaceChunk;
import com.chris.pkg.manager.bean.android.manifest.bean.chunk.xmlcontent.tag.EndTagChunk;
import com.chris.pkg.manager.bean.android.manifest.bean.chunk.xmlcontent.tag.StartTagChunk;
import com.chris.pkg.manager.bean.android.manifest.bean.chunk.xmlcontent.tag.base.TagChunk;
import com.chris.pkg.manager.bean.android.manifest.bean.chunk.xmlcontent.text.TextChunk;
import com.chris.pkg.manager.bean.android.manifest.gen.XmlGenerator;
import com.chris.pkg.manager.bean.io.Reader;

import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class XmlManifestParser {
    private ChunkHeader header = null;
    private Reader reader = null;
    private Map<String, String> info = null;

    public XmlManifestParser(File manifest) {
        try {
            this.reader = new Reader(new FileInputStream(manifest), false);
            parseXml();
            this.info = new XmlGenerator(this.header).getInfo();
        } catch (Exception e) {
            e.printStackTrace();
        }
        this.reader = null;
    }

    public Map<String, String> getInfo() {
        return this.info;
    }

    private void parseXml() {
        this.header = new ChunkHeader();
        this.header.setMagicNumber(this.reader.readInt());
        this.header.setFileSize(this.reader.readInt());
        this.header.setStringChunk(parseXmlStringChunk());
        this.header.setResourceIdChunk(parseXmlResourceIdChunk());
        this.header.setXmlContentChunks(parseXmlContentChunk());
    }

    private StringChunk parseXmlStringChunk() {
        this.reader.mark();
        StringChunk chunk = new StringChunk();
        chunk.setChunkType(this.reader.readInt());
        chunk.setChunkSize(this.reader.readInt());
        chunk.setStringCount(this.reader.readInt());
        chunk.setStyleCount(this.reader.readInt());
        chunk.setUnknown(this.reader.readInt());
        chunk.setStringPoolOffset(this.reader.readInt());
        chunk.setStylePoolOffset(this.reader.readInt());

        chunk.setStringOffsets(this.reader.readByteArray(chunk.getStringCount() * 4));
        if (chunk.getStyleCount() > 0) {
            chunk.setStyleOffsets(this.reader.readByteArray(chunk.getStyleCount() * 4));
        }
        chunk.setStringPool(this.reader.readUtf16StringArray(chunk.getStringCount()));
        if (chunk.getStylePoolOffset() > 0) {
            chunk.setStringPool(this.reader.readUtf16StringArray(chunk.getStyleCount()));
        }
        this.reader.readByteArray(chunk.getChunkSize() - this.reader.totalConsume());
        return chunk;
    }

    private ResourceIdChunk parseXmlResourceIdChunk() {
        ResourceIdChunk chunk = new ResourceIdChunk();
        chunk.setChunkType(this.reader.readInt());
        chunk.setChunkSize(this.reader.readInt());

        int[] resourceIds = new int[chunk.getChunkSize() / 4 - 2];
        for (int i = 0; i < resourceIds.length; i++) {
            resourceIds[i] = this.reader.readInt();
        }
        chunk.setResourceIds(resourceIds);
        return chunk;
    }

    private List<XmlContentChunk> parseXmlContentChunk() {
        List<XmlContentChunk> xmlContentChunks = new ArrayList<>();
        for (; ; ) {
            int chunkType = this.reader.readInt();
            if (chunkType == -1) {
                break;
            }
            XmlContentChunk chunk = null;
            switch (chunkType) {
                case 0x00100100:  // StartNameSpace
                case 0x00100101:  // EndNameSpace
                    chunk = new NameSpaceChunk();
                    parseXmlNamespaceChunk((NameSpaceChunk) chunk);
                    break;
                case 0x00100102:  // StartTag
                    chunk = new StartTagChunk();
                    parseXmlTagChunk((StartTagChunk) chunk);
                    break;
                case 0x00100103:  // EndTag
                    chunk = new EndTagChunk();
                    parseXmlTagChunk((TagChunk) chunk);
                    break;
                case 0x00100104:  // Text
                default:
                    chunk = new TextChunk();
                    parseXmlTextChunk((TextChunk) chunk);
            }
            chunk.setChunkType(chunkType);
            xmlContentChunks.add(chunk);
        }
        return xmlContentChunks;
    }

    private void parseXmlNamespaceChunk(NameSpaceChunk chunk) {
        chunk.setChunkSize(this.reader.readInt());
        chunk.setLineNumber(this.reader.readInt());
        chunk.setUnknown(this.reader.readInt());
        chunk.setPrefix(this.reader.readInt());
        chunk.setUri(this.reader.readInt());
    }

    private void parseXmlTagChunk(TagChunk chunk) {
        chunk.setChunkSize(this.reader.readInt());
        chunk.setLineNumber(this.reader.readInt());
        chunk.setUnknown(this.reader.readInt());
        chunk.setNamespaceUri(this.reader.readInt());
        chunk.setName(this.reader.readInt());
        if ((chunk instanceof StartTagChunk)) {
            parseXmlStartTagChunk((StartTagChunk) chunk);
        }
    }

    private void parseXmlStartTagChunk(StartTagChunk chunk) {
        chunk.setFlags(this.reader.readInt());
        chunk.setAttributeCount(this.reader.readInt());
        chunk.setClassAttribute(this.reader.readInt());

        int[][] attributes = new int[chunk.getAttributeCount()][5];
        for (int i = 0; i < chunk.getAttributeCount(); i++) {
            for (int j = 0; j < 5; j++) {
                attributes[i][j] = this.reader.readInt();
            }
        }
        chunk.setAttributes(attributes);
    }

    private void parseXmlTextChunk(TextChunk chunk) {
        chunk.setChunkSize(this.reader.readInt());
        chunk.setLineNumber(this.reader.readInt());
        chunk.setUnknown(this.reader.readInt());
        chunk.setName(this.reader.readInt());

        int[] unknowns = new int[2];
        for (int i = 0; i < unknowns.length; i++) {
            unknowns[i] = this.reader.readInt();
        }
        chunk.setUnknowns(unknowns);
    }
}
