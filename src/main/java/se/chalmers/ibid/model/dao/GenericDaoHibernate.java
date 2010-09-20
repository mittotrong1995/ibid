package se.chalmers.ibid.model.dao;

import java.io.Serializable;
import java.lang.reflect.ParameterizedType;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.orm.hibernate3.SessionFactoryUtils;

import se.chalmers.ibid.model.exception.InstanceNotFoundException;

public class GenericDaoHibernate<E, PK extends Serializable> implements
        GenericDao<E, PK> {

    private SessionFactory sessionFactory;

    private Class<E> entityClass;

    @SuppressWarnings("unchecked")
    public GenericDaoHibernate() {
        this.entityClass = (Class<E>) ((ParameterizedType) getClass()
                .getGenericSuperclass()).getActualTypeArguments()[0];
    }

    @Autowired
    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    protected Session getSession() {
        return sessionFactory.getCurrentSession();
    }

    protected DataAccessException convertHibernateAccessException(
            HibernateException e) {

        return SessionFactoryUtils.convertHibernateAccessException(e);

    }

    public void save(E entity) {
        try {
            getSession().saveOrUpdate(entity);
        } catch (HibernateException e) {
            throw convertHibernateAccessException(e);
        }
    }

    public boolean exists(PK id) {

        try {

            return getSession().createCriteria(entityClass).add(
                    Restrictions.idEq(id)).setProjection(Projections.id())
                    .uniqueResult() != null;

        } catch (HibernateException e) {
            throw convertHibernateAccessException(e);
        }

    }

    @SuppressWarnings("unchecked")
    public E find(PK id) throws InstanceNotFoundException {

        try {

            E entity = (E) getSession().get(entityClass, id);

            if (entity == null) {
                throw new InstanceNotFoundException(id, entityClass.getName());
            }

            return entity;

        } catch (HibernateException e) {
            throw convertHibernateAccessException(e);
        }

    }

    public void remove(PK id) throws InstanceNotFoundException {
        try {
            getSession().delete(find(id));
        } catch (HibernateException e) {
            throw convertHibernateAccessException(e);
        }
    }

}
