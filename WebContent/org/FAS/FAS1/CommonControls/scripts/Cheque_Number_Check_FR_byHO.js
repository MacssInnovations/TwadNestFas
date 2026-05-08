
///////////////////////////////////////////////////////////////////////////////// 
// Checking Cheque/DD Number. Whether already exits or not  
////////////////////////////////////////////////////////////////////////////////

 function check_dd_cheque()
 {
      var cheque_no= document.getElementById("txtCheque_DD_NO").value;
      var cmbAcc_UnitCode=document.getElementById("cmbAcc_UnitCode").value;
      var cmbOffice_code=document.getElementById("cmbOffice_code").value;
                 
      
      var url="../../../../../Cheque_Number_Check_FR_byHO.kv?Command=check_cheque_no&cheque_no="+cheque_no+"&cmbAcc_UnitCode="+cmbAcc_UnitCode+"&cmbOffice_code="+cmbOffice_code;
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
                  temp=temp+"Receipt Number = "+baseResponse.getElementsByTagName("rec_no")[k].firstChild.nodeValue+"\n";
                  temp=temp+"Amount  = "+baseResponse.getElementsByTagName("amount")[k].firstChild.nodeValue+"\n";
                  temp=temp+"Cashbook Year  =  "+baseResponse.getElementsByTagName("cbyear")[k].firstChild.nodeValue+"\n";
                  temp=temp+"Cashbook Month =  "+baseResponse.getElementsByTagName("cbmonth")[k].firstChild.nodeValue+"\n";
                  temp=temp+"\n";
                 
                 }  
                              
                 alert("  Cheque Number  "+ baseResponse.getElementsByTagName("cheq_no")[0].firstChild.nodeValue + " already exits "+'\n'+temp);   
               //  document.getElementById("txtCheque_DD_NO").value="";
                 
                 
              }
       }
   }    
}


