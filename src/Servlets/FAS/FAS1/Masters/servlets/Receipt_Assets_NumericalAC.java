package Servlets.FAS.FAS1.Masters.servlets;

import java.io.IOException;
import java.io.PrintWriter;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import java.sql.SQLException;
import java.sql.Timestamp;

import java.sql.Types;

import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.ResourceBundle;

import javax.servlet.*;
import javax.servlet.http.*;

import sun.util.calendar.Gregorian;

public class Receipt_Assets_NumericalAC extends HttpServlet {
    private static final String CONTENT_TYPE = "text/xml; charset=windows-1252";

   /* public void init(ServletConfig config) throws ServletException {
        super.init(config);
    }
*/
    public void doGet(HttpServletRequest request, 
                      HttpServletResponse response) throws ServletException, 
                                                           IOException {
       System.out.println("inside servelet");
     
        PrintWriter out = response.getWriter();
        response.setHeader("cache-control","no-cache");
        String CONTENT_TYPE = "text/xml; charset=windows-1252";
        response.setContentType(CONTENT_TYPE);
        String xml="<response>";
        HttpSession session = request.getSession(false);
        try {

            if (session == null) {

                System.out.println(request.getContextPath() + "/index.jsp");
                response.sendRedirect(request.getContextPath() + "/index.jsp");
                return;
            }
            System.out.println(session);

        } catch (Exception e) {
            System.out.println("Redirect Error :" + e);
        }


        Connection con = null;
        ResultSet rs = null;
        ResultSet rs2 = null;
        ResultSet rs3 = null;
        PreparedStatement ps = null;
        PreparedStatement ps2 = null;
        PreparedStatement ps1 = null;
        ResultSet rss1=null,rss2=null,rss3=null;
        String strCommand = "";
       
        try {
            ResourceBundle rs1 = 
                ResourceBundle.getBundle("Servlets.Security.servlets.Config");
            String ConnectionString = "";

            String strDriver = rs1.getString("Config.DATA_BASE_DRIVER");
            String strdsn = rs1.getString("Config.DSN");
            String strhostname = rs1.getString("Config.HOST_NAME");
            String strportno = rs1.getString("Config.PORT_NUMBER");
            String strsid = rs1.getString("Config.SID");
            String strdbusername = rs1.getString("Config.USER_NAME");
            String strdbpassword = rs1.getString("Config.PASSWORD");
            ConnectionString = strdsn.trim() + "://" + strhostname.trim() + ":" + strportno.trim() + "/" +strsid.trim() ;    // Postgres DB  Connection

            Class.forName(strDriver.trim());
            con = DriverManager.getConnection(ConnectionString, strdbusername.trim(), 
                            strdbpassword.trim());
        } catch (Exception e) {
            System.out.println("Exception in opening connection :" + e);

        }

        try {
            strCommand = request.getParameter("Command");
            System.out.println("assign..here command..." + strCommand);

        }

        catch (Exception e) {
            System.out.println("Exception in assigning..." + e);
        }
        System.out.println("servelets");
        int cmbasset = 0, cmbmajorclass = 0,offid=0,journalno=0;
        int cmbAcc_UnitCode = 0,cmbOffice_code=0,assetcode=0,txtqtyrecieved=0,receiptno=0,txtvaluerecieved=0;
    //    int count=0;
        //String txtqtyrecieved = "";
        String txtRemarks = "", cmbFinancialYear="";
        String journaldate="",txtcheck="",receiptdate="",dateref="";
        Date receiptdate_entry = null,journal_date=null,refdate=null,refdate_entry=null;
        Calendar c;
        double Amc_Amount=0; 
          int recflag=0,CashBookYear=0,CashBookMonth=0,refno=0;

        
        String update_user = (String)session.getAttribute("UserId");
        long l = System.currentTimeMillis();
        Timestamp ts = new Timestamp(l);
        String txtrefno=request.getParameter("txtrefno");
        String txtrefdate=request.getParameter("txtrefdate");
        String cmbassetclass=request.getParameter("cmbassetclass");
        System.out.println("cmbassetclass......"+cmbassetclass);
       System.out.println("txtrefno***************"+txtrefno);
        try {
                refno = Integer.parseInt(txtrefno);
        
        } catch (Exception e) {
            //System.out.println("Exception to catch refno>============= "+e);
        	e.printStackTrace();
        }
        
        try {
        	cmbmajorclass = Integer.parseInt(cmbassetclass);
        	System.out.println("cmbmajorclass........."+cmbmajorclass);
        } catch (Exception e) {
            //System.out.println("Exception to catch cmbmajorclass"+e);
        	e.printStackTrace();
        }
        try {
        	cmbAcc_UnitCode = Integer.parseInt(request.getParameter("cmbAcc_UnitCode"));
        } catch (Exception e) {
            System.out.println("Exception to catch cmbAcc_UnitCode ");
        }
        try {
        	cmbOffice_code = Integer.parseInt(request.getParameter("cmbOffice_code"));
        } catch (Exception e) {
            System.out.println("Exception to catch cmbOffice_code ");
        }
        
         try{
         txtqtyrecieved = Integer.parseInt(request.getParameter("txtqtyrecieved"));
         txtvaluerecieved = Integer.parseInt(request.getParameter("txtvaluerecieved"));
         }
        catch (Exception e) {
                    System.out.println("Exception to catch txtqtyrecieved ");
                }
        txtcheck=request.getParameter("txtcheck");

      
        txtRemarks = request.getParameter("txtRemarks");
        cmbFinancialYear=request.getParameter("cmbFinancialYear");
        System.out.println("financial_year"+cmbFinancialYear);
        receiptdate=request.getParameter("receipt_date");
        dateref=request.getParameter("txtrefdate");
        journaldate=request.getParameter("txtjournal_date");
        /** Get Cashbook Year */
        String CashBook_Year = request.getParameter("txtCB_Year");
        
        /** Get Cashbook Month */
        String CashBook_Month = request.getParameter("txtCB_Month");
        String cmbassetdesc=request.getParameter("txtassetdesc");
        String txtOffice_Name=request.getParameter("txtOffice_Name");
        String cmbjournalno=request.getParameter("cmbjournalno");
        String txtreceiptno=request.getParameter("txtreceiptno");
        try {
                receiptno = Integer.parseInt(request.getParameter("txtreceiptno"));
        
        } catch (Exception e) {
            //System.out.println("Exception to catch txtreceiptno "+e);
        	e.printStackTrace();
        }
      
        try {
                journalno = Integer.parseInt(cmbjournalno);
        
        } catch (Exception e) {
            System.out.println("Exception to catch cmbjournalno ");
        }
        try {
                offid = Integer.parseInt(txtOffice_Name);
        
        } catch (Exception e) {
            System.out.println("Exception to catch off id ");
        }
        
        /** Convert String to Interger -- Cashbook Year */
        try 
        {
            CashBookYear = Integer.parseInt(CashBook_Year);            
        } catch (Exception e) 
        {
            System.out.println("Exception in Year:" + e);
            CashBookYear = 0;
        }
        
        /** Convert String to Interger -- Cashbook Month */
        try 
        {
            CashBookMonth = Integer.parseInt(CashBook_Month);                     
        } catch (Exception e) 
        {
            System.out.println("Exception in Month:" + e);
            CashBookMonth = 0;
        }
        
         if(strCommand.equalsIgnoreCase("loadAssetCode"))
        {       
        	System.out.println(cmbAcc_UnitCode);
          	System.out.println(cmbOffice_code);
          	System.out.println(cmbmajorclass);
          	System.out.println(cmbFinancialYear);
        	    xml="<response>";
                xml=xml+"<command>loadAssetCode</command>";
                String[] divyear=cmbFinancialYear.split("-");
            	String newyear=(Integer.parseInt(divyear[0])-1)+"-"+(Integer.parseInt(divyear[1])-1);
                try{
                	//PreparedStatement pstmt = con.prepareStatement("select A.ASSET_CODE, A.PARTICULARS from FAS_ASSET_VAL_AC_DETAILS A inner join COM_MST_ASSETS_CLASS B on B.ASSET_TYPE_CODE=A.ASSET_TYPE_CODE WHERE ACCOUNTING_UNIT_ID=? and ACCOUNTING_UNIT_OFFICE_ID=? and ASSET_CLASS_CODE=? and FINANCIAL_YEAR=?");
                	//PreparedStatement pstmt = con.prepareStatement("SELECT A.ASSET_CODE as ASSET_CODE,A.PARTICULARS as PARTICULARS FROM FAS_ASSET_VAL_AC_DETAILS A WHERE a.accounting_unit_id =? AND A.accounting_unit_office_id= ? AND A.asset_major_class_code   = ? AND a.financial_year =?");
                	PreparedStatement pstmt = con.prepareStatement("SELECT A.ASSET_CODE as ASSET_CODE,A.PARTICULARS as PARTICULARS FROM FAS_A52_REGISTER A WHERE A.accounting_unit_office_id= ? AND A.asset_major_class_code   = ? AND a.financial_year =?");
                	//System.out.println(pstmt);
                	//pstmt.setInt(1,cmbAcc_UnitCode);
                    pstmt.setInt(1,cmbOffice_code);
                    pstmt.setInt(2,cmbmajorclass);
                    pstmt.setString(3,newyear);
                    rs2 = pstmt.executeQuery();                   
                    int count = 0;
                    while(rs2.next()){
                        xml+="<assetid>"+rs2.getString("ASSET_CODE")+"</assetid>" +
                                         "<assetName><![CDATA["+rs2.getString("PARTICULARS")+"]]></assetName>";
                    count++;
                    }
                        if (count > 0)
                        {
                        	xml=xml+"<count>"+count+"</count>";
                        	xml=xml+"<flag>success</flag>";
                        }else{
                        	xml=xml+"<flag>failure</flag>";
                        }
                }
                catch(SQLException e) {
                       e.printStackTrace();
                        xml=xml+"<flag>failure</flag>";
                }
        }
         else if(strCommand.equals("loadAssetDesc")){
        	
           String cmbassetcode=request.getParameter("cmbassetcode");
        	xml="<response>";
            xml=xml+"<command>loadAssetCode</command>";
            String[] divyear=cmbFinancialYear.split("-");
        	String newyear=(Integer.parseInt(divyear[0])-1)+"-"+(Integer.parseInt(divyear[1])-1);
            try{
            	//PreparedStatement pstmt = con.prepareStatement("select PARTICULARS from FAS_ASSET_VAL_AC_DETAILS where ASSET_CODE=?");
            	PreparedStatement pstmt = con.prepareStatement("select a.PARTICULARS from FAS_A52_REGISTER a  WHERE A.accounting_unit_office_id= ? AND A.asset_major_class_code   = ? AND a.financial_year =? and a.ASSET_CODE=?");
            	//pstmt.setInt(1,cmbAcc_UnitCode);
                pstmt.setInt(1,cmbOffice_code);
                pstmt.setInt(2,cmbmajorclass);
                pstmt.setString(3,newyear);
            	pstmt.setInt(4, Integer.parseInt(cmbassetcode));
            	 rs2 = pstmt.executeQuery();
            	 int count=0;
            	 while(rs2.next()){
                     xml=xml+ "<AssetName><![CDATA["+rs2.getString("PARTICULARS")+"]]></AssetName>";
                 count++;
                 }
                     if (count > 0)
                     {
                     	xml=xml+"<count>"+count+"</count>";
                     	xml=xml+"<flag1>success</flag1>";
                     }else{
                     	xml=xml+"<flag1>failure</flag1>";
                     }
            }catch(SQLException e){
            	e.printStackTrace();
            	
            }
    }else if(strCommand.equals("loadjournalno"))
               {
                       xml+="<command>loadjournalno</command>";
                       
                       try
                       {     System.out.println("CashBookYear"+CashBookYear);
                             System.out.println("CashBookMonth"+CashBookMonth);
                               PreparedStatement pstmt = con.prepareStatement("select ORGINATING_JVR_NO,to_char(ORGINATING_JVR_DATE,'DD/MM/YYYY') as jvdate from FAS_TDA_TCA_RAISED_MST2 where " + 
                                                   "ACCOUNTING_UNIT_ID=? and ACCOUNTING_FOR_OFFICE_ID=? and CASHBOOK_YEAR=? and CASHBOOK_MONTH=? and TDA_OR_TCA='TDAO'");
                               System.out.println(pstmt);
                           pstmt.setInt(1,cmbAcc_UnitCode);
                           pstmt.setInt(2,cmbOffice_code);
                           pstmt.setInt(3,CashBookYear);
                           pstmt.setInt(4,CashBookMonth);
                               rs2 = pstmt.executeQuery();
                               
                           int count = 0;
                           while(rs2.next())
                           {
                               xml+="<jvno>"+rs2.getInt("ORGINATING_JVR_NO")+"</jvno>" +
                               "<jvdate><![CDATA["+rs2.getString("jvdate")+"]]></jvdate>";
                              count++;                  
                           }
                           if (count == 0)
                               xml = xml + "<flag>nodata</flag>";
                           else
                           xml = xml + "<flag>success</flag>";
                           System.out.println("count  " + count);
                           pstmt.close();
                       }
                       catch(SQLException e) {
                               System.out.println("Exception executing 'LoadMajorClass Query'"+e);
                               xml=xml+"<flag>failure</flag>";
                       }
               } 
    else if (strCommand.equalsIgnoreCase("checkStatus")) {
    	int c1=0,c2=0,c3=0;
        xml = "<response><command>checkStatus</command>";
        System.out.println("checkStatus in receipt ");
        try {
        	
        	String[] divyear=cmbFinancialYear.split("-");
        	String newyear=(Integer.parseInt(divyear[0])-1)+"-"+(Integer.parseInt(divyear[1])-1);
        	System.out.println("new year  "+newyear);
        	int cricleID=0;
        	int cricleuintid=0;
        	String selectCricleunitid="select ACCT_RENDERING_UNIT_ID from FAS_ASSET_VAL_AC_RENDER_UNITS where STATUS='L' and  ACCT_UNIT_ID_RENDERED_FOR=? and RENDERING_UNIT_OFFICE_ID=?";
        	
        	PreparedStatement pss0=con.prepareStatement(selectCricleunitid);
            pss0.setInt(1, cmbAcc_UnitCode);
            pss0.setInt(2, cmbOffice_code);
            ResultSet rss0=pss0.executeQuery();
            if(rss0.next()){
            	cricleuintid=rss0.getInt("ACCT_RENDERING_UNIT_ID");	
            	
            }
           
            
        	/*String selectCircleID="select CIRCLE_OFFICE_ID from COM_MST_ALL_OFFICES_VIEW where OFFICE_ID=?";
        	  ps =  con.prepareStatement(selectCircleID);
              ps.setInt(1, cmbOffice_code);
              rss1=ps.executeQuery();
              while(rss1.next()){
            	  cricleID=rss1.getInt("CIRCLE_OFFICE_ID");	  
              }*/
            //  System.out.println("circle Id "+cricleID);
           // System.out.println("cricleuintid "+cricleuintid);
            String selectCircleID="select ACCOUNTING_UNIT_OFFICE_ID from FAS_MST_ACCT_UNITS where ACCOUNTING_UNIT_ID=?";
      	  ps =  con.prepareStatement(selectCircleID);
            ps.setInt(1, cricleuintid);
            rss1=ps.executeQuery();
            if(rss1.next()){
          	  cricleID=rss1.getInt("ACCOUNTING_UNIT_OFFICE_ID");	
          	  

            }
          //  System.out.println("cricleID "+cricleID);	

         	String searchquery="select A52_STATUS_VAL from FAS_A52_AA52_VERIFY_STATUS where ACCOUNTING_FOR_OFFICE_ID=? and FINANCIAL_YEAR='"+newyear+"' ";//and finanical_year='"+cmbFinancialYear+"' 
         	
             ps1 =  con.prepareStatement(searchquery);
             ps1.setInt(1, cricleID);
            // ps1.setString(2, newyear);
             rss2=ps1.executeQuery();
          //   System.out.println(" searchquery  "+searchquery);
            if(rss2.next()){
				xml = xml + "<flag>freezeCricle</flag>";
				System.out.println("freezeCricle   ");
				
            }else{
            	xml = xml + "<flag>notfreezeCricle</flag>";
            } 

        } catch (SQLException e) {
            e.printStackTrace();
            xml = xml + "<flag>failure</flag>";
        }
     
       
    }
      else if (strCommand.equalsIgnoreCase("Add")) {

            xml = "<response><command>Add</command>";
           // String particulars="";
            System.out.println("add");
            try {
                    cmbasset = Integer.parseInt(request.getParameter("cmbassetcode"));
                    
                  //  particulars=request.getParameter("txtassetdesc");
            
            } catch (Exception e) {
                System.out.println("Exception to catch asset id ");
            }
          int assetcode1=0,asstcd=0,asstcd1=0;
           try
            {
                String[] sd3 =txtrefdate.split("/");
                c = new GregorianCalendar(Integer.parseInt(sd3[2]), Integer.parseInt(sd3[1]) - 1,Integer.parseInt(sd3[0]));
                java.util.Date d3 = c.getTime();
                refdate = new Date(d3.getTime());
                System.out.println("txtrefdate " + refdate);
                
                
               
                   
            }
            catch (Exception e){
                System.out.println("Exception to catch refdate "+e); 
            }
            int cricleid=0;
            try {
            	try{
            		
            		 PreparedStatement pss0=con.prepareStatement("select ACCT_RENDERING_UNIT_ID From FAS_ASSET_VAL_AC_RENDER_UNITS where RENDERING_UNIT_OFFICE_ID="+cmbOffice_code);
                     
                     ResultSet rss0=pss0.executeQuery();
                     if(rss0.next()){
                    	 cricleid=rss0.getInt("ACCT_RENDERING_UNIT_ID");
                     }
            		
            		
            	 PreparedStatement pss=con.prepareStatement("select MAX(ASSET_CODE) as ASSET_CODE from FAS_A52_REGISTER_EDIT where ASSET_MAJOR_CLASS_CODE="+cmbmajorclass +" and ACCOUNTING_UNIT_ID="+cricleid);
                 
                 ResultSet rss=pss.executeQuery();
                 if(rss.next()){
                 	asstcd=rss.getInt("ASSET_CODE");
                 }
             
 		 PreparedStatement pss1=con.prepareStatement("select MAX(ASSET_CODE) as ASSET_CODE from FAS_ASSETS_RECEIPT where ASSET_MAJOR_CLASS_CODE="+cmbmajorclass+" and ACCOUNTING_UNIT_ID="+cmbAcc_UnitCode);
 		                
 		                ResultSet rsss1=pss1.executeQuery();
 		                if(rsss1.next()){
 		                	asstcd1=rsss1.getInt("ASSET_CODE");
 		                } 
                // System.out.println("cricleid  "+cricleid+"asstcd "+asstcd +" asstcd1 "+asstcd1);
                 if (asstcd>0){
                 int asstcd0=asstcd+1;
                 if(asstcd1>=asstcd0){
                 	assetcode1=asstcd1+1;
                 }
                 else{
                 	 assetcode1=asstcd0;
                 }
                 }
                 else{
                 	assetcode1=1;
                 	
                 }
            	}
            	catch (Exception e) {
					System.out.println("Max finding "+e);
				}
            	//System.out.println("assetcode1 "+assetcode1);

                ps =  con.prepareStatement("insert into FAS_ASSETS_RECEIPT(ACCOUNTING_UNIT_ID,ACCOUNTING_FOR_OFFICE_ID,CASHBOOK_YEAR,CASHBOOK_MONTH,ASSET_MAJOR_CLASS_CODE,ASSET_CODE," + 
                      "RECEIPT_NO,RECEIPT_DATE,RECEIVED_FROM_LEVEL_ID,RECEIVED_FROM_OFFICE,MBOOK_NO,MBOOK_DATE,RECEIVED_QTY,REMARKS,TDA_ACCEPTING_JVR_NO,TDA_ACCEPTING_JVR_DATE,"+
                      "UPDATED_BY_USER_ID,UPDATED_DATE,FINANCIAL_YEAR,RECEIVED_VALUE,PARTICULARS) values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)");
                ps.setInt(1, cmbAcc_UnitCode);
                ps.setInt(2, cmbOffice_code);
                ps.setInt(3,CashBookYear);
                ps.setInt(4,CashBookMonth);
                ps.setInt(5,cmbmajorclass);
               ps.setInt(6,assetcode1);
                ps.setInt(7,receiptno);
                if(receiptdate.equals(""))
                    ps.setNull(8,Types.DATE);
                else
                {
                    String[] sd2 =receiptdate.split("/");
                    c = new GregorianCalendar(Integer.parseInt(sd2[2]), Integer.parseInt(sd2[1]) - 1,Integer.parseInt(sd2[0]));
                    java.util.Date d2 = c.getTime();
                    receiptdate_entry = new Date(d2.getTime());
                    System.out.println("receiptdate " + receiptdate_entry);
                    ps.setDate(8, receiptdate_entry);    
                }
                ps.setString(9, txtcheck);
                ps.setInt(10,offid);
                ps.setInt(11,refno);
                ps.setDate(12,refdate);
                ps.setInt(13,txtqtyrecieved);
                ps.setString(14, txtRemarks);
                ps.setInt(15,journalno);
               if(journaldate.equals(""))
                    ps.setNull(16,Types.DATE);
                else
                {
                    String[] sd1 = journaldate.split("/");
                    c = 
                    new GregorianCalendar(Integer.parseInt(sd1[2]), Integer.parseInt(sd1[1]) - 1,
                                Integer.parseInt(sd1[0]));
                    java.util.Date d1 = c.getTime();
                    journal_date = new Date(d1.getTime());
                    System.out.println("journal_date " + journal_date); 
                    ps.setDate(16,journal_date);
                }
                      
                ps.setString(17, update_user);
                ps.setTimestamp(18, ts);
                ps.setString(19,cmbFinancialYear);
                ps.setInt(20,txtvaluerecieved);
                ps.setString(21,cmbassetdesc);
                
                ps.executeUpdate();
                xml = xml + "<flag>success</flag>";
                System.out.println("here is ok");
             

            } catch (SQLException e) {
                e.printStackTrace();
                xml = xml + "<flag>failure</flag>";
            }
         
           
        } else if (strCommand.equalsIgnoreCase("UpdateRow")) {
        	
         
            xml = "<response><command>Update</command>";
            System.out.println("inside update command");
            try {
                    cmbasset = Integer.parseInt(request.getParameter("cmbassetcode"));
            
            } catch (Exception e) {
                System.out.println("Exception to catch asset id ");
            }
            System.out.println("cmbasset===="+cmbasset);
            try {
                ps =  con.prepareStatement("update FAS_ASSETS_RECEIPT set RECEIPT_DATE=?,RECEIVED_FROM_LEVEL_ID=?,RECEIVED_FROM_OFFICE=?, MBOOK_NO=?,MBOOK_DATE=?,RECEIVED_QTY=?,REMARKS=?,UPDATED_BY_USER_ID=?,UPDATED_DATE=?,RECEIVED_VALUE=? where ACCOUNTING_UNIT_ID=? and  ACCOUNTING_FOR_OFFICE_ID=? and ASSET_CODE=? and RECEIPT_NO=? and CASHBOOK_YEAR=? and CASHBOOK_MONTH=? and ASSET_MAJOR_CLASS_CODE=? and FINANCIAL_YEAR=?");
                //ps.setInt(1,cmbasset);
                //ps.setInt(2,receiptno); ,TDA_ACCEPTING_JVR_NO=?, TDA_ACCEPTING_JVR_DATE=?
                if(receiptdate.equals(""))
                    ps.setNull(1,Types.DATE);
                else
                {
                    String[] sd2 =receiptdate.split("/");
                    c = new GregorianCalendar(Integer.parseInt(sd2[2]), Integer.parseInt(sd2[1]) - 1,Integer.parseInt(sd2[0]));
                    java.util.Date d2 = c.getTime();
                    receiptdate_entry = new Date(d2.getTime());
                    System.out.println("date " + receiptdate_entry);
                    ps.setDate(1, receiptdate_entry);    
                }
                
                ps.setString(2, txtcheck);
                ps.setInt(3,offid);
                ps.setInt(4,refno);
               // ps.setDate(5, refdate);
          
                if(txtrefdate.equals(""))
                    ps.setNull(5,Types.DATE);
                else
                {
                    String[] sd3 =txtrefdate.split("/");
                    c = new GregorianCalendar(Integer.parseInt(sd3[2]), Integer.parseInt(sd3[1]) - 1,Integer.parseInt(sd3[0]));
                    java.util.Date d3 = c.getTime();
                    refdate = new Date(d3.getTime());
                    System.out.println("date " + refdate);
                    ps.setDate(5, refdate);    
                }
                
                ps.setInt(6,txtqtyrecieved);
                
               // ps.setInt(7,journalno);
                
                /*if(journaldate.equals(""))
                    ps.setNull(8,Types.DATE);
                else
                {
                    String[] sd1 = journaldate.split("/");
                    c = 
                    new GregorianCalendar(Integer.parseInt(sd1[2]), Integer.parseInt(sd1[1]) - 1,
                                Integer.parseInt(sd1[0]));
                    java.util.Date d1 = c.getTime();
                    journal_date = new Date(d1.getTime());
                    System.out.println("journal_date " + journal_date); 
                    ps.setDate(8,journal_date);
                }*/
                ps.setString(7, txtRemarks);              
                ps.setString(8, update_user);
                ps.setTimestamp(9, ts);
                ps.setInt(10,txtvaluerecieved);
                
                ps.setInt(11, cmbAcc_UnitCode);
                ps.setInt(12, cmbOffice_code);
                ps.setInt(13,cmbasset);
                ps.setInt(14,receiptno);
                ps.setInt(15,CashBookYear);
                ps.setInt(16,CashBookMonth);
                ps.setInt(17,cmbmajorclass);
                ps.setString(18,cmbFinancialYear);
                ps.executeUpdate();
                xml = xml + "<flag>success</flag>";
                System.out.println("here is ok");
             
 
                ps.executeUpdate();
                xml = xml + "<flag>success</flag>";
            }

            catch (SQLException e) {
               e.printStackTrace();
                xml = xml + "<flag>failure</flag>";
            }
           

        }  else if (strCommand.equalsIgnoreCase("Delete")) {
         // System.out.println("deleteee cmbasset "+cmbasset);
            xml = "<response><command>Delete</command>";
            try {
                cmbasset = Integer.parseInt(request.getParameter("cmbassetcode"));
        
        } catch (Exception e) {
            System.out.println("Exception to catch asset id ");
        }
            try {
                ps = con.prepareStatement("delete from  FAS_ASSETS_RECEIPT " + 
                      " where ACCOUNTING_UNIT_ID=? and ACCOUNTING_FOR_OFFICE_ID=? and CASHBOOK_YEAR=? and CASHBOOK_MONTH=? and ASSET_MAJOR_CLASS_CODE=? and FINANCIAL_YEAR=? and ASSET_CODE=?");
                ps.setInt(1, cmbAcc_UnitCode);
                ps.setInt(2, cmbOffice_code);
                ps.setInt(3,CashBookYear);
                ps.setInt(4,CashBookMonth);
                ps.setInt(5,cmbmajorclass);
                ps.setString(6,cmbFinancialYear);
                ps.setInt(7,cmbasset);
               int pp= ps.executeUpdate();
                if(pp>0)
                xml = xml + "<flag>success</flag>";
                else
                	  xml = xml + "<flag>failure</flag>";
            } catch (SQLException e) {
                e.printStackTrace();
                xml = xml + "<flag>failure</flag>";
            }

        }
 
    
        xml = xml + "</response>";
        System.out.println("xml>>>"+xml);
        out.println(xml);
        out.close();
       
    }
   
}

    