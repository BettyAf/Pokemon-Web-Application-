<%@ taglib prefix="json" uri="http://www.atg.com/taglibs/json" %>

<json:object>
  <json:array name="challenges" var="c" items="${challenges}">
    <json:object>
      <json:property name="id" value="${c.id}"/>
      <json:property name="version" value="${c.version}"/>
      <json:property name="challenger" value="${c.challenger.id}"/>
      <json:property name="challengee" value="${c.challengee.id}"/>
      <json:property name="status" value="${c.status}"/>
    </json:object>
  </json:array>
</json:object>