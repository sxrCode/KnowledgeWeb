package com.sxr.knowledge.bean;

import java.util.Set;

import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.config.BeanDefinitionHolder;
import org.springframework.beans.factory.support.BeanDefinitionReaderUtils;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.GenericBeanDefinition;
import org.springframework.context.ResourceLoaderAware;
import org.springframework.context.annotation.ImportBeanDefinitionRegistrar;
import org.springframework.core.io.ResourceLoader;
import org.springframework.core.type.AnnotationMetadata;
import org.springframework.core.type.filter.AnnotationTypeFilter;

import com.sxr.knowledge.utils.StringUtils;

public class MyImportBeanRegister implements ImportBeanDefinitionRegistrar, ResourceLoaderAware {

	private ResourceLoader resourceLoader;

	@Override
	public void registerBeanDefinitions(AnnotationMetadata importingClassMetadata, BeanDefinitionRegistry registry) {
		MyComponentScanner scanner = new MyComponentScanner();
		scanner.setResourceLoader(resourceLoader);
		scanner.addIncludeFilter(new AnnotationTypeFilter(MyComponent.class));
		Set<BeanDefinition> myComponents = scanner.scan("com.sxr.knowledge.bean");
		for (BeanDefinition beanDefinition : myComponents) {
			String beanClassName = beanDefinition.getBeanClassName();
			String beanName = StringUtils
					.toLowerCaseFirstOne(beanClassName.substring(beanClassName.lastIndexOf(".") + 1));
			GenericBeanDefinition genericBeanDefinition = (GenericBeanDefinition) beanDefinition;
			System.out.println("MyComponent: " + beanName);
			BeanDefinitionHolder definitionHolder = new BeanDefinitionHolder(genericBeanDefinition, beanName);
			BeanDefinitionReaderUtils.registerBeanDefinition(definitionHolder, registry);
		}

	}

	@Override
	public void setResourceLoader(ResourceLoader resourceLoader) {
		this.resourceLoader = resourceLoader;
	}

}
