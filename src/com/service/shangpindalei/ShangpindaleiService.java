package com.service.shangpindalei;

import com.dao.Dao;
import com.entity.Shangpindalei;
import java.util.List;

public interface ShangpindaleiService extends Dao<Shangpindalei> {

	public void update(Shangpindalei shangpindalei);
	
	public void insert(Shangpindalei shangpindalei);
	
	public List<Shangpindalei> query(Shangpindalei shangpindalei);
}
