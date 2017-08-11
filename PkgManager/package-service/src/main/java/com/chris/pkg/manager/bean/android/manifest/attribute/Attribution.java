package com.chris.pkg.manager.bean.android.manifest.attribute;

public class Attribution {
    public static final int NAMESPACE_URI = 0;
    public static final int NAME = 1;
    public static final int VALUE = 2;
    public static final int TYPE = 3;
    public static final int DATA = 4;
    public static final int COMPLEX_UNIT_MASK = 15;
    public static final float[] RADIX_MULTS = {0.00390625F, 3.051758E-5F, 1.192093E-7F, 4.656613E-10F};
    public static final String[] DIMENSION_UNITS = {"px", "dip", "sp", "pt", "in", "mm", "", ""};
    public static final String[] FRACTION_UNITS = {"%", "%p", "", "", "", "", "", ""};

    public static String getNameByType(int type) {
        String name = null;
        switch (type) {
            case 0:
            case 7:
            case 8:
            case 9:
            case 10:
            case 11:
            case 12:
            case 13:
            case 14:
            case 15:
            case 19:
            case 20:
            case 21:
            case 22:
            case 23:
            case 24:
            case 25:
            case 26:
            case 27:
            default:
                name = AttributeType.ATTR_NULL.getName();
                break;
            case 1:
                name = AttributeType.ATTR_REFERENCE.getName();
                break;
            case 2:
                name = AttributeType.ATTR_ATTRIBUTE.getName();
                break;
            case 3:
                name = AttributeType.ATTR_STRING.getName();
                break;
            case 4:
                name = AttributeType.ATTR_FLOAT.getName();
                break;
            case 5:
                name = AttributeType.ATTR_DIMENSION.getName();
                break;
            case 6:
                name = AttributeType.ATTR_FRACTION.getName();
                break;
            case 16:
                name = AttributeType.ATTR_FIRSTINT.getName();
                break;
            case 17:
                name = AttributeType.ATTR_HEX.getName();
                break;
            case 18:
                name = AttributeType.ATTR_BOOLEAN.getName();
                break;
            case 28:
                name = AttributeType.ATTR_FIRSTCOLOR.getName();
                break;
            case 29:
                name = AttributeType.ATTR_RGB8.getName();
                break;
            case 30:
                name = AttributeType.ATTR_ARGB4.getName();
                break;
            case 31:
                name = AttributeType.ATTR_RGB4.getName();
        }
        return name;
    }

    public static String getAttributeData(String[] str, int type, int data) {
        String result = null;
        if (AttributeType.ATTR_STRING.getType() == type) {
            if (data != -1) {
                result = str[data];
            }
        } else if (AttributeType.ATTR_ATTRIBUTE.getType() == type) {
            result = String.format("?%s%08X", getPackage(data), data);
        } else if (AttributeType.ATTR_REFERENCE.getType() == type) {
            result = String.format("@%s%08X", getPackage(data), data);
        } else if (AttributeType.ATTR_FLOAT.getType() == type) {
            result = String.valueOf(Float.intBitsToFloat(data));
        } else if (AttributeType.ATTR_HEX.getType() == type) {
            result = String.format("0x%08X", data);
        } else if (AttributeType.ATTR_BOOLEAN.getType() == type) {
            result = data == 0 ? "false" : "true";
        } else if (AttributeType.ATTR_DIMENSION.getType() == type) {
            result = Float.toString(complexToFloat(data)) + DIMENSION_UNITS[(data & 0xF)];
        } else if (AttributeType.ATTR_FRACTION.getType() == type) {
            result = Float.toString(complexToFloat(data)) + FRACTION_UNITS[(data & 0xF)];
        } else if ((AttributeType.ATTR_FIRSTCOLOR.getType() <= type) && (type <= AttributeType.ATTR_LASTCOLOR.getType())) {
            result = String.format("#%08X", data);
        } else if ((AttributeType.ATTR_FIRSTINT.getType() <= type) && (type <= AttributeType.ATTR_LASTINT.getType())) {
            result = String.valueOf(data);
        }
        return result;
    }

    private static String getPackage(int id) {
        String pkg = "";
        if (id >> 24 == 1) {
            pkg = "android:";
        }
        return pkg;
    }

    private static float complexToFloat(int complex) {
        return (complex & 0xFF00) * RADIX_MULTS[(complex >> 4 & 0x3)];
    }
}
