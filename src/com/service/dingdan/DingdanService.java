package com.service.dingdan;

import com.dao.Dao;
import com.entity.Dingdan;
import java.util.List;

public interface DingdanService extends Dao<Dingdan> {

	public void update(Dingdan dingdan);
	
	public void insert(Dingdan dingdan);
	
	public List<Dingdan> query(Dingdan dingdan);
}
