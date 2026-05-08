package Servlets.FAS.FAS1.TDA.servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.ResourceBundle;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Servlet implementation class TDA_AcceptingList
 */
public class TDA_AcceptingList extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    
    public TDA_AcceptingList() {
        super();
        
    }

	
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try
        {
	             HttpSession session=request.getSession(false);
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
        Connection con=null;
        ResultSet rs=null,rs2=null;
        
        PreparedStatement ps=null;
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
                ConnectionString = strdsn.trim() + "://" + strhostname.trim() + ":" + strportno.trim() + "/" +strsid.trim() ;    // Postgres DB  Connection                Class.forName(strDriver.trim());
                con=DriverManager.getConnection(ConnectionString,strdbusername.trim(),strdbpassword.trim());
        }
        catch(Exception e)
        {
       	 	 System.out.println("Exception in openeing connection :"+e);

        }
        
        System.out.println("servlet called");
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
        String sql="",txtCreat_By_Module="",cmbStatus="",cmbJournal_type="",jType="",jType1="";
	    String dur1="";
        
        try{cmbAcc_UnitCode=Integer.parseInt(request.getParameter("cmbAcc_UnitCode"));}
        catch(NumberFormatException e){System.out.println("exception"+e );}
        
        
        try{cmbOffice_code=Integer.parseInt(request.getParameter("cmbOffice_code"));}
        catch(NumberFormatException e){System.out.println("exception"+e );}
        
        txtCB_Year=Integer.parseInt(request.getParameter("txtCB_Year"));
        txtCB_Month=Integer.parseInt(request.getParameter("txtCB_Month"));        
        cmbStatus=request.getParameter("cmbStatus");
        
        
        txtCreat_By_Module=request.getParameter("txtCreat_By_Module");
        
        cmbJournal_type=request.getParameter("cmbJournal_type");
         System.out.println("cmbJournal_type"+cmbJournal_type);
	    System.out.println("strType"+strType);
        if(cmbJournal_type.equals("63A"))
        {
            jType="TDAO";
            jType1="TDACB";
        }
        else if(cmbJournal_type.equals("63B")) {
            jType="TDAO";
            jType1="TDACB";
        }
        else if(cmbJournal_type.equals("66A")) {
       
            jType="TCAO"; 
            jType1="TCACB";
        }
        else if(cmbJournal_type.equals("66B")) {
            jType="TCAO";
            jType1="TCACB";
        }
        else if(cmbJournal_type.equals("63C")) {
        	if(cmbStatus.equals("L"))
        	{
        	jType="TDAO";
            jType1="TDACB";
        	}
        	else
        	{
        		jType="TDAA";
                jType1="TDACB";	
        	}
        }
        else if(cmbJournal_type.equals("66C")) {
            jType="TCAO";
            jType1="TCACB";
        }
        String hid_value="",sub_str="";
        if(strType.equalsIgnoreCase("searchByMonth"))  
        {
        	 hid_value=request.getParameter("hid");
        	 if(hid_value.equalsIgnoreCase("sup"))
        		 sub_str=" tda_tca.SUPPLEMENT_NO=1 and ";
        	 else  if(hid_value.equalsIgnoreCase("Nonsup"))
        		 sub_str="";
        	//System.out.println("monthhhhhhhhhhhhhhhhh");
	            xml="<response><command>searchByMonth</command>";                        
	           
				            try
				            {
				            	 sql="SELECT tda_tca.VOUCHER_NO,mst_acct.ACCOUNTING_UNIT_NAME,TO_CHAR(tda_tca.VOUCHER_DATE,'DD/MM/YYYY') AS vou_date ,tda_tca.REASON_FOR_NON_ACCEPT,trim(TO_CHAR(tda_tca.total_amount,'99999999999999.99')) AS tot_amount,tda_tca.particulars,tda_tca.ACCOUNTING_UNIT_ID," +
				            	 		" tda_tca.ACCOUNTING_FOR_OFFICE_ID FROM fas_tda_tca_raised_mst tda_tca,FAS_MST_ACCT_UNITS mst_acct WHERE tda_tca.TRF_ACCOUNTING_UNIT_ID =? AND tda_tca.cashbook_year =? AND tda_tca.cashbook_month  =? " +
                                                 " AND (tda_tca.ACCEPTING_SLNO  =0 OR tda_tca.ACCEPTING_SLNO IS NULL) AND tda_tca.ACCEPTING_DATE is null AND tda_tca.status  =? AND " +sub_str+
                                                 " (tda_tca.tda_or_tca ='"+jType+"' or tda_tca.tda_or_tca='"+jType1+"') and tda_tca.ACCOUNTING_UNIT_ID= mst_acct.ACCOUNTING_UNIT_ID ORDER BY tda_tca.voucher_no";
						           System.out.println("SQL::::"+sql);
						            int count=0;
						            ps=con.prepareStatement(sql);
						            ps.setInt(1,cmbAcc_UnitCode);
						            ps.setInt(2,txtCB_Year);
						            ps.setInt(3,txtCB_Month);
						            ps.setString(4,cmbStatus);System.out.println("cmbStatus"+cmbStatus);
                                                        
						            rs=ps.executeQuery();
				                //System.out.println("rs"+rs);
					                while(rs.next())
					                {System.out.println("while");
					                xml=xml+"<leng>";
					                	 xml=xml+"<flag>success</flag><Ucode>"+rs.getInt("ACCOUNTING_UNIT_ID")+"</Ucode><offcode>"+rs.getInt("ACCOUNTING_FOR_OFFICE_ID")+"</offcode><txtCB_Year>"+txtCB_Year+"</txtCB_Year><txtCB_Month>"+txtCB_Month+"</txtCB_Month>";
					                	
						                    //xml=xml+"<leng>";
						                    xml=xml+"<vou_no>"+rs.getInt("VOUCHER_NO")+"</vou_no>";
						                    xml=xml+"<vou_date>"+rs.getString("vou_date")+"</vou_date>";
						                    xml=xml+"<org_unit>"+rs.getString("ACCOUNTING_UNIT_NAME")+"</org_unit>";
						                    xml=xml+"<org_unitid>"+rs.getString("ACCOUNTING_UNIT_ID")+"</org_unitid>";
						                    xml=xml+"<offid>"+rs.getString("ACCOUNTING_FOR_OFFICE_ID")+"</offid>";
						                    xml=xml+"<Reason>"+rs.getString("REASON_FOR_NON_ACCEPT")+"</Reason>";
                                                                  //  System.out.println("particulars>>>>>"+rs.getString("particulars"));
                                                                    if(rs.getString("particulars")!="" || rs.getString("particulars")!="null"){
						                    xml=xml+"<Remak><![CDATA["+rs.getString("particulars")+"]]></Remak>";
                                                                    }
                                                                    else{
                                                                        xml=xml+"<Remak>"+"--"+"</Remak>";
                                                                    }
						                    xml=xml+"<Tot_Amt>"+rs.getString("tot_amount") +"</Tot_Amt>";
						                    xml=xml+"</leng>";
						                    
						                    count++;
					                }
					                if(count==0) 
					                {
						                    System.out.println("inside count==0");
						                    xml="<response><command>searchByMonth</command><flag>failure</flag>";
					                }
				            }
				            catch(SQLException sqle)
				            {
					        	    sqle.printStackTrace();
					        	    System.out.println("error while fetching data " + sqle);
					                xml="<response><command>searchByMonth</command><flag>failure</flag>";
				            }
               
        }
      
        else if(strType.equalsIgnoreCase("searchByDate"))  
        {
	        	String[] sd=request.getParameter("txtFrom_date").split("/");
	            c=new GregorianCalendar(Integer.parseInt(sd[2]),Integer.parseInt(sd[1])-1,Integer.parseInt(sd[0]));
	            java.util.Date d=c.getTime();
	            txtFrom_date=new Date(d.getTime());
	            
	            sd=request.getParameter("txtTo_date").split("/");
	            c=new GregorianCalendar(Integer.parseInt(sd[2]),Integer.parseInt(sd[1])-1,Integer.parseInt(sd[0]));
	            d=c.getTime();
	            txtTo_date=new Date(d.getTime());
	         
	            xml="<response><command>searchByDate</command>";                        
	            
	            sql="SELECT tda_tca.VOUCHER_NO,mst_acct.ACCOUNTING_UNIT_NAME,TO_CHAR(tda_tca.VOUCHER_DATE,'DD/MM/YYYY') AS vou_date ,tda_tca.REASON_FOR_NON_ACCEPT,trim(TO_CHAR(tda_tca.total_amount,'99999999999999.99')) AS tot_amount, tda_tca.particulars,tda_tca.ACCOUNTING_UNIT_ID,tda_tca.ACCOUNTING_FOR_OFFICE_ID FROM fas_tda_tca_raised_mst tda_tca,FAS_MST_ACCT_UNITS mst_acct WHERE tda_tca.TRF_ACCOUNTING_UNIT_ID =? AND (VOUCHER_DATE between ? and ?) AND tda_tca.ACCEPTING_DATE is null AND (tda_tca.ACCEPTING_SLNO  =0 OR tda_tca.ACCEPTING_SLNO IS NULL) AND tda_tca.status  =? " +
                    "AND (tda_tca.tda_or_tca ='"+jType+"' or tda_tca.tda_or_tca='"+jType1+"') and tda_tca.ACCOUNTING_UNIT_ID= mst_acct.ACCOUNTING_UNIT_ID ORDER BY tda_tca.voucher_no";
		         
				            System.out.println("SQL::::"+sql);
				            try
				            {
						            int count=0;
						            ps=con.prepareStatement(sql);
						            ps.setInt(1,cmbAcc_UnitCode);
						            ps.setDate(2,txtFrom_date);
						            ps.setDate(3,txtTo_date);
						            ps.setString(4,cmbStatus);
                                                    
						            rs2=ps.executeQuery();
						           while(rs2.next())
					                {
						        	   xml=xml+"<leng>";
					                	 xml=xml+"<flag>success</flag><Ucode>"+rs2.getInt("ACCOUNTING_UNIT_ID")+"</Ucode><offcode>"+rs2.getInt("ACCOUNTING_FOR_OFFICE_ID")+"</offcode><txtCB_Year>"+txtCB_Year+"</txtCB_Year><txtCB_Month>"+txtCB_Month+"</txtCB_Month>";
					                
						                //   System.out.println("xml::::"+xml);
						                    xml=xml+"<vou_no>"+rs2.getInt("VOUCHER_NO")+"</vou_no>";
						                    xml=xml+"<vou_date>"+rs2.getString("vou_date")+"</vou_date>";
						                    xml=xml+"<org_unit>"+rs2.getString("ACCOUNTING_UNIT_NAME")+"</org_unit>";
						                    xml=xml+"<Reason>"+rs2.getString("REASON_FOR_NON_ACCEPT")+"</Reason>";
						                //    xml=xml+"<Remak>"+rs2.getString("particulars")+"</Remak>";
						              //   System.out.println("particulars>>>>>"+rs2.getString("particulars"));
						                 if(rs2.getString("particulars")!="" || rs2.getString("particulars")!="null"){
						                // xml=xml+"<Remak>"+rs2.getString("particulars")+"</Remak>";
						                	 xml=xml+"<Remak><![CDATA["+rs2.getString("particulars")+"]]></Remak>";
						                 }
						                 else{
						                     xml=xml+"<Remak>"+"--"+"</Remak>";
						                 }
						                    xml=xml+"<Tot_Amt>"+rs2.getString("tot_amount") +"</Tot_Amt>";
						                    xml=xml+"</leng>";
						                   
						                    count++;
					                }
					                if(count==0) 
					                {
						                    System.out.println("inside count==0");
						                    xml="<response><command>searchByDate</command><flag>failure</flag>";
					                }
				            }
				            catch(SQLException sqle)
				            {
					        	    sqle.printStackTrace();
					        	    System.out.println("error while fetching data " + sqle);
					                xml="<response><command>searchByDate</command><flag>failure</flag>";
				            }
              
	           
            
        }
        else if(strType.equalsIgnoreCase("MonthAccepted"))  
        {
        	 hid_value=request.getParameter("hid");
        	 if(hid_value.equalsIgnoreCase("sup")){
        		 sub_str=" tda_tca.SUPPLEMENT_NO=1 and "; }
        	 else  if(hid_value.equalsIgnoreCase("Nonsup")){
        		 sub_str="  (TDA_TCA.SUPPLEMENT_NO=0 or  TDA_TCA.SUPPLEMENT_NO is null) and";
        	 }
        	
        if(cmbJournal_type.equals("63B") || cmbJournal_type.equals("66B")){
             dur1=" AND tda_tca.cashbook_year =? AND tda_tca.cashbook_month  =? ";
        }
        else if(cmbJournal_type.equals("63C") || cmbJournal_type.equals("66C")){
        	System.out.println("cmbStatus===>"+cmbStatus);
        	if(cmbStatus.equals("L")){        	 	
        			dur1=" and EXTRACT(YEAR FROM tda_tca1.ACCEPTING_DATE)=? and extract(month from tda_tca1.ACCEPTING_DATE)=? ";
        	
        	 System.out.println("cmbStatus is Live");
        	}
        	else
        	{
        		dur1=" AND tda_tca.cashbook_year =? AND tda_tca.cashbook_month  =? ";
        		System.out.println("cmbStatus is Cancel");
        	}
        }
        System.out.println("dur1****::::"+dur1);
	            xml="<response><command>MonthAccepted</command>";                        
	          
	           System.out.println("cmbAcc_UnitCode===>"+cmbAcc_UnitCode);
	           System.out.println("txtCB_Year===>"+txtCB_Year);
	           System.out.println("txtCB_Month===>"+txtCB_Month);
	           System.out.println("cmbStatus===>"+cmbStatus);
	           System.out.println("dur1===>"+dur1);
	           System.out.println("jType===>"+jType);
	           System.out.println("sub_str===>"+sub_str);
	           
	           
	           if(cmbStatus.equals("L")){
				            try
				            {
//				            	 sql="SELECT tda_tca.VOUCHER_NO, "+
//                                         "  mst_acct.ACCOUNTING_UNIT_NAME,"+
//			            	 "  TO_CHAR(tda_tca.VOUCHER_DATE,'DD/MM/YYYY') AS vou_date ,"+
//			            	 "   trim(TO_CHAR(tda_tca.total_amount,'99999999999999.99')) AS tot_amount,"+
//			            	 "   tda_tca.particulars,"+
//			            	 "   tda_tca.ACCEPTING_SLNO,"+
//			            	 "   TO_CHAR(tda_tca.ACCEPTING_DATE,'DD/MM/YYYY') AS accept_date ,"+
//			            	 "   tda_tca.ACCOUNTING_UNIT_ID,"+
//			            	 " 	  tda_tca.ACCOUNTING_FOR_OFFICE_ID"+
//			            	 " 	FROM fas_tda_tca_raised_mst tda_tca,"+
//			            	 " 	  FAS_MST_ACCT_UNITS mst_acct"+
//			            	 " 	WHERE tda_tca.TRF_ACCOUNTING_UNIT_ID =?"+dur1+
//			            	 " 	AND (tda_tca.ACCEPTING_SLNO IS NOT NULL) and tda_tca.accepting_date is not null"+
//			            	 " 	AND tda_tca.status            =? and "+
//			            	 sub_str+
//			            	 " 	 (tda_tca.tda_or_tca        ='"+jType+"' or tda_tca.tda_or_tca='"+jType1+"') AND tda_tca.ACCOUNTING_UNIT_ID= mst_acct.ACCOUNTING_UNIT_ID"+
//			            	 " 	ORDER BY tda_tca.voucher_no"; 
				            	
				            	//New code Changed on 25_Apr_18 for accepting_slno is not displayed issue.
				            	
				            	sql="SELECT tda_tca1.VOUCHER_NO,\n" + 
				            			"  MST_ACCT.ACCOUNTING_UNIT_NAME,\n" + 
				            			"  TO_CHAR(tda_tca1.VOUCHER_DATE,'DD/MM/YYYY')              AS vou_date ,\n" + 
				            			"  trim(TO_CHAR(tda_tca.total_amount,'99999999999999.99')) AS tot_amount,\n" + 
				            			"  TDA_TCA.PARTICULARS,\n" + 
				            			" TDA_TCA.supplement_no,\n"+
				            			"  TDA_TCA1.ACCEPTING_SLNO,\n" + 
				            			"  TO_CHAR(tda_tca1.ACCEPTING_DATE,'DD/MM/YYYY') AS accept_date ,\n" + 
				            			"  tda_tca.ACCOUNTING_UNIT_ID,\n" + 
				            			"  tda_tca.ACCOUNTING_FOR_OFFICE_ID\n" + 
				            			"FROM FAS_TDA_TCA_RAISED_MST TDA_TCA,\n" + 
				            			"FAS_TDA_TCA_RAISED_MST TDA_TCA1,\n" + 
				            			"  FAS_MST_ACCT_UNITS MST_ACCT\n" + 
				            			"  WHERE TDA_TCA.ACCOUNTING_UNIT_ID=TDA_TCA1.TRF_ACCOUNTING_UNIT_ID\n" + 
				            			"  AND TDA_TCA.VOUCHER_NO=TDA_TCA1.ACCEPTING_SLNO\n" + 
				            			"  AND TDA_TCA.VOUCHER_DATE=TDA_TCA1.ACCEPTING_DATE\n" + 
				            			"  AND TDA_TCA.TRF_ACCOUNTING_UNIT_ID        = MST_ACCT.ACCOUNTING_UNIT_ID\n" + 
				            			"  AND TDA_TCA1.TRF_ACCOUNTING_UNIT_ID =?\n"+dur1+
				            			"  AND (TDA_TCA1.ACCEPTING_SLNO          IS NOT NULL)\n" + 
				            			"  AND TDA_TCA1.ACCEPTING_DATE           IS NOT NULL\n" + 
				            			"  AND TDA_TCA.STATUS                    =?\n and " + sub_str+				            			
				            			"  (TDA_TCA1.TDA_OR_TCA               ='"+jType+"' "+
				            			"  OR TDA_TCA1.TDA_OR_TCA                 ='"+jType1+"')  \n" + 
				            			"  ORDER BY tda_tca1.voucher_no\n" ;
				            			
				            	
				            	
				            	
				            }
				            catch(Exception e)
				            {
				            	System.out.println("Exception e==>"+e);
				            }
	           }
	           else
	           {
	        	   try
	        	   {
	        		 
	        		   sql="SELECT tda_tca.VOUCHER_NO, "+
                               "  mst_acct.ACCOUNTING_UNIT_NAME,"+
	            	 "  TO_CHAR(tda_tca.VOUCHER_DATE,'DD/MM/YYYY') AS vou_date ,"+
	            	 "   trim(TO_CHAR(tda_tca.total_amount,'99999999999999.99')) AS tot_amount,"+
	            	 "   tda_tca.particulars,"+
	            	 "   tda_tca.ACCEPTING_SLNO,"+
	            	 "   TO_CHAR(tda_tca.ACCEPTING_DATE,'DD/MM/YYYY') AS accept_date ,"+
	            	 "   tda_tca.ACCOUNTING_UNIT_ID,"+
	            	 " 	  tda_tca.ACCOUNTING_FOR_OFFICE_ID"+
	            	 " 	FROM fas_tda_tca_raised_mst tda_tca,"+
	            	 " 	  FAS_MST_ACCT_UNITS mst_acct"+
	            	 " 	WHERE tda_tca.ACCOUNTING_UNIT_ID =?"+dur1+
	            	 //" 	AND (tda_tca.ACCEPTING_SLNO IS NULL) and tda_tca.accepting_date is null"+
	            	 " 	AND tda_tca.status            =? and "+
	            	 sub_str+
	            	 " 	 (tda_tca.tda_or_tca        ='"+jType+"' or tda_tca.tda_or_tca='"+jType1+"') AND tda_tca.ACCOUNTING_UNIT_ID= mst_acct.ACCOUNTING_UNIT_ID"+
	            	 " 	ORDER BY tda_tca.voucher_no";   
	        		   
	        		 
	        		   
	        		   
	        		   
	        		   
	        	   }
	        	   catch(Exception e)
	        	   {
	        		   System.out.println("Exception e===>"+e);
	        	   }
	           }
				
	           try
	           {
	           
	           
	           System.out.println("SQL::::"+sql);
						            int count=0;
						            ps=con.prepareStatement(sql);
						            ps.setInt(1,cmbAcc_UnitCode);
						            ps.setInt(2,txtCB_Year);
						            ps.setInt(3,txtCB_Month);
						            ps.setString(4,cmbStatus);
                                                            rs=ps.executeQuery();
						            
					                while(rs.next())
					                {
					                	
					                	 xml=xml+"<flag>success</flag><Ucode>"+rs.getInt("ACCOUNTING_UNIT_ID")+"</Ucode><offcode>"+rs.getInt("ACCOUNTING_FOR_OFFICE_ID")+"</offcode><txtCB_Year>"+txtCB_Year+"</txtCB_Year><txtCB_Month>"+txtCB_Month+"</txtCB_Month>";
					                	
						                    xml=xml+"<lengAccepted>";
						                    xml=xml+"<vou_no>"+rs.getInt("VOUCHER_NO")+"</vou_no>";
						                    xml=xml+"<vou_date>"+rs.getString("vou_date")+"</vou_date>";
						                    xml=xml+"<org_unit>"+rs.getString("ACCOUNTING_UNIT_NAME")+"</org_unit>";
						                    xml=xml+"<acc_no>"+rs.getInt("ACCEPTING_SLNO")+"</acc_no>";
						                    xml=xml+"<acc_date>"+rs.getString("accept_date")+"</acc_date>";
						                    xml=xml+"<supplement_no>"+rs.getInt("supplement_no")+"</supplement_no>";
						                 //   xml=xml+"<Remak>"+rs.getString("particulars")+"</Remak>";
						              //    System.out.println("particulars>>>>>"+rs.getString("particulars"));
						                  if(rs.getString("particulars")!="" || rs.getString("particulars")!="null"){
						                	  xml=xml+"<Remak><![CDATA["+rs.getString("particulars")+"]]></Remak>";
						                  }
						                  else{
						                      xml=xml+"<Remak>"+"--"+"</Remak>";
						                  }
						                    xml=xml+"<Tot_Amt>"+rs.getString("tot_amount") +"</Tot_Amt>";
						                    xml=xml+"</lengAccepted>";
						                    
						                    count++;
					                }
					                if(count==0) 
					                {
						                    System.out.println("inside count==0");
						                    xml="<response><command>MonthAccepted</command><flag>failure</flag>";
					                }
				            }
				            catch(SQLException sqle)
				            {
					        	    sqle.printStackTrace();
					        	    System.out.println("error while fetching data " + sqle);
					                xml="<response><command>MonthAccepted</command><flag>failure</flag>";
				            }
        	 
	           
        }
        else if(strType.equalsIgnoreCase("DateAccepted"))  
        {
            if(cmbJournal_type.equals("63B") || cmbJournal_type.equals("66B")){
                 dur1=" AND (VOUCHER_DATE BETWEEN ? AND ?) ";
            }
            else if(cmbJournal_type.equals("63C") || cmbJournal_type.equals("66C")){
                dur1=" and ACCEPTING_DATE BETWEEN ? and ?";
            }
            System.out.println("dur1****::::"+dur1);
        	
        	String[] sd=request.getParameter("txtFrom_date").split("/");
            c=new GregorianCalendar(Integer.parseInt(sd[2]),Integer.parseInt(sd[1])-1,Integer.parseInt(sd[0]));
            java.util.Date d=c.getTime();
            txtFrom_date=new Date(d.getTime());
           // System.out.println("from_date "+txtFrom_date);
            
            sd=request.getParameter("txtTo_date").split("/");
            c=new GregorianCalendar(Integer.parseInt(sd[2]),Integer.parseInt(sd[1])-1,Integer.parseInt(sd[0]));
            d=c.getTime();
            txtTo_date=new Date(d.getTime());
        
	            xml="<response><command>DateAccepted</command>";                        
	           
				            try
				            {
				            	 sql="SELECT tda_tca.VOUCHER_NO,\n" + 
				            	 "  mst_acct.ACCOUNTING_UNIT_NAME,\n" + 
				            	 "  TO_CHAR(tda_tca.VOUCHER_DATE,'DD/MM/YYYY')              AS vou_date ,\n" + 
				            	 "  trim(TO_CHAR(tda_tca.total_amount,'99999999999999.99')) AS tot_amount,\n" + 
				            	 "  tda_tca.particulars,\n" + 
				            	 "  tda_tca.ACCEPTING_SLNO,\n" + 
				            	 "  TO_CHAR(tda_tca.ACCEPTING_DATE,'DD/MM/YYYY') AS accept_date ,\n" + 
				            	 "  tda_tca.ACCOUNTING_UNIT_ID,\n" + 
				            	 "  tda_tca.ACCOUNTING_FOR_OFFICE_ID\n" + 
				            	 "FROM fas_tda_tca_raised_mst tda_tca,\n" + 
				            	 "  FAS_MST_ACCT_UNITS mst_acct\n" + 
				            	 "WHERE tda_tca.TRF_ACCOUNTING_UNIT_ID =?\n" + dur1+
				                  "AND (tda_tca.ACCEPTING_SLNO  IS NOT NULL) and tda_tca.accepting_date is not null\n" + 
				            	 "AND tda_tca.status            =?\n" + 
				            	 "AND (tda_tca.tda_or_tca       =?\n" + 
				            	 "OR tda_tca.tda_or_tca         =?)\n" + 
				            	 "AND tda_tca.ACCOUNTING_UNIT_ID= mst_acct.ACCOUNTING_UNIT_ID\n" + 
				            	 "ORDER BY tda_tca.voucher_no";
						        //   System.out.println("SQL::::"+sql);
						            int count=0;
						            ps=con.prepareStatement(sql);
						            ps.setInt(1,cmbAcc_UnitCode);
						            ps.setDate(2,txtFrom_date);
						            ps.setDate(3,txtTo_date);
						            ps.setString(4,cmbStatus);
                                                            ps.setString(5,jType);
                                                            ps.setString(6,jType1);
						            rs=ps.executeQuery();
						            while(rs.next())
					                {
					                	 xml=xml+"<flag>success</flag><Ucode>"+rs.getInt("ACCOUNTING_UNIT_ID")+"</Ucode><offcode>"+rs.getInt("ACCOUNTING_FOR_OFFICE_ID")+"</offcode><txtCB_Year>"+txtCB_Year+"</txtCB_Year><txtCB_Month>"+txtCB_Month+"</txtCB_Month>";
					                	
						                    xml=xml+"<lengAccepted>";
						                    xml=xml+"<vou_no>"+rs.getInt("VOUCHER_NO")+"</vou_no>";
						                    xml=xml+"<vou_date>"+rs.getString("vou_date")+"</vou_date>";
						                    xml=xml+"<org_unit>"+rs.getString("ACCOUNTING_UNIT_NAME")+"</org_unit>";
						                    xml=xml+"<acc_no>"+rs.getInt("ACCEPTING_SLNO")+"</acc_no>";
						                    xml=xml+"<acc_date>"+rs.getString("accept_date")+"</acc_date>";
						                  //  xml=xml+"<Remak>"+rs.getString("particulars")+"</Remak>";
						               //    System.out.println("particulars>>>>>"+rs.getString("particulars"));
						                   if(rs.getString("particulars")!="" || rs.getString("particulars")!="null"){
						                	   xml=xml+"<Remak><![CDATA["+rs.getString("particulars")+"]]></Remak>";
						                   }
						                   else{
						                       xml=xml+"<Remak>"+"--"+"</Remak>";
						                   }
						                    xml=xml+"<Tot_Amt>"+rs.getString("tot_amount") +"</Tot_Amt>";
						                    xml=xml+"</lengAccepted>";
						                    
						                    count++;
					                }
					                if(count==0) 
					                {
						                    System.out.println("inside count==0");
						                    xml="<response><command>DateAccepted</command><flag>failure</flag>";
					                }
				            }
				            catch(SQLException sqle)
				            {
					        	    sqle.printStackTrace();
					        	    System.out.println("error while fetching data " + sqle);
					                xml="<response><command>DateAccepted</command><flag>failure</flag>";
				            }
               
        }
        xml=xml+"</response>";   
        out.println(xml); 
        System.out.println("xml in::"+xml); 
        
	}

	
}
