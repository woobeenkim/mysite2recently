package com.javaex.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.javaex.dao.GuestbookDao;
import com.javaex.vo.PersonVo;


@WebServlet("/gbc")
public class GuestBookController extends HttpServlet {
	private static final long serialVersionUID = 1L;


	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String action = request.getParameter("action");
		
		if("list".equals(action)) {
			GuestbookDao gd = new GuestbookDao();
			List<PersonVo> PL = gd.getPersonList();
			request.setAttribute("pl", PL);
			
			RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/views/guestbook/Addlist.jsp");
			rd.forward(request, response);
		}
		else if("add".equals(action)) {
			
			String name = request.getParameter("name");
			String pass = request.getParameter("pass");
			String content = request.getParameter("content");
			String reg_date = request.getParameter("reg_date");
			GuestbookDao gd = new GuestbookDao();
			PersonVo pv  = new PersonVo(name,pass,content,reg_date);
			gd.personInsert(pv);
			response.sendRedirect("/mysite2/gbc?action=list");
		}
		else if("dform".equals(action)) {
			RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/views/guestbook/DeleteForm.jsp");
			rd.forward(request, response);
		}
		else if("delete".equals(action)) {
			int no = Integer.parseInt(request.getParameter("no"));
			String pass = request.getParameter("pass");
			GuestbookDao gd = new GuestbookDao();
			gd.personDelete(no, pass);
			response.sendRedirect("/mysite2/gbc?action=list");
		}
		else if("uform".equals(action)) {
			RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/views/guestbook/UpdateForm.jsp");
			rd.forward(request, response);
		}
		else if("update".equals(action)) {
			int no = Integer.parseInt(request.getParameter("no"));
			String name = request.getParameter("name");
			String pass = request.getParameter("pass");
			String content = request.getParameter("content");
			String reg_date = request.getParameter("reg_date");
			GuestbookDao gd = new GuestbookDao();
			PersonVo pv  = new PersonVo(no,name,pass,content,reg_date);
			gd.personUpdate(pv);
			response.sendRedirect("/mysite/gbc?action=list");
		}
	}

	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		
	}

}
