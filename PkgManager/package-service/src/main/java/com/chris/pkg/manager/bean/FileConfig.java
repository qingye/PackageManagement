package com.chris.pkg.manager.bean;

import com.chris.pkg.manager.bean.model.UploadFile;

import java.io.Serializable;

/**
 * Created by chris on 2017/8/10.
 */
public class FileConfig implements Serializable {

    private String appIdentifier;
    private UploadFile uploadFile;

    public String getAppIdentifier() {
        return this.appIdentifier;
    }

    public void setAppIdentifier(String appIdentifier) {
        this.appIdentifier = appIdentifier;
    }

    public UploadFile getUploadFile() {
        return this.uploadFile;
    }

    public void setUploadFile(UploadFile uploadFile) {
        this.uploadFile = uploadFile;
    }
}
