<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="my" tagdir="/WEB-INF/tags" %>
<!--tagdir :(태그 경로)에 있는 태그를 쓰겠다
	prefic :태그 경로를 지칭함 -->

<!DOCTYPE html>
<html><head><title>deptList.jsp</title></head>
<body>
<my:select name="departmentName"></my:select>
<!--my에 있는 select 태그를 쓰겠다
	name속성의 속성값은 departmentName -->
	<h3>부서등록</h3>
	<!-- 등록폼 시작 -->
	<form action="dept_control.jsp" method="post">
		<input type="hidden" name="action" value="insert"/>
		부서번호<input type="text" name="department_id"/>
		부서명<input type="text" name="department_name"/><br/>
		메니저<input type="text" name="manager_id"/>
		지역<input type="text" name="location_id"/>
		<input type="submit" value="등록"/><br/>
	</form>
	<!-- 등록폼 끝 -->
	<hr>
	<!-- 검색폼 시작 -->
	<div style="border: 1px solid gray">
		<form name="searchFrm" action="DeptListPagingServ">
		    <input type="hidden" name="p" value="1"/>
			메니저<input type="text" name="managerId" value="${param.managerId}"/>
			지역  <input type="text" name="locationId" value="${param.locationId}"/>
			<input type="submit" value="검색"/><br/>
		</form>
	</div>
	<!-- 검색폼 끝 -->
	
	<h3>부서목록</h3>
	<table>
		<tr>
			<th>부서번호</th>
			<th>부서명</th>
			<th>메니저</th>
			<th>지역</th>
		</tr>
		<c:forEach items="${list}" var="vo">
		<tr>
			<th>${vo.getDepartmentId()}</th>
			<th>${vo.getDepartmentName()}</th>
			<th>${vo.getManagerId()}</th>
			<th>${vo.getLocationId()}</th>
		</tr>
		</c:forEach>
	</table>
	
	<!-- 페이징 -->
	<my:paging jsfunc="doList" paging="${paging}"/>
	<script>
	function doList(p) {
		document.searchFrm.p.value = p; // 검색폼에 페이지 값 넣기
		document.searchFrm.submit(); // 검색폼 실행 (DeptListPagingServ)
	}
	</script>
</body>
</html>