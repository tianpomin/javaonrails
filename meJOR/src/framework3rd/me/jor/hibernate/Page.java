package me.jor.hibernate;

import java.util.List;

/**
 * 与me.jor.hibernate.AbstractHibernateBaseDao一起使用。<br/>
 * 作为保存分页查询结果的对象<br/>
 * list: 查询结果集<br/>
 * current:当前页码<br/>
 * total:总页数<br/>
 * count:总记录数
 * */
public class Page<T> {
	private List<T> list;//查询结果集
	private int current;//当前页码
	private int total;//总页数
	private int count;//总记录数
	
	public Page(){}
	public Page(List<T> list, int current, int count, int total) {
		this.list = list;
		this.current = current;
		this.total = total;
		this.count=count;
	}
	
	public List<T> getList() {
		return list;
	}
	public void setList(List<T> list) {
		this.list = list;
	}
	public int getCurrent() {
		return current;
	}
	public void setCurrent(int current) {
		this.current = current;
	}
	public int getTotal() {
		return total;
	}
	public void setTotal(int total) {
		this.total = total;
	}
	public int getCount() {
		return count;
	}
	public void setCount(int count) {
		this.count = count;
	}
}