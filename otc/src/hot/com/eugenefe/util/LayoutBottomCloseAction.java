package com.eugenefe.util;

import java.io.Serializable;

import org.jboss.seam.ScopeType;
import org.jboss.seam.annotations.AutoCreate;
import org.jboss.seam.annotations.Create;
import org.jboss.seam.annotations.Factory;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.annotations.Observer;
import org.jboss.seam.annotations.Out;
import org.jboss.seam.annotations.Scope;
import org.jboss.seam.annotations.Startup;
import org.primefaces.event.TabChangeEvent;

import com.eugenefe.entity.MarketVariable;


@Name("layoutBottomCloseAction")
@Scope(ScopeType.CONVERSATION)
//@AutoCreate
//@Startup
//@Scope(ScopeType.CONVERSATION)
public class LayoutBottomCloseAction implements Serializable {

//	@In
	@Out
	private boolean layoutBottomClosed ;
	
	public LayoutBottomCloseAction() {
//		System.out.println("Layout Bottom Constructor");
//		layoutBottomClosed = true;
	}
	
	@Create
	public void init(){
		System.out.println("Layout Bottom initalize");
		layoutBottomClosed = true;
	}
	
	public boolean isLayoutBottomClosed() {
		return layoutBottomClosed;
	}

	public void setLayoutBottomClosed(boolean layoutBottomClosed) {
		this.layoutBottomClosed = layoutBottomClosed;
	}
	
//	@Factory(value="layoutBottomClosed")
	public boolean initClose(){
		System.out.println("Layout Bottom Factory");
		return false;
	}
	
	public void onProductTabChange(TabChangeEvent event){
		System.out.println("On Tab Change" + event.getTab().getTitle());
		layoutBottomClosed = event.getTab().getTitle().equals("tabStock");
	}
	

//	@Observer("selectProduct")
	public void changeCollapsed(MarketVariable selectedProduct){
		if(selectedProduct.getProductYN().equals("N")){
			layoutBottomClosed = true;
		}
		else {
			layoutBottomClosed = false;
		}
	}
	
	
}
