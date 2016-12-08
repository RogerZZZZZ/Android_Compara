package com.example.rogerzzzz.compara.common.utils.unit;

import java.util.ArrayList;

public class UnitPairsMapping {
	ArrayList<UnitPairs> unitPairsMapping ;
	
	
	public UnitPairsMapping(){
		this.unitPairsMapping = new ArrayList<UnitPairs>();
	}
	
	public void add(UnitPairs unitParis){
		unitPairsMapping.add(unitParis);
	}
	
	public UnitPairs search(String unit1, String unit2){
		UnitPairs returnUnitParis = null;
		
		for(int i =0; i< this.unitPairsMapping.size(); i++){
			if(unitPairsMapping.get(i).unit1.unit.equalsIgnoreCase(unit1) &&
					unitPairsMapping.get(i).unit2.unit.equalsIgnoreCase(unit2)){
				returnUnitParis = unitPairsMapping.get(i);
				break;
			}
		}
		
		return returnUnitParis;
	}
	
}
