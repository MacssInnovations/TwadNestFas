
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

function show1(Command)

{ 
	
        var cmbAcc_UnitCode=document.getElementById("cmbAcc_UnitCode").value;    
        var cmbOffice_code=document.getElementById("cmbOffice_code").value;
	
        var txtCB_Year=document.getElementById("txtCB_Year").value;
        var txtCB_Month=document.getElementById("txtCB_Month").value;
      
       	   	
                var url="../../../../../Sanc_Proc_Single_Report?Command=searchByMonth&cmbAcc_UnitCode="+cmbAcc_UnitCode+"&cmbOffice_code="+cmbOffice_code+"&txtCB_Year="+txtCB_Year+"&txtCB_Month="+txtCB_Month;			                
           
                var req=getTransport();
                req.open("GET",url,true);  
                req.onreadystatechange=function()
                {
                
                   handleResponse(req);
                };   

                req.send(null);
		               
		           
        }
     
     

/*function handleResponse(req)
{ 
	    if(req.readyState==4)
	    {
		        if(req.status==200)
		        {  
			            var baseResponse=req.responseXML.getElementsByTagName("response")[0];
			            var tagcommand=baseResponse.getElementsByTagName("command")[0];
			            var hid=baseResponse.getElementsByTagName("hid")[0].firstChild.nodeValue;
			            var Command=tagcommand.firstChild.nodeValue;
			          
			            if(Command=="searchByMonth")
			            {
			            	
			            	if(hid=="OF"){
			                loadTable(baseResponse);
			            }if(hid=="HO"){
			            	 loadTableHO(baseResponse);
			            }
			            }
			            
		        }
	    }
}

function loadTable(baseResponse)
{
	
	//alert('test');
	 
	
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
         
           var WS_Amount=baseResponse.getElementsByTagName("AMOUNT")[0].firstChild.nodeValue;
        	if(WS_Amount=="WOS")
        		document.getElementById("WS_Amt").style.display="none";
        	else
        		document.getElementById("WS_Amt").style.display="block";
        	  var tbody=document.getElementById("tbody");
        	  var t = 0,k = 1;
        		for (t = tbody.rows.length - 1; t >= 0; t--) {
        			tbody.deleteRow(0);
        		}
                service=baseResponse.getElementsByTagName("leng");
                //alert(service.length);    
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
                            	 //alert('test check');
                            	 items[3]=service[i].getElementsByTagName("sanprocno")[0].firstChild.nodeValue;
                            	 //alert("items[11]"+items[3]);
                            	// alert("items[3] "+items[3]);
                            	 if(items[3]=="-"){
                            		// alert("if");
                            		 items[3]=items[2];
                            	 }else{
                            		 //alert("else");
                            		 items[3]=items[3];
                            	 }
                        	 }else{
                        		 items[3]="";
                        	 }  
                             
                             items[4]=service[i].getElementsByTagName("processingdate")[0].firstChild.nodeValue;
                             items[5]=service[i].getElementsByTagName("paytypecode")[0].firstChild.nodeValue;
                           
                             items[6]=service[i].getElementsByTagName("paycode")[0].firstChild.nodeValue;
                         
                             items[7]=service[i].getElementsByTagName("processing")[0].firstChild.nodeValue;                            
                             items[8]=service[i].getElementsByTagName("sancamt")[0].firstChild.nodeValue;
                             items[9]=service[i].getElementsByTagName("billamt")[0].firstChild.nodeValue;
                           //  items[10]=service[i].getElementsByTagName("AMOUNT")[0].firstChild.nodeValue;
                             items[10]=service[i].getElementsByTagName("remarks")[0].firstChild.nodeValue;
                             items[11]=service[i].getElementsByTagName("subdesc")[0].firstChild.nodeValue;
                          
                             var tbody=document.getElementById("tbody");
                             var mycurrent_row=document.createElement("TR");
                             items[5]="Employees";
                             var currentText="";
                           
                            for(j=0;j<11;j++)
                             {
                                
                            	if(j==2){
                            		
                            	}else
                            	{
                            		//  alert('test44q');
                            		cell2=document.createElement("TD");
                            	     if(items[j]!="null")
                                     {
                                          currentText=document.createTextNode(items[j]);
                                     }
                                     else
                                     {
                                          currentText=document.createTextNode('');
                                     }
                                   // currentText=document.createTextNode(items[j]);
                                    cell2.appendChild(currentText);
                                    mycurrent_row.appendChild(cell2);
                            	}
                            	
                            		else{
                            			if(j==10)
                                    	{
                                    		
                                    	 if (items[j]=="WOS"){
                                    		
                                    		}else{
                                    			currentText=document.createTextNode(items[j]);
                                    		}
                                    	}
                            		
                                 cell2=document.createElement("TD");
                                 cell2.setAttribute('align','left');
                                 if(items[j]!="null")
                                 {
                                      currentText=document.createTextNode(items[j]);
                                 }
                                 else
                                 {
                                      currentText=document.createTextNode('');
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
function  loadTableHO(baseResponse){

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
      
           var txtCB_Year=document.getElementById("txtCB_Year").value;
           var txtCB_Month=document.getElementById("txtCB_Month").value;
        
           
       	
       	  var tbody=document.getElementById("tbody");
       	  var t = 0,k = 1;
       		for (t = tbody.rows.length - 1; t >= 0; t--) {
       			tbody.deleteRow(0);
       		}
               service=baseResponse.getElementsByTagName("leng");
              // alert(service.length);    
               if(service)
                {
               	 for(i=0;i<service.length;i++)
                    {
                        
                             var items=new Array();
                             items[1]=service[i].getElementsByTagName("unitname")[0].firstChild.nodeValue;
                            items[2]=service[i].getElementsByTagName("billno")[0].firstChild.nodeValue;
                            items[3]=service[i].getElementsByTagName("billdate")[0].firstChild.nodeValue;
                            items[4]=service[i].getElementsByTagName("sancno")[0].firstChild.nodeValue;
                           
                            if(document.getElementsByName("sancidwith")[0].checked==true)
                       	 {
                           	 
                           	 items[5]=service[i].getElementsByTagName("sanprocno")[0].firstChild.nodeValue;
                           	 
                       	 }else{
                       		 items[5]="";
                       	 }  
                           
                            items[6]=service[i].getElementsByTagName("processingdate")[0].firstChild.nodeValue;
                            items[7]=service[i].getElementsByTagName("paytypecode")[0].firstChild.nodeValue;
                            items[8]=service[i].getElementsByTagName("paycode")[0].firstChild.nodeValue;
                      
                           items[9]=service[i].getElementsByTagName("processing")[0].firstChild.nodeValue;                            
                            items[10]=service[i].getElementsByTagName("sancamt")[0].firstChild.nodeValue;
                            items[11]=service[i].getElementsByTagName("billamt")[0].firstChild.nodeValue;
                            items[12]=service[i].getElementsByTagName("remarks")[0].firstChild.nodeValue;
                            items[13]=service[i].getElementsByTagName("subdesc")[0].firstChild.nodeValue;
                           
                            items[14]=service[i].getElementsByTagName("ACCOUNTING_UNIT_ID")[0].firstChild.nodeValue;
                            items[15]=service[i].getElementsByTagName("ACCOUNTING_UNIT_OFFICE_ID")[0].firstChild.nodeValue;
                            var tbody=document.getElementById("tbody");
                            var mycurrent_row=document.createElement("TR");
                            items[7]="Employees";
                            items[1]=items[1]+" ("+items[14]+")";
                           for(j=1;j<13;j++)
                            {
                        	 //  alert(items[j]);
                           	if(j==4){
                           		
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
                           var url="javascript:Show_new('"+items[14]+"','"+items[15]+"','"+txtCB_Year+"','"+txtCB_Month+"','"+items[2]+"','"+items[3]+"','"+SancWith+"','"+items[13]+"')";
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
}*/
function btncancel()
{

 self.close();
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

           var tbody=document.getElementById("tbody");
           var t=0;
        
         
           for(t=tbody.rows.length-1;t>=0;t--)
               {
                  tbody.deleteRow(0);
               } 
            
          if(Command=="searchByMonth")
            {
        	  

              var cmbAcc_UnitCode=document.getElementById("cmbAcc_UnitCode").value;    
              var cmbOffice_code=document.getElementById("cmbOffice_code").value;
      	
              var txtCB_Year=document.getElementById("txtCB_Year").value;
              var txtCB_Month=document.getElementById("txtCB_Month").value;
        	  
        	  
               var len=baseResponse.getElementsByTagName("Hrnoteno").length;
                	
               
               
                if(len>0)
                {
                	 var tbody=document.getElementById("tbody");
                    for(var i=0;i<len;i++)
                    {
                        var HR_NOTE_NO=baseResponse.getElementsByTagName("Hrnoteno")[i].firstChild.nodeValue;
                       
                        var tr = document.createElement("tr");
                        var td1=document.createElement("td");
                        var HR_NOTE_NO1=document.createTextNode(HR_NOTE_NO);
                        td1.appendChild(HR_NOTE_NO1);
                        td1.style.fontSize="14px";
                        
                       
                        var td2=document.createElement("td");
                        var NOTE_DATE=baseResponse.getElementsByTagName("hrnotedate")[i].firstChild.nodeValue;
                        var NOTE_DATE1=document.createTextNode(NOTE_DATE);
                        td2.appendChild(NOTE_DATE1);
                        td2.style.fontSize="14px";
                      
                        var td3=document.createElement("td");
                        var SANCTION_PROCEEDING_NO=baseResponse.getElementsByTagName("SANCTION_PROCEEDING_NO")[i].firstChild.nodeValue;
                        var NOTE_AMOUNT1=document.createTextNode(SANCTION_PROCEEDING_NO);
                        td3.appendChild(NOTE_AMOUNT1);
                        td3.style.fontSize="14px";
                       
                        var td4=document.createElement("td");
                        var NOTE_PREPARED_BY=baseResponse.getElementsByTagName("SANCTION_PROCEEDING_Date1")[i].firstChild.nodeValue;
                        var NOTE_PREPARED_BY1=document.createTextNode(NOTE_PREPARED_BY);
                        td4.appendChild(NOTE_PREPARED_BY1);
                        td4.style.fontSize="14px";
                       
                        var td5=document.createElement("td");
                        var bill_major_type_desc=baseResponse.getElementsByTagName("bill_major_type_desc1")[i].firstChild.nodeValue;
                        var bill_major_type_desc1=document.createTextNode(bill_major_type_desc);
                        td5.appendChild(bill_major_type_desc1);
                        td5.style.fontSize="14px";
                        
                       
                        var td6=document.createElement("td");
                        var bill_minor_type_desc=baseResponse.getElementsByTagName("bill_minor_type_desc1")[i].firstChild.nodeValue;
                        //alert(service);
                        var bill_minor_type_desc1=document.createTextNode(bill_minor_type_desc);
                        td6.appendChild(bill_minor_type_desc1);
                        td6.style.fontSize="14px";
                       
                       
                        var td7=document.createElement("td");
                        var bill_sub_type_desc=baseResponse.getElementsByTagName("bill_sub_type_desc1")[i].firstChild.nodeValue;
                       
                        var bill_sub_type_desc1=document.createTextNode(bill_sub_type_desc);
                        td7.appendChild(bill_sub_type_desc1);
                        td7.style.fontSize="14px";
                        
                        
                        var td8=document.createElement("td");
                        var anc=document.createElement("A");
                       // var url="";
                        var url="javascript:Show_new('"+cmbAcc_UnitCode+"','"+cmbOffice_code+"','"+txtCB_Year+"','"+txtCB_Month+"',"+SANCTION_PROCEEDING_NO+")";
                        anc.href=url;
                        var txtedit=document.createTextNode("DETAILS");
                        //td8.appendChild(txtedit);
                       // td8.style.fontSize="14px";
                      
                        anc.appendChild(txtedit);
                        td8.appendChild(anc);
                       
                        
              

                        tr.appendChild(td1);
                        tr.appendChild(td2);
                        tr.appendChild(td3);
                        tr.appendChild(td4);
                        tr.appendChild(td5);
                        tr.appendChild(td6);
                        tr.appendChild(td7);
                     
                       tr.appendChild(td8);
                        
                        tbody.appendChild(tr);

        }
    }
    }
        }
    }
}

function Show_new(unitcode,offid,yr,mon,sancno)
{
	/*//alert(sl_gp);
    if (Voucher_list_SL && Voucher_list_SL.open && !Voucher_list_SL.closed) 
    {
       Voucher_list_SL.resizeTo(500,500);
       Voucher_list_SL.moveTo(250,250); 
       Voucher_list_SL.focus();
    }
    else
    {
        Voucher_list_SL=null;
    }*/
    //alert(":::"+unitcode+offid+yr+mon+bilno+sancno);
    Voucher_list_SL= window.open("../../../../../org/FAS/FAS1/ProceedingGeneration/jsps/Bill_sublist1.jsp?cmbAcc_UnitCode="+unitcode+"&cmbOffice_code="+offid+"&yr="+yr+"&mon="+mon+"&sancno="+sancno); 
    Voucher_list_SL.moveTo(250,250);  
    Voucher_list_SL.focus();
    
}