package org.soen387.dom.command;

import java.sql.SQLException;

import org.dsrg.soenea.domain.MapperException;
import org.dsrg.soenea.domain.command.CommandException;
import org.dsrg.soenea.domain.command.impl.ValidatorCommand;
import org.dsrg.soenea.domain.helper.Helper;
import org.soen387.dom.mapper.challenge.ChallengeInputMapper;
import org.soen387.dom.mapper.player.PlayerInputMapper;
import org.soen387.dom.model.challenge.Challenge;
import org.soen387.dom.model.challenge.ChallengeFactory;
import org.soen387.dom.model.challenge.IChallenge;
import org.soen387.dom.model.player.IPlayer;
import org.soen387.dom.model.user.IUser;
import org.soen387.dom.status.ChallengeStatus;

public class RefuseChallengeCommand extends ValidatorCommand {
	
	public RefuseChallengeCommand(Helper helper) {
		super(helper);
	}

	@Override
	public void process() throws CommandException {
		try {
			
			IUser user = (IUser) helper.getSessionAttribute("CurrentUser");
			if(user == null) {
				throw new CommandException("Must be logged in to refuse a challenge.");
			}
			
			IPlayer player = PlayerInputMapper.find(user.getId()); 
			
			Long version = helper.getLong("version");
			if(version == null) {
				throw new CommandException("Need to specify a challenge version.");
			}
		
			String path = (String) helper.getRequestAttribute("path");
			path = path.replaceAll("[a-zA-Z/]+", "");
			long id = Long.parseLong(path);
			
			IChallenge challenge = ChallengeInputMapper.find(id);
			if(challenge == null) {
				throw new CommandException("Challenge does not exist.");
			} else if (challenge.getChallengee().getId() != player.getId()) {
				throw new CommandException("Cannot refuse a challenge that was not issued to you.");
			} else if (challenge.getStatus() != ChallengeStatus.Open.ordinal()) {
				throw new CommandException("Cannot refuse a challenge that is not open.");
			}
								
			Challenge c = ChallengeFactory.registerDirty(challenge.getId(), version, challenge.getChallenger(), challenge.getChallengee(), challenge.getChallengerDeck(), ChallengeStatus.Refused.ordinal());
			
		} catch (SQLException | MapperException e) {
			e.printStackTrace();
			throw new CommandException(e);
		}		
	}

}
