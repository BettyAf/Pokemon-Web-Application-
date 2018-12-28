package org.soen387.dom.model.discard;

import java.sql.SQLException;
import java.util.List;

import org.dsrg.soenea.domain.MapperException;
import org.dsrg.soenea.uow.MissingMappingException;
import org.dsrg.soenea.uow.UoW;
import org.soen387.dom.model.cardInPlay.ICardInPlay;
import org.soen387.dom.model.play.IPlay;
import org.soen387.ser.discard.DiscardTDG;

public class DiscardFactory {
	
	public static Discard createNew(IPlay play) throws SQLException {
		Discard h = new Discard(DiscardTDG.getMaxId(), 1, play);
		try {
			UoW.getCurrent().registerNew(h);
		} catch (MissingMappingException | MapperException e) {
			e.printStackTrace();
		} 
		return h;
	}
	
	public static Discard createClean(long id, long version, IPlay play, List<ICardInPlay> cards) throws SQLException {
		Discard h = new Discard(id, version, play, cards);
		UoW.getCurrent().registerClean(h);
		return h;	
	}

}
