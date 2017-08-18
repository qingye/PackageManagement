package com.chris.pkg.manager.system;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.config.PropertyPlaceholderConfigurer;

import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

/**
 * Created by chris on 2017/8/16.
 */
public class PropertyPlaceHolder extends PropertyPlaceholderConfigurer {

    private static Map<String, String> properties = new HashMap<>();

    @Override
    protected void processProperties(ConfigurableListableBeanFactory beanFactoryToProcess, Properties props) throws BeansException {
        super.processProperties(beanFactoryToProcess, props);
        for (Object key : props.keySet()) {
            String strKey = key.toString();
            String value = props.getProperty(strKey);
            properties.put(strKey, value);
        }
    }

    public static String getProperty(String name) {
        return properties.get(name);
    }
}
