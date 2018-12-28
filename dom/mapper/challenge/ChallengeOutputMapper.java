package org.soen387.dom.mapper.challenge;

import java.sql.SQLException;

import org.dsrg.soenea.domain.MapperException;
import org.dsrg.soenea.domain.mapper.GenericOutputMapper;
import org.dsrg.soenea.domain.mapper.LostUpdateException;
import org.soen387.dom.model.challenge.Challenge;
import org.soen387.ser.challenge.ChallengeTDG;

public class ChallengeOutputMapper extends GenericOutputMapper<Long, Challenge> {

	@Override
	public void insert(Challenge c) throws MapperException {
		try {
			ChallengeTDG.insert(c.getId(), c.getVersion(), c.getChallenger().getId(), c.getChallengee().getId(), c.getChallengerDeck().getId(), c.getStatus());
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void update(Challenge c) throws MapperException {
		try {
			int count = ChallengeTDG.update(c.getId(), c.getVersion(), c.getChallenger().getId(), c.getChallengee().getId(), c.getChallengerDeck().getId(), c.getStatus());
			if(count == 0) {
				throw new LostUpdateException("Could not update: Challenge with id " + c.getId() + " and version " + c.getVersion() + " could not be found.");
			} else {
				c.setVersion(c.getVersion() + 1);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void delete(Challenge c) throws MapperException {
		try {
			int count = ChallengeTDG.delete(c.getId(), c.getVersion());
			if(count == 0) {
				throw new LostUpdateException("Could not delete: Challenge with id " + c.getId() + " and version " + c.getVersion() + " could not be found.");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
