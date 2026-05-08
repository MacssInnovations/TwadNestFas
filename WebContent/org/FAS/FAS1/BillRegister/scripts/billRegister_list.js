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
	//alert(Command);
        var cmbAcc_UnitCode=document.getElementById("cmbAcc_UnitCode").value;    
        var cmbOffice_code=document.getElementById("cmbOffice_code").value;
        var txtCB_Year=document.getElementById("txtCB_Year").value;
        var txtCB_Month=document.getElementById("txtCB_Month").value;
        var sancidwith=document.getElementsByName("sancidwith");
        for(var i=0;i<sancidwith.length;i++){
        	if(sancidwith[i].checked){
        		break;
        	}
        }
        var optionId;
        
        if(document.frm_BillTokenRegisterEntry_WithProceeding.optionId[0].checked==true)
	    {
        	optionId=document.frm_BillTokenRegisterEntry_WithProceeding.optionId[0].value;
	    }else{
	    	optionId=document.frm_BillTokenRegisterEntry_WithProceeding.optionId[1].value;
	    }
        if(txtCB_Year=="")
        {
        	alert("Enter CashbookYear");
        }
        
        else
        {
                	   	
                var url="../../../../../billRegister_list_servlet?Command=searchByMonth&cmbAcc_UnitCode="+cmbAcc_UnitCode+
                "&cmbOffice_code="+cmbOffice_code+"&txtCB_Year="+txtCB_Year+"&txtCB_Month="+txtCB_Month+"&optionId="+optionId+"&sancidwith="+sancidwith[i].value;			                
         //      alert(url);
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
			             
			            if(Command=="searchByMonth")
			            {
			                loadTable(baseResponse);
			            }
			            
		        }
	    }
}

function loadTable(baseResponse)
{
	
	
	 
	 
	 var tbody=document.getElementById("tbody");
	 
	 
	  var t = 0,k = 1;
		for (t = tbody.rows.length - 1; t >= 0; t--) {
			tbody.deleteRow(0);
		}
        var flag=baseResponse.getElementsByTagName("flag")[0].firstChild.nodeValue;
        if(flag=="failure")
        {
      	 
                    alert("No Record exists");
          
        }
        else
        { 
        	 var SancWith=baseResponse.getElementsByTagName("SancWith")[0].firstChild.nodeValue;
        	var cmbAcc_UnitCode=document.getElementById("cmbAcc_UnitCode").value;    
            var cmbOffice_code=document.getElementById("cmbOffice_code").value;
            var txtCB_Year=document.getElementById("txtCB_Year").value;
            var txtCB_Month=document.getElementById("txtCB_Month").value;
         
            
        	
        	 /* var tbody=document.getElementById("tbody");
        	  var t = 0,k = 1;
        		for (t = tbody.rows.length - 1; t >= 0; t--) {
        			tbody.deleteRow(0);
        		}*/
                service=baseResponse.getElementsByTagName("leng");
               
                if(service)
                 {
                	 for(i=0;i<service.length;i++)
                     {
                         
                              var items=new Array();
                             items[0]=service[i].getElementsByTagName("billno")[0].firstChild.nodeValue;
                             items[1]=service[i].getElementsByTagName("billdate")[0].firstChild.nodeValue;
                             items[2]=service[i].getElementsByTagName("sancno")[0].firstChild.nodeValue;
                             
                             if(document.getElementsByName("sancidwith")[0].checked==true)
                        	 {
                            	 items[3]=service[i].getElementsByTagName("sanprocno")[0].firstChild.nodeValue;
                            	/* alert("items[3] "+items[3]);
                            	 if(items[3]=="-"){
                            		 alert("if");
                            		 items[3]=items[2];
                            	 }else{
                            		 alert("else");
                            		 items[3]=items[3];
                            	 }*/
                                 
                        	 }else{
                        		 items[3]="";
                        	 }  
                             items[4]=service[i].getElementsByTagName("processingdate")[0].firstChild.nodeValue;
                             items[5]=service[i].getElementsByTagName("paytypecode")[0].firstChild.nodeValue;
                             items[6]=service[i].getElementsByTagName("paycode")[0].firstChild.nodeValue;
                             items[7]=service[i].getElementsByTagName("processing")[0].firstChild.nodeValue;
                            
                             items[8]=service[i].getElementsByTagName("sancamt")[0].firstChild.nodeValue;
                             items[9]=service[i].getElementsByTagName("billamt")[0].firstChild.nodeValue;
                             items[10]=service[i].getElementsByTagName("remarks")[0].firstChild.nodeValue;
                             items[11]=service[i].getElementsByTagName("subdesc")[0].firstChild.nodeValue;
                             
                             var tbody=document.getElementById("tbody");
                             var mycurrent_row=document.createElement("TR");
                             items[5]="Employees";
                             //alert("wel");
                            for(j=0;j<11;j++)
                             {
                            	if(j==2){
                            		
                            	}else{
                            		
                                 cell2=document.createElement("TD");
                                 cell2.setAttribute('align','left');
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
                             }
                            var cell=document.createElement("TD");
                            cell.align='CENTER';
                            var anc=document.createElement("A");
                            var url="";
                            var url="javascript:Show_new('"+cmbAcc_UnitCode+"','"+cmbOffice_code+"','"+txtCB_Year+"','"+txtCB_Month+"','"+items[0]+"','"+items[2]+"','"+SancWith+"','"+items[11]+"')";
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
function Show_new(unitcode,offid,yr,mon,bilno,sancno,SancWith,sl_gp)
{
	//alert(sl_gp);
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
    Voucher_list_SL= window.open("../../../../../org/FAS/FAS1/BillRegister/jsps/Bill_subList.jsp?cmbAcc_UnitCode="+unitcode+"&cmbOffice_code="+offid+"&yr="+yr+"&mon="+mon+"&bilno="+bilno+"&sancno="+sancno+"&SancWith="+SancWith+"&sl_gp="+sl_gp,"ReceiptDateSearch","status=1,height=500,width=500,resizable=YES, scrollbars=yes"); 
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
