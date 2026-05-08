<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">
<%@ page session="false"  contentType="text/html;charset=windows-1252"%>
<%@ page import="java.sql.*,java.util.*"%>
<%@ include file="//org/Security/jsps/Check_SessionJSPF.jspf" %>
<html>
  <head>
    	<meta http-equiv="Content-Type" content="text/html; charset=windows-1252"/> 
    	<META HTTP-EQUIV="CACHE-CONTROL" CONTENT=" no-store, no-cache, must-revalidate" >
    	<META HTTP-EQUIV="CACHE-CONTROL" CONTENT=" pre-check=0, post-check=0, max-age=0" >
    	<title>List of Account Heads for Verification</title>
    	<link href="../../../../../css/Sample3.css" rel="stylesheet"    media="screen"/>
     	<link href="../../../../../css/CalendarControl.css" rel="stylesheet" media="screen" />     
     	
     	<script type="text/javascript" src="../../../../HR/HR1/OfficeMaster/scripts/CalendarControl.js"></script> 
    	<script type="text/javascript" src="../../../../Library/scripts/checkDate.js"></script>	
     	<script type="text/javascript" src="<%=request.getContextPath()%>/org/Library/scripts/comJS.js"></script>
    	<script type="text/javascript" src="../scripts/AccountHead_Verification.js"></script>
    	
    	<script type="text/javascript" language="javascript">
    	function loadDate()
        { 
                 var today= new Date(); 
                 var day=today.getDate();
                 var month=today.getMonth();
                 month=month+1;
                 
                 if(day<=9 && day>=1)
                 day="0"+day;
                 if(month<=9 && month>=1)
                 month="0"+month;
                 var year=today.getYear();
                 if(year < 1900) year += 1900;
                 var monthArray =new Array("January", "February", "March", 
                           "April", "May", "June", "July", "August",
                           "September", "October", "November", "December");
                 var finyear="";
                 if(month>=4)
                	 {
                	 	finyear=year+"-"+(year+1);
                	 }
                 else
                	 {
                	 	finyear=year+"-"+(year-1);
                	 }
                 document.FasAcc_Head_Verification.cmbAccountHead_FY.value=finyear;     
                 document.FasAcc_Head_Verification.txtverificationDate.value=day+"/"+month+"/"+year;
        }
    	</script>
    	<script type="text/javascript">
				var checked=false;
				var frmname='';
				function checkedAll(frmname)
				{
				 var valus= document.getElementById(frmname);
				 if (checked==false)
				 {
				 checked=true;
				 }
				 else
				 {
				 checked = false;
				 }
				 for (var i =0; i < valus.elements.length; i++)
				 {
				 valus.elements[i].checked=checked;
				 }
				}
	</script>
	<script language="javascript" type="text/javascript">

			function SelectAllCheckBoxes(action)
			
			{
			
			var myform=document.forms['FasAcc_Head_Verification'];
			
			var len = myform.elements.length;
			   for( var i=0 ; i < len ; i++)
			
			   {
			
			   if (myform.elements[i].type == 'checkbox')
			
			      myform.elements[i].checked = action;
			
			   }
			
			}

</script> 
    	<%String s=request.getContextPath(); %>
		<%System.out.println(s); %>
    	
   </head>
<body onload="loadDate();">
<table cellspacing="1" cellpadding="3" width="100%" >
      <tr class="tdH">
        <td colspan="2">
          <div align="center">
            <h3><strong>Account Head Verification</strong></h3>
          </div>
        </td>
      </tr>
    </table>
 <form name="FasAcc_Head_Verification" id="FasAcc_Head_Verification" method="POST"
                            action="../../../../../AccountHead_Verification?Command=Add"  onSubmit="return checkNull()">
                            <input type='hidden' name='RecordCount' id='RecordCount' value='0' /> 
   <%
		      Connection con=null;
		      ResultSet rs=null,rs2=null;
		      Statement st=null;
		      PreparedStatement ps=null;
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
		    
		                //ConnectionString = strdsn.trim() + "@" + strhostname.trim() + ":" + strportno.trim() + ":" +strsid.trim() ;
		    					ConnectionString = strdsn.trim() + "://" + strhostname.trim() + ":" + strportno.trim() + "/" +strsid.trim() ;    // Postgres DB  Connection
		                Class.forName(strDriver.trim());
		                con=DriverManager.getConnection(ConnectionString,strdbusername.trim(),strdbpassword.trim());
		      }
		      catch(Exception e)
		      {
		        System.out.println("Exception in connection...."+e);
		      }
    %>	
    <input type="hidden" name="cmbMas_SL_type" id="cmbMas_SL_type" value="7" onchange="doFunction('Load_MasterSL_Code',this.value);" />
    				<table cellspacing="1" cellpadding="3" border="1" width="100%">
                    	<tr align="left" class="table"> 
                        	<td width="27%">
                            	<div>
                                	Financial Year <font color="#ff2121">*</font>
                            	</div>
                        	</td>
                      		 <td width="73%">
                           	 	<div align="left">
                                	<select size="1" name="cmbAccountHead_FY" id="cmbAccountHead_FY" >
                                    <option value="0">Select</option>
                                   	<%
					                        st=con.createStatement();
					                        rs=st.executeQuery("select financial_year from cash_book_control order by financial_year");
					                        while(rs.next())
					                        {
					                            out.println("<option value='"+rs.getString("financial_year")+"'>"+rs.getString("financial_year")+"</option>");
					                        }
                    				%>
                                	</select>
                            	</div>
                        	  </td> 
                        </tr>
                       
              			 <tr>
					          <td class="table">Major Group<font color="#ff2121">*</font>
					          <select name="Major_Grp"  id="Major_Grp"  onchange="loadingMinor('loadMinor')">
					          <option value="All">All</option>
					            <%
					                try
					                {
					                ps=con.prepareStatement("select MAJOR_HEAD_CODE,MAJOR_HEAD_DESC from COM_MST_MAJOR_HEADS");
					                rs=ps.executeQuery();
					                    while(rs.next())
					                    {
					                    out.println("<option value="+rs.getString("MAJOR_HEAD_CODE")+">"+rs.getString("MAJOR_HEAD_DESC")+"</option>");
					                    }
					                } 
					                catch(Exception e)
					                {
					                System.out.println("Exception in Major combo..."+e);
					                }
					                finally
					                {
					                rs.close();
					                ps.close();
					                }   
					            %>
					          </select>
					          </td>
          						<td>
          							Minor Group <font color="#ff2121">*</font>
           							<select name="Minor_Grp" id="Minor_Grp">
             							<option value="All">All</option>
           							</select>
           							<input type="BUTTON" value="Go" name="MajMin" onclick="searchByMajorMinor();"/>
           							<input type="BUTTON" value="Verified List" name="verifiedlist" onclick="Verified_list();"/>
          						</td>
        				</tr>
        				<tr class="table">
							<td width="40%">
								<div align="left">Verification Done By<font color="#ff2121">*</font>
								</div>		
							</td>
							<td width="60%">
								<table align="left">
									<tr align="left">
										<td>
											<div align="left"><select size="1" name="cmbMas_SL_Code"	id="cmbMas_SL_Code">
															  </select>
											</div>				
										</td>
										<td>
											<div align="left" id="offlist_div_master" style="display: none">
												<img src="../../../../../images/c-lovi.gif" width="20" height="20"
													alt="OfficeList" onClick="jobpopup_master();">
												</img> 
												<input type="text" name="txtOfficeID_mas" id="txtOfficeID_mas"
													maxlength="4" size="5" onBlur="mas_office(this.value);" />
											</div>
												<div align="left" id="emplist_div_master">
													<img src="../../../../../images/c-lovi.gif" width="20" height="20"
														alt="empList" onClick="employee_popup_master();">
													</img> 
									                <input type="text" name="txtEmpID_mas" id="txtEmpID_mas" maxlength="6"
														size="6" onBlur="mas_employee(this.value);"  onchange="getOffice('<%=s %>');"/>
													<input type="hidden" name="cmbSL_type" id="cmbSL_type" /> 
                                					<input type="hidden" name="cmbSL_Code" id="cmbSL_Code" />
   												</div>
										</td>
									</tr>
								</table>		
							</td>
						</tr>
						<tr class="table">
							<td>
								<div align="left">Verification Done on <font color="#ff2121">*</font>
								</div>		
							</td>
							<td>
								<div align="left">
								<input type="text" name="txtverificationDate" id="txtverificationDate" maxlength="10" size="11"
									onfocus="javascript:vDateType='3';"
									onkeypress="return calins(event,this);" /> 
								<img src="../../../../../images/calendr3.gif" onclick="showCalendarControl(document.FasAcc_Head_Verification.txtverificationDate);"
									alt="Show Calendar">
								</img>
								</div>		
							</td>
						</tr>
				</table>
				 <table id="mytable" align="center"  cellspacing="3"  cellpadding="2" border="1" width="100%">
          			<tr class="tdH">
          			 	<th>
             			 <!-- 	<input type='checkbox' name='checkall' onclick='checkedAll("FasAcc_Head_Verification");'>Select All / deSelect All --> 
             	    	<a href="javascript:void(0)" onclick="SelectAllCheckBoxes(true)">Select All</a>
             			| <a href="javascript:void(0)" onclick="SelectAllCheckBoxes(false)">deSelect All</a> 
            			</th>
			            <th>
             				Account Head Code
            			</th>
            			<th>
             				Account Head Description
            			</th>
            			<th>
             				Major Group
            			</th>
            			<th>
             				Minor Group
            			</th>
            			<th>
             				Balance Type
            			</th>
			        </tr>
          			<tbody id="tbody" class="table">
		            </tbody>
                 </table>
                 <table align="center"  cellspacing="3" cellpadding="2" border="1" width="100%" >
            		<tr>
                		<td>
                    		 <table align="center" cellspacing="3" cellpadding="2" border="1"
                     width="100%">
                     <tr class="tdH">
                  <td>
                    <table align="center" cellspacing="3" cellpadding="2"
                           border="0" width="100%">
                      <tr>
                        <td width="30%">
                          <div align="left">
                            <div id="divpre" style="display:none"></div>
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
                                                            onchange="changepage()"></select>
                                  </div>
                                </td>
                                <td>
                                  <div id="divpage"></div>
                                </td>
                              </tr>
                            </table>
                          </div>
                        </td>
                        <td width="30%">
                          <div align="right">
                            <div id="divnext" style="display:none"></div>
                          </div>
                        </td>
                      </tr>
                    </table>
                  </td>
                </tr>
                
              </table>
        				</td>
    				</tr>
      				<tr class="tdH">
      					<td>
          					<div align="center">
          					<input type="submit" id="cmdverify" name="cmdverify" value="VERIFY">
         					<input type="button" id="cmdcancel" name="cancel" value="EXIT" onclick="btncancel()">
      						</div>
      					</td>
      				</tr>
		      </table>
</form>    
</body>
</html>
