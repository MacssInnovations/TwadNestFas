<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">
<%@ page session="false"  contentType="text/html;charset=windows-1252"%>
<%@ page import="java.sql.*,java.util.*,Servlets.Security.classes.UserProfile"%>
<%@ include file="//org/Security/jsps/Check_SessionJSPF.jspf" %>
<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=windows-1252"/>     <meta http-equiv="cache-control" content="no-cache">
    <title>Retirement List</title>
     <script type="text/javascript" src="<%=request.getContextPath()%>/org/Library/scripts/comJS.js"></script>
     <!--script type="text/javascript" src="../../../../../org/Library/scripts/checkDate.js"></script -->
    <script language="javascript" type="text/javascript" src="../scripts/RetirementView.js"></script>
    <link href="../../../../../../css/CalendarControl.css" rel="stylesheet"  media="screen"/>
    <link href="../../../../../../css/Sample3.css" rel="stylesheet" media="screen"/>
    <style type="text/css">
      body {
      background-color: #ffffff; 
}
      a:link { color: #002173; }
      
    .navigation {
  height:100px; width:283px; 
  border-style:1px solid #666;
  border-width:1px; 
  background-color: #fff;
  float:left; 
  overflow: auto;
}

      
       <!-- div.scroll {	
      height: 100px;	
      width: 100%;
      overflow: auto;	
      border: 1px solid #666;	
      background-color: #fff;	
      padding: 0px;
     visibility: hidden;
     position: relative;
      }-->
      
    </style>
    
     <script type="text/javascript" src="../../../../../org/HR/HR1/EmployeeMaster/scripts/CalendarControl.js"></script> 
     <script type="text/javascript" src="../../../../../org/FAS/FAS1/UnitwiseOffice.js"></script> 
   <script type="text/javascript" language="javascript">
     function loadyear_month()
     {
         var today= new Date(); 
         var day=today.getDate();
         var month=today.getMonth();
         month=month+1;
         var year=today.getYear();
         
         if(year < 1900) year += 1900;
         //alert(year);
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
                    cmbyearid.add(opt1);RetirementView
                }catch(errorObject)
                {
                    cmbyearid.add(opt1,null);
                }
                
        /*        
                var endyear=parseInt(year)+5;
               // alert(endyear);
                var cmbyearid1=document.getElementById("cmbyear");
                cmbyearid1.innerHTML="";
                
                var option=document.createElement("OPTION");
               // alert(option);
                option.text="--Select Year--";
                option.value="";
                try
                {
                    cmbyearid.add(option);
                }catch(errorObject)
                {
                    cmbyearid.add(option,null);
                }
for(var i=year;i<endyear;i++)
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
  }*/
                
              
     }
    </script>
      </head>
  <body class="table" onload="loadyear_month()" >
  <form name="frmRetirementList" method="POST">
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
            //ConnectionString = strdsn.trim() + "@" + strhostname.trim() + ":" + strportno.trim() + ":" +strsid.trim() ;

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
                    System.out.println("Employee_ id is "+empid);
                    int oid=0;
                    String deptid="",offlevel="";
                    
                         try
                        {
                         System.out.println("first");      
                                ps=con.prepareStatement("select b.OFFICE_LEVEL_ID,b.office_id from hrm_emp_current_posting a "+
                                                               " inner join com_mst_offices b on b.office_id=a.office_id "+
                                                               " where a.employee_id=?" );
                                ps.setInt(1,empid);
                        System.out.println("seccond");                                      
                                results=ps.executeQuery();
                                     if(results.next()) 
                                     {
                                        offlevel=results.getString("OFFICE_LEVEL_ID");
                                        oid=results.getInt("office_id");
                                        System.out.println("office id:"+oid);
                                System.out.println("level id:"+offlevel);
                                     }
                         System.out.println("third");                  
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
            <strong>Retirement Due for the Next Five Years</strong>
          </div>
        </td>
      </tr>
    </table>
     <div>
            <table cellspacing="1" cellpadding="2" border="1" width="100%">
        <tr class="tdH">
        <td colspan="2">
          <div>
            <strong>Select Cadre</strong>
          </div>
        </td>
      </tr>
            <tr class="table">
                <td colspan='2'> <input type="radio" name="optselect"  onclick="selectoption1()"
                             id="optselect2" value="All" Checked>All Cadre</input>
                <input type="radio" name="optselect"  onclick="selectoption1()"
                             id="optselect3" value="Specific">Specific Cadre</input></td>
                           
            </tr>
             
              <tr class="table" id="divcadre" style="display:none">
                
                  <td colspan='2'>
                    <div align="left">
                    <select name="cmbsgroup2" id="cmbsgroup2" onchange="getDesignation2()">
                <option value="0">Select Service Group</option>
                <%
            rs=null;
           try
           {
           ps=con.prepareStatement("select SERVICE_GROUP_ID,SERVICE_GROUP_NAME from HRM_MST_SERVICE_GROUP  order by SERVICE_GROUP_NAME");
            rs=ps.executeQuery();
              int strcode=0;
            String strname="";          
            while(rs.next())
            {
              
               
                strcode=rs.getInt("SERVICE_GROUP_ID");
                strname=rs.getString("SERVICE_GROUP_NAME");
                
                out.println("<option value='"+strcode+"'>"+strname+"</option>");
                
             }
          }
          catch(Exception e)
          {
            System.out.println("Exception in grid.."+e);
          }
           
                
        %>
               
               </select> 
                    
                    </div>
                  </td>
                </tr>
             <tr class="table" id="desigblock" style="display:none">
               <td colspan='2'>
               <div class="navigation" align='left' id="divdesignation">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</div>
               <!--p class="scroll" align='left' id="divdesignation">&nbsp;</p-->
               </td>
               </tr>  
             
       <!--      <tr class="table" id="cmbSpecificCadre"  style='display:none' >
                <td>
                  <div align="left">
                    Select&nbsp;Cadre&nbsp;Name
                  </div>
                </td>
                <td>
                  <div align="left">
                    
                    <select size="1" name="cmbCadreName" id="cmbCadreName" tabindex="1">
                     <option value="0"> Select Cadre Name </option>
                          <%
                      int cadreid=0;
                      String cadrename="";
                      try{
                     
                     
                                           //out.println("<option value="+0+">"+"-- Select Account Unit --"+"</option>");
                                //ps=con.prepareStatement("select ACCOUNTING_UNIT_ID,ACCOUNTING_UNIT_NAME from FAS_MST_ACCT_UNITS where ACCOUNTING_UNIT_OFFICE_ID=?");
                                String cadre="select cadre_id,cadre_name from HRM_MST_CADRE";
                                ps=con.prepareStatement(cadre);
                                rs=ps.executeQuery();
                              
                                  while(rs.next())
                                  {
                                      out.println("<option value="+rs.getInt("cadre_id")+">"+rs.getString("cadre_name")+"</option>");
                                      cadreid=rs.getInt("cadre_id");
                                      
                                      System.out.println(".."+rs.getInt("cadre_id"));
                                      System.out.println(".."+rs.getString("cadre_name"));
                                                                                
                                  }
                              ps.close();
                              rs.close();
                              }
                                                                                      
                    
                    catch(Exception e)
                    {
                        System.out.println(e);
                    }
                      %>
                      </select>
                  </div>
                </td>
              </tr>  -->
              
              <tr class="table">
                <td>
                  <div align="left">
                    Select&nbsp;the&nbsp;Year

                  </div>
                </td>
                <td>
                  <!--  This is used to load details about login user( super user or oridinary user ) login and their unit id-->
                 <div align="left">
                  
                   </div>
                  <!--end -->
                  
                  <div align="left">
                    <select size="1" name="cmbyear" id="cmbyear" tabindex="2" onchange="loadYears()">
                   <!-- <option value="2007">2007</option>
                      <option value="2008">2008</option>
                      <option value="2009">2009</option>
                      <option value="2010">2010</option>
                      <option value="2011">2011</option>-->
                     </select>
                  </div>
                </td>
              </tr>
              
              <tr class="table">
                       <td align="left">Report&nbsp;Type</td>
                       <td align="left"><select name="cmbReportType">
                              <option value="PDF" selected="selected">PDF</option>
                              <option value="EXCEL">EXCEL</option>
                            <!--  <option value="TXT">TXT</option>-->
                              <option value="HTML">HTML</option>
                            </select> 
                        </td>
                </tr>
            <!-- <tr>
                    <td colspan="2" class="table" align="left">
                      <div onclick="document.frmRetirementList.optoutputtype[0].checked=true;"
                           style="cursor:hand">
                        <input type="radio" name="optoutputtype" id="optoutputtype" value="PDF" checked></input>PDF
                                                                         Format
                      </div>
                    </td>
                  </tr>
                  <tr>
                    <td colspan="2" class="table" align="left">
                      <div onclick="document.frmRetirementList.optoutputtype[1].checked=true;"
                           style="cursor:hand">
                        <input type="radio" name="optoutputtype" id="optoutputtype" value="EXCEL"></input>EXCEL
                                                                         Format
                      </div>
                    </td>
                  </tr>
                  <tr>
                    <td colspan="2" class="table" align="left">
                      <div onclick="document.frmRetirementList.optoutputtype[2].checked=true;"
                           style="cursor:hand">
                        <input type="radio" name="optoutputtype" id="optoutputtype" value="HTML"></input>HTML
                                                                         Format
                      </div>
                    </td>
                  </tr>-->
              <tr class="tdH">
                <td colspan="2">
                <center>
                <input type="button" value="submit" onclick="displayCadreReport()"/>
                <input type="button" value="Cancel" onclick="self.close()"/>
                </center>
                </td>
              </tr>
             
            
    </table>
       </div>
        <br>
  
            
      </form>
  </body>
</html>