package com.dao;

import java.util.List;

public interface Dao<T> {
	public List<T> queryByKey(String key,String keyvalue);
	public List<T> getAll();
	public void executeSql(String sql);
	public Integer delBykey(String key, String keyvalue);
}
