<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">
<%@ page contentType="text/html;charset=windows-1252"%>
<%@ page session="false" contentType="text/html;charset=windows-1252"%>
<%@ page import="java.sql.*,java.util.*,Servlets.Security.classes.UserProfile"%>
<%@ include file="//org/Security/jsps/Check_SessionJSPF.jspf"%>

<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=windows-1252"/>
    <title>List of Account Head Consolidated Report</title>
    <!--script type="text/javascript" src="../scripts/ListofSubLedgerMainForm_CB.js"></script-->
    <script type="text/javascript" src="<%=request.getContextPath()%>/org/Library/scripts/comJS.js"></script>
    <link href="../../../../../css/Sample3.css" rel="stylesheet" media="screen"/>
   <script type="text/javascript" language="javascript">
    function btncancel()
    {
     self.close();
    }
    function EditHead(rowID)
    {
                var Accnt_head="",cmbOffice_code="",sec_name="",grp="";
                var sectionId=0,accnt_head_code=0;
            
                 r=document.getElementById(rowID);
                 rcells=r.cells;
      
                accnt_head_code =rcells.item(5).firstChild.nodeValue;
                Accnt_head =rcells.item(6).firstChild.nodeValue;
                secid=rcells.item(1).firstChild.nodeValue;
                 
                 sect_name=rcells.item(2).firstChild.nodeValue;
                grpid=rcells.item(3).firstChild.nodeValue;
                 
                 grp_name=rcells.item(4).firstChild.nodeValue;
      
       
        
        Minimize();
    ;
        //alert(accHeadCode,accHeadDesc,bankid,operID)
        opener.doParentSection(secid,accnt_head_code,Accnt_head,sect_name,grpid,grp_name)
       // return true;
   }
   
    function Minimize() 
    {
    window.resizeTo(0,0);
    window.screenX = screen.width;
    window.screenY = screen.height;
    opener.window.focus();
    }

</script>
  </head>
  <body  bgcolor="rgb(255,255,225)">
   <%
  
    Connection con=null;
    ResultSet rs=null;
    PreparedStatement ps=null;
    
    ResultSet rs1=null;
    PreparedStatement ps1=null;
     Connection connection=null;
ResultSet rs2=null;
    PreparedStatement ps2=null;
  ResultSet results=null;
  ResultSet results1=null;
  ResultSet results2=null;
    
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
      System.out.println("...............list all LISTjsp started.................");
      HttpSession session=request.getSession(false);
      UserProfile empProfile=(UserProfile)session.getAttribute("UserProfile");
      
       
           int  txtaccountheadcode=0,cmbOffice_code=0,cmbSectionId=0,groupid=0,SL_TYPE=0,SL_CODE=0;
            int voucherno=0;
            String financeyear="";
           /*
            try{
            cmbOffice_code=Integer.parseInt(request.getParameter("officeid"));
            System.out.println("cmbOffice_code..."+cmbOffice_code);
            }catch(Exception e){System.out.println("Exception in getting req:"+e);}*/
           try{
             cmbSectionId=Integer.parseInt(request.getParameter("txtSectionId"));
            }catch(Exception e){System.out.println("Exception in cmbSectionId:"+e);}
            try{
                groupid=Integer.parseInt(request.getParameter("groupid"));
               }catch(Exception e){System.out.println("Exception in cmbSectionId:"+e);}
              
      //  System.out.println();
        System.out.println("groupid"+groupid);
       System.out.println("cmbSectionId"+cmbSectionId);
       
        
            
   %>
  
  <form name="ChequeBookList" id="ChequeBookList">
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
              List of Account Head Directory Details
            </div></td>
        </tr>
       
      </table>
       <table cellspacing="2" cellpadding="3" border="1" width="100%">
       <tr class="tdH">
                        <th>
                            Select
                        </th>
                        <th >
                          Section Id
                        </th>
                        <th style="display:none"> Section Id </th>
                        <th >
                          Section Name
                        </th>
                        <th >
                          Group Id
                        </th>
                        <th style="display:none"> Section Id </th>
                        <th >
                          Group Name
                        </th>
                         <th>
                            Account Head Code
                        </th>
                         <th>
                            Account Head Name
                        </th>
                        
                        
                        
       </tr>
       <tbody id="tb" class="table" align="left">
          <%
         
         String grp="",sect_name="",grp_name="";
         int cnt=0,CheqNo=0;
         
      
           try
           {
        	   if(cmbSectionId==0 && groupid==0){
        		   System.out.println("1");
                      ps=con.prepareStatement("select a.ACCOUNT_HEAD_CODE,a.SECTION_ID,b.SECTION_NAME,c.ACCOUNT_HEAD_DESC,d.GROUP_NAME,d.GROUP_ID from FAS_MST_SECTIONS_GROUPS_HEADS a,FAS_MST_SECTIONS b,COM_MST_ACCOUNT_HEADS c,"+
                                              " FAS_MST_GROUPS d where a.SECTION_ID=b.section_id and a.ACCOUNT_HEAD_CODE=c.ACCOUNT_HEAD_CODE and a.GROUP_ID=d.GROUP_ID order by  a.SECTION_ID,d.GROUP_ID,a.ACCOUNT_HEAD_CODE ");
        	   } 
        	   else if(cmbSectionId!=0 && groupid==0){
        		   System.out.println("2");
        		   ps=con.prepareStatement("select a.ACCOUNT_HEAD_CODE,a.SECTION_ID,b.SECTION_NAME,c.ACCOUNT_HEAD_DESC,d.GROUP_NAME,d.GROUP_ID from FAS_MST_SECTIONS_GROUPS_HEADS a,FAS_MST_SECTIONS b,COM_MST_ACCOUNT_HEADS c,"+
                   " FAS_MST_GROUPS d where a.SECTION_ID=b.section_id and a.ACCOUNT_HEAD_CODE=c.ACCOUNT_HEAD_CODE and a.GROUP_ID=d.GROUP_ID and a.SECTION_ID=? order by a.SECTION_ID,d.GROUP_ID,a.ACCOUNT_HEAD_CODE ");
        		   ps.setInt(1,cmbSectionId);
        	   }
        	   else if(cmbSectionId!=0 && groupid!=0){
        		   System.out.println("3");
        		   ps=con.prepareStatement("select a.ACCOUNT_HEAD_CODE,a.SECTION_ID,b.SECTION_NAME,c.ACCOUNT_HEAD_DESC,d.GROUP_NAME,d.GROUP_ID from FAS_MST_SECTIONS_GROUPS_HEADS a,FAS_MST_SECTIONS b,COM_MST_ACCOUNT_HEADS c,"+
                   " FAS_MST_GROUPS d where a.SECTION_ID=b.section_id and a.ACCOUNT_HEAD_CODE=c.ACCOUNT_HEAD_CODE and a.GROUP_ID=d.GROUP_ID and a.SECTION_ID=? and  a.GROUP_ID=? order by a.SECTION_ID,d.GROUP_ID,a.ACCOUNT_HEAD_CODE ");
        		   ps.setInt(1,cmbSectionId);
        		   ps.setInt(2,groupid);
        	   }
                     //ps.setInt(1,cmbSectionId);
                    // ps.setInt(1,cmbOffice_code);
                   
                     
                     rs=ps.executeQuery();
                     
                    
            while(rs.next())
            {
              cnt++; 
              
             
              
               
                  if(rs.getString("SECTION_NAME")==null)
                      sect_name="--";
                      else{
                      sect_name=rs.getString("SECTION_NAME");
                      System.out.println("SECTION_NAME"+sect_name);
                      }
                if(rs.getString("GROUP_NAME")==null)
                      grp_name="--";
                      else{
                      grp_name=rs.getString("GROUP_NAME");
                      System.out.println("GROUP_NAME"+grp_name);
                      }
              
                out.println("<tr id='" + cnt + "'>");   
                out.println("<td><a href=\"javascript:EditHead('" + cnt + "')\">Edit</a></td>");
                out.println("<td>"+rs.getInt("SECTION_ID")+"</td>");
                 out.println("<td>"+sect_name+"</td>");
                 out.println("<td>"+rs.getInt("GROUP_ID")+"</td>");
                 out.println("<td>"+grp_name+"</td>");
                out.println("<td>"+rs.getInt("ACCOUNT_HEAD_CODE")+"</td>");
                out.println("<td>"+rs.getString("ACCOUNT_HEAD_DESC")+"</td>");
                
                
                
                
                
            }
            
            
            
                 rs.close();
                 
             if(cnt==0)
             out.println("<tr><td>No data found<td><td></td><td></td><td></td><td></td><td></td></tr>");
             
          }
          catch(Exception e)
          {
            System.out.println("Exception in grid.."+e);
          }
          %>
          
          </tbody>
       </table>
        <table align="center" cellspacing="3" cellpadding="2" border="1"
             width="100%">
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