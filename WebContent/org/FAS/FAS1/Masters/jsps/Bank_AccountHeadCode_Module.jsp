<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">
<%@ page session="false"  contentType="text/html;charset=windows-1252"%>
<%@ page import="java.sql.*,java.util.*,Servlets.Security.classes.UserProfile"%>
<%@ include file="//org/Security/jsps/Check_SessionJSPF.jspf" %>
<style type="text/css">
#temp
{
height:250px;
overflow:scroll;
}
</style>
<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=windows-1252"/>    
    <meta http-equiv="cache-control" content="no-cache">
    <title>Bank Accounting System</title>
   
    
 
   <script type="text/javascript"
            src="<%=request.getContextPath()%>/org/FAS/FAS1/CommonControls/scripts/Common_Load_Accounting_Unit_ID.js"></script>
    <script type="text/javascript" src="<%=request.getContextPath()%>/org/Library/scripts/comJS.js"></script>
    <script type="text/javascript"  src="../../../../../org/Library/scripts/checkDate.js"></script>
    <script type="text/javascript" src="../scripts/Bank_AccountHeadCode_Module1.js"></script>
    <script type="text/javascript" src="../../../../../org/FAS/FAS1/ReceiptSystem/scripts/Com_Popup_XMLreq_SL1.js"></script> 
    <link href="../../../../../css/Sample3.css" rel="stylesheet"  media="screen"/>
    <link href='../../../../../css/Fas_Account.css' rel='stylesheet' media='screen'/>
    
    <!-- to avoid future date the above script used-->
    <script type="text/javascript" language="javascript">
         function foc()
         {
         }
</script>
  </head>
  <body onload="LoadAccountingUnitID('LIST_ALL_UNITS');" bgcolor="rgb(255,255,225)">
  <table cellspacing="1" cellpadding="3" width="100%" >
      <tr class="tdH">
        <th colspan="2">
          <div align="center">
            <font size="4">Account Head Code Module details </font>
          </div>
        </th>
      </tr>
    </table>
    
    <form name="frmBank_AccountHeadCode_Module" id="frmBank_AccountHeadCode_Module" >
                  
  <%
  
      Connection con=null;
      ResultSet rs=null,rs2=null,rs3=null;
      PreparedStatement ps=null,ps2=null, ps3=null;
      ResultSet results=null;
      ResultSet results1=null;
      ResultSet results2=null;
      ResultSet results3=null;
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
     
    //int empid=9315;
    int  oid=0;
    String oname="",FAS_SU="",getWing="";        // office name,empname
    boolean isSuperUser=false;
     if(session.getAttribute("FAS_SU")!=null && ((String)session.getAttribute("FAS_SU")).equalsIgnoreCase("YES"))
         FAS_SU="YES";
    else
         FAS_SU="NO";
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
                <td colspan="2">
                  <div align="left">
                    Accounting Unit Code 
                    <font color="#ff2121">*</font>
                  </div>
                </td>
                <td>
                      <div align="left">
                         <select size="1" name="cmbAcc_UnitCode" id="cmbAcc_UnitCode" onchange="LoadBankAcc_No();">        
                         </select>
                      </div>
                    </td>
              </tr>
             <tr class="table">
                <td colspan="2">
                  <div align="left">Bank Account Number
                   <font color="#ff2121">*</font>
                   </div>
                </td>
                <td>
                  <div align="left">                                               
                            <select size="1" name="txtBankAccountNo" id="txtBankAccountNo" onchange="LoadBankAcc_Details();">
                            <option value="">-- Select Bank Ac No --</option>                            
                    </select>
                    
                  </div>
                </td>
              </tr>

              <tr class="table">
                <td>
                  <div align="left">
                     Bank & Branch Name
                    <font color="#ff2121">*</font>
                  </div>
                </td>
                <td>
                  <div align="left">
                  <input type="hidden" name="txtBankId" id="txtBankId"  readonly="readonly" class="disab" />
                  <input type="text" name="txtBankId_name" id="txtBankId_name" size="30"  />
                  </div>
                </td>
                 
                <td>
                  <div align="left">
                
                  <input type="hidden" name="txtBranchId" id="txtBranchId"  readonly="readonly" class="disab" />
                  <input type="text" name="txtBranchId_name" id="txtBranchId_name" size="30" />
                
                  </div>
                </td>
              </tr>
             
    <!-- siva added on 2016-06-07 in the purpose off add the Sl_No  -->
     <tr class="table">
                <td colspan="2">
                  <div align="left">Serial No
                  <font color="#ff2121">*</font>
                  </div>
                </td>
                <td>
                  <div align="left">
                    <input type="text" name="txtSl_no" id="txtSl_no" maxlength="5" onkeypress="return numbersonly(event)" />
                  </div>
                </td>
              </tr> 
    
             <tr class="table">
                <td>
                  <div align="left">
                     Bank Account Type & Mode
                    <font color="#ff2121">*</font>
                  </div>
                </td>
                <td>
                  <div align="left">
                  <input type="hidden" name="txtBankAcc_type" id="txtBankAcc_type"  readonly="readonly" class="disab" />
                  <input type="text" name="txtBankAcc_type_name" id="txtBankAcc_type_name" size="30" readonly="readonly" class="disab" />
                  </div>
                </td>
                 <td>
                  <div align="left">
                  <input type="hidden" name="txtOperation_mode" id="txtOperation_mode"  readonly="readonly" class="disab" />
                  <input type="text" name="txtOperation_mode_name" id="txtOperation_mode_name" size="30" readonly="readonly" class="disab" />
                  </div>
                </td>
              </tr>
              <tr class="table">
                <td  colspan="2">
                  <div align="left">
                    Account Head Code 
                    <font color="#ff2121">*</font>
                  </div>
                </td>
                <td>
                  <div align="left">
                    <input type="text" name="txtAcc_HeadCode" 
                           id="txtAcc_HeadCode" maxlength="8" style="background-color: #ececec" onblur="doFunction('checkCode','null');"
                           size="9"/>
                       <div id="accpopup" style="display:none;">    
                    <img src="../../../../../images/c-lovi.gif" width="20" 
                             height="20" alt="AccountHeadList"
                             onclick="AccHeadpopup();"></img>
                             </div>
                    <input type="text" name="txtAcc_HeadDesc" 
                           id="txtAcc_HeadDesc" style="background-color: #ececec"  maxlength="125" size="70"/>
                     <input type="button" name="gobtn" value="GO" id="gobtn" onclick="view_grid()" />      
                           
                  </div>
                </td>
              </tr>
            <!-- <tr class="table">
                <td  colspan="2">
                  <div align="left">
                     Module Type
                    <font color="#ff2121">*</font>
                  </div>
                </td>
                <td>
                  <div align="left">
                
                    <select size="1" name="cmbModule" id="cmbModule" >
                            <option value="">-- Select Module Type --</option>
                            <option value="MF004">Receipt System</option>
                            <option value="MF005">Payment System</option>
                            <option value="MF006">Remittance System</option>
                            <option value="MF015">Fund Transfer System</option>
                            <option value="MF009">Fund Receipt System</option>
                            <option value="MF010">Inter-Bank Transfer System</option>
                            <option value="MF008">Self-Cheque Drawl System</option>
                    </select>
                  </div>
                </td>
              </tr>  -->

         <!-- 
            <tr class="table">
                <td  colspan="2">
                  <div align="left">
                    CR/DR Indicator
                    <font color="#ff2121">*</font>
                  </div>
                </td>
                <td>
                  <div align="left">
                    <input type="radio" name="rad_CR_DR" id="rad_CR_DR" checked="checked" value="CR"/>Credit
                    <input type="radio" name="rad_CR_DR" id="rad_CR_DR" value="DR"/>Debit 
                    
                  </div>
                </td>
              </tr>
                <tr class="table">
                <td  colspan="2">
                  <div align="left">
                    Status
                    <font color="#ff2121">*</font>
                  </div>
                </td>
                <td>
                  <div align="left">
                    <input type="radio" name="radStatus" id="radStatus" checked="checked" value="L"/>Yes
                    <input type="radio" name="radStatus" id="radStatus" value="N"/>No 
                    
                  </div>
                </td>
              </tr>
              <tr class="table">
                <td colspan="2">
                  <div align="left">Remarks</div>
                </td>
                <td>
                  <div align="left">
                    <textarea name="txtRemarks" id="txtRemarks" cols="50" onkeypress="return check_leng(this.value);"
                              rows="2"></textarea>
                  </div>
                </td>
              </tr> 
             <tr class="table">
                <td  colspan="2">
                  <div align="left">Default </div>
                </td>
                <td>
                  <div align="left">
                    <input type="radio" name="radDefault" id="radDefault" value="1" checked="checked"> YES
                    <input type="radio" name="radDefault" id="radDefault" value="0"> NO
                  </div>
                </td>
              </tr>  -->
           <!--  <tr class="tdH">
              <td colspan="3">
                <div align="center">
                   <input type="button" name="cmdAdd" value="ADD" id="cmdAdd" onclick="add_grid()" />
                    <input type="button" name="cmdUpdate" value="UPDATE" id="cmdUpdate" style="display:none" onclick="doFunction('Update','null')"/>
                  
                    <input type="button" name="cmdClear" value="CLEAR" id="cmdClear" onclick="ClearAll()"/>
                    <input type="button" name="cmdList" value="LIST" id="cmdList" onclick="ListHeads()"/>
                    
                     </div>
                     </td>
            </tr>  -->
         </table>
                    <div id="grid" style="display:block">
                    <table id="mytable" cellspacing="3" cellpadding="2" border="1" width="100%">
                      
                       <tr class="tdH">
                       <th>Select
                       <a href="javascript:selectAll('ALL');">All</a>
                 <a href="javascript:selectAll('UNSelect');">Unselect</a> </th>
                            <th>Bank Account Type</th>
                            <th>Operational Mode Id</th>
                            <th>Module Id</th>
                            <th>CR DR Type</th>
                            <th>Status</th>
                            <th>Account Head Code</th>
                            
                      </tr>
                      
                       <tbody id="grid_body" class="table" align="left" >
                       </tbody>
					<tr class="tdH">
                                        <td colspan="10" align="center">
					<input type="button" name="submit" value="Submit" id="submit" onclick="doFunction('Add','null')" />
					 <input type="button" id="Exit" name="Exit" value="EXIT" onclick="self.close()">
                                        </td></tr>	
                    </table>
                  </div>
    </form>
   
    </body>
</html>

