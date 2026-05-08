<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">
<%@ page session="false"  contentType="text/html;charset=windows-1252"%>
<%@ page import="java.sql.*,java.util.*,Servlets.Security.classes.UserProfile"%>
<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=windows-1252"/>   
    <meta http-equiv="cache-control" content="no-cache">
    <title>Details of HR Sanction Proceedings Multiple Payee</title>
    <script type="text/javascript"
          src="<%=request.getContextPath()%>/org/Library/scripts/comJS.js"></script>
    <link href="../../../../../css/Sample3.css" rel="stylesheet"
          media="screen"/>
          <script type="text/javascript">
          function exit()
          {
          		self.close();
          }
          </script>
  </head>
  <body class="table"><form name="raised_List" method="POST">
  <%
		  Connection con=null;
		  ResultSet rs=null;
		  PreparedStatement ps=null;
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
		            ConnectionString = strdsn.trim() + "://" + strhostname.trim() + ":" + strportno.trim() + "/" +strsid.trim() ;    // Postgres DB  Connection
		            //ConnectionString = strdsn.trim() + "@" + strhostname.trim() + ":" + strportno.trim() + ":" +strsid.trim() ;
		
		            Class.forName(strDriver.trim());
		            con=DriverManager.getConnection(ConnectionString,strdbusername.trim(),strdbpassword.trim());
		  }
		  catch(Exception e)
		  {
		    	System.out.println("Exception in connection...."+e);
		  }
  		  int  cmbAcc_UnitCode=0,cmbOffice_code=0,yr=0,mon=0,vou_no=0,major=0,minor=0;

          try{
          	 cmbAcc_UnitCode=Integer.parseInt(request.getParameter("cmbAcc_UnitCode"));
          }catch(Exception e){System.out.println("Exception in getting req:"+e);}
          try{
            cmbOffice_code=Integer.parseInt(request.getParameter("cmbOffice_code"));
          }catch(Exception e){System.out.println("Exception in getting req:"+e);}
          try{
                 yr=Integer.parseInt(request.getParameter("cashbook_yr"));
          }catch(Exception e){System.out.println("Exception in getting req:"+e);}
          try{
                 mon=Integer.parseInt(request.getParameter("cashbook_mn"));
          }catch(Exception e){System.out.println("Exception in getting req:"+e);}
          try{
                 vou_no=Integer.parseInt(request.getParameter("voucher_no"));
          }catch(Exception e){System.out.println("Exception in getting req:"+e);}
          try{
              major=Integer.parseInt(request.getParameter("majorcode"));
       		}catch(Exception e){System.out.println("Exception in getting major code:"+e);}
      	 try{
           minor=Integer.parseInt(request.getParameter("minorcode"));
    	}catch(Exception e){System.out.println("Exception in getting minorcode:"+e);}
          
          
          System.out.println("cmbAcc_UnitCode===>"+cmbAcc_UnitCode+" cmbOffice_code====>"+cmbOffice_code+" yr====>"+yr+" mon====>"+mon+" vou_no===>"+vou_no +"major====>"+major+ "minor=======>"+minor);
  %>
      <table cellspacing="3" cellpadding="2"  width="100%">
        <tr class="tdH">
          <td colspan="2">
            <div align="center">
              <strong>Details of HR Sanction Proceedings Multiple Payee</strong>
            </div>
          </td>
        </tr>
      </table>
      <table id="mytable" align="center" cellspacing="3" cellpadding="2"
             border="1" width="100%">
                      
              <tr class="table">
                <th>Bill Sub Type</th>
                <th>Payee Type</th>
                <th>Payee Code</th>
                <th>HR Amount</th>
                <th>Particulars</th>
              </tr>
        
        <tbody id="tbody" class="table">
          <%
          		  ResultSet rs2=null,rs3=null;
		          PreparedStatement ps2=null,ps3=null;
		          try
		          {
		           	    System.out.println("inside try");
			 	        String sql="SELECT BILL_MAJOR_TYPE_CODE,BILL_MINOR_TYPE_CODE,BILL_SUB_TYPE_CODE,PAYEE_TYPE,PAYEE_CODE,HR_AMOUNT,REMARKS from("+
			 	        	" select ACCOUNTING_UNIT_ID,ACCOUNTING_FOR_OFFICE_ID,CASHBOOK_YEAR,CASHBOOK_MONTH,SANCTION_PROCEEDING_NO,"+
			 	        	" BILL_MAJOR_TYPE_CODE,BILL_MINOR_TYPE_CODE,SANCTION_AUTHORITY,BUDGET_PROVIDED,BUDGET_SOFAR_SPENT"+
			 	        	" from FAS_HR_SANC_PROC_MULTI_MST WHERE ACCOUNTING_UNIT_ID="+cmbAcc_UnitCode+" AND ACCOUNTING_FOR_OFFICE_ID="+cmbOffice_code+" AND CASHBOOK_YEAR="+yr+" AND CASHBOOK_MONTH="+mon+" AND SANCTION_PROCEEDING_NO="+vou_no+")a"+
			 	        	" left outer join"+
			 	        	" (SELECT ACCOUNTING_UNIT_ID,ACCOUNTING_FOR_OFFICE_ID,CASHBOOK_YEAR,CASHBOOK_MONTH,SANCTION_PROCEEDING_NO,BILL_SUB_TYPE_CODE,PAYEE_TYPE,PAYEE_CODE,HR_AMOUNT,REMARKS FROM FAS_HR_SANC_PROC_MULTI_TRN"+
			 	        	" WHERE ACCOUNTING_UNIT_ID="+cmbAcc_UnitCode+" AND ACCOUNTING_FOR_OFFICE_ID="+cmbOffice_code+" AND CASHBOOK_YEAR="+yr+" AND CASHBOOK_MONTH="+mon+" AND SANCTION_PROCEEDING_NO="+vou_no+")b"+
			 	        	" on a.ACCOUNTING_UNIT_ID=b.ACCOUNTING_UNIT_ID and a.ACCOUNTING_FOR_OFFICE_ID=b.ACCOUNTING_FOR_OFFICE_ID and a.CASHBOOK_YEAR=b.CASHBOOK_YEAR and a.CASHBOOK_MONTH=b.CASHBOOK_MONTH and a.SANCTION_PROCEEDING_NO=b.SANCTION_PROCEEDING_NO";
			 	        System.out.println("SQL ::: "+sql);
			            ps2=con.prepareStatement(sql);
			            rs2=ps2.executeQuery();
			            while(rs2.next())
			            {
			                System.out.println("while");
			                out.println("<tr>");   
			                	if(rs2.getInt("BILL_SUB_TYPE_CODE")==0)
			                	{
			                		out.println("<td value="+rs2.getInt("BILL_SUB_TYPE_CODE")+">"+rs2.getInt("BILL_SUB_TYPE_CODE")+"</td>");
					                out.println("<td align='left'>"+rs2.getString("PAYEE_TYPE")+"</td>");
					                out.println("<td align='left'>"+rs2.getInt("PAYEE_CODE")+"</td>");
					                out.println("<td align='left'>"+rs2.getString("HR_AMOUNT")+"</td>");
					                out.println("<td align='left'>"+rs2.getString("REMARKS")+"</td></tr>");
			                	}
			                	else
			                	{
			                		ps3=con.prepareStatement("select BILL_SUB_TYPE_DESC FROM FAS_BILL_SUB_TYPES where BILL_SUB_TYPE_CODE=? and BILL_MAJOR_TYPE_CODE =? and BILL_MINOR_TYPE_CODE = ?");
					                ps3.setInt(1,rs2.getInt("BILL_SUB_TYPE_CODE"));
					                ps3.setInt(2,rs2.getInt("BILL_MAJOR_TYPE_CODE"));
					                ps3.setInt(3,rs2.getInt("BILL_MINOR_TYPE_CODE"));
					                rs3=ps3.executeQuery();System.out.println(rs2.getInt("BILL_SUB_TYPE_CODE"));
					                if(rs3.next())
					                {
			                		out.println("<td value="+rs2.getInt("BILL_SUB_TYPE_CODE")+">"+rs3.getString("BILL_SUB_TYPE_DESC")+"</td>");			                	
					                out.println("<td align='left'>"+rs2.getString("PAYEE_TYPE")+"</td>");
					                out.println("<td align='left'>"+rs2.getInt("PAYEE_CODE")+"</td>");			                
					                out.println("<td align='left'>"+rs2.getString("HR_AMOUNT")+"</td>");
					                out.println("<td align='left'>"+rs2.getString("REMARKS")+"</td></tr>");
			                		}
				               }
			            }
		          }
		          catch(Exception e)
		          {
		            	System.out.println("Exception in grid.."+e.getMessage());
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
                     onclick="exit()"></input>
            </div>
          </td>
        </tr>
      </table>
    </form></body>
</html>