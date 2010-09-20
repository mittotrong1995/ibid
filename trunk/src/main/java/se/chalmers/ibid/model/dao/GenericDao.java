package se.chalmers.ibid.model.dao;

import java.io.Serializable;

import se.chalmers.ibid.model.exception.InstanceNotFoundException;

public interface GenericDao <E, PK extends Serializable>{

	void save(E entity);

	E find(PK id) throws InstanceNotFoundException;

	boolean exists(PK id);

	void remove(PK id) throws InstanceNotFoundException;

}
