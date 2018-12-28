package org.soen387.dom.model.deck;

import java.sql.SQLException;
import java.util.List;

import org.dsrg.soenea.domain.MapperException;
import org.dsrg.soenea.uow.MissingMappingException;
import org.dsrg.soenea.uow.UoW;
import org.soen387.dom.model.card.ICard;
import org.soen387.dom.model.player.IPlayer;
import org.soen387.ser.deck.DeckTDG;

public class DeckFactory {
	
	public static Deck createNew(IPlayer player) throws SQLException {
		Deck d = new Deck(DeckTDG.getMaxId(), 1, player);
		try {
			UoW.getCurrent().registerNew(d);
		} catch (MissingMappingException | MapperException e) {
			e.printStackTrace();
		} 
		return d;
	}
	
	public static Deck createClean(long id, long version, IPlayer player, List<ICard> cards) throws SQLException {
		Deck d = new Deck(id, version, player, cards);
		UoW.getCurrent().registerClean(d);
		return d;	
	}
}
