package com.sxr.knowledge;

import java.util.Map.Entry;

import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

import com.sxr.knowledge.bean.BeanRepository;
import com.sxr.knowledge.bean.MyItem;

@Component
public class AppListener implements ApplicationListener<ApplicationReadyEvent> {

	@Override
	public void onApplicationEvent(ApplicationReadyEvent event) {
		System.out.println(event.getApplicationContext().getApplicationName() + " Ready");

		for (Entry<String, Object> entry : BeanRepository.repository.entrySet()) {
			System.out.println(entry.getKey());
		}

		MyItem item = (MyItem) event.getApplicationContext().getBean("itemFactoryBean");
		System.out.println(item.getItem());

	}

}
