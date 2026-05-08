<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">
<%@ page contentType="text/html;charset=windows-1252"%>
<%@ page import="java.sql.*,java.util.*"%>
<%@ page session="false" contentType="text/html;charset=windows-1252"%>

<%@ page import="java.sql.*,java.util.*,Servlets.Security.classes.UserProfile"%>

<%@ include file="//org/Security/jsps/Check_SessionJSPF.jspf" %>
<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=windows-1252"/>     
    <meta http-equiv="cache-control" content="no-cache">
    <title>Asset Value AC Details</title>

<!------------------------------------- COMMON SCRIPTS ------------------------------------->
    
    <script type="text/javascript" src="<%=request.getContextPath()%>/org/Library/scripts/comJS.js"></script>
    <script type="text/javascript" src="../../../../../org/Library/scripts/checkDate.js"></script>
	<script type="text/javascript" src="../../../../../org/FAS/FAS1/CalendarCtrl.js"></script>

	<!-- script type="text/javascript" src="../../AccountHeadDirectory/scripts/Acc_Head_Directory_Sys.js"></script -->

    <link href="../../../../../css/Sample3.css" rel="stylesheet" media="screen"/>
	<link href="../../../../../css/CalendarControl.css" rel="stylesheet" media="screen"/>


    <script type="text/javascript"
            src="<%=request.getContextPath()%>/org/FAS/FAS1/CommonControls/scripts/Common_Load_Accounting_Unit_ID.js"></script>
    <script type="text/javascript"
            src="<%=request.getContextPath()%>/org/FAS/FAS1/CommonControls/scripts/Common_Load_Accounting_office.js"></script>
            
    <script type="text/javascript"
            src="<%=request.getContextPath()%>/org/FAS/FAS1/CommonControls/scripts/Load_Accounting_office.js"></script>
<!-------------------------------------------- end -------------------------------------------->


    <script type="text/javascript" src="../scripts/Asset_Value_AC_Details.js"></script>

    <script language="javascript" type="text/javascript">
                function closeWindow()
                {                
                    window.open('','_parent','');                
                    window.close(); 
                    window.opener.focus();
                }
                
    </script>


  </head>
  <body onload="LoadAccountingUnitID('BOTH_UNITS_AND_OFFICES'); setTimeout('LoadNAOffice(document.frmAssetValueACDetails.cmbAcc_UnitCode.value)', 2000);" class="table">
  <%
  Connection connection=null;
  Statement statement=null;
  PreparedStatement ps = null;
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
    }
    catch(SQLException e)
    {
    	
    }
  }
  catch(Exception e)
  {
  }
  %>
  
  <% 
		HttpSession session=request.getSession(false);
		UserProfile empProfile=(UserProfile)session.getAttribute("UserProfile");
		  
		System.out.println("user id::"+empProfile.getEmployeeId());
		int empid=empProfile.getEmployeeId();
		//int empid=9315;
		int  oid=0,sid=0;
		String oname="",sname="";
	   
		try
		{
	           
			ps=connection.prepareStatement("select OFFICE_ID from HRM_EMP_CURRENT_POSTING where EMPLOYEE_ID=?" );
			ps.setInt(1,empid);
			results=ps.executeQuery();
			     if(results.next()) 
			     {
			        oid=results.getInt("OFFICE_ID");
			     }
			results.close();
			ps.close();
			ps=connection.prepareStatement("select OFFICE_NAME from COM_MST_OFFICES where OFFICE_ID=?" );
			ps.setInt(1,oid);
			results=ps.executeQuery();
			     if(results.next()) 
			     {
			        oname=results.getString("OFFICE_NAME");
			      }
			results.close();
			ps.close();
	    }
	    catch(Exception e)
	    {
	        System.out.println(e);
	    }
	   	try
     	{
			ps=connection.prepareStatement("select a.SECTION_ID,b.SECTION_NAME from FAS_SECTION_GROUP_DETAILS a,COM_MST_OFFICE_SECTIONS b,"+
			                        "HRM_EMP_CURRENT_POSTING c where a.SECTION_ID=b.SECTION_ID and b.SECTION_ID=c.SECTION_ID " + 
			                        "and a.SECTION_ID=c.SECTION_ID and c.employee_id =? and a.office_id=?");
            ps.setInt(1,empid);
            ps.setInt(2,oid);
            results=ps.executeQuery();
          	if(results.next()) 
            {
               sid=results.getInt("SECTION_ID");
               sname=results.getString("SECTION_NAME");
            }
			results.close();
			ps.close();  
	   	}
	   catch(Exception e)
	    {
	        System.out.println(e);
	    }
	   
   %>
  
  <form action="" name="frmAssetValueACDetails">
 
  <table cellspacing="2" cellpadding="3" border="1" width="100%"
             align="center">
       <tr>
            <td colspan="3" class="tdH" align="center"><b>Details of Assets Maintained at Value A/c Rendering Unit </b></td>
       </tr> 

        <tr>
          <td class="table">Accounting Unit</td>
          <td class="table">
          		<select size="1" name="cmbAcc_UnitCode" id="cmbAcc_UnitCode"
                        tabindex="1" onchange="LoadOffice(this.value);LoadNAOffice(this.value);">
                	<%
/*	        			try
		                {
			                ps=connection.prepareStatement("SELECT a.acct_unit_id_rendered_for as acc_unit_id, " +
									                		  "b.accounting_unit_name as acc_unit_name " +
									                		  "FROM fas_asset_val_ac_render_units a " + 
									                		  "JOIN fas_mst_acct_units b " +
									                		  "ON a.acct_unit_id_rendered_for = b.accounting_unit_id " +
									                		  "ORDER BY a.acct_unit_id_rendered_for");
			                results=ps.executeQuery();
			                while(results.next())
			                {
			                	out.println("<option value = "+results.getInt("acc_unit_id")+">"+
			                			results.getString("acc_unit_name")+"</option>");
			                }
		         		}catch(Exception e)
		            	{
		         			System.out.println("Exception in Select:"+e);
		    	        }
*/	                %>
                </select>
           </td>
        </tr>

        <tr>
            <td class="table">Accounting Unit Office</td>
          	<td class="table">
                <select size="1" name="cmbOffice_code" id="cmbOffice_code" tabindex="2">
                </select>
          	</td>
        </tr>

 <tr>
	                                    <td class="table">Financial Year	</td>
		   <td class="table">
	            <select name="cmbFinYear" id="cmbFinYear" onchange="callServer('Get');">
	            <option value="2013-2014">2013-2014</option>
	          
	            
	          		<%
	          		
	    			//try
	               // {
		              //  ps=connection.prepareStatement("select FINANCIAL_YEAR from CASH_BOOK_CONTROL");
		               // results=ps.executeQuery();
		            //    while(results.next())
		              //  {
		                //	out.println("<option value = "+results.getString("FINANCIAL_YEAR")+">"+
		                		//	results.getString("FINANCIAL_YEAR")+"</option>");
		              //  }
	         		//}catch(Exception e)
	            	//{
	         			//System.out.println("Exception in Select:"+e);
	    	     //   }
	
	          		%>
	          		
	          		</select> 
	        </td>
        </tr>
        <tr>
            <td class="table">Date</td>
       
            <td class="table">
	            <input type="text" name="txtDate" id="txtDate"
	            	value="<%
				        		try
				                {
					                ps=connection.prepareStatement("select to_char(now(),'dd/mm/yyyy') as dateTran from dual");
					                results=ps.executeQuery();
					                while(results.next())
					                {
					                	out.println(results.getString("dateTran"));
					                }
				         		}catch(Exception e)
				            	{
				         			System.out.println("Exception fetching System Date ==> "+e);
				    	        }
	            			%>"
	            	onkeypress="return numInt(event,this)" onblur="return checkdt(this);" onFocus="javascript:vDateType='3'" maxlength=10 size="10">
	            <img src="../../../../../images/calendr3.gif" onclick="showCalendarControl(document.getElementById('txtDate'),1);" alt="Show Calendar"
	                                 height="24" width="19"></img>
	                                 </td></tr>
	                                 
 
        <tr >
            <td class="table"> <div id="div_assetCde" style="display: none;">Asset Code</div></td>
            <td class="table"><div id="div_assetCde1" style="display: none;">
	            <input maxlength=7 size=7 type="text" name="txtAssetCode" id="txtAssetCode" disabled onkeypress="return numInt(event,this)"/> (System generated)
	        </div></td>
        </tr>
        <tr>
          <td class="table">Office where NA is maintained for this Asset</td>
          <td class="table">
            <select name="cmbNAOffice" id="cmbNAOffice">
            </select>
          </td>
           
        </tr>
                <tr style="display: none;">
            <td class="table">Asset Type</td>
          <td class="table">
            <select name="cmbAssetType" id="cmbAssetType" onchange="listMajorClass();">
            <option value="G">General</option>
            
          		<!--<%
    			try
                {
	                ps=connection.prepareStatement("SELECT asset_type_code, asset_type_desc FROM com_mst_assets_type");
	                results=ps.executeQuery();
	                while(results.next())
	                {
	                	out.println("<option value = "+results.getString("asset_type_code")+
	                    			">"+results.getString("asset_type_desc")+"</option>");
	                }
         		}catch(Exception e)
            	{
         			System.out.println("Exception in Select:"+e);
    	        }
         		%>
            --></select>
          </td>
           
        </tr>
        <tr>
            <td class="table">MajorClassification
         </td>
            <td class="table">
            <select name="cmbMajorClass" id="cmbMajorClass" onchange="fetchAlias();minorCde_load();">
            </select>
            </td>
            </tr>
       <tr>
            <td class="table">Minor MajorClassification</td>
         <td class="table">
            <select name="cmbMinorClass" id="cmbMinorClass" >
          	
            </select>
          </td>
           
        </tr>
                <tr>
            <td class="table"> Category</td>
<td class="table">   Depreciation    
            <select name="cmbDepreCat" id="cmbDepreCat" style="width: 40mm" onchange="ifDepre(this.value);">
            	<option value="0">--Select Category--</option>
          		<%
    			try
                {
	                ps=connection.prepareStatement("SELECT depreciation_cate_code, " +
							                		 "depreciation_category " +
							                		 "FROM fas_depre_category_mst");
	                results=ps.executeQuery();
	                while(results.next())
	                {
	                	out.println("<option value = "+results.getInt("depreciation_cate_code")+
	                    			">"+results.getString("depreciation_category")+"</option>");
	                }
         		}catch(Exception e)
            	{
         			System.out.println("Exception in Select:"+e);
    	        }
         		%>
            </select>
        Apportionment of Grant
            <select name="cmbApportCat" id="cmbApportCat" style="width: 40mm" onchange="ifApport(this.value);">
            <option value="0">--Select Category--</option>
          		<%
    			try
                {
	                ps=connection.prepareStatement("SELECT apportion_grant_cate_code, " +
							                	   "  apportion_grant_category " +
							                	   "FROM fas_apport_category_mst");
	                results=ps.executeQuery();
	                while(results.next())
	                {
	                	out.println("<option value = "+results.getInt("apportion_grant_cate_code")+
	                    			">"+results.getString("apportion_grant_category")+"</option>");
	                }
         		}catch(Exception e)
            	{
         			System.out.println("Exception in Select:"+e);
    	        }
         		%>
            </select>
          </td>
           
        </tr>
        
        <tr>
            <td class="table">Alias Code</td>
            <td class="table">
	            <input maxlength=15 size=15 style="height: 15px;"  type="text" name="txtAlias" id="txtAlias"/></td>
        </tr>

                
        <tr>
            <td class="table">Description of the Asset</td>
            <td class="table">
	        	<textarea cols=70 style="height: 35px;" name="txtAssetDesc" id="txtAssetDesc"></textarea>    
	       
         &nbsp;&nbsp; &nbsp; Number of Assets
	            <input maxlength=7 size=7 style="height: 15px;"  type="text" name="txtNoOfAssets" id="txtNoOfAssets" onkeypress="return numFloatInt(event,this)"/></td>
        </tr>
        
        <tr>
            <td class="table">Location in which the Asset is available</td>
	        <td>
                  <div align="left">
                    <input type="text" name="txtOffice_Id" id="txtOffice_Id"
                           disabled="disabled"
                           onkeypress="return numbersonly1(event,this);"
                           maxlength="4" size="5"/>
                     
                    <img src="../../../../../images/c-lovi.gif" width="20"
                         height="20" alt="OfficeList" onclick="jobpopup();"></img>
                     
                    <input type="text" name="txtOfficeName" id="txtOfficeName" style="font-weight:bold"
                           maxlength="60" size="60" readonly="readonly"
                           class="disab"/>
                  </div>
            </td>
        </tr>

        <tr>
          <td class="table">Type Of Ownership</td>
             <td class="table">
          		<select id="cmbApportGrantCat" name="cmbApportGrantCat" onchange="display();">
	            	<option value="Hired">Hired</option>
	          		<option value="Donated">Donated</option>
	          		<option value="Grant">Grant</option>
          		</select>
        <font size="1" >(Hired/Donated/Grant)</font>
       Donating Agency (if Donated)
	            <input maxlength=30 size=30 type="text" name="txtAgency" id="txtAgency" disabled/></td>
        </tr>
        
        <tr>
            <td class="table">Project (if purchased from Grant)</td>
	        <td class="table">
	            <select name="cmbProjectCode" id="cmbProjectCode" disabled>
	            	<option value="0">--Select Project--</option>
	          		<%
	    			try
	                {
		                ps=connection.prepareStatement("SELECT project_id, " +
								                		 "project_name " +
								                		 "FROM pms_mst_projects_view where office_id="+oid);
		                results=ps.executeQuery();
		                while(results.next())
		                {
		                	out.println("<option value = "+results.getInt("project_id")+
		                    			">"+results.getString("project_name")+"</option>");
		                }
	         		}catch(Exception e)
	            	{
	         			System.out.println("Exception in Select:"+e);
	    	        }
	         		%>
	            </select>
          </td>
	    </tr>

        <tr>
            <td class="table">Year & Month of Purchase</td>
	     <td class="table">       <input maxlength=4 size=7 type="text" name="txtPurchaseYear" id="txtPurchaseYear" onkeypress="return numInt(event,this);"/>
      
          
	            <select name="cmbPurchaseMonth" id="cmbPurchaseMonth" >
	            	<option value="1">Jan</option>
	            	<option value="2">Feb</option>
	            	<option value="3">Mar</option>
	            	<option value="4">Apr</option>
	            	<option value="5">May</option>
	            	<option value="6">Jun</option>
	            	<option value="7">Jul</option>
	            	<option value="8">Aug</option>
	            	<option value="9">Sep</option>
	            	<option value="10">Oct</option>
	            	<option value="11">Nov</option>
	            	<option value="12">Dec</option>
	            </select> &nbsp;&nbsp;
	      Original Cost
	            <input maxlength=7 size=7 type="text" name="txtOriginalCost" id="txtOriginalCost" onkeypress="return numFloatInt(event,this)"/>
       </td>
       </tr>
        <tr>
            <td class="table">Under Warranty ? </td>
            <td class="table">
            	<input type="radio" name="radWarranty" id="radWarranty" value="Y" onclick="enableWarrantyDates();" />Yes
            	<input type="radio" name="radWarranty" id="radWarranty" value="N" onclick="disableWarrantyDates();" checked/>No
&nbsp;&nbsp;<font color="red">If Yes , Warranty Period</font>
	            From 
	            <input type="text" name="txtWarrantyFrom" id="txtWarrantyFrom" onkeypress="return numInt(event,this)" onblur="return checkdt(this);" onFocus="javascript:vDateType='3'" maxlength=10 size="8">
	            <img id="imgCal1" src="../../../../../images/calendr3.gif" onclick="showCalendarControl(document.getElementById('txtWarrantyFrom'),1);" alt="Show Calendar"
	                                 height="24" width="19"></img>
			    To
			   	<input type="text" name="txtWarrantyTo" id="txtWarrantyTo" onkeypress="return numInt(event,this)" onblur="return checkdt(this);" onFocus="javascript:vDateType='3'" maxlength=10 size="8">
	            <img id="imgCal2" src="../../../../../images/calendr3.gif" onclick="showCalendarControl(document.getElementById('txtWarrantyTo'),1);" alt="Show Calendar"
	                                 height="24" width="19"></img>
			</td>
        </tr>

        <tr>
            <td class="table">Current Value of Asset</td>
            <td class="table">After Depreciation
	            <input maxlength=7 size=7 type="text" name="txtAssetAfterDepre" id="txtAssetAfterDepre" onkeypress="return numFloatInt(event,this)"/>
   &nbsp;
         After Apportionment of grant
	            <input maxlength=7 size=7 type="text" name="txtAssetAfterApport" id="txtAssetAfterApport" onkeypress="return numFloatInt(event,this)"/></td>
        </tr>
        
        <tr>
            <td class="table">Usable condition </td>
            <td class="table"><font color="red">Is Under usable condition ?</font><input type="radio" name="radUsable" id="radUsable" value="Y" checked/>Yes
            	<input type="radio" name="radUsable" id="radUsable" value="N"/>No
	      
  &nbsp;    &nbsp;<font color="red"> Is in use ?</font>
            	<input type="radio" name="radInUse" id="radInUse" value="Y" checked/>Yes
            	<input type="radio" name="radInUse" id="radInUse" value="N"/>No
	        </td>
        </tr>
        
        <tr>
            <td class="table">Remarks</td>
            <td class="table">
	            <textarea cols=70 style="height: 35px;" name="txtRemarks" id="txtRemarks"></textarea>
	        </td>
        </tr>
        
        <tr align="center">
          <td colspan="3" class="tdH">
            <input type="button" name="CmdAdd" value="ADD" id="CmdAdd" onclick="callServer('Add')"/>
            <input type="button" name="CmdUpdate" value="UPDATE"
                   id="CmdUpdate" onclick="callServer('Update')" disabled/>
            <input type="button" name="CmdDelete" value="DELETE"
                   id="CmdDelete" onclick="callServer('Delete')" disabled/>
            <input type="button" name="CmdList" value="LIST"
                   id="CmdList" onclick="ListAll()"/>
            <input type="reset" name="CmdClear" value="CLEAR ALL"
                   id="CmdClear" onclick="clearAll();"/>
            <input type="button" name="CmdExit" value="EXIT" onclick="closeWindow()">
          </td>
        </tr>
    </table>

  
  </form>
  </body>
</html>