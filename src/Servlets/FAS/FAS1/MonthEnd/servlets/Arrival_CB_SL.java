package Servlets.FAS.FAS1.MonthEnd.servlets;

import java.io.IOException;
import java.io.PrintWriter;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import java.sql.Statement;

import java.util.ResourceBundle;

import javax.servlet.*;
import javax.servlet.http.*;

public class Arrival_CB_SL extends HttpServlet {
    private static final String CONTENT_TYPE = "text/html; charset=UTF-8";

    public void init(ServletConfig config) throws ServletException {
        super.init(config);
    }
    public void doGet(HttpServletRequest request,HttpServletResponse response) 
    throws ServletException,IOException {
    	System.out.println("get");
    	 PrintWriter out = response.getWriter();
	      response.setHeader("cache-control","no-cache");
	      String CONTENT_TYPE = "text/xml; charset=windows-1252";
	      response.setContentType(CONTENT_TYPE);
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
	      Connection con=null;
	      PreparedStatement ps2=null;        
	      ResultSet rs2=null;
	      String sql="";
	      int txtCB_Year=0,txtCB_Month=0;
	        	      
	      /**
	       * Database Connection 
	      */
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
                    ConnectionString = strdsn.trim() + "://" + strhostname.trim() + ":" + strportno.trim() + "/" +strsid.trim() ;    // Postgres DB  Connection                    Class.forName(strDriver.trim());
                    con=DriverManager.getConnection(ConnectionString,strdbusername.trim(),strdbpassword.trim());
            }
            catch(Exception e)
            {
                	System.out.println("Exception in opening connection :"+e);
            }
        
       
            int count=0,cmbAcc_UnitCode=0;
            String xml=null,cmd="",option="";          
      
            /** Get Employee ID */
            try{cmd=request.getParameter("command");}
            catch(Exception e){System.out.println(e);}
            
            try{cmbAcc_UnitCode=Integer.parseInt(request.getParameter("cmbAcc_UnitCode"));}
            catch(Exception e){System.out.println(e);}
            
            try{txtCB_Year=Integer.parseInt(request.getParameter("txtCB_Year"));}
            catch(Exception e){System.out.println(e);}
            
            try{txtCB_Month=Integer.parseInt(request.getParameter("txtCB_Month"));}
            catch(Exception e){System.out.println(e);}
            
            xml="<response>";
            if(cmd.equalsIgnoreCase("checkAvl"))
            {
                 
                    xml=xml+"<command>checkAvl</command>";
                    sql="Select Accounting_Unit_Id,Financial_Year,Year,Month," +
                    		"Account_Head_Code,(select c.ACCOUNT_HEAD_DESC from com_mst_account_heads c where c.ACCOUNT_HEAD_CODE=s.ACCOUNT_HEAD_CODE)as head_desc,"+
                    		"Sub_Ledger_Type_Code,(select a.SUB_LEDGER_TYPE_DESC  from com_mst_sl_types a where a.sub_ledger_type_code=s.Sub_Ledger_Type_Code)as sldesc," +
                    		" Sub_Ledger_Code," +
                    		"Project_Id,(select SL_CODENAME from sl_type_code_name_view v where v.sl_type=s.Sub_Ledger_Type_Code and v.sl_code=s.sub_ledger_code)as slcodename, " +
                    		"Month_Closing_Balance,Month_Closing_Bal_Dr_Cr_Ind,CREATED_BY_MODULE " +
                    		"From Fas_Sub_Ledger_Master_Cb s Where Accounting_Unit_Id="+cmbAcc_UnitCode+" And Year= " +txtCB_Year+"and MONTH="+txtCB_Month;                    
                    System.out.println(" SQL :: "+sql);
                    try
                    {
                             ps2=con.prepareStatement(sql);
                             
                             rs2=ps2.executeQuery();                                 
                             while(rs2.next()) 
                             {
                                     xml+= "<unit_id>"+ rs2.getInt("ACCOUNTING_UNIT_ID") +"</unit_id>";	 
                                     xml+= "<finyear>"+ rs2.getString("Financial_Year") +"</finyear>"; 
                                     xml+= "<year>"+ rs2.getInt("Year") +"</year>"; 
                                     xml+= "<Month>"+ rs2.getInt("Month") +"</Month>";
                                     xml+= "<hcode>"+ rs2.getInt("Account_Head_Code") +"</hcode>";
                                     xml+= "<head_desc>"+ rs2.getString("head_desc") +"</head_desc>";
                                     xml+= "<sltype>"+ rs2.getString("sldesc") +"</sltype>";
                                     xml+= "<slcode>"+ rs2.getString("slcodename") +"</slcode>";
                                     xml+= "<projectId>"+ rs2.getInt("Project_Id") +"</projectId>";
                                     xml+= "<cb>"+ rs2.getDouble("Month_Closing_Balance") +"</cb>";
                                     xml+= "<indicator>"+ rs2.getString("Month_Closing_Bal_Dr_Cr_Ind") +"</indicator>";
                                     xml+= "<createdbymodule>"+ rs2.getString("CREATED_BY_MODULE") +"</createdbymodule>";
                                     count++;
                             }					              
                             if(count==0)
                                     xml+="<flag>NoData</flag>";					           
                             else               
                                     xml+="<flag>success</flag>";
                                         
                    }
                    catch(Exception e) 
                    {
                             System.out.println("Exception in checkAvailability..."+e);
                             xml+="<flag>"+e.getMessage()+"</flag>";
                    }                      
            }
            xml=xml+"</response>";
            System.out.println(xml);
            out.println(xml);
            out.close();
    	
    }
   
    public void doPost(HttpServletRequest request,
                       HttpServletResponse response) throws ServletException,
                                                            IOException {
        response.setContentType(CONTENT_TYPE);
        PrintWriter out = response.getWriter();


        CallableStatement cs1 = null;
        Statement statement = null;
        Connection connection = null;

        PreparedStatement tbcheck_ps = null;
        ResultSet tbcheck_rs = null;


        /**
         * DataBase Connection
         */
        try {
            ResourceBundle rs =
                ResourceBundle.getBundle("Servlets.Security.servlets.Config");
            String ConnectionString = "";

            String strDriver = rs.getString("Config.DATA_BASE_DRIVER");
            String strdsn = rs.getString("Config.DSN");
            String strhostname = rs.getString("Config.HOST_NAME");
            String strportno = rs.getString("Config.PORT_NUMBER");
            String strsid = rs.getString("Config.SID");
            String strdbusername = rs.getString("Config.USER_NAME");
            String strdbpassword = rs.getString("Config.PASSWORD");

            ConnectionString = strdsn.trim() + "://" + strhostname.trim() + ":" + strportno.trim() + "/" +strsid.trim() ;    // Postgres DB  Connection

            Class.forName(strDriver.trim());
            connection =
                    DriverManager.getConnection(ConnectionString, strdbusername.trim(),
                                                strdbpassword.trim());
            try {
                statement = connection.createStatement();
                connection.clearWarnings();
            } catch (SQLException e) {
                System.out.println("Exception in creating statement:" + e);
            }
        } catch (Exception e) {
            System.out.println("Exception in openeing connection:" + e);
        }


        /**
         * Session Checking
         */
        response.setContentType(CONTENT_TYPE);
        HttpSession session = request.getSession(false);
        try {

            if (session == null) {
                System.out.println(request.getContextPath() + "/index.jsp");
                response.sendRedirect(request.getContextPath() + "/index.jsp");

            }
            System.out.println(session);

        } catch (Exception e) {
            System.out.println("Redirect Error :" + e);
        }


        /**
         * Get User ID
         */
        String userid = (String)session.getAttribute("UserId");
        System.out.println("session id is:" + userid);

        /**
         * Get Parameters
         */
        String Account_unit_Code = request.getParameter("cmbAcc_UnitCode");
        String CashBook_Year = request.getParameter("txtCB_Year");
        String CashBook_Month = request.getParameter("txtCB_Month");

        int AccountUnitCode = 0;
        int CashBookYear = 0;
        int CashBookMonth = 0;
        int error_code = 0;
        String update_user = (String)session.getAttribute("UserId");


        try {
            AccountUnitCode = Integer.parseInt(Account_unit_Code);
            CashBookYear = Integer.parseInt(CashBook_Year);
            CashBookMonth = Integer.parseInt(CashBook_Month);
        } catch (Exception e) {
            System.out.println("Exception in Converting Integer:" + e);
        }


        try {
            String sql =
                "" + "select                  	       	\n" + "    accounting_unit_id     	    \n" +
                "from fas_sl_cb_status          	\n" +
                "where                          	\n" +
                "    ACCOUNTING_UNIT_ID = ? and    	\n" +
                "    CASHBOOK_YEAR = ? and          \n" +
                "    CASHBOOK_MONTH = ?          ";
            tbcheck_ps = connection.prepareStatement(sql);
            tbcheck_ps.setInt(1, AccountUnitCode);
            tbcheck_ps.setInt(2, CashBookYear);
            tbcheck_ps.setInt(3, CashBookMonth);
            tbcheck_rs = tbcheck_ps.executeQuery();
            int cnt = 0;
            while (tbcheck_rs.next()) {
                cnt++;
            }
            if (cnt > 0) {
                sendMessage(response,
                            "Sorry! Sub Ledger Closing Balance has been Frozen",
                            "ok");
                return;
            }

        } catch (SQLException e) {
            System.out.println(e);
        }


        try {
            cs1 =
 connection.prepareCall("call FAS_ARRIVAL_CB_SL(?::numeric,?::numeric,?::numeric,?,?::numeric) ");
            cs1.setInt(1, AccountUnitCode);
            cs1.setInt(2, CashBookMonth);
            cs1.setInt(3, CashBookYear);
            cs1.setString(4, update_user);
            cs1.registerOutParameter(5, java.sql.Types.NUMERIC);
            //cs1.registerOutParameter(5, java.sql.Types.NUMERIC);
            cs1.setNull(5, java.sql.Types.NUMERIC);
            cs1.execute();
            //error_code = cs1.getInt(5);
            error_code = cs1.getBigDecimal(5).intValue();

            System.out.println("error_code------------------------<><>" +
                               error_code);

            if (error_code == 0) {
                sendMessage(response, "Arrival of CB Done Successfully", "ok");
            } else {
                sendMessage(response, "Arrival of CB failed", "ok");
            }
        } catch (SQLException e) {
            System.out.println(e);
        }

        out.close();
    }

    private void sendMessage(HttpServletResponse response, String msg,
                             String bType) {
        try {
            String url =
                "org/Library/jsps/Messenger.jsp?message=" + msg + "&button=" +
                bType;
            response.sendRedirect(url);
        } catch (IOException e) {
            System.out.println("ERROR");
        }
    }

}
