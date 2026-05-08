<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">
<%@ page contentType="text/html;charset=windows-1252"%>
<%@ page import="java.sql.*,java.util.*"%>
<%@ page session="false" contentType="text/html;charset=windows-1252"%>

<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=windows-1252"/>
    <title>WQS_PurchaseOrder_Popup</title>
    <script type="text/javascript" src="<%=request.getContextPath()%>/org/Library/scripts/comJS.js">
    </script>
    <link href="../../../../../../css/Sample3.css" rel="stylesheet" media="screen"/>
    <script type="text/javascript" src="../scripts/WQS_PurchaseOrder_Update.js"></script>
  </head>
  <body onload="retriveValues()">
  <form name="PurchaseDetails">
  <%
    int lab=Integer.parseInt(request.getParameter("lab"));
    int sno=Integer.parseInt(request.getParameter("sno"));
    String sna=request.getParameter("Supno");
    String cat=request.getParameter("cat");
    String icode=request.getParameter("icode");
    String idesc=request.getParameter("idesc");
    int qty=Integer.parseInt(request.getParameter("qty"));
    String uom=request.getParameter("uom");
    String rem=request.getParameter("rem");
    System.out.println(cat+" "+icode+" "+idesc+" "+qty+" "+rem);
    out.println("<input type=hidden name=lcode id=lcode value="+lab+">");
    out.println("<input type=hidden name=sno id=sno value="+sno+">");
    out.println("<input type=hidden name=cat id=cat value='"+cat+"'>");
    out.println("<input type=hidden name=sna id=sna value='"+sna+"'>");
    out.println("<input type=hidden name=icode id=icode value='"+icode+"'>");
    out.println("<input type=hidden name=idesc id=idesc value='"+idesc+"'>");
    out.println("<input type=hidden name=qty id=qty value="+qty+">");
    out.println("<input type=hidden name=uom id=uom value='"+uom+"'>");
    out.println("<input type=hidden name=rem id=rem value='"+rem+"'>");
    
  %>
  
  <table cellspacing="2" cellpadding="3" border="1" width="100%"
             align="center">
       <tr>
            <td colspan="2" class="tdH" align="center"><b>Purchase Order Item Update Details</b></td>
                   
       </tr> 
         
                    <%
                        Connection con=null;
                        Statement st=null;
                        ResultSet rs=null;
                        PreparedStatement ps=null;
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
                          ConnectionString = strdsn.trim() + "://" + strhostname.trim() + ":" + strportno.trim() + "/" +strsid.trim() ;    // Postgres DB  Connection
                          //ConnectionString = strdsn.trim() + "@" + strhostname.trim() + ":" + strportno.trim() + ":" +strsid.trim() ;
                          Class.forName(strDriver.trim());
                          con=DriverManager.getConnection(ConnectionString,strdbusername.trim(),strdbpassword.trim());
                          try
                          {
                               st=con.createStatement();
                               con.clearWarnings();
                          }
                          catch(SQLException e)
                          {
                               System.out.println("Exception in creating statement:"+e);
                          }
             }
             catch(Exception e)
             {
                System.out.println("Exception in opening connection:"+e);
             }
                                     
                    %>
        <tr>
            <td class="table" width="50%">Category</td>
            <td class="table" width="50%">
            <select name="category" id="category" onchange="changeCat()">
            <option value="">--Select Category--</option>
            <%
                 ps=con.prepareStatement("select * from (select distinct MAJOR_CATEGORY_CODE from WQS_MST_INV_SUP_ITEM  where LAB_CODE=? and SUPPLIER_CODE=?)a inner join(Select MAJOR_CATEGORY_CODE,MAJOR_CATEGORY_DESC from WQS_MST_INV_CATEGORY)b on a.MAJOR_CATEGORY_CODE=b.MAJOR_CATEGORY_CODE");
                 ps.setInt(1,lab);
                 ps.setString(2,sna);
                 rs=ps.executeQuery();
                 try
                 {
                 while(rs.next())
                 {
                    String catcode=rs.getString("MAJOR_CATEGORY_CODE");
                    System.out.println(catcode);
                    String catdesc=rs.getString("MAJOR_CATEGORY_DESC");
                    out.println("<option value='"+catcode+"--"+catdesc+"'>"+catcode+"--"+catdesc+"</option>");
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
            <select name="item" id="item">
            <option>--Select Item--</option>
            </select>
            </td>
        </tr>  
        
        <tr>
            <td class="table" align="left" width="49%">Qantity Ordered</td>
            <td class="table" align="left" width="51%">
                <input type="text" name="order" id="order" onkeypress="return isNumberKey(event,this)"></input><input type="text" name="uom" id="uom" value="<%=uom%>" size="3"></input>
            </td>
        </tr>
        <tr>
            <td class="table" align="left" width="49%">Remarks</td>
            <td class="table" align="left" width="51%">
                <textarea rows="2" name="remarks" id="remarks" cols="20"></textarea>
            </td>
        </tr>       
        <tr class="table">
          <td colspan="2">&nbsp;</td>
          
        </tr>
        <tr class="tdH">
          <td colspan="2" align="center">
            <input type="button" name="submit" value="Update Item" id="submit" onclick="btnsubmit()"/>
            <input type="button" name="exit" value="Cancel" id="exit" onclick="javascript:self.close();"/>
          </td>
         
        </tr>
    </table>
  </form>
  </body>
</html>