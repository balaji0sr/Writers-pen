
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>

<script src="http://code.jquery.com/jquery-latest.min.js"></script>
<script
	src="http://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"
	type="text/javascript"></script>
<script
	src="http://ajax.googleapis.com/ajax/libs/jqueryui/1.8/jquery-ui.min.js"
	type="text/javascript"></script>

<script src="https://cdn.quilljs.com/1.3.6/quill.js"></script>
<script src="https://cdn.quilljs.com/1.3.6/quill.min.js"></script>
<script
	src="https://cdn.prowritingaid.com/beyondgrammar-quill/1.0.72/dist/beyond-grammar-plugin.js"></script>


<link href="https://cdn.quilljs.com/1.3.6/quill.snow.css"
	rel="stylesheet">
<link href="https://cdn.quilljs.com/1.3.6/quill.bubble.css"
	rel="stylesheet">

<link rel="stylesheet"
	href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">

<link rel="stylesheet" href="Home.css">

<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">

<title>Writers Home</title>
</head>

<body>
	<input type="hidden" id="username"
		value="<%=(String) session.getAttribute("name")%>" />
	<input type="hidden" id="userid"
		value="<%=session.getAttribute("userid")%>" />
	<input type="hidden" id="usermail"
		value="<%=(String) session.getAttribute("mail")%>" />

	<div class="topnav">
		<button id="homebtn" class="btn" onclick="homebtn(event)">
			<i class="fa fa-home"></i>
		</button>

		<label style="margin-left: -6px; font-size: 22px;">Writers Pen</label>

		<div id="useroption" onclick="useroptionsdrop()">
			<%=session.getAttribute("name")%>
			<table id="useroptions" cellpadding="4px">
				<tr>
					<td><button id="homebtn" class="btn" onclick="homebtn(event)"
							style="color: black; font-size: 20px;">
							<i class="fa fa-home"></i>
						</button></td>
				<tr />
				<tr>
					<td onclick="yourstories(1)">Stories</td>
				<tr />
				<tr>
					<td onclick="getstories(2)">Drafts</td>
				<tr />
				<tr>
					<td onclick="subscribedgenre()">Subscribed Genre</td>
				<tr />
				<tr>
					<td onclick="logout(event)">Logout</td>
				<tr />
			</table>
		</div>

		<input type="button" value="Compose Blog" id="compose"
			onclick="showeditordiv()">
		<button type="submit" id="searchit">
			<i class="fa fa-search"></i>
		</button>
		<input type="text" placeholder="Search.." id="search">
	</div>

	<div id="storieslistdiv">

		<div id="storieslistviewoptiondiv">
			<div id="allstoriesbtn">
				<a onclick="getallstories()">All Stoies</a>
			</div>
			<div id="subscribedstroiesbtn">
				<a onclick="getsubscribedstories()">Subscribed Stoies</a>
			</div>
		</div>

		<span id="storylistdetail">Latest Stories</span>
		<div id="storylist">Loading awesome stories...</div>

		<div id="userstoryoptiondiv">
			<button id="likedstoriesbtn" onclick="likedstorieslist()"
				value="false">Liked Stories</button>
			<button id="commentedstoriesbtn" onclick="commentedstorieslist()"
				value="false">Commented Stories</button>
		</div>

		<div id="navigate" style="text-align: center;">
			<input type="button" value="Previous" id="previous" /> <span
				id="pagenumber"></span> <input type="button" value="Next" id="next" />
		</div>
	</div>


	<div id="subscribedgenrediv">

		<span>Subscribed Genre</span>
		<div id="subscribedgenrelist"></div>
		<span>Un-Subscribed Genre</span>
		<div id="unsubscribedgenrelist"></div>

		<button id="changesubscriptionbtn" onclick="changesubscription()"></button>

	</div>



	<div id="storydiv">

		<button class="backbtn" onclick="showstorieslistdiv()">
			<i class='fa fa-arrow-left'></i>
		</button>

		<div id="headingdiv">
			<table>
				<tr>
					<td><div id="title"></div></td>
					<td style="float: right;"><div id="genrenamediv">Genre : <span id = "genrenamespan">No Genre</span></div></td>
				</tr>
				<tr>
					<td><div id="publisherdiv"></div></td>
					<td style="float: right;"><span id="updatetimediv"></span></td>
				</tr>
				<tr>
					<td><span id="viewcountdiv"></span>

						<button id="likebtn" onclick="likeputordelete()" value="false">
							<i id="likeicon" class="fa fa-thumbs-up"></i>
						</button> <span style="float: right;" id="likecountdiv"></span></td>
					<td style="float: right;"><div id="modifiedtimediv"></div></td>
				</tr>
			</table>
		</div>
		<div id="content"></div>

		<div id="commentbox">

			<label for="commentbox" style="color: #777;"> Comments:</label>

			<div id="commentdiv"></div>
			<div id="commentinput">
				<textarea id="conmmentput" rows="4" cols="50"
					placeholder="Add your comment here"></textarea>
				<br>
				<button id="commentsub" onclick="putcomment()">Save Comment</button>
			</div>
		</div>
	</div>

	<div id="editordiv">
		<button class="backbtn" onclick=" showstorieslistdiv()">
			<i class="fa fa-arrow-left"></i>
		</button>
		<span id="editerdetail">Compose your Blog!</span>
		<div id="composediv">
			<div id="toolbar"></div>

			<div id="fullContainer">
				<input type="text" id="inserttitle" name="inserttitle"
					placeholder="Title"> <br /> <br />
				<div id="editor"></div>
			</div>

			<div id="additionaloptionsdiv">
				<input type="file" id="file" name="file" multiple>
			</div>

			<br />
			<div id="publishbuttondiv">

				<button id="upload" onclick="saveimageupload(1)" value="true">
					Publish As Story</button>
				<button id="draft" onclick="draftclick(2)" value="true">Save
					As Draft</button>
				<select name="genre" id="genre">
					<option value=" ">Select Genre</option>
					<option value="fashion">Fashion</option>
					<option value="food">Food</option>
					<option value="travel">Travel</option>
					<option value="music">Music</option>
					<option volvo="lifestyle">Lifestyle</option>
					<option value="fitness">Fitness</option>
					<option value="sports">Sports</option>
					<option value="finance">Finance</option>
					<option value="political">Political</option>
					<option value="business">Business</option>
					<option value="movie">Movie</option>
					<option value="news">News</option>
					<option value="gaming">Gaming</option>
					<option value="science">Science</option>
					<option value="history">History</option>
				</select>
			</div>
		</div>
	</div>
	<script src="Home.js"></script>
</body>
</html>