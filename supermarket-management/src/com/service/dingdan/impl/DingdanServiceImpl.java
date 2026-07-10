package com.service.dingdan.impl;

import com.dao.DaoSupport;
import com.entity.Dingdan;
import com.service.dingdan.DingdanService;
import com.db.DBUnitHelper;

import java.util.List;


public class DingdanServiceImpl extends DaoSupport<Dingdan> implements DingdanService{

	public DingdanServiceImpl(Dingdan entity) {
		super(entity);
	}

	public void update(Dingdan dingdan) {
		super.executeSql("update dingdan set "+"dingdanriqi='"+dingdan.getDingdanriqi()+"',"+"dingdanzhuangtai='"+dingdan.getDingdanzhuangtai()+"',"+"heji='"+dingdan.getHeji()+"',"+"zhifuzhuangtai='"+dingdan.getZhifuzhuangtai()+"',"+"dingdanbianhao='"+dingdan.getDingdanbianhao()+"',"+"maijia='"+dingdan.getMaijia()+"'" +" where id="+dingdan.getId());
	}

	public void insert(Dingdan dingdan) {
		super.executeSql("INSERT INTO dingdan(dingdanriqi,dingdanzhuangtai,heji,zhifuzhuangtai,dingdanbianhao,maijia) values ("+"'"+dingdan.getDingdanriqi()+"',"+"'"+dingdan.getDingdanzhuangtai()+"',"+"'"+dingdan.getHeji()+"',"+"'"+dingdan.getZhifuzhuangtai()+"',"+"'"+dingdan.getDingdanbianhao()+"',"+"'"+dingdan.getMaijia()+"'"+")");
	}
	
	public List<Dingdan> query(Dingdan dingdan) {
		StringBuffer sbf=new StringBuffer("select * from dingdan where 1=1 ");
		if(dingdan.getId()!=null){
			sbf.append(" and id="+dingdan.getId());
		}
		if(dingdan.getDingdanriqi()!=null&&!dingdan.getDingdanriqi().equals("")){
			sbf.append(" and dingdanriqi like '%"+dingdan.getDingdanriqi()+"%'");
		}
		if(dingdan.getDingdanzhuangtai()!=null&&!dingdan.getDingdanzhuangtai().equals("")){
			sbf.append(" and dingdanzhuangtai like '%"+dingdan.getDingdanzhuangtai()+"%'");
		}
		if(dingdan.getHeji()!=null&&!dingdan.getHeji().equals("")){
			sbf.append(" and heji like '%"+dingdan.getHeji()+"%'");
		}
		if(dingdan.getZhifuzhuangtai()!=null&&!dingdan.getZhifuzhuangtai().equals("")){
			sbf.append(" and zhifuzhuangtai like '%"+dingdan.getZhifuzhuangtai()+"%'");
		}
		if(dingdan.getDingdanbianhao()!=null&&!dingdan.getDingdanbianhao().equals("")){
			sbf.append(" and dingdanbianhao like '%"+dingdan.getDingdanbianhao()+"%'");
		}
		if(dingdan.getMaijia()!=null&&!dingdan.getMaijia().equals("")){
			sbf.append(" and maijia like '%"+dingdan.getMaijia()+"%'");
		}
		return DBUnitHelper.executeQuery(sbf.toString(), Dingdan.class);
	}

}
