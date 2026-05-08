package Servlets.FAS.FAS1.BRS.servlets;

import Servlets.Security.classes.UserProfile;

import java.io.IOException;
import java.io.PrintWriter;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.ResourceBundle;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;


public class BRS_Auto_Journal extends HttpServlet 
{
    private String CONTENT_TYPE = "text/xml; charset=windows-1252";
    
    public void doPost(HttpServletRequest request, 
                      HttpServletResponse response) throws ServletException, IOException 
    {
        
      /**
       * Session Checking 
       */
    
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
    
       
        
       /**
        *  Variables Declaration 
        */
               
         Connection con=null;
         ResultSet rs=null;
         CallableStatement cs1=null;
         PreparedStatement ps=null;
         String xml="";
         String strCommand="";
         
         
         
       /**
        * Database Connection
        */
        
            try {
                              ResourceBundle rs1=ResourceBundle.getBundle("Servlets.Security.servlets.Config");
                              String ConnectionString="";
                              String strDriver=rs1.getString("Config.DATA_BASE_DRIVER");
                              String strdsn=rs1.getString("Config.DSN");
                              String strhostname=rs1.getString("Config.HOST_NAME");
                              String strportno=rs1.getString("Config.PORT_NUMBER");
                              String strsid=rs1.getString("Config.SID");
                              String strdbusername=rs1.getString("Config.USER_NAME");
                              String strdbpassword=rs1.getString("Config.PASSWORD");
                              ConnectionString = strdsn.trim() + "://" + strhostname.trim() + ":" + strportno.trim() + "/" +strsid.trim() ;    // Postgres DB  Connection
                              Class.forName(strDriver.trim());
                              con=DriverManager.getConnection(ConnectionString,strdbusername.trim(),strdbpassword.trim());
             }
             catch(Exception e)
             {
                System.out.println("Exception in opening connection :"+e);                    
             }
             
        
                 
       /**
        * Get Command Parameter 
        */
                 
        try {
        
            strCommand=request.getParameter("Command");
            System.out.println("assign..here command..."+strCommand);
        }        
        catch(Exception e) 
        {
            System.out.println("Exception in assigning..."+e);
        }
        
        
        
        /**
         * If Command is ADD 
         */
       
        if(strCommand.equalsIgnoreCase("Add")) 
        {
        	int errcode=0;
		        
		            /** Set Content Type */ 
		            String CONTENT_TYPE = "text/html; charset=windows-1252";
		            response.setContentType(CONTENT_TYPE);
		            
		            xml="<response><command>Add</command>";
		            
		            /** Variables Declaration */
		            Calendar c;
		            int cmbAcc_UnitCode=0;
		            int cmbOffice_code=0;
		            int txtCB_Year=0;
		            int txtCB_Month=0;
		            String cmbBankAccNo="";
                            long cmbBankAccNo2=0;
		            Date txtCrea_date=null;		            
		            String update_user=(String)session.getAttribute("UserId");
		            long l=System.currentTimeMillis();
		            Timestamp ts=new Timestamp(l);
		            String sd[]=null;
		            java.util.Date d=null;
		            
		            /** Get Accounting Unit Id */
		            try{cmbAcc_UnitCode=Integer.parseInt(request.getParameter("cmbAcc_UnitCode"));}
		            catch(NumberFormatException e){System.out.println("exception"+e );}
		            System.out.println("cmbAcc_UnitCode "+cmbAcc_UnitCode);
		            
		            /** Get Accounting Office ID */
		            try{cmbOffice_code=Integer.parseInt(request.getParameter("cmbOffice_code"));}
		            catch(NumberFormatException e){System.out.println("exception"+e );}
		            System.out.println("cmbOffice_code "+cmbOffice_code);
		          
		            /** Get Cashbook Year */           
		            try{txtCB_Year=Integer.parseInt(request.getParameter("txtCB_Year"));}
		            catch(Exception e){System.out.println("exception"+e );}
		            System.out.println("txtCB_Year "+txtCB_Year);
		            
		            /** Get Cashbook Month */           
		            try{txtCB_Month=Integer.parseInt(request.getParameter("txtCB_Month"));}
		            catch(Exception e){System.out.println("exception"+e );}
		            System.out.println("txtCB_Month "+txtCB_Month);
		            
		        
                            /** Get Bank Account Number */           
		            try{
                               cmbBankAccNo=request.getParameter("cmbBankAccNo");
                                cmbBankAccNo2=Long.parseLong(cmbBankAccNo);
                            }
		            catch(Exception e){System.out.println("exception"+e );}
		            System.out.println("cmbBankAccNo "+cmbBankAccNo);
		            int month1=0;
		            int year1=0;
                                                        		            
                            /** Get Creation Date ( Journal Date )  */
		            try
		            {
                               sd=request.getParameter("txtCrea_date").split("/");
                               
                               c=new GregorianCalendar(Integer.parseInt(sd[2]),Integer.parseInt(sd[1])-1,Integer.parseInt(sd[0]));
                               month1 = Integer.parseInt(sd[1]);
                               year1 = Integer.parseInt(sd[2]);
                               System.out.println("month1:-----------------------="+month1);
                               System.out.println("year1:------------------------="+year1);
                               d=c.getTime();
                               txtCrea_date=new Date(d.getTime());
                               System.out.println("txtCrea_date "+txtCrea_date);
		            }catch(Exception e)
                            {
                              System.out.println("exception"+e );
                            }
		            System.out.println("txtCrea_date "+txtCrea_date);
		            
                            
                            
                            
                            
                          try
                          {
                                    con.clearWarnings();
                                    con.setAutoCommit(false);
                                    
                                    int txtAuth_By=0;                                    
                                    int txtTotTrans=0;
                                    
                                    
                                    /** Journal Master **/
                                    
                                
                                    /** Get Employee ID */  
                                    UserProfile empProfile=(UserProfile)session.getAttribute("UserProfile");
                                    txtAuth_By=empProfile.getEmployeeId();
                                    
                                    /**Get Total Number of Selected Records */
                                    try{txtTotTrans=Integer.parseInt(request.getParameter("txtTotTrans"));}
                                    catch(Exception e){System.out.println("exception"+e );}
                                    System.out.println("txtTotTrans"+txtTotTrans);
                                    
                                    String sel[] = request.getParameterValues("sel");                                    
                                    String crAmt[] = request.getParameterValues("cr_amount");                                    
			            String drAmt[] = request.getParameterValues("dr_amount");
                                    
                                    String slNo[] = request.getParameterValues("slNo");
                                    
			            ArrayList sel_al=new ArrayList();
                                    
                                    for( String selTemp:sel) {
                                       sel_al.add(selTemp);    
                                    }
                                    
                                    
                                   for (int i=0;i<txtTotTrans; i++ ) 
                                   {
                                         System.out.println("i-->"+i);                                     
                                         if ( sel_al.contains(Integer.toString(i))) 
                                         {
                                         
                                             System.out.println("It is selected Records -->"+i);
                                             System.out.println("cr amount-->"+crAmt[i]);
                                             System.out.println("dr amount-->"+drAmt[i]);
                                             System.out.println("slNo -->"+slNo[i]);
                                             
                                           
                                             try
                                             {
                                                 cs1=con.prepareCall("call FAS_BRS_AUTO_JOURNAL(?::numeric,?::numeric,?::numeric,?::numeric,?,?,?::numeric,?::numeric,?::numeric,?::numeric,?::numeric)") ; 
                                                 
                                                 cs1.setInt(1,cmbAcc_UnitCode);
                                                 cs1.setInt(2,cmbOffice_code);                                        
                                                 cs1.setInt(3,year1 );
                                                 cs1.setInt(4,month1);
                                                 cs1.setDate(5,txtCrea_date);
                                                 cs1.setString(6,update_user); 
                                                 cs1.registerOutParameter(7,java.sql.Types.NUMERIC);                                                 
                                                 cs1.setNull(7,java.sql.Types.NUMERIC);
                                                 cs1.setLong(8,cmbBankAccNo2);
                                                 cs1.setDouble(9,Double.parseDouble(drAmt[i]));
                                                 cs1.setDouble(10,Double.parseDouble(crAmt[i]));
                                                 cs1.setDouble(11,Integer.parseInt(slNo[i]));
                                                 
                                                 System.out.println("Double.parseDouble(crAmt[i])--->"+Double.parseDouble(crAmt[i]));
                                                 
                                                 cs1.execute();                                        
                                                 //errcode=cs1.getInt(7);
                                                 errcode = cs1.getBigDecimal(7).intValue();
                                                 System.out.println("Error Code -->"+errcode);                                        
                                                
                                             }
                                             catch (Exception e) 
                                             {
                                                 con.rollback();
                                                 System.out.println("Record Not Inserted into Journal Master "+e);
                                             }
                                           
                                           if(errcode==0)
                                           {
                                        	   con.commit();
                                               sendMessage(response,"BRS Auto Gernal Created Successfully","ok");
                                           }
                                           else
                                           {
                                        	   con.rollback();
                                               sendMessage(response,"Record Not Inserted into Journal Master","ok");
                                           }
                                           
                                         }
                                   }
                                   
                                   
                           
                              
                                     
			    }
		            catch(Exception e) 
		            {
		                try{con.rollback();}catch(SQLException sqle){System.out.println("Excep"+sqle);}
		                sendMessage(response,"The Authorization has failed ","ok");
		                System.out.println("Exception occur due to "+e);
		            }
		            finally
		            {
		                try{con.setAutoCommit(true);  }catch(SQLException sqle){System.out.println("Excep"+sqle);}
		            }
		            
		 }
    }
    
    
    
    
      
    public void doGet(HttpServletRequest request, 
                      HttpServletResponse response) throws ServletException, IOException    
    {
            
    	
       /** Session Checking  */
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
        
        
        
        
       /** Database Connection  */
        Connection con=null;
        ResultSet rs=null;
        PreparedStatement ps=null;
        
        response.setContentType(CONTENT_TYPE);
        response.setHeader("Cache-Control","no-cache");
        
        PrintWriter out = response.getWriter();
        String strCommand="";
        
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
                               ConnectionString = strdsn.trim() + "://" + strhostname.trim() + ":" + strportno.trim() + "/" +strsid.trim() ;    // Postgres DB  Connection
                               Class.forName(strDriver.trim());
                               con=DriverManager.getConnection(ConnectionString,strdbusername.trim(),strdbpassword.trim());
        }
        catch(Exception e)
        {
             System.out.println("Exception in opening connection :"+e);            
        }
        
        
        
        /** Receiving Command  */
        try 
        {
            strCommand=request.getParameter("Command");
            System.out.println("assign..here command..."+strCommand);
        }        
        catch(Exception e) 
        {
            System.out.println("Exception in assigning..."+e);
        }
        
        
        /** Variables Declaration  */
        String xml="";
        int cmbAcc_UnitCode=0;
        int cmbOffice_code=0;        
        int txtCB_Year=0; 
        int txtCB_Month=0;
        String TransType=""; 
        long cmbBankAccNo=0; 
        String txtOprMode=null;
        int txtBankID = 0;
        
        /** Get Accounting Unit ID  */
        try{cmbAcc_UnitCode=Integer.parseInt(request.getParameter("cmbAcc_UnitCode"));}
        catch(NumberFormatException e){System.out.println("exception"+e );}
        System.out.println("cmbAcc_UnitCode "+cmbAcc_UnitCode);
        
        /** Get Accounting Office ID  */
        try{cmbOffice_code=Integer.parseInt(request.getParameter("cmbOffice_code"));}
        catch(NumberFormatException e){System.out.println("exception"+e );}
        System.out.println("cmbOffice_code "+cmbOffice_code);
        
        /** Get Cashbook Month */
        try{txtCB_Month=Integer.parseInt(request.getParameter("txtCB_Month"));}
        catch(NumberFormatException e){System.out.println("exception"+e );}
        System.out.println("txtCB_Month "+txtCB_Month);
        
        /** Get Cashbook year */
        try{txtCB_Year=Integer.parseInt(request.getParameter("txtCB_Year"));}
        catch(NumberFormatException e){System.out.println("exception"+e );}
        System.out.println("txtCB_Year "+txtCB_Year);
        
        /** Get Transaction Type */
        try{TransType=request.getParameter("TransType");}
        catch(NumberFormatException e){System.out.println("exception"+e );}
        System.out.println("TransType "+TransType);
        
        /** Get Bank Account Number */
        try{cmbBankAccNo=Long.parseLong(request.getParameter("cmbBankAccNo"));}
        catch(NumberFormatException e){System.out.println("exception"+e );}
        System.out.println("cmbBankAccNo "+cmbBankAccNo);
        
        /* Get Operation Mode */
		try {
			txtOprMode = request.getParameter("txtOprMode");
		} catch (Exception e) {
			System.out.println("Error Not Getting Operation Mode -->" + e);
		}
		
		/* Get Bank ID */
		try {
			String txtBankID2 = request.getParameter("txtBankID");

			if (txtBankID2 != null) {
				if (txtBankID2.equals("")) {
					txtBankID = 0;
				} else {
					txtBankID = Integer.parseInt(txtBankID2);
				}
			}
		} catch (Exception e) {
			System.out.println("Error Not Getting Bank ID -->" + e);
		}		

       /** Load Transaction Details */        
        if(strCommand.equalsIgnoreCase("Load_TransDetails")) 
        {
             String CONTENT_TYPE = "text/xml; charset=windows-1252";
             response.setContentType(CONTENT_TYPE);
            
              xml="<response><command>Load_TransDetails</command>";
              String sql="";              
              String sql_NT="";
              
                sql_NT = "select SL_NO,PASSBOOK_DATE,PARTICULARS,CHEQUE_DD_NO,CR_AMOUNT,DR_AMOUNT,"
					+ "(ACCOUNT_HEAD_CODE ||'/'||ACCOUNT_HEAD_DESC) as ACCOUNT_HEAD_DESC from (select SL_NO,ACCOUNTING_UNIT_ID,"
					+ "to_char(PASSBOOK_DATE,'DD/MM/YYYY')  as PASSBOOK_DATE,PARTICULARS,CHEQUE_DD_NO,"
					+ "CR_AMOUNT,DR_AMOUNT,ACCOUNT_NO from fas_brs_transaction	where ACCOUNTING_UNIT_ID = ?"
					+ " and ACCOUNTING_FOR_OFFICE_ID = ? and CASHBOOK_YEAR = ? and CASHBOOK_MONTH = ?		"
					+ " and TWAD_OR_NON_TWAD = 'NT' and ACCOUNT_NO = ? and JOURNALIZED='Y' and "
					+ "doc_date is null)y left outer join ( select ACCOUNT_HEAD_DESC,ACCOUNT_HEAD_CODE,"
					+ "ACCOUNTING_UNIT_ID as acc_uni_id,BANK_AC_NO from ( select AC_HEAD_CODE,"
					+ "ACCOUNTING_UNIT_ID,BANK_AC_NO from fas_office_bank_ac_current where "
					+ "ACCOUNTING_UNIT_ID=? and MODULE_ID='MF004' and AC_OPERATIONAL_MODE_ID = ? and SL_NO=1)a "
					+ "left outer join (select ACCOUNT_HEAD_CODE,ACCOUNT_HEAD_DESC from com_mst_account_heads)b"
					+ " on a.AC_HEAD_CODE = b.ACCOUNT_HEAD_CODE )x on y.ACCOUNTING_UNIT_ID =x.acc_uni_id and "
					+ "y.ACCOUNT_NO =x.BANK_AC_NO  ";		
                        
                System.out.println(sql_NT);
                    
                     if (TransType.equalsIgnoreCase("NT"))
                     {
                       sql=sql_NT;
                     }
                     
                    
		                    try {
		                            ps=con.prepareStatement(sql);
		                            ps.setInt(1,cmbAcc_UnitCode);
		                            ps.setInt(2,cmbOffice_code);
		                            ps.setInt(3,txtCB_Year);
		                            ps.setInt(4,txtCB_Month);
		                            ps.setLong(5,cmbBankAccNo);
		                            ps.setInt(6,cmbAcc_UnitCode);
		                            ps.setString(7,txtOprMode);
		                            
		                            rs=ps.executeQuery();
		                            
		                            int count=0;
		                         
		                            while(rs.next())
		                            {
		                            	xml=xml+"<Sl_No>"+rs.getString("SL_NO")+"</Sl_No>";
		                            	xml=xml+"<Particulars>"+rs.getString("PARTICULARS")+"</Particulars>";
		                                xml=xml+"<Passbook_Date>"+rs.getString("PASSBOOK_DATE")+"</Passbook_Date>";
		                             	xml=xml+"<Cheque_No>"+rs.getString("CHEQUE_DD_NO")+"</Cheque_No>";
		                            	xml=xml+"<cr_amt>"+rs.getString("CR_AMOUNT")+"</cr_amt>";
		                            	xml=xml+"<dr_amt>"+rs.getString("DR_AMOUNT")+"</dr_amt>";
		                            	xml=xml+"<DR_Head_Code>"+rs.getString("ACCOUNT_HEAD_DESC")+"</DR_Head_Code>";
		                            	
		                                count++;
		                            }
		                            
		                            System.out.println("Count -->"+count);
		                            
		                            if(count==0)
		                                xml=xml+"<flag>NoRecords</flag>";
		                            else 
		                                xml=xml+"<flag>success</flag>";
		                            
		                           
		                        xml=xml+"<TransType>"+TransType+"</TransType>";
		                            
		                        System.out.println("count  "+count);
		                        
		                        ps.close();
		                        rs.close();
		                        
		                        }
		                        catch(Exception e)
		                        {
		                            System.out.println("catch..HERE.in load VOUCHER."+e);
		                            xml=xml+"<flag>failure</flag>";
		                        }
		                        
		                        
                        xml=xml+"</response>";
                        System.out.println(xml);
                        out.println(xml);                       
        }          
  }
        
    
    private void sendMessage(HttpServletResponse response,String msg,String bType)
    {
        try
        {
            String url="org/Library/jsps/MessengerOkBack.jsp?message=" + msg + "&button=" + bType;
            response.sendRedirect(url);
        }
        catch(IOException e)
        {
        System.out.println("Excep"+e);
        }
    }
    
}
