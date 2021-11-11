<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<!doctype html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>JBlog</title>
<Link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/jblog.css">
<script type="text/javascript" src="${pageContext.request.contextPath }/assets/js/jquery/jquery-1.9.0.js"></script>
<script>
$(function(){
	$("a[name='category']").click(function(event){
		event.preventDefault();
		console.log(this.getAttribute("href"));
		var splits = this.getAttribute("href").split("/");
		var blogId = splits[3];
		console.log(blogId);
		var urlLink =this.getAttribute("href");
		$.ajax({
			url:urlLink,
			async:true,
			type:"get",
			dataType:"json",
			success: function(response){
				console.log(response);
				var html =""
				for(var i=0;i<response.data.length;i++){
					html+="<li><a href='${pageContext.request.contextPath}/"+blogId+"/"+response.data[i].categoryNo+"/"+response.data[i].no+"'>"+response.data[i].title+"</a> <span>"+response.data[i].regDate+"</span></li>";	
				}
				
				$("ul[class='blog-list']").html(html);
			}
			
		});
	});
});
</script>
</head>
<body>
	<div id="container">
		<c:import url="/WEB-INF/views/includes/blog-header.jsp"/>
		<div id="wrapper">
			<div id="content">
				<div class="blog-content">
					<h4>${postVo.title }</h4>
					<p>
						${postVo.contents }
					<p>
				</div>
				<ul class="blog-list">
					<c:forEach items="${postList }" var="pList">
						<li><a href="${pageContext.request.contextPath}/${blogId }/${pList.categoryNo }/${pList.no }">${pList.title }</a> <span>${pList.regDate }</span>	</li>
					</c:forEach>
				</ul>
			</div>
		</div>

		<div id="extra">
			<div class="blog-logo">
				<img src="${pageContext.request.contextPath}/${blogVo.logo }">
			</div>
		</div>

		<div id="navigation">
			<h2>카테고리</h2>
			<ul>
				<c:forEach items="${categoryList }" var="list">
					<li><a name="category" href="${pageContext.request.contextPath}/api/${blogId }/${list.no }">${list.name }</a></li>
				</c:forEach>
			</ul>
		</div>
		<c:import url="/WEB-INF/views/includes/blog-footer.jsp"/>
	</div>
</body>
</html>