package org.soen387.dom.model.game;

import org.dsrg.soenea.domain.interf.IDomainObject;
import org.soen387.dom.model.deck.IDeck;
import org.soen387.dom.model.play.IPlay;
import org.soen387.dom.model.player.IPlayer;

public interface IGame extends IDomainObject<Long> {
	
	public abstract IPlayer getChallenger();
	public abstract void setChallenger(IPlayer challenger);
	public abstract IPlayer getChallengee();
	public abstract void setChallengee(IPlayer challengee);
	public abstract IDeck getChallengerDeck();
	public abstract void setChallengerDeck(IDeck challengerDeck);
	public abstract IDeck getChallengeeDeck();
	public abstract void setChallengeeDeck(IDeck challengeeDeck);
	public abstract IPlay getChallengerPlay();
	public abstract void setChallengerPlay(IPlay challengerPlay);
	public abstract IPlay getChallengeePlay();
	public abstract void setChallengeePlay(IPlay challengeePlay);
	public abstract long getCurrentPlayer();
	public abstract void setCurrentPlayer(long currentPlayer);

}
