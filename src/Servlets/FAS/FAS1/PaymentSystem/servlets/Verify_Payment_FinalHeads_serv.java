package Servlets.FAS.FAS1.PaymentSystem.servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.ResourceBundle;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;


public class Verify_Payment_FinalHeads_serv extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public Verify_Payment_FinalHeads_serv() {
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
        Connection con=null;
        ResultSet rs=null;
        
        PreparedStatement ps=null;
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
                                    ConnectionString = strdsn.trim() + "://" + strhostname.trim() + ":" + strportno.trim() + "/" +strsid.trim() ;    // Postgres DB  Connection                                    Class.forName(strDriver.trim());
                                    con=DriverManager.getConnection(ConnectionString,strdbusername.trim(),strdbpassword.trim());
                   }
                   catch(Exception e)
                       {
                          System.out.println("Exception in openeing connection :"+e);
                       }
        
      
       String CONTENT_TYPE = "text/xml; charset=windows-1252";
       response.setContentType(CONTENT_TYPE);
       PrintWriter out = response.getWriter();
       String strType = "",xml="<response>";
       try
       {
         strType = request.getParameter("Command");
       }
       catch(Exception e)
       {
         e.printStackTrace();
       }
       int  txtCB_Year=0, txtCB_Month=0,cmbAcc_UnitCode=0,cmbOffice_code=0;
       Date txtFrom_date=null,txtTo_date=null;
       Calendar c;
       String sql="",txtCreat_By_Module="",cmbStatus="";
       
       
       try{cmbAcc_UnitCode=Integer.parseInt(request.getParameter("cmbAcc_UnitCode"));}
       catch(NumberFormatException e){System.out.println("exception"+e );}
       
       
       try{cmbOffice_code=Integer.parseInt(request.getParameter("cmbOffice_code"));}
       catch(NumberFormatException e){System.out.println("exception"+e );}        
       txtCB_Year=Integer.parseInt(request.getParameter("txtCB_Year"));
       txtCB_Month=Integer.parseInt(request.getParameter("txtCB_Month"));       
       txtCreat_By_Module=request.getParameter("txtCreat_By_Module");       
       cmbStatus=request.getParameter("cmbStatus");
      System.out.println("cmbStatus>>>>>>>>>>>"+cmbStatus);
       if(strType.equalsIgnoreCase("searchByMonth"))  
       {
          xml="<response><command>searchByMonth</command>";            
           if(cmbStatus.equalsIgnoreCase("L"))
           {
           	
           	sql="SELECT distinct pay_mst.voucher_no, \n" + 
           	"    TO_CHAR(pay_mst.payment_date,'DD/MM/YYYY') AS vou_date ,\n" + 
           	" pay_mst.remarks,\n" + 
           	"  trim(TO_CHAR(pay_mst.total_amount,'99999999999999.99')) AS total_amount\n" + 
           	"       FROM fas_payment_master pay_mst,FAS_PAYMENT_TRANSACTION pay_trn \n" + 
           	"  WHERE \n" + 
           	"  pay_mst.accounting_unit_id      =?\n" + 
           	"  AND  pay_mst.accounting_for_office_id=?\n" + 
           	"  AND  pay_mst.cashbook_year           =?\n" + 
           	"  AND  pay_mst.cashbook_month          =?\n" + 
           	"  and  pay_mst.accounting_unit_id      =pay_trn.accounting_unit_id\n" + 
           	"  AND  pay_mst.accounting_for_office_id=pay_trn.accounting_for_office_id\n" + 
           	"  AND  pay_mst.cashbook_year           =pay_trn.cashbook_year\n" + 
           	"  AND  pay_mst.cashbook_month          =pay_trn.cashbook_month\n" + 
           	"  AND  pay_mst.VOUCHER_NO              =pay_trn.VOUCHER_NO\n" + 
           	"  AND  pay_mst.payment_status=?					\n" + 
           	"  AND  pay_mst.created_by_module       in('BPF','BPP') \n" + 
           	"  AND  (pay_trn.VERIFIED  IS NULL or pay_trn.VERIFIED='N') order by pay_mst.voucher_no";
           }
           else
           {
           		
           		sql="SELECT distinct pay_mst.voucher_no, \n" + 
           		"    TO_CHAR(pay_mst.payment_date,'DD/MM/YYYY') AS vou_date ,\n" + 
           		" pay_mst.remarks,\n" + 
           		"  trim(TO_CHAR(pay_mst.total_amount,'99999999999999.99')) AS total_amount\n" + 
           		"       FROM fas_payment_master pay_mst,FAS_PAYMENT_TRANSACTION pay_trn \n" + 
           		"  WHERE \n" + 
           		"  pay_mst.accounting_unit_id      =?\n" + 
           		"  AND  pay_mst.accounting_for_office_id=?\n" + 
           		"  AND  pay_mst.cashbook_year           =?\n" + 
           		"  AND  pay_mst.cashbook_month          =?\n" + 
           		"  and  pay_mst.accounting_unit_id      =pay_trn.accounting_unit_id\n" + 
           		"  AND  pay_mst.accounting_for_office_id=pay_trn.accounting_for_office_id\n" + 
           		"  AND  pay_mst.cashbook_year           =pay_trn.cashbook_year\n" + 
           		"  AND  pay_mst.cashbook_month          =pay_trn.cashbook_month\n" + 
           		"  AND  pay_mst.VOUCHER_NO              =pay_trn.VOUCHER_NO\n" + 
           		"  AND  pay_mst.payment_status=?					\n" + 
           		"  AND  pay_mst.created_by_module       in ('BPF','BPP') \n" + 
           		"  AND  (pay_trn.VERIFIED  IS NULL or pay_trn.VERIFIED='N') order by pay_mst.voucher_no";
		            	
           }
         //  System.out.println("SQL::::"+sql);
          try
          {
           int count=0;
           ps=con.prepareStatement(sql);
           ps.setInt(1,cmbAcc_UnitCode);
           ps.setInt(2,cmbOffice_code);
           ps.setInt(3,txtCB_Year);
           ps.setInt(4,txtCB_Month);
           ps.setString(5,cmbStatus);
           xml=xml+"<flag>success</flag><Ucode>"+cmbAcc_UnitCode+"</Ucode><Offid>"+cmbOffice_code+
           "</Offid><txtCB_Year>"+txtCB_Year+"</txtCB_Year><txtCB_Month>"+txtCB_Month+"</txtCB_Month>";
           rs=ps.executeQuery();
               while(rs.next())
               {
            	  
                   xml=xml+"<leng>";
                   xml=xml+"<Rec_no>"+rs.getInt("VOUCHER_NO")+"</Rec_no>";
                   xml=xml+"<Rec_Date>"+rs.getString("vou_date")+"</Rec_Date>";
                  // xml=xml+"<Remak>"+rs.getString("REMARKS")+"</Remak>";
                   xml=xml+"<Remak><![CDATA["+rs.getString("REMARKS")+"]]></Remak>";
                   xml=xml+"<Tot_Amt>"+rs.getString("total_amount") +"</Tot_Amt>";
                   
                   xml=xml+"</leng>";
                   count++;
               }
              if(count==0) 
              {
                 System.out.println("inside count==0");
                 xml="<response><command>searchByMonth</command><flag>failure</flag>";
              }
          }
          catch(SQLException sqle)
           {
       	   sqle.printStackTrace();
       	   System.out.println("error while fetching data " + sqle);
               xml="<response><command>searchByMonth</command><flag>failure</flag>";
           }
           
       }
     
       if(strType.equalsIgnoreCase("searchByDate"))
       {
           xml="<response><command>searchByDate</command>";
           
           String[] sd=request.getParameter("txtFrom_date").split("/");
           c=new GregorianCalendar(Integer.parseInt(sd[2]),Integer.parseInt(sd[1])-1,Integer.parseInt(sd[0]));
           java.util.Date d=c.getTime();
           txtFrom_date=new Date(d.getTime());
           
           sd=request.getParameter("txtTo_date").split("/");
           c=new GregorianCalendar(Integer.parseInt(sd[2]),Integer.parseInt(sd[1])-1,Integer.parseInt(sd[0]));
           d=c.getTime();
           txtTo_date=new Date(d.getTime());
         
           if(cmbStatus.equalsIgnoreCase("L"))
           {
           
           	sql="SELECT distinct pay_mst.voucher_no, \n" + 
           	"    TO_CHAR(pay_mst.payment_date,'DD/MM/YYYY') AS vou_date ,\n" + 
           	"  pay_mst.remarks,\n" + 
           	"  trim(TO_CHAR(pay_mst.total_amount,'99999999999999.99')) AS total_amount\n" + 
           	"  FROM fas_payment_master pay_mst,FAS_PAYMENT_TRANSACTION pay_trn \n" + 
           	"  where  \n" + 
           	"  pay_mst.accounting_unit_id      =?\n" + 
           	"  AND  pay_mst.accounting_for_office_id=?\n" + 
           	"  and  pay_mst.accounting_unit_id      =pay_trn.accounting_unit_id\n" + 
           	"  AND  pay_mst.accounting_for_office_id=pay_trn.accounting_for_office_id and\n" + 
           	"  pay_mst.CASHBOOK_YEAR=pay_trn.CASHBOOK_YEAR and\n" + 
           	"  pay_mst.CASHBOOK_MONTH=pay_trn.CASHBOOK_MONTH and\n" + 
                "  pay_mst.VOUCHER_NO              =pay_trn.VOUCHER_NO and\n"+
           	"  (pay_mst.payment_date between ? and ?) and  \n" + 
           	"  pay_mst.payment_status          =? \n" + 
               "  AND  pay_mst.created_by_module       in ('BPF','BPP') \n" + 
           	"  AND (pay_trn.VERIFIED IS NULL or pay_trn.VERIFIED='N') order by pay_mst.voucher_no";
           }
           else
           {
           		
           		sql="SELECT distinct pay_mst.voucher_no as voucher_no, \n" + 
           		"    TO_CHAR(pay_mst.payment_date,'DD/MM/YYYY') AS vou_date ,\n" + 
           		"  pay_mst.remarks,\n" + 
           		"  trim(TO_CHAR(pay_mst.total_amount,'99999999999999.99')) AS total_amount\n" + 
           		"   FROM fas_payment_master pay_mst,FAS_PAYMENT_TRANSACTION pay_trn \n" + 
           		"  where  \n" + 
           		"  pay_mst.accounting_unit_id      =?\n" + 
           		"  AND  pay_mst.accounting_for_office_id=?\n" + 
           		"   and  pay_mst.accounting_unit_id      =pay_trn.accounting_unit_id\n" + 
           		"   AND  pay_mst.accounting_for_office_id=pay_trn.accounting_for_office_id and\n" + 
           		"  pay_mst.CASHBOOK_YEAR=pay_trn.CASHBOOK_YEAR and\n" + 
           		"  pay_mst.CASHBOOK_MONTH=pay_trn.CASHBOOK_MONTH and\n" + 
                        "  pay_mst.VOUCHER_NO              =pay_trn.VOUCHER_NO and\n"+
           		"  (pay_mst.payment_date between ? and ?) and  \n" + 
           		"  pay_mst.payment_status          =? \n" + 
               "  AND  pay_mst.created_by_module       in ('BPF','BPP') \n" + 
           		"  AND (pay_trn.VERIFIED IS NULL or pay_trn.VERIFIED='N') order by pay_mst.voucher_no";
		            	
           }
        //   System.out.println("SQL::::"+sql);
          try
          {
            int count=0;
           ps=con.prepareStatement(sql);
           ps.setInt(1,cmbAcc_UnitCode);
           ps.setInt(2,cmbOffice_code);       
           ps.setDate(3,txtFrom_date);
           ps.setDate(4,txtTo_date);
        //   ps.setString(5,txtCreat_By_Module);
           ps.setString(5,cmbStatus);
            xml=xml+"<flag>success</flag><Ucode>"+cmbAcc_UnitCode+"</Ucode><Offid>"+cmbOffice_code+
            "</Offid><txtCB_Year>"+txtCB_Year+"</txtCB_Year><txtCB_Month>"+txtCB_Month+"</txtCB_Month>";
              rs=ps.executeQuery();
                  while(rs.next())
                  {
                      xml=xml+"<leng>";
                      xml=xml+"<Rec_no>"+rs.getInt("VOUCHER_NO")+"</Rec_no>";
                      xml=xml+"<Rec_Date>"+rs.getString("vou_date")+"</Rec_Date>";
                      xml=xml+"<Remak><![CDATA["+rs.getString("REMARKS")+"]]></Remak>";
                  //    xml=xml+"<Remak>"+rs.getString("REMARKS")+"</Remak>";
                      xml=xml+"<Tot_Amt>"+rs.getString("total_amount")+"</Tot_Amt>";
                      
                      xml=xml+"</leng>";
                      count++;
                  }
                 if(count==0) 
                 {
                    xml="<response><command>searchByDate</command><flag>failure</flag>";
                 }
              }
              catch(SQLException sqle)
              {
                System.out.println("error while fetching data " + sqle);
                  xml="<response><command>searchByDate</command><flag>failure</flag>";
              }
       }
       xml=xml+"</response>";   
       out.println(xml); 
       System.out.println("xml last:"+xml); 
      
   }
		
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
       // System.out.println("dopostttttttttttttttttttttttttttt");

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
		 * Variables Declaration 
		 */		        
		Connection con=null;
		PreparedStatement ps2=null,ps=null;        
		ResultSet rs2=null,rs=null;
                Date txtCrea_date=null;
                Calendar c;
                int txtCash_year=0,txtCash_Month_hid=0;
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
			ConnectionString = strdsn.trim() + "://" + strhostname.trim() + ":" + strportno.trim() + "/" +strsid.trim() ;    // Postgres DB  Connection			Class.forName(strDriver.trim());
			con=DriverManager.getConnection(ConnectionString,strdbusername.trim(),strdbpassword.trim());
		}
		catch(Exception e)
		{
			System.out.println("Exception in opening connection :"+e);
		}
		int cmbAcc_UnitCode=0,cmbOffice_code=0;

		System.out.println("do post starts");
		
		String update_user=(String)session.getAttribute("UserId");
		long l=System.currentTimeMillis();
		Timestamp ts=new Timestamp(l);                      
		 Date ctdate = new java.sql.Date(ts.getTime());  
		
		String chckparameter_Voucher_no[]=null; 
		try{cmbAcc_UnitCode=Integer.parseInt(request.getParameter("cmbAcc_UnitCode"));}
		catch(NumberFormatException e){System.out.println("exception"+e );}
	//	System.out.println("cmbAcc_UnitCode "+cmbAcc_UnitCode);

		try{cmbOffice_code=Integer.parseInt(request.getParameter("cmbOffice_code"));}
		catch(NumberFormatException e){System.out.println("exception"+e );}
	//	System.out.println("cmbOffice_code "+cmbOffice_code);

		String[] voucherno1=request.getParameterValues("voucherNo");
		//System.out.println("voucherno1"+voucherno1);
		String[] voucherDate1=request.getParameterValues("voucherDate"); 
	    System.out.println("voucherDate1"+voucherDate1);
		int voucherno2=0;
		try{
			con.clearWarnings();
			con.setAutoCommit(false);
			chckparameter_Voucher_no = request.getParameterValues("chckparameter"); 
		    System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~chckparameter_Voucher_no>>>>"+chckparameter_Voucher_no);
                    
                    
			System.out.println("chckparameter_Voucher_no>>>"+chckparameter_Voucher_no.length);
                    
			for(int i=0;i<chckparameter_Voucher_no.length;i++)
			{
                        int asg=Integer.parseInt(chckparameter_Voucher_no[i]);
                        System.out.println("voucherDate1[asg]>>>>"+voucherDate1[asg]);
                        
                         String[] sd = voucherDate1[asg].split("/");
                         c =
                         new GregorianCalendar(Integer.parseInt(sd[2]), Integer.parseInt(sd[1]) - 1,
                                      Integer.parseInt(sd[0]));
                         java.util.Date d = c.getTime();
                         txtCrea_date = new Date(d.getTime());
                         System.out.println("txtCrea_date " + txtCrea_date);

                         System.out.println("b4 getting month and year");
                         try {
                             txtCash_year = Integer.parseInt(sd[2]);
                         } catch (Exception e) {
                             System.out.println("exception" + e);
                         }
                         System.out.println("txtCash_year<<<<<<< " + txtCash_year);

                         try {
                             txtCash_Month_hid = Integer.parseInt(sd[1]);
                         } catch (Exception e) {
                             System.out.println("exception" + e);
                         }
                         System.out.println("txtCash_Month_hid<<<<<< " + txtCash_Month_hid); 
                
                       
				ps=con.prepareStatement("update FAS_PAYMENT_TRANSACTION set VERIFIED='Y',VERIFIED_BY=?,VERIFIED_DATE=? where \n" + 
				"ACCOUNTING_UNIT_ID=? and \n" + 
				"ACCOUNTING_FOR_OFFICE_ID=? and VOUCHER_NO=? and CASHBOOK_YEAR=? and CASHBOOK_MONTH=? ");
				ps.setString(1, update_user);
				ps.setDate(2, ctdate);
				ps.setInt(3, cmbAcc_UnitCode); 
				ps.setInt(4, cmbOffice_code);  
			    voucherno2=Integer.parseInt(voucherno1[asg]); 
                         //   System.out.println("voucherno2:::"+voucherno2);
				ps.setInt(5, voucherno2); 
				ps.setInt(6, txtCash_year);
                                ps.setInt(7, txtCash_Month_hid); 
                                //System.out.println("voucherDate1[i]"+voucherDate1[asg]);
				int up=ps.executeUpdate();  
             
                                if(up==0)
                                {
                                    System.out.println("redirect");                                
                                    sendMessage(response," Payment verified Failed ","ok"); 
                                }else{
                                        sendMessage(response,"The Payment Voucher Number  has been Verified ","ok");   
                                    con.commit();
                                }

			}  
		
		}
	catch(Exception e){System.out.println(e);e.printStackTrace();
		try {
			con.rollback();
		}
		catch (SQLException e1) {
			e1.printStackTrace();
		}
		}
		try{
			con.commit();
		}catch(Exception e){System.out.println(e);}
		
	}
	 private void sendMessage(HttpServletResponse response,String msg,String bType)
     {
         try
         {
             String url="org/Library/jsps/MessengerOkBack.jsp?message=" + msg + "&button=" + bType;
             response.sendRedirect(url);
         }
         catch(Exception e)
         {
                 System.out.println("error in messenger"+e);
         }
     }

}
