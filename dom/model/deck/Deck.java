package org.soen387.dom.model.deck;

import java.util.ArrayList;
import java.util.List;

import org.dsrg.soenea.domain.DomainObject;
import org.soen387.dom.model.card.ICard;
import org.soen387.dom.model.player.IPlayer;

public class Deck extends DomainObject<Long> implements IDeck {
	
	IPlayer player;
	List<ICard> cards;
	
	protected Deck(Long id, long version, IPlayer player) {
		super(id, version);
		this.player = player;
		this.cards = new ArrayList<ICard>();
	}
	
	protected Deck(Long id, long version, IPlayer player, List<ICard> cards) {
		super(id, version);
		this.player = player;
		this.cards = cards;
	}

	@Override
	public IPlayer getPlayer() {
		return this.player;
	}

	@Override
	public void setPlayer(IPlayer player) {
		this.player = player;
	}

	@Override
	public List<ICard> getCards() {
		return cards;
	}

	@Override
	public void setCards(List<ICard> cards) {
		this.cards = cards;
	}
	
	

}
