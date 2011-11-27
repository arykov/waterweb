package com.ryaltech.whitewater.gauges.services;

import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;

import com.ryaltech.whitewater.gauges.model.RiverInfo;
import com.ryaltech.whitewater.gauges.model.RiverLevel;

public class JpaWaterWebDao implements WaterWebDao{
	private static final EntityManagerFactory emfInstance =
	        Persistence.createEntityManagerFactory("transactions-optional");

	@Override
	public RiverInfo[] getAllRiversInfo() {
		EntityManager em = emfInstance.createEntityManager();
		Query q = em.createQuery("select t from RiverInfo t");
		List<RiverInfo> ri = q.getResultList();
		RiverInfo[] riArray =ri.toArray(new RiverInfo[ri.size()]);
		em.close();
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
	public void insertRunnableConditions(RiverInfo... riversInfo) {
		EntityManager em = emfInstance.createEntityManager();
		for(RiverInfo ri:riversInfo){
			em.persist(ri);
		}		
		em.close();		
	}

	@Override
	public void insertRiverLevels(RiverLevel... riverLevels) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void purgeRiverLevels(Date before) {
		// TODO Auto-generated method stub
		
	}

}
