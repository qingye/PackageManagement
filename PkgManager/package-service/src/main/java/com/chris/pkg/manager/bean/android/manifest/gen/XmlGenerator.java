package com.chris.pkg.manager.bean.android.manifest.gen;

import com.chris.pkg.manager.bean.android.manifest.attribute.Attribution;
import com.chris.pkg.manager.bean.android.manifest.bean.ChunkHeader;
import com.chris.pkg.manager.bean.android.manifest.bean.chunk.ChunkConstants;
import com.chris.pkg.manager.bean.android.manifest.bean.chunk.xmlcontent.XmlContentChunk;
import com.chris.pkg.manager.bean.android.manifest.bean.chunk.xmlcontent.namespace.base.NameSpaceChunk;
import com.chris.pkg.manager.bean.android.manifest.bean.chunk.xmlcontent.tag.base.TagChunk;
import com.chris.pkg.manager.bean.android.manifest.bean.chunk.xmlcontent.tag.EndTagChunk;
import com.chris.pkg.manager.bean.android.manifest.bean.chunk.xmlcontent.tag.StartTagChunk;
import com.chris.pkg.manager.bean.android.manifest.bean.chunk.xmlcontent.text.TextChunk;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class XmlGenerator {
    private final String GAP = "    ";
    private StringBuffer sb = new StringBuffer();
    private String[] arrayString = null;
    private Map<String, String> nameSpaceMap = null;
    private StringBuffer gap = new StringBuffer();
    private Map<String, String> info = new HashMap<>();

    public XmlGenerator(ChunkHeader header) {
        this.arrayString = header.getStringChunk().getStringPool();

        this.sb.append("<?xml version=\"1.0\" encoding=\"utf-8\"?>").append("\n");
        genXmlContent(header.getXmlContentChunks());
    }

    private void genXmlContent(List<XmlContentChunk> xmlContentChunks) {
        if (this.nameSpaceMap == null) {
            this.nameSpaceMap = new HashMap<>();
        } else {
            this.nameSpaceMap.clear();
        }
        String startTagName = null;
        for (XmlContentChunk chunk : xmlContentChunks) {
            switch (chunk.getChunkType()) {
                case ChunkConstants.START_NAMESPACE:
                    doStartNamespace((NameSpaceChunk) chunk);
                    break;
                case ChunkConstants.END_NAMESPACE:
                    doEndNamespace((NameSpaceChunk) chunk);
                    break;
                case ChunkConstants.START_TAG:
                    startTagName = this.arrayString[((TagChunk) chunk).getName()];
                    genStartTag((StartTagChunk) chunk);
                    this.gap.append("    ");
                    break;
                case ChunkConstants.END_TAG:
                    this.gap.setLength(this.gap.length() - "    ".length());
                    genEndTag((EndTagChunk) chunk, startTagName);
                    startTagName = null;
                    break;
                case ChunkConstants.TEXT:
                default:
                    genText((TextChunk) chunk);
            }
        }
    }

    private void doStartNamespace(NameSpaceChunk chunk) {
        if ((chunk.getUri() != -1) && (chunk.getPrefix() != -1)) {
            this.nameSpaceMap.put(this.arrayString[chunk.getUri()], this.arrayString[chunk.getPrefix()]);
        }
    }

    private void doEndNamespace(NameSpaceChunk chunk) {
        if ((chunk.getUri() != -1) && (chunk.getPrefix() != -1)) {
            this.nameSpaceMap.remove(this.arrayString[chunk.getUri()]);
        }
    }

    private void genStartTag(StartTagChunk chunk) {
        this.sb.append(this.gap).append("<").append(this.arrayString[chunk.getName()]).append(" ");
        if (this.arrayString[chunk.getName()].equals("manifest")) {
            Iterator<String> it = this.nameSpaceMap.keySet().iterator();
            while (it.hasNext()) {
                String namespace = it.next();
                String prefix = this.nameSpaceMap.get(namespace);
                this.sb.append("xmls:").append(prefix).append("=\"").append(namespace).append("\"\n").append(this.gap).append("    ");
            }
        }
        int[][] attributes = chunk.getAttributes();
        for (int i = 0; i < chunk.getAttributeCount(); i++) {
            int[] values = attributes[i];
            int attr = 0;
            boolean isNeed = false;
            for (int j = 0; j < values.length; j++) {
                attr = values[j];
                switch (j) {
                    case 0:
                        if (attr != -1) {
                            this.sb.append(this.nameSpaceMap.get(this.arrayString[attr])).append(":");
                        }
                        break;
                    case 1:
                        if (attr != -1) {
                            this.sb.append(this.arrayString[attr]).append("=\"");
                            isNeed = isNeed(this.arrayString[chunk.getName()], this.arrayString[attr]);
                        }
                        break;
                    case 4:
                        this.sb.append(Attribution.getAttributeData(this.arrayString, values[(j - 1)] >> 24, attr)).append("\"\n").append(this.gap).append("    ");
                        if (isNeed) {
                            gatherInfo(this.arrayString[values[1]], Attribution.getAttributeData(this.arrayString, values[(j - 1)] >> 24, attr));
                        }
                        break;
                }
            }
        }
        if (chunk.getAttributeCount() > 0) {
            this.sb.setLength(this.sb.length() - this.gap.length() - "    ".length());
        } else {
            this.sb.deleteCharAt(this.sb.length() - 1).append("\n");
        }
        this.sb.insert(this.sb.length() - 1, ">");
    }

    private void genEndTag(EndTagChunk chunk, String startTagName) {
        String endTagName = this.arrayString[chunk.getName()];
        if ((startTagName != null) && (startTagName.length() > 0) && (endTagName.equals(startTagName))) {
            this.sb.insert(this.sb.length() - 2, "/");
        } else {
            this.sb.append(this.gap).append("</").append(this.arrayString[chunk.getName()]).append(">").append("\n");
        }
    }

    private void genText(TextChunk chunk) {
        this.sb.append(this.gap).append(this.arrayString[chunk.getName()]);
    }

    private boolean isNeed(String chunkName, String name) {
        boolean need = false;
        if (((chunkName.equals("manifest")) || (chunkName.equals("application"))) && (
                (name.equals("versionName")) || (name.equals("package")) || (name.equals("label")))) {
            need = true;
        }
        return need;
    }

    private void gatherInfo(String name, String value) {
        if (name.equals("versionName")) {
            this.info.put("version", value);
        } else if (name.equals("label")) {
            this.info.put("appName", value);
        } else if (name.equals("package")) {
            this.info.put("bundleId", value);
        }
    }

    public Map<String, String> getInfo() {
        return this.info;
    }
}
