package com.gpingguo.provide;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

import java.util.concurrent.ConcurrentHashMap;

@Component
@Slf4j
public class DnsContainer implements ApplicationListener<ApplicationEvent> {

private ConcurrentHashMap<String, DnsService> validatorMap = new ConcurrentHashMap<>();

@Override
public void onApplicationEvent(ApplicationEvent applicationEvent) {

    ContextRefreshedEvent contextRefreshedEvent = (ContextRefreshedEvent) applicationEvent;
    ApplicationContext context = contextRefreshedEvent.getApplicationContext();

    String[] names = context.getBeanNamesForAnnotation(Validator.class);
    for (String name : names) {
        log.info("DnsService:{}", name);
        DnsService dnsService = context.getBean(name, DnsService.class);
        Validator validator = dnsService.getClass().getAnnotation(Validator.class);
        validatorMap.put(validator.authType().getName(), dnsService);
    }
}

    public ConcurrentHashMap<String, DnsService> getValidatorMap() {
        return validatorMap;
    }

}
