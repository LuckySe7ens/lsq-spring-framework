package com.lsq.context.bean;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * Created by liushiquan on 2019/1/4.
 */
public abstract class DefaultApplicationContext implements ApplicationContext {

    static Properties pro = new Properties();

    private static void init(String appProPath) {
        InputStream is = null;
        try {
            ApplicationContext.class.getClassLoader().getResourceAsStream(appProPath);
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

    public static final void startUp() {
        startUp(DEFAULT_APP_PROPERTIES_FILENAME);
    }

    public static final void startUp(String appProperties) {

    }
}
