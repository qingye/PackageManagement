package com.chris.pkg.manager.bean.android.resource.bean;

import com.chris.pkg.manager.bean.android.resource.bean.chunk.header.ResTableHeader;
import com.chris.pkg.manager.bean.android.resource.bean.chunk.pkg.ResTablePackage;
import com.chris.pkg.manager.bean.android.resource.bean.chunk.string.ResStringPoolHeader;

import java.io.Serializable;

public class ResChunkTable implements Serializable {
    private ResTableHeader header = null;
    private ResStringPoolHeader stringPool = null;
    private ResTablePackage[] pkg = null;

    public ResTableHeader getHeader() {
        return this.header;
    }

    public void setHeader(ResTableHeader header) {
        this.header = header;
    }

    public ResStringPoolHeader getStringPool() {
        return this.stringPool;
    }

    public void setStringPool(ResStringPoolHeader stringPool) {
        this.stringPool = stringPool;
    }

    public ResTablePackage[] getPkg() {
        return this.pkg;
    }

    public void setPkg(ResTablePackage[] pkg) {
        this.pkg = pkg;
    }
}
