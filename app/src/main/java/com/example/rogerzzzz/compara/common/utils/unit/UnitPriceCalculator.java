package com.example.rogerzzzz.compara.common.utils.unit;
import java.util.HashMap;
import java.util.Map;


public class UnitPriceCalculator {
	//return value
	public HashMap<String, String> returnValue;
	public enum unit{KG, GM, g, dozen}; 
	
	public final String keyNumberOfUnitPricePair = "[numberOfUitPricePair]";
	public final String keyPrice = "[price]";
	public final String keyUnit = "[unit]";
	
	private double invalidDoubleValue = -999;
	private int invalidIntValue = -999;
	
	
	public HashMap<String, String> calculateUnitPrice(String priceTagString){
		returnValue =  new HashMap();
		
		//split the string for processing in the later stage
		String[] splitedPriceTageStringValue = priceTagString.split("\\s+");
		
		
		//for debugging
		System.out.println("before pre-processing");
		for(int i=0; i<splitedPriceTageStringValue.length ; i++){
			System.out.println(i+"item: " + splitedPriceTageStringValue[i]);
		}
		
		
		//pre-processing
		for(int i=0; i<splitedPriceTageStringValue.length ; i++){
			//trim space in string of the array
			splitedPriceTageStringValue[i] = splitedPriceTageStringValue[i].trim();
			
			
			//combine price tag data in a single string for easy handling afterwards
			if(splitedPriceTageStringValue[i].contains(".")){ 
				if(splitedPriceTageStringValue[i].length() == 1){
					if(i != 0 && i+1 < splitedPriceTageStringValue.length ){
						//combine the price string into 1 string value, if it is splited into to string
						//eg. splitedPriceTageStringValue[i-1] = 1
						//    splitedPriceTageStringValue[i]   = .
						//    splitedPriceTageStringValue[i+1] = 6
					
						splitedPriceTageStringValue[i] = splitedPriceTageStringValue[i-1].trim() + splitedPriceTageStringValue[i].trim() + splitedPriceTageStringValue[i+1].trim();
						splitedPriceTageStringValue[i-1] = "";
						splitedPriceTageStringValue[i+1] = "";
						
						if (splitedPriceTageStringValue[i].contains("/") && 
								splitedPriceTageStringValue[i].indexOf("/") == splitedPriceTageStringValue[i].length() -1){
							if(i+2 < splitedPriceTageStringValue.length){
								splitedPriceTageStringValue[i] = splitedPriceTageStringValue[i].trim() + splitedPriceTageStringValue[i+2].trim();
								splitedPriceTageStringValue[i+2] = "";
							}
						}
					}
				}else if(splitedPriceTageStringValue[i].indexOf(".") == 0){
					splitedPriceTageStringValue[i-1] = splitedPriceTageStringValue[i-1].trim() + splitedPriceTageStringValue[i].trim();
					splitedPriceTageStringValue[i] = "";
					
					if (splitedPriceTageStringValue[i-1].contains("/") && 
							splitedPriceTageStringValue[i-1].indexOf("/") == splitedPriceTageStringValue[i-1].length() -1){
						if(i+1 < splitedPriceTageStringValue.length){
							splitedPriceTageStringValue[i-1] = splitedPriceTageStringValue[i-1].trim() + splitedPriceTageStringValue[i+1].trim();
							splitedPriceTageStringValue[i+1] = "";
						}
					}
					
				}else if(splitedPriceTageStringValue[i].indexOf(".") == splitedPriceTageStringValue[i].length() -1){
					if( i+1 < splitedPriceTageStringValue.length){
						splitedPriceTageStringValue[i] = splitedPriceTageStringValue[i].trim() + splitedPriceTageStringValue[i + 1].trim();
						splitedPriceTageStringValue[i + 1] = "";
						
						
						if (splitedPriceTageStringValue[i].contains("/") && 
								splitedPriceTageStringValue[i].indexOf("/") == splitedPriceTageStringValue[i].length() -1){
							if(i+2 < splitedPriceTageStringValue.length){
								splitedPriceTageStringValue[i] = splitedPriceTageStringValue[i].trim() + splitedPriceTageStringValue[i+2].trim();
								splitedPriceTageStringValue[i+2] = "";
							}
						}
						
					}
				}else{
					if (splitedPriceTageStringValue[i].contains("/") && 
							splitedPriceTageStringValue[i].indexOf("/") == splitedPriceTageStringValue[i].length() -1){
						if(i+1 < splitedPriceTageStringValue.length){
							splitedPriceTageStringValue[i] = splitedPriceTageStringValue[i].trim() + splitedPriceTageStringValue[i+1].trim();
							splitedPriceTageStringValue[i+1] = "";
						}
					}
				}
			
				
			}else if(splitedPriceTageStringValue[i].contains("/")){ 
				
				
					if(splitedPriceTageStringValue[i].length() == 1){
				
						//combine the price string into 1 string value, if it is splited into to string
						//eg. splitedPriceTageStringValue[i-1] = 10.5
						//    splitedPriceTageStringValue[i]   = /
						//    splitedPriceTageStringValue[i+1] = 6
						splitedPriceTageStringValue[i] = splitedPriceTageStringValue[i-1].trim() + splitedPriceTageStringValue[i].trim() + splitedPriceTageStringValue[i+1].trim();
						splitedPriceTageStringValue[i-1] = "";
						splitedPriceTageStringValue[i+1] = "";
					}else if(splitedPriceTageStringValue[i].indexOf("/") == 0){
						if(i != 0){
							splitedPriceTageStringValue[i-1] = splitedPriceTageStringValue[i-1].trim() + splitedPriceTageStringValue[i].trim();
							splitedPriceTageStringValue[i] = "";
						}
					}else if(splitedPriceTageStringValue[i].indexOf("/") == splitedPriceTageStringValue[i].length() -1){
						
						if( i+1 < splitedPriceTageStringValue.length){
							splitedPriceTageStringValue[i] = splitedPriceTageStringValue[i].trim() + splitedPriceTageStringValue[i + 1].trim();
							splitedPriceTageStringValue[i + 1] = "";
						}
					}
				
			}else if(!containsUnitEnum(splitedPriceTageStringValue[i]).equalsIgnoreCase("")){ 
				String unit = containsUnitEnum(splitedPriceTageStringValue[i]);
				
				if(splitedPriceTageStringValue[i].length() != unit.length()){
					splitedPriceTageStringValue[i] = splitedPriceTageStringValue[i].replace(unit, "") + " " + unit;
				}else{
					if( i == 0){
						splitedPriceTageStringValue[i] = "";
					}else{
						splitedPriceTageStringValue[i -1] = splitedPriceTageStringValue[i -1].trim() + " "+ unit;
						
						splitedPriceTageStringValue[i] = "";
					}
				}
				//put the unit and number into a string for processing later
		
			}
		}
		
		
		//for debugging
		System.out.println("after pre-processing");
		for(int i=0; i<splitedPriceTageStringValue.length ; i++){
			System.out.println(i+"item: " + splitedPriceTageStringValue[i]);
		}
		
		
		int returnValueCounter=0;
		double currentUnitPrice =0;
		String currentUnit = "";
		//calculation of unit price
		for(int i=0; i<splitedPriceTageStringValue.length ; i++){
			
			// for handle the price string 
			if(splitedPriceTageStringValue[i] != null &&
					splitedPriceTageStringValue[i].contains(".")){
				
				//for handle the price string with "/" eg. 25.0/2 units
				if(splitedPriceTageStringValue[i].contains("/")){
					
					String[] splitedPrceString =  splitedPriceTageStringValue[i].split("\\/");
					
					if(splitedPrceString.length == 2){
						double price = StringToDouble(splitedPrceString[0]);
						double numberOfUnit = StringToDouble(splitedPrceString[1]);
						
						if(price != -999 && numberOfUnit != invalidDoubleValue){
							double unitPrice = price/numberOfUnit;
						
							if(currentUnitPrice == 0 || currentUnitPrice > unitPrice){
								currentUnitPrice = unitPrice;
								if(i+1 <  splitedPriceTageStringValue.length && !splitedPriceTageStringValue[i+1].equalsIgnoreCase("")){
									currentUnit = splitedPriceTageStringValue[i+1];
								}else{
									currentUnit= "unit";
								}
							}
						}
						
					}
				}else{
					double unitPrice = StringToDouble(splitedPriceTageStringValue[i]);
					
					if(unitPrice != invalidDoubleValue){
						if(currentUnitPrice == 0 || currentUnitPrice > unitPrice){
							currentUnitPrice = unitPrice;
							currentUnit = "unit";
						}
					}
				}
			}
		}
		
		//store the current unit price to the return Harsh Map
		if(currentUnitPrice !=0 && !currentUnit.equalsIgnoreCase("")){
			returnValueCounter ++;
			returnValue.put(this.keyPrice + returnValueCounter, currentUnitPrice+"");
			returnValue.put(this.keyUnit + returnValueCounter, currentUnit);
		}
		
		//for debugging
		System.out.println("after calculation of current unit Price");
		for(Map.Entry<String, String>entry : returnValue.entrySet()){
			String key = entry.getKey();
			String value = entry.getValue();
			
			System.out.println("key: " + key + " value: " + value );
			
		}
		
		
		//for calculation of price per unit eg. KG, GM
		for(int i=0; i<splitedPriceTageStringValue.length ; i++){
			if(splitedPriceTageStringValue[i] != null &&
					!containsUnitEnum(splitedPriceTageStringValue[i]).equalsIgnoreCase("")){
				String[] splitedUnitArray = splitedPriceTageStringValue[i].split("\\s+");

				
				if(splitedUnitArray.length == 2){
					double NumberUnit = StringToDouble(splitedUnitArray[0]);
					
					if(NumberUnit != this.invalidDoubleValue){
						double tmpUnitPrice = currentUnitPrice/ NumberUnit;
					
						returnValueCounter ++;
						returnValue.put(this.keyPrice + returnValueCounter, tmpUnitPrice+"");
						returnValue.put(this.keyUnit + returnValueCounter, splitedUnitArray[1]);
					}
				}
				
				
				
			}
		}
		
		
		//for debugging
		System.out.println("after calculation of price per unit");
		for(Map.Entry<String, String>entry : returnValue.entrySet()){
			String key = entry.getKey();
			String value = entry.getValue();
			
			System.out.println("key: " + key + " value: " + value );
			
		}
		

		
		returnValue.put(this.keyNumberOfUnitPricePair, ""+returnValueCounter);
	
		return this.returnValue;
	}
	
	
	
	private double StringToDouble(String _string){
		double returnValue = invalidDoubleValue;
		
		try{
			returnValue = Double.parseDouble(_string);
		}catch(Exception e){
			returnValue = invalidDoubleValue;
		}
		return	returnValue;	
	}
	
	private int StringToInteger(String _string){
		int returnValue = invalidIntValue;
		
		try{
			returnValue = Integer.parseInt(_string);
		}catch(Exception e){
			returnValue = invalidIntValue;
		}
		return returnValue;
	}
	
	
	public String containsUnitEnum(String _string) {

		String ExistedEnumName = "";
	    for (unit c : unit.values()) {
	        if (_string.contains(c.name())) {
	        	ExistedEnumName = c.name();
	        }
	    }

	    return ExistedEnumName;
	}
	
	static public void main(String[] arg){
		System.out.println("testing");
		
		UnitPriceCalculator unitPriceCalculator = new UnitPriceCalculator();
		HashMap<String, String> result = unitPriceCalculator.calculateUnitPrice("2.5/2 unit  safioewnfaiafiie  djaoifjeifj dfsjaoiej 2.6 3.5  3 KG 6GM   7     g");
		
		
		//sample for getting the price and unit
		for(int i=1; i<= Integer.parseInt(result.get(unitPriceCalculator.keyNumberOfUnitPricePair)); i++ ){
			String unit = result.get(unitPriceCalculator.keyUnit + i);
			String price = result.get(unitPriceCalculator.keyPrice + i);
		
			System.out.println("unit: "+ unit + " price: " +price );
		}
		
		
	}
	
}
