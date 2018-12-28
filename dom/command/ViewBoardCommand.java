package org.soen387.dom.command;

import java.sql.SQLException;

import org.dsrg.soenea.domain.MapperException;
import org.dsrg.soenea.domain.command.CommandException;
import org.dsrg.soenea.domain.command.impl.ValidatorCommand;
import org.dsrg.soenea.domain.helper.Helper;
import org.soen387.dom.mapper.game.GameInputMapper;
import org.soen387.dom.model.game.IGame;
import org.soen387.dom.model.play.IPlay;
import org.soen387.dom.model.play.Play;
import org.soen387.dom.model.user.IUser;
import org.soen387.dom.status.PlayStatus;

public class ViewBoardCommand extends ValidatorCommand {

	public ViewBoardCommand(Helper helper) {
		super(helper);
	}

	@Override
	public void process() throws CommandException {
		try {
			IUser user = (IUser) helper.getSessionAttribute("CurrentUser");
			if(user == null) {
				throw new CommandException("Must be logged in to view your game.");
			}
			String path = (String) helper.getRequestAttribute("path");
			path = path.replaceAll("[a-zA-Z/]+", "");
			long id = Long.parseLong(path);
			IGame game = GameInputMapper.find(id);
			
			if(game.getChallenger().getId() != user.getId() && game.getChallengee().getId() != user.getId()) {
				throw new CommandException("This is not your game.");
			}
			prepareBoardRequestAttributes(game);
		} catch (SQLException | MapperException e) {
			e.printStackTrace();
			throw new CommandException(e);
		}		
	}
	
	private void prepareBoardRequestAttributes(IGame game) {
		helper.setRequestAttribute("game", game);
		
		IPlay waitingPlay = getWaitingPlay(game);
		helper.setRequestAttribute("waitingPlay", waitingPlay);
		helper.setRequestAttribute("waitingStatus", PlayStatus.values()[waitingPlay.getStatus()]);
		helper.setRequestAttribute("waitingHand", waitingPlay.getHand().getCardsInPlay().size());
		helper.setRequestAttribute("waitingDiscard", waitingPlay.getDiscard().getCardsInPlay().size());
		helper.setRequestAttribute("waitingBench", waitingPlay.getBench().getCardsInPlay());
		helper.setRequestAttribute("waitingDeck", Play.getDeckSize(waitingPlay));
		
		IPlay currentPlay = getCurrentPlay(game);
		helper.setRequestAttribute("currentPlay", currentPlay);
		helper.setRequestAttribute("currentStatus", PlayStatus.values()[currentPlay.getStatus()]);
		helper.setRequestAttribute("currentHand", currentPlay.getHand().getCardsInPlay().size());
		helper.setRequestAttribute("currentDiscard", currentPlay.getDiscard().getCardsInPlay().size());
		helper.setRequestAttribute("currentBench", currentPlay.getBench().getCardsInPlay());
		helper.setRequestAttribute("currentDeck", Play.getDeckSize(currentPlay));
	}
	
	private IPlay getCurrentPlay(IGame game) {
		return game.getChallenger().getId() == game.getCurrentPlayer() ? game.getChallengerPlay() : game.getChallengeePlay();
	}
	
	private IPlay getWaitingPlay(IGame game) {
		return game.getChallenger().getId() == game.getCurrentPlayer() ? game.getChallengeePlay() : game.getChallengerPlay();
	}
}
