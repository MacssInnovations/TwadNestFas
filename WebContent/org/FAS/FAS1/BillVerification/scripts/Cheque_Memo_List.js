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


function doFunction(Command,param)
{ 
	//alert(param);
	var cmbAcc_UnitCode;    
    var cmbOffice_code;
	if(param=="MemoOFF"){
	    var cmbAcc_UnitCode=document.getElementById("cmbAcc_UnitCode").value;    
        var cmbOffice_code=document.getElementById("cmbOffice_code").value;
	}else if(param=="MemoHO"){
		var cmbAcc_UnitCode=0;    
	    var cmbOffice_code=0;}
        var txtCB_Year=document.getElementById("txtCB_Year").value;
        var txtCB_Month=document.getElementById("txtCB_Month").value;
        var optionId;//=document.getElementById("optionId").value;
        
        if(document.frmchequememo_list.optionId[0].checked==true)
	    {
        	optionId=document.frmchequememo_list.optionId[0].value;
	    }else{
	    	optionId=document.frmchequememo_list.optionId[1].value;
	    }
        
        if(txtCB_Year=="")
        {
        	alert("Enter CashbookYear");
        }
        
        else
        {
               
                var url="../../../../../Cheque_Memo_List?Command=searchByMonth&cmbAcc_UnitCode="+cmbAcc_UnitCode+
                "&reportType="+param+"&cmbOffice_code="+cmbOffice_code+"&txtCB_Year="+txtCB_Year+"&txtCB_Month="+txtCB_Month+"&optionId="+optionId;			                
                
                var req=getTransport();
                req.open("GET",url,true);     
                req.onreadystatechange=function()
                {
                   handleResponse(req);
                }   
                req.send(null);
		               
		           
        }
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
			            var ReportType=baseResponse.getElementsByTagName("reportType")[0].firstChild.nodeValue;
			           
			            if(Command=="searchByMonth")
			            {
			            	if(ReportType=="MemoOFF"){
			                loadTable(baseResponse);}
			            	if(ReportType=="MemoHO"){
			            		
				                loadTableHO(baseResponse);}
			            }
			            
		        }
	    }
}

function loadTable(baseResponse)
{
   
        var flag=baseResponse.getElementsByTagName("flag")[0].firstChild.nodeValue;
        var tbody=document.getElementById("tbody");
   	 //tbody.rows.length=0;
   	 
   	 var t=0;
        for(t=tbody.rows.length-1;t>=0;t--)
            {
               tbody.deleteRow(0);
            } 
        if(flag=="failure")
        {
        	 
                    alert("No Record exists");
          
        }
        else
        { 
        
        	
        	var cmbAcc_UnitCode=document.getElementById("cmbAcc_UnitCode").value;    
            var cmbOffice_code=document.getElementById("cmbOffice_code").value;
            var txtCB_Year=document.getElementById("txtCB_Year").value;
            var txtCB_Month=document.getElementById("txtCB_Month").value;
        	
        	 // var tbody=document.getElementById("tbody");
                service=baseResponse.getElementsByTagName("leng");
                if(service)
                 {
                	 for(i=0;i<service.length;i++)
                     {
                         
                              var items=new Array();
                              	
			                    
			                 items[0]=service[i].getElementsByTagName("cheque_memo_no")[0].firstChild.nodeValue;
                             items[1]=service[i].getElementsByTagName("CHEQUE_MEMO_TYPE_CODE")[0].firstChild.nodeValue;
                             items[2]=service[i].getElementsByTagName("cheque_memo_date")[0].firstChild.nodeValue;
                          
                             items[3]=service[i].getElementsByTagName("voucher_date")[0].firstChild.nodeValue;
                             items[4]=service[i].getElementsByTagName("account_head_code")[0].firstChild.nodeValue;
                             items[5]=service[i].getElementsByTagName("bank_ac_no")[0].firstChild.nodeValue;
                             items[6]=service[i].getElementsByTagName("bankName")[0].firstChild.nodeValue;
                          
                           
                             items[7]=service[i].getElementsByTagName("typedesc")[0].firstChild.nodeValue;
                             items[8]=service[i].getElementsByTagName("paydesc")[0].firstChild.nodeValue;
                          
                             items[9]=service[i].getElementsByTagName("particulars")[0].firstChild.nodeValue;
                             items[10]=service[i].getElementsByTagName("cheque_amount")[0].firstChild.nodeValue;
                           
                          
                           
                             
                            // var tbody=document.getElementById("tbody");
                             var mycurrent_row=document.createElement("TR");
                           //  items[4]="Employees";
                            for(j=0;j<11;j++)
                             {
                                 cell2=document.createElement("TD");
                                 if(j==10){
                                	 cell2.setAttribute('align','right');

                                 }else{
                                	 cell2.setAttribute('align','left');
 
                                 }
                                  if(items[j]!="null")
                                 {
                                     var currentText=document.createTextNode(items[j]);
                                 }
                                 else
                                 {
                                     var currentText=document.createTextNode('');
                                 }
                                 cell2.appendChild(currentText);
                                 mycurrent_row.appendChild(cell2);
                             }
                            
                            var cell=document.createElement("TD");
                            cell.align='CENTER';
                            var anc=document.createElement("A");
                            var url="";
                            var url="javascript:Show_new('"+cmbAcc_UnitCode+"','"+cmbOffice_code+"','"+txtCB_Year+"','"+txtCB_Month+"','"+items[0]+"')";
                            anc.href=url;
                            var txtedit=document.createTextNode("DETAILS");
                            anc.appendChild(txtedit);
                            cell.appendChild(anc);
                            
                            mycurrent_row.appendChild(cell);
                                tbody.appendChild(mycurrent_row);
                            
                     }
                 }
               
               
        }
}
function loadTableHO(baseResponse){

	   
    var flag=baseResponse.getElementsByTagName("flag")[0].firstChild.nodeValue;
    var tbody=document.getElementById("tbody");
	 //tbody.rows.length=0;
  // alert(" ... "+flag);
	 var t=0;
    for(t=tbody.rows.length-1;t>=0;t--)
        {
           tbody.deleteRow(0);
        } 
    if(flag=="failure")
    {
    	 
                alert("No Record exists");
      
    }
    else
    { 
    
    	
    	/*var cmbAcc_UnitCode=document.getElementById("cmbAcc_UnitCode").value;    
        var cmbOffice_code=document.getElementById("cmbOffice_code").value;*/
        var txtCB_Year=document.getElementById("txtCB_Year").value;
        var txtCB_Month=document.getElementById("txtCB_Month").value;
    	
    	 // var tbody=document.getElementById("tbody");
            service=baseResponse.getElementsByTagName("leng");
            if(service)
             {
            	 for(i=0;i<service.length;i++)
                 {
                     
                          var items=new Array();
                          	
                          items[0]=service[i].getElementsByTagName("unitname")[0].firstChild.nodeValue;
		                 items[1]=service[i].getElementsByTagName("cheque_memo_no")[0].firstChild.nodeValue;
                         items[2]=service[i].getElementsByTagName("CHEQUE_MEMO_TYPE_CODE")[0].firstChild.nodeValue;
                         items[3]=service[i].getElementsByTagName("cheque_memo_date")[0].firstChild.nodeValue;
                      
                         items[4]=service[i].getElementsByTagName("voucher_date")[0].firstChild.nodeValue;
                         items[5]=service[i].getElementsByTagName("account_head_code")[0].firstChild.nodeValue;
                         items[6]=service[i].getElementsByTagName("bank_ac_no")[0].firstChild.nodeValue;
                         items[7]=service[i].getElementsByTagName("bankName")[0].firstChild.nodeValue;
                      
                       
                         items[8]=service[i].getElementsByTagName("typedesc")[0].firstChild.nodeValue;
                         items[9]=service[i].getElementsByTagName("paydesc")[0].firstChild.nodeValue;
                      
                         items[10]=service[i].getElementsByTagName("particulars")[0].firstChild.nodeValue;
                         items[11]=service[i].getElementsByTagName("cheque_amount")[0].firstChild.nodeValue;
                         items[12]=service[i].getElementsByTagName("ACCOUNTING_UNIT_ID")[0].firstChild.nodeValue;
                         items[13]=service[i].getElementsByTagName("ACCOUNTING_FOR_OFFICE_ID")[0].firstChild.nodeValue;
                       
                      
                       
                         
                        // var tbody=document.getElementById("tbody");
                         var mycurrent_row=document.createElement("TR");
                       //  items[4]="Employees";
                        for(j=0;j<12;j++)
                         {
                             cell2=document.createElement("TD");
                             if(j==11){
                            	 cell2.setAttribute('align','right');

                             }else{
                            	 cell2.setAttribute('align','left');

                             }
                              if(items[j]!="null")
                             {
                                 var currentText=document.createTextNode(items[j]);
                             }
                             else
                             {
                                 var currentText=document.createTextNode('');
                             }
                             cell2.appendChild(currentText);
                             mycurrent_row.appendChild(cell2);
                         }
                        
                        var cell=document.createElement("TD");
                        cell.align='CENTER';
                        var anc=document.createElement("A");
                        var url="";
                        var url="javascript:Show_new('"+items[12]+"','"+items[13]+"','"+txtCB_Year+"','"+txtCB_Month+"','"+items[1]+"')";
                        anc.href=url;
                        var txtedit=document.createTextNode("DETAILS");
                        anc.appendChild(txtedit);
                        cell.appendChild(anc);
                        
                        mycurrent_row.appendChild(cell);
                            tbody.appendChild(mycurrent_row);
                        
                 }
             }
           
           
    }

}
var Voucher_list_SL;
function Show_new(unitcode,offid,yr,mon,checkno)
{
    if (Voucher_list_SL && Voucher_list_SL.open && !Voucher_list_SL.closed) 
    {
       Voucher_list_SL.resizeTo(500,500);
       Voucher_list_SL.moveTo(250,250); 
       Voucher_list_SL.focus();
    }
    else
    {
        Voucher_list_SL=null;
    }
    Voucher_list_SL= window.open("../../../../../org/FAS/FAS1/BillVerification/jsps/Cheque_Memo_SubList.jsp?cmbAcc_UnitCode="+unitcode+"&cmbOffice_code="+offid+"&yr="+yr+"&mon="+mon+"&checkno="+checkno,"ReceiptDateSearch","status=1,height=500,width=500,resizable=YES, scrollbars=yes"); 
    Voucher_list_SL.moveTo(250,250);  
    Voucher_list_SL.focus();
    
}

window.onunload=function()
{
if (Voucher_list_SL && Voucher_list_SL.open && !Voucher_list_SL.closed) Voucher_list_SL.close();
}
function btncancel()
{

 self.close();
}
