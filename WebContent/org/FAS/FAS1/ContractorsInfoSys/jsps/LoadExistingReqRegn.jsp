<%@ page contentType="text/html;charset=windows-1252"%>
<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=windows-1252">
    <title>Search Records</title>
    <link href="../../../../../css/Sample3.css" rel="stylesheet" media="screen"/>
    
    <script language="javascript" src="../scripts/AjaxExistingRegn.js"></script>
    <script language="javascript">
    //function to load the date on loading the form
     function loadDate()
   {
     var today = new Date()
     var year = today.getYear()
     if(year < 1000)
     {
      year += 1900
     }
     document.SearchRecord.txtDate.value=today.getDate() + "/" + (today.getMonth()+1) +  "/" + (year+"");
}
</script>
  </head>
  <body onload="loadDate()">
  
    <form name="SearchRecord" method="get">
      <P>&nbsp;</P>
      <table cellspacing="2" cellpadding="3" border="1" width="92%" class="table" align="center">
        <tr>
          <td colspan="3" class="tdH">
            <DIV align="center">
              <STRONG>SELECTION CRITERIA </STRONG>
              </DIV>
          </td>
        </tr>
        <tr>
          <td width="28%">
            <input type="radio" name="search" onclick="callServer()" checked/>Request Sequence No</td>
          <td width="35%">
            <input type="radio" name="search" onclick="callServer()"/>Date Of Registration</td>
          <td width="37%">
            <input type="radio" name="search" onclick="callServer()"/>Contractor Name</td>
        </tr>
        <tr>
          <td width="28%">
            <input type="text" size="7" name="txtReq_no"/>
          </td>
          <td width="35%">
            <input type="text" maxlength="10" size="10" name="txtDate" disabled/>(dd/mm/yyyy)
          </td>
          <td width="37%">
            <input type="text" maxlength="15" size="15" name="txtCont_Name" disabled/>
          </td>
        </tr>
        <tr>
          <td width="28%" colspan="3">
            <DIV align="center"> 
            <input type="BUTTON" value="Go" onclick="List()"/>
            <input type="reset" value="Cancel"/></DIV>
         
          </td>
        </tr>
      </table>
      
        <%--<iframe id="ListRecord" width="100%" height="500px" >
    </iframe>--%></P>
    <table id="tblTable" class="table" border="0" cellspacing="1" cellpadding="2" width="92%" align="center">
              <tr>
                  <td colspan="6" class="tdH">
                    <center>
                      <b>
                          <center>
                          LISTING THE DETAILS
</center>
                        
                      </b>
                    </center>
                    
                 </td>
                </tr>
              <tr>
              <table  cellspacing="3" cellpadding="2" border="1" width="92%" id="ExistingRecord" class="table" align="center">
                 <th>
                    <FONT class="td3">Select</FONT>
                  </th> 
                 
                  <th>
                    <FONT class="td3" id="reg_no"><b>Req.SeqNo</b></FONT>
                  </th>
                  <th>
                   <FONT class="td3" id="cid"><b>Contractor Id</b></FONT>
                  </th>
                  <th>
                    <FONT class="td3" id="date"><b>Date OF Reg</b></FONT>
                  </th>
                  <th>
                    <FONT class="td3" id="con_name"><b>Contractor Name</b></FONT>
                  </th>
                  <th>
                    <FONT class="td3" id="addr"><b>Address</b></FONT>
                  </th>
                  <th>
                    <FONT class="td3" id="class"><b>Class</b></FONT>
                  </th>
                  
                  
              
                 <tbody id="tblList"> 
                </tbody>
   
      
     
          </table>
          </tr>
         
          </table>
          <table border="1" cellspacing="1" cellpadding="2" width="92%" align="center">
          <tr>
          <td class="table">
          <P align="center">
            <input type="BUTTON" value="    Ok    " name="Submit" onclick="f1()"/>
            <input type="BUTTON" value="Cancel" name="Cancel" onclick="f2()"/>
            
         </P>
         </td>
         </tr>
         </table>
       
    </form>
  </body>
</html>
