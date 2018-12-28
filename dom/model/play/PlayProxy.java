package org.soen387.dom.model.play;

import java.sql.SQLException;

import org.dsrg.soenea.domain.DomainObjectCreationException;
import org.dsrg.soenea.domain.MapperException;
import org.dsrg.soenea.domain.proxy.DomainObjectProxy;
import org.soen387.dom.mapper.play.PlayInputMapper;
import org.soen387.dom.model.bench.IBench;
import org.soen387.dom.model.deck.IDeck;
import org.soen387.dom.model.discard.IDiscard;
import org.soen387.dom.model.game.IGame;
import org.soen387.dom.model.hand.IHand;
import org.soen387.dom.model.player.IPlayer;

public class PlayProxy extends DomainObjectProxy<Long, Play> implements IPlay{

	public PlayProxy(Long id) {
		super(id);
	}
	
	@Override
	protected Play getFromMapper(Long id) throws MapperException, DomainObjectCreationException {
		try {
			return PlayInputMapper.find(id);
		} catch(MapperException | SQLException e) {
			throw new DomainObjectCreationException(e.getMessage(), e);
		}
	}

	@Override
	public IGame getGame() {
		return getInnerObject().getGame();
	}

	@Override
	public void setGame(IGame game) {
		getInnerObject().setGame(game);
	}

	@Override
	public IPlayer getPlayer() {
		return getInnerObject().getPlayer();
	}

	@Override
	public void setPlayer(IPlayer player) {
		getInnerObject().setPlayer(player);
	}

	@Override
	public int getStatus() {
		return getInnerObject().getStatus();
	}

	@Override
	public void setStatus(int status) {
		getInnerObject().setStatus(status);
	}

	@Override
	public IDeck getDeck() {
		return getInnerObject().getDeck();
	}

	@Override
	public void setDeck(IDeck deck) {
		getInnerObject().setDeck(deck);
	}

	@Override
	public IHand getHand() {
		return getInnerObject().getHand();
	}

	@Override
	public void setHand(IHand hand) {
		getInnerObject().setHand(hand);
	}

	@Override
	public IDiscard getDiscard() {
		return getInnerObject().getDiscard();
	}

	@Override
	public void setDiscard(IDiscard discard) {
		getInnerObject().setDiscard(discard);
	}

	@Override
	public IBench getBench() {
		return getInnerObject().getBench();
	}

	@Override
	public void setBench(IBench bench) {
		getInnerObject().setBench(bench);
	}

}
