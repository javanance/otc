package com.eugenefe.util;

import javax.faces.event.PhaseEvent;

import org.jboss.seam.ScopeType;
import org.jboss.seam.annotations.Logger;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.annotations.Observer;
import org.jboss.seam.annotations.Scope;
import org.jboss.seam.log.Log;


@Name("applicationPhaseListener")
@Scope(ScopeType.APPLICATION)
public class ApplicationListener {
	@Logger
	private Log log;

    /**
      * Called TRANSPARENTLY by Seam
      */
//    @Observer("org.jboss.seam.beforePhase")
    public void beforePhase(PhaseEvent event) {
    	 System.out.println("START PHASE " + event.getPhaseId());

    }

    /**
      * Called TRANSPARENTLY by Seam
      */
    @Observer("org.jboss.seam.afterPhase")
    public void afterPhase(PhaseEvent event) {
    	 System.out.println("END PHASE " + event.getPhaseId());
    }



}