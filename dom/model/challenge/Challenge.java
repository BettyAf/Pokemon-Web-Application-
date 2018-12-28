package org.soen387.dom.model.challenge;

import org.dsrg.soenea.domain.DomainObject;
import org.soen387.dom.model.deck.IDeck;
import org.soen387.dom.model.player.IPlayer;

public class Challenge extends DomainObject<Long> implements IChallenge {
	

	IPlayer challenger;
	IPlayer challengee;
	IDeck challengerDeck;
	int status;
	
	protected Challenge(Long id, long version, IPlayer challenger, IPlayer challengee, IDeck challengerDeck, int status) {
		super(id, version);
		this.challenger = challenger;
		this.challengee = challengee;
		this.challengerDeck = challengerDeck;
		this.status = status;
	}

	public IPlayer getChallenger() {
		return challenger;
	}

	public void setChallenger(IPlayer challenger) {
		this.challenger = challenger;
	}

	public IPlayer getChallengee() {
		return challengee;
	}

	public void setChallengee(IPlayer challengee) {
		this.challengee = challengee;
	}

	public IDeck getChallengerDeck() {
		return challengerDeck;
	}

	public void setChallengerDeck(IDeck challengerDeck) {
		this.challengerDeck = challengerDeck;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}
	
}
