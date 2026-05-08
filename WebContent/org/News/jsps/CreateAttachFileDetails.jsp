<%@ page contentType="text/html;charset=windows-1252"%>
<%@ page import="java.sql.*,java.util.*"%>
<%@ page session="false" contentType="text/html;charset=windows-1252"%>
<%@ include file="//org/Security/jsps/Check_SessionJSPF.jspf" %>



<html>
  <head>
      <script type="text/javascript" src="<%=request.getContextPath()%>/org/Library/scripts/comJS.js"></script>

    <meta http-equiv="Content-Type" content="text/html; charset=windows-1252"/>
    <title>Upload Attach File Details</title>    
   
    <script type="text/javascript" src="../scripts/CreateAttachFileDetailsJS.js"></script>
    
    
    <script language="javascript" type="text/javascript">
                function closeWindow()
                {                
                    window.open('','_parent',''); 
                    window.close(); 
                    var w=window.open(window.location.href,"_self");
                    w.close();
                    window.opener.focus();
                }
                function toExit()
                {
                 
                var w=window.open(window.location.href,"_self");
                w.close();
                }
                
                
    function check() 
    {
        var ext = document.frmCreateNews.txtattachFile.value;
        var dot=ext.lastIndexOf('.');
        
        ext1 = ext.substring(ext.length-3,ext.length);
        ext2 = ext1.toLowerCase();
        
    
        if((ext2 != 'pdf')&&(ext2!='xls')&&(ext2!='doc')) 
         {
                alert('You have selected a .'+ext2+' file; Please select a pdf,doc or xls file instead!');
                return false; 
         }
        else
              {
                  loadFileUpload();
                  return true;
                 
                }
    }
    </script>
    <link href='../../../css/Sample3.css' rel='stylesheet' media='screen'/>
    <link href="../../../css/CalendarControl.css" rel="stylesheet" media="screen"/> 
  </head>
  <body class="table">
  <%
   Connection connection=null;
   Statement statement=null;
   ResultSet results=null;   

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
    ConnectionString = strdsn.trim() + "://" + strhostname.trim() + ":" + strportno.trim() + "/" +strsid.trim() ;    // Postgres DB  Connection
    //ConnectionString = strdsn.trim() + "@" + strhostname.trim() + ":" + strportno.trim() + ":" +strsid.trim() ;

     Class.forName(strDriver.trim());
     connection=DriverManager.getConnection(ConnectionString,strdbusername.trim(),strdbpassword.trim());

       
       try
       {
            statement=connection.createStatement();
            connection.clearWarnings();
       }
       catch(SQLException e)
       {
              //System.out.println("Exception in creating statement:"+e);
       }          
  }
  catch(Exception e)
  {
         //System.out.println("Exception in openeing connection:"+e);
  }  
 %>

  <form name="frmCreateNews" enctype="multipart/form-data"  >
      <table width="100%" align="center">
        <tr>
            <td class="tdH">
                <center><b>Upload Attach File Details</b></center>
            </td>
        </tr>
        <tr>
            <td>
                <table cellspacing="3" cellpadding="1" width="100%">
                <tr>
                  <td width="36%">
                  News Caption ID                 
                  </td>
                  <td width="64%">       
                    <input type="text" id="txtEventId" name="txtEventId" size="10"
                        onchange="callServer('Existg','null')" onkeypress="return  numbersonly1(event,this)"/> 
                      
                  </td>
                </tr>
                
                <tr>
                  <td width="36%">
                  News Caption
                  
                  </td>
                  <td width="64%">
                  <input type="text" id="txtCaption" name="txtCaption" size="40" disabled="disabled" />
                  </td>
                </tr>
                
                <tr>
                  <td width="36%">
                  Attach Sl.No.                 
                  </td>
                  <td width="64%">       
                    <input type="text" id="txtatt_slno" name="txtatt_slno" size="10" disabled="disabled"/> 
                      ( System 'll Generate )
                  </td>
                </tr>               
                
                <tr>
                    <td  width="36%">Attach File</td>
                    <td width="64%">
                  <input type="file" id="txtattachFile" name="txtattachFile" size="40" />
                  &nbsp;
                </td>
                </tr>
              </table>
          </td>
        </tr>        
        <tr>
        <td align="center" class="tdH">
            <input type="button" value="Submit" onclick="check()">
            <input type="button" value="Cancel" onclick="closeWindow()">
        </td>
        </tr>
      </table>
      <p>
        &nbsp;
      </p>
    </form>
  </body>
</html>