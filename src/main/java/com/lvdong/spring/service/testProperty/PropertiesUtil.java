package com.lvdong.spring.service.testProperty;

import org.apache.commons.lang.ArrayUtils;
import org.springframework.beans.factory.config.PropertyPlaceholderConfigurer;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * Created by lvdong on 2017/7/4.
 */
public class PropertiesUtil {

    public static void main(String[] args) {
        ClassPathXmlApplicationContext ctx = new ClassPathXmlApplicationContext("classpath:/testPropertyConf/properties.xml");
        HelloWorld a = (HelloWorld) ctx.getBean("helloWorldA");
        HelloWorld b = (HelloWorld) ctx.getBean("helloWorldB");

        String[] beanNamesForType = ctx.getBeanNamesForType(PropertyPlaceholderConfigurer.class);
        System.out.println(ArrayUtils.toString(beanNamesForType));
        System.out.println("########################");
        System.out.println(a.getName());
        System.out.println(a.getId());
        System.out.println(a.getColor());
        System.out.println("~~~~~~~~~~~~~~~~");
        System.out.println(b.getName());
        System.out.println(b.getId());
        System.out.println(b.getColor());
    }
}
