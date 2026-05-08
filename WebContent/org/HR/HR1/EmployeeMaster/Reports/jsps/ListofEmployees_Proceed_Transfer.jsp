<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">
<%@ page contentType="text/html;charset=windows-1252"%>
<%@ page session="false"  contentType="text/html;charset=windows-1252"%>
<%@ page import="java.sql.*,java.util.*,Servlets.Security.classes.UserProfile"%>
<%@ include file="//org/Security/jsps/Check_SessionJSPF.jspf" %>
<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=windows-1252"/>
    <title>Employees Transfer Order</title>
    <script type="text/javascript" src="../scripts/ListofEmployees_Proceed_Transfer.js"> </script>
    <script type="text/javascript"            src="../../../../../org/Library/scripts/checkDate.js"></script>
   <link href="../../../../../../css/Sample3.css" rel="stylesheet"          media="screen"/>
    <link href="../../../../../../css/CalendarControl.css" rel="stylesheet"          media="screen"/>
     <script type="text/javascript" src="../../scripts/CalendarControl.js"></script>
  </head>
  <body>
       <%
  
  Connection con=null;
    ResultSet rs=null;
    PreparedStatement ps=null;
     ResultSet rs2=null;
    PreparedStatement ps1=null;
    ResultSet results=null;
  ResultSet results1=null;
  ResultSet results2=null;
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
            ConnectionString = strdsn.trim() + "://" + strhostname.trim() + ":" + strportno.trim() + "/" +strsid.trim() ;    // Postgres DB  Connection
            //ConnectionString = strdsn.trim() + "@" + strhostname.trim() + ":" + strportno.trim() + ":" +strsid.trim() ;

             Class.forName(strDriver.trim());
             con=DriverManager.getConnection(ConnectionString,strdbusername.trim(),strdbpassword.trim());
             System.out.println("connected");
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
    int  oid=0;
    String oname="",oadd1="",oadd2="",ocity="",odist="",olid="",owid="";
    String olname=""; 
    String ownature="";
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
            ps=con.prepareStatement("select a.OFFICE_NAME,a.OFFICE_ADDRESS1,a.OFFICE_ADDRESS2,a.DISTRICT_CODE,a.CITY_TOWN_NAME,a.OFFICE_LEVEL_ID,a.PRIMARY_WORK_ID,b.DISTRICT_NAME from COM_MST_OFFICES a "+
            " left outer join com_mst_districts b on b.DISTRICT_CODE= a.DISTRICT_CODE where OFFICE_ID=?" );
            ps.setInt(1,oid);
            results=ps.executeQuery();
                 if(results.next()) 
                 {
                    oname=results.getString("OFFICE_NAME");
                    oadd1=results.getString("OFFICE_ADDRESS1");
                    oadd2=results.getString("OFFICE_ADDRESS2");
                    ocity=results.getString("CITY_TOWN_NAME");
                    odist=results.getString("DISTRICT_NAME");
                    
                  }
            results.close();
            ps.close();
     /* */      
                 
    }
    catch(Exception e)
    {
        System.out.println(e);
    }
   
   %>
  
  <form name="frmListEmpProceed"  method="POST">
      <table cellspacing="2" cellpadding="3" border="1" width="100%">
        <tr class="tdH">
            <td colspan="2">
              <div align="center">
                <strong>Generate Draft Proceedings for Transfer Order</strong>
               </div> 
            </td>
          </tr>
        <tr class="tdH">
            <td colspan="2">
              <div align="left">
                <strong>Office Details</strong>
               </div> 
            </td>
          </tr>
              <tr class="table">
            <td>
              <div align="left">Office Name</div>
            </td>
            <td>
              <div align="left">
                <input type="text" name="txtOffName" id="txtOffName" value="<%=oname%>"
                        maxlength="60" size="60"
                       readonly="readonly" class="disab"/>
                <input type="hidden" name="txtOffId" id="txtOffId" value="<%=oid%>"></input>
              </div>
            </td>
          </tr>
          <tr class="table">
            <td>
              <div align="left">Office Address</div>
            </td>
            <td>
              <div align="left">
                <textarea rows="4" cols="40"  name="txtOffAddr" id="txtOffAddr" readonly="readonly"
                class="disab"><%
                String s=null;
                if(oadd1!=null)
                {
                    s=oadd1;
                }
                if(oadd2!=null)
                {
                    s+="\n"+oadd2;
                }
                if(ocity!=null)
                {
                    s+="\n"+ocity;
                }
                if(odist!=null)
                {
                    s+="\n"+odist;
                }
                if(s!=null)
                    out.print(s);   
                                
                %></textarea>
             
              </div>
            </td>
          </tr>
            <tr class="tdH">
            <td colspan="2">
            <div align="left">
                <strong>Proceedings Details</strong>
               </div> 
            </td>
          </tr>
          <tr class="table">
            <td>
              <div align="left">
                Proceeding No
                <font color="#ff2121">*</font>
              </div>
            </td>
            <td>
              
               <!-- <input type="text" name="txtPNo" id="txtPNo"  maxlength="50"
                       size="20" />                       -->
                          
            <select size="1" name="cmbProceed" id="cmbProceed" onchange="loadServer('loadEmp')">
            <option value="">--Select Proceedings Number--</option>
            <%
                try
                {
                ps=con.prepareStatement("select distinct Proceeding_no from HRM_EMP_RELIEVAL_DETAILS where process_flow_status_id !='FR' and Proceeding_no is not null");
                rs=ps.executeQuery();
                    while(rs.next())
                    {
                    out.println("<option value="+rs.getString("Proceeding_no")+">"+rs.getString("Proceeding_no")+"</option>");
                    }
                }
                catch(Exception e)
                {
                System.out.println("Exception in Reason combo..."+e);
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
          <td colspan="2">
            <table cellspacing="3" cellpadding="2" border="1" width="100%">
              <tr class="tdH">
                <td>
                Select All <input type="checkbox" name="chkall" onclick="sellectall()"><br/>
                     <a href="javascript:inverseselect()">Inverse&nbsp;Select</a>
                </td>
                <td>Employee Id</td>
                <td>Employee Name</td>
                <td>Designation</td>
                <td>Transferred To</td>
                <td>Record Status</td>
              </tr>
              <tbody id="grid_body">
                           
              </tbody>
              
              
     <tr class="tdH">
      <td colspan="6">
       <table align="center"  cellspacing="3" cellpadding="2" border="0" width="100%" >
       <tr >
       <td width="30%">
          <div align="left"> <div id="divpre" style="display:none" ></div> </div>
      </td>
       <td width="40%">
          <div align="center"><table border="0"><tr><td> <div id="divcmbpage" style="display:none">Page&nbsp;&nbsp;<select name="cmbpage" id="cmbpage" onchange="changepage()"></select></div></td><td><div id="divpage"></div></td></tr></table> </div>
      </td>
      <td width="30%">
          <div align="right"> <div id="divnext" style="display:none"></div> </div>
      </td>
      </tr>
      </table>
      </tr>  
           
           
            </table>
          </td>
        </tr>
        <tr class="tdH">
                      <td colspan="5">
                      <div align="left">
                        <strong>Additional Proceedings Details</strong>
                       </div> 
                    </td>
                  </tr> 
       <tr class="table">
           <td>
              <div align="left">
                Proceeding Date
                <font color="#ff2121">*</font>
              </div>
            </td>
          <td>
              <div align="left">
               <input type="text" name="txtPDat" id="txtPDat"
                       maxlength="10" size="11"
                       onfocus="javascript:vDateType='3'; "
                       onkeypress="return calins(event,this);" 
                       onblur="if(checkcurdt(this)==true)return checkdt(this);"/>
             
                     
                     <img src="../../../../../../images/calendr3.gif"
                     onclick="showCalendarControl(document.frmListEmpProceed.txtPDat);"
                     alt="Show Calendar"></img>
                     
                     </div>
             </td>
            </tr>
            
            <tr class="table">
            <td>
              <div align="left">
                Presiding Officer
                <font color="#ff2121">*</font>
              </div>
            </td>
            <td>
              <div align="left">
                <input type="text" name="txtPO" id="txtPO"
                       size="50" />                       
              </div>
            </td>
          </tr>
          <tr class="table">
            <td>
              <div align="left">
                Presiding Officer Designation
                <font color="#ff2121">*</font>
              </div>
            </td>
            <td>
              <div align="left">
                <input type="text" name="txtPODesig" id="txtPODesig" 
                       size="50" />                       
              </div>
            </td>
          </tr>
          
          <tr class="table">
          
            <td><div align="left">Subject</div></td>
            <td><div align="left"><textarea name="txtSubject"   title="Don't type '&' Character while entering data" id="txtSubject" cols="50" rows="2" onkeypress="return noEnter(event)"></textarea></div></td>
            
          </tr>
          <tr class="table">
            <td><div align="left">Reference</div></td>
            <td><div align="left"><textarea name="txtRef" id="txtRef"  title="Don't type '&' Character while entering data" cols="50" rows="2" onkeypress="return noEnter(event)"></textarea></div></td>
          </tr>
          <tr class="table">
            <td><div align="left">Additional Para1</div></td>
            <td><div align="left"><textarea name="txtPara1" id="txtPara1"  title="Don't type '&' Character while entering data" cols="50" rows="4" onkeypress="return noEnter(event)"></textarea></div></td>
          </tr>
          <tr class="table">
            <td><div align="left">Additional Para12</div></td>
            <td><div align="left"><textarea name="txtPara2" id="txtPara2"  title="Don't type '&' Character while entering data" cols="50" rows="4" onkeypress="return noEnter(event)"></textarea></div></td>
          </tr>
          <tr class="table">
            <td>
              <div align="left">
                Copy To
                <!--font color="#ff2121">*</font-->
              </div>
            </td>
            <td>
              <div align="left">
                <textarea name="txtCopy" id="txtCopy"  title="Don't type '&' Character while entering the remarks" cols="40" rows="5" onkeypress="return noEnter(event)"></textarea>
              </div>
            </td>
          </tr>
          <!--tr class="tdH">
            <td colspan="2">
              <div align="center">
                <input type="button" name="butSave" id="butSave" value="Save" onclick="loadServer('save')" />
                 &nbsp;&nbsp;&nbsp; 
                <input type="button" name="butCan" id="butCan" value="Cancel"
                       onclick="javascript:self.close();"/>
              </div>
            </td>
          </tr-->
       
        
        <tr class="tdH">
          <td colspan="2">
            <div align="center">
              <input type="submit" name="btnSubmit" value="Generate Report" onclick="return btnsubmit()"/>
               <input type="button" name="button" value="Clear All" onclick="return clearAll()"/>
                <input type="button" name="button" value="Cancel" onclick="self.close()"/>
            </div></td>
        </tr>
      </table>
      <input type="hidden" name="txtseloff">
    </form></body>
</html>