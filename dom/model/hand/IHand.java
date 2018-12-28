package org.soen387.dom.model.hand;

import java.util.List;

import org.dsrg.soenea.domain.interf.IDomainObject;
import org.soen387.dom.model.cardInPlay.ICardInPlay;
import org.soen387.dom.model.play.IPlay;

public interface IHand extends IDomainObject<Long> {
	
	public abstract IPlay getPlay();
	public abstract void setPlay(IPlay play);
	public abstract List<ICardInPlay> getCardsInPlay();
	public abstract void setCardsInPlay(List<ICardInPlay> cards);

}
