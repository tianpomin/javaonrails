package me.jor.roa.core;

import java.util.Map;
import java.util.Set;

import me.jor.roa.core.accessable.Accessable;
import me.jor.roa.core.accessable.Result;

public abstract class CRUDAccess<E extends Accessable> implements Accessable{

	private String defaultDataType;
	private String defaultErrorType;
	
	private Interceptor interceptor;
	
	private Map<String, Result> resultMap;
	
	private String[] dependingResources;
	
	private E tag;
	
	CRUDAccess(String defaultDataType, String defaultErrorType,Interceptor interceptor, E tag, String... dependingResources){
		this.defaultDataType=defaultDataType;
		this.defaultErrorType=defaultErrorType;
		this.interceptor=interceptor;
		this.tag=tag;
		this.dependingResources=dependingResources;
	}
	@Override
	public Object access(ResourceAccessContext context) {
		return interceptor.intercept(context);
	}
	
	Object accessTag(ResourceAccessContext context){
		return tag.access(context);
	}

	public String getDefaultDataType() {
		return defaultDataType;
	}

	public void setDefaultDataType(String defaultDataType) {
		this.defaultDataType = defaultDataType;
	}

	public String getDefaultErrorType() {
		return defaultErrorType;
	}

	public void setDefaultErrorType(String defaultErrorType) {
		this.defaultErrorType = defaultErrorType;
	}

	public String[] getDependingResources() {
		return dependingResources;
	}

	public void setDependingResources(String[] dependingResources) {
		this.dependingResources = dependingResources;
	}

	public Set<String> getResultNames(){
		return resultMap.keySet();
	}
	
	void addResult(String name, Result result){
		resultMap.put(name, result);
	}
	Result getResult(String name){
		return resultMap.get(name);
	}
}
