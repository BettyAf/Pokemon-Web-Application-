<%@ taglib prefix="json" uri="http://www.atg.com/taglibs/json" %>

<json:object>
  <json:array name="players" var="u" items="${users}">
    <json:object>
      <json:property name="id" value="${u.id}"/>
      <json:property name="user" value="${u.username}"/>
    </json:object>
  </json:array>
</json:object>