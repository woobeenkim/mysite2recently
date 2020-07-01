package com.javaex.utill;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class WebUtill {
	
	//forward, redirect의 사용생김새가 다르기 떄문에 비슷한형식으로 묵어 한번에 사용하기위한 메소드

	//포워드
	public void forward(HttpServletRequest request, HttpServletResponse response, String path)
	throws ServletException, IOException{
		
	RequestDispatcher rd = request.getRequestDispatcher(path);
	rd.forward(request, response);
	}
	//리다이렉트
	public void redirect(HttpServletRequest request, HttpServletResponse response, String url)
	throws IOException{
				
	response.sendRedirect(url);
	
	}
}
