<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">
<%@ page contentType="text/html;charset=windows-1252"%>
<%@ page import="java.sql.*,java.util.*"%>
<%@ page import="Servlets.Security.classes.UserProfile"%>
<%@ page session="true"%>
<html>
  <head>
    
    <meta http-equiv="Content-Type" content="text/html; charset=windows-1252"/>
    <title>Request for New Office Creation</title>
      <script type="text/javascript"   src="../../../../Library/scripts/controllingOffice.js"></script>
      <script type="text/javascript" src="../scripts/RequestNewCreationOfficeAddLoad.js"></script>
      <script type="text/javascript" src="../../../../Library/scripts/checkDate.js"></script>  
      <script type="text/javascript" src="../scripts/EditReqForNewOfficeCreationValids.js"></script> 
     <!-- contains Number,E-Mail validations
    <script type="text/javascript" src="../../../../Library/scripts/NumericEMailValidations.js"></script>
    <!-- contains officeSelection 
    <script type="text/javascript" src="../../../../Library/scripts/selectOffice1.js"></script>
    <!-- contains onBlur functionality
    <script type="text/javascript" src="../scripts/OfficeAddressLoad.js"></script>
    <script type="text/javascript" src="../scripts/LoadInitialGridVals.js"></script> -->
 <!--   <script type="text/javascript" src="../scripts/selectAttachedOffice.js"></script>
    
     <script type="text/javascript" src="../scripts/ClosureOfOfficeValids.js"></script>-->
    
     
    <link href="../../../../../css/testing.css" rel="stylesheet" media="screen"/>
    <link href="../../../../../css/yellow.css" rel="stylesheet" media="screen"/>
    <link href="../../../css/Sample3.css" rel="stylesheet" media="screen"/>
  <!--  <script type="text/javascript" src="../scripts/UpadeOldRecAjax.js" ></script>-->
    
    
    <script language="javascript" type="text/javascript">
             
               function loadIntoTextBox(id)
               {
              // alert(id);
              // document.frmOffice.txt_Request_Id.value=id;
               document.frmOffice.txt_Request_Id1.value=id;
               //document.frmOffice.cmbRequestId.value=0;
               loadGridInitValsForReq(id);
               }
               function getValsFromSession()
                {
                    document.frmOffice.txtOffice_Id.disabled=false;
                    document.frmOffice.txtOffice_Id.focus();
                    
                }
               
                function closeWindow()
                {                
                    window.open('','_parent','');                
                    window.close(); 
                    window.opener.focus();
                }
    </script> 
  </head>
<!--  <body onkeyup="testKeyListener(event)" onload="divposition()" class="bgbody" onclick="popupdisable()"> -->
<body class="bgbody" onload="getValsFromSession()">

<%
  Connection connection=null;
   Statement statement=null;
   ResultSet results=null;   

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
              //System.out.println("Exception in creating statement:"+e);
       }          
  }
  catch(Exception e)
  {
         //System.out.println("Exception in openeing connection:"+e);
  }
  
  %>


<form name="frmOffice" method="POST" action="../../../../../ServletEditReqForNewOfficeCreation.con" >
<table width=100% cellpadding="3" cellspacing="1" border="1" align="center">
 <tr >
    <td class="bgClass">
        <center><h3>Edit New Office Creation Request</h3></center>
    </td>
 </tr>
 <tr>
    <td>                        
        <table  cellspacing="3" cellpadding="1"  width="101%" >
             <tr>
                <td width="28%">
                   <font color="#808080"> Existing Office Id</font>
                </td>
                <td width="72%">
                
                 <%
                System.out.println("hai");
                UserProfile empProfile=(UserProfile)session.getAttribute("UserProfile");
                System.out.println("emp"+empProfile);
                int Emp_Id=empProfile.getEmployeeId();
                String Level=empProfile.getOfficeLevel();
                
                System.out.println("the emp id is---->>   "+Emp_Id);
                System.out.println("the Level is---->>   "+Level);
                int Office_Id=0;
                 try
                   {
                        results=statement.executeQuery("select OFFICE_ID from HRM_EMP_CURRENT_POSTING where EMPLOYEE_ID="+Emp_Id); 
                        if(results.next()) 
                        {
                           Office_Id=results.getInt("OFFICE_ID");
                        System.out.println("the office id is---->>   "+Office_Id);
                        }
                        results.close();
                   }
                catch(Exception e)
                {
                        System.out.println("exception occured : " + e);
                }    
                %>
               
                <input type="text" name="txtOffice_Id" id="txtOffice_Id" onfocus="loadOffice(document.frmOffice.txtOffice_Id.value,'nothing')" value=<%=Office_Id%> >
                <input type="hidden"  name="txtOffice_Id1" id="txtOffice_Id1" value=<%=Office_Id%> >
                </td>
             </tr>
             <tr>
                <td width="28%">
                    <font color="#808080">Name of the Exiting Office</font>
                </td>
                <td width="72%">
                    <input type="text" name="txt_ExtOffice_Name" id="txt_ExtOffice_Name" disabled>
                </td>
             </tr>
             <tr>
                <td width="28%">
                    <font color="#808080">Office Address1</font>
                </td>
                <td width="72%">
                    <input type="text" name="txt_ExtOffice_Address1" id="txt_ExtOffice_Address1" disabled>
                </td>
             </tr>
             <tr>
                <td width="28%">
                    <font color="#808080">Office Address2</font>
                </td>
                <td width="72%">
                    <input type="text" name="txt_ExtOffice_Address2" id="txt_ExtOffice_Address2" disabled>
                </td>
             </tr>
             <tr>
                <td width="28%">
                    <font color="#808080">City/Town</font>
                </td>
                <td width="72%">
                    <input type="text" name="txt_ExtOffice_City" id="txt_ExtOffice_City" disabled>
                </td>
            </tr>
            <tr>
                <td width="28%" height="31">
                    <font color="#808080">District</font>
                </td>
                <td width="72%" height="31">
                    <select name="cmb_ExtDistrict" id="cmb_ExtDistrict" disabled>
                    <option>----Select District----</option>
                     <%
                                                        try
                                                        {
                                                          results=statement.executeQuery("select * from COM_MST_DISTRICTS"); 
                                                          while(results.next()) 
                                                          {
                                                              out.print("<option value='" + results.getInt("District_Code") + "'>" + results.getString("District_Name") + "</option>");                      
                                                          }
                                                          results.close();
                                                        }
                                                        catch(Exception e)
                                                        {
                                                            System.out.println("exception occured : " + e);
                                                        }      
                                                  %>
                    </select>
                </td>
            </tr>
             <tr>
                    <td colspan="2" class="bgClass" height="21">
                     Proposed Office Details
                    </td>
            </tr>
            <tr>
                <td width="28%">
                    <font color="#808080">Request Id</font>
                </td>
                
                
                
                
                <td width="72%"> 
               
           <!--     <input type="text" name="txt_Request_Id" id="txt_Request_Id" onblur="loadGridInitValsForReq(this.value)"  > -->
                <input type="hidden" name="txt_Request_Id1" id="txt_Request_Id1" > 
                 <SELECT size=1 name="cmbRequestId" onchange="loadIntoTextBox(this.value)">   
                                            <option value="0">
                                                       ----Select Request ID----
                                                  </option>
                                            <%
                                                  try
                                                  {
                                                    results=statement.executeQuery("select NEW_OFFICE_REQUEST_ID from com_office_new_requests where REQUESTING_OFFICE_ID="+Office_Id); 
                                                    while(results.next()) 
                                                    {
                                                        out.print("<option value='" + results.getInt("NEW_OFFICE_REQUEST_ID") + "'>" + results.getInt("NEW_OFFICE_REQUEST_ID") + "</option>");                      
                                                    }
                                                    results.close();
                                                  }
                                                  catch(Exception e)
                                                  {                        
                                                  }      
                                            %>
                                          </SELECT>
                
                </td>
                
             </tr>
            
             <tr>
                <td width="28%">
                    Name of the Proposed Office<label style="color:rgb(255,0,0);">*</label>
                </td>
                <td width="72%">
                    <input type="text" name="txt_Prop_Office_Name" id="txt_Prop_Office_Name"  >
                </td>
             </tr>
             <tr>
                <td width="28%">
                      Short Name of the Proposed Office<label style="color:rgb(255,0,0);">*</label>
                </td>
                <td width="72%">
                    <input type=text name="txt_Prop_Short_Name" id="txt_Prop_Short_Name" >
                </td>
             </tr>
             <tr>
                <td width="28%">
                  Office Level
                  <label style="color:rgb(255,0,0);">*</label>
                </td>
                <td>
                                        <SELECT size=1 name="cmbLevelId" >   
                                            <option value="0">
                                                       ----Select OfficeLevel----
                                                  </option>
                                            <%
                                                 int level_id=0;
                                                 int level_hierarchy=0;
                                                 try
                                                 {
                                                   System.out.println("select HIERARCHICAL_SEQUENCE from COM_MST_OFFICE_LEVELS where OFFICE_LEVEL_NAME='"+Level+"'");
                                                   results=statement.executeQuery("select HIERARCHICAL_SEQUENCE from COM_MST_OFFICE_LEVELS where OFFICE_LEVEL_NAME='"+Level+"'"); 
                                                   if(results.next()) 
                                                    {
                                                        level_id=results.getInt("HIERARCHICAL_SEQUENCE"); 
                                                       System.out.println("level id value is------>  "+level_id);
                                                    }
                                                    results.close();
                                                 level_hierarchy=level_id/10;
                                                 System.out.println("level hierarchy id-------> ....."+level_hierarchy);
                                                 }
                                                 catch(Exception e)
                                                 {
                                                 }
                                                 try
                                                  {
                                                    System.out.println(" the NEW querry is..............**      "+"select * from com_mst_office_levels where HIERARCHICAL_SEQUENCE>"+level_id+" and HIERARCHICAL_SEQUENCE not like '"+level_hierarchy+"%'"); 
                                                    results=statement.executeQuery("select * from com_mst_office_levels where HIERARCHICAL_SEQUENCE>"+level_id+" and HIERARCHICAL_SEQUENCE not like '"+level_hierarchy+"%'"); 
                                                    while(results.next()) 
                                                    {
                                                        out.print("<option value='" + results.getString("Office_Level_Id") + "'>" + results.getString("Office_Level_Name") + "</option>");                      
                                                    }
                                                    results.close();
                                                  }
                                                  catch(Exception e)
                                                  {                        
                                                  }      
                                            %>
                                          </SELECT>
                  <font color="#808080">(For Offices Other than HO &amp; Regional)</font>
                </td>
             </tr>
            
             <tr>
                <td width="28%">Primary Nature of Work<label style="color:rgb(255,0,0);">*</label></td>
                <td width="72%">
                 <select name="cmbPrimaryID">                                        
                                            <option value="0">
                                                       ----Select Work Nature----
                                                  </option>
                                            <%
                                                  try
                                                  {
                                                    results=statement.executeQuery("select * from COM_MST_WORK_NATURE"); 
                                                    while(results.next()) 
                                                    {
                                                        out.print("<option value='" + results.getString("Work_Nature_Id") + "'>" + results.getString("Work_Nature_Desc") + "</option>");                      
                                                    }
                                                    results.close();
                                                  }
                                                  catch(Exception e)
                                                  {}      
                                            %>                 
                                        </select>
                </td>
             </tr>
             <tr>
                <td width="28%">Remarks</td>
                <td width="72%">
                 <textarea rows="4" name="txt_Remark" id="txt_Remark" cols="38" ></textarea>
                </td>
             </tr>
             <tr>
                <td width="23%">
                    Date of Request<label style="color:rgb(255,0,0);">*</label>
                </td>
                <td width="77%">
                    <input type=text name="txt_Date" id="txt_Date" onFocus="javascript:vDateType='3'" onKeyUp="DateFormat(this,this.value,event,false,'3')" onBlur="DateFormat(this,this.value,event,true,'3')">
                </td>
             </tr>
             
             
             <tr>
                    <td colspan="2" class="bgClass" height="21">
                     Details Additional Nature of Work
                    </td>
            </tr>
        <!--    
            <tr>
                <td width="28%" > <font color="#808080">SI.No</font></td>
                <td width="72%">
                 <input type="text" name="SL_NO" id="SL_NO">
                </td>
             </tr>
         -->    
             <tr>
                <td width="28%">Additional Nature of Work<label style="color:rgb(255,0,0);"/></td>
                <td width="72%">
                 <select name="cmbSecondaryID">                                        
                                            <option value="0">
                                                       ----Select Work Nature----
                                                  </option>
                                            <%
                                                  try
                                                  {
                                                    results=statement.executeQuery("select * from COM_MST_WORK_NATURE"); 
                                                    while(results.next()) 
                                                    {
                                                        out.print("<option value='" + results.getString("Work_Nature_Id") + "'>" + results.getString("Work_Nature_Desc") + "</option>");                      
                                                    }
                                                    results.close();
                                                  }
                                                  catch(Exception e)
                                                  {}      
                                            %>                 
                                        </select>
                </td>
             </tr>
             <tr>
                <td width="28%">Remarks</td>
                <td width="72%">
                 <textarea rows="4" name="txt_Remark_Second" id="txt_Remark_Second" cols="38" ></textarea>
                </td>
             </tr>
            
            
            
             <tr>
                <td colspan=2 class="bgclass">
                                          <input type="Button" value="  Add " id="Add" onclick="callServer1('Add','null')" name="cmdAdd" > 
                                          <input type="Button" value=" Update" onclick="callServer1('Update','null')" disabled name="cmdUpdate">
                                          <input type="Button" value=" Delete" id="Revoke" onclick="callServer1('Delete','null')" disabled name="cmdDelete">
                                          <input type="Button" value="Clear All" onclick="clearAllWing()" name="cmdClearAll">                                 
                                      </td>
            </tr>
            <tr>
                <td colspan=2>
                </td>
            </tr>
        </table>
         <table name="Existing" id="Existing"  border="1" width="100%"  style="font-family:arial;">
                                    <tr>
                                            <td colspan=7 class="bgClass">
                                                Existing Details
                                            </td>
                                    </tr>
                                    
                                    <tr>
                                        <th>
                  View
                </th>
                                     <th>SL No</th>
                                        <th>
                                    Additional Nature of Work
                                </th>
                                        
                                        <th>
                                    Remarks
                                </th>
                                 
                                </tr>
                                   
                                    <tbody id="tblList" name="tblList">
                                    </tbody>
                                    <tr>
                                        <td colspan=7 class="bgClass"><center>
                                            <input type=Submit value=Submit onclick="return nullcheck1()"/>
                                            <input type=button value=Cancel onclick="closeWindow()"/></center>
                                        </td>
                                    </tr>
                                  </table>
            
</table>
</form>
</body>
</html>