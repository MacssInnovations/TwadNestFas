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

public class ValueAC_Rendering extends HttpServlet {
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


    	System.out.println("\nValueAC_Rendering.java - POST\n");
    	
    	/* Variables Declaration */
    	Connection connection=null;
        PreparedStatement ps=null,ps1=null,ps11=null;
        ResultSet rs11=null;


    	
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
    	int renderUnitId = 0;
    	int accUnitId = 0;
    	int accOfficeId = 0;
    	String dtFrm = null;
    	String active = null;
    	String dtTo = null;
    	String xml = null;
    	int cmbAcc_UnitCode=0;
    	String userid=(String)session.getAttribute("UserId");
        System.out.println("User Id is:"+userid);
    	
    	
    	
    	/**
    	 *   Get Parameters from Form 
    	 */

    	/* Get Command Parameter */
    	try
    	{
    	  Command = request.getParameter("Command");	
    	  System.out.println("Command : "+Command);
    	}
    	catch(Exception e)
    	{
    		System.out.println("Error fetching Command Parameter");
    	}
    	
    	
    	/**
    	 * Get renderUnitId Parameter 
    	 * 
    	 */
    	try
    	{
    		renderUnitId = Integer.parseInt(request.getParameter("renderUnitId"));
    		System.out.println("renderUnitId : "+renderUnitId);
    	}
    	catch(Exception e)
    	{
    		System.out.println("Error fetching renderUnitId Parameter");
    	}
    	
    	
    	
    	/**
    	 * Check Operation Details 
    	 */
    	if (Command.equalsIgnoreCase("ADD"))
    	{
    		

        	/* Get UnitId Parameter */
        	try
        	{
        		accUnitId = Integer.parseInt(request.getParameter("accUnitId"));	
        		System.out.println("accUnitId : "+accUnitId);
        	}
        	catch(Exception e)
        	{
        		System.out.println("Error fetching accUnitId Parameter");
        	}
        	
        	/* Get OfficeId Parameter */
        	try
        	{
        		accOfficeId = Integer.parseInt(request.getParameter("accOfficeId"));	
        		System.out.println("accOfficeId : "+accOfficeId);
        	}
        	catch(Exception e)
        	{
        		System.out.println("Error fetching accOfficeId Parameter");
        	}
        	
            	
    		/* Get Date Effective from */
        	try
        	{
        		dtFrm = request.getParameter("dtFrm");
        		System.out.println("dtFrm : "+dtFrm);
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

			
			/* Insert Query Execution */
            xml="<response><command>Add</command>";
            try {
                ps=connection.prepareStatement("INSERT INTO FAS_ASSET_VAL_AC_RENDER_UNITS(ACCT_RENDERING_UNIT_ID,ACCT_UNIT_ID_RENDERED_FOR,RENDERING_UNIT_OFFICE_ID,DATE_EFFECT_FROM,ACTIVE,DATE_EFFECT_UPTO,UPDATED_BY_USERID,UPDATED_DATE,STATUS) VALUES(?,?,?,?,?,?,?,SYSTIMESTAMP,'L')");
                ps.setInt(1,renderUnitId);
                ps.setInt(2,accUnitId);
                ps.setInt(3,accOfficeId);
                ps.setDate(4,dateFrom);
                ps.setString(5,active);
                ps.setDate(6,dateTo);
                ps.setString(7,userid);
                ps.executeUpdate();
                xml=xml+"<flag>success</flag>";
            }
            catch(Exception e) {
            
                 System.out.println("Exception in adding...."+e);
                xml=xml+"<flag>failure</flag>";
            }

        	
    	}
    	else if (Command.equalsIgnoreCase("UPDATE"))
    	{

        	/* Get UnitId Parameter */
        	try
        	{
        		accUnitId = Integer.parseInt(request.getParameter("accUnitId"));
        		System.out.println("accUnitId: "+accUnitId);
        	}
        	catch(Exception e)
        	{
        		System.out.println("Error fetching accUnitId Parameter");
        	}
        	
        	/* Get OfficeId Parameter */
        	try
        	{
        		accOfficeId = Integer.parseInt(request.getParameter("accOfficeId"));
        		System.out.println("accOfficeId: "+accOfficeId);
        	}
        	catch(Exception e)
        	{
        		System.out.println("Error fetching accOfficeId Parameter");
        	}
        	
 
    		/* Get Date Effective from */
        	try
        	{
        		dtFrm = request.getParameter("dtFrm");	
        		System.out.println("dtFrm: "+dtFrm);
        	}
        	catch(Exception e)
        	{
        		System.out.println("Error fetching dtFrm Parameter");
        	}
    		
        	
    		/* Get Active Status */
        	try
        	{
        		active = request.getParameter("active");	
        		System.out.println("active: "+active);
        	}
        	catch(Exception e)
        	{
        		System.out.println("Error fetching active Parameter");
        	}
   		
    		
        	/* Get Date Effect Up to */
        	try
        	{
        		dtTo = request.getParameter("dtTo");
        		System.out.println("dtTo: "+dtTo);
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
            xml="<response><command>Update</command>";
            try {
                ps=connection.prepareStatement("UPDATE FAS_ASSET_VAL_AC_RENDER_UNITS SET ACCT_UNIT_ID_RENDERED_FOR = ?, RENDERING_UNIT_OFFICE_ID = ?, DATE_EFFECT_FROM = ?, ACTIVE = ?, DATE_EFFECT_UPTO = ?, UPDATED_BY_USERID = ?, UPDATED_DATE = SYSTIMESTAMP WHERE ACCT_RENDERING_UNIT_ID = ?");
                ps.setInt(1,accUnitId);
                ps.setInt(2,accOfficeId);
                ps.setDate(3,dateFrom);
                ps.setString(4,active);
                ps.setDate(5,dateTo);
                ps.setString(6,userid);
                ps.setInt(7,renderUnitId);
                ps.executeUpdate();
                xml=xml+"<flag>success</flag>";
            }
            catch(Exception e) {
            
                System.out.println("Exception in updating...."+e);
                xml=xml+"<flag>failure</flag>";
            }
       	
    	}
    	else if (Command.equalsIgnoreCase("DELETE"))
    	{

    		System.out.println("renderUnitId : "+renderUnitId);
    		
			/* Delete Query Execution */
            xml="<response><command>Delete</command>";
            try {
            	
                ps=connection.prepareStatement("UPDATE FAS_ASSET_VAL_AC_RENDER_UNITS SET STATUS='C' WHERE ACCT_RENDERING_UNIT_ID = ?");
                ps.setInt(1,renderUnitId);
                ps.executeUpdate();
                xml=xml+"<flag>success</flag>";
            }
            catch(Exception e) {
            
                System.out.println("Exception in deleting...."+e);
                xml=xml+"<flag>failure</flag>";
            }
    	
    	}
    	else if (Command.equalsIgnoreCase("AssetRender"))
    	{

    		System.out.println("\n****************\nAssetRender\n**************\n");
    		int offid=Integer.parseInt(request.getParameter("offid"));
			/* Load Asset Rendering Units -  Query Execution */
            xml="<response><command>AssetRender</command>";
            try {
            	
                ps=connection.prepareStatement("SELECT a.ACCT_RENDERING_UNIT_ID, b.ACCOUNTING_UNIT_NAME FROM FAS_ASSET_NUM_AC_RENDER_UNITS a JOIN FAS_MST_ACCT_UNITS b ON a.ACCT_RENDERING_UNIT_ID = b.ACCOUNTING_UNIT_ID  where a.status='L' order by ACCT_RENDERING_UNIT_ID");
              // ps.setInt(1,offid);
                ResultSet result = ps.executeQuery();
                
                while(result.next())
                {
                	xml += "<record>";
                	xml += "<ASSET_RENDERING_UNIT_ID>"+result.getInt("ACCT_RENDERING_UNIT_ID")+"</ASSET_RENDERING_UNIT_ID>";
                	xml += "<ASSET_RENDERING_UNIT_NAME>"+result.getString("ACCOUNTING_UNIT_NAME").trim()+"</ASSET_RENDERING_UNIT_NAME>";
                	xml += "</record>";
                }
                xml=xml+"<flag>success</flag>";
            }
            catch(Exception e) {
            
                System.out.println("Exception loading Asset Rendering Units...."+e);
                xml=xml+"<flag>failure</flag>";
            }
    	
    	}
    	else if (Command.equalsIgnoreCase("LoadDivisions"))
    	{
// getting the circle office id from screen and pop up the acc unit and off id related to that
    		String circle_id="";
    		int cir_id=0;
    		try
    		{
    			circle_id=request.getParameter("circle_id");
    			cir_id=Integer.parseInt(circle_id);
    			//System.out.println("cir_id$$$$$$$$$$$$$$$$$$"+cir_id);
    		}
    		catch(Exception ee)
    		{
    			System.out.println("Exception arised while getting the circle id"+ee);
    	
    		}
    				
			/* Load division Units -  Query Execution */
            xml="<response><command>LoadDivisions</command>";
            try 
            	{
            	ps = connection.prepareStatement("select accounting_unit_office_id from fas_mst_acct_units where ACCOUNTING_UNIT_ID=?");
            	ps.setInt(1, cir_id);
            	ResultSet result = ps.executeQuery();
            	if(result.next())
            	{
            		try
                    {
                        ps11=connection.prepareStatement("select office_id,office_name from com_mst_all_offices_view where office_level_id in ('DN','LB') and circle_office_id=? order by office_id");
                        ps11.setInt(1, result.getInt("accounting_unit_office_id"));
                        ResultSet result11 = ps11.executeQuery();
                        int cir_off_id=0;
                        while(result11.next())
                        {
                        	cir_off_id=result11.getInt("office_id");
                        	ps1=connection.prepareStatement("select ACCOUNTING_UNIT_ID,ACCOUNTING_UNIT_NAME from fas_mst_acct_units where accounting_unit_office_id = ?");
                        	ps1.setInt(1,cir_off_id);
                        	ResultSet rs12 = ps1.executeQuery();
                        	while(rs12.next())
                        	{
		                        	xml += "<record>";
		                        	xml += "<ASSET_RENDERING_UNIT_ID>"+rs12.getInt("ACCOUNTING_UNIT_ID")+"</ASSET_RENDERING_UNIT_ID>";
		                        	xml += "<ASSET_RENDERING_UNIT_NAME>"+rs12.getString("ACCOUNTING_UNIT_NAME").trim()+"</ASSET_RENDERING_UNIT_NAME>";
		                        	xml += "</record>";
                        	}
                        }
                        xml=xml+"<flag>success</flag>";
                    }
            		catch(Exception ee)
            		{
            			System.out.println("Exception loading Asset Rendering Units...."+ee);
            			 xml=xml+"<flag>failure</flag>";
            		}
            		
            	}
            	}
            
            catch(Exception e) {
            
                System.out.println("Exception loading Asset Rendering Unitssss****."+e);
                xml=xml+"<flag>failure</flag>";
            }
    	
    	}       else if (Command.equalsIgnoreCase("LoadUnitWise_Office")) {

            String CONTENT_TYPE = "text/xml; charset=windows-1252";
            response.setContentType(CONTENT_TYPE);
            xml = "<response><command>LoadUnitWise_Office</command>";


            try {
                cmbAcc_UnitCode =
                        Integer.parseInt(request.getParameter("cmbAcc_UnitCode"));
            } catch (Exception e) {
                System.out.println("Exception to catch account head ");
            }


            try {
                ps =
                	connection.prepareStatement("select ACCOUNTING_FOR_OFFICE_ID,b.OFFICE_NAME from FAS_MST_ACCT_UNIT_OFFICES a," +
                       "COM_MST_OFFICES b where a.ACCOUNTING_FOR_OFFICE_ID=b.OFFICE_ID and a.ACCOUNTING_UNIT_ID=? ");
                ps.setInt(1, cmbAcc_UnitCode);
                ResultSet rs = ps.executeQuery();
                int cnt = 0;

                while (rs.next()) {
				                    xml =
				 xml + "<offid>" + rs.getInt("ACCOUNTING_FOR_OFFICE_ID") + "</offid>";
				                    xml =
				 xml + "<offname>" + rs.getString("OFFICE_NAME") + "</offname>";
				                    cnt++;
                }
                if (cnt != 0)
                    xml = xml + "<flag>success</flag>";
                else
                    xml = xml + "<flag>failure</flag>";
            } catch (Exception e) {
                System.out.println("catch..HERE.in load head code." + e);
                xml = xml + "<flag>failure</flag>";
            }
        }
    	
    	
    	xml = xml + "</response>";
    	System.out.println(xml);
    	out.println(xml);
    	
    }
}
