package com.gpingguo;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.web.embedded.tomcat.TomcatServletWebServerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import java.net.InetAddress;
import java.net.UnknownHostException;

@Slf4j
@Component
public class MySpringRunner implements ApplicationRunner {

    @Autowired
    ApplicationContext applicationContext;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        print();
    }

    /**
     * @Description 启动成功
     * @param
     * @return void
     **/
    private void print() {
            try {
                String host = InetAddress.getLocalHost().getHostAddress();
                TomcatServletWebServerFactory tomcatServletWebServerFactory = (TomcatServletWebServerFactory) applicationContext
                        .getBean("tomcatServletWebServerFactory");
                int port = tomcatServletWebServerFactory.getPort();
                String contextPath = tomcatServletWebServerFactory.getContextPath();
                String url = "http://" + host + ":" + port + contextPath + "/";
                log.info("启动成功,访问:{}", url);
            } catch (UnknownHostException e) {
                //输出到日志文件中
                log.error("获取访问路径失败");
            }
    }
}
