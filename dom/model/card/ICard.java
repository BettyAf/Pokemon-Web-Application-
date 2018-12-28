package org.soen387.dom.model.card;

import org.dsrg.soenea.domain.interf.IDomainObject;
import org.soen387.dom.model.deck.IDeck;

public interface ICard extends IDomainObject<Long> {

	public abstract String getType();
	public abstract void setType(String type);
	public abstract String getName();
	public abstract void setName(String name);
	public abstract String getBasic();
	public abstract void setBasic(String basic);
	public abstract IDeck getDeck();
	public abstract void setDeck(IDeck deck);
}
	