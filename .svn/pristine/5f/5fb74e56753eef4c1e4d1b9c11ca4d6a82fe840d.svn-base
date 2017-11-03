package com.sesame.api;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

/**
 * 项目里的配置
 * 
 * @author johnny
 * @date 2017-06-10 09:13
 * @classpath com.sesame.api
 * @Description:
 */
@Component
@PropertySource("classpath:application.properties")
public class Config {

	/** 资源在服务器里的磁盘路径 */
	public static String path;
	/** 资源用http访问的路径 */
	public static String mapping;

	@Value("${app.file}")
	public void setPath(String path) {
		Config.path = path;
	}

	@Value("${app.resource}")
	public void setMapping(String mapping) {
		Config.mapping = mapping;
	}
}