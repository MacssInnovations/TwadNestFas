<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">

<html>
    <head>
        <meta http-equiv="Content-Type"
              content="text/html; charset=windows-1252 "/>
        <link href='../../../../../css/Sample3.css' rel='stylesheet'
              media='screen'/>
       <link href='../../../../../css/CalendarControl.css' rel='stylesheet'
              media='screen'/>
      <!-- Before and After dis --><script type="text/javascript"
               src="../../../../../org/Library/scripts/CalendarControl.js"></script>
           <!--Before dis-->  <!-- <script type="text/javascript" src="../../../../../org/HR/HR1/EmployeeMaster/scripts/CalendarControl.js"></script>-->
        <script type="text/javascript"
                src="../scripts/Pms_Dcb_Beneficiary_metre.js"></script>
            <!--    <script type="text/javascript"
                src="../scripts/calend.js"></script>-->
        <!-- <link href="../includes/popupwindow/multibox.css" rel="stylesheet" type="text/css" />
      <script language="javascript" type="text/javascript" src="../includes/popupwindow/mootools.js"></script>
<script language="javascript" type="text/javascript" src="../includes/popupwindow/multibox.js"></script>
<script language="javascript" type="text/javascript" src="../includes/popupwindow/overlay.js"></script>-->
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
        <title>Pms_Dcb_Trn_Beneficiary_metre</title>
    </head><!--doFunction('get');-->
    <body onload="divisionname();loadbeneficiarytype();loadschemes();loadsubdivision();">
    <form name="beneficary_meter" action="">
            <table border="1" width="85%" align="center" cellpadding="0" cellspacing="0" class="border1">
                <tr class="tdH" align="center" style="color:black">
                    <td colspan="4">
                        <div align="center">
                        <strong>  Beneficiary Meter Details 
                            <label id="divisionname"></label>
                             </strong>
                        </div>
                    </td>
                </tr>
                <tr class="table">
                    <td style="padding-left:10px;" width="50%" colspan="2">Meter Location ID </td>
                    <td colspan="2" style="padding-left:10px;">
                        <input type="text" name="Meter_Sno" id="Meter_Sno"
                               maxlength="5" size="5" readonly="readonly"
                               style="background-color: #ececec" tabindex="1"/>
                        <small>(Auto generated)</small>
                    </td>
                </tr>
                <tr class="table">
                    <td style="padding-left:10px;" colspan="2">
                         Beneficiary Type
                        <font color="#ff2121">*</font>
                    </td>
                    <td style="padding-left:10px;" colspan="2">
                    <!--For loading schemes <select id="Beneficiary_type"
                                onchange="loadhabitations(); loadbeneficiaryname(); loadschname();" tabindex="4">-->
                               <select id="Beneficiary_type" onchange="districload();
                                loadhabitations(); loadbeneficiaryname();" tabindex="2">
                            <option value="" selected="selected">- - - Select - - -</option>
                        </select>
                      
                    </td>
                </tr>
                </table>
                <div id="district">
                <table border="1" width="85%" align="center" cellpadding="0" cellspacing="0" class="border1">
                    <tr class="table">
	<td width="25%" colspan="1" style="padding-left:10px;">
		District 
                                                </td>
                        <td width="25%" style="padding-left:10px;">
		  <select id="district_Name" name="district_Name" onchange="loadblocks();" style="min-width: 190px; max-width: 190px; width : 190px;" tabindex="3">
                           <option value="" selected="selected">- - - Select - - -</option>
                    </select> 
	</td>
                        <td width="25%" style="padding-left:10px;">Block&nbsp;&nbsp;</td>
                        <td style="padding-left:10px;" width="25%">
                            <select id="block_Name" name="block_Name" onchange="loadbenname();" style="min-width: 190px; max-width: 190px; width : 190px;" size="1" tabindex="4">
                           <option value="" selected="selected">- - - Select - - -</option>
                    </select> 
                        </td>
                    </tr>
                </table>
                </div>
                 <table border="1" width="85%" align="center" cellpadding="0" cellspacing="0" class="border1">
               <tr class="table">
                    <td style="padding-left:10px;" colspan="2" width="50%">
                        <label id="benname" >     Beneficiary Name </label> <font color="#ff2121">*</font>
                            
                  </td>
                        <td style="padding-left:10px;"><select id="Beneficiary_Name" onchange="getvaluegrid();loadcategory(); loadhabitationlist();" tabindex="5">
                            <option value="" selected="selected">- - - Select -
                                                      
                                                                 - -</option>
                        </select> 
                         <input type="hidden" name="BENEFICIARY_TYPE_ID" maxlength="35"
                               size="6" id="BENEFICIARY_TYPE_ID" />
                         <input type="hidden" name="OTHERS_PRIVATE_SNO" maxlength="35"
                               size="6" id="OTHERS_PRIVATE_SNO"/>
                                <input type="hidden" name="VILLAGE_PANCHAYAT_SNO" maxlength="35"
                               size="6" id="VILLAGE_PANCHAYAT_SNO"/>
                                <input type="hidden" name="URBANLB_SNO" maxlength="35"
                               size="6" id="URBANLB_SNO"/>
                                 <input type="hidden" name="Consumption_Category"
                               maxlength="35" size="35"
                               id="Consumption_Category"
                               style="background-color: #ececec" tabindex="6"/>
                               <input type="hidden" name="Consumption_Categoryid"
                               maxlength="35" id="Consumption_Categoryid"/>
                               </td>
            
               <!-- <tr class="table">
                        <td style="padding-left:10px;" colspan="2">    Consumption Category</td>
                        <td style="padding-left:10px;">
                            <input type="text" name="Consumption_Category"
                               maxlength="35" readonly="readonly" size="35"
                               id="Consumption_Category"
                               style="background-color: #ececec" tabindex="6"/>
                               <input type="hidden" name="Consumption_Categoryid"
                               maxlength="35" id="Consumption_Categoryid"/>
                        </td>
                </tr>-->
                 
                 
                 
                          
                 
                 <tr class="table">
                    <td style="padding-left:10px;" colspan="2">
                            Sub Division <font color="#ff2121">*</font>
                            
                        </td>
                        <td style="padding-left:10px;" colspan="2"><!--<select id="SubDivision" onchange="subdivision();">-->
                        <select id="SubDivision" tabindex="2" onchange="schemecheck();habcheckdup();metercheck()" tabindex="6">
                            <option value="" selected="selected">- - - Select - - -</option>
                        </select></td>
                    </tr>
                
               </tr>
                    <tr class="table">
                        <td style="padding-left:10px;" colspan="2"> Schemes <font color="#ff2121">* &nbsp;&nbsp;</font>&nbsp;&nbsp;</td>
                        <td style="padding-left:10px;" colspan="2">
                            <!--<select id="Schemes" onchange="Schemesval();">-->
                                                            
                            <select id="Schemes" tabindex="3" onchange="loadschname(); schemecheck();habcheckdup();metercheck();" tabindex="7">
                            <option value="" selected="selected">- - - Select - - -</option>
                        </select>
                         <input type="hidden" name="SCH_TYPE_ID" maxlength="35"
                               size="6" id="SCH_TYPE_ID"/>
                        </td>

                </tr>
              
               
            </table>
            <div id="Habitation">
                <table border="1" width="85%" align="center" cellpadding="0" cellspacing="0" class="border1">
                    <tr class="table">
                        <td style="padding-left:10px;" width="50%">Habitation Name</td>
                        <td style="padding-left:10px;">
                            <select id="Habitation_Name" onchange="loadhabname(); habcheckdup();" tabindex="8">
                                <option value="" selected="selected">- - -
                                                                     Select - - -</option>
                            </select>
                        </td>
                    </tr>
                </table>
            </div>
             <div id="Metre_Location_div">
            <table border="1" width="85%" align="center" cellpadding="0" cellspacing="0" class="border1">
                
                
                <tr class="table">
               
                    <td style="padding-left:10px;" colspan="2"  width="50%">
                    
                        <label id="location">Metre Location</label>
                        <font color="#ff2121">*</font>
                  
                    </td>
                    
                    <td style="padding-left:10px;" colspan="2"  width="50%">
                   
                        <input type="text" name="Metre_Location" maxlength="35"
                               size="35" id="Metre_Location" 
                                tabindex="9" onblur="metercheck()";/>
                               
                             
                               
                    </td>
                </tr>
                </table>
                 </div>
                <table border="1" width="85%" align="center" cellpadding="0" cellspacing="0" class="border1">
                <tr class="table">
                    <td style="padding-left:10px;" width="50%" colspan="2">
                        Tariff Rate 
                        <small>(Rs)</small> 
                        <font color="#ff2121">*</font>
                    </td>
                    <td style="padding-left:10px;" colspan="2">
                        <input type="text" name="Tariff_Id" maxlength="35"
                               size="6" id="Tariff_Id" readonly="readonly" value="1"
                               style="background-color: #ececec" tabindex="9" onkeyPress="return numonly(event);"/>
                               <input type="hidden" name="Tariff_Id_val" maxlength="35"
                               size="6" id="Tariff_Id_val"/>
                    </td>
                </tr>
                <tr class="table">
                    <td style="padding-left:10px;" colspan="2">Whether Metre fixed?</td>
                    <td style="padding-left:10px;" colspan="2">
                        <input type="radio" name="meterfixed" id="meterfixed" value="y" onclick="meterdisplay()"></input>Yes
                        <input type="radio" name="meterfixed" id="meterfixed" value="n" onclick="meterdisplay()"></input>No
                    </td>
                </tr>
            </table>
            <div id="prevref">
                <table border="1" width="85%" align="center" cellpadding="0" cellspacing="0" class="border1">
                    <tr class="table">
                        <td colspan="2" style="padding-left:10px;" width="50%">Whether Metre Working?</td>
                        <td colspan="2" style="padding-right:130px; padding-left:10px;">
                            <input type="radio" name="meterworking" id="meterworking" value="y" onclick="chmeterworking()"></input>Yes
                            <input type="radio" name="meterworking" id="meterworking" value="n" onclick="chmeterworking()"></input>No
                        </td>
                    </tr>
                    <tr class="table">
                        <td colspan="2" style="padding-left:10px;">Metre Type<font color="#ff2121">*</font></td>
                        <td colspan="2" style="padding-left:10px;">
                        <input type="radio" name="Metre_Type" id="Metre_Type" value="1" onclick="MetreType()"></input>KL
                         <input type="radio" name="Metre_Type" id="Metre_Type" value="2" onclick="MetreType()"></input>ML
                          <!--  <select id="Metre_Type">
                                <option value="" selected="selected">- - - Select - - -</option>
                                <option value="0">KL</option>
                                <option value="1">ML</option>
                            </select>-->
                        </td>
                    </tr>
                    <tr class="table">
                        <td colspan="2" style="padding-left:10px;">Multiplying factor</td>
                        <td colspan="2" style="padding-left:10px;">
                            <input type="text" name="Multiply_factor"
                                   maxlength="35" size="4" id="Multiply_factor" value="1"
                                    onkeyPress="return numonly(event);"/>
                        </td>
                    </tr>
                    <tr class="table">
                       <td width="32%" colspan="1" style="padding-left:10px;">
                            Metre initial reading <font color="#ff2121">*</font>
                            
                        </td>
                        <td width="18%" style="padding-left:10px;"><input type="text" name="Metre_init_reading"
                                   id="Metre_init_reading" maxlength="35"
                                   size="6"  onkeyPress="return numonly(event);"/></td>
                        <td width="31%" style="padding-left:10px;">Initial Reading Record date&nbsp;&nbsp;</td>
                        <td style="padding-left:10px;">
                            <input type="text" name="Init_Reading_Record_date"
                                   maxlength="35" size="8"
                                   id="Init_Reading_Record_date"
                                   />
                            <img src="../../../../../images/calendr3.gif"
                                 alt="calendar"
                                 onclick="showCalendarControl(document.beneficary_meter.Init_Reading_Record_date)"></img>
                        </td>
                    </tr>
                </table>
            </div>
            <div id="alloted_quan">
           
            <table border="1" width="85%" align="center" cellpadding="0" cellspacing="0" class="border1">
                    <tr class="table">
                        <td colspan="2" style="padding-left:10px;" width="50%">Whether Alloted Quantity Applicable?</td>
                        <td colspan="2" style="padding-right:130px; padding-left:10px;">
                            <input type="radio" name="Applicableval" id="Applicableval" value="y" onclick="Applicable()"></input>Yes
                            <input type="radio" name="Applicableval" id="Applicableval" value="n" onclick="Applicable()" checked="checked"></input>No
                        </td>
                    </tr>
            </table>
            </div>
            <div id="fixed">
            <table border="1" width="85%" align="center" cellpadding="0" cellspacing="0" class="border1">
                <tr class="table">
                    <td width="32%" colspan="1" style="padding-left:10px;">
                            Allotted Quantity <small>(Rs)</small>&nbsp;&nbsp;&nbsp;&nbsp;                        </td>
                  <td width="18%" style="padding-left:10px;"><input type="text" name="Allotted_Qty" maxlength="35"
                               size="6" id="Allotted_Qty"
                                onkeyPress="return numonly(event);"/></td>
                        <td width="31%" style="padding-left:10px;">Minimum Billing Quantity <small>(Rs)</small></td>
                        <td width="19%" style="padding-left:10px;">
                            <input type="text" name="Min_bill_Qty" maxlength="35"
                               size="6" id="Min_bill_Qty"
                                onkeyPress="return numonly(event);"/>
                            
                  </td>
                </tr>
               
               
                   <!--<td colspan="2" style="padding-left:10px;">
                        Excess Tariff Rate 
                        <small>(Rs)</small>
                    </td>
                    <td colspan="2" style="padding-left:10px;">
                        <input type="text" name="Excess_Tariff_Rate"
                               maxlength="35" size="6" id="Excess_Tariff_Rate" onkeyPress="return numonly(event);"/>
                    </td>-->
                    
                     <tr class="table">
                    <td width="32%" colspan="1" style="padding-left:10px;">
                        Excess Tariff Rate 
                        <small>(Rs)</small>
                    </td>
                    <td width="18%" style="padding-left:10px;">
                        <input type="text" name="Excess_Tariff_Rate"
                               maxlength="35" size="6" id="Excess_Tariff_Rate" value="1" onkeyPress="return numonly(event);"/>
                    </td>
                    <td colspan="2" style="padding-left:10px;">
                        <input type="hidden" name="dsd"/>
                    </td>
                  <!--  <td width="32%" colspan="1" style="padding-left:10px;">
                            Excess Tariff Rate &nbsp;&nbsp;&nbsp;&nbsp;                        </td>
                  <td width="18%" style="padding-left:10px;"><input type="text" name="Excess_Tariff_Rate" maxlength="35"
                               size="6" id="Excess_Tariff_Rate"
                                onkeyPress="return numonly(event);"/></td>
                <td width="31%" style="padding-left:10px;"></td>
                <td width="19%" style="padding-left:10px;"></td>-->
                </tr>
                </table>
                </div>
               <table border="1" width="85%" align="center" cellpadding="0" cellspacing="0" class="border1">
                <tr class="table">
                    <td width="32%" colspan="1" style="padding-left:10px;">
                            Service Connection No&nbsp;&nbsp;&nbsp;&nbsp;                        </td>
                  <td width="18%" style="padding-left:10px;"><input type="text" name="Service_Connection"
                               maxlength="35" size="6" id="Service_Connection"
                                onkeyPress="return numonly(event);"/></td>
                        <td width="31%" style="padding-left:10px;">Service Connection Date</td>
                        <td width="19%" style="padding-left:10px;">
                           <input type="text" name="Service_Connection_date"
                               maxlength="35" size="8"
                               id="Service_Connection_date"
                               />
                               <img src="../../../../../images/calendr3.gif"
                             alt="calendar"
                             onclick="showCalendarControl(document.beneficary_meter.Service_Connection_date)"></img>
                            
                  </td>
                </tr>
            </table>
            <table border="1" width="85%" align="center" cellpadding="0" cellspacing="0" class="border1">
                <tr class="tdH" align="center">
                    <td>
                        <input type="button" name="cmdadd" value="Add"
                               id="cmdadd" onclick="doFunction('Add');"/>
                        <input type="button" name="cmdupdate" value="Update"
                               id="cmdupdate" onclick="doFunction('Update');"
                               />
                        <input type="button" name="cmddelete" value="Delete"
                               id="cmddelete" onclick="doFunction('Delete');"
                               />
                        <input type="button" name="cmdreport" value="Report"
                               id="cmdreport" onclick="popup();"/>
                        <input type="button" name="cmdclear" value="Clear"
                               id="cmdclear" onclick="refresh()"/>
                               
                        <input type="button" name="cmdexit" value="Exit"
                               id="cmdlist" onclick="exitwindow();"/>
                    </td>
                </tr>
            </table>
       <table id="existing" border="1" width="85%" align="center" class="border1">
                <tr>
                 
                    <th class="tdH">Edit</th>
                   <th class="tdH">Sno</th>
                  <!-- <th class="tdH">Beneficiary Type</th>
                  <th class="tdH">Beneficiary Name</th>-->
                   <th class="tdH" id="meterlabel">Metre Location</th>
                    <th class="tdH">Sub Division</th>
                    <th class="tdH">Scheme</th>
                 <!--   <th class="tdH">Location</th>-->
                    <th class="tdH">Tariff Rate</th>
                    <th class="tdH">Metre fixed</th>
                    <th class="tdH">Metre Working</th>
                    <!--<th class="tdH" id="meterlabel">Metre Location</th>-->
                 <!--   <th class="tdH" id="Habitationlabel">Habitation name</th>-->
                </tr>
                <tbody id="getvaluerows" class="table"></tbody>
            </table>
        </form></body>
</html>