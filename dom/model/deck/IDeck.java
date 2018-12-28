package org.soen387.dom.model.deck;

import java.util.List;

import org.dsrg.soenea.domain.interf.IDomainObject;
import org.soen387.dom.model.card.ICard;
import org.soen387.dom.model.player.IPlayer;

public interface IDeck extends IDomainObject<Long>{
	
	public abstract IPlayer getPlayer();
	public abstract void setPlayer(IPlayer player);
	public abstract List<ICard> getCards();
	public abstract void setCards(List<ICard> cards);

}
