package com.lvdong.spring.common;

import com.lvdong.spring.utils.Config;
import com.lvdong.spring.utils.RedisUtil;
import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.apache.log4j.Logger;

import javax.annotation.Resource;
import java.util.List;

/**
 * 缓存切面：
 * 用来给不同的方法来加入判断如果缓存存在数据，从缓存取数据。否则第一次从数据库取，并将结果保存到缓存中去。
 * Created by lvdong on 2016/10/8.
 */
public class MethodCacheInterceptor implements MethodInterceptor {

    private static final Logger logger = Logger.getLogger(MethodCacheInterceptor.class);
    @Resource
    private RedisUtil redisUtil;

    private List<String> targetNamesList; // 不加入缓存的service名称
    private List<String> methodNamesList; // 不加入缓存的方法名称
    private Long defaultCacheExpireTime; // 缓存默认的过期时间
    private Long xxxRecordManagerTime; //
    private Long xxxSetRecordManagerTime; //

    /**
     * 初始化读取不需要加入缓存的类名和方法名称
     */
    public MethodCacheInterceptor() {
        try {
            // 分割字符串
            targetNamesList = Config.getList(String.class, "targetNames");
            methodNamesList = Config.getList(String.class, "methodNames");

            // 加载过期时间设置
            defaultCacheExpireTime = Config.getLong("defaultCacheExpireTime");
            xxxRecordManagerTime = Config.getLong("com.service.impl.xxxRecordManager");
            xxxSetRecordManagerTime = Config.getLong("com.service.impl.xxxSetRecordManager");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public Object invoke(MethodInvocation invocation) throws Throwable {
        Object value = null;
        String targetName = invocation.getThis().getClass().getName();
        String methodName = invocation.getMethod().getName();

        // 不需要缓存的内容
        // String[] targetNameArr = targetName.split(".");
        // String target = targetNameArr[targetNameArr.length - 1];
        if (!isAddCache(targetName, methodName)) {
            return invocation.proceed();
        }

        Object[] arguments = invocation.getArguments();
        String cacheKey = getCacheKey(targetName, methodName, arguments);
        logger.info("cacheKey : " + cacheKey);

        try {
            // 判断是否有缓存
            if (redisUtil.exists(cacheKey)) {
                return redisUtil.get(cacheKey);
            }

            // 写入缓存
            value = invocation.proceed();
            if (value != null) {
                final String tKey = cacheKey;
                final Object tValue = value;

                // 设置缓存时间
                new Thread(new Runnable() {
                    public void run() {
                        Long expireTime = defaultCacheExpireTime;
                        if (tKey.startsWith("com.service.impl.xxxRecordManager")) {
                            expireTime = xxxRecordManagerTime;
                        } else if (tKey.startsWith("com.service.impl.xxxSetRecordManager")) {
                            expireTime = xxxSetRecordManagerTime;
                        }
                        redisUtil.set(tKey, tValue, expireTime);
                    }
                }).start();
            }
        } catch (Exception e) {
            e.printStackTrace();
            if (value == null) {
                return invocation.proceed();
            }
        }

        return value;

    }

    /**
     * 是否加入缓存
     *
     * @return T Or F
     */
    private boolean isAddCache(String targetName, String methodName) {
        boolean flag = true;
        if (targetNamesList.contains(targetName)
                || methodNamesList.contains(methodName)) {
            flag = false;
        }
        return flag;
    }

    /**
     * 创建缓存key
     *
     * @param targetName
     * @param methodName
     * @param arguments
     */
    private String getCacheKey(String targetName, String methodName, Object[] arguments) {
        StringBuffer sbu = new StringBuffer();
        sbu.append(targetName).append("_").append(methodName);
        if ((arguments != null) && (arguments.length != 0)) {
            for (int i = 0; i < arguments.length; i++) {
                sbu.append("_").append(arguments[i]);
            }
        }
        return sbu.toString();
    }

}
