<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">
<%@ page contentType="text/html;charset=windows-1252"%>
<%@ page import="java.sql.*,java.util.*,Servlets.Security.classes.UserProfile"%>
<%@ include file="//org/Security/jsps/Check_SessionJSPF.jspf" %>
<%@ include file="//org/Security/jsps/IntialLoad.jsp"%>
<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=windows-1252"/>
    <META HTTP-EQUIV="CACHE-CONTROL" CONTENT=" no-store, no-cache, must-revalidate" >
	<META HTTP-EQUIV="CACHE-CONTROL" CONTENT=" pre-check=0, post-check=0, max-age=0" >
    <title>Fund Receipt Create at HO </title>
   
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
   
   
   <script language="javascript" type="text/javascript" src="../scripts/Auto_Fund_Receipt_Create_atHOjs.js"></script>
      
      
   <script type="text/javascript"
           src="<%=request.getContextPath()%>/org/FAS/FAS1/CommonControls/scripts/Load_Accounting_office.js"></script>
      
      
      
   <script type="text/javascript" language="javascript">
    function loadyear_month()
      {
            var today= new Date(); 
                 var day=today.getDate();
                 var month=today.getMonth();
                 month=month+1;
                 var mon=month;
                 if(day<=9 && day>=1)
                 day="0"+day;
                 if(month<=9 && month>=1)
                 month="0"+month;
                 var year=today.getYear();
                 if(year < 1900) 
                {
                	 year += 1900;                 
                	 }
                 
                document.frmauto_Fund.txtCrea_date.value=day+"/"+month+"/"+year;
                
                document.frmauto_Fund.txtCB_Year.value=year;
                document.frmauto_Fund.txtCB_Month.value=month;
      };
    
   
    </script>
  </head>  
   <%
response.setHeader("Cache-Control","no-cache"); //HTTP 1.1
response.setHeader("Pragma","no-cache"); //HTTP 1.0
response.setDateHeader ("Expires", 0); //prevent caching at the proxy server
%>
<body class="table" onload="loadyear_month();LoadAccountingUnitID('LIST_ALL_UNITS');">

  
    
 <form name="frmauto_Fund" id="frmauto_Fund" > 
    
    
 
    <table cellspacing="1" cellpadding="3" width="100%" >
      <tr class="tdH">
        <td colspan="2">
          <div align="center">
            <font size="4">Auto Fund Receipt Creation at HO</font>
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
              
              
              
              
             <tr class="table">
                <td>
                  <div align="left">
                    Receipt Date
                    <font color="#ff2121">*</font>
                  </div>
                </td>
                <td>
                  <div align="left">
                    <input type="text" name="txtCrea_date" id="txtCrea_date" tabindex="3" 
                           maxlength="10" size="11"  
                           onfocus="javascript:vDateType='3';" onchange="clearGrid();"
                           onkeypress="return calins(event,this);"
                           onblur="call_date(this);"/>
                     <img src="../../../../../images/calendr3.gif"
                         onclick="showCalendarControl(document.frmauto_Fund.txtCrea_date,1);"
                         alt="Show Calendar"></img> 
                         
                  </div>
                </td>
             </tr>
               
              
              
              
             <tr class="table">
               <td>
                 <div align="left">
              		Receipt Type <font color="#ff2121"> * </font>
                 </div>
               </td>
               <td>
                <div align="left">
                	<select name="remit_type_sel" id="remit_type_sel" >
                		<option value="C,U,NM,NS,NC,UNM,UNS,UNC,FDW,WATCHARGEREV_Hogenakkal,WATCHARGEREV,NONWATCHARGEREV,FieldKit,FDW from Collection,LB100PCNTCONTRIB,JICA,Security Deposit,KFW,UIDDSMT">All</option>
                		<option value="C"> Regular Collection </option>      
                		<option value="U"> Regular Unspent </option>    
                		<option value="NM"> NRDWP - Main Unspent</option>      
                		<option value="NS"> NRDWP - Support Unspent</option><!--    
                		<option value="SCH">Scheme Account</option>    
                		--><option value="UNC">NRDWP – Calamity Unspent</option>    
                			<option value="UNM">NRDWP – Main Interest Transferred</option>    
                				<option value="UNS">NRDWP – Support Interest Transferred</option>    
                					<option value="NCI">NRDWP – Calamity Interest Transferred</option>
                						<option value="NRDWP-WQM-SP">Unspent from NRDWP-WQMSP</option>        
                							<option value="FDW">FDW</option>
                							<option value="WATCHARGEREV_Hogenakkal">Hogenakkal Office Collection-Water Charges Transfer</option>
                							<option value="WATCHARGEREV">Collection Water Charges Transfer</option>
                							<option value="NONWATCHARGEREV">Collection Non Watercharges Transfer</option>
                							<option value="FieldKit">Collection-Field Kit</option>
                							<option value="FDW from Collection">Collection - Full Deposit Received</option>
                							<option value="LB100PCNTCONTRIB">Collection-LB 100 Pcnt Contribution</option>
                							<option value="JICA">Collection-JICA Fund Including LB Contribution</option>
                							<option value="Security Deposit">Collection - SD Withheld or Recovered</option>
                							<option value="KFW"> Collection-KFW Fund Including LB Contribution</option>
                							<option value="UIDDSMT"> Collection-UIDDSMT Fund Including LB Contribution </option>
                							
                	</select>
                </div>
               </td>
              </tr>
            
           
              <tr class="table">
               <td>
                 <div align="left">
              		Displying Order <font color="#ff2121"> * </font>
                 </div>
               </td>
               <td>
                   <input type="radio" name="displayingOrder" id="displayingOrder" value="RW" onclick="cb_month_year(this.value)" checked></input>Region Wise &nbsp;&nbsp; 
                   <input type="radio" name="displayingOrder" id="displayingOrder" value="BW" onclick="cb_month_year(this.value)" ></input>Bank Wise          
                  <br><br>
                  
                   <div align="left" id="Regions" name="Regions" style="display:block">                	
                   
                    <select size="1" name="txtRegionId" id="txtRegionId" tabindex="1">
                     <option value="101">All Region</option>
                     
                      <%
                    
                      try{
                                ps=con.prepareStatement("select OFFICE_NAME,OFFICE_ID from COM_MST_OFFICES where OFFICE_LEVEL_ID in ('RN','HO') and OFFICE_STATUS_ID='CR'");
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
                
                
                       
               
                 <div align="left" id="Banks" name="Banks" style="display:none">
                    <select name="txtBankAccountNo" id="txtBankAccountNo" onchange="clearGrid();" >
                    <option value="">-- Select Bank Account No--</option>
                    <%
                        String getbankAccNo="SELECT distinct a.bankShow,a.BANK_AC_NO,a.BANK_SHORT_NAME from(select b.BANK_AC_NO,c.BANK_SHORT_NAME || '-' || b.BANK_AC_TYPE_ID ||'-'|| b.BANK_AC_NO  as bankShow, c.BANK_SHORT_NAME from FAS_OFFICE_BANK_AC_CURRENT b,FAS_MST_BANKS c "+
                                            " where b.BANK_ID=c.BANK_ID and b.ACCOUNTING_UNIT_ID=? and MODULE_ID=? and CR_DR_TYPE=? and b.STATUS='Y' order by c.BANK_SHORT_NAME)a order by a.BANK_SHORT_NAME";
                        System.out.println(getbankAccNo);
                        psbank=con.prepareStatement(getbankAccNo); 
                        psbank.setInt(1,unitid);
                        psbank.setString(2,"MF009");
                        psbank.setString(3,"DR");
                        rsbank=psbank.executeQuery();
                        while(rsbank.next())
                        {
                        out.println("<option value="+rsbank.getLong("BANK_AC_NO")+">"+rsbank.getString("bankShow")+"</option>");
                        }
                    %>
                    </select>         
                    
                </div>
                
                
               </td>               
              </tr>           
               
              
              
              <tr class="table">
               <td>
                 <div align="left">
              		Remarks 
                 </div>
               </td>
               <td>
                <div align="left">
                	<textarea cols="50" rows="2" name="comRemarks" id="comRemarks"></textarea>
                </div>
               </td>
              </tr>
            
                    
            </table>            
    
       <table cellspacing="1" cellpadding="1" border="0" width="100%">
       <tr align="left" class="tdH"> <th>Search By Month or Date</th></tr>
        <tr align="left">
          <td class="table">
          <div align="left">
              Cash Book Year &amp; Month&nbsp;&nbsp;<strong>:</strong>
                         <input type="text" name="txtCB_Year" id="txtCB_Year" tabindex="3"  maxlength="4" size="5" onkeypress="return numbersonly(event)">
         
          <select name="txtCB_Month"  id="txtCB_Month" tabindex="4" >
          <option value="01">January</option>
          <option value="02">February</option>
          <option value="03">March</option>
          <option value="04">April</option>
          <option value="05">May</option>
          <option value="06">June</option>
          <option value="07">July</option>
          <option value="08">August</option>
          <option value="09">September</option>
          <option value="10">October</option>
          <option value="11">November</option>
          <option value="12">December</option>
          </select>
          
           <input type="BUTTON" value="GO" name="ByMonth" id="ByMonth"  tabindex="5" onclick="doFunction('searchByMonth','null')"/>
           </div>
          </td>
        </tr>
        
        <tr align="left">
          <td class="table">
          <div align="left">
              From Date &amp; To Date&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<strong>:</strong>
                                   <input type="text" name="txtFrom_date" id="txtFrom_date"  tabindex="6"
                           maxlength="10" size="11"
                           onfocus="javascript:vDateType='3';"
                           onkeypress="return calins(event,this);"
                           onblur="return checkdt(this);"/>
                     
                    <img src="../../../../../images/calendr3.gif"
                         onclick="showCalendarControl(document.frmauto_Fund.txtFrom_date);"
                         alt="Show Calendar"></img>
           
                    <input type="text" name="txtTo_date" id="txtTo_date"  tabindex="7"
                           maxlength="10" size="11"
                           onfocus="javascript:vDateType='3';"
                           onkeypress="return calins(event,this);"
                           onblur="return checkdt(this);"/>
                     
                     <img src="../../../../../images/calendr3.gif"
                         onclick="showCalendarControl(document.frmauto_Fund.txtTo_date);"
                         alt="Show Calendar"></img>
            <input type="BUTTON" value="GO" name="ByMonth" id="ByMonth" tabindex="8" onclick="doFunction('searchByDate','null')"/>
            </div>
          </td>
         </tr>
         
         
         
        
     </table>
    
     <table cellspacing="1" cellpadding="1" border="0" width="100%">
      <tr align="left" class="tdH"> <th>Details (Fund Transfer to this office from HO)</th></tr>
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
              <font size="2"> Voc No.</font>
            </th>            
            <th>
             <font size="2"> DR HOA </font>
            </th>            
            <th>
             <font size="2"> HO A/C No. </font>
            </th>
            <th>
             <font size="2"> CR HOA </font>
            </th>
            <th>
             <font size="2"> Office A/C No. </font>
            </th>          
            <th>
              <font size="2">Fund Type </font>
            </th>          
            <th>
             <font size="2"> Total Amount </font>
            </th>             
            <th>
             <font size="2"> Ref No.</font>
            </th>             
            <th>
             <font size="2"> Ref Date (dd/mm/yyyy)</font>
            </th>                       
            <th>
             <font size="2">  Recd. From</font>
            </th>
            
            <th>
              <font size="2">  Trf. Date </font>
            </th>
            
            
          </tr>
          
          <tbody id="tbody" class="table">
          
         
          </tbody>
      <tr class="tdH">
      <td colspan="12">
          <div align="center" id="testsubmitdiv" style="display:none">
         <input type="hidden"  value="" name="TotalRecords" id="TotalRecords">
         <input type="button"  value="Submit" onclick="btSubmit()">
         <input type="button" id="cmdcancel" name="cancel" value="Exit" onclick="self.close()">
      </div>
       <div align="center" id="testexitdiv" style="display:block">
         <input type="hidden"  value="" name="TotalRecords" id="TotalRecords">
         <input type="button" id="cmdcancel" name="cancel" value="Exit" onclick="self.close()">
      </div>
      </td>
      </tr>

        </table> 
        
        </form>
    </body>
</html>