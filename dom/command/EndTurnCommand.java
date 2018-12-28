package org.soen387.dom.command;

import java.sql.SQLException;

import org.dsrg.soenea.domain.MapperException;
import org.dsrg.soenea.domain.command.CommandException;
import org.dsrg.soenea.domain.command.impl.ValidatorCommand;
import org.dsrg.soenea.domain.helper.Helper;
import org.soen387.dom.mapper.game.GameInputMapper;
import org.soen387.dom.mapper.play.PlayInputMapper;
import org.soen387.dom.mapper.player.PlayerInputMapper;
import org.soen387.dom.model.card.ICard;
import org.soen387.dom.model.cardInPlay.CardInPlayFactory;
import org.soen387.dom.model.cardInPlay.ICardInPlay;
import org.soen387.dom.model.game.GameFactory;
import org.soen387.dom.model.game.IGame;
import org.soen387.dom.model.play.IPlay;
import org.soen387.dom.model.play.Play;
import org.soen387.dom.model.player.IPlayer;
import org.soen387.dom.model.user.IUser;
import org.soen387.dom.status.CardLocation;
import org.soen387.dom.status.PlayStatus;

public class EndTurnCommand extends ValidatorCommand {
	
	public EndTurnCommand(Helper helper) {
		super(helper);
	}

	@Override
	public void process() throws CommandException {
		try {
			IUser user = (IUser) helper.getSessionAttribute("CurrentUser");
			if(user == null) {
				throw new CommandException("Must be logged in to end your turn.");
			}
			IPlayer player = PlayerInputMapper.find(user.getId());
			
			String path = (String) helper.getRequestAttribute("path");
			path = path.replaceAll("[a-zA-Z/]+", "");
			long id = Long.parseLong(path);
			
			Long version = helper.getLong("version");
			if(version == null) {
				throw new CommandException("Need to specify a game version.");
			}
			
			IGame game = GameInputMapper.find(id);
			if(game == null) {
				throw new CommandException("Game does not exist.");
			} else if (game.getChallengee().getId() != player.getId() && game.getChallenger().getId() != player.getId()) {
				throw new CommandException("Cannot end your turn in a game you're not playing in.");
			} else if (game.getCurrentPlayer() != player.getId()) {
				throw new CommandException("It is not currently your turn.");
			}
			
			IPlayer waitingPlayer = game.getChallenger().getId() == game.getCurrentPlayer() ? game.getChallengee() : game.getChallenger();
			IPlay currentPlay = PlayInputMapper.findByPlayerAndGame(player, game);
			IPlay waitingPlay = PlayInputMapper.findByPlayerAndGame(waitingPlayer, game);
			if(currentPlay.getStatus() == PlayStatus.retired.ordinal()) {
				throw new CommandException("Cannot end your turn in a game that you retired from.");
			}
			
			if(currentPlay.getHand().getCardsInPlay().size() > 7) {
				ICardInPlay c = findOldestCardInHand(currentPlay);
				CardInPlayFactory.registerDirty(c.getId(), c.getVersion(), currentPlay, CardLocation.Discard.ordinal(), c.getCard(), c.getEnergy(), c.getBasic());
			}
			
			ICard card = Play.getNextCardInPlay(waitingPlay);
			CardInPlayFactory.createNew(waitingPlay, CardLocation.Hand.ordinal(), card);
			
			GameFactory.registerDirty(game.getId(), version, game.getChallenger(), game.getChallengee(), game.getChallengerDeck(), game.getChallengeeDeck(), game.getChallengerPlay(), game.getChallengeePlay(), waitingPlayer.getId());
			
		} catch (SQLException | MapperException e) {
			e.printStackTrace();
			throw new CommandException(e);
		}		
	}

	private static ICardInPlay findOldestCardInHand(IPlay play) {
		ICardInPlay oldest = play.getHand().getCardsInPlay().get(0);
		for(ICardInPlay c : play.getHand().getCardsInPlay()) {
			if(c.getCard().getId() < oldest.getCard().getId()) {
				oldest = c;
			}
		}
		return oldest;
	}
}
