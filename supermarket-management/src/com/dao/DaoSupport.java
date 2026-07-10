package com.dao;



import java.util.List;

import com.db.DBUnitHelper;

public class DaoSupport<T> implements Dao<T> {
	String clazzname;
	Class clazz;
	public DaoSupport(T entity) {
		clazz=entity.getClass();
		clazzname=clazz.getSimpleName().toLowerCase();
	}
	public List<T> queryByKey(String key,String keyvalue){
		String sql="select * from "+clazzname+" where "+key+"=?";
		List<T> list = (List<T>) DBUnitHelper.executeQuery(sql, clazz, keyvalue);
		return list;
	}
	
	public List<T> getAll(){
		String sql="select * from "+clazzname;
		List<T> list = (List<T>) DBUnitHelper.executeQuery(sql, clazz);
		return list;
	}
	public void executeSql(String sql) {
		DBUnitHelper.executeUpdate(sql);
	}
	public Integer delBykey(String key, String keyvalue) {
		String sql = "delete from "+clazzname+" where "+key+"=?";
		return DBUnitHelper.executeUpdate(sql, keyvalue);
	}
}
