package Servlets.FAS.FAS1.MonthEnd.servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ResourceBundle;

import javax.servlet.*;
import javax.servlet.http.*;

public class UnFreezeTB_SJV extends HttpServlet {
    private static final String CONTENT_TYPE =
        "text/html; charset=windows-1252";

    public void init(ServletConfig config) throws ServletException {
        super.init(config);
    }

    public void doPost(HttpServletRequest request,
                       HttpServletResponse response) throws ServletException,
                                                            IOException {

System.out.println("servlet filesssssssssssssssssssssssssssssssssssssssssssssssssssssssssss");
        System.out.println("I am in sjv Unfreeze TB ------>>><><><>< ");

        /**
         * Variables Declaration
         */
        Connection con = null;
        Statement statement = null;


        /**
         * Database Connection
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
            con =
 DriverManager.getConnection(ConnectionString, strdbusername.trim(),
                             strdbpassword.trim());
            try {
                statement = con.createStatement();
                con.clearWarnings();
            } catch (SQLException e) {
                System.out.println("Exception in creating statement:" + e);
            }
        } catch (Exception e) {
            System.out.println("Exception in openeing con:" + e);
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
         * Variables Declaration
         */
        int txtCB_Year = 0, txtCB_Month = 0, cmbAcc_UnitCode = 0;
       
        String radTB_status = "";


        /**
         * Get Accounting Unit ID
         */
        try {
            cmbAcc_UnitCode =
                    Integer.parseInt(request.getParameter("cmbAcc_UnitCode"));
        } catch (NumberFormatException e) {
            System.out.println("exception" + e);
        }
        System.out.println("cmbAcc_UnitCode " + cmbAcc_UnitCode);


        /**
         * Get Cashbook Month and Year
         */
        txtCB_Year = Integer.parseInt(request.getParameter("txtCB_Year"));
        txtCB_Month = Integer.parseInt(request.getParameter("txtCB_Month"));
        System.out.println("year..." + txtCB_Year);
        System.out.println("Month..." + txtCB_Month);


        /**
         *  The variables 'radTB_status' will be always 'N' because this menu is exclusive for Unfreezif TB
         */
        radTB_status = request.getParameter("radTB_status");
        System.out.println("radTB_status..." + radTB_status);


        /**
         * Get User ID who is unfreezing TB
         */
        String userid = (String)session.getAttribute("UserId");


        /**
         * Get Time of Unfreezing TB
         */
        long l = System.currentTimeMillis();
        Timestamp ts = new Timestamp(l);

        /** Get Supplement Number */
        int Supplement_No = 0;
        try {
            Supplement_No =
                    Integer.parseInt(request.getParameter("txtsupplement_no"));
        } catch (Exception e) {
            System.out.println(e);
        }
        //int nextSup=Supplement_No+1;
        int prevsupCount=0;
        int one=0,two=0;
System.out.println("Supplement_No"+Supplement_No);
        try {
            /** Set Auotcommit to be false */
            con.setAutoCommit(false);


            /** Variables Declaration */
            PreparedStatement ps = null;
            PreparedStatement ps2 = null,ps3=null,ps4=null;
            String msg = " ";
            System.out.println("be4   radTB_statusssssssss"+radTB_status);

            try {
                
 	    	   String sql_two="select SUPPLEMENT_NO from FAS_TRIAL_BALANCE_STATUS_SJV where " +
 	    	   		"ACCOUNTING_UNIT_ID="+cmbAcc_UnitCode+" and CASHBOOK_YEAR="+txtCB_Year+" and CASHBOOK_MONTH="+txtCB_Month+" and SUPPLEMENT_NO="+Supplement_No;
 	    	   System.out.println("sql_two::::"+sql_two);
                ps=con.prepareStatement(sql_two);
              ResultSet  results=ps.executeQuery();
                if(results.next())
                {
             	   prevsupCount++;
             	   System.out.println("prev:::::"+ results.getInt("SUPPLEMENT_NO"));
                }
                results.close();
                ps.close();   
                
            }
            catch(Exception e) {
                System.out.println(e);
            }
 		   
 	   
            if(prevsupCount>0){
            
            
            if (radTB_status.equalsIgnoreCase("N")) {
            System.out.println("radTB_statusssssssss"+radTB_status);

                ps = con.prepareStatement("delete from FAS_TRIAL_BALANCE_STATUS_SJV where  ACCOUNTING_UNIT_ID=? and CASHBOOK_YEAR=? and CASHBOOK_MONTH=? and SUPPLEMENT_NO=? ");
                ps.setInt(1, cmbAcc_UnitCode);
                ps.setInt(2, txtCB_Year);
                ps.setInt(3, txtCB_Month);
                ps.setInt(4, Supplement_No);
                one=ps.executeUpdate();
                ps.close();
                
                if(one>0)
                {
                	int Memo_delete=0;
                	PreparedStatement prs2 = con
							.prepareStatement("select VERIFY_FLAG from FAS_SCH_EXP_VERIFY_MONTH_SJV where ACCOUNTING_UNIT_ID="
									+ cmbAcc_UnitCode
									+ " and "
									+ "CASHBOOK_YEAR="
									+ txtCB_Year
									+ " and CASHBOOK_MONTH::numeric="
									+ txtCB_Month
									+"and SUPPLEMENT_NO="
									+Supplement_No);
					ResultSet res2 = prs2.executeQuery();
					
					if(res2.next()) {
						

						PreparedStatement prep1 = con
								.prepareStatement("delete from FAS_SCH_EXP_VERIFY_MONTH_SJV where  ACCOUNTING_UNIT_ID=? and CASHBOOK_YEAR=? and CASHBOOK_MONTH::numeric=? and SUPPLEMENT_NO=? ");
						prep1.setInt(1, cmbAcc_UnitCode);
						prep1.setInt(2, txtCB_Year);
						prep1.setInt(3, txtCB_Month);
						prep1.setInt(4, Supplement_No);
						Memo_delete = prep1.executeUpdate();
						prep1.close();

					}
					
//                	if(Memo_delete>0)
//                	{
                	ps4 = con.prepareStatement("Update FAS_TB_SUS_UNFREEZE_REQUEST set STATUS='C' where  ACCOUNTING_UNIT_ID=? and CASHBOOK_YEAR=? and CASHBOOK_MONTH=? and SUPPLEMENT_NO=? and STATUS='O'");
                	 ps4.setInt(1, cmbAcc_UnitCode);
                	 ps4.setInt(2, txtCB_Year);
                	 ps4.setInt(3, txtCB_Month);
                	 ps4.setInt(4, Supplement_No);
                	two=ps4.executeUpdate();
                	 ps4.close();
//                	}
                	
                	 System.out.println("two===>"+two);
                
                if(two>0)
                {
                ps3 = con.prepareStatement("delete from FAS_TRIAL_BALANCE_SJV_CMP where  ACCOUNTING_UNIT_ID=? and CASHBOOK_YEAR=? and CASHBOOK_MONTH=? and SUPPLEMENT_NO=? ");
                ps3.setInt(1, cmbAcc_UnitCode);
                ps3.setInt(2, txtCB_Year);
                ps3.setInt(3, txtCB_Month);
                ps3.setInt(4, Supplement_No);
                ps3.executeUpdate();
                ps3.close();

                ps2 =
                	 con.prepareStatement("insert into FAS_TRIAL_BALANCE_SJV_LOG ( ACCOUNTING_UNIT_ID, ACCOUNTING_FOR_OFFICE_ID, CASHBOOK_YEAR,  CASHBOOK_MONTH , TB_STATUS, TB_UNFREEZE_DATE ,  UPDATED_BY_USER_ID , UPDATED_DATE, SUPPLEMENT_NO ) values(?,?,?,?,?,?,?,?,?)");
                ps2.setInt(1, cmbAcc_UnitCode);
                ps2.setInt(2, 0);
                ps2.setInt(3, txtCB_Year);
                ps2.setInt(4, txtCB_Month);
                ps2.setString(5, "Y");
                ps2.setTimestamp(6, ts);
                ps2.setString(7, userid);
                ps2.setTimestamp(8, ts);
                ps2.setInt(9, Supplement_No);

                ps2.executeUpdate();
                ps2.close();
                System.out.println("executedddddddddddddddddd");
                
                
                
                
                msg ="Supplement Trial Balance Froze Status is Removed... You have to Generate Supplement Trial Balance again for this Accounting Unit ";
                }
            }
                else
                {
                	 String msgs =
                         "Supplement Trial Balance Status Changing is Unsuccessful.......";
                     msgs = msgs + "<br><br>";
                     sendMessage(response, msgs, "ok");
                }
            }
        }
        /*else
        {
        	msg ="Please UnFreeze Supplement No "+Supplement_No;
        }
*/
            else
            {
            	msg="Cannot Unfreeze TB,First Freeze TB for CASHBOOK_YEAR="+txtCB_Year+" and CASHBOOK_MONTH="+txtCB_Month;
            }
            	System.out.println("msg"+msg);
            /** Commit the database */
            con.commit();
            con.setAutoCommit(true);

            
            msg = msg + "<br><br>";
            sendMessage(response, msg, "ok");

        } catch (Exception e) {
            try {
                con.rollback();
            } catch (SQLException e1) {
                System.out.println("exception in rollback" + e1);
            }
            System.out.println("Exception in Trial balance " + e);
            String msg =
                "Supplement Trial Balance Status Changing is Unsuccessful.......";
            msg = msg + "<br><br>";
            sendMessage(response, msg, "ok");
        }


    }

    private void sendMessage(HttpServletResponse response, String msg,
                             String bType) {
        try {
            String url =
                "org/Library/jsps/Messenger.jsp?message=" + msg + "&button=" +
                bType;
            response.sendRedirect(url);
        } catch (IOException e) {
            System.out.println(e);
        }
    }

}

