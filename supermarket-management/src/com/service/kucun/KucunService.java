package com.service.kucun;

import com.dao.Dao;
import com.entity.Kucun;
import java.util.List;

public interface KucunService extends Dao<Kucun> {

	public void update(Kucun kucun);
	
	public void insert(Kucun kucun);
	
	public List<Kucun> query(Kucun kucun);
}
