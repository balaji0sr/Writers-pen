<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>

<meta http-equiv="Content-Type" content="text/html;	 charset=UTF-8">


<script src="http://code.jquery.com/jquery-latest.min.js"></script>
<script src="http://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js" type="text/javascript"></script>
<script src="http://ajax.googleapis.com/ajax/libs/jqueryui/1.8/jquery-ui.min.js" type="text/javascript"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/crypto-js/3.1.2/rollups/aes.js"></script>

<link rel="stylesheet" href="Login.css">


<title>login</title>
</head>
<body>

	<div class="topnav">
		<label style="margin-left: 2%; font-size: 22px; margin-top: -10px;">
			Writers	Pen
		</label> 
		<a href="Aboutus.html" style="font-size: 12px">About Us</a>
	</div>

	<div id="box">
		
		<div id="loginbox">
			<form action="" method="post">
				<input type="text" id="loginmail" name="mail" placeholder="Mail" required>
					
				<div id="loginmsg" class="invalid"></div>
					
				<input type="password" id="loginpas" name="pas" placeholder="Password" required> <br>
				<button type="submit" id="loginsub" onclick = "loginsubmit()">login</button>
			</form>
		</div>

		<div id="signupbox">
			<form action="" method="post">
				<input type="text" id="signupname" name="name" placeholder="Name" required><br> 
				<input type="text" id="signupmail" name="mail" placeholder="Mail" required><br>
				<div id="signupmsg" class="invalid"></div>

				<input type="password" id="signuppas" name="pas" placeholder="Password" pattern="(?=.*\d)(?=.*[a-z])(?=.*[A-Z]).{8,}" required> <br>
				<input type="checkbox" id="showpas" onclick="show()"/>
				<span style="font-size: 12px; color: #999; margin-left: -2px;">Show Password</span> <br>

				<div id="signupmsg2">
					<h3>Password must contain the following:</h3>
					<p id="letter" class="invalid"> A <b>lowercase</b> letter </p> 
					<p id="capital" class="invalid"> A <b>capital (uppercase)</b> letter </p>
					<p id="number" class="invalid"> A <b>number</b> </p>
					<p id="length" class="invalid"> Minimum <b>8 characters</b> </p>
				</div>

				<input type="password" id="re-enter-pas" name="re-enter-pas" placeholder="re-enter password" required>
				<div id="signupmsg3" class="invalid"> </div>
				<button type="submit" id="signupsub" onclick = "signupsubmit()">create user</button>
			</form>
		</div>
		
		<button id = "logindiv" onclick="login()" style = "display: none; ">login</button>
		<button id = "signupdiv" onclick="signup()" >signup</button>
		
	</div>
	
	<script src="Login.js"></script>
</body>
</html>
