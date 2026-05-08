<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">
<%@ page session="false"  contentType="text/html;charset=windows-1252"%>
<%@ page import="java.sql.*,java.util.*,Servlets.Security.classes.UserProfile"%>
<%@ include file="//org/Security/jsps/Check_SessionJSPF.jspf" %>
<html>
  

    <meta http-equiv="Content-Type" content="text/html; charset=windows-1252"/>    
    <meta http-equiv="cache-control" content="no-cache">
    <title>Land Details</title>
    <script type="text/javascript" src="<%=request.getContextPath()%>/org/Library/scripts/comJS.js"></script>
    <script type="text/javascript"  src="../../../../../org/FAS/FAS1/Masters/scripts/Land_details.js"></script>
    <script type="text/javascript" src="../../../../../org/Library/scripts/checkDate.js"></script>
   
    <link href="../../../../../css/Sample3.css" rel="stylesheet"  media="screen"/>
    <link href="../../../../../css/CalendarControl.css" rel="stylesheet" media="screen"/>
          <link href='../../../../../css/Fas_Account.css' rel='stylesheet' media='screen'/>
    <script type="text/javascript" src="../../../../../org/HR/HR1/EmployeeMaster/scripts/CalendarControl.js"></script> 
     <!-- to avoid future date the above script used-->
    <script type="text/javascript" language="javascript">
         function foc()
         {
         }
</script>

     <script language="javascript" type="text/javascript">
                function closeWindow()
                {                
                    window.open('','_parent','');                
                    window.close(); 
                    window.opener.focus();
                }
    </script>
    
  </head>
  
  <body onload="foc()" bgcolor="rgb(255,255,225)">
  <table cellspacing="1" cellpadding="3" width="100%" >
      <tr class="tdH">
        <td colspan="2">
          <div align="center">
            <font size="4">Land Details </font>
          </div>
        </td>
      </tr>
    </table>
    
    <form name="frmland_details" id="frmland_details" >
                  
  <%
  
      Connection con=null;
      ResultSet rs=null,rs2=null;
      PreparedStatement ps=null,ps2=null;
      ResultSet results=null;
      ResultSet results1=null;
      ResultSet results2=null;
       Statement statement=null;
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
           <% 
       HttpSession session=request.getSession(false);
         UserProfile empProfile=(UserProfile)session.getAttribute("UserProfile");
      
     System.out.println("user id::"+empProfile.getEmployeeId());
     int empid=empProfile.getEmployeeId();
     int bankid=0;
     
    //int empid=9315;
    int  oid=0;
    String oname="";
    try
    {
           
            ps=con.prepareStatement("select OFFICE_ID from HRM_EMP_CURRENT_POSTING where EMPLOYEE_ID=?" );
            ps.setInt(1,empid);
            results=ps.executeQuery();
                 if(results.next()) 
                 {
                    oid=results.getInt("OFFICE_ID");
                 }
            results.close();
            ps.close();
            ps=con.prepareStatement("select OFFICE_NAME from COM_MST_OFFICES where OFFICE_ID=?" );
            ps.setInt(1,oid);
            results=ps.executeQuery();
                 if(results.next()) 
                 {
                    oname=results.getString("OFFICE_NAME");
                  }
            results.close();
            ps.close();
     /* */      
            System.out.println("off id.. emp id"+oid+".."+empid);     
    }
    catch(Exception e)
    {
        System.out.println(e);
    }
   
   %>
   
    
            <table cellspacing="1" cellpadding="2" border="1" width="100%"  >
        
                        <tr class="table">
                <td>
                  <div align="left">
                    Accounting Unit Code 
                    <font color="#ff2121">*</font>
                  </div>
                </td>
                <td colspan="3">
                  <div align="left">
                    
                    <select size="1" name="cmbAcc_UnitCode" id="cmbAcc_UnitCode" tabindex="1">
                    
                          <%
                      int unitid=0;
                      String unitname="";
                     String FAS_SU="";
   
    if(session.getAttribute("FAS_SU")!=null && ((String)session.getAttribute("FAS_SU")).equalsIgnoreCase("YES"))
         FAS_SU="YES";
    else
         FAS_SU="NO";
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
                                      ps.close();
                                      rs.close();
                                          if(session.getAttribute("FAS_SU")!=null && ((String)session.getAttribute("FAS_SU")).equalsIgnoreCase("YES"))
                                          { 
                                             String su="select ACCOUNTING_UNIT_ID,ACCOUNTING_UNIT_NAME from FAS_MST_ACCT_UNITS where ACCOUNTING_UNIT_ID!=? order by ACCOUNTING_UNIT_NAME";
                                             ps=con.prepareStatement(su);
                                             ps.setInt(1,unitid);
                                             rs=ps.executeQuery();
                                              while(rs.next())
                                              {
                                                  out.println("<option value="+rs.getInt("ACCOUNTING_UNIT_ID")+">"+rs.getString("ACCOUNTING_UNIT_NAME")+"</option>");
                                              }
                                          }
                                  }
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
                                          ps.close();
                                          rs.close();
                                          if(session.getAttribute("FAS_SU")!=null && ((String)session.getAttribute("FAS_SU")).equalsIgnoreCase("YES"))
                                          { 
                                             String su="select ACCOUNTING_UNIT_ID,ACCOUNTING_UNIT_NAME from FAS_MST_ACCT_UNITS where ACCOUNTING_UNIT_ID!=? order by ACCOUNTING_UNIT_NAME";
                                             ps=con.prepareStatement(su);
                                             ps.setInt(1,unitid);
                                             rs=ps.executeQuery();
                                              while(rs.next())
                                              {
                                                 out.println("<option value="+rs.getInt("ACCOUNTING_UNIT_ID")+">"+rs.getString("ACCOUNTING_UNIT_NAME")+"</option>");
                                              }
                                          }
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
                  </div>
                </td>
              </tr>
              <tr class="table">
                <td>
                  <div align="left">
                    Accounting For Office Code
                    <font color="#ff2121">*</font>
                  </div>
                </td>
                <td colspan="3">
                  <!--  This is used to load details about login user( super user or oridinary user ) login and their unit id-->
                 <input type="hidden" name="FAS_SU_check" id="FAS_SU_check" value="<%=FAS_SU%>" />
                   <input type="hidden" name="unitid" id="unitid" value="<%=unitid%>" />
                   <!--/div-->
                   <!--end -->
                  
                  <div align="left">
                    <select size="1" name="cmbOffice_code" id="cmbOffice_code" tabindex="2">
                      
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
                  </div>
                </td>
              </tr>
              
           <tr align="left">
              <td class="table">
                <div align="left">
                         Financial Year <font color="#ff2121">*</font>
              </div>
              </td>
              <td colspan="3"  class="table">
                    <select name="cmbFinancialYear" id="cmbFinancialYear" tabindex="3" >
                    <option value="">--Select Year--</option>
                    <option value="2012-2013">2012-2013</option>
                    </select>
              </td>
              </tr>
          
              <tr class="table">
                <td>
                  <div align="left">
                     Asset Code
                    <font color="#ff2121">*</font>
                  </div>
                </td>
               <td colspan="3">
                  <div align="left">
                
                    <select size="1" name="cmbasset" id="cmbasset" tabindex="4" onblur="callServer('Get')" >
                    <option value=0>-- Select Asset Code --</option>
                      <%
                      int asset=0;
                    try
                    {ps=con.prepareStatement("select ASSET_CODE from FAS_ASSET_VAL_AC_DETAILS where ASSET_MAJOR_CLASS_CODE=1 and ACCOUNTING_UNIT_ID=?");
                     //ps=con.prepareStatement("select ASSET_CODE from FAS_ASSET_VAL_AC_DETAILS where ASSET_MAJOR_CLASS_CODE=? and ACCOUNTING_UNIT_ID=?");
                    // ps.setInt(1,7);
                     ps.setInt(1,unitid);
                     System.out.println("select ASSET_CODE from FAS_ASSET_VAL_AC_DETAILS where ASSET_MAJOR_CLASS_CODE='"+7+"' and ACCOUNTING_UNIT_ID='"+unitid+"'?");
                     rs=ps.executeQuery();
                     while(rs.next())
                     {
                    	System.out.println(rs.getInt("ASSET_CODE"));
                       // out.println("<option value="+rs.getInt("ASSET_CODE")+"</option>");
                        asset =rs.getInt("ASSET_CODE");
                      System.out.println("asset"+asset);
                      out.println("<option value='"+rs.getInt("ASSET_CODE")+"'>"+rs.getInt("ASSET_CODE")+"</option>");
                        //out.println("<option value="+asset+"</option>");
                     }
                        
                    } 
                    catch(Exception e)
                    {
                    System.out.println("Exception in asset combo..."+e);
                    }
                    finally
                    {
                    rs.close();
                    ps.close();
                    }  
                %>
                    </select>
                  </div>
                </td>
              </tr>
              <!--tr class="table">
                <td>
                  <div align="left">
                    Account Head Code 
                    <font color="#ff2121">*</font>
                  </div>
                </td>
                <td>
                  <div align="left">
                    <input type="text" name="txtAcc_HeadCode" 
                           id="txtAcc_HeadCode" maxlength="6"
                           onkeypress="return numbersonly(event)"
                            onchange="sixdigit();" 
                            onblur="doFunction('checkCode','null');"  size="9"/>
                    <img src="../../../../../images/c-lovi.gif" width="20" 
                             height="20" alt="AccountHeadList"
                             onclick="AccHeadpopup();"></img>
                    <input type="text" name="txtAcc_HeadDesc" readonly="readonly" 
                           id="txtAcc_HeadDesc" style="background-color: #ececec"  maxlength="125" size="70"/>
                  </div>
                </td>
              </tr-->
              
              <TR class="table">
                                      <TD >
            Name of the Project/Work/Scheme for which land is acquired <font color="#ff2121">*</font>
                                </TD>
                                      <td colspan="3">
                                        <input type="text" name="txtproject_name" id="txtproject_name" tabindex="5" onkeypress="return numFloatInt(event,this);"> 
                                         <input type="button" name="CmdCode" value="Select Code" onclick="ListHeads()"> 
                                         <input type="text" name="txtsubledger" readonly="readonly" 
                           id="txtsubledger" style="background-color: #ececec"  maxlength="125" size="70"/>
                                      </TD>
                                  </TR>
              <TR class="table">
                                      <TD>
                                        B.P.No <font color="#ff2121">*</font>
                             </td> <td colspan="3">
                                      <input type="text" name="txtBP_No" id="txtBP_No" tabindex="6" size="30" onkeypress="return numFloatInt(event,this);" >
                                          
                                      </TD>
                                  </TR>
              <tr class="table">
                                    <td>Taluk <font color="#ff2121">*</font>
                                    </td>
                                    <td colspan="3">
                                    <input type="text" name="txttaluk" id="txttaluk" tabindex="7" size="30" onchange="return toCheck1()" >
                                        
                                      </TD>
                                                                  
                                  </tr>
             
                                   <!-- <tr class="table">
                <td>
                  <div align="left"> Village  
                   
                   </div>
                </td>
                <TD>
                                     <input type="text" name="txtvillage" id="txtvillage"  tabindex="8" size="30" onchange="return toCheck1()" >
                                     
                       
                                      </TD>
              </tr>  -->    
                                  <TR class="table">
                                      <TD>
                   Nature of acquisition <font color="#ff2121">*</font>
            
                                </TD>
                                 <td colspan="3">
                            <select name="cmbacqn" id="cmbacqn" onchange="loadType()">
                            <option value="0">--Select acquistion Type--</option>
                            <option value="G">Gift</option>
                            <option value="P">Purchase</option>
                            <option value="H">Hire</option>
                            </select>
                        </td>
                                     
                                  </TR>
                                  <TR class="table">
                                      <TD>
                                       North Boundary <font color="#ff2121">*</font>
                                </TD>
                                      <TD>
                                         <input type="text" name="txtnorth" id="txtnorth"  tabindex="12" ></TD>
                           <td><div style="width: 40mm;" >South Boundary <font color="#ff2121">*</font></div></td><td> <input type="text" name="txtSouth" id="txtSouth" tabindex="13"  >
                                          
                          
                                      </TD>
                                 <!--  </TR>
                                  <TR class="table">
                                      <TD>
                                         South Boundary
                                </TD>
                                      <TD>
                                          <input type="text" name="txtSouth" id="txtSouth" tabindex="13"  >
                                          
                                      </TD>
                                  </TR> -->
                                  <TR class="table">
                                      <TD>
                                        East Boundary <font color="#ff2121">*</font>
                                </TD>
                                      <TD>
                                          <input type="text" name="txtEast" id="txtEast" tabindex="14" ></td>
                                          <td><div style="width: 40mm;" > West Boundary <font color="#ff2121">*</font></div></td><td><input type="text" name="txtWest" id="txtWest" tabindex="15" > 
                            
                                      </TD>
                                  </TR>
                                  <!-- <TR class="table">
                                      <TD>
                                         West Boundary
                                </TD>
                                      <TD>
                                          <input type="text" name="txtWest" id="txtWest" tabindex="15" > 
                            
                                      </TD>
                                  </TR> -->
                                  
              <tr class="table">
                <td>
                  <div align="left">Land Type <font color="#ff2121">*</font></div>
                </td>
                <td colspan="">
                         <div>   <select name="cmblandtype" id="cmblandtype" onchange="loadType()">
                            <option value="0">--Select Land Type--</option>
                            <option value="D">Dry</option>
                            <option value="W">Wet</option>
                            
                            </select> 
                            </div>
                            
                           </td><td><div style="width: 40mm;" >Extent Area(in acrs)<font color="#ff2121">*</font></div> </td>

                                        <td><input type="text" name="txtext_area" id="txtext_area" tabindex="18"  onkeypress="return numFloatInt(event,this);">
                                        </td>  
                                      
                <!--td>
                  <div align="left">
                     <input type="text" name="txtlandtype" id="txtlandtype"  tabindex="17" >
                            
                         
                  </div>
                </td-->
              </tr>
        <!--      <TR class="table">
                                      <TD>
                                         Extent Area(in acrs)
                                </TD>
                                      <TD>
                                          <input type="text" name="txtext_area" id="txtext_area" tabindex="18"  onkeypress="return numFloatInt(event,this);">
                                          
                                      </TD>
                                  </TR> -->   
                                   <TR class="table">
                                      <TD>
                                      <div align="left"> Survey No <font color="#ff2121">*</font></div>
                                        
                                </TD>
                                      <TD>
                                             <input type="text" name="txtsurvey_no" id="txtsurvey_no" tabindex="19"  >
                                     </td><td><div style="width: 40mm;" >Type of Foundation <font color="#ff2121">*</font></div></td>
                                     <td> <input type="text" name="txtfound" id="txtfound" tabindex="20"  >
                                          
                                      
                                      </TD>
                                  </TR>                   
             
                <!--  <TR class="table">
                                      <TD>
                          Type of Foundation
           
                                </TD>
                                      <TD>
                                          <input type="text" name="txtfound" id="txtfound" tabindex="20"  >
                                          
                                      </TD>
                                  </TR> -->
                                  <tr class="table">
                                      <td>
                         Name of the owner from whom land acquired <font color="#ff2121">*</font>
           
                                </td>
                                      <td>
                                          <input type="text" name="txtowner" id="txtowner" tabindex="21"  >
                                       </td><td><div style="width: 40mm;" >Amount paid per Land <font color="#ff2121">*</font></div></td>
                                           <td>  <input type="text" name="txtlease" id="txtlease" tabindex="22" onkeypress="return numFloatInt(event,this);" >
                                          
                                      </td>
                                  </tr>
                                 <!--  <tr class="table">
                                      <td>
                         Lease Period(in years)
           
                                </td>
                                      <td>
                                          <input type="text" name="txtlease" id="txtlease" tabindex="22" onkeypress="return numFloatInt(event,this);" >
                                          
                                      </td>
                                  </tr> --> 
                                  <tr class="table">
                                      <td>
                         Amount paid per Land <font color="#ff2121">*</font>
           
                                </td>
                                      <td colspan="3">
                                          <input type="text" name="txtpaid_land" id="txtpaid_land" tabindex="23"  onkeypress="return numFloatInt(event,this);">
                                          
                                      </td>
                                  </tr>
                                  <tr class="table">
                                      <td>
                         Amount paid for Wells,Buildings,Trees etc <font color="#ff2121">*</font>
           
                                </td>
                                      <td colspan="3">
                                          <input type="text" name="txtpaid_buildings" id="txtpaid_buildings" tabindex="24" onkeypress="return numFloatInt(event,this);" >
                                          
                                      </td>
                                  </tr>
                                  <tr class="table">
                                       <td>
                        Total Amount paid <font color="#ff2121">*</font>
           
                                </td>
                                       <td colspan="3">
                                          <input type="text" name="txttotal_amount" id="txttotal_amount" tabindex="25" onkeypress="return numFloatInt(event,this);" >
                                          
                                      </td>
                                  </tr>
                                    <tr class="table">
                                      <td>
                        Details of Voucher No and Date <font color="#ff2121">*</font>
           
                                </td>
                                    <td colspan="3">
                                          <input type="text" name="txtvoucher_no" id="txtvoucher_no" tabindex="26" onkeypress="return numFloatInt(event,this);" >
                                          <input type="text" name="txtvoucher_date" id="txtvoucher_date"  tabindex="27" 
                            
                           maxlength="10" size="11"  
                           onfocus="javascript:vDateType='3';"
                           onkeypress="return calins(event,this);"
                           onblur="return checkdt(this);"/>
                           
                     <img src="../../../../../images/calendr3.gif"
                         onclick="showCalendarControl(document.frmland_details.txtvoucher_date,1);"
                         alt="Show Calendar"></img>
                                      </td>
                                  </tr>
                                   
                                   <tr class="table">
                                      <td>
                        Registration office <font color="#ff2121">*</font>
           
                                </td>
                                     <td colspan="3">
                                          <input type="text" name="txtregister_office" id="txtregister_office" tabindex="28" >
                                          
                                      </td>
                                  </tr>
                                   <tr class="table">
                                      <td>
                        Date of Registration and Doc.No <font color="#ff2121">*</font>
           
                                </td>
                                       <td colspan="3">
                                          <input type="text" name="txtdoc_no" id="txtdoc_no" tabindex="29" onkeypress="return numFloatInt(event,this);" >
                                          <input type="text" name="txtdoc_date" id="txtdoc_date"  tabindex="30" 
                            
                           maxlength="10" size="11"  
                           onfocus="javascript:vDateType='3';"
                           onkeypress="return calins(event,this);"
                           onblur="return checkdt(this);"/>
                           
                     <img src="../../../../../images/calendr3.gif"
                         onclick="showCalendarControl(document.frmland_details.txtdoc_date,1);"
                         alt="Show Calendar"></img>
                                      </td>
                                  </tr>
                             <tr class="table">
                                      <td>
                        Details of Possession of title deed <font color="#ff2121">*</font>
           
                                </td>
                                      <td colspan="3">
                                          <input type="text" name="txtdetails" id="txtdetails" tabindex="31"  >
                                          
                                      </td>
                                  </tr>      
                                  
                                   <TR class="table">
                                      <TD>
                                         Remarks <font color="#ff2121">*</font>
                                </TD>
                                      <td colspan="3">
                                          <textarea name="txtRemarks" id="txtRemarks" cols="50" tabindex="21" 
                              rows="4"></textarea>
                                          
                                      </TD>
                                  </TR>
             <tr class="tdH">
              <td colspan="4">
                <div align="center">
                <table >
                 <tr>
          <td colspan="3" class="table">
            <input type="button" name="CmdAdd" value="ADD" id="CmdAdd" onclick="callServer('Add')"/>
            <input type="button" name="CmdUpdate" value="UPDATE"
                   id="CmdUpdate" onclick="callServer('Update')" disabled/>
            <input type="button" name="CmdDelete" value="DELETE"
                   id="CmdDelete" onclick="callServer('Delete')" disabled/>
            <!--input type="button" name="CmdList" value="LIST"
                   id="CmdList" onclick="callServer('Get')"/-->
            <input type="reset" name="CmdClear" value="CLEAR ALL"
                   id="CmdClear" onclick="clearAll();"/>
                    <input type="button" name="CmdList" value="LIST"
                   id="CmdList" onclick="ListAll();"/>
            <input type="button" name="CmdExit" value="EXIT" onclick="closeWindow()">
          </td>
        </tr>
                </table>
                </div>
              </td>
            </tr>
         </table>
    </form></body>
</html>