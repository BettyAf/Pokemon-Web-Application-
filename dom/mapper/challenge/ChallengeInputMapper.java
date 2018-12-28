package org.soen387.dom.mapper.challenge;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.dsrg.soenea.domain.DomainObjectCreationException;
import org.dsrg.soenea.domain.MapperException;
import org.dsrg.soenea.domain.mapper.DomainObjectNotFoundException;
import org.dsrg.soenea.domain.mapper.IdentityMap;
import org.soen387.dom.model.challenge.Challenge;
import org.soen387.dom.model.challenge.ChallengeFactory;
import org.soen387.dom.model.challenge.ChallengeProxy;
import org.soen387.dom.model.challenge.IChallenge;
import org.soen387.dom.model.deck.DeckProxy;
import org.soen387.dom.model.player.IPlayer;
import org.soen387.dom.model.player.PlayerProxy;
import org.soen387.ser.challenge.ChallengeFinder;

public class ChallengeInputMapper {
	
	public static Challenge find(long id) throws SQLException, MapperException, DomainObjectCreationException {
		try {
			return IdentityMap.get(id, Challenge.class);
		} catch(DomainObjectNotFoundException e) {
			// Just did not find in IdentityMap.
		}
		
		ResultSet rs = ChallengeFinder.find(id);
		if(!rs.next()) {
			throw new MapperException("Challenge with this id does not exist.");
		} 
		return getChallenge(rs);
	}
	
	public static List<IChallenge> findByChallengerAndChallengee(IPlayer challenger, IPlayer challengee) throws SQLException, MapperException, DomainObjectCreationException {
		ResultSet rs = ChallengeFinder.findByChallengerAndChallengee(challenger.getId(), challengee.getId());
		return getChallengeList(rs);
	}
	
	public static List<IChallenge> findAllByPlayer(IPlayer player) throws SQLException, MapperException, DomainObjectCreationException {
		ResultSet rs = ChallengeFinder.findAllByPlayer(player.getId());
		return getChallengeList(rs);
	}
	
	private static Challenge getChallenge(ResultSet rs) throws SQLException, MapperException, DomainObjectCreationException {
		long id = rs.getLong("id");
		Challenge c = ChallengeFactory.createClean(id, rs.getLong("version"), new PlayerProxy(rs.getLong("challenger")), new PlayerProxy(rs.getLong("challengee")), new DeckProxy(rs.getLong("challengerdeck")), rs.getInt("status"));
		return c;
	}
	
	private static List<IChallenge> getChallengeList(ResultSet rs) throws SQLException, MapperException, DomainObjectCreationException {
		List<IChallenge> challenges = new ArrayList<IChallenge>();
		while(rs.next()) {
			challenges.add(new ChallengeProxy(rs.getLong("id")));
		}
		return challenges; 
	}


}
