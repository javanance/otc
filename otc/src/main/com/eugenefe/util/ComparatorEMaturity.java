package com.eugenefe.util;

import java.util.Comparator;

import com.eugenefe.enums.EMaturity;

public class ComparatorEMaturity implements Comparator<EMaturity>{
	@Override
	public int compare(EMaturity myMaturity, EMaturity other) {
		return ((Integer)myMaturity.numDays).compareTo(other.numDays);
	}

}
