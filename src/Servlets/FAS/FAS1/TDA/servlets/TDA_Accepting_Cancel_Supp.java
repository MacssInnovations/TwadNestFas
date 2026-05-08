package Servlets.FAS.FAS1.TDA.servlets;

import java.io.IOException;
import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
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
 * Servlet implementation class TDA_Accepting_Cancel_Supp
 */
public class TDA_Accepting_Cancel_Supp extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public TDA_Accepting_Cancel_Supp() {
        super();
        // TODO Auto-generated constructor stub
    }

    public void doPost(HttpServletRequest request, 
            HttpServletResponse response) throws ServletException, IOException 
{


String strCommand="";
Connection con=null;        
PreparedStatement ps=null,ps1=null,ps2=null,ps3=null;
String xml="";
Statement st=null;
ResultSet rs=null;
Boolean flag=true;
//-----------------------------------------------------------------------------------------------        

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

//-----------------------------------------------------------------------------------------------        

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
    ConnectionString = strdsn.trim() + "://" + strhostname.trim() + ":" + strportno.trim() + "/" +strsid.trim() ;    // Postgres DB  Connection    Class.forName(strDriver.trim());
    con=DriverManager.getConnection(ConnectionString,strdbusername.trim(),strdbpassword.trim());
    st=con.createStatement();
}
catch(Exception e)
{
   System.out.println("Exception in opening connection :"+e);
   //sendMessage(response,"probably Failed to Establish connection to the database server.. due to "+e,"ok");

}
       
//-----------------------------------------------------------------------------------------------        

try
{        
   strCommand=request.getParameter("Command");
   System.out.println("assign..here command..."+strCommand);
 
}

catch(Exception e) 
{
   System.out.println("Exception in assigning..."+e);
}

//-----------------------------------------------------------------------------------------------        

if(strCommand.equalsIgnoreCase("Cancel")) 
{
   String CONTENT_TYPE = "text/html; charset=windows-1252";
   response.setContentType(CONTENT_TYPE);
  
   //~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
   Calendar c;
   int cmbAcc_UnitCode=0,cmbOffice_code=0,txtCash_Month_hid=0,txtCash_year=0,supNo=0;
   int accepted_JVR_no;
   Date txtCrea_date=null;
   String Journal_type="",accepted_JVR_d,txtRemarks="",accepted_JVR_dt="";
   
                           // changes here
   String update_user=(String)session.getAttribute("UserId");
   long l=System.currentTimeMillis();
   Timestamp ts=new Timestamp(l);                      
   int errcode=0;
   
   //~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
   try{cmbAcc_UnitCode=Integer.parseInt(request.getParameter("cmbAcc_UnitCode"));}
   catch(NumberFormatException e){System.out.println("exception"+e );}
 
   try{cmbOffice_code=Integer.parseInt(request.getParameter("cmbOffice_code"));}
   catch(NumberFormatException e){System.out.println("exception"+e );}
   
   String[] sd=request.getParameter("txtCrea_date").split("/");
   c=new GregorianCalendar(Integer.parseInt(sd[2]),Integer.parseInt(sd[1])-1,Integer.parseInt(sd[0]));
   java.util.Date d=c.getTime();
   txtCrea_date=new Date(d.getTime());

   try{txtCash_year=Integer.parseInt(sd[2]);}
   catch(Exception e){System.out.println("exception"+e );}
   
   try{txtCash_Month_hid=Integer.parseInt(sd[1]);}
   catch(Exception e){System.out.println("exception"+e );}
   
   int accepting_SL_No=0;
             
   try{Journal_type=request.getParameter("Journal_type");}
   catch(Exception e){System.out.println("Journal_type "+e );}
  
   try{accepting_SL_No=Integer.parseInt(request.getParameter("accepted_slno"));}	           
   catch(Exception e){System.out.println("exception"+e );}
  
   try{txtRemarks=request.getParameter("txtRemarks");}
   catch(Exception e){System.out.println("txtRemarks "+e );}
   
   try{supNo=Integer.parseInt(request.getParameter("supNo"));}
   catch(NumberFormatException e){System.out.println("exception"+e );}
   
 //~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~        
   
   try 
   {   
            con.clearWarnings();
            con.setAutoCommit(false);
            ps=con.prepareStatement("update FAS_TDA_TCA_RAISED_MST set PARTICULARS=?,STATUS=?,UPDATED_BY_USERID=?,UPDATED_DATE=? where ACCOUNTING_UNIT_ID=? and ACCOUNTING_FOR_OFFICE_ID=? and CASHBOOK_YEAR=? and CASHBOOK_MONTH=? and VOUCHER_NO=? and TDA_OR_TCA=? and SUPPLEMENT_NO=?");
            ps.setString(1,txtRemarks);                   	                     
            ps.setString(2,"C");
            ps.setString(3,update_user);
            ps.setTimestamp(4,ts);
       //     ps.setInt(5,accepting_SL_No);
            ps.setInt(5,cmbAcc_UnitCode);
            ps.setInt(6,cmbOffice_code);
            ps.setInt(7,txtCash_year);
            ps.setInt(8,txtCash_Month_hid);
            ps.setInt(9,accepting_SL_No);
            ps.setString(10,Journal_type);
            ps.setInt(11,supNo);
            errcode=ps.executeUpdate();
            if(errcode==0)
            {         
          	  		flag=false;                                        
            }
            else
            {
                  	ps=con.prepareStatement("select ACCEPTING_JVR_NO,to_char(ACCEPTING_JVR_DATE,'dd-mm-yyyy')as ACCEPTING_JVR_DATE from FAS_TDA_TCA_RAISED_MST where ACCOUNTING_UNIT_ID=? and ACCOUNTING_FOR_OFFICE_ID=? and CASHBOOK_YEAR=? and CASHBOOK_MONTH=? and VOUCHER_NO=? and SUPPLEMENT_NO=?");
                		ps.setInt(1,cmbAcc_UnitCode);
                		ps.setInt(2,cmbOffice_code);
                		ps.setInt(3,txtCash_year);
                		ps.setInt(4,txtCash_Month_hid);
                		ps.setInt(5,accepting_SL_No);
                		ps.setInt(6,supNo);
                		rs=ps.executeQuery();
                		if(rs.next())
                		{
                			
                				accepted_JVR_no=rs.getInt("ACCEPTING_JVR_NO");
                				accepted_JVR_dt=rs.getString("ACCEPTING_JVR_DATE");
                				if(rs.getInt("ACCEPTING_JVR_NO")!=0)
                				{
                						ps.close();
                						ps=con.prepareStatement("update FAS_JOURNAL_MASTER set JOURNAL_STATUS='C' where ACCOUNTING_UNIT_ID=? and ACCOUNTING_FOR_OFFICE_ID=? and VOUCHER_DATE=TO_DATE(?,'DD-MM-YYYY') and VOUCHER_NO=? and SUPPLEMENT_NO=?");               						
                						ps.setInt(1,cmbAcc_UnitCode);
                                		ps.setInt(2,cmbOffice_code);
                                		ps.setString(3,accepted_JVR_dt);
                                		ps.setInt(4,accepted_JVR_no);
                                		ps.setInt(5,supNo);
                                		int upd=ps.executeUpdate();
                                		if(upd>0)
                                				System.out.println("journal_master flag updated successfully"); 
                                		else
                                				flag=false;
                              }
                				else
                						flag=true;
                				ps3=con.prepareStatement("update FAS_TDA_TCA_RAISED_MST set ACCEPTANCE_STATUS=null,ACCEPTING_SLNO=0,ACCEPTING_DATE=null where TRF_ACCOUNTING_UNIT_ID=?  AND  ACCEPTING_SLNO	=? and SUPPLEMENT_NO=?");
                                              //ps3=con.prepareStatement("update FAS_TDA_TCA_RAISED_MST set ACCEPTANCE_STATUS=null,ACCEPTING_SLNO=0 where TRF_ACCOUNTING_UNIT_ID=?  and TDA_OR_TCA='"+Journal_type+"' AND  ACCEPTING_SLNO	=?");
                				ps3.setInt(1,cmbAcc_UnitCode);System.out.println("cmbAcc_UnitCode"+cmbAcc_UnitCode);
                				ps3.setInt(2,accepting_SL_No);System.out.println("accepting_SL_No>>>>>>>>>"+accepting_SL_No);
                				ps3.setInt(3,supNo);System.out.println("supNo>>>>>>>>>"+supNo);
                				int upd1=ps3.executeUpdate();
                			
                		}                		
                		else
                		{
                				System.out.println("Record not available");	
                				flag=true;
                		}
            }
            if(flag)
            {
                      
                      con.commit();
                      if(Journal_type.equals("TDAA"))
                        		sendMessage(response,"The TDA Accepted Sl.No '"+accepting_SL_No+"' has been Cancelled Successfully ","ok");
                      else
                        		sendMessage(response,"The TCA Accepted Sl.No '"+accepting_SL_No+"' has been Cancelled Successfully ","ok");
            }
            else
            {
                    
                      con.rollback();
                      if(Journal_type.equals("TDAA"))
                    	  		sendMessage(response,"The TDA Cancellation Failed ","ok");
                        else
                  	  			sendMessage(response,"The TCA Cancellation Failed ","ok");     
            }
           
    }
    catch(Exception e) 
    {
            try{con.rollback();}catch(SQLException sqle){System.out.println("exception in rollback "+sqle);}
            e.getStackTrace();
            if(Journal_type.equals("TDAO"))
          	  		sendMessage(response,"The TDA Cancellation Failed ","ok");
            else
        	  	  		sendMessage(response,"The TCA Cancellation Failed ","ok");                    	  	
            System.out.println("Exception occur due to "+e);
        
    }
    finally
    {
       
        try{con.setAutoCommit(true);  }catch(SQLException sqle){}
    }
            
          
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
