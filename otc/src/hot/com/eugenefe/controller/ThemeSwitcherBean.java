package com.eugenefe.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import org.jboss.seam.ScopeType;
import org.jboss.seam.annotations.Create;
import org.jboss.seam.annotations.In;
import org.jboss.seam.annotations.Logger;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.annotations.Out;
import org.jboss.seam.annotations.Scope;
import org.jboss.seam.log.Log;

@Name("themeSwitcher")
@Scope(ScopeType.SESSION)
public class ThemeSwitcherBean {
	@Logger
	private Log log;

	private Map<String, String> themes;

	private List<Theme> advancedThemes;

	private String theme;

	@In(create=true)
//	@Out
	private GuestPreferences guestPref;

	public ThemeSwitcherBean() {

	}

	@Create()
	public void init() {
		if ( guestPref== null){
			log.info("Prep Null");
		}
		log.info("Prep:#0", guestPref.getTheme());
		theme = guestPref.getTheme();
		// theme = "aristo";

		advancedThemes = new ArrayList<Theme>();
		advancedThemes.add(new Theme("aristo", "aristo.png"));
		advancedThemes.add(new Theme("cupertino", "cupertino.png"));
		advancedThemes.add(new Theme("trontastic", "trontastic.png"));

		themes = new TreeMap<String, String>();
		themes.put("Aristo", "aristo");
		themes.put("ARedGrey", "redgrey");
		themes.put("Black-Tie", "black-tie");
		themes.put("Blitzer", "blitzer");
		themes.put("Bluesky", "bluesky");
		themes.put("Casablanca", "casablanca");
		themes.put("Cupertino", "cupertino");
		themes.put("Dark-Hive", "dark-hive");
		themes.put("Dot-Luv", "dot-luv");
		themes.put("Eggplant", "eggplant");
		themes.put("Excite-Bike", "excite-bike");
		themes.put("Flick", "flick");
		themes.put("Glass-X", "glass-x");
		themes.put("Hot-Sneaks", "hot-sneaks");
		themes.put("Humanity", "humanity");
		themes.put("Le-Frog", "le-frog");
		themes.put("Midnight", "midnight");
		themes.put("Mint-Choc", "mint-choc");
		themes.put("Overcast", "overcast");
		themes.put("Pepper-Grinder", "pepper-grinder");
		themes.put("Redmond", "redmond");
		themes.put("Rocket", "rocket");
		themes.put("Sam", "sam");
		themes.put("Smoothness", "smoothness");
		themes.put("South-Street", "south-street");
		themes.put("Start", "start");
		themes.put("Sunny", "sunny");
		themes.put("Swanky-Purse", "swanky-purse");
		themes.put("Trontastic", "trontastic");
		themes.put("UI-Darkness", "ui-darkness");
		themes.put("UI-Lightness", "ui-lightness");
		themes.put("Vader", "vader");
	}

	public Map<String, String> getThemes() {
		return themes;
	}

	public void setThemes(Map<String, String> themes) {
		this.themes = themes;
	}

	public String getTheme() {
		return theme;
	}

	public void setTheme(String theme) {
		this.theme = theme;
	}

	public GuestPreferences getGuestPref() {
		return guestPref;
	}

	public void setGuestPref(GuestPreferences guestPref) {
		this.guestPref = guestPref;
	}

	public void saveTheme() {
//		log.info("Selected Theme1 :#0", guestPref.getTheme());
		guestPref.setTheme(theme);
		log.info("Selected Theme :#0", guestPref.getTheme());
	}

	public List<Theme> getAdvancedThemes() {
		return advancedThemes;
	}

}
