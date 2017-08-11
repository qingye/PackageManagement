package com.chris.pkg.manager.bean.ios.repng;

/**
 * Created by chris on 2017/8/10.
 */
public class IOSPngChunk {
    private int length = 0;
    private String type = null;
    private byte[] data = null;
    private int crc = 0;

    public int getLength() {
        return this.length;
    }

    public void setLength(int length) {
        this.length = length;
    }

    public String getType() {
        return this.type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public byte[] getData() {
        return this.data;
    }

    public void setData(byte[] data) {
        this.data = data;
    }

    public int getCrc() {
        return this.crc;
    }

    public void setCrc(int crc) {
        this.crc = crc;
    }
}
