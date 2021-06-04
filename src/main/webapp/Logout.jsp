<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Logout</title>
</head>
<body>

<style type="text/css">
.topnav {
	background-color: #38f;
	border-radius: 10px;
	height: 30px;
	margin-top: 1%;
	color: white;
	padding: 18px;
	font-weight: bold;
}
</style>

	<%
	HttpSession ses = request.getSession();
	ses.invalidate();%>
	
	<div class="topnav">
		<label style="margin-left: 2%; font-size: 22px; margin-top: -10px;">
			Writers	Pen
		</label> 
	</div>
	
	<br>
	<div style="margin-left: 2%">
		Thank you for using our site. 
		<a href = "Login.jsp">Login</a> once again browse your favourite stories.
	</div>

</body>
</html>