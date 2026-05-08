<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ page contentType="text/html;charset=windows-1252"%>
<%@ page import="java.sql.*,java.util.*"%>
<%@ page session="false" contentType="text/html;charset=windows-1252"%>
<%-- <%@ include file="//org/Security/jsps/Check_SessionJSPF.jspf" %>--%>
<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=windows-1252"/>
    <title>Employee Basic Detail View</title>
    <link href="../../../../../css/Sample3.css" rel="stylesheet"
          media="screen"/>
    <script type="text/javascript"
            src="<%=request.getContextPath()%>/org/Library/scripts/comJS.js"></script>
    <script type="text/javascript">
          
//////////////   FOR EMPLOYEE POPUP WINDOW //////////////////////
var winemp;
var my_window;
function servicepopup()
{
    if (winemp && winemp.open && !winemp.closed) 
    {
       winemp.resizeTo(500,600);
       winemp.moveTo(200,200); 
       winemp.focus();
       return ;
    }
    else
    {
        winemp=null
    }
        
    winemp= window.open("../../../../../org/HR/HR1/EmployeeMaster/jsps/EmpServicePopup.jsp","mywindow1","status=1,height=500,width=600,resizable=YES, scrollbars=yes"); 
    winemp.moveTo(250,250);  
    winemp.focus();
    
}


function doParentEmp(emp)
{
document.frmview.txtEmpId.value=emp;
document.frmview.submit();
}

function getTransport()
{
 var req = false;
 try 
 {
       req= new ActiveXObject("Msxml2.XMLHTTP");
 }
 catch (e) 
 {
       try 
       {
            req = new ActiveXObject("Microsoft.XMLHTTP");
       }
       catch (e2) 
       {
            req = false;
       }
 }
 if (!req && typeof XMLHttpRequest != 'undefined') 
 {
       req = new XMLHttpRequest();
 }   
 return req;
}

function validate()
{
    if(document.frmview.txtEmpId.value.length==0)
    {
        alert('Select Employee Id');
        document.frmview.txtEmpId.focus();
        return false;
    }
    else
    {
    
        var strEmpId=document.frmview.txtEmpId.value;
        // startwaiting(document.frmEmployee) ; 
           url="../../../../../InsertEmployee1.con?command=Existgview&EmpId=" + strEmpId;   
                     var req=getTransport();
            req.open("GET",url,true); 
            req.onreadystatechange=function()
            {
                if(req.readyState==4)
                    {
                      if(req.status==200)
                      {            
                          //stopwaiting(document.frmEmployee) ;
                           var baseResponse=req.responseXML.getElementsByTagName("response")[0];
                           var flag=baseResponse.getElementsByTagName("flag")[0].firstChild.nodeValue; 
                           //alert(flag);
                            if(flag=="success")
                            {
                                document.frmview.submit();
                                return true;
                            }
                            else
                            {
                                   // window.location="<%=request.getContextPath()%>/org/Library/jsps/Messenger.jsp?message=" + "Given Employee Id not Found!"; 
                                   alert('Enter a valid Employee Id');
                                   document.frmview.txtEmpId.value="";
                                   document.frmview.txtEmpId.focus();
                                   return;
                           }
                       } 
                    }
         }
        req.send(null);
    }
}

function numbersonly1(e,t)
    {
       var unicode=e.charCode? e.charCode : e.keyCode;
       if(unicode==13)
        {
          try{t.blur();}catch(e){}
          return true;
        
        }
        if (unicode!=8 && unicode !=9  )
        {
            if (unicode<48||unicode>57 ) 
                return false 
        }
     }
     
     

window.onunload=function()
{
if (winemp && winemp.open && !winemp.closed) winemp.close();
}

</script>
  </head>
  <body class="table" onload="document.frmview.reset()">
  <form name="frmview" method="post" action="">
  <%
      
     
     if(request.getParameter("txtEmpId")==null)
     {
%>

         <table border="1px" width="100%" align="center">
              <tr>
                <td align="left" class="tdH" colspan="2">
                  <center>
                    <b>Employee&nbsp;Profile </b>
                  </center>
                </td>
              </tr>
              <tr>
                <td >Employee Id:</td>
                <td  valign="center">
                  <table border="0" align="left">
                    <tr>
                      <td>
                        <input tabindex="1" type="text" name="txtEmpId"      id="txtEmpId" maxlength="5" value=""
                               onkeypress="return  numbersonly1(event,this)"  onchange="validate()"></input>
                        <img src="../../../../../images/c-lovi.gif" width="20"
                             height="20" alt="empList"       onclick="servicepopup();"></img>
                      </td>
                      <td>
                         <img align="top"       src="../../../../../images/sample_emp.bmp"
                             alt="Photo" width="90" height="90" id="EmpImage"></img>
                       </td>
                    </tr>
                  </table>
                </td>
              </tr>
              <tr>
                <td >Employee Name</td>
                <td ><b>&nbsp;</b> </td>
              </tr>
              <tr>
                <td  colspan="2" class="tdH">
                  <b><font face="Tahoma" color="#000000" size="2">Designation and Office Details</font></b>
                </td>
              </tr>
              <tr>
                <td >Designation </td>
                <td >&nbsp;</td>
              </tr>
              <tr>
                <td >Grade </td>
                <td >&nbsp; </td>
              </tr>
              <tr>
                <td >Office </td>
                <td >&nbsp; </td>
              </tr>
              <tr>
                <td >Date of Current Posting</td>
                <td >&nbsp;  </td>
              </tr>
              <tr>
                <td >Current Status</td>
                <td >&nbsp;</td>
              </tr>
              <tr>
                <td colspan="2" class="tdH">
                  <b><font face="Tahoma" color="#000000" size="2">General Particulars</font></b>
                </td>
              </tr>
              <tr>
                <td >Date Of Birth </td>
                <td >&nbsp; </td>
              </tr>
              <tr>
                <td >Gender </td>
                <td >&nbsp; </td>
              </tr>
              <tr>
                <td >Community </td>
                <td >&nbsp;</td>
              </tr>
              <tr>
                <td >Native&nbsp;District</td>
                <td >&nbsp;</td>
              </tr>
              <tr>
                <td >Native Taluk Name</td>
                <td >&nbsp;</td>
              </tr>
              <tr>
                <td >Employment&nbsp;Status</td>
                <td >&nbsp;</td>
              </tr>
              <tr>
                <td >GPF Number</td>
                <td >&nbsp; </td>
              </tr>
              <tr>
                <td >Date&nbsp;of&nbsp;Joining in TWAD Board (as per SR)</td>
                <td >&nbsp;</td>
              </tr>
              <tr>
                <td >Designation&nbsp;at&nbsp;the&nbsp;Time&nbsp;of&nbsp;Joining&nbsp;(as per SR) </td>
                <td >&nbsp;</td>
              </tr>
              <tr>
                <td >Marital Status</td>
                <td >&nbsp;</td>
              </tr>
              <tr>
                <td >Remarks</td>
                <td >&nbsp; </td>
              </tr>
              
              <tr>
                <td colspan="2" class="tdH" align="right">
                  <input type="BUTTON" value=" Ok " onclick="self.close();"></input>
                  <input type="Button" value=" Print "
                         onclick="window.print();"></input>
                </td>
              </tr>
            </table>
 <%
    }
    else
    {
        String strEmpId=request.getParameter("txtEmpId");
        int empid=Integer.parseInt(strEmpId);
        Connection con=null;
        Statement statement=null;
        PreparedStatement ps=null;
        ResultSet rs=null;
        String temp="",strCommunity="",strDist="",strTaluk="",strGpf="",strDef="",strCurStatus="",strDoj="";
        String strEmpPrefix="",strEmpInitial="",strOfficeId="",strOfficeName="",strSRDesignation="",strOffice_Grade="",strDob="";
        String strEmpName="",strGender="",strDesignation="",strMarital="",strRemarks="",strEmpStatus="",DeptId="",OffName="",OffDName="";
        String EmpImage="../../../../../images/sample_emp.bmp";
        int gpfno=0; 
         String img="";
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
                 
                
                String imagepath=rs1.getString("Config.EMPLOYEE_PHOTOS_PATH");
                String NewImage="";
                System.out.println("imaghe path : " + imagepath);
                String context=request.getContextPath();
                imagepath=rs1.getString("Config.EMPLOYEE_PHOTOS_PATH_VIEW");
                System.out.println("image path after is:"+imagepath);
                img=context+imagepath;
        }
        catch(Exception e)
        {
                System.out.println("Connection Error:"+e);
        }
              
        try{
                String sql="select a.EMPLOYEE_NAME,a.EMPLOYEE_INITIAL,a.GPF_NO,a.DATE_OF_BIRTH,a.GENDER,"
                +"a.REMARKS,a.MARITAL_STATUS,b.COMMUNITY_NAME,c.District_Name,d.Taluk_Name,e.EMPLOYMENT_STATUS, "
                +"f.EMP_PHOTO_FILE_NAME "
                +" from HRM_MST_EMPLOYEES a"
                +" left outer join HRM_MST_COMMUNITY b on to_number(b.COMMUNITY_CODE)=to_number(a.COMMUNITY_ID) "
                +" left outer join COM_MST_DISTRICTS c on c.District_Code=a.NATIVE_DISTRICT_CODE "
                +" left outer join COM_MST_TALUKS d on d.Taluk_Code=a.NATIVE_TALUK_CODE "
                +" left outer join HRM_MST_EMPLOYMENT_STATUS e on e.EMPLOYMENT_STATUS_ID=a.EMPLOYMENT_STATUS_ID "
                +" left outer join hrm_emp_addl_details f on f.EMPLOYEE_ID=a.EMPLOYEE_ID "
                +" where a.EMPLOYEE_ID=?";
               
                ps=con.prepareStatement(sql);
                ps.setInt(1,empid);
                rs=ps.executeQuery();
               
                while(rs.next())
                {
                    strEmpName=rs.getString("EMPLOYEE_NAME");
                    if(rs.getString("EMPLOYEE_INITIAL")!=null)
                    {
                            strEmpName=rs.getString("EMPLOYEE_INITIAL")+"."+strEmpName;
                    }
                    System.out.println("GPF No:"+rs.getInt("GPF_NO"));
                    gpfno=rs.getInt("GPF_NO");
                    if(gpfno!=0)
                    {
                        strGpf=String.valueOf(gpfno);
                    }
                    if(rs.getDate("DATE_OF_BIRTH")!=null)
                    {
                        String[] sd=rs.getDate("DATE_OF_BIRTH").toString().split("-");
                        strDob=sd[2]+"/"+sd[1]+"/"+sd[0];
                    }
                    if(rs.getString("REMARKS")!=null){
                        strRemarks=rs.getString("REMARKS");
                    }
                    if(rs.getString("MARITAL_STATUS")!=null){
                        strMarital=rs.getString("MARITAL_STATUS");
                        if(strMarital.equalsIgnoreCase("M"))
                            strMarital="Married";
                        else
                            strMarital="Single";
                    }
                    if(rs.getString("GENDER")!=null){
                        strGender=rs.getString("GENDER");
                    }
                    System.out.println("community:"+rs.getString("COMMUNITY_NAME"));
                     if(rs.getString("COMMUNITY_NAME")!=null){
                        strCommunity=rs.getString("COMMUNITY_NAME");
                    }
                    if(rs.getString("District_Name")!=null){
                            strDist=rs.getString("District_Name");
                    }
                    if(rs.getString("Taluk_Name")!=null){
                            strTaluk=rs.getString("Taluk_Name");
                    }
                    if(rs.getString("EMPLOYMENT_STATUS")!=null){
                        strEmpStatus=rs.getString("EMPLOYMENT_STATUS");
                    }
                     if(rs.getString("EMP_PHOTO_FILE_NAME")!=null){
                        EmpImage=rs.getString("EMP_PHOTO_FILE_NAME");
                        EmpImage=img+EmpImage;
                    }
                    
                    
                    
                }
                
        
        }
        catch(Exception e)
        {
                System.out.println("Error in Employee Basic 1:"+e);
        }

        try{
        
                String sql="select a.DATE_EFFECTIVE_FROM,a.OFFICE_GRADE,b.OFFICE_NAME,c.DESIGNATION,"
                +" d.EMPLOYEE_STATUS_DESC "
                +" from HRM_EMP_CURRENT_POSTING a "
                +" left outer join COM_MST_OFFICES b on b.OFFICE_ID=a.OFFICE_ID "
                +" left outer join HRM_MST_DESIGNATIONS c on c.DESIGNATION_ID=a.DESIGNATION_ID "
                +" left outer join HRM_MST_EMPLOYEE_STATUS d on d.EMPLOYEE_STATUS_ID = a.EMPLOYEE_STATUS_ID "
                +" where a.EMPLOYEE_ID=?";
               //System.out.println(sql);
                ps=con.prepareStatement(sql);
                ps.setInt(1,empid);
                rs=ps.executeQuery();
                while(rs.next())
                {
                        System.out.println("current posting is available");
                        if(rs.getDate("DATE_EFFECTIVE_FROM")!=null)
                        {
                            String[] sd=rs.getDate("DATE_EFFECTIVE_FROM").toString().split("-");
                            strDef=sd[2]+"/"+sd[1]+"/"+sd[0];
                        }
                        
                        if(rs.getString("OFFICE_GRADE")!=null){
                            strOffice_Grade=rs.getString("OFFICE_GRADE");
                        }
                         if(rs.getString("OFFICE_NAME")!=null){
                            OffName=rs.getString("OFFICE_NAME");
                        }
                         if(rs.getString("DESIGNATION")!=null){
                            strDesignation=rs.getString("DESIGNATION");
                        }
                         if(rs.getString("EMPLOYEE_STATUS_DESC")!=null){
                            strCurStatus=rs.getString("EMPLOYEE_STATUS_DESC");
                        }
                        
                        
                
                }
        }
        catch(Exception e)
        {
                System.out.println("Error in Current  Posting 2:"+e);
        }
        
         try{
        
                String sql="select b.DATE_FROM ,c.DESIGNATION"
                +" from HRM_EMP_SERVICE_DATA b"
                 +" left outer join HRM_MST_DESIGNATIONS c on c.DESIGNATION_ID=b.DESIGNATION_ID "
                +" where EMPLOYEE_ID=? and  b.DATE_FROM = (select min(a.DATE_FROM) from HRM_EMP_SERVICE_DATA a  where a.EMPLOYEE_ID=?)";
               //System.out.println(sql);
                ps=con.prepareStatement(sql);
                ps.setInt(1,empid);
                ps.setInt(2,empid);
                rs=ps.executeQuery();
                while(rs.next())
                {
                        System.out.println("SR is available");
                         if(rs.getDate("DATE_FROM")!=null)
                        {
                            String[] sd=rs.getDate("DATE_FROM").toString().split("-");
                            strDoj=sd[2]+"/"+sd[1]+"/"+sd[0];
                        }
                        if(rs.getString("DESIGNATION")!=null){
                            strSRDesignation=rs.getString("DESIGNATION");
                        }
                        
                
                }
        }
        catch(Exception e)
        {
                System.out.println("Error in Service Record 3:"+e);
        }
        
    
%>


    <table border="1px" width="100%" align="center">
              <tr>
                <td align="left" class="tdH" colspan="2">
                  <center>
                    <b>Employee&nbsp;Profile </b>
                  </center>
                </td>
              </tr>
              <tr>
                <td >Employee Id:</td>
                <td  valign="center">
                  <table border="0" align="left">
                    <tr>
                      <td>
                        <input tabindex="1" type="text" name="txtEmpId"      id="txtEmpId" maxlength="5" value="<%=strEmpId%>"
                               onkeypress="return  numbersonly1(event,this)"  onchange="validate()"></input>
                        <img src="../../../../../images/c-lovi.gif" width="20"
                             height="20" alt="empList"       onclick="servicepopup();"></img>
                      </td>
                      <td>
                         <img align="top"       src="<%=EmpImage%>"
                             alt="Photo" width="90" height="90" id="EmpImage"></img>
                       </td>
                    </tr>
                  </table>
                </td>
              </tr>
              <tr>
                <td >Employee Name</td>
                <td ><b><%=strEmpName%>&nbsp;</b> </td>
              </tr>
              <tr>
                <td  colspan="2" class="tdH">
                  <b><font face="Tahoma" color="#000000" size="2">Designation and Office Details</font></b>
                </td>
              </tr>
              <tr>
                <td >Designation </td>
                <td ><%=strDesignation%>&nbsp;</td>
              </tr>
              <tr>
                <td >Grade </td>
                <td ><%=strOffice_Grade%>&nbsp; </td>
              </tr>
              <tr>
                <td >Office </td>
                <td ><%=OffName%>&nbsp; </td>
              </tr>
              <tr>
                <td >Date of Current Posting</td>
                <td ><%=strDef%>&nbsp;  </td>
              </tr>
               <tr>
                <td >Current Status</td>
                <td ><%=strCurStatus%>&nbsp;</td>
              </tr>
              <tr>
                <td colspan="2" class="tdH">
                  <b><font face="Tahoma" color="#000000" size="2">General Particulars</font></b>
                </td>
              </tr>
              <tr>
                <td >Date Of Birth </td>
                <td ><%=strDob%>&nbsp; </td>
              </tr>
              <tr>
                <td >Gender </td>
                <td ><%=strGender%>&nbsp; </td>
              </tr>
              <tr>
                <td >Community </td>
                <td ><%=strCommunity%>&nbsp;</td>
              </tr>
              <tr>
                <td >Native&nbsp;District</td>
                <td ><%=strDist%>&nbsp;</td>
              </tr>
              <tr>
                <td >Native Taluk Name</td>
                <td ><%=strTaluk%>&nbsp;</td>
              </tr>
              <tr>
                <td >Employment&nbsp;Status</td>
                <td ><%=strEmpStatus%>&nbsp;</td>
              </tr>
              <tr>
                <td >GPF Number</td>
                <td ><%=strGpf%>&nbsp; </td>
              </tr>
              <tr>
                <td >Date&nbsp;of&nbsp;Joining in TWAD Board (as per SR)</td>
                <td ><%=strDoj%>&nbsp;</td>
              </tr>
              <tr>
                <td >Designation&nbsp;at&nbsp;the&nbsp;Time&nbsp;of&nbsp;Joining&nbsp;(as per SR) </td>
                <td ><%=strSRDesignation%>&nbsp;</td>
              </tr>
              <tr>
                <td >Marital Status</td>
                <td ><%=strMarital%>&nbsp;</td>
              </tr>
              <tr>
                <td >Remarks</td>
                <td ><%=strRemarks%>&nbsp; </td>
              </tr>
             
              <tr>
                <td colspan="2" class="tdH" align="right">
                  <input type="BUTTON" value=" Ok " onclick="self.close();"></input>
                  <input type="Button" value=" Print "
                         onclick="window.print();"></input>
                </td>
              </tr>
            </table>
<%
        }
%>
            
     </form></body>
</html>