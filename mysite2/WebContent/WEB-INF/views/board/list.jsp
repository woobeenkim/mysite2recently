<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import = "com.javaex.dao.BoardDao" %>
<%@ page import = "com.javaex.vo.BoardVo" %>
<%@ page import = "com.javaex.vo.UserVo" %>
<%@ page import = "java.util.List" %>

<% 
UserVo auuv = (UserVo)session.getAttribute("authUser");
List<BoardVo> BL = (List<BoardVo>)request.getAttribute("bl"); 

%>


<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<link href="/mysite2/assets/css/mysite.css" rel="stylesheet" type="text/css">
<link href="/mysite2/assets/css/board.css" rel="stylesheet" type="text/css">

</head>


<body>
	<div id="wrap">

		<jsp:include page="/WEB-INF/views/include/header.jsp"></jsp:include>
		<!-- //header -->
		
		<jsp:include page="/WEB-INF/views/include/navform.jsp"></jsp:include>
		<!-- //nav -->

		<jsp:include page="/WEB-INF/views/include/asideboard.jsp"></jsp:include>
		<!-- //aside -->

		<div id="content">

			<div id="content-head">
				<h3>게시판</h3>
				<div id="location">
					<ul>
						<li>홈</li>
						<li>게시판</li>
						<li class="last">일반게시판</li>
					</ul>
				</div>
				<div class="clear"></div>
			</div>
			<!-- //content-head -->

			<div id="board">
				<div id="list">
					<form action="/mysite2/bc" method="get">
						<div class="form-group text-right">
							<input type="text">
							<button type="submit" id=btn_search>검색</button>
						</div>
					</form>
				
					<table >
						<thead>
							<tr>
								<th>번호</th>
								<th>제목</th>
								<th>글쓴이</th>
								<th>조회수</th>
								<th>작성일</th>
								<%if(auuv==null) {%>

								<%}else{ %>
									<th>관리</th>
								<%} %>
								
							</tr>
						</thead>
						<%for(BoardVo bv : BL) { %>
						<tbody>
							<tr>
								<td><%=bv.getNo() %></td>
								<td class="text-left"><a href="/mysite2/Board?action=read&no=<%=bv.getNo()%>&hit=<%=bv.getHit()%>"><%=bv.getTitle() %></a></td>
								<td><%=bv.getName() %></td>
								<td><%=bv.getHit() %></td>
								<td><%=bv.getReg_date() %></td>
								<%if(auuv==null) {%>
								<%}else if(auuv.getNo()==bv.getUser_no()){ %>
								<td><a href="/mysite2/Board?action=delete&no=<%=bv.getNo()%>" >[삭제]</a></td>
								<%} %>
							</tr>
						
						</tbody>
						<%} %>
					</table>
				
		
					<div id="paging">
						<ul>
							<li><a href="">◀</a></li>
							<li><a href="">1</a></li>
							<li><a href="">2</a></li>
							<li><a href="">3</a></li>
							<li><a href="">4</a></li>
							<li class="active"><a href="">5</a></li>
							<li><a href="">6</a></li>
							<li><a href="">7</a></li>
							<li><a href="">8</a></li>
							<li><a href="">9</a></li>
							<li><a href="">10</a></li>
							<li><a href="">▶</a></li>
						</ul>
						
						
						<div class="clear"></div>
					</div>
					<%if(auuv==null) {%>

				<%}else{ %>
					<a id="btn_write" href="/mysite2/Board?action=wform">글쓰기</a>
				<%} %>
				</div>
				<!-- //list -->
			</div>
			<!-- //board -->
		</div>
		<!-- //content  -->
		<div class="clear"></div>

		<jsp:include page="/WEB-INF/views/include/footer.jsp"></jsp:include>
		<!-- //footer -->
	</div>
	<!-- //wrap -->

</body>

</html>
