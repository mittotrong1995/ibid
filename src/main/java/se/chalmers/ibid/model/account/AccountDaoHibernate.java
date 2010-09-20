package se.chalmers.ibid.model.account;

import org.springframework.stereotype.Repository;

import se.chalmers.ibid.model.dao.GenericDaoHibernate;

@Repository("cuentaDao")
public class AccountDaoHibernate extends GenericDaoHibernate<Account, Long> implements AccountDao{

}
