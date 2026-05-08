package Servlets.FAS.FAS1.MonthEnd.servlets;

import Servlets.Security.classes.UserProfile;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.CallableStatement;
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

public class FreezeTB_SJV extends HttpServlet {
    private static final String CONTENT_TYPE =
        "text/xml; charset=windows-1252";

    public void init(ServletConfig config) throws ServletException {
        super.init(config);
    }

    public void doPost(HttpServletRequest request,
                       HttpServletResponse response) throws ServletException,
                                                            IOException {

        /**
         * Database Connection
         */
        Connection con = null;
        Statement statement = null;
        PreparedStatement ps=null,ps11=null;
        ResultSet results=null;
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
         * Content Type Setting
         */
        response.setContentType(CONTENT_TYPE);


        /**
         * Session Checking
         */
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


        /**
         * Variables Declaration
         */

        int txtCB_Year = 0, txtCB_Month = 0, cmbAcc_UnitCode =
            0, cmbOffice_code = 0;
        String radTB_status = "";


        /**
         * Get Parameters
         */

        /** Get Accounting Unit Id */
        try {
            cmbAcc_UnitCode =
                    Integer.parseInt(request.getParameter("cmbAcc_UnitCode"));
        } catch (NumberFormatException e) {
            System.out.println("exception" + e);
        }
        System.out.println("cmbAcc_UnitCode " + cmbAcc_UnitCode);

        /** Get Cash Book Year */
        txtCB_Year = Integer.parseInt(request.getParameter("txtCB_Year"));

        /** Get Cash Book Month */
        txtCB_Month = Integer.parseInt(request.getParameter("txtCB_Month"));
        System.out.println("year..." + txtCB_Year);
        System.out.println("Month..." + txtCB_Month);

        /** Get YES or NO Status */
        radTB_status = request.getParameter("radTB_status");
        System.out.println("radTB_status..." + radTB_status);

        /** Get User ID */
        String userid = (String)session.getAttribute("UserId");

        /** Get Updated Time */
        long l = System.currentTimeMillis();
        Timestamp ts = new Timestamp(l);

        /** Get Supplement Number */
        int Supplement_No = 0,notAllowed=0;
        try {
            Supplement_No =
                    Integer.parseInt(request.getParameter("txtsupplement_no"));
        } catch (Exception e) {
            System.out.println(e);
        }
        UserProfile up=(UserProfile)session.getAttribute("UserProfile");
        int empid1= up.getEmployeeId();
        System.out.println("======================================================================>"+empid1);
        
        int OfficeCode=0,supNo_db=0,supCount=0,prevsupCount=1;
        int prevSup=Supplement_No-1;
        String msg = "";
        Boolean flag = false, flag2 = false, flag3 = false;
        
        try {
                                  
                                   ps=con.prepareStatement("select ACCOUNTING_UNIT_OFFICE_ID from FAS_MST_ACCT_UNITS where ACCOUNTING_UNIT_ID=?" );
                                   ps.setInt(1,cmbAcc_UnitCode);
                                   results=ps.executeQuery();
                                   if(results.next())
                                   {
                                   OfficeCode=results.getInt("ACCOUNTING_UNIT_OFFICE_ID");
                                   System.out.println("-----------------------------------------------------------------------------------"+OfficeCode);
                                   }
                                   results.close();
                                   ps.close();   
                                   
                               }
                               catch(Exception e) {
                                   System.out.println(e);
                               }
        
        /**
         * 
         * SlPart */
	       try {
	           con.setAutoCommit(false);
	       } catch (Exception e) {
	           System.out.println("");
	       }
	       
	       //New code added on 28-08-2018.
	       
	       try {
	            PreparedStatement ps1 = null;
	            ps1 =
	  con.prepareStatement("  select 'X'                  \n" + "  from                        \n" +
	                       "    FAS_TRIAL_BALANCE_STATUS  \n" +
	                       "  WHERE                       \n" +
	                       "     ACCOUNTING_UNIT_ID=?     \n" +
	                       "  AND CASHBOOK_YEAR=?      \n" +
	                       "  AND CASHBOOK_MONTH=?");
	            ps1.setInt(1, cmbAcc_UnitCode);
	            ps1.setInt(2, txtCB_Year);
	            ps1.setInt(3, txtCB_Month);
	            ResultSet rh = ps1.executeQuery();
	            if (!rh.next()) // if the row doesn't return by the query
	            {
	                sendMessage(response, "Please Freeze Regular March for this cashbook year", "ok");
	                return;
	            }
	            rh.close();
	            ps1.close();
	        } catch (Exception e) {
	            System.out.println(e);
	        }
	       
        
	       try {
               
	    	   String sql_one="select max(SUPPLEMENT_NO)as dbsup from FAS_TRIAL_BALANCE_STATUS_SJV where " +
	    	   		"ACCOUNTING_UNIT_ID="+cmbAcc_UnitCode+" and CASHBOOK_YEAR="+txtCB_Year+" and CASHBOOK_MONTH="+txtCB_Month+" and NIL_TB_STATUS is null";
	    	   System.out.println("sql_one::::"+sql_one);
               ps=con.prepareStatement(sql_one);
               results=ps.executeQuery();
               if(results.next())
               {
            	   supCount++;
               supNo_db=results.getInt("dbsup");
               System.out.println("supNo_db::"+supNo_db);
               }
               results.close();
               ps.close();   
               
           }
           catch(Exception e) {
               System.out.println(e);
           }
	       if(supCount>0)
	       {
	    	   System.out.println("count  comess");
	    	   System.out.println("supNo_db::"+supNo_db);
	    	   System.out.println("Supplement_No::"+Supplement_No);
	    	   if(supNo_db==Supplement_No)
	    	   {
	    		   System.out.println("equals");
	    		   notAllowed++;	
	    		}
	    	   else if(supNo_db>Supplement_No)
	    	   {
	    		   notAllowed++;
	    	   }
	    	   
	       }
	       else
	       {
	    	   if(Supplement_No>1)
	    	   {
	    		  
	    		   try {
	                   
	    	    	   String sql_two="select SUPPLEMENT_NO from FAS_TRIAL_BALANCE_STATUS_SJV where " +
	    	    	   		"ACCOUNTING_UNIT_ID="+cmbAcc_UnitCode+" and CASHBOOK_YEAR="+txtCB_Year+" and CASHBOOK_MONTH="+txtCB_Month+" and SUPPLEMENT_NO="+prevSup;
	    	    	   System.out.println("sql_two::::"+sql_two);
	                   ps=con.prepareStatement(sql_two);
	                   results=ps.executeQuery();
	                   if(results.next())
	                   {
	                	   prevsupCount++;
	                	   System.out.println("prev:::::"+ results.getInt("SUPPLEMENT_NO"));
	                   }
	                   else
	                   {
	                	   prevsupCount=0;
	                   }
	                   results.close();
	                   ps.close();   
	                   
	               }
	               catch(Exception e) {
	                   System.out.println(e);
	               }
	    		   
	    	   }
	    	  
	    	   
	       }
	   
	       System.out.println("notAllowed:"+notAllowed);
	       if(notAllowed==0)
	       {
	       
	    	   if(prevsupCount>0){
	    	   
        /**
         * Calling Procedure
         */
        CallableStatement cs = null;
        String msg1 = "";
      //dhana changes on may10 for adding office code in sl table...
        int offCount1=0;
        
       try
       {
    	   System.out.println("loop starts:::");
    	   PreparedStatement ps3=con.prepareStatement("delete from FAS_SUB_LEDGER_MASTER_SJV " +
    	   		"where  Accounting_Unit_Id =  "+cmbAcc_UnitCode+" and MONTH = "+txtCB_Month+" and Year  = "+txtCB_Year+" and SUPPLEMENT_NO="+Supplement_No);
    	   int ss=ps3.executeUpdate();
    	   if(ss>0)
    	   {
    		   con.commit();
    	   }
       }
       catch (Exception e) {
           System.out.println("error in delete:"+e);
       }
			try{
				String sql="select ACCOUNTING_FOR_OFFICE_ID from FAS_MST_ACCT_UNIT_OFFICES where ACCOUNTING_UNIT_ID="+cmbAcc_UnitCode;
				System.out.println("sql:::"+sql);
				PreparedStatement ps2=con.prepareStatement(sql);
				ResultSet rs2=ps2.executeQuery();
				while(rs2.next())
				{
					offCount1++;
					CallableStatement cs3 = null;
			       
			        try {
			            cs3 =
			            	con.prepareCall(" call FAS_SL_SJV_POSTING_NEW ( ?::numeric,?::numeric,?::numeric,?::numeric,?,?::numeric,?::numeric ) ");
			            cs3.setInt(1, cmbAcc_UnitCode);
			            System.out.println("offcode:::"+rs2.getInt("ACCOUNTING_FOR_OFFICE_ID"));
			            cs3.setInt(2, rs2.getInt("ACCOUNTING_FOR_OFFICE_ID"));
			            cs3.setInt(3, txtCB_Year);
			            cs3.setInt(4, txtCB_Month);
			            cs3.setString(5, userid);
			            cs3.setInt(6, Supplement_No);
			            cs3.registerOutParameter(7, java.sql.Types.NUMERIC);
			            cs3.setInt(7,0);
			            cs3.execute();
			            //int errcode = cs3.getInt(7);
			            int errcode = cs3.getBigDecimal(7).intValue();

			            System.out.println("Error Code -sl-->" + errcode);

			            if (errcode != 0) {
			                msg = "Sub Ledger Posting for SJV  Failed ";
			                msg = msg + "<br><br>";
			                sendMessage(response, msg, "ok");
			                return;
			            }
			            else {
			                  
			                  flag=true;
			               
			                con.setAutoCommit(true);
			              
			            }
			            

			        } catch (Exception e) {
			            System.out.println("error:"+e);
			        }

					
				}
			}
			catch (Exception e) {
	            System.out.println("exception be4 ::"+e);
	        }
        
      /*  try {
            cs =
         con.prepareCall("{ call FAS_SL_SJV_POSTING ( ?,?,?,?,?,? ) }");
            cs.setInt(1, cmbAcc_UnitCode);
          //  cs.setInt(2, OfficeCode);
            cs.setInt(2, txtCB_Year);
            cs.setInt(3, txtCB_Month);
            cs.setString(4, userid);
            cs.setInt(5, Supplement_No);
            cs.registerOutParameter(6, java.sql.Types.NUMERIC);
            cs.execute();
            int errcode = cs.getInt(6);

            System.out.println("Error Code SL --->" + errcode);

            if (errcode != 0) {
                msg1 = "Sub Ledger Posting for SJV  Failed ";
                msg1 = msg1 + "<br><br>";
                sendMessage(response, msg1, "ok");
            } else {
                  
                  flag=true;
                msg1 = "Sub Ledger Posting for SJV Generated Successfully !  ";
                msg1 = msg1 + "<br><br>";
                con.setAutoCommit(true);
                //sendMessage(response, msg1, "ok");
            }

        } catch (Exception e) {
            try {
                            con.rollback();
                        } catch (SQLException e1) {
                            System.out.println("catch:" + e1);
                        }
            System.out.println(e);
        }  */
   
   /*
    *   Gl Part
    * 
    * 
    * 
    */
      if(flag) {
          
          
          
          System.out.println("-----------------------------------------------------------------------------------"+flag);
                  /**
                   * Calling Procedure
                   */
                //  CallableStatement cs = null;
            /*      String msg = "";
                  try {
                      cs =
            con.prepareCall("{ call FAS_GL_SJV_POSTING ( ?,?,?,?,?,? ) }");
                      cs.setInt(1, cmbAcc_UnitCode);
                //      cs.setInt(2, OfficeCode);
                      cs.setInt(2, txtCB_Year);
                      cs.setInt(3, txtCB_Month);
                      cs.setString(4, userid);
                      cs.setInt(5, Supplement_No);
                      cs.registerOutParameter(6, java.sql.Types.NUMERIC);
                      cs.execute();
                      int errcode = cs.getInt(6);

                      System.out.println("Error CodeGL --->" + errcode);

                      if (errcode != 0) {
                          msg = "General Ledger Posting for SJV  Failed ";
                          msg = msg + "<br><br>";
                          sendMessage(response, msg, "ok");
                      } else {
                      
                         flag2=true;
                          msg =
           "General Ledger Posting for SJV Generated Successfully !  ";
                          msg = msg + "<br><br>";
                          con.setAutoCommit(true);
                         // sendMessage(response, msg, "ok");
                      }

                  } catch (Exception e) {
                  
                      try {
                                      con.rollback();
                                  } catch (SQLException e1) {
                                      System.out.println("catch:" + e1);
                                  }
                      System.out.println(e);
                  } */

          
        //dhana changes on may10 for adding office code in gl table...
          int offCount=0;
          try
          {
       	   PreparedStatement ps3=con.prepareStatement("delete from FAS_GENERAL_LEDGER_SJV " +
       	   		"where  Accounting_Unit_Id =  "+cmbAcc_UnitCode+" and MONTH = "+txtCB_Month+" and Year  = "+txtCB_Year+" and SUPPLEMENT_NO="+Supplement_No);
       	   int ss1=ps3.executeUpdate();
       	   if(ss1>0)
       	   {
       		con.commit();
       	   }
          }
          catch (Exception e) {
              System.out.println("error in delete:"+e);
          }
  			try{
  				String sql="select ACCOUNTING_FOR_OFFICE_ID from FAS_MST_ACCT_UNIT_OFFICES where ACCOUNTING_UNIT_ID="+cmbAcc_UnitCode;
  				System.out.println("sql:::"+sql);
  				PreparedStatement ps2=con.prepareStatement(sql);
  				ResultSet rs2=ps2.executeQuery();
  				while(rs2.next())
  				{
  					offCount++;
  					 CallableStatement cs2 = null;
  				        try {
  				            cs2 = con.prepareCall(" call FAS_GL_SJV_POSTING_NEW ( ?::numeric,?::numeric,?::numeric,?::numeric,?,?::numeric,?::numeric ) ");
  				            cs2.setInt(1, cmbAcc_UnitCode);
  				            System.out.println("offcode:::"+rs2.getInt("ACCOUNTING_FOR_OFFICE_ID"));
  				            cs2.setInt(2, rs2.getInt("ACCOUNTING_FOR_OFFICE_ID"));
  				            cs2.setInt(3, txtCB_Year);
  				            cs2.setInt(4, txtCB_Month);
  				            cs2.setString(5, userid);
  				            cs2.setInt(6, Supplement_No);
  				            cs2.registerOutParameter(7, java.sql.Types.NUMERIC);
  				            cs2.setInt(7, 0);
  				            cs2.execute();
  				            //int errcode = cs2.getInt(7);
  				          int errcode = cs2.getBigDecimal(7).intValue();

  				            System.out.println("Error Code -gl-->" + errcode);

  				            if (errcode != 0) {
  				            	System.out.println("error:::::::");
  				                msg = "General Ledger Posting for SJV  Failed ";
  				                msg = msg + "<br><br>";
  				                sendMessage(response, msg, "ok");
  				              return;
  				            }
  				          else {
  		                      
  	                         flag2=true;
  	                          
  	                          con.setAutoCommit(true);
  	                         // sendMessage(response, msg, "ok");
  	                      }

  				        } catch (Exception e) {
  				            System.out.println(e);
  				        }
  					
  				}
  			}
  			catch (Exception e) {
  	            System.out.println("exception be4 ::"+e);
  	        }
          
          
          
          
          
          
          
      }
      
      /**
       * 
       * 
       * TB Part
       * 
       * 
       */
      
      
      
      
      
      
      if(flag2) {
          System.out.println("-----------------------------------------------------------------------------------"+flag2);
        //  String msg = "";
          
          /**
           * Should not allow for Generating TB if Vouchers in Journal are not verified
           */
          int journal_count = 0;
          try {

              String sql =
                  "" + "select                                                         \n" +
                  "  count(*) as v_count                                          \n" +
                  "from                                                           \n" +
                  "  fas_journal_master a ,                                       \n" +
                  "  fas_journal_transaction b                                    \n" +
                  "where                                                          \n" +
                  "    a.accounting_unit_id = ?                                   \n" +
                  "and a.cashbook_month= ?                                        \n" +
                  "and a.cashbook_year = ?                                        \n" +
                  "and a.accounting_unit_id=b.accounting_unit_id                  \n" +
                  "and a.accounting_for_office_id =b.accounting_for_office_id     \n" +
                  "and a.cashbook_year=b.cashbook_year                            \n" +
                  "and a.cashbook_month=b.cashbook_month                          \n" +
                  "and a.voucher_no=b.voucher_no                                  \n" +
                  "and a.JOURNAL_STATUS='L'                                       \n" +
                  "and b.VERIFIED is null and a.created_by_module in ( 'SJV' )   ";

              ps11 = con.prepareStatement(sql);
              ps11.setInt(1, cmbAcc_UnitCode);
              ps11.setInt(2, txtCB_Month);
              ps11.setInt(3, txtCB_Year);
              System.out.println("before execution");
              ResultSet rs = ps11.executeQuery();
              System.out.println("after execution");
              while (rs.next()) {
                  journal_count = rs.getInt("v_count");
              }

              System.out.println("journal_count--------------------------------------------->" + journal_count);
              
              if(journal_count>0) {
                  sendMessage(response, " Supplement Journal Vouchers are pending for  Verification", "ok");
                  return;
              }
              

          } catch (Exception e) {
              System.out.println(e);
          }


          
          
          try {
              cs = con.prepareCall("call FAS_SUPPLEMENT_TB_GEN_NEW(?::numeric,?::numeric,?::numeric,?,?::numeric,?::numeric)");
              //cs = con.prepareCall("{call FAS_SUPPLEMENT_TB_GENERATION(?,?,?,?,?,?)}");
              cs.setInt(1, cmbAcc_UnitCode);
              cs.setInt(2, txtCB_Year);
              cs.setInt(3, txtCB_Month);
              cs.setString(4, userid);
              cs.setInt(5, Supplement_No);
              cs.registerOutParameter(6, java.sql.Types.NUMERIC);
              cs.setNull(6, java.sql.Types.NUMERIC);
              cs.execute();
             // int errcode = cs.getInt(6);
              int errcode = cs.getBigDecimal(6).intValue();

              System.out.println("Error Code TB--->" + errcode);

              if (errcode != 0) {
                  sendMessage(response,
                              " Supplement Trial Balance Generation Failed ",
                              "ok");
                  return;
              } else {
              
              flag3=true;
              
              }

          
          
          
      }catch (Exception e) {
    	  e.printStackTrace();
            System.out.println("Exception in Main:" + e);
            try {
                con.rollback();
            } catch (SQLException e1) {
                System.out.println("catch:" + e1);
            }
            msg = "Trial Balance Has failed to Update";
            msg = msg + "<br><br>";
            sendMessage(response, msg, "ok");
            return;
        }
      
      int rsflag=0; 
      try{
           	String td="Select Verify_Flag From FAS_TDA_TCA_MONTHEND_SJV WHERE ACCOUNTING_UNIT_ID    =? AND " +
           			" Cashbook_Year           =? AND CASHBOOK_MONTH=? and SUPPLEMENT_NO=?";
           	PreparedStatement ps22=con.prepareStatement(td);
           	ps22.setInt(1,cmbAcc_UnitCode);
           	ps22.setInt(2,txtCB_Year);
           	ps22.setInt(3,txtCB_Month);
           	ps22.setInt(4,Supplement_No);
           ResultSet rs22=ps22.executeQuery();
           	while(rs22.next())
           	{
           		rsflag++;
           	}
           	if(rsflag==0)
              {
              	con.rollback(); 
                  sendMessage(response, "Please Verify of TDA/TCA register For Supplement","ok");  
                  return;
              }
           	
           }
           catch(Exception ee)
           {
           	System.out.println("excep::::"+ee);
           }
      
      
      
      int rsflag1=0; 
      try{
           	String td="Select Verify_Flag From FAS_SCH_EXP_VERIFY_MONTH_SJV WHERE ACCOUNTING_UNIT_ID    =? AND " +
           			" Cashbook_Year           =? AND CASHBOOK_MONTH=? and SUPPLEMENT_NO=?";
           	PreparedStatement ps23=con.prepareStatement(td);
           	ps23.setInt(1,cmbAcc_UnitCode);
           	ps23.setInt(2,txtCB_Year);
           	ps23.setInt(3,txtCB_Month);
           	ps23.setInt(4,Supplement_No);
           ResultSet rs23=ps23.executeQuery();
           	while(rs23.next())
           	{
           		rsflag1++;
           	}
           	if(rsflag1==0)
              {
              	con.rollback(); 
              	 sendMessage(response, " Please Verify Scheme Expenditure Supplement and Then Generate TB ","ok"); 
                 return;   
                 
              }
           	
           }
           catch(Exception ee)
           {
           	System.out.println("excep::::"+ee);
           }
      
      
      }
        
      
        
        if(flag3)
        {
        
        
            System.out.println("-----------------------------------------------------------------------------------"+flag3);
        

        try {
            con.setAutoCommit(false);
           // PreparedStatement ps = null;
           // String msg = " ";

            /**Display Warning Message if TB in not generated */

            ps =
  con.prepareStatement("select count(*) as cnt from FAS_TRIAL_BALANCE_SUPPLEMENT where accounting_unit_id=? and cashbook_year=? and cashbook_month=? and SUPPLEMENT_NO=?");
            ps.setInt(1, cmbAcc_UnitCode);
            ps.setInt(2, txtCB_Year);
            ps.setInt(3, txtCB_Month);
            ps.setInt(4, Supplement_No);

            ResultSet res1 = ps.executeQuery();
            if (res1.next()) // if the row doesn't return by the query
            {
                int count = res1.getInt("cnt");
                if (count == 0) {
                    sendMessage(response,
                                "Supplement Trial Balance not Generated",
                                "ok");
                    System.out.println("Supplement Trial Balance not Generated");
                    return;
                }
            }


            /** For Yes Condition */

            if (radTB_status.equalsIgnoreCase("Y")) {
                System.out.println("Inside TB_status 'Y'");
                System.out.println("..0");
                ps =
  con.prepareStatement("select 'X' from FAS_TRIAL_BALANCE_SUPPLEMENT " +
                       "WHERE ACCOUNTING_UNIT_ID=? and supplement_no= ? AND CASHBOOK_YEAR=? AND CASHBOOK_MONTH=?" +
                       "HAVING SUM(CURRENT_MONTH_DEBIT)!=SUM(CURRENT_MONTH_CREDIT) AND SUM(CURRENT_MONTH_DEBIT)=0 " +
                       "AND SUM(CURRENT_MONTH_CREDIT)=0");
                ps.setInt(1, cmbAcc_UnitCode);
                ps.setInt(2, Supplement_No);
                ps.setInt(3, txtCB_Year);
                ps.setInt(4, txtCB_Month);

                ResultSet res = ps.executeQuery();
                if (res.next()) // if the row doesn't return by the query
                {
                    sendMessage(response,
                                "Supplement Trial Balance doesn't Tally",
                                "ok");
                    System.out.println("Supplement Trial Balance doesn't Tally");
                    return;
                }
                res.close();
                ps.close();


                ps =
  con.prepareStatement("select TB_STATUS from FAS_TRIAL_BALANCE_STATUS_SJV where  ACCOUNTING_UNIT_ID=? and CASHBOOK_YEAR=? and CASHBOOK_MONTH=?  and SUPPLEMENT_NO = ? ");
                ps.setInt(1, cmbAcc_UnitCode);
                ps.setInt(2, txtCB_Year);
                ps.setInt(3, txtCB_Month);
                ps.setInt(4, Supplement_No);

                ResultSet rs = ps.executeQuery();
                if (rs.next()) // if already in TB_STATUS="N' then
                {
                    // Mostly it never occur, but here just checking it whether already exist
                    System.out.println("..3");
                    PreparedStatement ps1 =
                        con.prepareStatement("update FAS_TRIAL_BALANCE_STATUS_SJV set TB_STATUS='Y',TB_DATE=?,UPDATED_BY_USER_ID=?,UPDATED_DATE=? where  ACCOUNTING_UNIT_ID=? and CASHBOOK_YEAR=? and CASHBOOK_MONTH=? and SUPPLEMENT_NO = ? ");
                    ps1.setTimestamp(1, ts);
                    ps1.setString(2, userid);
                    ps1.setTimestamp(3, ts);
                    ps1.setInt(4, cmbAcc_UnitCode);
                    ps1.setInt(5, txtCB_Year);
                    ps1.setInt(6, txtCB_Month);

                    ps1.setInt(7, Supplement_No);

                    ps1.executeUpdate();
                    ps1.close();

                } else // if NOT exist in table
                {
                	   System.out.println("..3 ELSE .. ");
                	PreparedStatement pss=con.prepareStatement("delete from FAS_TRIAL_BALANCE_SJV_CMP where ACCOUNTING_UNIT_ID=? and CASHBOOK_YEAR=? and CASHBOOK_MONTH=? and SUPPLEMENT_NO=?");
                   pss.setInt(1,cmbAcc_UnitCode);
                   pss.setInt(2,txtCB_Year);
                   pss.setInt(3,txtCB_Month);
                   pss.setInt(4,Supplement_No);
                   int k=pss.executeUpdate();
                   System.out.println("k updatedin:::::::"+k);
                   if(k>0)
                   {
                	   con.commit();
                       con.setAutoCommit(true);
                   }
                	System.out.println("..4");
                    System.out.println("cmbAcc_UnitCode "+cmbAcc_UnitCode+" txtCB_Year "+txtCB_Year+" txtCB_Month "+txtCB_Month+" Supplement_No "+Supplement_No);
                    PreparedStatement ps1 =
                        con.prepareStatement("insert into FAS_TRIAL_BALANCE_STATUS_SJV(ACCOUNTING_UNIT_ID,ACCOUNTING_FOR_OFFICE_ID,CASHBOOK_YEAR,CASHBOOK_MONTH,TB_STATUS,TB_DATE,UPDATED_BY_USER_ID,UPDATED_DATE,SUPPLEMENT_NO,NIL_TB_STATUS) values(?,?,?,?,?,?,?,?,?,?)");
                    ps1.setInt(1, cmbAcc_UnitCode);
                    ps1.setInt(2, 0);
                    ps1.setInt(3, txtCB_Year);
                    ps1.setInt(4, txtCB_Month);
                    ps1.setString(5, "Y");
                    ps1.setTimestamp(6, ts);
                    ps1.setString(7, userid);
                    ps1.setTimestamp(8, ts);
                    ps1.setInt(9, Supplement_No);
                    ps1.setString(10, "");
                    ps1.executeUpdate();
                    ps1.close();
                }
                msg =
 "Supplement Trial Balance Status has been Frozen Successfully.......";
                con.commit();
                con.setAutoCommit(true);
                msg = msg + "<br><br>";
                sendMessage(response, msg, "ok");
            //    return;
                ps.close();
                return;
            }
            /** For Not Condition */
            else if (radTB_status.equalsIgnoreCase("N")) // If trial balance status enabled to "N" from "Y" means
            {
                System.out.println("Inside TB_status 'N'");

                System.out.println("..5");
                // Here i deleted the TB generated info from FAS_TRIAL_BALANCE_STATUS , if exist
                ps =
  con.prepareStatement("delete from FAS_TRIAL_BALANCE_STATUS_SJV where  ACCOUNTING_UNIT_ID=? and CASHBOOK_YEAR=? and CASHBOOK_MONTH=?");
                ps.setInt(1, cmbAcc_UnitCode);
                ps.setInt(2, txtCB_Year);
                ps.setInt(3, txtCB_Month);
                ps.executeUpdate();
                ps.close();
                System.out.println("..6");

                msg =
 "Trial Balance Froze Status is Removed... You have to do General Ledger Posting Again for this Accounting Unit ";
            }
            con.commit();
            con.setAutoCommit(true);
            msg = msg + "<br><br>";
            sendMessage(response, msg, "ok");
            return;
        }
        catch (Exception e) {
            try {
                con.rollback();
            } catch (SQLException e1) {
                System.out.println("exception in rollback" + e1);
            }
            System.out.println("Exception in Trial balance " + e);
            e.printStackTrace();
            msg =
                "Supplement Trial Balance Status Changeing is Unsuccessful.......";
            msg = msg + "<br><br>";
            sendMessage(response, msg, "ok");
            return;

        }
        }
        else {
                    try {
                        con.rollback();
                    } catch (SQLException e1) {
                        System.out.println("catch:" + e1);
                    }
                    msg = "Trial Balance Has failed to Update";
                    msg = msg + "<br><br>";
                    sendMessage(response, msg, "ok");
                    return;
                }
        
	       }
	       else
	       {
	    	  
		    	   msg = "Please Freeze Supplement No "+prevSup;
	               msg = msg + "<br><br>";
	               sendMessage(response, msg, "ok");
		       return;
	       }
        
	       }
	       else
	       {
	    	   msg = "Supplement No "+Supplement_No+" is Already Freezed For This Unit";
               msg = msg + "<br><br>";
               sendMessage(response, msg, "ok");
	       }
        
    }
    
    
    public void doGet(HttpServletRequest request,
            HttpServletResponse response) throws ServletException,
                                                 IOException {
    	  Connection con = null;
          Statement statement = null;
          ResultSet rst = null, results = null;
          PreparedStatement ps1=null,ps2=null;
          try {
              ResourceBundle rs =
                  ResourceBundle.getBundle("Servlets.Security.servlets.Config");
                  
              String conString = "";

              String strDriver = rs.getString("Config.DATA_BASE_DRIVER");
              String strdsn = rs.getString("Config.DSN");
              String strhostname = rs.getString("Config.HOST_NAME");
              String strportno = rs.getString("Config.PORT_NUMBER");
              String strsid = rs.getString("Config.SID");
              String strdbusername = rs.getString("Config.USER_NAME");
              String strdbpassword = rs.getString("Config.PASSWORD");

//              conString =
//                      strdsn.trim() + "@" + strhostname.trim() + ":" + strportno.trim() +
//                      ":" + strsid.trim();
              conString = strdsn.trim() + "://" + strhostname.trim() + ":" + strportno.trim() + "/" +strsid.trim() ;    // Postgres DB  Connection


              Class.forName(strDriver.trim());
              con =
   DriverManager.getConnection(conString, strdbusername.trim(),
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
          int cmbAcc_UnitCode =0;
          try {
              cmbAcc_UnitCode =
                      Integer.parseInt(request.getParameter("cmbAcc_UnitCode"));
          } catch (NumberFormatException e) {
              System.out.println("exception" + e);
          }
          
          int txtCB_Year = Integer.parseInt(request.getParameter("txtCB_Year"));

          int txtCB_Month = Integer.parseInt(request.getParameter("txtCB_Month"));
          
      String cmd=request.getParameter("command");
      String xml="";
      PrintWriter ps=response.getWriter();
      
    
    
    if(cmd.equalsIgnoreCase("verify"))
    {
  	  
  	  xml="<response><command>verify</command>";
  	  System.out.println("Inside Verify!!!!!!!");
  	 
  	  String sql1="";ResultSet rs=null;
  	 
  	  try{
  	  
  		  System.out.println("cmbAcc_UnitCode>>>>"+cmbAcc_UnitCode);
  		  
  		  
  		int count1=0;  
		  
		  try {
	            PreparedStatement ps11 = null;
	            ps11 =
	  con.prepareStatement("  select 'X'                  \n" + "  from                        \n" +
	                       "    FAS_TRIAL_BALANCE_STATUS_SJV  \n" +
	                       "  WHERE                       \n" +
	                       "     ACCOUNTING_UNIT_ID=?     \n" +
	                       "  AND CASHBOOK_YEAR=?      \n" +
	                       "  AND CASHBOOK_MONTH=?");
	            ps11.setInt(1, cmbAcc_UnitCode);
	            ps11.setInt(2, txtCB_Year);
	            ps11.setInt(3, txtCB_Month);
	            ResultSet rh = ps11.executeQuery();
	            if (rh.next()) // if the row doesn't return by the query
	            {
	            	 xml=xml+"<flag>Already_Frozen</flag>";
	            	 count1=1;
	            }
	            rh.close();
	            ps11.close();
	        } catch (Exception e) {
	            System.out.println(e);
	        }
		  
		  
		  
		if(count1==0)  {
  		  
  		  
  		  
  		  
  	  if(cmbAcc_UnitCode!=5)
  	 {
  	  System.out.println("inside !=5 ........");
  		  
  		  sql1="SELECT Descr," + 
  	 		"  accounting_unit_id," + 
  	 		"  YEAR," + 
  	 		"  MONTH," + 
  	 		"  SUM(debit)  AS debit," + 
  	 		"  SUM(credit) AS credit," + 
  	 		"  SUM(debit-credit) as diff" + 
  	 		" FROM" + 
  	 		"  (SELECT 'GL' AS Descr," + 
  	 		"    g.accounting_unit_id," + 
  	 		"    g.year," + 
  	 		"    g.month," + 
  	 		"    SUM(g.current_month_debit)  AS debit ," + 
  	 		"    SUM(g.current_month_credit) AS credit" + 
  	 		"  FROM fas_general_ledger_sjv g" + 
  	 		"  WHERE g.accounting_unit_id =" + cmbAcc_UnitCode +
  	 		"  AND g.year                 =" + txtCB_Year +
  	 		"  AND g.month                =" + txtCB_Month +
  	 		"  GROUP BY g.accounting_unit_id," + 
  	 		"    g.year," + 
  	 		"    g.month" + 
  	 		"  UNION ALL" + 
  	 		"  SELECT 'TB' AS Descr," + 
  	 		"    g.accounting_unit_id," + 
  	 		"    g.cashbook_year             AS YEAR ," + 
  	 		"    g.cashbook_month            AS MONTH," + 
  	 		"    SUM(g.current_month_debit)  AS debit ," + 
  	 		"    SUM(g.current_month_credit) AS credit" + 
  	 		"  FROM FAS_TRIAL_BALANCE_SUPPLEMENT g" + 
  	 		"  WHERE g.accounting_unit_id =" + cmbAcc_UnitCode +
  	 		"  AND g.cashbook_year        =" + txtCB_Year +
  	 		"  AND g.cashbook_month       =" + txtCB_Month +
  	 		"  GROUP BY g.accounting_unit_id," + 
  	 		"    g.cashbook_year," + 
  	 		"    g.cashbook_month" + 
  	 		"  UNION ALL" + 
  	 		
  	 		"  SELECT 'Transaction' AS Descr," + 
  	 		"    accounting_unit_id," + 
  	 		"    cashbook_year  AS YEAR," + 
  	 		"    cashbook_month AS MONTH," + 
  	 		"    SUM(debit)     AS debit ," + 
  	 		"    SUM(credit)    AS credit" + 
  	 		"  FROM" + 
  	 		"    (SELECT m.accounting_unit_id," + 
  	 		"      m.cashbook_year," + 
  	 		"      m.cashbook_month," + 
  	 		"      t.amount AS credit," + 
  	 		"      0        AS debit" + 
  	 		"    FROM fas_journal_master m," + 
  	 		"      fas_journal_transaction t" + 
  	 		"    WHERE t.accounting_unit_id     = m.accounting_unit_id" + 
  	 		"    AND t.accounting_for_office_id = m.accounting_for_office_id" + 
  	 		"    AND m.cashbook_year            = t.cashbook_year" + 
  	 		"    AND m.cashbook_month           = t.cashbook_month" + 
  	 		"    AND m.voucher_no               = t.voucher_no" + 
  	 		"    AND m.Journal_status           ='L'" + 
  	 		"    AND t.cr_dr_indicator          = 'CR'" + 
  	        "    AND m.CREATED_BY_MODULE  in ('SJV') " +
  	 		"    AND m.SUPPLEMENT_NO              =1 " +
  	 		"    AND m.accounting_unit_id       =" + cmbAcc_UnitCode +
  	 		"    AND m.cashbook_year            =" + txtCB_Year +
  	 		"    AND m.cashbook_month           =" + txtCB_Month +
  	 		"    UNION ALL" + 
  	 		"    SELECT m.accounting_unit_id," + 
  	 		"      m.cashbook_year," + 
  	 		"      m.cashbook_month," + 
  	 		"      0        AS credit," + 
  	 		"      t.amount AS debit" + 
  	 		"    FROM fas_journal_master m," + 
  	 		"      fas_journal_transaction t" + 
  	 		"    WHERE t.accounting_unit_id     = m.accounting_unit_id" + 
  	 		"    AND t.accounting_for_office_id = m.accounting_for_office_id" + 
  	 		"    AND m.cashbook_year            = t.cashbook_year" + 
  	 		"    AND m.cashbook_month           = t.cashbook_month" + 
  	 		"    AND m.voucher_no               = t.voucher_no" + 
  	 		"    AND m.Journal_status           ='L'" + 
  	 		"    AND t.cr_dr_indicator          = 'DR'" + 
  	 		"    AND m.CREATED_BY_MODULE  in ('SJV') " +
  	 		"    AND m.SUPPLEMENT_NO              =1 " +
  	 		"    AND m.accounting_unit_id       =" + cmbAcc_UnitCode +
  	 		"    AND m.cashbook_year            =" + txtCB_Year +
  	 		"    AND m.cashbook_month           =" + txtCB_Month +
  	 		"    )as sm " + 
  	 		"  GROUP BY accounting_unit_id," + 
  	 		"    cashbook_year," + 
  	 		"    cashbook_month" + 
  	 		
  	 		"  )as sm" + 
  	 		" GROUP BY descr," + 
  	 		"  accounting_unit_id," + 
  	 		"  YEAR," + 
  	 		"  MONTH " + 
  	 		" ORDER BY accounting_unit_id," + 
  	 		"  YEAR," + 
  	 		"  MONTH," + 
  	 		"  descr ";	 
  		  
  		  System.out.println("SQL1>>>1>>>>"+sql1);
  		  
  		  
  	 }
  	 else if(cmbAcc_UnitCode==5)
  	 {

      	 sql1="SELECT Descr," + 
      	 		"  accounting_unit_id," + 
      	 		"  YEAR," + 
      	 		"  MONTH," + 
      	 		"  SUM(debit)  AS debit," + 
      	 		"  SUM(credit) AS credit," + 
      	 		"  SUM(debit-credit) as diff" + 
      	 		"  FROM" + 
      	 		"  (SELECT 'GL' AS Descr," + 
      	 		"    g.accounting_unit_id," + 
      	 		"    g.year," + 
      	 		"    g.month," + 
      	 		"    SUM(g.current_month_debit)  AS debit ," + 
      	 		"    SUM(g.current_month_credit) AS credit" + 
      	 		"  FROM fas_general_ledger_sjv g" + 
      	 		"  WHERE g.accounting_unit_id =5" +
      	 		"  AND g.year                 =" + txtCB_Year +
      	 		"  AND g.month                =" + txtCB_Month +
      	 		"  GROUP BY g.accounting_unit_id," + 
      	 		"    g.year," + 
      	 		"    g.month" + 
      	 		"  UNION ALL" + 
      	 		"  SELECT 'TB' AS Descr," + 
      	 		"    g.accounting_unit_id," + 
      	 		"    g.cashbook_year             AS YEAR ," + 
      	 		"    g.cashbook_month            AS MONTH," + 
      	 		"    SUM(g.current_month_debit)  AS debit ," + 
      	 		"    SUM(g.current_month_credit) AS credit" + 
      	 		"  FROM FAS_TRIAL_BALANCE_SUPPLEMENT g" + 
      	 		"  WHERE g.accounting_unit_id =5" + 
      	 		"  AND g.cashbook_year        =" + txtCB_Year +
      	 		"  AND g.cashbook_month       =" + txtCB_Month +
      	 		"  GROUP BY g.accounting_unit_id," + 
      	 		"    g.cashbook_year," + 
      	 		"    g.cashbook_month" + 
      	 		"  UNION ALL" + 
      	 		
      	 		"  SELECT 'Transaction' AS Descr," + 
      	 		"    accounting_unit_id," + 
      	 		"    cashbook_year  AS YEAR," + 
      	 		"    cashbook_month AS MONTH," + 
      	 		"    SUM(debit)     AS debit ," + 
      	 		"    SUM(credit)    AS credit" + 
      	 		"  FROM" + 
      	 		"    (SELECT m.accounting_unit_id," + 
      	 		"      m.cashbook_year," + 
      	 		"      m.cashbook_month," + 
      	 		"      t.amount AS credit," + 
      	 		"      0        AS debit" + 
      	 		"    FROM fas_journal_master m," + 
      	 		"      fas_journal_transaction t" + 
      	 		"    WHERE t.accounting_unit_id     = m.accounting_unit_id" + 
      	 		"    AND t.accounting_for_office_id = m.accounting_for_office_id" + 
      	 		"    AND m.cashbook_year            = t.cashbook_year" + 
      	 		"    AND m.cashbook_month           = t.cashbook_month" + 
      	 		"    AND m.voucher_no               = t.voucher_no" + 
      	 		"    AND m.Journal_status           ='L'" + 
      	 		"    AND t.cr_dr_indicator          = 'CR'" + 
      	 		"    AND m.SUPPLEMENT_NO            =1 " +
      	 		"    AND m.CREATED_BY_MODULE  in ('SJV') " +
      	 		"    AND m.accounting_unit_id       =5" + 
      	 		"    AND m.cashbook_year            =" + txtCB_Year +
      	 		"    AND m.cashbook_month           =" + txtCB_Month +
      	 		"    UNION ALL" + 
      	 		"    SELECT m.accounting_unit_id," + 
      	 		"      m.cashbook_year," + 
      	 		"      m.cashbook_month," + 
      	 		"      0        AS credit," + 
      	 		"      t.amount AS debit" + 
      	 		"    FROM fas_journal_master m," + 
      	 		"      fas_journal_transaction t" + 
      	 		"    WHERE t.accounting_unit_id     = m.accounting_unit_id" + 
      	 		"    AND t.accounting_for_office_id = m.accounting_for_office_id" + 
      	 		"    AND m.cashbook_year            = t.cashbook_year" + 
      	 		"    AND m.cashbook_month           = t.cashbook_month" + 
      	 		"    AND m.voucher_no               = t.voucher_no" + 
      	 		"    AND m.Journal_status           ='L'" + 
      	 		"    AND t.cr_dr_indicator          = 'DR'" + 
      	 		"    AND m.SUPPLEMENT_NO            =1 " +
      	 		"    AND m.CREATED_BY_MODULE  in ('SJV') " +
      	 		"    AND m.accounting_unit_id       =5" + 
      	 		"    AND m.cashbook_year            =" + txtCB_Year +
      	 		"    AND m.cashbook_month           =" + txtCB_Month +
      	 		"    )" + 
      	 		"  GROUP BY accounting_unit_id," + 
      	 		"    cashbook_year," + 
      	 		"    cashbook_month" + 

      	 		"  )" + 
      	 		"  GROUP BY descr," + 
      	 		"  accounting_unit_id," + 
      	 		"  YEAR," + 
      	 		"  MONTH" + 
      	 		"  ORDER BY accounting_unit_id," + 
      	 		"  YEAR," + 
      	 		"  MONTH," + 
      	 		"  descr ";	 
      	 System.out.println("SQL1>>>>2>>>"+sql1);
      	 
  	 }
  	  ps2 = con.prepareStatement(sql1);
  	     rs = ps2.executeQuery();
  	  
  	  
  	  
  	  
        System.out.println("after execution");
        
        int diff=0;
        int count=0;
        
        while (rs.next()) {
  	 
       xml=xml+"<accounting_unit_id>"+rs.getInt("accounting_unit_id")+"</accounting_unit_id>";
       xml=xml+"<Descr>"+rs.getString("Descr")+"</Descr>";
       xml=xml+"<year>"+rs.getInt("YEAR")+"</year>";
       xml=xml+"<month>"+rs.getInt("MONTH")+"</month>";
       xml=xml+"<debit>"+rs.getBigDecimal("debit")+"</debit>";
       xml=xml+"<credit>"+rs.getBigDecimal("credit")+"</credit>";
       xml=xml+"<diff>"+rs.getBigDecimal("diff")+"</diff>";
       count++;
     // diff=rs.getInt("diff");
       
       
        }
        if(count>0)
        {
      	  xml=xml+"<flag>success</flag>";
        }
        else
        {
      	  xml=xml+"<flag>NoRecord</flag>";
        }
        
        
//        if(diff>0)
//        {
//       	 xml=xml+"<flag>NotTally</flag>";
//        }
//        else if(diff==0)
//        {
//       	 xml=xml+"<flag>success</flag>";
//        }
//        else
//        {
//       	 xml=xml+"<flag>failure</flag>";
//        }
		}
  	  }catch(Exception e)
        {
      	  System.out.println("Exception >>>>"+e);
        }
    }
    
    xml=xml+"</response>";
    System.out.println(">>> "+xml);
    ps.println(xml);
    ps.close();
}
    
    

    private void sendMessage(HttpServletResponse response, String msg,
                             String bType) {
        try {
            String url =
                "org/Library/jsps/Messenger.jsp?message=" + msg + "&button=" +
                bType;
            response.sendRedirect(url);
        } catch (IOException e) {
        System.out.println("Final"+e);
        }  return;
    }

}
