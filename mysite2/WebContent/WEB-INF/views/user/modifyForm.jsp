<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import = "com.javaex.dao.UserDao"  %>
<%@ page import = "com.javaex.vo.UserVo" %>
<%
	UserVo uv = (UserVo)request.getAttribute("userVo");

	//리스트에서 선택한 해당인원의 정보중 기본키 no을가져와서 그에 해당하는 모든 정보를 담아서 Controller에 보낸다.
	//Controller에서는 받아온 정보를 재입력하여 정보를 수정한다.
	//웹실행시 500에러가 나면 보내는 파라미터가 전부 잘 들어갔나 확인.
%>
<!DOCTYPE html>
		
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<link href="/mysite2/assets/css/mysite.css" rel="stylesheet" type="text/css">
<link href="/mysite2/assets/css/user.css" rel="stylesheet" type="text/css">
</head>
<body>
	
		<!-- //header -->
		<div id="wrap">

	
		<jsp:include page="/WEB-INF/views/include/header.jsp"></jsp:include>
		</div>
<!-- //header -->
		<jsp:include page="/WEB-INF/views/include/navform.jsp"></jsp:include>
		<!-- //nav -->

		<jsp:include page="/WEB-INF/views/include/asideuser.jsp"></jsp:include>
		<!-- //aside -->

		<div id="content">
			
			<div id="content-head">
            	<h3>회원정보</h3>
            	<div id="location">
            		<ul>
            			<li>홈</li>
            			<li>회원</li>
            			<li class="last">회원정보</li>
            		</ul>
            	</div>
                <div class="clear"></div>
            </div>
             <!-- //content-head -->

			<div id="user">
				<div id="modifyForm">
					<form action="/mysite2/user" method="get">

						<!-- 아이디 -->
						<div class="form-group">
							<label class="form-text" for="input-uid">아이디</label> 
							<span class="text-large bold"><%=uv.getId()%></span>
							<input type="hidden" name = "id" value = "<%=uv.getId()%>">
						</div>

						<!-- 비밀번호 -->
						<div class="form-group">
							<label class="form-text" for="input-pass">패스워드</label> 
							<input type="text" id="input-pass" name="password" value="" placeholder="비밀번호를 입력하세요"	>
						</div>

						<!-- 이메일 -->
						<div class="form-group">
							<label class="form-text" for="input-name">이름</label> 
							<input type="text" id="input-name" name="name" value="" placeholder="이름을 입력하세요">
						</div>

						<!-- //나이 -->
						<div class="form-group">
							<span class="form-text">성별</span> 
							<%if(("남").equals(uv.getGender())){ %>
							<label for="rdo-male">남</label> 
							<input type="radio" id="rdo-male" name="gender" value="남" checked = "checked"> 
							<label for="rdo-female">여</label> 
							<input type="radio" id="rdo-female" name="gender" value="여"> 
							<%}else{ %>
								<label for="rdo-male">남</label> 
							<input type="radio" id="rdo-male" name="gender" value="남"> 
							<label for="rdo-female">여</label> 
							<input type="radio" id="rdo-female" name="gender" value="여" checked = "checked"> 
							<%} %>
						</div>

						<!-- 버튼영역 -->
		                <div class="button-area">
		                    <button type="submit" id="btn-submit">회원정보수정</button>
		                </div>
						<input type="hidden" name ="action" value="modify">
					</form>
				
				
				</div>
				<!-- //modifyForm -->
			</div>
			<!-- //user -->
		</div>
		<!-- //content  -->
		<div class="clear"></div>
		
	<jsp:include page="/WEB-INF/views/include/footer.jsp"></jsp:include>
		<!-- //footer -->
		
	<!-- //wrap -->

</body>

</html>