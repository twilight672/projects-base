package com.service.dingdanmingxi.impl;

import com.dao.DaoSupport;
import com.entity.Dingdanmingxi;
import com.service.dingdanmingxi.DingdanmingxiService;
import com.db.DBUnitHelper;

import java.util.List;


public class DingdanmingxiServiceImpl extends DaoSupport<Dingdanmingxi> implements DingdanmingxiService{

	public DingdanmingxiServiceImpl(Dingdanmingxi entity) {
		super(entity);
	}

	public void update(Dingdanmingxi dingdanmingxi) {
		super.executeSql("update dingdanmingxi set "+"dingdanbianhao='"+dingdanmingxi.getDingdanbianhao()+"',"+"jiage='"+dingdanmingxi.getJiage()+"',"+"shangpin='"+dingdanmingxi.getShangpin()+"',"+"shuliang='"+dingdanmingxi.getShuliang()+"',"+"zongjia='"+dingdanmingxi.getZongjia()+"',"+"shangpinid='"+dingdanmingxi.getShangpinid()+"'" +" where id="+dingdanmingxi.getId());
	}

	public void insert(Dingdanmingxi dingdanmingxi) {
		super.executeSql("INSERT INTO dingdanmingxi(dingdanbianhao,jiage,shangpin,shuliang,zongjia,shangpinid) values ("+"'"+dingdanmingxi.getDingdanbianhao()+"',"+"'"+dingdanmingxi.getJiage()+"',"+"'"+dingdanmingxi.getShangpin()+"',"+"'"+dingdanmingxi.getShuliang()+"',"+"'"+dingdanmingxi.getZongjia()+"',"+"'"+dingdanmingxi.getShangpinid()+"'"+")");
	}
	
	public List<Dingdanmingxi> query(Dingdanmingxi dingdanmingxi) {
		StringBuffer sbf=new StringBuffer("select * from dingdanmingxi where 1=1 ");
		if(dingdanmingxi.getId()!=null){
			sbf.append(" and id="+dingdanmingxi.getId());
		}
		if(dingdanmingxi.getDingdanbianhao()!=null&&!dingdanmingxi.getDingdanbianhao().equals("")){
			sbf.append(" and dingdanbianhao like '%"+dingdanmingxi.getDingdanbianhao()+"%'");
		}
		if(dingdanmingxi.getJiage()!=null&&!dingdanmingxi.getJiage().equals("")){
			sbf.append(" and jiage like '%"+dingdanmingxi.getJiage()+"%'");
		}
		if(dingdanmingxi.getShangpin()!=null&&!dingdanmingxi.getShangpin().equals("")){
			sbf.append(" and shangpin like '%"+dingdanmingxi.getShangpin()+"%'");
		}
		if(dingdanmingxi.getShuliang()!=null&&!dingdanmingxi.getShuliang().equals("")){
			sbf.append(" and shuliang like '%"+dingdanmingxi.getShuliang()+"%'");
		}
		if(dingdanmingxi.getZongjia()!=null&&!dingdanmingxi.getZongjia().equals("")){
			sbf.append(" and zongjia like '%"+dingdanmingxi.getZongjia()+"%'");
		}
		if(dingdanmingxi.getShangpinid()!=null&&!dingdanmingxi.getShangpinid().equals("")){
			sbf.append(" and shangpinid like '%"+dingdanmingxi.getShangpinid()+"%'");
		}
		return DBUnitHelper.executeQuery(sbf.toString(), Dingdanmingxi.class);
	}

}
