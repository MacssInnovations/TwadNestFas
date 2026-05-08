<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">
<%@ page contentType="text/html;charset=UTF-8"%>
<%@ page import="java.sql.*,java.util.*,Servlets.Security.classes.UserProfile"%>
<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=windows-1252"/>   
    <meta http-equiv="cache-control" content="no-cache">
    <title>Vehicle Bill Details</title>
         <link href="../../../../../css/Sample3.css" rel="stylesheet" media="screen"/>
         <link href="../../../../../css/CalendarControl.css" rel="stylesheet" media="screen"/>
         
         <script type="text/javascript" src="../../../../../org/FAS/FAS1/CalendarCtrl.js"></script> 
         <script type="text/javascript" src="../../../../../org/Library/scripts/checkDate.js"></script>
         
         <script type="text/javascript" src="../scripts/Vehicle_Bills.js"></script>
         <script type="text/javascript" src="<%=request.getContextPath()%>/org/Library/scripts/comJS.js"></script>
        
         <script language="javascript" type="text/javascript">
            function closeWindow()
            {                
                window.open('','_parent','');                
                window.close(); 
                window.opener.focus();
            }
         function loadDate()
        {
        	 var today= new Date(); 
                 var day=today.getDate();
                 var month=today.getMonth();
                 month=month+1;
                 
                 if(day<=9 && day>=1)
                 day="0"+day;
                 if(month<=9 && month>=1)
                 month="0"+month;
                 var year=today.getYear();
                 if(year < 1900) year += 1900;                
                 document.VehicleBill.ann_date.value=day+"/"+month+"/"+year;                
        }
    </script>
  </head>
  <body class="table" onload="loadDate();">
     <%
             Connection con=null;
             ResultSet rs=null;
             PreparedStatement ps=null,ps2=null;
            
             Connection connection=null;
        
             ResultSet results=null,rs2=null;
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
                       System.out.println("Exception in opening connection :"+e);
             }  UserProfile empProfile=(UserProfile)session.getAttribute("UserProfile");
                int empid=empProfile.getEmployeeId();
                int  oid=0;             // Office id
                String oname="";        // office name
           
            try
            {
                   
                    ps=con.prepareStatement("select OFFICE_ID from HRM_EMP_CURRENT_POSTING where EMPLOYEE_ID=?" );
                    ps.setInt(1,empid);
                    results=ps.executeQuery();
                         if(results.next()) 
                         {
                            oid=results.getInt("OFFICE_ID");
                            System.out.println("Office id is:"+oid);
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

        
  <form name="VehicleBill" id="VehicleBill" method="POST">
      <table cellspacing="2" cellpadding="3" border="1" width="100%">
        <tr class="tdH">
              <td>
                <div align="center">
                  TWAD BOARD-INTEGRATED ONLINE SYSTEM -FINANCIAL ACCOUNTING SYSTEM
                </div>
              </td>
        </tr>
        
        <tr class="table">
          <td>
            <div align="center">
              <b>Vehicle Bill Details</b>
            </div>
          </td>
        </tr>
        </table>
      <table cellspacing="2" cellpadding="3" border="1" width="100%" align="center">
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
                               System.out.println("here::::::::::");
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
      <tr class="table">
          <td>Date<font color="#ff2121">*</font></td>
              <td>
                     <input type="text" name="ann_date" id="ann_date" tabindex="3" 
                           maxlength="10" size="11"  
                           onfocus="javascript:vDateType='3';"
                           onkeypress="return calins(event,this);"
                           onblur="call_date(this);"/>
                     <img src="../../../../../images/calendr3.gif"
                         onclick="showCalendarControl(document.VehicleBill.ann_date,0);"
                         alt="Show Calendar"></img>                   
              
                </td>
        </tr>

      <tr class="table">
         <td>Vehicle No.<label style="color:rgb(255,0,0);">&nbsp;*</label></td>
           <td>
            <select onchange="checkParam()" name="veh_no" id="veh_no">
              <option value="Value">--Select Asset Code for Vehicle--</option>
                <%                    
                      try
                            {                           
                                ps=con.prepareStatement("select VEHICLE_CODE from FAS_VEHICLE_MASTER");
                                rs=ps.executeQuery();
                                 while(rs.next())
                                    {
                                       
                                         out.println("<option value="+ rs.getInt("VEHICLE_CODE") + ">" + rs.getInt("VEHICLE_CODE") +"</option>" );
                                    }
                            } 
                             catch(Exception e)
                                {
                                    System.out.println("Exception in Bill Type Major combo..."+e);
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
          <td>M.Book Ref No. & Date</td>
              <td>
                    <input type="text" name="ref_no" id="ref_no" onkeypress="return  numbersonly(event,this)">
                
                    <input type="text" name="ann_date1" id="ann_date1" 
                           maxlength="10" size="10"  
                           onFocus="javascript:vDateType='3'"
                           onkeypress="return  calins(event,this)"
                           onblur="return checkdt(this);" />&nbsp;&nbsp;
                     <img src="../../../../../images/calendr3.gif"
                         onclick="showCalendarControl(document.VehicleBill.ann_date1,0);"
                         alt="Show Calendar"></img>   
                         
                         
              </td>
        </tr>
        
    <tr class="table">
          <td>Fuel Used<font color="#ff2121">*</font></td>
          <td>
              <div align="left">
                 <table border="0">
                       <tr> 
                             <td>
                                Qty
                                <input type="text" name="fuel_qty" id="fuel_qty" onfocus="asset();" onchange="check()" onkeypress="return filter_real(event,this,5,5)"/>
                            </td>
                            <td>
                                Amount
                                <input type="text" name="fuel_amt" id="fuel_amt" onfocus="asset();" onchange="check()" onkeypress="return filter_real(event,this,6,6)"/>
                          </td>
                      </tr>
               </table>       
             </div>
           </td>
        </tr>
        <tr class="table">
          <td>Oil Used</td>
          <td>
                  <div align="left">
                   <table border="0">
                    <tr> 
                     <td>
                       Qty
                        <input type="text" name="oil_qty" id="oil_qty" onfocus="asset();" onchange="check()" onkeypress="return filter_real(event,this,5,5)"/>
                       </td>
                     <td>
                     
                      Amount
                        <input type="text" name="oil_amt" id="oil_amt" onfocus="asset();" onchange="check()" onkeypress="return filter_real(event,this,6,6)"/>
                     </td>
                    </tr>
                    </table>       
                  </div>
                </td>
        </tr>
         <tr>
            <td class="table">Is R & M for Major Overhauling ?</td>
            <td class="table">
	            <input type="radio" name="radActive" id="radActive" value="Y">Yes &nbsp;
	            <input type="radio" name="radActive" id="radActive" value="N" checked>No
            </td>
       </tr>
      <tr class="table">
         <td>Particulars (100 Characters)</td>
          <td>
            <textarea name="txtRemarks" id="txtRemarks" cols="60"  onkeypress="return checklength(event,this);"
                              rows="3"></textarea>
                              </td>
        </tr>
        <tr class="tdH">
            <td colspan=12>
                <div align="center">
                 <table >
                     <tr>
                        <td>
                        <input type="button" name="cmdAdd" value="ADD" id="cmdAdd" onclick="calling('Add')" tabindex="20"/>
                         </td>
                         <td>
                        <input type="button" name="cmdEdit" value="UPDATE" id="cmdEdit"  onclick="calling('Update')" tabindex="30" disabled/>
                         </td>
                        <td>
                        <input type="button" name="cmdDelete" value="CANCEL" id="cmdDelete"  onclick="calling('Delete')" tabindex="40" disabled/>
                         </td>
                         <td>
                        <input type="button" name="cmdList" value="LIST" id="cmdList" onclick="Lists();" tabindex="60"/>
                         </td>
                           <td>
                         <input type="button" name="cmdCancel" value="CANCEL"  id="cmdCancel" onclick="ClearAll()" tabindex="70"/>
                         </td>
                     </tr>
                </table>
                    </div>
            </td>
        </tr>
      </table>
       
      
        <table align="center" cellspacing="3" cellpadding="2" border="1"
             width="100%">
        <tr class="tdH">
          <td>
            <div align="center">
              <input type="button" id="cmdcancel" name="cancel" value="Exit"
                     onclick="self.close();"></input>
            </div>
          </td>
        </tr>
      </table> 
    </form>
</html>
