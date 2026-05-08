package Servlets.FAS.FAS1.Imprest.servlets;

import java.io.IOException;
import java.io.PrintWriter;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.ResourceBundle;
import javax.servlet.*;
import javax.servlet.http.*;

public class Imprest_Account_List extends HttpServlet 
{
   
    public void init(ServletConfig config) throws ServletException
    {
        super.init(config);
       
    }
    
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
    {   
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
                                     ConnectionString = strdsn.trim() + "://" + strhostname.trim() + ":" + strportno.trim() + "/" +strsid.trim() ;    // Postgres DB  Connection                                     Class.forName(strDriver.trim());
                                     con=DriverManager.getConnection(ConnectionString,strdbusername.trim(),strdbpassword.trim());
                    }
                    catch(Exception e)
                        {
                           System.out.println("Exception in openeing connection :"+e);
            //               sendMessage(response,"probably Failed to Establish connection to the database server.. due to "+e,"ok");

                        }
         
        System.out.println("servlet called");
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
        String sql="",txtCreat_By_Module="",cmbStatus="",txtMode_of_creat="";
        
        
        try{cmbAcc_UnitCode=Integer.parseInt(request.getParameter("cmbAcc_UnitCode"));}
        catch(NumberFormatException e){System.out.println("exception"+e );}
        
        
        try{cmbOffice_code=Integer.parseInt(request.getParameter("cmbOffice_code"));}
        catch(NumberFormatException e){System.out.println("exception"+e );}        
        txtCB_Year=Integer.parseInt(request.getParameter("txtCB_Year"));
        txtCB_Month=Integer.parseInt(request.getParameter("txtCB_Month"));       
        txtCreat_By_Module=request.getParameter("txtCreat_By_Module");       
        cmbStatus=request.getParameter("cmbStatus");
        try{txtMode_of_creat=request.getParameter("txtMode_of_creat");}
        catch(Exception e){System.out.println("Err in get txtMode_of_creat :: "+e.getMessage());}
        System.out.println("cmbAcc_UnitCode "+cmbAcc_UnitCode);
        System.out.println("cmbOffice_code "+cmbOffice_code);
        System.out.println("strtype  "+strType);
        System.out.println("cmbStatus.."+cmbStatus+" txtCreat_By_Module:  "+txtCreat_By_Module);
        System.out.println("year..."+txtCB_Year);
        System.out.println("Month..."+txtCB_Month);
        if(strType.equalsIgnoreCase("searchByMonth"))  
        {
        	System.out.println("inside if");
            xml="<response><command>searchByMonth</command>";            
            if(cmbStatus.equalsIgnoreCase("Y"))
            {
            	System.out.println("Inside cheque cancelled 1st");
            	sql="select a.voucher_no,"+
					       "to_char(payment_date,'DD/MM/YYYY') as rec_date ,"+
					       "a.remarks,"+
					       "trim(to_char(total_amount,'99999999999999.99')) as total_amount,"+
					       "b.voucher_no as GJV_No,"+
					       "b.cheque_dd_date as GJV_Date "+
					"from fas_payment_master a,"+
					     "fas_journal_transaction b "+
					"where "+ 
					      "a.voucher_no=b.cb_ref_no and "+
					      "a.payment_date=b.cheque_dd_date and "+ 
					      "a.accounting_unit_id=? and "+ 
					      "a.accounting_for_office_id=? and "+ 
					      "a.cashbook_year=? and "+ 
					      "a.cashbook_month=? and "+ 
					      "a.created_by_module=? and "+
					      "a.cheq_cancel_status=? and "+
					      "(a.mode_of_creation='"+txtMode_of_creat+"' or a.mode_of_creation='IT')" ;      
            	}
            else
            {
            		System.out.println("Inside Alive/cancelled..2nd");	
            		 sql="select voucher_no,"+
					       "to_char(payment_date,'DD/MM/YYYY') as rec_date ,"+
					       "remarks,"+
					       "trim(to_char(total_amount,'99999999999999.99')) as total_amount "+					     
					"from fas_payment_master "+
					"where "+ 
					      "accounting_unit_id=? and "+ 
					      "accounting_for_office_id=? and "+ 
					      "cashbook_year=? and "+ 
					      "cashbook_month=? and "+ 
					      "created_by_module=? and "+
					      "payment_status=? and "+
					      "(mode_of_creation='"+txtMode_of_creat+"' or mode_of_creation='IT')" ;  
            	 }
            System.out.println("SQL for searchByMonth::::"+sql);
           try
           {
            int count=0;
            ps=con.prepareStatement(sql);
            ps.setInt(1,cmbAcc_UnitCode);System.out.println(cmbAcc_UnitCode);
            ps.setInt(2,cmbOffice_code);System.out.println(cmbOffice_code);
            ps.setInt(3,txtCB_Year);System.out.println(txtCB_Year);
            ps.setInt(4,txtCB_Month);System.out.println(txtCB_Month);
            ps.setString(5,txtCreat_By_Module);System.out.println(txtCreat_By_Module);
            ps.setString(6,cmbStatus);System.out.println(cmbStatus);
        //    ps.setString(7,txtMode_of_creat);System.out.println(txtMode_of_creat);
            xml=xml+"<flag>success</flag><Ucode>"+cmbAcc_UnitCode+"</Ucode><Offid>"+cmbOffice_code+
            "</Offid><txtCB_Year>"+txtCB_Year+"</txtCB_Year><txtCB_Month>"+txtCB_Month+"</txtCB_Month>";
            rs=ps.executeQuery();
            System.out.println("rs"+rs);
            
                while(rs.next())
                {
                	System.out.println("inside while loop");
                    xml=xml+"<leng>";
                    xml=xml+"<Rec_no>"+rs.getInt("VOUCHER_NO")+"</Rec_no>";
                    xml=xml+"<Rec_Date>"+rs.getString("rec_date")+"</Rec_Date>";
                    xml=xml+"<Remak>"+rs.getString("REMARKS")+"</Remak>";
                    xml=xml+"<Tot_Amt>"+rs.getString("TOTAL_AMOUNT") +"</Tot_Amt>";
                    if(cmbStatus.equalsIgnoreCase("Y"))
                    {
                    	xml=xml+"<GJV_No>"+rs.getInt("GJV_No") +"</GJV_No>";
                    	xml=xml+"<GJV_Date>"+rs.getString("GJV_Date") +"</GJV_Date>";
                    }
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
        System.out.println("here "+strType.equalsIgnoreCase("searchByDate"));
        if(strType.equalsIgnoreCase("searchByDate"))
        {
            xml="<response><command>searchByDate</command>";
            System.out.println("here "+strType.equalsIgnoreCase("searchByDate"));
           
            String[] sd=request.getParameter("txtFrom_date").split("/");
            c=new GregorianCalendar(Integer.parseInt(sd[2]),Integer.parseInt(sd[1])-1,Integer.parseInt(sd[0]));
            java.util.Date d=c.getTime();
            txtFrom_date=new Date(d.getTime());
            System.out.println("from_date "+txtFrom_date);
            
            sd=request.getParameter("txtTo_date").split("/");
            c=new GregorianCalendar(Integer.parseInt(sd[2]),Integer.parseInt(sd[1])-1,Integer.parseInt(sd[0]));
            d=c.getTime();
            txtTo_date=new Date(d.getTime());
            System.out.println("txtTo_date "+txtTo_date);
            /*sql="select VOUCHER_NO,to_char(PAYMENT_DATE,'DD/MM/YYYY') as rec_date,REMARKS, trim(to_char(TOTAL_AMOUNT,'99999999999999.99')) as TOTAL_AMOUNT from FAS_PAYMENT_MASTER where ACCOUNTING_UNIT_ID=? and  " +
            "ACCOUNTING_FOR_OFFICE_ID=? and CASHBOOK_YEAR=? and CASHBOOK_MONTH=? and PAYMENT_DATE>=? and PAYMENT_DATE<=? and CREATED_BY_MODULE=?";
            if(cmbStatus.equalsIgnoreCase("Y"))
            {
            	System.out.println("Cheque Cancelled");
            	sql=sql+" and CHEQ_CANCEL_STATUS=?";
            }
            else
            {
            	System.out.println("Live or Cancel");
            	sql=sql+" and PAYMENT_STATUS=?";
            	
            }*/
            if(cmbStatus.equalsIgnoreCase("Y"))
            {
            	System.out.println("Inside cheque cancelled");
            	sql="select a.voucher_no,"+
					       "to_char(payment_date,'DD/MM/YYYY') as rec_date ,"+
					       "a.remarks,"+
					       "trim(to_char(total_amount,'99999999999999.99')) as total_amount,"+
					       "b.voucher_no as GJV_No,"+
					       "to_char(b.cheque_dd_date,'DD/MM/YYYY') as GJV_Date "+
					"from fas_payment_master a,"+
					     "fas_journal_transaction b "+
					"where "+ 
					      "a.voucher_no=b.cb_ref_no and "+
					      "a.payment_date=b.cheque_dd_date and "+ 
					      "a.accounting_unit_id=? and "+ 
					      "a.accounting_for_office_id=? and "+ 
					      "a.payment_date>=? and "+ 
					      "a.payment_date<=? and "+ 
					      "a.created_by_module=? and "+
					      "a.cheq_cancel_status=? and "+
					      "mode_of_creation='"+txtMode_of_creat+"'";
            }
            else
            {
            		System.out.println("Inside Alive/cancelled");	
            		sql="select voucher_no,"+
					       "to_char(payment_date,'DD/MM/YYYY') as rec_date ,"+
					       "remarks,"+
					       "trim(to_char(total_amount,'99999999999999.99')) as total_amount "+					     
					"from fas_payment_master "+
					"where "+ 
					      "accounting_unit_id=? and "+ 
					      "accounting_for_office_id=? and "+ 
					      "payment_date>=? and "+ 
					      "payment_date<=? and "+ 
					      "created_by_module=? and "+
					      "payment_status=? and " +
					      "mode_of_creation='"+txtMode_of_creat+"'";  
		            	
            }
            System.out.println("SQL::::"+sql);
           try
           {
             int count=0;
            ps=con.prepareStatement(sql);
            ps.setInt(1,cmbAcc_UnitCode);
            ps.setInt(2,cmbOffice_code);       
            ps.setDate(3,txtFrom_date);
            ps.setDate(4,txtTo_date);
            ps.setString(5,txtCreat_By_Module);
            ps.setString(6,cmbStatus);
          //  ps.setString(7,txtMode_of_creat);
             xml=xml+"<flag>success</flag><Ucode>"+cmbAcc_UnitCode+"</Ucode><Offid>"+cmbOffice_code+
             "</Offid><txtCB_Year>"+txtCB_Year+"</txtCB_Year><txtCB_Month>"+txtCB_Month+"</txtCB_Month>";
               rs=ps.executeQuery();
                   while(rs.next())
                   {
                       xml=xml+"<leng>";
                       xml=xml+"<Rec_no>"+rs.getInt("VOUCHER_NO")+"</Rec_no>";
                       xml=xml+"<Rec_Date>"+rs.getString("rec_date")+"</Rec_Date>";
                       xml=xml+"<Remak>"+rs.getString("REMARKS")+"</Remak>";
                       xml=xml+"<Tot_Amt>"+rs.getString("TOTAL_AMOUNT")+"</Tot_Amt>";
                       if(cmbStatus.equalsIgnoreCase("Y"))
                       {
	                       	xml=xml+"<GJV_No>"+rs.getInt("GJV_No") +"</GJV_No>";
	                       	xml=xml+"<GJV_Date>"+rs.getString("GJV_Date") +"</GJV_Date>";
                       }
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
        System.out.println(xml); 
    }
}
