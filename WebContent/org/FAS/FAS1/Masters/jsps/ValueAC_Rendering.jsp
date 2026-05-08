<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">
<%@ page contentType="text/html;charset=windows-1252"%>
<%@ page import="java.sql.*,java.util.*,Servlets.Security.classes.UserProfile"%>
<%@ page session="false" contentType="text/html;charset=windows-1252"%>
<%@ page import="Servlets.FAS.FAS1.CivilBills.servlets.LoadDriver" %>
<%@ include file="//org/Security/jsps/Check_SessionJSPF.jspf" %>
<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=windows-1252"/>     <meta http-equiv="cache-control" content="no-cache">
    <title>Accounting Unit Master</title>

    <link href="../../../../../css/Sample3.css" rel="stylesheet" media="screen"/>
    <link href="../../../../../css/CalendarControl.css" rel="stylesheet" media="screen"/>

    <script type="text/javascript" src="<%=request.getContextPath()%>/org/Library/scripts/comJS.js"></script>
    <script type="text/javascript" src="../../../../../org/Library/scripts/checkDate.js"></script>
    <script type="text/javascript" src="../scripts/ValueAC_Rendering.js"></script>
    
    <!-- script type="text/javascript" src="../scripts/Asset_Rendering_DateCheck.js"></script-->
    <script type="text/javascript" src="../../../../../org/FAS/FAS1/CalendarCtrl.js"></script>
    
    <script type="text/javascript" src="../scripts/ValueAC_Rendering_ListAll.js"></script>
    
    <!-- script type="text/javascript" src="../../../../Library/scripts/CalendarControl.js"></script-->
    
    <script type="text/javascript"
            src="<%=request.getContextPath()%>/org/FAS/FAS1/CommonControls/scripts/Common_Load_Accounting_Unit_ID.js"></script>
    <script type="text/javascript"
            src="<%=request.getContextPath()%>/org/FAS/FAS1/CommonControls/scripts/Common_Load_Accounting_office.js"></script>
            
    <script type="text/javascript"
            src="<%=request.getContextPath()%>/org/FAS/FAS1/CommonControls/scripts/Load_Accounting_office.js"></script>
    
    
    <script language="javascript" type="text/javascript">
                function closeWindow()
                {                
                    window.open('','_parent','');                
                    window.close(); 
                    window.opener.focus();
                }
    </script>
  </head>
  <%  
      Connection con=null;
      ResultSet rs=null,rs2=null;
      PreparedStatement ps=null,ps2=null;
      ResultSet results=null;    
      Statement statement=null;
      try{
    	  LoadDriver load = new LoadDriver();
    	  con = load.getConnection();    
      }catch(Exception e){
        System.out.println("Exception in connection...."+e);
      }  
      HttpSession session=request.getSession(false);
      UserProfile empProfile=(UserProfile)session.getAttribute("UserProfile");      
     System.out.println("user id::"+empProfile.getEmployeeId());
     int empid=empProfile.getEmployeeId();
     int bankid=0;
     
    //int empid=9315;
    int  oid=0;
    String oname="";
    try{
           
       ps=con.prepareStatement("select OFFICE_ID from HRM_EMP_CURRENT_POSTING where EMPLOYEE_ID=?" );
       ps.setInt(1,empid);
       results=ps.executeQuery();
       if(results.next()){
               oid=results.getInt("OFFICE_ID");
       }
       results.close();
       ps.close();
       ps=con.prepareStatement("select OFFICE_NAME from COM_MST_OFFICES where OFFICE_ID=?" );
       ps.setInt(1,oid);
       results=ps.executeQuery();
       if(results.next()){
               oname=results.getString("OFFICE_NAME");
       }
       results.close();
       ps.close();
     /* */      
       System.out.println("off id.. emp id"+oid+".."+empid);     
    }catch(Exception e){
        System.out.println(e);
    }
   
   %>
  <body class="table" onload="LoadAssetRenderingUnits(<%=oid%>);">
  <form action="../../../../../AccountingUnitServlet.con?command=Add" name="frmAccountUnit" method="post" onsubmit="return nullcheck1()">
  <table cellspacing="2" cellpadding="3" border="1" width="100%" align="center">
       <tr>
            <td colspan="3" class="tdH" align="center"><b>Value Account for Assets maintained for Offices - Maintained by HO</b></td>
                   
       </tr> 
       
          
          <tr class="table">
            <td>
              <div align="left">
                 Asset Rendering Accounting Unit
                <font color="#ff2121">*</font>
              </div>
            </td>
            <td>
              <div align="left">
                <select size="1" name="cmbRenderAccUnit" id="cmbRenderAccUnit"
                        tabindex="2" onchange="refreshButtons();LoadaccUnits(this.value);">
                
                </select>
              </div>
            </td>
          </tr>

       
         <tr class="table">
            <td>
              <div align="left">
                Value Account Maintained for (office)
                <font color="#ff2121">*</font>
              </div>
            </td>
            <td>
              <div align="left">
                 
                <select size="1" name="cmbAcc_UnitCode" id="cmbAcc_UnitCode"
                        tabindex="1" onchange="LoadOffice(this.value);">
                
                </select>
              </div>
            </td>
          </tr>    
          <tr class="table">
            <td>
              <div align="left">
                Value Account maintained for Accounting Unit<font color="#ff2121">*</font>
              </div>
            </td>
            <td>
              <div align="left">
                <select size="1" name="cmbOffice_code" id="cmbOffice_code" tabindex="1">  
                      
                </select>
              </div>
            </td>
          </tr>
          
       <tr >
            <td class="table">Date Effect From:</td>
            <td class="table">
            <input type="text" name="txtDtFrm" id="txtDtFrm" onkeypress="return  calins(event,this)" onblur="return checkdt(this);" onFocus="javascript:vDateType='3'" maxlength=10 size="10">
            <img src="../../../../../images/calendr3.gif" onclick="showCalendarControl(document.getElementById('txtDtFrm'),1);" alt="Show Calendar"
                                 height="24" width="19"></img>
            </td>
       </tr>
       <tr>
            <td class="table">Active:</td>
            <td class="table">
	            <input type="radio" name="radActive" id="radActive" value="Y" onclick="document.getElementById('trDtTo1').style.display='none';document.getElementById('trDtTo2').style.display='none';" checked>Yes &nbsp;
	            <input type="radio" name="radActive" id="radActive" value="N" onclick="document.getElementById('trDtTo1').style.display='block';document.getElementById('trDtTo2').style.display='block';" >No
            </td>
       </tr>
      
       <tr id="trDtTo"  class="table" width="100%">
            <td class="table"><div id="trDtTo1" style="display:none;"> Date Effect Upto:</div></td>
            <td class="table">
            <div id="trDtTo2" style="display:none;">
            <input type="text" name="txtDtTo" id="txtDtTo" onkeypress="return  calins(event,this)" onblur="return checkdt(this);" onFocus="javascript:vDateType='3'" maxlength=10 size="10">
            <img src="../../../../../images/calendr3.gif" onclick="showCalendarControl(document.getElementById('txtDtTo'),1);" alt="Show Calendar"
                     height="24" width="19"></img>
             </div>
            </td>
       </tr>
    </table>
        
    
    <table cellspacing="2" cellpadding="3" border="0" width="50%" align="center">
       <tr style=" width : 869px;">
			<td class="table" style=" width : "15%">
				<input type="button" name="CmdAdd" value="ADD" id="CmdAdd" onclick="callServer('Add','null')"/>
			</td>
	        <td>    
	            <input type="button" name="CmdUpdate" value="UPDATE" id="CmdUpdate" onclick="callServer('Update','null')" disabled/>
	        </td>
	        <td>
	            <input type="button" name="CmdDelete" value="CANCEL" id="CmdDelete" onclick="callServer('Delete','null')" disabled/>
	        </td>
	        <td>
	            <input type="button" name="CmdClear" value="CLEAR" id="CmdClear" onclick="clearAll(<%=oid%>)"/>
	        </td>
	        <td>
	            <input type="button" name="CmdList" value="LIST ALL" id="CmdList" onclick="ListAll()">
	        </td>
	        <td>
	            <input type="button" name="CmdClose" value="EXIT" id="CmdList" onclick="closeWindow()">
	        </td>
       </tr>
        
    </table>
   
    <!--table cellspacing="3" cellpadding="2" border="0" width="100%"  align="center" >
            <tr>
                <td class="tdH" colspan="2"><b>Existing Details</b></td>
            </tr>
    </table>
    <table id="Existing" cellspacing="2" cellpadding="3" border="0" width="100%"
             align="center">
        <tr class="tdH">
          <th>
            Select
          </th>
          <th>
            Office Id
          </th>
          <th>
            Office Name
          </th>
        </tr>
        <tbody id="tblList" name="tblList" class="table">
        </tbody>
        <tr>
            <td colspan="5" align="center"><input type="submit" value="Submit">
            <input type="reset" value="Clear" onclick="clearall()">
    <input type="button" value="Exit" onclick="closeWindow()">
    </td>
    </tr>
    </table-->
  </form>
  </body>
</html>