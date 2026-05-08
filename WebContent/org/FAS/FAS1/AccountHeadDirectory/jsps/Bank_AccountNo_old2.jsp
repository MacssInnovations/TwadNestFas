<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">
<%@ page session="false"  contentType="text/html;charset=windows-1252"%>
<%@ page import="java.sql.*,java.util.*,Servlets.Security.classes.UserProfile"%>
<%@ include file="//org/Security/jsps/Check_SessionJSPF.jspf" %>
<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=windows-1252"/> 
    <meta http-equiv="cache-control" content="no-cache">
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
        var accno="",bankid="",br_id="",B_name="",micr="",addr="";
        
        if(sele>0)
        {
           
            if(sele==1)
            {
             
                if(document.FAS_AccNumber_Form.choice.checked==true)
                {
                 accno=document.FAS_AccNumber_Form.choice.value;
                 r=document.getElementById(accno);
                 rcells=r.cells;
                 bankid=rcells.item(1).firstChild.nodeValue;
                 B_name=rcells.item(2).firstChild.nodeValue;
                 addr=document.FAS_AccNumber_Form.haddr.value;
                 micr=document.getElementById("hmicr").value;                             
                 br_id=rcells.item(4).firstChild.nodeValue;
                 
               /*  
                 B_name=rcells.item(3).firstChild.nodeValue+" - "+rcells.item(5).firstChild.nodeValue;
                 micr=rcells.item(6).firstChild.nodeValue;
                 addr=rcells.item(7).firstChild.nodeValue;*/
                }
            }
            else
            {     
                    for(i=0;i<sele;i++)
                    { 
                       
                        if(document.FAS_AccNumber_Form.choice[i].checked==true)
                        {
                             accno=document.FAS_AccNumber_Form.choice[i].value;
                             
                             r=document.getElementById(accno);
                             rcells=r.cells;
                             bankid=rcells.item(1).firstChild.nodeValue;
                             B_name=rcells.item(2).firstChild.nodeValue;
                             addr=document.getElementById("haddr").value;
                             micr=document.getElementById("hmicr").value;
                             br_id=rcells.item(4).firstChild.nodeValue; 
                           
                          /* 
                             B_name=rcells.item(3).firstChild.nodeValue+"-"+rcells.item(5).firstChild.nodeValue;
                             micr=rcells.item(6).firstChild.nodeValue;
                             addr=rcells.item(7).firstChild.nodeValue;*/
                             break;
                        }
                    }
             }
           
            
        }
            
        Minimize();
        opener.doParentAcc_NO(accno,bankid,br_id,B_name,micr,addr);
       // opener.doParentAcc_NO(accno,B_name,addr,micr);
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
  
  <form name="FAS_AccNumber_Form" id="FAS_AccNumber_Form">
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
     <center>
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
            <!--th>
             Bank Id
            </th-->
            <th>
             Bank Name
            </th>
            <!--th>
             Branch Id
            </th>
            <th>
             Branch Name
            </th-->
            <th>
             Account Type
            </th>
            <th>
            Operation Mode
            </th>
           
         <!--   <th>
             Account Head Code
            </th>-->
            
          </tr>
          <tbody id="tb" class="table">

          <%
          int cmbAcc_UnitCode=0;
           ResultSet rs2=null,rs3=null,rs4=null,rs5=null;
           PreparedStatement ps2=null,ps3=null;
           try
           {
           int txtCash_Acc_code=0,y=0;
            try{
             System.out.println("hai");
             cmbAcc_UnitCode=Integer.parseInt(request.getParameter("cmbAcc_UnitCode"));
             
            }catch(Exception e){System.out.println("Exception in getting req:"+e);}
           
            String sql="select distinct BANK_ID, BRANCH_ID, BANK_AC_NO from FAS_OFFICE_BANK_AC_CURRENT where STATUS='Y' and ACCOUNTING_UNIT_ID="+cmbAcc_UnitCode;
            
            System.out.println(sql);
            ps2=con.prepareStatement(sql);
            rs2=ps2.executeQuery();
                       
            while(rs2.next())
            {
                
                //System.out.println("hi")     ;
                out.println("<tr id="+rs2.getLong("BANK_AC_NO")+">");   
                //out.println("<td><a href=\"javascript:loadTable('" + edit_code + "')\">Edit</a></td>");
                if(y==0)
                {
                out.println("<td><input type='radio' id='choice' name='choice' checked value='"+rs2.getLong("BANK_AC_NO")+"'/></td>");
                y=1;
                }
                else
                out.println("<td><input type='radio' id='choice' name='choice' value='"+rs2.getLong("BANK_AC_NO")+"'/></td>");
                 out.println("<td align='left'>"+rs2.getLong("BANK_AC_NO")+"</td>");
                ps3=con.prepareStatement("select BANK_NAME from FAS_MST_BANKS where BANK_ID="+rs2.getInt("BANK_ID")); 
                rs3=ps3.executeQuery();
               // out.println("<td align='left'>"+rs2.getInt("BANK_ID")+"</td>");
                if(rs3.next())
                out.println("<td align='left'>"+rs3.getString("BANK_NAME")+"</td>");
               // out.println("<td align='left'>"+rs2.getInt("BRANCH_ID")+"</td>");
                rs3.close();
                ps3.close();
                
                ps3=con.prepareStatement("select distinct a.ac_operational_mode_id,b.account_type from FAS_OFFICE_BANK_AC_CURRENT a,"
                                        +" FAS_MST_BANK_AC_TYPES b where a.STATUS='Y' and a.bank_ac_type_id=b.account_type_id and a.bank_id ="+rs2.getInt("BANK_ID")+ "  and" 
                                        +" accounting_unit_id="+cmbAcc_UnitCode+" and a.bank_ac_no="+rs2.getInt("BANK_ac_no")); 
                rs3=ps3.executeQuery();
                if(rs3.next())
                {
                out.println("<td align='left'>"+rs3.getString("account_type").trim()+"</td>");
                out.println("<td align='left'>"+rs3.getString("ac_operational_mode_id").trim()+"</td></tr>");
                }
                rs3.close();
                ps3.close();
                
                ps3=con.prepareStatement("select BRANCH_NAME,BRANCH_ADDRESS1,BRANCH_ADDRESS2,CITY_TOWN_NAME,MICR_CODE from FAS_MST_BANK_BRANCHES where BANK_ID="+rs2.getInt("BANK_ID")+"and BRANCH_ID="+rs2.getInt("BRANCH_ID")); 
         //System.out.println("sh.................");
                rs3=ps3.executeQuery();
                String addr1="",final_addr="";
                String addr2="",city="";
                if(rs3.next())
                {
              //  out.println("<td align='left'>"+rs3.getString("BRANCH_NAME")+"</td>");
                if(rs3.getString("BRANCH_ADDRESS1")==null)
                addr1="";
                else
                addr1=rs3.getString("BRANCH_ADDRESS1");
                
                if(rs3.getString("BRANCH_ADDRESS2")==null)
                addr2="";
                else
                addr2=rs3.getString("BRANCH_ADDRESS2");
                
                if(rs3.getString("CITY_TOWN_NAME")==null)
                city="";
                else
                city=rs3.getString("CITY_TOWN_NAME");
                
                final_addr=addr1+addr2+city;
                //System.out.println("Address:...."+final_addr);
                String micr=rs3.getString("micr_code");
                //System.out.println("Micr"+micr);
               out.println("<input type='hidden' name='haddr' id='haddr' value='"+final_addr+"'></input>");
               out.println("<input type='hidden' name='hmicr' id='hmicr' value='"+micr+"'></input>");
                //out.println("<td align='left'>"+rs3.getString("MICR_CODE").trim()+"</td>");
               // out.println("<td align='left'>"+final_addr+"</td></tr>");
               
                }
               
                
            }
          }
          catch(Exception e)
          {
          System.out.println("here error");
            System.out.println("Exception in grid.."+e);
          }
           finally
          {
                rs2.close();
                ps2.close();
                rs3.close();
                ps3.close();
                con.close();
          }
          %>
         
         </tbody>
         <%--
         try
         {
        PreparedStatement ps4=null,ps5=null;
         String sql="select distinct BANK_ID, BRANCH_ID, BANK_AC_NO from FAS_OFFICE_BANK_AC_CURRENT where ACCOUNTING_UNIT_ID="+cmbAcc_UnitCode;
         System.out.println("SSS"+sql);
            System.out.println(sql);
            ps4=con.prepareStatement(sql);
            rs4=ps4.executeQuery();
            while(rs4.next())
            {
            
         ps5=con.prepareStatement("select BRANCH_NAME,BRANCH_ADDRESS1,BRANCH_ADDRESS2,CITY_TOWN_NAME,MICR_CODE from FAS_MST_BANK_BRANCHES where BANK_ID="+rs4.getInt("BANK_ID")+"and BRANCH_ID="+rs4.getInt("BRANCH_ID")); 
         System.out.println("sh.................");
                rs5=ps5.executeQuery();
                String addr1="",final_addr="";
                String addr2="",city="";
                if(rs5.next())
                {
              //  out.println("<td align='left'>"+rs3.getString("BRANCH_NAME")+"</td>");
                if(rs5.getString("BRANCH_ADDRESS1")==null)
                addr1="";
                else
                addr1=rs5.getString("BRANCH_ADDRESS1");
                
                if(rs5.getString("BRANCH_ADDRESS2")==null)
                addr2="";
                else
                addr2=rs5.getString("BRANCH_ADDRESS2");
                
                if(rs5.getString("CITY_TOWN_NAME")==null)
                city="";
                else
                city=rs5.getString("CITY_TOWN_NAME");
                
                final_addr=addr1+addr2+city;
               out.println("<input type='hidden' name='haddr' id='haddr' value='"+final_addr+"'></input>");
                //out.println("<td align='left'>"+rs3.getString("MICR_CODE").trim()+"</td>");
               // out.println("<td align='left'>"+final_addr+"</td></tr>");
               
                }
              }  rs5.close();
                rs4.close();
                
         }catch(Exception e)
         {
         System.out.println("err");
         }
         --%>
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
      </center>
       </div>
    </td></tr></table>
    </form></body>
</html>