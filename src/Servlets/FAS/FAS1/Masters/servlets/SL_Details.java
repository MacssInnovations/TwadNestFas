package Servlets.FAS.FAS1.Masters.servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.CallableStatement;
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
 * Servlet implementation class SL_Details
 */
public class SL_Details extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public SL_Details() {
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
	
		
		PrintWriter out = response.getWriter();
		String strCommand = "";
        Connection con = null;
        ResultSet rs = null,res=null;
        CallableStatement cs = null;
        CallableStatement cs1 = null;
        PreparedStatement ps = null,ps1=null;
		String xml = "";
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

        try {

            strCommand = request.getParameter("command");
            System.out.println("assign..here command..." + strCommand);
        }

        catch (Exception e) {
            System.out.println("Exception in assigning..." + e);
        }
        
        
        
        if (strCommand.equalsIgnoreCase("Add")) {
        	
        	xml = xml + "<response><command>Add</command>";
        	
        	
        	int cmbOffice_code1=0,cmbOffice_code2=0,sl_type=0;
        	int k=0;
        	try {
                cmbOffice_code1 =
                        Integer.parseInt(request.getParameter("cmbOffice_code1"));
            } catch (NumberFormatException e) {
                System.out.println("exception" + e);
            }
            System.out.println("cmbOffice_code1 " + cmbOffice_code1);
        	
            try {
            	cmbOffice_code2 =
                        Integer.parseInt(request.getParameter("cmbOffice_code2"));
            } catch (NumberFormatException e) {
                System.out.println("exception" + e);
            }
            System.out.println("cmbOffice_code2 " + cmbOffice_code2);
            
            
             sl_type=Integer.parseInt(request.getParameter("cmbMas_SL_type"));
             
             if(sl_type==1)
             {
            	 System.out.println("inside 11 Supplier");
            	 try
                 {
            		 ps = con.prepareStatement("insert into COM_SUPPLIER_SL_MST_MAP ( select  ACCOUNTING_UNIT_ID, " +
            				 	" ACCOUNTING_FOR_OFFICE_ID,SUPPLIER_ID,SUPPLIER_ALIAS_ID,SUPPLIER_NAME,SUPPLIER_ADDRESS, " +
            				 	" SUPPLIER_PHONE,SUPPLIER_FAX,SUPPLIER_EMAIL_ID,DATE_OF_REGISTRATION,DATE_OF_LAST_SUPPLY, " +
            				 	" UPDATED_BY_USER_ID,UPDATED_DATE,SUPPLIER_CITY,SUPPLIER_ADDRESS1,PINCODE,STATUS,OLD_OFFICE_ID,"+
            				 	" OLD_SL_CODE,DATE_OF_TRANSFER,TPA_VOUCHER_NO,TPA_VOUCHER_DATE from COM_SUPPLIER_SL_MST WHERE ACCOUNTING_FOR_OFFICE_ID= "+ cmbOffice_code1 +") ORDER BY SUPPLIER_NAME " );
            		 k=ps.executeUpdate();
            		 ps1 = con.prepareStatement("update COM_SUPPLIER_SL_MST_MAP set ACCOUNTING_FOR_OFFICE_ID="+cmbOffice_code2+ "where ACCOUNTING_FOR_OFFICE_ID="+cmbOffice_code1);
                     int k1=ps1.executeUpdate(); 
                     if((k>0)&&(k1>0))
            		 {
            	 		xml = xml + "<flag>success</flag>";
            	 		con.setAutoCommit(true);
            	 		con.commit();
            		 }
             else
             {
            	 con.rollback();
            	 xml = xml + "<flag>fail</flag>";
             }
                     ps.close();
                     ps1.close();
                     
                 }
            	 catch (Exception e) {
     				try {
     					con.rollback();
     				} catch (SQLException e1) {					
     					e1.printStackTrace();
     				}				
     				e.printStackTrace();
     				String sss = e.toString().substring(34, 51);											
     				if(sss.equals("unique constraint"))
     				{
     					xml = xml + "<flag>Exist</flag>";
     				}else{
     					xml = xml + "<flag>fail</flag>";
     				}
     			}
             }
             else if(sl_type==2)
             {
            	 System.out.println("inside 2 Firm");
            	 try
                 {
            		 ps = con.prepareStatement("insert into COM_FIRMS_SL_MST_MAP  ( select  ACCOUNTING_UNIT_ID , " +
            				 "ACCOUNTING_FOR_OFFICE_ID,FIRMS_ID,FIRMS_ALIAS_ID,FIRMS_NAME,FIRMS_ADDRESS,FIRMS_PHONE,FIRMS_FAX, "+
            				 "FIRMS_EMAIL_ID,DATE_OF_REGISTRATION,DATE_OF_LAST_SUPPLY,UPDATED_BY_USER_ID,UPDATED_DATE,FIRMS_ADDRESS1, "+
            				 "FIRMS_CITY,PINCODE,STATUS,OLD_OFFICE_ID,OLD_SL_CODE,DATE_OF_TRANSFER,TPA_VOUCHER_NO,TPA_VOUCHER_DATE " +
            				 " from COM_FIRMS_SL_MST    WHERE ACCOUNTING_FOR_OFFICE_ID= "+ cmbOffice_code1 +") ORDER BY FIRMS_NAME " ); 
            		 k=ps.executeUpdate();
            		 ps1 = con.prepareStatement("update COM_FIRMS_SL_MST_MAP set ACCOUNTING_FOR_OFFICE_ID="+cmbOffice_code2+ "where ACCOUNTING_FOR_OFFICE_ID="+cmbOffice_code1);
                     int k1=ps1.executeUpdate(); 
                     if((k>0)&&(k1>0))
            		 {
            	 		xml = xml + "<flag>success</flag>";
            	 		con.setAutoCommit(true);
            	 		con.commit();
            		 }
                     ps.close();
                     ps1.close();
                 }
            	 catch (Exception e) {
      				try {
      					con.rollback();
      				} catch (SQLException e1) {					
      					e1.printStackTrace();
      				}				
      				e.printStackTrace();
      				String sss = e.toString().substring(34, 51);											
      				if(sss.equals("unique constraint"))
      				{
      					xml = xml + "<flag>Exist</flag>";
      				}else{
      					xml = xml + "<flag>fail</flag>";
      				}
      				
      			}
             }
             else if(sl_type==11)
             {
                 System.out.println("inside 11 contractors");
  
                 try
                 {
                 ps = con.prepareStatement("insert into PMS_CONT_REQUEST_REGN_MAP (select OFFICE_ID,REGN_YEAR, " +
                		 " DATE_OF_REGN,CONTRACTOR_ID,CONTRACTOR_NAME,ADDRESS,PHONE,EMAIL,REGN_CLASS_ID,REGN_STATE_COVERAGE, "+
                		 " REGN_VALID_UPTO,REGN_ALIAS_CODE,UPDATED_BY_USER_ID,UPDATED_DATE,JURISDICTION,STATUS,WING_ID, " +
                		 " OLD_OFFICE_ID,OLD_SL_CODE,DATE_OF_TRANSFER,TPA_VOUCHER_NO,TPA_VOUCHER_DATE,PAN_NO,CONTRACTOR_TYPE,REGN_SLNO " +
                		 " from PMS_CONT_REQUEST_REGN WHERE OFFICE_ID="+cmbOffice_code1+") order by REGN_YEAR");
                 
                  k=ps.executeUpdate();
               
                  
                  ps1 = con.prepareStatement("update PMS_CONT_REQUEST_REGN_MAP set OFFICE_ID="+cmbOffice_code2+ "where OFFICE_ID="+cmbOffice_code1);
                 int k1=ps1.executeUpdate();
             
             if((k>0)&&(k1>0))
            		 {
            	 		xml = xml + "<flag>success</flag>";
            	 		con.setAutoCommit(true);
            	 		con.commit();
            		 }
             else
             {
            	 con.rollback();
            	 xml = xml + "<flag>fail</flag>";
             }
                 }
                 catch (Exception e) {
      				try {
      					con.rollback();
      				} catch (SQLException e1) {					
      					e1.printStackTrace();
      				}				
      				e.printStackTrace();
      				String sss = e.toString().substring(34, 51);											
      				if(sss.equals("unique constraint"))
      				{
      					xml = xml + "<flag>Exist</flag>";
      				}else{
      					xml = xml + "<flag>fail</flag>";
      				}
      			}
             
             }
             
             
             
        }
	
        xml = xml + "</response>";
 		out.write(xml);
 		System.out.println(xml);
	
	}

}
