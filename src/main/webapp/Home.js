var userid = document.getElementById("userid").value;
var username = document.getElementById("username").value;
var usermail = document.getElementById("usermail").value;
var parentstoryid = 0;
var likecount = 0;
var page = 1;
var type;
var draftid;
var searchcontent;
var subscribedstories = "false";
var quill;

window.onload = getstories(0);

$('#searchit').click(function() {
	var searchvalue = document.getElementById('search').value;

	if (searchvalue != null) {
		if (searchvalue.length > 2) {
			page = 1;
			searchcontent = searchvalue;
		}
	}
	getstories();
	document.getElementById('search').value = "";
});

function getsubscribedstories() {
	subscribedstories = "true";
	getstories(0);
}

function getallstories() {
	subscribedstories = "false";
	getstories(0);
}

function getstories(typeid) {
	var likedstoriesbtnvalue = document.getElementById("likedstoriesbtn").value;
	var commentedstoriesbtnvalue = document.getElementById("commentedstoriesbtn").value;
	var dis = "";
	if (typeid != null) {
		type = typeid;
		page = 1;
		searchcontent = "";
	}
	if (type == 0) $('#storylistdetail').html("Latest Stories");
	if (type == 1) $('#storylistdetail').html("Your Stories");
	if (type == 2) $('#storylistdetail').html("Saved As Draft");
	$.ajax({
		url: 'stories',
		type: 'GET',
		cache: false,
		data: {
			page: page,
			type: type,
			searchcontent: searchcontent,
			likedstories: likedstoriesbtnvalue,
			commentedstories: commentedstoriesbtnvalue,
			subscribedstories: subscribedstories
		},
		success: function(data) {
			$('#useroptions').hide();

			page = data.info.page;
			previousblock(page);
			nextblock(data.info.moredata);

			if (data.story.length == 0) dis = "no stories ";
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
				dis = dis + "<div class=" + '"' + "wrapper" + '">' + data.story[i].contenttext + "</div>" + "</div>";
			}
			$('#storylist').html(dis);
			showstorieslistdiv();
		},
		error: function() {
			console.log('error');
			window.location.href = "Login.jsp";
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

function yourstories() {

	$('#userstoryoptiondiv').show();
	getstories(1);
}

function likedstorieslist() {
	document.getElementById("likedstoriesbtn").value = "true";
	getstories();
}

function commentedstorieslist() {
	document.getElementById("commentedstoriesbtn").value = "true";
	getstories();
}

function subscribedgenre() {
	$.ajax({
		url: 'stories/' + userid + '/subscribedgenre',
		type: 'GET',
		cache: false,
		success: function(data) {

			var substr = "";
			var unsubstr = "";

			if (data.subgenre.length == 0) substr = "no stories ";
			for (let j = 0; j < data.subgenre.length; j++) {
				substr = substr + "<button id = \"" + data.subgenre[j].genre_userid + "\" class = \"genrebtn\" onclick = \"deletesubscription(" + data.subgenre[j].genreid + ")\" >" + data.subgenre[j].genrename + "</button>";
			}
			for (let j = 1; j < data.genre.length; j++) {
				unsubstr = unsubstr + "<button id = \"" + data.genre[j].genreid + "\" class = \"genrebtn\" onclick = \"addsubscription(" + data.genre[j].genreid + ")\">" + data.genre[j].genrename + "</button>";
			}

			$('#subscribedgenrelist').html(substr);
			$('#unsubscribedgenrelist').html(unsubstr);
			showsubscribedgenrediv();
		},
		error: function() {
			console.log('error');
		},
	});
}

function addsubscription(genreid) {
	$.ajax({
		url: 'stories/subscribedgenre/' + genreid,
		type: 'POST',
		cache: false,
		success: function(data) {
			subscribedgenre();
		},
	});
}


function deletesubscription(genre_userid) {
	$.ajax({
		url: 'stories/subscribedgenre/' + genre_userid,
		type: 'DELETE',
		cache: false,
		success: function(data) {
			subscribedgenre();
		},
	});
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
[{ 'align': [] }],
[{ beyondgrammar: ['en-US', 'en-GB', false] }]
];

window.BeyondGrammar.initBeyondGrammar(function() {

	quill = new Quill('#editor', {
		modules: {
			toolbar: {
				container: toolbarOptions,
				handlers: {
					beyondgrammar: window.BeyondGrammar.getToolbarHandler(quill)
				}
			},
			beyondgrammar: {
				service: {
					apiKey: "E8FEF7AE-3F36-4EAF-A451-456D05E6F2A3",
				},
				grammar: {
					languageIsoCode: "en-US",

					checkStyle: true,

					checkSpelling: true,

					checkGrammar: true,

					showThesaurusByDoubleClick: true,

					showContextThesaurus: false,
				}
			}
		},
		theme: 'snow'
	});
});

function homebtn(event) {
	event.stopPropagation();
	window.location = "Home.jsp";
}

$("#file").on("change", function() {
	var files = $("#file")[0].files;

	if (files.length > 5) {
		alert("You can select only 5 files");
	} else {
		saveattachment();
	}
});

var atachmentcontent = "";

function saveattachment() {
	console.log("in save");

	var files = $('#file')[0].files;
	var fileslength = files.length;

	if (fileslength > 0) {
		for (let i = 0; i < fileslength; i++) {

			let fd = new FormData();

			fd.append('file', files[i]);

			$.ajax({
				url: 'attachmentservice',
				type: 'post',
				data: fd,
				contentType: false,
				processData: false,
				dataType: "json",
				async: false,
				success: function(filedata) {
					var obj = JSON.parse(filedata);
					atachmentlink = "<div id = \"attachmentdiv\"><i class=\"fa fa-file-pdf-o\"> <a href=" + obj.link + " target=\"_blank\" >" + obj.filename + "</a></i></div>";
					atachmentcontent = atachmentcontent + atachmentlink;
				},
			});
		}
	}
}

var content = "";
var html = "";
var base64str = "";
var contentsrcpath = "";
var encodedContent = "";

function saveimageupload(type) {

	content = quill.container.innerHTML;
	html = new DOMParser().parseFromString(content, "text/html");
	result = [...html.images].map(e => e.src);

	for (let i = 0; i < result.length; i++) {
		base64str = result[i];
		$.ajax({
			url: 'imageservice',
			type: 'POST',
			data: {
				encodedbase64: base64str,
			},
			cache: false,
			async: false,
			success: function(data) {
				base64str = '"' + base64str + '"';
				content = content.replace(base64str, data);
			},
			error: function() {
				console.log('error' + i);
			}
		});
	}

	content = content + atachmentcontent;
	encodedContent = encodeURI(content);
	uploadclick(type);
}

function uploadclick(type) {

	document.getElementById("upload").disabled = true;
	document.getElementById("upload").textContent = "saving..."
	if (draftid != null) {
		deletedraft(draftid);
		draftid = null;
	}

	var titlein = document.getElementById("inserttitle").value;
	var newcontent = document.getElementById("upload").value;
	var genre = document.getElementById("genre").value;

	var conenttext = document.getElementById('editor').getElementsByTagName("p");
	var contentstr = "";
	for (let i = 0; i < conenttext.length; i++) {
		contentstr = contentstr + conenttext[i].textContent + " ";
	}

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
				parentstoryid: parentstoryid,
				contenttext: contentstr,
				genre: genre,
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
		var obj = { storyid: storyid, title: titlein, encodeedcontent: encodedContent, contenttext: contentstr };
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


	content = quill.container.innerHTML;
	html = new DOMParser().parseFromString(content, "text/html");
	result = [...html.images].map(e => e.src);
	base64str = result[0];

	var conenttext = document.getElementById('editor').getElementsByTagName("p");
	var contentstr = "";
	for (let i = 0; i < conenttext.length; i++) {
		contentstr = contentstr + conenttext[i].textContent + " ";
	}

	$.ajax({
		url: 'Stories/photos',
		type: 'POST',
		data: {
			encodedbase64: base64str
		},
		cache: false,
		success: function(data) {
			var contentsrcpath = content.replace(base64str, data);

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
						parentstoryid: parentstoryid,
						contenttext: contentstr
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
				var obj = { storyid: storyid, title: titlein, encodeedcontent: encodedContent, contenttext: contentstr };
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
		},
		error: function() {
			console.log('error')
		}
	});
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
				$("#likebtn").css('color', '#8d8b8b');
				document.getElementById("likebtn").value = "true";
			} else {
				if (d.status == 1) {
					$("#likebtn").css('color', 'green');
					document.getElementById("likebtn").value = "false";
				} else {
					$("#likebtn").css('color', '#8d8b8b');
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
				$("#likebtn").css('color', 'green');
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
				$("#likebtn").css('color', '#8d8b8b');
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

			str = 'Created Time : <span style="font-size: 14px;"> ' + d.updatetime; + "</span>"
			$('#updatetimediv').html(str);

			str = 'Modified Time : <span style="font-size: 14px;"> ' + d.modifiedtime; + "</span>"
			$('#modifiedtimediv').html(str);

			str = 'Likes : <span id = "likecountspan">' + d.likecount + "</span>";
			$('#likecountdiv').html(str);

			str = 'Views : <span>' + d.viewcount + "</span>";
			$('#viewcountdiv').html(str);

			str = d.content;
			$('#content').html($.parseHTML(str));
			str = "";

if (d.genreid > 0) getgenrename(d.genreid);
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

function getgenrename(genreid) {
	$.ajax({
		url: 'stories/genre/' + genreid,
		type: 'GET',
		cache: false,
		success: function(data) {
		let str = 'Genre :<span id = "genrenamespan"> ' + data.genrename + "</span>"
			$('#genrenamediv').html(str);
		},
		error: function() {
			console.log('error')
		}
	});
}

var cmt = "";
function getcomment() {
	$.ajax({
		url: 'stories/' + storyid + '/comment',
		type: 'GET',
		cache: false,
		success: function(commentdata) {
			for (let i = 0; i < commentdata.length; i++) {
				cmt = cmt + "<div id=" + '"' + commentdata[i].commentid + '"' + " class=" + '"' + "commentblock" + '"' + ">";
				cmt = cmt + "<div class=" + '"' + "cmtuser" + '"><span>' + commentdata[i].name + "</span></div>";
				cmt = cmt + "<div class=" + '"' + "cmttime" + '"><span>' + commentdata[i].updatetime + "</span></div>";
				cmt = cmt + "<div class=" + '"' + "comment" + '"><span>' + commentdata[i].comment + "</span></div>";
				if (commentdata[i].userid == userid) {
					cmt = cmt + "<div class=" + '"' + "cmtdeletebtn" + '"' + " onclick=" + '"' + "deletecomment(" + commentdata[i].commentid + ")" + '"' + ">Delete</div>"
				}
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
	$('#subscribedgenrediv').hide();

}

function showstorydiv() {
	$('.ql-editor').attr('contenteditable', 'false');
	$('#storydiv').show();
	$('#storieslistdiv').hide();
	$('#editordiv').hide();
	$('#subscribedgenrediv').hide();

}

function showeditordiv() {
	$('#upload').disabled = false;
	$('#draft').disabled = false;

	$('.ql-editor').attr('contenteditable', 'true');
	$('#editordiv').show();
	$('#storieslistdiv').hide();
	$('#storydiv').hide();
	$('#subscribedgenrediv').hide();

}

function showsubscribedgenrediv() {
	$('.ql-editor').attr('contenteditable', 'false');
	$('#subscribedgenrediv').show();
	$('#storieslistdiv').hide();
	$('#storydiv').hide();
	$('#editordiv').hide();

}

function logout() {
	$("#useroptions").hide();
	event.stopPropagation();
	window.location = "Logout.jsp";
	getstories();
}
