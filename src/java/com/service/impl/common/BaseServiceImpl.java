package com.service.impl.common;

import java.lang.reflect.ParameterizedType;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.service.common.BaseService;
import com.util.CollectionUtil;
import com.util.StringUtil;

public class BaseServiceImpl<T> implements BaseService<T> {
	private Class clazz;
	@Autowired 
	private SessionFactory sessionFactory;
	protected Session getSession(){
		return sessionFactory.getCurrentSession();
	}
	public BaseServiceImpl(){
		ParameterizedType type = (ParameterizedType) this.getClass().getGenericSuperclass();
		clazz = (Class) type.getActualTypeArguments()[0];
	}
	/**
	 * 通过id获取对象
	 */
	public T loadById(long id) {
		// TODO Auto-generated method stub
		return (T) getSession().get(clazz, id);
	}
	/**
	 * 通过属性获取查询结果
	 */
	public List<T> loadByProperty(String property, Object value) {
		// TODO Auto-generated method stub
		String hql = "from " + clazz.getSimpleName()+" where "+property+"='"+value+"'";
		return getSession().createQuery(hql).list();
	}
	/**
	 * 保存数据
	 */
	@SuppressWarnings("unchecked")
	public T save(T t) {
		// TODO Auto-generated method stub
		 Long id = (Long) getSession().save(t);
		 return loadById(id);
	}
	/**
	 * 根据条件查询数据,并实现分页跟排序
	 */
	public List<T> query(String whereCondition,int pageStart,int pageSize,String orderBy,String order) {
		// TODO Auto-generated method stub
		String hql = "from " + clazz.getSimpleName()+" where 1=1 "+whereCondition;
		if(!StringUtil.isEmptyString(orderBy)){
			hql +=" order by "+orderBy+" "+order;
		}
		return getSession().createQuery(hql).setFirstResult(pageStart).setMaxResults(pageSize).list();
	}
	/**
	 * 更新数据
	 */
	public T update(T t) {
		// TODO Auto-generated method stub
		 getSession().update(t);
		 return t;
	}
	/**
	 * 删除数据
	 */
	public void delete(T t) {
		// TODO Auto-generated method stub
		getSession().delete(t);
	}
	/**
	 * 获得根据条件查询的所有行数
	 */
	public long getCount(String whereCondition) {
		// TODO Auto-generated method stub
		String hql = "from " + clazz.getSimpleName()+" where 1=1 "+whereCondition;
		List list = getSession().createQuery(hql).list();
		if(!CollectionUtil.isEmptyCollection(list)){
			return list.size();
		}
		return 0;
	}
	/**
	 * 根据id删除数据
	 */
	public int deleteById(Long id) {
		// TODO Auto-generated method stub
		String tableName=clazz.getSimpleName().replaceFirst(clazz.getSimpleName().substring(0, 1),clazz.getSimpleName().substring(0, 1).toLowerCase());
		String hql = "delete from " + clazz.getSimpleName()+" where "+tableName+"Id='"+id+"'";
		return getSession().createQuery(hql).executeUpdate();
	}
}
