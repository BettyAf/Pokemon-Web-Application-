package org.soen387.dom.model.hand;

import java.sql.SQLException;
import java.util.List;

import org.dsrg.soenea.domain.DomainObjectCreationException;
import org.dsrg.soenea.domain.MapperException;
import org.dsrg.soenea.domain.proxy.DomainObjectProxy;
import org.soen387.dom.mapper.hand.HandInputMapper;
import org.soen387.dom.model.cardInPlay.ICardInPlay;
import org.soen387.dom.model.play.IPlay;

public class HandProxy extends DomainObjectProxy<Long, Hand> implements IHand {
	
	
	public HandProxy(long id) {
		super(id);
	}
	
	@Override
	protected Hand getFromMapper(Long id) throws MapperException, DomainObjectCreationException {
		try {
			return HandInputMapper.find(id);
		} catch(MapperException | SQLException e) {
			throw new DomainObjectCreationException(e.getMessage(), e);
		}
	}

	@Override
	public IPlay getPlay() {
		return getInnerObject().getPlay();
	}

	@Override
	public void setPlay(IPlay play) {
		getInnerObject().setPlay(play);
	}

	@Override
	public List<ICardInPlay> getCardsInPlay() {
		return getInnerObject().getCardsInPlay();
	}

	@Override
	public void setCardsInPlay(List<ICardInPlay> cards) {
		getInnerObject().setCardsInPlay(cards);
	}
}
