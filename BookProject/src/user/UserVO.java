package user;

import lombok.Data;

@Data

public class UserVO {
	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getUserPw() {
		return userPw;
	}

	public void setUserPw(String userPw) {
		this.userPw = userPw;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getUserRole() {
		return userRole;
	}

	public void setUserRole(String userRole) {
		this.userRole = userRole;
	}

	//필드값
	private String userId;		//ID
	private String userPw;		//비밀번호
	private String userName;	//이름
	private String userRole;	//권한(0:관리자, 1:유저)
	
	//생성자
	public UserVO() {}
	
	public UserVO(String userId, String userPw, String userName, String userRole) {
		this.userId = userId;
		this.userPw = userPw;
		this.userName =userName;
		this.userRole = userRole;
	}



	
}
