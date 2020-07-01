package com.javaex.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import com.javaex.vo.UserVo;

public class UserDao {
	private Connection conn = null;
	private PreparedStatement pstmt = null;
	private ResultSet rs = null;
	
	private String driver = "oracle.jdbc.driver.OracleDriver";
	private String url = "jdbc:oracle:thin:@localhost:1521:xe";
	private String id = "webdb";
	private String pw = "webdb";
	
	private void getConnection() {
		try {
			// 1. JDBC 드라이버 (Oracle) 로딩
			Class.forName(driver);

			// 2. Connection 얻어오기
			conn = DriverManager.getConnection(url, id, pw);
			// System.out.println("접속성공");

		} catch (ClassNotFoundException e) {
			System.out.println("error: 드라이버 로딩 실패 - " + e);
		} catch (SQLException e) {
			System.out.println("error:" + e);
		}
	}
	
	private void close() {
		// 5. 자원정리
		try {
			if (rs != null) {
				rs.close();
			}
			if (pstmt != null) {
				pstmt.close();
			}
			if (conn != null) {
				conn.close();
			}
		} catch (SQLException e) {
			System.out.println("error:" + e);
		}
	}
	
	public int insert(UserVo vo) {
		int count=0;
		getConnection();
		
		try {
			String query = ""; // 쿼리문 문자열만들기, ? 주의
			query += " INSERT INTO users ";
			query += " VALUES (seq_users_no.nextval, ?, ?, ?, ?) ";
			
			pstmt = conn.prepareStatement(query);

			pstmt.setString(1, vo.getId());
			pstmt.setString(2, vo.getPassword());
			pstmt.setString(3, vo.getName());
			pstmt.setString(4, vo.getGender());
			
			count = pstmt.executeUpdate(); // 쿼리문 실행
		}catch(SQLException e) {
			System.out.println("error:" + e);
		}
		
		
		close();
		return count;
	}
	
	
	public UserVo getUser(String id, String password){
		getConnection();
		UserVo uv = new UserVo();
		
		try {
			
		String query="";
		query += " select  no, ";
		query += "         name ";
		query += " from    users ";
		query += " where   id = ? ";
		query += " and     password = ? ";
		
		pstmt = conn.prepareStatement(query); // 쿼리로 만들기
		
		pstmt.setString(1, id);
		pstmt.setString(2, password);
		

		rs = pstmt.executeQuery();
		
		while (rs.next()) {
			
			int no = rs.getInt("no");
			String name = rs.getString("name");
			uv.setNo(no);
			uv.setName(name);
			//uv.setId(id);
		}
		
		}catch(SQLException e)
		{
			System.out.println("error:" + e);
		}
		
		close();
		return uv;
	}
	public UserVo getpass (String id){
		getConnection();
		UserVo uv = new UserVo();
		
		try {
			
		String query="";
		query += " select  password ";
		query += " from    users ";
		query += " where   id = ? ";
		
		pstmt = conn.prepareStatement(query); // 쿼리로 만들기
		
		pstmt.setString(1, id);
		

		rs = pstmt.executeQuery();
		
		while (rs.next()) {
			
	
			String password = rs.getString("password");
			uv.setPassword(password);
			//uv.setId(id);
		}
		
		}catch(SQLException e)
		{
			System.out.println("error:" + e);
		}
		
		close();
		return uv;
	}
	
	public int modify(UserVo uv) {
		int count = 0;
		getConnection();

		try {

			// 3. SQL문 준비 / 바인딩 / 실행
			String query = ""; // 쿼리문 문자열만들기, ? 주의
			query += " update users ";
			query += " set   	  password = ?, ";
			query += "    	  name = ?, ";
			query += "    	  gender = ? ";
			query += " where  id = ? ";

			pstmt = conn.prepareStatement(query); // 쿼리로 만들기
			pstmt.setString(1, uv.getPassword()); // ?(물음표) 중 1번째, 순서중요
			pstmt.setString(2, uv.getName()); // ?(물음표) 중 1번째, 순서중요
			pstmt.setString(3, uv.getGender()); // ?(물음표) 중 1번째, 순서중요
			pstmt.setString(4, uv.getId()); // ?(물음표) 중 1번째, 순서중요
			
	

			count = pstmt.executeUpdate(); // 쿼리문 실행

			// 4.결과처리
			// System.out.println(count + "건 수정되었습니다.");

		} catch (SQLException e) {
			System.out.println("error:" + e);
		}

		close();
		return count;
	}
	
	public UserVo getUser(int no) {
		/*
		 UpdateForm 만들시 500번 에러확인 되면, 다시 와서 쿼리 확인하기.
		 확인 되었으면 인터넷 창으로 수정버튼 누른 후에 이클립스 콘솔창에 테스트문 프린트 되는지 확인.
		 */
		UserVo uv = null;
		getConnection();

		try {
			
			String query = "";
			query += " select  no, ";
			query += "   	   id, ";
			query += "   	   password, ";
			query += "   	   name, ";
			query += "   	   gender ";
			query += " from  users";
			query += " where no = ? ";
			
			pstmt = conn.prepareStatement(query);
			pstmt.setInt(1, no);
			rs = pstmt.executeQuery();
			
			while (rs.next()) {
				int no1 = rs.getInt("no");
				String id = rs.getString("id");
				String password = rs.getString("password");
				String name = rs.getString("name");
				String gender = rs.getString("gender");

				uv = new UserVo(no1, id, password, name, gender);
			}

		} catch (SQLException e) {
			System.out.println("error:" + e);
		}

		close();
		return uv;
	}
	
	
	
	
	
}
