package org.soen387.dom.command;

import java.sql.SQLException;
import java.util.List;

import org.dsrg.soenea.domain.MapperException;
import org.dsrg.soenea.domain.command.CommandException;
import org.dsrg.soenea.domain.command.impl.ValidatorCommand;
import org.dsrg.soenea.domain.helper.Helper;
import org.soen387.dom.mapper.challenge.ChallengeInputMapper;
import org.soen387.dom.mapper.player.PlayerInputMapper;
import org.soen387.dom.model.challenge.IChallenge;
import org.soen387.dom.model.player.IPlayer;
import org.soen387.dom.model.user.IUser;

public class ListChallengesCommand extends ValidatorCommand {
	
	public ListChallengesCommand(Helper helper) {
		super(helper);
	}

	@Override
	public void process() throws CommandException {
		try {
			IUser user = (IUser) helper.getSessionAttribute("CurrentUser");
			if(user == null) {
				throw new CommandException("Must be logged in to view your challenges.");
			}
			IPlayer player = PlayerInputMapper.find(user.getId());
			List<IChallenge> challenges = ChallengeInputMapper.findAllByPlayer(player);
			helper.setRequestAttribute("challenges", challenges); 
		} catch (SQLException | MapperException e) {
			e.printStackTrace();
			throw new CommandException(e);
		}		
	}

}
