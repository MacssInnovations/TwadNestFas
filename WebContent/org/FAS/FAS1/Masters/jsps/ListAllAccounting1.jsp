<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">
<%@ page contentType="text/html;charset=windows-1252"%>
<%@ page session="false" contentType="text/html;charset=windows-1252"%>
<%@ page import="java.sql.*,java.util.*,Servlets.Security.classes.UserProfile"%>
<%@ include file="//org/Security/jsps/Check_SessionJSPF.jspf"%>

<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=windows-1252"/>     <meta http-equiv="cache-control" content="no-cache">
    <title>Bank Account Heads List</title>
    
      <script type="text/javascript" src="<%=request.getContextPath()%>/org/Library/scripts/comJS.js"></script>
      <script type="text/javascript" src="../scripts/ListAllAccountingUnit.js"></script>
    <link href="../../../../../css/Sample3.css" rel="stylesheet" media="screen"/>
   <script type="text/javascript" language="javascript">

    function btncancel()
    {
     self.close();
    }

/*function Minimize() 
{
window.resizeTo(0,0);
window.screenX = screen.width;
window.screenY = screen.height;
opener.window.focus();
}

function loadValuesFromTable(id)
{
alert(id)
    Minimize();
    var r=document.getElementById(id);
    alert(r);
    var rcells=r.cells;
    var table=document.getElementById("tblList");
   // var table=document.frmAccountUnit.tblList;
    alert(table);
    var rows=table.rows;
    var value;
    var length=rows.length;
    alert(length);
    var accountingunitid=rcells.item(1).firstChild.nodeValue;
    alert(accountingunitid);
    var accountunitname=rcells.item(1).lastChild.value;
    alert(accountingname);
    var accountunitoffice=rcells.item(2).firstChild.nodeValue;
    var accountrenderoffice=rcells.item(3).firstChild.nodeValue;
    opener.List(accountingunitid,accountunitname,accountunitoffice,accountrenderoffice,length);
    self.close();
}
*/
    
    function EditHead(accHeadId)
    {
                
                var accHeadCode="",accHeadDesc="",bankid="",operID="";
                 accHeadCode=accHeadId;
                 r=document.getElementById(accHeadCode);
                 rcells=r.cells;
                 //alert(rcells.item(1).firstChild.nodeValue);
                 //alert(rcells.item(2).firstChild.nodeValue);
                 //alert(rcells.item(3).firstChild.nodeValue);
                 //alert(rcells.item(4).firstChild.nodeValue);
                 //alert(rcells.item(5).firstChild.nodeValue);
                 //alert(rcells.item(6).firstChild.nodeValue);
                 accHeadDesc=rcells.item(2).firstChild.nodeValue;
                 bankid=rcells.item(3).firstChild.nodeValue;
                 operID=rcells.item(5).firstChild.nodeValue;
     
        Minimize();
    
        //alert(accHeadCode,accHeadDesc,bankid,operID)
        opener.doParentBankAccHeads(accHeadCode,accHeadDesc,bankid,operID);
        //return true;
   }
   

</script>
  </head>
  <body  bgcolor="rgb(255,255,225)">
 
 
   <%
  
    Connection con=null;
    ResultSet rs=null;
    PreparedStatement ps=null;
    
    
     Connection connection=null;
ResultSet res=null;
  ResultSet results=null;
  ResultSet results1=null;
  ResultSet results2=null;
    PreparedStatement ps1=null;
    PreparedStatement ps2=null;
    PreparedStatement ps3=null;
    String sql5="";
    String sql3="";
    ResultSet results3=null;
   try
  {
  
             ResourceBundle rb=ResourceBundle.getBundle("Servlets.Security.servlets.Config");
            String ConnectionString="";

            String strDriver=rb.getString("Config.DATA_BASE_DRIVER");
            String strdsn=rb.getString("Config.DSN");
            String strhostname=rb.getString("Config.HOST_NAME");
            String strportno=rb.getString("Config.PORT_NUMBER");
            String strsid=rb.getString("Config.SID");
            String strdbusername=rb.getString("Config.USER_NAME");
            String strdbpassword=rb.getString("Config.PASSWORD");

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
  
   <%
   int accid=0;
                     
                      %>  
                                              
                        
                        
 
        
  <form action="" name="frmAccountList">
      <table cellspacing="2" cellpadding="3" border="1" width="100%">
        <tr class="tdH">
          <td>
            <div align="center">
              TWAD BOARD-INTEGRATED ONLINE SYSTEM -FINANACIAL ACCOUNTING SYSTEM
            </div></td>
        </tr>
        <tr class="table">
          <td>
            <div align="center">
              List of All Accounting Details
            </div></td>
            
        </tr>
       
      </table>
       <table cellspacing="2" cellpadding="3" border="1" width="100%">
       
       <tr class="tdH">
      <th>Select </th>
       <th>Accounting&nbsp;Unit&nbsp;ID  </th>
       <th>
             Accounting Unit Name
      </th>
        <th style="display:none"> Account Head Name </th>
       <th style="display:none"> Bank Id </th>
       <th>
            Accounting Rendering Office ID
          </th>
       <th style="display:none">Operational Mode Id       </th>
       <th>
            Office Name
          </th>
            <th>
            Date of Opening
          </th>
       </tr>
       <tbody id="tb" class="table" align="left">
          <%
           String officeid=""; 
           String offname="";
           String accid1="";
           String accname="";
           String ed="";
           String DOpening=null;
           int flag=0;
           try
                        {
                        
                     /*   ps2=con.prepareStatement("select count(*) as cnt,a.ACCOUNTING_UNIT_ID from fas_mst_acct_unit_offices a group by ACCOUNTING_UNIT_ID order by accounting_unit_id" );
                            res=ps2.executeQuery();
                             while(res.next())
                              {
                              int cont=res.getInt("cnt");
                              System.out.println("count:"+cont);
                              if(cont>1)
                              {
                             // officeid=officeid+","+results1.getInt("accounting_unit_office_id");
                             // offname=offname+","+results1.getString("office_name");
                             flag=1;
                              }
                              else
                              {
                              
                             // officeid=results1.getString("accounting_unit_office_id");
                            //  offname=results1.getString("office_name");
                            flag=0;
                              }
                              
                              }*/
                        
                        
                        
                     //   ps=con.prepareStatement("select accounting_unit_id,accounting_unit_name from fas_mst_acct_units order by accounting_unit_id");
                     ps=con.prepareStatement("select count(*) as cnt,a.ACCOUNTING_UNIT_ID from fas_mst_acct_unit_offices a group by ACCOUNTING_UNIT_ID order by accounting_unit_id" );
                        results=ps.executeQuery();
                        while(results.next())
                        {
                        accid=results.getInt("accounting_unit_id");
                        System.out.println("Accid"+accid);
                        
                        int cont=results.getInt("cnt");
                        System.out.println(cont);
                                   
                  if(cont>1)
                  {
                  flag=1;
                    sql3="select a.accounting_unit_id,a.accounting_unit_name,a.accounting_unit_office_id,b.accounting_for_office_id,c.office_name,to_char(b.DATE_EFFECTIVE_FROM,'DD/MM/YYYY') as  DATE_OF_OPENING from fas_mst_acct_units a,fas_mst_acct_unit_offices b,com_mst_offices c where a.accounting_unit_id=b.accounting_unit_id and b.accounting_for_office_id=c.office_id and a.accounting_unit_id=?";
                        ps3=con.prepareStatement(sql3);
                        ps3.setInt(1,accid);
                        results3=ps3.executeQuery();
                        while(results3.next())
                        {
                        	ed="<a href=\"javascript:loadValuesFromTable(" + accid + ")\">Edit</a>";
                         	accid1=results3.getString("accounting_unit_id");
                         	accname=results3.getString("accounting_unit_name");
                        	officeid=results3.getString("accounting_for_office_id")+","+officeid;
                        	offname=results3.getString("office_name")+","+offname;
                        	if(results3.getString("DATE_OF_OPENING")==null)
                        	{
                        		DOpening="";
                        	}
                        	else
                        	{
                        		DOpening=results3.getString("DATE_OF_OPENING")+","+DOpening;
                        	}
                        		
                        }                     
                  }
                  else
                  {
                  flag=0;
                   sql3="select a.accounting_unit_id,a.accounting_unit_name,a.accounting_unit_office_id,b.accounting_for_office_id,c.office_name,to_char(a.DATE_OF_OPENING,'DD/MM/YYYY') as DATE_OF_OPENING  from fas_mst_acct_units a,fas_mst_acct_unit_offices b,com_mst_offices c where a.accounting_unit_id=b.accounting_unit_id and b.accounting_for_office_id=c.office_id and a.accounting_unit_id=?";
                        ps3=con.prepareStatement(sql3);
                        ps3.setInt(1,accid);
                        results3=ps3.executeQuery();
                        while(results3.next())
                        {
	                         ed="<a href=\"javascript:loadValuesFromTable(" + accid + ")\">Edit</a>";
	                         accid1=results3.getString("accounting_unit_id");
	                         accname=results3.getString("accounting_unit_name");
	                         officeid=results3.getString("accounting_for_office_id");
	                         offname=results3.getString("office_name");
	                         if(results3.getString("DATE_OF_OPENING")==null)
	                        	{
	                        		DOpening="";
	                        	}
	                        	else
	                        	{
	                        		DOpening=results3.getString("DATE_OF_OPENING");
	                        	}
                        }
                  }
                  try
                        {
                        System.out.println("inside");
                        System.out.println(accid);
                        sql5="select distinct a.accounting_unit_id,a.accounting_unit_name,a.accounting_unit_office_id,b.accounting_for_office_id,c.office_name from fas_mst_acct_units a,fas_mst_acct_unit_offices b,com_mst_offices c where a.accounting_unit_id=b.accounting_unit_id and b.accounting_for_office_id=c.office_id and a.accounting_unit_id=?";
                        ps1=con.prepareStatement(sql5);
                        ps1.setInt(1,accid);
                        results1=ps1.executeQuery();
                        int cnt=0;   
                         
                                   
                        while(results1.next())
                        {
                            cnt++;
                            //offname=offname.substring(0,offname.length()-1);
                            //officeid=officeid.substring(0,officeid.length()-1);
                            System.out.println("After this"+officeid);
                            out.println("<tr id=" + accid+ ">");   
                            out.println("<td>"+ed+"</td>");
                            out.println("<td>"+accid1+"</td>");
                
                            out.println("<td>"+accname+"</td>");
                            
                            out.println("<td>"+officeid+"</td>");
                
                            out.println("<td>"+offname+"</td>");
                            if(DOpening!="")
                            {
                            	out.println("<td>"+DOpening+"</td>");
                            }
                            else 
                            {
                            	out.println("<td>--</td>");
                            }
                           
                            System.out.println("dfd");
                            officeid="";
                            ed=""; accid1="";
                            accname="";
                            offname="";
                            DOpening="";
                        }
                        if(cnt==0)
                           {
                            //out.println("<tr><td>No data found<td><td></td><td></td></tr>");
                           } 
                        }
                        catch(Exception e)
                            {
                            System.out.println("Exception in grid.."+e);
                            }
               //   }
                //  }
                  
         
         }
         }catch(Exception e)
            {
                System.out.println("Exception in Select:"+e);
            }
          
          
          %>
                       
          </tbody>
       </table>
        <table align="center" cellspacing="3" cellpadding="2" border="1"
             width="100%">
             
         <tr>
            <td colspan="2">
                <table id="Existing" cellspacing="2" cellpadding="3" border="1" width="100%">
                          <tr class="tdH">
                         
                          
                                <tbody id="tblList" name="tblList">
                                </tbody>
                         </tr>
                         
                 </table>
            
            </td>
            
        </tr>    
        <tr class="tdH">
          <td>
            <div align="center">
              <input type="button" id="cmdcancel" name="cancel" value="Exit"
                     onclick=" self.close();"></input>
            </div>
          </td>
        </tr>
      </table> 
    </form></body>
</html>