<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">
<%@ page session="false" contentType="text/html;charset=windows-1252"%>
<%@ page import="java.sql.*,java.util.*,Servlets.Security.classes.UserProfile"%>
<%@ include file="//org/Security/jsps/Check_SessionJSPF.jspf" %>
<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=windows-1252"/>
    <title>List of Employees Years Completion(Seniority Based)</title>
   <script type="text/javascript"> 
   function checkboxVal(){
   var count=0;
   for(i=0;i<document.frmlist_service.select1.length;i++){
      if(document.frmlist_service.select1[i].checked){
   	  count=count+1;
  	 }
   }
    if (count<1){
	alert("Please Select Employee Status");
	document.frmlist_service.select1[0].focus();
	return false;
	}
	return true;
   }
function frmrolesubmit1()
{
if(checkboxVal()){
		 if(document.frmlist_service.OffType[1].checked==true)
     {
			 
	 var desigval=0;
	 var optselect="";
	 var forquery="";

        var offi=0;
       var url=""; 
        if(document.frmlist_service.OffType[1].checked==true)
         {
                  if(document.frmlist_service.rad_off[0].checked) 
                  {
                       url=url+"&officeselected="+5000; 
                       document.frmlist_service.officeselected.value=5000;
                       document.frmlist_service.allofficeid.value="All";
                       forquery="All";
                  }
                     else 
                     {
                        
                            var region="";
                           // alert(document.frmlist_service.chkregion);
                           var check2=document.frmlist_service.chkregion; 
                          // alert(check2);
                            if(check2!=null)
                              {
                          //  alert('here'+document.frmlist_service.chkregion.length);
                          
                                        if(document.frmlist_service.chkregion.length)
                                        {
                                                  for(i=0;i<document.frmlist_service.chkregion.length;i++)
                                                    {
                                                            if(document.frmlist_service.chkregion[i].checked==true)
                                                              region= region+"'"+document.frmlist_service.chkregion[i].value +"'"+",";
                                                        
                                                            
                                                    }
                                                    if(region!="")
                                                    {
                                                        region=region.substring(0,region.length-1);
                                                         url=url+"&officeselected="+region;
                                                        // alert(url);
                                                       // alert("Region "+region);       
                                                         document.frmlist_service.officeselected.value=region;
                                                         forquery="region_office_id in ("+region+")";
                                                        // alert(forquery);
                                                         //alert(document.frmlist_service.officeselected.value);
                                                    }
                                                    else
                                                    {
                                                           alert('Select the Region.');
                                                            document.frmlist_service.cmbregion[0].focus(); 
                                                            return false;
                                                    }
                                            
                                        }
                                        else{
                                           
                                                if(document.frmlist_service.chkregion.checked==true)
                                                {
                                                            region= "'"+document.frmlist_service.chkregion.value +"'";
                                                            document.frmlist_service.officeselected.value=region;
                                                            offi=region;
                                                            forquery=forquery+"region_office_id in ("+region+")";
                                                           // alert(forquery);
                                                }
                                                 else
                                                    {
                                                           alert('Select the Region..');
                                                            document.frmlist_service.cmbregion.focus(); 
                                                            return false;
                                                    }
                                                     //alert(region);
                                                            
                                        }
                                }
                                 else
                                {
                                       alert('Select the Region...');
                                        document.frmlist_service.cmbregion.focus(); 
                                        return false;
                                }    
                            ////
                            
                          
                            
                            ////////
                            var circle="";
                            if(document.frmlist_service.rad_off[2].checked){    
                          //  alert("Circle");
                           var check0=document.frmlist_service.chkcircle;
                            if(check0!=null)
                              {
                              
                                       if(document.frmlist_service.chkcircle.length )
                                        {
                                                
                                                    for(i=0;i<document.frmlist_service.chkcircle.length;i++)
                                                    {
                                                            if(document.frmlist_service.chkcircle[i].checked==true)
                                                                    circle= circle+"'"+document.frmlist_service.chkcircle[i].value +"'"+",";
                                                                    // alert(circle);  
                                                    }
                                                    if(circle!="")
                                                    {
                                                        circle=circle.substring(0,circle.length-1);
                                                         url=url+"&officeselected="+circle;
                                                         document.frmlist_service.officeselected.value=circle;
                                                         forquery=forquery+" and circle_office_id in("+circle+")";
                                                       //  alert(forquery);
                                                    }
                                                    else
                                                    {
                                                           alert('Select the Circle.');
                                                            document.frmlist_service.cmbcircle.focus(); 
                                                             return false;
                                                    }
                                        }
                                        else{
                                          
                                                if(document.frmlist_service.chkcircle.checked==true)
                                                {
                                                            circle= "'"+document.frmlist_service.chkcircle.value+"'" ;
                                                            document.frmlist_service.officeselected.value=circle;
                                                            offi=circle;
                                                            forquery=forquery+" and circle_office_id in("+circle+")";
                                                          //  alert(forquery);
                                                           // alert(circle);
                                                }
                                                 else
                                                    {
                                                           alert('Select the Circle..');
                                                            document.frmlist_service.cmbcircle.focus(); 
                                                            return false;
                                                    }
                                                     //alert(region);
                                                            
                                        }
                                }
                                else
                                {
                                       alert('Select the Circle...');
                                       try{
                                        document.frmlist_service.cmbcircle.focus(); 
                                        }
                                        catch(e){
                                        document.frmlist_service.cmbregion.focus(); 
                                        }
                                         return false;
                                }
                            }
                            ////
                            
                           
                           
                            ////////
                            var division="";
                          //  alert("i am here"+document.frmlist_service.chkdivision);
                            if(document.frmlist_service.rad_off[3].checked){     
                            var check1=document.frmlist_service.chkdivision;
                            if(check1!=null)
                              {
                          
                                     if(document.frmlist_service.chkdivision.length )
                                        {
                                        
                                        
                                                for(i=0;i<document.frmlist_service.chkdivision.length;i++)
                                                {
                                                        if(document.frmlist_service.chkdivision[i].checked==true)
                                                                division= division+"'"+document.frmlist_service.chkdivision[i].value +"'"+",";
                                                        
                                                }
                                                
                                                if(division!="")
                                                {
                                                    division=division.substring(0,division.length-1);
                                                     url=url+"&officeselected="+division;
                                                     document.frmlist_service.officeselected.value=division;
                                                     offi=division;
                                                     forquery=forquery+" and division_office_id in ("+division+")";
                                                    // alert(forquery);
                                                     
                                                }
                                                else
                                                {
                                                       alert('Select the Division.');
                                                        document.frmlist_service.cmbdivision.focus(); 
                                                         return false;
                                                }
                                        }
                                        else
                                        {
                                                if(document.frmlist_service.chkdivision.checked==true)
                                                {
                                                                division= "'"+document.frmlist_service.chkdivision.value+"'";
                                                                    //division=division.substring(0,division.length-1);
                                                                 url=url+"&officeselected="+division;
                                                                 document.frmlist_service.officeselected.value=division;
                                                                 offi=division;
                                                                 forquery=forquery+" and division_office_id in ("+division+")";
                                                                 //alert(forquery);
                                                }
                                                else
                                                {
                                                       alert('Select the Division..');
                                                        document.frmlist_service.cmbdivision.focus(); 
                                                         return false;
                                                }
                                        
                                        
                                        }
                                }
                                 else
                                {
                                       alert('Select the Division...');
                                       try{
                                        document.frmlist_service.cmbdivision.focus(); 
                                        }catch(e)
                                        {
                                        try{
                                        document.frmlist_service.cmbcircle.focus(); 
                                        }
                                        catch(e){
                                        document.frmlist_service.cmbregion.focus(); 
                                        }
                                        }
                                         return false;
                                }
                            }   
                          
                     }     
                            
                }
                else
                 url=url+"&officeselected=alloffice";
  // alert(url);
  // alert(document.frmlist_service.officeselected.value);
  // document.getElementById("officeselected").value=offi;
  // alert(forquery);
   document.frmlist_service.allofficeid.value=forquery;
  // alert("Final "+document.frmlist_service.allofficeid.value);
  if(document.frmlist_service.allofficeid.value==""){
	  alert('Please Select Office');
	  return false;
  } 
 
     }
return true;
 }
 else{
  return false;
 }
  
}
</script>
    <script type="text/javascript"
            src="<%=request.getContextPath()%>/org/Library/scripts/comJS.js"></script>
    <script type="text/javascript" src="../scripts/Office_Wise_Gender.js"></script>
    
    <link href="../../../../../../css/Sample3.css" rel="stylesheet" media="screen"/>
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
    
  </head>
  <%
  
  Connection connection=null;
  PreparedStatement ps=null;
  ResultSet results=null;
  ResultSet results1=null;
 
  
  
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
  
  <body>
  <form name="frmlist_service" id="frmlist_service" onsubmit="return frmrolesubmit1()" action="../../../../../../Office_Wise_Gender" method="post">
   
      
        <input type="hidden" name="allofficeid" id="allofficeid">
       <input type="hidden" name="offlevel">
            <input type="hidden" name="office">
            <input type="hidden" name="officetype">
            <input type="hidden" name="officeselected">
            <input type="hidden" name="outputtype">
            <input type="hidden" name="ordertype">
      
      
  <!--action="ListofEmp_YearExp_Serv" method="post"-->
  <table cellpadding="3" cellspacing="2" border="1" width="100%"> 
  
  <tr class="tdH">
  <th colspan="3" align="center">Office Wise Employees Gender</th>
  </tr>
  
  
   <tr>
            <td colspan="3" ><input type="radio" name="OffType" value="All" id="OffType" onclick="HideOfficeType()" checked="checked" >All Office &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
				    <input type="radio" name="OffType" id="OffType" value="Specific" onclick="VisibleOfficeType()">Specific Office	

            
        </tr>
      
      <tr>
            <td  colspan="4">
                <div style="display:none" id="SpecificOffType" >
                        <table  border='1' width="100%">
                            <tr>
                                      <td  colspan="3" class="tdH">Selection of Office Jurisdiction</td>
                                    </tr>
                                    <tr>
                                      <td  colspan="3" class="table" align="left">
                                                                
                                                  <%
                                          HttpSession session=request.getSession(false);
                                          UserProfile empProfile=(UserProfile)session.getAttribute("UserProfile");
                                          
                                        System.out.println("user id::"+empProfile.getEmployeeId());
                                        int empid=empProfile.getEmployeeId();
                                        int oid=0;
                                        String deptid="",offlevel="";
                                        
                                             try
                                            {
                                                   
                                                    ps=connection.prepareStatement("select b.OFFICE_LEVEL_ID,b.office_id from hrm_emp_current_posting a "+
                                                            " inner join com_mst_offices b on b.office_id=a.office_id "+
                                                            " where a.employee_id=?" );
                                                    ps.setInt(1,empid);
                                                    results=ps.executeQuery();
                                                         if(results.next()) 
                                                         {
                                                            offlevel=results.getString("OFFICE_LEVEL_ID");
                                                            oid=results.getInt("office_id");
                                                         }
                                                    results.close();
                                                    ps.close();
                                                    System.out.println("office id:"+oid);
                                                    System.out.println("dept id:"+deptid);
                                                    
                                                    }
                                                     catch(Exception e)
                                            {
                                                System.out.println(e);
                                            }
                                            
                                            
                       
                                        
                                        %>
                                             
                                                <%   
                                                String  profile=(String)session.getAttribute("profile");%>
                                              
                                         <%  if(offlevel.equalsIgnoreCase("HO") || profile.equalsIgnoreCase("other"))
                                           {%>
                                         
                                         <div id="divallofficelevel" style="cursor:hand">                          
                                          <input type="radio" name="rad_off" id="rad_ho" value="ho" checked="checked" onclick="selectoption2()"></input>HO
                                          <input type="radio" name="rad_off" id="rad_reg" value="rg" onclick="selectoption2()"></input>Region
                                          <input type="radio" name="rad_off" id="rad_cir" value="cr" onclick="selectoption2()"></input>Circle
                                          <input type="radio" name="rad_off" id="rad_div" value="dv" onclick="selectoption2()"></input>Division                    </div>
                                        
                                        <% }
                                        
                                          else if(offlevel.equalsIgnoreCase("RN")) { %>
                                          
                                          <div id="divallofficelevel" style="cursor:hand">                          
                                          <input type="radio" name="rad_off" id="rad_ho" value="ho" onclick="selectoption2()" disabled="disabled"></input>HO
                                          <input type="radio" name="rad_off" id="rad_reg" value="rg" onclick="selectoption2()"></input>Region
                                          <input type="radio" name="rad_off" id="rad_cir" value="cr" onclick="selectoption2()"></input>Circle
                                          <input type="radio" name="rad_off" id="rad_div" value="dv" onclick="selectoption2()"></input>Division                    </div>
                                        
                                        <%}
                                           else if(offlevel.equalsIgnoreCase("CL")) {%>
                                           
                                          <div id="divallofficelevel" style="cursor:hand">                          
                                          <input type="radio" name="rad_off" id="rad_ho" value="ho" checked="checked" onclick="selectoption2()" disabled="disabled"></input>HO
                                          <input type="radio" name="rad_off" id="rad_reg" value="rg" onclick="selectoption2()" disabled="disabled"></input>Region
                                          <input type="radio" name="rad_off" id="rad_cir" value="cr" onclick="selectoption2()"></input>Circle
                                          <input type="radio" name="rad_off" id="rad_div" value="dv" onclick="selectoption2()"></input>Division                      </div>
                       
                                        <%}
                                            else if(offlevel.equalsIgnoreCase("DN")) {%>
                                            
                                            <div id="divallofficelevel" style="cursor:hand">                          
                                          <input type="radio" name="rad_off" id="rad_ho" value="ho" checked="checked" onclick="selectoption2()" disabled="disabled"></input>HO
                                          <input type="radio" name="rad_off" id="rad_reg" value="rg" onclick="selectoption2()" disabled="disabled"></input>Region
                                          <input type="radio" name="rad_off" id="rad_cir" value="cr" onclick="selectoption2()" disabled="disabled"></input>Circle
                                          <input type="radio" name="rad_off" id="rad_div" value="dv" onclick="selectoption2()"></input>Division                      </div>
                                            
                                            <%}
                                            
                                               else
                                               {
                                                 System.out.println("No Office id");
                                               }%>                    </td>
                                    </tr>
                                
                                     
                                    <tr class="table" id="trofficeselection1" style="display:none">
                                      <td colspan="5" class="tdH" style="width: 1025px">Selection of Office</td>
                                    </tr>
                                    <tr class="table" id="trofficeselection" style="display:none" VALIGN="TOP">
                                      <td colspan="5">
                                        <div align="left">
                                          <table  border="0" width="100%">
                                            <tr>
                                              <td>
                                                <div id="divregion" style="display:none">Region</div>                          </td>
                                              <td>
                                                <div id="divcircle" style="display:none">Circle</div>                          </td>
                                              <td>
                                                <div id="divofftype" style="display:none">Office Type</div>                          </td>
                                              <td>
                                                <div id="divdivision" style="display:none">Division</div>                          </td>
                                            </tr>
                                            <tr VALIGN="TOP">
                                              <td>
                                                <table border="0" cellpadding="0" cellspacing="0"  width="100%">
                                                  <tr VALIGN="TOP">
                                                    <td>
                                                      <select name="cmbregion" id="cmbregion"
                                                              style="display:none;width:100%"
                                                              onclick="getRegion()">
                                                        <option>Select the Regions</option>
                                                      </select>                                </td>
                                                  </tr>
                                                  <tr>
                                                    <td>
                             <div class="scroll" id="diviframeregion">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</div>                                </td>
                                                  </tr>
                                                </table>                          </td>
                                              <td>
                                                <table border="0" cellpadding="0" cellspacing="0" width="100%" >
                                                  <tr VALIGN="TOP">
                                                    <td>
                                                      <select name="cmbcircle" id="cmbcircle"
                                                              style="display:none;width:100%"
                                                              onclick="getCircle('null')">
                                                        <option>Select the Circle</option>
                                                      </select>                                </td>
                                                  </tr>
                                                  <tr>
                                                    <td>
                                                      <div class="scroll" id="diviframecircle">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</div>                                </td>
                                                  </tr>
                                                </table>                          </td>
                                              
                                                <td>
                                                <table border="0" cellpadding="0" cellspacing="0"   width="100%">
                                                  <tr VALIGN="TOP">
                                                    <td>
                                                      <select name="cmbofftype" id="cmbofftype"
                                                              style="display:none;width:100%"
                                                              onclick="getofftype('null')">
                                                        <option>Select the Office Type</option>
                                                      </select>                                </td>
                                                  </tr>
                                                  <tr>
                                                    <td>
                                                      <div class="scroll" id="diviframeofftype">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</div>                                </td>
                                                  </tr>
                                                </table>                          </td>
                                              
                                              <td>
                                                <table border="0" cellpadding="0" cellspacing="0"
                                                       width="100%">
                                                  <tr VALIGN="TOP">
                                                    <td>
                                                      <select name="cmbdivision" id="cmbdivision"
                                                              style="display:none;width:100%"
                                                              onclick="getDivision('null')">
                                                        <option>Select the Division</option>
                                                      </select>                                </td>
                                                  </tr>
                                                  <tr>
                                                    <td>
                                                      <div class="scroll" id="diviframedivision">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</div>                                </td>
                                                  </tr>
                                                </table>                          </td>
                                            </tr>
                                          </table>
                                        </div>                  </td>
                                    </tr>
                                </table>
                         </div>                        
                    </td>
                </tr>
           
           
  
  
  
  
   
  
   <tr class="table">
   <td align="left" style="width: 172px">Employee Status   
   </td>
   <td>
    <input type="checkbox" name="select1" value="WKG" id="worselect">Working <br></br>
   <input type="checkbox" name="select1" value="TRT" id="traselect">Transit<br></br>
   <input type="checkbox" name="select1" value="DPN" id="depselect">Deputation
   </td>
   </tr>
   <tr class="table">
   <td align="left">Report&nbsp;Type   
   </td>
   <td>
   <select name="cmbReportType">
   <option value="PDF" selected="selected">PDF</option>
   <option value="EXCEL">EXCEL</option>
   <option value="HTML">HTML</option>
   </select>
   </tr>
   
   <tr class="tdH">
   <td colspan="2">
     <div align="center">
       <input type="submit" name="submit" value="Submit"  />
       <input type="button" value="Clear" onclick="clearall()"/>
       <input type="button" value="Exit" onclick="self.close()"/>       
     </div>
   </td>
   </tr>
  
  </table>
  </form>
  </body>
</html>