package com.sxr.knowledge.bean;

import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.util.StringUtils;

public class BeanRepository {

	public volatile static Map<String, Object> repository = new ConcurrentHashMap<>();

	public static void add(String name, Object bean) {
		if (!StringUtils.isEmpty(name) && bean != null) {
			repository.put(name, bean);
		}
	}

	public static Object get(String name) {
		return repository.get(name);
	}

	public static Set<String> getNames() {
		System.out.println(repository.size());
		return repository.keySet();
	}
}
