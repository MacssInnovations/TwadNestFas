 <!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">
<%@ page contentType="text/html;charset=windows-1252"%>
<%@ page import="java.sql.*,java.util.*,Servlets.Security.classes.UserProfile"%>
<%@ page session="false" contentType="text/html;charset=windows-1252"%>
<%@ include file="//org/Security/jsps/Check_SessionJSPF.jspf" %>
<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=windows-1252"/>
    <title>WQS_SampleResultJSP</title>
    <link href="../../../../../../css/Sample3.css" rel="stylesheet" media="screen"/>
    <script language="javascript" src="../scripts/WQS_sampleResultJS.js" type="text/javascript"></script>
    <link href="../../../../../../css/CalendarControl.css" rel="stylesheet" media="screen"/>    
    <script type="text/javascript" src="<%=request.getContextPath()%>/org/Library/scripts/CalendarControl.js"></script>        
    <script type="text/javascript" src="../HR/HR1/EmployeeMaster/scripts/CalendarControl.js"></script>  
  </head>
  <body>
   <form name="SampleResult">
     <%
        Connection con=null;
        Statement stmt=null;
        ResultSet rs=null;
        PreparedStatement ps=null;
        String xml=null;
        String odt="",lb="",did="",dspec="",dist="",bcode="",bdesc="";   
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
                       try
                       {
                            stmt=con.createStatement();
                            con.clearWarnings();
                       }
                       catch(SQLException e)
                       {
                            System.out.println("Exception in creating statement:"+e);
                       }
                        stmt=con.createStatement();
           }
           catch(Exception e)
           {
                System.out.println("Exception in opening connection:"+e);
           }  
           
                HttpSession session=request.getSession(false);
                UserProfile empProfile=(UserProfile)session.getAttribute("UserProfile");
                  
                System.out.println("user id::"+empProfile.getEmployeeId());
                int empid=empProfile.getEmployeeId();
                int  oid=0,odidt=0;
                
                try
                {
           
                    ps=con.prepareStatement("select OFFICE_ID from HRM_EMP_CURRENT_POSTING where EMPLOYEE_ID=?");
                    ps.setInt(1,empid);
                    rs=ps.executeQuery();
                    if(rs.next()) 
                    {
                            oid=rs.getInt("OFFICE_ID");
                        
                    }
                    rs.close();
                    ps.close();
                    ps=con.prepareStatement("select LAB_CODE,LAB_DESC from WQS_MST_LAB where LAB_CODE=?");
                    ps.setInt(1,oid);
                    rs=ps.executeQuery();
                    if(rs.next()) 
                    {
                        odidt=Integer.parseInt(rs.getString("LAB_CODE"));
                        odt=rs.getString("LAB_DESC");
                        lb=odidt+"--"+odt;
                        System.out.println(lb);
                    }
                    rs.close();
                    ps.close();
                   /* ps=con.prepareStatement("select b.district_code,b.districtname from(select office_id,district_code from com_mst_offices)a inner join(select distcode_nic,district_code,districtname from com_mst_districts)b on a.district_code=b.distcode_nic where a.office_id=?");
                    ps.setInt(1,oid);
                    rs=ps.executeQuery();
                    if(rs.next())
                    {
                        did=rs.getString("district_code");
                        System.out.println("district code:"+did);
                        dspec=rs.getString("districtname");
                        dist=did+"--"+dspec;
                        System.out.println("District:"+dist);
                    }*/
                }
                catch(Exception e)
                {
                    System.out.println(e);
                }
  %>
   <table cellspacing="2" cellpadding="3" border="1" width="100%" align="center">
       <tr>
            <td colspan="2" class="tdH" align="center"><b>Sample Result</b></td>                   
       </tr> 
        <tr>
            <td class="table" align="left" width="49%">Laboratory</td>
            <td class="table" align="left" width="51%">
            <input type="text" name="lab" id="lab"  value="<%=lb%>" size="40" disabled="true">
            </td>
        </tr>
        <tr>
            <td class="table" align="left" width="49%">Customer Id</td>
            <td class="table" align="left" width="51%">
            <input type="text" name="cid" id="cid" size="40" onblur="changeCid()">
            </td>
        </tr>
         <tr>
            <td class="table" align="left" width="49%">Customer Name</td>
            <td class="table" align="left" width="51%">
            <input type="text" name="cname" id="cname" size="40" disabled="true">            
            </td>
        </tr>
         <tr>
            <td class="table" align="left" width="49%">Customer Name</td>
            <td class="table" align="left" width="51%">           
            <input type="text" name="ctype" id="ctype" disabled="true">
            </td>
        </tr>
        <tr>
            <td class="table" width="50%">Customer Reference Number</td>
            <td class="table" width="50%">
                 <input type="text" name="rno" id="rno" onchange="changeReference()">
            </td>
        </tr> 
        <tr>
            <td class="table" align="left" width="49%">Sample Number</td>
            <td class="table" align="left" width="51%">
                <input type="text" name="sno" id="sno" onchange="changeSample()">
            </td>
        </tr> 
        <tr>
            <td class="table" align="left" width="49%">Sample Collection Date</td>
            <td class="table" align="left" width="51%">
                <input type="text" name="sdate" id="sdate">
                 <img src="../../../../../../images/calendr3.gif" onclick="showCalendarControl(document.SampleResult.sdate);" alt="Show Calendar" id="pur_date_cal"></img>                           
            </td>
        </tr> 
        <tr>
            <td class="table" align="left" width="49%">Sample Receipt Date</td>
            <td class="table" align="left" width="51%">
                <input type="text" name="rdate" id="rdate">
                 <img src="../../../../../../images/calendr3.gif" onclick="showCalendarControl(document.SampleResult.rdate);" alt="Show Calendar" id="pur_date_cal"></img>                           
        </td>
        </tr>                     
        <tr>
            <td class="table" width="50%">Resurvey District</td>
            <td class="table" width="50%">
                <input type="text" name="dcode" id="dcode" readonly="readonly">
                <input type="hidden" name="dc" id="dc"></input>
            </td>
        </tr>         
        <tr>
            <td class="table" width="50%">Resurvey Block</td>
            <td class="table" width="50%">
                <input type="text" name="bcode" id="bcode" readonly="readonly">
                 <input type="hidden" name="bc" id="bc"></input>
            </td>
        </tr>         
         <tr>
            <td class="table" width="50%">Resurvey Panchayat</td>
            <td class="table" width="50%">
                <input type="text" name="pancode" id="pancode" readonly="readonly">
                <input type="hidden" name="pac" id="pac"></input>
            </td>
        </tr>         
         <tr>
            <td class="table" width="50%">Resurvey Habitation</td>
            <td class="table" width="50%">
                <input type="text" name="hcode" id="hcode" readonly="readonly">
                <input type="hidden" name="hc" id="hc"></input>
            </td>
        </tr> 
         <tr>
            <td class="table" width="50%">Programme</td>
            <td class="table" width="50%">
                <input type="text" name="pcode" id="pcode" readonly="readonly" size="45"></input>
                <input type="hidden" name="pc" id="pc"></input>
            </td>
        </tr> 
        <tr>
            <td class="table" width="50%">Scheme Type</td>
            <td class="table" width="50%">
                <input type="text" name="stype" id="stype" readonly="readonly">
                 <input type="hidden" name="sti" id="sti"></input>
            </td>
        </tr> 
        <tr>
            <td class="table" width="50%">Source Type</td>
            <td class="table" width="50%">
                <select name="srtype" id="srtype">
                <option value=0>--Select Source Type--</option>
                <%
                    rs=stmt.executeQuery("select WATER_SOURCE_TYPE_ID,WATER_SOURCE_TYPE from RWS_MST_WATER_SOURCE_TYPE");
                    while(rs.next())
                    {
                        System.out.println(rs.getString("WATER_SOURCE_TYPE"));
                        out.println("<option value='"+rs.getString("WATER_SOURCE_TYPE_ID")+"'>"+rs.getString("WATER_SOURCE_TYPE")+"<option>");
                    }
                %>
                </select>
                 <input type="hidden" name="wst" id="wst"></input>
            </td>
        </tr> 
        <tr>
            <td class="table" width="50%">Source code</td>
            <td class="table" width="50%">
                <input type="text" name="scode" id="scode" readonly="readonly">
                <input type="hidden" name="sco" id="sco">
            </td>
        </tr>
        <tr>
            <td class="table" width="50%">Source District</td>
            <td class="table" width="50%">
                <input type="text" name="dname" id="dname" disabled="disabled">
            </td>
        </tr> 
         <tr>
            <td class="table" width="50%">Source Panchayat/Town</td>
            <td class="table" width="50%">
                <input type="text" name="pname" id="pname" disabled="disabled">
            </td>
        </tr> 
         <tr>
            <td class="table" width="50%">Source Habitation</td>
            <td class="table" width="50%">
                <input type="text" name="hname" id="hname" disabled="disabled">
            </td>
        </tr> 
        <tr>
            <td class="table" width="50%">Location</td>
            <td class="table" width="50%">
                <input type="text" name="location" id="location" size=70>
         </td>
        </tr> 
        <tr>
            <td class="table" width="50%">Appearance</td>
            <td class="table" width="50%">
                <input type="text" name="appear" id="appear" onkeypress="noEnter(event)"></input>
            </td>
        </tr> 
        <tr>
            <td class="table" width="50%">Colour in Pt/Co scale</td>
            <td class="table" width="50%">
                <input type="text" name="color" id="color" onblur="this.value=checkString(this.value)"></input>
            </td>
        </tr> 
        <tr>
            <td class="table" width="50%">Odour</td>
            <td class="table" width="50%">
                <input type="text" name="odour" id="odour"></input>
            </td>
        </tr> 
        <tr>
            <td class="table" width="50%">Turbidity</td>
            <td class="table" width="50%">
                <input type="text" name="turb" id="turb" onblur="this.value=checkString(this.value)"></input>
            </td>
        </tr> 
        <tr>
            <td class="table" width="50%">Total Solids</td>
            <td class="table" width="50%">
                <input type="text" name="solid" id="solid" onblur="this.value=checkString(this.value)"></input>
            </td>
        </tr> 
        <tr>
            <td class="table" width="50%">Total Suspended Solids</td>
            <td class="table" width="50%">
                <input type="text" name="tss" id="tss" onblur="this.value=checkString(this.value)"></input>
            </td>
        </tr> 
        <tr>
            <td class="table" width="50%">Total Dissolved Solids</td>
            <td class="table" width="50%">
                <input type="text" name="tds" id="tds" onblur="this.value=checkString(this.value)"></input>
            </td>
        </tr> 
         <tr>
            <td class="table" width="50%">Electrical Conductivity</td>
            <td class="table" width="50%">
                <input type="text" name="ec" id="ec" onblur="this.value=checkString(this.value)"></input>
            </td>
        </tr> 
        <tr>
            <td class="table" width="50%">pH</td>
            <td class="table" width="50%">
                <input type="text" name="ph" id="ph" onblur="this.value=checkString(this.value)"></input>
            </td>
        </tr> 
        <tr>
            <td class="table" width="50%">ACIDITY</td>
            <td class="table" width="50%">
                <input type="text" name="acidity" id="acidity" onblur="this.value=checkString(this.value)"></input>
            </td>
        </tr> 
        <tr>
            <td class="table" width="50%">PhALK</td>
            <td class="table" width="50%">
                <input type="text" name="phalk" id="phalk" onblur="this.value=checkString(this.value)"></input>
            </td>
        </tr> 
        <tr>
            <td class="table" width="50%">TALK</td>
            <td class="table" width="50%">
                <input type="text" name="talk" id="talk" onblur="this.value=checkString(this.value)"></input>
            </td>
        </tr> 
        <tr>
            <td class="table" width="50%">Total Hardness</td>
            <td class="table" width="50%">
                <input type="text" name="th" id="th" onblur="this.value=checkString(this.value)"></input>
            </td>
        </tr> 
        <tr>
            <td class="table" width="50%">Calcium</td>
            <td class="table" width="50%">
                <input type="text" name="ca" id="ca" onblur="this.value=checkString(this.value)"></input>
            </td>
        </tr> 
        <tr>
            <td class="table" width="50%">Magnesium</td>
            <td class="table" width="50%">
                <input type="text" name="mg" id="mg" onblur="this.value=checkString(this.value)"></input>
            </td>
        </tr> 
        <tr>
            <td class="table" width="50%">Sodium</td>
            <td class="table" width="50%">
                <input type="text" name="na" id="na" onblur="this.value=checkString(this.value)"></input>
            </td>
        </tr> 
        <tr>
            <td class="table" width="50%">Potassium</td>
            <td class="table" width="50%">
                <input type="text" name="potassium" id="potassium" onblur="this.value=checkString(this.value)"></input>
            </td>
        </tr> 
        <tr>
            <td class="table" width="50%">Iron</td>
            <td class="table" width="50%">
                <input type="text" name="fe" id="fe" onblur="this.value=checkString(this.value)"></input>
            </td>
        </tr> 
        <tr>
            <td class="table" width="50%">Manganese</td>
            <td class="table" width="50%">
                <input type="text" name="mn" id="mn" onblur="this.value=checkString(this.value)"></input>
            </td>
        </tr> 
        <tr>
            <td class="table" width="50%">Ammonia</td>
            <td class="table" width="50%">
                <input type="text" name="nh3" id="nh3" onblur="this.value=checkString(this.value)"></input>
            </td>
        </tr> 
        <tr>
            <td class="table" width="50%">Nitrite</td>
            <td class="table" width="50%">
                <input type="text" name="no2" id="no2" onblur="this.value=checkString(this.value)"></input>
            </td>
        </tr> 
        <tr>
            <td class="table" width="50%">Nitrate</td>
            <td class="table" width="50%">
                <input type="text" name="no3" id="no3" onblur="this.value=checkString(this.value)"></input>
            </td>
        </tr> 
        <tr>
            <td class="table" width="50%">Chloride</td>
            <td class="table" width="50%">
                <input type="text" name="cl" id="cl" onblur="this.value=checkString(this.value)"></input>
            </td>
        </tr> 
        <tr>
            <td class="table" width="50%">Fluoride</td>
            <td class="table" width="50%">
                <input type="text" name="fluoride" id="fluoride" onblur="this.value=checkString(this.value)"></input>
            </td>
        </tr> 
        <tr>
            <td class="table" width="50%">Sulphate</td>
            <td class="table" width="50%">
                <input type="text" name="so4" id="so4" onblur="this.value=checkString(this.value)"></input>
            </td>
        </tr> 
        <tr>
            <td class="table" width="50%">Phosphate</td>
            <td class="table" width="50%">
                <input type="text" name="po4" id="po4" onblur="this.value=checkString(this.value)"></input>
            </td>
        </tr> 
        <tr>
            <td class="table" width="50%">TIDYS</td>
            <td class="table" width="50%">
                <input type="text" name="tidys" id="tidys" onblur="this.value=checkString(this.value)"></input>
            </td>
        </tr> 
        <tr>
            <td class="table" width="50%">Silica</td>
            <td class="table" width="50%">
                <input type="text" name="sio2" id="sio2" onblur="this.value=checkString(this.value)"></input>
            </td>
        </tr> 
        <tr>
            <td class="table" width="50%">Total Kjeldhal Nitrogen</td>
            <td class="table" width="50%">
                <input type="text" name="tkn" id="tkn" onblur="this.value=checkString(this.value)"></input>
            </td>
        </tr> 
        <tr>
            <td class="table" width="50%">DO</td>
            <td class="table" width="50%">
                <input type="text" name="dl" id="dl" onblur="this.value=checkString(this.value)"></input>
            </td>
        </tr>
        <tr>
            <td class="table" width="50%">BOD</td>
            <td class="table" width="50%">
                <input type="text" name="bod" id="bod" onblur="this.value=checkString(this.value)"></input>
            </td>
        </tr>
        <tr>
            <td class="table" width="50%">COD</td>
            <td class="table" width="50%">
                <input type="text" name="cod" id="cod" onblur="this.value=checkString(this.value)"></input>
            </td>
        </tr>
         <tr>
            <td class="table" width="50%">Oil and Grease</td>
            <td class="table" width="50%">
                <input type="text" name="oil" id="oil" onblur="this.value=checkString(this.value)"></input>
            </td>
        </tr>
         <tr>
            <td class="table" width="50%">Residual Chlorine</td>
            <td class="table" width="50%">
                <input type="text" name="residual" id="residual" onblur="this.value=checkString(this.value)"></input>
            </td>
        </tr> 
        <tr>
            <td class="table" width="50%">Cyanide</td>
            <td class="table" width="50%">
                <input type="text" name="cyanide" id="cyanide" onblur="this.value=checkString(this.value)"></input>
            </td>
        </tr> 
        <tr>
            <td class="table" width="50%">Arsenic</td>
            <td class="table" width="50%">
                <input type="text" name="ag" id="ag" onblur="this.value=checkString(this.value)"></input>
            </td>
        </tr> 
        <tr>
            <td class="table" width="50%">Cadmium</td>
            <td class="table" width="50%">
                <input type="text" name="cd" id="cd" onblur="this.value=checkString(this.value)"></input>
            </td>
        </tr> 
        <tr>
            <td class="table" width="50%">Copper</td>
            <td class="table" width="50%">
                <input type="text" name="cu" id="cu" onblur="this.value=checkString(this.value)"></input>
            </td>
        </tr> 
        <tr>
            <td class="table" width="50%">Lead</td>
            <td class="table" width="50%">
                <input type="text" name="pb" id="pb" onblur="this.value=checkString(this.value)"></input>
            </td>
        </tr> 
       
        <tr>
            <td class="table" width="50%">Chromium</td>
            <td class="table" width="50%">
                <input type="text" name="cr" id="cr" onblur="this.value=checkString(this.value)"></input>
            </td>
        </tr> 
        <tr>
            <td class="table" width="50%">Zinc</td>
            <td class="table" width="50%">
                <input type="text" name="zn" id="zn" onblur="this.value=checkString(this.value)"></input>
            </td>
        </tr> 
        <tr>
            <td class="table" width="50%">Aluminium</td>
            <td class="table" width="50%">
                <input type="text" name="aluminium" id="aluminium" onblur="this.value=checkString(this.value)"></input>
            </td>
        </tr>
         <tr>
            <td class="table" width="50%">Standard Plate Counts</td>
            <td class="table" width="50%">
                    <input type="text" name="spc" id="spc" onblur="this.value=checkString(this.value)"></input>
            </td>
        </tr> 
        <tr>
            <td class="table" width="50%">Total Coliform Counts</td>
            <td class="table" width="50%">
                <input type="text" name="tc" id="tc" onblur="this.value=checkString(this.value)"></input>
            </td>
        </tr>
        <tr>
            <td class="table" width="50%">Fecal Coliform Counts</td>
            <td class="table" width="50%">
                <input type="text" name="fc" id="fc" onblur="this.value=checkString(this.value)"></input>
            </td>
        </tr>
        <tr>
            <td class="table" width="50%">Fecal Streptococci Counts</td>
            <td class="table" width="50%">
                <input type="text" name="fsc" id="fsc" onblur="this.value=checkString(this.value)"></input>
            </td>
        </tr>
        <tr>
            <td class="table" width="50%">C/NC</td>
            <td class="table" width="50%">
                <input type="text" name="cnc" id="cnc"></input>
            </td>
        </tr>
         <tr>
            <td class="table" width="50%">P/NP</td>
            <td class="table" width="50%">
                <input type="text" name="pnp" id="pnp"></input>
            </td>
        </tr>
        <tr>
            <td class="table" width="50%">Reason <font color="Gray">&nbsp;&nbsp;&nbsp;&nbsp;[maxmimum 700 Characters]</font></td>
            <td class="table" width="50%">
                <textarea cols="60" rows="2" name="reason" id="reason" onkeypress="return checklength(event,this)"></textarea>
            </td>
        </tr>
        <tr class="tdH">
          <td colspan="2" align="center">
            <input type="button" name="add" value="Add" id="add" onclick="callServer('Add',null)">
            <input type="button" name="del" value="Delete" id="del" onclick="callServer('Del',null)"/>
            <input type="button" name="update" value="Update" id="update" onclick="callServer('Update',null)"/>
            <input type="button" name="clear" value="Clear" id="clear" onclick="clearAll()"/>
            <input type="button" name="exit" value="Cancel" id="exit" onclick="javascript:self.close();"/>
          </td>
        </tr>
    </table>
  </form>
  </body>
</html>