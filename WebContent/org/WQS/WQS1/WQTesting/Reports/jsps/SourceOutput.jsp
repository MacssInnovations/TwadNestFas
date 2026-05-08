<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">
<%@ page contentType="text/html;charset=windows-1252"%>
<%@ page import="java.sql.*,java.util.*"%>
<%@ page import="java.util.*"%>
<%@ page import="java.util.Date"%>
<%@ page import="java.io.IOException"%>
<%@ page import="java.lang.*"%>
<%@ page import="java.text.SimpleDateFormat"%>
<%@ page import="javax.servlet.*"%>

<%@ page import="javax.servlet.http.*"%>

<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=windows-1252"/>
    <title>Source Wise Query</title>
    <script type="text/javascript" src="<%=request.getContextPath()%>/org/Library/scripts/comJS.js"></script>
    <link href="../../../../../../css/CalendarControl.css" rel="stylesheet" media="screen"/> 
    <link href="../../../../../../css/Sample3.css" rel='stylesheet' media='screen'/>
    
    <!--<script type="text/javascript" src="../../scripts/CalendarControl.js"></script>-->
            
    <script language="javascript" type="text/javascript">
                function closeWindow()
                {                
                    window.open('','_parent','');                
                    window.close(); 
                    window.opener.focus();
                }
    </script>
  </head>
  <body class="table">
  <!--<marquee>
  Page Under Progress
  </marquee>-->
  <%
  Connection connection=null;
  Statement statement=null;
  ResultSet results=null;
  ResultSet results1=null;
   ResultSet result1=null;
    ResultSet result2=null;
    ResultSet result3=null;
    PreparedStatement pstmt=null;
    ResultSetMetaData rsmd=null;
        PreparedStatement pstmt1=null;
        PreparedStatement pstmt2=null;
        PreparedStatement pstmt3=null;
        response.setContentType("text/html");
        //PrintWriter =new PrintWriter();
        //PrintWriter pw=response.getWriter();
        
        
            java.sql.Date dateOfAttachment=null;
        java.sql.Date dateto=null;
        
        int phy=0;
        int che=0;
        int bac=0;
        String manual="";
        int resvalue=0;
        String restext="";
        String remark="";
        
  try
          {
               ResourceBundle rs=ResourceBundle.getBundle("Servlets.Security.servlets.Config");
                 String ConnectionString="";
                
                 String strDriver=rs.getString("Config.DATA_BASE_DRIVER");
                 String strdsn=rs.getString("Config.DSN");
                 String strhostname=rs.getString("Config.HOST_NAME");
                 String strportno=rs.getString("Config.PORT_NUMBER");
                 String strsid=rs.getString("Config.SID");
                 String strdbusername=rs.getString("Config.USER_NAME");
                 String strdbpassword=rs.getString("Config.PASSWORD");
                   
                 //ConnectionString = strdsn.trim() + "@" + strhostname.trim() + ":" + strportno.trim() + ":" +strsid.trim() ;
					ConnectionString = strdsn.trim() + "://" + strhostname.trim() + ":" + strportno.trim() + "/" +strsid.trim() ;    // Postgres DB  Connection
                  Class.forName(strDriver.trim());
                  connection=DriverManager.getConnection(ConnectionString,strdbusername.trim(),strdbpassword.trim());

   
      statement=connection.createStatement();
      System.out.println("calling servlet");
            //////////Varibale Declaration////////
                String fromdate=request.getParameter("txtfromdate");
                String todate=request.getParameter("txttodate");
                int lab=Integer.parseInt(request.getParameter("txtLabCode"));
                int district=Integer.parseInt(request.getParameter("txtDistrictCode"));
                String sampletype=request.getParameter("txtSampleType");
                int schemetype=Integer.parseInt(request.getParameter("txtSchemeType"));
                int sourcetype=Integer.parseInt(request.getParameter("txtSourceType"));
                String progtype=request.getParameter("txtProgType");
                int subprogtype=Integer.parseInt(request.getParameter("txtSubProgType"));
                
                
                
                //Date Conversion for Date
                 //java.sql.Date dateOfAttachment=null;
                 System.out.println("before converting date");
                 String dateString = fromdate;
                 SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
                 java.util.Date d;                    
                 d = dateFormat.parse(fromdate.trim());
                 System.out.println("util date is:"+d);
                 dateFormat.applyPattern("yyyy-MM-dd");
                 dateString = dateFormat.format(d);
                 dateOfAttachment = java.sql.Date.valueOf(dateString);
                 
                //java.sql.Date dateto=null;
                System.out.println("before converting date");
                String dateString1 = todate;
                SimpleDateFormat dateFormat1 = new SimpleDateFormat("dd/MM/yyyy");
                java.util.Date d1;                    
                d1 = dateFormat1.parse(todate.trim());
                dateFormat1.applyPattern("yyyy-MM-dd");
                dateString1 = dateFormat1.format(d1);
                dateto = java.sql.Date.valueOf(dateString1);
               
               System.out.println("fromdate"+dateOfAttachment);
                System.out.println("todate"+dateto);
                System.out.println("Lab code is"+lab);
                System.out.println("District Code"+district);
                System.out.println("Sample Type is"+sampletype);
                System.out.println("Scheme Type is"+schemetype);
                System.out.println("Source Type is"+sourcetype);
               System.out.println("Programme Type is"+progtype);
               System.out.println("Sub Programme Type is"+subprogtype);
               
     pstmt1 = connection.prepareStatement(
     "select LAB_CODE,INVOICE_NUMBER,SAMPLE_NUMBER from WQS_SAMPLE_DETAILS where LAB_CODE=? and SAMPLE_TYPE=? and SOURCE_TYPE=? and SCHEME_TYPE=? and PROG_MAIN_TYPE=? and PROG_SUB_TYPE=? and SOURCE_LOC_DISTRICT_CODE=? and DATE_OF_RECEIPT between ? and ?"); 
             pstmt1.setInt(1,lab);
             pstmt1.setString(2,sampletype);
             pstmt1.setInt(3,sourcetype);
             pstmt1.setInt(4,schemetype);
             pstmt1.setString(5,progtype);
             pstmt1.setInt(6,subprogtype);
             pstmt1.setInt(7,district);
             pstmt1.setDate(8,dateOfAttachment); 
             pstmt1.setDate(9,dateto);
             result1 = pstmt1.executeQuery();       
             while (result1.next()) {
                int labcode = result1.getInt("LAB_CODE");    
                int invoice=result1.getInt("INVOICE_NUMBER");
                int sampleno=result1.getInt("SAMPLE_NUMBER");
                System.out.println("lab code = " + labcode +"invoice number =  "+invoice+ "sampleno = "+sampleno);
     pstmt2=connection.prepareStatement(
    "select MANUAL_CALCULATED,RESULT_IN_VALUE,RESULT_IN_TEXT,REMARK from WQS_SAMPLE_RESULTS where LAB_CODE=? and INVOICE_NUMBER=? and SAMPLE_NUMBER=?");
             pstmt2.setInt(1,labcode);
           pstmt2.setInt(2,invoice);
         pstmt2.setInt(3,sampleno);
       result2=pstmt2.executeQuery();
                        
                       /* rsmd=result2.getMetaData();
                        int length=rsmd.getColumnCount();
                        System.out.println("out.println(length);*/
                        
       while(result2.next()) {
        manual=result2.getString("MANUAL_CALCULATED");
        resvalue=result2.getInt("RESULT_IN_VALUE");
       restext=result2.getString("RESULT_IN_TEXT");
        remark=result2.getString("REMARK");
        System.out.println("Manual Calculated = "+manual);
        System.out.println("Result in Value = "+resvalue);
        System.out.println("Result in Text = "+restext);
        System.out.println("Remark = "+remark);                     
                     
                  
                          
                    //Saravanan");
                    //result1.close();
                    //pstmt1.close();                   
                 
              }
              
       
    }
    }
    catch(SQLException e)
    {
    
    }
  
 catch (Exception ex) {
            String connectMsg = 
                "Could not create the report " + ex.getMessage() + " " + 
                ex.getLocalizedMessage();
            System.out.println(connectMsg);
            System.out.println(ex.getMessage());
        }
  %>
 
      <table cellspacing="3" cellpadding="2" border="1" width="75%"
             align="center" >
        <tr>
           <td class="tdH">
      <center>
      <b>Source Wise Query Details</b>
      </center>
      </td>
        </tr>
        
      </table>
      <table id="Existing" cellspacing="2" cellpadding="3" border="1" width="75%"
             align="center">
        <tr class="tdH">
          <th>
            Manual Calculated
          </th>
          <th>
            Result in Value
          </th>
          <th>
            Result in Text
          </th>
          <th>
         Remarks
          </th>
         
          </tr>
      
         <tr>
         <td class="tdH">
         <center>
         <%
         out.println(manual);%>
         </center>
         </td>
         <td class="tdH">
         <center>
         <%
         out.println(resvalue);
         %>
         </center>
         </td>
         <td class="tdH">
         <center>
         <%
         out.println(restext);
         %>
         </center>
         </td>
         <td class="tdH">
         <center>
         <%
         out.println(remark);
         %>
         </center>
         </td>
         </tr>
       </table>
        
<!--        <table align="center" border="1" cellspacing="2" cellpadding="1" width="100%"> 
       <tr>
       <td class="tdH">
      <center>
      <b>Lab Wise Test Details</b>
      </center>
      </td>
       
        </tr>
        
        
        
            <tr>
            <td class="tdH">
            <b>No of Physical Tests: </b>
            </td>
            <td class="tdH">
            <%
            out.println(phy);
            //response.write(bac);
            %>
            </td>
            </tr>
            
            <tr>
            <td class="tdH">
            <b>No of Chemical Tests: </b>
            </td>
            <td class="tdH">
            <%
            out.println(che);
            //response.write(bac);
            %>
            
            </td>
            </tr>
            
            <tr>
            <td class="tdH">
            <b>No of Bacteriological Tests: </b>
            </td>
            <td class="tdH">
            <%
            out.println(bac);
            //response.write(bac);
            %>
            </td>
            </tr>
            
       </table>-->
        </body>
  </html>
