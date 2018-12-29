<%@ taglib prefix="json" uri="http://www.atg.com/taglibs/json" %>

<json:object>
	<json:property name="id" value="${deck.id}"/>
	<json:array name="cards" var="card" items="${deck.cards}">
	  <json:object>
	    <json:property name="id" value="${card.id}"/>
	    <json:property name="t" value="${card.type}"/>
	    <json:property name="n" value="${card.name}"/>
	    <json:property name="b" value="${card.basic}"/>
	  </json:object>
	</json:array>
</json:object>