package com.pibigstar.session;

/**
 * 查询接口
 * @author pibigstar
 *
 */
public interface Excutor {
	
	public <T> T query(String sql,Object paramater);

}
