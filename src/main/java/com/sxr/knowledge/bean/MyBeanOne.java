package com.sxr.knowledge.bean;

import org.springframework.beans.factory.BeanNameAware;
import org.springframework.stereotype.Component;

@Component
public class MyBeanOne implements BeanNameAware {

	private String name;

	@Override
	public void setBeanName(String name) {
		this.name = name;
		BeanRepository.add(name, this);
		System.out.println("add " + name + "to BeanRepository!");
	}
}
