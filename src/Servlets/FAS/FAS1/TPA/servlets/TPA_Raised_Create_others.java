package Servlets.FAS.FAS1.TPA.servlets;

import java.io.IOException;
import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.ResourceBundle;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Servlet implementation class TPA_Raised_Create_others
 */
public class TPA_Raised_Create_others extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
   
    public TPA_Raised_Create_others() {
        super();
        // TODO Auto-generated constructor stub
    }

	

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
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
	      String sql=null;      
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
                 ConnectionString = strdsn.trim() + "://" + strhostname.trim() + ":" + strportno.trim() + "/" +strsid.trim() ;    // Postgres DB  Connection                 Class.forName(strDriver.trim());
                 con=DriverManager.getConnection(ConnectionString,strdbusername.trim(),strdbpassword.trim());
         }
         catch(Exception e)
         {
             	  System.out.println("Exception in opening connection :"+e);
         }
         Calendar c;
         int txtAcc_HeadCode=0,cmbAcc_UnitCode=0,cmbOffice_code=0,txtCash_Month_hid=0,txtCash_year=0,txtUnitId=0,count=0;
         String cr_dr=null,reason4Trf=null,particulars=null;
         Date txtCrea_date=null,txtVoucher_date=null;
         String update_user=(String)session.getAttribute("UserId");
         long l=System.currentTimeMillis();
         Timestamp ts=new Timestamp(l);                      
         int errcode=0;
         
         //~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
         
            try{cmbAcc_UnitCode=Integer.parseInt(request.getParameter("cmbAcc_UnitCode"));}
            catch(NumberFormatException e){System.out.println("exception"+e );}
            System.out.println("cmbAcc_UnitCode "+cmbAcc_UnitCode);
            
            try{cmbOffice_code=Integer.parseInt(request.getParameter("cmbOffice_code"));}
            catch(NumberFormatException e){System.out.println("exception"+e );}
            System.out.println("cmbOffice_code "+cmbOffice_code);
            
            String[] sd=request.getParameter("Voucher_Date").split("/");
            c=new GregorianCalendar(Integer.parseInt(sd[2]),Integer.parseInt(sd[1])-1,Integer.parseInt(sd[0]));
            java.util.Date d=c.getTime();
            txtCrea_date=new Date(d.getTime());
    
            try{txtCash_year=Integer.parseInt(sd[2]);}
            catch(Exception e){System.out.println("exception"+e );}
            System.out.println("txtCash_year "+txtCash_year);
            
            try{txtCash_Month_hid=Integer.parseInt(sd[1]);}
            catch(Exception e){System.out.println("exception"+e );}
            System.out.println("txtCash_Month_hid "+txtCash_Month_hid);
            int Originated_SL_No=0;
            int fin_year_from=0,fin_year_to=0;
            
            //////////////////////Financial year calculation/////////////////////////////////
            if(txtCash_Month_hid>3)
            {
           	 	  fin_year_from=txtCash_year;
           	 	  fin_year_to=txtCash_year+1;
            }
            else
            {
           	 	  fin_year_from=txtCash_year-1;
           	 	  fin_year_to=txtCash_year;
            }
            System.out.println("fin_year_from ::: "+fin_year_from+"  fin_year_to:::"+fin_year_to);
            try
            {
           	 	  sql="SELECT VOUCHER_NO FROM FAS_TPA_MASTER GROUP BY VOUCHER_NO HAVING  "+
           	 	  	  " VOUCHER_NO =(select max(VOUCHER_NO) from FAS_TPA_MASTER where to_date(CASHBOOK_MONTH||'-'||CASHBOOK_YEAR,'mm-yyyy') between to_date(4||'-'||?,'mm-yyyy') and to_date(3||'-'||?,'mm-yyyy'))";	
                     ps=con.prepareStatement(sql);
                     ps.setInt(1,fin_year_from);
                     ps.setInt(2,fin_year_to);
           	 	  rs=ps.executeQuery();
                     if(rs.next()) 
                     {
                   	  Originated_SL_No = rs.getInt(1);                                               
                     }
                     Originated_SL_No=Originated_SL_No+1;
                     rs.close();
            }           	    
            catch(Exception e){System.out.println("exception"+e );}
            System.out.println("Originated_SL_No "+Originated_SL_No);
            
            String[] sd1=request.getParameter("Voucher_Date").split("/");
            c=new GregorianCalendar(Integer.parseInt(sd1[2]),Integer.parseInt(sd1[1])-1,Integer.parseInt(sd1[0]));
            java.util.Date d1=c.getTime();
            txtVoucher_date=new Date(d1.getTime());
            
            try{cr_dr=request.getParameter("Org_CR_DR");}
            catch(NumberFormatException e){System.out.println("exception"+e );}
            System.out.println("Org_CR_DR "+cr_dr);
            
            if(cr_dr.equals("CR"))
           	 cr_dr="TPAOC";
            else
           	 cr_dr="TPAOD";
            
            try{txtUnitId=Integer.parseInt(request.getParameter("TransferedID"));}
            catch(NumberFormatException e){System.out.println("exception"+e );}
            System.out.println("txtUnitId "+txtUnitId);
            	                                     
            try{reason4Trf=request.getParameter("Reason4Trf");}
            catch(NumberFormatException e){System.out.println("exception"+e );}
            System.out.println("txtRemarks "+reason4Trf);
            
            try{particulars=request.getParameter("GenParticulars");}
            catch(Exception e){System.out.println("exception"+e );}
            System.out.println("txtAmount "+particulars);
            boolean flag=true;
            //~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
            try
            {
	             try
	       	 	 {
	                     con.clearWarnings();
	                     con.setAutoCommit(false);
	                     ps=con.prepareStatement("insert into FAS_TPA_MASTER(ACCOUNTING_UNIT_ID,ACCOUNTING_FOR_OFFICE_ID,CASHBOOK_YEAR,CASHBOOK_MONTH,VOUCHER_NO,TPA_TYPE,VOUCHER_DATE,TRF_ACCOUNTING_UNIT_ID,REASON_FOR_TRANSFER,PARTICULARS,STATUS,UPDATED_BY_USERID,UPDATED_DATE) values (?,?,?,?,?,?,?,?,?,?,?,?,?)");	                     
	                     ps.setInt(1,cmbAcc_UnitCode);
	                     ps.setInt(2,cmbOffice_code);
	                     ps.setInt(3,txtCash_year);
	                     ps.setInt(4,txtCash_Month_hid);
	                     ps.setInt(5,Originated_SL_No);
	                     ps.setString(6,cr_dr);
	                     ps.setDate(7,txtCrea_date);
	                     ps.setInt(8,txtUnitId);                      
	                     ps.setString(9,reason4Trf);               
	                     ps.setString(10,particulars);
	                     ps.setString(11,"L");
	                     ps.setString(12,update_user);
	                     ps.setTimestamp(13,ts);
	                     errcode=ps.executeUpdate();
	       	 	 }catch(Exception e){System.out.println("Err in first table create :: "+e.getMessage());}
                if(errcode==0)
                {         
	                     System.out.println("redirect");
	                     flag=false;                                   
                }
               
                else
                {
               	 	 int acc_head_code=0,cmbSL_type=0,cmbSL_Code=0,SL_NO=0;
               	 	 int acode_gl=0;
               	 	 double amount=0,txtsub_Amount=0;
               	 	double txtsub_Amount_gl=0;
               	 	 String cr_dr_indicator=null,det_particulars=null;
               	 	 System.out.println("First tableinserted successfully");
               	 
               	 	 
               	 	 String Grid_H_code[]=request.getParameterValues("H_code");
               	 	 String Grid_SL_type[]=request.getParameterValues("SL_type");
                        String Grid_SL_code[]=request.getParameterValues("SL_code"); 
                        String Grid_CR_DR[]=request.getParameterValues("CR_DR_type");
                        String Grid_Amt[]=request.getParameterValues("sl_amt");
                        String Grid_particular[]=request.getParameterValues("sl_particular");
                      
                       try
                        {	                        	 
                       	 sql="insert into FAS_TPA_TRANSACTION(ACCOUNTING_UNIT_ID,ACCOUNTING_FOR_OFFICE_ID,CASHBOOK_YEAR,CASHBOOK_MONTH,VOUCHER_NO,SL_NO,ACCOUNT_HEAD_CODE,CR_DR_INDICATOR,SUB_LEDGER_TYPE_CODE,SUB_LEDGER_CODE,AMOUNT,PARTICULARS,UPDATED_BY_USER_ID,UPDATED_DATE) values(?,?,?,?,?,?,?,?,?,?,?,?,?,?)" ;                          
                            ps=con.prepareStatement(sql);
                            System.out.println("sl length:::"+Grid_H_code.length);
                          
                            for(int k=0;k<Grid_H_code.length;k++) 
                            {
                            	
                               //   txtAcc_HeadCode=0;txtsub_Amount=0;                                             
                                  SL_NO++;
                                  ps.setInt(1,cmbAcc_UnitCode);     
                                  ps.setInt(2,cmbOffice_code);    
                                  ps.setInt(3,txtCash_year);
                                  System.out.println("txtCash_Month_hid :: "+txtCash_Month_hid);
                                  ps.setInt(4,txtCash_Month_hid);
                                  ps.setInt(5,Originated_SL_No);       
                                  ps.setInt(6,SL_NO);
                                 
                               	   txtAcc_HeadCode=Integer.parseInt(Grid_H_code[k]);
                                      ps.setInt(7,txtAcc_HeadCode);
                                    System.out.println("*********"+txtAcc_HeadCode);
                                      String rad_sub_CR_DR=Grid_CR_DR[k];                               
                                      ps.setString(8,rad_sub_CR_DR);   
                                      
                                      try{cmbSL_type=Integer.parseInt(Grid_SL_type[k]);}
                                      catch(NumberFormatException e){System.out.println("exception"+e );}
                                      ps.setInt(9,cmbSL_type); 
                                      
                                      try{cmbSL_Code=Integer.parseInt(Grid_SL_code[k]);}
                                      catch(NumberFormatException e){System.out.println("exception"+e );}
                                      ps.setInt(10,cmbSL_Code);
                                                                     
                                      try{txtsub_Amount=Double.parseDouble(Grid_Amt[k]);}
                                      catch(NumberFormatException e){System.out.println("exception"+e );}
                                      ps.setDouble(11,txtsub_Amount);
                                      
                                      ps.setString(12,Grid_particular[k]);  
                                     
                                  
                                  ps.setString(13,update_user);
                                  ps.setTimestamp(14,ts);
                                  int i=ps.executeUpdate(); 
                                  if(i>0)
                                  {
                                      count++;
                                      System.out.println("------------------------"+SL_NO+" inserted successfully");
                                  }
                                  else
                                  {
                                	  flag=false;
                                  }
                                 
                            }
                          
                              
                              if(count==Grid_H_code.length)
                       	   		   flag=true;
                              else
                           	   	   flag=false;
                        }
                        catch(Exception e)
                        {
                       	 e.printStackTrace();
                       	   System.out.println("Exp in 2 nd table ::: "+e.getMessage());	        
                       	   flag=false;       
                        }
                }
            }
            catch(Exception e)
            {
       	 	 System.out.println("Err in insertion :: "+e.getMessage());
       	 	 flag=false;  
            }
            try
            {	
           	 if(flag==true)
	             {
	            	 sendMessage(response,"The TPA Sl.No '"+Originated_SL_No+"' has been Created Successfully ","ok");
                    con.commit();
	             }
	             else
                {
		             System.out.println("b4 Rollback");
		             sendMessage(response,"The TPA Creation Failed ","ok");
		             con.rollback(); 
                }
            }
            catch(Exception e)
            {
           	 System.out.println("b4 Rollback");
	             sendMessage(response,"The TPA Creation Failed ","ok");
	             //con.rollback();
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
	        }
    }
}
