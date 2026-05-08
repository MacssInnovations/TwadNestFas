<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=windows-1252"/>
    <title>Payee Type List</title>
     <link href="../../../../../css/Sample3.css" rel="stylesheet" media="screen"/>
     <script type="text/javascript" src="../scripts/phoneCertificate_list.js"></script>
  </head>
  <body onload="initialLoad();">
          <form name="phonecertificatelistform" action="Get">
               <table cellspacing="2" cellpadding="3" border="1" width="100%" align="center" >
                 <tr class="tdH" align="center">
                    <th>
                            List of Phone Certificate
                    </th>
                </tr>
           </table>
           
           <table id="Existing" cellspacing="2" cellpadding="3" border="1" width="100%" align="center" >
                <tr class="tdH">
                     <th>Select</th>
                     <th>Phone Certificate Number</th>
                     <th>Phone Number</th>  
                     <th>Bill Month</th>      
                     <th>Bill Year</th>  
                     <th>Invoice Number</th>  
                     <th>Certificate Text</th>  
                     <th>Status</th>
                </tr>
             <tbody id="tblList" align="center" class="table">
             </tbody>
            </table>
            <table align="center" cellspacing="2" cellpadding="3" border="1" width="100%">
                <tr class="tdH">
                  <td>
                    <div align="center">
                             <input type="button" id="exit" name="exit" value="Exit"
                             onclick=" self.close();"></input>
                    </div>
                  </td>
                </tr>
            </table> 
          </form>
  </body>
</html>