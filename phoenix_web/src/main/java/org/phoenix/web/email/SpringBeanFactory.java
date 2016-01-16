package org.phoenix.web.email;

import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;

/**
 * 通过此方法可方便的获取到配置文件中配置的bean对象和注入的对象
 * 并发量不会太大，单例模式
 * @author mengfeiyang
 *
 */
public class SpringBeanFactory extends SpringBeanAutowiringSupport {
    @Autowired
    private BeanFactory beanFactory;
    private static SpringBeanFactory instance = new SpringBeanFactory();
    private SpringBeanFactory(){}

    public Object getBeanById(String beanId) {
        return beanFactory.getBean(beanId);
    }

    public static SpringBeanFactory getInstance() {
        return instance;
    }
}