package com.javaex.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.javaex.vo.BoardVo;

public class BoardDao {
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
	public List<BoardVo> getBoardList() {
		List<BoardVo> BL = new ArrayList<BoardVo>();

		getConnection();

		try {

			// 3. SQL문 준비 / 바인딩 / 실행 --> 완성된 sql문을 가져와서 작성할것
			String query = "";
			query += " select  b.user_no, ";
			query += "         b.no, ";
			query += "         b.title, ";
			query += "         u.name, ";
			query += "         b.hit, ";
			query += "         b.content, ";
			query += "         b.reg_date ";
			query += " from board b, users u ";
			query += " where u.no = b.user_no ";
			
			pstmt = conn.prepareStatement(query); // 쿼리로 만들기
			

			rs = pstmt.executeQuery();

			// 4.결과처리
			while (rs.next()) {
				int no = rs.getInt("no");
				int user_no = rs.getInt("user_no");
				String title = rs.getString("title");
				int hit = rs.getInt("hit");
				String reg_date = rs.getString("reg_date");
				String content = rs.getString("content");
				String name = rs.getString("name");

				BoardVo bv = new BoardVo(no, user_no, title, hit, reg_date, content, name);
				System.out.println(bv);
				BL.add(bv);
				
			}

		} catch (SQLException e) {
			System.out.println("error:" + e);
		}

		close();

		return BL;

	}
	public int insert(BoardVo bv) {
		int count=0;
		getConnection();
		try {
			String query = ""; // 쿼리문 문자열만들기, ? 주의
			query += " INSERT INTO board ";
			query += " VALUES (seq_board_no.nextval, ?, ?, 0, sysdate, ?) ";
			
			pstmt = conn.prepareStatement(query);

			pstmt.setString(1, bv.getTitle());
			pstmt.setString(2, bv.getContent());
			pstmt.setInt(3, bv.getUser_no());
			
			
			count = pstmt.executeUpdate(); // 쿼리문 실행
		}catch(SQLException e) {
			System.out.println("error:" + e);
		}
		
		
		close();
		return count;
	}
	
	
	public BoardVo getContent(int no){
		getConnection();
		BoardVo bv = new BoardVo();
		
		try {
			
		String query="";
		query += " select  u.name, ";
		query += "   	   b.no, ";
		query += "   	   b.user_no, ";
		query += "   	   b.hit, ";
		query += "  	   b.reg_date, ";
		query += "   	   b.title, ";
		query += "   	   b.content ";
		query += " from    users u, board b ";
		query += " where   b.no = ? ";
		query += " and     u.no = b.user_no  ";
		
		
		pstmt = conn.prepareStatement(query); // 쿼리로 만들기
		
		pstmt.setInt(1, no);
		

		rs = pstmt.executeQuery();
		
		while (rs.next()) {
			String name = rs.getString("name");
			int hit = rs.getInt("hit");
			int user_no = rs.getInt("user_no");
			String reg_date = rs.getString("reg_date");
			String title = rs.getString("title");
			String content = rs.getString("content");
			bv.setName(name);
			bv.setUser_no(user_no);
			bv.setNo(no);
			bv.setHit(hit);
			bv.setReg_date(reg_date);
			bv.setTitle(title);
			bv.setContent(content);
			System.out.println(bv);
			//uv.setId(id);
		}
		
		}catch(SQLException e)
		{
			System.out.println("error:" + e);
		}
		
		close();
		return bv;
	}
	public int Delete(int no) {
		int count = 0;
		getConnection();

		try {
			
			String query = ""; 
			query += " delete from board ";
			query += " where no = ? ";
			
			pstmt = conn.prepareStatement(query);


			pstmt.setInt(1, no);
			
			count = pstmt.executeUpdate();


		} catch (SQLException e) {
			System.out.println("error:" + e);
		}

		close();
		return count;
	
	}

	
	public int modify(BoardVo bv) {
		int count = 0;
		getConnection();

		try {

			
			String query = ""; 
			query += " update board ";
			query += " set 	  title = ?, ";
			query += " 	  	  content = ?, ";
			query += " 	  	  hit = ? ";
			query += " where  no = ? ";

			pstmt = conn.prepareStatement(query); 
			pstmt.setString(1, bv.getTitle()); 
			pstmt.setString(2, bv.getContent()); 
			pstmt.setInt(3, bv.getHit()); 
			pstmt.setInt(4, bv.getNo()); 
			
			System.out.println(bv);
			
			count = pstmt.executeUpdate(); 
			
		

		} catch (SQLException e) {
			System.out.println("error:" + e);
		}

		close();
		return count;
	}	
	
	public int phit(BoardVo bv) {
		int count = 0;
		getConnection();

		try {

			
			String query = ""; 
			query += " update board ";
			query += " set 	  hit = ? ";
			query += " where  no = ? ";

			pstmt = conn.prepareStatement(query); 
			pstmt.setInt(1, bv.getHit()); 
			pstmt.setInt(2, bv.getNo()); 
			
			System.out.println(bv);
			
			count = pstmt.executeUpdate(); 
			
		

		} catch (SQLException e) {
			System.out.println("error:" + e);
		}

		close();
		return count;
	}
	
	
}
