package com.chris.pkg.manager.bean.android.manifest.attribute;

public enum AttributeType {
    ATTR_NULL(0, "NULL"),
    ATTR_REFERENCE(1, "REFERENCE"),
    ATTR_ATTRIBUTE(2, "ATTRIBUTE"),
    ATTR_STRING(3, "STRING"),
    ATTR_FLOAT(4, "FLOAT"),
    ATTR_DIMENSION(5, "DIMENSION"),
    ATTR_FRACTION(6, "FRACTION"),
    ATTR_FIRSTINT(16, "FIRSTINT"),
    ATTR_HEX(17, "HEX"),
    ATTR_BOOLEAN(18, "BOOLEAN"),
    ATTR_FIRSTCOLOR(28, "FIRSTCOLOR"),
    ATTR_RGB8(29, "RGB8"),
    ATTR_ARGB4(30, "ARGB4"),
    ATTR_RGB4(31, "RGB4"),
    ATTR_LASTCOLOR(31, "LASTCOLOR"),
    ATTR_LASTINT(31, "LASTINT");

    private int type = 0;
    private String name = null;

    private AttributeType(int type, String name) {
        this.type = type;
        this.name = name;
    }

    public int getType() {
        return this.type;
    }

    public String getName() {
        return this.name;
    }
}
