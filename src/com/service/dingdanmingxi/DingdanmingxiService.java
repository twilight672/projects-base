package com.service.dingdanmingxi;

import com.dao.Dao;
import com.entity.Dingdanmingxi;
import java.util.List;

public interface DingdanmingxiService extends Dao<Dingdanmingxi> {

	public void update(Dingdanmingxi dingdanmingxi);
	
	public void insert(Dingdanmingxi dingdanmingxi);
	
	public List<Dingdanmingxi> query(Dingdanmingxi dingdanmingxi);
}
