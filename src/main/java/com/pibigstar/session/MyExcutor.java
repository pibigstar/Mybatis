package com.pibigstar.session;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.pibigstar.bean.User;

/**
 * MyExcutor中封装了JDBC的操作：
 * @author pibigstar
 *
 */
public class MyExcutor implements Excutor{

	private MyConfiguration config = new MyConfiguration();

	@Override
	public <T> T query(String sql, Object paramater) {
		Connection connection = getConnection();
		ResultSet rs = null;
		PreparedStatement ps = null;

		try {
			ps = connection.prepareStatement(sql);

			ps.setString(1, paramater.toString());
			rs = ps.executeQuery();
			User user = new User();
			while (rs.next()) {
				user.setId(rs.getString("id"));
				user.setUsername(rs.getString("username"));
				user.setPassword(rs.getString("password"));
			}
			return (T)user;

		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			try {
				if (rs!=null) {
					rs.close();
				}
				if (ps!=null) {
					ps.close();
				}
				if (connection!=null) {
					connection.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}

		}

		return null;
	}

	private Connection getConnection() {
		Connection connection = config.bulid("config.xml");
		return connection;
	}

}
