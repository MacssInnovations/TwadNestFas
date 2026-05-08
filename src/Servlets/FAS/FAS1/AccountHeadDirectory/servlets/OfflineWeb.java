package Servlets.FAS.FAS1.AccountHeadDirectory.servlets;


import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.URL;
import java.net.URLConnection;




public class OfflineWeb {
	
	 public static void main(String[] args) throws Exception {
		 try
		 {
	        
	        

	        // Install the all-trusting host verifier
	      

	        URL url = new URL("https://115.114.131.65:8443/TAMIL/services/TamilTax?wsdl");
	        URLConnection con = url.openConnection();
	        final Reader reader = new InputStreamReader(con.getInputStream());
	        
	        final BufferedReader br = new BufferedReader(reader);        
	        String line = "";
	        while ((line = br.readLine()) != null) {
	            System.out.println(line);
	        }        
	        br.close();
	     // End of main 
	 }catch (Exception e) {
		// TODO: handle exception
		 e.printStackTrace();
	}
	/*public static void main(String args[] )
	{}*/
	 }
}

