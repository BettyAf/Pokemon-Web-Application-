package org.soen387.dom.model.cardInPlay;

import java.sql.SQLException;

import org.dsrg.soenea.domain.MapperException;
import org.dsrg.soenea.uow.MissingMappingException;
import org.dsrg.soenea.uow.UoW;
import org.soen387.dom.model.card.ICard;
import org.soen387.dom.model.play.IPlay;
import org.soen387.ser.playCard.PlayCardTDG;

public class CardInPlayFactory {

	public static CardInPlay createNew(IPlay play, int location, ICard card) throws SQLException {
		CardInPlay c = new CardInPlay(PlayCardTDG.getMaxId(), 1, play, location, card, null, null);
		try {
			UoW.getCurrent().registerNew(c);
		} catch (MissingMappingException | MapperException e) {
			e.printStackTrace();
		} 
		return c;
	}
	
	public static CardInPlay createClean(long id, long version, IPlay play, int location, ICard card, ICard energy, ICard basic) throws SQLException {
		CardInPlay c = new CardInPlay(id, version, play, location, card, energy, basic);
		UoW.getCurrent().registerClean(c);
		return c;	
	}
	
	public static CardInPlay registerDirty(long id, long version, IPlay play, int location, ICard card, ICard energy, ICard basic) throws SQLException, MissingMappingException, MapperException {
		CardInPlay c = new CardInPlay(id, version, play, location, card, energy, basic);
		UoW.getCurrent().registerDirty(c);
		return c;	
	}
}
