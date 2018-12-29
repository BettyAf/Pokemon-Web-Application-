<%@ taglib prefix="json" uri="http://www.atg.com/taglibs/json" %>

<json:object>
  <json:array name="discard" var="d" items="${discard}">
      <json:property value="${d.card.id}"/>
  </json:array>
</json:object>