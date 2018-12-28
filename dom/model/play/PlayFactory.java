package org.soen387.dom.model.play;

import java.sql.SQLException;

import org.dsrg.soenea.domain.MapperException;
import org.dsrg.soenea.uow.MissingMappingException;
import org.dsrg.soenea.uow.UoW;
import org.soen387.dom.model.bench.IBench;
import org.soen387.dom.model.deck.IDeck;
import org.soen387.dom.model.discard.IDiscard;
import org.soen387.dom.model.game.IGame;
import org.soen387.dom.model.hand.IHand;
import org.soen387.dom.model.player.IPlayer;
import org.soen387.ser.play.PlayTDG;

public class PlayFactory {

	public static Play createNew(IGame game, IPlayer player, IDeck deck, int status) throws SQLException {
		Play p = new Play(PlayTDG.getMaxId(), 1, game, player, deck, status);
		try {
			UoW.getCurrent().registerNew(p);
		} catch (MissingMappingException | MapperException e) {
			e.printStackTrace();
		} 
		return p;
	}
	
	public static Play createClean(long id, long version, IGame game, IPlayer player, IDeck deck, IHand hand, IDiscard discard, IBench bench, int status) throws SQLException {
		Play p = new Play(id, version, game, player, deck, hand, discard, bench, status);
		UoW.getCurrent().registerClean(p);
		return p;	
	}
	
	public static Play registerDirty(long id, long version, IGame game, IPlayer player, IDeck deck, IHand hand, IDiscard discard, IBench bench, int status) throws SQLException, MissingMappingException, MapperException {
		Play p = new Play(id, version, game, player, deck, hand, discard, bench, status);
		UoW.getCurrent().registerDirty(p);
		return p;	
	}
}
