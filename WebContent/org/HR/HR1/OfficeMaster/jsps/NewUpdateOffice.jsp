<!--
    File Name     : MasterBenefit.jsp
    Purpose       : To create form that allows us add,modify and delete records residing in database
    References    : BenefitAjax.js,BenefitValidations.js,sample2.css
    Servlet Ref.  : ServletBenefitMaster.java
-->

<%@ page contentType="text/html;charset=windows-1252"%>
<%@ page import="java.sql.*,java.util.*"%>
<html>
    <head>
      <meta http-equiv="Content-Type" content="text/html; charset=windows-1252">
      <title>Update An Office details</title>    
          <script type="text/javascript" src="../scripts/NewOfficeValidation.js" >    
          </script>
          <script type="text/javascript" src="../scripts/controllingOffice1.js"></script>
          <script type="text/javascript" src="../../../../Library/scripts/checkDate.js"></script>
          <script type="text/javascript">            
          function closeWindow()
          {              
              self.close();
              window.opener.focus();
          }          
          </script>
          <link href='../../../../../css/yellow.css' rel='stylesheet' media='screen'/>
    </head>
 <body class="bgbody" onload="officename()"> 
 <%
   Connection connection=null;
   Statement statement=null;
   ResultSet results=null;   
   ResultSet rs=null; 
   
   String strOId=request.getParameter("txtOffice_Id");   
   String strName="",strSName="",strCCAClassId="";
   String strLevel="",strOCode="",strPrimaryId="",strDateOfFormation="",strHRAClassId="",strIsAccountUnit="",strWingsApplicable="",strRemarks="";
   
   int intHeadCode=0,intCOffId=0;
   
   java.sql.Date DateOfFormation=null;
   String DateToBeDisplayed="",ControllingOfficeName="";
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
     connection=DriverManager.getConnection(ConnectionString,strdbusername.trim(),strdbpassword.trim());

       try
       {
            statement=connection.createStatement();
            connection.clearWarnings();
            String sql="select * from COM_MST_OFFICES where Office_Id=" + strOId;
            System.out.println("query : " + sql);
            rs=statement.executeQuery(sql);
            if(rs.next())
            {
              try
                {                 
                      strName = rs.getString("Office_Name"); 
                      System.out.println("OfficeName"+strName);
                      strSName = rs.getString("Office_Short_Name");  
                      System.out.println("OfficeShorName"+strSName);
                      try
                      {
                        intHeadCode = rs.getInt("OFFICE_HEAD_CADRE_ID");  
                      }catch(NumberFormatException mfe)
                      {} 
                        
                      strLevel = rs.getString("Office_Level_Id");  
                      //strOCode = rs.getString("Office_Old_Code");  
                      strPrimaryId = rs.getString("Primary_Work_Id");                      
                      DateOfFormation = rs.getDate("Date_of_Formation");  
                      
                      if(DateOfFormation==null)
                      {
                          DateToBeDisplayed="";
                      }
                      else
                      {
                          try
                          {
                              java.text.SimpleDateFormat sdf=new java.text.SimpleDateFormat("dd/MM/yyyy");
                              DateToBeDisplayed=sdf.format(DateOfFormation);
                          }
                          catch(Exception e)
                          {
                            System.out.println("error while formatting date : " + e);
                          }
                      } 
                      System.out.println("date : " + DateToBeDisplayed);
                          
                      //strHRAClassId = rs.getString("HRA_Class_Id");  
                      //strCCAClassId = rs.getString("CCA_Class_Id");  
                      //strIsAccountUnit = rs.getString("Accounting_Unit");  
                      //strWingsApplicable = rs.getString("Wings_Applicable");  
                      strRemarks=rs.getString("Remarks");
                      strRemarks=strRemarks.trim();
                      System.out.println("remarks:"+strRemarks);
                      rs.close(); 
                      try
                      {
                          rs=statement.executeQuery("select CONTROLLING_OFFICE_ID from COM_OFFICE_CONTROL where OFFICE_ID=" + strOId );
                          if(rs.next())
                          {
                            intCOffId=rs.getInt("Controlling_Office_Id");
                          }
                          else
                          {
                            intCOffId=0;
                          }
                      }catch(NumberFormatException mfe)
                      {}
                      
                      System.out.println("-------------------------------------");
                      System.out.println(intHeadCode);
                      System.out.println(intCOffId);
                      System.out.println(strLevel);
                      System.out.println(strOCode);
                      System.out.println(strPrimaryId);
                      System.out.println(DateOfFormation);
                      System.out.println(strHRAClassId);
                      System.out.println(strCCAClassId);
                      System.out.println(strIsAccountUnit);
                      System.out.println(strWingsApplicable);
                      System.out.println("-------------------------------------");
                      
                      System.out.println("values retrived sucessfully");
                }
                catch(Exception e)
                {
                  e.printStackTrace();
                }     
                               
            }
            else
            {
               String url="../../../../Library/jsps/Messenger.jsp?message=" + "Invalid Id <br>or Office Does not exist with this Id.<br>Please check the Id...";
               response.sendRedirect(url);
            }            
       }
       catch(SQLException e)
       {
            String url="../../../../Library/jsps/Messenger.jsp?message=" + "Could not open database...";
            response.sendRedirect(url);            
       }          
  }
  catch(Exception e)
  {
         System.out.println("Exception in openeing connection:"+e);
  }  
 %>
        
        <form name="frmOffice" method="POST" action="../../../../../NewServletUpdateOfficeDetails.con" onsubmit="return nullcheckUp()" >
           <table  cellspacing="1" cellpadding="3" width="100%" >
                    <tr>
                        <td class="bgClass">
                            <center><b>Offices Updation</b></center>
                        </td>
                    </tr>
                    <tr>
                        <td>                        
                              <table  cellspacing="3" cellpadding="1"  width="100%">
                                  <TR>
                                      <TD vAlign=top width=209 height=3>
                                          <P style="MARGIN-BOTTOM: -16px"><B><FONT face=Tahoma color=#808080 
                                                                        size=2>Office Id</FONT></B></P>
                                      </TD>
                                      <TD vAlign=top width=701 height=3>
                                          <input type="text" name="txtOff_Id" size="5" maxlength="5" value=<%=strOId%> disabled>   
                                          <input type="hidden" name="txtOffice_Id" value=<%=strOId%> >   
                                          
                                      </TD>
                                  </TR>
                                  <tr>
                                      <td  >Office Name <label style="color:rgb(255,0,0);">&nbsp;*</label> </td>
                                      <td style="color:rgb(255,51,102);">
                                        <input type="text" name="txtOffName"
                                               size="40" maxlength="75" value="<%=strName%>" >
                                      </td>
                                  </tr>
                                  <tr>
                                      <td  >Office Short Name
                                      <label style="color:rgb(255,0,0);">&nbsp;*</label></td>
                                      <td>
                                        <input type="text" name="txtShortName" maxlength="55"
                                               size="40" value="<%=strSName%>" />
                                      </td>
                                  </tr> 
                                  <tr>
                                      <td  >Office Head Code
                                      <label style="color:rgb(255,0,0);">&nbsp;*</label></td>
                                      <td>
                                        <select name="cmbHeadCode">                                        
                                            <option value=0>
                                            ----Select Cadre-------------
                                        </option>                                            
                                            <%
                                                  try
                                                  {                                                    
                                                    results=statement.executeQuery("select * from HRm_MST_CADRE");                                                     
                                                    while(results.next()) 
                                                    {
                                                        int sel2=results.getInt("Cadre_Id");
                                                        out.print("<option value='" + sel2 + "' ");                                                         
                                                        if(intHeadCode==sel2)
                                                        {
                                                          out.print("selected");
                                                        }
                                                        out.print(" >" + results.getString("Cadre_Name") + "</option>");
                                                    }
                                                    results.close();
                                                  }
                                                  catch(Exception e)
                                                  {
                                                  System.out.println("Exception in creating result set:"+e);
                                                  }      
                                            %>                 
                                        </select>
                                      </td>
                                  </tr> 
                                  <tr>
                                      <td  >Office&nbsp;Level
                                      <label style="color:rgb(255,0,0);">&nbsp;*</label></td>
                                      <td>
                                        <SELECT size=1 name=cmbLevelId >   
                                            <option value=0>
                                            ----Select OfficeLevel------------
                                        </option>
                                            <%
                                                  try
                                                  {
                                                    results=statement.executeQuery("select * from COM_MST_OFFICE_LEVELS"); 
                                                    while(results.next()) 
                                                    {
                                                        String sel2=results.getString("Office_Level_Id");
                                                        out.print("<option value='" + sel2 + "' ");                                                          
                                                        if(strLevel!=null)
                                                        {
                                                            if(strLevel.equals(sel2))
                                                            {
                                                                out.print("selected");
                                                            }
                                                        }
                                                        out.print(" >" + results.getString("Office_Level_Name") + "</option>");                                                         
                                                    }
                                                    results.close();
                                                  }
                                                  catch(Exception e)
                                                  {    
                                                    System.out.println("exception : " + e);
                                                  }   
                                                  //System.out.println("loaded");
                                            %>
                                          </SELECT>
                                      </td>
                                  </tr>
                                    
                                  <tr>
                                      <td class="td">Controlling Office Id  
                                      <label style="color:rgb(255,0,0);"> &nbsp;*</label></td>
                                      <td>
                                        <table>
                                        <tr>                                       
                                            <td></td>
                                            <td>Office Level</td>
                                            <td><div id="divType1" style="visibility:hidden">Office Type</div></td>
                                            <td><div id="divType2" style="visibility:hidden">Select Office</div></td>
                                        </tr>
                                        <tr>
                                            <td>
                                                <input type="text" name="txtContrllingOfficeID" maxlength="70" size="6" value="<%=intCOffId%>"  onblur="officename()" >                                                 
                                                <input type="hidden" name="txtHContrllingOfficeID" />
                                            </td>
                                            <td>
                                                <SELECT size=1 name=cmbControllingLevel onchange="getOfficesByLevel()">   
                                                <option value>
                                                        ----Select
                                                        OfficeLevel----
                                                    </option>
                                                <%
                                                      try
                                                      {
                                                        results=statement.executeQuery("select * from COM_MST_OFFICE_LEVELS"); 
                                                        while(results.next()) 
                                                        {
                                                            out.print("<option value='" + results.getString("Office_Level_Id") + "'>" + results.getString("Office_Level_Name") + "</option>");                      
                                                        }
                                                        results.close();
                                                      }
                                                      catch(Exception e)
                                                      {                        
                                                      }      
                                                %>
                                              </SELECT>
                                              </td>
                                              <td>
                                                <select name="cmbOfficeType" style="visibility:hidden" onchange="getOfficesByType()">
                                                    <option value=0>
                                                        ----Select Office
                                                        Type----
                                                    </option>
                                                    <%
                                                          try
                                                          {
                                                            results=statement.executeQuery("select * from COM_MST_WORK_NATURE"); 
                                                            while(results.next()) 
                                                            {
                                                                out.print("<option value='" + results.getString("Work_Nature_Id") + "'>" + results.getString("Work_Nature_Desc") + "</option>");                      
                                                            }
                                                            results.close();
                                                          }
                                                          catch(Exception e)
                                                          {}      
                                                    %>       
                                                </select>
                                            </td>
                                            <td>
                                                <select name="cmbSelectOffice" style="visibility:hidden" id="cmbSelectOffice" onchange="selectControllineOffice()">
                                                    <option value=0>----Select Office----</option>                                               
                                                </select>
                                            </td>
                                        </tr>
                                        </table>
                                      </td>
                                  </tr>
                                     <tr>
                                    <td>Controlling OfficeName</td>
                                    <td>
                                        <input type=text name="txtconOfficeName" value="<%=strName%>" disabled
                                               size="40">
                                    </td>
                                  </tr>                                                            
                                  <!--<tr>
                                      <td  >Office&nbsp;Old Code                                      <label style="color:rgb(255,0,0);"></label></td>
                                      <td>
                                        <input type="text" name="txtOCode"  maxlength="3" size="3"  onkeyup="isInteger(this,event);" value=<%=strOCode%>  ></td>
                                  </tr>-->
                                  <tr>
                                        <td rowspan="2">Controlling OfficeAddress
                                                </td>
                                        <td><input type=text name="txtconOfficeAddress" size="45" disabled></td>
                                        
                                  </tr>
                                  <tr>
                                        <td><input type=text name="txtconOfficeAddress1" size="45" disabled></td>
                                        
                                  </tr>
                                 
                                  <tr>
                                      <td >Primary Work Nature  
                                      </td>
                                      <td>                                        
                                        <select name="cmbPrimaryID">                                        
                                            <option value=0>----Select Work Nature--------</option>
                                            <%
                                                  try
                                                  {
                                                    results=statement.executeQuery("select * from COM_MST_WORK_NATURE"); 
                                                    while(results.next()) 
                                                    {
                                                        String sel2=results.getString("Work_Nature_Id");
                                                        out.print("<option value='" + sel2 + "' ");                                                          
                                                        if(strPrimaryId!=null)
                                                        {
                                                            if(strPrimaryId.equals(sel2))
                                                            {
                                                                out.print("selected");
                                                            }
                                                        }
                                                        out.print(" >" + results.getString("Work_Nature_Desc") + "</option>");                                                         
                                                    }
                                                    results.close();
                                                  }
                                                  catch(Exception e)
                                                  {}      
                                            %>                 
                                        </select>
                                      </td>
                                  </tr>
                          
                                  <tr>
                                      <td>Date of Formation</td>
                                      <td>
                                        <input type="text" name="txtDOF" maxlength="10" size="10"  onFocus="javascript:vDateType='3'" onKeyUp="DateFormat(this,this.value,event,false,'3')" onBlur="DateFormat(this,this.value,event,true,'3')" value=<%=DateToBeDisplayed%> >
                                        <!--<input type="text" name="txtDOF" maxlength="10" size="10" onFocus="javascript:vDateType='3'" onKeyUp="DateFormat(this,this.value,event,false,'3')" onBlur="DateFormat(this,this.value,event,true,'3')"/>-->
                                      </td>
                                  </tr>
                                  
                                  
                                  <tr>
                                      <td  >
                                        Remarks
                                      </td>
                                      <td>
                                        <textarea name="txtRemarks" cols="25" rows="5"><%=strRemarks%></textarea>
                                      </td>
                                  </tr>
                                  <tr>
                                      <td colspan=3 class="bgClass" align="center">
                                         
                                          <input type="SUBMIT" value="  Submit  " name="cmdSub"> &nbsp;
                                          <input type="RESET" value=" Clear All " name="cmdClear">&nbsp; 
                                          <input type="Button" value=" Cancel " name="cmdCancel" onclick="closeWindow();"> &nbsp;                                                              
                                        
                                      </td>                              
                                  </tr>                              
                              </table>                              
                        </td>                        
                    </tr>
              </table>
        </form>
  </body>
</html>

