package org.soen387.dom.mapper.game;

import java.sql.SQLException;

import org.dsrg.soenea.domain.MapperException;
import org.dsrg.soenea.domain.mapper.GenericOutputMapper;
import org.dsrg.soenea.domain.mapper.LostUpdateException;
import org.soen387.dom.model.game.Game;
import org.soen387.ser.game.GameTDG;

public class GameOutputMapper extends GenericOutputMapper<Long, Game> {

	@Override
	public void insert(Game g) throws MapperException {
		try {
			GameTDG.insert(g.getId(), g.getVersion(), g.getChallenger().getId(), g.getChallengee().getId(), g.getChallengerDeck().getId(), g.getChallengeeDeck().getId(), g.getChallengerPlay().getId(), g.getChallengeePlay().getId(), g.getCurrentPlayer());
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void update(Game g) throws MapperException {
		try {
			int count = GameTDG.update(g.getId(), g.getVersion(), g.getChallenger().getId(), g.getChallengee().getId(), g.getChallengerDeck().getId(), g.getChallengeeDeck().getId(), g.getChallengerPlay().getId(), g.getChallengeePlay().getId(), g.getCurrentPlayer());
			if(count == 0) {
				throw new LostUpdateException("Could not update: Game with id " + g.getId() + " and version " + g.getVersion() + " could not be found.");
			} else {
				g.setVersion(g.getVersion() + 1);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void delete(Game g) throws MapperException {
		try {
			int count = GameTDG.delete(g.getId(), g.getVersion());
			if(count == 0) {
				throw new LostUpdateException("Could not delete: Game with id " + g.getId() + " and version " + g.getVersion() + " could not be found.");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
