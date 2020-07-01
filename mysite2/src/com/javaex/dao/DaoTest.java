package com.javaex.dao;

import com.javaex.vo.UserVo;

public class DaoTest {
	public static void main() {
		
		UserDao ud = new UserDao();
		UserVo uv = new UserVo("armrit","123123","김우진","남");
		ud.insert(uv);
		
	}
}
