package com.chris.pkg.manager.bean.android.resource.bean.chunk.type;

import com.chris.pkg.manager.bean.android.resource.bean.chunk.string.ResStringPoolRef;

import java.io.Serializable;

public class ResTableEntry implements Serializable {
    public static final int FLAG_COMPLEX = 1;
    public static final int FLAG_PUBLIC = 2;
    private short size = 0;
    private short flags = 0;
    private ResStringPoolRef ref = null;

    public short getSize() {
        return this.size;
    }

    public void setSize(short size) {
        this.size = size;
    }

    public short getFlags() {
        return this.flags;
    }

    public void setFlags(short flags) {
        this.flags = flags;
    }

    public ResStringPoolRef getRef() {
        return this.ref;
    }

    public void setRef(ResStringPoolRef ref) {
        this.ref = ref;
    }
}
