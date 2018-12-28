package org.soen387.dom.model.player;

import java.sql.SQLException;
import java.util.List;

import org.dsrg.soenea.domain.DomainObjectCreationException;
import org.dsrg.soenea.domain.MapperException;
import org.dsrg.soenea.domain.proxy.DomainObjectProxy;
import org.soen387.dom.mapper.player.PlayerInputMapper;
import org.soen387.dom.model.deck.IDeck;
import org.soen387.dom.model.user.IUser;

public class PlayerProxy extends DomainObjectProxy<Long, Player> implements IPlayer{

	public PlayerProxy(long id) {
		super(id);
	}
	
	@Override
	protected Player getFromMapper(Long id) throws MapperException, DomainObjectCreationException {
		try {
			return PlayerInputMapper.find(id);
		} catch(MapperException | SQLException e) {
			throw new DomainObjectCreationException(e.getMessage(), e);
		}
	}

	@Override
	public IUser getUser() {
		return getInnerObject().getUser();
	}

	@Override
	public void setUser(IUser user) {
		getInnerObject().setUser(user);
	}

	@Override
	public List<IDeck> getDecks() {
		return getInnerObject().getDecks();
	}

	@Override
	public void setDecks(List<IDeck> decks) {
		getInnerObject().setDecks(decks);
	}

}
