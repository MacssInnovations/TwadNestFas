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
    <title>Lab Wise Query Details</title>
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
            
                String fromdate=request.getParameter("txtfromdate");
                String todate=request.getParameter("txttodate");
                int lab=Integer.parseInt(request.getParameter("txtLabCode"));
                
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
               
     pstmt1 = connection.prepareStatement(
     "select LAB_CODE,INVOICE_NUMBER,SAMPLE_NUMBER from WQS_SAMPLE_DETAILS where LAB_CODE=? and DATE_OF_RECEIPT between ? and ?"); 
             pstmt1.setInt(1,lab);
             pstmt1.setDate(2,dateOfAttachment); 
             pstmt1.setDate(3,dateto);
             result1 = pstmt1.executeQuery();       
             while (result1.next()) {
                int labcode = result1.getInt("LAB_CODE");    
                int invoice=result1.getInt("INVOICE_NUMBER");
                int sampleno=result1.getInt("SAMPLE_NUMBER");
                System.out.println("lab code = " + labcode +"invoice number =  "+invoice+ "sampleno = "+sampleno);
         
            
            pstmt2=connection.prepareStatement(
     "select STD_NON_STD,RESULT_IN_VALUE,REMARK from WQS_SAMPLE_RESULTS where LAB_CODE=? and INVOICE_NUMBER=? and SAMPLE_NUMBER=?");
             pstmt2.setInt(1,labcode);
             pstmt2.setInt(2,invoice);
             pstmt2.setInt(3,sampleno);
             result2=pstmt2.executeQuery();
                        
                       /* rsmd=result2.getMetaData();
                        int length=rsmd.getColumnCount();
                        System.out.println("out.println(length);*/
                        
             while(result2.next()) {
                int stdcode=result2.getInt("STD_NON_STD");
                int result=result2.getInt("RESULT_IN_VALUE");
                String remark=result2.getString("REMARK");
                System.out.println("Code to test = "+stdcode);
                System.out.println("Result in value = "+result);
                System.out.println("Remark = "+remark);
      pstmt3=connection.prepareStatement(
      "select TEST_TYPE,ELEMENT_SYMBOL from WQS_MST_STD_TEST where STD_CODE=? union select TEST_TYPE,ELEMENT_SYMBOL from WQS_MST_NONSTD_TEST where NON_STD_CODE=?");
             System.out.println(pstmt1);
             pstmt3.setInt(1,stdcode);
             pstmt3.setInt(2,stdcode);
             result3=pstmt3.executeQuery();
             while(result3.next()) {
                String testtype1=result3.getString("TEST_TYPE");
                String testtype2=result3.getString("TEST_TYPE");
                //String element=result3.getString("ELEMENT_SYMBOL");
                System.out.println("Test Type is"+testtype1);
                //System.out.println("Element Symbol is"+element);
                if(testtype1.equalsIgnoreCase("phy")) {
                            phy++;
                        }
                  else if(testtype1.equalsIgnoreCase("che")) {
                            che++;
                        }
                  else if(testtype1.equalsIgnoreCase("bac")) {
                            bac++;
                        }
                
     }
            }
            }
             System.out.println("Physical count is "+phy);
       System.out.println("Chemical count is "+che);
       System.out.println("Bacterilogical count is "+bac);
            
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
 
      
                     
       
        
   <!--         <table align="center" border="1" cellspacing="2" cellpadding="1" width="100%"> 
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
       
       <table cellspacing="3" cellpadding="2" border="1" width="75%"
             align="center" >
        <tr>
           <td class="tdH">
      <center>
      <b>Lab Wise Test Details</b>
      </center>
      </td>
        </tr>
        
      </table>
      <table id="Existing" cellspacing="2" cellpadding="3" border="1" width="75%"
             align="center">
        <tr class="tdH">
          <th>
            No of Physical Tests
          </th>
          <th>
            No of Chemical Tests
          </th>
          <th>
            No of Bacteriological Tests
          </th>
          
         
          </tr>
      
         <tr>
         <td class="tdH">
         <center>
         <%
         out.println(phy);
         %>
         </center>
         </td>
         
         <td class="tdH">
         <center>
         <%
         out.println(che);
         %>
         </center>
         </td>
         
         <td class="tdH">
         <center>
         <%
         out.println(bac);
         %>
         </center>
         </td>
         
         </tr>
       </table>
      
        </body>
  </html>
    
     
       
       
       
       
        
       
        