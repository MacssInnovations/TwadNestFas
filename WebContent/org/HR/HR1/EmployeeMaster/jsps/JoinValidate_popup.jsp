<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">
<%@ page session="false"  contentType="text/html;charset=windows-1252"%>
<%@ page import="java.sql.*,java.util.*"%>
<%@ include file="//org/Security/jsps/Check_SessionJSPF.jspf" %>
<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=windows-1252"/>
    <title>Join Validation</title>
  <!--   <script type="text/javascript" src="<%=request.getContextPath()%>/org/Library/scripts/comJS.js"></script>-->
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
             
                if(document.HRM_Join_Valid.choice.checked==true)
                {
                val=document.HRM_Join_Valid.choice.value;
               // alert(val);
                }
            }
            else
            {      //alert("else"+sele);
                    for(i=0;i<sele;i++)
                    { 
                        
                        if(document.HRM_Join_Valid.choice[i].checked==true)
                        {
                        val=document.HRM_Join_Valid.choice[i].value;
                        
                        break;
                        }
                    }
             }
           
            
        }
            
        Minimize();
        // alert(val);
        opener.doParentJoin(val);
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
  
  <form name="HRM_Join_Valid" id="HRM_Join_Valid">
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
                SELECTION OF AN EMPLOYEE JOIN SERIAL NUMBER
                </th>
           </tr>
        <tr class="tdH" >
        <th align="left" colspan="2">
                Employee Join Serial Number Search Criteria Page
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
             Joining Report Id
            </th>
            <th>
             Employee Id
            </th>
            <th>
             DOJ
            </th>
            <th>
             FN_OR_AN
            </th>
           
            <th>
             Office Name
            </th>
            <th>
             Remarks
            </th>
            <th>
             Process Status
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
             System.out.println("eid::"+request.getParameter("eid"));
            eid=Integer.parseInt(request.getParameter("eid"));
            System.out.println("eid::"+eid);
            }catch(Exception e){System.out.println("Exception in getting req:"+e);}
            //String sql="select OFFICE_ID, RELIEVAL_SLNO, EMPLOYEE_ID, TO_CHAR((DATE_OF_RELIEVAL),'DD/MM/YYYY') as DATE_OF_RELIE, RELIEVAL_FN_AN, RELIEVAL_REASON_ID, REMARKS  from HRM_EMP_RELIEVAL_DETAILS where OFFICE_ID="+id+" and  EMPLOYEE_ID="+eid+" order by DATE_OF_RELIEVAL desc  ";
            String sql="select a.EMPLOYEE_ID,a.JOINING_REPORT_ID,a.DATE_OF_JOINING,a.FN_OR_AN,b.office_name, "+
            " a.REMARKS,c.PROCESS_FLOW_STATUS_DESC from HRM_EMP_JOIN_REPORTS a inner join COM_MST_OFFICES b on a.office_id=b.office_id "+
            "  inner join COM_MST_PROCESS_FLOW c on c.PROCESS_FLOW_STATUS_ID=a.PROCESS_FLOW_STATUS_ID"+
            " where a.EMPLOYEE_ID=?";
            
            ps2=con.prepareStatement(sql);
            ps2.setInt(1,eid);
            rs2=ps2.executeQuery();
                       
            while(rs2.next())
            {
                
                //System.out.println("hi")     ;
                out.println("<tr>");   
                /* if(y==0)
                {
                out.println("<td><input type='radio' id='choice' name='choice' checked value='"+rs2.getInt("JOINING_REPORT_ID")+"'/></td>");
                y=1;
                }
                else*/
                out.println("<td><input type='radio' id='choice' name='choice' value='"+rs2.getInt("JOINING_REPORT_ID")+"' checked/></td>");
                out.println("<td align='left'>"+rs2.getInt("JOINING_REPORT_ID")+"</td>");
                out.println("<td align='left'>"+rs2.getInt("EMPLOYEE_ID")+"</td>");
                
                if(rs2.getDate("DATE_OF_JOINING")!=null)
                {
                String[] sd=rs2.getDate("DATE_OF_JOINING").toString().split("-");
                String od=sd[2]+"/"+sd[1]+"/"+sd[0];
                out.println("<td align='left'>"+od+"</td>");
                }
                else 
                    out.println("<td align='left'>"+rs2.getDate("DATE_OF_JOINING")+"</td>");
                 out.println("<td align='left'>"+rs2.getString("FN_OR_AN")+"</td>");
                out.println("<td align='left'>"+rs2.getString("office_name")+"</td>");
                if(rs2.getString("REMARKS")!=null)
                out.println("<td align='left'>"+rs2.getString("REMARKS")+"</td>");
                if(rs2.getString("REMARKS")==null)
                out.println("<td align='left'>"+""+"</td>");
                 out.println("<td align='left'>"+rs2.getString("PROCESS_FLOW_STATUS_DESC")+"</td></tr>");
              
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