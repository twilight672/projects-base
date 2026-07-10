package com.service.yonghu.impl;

import com.dao.DaoSupport;
import com.entity.Yonghu;
import com.service.yonghu.YonghuService;
import com.db.DBUnitHelper;

import java.util.List;


public class YonghuServiceImpl extends DaoSupport<Yonghu> implements YonghuService{

	public YonghuServiceImpl(Yonghu entity) {
		super(entity);
	}

	public void update(Yonghu yonghu) {
		super.executeSql("update yonghu set "+"yonghuming='"+yonghu.getYonghuming()+"',"+"mima='"+yonghu.getMima()+"',"+"jiaose='"+yonghu.getJiaose()+"',"+"dianhua='"+yonghu.getDianhua()+"',"+"dizhi='"+yonghu.getDizhi()+"',"+"xingming='"+yonghu.getXingming()+"',"+"youbian='"+yonghu.getYoubian()+"',"+"youjian='"+yonghu.getYoujian()+"',"+"touxiang='"+yonghu.getTouxiang()+"'" +" where id="+yonghu.getId());
	}

	public void insert(Yonghu yonghu) {
		super.executeSql("INSERT INTO yonghu(yonghuming,mima,jiaose,dianhua,dizhi,xingming,youbian,youjian,touxiang) values ("+"'"+yonghu.getYonghuming()+"',"+"'"+yonghu.getMima()+"',"+"'"+yonghu.getJiaose()+"',"+"'"+yonghu.getDianhua()+"',"+"'"+yonghu.getDizhi()+"',"+"'"+yonghu.getXingming()+"',"+"'"+yonghu.getYoubian()+"',"+"'"+yonghu.getYoujian()+"',"+"'"+yonghu.getTouxiang()+"'"+")");
	}
	
	public List<Yonghu> query(Yonghu yonghu) {
		StringBuffer sbf=new StringBuffer("select * from yonghu where 1=1 ");
		if(yonghu.getId()!=null){
			sbf.append(" and id="+yonghu.getId());
		}
		if(yonghu.getYonghuming()!=null&&!yonghu.getYonghuming().equals("")){
			sbf.append(" and yonghuming like '%"+yonghu.getYonghuming()+"%'");
		}
		if(yonghu.getMima()!=null&&!yonghu.getMima().equals("")){
			sbf.append(" and mima like '%"+yonghu.getMima()+"%'");
		}
		if(yonghu.getJiaose()!=null&&!yonghu.getJiaose().equals("")){
			sbf.append(" and jiaose like '%"+yonghu.getJiaose()+"%'");
		}
		if(yonghu.getDianhua()!=null&&!yonghu.getDianhua().equals("")){
			sbf.append(" and dianhua like '%"+yonghu.getDianhua()+"%'");
		}
		if(yonghu.getDizhi()!=null&&!yonghu.getDizhi().equals("")){
			sbf.append(" and dizhi like '%"+yonghu.getDizhi()+"%'");
		}
		if(yonghu.getXingming()!=null&&!yonghu.getXingming().equals("")){
			sbf.append(" and xingming like '%"+yonghu.getXingming()+"%'");
		}
		if(yonghu.getYoubian()!=null&&!yonghu.getYoubian().equals("")){
			sbf.append(" and youbian like '%"+yonghu.getYoubian()+"%'");
		}
		if(yonghu.getYoujian()!=null&&!yonghu.getYoujian().equals("")){
			sbf.append(" and youjian like '%"+yonghu.getYoujian()+"%'");
		}
		if(yonghu.getTouxiang()!=null&&!yonghu.getTouxiang().equals("")){
			sbf.append(" and touxiang like '%"+yonghu.getTouxiang()+"%'");
		}
		return DBUnitHelper.executeQuery(sbf.toString(), Yonghu.class);
	}

}
