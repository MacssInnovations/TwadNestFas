<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">
<%@ page contentType="text/html;charset=windows-1252"%>
<%@ page session="false" contentType="text/html;charset=windows-1252"%>
<%@ page import="java.sql.*,java.util.*"%>
<%@ include file="//org/Security/jsps/Check_SessionJSPF.jspf"%>
<%@ page import="Servlets.Security.classes.UserProfile"%>
<%@ include file="//org/Security/jsps/IntialLoad.jsp"%>

<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=windows-1252"/>    
   <META HTTP-EQUIV="CACHE-CONTROL" CONTENT=" no-store, no-cache, must-revalidate" >
   <META HTTP-EQUIV="CACHE-CONTROL" CONTENT=" pre-check=0, post-check=0, max-age=0" >
    <title>Firms Master</title>
    <script type="text/javascript" src="../../../../Library/scripts/checkDate.js"></script>
    <script type="text/javascript" src="../scripts/FirmsJS.js"></script>
    <script type="text/javascript" src="<%=request.getContextPath()%>/org/Library/scripts/comJS.js"></script>
    
    <link href="../../../../../css/Sample3.css" rel="stylesheet" media="screen"/>
    <link href="../../../../../css/CalendarControl.css" rel="stylesheet" media="screen"/> 
    <script type="text/javascript" src="../../../../../org/Library/scripts/CalendarControl.js"></script>

  </head>
  <body  bgcolor="rgb(255,255,225)">
  <form name="firmsForm" id="firmsForm">
  <%
response.setHeader("Cache-Control","no-cache"); //HTTP 1.1
response.setHeader("Pragma","no-cache"); //HTTP 1.0
response.setDateHeader ("Expires", 0); //prevent caching at the proxy server
%>
  
    <table cellspacing="2" cellpadding="3" border="1" width="100%">
      <tr class="tdH">
        <td colspan="2">
          <div align="center">
                <strong>Firms Master Maintenance System</strong> 
          </div>
        </td>
      </tr>
      
      
      
      
   
  
      <tr align="left" class="table">
        <td width="53%">Accounting Unit Code 
            <font color="#ff2121">
              *
            </font>
          </td>
        <td width="47%">
        <select size="1" name="cmbAcc_UnitCode" id="cmbAcc_UnitCode" tabindex="1">
<%
                      int unitid=0;
                      String unitname="";
                      try{
                        if(oid==5000)
                        {
                             //out.println("<option value="+0+">"+"-- Select Account Unit --"+"</option>");
                            //ps=con.prepareStatement("select ACCOUNTING_UNIT_ID,ACCOUNTING_UNIT_NAME from FAS_MST_ACCT_UNITS where ACCOUNTING_UNIT_OFFICE_ID=?");
                            String getWing="select ACCOUNTING_UNIT_ID,ACCOUNTING_UNIT_NAME,OFFICE_WING_SINO from FAS_MST_ACCT_UNITS where ACCOUNTING_UNIT_OFFICE_ID=? and OFFICE_WING_SINO=(select OFFICE_WING_SINO from hrm_emp_current_wing where employee_id=? and office_id=?)";
                            ps=con.prepareStatement(getWing);
                            ps.setInt(1,oid);
                            ps.setInt(2,empid);
                            ps.setInt(3,oid);
                            rs=ps.executeQuery();
                          
                              if(rs.next())
                              {
                              out.println("<option value="+rs.getInt("ACCOUNTING_UNIT_ID")+">"+rs.getString("ACCOUNTING_UNIT_NAME")+"</option>");
                              unitid=rs.getInt("ACCOUNTING_UNIT_ID");
                              
                              System.out.println(".."+rs.getInt("ACCOUNTING_UNIT_ID"));
                              System.out.println(".."+rs.getString("ACCOUNTING_UNIT_NAME"));
                              System.out.println(".."+rs.getInt("OFFICE_WING_SINO"));
                              
                              }
                          System.out.println(oid+" "+oname);
                          ps.close();
                          rs.close();
                          }
                              else
                              {
                                ps=con.prepareStatement("select ACCOUNTING_UNIT_ID,ACCOUNTING_UNIT_NAME from FAS_MST_ACCT_UNITS where ACCOUNTING_UNIT_ID=(select ACCOUNTING_UNIT_ID from FAS_MST_ACCT_UNIT_OFFICES where ACCOUNTING_FOR_OFFICE_ID=?)");
                                ps.setInt(1,oid);
                                rs=ps.executeQuery();
                                  if(rs.next())
                                  {
                                  System.out.println(rs.getInt("ACCOUNTING_UNIT_ID"));
                                  System.out.println(rs.getString("ACCOUNTING_UNIT_NAME"));
                                  //out.println("<option value="+0+">"+"-- Select Account Unit --"+"</option>");
                                  out.println("<option value="+rs.getInt("ACCOUNTING_UNIT_ID")+" >"+rs.getString("ACCOUNTING_UNIT_NAME")+"</option>");
                                  unitid=rs.getInt("ACCOUNTING_UNIT_ID");
                                  }
                                  ps.close();
                                  rs.close();
                              }
                          }
                      catch(Exception e)
                        {
                            System.out.println(e);
                        }
                      %>
                        </select>
        </td>
      </tr>
      <tr align="left" class="table">
        <td width="53%">Office for which Firm is being registered  
            <font color="#ff2121">
              *
            </font>
          </td>
        <td width="47%">
        <select size="1" name="comOffId" id="comOffId">
         <%
                   System.out.println("here");
                   System.out.println(oid+"  " +oname);
                try
                {
                   if(oid==5000)
                    {
                        out.println("<option value="+oid+">"+"HEAD OFFICE"+"</option>");
                    }
                    else
                    {
                        ps=con.prepareStatement("select ACCOUNTING_UNIT_ID, ACCOUNTING_FOR_OFFICE_ID  from FAS_MST_ACCT_UNIT_OFFICES where ACCOUNTING_UNIT_ID=? order by ACCOUNTING_FOR_OFFICE_ID desc");
                        ps.setInt(1,unitid);
                        rs=ps.executeQuery();
                        //out.println("<option value="+oid+">"+oname+"</option>");
                        while(rs.next())
                        {
                        ps2=con.prepareStatement("select OFFICE_NAME from COM_MST_OFFICES where OFFICE_ID=?");
                        ps2.setInt(1,rs.getInt("ACCOUNTING_FOR_OFFICE_ID"));
                        rs2=ps2.executeQuery();
                        if(rs2.next())
                        out.println("<option value="+rs.getInt("ACCOUNTING_FOR_OFFICE_ID")+">"+rs2.getString("OFFICE_NAME")+"</option>");
                        }
                    }
                    
                } 
                catch(Exception e)
                {
                System.out.println("Exception in Office combo..."+e);
                }
                finally
                {
                rs.close();
                ps.close();
                }  
                %>
        </select>
        <!--  <input type="text" name="txtOffCodeRen" id="txtOffCodeRen" size="8"/>-->
         <!-- <input type="button" name="cmdSelect" id="cmdSelect" value="Select"/>-->
        </td>
      </tr>
      <tr align="left" class="table">
        <td width="53%">Firm's Id</td>
        <td width="47%">
          <input type="text" name="txtfirmId" id="txtfirmId" size="4" class="disab" readonly/>
          (System Generated)
        </td>
      </tr>
      <tr align="left" class="table">
        <td width="53%">Firm's Alias Id</td>
        <td width="47%">
          <input type="text" name="txtFirmsAliasId" id="txtFirmsAliasId" size="8"/>
          
        </td>
      </tr>
      <tr align="left" class="table">
        <td width="53%">Name of the Firm    <font color="#ff2121">*</font></td>
        <td width="47%">
          <input type="text" name="txtfirmName" id="txtfirmName" size="20" onchange="FirmNamecheck()"/>
        </td>
      </tr>
      <tr align="left" class="table">
        <td width="53%">Address1 <font color="#ff2121">*</font></td>
        <td width="47%">
          <input type="text" name="txtaddr" size="20" id="txtaddr" onblur="Addr1check()"/>
        </td>
      </tr>
      <tr align="left" class="table">
        <td width="53%">Address2</td>
        <td width="47%">
          <input type="text" name="txtaddr2" size="20" id="txtaddr2"/>
        </td>
      </tr>
      <tr align="left" class="table">
        <td width="53%">City or Town    <font color="#ff2121">*</font></td>
        <td width="47%">
          <input type="text" name="txtcity" size="20" id="txtcity" onblur="checkcity()" />
        </td>
      </tr>
       <tr align="left" class="table">
        <td width="53%">PinCode</td>
        <td width="47%">
          <input type="text" name="txtPincode" size="20" id="txtPincode" maxlength="6" onchange="pincodecheck()"  onkeypress="return numbersonly1(event,this)"/>
        </td>
      </tr>
      
      <tr align="left" class="table">
        <td width="53%">Phone</td>
        <td width="47%">
          <input type="text" name="txtPhone" size="20" id="txtPhone"  onchange="PhoneCheck()" onkeypress="return numbersonly1(event,this)"/>
        </td>
      </tr>
      <tr align="left" class="table">
        <td width="53%">Fax</td>
        <td width="47%">
          <input type="text" name="txtFax" size="20" id="txtFax" onchange="FaxCheck()" onkeypress="return numbersonly1(event,this)"/>
        </td>
      </tr>
      <tr align="left" class="table">
        <td width="53%">E-Mail Id</td>
        <td width="47%">
          <input type="text" name="txtEmail" size="20" id="txtEmail" onchange="EmailCheck()"/>
        </td>
      </tr>
      <tr align="left" class="table">
        <td width="53%">Date of Registration </td>
        <td width="47%">
         <input type="text" name="txtDateReg" size="20" maxlength="10" id="txtDateReg" onFocus="javascript:vDateType='3'"
        onkeypress="return  calins(event,this)" onblur="return checkdt(this);">
        <img src="../../../../../images/calendr3.gif" onclick="showCalendarControl(document.firmsForm.txtDateReg);" alt="Show Calendar" ></img>
       </td>
      </tr>
      <tr align="left" class="table">
        <td width="53%">Date of Last Service </td>
        <td width="47%">
         <input type="text" name="txtDateLastSupply" size="20" maxlength="10" id="txtDateLastSupply" onFocus="javascript:vDateType='3'"
             onkeypress="return  calins(event,this)" onblur="return checkdt(this);" disabled="disabled">
         <img src="../../../../../images/calendr3.gif" onclick="showCalendarControl(document.firmsForm.txtDateLastSupply);" alt="Show Calendar" disabled=disabled ></img>
       </td>
      </tr>
       <tr class="table">
             <td>
                  Status </td>
                  <td>
                  
                    <input type="radio" name="txtstatus" id="txtstatus" tabindex="15"
                            value="L" checked  >Live
                                                        &nbsp;&nbsp;&nbsp;&nbsp; 
                    <input type="radio" name="txtstatus" id="txtstatus" 
                           value="C"  >Cancel &nbsp;&nbsp;&nbsp;&nbsp; 
         
                </td>
             </tr>
      <tr class="tdH">
        <td width="60%" colspan="2">
          <div align="center">
          <table>
          <tr>
          <td>
            <input type="button" name="cmdAdd" value="ADD" id="cmdAdd" onclick="doFunction('Add','null')"/>
             </td><td>
            <input type="button" name="cmdUpdate" value="UPDATE" id="cmdUpdate" style="display:none" onclick="doFunction('Update','null')"/>
             </td><td>
            <input type="button" name="cmdDelete" value="CANCEL" id="cmdDelete" style="display:none" onclick="doFunction('Cancel','null')" />
             </td><td>
            <input type="button" name="cmdClear" value="CLEAR" id="cmdClear" onclick="ClearAll()"/>
             </td>
             <td>
            <input type="button" name="cmdList" value="LIST" id="cmdList" onclick="ListFirms()"/>
            
             </td>
               <td>
             <input type="button" id="Exit" name="Exit" value="EXIT" onclick="self.close()">
             </td>
             </tr>
             </table>
          </div>
        </td>
      </tr>
    </table>
    
  
  <!--
  <table id="mytable" align="center"  cellspacing="3" cellpadding="2" border="1" width="100%">
          <tr class="tdH">
            <th>
              Select
            </th>
            <th>
              Firm ID
            </th>
            <th>
              NAME
            </th>
            <th>
             ADDRESS
            </th>
            
            <th>
             CITY
            </th>
            <th>
              E-MAIL
            </th>
            <th>
              PHONE
            </th>
            <th>
              FAX
            </th>
            <th>
              DATE
            </th>
          </tr>
          <tbody id="tb" class="table" align="left">
          
          <%/*
          System.out.println("hereeeeeeeeeeeeeeeeeeeee oid is"+oid);
           try
           {
            ps=con.prepareStatement("select * from COM_FIRMS_SL_MST where ACCOUNTING_UNIT_ID="+acId+" order by FIRMS_ID");
            rs=ps.executeQuery();
                       
            while(rs.next())
            {
               
                int FirmId=rs.getInt("FIRMS_ID");
                System.out.println("*****************"+FirmId);
                String FirmName=rs.getString("FIRMS_NAME");
                 System.out.println(FirmName);
                String FirmAddr=rs.getString("FIRMS_ADDRESS");
                 System.out.println(FirmAddr);
                String FirmAddr1=rs.getString("FIRMS_ADDRESS1");
                 System.out.println(FirmAddr1);
                String FirmCity=rs.getString("FIRMS_CITY");
                 System.out.println(FirmCity);
                int FirmPh=rs.getInt("FIRMS_PHONE");
                 System.out.println(FirmPh);
                int FirmFax=rs.getInt("FIRMS_FAX");
                 System.out.println(FirmFax);
                String FirmEmail=rs.getString("FIRMS_EMAIL_ID");
                 System.out.println(FirmEmail+"*****************");
                 
                                      String[] sd;
                     sd=rs.getDate("DATE_OF_REGISTRATION").toString().split("-");
                     String dateReg=sd[2]+"/"+sd[1]+"/"+sd[0];
                    System.out.println("first date..."+dateReg);
                    String[] sd1;
                    sd1=rs.getDate("DATE_OF_LAST_SUPPLY").toString().split("-");
                    String datelast=sd1[2]+"/"+sd1[1]+"/"+sd1[0];
                
                out.println("<tr id='" + FirmId + "'>");   
                out.println("<td><a href=\"javascript:loadTable('" + FirmId + "')\">Edit</a></td>");
                
                out.println("<td>"+FirmId+"</td>");
                out.println("<td>"+FirmName+"</td>");
                out.println("<td>"+FirmAddr+",<br>"+FirmAddr1+"</td>");
                
                out.println("<td>"+FirmCity+"</td>");
                out.println("<td>"+FirmEmail+"</td>");
                out.println("<td>"+FirmPh+"</td>");
                out.println("<td>"+FirmFax+"</td>");
                out.println("<td>"+dateReg+"<br>"+datelast+"</td></tr>");
            }
          }
          catch(Exception e)
          {
            System.out.println("Exception in grid.."+e);
          }
          */%>
          </tbody>
        </table>
      
      <table cellspacing="3" cellpadding="2" border="1" width="100%" >
  
      <tr class="tdH">
      <td>
          <div align="center">
      <input type="button" id="Exit" name="Exit" value="Exit" onclick="self.close()">
      </div>
      </td>
      </tr>
      
      </table>-->
</form>
  </body>
</html>