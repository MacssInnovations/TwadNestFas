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

public class UnFreezeTB extends HttpServlet {
    private static final String CONTENT_TYPE =
        "text/html; charset=windows-1252";

    public void init(ServletConfig config) throws ServletException {
        super.init(config);
    }

    public void doPost(HttpServletRequest request,
                       HttpServletResponse response) throws ServletException,
                                                            IOException {

        /**
         * Variables Declaration
         */
        Connection con = null;
        Statement statement = null;
        PreparedStatement ps3 = null,prs=null;
        ResultSet rs3 = null;


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
//            ConnectionString =
//                    strdsn.trim() + "@" + strhostname.trim() + ":" + strportno.trim() +
//                    ":" + strsid.trim();
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

        int	tda_delete=0;  int Memo_delete=0;
        /**
         * Variables Declaration
         */
        int txtCB_Year = 0, txtCB_Month = 0, cmbAcc_UnitCode = 0,newyr=0,newmnth=0;
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
         * Get User ID who is unfreezing TB
         */
        String userid = (String)session.getAttribute("UserId");


        /**
         * Get Time of Unfreezing TB
         */
        long l = System.currentTimeMillis();
        Timestamp ts = new Timestamp(l);


        /** Check Trial Balance Closure table --'FAS_TB_CLOSURE'
         *  If Record exits in FAS_TB_CLOSURE table, You cant allow TB to Unfreeze
         */

        int count = 0,count_gl=0,count_sl=0,count1=0,count2=0;
        try {
            con.setAutoCommit(false);
            ps3 =
 con.prepareStatement("select * from fas_tb_closure where cashbook_year=?  and  cashbook_month= ?  and  tb_status='Y' ");
            ps3.setInt(1, txtCB_Year);
            ps3.setInt(2, txtCB_Month);
            rs3 = ps3.executeQuery();
            if (rs3.next()) {
                System.out.println("3");
                count++;
            }
            if (count > 0) {
                sendMessage(response,
                            "Sorry !  You can't Unfreeze Trial Balance.   TB Closure has already been Frozen",
                            "ok");
                return;
            }

        } catch (Exception e) {
            System.out.println("Error in TB Closure " + e);
        }
        try {
        	 if(txtCB_Month==12){
             	newyr=txtCB_Year+1;
             	newmnth=1;
             }else{
             	newyr=txtCB_Year;
             	newmnth=txtCB_Month+1;
             }
        	
            con.setAutoCommit(false);
            ps3 = con.prepareStatement("Select Tb_Status,Cashbook_Year,CASHBOOK_MONTH From Fas_Trial_Balance_Status Where Cashbook_Year>=? and Cashbook_Month >=? and ACCOUNTING_UNIT_ID=? ");
            ps3.setInt(1, newyr);
            ps3.setInt(2, newmnth);
            ps3.setInt(3, cmbAcc_UnitCode);
            rs3 = ps3.executeQuery();
            if (rs3.next()) {
                System.out.println("333333");
                count1++;
            }
            System.out.println(" count1  "+count1);
            if (count1 > 0) {
                sendMessage(response,
                            "Cannot Unfreeze TB for this month.......   First  Unfreeze TB for the subsequent months",
                            "ok");
                return;
            }	//Lakshmi start
            else{
       

                try {
               	 
                    if(txtCB_Month==12){
                    	newyr=txtCB_Year+1;
                    	newmnth=1;
                    }else{
                    	newyr=txtCB_Year;
                    	newmnth=txtCB_Month+1;
                    }
                	PreparedStatement ps=null;
                    con.setAutoCommit(false);
                    ps =con.prepareStatement("Select Tb_Status,Cashbook_Year,CASHBOOK_MONTH,SUPPLEMENT_NO From FAS_TRIAL_BALANCE_STATUS_SJV Where Cashbook_Year>=? and Cashbook_Month>=? and ACCOUNTING_UNIT_ID=? ");
                    ps.setInt(1, newyr);
                    ps.setInt(2, newmnth);
                    ps.setInt(3, cmbAcc_UnitCode);
                    rs3 = ps.executeQuery();
                    if (rs3.next()) {
                        System.out.println("supplement no   ");
                        count2++;
                    }
                    if (count2 > 0) {
                        sendMessage(response,
                                    "Cannot Unfreeze TB for this month.......   First  Unfreeze TB for the supplement ",
                                    "ok");
                        return;
                    }

                } catch (Exception e) {
                    System.out.println("Error in TB supplement status ********* " + e);
                }
   	
         
         
            }
            //Lakshmi end 

        } catch (Exception e) {
            System.out.println("Error in TB status ********* " + e);
        }
      
        
        try {
            con.setAutoCommit(false);
            ps3 =con.prepareStatement("select * from FAS_GL_PBSTATUS where ACCOUNTING_UNIT_ID=? and Cashbook_Year=? and CASHBOOK_MONTH=? and GL_PB_FREEZE_STATUS='Y'");
            ps3.setInt(1, cmbAcc_UnitCode);
            ps3.setInt(2, txtCB_Year);
            ps3.setInt(3, txtCB_Month);
            rs3 = ps3.executeQuery();
            if (rs3.next()) {
                System.out.println("cant unfreeze");
                count_gl++;
            }
           

        } catch (Exception e) {
            System.out.println("Error in TB Closure " + e);
        }
        System.out.println("count_gl "+count_gl);
        if (count_gl > 0) {
            sendMessage(response,
                        "Sorry !  You can't Unfreeze TB.<br>PB for This Month is Already Freezed for the Selected Unit",
                        "ok");
            return;
        }
        else{

        	try {
                con.setAutoCommit(false);
                ps3 =con.prepareStatement("select * from FAS_SL_PBSTATUS where ACCOUNTING_UNIT_ID=? and Cashbook_Year=? and CASHBOOK_MONTH=? and SL_PB_FREEZE_STATUS='Y'");
                ps3.setInt(1, cmbAcc_UnitCode);
                ps3.setInt(2, txtCB_Year);
                ps3.setInt(3, txtCB_Month);
                rs3 = ps3.executeQuery();
                if (rs3.next()) {
                    System.out.println("cant unfreeze");
                    count_sl++;
                }
               

            } catch (Exception e) {
                System.out.println("Error in TB Closure " + e);
            }
            System.out.println("count_sl-->"+count_sl);
            if (count_sl > 0) {
                sendMessage(response,
                            "Sorry !  You can't Unfreeze TB.<br>PB for This Month is Already Freezed for the Selected Unit",
                            "ok");
                return;
            }
	            else{
	            	
	            	
	        	
				        try {
				
				            /** Variables Declaration */
				            PreparedStatement ps = null;
				            PreparedStatement ps2 = null;
				            String msg = " ";
				System.out.println("welcome else "+cmbAcc_UnitCode+"--"+txtCB_Year+"--"+txtCB_Month);
			String sql1="delete from FAS_TRIAL_BALANCE_STATUS where  ACCOUNTING_UNIT_ID=? and CASHBOOK_YEAR=? and CASHBOOK_MONTH=?";
				            ps =
				  con.prepareStatement(sql1);
				           
				            ps.setInt(1, cmbAcc_UnitCode);
				            ps.setInt(2, txtCB_Year);
				            ps.setInt(3, txtCB_Month);
				            int del=ps.executeUpdate();
				            ps.close();
				
				            	System.out.println("del--->"+del);
				            ps2 =
				    				 con.prepareStatement("insert into FAS_TRIAL_BALANCE_LOG ( ACCOUNTING_UNIT_ID, ACCOUNTING_FOR_OFFICE_ID, CASHBOOK_YEAR,  CASHBOOK_MONTH , TB_STATUS, TB_UNFREEZE_DATE ,  UPDATED_BY_USER_ID , UPDATED_DATE ) values(?,?,?,?,?,?,?,?)");
				            ps2.setInt(1, cmbAcc_UnitCode);
				            ps2.setInt(2, 0);
				            ps2.setInt(3, txtCB_Year);
				            ps2.setInt(4, txtCB_Month);
				            ps2.setString(5, "Y");
				            ps2.setTimestamp(6, ts);
				            ps2.setString(7, userid);
				            ps2.setTimestamp(8, ts);
				            ps2.executeUpdate();
				            ps2.close();
				            
				            
				            PreparedStatement ps4 =con.prepareStatement("update FAS_TB_UNFREEZE_REQUEST set STATUS='C' where  ACCOUNTING_UNIT_ID=? and CASHBOOK_YEAR=? and CASHBOOK_MONTH=?");
				            ps4.setInt(1, cmbAcc_UnitCode);
				            ps4.setInt(2, txtCB_Year);
				            ps4.setInt(3, txtCB_Month); 
				            int sss=ps4.executeUpdate();
				            System.out.println("yes::::YYYY::"+sss);
							if(del>0)
							{
								System.out.println("sdel Entered..........");
								if (cmbAcc_UnitCode!=5) {								
								
								prs=con.prepareStatement("select VERIFY_FLAG from FAS_TDA_TCA_MONTHEND where ACCOUNTING_UNIT_ID="+cmbAcc_UnitCode+" and " +
										"CASHBOOK_YEAR="+txtCB_Year+" and CASHBOOK_MONTH::numeric="+txtCB_Month);
								ResultSet res=prs.executeQuery();
								
								if(res.next())
								{
									System.out.println("while Entered");
									PreparedStatement prep =con.prepareStatement("delete from FAS_TDA_TCA_MONTHEND where  ACCOUNTING_UNIT_ID=? and CASHBOOK_YEAR=? and CASHBOOK_MONTH::numeric=?");
									prep.setInt(1, cmbAcc_UnitCode);
									prep.setInt(2, txtCB_Year);
									prep.setInt(3, txtCB_Month);
							         tda_delete=prep.executeUpdate();
							         
							         System.out.println("tda_delete ----- > "+tda_delete);
								}
								if(tda_delete>0)
								{
									Memo_delete = 0;
									
									System.out.println("txtCB_Year============>"+txtCB_Year);
							if ((txtCB_Year == 2015 && txtCB_Month > 10)
									|| txtCB_Year > 2015) {
								System.out.println("txtCB_Year============>"+(txtCB_Year>2015));

								
								if (cmbAcc_UnitCode != 3
										|| cmbAcc_UnitCode != 5
										|| cmbAcc_UnitCode != 6) {
									PreparedStatement prs1 = con
											.prepareStatement("select VERIFY_FLAG from FAS_CHEQUE_MEMO_MONTHEND where ACCOUNTING_UNIT_ID="
													+ cmbAcc_UnitCode
													+ " and "
													+ "CASHBOOK_YEAR="
													+ txtCB_Year
													+ " and CASHBOOK_MONTH::numeric="
													+ txtCB_Month);
									ResultSet res1 = prs1.executeQuery();
									while (res1.next()) {
								

										PreparedStatement prep1 = con
												.prepareStatement("delete from FAS_CHEQUE_MEMO_MONTHEND where  ACCOUNTING_UNIT_ID=? and CASHBOOK_YEAR=? and CASHBOOK_MONTH::numeric=?");
										prep1.setInt(1, cmbAcc_UnitCode);
										prep1.setInt(2, txtCB_Year);
										prep1.setInt(3, txtCB_Month);
										Memo_delete = prep1.executeUpdate();

									}
									PreparedStatement prs2 = con
											.prepareStatement("select VERIFY_FLAG from FAS_SCH_EXP_VERIFY_MONTHEND where ACCOUNTING_UNIT_ID="
													+ cmbAcc_UnitCode
													+ " and "
													+ "CASHBOOK_YEAR="
													+ txtCB_Year
													+ " and CASHBOOK_MONTH::numeric="
													+ txtCB_Month);
									ResultSet res2 = prs2.executeQuery();
									
									while (res2.next()) {
										

										PreparedStatement prep2 = con
												.prepareStatement("delete from FAS_SCH_EXP_VERIFY_MONTHEND where  ACCOUNTING_UNIT_ID=? and CASHBOOK_YEAR=? and CASHBOOK_MONTH::numeric=?");
										prep2.setInt(1, cmbAcc_UnitCode);
										prep2.setInt(2, txtCB_Year);
										prep2.setInt(3, txtCB_Month);
										Memo_delete = prep2.executeUpdate();

									}
									
									
								} else {
									Memo_delete = 1;
								}
							} else {
								Memo_delete = 1;
							}
						}
								
								}else
								{
									Memo_delete = 1;
								}
								
								System.out.println("Memo_delete===========>"+Memo_delete);
								//remove
								Memo_delete=1;
								
						if(Memo_delete>0)    {   msg ="Trial Balance Froze Status is Removed... You have to do General Ledger Posting Again for this Accounting Unit ";
							            con.commit();
							            con.setAutoCommit(true);
							
							            msg = msg + "<br><br>";
						            sendMessage(response, msg, "ok"); return;
						}else{
							System.out.println("TEST 123");
							   msg =
						                "Trial Balance Status Change is Unsuccessful.......";
						            msg = msg + "<br><br>";
						            sendMessage(response, msg, "ok"); return;
						}
					       
							}
						        } catch (Exception e) {
			            try {
			                con.rollback();
			            } catch (SQLException e1) {
			                System.out.println("exception in rollback" + e1);
			            }
			            System.out.println("Exception in Trial balance " + e);
			            System.out.println("****************************");
			            String msg =
			                "Trial Balance Status Change is Unsuccessful.......";
			            msg = msg + "<br><br>";
			            sendMessage(response, msg, "ok");
			            return;
			        }
	            }
        }

    }

    private void sendMessage(HttpServletResponse response, String msg,
                             String bType) {
        try {
            String url =
                "org/Library/jsps/Messenger.jsp?message=" + msg + "&button=" +
                bType;
            response.sendRedirect(url);
            return;
        } catch (IOException e) {
            System.out.println(e);
        }
    }

}
