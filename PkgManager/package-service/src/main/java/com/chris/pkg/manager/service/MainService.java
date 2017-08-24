package com.chris.pkg.manager.service;

import com.alibaba.fastjson.JSON;
import com.chris.pkg.manager.bean.FileConfig;
import com.chris.pkg.manager.bean.ios.Base64;
import com.chris.pkg.manager.bean.ios.repng.NormalizedPNG;
import com.chris.pkg.manager.bean.ios.template.PlistTemplate;
import com.chris.pkg.manager.bean.model.UploadFile;
import com.chris.pkg.manager.dao.entity.ConfigEntity;
import com.chris.pkg.manager.dao.mapper.PMConfigDaoMapper;
import com.chris.pkg.manager.system.PropertyPlaceHolder;
import com.chris.pkg.manager.utils.FileUtil;

import java.io.File;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MainService {
    private static final Logger log = LoggerFactory.getLogger(MainService.class);
    @Autowired
    private PMConfigDaoMapper daoMapper;

    public void fileUpload(FileConfig config) {
        ConfigEntity entity = new ConfigEntity();
        entity.setKeyId(UUID.randomUUID().toString());
        entity.setState(1);

        UploadFile uploadFile = config.getUploadFile();
        try {
            File file = FileUtil.write(uploadFile.getFileName(), uploadFile.getContent().getBytes(uploadFile.getCharset()));
            if ((!file.getName().endsWith(".ipa")) && (!file.getName().endsWith(".apk"))) {
                throw new RuntimeException("文件类型不符合!");
            }
            Map<String, String> info = FileUtil.getPackInfo(file);
            entity.setFileName(file.getName());
            if (file.getName().endsWith(".ipa")) {
                entity.setType("iOS");
            } else if (file.getName().endsWith(".apk")) {
                entity.setType("android");
            }
            if (info != null) {
                entity.setVersion(info.get("version"));
                entity.setAppName(info.get("appName"));

                String bundleId = info.get("bundleId");
                entity.setBundleId(bundleId);
                String identifier = bundleId.replace("hoc", "").replace("dev", "").replace("pl", "").replace("qa", "");
                while (identifier.endsWith(".")) {
                    identifier = identifier.substring(0, identifier.length() - 1);
                }
                entity.setAppIdentifier(identifier);

                if (entity.getType().equals("iOS")) {
                    configHtml(entity);
                }

                String destDir = String.format("%s/apps/%s/%s/%s", FileUtil.getRootLastPath(), entity.getAppIdentifier(), entity.getVersion(), entity.getType());
                moveFiles(FileUtil.getTempDirPath(), destDir, entity);
            } else {
                throw new RuntimeException("这是个假文件!");
            }
            log.info("file entity = {}", JSON.toJSONString(entity));
            this.daoMapper.insertConfig(entity);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void moveFiles(String srcDir, String destDir, ConfigEntity entity) {
        log.info("\n{} \n=> \n{}", srcDir, destDir);
        File dir = new File(destDir);
        if (!dir.exists()) {
            dir.mkdirs();
        }
        String httpPrefix = "https://appmgr.vd.cn/";

        String relativePath = String.format("apps/%s/%s/%s", entity.getAppIdentifier(), entity.getVersion(), entity.getType());
        String httpPath = String.format("%s%s", httpPrefix, relativePath);
        String ipa = null;
        String icon = null;

        File sd = new File(srcDir);
        File[] fileList = sd.listFiles();
        if (fileList != null) {
            for (File file : fileList) {
                String filepath = destDir + "/" + file.getName();
                if ((file.getName().endsWith(".ipa")) || (file.getName().endsWith(".apk"))) {
                    ipa = file.getName();
                    entity.setPhysicalPath(String.format("%s/%s", relativePath, file.getName()));
                    entity.setFileUrl(String.format("%s/%s", httpPath, file.getName()));
                } else {
                    if (entity.getType().equals("iOS")) {
                        file = new NormalizedPNG(file).getPNGFile();
                        filepath = destDir + "/" + file.getName();
                    }
                    icon = file.getName();
                    entity.setIconUrl(String.format("%s/%s", httpPath, file.getName()));
                }
                FileUtil.write(filepath, file);
                file.delete();
            }
        }

        if (entity.getType().equals("iOS")) {
            String plistName = String.format("%d.plist", System.currentTimeMillis());
            FileUtil.write(
                    String.format("%s/%s", destDir, plistName),
                    new PlistTemplate(httpPath, ipa, icon, entity.getBundleId(), entity.getAppName()).getPlist()
            );
            entity.setFileUrl(String.format("itms-services://?action=download-manifest&url=%s/%s", httpPath, plistName));
        }
        sd.delete();
    }

    private void configHtml(ConfigEntity entity) {
        ConfigEntity ce = new ConfigEntity();
        ce.setAppIdentifier(entity.getAppIdentifier());
        ce.setBundleId(entity.getBundleId());
        ce.setVersion(entity.getVersion());
        entity.setHtmlUrl(String.format("%s/%s",
                PropertyPlaceHolder.getProperty("singlePageForIOS"),
                Base64.encodeBytes(JSON.toJSONString(ce).getBytes())));
    }

    public List<ConfigEntity> getMenus() {
        List<ConfigEntity> list = null;
        try {
            list = this.daoMapper.queryMenu();
            if ((list != null) && (list.size() > 0)) {
                for (ConfigEntity entity : list) {
                    entity.setAppName(entity.getAppName().replace("DEV", "").replace("PL", "").trim());
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    public List<ConfigEntity> getNewestVersionByIdentifier(ConfigEntity config) {
        List<ConfigEntity> list = null;
        try {
            list = this.daoMapper.queryNewestVersionByIdentifier(config);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    public List<ConfigEntity> getVersions(ConfigEntity config) {
        List<ConfigEntity> list = null;
        try {
            list = this.daoMapper.queryVersions(config);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    public List<ConfigEntity> getApps(ConfigEntity config) {
        List<ConfigEntity> list = null;
        try {
            list = this.daoMapper.queryApps(config);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }
}
