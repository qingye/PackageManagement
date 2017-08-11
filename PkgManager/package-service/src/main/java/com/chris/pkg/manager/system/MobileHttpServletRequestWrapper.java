package com.chris.pkg.manager.system;

import com.alibaba.fastjson.JSON;
import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

import javax.servlet.ReadListener;
import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.*;

/**
 * 存储请求数据(由于getInputStream只能调用一次)
 *
 * @author chris
 * @ClassName: MobileHttpServletRequestWrapper
 * @Description: 存储请求数据(由于getInputStream只能调用一次)
 * @date 2016年10月10日 11:58:15
 */
public class MobileHttpServletRequestWrapper extends HttpServletRequestWrapper {

    private static final Logger log = LoggerFactory.getLogger(MobileHttpServletRequestWrapper.class);
    private static final String DEFAULT_CHARSET = "UTF-8";
    private static final String ISO_CHARSET = "ISO-8859-1";
    private Map<String, String> headers = null;
    private Map<String, String[]> parameterMap = null;
    private Map<String, Object> parametersMap = null;
    private String inputStreamStr = null;

    public MobileHttpServletRequestWrapper(HttpServletRequest request) {
        super(request);

        try {
            CommonsMultipartResolver resolver = new CommonsMultipartResolver(request.getServletContext());
            if (resolver.isMultipart(request)) {
                MultipartHttpServletRequest msRequest = resolver.resolveMultipart(request);
                setParametersMap(msRequest);
                inputStreamStr = "";
            } else {
                setParametersMap(request);
                inputStreamStr = IOUtils.toString(request.getInputStream(), DEFAULT_CHARSET);
            }
            setHeaders(request);
        } catch (Exception e) {
            log.error(e.toString());
        }
    }

    @Override
    public ServletInputStream getInputStream() throws IOException {
        final ByteArrayInputStream is = new ByteArrayInputStream(inputStreamStr.getBytes(DEFAULT_CHARSET));
        return new ServletInputStream() {
            @Override
            public boolean isFinished() {
                return false;
            }

            @Override
            public boolean isReady() {
                return false;
            }

            @Override
            public void setReadListener(ReadListener readListener) {

            }

            @Override
            public int read() throws IOException {
                return is.read();
            }
        };
    }

    @Override
    public Map<String, String[]> getParameterMap() {
        return parameterMap;
    }

    public Map<String, Object> getParametersMap() {
        return parametersMap;
    }

    public Map<String, String> getHeaders() {
        return headers;
    }

    private void setHeaders(HttpServletRequest request) {
        headers = new HashMap<>();
        Enumeration<String> keys = request.getHeaderNames();
        while (keys.hasMoreElements()) {
            String key = keys.nextElement();
            String value = request.getHeader(key);
            headers.put(key, value);
        }
    }

    private void setParametersMap(HttpServletRequest request) {
        parameterMap = new HashMap<>(request.getParameterMap());
        parametersMap = new HashMap<>();
        for (Map.Entry<String, String[]> entry : parameterMap.entrySet()) {
            String[] dataObject = entry.getValue();
            if (dataObject.length > 1) {
                parametersMap.put(entry.getKey(), entry.getValue());
            } else {
                parametersMap.put(entry.getKey(), dataObject[0]);
            }
        }
        if (request instanceof MultipartHttpServletRequest) {
            addFiles((MultipartHttpServletRequest) request);
        }
    }

    private void addFiles(MultipartHttpServletRequest request) {
        List<Map<String, Object>> files = getFiles(request);
        parametersMap.put("uploadFile", files.size() == 1 ? files.get(0) : files);

        String[] fileArray = new String[files.size()];
        for (int i = 0; i < fileArray.length; i++) {
            fileArray[i] = JSON.toJSONString(files.get(i));
        }
        parameterMap.put("uploadFile", fileArray);
    }

    private List<Map<String, Object>> getFiles(MultipartHttpServletRequest request) {
        List<Map<String, Object>> files = new ArrayList<>();
        Iterator<String> keys = request.getFileNames();
        while (keys.hasNext()) {
            String key = keys.next();
            MultipartFile file = request.getFile(key);
            Map<String, Object> map = new HashMap<>();
            map.put("fileName", file.getOriginalFilename());
            map.put("charset", ISO_CHARSET);
            try {
                map.put("content", IOUtils.toString(file.getInputStream(), ISO_CHARSET));
            } catch (Exception e) {
            }
            map.put("size", file.getSize());
            files.add(map);
        }
        return files;
    }


}
