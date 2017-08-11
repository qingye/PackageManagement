package com.chris.pkg.manager.bean.constants;

public class PackageConstants {
    public static final String IPA_INFO_PLIST = "Info.plist";
    public static final String IPA_APP_ICON = "Icon@3x.png";
    public static final String APK_MANIFEST_XML = "AndroidManifest.xml";
    public static final String APK_RESOURCE_ARSC = "resources.arsc";
    public static final String APK_APP_ICON = "app_logo.png";

    private static String iosFileKey(String key) {
        return String.format(".app/%s", key);
    }

    public static boolean fileBingo(String name) {
        String[] sn = name.split("/");

        return (name.endsWith(iosFileKey("Info.plist"))) ||
                (name.endsWith(iosFileKey("Icon@3x.png"))) ||
                (name.equals("AndroidManifest.xml")) ||
                (name.equals("resources.arsc")) ||
                (sn[(sn.length - 1)].equals("app_logo.png"));
    }
}
