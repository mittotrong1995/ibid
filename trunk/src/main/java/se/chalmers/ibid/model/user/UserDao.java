package se.chalmers.ibid.model.user;

import es.udc.pojo.modelutil.dao.GenericDao;
import es.udc.pojo.modelutil.exceptions.InstanceNotFoundException;

public interface UserDao extends GenericDao<User, Long>{

	public User buscarUsuarioPorLogin(String login) throws InstanceNotFoundException;
}
