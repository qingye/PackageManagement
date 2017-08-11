package com.chris.pkg.manager.bean.android.manifest.bean.chunk.resourceid;

import com.chris.pkg.manager.bean.android.manifest.bean.chunk.base.BaseChunk;

public class ResourceIdChunk extends BaseChunk {
    private int[] resourceIds = null;

    public int[] getResourceIds() {
        return this.resourceIds;
    }

    public void setResourceIds(int[] resourceIds) {
        this.resourceIds = resourceIds;
    }
}
