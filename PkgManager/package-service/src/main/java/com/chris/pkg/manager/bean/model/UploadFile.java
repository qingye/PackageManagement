package com.chris.pkg.manager.bean.model;

import java.io.Serializable;

public class UploadFile implements Serializable {

    private String fileName = null;
    private String charset = null;
    private String content = null;
    private long size = 0L;

    public String getFileName() {
        return this.fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getCharset() {
        return this.charset;
    }

    public void setCharset(String charset) {
        this.charset = charset;
    }

    public String getContent() {
        return this.content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public long getSize() {
        return this.size;
    }

    public void setSize(long size) {
        this.size = size;
    }
}
