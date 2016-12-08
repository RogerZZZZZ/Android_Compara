package com.example.rogerzzzz.compara.common.utils.unit;

public class UnitPriceComparator {
	
	UnitPairsMapping unitPairsMapping;
	
	public UnitPriceComparator(){
		this.unitPairsMapping = new UnitPairsMapping();
		unitPairsMapping.add(new UnitPairs(new Unit(1,"kg"), new Unit(1000,"g")));
		unitPairsMapping.add(new UnitPairs(new Unit(1,"L"), new Unit(1000,"ml")));
		unitPairsMapping.add(new UnitPairs(new Unit(1,"ml"), new Unit(1,"cm3")));
		unitPairsMapping.add(new UnitPairs(new Unit(1,"kg"), new Unit(2.20462262,"lb")));
		unitPairsMapping.add(new UnitPairs(new Unit(1,"g"), new Unit(0.00220462262,"lb")));
		unitPairsMapping.add(new UnitPairs(new Unit(1,"dozen"), new Unit(12,"unit")));
		unitPairsMapping.add(new UnitPairs(new Unit(1,"m"), new Unit(100,"cm")));
		unitPairsMapping.add(new UnitPairs(new Unit(1,"m"), new Unit(1000,"mm")));
		unitPairsMapping.add(new UnitPairs(new Unit(1,"cm"), new Unit(10,"mm")));
	}
	
	
	/**
	 * 
	 * @return
	 * -1 --> cannot compare
	 * 0 --> two production with same unit price
	 * 1 --> the first product is cheaper
	 * 2 --> the second product is cheaper
	 */
	public UnitPriceCompResult UnitPriceCompare(String prd1_Name, double prd1_price, double prd1_unit_quality,String prd1_unit,
			String prd2_Name, double prd2_price, double prd2_unit_quality,String prd2_unit){
		UnitPriceCompResult result = new UnitPriceCompResult();
		
		prd1_Name = prd1_Name.trim();
		prd1_unit = prd1_unit.trim();
		prd2_Name = prd2_Name.trim();
		prd2_unit = prd2_unit.trim();
		
		UnitPairs uniPairs = unitPairsMapping.search(prd1_unit, prd2_unit);
		
		
		if(uniPairs != null){
			double prd1_unitPrice = prd1_price/prd1_unit_quality;
			
			double prd2_unit_convered = prd2_unit_quality/uniPairs.unit2.quality * uniPairs.unit1.quality;
			double prd2_unitPrice = prd2_price/ prd2_unit_convered;
			
			if(prd1_unitPrice > prd2_unitPrice){
				// product 2 is cheaper
				result.cheaperPrd = 2;
				
				result.cheaperPrdName = prd2_Name;
				result.cheaperPrdUnitPrice = prd2_unitPrice;
				result.cheaperPrdUnitQuality = prd2_unit_convered;
				
				result.expensivePrdName = prd1_Name;
				result.expensiverPrdUnitPrice = prd1_unitPrice;
				result.expensivePrdUnitQuality = prd1_unit_quality;
				result.CompareUnit= uniPairs.unit1.unit;
				
				return result;
			}else if (prd1_unitPrice == prd2_unitPrice){
				//same product price
				result.cheaperPrd = 0;
				
				result.cheaperPrdName = prd2_Name;
				result.cheaperPrdUnitQuality = prd2_unit_convered;
				
				result.expensivePrdName = prd1_Name;
				result.expensivePrdUnitQuality = prd1_unit_quality;
				
				result.cheaperPrdUnitPrice = result.expensiverPrdUnitPrice = prd1_unitPrice;
				result.CompareUnit = uniPairs.unit1.unit;
				
				
			}else{
				//product 1 is cheaper
				result.cheaperPrd = 1;
				
				result.cheaperPrdName = prd1_Name;
				result.cheaperPrdUnitPrice = prd1_unitPrice;
				result.cheaperPrdUnitQuality = prd1_unit_quality;
				
				result.expensivePrdName = prd2_Name;
				result.expensiverPrdUnitPrice = prd2_unitPrice;
				result.expensivePrdUnitQuality = prd2_unit_convered;
				
				result.CompareUnit= uniPairs.unit1.unit;
			}

			return result;
		}else{
			uniPairs = unitPairsMapping.search(prd2_unit, prd1_unit);
		}
		
		
		if(uniPairs != null){
			double prd2_unitPrice = prd2_price/prd2_unit_quality;
			
			double prd1_unit_convered = prd1_unit_quality/uniPairs.unit2.quality * uniPairs.unit1.quality;
			double prd1_unitPrice = prd1_price/ prd1_unit_convered;
			
			if(prd1_unitPrice > prd2_unitPrice){
				// product 2 is cheaper
				result.cheaperPrd = 2;
				
				result.cheaperPrdName = prd2_Name;
				result.cheaperPrdUnitPrice = prd2_unitPrice;
				result.cheaperPrdUnitQuality = prd2_unit_quality;
				
				result.expensivePrdName = prd1_Name;
				result.expensiverPrdUnitPrice = prd1_unitPrice;
				result.expensivePrdUnitQuality = prd1_unit_convered;
				result.CompareUnit= uniPairs.unit1.unit;
				
			}else if (prd1_unitPrice == prd2_unitPrice){
				
				//same product price
				result.cheaperPrd = 0;
				
				result.cheaperPrdName = prd2_Name;
				result.cheaperPrdUnitQuality = prd2_unit_quality;
				
				result.expensivePrdName = prd1_Name;
				result.expensivePrdUnitQuality = prd1_unit_convered;
				
				result.cheaperPrdUnitPrice = result.expensiverPrdUnitPrice = prd1_unitPrice;
				result.CompareUnit = uniPairs.unit1.unit;
			}else{
				//product 1 is cheaper
				result.cheaperPrd = 1;
				
				result.cheaperPrdName = prd1_Name;
				result.cheaperPrdUnitPrice = prd1_unitPrice;
				result.cheaperPrdUnitQuality = prd1_unit_convered;
				
				result.expensivePrdName = prd2_Name;
				result.expensiverPrdUnitPrice = prd2_unitPrice;
				result.expensivePrdUnitQuality = prd2_unit_quality;
				
				result.CompareUnit= uniPairs.unit1.unit;
			}
		}else{
			//cannot compare
			result.cheaperPrd = -1;
			return result;
		}
		
		return result;
		
	}
	
//	static public void main(String[] arg){
//		UnitPriceComparator unitPrice = new UnitPriceComparator();
//		UnitPriceCompResult result = unitPrice.UnitPriceCompare("A", 20, 11, "unit",
//				"B", 24, 1,"dozen");
//
//		System.out.println("cheaper product ID: " + result.cheaperPrd);
//
//		System.out.println("cheaper product Name: " + result.cheaperPrdName);
//		System.out.println("cheaper product unit price: " + result.cheaperPrdUnitPrice);
//		System.out.println("cheaper product unit quality: " + result.cheaperPrdUnitQuality);
//
//		System.out.println("expensive product Name: " + result.expensivePrdName);
//		System.out.println("expensive product unit price: " + result.expensiverPrdUnitPrice);
//		System.out.println("expensive product unit quality: " + result.expensivePrdUnitQuality);
//
//		System.out.println("MoneySave: " + result.getMoneySave());
//
//		System.out.println("unit price difference: " + result.getUnitPriceDifference());
//
//		System.out.println(result.getMsg());
//	}
}
