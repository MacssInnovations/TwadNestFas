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
     <link href="../../../../../css/Sample3.css" rel="stylesheet" media="screen"/>
      
    <script type="text/javascript" language="javascript">
    function btncancel()
    {
     self.close();
    }
    
    function btnsubmit()
    {
        
        var sele=document.getElementsByName("choice").length;
        var val=0;
        var vouNO="",vouDATE,vouTYPE;
        
        if(sele>0)
        {
            if(sele==1)
            {
                document.frmPanding_Bills.choice.checked=true;
                //alert("here");
                if(document.frmPanding_Bills.choice.checked==true)
                {
                 vouNO=document.frmPanding_Bills.choice.value;
                 r=document.getElementById(vouNO);
                 rcells=r.cells;
                  
                 vouDATE=rcells.item(2).firstChild.nodeValue;
                 vouTYPE=rcells.item(3).firstChild.value;
                }
            }
            else
            {      //alert("else"+sele);
                    for(i=0;i<sele;i++)
                    { 
                        //alert(document.frmPanding_Bills.choice[i].checked)
                        if(document.frmPanding_Bills.choice[i].checked==true)
                        {
                             vouNO=document.frmPanding_Bills.choice[i].value;
                             r=document.getElementById(vouNO);
                             rcells=r.cells;
                             vouDATE=rcells.item(2).firstChild.nodeValue;
                             //alert(rcells.item(3).firstChild.value)
                             vouTYPE=rcells.item(3).firstChild.value;
                             break;
                        }
                    }
             }
        }
            
        Minimize();
        opener.doParentPendingbills(vouNO,vouDATE,vouTYPE);
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
      
      </head>
       <%
response.setHeader("Cache-Control","no-cache"); //HTTP 1.1
response.setHeader("Pragma","no-cache"); //HTTP 1.0
response.setDateHeader ("Expires", 0); //prevent caching at the proxy server
%>
  <body class="table"  >
  <form name="frmPanding_Bills" method="POST">
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
   int  cmbAcc_UnitCode=0,cmbOffice_code=0,yr=0,mon=0,type_MasSL=0,code_MasSL=0;
   Date txtCrea_date=null;
    Calendar c;
    System.out.println("haui");
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
     
     <table id="mytable" align="center"  cellspacing="3" 
         cellpadding="2" border="1" width="100%">
          <tr class="tdH">
             <th>
              Select
            </th>
            <th>
              Voucher Number
            </th>
            <th>
              Voucher Date
            </th>
             <th>
              Voucher Type
            </th>
             <th>
            Total Amount Pending
            </th>
            <th>
            Remarks
            </th>
           
         
          </tr>
          <tbody id="tbody" class="table">
          
         <%
          
           try
           {
           String sql_Jrnal="select Jmas.CB_REF_TYPE, Jmas.CB_REF_NO, Jmas.CB_REF_DATE,Jmas.ACCOUNTING_UNIT_ID,"+
           "Jmas.ACCOUNTING_FOR_OFFICE_ID,Jmas.VOUCHER_NO as vou_no,Jtrs.SL_NO,"+
           "to_char(Jmas.VOUCHER_DATE,'DD/MM/YYYY') as vou_date,"+
           "Jmas.JOURNAL_TYPE_CODE as Jtype,decode(Jmas.JOURNAL_TYPE_CODE,8,'Contractors',9,'Employees',10,'Others',11,'Projects',12,'Supplier',13,'Firms','No Match') as Jtype_desc"+
           " ,Jmas.CASHBOOK_YEAR,Jmas.CASHBOOK_MONTH,Jtrs.AMOUNT as trans_amt,JMas.SUB_LEDGER_CODE,Jtrs.SUB_LEDGER_CODE,"+
           "Jmas.DEPRECIATION_RATE,"+
           "Jmas.TOTAL_TRN_RECORDS,Jmas.REMARKS as rmks from FAS_JOURNAL_MASTER Jmas,FAS_JOURNAL_TRANSACTION Jtrs "+
           "where Jmas.ACCOUNTING_UNIT_ID=? and  Jmas.ACCOUNTING_FOR_OFFICE_ID=? and Jmas.CASHBOOK_YEAR<=?  "+
           "and Jmas.JOURNAL_TYPE_CODE=? and Jmas.SUB_LEDGER_CODE=? and Jmas.JOURNAL_STATUS='L' and JMas.CREATED_BY_MODULE='LJV' "+
           "and Jmas.ACCOUNTING_UNIT_ID=Jtrs.ACCOUNTING_UNIT_ID and Jmas.ACCOUNTING_FOR_OFFICE_ID=Jtrs.ACCOUNTING_FOR_OFFICE_ID "+
           "and Jmas.CASHBOOK_YEAR=Jtrs.CASHBOOK_YEAR  and Jmas.CASHBOOK_MONTH=Jtrs.CASHBOOK_MONTH and Jmas.VOUCHER_NO=Jtrs.VOUCHER_NO "+
           " and Jmas.SUB_LEDGER_CODE=Jtrs.SUB_LEDGER_CODE";
           //and Jmas.JOURNAL_TYPE_CODE=Jtrs.SUB_LEDGER_TYPE_CODE  ******* Not equal bcoz both r from different table(value r differ for contractors)
           //"and JMas.CB_REF_TYPE='J' ";
           //if(type_MasSL==8)          
             //   sql_Jrnal=sql_Jrnal+" and Jtrs.ACCOUNT_HEAD_CODE=550102";
           //else if(type_MasSL==12)
                
            //System.out.println("hai"+" "+sql_Jrnal);  
            int y=0;
            ps2=con.prepareStatement(sql_Jrnal);
            ps2.setInt(1,cmbAcc_UnitCode);
            ps2.setInt(2,cmbOffice_code);
            ps2.setInt(3,yr);
            ps2.setInt(4,type_MasSL);
            ps2.setInt(5,code_MasSL);
            //ps2.set
            rs2=ps2.executeQuery();
             
            String print_stmt="";
            double partamt_total=0;
            int count=1,cnt=0;
            while(rs2.next())
            {
                System.out.println("vouno..."+rs2.getInt("vou_no")+"..count.."+count++);
                //out.println("<tr id="+rs2.getInt("vou_no")+">");   
                print_stmt="<tr id="+rs2.getInt("vou_no")+">";
               System.out.println("value of y"+y);
                if(y==0)
                {
                System.out.println("y="+y);
                //out.println("<td><input type='radio' id='choice' name='choice' checked value='"+rs2.getInt("vou_no")+"'/></td>");
                print_stmt=print_stmt+"<td><input type='radio' id='choice' name='choice' checked value='"+rs2.getInt("vou_no")+"'/></td>";
                y=1;
                }
                else
                print_stmt=print_stmt+"<td><input type='radio' id='choice' name='choice' value='"+rs2.getInt("vou_no")+"'/></td>" ;  //out.println("<td><input type='radio' id='choice' name='choice' value='"+rs2.getInt("vou_no")+"'/></td>");
                
                print_stmt=print_stmt+"<td align='left'>"+rs2.getInt("vou_no")+"</td>"; // out.println("<td align='left'>"+rs2.getInt("vou_no")+"</td>");
                             
                 print_stmt=print_stmt+"<td align='left'>"+rs2.getString("vou_date")+"</td>";           //out.println("<td align='left'>"+rs2.getString("vou_date")+"</td>");
                 print_stmt=print_stmt+"<td align='left'><input type='hidden' value="+rs2.getInt("Jtype")+" />"+rs2.getString("Jtype_desc")+"</td>"; 
             if(rs2.getInt("CB_REF_NO")==0)
              {
                //out.println("<td align='left'>"+rs2.getString("trans_amt").trim()+"</td>");
                System.out.println("CB_REF_NO..=0.."+rs2.getString("trans_amt"));
                print_stmt=print_stmt+"<td align='left'>"+rs2.getString("trans_amt").trim()+"</td>";
              }
             else if(rs2.getInt("CB_REF_NO")!=0)
              {
                 String sql_Pay="select sum(PART_AMOUNT) as tot_part_amt from FAS_PAYMENT_MASTER where PART_PAYMENT='Y' "+// " and ACCOUNTING_UNIT_ID=? and ACCOUNTING_FOR_OFFICE_ID=?"+
                 " and  PAYABLE_VOUCHER_NO=? and PAYABLE_VOUCHER_DATE=to_date(?,'DD/MM/YYYY') and JOURNAL_TYPE_CODE=? group by PAYABLE_VOUCHER_NO";
                 ps3=con.prepareStatement(sql_Pay);
                 //ps3.setInt(1,rs2.getInt("ACCOUNTING_UNIT_ID"));
                 //ps3.setInt(2,rs2.getInt("ACCOUNTING_FOR_OFFICE_ID"));
                
                 //ps3.setInt(5,txtCash_Month_hid);
                 System.out.println("here else if(rs2.getInt(!=0) ");
                 System.out.println(rs2.getInt("vou_no"));
                 System.out.println(rs2.getString("vou_date"));
                 System.out.println(rs2.getInt("Jtype"));
                 ps3.setInt(1,rs2.getInt("vou_no"));
                 ps3.setString(2,rs2.getString("vou_date"));
                 ps3.setInt(3,rs2.getInt("Jtype"));
                 rs3=ps3.executeQuery();
                 if(rs3.next())
                 {
                    System.out.println("CB_REF_NO.!=0..trans_amt"+rs2.getString("trans_amt")+".tot_part_amt..."+rs3.getDouble("tot_part_amt"));
                    if(rs2.getDouble("trans_amt")!=rs3.getDouble("tot_part_amt"))
                    {
                    System.out.println("QQ .."+Double.toString((rs2.getDouble("trans_amt")-rs3.getDouble("tot_part_amt"))));
                    print_stmt=print_stmt+ "<td align='left'>"+Double.toString((rs2.getDouble("trans_amt")-rs3.getDouble("tot_part_amt")))+"</td>";     //out.println("<td align='left'>"+Double.toString((rs2.getDouble("trans_amt")-rs3.getDouble("tot_part_amt")))+"</td>");//Double.toString((rs2.getDouble("trans_amt")-rs3.getDouble("tot_part_amt")));     
                    }
                    else if(rs2.getDouble("trans_amt")==rs3.getDouble("tot_part_amt"))                       //out.println("<td align='left'>"+rs2.getString("trans_amt").trim()+"</td>");
                    {
                    System.out.println("continue");
                    continue;
                    }
                 }
                 else
                 {
                    System.out.println("2nd continue");
                    continue;
                 }
                 rs3.close();
                 ps3.close();
              }
              print_stmt=print_stmt+"<td align='left'>"+rs2.getString("rmks")+"</td></tr>";
              out.println(print_stmt);
              cnt++;
            }

            if(y==0 || cnt==0)
             out.println("<tr><td align='left'>"+"NO DATA FOUND"+"</td></tr>");


          /*  String sql_Pay="select PART_AMOUNT,PART_PAYMENT from FAS_PAYMENT_MASTER where VOUCHER_NO=cb_ref_no?";             
            while(rs2.next())
            {
                
                
                out.println("<tr id="+rs2.getInt("vou_no")+">");   
               
                if(y==0)
                {
                out.println("<td><input type='radio' id='choice' name='choice' checked value='"+rs2.getInt("vou_no")+"'/></td>");
                y=1;
                }
                else
                out.println("<td><input type='radio' id='choice' name='choice' value='"+rs2.getInt("vou_no")+"'/></td>");
                
                 out.println("<td align='left'>"+rs2.getInt("vou_no")+"</td>");
                             
                out.println("<td align='left'>"+rs2.getString("vou_date")+"</td>");
               
                out.println("<td align='left'>"+rs2.getInt("Jtype")+"</td>");
                
                 out.println("<td align='left'>"+rs2.getString("amt").trim()+"</td>");
                out.println("<td align='left'>"+rs2.getString("rmks")+"</td></tr>");
               
                
            }*/
            
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
      </form>
  </body>
</html>