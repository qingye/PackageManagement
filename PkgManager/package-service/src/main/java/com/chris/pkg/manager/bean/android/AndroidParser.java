package com.chris.pkg.manager.bean.android;

import com.chris.pkg.manager.bean.android.manifest.XmlManifestParser;
import com.chris.pkg.manager.bean.android.resource.ResourceParser;
import java.io.File;
import java.util.Map;

/**
 * Created by chris on 2017/8/10.
 */
public class AndroidParser {

    private Map<String, String> info = null;

    public AndroidParser(File dir) {
        String path = dir.getAbsolutePath();

        File manifest = new File(path + "/" + "AndroidManifest.xml");
        if (manifest.exists()) {
            this.info = new XmlManifestParser(manifest).getInfo();
            manifest.delete();
        }
        File arsc = new File(path + "/" + "resources.arsc");
        if (arsc.exists()) {
            String appName = new ResourceParser(arsc).valueByResId((String) this.info.get("appName"));
            this.info.put("appName", appName);
            arsc.delete();
        }
    }

    public Map<String, String> getInfo() {
        return this.info;
    }
}
