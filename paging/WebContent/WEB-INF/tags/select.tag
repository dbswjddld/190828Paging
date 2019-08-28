<%@ tag language="java" pageEncoding="UTF-8"%>
<%@ attribute name="name" required="true" %>
<!--attribute :속성 정의
	name :속성 명
	required :필수 사용 속성 -->
<select name="${name }">
	<option>개발</option>
	<option>인사</option>
	<option>기획</option>
</select>