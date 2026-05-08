var seq=0;

/**
 *  Browser Identification 
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



/**
 *   Main Function 
 */ 

function  PendingJournals_11() 
{   
      
            var cmbAcc_UnitCode=document.getElementById("cmbAcc_UnitCode").value;
            var cmbOffice_code=document.getElementById("cmbOffice_code").value;
            var txtCB_Year= document.getElementById("txtCB_Year").value;
            var txtCB_Month= document.getElementById("txtCB_Month").value;
            
            var url="../../../../../View_Centage_AfterPosting.kv?Command=PendingJournals&cmbAcc_UnitCode="+cmbAcc_UnitCode
                    +"&cmbOffice_code="+cmbOffice_code+"&txtCB_Year="+txtCB_Year+"&txtCB_Month="+txtCB_Month;
                    
                
                var req=getTransport();
                req.open("GET",url,true); 
                req.onreadystatechange=function()
                {
                   handleResponse_PendingJournals(req);
                }   
                   req.send(null);
        
}




/**
 *  Server Response Checking Function 
 */
function handleResponse_PendingJournals(req)
{  
     
    if(req.readyState==4)
    {   
       if(req.status==200)
        {  
            var baseResponse=req.responseXML.getElementsByTagName("response")[0];
            
            var tagcommand=baseResponse.getElementsByTagName("command")[0];
            var Command=tagcommand.firstChild.nodeValue;             
            if(Command=="PendingJournals")
            {     
                PendingJournals(baseResponse);
            }
        }
    }
}



/**
 *  Display All Journals in the grid 
 */

function PendingJournals(baseResponse)
{
     var flag=baseResponse.getElementsByTagName("flag")[0].firstChild.nodeValue;
     if(flag=="success")
     {
        
        /**
         *  First Clear the Grid Before displaying all the Details
         */
        var tbody=document.getElementById("grid_body");
        var t=0;
        for(t=tbody.rows.length-1;t>=0;t--)
        {
           tbody.deleteRow(0);
        }
        
        
        /**
         *  Get Voucher Number for finding the length
         */
        
        var items=new Array();    
         
        var option_count=baseResponse.getElementsByTagName("option");  
        
        var voucher_no=baseResponse.getElementsByTagName("voucher_no");
       
        var root = null;
            
        for(var i=0;i<option_count.length;i++)
        {  
           
             root = baseResponse.getElementsByTagName("option")[i];
            
             items[0]=root.getElementsByTagName("voucher_no")[0].firstChild.nodeValue;   
             items[1]=root.getElementsByTagName("voucher_date")[0].firstChild.nodeValue;   
             items[2]=root.getElementsByTagName("dr_account_head_code")[0].firstChild.nodeValue;   
             items[3]=root.getElementsByTagName("cr_account_head_code")[0].firstChild.nodeValue;   
             items[4]=root.getElementsByTagName("amount")[0].firstChild.nodeValue; 
             items[5]=root.getElementsByTagName("dr_account_head_code_desc")[0].firstChild.nodeValue; 
             items[6]=root.getElementsByTagName("cr_account_head_code_desc")[0].firstChild.nodeValue; 
             
             seq=parseInt(seq)+1;
              
              var mycurrent_row=document.createElement("TR");
                mycurrent_row.id=seq;   
                
                       
                var cell2;
                
                
                      cell2=document.createElement("TD");
                      var anc=document.createElement("A");
                      var url="javascript:call_Revoke('"+items[0]+"','"+items[1]+"')";
                      anc.href=url;
                      var txtedit=document.createTextNode("Revoke");
                      anc.appendChild(txtedit);
                      cell2.appendChild(anc);
                      mycurrent_row.appendChild(cell2);
                
                
                
                      cell2=document.createElement("TD");
                          var voucher_no=document.createElement("input");
                          cell2.style.textAlign='center';
                          voucher_no.type="hidden";
                          voucher_no.name="voucher_no";
                          voucher_no.value=items[0];
                          cell2.appendChild(voucher_no);
                          var currentText=document.createTextNode(items[0]);
                          cell2.appendChild(currentText);
                          mycurrent_row.appendChild(cell2);                            
                 
                     cell2=document.createElement("TD");
                          var voucher_date=document.createElement("input");
                          voucher_date.type="hidden";
                          voucher_date.name="voucher_date";
                          voucher_date.value=items[1];
                          cell2.appendChild(voucher_date);
                          var currentText=document.createTextNode(items[1]);
                          cell2.appendChild(currentText);
                          mycurrent_row.appendChild(cell2);
                           
                     cell2=document.createElement("TD");
                          var dr_account_head_code=document.createElement("input");
                          cell2.style.textAlign='center';
                          dr_account_head_code.type="hidden";
                          dr_account_head_code.name="dr_account_head_code";
                          dr_account_head_code.value=items[2];
                          cell2.appendChild(dr_account_head_code); 
                          var currentText=document.createTextNode(items[2]+"-- "+items[5]);
                          cell2.appendChild(currentText);
                          mycurrent_row.appendChild(cell2);                         
                          
                      
                     cell2=document.createElement("TD");
                          var dr_account_head_code=document.createElement("input");
                          cell2.style.textAlign='center';
                          dr_account_head_code.type="hidden";
                          dr_account_head_code.name="dr_account_head_code";
                          dr_account_head_code.value=items[2];
                          cell2.appendChild(dr_account_head_code); 
                          var currentText=document.createTextNode(items[2]+"-- "+items[5]);
                          cell2.appendChild(currentText);
                          mycurrent_row.appendChild(cell2);                          
                          
                          
                      cell2=document.createElement("TD");
                          var amount=document.createElement("input");
                          cell2.style.textAlign='right';
                          amount.type="hidden";
                          amount.name="amount";
                          amount.value=items[4];
                          cell2.appendChild(amount); 
                          var currentText=document.createTextNode(items[4]);
                          cell2.appendChild(currentText);
                          mycurrent_row.appendChild(cell2);                         
                          
                          
                       cell2=document.createElement("TD");
                          var cr_account_head_code=document.createElement("input");
                          cell2.style.textAlign='center';
                          cr_account_head_code.type="hidden";
                          cr_account_head_code.name="cr_account_head_code";
                          cr_account_head_code.value=items[3];
                          cell2.appendChild(cr_account_head_code); 
                          var currentText=document.createTextNode(items[3]+"-- "+items[6]);
                          cell2.appendChild(currentText);
                          mycurrent_row.appendChild(cell2);
                         
                       cell2=document.createElement("TD");
                          var amount=document.createElement("input");
                          cell2.style.textAlign='right';
                          amount.type="hidden";
                          amount.name="amount";
                          amount.value=items[4];
                          cell2.appendChild(amount); 
                          var currentText=document.createTextNode(items[4]);
                          cell2.appendChild(currentText);
                          mycurrent_row.appendChild(cell2);
                          
                          
              tbody.appendChild(mycurrent_row);
           
        }
        
        
     }
     else if(flag=="failure")
     {
        var tbody=document.getElementById("grid_body");
        var t=0;
        for(t=tbody.rows.length-1;t>=0;t--)
        {
           tbody.deleteRow(0);
        }
        
        alert("No Journals Found ");
     }
}



/** Revoke Function */

function call_Revoke(cjv_no, cjv_date) 
{
        var cmbAcc_UnitCode=document.getElementById("cmbAcc_UnitCode").value;
        var cmbOffice_code=document.getElementById("cmbOffice_code").value;
        var txtCB_Year= document.getElementById("txtCB_Year").value;
        var txtCB_Month= document.getElementById("txtCB_Month").value;            
       
        var url="../../../../../View_Centage_AfterPosting.kv?Command=RevokeJournals&cmbAcc_UnitCode="+cmbAcc_UnitCode
        +"&cmbOffice_code="+cmbOffice_code+"&CJVno="+cjv_no+"&CJVdate="+cjv_date+"&txtCB_Year="+txtCB_Year+"&txtCB_Month="+txtCB_Month;
    
        var req=getTransport();
        req.open("GET",url,true); 
        req.onreadystatechange=function()
        {
           handleResponse_RevokeJournals(req);
        }   
           req.send(null);
}



/**
 *  Server Response Checking Function 
 */
function handleResponse_RevokeJournals(req)
{  
     
    if(req.readyState==4)
    {   
       if(req.status==200)
        {  
            //alert(req.responseText);
            var baseResponse=req.responseXML.getElementsByTagName("response")[0];            
            
           
            var tagcommand=baseResponse.getElementsByTagName("command")[0];
            var Command=tagcommand.firstChild.nodeValue;             
            if(Command=="RevokeJournals")
            {     
                RevokeJournals(baseResponse);
            }
        }
    }
}


function RevokeJournals(baseResponse)
{
     var flag=baseResponse.getElementsByTagName("flag")[0].firstChild.nodeValue;
     if(flag=="success")
     {
       alert("Journals Revoked Sucessfully");
       PendingJournals_11();
     }
     else if (flag=="failure") 
     {
       alert("Journals Revoke Failed ");
     }
     
     
}