package org.soen387.app;

import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.dsrg.soenea.application.servlet.dispatcher.Dispatcher;
import org.dsrg.soenea.application.servlet.impl.SmartDispatcherServlet;
import org.dsrg.soenea.uow.MapperFactory;
import org.dsrg.soenea.uow.UoW;
import org.soen387.app.dispatcher.board.ViewBoard;
import org.soen387.app.dispatcher.board.ViewDiscardPile;
import org.soen387.app.dispatcher.challenge.AcceptChallenge;
import org.soen387.app.dispatcher.challenge.ChallengePlayer;
import org.soen387.app.dispatcher.challenge.ListChallenges;
import org.soen387.app.dispatcher.challenge.RefuseChallenge;
import org.soen387.app.dispatcher.challenge.WithdrawChallenge;
import org.soen387.app.dispatcher.deck.UploadDeck;
import org.soen387.app.dispatcher.deck.ViewDeck;
import org.soen387.app.dispatcher.deck.ViewDecks;
import org.soen387.app.dispatcher.game.End;
import org.soen387.app.dispatcher.game.ListGames;
import org.soen387.app.dispatcher.game.Retire;
import org.soen387.app.dispatcher.hand.ViewHand;
import org.soen387.app.dispatcher.player.ListPlayers;
import org.soen387.app.dispatcher.player.PlayCard;
import org.soen387.app.dispatcher.user.Login;
import org.soen387.app.dispatcher.user.Logout;
import org.soen387.app.dispatcher.user.Register;
import org.soen387.dom.mapper.bench.BenchOutputMapper;
import org.soen387.dom.mapper.card.CardOutputMapper;
import org.soen387.dom.mapper.cardInPlay.CardInPlayOutputMapper;
import org.soen387.dom.mapper.challenge.ChallengeOutputMapper;
import org.soen387.dom.mapper.deck.DeckOutputMapper;
import org.soen387.dom.mapper.discard.DiscardOutputMapper;
import org.soen387.dom.mapper.game.GameOutputMapper;
import org.soen387.dom.mapper.hand.HandOutputMapper;
import org.soen387.dom.mapper.play.PlayOutputMapper;
import org.soen387.dom.mapper.player.PlayerOutputMapper;
import org.soen387.dom.mapper.user.UserOutputMapper;
import org.soen387.dom.model.bench.Bench;
import org.soen387.dom.model.card.Card;
import org.soen387.dom.model.cardInPlay.CardInPlay;
import org.soen387.dom.model.challenge.Challenge;
import org.soen387.dom.model.deck.Deck;
import org.soen387.dom.model.discard.Discard;
import org.soen387.dom.model.game.Game;
import org.soen387.dom.model.hand.Hand;
import org.soen387.dom.model.play.Play;
import org.soen387.dom.model.player.Player;
import org.soen387.dom.model.user.User;

/*
 * FrontController: https://users.encs.concordia.ca/~sthiel/soen387/Thesis_Stuart.pdf
 * [November 16th]
 * */

/**
 * Servlet implementation class FrontController
 */
@WebServlet("/")
public class PokeFC extends SmartDispatcherServlet {
	private static final long serialVersionUID = 1L;
	
	public PokeFC() {
		super();
	}

    @Override
    public void init(ServletConfig config) throws ServletException {
    	super.init(config);
    	
    	try {
			Class.forName("com.mysql.cj.jdbc.Driver");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
    	super.prepareDbRegistry("");
    	setUpUoW();
    }
    
    public static void setUpUoW() {
    	MapperFactory mapper = new MapperFactory();
    	mapper.addMapping(User.class, UserOutputMapper.class);
    	mapper.addMapping(Player.class, PlayerOutputMapper.class);
    	mapper.addMapping(Card.class, CardOutputMapper.class);
    	mapper.addMapping(Deck.class, DeckOutputMapper.class);
    	mapper.addMapping(Challenge.class, ChallengeOutputMapper.class);
    	mapper.addMapping(Bench.class, BenchOutputMapper.class);
    	mapper.addMapping(Discard.class, DiscardOutputMapper.class);
    	mapper.addMapping(Hand.class, HandOutputMapper.class);
    	mapper.addMapping(Play.class, PlayOutputMapper.class);
    	mapper.addMapping(CardInPlay.class, CardInPlayOutputMapper.class);
    	mapper.addMapping(Game.class, GameOutputMapper.class);
    	
		UoW.initMapperFactory(mapper);
    }
    
    public void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    	super.preProcessRequest(request, response);
    	
    	
    	String path = request.getServletPath();
		try {
	    	Dispatcher command = getCommandName(path, request, response);
			if(command != null) {
				command.init(request, response);
				command.execute();
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			super.postProcessRequest(request, response); 	
		}
    }
    
    private Dispatcher getCommandName(String path, HttpServletRequest request, HttpServletResponse response) {
    	Dispatcher command = null;
    	
    	
    	path = path.substring(5, path.length());
    	if(matchCommand(path, "/Player/Login")) {
    		command = new Login(request, response);
    	} else if(matchCommand(path, "/Player/Register")) {
    		command = new Register(request, response);
    	} else if(matchCommand(path, "/Player/Logout")) {
    		command = new Logout(request, response);
    	} else if(matchCommand(path, "/Deck") && request.getParameterValues("deck") != null) {
    		command = new UploadDeck(request, response);
    	} else if(matchCommand(path, "/Deck/[0-9]+")) {
    		command = new ViewDeck(request, response);
    	} else if(matchCommand(path, "/Deck")) {
    		command = new ViewDecks(request, response);
    	} else if(matchCommand(path, "/Player")) {
    		command = new ListPlayers(request, response);
    	} else if(matchCommand(path, "/Player/-*[0-9]+/Challenge")) {
    		command = new ChallengePlayer(request, response);
    	} else if(matchCommand(path, "/Challenge")) {
    		command = new ListChallenges(request, response);
    	} else if(matchCommand(path, "/Challenge/[0-9]+/Withdraw")) {
    		command = new WithdrawChallenge(request, response);
    	} else if(matchCommand(path, "/Challenge/[0-9]+/Refuse")) {
    		command = new RefuseChallenge(request, response);
    	} else if(matchCommand(path, "/Challenge/[0-9]+/Accept")) {
    		command = new AcceptChallenge(request, response);
    	} else if(matchCommand(path, "/Game")) {
    		command = new ListGames(request, response);
    	} else if(matchCommand(path, "/Game/[0-9]+")) {
    		command = new ViewBoard(request, response);
    	} else if(matchCommand(path, "/Game/[0-9]+/Hand")) {
    		command = new ViewHand(request, response);
    	} else if(matchCommand(path, "/Game/[0-9]+/Player/[0-9]+/Discard")) {
    		command = new ViewDiscardPile(request, response);
    	} else if(matchCommand(path, "/Game/[0-9]+/Hand/[0-9]+/Play")) {
    		command = new PlayCard(request, response);
    	} else if(matchCommand(path, "/Game/[0-9]+/Retire")) {
    		command = new Retire(request, response);
    	} else if(matchCommand(path, "/Game/[0-9]+/EndTurn")) {
    		command = new End(request, response);
    	} 
    	
    	System.out.println(command);
    	return command;
    }
    
    private boolean matchCommand(String path, String regex) {
    	Pattern pattern = Pattern.compile(regex);
    	Matcher matcher = pattern.matcher(path);
    	return matcher.matches();
    }

	@Override
	protected String getXMLErrorTemplate() {
		return "/WEB-INF/jsp/fail.jsp";
	}

	@Override
	protected String getJSONErrorTemplate() {
		return "/WEB-INF/jsp/fail.jsp";
	}

	@Override
	protected String getErrorTemplate() {
		return "/WEB-INF/jsp/fail.jsp";
	}

	@Override
	protected String getMessageTemplate() {
		return "/WEB-INF/jsp/fail.jsp";
	}

	@Override
	protected String getMainTemplate() {
		return "/WEB-INF/jsp/fail.jsp";
	}
}
