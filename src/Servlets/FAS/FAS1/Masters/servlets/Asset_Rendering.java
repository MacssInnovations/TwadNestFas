package Servlets.FAS.FAS1.Masters.servlets;

import java.io.IOException;
import java.io.PrintWriter;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;

import java.util.ResourceBundle;

import javax.servlet.*;
import javax.servlet.http.*;

public class Asset_Rendering extends HttpServlet {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static final String CONTENT_TYPE = "text/xml; charset=windows-1252";
    
    public void init(ServletConfig config) throws ServletException 
    {
        super.init(config);
    }

    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
    {


    	System.out.println("\nAsset_Rendering.java - POST\n");
    	
    	/* Variables Declaration */
    	Connection connection=null;
        ResultSet result=null;
        ResultSet rs1=null;
        PreparedStatement ps=null;
        PreparedStatement ps1=null;
         

    	
    	/* Database Connection */
    	try
        {
               ResourceBundle rb=ResourceBundle.getBundle("Servlets.Security.servlets.Config");
               String ConnectionString="";
              
               String strDriver=rb.getString("Config.DATA_BASE_DRIVER");
               String strdsn=rb.getString("Config.DSN");
               String strhostname=rb.getString("Config.HOST_NAME");
               String strportno=rb.getString("Config.PORT_NUMBER");
               String strsid=rb.getString("Config.SID");
               String strdbusername=rb.getString("Config.USER_NAME");
               String strdbpassword=rb.getString("Config.PASSWORD");
                 
               ConnectionString = strdsn.trim() + "://" + strhostname.trim() + ":" + strportno.trim() + "/" +strsid.trim() ;    // Postgres DB  Connection

                Class.forName(strDriver.trim());
                connection=DriverManager.getConnection(ConnectionString,strdbusername.trim(),strdbpassword.trim());
                connection.clearWarnings();              
        }
    	catch(Exception e)
    	{
    		System.out.println("Exception in openeing connection:"+e);
    	}
    	
        /* Set Content Type */
    	response.setContentType(CONTENT_TYPE);      
    	PrintWriter out = response.getWriter();
        HttpSession session=request.getSession(false);
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

    	
    	
    	/* Variables Declaration */
    	String Command = null;
    	int accUnitId = 0;
    	int accOfficeId = 0;
    	String dtFrm = null;
    	String active = null;
    	String dtTo = null;
    	int accUnitRenderedFor = 0;
    	
    	String xml = null;

    	String userid=(String)session.getAttribute("UserId");
        System.out.println("User Id is:"+userid);
        response.setContentType("text/xml");
        response.setHeader("Cache-Control","no-cache");
    	
    	
    	/**
    	 *   Get Parameters from Form 
    	 */

    	/* Get Command Parameter */
    	try
    	{
    	  Command = request.getParameter("Command");	
    	}
    	catch(Exception e)
    	{
    		System.out.println("Error fetching Command Parameter");
    	}
    	
    	/* Get UnitId Parameter */
    	try
    	{
    		accUnitId = Integer.parseInt(request.getParameter("accUnitId"));	
    	}
    	catch(Exception e)
    	{
    		System.out.println("Error fetching accUnitId Parameter");
    	}
    	
    	/* Get OfficeId Parameter */
    	try
    	{
    		accOfficeId = Integer.parseInt(request.getParameter("accOfficeId"));	
    	}
    	catch(Exception e)
    	{
    		System.out.println("Error fetching accOfficeId Parameter");
    	}
    	
        	
      
    	
    	
    	/**
    	 * Check Operation Details 
    	 */
    	if (Command.equalsIgnoreCase("ADD"))
    	{      xml="<response><command>Add</command>";
    	
    		/* Get Date Effective from */
        	try
        	{
        		dtFrm = request.getParameter("dtFrm");	
        	}
        	catch(Exception e)
        	{
        		System.out.println("Error fetching dtFrm Parameter");
        	}
    		
        	
    		/* Get Active Status */
        	try
        	{
        		active = request.getParameter("active");	
        	}
        	catch(Exception e)
        	{
        		System.out.println("Error fetching active Parameter");
        	}
   		
    		
        	/* Get Date Effect Up to */
        	try
        	{
        		dtTo = request.getParameter("dtTo");	
        	}
        	catch(Exception e)
        	{
        		if(active.equalsIgnoreCase("N"))
        		{
        			System.out.println("Error fetching dtTo Parameter. Either fill the date or toggle Active status");
        		}
        	}
        	
        	
    		
        	/* Get Date Effect Up to 
        	try
        	{
        		accUnitRenderedFor = Integer.parseInt(request.getParameter("accUnitRenderedFor"));	
        	}
        	catch(Exception e)
        	{
        		System.out.println("Error fetching dtTo Parameter. Either fill the date or toggle Active status");
        	}
        	
        	 	*/
        	/* Convert Strings to Date */
        	
        	java.sql.Date dateFrom=null;
			java.sql.Date dateTo=null;
        	
    		if(!dtFrm.equalsIgnoreCase(""))
            {
                SimpleDateFormat dateFormat1 = new SimpleDateFormat("dd/MM/yyyy");
                try
                {
                    java.util.Date d1=dateFormat1.parse(dtFrm);
                    dateFormat1.applyPattern("yyyy-MM-dd");
                    dtFrm=dateFormat1.format(d1);
                
                }catch(Exception e)
                {
                     e.printStackTrace();
                }
                dateFrom=Date.valueOf(dtFrm);
                System.out.println("From date : "+dateFrom);
            }

			if(!dtTo.equalsIgnoreCase(""))
            {
                SimpleDateFormat dateFormat2 = new SimpleDateFormat("dd/MM/yyyy");
                try
                {
                    java.util.Date d2=dateFormat2.parse(dtTo);
                    dateFormat2.applyPattern("yyyy-MM-dd");
                    dtTo=dateFormat2.format(d2);
                
                }catch(Exception e)
                {
                     e.printStackTrace();
                }
                dateTo=Date.valueOf(dtTo);
                System.out.println("To date : "+dateTo);
            }
    	    try {
    	    	System.out.println("accUnitId : "+accUnitId);
    	    	System.out.println("accOfficeId : "+accOfficeId);
    	    	String sql="select ACCT_RENDERING_UNIT_ID,RENDERING_UNIT_OFFICE_ID,DATE_EFFECT_FROM,ACTIVE, " +
                "DATE_EFFECT_UPTO from FAS_ASSET_NUM_AC_RENDER_UNITS where ACCT_RENDERING_UNIT_ID=? AND RENDERING_UNIT_OFFICE_ID=?";
    	    	System.out.println("sql "+sql);
    	                ps1=connection.prepareStatement(sql);
    	                       ps1.setInt(1,accUnitId);
    	                       ps1.setInt(2,accOfficeId);
    	                     
    	                rs1=ps1.executeQuery();
    	                if(!rs1.next())
    	                {
    	                    System.out.println("this i sinside the if loop");
    	                
			
			/* Insert Query Execution */
           
            try {
                ps=connection.prepareStatement("INSERT INTO FAS_ASSET_NUM_AC_RENDER_UNITS(ACCT_RENDERING_UNIT_ID,RENDERING_UNIT_OFFICE_ID,DATE_EFFECT_FROM,ACTIVE,DATE_EFFECT_UPTO,UPDATED_BY_USERID,UPDATED_DATE,STATUS) VALUES(?,?,?,?,?,?,SYSTIMESTAMP,'L')");
                ps.setInt(1,accUnitId);
                ps.setInt(2,accOfficeId);
                ps.setDate(3,dateFrom);
                ps.setString(4,active);
                ps.setDate(5,dateTo);
                //ps.setInt(6,accUnitRenderedFor);
                ps.setString(6,userid);
                ps.executeUpdate();
                xml=xml+"<flag>success</flag>";
            }
            catch(Exception e) {
            	e.printStackTrace();
                xml=xml+"<flag>failure</flag>";
            }
    	    }
    	    else
    	    {
    	    System.out.println("This is Else Loop");
    	    xml=xml+"<flag>AlreadyExist</flag>";
    	    }
    	    
    	 //   xml=xml+"</response>";
    	    }catch(Exception e){
    	    System.out.println("Exception in select:"+e);
    	    }
        	
    	}
    	if (Command.equalsIgnoreCase("UPDATE"))
    	{
    	    xml="<response><command>Update</command>";
    		/* Get Date Effective from */
        	try
        	{
        		dtFrm = request.getParameter("dtFrm");	
        	}
        	catch(Exception e)
        	{
        		System.out.println("Error fetching dtFrm Parameter");
        	}
    		
        	
    		/* Get Active Status */
        	try
        	{
        		active = request.getParameter("active");	
        	}
        	catch(Exception e)
        	{
        		System.out.println("Error fetching active Parameter");
        	}
   		
    		
        	/* Get Date Effect Up to */
        	try
        	{
        		dtTo = request.getParameter("dtTo");	
        	}
        	catch(Exception e)
        	{
        		if(active.equalsIgnoreCase("N"))
        		{
        			System.out.println("Error fetching dtTo Parameter. Either fill the date or toggle Active status");
        		}
        	}
        	
        	

        	/* Convert Strings to Date */
        	
        	java.sql.Date dateFrom=null;
			java.sql.Date dateTo=null;
        	
    		if(!dtFrm.equalsIgnoreCase(""))
            {
                SimpleDateFormat dateFormat1 = new SimpleDateFormat("dd/MM/yyyy");
                try
                {
                    java.util.Date d1=dateFormat1.parse(dtFrm);
                    dateFormat1.applyPattern("yyyy-MM-dd");
                    dtFrm=dateFormat1.format(d1);
                
                }catch(Exception e)
                {
                     e.printStackTrace();
                }
                dateFrom=Date.valueOf(dtFrm);
                System.out.println("From date : "+dateFrom);
            }

			if(!dtTo.equalsIgnoreCase(""))
            {
                SimpleDateFormat dateFormat2 = new SimpleDateFormat("dd/MM/yyyy");
                try
                {
                    java.util.Date d2=dateFormat2.parse(dtTo);
                    dateFormat2.applyPattern("yyyy-MM-dd");
                    dtTo=dateFormat2.format(d2);
                
                }catch(Exception e)
                {
                     e.printStackTrace();
                }
                dateTo=Date.valueOf(dtTo);
                System.out.println("To date : "+dateTo);
            }

			
			/* Update Query Execution */
            
            try {
                ps=connection.prepareStatement("UPDATE FAS_ASSET_NUM_AC_RENDER_UNITS SET DATE_EFFECT_FROM = ?, ACTIVE = ?, DATE_EFFECT_UPTO = ?, UPDATED_BY_USERID = ?, UPDATED_DATE = SYSTIMESTAMP WHERE ACCT_RENDERING_UNIT_ID = ? AND RENDERING_UNIT_OFFICE_ID = ?");
                ps.setDate(1,dateFrom);
                ps.setString(2,active);
                ps.setDate(3,dateTo);
                ps.setString(4,userid);
                ps.setInt(5,accUnitId);
                ps.setInt(6,accOfficeId);
                ps.executeUpdate();
                xml=xml+"<flag>success</flag>";
            }
            catch(Exception e) {
            
                System.out.println("Exception in updating...."+e);
                xml=xml+"<flag>failure</flag>";
            }
       	
    	}
    	if (Command.equalsIgnoreCase("DELETE"))
    	{

    		System.out.println("accUnitId : "+accUnitId);
    		System.out.println("accOfficeId : "+accOfficeId);
    		
			/* Delete Query Execution */
            xml="<response><command>Delete</command>";
            try {            	
                //ps=connection.prepareStatement("DELETE FROM FAS_ASSET_NUM_AC_RENDER_UNITS WHERE ACCT_RENDERING_UNIT_ID = ? AND RENDERING_UNIT_OFFICE_ID = ?");
            	ps=connection.prepareStatement("UPDATE FAS_ASSET_NUM_AC_RENDER_UNITS SET STATUS='C' WHERE ACCT_RENDERING_UNIT_ID = ? AND RENDERING_UNIT_OFFICE_ID = ?");
                ps.setInt(1,accUnitId);
                ps.setInt(2,accOfficeId);
                ps.executeUpdate();
                xml=xml+"<flag>success</flag>";
            }
            catch(Exception e) {
            
                System.out.println("Exception in deleting...."+e);
                xml=xml+"<flag>failure</flag>";
            }
    	
    	}
    	
    	if (Command.equalsIgnoreCase("LoadRenFor"))
    	{

			/* 'Load - A/c Unit Rendered For' Query Execution */
            xml="<response><command>Delete</command>";
            try {
            	
                ps=connection.prepareStatement("SELECT ACCOUNTING_UNIT_ID, " +
												"  ACCOUNTING_UNIT_NAME " +
												"FROM FAS_MST_ACCT_UNITS " +
												"ORDER BY ACCOUNTING_UNIT_NAME");
                result = ps.executeQuery();
                xml=xml+"<flag>success</flag>";
                while(result.next())
                {
                	xml += "<ACCOUNTING_UNIT_ID>" + result.getInt("ACCOUNTING_UNIT_ID") + "</ACCOUNTING_UNIT_ID>";
                	xml += "<ACCOUNTING_UNIT_NAME>" + result.getString("ACCOUNTING_UNIT_NAME") + "</ACCOUNTING_UNIT_NAME>";
                }
            }
            catch(Exception e) {
            
                System.out.println("Exception in deleting...."+e);
                xml=xml+"<flag>failure</flag>";
            }
    	
    	}
    	if (Command.equalsIgnoreCase("LoadCicles"))
    	{

			/* 'Load - Circles for Account Rendering units*/
            xml="<response><command>LoadCicles</command>";
            try {
            	
                ps=connection.prepareStatement("select office_id,OFFICE_NAME from com_mst_offices_view where  OFFICE_LEVEL_ID in ('CL','HO')  and  OFFICE_STATUS_ID not in  ('RD','NC','CL') order by office_id");
                result = ps.executeQuery();
                xml=xml+"<flag>success</flag>";
                while(result.next())
                {
                	int off_id=result.getInt("office_id");
                	//System.out.println("Circleeeeeeeee"+off_id);
                	ps1=connection.prepareStatement("SELECT accounting_unit_id,accounting_unit_name,accounting_unit_office_id from fas_mst_acct_units "+
                									" where accounting_unit_office_id =? and (OFFICE_WING_SINO =1 OR OFFICE_WING_SINO IS NULL) order by accounting_unit_id ");
                	ps1.setInt(1,off_id);
                	rs1=ps1.executeQuery();
                	while(rs1.next())
                	{
                		xml += "<rec><ACCOUNTING_UNIT_ID>" + rs1.getInt("accounting_unit_id") + "</ACCOUNTING_UNIT_ID>";
                    	xml += "<ACCOUNTING_UNIT_NAME>" + rs1.getString("accounting_unit_name") + "</ACCOUNTING_UNIT_NAME></rec>";
                	}
                }
            }
            catch(Exception e) {
            
                System.out.println("Exception in loading rendering unitssssssssssss...."+e);
                xml=xml+"<flag>failure</flag>";
            }
    	
    	}
    	
    	xml = xml + "</response>";
    	System.out.println(xml);
    	out.println(xml);
    	
    }
    
    
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
    {
    	
    	System.out.println("\nAsset_Rendering.java - GET\n");
    	
        Connection connection=null;
        ResultSet results=null;
        PreparedStatement ps=null;

    
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
                  connection=DriverManager.getConnection(ConnectionString,strdbusername.trim(),strdbpassword.trim());
                  connection.clearWarnings();
           }
          catch(Exception e)
          {
             System.out.println("Exception in openeing connection:"+e);
          }
         
        response.setContentType(CONTENT_TYPE);      
        PrintWriter out = response.getWriter();
        String accountname=request.getParameter("txtaccountname");

        System.out.println("accountname is:"+accountname);
        String command=request.getParameter("command");
        System.out.println("command is:"+command);
        
         if(command.equalsIgnoreCase("List")) 
         {
                    System.out.println("List");    
                    String accountunitid=request.getParameter("AccountUnitId");
                    int aunitid=0;
                    if(accountunitid!=null) 
                    {
                        aunitid=Integer.parseInt(accountunitid);
                        System.out.println("Unit is:"+aunitid);
                    }
                    try
                    {
                    String sql5="select a.accounting_unit_id,a.accounting_unit_name,a.accounting_unit_office_id,b.accounting_for_office_id,c.office_name from fas_mst_acct_units a,fas_mst_acct_unit_offices b,com_mst_offices c where a.accounting_unit_id=b.accounting_unit_id and b.accounting_for_office_id=c.office_id and a.accounting_unit_id=?";
                    ps=connection.prepareStatement(sql5);
                    ps.setInt(1,aunitid);
                    results=ps.executeQuery();
                        String xml="";
                        xml="<response><flag>success</flag>";
                    while(results.next()) 
                    {
                        
                        response.setContentType("text/xml");
                        
                        int accountid=results.getInt("accounting_unit_id");
                        String unitname=results.getString("accounting_unit_name").trim();
                        int accofficeid=results.getInt("accounting_unit_office_id");
                        int accforofficeid=results.getInt("accounting_for_office_id");
                        String officename=results.getString("Office_name");
                        
                        xml=xml+"<leng><accountid>"+accountid+"</accountid><unitname>"+unitname+"</unitname><accofficeid>"+accofficeid+"</accofficeid><accforofficeid>"+accforofficeid+"</accforofficeid><officename>"+officename+"</officename></leng>";
                        
                    }
                    xml=xml+"</response>";
                    out.write(xml);
                    System.out.println("xml is:"+xml);
                    }catch(Exception e) 
                    {
                        System.out.println("Exception in Accounting Unit:"+e);    
                      String  xml="<response><flag>failure</flag></response>";
                      out.write(xml);
                    }
                    
                     
         }
        if(command.equalsIgnoreCase("AccountUnit")) 
        {
                   System.out.println("AccountUnit");
                   String accountunitid=request.getParameter("AccountUnitId");
                   int aunitid=0;
                   if(accountunitid!=null) 
                   {
                       aunitid=Integer.parseInt(accountunitid);
                       System.out.println("Unit is:"+aunitid);
                   }
                   try
                   {
                   String sqlUnit="SELECT ACCOUNTING_UNIT_NAME,ACCOUNTING_UNIT_OFFICE_ID FROM FAS_MST_ACCT_UNITS WHERE ACCOUNTING_UNIT_ID = ?"; 
                   ps=connection.prepareStatement(sqlUnit);
                   ps.setInt(1,aunitid);
                   
                   results=ps.executeQuery();
                       String xml="";
                       xml="<response><flag>success</flag>";
                   if(results.next()) 
                   {
                       
                       response.setContentType("text/xml");
                       
                       String unitname=results.getString("ACCOUNTING_UNIT_NAME").trim();
                       int accofficeid=results.getInt("ACCOUNTING_UNIT_OFFICE_ID");
                     
                       
                       xml=xml+"<accountid>"+aunitid+"</accountid><unitname>"+unitname+"</unitname><accofficeid>"+accofficeid+"</accofficeid>";
                       
                   }
                   xml=xml+"</response>";
                   out.write(xml);
                   System.out.println("xml is:"+xml);
                   }catch(Exception e) 
                   {
                     System.out.println("Exception in Accounting Unit:"+e);    
                     String  xml="<response><flag>failure</flag></response>";
                     out.write(xml);
                     System.out.println("xml is:"+xml);
                   }
                   
                    
        }
         
    }

 
}
