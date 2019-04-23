package com.sxr.knowledge.bean;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@Import(MyImportBeanRegister.class)
public class MyConfiguration {

}
