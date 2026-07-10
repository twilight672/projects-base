package com.service.shangpindalei.impl;

import com.dao.DaoSupport;
import com.entity.Shangpindalei;
import com.service.shangpindalei.ShangpindaleiService;
import com.db.DBUnitHelper;

import java.util.List;


public class ShangpindaleiServiceImpl extends DaoSupport<Shangpindalei> implements ShangpindaleiService{

	public ShangpindaleiServiceImpl(Shangpindalei entity) {
		super(entity);
	}

	public void update(Shangpindalei shangpindalei) {
		super.executeSql("update shangpindalei set "+"leibieming='"+shangpindalei.getLeibieming()+"',"+"zhanshitupian='"+shangpindalei.getZhanshitupian()+"'" +" where id="+shangpindalei.getId());
	}

	public void insert(Shangpindalei shangpindalei) {
		super.executeSql("INSERT INTO shangpindalei(leibieming,zhanshitupian) values ("+"'"+shangpindalei.getLeibieming()+"',"+"'"+shangpindalei.getZhanshitupian()+"'"+")");
	}
	
	public List<Shangpindalei> query(Shangpindalei shangpindalei) {
		StringBuffer sbf=new StringBuffer("select * from shangpindalei where 1=1 ");
		if(shangpindalei.getId()!=null){
			sbf.append(" and id="+shangpindalei.getId());
		}
		if(shangpindalei.getLeibieming()!=null&&!shangpindalei.getLeibieming().equals("")){
			sbf.append(" and leibieming like '%"+shangpindalei.getLeibieming()+"%'");
		}
		if(shangpindalei.getZhanshitupian()!=null&&!shangpindalei.getZhanshitupian().equals("")){
			sbf.append(" and zhanshitupian like '%"+shangpindalei.getZhanshitupian()+"%'");
		}
		return DBUnitHelper.executeQuery(sbf.toString(), Shangpindalei.class);
	}

}
