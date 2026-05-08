<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">
<%@ page contentType="text/html;charset=windows-1252"%>
<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=windows-1252"/>
    <title>Pms_Dcb_Beneficiary_Metre_Report</title>
    <link href='../../../../../css/Sample3.css' rel='stylesheet' media='screen'/>
    <script type="text/javascript" src="../scripts/Pms_Dcb_Beneficiary_Metre_Report.js"></script>
    <style type="text/css">
					
					table.border1
					{
					color:#000000;
					padding:0px;
					border-top: 1px solid #dddddd;
					border-left: 1px solid #dddddd;
					border-bottom: 0px solid #dddddd;
					border-right: 0px solid #dddddd;
					font-size: 14  px;
					
					}
					
					table.border1 th, table.border1 td 
					{
					padding:5px;
					padding-bottom:2px;
					border-top: 0px solid #dddddd;
					border-left: 0px solid #dddddd;
					border-bottom: 1px solid #dddddd;
					border-right: 1px solid #dddddd;
					}
										
				</style>
  </head>
  <body onload="divisionname();loadbeneficiarytype();loadsubdivision();">
  <form name="beneficary_meter_report" action="">
    <table border="0" width="100%" align="center" cellpadding="0" cellspacing="0" class="border1">
    <tr align="center" style="color:black">
                    <td colspan="6">
                        <div align="center">
                        <strong>  Beneficiary Meter Report 
                            <label id="divisionname"></label>
                             </strong>
                        </div>
                    </td>
                </tr>
        <tr>
                    <td style="padding-left:150px;" colspan="2">
                         Beneficiary Type
                    
                    </td>
                   <td style="padding-left:100px;">
                         <select id="Beneficiary_type" onchange="loadbeneficiaryname();loadreport('benreport');loadreport('bentype');loadreport('subreport');loadreport('benname')">
                               <option value="" selected="selected">- - - Select - - -</option>
                        </select>
                      
                    </td>
        </tr>
         <tr>
              <td style="padding-left:150px;" colspan="2">
                            Sub Division 
                </td>
                <td style="padding-left:100px;">
              
                        <select id="SubDivision" tabindex="2" onchange="loadreport('benreport');loadreport('benreport');loadreport('subreport');loadreport('bentype');loadreport('benname')">
                            <option value="" selected="selected">- - - Select - - -</option>
                        </select>
                </td>
        </tr>
         <tr>
                <td style="padding-left:150px;" colspan="2">
                            Beneficiary Name 
                            
                 </td>
                 <td style="padding-left:100px;">
                        <select id="Beneficiary_Name" onchange="loadreport('benreport');loadreport('bentype');loadreport('benname');">
                            <option value="" selected="selected">- - - Select - - -</option>
                        </select> 
                </td>
        </tr>  
    </table>
    <br>
    
    <table id="existing" border="1" width="100%" align="center" class="border1">
                <tr>
                 
                   <th >Sno</th>
                      <th id="meterlabel">Metre Location</th>
                    <th>Sub Division</th>
                    <th>Scheme</th>
                 
                    <th>Tariff Rate</th>
                    <th>Metre fixed</th>
                    <th>Metre Working</th>
                
                </tr>
                <tbody id="getvaluerows">
                
                    
                
                </tbody>
                
               
            </table>
            <br>
            <table align="center">
            <tr>
            <td>
             <input type="button" name="cmdexit" value="Exit" id="cmdlist" onclick="exitwindow();"/>
             <td>
             </tr>
             </table>
        </form>
  
  </body>
</html>