package com.chris.pkg.manager.bean.android.resource.bean.chunk.type;

import java.io.Serializable;

public class ResValue implements Serializable {
    public static final int TYPE_NULL = 0;
    public static final int TYPE_REFERENCE = 1;
    public static final int TYPE_ATTRIBUTE = 2;
    public static final int TYPE_STRING = 3;
    public static final int TYPE_FLOAT = 4;
    public static final int TYPE_DIMENSION = 5;
    public static final int TYPE_FRACTION = 6;
    public static final int TYPE_FIRST_INT = 16;
    public static final int TYPE_INT_DEC = 16;
    public static final int TYPE_INT_HEX = 17;
    public static final int TYPE_INT_BOOLEAN = 18;
    public static final int TYPE_FIRST_COLOR_INT = 28;
    public static final int TYPE_INT_COLOR_ARGB8 = 28;
    public static final int TYPE_INT_COLOR_RGB8 = 29;
    public static final int TYPE_INT_COLOR_ARGB4 = 30;
    public static final int TYPE_INT_COLOR_RGB4 = 31;
    public static final int TYPE_LAST_COLOR_INT = 31;
    public static final int TYPE_LAST_INT = 31;
    public static final int COMPLEX_UNIT_PX = 0;
    public static final int COMPLEX_UNIT_DIP = 1;
    public static final int COMPLEX_UNIT_SP = 2;
    public static final int COMPLEX_UNIT_PT = 3;
    public static final int COMPLEX_UNIT_IN = 4;
    public static final int COMPLEX_UNIT_MM = 5;
    public static final int COMPLEX_UNIT_SHIFT = 0;
    public static final int COMPLEX_UNIT_MASK = 15;
    public static final int COMPLEX_UNIT_FRACTION = 0;
    public static final int COMPLEX_UNIT_FRACTION_PARENT = 1;
    public static final int COMPLEX_RADIX_23p0 = 0;
    public static final int COMPLEX_RADIX_16p7 = 1;
    public static final int COMPLEX_RADIX_8p15 = 2;
    public static final int COMPLEX_RADIX_0p23 = 3;
    public static final int COMPLEX_RADIX_SHIFT = 4;
    public static final int COMPLEX_RADIX_MASK = 3;
    public static final int COMPLEX_MANTISSA_SHIFT = 8;
    public static final int COMPLEX_MANTISSA_MASK = 0xFFFFFF;
    private short size = 0;
    private byte res0 = 0;
    private byte dataType = 0;
    private int data = 0;

    public short getSize() {
        return this.size;
    }

    public void setSize(short size) {
        this.size = size;
    }

    public byte getRes0() {
        return this.res0;
    }

    public void setRes0(byte res0) {
        this.res0 = res0;
    }

    public byte getDataType() {
        return this.dataType;
    }

    public void setDataType(byte dataType) {
        this.dataType = dataType;
    }

    public int getData() {
        return this.data;
    }

    public void setData(int data) {
        this.data = data;
    }

    public String getTypeStr() {
        switch (this.dataType) {
            case 0:
                return "TYPE_NULL";
            case 1:
                return "TYPE_REFERENCE";
            case 2:
                return "TYPE_ATTRIBUTE";
            case 3:
                return "TYPE_STRING";
            case 4:
                return "TYPE_FLOAT";
            case 5:
                return "TYPE_DIMENSION";
            case 6:
                return "TYPE_FRACTION";
            case 16:
                return "TYPE_FIRST_INT";
            case 17:
                return "TYPE_INT_HEX";
            case 18:
                return "TYPE_INT_BOOLEAN";
            case 28:
                return "TYPE_FIRST_COLOR_INT";
            case 29:
                return "TYPE_INT_COLOR_RGB8";
            case 30:
                return "TYPE_INT_COLOR_ARGB4";
            case 31:
                return "TYPE_INT_COLOR_RGB4";
        }
        return "";
    }

    public String getTypeData(String[] str) {
        String result = null;
        int type = this.dataType;
        if (3 == type) {
            if (this.data != -1) {
                result = str[this.data];
            }
        } else if (2 == type) {
            result = String.format("?%s%08X", getPackage(data), data);
        } else if (1 == type) {
            result = String.format("@%s%08X", getPackage(data), data);
        } else if (4 == type) {
            result = String.valueOf(Float.intBitsToFloat(this.data));
        } else if (17 == type) {
            result = String.format("0x%08X", data);
        } else if (18 == type) {
            result = this.data == 0 ? "false" : "true";
        } else if (5 == type) {
            result = Float.toString(complexToFloat(this.data)) + com.chris.pkg.manager.bean.android.manifest.attribute.Attribution.DIMENSION_UNITS[(this.data & 0xF)];
        } else if (6 == type) {
            result = Float.toString(complexToFloat(this.data)) + com.chris.pkg.manager.bean.android.manifest.attribute.Attribution.FRACTION_UNITS[(this.data & 0xF)];
        } else if ((28 <= type) && (type <= 31)) {
            result = String.format("#%08X", data);
        } else if ((16 <= type) && (type <= 31)) {
            result = String.valueOf(this.data);
        }
        return result;
    }

    private String getPackage(int id) {
        String pkg = "";
        if (id >> 24 == 1) {
            pkg = "android:";
        }
        return pkg;
    }

    private float complexToFloat(int complex) {
        return (complex & 0xFF00) * com.chris.pkg.manager.bean.android.manifest.attribute.Attribution.RADIX_MULTS[(complex >> 4 & 0x3)];
    }
}
