package org.soen387.dom.model.card;

import org.dsrg.soenea.domain.DomainObject;
import org.soen387.dom.model.deck.IDeck;

public class Card extends DomainObject<Long> implements ICard {
	
	String type;
	String name;
	String basic;
	IDeck deck;
	
	public Card(Long id, long version, String type, String name, String basic, IDeck deck) {
		super(id, version);
		this.type = type;
		this.name = name;
		this.basic = basic;
		this.deck = deck;
	}

	@Override
	public String getType() {
		return type;
	}

	@Override
	public void setType(String type) {
		this.type = type;
		
	}

	@Override
	public String getName() {
		return name;
	}

	@Override
	public void setName(String name) {
		this.name = name;
		
	}

	@Override
	public String getBasic() {
		return basic;
	}

	@Override
	public void setBasic(String basic) {
		this.basic = basic;
		
	}

	@Override
	public IDeck getDeck() {
		return deck;
	}

	@Override
	public void setDeck(IDeck deck) {
		this.deck = deck;
	}
}
