package org.soen387.ser;

import java.sql.Connection;
import java.sql.Statement;

import org.dsrg.soenea.service.threadLocal.DbRegistry;
import org.soen387.app.PokeFC;
import org.soen387.ser.user.UserTDG;

public class RenewDatabase {

	public static void main(String[] args) {
		PokeFC.prepareDbRegistry("");

				
		try {
			UserTDG.dropTable();
			} catch(Exception e){}
			
			
			try {
				UserTDG.createTable();
				
			} catch(Exception e){}

	}

}
