package Servlets.FAS.FAS1.GPF.servlets;

import Servlets.FAS.FAS1.CommonControls.servlets.Com_CashBook1;
import Servlets.Security.classes.UserProfile;

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
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.ResourceBundle;

import javax.servlet.*;
import javax.servlet.http.*;
/**
 * Servlet implementation class GPF_Debit_Schedule
 */
public class GPF_Debit_Schedule extends HttpServlet {
	private String CONTENT_TYPE = "text/xml; charset=windows-1252";
	private static final long serialVersionUID = 1L;
    /**
     * @see HttpServlet#HttpServlet()
     */
    public GPF_Debit_Schedule() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	
		
		System.out.println("GPF_Debit_Schedule Servlet Called!........");
		
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
        ResultSet rs=null,rs2=null,rs3=null;
        //CallableStatement cs=null;
        PreparedStatement ps=null,ps1=null,ps2=null,ps3=null;
        String xml="";
        int k=0;
        int cmbAcc_UnitCode = 0, cmbOffice_code =0;
        int txtCB_Year = 0, txtCB_Month = 0;
        String debit_type=null,majorcc=null,minorcc=null;
        int emp_id=0,count=0;
        
        
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
            con =
 DriverManager.getConnection(ConnectionString, strdbusername.trim(),
                             strdbpassword.trim());
        } catch (Exception e) {
            System.out.println("Exception in opening connection :" + e);
            //sendMessage(response,"probably Failed to Establish connection to the database server.. due to "+e,"ok");

        }

        response.setContentType(CONTENT_TYPE);
        response.setHeader("Cache-Control", "no-cache");
        PrintWriter out = response.getWriter();
        String strCommand = "";
        
        String update_user=(String)session.getAttribute("UserId");
        long l = System.currentTimeMillis();
		Timestamp ts = new Timestamp(l);
        try {
            strCommand = request.getParameter("Command");
            System.out.println("assign..here command..." + strCommand);
        }

        catch (Exception e) {
            System.out.println("Exception in assigning..." + e);
        }
        if (strCommand.equalsIgnoreCase("Add")) {
            String CONTENT_TYPE = "text/xml; charset=windows-1252";
            response.setContentType(CONTENT_TYPE);
            xml = xml + "<response><command>Add</command>";
       
        try {
            cmbAcc_UnitCode =
                    Integer.parseInt(request.getParameter("unit_id"));
        } catch (NumberFormatException e) {
            System.out.println("exception" + e);
        }
        System.out.println("cmbAcc_UnitCode " + cmbAcc_UnitCode);

        try {
            cmbOffice_code =
                    Integer.parseInt(request.getParameter("office_id"));
        } catch (NumberFormatException e) {
            System.out.println("exception" + e);
        }
        System.out.println("cmbOffice_code " + cmbOffice_code);
        
        txtCB_Year = Integer.parseInt(request.getParameter("cbyear"));
        System.out.println("txtCB_Year " + txtCB_Year);
        txtCB_Month = Integer.parseInt(request.getParameter("cbmonth"));
        System.out.println("txtCB_Month " + txtCB_Month);
        
        majorcc=request.getParameter("majorType");
        minorcc=request.getParameter("minorType");
        String subtype=request.getParameter("subtype");
        int txtEmpID_mas = Integer.parseInt(request.getParameter("txtEmpID_mas"));
        String cmbMas_SL_Code = request.getParameter("cmbMas_SL_Code");
        
        
        debit_type = request.getParameter("type");
        
        System.out.println("debit_type " + debit_type);
        
        
        Servlets.Security.classes.UserProfile empProfile = (Servlets.Security.classes.UserProfile) session
				.getAttribute("UserProfile");
        System.out.println("user id::"+empProfile.getEmployeeId());
         emp_id=empProfile.getEmployeeId();
         
        System.out.println("emp id::"+emp_id);
        try
        {
        	con.setAutoCommit(false);
        	
//        	if(debit_type.equalsIgnoreCase("R"))
//        	{
//        	ps =con.prepareStatement("SELECT MAS.ACCOUNTING_UNIT_ID,MAS.ACCOUNTING_FOR_OFFICE_ID, TRN.PAYABLE_VOUCHER_NO,"
//        		+ " to_char(TRN.PAYABLE_VOUCHER_DATE,'DD/MM/YYYY') as pay_voudate,trim(to_char(TRN.AMOUNT,'99999999999999.99')) as  AMOUNT,TRN.CHEQUE_DD_DATE ,TRN.CHEQUE_DD_NO, TRN.ACCOUNT_HEAD_CODE,"
//        		+ " trn.CR_DR_INDICATOR FROM FAS_PAYMENT_MASTER mas,FAS_PAYMENT_TRANSACTION TRN"
//        		+ " WHERE MAS.ACCOUNTING_UNIT_ID    =TRN.ACCOUNTING_UNIT_ID "
//        		+ " AND MAS.ACCOUNTING_FOR_OFFICE_ID=TRN.ACCOUNTING_FOR_OFFICE_ID "
//        		+ " AND MAS.VOUCHER_NO              =TRN.VOUCHER_NO "
//        		+ " AND MAS.CASHBOOK_YEAR           =TRN.CASHBOOK_YEAR "
//        		+ " AND MAS.CASHBOOK_MONTH          =TRN.CASHBOOK_MONTH "
//        		+ " AND mas.ACCOUNTING_UNIT_ID    ="+cmbAcc_UnitCode+
//        				" AND mas.ACCOUNTING_FOR_OFFICE_ID ="+cmbOffice_code+ " AND mas.CASHBOOK_YEAR= "+txtCB_Year+ 
//        				" AND mas.CASHBOOK_MONTH = "+txtCB_Month+ " and mas.MODE_OF_CREATION='A'  AND TRN.ACCOUNT_HEAD_CODE in (390305,391503) ");
//        	
//        	
//        	
//        	System.out.println("SELECT MAS.ACCOUNTING_UNIT_ID,MAS.ACCOUNTING_FOR_OFFICE_ID, TRN.PAYABLE_VOUCHER_NO,"
//        		+ " to_char(TRN.PAYABLE_VOUCHER_DATE,'DD/MM/YYYY') as pay_voudate,trim(to_char(TRN.AMOUNT,'99999999999999.99')) as  AMOUNT,TRN.CHEQUE_DD_DATE ,TRN.CHEQUE_DD_NO, TRN.ACCOUNT_HEAD_CODE,"
//        		+ " trn.CR_DR_INDICATOR FROM FAS_PAYMENT_MASTER mas,FAS_PAYMENT_TRANSACTION TRN"
//        		+ " WHERE MAS.ACCOUNTING_UNIT_ID    =TRN.ACCOUNTING_UNIT_ID "
//        		+ " AND MAS.ACCOUNTING_FOR_OFFICE_ID=TRN.ACCOUNTING_FOR_OFFICE_ID "
//        		+ " AND MAS.VOUCHER_NO              =TRN.VOUCHER_NO "
//        		+ " AND MAS.CASHBOOK_YEAR           =TRN.CASHBOOK_YEAR "
//        		+ " AND MAS.CASHBOOK_MONTH          =TRN.CASHBOOK_MONTH "
//        		+ " and mas.ACCOUNTING_UNIT_ID    ="+cmbAcc_UnitCode+
//        				" AND mas.ACCOUNTING_FOR_OFFICE_ID ="+cmbOffice_code+ " AND mas.CASHBOOK_YEAR= "+txtCB_Year+ 
//        				" AND mas.CASHBOOK_MONTH = "+txtCB_Month+ " and mas.MODE_OF_CREATION='A' AND TRN.ACCOUNT_HEAD_CODE in (390305,391503)");
//        	
//        	
//        	
//        	
//        	
//        	}
//        	else if(debit_type.equalsIgnoreCase("IM-R"))
//        	{
//        		ps =con.prepareStatement("SELECT MAS.ACCOUNTING_UNIT_ID,MAS.ACCOUNTING_FOR_OFFICE_ID, TRN.PAYABLE_VOUCHER_NO,"
//                		+ " to_char(TRN.PAYABLE_VOUCHER_DATE,'DD/MM/YYYY') as pay_voudate,trim(to_char(TRN.AMOUNT,'99999999999999.99')) as  AMOUNT ,TRN.CHEQUE_DD_DATE ,TRN.CHEQUE_DD_NO, TRN.ACCOUNT_HEAD_CODE,"
//                		+ " trn.CR_DR_INDICATOR FROM FAS_PAYMENT_MASTER mas,FAS_PAYMENT_TRANSACTION TRN"
//                		+ " WHERE MAS.ACCOUNTING_UNIT_ID    =TRN.ACCOUNTING_UNIT_ID "
//                		+ " AND MAS.ACCOUNTING_FOR_OFFICE_ID=TRN.ACCOUNTING_FOR_OFFICE_ID "
//                		+ " AND MAS.VOUCHER_NO              =TRN.VOUCHER_NO "
//                		+ " AND MAS.CASHBOOK_YEAR           =TRN.CASHBOOK_YEAR "
//                		+ " AND MAS.CASHBOOK_MONTH          =TRN.CASHBOOK_MONTH "
//                		+ " and mas.ACCOUNTING_UNIT_ID    ="+cmbAcc_UnitCode+
//                				" AND mas.ACCOUNTING_FOR_OFFICE_ID ="+cmbOffice_code+ " AND mas.CASHBOOK_YEAR= "+txtCB_Year+ 
//                				" AND mas.CASHBOOK_MONTH = "+txtCB_Month+ " and mas.MODE_OF_CREATION='A' AND TRN.ACCOUNT_HEAD_CODE in (391003) ");	
//        		
//        		System.out.println("SELECT MAS.ACCOUNTING_UNIT_ID,MAS.ACCOUNTING_FOR_OFFICE_ID, TRN.PAYABLE_VOUCHER_NO,"
//                		+ " to_char(TRN.PAYABLE_VOUCHER_DATE,'DD/MM/YYYY') as pay_voudate,trim(to_char(TRN.AMOUNT,'99999999999999.99')) as  AMOUNT,TRN.CHEQUE_DD_DATE ,TRN.CHEQUE_DD_NO, TRN.ACCOUNT_HEAD_CODE,"
//                		+ " trn.CR_DR_INDICATOR FROM FAS_PAYMENT_MASTER mas,FAS_PAYMENT_TRANSACTION TRN"
//                		+ " WHERE MAS.ACCOUNTING_UNIT_ID    =TRN.ACCOUNTING_UNIT_ID "
//                		+ " AND MAS.ACCOUNTING_FOR_OFFICE_ID=TRN.ACCOUNTING_FOR_OFFICE_ID "
//                		+ " AND MAS.VOUCHER_NO              =TRN.VOUCHER_NO "
//                		+ " AND MAS.CASHBOOK_YEAR           =TRN.CASHBOOK_YEAR "
//                		+ " AND MAS.CASHBOOK_MONTH          =TRN.CASHBOOK_MONTH "
//                		+ " and mas.ACCOUNTING_UNIT_ID    ="+cmbAcc_UnitCode+
//                				" AND mas.ACCOUNTING_FOR_OFFICE_ID ="+cmbOffice_code+ " AND mas.CASHBOOK_YEAR= "+txtCB_Year+ 
//                				" AND mas.CASHBOOK_MONTH = "+txtCB_Month+ " and mas.MODE_OF_CREATION='A' AND TRN.ACCOUNT_HEAD_CODE in (391003)");
//        		
//        	}
//        	
//        	else if(debit_type.equalsIgnoreCase("IM"))
//        	{
//        		ps =con.prepareStatement("SELECT MAS.ACCOUNTING_UNIT_ID,MAS.ACCOUNTING_FOR_OFFICE_ID, TRN.PAYABLE_VOUCHER_NO,"
//                		+ " to_char(TRN.PAYABLE_VOUCHER_DATE,'DD/MM/YYYY') as pay_voudate,trim(to_char(TRN.AMOUNT,'99999999999999.99')) as  AMOUNT ,TRN.CHEQUE_DD_DATE ,TRN.CHEQUE_DD_NO, TRN.ACCOUNT_HEAD_CODE,"
//                		+ " trn.CR_DR_INDICATOR FROM FAS_PAYMENT_MASTER mas,FAS_PAYMENT_TRANSACTION TRN"
//                		+ " WHERE MAS.ACCOUNTING_UNIT_ID    =TRN.ACCOUNTING_UNIT_ID "
//                		+ " AND MAS.ACCOUNTING_FOR_OFFICE_ID=TRN.ACCOUNTING_FOR_OFFICE_ID "
//                		+ " AND MAS.VOUCHER_NO              =TRN.VOUCHER_NO "
//                		+ " AND MAS.CASHBOOK_YEAR           =TRN.CASHBOOK_YEAR "
//                		+ " AND MAS.CASHBOOK_MONTH          =TRN.CASHBOOK_MONTH "
//                		+ " and mas.ACCOUNTING_UNIT_ID    ="+cmbAcc_UnitCode+
//                				" AND mas.ACCOUNTING_FOR_OFFICE_ID ="+cmbOffice_code+ " AND mas.CASHBOOK_YEAR= "+txtCB_Year+ 
//                				" AND mas.CASHBOOK_MONTH = "+txtCB_Month+ " and mas.MODE_OF_CREATION='A'  AND TRN.ACCOUNT_HEAD_CODE in (391303) ");	
//        		
//        		
//        		System.out.println("SELECT MAS.ACCOUNTING_UNIT_ID,MAS.ACCOUNTING_FOR_OFFICE_ID, TRN.PAYABLE_VOUCHER_NO,"
//                		+ " to_char(TRN.PAYABLE_VOUCHER_DATE,'DD/MM/YYYY') as pay_voudate,trim(to_char(TRN.AMOUNT,'99999999999999.99')) as  AMOUNT,TRN.CHEQUE_DD_DATE ,TRN.CHEQUE_DD_NO, TRN.ACCOUNT_HEAD_CODE,"
//                		+ " trn.CR_DR_INDICATOR FROM FAS_PAYMENT_MASTER mas,FAS_PAYMENT_TRANSACTION TRN"
//                		+ " WHERE MAS.ACCOUNTING_UNIT_ID    =TRN.ACCOUNTING_UNIT_ID "
//                		+ " AND MAS.ACCOUNTING_FOR_OFFICE_ID=TRN.ACCOUNTING_FOR_OFFICE_ID "
//                		+ " AND MAS.VOUCHER_NO              =TRN.VOUCHER_NO "
//                		+ " AND MAS.CASHBOOK_YEAR           =TRN.CASHBOOK_YEAR "
//                		+ " AND MAS.CASHBOOK_MONTH          =TRN.CASHBOOK_MONTH "
//                		+ " and mas.ACCOUNTING_UNIT_ID    ="+cmbAcc_UnitCode+
//                				" AND mas.ACCOUNTING_FOR_OFFICE_ID ="+cmbOffice_code+ " AND mas.CASHBOOK_YEAR= "+txtCB_Year+ 
//                				" AND mas.CASHBOOK_MONTH = "+txtCB_Month+ " and mas.MODE_OF_CREATION='A' AND TRN.ACCOUNT_HEAD_CODE in (391303)");
//        		
//        	}
        	
        	ps =con.prepareStatement("SELECT MAS.ACCOUNTING_UNIT_ID," +
        			  "MAS.ACCOUNTING_FOR_OFFICE_ID," +
        			  "TRN.PAYABLE_VOUCHER_NO," +
        			  "TO_CHAR(TRN.PAYABLE_VOUCHER_DATE,'DD/MM/YYYY') AS pay_voudate," +
        			  "trim(TO_CHAR(TRN.AMOUNT,'99999999999999.99'))  AS AMOUNT," +
        			  "TRN.CHEQUE_DD_DATE ," +
        			  "TRN.CHEQUE_DD_NO," +
        			  "TRN.ACCOUNT_HEAD_CODE," +
        			  "trn.CR_DR_INDICATOR" +
        			" FROM FAS_PAYMENT_MASTER mas," +
        			 " FAS_PAYMENT_TRANSACTION TRN" +
        			" WHERE MAS.ACCOUNTING_UNIT_ID     =TRN.ACCOUNTING_UNIT_ID" +
        			" AND MAS.ACCOUNTING_FOR_OFFICE_ID =TRN.ACCOUNTING_FOR_OFFICE_ID" +
        			" AND MAS.VOUCHER_NO               =TRN.VOUCHER_NO" +
        			" AND MAS.CASHBOOK_YEAR            =TRN.CASHBOOK_YEAR" +
        			" AND MAS.CASHBOOK_MONTH           =TRN.CASHBOOK_MONTH" +
        			" AND mas.ACCOUNTING_UNIT_ID       =" +cmbAcc_UnitCode+
        			" AND mas.ACCOUNTING_FOR_OFFICE_ID =" +cmbOffice_code+
        			" AND mas.CASHBOOK_YEAR            =" +txtCB_Year+
        			" AND mas.CASHBOOK_MONTH           =" +txtCB_Month+
        			" AND MAS.MODE_OF_CREATION         ='A'" +
        			" AND TRN.ACCOUNT_HEAD_CODE       IN(select AC_HEAD_CODE from HRM_GPF_DEBIT_TYPES where "+
        		    " BILL_MAJOR_TYPE_CODE= "+majorcc+
			       " and BILL_MINOR_TYPE_CODE="+minorcc+" and BILL_SUB_TYPE_CODE="+subtype+") order by trn.PAYABLE_VOUCHER_NO");
        	
        	System.out.println("SELECT MAS.ACCOUNTING_UNIT_ID," +
      			  "MAS.ACCOUNTING_FOR_OFFICE_ID," +
      			  "TRN.PAYABLE_VOUCHER_NO," +
      			  "TO_CHAR(TRN.PAYABLE_VOUCHER_DATE,'DD/MM/YYYY') AS pay_voudate," +
      			  "trim(TO_CHAR(TRN.AMOUNT,'99999999999999.99'))  AS AMOUNT," +
      			  "TRN.CHEQUE_DD_DATE ," +
      			  "TRN.CHEQUE_DD_NO," +
      			  "TRN.ACCOUNT_HEAD_CODE," +
      			  "trn.CR_DR_INDICATOR" +
      			" FROM FAS_PAYMENT_MASTER mas," +
      			 " FAS_PAYMENT_TRANSACTION TRN" +
      			" WHERE MAS.ACCOUNTING_UNIT_ID     =TRN.ACCOUNTING_UNIT_ID" +
      			" AND MAS.ACCOUNTING_FOR_OFFICE_ID =TRN.ACCOUNTING_FOR_OFFICE_ID" +
      			" AND MAS.VOUCHER_NO               =TRN.VOUCHER_NO" +
      			" AND MAS.CASHBOOK_YEAR            =TRN.CASHBOOK_YEAR" +
      			" AND MAS.CASHBOOK_MONTH           =TRN.CASHBOOK_MONTH" +
      			" AND mas.ACCOUNTING_UNIT_ID       =" +cmbAcc_UnitCode+
      			" AND mas.ACCOUNTING_FOR_OFFICE_ID =" +cmbOffice_code+
      			" AND mas.CASHBOOK_YEAR            =" +txtCB_Year+
      			" AND mas.CASHBOOK_MONTH           =" +txtCB_Month+
      			" AND MAS.MODE_OF_CREATION         ='A'" +
      			" AND TRN.ACCOUNT_HEAD_CODE       IN(select AC_HEAD_CODE from HRM_GPF_DEBIT_TYPES where "+
        		    " BILL_MAJOR_TYPE_CODE= "+majorcc+
			       " and BILL_MINOR_TYPE_CODE="+minorcc+" and BILL_SUB_TYPE_CODE="+subtype+") order by trn.PAYABLE_VOUCHER_NO");
        	
        	
        	
       ResultSet rs1 = ps.executeQuery();
       
       
       
       
       while(rs1.next())
       {
    	  
    	   
    	   
    	   
    	   
    	   System.out.println("cmbAcc_UnitCode==>"+cmbAcc_UnitCode); 
  		 System.out.println("cmbOffice_code==>"+cmbOffice_code); 
  		 System.out.println("txtCB_Year==>"+txtCB_Year); 
  		 System.out.println("txtCB_Month==>"+txtCB_Month); 
  		 System.out.println("debit_type==>"+debit_type); 
  		 System.out.println("empid==>"+txtEmpID_mas);
  		 System.out.println("empName==>"+cmbMas_SL_Code);
  		 System.out.println("PAYABLE_VOUCHER_NO==>"+rs1.getInt("PAYABLE_VOUCHER_NO"));
  		 System.out.println("pay_voudate==>"+rs1.getString("pay_voudate"));
  		 System.out.println("AMOUNT==>"+rs1.getString("AMOUNT"));
  		 System.out.println("CHEQUE_DD_NO==>"+rs1.getInt("CHEQUE_DD_NO"));
  		 System.out.println("CHEQUE_DD_DATE==>"+rs1.getDate("CHEQUE_DD_DATE"));
  		 System.out.println("ACCOUNT_HEAD_CODE==>"+rs1.getInt("ACCOUNT_HEAD_CODE"));
  		 System.out.println("CR_DR_INDICATOR==>"+rs1.getString("CR_DR_INDICATOR"));
    	   
    	  try
    	  {
    		 
    		
    		 
    		  String sql =
                "insert into FAS_GPF_DEBIT_HEAD(ACCOUNTING_UNIT_ID, " +
                "ACCOUNTING_FOR_OFFICE_ID ,CASHBOOK_YEAR, CASHBOOK_MONTH, DEBIT_SCHEDULE_TYPE,EMPLOYEE_CODE,"
                + " EMPLOYEE_NAME,GPF_NUMBER,PAYMENT_VR_NO,PAYMENT_VR_DATE,PAYMENT_AMOUNT,CHEQUE_NO,CHEQUE_DATE,"
                + " HEAD_OF_ACCOUNT,DR_CR_INDICATOR,CREATED_BY_USER_ID,UPDATED_DATE ) " +
                "values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
        ps2 = con.prepareStatement(sql);
        
        
        ps2.setInt(1, cmbAcc_UnitCode);
        ps2.setInt(2, cmbOffice_code);
        ps2.setInt(3, txtCB_Year);
        ps2.setInt(4, txtCB_Month);
        ps2.setString(5, debit_type);
        ps2.setInt(6, txtEmpID_mas);
        ps2.setString(7,cmbMas_SL_Code);
       
        ps2.setInt(8, txtEmpID_mas);
              
        ps2.setInt(9, rs1.getInt("PAYABLE_VOUCHER_NO"));
               
        
        ps2.setString(10, rs1.getString("pay_voudate"));
         
        
        ps2.setString(11, rs1.getString("AMOUNT"));
        
        
        ps2.setInt(12, rs1.getInt("CHEQUE_DD_NO"));
        
        
        ps2.setDate(13, rs1.getDate("CHEQUE_DD_DATE"));
        
        
        ps2.setInt(14, rs1.getInt("ACCOUNT_HEAD_CODE"));
         
        
        ps2.setString(15, rs1.getString("CR_DR_INDICATOR"));
        
        
        
        ps2.setString(16, update_user);
        ps2.setTimestamp(17, ts);
        k=ps2.executeUpdate();
       
        ps2.close();
        
        System.out.println("K value===>"+k);
        
       }
    	  catch(Exception e)
    	  {
              System.out.println("Exception occur due to " + e);
  
    	  }
    	  
    	  
    	  
    	  
    	  finally {
              System.out.println("done here");
              try {
                  con.setAutoCommit(true);
              } catch (SQLException sqle) {
                  System.out.println("Excep" + sqle);
              }
          }
       }
       
       if(k>0){
//    	   con.commit();
    	   xml = xml + "<flag>success</flag>";
			con.setAutoCommit(true);
			con.commit();
       }
       else
       {
    	   try {
               con.rollback();
               xml = xml + "<flag>fail</flag>";
           } catch (SQLException sqle) {
               System.out.println("Excep" + sqle);
           }
           
			
       }
        
        }
        catch(Exception e)
        {
        	try {
                con.rollback();
            } catch (SQLException sqle) {
                System.out.println("exception in rollback " + sqle);
            }
        	System.out.println("Exception is===>"+e);
        	
        	
        }
        
        xml = xml + "</response>";
		out.println(xml);
		System.out.println(xml);
        }
        
        if(strCommand.equalsIgnoreCase("check"))
        {           
                  int majorType=Integer.parseInt(request.getParameter("majorType"));    
                  System.out.println("majorType"+majorType);                              
                    xml="<response><command>Disp</command>";
                    try 
                        {
                            System.out.println("inside try");
                            ps = con.prepareStatement("select BILL_MINOR_TYPE_CODE,BILL_MINOR_TYPE_DESC,SUB_TYPE_APPLICABLE from FAS_BILL_MINOR_TYPES_MST where BILL_MAJOR_TYPE_CODE=? AND BILL_MINOR_TYPE_CODE=1");
                            ps.setInt(1,majorType);
                            ResultSet result1 = ps.executeQuery(); 
                            System.out.println("result is"+result1);
                            while(result1.next())      
                            {
                                xml=xml+"<mincode>"+result1.getString("BILL_MINOR_TYPE_CODE")+"</mincode>";
                               xml=xml+"<mindesc>"+result1.getString("BILL_MINOR_TYPE_DESC")+"</mindesc>";
                               xml=xml+"<subtype>"+result1.getString("SUB_TYPE_APPLICABLE")+"</subtype>";
                                xml=xml+"<flag>success</flag>";
                            }xml=xml+"<flag>failure</flag>";
                          }
                    catch(Exception e1)
                        {
                            System.out.println("Exception in idcheck ===> "+e1);
                            xml=xml+"<flag>failure</flag>";
                        }  
                        System.out.println("response end............."+xml);
                        xml = xml + "</response>";
                		out.println(xml);
                		System.out.println(xml);
        }
        else if(strCommand.equalsIgnoreCase("subType")) 
	    {
		System.out.println(":::::::::::::sub type::::::::::");
	        xml="<response><command>subb</command>"; 
	        try 
	                {
	        	 		int major=Integer.parseInt(request.getParameter("major2"));
	                    int sub=Integer.parseInt(request.getParameter("sub2"));	
	                    System.out.println("sub"+sub);
	                    ps = con.prepareStatement("select BILL_SUB_TYPE_CODE,BILL_SUB_TYPE_DESC from FAS_BILL_SUB_TYPES where BILL_MAJOR_TYPE_CODE="+major+" and BILL_MINOR_TYPE_CODE="+sub);
	                    System.out.println("ps"+ps);
	                   ResultSet result = ps.executeQuery();
	                    xml=xml+"<flag>success</flag>";
	                    while (result.next()) 
	                        {
	                    	
	                    	    System.out.println("subdesc"+result.getString("BILL_SUB_TYPE_DESC"));
	                            xml=xml+"<subcode>"+result.getString("BILL_SUB_TYPE_CODE")+"</subcode>";
	                            xml=xml+"<subdesc>"+result.getString("BILL_SUB_TYPE_DESC")+"</subdesc>";
	                        }   
	                }
	          catch(Exception e) 
	                {
	                        System.out.println("Exception in minor ===> "+e);   
	                        xml=xml+"<flag>failure</flag>";  
	                }
	        xml = xml + "</response>";
    		out.println(xml);
    		System.out.println(xml);
	    }
        
	
	}
	
	
	private void sendMessage(HttpServletResponse response, String msg,
            String bType) {
		try {
				String url =
							"org/Library/jsps/MessengerOkBack.jsp?message=" + msg +
							"&button=" + bType;
							response.sendRedirect(url);
			} catch (IOException e) {
			}
	}

}
