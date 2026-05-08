<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">
<%@ page session="false"  contentType="text/html;charset=windows-1252"%>
<%@ page import="java.sql.*,java.util.*"%>
<%@ include file="//org/Security/jsps/Check_SessionJSPF.jspf" %>
<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=windows-1252"/>
    <title>Edit_Relieval_popup</title>
     <script type="text/javascript" src="<%=request.getContextPath()%>/org/Library/scripts/comJS.js"></script>
  <!--<script type="text/javascript" src="../scripts/Edit_Relieval_popup.js">
    </script>-->
    <script type="text/javascript" language="javascript">
    function btncancel()
    {
     self.close();
    }
    function btnsubmit()
    {
        
        var sele=document.getElementsByName("choice").length;
        //sele=sele.length;
       // alert(sele);
        var val=0;
        if(sele>0)
        {
           
            if(sele==1)
            {
             
                if(document.HRM_Relieval_slno.choice.checked==true)
                {
                val=document.HRM_Relieval_slno.choice.value;
                
                }
            }
            else
            {      //alert("else"+sele);
                    for(i=0;i<sele;i++)
                    { 
                        //alert(document.HRM_Relieval_slno.choice[i].checked)
                        if(document.HRM_Relieval_slno.choice[i].checked==true)
                        {
                        val=document.HRM_Relieval_slno.choice[i].value;
                        break;
                        }
                    }
             }
           
            
        }
            
        Minimize();
        opener.doParentRelie_SLNO(val);
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
  <body >
  
  <form name="HRM_Relieval_slno" id="HRM_Relieval_slno">
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

           // ConnectionString = strdsn.trim() + "@" + strhostname.trim() + ":" + strportno.trim() + ":" +strsid.trim() ;
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
                SELECTION OF AN EMPLOYEE RELIEVAL SERIAL NUMBER
                </th>
           </tr>
        <tr class="tdH" >
        <th align="left" colspan="2">
                Employee Relieval Serial Number Search Criteria Page
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
             Office Id
            </th>
            <th>
             Relieval SLNO
            </th>
            <th>
             Employee Id
            </th>
            <th>
             Date Of Relieval 
            </th>
           
            <th>
             Relieval Time
            </th>
            <th>
             Relieval Reason ID
            </th>
            <th>
             Remarks
            </th>
            
          </tr>
          <tbody id="tb" class="table">
          
          <%
           ResultSet rs2=null;
           PreparedStatement ps2=null;
           try
           {
           int id=0,eid=0,y=0;
            try{
             id=Integer.parseInt(request.getParameter("id"));
            }catch(Exception e){System.out.println("Exception in getting req:"+e);}
            try{
            eid=Integer.parseInt(request.getParameter("eid"));
            }catch(Exception e){System.out.println("Exception in getting req:"+e);}
            String sql="select OFFICE_ID, RELIEVAL_SLNO, EMPLOYEE_ID, TO_CHAR((DATE_OF_RELIEVAL),'DD/MM/YYYY') as DATE_OF_RELIE, RELIEVAL_FN_AN, RELIEVAL_REASON_ID, REMARKS  from HRM_EMP_RELIEVAL_DETAILS where OFFICE_ID="+id+" and  EMPLOYEE_ID="+eid+" order by DATE_OF_RELIEVAL desc  ";
            System.out.println(sql);
            ps2=con.prepareStatement(sql);
            rs2=ps2.executeQuery();
                       
            while(rs2.next())
            {
                
                //System.out.println("hi")     ;
                out.println("<tr>");   
                //out.println("<td><a href=\"javascript:loadTable('" + edit_code + "')\">Edit</a></td>");
                //String sd=(String) rs2.get("DATE_OF_RELIEVAL");//.split('-');
                if(y==0)
                {
                out.println("<td><input type='radio' id='choice' name='choice' checked value='"+rs2.getInt("RELIEVAL_SLNO")+"'/></td>");
                y=1;
                }
                else
                out.println("<td><input type='radio' id='choice' name='choice' value='"+rs2.getInt("RELIEVAL_SLNO")+"'/></td>");
                 //System.out.println("hidgf")     ;
                out.println("<td align='left'>"+rs2.getInt("OFFICE_ID")+"</td>");
                out.println("<td align='left'>"+rs2.getInt("RELIEVAL_SLNO")+"</td>");
                out.println("<td align='left'>"+rs2.getInt("EMPLOYEE_ID")+"</td>");
                // System.out.println("hi")     ;
                out.println("<td align='left'>"+rs2.getString("DATE_OF_RELIE")+"</td>");
                out.println("<td align='left'>"+rs2.getString("RELIEVAL_FN_AN")+"</td>");
                out.println("<td align='left'>"+rs2.getString("RELIEVAL_REASON_ID")+"</td>");
                if(rs2.getString("REMARKS")!=null)
                out.println("<td align='left'>"+rs2.getString("REMARKS")+"</td></tr>");
                if(rs2.getString("REMARKS")==null)
                out.println("<td align='left'>"+""+"</td></tr>");
              
            }
          }
          catch(Exception e)
          {
            System.out.println("Exception in grid.."+e);
          }
           finally
          {
                rs2.close();
                ps2.close();
                con.close();
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