package org.soen387.dom.model.play;

import org.dsrg.soenea.domain.interf.IDomainObject;
import org.soen387.dom.model.bench.IBench;
import org.soen387.dom.model.deck.IDeck;
import org.soen387.dom.model.discard.IDiscard;
import org.soen387.dom.model.game.IGame;
import org.soen387.dom.model.hand.IHand;
import org.soen387.dom.model.player.IPlayer;

public interface IPlay extends IDomainObject<Long> {

	public abstract IGame getGame();
	public abstract void setGame(IGame game);
	public abstract IPlayer getPlayer();
	public abstract void setPlayer(IPlayer player);
	public abstract int getStatus();
	public abstract void setStatus(int status);
	public abstract IDeck getDeck();
	public abstract void setDeck(IDeck deck);
	public abstract IHand getHand();
	public abstract void setHand(IHand hand);
	public abstract IDiscard getDiscard();
	public abstract void setDiscard(IDiscard discard);
	public abstract IBench getBench();
	public abstract void setBench(IBench bench);
}
