package Servlets.FAS.FAS1.Masters.servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.ResourceBundle;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;


public class PhysicalVerification_Servlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    
    public PhysicalVerification_Servlet() {
        super();
        
    }

	
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    	HttpSession session=request.getSession(false);
		try
        {
            
            
            if(session==null)
            {
                System.out.println(request.getContextPath()+"/index.jsp");
                response.sendRedirect(request.getContextPath()+"/index.jsp");
                return;
            }
            System.out.println(session);
                
        }catch(Exception e)
        {
        System.out.println("Redirect Error :"+e);
        }
        
        String update_user=(String)session.getAttribute("UserId");
        long l=System.currentTimeMillis();
        Timestamp ts=new Timestamp(l);
        Connection con=null;
        ResultSet rs=null;
        Statement statement=null;
        ResultSet result=null;
        int assetmajor=0;
        int assetminor=0;
        PreparedStatement ps=null;
        try {
            ResourceBundle rs1=ResourceBundle.getBundle("Servlets.Security.servlets.Config");
            String ConnectionString="";
            String strDriver=rs1.getString("Config.DATA_BASE_DRIVER");
            String strdsn=rs1.getString("Config.DSN");
            String strhostname=rs1.getString("Config.HOST_NAME");
            String strportno=rs1.getString("Config.PORT_NUMBER");
            String strsid=rs1.getString("Config.SID");
            String strdbusername=rs1.getString("Config.USER_NAME");
            String strdbpassword=rs1.getString("Config.PASSWORD");
            ConnectionString = strdsn.trim() + "://" + strhostname.trim() + ":" + strportno.trim() + "/" +strsid.trim() ;    // Postgres DB  Connection
            Class.forName(strDriver.trim());
            con=DriverManager.getConnection(ConnectionString,strdbusername.trim(),strdbpassword.trim());
        }
	catch(Exception e)
	{
	  System.out.println("Exception in openeing connection :"+e);
	}
	
	
	
	String CONTENT_TYPE = "text/xml; charset=windows-1252";
	response.setContentType(CONTENT_TYPE);
	PrintWriter out = response.getWriter();
	String strType = "",xml="<response>";
	try
	{
	  strType = request.getParameter("Command");
	}
	catch(Exception e)
	{
	  e.printStackTrace();
	}
	int  txtCB_Year=0, txtCB_Month=0,cmbAcc_UnitCode=0,cmbOffice_code=0;
	Date txtFrom_date=null,txtTo_date=null;
	Calendar c;
	String sql="",finYear="",cmbStatus="";//verifyEmp1="";
	
	
	try{cmbAcc_UnitCode=Integer.parseInt(request.getParameter("cmbAcc_UnitCode"));}
	catch(NumberFormatException e){System.out.println("cmbAcc_UnitCode "+e );}
	
	
	try{cmbOffice_code=Integer.parseInt(request.getParameter("cmbOffice_code"));}
	catch(NumberFormatException e){System.out.println("cmbOffice_code "+e );}     

	finYear=request.getParameter("finYear");
	try{assetmajor = Integer.parseInt(request.getParameter("assetmajor"));}
	catch(NumberFormatException e){System.out.println("assetmajor "+e );}
	try{ assetminor = Integer.parseInt(request.getParameter("assetminor"));}
	catch(NumberFormatException e){System.out.println("assetminor "+e );}    
	
	if(strType.equalsIgnoreCase("goCmd"))  
    {
    
        xml="<response><command>goCmd</command>";            
     //  sql="SELECT ASSET_CODE,TOTAL_YEAR_QTY FROM FAS_A52REGISTER where ACCOUNTING_UNIT_ID="+cmbAcc_UnitCode+" and ACCOUNTING_UNIT_OFFICE_ID="+cmbOffice_code+" and FINANCIAL_YEAR='"+finYear+"'";
    
				/*	      sql="select asset_code, accounting_unit_office_id, "+
					"(open_bal_qty+reciepts_year_qty)-issues_year_qty as tot "+
					" from  "+
					"  ( "+
					" select "+
					" asset_code, accounting_unit_office_id, "+
					" (case when open_bal_qty is null then 0 else open_bal_qty end) as open_bal_qty, "+
					" (case when reciepts_year_qty is null then 0 else reciepts_year_qty end) as reciepts_year_qty,"+
					" (case when issues_year_qty is null then 0 else issues_year_qty end) as issues_year_qty "+
					
					" from fas_a52_register "+
					" where accounting_unit_id  ="+cmbAcc_UnitCode+
					
					" and accounting_unit_office_id="+cmbOffice_code+
					" and financial_year='"+finYear+"'"+
					" and asset_major_class_code="+assetmajor+
					" and ASSET_MINOR_CLASS_CODE= "+assetminor +")";*/
        //8April2014
       sql="select asset_code, accounting_unit_office_id, remarks,"+
		"((open_bal_qty+(RECIEPTS_YEAR_DR_QTY+RECIEPTS_YEAR_CR_QTY))-(ISSUES_YEAR_CR_QTY+ISSUES_YEAR_DR_QTY)) as tot  "+
		" from  "+
		"  ( "+
		" select "+
		" asset_code,remarks, accounting_unit_office_id, "+
		" (case when open_bal_qty is null then 0 else open_bal_qty end) as open_bal_qty, "+
		" (case when RECIEPTS_YEAR_DR_QTY is null then 0 else RECIEPTS_YEAR_DR_QTY end) as RECIEPTS_YEAR_DR_QTY, "+
		"  (case when RECIEPTS_YEAR_CR_QTY is null then 0 else RECIEPTS_YEAR_CR_QTY end) as RECIEPTS_YEAR_CR_QTY, "+
		"  (case when ISSUES_YEAR_DR_QTY is null then 0 else ISSUES_YEAR_DR_QTY end) as ISSUES_YEAR_DR_QTY, "+
		"  (case when ISSUES_YEAR_CR_QTY is null then 0 else ISSUES_YEAR_CR_QTY end) as ISSUES_YEAR_CR_QTY  "+
		" from fas_a52_register_Edit "+
		" where accounting_unit_id  ="+cmbAcc_UnitCode+
		" and accounting_unit_office_id="+cmbOffice_code+
		" and financial_year='"+finYear+"'"+
		" and TRANSFER_REMARKS is null and asset_major_class_code="+assetmajor+
		" and ASSET_MINOR_CLASS_CODE= "+assetminor +")";
        
      try
       {
        int count=0;
        ps=con.prepareStatement(sql);
        System.out.println(sql);
        xml=xml+"<flag>success</flag>";
        rs=ps.executeQuery();
            while(rs.next())
            {
         	  
                xml=xml+"<leng>";
                
                xml=xml+"<acc_code>"+rs.getInt("asset_code")+"</acc_code>";
                xml=xml+"<year_qty>"+rs.getInt("tot")+"</year_qty>";
                xml=xml+"<remarks>"+rs.getString("remarks")+"</remarks>";
                xml=xml+"<accounting_unit_office_id>"+rs.getInt("accounting_unit_office_id")+"</accounting_unit_office_id>";
                
                xml=xml+"</leng>";
                count++;
            }
           if(count==0) 
           {
              System.out.println("inside count==0");
              xml="<response><command>goCmd</command><flag>failure</flag>";
           }
       }
       catch(SQLException sqle)
        {
    	   sqle.printStackTrace();
    	   System.out.println("error while fetching data " + sqle);
            xml="<response><command>goCmd</command><flag>failure</flag>";
        }
        
    }
	else if (strType.equalsIgnoreCase("checkStatus")) {
    //	int c1=0,c2=0,c3=0;
        xml = "<response><command>checkStatus</command>";
        System.out.println("checkStatus in physcial ");
        try {
        	
        	/*String[] divyear=finYear.split("-");
        	String newyear=(Integer.parseInt(divyear[0])-1)+"-"+(Integer.parseInt(divyear[1])-1);
        	System.out.println("new year  "+newyear);*/
        
         	String searchquery="select 'X' from FAS_A52_VERIFY_STATUS where ACCOUNTING_FOR_OFFICE_ID=? and FINANCIAL_YEAR='"+finYear+"' and A52_STATUS='Y' ";//and finanical_year='"+cmbFinancialYear+"' 
         	
           PreparedStatement ps1 =  con.prepareStatement(searchquery);
           ps1.setInt(1, cmbOffice_code);
           ResultSet rss2=ps1.executeQuery();
          
             System.out.println(" searchquery  "+searchquery);
            if(rss2.next()){
				xml = xml + "<flag>freeze</flag>";
				System.out.println("freeze   ");
				
            }else{
            	xml = xml + "<flag>notfreeze</flag>";
            } 

        } catch (SQLException e) {
            e.printStackTrace();
            xml = xml + "<flag>failure</flag>";
        }
     
       
    }
	else if(strType.equalsIgnoreCase("Add"))  
	{
		 xml="<response><command>adding</command>";
		String[] txtDtFrm1=request.getParameter("txtDtFrm").split("/");
		System.out.println("txtDtFrm1 [1] "+txtDtFrm1[1]);
		int cashbookmonth=Integer.parseInt(txtDtFrm1[1]);
		System.out.println("cashbookmonth"+cashbookmonth);
		
		int cashbookyear=Integer.parseInt(txtDtFrm1[2]);
		
	
		
			String[] assetCode=request.getParameterValues("assetCode");
	     	String[] availQty=request.getParameterValues("availQty");
	     	String[] excessQty=request.getParameterValues("excessQty");
	     	String[] shQty=request.getParameterValues("shQty");
	     	String[] underUsable=request.getParameterValues("underUsable");
	     	String[] qtyNonuse=request.getParameterValues("qtyNonuse");
	     	
	     	String[] qtyusable=request.getParameterValues("qtyusable");
	     	String[] qtytobe=request.getParameterValues("qtytobe");
	     	String[] Reason=request.getParameterValues("Reason");
	     	String[] offcode=request.getParameterValues("offcode");
	     
	    	int ss=assetCode.length;
	        
	    
	    	int cno=0;
	       	for(int ii=0;ii<ss;ii++){
	       		
	
		try{
			
             int assetno=Integer.parseInt(assetCode[ii]);
      		int availQty1=Integer.parseInt(availQty[ii]);
      		  int excessQty1=Integer.parseInt(excessQty[ii]); 
                int shQty1=Integer.parseInt(shQty[ii]);
                int underUsable1=Integer.parseInt(underUsable[ii]);
                int qtyNonuse1=Integer.parseInt(qtyNonuse[ii]);
                int qtyusable1=Integer.parseInt(qtyusable[ii]);
                int qtytobe1=Integer.parseInt(qtytobe[ii]);
            	   String Reason1=Reason[ii];
              int offcode1=Integer.parseInt(offcode[ii]);
			ps=con.prepareStatement("insert into A52_VERIFACATION_DETAILS(ACCOUNTING_UNIT_ID,ACCOUNTING_UNIT_OFFICE_ID,FINANCIAL_YEAR,ASSET_CODE,QTY_AVALABLE,EXCESS_QTY,SHORTAGE_QTY,QTY_USABLE,QTY_NONUSABLE,QTY_MADE_USABLE,QTY_TO_BE_CONDEMNED,REASON_FOR_CONDEMN,VERIFIEDON,VERIFIEDBY,ASSET_MAJOR_CLASS_CODE,ASSET_MINOR_CLASS_CODE) values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)");
	
			 ps.setInt(1,cmbAcc_UnitCode);
             ps.setInt(2,cmbOffice_code);
             ps.setString(3,finYear);
         
            
             ps.setInt(4,assetno);
             ps.setInt(5,availQty1);
             ps.setInt(6,excessQty1);
             ps.setInt(7,shQty1);
             ps.setInt(8,underUsable1);
             ps.setInt(9,qtyNonuse1);
             ps.setInt(10,qtyusable1);
             ps.setInt(11,qtytobe1);
             ps.setString(12,Reason1);
             ps.setTimestamp(13,ts);
             ps.setString(14,update_user);
             ps.setInt(15,assetmajor);
             ps.setInt(16,assetminor);
            
           int eg= ps.executeUpdate();
           if(eg==0)
           {
        	   System.out.println("redirect");
        	   cno=cno+1;
        	  // xml=xml+"<flag>failure</flag>";		 
           }
           else
           {
        	//   cno=cno+0;
        	   System.out.println("verified");
        	   
        	   try{
        		PreparedStatement ps_chk=con.prepareStatement("update fas_a52_register_Edit set "
        				+ "TRANSFER_REMARKS =? WHERE accounting_unit_id     =?   "
        				+ "AND accounting_unit_office_id=?   AND financial_year         "
        				+ "  =?  AND asset_major_class_code   =?  AND ASSET_MINOR_CLASS_CODE   = ? "
        				+ "and ASSET_CODE=?");   
        		ps_chk.setString(1, "PV");
        		ps_chk.setInt(2, cmbAcc_UnitCode);
        		ps_chk.setInt(3, cmbOffice_code);
        		ps_chk.setString(4, finYear);
        		ps_chk.setInt(5, assetmajor);
        		ps_chk.setInt(6, assetminor);
        		ps_chk.setInt(7, assetno);
							int cc=ps_chk.executeUpdate();
							if(cc==0){
								 cno=cno+1;
					        	  // xml=xml+"<flag>failure</flag>";		 
							}else{
								   cno=cno+0;
							}
        	   
        	   }catch(Exception e){
        		   cno=cno+1;
	        	 //  xml=xml+"<flag>failure</flag>";		
        		   System.out.println("Catch ..... "+e);
        	   }
        	 //  xml=xml+"<flag>success</flag>";
           }
		
		}
		catch(Exception e)
		{
			System.out.println("exception in insertion ---> "+e.getMessage());
			//xml=xml+"<flag>failure</flag>";	
		}
	       	}
	       	if(cno==0){
	       		xml=xml+"<flag>success</flag>";
	       		try {
					con.commit();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
	       	}else{
	       		xml=xml+"<flag>failure</flag>";	
	       		try {
					con.rollback();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
	       	}
	}
	else if(strType.equals("loadMajor"))
    { 
    	System.out.println("\n*************\nloadMajor\n**************\n");
        xml="<response><command>loadMajor</command>";
        try 
        {
           String sqlQuery="select ASSET_MAJOR_CLASS_CODE,ASSET_MAJOR_CLASS_DESC from FAS_MST_ASSETS_CLASS order by ASSET_MAJOR_CLASS_CODE"; 
       // result = statement.executeQuery();
        // System.out.println(sqlQuery);
		   	 ps = con.prepareStatement(sqlQuery);
		     rs=ps.executeQuery();
		      //  int up=ps.executeUpdate();
            //System.out.println("aft res");
         try
         {
        	 xml=xml+"<flag>success</flag>";
        	 String valExists = "No";
             while(rs.next())
             { 
            	 valExists = "Yes";
            	 xml += "<ASSET_MAJOR_CLASS_CODE>" + rs.getInt("ASSET_MAJOR_CLASS_CODE") + "</ASSET_MAJOR_CLASS_CODE>";
            	 xml += "<ASSET_MAJOR_CLASS_DESC><![CDATA[" + rs.getString("ASSET_MAJOR_CLASS_DESC") + "]]></ASSET_MAJOR_CLASS_DESC>";
             }

             xml += "<exists>"+valExists+"</exists>";
         }catch(Exception e)
         {
        	 System.out.println("Exception in getting values from ASSET_MAJOR_CLASS_CODE: " + e);
         }
         
       //  result.close();
         //response.setHeader("cache-control","no-cache");
         
        }
        catch(Exception e1)
        {
        	System.out.println("Exception is in Get"+e1);
        	xml=xml+"<flag>failure</flag>";
        }
      //  xml=xml+"</response>";
    }  
    else if(strType.equals("loadMinor"))
    { 
    	System.out.println("\n*************\nloadMinor\n**************\n");
        xml="<response><command>loadMinor</command>";
      // assetmajor = Integer.parseInt(request.getParameter("assetmajor"));
        try 
        {
            
         //result = statement.executeQuery("select ASSET_MINOR_CLASS_CODE,ASSET_MINOR_CLASS_DESC from FAS_ASSET_MINOR_CLASSIFICATION where ASSET_MAJOR_CLASS_CODE="+assetmajor+" order by ASSET_MINOR_CLASS_CODE");
         String sqlQuery="select ASSET_MINOR_CLASS_CODE,ASSET_MINOR_CLASS_DESC from FAS_ASSET_MINOR_CLASSIFICATION where ASSET_MAJOR_CLASS_CODE="+assetmajor+" order by ASSET_MINOR_CLASS_CODE"; 
         // result = statement.executeQuery();
         //  System.out.println(sqlQuery);
  		   	 ps = con.prepareStatement(sqlQuery);
  		     rs=ps.executeQuery();
            //System.out.println("aft res");
         try
         {
        	 xml=xml+"<flag>success</flag>";
        	 String valExists = "No";
             while(rs.next())
             { 
            	 valExists = "Yes";
            	 xml += "<ASSET_MINOR_CLASS_CODE>" + rs.getInt("ASSET_MINOR_CLASS_CODE") + "</ASSET_MINOR_CLASS_CODE>";
            	 xml += "<ASSET_MINOR_CLASS_DESC><![CDATA[" + rs.getString("ASSET_MINOR_CLASS_DESC") + "]]></ASSET_MINOR_CLASS_DESC>";
             }

             xml += "<exists>"+valExists+"</exists>";
         }catch(Exception e)
         {
        	 System.out.println("Exception in getting values from ASSET_MINOR_CLASS_DESC: " + e);
         }
         
        // result.close();
         //response.setHeader("cache-control","no-cache");
         
        }
        catch(Exception e1)
        {
        	System.out.println("Exception is in Get"+e1);
        	xml=xml+"<flag>failure</flag>";
        }
       // xml=xml+"</response>";
    }  
	
	xml=xml+"</response>";   
    out.println(xml); 
    System.out.println("xml last:"+xml);
	
	
	}
		public void doPost(HttpServletRequest request,HttpServletResponse response) throws ServletException, IOException 
		{
		    System.out.println("physical verification");
			String strCommand="";
	         Connection con=null;        
	         PreparedStatement ps=null,ps1=null,ps2=null;
	         String xml="";
	         Statement st=null;
	         ResultSet rs=null;
	 //-----------------------------------------------------------------------------------------------        
	 
	       //  String verifyEmp1=null;
	         
	        HttpSession session=request.getSession(false);
	        try
	        {
	            
	            if(session==null)
	            {
	                System.out.println(request.getContextPath()+"/index.jsp");
	                response.sendRedirect(request.getContextPath()+"/index.jsp");
	                return;
	            }
	            System.out.println(session);
	                
	        }catch(Exception e)
	        {
	        System.out.println("Redirect Error :"+e);
	        } 
	        
	        try 
	        {
	              ResourceBundle rs1=ResourceBundle.getBundle("Servlets.Security.servlets.Config");
	              String ConnectionString="";

	              String strDriver=rs1.getString("Config.DATA_BASE_DRIVER");
	              String strdsn=rs1.getString("Config.DSN");
	              String strhostname=rs1.getString("Config.HOST_NAME");
	              String strportno=rs1.getString("Config.PORT_NUMBER");
	              String strsid=rs1.getString("Config.SID");
	              String strdbusername=rs1.getString("Config.USER_NAME");
	              String strdbpassword=rs1.getString("Config.PASSWORD");
	              ConnectionString = strdsn.trim() + "://" + strhostname.trim() + ":" + strportno.trim() + "/" +strsid.trim() ;    // Postgres DB  Connection
	              Class.forName(strDriver.trim());
	              con=DriverManager.getConnection(ConnectionString,strdbusername.trim(),strdbpassword.trim());
	              st=con.createStatement();
	         }
	         catch(Exception e)
	         {
	             System.out.println("Exception in opening connection :"+e);
	             //sendMessage(response,"probably Failed to Establish connection to the database server.. due to "+e,"ok");

	         }
	                 
	 //-----------------------------------------------------------------------------------------------        
	        int cmbAcc_UnitCode=0,cmbOffice_code=0;
	        String finYear=null;
	        try
	        {        
	             strCommand=request.getParameter("Command");
	             System.out.println("assign..here command..."+strCommand);
	           
	        }
	        
	        catch(Exception e) 
	        {
	             System.out.println("Exception in assigning..."+e);
	        }
	        
	        String update_user=(String)session.getAttribute("UserId");
            long l=System.currentTimeMillis();
            Timestamp ts=new Timestamp(l);
	        
	        try{cmbAcc_UnitCode=Integer.parseInt(request.getParameter("cmbAcc_UnitCode"));}
	    	catch(NumberFormatException e){System.out.println("exception"+e );}
	    	
	    	
	    	try{cmbOffice_code=Integer.parseInt(request.getParameter("cmbOffice_code"));}
	    	catch(NumberFormatException e){System.out.println("exception"+e );}     

	    	finYear=request.getParameter("finYear");
	        
	        if(strCommand.equalsIgnoreCase("add")) 
	        {
	        		String[] txtDtFrm1=request.getParameter("txtDtFrm").split("/");
	        		System.out.println(txtDtFrm1[1]);
	        		int cashbookmonth=Integer.parseInt(txtDtFrm1[1]);
	        		System.out.println("cashbookmonth"+cashbookmonth);
	        		
	        		int cashbookyear=Integer.parseInt(txtDtFrm1[2]);
	        		
	        		/*verifyEmp1=request.getParameter("verifyEmp");
	        		System.out.println("emp>>>"+verifyEmp1);*/
	        	
	        		  String[] grid1=request.getParameter("grid").split(",");
	        	     int len=grid1.length;
	        			System.out.println("arraylength"+len);
	        			System.out.println("1>>>"+grid1[1]+"2>>"+grid1[2]+"3>>"+grid1[3]);
	        			
	        		try{
	        			ps=con.prepareStatement("insert into  A52_VERIFACATION_DETAILS (ACCOUNTING_UNIT_ID,ACCOUNTING_UNIT_OFFICE_ID,FINANCIAL_YEAR,ASSET_CODE,QTY_AVALABLE,EXCESS_QTY,SHORTAGE_QTY,ISUNDERWORKING,QTY_USABLE,ISNONUSABLE,QTY_NONUSABLE,ISMADEUSABLE,QTY_MADE_USABLE,TO_BE_CONDEMNED,QTY_TO_BE_CONDEMNED,REASON_FOR_CONDEMN,VERIFIEDON,VERIFIEDBY) values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)");
	        		System.out.println("ps");
	        			 ps.setInt(1,cmbAcc_UnitCode);
	                     ps.setInt(2,cmbOffice_code);
	                     ps.setString(3,finYear);
	                     System.out.println("finYear"+finYear);
	                     ps.setString(4,grid1[0]);
	                     System.out.println("first>>"+grid1[0]);
	                     ps.setString(5,grid1[1]);
	                     ps.setString(6,grid1[2]);
	                     ps.setString(7,grid1[3]);
	                     ps.setString(8,grid1[4]);
	                     ps.setString(9,grid1[5]);
	                     ps.setString(10,grid1[6]);
	                     ps.setString(11,grid1[7]);
	                     ps.setString(12,grid1[8]);
	                     ps.setString(13,grid1[9]);
	                     ps.setString(14,grid1[10]);
	                     ps.setString(15,grid1[11]);
	                     ps.setString(16,grid1[12]);
	                     ps.setTimestamp(17,ts);
	                     ps.setString(18,update_user);
	                   int eg= ps.executeUpdate();
	                   if(eg==0)
	                   {
	                	   System.out.println("redirect");
	                	   sendMessage(response,"The Insertion of records into the table Failed ","ok");	 
	                   }
	                   else
	                   {
	                	   System.out.println("verified");
	                	   sendMessage(response,"The Records are inserted into the table Successfully ","ok");
	                   }
	        		
	        		}
        			catch(Exception e)
        			{
        				System.out.println("exception in insertion"+e.getMessage());
        				  sendMessage(response,"The Insertion of records into the table Failed ","ok");
        			}
	        			
	        }
		    
		}
		
		 private void sendMessage(HttpServletResponse response,String msg,String bType)
		    {
			 try
		        {
				 System.out.println("sendMessage");
		             String url="org/Library/jsps/MessengerOkBack.jsp?message=" + msg + "&button=" + bType;
		             System.out.println("url>>>>"+url);
		             response.sendRedirect(url);
		        }
		        catch(IOException e)
		        {
		        	System.out.println("IOException>>>>>"+e.getMessage());
		        }
		    }
}
