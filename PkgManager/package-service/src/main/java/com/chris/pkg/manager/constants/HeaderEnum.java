package com.chris.pkg.manager.constants;

/**
 * Created by chris on 2017/8/10.
 */
public enum HeaderEnum {

    AccessControlAllowOrigin("Access-Control-Allow-Origin"),
    AccessControlAllowMethods("Access-Control-Allow-Methods"),
    AccessControlMaxAge("Access-Control-Max-Age"),
    AccessControlAllowHeaders("Access-Control-Allow-Headers"),
    AccessControlAllowCredentials("Access-Control-Allow-Credentials");

    public String value;

    private HeaderEnum(String value) {
        this.value = value;
    }
}
