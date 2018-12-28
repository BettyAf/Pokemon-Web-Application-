package org.soen387.dom.model.cardInPlay;

import org.dsrg.soenea.domain.interf.IDomainObject;
import org.soen387.dom.model.card.ICard;
import org.soen387.dom.model.play.IPlay;

public interface ICardInPlay extends IDomainObject<Long> {

	public abstract IPlay getPlay();
	public abstract void setPlay(IPlay play);
	public abstract int getLocation();
	public abstract void setLocation(int location);
	public abstract ICard getCard();
	public abstract void setCard(ICard card);
	public abstract ICard getEnergy();
	public abstract void setEnergy(ICard energy);
	public abstract ICard getBasic();
	public abstract void setBasic(ICard basic);
}
	