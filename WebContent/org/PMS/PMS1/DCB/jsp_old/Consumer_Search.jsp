<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">
<%@ page session="false" contentType="text/html;charset=windows-1252"%>
<%@ page import="java.sql.*,java.util.ResourceBundle"%>
<%@ page import="Servlets.Security.classes.UserProfile"%>
<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=windows-1252"/>
    <title>Search Consumer Name</title>
    
    <link href="../../../../../css/Sample3.css" rel="stylesheet" media="screen"/>
    
	<script type="text/javascript" src="../scripts/Consumer_Search.js"></script>
			
	 
	<%
		int ctp = Integer.parseInt(request.getParameter("ctype"));
		String ctypedesc = request.getParameter("ctypedesc");
	%>
	<script type="text/javascript">
		var CONS;
		var CTYPE = <%=ctp%>;
		var CTYPEDESC = <%=ctypedesc%>;
	</script>
 

  </head>
  <body onload="init(); callServer('District');">
  <%
   Connection connection=null;
   Statement statement=null;
   ResultSet results=null; 
   ResultSet results1=null; 

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
    //ConnectionString = strdsn.trim() + "@" + strhostname.trim() + ":" + strportno.trim() + ":" +strsid.trim() ;

     Class.forName(strDriver.trim());
     connection=DriverManager.getConnection(ConnectionString,strdbusername.trim(),strdbpassword.trim());

       
       try
       {
            statement=connection.createStatement();
            connection.clearWarnings();
       }
       catch(SQLException e)
       {
              System.out.println("Exception in creating statement:"+e);
              return;
       }          
  }
  catch(Exception e)
  {         
         System.out.println("Exception in openeing connection:"+e);
         return;
  }  
 %>
 

 
  
 <form name="frmConsumerSearch" method="get" action="">
		      <table width="100%">
		        <tr> 
		          <td class="tdH">
		            <center>
		              <h3>Beneficiary Search</h3>
		            </center>
		          </td>
		        </tr>
		      </table>




            <table cellspacing="3" cellpadding="2" border="0" width="100%" align="center" class="table">
              
<tr><td id="box1" style="display:none"><div><table cellspacing="3" cellpadding="2" border="1" width="100%" align="center">              
              <tr>
                <td>
                	<div align="left">
                    	District
                  	</div>
                </td>
                
                <td>
                  <div align="left">
                  		<select name="dis" id="dis" onchange="callServer('Block');">
                  				<option value="">--Select District--</option>
                                <!-- %
                                            String sql = null;
                                             try
                                               {
                                                    sql="select DISTRICT_CODE,DISTRICT_NAME FROM COM_MST_DISTRICTS";
                                                    results=statement.executeQuery(sql);
                                                    while(results.next()) 
                                                    {
                                                        out.println("<option value=" + results.getInt("DISTRICT_CODE") + ">" + results.getString("DISTRICT_NAME") + "</option>");
                                                    }
                                                    results.close();
                                               }
                                            catch(Exception e)
                                            {
                                                    System.out.println("Exception executing District Query : " + e + "\nQUERY: " + sql);
                                            }    
                                %-->
                        </select>
                  </div>
                </td>
              </tr>
              
              
              <tr>
                <td>
                  <div align="left">
                    Block
                  </div>
                </td>
                
                <td>
                  <div align="left">
                    <select size="1" name="blk" id="blk" onchange="search();" >
                      <option value="">----Select Block----</option>
                    </select>
                  </div>
                </td>
              </tr>
              
                            
              <tr>
                <td>
                  <div align="left">
                    Panchayat Name
                  </div>
                </td>
                
                <td>
                  <div align="left">
				  	<input name="pan" id="pan" type="text" onkeyup="search();"/>
                  </div>
                </td>
              </tr>
              
</table></div></td>   


                                   
<td id="box2" style="display:none"><div><table cellspacing="3" cellpadding="2" border="1" width="100%" align="center">              
              <!-- tr>
                <td>
                  <div align="left">Area Type</div>
                </td>
                
                <td>
                  <div align="left">
                    <select size="1" name="area" id="area" onchange="search()">
                      <option value="">--Select Area Type--</option>
                    </select>
                  </div>
                </td>
              </tr-->
              
              
                                      
              
              <tr>
                <td>
                  <div align="left">Urban LB Grade</div>
                </td>
                
                <td>
                  <div align="left">
                    <select size="1" name="ULBgrade" id="ULBgrade" disabled onchange="search()">
                      <option value="">--Select Urban LB Grade--</option>
                    </select>
                  </div>
                </td>
              </tr>
                            
                            
              <tr>
                <td>
                  <div align="left">
                    Urban LB Name
                  </div>
                </td>
                
                <td>
                  <div align="left">
				  	<input name="ulb" id="ulb" type="text" onkeyup="search();"/>
                  </div>
                </td>
              </tr>
              
</table></div></td>


                                   
<td id="box3" style="display:none"><div><table cellspacing="3" cellpadding="2" border="1" width="100%" align="center">              
           
              <tr>
                <td>
                  <div align="left">
                    Name of the Private/Other Consumer
                  </div>
                </td>
                
                <td>
                  <div align="left">
				  	<input name="priv" id="priv" type="text" onkeyup="search();"/>
                  </div>
                </td>
              </tr>
              
</table></div></td>



</tr>              
</table>              
    
     
     
        <table cellspacing="3" cellpadding="2" border="1" width="100%" align="center">
        	<tr>                            
          		<td class="tdH">
          			<div align="center">
          				
          				<input type="reset" name="clear" value="CLEAR"
                   				id="clear" align="middle" onclick="clearGrid();"/>
          				<input type="button" name="exit" value="EXIT"
                   				id="exit" onclick="self.close()" align="middle"/>
          			</div>                   
          		</td>
          	</tr>
      </table>
                   		

      <table cellspacing="3" cellpadding="2" border="1" width="100%" align="center" class="table">
        	
        	<tr>
        		<th id="tblHead" align="center" colspan="4">
        			<br/>
        			List of Beneficiaries
        			<div align="right">
		                Page Size &nbsp;&nbsp;&nbsp;&nbsp;
		                <select name="cmbpagination" id="cmbpagination" onchange="search();">
	                  		<option value="5" >5</option>
		                  	<option value="10" selected="selected">10</option>
		                  	<option value="15">15</option>
		                  	<option value="20">20</option>
	                	</select>
                	</div>
        		</th>
        	</tr>
  
        	<tr class="tdH" id="colHead">    
        		    
   			</tr>

  
          	<tr>
        		<td id="tdNoData" align="center" colspan="4">
        			<div id="nodata" style="display:none">(No Data Found)</div>
        		</td>
        	</tr>
  
  
	        <tbody name="tbody" id="tbody" >
	   		</tbody>
   		



   	  </table>


    
        <table cellspacing="3" cellpadding="2" border="1" width="100%" align="center">
            <tr class="tdH" name="pgbar" id="pgbar" >
              <td> 
                <table align="center" cellspacing="3" cellpadding="2"
                       border="0" width="100%">
                  <tr>
                    <td width="30%">
                      <div align="left">
                        <div id="divpre" style="display:none"><a href="javascript:prev()"><label><< </label>Previous</a></div>
                      </div>
                    </td>
                    <td width="40%">
                      <div align="center">
                        <table border="0">
                          <tr>
                            <td>
                              <div id="divcmbpage" style="display:none">
                                Page&nbsp;&nbsp;<select name="cmbpage"
                                                        id="cmbpage"
                                                        onchange="callServer(CONS);">
                                                	<option value="1">1</option>        
                                                </select>
                              </div>
                            </td>
                            <td>
                              <div id="divpage" style="display:none">1</div>
                            </td>
                          </tr>
                        </table>
                      </div>
                    </td>
                    <td width="30%">
                      <div align="right">
                        <div id="divnext" style="display:none"><a href="javascript:next()">Next <label>>></label></a></div>
                      </div>
                    </td>
                  </tr>
                </table>
              </td>
            </tr>
            
            
        	<tr>                            
          		<td class="tdH">
          			<div align="center">
          				<input type="button" name="add" value="SUBMIT"
                   				id="add" onclick="done();" align="middle"/>
          				<input type="button" name="exit" value="EXIT"
                   				id="exit" onclick="self.close()" align="middle"/>
          			</div>                   
          		</td>
          	</tr>
      </table>
                   		


 
    </form>
  </body>
</html>