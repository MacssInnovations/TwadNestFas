package Servlets.FAS.FAS1.BillScrutiny.servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Servlet implementation class Bill_Scrutiny_Details_Report_List_All
 */
public class Bill_Scrutiny_Details_Report_List_All extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final String CONTENT_TYPE = "text/html; charset=windows-1252";
    Connection connection = null; 
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Bill_Scrutiny_Details_Report_List_All() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		
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
	         ResultSet rs=null;
	         int  txtCB_Year=0, txtCB_Month=0,cmbAcc_UnitCode=0,cmbOffice_code=0;
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
	                 ConnectionString = strdsn.trim() + "://" + strhostname.trim() + ":" + strportno.trim() + "/" +strsid.trim() ;    // Postgres DB  Connection	                 Class.forName(strDriver.trim());
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
	       
		 if(strType.equalsIgnoreCase("searchByMonth"))  
	        {

    	/* try{cmbAcc_UnitCode=Integer.parseInt(request.getParameter("cmbAcc_UnitCode"));}
         catch(NumberFormatException e){System.out.println("exception"+e );}
         
         
         try{cmbOffice_code=Integer.parseInt(request.getParameter("cmbOffice_code"));}
         catch(NumberFormatException e){System.out.println("exception"+e );}
         */
         txtCB_Year=Integer.parseInt(request.getParameter("txtCB_Year"));
         txtCB_Month=Integer.parseInt(request.getParameter("txtCB_Month"));        
        
         
         String optionId=request.getParameter("optionId");
         String optioiiid="";
         
        if(optionId.equalsIgnoreCase("live")){
      	  optioiiid=" and STATUS='L'";
        }else if(optionId.equalsIgnoreCase("cancel")){
      	  optioiiid=" and STATUS='C'";
        }
        
	            xml="<response><command>searchByMonth</command>";                        
	       
	            String sql="SELECT bill_no,CASHBOOK_YEAR,CASHBOOK_MONTH  , ACCOUNTING_UNIT_ID,ACCOUNTING_UNIT_OFFICE_ID," +
	           " (select ACCOUNTING_UNIT_NAME from FAS_MST_ACCT_UNITS f1 where f1.ACCOUNTING_UNIT_ID= m.ACCOUNTING_UNIT_ID  )as UnitName, "+
	           " (select OFFICE_NAME from COM_MST_OFFICES f2 where f2.OFFICE_ID=m.ACCOUNTING_UNIT_OFFICE_ID) as OfficeName, "+
	            "  BILL_MINOR_TYPE_CODE, " +
	            "  BILL_MAJOR_TYPE, " +
	            "  BILL_SUB_TYPE_CODE, " +
	            "  TO_CHAR(BILL_DATE,'dd/mm/yyyy')BILL_DATE, " +
	            "  SANCTION_PROC_NO, " +
	            "  CASE " +
	            "    WHEN   bill_type <> 'WOSP' and  BILL_MINOR_TYPE_CODE=2 " +
	            "    AND BILL_MAJOR_TYPE      =2 " +
	            "    AND BILL_SUB_TYPE_CODE   =1 " +
	            "    THEN " +
	            "      (SELECT SANCTION_PROC_NO " +
	            "      FROM SLS_SANCTIONS_BILLS_LINK_MST1 " +
	            "      WHERE HRMS_SANCTION_ID=m.SANCTION_PROC_NO " +
	            "      ) " +
	            " when  bill_type = 'WOSP' then  m.SANCTION_PROC_NO "+
	            "    ELSE " +
	            "      (SELECT SANCTION_PROC_NO " +
	            "      FROM HRM_SANCTIONS_BILLS_LINK_MST " +
	            "      WHERE HRMS_SANCTION_ID=m.SANCTION_PROC_NO " +
	            "      ) " +
	            "  END AS SanProcNo, " +
	            "  SANCTION_PROC_NO, " +
	            "  TO_CHAR(PROCEEDING_RECD_DATE,'dd/mm/yyyy')PROCEEDING_RECD_DATE, " +
	            "  TOTAL_SANCTIONED_AMOUNT, " +
	            "  payee_type_code, " +
	            "  (SELECT sub_ledger_type_desc " +
	            "  FROM com_mst_sl_types " +
	            "  WHERE sub_ledger_type_code=payee_type_code " +
	            "  )AS typedesc, " +
	            "  payee_code, " +
	            "  (SELECT SL_CODENAME " +
	            "  FROM SL_TYPE_CODE_NAME_VIEW " +
	            "  WHERE SL_CODE=payee_code " +
	            "  AND SL_TYPE  =payee_type_code " +
	            "  )AS codeDesc, " +
	            "  deducted_amount, " +
	            "  NET_AMOUNT, " +
	            "  remarks  " +
	            " FROM fas_bill_register_master m " +
	            " WHERE " +
	           " EXTRACT (YEAR FROM BILL_SCRUTINY_DATE)="+txtCB_Year+" AND "+
	            " extract (month from BILL_SCRUTINY_DATE)="+txtCB_Month+
	       /*     " CASHBOOK_YEAR       =" ++
	            " AND CASHBOOK_MONTH      = " +txtCB_Month+*/
	            " AND BILL_SCRUTINY_DONE  ='Y' " +optioiiid +" order by   ACCOUNTING_UNIT_ID,bill_no";
	            //" AND STATUS              ='L'" ;
				            
				            System.out.println("SQL::::"+sql);
				            try
				            {
						            int count=0;
						            ps=con.prepareStatement(sql);
						            rs=ps.executeQuery();
					                while(rs.next())
					                {
						                    xml=xml+"<leng>";
						                    
						                    xml=xml+"<ACCOUNTING_UNIT_ID>"+rs.getInt("ACCOUNTING_UNIT_ID")+"</ACCOUNTING_UNIT_ID>";						                  
						                    xml=xml+"<UnitName><![CDATA["+rs.getString("UnitName")+"]]></UnitName>";
						                    
						                    xml=xml+"<ACCOUNTING_UNIT_OFFICE_ID>"+rs.getInt("ACCOUNTING_UNIT_OFFICE_ID")+"</ACCOUNTING_UNIT_OFFICE_ID>";						                  
						                    xml=xml+"<OfficeName><![CDATA["+rs.getString("OfficeName")+"]]></OfficeName>";
						                    
						                
						                    xml=xml+"<CASHBOOK_YEAR>"+rs.getInt("CASHBOOK_YEAR")+"</CASHBOOK_YEAR>";
						                    xml=xml+"<CASHBOOK_MONTH>"+rs.getInt("CASHBOOK_MONTH")+"</CASHBOOK_MONTH>";
						                    xml=xml+"<billno>"+rs.getInt("bill_no")+"</billno>";
						                    xml=xml+"<sancno>"+rs.getString("SanProcNo")+"</sancno>";
						                    xml=xml+"<billdate>"+rs.getString("BILL_DATE")+"</billdate>";
						                    xml=xml+"<billscrdate>"+rs.getString("PROCEEDING_RECD_DATE")+"</billscrdate>";
						                    
						                    xml=xml+"<paycode>"+rs.getString("codeDesc")+"</paycode>";
						                    xml=xml+"<paytypecode>"+rs.getString("typeDesc")+"</paytypecode>";
						                    xml=xml+"<deducted_amount>"+rs.getString("deducted_amount") +"</deducted_amount>";
						                    xml=xml+"<NET_AMOUNT>"+rs.getString("NET_AMOUNT") +"</NET_AMOUNT>";
						                    xml=xml+"<sancamt>"+rs.getString("TOTAL_SANCTIONED_AMOUNT") +"</sancamt>";
						                   // xml=xml+"<billamt>"+rs.getString("TOTAL_BILL_AMOUNT") +"</billamt>";
						                    xml=xml+"<remarks><![CDATA["+rs.getString("remarks")+"]]></remarks>";
						                    
						                    xml=xml+"</leng>";
						                    count++;
					                }
					                if(count>0) 
					                {
						                   // System.out.println("inside count==0");
						                    xml=xml+"<flag>success</flag>";
						                  
					                }
					                else
					                {
					                	 xml=xml+"<flag>failure</flag>";
					                	
					                }
				            }
				            catch(SQLException sqle)
				            {
					        	    sqle.printStackTrace();
					        	    System.out.println("error while fetching data " + sqle);
					                xml="<response><command>searchByMonth</command><flag>failure</flag>";
				            }
            
           
			 
	        }
	        
	        xml=xml+"</response>";   
	        out.println(xml); 
	        System.out.println(xml); 
	
			
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

}
