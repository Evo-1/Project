import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Random;
import java.util.Scanner;
import java.util.Stack;
public class Main {
      private String valid_chars;    
      private String left_braces; 
      private String right_braces; 
      private Stack<Character> brace;
      private String stCalc, x;  
       public Main()
       {
           valid_chars = "0123456789+-/*(){}[]";
           left_braces = "{[("; 
           right_braces = "}])";
           stCalc = " ";
           x = " ";
           brace = new Stack<Character>();

        }
        public int calc(String st)
        {
            st = st.replaceAll("\\s","");
            if(st.charAt(0)== '-')
            {
                st = ("0").concat(x);
            }
            x = st.replaceAll("-","+-");
            char[] carr = x.toCharArray();          
            int len = carr.length, i = 0;
            String symbol = "";
            int indicate = -5;
            while(i < len)
            {
                int m = valid_chars.indexOf(carr[i]);
                int b = left_braces.indexOf(carr[i]);
                if(m == -1)
                {
                    System.out.println("Error: "+i+"-th element is invalid");
                    return 0;
                }
                if(b != -1)
                {
                    brace.push(carr[i]);
                }
                i++;
            }
            int left = 0, right=carr.length;
          char tps =' ', right_tps= ' ';
          int match_index = -5;
            String [] opString = {"/", "*", "+"};       
             while (brace.empty()==false) {
                   carr = x.toCharArray();
                   left= findLeft(carr);
                   tps = brace.peek();
                   match_index = left_braces.indexOf(tps);
                    right_tps = right_braces.charAt(match_index);                     
                    right = x.indexOf(right_tps);                        
                    stCalc = x.substring(left+1,right);

                   for(int j =0; j < opString.length; j++)
                   {             
                      symbol = opString[j];                           
                      indicate = stCalc.indexOf(symbol);
                      stCalc = rep(symbol, x, indicate);                                                  
                   }
               x = x.substring(0,left) + stCalc + x.substring(right+1);
               carr = x.toCharArray();
               left = findLeft(carr);           
               right_tps = right_braces.charAt(match_index);
               right = x.indexOf(right_tps);
               brace.pop();

             }
            stCalc = x;
            for(int j = 0; j< opString.length; j++)
            {
               symbol = opString[j];              
               indicate = stCalc.indexOf(symbol);
               boolean flag = true;

               while(indicate != -1){
                    indicate = munger(indicate, symbol, flag);                                          
                }             
            }
            int calculation = Integer.parseInt(stCalc);            
            return calculation;
        }

        public String rep(String symbol, String x, int indicate)
        {    
              boolean flag = false;
              indicate = stCalc.indexOf(symbol);
              while(indicate != -1){                  
                  indicate = munger(indicate, symbol, flag);                                       
              }  

              if(stCalc == "N"){
                 return " ";
                }           
              return stCalc;
        }


        public int munger(int indicate, String symbol, boolean flag)
        {                   
            int i=0;     
            String s1 = stCalc.substring(0,indicate);                    
            String [] items1 = s1.split("\\+|\\*|\\/");                     
            float [] results1 = new float[items1.length];
             for (i = 0; i < items1.length; i++) {                 
                results1[i] = Float.valueOf(items1[i]);
             }

            float c1 = results1[results1.length-1];                        
            String s2 = stCalc.substring(indicate+1);           
            String [] items2 = s2.split("\\+|\\*|\\/");                                  
            float[] results2 = new float[items2.length];
             for (i = 0; i < items2.length; i++) {
                results2[i] = Float.parseFloat(items2[i]);
             }
            float c2 = results2[0];                     
            int intc1 = (int)c1;
            int intc2 = (int)c2;            
            String sc1 = Integer.toString(intc1); 
            String sc2 = Integer.toString(intc2);           
            int len_sc1 = sc1.length();
            int len_sc2 = sc2.length();           
            stCalc = calcNoBrace(intc1, intc2, symbol);
            if(flag == true){
                String left_side = x.substring(0,indicate- len_sc1);
                String right_side = x.substring(indicate + len_sc2 +1 );                                                              
                x = left_side + stCalc + right_side;               
                stCalc = x;                          
              }                                                         
            indicate = stCalc.indexOf(symbol);              

            return indicate;
        }

        public int findLeft( char[] carr)
        {
           int left = -1;
           for( int i = 0; i< carr.length; i++)
            {            
              int b = left_braces.indexOf(carr[i]);
              if(b != -1)
                 left = i;

            }  
            return left;
        }

        public String calcNoBrace(int calc1, int calc2, String symbol)
        {
               int res = 0;             
                if(symbol == "/")
                {
                     if(calc2 == 0){
                      System.out.println("Error: division by 0 condition");
                      return " ";
                     }
                     if(calc1 ==0 && calc2 == 0)
                     {
                      System.out.println("Error: undefined condition");
                      return " ";
                     }  

                    res = calc1/calc2;
                }

                if(symbol == "*")
                {
                 res = calc1*calc2;   
                }

                if(symbol == "+")
                {
                  res =  calc1 + calc2;
                }                                      
                String resSt = Integer.toString(res);
                return resSt;
        }

        public void test() {
            @SuppressWarnings({ "resource", "unused" })
			Scanner sc = new Scanner(System.in);
            BufferedWriter bw=null;
            Random random=new Random();
            int n=sc.nextInt();
            try {
				bw = new BufferedWriter(new FileWriter("result.txt"));
				bw.write("201571030328\n");
				bw.write(String.valueOf(n));
				bw.write("\n");
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
        	
        	String question="";
            for(int i=0;i<n;i++) {
        		int len=random.nextInt(3)+3;
        		question="";
        		for(int j=0;j<len;j++) {
        			question=question+random.nextInt(101);
        			if(j<len-1) {
        				question=question+oper(random.nextInt(4));
        			}
        		}
        		int result=calc(question);
        		if(result<=0||result>500) {
        			i--;
        			continue;
        		}
        		try {
					bw.write(question);
					bw.write("=");
					bw.write(String.valueOf(result));
					bw.write("\n");
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
        		System.out.println(question+"="+result);
        	}
            try {
				bw.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
        	
        }

        public String oper(int n) {
        	if(n==0) {
        		return "+";
        	}else if(n==1) {
        		return "-";
        	}else if(n==2) {
        		return "*";
        	}else {
        		return "/";
        	}
        }
        
        public  static void main(String [] args){
            Main ob = new Main();  
            ob.test();
        }

}
