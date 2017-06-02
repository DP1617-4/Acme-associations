
package com;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.resource.DefaultServletHttpRequestHandler;
import org.springframework.web.servlet.view.InternalResourceViewResolver;
import org.springframework.web.servlet.view.JstlView;

@EnableWebMvc
@Configuration
@ComponentScan({
	"com"
})
public class SpringWebMvcConfig extends WebMvcConfigurerAdapter {

	// Bean name must be "multipartResolver", by default Spring uses method name as bean name.
	//	@Bean
	//	public MultipartResolver multipartResolver() {
	//		return new StandardServletMultipartResolver();
	//	}

	/*
	 * // if the method name is different, you must define the bean name manually like this :
	 * 
	 * @Bean(name = "multipartResolver")
	 * public MultipartResolver createMultipartResolver() {
	 * return new StandardServletMultipartResolver();
	 * }
	 */

	@Bean
	public CommonsMultipartResolver multipartResolver() {
		final CommonsMultipartResolver resolver = new CommonsMultipartResolver();
		resolver.setDefaultEncoding("utf-8");
		return resolver;
	}

	@Bean
	public InternalResourceViewResolver viewResolver() {
		final InternalResourceViewResolver viewResolver = new InternalResourceViewResolver();
		viewResolver.setViewClass(JstlView.class);
		viewResolver.setPrefix("/WEB-INF/views/jsp/");
		viewResolver.setSuffix(".jsp");
		return viewResolver;
	}

	@Bean
	public ViewResolver createInternalResourceViewResolver() {
		final InternalResourceViewResolver viewResolver = new InternalResourceViewResolver();
		viewResolver.setViewClass(JstlView.class);
		viewResolver.setContentType("text/html");
		viewResolver.setPrefix("/view/");
		//viewResolver.setSuffix(".jsp");
		return viewResolver;
	}

	@Bean
	public DefaultServletHttpRequestHandler createDefaultServletHttpRequestHandler() {
		return new DefaultServletHttpRequestHandler();
	}

}
