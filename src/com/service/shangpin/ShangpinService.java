package com.service.shangpin;

import com.dao.Dao;
import com.entity.Shangpin;
import java.util.List;

public interface ShangpinService extends Dao<Shangpin> {

	public void update(Shangpin shangpin);
	
	public void insert(Shangpin shangpin);
	
	public List<Shangpin> query(Shangpin shangpin);
}
