<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">
<%@ page contentType="text/html;charset=windows-1252"%>
<%@ page import="java.sql.*,java.util.*,Servlets.Security.classes.UserProfile"%>
<%@ page session="false" contentType="text/html;charset=windows-1252"%>
<%@ include file="//org/Security/jsps/Check_SessionJSPF.jspf" %>
<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=windows-1252"/>
    <title>WQS_LabTransferJSP</title>
    <script type="text/javascript" src="<%=request.getContextPath()%>/org/Library/scripts/comJS.js">
    </script>
    <link href="../../../../../../css/Sample3.css" rel="stylesheet" media="screen"/>
    <script type="text/javascript" src="../scripts/WQS_LabTransferJS.js"></script>
    <link href="../../../../../../css/CalendarControl.css" rel="stylesheet"          media="screen"/>    
    <script type="text/javascript" src="<%=request.getContextPath()%>/org/Library/scripts/CalendarControl.js"></script>        
    <script type="text/javascript" src="../HR/HR1/EmployeeMaster/scripts/CalendarControl.js"></script>  
  </head>
 <body onload="clearAll()">
  <form name="transfer">
  <%
            Connection con=null;
            Statement st=null;
            ResultSet rs=null;
            PreparedStatement ps=null;
            try
              {
                  //Class.forName("oracle.jdbc.OracleDriver");
                  //con=DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe","test","test");
                  ResourceBundle rb=ResourceBundle.getBundle("Servlets.Security.servlets.Config");
                  String ConnectionString="";

                  String strDriver=rb.getString("Config.DATA_BASE_DRIVER");
                  String strdsn=rb.getString("Config.DSN");
                  String strhostname=rb.getString("Config.HOST_NAME");
                  String strportno=rb.getString("Config.PORT_NUMBER");
                  String strsid=rb.getString("Config.SID");
                  String strdbusername=rb.getString("Config.USER_NAME");
                  String strdbpassword=rb.getString("Config.PASSWORD");
                  ConnectionString = strdsn.trim() + "://" + strhostname.trim() + ":" + strportno.trim() + "/" +strsid.trim() ;    // Postgres DB  Connection
                  //ConnectionString = strdsn.trim() + "@" + strhostname.trim() + ":" + strportno.trim() + ":" +strsid.trim() ;
                  Class.forName(strDriver.trim());
                  con=DriverManager.getConnection(ConnectionString,strdbusername.trim(),strdbpassword.trim());             
                  System.out.println("Connected THRO JSP");
              }
              catch(Exception e)
              {
              System.out.println(e.getMessage());
              }
 
                HttpSession session=request.getSession(false);
                UserProfile empProfile=(UserProfile)session.getAttribute("UserProfile");
                  
                System.out.println("user id::"+empProfile.getEmployeeId());
                int empid=empProfile.getEmployeeId();
                int  oid=0,odidt=0;
                String odt="",lb=""; 
                try
                {
           
                    ps=con.prepareStatement("select OFFICE_ID from HRM_EMP_CURRENT_POSTING where EMPLOYEE_ID=?" );
                    ps.setInt(1,empid);
                    rs=ps.executeQuery();
                    if(rs.next()) 
                    {
                            oid=rs.getInt("OFFICE_ID");
                        
                    }
                    rs.close();
                    ps.close();
                    ps=con.prepareStatement("select LAB_CODE,LAB_DESC from WQS_MST_LAB where LAB_CODE=?");
                    ps.setInt(1,oid);
                    rs=ps.executeQuery();
                    if(rs.next()) 
                    {
                        odidt=Integer.parseInt(rs.getString("LAB_CODE"));
                        odt=rs.getString("LAB_DESC");
                        lb=odidt+"--"+odt;
                        System.out.println(lb);
                    }
                    rs.close();
                    ps.close();
               
                }
                catch(Exception e)
                {
                    System.out.println(e);
                }
  %>
   <table cellspacing="2" cellpadding="3" border="1" width="100%"
             align="center">
       <tr>
            <td colspan="2" class="tdH" align="center"><b>Lab to Lab Stock Transaction</b></td>
       </tr> 
         <tr>
            <td class="table" align="left" width="49%">Lab From</td>
            <td class="table" align="left" width="51%">
                <input type="text" name="flab" id="flab" size="35" value="<%=lb%>" readonly="readonly"/>
            </td>
        </tr>
         <tr>
            <td class="table" align="left" width="49%">Lab To</td>
            <td class="table" align="left" width="51%">
                <select name="tlab" id="tlab">
                    <option value="0">--Select Lab--</option>
                    <%
                         try
                        {
                            st=con.createStatement();
                            rs=st.executeQuery("Select LAB_CODE,LAB_DESC from WQS_MST_LAB");
                            while(rs.next())
                            {
                                int lcode=Integer.parseInt(rs.getString("LAB_CODE"));
                                String ldesc=rs.getString("LAB_DESC");
                                if(lcode!=odidt)
                                {
                                    out.println("<option value='"+lcode+"--"+ldesc+"'>"+lcode+"--"+ldesc+"</option>");
                                }
                            }      
                        }
                        catch(Exception e)
                        {
                            System.out.println("Err2:"+e.getMessage());
                        }              
                    %>
                </select>
            </td>
        </tr>
        <tr>
            <td class="table" width="50%">Category</td>
            <td class="table" width="50%">
            <select name="category" id="category" onchange="changeCat()">
            <option value="">--Select Category--</option>
            <%
                 st=con.createStatement();
                 rs=st.executeQuery("Select MAJOR_CATEGORY_CODE,MAJOR_CATEGORY_DESC from WQS_MST_INV_CATEGORY");
                 try
                 {
                     while(rs.next())
                     {
                        String catcode=rs.getString("MAJOR_CATEGORY_CODE");
                        System.out.println(catcode);
                        String catdesc=rs.getString("MAJOR_CATEGORY_DESC");
                        if(!((catdesc.equalsIgnoreCase("instrument"))||(catcode.equalsIgnoreCase("ins"))))
                        {
                            out.println("<option value='"+catcode+"--"+catdesc+"'>"+catcode+"--"+catdesc+"</option>");
                        }                        
                     }
                 }
                 catch(Exception e)
                 {
                    System.out.println("Err3:"+e.getMessage());
                 }
            %>
            </select>
            </td>
        </tr>  
        <tr>
            <td class="table" width="50%">Item</td>
            <td class="table" width="50%">
            <select name="item" id="item" onchange="changeItem('item','null')">
            <option value="">--Select Item--</option>
            </select>
            </td>
        </tr>
        <tr id="divwork" style="display:none">
           <td class="table" width="50%">Brand</td>
           <td class="table" width="50%">
              <select name="bcode" id="bcode" onchange="changeItem('brand','null')">
               <option value="">--Select Brand--</option>
                <%
                  try
                  {
                     st=con.createStatement();
                     rs=st.executeQuery("select brand_code from wqs_chemical_brand");
                     while(rs.next())
                     {
                      out.print("<option value='"+rs.getString(1)+"'>"+rs.getString(1)+"</option>");
                     }
                   }
                   catch(Exception e)
                   {
                      out.println(e.getMessage());
                   }
                %>
              </select>
            </td>
        </tr> 
        <tr>
            <td class="table" width="50%">Available Stock</td>
            <td class="table" width="50%">
            <input type="text" name="avail" id="avail" readonly="readonly">
            <input type="text" name=auom id=auom readonly="readonly" size="3" style="visibility:hidden">
            </td>
        </tr> 
        <tr>
            <td class="table" align="left" width="49%">Date Of Issue &nbsp;<font color="Gray">[dd/mm/yyyy]</font></td>
            <td class="table" align="left" width="51%">
                <input type="text" name="rdate" id="rdate" readonly="readonly"></input>
                <img src="../../../../../../images/calendr3.gif" onclick="showCalendarControl(document.transfer.rdate);" alt="Show Calendar" id="pur_date_cal"></img>                                    
            </td>
        </tr>
        
        <tr>
            <td class="table" align="left" width="49%">Qantity Issued</td>
            <td class="table" align="left" width="51%">
                    <input type="text" name="issued" id="issued" onkeypress="return checklength(event,this)" onblur="checkqty()"></input>&nbsp;<input type="text" name="uom" id="uom" readonly="readonly" size="3" style="visibility:hidden"></input>
            </td>
        </tr>
        <tr>
            <td class="table" align="left" width="49%">Remarks <font color="Gray">&nbsp;&nbsp;&nbsp;&nbsp;[maxmimum 100 Characters]</font></td>
            <td class="table" align="left" width="51%">
                <textarea rows="2" name="remarks" id="remarks" cols="20" onkeypress="return checkremlength(event,this)"></textarea>
            </td>
        </tr>
        
        <tr class="table">
          <td colspan="2">&nbsp;</td>
          
        </tr>
        <tr class="tdH">
          <td colspan="2" align="center">
            <input type="button" value="Submit" onclick="InsertVal()"/>
            <input type="button" name="exit" value="Cancel" id="exit" onclick="javascript:self.close();"/>
          </td>
        </tr>
    </table>
  </form>
  </body>
</html>