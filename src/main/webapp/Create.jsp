<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">

<script src="https://cdn.quilljs.com/1.3.6/quill.js"></script>
<script src="https://cdn.quilljs.com/1.3.6/quill.min.js"></script>
<script
	src="https://cdnjs.cloudflare.com/ajax/libs/jquery/3.6.0/jquery.min.js"></script>

<link href="https://cdn.quilljs.com/1.3.6/quill.snow.css"
	rel="stylesheet">
<link href="https://cdn.quilljs.com/1.3.6/quill.bubble.css"
	rel="stylesheet">

<title>Insert title here</title>
</head>
<body>
   <style>
   
#composestory #title {
    border: none;    
}

#composestory #title.focus {
    border: none;   
}
    
#composestory #fullContainer{
   width: 80%;
   margin-left: 10% 
   }
   
  #composestory #editor{
   height: 200px;
   }
   </style>
    

	<div id="toolbar"></div>
	<br/><br/>
	<div id="fullContainer">
	<input type = "text" id = "title" name = "title" placeholder="Title">
	<br/><br/>
	<div id="editor"></div>
	<br/><br/>
	<button id="upload" onclick = "uploadclick()">upload</button>
	<button id="draft">draft</button>
</div>
	<script type="text/javascript">
		var toolbarOptions = [[ 'bold', 'italic', 'underline', 'strike' ] , 
		['blockquote' , 'code-block'],
		[{'header' : [1, 2 , 3 , 4 , 5 , 6 , false]}],
		[{'list' : 'ordered'} , {'list' : 'bullet'}],
		[{'script' : 'sub'} , {'script' : 'super'}],
		[{'indent' : '-1'} , {'indent' : '+1'}],
		[{'direction' : 'rtl'}],
		[{'size' : ['small' , false , 'large' , 'huge']}],
		['image' , 'video' , 'formula'],
		[{'color' : []} , {'background' : []}],
		[{'font' : []}],
		[{'align' : []}]		
];


		
		var quill = new Quill('#editor', {
			modules : {
				toolbar : toolbarOptions
			},
			theme : 'snow'
		});
		

		function uploadclick() {

			console.log(quill.container.innerHTML);

			var encodedContent = encodeURI(quill.container.innerHTML)
			var title = document.getElementById("title").value;
			

			$.ajax({
				url : 'uploaded',
				type : 'POST',
				//contentType : 'application/json' ,
				
				data :{
					userid : "1" ,
					username : "balaji" ,
						title : title ,
					encodeedcontent : encodedContent
					},
				cache : false,
				success : function(data) {
						console.log(data);
				},
				error : function() {
					console.log('error')
				}
			});
			}

		//quill.getContents(),
		
	</script>
</body>
</html>