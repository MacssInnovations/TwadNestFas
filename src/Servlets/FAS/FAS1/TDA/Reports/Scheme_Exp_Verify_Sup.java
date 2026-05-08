package Servlets.FAS.FAS1.TDA.Reports;

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

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import Servlets.Security.classes.UserProfile;

/**
 * Servlet implementation class tda_tca_verification_units_only
 */
public class Scheme_Exp_Verify_Sup extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    
    public Scheme_Exp_Verify_Sup() {
        super();
        
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println("units only:::");
		 response.setHeader("Cache-Control", "no-cache");
         String CONTENT_TYPE = "text/xml; charset=windows-1252";
         response.setContentType(CONTENT_TYPE);
        
         PrintWriter out = response.getWriter();
         String cmd;
         int major;
         int unitid=0,offid=0,invoiceNo=0,billno=0,headcode=0;
         String todate="";
         int agreeno=0,count=0,verified_already=0;
         String isection="",expen="";
         String xml="",sql="";
         
         Connection con=null;
         PreparedStatement ps=null,ps2=null,ps3=null,ps4=null,pss2=null;
         Statement st=null;
         ResultSet result=null,rs3=null,rss2=null;
         int eid=0,supNo=0;
         cmd=request.getParameter("command");
         try
         {
                  ResourceBundle rs=ResourceBundle.getBundle("Servlets.Security.servlets.Config");
                  String ConnectionString="";
                 
                  String strDriver=rs.getString("Config.DATA_BASE_DRIVER");
                  String strdsn=rs.getString("Config.DSN");
                  String strhostname=rs.getString("Config.HOST_NAME");
                  String strportno=rs.getString("Config.PORT_NUMBER");
                  String strsid=rs.getString("Config.SID");
                  String strdbusername=rs.getString("Config.USER_NAME");
                  String strdbpassword=rs.getString("Config.PASSWORD");
                    
                  ConnectionString = strdsn.trim() + "://" + strhostname.trim() + ":" + strportno.trim() + "/" +strsid.trim() ;    // Postgres DB  Connection         
                   Class.forName(strDriver.trim());
                   con=DriverManager.getConnection(ConnectionString,strdbusername.trim(),strdbpassword.trim());
                   try
                   {
                         st=con.createStatement();
                         con.clearWarnings();
                   }
                   catch(SQLException e)
                   {
                         System.out.println("Exception in creating statement:"+e);
                   }          
         }
         catch(Exception e)
         {
                    System.out.println("Exception in openeing connection:"+e);
         }
           
         HttpSession session=request.getSession(false);
         UserProfile empProfile=(UserProfile)session.getAttribute("UserProfile");
         eid=empProfile.getEmployeeId();
         int cmbAcc_UnitCode=0,cmbOffice_code=0,txtCB_Month=0,txtCB_Year=0,advnumber=0;
         System.out.println("employee id:"+eid);
          try
          {
             
                 if(session==null)
                 {
                     System.out.println(request.getContextPath()+"/index.jsp");
                     response.sendRedirect(request.getContextPath()+"/index.jsp");                   
                 }
                 System.out.println(session);
                 
         }catch(Exception e)
         {
         //System.out.println("Redirect Error :"+e);
         }
          String userid=(String)session.getAttribute("UserId");
          System.out.println("session id is:"+userid);
          long l=System.currentTimeMillis();
          Timestamp ts=new Timestamp(l);
       
           
          if(cmd.equalsIgnoreCase("verify_cr_dr"))
          {
      	 try{cmbAcc_UnitCode=Integer.parseInt(request.getParameter("cmbAcc_UnitCode"));}
           catch(NumberFormatException e){System.out.println("exception"+e );}
        //   System.out.println("cmbOffice_code "+cmbOffice_code);
           try{txtCB_Year=Integer.parseInt(request.getParameter("txtCB_Year"));}
           catch(NumberFormatException e){System.out.println("exception"+e );}
           try{txtCB_Month=Integer.parseInt(request.getParameter("txtCB_Month"));}
           catch(NumberFormatException e){System.out.println("exception in txtCB_Month:"+e );}
           try{supNo=Integer.parseInt(request.getParameter("supNo"));}
           catch(NumberFormatException e){System.out.println("exception"+e );} 
           
           
           xml="<response><command>verify_cr_dr</command>"; 
             
             
             
             
             
              try 
                      {
             	
            	  String que= " SELECT ROWNUM AS sl_no, "+
            			  " ACCOUNTING_UNIT_ID, "+
            			  " CASHBOOK_MONTH, "+
            			  " SUB_LEDGER_CODE, "+
            			 " SLNAME, "+
            			 " DR_AMOUNT, "+
            			 " CR_AMOUNT , "+
            			  " ABS(NET_AMT) as NET_AMOUNT,"+
            			 "CASE WHEN NET_AMT>=0 THEN 'DR' WHEN NET_AMT<0 THEN 'CR' END AS NET_INDI"+
            			" FROM "+
            			 " (SELECT Accounting_Unit_Id, "+
            			 "   CASHBOOK_MONTH, "+
            			  "  SUB_LEDGER_CODE, "+
            			   " SLNAME, "+
            			   " SUM(DR_AMOUNT) AS DR_AMOUNT, "+
            			   " SUM(CR_AMOUNT) AS CR_AMOUNT , "+
            			    "SUM(DR_AMOUNT-CR_AMOUNT)AS NET_AMT "+
            			 " FROM "+
            			  "  (SELECT B.CASHBOOK_MONTH,"+
            			   "   B.Accounting_Unit_Id, "+
            			     " B.SUB_LEDGER_CODE, "+
            			     " (SELECT distinct SCH_NAME "+
            			    "  FROM PMS_SCH_MASTER "+
            			    "  WHERE PROJECT_ID=B.SUB_LEDGER_CODE "+
            			     " ) AS SLNAME , "+
            			     " CASE "+
            			      "  WHEN B.Cr_Dr_Indicator='DR' "+
            			      "  THEN SUM(B.Amount) " +
            			      "  ELSE 0 "+
            			    "  END AS dr_Amount, "+
            			     " CASE "+
            			       " WHEN b.Cr_Dr_Indicator='CR' "+
            			       " THEN SUM(B.Amount) "+
            			      "  ELSE 0 "+
            			    "  END AS cr_amount "+
            			  "  FROM fas_journal_master a, "+
            			    "  Fas_Journal_Transaction b "+
            			  "  WHERE A.Accounting_Unit_Id    =b.Accounting_Unit_Id "+
            			  "  AND A.Accounting_For_Office_Id=B.Accounting_For_Office_Id "+
            			   " AND A.Cashbook_Year           =B.Cashbook_Year "+
            			 "   AND A.Cashbook_Month          =B.Cashbook_Month "+
            			  "  AND A.Voucher_No              =B.Voucher_No "+
            			   " AND A.JOURNAL_STATUS          ='L' "+
            			   " AND A.ACCOUNTING_UNIT_ID      ="+cmbAcc_UnitCode+
            			  "  AND B.CASHBOOK_YEAR           ="+txtCB_Year+
            			   " AND B.CASHBOOK_MONTH          ="+txtCB_Month+
            			   "  AND B.SUB_LEDGER_TYPE_CODE    =10 "+
            			   " GROUP BY B.CASHBOOK_MONTH, "+
            			    "  B.Accounting_Unit_Id, "+
            			    "  B.SUB_LEDGER_CODE, "+
            			    "  B.CR_DR_INDICATOR "+
            			   " UNION ALL "+
            			   " SELECT B.CASHBOOK_MONTH, "+
            			    "  B.Accounting_Unit_Id, "+
            			     " B.SUB_LEDGER_CODE, "+
            			     " (SELECT distinct SCH_NAME "+
            			     " FROM PMS_SCH_MASTER "+
            			    "  WHERE PROJECT_ID=B.SUB_LEDGER_CODE "+
            			    "  ) AS SLNAME , "+
            			    "  CASE "+
            			     "   WHEN B.Cr_Dr_Indicator='DR' "+
            			       " THEN SUM(B.Amount) "+
            			      "  ELSE 0 "+
            			     "  END AS dr_Amount, "+
            			     " CASE "+
            			       " WHEN b.Cr_Dr_Indicator='CR' "+
            			       " THEN SUM(B.Amount) "+
            			      "  ELSE 0 "+
            			    "  END AS CR_AMOUNT  "+
            			   " FROM FAS_PAYMENT_MASTER A , "+
            			    "  Fas_Payment_Transaction b "+
            			  "  WHERE A.Accounting_Unit_Id    =b.Accounting_Unit_Id "+
            			   " AND A.Accounting_For_Office_Id=B.Accounting_For_Office_Id "+
            			  "  AND A.Cashbook_Year           =B.Cashbook_Year "+
            			  "  AND A.Cashbook_Month          =B.Cashbook_Month "+
            			  "  AND A.VOUCHER_NO              =B.VOUCHER_NO "+
            			   " AND A.PAYMENT_STATUS          ='L' "+
            			   " AND A.ACCOUNTING_UNIT_ID      ="+cmbAcc_UnitCode+
            			   " AND B.CASHBOOK_YEAR           ="+txtCB_Year+
            			   " AND B.CASHBOOK_MONTH          ="+txtCB_Month+
            			   " AND B.SUB_LEDGER_TYPE_CODE    = 10 "+
            			   " GROUP BY B.CASHBOOK_MONTH, "+
            			    "  B.Accounting_Unit_Id , "+
            			    "  B.SUB_LEDGER_CODE, " +
            			     " B.Cr_Dr_Indicator "+
            			  "  UNION ALL "+
            			  "  SELECT B.CASHBOOK_MONTH, "+
            			     " B.Accounting_Unit_Id, "+
            			    "  B.SUB_LEDGER_CODE, "+
            			     " (SELECT distinct SCH_NAME "+
            			     " FROM PMS_SCH_MASTER "+
            			    "  WHERE PROJECT_ID=B.SUB_LEDGER_CODE "+
            			     " ) AS SLNAME , "+
            			     " CASE "+
            			      "  WHEN B.Cr_Dr_Indicator='DR' "+
            			       " THEN SUM(B.Amount) "+
            			       " ELSE 0 "+
            			     " END AS dr_Amount, "+
            			     " CASE "+
            			      "  WHEN b.Cr_Dr_Indicator='CR' "+
            			     "   THEN SUM(B.Amount) "+
            			      "  ELSE 0 "+
            			     " END AS CR_AMOUNT "+
            			   " FROM FAS_RECEIPT_MASTER A, "+
            			    "  Fas_Receipt_Transaction b "+
            			   "  WHERE A.Accounting_Unit_Id    =b.Accounting_Unit_Id  "+
            			   " AND A.Accounting_For_Office_Id=B.Accounting_For_Office_Id "+
            			   " AND A.Cashbook_Year           =B.Cashbook_Year "+
            			   " AND A.CASHBOOK_MONTH          =B.CASHBOOK_MONTH "+
            			  "  AND A.RECEIPT_NO              =B.RECEIPT_NO "+
            			   " AND A.RECEIPT_STATUS          ='L' "+
            			   " AND A.ACCOUNTING_UNIT_ID      ="+cmbAcc_UnitCode+
            			  "  AND B.CASHBOOK_YEAR           ="+txtCB_Year+
            			   " AND B.CASHBOOK_MONTH          ="+txtCB_Month+
            			  " AND B.SUB_LEDGER_TYPE_CODE    =10 "+
            			  "  GROUP BY B.CASHBOOK_MONTH, "+
            			     " B.Accounting_Unit_Id, "+
            			    "  B.SUB_LEDGER_CODE, "+
            			    "  B.Cr_Dr_Indicator "+
            			  "  ) "+
            			 " GROUP BY ACCOUNTING_UNIT_ID, "+
            			   " CASHBOOK_MONTH, "+
            			   " SUB_LEDGER_CODE, "+
            			  "  SLNAME "+
            			  " ORDER BY Accounting_Unit_Id, "+
            			   " CASHBOOK_MONTH "+
            			 " ) "+
            			" GROUP BY ROWNUM, "+
            			 " ACCOUNTING_UNIT_ID, "+
            			 " CASHBOOK_MONTH, "+
            			 " SUB_LEDGER_CODE, "+
            			 " SLNAME, "+
            			 " DR_AMOUNT, "+
           			  "  CR_AMOUNT, \n"+
            			 " NET_AMT \n"+
            			" ORDER BY ACCOUNTING_UNIT_ID, "+
            			 " CASHBOOK_MONTH ";
            	  
            	  
            
	              
                 
                 
                                
              System.out.println("que:::"+que);
                              ps = con.prepareStatement(que);
                              result = ps.executeQuery();                                
                              while(result.next()) 
                              {
                            	  xml=xml+"<Sl_No>"+result.getInt("sl_no")+"</Sl_No>";
                            	  xml=xml+"<month>"+result.getInt("Cashbook_Month")+"</month>";
                                  xml=xml+"<SUB_LEDGER_CODE>"+result.getInt("SUB_LEDGER_CODE")+"</SUB_LEDGER_CODE>";
                                  xml=xml+"<SLNAME><![CDATA["+result.getString("SLNAME")+"]]></SLNAME>";
                                  
                                  xml=xml+"<DR_AMOUNT>"+result.getString("DR_AMOUNT")+"</DR_AMOUNT>";
                                  xml=xml+"<CR_AMOUNT>"+result.getString("CR_AMOUNT")+"</CR_AMOUNT>";
                                  xml=xml+"<NET_AMT>"+result.getString("NET_AMOUNT")+"</NET_AMT>";
                                  xml=xml+"<NET_INDI>"+result.getString("NET_INDI")+"</NET_INDI>";
                                                                 
                                  count++;
                              }
                              System.out.println("count=========>"+count);
                              if(count>0)
                              {
                             	  xml=xml+"<flag>success</flag>";
 
                                  try
                                  {
                                 	pss2=con.prepareStatement("select VERIFY_FLAG from FAS_SCH_EXP_VERIFY_MONTH_SJV  " +
                                 			" where ACCOUNTING_UNIT_ID=? and Cashbook_Year=? and CASHBOOK_MONTH=? and SUPPLEMENT_NO=?");
                                 	pss2.setInt(1,cmbAcc_UnitCode);
                               	    pss2.setInt(2,txtCB_Year);
                               	    pss2.setInt(3,txtCB_Month);
                               	    pss2.setInt(4,supNo);
                               	 
                               	    rss2=pss2.executeQuery();
                               	    while(rss2.next())
                               	       {
                                   	    verified_already++;	
                                   	    }
                                   	  xml=xml+"<verified_already>"+verified_already+"</verified_already>";
                                      }
                                      catch(Exception ee)
                                      {
                                     	 
                                      }
                                 
                                 
                                  
                              }
                              else if(count==0)
                              {
                                  xml=xml+"<flag>NoRecord</flag>";
                                  
                                  try
                                  {
                                 	pss2=con.prepareStatement("select VERIFY_FLAG from FAS_SCH_EXP_VERIFY_MONTH_SJV  " +
                                 			" where ACCOUNTING_UNIT_ID=? and Cashbook_Year=? and CASHBOOK_MONTH=? and SUPPLEMENT_NO=?");
                                 	pss2.setInt(1,cmbAcc_UnitCode);
                               	    pss2.setInt(2,txtCB_Year);
                               	    pss2.setInt(3,txtCB_Month);
                               	    pss2.setInt(4,supNo);
                               	    rss2=pss2.executeQuery();
                               	    while(rss2.next())
                               	       {
                                   	    verified_already++;	
                                   	    }
                                   	  xml=xml+"<verified_already>"+verified_already+"</verified_already>";
                                      }
                                      catch(Exception ee)
                                      {
                                     	 
                                      }
                                  
                              }
                                  
                          }
                    catch(Exception e) 
                          {
                                  System.out.println("Exception in advno ===> "+e);   
                                  xml=xml+"<flag>failure</flag>";  
                          }
                      xml=xml+"</response>";
              }  
          else if(cmd.equalsIgnoreCase("submit_all")) 
          {
         	 int errcode=0,err=0;
         	 try{cmbAcc_UnitCode=Integer.parseInt(request.getParameter("cmbAcc_UnitCode"));}
              catch(NumberFormatException e){System.out.println("exception"+e );}
              try{cmbOffice_code=Integer.parseInt(request.getParameter("cmbOffice_code"));}
              catch(NumberFormatException e){System.out.println("exception"+e );}
              try{txtCB_Year=Integer.parseInt(request.getParameter("txtCB_Year"));}
              catch(NumberFormatException e){System.out.println("exception"+e );}
              try{txtCB_Month=Integer.parseInt(request.getParameter("txtCB_Month"));}
              catch(NumberFormatException e){System.out.println("exception in txtCB_Month:"+e );}
              try{supNo=Integer.parseInt(request.getParameter("supNo"));}
              catch(NumberFormatException e){System.out.println("exception"+e );}
              
              
                xml="<response><command>submit_all</command>";
                
                
                try
                {
             	   ps3=con.prepareStatement("Select Verify_Flag From FAS_SCH_EXP_VERIFY_MONTH_SJV Where Accounting_Unit_Id=? and ACCOUNTING_FOR_OFFICE_ID=? and Cashbook_Year=? and CASHBOOK_MONTH=? AND SUPPLEMENT_NO=? ");
             	   ps3.setInt(1,cmbAcc_UnitCode);
             	   ps3.setInt(2,cmbOffice_code);
             	   ps3.setInt(3,txtCB_Year);
             	   ps3.setInt(4,txtCB_Month);
             	   ps3.setInt(5,supNo);
             	   rs3=ps3.executeQuery();
             	   while(rs3.next())
             	   {
             		   ps4=con.prepareStatement("delete from FAS_SCH_EXP_VERIFY_MONTH_SJV Where Accounting_Unit_Id=? and ACCOUNTING_FOR_OFFICE_ID=? and Cashbook_Year=? and CASHBOOK_MONTH=? AND SUPPLEMENT_NO=? ");
             		   ps4.setInt(1,cmbAcc_UnitCode);
             		   ps4.setInt(2,cmbOffice_code);
             		   ps4.setInt(3,txtCB_Year);
             		   ps4.setInt(4,txtCB_Month);
             		   ps4.setInt(5,supNo);
             		  err= ps4.executeUpdate();
             	   }
             	   rs3.close();
             	   ps3.close();
                }
                catch(Exception qe)
                {
             	 System.out.println("error in delete");  
                }
                
                               
                try{
             	   ps2=con.prepareStatement("insert into FAS_SCH_EXP_VERIFY_MONTH_SJV(ACCOUNTING_UNIT_ID,ACCOUNTING_FOR_OFFICE_ID,CASHBOOK_YEAR,CASHBOOK_MONTH,VERIFY_FLAG,UPDATED_BY_USERID,UPDATED_DATE,SUPPLEMENT_NO) values(?,?,?,?,?,?,?,?)");
             	   ps2.setInt(1,cmbAcc_UnitCode);
             	   ps2.setInt(2,cmbOffice_code);
             	   ps2.setInt(3,txtCB_Year);
             	   ps2.setInt(4,txtCB_Month);
             	   ps2.setString(5,"Y");
             	   ps2.setString(6,userid);
             	   ps2.setTimestamp(7,ts);
             	   ps2.setInt(8,supNo);
             	   errcode=ps2.executeUpdate();
                    if(errcode==0)
                    {         
                        System.out.println("redirect");
                        xml=xml+"<flag>failure</flag>";  
                        
                    }
                    else
                    { 
                 	   xml=xml+"<flag>success</flag>";
                    }
             	
                }
                catch(Exception ee)
                {
             	   System.out.println("exception in submit:::"+ee);
                }
                xml=xml+"</response>";
          } 
          
          
          
          System.out.println(xml);
          out.println(xml);
          out.close();
         
         
}
}
