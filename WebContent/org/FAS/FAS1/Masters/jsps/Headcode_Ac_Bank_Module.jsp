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
    <title>Bank Accounting System for Manage Account HeadCode</title>
   

   <script type="text/javascript"
            src="<%=request.getContextPath()%>/org/FAS/FAS1/CommonControls/scripts/Common_Load_Accounting_Unit_ID.js"></script>
 
    <script type="text/javascript" src="../scripts/Headcode_Ac_Bank_Module.js"></script>
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
                         <select size="1" name="cmbAcc_UnitCode" id="cmbAcc_UnitCode" onchange="Cmb_BankAcc_No();">        
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
                            <select size="1" name="txtBankAccountNo" id="txtBankAccountNo" onchange="LoadBankAcc_Details();" >
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
                   <!--   <input type="button" name="gobtn" value="GO" id="gobtn" onclick="view_grid();" />    -->  
                           
                  </div>
                </td>
              </tr>
                           
                  
          
   <!--       <tr class="table">
          
                <td  colspan="2">
                  <div align="left">
                     Module Type
                    <font color="#ff2121">*</font>
                  </div>
                </td>
                <td>
                  <div align="left">
                
                    <select size="1" name="cmbModule" id="cmbModule">
                            <option value="">-- Select Module Type --</option>
                            
                  </select></div>
                </td>
</tr>  -->  
       
                <tr class="tdH">
              <td colspan="3">
                <div align="center">
                   <input type="button" name="cmdAdd" value="GO" id="cmdAdd" onclick="add_grid()" />
                    <input type="button" id="btnSub" name="btnSub"  value="LIST" onclick="listData();"/>             
                    <!--  <input type="button" name="cmdUpdate" value="UPDATE" id="cmdUpdate" style="display:none" onclick="doFunction('Update','null')"/>
<input type="button" name="cmdList" value="LIST" id="cmdList" onclick="ListHeads()"/>                 
 -->
                    <input type="button" name="cmdClear" value="CLEAR" id="cmdClear" onclick="AllClear();"/>
                    <input type="button" id="Exit" name="Exit" value="EXIT" onclick="self.close()">
                    
                     </div>
                     </td>
            </tr>   
         </table>
                    <div id="grid" style="display:none">
                    <table id="mytable" cellspacing="3" cellpadding="2" border="1" width="100%" >
                     <tr class="tdH" id="tr_grid" > </tr>
                       <tr class="tdH"> 
                     
                      
                           
                            
                             <th>Name of the units</th>
                            <th>Account No</th>
                            <th>Bank Id</th>
                            <th>Branch Id</th>
                            <th>Bank Account Type</th>
                            <th>Bank Account Mode</th>
                             <th>Account Head Code</th>
                            <th>Account Module</th>
                        
                        
        
                
                   
                      </tr> 
                    
                       <tbody id="grid_body" class="table" align="left" >
                       </tbody>
					<tr class="tdH">
                      <td colspan="10" align="center">
					<input type="button" name="submit" value="Submit" id="submit" onclick="UpdateAc_Head();" style="display: inline;" />
					<input type="button" name="Update" value="Update" id="Update" onclick="ModifyAc_Head();" style="display: none;" />
                                        </td></tr>	
                    </table>
                  </div>
    </form>
   
    </body>
</html>

