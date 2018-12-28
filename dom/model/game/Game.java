package org.soen387.dom.model.game;

import org.dsrg.soenea.domain.DomainObject;
import org.soen387.dom.model.deck.IDeck;
import org.soen387.dom.model.play.IPlay;
import org.soen387.dom.model.player.IPlayer;

public class Game extends DomainObject<Long> implements IGame {
	
	IPlayer challenger;
	IPlayer challengee;
	IDeck challengerDeck;
	IDeck challengeeDeck;
	IPlay challengerPlay;
	IPlay challengeePlay;
	long currentPlayer;
	
	protected Game(Long id, long version, IPlayer challenger, IPlayer challengee, IDeck challengerDeck, IDeck challengeeDeck, long currentPlayer) {
		super(id, version);
		this.challenger = challenger;
		this.challengee = challengee;
		this.challengerDeck = challengerDeck;
		this.challengeeDeck = challengeeDeck;
		this.currentPlayer = currentPlayer;
	}
	
	protected Game(Long id, long version, IPlayer challenger, IPlayer challengee, IDeck challengerDeck, IDeck challengeeDeck, IPlay challengerPlay, IPlay challengeePlay, long currentPlayer) {
		super(id, version);
		this.challenger = challenger;
		this.challengee = challengee;
		this.challengerDeck = challengerDeck;
		this.challengeeDeck = challengeeDeck;
		this.challengerPlay = challengerPlay;
		this.challengeePlay = challengeePlay;
		this.currentPlayer = currentPlayer;
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

	@Override
	public IDeck getChallengeeDeck() {
		return this.challengeeDeck;
	}

	@Override
	public void setChallengeeDeck(IDeck challengeeDeck) {
		this.challengeeDeck = challengeeDeck;
	}

	@Override
	public IPlay getChallengerPlay() {
		return this.challengerPlay;
	}

	@Override
	public void setChallengerPlay(IPlay challengerPlay) {
		this.challengerPlay = challengerPlay;
	}

	@Override
	public IPlay getChallengeePlay() {
		return challengeePlay;
	}

	@Override
	public void setChallengeePlay(IPlay challengeePlay) {
		this.challengeePlay = challengeePlay;
	}

	@Override
	public long getCurrentPlayer() {
		return currentPlayer;
	}

	@Override
	public void setCurrentPlayer(long currentPlayer) {
		this.currentPlayer = currentPlayer;
	}
	
}
