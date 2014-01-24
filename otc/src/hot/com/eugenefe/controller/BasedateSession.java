package com.eugenefe.controller;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

import org.jboss.seam.ScopeType;
import org.jboss.seam.annotations.Begin;
import org.jboss.seam.annotations.Create;
import org.jboss.seam.annotations.In;
import org.jboss.seam.annotations.Logger;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.annotations.Out;
import org.jboss.seam.annotations.Scope;
import org.jboss.seam.annotations.Startup;
import org.jboss.seam.core.Conversation;
import org.jboss.seam.core.Events;
import org.jboss.seam.framework.CurrentDate;
import org.jboss.seam.log.Log;
import org.primefaces.event.SelectEvent;

import com.eugenefe.entity.Basedate;
import com.eugenefe.enums.EMaturity;
import com.eugenefe.session.BasedateList;
import com.eugenefe.util.FnCalendar;

@Name("basedateSession")
@Scope(ScopeType.SESSION)
@Startup
// @Scope(ScopeType.CONVERSATION)
public class BasedateSession implements Serializable {

	@Logger
	private Log log;

	// @In(create = true)
	// private BasedateList basedateList;

	// @In
	private Date currentDate;

	private FnCalendar cal;

	private Date baseDate;
	private Date stDate;
	private Date endDate;

	public BasedateSession() {
	}

	@Create
	public void init(){
		cal = FnCalendar.getInstance();
//		cal = new FnCalendar(2013, 4, 29);
		
		baseDate = cal.getTime();
		endDate =cal.getTime();
		stDate = cal.minusTerm(EMaturity.Y01, true).getTime();
	}

	// @Create
	public void initNew() {
		baseDate = currentDate;
		stDate = baseDate;
		endDate = baseDate;
	}

	// *********************************************
	public void handleDateSelect(SelectEvent event) {
		Events.instance().raiseEvent("evtBaseDateChange", (Date) (event.getObject()));
	}

	public void handleStartDateSelect(SelectEvent event) {
		Events.instance().raiseEvent("evtStartDateChange", (Date) (event.getObject()));
	}

	public void handleEndDateSelect(SelectEvent event) {
		Events.instance().raiseEvent("evtEndDateChange", (Date) (event.getObject()));
	}

	// *********************************Getter and Setter***********************

	public Date getBaseDate() {
		return baseDate;
	}

	public void setBaseDate(Date baseDate) {
		this.baseDate = baseDate;
	}

	public Date getStDate() {
		return stDate;
	}

	public void setStDate(Date stDate) {
		this.stDate = stDate;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}
}
