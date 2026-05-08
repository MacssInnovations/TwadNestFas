package Servlets.FAS.FAS1.JournalSystem.servlets;

import java.io.*;
import java.sql.*;
import java.util.ResourceBundle;

import javax.servlet.*;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;


public class Journal_General_Pending extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final String CONTENT_TYPE = null;
  
    public Journal_General_Pending() {
        super();
        
    }
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
      
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

          

      /**
       * Variables Declaration 
       */

       Connection con=null;
       ResultSet rs2=null,rs3=null,rs4=null;
       PreparedStatement ps2=null,ps3=null,ps4=null;
       String xml="<response>";
       response.setContentType(CONTENT_TYPE);
       response.setHeader("Cache-Control","no-cache");
       PrintWriter out = response.getWriter();
       int cmbAcc_UnitCode=0,cmbOffice_code=0,txtCash_Month_hid=0,txtCash_year=0;
   
       
       
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
                              ConnectionString = strdsn.trim() + "://" + strhostname.trim() + ":" + strportno.trim() + "/" +strsid.trim() ;    // Postgres DB  Connection                              Class.forName(strDriver.trim());
                              con=DriverManager.getConnection(ConnectionString,strdbusername.trim(),strdbpassword.trim());
       }
       catch(Exception e)
         {
            System.out.println("Exception in opening connection :"+e);
           
         }
     //  System.out.println("new wwwwwwwwwwwwwwwwwwwwwwwwwwwww");
       
       /** Get Accounting Unit ID */
               
       try{cmbAcc_UnitCode=Integer.parseInt(request.getParameter("cmbAcc_UnitCode"));}
       catch(NumberFormatException e){System.out.println("exception"+e );}
       System.out.println("cmbAcc_UnitCode...... "+cmbAcc_UnitCode);
       
       /** Get Accounting Office ID */        
       try{cmbOffice_code=Integer.parseInt(request.getParameter("cmbOffice_code"));}
       catch(NumberFormatException e){System.out.println("exception"+e );}
       System.out.println("cmbOffice_code......... "+cmbOffice_code);
       txtCash_year=Integer.parseInt(request.getParameter("year"));System.out.println("year...."+txtCash_year);
       txtCash_Month_hid=Integer.parseInt(request.getParameter("month"));     System.out.println("month"+txtCash_Month_hid);       
      
      
      try
      {
           
              String CONTENT_TYPE = "text/xml; charset=windows-1252";
              response.setContentType(CONTENT_TYPE);
              int type_MasSL=0;
                                  
              try{type_MasSL=Integer.parseInt(request.getParameter("type_MasSL"));}
              catch(NumberFormatException e){System.out.println("exception"+e );}
              System.out.println("cmbMas_SL_type................ "+type_MasSL);
              
//              try{cmbMaS_SL_code=Integer.parseInt(request.getParameter("cmbMaS_SL_code"));}
//              catch(NumberFormatException e){System.out.println("exception"+e );}
//              System.out.println("cmbMaS_SL_code "+cmbMaS_SL_code);
              
              
          
               String query="select trs.VOUCHER_NO,trs.SL_NO,to_char(mas.VOUCHER_DATE,'DD/MM/YYYY') as vou_date,'LJV' as vouType," +
                             " trs.ACCOUNT_HEAD_CODE ,decode (trs.CR_DR_INDICATOR,'DR','CR','CR','DR') as cr_dr_indicator ,trs.SUB_LEDGER_TYPE_CODE ,trs.SUB_LEDGER_CODE,trs.BILL_NO, trs.BILL_TYPE, " +
                             " trs.AGREEMENT_NO, to_char(trs.AGREEMENT_DATE,'DD/MM/YYYY') as agree_date,to_char(trs.BILL_DATE,'DD/MM/YYYY') as b_date ," +
                             " trim(to_char(trs.AMOUNT,'99999999999999.99')) as  AMOUNT,trs.PARTICULARS from  " +
                             " FAS_JOURNAL_MASTER mas,FAS_JOURNAL_TRANSACTION trs " +
                             " where mas.CASHBOOK_YEAR=? and trs.ACCOUNTING_UNIT_ID=? " +
                             " and trs.ACCOUNTING_FOR_OFFICE_ID=? " +
                             " and trs.CASHBOOK_MONTH=?  " +
                             " and mas.ACCOUNTING_FOR_OFFICE_ID=trs.ACCOUNTING_FOR_OFFICE_ID " +
                             " and mas.ACCOUNTING_UNIT_ID=trs.ACCOUNTING_UNIT_ID and mas.CASHBOOK_YEAR=trs.CASHBOOK_YEAR  " +
                             " and mas.CASHBOOK_MONTH=trs.CASHBOOK_MONTH and mas.VOUCHER_NO=trs.VOUCHER_NO and mas.JOURNAL_STATUS='L' " +
                             " and mas.CREATED_BY_MODULE='LJV' and trs.CB_REF_NO=0 and trs.CB_REF_DATE is  null and (mas.cb_ref_type !='A' or mas.cb_ref_type is null) "+

                             " union "+

                              "select trs.VOUCHER_NO,trs.SL_NO,to_char(mas.VOUCHER_DATE,'DD/MM/YYYY') as vou_date,'LJV' as vouType," +
                              " trs.ACCOUNT_HEAD_CODE , decode (trs.CR_DR_INDICATOR,'DR','CR','CR','DR') as cr_dr_indicator , trs.SUB_LEDGER_TYPE_CODE ,trs.SUB_LEDGER_CODE,trs.BILL_NO, trs.BILL_TYPE, " +
                              " trs.AGREEMENT_NO, to_char(trs.AGREEMENT_DATE,'DD/MM/YYYY') as agree_date,to_char(trs.BILL_DATE,'DD/MM/YYYY') as b_date ," +
                              " trim(to_char(trs.AMOUNT,'99999999999999.99')) as  AMOUNT,trs.PARTICULARS from  " +
                              " FAS_JOURNAL_MASTER mas,FAS_JOURNAL_TRANSACTION trs " +
                              " where mas.CASHBOOK_YEAR=? and trs.ACCOUNTING_UNIT_ID=? and trs.ACCOUNTING_FOR_OFFICE_ID=? " +
                              " and trs.CASHBOOK_MONTH=?  " +
                              " and mas.ACCOUNTING_FOR_OFFICE_ID=trs.ACCOUNTING_FOR_OFFICE_ID " +
                              " and mas.ACCOUNTING_UNIT_ID=trs.ACCOUNTING_UNIT_ID and mas.CASHBOOK_YEAR=trs.CASHBOOK_YEAR  " +
                              " and mas.CASHBOOK_MONTH=trs.CASHBOOK_MONTH and mas.VOUCHER_NO=trs.VOUCHER_NO and mas.JOURNAL_STATUS='L' " +
                              " and mas.CREATED_BY_MODULE='LJV' and trs.CB_REF_NO=0 and trs.CB_REF_DATE is  null and (mas.cb_ref_type !='A' or mas.cb_ref_type is null) " +
                              " order by vou_date,voucher_no";    // Adjustment for pending bills  ************* important to note -- mas.JOURNAL_TYPE_CODE=61
                           ps2=con.prepareStatement(query);
                           //System.out.println(query); 
                           ps2.setInt(1,txtCash_year);
                           ps2.setInt(2,cmbAcc_UnitCode);
                           ps2.setInt(3,cmbOffice_code);
                           ps2.setInt(4,txtCash_Month_hid);
                           ps2.setInt(5,txtCash_year);
                           ps2.setInt(6,cmbAcc_UnitCode);
                           ps2.setInt(7,cmbOffice_code);
                           ps2.setInt(8,txtCash_Month_hid);
                           rs2=ps2.executeQuery();
               int  cnt=0;
       while(rs2.next()) 
       {
          // System.out.println("while start");
           xml=xml+"<VOUCHER_NO>"+rs2.getInt("VOUCHER_NO")+"</VOUCHER_NO>";
           xml=xml+"<SL_NO>"+rs2.getInt("SL_NO")+"</SL_NO>";
           xml=xml+"<vou_date>"+rs2.getString("vou_date")+"</vou_date>";
           xml=xml+"<JOURNAL_TYPE>"+rs2.getString("vouType") +"</JOURNAL_TYPE>";
           
           xml=xml+"<AHcode>"+rs2.getInt("ACCOUNT_HEAD_CODE")+"</AHcode>";
           ps3=con.prepareStatement("select ACCOUNT_HEAD_DESC from COM_MST_ACCOUNT_HEADS where ACCOUNT_HEAD_CODE=?");
           ps3.setInt(1,rs2.getInt("ACCOUNT_HEAD_CODE"));
           rs3=ps3.executeQuery();
           if(rs3.next())
           xml=xml+"<AHdesc>"+rs3.getString("ACCOUNT_HEAD_DESC")+"</AHdesc>";
           ps3.close();
           rs3.close();
           xml=xml+"<CR_DR_ind>"+rs2.getString("CR_DR_INDICATOR")+
           "</CR_DR_ind><SL_Type>"+rs2.getInt("SUB_LEDGER_TYPE_CODE")+
           "</SL_Type>";
           if(rs2.getInt("SUB_LEDGER_TYPE_CODE")!=0)
           {
      
           ps3=con.prepareStatement("select SUB_LEDGER_TYPE_DESC from COM_MST_SL_TYPES where SUB_LEDGER_TYPE_CODE=?");
           ps3.setInt(1,rs2.getInt("SUB_LEDGER_TYPE_CODE"));
           rs3=ps3.executeQuery();
           if(rs3.next())
           xml=xml+"<SL_Desc>"+rs3.getString("SUB_LEDGER_TYPE_DESC")+"</SL_Desc>";
           }
           else
              {
              xml=xml+"<SL_Desc>"+null+"</SL_Desc>";   // it also return null value
         
              }
           
           rs3.close();                            
           ps3.close();
           
           xml=xml+"<SL_Code>"+rs2.getInt("SUB_LEDGER_CODE")+"</SL_Code>";
           //System.out.println("code>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>."+rs2.getInt("SUB_LEDGER_CODE"));
           if(rs2.getInt("SUB_LEDGER_TYPE_CODE")!=0)
             {
              //  System.out.println("insideeeeeeeeeee****************************");
                 ps4=con.prepareStatement("select sl_codename from SL_TYPE_CODE_NAME_VIEW where sl_type=? and sl_code=?");
                 ps4.setInt(1,rs2.getInt("SUB_LEDGER_TYPE_CODE"));
                 ps4.setInt(2,rs2.getInt("SUB_LEDGER_CODE"));
              //   System.out.println("SUB_LEDGER_CODE"+rs2.getInt("SUB_LEDGER_CODE"));
                 rs4=ps4.executeQuery();
               //  System.out.println("rs4:::::::::"+rs4);
                 if(rs4.next())
                 xml=xml+"<SL_name><![CDATA["+rs4.getString("sl_codename")+"]]></SL_name>";
                     
             }
             else
             {
                 xml=xml+"<SL_name>"+null+"</SL_name>";  
             }
//           rs4.close();                            
//           ps4.close();  
           // End of fetching sub-Ledger
                 
           
          // System.out.println("ha........stop 7");    
           xml=xml+"<Bill_NO>"+rs2.getString("BILL_NO")+"</Bill_NO>"+"<Bill_date>"+rs2.getString("b_date")+"</Bill_date>"+"<Bill_type>"
           +rs2.getString("BILL_TYPE")+"</Bill_type>"+"<Agree_No>"+rs2.getString("AGREEMENT_NO")+"</Agree_No>"+
           "<Agree_date>"+rs2.getString("agree_date")+"</Agree_date>"+
           "<sub_amount>"+rs2.getString("AMOUNT") +
           "</sub_amount><sub_part><![CDATA["+rs2.getString("PARTICULARS")+"]]></sub_part>";
          // System.out.println("hai stop 8");
           cnt++;
       }
       if(cnt==0)
           xml=xml+"<flag>failure</flag>";
       else
          xml=xml+"<flag>success</flag>";
       
       }
       catch(Exception e)
       {
       System.out.println("catch..HERE.in load head code."+e);
       xml=xml+"<flag>failure</flag>";
       }
       
       xml=xml+"</response>";
     //  System.out.println(xml);
       out.println(xml);
       }
    }
