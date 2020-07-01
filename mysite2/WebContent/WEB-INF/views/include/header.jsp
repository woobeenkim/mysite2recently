<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import = "com.javaex.vo.UserVo"%>
<%
	UserVo auuv = (UserVo)session.getAttribute("authUser");
%>
<div id="header">
			<h1><a href="/mysite2/main">MySite</a></h1>
			
	
			 <!-- 로그인 전 or 실패 -->
			 <%if(auuv==null){ %>
			<ul>
				<li><a href="/mysite2/user?action=lform">로그인</a></li>
				<li><a href="/mysite2/user?action=jform">회원가입</a></li>
			</ul>
		
			<%}else { %>
		
			<ul>
				<li><%=auuv.getName() %>님 안녕하세요^^</li>
				<li><a href="/mysite2/user?action=logout">로그아웃</a></li>
				<li><a href="/mysite2/user?action=mform">회원정보수정</a></li>
			</ul>
		
			 <%} %>
		</div>