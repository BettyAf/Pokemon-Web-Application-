package org.soen387.dom.model.game;

import java.sql.SQLException;

import org.dsrg.soenea.domain.DomainObjectCreationException;
import org.dsrg.soenea.domain.MapperException;
import org.dsrg.soenea.domain.proxy.DomainObjectProxy;
import org.soen387.dom.mapper.game.GameInputMapper;
import org.soen387.dom.model.deck.IDeck;
import org.soen387.dom.model.play.IPlay;
import org.soen387.dom.model.player.IPlayer;

public class GameProxy extends DomainObjectProxy<Long, Game> implements IGame {

	public GameProxy(Long id) {
		super(id);
	}

	@Override
	protected Game getFromMapper(Long id) throws MapperException, DomainObjectCreationException {
		try {
			return GameInputMapper.find(id);
		} catch(MapperException | SQLException e) {
			throw new DomainObjectCreationException(e.getMessage(), e);
		}
	}

	@Override
	public IPlayer getChallenger() {
		return getInnerObject().getChallenger();
	}

	@Override
	public void setChallenger(IPlayer challenger) {
		getInnerObject().setChallenger(challenger);
	}

	@Override
	public IPlayer getChallengee() {
		return getInnerObject().getChallengee();
	}

	@Override
	public void setChallengee(IPlayer challengee) {
		getInnerObject().setChallengee(challengee);
	}

	@Override
	public IDeck getChallengerDeck() {
		return getInnerObject().getChallengerDeck();
	}

	@Override
	public void setChallengerDeck(IDeck challengerDeck) {
		getInnerObject().setChallengerDeck(challengerDeck);
	}

	@Override
	public IDeck getChallengeeDeck() {
		return getInnerObject().getChallengeeDeck();
	}

	@Override
	public void setChallengeeDeck(IDeck challengeeDeck) {
		getInnerObject().setChallengeeDeck(challengeeDeck);
	}

	@Override
	public IPlay getChallengerPlay() {
		return getInnerObject().getChallengerPlay();
	}

	@Override
	public void setChallengerPlay(IPlay challengerPlay) {
		getInnerObject().setChallengerPlay(challengerPlay);
	}

	@Override
	public IPlay getChallengeePlay() {
		return getInnerObject().getChallengeePlay();
	}

	@Override
	public void setChallengeePlay(IPlay challengeePlay) {
		getInnerObject().setChallengeePlay(challengeePlay);
	}

	@Override
	public long getCurrentPlayer() {
		return getInnerObject().getCurrentPlayer();
	}

	@Override
	public void setCurrentPlayer(long currentPlayer) {
		getInnerObject().setCurrentPlayer(currentPlayer);
	}

}
