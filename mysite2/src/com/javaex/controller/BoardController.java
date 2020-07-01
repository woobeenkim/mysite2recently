package com.javaex.controller;

import java.io.IOException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.javaex.dao.BoardDao;
import com.javaex.dao.UserDao;
import com.javaex.vo.BoardVo;
import com.javaex.vo.UserVo;


@WebServlet("/Board")
public class BoardController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
   
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		
		request.setCharacterEncoding("UTF-8");
		String action = request.getParameter("action");
		if("read".equals(action)) {
			
			BoardDao bd = new BoardDao();
			int no = Integer.parseInt(request.getParameter("no"));
			BoardVo BV = bd.getContent(no);
			request.setAttribute("bv", BV);
			int hit = Integer.parseInt(request.getParameter("hit"));
			hit += 1;
			BoardVo bv = new BoardVo();
			bv.setHit(hit);
			bv.setNo(no);
			bd.phit(bv);
			RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/views/board/read.jsp");
			rd.forward(request, response);
		}
		else if("wform".equals(action)) {
			
			RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/views/board/writeForm.jsp");
			rd.forward(request, response);

		}
		else if("add".equals(action)) {
			HttpSession session = request.getSession();
			UserVo auuv = (UserVo)session.getAttribute("authUser");
			String name = auuv.getName();
			int user_no = auuv.getNo();
			String title = request.getParameter("title");
			String content = request.getParameter("content");
			BoardDao bd = new BoardDao();
			BoardVo bv = new BoardVo(user_no, title, content, name);
			bd.insert(bv);
			System.out.println(bv);
			response.sendRedirect("/mysite2/board");
		}
		else if("delete".equals(action)) {
			BoardDao bd = new BoardDao();
			int no = Integer.parseInt(request.getParameter("no"));
			BoardVo BV = bd.getContent(no);
			request.setAttribute("bv", BV);
			bd.Delete(no);
			response.sendRedirect("/mysite2/board");
		}
		else if("mform".equals(action)) {
			BoardDao bd = new BoardDao();
			int no = Integer.parseInt(request.getParameter("no"));
			BoardVo BV = bd.getContent(no);
			request.setAttribute("bv", BV);
			RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/views/board/modifyForm.jsp");
			rd.forward(request, response);
		}
		else if("modify".equals(action)) {
			int no = Integer.parseInt(request.getParameter("no"));
			int hit = Integer.parseInt(request.getParameter("hit"));
			String title = request.getParameter("title");
			String content = request.getParameter("content");
			BoardDao bd = new BoardDao();
			BoardVo bv = new BoardVo(no,hit,title,content);
			bd.modify(bv);
			response.sendRedirect("/mysite2/board");
			
		}
	}


	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

	}

}
