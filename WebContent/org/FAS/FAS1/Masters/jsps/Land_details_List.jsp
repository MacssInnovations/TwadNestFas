<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">
<%@ page contentType="text/html;charset=windows-1252"%>
<%@ page session="false" contentType="text/html;charset=windows-1252"%>
<%@ page import="java.sql.*,java.util.*,Servlets.Security.classes.UserProfile"%>
<%@ include file="//org/Security/jsps/Check_SessionJSPF.jspf"%>

<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=windows-1252"/>
    <meta http-equiv="cache-control" content="no-cache">
    <title>List of Lands</title>
    
    
    <script type="text/javascript" src="<%=request.getContextPath()%>/org/FAS/FAS1/CommonControls/scripts/Common_Load_Accounting_Unit_ID.js"></script>
    <script type="text/javascript" src="<%=request.getContextPath()%>/org/FAS/FAS1/CommonControls/scripts/Common_Load_Accounting_office.js"></script>


       <script type="text/javascript" src="<%=request.getContextPath()%>/org/Library/scripts/comJS.js"></script>
       <script type="text/javascript" src="../../../../../org/FAS/FAS1/Masters/scripts/Land_details.js"></script>
	   <link href="../../../../../css/Sample3.css" rel="stylesheet" media="screen"/>
	   <script type="text/javascript" language="javascript">
	
		   function btncancel()
		   {
		   	self.close();
		   }
		
		
		   
		
		  
		   
	  </script>
  </head>
  <%
  int unitcode=Integer.parseInt(request.getParameter("accounting_unit_id"));
  System.out.println("unitcode::::"+unitcode);
  int accounting_unit_office_id=Integer.parseInt(request.getParameter("accounting_unit_office_id"));
  System.out.println("accounting_unit_office_id::::"+accounting_unit_office_id);
  String financial_year=request.getParameter("financial_year");
  System.out.println("financial_year::::"+financial_year);
  %>
  <body onload="callServer1(<%= unitcode %>,<%= accounting_unit_office_id %>,'<%=financial_year%>');" bgcolor="rgb(255,255,225)">
 
                               
                        
                        
 
        
  <form >
      <%
  
  Connection con=null;
    ResultSet result1=null;
    PreparedStatement ps1=null;
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

           // ConnectionString = strdsn.trim() + "@" + strhostname.trim() + ":" + strportno.trim() + ":" +strsid.trim() ;
				ConnectionString = strdsn.trim() + "://" + strhostname.trim() + ":" + strportno.trim() + "/" +strsid.trim() ;    // Postgres DB  Connection
             Class.forName(strDriver.trim());
             con=DriverManager.getConnection(ConnectionString,strdbusername.trim(),strdbpassword.trim());
  }
  catch(Exception e)
  {
    System.out.println("Exception in connection...."+e);
  }
  
  
  %>
      
      <table cellspacing="2" cellpadding="3" border="1" width="100%">

	       <tr class="tdH">
		      	<th>Asset Code</th>
		        <th>Taluk</th>
		        <th>Village</th>
		        <th>Nature of acquisition </th>
		        <th>Name of the owner</th>
		 		<th>Lease Period</th>
		 		<th>Total Amount paid </th>
		 		<th>Registration office </th>
	       </tr>
	
	      <tbody id="tblBuild" class="table">

          <%
         
       try
       {
          int y=0;
                                     
                    	   System.out.println("all units::::::::");
                    	   String query_select= "SELECT accounting_unit_id,  " +
						"  accounting_unit_office_id,  " +
						"  financial_year,  " +
						"  asset_code,  " +
						"  SUB_LEDGER_TYPE_CODE,  " +
						"  SUB_LEDGER_CODE,  " +
						"  BP_NO,  " +
						"  TALUK,  " +
						"  VILLAGE,  " +
						"  NATURE_ACQN,  " +
						"  BOUND_NORTH,  " +
						"  BOUND_SOUTH,  " +
						"  BOUND_EAST,  " +
						"  BOUND_WEST,  " +
						"  LAND_TYPE,  " +
						"  EXTENT_AREA,  " +
						"  SURVEY_NO,  " +
						"  FOUNDN_TYPE,  " +
						"  OWNER_NAME,  " +
						"  LEASE_PERIOD,"+
						"  AMOUNT_LAND,"+
						"  AMOUNT_BUILDINGS,"+
						"  TOTAL_AMOUNT,"+
						"  VOUCHER_NO,"+
						"  VOUCHER_DATE,"+
						"  REG_OFFICE,"+
						"  REG_DOCNO,"+
						"  REG_DATE,"+
						"  TITLE_DETAILS,"+
						"  REMARKS,"+
						"  UPDATED_BY_USERID,"+
						"  UPDATED_DATE,"+
						"  STATUS "+
						"FROM fas_land_details " +
						"WHERE accounting_unit_id =" +unitcode+	"  AND accounting_unit_office_id =" +accounting_unit_office_id+	"  AND financial_year ='"+financial_year+"'";
                    	   System.out.println("query_select:::::"+query_select);
                    	   System.out.println("ps1:::::"+ps1);
                    	  ps1=con.prepareStatement(query_select);
                    	  System.out.println("ps1:oooo::::"+ps1);
       		         result1 = ps1.executeQuery(); 
                        
                      //  System.out.println("ps2:::::"+ps2);
                       // rs2=ps2.executeQuery();
                        //System.out.println("rs2:::::");
                        int rowid=0;  
                      
                        while(result1.next())
                        {
                        	 System.out.println("whileeeeeeeeee:::::");
                            rowid++;
                            out.println("<tr id="+rowid+">");   
                            if(y==0)
                            {
                            	out.println("<td align='left'>"+result1.getInt("ASSET_CODE")+"</td>");
                            y=1;
                            }
                            else                     
                          
                            out.println("<td align='left'>"+result1.getInt("ASSET_CODE")+"</td>");
                            out.println("<td align='left'>"+result1.getString("TALUK")+"</td>");
                            out.println("<td align='left'>"+result1.getString("VILLAGE")+"</td>");
                            out.println("<td align='left'>"+result1.getString("NATURE_ACQN")+"</td>");
                            out.println("<td align='left'>"+result1.getString("OWNER_NAME")+"</td>");
                            out.println("<td align='left'>"+result1.getString("LEASE_PERIOD")+"</td>");
                            out.println("<td align='left'>"+result1.getString("TOTAL_AMOUNT")+"</td>");
                            out.println("<td align='left'>"+result1.getInt("REG_OFFICE")+"</td>");
                           
                        }
                      
                        if(y!=0)
                        {
                        	result1.close();
                               ps1.close();
                        }
                        else
                        
                        out.println("<tr><td align='left'>No data found</td><td></td><td></td><td></td><td></td><td></td></tr>");
                       
            
          }
          catch(Exception e)
          {
            System.out.println("Exception in ggggg.."+e);
          }
         
          %>
         </tbody>

       </table>






      <table align="center" cellspacing="3" cellpadding="2" border="1"
             width="100%">             
        <tr class="tdH">
          <td>
            <div align="center">
              <input type="button" id="cmdcancel" name="cancel" value="Exit"
                     onclick=" self.close();"></input>
            </div>
          </td>
        </tr>
      </table> 
      
      
    </form>
  </body>
</html>
