package Servlets.FAS.FAS1.CivilBills.servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ResourceBundle;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import Servlets.Security.classes.UserProfile;

public class Closing_Acc_Unit extends HttpServlet {
	private static final long serialVersionUID = 1L;
       

    public Closing_Acc_Unit() {
        super();        
    }
    Connection con=null;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		final String CONTENT_TYPE = "text/xml; charset=UTF-8";
		response.setContentType(CONTENT_TYPE);
        response.setHeader("Cache-Control", "no-cache");
        PrintWriter out = response.getWriter();
        String cmd;
        String xml="";	
        
        PreparedStatement ps,ps1=null,ps11=null;
        ResultSet rs1=null;
        int eid=0;
        int cmbAcc_UnitCode = 0,cmbOffice_code=0;	 
        cmd=request.getParameter("Command");
        
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
        System.out.println("employee id:"+eid);
        String update_user;
        update_user = (String)session.getAttribute("UserId");
        long l=System.currentTimeMillis();
        Timestamp ts=new Timestamp(l);
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
        System.out.println("Redirect Error :"+e);
        }
         String userid=(String)session.getAttribute("UserId");
         

   if(cmd.equalsIgnoreCase("offname")) 
      {
	   cmbAcc_UnitCode = Integer.parseInt(request.getParameter("id"));
       xml="<response><command>offnames</command>"; 
       try 
               {
		           ps=con.prepareStatement("SELECT ACCOUNTING_FOR_OFFICE_ID,ACCOUNTING_UNIT_NAME from" +
		           		" (SELECT ACCOUNTING_FOR_OFFICE_ID,REMARKS,ACCOUNTING_UNIT_ID" +
		           		" FROM FAS_MST_ACCT_UNIT_OFFICES WHERE ACCOUNTING_UNIT_ID="+cmbAcc_UnitCode+")a" +
		           		" left outer join (SELECT ACCOUNTING_UNIT_ID,ACCOUNTING_UNIT_NAME,ACCOUNTING_UNIT_OFFICE_ID" +
		           		" FROM FAS_MST_ACCT_UNITS WHERE ACCOUNTING_UNIT_ID="+cmbAcc_UnitCode+")b" +
		           		" on a.ACCOUNTING_UNIT_ID=b.ACCOUNTING_UNIT_ID" +
		        		" where a.ACCOUNTING_FOR_OFFICE_ID=b.ACCOUNTING_UNIT_OFFICE_ID");
		           		ResultSet rs=ps.executeQuery();
		           if(rs.next())
		           {
			           PreparedStatement ps2=con.prepareStatement("select OFFICE_NAME from COM_MST_OFFICES where OFFICE_ID=?");
			           ps2.setInt(1,rs.getInt("ACCOUNTING_FOR_OFFICE_ID"));
			           ResultSet rs2=ps2.executeQuery();
			           if(rs2.next())
			           {
			        	   	   xml=xml+"<flag>success</flag>";
	                    	   xml=xml+"<offname>"+rs2.getString("OFFICE_NAME")+"</offname>";
	                           xml=xml+"<offid>"+rs.getInt("ACCOUNTING_FOR_OFFICE_ID")+"</offid>";
	                    	   xml=xml+"<unitname>"+rs.getString("ACCOUNTING_UNIT_NAME")+"</unitname>";
	                           xml=xml+"<unitid>"+cmbAcc_UnitCode+"</unitid>";
	                   }
		           }
		           
               }//try close
         catch(Exception e) 
               {
                       System.out.println("Exception in minor ===> "+e);   
                       xml=xml+"<flag>failure</flag>";  
               }
           xml=xml+"</response>";
      }
   else if(cmd.equalsIgnoreCase("offname1")) 
   {
	   cmbAcc_UnitCode = Integer.parseInt(request.getParameter("id"));
    xml="<response><command>offnames1</command>"; 
    try 
            {
		          System.out.println("To load the transfering units**************");
		           ps1=con.prepareStatement("SELECT ACCOUNTING_FOR_OFFICE_ID,ACCOUNTING_UNIT_NAME,a.ACCOUNTING_UNIT_ID as ACCOUNTING_UNIT_ID from" +
		           		" (SELECT ACCOUNTING_FOR_OFFICE_ID,REMARKS,ACCOUNTING_UNIT_ID" +
		           		" FROM FAS_MST_ACCT_UNIT_OFFICES WHERE ACCOUNTING_UNIT_ID not in ("+cmbAcc_UnitCode+") ) a" +
		           		" right outer join (SELECT ACCOUNTING_UNIT_ID,ACCOUNTING_UNIT_NAME,ACCOUNTING_UNIT_OFFICE_ID" +
		           		" FROM FAS_MST_ACCT_UNITS WHERE ACCOUNTING_UNIT_ID not in ("+cmbAcc_UnitCode+") ) b" +
		           		" on a.ACCOUNTING_UNIT_ID=b.ACCOUNTING_UNIT_ID order by ACCOUNTING_UNIT_NAME") ;
//		           ps1.setInt(1,cmbAcc_UnitCode);
//		           ps1.setInt(2,cmbAcc_UnitCode);
		           
		           rs1=ps1.executeQuery();
		           System.out.println("sql query***"+rs1);
		           while(rs1.next())
		           {
			           PreparedStatement ps3=con.prepareStatement("select OFFICE_NAME from COM_MST_OFFICES where office_level_id not in('RD','CL') and OFFICE_ID=?");
			           ps3.setInt(1,rs1.getInt("ACCOUNTING_FOR_OFFICE_ID"));
			           ResultSet rs3=ps3.executeQuery();
			           while(rs3.next())
			           {
			        	   	   xml=xml+"<flag>success</flag>";
	                    	   xml=xml+"<offname1>"+rs3.getString("OFFICE_NAME")+"</offname1>";
	                           xml=xml+"<offid1>"+rs1.getInt("ACCOUNTING_FOR_OFFICE_ID")+"</offid1>";
	                    	   xml=xml+"<unitname1>"+rs1.getString("ACCOUNTING_UNIT_NAME")+"</unitname1>";
	                           xml=xml+"<unitid1>"+rs1.getInt("ACCOUNTING_UNIT_ID")+"</unitid1>";
//	                           System.out.println("tesing by NIC");
	                   }
		           }
		           
            }//try close
      catch(Exception e) 
            {
                    System.out.println("Exception in minor ********* ===> "+e);   
                    xml=xml+"<flag>failure</flag>";  
            }
        xml=xml+"</response>";
   }

   else if(cmd.equalsIgnoreCase("loadDOpen")) 
   {
	   cmbAcc_UnitCode = Integer.parseInt(request.getParameter("id"));
    xml="<response><command>loadDOpennn</command>"; 
    try 
            {
		           ps1=con.prepareStatement("select to_char(DATE_OF_OPENING,'DD/MM/YYYY') as DATE_OF_OPENING from fas_mst_acct_units where accounting_unit_id=?");
		           ps1.setInt(1,cmbAcc_UnitCode);
//		           ps1.setInt(2,cmbAcc_UnitCode);
		           
		           rs1=ps1.executeQuery();
		           while(rs1.next())
		           {
			           	   	   xml=xml+"<flag>success</flag>";
	                    	   if(rs1.getString("DATE_OF_OPENING")==null)
	                    	   {
			           	   	   		xml=xml+"<DOOpening>--</DOOpening>";
	                    	   }
	                    	   else
	                    	   {
	                    		   xml=xml+"<DOOpening>"+rs1.getString("DATE_OF_OPENING")+"</DOOpening>";
	                    	   }
	                                     
		           }
		           
            }//try close
      catch(Exception e) 
            {
                    System.out.println("Exception in minor ********* ===> "+e);   
                    xml=xml+"<flag>failure</flag>";  
            }
        xml=xml+"</response>";
   }

   if(cmd.equalsIgnoreCase("updat")) 
   {
	   cmbAcc_UnitCode=Integer.parseInt(request.getParameter("cmbAcc_UnitCode"));
       cmbOffice_code=Integer.parseInt(request.getParameter("cmbOffice_code"));
       int unit=Integer.parseInt(request.getParameter("unit"));
       String open = request.getParameter("open");
       String close=request.getParameter("close");
       System.out.println("value of close$$$$"+close+"updated date$$"+ts+"unittrf"+unit);
       xml="<response><command>Updated</command>"; 
            try {
               String sql="update FAS_MST_ACCT_UNITS set STATUS=?,DATE_OF_CLOSURE=to_date(?,'dd/mm/yyyy'),ACCT_TRF_UNIT_ID =?,UPDATED_BY_USER_ID=?,UPDATED_DATE=? where ACCOUNTING_UNIT_ID=? and ACCOUNTING_UNIT_OFFICE_ID=?";
            	System.out.println("Sql--->"+sql);
            	ps =con.prepareStatement(sql);
               ps.setString(1,"C");
               ps.setString(2,close);
               ps.setInt(3,unit);
               ps.setString(4,update_user);
               ps.setTimestamp(5,ts);
               ps.setInt(6,cmbAcc_UnitCode);
               ps.setInt(7,cmbOffice_code);       
               int num=ps.executeUpdate();
               System.out.println("num==>"+num);
               if(num>0)
               {
            	   String sql1="update FAS_MST_ACCT_UNIT_OFFICES set DATE_OF_CLOSURE=to_date(?,'dd/mm/yyyy'),CLOSED='Y' where ACCOUNTING_UNIT_ID=? and ACCOUNTING_FOR_OFFICE_ID=?";
                   ps11=con.prepareStatement(sql1);
                   ps11.setString(1,close);
                   ps11.setInt(2,cmbAcc_UnitCode);
                   ps11.setInt(3,cmbOffice_code);    
                   ps11.executeUpdate();
                   xml = xml + "<flag>success</flag>";
                   
               }
               System.out.println("here FAS_MST_ACCT_UNITS and FAS_MST_ACCT_UNIT_OFFICES ok ");

            
           }
           catch (Exception e) {
               System.out.println("catch..HERE.in load head code." + e);
               xml = xml + "<flag>failure</flag>";	
           }
          
           
           xml = xml + "</response>";       
   }
      System.out.println("xml ::"+xml);
      out.println(xml);
      out.close();		
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

	}

}
