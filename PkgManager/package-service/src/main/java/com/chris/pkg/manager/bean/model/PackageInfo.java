package com.chris.pkg.manager.bean.model;

import java.io.Serializable;

public class PackageInfo implements Serializable {
    private String appName = null;
    private String bundleId = null;
    private String version = null;

    public PackageInfo() {
    }

    public PackageInfo(String appName, String bundleId, String version) {
        this.appName = appName;
        this.bundleId = bundleId;
        this.version = version;
    }

    public String getAppName() {
        return this.appName;
    }

    public void setAppName(String appName) {
        this.appName = appName;
    }

    public String getBundleId() {
        return this.bundleId;
    }

    public void setBundleId(String bundleId) {
        this.bundleId = bundleId;
    }

    public String getVersion() {
        return this.version;
    }

    public void setVersion(String version) {
        this.version = version;
    }
}
