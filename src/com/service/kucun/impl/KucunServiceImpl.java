package com.service.kucun.impl;

import com.dao.DaoSupport;
import com.entity.Kucun;
import com.service.kucun.KucunService;
import com.db.DBUnitHelper;

import java.util.List;


public class KucunServiceImpl extends DaoSupport<Kucun> implements KucunService{

	public KucunServiceImpl(Kucun entity) {
		super(entity);
	}

	public void update(Kucun kucun) {
		super.executeSql("update kucun set "+"shangpinming='"+kucun.getShangpinming()+"',"+"kucunliang='"+kucun.getKucunliang()+"'" +" where id="+kucun.getId());
	}

	public void insert(Kucun kucun) {
		super.executeSql("INSERT INTO kucun(shangpinming,kucunliang) values ("+"'"+kucun.getShangpinming()+"',"+"'"+kucun.getKucunliang()+"'"+")");
	}
	
	public List<Kucun> query(Kucun kucun) {
		StringBuffer sbf=new StringBuffer("select * from kucun where 1=1 ");
		if(kucun.getId()!=null){
			sbf.append(" and id="+kucun.getId());
		}
		if(kucun.getShangpinming()!=null&&!kucun.getShangpinming().equals("")){
			sbf.append(" and shangpinming like '%"+kucun.getShangpinming()+"%'");
		}
		if(kucun.getKucunliang()!=null&&!kucun.getKucunliang().equals("")){
			sbf.append(" and kucunliang like '%"+kucun.getKucunliang()+"%'");
		}
		return DBUnitHelper.executeQuery(sbf.toString(), Kucun.class);
	}

}
