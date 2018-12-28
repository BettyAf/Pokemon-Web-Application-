package org.soen387.dom.model.game;

import java.sql.SQLException;

import org.dsrg.soenea.domain.MapperException;
import org.dsrg.soenea.uow.MissingMappingException;
import org.dsrg.soenea.uow.UoW;
import org.soen387.dom.model.deck.IDeck;
import org.soen387.dom.model.play.IPlay;
import org.soen387.dom.model.player.IPlayer;
import org.soen387.ser.game.GameTDG;

public class GameFactory {
	
	public static Game createNew(IPlayer challenger, IPlayer challengee, IDeck challengerDeck, IDeck challengeeDeck, long currentPlayer) throws SQLException {
		Game g = new Game(GameTDG.getMaxId(), 1, challenger, challengee, challengerDeck, challengeeDeck, currentPlayer);
		try {
			UoW.getCurrent().registerNew(g);
		} catch (MissingMappingException | MapperException e) {
			e.printStackTrace();
		} 
		return g;
	}
	
	public static Game createClean(long id, long version, IPlayer challenger, IPlayer challengee, IDeck challengerDeck, IDeck challengeeDeck, IPlay challengerPlay, IPlay challengeePlay, long currentPlayer) throws SQLException {
		Game g = new Game(id, version, challenger, challengee, challengerDeck, challengeeDeck, challengerPlay, challengeePlay, currentPlayer);
		UoW.getCurrent().registerClean(g);
		return g;	
	}
	
	public static Game registerDirty(long id, long version, IPlayer challenger, IPlayer challengee, IDeck challengerDeck, IDeck challengeeDeck, IPlay challengerPlay, IPlay challengeePlay, long currentPlayer) throws SQLException, MissingMappingException, MapperException {
		Game g = new Game(id, version, challenger, challengee, challengerDeck, challengeeDeck, challengerPlay, challengeePlay, currentPlayer);
		UoW.getCurrent().registerDirty(g);
		return g;	
	}

}
