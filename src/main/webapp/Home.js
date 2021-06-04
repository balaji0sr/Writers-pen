var userid = document.getElementById("userid").value;
var username = document.getElementById("username").value;
var usermail = document.getElementById("usermail").value;
var parentstoryid = 0;
var likecount = 0;
var page = 1;
var type;
var draftid;

window.onload = getstories(0);

function getstories(typeid) {
	$('#useroptions').hide();

	if (typeid != null) {
		type = typeid;
		page = 1;
	}
	var dis = "";

	if (type == 0) $('#storylistdetail').html("Latest Stories");
	if (type == 1) $('#storylistdetail').html("Your Stories");
	if (type == 2) $('#storylistdetail').html("Saved As Draft");

	$.ajax({
		url: 'stories',
		type: 'GET',
		cache: false,
		data: {
			page: page,
			type: type
		},
		success: function(data) {
			page = data.info.page;
			previousblock(page);
			nextblock(data.info.moredata);


			for (let i = 0; i < data.story.length; i++) {
				dis = dis + "<div id=" + '"' + data.story[i].storyid + '"' + " class=" + '"' + "storyblock" + '"' + ">";
				dis = dis + "<div class='topic'><span onclick=\"story(" + data.story[i].storyid + ")\">" + data.story[i].title + "</span></div>";

				dis = dis + "<div class=" + '"' + "likecountdiv" + '"><span>likes: ' + data.story[i].likecount + "</span></div><br>";
				dis = dis + "<div class=" + '"' + "viewcountdiv" + '"><span>views: ' + data.story[i].viewcount + "</span></div>";

				if (type > 0) {
					dis = dis + "<div class=" + '"' + "deletebtn" + '"' + " onclick=" + '"' + "deletecontent(" + data.story[i].storyid + ")" + '"' + ">Delete</div>"

					if (type == 1) {
						dis = dis + "<div class=" + '"' + "editbtn" + '"' + " onclick=" + '"' + "editdiv(" + data.story[i].storyid + ")" + '"' + ">Edit</div>"
					} else {
						dis = dis + "<div class=" + '"' + "editbtn" + '"' + " onclick=" + '"' + "drafteditdiv(" + data.story[i].storyid + ")" + '"' + ">Edit</div>"
					}
				}

				if (type == 0) {
					dis = dis + "<div class=" + '"' + "essence" + '"' + "><span>publisher : " + data.story[i].username + "</span></div>";
				}
				dis = dis + "<div class=" + '"' + "time" + '"><span>' + data.story[i].updatetime + "</span></div>";
				dis = dis + "<div class=" + '"' + "wrapper" + '">' + data.story[i].content + "</div>" + "</div>";
			}

			$('#storylist').html(dis);
			showstorieslistdiv();
		},
		error: function() {
			console.log('error');
			//window.location.href = "Login.jsp";
		},
	});
}

$('#previous').click(function() {
	if (page > 1) {
		page = page - 1
		getstories();
	}
});

$('#next').click(function() {
	page = page + 1
	getstories();
});


function previousblock(page) {
	if (page == 1) {
		$('#previous').hide();
	}
	else {
		$('#previous').show();
	}
}

function nextblock(lastpage) {
	if (lastpage) {
		$('#next').show();
	}
	else {
		$('#next').hide();
	}
}

var toolbarOptions = [['bold', 'italic', 'underline', 'strike'],
['blockquote', 'code-block'],
[{ 'header': [1, 2, 3, 4, 5, 6, false] }],
[{ 'list': 'ordered' }, { 'list': 'bullet' }],
[{ 'script': 'sub' }, { 'script': 'super' }],
[{ 'indent': '-1' }, { 'indent': '+1' }],
[{ 'direction': 'rtl' }],
[{ 'size': ['small', false, 'large', 'huge'] }],
['image', 'video', 'formula'],
[{ 'color': [] }, { 'background': [] }],
[{ 'font': [] }],
[{ 'align': [] }]
];

var quill = new Quill('#editor', {
	modules: {
		toolbar: toolbarOptions
	},
	theme: 'snow'
});

function homebtn(event) {
	event.stopPropagation();
	window.location = "Home.jsp";
}

/*function uploadimage() {

	let content = quill.container.innerHTML;

	let html = new DOMParser().parseFromString(content, "text/html");
	let result = [...html.images].map(e => e.src);

	var base64str = result[0];
	console.log(base64str);
			var encodedbase64 = encodeURI(base64str);
	
	
	$.ajax({
				url: 'Stories/photos',
				type: 'POST',
				data: {
					encodedbase64: encodedbase64
				},
				cache: false,
				success: function(data) {
					console.log(data);
				},
				error: function() {
					console.log('error')
				}
			});
	

}
*/


function uploadclick(type) {
	//uploadimage();


	document.getElementById("upload").disabled = true;
	document.getElementById("upload").textContent = "saving..."
	if (draftid != null) {
		deletedraft(draftid);
		draftid = null;
	}
	var encodedContent = encodeURI(quill.container.innerHTML);
	var titlein = document.getElementById("inserttitle").value;
	var newcontent = document.getElementById("upload").value;
	if (newcontent == "true") {
		$.ajax({
			url: 'stories',
			type: 'POST',
			//contentType : 'application/json' ,
			data: {
				userid: userid,
				username: username,
				title: titlein,
				encodeedcontent: encodedContent,
				type: type,
				parentstoryid: parentstoryid
			},
			cache: false,
			success: function(data) {
				getstories(0);
				showstorieslistdiv();
				document.getElementById("upload").textContent = "Publish As Story";
			},
			error: function() {
				console.log('error')
			}
		});
	} else {
		if (parentstoryid > 0) storyid = parentstoryid
		var obj = { storyid: storyid, title: titlein, encodeedcontent: encodedContent };
		var jsonstr = JSON.stringify(obj);
		$.ajax({
			url: 'stories',
			type: 'PUT',
			contentType: 'application/json',
			data: jsonstr,
			cache: false,
			success: function(data) {
				getstories(0);
				showstorieslistdiv();
				document.getElementById("upload").textContent = "Publish As Story";
			},
			error: function() {
				console.log('error')
			}
		});
	}
}

function draftclick(type) {
	document.getElementById("draft").disabled = true;
	document.getElementById("draft").textContent = "saving...";
	var newcontent = document.getElementById("draft").value;

	var encodedContent = encodeURI(quill.container.innerHTML)
	var titlein = document.getElementById("inserttitle").value;

	if (newcontent == "true") {
		$.ajax({
			url: 'stories',
			type: 'POST',
			data: {
				userid: userid,
				username: username,
				title: titlein,
				encodeedcontent: encodedContent,
				type: type,
				parentstoryid: parentstoryid
			},
			cache: false,
			success: function(data) {
				parentstoryid = null
				getstories(2);
				showstorieslistdiv();
				document.getElementById("draft").textContent = "Save As draft";
			},
			error: function() {
				console.log('error')
			}
		});
	}
	else {
		var obj = { storyid: storyid, title: titlein, encodeedcontent: encodedContent };
		var jsonstr = JSON.stringify(obj);
		$.ajax({
			url: 'stories',
			type: 'PUT',
			contentType: 'application/json',
			data: jsonstr,
			cache: false,
			success: function(data) {
				getstories(2);
				showstorieslistdiv();
				document.getElementById("draft").textContent = "Save As draft";

			},
			error: function() {
				console.log('error')
			}
		});
	}
}

function deletecontent(id) {
	$.ajax({
		url: 'stories/' + id,
		type: 'DELETE',
		cache: false,
		success: function(d) {
		},
		error: function() {
			console.log('error')
		}, complete: function() {
			getstories();
		}
	});
}

function deletecomment(id) {
	$.ajax({
		url: 'stories/' + id + '/comment',
		type: 'DELETE',
		cache: false,
		success: function(d) {
		},
		error: function() {
			console.log('error')
		}, complete: function() {
			getcomment();
		}
	});
}

function likeget() {
	$.ajax({
		url: 'stories/' + storyid + '/like',
		type: 'GET',
		cache: false,
		success: function(d) {
			if (d == null) {
				$("#likebtn").css('color', '#31364f');
				document.getElementById("likebtn").value = "true";
			} else {
				if (d.status == 1) {
					$("#likebtn").css('color', 'blue');
					document.getElementById("likebtn").value = "false";
				} else {
					$("#likebtn").css('color', '#31364f');
					document.getElementById("likebtn").value = "true";
				}
			}
		},
		error: function() {
			console.log('error')
		}, complete: function() {
		}
	});
}

function likeputordelete() {
	console.log(likecount)
	var nolike = document.getElementById("likebtn").value;
	if (nolike == "true") {
		$.ajax({
			url: 'stories/' + storyid + '/like',
			type: 'POST',
			cache: false,
			success: function(d) {
			},
			error: function() {
				console.log('error')
			}, complete: function() {
				$("#likebtn").css('color', 'blue');
				likecount = likecount + 1
				$("#likecountspan").html(likecount);
				document.getElementById("likebtn").value = "false";
			}
		});
	} else {
		$.ajax({
			url: 'stories/' + storyid + '/like',
			type: 'DELETE',
			cache: false,
			success: function(d) {
			},
			error: function() {
				console.log('error')
			}, complete: function() {
				$("#likebtn").css('color', '#31364f');
				likecount = likecount - 1
				$("#likecountspan").html(likecount);
				document.getElementById("likebtn").value = "true";
			}
		});
	}
}


function composeit() {
	showeditordiv();

	document.getElementById("upload").value = "true";
	document.getElementById("draft").value = "true";

}

function composeoredit() {
	showeditordiv()
}

var storyid;
function story(id) {
	var str = "";
	$.ajax({
		url: 'stories/' + id,
		type: 'GET',
		cache: false,
		success: function(d) {
			storyid = d.storyid;
			likecount = d.likecount

			str = '<span>' + d.title + "</span>";
			$('#title').html(str);

			str = 'Publisher :<span id = "publisherspan"> ' + d.username; + "</span>"
			$('#publisherdiv').html(str);

			str = 'Created Time : <span style="font-size: 13px;"> ' + d.updatetime; + "</span>"
			$('#updatetimediv').html(str);

			str = 'Last Modified Time : <span style="font-size: 13px;"> ' + d.modifiedtime; + "</span>"
			$('#modifiedtimediv').html(str);

			str = 'Likes : <span id = "likecountspan">' + d.likecount + "</span>";
			$('#likecountdiv').html(str);

			str = 'Views : <span>' + d.viewcount + "</span>";
			$('#viewcountdiv').html(str);

			str = d.content;
			$('#content').html(str);
			str = "";

			getcomment();
			likeget();
		},
		error: function() {
			console.log('error')
		},
		complete: function() {
			showstorydiv();
		}
	});
}

var cmt = "";
function getcomment() {
	console.log("incomment");
	$.ajax({
		url: 'stories/' + storyid + '/comment',
		type: 'GET',
		cache: false,
		success: function(commentdata) {
			for (let i = 0; i < commentdata.length; i++) {
				cmt = cmt + "<div id=" + '"' + commentdata[i].commentid + '"' + " class=" + '"' + "commentblock" + '"' + ">";
				cmt = cmt + "<div class=" + '"' + "cmtuser" + '">' + commentdata[i].name + "</div>";
				if (commentdata[i].userid == userid) {
					cmt = cmt + "<div class=" + '"' + "deletebtn" + '"' + " onclick=" + '"' + "deletecomment(" + commentdata[i].commentid + ")" + '"' + ">Delete</div>"
				}
				cmt = cmt + "<div class=" + '"' + "cmttime" + '">' + commentdata[i].updatetime + "</div>";
				cmt = cmt + "<div class=" + '"' + "comment" + '">' + commentdata[i].comment + "</div>";
				cmt = cmt + "</div>";
			}
			$('#commentdiv').html(cmt);
			cmt = "";
		},
		error: function() {
			console.log('error')
		}
	});
}

function putcomment() {
	var conmmentput = document.getElementById("conmmentput").value;
	if (conmmentput.length > 1) {
		document.getElementById('conmmentput').value = '';
		$.ajax({
			url: 'stories/' + storyid + '/comment',
			type: 'POST',
			data: {
				userid: userid,
				name: username,
				comment: conmmentput
			},
			cache: false,
			success: function(da) {
				getcomment();
			},
			error: function() {
				console.log('error');
			}
		});
	}
}


function editdiv(id) {
	$.ajax({
		url: 'stories/' + id,
		type: 'GET',
		cache: false,
		success: function(d) {
			document.getElementById("upload").value = "false";
			storyid = d.storyid;

			parentstoryid = d.storyid;

			document.getElementById("inserttitle").value = d.title;
			$('#editor').html(d.content);

			document.getElementById("upload").textContent = "Re-Publish Story";
			composeoredit();
		},
		error: function() {
			console.log('error')
		}
	});
}

function drafteditdiv(id) {
	$.ajax({
		url: 'stories/' + id,
		type: 'GET',
		cache: false,
		success: function(d) {
			storyid = d.storyid;
			parentstoryid = d.parentstoryid;
			document.getElementById("draft").value = "false";

			document.getElementById("inserttitle").value = d.title;
			$('#editor').html(d.content);

			document.getElementById("draft").textContent = "Update Draft";
			if (parentstoryid > 0) {
				document.getElementById("upload").textContent = "Re-Publish As Story";
				document.getElementById("upload").value = "false"
			}
			composeoredit();
		},
		error: function() {
			console.log('error')
		}
	});
}

function useroptionsdrop() {
	$('#useroptions').show();
}

function useroptionshide() {
}

function showstorieslistdiv() {
	$('.ql-editor').attr('contenteditable', 'false');
	$('#pagenumber').html(page);
	$('#storieslistdiv').show();
	$('#storydiv').hide();
	$('#editordiv').hide();
}

function showstorydiv() {
	$('.ql-editor').attr('contenteditable', 'false');
	$('#storydiv').show();
	$('#storieslistdiv').hide();
	$('#editordiv').hide();
}

function showeditordiv() {
	$('#upload').disabled = false;
	$('#draft').disabled = false;

	$('.ql-editor').attr('contenteditable', 'true');
	$('#editordiv').show();
	$('#storieslistdiv').hide();
	$('#storydiv').hide();
}

function logout() {
	$("#useroptions").hide();
	event.stopPropagation();
	window.location = "Logout.jsp";
	getstories();
}


