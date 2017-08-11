package com.chris.pkg.manager.bean.android.resource.bean.chunk.header;

import java.io.Serializable;

public class ResTableHeader implements Serializable {
    private ResChunkHeader header = null;
    private int packageCount = 0;

    public ResChunkHeader getHeader() {
        return this.header;
    }

    public void setHeader(ResChunkHeader header) {
        this.header = header;
    }

    public int getPackageCount() {
        return this.packageCount;
    }

    public void setPackageCount(int packageCount) {
        this.packageCount = packageCount;
    }
}
