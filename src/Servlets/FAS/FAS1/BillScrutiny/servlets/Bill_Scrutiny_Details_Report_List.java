package Servlets.FAS.FAS1.BillScrutiny.servlets;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import net.sf.jasperreports.engine.JRExporterParameter;
import net.sf.jasperreports.engine.JRRuntimeException;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.export.JRHtmlExporter;
import net.sf.jasperreports.engine.export.JRHtmlExporterParameter;
import net.sf.jasperreports.engine.export.JRTextExporter;
import net.sf.jasperreports.engine.export.JRTextExporterParameter;
import net.sf.jasperreports.engine.export.JRXlsExporter;
import net.sf.jasperreports.engine.export.JRXlsExporterParameter;
import net.sf.jasperreports.engine.util.JRLoader;

/**
 * Servlet implementation class Bill_Scrutiny_Detials_Report_List
 */
public class Bill_Scrutiny_Details_Report_List extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final String CONTENT_TYPE = "text/html; charset=windows-1252";
    Connection connection = null; 
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Bill_Scrutiny_Details_Report_List() {
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

      	 try{cmbAcc_UnitCode=Integer.parseInt(request.getParameter("cmbAcc_UnitCode"));}
           catch(NumberFormatException e){System.out.println("exception"+e );}
           
           
           try{cmbOffice_code=Integer.parseInt(request.getParameter("cmbOffice_code"));}
           catch(NumberFormatException e){System.out.println("exception"+e );}
           
           txtCB_Year=Integer.parseInt(request.getParameter("txtCB_Year"));
           txtCB_Month=Integer.parseInt(request.getParameter("txtCB_Month"));        
          
           
           String optionId=request.getParameter("optionId");
           String optioiiid="";
           String sub_q = "",sub_main="";
		/*	if(txtCB_Year>2014 && txtCB_Month>3)
			{
				 sub_q = " FAS_BILL_REGISTER_MASTERNEW "; 
				 sub_main=" Fas_Bill_Register_MasterNEW M, "+
				" 	  Fas_Bill_Register_Transactionw T ";
			}else{
				sub_q = " FAS_BILL_REGISTER_MASTER "; 
				 sub_main=" Fas_Bill_Register_Master M, "+
							" 	  Fas_Bill_Register_Transaction T ";
			}*/
           

			if (txtCB_Year > 2014) {
				if (txtCB_Year == 2015 && txtCB_Month <= 3) {
					sub_q = " FAS_BILL_REGISTER_MASTER "; 
					 sub_main=" Fas_Bill_Register_Master M, "+
								" 	  Fas_Bill_Register_Transaction T ";
				}else{
					sub_q = " FAS_BILL_REGISTER_MASTERNEW "; 
					 sub_main=" Fas_Bill_Register_MasterNEW M, "+
					" 	  Fas_Bill_Register_Transactionw T ";
				}}else
				{
					sub_q = " FAS_BILL_REGISTER_MASTER "; 
					 sub_main=" Fas_Bill_Register_Master M, "+
								" 	  Fas_Bill_Register_Transaction T ";
				}
           
          if(optionId.equalsIgnoreCase("live")){
        	  optioiiid=" and STATUS='L'";
          }else if(optionId.equalsIgnoreCase("cancel")){
        	  optioiiid=" and STATUS='C'";
          }
          
	            xml="<response><command>searchByMonth</command>";                        
	       
	            String sql="SELECT bill_no,CASHBOOK_YEAR,CASHBOOK_MONTH, " +
	            "  BILL_MINOR_TYPE_CODE, " +
	            "  BILL_MAJOR_TYPE, " +
	            "  BILL_SUB_TYPE_CODE, " +
	            "  TO_CHAR(BILL_DATE,'dd/mm/yyyy')BILL_DATE, " +
	            "  SANCTION_PROC_NO, " +
	            "  CASE " +
	            "    WHEN bill_type <> 'WOSP'  and  BILL_MINOR_TYPE_CODE=2 " +
	            "    AND BILL_MAJOR_TYPE      =2 " +
	            "    AND BILL_SUB_TYPE_CODE   =1 " +
	            "    THEN " +
	            "      (SELECT SANCTION_PROC_NO " +
	            "      FROM SLS_SANCTIONS_BILLS_LINK_MST1 " +
	            "      WHERE HRMS_SANCTION_ID::varchar=m.SANCTION_PROC_NO " +
	            "      ) when bill_type = 'WOSP'  then m.SANCTION_PROC_NO " +
	            "    ELSE " +
	            "      (SELECT SANCTION_PROC_NO " +
	            "      FROM HRM_SANCTIONS_BILLS_LINK_MST " +
	            "      WHERE HRMS_SANCTION_ID::varchar=m.SANCTION_PROC_NO " +
	            "      ) " +
	            "  END AS SanProcNo, " +
	            "  SANCTION_PROC_NO, " +
	            "  TO_CHAR(PROCEEDING_RECD_DATE,'dd/mm/yyyy')PROCEEDING_RECD_DATE, " +
	            "  TOTAL_SANCTIONED_AMOUNT, " +
	            "  payee_type_code, " +
	            "  (SELECT sub_ledger_type_desc " +
	            "  FROM com_mst_sl_types " +
	            "  WHERE sub_ledger_type_code::varchar=payee_type_code " +
	            "  )AS typedesc, " +
	            "  payee_code, " +
	            "  (SELECT SL_CODENAME " +
	            "  FROM SL_TYPE_CODE_NAME_VIEW " +
	            "  WHERE SL_CODE=payee_code " +
	            "  AND SL_TYPE::varchar  =payee_type_code " +
	            "  )AS codeDesc, " +
	            "  deducted_amount, " +
	            "  NET_AMOUNT, " +
	            "  remarks  " +
	            " FROM "+sub_q+" m " +
	            " WHERE ACCOUNTING_UNIT_ID=" +cmbAcc_UnitCode+
	               " and EXTRACT (YEAR FROM BILL_SCRUTINY_DATE)="+txtCB_Year+" AND "+
	            " extract (month from BILL_SCRUTINY_DATE)="+txtCB_Month+
	       /*     " AND CASHBOOK_YEAR       =" +txtCB_Year+
	            " AND CASHBOOK_MONTH      = " +txtCB_Month+*/
	            " AND BILL_SCRUTINY_DONE  ='Y' " +optioiiid +" order by bill_no";
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
						                    xml=xml+"<billno>"+rs.getInt("bill_no")+"</billno>";
						                    xml=xml+"<sancno>"+rs.getString("SanProcNo")+"</sancno>";
						                    xml=xml+"<billdate>"+rs.getString("BILL_DATE")+"</billdate>";
						                    xml=xml+"<billscrdate>"+rs.getString("PROCEEDING_RECD_DATE")+"</billscrdate>";
						                    xml=xml+"<CASHBOOK_YEAR>"+rs.getInt("CASHBOOK_YEAR")+"</CASHBOOK_YEAR>";
						                    xml=xml+"<CASHBOOK_MONTH>"+rs.getInt("CASHBOOK_MONTH")+"</CASHBOOK_MONTH>";
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
