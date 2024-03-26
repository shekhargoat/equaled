package com.equaled.customrepository.impl;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.transaction.Transactional;

import com.equaled.customrepository.ICustomRepository;
import com.equaled.dozer.DozerUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import org.springframework.validation.annotation.Validated;

/**
 * This is the implementer class which containing the generic method which is taking the Entity name and Sid as parameter
 * returning the id associated with that particular Sid.
 */

@Repository
@Transactional
@Validated
public class CustomRepositoyImpl implements ICustomRepository {
	private final Logger logger=LoggerFactory.getLogger(CustomRepositoyImpl.class);

	@PersistenceContext
    EntityManager entitymangager;

	@Autowired
	DozerUtils dozerUtils;


	/** 
	 * return ID of any entity by Sid
	 */
	@Override
	public Integer findIdBySid(String classz, String sid) {
		String customQuery = "SELECT a.id from "+classz+" a where hex(a.sid)='"+sid+"'";
		Query query = entitymangager.createQuery(customQuery);
		return (Integer)query.getSingleResult();	
	}	
	


}
