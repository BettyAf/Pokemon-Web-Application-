package org.soen387.dom.model.bench;

import java.sql.SQLException;
import java.util.List;

import org.dsrg.soenea.domain.MapperException;
import org.dsrg.soenea.uow.MissingMappingException;
import org.dsrg.soenea.uow.UoW;
import org.soen387.dom.model.cardInPlay.ICardInPlay;
import org.soen387.dom.model.play.IPlay;
import org.soen387.ser.bench.BenchTDG;

public class BenchFactory {
	
	public static Bench createNew(IPlay play) throws SQLException {
		Bench h = new Bench(BenchTDG.getMaxId(), 1, play);
		try {
			UoW.getCurrent().registerNew(h);
		} catch (MissingMappingException | MapperException e) {
			e.printStackTrace();
		} 
		return h;
	}
	
	public static Bench createClean(long id, long version, IPlay play, List<ICardInPlay> cards) throws SQLException {
		Bench h = new Bench(id, version, play, cards);
		UoW.getCurrent().registerClean(h);
		return h;	
	}

}
