package com.chris.pkg.manager.bean.android.resource.bean.chunk.type;

import java.io.Serializable;

public class ResTableConfig implements Serializable {
    public static final int MASK_UI_MODE_TYPE = 0;
    public static final int UI_MODE_TYPE_ANY = 0;
    public static final int UI_MODE_TYPE_NORMAL = 1;
    public static final int UI_MODE_TYPE_DESK = 2;
    public static final int UI_MODE_TYPE_CAR = 3;
    public static final int UI_MODE_TYPE_TELEVISION = 4;
    public static final int UI_MODE_TYPE_APPLIANCE = 5;
    public static final int UI_MODE_TYPE_WATCH = 6;
    public static final int MASK_UI_MODE_NIGHT = 0;
    public static final int SHIFT_UI_MODE_NIGHT = 0;
    public static final int UI_MODE_NIGHT_ANY = 0;
    public static final int UI_MODE_NIGHT_NO = 1;
    public static final int UI_MODE_NIGHT_YES = 2;
    public static final int MASK_SCREENSIZE = 0;
    public static final int SCREENSIZE_ANY = 0;
    public static final int SCREENSIZE_SMALL = 1;
    public static final int SCREENSIZE_NORMAL = 2;
    public static final int SCREENSIZE_LARGE = 3;
    public static final int SCREENSIZE_XLARGE = 4;
    public static final int MASK_SCREENLONG = 0;
    public static final int SHIFT_SCREENLONG = 0;
    public static final int SCREENLONG_ANY = 0;
    public static final int SCREENLONG_NO = 1;
    public static final int SCREENLONG_YES = 2;
    public static final int MASK_LAYOUTDIR = 0;
    public static final int SHIFT_LAYOUTDIR = 0;
    public static final int LAYOUTDIR_ANY = 0;
    public static final int LAYOUTDIR_LTR = 1;
    public static final int LAYOUTDIR_RTL = 2;
    private int size = 0;
    private short mcc = 0;
    private short mnc = 0;
    private int imsi = 0;
    private byte[] language = new byte[2];
    private byte[] country = new byte[2];
    private int locale = 0;
    private byte orientation = 0;
    private byte touchscreen = 0;
    private short density = 0;
    private int screenType = 0;
    private byte keyboard = 0;
    private byte navigation = 0;
    private byte inputFlags = 0;
    private byte inputPad0 = 0;
    private int input = 0;
    private short screenWidth = 0;
    private short screenHeight = 0;
    private int screenSize = 0;
    private short sdkVersion = 0;
    private short minorVersion = 0;
    private int version = 0;
    private byte screenLayout = 0;
    private byte uiMode = 0;
    private short smallestScreenWidthDp = 0;
    private int screenConfig = 0;
    private short screenWidthDp = 0;
    private short screenHeightDp = 0;
    private int screenSizeDp = 0;
    private byte[] localeScript = new byte[4];
    private byte[] localeVariant = new byte[8];

    public int getSize() {
        return this.size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public short getMcc() {
        return this.mcc;
    }

    public void setMcc(short mcc) {
        this.mcc = mcc;
    }

    public short getMnc() {
        return this.mnc;
    }

    public void setMnc(short mnc) {
        this.mnc = mnc;
    }

    public int getImsi() {
        return this.imsi;
    }

    public void setImsi(int imsi) {
        this.imsi = imsi;
    }

    public byte[] getLanguage() {
        return this.language;
    }

    public void setLanguage(byte[] language) {
        this.language = language;
    }

    public byte[] getCountry() {
        return this.country;
    }

    public void setCountry(byte[] country) {
        this.country = country;
    }

    public int getLocale() {
        return this.locale;
    }

    public void setLocale(int locale) {
        this.locale = locale;
    }

    public byte getOrientation() {
        return this.orientation;
    }

    public void setOrientation(byte orientation) {
        this.orientation = orientation;
    }

    public byte getTouchscreen() {
        return this.touchscreen;
    }

    public void setTouchscreen(byte touchscreen) {
        this.touchscreen = touchscreen;
    }

    public short getDensity() {
        return this.density;
    }

    public void setDensity(short density) {
        this.density = density;
    }

    public int getScreenType() {
        return this.screenType;
    }

    public void setScreenType(int screenType) {
        this.screenType = screenType;
    }

    public byte getKeyboard() {
        return this.keyboard;
    }

    public void setKeyboard(byte keyboard) {
        this.keyboard = keyboard;
    }

    public byte getNavigation() {
        return this.navigation;
    }

    public void setNavigation(byte navigation) {
        this.navigation = navigation;
    }

    public byte getInputFlags() {
        return this.inputFlags;
    }

    public void setInputFlags(byte inputFlags) {
        this.inputFlags = inputFlags;
    }

    public byte getInputPad0() {
        return this.inputPad0;
    }

    public void setInputPad0(byte inputPad0) {
        this.inputPad0 = inputPad0;
    }

    public int getInput() {
        return this.input;
    }

    public void setInput(int input) {
        this.input = input;
    }

    public short getScreenWidth() {
        return this.screenWidth;
    }

    public void setScreenWidth(short screenWidth) {
        this.screenWidth = screenWidth;
    }

    public short getScreenHeight() {
        return this.screenHeight;
    }

    public void setScreenHeight(short screenHeight) {
        this.screenHeight = screenHeight;
    }

    public int getScreenSize() {
        return this.screenSize;
    }

    public void setScreenSize(int screenSize) {
        this.screenSize = screenSize;
    }

    public short getSdkVersion() {
        return this.sdkVersion;
    }

    public void setSdkVersion(short sdkVersion) {
        this.sdkVersion = sdkVersion;
    }

    public short getMinorVersion() {
        return this.minorVersion;
    }

    public void setMinorVersion(short minorVersion) {
        this.minorVersion = minorVersion;
    }

    public int getVersion() {
        return this.version;
    }

    public void setVersion(int version) {
        this.version = version;
    }

    public byte getScreenLayout() {
        return this.screenLayout;
    }

    public void setScreenLayout(byte screenLayout) {
        this.screenLayout = screenLayout;
    }

    public byte getUiMode() {
        return this.uiMode;
    }

    public void setUiMode(byte uiMode) {
        this.uiMode = uiMode;
    }

    public short getSmallestScreenWidthDp() {
        return this.smallestScreenWidthDp;
    }

    public void setSmallestScreenWidthDp(short smallestScreenWidthDp) {
        this.smallestScreenWidthDp = smallestScreenWidthDp;
    }

    public int getScreenConfig() {
        return this.screenConfig;
    }

    public void setScreenConfig(int screenConfig) {
        this.screenConfig = screenConfig;
    }

    public short getScreenWidthDp() {
        return this.screenWidthDp;
    }

    public void setScreenWidthDp(short screenWidthDp) {
        this.screenWidthDp = screenWidthDp;
    }

    public short getScreenHeightDp() {
        return this.screenHeightDp;
    }

    public void setScreenHeightDp(short screenHeightDp) {
        this.screenHeightDp = screenHeightDp;
    }

    public int getScreenSizeDp() {
        return this.screenSizeDp;
    }

    public void setScreenSizeDp(int screenSizeDp) {
        this.screenSizeDp = screenSizeDp;
    }

    public byte[] getLocaleScript() {
        return this.localeScript;
    }

    public void setLocaleScript(byte[] localeScript) {
        this.localeScript = localeScript;
    }

    public byte[] getLocaleVariant() {
        return this.localeVariant;
    }

    public void setLocaleVariant(byte[] localeVariant) {
        this.localeVariant = localeVariant;
    }
}
