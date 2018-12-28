package org.soen387.dom.command;

import java.sql.SQLException;

import org.dsrg.soenea.domain.MapperException;
import org.dsrg.soenea.domain.command.CommandException;
import org.dsrg.soenea.domain.command.impl.ValidatorCommand;
import org.dsrg.soenea.domain.helper.Helper;
import org.dsrg.soenea.uow.MissingMappingException;
import org.soen387.dom.mapper.card.CardInputMapper;
import org.soen387.dom.mapper.game.GameInputMapper;
import org.soen387.dom.mapper.play.PlayInputMapper;
import org.soen387.dom.mapper.player.PlayerInputMapper;
import org.soen387.dom.model.card.ICard;
import org.soen387.dom.model.cardInPlay.CardInPlayFactory;
import org.soen387.dom.model.cardInPlay.ICardInPlay;
import org.soen387.dom.model.game.GameFactory;
import org.soen387.dom.model.game.IGame;
import org.soen387.dom.model.play.IPlay;
import org.soen387.dom.model.player.IPlayer;
import org.soen387.dom.model.user.IUser;
import org.soen387.dom.status.CardLocation;

public class PlayCardCommand extends ValidatorCommand {
	
	public PlayCardCommand(Helper helper) {
		super(helper);
	}

	@Override
	public void process() throws CommandException {
		try {
						
			// Check user is logged in
			IUser user = (IUser) helper.getSessionAttribute("CurrentUser");
			if(user == null) {
				throw new CommandException("Must be logged in to play a card.");
			}
			IPlayer player = PlayerInputMapper.find(user.getId());
						
			// Check that a version of the game was included.
			Long version = helper.getLong("version");
			if(version == null) {
				throw new CommandException("Need to specify a game version.");
			}
			
			// Retrieve the in-url parameters
			String path = (String) helper.getRequestAttribute("path");
			path = path.replaceAll("(/[a-zA-Z]+)*", "");
			// �\_(@.@)_/�
			long gameId = Long.parseLong(path.substring(1, path.indexOf("/", 1)));
			long cardId = Long.parseLong(path.substring(path.indexOf("/", 1)+1));
			
			// Check that the game exists, and user is part of the game, and that its their turn.
			IGame game = GameInputMapper.find(gameId);
			if(game == null) {
				throw new CommandException("Game does not exist.");
			} else if (game.getChallengee().getId() != player.getId() && game.getChallenger().getId() != player.getId()) {
				throw new CommandException("Cannot play a card in a game you're not playing in.");
			} else if (game.getCurrentPlayer() != player.getId()) {
				throw new CommandException("It is not currently your turn.");
			}
			
			// Check that the card exists
			ICard card = CardInputMapper.find(cardId);
			if(card == null) {
				throw new CommandException("Card does not exist.");
			} 
			
			// Check that card is in hand
			IPlay play = PlayInputMapper.findByPlayerAndGame(player, game);
			boolean cardInHand = false;
			ICardInPlay cardToPlay = null;
			for(ICardInPlay c : play.getHand().getCardsInPlay()) {
				if(c.getCard().getId() == cardId) {
					cardInHand = true;
					cardToPlay = c;
				}
			}
			if(!cardInHand) {
				throw new CommandException("Card is not in player hand.");
			}
			
			// Filter the card types into the different plays.
			if(card.getType().equals("e")) {
				Long pokemonId = helper.getLong("pokemon");
				if(pokemonId != null) {
					playEnergy(cardToPlay, play, pokemonId);
				} else {
					throw new CommandException("Missing the pokemon in which to play the energy card on.");
				}
			} else if(card.getType().equals("t")) {
				playTrainer(cardToPlay, play);
			} else if(card.getType().equals("p")) {
				String basicId = helper.getString("basic");
				if(basicId == null) {
					playToBench(cardToPlay, play);
				} else {
					evolvePokemon(cardToPlay, play, Long.parseLong(basicId));
				}
			}	
			
			// Update game version at the end. 
			GameFactory.registerDirty(game.getId(),version, game.getChallenger(), game.getChallengee(), game.getChallengerDeck(), game.getChallengeeDeck(), game.getChallengerPlay(), game.getChallengeePlay(), game.getCurrentPlayer());
			
		} catch (SQLException | MapperException e) {
			e.printStackTrace();
			throw new CommandException(e);
		}		
	}
	
	private static void playToBench(ICardInPlay cardToPlay, IPlay play) throws MissingMappingException, SQLException, MapperException, CommandException {
		if(!cardToPlay.getCard().getBasic().isEmpty()) {
			throw new CommandException("Card is not a basic pokemon.");
		} else {
			CardInPlayFactory.registerDirty(cardToPlay.getId(), cardToPlay.getVersion(), play, CardLocation.Bench.ordinal(), cardToPlay.getCard(), cardToPlay.getEnergy(), cardToPlay.getBasic());
		}
	}
	
	private static void playTrainer(ICardInPlay cardToPlay, IPlay play) throws MissingMappingException, SQLException, MapperException {
		CardInPlayFactory.registerDirty(cardToPlay.getId(), cardToPlay.getVersion(), play, CardLocation.Discard.ordinal(), cardToPlay.getCard(), cardToPlay.getEnergy(), cardToPlay.getBasic());
	}
	
	private static void playEnergy(ICardInPlay cardToPlay, IPlay play, long pokemonId) throws MissingMappingException, SQLException, MapperException, CommandException {
		ICard pokemonCard = CardInputMapper.find(pokemonId);
		if(pokemonCard == null) {
			throw new CommandException("Card does not exist.");
		} else if (!pokemonCard.getType().equals("p")) {
			throw new CommandException("Card is not a pokemon card.");
		}
		boolean cardInBench = false;
		for(ICardInPlay c : play.getBench().getCardsInPlay()) {
			if(c.getCard().getId() == cardToPlay.getCard().getId()) {
				cardInBench = true;
			}
		}
		if(!cardInBench) {
			throw new CommandException("Card is not on the bench.");
		}
		
		// Play energy
	}
	
	private static void evolvePokemon(ICardInPlay cardToPlay, IPlay play, long basicId) throws MissingMappingException, SQLException, MapperException, CommandException {
		boolean cardInBench = false;
		ICardInPlay cardOnBench= null;
		for(ICardInPlay c : play.getBench().getCardsInPlay()) {
			if(c.getCard().getId() == basicId) {
				cardInBench = true;
				cardOnBench = c;
			}
		}
		if(!cardInBench) {
			throw new CommandException("Card is not on the bench.");
		} else if(!cardToPlay.getCard().getBasic().equals(cardOnBench.getCard().getName())) {
			throw new CommandException("Card is not the basic of this stage one pokemon.");
		}
		
		CardInPlayFactory.registerDirty(cardOnBench.getId(), cardOnBench.getVersion(), play, CardLocation.Basic.ordinal(), cardOnBench.getCard(), cardOnBench.getEnergy(), cardOnBench.getBasic());
		CardInPlayFactory.registerDirty(cardToPlay.getId(), cardToPlay.getVersion(), play, CardLocation.Bench.ordinal(), cardToPlay.getCard(), cardOnBench.getEnergy(), cardOnBench.getCard());
	}

}
