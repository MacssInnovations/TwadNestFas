<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">
<%@ page contentType="text/html;charset=windows-1252"%>
<%@ page import="java.sql.*,java.util.*,Servlets.Security.classes.UserProfile"%>
<%@ include file="//org/Security/jsps/Check_SessionJSPF.jspf" %>
<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=windows-1252"/>
    <title>Acceptance Verification</title>
   
   <script type="text/javascript" src="<%=request.getContextPath()%>/org/Library/scripts/comJS.js"></script>
   <script type="text/javascript" src="../../../../../org/Library/scripts/checkDate.js"></script>
   <link href="../../../../../css/CalendarControl.css" rel="stylesheet"  media="screen"/>
   <link href="../../../../../css/Sample3.css" rel="stylesheet" media="screen"/>
   
      
   <script type="text/javascript" src="../../../../../org/HR/HR1/EmployeeMaster/scripts/CalendarControl.js"></script> 
   <script type="text/javascript" src="../../../../../org/FAS/FAS1/CalendarCtrl.js"></script> 
   
   <script type="text/javascript"
            src="<%=request.getContextPath()%>/org/FAS/FAS1/CommonControls/scripts/Common_Load_Accounting_Unit_ID.js"></script>
   
   <script type="text/javascript"
            src="<%=request.getContextPath()%>/org/FAS/FAS1/CommonControls/scripts/Common_Load_Accounting_office.js"></script>
   
   
   <script language="javascript" type="text/javascript" src="../js/Acceptance_Verification.js"></script>
      
      
   <script type="text/javascript"
           src="<%=request.getContextPath()%>/org/FAS/FAS1/CommonControls/scripts/Load_Accounting_office.js"></script>
      
      
      
   <script type="text/javascript" language="javascript">
    function loadyear_month()
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
                 
                document.frmauto_Fund.txtCrea_date.value=day+"/"+month+"/"+year;
                
                document.frmauto_Fund.txtCB_Year.value=year
                document.frmauto_Fund.txtCB_Month.value=month;
      }
    </script>
  </head>  
<body class="table" onload="loadyear_month()">

  
    
 <form name="frmauto_Fund" id="frmauto_Fund"  action="../../../../../Acceptance_Verification" method="post" onsubmit="return checknull();"> 
    
    
 <%
  
  Connection con=null;
  ResultSet rs=null,rs2=null,rsbank=null;
  PreparedStatement ps=null,ps2=null,psbank=null;
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
 
 
     HttpSession session=request.getSession(false);
     UserProfile empProfile=(UserProfile)session.getAttribute("UserProfile");
      
     System.out.println("user id::"+empProfile.getEmployeeId());
     int empid=empProfile.getEmployeeId();     
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
            
    }
    catch(Exception e)
    {
        System.out.println(e);
    }
   
   %>
   
    <table cellspacing="1" cellpadding="3" width="100%" >
      <tr class="tdH">
        <td colspan="2">
          <div align="center">
            <font size="4">Acceptance Verification</font>
          </div>
        </td>
      </tr>
    </table>
    
    
          <table cellspacing="2" cellpadding="2" border="0" width="100%">
              <tr class="table">
                <td>
                  <div align="left">
                    Accounting Unit Code 
                    <font color="#ff2121">*</font>
                  </div>
                </td>
                <td>
                  <div align="left">
                    <!--<input type="text" name="txtAcc_UnitCode"
                           id="txtAcc_UnitCode" maxlength="4" size="5"/>-->
                    <select size="1" name="cmbAcc_UnitCode" id="cmbAcc_UnitCode" tabindex="1" onchange="LoadOffice(this.value);" >
                     <!-- <option value="0"> Select Account Unit </option>-->
                          <%
                      int unitid=0;
                      String unitname="";
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
              <font color="#ff2121">
                *
              </font>
                  </div>
                </td>
                <td>
               
                
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
                    <input type="hidden" name="txtOffid" id="txtOffid" value="<%=oid%>">
                </td>
              </tr>      
              
       
                    
            </table>            
    
       <table cellspacing="1" cellpadding="1" border="0" width="100%">
       <tr align="left" class="tdH"> <th>Search By Month </th></tr>
        <tr align="left">
          <td class="table">
          <div align="left">
              Cash Book Year &amp; Month&nbsp;&nbsp;<strong>:</strong>
                         <input type="text" name="txtCB_Year" id="txtCB_Year" tabindex="3"  maxlength="4" size="5" onkeypress="return numbersonly(event)">
         
          <select name="txtCB_Month"  id="txtCB_Month" tabindex="4" >
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
          
           <input type="BUTTON" value="GO" name="ByMonth" id="ByMonth"  tabindex="5" onclick="call('loadvoucher')"/>
           </div>
          </td>
        </tr>
                 
         
         
        
     </table>
    
     <table cellspacing="1" cellpadding="1" border="0" width="100%">
      <tr align="left" class="tdH"> <th>Details </th></tr>
      </table>
     <table id="mytable" align="center"  cellspacing="3" 
         cellpadding="2" border="0" width="100%">
         
          <tr class="tdH">
            
            <th>
            <font size="2">
             Select
                 <a href="javascript:selectAll('ALL');">All</a>
                 <a href="javascript:selectAll('UNSelect');">Unselect</a> 
             </font>
            </th>
            
            <th>
              <font size="2"> Advice No</font>
            </th>   
             <th>
              <font size="2"> Voucher No</font>
            </th>             
            <th>
             <font size="2"> Date </font>
            </th>            
            <th>
             <font size="2"> TRN Unit </font>
            </th>
            <th>
             <font size="2"> Amount </font>
            </th>
            <th>
             <font size="2"> Acceptance Date </font>
            </th>          
            <th>
              <font size="2"> Particulars </font>
            </th>          
          
            
            
          </tr>
          
          <tbody id="tbody" class="table">
          
         
          </tbody>
      <tr class="tdH">
      <td colspan="12">
          <div align="center">
          
         <input type="hidden"  value="" name="txtCrea_date" id="txtCrea_date">
         
         <input type="submit"  value="Submit" >
         <input type="button" id="cmdcancel" name="cancel" value="Exit" onclick="self.close()">
         
      </div>
      </td>
      </tr>

        </table> 
        
        </form>
    </body>
</html>
