package org.soen387.dom.model.player;
import java.util.List;

import org.dsrg.soenea.domain.interf.IDomainObject;
import org.soen387.dom.model.deck.IDeck;
import org.soen387.dom.model.user.IUser;

public interface IPlayer extends IDomainObject<Long> {

	public abstract IUser getUser();
	public abstract void setUser(IUser user);
	public abstract List<IDeck> getDecks();
	public abstract void setDecks(List<IDeck> decks);
	
}
