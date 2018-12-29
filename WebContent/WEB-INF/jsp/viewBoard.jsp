<%@ taglib prefix="json" uri="http://www.atg.com/taglibs/json" %>

<json:object>
  <json:object name="game">
	  <json:property name="id" value="${game.id}"/>
	  <json:property name="version" value="${game.version}"/>
      <json:array name="players">
      	  <json:property value="${game.challenger.id}"/>
      	  <json:property value="${game.challengee.id}"/>
  	  </json:array>
	  <json:property name="current" value="${game.currentPlayer}"/>
	  <json:array name="deck">
      	  <json:property value="${game.challengerDeck.id}"/>
      	  <json:property value="${game.challengeeDeck.id}"/>
  	  </json:array>
	  <json:object name="play">
		  <json:object name="${waitingPlay.player.id}">
		  	<json:property name="status" value="${waitingStatus}"/>
		  	<json:property name="discardsize" value="${waitingDiscard}"/>
		  	<json:array name="bench" var="b" items="${waitingBench}">	
		  		<json:property value="${b.id}"/>
		  	</json:array>  	
		  </json:object>
		  <json:object name="${currentPlay.player.id}">
			  <json:property name="status" value="${currentStatus}"/>
			  	<json:property name="decksize" value="${currentDeck}"/>
			  	<json:property name="discardsize" value="${currentDiscard}"/>
			  	<json:array name="bench" var="b" items="${currentBench}">	
			  		<json:object>
			  			<json:property name="id" value="${b.card.id}"/>
			  			<json:property name="e" value="${b.energy.id == 0 ? null : b.energy.id}"/>
			  			<json:property name="b" value="${b.basic.id == 0 ? null : b.basic.id}"/>
			  		</json:object>
			  	</json:array>
		  </json:object>
	  </json:object>  
  </json:object>
</json:object>