package Servlets.FAS.FAS1.BillVerification.servlets;


import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ResourceBundle;




import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;





import Servlets.Security.classes.UserProfile;

/**
 * Servlet implementation class DrawingOfficerDetails
 */
public class Pre_AuditDetails extends HttpServlet {
	private static final String CONTENT_TYPE = "text/xml; charset=windows-1252";
	private static final long serialVersionUID = 1L;
	Connection con = null;
	PreparedStatement ps = null;
	ResultSet rs = null;
	PreparedStatement ps1 = null;
	ResultSet rs1 = null;
	PreparedStatement ps2 = null;
	ResultSet rs2 = null;
       
     public Pre_AuditDetails() {
        super();
        // TODO Auto-generated constructor stub
    }

	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doPost(request,response);
	}

		/* (non-Javadoc)
		 * @see javax.servlet.http.HttpServlet#doPost(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse)
		 */
		protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	// TODO Auto-generated method stub
		//	PrintWriter out = response.getWriter();
			  PrintWriter out = response.getWriter();
		      response.setHeader("cache-control","no-cache");
		      String CONTENT_TYPE = "text/xml; charset=windows-1252";
		      response.setContentType(CONTENT_TYPE);
			
	   HttpSession session=request.getSession(true);
	    String cmd=request.getParameter("command");
	    String unit_id=request.getParameter("unit_id");
	    String office_id=request.getParameter("office_id");
	    String billno="";
	    
	    System.out.println(cmd);
	    System.out.println(unit_id);
	    System.out.println(office_id);
	  
	   
	   
        String xml="";
        try {
			ResourceBundle rsb = ResourceBundle
					.getBundle("Servlets.Security.servlets.Config");
			String ConnectionString = "";

			String strDriver = rsb.getString("Config.DATA_BASE_DRIVER");
			String strdsn = rsb.getString("Config.DSN");
			String strhostname = rsb.getString("Config.HOST_NAME");
			String strportno = rsb.getString("Config.PORT_NUMBER");
			String strsid = rsb.getString("Config.SID");
			String strdbusername = rsb.getString("Config.USER_NAME");
			String strdbpassword = rsb.getString("Config.PASSWORD");

			ConnectionString = strdsn.trim() + "://" + strhostname.trim() + ":" + strportno.trim() + "/" +strsid.trim() ;    // Postgres DB  Connection

			Class.forName(strDriver.trim());
			con = DriverManager.getConnection(ConnectionString,
					strdbusername.trim(), strdbpassword.trim());
			
		} catch (Exception e) {
			System.out.println("Exception in openeing connection:" + e);
		}
		
		if(cmd.equalsIgnoreCase("getEmpName"))
		{
			   String empid=request.getParameter("empid");
			   String s1="In Valid EmployeeCode";
			 int eid=  Integer.parseInt(empid);
			xml="<response><command>loadempdetails</command>";
			try
			{      
				ps=con.prepareStatement("select * from HRM_MST_EMPLOYEES where EMPLOYEE_ID='"+eid+"'");
				rs=ps.executeQuery();
			
				if(rs.next())
				{
				PreparedStatement pres=con.prepareStatement("select * from HRM_EMP_CURRENT_POSTING where " +
						"OFFICE_ID=5000 and EMPLOYEE_ID="+rs.getString("EMPLOYEE_ID"));
					ResultSet rset=pres.executeQuery();
					if(rset.next())
					{
					xml=xml+"<id>"+rs.getString("EMPLOYEE_NAME")+"</id>";
					xml=xml+"<flag>success</flag>";
					}
					else
					{
						xml=xml+"<flag>nodata</flag>";	
					}
				}
				else
				{
					xml=xml+"<flag>nodata</flag>";
					
				}
				
				
				
			}
			catch(Exception e)
			{
				xml = xml + "<flag>failure</flag>";
				System.out.println(e);
			}
			xml = xml + "</response>";
			System.out.println(xml);	
			
		}
		
	
		if(cmd.equalsIgnoreCase("loadmajortype"))
		{
			 
			
			
			xml="<response><command>loadmajortype</command>";
			try
			{      
				ps=con.prepareStatement("select * from FAS_BILL_MAJOR_TYPES where STATUS='L' order by BILL_MAJOR_TYPE_CODE");
				rs=ps.executeQuery();
				xml = xml + "<flag>success</flag>";
				while(rs.next())
				{
					xml=xml+"<majorid>"+rs.getInt(1)+"</majorid>";
					xml=xml+"<majorname>"+rs.getString(2)+"</majorname>";
					
				}
				
				
				
				
			}
			catch(Exception e)
			{
				xml = xml + "<flag>failure</flag>";
				System.out.println(e);
			}
			xml = xml + "</response>";
			System.out.println(xml);
			
			
		}
		
		else if(cmd.equalsIgnoreCase("loadgrid"))
		{
			String finyear="";
			String cmbAcc_UnitCode=request.getParameter("cmbAcc_UnitCode");
			int accno=Integer.parseInt(cmbAcc_UnitCode);
			System.out.println("values..............................."+accno);
			
			String cmbOffice_code=request.getParameter("cmbOffice_code");
			int officecode=Integer.parseInt(cmbOffice_code);
			System.out.println("values..............................."+officecode);
			
			int CashBook_Year=Integer.parseInt(request.getParameter("cboCashBook_Year"));
			int CashBook_Month=Integer.parseInt(request.getParameter("cboCashBook_Month"));
			if(CashBook_Month>3)
			{
				int fyear=CashBook_Year+1;
				finyear=CashBook_Year+"-"+fyear;//2013-2014
			}
			if(CashBook_Month<4)
			{
				int pastyr=CashBook_Year-1;
				finyear=pastyr+"-"+CashBook_Year;
			}
			//System.out.println(":end::"+finyear.substring(7, 9));
			//System.out.println(":start::"+finyear.substring(0, 5));
			String valid_to=(finyear.substring(0, 5)).concat(finyear.substring(7, 9));
			//System.out.println("valid_to::"+valid_to);
			
			xml="<response><command>loadgrid</command>";
			try
			{      
				ps=con.prepareStatement("select * from FAS_PRE_AUDIT_CHKLST_MST where ACCOUNTING_UNIT_ID='"+cmbAcc_UnitCode+"'and " +
				"ACCOUNTING_FOR_OFFICE_ID='"+officecode+"'and FINANCIAL_YEAR='"+valid_to+"' and STATUS='L'");
				rs=ps.executeQuery();
				
				
				if(rs.next())
				{
					xml=xml+"<flag>success</flag>";
					xml = xml + "<des>"+rs.getString("CHECK_LIST_DESC")+"</des>";
					xml = xml + "<descode>"+rs.getString("CHECK_LIST_CODE")+"</descode>";
					xml = xml + "<mandate>"+rs.getString("MANDATE")+"</mandate>";
					xml = xml + "<app>"+rs.getString("NOT_APPLY")+"</app>";
				}
				else 
				{
					xml = xml + "<flag>NoData</flag>";
				}
				
			}
			catch(Exception e)
			{
				xml = xml + "<flag>failure</flag>";
				System.out.println(e);
			}
			xml = xml + "</response>";
			System.out.println(xml);	
			
		}
		else if(cmd.equalsIgnoreCase("loadbillno"))
		{
			int goins=0;
			xml="<response><command>loadbillno</command>";
			String cmbAcc_UnitCode=request.getParameter("cmbAcc_UnitCode");
			int accno=Integer.parseInt(cmbAcc_UnitCode);
			System.out.println("values..............................."+accno);
			
			String cmbOffice_code=request.getParameter("cmbOffice_code");
			int officecode=Integer.parseInt(cmbOffice_code);
			System.out.println("values..............................."+officecode);
			
			
			String cboCashBook_Year=request.getParameter("cboCashBook_Year");
			int CashBook_Year=Integer.parseInt(cboCashBook_Year);
			int CashBook_month=Integer.parseInt(request.getParameter("cboCashBook_Month"));
			String sub_q = "",sub_main="";
			try
			{
				int ct=0;
				ps=con.prepareStatement("select BILL_NO,BILL_MAJOR_TYPE_CODE from FAS_MTC70_REGISTER where ACCOUNTING_UNIT_ID='"+accno+"' and " +
						" ACCOUNTING_FOR_OFFICE_ID='"+cmbOffice_code+"'and CASHBOOK_YEAR='"+CashBook_Year+"'and CASHBOOK_MONTH=" +CashBook_month+
						" and REGISTER_UPDATED_DATE is not null and status = 'L' and PRE_AUDIT_SENT_DATE is not null and " +
						" CHECKED_AND_PASSED_DATE is not null");
				rs=ps.executeQuery();
				while(rs.next())
				{
				
					/*if(CashBook_Year>2014 && CashBook_month>3)
					{
						 sub_q = " FAS_BILL_REGISTER_MASTERNEW "; 
						 sub_main=" Fas_Bill_Register_MasterNEW M, "+
						" 	  Fas_Bill_Register_Transactionw T ";
					}else{
						sub_q = " FAS_BILL_REGISTER_MASTER "; 
						 sub_main=" Fas_Bill_Register_Master M, "+
									" 	  Fas_Bill_Register_Transaction T ";
					}*/
					if (CashBook_Year > 2014) {
						if (CashBook_Year == 2015 && CashBook_month <= 3) {
							sub_q = " FAS_BILL_REGISTER_MASTER "; 
							 sub_main=" Fas_Bill_Register_Master M, "+
										" 	  Fas_Bill_Register_Transaction T ";
						}else{
							sub_q = " FAS_BILL_REGISTER_MASTERNEW "; 
							 sub_main=" Fas_Bill_Register_MasterNEW M, "+
							" 	  Fas_Bill_Register_Transactionw T ";
						}
					}else{
						sub_q = " FAS_BILL_REGISTER_MASTER "; 
						 sub_main=" Fas_Bill_Register_Master M, "+
									" 	  Fas_Bill_Register_Transaction T ";
					}
					 int bno=rs.getInt("BILL_NO");
					 int major=rs.getInt("BILL_MAJOR_TYPE_CODE");
					 PreparedStatement prst=con.prepareStatement("SELECT BILL_NO,b.SANCTION_PROC_NO, "+
							 " (select m.SANCTION_PROC_NO as id from HRM_SANCTIONS_BILLS_LINK_MST m " +
							 "where m.HRMS_SANCTION_ID=b.SANCTION_PROC_NO::numeric )as sancid "+
							 " FROM "+sub_q+" b "+
							 " WHERE ACCOUNTING_UNIT_ID     = "+accno+
							 " AND ACCOUNTING_UNIT_OFFICE_ID = "+cmbOffice_code+
							 " AND CASHBOOK_YEAR            = "+CashBook_Year+
							 " AND CASHBOOK_MONTH           = "+CashBook_month+
							 " and BILL_NO= "+bno+
							 " and STATUS='L' and PRE_AUDIT_BY is null  and bill_type <> 'WOSP' ");
					ResultSet resss=prst.executeQuery();
					while(resss.next())
					{
						goins++;
						System.out.println("be44");
						String sanctionid=resss.getString("sancid");
						if(sanctionid==null)
						{
							System.out.println("here null");
							
							 PreparedStatement prst1=con.prepareStatement("SELECT BILL_NO,b.SANCTION_PROC_NO, "+
									 " (select distinct m.SANCTION_PROC_NO as id from SLS_SANCTIONS_BILLS_LINK_MST1 m " +
									 "where m.HRMS_SANCTION_ID=b.SANCTION_PROC_NO)as sancid "+
									 " FROM "+sub_q+" b "+
									 " WHERE ACCOUNTING_UNIT_ID     = "+accno+
									 " AND ACCOUNTING_UNIT_OFFICE_ID = "+cmbOffice_code+
									 " AND CASHBOOK_YEAR            = "+CashBook_Year+
									 " AND CASHBOOK_MONTH           = "+CashBook_month+
									 " and BILL_NO= "+bno+
									 " and STATUS='L' and PRE_AUDIT_BY is null and bill_type <> 'WOSP' ");
							ResultSet resss1=prst1.executeQuery();
							if(resss1.next())
							{
								ct++;	
								xml=xml+"<billno>"+resss1.getInt("BILL_NO")+"</billno>";
								xml=xml+"<major>"+major+"</major>";
								
								xml=xml+"<sancid>"+resss1.getString("sancid")+"</sancid>";
							}
						}
						else
						{
						ct++;	
						xml=xml+"<billno>"+resss.getInt("BILL_NO")+"</billno>";
						xml=xml+"<sancid>"+resss.getString("sancid")+"</sancid>";
						xml=xml+"<major>"+major+"</major>";
						}
					}
					if(major!=2 && bno!=0)
					{
						String ss="SELECT BILL_NO "+
							" FROM FAS_BILL_REGISTERNEW b WHERE ACCOUNTING_UNIT_ID      =  "+accno+
							" AND ACCOUNTING_UNIT_OFFICE_ID = "+cmbOffice_code+" AND CASHBOOK_YEAR             =  "+CashBook_Year+
							" AND CASHBOOK_MONTH            = "+CashBook_month+" AND BILL_NO                   =  "+bno+
							" AND STATUS                    ='L' AND PRE_AUDIT_BY             IS NULL";
						PreparedStatement billnew=con.prepareStatement(ss);
						ResultSet rnew=billnew.executeQuery();
						while(rnew.next())
						{
						ct++;
						xml=xml+"<billno>"+bno+"</billno>";
						xml=xml+"<sancid>"+0+"</sancid>";
						xml=xml+"<major>"+major+"</major>";
						}
						
					}else {
						System.out.println("TEST ELSE PART .. ");
					/*	PreparedStatement prst2=con.prepareStatement("SELECT BILL_NO,b.SANCTION_PROC_NO as sancid "+
								// " (select distinct m.SANCTION_PROC_NO as id from SLS_SANCTIONS_BILLS_LINK_MST1 m " +
								 //"where m.HRMS_SANCTION_ID=b.SANCTION_PROC_NO)as sancid "+
								 " FROM "+sub_q+" b "+
								 " WHERE ACCOUNTING_UNIT_ID     = "+accno+
								 " AND ACCOUNTING_UNIT_OFFICE_ID = "+cmbOffice_code+
								 " AND CASHBOOK_YEAR            = "+CashBook_Year+
								 " AND CASHBOOK_MONTH           = "+CashBook_month+
								 " and BILL_NO= "+bno+
								 " and STATUS='L' and PRE_AUDIT_BY is null and bill_type = 'WOSP' ");*/
						PreparedStatement prst2=con.prepareStatement("SELECT b.* "+
								 " FROM "+
								 "  (SELECT T.Bill_No, "+
								 "    M.Bill_Date, "+
								 "    t.Accounting_Unit_Id , "+
								 "   T.Accounting_For_Office_Id, "+
								 "    T.Payment_Unit, "+
								 "    T.Payment_Office "+
								 "  FROM Fas_Memo_Of_Payment_Mst M "+
								 "  INNER JOIN Fas_Memo_Of_Payment_Trn T "+
								 "   ON M.Accounting_Unit_Id       =T.Accounting_Unit_Id "+
								 "   AND M.Accounting_For_Office_Id=T.Accounting_For_Office_Id "+
								 "   AND M.Cashbook_Year           =T.Cashbook_Year "+
								 "   AND M.Cashbook_Month          =T.Cashbook_Month "+
								 "   AND M.Bill_No                 =T.Bill_No "+
								 "   AND M.Status                  ='L' "+
								 "   AND T.Payment_Unit            = "+accno+
								 "  AND T.Payment_Office          = "+cmbOffice_code+
								 "  AND T.Cashbook_Year           = "+CashBook_Year+
								 " AND T.Cashbook_Month          =  "+CashBook_month+
								 "   AND M.Bill_No                 = "+bno+
								 "  )A "+
								 " INNER JOIN "+
								 "   (SELECT Bill_No, "+
								 "     B.Sanction_Proc_No AS Sancid , "+
								 "     Accounting_Unit_Id, "+
								 "    Accounting_Unit_Office_Id "+
   // " (select distinct m.SANCTION_PROC_NO as id from SLS_SANCTIONS_BILLS_LINK_MST1 m " +
      //"where m.HRMS_SANCTION_ID=b.SANCTION_PROC_NO)as sancid
 	 "  FROM "+sub_q+" B "+
	 "  WHERE "+
    //              Accounting_Unit_Id     = 3
  //-        And Accounting_Unit_Office_Id =5000         AND
	 "    CASHBOOK_YEAR     = "+CashBook_Year+
	 "  AND Cashbook_Month  =  "+CashBook_month+
	 "   AND BILL_NO         = "+bno+
	 "  AND STATUS         ='L' "+
	 "   AND PRE_AUDIT_BY  IS NULL "+
	 "   AND bill_type      = 'WOSP' "+
	 "   )B "+
	 " ON b.Accounting_Unit_Id        =A.Accounting_Unit_Id "+
	 " AND B.Accounting_Unit_Office_Id=A.Accounting_For_Office_Id "+
	 " AND b.Bill_No                  =a.Bill_No ");
						
						ResultSet resss2=prst2.executeQuery();
						if(resss2.next())
						{
							ct++;	
							xml=xml+"<billno>"+resss2.getInt("BILL_NO")+"</billno>";
							xml=xml+"<major>"+major+"</major>";
							
						
							xml=xml+"<sancid>"+resss2.getString("sancid")+"</sancid>";
						}
						
						
					}
					
				}
				if(ct>0)
				{
					xml=xml+"<flag>success</flag>";
				}
				else
				{
					xml = xml + "<flag>Nodata</flag>";
				}
			}
			catch(Exception e)
			{
				xml = xml + "<flag>failure</flag>";
				System.out.println(e);
			}
			xml = xml + "</response>";
			System.out.println(xml);	
			
			
		}
		
		else if(cmd.equalsIgnoreCase("getbilldatails"))
		{
			
			
			xml="<response><command>getbilldatails</command>";
			String cmbAcc_UnitCode=request.getParameter("cmbAcc_UnitCode");
			int accno=Integer.parseInt(cmbAcc_UnitCode);
			System.out.println("values..............................."+accno);
			String sub_q = "",sub_main="";
			String cmbOffice_code=request.getParameter("cmbOffice_code");
			int officecode=Integer.parseInt(cmbOffice_code);
			System.out.println("values..............................."+officecode);
			
			int year_from=Integer.parseInt(request.getParameter("cboCashBook_Year"));
			int month_from=Integer.parseInt(request.getParameter("cboCashBook_Month"));
			
			int majorhidden=Integer.parseInt(request.getParameter("majorhidden"));
			String cmbBillNO=request.getParameter("cmbBillNO");
			int billno1=Integer.parseInt(cmbBillNO);
			System.out.println("values..............................."+billno1);
		/*	if(year_from>2014 && month_from>3)
			{
				 sub_q = " FAS_BILL_REGISTER_MASTERNEW "; 
				 sub_main=" Fas_Bill_Register_MasterNEW M, "+
				" 	  Fas_Bill_Register_Transactionw T ";
			}else{
				sub_q = " FAS_BILL_REGISTER_MASTER "; 
				 sub_main=" Fas_Bill_Register_Master M, "+
							" 	  Fas_Bill_Register_Transaction T ";
			}*/
			if (year_from > 2014) {
				if (year_from == 2015 && month_from <= 3) {
					sub_q = " FAS_BILL_REGISTER_MASTER "; 
					 sub_main=" Fas_Bill_Register_Master M, "+
								" 	  Fas_Bill_Register_Transaction T ";
				}else{
					 sub_q = " FAS_BILL_REGISTER_MASTERNEW "; 
					 sub_main=" Fas_Bill_Register_MasterNEW M, "+
					" 	  Fas_Bill_Register_Transactionw T ";	
				}
			}else{
				sub_q = " FAS_BILL_REGISTER_MASTER "; 
				 sub_main=" Fas_Bill_Register_Master M, "+
							" 	  Fas_Bill_Register_Transaction T ";
			}
			try
			{
				String ss="";
				if(majorhidden==2)
				{
				/*ss="select b.*,(SELECT M.BILL_MAJOR_TYPE_DESC FROM " +
						" FAS_BILL_MAJOR_TYPES M WHERE M.BILL_MAJOR_TYPE_CODE=B.BILL_MAJOR_TYPE)as major_desc," +
						" (select BILL_MINOR_TYPE_DESC  from FAS_BILL_MINOR_TYPES_MST N "+
						" 		  where N.BILL_MAJOR_TYPE_CODE=B.BILL_MAJOR_TYPE and  "+
						" 	  N.BILL_MINOR_TYPE_CODE=B.BILL_MINOR_TYPE_CODE and status='L')as minordesc, "+
						" 	  (select BILL_SUB_TYPE_DESC from FAS_BILL_SUB_TYPES O "+
						" 	  where O.BILL_MAJOR_TYPE_CODE=B.BILL_MAJOR_TYPE and O.BILL_MINOR_TYPE_CODE=B.BILL_MINOR_TYPE_CODE and "+
						" 	  O.BILL_SUB_TYPE_CODE=b.bill_sub_type_code and status='L')as subdesc "+
						" from " +
					sub_q	+"  b where ACCOUNTING_UNIT_ID='"+cmbAcc_UnitCode+"' and ACCOUNTING_UNIT_OFFICE_ID='"
						+cmbOffice_code+"'and CASHBOOK_YEAR='"+year_from+"' and CASHBOOK_MONTH="+month_from+" and BILL_NO='"+billno1+"' and STATUS='L'";*/
					
					ss="SELECT b.* " +
					" FROM " +
					"  (SELECT T.Bill_No, " +
					"    M.Bill_Date, " +
					"    t.Accounting_Unit_Id , " +
					"    T.Accounting_For_Office_Id, " +
					"    T.Payment_Unit, " +
					"    T.Payment_Office " +
					"  FROM Fas_Memo_Of_Payment_Mst M " +
					"  INNER JOIN Fas_Memo_Of_Payment_Trn T " +
					"  ON M.Accounting_Unit_Id       =T.Accounting_Unit_Id " +
					"  AND M.Accounting_For_Office_Id=T.Accounting_For_Office_Id " +
					"  AND M.Cashbook_Year           =T.Cashbook_Year " +
					"  AND M.Cashbook_Month          =T.Cashbook_Month " +
					"  AND M.Bill_No                 =T.Bill_No " +
					"  AND M.Status                  ='L' " +
					"  AND T.Payment_Unit            ="+cmbAcc_UnitCode+
					"  AND T.Payment_Office          ="+cmbOffice_code+
					"  AND T.Cashbook_Year           ="+year_from+
					"  AND T.Cashbook_Month          = "+month_from+
					"  AND M.Bill_No                 ="+billno1+
					"  )A " +
					" INNER JOIN " +
					"  (SELECT b.*, " +
					"    (SELECT M.BILL_MAJOR_TYPE_DESC " +
					"    FROM FAS_BILL_MAJOR_TYPES M " +
					"    WHERE M.BILL_MAJOR_TYPE_CODE=B.BILL_MAJOR_TYPE " +
					"    )AS major_desc, " +
					"    (SELECT BILL_MINOR_TYPE_DESC " +
					"    FROM FAS_BILL_MINOR_TYPES_MST N " +
					"    WHERE N.BILL_MAJOR_TYPE_CODE=B.BILL_MAJOR_TYPE " +
					"    AND N.BILL_MINOR_TYPE_CODE  =B.BILL_MINOR_TYPE_CODE " +
					"    AND status                  ='L' " +
					"    )AS minordesc, " +
					"    (SELECT BILL_SUB_TYPE_DESC " +
					"    FROM FAS_BILL_SUB_TYPES O " +
					"    WHERE O.BILL_MAJOR_TYPE_CODE=B.BILL_MAJOR_TYPE " +
					"    AND O.BILL_MINOR_TYPE_CODE  =B.BILL_MINOR_TYPE_CODE " +
					"    AND O.BILL_SUB_TYPE_CODE    =b.bill_sub_type_code " +
					"    AND status                  ='L' " +
					"    )AS subdesc " +
					"  FROM "+sub_q	+"   b " +
					"  WHERE CASHBOOK_YEAR ="+year_from+
					"  AND CASHBOOK_MONTH  ="+month_from+
					"  AND BILL_NO         ="+billno1+
					"  AND STATUS          ='L' " +
					"  )B " +
					" ON b.Accounting_Unit_Id        =A.Accounting_Unit_Id " +
					" AND B.Accounting_Unit_Office_Id=A.Accounting_For_Office_Id " +
					" AND b.Bill_No                  =a.Bill_No" ;
				}
				else
				{
					ss="select b.*,(SELECT M.BILL_MAJOR_TYPE_DESC FROM " +
					" FAS_BILL_MAJOR_TYPES M WHERE M.BILL_MAJOR_TYPE_CODE=B.BILL_MAJOR_TYPE)as major_desc," +
					" (select BILL_MINOR_TYPE_DESC  from FAS_BILL_MINOR_TYPES_MST N "+
					" 		  where N.BILL_MAJOR_TYPE_CODE=B.BILL_MAJOR_TYPE and  "+
					" 	  N.BILL_MINOR_TYPE_CODE=B.BILL_MINOR_TYPE_CODE and status='L')as minordesc, "+
					" 	  (select BILL_SUB_TYPE_DESC from FAS_BILL_SUB_TYPES O "+
					" 	  where O.BILL_MAJOR_TYPE_CODE=B.BILL_MAJOR_TYPE and O.BILL_MINOR_TYPE_CODE=B.BILL_MINOR_TYPE_CODE and "+
					" 	  O.BILL_SUB_TYPE_CODE=b.bill_sub_type_code and status='L')as subdesc "+
					" from " +
					" FAS_BILL_REGISTERNEW b where ACCOUNTING_UNIT_ID='"+cmbAcc_UnitCode+"' and ACCOUNTING_UNIT_OFFICE_ID='"
					+cmbOffice_code+"'and CASHBOOK_YEAR='"+year_from+"' and CASHBOOK_MONTH="+month_from+" and BILL_NO='"+billno1+"' and STATUS='L'";
				}
				System.out.println("ss:::"+ss);
				ps=con.prepareStatement(ss);
				rs=ps.executeQuery();
				
				if(rs.next())
				{
					String billdate[]=rs.getDate("BILL_DATE").toString().split("-");
					String billdate1=billdate[2]+"/"+billdate[1]+"/"+billdate[0];
					
					String SENT_TO_PRE_AUDIT_ON[]=rs.getDate("SENT_TO_PRE_AUDIT_ON").toString().split("-");
					String SENT_TO_PRE_AUDIT_ON_one=SENT_TO_PRE_AUDIT_ON[2]+"/"+SENT_TO_PRE_AUDIT_ON[1]+"/"+SENT_TO_PRE_AUDIT_ON[0];
					
					xml=xml+"<billdate>"+billdate1+"</billdate>";
					xml=xml+"<SENT_TO_PRE_AUDIT_ON>"+SENT_TO_PRE_AUDIT_ON_one+"</SENT_TO_PRE_AUDIT_ON>";
					xml=xml+"<billamt>"+rs.getDouble("TOTAL_BILL_AMOUNT")+"</billamt>";
					xml=xml+"<major_type>"+rs.getInt("BILL_MAJOR_TYPE")+"</major_type>";
					xml=xml+"<major_desc>"+rs.getString("major_desc")+"</major_desc>";
					xml=xml+"<minortype>"+rs.getInt("BILL_MINOR_TYPE_CODE")+"</minortype>";
					xml=xml+"<minordesc>"+rs.getString("minordesc")+"</minordesc>";
					xml=xml+"<subtype>"+rs.getInt("BILL_SUB_TYPE_CODE")+"</subtype>";
					xml=xml+"<subdesc>"+rs.getString("subdesc")+"</subdesc>";
					xml=xml+"<Accounting_Unit_Id>"+rs.getInt("Accounting_Unit_Id")+"</Accounting_Unit_Id>";
					xml=xml+"<Accounting_Unit_Office_Id>"+rs.getInt("Accounting_Unit_Office_Id")+"</Accounting_Unit_Office_Id>";
					//System.out.println("be4");
					if(rs.getDate("DRAWING_OFFICER_APPROVE_DATE")==null)
					{
						xml=xml+"<appdate>"+null+"</appdate>";
					}
					else{
					String approvalDate[]=rs.getDate("DRAWING_OFFICER_APPROVE_DATE").toString().split("-");
					String approvalDate1=approvalDate[2]+"/"+approvalDate[1]+"/"+approvalDate[0];
					xml=xml+"<appdate>"+approvalDate1+"</appdate>";
					}
					String treasury_date=rs.getString("TREASURY_VERIFY_DATE");
				
					try{
						System.out.println("yes");
					String treasury[]=rs.getDate("TREASURY_VERIFY_DATE").toString().split("-");
					String treasury1=treasury[2]+"/"+treasury[1]+"/"+treasury[0];
					xml=xml+"<treDate>"+treasury1+"</treDate>";
					}
					catch(Exception ee)
					{
						System.out.println("exception:"+ee);
						xml=xml+"<treDate>"+null+"</treDate>";
					}
					
					 xml=xml+"<flag>success</flag>";
				   
				}
				else
				{
					 xml=xml+"<flag>NoData</flag>";
				}
				
				
				
				
			}
			catch(Exception e)
			{
				xml = xml + "<flag>failure</flag>";
				System.out.println(e);
			}
			xml = xml + "</response>";
			System.out.println(xml);	
			
		}
		
		
		
		else if(cmd.equalsIgnoreCase("add"))
		{
			
			int k=0;
			xml=xml+"<response><command>add</command>";
			String cmbAcc_UnitCode=request.getParameter("cmbAcc_UnitCode");
			int accno=Integer.parseInt(cmbAcc_UnitCode);
			System.out.println("values..............................."+accno);
			
			String cmbOffice_code=request.getParameter("cmbOffice_code");
			int officecode=Integer.parseInt(cmbOffice_code);
			System.out.println("values..............................."+officecode);
			
			
			String cmbMajorType=request.getParameter("cmbMajorType");
			int majorCode=Integer.parseInt(cmbMajorType);
			System.out.println("values..............................."+majorCode);
			
			
			
			String txtBillMinorType=request.getParameter("txtBillMinorType");
			int minortype=Integer.parseInt(txtBillMinorType);
			System.out.println("values..............................."+minortype);
			
			
			String txtBillSubType=request.getParameter("txtBillSubType");
			int subtype=Integer.parseInt(txtBillSubType);
			System.out.println("values..............................."+subtype);
			
			String cmbBillNO=request.getParameter("cmbBillNO");
			int billno1=Integer.parseInt(cmbBillNO);
			System.out.println("values..............................."+billno1);
			
			
			String billAmount=request.getParameter("txtBillAmount");
			double billamt=Double.parseDouble(billAmount);
			System.out.println("values..............................."+billamt);
			
			
			
			String txtEmpID_mas=request.getParameter("receivedBy");
			int receivedby=Integer.parseInt(txtEmpID_mas);
			System.out.println("values..............................."+receivedby);
		
			
			String txtEmpID_mas1=request.getParameter("auditBy");
			int auditby=Integer.parseInt(txtEmpID_mas1);
			System.out.println("values..............................."+auditby);
			String sub_q="",sub_main="";
			
			int ori_unit=Integer.parseInt(request.getParameter("ori_unit"));
			int ori_office=Integer.parseInt(request.getParameter("ori_office"));
			
			
			
			String txtDate=request.getParameter("sendtreasury");
			
			 java.sql.Date treasuryDate = null;
			 java.sql.Date txtAuditDate = null;
			 java.sql.Date approvalDate = null;
			 java.sql.Date receiptDate = null;
			 java.sql.Date verifydate = null;
			 java.sql.Date billdate = null;
			 
			   java.util.GregorianCalendar c2;
			
			   
			   String[] reg_Date= txtDate.split("/");
				c2 = new java.util.GregorianCalendar(Integer.parseInt(reg_Date[2]),
			    Integer.parseInt(reg_Date[1]) - 1, Integer.parseInt(reg_Date[0]));
				java.util.Date ddd = c2.getTime();
				treasuryDate = new Date(ddd.getTime());
				System.out.println("date-----treasury----------"+treasuryDate);
			   
				String txtApprovalDate=request.getParameter("txtApprovalDate");
				if(txtApprovalDate.equals("-"))
				{
					approvalDate=null;
				}
			   
				else{
				txtApprovalDate=request.getParameter("txtApprovalDate");
				String[] appdate= txtApprovalDate.split("/");
				c2 = new java.util.GregorianCalendar(Integer.parseInt(appdate[2]),
			    Integer.parseInt(appdate[1]) - 1, Integer.parseInt(appdate[0]));
				java.util.Date ddd2 = c2.getTime();
				approvalDate = new Date(ddd2.getTime());
				System.out.println("date----------txtApprovalDate------------"+approvalDate);
			   }
				
				
				String txtDateOfReceipt=request.getParameter("txtDateOfReceipt");
				if(!txtDateOfReceipt.equalsIgnoreCase(null)){
				String[] repDate= txtDateOfReceipt.split("/");
				c2 = new java.util.GregorianCalendar(Integer.parseInt(repDate[2]),
			    Integer.parseInt(repDate[1]) - 1, Integer.parseInt(repDate[0]));
				java.util.Date ddd3 = c2.getTime();
				receiptDate = new Date(ddd3.getTime());
				System.out.println("date----------txtDateOfReceipt----------------"+receiptDate);
				
				}else{
					receiptDate=null;
				}
				
				String txtVerificationDate=request.getParameter("txtVerificationDate");
				if(txtVerificationDate.equals("-"))
				{
					verifydate=null;
				}
				else{
				String[] verDate= txtVerificationDate.split("/");
				c2 = new java.util.GregorianCalendar(Integer.parseInt(verDate[2]),
			    Integer.parseInt(verDate[1]) - 1, Integer.parseInt(verDate[0]));
				java.util.Date ddd4 = c2.getTime();
				verifydate = new Date(ddd4.getTime());
				System.out.println("date---------txtVerificationDate-----------"+verifydate);
				
				}
				
				
				String txtBillDate=request.getParameter("txtBillDate");
				String[] billDate= txtBillDate.split("/");
				
				int month=Integer.parseInt(billDate[1]);
				System.out.println("date-----------------------------------"+month);
				
				int year=Integer.parseInt(billDate[2]);
				System.out.println("date-----------------------------------"+year);
				c2 = new java.util.GregorianCalendar(Integer.parseInt(billDate[2]),
			    Integer.parseInt(billDate[1]) - 1, Integer.parseInt(billDate[0]));
				java.util.Date ddd5 = c2.getTime();
				billdate = new Date(ddd5.getTime());
				System.out.println("date-----------------------------------"+billdate);
				
			/*	if(year>2014 && month>3)
				{
					 sub_q = " FAS_BILL_REGISTER_MASTERNEW "; 
					 sub_main=" Fas_Bill_Register_MasterNEW M, "+
					" 	  Fas_Bill_Register_Transactionw T ";
				}else{
					sub_q = " FAS_BILL_REGISTER_MASTER "; 
					 sub_main=" Fas_Bill_Register_Master M, "+
								" 	  Fas_Bill_Register_Transaction T ";
				}*/
				if (year > 2014) {
					if (year == 2015 && month <= 3) {
						sub_q = " FAS_BILL_REGISTER_MASTER "; 
						 sub_main=" Fas_Bill_Register_Master M, "+
									" 	  Fas_Bill_Register_Transaction T ";	
					}else{
						 sub_q = " FAS_BILL_REGISTER_MASTERNEW "; 
						 sub_main=" Fas_Bill_Register_MasterNEW M, "+
						" 	  Fas_Bill_Register_Transactionw T ";
					}
				}else{
					sub_q = " FAS_BILL_REGISTER_MASTER "; 
					 sub_main=" Fas_Bill_Register_Master M, "+
								" 	  Fas_Bill_Register_Transaction T ";
				}
				
				
				String AuditDate=request.getParameter("txtAuditDate");
				String[] reg_Date1= AuditDate.split("/");
				c2 = new java.util.GregorianCalendar(Integer.parseInt(reg_Date1[2]),
			    Integer.parseInt(reg_Date1[1]) - 1, Integer.parseInt(reg_Date1[0]));
				java.util.Date ddd1 = c2.getTime();
				txtAuditDate = new Date(ddd1.getTime());
				System.out.println("date-----------------------------------"+txtAuditDate);
		
			
				String approval=request.getParameter("r1");
				System.out.println("values..............................."+approval);
			
			
			
			String mtxtRemarks=request.getParameter("mtxtRemarks");
			System.out.println("values..............................."+mtxtRemarks);
			
			 String profile=(String)session.getAttribute("UserId");
				//int employeeid=profile.getEmployeeId();
				System.out.println("values..............................."+profile);
				
			
				long l = System.currentTimeMillis();
				Timestamp ts = new Timestamp(l);
				System.out.println("values..............................."+ts);
				
			
			System.out.println("***********************************************************************************************end");
			
			
			
			
           
            try
            {
           if(majorCode==2)
           {
               ps=con.prepareStatement("update "+sub_q+" set DRAWING_OFFICER_APPROVE_DATE=?,DOR_BY_PRE_AUDIT=?,PRE_AUDIT_RECEIVED_BY=?,PRE_AUDIT_BY=?,PRE_AUDIT_DATE=?,BILL_APPROVED=?,DATE_SENT_TO_TREASURY_SECTION=?,PRE_AUDIT_REMARKS=? where ACCOUNTING_UNIT_ID=? and ACCOUNTING_UNIT_OFFICE_ID=? and CASHBOOK_YEAR=? and CASHBOOK_MONTH=? and BILL_NO=? and BILL_MAJOR_TYPE=?");
           }
           else
           {
        	   ps=con.prepareStatement("update FAS_BILL_REGISTERNEW set DRAWING_OFFICER_APPROVE_DATE=?,DOR_BY_PRE_AUDIT=?,PRE_AUDIT_RECEIVED_BY=?,PRE_AUDIT_BY=?,PRE_AUDIT_DATE=?,BILL_APPROVED=?,DATE_SENT_TO_TREASURY_SECTION=?,PRE_AUDIT_REMARKS=? where ACCOUNTING_UNIT_ID=? and ACCOUNTING_UNIT_OFFICE_ID=? and CASHBOOK_YEAR=? and CASHBOOK_MONTH=? and BILL_NO=? and BILL_MAJOR_TYPE=?");
           }
               ps.setDate(1,approvalDate);
               ps.setDate(2,receiptDate);
               ps.setInt(3,receivedby);
               ps.setInt(4,auditby);
               ps.setDate(5,txtAuditDate);
               ps.setString(6,approval);
               ps.setDate(7,treasuryDate);
               ps.setString(8,mtxtRemarks);
               ps.setInt(9,ori_unit);
               ps.setInt(10,ori_office);
               ps.setInt(12,month);
               ps.setInt(11,year);
               ps.setInt(14,majorCode);
               ps.setInt(13,billno1);
               int j=ps.executeUpdate();
           // System.out.println("j:"+j);
             String[] grid=request.getParameter("grid").split(",");
             int len=grid.length;
			 System.out.println("arraylength"+len);
             for(int i=0;i<len;i=i+5)
			 {
            	
    			
    			String description=grid[i];
    			System.out.println("values..............................."+description);
    			
    			String mandate=grid[i+1];
    			System.out.println("values..............................."+mandate);
    			
    			String notApplicable=grid[i+2];
    			System.out.println("values..............................."+notApplicable);
    			  int deccode=Integer.parseInt(grid[i+4]);
              	
              	System.out.println("values..............................."+deccode);
            	
			
          ps1=con.prepareStatement("insert into FAS_PRE_AUDIT_CHECK_NEW values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)");
           ps1.setInt(1,accno);
           ps1.setInt(2,officecode);
           ps1.setInt(3,year);
            ps1.setInt(4,month);
            ps1.setInt(5, deccode);
           ps1.setInt(6, majorCode);
           ps1.setInt(7, minortype);
           ps1.setInt(8, subtype);
           ps1.setString(9,"L");
           ps1.setString(10, profile);
           ps1.setTimestamp(11, ts);
           ps1.setInt(12,billno1);
           ps1.setDate(13,billdate);
         
           ps1.setDouble(14,billamt);
           ps1.setDate(15, txtAuditDate);
           ps1.setInt(16, auditby);

            ps1.setString(17,mtxtRemarks);
            k=ps1.executeUpdate();
			}
             if(k>0)
             {
            	  xml=xml+"<flag>success</flag>";
             }
          
            }
            catch(Exception e)
            {
            	 xml=xml+"<flag>failure</flag>";
            System.out.println(e);
            }
            finally
            {
                System.out.println("done");
                //try{con.setAutoCommit(true);  }catch(SQLException sqle){}
            }
			xml=xml+"</response>";
			System.out.println(xml);
            
		}
	
		out.write(xml);
	}

}
