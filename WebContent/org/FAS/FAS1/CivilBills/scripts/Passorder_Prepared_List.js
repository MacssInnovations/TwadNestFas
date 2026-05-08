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
	    var cmbAcc_UnitCode=document.getElementById("cmbAcc_UnitCode").value;    
        var cmbOffice_code=document.getElementById("cmbOffice_code").value;
        var txtCB_Year=document.getElementById("txtCB_Year").value;
        var txtCB_Month=document.getElementById("txtCB_Month").value;
        
        var optionId;//=document.getElementById("optionId").value;
        
        if(document.frmpassorder_pre_list.optionId[0].checked==true)
	    {
        	optionId=document.frmpassorder_pre_list.optionId[0].value;
	    }else{
	    	optionId=document.frmpassorder_pre_list.optionId[1].value;
	    }
        if(txtCB_Year=="")
        {
        	alert("Enter CashbookYear");
        }
        
        else
        {
               
                var url="../../../../../PassOrder_List?Command=searchByMonthPrepared&cmbAcc_UnitCode="+cmbAcc_UnitCode+
                "&cmbOffice_code="+cmbOffice_code+"&txtCB_Year="+txtCB_Year+"&txtCB_Month="+txtCB_Month+"&optionId="+optionId;			                
                var req=getTransport();
                req.open("GET",url,true);     
//                alert("url "+url);
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
//			        alert(req.responseText);   
		        	var baseResponse=req.responseXML.getElementsByTagName("response")[0];
//		        	alert("after"+req.responseText);
			            var tagcommand=baseResponse.getElementsByTagName("command")[0];
//			            alert("after"+req.responseText);
			           
			            var Command=tagcommand.firstChild.nodeValue;
//			            alert("command==>"+Command);
			             
			            if(Command=="searchByMonthPrepared")
			            {
			                loadTable(baseResponse);
			            }
			            
		        }
	    }
}

function loadTable(baseResponse)
{
//   alert("welcome");
        var flag=baseResponse.getElementsByTagName("flag")[0].firstChild.nodeValue;
        var tbody=document.getElementById("tbody");
    	 
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
                	
        	       	
                service=baseResponse.getElementsByTagName("leng");
                if(service)
                 {
                	 for(i=0;i<service.length;i++)
                     {
                         
                              var items=new Array();
                             
                             items[0]=service[i].getElementsByTagName("pass_order_no")[0].firstChild.nodeValue;
                             items[1]=service[i].getElementsByTagName("PASS_ORDER_DATE")[0].firstChild.nodeValue;
                             items[2]=service[i].getElementsByTagName("pass_order_prepared_by")[0].firstChild.nodeValue;
                             items[3]=service[i].getElementsByTagName("amount")[0].firstChild.nodeValue;
                             items[4]=service[i].getElementsByTagName("BILL_NO")[0].firstChild.nodeValue;
//                             alert(items[4]);
                             items[5]=service[i].getElementsByTagName("BILL_DATE")[0].firstChild.nodeValue;
//                             alert(items[5]);
                             items[6]=service[i].getElementsByTagName("BILL_AMOUNT")[0].firstChild.nodeValue;
//                             alert(items[6]);
                             //items[3]=service[i].getElementsByTagName("approved_by")[0].firstChild.nodeValue;
                           //  items[4]=service[i].getElementsByTagName("APPROVED_DATE")[0].firstChild.nodeValue;
                           //  items[5]=service[i].getElementsByTagName("remarks")[0].firstChild.nodeValue;
                           
                            
                          var mycurrent_row=document.createElement("TR");
                         
                          
                            for(j=0;j<7;j++)
                             {
                                 cell2=document.createElement("TD");
                                 
                                 if((j==3)||(j==6)){
                                	 cell2.setAttribute('align','right');
                                 }else{
                                	 cell2.setAttribute('align','center');
                                 }
                                 
//                                 cell2.setAttribute('align','right');
                                
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
var Voucher_list_SL;
function Show_new(unitcode,offid,yr,mon,passno)
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
    //alert(":::"+unitcode+offid+yr+mon+bilno+sancno);
    Voucher_list_SL= window.open("../../../../../org/FAS/FAS1/CivilBills/jsps/PassOrder_SubList.jsp?cmbAcc_UnitCode="+unitcode+"&cmbOffice_code="+offid+"&yr="+yr+"&mon="+mon+"&passno="+passno,"ReceiptDateSearch","status=1,height=500,width=500,resizable=YES, scrollbars=yes"); 
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
