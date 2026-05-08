<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">
<%@ page contentType="text/html;charset=windows-1252"%>
<%@ page session="false" contentType="text/html;charset=windows-1252"%>
<%@ page import="java.sql.*,java.util.*,Servlets.Security.classes.UserProfile"%>
<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=windows-1252"/>
    <title>Emp_Family_NewJSP</title>
     <script type="text/javascript" src="../scripts/comJS.js"></script>
    <script type="text/javascript"       src="../scripts/checkDate.js"></script>
  
    <script type="text/javascript" src="../scripts/NominAjaxOfficeContactId.js"></script>
    <!-- <script type="text/javascript" src="../scripts/controllingOfficeContact.js"></script>-->
    <script type="text/javascript"     src="../scripts/Emp_FamilyJS.js"></script>
    <%
     HttpSession session=request.getSession(false);
     
     %>
    <link href="../../../../../css/Sample3.css" rel="stylesheet"         media="screen"/>
    <link href="../../../../../css/CalendarControl.css" rel="stylesheet"         media="screen"/>
    
      <script type="text/javascript" src="../scripts/CalendarControl.js"></script>
  </head>
 
 <body id="bodyid" onload="doFunction('efocus','null');">
 


  <form name="EMP_FAMILY" id="EMP_FAMILY">
      <p>
        <%
  
  Connection connection=null;
  PreparedStatement ps=null;
  ResultSet results=null;
  ResultSet results1=null;
  ResultSet results2=null;
  
  
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

            //ConnectionString = strdsn.trim() + "@" + strhostname.trim() + ":" + strportno.trim() + ":" +strsid.trim() ;
					ConnectionString = strdsn.trim() + "://" + strhostname.trim() + ":" + strportno.trim() + "/" +strsid.trim() ;    // Postgres DB  Connection
             Class.forName(strDriver.trim());
             connection=DriverManager.getConnection(ConnectionString,strdbusername.trim(),strdbpassword.trim());
  }
  catch(Exception e)
  {
    System.out.println("Exception in connection...."+e);
  }
  
  
  %>
      </p>
    
      <div align="center">
        <table width="100%">
          <tr>
            <td>
              <table cellspacing="2" cellpadding="3" border="0" width="100%" class="table">
                <tr class="tdH">
                  <th align="center" colspan="2" >
                    <div align="center">
                      EMPLOYEE'S FAMILY DETAILS 
                    </div>
                  </th>
                </tr>
                <!-- OFFICE DETAILS -->
                <% 
         session=request.getSession(false);
      UserProfile empProfile=(UserProfile)session.getAttribute("UserProfile");
      
    System.out.println("user id::"+empProfile.getEmployeeId());
    int empid=empProfile.getEmployeeId();
  // int empid=11263;
    int  oid=0;
    String oname="",oadd1="",oadd2="",ocity="",olid="",owid="",odist="";
    String olname=""; 
    String ownature="",deptid="",type="";
    String fulladd="";
    try
    {
           
            ps=connection.prepareStatement("select OFFICE_ID,DEPARTMENT_ID from HRM_EMP_CURRENT_POSTING where EMPLOYEE_ID=?" );
            ps.setInt(1,empid);
            results=ps.executeQuery();
                 if(results.next()) 
                 {
                    oid=results.getInt("OFFICE_ID");
                    deptid=results.getString("DEPARTMENT_ID");
                 
                 }
            results.close();
            ps.close();
            System.out.println("office id:"+oid);
            System.out.println("dept id:"+deptid);
            if( deptid==null || deptid.equalsIgnoreCase("TWAD"))
            {
                    type="(TWAD)";
                    ps=connection.prepareStatement("select a.OFFICE_NAME,a.OFFICE_ADDRESS1,a.OFFICE_ADDRESS2,a.CITY_TOWN_NAME,a.OFFICE_LEVEL_ID,a.PRIMARY_WORK_ID,d.DISTRICT_NAME  from COM_MST_OFFICES a left outer join com_mst_districts d on d.DISTRICT_CODE=a.DISTRICT_CODE  where a.OFFICE_ID=?" );
                    ps.setInt(1,oid);
                    results=ps.executeQuery();
                         if(results.next()) 
                         {
                            oname=results.getString("OFFICE_NAME");
                            oadd1=results.getString("OFFICE_ADDRESS1");
                            if(oadd1==null)oadd1="";
                            oadd2=results.getString("OFFICE_ADDRESS2");
                            if(oadd2==null)oadd2="";
                            ocity=results.getString("CITY_TOWN_NAME");
                            if(ocity==null)ocity="";
                            olid=results.getString("OFFICE_LEVEL_ID");
                            if(olid==null)olid="";
                            owid=results.getString("PRIMARY_WORK_ID");
                            odist =results.getString("DISTRICT_NAME");
                            if(odist==null)odist="";
                            
                            if(oadd1.length()>0)
                                fulladd=oadd1;
                            if(oadd2.length()>0)
                                fulladd+="\n"+oadd2;
                            if(ocity.length()>0)
                                fulladd+="\n"+ocity;
                            if(odist.length()>0)
                                fulladd+="\n"+odist;
                            
                            System.out.println("Full address:"+fulladd);
                            
                            
                          }
                          //System.out.println("office name:"+oname);
                    results.close();
                    ps.close();
                  
                    ps=connection.prepareStatement("select OFFICE_LEVEL_NAME from COM_MST_OFFICE_LEVELS where OFFICE_LEVEL_ID=?" );
                    ps.setString(1,olid);
                    results=ps.executeQuery();
                         if(results.next()) 
                         {
                            olname=results.getString("OFFICE_LEVEL_NAME");
                            
                          }
                    results.close();
                    ps.close();
                    
                    ps=connection.prepareStatement("select WORK_NATURE_DESC from COM_MST_WORK_NATURE where WORK_NATURE_ID=?" );
                    ps.setString(1,owid);
                    results=ps.executeQuery();
                         if(results.next()) 
                         {
                            ownature=results.getString("WORK_NATURE_DESC");
                            
                          }
                    results.close();
                    ps.close();
            }
            else
            {
                System.out.println("other");
                System.out.println("off id::"+oid);
                    type="(OTHER="+deptid+")";
                    String otherdeptid="";
                    ps=connection.prepareStatement("select OTHER_DEPT_ID,OTHER_DEPT_OFFICE_NAME,ADDRESS1,ADDRESS2,CITY_TOWN from HRM_MST_OTHER_DEPT_OFFICES where OTHER_DEPT_OFFICE_ID=?" );
                    ps.setInt(1,oid);
                    results=ps.executeQuery();
                         if(results.next()) 
                         {
                            oname=results.getString("OTHER_DEPT_OFFICE_NAME");
                            System.out.println("oname::"+oname);
                            oadd1=results.getString("ADDRESS1");
                            if(oadd1==null)oadd1="";
                            oadd2=results.getString("ADDRESS2");
                            if(oadd2==null)oadd2="";
                            ocity=results.getString("CITY_TOWN");
                             if(ocity==null)ocity="";
                            otherdeptid=results.getString("OTHER_DEPT_ID");
                           // owid=results.getString("PRIMARY_WORK_ID");
                            if(oadd1.length()>0)
                                fulladd=oadd1;
                            if(oadd2.length()>0)
                                fulladd+="\n"+oadd2;
                            if(ocity.length()>0)
                                fulladd+="\n"+ocity;
                          }
                    results.close();
                    ps.close();
                  
                    ps=connection.prepareStatement("select OTHER_DEPT_NAME from HRM_MST_OTHER_DEPTS where OTHER_DEPT_ID=?" );
                    ps.setString(1,otherdeptid);
                    results=ps.executeQuery();
                         if(results.next()) 
                         {
                            olname=results.getString("OTHER_DEPT_NAME");
                            
                          }
                    results.close();
                    ps.close();
                    
                  
            }
            
     /* */      
                 
    }
    catch(Exception e)
    {
        System.out.println(e);
    }
   
   %>
                <tr class="table">
                  <td width="39%">
                    <div align="left">
                      <font color="#808080">Office ID</font>
                    </div>
                  </td>
                  <td width="61%">
                    <div align="left">
                      <font color="#808080">
                        <input type="text" name="txtCOffice_ID"
                               id="txtCOffice_ID" value="<%=oid%>"
                               style="background-color: #FFFFFF"
                               disabled="disabled"  readonly="readonly"></input>
                      </font>
                    </div>
                  </td>
                </tr>
                <tr class="table">
                  <td width="39%">
                    <div align="left">
                      <font color="#808080">Office Name</font>
                    </div>
                  </td>
                  
            <td>
              <div align="left">
                <input type="text" name="txtOffName" id="txtOffName" value="<%=oname%>"
                        maxlength="60" size="60"
                       readonly="readonly" disabled="disabled" style="background-color: #FFFFFF" />
                <input type="hidden" name="txtOffId" id="txtOffId" value="<%=oid%>"></input>
              </div>             
                                    
               </tr>
                <tr class="table">
                  <td width="39%">
                    <div align="left">
                      <font color="#808080">Office Address</font>
                    </div>
                    <div align="left"/>
                  </td>
                  <td width="61%">
                    <div align="left">
                      <textarea name="txtCOffice_Address1" rows="4" cols="40"
                             id="txtCOffice_Address1"  
                             style="background-color: #FFFFFF"  readonly="readonly"
                             disabled="disabled" ><%=fulladd%></textarea>
                    </div>
                  </td>
                </tr>
            
                <!-- OFFICE DETAILS  -->
                <tr class="tdH">
                  <th colspan="2">
                    <div align="left">
                      Employee Details
                    </div>
                  </th>
                </tr>
                <tr class="table">
                  <td width="39%">
                    <div align="left">Employee ID</div>
                  </td>
                  <td width="61%">
                    <div align="left">
                      <input type="text" name="txtEmployeeid" id="txtEmployeeid"
                             maxlength="5" size="5"
                             onchange=" doFunction('loademp','null') "
                             onkeypress="return numbersonly1(event,this);"/>
                             <img src="../../../../../images/c-lovi.gif" width="20" height="20" alt="empList" onclick="servicepopup();">
                    </div>
                  </td>
                </tr>
     <tr class="table">
                  <td width="39%">
                    <div align="left">
                      <font color="#808080">Employee Name</font>
                    </div>
                  </td>
                  <td width="61%">
                    <div align="left">
                      <input type="text" name="txtEmployee" id="txtEmployee"
                             style="TEXT-TRANSFORM:UPPERCASE; background-color: #ffffff"
                             maxlength="60" size="40" disabled="disabled"/>
                    </div>
                  </td>
                </tr>
                <tr class="table">
                  <td width="39%">
                    <div align="left">
                      <font color="#808080">Date of Birth</font>
                    </div>
                  </td>
                  <td width="61%">
                    <div align="left">
                      <input type="text" name="txtdob" id="txtdob"
                             maxlength="10" size="10" disabled="disabled"
                             style="TEXT-TRANSFORM:UPPERCASE; background-color: #ffffff"/>
                       
                    </div>
                  </td>
                </tr>
                <tr class="table">
                  <td width="39%">
                    <div align="left">
                      <font color="#808080">GPF No.</font>
                    </div>
                  </td>
                  <td width="61%">
                    <div align="left">
                      <input type="text" name="txtGpf" id="txtGpf"
                             maxlength="10" size="10" disabled="disabled"
                             style="TEXT-TRANSFORM:UPPERCASE; background-color: #ffffff"/>
                             <input type="hidden" name="gender" id="gender"/>
                    </div>
                  </td>
                </tr>
                <tr class="tdH">
                  <th colspan="2">
                    <div align="left">
                      Family Details
                    </div>
                  </th>
                </tr>
                           
                  <tr class="table">
                  <td width="39%">
                    <div align="left">
                       Serial No
                    </div>
                  </td>
                  <td width="61%">
                    <div align="left">
                      <input type="text" name="txtSNo" id="txtSNo" maxlength="5"        size="5" readonly="readonly"
                             style="background-color: #ececec"/>&nbsp;(System
                                                                Generated)
                                            </div>
                  </td>
                </tr>
                <tr class="table">
                  <td width="39%">
                    <div align="left">
                      Family&nbsp;Member Name
                    </div>
                  </td>
                  <td width="61%">
                    <div align="left">
                      <input type="text" name="Member_Name" maxlength="30"
                             size="30" onclick="checkempid()" onkeypress="return  ischarValid(event,this)" />
                    </div>
                  </td>
                </tr>
                <tr class="table">
                  <td width="39%">
                    <div align="left">
                      Relationship
                    </div></td>
                  <td width="61%">
                    <div align="left">
                      <select size="1" name="Relationship" id="Relationship" onclick="checkmember()" onchange="chkappliable()" >
                        <option value=0>- Select the Relationship -</option>
                      </select>
                    </div>
                    
                  </td>
                </tr>
                <tr class="table" >
                    <td width="39%">
                        <div align="left" id="spouse" style="display:none">
                            Spouse Working Category
                        </div>
                    </td>
                    <td width="61%">
                        <div align="left" id="spouse_det" style="display:none">
                            <input type="radio" name="working_category" >Working in Government
                            <input type="radio" name="working_category" >Working in Private
                            <input type="radio" name="working_category" >Not Working
                        </div>
                </tr>
                 <tr class="table">
                    <td width="39%">
                        <div align="left" id="handicapt" style="display:none">
                            Is Physically Handicapted
                        </div>
                    </td>
                    <td width="61%">
                        <div align="left" id="handicapt_det" style="display:none">
                            <input type="radio" name="is_handicapt" >Yes
                            <input type="radio" name="is_handicapt" >No
                        </div>
                </tr>
                <tr class="table">
                <td width="39%">
                <div align="left" style="display:none" id="marry" >
                Married ?
                </div>
                </td>
                <td width="61%">
                <div align="left"  style="display:none" id="marry_det" >
                 Yes<input type="radio" name="married" value="Yes"
                              onclick="radioclick()"/>
                No<input type="radio" name="married" value="No"
                             checked />
                 </div>            
                </td>
                
                </tr>
                <tr class="table">
                  <td width="39%">
                    <div align="left">
                      Date of Birth/Age
                    </div>
                  </td>
                  <td width="61%">
                    <div align="left">
                    <input type="radio" name="radioage" value="Yes" id="radioage"
                             checked="radioclick1checked" onclick="radioclick()"/>
                     <input type="text" name="DOB" id="DOB"
                             maxlength="10" size="10"
                             onfocus="javascript:vDateType='3';return checkempid();"
                             onblur="return checkdt(this);"
                             onkeypress="return calins(event,this);" onclick="checkrelation()"/>
                       
                      <img id="fromimg" name="fromimg" src="../../../../../images/calendr3.gif"
                           onclick=" if(checkradio()==true)showCalendarControl(document.EMP_FAMILY.DOB);"
                           alt="Show Calendar" ></img>
                       
                        <div  id=divage style="display:block">   
                           <input type="radio" name="radioage" value="No" id="radioage"
                             onclick="radioclick1()" />

                        
                        Age
                        <input type="text" name="age" maxlength="2" size="3" disabled=true onfocus="checkrelation()" onkeypress="return numbersonly1(event,this);">
                      </div>
                     
                    </div>
                  </td>
                </tr>
                <tr class="table">
                  <td width="39%">
                    <div align="left">
                    Dependent
                    </div>
                  </td>
                  <td width="61%">
                    <div align="left">
                      <input type="radio" name="Dependent" value="Yes"
                             checked="checked" onchange="checkage()"/>Yes &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;  <input type="radio"
                                                       name="Dependent"
                                                       value="No" onchange="checkage()"/> No
                    </div>
                  </td>
                </tr>
                  <tr class="table">
                  <td width="39%">
                    <div align="left">
                       Address1
                    </div>
                  </td>
                  <td width="61%">
                    <div align="left">
                      <input type="text" name="address1" maxlength="50"
                             size="50"/>
                       
                     
                     
                    </div>
                  </td>
                </tr>
                <tr class="table">
                  <td width="39%">
                    <div align="left">
                      Address2
                    </div>
                  </td>
                  <td width="61%">
                    <div align="left">
                            
               
               <input type="text" name="address2"
                               maxlength="50" size="50"/>
                             
                    </div>
                  </td>
                </tr>
                <tr class="table">
                  <td width="39%">
                    <div align="left">
                       Address3
                    </div>
                  </td>
                  <td width="61%">
                    <div align="left">
                      
                      <input type="text" name="address3"
                                      maxlength="50" size="50"/>
                    </div>
                  </td>
                </tr>
                <tr class="table">
                  <td width="39%">
                    <div align="left">
                      Pincode
                    </div></td>
                  <td width="61%">
                    <div align="left">
                      <input type="text" name="pincode" maxlength="6"
                             size="6"   onkeypress="return numbersonly1(event,this);" onblur="checkPin()" onclick="checkage()"/>
                    </div>
                  </td>
                </tr>
              
                <tr class="table">
                  <td width="39%">
                    <div align="left">
                      Remarks
                    </div>
                  </td>
                  <td width="61%">
                    <div align="left">
                            <textarea name="Remarks" rows="4" cols="40"
                             id="Remarks"  
                             style="background-color: #FFFFFF"  >
                      
                             </textarea>
                    </div>
                  </td>
                </tr>
                 <tr class="table">
                  <td colspan="2">
                    <div align="center">
                    <table border="0">
                    <tr>
                        <td>
                      <input type="button" name="cmdadd" value="Add" id="cmdadd"
                             onclick="doFunction('Add','null'); "  style="display:block"/>
                       </td> <td>
                      <input type="button" name="cmdupdate" value="UPDATE"
                             id="cmdupdate"
                             onclick="doFunction('Update','null')"
                             style="display:none"/>
                       </td> 
                        
                       
                       <td>
                      <input type="button" name="cmddelete" value="DELETE"
                             id="cmddelete"
                             onclick="doFunction('Delete','null')"
                             disabled="disabled" />
                       </td> <td>
                      <input type="button" name="cmdclear" value="CLEAR ALL"
                             id="cmdclear"
                             onclick="doFunction('Clear','null')"/>
                       </td> 
                       <td>
                        <input type="Button"
                                                             value=" Exit "
                                                             name="cmdCancel"
                                                             onclick="self.close();"></input>
                          </td>                                   
                    
                            
                            
                        </tr>
                    </table>
                    </div>
                  </td>
                </tr>
              </table>
              <table id="mytable" align="center" cellspacing="3" cellpadding="2"
                     border="1" width="100%">
                <tr class="tdH">
                  <th align="LEFT" colspan="15">Existing Family Details</th>
                </tr>
                <tr class="tdH">
                  <th>Select</th>
                   <th>
                  SL.NO
                  </th>
                  <th>
                 Family Member Name
                  </th>
                  <th>
                   Relationship
                  </th>
                  <th>
                  Working Category
                  </th>
                  <th>
                  Is Handicapted
                  </th>
                  <th>
                    Married
                    </th>
                  <th>
                    Date of Birth
                    </th>
                     <th>
                    Age
                    </th>
                  <TH>
                  Dependent
                  </TH>
                   <th>
                    ADDRESS1
                  </th>
                  <th>
                   ADDRESS2
                  </th>
                  <th>
                    ADDRESS3
                  </th>
                  <TH>
                  PINCODE
                  </TH>
                  <TH>Remarks</TH>
                 
                  
                </tr>
                <tbody id="tb" class="table" >
                </tbody>
              </table>
              <table align="center" cellspacing="3" cellpadding="2" border="1"
                     width="100%">
                     <tr class="tdH">
                  <td>
                   <table align="center" cellspacing="3" cellpadding="2"
                           border="0" width="100%">
                      <tr>
                        <td width="30%">
                          <div align="left">
                            <div id="divpre" style="display:none"></div>
                          </div>
                        </td>
                        <td width="40%">
                          <div align="center">
                            <table border="0">
                              <tr>
                                <td>
                                  <div id="divcmbpage" style="display:none">
                                    Page&nbsp;&nbsp;<select name="cmbpage"
                                                            id="cmbpage"
                                                            onchange="changepage()"></select>
                                  </div>
                                </td>
                                <td>
                                  <div id="divpage"></div>
                                </td>
                              </tr>
                            </table>
                          </div>
                        </td>
                        <td width="30%">
                          <div align="right">
                            <div id="divnext" style="display:none"></div>
                          </div>
                        </td>
                      </tr>
                    </table>
                  </td>
                </tr>
                 </table>
            </td>
          </tr>
        </table>
      </div>
    </form></body>
</html>