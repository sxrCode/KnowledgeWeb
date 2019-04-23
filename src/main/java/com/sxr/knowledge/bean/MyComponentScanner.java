package com.sxr.knowledge.bean;

import java.util.Set;

import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.ClassPathScanningCandidateComponentProvider;

public class MyComponentScanner extends ClassPathScanningCandidateComponentProvider {

	public Set<BeanDefinition> scan(String basePackage) {
		return findCandidateComponents(basePackage);
	}
}
