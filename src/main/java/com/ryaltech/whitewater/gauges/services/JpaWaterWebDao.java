package com.ryaltech.whitewater.gauges.services;

import java.util.Date;
import java.util.List;
import java.util.UUID;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceUnit;
import javax.persistence.Query;

import com.ryaltech.whitewater.gauges.model.RiverInfo;
import com.ryaltech.whitewater.gauges.model.RiverLevel;

public class JpaWaterWebDao implements WaterWebDao{
	@PersistenceContext
    private EntityManager em;
	        

	@Override
	public RiverInfo[] getAllRiversInfo() {	
		Query q = em.createQuery("select t from RiverInfo t");
		List<RiverInfo> ri = q.getResultList();
		RiverInfo[] riArray =ri.toArray(new RiverInfo[ri.size()]);		
		return riArray;
	}

	@Override
	public RiverInfo getRiverInfo(String riverId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public RiverLevel getLatestRiverLevel(String riverId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public RiverLevel[] getLatestRiverLevels() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void persistRunnableConditions(RiverInfo... riversInfo) {
		
		for(RiverInfo ri:riversInfo){
			if(ri.getRiverId() == null)ri.setRiverId(UUID.randomUUID().toString());
			em.persist(ri);
		}		
				
	}

	@Override
	public void insertRiverLevels(RiverLevel... riverLevels) {
		

		
	}

	@Override
	public void purgeRiverLevels(Date before) {
		// TODO Auto-generated method stub
		
	}

}
