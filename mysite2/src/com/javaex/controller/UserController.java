package com.javaex.controller;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.javaex.dao.UserDao;
import com.javaex.vo.UserVo;


@WebServlet("/user")
public class UserController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       


	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		request.setCharacterEncoding("UTF-8");
		String action = request.getParameter("action");
		
		
		if("jform".equals(action)) {
			System.out.println("joinform");
			RequestDispatcher rd = request.getRequestDispatcher("WEB-INF/views/user/JoinForm.jsp");
			rd.forward(request, response);
		}
	
		
		else if("join".equals(action)) {
			
		String id = request.getParameter("id");
		String password = request.getParameter("password");
		String name = request.getParameter("name");
		String gender = request.getParameter("gender");
		UserDao ub = new UserDao();
		UserVo uv = new UserVo(id,password,name,gender);
		ub.insert(uv);
		
		System.out.println(uv.toString());
		RequestDispatcher rd = request.getRequestDispatcher("WEB-INF/views/user/joinOk.jsp");
		rd.forward(request, response);
		}
		
		else if("lform".equals(action)) {
			RequestDispatcher rd = request.getRequestDispatcher("WEB-INF/views/user/loginForm.jsp");
			rd.forward(request, response);
		}
		else if("login".equals(action)) {
			System.out.println("login");
			String id = request.getParameter("id");
			String password = request.getParameter("password");
			
			UserDao ud = new UserDao();
			UserVo uv = ud.getUser(id, password);
			UserVo uv1 = ud.getpass(id);
			System.out.println(uv1.getPassword()+","+password);
			if(uv==null) {
			//성공시 세션영역에 필요값 대입하기.
				System.out.println("로그인실패.");
				response.sendRedirect("/mysite2/user?action=lform&result=fail");
				
			}
			else if(uv1.getPassword().equals(password)) {
				HttpSession session =  request.getSession();
				session.setAttribute("authUser", uv);
				
				response.sendRedirect("/mysite2/main");
			}
			else {
				request.setAttribute("fail", "result");
				System.out.println("id와 password가 일치하지 않습니다.");
				
				
				response.sendRedirect("/mysite2/user?action=lform&result=fail");
	
			}
		}
		else if("mform".equals(action)){
			HttpSession session = request.getSession();
		//	UserVo uv = (UserVo)session.getAttribute("authUser");
			int no = ((UserVo)session.getAttribute("authUser")).getNo();
			UserDao ud = new UserDao();
			UserVo uv = ud.getUser(no);
			System.out.println(uv);
			request.setAttribute("userVo", uv);
			RequestDispatcher rd = request.getRequestDispatcher("WEB-INF/views/user/modifyForm.jsp");
			rd.forward(request, response);
			
		}
		else if("modify".equals(action)) {
			HttpSession session = request.getSession();
			String password = request.getParameter("password");
			String name = request.getParameter("name");
			String id = request.getParameter("id");
			String gender = request.getParameter("gender");
			UserDao ub = new UserDao();
			UserVo uv = new UserVo(id,password,name,gender);
			ub.modify(uv);
			//정보 수정시 500번 에러 나면 다오에서 modify관련 변수에서 사용하지않는 no을 뺴주기 여기서도 같이.
			
			
			UserVo vo = (UserVo)session.getAttribute("authUser");
			vo.setName(name);
			//수정시 홈페이지 내에서의 이름도 수정한 이름으로 바꿔주어야 하기떄문에 세션의 이름을 수정해준다.
			response.sendRedirect("/mysite2/main");
		}
		
		else if("logout".equals(action)) {
			HttpSession session = request.getSession();
			session.removeAttribute("authUser");
			session.invalidate();

			response.sendRedirect("/mysite2/main");
		}
		
		
	}


	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	
	}

}
