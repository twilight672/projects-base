package com.service.shangpin.impl;

import com.dao.DaoSupport;
import com.entity.Shangpin;
import com.service.shangpin.ShangpinService;
import com.db.DBUnitHelper;

import java.util.List;


public class ShangpinServiceImpl extends DaoSupport<Shangpin> implements ShangpinService{

	public ShangpinServiceImpl(Shangpin entity) {
		super(entity);
	}

	public void update(Shangpin shangpin) {
		super.executeSql("update shangpin set "+"shangpinming='"+shangpin.getShangpinming()+"',"+"jiage='"+shangpin.getJiage()+"',"+"shangpinmiaoshu='"+shangpin.getShangpinmiaoshu()+"',"+"shangpindalei='"+shangpin.getShangpindalei()+"',"+"tupian='"+shangpin.getTupian()+"'" +" where id="+shangpin.getId());
	}

	public void insert(Shangpin shangpin) {
		super.executeSql("INSERT INTO shangpin(shangpinming,jiage,shangpinmiaoshu,shangpindalei,tupian) values ("+"'"+shangpin.getShangpinming()+"',"+"'"+shangpin.getJiage()+"',"+"'"+shangpin.getShangpinmiaoshu()+"',"+"'"+shangpin.getShangpindalei()+"',"+"'"+shangpin.getTupian()+"'"+")");
	}
	
	public List<Shangpin> query(Shangpin shangpin) {
		StringBuffer sbf=new StringBuffer("select * from shangpin where 1=1 ");
		if(shangpin.getId()!=null){
			sbf.append(" and id="+shangpin.getId());
		}
		if(shangpin.getShangpinming()!=null&&!shangpin.getShangpinming().equals("")){
			sbf.append(" and shangpinming like '%"+shangpin.getShangpinming()+"%'");
		}
		if(shangpin.getJiage()!=null&&!shangpin.getJiage().equals("")){
			sbf.append(" and jiage like '%"+shangpin.getJiage()+"%'");
		}
		if(shangpin.getShangpinmiaoshu()!=null&&!shangpin.getShangpinmiaoshu().equals("")){
			sbf.append(" and shangpinmiaoshu like '%"+shangpin.getShangpinmiaoshu()+"%'");
		}
		if(shangpin.getShangpindalei()!=null&&!shangpin.getShangpindalei().equals("")){
			sbf.append(" and shangpindalei like '%"+shangpin.getShangpindalei()+"%'");
		}
		if(shangpin.getTupian()!=null&&!shangpin.getTupian().equals("")){
			sbf.append(" and tupian like '%"+shangpin.getTupian()+"%'");
		}
		return DBUnitHelper.executeQuery(sbf.toString(), Shangpin.class);
	}

}
