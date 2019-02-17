package retailmanager.spyhunter272.com.utils;

import java.util.Scanner;


public class NumberToWord {

	public static String  NTOW(double total) {
	
		String single_digits[] = { "", " one", " two", " three", " four",
	                              " five", " six", " seven", " eight", " nine"};
	    
		String two_digits[] = { " ten", " eleven", " twelve", " thirteen", " fourteen",
	                     " fifteen", " sixteen", " seventeen", " eighteen", " nineteen"};
	 
	    String tens_multiple[] = {"", "", " twenty", " thirty", " forty", " fifty",
	                             " sixty", " seventy", " eighty", " ninety"};
	 
	    String tens_power[] = {"","",""," hundred", " thousand",""," lakh",""," corrode"};
	



    	int inum = (int)total;
    	String rs = "";

    	int len = String.valueOf(inum).length();

    	if(inum==0)return "";

    	for(int i=len;i>0;i--){
    		
    		int temp=0;
    		
    		
    		if(i==8){
    			temp=inum/10000000;
    			inum=inum-(10000000*temp);
    			
    			rs=rs+single_digits[temp];
    			rs=rs+tens_power[i];
    			
    		}
    		
    		if(i==7){
    			temp=inum/1000000;
    			inum=inum-(1000000*temp);
    			
    			if(temp==1){
    				
    				temp=inum/100000;
        			
    				inum=inum-(100000*temp);
        			
        			rs=rs+two_digits[temp];
    				
    			}else{
    		
    				rs=rs+tens_multiple[temp];
    			
    			}
    		}
    		
    		
    		if(i==6){
    			temp=inum/100000;
    			inum=inum-(100000*temp);
    			
    			rs=rs+single_digits[temp];
    			rs=rs+tens_power[i];
    			
    		}
    		
    		if(i==5){
    			temp=inum/10000;
    			inum=inum-(10000*temp);
    			
    			if(temp==1){
    				
    				temp=inum/1000;
        			
    				inum=inum-(1000*temp);
        			
        			rs=rs+two_digits[temp];
    				
    			}else{
    		
    				rs=rs+tens_multiple[temp];
    			
    			}
    		}
    		
    		if(i==4){
    			temp=inum/1000;
    			inum=inum-(1000*temp);
    			
    			rs=rs+single_digits[temp];
    			rs=rs+tens_power[i];
    			
    			//System.out.print(temp+"\n");
    		}
    		
    		if(i==3){
    			temp=inum/100;
    			inum=inum-(100*temp);
    			
    			rs=rs+single_digits[temp];
    			if(temp != 0)
    			rs=rs+tens_power[i];
    			
    			//System.out.print(temp+"\n");
    		}
    		
    		if(i==2){
    			temp=inum/10;
    			inum=inum-(10*temp);
    				
    			if(temp==1){
    				
    				temp=inum/1;
        			
    				inum=inum-(1*temp);
        			
        			rs=rs+two_digits[temp];
    				
    			}else{
    		
					rs=rs+tens_multiple[temp];
					
					//System.out.print(temp+"\n");
    			}
    		}
    		
    		if(i==1){
    			temp=inum/1;
    			inum=inum-(1*temp);
    			
    			
	    			rs=rs+single_digits[temp];
	    			//System.out.print(temp+"\n");
    		
    			}
    		
    	}	
    	
    	
    	return rs+" only/-";

	}
}
