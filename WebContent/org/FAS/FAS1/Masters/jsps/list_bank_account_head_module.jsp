<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">
<%@ page session="false"  contentType="text/html;charset=windows-1252"%>
<%@ page import="java.sql.*,java.util.*,Servlets.Security.classes.UserProfile"%>
<%@ include file="//org/Security/jsps/Check_SessionJSPF.jspf" %>
<style type="text/css">
#temp
{
height:250px;
overflow:scroll;
}
</style>
<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=windows-1252"/>    
    <meta http-equiv="cache-control" content="no-cache">
    <title>Bank Accounting System</title>
   
    
 
   
    <script type="text/javascript" src="<%=request.getContextPath()%>/org/Library/scripts/comJS.js"></script>
    <script type="text/javascript"  src="../../../../../org/Library/scripts/checkDate.js"></script>
    <script type="text/javascript" src="../scripts/list_bank_account_head_module.js"></script>
    <script type="text/javascript" src="../../../../../org/FAS/FAS1/ReceiptSystem/scripts/Com_Popup_XMLreq_SL1.js"></script> 
    <link href="../../../../../css/Sample3.css" rel="stylesheet"  media="screen"/>
    <link href='../../../../../css/Fas_Account.css' rel='stylesheet' media='screen'/>
    
    <!-- to avoid future date the above script used-->
    <script type="text/javascript" language="javascript">
         function foc()
         {
         }
</script>
  </head>
  <body bgcolor="rgb(255,255,225)">
  <table cellspacing="1" cellpadding="3" width="100%" >
      <tr class="tdH">
        <th colspan="2">
          <div align="center">
            <font size="4">Account Head Code Module details </font>
          </div>
        </th>
      </tr>
    </table>
    
    <form name="frmBank_AccountHeadCode_Module" id="frmBank_AccountHeadCode_Module" >
                  
  <%
  
      Connection con=null;
      ResultSet rs=null,rs2=null,rs3=null;
      PreparedStatement ps=null,ps2=null, ps3=null;
      ResultSet results=null;
      ResultSet results1=null;
      ResultSet results2=null;
      ResultSet results3=null;
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
           <% 
       HttpSession session=request.getSession(false);
         UserProfile empProfile=(UserProfile)session.getAttribute("UserProfile");
      
     System.out.println("user id::"+empProfile.getEmployeeId());
     int empid=empProfile.getEmployeeId();
     
    //int empid=9315;
    int  oid=0;
    String oname="",FAS_SU="",getWing="";        // office name,empname
    boolean isSuperUser=false;
     if(session.getAttribute("FAS_SU")!=null && ((String)session.getAttribute("FAS_SU")).equalsIgnoreCase("YES"))
         FAS_SU="YES";
    else
         FAS_SU="NO";
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
     /* */      
            System.out.println("off id.. emp id"+oid+".."+empid);     
    }
    catch(Exception e)
    {
        System.out.println(e);
    }
   
   %>
    
            <table cellspacing="1" cellpadding="2" border="1" width="100%">
        
            <tr class="table">
                <td colspan="2">
                  <div align="left">
                    Accounting Unit Code 
                    <font color="#ff2121">*</font>
                  </div>
                </td>
                <td>
                      <div align="left">
                         <select size="1" name="cmbAcc_UnitCode" id="cmbAcc_UnitCode" >
                         <%
                            
                                     try
                                     {
                                            ps=con.prepareStatement("select accounting_unit_id,accounting_unit_name from fas_mst_acct_units  order by accounting_unit_name");
                                            rs=ps.executeQuery();
                                            while(rs.next())
                                            {
                                                out.println("<option value="+rs.getInt("accounting_unit_id")+" >"+rs.getString("accounting_unit_name")+"</option>");
                                            }
                                        
                                     } 
                                     catch(Exception e)
                                     {
                                            System.out.println("Exception in unit..."+e);
                                     }
                                     finally
                                     {
                                            rs.close();
                                            ps.close();
                                     }  			
                            
                            %>
                         </select>
                         <input type="button" name="gobtn" id="gobtn" value="Go" onclick="loadhead();"></input>
                      </div>
                    </td>
              </tr>
             
         </table>
                    <div id="grid" style="display:block">
                    <table id="mytable" cellspacing="3" cellpadding="2" border="1" width="100%">
                      
                       <tr class="tdH">
                        	<th>Sl No</th>
                            <th>Bank Name</th>
                            <th>Branch Name</th>
                            <th>Account Head Code</th>
                            <th>Bank Account No</th>
                            <th>Status</th>  
                      </tr>
                      
                       <tbody id="grid_body" class="table" align="left" >
                       </tbody>
					<tr class="tdH">
                                        <td colspan="10" align="center">
					
					 <input type="button" id="Exit" name="Exit" value="EXIT" onclick="self.close()">
                                        </td></tr>	
                    </table>
                  </div>
    </form>
   
    </body>
</html>

