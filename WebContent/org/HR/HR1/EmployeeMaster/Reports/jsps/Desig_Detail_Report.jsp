<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">
<%@ page session="false" contentType="text/html;charset=windows-1252"%>
<%@ page import="java.sql.*,java.util.*,Servlets.Security.classes.UserProfile"%>
<%@ include file="//org/Security/jsps/Check_SessionJSPF.jspf" %>
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
            ConnectionString = strdsn.trim() + "://" + strhostname.trim() + ":" + strportno.trim() + "/" +strsid.trim() ;    // Postgres DB  Connection
            //ConnectionString = strdsn.trim() + "@" + strhostname.trim() + ":" + strportno.trim() + ":" +strsid.trim() ;

             Class.forName(strDriver.trim());
             connection=DriverManager.getConnection(ConnectionString,strdbusername.trim(),strdbpassword.trim());
  }
  catch(Exception e)
  {
    System.out.println("Exception in connection...."+e);
  }
  
  
  %>
<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=windows-1252"/>
    <title> Designation Wise Detail Report</title>
      <script type="text/javascript" src="<%=request.getContextPath()%>/org/Library/scripts/comJS.js"></script>

    <script type="text/javascript"
            src="../scripts/Desig_Detail_ReportJS.js"></script>
    <link href="../../../../../../css/Sample3.css" rel="stylesheet"
          media="screen"/>
    <style type="text/css">
      body {
      background-color: #ffffff; 
}
      a:link { color: #002173; }
      
      <!--div.scroll {	
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
  </head>
  
  <body><form name="frmValidationSummaryRep">
  
       
            <input type="hidden" name="offlevel">
            <input type="hidden" name="office">
            <input type="hidden" name="officetype">
            <input type="hidden" name="officeselected">
            <input type="hidden" name="designationlevel">
            <input type="hidden" name="designation">
            <input type="hidden" name="outputtype">
            <input type="hidden" name="ordertype">
            
  
      <div align="center">
        <table border='2' width="100%">
          <tr>
            <td colspan="2" align="left" valign="top" class="table" width="60%">
              <table border="0" width="100%">
             
                <tr>
                  <td colspan='2' class="tdH" align="center"><strong> Designation Wise Detail Report</strong></td>
                </tr>
                
                
           
       
       <tr>
                  <td colspan="2" class="tdH">Selection of Designation</td>
                </tr>
       
      
         <tr>
            <td colspan="3" ><input type="radio" name="desType" value="All" id="desType" onclick="HideOfficeType()" checked="checked" >All &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
				    <input type="radio" name="desType" id="desType" value="Specific" onclick="VisibleOfficeType()">Specific	

            
        </tr>
         <tr class="table" id="divdest" style="display:block; width: 678px">
                  <td>
                    <div align="left" style="display:none" id="SpecificDes">
                    <select name="cmbsgroup" id="cmbsgroup" onchange="getDesignation()" style="width: 229px">
                <option value="0">Select Service Group</option>
                        <%
           ResultSet rs=null;
           try
           {
           ps=connection.prepareStatement("select SERVICE_GROUP_ID,SERVICE_GROUP_NAME from HRM_MST_SERVICE_GROUP  order by SERVICE_GROUP_NAME");
            rs=ps.executeQuery();
              int strcode=0;
            String strname="";          
            while(rs.next())
            {
              
               
                strcode=rs.getInt("SERVICE_GROUP_ID");
                strname=rs.getString("SERVICE_GROUP_NAME");
                System.out.println("service group code:"+strcode);
                System.out.println("service gorup name:"+strname);
                out.println("<option value='"+strcode+"'>"+strname+"</option>");
                
             }
          }
          catch(Exception e)
          {
            System.out.println("Exception in grid.."+e);
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
                <tr class="table" id="desigblock" style="display:none">
               <td>
               <div class="scroll" align='left' id="divdesignation">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</div>
               </td>
               </tr>  
        <tr class="table">
   <td  colspan="2" class="tdH">Employee Status   
   </td>
   </tr>
   <tr>
   <td align="left"><table border="0">
   <tr><td align="left"><input type="checkbox" name="select1" value="WKG" id="worselect">Working </td>
   <td align="left"><input type="checkbox" name="select1" value="TRT" id="traselect">Transit</td>
   <td align="left"><input type="checkbox" name="select1" value="DPN" id="depselect">Deputation
   </tr>
   <tr><td align="left">  <input type="checkbox" name="select1" value="STT" id="worselect">Studies Transit </td>
   <td align="left"><input type="checkbox" name="select1" value="DPT" id="traselect">Deputation Transit </td>
   <td align="left"><input type="checkbox" name="select1" value="SAN" id="depselect">Superannuation</td>
   </tr>
   <tr><td align="left"><input type="checkbox" name="select1" value="SUS" id="worselect">Supspension </td>
   <td align="left"><input type="checkbox" name="select1" value="LLV" id="traselect">Leave</td>
   <td align="left"><input type="checkbox" name="select1" value="STU" id="depselect">Studies</td>
   </tr>
   <tr><td align="left"><input type="checkbox" name="select1" value="UAL" id="worselect">Unauthorised Absent (Absconded)</td>
   <td align="left"><input type="checkbox" name="select1" value="TCL" id="traselect">Transit Cum Leave</td>
   <td align="left"><input type="checkbox" name="select1" value="LOP" id="depselect">Loss Of Pay</td>
   </tr>
   <tr><td align="left"><input type="checkbox" name="select1" value="CMW" id="worselect">Compulsory Wait </td>
   <td align="left"><input type="checkbox" name="select1" value="TRA" id="worselect">Training</td>
   <td align="left"><input type="checkbox" name="select1" value="VRS" id="traselect">Voluntary Retirement</td>
   </tr>
   <tr><td align="left"><input type="checkbox" name="select1" value="DIS" id="depselect">Dismissed</td>
   <td align="left"><input type="checkbox" name="select1" value="DTH" id="worselect">Death </td>
   <td align="left"><input type="checkbox" name="select1" value="ABR" id="traselect">Abroad</td>
   </tr>
   <tr><td align="left"><input type="checkbox" name="select1" value="RES" id="depselect">Resigned</td>
   <td></td>
   <td></td>
   </tr>
   </table>

   </td>
   </tr>
                  <tr>
                    
             <td colspan="2" align="left" valign="top" class="table">
              <div align="center">
                <table border="0" width="100%">
                  <tr>
                    <td colspan="2" class="tdH">Report Output Format</td>
                  </tr>
                  <tr>
                    <td colspan="2" class="table" align="left">
                      <div onclick="document.frmEmployeeDetailRep.optoutputtype[0].checked=true;"
                           style="cursor:hand">
                        <input type="radio" name="optoutputtype" checked></input>PDF
                                                                         Format
                      </div>
                    </td>
                  </tr>
                  <tr>
                    <td colspan="2" class="table" align="left">
                      <div onclick="document.frmEmployeeDetailRep.optoutputtype[1].checked=true;"
                           style="cursor:hand">
                        <input type="radio" name="optoutputtype"></input>EXCEL
                                                                         Format
                      </div>
                    </td>
                  </tr>
                  <tr>
                    <td colspan="2" class="table" align="left">
                      <div onclick="document.frmEmployeeDetailRep.optoutputtype[2].checked=true;"
                           style="cursor:hand">
                        <input type="radio" name="optoutputtype"></input>HTML
                                                                         Format
                      </div>
                    </td>
                  </tr>
                </table>
              </div>
            </td>
          </tr>
          <tr>
            <td colspan="2" class="tdH" align="center"><input type="button" value="Submit" onclick="frmsubmit()">
            <input type="button" id="cmdcancel" name="cancel" value="Cancel" onclick="self.close();"> </td>
          </tr>
        </table>
      </div>
    </form></body>
</html>