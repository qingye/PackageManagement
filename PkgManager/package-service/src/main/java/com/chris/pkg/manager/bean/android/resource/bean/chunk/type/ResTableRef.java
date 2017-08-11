package com.chris.pkg.manager.bean.android.resource.bean.chunk.type;

import java.io.Serializable;

public class ResTableRef implements Serializable {
    private int ident = 0;

    public int getIdent() {
        return this.ident;
    }

    public void setIdent(int ident) {
        this.ident = ident;
    }
}
