package org.soen387.dom.model.challenge;

import java.sql.SQLException;

import org.dsrg.soenea.domain.MapperException;
import org.dsrg.soenea.uow.MissingMappingException;
import org.dsrg.soenea.uow.UoW;
import org.soen387.dom.model.deck.IDeck;
import org.soen387.dom.model.player.IPlayer;
import org.soen387.ser.challenge.ChallengeTDG;

public class ChallengeFactory {
	
	public static Challenge createNew(IPlayer challenger, IPlayer challengee, IDeck challengerDeck, int status) throws SQLException {
		Challenge c = new Challenge(ChallengeTDG.getMaxId(), 1, challenger, challengee, challengerDeck, status);
		try {
			UoW.getCurrent().registerNew(c);
		} catch (MissingMappingException | MapperException e) {
			e.printStackTrace();
		} 
		return c;
	}
	
	public static Challenge createClean(long id, long version, IPlayer challenger, IPlayer challengee, IDeck challengerDeck, int status) throws SQLException {
		Challenge c = new Challenge(id, version, challenger, challengee, challengerDeck, status);
		UoW.getCurrent().registerClean(c);
		return c;	
	}
	
	public static Challenge registerDirty(long id, long version, IPlayer challenger, IPlayer challengee, IDeck challengerDeck, int status) throws SQLException, MissingMappingException, MapperException {
		Challenge c = new Challenge(id, version, challenger, challengee, challengerDeck, status);
		UoW.getCurrent().registerDirty(c);
		return c;	
	}

}
