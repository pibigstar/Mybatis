package com.pibigstar.config;

import java.util.List;

/**
 * <mapper nameSpace="com.liugh.mapper.UserMapper">
    	<select id="getUserById" resultType ="com.liugh.bean.User">
        	select * from user where id = ?
    	</select>
 * </mapper>
 * @author pibigstar
 *
 */

public class MapperBean {
	private String interfaceName;//接口的名字  com.liugh.mapper.UserMapper
	private List<Function> functions;//接口下面的所以方法 getUserById
	public String getInterfaceName() {
		return interfaceName;
	}
	public void setInterfaceName(String interfaceName) {
		this.interfaceName = interfaceName;
	}
	public List<Function> getFunctions() {
		return functions;
	}
	public void setFunctions(List<Function> functions) {
		this.functions = functions;
	}
	
	
}
