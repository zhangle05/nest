/**
 * 
 */
package ￥UTIL_PACKAGE￥;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * @author zhangle
 *
 */
public class SpringContextHelper {

    private static final SpringContextHelper instance = new SpringContextHelper();
    private ApplicationContext ctx;

    /**
     * Constructor
     */
    private SpringContextHelper() {
        ctx = new ClassPathXmlApplicationContext("classpath*:META-INF/springContext.xml");
    }

    public static SpringContextHelper getInstance() {
        return instance;
    }

    public <T> T getBean(Class<T> requiredType) {
        return ctx.getBean(requiredType);
    }
}
