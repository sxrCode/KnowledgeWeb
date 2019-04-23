package com.sxr.knowledge.bean;

import org.springframework.beans.factory.FactoryBean;

@MyComponent
public class ItemFactoryBean implements FactoryBean<MyItem> {

	@Override
	public MyItem getObject() throws Exception {
		String item = String.valueOf(Math.random() * 10000);
		return new MyItem(item);
	}

	@Override
	public Class<?> getObjectType() {
		return MyItem.class;
	}

	@Override
	public boolean isSingleton() {

		return false;
	}

}
