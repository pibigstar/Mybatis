package com.pibigstar.session;

import java.lang.reflect.Proxy;

import com.pibigstar.proxy.MyMapperProxy;

/**
 * 接下来实现我们的MySqlSession,
 * 首先的成员变量里得有Excutor和MyConfiguration，代码的精髓就在getMapper的方法里。
 * @author pibigstar
 *
 */
public class MySqlSession {
	
	private Excutor excutor = new MyExcutor();
	
	private MyConfiguration myConfiguration = new MyConfiguration();
	
	public <T> T selectOne(String sql,String paramater) {
		return excutor.query(sql, paramater);
	}
	
	/**
	 * 调用代理
	 * @param clas
	 * @return
	 */
	public <T> T getMapper(Class clas) {
		
		return (T)Proxy.newProxyInstance(clas.getClassLoader(), new Class[] {clas}, new MyMapperProxy(this, myConfiguration));
		
	}
	
	

}
