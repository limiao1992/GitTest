package cn.li.maven;

import cn.li.service.UserService;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.core.io.ClassPathResource;

/**
 * ClassName:RedisTemplateTest
 * Package:cn.li.maven
 * Description:
 *
 * @date:2019/7/6 21:41
 * @author:cn.li
 */
public class RedisTemplateTest {

    /*
    * 测试string RedisTemplate
    *
    * */
    @Test
    public void t1(){
        ClassPathXmlApplicationContext ctx=new ClassPathXmlApplicationContext("spring-redis.xml");
        UserService userService=ctx.getBean(UserService.class);
        System.out.println(userService);
        String key="app";
        String applicationName=userService.getString(key);
        System.out.println(applicationName);
    }
}
