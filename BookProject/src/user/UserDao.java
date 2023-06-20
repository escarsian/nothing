package user;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import book.Dao;

public class UserDao {

	Connection conn;
	PreparedStatement psmt;
	ResultSet rs;
	String sql;

	private void close() {
		try {
			if (conn != null) {
				conn.close();
			}
			if (psmt != null) {
				psmt.close();
			}
			if (rs != null) {
				rs.close();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// login id & pw
	public UserVO login(String id, String pw) {
		UserVO user = new UserVO();
		conn = Dao.getConnect();
		sql = "select * from user_manage where user_id = ? and user_pw = ?";
		try {
			psmt = conn.prepareStatement(sql);
			psmt.setString(1, id);
			psmt.setString(2, pw);

			rs = psmt.executeQuery();
			if (rs.next()) {
				user.setUserId(rs.getString("user_id"));
				user.setUserPw(rs.getString("user_pw"));
				user.setUserName(rs.getString("user_name"));
				user.setUserRole(rs.getString("user_role"));
				return user; //
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close();
		}
		return user;
	}
	// role check.
	public UserVO idCheck(String role) {
		conn = Dao.getConnect();
		sql = "select * from user_manage where user_role = ?";
		try {
			psmt = conn.prepareStatement(sql);
			psmt.setString(1, role);
			rs = psmt.executeQuery();

			if (rs.next()) {
				UserVO user = new UserVO();
				user.setUserRole(rs.getString("user_role"));
				return user;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			close();
		}
		return null;
	}
}
