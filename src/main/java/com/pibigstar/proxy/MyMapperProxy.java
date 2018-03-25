package com.pibigstar.proxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.util.List;

import com.pibigstar.config.Function;
import com.pibigstar.config.MapperBean;
import com.pibigstar.session.Excutor;
import com.pibigstar.session.MyConfiguration;
import com.pibigstar.session.MySqlSession;

/**
 * 代理角色处理器
 * MyMapperProxy代理类完成xml方法和真实方法对应，执行查询：
 * @author pibigstar
 *
 */
public class MyMapperProxy implements InvocationHandler{
	
	private MySqlSession sqlSession;
	private MyConfiguration myConfiguration;

	public MyMapperProxy(MySqlSession sqlSession, MyConfiguration myConfiguration) {
		this.sqlSession = sqlSession;
		this.myConfiguration = myConfiguration;
	}

	@Override
	public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
		MapperBean mapperBean = myConfiguration.readMapper("UserMapper.xml");
		
		if (!mapperBean.getInterfaceName().equals(method.getDeclaringClass().getName())) {
			return null;
		}
		
		List<Function> functions = mapperBean.getFunctions();
		for (Function function : functions) {
			System.out.println(method.getName());
			if (function.getFunName().equals(method.getName())) {
				 return sqlSession.selectOne(function.getSql(), String.valueOf(args[0]));
			}
		}
		return null;
	}

}
