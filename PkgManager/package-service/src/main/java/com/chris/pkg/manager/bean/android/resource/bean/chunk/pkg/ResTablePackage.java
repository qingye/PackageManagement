package com.chris.pkg.manager.bean.android.resource.bean.chunk.pkg;

import com.chris.pkg.manager.bean.android.resource.bean.chunk.header.ResChunkHeader;
import com.chris.pkg.manager.bean.android.resource.bean.chunk.string.ResStringPoolHeader;

import java.io.Serializable;
import java.util.List;

public class ResTablePackage implements Serializable {
    private ResChunkHeader header = null;
    private int id = 0;
    private char[] name = null;
    private int typeStrings = 0;
    private int lastPublicType = 0;
    private int keyStrings = 0;
    private int lastPublicKey = 0;
    private int skip = 0;
    private ResStringPoolHeader typeStringPool = null;
    private ResStringPoolHeader keyStringPool = null;
    private List<Object> resTypes = null;

    public ResChunkHeader getHeader() {
        return this.header;
    }

    public void setHeader(ResChunkHeader header) {
        this.header = header;
    }

    public int getId() {
        return this.id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public char[] getName() {
        return this.name;
    }

    public void setName(char[] name) {
        this.name = name;
    }

    public int getTypeStrings() {
        return this.typeStrings;
    }

    public void setTypeStrings(int typeStrings) {
        this.typeStrings = typeStrings;
    }

    public int getLastPublicType() {
        return this.lastPublicType;
    }

    public void setLastPublicType(int lastPublicType) {
        this.lastPublicType = lastPublicType;
    }

    public int getKeyStrings() {
        return this.keyStrings;
    }

    public void setKeyStrings(int keyStrings) {
        this.keyStrings = keyStrings;
    }

    public int getLastPublicKey() {
        return this.lastPublicKey;
    }

    public void setLastPublicKey(int lastPublicKey) {
        this.lastPublicKey = lastPublicKey;
    }

    public int getSkip() {
        return this.skip;
    }

    public void setSkip(int skip) {
        this.skip = skip;
    }

    public ResStringPoolHeader getTypeStringPool() {
        return this.typeStringPool;
    }

    public void setTypeStringPool(ResStringPoolHeader typeStringPool) {
        this.typeStringPool = typeStringPool;
    }

    public ResStringPoolHeader getKeyStringPool() {
        return this.keyStringPool;
    }

    public void setKeyStringPool(ResStringPoolHeader keyStringPool) {
        this.keyStringPool = keyStringPool;
    }

    public List<Object> getResTypes() {
        return this.resTypes;
    }

    public void setResTypes(List<Object> resTypes) {
        this.resTypes = resTypes;
    }
}
