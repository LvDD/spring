package com.lvdong.spring.utils;

import org.apache.commons.configuration2.PropertiesConfiguration;
import org.apache.commons.configuration2.builder.ReloadingFileBasedConfigurationBuilder;
import org.apache.commons.configuration2.builder.fluent.Parameters;
import org.apache.commons.configuration2.ex.ConfigurationException;
import org.apache.commons.configuration2.reloading.PeriodicReloadingTrigger;
import org.apache.log4j.Logger;

import java.io.*;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;

/**
 * 配置项工具类
 * Created by lvdong on 2016/10/9.
 */
public class Config {


    private static Logger logger = Logger.getLogger(Config.class);
    private static PropertiesConfiguration configuration;
    public static Set<String> imgfliter = new HashSet<String>();

    /**
     * 初始化加载配置
     *
     * @throws IOException            IO异常
     * @throws ConfigurationException 配置异常
     */
    public static void init() throws IOException, ConfigurationException {

        // Read data from this file
        String path = "config.properties";
        logger.info("加载:" + path + " 配置文件");


        ReloadingFileBasedConfigurationBuilder<PropertiesConfiguration> builder =
                new ReloadingFileBasedConfigurationBuilder<PropertiesConfiguration>(PropertiesConfiguration.class)
                        .configure(new Parameters().fileBased()
                                .setFile(new File(path)));
        configuration = builder.getConfiguration();
        PeriodicReloadingTrigger trigger = new PeriodicReloadingTrigger(builder.getReloadingController(), null, 1, TimeUnit.MINUTES);
        trigger.start();
    }

    public static void readImgFliter(String filePath) {
        InputStreamReader isr = null;
        BufferedReader br = null;

        try {
            File file = new File(filePath);
            isr = new InputStreamReader(new FileInputStream(file), "UTF-8");
            br = new BufferedReader(isr);


            while (br.ready()) {
                String line = br.readLine();

                imgfliter.add(line);
            }

            br.close();
            isr.close();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                br.close();
                isr.close();
            } catch (Exception ee) {
            }
        }
    }

    /**
     * 获取String型配置项
     *
     * @param key 键
     * @return 值
     */
    public static String getString(String key) {

        return configuration.getString(key);
    }

    /**
     * 获取StringArray型配置项
     *
     * @param key 键
     * @return 值
     */
    public static String[] getStringArray(String key) {

        return configuration.getStringArray(key);
    }

    /**
     * 获取int型配置项
     *
     * @param key 键
     * @return 值
     */
    public static int getInt(String key) {

        return configuration.getInt(key);
    }

    /**
     * 获取int型配置项，如果值为null，则返回默认值
     *
     * @param key          键
     * @param defaultValue 默认值
     * @return 值
     */
    public static int getInt(String key, int defaultValue) {

        return configuration.getInt(key, defaultValue);
    }

    /**
     * 获取long型配置项
     *
     * @param key 键
     * @return 值
     */
    public static long getLong(String key) {
        return configuration.getLong(key);
    }

    /**
     * 获取List<T>型配置项
     *
     * @param cls 返回值泛型
     * @param key 键
     * @param <T> 泛型
     * @return 值
     */
    public static <T> List<T> getList(Class<T> cls, String key) {
        return configuration.getList(cls, key);
    }


}
