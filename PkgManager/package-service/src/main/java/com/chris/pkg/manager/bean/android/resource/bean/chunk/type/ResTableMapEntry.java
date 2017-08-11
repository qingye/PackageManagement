package com.chris.pkg.manager.bean.android.resource.bean.chunk.type;

public class ResTableMapEntry extends ResTableEntry {
    private ResTableRef parent = null;
    private int count = 0;

    public ResTableRef getParent() {
        return this.parent;
    }

    public void setParent(ResTableRef parent) {
        this.parent = parent;
    }

    public int getCount() {
        return this.count;
    }

    public void setCount(int count) {
        this.count = count;
    }
}
