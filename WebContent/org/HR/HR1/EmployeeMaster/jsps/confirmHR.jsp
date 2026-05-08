<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">
<%@ page session="false" contentType="text/html;charset=windows-1252"%>
<%@ page import="java.sql.*,java.util.*,Servlets.Security.classes.UserProfile"%>
<%@ include file="//org/Security/jsps/Check_SessionJSPF.jspf" %>

<html>
  <head>
  
      <script type="text/javascript" src="<%=request.getContextPath()%>/org/Library/scripts/comJS.js"></script>
      <script type="text/javascript" src="../../../../Library/scripts/checkDate.js"></script>
      <script type="text/javascript"       src="../scripts/CalendarControl.js"></script>
      <script type="text/javascript" src="../../../../Security/scripts/tabpane.js"></script>
      <script type="text/javascript" src="../scripts/confirmHr.js"></script>
      <meta http-equiv="Content-Type" content="text/html; charset=windows-1252">
      <meta http-equiv="cache-control" content="no-cache">
      <title>Confirmation on HR details Updated</title>       
          
          <script language="javascript" type="text/javascript">
                function closeWindow()
                {                
                    window.open('','_parent','');                
                    window.close(); 
                    window.opener.focus();
                    
                    document.frmTransfer.txthPid.value=0;
                }
          </script> 
             <link href="../../../../../css/CalendarControl.css" rel="stylesheet" media="screen"/> 
          <link href='../../../../../css/RWS_CSSColour.css' rel='stylesheet' media='screen'/>
          <link href='../../../../../css/Sample3.css' rel='stylesheet' media='screen'/>
          
          <style type="text/css">
                .divClass{display: none;  }                                
          </style>
    </head>
 <body onLoad=callServer('load','null') > 
 <%
   Connection connection=null;
   Statement statement=null;
   ResultSet results=null;   

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

       
       try
       {
            statement=connection.createStatement();
            connection.clearWarnings();
       }
       catch(SQLException e)
       {
              System.out.println("Exception in creating statement:"+e);
              return;
       }          
  }
  catch(Exception e)
  {         
         System.out.println("Exception in openeing connection:"+e);
         return;
  }  
 %>  
 <%
          
          PreparedStatement ps=null;
          ResultSet rs1=null;
          
          PreparedStatement ps3=null;
          ResultSet rs3=null;
 
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
           
            ps=connection.prepareStatement("select OFFICE_ID from HRM_EMP_CURRENT_POSTING where EMPLOYEE_ID=?" );
            ps.setInt(1,empid);
            rs1=ps.executeQuery();
                 if(rs1.next()) 
                 {
                    oid=rs1.getInt("OFFICE_ID");
                 
                 }
            rs1.close();
            ps.close();
            ps=connection.prepareStatement("select a.OFFICE_NAME,a.OFFICE_ADDRESS1,a.OFFICE_ADDRESS2,a.DISTRICT_CODE,a.CITY_TOWN_NAME,a.OFFICE_LEVEL_ID,a.PRIMARY_WORK_ID,b.DISTRICT_NAME from COM_MST_OFFICES a "+
            " left outer join com_mst_districts b on b.DISTRICT_CODE= a.DISTRICT_CODE where OFFICE_ID=?" );
            ps.setInt(1,oid);
            rs1=ps.executeQuery();
                 if(rs1.next()) 
                 {
                    oname=rs1.getString("OFFICE_NAME");
                    oadd1=rs1.getString("OFFICE_ADDRESS1");
                    oadd2=rs1.getString("OFFICE_ADDRESS2");
                    ocity=rs1.getString("CITY_TOWN_NAME");
                    odist=rs1.getString("DISTRICT_NAME");
                    
                  }
            rs1.close();
            ps.close();
       
                 
           }
           catch(Exception e)
           {
             System.out.println(e);
           }
  
  %>
        <table  cellspacing="1" cellpadding="3" width="100%" class="table">
                    <tr>
                        <td class="tdH">
                            <center>                           
                            <h3>Confirmation on HR details updated</h3>                            
                            </center>
                        </td>
                    </tr>                    
              </table>
        <form name="frmconfirm" id="frmconfirm" method="POST" action="../../../../../Confirm_OrderServ?Command=Insert">                
                        <div align="center">
                            <table  cellspacing="3" cellpadding="1"  width="100%">
                                  
                                  <tr class="tdTitle">
                                    <td colspan="2">
                                     <div align="left">
                                       <strong>Office Details</strong>
                                     </div>
                                    </td>
                                   </tr>
                                   
                                   <tr class="table">
                                     <td  >
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
                <strong>Confirmation details Period</strong>
              </div>
            </td>
          </tr>
          
          <tr class="table">
            <td>
            
              <div align="left">Year  <font color="#ff2121">*</font>  </div>
              
            </td>
            <td>
              <div align="left">
             <input type="text" name="txtyear" id="txtyear"  maxlength="4"   size="5"  onkeypress="return  numbersonly1(event,this)" onblur="monthcall()"/>                       
                   
              </div>
            </td>
          </tr>
          
          <tr class="table">
            <td>
              <div align="left">Month
              <font color="#ff2121">*</font>
              </div>
            </td>
            <td>
              <div align="left">
                <select name="txtmonth" id="txtmonth">                       
                        <option value="">--Select the Month--</option>
                        </select>
              </div>
            </td>
          </tr>
         
          <tr class="tdTitle">
            <td colspan="2">
              <div align="left">
                <strong>Confirmation details </strong>
              </div>
            </td>
          </tr>
         
          
          <tr class="table">
          
          <td colspan="2">
              <div align="left">
                 &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;1.All the joining and relieval entries updated
                
              </div>
            </td>
                      </tr>
            
        
            
          
          <tr class="table">
            <td colspan="2">
              <div align="left">
                 &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;2.Office address updated
              
              </div>
            </td>
             
          </tr>
          
           <tr class="table">
        <td colspan="2">
              <div align="left"> &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;3.Grade changes of the employee details</div>
            </td>
           
          </tr>
          
          <tr class="table">
            <td colspan="2">
              <div align="left"> &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;4.All employees additional details and service particulars updated</div>
            </td>
            
          </tr>
          
          <tr class="table">
            <td colspan="2">
              <div align="left"> &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;5.The sanction strength and Vancancy position report verified</div>
            </td>
            
          </tr>
                          <tr class="table">
                            <td colspan="2">
  <div align="center">
             <input type="radio" name="allupdate" id="allupdate"
                                value="Y">YES &nbsp;&nbsp;&nbsp;&nbsp;
             <input type="radio" name="allupdate" id="allupdate"
                                value="N" checked="checked">NO &nbsp;&nbsp;&nbsp;&nbsp;
                <input type="hidden" name="proc_flow_status" id="proc_flow_status" >
               </div>
             </td>
             </tr>
             </table>
            
             
            <!--   <table id="mytable" align="center" 
                     border="0" width="100%">   -->
                   
              
              
                            <table border="0">
                            

                            <tr class="tdTitle">
                           
            <td>
                       <input type="button" name="cmdadd" value="Add" id="cmdadd"
                             onclick="callServer('Add','null'); "  />
                             </td>
                             <td>
                             <input type="button" name="cmdupdate" value="UPDATE"
                             id="cmdupdate"
                             onclick="callServer('Update','null')"
                             />
                             </td>
                             <td>
                             <input type="button" name="cmddelete" value="DELETE"
                             id="cmddelete"
                             onclick="callServer('Delete','null')"
                             disabled="disabled" />
                             <td>
                             <input type="button" name="cmdclear" value="CLEAR ALL"
                             id="cmdclear"
                             onclick="clearfun()"/>
                     
                                              
               
                </td>
                </div>
                </tr>
                
                </table>
                <table id="mytable" align="center" cellspacing="3" cellpadding="2"
                     border="1" width="100%">
                <tr class="tdH">
                  <th align="LEFT" colspan="5">Existing Details</th>
                </tr>
                <tr class="tdH">
                  <th>Select</th>
                  <th>Year </th>
                  <th>Month</th>
                 
                  <th>Updation Status </th>
                  
<th>Validation</th>                <!--  <th>Emp&nbsp;Status&nbsp;Desc </th>-->
                  
                </tr>
                <tbody id="tb" class="table">
                </tbody>
              </table>
              
             </form> 
        
  </body>
</html>

