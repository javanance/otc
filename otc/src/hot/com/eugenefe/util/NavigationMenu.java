package com.eugenefe.util;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;

import org.jboss.seam.ScopeType;
import org.jboss.seam.annotations.Begin;
import org.jboss.seam.annotations.Create;
import org.jboss.seam.annotations.Logger;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.annotations.Scope;
import org.jboss.seam.annotations.Startup;
import org.jboss.seam.annotations.web.RequestParameter;
import org.jboss.seam.log.Log;
import org.primefaces.component.tieredmenu.TieredMenu;
import org.primefaces.model.menu.DefaultMenuItem;
import org.primefaces.model.menu.DefaultMenuModel;
import org.primefaces.model.menu.DefaultSubMenu;
import org.primefaces.model.menu.Submenu;
//import org.primefaces.model.menu.DefaultMenuItem;
//import org.primefaces.model.menu.DefaultMenuModel;
//import org.primefaces.model.menu.DefaultSubMenu;
//import org.primefaces.model.menu.MenuElement;
//import org.primefaces.model.menu.MenuItem;
//import org.primefaces.model.menu.MenuModel;
import org.primefaces.model.menu.MenuModel;

import com.eugenefe.entity.IrCurve;

@Name("navigationMenu")
@Scope(ScopeType.SESSION)
@Startup
public class NavigationMenu {
	@Logger
	private Log log;

	private MenuModel menuModel;
	private DefaultSubMenu subMenu;
	private DefaultSubMenu upperSubMenu;
	// private Set<String> groups = new HashSet<String>();
//	private List<String> groups = new ArrayList<String>();
//	private Map<String, List<String>> upperGroups = new HashMap<String, List<String>>();

	public NavigationMenu() {
		// System.out.println("Construction NavigationMenu");
	}

	/*
	 * @Create public void create() { menuModel = new DefaultMenuModel(); //
	 * subMenu = new DefaultSubMenu(); // subMenu.setLabel("AAA");
	 * DefaultMenuItem item;
	 * 
	 * // for (ENavigationData navi : ENavigationData.values()) { //
	 * groups.add(navi.getGroup()); // }
	 * 
	 * groups.add("Master"); groups.add("Product"); groups.add("OTC");
	 * groups.add("Position"); groups.add("Portfolio"); groups.add("History");
	 * groups.add("Pricing");
	 * 
	 * 
	 * 
	 * for (String aa : groups) { log.info("NavigationMenu:#0", aa); subMenu =
	 * new DefaultSubMenu(); subMenu.setLabel(aa); for (ENavigationData navi :
	 * ENavigationData.values()) { if(aa.equals(navi.getGroup())){ item = new
	 * DefaultMenuItem(); item.setValue(navi.getShortName());
	 * item.setTitle(navi.getQualifiedName());
	 * 
	 * item.setParam("navigation", navi.getShortName());
	 * item.setIncludeViewParams(true);
	 * 
	 * item.setOutcome("/view/v900DataNavigation");
	 * 
	 * subMenu.addElement(item); // log.info("NavigationMenu:#0",
	 * subMenu.getLabel()); } } menuModel.addElement(subMenu); } }
	 */

	/*@Create
	public void create() {
		menuModel = new DefaultMenuModel();
		DefaultMenuItem item;
		for (Map.Entry<String, List<EMenuGroup>> entry : EMenuGroup.getChildMap().entrySet()) {
			upperSubMenu = new DefaultSubMenu();
			upperSubMenu.setLabel(entry.getKey());

			for (EMenuGroup aa : entry.getValue()) {
				log.info("NavigationMenu:#0", aa);
				subMenu = new DefaultSubMenu();
				subMenu.setLabel(aa.name());
				for (ENavigationData navi : ENavigationData.values()) {
					if (aa.equals(navi.getGroup())) {
						item = new DefaultMenuItem();
						item.setValue(navi.getShortName());
						item.setTitle(navi.getQualifiedName());

						item.setParam("navigation", navi.getShortName());
						item.setIncludeViewParams(true);

						item.setOutcome("/view/v900DataNavigation");

						subMenu.addElement(item);
						// log.info("NavigationMenu:#0", subMenu.getLabel());
					}
				}
				upperSubMenu.addElement(subMenu);
			}
			menuModel.addElement(upperSubMenu);
		}

	}
*/
	
	@Create
	public void create() {
		menuModel = new DefaultMenuModel();
		
		for(EMenuGroup aa : EMenuGroup.ROOT.getChidrenGroup()){
			upperSubMenu = new DefaultSubMenu();
			upperSubMenu.setLabel(aa.name());
			recursive(upperSubMenu, aa);
			menuModel.addElement(upperSubMenu);
		}
	}

	private void recursive(DefaultSubMenu upperSubMenu, EMenuGroup group) {
		DefaultMenuItem item;
		for (ENavigationData navi : ENavigationData.getElement(group)) {
				item = new DefaultMenuItem();
				item.setValue(navi.getShortName());
				item.setTitle(navi.getQualifiedName());

				item.setParam("navigation", navi.getShortName());
				item.setIncludeViewParams(true);

				item.setOutcome("/view/v900DataNavigation");

				upperSubMenu.addElement(item);
				// log.info("NavigationMenu:#0", subMenu.getLabel());
//				log.info("NavigationMenu1:#0,#1", upperSubMenu.getLabel(),  item.getValue()	);
			}
		if(!group.getChidrenGroup().isEmpty()){
			for (EMenuGroup aa : group.getChidrenGroup()) {
				DefaultSubMenu tempMenu = new DefaultSubMenu();
				tempMenu.setLabel(aa.name());
				
				recursive(tempMenu, aa);
				upperSubMenu.addElement(tempMenu);
				
			}	
		}
	}
	
	// ****************Getter and Setter***********
	public MenuModel getMenuModel() {
		return menuModel;
	}

	public void setMenuModel(MenuModel menuModel) {
		this.menuModel = menuModel;
	}

	public DefaultSubMenu getSubMenu() {
		return subMenu;
	}

	public void setSubMenu(DefaultSubMenu subMenu) {
		this.subMenu = subMenu;
	}
}
