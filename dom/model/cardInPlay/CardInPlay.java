package org.soen387.dom.model.cardInPlay;

import org.dsrg.soenea.domain.DomainObject;
import org.soen387.dom.model.card.ICard;
import org.soen387.dom.model.play.IPlay;

public class CardInPlay extends DomainObject<Long> implements ICardInPlay {
	
	IPlay play;
	int location;
	ICard card;
	ICard energy;
	ICard basic;
	
	public CardInPlay(Long id, long version, IPlay play, int location, ICard card) {
		super(id, version);
		this.play = play;
		this.location = location;
		this.card = card;
	}
	
	public CardInPlay(Long id, long version, IPlay play, int location, ICard card, ICard energy, ICard basic) {
		super(id, version);
		this.play = play;
		this.location = location;
		this.card = card;
		this.energy = energy;
		this.basic = basic;
	}

	@Override
	public IPlay getPlay() {
		return this.play;
	}

	@Override
	public void setPlay(IPlay play) {
		this.play = play;
	}

	@Override
	public int getLocation() {
		return location;
	}

	@Override
	public void setLocation(int location) {
		this.location = location;
	}

	@Override
	public ICard getCard() {
		return card;
	}

	@Override
	public void setCard(ICard card) {
		this.card = card;
	}

	@Override
	public ICard getEnergy() {
		return energy;
	}

	@Override
	public void setEnergy(ICard energy) {
		this.energy = energy;
	}

	@Override
	public ICard getBasic() {
		return basic;
	}

	@Override
	public void setBasic(ICard basic) {
		this.basic = basic;
	}

}
