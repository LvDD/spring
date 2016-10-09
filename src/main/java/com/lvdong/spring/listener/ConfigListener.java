package com.lvdong.spring.listener;

import com.lvdong.spring.utils.Config;
import org.apache.commons.configuration2.ex.ConfigurationException;
import org.apache.log4j.Logger;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import java.io.IOException;

/**
 * 配置监听器
 * Created by lvdong on 2016/10/9.
 */
public class ConfigListener implements ServletContextListener {

    private static final Logger logger = Logger.getLogger(Config.class);

    public void contextInitialized(ServletContextEvent servletContextEvent) {
        logger.info("初始化ConfigListener");
        try {
            Config.init();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ConfigurationException e) {
            e.printStackTrace();
        }
    }

    public void contextDestroyed(ServletContextEvent servletContextEvent) {
        logger.info("销毁ConfigListener");
    }
}
