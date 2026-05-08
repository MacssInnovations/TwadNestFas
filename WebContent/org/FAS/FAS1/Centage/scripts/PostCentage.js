var seq=0;
var seq1=0;

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


function callTtlCent()
{
	 var cmbAcc_UnitCode=document.getElementById("cmbAcc_UnitCode").value;
     var cmbOffice_code=document.getElementById("cmbOffice_code").value;
     var txtCB_Year= document.getElementById("txtCB_Year").value;
     var txtCB_Month= document.getElementById("txtCB_Month").value;
   //  var tcen;
     if(document.forms[0].totalcent[0].checked==true)
     {
    	// tcen="Y";
    	 
    	 var url="../../../../../PostCentage.kv?Command=callCentY&cmbAcc_UnitCode="+cmbAcc_UnitCode
         +"&cmbOffice_code="+cmbOffice_code+"&txtCB_Year="+txtCB_Year+"&txtCB_Month="+txtCB_Month;
        
	     var req=getTransport();
	     req.open("GET",url,true); 
	     req.onreadystatechange=function()
	     {
	    	 handleResponse_PendingJournals(req);
	     }   
	        req.send(null);
     }
     else{
    	 
    	// tcen="N";
           var url="../../../../../PostCentage.kv?Command=callCentN&cmbAcc_UnitCode="+cmbAcc_UnitCode
             +"&cmbOffice_code="+cmbOffice_code+"&txtCB_Year="+txtCB_Year+"&txtCB_Month="+txtCB_Month;
         
         var req=getTransport();
         req.open("GET",url,true); 
         req.onreadystatechange=function()
         {
        	 handleResponse_PendingJournals(req);
         }   
            req.send(null);
     }
}

function handleResponse_PendingJournals(req)
{  
     
    if(req.readyState==4)
    {   
       if(req.status==200)
        {  
            var baseResponse=req.responseXML.getElementsByTagName("response")[0];
            
            var tagcommand=baseResponse.getElementsByTagName("command")[0];
            var Command=tagcommand.firstChild.nodeValue;             
            if(Command=="callCentN")
            {     
            	callCentN(baseResponse);
            }
            else if(Command=="callCentY")
            {     
            	callCentY(baseResponse);
            }
        }
    }
}



function callCentN(baseResponse)
{
     var flag=baseResponse.getElementsByTagName("flag")[0].firstChild.nodeValue;
     if(flag=="success")
     {
       
    	 
         
         var cellh1=document.getElementById("grid1");
         cellh1.style.display="none";
         
         var secgrid=document.getElementById("grid_body1");
         var m=0;
         for(m=secgrid.rows.length-1;m>=0;m--)
         {
         	secgrid.deleteRow(0);
         }
    	 
         var cellh=document.getElementById("grid");
         cellh.style.display="block";
         
	       var tbody=document.getElementById("grid_body");
	        var t=0;
	        for(t=tbody.rows.length-1;t>=0;t--)
	        {
	           tbody.deleteRow(0);
	        }
        
        var items=new Array();    
         
        var option_count=baseResponse.getElementsByTagName("option");  
        
        var voucher_no=baseResponse.getElementsByTagName("voucher_no");
       
        var root = null;
        seq=0;
        for(var i=0;i<option_count.length;i++)
        {  
           
             root = baseResponse.getElementsByTagName("option")[i];
            
             items[0]=root.getElementsByTagName("voucher_no")[0].firstChild.nodeValue;   
             items[1]=root.getElementsByTagName("voucher_date")[0].firstChild.nodeValue;   
             items[2]=root.getElementsByTagName("dr_account_head_code")[0].firstChild.nodeValue;   
             items[3]=root.getElementsByTagName("cr_account_head_code")[0].firstChild.nodeValue;   
             items[4]=root.getElementsByTagName("amount")[0].firstChild.nodeValue;   
             items[5]=root.getElementsByTagName("cr_account_head_code_desc")[0].firstChild.nodeValue;   
             items[6]=root.getElementsByTagName("dr_account_head_code_desc")[0].firstChild.nodeValue;   
             
             
             seq=parseInt(seq)+1;
              
              var mycurrent_row=document.createElement("TR");
                mycurrent_row.id=seq;   
              //  alert( mycurrent_row.id);
                var cell2;
                
                cell2=document.createElement("TD");
                var seqno=document.createElement("input");
                cell2.style.textAlign='center';
                seqno.type="hidden";
                seqno.name="serialNo";
                seqno.value=seq;
                cell2.appendChild(seqno);
                var currentText=document.createTextNode(seq);
                cell2.appendChild(currentText);
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
                          var currentText=document.createTextNode(items[2]+"-- "+items[6]);
                          cell2.appendChild(currentText);
                          mycurrent_row.appendChild(cell2);                         
                          
                      
                     cell2=document.createElement("TD");
                          var dr_account_head_code=document.createElement("input");
                          cell2.style.textAlign='center';
                          dr_account_head_code.type="hidden";
                          dr_account_head_code.name="dr_account_head_code";
                          dr_account_head_code.value=items[2];
                          cell2.appendChild(dr_account_head_code); 
                          var currentText=document.createTextNode(items[2]+"-- "+items[6]);
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
                          var currentText=document.createTextNode(items[3]+"-- "+items[5]);
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
        var secgrid=document.getElementById("grid_body1");
        var m=0;
        for(m=secgrid.rows.length-1;m>=0;m--)
        {
        	secgrid.deleteRow(0);
        }
        
        alert("No Journals Found for Centage Posting");
     }
}

function callCentY(baseResponse)
{
     var flag=baseResponse.getElementsByTagName("flag")[0].firstChild.nodeValue;
     if(flag=="success")
     {
       
        var cellh=document.getElementById("grid");
        cellh.style.display="none";
        
        
        var firstgrid=document.getElementById("grid_body");
        var n=0;
        for(n=firstgrid.rows.length-1;n>=0;n--)
        {
        	firstgrid.deleteRow(0);
        }
        
        var cellh1=document.getElementById("grid1");
        cellh1.style.display="block";
        
        var tbody=document.getElementById("grid_body1");
        var t=0;
        for(t=tbody.rows.length-1;t>=0;t--)
        {
           tbody.deleteRow(0);
        }
       
        var items=new Array();    
         
        var option_count=baseResponse.getElementsByTagName("option");  
      
        var root = null;
        seq1=0;  
        for(var i=0;i<option_count.length;i++)
        {  
           
             root = baseResponse.getElementsByTagName("option")[i];
             
             items[1]=root.getElementsByTagName("dr_account_head_code")[0].firstChild.nodeValue;   
           
             items[2]=root.getElementsByTagName("cr_account_head_code")[0].firstChild.nodeValue;
             items[3]=root.getElementsByTagName("dramount")[0].firstChild.nodeValue;
             items[4]=root.getElementsByTagName("cr_account_head_code_desc")[0].firstChild.nodeValue;
             items[5]=root.getElementsByTagName("dr_account_head_code_desc")[0].firstChild.nodeValue;  
             items[6]=root.getElementsByTagName("cramount")[0].firstChild.nodeValue;
             seq1=parseInt(seq1)+1;
              
              var mycurrent_row1=document.createElement("TR");
                mycurrent_row1.id=seq1;   
                
                var cell2;
                
                cell2=document.createElement("TD");
                var seqno=document.createElement("input");
                cell2.style.textAlign='center';
                seqno.type="hidden";
                seqno.name="serialNo";
                seqno.value=seq1;
                cell2.appendChild(seqno);
                var currentText=document.createTextNode(seq1);
                cell2.appendChild(currentText);
                mycurrent_row1.appendChild(cell2);  
                       
                     cell2=document.createElement("TD");
                          var dr_account_head_code=document.createElement("input");
                          cell2.style.textAlign='center';
                          dr_account_head_code.type="hidden";
                          dr_account_head_code.name="dr_account_head_code";
                          dr_account_head_code.value=items[1];
                          cell2.appendChild(dr_account_head_code); 
                          var currentText=document.createTextNode(items[1]+"-- "+items[5]);
                          cell2.appendChild(currentText);
                          mycurrent_row1.appendChild(cell2);                         
                          
                      
                     cell2=document.createElement("TD");
                          var dr_account_head_code=document.createElement("input");
                          cell2.style.textAlign='center';
                          dr_account_head_code.type="hidden";
                          dr_account_head_code.name="dr_account_head_code";
                          dr_account_head_code.value=items[1];
                          cell2.appendChild(dr_account_head_code); 
                          var currentText=document.createTextNode(items[1]+"-- "+items[5]);
                          cell2.appendChild(currentText);
                          mycurrent_row1.appendChild(cell2);                          
                          
                          
                      cell2=document.createElement("TD");
                          var amount=document.createElement("input");
                          cell2.style.textAlign='right';
                          amount.type="hidden";
                          amount.name="amount";
                          amount.value=items[3];
                          cell2.appendChild(amount); 
                          var currentText=document.createTextNode(items[3]);
                          cell2.appendChild(currentText);
                          mycurrent_row1.appendChild(cell2);                         
                          
                          
                       cell2=document.createElement("TD");
                          var cr_account_head_code=document.createElement("input");
                          cell2.style.textAlign='center';
                          cr_account_head_code.type="hidden";
                          cr_account_head_code.name="cr_account_head_code";
                          cr_account_head_code.value=items[2];
                          cell2.appendChild(cr_account_head_code); 
                          var currentText=document.createTextNode(items[2]+"-- "+items[4]);
                          cell2.appendChild(currentText);
                          mycurrent_row1.appendChild(cell2);
                         
                       cell2=document.createElement("TD");
                          var amount=document.createElement("input");
                          cell2.style.textAlign='right';
                          amount.type="hidden";
                          amount.name="amount";
                          amount.value=items[6];
                          cell2.appendChild(amount); 
                          var currentText=document.createTextNode(items[6]);
                          cell2.appendChild(currentText);
                          mycurrent_row1.appendChild(cell2);
                          
                          
              tbody.appendChild(mycurrent_row1);
           
        }
        
        
     }
     else if(flag=="failure")
     {
    	 var firstgrid=document.getElementById("grid_body");
         var n=0;
         for(n=firstgrid.rows.length-1;n>=0;n--)
         {
         	firstgrid.deleteRow(0);
         }
    	 
        var tbody=document.getElementById("grid_body1");
        var t=0;
        for(t=tbody.rows.length-1;t>=0;t--)
        {
           tbody.deleteRow(0);
        }
        
        alert("No Journals Found for Centage Posting");
     }
}
