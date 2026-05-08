<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">
<%@ page session="false" contentType="text/html;charset=windows-1252"%>
<%@ page import="java.sql.*,java.util.*,Servlets.Security.classes.UserProfile"%>
<%@ page import="Servlets.FAS.FAS1.CommonControls.servlets.Com_CashBook1"%>
<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
    <title>List for Bill And Account Head Details</title>
    <script type="text/javascript" src="<%=request.getContextPath()%>/org/Library/scripts/comJS.js"></script>
    <script type="text/javascript" src="../scripts/Bill_Account_Heads.js"></script>
    <link href="../../../../../css/Sample3.css" rel="stylesheet" media="screen"/>
    <script type="text/javascript" language="javascript">
	  function EditRender(rid,macode,mincode,sucod)
		   {
		 // alert(rid+" "+macode+" "+mincode);
		   	var major="",minor="",acchead="",status="",rem="",sub="",headDes="";
		   	r=document.getElementById(rid);
		   	rcells=r.cells;
		   	//alert(rcells.item(2).firstChild.nodeValue);	
				   	
		   	major=rcells.item(1).firstChild.nodeValue;
		   	minor=rcells.item(2).firstChild.nodeValue;
		   	sub=rcells.item(3).firstChild.nodeValue;
            acchead=rcells.item(4).firstChild.nodeValue;
            headDes=rcells.item(5).firstChild.nodeValue;
            status=rcells.item(6).firstChild.nodeValue;
            rem=rcells.item(7).firstChild.nodeValue;
		   	close();
		   	opener.goBack(major,minor,sub,acchead,headDes,status,rem,macode,mincode,sucod);        
		   }
		   
	  </script>
  </head>
  <body class="table">         
  <form>
 <%
  
        Connection con=null;
        ResultSet rs=null;
        PreparedStatement ps=null;
        PreparedStatement ps1=null;
        PreparedStatement ps2=null;
        Connection connection=null;
        ResultSet results=null;
        ResultSet rs1=null; 
        ResultSet rs2=null; 
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

            //ConnectionString = strdsn.trim() + "@" + strhostname.trim() + ":" + strportno.trim() + ":" +strsid.trim() ;
				ConnectionString = strdsn.trim() + "://" + strhostname.trim() + ":" + strportno.trim() + "/" +strsid.trim() ;    // Postgres DB  Connection
             Class.forName(strDriver.trim());
             con=DriverManager.getConnection(ConnectionString,strdbusername.trim(),strdbpassword.trim());
  }
  catch(Exception e)
  {
    System.out.println("Exception in connection...."+e);
  }
       System.out.println("...............list all LISTjsp started.................");
      HttpSession session=request.getSession(false);
      UserProfile empProfile=(UserProfile)session.getAttribute("UserProfile");
     
           String finyr = "",majorcc="",minorcc="";
           int majorType=0,minorType=0;   
           finyr=request.getParameter("finyr");
           System.out.println(finyr);
       	majorcc=request.getParameter("majorType");
        minorcc=request.getParameter("minorType");
        	   String addsql="";
        	if((!majorcc.equalsIgnoreCase(""))&&(!minorcc.equalsIgnoreCase(""))){
        		  majorType=Integer.parseInt(majorcc);  
                  System.out.println(majorType);
                  minorType=Integer.parseInt(minorcc); 
                  System.out.println(minorType);
                
        		addsql="  and a2.bill_major_type_code= "+majorType+
				    "  AND a2.BILL_MINOR_TYPE_CODE= "+minorType;
        	}else if((!majorcc.equalsIgnoreCase(""))&&(minorcc.equalsIgnoreCase(""))){
        		  majorType=Integer.parseInt(majorcc);  
                  System.out.println(majorType);
                 
        		addsql="  and a2.bill_major_type_code= "+majorType;
        	}else{
        		addsql="";	
        	}
         
          %>
  
 
      <table cellspacing="2" cellpadding="3" border="1" width="100%">
        <tr class="tdH">
          <td>
            <div align="center">
              TWAD BOARD-INTEGRATED ONLINE SYSTEM -FINANCIAL ACCOUNTING SYSTEM
            </div></td>
        </tr>
        <tr class="table">
          <td>
            <div align="center">
              List of Bill and Account Heads Details
            </div></td>
        </tr>
       
      </table>
       <table cellspacing="2" cellpadding="3" border="1" width="100%">
           <tr class="tdH">
               <th>Select </th>
               <th>Major Type Code </th>
               <th>Minor Type</th>
               <th>Sub Type</th>
               <th>Account Head Code</th>
                <th>A/C Head Desc</th>
               <th>In Use</th>
               <th>Remarks</th>
            </tr>
        <tbody id="tblList" class="table">
          <%
		try
                {
			
			String sql1="SELECT BILL_SUB_TYPE_CODE,BILL_MAJOR_TYPE_CODE, "+
				  " BILL_SUB_TYPE_DESC,majordesc, "+
				  "  BILL_MINOR_TYPE_CODE, "+
				  " descr, "+
				  " ACCOUNT_HEAD_CODE, "+
				  "  acheadDesc, "+
				  "  STATUS, "+
				  "  to_date(USED_UPTO_DATE,'dd-mm-yy') AS USED_UPTO_DATE, "+
				  "  REMARKS "+
				  " FROM "+
				  "  (SELECT a2.BILL_MAJOR_TYPE_CODE, "+
				  "    a2.BILL_MINOR_TYPE_CODE, "+
				  "    a2.BILL_SUB_TYPE_CODE, "+
				  "    a2.ACCOUNT_HEAD_CODE, "+
				  "   a2.status, "+
				  "  (select BILL_MAJOR_TYPE_DESC from FAS_BILL_MAJOR_TYPES where BILL_MAJOR_TYPE_CODE=a2.bill_major_type_code  )as majordesc,"+
				  "    (select bill_sub_type_desc "+
				  "  from fas_bill_sub_types f1 "+
				  "  where f1.bill_major_type_code=a2.bill_major_type_code "+
				  "  and f1.bill_minor_type_code=a2.bill_minor_type_code "+
				  "  and f1.BILL_SUB_TYPE_CODE=a2.BILL_SUB_TYPE_CODE "+
				  "  ) as BILL_SUB_TYPE_DESC, "+
				  "    (SELECT aa.account_head_desc "+
				  "    FROM com_mst_account_heads aa "+
				  "    WHERE aa.usage_status   ='Y' "+
				  "   AND aa.ACCOUNT_HEAD_CODE=a2.ACCOUNT_HEAD_CODE "+
				  "  )AS acheadDesc, "+
				  "  (SELECT BILL_MINOR_TYPE_DESC "+
				  "  from fas_bill_minor_types_mst "+
				  "  where bill_major_type_code=a2.bill_major_type_code "+
				  "  AND BILL_MINOR_TYPE_CODE  =a2.BILL_MINOR_TYPE_CODE "+
				    "  )                                    AS descr, "+  
				    "   to_date(a2.USED_UPTO_DATE,'dd-mm-yy') AS USED_UPTO_DATE, "+
				    "  a2.REMARKS "+
				    "  from fas_bill_account_heads a2 "+
				    "  where a2.financial_year    ='"+finyr+"'" +addsql +
				   // "  and a2.bill_major_type_code=1 "+
				   // "  AND a2.BILL_MINOR_TYPE_CODE=4 "+
				    "   )";
			//String sql="SELECT a.BILL_SUB_TYPE_CODE,BILL_SUB_TYPE_DESC,BILL_MINOR_TYPE_CODE,descr,ACCOUNT_HEAD_CODE,acheadDesc,STATUS,to_date(USED_UPTO_DATE,'dd-mm-yy') AS USED_UPTO_DATE,REMARKS from (SELECT a2.BILL_MAJOR_TYPE_CODE,a2.BILL_MINOR_TYPE_CODE,a2.BILL_SUB_TYPE_CODE,a2.ACCOUNT_HEAD_CODE,a2.STATUS,(select aa.account_head_desc from com_mst_account_heads aa  where aa.usage_status='Y'  and aa.ACCOUNT_HEAD_CODE=a2.ACCOUNT_HEAD_CODE)as acheadDesc,(SELECT BILL_MINOR_TYPE_DESC FROM FAS_BILL_MINOR_TYPES_MST WHERE BILL_MAJOR_TYPE_CODE=? AND BILL_MINOR_TYPE_CODE=?)AS descr,to_date(a2.USED_UPTO_DATE,'dd-mm-yy') AS USED_UPTO_DATE,a2.REMARKS	FROM FAS_BILL_ACCOUNT_HEADS	a2 WHERE a2.FINANCIAL_YEAR=? AND a2.BILL_MAJOR_TYPE_CODE=? AND a2.BILL_MINOR_TYPE_CODE=?)a left outer join (SELECT BILL_SUB_TYPE_CODE,BILL_SUB_TYPE_DESC FROM FAS_BILL_SUB_TYPES WHERE BILL_MAJOR_TYPE_CODE=? AND BILL_MINOR_TYPE_CODE=?)b on a.BILL_SUB_TYPE_CODE=b.BILL_SUB_TYPE_CODE";
			
	                  ps=con.prepareStatement(sql1);
                     // ps.setInt(1,majorType);
                    //  ps.setInt(2,minorType);
                     // ps.setString(3,finyr);
                     // ps.setInt(4,majorType);
                     // ps.setInt(5,minorType);
                     // ps.setInt(6,majorType);
                     // ps.setInt(7,minorType);
                      results=ps.executeQuery();
                      System.out.println("sql1sql1---   "+sql1);
                      System.out.println(results);
	                  int cnt=0;
	                  while(results.next())
	                  {

									String majdes=results.getString("majordesc");
                                      String MajDesc=results.getString("descr");
                                      String MinDesc=results.getString("BILL_SUB_TYPE_DESC");
                                      String headDesc=results.getString("acheadDesc");
	                                  cnt++;
	                                  out.println("<tr id=" + cnt + ">");
	                                 // <td align='center'><a href= javascript:Show('"+rs2.getInt("accounting_unit_id")+"','"+rs2.getInt("asset_major_class_code")+"','"+finyr+"')>Office Wise BreakUp</a></td>
	                                 // out.println("<td><a href='javascript:EditRender("+cnt+ ","+majcc+","+MajCode+","+MinCode+")'>EDIT</a></td>");
	                                  out.println("<td><a href=javascript:EditRender('"+cnt+"','"+results.getInt("BILL_MAJOR_TYPE_CODE")+"','"+results.getInt("BILL_MINOR_TYPE_CODE")+"','"+results.getInt("BILL_SUB_TYPE_CODE")+"')>EDIT</a></td>");
                                      out.println("<td >"+majdes+"</td>");
	                                  out.println("<td >"+MajDesc+"</td>");  
	                                  out.println("<td >"+MinDesc+"</td>"); 
	                                  out.println("<td>"+results.getInt("ACCOUNT_HEAD_CODE")+"</td>");
	                                  out.println("<td>"+results.getString("acheadDesc")+"</td>");
	                                  out.println("<td>"+results.getString("STATUS")+"</td>");
	                                  out.println("<td>"+results.getString("REMARKS")+"</td>");
	                                  out.println("</tr>");
                        }
	                  if(cnt==0)
	                     {
	                        out.println("<tr><td colspan='8' align='center'>No data found</td></tr>");
	                     } 
         		}
                catch(Exception e)
            	{
	                System.out.println("Exception in Select:"+e);
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
    </form>
  </body>
</html>
