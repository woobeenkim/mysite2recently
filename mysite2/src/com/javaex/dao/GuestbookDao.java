package com.javaex.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.javaex.vo.PersonVo;

public class GuestbookDao {
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

	public void close() {
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
	
	public int personInsert(PersonVo personVo) {
		int count = 0;
		getConnection();

		try {

			// 3. SQL문 준비 / 바인딩 / 실행
			String query = ""; // 쿼리문 문자열만들기, ? 주의
			query += " INSERT INTO guestbook ";
			query += " VALUES (seq_guestbook_no.nextval, ?, ?, ?, sysdate) ";
			// System.out.println(query);

			pstmt = conn.prepareStatement(query); // 쿼리로 만들기

			pstmt.setString(1, personVo.getName()); 
			pstmt.setString(2, personVo.getPassword());
			pstmt.setString(3, personVo.getContent());

			count = pstmt.executeUpdate(); // 쿼리문 실행


		} catch (SQLException e) {
			System.out.println("error:" + e);
		}
		close();
		return count;
	}
	


	// 사람 리스트(검색할때)
	public List<PersonVo> getPersonList() {
		List<PersonVo> personList = new ArrayList<PersonVo>();

		getConnection();

		try {

			// 3. SQL문 준비 / 바인딩 / 실행 --> 완성된 sql문을 가져와서 작성할것
			String query = "";
			query += " select  g.no, ";
			query += "         g.name, ";
			query += "         g.password, ";
			query += "         g.content, ";
			query += "         g.reg_date ";
			query += " from guestbook g ";
			pstmt = conn.prepareStatement(query); // 쿼리로 만들기
			

			rs = pstmt.executeQuery();

			// 4.결과처리
			while (rs.next()) {
				int no = rs.getInt("no");
				String name = rs.getString("name");
				String password = rs.getString("password");
				String content = rs.getString("content");
				String reg_date = rs.getString("reg_Date");

				PersonVo personVo = new PersonVo(no, name, password, content, reg_date);
				personList.add(personVo);
			}

		} catch (SQLException e) {
			System.out.println("error:" + e);
		}

		close();

		return personList;

	}
	public int personDelete(int no, String password) {
		int count = 0;
		getConnection();

		try {
			// 3. SQL문 준비 / 바인딩 / 실행
			String query = ""; // 쿼리문 문자열만들기, ? 주의
			query += " delete from guestbook ";
			query += " where no = ? ";
			query += " and	 password = ? ";
			pstmt = conn.prepareStatement(query); // 쿼리로 만들기


			pstmt.setInt(1, no);// ?(물음표) 중 1번째, 순서중요
			pstmt.setString(2, password);// ?(물음표) 중 1번째, 순서중요

			count = pstmt.executeUpdate(); // 쿼리문 실행

			// 4.결과처리
			// System.out.println(count + "건 삭제되었습니다.");

		} catch (SQLException e) {
			System.out.println("error:" + e);
		}

		close();
		return count;
	
	}
	
	public int personUpdate(PersonVo personVo) {
		int count = 0;
		getConnection();

		try {

			// 3. SQL문 준비 / 바인딩 / 실행
			String query = ""; // 쿼리문 문자열만들기, ? 주의
			query += " update guestbook ";
			query += " set    name = ? , ";
			query += "    	  password = ?, ";
			query += "    	  content = ?, ";
			query += "    	  reg_date = ? ";
			query += " where  no = ? ";

			pstmt = conn.prepareStatement(query); // 쿼리로 만들기

			pstmt.setString(1, personVo.getName()); // ?(물음표) 중 1번째, 순서중요
			pstmt.setString(2, personVo.getPassword()); // ?(물음표) 중 2번째, 순서중요
			pstmt.setString(3, personVo.getContent()); // ?(물음표) 중 3번째, 순서중요
			pstmt.setString(4, personVo.getReg_date()); // ?(물음표) 중 4번째, 순서중요
			pstmt.setInt(5, personVo.getNo());

			count = pstmt.executeUpdate(); // 쿼리문 실행

			// 4.결과처리
			// System.out.println(count + "건 수정되었습니다.");

		} catch (SQLException e) {
			System.out.println("error:" + e);
		}

		close();
		return count;
	}
	public PersonVo getPerson(int no) {
		/*
		 UpdateForm 만들시 500번 에러확인 되면, 다시 와서 쿼리 확인하기.
		 확인 되었으면 인터넷 창으로 수정버튼 누른 후에 이클립스 콘솔창에 테스트문 프린트 되는지 확인.
		 */
		PersonVo personVo = null;
		getConnection();

		try {
			
			String query = "";
			query += " select  no, ";
			query += "   	   name, ";
			query += "   	   password, ";
			query += "   	   content, ";
			query += "   	   reg_date ";
			query += " from guestbook ";
			query += " where no = ? ";
			
			pstmt = conn.prepareStatement(query);
			pstmt.setInt(1, no);
			rs = pstmt.executeQuery();
			
			while (rs.next()) {
				int n = rs.getInt("no");
				String name = rs.getString("name");
				String password = rs.getString("password");
				String content = rs.getString("content");
				String reg_date = rs.getString("reg_date");

				personVo = new PersonVo(no, name, password, content, reg_date);
			}

		} catch (SQLException e) {
			System.out.println("error:" + e);
		}

		close();
		return personVo;
	}

}
