package Servlets.FAS.FAS1.TPA.servlets;

import java.io.IOException;
import java.io.PrintWriter;
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
 * Servlet implementation class TPA_Authorization_System
 */
public class TPA_Authorization_System extends HttpServlet {
	private static final long serialVersionUID = 1L;
	 private String CONTENT_TYPE = "text/xml; charset=windows-1252";   
    /**
     * @see HttpServlet#HttpServlet()
     */
    public TPA_Authorization_System() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		  /**
	       * Session Checking 
	      */
		 PrintWriter out = response.getWriter();
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
                ConnectionString = strdsn.trim() + "://" + strhostname.trim() + ":" + strportno.trim() + "/" +strsid.trim() ;    // Postgres DB  Connection                Class.forName(strDriver.trim());
                con=DriverManager.getConnection(ConnectionString,strdbusername.trim(),strdbpassword.trim());
        }
        catch(Exception e)
        {
            	  System.out.println("Exception in opening connection :"+e);
        }
        response.setContentType(CONTENT_TYPE);
        int cmbAcc_UnitCode=0,cmbOffice_code=0,acceptUnitId=0,authorisedUnit=0,cashYear=0,cashMonth=0;
        String particulars=null;
       
        String update_user=(String)session.getAttribute("UserId");
        long l=System.currentTimeMillis();
        Timestamp ts=new Timestamp(l);                      
        
        try{cmbAcc_UnitCode=Integer.parseInt(request.getParameter("cmbAcc_UnitCode"));}
        catch(NumberFormatException e){System.out.println("exception"+e );}
        System.out.println("cmbAcc_UnitCode "+cmbAcc_UnitCode);
        
        try{cmbOffice_code=Integer.parseInt(request.getParameter("cmbOffice_code"));}
        catch(NumberFormatException e){System.out.println("exception"+e );}
        System.out.println("cmbOffice_code "+cmbOffice_code);
        
        
        String transferType=request.getParameter("Org_CR_DR");
        
        try{authorisedUnit=Integer.parseInt(request.getParameter("TransferedID"));}
        catch(NumberFormatException e){System.out.println("exception"+e );}
        System.out.println("authorisedUnit "+authorisedUnit);
        
        
        try{acceptUnitId=Integer.parseInt(request.getParameter("acceptUnitId"));}
        catch(NumberFormatException e){System.out.println("exception"+e );}
        System.out.println("acceptUnitId "+acceptUnitId);
        
        
        String effectiveDate=request.getParameter("effectivedate");
        
        try{String[] sd1=effectiveDate.split("/");
       // c=new GregorianCalendar(Integer.parseInt(sd1[2]),Integer.parseInt(sd1[1])-1,Integer.parseInt(sd1[0]));
        cashYear=Integer.parseInt(sd1[2]);
        cashMonth=Integer.parseInt(sd1[1]);
        }catch(Exception e){System.out.println("Err in originated_date "+e.getMessage());}
        
        
        String reason=request.getParameter("unitauthoriz");
        
        particulars=request.getParameter("GenParticulars");
       
        String command=request.getParameter("command");
      if(command.equalsIgnoreCase("Add"))
      {
     String xml="<response>";
     xml=xml+"<command>Add</command>";
        
        try{
        	
        	int loopAll=0;
        	if(transferType.equalsIgnoreCase("ALL"))
        	{
        		loopAll=1;
        		transferType="CR";
        	}
        	
        	for(int i=0;i<=loopAll;i++){
       ps=con.prepareStatement("insert into FAS_TPA_AHUTHORIZATION_SYSTEM(ACCOUNTING_UNIT_ID,ACCOUNTING_FOR_OFFICE_ID,TPA_TYPE,AUTHORIZED_ACCOUNTING_UNIT_ID,REASON_FOR_TRANSFER,EFFECTIVE_DATE,PARTICULARS,UPDATED_BY_USERID,UPDATED_DATE,CASHBOOK_YEAR,CASHBOOK_MONTH,ACCEPT_ACCOUNTING_UNIT_ID) values(?,?,?,?,?,to_date(?,'dd/MM/yyyy'),?,?,?,?,?,?) ") ;
        ps.setInt(1, cmbAcc_UnitCode);
        ps.setInt(2, cmbOffice_code);
        ps.setString(3, transferType);
        ps.setInt(4, authorisedUnit);
        ps.setString(5, reason);
        ps.setString(6, effectiveDate);
        ps.setString(7, particulars);
        ps.setString(8, update_user);
        ps.setTimestamp(9, ts);
        ps.setInt(10, cashYear);
        ps.setInt(11, cashMonth);
        ps.setInt(12, acceptUnitId);
        ps.execute();
        
        transferType="DR";
        
        
        	}
        
        xml+="<flag>success</flag>";
       
        
        }catch(Exception e)
        {
        	System.out.print("Exce"+e);
        	 xml+="<flag>error</flag>";
        }
        
        
        xml=xml+"</response>";
        System.out.println(xml);
        out.println(xml);
        out.close(); 
      }
      else if(command.equalsIgnoreCase("update"))
      {
     String xml="<response>";
     xml=xml+"<command>update</command>";
        
        try{
        	int preAuthorizedUnit=0,preCashBookYear=0,preCahBookMonth=0;
        	
        	
        	 try{preAuthorizedUnit=Integer.parseInt(request.getParameter("pre_authorizedunit"));}
             catch(NumberFormatException e){System.out.println("exception"+e );}
             System.out.println("preAuthorizedUnit "+preAuthorizedUnit);
             
             
             
             
             String preTransferType=request.getParameter("pre_tpa_type");
             String tpaorgiType="";
             
             if(preTransferType.equalsIgnoreCase("CR"))
            	 tpaorgiType="TPAOC";
             else
            	 tpaorgiType="TPAOD"; 
             
             String preEffectiveDate=request.getParameter("pre_effectivedate");
             
             try{String[] sd1=preEffectiveDate.split("/");
            // c=new GregorianCalendar(Integer.parseInt(sd1[2]),Integer.parseInt(sd1[1])-1,Integer.parseInt(sd1[0]));
             preCashBookYear=Integer.parseInt(sd1[2]);
             preCahBookMonth=Integer.parseInt(sd1[1]);
             }catch(Exception e){System.out.println("Err in originated_date "+e.getMessage());}
                          
             String preReason=request.getParameter("pre_reason");
             
             
             ps=con.prepareStatement("select * from FAS_TPA_MASTER where ACCOUNTING_UNIT_ID=? and CASHBOOK_YEAR=? and CASHBOOK_MONTH=? and TPA_TYPE=? and REASON_FOR_TRANSFER=?") ;
           /*  System.out.print("preAuthorizedUnit"+preAuthorizedUnit);
             System.out.print("preCashBookYear"+preCashBookYear);
             System.out.print("preCahBookMonth"+preCahBookMonth);
             System.out.print("tpaorgiType"+tpaorgiType);
             System.out.print("preReason"+preReason);*/
             ps.setInt(1, preAuthorizedUnit);
             ps.setInt(2, preCashBookYear);
             ps.setInt(3, preCahBookMonth);
             ps.setString(4, tpaorgiType);
             ps.setString(5, preReason);
        	 ps.execute();
             
             rs2=ps.executeQuery();
             
             if(!rs2.next())
             {
             
            
             ps=con.prepareStatement("delete from FAS_TPA_AHUTHORIZATION_SYSTEM where TPA_TYPE=? and AUTHORIZED_ACCOUNTING_UNIT_ID=? and REASON_FOR_TRANSFER=? and CASHBOOK_YEAR=? and CASHBOOK_MONTH=?") ;
             ps.setString(1, preTransferType);
             ps.setInt(2, preAuthorizedUnit);
             ps.setString(3, preReason);
             ps.setInt(4, preCashBookYear);
             ps.setInt(5, preCahBookMonth);
        	 ps.execute();
        	
        	ps.close();
        	
        	int loopAll=0;
        	if(transferType.equalsIgnoreCase("ALL"))
        	{
        		loopAll=1;
        		transferType="CR";
        	}
        	
        	for(int i=0;i<=loopAll;i++){
        	
        	 ps=con.prepareStatement("insert into FAS_TPA_AHUTHORIZATION_SYSTEM(ACCOUNTING_UNIT_ID,ACCOUNTING_FOR_OFFICE_ID,TPA_TYPE,AUTHORIZED_ACCOUNTING_UNIT_ID,REASON_FOR_TRANSFER,EFFECTIVE_DATE,PARTICULARS,UPDATED_BY_USERID,UPDATED_DATE,CASHBOOK_YEAR,CASHBOOK_MONTH,ACCEPT_ACCOUNTING_UNIT_ID) values(?,?,?,?,?,to_date(?,'dd/MM/yyyy'),?,?,?,?,?,?) ") ;
             ps.setInt(1, cmbAcc_UnitCode);
             ps.setInt(2, cmbOffice_code);
             ps.setString(3, transferType);
             ps.setInt(4, authorisedUnit);
             ps.setString(5, reason);
             ps.setString(6, effectiveDate);
             ps.setString(7, particulars);
             ps.setString(8, update_user);
             ps.setTimestamp(9, ts);
             ps.setInt(10, cashYear);
             ps.setInt(11, cashMonth);
             ps.setInt(12, acceptUnitId);
             ps.execute();
             transferType="DR";
        	}
             xml+="<flag>success</flag>";
             }else{
            	 xml+="<flag>Raised</flag>"; 
             }
       
        
        }catch(Exception e)
        {
        	System.out.print("Exce"+e);
        	 xml+="<flag>error</flag>";
        }
        
        
        xml=xml+"</response>";
        System.out.println(xml);
        out.println(xml);
        out.close(); 
      }
      
      else if(command.equalsIgnoreCase("delete"))
      {
     String xml="<response>";
     xml=xml+"<command>delete</command>";
        
        try{
        	int preAuthorizedUnit=0,preCashBookYear=0,preCahBookMonth=0;
        	 try{preAuthorizedUnit=Integer.parseInt(request.getParameter("pre_authorizedunit"));}
             catch(NumberFormatException e){System.out.println("exception"+e );}
             System.out.println("preAuthorizedUnit "+preAuthorizedUnit);
            
             String preTransferType=request.getParameter("pre_tpa_type");
             
             String tpaorgiType="";
             
             if(preTransferType.equalsIgnoreCase("CR"))
            	 tpaorgiType="TPAOC";
             else
            	 tpaorgiType="TPAOD"; 
             
             String preEffectiveDate=request.getParameter("pre_effectivedate");
             
             try{String[] sd1=preEffectiveDate.split("/");
            // c=new GregorianCalendar(Integer.parseInt(sd1[2]),Integer.parseInt(sd1[1])-1,Integer.parseInt(sd1[0]));
             preCashBookYear=Integer.parseInt(sd1[2]);
             preCahBookMonth=Integer.parseInt(sd1[1]);
             }catch(Exception e){System.out.println("Err in originated_date "+e.getMessage());}
                          
             String preReason=request.getParameter("pre_reason");
             
             ps=con.prepareStatement("select * from FAS_TPA_MASTER where ACCOUNTING_UNIT_ID=? and CASHBOOK_YEAR=? and CASHBOOK_MONTH=? and TPA_TYPE=? and REASON_FOR_TRANSFER=?") ;
             /*System.out.print("preAuthorizedUnit"+preAuthorizedUnit);
             System.out.print("preCashBookYear"+preCashBookYear);
             System.out.print("preCahBookMonth"+preCahBookMonth);
             System.out.print("tpaorgiType"+tpaorgiType);
             System.out.print("preReason"+preReason);*/
             
             ps.setInt(1, preAuthorizedUnit);
             ps.setInt(2, preCashBookYear);
             ps.setInt(3, preCahBookMonth);
             ps.setString(4, tpaorgiType);
             ps.setString(5, preReason);
        	 ps.execute();
             
             rs2=ps.executeQuery();
             
             if(!rs2.next())
             {
             ps=con.prepareStatement("delete from FAS_TPA_AHUTHORIZATION_SYSTEM where TPA_TYPE=? and AUTHORIZED_ACCOUNTING_UNIT_ID=? and REASON_FOR_TRANSFER=? and CASHBOOK_YEAR=? and CASHBOOK_MONTH=?") ;
             ps.setString(1, preTransferType);
             ps.setInt(2, preAuthorizedUnit);
             ps.setString(3, preReason);
             ps.setInt(4, preCashBookYear);
             ps.setInt(5, preCahBookMonth);
        	 ps.execute();
        	 ps.close();
        	
        
             xml+="<flag>success</flag>";
             }else{
            	 xml+="<flag>Raised</flag>"; 
             }
       
        
        }catch(Exception e)
        {
        	System.out.print("Exce"+e);
        	 xml+="<flag>error</flag>";
        }
        
        
        xml=xml+"</response>";
        System.out.println(xml);
        out.println(xml);
        out.close(); 
      }
      
      
        
	}

}
