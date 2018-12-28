package org.soen387.dom.model.bench;

import java.util.ArrayList;
import java.util.List;

import org.dsrg.soenea.domain.DomainObject;
import org.soen387.dom.model.cardInPlay.ICardInPlay;
import org.soen387.dom.model.play.IPlay;

public class Bench extends DomainObject<Long> implements IBench{

	IPlay play;
	List<ICardInPlay> cards;
	
	protected Bench(Long id, long version, IPlay play) {
		super(id, version);
		this.play = play;
		this.cards = new ArrayList<ICardInPlay>();
	}
	
	protected Bench(Long id, long version, IPlay play, List<ICardInPlay> cards) {
		super(id, version);
		this.play = play;
		this.cards = cards;
	}

	@Override
	public IPlay getPlay() {
		return play;
	}

	@Override
	public void setPlay(IPlay play) {
		this.play = play;
	}

	@Override
	public List<ICardInPlay> getCardsInPlay() {
		return cards;
	}

	@Override
	public void setCardsInPlay(List<ICardInPlay> cards) {
		this.cards = cards;
	}	

}
