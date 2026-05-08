<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">
<%@ page contentType="text/html;charset=windows-1252"%>
<%@ page import="java.sql.*,java.util.*,Servlets.Security.classes.UserProfile"%>
<%@ include file="//org/Security/jsps/Check_SessionJSPF.jspf" %>
<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=windows-1252"/>
    <title>Unitwise Journal Report </title>
   
   <script type="text/javascript" src="<%=request.getContextPath()%>/org/Library/scripts/comJS.js"></script>
       <link href="../../../../../../css/Sample3.css" rel="stylesheet"   media="screen"/>
   
   <script type="text/javascript"
            src="<%=request.getContextPath()%>/org/FAS/FAS1/CommonControls/scripts/Common_Load_Accounting_Unit_ID_4Rpt.js"></script>
    
   
   <script type="text/javascript"
            src="<%=request.getContextPath()%>/org/FAS/FAS1/CommonControls/scripts/Common_Load_Accounting_office.js"></script>
      
   <script type="text/javascript"
           src="<%=request.getContextPath()%>/org/FAS/FAS1/CommonControls/scripts/Load_Accounting_office.js"></script>
      
      
      
   <script type="text/javascript" language="javascript">
    function loadyear_month(){
                var today= new Date(); 
                var day=today.getDate();
                var month=today.getMonth();
                month=month+1;
                var year=today.getYear();
                if(year < 1900) year += 1900;
              
               document.journal.txtCB_Year.value=year;
               document.journal.txtCB_Month.value=month;
                       
               document.journal.txtCB_Year_from.value=year;
               document.journal.txtCB_Month_from.value=month;
               
               document.journal.txtCB_Year_to.value=year;
               document.journal.txtCB_Month_to.value=month;
      }

    function cb_month_year1(id){
       var Regions=document.getElementById("Regions");
       var Banks=document.getElementById("officewise");
      if(id=="RW")
      {
    	  Regions.style.display="block";
    	  Banks.style.display="none";
      }
      if(id=="OW")
      {
    	 Banks.style.display="block";
         Regions.style.display="none";
      }
      if(id=="ALL")
      {
    	 Banks.style.display="none";
         Regions.style.display="none";
      }
    }
function chooseReport()
         {
             if(document.getElementById("proformatype").value=="CR")
             {
                var t1=document.getElementById("tdablock");
                t1.style.display="none";
                var t2=document.getElementById("tcablock");
                t2.style.display="block";
                var t3=document.getElementById("allBlock");
                t3.style.display="none";
             }
             else if(document.getElementById("proformatype").value=="DR")
             {
                var t1=document.getElementById("tdablock");
                t1.style.display="block";
                var t2=document.getElementById("tcablock");
                t2.style.display="none";
                var t3=document.getElementById("allBlock");
                t3.style.display="none";
             }
             else if(document.getElementById("proformatype").value=="allType")
             {
               var t1=document.getElementById("tdablock");
                t1.style.display="none";
                var t2=document.getElementById("tcablock");
                t2.style.display="none";
                var t3=document.getElementById("allBlock");
                t3.style.display="block";
             }
         }
        function checkNull()
         {
                if(document.getElementById("proformatype").value=="")
                {
                alert("Choose Type TDA or TCA");
                return false;
                }
                
                
         }
        function cb_month_year(id)
        {
           var particular=document.getElementById("particular");
           var more=document.getElementById("more");
               
          if(id=="particular_cb")
          {
             particular.style.display="block";
             more.style.display="none";
          }
          if(id=="more_cb")
          {
            more.style.display="block";
            particular.style.display="none";
          }
        }
    </script>
  </head>  
<body class="table" onload="loadyear_month();LoadAccountingUnitID('LIST_ALL_UNITS');">

  
    
 <form name="journal" id="journal" action="../../../../../../UnitsJournal" method="post"> 
    
    
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
            ConnectionString = strdsn.trim() + "://" + strhostname.trim() + ":" + strportno.trim() + "/" +strsid.trim() ;    // Postgres DB  Connection
            //ConnectionString = strdsn.trim() + "@" + strhostname.trim() + ":" + strportno.trim() + ":" +strsid.trim() ;

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
            <font size="4">Journal Report</font>
          </div>
        </td>
      </tr>
    </table>
    
    
          <table cellspacing="2" align="center" cellpadding="2" border="1" width="80%">
    		<tr align="left">
           <td class="table">
              <div align="left">
                 Cash Book Year &amp; Month <font color="#ff2121">*</font>
              </div>
           </td>
          <td>
         
          <input type="radio" name="month_year" id="month_year" value="particular_cb" onclick="cb_month_year(this.value)" >one Month 
          <input type="radio" name="month_year" id="month_year" value="more_cb" onclick="cb_month_year(this.value)"> More than one Month 
          
          <br><br>       
          
          <div id="particular" name="particular" style="display:none">
            
          Year 
          <input type="text" name="txtCB_Year" id="txtCB_Year" tabindex="3"  maxlength="4" size="5" onkeypress="return numbersonly(event)">
          Month 
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
          
          </div>
          
          <div id="more" name="more" style="display:none">
          
          From   
          <input type="text" name="txtCB_Year_from" id="txtCB_Year_from" tabindex="3"  maxlength="4" size="5" onkeypress="return numbersonly(event)">
         
          <select name="txtCB_Month_from"  id="txtCB_Month_from" tabindex="4" >
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
          To  
          <input type="text" name="txtCB_Year_to" id="txtCB_Year_to" tabindex="3"  maxlength="4" size="5" onkeypress="return numbersonly(event)">
         
          <select name="txtCB_Month_to"  id="txtCB_Month_to" tabindex="4" >
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
              		Displaying Order <font color="#ff2121"> * </font>
                 </div>
               </td>
               <td>
                   <input type="radio" name="displayingOrder" id="displayingOrder" value="ALL" onclick="cb_month_year1(this.value)" checked ></input>All    &nbsp;&nbsp;
                   <input type="radio" name="displayingOrder" id="displayingOrder" value="RW" onclick="cb_month_year1(this.value)" ></input>Region Wise &nbsp;&nbsp; 
                   <input type="radio" name="displayingOrder" id="displayingOrder" value="OW" onclick="cb_month_year1(this.value)" ></input>Office Wise     
                        
                        
                  <br><br>
                  
                   <div align="left" id="Regions" name="Regions" style="display:none">                	
                   
                    <select size="1" name="txtRegionId" id="txtRegionId" tabindex="1">
                     <option value="">--Select Region--</option>
                     
                      <%
                    
                      try{
                                ps=con.prepareStatement("select OFFICE_NAME,OFFICE_ID from COM_MST_OFFICES where OFFICE_LEVEL_ID in ('RN') and office_status_id not in('CL','RD','NC') order by office_name");
                                rs=ps.executeQuery();
                                while(rs.next())
                                {
                                    out.println("<option value="+rs.getInt("OFFICE_ID")+">"+rs.getString("OFFICE_NAME")+"</option>");
                                }
                                
                          }
                      catch(Exception e)
                        {
                            System.out.println(e);
                        }
                      %>
                    </select>        
                  </div>   
                
                
                       
               
                 <div align="left" id="officewise" name="officewise" style="display:none">
                         	
                    <select size="1" name="cmbAcc_UnitCode" id="cmbAcc_UnitCode" tabindex="1"></select>     
                    
                </div>
                
                
               </td>               
              </tr> 
             
            
            <tr class="tdH">
              
               <td colspan="2">
                <div align="center">
                	<input type="submit" value="Submit"> &nbsp;&nbsp; <input type="button" value="Exit" onclick="javascript:window.close()">
                </div>
               </td>
              </tr>
              
              
            
                    
            </table>            
   
    
    
        </form>
    </body>
</html>