package org.soen387.dom.model.challenge;

import org.dsrg.soenea.domain.interf.IDomainObject;
import org.soen387.dom.model.deck.IDeck;
import org.soen387.dom.model.player.IPlayer;

public interface IChallenge extends IDomainObject<Long> {
	
	public abstract IPlayer getChallenger();
	public abstract void setChallenger(IPlayer challenger);
	public abstract IPlayer getChallengee();
	public abstract void setChallengee(IPlayer challengee);
	public abstract IDeck getChallengerDeck();
	public abstract void setChallengerDeck(IDeck challengerDeck);
	public abstract int getStatus();
	public abstract void setStatus(int status);

}
