package Servlets.FAS.FAS1.Centage.servlets;

import java.io.*;
import java.sql.*;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.ResourceBundle;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.*;

public class PostCentage extends HttpServlet
{

    private static final String CONTENT_TYPE = "text/html; charset=UTF-8";

    public void init(ServletConfig config)
        throws ServletException
    {
        super.init(config);
    }

    public void doGet(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException
    {
        String CONTENT_TYPE = "text/xml; charset=windows-1252";
        response.setContentType(CONTENT_TYPE);
        PrintWriter out = response.getWriter();
        String strCommand = "";
        int cmbAcc_UnitCode = 0;
        int cmbOffice_code = 0;
        int txtCB_Month = 0;
        int txtCB_Year = 0;
        String xml = null;
        String sql = null;
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        HttpSession session = request.getSession(false);
        try
        {
            if(session == null)
            {
                System.out.println((new StringBuilder()).append(request.getContextPath()).append("/index.jsp").toString());
                response.sendRedirect((new StringBuilder()).append(request.getContextPath()).append("/index.jsp").toString());
                return;
            }
            System.out.println(session);
        }
        catch(Exception e)
        {
            System.out.println((new StringBuilder()).append("Redirect Error :").append(e).toString());
        }
        try
        {
            ResourceBundle rs1 = ResourceBundle.getBundle("Servlets.Security.servlets.Config");
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
            con = DriverManager.getConnection(ConnectionString, strdbusername.trim(), strdbpassword.trim());
        }
        catch(Exception e)
        {
            System.out.println((new StringBuilder()).append("Exception in opening connection :").append(e).toString());
        }
        try
        {
            strCommand = request.getParameter("Command");
            System.out.println((new StringBuilder()).append("assign..here command...").append(strCommand).toString());
        }
        catch(Exception e)
        {
            System.out.println((new StringBuilder()).append("Exception in assigning...").append(e).toString());
        }
        try
        {
            cmbAcc_UnitCode = Integer.parseInt(request.getParameter("cmbAcc_UnitCode"));
        }
        catch(NumberFormatException e)
        {
            System.out.println((new StringBuilder()).append("exception").append(e).toString());
        }
        System.out.println((new StringBuilder()).append("cmbAcc_UnitCode ").append(cmbAcc_UnitCode).toString());
        try
        {
            cmbOffice_code = Integer.parseInt(request.getParameter("cmbOffice_code"));
        }
        catch(NumberFormatException e)
        {
            System.out.println((new StringBuilder()).append("exception").append(e).toString());
        }
        System.out.println((new StringBuilder()).append("cmbOffice_code ").append(cmbOffice_code).toString());
        try
        {
            txtCB_Year = Integer.parseInt(request.getParameter("txtCB_Year"));
        }
        catch(NumberFormatException e)
        {
            System.out.println((new StringBuilder()).append("exception").append(e).toString());
        }
        System.out.println((new StringBuilder()).append("txtCB_Year ").append(txtCB_Year).toString());
        try
        {
            txtCB_Month = Integer.parseInt(request.getParameter("txtCB_Month"));
        }
        catch(NumberFormatException e)
        {
            System.out.println((new StringBuilder()).append("exception").append(e).toString());
        }
        System.out.println((new StringBuilder()).append("txtCB_Month ").append(txtCB_Month).toString());
       
        if(strCommand.equalsIgnoreCase("callCentN"))
        {
        	 xml = "<response><command>callCentN</command>";
            try
            {
            	sql="SELECT accounting_unit_id ,accounting_for_office_id,cashbook_year,cashbook_month," +
            			"voucher_no,  TO_CHAR(voucher_DATE,'DD-MON-YY') AS voucher_date," +
            			"cr_account_head_code,(SELECT account_head_desc FROM com_mst_account_heads " +
            			"WHERE account_head_code = cr_account_head_code) AS cr_account_head_code_desc," +
            			"dr_account_head_code, (SELECT account_head_desc FROM com_mst_account_heads  " +
            			"WHERE account_head_code = dr_account_head_code) AS dr_account_head_code_desc," +
            			"trim(TO_CHAR(amount,'99999999999999.99')) as amount FROM (SELECT * FROM (SELECT " +
            			"accounting_unit_id,accounting_for_office_id,cashbook_year,cashbook_month,voucher_no," +
            			"voucher_date FROM FAS_CENTAGE_BEFORE_POST_MST where ACCOUNTING_UNIT_ID =?  AND " +
            			"ACCOUNTING_FOR_OFFICE_ID = ? AND CASHBOOK_YEAR = ?  AND CASHBOOK_MONTH = ?)a " +
            			"RIGHT OUTER JOIN (select accounting_unit_id AS unit_id, " +
            			"accounting_for_office_id AS office_id, cashbook_year AS cb_year, " +
            			"cashbook_month cb_month, voucher_no AS vou_no, SUM(cr_account_head_code) AS cr_account_head_code, " +
            			"SUM(dr_account_head_code) AS dr_account_head_code, amount,CENTAGE_CREATED FROM   " +
            			"(SELECT accounting_unit_id,accounting_for_office_id,cashbook_year,cashbook_month," +
            			"voucher_no,account_head_code,cr_dr_indicator,    CASE WHEN cr_dr_indicator='CR' " +
            			"THEN account_head_code END AS cr_account_head_code,CASE WHEN cr_dr_indicator ='DR' " +
            			"THEN account_head_code END AS dr_account_head_code,CENTAGE_CREATED,amount " +
            			"from FAS_CENTAGE_BEFORE_POST_TRN where ACCOUNTING_UNIT_ID = ? " +
            			"AND ACCOUNTING_FOR_OFFICE_ID= ? AND CASHBOOK_YEAR   = ? AND CASHBOOK_MONTH= ?  " +
            			"and (CENTAGE_CREATED='N' or CENTAGE_CREATED is null))GROUP BY ACCOUNTING_UNIT_ID, " +
            			"ACCOUNTING_FOR_OFFICE_ID, CASHBOOK_YEAR, CASHBOOK_MONTH, voucher_no, amount,CENTAGE_CREATED )b " +
            			"on a.accounting_unit_id =b.unit_id AND a.ACCOUNTING_FOR_OFFICE_ID= b.OFFICE_ID AND " +
            			"a.CASHBOOK_YEAR  = b.cb_year AND a.CASHBOOK_MONTH =b.cb_month   AND a.voucher_no =b.vou_no  " +
            			"and (b.CENTAGE_CREATED='N'  or b.CENTAGE_CREATED is null)) " +
            			"ORDER BY accounting_unit_id ,accounting_for_office_id,cashbook_year,cashbook_month,voucher_no," +
            			"voucher_date";
          	
                ps = con.prepareStatement(sql);
                ps.setInt(1, cmbAcc_UnitCode);
                ps.setInt(2, cmbOffice_code);
                ps.setInt(3, txtCB_Year);
                ps.setInt(4, txtCB_Month);
                ps.setInt(5, cmbAcc_UnitCode);
                ps.setInt(6, cmbOffice_code);
                ps.setInt(7, txtCB_Year);
                ps.setInt(8, txtCB_Month);
                rs = ps.executeQuery();
                int cnt = 0;
                xml = (new StringBuilder()).append(xml).append("<flag>success</flag>").toString();
                while(rs.next()) 
                {
                    xml = (new StringBuilder()).append(xml).append("<option>").append("<voucher_no>").append(rs.getInt("voucher_no")).append("</voucher_no>").append("<voucher_date>").append(rs.getString("voucher_date")).append("</voucher_date>").append("<cr_account_head_code>").append(rs.getInt("cr_account_head_code")).append("</cr_account_head_code>").append("<cr_account_head_code_desc>").append(rs.getString("cr_account_head_code_desc")).append("</cr_account_head_code_desc>").append("<dr_account_head_code>").append(rs.getInt("dr_account_head_code")).append("</dr_account_head_code>").append("<dr_account_head_code_desc>").append(rs.getString("dr_account_head_code_desc")).append("</dr_account_head_code_desc>").append("<amount>").append(rs.getString("amount")).append("</amount></option>").toString();
                    cnt++;
                }
                if(cnt == 0)
                {
                    xml = "<response><command>callCentN</command><flag>failure</flag>";
                }
            }
            catch(Exception e)
            {
                System.out.println((new StringBuilder()).append("Error ").append(e).toString());
                xml = "<response><command>callCentN</command><flag>failure</flag>";
            }
            
        }
        else if(strCommand.equalsIgnoreCase("callCentY"))
        {
        	
        	 xml = "<response><command>callCentY</command>";
        	 try
             {
             	sql="select ACCOUNTING_UNIT_ID,ACCOUNTING_FOR_OFFICE_ID,CASHBOOK_YEAR,CASHBOOK_MONTH," +
             			"DRHeadCode," +
					 " (SELECT account_head_desc" +
					 "   FROM com_mst_account_heads" +
					 "  WHERE account_head_code = DRHeadCode) AS dr_account_head_code_desc," +
					 "   CR_DR_INDICATOR," +
					 "   drAmt," +
					 "   CRHeadCode," +
					 "   (SELECT account_head_desc" +
					 "   FROM com_mst_account_heads" +
					 "   WHERE account_head_code = CRHeadCode) AS cr_account_head_code_desc," +
					 "   crIndicator," +
					 "   crAmt" +
					 "   from" +
					 " (SELECT ACCOUNTING_UNIT_ID," +
					 "   ACCOUNTING_FOR_OFFICE_ID," +
					 "   CASHBOOK_YEAR," +
					 "   CASHBOOK_MONTH," +
					 "   ACCOUNT_HEAD_CODE AS DRHeadCode," +
					 "   CR_DR_INDICATOR," +
					 "   SUM(AMOUNT)AS drAmt," +
					 "   100101     AS CRHeadCode," +
					 "   'CR'       AS crIndicator," +
					 "   SUM(AMOUNT)AS crAmt" +
					 " FROM FAS_CENTAGE_BEFORE_POST_TRN" +
					 " WHERE ACCOUNT_HEAD_CODE!    =(100101)" +
					 " AND ACCOUNTING_UNIT_ID      = ?" +
					 " AND ACCOUNTING_FOR_OFFICE_ID= ?" +
					 " AND CASHBOOK_YEAR           = ?" +
					 " AND CASHBOOK_MONTH          = ? AND (CENTAGE_CREATED = 'N' or CENTAGE_CREATED is null)" +
					 " GROUP BY ACCOUNTING_UNIT_ID," +
					 "  ACCOUNTING_FOR_OFFICE_ID," +
					 "  CASHBOOK_YEAR," +
					 "  CASHBOOK_MONTH," +
					 "  ACCOUNT_HEAD_CODE," +
					 "  CR_DR_INDICATOR)";
             	
                 ps = con.prepareStatement(sql);
                 ps.setInt(1, cmbAcc_UnitCode);
                 ps.setInt(2, cmbOffice_code);
                 ps.setInt(3, txtCB_Year);
                 ps.setInt(4, txtCB_Month);
                
                 rs = ps.executeQuery();
                 
                 int cnt = 0;
                 xml = (new StringBuilder()).append(xml).append("<flag>success</flag>").toString();
                 while(rs.next()) 
                 {
                     xml = (new StringBuilder()).append(xml).append("<option>").append("<cr_account_head_code>").append(rs.getInt("CRHeadCode")).append("</cr_account_head_code>").append("<cr_account_head_code_desc>").append(rs.getString("cr_account_head_code_desc")).append("</cr_account_head_code_desc>").append("<dr_account_head_code>").append(rs.getInt("DRHeadCode")).append("</dr_account_head_code>").append("<dr_account_head_code_desc>").append(rs.getString("dr_account_head_code_desc")).append("</dr_account_head_code_desc>").append("<dramount>").append(rs.getString("drAmt")).append("</dramount>").append("<cramount>").append(rs.getString("crAmt")).append("</cramount></option>").toString();
                     cnt++;
                 }
                 if(cnt == 0)
                 {
                     xml = "<response><command>callCentY</command><flag>failure</flag>";
                 }
             }
             catch(Exception e)
             {
                 System.out.println((new StringBuilder()).append("Error ").append(e).toString());
                 xml = "<response><command>callCentY</command><flag>failure</flag>";
             }        	 
        	 
        	 
        	 
        }
        xml = (new StringBuilder()).append(xml).append("</response>").toString();
       // System.out.println(xml);
        out.println(xml);
    }

    public void doPost(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException
    {
        response.setContentType("text/html; charset=UTF-8");
        String CONTENT_TYPE = "text/html; charset=windows-1252";
        response.setContentType(CONTENT_TYPE);
        PrintWriter out = response.getWriter();
        Connection con = null;
        CallableStatement cs = null;
        PreparedStatement ps=null,ps1=null,ps2=null;
        Statement st1=null;
        ResultSet rs=null,rs2=null,rs3=null;
        String strCommand = "";
        HttpSession session = request.getSession(false);
        int voucherNo=0,drHCode=0,crHCode=0,pAmt=0,count=0,j2=0;
        String vDate=null;
        Calendar c;
        try
        {
            if(session == null)
            {
                System.out.println((new StringBuilder()).append(request.getContextPath()).append("/index.jsp").toString());
                response.sendRedirect((new StringBuilder()).append(request.getContextPath()).append("/index.jsp").toString());
                return;
            }
            System.out.println(session);
        }
        catch(Exception e)
        {
            System.out.println((new StringBuilder()).append("Redirect Error :").append(e).toString());
        }
        String update_user = (String)session.getAttribute("UserId");
        long l=System.currentTimeMillis();
        Timestamp ts=new Timestamp(l);
        Date ctdate = new java.sql.Date(ts.getTime()); 
        
        try
        {
            ResourceBundle rs1 = ResourceBundle.getBundle("Servlets.Security.servlets.Config");
            String ConnectionString = "";
            String strDriver = rs1.getString("Config.DATA_BASE_DRIVER");
            String strdsn = rs1.getString("Config.DSN");
            String strhostname = rs1.getString("Config.HOST_NAME");
            String strportno = rs1.getString("Config.PORT_NUMBER");
            String strsid = rs1.getString("Config.SID");
            String strdbusername = rs1.getString("Config.USER_NAME");
            String strdbpassword = rs1.getString("Config.PASSWORD");
            ConnectionString = strdsn.trim() + "://" + strhostname.trim() + ":" + strportno.trim() + "/" +strsid.trim() ;    // Postgres DB  Connection            Class.forName(strDriver.trim());
            con = DriverManager.getConnection(ConnectionString, strdbusername.trim(), strdbpassword.trim());
            st1=con.createStatement();
        }
        catch(Exception e)
        {
            System.out.println((new StringBuilder()).append("Exception in opening connection :").append(e).toString());
        }
        
        int cmbAcc_UnitCode = Integer.parseInt(request.getParameter("cmbAcc_UnitCode"));
       // System.out.println((new StringBuilder()).append("cmbAcc_UnitCode-->").append(cmbAcc_UnitCode).toString());
        int cmbOffice_code = Integer.parseInt(request.getParameter("cmbOffice_code"));
       // System.out.println((new StringBuilder()).append("cmbOffice_code-->").append(cmbOffice_code).toString());
        int txtCB_Year = Integer.parseInt(request.getParameter("txtCB_Year"));
       // System.out.println((new StringBuilder()).append("txtCB_Year-->").append(txtCB_Year).toString());
        int txtCB_Month = Integer.parseInt(request.getParameter("txtCB_Month"));
      //  System.out.println((new StringBuilder()).append("txtCB_Month-->").append(txtCB_Month).toString());
    
        int txtVoucher_No=0;
        try
        {
        	
			rs=st1.executeQuery("select max(VOUCHER_NO) from FAS_JOURNAL_MASTER where ACCOUNTING_UNIT_ID="+cmbAcc_UnitCode+" and ACCOUNTING_FOR_OFFICE_ID="+cmbOffice_code+" and CASHBOOK_YEAR="+txtCB_Year+" and CASHBOOK_MONTH="+txtCB_Month);
        	
            if(rs.next()) 
            {
            	
            	txtVoucher_No = rs.getInt(1);                		                   
            }
            txtVoucher_No=txtVoucher_No+1;
           
        }
        catch(Exception e){System.out.println("exception in VOUCHER_NO"+e );}
        
        double postAmt=0;
        Date vouch_Date=null;
        
        try
        {
        	con.clearWarnings();
            con.setAutoCommit(false);
        	
        	String dr_accHCode[] = request.getParameterValues("dr_account_head_code");
        	int hCode=dr_accHCode.length;
        	        
            cs = con.prepareCall("call FAS_POST_CENTAGE( ?::numeric,?::numeric,?::numeric,?::numeric,?::numeric,?,?::numeric,?,?,?::numeric) ");
            cs.setInt(1, cmbAcc_UnitCode);
            cs.setInt(2, cmbOffice_code);
            cs.setInt(3, txtCB_Month);
            cs.setInt(4, txtCB_Year);
            cs.setInt(5, txtVoucher_No);
            cs.setString(6, update_user);
            cs.registerOutParameter(7, 2);
            cs.setDate(8,ctdate);
            cs.setTimestamp(9,ts);
            cs.setInt(10, hCode);
            
            cs.execute();
            //int error_code = cs.getInt(7);
            int error_code = cs.getBigDecimal(7).intValue();
            System.out.println((new StringBuilder()).append("Error Code ---->").append(error_code).toString());
            
            if(error_code!=0)
            {         
                   System.out.println("redirect");
                   sendMessage(response,"Centage Posting Failed ","ok");
                                     
            }

            else
            {  
            		
            	// rows span 2 so (rowcount*2)
            		String serial_No[] = request.getParameterValues("serialNo");
            		int slen=serial_No.length*2;
            		
	            	String dr_account_head_code[] = request.getParameterValues("dr_account_head_code");
	            
	                String cr_account_head_code[] = request.getParameterValues("cr_account_head_code");
	            
	                String post_amount[] = request.getParameterValues("amount");
	               	
                   for(int k=1;k<=slen;k=k+2) 
                   {
                	System.out.println("slen length::;;"+slen);
                	   String sql="insert into FAS_JOURNAL_TRANSACTION(ACCOUNTING_UNIT_ID, " +
	                    "   ACCOUNTING_FOR_OFFICE_ID ,CASHBOOK_YEAR, CASHBOOK_MONTH, " +
	                    "VOUCHER_NO, SL_NO, ACCOUNT_HEAD_CODE, " +
	                    "   CR_DR_INDICATOR, SUB_LEDGER_TYPE_CODE, SUB_LEDGER_CODE, " +
	                    "   AMOUNT, CB_REF_NO,UPDATED_BY_USER_ID,UPDATED_DATE ) "+
	                    "   values(?,?,?,?,?,?,?,?,?,?,?,?,?,?)" ;
                	   ps=con.prepareStatement(sql); 
                	 
                	   int serialNo=0;
                       try
                       {
                    	   rs2=st1.executeQuery("SELECT SL_NO FROM FAS_JOURNAL_TRANSACTION GROUP BY SL_NO " +
                       			"HAVING SL_NO =(select max(SL_NO) from FAS_JOURNAL_TRANSACTION " +
                       			"where ACCOUNTING_UNIT_ID="+cmbAcc_UnitCode+" and " +
                       			"ACCOUNTING_FOR_OFFICE_ID="+cmbOffice_code+" and CASHBOOK_YEAR="+txtCB_Year+" and " +
                       			"CASHBOOK_MONTH="+txtCB_Month+" and VOUCHER_NO="+txtVoucher_No+")");
                    	   
                           if(rs2.next()) 
                           {
                        	   serialNo = rs2.getInt("SL_NO");                		                   
                           }
                           serialNo=serialNo+1;
                       }
                       catch(Exception e){System.out.println("exception"+e );}

                           try{drHCode=Integer.parseInt(dr_account_head_code[k]);}catch(Exception e){System.out.println("exception in drHCode "+e);}
                           try{crHCode=Integer.parseInt(cr_account_head_code[2]);}catch(Exception e){System.out.println("exception in crHCode "+e);}
                           try{
                               postAmt = Double.parseDouble(post_amount[k]);
                               }
                               catch(Exception e1)
                               {
                               	System.out.println(" exception in post_amount"+e1);
                               }
                                                    
                           ps.setInt(1,cmbAcc_UnitCode);
                           ps.setInt(2,cmbOffice_code);
                           ps.setInt(3,txtCB_Year);
                           ps.setInt(4,txtCB_Month);
                           ps.setInt(5,txtVoucher_No);
                           ps.setInt(6,serialNo);
                         //  ps.setInt(7,crHCode);System.out.println("crHCode>>>>>>>>>"+crHCode);
                           ps.setString(7,"100101");
                           ps.setString(8,"CR");
                           ps.setString(9,"0");
                           ps.setString(10,"0");
                         
                           ps.setDouble(11,postAmt);
                           ps.setString(12,"0");
                         
                           ps.setString(13,update_user);
                           ps.setTimestamp(14,ts);
                       
                         int j1=  ps.executeUpdate(); 
                      
                           if(j1>0)
                           {
                        	   String sql1="insert into FAS_JOURNAL_TRANSACTION(ACCOUNTING_UNIT_ID, " +
       	                    "   ACCOUNTING_FOR_OFFICE_ID ,CASHBOOK_YEAR, CASHBOOK_MONTH, " +
       	                    "VOUCHER_NO, SL_NO, ACCOUNT_HEAD_CODE, " +
       	                    "   CR_DR_INDICATOR, SUB_LEDGER_TYPE_CODE, SUB_LEDGER_CODE, " +
       	                    "   AMOUNT, CB_REF_NO,UPDATED_BY_USER_ID,UPDATED_DATE ) "+
       	                    "   values(?,?,?,?,?,?,?,?,?,?,?,?,?,?)" ;
                        	ps1=con.prepareStatement(sql1);
                        	
                        	int serialNum=0;
                            try
                            {
                         	   rs3=st1.executeQuery("SELECT SL_NO FROM FAS_JOURNAL_TRANSACTION GROUP BY SL_NO " +
                            			"HAVING SL_NO =(select max(SL_NO) from FAS_JOURNAL_TRANSACTION " +
                            			"where ACCOUNTING_UNIT_ID="+cmbAcc_UnitCode+" and " +
                            			"ACCOUNTING_FOR_OFFICE_ID="+cmbOffice_code+" and CASHBOOK_YEAR="+txtCB_Year+" and " +
                            			"CASHBOOK_MONTH="+txtCB_Month+" and VOUCHER_NO="+txtVoucher_No+")");
                                if(rs3.next()) 
                                {
                                	serialNum = rs3.getInt("SL_NO");                		                   
                                }
                                serialNum=serialNum+1;
                            }
                            catch(Exception e){System.out.println("exception"+e );}
                        	
                        	ps1.setInt(1,cmbAcc_UnitCode);
                        	ps1.setInt(2,cmbOffice_code);
                        	ps1.setInt(3,txtCB_Year);
                        	ps1.setInt(4,txtCB_Month);
                            ps1.setInt(5,txtVoucher_No);
                            ps1.setInt(6,serialNum);
                            ps1.setInt(7,drHCode);
                            ps1.setString(8,"DR");
                            ps1.setString(9,"0");
                            ps1.setString(10,"0");
                          
                            ps1.setDouble(11,postAmt);
                            ps1.setString(12,"0");
                          
                            ps1.setString(13,update_user);
                            ps1.setTimestamp(14,ts);
                           
                           j2=  ps1.executeUpdate(); 
                           }
                           if(j1==0 && j2==0)
                           {
                        	  
	                        	 con.rollback();
                           }
                           
                   }
                                     
                   }
            if(j2>0)
            {
	          	 ps2=con.prepareStatement("update FAS_CENTAGE_BEFORE_POST_TRN set CENTAGE_CREATED=? where ACCOUNTING_UNIT_ID=? and ACCOUNTING_FOR_OFFICE_ID=? and CASHBOOK_YEAR=? and CASHBOOK_MONTH=? ");
	 		 	 ps2.setString(1,"Y");
	 		 	 ps2.setInt(2,cmbAcc_UnitCode);
	   	 		 ps2.setInt(3,cmbOffice_code);
	   	 	 	 ps2.setInt(4,txtCB_Year);
	   	 		 ps2.setInt(5,txtCB_Month);
	   	 		 int cnt=ps2.executeUpdate();
	   	 		 if(cnt>0)
		   	 		 {
		   	 			 con.commit();
		              	 sendMessage(response,"Centage Posting Voucher Number '"+txtVoucher_No+"' has been Created Successfully ","ok");
		             }
            }
        }
        catch(SQLException e)
        {
        	try
            {
                con.rollback();
            }
            catch(SQLException ee)
            {
                System.out.println(ee);
            }
            sendMessage(response, "Centage Posting Failed ", "OK");
        }
        
        finally
        {
          
            try
            {
           	 	 con.setAutoCommit(true); 
           	 	 con.close();
            }catch(SQLException sqle){}
            
        }
    }

    private void sendMessage(HttpServletResponse response, String msg, String bType)
    {
        try
        {
           
            String url = (new StringBuilder()).append("org/Library/jsps/MessengerOkBack.jsp?message=").append(msg).append("&button=").append(bType).toString();
            response.sendRedirect(url);
        }
        catch(IOException e)
        {
            System.out.println((new StringBuilder()).append("Excep").append(e).toString());
        }
    }
  
}
