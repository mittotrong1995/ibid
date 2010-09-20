package se.chalmers.ibid.model.user;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.springframework.stereotype.Repository;

import se.chalmers.ibid.model.dao.GenericDaoHibernate;
import se.chalmers.ibid.model.exception.InstanceNotFoundException;

@Repository("userDao")
public class UserDaoHibernate extends GenericDaoHibernate<User, Long> implements UserDao {
	
	public User searchUserByLogin(String login) throws InstanceNotFoundException{
		String stringQuery;
		stringQuery = "SELECT u FROM User u WHERE u.login LIKE :login";
		Query query = getSession().createQuery(stringQuery);
		query.setParameter("login", login);
		User user;

		try {
			user = (User) query.uniqueResult();
			if (user == null) throw new InstanceNotFoundException(login, User.class.getName());
		} catch (HibernateException e) {
            throw convertHibernateAccessException(e);
        }
		return user;
	}

}