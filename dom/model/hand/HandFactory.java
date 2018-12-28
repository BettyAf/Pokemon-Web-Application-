package org.soen387.dom.model.hand;

import java.sql.SQLException;
import java.util.List;

import org.dsrg.soenea.domain.MapperException;
import org.dsrg.soenea.uow.MissingMappingException;
import org.dsrg.soenea.uow.UoW;
import org.soen387.dom.model.cardInPlay.ICardInPlay;
import org.soen387.dom.model.play.IPlay;
import org.soen387.ser.hand.HandTDG;

public class HandFactory {
	
	public static Hand createNew(IPlay play) throws SQLException {
		Hand h = new Hand(HandTDG.getMaxId(), 1, play);
		try {
			UoW.getCurrent().registerNew(h);
		} catch (MissingMappingException | MapperException e) {
			e.printStackTrace();
		} 
		return h;
	}
	
	public static Hand createClean(long id, long version, IPlay play, List<ICardInPlay> cards) throws SQLException {
		Hand h = new Hand(id, version, play, cards);
		UoW.getCurrent().registerClean(h);
		return h;	
	}

}
