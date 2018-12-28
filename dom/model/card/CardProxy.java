package org.soen387.dom.model.card;

import java.sql.SQLException;

import org.dsrg.soenea.domain.DomainObjectCreationException;
import org.dsrg.soenea.domain.MapperException;
import org.dsrg.soenea.domain.proxy.DomainObjectProxy;
import org.soen387.dom.mapper.card.CardInputMapper;
import org.soen387.dom.model.deck.IDeck;

public class CardProxy extends DomainObjectProxy<Long, Card> implements ICard {

	public CardProxy(Long id) {
		super(id);
	}
	
	@Override
	protected Card getFromMapper(Long id) throws MapperException, DomainObjectCreationException {
		try {
			return CardInputMapper.find(id);
		} catch(MapperException | SQLException e) {
			throw new DomainObjectCreationException(e.getMessage(), e);
		}
	}

	@Override
	public String getType() {
		return getInnerObject().getType();
	}

	@Override
	public void setType(String type) {
		getInnerObject().setType(type);
		
	}

	@Override
	public String getName() {
		return getInnerObject().getName();
	}

	@Override
	public void setName(String name) {
		getInnerObject().setName(name);
	}

	@Override
	public String getBasic() {
		return getInnerObject().getBasic();
	}

	@Override
	public void setBasic(String basic) {
		getInnerObject().setBasic(basic);
		
	}

	@Override
	public IDeck getDeck() {
		return getInnerObject().getDeck();
	}

	@Override
	public void setDeck(IDeck deck) {
		getInnerObject().setDeck(deck);
	}

}
