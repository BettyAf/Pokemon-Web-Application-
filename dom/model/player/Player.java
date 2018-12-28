package org.soen387.dom.model.player;

import java.util.ArrayList;
import java.util.List;

import org.dsrg.soenea.domain.DomainObject;
import org.soen387.dom.model.deck.IDeck;
import org.soen387.dom.model.user.IUser;

public class Player extends DomainObject<Long> implements IPlayer {
	
	IUser user;
	List<IDeck> decks;
	
	protected Player(Long id, long version, IUser user) {
		super(id, version);
		this.user = user;
		this.decks = new ArrayList<IDeck>();
	}
	
	protected Player(Long id, long version, IUser user, List<IDeck> decks) {
		super(id, version);
		this.user = user;
		this.decks = decks;
	}

	public IUser getUser() {
		return user;
	}

	public void setUser(IUser user) {
		this.user = user;
	}

	public List<IDeck> getDecks() {
		return decks;
	}

	public void setDecks(List<IDeck> decks) {
		this.decks = decks;
	}

}
