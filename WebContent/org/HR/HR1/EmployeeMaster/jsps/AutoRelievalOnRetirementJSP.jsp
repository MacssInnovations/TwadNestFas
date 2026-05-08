<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">

<%@ page session="false"  contentType="text/html;charset=windows-1252"%>
<%@ page import="java.sql.*,java.util.*,Servlets.Security.classes.UserProfile"%>
<%@ include file="//org/Security/jsps/Check_SessionJSPF.jspf" %>
<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
    <script type="text/javascript" src="../scripts/AutoRelievalOnRetirementJS.js"></script>
    <script type="text/javascript" src="<%=request.getContextPath()%>/org/Library/scripts/comJS.js"></script>
    <title>AutoRelievalOnRetirementJSP</title>
    <link href="../../../../../css/Sample3.css" rel="stylesheet"
          media="screen"/>
     <script type="text/javascript" language="javascript">
     function loadyear_month()
     {
      //   var mon[]={'january',}
         var today= new Date(); 
         var day=today.getDate();
         var month=today.getMonth();
         month=month+1;
         //alert(month);
         
         var year=today.getYear();
         if(year < 1900) year += 1900;
       var cmbyearid=document.getElementById("cmbyear");
       var option=document.createElement("OPTION");
                option.text="--Select Year--";
                option.value="";
                try
                {
                    cmbyearid.add(option);
                }catch(errorObject)
                {
                    cmbyearid.add(option,null);
                }
                var opt1=document.createElement("option");
                opt1.text=year;
                opt1.value=year;
                 try
                {
                    cmbyearid.add(opt1);
                }catch(errorObject)
                {
                    cmbyearid.add(opt1,null);
                }
                
                var endyear=parseInt(year)-1;
                //alert(endyear);
                var cmbyearid1=document.getElementById("cmbyear");
                cmbyearid1.innerHTML="";
                
                var option=document.createElement("OPTION");
                //alert(option);
                option.text="--Select Year--";
                option.value="";
                try
                {
                    cmbyearid.add(option);
                }catch(errorObject)
                {
                    cmbyearid.add(option,null);
                }
for(var i=year;i>=endyear;i--)
  {
  
                opt1=document.createElement("option");
                opt1.text=i;
                opt1.value=i;
                //alert()
                 try
                {
                    cmbyearid1.add(opt1);
                }catch(errorObject)
                {
                    cmbyearid1.add(opt1,null);
                }
  }
    //alert(month);
         document.frmEmployeeRelievalOnRetirement.cmbmonth.value=month-1; 
         document.frmEmployeeRelievalOnRetirement.cmbyear.value=year; 
         //alert(month);
     }
    </script>      
  </head>
  <body  class="table" onload="loadyear_month()" >
  <form name="frmEmployeeRelievalOnRetirement">
  <center>
  <% 
      HttpSession session=request.getSession(false);
      UserProfile empProfile=(UserProfile)session.getAttribute("UserProfile");
      
    System.out.println("user id::"+empProfile.getEmployeeId());
  %>  
  <table border="1" width="100%" >
  <tr>
     <td colspan="2">
      <table width="100%" border="1">
              <tr>
                <td align="center" class="tdH" colspan="4">
                  <div align="left">
                    <center>
                      <b>Auto Relieval On Retirement</b>
                    </center>
                  </div>
                </td>
              </tr>
              <tr>
                <td colspan="2">
                  <div align="left">
                    Relieval Year 
                  </div>
                </td>
               
                <td>
                  <div align="left">
                    <select size="1" name="cmbyear" id="cmbyear" tabindex="2" onchange="validateMonth()">
                     <%
                        java.util.Date d=new java.util.Date();
                        System.out.println("date is "+d);
                        int currentyear=d.getYear()+1900;
                        System.out.println("current year"+currentyear);
                        int endyear=currentyear-1;
                        System.out.println("end year"+endyear);
                        for(int i=currentyear;i>=endyear;i--)
                          {
                          System.out.println(i);
                        %>
                            <option value='<%=i%>'> <%=i%> </option>
                           <%
                           }
                           %>
                    </select>
                  </div>
                </td>
              </tr>
              <tr>
                <td colspan="2">
                  <div align="left">
                    Relieval Month 
                  </div>
                </td>
                <td>
                  <div align="left">
                    <select size="1" name="cmbmonth" id="cmbmonth" tabindex="2" onchange="validateMonth()" >
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
                  <input type="button" id="btnGo" value="GO" onclick="loadServer('loadEmployee')"></input>
                  </div>
                </td>
              </tr>
              
            </table>
         </td>
      </tr>
      <tr>
         <td colspan="2">
            <table id="table_id" border='1' class="table" width="100%">
                <tr>
                  <th class="tdH">
                    Select
                 </th>
                 <th class="tdH">
                    Employee Id
                 </th>
                 <th class="tdH">
                    Employee Name
                 </th>
                 <th class="tdH">
                    Date Of Birth 
                 </th>
                 <th class="tdH">
                    Date Of Retirement 
                 </th>
                 <th class="tdH">
                    Employee Status 
                 </th>
                 <th class="tdH">
                    Already Relieved?
                 </th>
                 <th class="tdH">
                    Office Id
                 </th>
                 <th class="tdH">
                    Office Name
                 </th>
                </tr>
                <tbody id="grid_body">
                
                </tbody>
                <tr >
                   <td class="tdH"  colspan="9">
                     <center>
                        <input type="button" id="btnRelieve" value="Relieve" disabled onclick="loadServer('relieveEmployee')"></input>
                        <input type="button" value="Cancel" onclick="self.close()"></input>
                     </center>
                   </td>
                </tr>
            </table>
         </td>
        </tr>
      </table>
      </center>
     </form>   
  
  </body>
</html>