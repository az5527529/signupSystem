package com.service.common;

import java.util.List;

public interface BaseService<T> {
	
	public T loadById(long id);
	
	public List<T> loadByProperty(String property,Object value);
	
	public T save(T t);
	
	public List<T> query(String whereCondition,int pageStart,int pageSize,String orderBy, String order);
	
	public long getCount(String whereCondition);
	
	public T update(T t);
	
	public void delete(T t);
	
	public int deleteById(Long id);
}
