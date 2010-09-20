package se.chalmers.ibid.model.user;

import se.chalmers.ibid.model.dao.GenericDao;
import se.chalmers.ibid.model.exception.InstanceNotFoundException;

public interface UserDao extends GenericDao<User, Long>{

	public User buscarUsuarioPorLogin(String login) throws InstanceNotFoundException;
}
