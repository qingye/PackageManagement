package com.chris.pkg.manager.bean.ios.template;

public class PlistTemplate {
    public String getTemplate(String ipa, String bundleId, String appName) {
        StringBuilder sb = new StringBuilder();
        sb.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n")
                .append("<!DOCTYPE plist PUBLIC \"-//Apple//DTD PLIST 1.0//EN\" \"http://www.apple.com/DTDs/PropertyList-1.0.dtd\">\n")
                .append("<plist version=\"1.0\">\n")
                .append("<dict>\n")
                .append("\t<key>items</key>\n")
                .append("\t<array>\n")
                .append("\t\t<dict>\n")
                .append("\t\t\t<key>assets</key>\n")
                .append("\t\t\t<array>\n")
                .append("\t\t\t\t<dict>\n")
                .append("\t\t\t\t\t<key>kind</key>\n")
                .append("\t\t\t\t\t<string>software-package</string>\n")
                .append("\t\t\t\t\t<key>url</key>\n")
                .append("\t\t\t\t\t<string>").append(ipa).append("</string>\n")
                .append("\t\t\t\t</dict>\n")
                .append("\t\t\t\t<dict>\n")
                .append("\t\t\t\t\t<key>kind</key>\n")
                .append("\t\t\t\t\t<string>display-image</string>\n")
                .append("\t\t\t\t\t<key>needs-shine</key>\n")
                .append("\t\t\t\t\t<false/>\n")
                .append("\t\t\t\t\t<key>url</key>\n")
                .append("\t\t\t\t\t<string></string>\n")
                .append("\t\t\t\t</dict>\n")
                .append("\t\t\t</array>\n")
                .append("\t\t\t<key>metadata</key>\n")
                .append("\t\t\t<dict>\n")
                .append("\t\t\t\t<key>bundle-identifier</key>\n")
                .append("\t\t\t\t<string>").append(bundleId).append("</string>\n")
                .append("\t\t\t\t<key>bundle-version</key>\n")
                .append("\t\t\t\t<string>1.0</string>\n")
                .append("\t\t\t\t<key>kind</key>\n")
                .append("\t\t\t\t<string>software</string>\n")
                .append("\t\t\t\t<key>title</key>\n")
                .append("\t\t\t\t<string>").append(appName).append("</string>\n")
                .append("\t\t\t</dict>\n")
                .append("\t\t</dict>\n")
                .append("\t</array>\n")
                .append("</dict>\n")
                .append("</plist>");

        return sb.toString();
    }
}
