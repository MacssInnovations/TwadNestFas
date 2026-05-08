var seq=0;
var loadRemit_ReceiptDetails;
var OptionType;


/** 
 *  Browser Indentification 
 */ 
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


window.onunload=function()
{
if (loadRemit_ReceiptDetails && loadRemit_ReceiptDetails.open && !loadRemit_ReceiptDetails.closed) loadRemit_ReceiptDetails.close();
}


function doFunction(Command,param)
{   
           var cmbAcc_UnitCode=document.getElementById("cmbAcc_UnitCode").value;
           var cmbOffice_code=document.getElementById("cmbOffice_code").value;
           
           var txtCB_Year = document.getElementById("txtCB_Year").value;
           var txtCB_Month = document.getElementById("txtCB_Month").value;
            
           var txtfromdate = document.getElementById("txtfromdate").value;
           var txttodate = document.getElementById("txttodate").value;
           
            
           if(Command=="loadPendingJournals_NV")
           {
           
            OptionType = "NV"; 
            document.getElementById("butSub").disabled=false;     
            
            var url="../../../../../General_Journal_Verify.kv?Command=loadPendingJournals_NV&cmbAcc_UnitCode="+cmbAcc_UnitCode
                    +"&cmbOffice_code="+cmbOffice_code+"&txtCB_Year="+txtCB_Year+"&txtCB_Month="+txtCB_Month+"&txtfromdate="+txtfromdate+"&txttodate="+txttodate;
           }
           if(Command=="loadPendingJournals_V")
           {
           
           OptionType = "V";
           document.getElementById("butSub").disabled=true;     
           
           var url="../../../../../General_Journal_Verify.kv?Command=loadPendingJournals_V&cmbAcc_UnitCode="+cmbAcc_UnitCode
                    +"&cmbOffice_code="+cmbOffice_code+"&txtCB_Year="+txtCB_Year+"&txtCB_Month="+txtCB_Month+"&txtfromdate="+txtfromdate+"&txttodate="+txttodate;
           }
                    
               var req=getTransport();
                req.open("GET",url,true); 
                req.onreadystatechange=function()
                {
                   handleResponse(req);
                }   
                        req.send(null);
                    
}




function handleResponse(req)
{  
    if(req.readyState==4)
    {
        if(req.status==200)
        {   
            var baseResponse=req.responseXML.getElementsByTagName("response")[0];
            var tagcommand=baseResponse.getElementsByTagName("command")[0];
            var Command=tagcommand.firstChild.nodeValue;             
            if(Command=="loadPendingJournals")
            {
                PendingRemittance(baseResponse);
            }
        }
    }
}


function PendingRemittance(baseResponse)
{
  var flag=baseResponse.getElementsByTagName("flag")[0].firstChild.nodeValue;
  
    if(flag=="success")
     {
        var tbody=document.getElementById("grid_body");
        var t=0;
        for(t=tbody.rows.length-1;t>=0;t--)
        {
           tbody.deleteRow(0);
        }
        
        var Voucher_no=baseResponse.getElementsByTagName("Voucher_no");
        var items=new Array();
        var Ucode=document.getElementById("cmbAcc_UnitCode").value;
        var Offid=document.getElementById("cmbOffice_code").value;
        
       // alert(Voucher_no.length);
        
        
        for(var k=0;k<Voucher_no.length;k++)
        {
           
             items[0]=baseResponse.getElementsByTagName("Voucher_no")[k].firstChild.nodeValue;   
             items[1]=baseResponse.getElementsByTagName("Voucher_date")[k].firstChild.nodeValue;  
             items[2]=baseResponse.getElementsByTagName("Amount")[k].firstChild.nodeValue;
            
             try
             {
               items[3]=baseResponse.getElementsByTagName("remarks")[k].firstChild.nodeValue;
             }
             catch(e){e.description}
             
             
             items[4]=baseResponse.getElementsByTagName("Account_Head")[k].firstChild.nodeValue;
             items[5]=baseResponse.getElementsByTagName("Account_Head_Desc")[k].firstChild.nodeValue;
             items[6]=baseResponse.getElementsByTagName("cr_dr_ind")[k].firstChild.nodeValue;
            
            
             if( items[3]=="null")
               items[3]="";
             tbody=document.getElementById("grid_body");
             
                var mycurrent_row=document.createElement("TR");
                
                var cell2;
                
                    cell2=document.createElement("TD");
                    
                          if ( OptionType == "NV" )                              
                            cell2.style.color="#900000";
                          if ( OptionType == "V" )  
                            cell2.style.color="green";
                            
                          var Challan_no=document.createElement("input");
                          Challan_no.type="hidden";
                          Challan_no.name="Voucher_no";
                          Challan_no.value=items[0];
                          cell2.appendChild(Challan_no);
                          var currentText=document.createTextNode(items[0]);
                          cell2.appendChild(currentText);
                           mycurrent_row.appendChild(cell2);
                           
                     cell2=document.createElement("TD");
                     
                         if ( OptionType == "NV" )  
                            cell2.style.color="#900000";
                          if ( OptionType == "V" )  
                            cell2.style.color="green";
                          
                          var challan_date=document.createElement("input");
                          challan_date.type="hidden";
                          challan_date.name="Voucher_date";
                          challan_date.value=items[1];
                          cell2.appendChild(challan_date); 
                           var currentText=document.createTextNode(items[1]);
                          cell2.appendChild(currentText);
                        mycurrent_row.appendChild(cell2);
                     
                     
                      cell2=document.createElement("TD");
                      
                          if ( OptionType == "NV" )  
                            cell2.style.color="#900000";
                          if ( OptionType == "V" )  
                            cell2.style.color="green";
                          
                          var Account_Head=document.createElement("input");
                          Account_Head.type="hidden";
                          Account_Head.name="Account_Head";
                          Account_Head.value=items[4]+" - "+items[5];
                          cell2.appendChild(Account_Head); 
                           var currentText=document.createTextNode(items[4]+" - "+items[5]);
                          cell2.appendChild(currentText);
                        mycurrent_row.appendChild(cell2);
                     
                     
                   /*   cell2=document.createElement("TD");
                          var Account_Head_Desc=document.createElement("input");
                          Account_Head_Desc.type="hidden";
                          Account_Head_Desc.name="Account_Head_Desc";
                          Account_Head_Desc.value=items[5];
                          cell2.appendChild(Account_Head_Desc); 
                           var currentText=document.createTextNode(items[5]);
                          cell2.appendChild(currentText);
                        mycurrent_row.appendChild(cell2);
                   */
                   
                      cell2=document.createElement("TD");
                         
                          if ( OptionType == "NV" )  
                            cell2.style.color="#900000";
                          if ( OptionType == "V" )  
                            cell2.style.color="green";
                      
                          var cr_dr_ind=document.createElement("input");
                          cr_dr_ind.type="hidden";
                          cr_dr_ind.name="cr_dr_ind";
                          cr_dr_ind.value=items[6];
                          cell2.appendChild(cr_dr_ind); 
                           var currentText=document.createTextNode(items[6]);
                          cell2.appendChild(currentText);
                        mycurrent_row.appendChild(cell2);
                           
                  
                    cell2=document.createElement("TD");
                         if ( OptionType == "NV" )  
                            cell2.style.color="#900000" ;
                          if ( OptionType == "V" )  
                            cell2.style.color="green";       
                          cell2.style.textAlign='right';
                          var currentText=document.createTextNode(items[2]);
                          cell2.appendChild(currentText);
                        mycurrent_row.appendChild(cell2);
                        
                   /*  cell2=document.createElement("TD");
                          if ( OptionType == "NV" )  
                            cell2.style.color="blue";
                          if ( OptionType == "V" )  
                            cell2.style.color="green";
                          var currentText=document.createTextNode(items[3]);                      
                          cell2.appendChild(currentText);
                          mycurrent_row.appendChild(cell2);         
                   */  
                        
              tbody.appendChild(mycurrent_row);
        }
        
     }
    else if(flag=="failure")
     { 
        document.getElementById("butSub").disabled=false; 
        
       if ( OptionType=="NV" )
       {
         alert("No Journals found for Verification"); 
       }       
       else if (OptionType=="V" )
       {
        alert("No Verified Journals found / Journal have not been Verified");         
       } 
        
        
         var tbody=document.getElementById("grid_body");
        var t=0;
        for(t=tbody.rows.length-1;t>=0;t--)
        {
           tbody.deleteRow(0);
        }
     }
}



function call_clr()
{
        var tbody=document.getElementById("grid_body");
        var t=0;
        for(t=tbody.rows.length-1;t>=0;t--)
        {
           tbody.deleteRow(0);
        }
}




function exit()
{
       self.close();
}




function checkNull()
{
 var tbody=document.getElementById("grid_body");
if(tbody.rows.length==0)
{
    alert("No data for verification");    
    return false; 
}
return true;
}



function numbersonly(e)
{
    var unicode=e.charCode? e.charCode : e.keyCode;
   if(unicode==13)
    {
      
    }
    if (unicode!=8 && unicode !=9  )
    {
        if (unicode<48 || unicode>57 ) 
            return false 
    }
 }