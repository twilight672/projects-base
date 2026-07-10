package com.service.yonghu;

import com.dao.Dao;
import com.entity.Yonghu;
import java.util.List;

public interface YonghuService extends Dao<Yonghu> {

	public void update(Yonghu yonghu);
	
	public void insert(Yonghu yonghu);
	
	public List<Yonghu> query(Yonghu yonghu);
}
