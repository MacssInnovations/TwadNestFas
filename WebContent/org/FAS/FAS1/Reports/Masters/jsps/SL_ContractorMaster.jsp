<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">
<%@ page session="false"  contentType="text/html;charset=windows-1252"%>
<%@ page import="java.sql.*,java.util.*,Servlets.Security.classes.UserProfile"%>
<%@ include file="//org/Security/jsps/Check_SessionJSPF.jspf" %>
<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=windows-1252"/> 
    <meta http-equiv="cache-control" content="no-cache">
    <title>ContractorMaster</title>
    
     <script type="text/javascript" src="<%=request.getContextPath()%>/org/Library/scripts/comJS.js"></script>
     <link href="../../../../../../css/Sample3.css" rel="stylesheet" media="screen"/>
     
     <script language="javascript" type="text/javascript">
        function closeWindow()
        {                
             window.open('','_parent','');                
             window.close(); 
             window.opener.focus();
        }
     </script>
    </head>
  <body class="table" onload="loadyear_month()" >
  <form action="../../../../../../SubsidiaryLedger_ContractorMaster.con?SubsidiaryLedger_ContractorMaster=SubsidiaryLedger_ContractorMaster" 
  name="ContractorMaster" id="ContractorMaster" method="post" >
  
   <%
  Connection con=null;
  ResultSet rs=null,rs2=null;
  PreparedStatement ps=null,ps2=null;
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
           // ConnectionString = strdsn.trim() + "@" + strhostname.trim() + ":" + strportno.trim() + ":" +strsid.trim() ;
            Class.forName(strDriver.trim());
            con=DriverManager.getConnection(ConnectionString,strdbusername.trim(),strdbpassword.trim());
  }
  catch(Exception e)
  {
    System.out.println("Exception in connection...."+e);
  }
  int  cmbAcc_UnitCode=0,cmbOffice_code=0;

            
  %>
  
  <% 
    
      HttpSession session=request.getSession(false);
      UserProfile empProfile=(UserProfile)session.getAttribute("UserProfile");
      
    System.out.println("user id::"+empProfile.getEmployeeId());
    int empid=empProfile.getEmployeeId();   
    int oid=0;
  /* int empid=10099;
   int empid=7442;
   int empid=2837;
  */
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
                  
    }
    catch(Exception e)
    {
        System.out.println(e);
    }
   
   %>
   
  <table cellspacing="2" cellpadding="3" width="100%" >
      <tr class="tdH">
        <td colspan="2">
          <div align="center">
              <strong>ContractorMaster_Reports</strong>
            </div>
        </td>
      </tr>
   </table>
   <div align="center">
          <table cellspacing="1" cellpadding="2" border="1" width="100%">
             <tr class="table">
                <td>
                  <div align="left">
                    Accounting Unit Code 
                    <font color="#ff2121">*</font>
                  </div>
                </td>
                <td>
                  <div align="left">
                     <select size="1" name="cmbAcc_UnitCode" id="cmbAcc_UnitCode" tabindex="1">
                     <%
                      int unitid=0;
                      String unitname="";
                      try{
                        if(oid==5000)
                        {
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
                <td>
                  <div align="left">
                    <select size="1" name="cmbOffice_code" id="cmbOffice_code" tabindex="2">
                      <%
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
              
               <tr class="table">
                <td>
                  <div align="left">
                    Units As On
                    <font color="#ff2121">*</font>
                  </div>
                </td>
                <td>
                  <div align="left">
                    <input type="text" name="txtCrea_date" id="txtCrea_date"
                           tabindex="3" maxlength="10" size="11"
                           onfocus="javascript:vDateType='3';"
                           onkeypress="return calins(event,this);"
                           onblur="call_date(this);"/>
                     
                   
                  </div>
                </td>
              </tr>
              
              <tr>
                  <td align="left">  Report Option:  </td>
                  <td colspan="3" align="left">
                        <input type=radio name=txtoption id=txtoption value="PDF" checked>PDF
                        <input type=radio name=txtoption id=txtoption value="EXCEL">Excel
                        <input type=radio name=txtoption id=txtoption value="HTML">HTML
                  </td>
              </tr>
          </table>
          </div>
            <table align="center"  cellspacing="3" cellpadding="2" border="1" width="100%" >
              <tr class="tdH">
                <td>
                  <div align="center">
                      <input type=submit value=Submit >
                      <input type="button" id="cmdcancel" name="cancel" value="EXIT" onclick="closeWindow()">
                  </div>
                </td>
              </tr>
           </table>
      </form>
  </body>
</html>