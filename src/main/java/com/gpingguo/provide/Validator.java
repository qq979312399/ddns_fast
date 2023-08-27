package com.gpingguo.provide;

import com.gpingguo.model.em.PlatformEnum;
import org.springframework.stereotype.Component;

import java.lang.annotation.*;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Component
@Inherited
public @interface Validator {

    PlatformEnum authType();

}
