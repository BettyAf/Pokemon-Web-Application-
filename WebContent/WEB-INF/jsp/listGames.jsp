<%@ taglib prefix="json" uri="http://www.atg.com/taglibs/json" %>

<json:object>
  <json:array name="games" var="g" items="${games}">
    <json:object>
      <json:property name="id" value="${g.id}"/>
      <json:property name="version" value="${g.version}"/>
      <json:array name="players">
      	  <json:property value="${g.challenger.id}"/>
      	  <json:property value="${g.challengee.id}"/>
  	  </json:array>
    </json:object>
  </json:array>
</json:object>