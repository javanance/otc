package com.eugenefe.util;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.ejb.Startup;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;

import org.jboss.seam.ScopeType;
import org.jboss.seam.annotations.Create;
import org.jboss.seam.annotations.In;
import org.jboss.seam.annotations.Logger;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.annotations.Observer;
import org.jboss.seam.annotations.Out;
import org.jboss.seam.annotations.Scope;
import org.jboss.seam.annotations.web.RequestParameter;
import org.jboss.seam.log.Log;

import com.eugenefe.entity.MarketVariable;
import com.eugenefe.util.RiskMeasureGroup;

@Name("layoutInit")
@Scope(ScopeType.CONVERSATION)
public class LayoutInit {
	@Logger	private Log log;
	 
	@In(create=true) 
	private Map<String, String> messages;
	
	private String viewId;
	private String viewUrl;
	
	private boolean renderRight;
	private boolean renderLeft;
	private boolean renderBottom;
	private boolean renderInnerTop;
	private boolean renderInnerRight;
	private boolean renderInnerBottom;
	
//	private Map<String, Renderer> rendererMap =new HashMap<String, Renderer>();
//	private Renderer renderer;

	public LayoutInit() {
		System.out.println("LayoutInit Construction");
	}

	public String getViewId() {
		return viewId;
	}
	public void setViewId(String viewId) {
		this.viewId = viewId;
	}
	
	public String getViewUrl() {
		return viewUrl;
	}
	public void setViewUrl(String viewUrl) {
		this.viewUrl = viewUrl;
	}
	
	public boolean isRenderRight() {
		return renderRight;
	}
	public void setRenderRight(boolean renderRight) {
		this.renderRight = renderRight;
	}
	public boolean isRenderLeft() {
		return renderLeft;
	}
	public void setRenderLeft(boolean renderLeft) {
		this.renderLeft = renderLeft;
	}
	public boolean isRenderBottom() {
		return renderBottom;
	}
	public void setRenderBottom(boolean renderBottom) {
		this.renderBottom = renderBottom;
	}
    public boolean isRenderInnerRight() {
		return renderInnerRight;
	}

	public void setRenderInnerRight(boolean renderInnerRight) {
		this.renderInnerRight = renderInnerRight;
	}

	public boolean isRenderInnerBottom() {
		return renderInnerBottom;
	}

	public void setRenderInnerBottom(boolean renderInnerBottom) {
		this.renderInnerBottom = renderInnerBottom;
	}
	
	public boolean isRenderInnerTop() {
		return renderInnerTop;
	}
	public void setRenderInnerTop(boolean renderInnerTop) {
		this.renderInnerTop = renderInnerTop;
	}

	//***********************************************************************
	public String setupLayout(){
//    	public String loadLayout(String view){    	
    	log.info("Layout : #0", viewId);
    	EViewNew temp = EViewNew.valueOf(viewId);
    	renderBottom = temp.isRenderBottom();
    	renderLeft = temp.isRenderLeft();
    	renderRight = temp.isRenderRight();
    	renderInnerTop = temp.isRenderInnerTop();
    	renderInnerRight =temp.isRenderInnerRight();
    	renderInnerBottom =temp.isRenderInnerBottom();
    	
    	viewUrl = temp.getUrl();
    	return viewId;
    }
    
//    public String loadLayout2(){
////    	public String loadLayout(String view){    	
//    	
//    	EViewNew temp = EViewNew.valueOf("v691");
//    	renderBottom = temp.isRenderBottom();
//    	renderLeft = temp.isRenderLeft();
//    	renderRight = temp.isRenderRight();
//    	return "v691";
//    }
    public void extractViewId(ActionEvent evt){
    	log.info("Layout : #0", evt.getComponent().getId());
    	viewId =evt.getComponent().getId();
    }
}
