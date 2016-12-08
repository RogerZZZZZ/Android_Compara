package com.example.rogerzzzz.compara.common.utils.unit;

public class UnitPriceCompResult {
	public int cheaperPrd;
	
	public String cheaperPrdName;
	public double cheaperPrdUnitPrice;
	public double cheaperPrdUnitQuality;
	
	public String expensivePrdName;
	public double expensiverPrdUnitPrice;
	public double expensivePrdUnitQuality;
	
	public String CompareUnit;

	
	
	public String getMsg(){
		if(cheaperPrd == 0){
			return cheaperPrdName + " and " + expensivePrdName + " with the same unit price";
		}else if(cheaperPrd == -1){
			return cheaperPrdName + " and " + expensivePrdName + " cannot be compared";
		}else{
			return cheaperPrdName + " cheaper than " + expensivePrdName +" for " + getUnitPriceDifference() + " per " +CompareUnit;
		}
	}
	
	public double getUnitPriceDifference(){
		return expensiverPrdUnitPrice - cheaperPrdUnitPrice;
	}
	
	
	public double getMoneySave(){
		return (expensiverPrdUnitPrice - cheaperPrdUnitPrice) * cheaperPrdUnitQuality;
	}
}
