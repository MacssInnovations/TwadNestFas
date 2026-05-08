<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">
<%@ page contentType="text/html;charset=UTF-8"%>
<%@ page import="java.sql.*,java.util.*,Servlets.Security.classes.UserProfile"%>
<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=windows-1252"/>   
    <meta http-equiv="cache-control" content="no-cache">
    <title>HR Note Preparation</title>
       <script type="text/javascript" src="<%=request.getContextPath()%>/org/Library/scripts/comJS.js"></script>
       <link href="../../../../../css/Sample3.css" rel="stylesheet" media="screen"/>
       <link href="../../../../../css/CalendarControl.css" rel="stylesheet" media="screen"/>
       
         <script type="text/javascript" src="../../../../../org/FAS/FAS1/CalendarCtrl.js"></script> 
         <script type="text/javascript" src="../../../../../org/Library/scripts/checkDate.js"></script>
         <script type="text/javascript"
            src="<%=request.getContextPath()%>/org/FAS/FAS1/CommonControls/scripts/Common_Load_Accounting_Unit_ID.js"></script>
    <script type="text/javascript"
            src="<%=request.getContextPath()%>/org/FAS/FAS1/CommonControls/scripts/Common_Load_Accounting_office.js"></script>
         <script type="text/javascript" src="../../../../../org/FAS/FAS1/ReceiptSystem/scripts/Com_Popup_XMLreq_SL.js"></script> 
    <script type="text/javascript" src="../../../../../org/FAS/FAS1/ProceedingGeneration/scripts/HR_Note_Cancel.js"></script> 
                  
       <script type="text/javascript" src="../scripts/HR_Note_Cancel.js"></script> 
         

         
       <script type="text/javascript" language="javascript">
       function loadyear_month()
       {
             var today= new Date(); 
             var day=today.getDate();
             var month=today.getMonth();
             month=month+1;
             var year=today.getYear();
             if(year < 1900) year += 1900;
                        
            document.HR_Note.txtCB_Year.value=year;
            document.HR_Note.txtCB_Month.value=month;
      }
    
    </script>
   
  </head>
  <body class="table" onload="LoadAccountingUnitID('LIST_ALL_UNITS');loadyear_month();loadDate() ">
     <%int unitid=0;
             Connection con=null;
             ResultSet rs=null;
             PreparedStatement ps=null,ps2=null;
            
             Connection connection=null;
        
             ResultSet results=null,rs2=null;
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
                       System.out.println("Exception in opening connection :"+e);
             }   UserProfile empProfile=(UserProfile)session.getAttribute("UserProfile");
                int empid=empProfile.getEmployeeId();
                int  oid=0;             // Office id
                String oname="";        // office name
           
            try
            {
                   
                    ps=con.prepareStatement("select OFFICE_ID from HRM_EMP_CURRENT_POSTING where EMPLOYEE_ID=?" );
                    ps.setInt(1,empid);
                    results=ps.executeQuery();
                         if(results.next()) 
                         {
                            oid=results.getInt("OFFICE_ID");
                            System.out.println("Office id is:"+oid);
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
         
         
        
  <form name="HR_Note" id="HR_Note" method=POST action="../../../../../HR_Note_Cancel1" onsubmit="cancel_live();">
      <table cellspacing="2" cellpadding="3" border="1" width="100%">
        <tr class="tdH">
              <td>
                <div align="center">
                  TWAD BOARD-INTEGRATED ONLINE SYSTEM -FINANCIAL ACCOUNTING SYSTEM
                </div>
              </td>
        </tr>
        
        <tr class="table">
          <td>
            <div align="center">
            
              <b>HR Note Cancel</b>
            </div>
          </td>
        </tr>
        </table>
      <table cellspacing="2" cellpadding="3" border="1" width="100%" align="center">
          
          
          
          
          <tr class="table">
                    <td>
                      <div align="left" >
                              Accounting Unit Code  <font color="#ff2121">*</font>
                      </div>
                    </td>
                    <td>
                      <div align="left">
                         <select size="1" name="cmbAcc_UnitCode" id="cmbAcc_UnitCode" onchange="common_LoadOffice(this.value);">        
                         </select>
                      </div>
                    </td>
              </tr>


              <tr class="table">
                    <td>
                      <div align="left">
                        Accounting For Office Code <font color="#ff2121">*</font>
                      </div>
                    </td>
                    <td>
                      <div align="left">
                        <select size="1" name="cmbOffice_code" id="cmbOffice_code" >
                          
                        </select>
                      </div>
                    </td>
              </tr>
              
         
          
          
   <tr>
					<td class="table">
			                    CashbookYear & Month
			                           <label style="color:rgb(255,0,0);">&nbsp;*</label></td>
			                   	
			             
					<td>
			                    
							  <input type="text" name="txtCB_Year" id="txtCB_Year" tabindex="3"  maxlength="4" size="5" onkeypress="return numbersonly(event)">
					          <select name="txtCB_Month"  id="txtCB_Month" tabindex="4" onchange="LoadBankAccountNumber_opr1()">
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
					       
             <%--  
              <tr class="table">
                    <td>
                      <div align="left">
                        HR Note No <font color="#ff2121">*</font>
                      </div>
                    </td>
                    <td>
                      <div align="left">
                   
   <select name="note no" id="note no">
<% 


try
{
     
    // HttpSession ss=request.getSession();
    // String uid=(String)ss.getAttribute("id");
     PreparedStatement pst=con.prepareStatement("SELECT F.Hr_Note_No " +
    		 "FROM FAS_HR_NOTE_DETAILS f, " +
    		 "  FAS_HR_SANC_PROC_MST f1 " +
    		 "WHERE F.Accounting_Unit_Id    =F1.Accounting_Unit_Id " +
    		 "AND F.Accounting_For_Office_Id=F1.Accounting_For_Office_Id " +
    		 "AND F.Cashbook_Year           =F1.Cashbook_Year " +
    		 "AND F.Cashbook_Month          =F1.Cashbook_Month " +
    		 "AND Status                    ='L' " +
    		 "ORDER BY F.Hr_Note_No");
    		
    // pst.setInt(1,unitid);
    // System.out.println(unitid);
     rs=pst.executeQuery();
     while(rs.next())
     {
    	 
          String no = rs.getString("Hr_Note_No");
%>
          <option value="<%=no%>"><%=no%></option>
<%
     }
}catch(Exception e)
{    out.print(e);
}
%>

					          <input type="BUTTON" value="GO" name="ByMonth" id="ByMonth"  tabindex="5" onclick="doFun('searchByMonth','null')"/>
			                    	
			                </td>
				</tr> --%>
				
				
	<tr class="table">
		<td>
		<div align="left">Hr Note No.</div>
		</td>
		<td>
		<div align="left"><select name="cmbBankAccNo" id="cmbBankAccNo" >
			<option value="s">-- select ---</option>
		</select><input type="hidden"
			name="txtOprMode" id="txtOprMode" tabindex="5"
			style="background-color: #ececec" readonly="readonly" size="50" />
			
			    <input type="button" name="Go" id="Go" value="Go"
			onclick="doFun(this.value)" /> </div>
		</td>
	</tr>
				
         
       
       
                </table>
                    
            
        
        

       
      
       <table id="mytable" align="center"  cellspacing="3" 
         cellpadding="2" border="1" width="100%">
          <tr class="tdH">
            <!-- <th><font size="2">
           NOTE NO </font></th> -->
           
          
              <th><font size="2">
           NOTE DATE </font></th>
           
                       
            <th><font size="2">
           BILL_MAJOR_TYPE </font></th>
            <th><font size="2">
           BILL_MINOR_TYPE 
           </font> </th>
            <th> <font size="2">
          BILL_SUB_TYPE
            </font></th>
            <th><font size="2">
       AMOUNT 
            </font></th>
            <th><font size="2">
        NOTE_PREPARED_BY 
           </font> </th>
            <th><font size="2">
        ACCOUNT_HEAD_CODE 
           </font> </th>
           
          </tr>
          <tbody id="tbody" class="table">          
          </tbody>
          
        </table>
        <table align="center"  cellspacing="3" cellpadding="2" border="1" width="100%" >
            
      <tr class="tdH">
      <td>
          <div align="center">
           <input type="submit" id="cancelbtn" name="cancelbtn" value="Submit">
         <input type="button" id="cmdcancel" name="cancel" value="EXIT" onclick="btncancel1()">
      </div>
      </td>
      </tr>
      
      </table>
    </form>
</html>
