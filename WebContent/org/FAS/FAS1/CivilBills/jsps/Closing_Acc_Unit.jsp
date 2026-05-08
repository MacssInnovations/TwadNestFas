<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">
<%@ page contentType="text/html;charset=UTF-8"%>
<%@ page import="java.sql.*,java.util.*,Servlets.Security.classes.UserProfile"%>
<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=windows-1252"/>   
    <meta http-equiv="cache-control" content="no-cache">
    <title>Closing Accounting Unit</title>
       <script type="text/javascript" src="<%=request.getContextPath()%>/org/Library/scripts/comJS.js"></script>
       <link href="../../../../../css/Sample3.css" rel="stylesheet" media="screen"/>
       <link href="../../../../../css/CalendarControl.css" rel="stylesheet" media="screen"/>
       
         <script type="text/javascript" src="../../../../../org/FAS/FAS1/CalendarCtrl.js"></script> 
         <script type="text/javascript" src="../../../../../org/Library/scripts/checkDate.js"></script>
         
         <script type="text/javascript" src="../../../../../org/FAS/FAS1/ReceiptSystem/scripts/Com_Popup_XMLreq_SL.js"></script> 
         <script type="text/javascript" src="../../../../../org/FAS/FAS1/ReceiptSystem/scripts/Com_Function_SL_Case.js"></script>
                  
         <script type="text/javascript" src="../scripts/Closing_Acc_Unit.js"></script>
         <script type="text/javascript" language="javascript">
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
	                 document.close_acc.date_close.value=day+"/"+month+"/"+year;                   
	        }

       </script>
   
  </head>
  <body class="table" onload="loadDate();">
     <%
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
        
  <form name="close_acc" id="close_acc" method="POST" action="../../../../../close_acc" onsubmit="return nullcheck()">
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
              <b>Closing Accounting Unit</b>
            </div>
          </td>
        </tr>
        </table>
      <table cellspacing="2" cellpadding="3" border="1" width="100%" align="center">
          <tr class="table">
            <td>
              <div align="left">
                Select Accounting Unit Id to be Closed 
                <font color="#ff2121">*</font>
              </div>
            </td>
            <td>
              <div align="left">
                 <select size="1" name="cmbAcc_UnitCode" id="cmbAcc_UnitCode" tabindex="1" onChange="offname();offname1();loadDOpen();">
                     <option value="0">--Select Accounting Unit Id--</option>           
                      <%
                      int unitid=0;
                      String unitname="";
                      try{

                            String getWing="select ACCOUNTING_UNIT_ID,ACCOUNTING_UNIT_NAME,OFFICE_WING_SINO from FAS_MST_ACCT_UNITS order by ACCOUNTING_UNIT_NAME";
                            ps=con.prepareStatement(getWing);
                            rs=ps.executeQuery();
                          
                              while(rs.next())
                              {
                              out.println("<option value="+rs.getInt("ACCOUNTING_UNIT_ID")+">"+rs.getString("ACCOUNTING_UNIT_NAME")+"("+rs.getInt("ACCOUNTING_UNIT_ID")+")"+"</option>");
                              unitid=rs.getInt("ACCOUNTING_UNIT_ID");                         
                              }
                          System.out.println(oid+" "+oname);
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
          </tr> 
          
          <tr class="table">
            <td>
              <div align="left">
                Accounting Unit Office Name 
                <font color="#ff2121">*</font>
              </div>
            </td>
            <td>
              <div align="left">
                <select size="1" name="cmbOffice_code" id="cmbOffice_code" tabindex="2">
                         <option value="0">--Select Office Name--</option>           
                </select>
              </div>
            </td>
          </tr>
		  <tr>
		    <td class="table">Date of Opening</td>
            <td class="table">
		                
			                  <div align="left">
			                    <input type="text" name="date_open" value="" size="10" disabled readonly
			                           id="date_open"/>			                     
			                <!--     <img src="../../../../../images/calendr3.gif"
			                         onclick="showCalendarControl(document.close_acc.date_open,0);"
			                         alt="Show Calendar"></img>  -->
			                  </div>
			              
            </td>
	       </tr>
	       <tr>
			    <td class="table">Date of Closure</td>
	            <td class="table">
			                
				                  <div align="left">
				                    <input type="text" name="date_close" value="" size="10"
				                           id="date_close"/>			                     
				                    <img src="../../../../../images/calendr3.gif"
				                         onclick="showCalendarControl(document.close_acc.date_close,0);"
				                         alt="Show Calendar"></img>
				                  </div>
				              
	            </td>
	     </tr>  	               
          <tr class="table">
            <td>
              <div align="left">
                Accounts to be Transferred to
              </div>
            </td>
            <td>
              <div align="left">
                <select size="1" name="unit_id" id="unit_id" tabindex="2" >
                         <option value="0">--Select--</option>           
                </select>
              </div>
            </td>
          </tr>  
        <tr class="tdH">
            <td colspan=12>
                <div align="center">
                 <table >
                     <tr>
                        <td>
                        <input type="button" name="cmdAdd" value="Submit" id="cmdAdd" onclick="update()" tabindex="20"/>
                         </td>
                         <td>
                        <input type="button" name="cmdEdit" value="Cancel" id="cmdEdit"  onclick="clr()" tabindex="30"/>
                         </td>
                     </tr>
                </table>
                    </div>
            </td>
        </tr>
      </table>
       
      
        <table align="center" cellspacing="3" cellpadding="2" border="1"
             width="100%">
        <tr class="tdH">
          <td>
            <div align="center">
              <input type="button" id="cmdcancel" name="cancel" value="Exit"
                     onclick="self.close();"></input>
            </div>
          </td>
        </tr>
      </table> 
    </form>
</html>
