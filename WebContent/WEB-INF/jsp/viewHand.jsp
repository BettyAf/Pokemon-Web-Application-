<%@ taglib prefix="json" uri="http://www.atg.com/taglibs/json" %>

<json:object>
  <json:array name="hand" var="h" items="${hand}">
      <json:property value="${h.card.id}"/>
  </json:array>
</json:object>