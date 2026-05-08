
///////////////////////////////////////////////////////////////////////////////// 
// Checking Cheque/DD Number. Whether already exits or not  
////////////////////////////////////////////////////////////////////////////////

 function checkjournal_dd_cheque()
 {
    //  var cheque_no= document.getElementById("txtCheque_DD_NO").value;
      var cheque_no=document.getElementById("txtCheque_DD_NO2").value;
      var cmbAcc_UnitCode=document.getElementById("cmbAcc_UnitCode").value;
      var url="../../../../../Cheque_Number_Check_forJOURNAL?Command=check_cheque_no&cheque_no="+cheque_no+"&cmbAcc_UnitCode="+cmbAcc_UnitCode;
      alert(url);
      var req=getTransport();
      req.open("GET",url,true); 
      req.onreadystatechange=function()
         {
            handleResponse_cheque_no(req);
         }   
      req.send(null); 
 }
 
function handleResponse_cheque_no(req) 
{ 
   
    if(req.readyState==4)
    {
       
        if(req.status==200)
        {  
            var baseResponse=req.responseXML.getElementsByTagName("response")[0];
            var tagcommand=baseResponse.getElementsByTagName("command")[0];
            var Command=tagcommand.firstChild.nodeValue;
            var flag=baseResponse.getElementsByTagName("flag")[0].firstChild.nodeValue;
            if(flag=="Success")
              {
              
                 var cheque_no = baseResponse.getElementsByTagName("cheq_no");   
                
                 var max=cheque_no.length;
               
                 if(max > 1 )
                    max--;
                  
                              
                 var temp="";
                 for(var k=0;k<max;k++)
                 { 
                    temp=temp+"----------------------------------------------------\n";
                  temp=temp+"Voucher Number = "+baseResponse.getElementsByTagName("vo_no")[k].firstChild.nodeValue+"\n";
                  temp=temp+"Amount  = "+baseResponse.getElementsByTagName("amount")[k].firstChild.nodeValue+"\n";
                  temp=temp+"Created By Module = "+baseResponse.getElementsByTagName("crm")[k].firstChild.nodeValue+"\n";
                  temp=temp+"Cashbook Year  =  "+baseResponse.getElementsByTagName("cbyear")[k].firstChild.nodeValue+"\n";
                  temp=temp+"Cashbook Month =  "+baseResponse.getElementsByTagName("cbmonth")[k].firstChild.nodeValue+"\n";
                  temp=temp+"\n";
                 
                 }  
                              
                 alert("  Cheque Number  "+ baseResponse.getElementsByTagName("cheq_no")[0].firstChild.nodeValue + " already exits "+'\n'+temp);   
                 
                   
               /*     
                 var temp="";
                 for(var k=0;k<max;k++)
                 { 
                  temp=temp+"_______";
                  temp=temp+baseResponse.getElementsByTagName("vo_no")[k].firstChild.nodeValue+"______|______";
                  temp=temp+baseResponse.getElementsByTagName("amount")[k].firstChild.nodeValue+"______|______";
                  temp=temp+baseResponse.getElementsByTagName("crm")[k].firstChild.nodeValue+"______|______";
                  temp=temp+baseResponse.getElementsByTagName("cbyear")[k].firstChild.nodeValue+"______|______";
                  temp=temp+baseResponse.getElementsByTagName("cbmonth")[k].firstChild.nodeValue+"";
                  temp=temp+"\n";
                 
                 }
                 
                 alert("  Cheque Number  "+ baseResponse.getElementsByTagName("cheq_no")[0].firstChild.nodeValue + " already exits "+'\n'+ 
                       " ------------------------------------------------------------------------------------------------------------"+'\n'+  
                       " Voucher Number |____ Amount_____ | Created By Module |___ CB Year___ |___ CB Month " + '\n' + temp ); 
                       
                 
                                        
                 
                 my_window= window.open ("","mywindow1","height=150,width=420,scrollbars=1,resizable=1");
                 my_window.document.write('<html><head><title>Cheque Number Checking</title><body>');
                 my_window.document.write('<table border=1 width=380><tr><th align=center colspan=5>Cheque Number '+baseResponse.getElementsByTagName("cheq_no")[0].firstChild.nodeValue+' already exits '); 
                 my_window.document.write('<tr> <td align=center>Voucher No.</td> <td align=center>Amount</td>  <td align=center>Created by Module</td>  <td align=center>CB Year</td>  <td align=center>CB Month</td> </tr>');
                 my_window.document.write('<tr>');
                 
                 for(var k=0;k<max;k++)
                 {
                   my_window.document.write('<tr><td align=center>'+baseResponse.getElementsByTagName("vo_no")[k].firstChild.nodeValue); 
                   my_window.document.write('<td align=center>'+baseResponse.getElementsByTagName("amount")[k].firstChild.nodeValue); 
                   my_window.document.write('<td align=center>'+baseResponse.getElementsByTagName("crm")[k].firstChild.nodeValue); 
                   my_window.document.write('<td align=center>'+baseResponse.getElementsByTagName("cbyear")[k].firstChild.nodeValue); 
                   my_window.document.write('<td align=center>'+baseResponse.getElementsByTagName("cbmonth")[k].firstChild.nodeValue); 
                 }
                 
                 my_window.document.write('</table></body></html>');
                 my_window.moveTo(200,250);
                
                */ 
                
                
                 
               //  document.getElementById("txtCheque_DD_NO").value="";
                 
                 
              }
       }
   }    
}


