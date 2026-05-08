<%@ page contentType="text/html;charset=windows-1252"%>
<%@ page import="java.sql.*"%>
<%@ page import=" java.text.SimpleDateFormat"%>
<%@ page import="java.util.*"%>
<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=windows-1252">
    <title>Edit Account Head Details</title>
    <script language="javascript" src="../scripts/Listajax1.js"></script>
    <script language="javascript" src="../scripts/Listajax2.js"></script>
    <script language="javascript" src="../scripts/Listfunction.js"></script>
    <script language="javascript" src="../scripts/popup.js"></script>
    
   
    
    
    
    <!--<script language="javascript" src="validate.js"> </script>-->

       
    <script language="javascript" src="validate.js"> </script>
    <link href="css/sample.css" rel="stylesheet" media="screen"/>
  </head>
  <body  onload="Load()">
   <form name="form1" action="../../../../../ServletMaster2.view" method="post" >
  <%! String acctHC; %>
    <%
    acctHC=request.getParameter("AHC");
    System.out.println(acctHC);
    %>
    <input type="HIDDEN" name="acctHC"   value=<%=acctHC%>>
 
<%
  System.out.println("page called ");
  Connection connection=null;
  Statement statement=null;
  ResultSet results=null;
  try
  {
    Class.forName("sun.jdbc.odbc.JdbcOdbcDriver");
    connection = DriverManager.getConnection("jdbc:odbc:fas");
    try
    {
      statement=connection.createStatement();
    }
    catch(SQLException e)
    {
    System.out.println("The Exception in Statement:"+e);
    }
  }
  catch(Exception e)
  {
  System.out.println("The Exception in Connection:"+e);
  }
  String ahc="";
  String hoc="";
  String AcctHeadDesc="";
  String DateCreation="";
  String MajHeadCode="";
  String MinHeadCode="";
  String SubGrp1="";
  String SubGrp2="";
  String BalType="";
  String OprnStatus="";
  String LastDate="";
  String fileRefNo="";
  String fileRefDate="";
  String ResAcc="";
  String accBy="";
  String sl="";
  String remarks="";
  
  try
  {
  ahc=request.getParameter("AHC");
  System.out.println("page called " + ahc);
  String sql3="SELECT * FROM FAS_ACCOUNT_HEAD_MASTER WHERE  ACCOUNT_HEAD_CODE='" + ahc + "'" ;                                            
                       System.out.println(sql3);
                       results=statement.executeQuery(sql3);
                       if(results.next())
                       {
                          //hoc=results.getString("Head_Office_Code");
                          
                          AcctHeadDesc=results.getString("ACCOUNT_HEAD_DESC");
                          DateCreation=results.getString("DATE_OF_CREATION");
                         MajHeadCode=results.getString("MAJOR_HEAD_CODE");
                         MinHeadCode=results.getString("MINOR_HEAD_CODE");
                         SubGrp1=results.getString("SUB_HEAD1_CODE");
                         SubGrp2=results.getString("SUB_HEAD2_CODE");
                         BalType=results.getString("BALANCE_TYPE");
                         OprnStatus=results.getString("STATUS");
                         LastDate=results.getString("ACTIVE_UPTO_DATE");
                         fileRefNo=results.getString("FILE_REF_NO");
                         fileRefDate=results.getString("FILE_REF_DATE");
                         ResAcc=results.getString("ACCESS_RESTRICTED");
                         accBy=results.getString("ACCESSIBLE_BY_CODE");
                         sl=results.getString("SUB_LEDGER_TYPE_APPLY");
                         remarks=results.getString("REMARKS");
                        }
                        else
                        {
                          System.out.println("values not found");
                        }
                      results.close();
                      
 }
 catch(Exception ex)
 {
 System.out.println("The exception is:"+ex);
 }

  %>
  
  <%
    SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
          java.util.Date d1;
          try 
              {
              d1 = dateFormat.parse(DateCreation);
              //dateFormat.applyPattern("dd-MM-yyyy");
              DateCreation = dateFormat.format(d1);
              } 
              catch (Exception e)
              {
                e.printStackTrace();
              }
		
              //java.sql.Date date1 = java.sql.Date.valueOf(DateCreation);
              //System.out.println(date1);
              System.out.println(DateCreation);
              
              
              
              /*
              dateFormat = new SimpleDateFormat("dd/MM/yyyy");
              java.util.Date d2;
              try 
                {
                d2 = dateFormat.parse(LastDate);
                dateFormat.applyPattern("yyyy-MM-dd");
                LastDate = dateFormat.format(d2);
                } 
                catch (Exception e)
                {
                  e.printStackTrace();
                }
		
                java.sql.Date date2 = java.sql.Date.valueOf(LastDate);
                System.out.println(date2);
 */
  %>
  <div id="parentt" style="this.style.z-index:1;subledger.style.z-index:2;position:absolute;top:10px;left:10px;" >
    

   
    <%
    System.out.println("page called ");
    %>
      <DIV align="center">
        <H2>
          <FONT color="#993333" size="3"><FONT face="Tahoma"><FONT face="Times New Roman"><FONT size="4"><STRONG>&nbsp;</STRONG><STRONG><FONT color="#333333">Financial Accounting System</FONT></STRONG></FONT> &nbsp;</FONT></FONT></FONT>
          <EM><FONT color="#993333" size="3"><FONT face="Tahoma"><FONT face="Times New Roman" size="2">(</FONT></FONT><FONT size="2">Creation of New Account Head) </FONT></FONT></EM>
        </H2>
        <H2 align="left">
          <EM><FONT color="#3333ff" size="3"><FONT color="#990000"/></FONT></EM>
          <FONT color="#990000" size="3"><STRONG>
            <U><FONT color="#993333">Account Head Directory Maintenance System</FONT></U>
:           </STRONG>
          </FONT>
        </H2>
        <table cellspacing="0" cellpadding="0" border="1" width="90%">
          <tr style="height:1.0pt;">
            <td height="25" width="30%">
              <DIV align="left">
                <FONT size="2">Head Office Code</FONT> 
              </DIV>
</td>
            <td height="25" width="70%">
              <FONT color="#ff9999">
                <input type="text" name="head_off_code" size="25" value=<%=hoc%>>
              </FONT>
            </td>
          </tr>
          <tr>
            <td width="30%" height="26" align="center" >
              <DIV align="left">
                <FONT size="2">Account Head Code *</FONT> 
              </DIV>
</td>
            <td width="70%" height="26">
              <FONT color="#ff9999">
                <input type="text" name="acct_head_code" onblur="callServer('verify')" size="25" value=<%=ahc%>>
              </FONT>
            </td>
          </tr>
          <tr>
            <td width="30%" height="24" align="center">
              <DIV align="left">
                <FONT size="2">Account Head Name *</FONT> 
              </DIV>
</td>
            <td width="70%" height="24">
              <FONT color="#ff9999">
                <input type="text" name="acct_head_des" size="25" value=<%=AcctHeadDesc%>>
              </FONT>
            </td>
          </tr>
          <tr>
            <td width="30%" height="24" align="center">
              <DIV align="left">
                <FONT size="2">Date of Creation *</FONT> 
              </DIV>
            </td>
            <td width="70%" height="24">              
                <input type="text" name="date_creation" size="10" value=<%=DateCreation%>>(dd/mm/yyyy)             
            </td>
          </tr>
          <tr>
            <td width="30%" height="20" align="center">
              <DIV align="left">
                <FONT size="2">Major Group *</FONT> 
              </DIV>
</td>
            <td width="70%" height="20">
              <select name="major_grp_code" onchange="callMySelect()">              
              <%
                  try
                  {
                      String sql="select * from FAS_MINOR_ACCOUNT_HEAD";                      
                      results=statement.executeQuery(sql);
                      while(results.next())
                      {
                          String temp=results.getString("MAJOR_HEAD_CODE");
                          if(temp.equals(MajHeadCode))
                            out.println("<option value='" + temp + "' selected>" + results.getString("MAJOR_HEAD_CODE") + "</option>");                             
                          else
                            out.println("<option value='" + temp + "'>" + results.getString("MAJOR_HEAD_CODE") + "</option>");                             
                      }
                      results.close();
                  }
                  catch(SQLException e)
                  {
                        System.out.println("Exception in creating statement:"+e);
                  }
              %>
              </select>                
            </td>
          </tr>
          
          <tr>
            <td width="30%" align="center">
              <DIV align="left">
                <FONT size="2">Minor Group *</FONT> 
              </DIV>
</td>
            <td width="70%" >
              <select name="minor_grp_code">
                <%
                  try
                  {
                      String sql="select * from FAS_MINOR_ACCOUNT_HEAD";                      
                      results=statement.executeQuery(sql);
                      while(results.next())
                      {
                          String temp=results.getString("MINOR_HEAD_CODE");
                          if(temp.equals(MinHeadCode))
                            out.println("<option value='" + temp + "' selected>" + results.getString("MINOR_HEAD_DESC") + "</option>");                             
                          else
                            out.println("<option value='" + temp + "'>" + results.getString("MINOR_HEAD_DESC") + "</option>");                             
                      }
                      results.close();
                  }
                  catch(SQLException e)
                  {
                        System.out.println("Exception in creating statement:"+e);
                  }
              %>
              </select>
            </td>
          </tr>
          <tr>
            <td width="30%" height="18" align="center">
              <DIV align="left">
                <FONT size="2">Sub-Group-1 *</FONT> 
              </DIV>
</td>
            <td width="70%" height="18">
              <select name="sub_grp1">
              <%
              if(SubGrp1.equals("LI"))
               out.print("<option value=\"LIC\" selected>LIC</option>");
              else
                out.print("<option value=\"LIC\">LIC</option>");
              
              if(SubGrp1.equals("MN"))
               out.print("<option value=\"MNP\" selected>MNP</option>");
              else
                out.print("<option value=\"MNP\">MNP</option>");
              
              if(SubGrp1.equals("AR"))
               out.print("<option value=\"ARP\" selected>ARP</option>");
              else
                out.print("<option value=\"ARP\">ARP</option>");
              %>
              </select>              
            </td>
          </tr>
          <tr>
            <td width="30%" align="center">
              <DIV align="left">
                <FONT size="2">Sub-Group-2 *</FONT> 
              </DIV>
</td>
            <td width="70%">
              <select name="sub_grp2">
           <%
              if(SubGrp2.equals("RS"))
               out.print("<option value=\"RWS Schemes\" selected>RWS Schemes</option>");
              else
                out.print("<option value=\"RWS Schemes\">RWS Schemes</option>");
              
              if(SubGrp2.equals("US"))
               out.print("<option value=\"Urban Schemes\" selected>Urban Schemes</option>");
              else
                out.print("<option value=\"Urban Schemes\">Urban Schemes</option>");
              
              if(SubGrp2.equals("MS"))
               out.print("<option value=\"Maintenance Schemes\" selected>Maintenance Schemes</option>");
              else
                out.print("<option value=\"Maintenance Schemes\">Maintenance Schemes</option>");
              %>
              </select>
                  
            </td>
          </tr>
          <tr>
            <td width="30%" align="center">
              <DIV align="left">
                <FONT size="2">Balance Type *</FONT> 
              </DIV>
</td>
            <td width="70%">
            <%
              if(BalType.equals("CR"))
              {
               out.print("<input type=\"radio\" name=\"bal_type1\" value=\"CR\" checked/>Credit");
               out.print("<input type=\"radio\" name=\"bal_type1\" value=\"DB\" />Dedit");
               out.print("<input type=\"radio\" name=\"bal_type1\" value=\"CD\" />Credit/Debit");
             }
             else if(BalType.equals("DB"))
             {
              out.print("<input type=\"radio\" name=\"bal_type1\" value=\"CR\"/>Crebit");
              out.print("<input type=\"radio\" name=\"bal_type1\" value=\"DB\" checked/>Debit");
              out.print("<input type=\"radio\" name=\"bal_type1\" value=\"CD\"/>Credit/Debit");
             } 
            else 
            {
              out.print("<input type=\"radio\" name=\"bal_type1\" value=\"CR\" />Credit");
              out.print("<input type=\"radio\" name=\"bal_type1\" value=\"DB\" />Debit");
              out.print("<input type=\"radio\" name=\"bal_type1\" value=\"CD\" checked/>Credit/Debit");
            }   
               //out.print("<option value=\"RWS Schemes\" selected>RWS Schemes</option>");
            %>
            <!--<input type="radio" name="bal_type1" value="Credit" checked/>Credit &nbsp;<input type="radio" name="bal_type1" value="Debit"/>Debit &nbsp;<input type="radio" name="bal_type1" value="Credit/Debit"/>Credit/Debit-->
            </td>
          </tr>
          <tr>
            <td width="30%" height="21" align="center" >
              <DIV align="left">
                <FONT size="2">In Use? *</FONT> 
              </DIV>
</td>
            <td width="70%" height="21">
            <%
              if(OprnStatus.equalsIgnoreCase("Y"))
              {
                out.print("<input type=\"radio\" name=\"status1\" value=\"Y\" checked  onclick=\"disableFields()\"/>Yes");
                out.print("<input type=\"radio\" name=\"status1\" value=\"N\"  onclick=\"enableFields()\" />No");
              }
              else if(OprnStatus.equalsIgnoreCase("N"))
              {
               out.print("<input type=\"radio\" name=\"status1\" value=\"Y\"   onclick=\"disableFields()\"/>Yes");
               out.print("<input type=\"radio\" name=\"status1\" value=\"N\" checked  onclick=\"enableFields()\"/>No");
              }
            %>
             <!-- <input type="radio" name="status1" value="Yes" checked onclick="disableFields()"/>Yes &nbsp;<input type="radio" name="status1" value="No" onclick="enableFields()"/>No-->
              </td>
          </tr>
          <tr>
            <td width="30%" height="26" style="color:rgb(128,0,0);" align="center">
              <DIV align="left">
                <FONT size="2">If Not in use, when last used</FONT> 
              </DIV>
</td>
            <td width="70%" height="26">
            
              <input type="text" name="Last_date_used" size="10" value="<%=LastDate%>"/>(dd/mm/yyyy)
            </td>
          </tr>
          <tr>
            <td width="30%" height="26" style="color:rgb(128,0,0);" align="center">
              <DIV align="left">
                <FONT size="2">Reference No. and Date</FONT> 
              </DIV>
</td>
            <td width="70%" height="26">
              <input type="text" maxlength="6" size="6" name="File_Ref_No" value="<%=fileRefNo%>"/>
              <input type="text" maxlength="10" size="10" name="File_Ref_Date" value="<%=fileRefDate%>"/>(dd/mm/yyyy)
            </td>
          </tr>
          <tr>
            <td width="30%" align="center">
              <DIV align="left">
                <FONT size="2">Restricted Access *</FONT> 
              </DIV>
</td>
            <td width="70%">
            <%
               if( ResAcc.equalsIgnoreCase("Y"))
              {
                out.print("<input type=\"radio\" name=\"res_access1\" value=\"Y\" checked  onclick=\"enableMe()\"/>Yes");
                out.print("<input type=\"radio\" name=\"res_access1\" value=\"N\"  onclick=\"disableMe()\" />No");
              }
              else if( ResAcc.equalsIgnoreCase("N"))
              {
               out.print("<input type=\"radio\" name=\"res_access1\" value=\"Y\"   onclick=\"enableMe()\"/>Yes");
               out.print("<input type=\"radio\" name=\"res_access1\" value=\"N\" checked  onclick=\"disableMe()\"/>No");
              }
              
            %>
            <!--
              <input type="radio" name="res_access1" value="Yes" onclick="enableMe()" checked/>Yes &nbsp;<input type="radio" name="res_access1"  value="No" onclick="disableMe()"/>No
            --> 
              </td>
          </tr>
          <tr>
            <td width="30%" align="center">
              <DIV align="left">
                <FONT size="2">Accessible only by</FONT> 
              </DIV>
</td>
            <td width="70%">
              <input type="text" maxlength="10" size="15" name="AccessibleBy" readonly value="<%=accBy%>"/>
              <input type="button" value="Select office/ section" onclick="popWindow()" name="selOff"/>
            </td>
          </tr>
          <tr>
            <td width="30%" align="center">
              <DIV align="left">
                <FONT size="2">Sub-Ledgers Applicable *</FONT> 
              </DIV>
</td>
            <td width="70%">
            <%
              if(sl.equalsIgnoreCase("Y"))
              {
                out.print("<input type=\"radio\" name=\"sub_ledg1\" value=\"Y\" checked  onclick=\"parentt.style.zIndex=1; subledger.style.zIndex=2; subledger.style.display='block';\"/>Yes");
                out.print("<input type=\"radio\" name=\"sub_ledg1\" value=\"N\"  onclick=\"parentt.style.zIndex=2; subledger.style.zIndex=1; subledger.style.display='none';\" />No");
              }
              else if(sl.equalsIgnoreCase("N"))
              {
                out.print("<input type=\"radio\" name=\"sub_ledg1\" value=\"Y\"  onclick=\"parentt.style.zIndex=1; subledger.style.zIndex=2; subledger.style.display='block';\"/>Yes");
                out.print("<input type=\"radio\" name=\"sub_ledg1\" value=\"N\"  checked  onclick=\"parentt.style.zIndex=2; subledger.style.zIndex=1; subledger.style.display='none';\" />No");
              }
            %>
            
             <!-- <input type="radio" name="sub_ledg1" value="Yes" onclick="parentt.style.zIndex=1; subledger.style.zIndex=2; subledger.style.display='block';"/>Yes &nbsp;&nbsp;
              <input type="radio" name="sub_ledg1" value="No" onclick="parentt.style.zIndex=2; subledger.style.zIndex=1; subledger.style.display='none';" checked/>No
              -->
              </td>
          </tr>
          <tr>
            <td width="30%" align="center">
              <DIV align="left">
                <FONT size="2">Remarks</FONT> 
              </DIV>
</td>
            <td width="70%">
              <textarea cols="35" rows="4" name="txtRemarks"><%=remarks%></textarea>
            </td>
          </tr>
         
            </table>
            </div>
            
           
           
            
            <div id="subledger" class="pupupdiv"><%--as "none", initially the table is disabled --%>
            <DIV align="center">
              <table cellspacing="2" cellpadding="3" border="1" width="90%">
                <tr>
                  <td colspan="4">
                    <STRONG>
                      <center>
                        <FONT size="3">New Applicable Sub-Ledger Types</FONT> 
                      </center>
                    </STRONG>
                  </td>
                </tr>
                <tr>
                  <td colspan="3">Sub-Ledger Type Code</td>
                  <td>
                    <input type="text" name="txt_sltypeCode" maxlength="10" size="10" readonly/>
                  </td>
                </tr>
                <tr>
                  <td colspan="3">Sub-Ledger Type Description</td>
                  <td>
                    <%!  int ahcode; %>
                    <% 
                  try
                  {
                  ahcode=Integer.parseInt(request.getParameter("AHC"));
                  }
                  catch(NumberFormatException e)
                  {
                  System.out.println("The Excepton in accHead:"+e);
                  }
                  %>
                    <select name="txt_sldesc" onchange="displaysubcode()">
                      <option value>---Select Here---</option>
                      <% 
                       String sql="SELECT [SUB_LEDGER_TYPE_CODE], [SUB_LEDGER_TYPE_DESC] FROM FAS_SUB_LEDGER_TYPE WHERE SUB_LEDGER_TYPE_CODE not in (select SUB_LEDGER_TYPE_CODE from FAS_APPLICABLE_SL_TYPE where ACCOUNT_HEAD_CODE='" + ahcode + "')" ;                                            
                       System.out.println(sql);
                       results=statement.executeQuery(sql);
                       while(results.next())
                       {
                            //String ss=results.getString("Sub_Ledger_Desc");
                            //if(sldesc.equals(ss))
                              //out.println("<option value='" + results.getString("Sub_Ledger_Type_Code") + "' selected >" + ss + "</option>");
                            //else
                              //out.println("<option value='" + results.getString("Sub_Ledger_Type_Code") + "'>" + ss + "</option>");
                            out.println("<option value='" + results.getString("SUB_LEDGER_TYPE_CODE") + "'>" + results.getString("SUB_LEDGER_TYPE_DESC") + "</option>");
   
                      }
                      results.close();

                %>
                    </select>
                  </td>
                </tr>
                <tr>
                  <td colspan="4">
                    <input type="button" value="Update" name="update" onclick="addSL();subledger.style.display='none';"/>
                    <input type="button" value="Cancel" name="Cancel2" onclick="subledger.style.display='none'"/>
                    <input type="BUTTON" value="Edit"  name="edit2" onclick="changeTableContent()"/>
                  </td>
                </tr>
              </table>
            </DIV>
             <!--<P>
                <center><input type="BUTTON" value=" Okay " name="Submit2" onclick="subledger.style.display='none';"/>
                
              </P>
             --> 
            
            
            </div>
                        
              <!--<table id="tblTable">
              <tr>
                  <td colspan="6">-->
                    <P align="left">
                      
                        <b><FONT  color="#993333" size="3"><U>Existing Applicable Sub-Ledger Types: </U></FONT></b>
                      
                    </P>
                    
                 <!--</td>
                </tr>
              <tr>-->
                <DIV align="center">
                  <table  cellspacing="0" cellpadding="0" border="1" width="90%" id="SubLedger" align="center">
                    <th>
                      <FONT color="#000000" id="si_no">SI.No</FONT>
                      <FONT color="#000000"> </FONT>
                      </th>
                    <th>
                      <FONT color="#000000" id="sl">Sub-Ledger Type Code</FONT>
                      <FONT color="#000000"> </FONT>
                      </th>
                    <th>
                      <FONT color="#000000" id="sld">Sub-Ledger Type Description</FONT>
                      <FONT color="#000000"> </FONT>
                      </th>
                    <th>
                      <FONT color="#000000">Delete Record?</FONT>
                      <FONT color="#000000"> </FONT>
                      </th>
                    <th>
                      <FONT color="#000000">Edit Record?</FONT>
                    </th>
                    <tbody id="tblList"/>
                  </table>
                </DIV>
          <!--</tr>
          </table>-->
          <DIV align="center">
            <table cellspacing="0" cellpadding="0" border="1" width="90%" align="center">
              <tr>
                <td height="34">
                  <center>
                    <input type="BUTTON" value="Add New SL Type" name="add_new" onclick="enable_NewSL();parentt.style.zIndex=1; subledger.style.zIndex=2; subledger.style.display='block';"/>
                     <script language="javascript">
                     function muruga()
                     {
                     //alert("saravanabhavaa");
                    
                     }
                     </script>
                  </center>
                </td>
              </tr>
              <tr>
              <td align="center" onclick="a();">
                    <input type="submit" value=" Submit " name="finalsubmit"/>
                    <input type="button" value="Cancel" name="Cancel1" onclick="closeWindow()"/>
                    <input type="HIDDEN" name="txtahc" value=<%=ahcode%>>
              </td>
              </tr>
            </table>
          </DIV>
          <P>&nbsp;</P>
          <P>&nbsp;</P>
          <P>&nbsp;</P>
          <P>&nbsp;</P>
          <P>&nbsp;</P>
          <P>&nbsp;</P>
          <P>&nbsp;</P>
           
           
            
             
              
         </div>      
         </form>
          
          
      
        <H2/>
        <H2/>
      </DIV>
   </div>  
      
  
 
 
  </body>
</html>
