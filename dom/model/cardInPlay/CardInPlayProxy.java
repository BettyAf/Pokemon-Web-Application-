package org.soen387.dom.model.cardInPlay;

import java.sql.SQLException;

import org.dsrg.soenea.domain.DomainObjectCreationException;
import org.dsrg.soenea.domain.MapperException;
import org.dsrg.soenea.domain.proxy.DomainObjectProxy;
import org.soen387.dom.mapper.cardInPlay.CardInPlayInputMapper;
import org.soen387.dom.model.card.ICard;
import org.soen387.dom.model.play.IPlay;

public class CardInPlayProxy extends DomainObjectProxy<Long, CardInPlay> implements ICardInPlay {

	public CardInPlayProxy(Long id) {
		super(id);
	}
	
	@Override
	protected CardInPlay getFromMapper(Long id) throws MapperException, DomainObjectCreationException {
		try {
			return CardInPlayInputMapper.find(id);
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
	public int getLocation() {
		return getInnerObject().getLocation();
	}

	@Override
	public void setLocation(int location) {
		getInnerObject().setLocation(location);
	}

	@Override
	public ICard getCard() {
		return getInnerObject().getCard();
	}

	@Override
	public void setCard(ICard card) {
		getInnerObject().setCard(card);
	}

	@Override
	public ICard getEnergy() {
		return getInnerObject().getEnergy();
	}

	@Override
	public void setEnergy(ICard energy) {
		getInnerObject().setEnergy(energy);
	}

	@Override
	public ICard getBasic() {
		return getInnerObject().getBasic();
	}

	@Override
	public void setBasic(ICard basic) {
		getInnerObject().setBasic(basic);
	}

}