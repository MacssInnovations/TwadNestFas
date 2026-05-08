<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">
<%@ page contentType="text/html;charset=windows-1252"%>
<%@ page import="java.sql.*,java.util.*,Servlets.Security.classes.UserProfile"%>
<%@ page session="false" contentType="text/html;charset=windows-1252"%>
<%@ include file="//org/Security/jsps/Check_SessionJSPF.jspf"%>
<%@ include file="//org/Security/jsps/IntialLoad.jsp"%>
<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=windows-1252"/>
    <META HTTP-EQUIV="CACHE-CONTROL" CONTENT=" no-store, no-cache, must-revalidate" >
   <META HTTP-EQUIV="CACHE-CONTROL" CONTENT=" pre-check=0, post-check=0, max-age=0" >
    <title>Asset Details</title>
    <script type="text/javascript" src="../scripts/AssetScript.js"></script>
    <script type="text/javascript"
            src="../../../../Library/scripts/checkDate.js"></script>
            <script type="text/javascript" src="<%=request.getContextPath()%>/org/Library/scripts/comJS.js"></script>
  
    <script type="text/javascript">
    function servicepopup()
    {
       
      my_window= window.open("EmpServicePopup.jsp","mywindow1","status=1,height=400,width=500,resizable=YES, scrollbars=yes"); 
      my_window.moveTo(250,250);    
    }
    </script>
    <script type="text/javascript">
   
    
    </script>
   
    <link href="../../../../../css/Sample3.css" rel="stylesheet"
          media="screen"/>
    <link href="../../../../../css/CalendarControl.css" rel="stylesheet"
          media="screen"/>
    <script type="text/javascript" src="../../../../../org/FAS/FAS1/CalendarCtrl.js"></script>  
  </head>
  <body onload="loadDate()"  bgcolor="rgb(255,255,225)"><form name="AssetForm" id="AssetForm">
     <%
response.setHeader("Cache-Control","no-cache"); //HTTP 1.1
response.setHeader("Pragma","no-cache"); //HTTP 1.0
response.setDateHeader ("Expires", 0); //prevent caching at the proxy server
%>
      
      <table cellspacing="3" cellpadding="2" border="1" width="100%">
        <tr class="tdH">
          <td colspan="2">
            <div align="center">
              <strong>Assets Maintenance System(Value Account)</strong>
            </div>
          </td>
        </tr>
        <tr class="table">
          <td width="40%">Accounting Unit Code <font color="#ff2121">*</font></td>
          <td width="60%">
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
        <tr class="table">
          <td width="40%">
            Office for which Asset is being registered 
            <font color="#ff2121">
              *
            </font></td>
          <td width="60%">
              <select size="1" name="comOffCode" id="comOffCode" >
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
          </td>
        </tr>
         <tr class="table">
          <td width="40%">Date <font color="#ff2121">*</font></td>
          <td width="60%">
            <input type="text" name="txtDate" id="txtDate" maxlength="10" 
                   size="10" onfocus="javascript:vDateType='3'"
                   onkeypress="return calins(event,this)"
                   onblur="if(checkdt(this)) finanYear();"></input>
             <img src="../../../../../images/calendr3.gif" id="calenderCTRL" 
                         onclick="showCalendarControl(document.AssetForm.txtDate);"
                         alt="Show Calendar"></img>
          </td>
        </tr>

        <tr class="table">
          <td width="40%">Financial Year <font color="#ff2121">*</font></td>
          <td width="60%">
          <!--  <input type="text" name="txtFinYear" id="txtFinYear" onfocus="finanYear();" maxlength="9" 
                   size="10" />-->
          <input type="text" name="txtFinYear" id="txtFinYear" maxlength="9"  readonly="readonly" class="disab"
                   size="10" />
       <!--   <select name="txtFinYear" id="txtFinYear" >
          <option value="">Select Financial year</option>
          <option value="2000-2001">2000-2001</option>
          <option value="2000-2001">2002-2003</option>
          <option value="2000-2001">2004-2005</option>
          <option value="2000-2001">2005-2006</option>
          <option value="2000-2001">2006-2007</option>
          </select> -->
          </td>
        </tr>
        <tr class="table">
          <td width="40%">Asset Type</td>
          <td width="60%">
          <select size="1" name="txtAssTypeCode" id="txtAssTypeCode" onchange="load_AssetClassification()">
          <option value="">--Select Asset Type--</option>
          <%
          ps1=con.prepareStatement("select ASSET_TYPE_CODE,ASSET_TYPE_DESC from COM_MST_ASSETS_TYPE");
         ResultSet rs1=ps1.executeQuery();
          while(rs1.next())
          {
          out.println("<option value="+rs1.getString("ASSET_TYPE_CODE").trim()+">"+rs1.getString("ASSET_TYPE_DESC")+"</option>");
          System.out.println(rs1.getString("ASSET_TYPE_CODE"));
          }
          %>
          </select>
          <!--
            <input type="text" name="txtAssTypeCode" id="txtAssTypeCode" size="4" readonly="readonly" class="disab" />
            <input type="text" name="txtAssType" maxlength="8" size="8"
                   id="txtAssType" readonly="readonly" class="disab"/> -->
          </td>
        </tr>
        <tr class="table">
          <td width="40%">Classification Of Asset <font color="#ff2121">*</font></td>
          <td width="60%">
            <select size="1" name="comClasAss" id="comClasAss" onfocus="finanYear();"
                    onchange="doFunction('Depreciation_assetType','null')">
              <option value="">-- Select Asset Class --</option>
              <%
               /*
                try
                {
                ps1=con.prepareStatement("select ASSET_CLASS_CODE,ASSET_CLASS_DESC from COM_MST_ASSETS_CLASS order by ASSET_CLASS_CODE");
                int des1=0;
                rs1=ps1.executeQuery();
                while(rs1.next())
                {
                des1=rs1.getInt("ASSET_CLASS_CODE");
                out.println("<option value="+des1+">"+rs1.getString("ASSET_CLASS_DESC")+"</option>");
                }
                }
                catch(Exception ae)
                {
                System.out.println("exception in asset types...."+ae);
                }
                
                
                rs1.close();
                ps1.close();
                */
                %>
            </select>
          </td>
        </tr>
        
        <tr class="table">
          <td width="40%">Asset Code </td>
          <td width="60%">
            <input type="text" name="txtAssCode" maxlength="5" size="5"
                   id="txtAssCode" readonly="readonly" class="disab"/> (System Generated)
          </td>
        </tr>
        <tr class="table">
          <td width="40%">Alias Code</td>
          <td width="60%">
            <input type="text" name="txtAliasCode" maxlength="10" size="15"
                   id="txtAliasCode"/>
          </td>
        </tr>
        
        
        <tr class="table">
          <td width="40%">Description Of the Asset <font color="#ff2121">*</font></td>
          <td width="60%">
            <input type="text" name="txtDesAsset" id="txtDesAsset" size="75" maxlength="100">
          </td>
        </tr>
        <tr class="table">
          <td width="40%">Year Of Purchase </td>
          <td width="60%">
            <input type="text" name="txtPurchaseYear" id="txtPurchaseYear"
                   onkeypress="return numbersonly1(event,this)" maxlength="4" size="4" onblur="checkYear()"/>
          </td>
        </tr>
        <tr class="table">
          <td width="40%">Month Of Purchase </td>
          <td width="60%">
            <select size="1" name="txtPurchaseMonth" id="txtPurchaseMonth">
              <option value="">---Select---</option>
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
            </select>
          </td>
        </tr>
        
        
        <tr class="table">
          <td width="40%">Original Cost <font color="#ff2121">*</font></td>
          <td width="60%">
            <input type="text" name="txtOrigCost" id="txtOrigCost" onkeypress="return limit_amt(this,event);" onblur="valid_amt(this);"
                  size="17" maxlength="17"  />
          </td>
        </tr>
        <tr class="table">
          <td width="40%">Percentage Of Depreciation </td>
          <td width="60%">
            <input type="text" name="txtPercDep" id="txtPercDep" class="disab"
                    maxlength="3" size="5"
                   />
          </td>
        </tr>
        <tr class="table">
          <td width="40%">Current Value Of Asset after Depreciation <font color="#ff2121">*</font> </td>
          <td width="60%">
            <input type="text" name="txtCurrVal" id="txtCurrVal" size="17" maxlength="17" 
            onkeypress="return limit_amt(this,event);" onblur="valid_amt(this);" />
            </td>
        </tr>
        
        <tr class="table">
          <td width="40%">Remarks</td>
          <td width="60%">
            <input type="text" name="txtRem" id="txtRem" size="75" maxlength="100"/>
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
          <tr>
            <td colspan="2">
             <div id="vehicle"  style="display:none">                          <!--  will be used in case of vehicles -->
            <table cellspacing="3" cellpadding="2" border="1" width="100%">
                <tr class="tdH">
                    <td colspan="2">
                        Vehicles Details
                    </td>
                </tr>
                <tr class="table">
                  <td width="40%">Type Of Ownership <font color="#ff2121">*</font></td>
                  <td width="60%">
                    <select size="1" name="comOwner" id="comOwner">
                      <option value="">---Select---</option>
                      <option value='B'>Owned by Board</option>
                      <option value='H'>Hired</option>
                      <option value='D'>Donated</option>
                    </select>
                  </td>
                </tr>
                <tr class="table">
                  <td width="40%">Name Of the Agency ( Donated or Hired) 
                    <font color="#ff2121">
                      *
                    </font>
                  </td>
                  <td width="60%">
                    <input type="text" name="txtDenName" id="txtDenName" size="75" maxlength="100"/>
                  </td>
                </tr>
                <tr class="table">
                  <td width="40%">Fuel Type Used 
                    <font color="#ff2121">
                      *
                    </font>
                  </td>
                  <td width="60%">
                    <select size="1" name="comFuel" id="comFuel">
                      <option value="">---select---</option>
                      <option value="Petrol">Petrol</option>
                      <option value="Diesel">Diesel</option>
                    </select>
                  </td>
                </tr>
                <tr class="table">
                  <td width="40%">Location in which the vehicle is used at present 
                    <font color="#ff2121">
                      *
                    </font>
                  </td>
                  <td width="60%">
                    <input type="text" name="txtlocation" id="txtlocation" onchange="doFunction('office',this.value);"
                            size="5" maxlength="4"/>
                        
                    <img src="../../../../../images/c-lovi.gif" width="20" height="20"
                         alt="OfficeList" onclick="jobpopup1();"/>
                     <input type="text" name="txtlocationName" id="txtlocationName" class="disab"
                                   maxlength="50" size="50"
                                      readonly="readonly"/>
                  </td>
                </tr>
            </table>
            </div>
            </td>
            </tr>
        <tr class="tdH">
          <td width="40%" colspan="2">
            <div align="center">
              <table>
                <tr>
                  <td>
                    <input type="button" name="cmdAdd" id="cmdAdd" 
                           value="ADDNEW" onclick="doFunction('Add','null')"/>
                  </td>
                  <td>
                    <input type="button" name="cmdUpdate" value="UPDATE" 
                           id="cmdUpdate" style="display:none"
                           onclick="doFunction('Update','null')"/>
                  </td>
                  <td>
                    <input type="button" name="cmdDelete" value="CANCEL"
                           id="cmdDelete" style="display:none"
                           onclick="doFunction('Cancel','null')"/>
                  </td>
                  <td>
                    <input type="button" name="cmdCancel" value="EXIT"
                           id="cmdCancel" onclick="Exit()"/>
                  </td>
                  <td>
                    <input type="button" name="cmdClear" value="CLEARALL"
                           id="cmdClear" onclick="clearall()"/>
                  </td>
                  <td>
                    <input type="button" name="cmdList" value="LIST"
                           id="cmdList" onclick="listall()"/>
                  </td>
                </tr>
              </table>
            </div>
          </td>
        </tr>
      </table>
    </form></body>
</html>