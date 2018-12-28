package org.soen387.dom.model.role;

import org.dsrg.soenea.domain.role.IRole;
import org.dsrg.soenea.domain.role.Role;

public class LoggedInRole extends Role implements IRole{

	public LoggedInRole() {
		super((long) 2, 1, "LoggedInRole");
	}
	
	public Long getId() {
		return (long) 2;
	}

	public long getVersion() {
		return 1;
	}
	
	public String getName() {
		return "LoggedInRole";
	}

}
