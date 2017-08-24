package com.chris.pkg.manager.utils;

import com.chris.pkg.manager.bean.android.AndroidParser;
import com.chris.pkg.manager.bean.constants.PackageConstants;
import com.chris.pkg.manager.bean.ios.NSDictionary;
import com.chris.pkg.manager.bean.ios.PropertyListParser;

import java.io.*;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

/**
 * Created by chris on 2017/8/10.
 */
public class FileUtil {

    private static final int BUFFER_SIZE = 1024;

    public static File write(String filename, byte[] content) {
        return write(getTempDirPath() + "/" + System.currentTimeMillis() + getFilenameInfo(filename, true), new ByteArrayInputStream(content));
    }

    public static void write(String filePath, File src) {
        try {
            write(filePath, new FileInputStream(src));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void write(String filePath, String content) {
        write(filePath, new ByteArrayInputStream(content.getBytes()));
    }

    public static Map<String, String> getPackInfo(File pack) throws Exception {
        String filename = getFilenameInfo(pack.getAbsolutePath(), false) + ".zip";
        if (pack.exists()) {
            unzip(write(filename, new FileInputStream(pack)));
        }
        return getFilenameInfo(pack.getAbsolutePath(), true).equals(".ipa") ? getPlist() : getManifest();
    }

    private static Map<String, String> getPlist() {
        Map<String, String> info = null;
        File plist = new File(getTempDirPath() + "/" + "Info.plist");
        if (plist.exists()) {
            try {
                NSDictionary dict = (NSDictionary) PropertyListParser.parse(plist);
                info = new HashMap<>();
                if (dict.objectForKey("CFBundleDisplayName") != null) {
                    info.put("appName", dict.objectForKey("CFBundleDisplayName").toString());
                } else {
                    info.put("appName", dict.objectForKey("CFBundleName").toString());
                }
                info.put("bundleId", dict.objectForKey("CFBundleIdentifier").toString());
                info.put("version", dict.objectForKey("CFBundleShortVersionString").toString());
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                plist.delete();
            }
        }
        return info;
    }

    private static Map<String, String> getManifest() {
        return new AndroidParser(new File(getTempDirPath())).getInfo();
    }

    public static String getRootPath() {
        return System.getProperty("webapp.root");
    }

    public static String getRootLastPath() {
        String rootPath = getRootPath();
        if (rootPath.lastIndexOf("/") == rootPath.length() - 1) {
            rootPath = rootPath.substring(0, rootPath.length() - 1);
        }
        if (rootPath.lastIndexOf("/") >= 0) {
            rootPath = rootPath.substring(0, rootPath.lastIndexOf("/"));
        }
        return rootPath;
    }

    public static String getTempDirPath() {
        File dirs = new File(getRootPath() + "temp/");
        if (dirs.exists()) {
            dirs.delete();
        }
        dirs.mkdirs();
        return dirs.getAbsolutePath();
    }

    private static String getFilenameInfo(String file, boolean suffix) {
        String info = null;
        int indexOf = file.lastIndexOf(".");
        if (suffix) {
            info = file.substring(indexOf, file.length());
        } else {
            info = file.substring(0, indexOf);
        }
        return info;
    }

    private static File write(String filePath, InputStream input) {
        File file = null;
        try {
            file = new File(filePath);
            FileOutputStream out = new FileOutputStream(file);
            byte[] buffer = new byte[BUFFER_SIZE];
            int count = 0;
            while ((count = input.read(buffer, 0, BUFFER_SIZE)) != -1) {
                out.write(buffer, 0, count);
            }
            out.flush();
            out.close();
            input.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return file;
    }

    private static void unzip(File file) throws Exception {
        ZipFile zipFile = null;
        try {
            zipFile = new ZipFile(file);
            Enumeration<?> zipEnum = zipFile.entries();
            while (zipEnum.hasMoreElements()) {
                ZipEntry entry = (ZipEntry) zipEnum.nextElement();
                if (PackageConstants.fileBingo(entry.getName())) {
                    String[] nameList = entry.getName().split("\\/");
                    write(getTempDirPath() + "/" + nameList[(nameList.length - 1)], zipFile.getInputStream(entry));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (zipFile != null) {
                zipFile.close();
            }
            if (file != null) {
                file.delete();
            }
        }
    }
}
