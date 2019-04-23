package com.sxr.knowledge.bean;

import org.springframework.beans.factory.BeanNameAware;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class MyRow implements BeanNameAware {

	@Autowired
	private MyItem item;

	@Override
	public void setBeanName(String name) {
		System.out.println("MyRow " + item.getItem());
	}
}
