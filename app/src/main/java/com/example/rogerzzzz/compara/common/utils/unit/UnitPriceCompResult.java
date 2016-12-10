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
			return  "Products with the same unit price";
		}else if(cheaperPrd == -1){
			return " cannot be compared";
		}else{
			return "Product " + cheaperPrd + " is cheaper for " + getUnitPriceDifference() + " per " +CompareUnit + " and money save is " + getMoneySave();
		}
	}
	
	public double getUnitPriceDifference(){
		return expensiverPrdUnitPrice - cheaperPrdUnitPrice;
	}
	
	
	public double getMoneySave(){
		return (expensiverPrdUnitPrice - cheaperPrdUnitPrice) * cheaperPrdUnitQuality;
	}
}
