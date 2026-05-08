<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">
<%@ page session="false"  contentType="text/html;charset=windows-1252"%>
<%@ page import="java.sql.*,java.util.*,java.sql.Date,Servlets.Security.classes.UserProfile"%>
<%@ include file="//org/Security/jsps/Check_SessionJSPF.jspf" %>
<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=windows-1252"/>   
     <META HTTP-EQUIV="CACHE-CONTROL" CONTENT=" no-store, no-cache, must-revalidate" >
   <META HTTP-EQUIV="CACHE-CONTROL" CONTENT=" pre-check=0, post-check=0, max-age=0" >
    <title>Payment System</title>
   
     <script type="text/javascript" src="<%=request.getContextPath()%>/org/Library/scripts/comJS.js"></script>
     <script type="text/javascript" src="../scripts/PendingBills_Revised.js"></script>
     <link href="../../../../../css/Sample3.css" rel="stylesheet" media="screen"/>
     <!-- /*@NK Included on 26062019 to create own alert box*/ -->
      <!--/*MK@18102021 <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.2.1/jquery.min.js"></script> -->
      <script src="https://code.jquery.com/jquery-3.6.0.min.js" type="text/javascript"></script>
      <!-- /*@NK Included on 26062019 to create own alert box*/ -->
    <script type="text/javascript" language="javascript">
    
    function btncancel()
    {
     self.close();
    }
   
    
   /**/
   
    function Minimize() 
    {
    window.resizeTo(0,0);
    window.screenX = screen.width;
    window.screenY = screen.height;
    opener.window.focus();
    }
   function loadimg()
   {
	 document.getElementById("img1").style.visibility="visible";  
   }
    </script>
    
    
     <!-- /*@NK Included on 26062019 to create own alert box*/ -->
 <style>
         #confirm {
            display: none;
            background-color: #FF0000;
            border: 1px solid #aaa;
            position: fixed;
            width: 250px;
            left: 50%;
            margin-left: -100px;
            padding: 6px 8px 8px;
            box-sizing: border-box;
            text-align: center;
         }
         #confirm button {
            background-color: #48E5DA;
            display: inline-block;
            border-radius: 5px;
            border: 1px solid #aaa;
            padding: 5px;
            text-align: center;
            width: 80px;
            cursor: pointer;
         }
         #confirm .message {
            text-align: left;
         }
      </style>   
    
    <!-- /*@NK Included on 26062019 to create own alert box*/ -->
    
      </head>
       <%
response.setHeader("Cache-Control","no-cache"); //HTTP 1.1
response.setHeader("Pragma","no-cache"); //HTTP 1.0
response.setDateHeader ("Expires", 0); //prevent caching at the proxy server
%>
  <body class="table"  onload="GetVoucherInfo();" >
  <form name="frmPending_Bills_revised" method="POST" >
  <input type="hidden" id="hid_value" name="hid_value" value="Single">
   <%
  Connection con=null;
  ResultSet rs=null,rs2=null,rs3=null;
  PreparedStatement ps=null,ps2=null,ps3=null;
  ResultSet results=null;
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
    System.out.println("Exception in connection...."+e);
  }
   int  cmbAcc_UnitCode=0,cmbOffice_code=0,yr=0,mon=0,type_MasSL=0,code_MasSL=0,hid=0;
   Date txtCrea_date=null,frmDate=null,toDate=null;
    Calendar c;
    System.out.println("hai");
            try{
             cmbAcc_UnitCode=Integer.parseInt(request.getParameter("cmbAcc_UnitCode"));
            }catch(Exception e){System.out.println("Exception in getting req:"+e);}
            try{
            cmbOffice_code=Integer.parseInt(request.getParameter("cmbOffice_code"));
            }catch(Exception e){System.out.println("Exception in getting req:"+e);}
            
            String[] sd=request.getParameter("dateval").split("/");
            c=new GregorianCalendar(Integer.parseInt(sd[2]),Integer.parseInt(sd[1])-1,Integer.parseInt(sd[0]));
            java.util.Date d=c.getTime();
            txtCrea_date=new Date(d.getTime());
            System.out.println("txtCrea_date "+txtCrea_date);
            
            System.out.println("b4 getting month and year");
            try{yr=Integer.parseInt(sd[2]);}
                        catch(Exception e){System.out.println("exception"+e );}
                        System.out.println("txtCash_year "+yr);
                        
                        try{mon=Integer.parseInt(sd[1]);}
                        catch(Exception e){System.out.println("exception"+e );}
                        System.out.println("txtCash_Month_hid "+mon);
            /*try{
                 yr=Integer.parseInt(request.getParameter("dateval"));
               }catch(Exception e){System.out.println("Exception in getting req:"+e);}
            try{
                 mon=Integer.parseInt(request.getParameter("mon"));
            }catch(Exception e){System.out.println("Exception in getting req:"+e);}*/
            
            try{
                 type_MasSL=Integer.parseInt(request.getParameter("type_MasSL"));
            }catch(Exception e){System.out.println("Exception in getting req:"+e);}
        
            try{
                 code_MasSL=Integer.parseInt(request.getParameter("code_MasSL"));
            }catch(Exception e){System.out.println("Exception in getting req:"+e);}
           
            String[] sd1=request.getParameter("frmDate").split("/");
            c=new GregorianCalendar(Integer.parseInt(sd1[2]),Integer.parseInt(sd1[1])-1,Integer.parseInt(sd1[0]));
            java.util.Date d1=c.getTime();
            frmDate=new Date(d1.getTime());
            System.out.println("frmDate "+frmDate);
            try{
                hid=Integer.parseInt(request.getParameter("hid"));
           }catch(Exception e){System.out.println("Exception in getting req:"+e);}
            
            
            String[] sd2=request.getParameter("toDate").split("/");
            c=new GregorianCalendar(Integer.parseInt(sd2[2]),Integer.parseInt(sd2[1])-1,Integer.parseInt(sd2[0]));
            java.util.Date d2=c.getTime();
            toDate=new Date(d2.getTime());
            System.out.println("toDate "+toDate);
          
  %>
  
     
  <table cellspacing="2" cellpadding="3" width="100%" >
      <tr class="tdH">
        <td colspan="2">
          <div align="center">
            <strong>List of Pending Vouchers</strong>
          </div>
        </td>
      </tr>
       
    </table>
     <br>
     <table id="mytable" align="center"  cellspacing="3" 
         cellpadding="2" border="1" width="100%">
          <tr class="tdH">
           <th>     <a href="javascript:sel();">Select </a> <br><a href="javascript:Unsel();">UnSelect </a></th>
           
             <th > Vr.Type  </th>
             <th> Vr.Date </th>
              <th> Vr.No </th>
            <!--   <th style="display:none"> SL.No </th>         -->   <!--Transaction Serial Number--><!-- @NK HIDE ON 01072019 -->
            
              <th > Vr.Sl.No </th> 
             
            <th>A/c Head Code</th>
            <th>CR/DR</th>
            <th >Sub Ledger Type</th>
            <th>Sub Ledger Code</th>
           <!-- <th>Cheque/DD No.</th>
            <th>Cheque/DD Date</th> -->
            <th width="80">Jrnl Amt</th>
               <th >Vr.Amt</th>
           
         
          </tr>
          <tbody id="grid_body" class="table">
          
        
          </tbody>
        </table>
         <table align="center"  cellspacing="3" cellpadding="2" border="1" width="100%" >
  
   
      <tr class="tdH">
      <td>
          <div align="center">
      <input type="button" id="cmdsubmit" name="Submit" value="Submit" onclick="btnsubmit()">
         <input type="button" id="cmdcancel" name="cancel" value="Cancel" onclick="btncancel()">
      </div>
      </td>
      </tr>
      
   
      
      </table>
      
      
        <div id="imgfld" style="position: absolute; top: 354px; visibility: hidden; left: 378px; width: 212px; height: 6px;"
			left=100 top=100><input type="image" name="img1" id="img1"
			src="../../../../../images/Loading.gif" height="200"></div>
   
      </form>
      
      
      
      <!-- /*@NK Included on 26062019 to create own alert box*/ -->
      
      <div id = "confirm">
         <div class = "message">Please select the same Vr.Type </div>
         <button class = "yes">OK</button>
      </div>
      <!-- /*@NK Included on 26062019 to create own alert box*/ -->
      
  </body>
</html>