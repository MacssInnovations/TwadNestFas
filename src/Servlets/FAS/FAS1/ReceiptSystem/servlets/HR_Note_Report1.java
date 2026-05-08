package Servlets.FAS.FAS1.ReceiptSystem.servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ResourceBundle;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import Servlets.Security.classes.UserProfile;


public class HR_Note_Report1 extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private String CONTENT_TYPE = "text/xml; charset=windows-1252";
    public HR_Note_Report1() {
        super();
        // TODO Auto-generated constructor stub
    }

	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {



        try {
            HttpSession session = request.getSession(false);
            if (session == null) {

                System.out.println(request.getContextPath() + "/index.jsp");
                response.sendRedirect(request.getContextPath() + "/index.jsp");
                return;
            }
            System.out.println(session);

        } catch (Exception e) {
            System.out.println("Redirect Error :" + e);
        }


        int employee_id = 0;

        HttpSession session = request.getSession(false);
        UserProfile empProfile =
            (UserProfile)session.getAttribute("UserProfile");
        System.out.println("user id::" + empProfile.getEmployeeId());
        employee_id = empProfile.getEmployeeId();
        int old_id=0;

        Connection con = null;
        ResultSet rs = null, rs2 = null, rs3 = null, rsbank = null,rs33=null;
        PreparedStatement ps = null, ps2 = null, ps3 = null, psbank = null,ps33=null;
        //String xml="";
        response.setContentType(CONTENT_TYPE);
        response.setHeader("Cache-Control", "no-cache");
        PrintWriter out = response.getWriter();
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
            ConnectionString =
                    strdsn.trim() + "@" + strhostname.trim() + ":" + strportno.trim() +
                    ":" + strsid.trim();
            Class.forName(strDriver.trim());
            con =
 DriverManager.getConnection(ConnectionString, strdbusername.trim(),
                             strdbpassword.trim());
        } catch (Exception e) {
            System.out.println("Exception in opening connection :" + e);
            //sendMessage(response,"probably Failed to Establish connection to the database server.. due to "+e,"ok");

        }

        try {
            strCommand = request.getParameter("Command");
            System.out.println("hkjfkfsdjfklsdfjsl..." + strCommand);

        }

        catch (Exception e) {
            System.out.println("Exception in assigning..." + e);
        }
    
       /* if(strCommand.equalsIgnoreCase("txtCB_Month")){
        	  int cmbOffice_code = 0;
        	System.out.println("hai");
        	
        	int cmbAcc_UnitCode = Integer.parseInt(request.getParameter("cmbAcc_UnitCode"));
        	
        	cmbOffice_code =Integer.parseInt(request.getParameter("cmbOffice_code"));

        	int txtCB_Year = Integer.parseInt(request.getParameter("txtCB_Year"));

        	int txtCB_Month = Integer.parseInt(request.getParameter("txtCB_Month"));
        	
        	
        	
        	
        }*/
            
        if (strCommand.equalsIgnoreCase("searchByMonth")) {

            String CONTENT_TYPE = "text/xml; charset=windows-1252";
            response.setContentType(CONTENT_TYPE);
            String xml = "";
            xml = "<response><command>" + strCommand + "</command>";
            int cmbOffice_code=0;
       

           int cmbAcc_UnitCode   =
                        Integer.parseInt(request.getParameter("cmbAcc_UnitCode"));
System.out.print(cmbAcc_UnitCode);

            	cmbOffice_code =Integer.parseInt(request.getParameter("cmbOffice_code"));
            	int txtCB_Year11 = Integer.parseInt(request.getParameter("txtCB_Year"));

            	int txtCB_Month11 = Integer.parseInt(request.getParameter("txtCB_Month"));
            	

          

        

                try {
                    ps = con.prepareStatement("SELECT f.HR_NOTE_NO, " +
                    		"  TO_CHAR(f.NOTE_DATE,'dd/mm/yyyy') AS NOTE_DATE, " +
                    		"  f1.bill_major_type_desc, " +
                    		"  f2.bill_minor_type_desc, " +
                    		"  (SELECT f3.bill_sub_type_desc " +
                    		"  FROM FAS_BILL_SUB_TYPES f3 " +
                    		"  WHERE f.bill_major_type_code =f3.bill_major_type_code " +
                    		"  AND f.bill_minor_type_code   =f3.bill_minor_type_code " +
                    		"  AND f.bill_sub_type_code     =f3.bill_sub_type_code " +
                    		"  ) AS bill_sub_type_desc , " +
                    		"  f.NOTE_AMOUNT, " +
                    		"  f.NOTE_PREPARED_BY, " +
                    		"  f.ACCOUNT_HEAD_CODE " +
                    		" FROM FAS_HR_NOTE_DETAILS f " +
                    		" INNER JOIN FAS_BILL_MAJOR_TYPES f1 " +
                    		" ON f.BILL_MAJOR_TYPE_CODE=f1.BILL_MAJOR_TYPE_CODE " +
                    		" INNER JOIN FAS_BILL_MINOR_TYPES_MST f2 " +
                    		" ON f.bill_major_type_code  =f2.bill_major_type_code " +
                    		" AND f.bill_minor_type_code =f2.bill_minor_type_code " +
                    		" AND ACCOUNTING_UNIT_ID     =? " +
                    		" AND CASHBOOK_MONTH         =? " +
                    		" AND CASHBOOK_YEAR          =?");
                   ps.setInt(1, cmbAcc_UnitCode);
                   System.out.println(cmbAcc_UnitCode+cmbOffice_code+txtCB_Month11+txtCB_Month11);
               //    ps.setInt(2, cmbOffice_code);
                   ps.setInt(3 ,txtCB_Year11); 
                   ps.setInt(2, txtCB_Month11); 
                   rs= ps.executeQuery();
                    while (rs.next()) {
                        xml =xml + "<flag>success</flag><HR_NOTE_NO>" +
					   rs.getInt("HR_NOTE_NO") + "</HR_NOTE_NO><NOTE_DATE>" +
					   rs.getString("NOTE_DATE") + "</NOTE_DATE><bill_major_type_desc>" +
					   rs.getString("bill_major_type_desc")	 + "</bill_major_type_desc><bill_minor_type_desc>" +
					   rs.getString("bill_minor_type_desc") + "</bill_minor_type_desc><bill_sub_type_desc>" +
					   rs.getString("bill_sub_type_desc") + "</bill_sub_type_desc><NOTE_AMOUNT>" +
					   rs.getBigDecimal("NOTE_AMOUNT")	 + "</NOTE_AMOUNT><NOTE_PREPARED_BY>" +
					   rs.getString("NOTE_PREPARED_BY") + "</NOTE_PREPARED_BY><ACCOUNT_HEAD_CODE>" +
					   rs.getInt("ACCOUNT_HEAD_CODE") + "</ACCOUNT_HEAD_CODE>" ;			   
					   
					   
					  
                        
                        }
                }
                        	
                   



             catch (Exception e) {
                System.out.println("catch..HERE.in TB_date " + e);
                xml = xml + "<flag>failure</flag>";
            }
            xml = xml + "</response>";
            System.out.println(xml);
            out.println(xml);
        }
    
	}

	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

}
