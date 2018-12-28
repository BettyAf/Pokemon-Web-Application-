package org.soen387.dom.model.deck;

import java.sql.SQLException;
import java.util.List;

import org.dsrg.soenea.domain.DomainObjectCreationException;
import org.dsrg.soenea.domain.MapperException;
import org.dsrg.soenea.domain.proxy.DomainObjectProxy;
import org.soen387.dom.mapper.deck.DeckInputMapper;
import org.soen387.dom.model.card.ICard;
import org.soen387.dom.model.player.IPlayer;

public class DeckProxy extends DomainObjectProxy<Long, Deck> implements IDeck {

	public DeckProxy(Long id) {
		super(id);
	}
	
	@Override
	protected Deck getFromMapper(Long id) throws MapperException, DomainObjectCreationException {
		try {
			return DeckInputMapper.find(id);
		} catch(MapperException | SQLException e) {
			throw new DomainObjectCreationException(e.getMessage(), e);
		}
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
	public List<ICard> getCards() {
		return getInnerObject().getCards();
	}

	@Override
	public void setCards(List<ICard> cards) {
		getInnerObject().setCards(cards);
	}
}
