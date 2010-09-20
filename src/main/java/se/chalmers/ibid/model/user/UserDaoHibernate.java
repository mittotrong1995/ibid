package se.chalmers.ibid.model.user;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.springframework.stereotype.Repository;

import se.chalmers.ibid.model.dao.GenericDaoHibernate;
import se.chalmers.ibid.model.exception.InstanceNotFoundException;

@Repository("usuarioDao")
public class UserDaoHibernate extends GenericDaoHibernate<User, Long> implements UserDao {
	
	public User buscarUsuarioPorLogin(String login) throws InstanceNotFoundException{
		String stringConsulta;
		stringConsulta = "SELECT u FROM Usuario u WHERE u.login LIKE :login";
		Query consulta = getSession().createQuery(stringConsulta);
		consulta.setParameter("login", login);
		User usuario;
		// El login es unico (restriccion UNIQUE)
		try {
			usuario = (User) consulta.uniqueResult();
			if (usuario == null) throw new InstanceNotFoundException(login, User.class.getName());
		} catch (HibernateException e) {
            throw convertHibernateAccessException(e);
        }
		return usuario;
	}

}