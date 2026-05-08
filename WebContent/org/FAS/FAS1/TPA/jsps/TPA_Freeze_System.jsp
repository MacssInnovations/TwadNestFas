<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">
<%@ page session="false"  contentType="text/html;charset=windows-1252"%>
<%@ page import="java.sql.*,java.util.*,java.text.*,Servlets.Security.classes.UserProfile"%>
<%@ include file="//org/Security/jsps/Check_SessionJSPF.jspf" %>
<%@ include file="//org/Security/jsps/IntialLoad.jsp"%>

<html> 
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=windows-1252"/>     
    <meta http-equiv="cache-control" content="no-cache">
    <title>Transfer Proforma Accounting System (Credit / Debit) Freeze System</title>
    
    <link href="../../../../../css/Sample3.css" rel="stylesheet"  media="screen"/>
    <link href="../../../../../css/CalendarControl.css" rel="stylesheet" media="screen"/>
    <link href='../../../../../css/Fas_Account.css' rel='stylesheet' media='screen'/>
    
    
     <script type="text/javascript" src="<%=request.getContextPath()%>/org/Library/scripts/CalendarControl.js"></script>
    <script type="text/javascript" src="<%=request.getContextPath()%>/org/Library/scripts/comJS.js"></script>
    <script type="text/javascript"  src="../../../../../org/Library/scripts/checkDate.js"></script>
    
   
    
    <script type="text/javascript"
            src="<%=request.getContextPath()%>/org/FAS/FAS1/CommonControls/scripts/Common_Load_Accounting_office.js"></script>
    
    

    
    <script type="text/javascript" src="../scripts/TPA_Freeze_System.js"></script>
    <script type="text/javascript"   src="../../../../Security/scripts/tabpane.js"></script>

      
    <!-- to avoid future date the above script used-->
    <script type="text/javascript" language="javascript">
         function foc()
         {
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
             var monthArray =new Array("January", "February", "March", 
                       "April", "May", "June", "July", "August",
                       "September", "October", "November", "December");
            document.frm_TPA_Raised_Create.tpadate.value=day+"/"+month+"/"+year;
            document.frm_TPA_Raised_Create.txtCB_Year.value=year;
            //alert(month);
            document.frm_TPA_Raised_Create.txtCB_Month.value=month;
            
         }
</script>


 </head>
 <body onload="common_LoadOffice(this.value);loadDate();" bgcolor="rgb(255,255,225)">
  <%
  
  /*Connection con=null;
  ResultSet rs=null,rs2=null,rsbank=null;
  PreparedStatement ps=null,ps2=null,psbank=null;
  ResultSet results=null;
  ResultSet results1=null;
  ResultSet results2=null;*/
  
  
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
 %>
   
   <form id="frm_TPA_Raised_Create" method="post" name="frm_TPA_Raised_Create" action="../../../../../TPA_Freeze_System" onsubmit="return checknull_submit();">  
   
   
    <table cellspacing="1" cellpadding="3" width="100%" >
      <tr class="tdH">
        <td colspan="2">
          <div align="center">
            <font size="4">Transfer Proforma Accounting System (Credit / Debit) Freeze System </font>
          </div>
        </td>
      </tr>
    </table>
    
            <table cellspacing="1" cellpadding="2" border="0" width="100%">
           
                <tr class="tdTitle">
                <td colspan="2">
                  <div align="left">
                    <strong>General Details</strong>
                  </div>
                </td>
              </tr>
              <tr class="table">
                <td>
                  <div align="left">
                    Accounting Unit Code 
                    <font color="#ff2121">*</font>
                  </div>
                </td>
                <td>
                  <div align="left">
                    <select size="1" name="cmbAcc_UnitCode" id="cmbAcc_UnitCode" tabindex="1" onchange="common_LoadOffice(this.value);clrForm();">
                  
                  <% 
                    String office_level_id = "";
                    int unitid=0;
                    int office_id = 0;
                    ResultSet rs6 = null;
                    PreparedStatement ps6 = null;	
                        	
                       
                    
             try
            {
                System.out.println("*********************************inside jsp********************************BEFORE"+oid);
                ps2 =
                        con.prepareStatement("select OFFICE_LEVEL_ID  from COM_MST_OFFICES  where OFFICE_ID=?");
                        ps2.setInt(1, oid);
                        rs2 = ps2.executeQuery();
                        if(rs2.next())
                        { 
                        	//office_level_id=rs2.getString("OFFICE_LEVEL_ID");
                            System.out.println("*******************************inside jsp**********************************office_level_id"+office_level_id);
                           // if(office_level_id.equals("AW")) 
                           // {
                                        System.out.println("****************************inside jsp*************************************Enter"+office_level_id);
            			                ps1 =
            			                con.prepareStatement("select REGION_OFFICE_ID  from FAS_TPA_AUDIT_ALL_OFFICES_VIEW  where OFFICE_ID=?");
            			                ps1.setInt(1, oid);
            			                rs = ps1.executeQuery();
            			                if(rs.next()) 
            			                {
            			                    oid=rs.getInt("REGION_OFFICE_ID");
            			                 }
                           
                            // }
                         }
                            
                        
                                
                                
                                
                                
                             String RegionSql=   "  SELECT a.ACCOUNTING_UNIT_ID,a.ACCOUNTING_FOR_OFFICE_ID,b.accounting_unit_name FROM \n" +
                            "  (SELECT distinct aa.ACCOUNTING_UNIT_ID, aa.ACCOUNTING_FOR_OFFICE_ID FROM FAS_TPA_MASTER aa WHERE aa.AUDIT_VERIFY ='Y' \n" +
                            "  and ACCOUNTING_UNIT_ID NOT IN(SELECT distinct c.ACCOUNTING_UNIT_ID FROM FAS_TPA_STATUS c where C.TPA_VR_NO=AA.VOUCHER_NO AND C.CASHBOOK_YEAR=AA.CASHBOOK_YEAR and C.CASHBOOK_MONTH=AA.CASHBOOK_MONTH))a  \n" +		 
                            "  inner join \n" +
                            "   (select                           \n" + "       accounting_unit_id,          \n" +
                            "       accounting_unit_name         \n" +
                            "   from                             \n" +
                            "        fas_mst_acct_units          \n" +
                            "   where                            \n" +
                            "        accounting_unit_office_id in   \n" +
                            "    (                               \n" +
                            "       select                       \n" +
                            "          office_id                 \n" +
                            "       from                         \n" +
                            "          FAS_TPA_AUDIT_ALL_OFFICES_VIEW  \n" +
                            "       where region_office_id= "+oid+"    \n" +
                            "    )   )b                           \n" +
                            "  ON B.ACCOUNTING_UNIT_ID=A.ACCOUNTING_UNIT_ID \n" +
                            "   order by accounting_unit_name      \n" +
                            "                                    \n";
                            
                            System.out.println("RegionSql"+RegionSql);
                            try {
                            ps6 = con.prepareStatement(RegionSql);
                           // ps6.setInt(1, oid);
                            rs6 = ps6.executeQuery();
                            
                            
                            
                            while(rs6.next())
                                           {
                            
                             
                            
                                out.println("<option value="+rs6.getInt("accounting_unit_id")+" >"+rs6.getString("accounting_unit_name")+"("+rs6.getInt("accounting_unit_id")+")"+"</option>");

                               
                              
                                System.out.println(".."+rs6.getInt("accounting_unit_id"));
                                          System.out.println(".."+rs6.getString("accounting_unit_name"));          
                               
                                }
                                }
                               catch(Exception e)
                                    {
                                        System.out.println("Exception is"+e);
                                    }
                            
                            //}
                        }
                         catch(Exception e) {
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
                    </select>
                  </div>
                </td>
              </tr>
               <tr  class="table">
          <td >
         <div align="left">
              For Year &amp; Month<font color="#ff2121">*</font>
             </div>
              </td>
              <td>
              <div align="left">
	          <input type="text" name="txtCB_Year" id="txtCB_Year"  maxlength="4" size="5" onkeypress="return numbersonly(event)">
	          <select name="txtCB_Month"  id="txtCB_Month" >
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
	          
	           <input type="BUTTON" value="GO" name="ByMonth" id="ByMonth"   onclick="call('get')"/>
	          
          </div>
          </td>
           
         </tr>
              <tr class="table">
                <td>
                  <div align="left">
                     Freeze Date
                    <font color="#ff2121">*</font>
                  </div>
                </td>
                <td>
                  <div align="left">
                    <input type="text" name="tpadate" id="tpadate" 
                           maxlength="10" size="11" />
                     <img src="../../../../../images/calendr3.gif"
                         onclick="showCalendarControl(document.frm_TPA_Raised_Create.tpadate,1);"
                         alt="Show Calendar"></img>                   
                  </div>
                </td>
              </tr>
       
              
                          
              
            
         
         
          
            </table>
     
      <table id="mytable" align="center"  cellspacing="3" 
         cellpadding="2" border="1" width="100%">
          <tr class="tdH">
            
              <th>
            <font >
             Select
                 <a href="javascript:selectAll('ALL');">All</a>
                 <a href="javascript:selectAll('UNSelect');">Unselect</a> 
             </font>
             
            </th>
            <th>
              Voucher Number
            </th>
            <th>
              Orginated Date
            </th>
            
             <th>
              Orginated Unit
            </th>
            
              <th>
              CR/DR
            </th>
            
              <th>
              Amount
            </th>
                                            
            <th>
            Reason
            </th>
            <th>
            Acceptance Unit
            </th>
            <th>
            Audit Verify Status
            </th>
            <th>
            Audit Verify Date
            </th>
           
          </tr>
          <tbody id="grid_body" class="table">          
          </tbody>
        </table>
     
     
     
      <div align="center">
        <table cellspacing="1" cellpadding="6" width="100%" >
          <tr class="tdH">
            <td >
              <div align="center">
             
                <input type="submit" name="butSub" id="butSub" value="SUBMIT" />
                 &nbsp;&nbsp;&nbsp; 
               <input type="button" name="butCan" id="butCan" value="CANCEL"
                       onclick="clrForm();"/>
                 &nbsp;&nbsp;&nbsp; 
                 
                <input type="button" name="butCan" id="butCan" value="EXIT"
                       onclick="javascript:self.close();"/>
                     &nbsp;&nbsp;&nbsp;   
                 
                       
              </div>
            </td>
           
          </tr>
        </table>
      </div>
       
  </form>       

</body>
</html>