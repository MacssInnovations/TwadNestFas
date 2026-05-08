<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">
<%@ page session="false" contentType="text/html;charset=windows-1252"%>
<%@ page import="java.sql.*,java.util.ResourceBundle"%>
<%@ page import="Servlets.Security.classes.UserProfile"%>
<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=windows-1252"/>
    <title>Consumer Details</title>
    
    <link href="../../../../../css/Sample3.css" rel="stylesheet" media="screen"/>
    
              <% 
		                    Connection connection=null;
					        Statement statement=null;
					        ResultSet result=null;
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
						              }          
					           }
					          catch(Exception e)
					          {
					             System.out.println("Exception in openeing connection:"+e);
					          }

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
												
				         	UserProfile empProfile = (UserProfile)session.getAttribute("UserProfile");
				         	System.out.println("user id::" + empProfile.getEmployeeId());
				         	
				         	
				         	int empid = empProfile.getEmployeeId();
				         	int oid = 0;
				         	String oname = "";
				
				         	try 
				         	{
				         		ps = connection.prepareStatement("select OFFICE_ID from HRM_EMP_CURRENT_POSTING where EMPLOYEE_ID=?");
				         		ps.setInt(1, empid);
				         		result = ps.executeQuery();
				         		if(result.next()) 
				         		{
				         			oid = result.getInt("OFFICE_ID");
				         		}
				         		result.close();
				         		ps.close();
				         	} 
				         	catch (Exception e) 
				         	{
				         		System.out.println(e);
				         	}
				         
              %>
    <script type="text/javascript">
    	var OID = <%=oid%>
    	
    </script>
    <script type="text/javascript" src="../scripts/Consumer.js"></script>
    <script type="text/javascript" src="../scripts/Pagination.js"></script>
 
  </head>
  <body onload="callServer('Type'); callServer('Get'); //callServer('Group'); ">
   <font size="2">
 
 <form name="frmConsumer" method="get" action="Consumer?Report">

      <table width="100%">
        <tr>
          <td class="tdH">
            <center>
              <h3>Beneficiary Master</h3></center>
          </td>
        </tr>
      </table>


		<table cellspacing="1" cellpadding="1" border="1" width="100%" align="center" class="table">
            
              <tr>
                <td class="table" style="width: 559px">
                    <b><font face="Tahoma" color="#808080" size="2">Beneficiary No</font></b>
                </td>
                
                <td class="table">
                  <div align="left">
                    <input type="text" name="cid" id="cid" readonly="readonly" size="4" /> (System generated)
                  </div>
                </td>
              </tr>
              
              

              <!-- tr>
                <td class="table">
                  <div align="left">Beneficiary Code(User Reference)</div>
                </td>
                
                <td class="table">
                  <div align="left">
                    <input type="text" name="ccode" id="ccode" size="10" />
                  </div>
                </td>
              </tr-->

              
              <tr>
                <td class="table">
                  <div align="left">
                    Beneficiary Type <font color="red">*</font><label style="color:rgb(255,0,0);">&nbsp;</label>
                  </div>
                </td>
                
                <td class="table">
                  <div align="left">
                    <select size="1" name="ctype" id="ctype" onchange="clrBen(); callServer('Get');" >                    <!--  onchange="searchBen();" --> <!-- onchange="isPriv();"-->
                      <option value="">----Select Type----</option>
                    </select>
                  </div>
                </td>
              </tr>
              

              <!-- tr>
              	<td>
              		District
              	</td>
              	<td>
                    <select name="dis" id="dis" onchange="callServer('Block');">
                  		<option value="">--Select District--</option>
              		</select>
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
                    <select size="1" name="blk" id="blk" onchange="callServer('Panch');" disabled="disabled">
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
                    <select size="1" name="pan" id="pan" disabled="disabled">
                      <option value="">----Select Panchayat----</option>
                    </select>
                </td>
              </tr-->
              
              
              
              
              
              
              
              <tr>
                <td class="table">
                  <div align="left">Beneficiary Name <font color="red">*</font></div>
                </td>
                
                <td class="table">
                  <div align="left">
                    <input type="text" name="cname" id="cname" readonly/>
                    <img src="../../../../../images/c-lovi.gif" width="20" height="20" alt="Click here" onclick="searchConsumer();"/>
                  </div>
                </td>
              </tr>
              
             


              <!-- tr>
                <td class="table">
                  <div align="left">Beneficiary Group Id</div>
                </td>
                
                <td class="table">
                  <div align="left">
                    <select size="1" name="group" id="group">
                      <option value="">----Select Type----</option>
                    </select>
                  </div>
                </td>
              </tr-->

            


              <tr>
                <td class="table">
                  <div align="left">Consumption Category</div>
                </td>
                
                <td class="table">
                  <div align="left">
					<input type="radio" name="consumption" id="petty" value="0" checked>Petty WS <input type="radio" name="consumption" id="bulk" value="1">Bulk WS </div>
                </td>
              </tr>


<tr>


<td>
		<table cellspacing="1" cellpadding="1" border="0" width="100%" align="center">
			  <tr>
				  <td class="tdH" colspan="2">
					  <b>Billing Address</b>
				  </td>
			  </tr>

		
              <tr>
                <td class="table">
                  <div align="left">Billing address 1 <font color="red">*</font></div>
                </td>
                
                <td class="table">
                  <div align="left">
                    <input type="text" name="adr1" id="adr1" onkeyup="this.value = (this.value).toUpperCase();" />
                  </div>
                </td>
              </tr>


              <tr>
                <td class="table">
                  <div align="left">Billing address 2  <font color="red">*</font></div>
                </td>
                
                <td class="table">
                  <div align="left">
                    <input type="text" name="adr2" id="adr2" onkeyup="this.value = (this.value).toUpperCase();" size="30" />
                  </div>
                </td>
              </tr>


              <tr>
                <td class="table">
                  <div align="left">Billing address 3</div>
                </td>
                
                <td class="table">
                  <div align="left">
                    <input type="text" name="adr3" id="adr3" onkeyup="this.value = (this.value).toUpperCase();" size="30" />
                  </div>
                </td>
              </tr>


              <tr>
                <td class="table">
                   Pincode 
                </td>
                
                <td class="table">
                  <div align="left">
                    <input type="text" name="pin" id="pin" onkeypress="return numonly(this.value,6,event);" size="6" />
                  </div>
                </td>
              </tr>


              <tr>
                <td class="table">
                  Landline
                </td>
                
                <td class="table">
                  <div align="left">
                    <input type="text" name="llno" id="llno" onkeypress="return landline(this.value,event);" onblur="llnoLastCheck(this.value)" size="14" />
                  </div>
                </td>
              </tr>


              <tr>
                <td class="table">
                   Mobile
                </td>
                
                <td class="table">
                  <div align="left">
                    <input type="text" name="cell" id="cell" onkeypress="return numonly(this.value,10,event);" size="10" />
                  </div>
                </td>
              </tr>


              <tr>
                <td class="table">
                  <div align="left">e-mail id</div>
                </td>
                
                <td class="table">
                  <div align="left">
                    <input type="text" name="email" id="email" onchange="emailCheck(this.value);" />
                  </div>
                </td>
              </tr>
</table>
</td>		
<td>
			<table cellspacing="1" cellpadding="1" border="0" width="100%" align="center">
			  <tr>
				  <td class="tdH" colspan="2">
					  <b>Beneficiary Office Address</b> &nbsp; &nbsp; &nbsp; <input type="checkbox" onclick="copyContact(this.checked)" /> <font size='2'>Tick if same as Billing Address </font> 
				  </td>
			  </tr>


              <tr>
                <td class="table">
                  <div align="left">Office address 1 </div>
                </td>
                
                <td class="table">
                  <div align="left">
                    <input type="text" name="adr1off" id="adr1off" onkeyup="this.value = (this.value).toUpperCase();" />
                  </div>
                </td>
              </tr>


              <tr>
                <td class="table">
                  <div align="left">Office address 2 </div>
                </td>
                
                <td class="table">
                  <div align="left">
                    <input type="text" name="adr2off" id="adr2off"  onkeyup="this.value = (this.value).toUpperCase();" size="30" />
                  </div>
                </td>
              </tr>


              <tr>
                <td class="table">
                  <div align="left">Office address  3 </div>
                </td>
                
                <td class="table">
                  <div align="left">
                    <input type="text" name="adr3off" id="adr3off" onkeyup="this.value = (this.value).toUpperCase();" size="30" />
                  </div>
                </td>
              </tr>


              <tr>
                <td class="table">
                   Pincode 
                </td>
                
                <td class="table">
                  <div align="left">
                    <input type="text" name="pinoff" id="pinoff" onkeypress="return numonly(this.value,6,event);" size="6"/>
                  </div>
                </td>
              </tr>


              <tr>
                <td class="table">
                  Landline
                </td>
                
                <td class="table">
                  <div align="left">
                    <input type="text" name="llnooff" id="llnooff" onkeypress="return landline(this.value,event);" onblur="llnoLastCheck(this.value)" size="14" />
                  </div>
                </td>
              </tr>


              <tr>
                <td class="table">
                   Mobile
                </td>
                
                <td class="table">
                  <div align="left">
                    <input type="text" name="celloff" id="celloff" onkeypress="return numonly(this.value,10,event);" size="10" />
                  </div>
                </td>
              </tr>


              <tr>
                <td class="table">
                  <div align="left">e-mail id</div>
                </td>
                
                <td class="table">
                  <div align="left">
                    <input type="text" name="emailoff" id="emailoff" />
                  </div>
                </td>
              </tr>
</table>
</td>





</tr>
</table>
     
     
     
     
     
     
     
     
     
     
        <table cellspacing="3" cellpadding="2" border="1" width="100%" align="center">
        	<tr>                            
          		<td class="tdH" width="85%">
          			<div align="center">
          				<input type="button" name="Add" value="ADD"
                   				id="Add" onclick="callServer('Add')" align="middle"/>
          				<input type="button" name="Update" value="UPDATE" style="display:none"
                   				id="Update" onclick="callServer('Update')" align="middle"/>
          				<input type="button" name="Delete" value="DELETE" style="display:none"
                   				id="Delete" onclick="callServer('Delete')" align="middle"/>
          				<input type="button" name="Report" value="VIEW REPORT"
                   				id="Report" onclick="report();" align="middle"/>
          				<input type="button" name="clear" value="CLEAR"
                   				id="clear" align="middle" onclick="refresh();"/>
          				<input type="button" name="exit" value="EXIT"
                   				id="exit" onclick="self.close()" align="middle"/>
          			</div>                   
          		</td>
          		<th id="tblHead" align="right" colspan="1" class="tdH" width="15%">
		                Page Size &nbsp;&nbsp;&nbsp;&nbsp;
		                <select name="cmbpagination" id="cmbpagination" onchange="search();">
	                  		<option value="5" >5</option>
		                  	<option value="10" selected="selected">10</option>
		                  	<option value="15">15</option>
		                  	<option value="20">20</option>
	                	</select>
        		</th>
          		
          	</tr>
      </table>
                   		<!--input type="button" name="tmp" value="GENERATE CODE"
                   				id="exit" onclick="temp()" align="middle"/-->

 
 
 
 
 
 
 
 
 
 
 
 
                    		

      <table cellspacing="1" cellpadding="1" border="1" width="100%" align="center" class="table">
        	
        	<!-- tr>
        		<th id="tblHead" align="right" colspan="3">
        			<br>
        			
        			<br>
        		</th>
        		<th id="tblHead" align="right" colspan="1">
		                Page Size &nbsp;&nbsp;&nbsp;&nbsp;
		                <select name="cmbpagination" id="cmbpagination" onchange="search();">
	                  		<option value="5" >5</option>
		                  	<option value="10" selected="selected">10</option>
		                  	<option value="15">15</option>
		                  	<option value="20">20</option>
	                	</select>
        		</th>
        	</tr-->
  
        	<tr class="tdH" id="colHead">    
        		    
   			</tr>

  
			<tr class="tdH">
          		<th>
          			Select
          		</th>
          		<th>
          			Beneficiary Type
          		</th>
          		<th>
          			Beneficiary Name
          		</th>
          		<th>
          			Billing Address
          		</th>
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
 		</table>           
            
 
 
 
 
    </form>
    </font>
  </body>
</html>