package com.eugenefe.entity;

import java.io.Serializable;
import java.util.List;
import java.util.Set;

public interface ITree extends Serializable{
	
	public String getId();
//	public String getName();
	
//	public void add(ITree node);
//	public void remove(ITree node);

	public List<? extends ITree> getChildren();
//	public int getChildrenSize();
//	public ITree getChild(String id);
	
//	public ITree getChildren(String id);
	
	
  
}
