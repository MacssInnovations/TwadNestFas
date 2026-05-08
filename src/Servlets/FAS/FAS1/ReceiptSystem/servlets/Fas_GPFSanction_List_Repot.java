package Servlets.FAS.FAS1.ReceiptSystem.servlets;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
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
 * Servlet implementation class A5ScheduleReport
 */
public class Fas_GPFSanction_List_Repot extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final String CONTENT_TYPE = "text/html; charset=windows-1252"; 
	 Connection connection = null;
      
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Fas_GPFSanction_List_Repot() {
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
            
            }
            System.out.println(session);
                
        }catch(Exception e)
        {
        	System.out.println("Redirect Error :"+e);
        }
        String opt="";
        response.setContentType("text/xml; charset=windows-1252");
        try 
        {
        	ResourceBundle rs = ResourceBundle.getBundle("Servlets.Security.servlets.Config");
            String ConnectionString = "";

            String strDriver = rs.getString("Config.DATA_BASE_DRIVER");
            String strdsn = rs.getString("Config.DSN");
            String strhostname = rs.getString("Config.HOST_NAME");
            String strportno = rs.getString("Config.PORT_NUMBER");
            String strsid = rs.getString("Config.SID");
            String strdbusername = rs.getString("Config.USER_NAME");
            String strdbpassword = rs.getString("Config.PASSWORD");

            ConnectionString =strdsn.trim() + "@" + strhostname.trim() + ":" + strportno.trim() +":" + strsid.trim();

            Class.forName(strDriver.trim());
            connection =DriverManager.getConnection(ConnectionString, strdbusername.trim(),strdbpassword.trim());
        } catch (Exception ex) {
            String connectMsg ="Could not create the connection" + ex.getMessage() + " " + 
            ex.getLocalizedMessage();
            System.out.println(connectMsg);
        }
        
        PrintWriter out=response.getWriter();
        String command=request.getParameter("command");
    	String qry="",xml="";
    	int count=0,Major_Code=0,Minor_Code=0;
    	PreparedStatement ps=null;
    	ResultSet rs=null;
        if(command.equalsIgnoreCase("getBillMajor")){
        	xml="<response><command>"+command+"</command>";
        
        	try{
        		qry="SELECT BILL_MAJOR_TYPE_CODE,BILL_MAJOR_TYPE_DESC  FROM FAS_BILL_MAJOR_TYPES";
        		System.out.println(qry);
        		ps=connection.prepareStatement(qry);
        		rs=ps.executeQuery();
        		count=0;
        		while(rs.next()){
					xml += "<Major_Len><BILL_MAJOR_TYPE_CODE>"
							+ rs.getInt("BILL_MAJOR_TYPE_CODE")
							+ "</BILL_MAJOR_TYPE_CODE>" +"<BILL_MAJOR_TYPE_DESC>"
							+ rs.getString("BILL_MAJOR_TYPE_DESC")
							+ "</BILL_MAJOR_TYPE_DESC></Major_Len>";
	count++;
        		}if(count>0)
        		xml+="<flag>success</flag>";
        		else
        		xml+="<flag>failure</flag>";
        	
        		
        	}catch (Exception e) {
System.out.println(e);
        	}
        	xml+="</response>";
        }
        
        if(command.equalsIgnoreCase("getBillMinor")){
        	
        	Major_Code=Integer.parseInt(request.getParameter("cmbBill_Major"));
        	xml="<response><command>"+command+"</command>";
        
        	try{
        		qry="SELECT BILL_MINOR_TYPE_CODE,BILL_MINOR_TYPE_DESC FROM FAS_BILL_MINOR_TYPES_MST WHERE BILL_MAJOR_TYPE_CODE="+Major_Code;
        		System.out.println(qry);
        		ps=connection.prepareStatement(qry);
        		rs=ps.executeQuery();
        		count=0;
        		while(rs.next()){
					xml += "<Minor_Len><BILL_MINOR_TYPE_CODE>"
							+ rs.getInt("BILL_MINOR_TYPE_CODE")
							+ "</BILL_MINOR_TYPE_CODE>" +"<BILL_MINOR_TYPE_DESC>"
							+ rs.getString("BILL_MINOR_TYPE_DESC")
							+ "</BILL_MINOR_TYPE_DESC></Minor_Len>";
	count++;
        		}if(count>0)
        		xml+="<flag>success</flag>";
        		else
        		xml+="<flag>failure</flag>";
        	
        		
        	}catch (Exception e) {
System.out.println(e);
        	}
        	xml+="</response>";
        }
        
  if(command.equalsIgnoreCase("getSubType")){
        	
        	Major_Code=Integer.parseInt(request.getParameter("cmbBill_Major"));
        	Minor_Code=Integer.parseInt(request.getParameter("cmbBill_Minor"));
        	xml="<response><command>"+command+"</command>";
        
        	try{
        		qry="SELECT BILL_SUB_TYPE_CODE, " +
        		"  BILL_SUB_TYPE_DESC " +
        		" FROM FAS_BILL_SUB_TYPES " +
        		" WHERE BILL_MAJOR_TYPE_CODE= " +Major_Code+
        		" AND BILL_MINOR_TYPE_CODE  ="+Minor_Code;

        		System.out.println(qry);
        		ps=connection.prepareStatement(qry);
        		rs=ps.executeQuery();
        		count=0;
        		while(rs.next()){
					xml += "<Sub_Len><BILL_SUB_TYPE_CODE>"
							+ rs.getInt("BILL_SUB_TYPE_CODE")
							+ "</BILL_SUB_TYPE_CODE>" +"<BILL_SUB_TYPE_DESC>"
							+ rs.getString("BILL_SUB_TYPE_DESC")
							+ "</BILL_SUB_TYPE_DESC></Sub_Len>";
	count++;
        		}if(count>0)
        		xml+="<flag>success</flag>";
        		else
        		xml+="<flag>failure</flag>";
        	
        		
        	}catch (Exception e) {
System.out.println(e);
        	}
        	xml+="</response>";
        }
        out.write(xml);
    	System.out.println(xml);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		 try
	        {
	            HttpSession session=request.getSession(false);
	            if(session==null)
	            {
	                System.out.println(request.getContextPath()+"/index.jsp");
	                response.sendRedirect(request.getContextPath()+"/index.jsp");
	            
	            }
	            System.out.println(session);
	                
	        }catch(Exception e)
	        {
	        	System.out.println("Redirect Error :"+e);
	        }
	        String opt="";
	        response.setContentType(CONTENT_TYPE);
	        try 
	        {
	        	ResourceBundle rs = ResourceBundle.getBundle("Servlets.Security.servlets.Config");
	            String ConnectionString = "";

	            String strDriver = rs.getString("Config.DATA_BASE_DRIVER");
	            String strdsn = rs.getString("Config.DSN");
	            String strhostname = rs.getString("Config.HOST_NAME");
	            String strportno = rs.getString("Config.PORT_NUMBER");
	            String strsid = rs.getString("Config.SID");
	            String strdbusername = rs.getString("Config.USER_NAME");
	            String strdbpassword = rs.getString("Config.PASSWORD");

	            ConnectionString =strdsn.trim() + "@" + strhostname.trim() + ":" + strportno.trim() +":" + strsid.trim();

	            Class.forName(strDriver.trim());
	            connection =DriverManager.getConnection(ConnectionString, strdbusername.trim(),strdbpassword.trim());
	        } catch (Exception ex) {
	            String connectMsg ="Could not create the connection" + ex.getMessage() + " " + 
	            ex.getLocalizedMessage();
	            System.out.println(connectMsg);
	        }
	        JasperDesign jasperDesign = null;
	        File reportFile=null;
	        try 
	        {     System.out.println("test");
	        String command=request.getParameter("command");
            System.out.println(command);
	        	int txtCB_Year=Integer.parseInt(request.getParameter("txtCB_Year"));
	        	 String txtCB_Month=request.getParameter("txtCB_Month");
	             
	             String rtype=request.getParameter("txtoption");
	             int Major_Code=Integer.parseInt(request.getParameter("cmbBill_Major"));
	              int Minor_Code=Integer.parseInt(request.getParameter("cmbBill_Minor"));
	              int Sub_type=Integer.parseInt(request.getParameter("cmbSub_Type"));
	        
	           String monthInWords="";
	          if(txtCB_Month.equalsIgnoreCase("01"))
	 	            monthInWords="January";
	 	            else  if(txtCB_Month.equalsIgnoreCase("02"))
	 	            monthInWords="February";
	 	            else  if(txtCB_Month.equalsIgnoreCase("03"))
	 	            monthInWords="March";
	 	            else  if(txtCB_Month.equalsIgnoreCase("04"))
	 	            monthInWords="April";
	 	            else  if(txtCB_Month.equalsIgnoreCase("05"))
	 	            monthInWords="May";
	 	            else  if(txtCB_Month.equalsIgnoreCase("06"))
	 	            monthInWords="June";
	 	            else  if(txtCB_Month.equalsIgnoreCase("07"))
	 	            monthInWords="July";
	 	            else  if(txtCB_Month.equalsIgnoreCase("08"))
	 	            monthInWords="August";
	 	            else  if(txtCB_Month.equalsIgnoreCase("09"))
	 	            monthInWords="September";
	 	            else  if(txtCB_Month.equalsIgnoreCase("10"))
	 	            monthInWords="October";
	 	            else  if(txtCB_Month.equalsIgnoreCase("11"))
	 	            monthInWords="November";
	 	            else  if(txtCB_Month.equalsIgnoreCase("12"))
	 	            monthInWords="December";
	         	 	
	 	        StringBuilder sb=new StringBuilder();
	 	     String chk_val="";
	 	     String sub_qry="",tabl_name="",tabl_name1="",head="";

	 	         
	 	                if(command.equalsIgnoreCase("All")){
	 	              sub_qry=" WHERE  TO_CHAR(SANCTION_PROC_DATE,'MM-YYYY')='" +txtCB_Month+"-"+txtCB_Year+"'";
	 	            reportFile = new File(getServletContext().getRealPath("/org/FAS/FAS1/Reports/ReceiptSystem/jasper/GPFSanctionAllOfficeReport.jasper"));
	 	            
	 	            
	 	            
	 	       /*    chk_val= "SELECT a.bill_major_type_code, " +
        			"  fbill.bill_major_type_desc major_desc, " +
         			"  a.bill_minor_type_code, " +
         			"  f_min.bill_minor_type_desc minor_desc, " +
         			"  a.bill_sub_type_code, " +
         			"  sub.bill_sub_type_desc sub_desc, " +
         			"  a.EMPLOYEE_ID, " +
         			"  HE. EMPLOYEE_INITIAL " +
         			"  ||'.' " +
         			"  ||he.EMPLOYEE_NAME AS emp_name, " +
         			"  a.gpf_no, " +
         			"  a.designation_id, " +
         			"  des.designation desig, " +
         			"  a.OFFICE_ID, " +
         			"  off.office_name office_desc, " +
         			"  a.sanction_proc_office_id, " +
         			"  (SELECT OFFICE_NAME " +
         			"  FROM COM_MST_OFFICES " +
         			"  WHERE OFFICE_ID=a.SANCTION_PROC_OFFICE_ID " +
         			"  )AS SANC_office_desc, " +
         			"  a.SANCTION_PROC_NO, " +
         			"  a.SANCTION_PROC_DATE, " +
         			"  a.SANCTIONED_AMOUNT, " +
         			"  a.PAYMENT_HEAD_OF_AC, " +
         			"  a.payment_amount, " +
         			"  a.REMARKS , " +
         			"  b.tot_san " +
         			"FROM " +
         			"  (SELECT master_san.BILL_MAJOR_TYPE_CODE, " +
         			"    master_san.bill_minor_type_code, " +
         			"    master_san.BILL_SUB_TYPE_CODE, " +
         			"    master_san.EMPLOYEE_ID, " +
         			"    master_san.GPF_NO, " +
         			"    master_san.DESIGNATION_ID, " +
         			"    master_san.OFFICE_ID, " +
         			"    master_san.SANCTION_PROC_OFFICE_ID, " +
         			"    master_san.SANCTION_PROC_NO, " +
         			"    master_san.SANCTION_PROC_DATE, " +
         			"    master_san.SANCTIONED_AMOUNT, " +
         			"    master_tran.PAYMENT_HEAD_OF_AC, " +
         			"    master_tran.PAYMENT_AMOUNT, " +
         			"    master_tran.REMARKS " +
         			"  FROM " +
         			"    (SELECT HRMS_SANCTION_ID, " +
         			"      BILL_MAJOR_TYPE_CODE, " +
         			"      BILL_MINOR_TYPE_CODE, " +
         			"      BILL_SUB_TYPE_CODE, " +
         			"      EMPLOYEE_ID, " +
         			"      GPF_NO, " +
         			"      DESIGNATION_ID, " +
         			"      OFFICE_ID, " +
         			"      SANCTION_PROC_NO, " +
         			"      SANCTION_PROC_DATE, " +
         			"      SANCTIONED_AMOUNT, " +
         			"      SANCTION_PROC_OFFICE_ID " +
         			"    FROM HRM_SANCTIONS_BILLS_LINK_MST " +
         			sub_qry+
         			"    ) master_san " +
         			"  JOIN " +
         			"    (SELECT HRMS_SANCTION_ID, " +
         			"      BILL_MAJOR_TYPE_CODE, " +
         			"      BILL_MINOR_TYPE_CODE, " +
         			"      BILL_SUB_TYPE_CODE, " +
         			"      EMPLOYEE_ID, " +
         			"      GPF_NO, " +
         			"      DESIGNATION_ID, " +
         			"      REMARKS, " +
         			"      PAYMENT_HEAD_OF_AC, " +
         			"      PAYMENT_AMOUNT, " +
         			"      OFFICE_ID " +
         			"    FROM HRM_SANCTIONS_BILLS_LINK_TRN " +
         			"    ) master_tran " +
         			"  ON master_san.HRMS_SANCTION_ID       = master_tran.HRMS_SANCTION_ID " +
         			"  AND master_san.BILL_MAJOR_TYPE_CODE  = master_tran.BILL_MAJOR_TYPE_CODE " +
         			"  AND master_san.BILL_MINOR_TYPE_CODE = master_tran.BILL_MINOR_TYPE_CODE " +
         			"  AND master_san.BILL_SUB_TYPE_CODE   = master_tran.BILL_SUB_TYPE_CODE " +
         			"  AND master_san.EMPLOYEE_ID          = master_tran.EMPLOYEE_ID " +
         			"  AND master_san.GPF_NO               = master_tran.GPF_NO " +
         			"  AND master_san.designation_id       = master_tran.designation_id " +
         			"  AND master_san.office_id            = master_tran.office_id " +
         			"  )a " +
         			"INNER JOIN " +
         			"  (SELECT office_id, " +
         			"    SUM(SANCTIONED_AMOUNT) tot_san " +
         			"  FROM HRM_SANCTIONS_BILLS_LINK_MST " +
         			sub_qry+
         			"  GROUP BY office_id " +
         			"  )b " +
         			"ON a.office_id=b.office_id " +
         			"INNER JOIN fas_bill_major_types fbill " +
         			"ON a.bill_major_type_code=fbill.bill_major_type_code " +
         			"INNER JOIN fas_bill_minor_types_mst f_min " +
         			"ON a.bill_major_type_code =f_min.bill_major_type_code " +
         			"AND a.bill_minor_type_code=f_min.bill_minor_type_code " +
         			"INNER JOIN fas_bill_sub_types sub " +
         			"ON a.bill_major_type_code =sub.bill_major_type_code " +
         			"AND a.bill_minor_type_code=sub.bill_minor_type_code " +
         			"AND a.bill_sub_type_code  =sub.bill_sub_type_code " +
         			"INNER JOIN hrm_mst_employees he " +
         			"ON a.employee_id=he.employee_id " +
         			"INNER JOIN hrm_mst_designations des " +
         			"ON a.designation_id=des.designation_id " +
         			"INNER JOIN com_mst_offices OFF " +
         			"ON a.OFFICE_ID=off.OFFICE_ID " +
         			"ORDER BY a.office_id";*/
	 	           chk_val="(SELECT a.bill_major_type_code, " +
	 	           "  fbill.bill_major_type_desc major_desc, " +
	 	          "  a.bill_minor_type_code, " +
	 	          "  f_min.bill_minor_type_desc minor_desc, " +
	 	          "  a.bill_sub_type_code, " +
	 	          "  sub.bill_sub_type_desc sub_desc, " +
	 	          "  a.EMPLOYEE_ID, " +
	 	          "  HE. EMPLOYEE_INITIAL " +
	 	          "  ||'.' " +
	 	          "  ||he.EMPLOYEE_NAME AS emp_name, " +
	 	          "  a.gpf_no, " +
	 	          "  a.designation_id, " +
	 	          "  des.designation desig, " +
	 	          "  a.OFFICE_ID, " +
	 	          "  off.office_name office_desc, " +
	 	          "  a.sanction_proc_office_id, " +
	 	          "  (SELECT OFFICE_NAME " +
	 	          "  FROM COM_MST_OFFICES " +
	 	          "  WHERE OFFICE_ID=a.SANCTION_PROC_OFFICE_ID " +
	 	          "  )AS SANC_office_desc, " +
	 	          "  a.SANCTION_PROC_NO, " +
	 	          "  a.SANCTION_PROC_DATE, " +
	 	          "  a.SANCTIONED_AMOUNT, " +
	 	          "  a.PAYMENT_HEAD_OF_AC, " +
	 	          "  a.payment_amount, " +
	 	          "  a.REMARKS , " +
	 	          "  b.tot_san " +
	 	          "FROM " +
	 	          "  (SELECT master_san.BILL_MAJOR_TYPE_CODE, " +
	 	          "    master_san.bill_minor_type_code, " +
	 	          "    master_san.BILL_SUB_TYPE_CODE, " +
	 	          "    master_san.EMPLOYEE_ID, " +
	 	          "    master_san.GPF_NO, " +
	 	          "    master_san.DESIGNATION_ID, " +
	 	          "    master_san.OFFICE_ID, " +
	 	          "    master_san.SANCTION_PROC_OFFICE_ID, " +
	 	          "    master_san.SANCTION_PROC_NO, " +
	 	          "    master_san.SANCTION_PROC_DATE, " +
	 	          "    master_san.SANCTIONED_AMOUNT, " +
	 	          "    master_tran.PAYMENT_HEAD_OF_AC, " +
	 	          "    master_tran.PAYMENT_AMOUNT, " +
	 	          "    master_tran.REMARKS " +
	 	          "  FROM " +
	 	          "    (SELECT HRMS_SANCTION_ID, " +
	 	          "      BILL_MAJOR_TYPE_CODE, " +
	 	          "      BILL_MINOR_TYPE_CODE, " +
	 	          "      BILL_SUB_TYPE_CODE, " +
	 	          "      EMPLOYEE_ID, " +
	 	          "      GPF_NO, " +
	 	          "      DESIGNATION_ID, " +
	 	          "      OFFICE_ID, " +
	 	          "      SANCTION_PROC_NO, " +
	 	          "      SANCTION_PROC_DATE, " +
	 	          "      SANCTIONED_AMOUNT, " +
	 	          "      SANCTION_PROC_OFFICE_ID " +
	 	          "    FROM Hrm_Sanctions_Bills_Link_Mst " +
	 	        sub_qry +
	 	          "    ) master_san " +
	 	          "  JOIN " +
	 	          "    (SELECT HRMS_SANCTION_ID, " +
	 	          "      BILL_MAJOR_TYPE_CODE, " +
	 	          "      BILL_MINOR_TYPE_CODE, " +
	 	          "      BILL_SUB_TYPE_CODE, " +
	 	          "      EMPLOYEE_ID, " +
	 	          "      GPF_NO, " +
	 	          "      DESIGNATION_ID, " +
	 	          "      REMARKS, " +
	 	          "      PAYMENT_HEAD_OF_AC, " +
	 	          "      PAYMENT_AMOUNT, " +
	 	          "      OFFICE_ID " +
	 	          "    FROM HRM_SANCTIONS_BILLS_LINK_TRN " +
	 	          "    ) master_tran " +
	 	          "  ON master_san.HRMS_SANCTION_ID      = master_tran.HRMS_SANCTION_ID " +
	 	          "  AND master_san.BILL_MAJOR_TYPE_CODE = master_tran.BILL_MAJOR_TYPE_CODE " +
	 	          "  AND master_san.BILL_MINOR_TYPE_CODE = master_tran.BILL_MINOR_TYPE_CODE " +
	 	          "  AND master_san.BILL_SUB_TYPE_CODE   = master_tran.BILL_SUB_TYPE_CODE " +
	 	          "  AND master_san.EMPLOYEE_ID          = master_tran.EMPLOYEE_ID " +
	 	          "  AND master_san.GPF_NO               = master_tran.GPF_NO " +
	 	          "  AND master_san.designation_id       = master_tran.designation_id " +
	 	          "  AND master_san.office_id            = master_tran.office_id " +
	 	          "  )a " +
	 	          "INNER JOIN " +
	 	          "  (SELECT office_id, " +
	 	          "    SUM(SANCTIONED_AMOUNT) tot_san " +
	 	          "  FROM Hrm_Sanctions_Bills_Link_Mst " +
	 	         sub_qry +
	 	          "  GROUP BY office_id " +
	 	          "  )b " +
	 	          "ON a.office_id=b.office_id " +
	 	          "INNER JOIN fas_bill_major_types fbill " +
	 	          "ON a.bill_major_type_code=fbill.bill_major_type_code " +
	 	          "INNER JOIN fas_bill_minor_types_mst f_min " +
	 	          "ON a.bill_major_type_code =f_min.bill_major_type_code " +
	 	          "AND a.bill_minor_type_code=f_min.bill_minor_type_code " +
	 	          "INNER JOIN fas_bill_sub_types sub " +
	 	          "ON a.bill_major_type_code =sub.bill_major_type_code " +
	 	          "AND a.bill_minor_type_code=sub.bill_minor_type_code " +
	 	          "AND a.bill_sub_type_code  =sub.bill_sub_type_code " +
	 	          "INNER JOIN hrm_mst_employees he " +
	 	          "ON a.employee_id=he.employee_id " +
	 	          "INNER JOIN hrm_mst_designations des " +
	 	          "ON a.designation_id=des.designation_id " +
	 	          "INNER JOIN Com_Mst_Offices OFF " +
	 	          "ON A.Office_Id=Off.Office_Id " +
	 	          ") " +
	 	          "UNION ALL " +
	 	          "  (SELECT a.bill_major_type_code, " +
	 	          "    fbill.bill_major_type_desc major_desc, " +
	 	          "    a.bill_minor_type_code, " +
	 	          "    f_min.bill_minor_type_desc minor_desc, " +
	 	          "    a.bill_sub_type_code, " +
	 	          "    sub.bill_sub_type_desc sub_desc, " +
	 	          "    a.EMPLOYEE_ID, " +
	 	          "    HE. EMPLOYEE_INITIAL " +
	 	          "    ||'.' " +
	 	          "    ||he.EMPLOYEE_NAME AS emp_name, " +
	 	          "    a.gpf_no, " +
	 	          "    a.designation_id, " +
	 	          "    des.designation desig, " +
	 	          "    a.OFFICE_ID, " +
	 	          "    off.office_name office_desc, " +
	 	          "    a.sanction_proc_office_id, " +
	 	          "    (SELECT OFFICE_NAME " +
	 	          "    FROM COM_MST_OFFICES " +
	 	          "    WHERE OFFICE_ID=a.SANCTION_PROC_OFFICE_ID " +
	 	          "    )AS SANC_office_desc, " +
	 	          "    a.SANCTION_PROC_NO, " +
	 	          "    a.SANCTION_PROC_DATE, " +
	 	          "    a.SANCTIONED_AMOUNT, " +
	 	          "    a.PAYMENT_HEAD_OF_AC, " +
	 	          "    a.payment_amount, " +
	 	          "    a.REMARKS , " +
	 	          "    b.tot_san " +
	 	          "  FROM " +
	 	          "    (SELECT master_san.BILL_MAJOR_TYPE_CODE, " +
	 	          "      master_san.bill_minor_type_code, " +
	 	          "      master_san.BILL_SUB_TYPE_CODE, " +
	 	          "      master_san.EMPLOYEE_ID, " +
	 	          "      master_san.GPF_NO, " +
	 	          "      master_san.DESIGNATION_ID, " +
	 	          "      master_san.OFFICE_ID, " +
	 	          "      master_san.SANCTION_PROC_OFFICE_ID, " +
	 	          "      master_san.SANCTION_PROC_NO, " +
	 	          "      master_san.SANCTION_PROC_DATE, " +
	 	          "      master_san.SANCTIONED_AMOUNT, " +
	 	          "      master_tran.PAYMENT_HEAD_OF_AC, " +
	 	          "      master_tran.PAYMENT_AMOUNT, " +
	 	          "      master_tran.REMARKS " +
	 	          "    FROM " +
	 	          "      (SELECT HRMS_SANCTION_ID, " +
	 	          "        BILL_MAJOR_TYPE_CODE, " +
	 	          "        BILL_MINOR_TYPE_CODE, " +
	 	          "        BILL_SUB_TYPE_CODE, " +
	 	          "        EMPLOYEE_ID, " +
	 	          "        GPF_NO, " +
	 	          "        DESIGNATION_ID, " +
	 	          "        OFFICE_ID, " +
	 	          "        SANCTION_PROC_NO, " +
	 	          "        SANCTION_PROC_DATE, " +
	 	          "        SANCTIONED_AMOUNT, " +
	 	          "        SANCTION_PROC_OFFICE_ID " +
	 	          "      FROM Sls_Sanctions_Bills_Link_Mst1 " +
	 	       sub_qry+
	 	          "      ) master_san " +
	 	          "    JOIN " +
	 	          "      (SELECT HRMS_SANCTION_ID, " +
	 	          "        BILL_MAJOR_TYPE_CODE, " +
	 	          "        BILL_MINOR_TYPE_CODE, " +
	 	          "        BILL_SUB_TYPE_CODE, " +
	 	          "        EMPLOYEE_ID, " +
	 	          "        GPF_NO, " +
	 	          "        DESIGNATION_ID, " +
	 	          "        REMARKS, " +
	 	          "        PAYMENT_HEAD_OF_AC, " +
	 	          "        PAYMENT_AMOUNT, " +
	 	          "        OFFICE_ID " +
	 	          "      FROM SLS_SANCTIONS_BILLS_LINK_TRN1 " +
	 	          "      ) master_tran " +
	 	          "    ON master_san.HRMS_SANCTION_ID      = master_tran.HRMS_SANCTION_ID " +
	 	          "    AND master_san.BILL_MAJOR_TYPE_CODE = master_tran.BILL_MAJOR_TYPE_CODE " +
	 	          "    AND master_san.BILL_MINOR_TYPE_CODE = master_tran.BILL_MINOR_TYPE_CODE " +
	 	          "    AND master_san.BILL_SUB_TYPE_CODE   = master_tran.BILL_SUB_TYPE_CODE " +
	 	          "    AND master_san.EMPLOYEE_ID          = master_tran.EMPLOYEE_ID " +
	 	          "    AND master_san.GPF_NO               = master_tran.GPF_NO " +
	 	          "    AND master_san.designation_id       = master_tran.designation_id " +
	 	          "    AND master_san.office_id            = master_tran.office_id " +
	 	          "    )a " +
	 	          "  INNER JOIN " +
	 	          "    (SELECT office_id, " +
	 	          "      SUM(SANCTIONED_AMOUNT) tot_san " +
	 	          "    FROM Sls_Sanctions_Bills_Link_Mst1 " +
	 	        sub_qry+
	 	          "    GROUP BY office_id " +
	 	          "    )b " +
	 	          "  ON a.office_id=b.office_id " +
	 	          "  INNER JOIN fas_bill_major_types fbill " +
	 	          "  ON a.bill_major_type_code=fbill.bill_major_type_code " +
	 	          "  INNER JOIN fas_bill_minor_types_mst f_min " +
	 	          "  ON a.bill_major_type_code =f_min.bill_major_type_code " +
	 	          "  AND a.bill_minor_type_code=f_min.bill_minor_type_code " +
	 	          "  INNER JOIN fas_bill_sub_types sub " +
	 	          "  ON a.bill_major_type_code =sub.bill_major_type_code " +
	 	          "  AND a.bill_minor_type_code=sub.bill_minor_type_code " +
	 	          "  AND a.bill_sub_type_code  =sub.bill_sub_type_code " +
	 	          "  INNER JOIN hrm_mst_employees he " +
	 	          "  ON a.employee_id=he.employee_id " +
	 	          "  INNER JOIN hrm_mst_designations des " +
	 	          "  ON a.designation_id=des.designation_id " +
	 	          "  INNER JOIN Com_Mst_Offices OFF " +
	 	          "  ON A.Office_Id=Off.Office_Id " +
	 	          "  ) " +
	 	          "ORDER BY office_id, bill_major_type_code, " +
	 	          "  Bill_Minor_Type_Code, " +
	 	          "  Bill_Sub_Type_Code, " +
	 	          "  employee_id" ;
	 	            
	 	                }  
	 	                else if(command.equalsIgnoreCase("Type")){
	 	                	System.out.println(Major_Code+""+Minor_Code+" jjjj  "+Sub_type);
	 	                	  if(Major_Code==2 && Minor_Code==2 && (Sub_type==1 || Sub_type==0 )){
	 	                		  head="List of SLS Sanction Proceeding";
	 	                		 tabl_name=" from SLS_SANCTIONS_BILLS_LINK_MST1 "; 
	 	                		tabl_name1=" from SLS_SANCTIONS_BILLS_LINK_TRN1 ";
	 	                	  }  
	 	                	  else{
	 	                		  head="List of GPF Sanction Proceeding";
	 	                		  tabl_name=" from HRM_SANCTIONS_BILLS_LINK_MST ";
	 	                		  tabl_name1="    FROM HRM_SANCTIONS_BILLS_LINK_TRN ";
	 	                	  }
	 	                	  
	 	                		  
	 	                	System.out.println("Bill Major Type Wise");
	 	     	              if(Major_Code!=0 && Minor_Code==0 && Sub_type==0){
	 	     	            	  
                               sub_qry=" WHERE bill_major_type_code               =" +Major_Code+" And TO_CHAR(SANCTION_PROC_DATE,'MM-YYYY')='" +txtCB_Month+"-"+txtCB_Year+"'" ;	 	     	            	
	 	     	              reportFile = new File(getServletContext().getRealPath("/org/FAS/FAS1/Reports/ReceiptSystem/jasper/GPF_san/GPFSanctionAllMajorReport_cp.jasper")); 
	 	     	              }
	 	     	             if(Major_Code!=0 && Minor_Code!=0 && Sub_type==0){
	 	     	            	sub_qry=" WHERE BILL_MAJOR_TYPE_CODE               =" +Major_Code+" and BILL_MINOR_TYPE_CODE="+Minor_Code+" AND TO_CHAR(SANCTION_PROC_DATE,'MM-YYYY')='" +txtCB_Month+"-"+txtCB_Year+"'";
	 	     	            	reportFile = new File(getServletContext().getRealPath("/org/FAS/FAS1/Reports/ReceiptSystem/jasper/GPF_san/GPFSanctionMinorReport_cp.jasper")); 
	 		     	              
	 	     	             }
	 	     	            if(Major_Code!=0 && Minor_Code!=0 && Sub_type!=0){
	 	     	            	sub_qry=" WHERE BILL_MAJOR_TYPE_CODE               =" +Major_Code+" and BILL_MINOR_TYPE_CODE="+Minor_Code+"  and BILL_SUB_TYPE_CODE="+Sub_type+"  AND TO_CHAR(SANCTION_PROC_DATE,'MM-YYYY')='" +txtCB_Month+"-"+txtCB_Year+"'";
	 	     	            	reportFile = new File(getServletContext().getRealPath("/org/FAS/FAS1/Reports/ReceiptSystem/jasper/GPF_san/GPFSanctionSUbTypeReport_cp.jasper")); 
	 		     	              
	 	     	             }
	 	     	           chk_val=sb.toString();
	 	               // }
	 	                
	 	                
	 	                chk_val= "SELECT a.bill_major_type_code, " +
	     	            			"  fbill.bill_major_type_desc major_desc, " +
 	     	            			"  a.bill_minor_type_code, " +
 	     	            			"  f_min.bill_minor_type_desc minor_desc, " +
 	     	            			"  a.bill_sub_type_code, " +
 	     	            			"  sub.bill_sub_type_desc sub_desc, " +
 	     	            			"  a.EMPLOYEE_ID, " +
 	     	            			"  HE. EMPLOYEE_INITIAL " +
 	     	            			"  ||'.' " +
 	     	            			"  ||he.EMPLOYEE_NAME AS emp_name, " +
 	     	            			"  a.gpf_no, " +
 	     	            			"  a.designation_id, " +
 	     	            			"  des.designation desig, " +
 	     	            			"  a.OFFICE_ID, " +
 	     	            			"  off.office_name office_desc, " +
 	     	            			"  a.sanction_proc_office_id, " +
 	     	            			"  (SELECT OFFICE_NAME " +
 	     	            			"  FROM COM_MST_OFFICES " +
 	     	            			"  WHERE OFFICE_ID=a.SANCTION_PROC_OFFICE_ID " +
 	     	            			"  )AS SANC_office_desc, " +
 	     	            			"  a.SANCTION_PROC_NO, " +
 	     	            			"  a.SANCTION_PROC_DATE, " +
 	     	            			"  a.SANCTIONED_AMOUNT, " +
 	     	            			"  a.PAYMENT_HEAD_OF_AC, " +
 	     	            			"  a.payment_amount, " +
 	     	            			"  a.REMARKS , " +
 	     	            			"  b.tot_san " +
 	     	            			"FROM " +
 	     	            			"  (SELECT master_san.BILL_MAJOR_TYPE_CODE, " +
 	     	            			"    master_san.bill_minor_type_code, " +
 	     	            			"    master_san.BILL_SUB_TYPE_CODE, " +
 	     	            			"    master_san.EMPLOYEE_ID, " +
 	     	            			"    master_san.GPF_NO, " +
 	     	            			"    master_san.DESIGNATION_ID, " +
 	     	            			"    master_san.OFFICE_ID, " +
 	     	            			"    master_san.SANCTION_PROC_OFFICE_ID, " +
 	     	            			"    master_san.SANCTION_PROC_NO, " +
 	     	            			"    master_san.SANCTION_PROC_DATE, " +
 	     	            			"    master_san.SANCTIONED_AMOUNT, " +
 	     	            			"    master_tran.PAYMENT_HEAD_OF_AC, " +
 	     	            			"    master_tran.PAYMENT_AMOUNT, " +
 	     	            			"    master_tran.REMARKS " +
 	     	            			"  FROM " +
 	     	            			"    (SELECT HRMS_SANCTION_ID, " +
 	     	            			"      BILL_MAJOR_TYPE_CODE, " +
 	     	            			"      BILL_MINOR_TYPE_CODE, " +
 	     	            			"      BILL_SUB_TYPE_CODE, " +
 	     	            			"      EMPLOYEE_ID, " +
 	     	            			"      GPF_NO, " +
 	     	            			"      DESIGNATION_ID, " +
 	     	            			"      OFFICE_ID, " +
 	     	            			"      SANCTION_PROC_NO, " +
 	     	            			"      SANCTION_PROC_DATE, " +
 	     	            			"      SANCTIONED_AMOUNT, " +
 	     	            			"      SANCTION_PROC_OFFICE_ID " +
 	     	            			tabl_name+
 	     	            			sub_qry+
 	     	            			"    ) master_san " +
 	     	            			"  JOIN " +
 	     	            			"    (SELECT HRMS_SANCTION_ID, " +
 	     	            			"      BILL_MAJOR_TYPE_CODE, " +
 	     	            			"      BILL_MINOR_TYPE_CODE, " +
 	     	            			"      BILL_SUB_TYPE_CODE, " +
 	     	            			"      EMPLOYEE_ID, " +
 	     	            			"      GPF_NO, " +
 	     	            			"      DESIGNATION_ID, " +
 	     	            			"      REMARKS, " +
 	     	            			"      PAYMENT_HEAD_OF_AC, " +
 	     	            			"      PAYMENT_AMOUNT, " +
 	     	            			"      OFFICE_ID " +
 	     	            			tabl_name1 +
 	     	            			"    ) master_tran " +
 	     	            			"  ON master_san.HRMS_SANCTION_ID       = master_san.HRMS_SANCTION_ID " +
 	     	            			"  AND master_san.BILL_MAJOR_TYPE_CODE  = master_san.BILL_MAJOR_TYPE_CODE " +
 	     	            			"  AND master_tran.BILL_MINOR_TYPE_CODE = master_san.BILL_MINOR_TYPE_CODE " +
 	     	            			"  AND master_tran.BILL_SUB_TYPE_CODE   = master_san.BILL_SUB_TYPE_CODE " +
 	     	            			"  AND master_tran.EMPLOYEE_ID          = master_san.EMPLOYEE_ID " +
 	     	            			"  AND master_tran.GPF_NO               = master_san.GPF_NO " +
 	     	            			"  AND master_tran.designation_id       = master_san.designation_id " +
 	     	            			"  AND master_tran.office_id            = master_san.office_id " +
 	     	            			"  )a " +
 	     	            			"INNER JOIN " +
 	     	            			"  (SELECT office_id, " +
 	     	            			"    SUM(SANCTIONED_AMOUNT) tot_san " +
 	     	            		tabl_name+
 	     	            			sub_qry+
 	     	            			"  GROUP BY office_id " +
 	     	            			"  )b " +
 	     	            			"ON a.office_id=b.office_id " +
 	     	            			"INNER JOIN fas_bill_major_types fbill " +
 	     	            			"ON fbill.bill_major_type_code=a.bill_major_type_code " +
 	     	            			"INNER JOIN fas_bill_minor_types_mst f_min " +
 	     	            			"ON f_min.bill_major_type_code =a.bill_major_type_code " +
 	     	            			"AND f_min.bill_minor_type_code=a.bill_minor_type_code " +
 	     	            			"INNER JOIN fas_bill_sub_types sub " +
 	     	            			"ON sub.bill_major_type_code =a.bill_major_type_code " +
 	     	            			"AND sub.bill_minor_type_code=a.bill_minor_type_code " +
 	     	            			"AND sub.bill_sub_type_code  =a.bill_sub_type_code " +
 	     	            			"INNER JOIN hrm_mst_employees he " +
 	     	            			"ON he.employee_id=a.employee_id " +
 	     	            			"INNER JOIN hrm_mst_designations des " +
 	     	            			"ON des.designation_id=a.designation_id " +
 	     	            			"INNER JOIN com_mst_offices OFF " +
 	     	            			"ON off.OFFICE_ID=a.OFFICE_ID " +
 	     	            			"ORDER BY a.office_id, a.EMPLOYEE_ID";
 	    	                	
	 	                	  
	 	                }
	 	                System.out.println("reportFile >>> "+reportFile);
	 	              
	 	            if (!reportFile.exists())
	 	            throw new JRRuntimeException("File J not found. The report design must be compiled first.");
	 	            
	 	            JasperReport jasperReport = (JasperReport)JRLoader.loadObject(reportFile.getPath());
	 	           
	 	            Map map=new HashMap();               
	              
	 	         System.out.println("chk_val >>>> "+chk_val);
	 	            map.put("year",txtCB_Year);
	 	            map.put("month",txtCB_Month);
	 	            map.put("chk_val", chk_val);
	 	            map.put("monthinWords", monthInWords);
	 	            map.put("Major",Major_Code );
	 	            map.put("Minor", Minor_Code);
	 	            map.put("SubType", Sub_type);
	 	            map.put("head", head);
	 	            System.out.println("Map >>>> "+map);
	 	       
	 	           
	 	         
	 	            JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, map, connection);    
	            if (rtype.equalsIgnoreCase("HTML"))   
	            {
	            	
	                        response.setContentType("text/html");
	                        response.setHeader ("Content-Disposition", "attachment;filename=\"GPF Sanction Report.html\"");
	                        PrintWriter out = response.getWriter();
	                        JRHtmlExporter exporter = new JRHtmlExporter();
	                       
	                        exporter.setParameter(JRHtmlExporterParameter.IS_USING_IMAGES_TO_ALIGN,  false);
	                        exporter.setParameter(JRExporterParameter.JASPER_PRINT, jasperPrint);
	                        exporter.setParameter(JRExporterParameter.OUTPUT_WRITER, out);
	                        exporter.exportReport();
	                        out.flush();
	                        out.close();
	            }
	            else if (rtype.equalsIgnoreCase("PDF"))   
	            {
	            	
	            	//System.out.println("inside apdf...");
	                        byte buf[]=JasperExportManager.exportReportToPdf(jasperPrint);
	           
	                        response.setContentType("application/pdf");
	                        response.setContentLength(buf.length);
	                                         
	                        response.setHeader ("Content-Disposition", "attachment;filename=\"GPF Sanction Report.pdf\"");
	                        OutputStream out = response.getOutputStream();
	                        out.write(buf, 0, buf.length);
	                        out.close();
	            }
	            else if (rtype.equalsIgnoreCase("EXCEL"))   
	            {
	    
	            	
	                    	response.setContentType("application/vnd.ms-excel");
		                    response.setHeader ("Content-Disposition", "attachment;filename=\"GPF Sanction Report.xls\"");
		                    JRXlsExporter exporterXLS = new JRXlsExporter(); 
		                    exporterXLS.setParameter(JRXlsExporterParameter.JASPER_PRINT, jasperPrint); 
	                     
	                        ByteArrayOutputStream xlsReport = new ByteArrayOutputStream();
	                        exporterXLS.setParameter(JRXlsExporterParameter.OUTPUT_STREAM,xlsReport); 
	                        exporterXLS.setParameter(JRXlsExporterParameter.IS_ONE_PAGE_PER_SHEET, Boolean.FALSE); 
	                        exporterXLS.setParameter(JRXlsExporterParameter.IS_AUTO_DETECT_CELL_TYPE, Boolean.TRUE); 
		                    exporterXLS.setParameter(JRXlsExporterParameter.IS_WHITE_PAGE_BACKGROUND, Boolean.FALSE); 
		                    exporterXLS.setParameter(JRXlsExporterParameter.IS_REMOVE_EMPTY_SPACE_BETWEEN_ROWS, Boolean.TRUE); 
		                    exporterXLS.exportReport(); 
		                    byte []bytes;
		                    bytes = xlsReport.toByteArray();
		                    ServletOutputStream ouputStream = response.getOutputStream();
		                    ouputStream.write(bytes, 0, bytes.length);
		                    ouputStream.flush();
		                    ouputStream.close();

	            }
	            else if (rtype.equalsIgnoreCase("TXT"))   
	            {
	            	
	            
			                response.setContentType("text/plain");
			                response.setHeader ("Content-Disposition", "attachment;filename=\"GPF Sanction Report.txt\"");
			                     
			                JRTextExporter exporter = new JRTextExporter();
			                exporter.setParameter(JRExporterParameter.JASPER_PRINT, jasperPrint);
			                ByteArrayOutputStream txtReport = new ByteArrayOutputStream();
			                exporter.setParameter(JRExporterParameter.OUTPUT_STREAM,txtReport); 
			                exporter.setParameter(JRTextExporterParameter.CHARACTER_WIDTH, new Integer(200));
			                exporter.setParameter(JRTextExporterParameter.CHARACTER_HEIGHT, new Integer(50));
			                exporter.exportReport(); 
			                
		                    byte []bytes;
		                    bytes = txtReport.toByteArray();
		                    ServletOutputStream ouputStream = response.getOutputStream();
		                    ouputStream.write(bytes, 0, bytes.length);
		                    ouputStream.flush();
		                    ouputStream.close();

	            }
	     
	 	                
	 	                }
	        
	        catch (Exception ex) 
	        {
	            String connectMsg = 
	            "Could not create the report " + ex.getMessage() + " " + 
	            ex.getLocalizedMessage();
	            System.out.println(connectMsg);
	        }
	}

}