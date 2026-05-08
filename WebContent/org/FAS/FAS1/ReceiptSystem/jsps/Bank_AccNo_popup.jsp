<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">
<%@ page session="false"  contentType="text/html;charset=windows-1252"%>
<%@ page import="java.sql.*,java.util.*,Servlets.Security.classes.UserProfile"%>
<%@ include file="//org/Security/jsps/Check_SessionJSPF.jspf" %>
<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=windows-1252"/> 
    <META HTTP-EQUIV="CACHE-CONTROL" CONTENT=" no-store, no-cache, must-revalidate" >
   <META HTTP-EQUIV="CACHE-CONTROL" CONTENT=" pre-check=0, post-check=0, max-age=0" >
    <title>Account Number List</title>
     <script type="text/javascript" src="<%=request.getContextPath()%>/org/Library/scripts/comJS.js"></script>
    <script type="text/javascript" language="javascript">
    function btncancel()
    {
     self.close();
    }
    function btnsubmit()
    {
        
        var sele=document.getElementsByName("choice").length;
        var val=0;
        var accno,bankid,br_id,B_name;
        
        if(sele>0)
        {
           
            if(sele==1)
            {
             
                if(document.FAS_AccNo_Popup_Form.choice.checked==true)
                {
                 accno=document.FAS_AccNo_Popup_Form.choice.value;
                 r=document.getElementById(accno);
                 rcells=r.cells;
                 bankid=rcells.item(2).firstChild.nodeValue;
                 br_id=rcells.item(4).firstChild.nodeValue;
                 B_name=rcells.item(3).firstChild.nodeValue+" - "+rcells.item(5).firstChild.nodeValue;
                }
            }
            else
            {      //alert("else"+sele);
                    for(i=0;i<sele;i++)
                    { 
                        //alert(document.FAS_AccNo_Popup_Form.choice[i].checked)
                        if(document.FAS_AccNo_Popup_Form.choice[i].checked==true)
                        {
                             accno=document.FAS_AccNo_Popup_Form.choice[i].value;
                             
                             r=document.getElementById(accno);
                             rcells=r.cells;
                             bankid=rcells.item(2).firstChild.nodeValue;
                             br_id=rcells.item(4).firstChild.nodeValue;
                             B_name=rcells.item(3).firstChild.nodeValue+"-"+rcells.item(5).firstChild.nodeValue;
                             break;
                        }
                    }
             }
           
            
        }
            
        Minimize();
        opener.doParentAcc_NO(accno,bankid,br_id,B_name);
        return true;
   }
   
    function Minimize() 
    {
    window.resizeTo(0,0);
    window.screenX = screen.width;
    window.screenY = screen.height;
    opener.window.focus();
    }

</script>
    <link href="../../../../../css/Sample3.css" rel="stylesheet" media="screen"/>
  </head>
  <%
response.setHeader("Cache-Control","no-cache"); //HTTP 1.1
response.setHeader("Pragma","no-cache"); //HTTP 1.0
response.setDateHeader ("Expires", 0); //prevent caching at the proxy server
%>
  <body >
  
  <form name="FAS_AccNo_Popup_Form" id="FAS_AccNo_Popup_Form">
      <p>
        <%
  
  Connection con=null;
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
      </p>
      <p>
        &nbsp;
      </p>
     
      <table  border="0" width="80%">
      <tr><td>
       <div align="center">
        <table cellspacing="2" cellpadding="3" border="1" width="100%">
         <tr class="tdH" >
        <th align="center" colspan="2">
                Selection of Account Number
                </th>
           </tr>
     </table>
      
         
        
         <table id="mytable" align="center"  cellspacing="3" 
         cellpadding="2" border="1" width="100%">
          <tr class="tdH">
            <th>
              Select
            </th>
             <th>
             Account Number
            </th>
            <th>
             Bank Id
            </th>
            <th>
             Bank Name
            </th>
            <th>
             Branch Id
            </th>
            <th>
             Branch Name
            </th>
           
         <!--   <th>
             Account Head Code
            </th>-->
            
          </tr>
          <tbody id="tb" class="table">

          <%
           ResultSet rs2=null,rs3=null;
           PreparedStatement ps2=null,ps3=null;
           try
           {
           int cmbOffice_code=0,txtCash_Acc_code=0,y=0;
            try{
             cmbOffice_code=Integer.parseInt(request.getParameter("cmbOffice_code"));
            }catch(Exception e){System.out.println("Exception in getting req:"+e);}
            try{
            txtCash_Acc_code=Integer.parseInt(request.getParameter("txtCash_Acc_code"));
            }catch(Exception e){System.out.println("Exception in getting req:"+e);}
            String sql="select BANK_ID, BRANCH_ID, BANK_AC_NO from FAS_OFFICE_BANK_AC_CURRENT where  OFFICE_ID="+cmbOffice_code+" and STATUS='Y' and  AC_HEAD_CODE="+txtCash_Acc_code;
            //String sql="select BANK_ID, BRANCH_ID, BANK_AC_NO from FAS_OFFICE_BANK_AC_CURRENT where OFFICE_ID="+cmbOffice_code;
            System.out.println(sql);
            ps2=con.prepareStatement(sql);
            rs2=ps2.executeQuery();
                       
            while(rs2.next())
            {
                
                out.println("<tr id="+rs2.getInt("BANK_AC_NO")+">");   
               if(y==0)
                {
                out.println("<td><input type='radio' id='choice' name='choice' checked value='"+rs2.getInt("BANK_AC_NO")+"'/></td>");
                y=1;
                }
                else
                out.println("<td><input type='radio' id='choice' name='choice' value='"+rs2.getInt("BANK_AC_NO")+"'/></td>");
                
                out.println("<td align='left'>"+rs2.getInt("BANK_AC_NO")+"</td>");
                ps3=con.prepareStatement("select BANK_NAME from FAS_MST_BANKS where BANK_ID="+rs2.getInt("BANK_ID")); 
                rs3=ps3.executeQuery();
                out.println("<td align='left'>"+rs2.getInt("BANK_ID")+"</td>");
                if(rs3.next())
                out.println("<td align='left'>"+rs3.getString("BANK_NAME")+"</td>");
                out.println("<td align='left'>"+rs2.getInt("BRANCH_ID")+"</td>");
                rs3.close();
                ps3.close();
                ps3=con.prepareStatement("select BRANCH_NAME from FAS_MST_BANK_BRANCHES where BANK_ID="+rs2.getInt("BANK_ID")+"and BRANCH_ID="+rs2.getInt("BRANCH_ID")); 
                rs3=ps3.executeQuery();
                if(rs3.next())
                out.println("<td align='left'>"+rs3.getString("BRANCH_NAME")+"</td></tr>");
            }
            if(y!=0)
             {
                    rs2.close();
                    ps2.close();
                    rs3.close();
                    ps3.close();
             }
          }
          catch(Exception e)
          {
            System.out.println("Exception in grid.."+e);
          }
         
          %>
         </tbody>
        </table>
     
      <table align="center"  cellspacing="3" cellpadding="2" border="1" width="100%" >
  
      <tr class="tdH">
      <td>
          <div align="center">
      <input type="button" id="cmdsubmit" name="Submit" value="Submit" onclick=" btnsubmit()">
         <input type="button" id="cmdcancel" name="cancel" value="Cancel" onclick="btncancel()">
      </div>
      </td>
      </tr>
      
      </table>
       </div>
    </td></tr></table>
    </form></body>
</html>