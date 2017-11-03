package com.sesame;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.context.ConfigurableApplicationContext;

import com.sesame.framework.web.util.SpringContextUtil;

import java.net.URL;

/**
 * @author johnny
 * @date 2017-06-10 12:41
 * @classpath com.sesame
 * @Description:@ServletComponentScan : 监听器
 */
@ServletComponentScan
@SpringBootApplication
public class SsoApp {

	public static void main(String[] arr) {
		
		ConfigurableApplicationContext context = SpringApplication.run(SsoApp.class, arr);

		SpringContextUtil.setApplicationContext(context);
		SpringContextUtil.printlnInfo();

	}

	public static boolean isJar(){
		URL resource = SsoApp.class.getResource("AppIM.class");
		return  resource.toString().contains("jar:file");
	}
}