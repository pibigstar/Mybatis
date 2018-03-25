package com.pibigstar.config;

/**
 *  <select id="getUserById" resultType ="com.liugh.bean.User">
        select * from user where id = ?
    </select>
 * @author pibigstar
 *
 */

public class Function {
	private String funName;
	private String sql;
	private Object resultType;
	private String paramaterType;
	public String getFunName() {
		return funName;
	}
	public void setFunName(String funName) {
		this.funName = funName;
	}
	public String getSql() {
		return sql;
	}
	public void setSql(String sql) {
		this.sql = sql;
	}
	public Object getResultType() {
		return resultType;
	}
	public void setResultType(Object resultType) {
		this.resultType = resultType;
	}
	public String getParamaterType() {
		return paramaterType;
	}
	public void setParamaterType(String paramaterType) {
		this.paramaterType = paramaterType;
	} 
	
}
