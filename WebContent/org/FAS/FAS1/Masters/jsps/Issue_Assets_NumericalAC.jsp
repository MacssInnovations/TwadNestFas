<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">
<%@ page session="false"  contentType="text/html;charset=windows-1252"%>
<%@ page import="java.sql.*,java.util.*,Servlets.Security.classes.UserProfile"%>

<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=windows-1252"/>    
    <meta http-equiv="cache-control" content="no-cache">
    <title>Issue of Assets Numerical A/C </title>
    <script type="text/javascript" src="<%=request.getContextPath()%>/org/Library/scripts/comJS.js"></script>
    <script type="text/javascript"  src="../../../../../org/Library/scripts/checkDate.js"></script>
     <script type="text/javascript" src="../scripts/Issue_Asset_NumericalAC.js"></script>
    <link href="../../../../../css/Sample3.css" rel="stylesheet"  media="screen"/>
    <link href="../../../../../css/CalendarControl.css" rel="stylesheet" media="screen"/>
          <link href='../../../../../css/Fas_Account.css' rel='stylesheet' media='screen'/>
          <script type="text/javascript"
            src="<%=request.getContextPath()%>/org/FAS/FAS1/CommonControls/scripts/Common_Load_Accounting_Unit_ID.js"></script>
    <script type="text/javascript"
            src="<%=request.getContextPath()%>/org/FAS/FAS1/CommonControls/scripts/Common_Load_Accounting_office.js"></script>
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
  <body onload="foc();setTimeout('checkStatus()',800);LoadAccountingUnitID_new('LIST_ALL_UNITS');">
  <table cellspacing="1" cellpadding="3" width="100%" >
      <tr class="tdH">
        <td colspan="2">
          <div align="center">
            <font size="4">Issue of Assets Numerical A/C </font>
          </div>
        </td>
      </tr>
    </table>
    
    <form name="frmIssue_Asset" id="frmIssue_Asset" >
                  
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
    int  oid=0,majorclass=0;
    int assetmajor=0;
    String oname="";
    String AssetDesc="";
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
   
    
            <table cellspacing="1" cellpadding="2" border="1" width="100%">
        
                        <tr class="table">
                    <td>
                      <div align="left" >
                              Accounting Unit Code  <font color="#ff2121">*</font>
                      </div>
                    </td>
                    <td>
                      <div align="left">
                         <select size="1" name="cmbAcc_UnitCode" id="cmbAcc_UnitCode" onchange="loadOffice_REC();loadTransferUnit();">        
                         </select>
                      </div>
                    </td>
              </tr>


              <tr class="table">
                    <td>
                      <div align="left">
                        Accounting For Office Code <font color="#ff2121">*</font>
                      </div>
                    </td>
                    <td>
                      <div align="left">
                        <select size="1" name="cmbOffice_code" id="cmbOffice_code" >
                          
                        </select>
                      </div>
                    </td>
              </tr>
                  <tr class="table">
              <td >
                <div align="left">
                         Financial Year <font color="#ff2121">*</font>
              </div>
              </td>
              <td colspan="2">
                    <select name="cmbFinancialYear" id="cmbFinancialYear" onchange="checkStatus();">
                    <option value="">--select--</option>
                    <option value="2012-13">2012-13</option>
                    <!--<%
                        Statement st=con.createStatement();
                        rs=st.executeQuery("select financial_year from cash_book_control");
                        while(rs.next())
                        {
                            out.println("<option value='"+rs.getString("financial_year")+"'>"+rs.getString("financial_year")+"</option>");
                        }
                    %>
                    --></select>
              </td>
              </tr>
              <tr class="table">
          <td class="table">
          <div align="left">
              Cash Book Year &amp; Month
              </div>
            </td>
            <td>
             <div align="left">
          <input type="text" name="txtCB_Year" id="txtCB_Year" value=2012 tabindex="3"  maxlength="4" size="5" onblur=" checkcashmonth()" onchange="checkcashmonth();">
         
          <select name="txtCB_Month"  id="txtCB_Month" tabindex="4" onblur=" checkcashmonth()" onchange="checkcashmonth();">        
          <option value="1">January</option>
          <option value="2">February</option>
          <option value="3">March</option>
          <option value="4">April</option>
          <option value="5">May</option>
          <option value="6">June</option>
          <option value="7">July</option>
          <option value="8">August</option>
          <option value="9">September</option>
          <option value="10">October</option>
          <option value="11">November</option>
          <option value="12">December</option>
          </select>
           </div>
          </td>
        </tr>
              
              
                 <tr class="table">
                <td>
                    <div align="left">
                        Asset Major Classification
                        <font color="#ff2121">*</font>
                     </div>
                </td>
                <td>
                  <div align="left">
                    
                    <select size="1" name="cmbassetclass" id="cmbassetclass"  tabindex="4" onchange="LoadAssetCode2();checkcashmonth();">
                    <option value="">-- Select Asset Major Class--</option>
                      <%
                      
                    try
                    {
                    	ps=con.prepareStatement("select ASSET_MAJOR_CLASS_CODE,ASSET_MAJOR_CLASS_DESC from FAS_MST_ASSETS_CLASS WHERE asset_type_code='G' ORDER BY asset_major_class_code");
                   //  ps=con.prepareStatement("select ASSET_CLASS_CODE,ASSET_CLASS_DESC from COM_MST_ASSETS_CLASS ");
                     rs=ps.executeQuery();
                     while(rs.next())
                     {  
                    	 assetmajor=rs.getInt("ASSET_MAJOR_CLASS_CODE");
                        System.out.println("asset"+assetmajor);
                    	System.out.println(rs.getString("ASSET_MAJOR_CLASS_DESC"));
                      
                     // System.out.println("asset"+asset);
                      out.println("<option value="+rs.getInt("ASSET_MAJOR_CLASS_CODE")+">"+rs.getString("ASSET_MAJOR_CLASS_DESC")+"</option>");
                       
                     }
                        System.out.println("asset"+assetmajor); 
                    } 
                    catch(Exception e)
                    {
                    e.printStackTrace();
                    }
                    %>
                    </select>
                  </div>
                </td>
              </tr>
              <tr class="table">
                <td>
                    <div align="left">
                        Asset Code
                        <font color="#ff2121">*</font>
                     </div>
                </td>
                <td>
                  <div align="left">
                    
                    <select size="1" name="cmbassetcode" id="cmbassetcode" tabindex="4" onchange="loadAssetDesc();">
                    
                    <option value="">-- Select Asset code --</option>
                  
                    </select>
                  </div>
                </td>
              </tr>    
                
                             
               <tr class="table">
                <td>
                    <div align="left">
                       Asset Description
                     </div>
                </td>
              
                 <td>
                  <div align="left" >
                  
                  <input type="text" name="txtassetdesc"  id="txtassetdesc" size="120" readonly="readonly"/> 
                   
                  </div>
                </td>

              </tr>
              
              <tr class="table">
                                      <td>
                                       <div align="left">
              Date of issue of the Asset
              <font color="#ff2121">*</font>
                                        </div>
                                        </td>
                                      <TD>
                                      <div align="left">
                     <input type="text" name="issue_date" id="issue_date"  tabindex="7"
                            
                           maxlength="10" size="11"  
                           onfocus="javascript:vDateType='3';"
                           onkeypress="return calins(event,this);" 
                           onchange="checkDate();"
                           /><!--
                           onblur="return checkdt(this);"
                     --><img src="../../../../../images/calendr3.gif"
                         onclick="showCalendarControl(document.frmIssue_Asset.issue_date,1);"
                         alt="Show Calendar"></img>                     
                  </div>
                              </TD>
                              
                                  </TR>
              <tr class="table">
                <td>
                    <div align="left">
              Issue within in the Division/Circle/Outside Circle
            </div>
                </td>
                 <td>
                  
                    <input type="radio" name="levelid" id="levelid" tabindex="13" value="D" checked="checked">Division                                   
                    <input type="radio" name="levelid" id="levelid" tabindex="14" value="C" >Circle 
                    <input type="radio" name="levelid" id="levelid" tabindex="15" value="OC">Outside Circle       
                    </td>
              </tr>
            
              <TR class="table">
                                      <TD >
                                         Office to which the Asset is sent <font color="#ff2121">*</font>
                                </TD>
                                <td>
                                        <SELECT size=1 name=txtOffice_Name id=txtOffice_Name >   
                                            <option value>----Select Officename----</option>
                                            <%
                                            try
                                            {
                                                
                                             
                                                
                                                    ps=con.prepareStatement("select OFFICE_ID,OFFICE_NAME from COM_MST_OFFICES where office_id in (select ACCOUNTING_FOR_OFFICE_ID  from FAS_MST_ACCT_UNIT_OFFICES) order by office_id");
                                                    //ps.setInt(1,unitid);
                                                    rs=ps.executeQuery();
                                                    //out.println("<option value="+oid+">"+oname+"</option>");
                                                    while(rs.next())
                                                    {
                                                    
                                                   // out.println("<option value="+rs.getString("OFFICE_NAME")+"</option>");
                                                        out.println("<option value="+rs.getInt("OFFICE_ID")+">"+rs.getString("OFFICE_NAME")+"</option>");  
                                                }
                                               
                                            } 
                                            catch(Exception e)
                                            {
                                            System.out.println("Exception in Office combo..."+e);
                                            }
                                                
                                            %>
                                          </SELECT>
                                      </td>
                                     </TR>
                  
                                  <tr class="table">
              
                <td>
                  <div align="left">
                   Quantity Issued         <font color="#ff2121">* </font>
                          </div>
                </td>
                <td>
                  <div align="left" >
                  
                  <input type="text" name="txtqtyissued"  id="txtqtyissued"  onkeypress="return numbersonly(event)" maxlength="20" size="25" > 
                    
                  </div>
                </td>
                </tr>
                 <tr class="table">
              
                <td>
                  <div align="left">
                   Value Issued         <font color="#ff2121">* </font>
                          </div>
                </td>
                <td>
                  <div align="left" >
                  
                  <input type="text" name="txtvalueissued"  id="txtvalueissued"  onkeypress="return numbersonly(event)" maxlength="20" size="25" > 
                    
                  </div>
                </td>
                </tr>
                 <tr class="table">
                <td>
                  <div align="left">Remarks</div>
                </td>
                <td>
                  <div align="left">
                    <textarea name="txtRemarks" id="txtRemarks" cols="50" tabindex="13" 
                              rows="4"></textarea>
                  </div>
                </td>
              </tr>
               <tr class="table">
                <td>
                    <div align="left" style="display:none;">
                        TDA Originating Journal NO.
                     </div>
                </td>
                <td>
                  <div align="left" style="display:none;">
                    
                    <select size="1" name="cmbjournalno" id="cmbjournalno" tabindex="4" >
                    <option value="">-- Select here--</option>
                     
                    </select>
                  </div>
                </td>
              </tr>
                <tr class="table">
                
                                      <td>
                                       <div align="left" style="display:none;">
                                       TDA Originating Journal Date <font color="#ff2121">*</font>
                                        </div>
                                        </td>
                                      <TD>
                                      <div align="left" style="display:none;">
                    <input type="text" name="txtjournal_date" id="txtjournal_date"  tabindex="7" 
                            
                           maxlength="10" size="11"  
                           onfocus="javascript:vDateType='3';"
                           onkeypress="return calins(event,this);"
                           onblur="return checkdt(this);"/>
                           
                     <img src="../../../../../images/calendr3.gif"
                         onclick="showCalendarControl(document.frmIssue_Asset.txtjournal_date,1);"
                         alt="Show Calendar"></img>                     
                  </div>
                              </TD>
                              
                                  </TR>
                        
                
               
                </table>
             
                               <table cellspacing="1" cellpadding="2" border="1" width="100%">
        
             <tr class="tdH">
              <td colspan="2">
                <div align="center">
                <table >
                 <tr>
                    <td>
                    <input type="button" name="cmdAdd" value="ADD" id="cmdAdd" onclick="checkDate();doFunction('Add','null')" tabindex="20"/>
                     </td>
                     <td>
                    <input type="button" name="cmdUpdate" value="UPDATE" id="cmdUpdate" style="display:none" onclick="checkDate();doFunction('Update','null')" tabindex="30"/>
                     </td>
                    <td>
                    <input type="button" name="cmdDelete" value="DELETE" id="cmdDelete" style="display:none" onclick="doFunction('Delete','null')" tabindex="40"/>
                     </td>
                     <td>
                    <input type="button" name="cmdClear" value="CLEAR" id="cmdClear" onclick="ClearAll()" tabindex="50"/>
                     </td>
                     <td>
                    <input type="button" name="cmdList" value="LIST" id="cmdList" onclick="ListAll()" tabindex="60"/>
                     </td>
                       <td>
                     <input type="button" id="Exit" name="Exit" value="EXIT" onclick="exit()" tabindex="70"/>
                     </td>
                 </tr>
                </table>
                </div>
              </td>
            </tr>
         </table>
    </form></body>
</html>