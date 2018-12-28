package org.soen387.dom.model.play;

import java.util.List;

import org.dsrg.soenea.domain.DomainObject;
import org.soen387.dom.model.bench.IBench;
import org.soen387.dom.model.card.ICard;
import org.soen387.dom.model.cardInPlay.ICardInPlay;
import org.soen387.dom.model.deck.IDeck;
import org.soen387.dom.model.discard.IDiscard;
import org.soen387.dom.model.game.IGame;
import org.soen387.dom.model.hand.IHand;
import org.soen387.dom.model.player.IPlayer;

public class Play extends DomainObject<Long> implements IPlay {
	
	IGame game;
	IPlayer player;
	IDeck deck;
	IHand hand;
	IDiscard discard;
	IBench bench;
	int status;
	
	public Play(Long id, long version, IGame game, IPlayer player, IDeck deck, int status) {
		super(id, version);
		this.game = game;
		this.player = player;
		this.deck = deck;
		this.status = status;
	}
	
	public Play(Long id, long version, IGame game, IPlayer player, IDeck deck, IHand hand, IDiscard discard, IBench bench, int status) {
		super(id, version);
		this.game = game;
		this.player = player;
		this.deck = deck;
		this.hand = hand;
		this.discard = discard;
		this.bench = bench;
		this.status = status;
	}

	@Override
	public IGame getGame() {
		return game;
	}

	@Override
	public void setGame(IGame game) {
		this.game = game;
	}

	@Override
	public IPlayer getPlayer() {
		return player;
	}

	@Override
	public void setPlayer(IPlayer player) {
		this.player = player;
	}

	@Override
	public int getStatus() {
		return status;
	}

	@Override
	public void setStatus(int status) {
		this.status = status;
	}

	@Override
	public IDeck getDeck() {
		return deck;
	}

	@Override
	public void setDeck(IDeck deck) {
		this.deck = deck;
	}

	@Override
	public IHand getHand() {
		return hand;
	}

	@Override
	public void setHand(IHand hand) {
		this.hand = hand;
	}

	@Override
	public IDiscard getDiscard() {
		return discard;
	}

	@Override
	public void setDiscard(IDiscard discard) {
		this.discard = discard;
	}

	@Override
	public IBench getBench() {
		return bench;
	}

	@Override
	public void setBench(IBench bench) {
		this.bench = bench;
	}
	
	public static ICard getNextCardInPlay(IPlay play) {
		int nextIndex = getSizeOfCardsInPlay(play);
		return play.getDeck().getCards().get(nextIndex);
	}
	
	public static int getDeckSize(IPlay play) {
		return 40 - getSizeOfCardsInPlay(play);
	}
	
	private static int getSizeOfCardsInPlay(IPlay play) {
		int handSize = play.getHand().getCardsInPlay().size();
		int discardSize = play.getDiscard().getCardsInPlay().size();
		int benchSize = play.getBench().getCardsInPlay().size();
		List<ICardInPlay> benchCards = play.getBench().getCardsInPlay();
		int count = 0;
		for(ICardInPlay c : benchCards) {
			if(c.getEnergy() != null && c.getEnergy().getId() != 0) {
				count++;
			}
			if(c.getBasic() != null && c.getBasic().getId() != 0) {
				count++;
			}
		}
		return handSize + discardSize + benchSize + count;
	}
}
