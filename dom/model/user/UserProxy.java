package org.soen387.dom.model.user;

import java.sql.SQLException;

import org.dsrg.soenea.domain.DomainObjectCreationException;
import org.dsrg.soenea.domain.MapperException;
import org.dsrg.soenea.domain.proxy.DomainObjectProxy;
import org.soen387.dom.mapper.user.UserInputMapper;

public class UserProxy extends DomainObjectProxy<Long, User> implements IUser {
	
	public UserProxy(long id) {
		super(id);
	}

	@Override
	protected User getFromMapper(Long id) throws MapperException, DomainObjectCreationException {
		try {
			return UserInputMapper.find(id);
		} catch(MapperException | SQLException e) {
			throw new DomainObjectCreationException(e.getMessage(), e);
		}
	}

	@Override
	public String getUsername() {
		return getInnerObject().getUsername();
	}

	@Override
	public void setUsername(String username) {
		getInnerObject().setUsername(username);
	}

	@Override
	public String getPassword() {
		return getInnerObject().getPassword();
	}

	@Override
	public void setPassword(String password) {
		getInnerObject().setPassword(password);
	}
}
