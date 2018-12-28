package org.soen387.dom.model.challenge;

import java.sql.SQLException;

import org.dsrg.soenea.domain.DomainObjectCreationException;
import org.dsrg.soenea.domain.MapperException;
import org.dsrg.soenea.domain.proxy.DomainObjectProxy;
import org.soen387.dom.mapper.challenge.ChallengeInputMapper;
import org.soen387.dom.model.deck.IDeck;
import org.soen387.dom.model.player.IPlayer;

public class ChallengeProxy extends DomainObjectProxy<Long, Challenge> implements IChallenge {

	public ChallengeProxy(Long id) {
		super(id);
	}

	@Override
	protected Challenge getFromMapper(Long id) throws MapperException, DomainObjectCreationException {
		try {
			return ChallengeInputMapper.find(id);
		} catch(MapperException | SQLException e) {
			throw new DomainObjectCreationException(e.getMessage(), e);
		}
	}

	@Override
	public IPlayer getChallenger() {
		return getInnerObject().getChallenger();
	}

	@Override
	public void setChallenger(IPlayer challenger) {
		getInnerObject().setChallenger(challenger);
	}

	@Override
	public IPlayer getChallengee() {
		return getInnerObject().getChallengee();
	}

	@Override
	public void setChallengee(IPlayer challengee) {
		getInnerObject().setChallengee(challengee);
	}

	@Override
	public IDeck getChallengerDeck() {
		return getInnerObject().getChallengerDeck();
	}

	@Override
	public void setChallengerDeck(IDeck challengerDeck) {
		getInnerObject().setChallengerDeck(challengerDeck);
	}

	@Override
	public int getStatus() {
		return getInnerObject().getStatus();
	}

	@Override
	public void setStatus(int status) {
		getInnerObject().setStatus(status);
	}

}
