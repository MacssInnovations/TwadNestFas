<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">
<%@ page contentType="text/html;charset=UTF-8"%>
<%@ page import="java.sql.*,java.util.*,Servlets.Security.classes.UserProfile"%>
<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=windows-1252"/>   
    <meta http-equiv="cache-control" content="no-cache">
    <title>Bill_Account_Heads</title>
       <script type="text/javascript" src="<%=request.getContextPath()%>/org/Library/scripts/comJS.js"></script>
       <link href="../../../../../css/Sample3.css" rel="stylesheet" media="screen"/>
       <link href="../../../../../css/CalendarControl.css" rel="stylesheet" media="screen"/>
       
         <script type="text/javascript" src="../../../../../org/FAS/FAS1/CalendarCtrl.js"></script> 
         <script type="text/javascript" src="../../../../../org/Library/scripts/checkDate.js"></script>
         
         <script type="text/javascript" src="../scripts/Bill_Account_Heads.js"></script>
         <script type="text/javascript" src="../../../../../org/FAS/FAS1/ReceiptSystem/scripts/Com_Popup_XMLreq_SL.js"></script> 
         
         <script type="text/javascript" src="../scripts/ListAllVehicleBill.js"></script>
         <script language="javascript" type="text/javascript">
         
         function closeWindow()
         {                
             window.open('','_parent','');                
             window.close(); 
             window.opener.focus();
         }
         
       function loadyear_month()
         {
              var currentTime = new Date();
              var month = currentTime.getMonth() + 1;  
              var day = currentTime.getDate();
              var year = currentTime.getFullYear();	
              var ssyr1="";
              fin_year_from="",fin_year_to="",fin_year_to_s="";
              var itemcombo=document.getElementById("fin_yr");
              if(month<4)
                     year=year-1;
              i=0;
              while(i<2)
              {
                         fin_year_from=year;
                         fin_year_to=year+1;
                  // alert("fin_year_to "+fin_year_to);
                   fin_year_to_s=fin_year_to.toString();
     					 ssyr1=fin_year_to_s.substring(2,4);
                        // alert("ssyr1 "+ssyr1);
                         var option=document.createElement("option");
                         var text=document.createTextNode(fin_year_from+"-"+ssyr1);
                         option.setAttribute("value",fin_year_from+"-"+ssyr1);
                         if(i==0)
                                 option.setAttribute("selected","true");
                         option.appendChild(text);
                         itemcombo.appendChild(option);
                         year=year-1;i++;
             }
         }
       function loadDate()
       {
            var today= new Date(); 
            var day=today.getDate();
            var month=today.getMonth();
            month=month+1;
            if(day<=9 && day>=1)
            day="0"+day;
            if(month<=9 && month>=1)
            month="0"+month;
            var year=today.getYear();
            if(year < 1900) year += 1900;                
            document.AccHead.ann_date1.value=day+"/"+month+"/"+year;                
      }
     
    </script>
  </head>
  <body class="table" onload="loadyear_month();loadDate();">
     <%
             Connection con=null;
             ResultSet rs=null;
             PreparedStatement ps=null,ps2=null;
            
             Connection connection=null;
        
             ResultSet results=null,rs2=null;
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
                       System.out.println("Exception in opening connection :"+e);
             } 
   %>
        
  <form name="AccHead" id="AccHead" method="POST">
      <table cellspacing="2" cellpadding="3" border="1" width="100%">
        <tr class="tdH">
              <td>
                <div align="center">
                  TWAD BOARD-INTEGRATED ONLINE SYSTEM -FINANCIAL ACCOUNTING SYSTEM
                </div>
              </td>
        </tr>
        
        <tr class="table">
          <td>
            <div align="center">
              <b>Bill and Account Heads</b>
            </div>
          </td>
        </tr>
        </table>
      <table cellspacing="2" cellpadding="3" border="1" width="100%" align="center">
        <tr>
            <td class="table">Financial Year<label style="color:rgb(255,0,0);">&nbsp;*</label></td>
            <td class="table">
		                 <select name="fin_yr" id="fin_yr">
		                 </select>
            </td>
      </tr>
        <tr class="table">
                <td width="40%">
                  <div align="left">
                       Bill Major Type <font color="#ff2121">*</font>
                  </div>
                  
                </td>
                <td width="60%">
                  <div align="left">
                    <select  name="majorType" id="majorType" tabindex="6"
                            onchange="callminor()">
                      <option value="">--Select Major Type--</option>
                      <%
                        try
                        {
                        ps=con.prepareStatement("select BILL_MAJOR_TYPE_CODE,BILL_MAJOR_TYPE_DESC from FAS_BILL_MAJOR_TYPES order by BILL_MAJOR_TYPE_DESC");
                        rs=ps.executeQuery();
                            while(rs.next())
                            {
                            out.println("<option value="+rs.getInt("BILL_MAJOR_TYPE_CODE")+">"+rs.getString("BILL_MAJOR_TYPE_DESC")+"</option>");
                            }
                        }
                        catch(Exception e)
                        {
                        System.out.println("Exception in Reason combo..."+e);
                        }
                        finally
                        {
                        rs.close();
                        ps.close();
                        }   
                      %>
                    </select>
                  </div>
                </td>
          </tr>
          <tr class="table">
            <td width="40%">
              <div align="left">
                   Bill Minor Type <font color="#ff2121">*</font>
              </div>
            </td>
            <td width="60%">
                <table align="left">
                 <tr align="left">
                     <td>
                         <div align="left">
                                <select size="1" name="minorType" id="minorType" onchange="callsub(this.value);">                                            
                                  <option value="">--Select Minor Type--</option>
                                </select>
                         </div>
                      </td>
                 </tr>
               </table>
            </td>
          </tr>
          <tr>
                                    <td class="table" width="40%" align="left">Bill Sub Type<font color="#ff2121">*</font></td>
                                    <td class="table" align="left"> 
                                            <select size="1" name="billsubtype" id="billsubtype"> 
                                                <option value='0'>--Select Sub Type--</option>
                                            </select>
                                    </td>               
          </tr>
         <tr class="table">
          <td>Debit Account Head Code <font color="#ff2121">*</font></td>
          <td>
              <div align="left">
                 <table border="0">
                       <tr> 
                        <td class="table" align="left">
                 
			                    <input type="text" name="txtAcc_HeadCode" 
			                           id="txtAcc_HeadCode" maxlength="6" onkeypress="return numbersonly(event)"
				                         onchange="sixdigit(this.value);" size="9"  onblur="doFunction11('checkCode1');" />
			                    <img src="../../../../../images/c-lovi.gif" width="20" height="20" alt="AccountHeadList" onclick="AccHeadpopup();"></img>
			                    <input type="text" name="txtAcc_HeadDesc" readonly="readonly" 
			                           id="txtAcc_HeadDesc" style="background-color: #ececec"  maxlength="125" size="70"/>
			                  
                </td>
                             <!--<td>
                               <input type="text" name="txtAcc_HeadCode" id="txtAcc_HeadCode" onkeypress="return  numbersonly(event,this);"/>
                            </td>
                            <td>
                                <input type="button" value="Select" name="txtAcc_HeadCodeVal" id="txtAcc_HeadCodeVal" onclick="AccHeadpopup();"/>
                          </td>
                      --></tr>
               </table>       
             </div>
           </td>
        </tr>
        <tr>
            <td class="table">In Use<font> ?</font></td>
            <td class="table">
	            <input type="radio" name="radActive" id="radActive" value="Y" onclick="document.getElementById('trDtTo1').style.display='none';document.getElementById('trDtTo2').style.display='none';" checked>Yes &nbsp;
	            <input type="radio" name="radActive" id="radActive" value="N" onclick="document.getElementById('trDtTo1').style.display='block';document.getElementById('trDtTo2').style.display='block';" >No
            </td>
       </tr>
      
       <tr id="trDtTo" class="table">
            <td class="table"><div id="trDtTo1" style="display:none" >Used Up to Date</div></td>
            <td class="table">
            <div id="trDtTo2" style="display:none">
                            <input type="text" name="ann_date1" id="ann_date1" tabindex="3" 
                                   maxlength="10" size="11"  
                                   onfocus="javascript:vDateType='3';"
                                   onkeypress="return calins(event,this);"
                                   onblur="call_date(this);"/>
                             <img src="../../../../../images/calendr3.gif"
                                 onclick="showCalendarControl(document.AccHead.ann_date1,0);"
                                 alt="Show Calendar"></img>  
             </div>
            </td>
       </tr>
      
       <tr id="trDtTo" style="display='inline'" class="table">
            <td class="table"><div id="trDtTo1">Remarks (100 Characters)</div></td>
            <td class="table">
            <div id="trDtTo2">
            	<textarea name="txtRemarks" id="txtRemarks" cols="60"  onkeypress="return checklength(event,this);"
                              rows="3"></textarea>
            </div>
            </td>
       </tr>
 </table>          
            <div align="center">
                     <table >
                         <tr class="tdH">
                             <td colspan=12>
                            <input type="button" name="cmdAdd" value="ADD" id="cmdAdd" onclick="calling('Add')" tabindex="20"/>
                             </td>
                             <td>
                            <input type="button" name="cmdEdit" value="UPDATE" id="cmdEdit"  onclick="calling('Update')" tabindex="30" disabled/>
                             </td>
                            <td>
                            <input type="button" name="cmdDelete" value="DELETE" id="cmdDelete"  onclick="calling('Delete')" tabindex="40" disabled/>
                             </td>
                             <td>
                            <input type="button" name="cmdList" value="LIST" id="cmdList" onclick="Lists();" tabindex="60"/>
                             </td>
                               <td>
                             <input type="button" name="cmdCancel" value="CANCEL"  id="cmdCancel" onclick="ClearAll()" tabindex="70"/>
                             </td>
                         </tr>
                    </table>
                </div>
      
       
      
        <table align="center" cellspacing="3" cellpadding="2" border="1"
             width="100%">
        <tr class="tdH">
          <td>
            <div align="center">
              <input type="button" id="cmdcancel" name="cancel" value="Exit"
                     onclick="self.close();"></input>
            </div>
          </td>
        </tr>
      </table> 
    </form>
</html>
