package com.lsq.context.bean;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * Created by liushiquan on 2019/1/4.
 */
public abstract class AbstractEnvironment implements Environment {

    static Properties pro = new Properties();

    public static final String DEFAULT_APP_PROPERTIES_FILENAME = "application.properties";

    /**
     * 加载配置文件
     */
    private void loadingProperties(String appProPath) {
        InputStream is = null;
        try {
            is = ApplicationContext.class.getClassLoader().getResourceAsStream(appProPath);
            pro.load(is);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if(is != null) {
                try {
                    is.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public void initEnvironment(String appProPath) {
        if(null == appProPath || "".equals(appProPath.trim())) {
        	loadingProperties(DEFAULT_APP_PROPERTIES_FILENAME);
        }
        loadingProperties(appProPath);
    }

}
