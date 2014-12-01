package Entities;

import java.io.Serializable;

public class ProfileObject implements Serializable {
	private static final long serialVersionUID = -3991119953016021875L;
	public String username, password;
	public ProfileObject(String username, String password) {
		this.username = username;
		this.password = password;
	}
}
