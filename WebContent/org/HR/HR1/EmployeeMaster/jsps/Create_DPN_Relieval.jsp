<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">

<%@ page session="false"  contentType="text/html;charset=windows-1252"%>
<%@ page import="java.sql.*,java.util.*,Servlets.Security.classes.UserProfile"%>
<%@ include file="//org/Security/jsps/Check_SessionJSPF.jspf" %>
<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=windows-1252"/>
    <title>Creation Of Deputation Completion Details</title>
     <script type="text/javascript" src="<%=request.getContextPath()%>/org/Library/scripts/comJS.js"></script>
    <script type="text/javascript" src="../scripts/Emp_DPNRelieval_Script.js"> </script>
    <script type="text/javascript"            src="../../../../../org/Library/scripts/checkDate.js"></script>
   <!-- <script type="text/javascript" src="../scripts/CalendarCtl_Relieval.js"></script>-->
     <link href="../../../../../css/Sample3.css" rel="stylesheet"          media="screen"/>
    <link href="../../../../../css/CalendarControl.css" rel="stylesheet"        media="screen"/>
      <script type="text/javascript" src="../scripts/CalendarControl.js"></script>
  </head>
  <body  id="bodyid"><form name="EmpRelieval" id="EmpRelieval" method="POST"
              action="../../../../../Create_Relieval_ReportServ.view?Command=Add&cmbStatus=CR"
              onsubmit="return checkNull()">
      <%
  
  Connection con=null;
    ResultSet rs=null;
    PreparedStatement ps=null;
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
  
      <div align="center">
        <table cellspacing="3" cellpadding="2" border="1" width="100%">
          <tr class="tdH">
            <td colspan="2">
              <div align="center">
                <strong> Creation Of Deputation Completion Details</strong>
              </div>
            </td>
          </tr>
          <tr class="tdTitle">
            <td colspan="2">
              <div align="left">
                <strong>Office Details</strong>
              </div>
            </td>
          </tr>
          <tr class="table">
            <td>
              <div align="left">
                Office ID 
              </div>
            </td>
            <td>
              <div align="left">
                <input type="text" name="txtOffId" id="txtOffId" maxlength="4" value="<%=oid%>"
                        size="5" class="disab"  readonly="readonly"/>
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
          <tr class="tdTitle">
            <td colspan="2">
              <div align="left">
                <strong>Employee Details</strong>
              </div>
            </td>
          </tr>
          <tr class="table">
            <td>
              <div align="left">
                Employee ID 
                <font color="#ff2121">*</font>
              </div>
            </td>
            <td>
              <div align="left">
                <input type="text" name="txtEmployeeid" id="txtEmployeeid"
                       maxlength="5" size="6"
                       onkeypress="return numbersonly1(event,this);"
                       onchange="doFunction('loademp','null');"/>
                 
                <img src="../../../../../images/c-lovi.gif" width="20"
                     height="20" alt="empList" onclick="servicepopup();"></img>
              </div>
            </td>
          </tr>
          <tr class="table">
            <td>
              <div align="left">Employee Name</div>
            </td>
            <td>
              <div align="left">
                <input type="text" name="txtEmpName" id="txtEmpName"
                       readonly="readonly" class="disab" maxlength="40"
                       size="40"/>
              </div>
            </td>
          </tr>
         <!-- <tr class="table">
            <td>
              <div align="left">Designation</div>
            </td>
            <td>
              <div align="left">
                <input type="text" name="txtEmpDesig" id="txtEmpDesig"
                       readonly="readonly" class="disab" maxlength="40"
                       size="40"/>
              </div>
            </td>
          </tr>
          -->
          <tr class="tdTitle">
            <td colspan="2">
              <div align="left">
                <strong>Deputation Joining Details</strong>
              </div>
            </td>
          </tr>
          <tr class="table">
            <td>
              <div align="left">
                Deputation Joining Date
                
              </div>
            </td>
            <td>
              <div align="left">
                <input type="text" name="txtDepDoj" id="txtDepDoj"
                       readonly="readonly" class="disab" maxlength="10" size="10"/>
              </div>
            </td>
          </tr>
          <tr class="table">
            <td>
              <div align="left">
                Other Department Name
               
              </div>
            </td>
            <td>
              <div align="left">
                <input type="text" name="txtDepName" id="txtDepName"
                         readonly="readonly" class="disab" maxlength="30" size="30"/>
               
              </div>
            </td>
          </tr>
          <tr class="table">
            <td>
              <div align="left">
                Other Office Name 
              </div>
            </td>
            <td>
              <div align="left">
                <input type="text" name="txtDepOffName" id="txtDepOffName"
                         readonly="readonly" class="disab" maxlength="30" size="30"/>
               
              </div>
            </td>
          </tr>
        
          
          
          <tr class="tdTitle">
            <td colspan="2">
              <div align="left">
                <strong>Deputation Completion Details</strong>
              </div>
            </td>
          </tr>
          <tr class="table">
            <td>
              <div align="left">
                Serial&nbsp;Number 
              </div>
            </td>
            <td>
              <div align="left">
                <input type="text" name="txtRel_SLNO" id="txtRel_SLNO"
                       readonly="readonly" class="disab" maxlength="5"
                       size="6"/>
                 (System Generated)
              </div>
            </td>
          </tr>
          <tr class="table">
            <td>
              <div align="left">
                Date Of Deputation&nbsp;Completion  
                <font color="#ff2121">*</font>
              </div>
            </td>
            <td>
              <div align="left">
                <input type="text" name="txtDORelieval" id="txtDORelieval"
                       maxlength="10" size="11"
                       onfocus="javascript:vDateType='3';return checkEID();"
                       onkeypress="return calins(event,this);"
                       onblur="if(checkcurdt(this)==true)return checkdt(this);"/>
                 
                <img src="../../../../../images/calendr3.gif"
                     onclick=" if(checkEID()==true)showCalendarControl(document.EmpRelieval.txtDORelieval);"
                     alt="Show Calendar"></img>
                 
                <input type="radio" name="rad_DORelieval" id="rad_DORelieval"
                       value="FN" checked="checked" />FN &nbsp;&nbsp;&nbsp;&nbsp; 
                <input type="radio" name="rad_DORelieval" id="rad_DORelieval"
                       value="AN" />AN
              </div>
            </td>
          </tr>
         
          <tr class="table">
            <td>
              <div align="left">Remarks</div>
            </td>
            <td>
              <div align="left">
                <textarea name="txtRemarks" id="txtRemarks" title="Don't type '&' Character while entering the remarks" cols="50" rows="4" onkeypress="return noEnter(event)"></textarea>
              </div>
            </td>
          </tr>
          <tr class="tdH">
            <td colspan="2">
              <div align="center">
                <input type="SUBMIT" name="butSub" id="butSub" value="SUBMIT"/>
                 &nbsp;&nbsp;&nbsp; 
                <input type="button" name="butCan" id="butCan" value="CANCEL"
                       onclick="javascript:self.close();"/>
              </div>
            </td>
          </tr>
        </table>
      </div>
    </form></body>
</html>