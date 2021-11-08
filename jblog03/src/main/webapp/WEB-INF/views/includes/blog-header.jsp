<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
<div id="header">
	<h1><a href="${pageContext.request.contextPath}/${blogVo.id }" style="color:#fff;">${blogVo.title }</a></h1>
	<ul>
		<c:choose>
			<c:when test="${authUser ==null }">
				<li><a href="${pageContext.request.contextPath}/user/login/${blogId }">로그인</a></li>
			</c:when>
			<c:when test="${authUser.id != blogId }">
				<li><a href="${pageContext.request.contextPath}/user/logout">로그아웃</a></li>
			</c:when>
			<c:when test="${authUser.id == blogId }">
				<li><a href="${pageContext.request.contextPath}/user/logout">로그아웃</a></li>
				<li><a href="${pageContext.request.contextPath}/${blogId }/adminBasic">블로그 관리</a></li>
			</c:when>
			<c:otherwise>
			</c:otherwise>
		</c:choose>
	</ul>
</div>