package com.chris.pkg.manager.bean.android.resource.bean.chunk.type;

import java.io.Serializable;

public class ResTableMap implements Serializable {
    private ResTableRef name = null;
    private ResValue value = null;

    public ResTableRef getName() {
        return this.name;
    }

    public void setName(ResTableRef name) {
        this.name = name;
    }

    public ResValue getValue() {
        return this.value;
    }

    public void setValue(ResValue value) {
        this.value = value;
    }
}
