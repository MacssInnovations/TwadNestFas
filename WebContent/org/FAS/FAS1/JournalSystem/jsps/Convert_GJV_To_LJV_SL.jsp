<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">
<%@ page session="false"  contentType="text/html;charset=windows-1252"%>
<%@ page import="java.sql.*,java.util.*,Servlets.Security.classes.UserProfile"%>
<%@ include file="//org/Security/jsps/Check_SessionJSPF.jspf" %>
<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=windows-1252"/>    
    <META HTTP-EQUIV="CACHE-CONTROL" CONTENT=" no-store, no-cache, must-revalidate" >
   <META HTTP-EQUIV="CACHE-CONTROL" CONTENT=" pre-check=0, post-check=0, max-age=0" >
    <title>Convert GJV to LJV </title>
    
    
    <script type="text/javascript"
            src="<%=request.getContextPath()%>/org/Library/scripts/comJS.js"></script>
          
    <script language="javascript" type="text/javascript"
            src="../scripts/Convert_GJV_To_LJV_SL.js"></script>
            
    <link href="../../../../../css/Sample3.css" rel="stylesheet"
          media="screen"/>
          <script type="text/javascript">
          function exit()
          {
          self.close();
          }
          </script>
  </head>
<body class="table">

<form name="frmJournal_ListAll_SL" method="POST" action="../../../../../Convert_GJV_To_LJV_SL.kv" >
  
 <%
  
  /**
   *  Database Connection 
   */
  Connection con=null;
 
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
  
  
            /**
             *  Variables Declaration 
             */
            int cmbAcc_UnitCode=0;
            int cmbOffice_code=0;
            int yr=0;
            int mon=0;
            int recNo=0;


            /** Accounting Unit ID */
            try{
             cmbAcc_UnitCode=Integer.parseInt(request.getParameter("cmbAcc_UnitCode"));
            }catch(Exception e){System.out.println("Exception in getting req:"+e);}
            
            /** Office ID */
            try{
            cmbOffice_code=Integer.parseInt(request.getParameter("cmbOffice_code"));
            }catch(Exception e){System.out.println("Exception in getting req:"+e);}
            
            /** Cashbook Year */
            try{
                 yr=Integer.parseInt(request.getParameter("yr"));
               }catch(Exception e){System.out.println("Exception in getting req:"+e);}
               
            /** Cashbook Month */      
            try{
                 mon=Integer.parseInt(request.getParameter("mon"));
            }catch(Exception e){System.out.println("Exception in getting req:"+e);}
            
            /** Receipt Number */
            try{
                 recNo=Integer.parseInt(request.getParameter("recNo"));
            }catch(Exception e){System.out.println("Exception in getting req:"+e);}
            
  %>
  
  
  
      <table cellspacing="3" cellpadding="2"  width="130%">
        <tr class="tdH">
          <td colspan="2">
            <div align="center">
              <strong> Voucher Details </strong>
            </div>
          </td>
        </tr>
      </table>
      
      <input type="hidden" id="cmbAcc_UnitCode" name="cmbAcc_UnitCode" value="<%=cmbAcc_UnitCode %>" />
      <input type="hidden" id="cmbOffice_code" name="cmbOffice_code" value="<%=cmbOffice_code %>" />
      
      <input type="hidden" id="txtCashbookYear" name="txtCashbookYear" value="<%=yr %>" />
      <input type="hidden" id="txtCashbookMonth" name="txtCashbookMonth" value="<%=mon %>" />
      <input type="hidden" id="txtVoucherNo" name="txtVoucherNo" value="<%=recNo %>" />
      
      
      <table id="mytable" align="center" cellspacing="3" cellpadding="2"
             border="1" width="130%">
             
        <tr class="tdH">
          <th>Serial Number</th>
          <th>Account Head Code</th>
          <th>Credit/Debit</th>
          <th>Sub-Ledger Type</th>
          <th>Sub-Ledger Code</th>
          <th>Amount</th>
          <th>Particulars</th>
        </tr>
        
        
        <tbody id="tbody" class="table">
        
        
          <%
          
          /** Variables Declaration */
          
          ResultSet rs=null;
          ResultSet rs2=null;
          ResultSet rs3=null;  
          ResultSet rs4=null;  
          ResultSet rs5=null;  
          
          PreparedStatement ps=null;
          PreparedStatement ps2=null;
          PreparedStatement ps3=null;
          PreparedStatement ps4=null;
          PreparedStatement ps5=null;
          
          String tmp_slcode="";
          String tmp_slcodeName="";
          int sltype=0;
            String sel = "";          
          /** Generate Sequence Number */
          int seq=1;
          
           try
           {
           
           
           
           
            
             /** Main SQL Query */
            String sql="select SL_NO, ACCOUNT_HEAD_CODE ,CR_DR_INDICATOR, SUB_LEDGER_CODE, "+
            "SUB_LEDGER_TYPE_CODE ,(select SL_CODENAME from SL_TYPE_CODE_NAME_VIEW where SL_TYPE=SUB_LEDGER_TYPE_CODE and SL_CODE=SUB_LEDGER_CODE) as SLNAME ,AMOUNT, PARTICULARS "+
            "from FAS_JOURNAL_TRANSACTION where ACCOUNTING_UNIT_ID="+cmbAcc_UnitCode+" and ACCOUNTING_FOR_OFFICE_ID="+
            cmbOffice_code+"and CASHBOOK_YEAR="+yr+"and CASHBOOK_MONTH="+mon+"and  VOUCHER_NO="+recNo;
            
            
            ps2=con.prepareStatement(sql);
            rs2=ps2.executeQuery();            
            while(rs2.next())
            {
                
                    int acc_head=0;
                    out.println("<tr>");   
                    
                //----------------------------------------------------------------------------------------    
                    
                    /** Print Sequence Number */
                    out.println("<td align='left'><input type='hidden' name='txtSLNo"+seq+"' id='txtSLNo"+seq+"' value='"+rs2.getInt("SL_NO")+"' />");
                    out.println(rs2.getInt("SL_NO")+"</td>");
                    
                //----------------------------------------------------------------------------------------    
                
                    /** Print Account Head Code and its desciption */
                    ps3=con.prepareStatement("select ACCOUNT_HEAD_DESC from COM_MST_ACCOUNT_HEADS where ACCOUNT_HEAD_CODE=?");
                    ps3.setInt(1,rs2.getInt("ACCOUNT_HEAD_CODE"));
                    rs3=ps3.executeQuery();
                    if(rs3.next())
                    {
                      out.println("<td align='left'><input type='hidden' name='txtAccountHeadCode"+seq+"' id='txtAccountHeadCode"+seq+"' value='"+rs2.getInt("ACCOUNT_HEAD_CODE")+"' />");                      
                      out.println(rs2.getInt("ACCOUNT_HEAD_CODE")+"-"+rs3.getString("ACCOUNT_HEAD_DESC")+"</td>");
                      acc_head=rs2.getInt("ACCOUNT_HEAD_CODE");
                    }
                
                //----------------------------------------------------------------------------------------    
                    
                    /** Print CR DR Indicator */
                    out.println("<td align='left'><input type='hidden' name='txtCRDRInd"+seq+"' id='txtCRDRInd"+seq+"' value='"+rs2.getString("CR_DR_INDICATOR")+"' />");                      
                    out.println(rs2.getString("CR_DR_INDICATOR")+"</td>");
              
                //----------------------------------------------------------------------------------------    
                   
                    /** Print Sub Ledger Type Description Only */
                    if(rs2.getInt("SUB_LEDGER_TYPE_CODE")!=0)
                     {                    
                    
                         ps=con.prepareStatement("select SUB_LEDGER_TYPE_DESC from COM_MST_SL_TYPES where SUB_LEDGER_TYPE_CODE=?");
                         ps.setInt(1,rs2.getInt("SUB_LEDGER_TYPE_CODE"));
                         rs=ps.executeQuery();
                         if(rs.next())
                           out.println("<td align='left'>"+rs.getString("SUB_LEDGER_TYPE_DESC"));
                    }
                    else
                       out.println("<td align='left'>"+"  --  ");
                       
                //----------------------------------------------------------------------------------------    
                  
                      
                      /** SQL for getting sub ledger type decsiption */          
            String sql_2=" select sub_ledger_type_code , sub_ledger_type_desc from com_mst_sl_types\n" + 
        "where sub_ledger_type_code in \n" + 
        "( \n" + 
        "  select sub_ledger_type_code from fas_applicable_sl_type\n" + 
        "  where account_head_code in \n" + 
        "  (\n" + 
        "       select account_head_code from com_mst_account_heads\n" + 
        "       where sub_ledger_type_applicable ='Y'\n" + 
        "       and account_head_code = ?\n" + 
        "  )\n" + 
        ") \n ";
         
                    ps4=con.prepareStatement(sql_2);
                    ps4.setInt(1,acc_head);
                    rs4=ps4.executeQuery();        
                    
                  %>         
                  
                  
                 <select name='cmbSL_type<%=seq %>' id='cmbSL_type<%=seq %>' onchange="doFunction('Load_SL_Code',this.value,'<%=seq %>');" >                                       
               
                 <option value='0'>-- select SL Type -- </option>                    
                   
                    
                  <%  
                  
                    while(rs4.next())
                    {                     
                    
                         String load_sql="" +
        "select                                                                         \n" + 
        "  sltype,                                                                                                      \n" + 
        "  (select SUB_LEDGER_TYPE_DESC from COM_MST_SL_TYPES where SUB_LEDGER_TYPE_CODE= sltype) as sltype_desc,       \n" + 
        "  slcode ,                                                                                                     \n" + 
        "  (select SL_CODENAME from SL_TYPE_CODE_NAME_VIEW where SL_TYPE= sltype and SL_CODE = slcode ) as SLCODENAME   \n" + 
        "from                                                                                                           \n" + 
        "(                                                                              \n" + 
        "select                                                                         \n" + 
        "  SUB_LEDGER_TYPE_CODE as sltype,                                              \n" + 
        "  SUB_LEDGER_CODE as slcode                                                    \n" + 
        "from                                                                           \n" + 
        "  FAS_CONVERT_GJV_TO_LJV_TMP                                                   \n" + 
        "where                                                                        \n" + 
        "  ACCOUNTING_UNIT_ID = ?  and                                                \n" + 
        "  ACCOUNTING_FOR_OFFICE_ID =?  and                                           \n" + 
        "  CASHBOOK_YEAR = ? and                                                      \n" + 
        "  CASHBOOK_MONTH = ?  and                                                    \n" + 
        "  VOUCHER_NO = ?  and ACCOUNT_HEAD_CODE= ?  and CR_DR_INDICATOR = ?          \n" + 
        "  and SL_NO=? ) ";
                   
                        
                        ps5=con.prepareStatement(load_sql);
                        ps5.setInt(1,cmbAcc_UnitCode);
                        ps5.setInt(2,cmbOffice_code);
                        ps5.setInt(3,yr);
                        ps5.setInt(4,mon);
                        ps5.setInt(5,recNo);
                        ps5.setInt(6,rs2.getInt("ACCOUNT_HEAD_CODE"));
                        ps5.setString(7,rs2.getString("CR_DR_INDICATOR"));
                        ps5.setInt(8,seq);
                        
                        
                        try
                        {
                          rs5=ps5.executeQuery();      
                        }
                        catch(Exception e ) 
                        {
                         System.out.println(e);
                        }
                        while (rs5.next())
                        {
                          sltype=rs5.getInt("sltype");
                          tmp_slcode=rs5.getString("slcode");
                          tmp_slcodeName=rs5.getString("SLCODENAME");                          
                        }
                        
                        sel = "";
                        int exit_sltype=Integer.parseInt(rs4.getString("SUB_LEDGER_TYPE_CODE")); 
                        
                         
                        if( sltype > 0){
                            if(sltype == exit_sltype )  
                            {
                                    sel = " selected ";
                            }
                        }
                        else 
                        {
                          if (rs2.getInt("SUB_LEDGER_TYPE_CODE") == exit_sltype )
                          {
                             sel = " selected ";
                          }
                        }                          
                        
                        System.out.println(sel+"--"+exit_sltype+"--"+rs2.getInt("SUB_LEDGER_TYPE_CODE")+"--"+sltype); 
                        
                        out.println("<option "+sel+" value='"+exit_sltype+"'>"+rs4.getString("SUB_LEDGER_TYPE_DESC")+"</option>"); 
                        
                    }

                    out.println("</select></td>");  

               //----------------------------------------------------------------------------------------    
                
                    /** Print Sub Ledger Name */
                    if(rs2.getString("SLNAME")!=null)
                        out.println("<td align='left'>"+rs2.getString("SLNAME"));
                    else
                        out.println("<td align='left'>"+"--");
              //----------------------------------------------------------------------------------------    
                        
                    /** Load Sub Ledger Code combo box */ 
                    out.println("<select name='cmbSL_Code"+seq+"' id='cmbSL_Code"+seq+"' > "); 
                    
                    if( sltype > 0  )               
                    {
                      out.println("<option selected value='"+tmp_slcode+"' > "+tmp_slcodeName+" </option>");   
                    }
                    else
                    {
                        if ( rs2.getInt("SUB_LEDGER_CODE") >0 ) 
                        {
                            out.println("<option selected value='"+rs2.getInt("SUB_LEDGER_CODE")+"'> "+rs2.getString("SLNAME")+"</option>");    
                        }
                        else  
                        {
                            out.println("<option selected value='0'>-- select SL Code -- </option>");    
                        }
                    }
                
                    out.println("</select></td>");  
                    
             //----------------------------------------------------------------------------------------    
                                    
                    /** Print Amount */  
                    out.println("<td align='left'><input type='hidden' name='txtAmount"+seq+"' id='txtAmount"+seq+"' value='"+rs2.getString("AMOUNT")+"' />");                      
                    out.println(rs2.getString("AMOUNT")+"</td>");
             //----------------------------------------------------------------------------------------    
                       
                    /** Print Particualars */            
                    if(rs2.getString("PARTICULARS")!=null)
                        out.println("<td align='left'>"+rs2.getString("PARTICULARS")+"</td></tr>");
                    else
                        out.println("<td align='left'>"+"   --  "+"</td></tr>");
             //----------------------------------------------------------------------------------------    
                        
                    seq++;    
                    
                
            }  // Main Loop End -- while(rs2.next())
          }
          catch(Exception e)
          {
            System.out.println("Exception in grid.."+e);
          }
          
          
         %>
         
         <input type='hidden' name='seq' id='seq' value='<%=seq-1 %>'/>
         
        </tbody>
      </table>
      <table align="center" cellspacing="3" cellpadding="2" border="1"
             width="130%">
        <tr class="tdH">
          <td>
            <div align="center">
            
              <input type="submit" id="cmdApply" name="cmdApply" value="Apply" ></input>
              <input type="button" id="cmdcancel" name="cancel" value="Exit"
                     onclick="exit()"></input>
                     
            </div>
          </td>
        </tr>
      </table>
    </form>
   </body>
</html>