package com.eugenefe.entity;

import java.util.List;
import java.util.Map;

public class HiFiveResult {
	private double theoPrice;
	private Map<String, Double> delta;
	private Map<String, Double> gamma;
	private Map<String, Double> vega;
	private double theta;
	private double rho;
	private List<Double> strikeProbability;
	
	public HiFiveResult(double theoPrice, Map<String, Double> delta, Map<String, Double> gamma, Map<String, Double> vega,
			double theta, double rho, List<Double> strikeProbability) {
		this.theoPrice = theoPrice;
		this.delta = delta;
		this.gamma = gamma;
		this.vega = vega;
		this.theta = theta;
		this.rho = rho;
		this.strikeProbability = strikeProbability;
	}
	public HiFiveResult() {
	}
	
	public double getTheoPrice() {
		return theoPrice;
	}
	public void setTheoPrice(double theoPrice) {
		this.theoPrice = theoPrice;
	}
	public Map<String, Double> getDelta() {
		return delta;
	}
	public void setDelta(Map<String, Double> delta) {
		this.delta = delta;
	}
	public Map<String, Double> getGamma() {
		return gamma;
	}
	public void setGamma(Map<String, Double> gamma) {
		this.gamma = gamma;
	}
	public Map<String, Double> getVega() {
		return vega;
	}
	public void setVega(Map<String, Double> vega) {
		this.vega = vega;
	}
	public double getTheta() {
		return theta;
	}
	public void setTheta(double theta) {
		this.theta = theta;
	}
	public double getRho() {
		return rho;
	}
	public void setRho(double rho) {
		this.rho = rho;
	}
	public List<Double> getStrikeProbability() {
		return strikeProbability;
	}
	public void setStrikeProbability(List<Double> strikeProbability) {
		this.strikeProbability = strikeProbability;
	}
	
	
}
