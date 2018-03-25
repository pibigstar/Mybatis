package com.pibigstar.session;

import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.util.ArrayList;
import java.util.List;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import com.pibigstar.config.Function;
import com.pibigstar.config.MapperBean;


/**
 * 读取配置文件，返回数据库连接
 * 读取mapper文件，返回MapperBean
 * @author pibigstar
 *
 */
public class MyConfiguration {
	private static ClassLoader loader = ClassLoader.getSystemClassLoader();

	/**
	 * 读取配置文件
	 * @param resource
	 * @return
	 */
	public Connection bulid(String resource) {
		InputStream inputStream = loader.getResourceAsStream(resource);
		SAXReader reader = new SAXReader();

		try {
			Document document = reader.read(inputStream);
			Element root = document.getRootElement();

			if (!root.getName().equals("database")) {
				throw new RuntimeException("root should be <database>");
			}
			String driverName = null;
			String username = null;
			String password = null;
			String url = null;


			//获得根节点下面的所以标签为property的结点
			List<Element> properties = root.elements("property");
			for (Element element : properties) {
				String name = element.attributeValue("name");
				String value = getValue(element);
				
				if (name==null||value==null) {
					throw new RuntimeException("[database]: <property> should contain name and value");
				}
				
				switch (name) {
				case "driverName":driverName = value;break;
				case "url":url = value;break;
				case "username":username = value;break;
				case "password":password = value;break;
				default:
					throw new RuntimeException("[database]: <property> unknown name"); 
				}
				
			}

			//链接数据库
			Class.forName(driverName);
			Connection connection = DriverManager.getConnection(url, username, password);
			
			return connection;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	//获取property属性的值,如果有value值,则读取 没有设置value,则读取内容
	private  String getValue(Element node) {
		return node.hasContent() ? node.getText() : node.attributeValue("value");
	}
	
	/**
	 * 读取mapper文件，返回MapperBean
	 * @param path
	 * @return
	 * @throws DocumentException 
	 */
	
	public MapperBean readMapper(String path) throws DocumentException {
		MapperBean mapperBean = new MapperBean();
		
		List<Function> functions = new ArrayList<>();
		
		InputStream inputStream = loader.getResourceAsStream(path);
		SAXReader reader = new SAXReader();
		Document document = reader.read(inputStream);
		Element root = document.getRootElement();
		String interfaceName = root.attributeValue("nameSpace").trim();
		mapperBean.setInterfaceName(interfaceName);
		
		@SuppressWarnings("unchecked")
		List<Element> elements = root.elements();
		for (Element element : elements) {
			Function function = new Function();
			
			String funName = element.attributeValue("id").trim();
			String sql = element.getText().trim();
			String paramaterType = element.attributeValue("paramaterType");
			String resultType = element.attributeValue("resultType");
			function.setFunName(funName);
			function.setSql(sql);
			function.setParamaterType(paramaterType);
			Object result = null;
			try {
				result = Class.forName(resultType);
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			}
			function.setResultType(result);
			functions.add(function);
		}
		mapperBean.setFunctions(functions);
		
		
		return mapperBean;
	}

}
