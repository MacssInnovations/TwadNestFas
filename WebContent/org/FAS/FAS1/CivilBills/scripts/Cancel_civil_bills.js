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
var seq=0;
var samelab=0;
function doFunction(Command,param)
{   
	
	    var cmbAcc_UnitCode=document.getElementById("cmbAcc_UnitCode").value;    
        var cmbOffice_code=document.getElementById("cmbOffice_code").value;
        var txtCB_Year=document.getElementById("txtCB_Year").value;
        var txtCB_Month=document.getElementById("txtCB_Month").value;
      
        var optionId=document.getElementById("Bills_list").value;
       
        if(txtCB_Year=="")
        {
        	alert("Enter CashbookYear");
        }
        
        else
        {

                var url="../../../../../Cancel_civil_bills?Command=searchByMonth&cmbAcc_UnitCode="+cmbAcc_UnitCode+
                "&cmbOffice_code="+cmbOffice_code+"&txtCB_Year="+txtCB_Year+"&txtCB_Month="+txtCB_Month+"&optionId="+optionId;			                
                var req=getTransport();
//                alert(url);
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
							if(baseResponse.getElementsByTagName("flag")[0] == undefined){
								var flag = "";
							}else{
								var flag=baseResponse.getElementsByTagName("flag")[0].firstChild.nodeValue;
							}
			            	//  alert("Flag>>>>>>>"+flag);
			               if(flag=="failure")
			               {
			            	     alert("No Record exists");
			            	   //loadTable(baseResponse);
			               }else{
			               loadTable(baseResponse);
			               }
			            }
			            
		        }
	    }
}

function clearalllist()
{
	var tbody=document.getElementById("tbody");
    try{tbody.innerHTML="";}
    catch(e) {tbody.innerText="";}
}
function chngevalue(id){

	if(document.getElementById("chckparameter"+id).checked==true){

		document.getElementById("param"+id).value='Checked';
		
	}else if(document.getElementById("chckparameter"+id).checked==false){
	
		document.getElementById("param"+id).value='UnChecked';
		
	}
}
function loadTable(baseResponse)
{
	seq=0;
       // var flag=baseResponse.getElementsByTagName("flag")[0].firstChild.nodeValue;
      //  alert(flag);
        var tbody=document.getElementById("tbody");
        try{tbody.innerHTML="";}
        catch(e) {tbody.innerText="";}
       /* if(flag=="failure")
        {
        	 
                    alert("No Record exists");
                    var tbody=document.getElementById("tbody");
                    try{tbody.innerHTML="";}
                    catch(e) {tbody.innerText="";}
        }
        else
        { 
        */
        	 samelab=0;
        	var cmbAcc_UnitCode=document.getElementById("cmbAcc_UnitCode").value;    
            var cmbOffice_code=document.getElementById("cmbOffice_code").value;
            var txtCB_Year=document.getElementById("txtCB_Year").value;
            var txtCB_Month=document.getElementById("txtCB_Month").value;
           
            service=baseResponse.getElementsByTagName("leng");
           // items[0]=service[i].getElementsByTagName("bill_no")[0].firstChild.nodeValue;
            //  alert(service.length)
                if(service)
                 { var bno="";
               //  alert(service.length);
                	 for(i=0;i<service.length;i++)
                     {
                        
                		 
		                   var items=new Array();
		                   var Bills_list=document.getElementById("Bills_list").value;    
		                   if(Bills_list==5)
		                   {
		                	   samelab=0; 
		                   }
		                   else if(Bills_list==6)
		                   {
		                	   samelab=0; 
		                   }
		                   else if(Bills_list==10)
		                   {
		                	   samelab=-1; 
		                   } 
		                   else 
		                   {
		                	   samelab++;
		                   }
//		          alert(Bills_list+samelab);
                             if(samelab>0 )
                             {
                            	    if(Bills_list==4) {
                             items[0]=service[i].getElementsByTagName("bill_no")[0].firstChild.nodeValue+'-'+service[i].getElementsByTagName("memo_date")[0].firstChild.nodeValue;
                             }else{
                            	 items[0]=service[i].getElementsByTagName("bill_no")[0].firstChild.nodeValue;
                             }
                            	 items[1]=service[i].getElementsByTagName("BILL_DATE")[0].firstChild.nodeValue;
                             items[2]=service[i].getElementsByTagName("sanction_proceeding_no")[0].firstChild.nodeValue;
                             items[3]=service[i].getElementsByTagName("sanctioned_amount")[0].firstChild.nodeValue;
                             items[4]=service[i].getElementsByTagName("STATUS")[0].firstChild.nodeValue;
                           //  items[5]=service[i].getElementsByTagName("memo_date")[0].firstChild.nodeValue;
                             
                             }
                             else if(samelab==0)
                              {
                            	 
                            	 
                            	 items[0]=service[i].getElementsByTagName("passno")[0].firstChild.nodeValue;
                            
                            	 items[1]=service[i].getElementsByTagName("passdate")[0].firstChild.nodeValue;
                                 items[2]=service[i].getElementsByTagName("billno")[0].firstChild.nodeValue;
                                 items[3]=service[i].getElementsByTagName("billamt")[0].firstChild.nodeValue;
                                 items[4]=service[i].getElementsByTagName("STATUS")[0].firstChild.nodeValue;

                                 document.getElementById("bid").innerHTML="Passorder No";
                                 document.getElementById("bdid").innerHTML="Passorder Date";
                                 document.getElementById("sanid").style.visibility="hidden";
                                 document.getElementById("sanamtid").style.visibility="hidden";
                               //  document.getElementById("sanid").innerHTML="Bill No";
                               //  document.getElementById("sanamtid").innerHTML="Bill Amount";
                                 
                              }
                             else  if(samelab < 0 )
                             {
                            	 items[0]=service[i].getElementsByTagName("billno")[0].firstChild.nodeValue;
                                 items[1]=service[i].getElementsByTagName("billdate")[0].firstChild.nodeValue;
                           	 items[2]=service[i].getElementsByTagName("memono")[0].firstChild.nodeValue;
                           
                           	 items[3]=service[i].getElementsByTagName("chequeamt")[0].firstChild.nodeValue;
                           	 items[4]=service[i].getElementsByTagName("memodate")[0].firstChild.nodeValue;
                           	
                     /*          previous codfe
                               document.getElementById("bid").innerHTML="ChequeMemo No";
                            document.getElementById("bdid").innerHTML="ChequeMemo Date";
                        	 document.getElementById("sanid").style.visibility="visible";
                          document.getElementById("sanamtid").style.visibility="visible";
                             document.getElementById("sanid").innerHTML="ChequeMemo No";
                             document.getElementById("sanamtid").innerHTML="Cheque Amount";
                             document.getElementById("statusid").innerHTML="ChequeMemo Date";
*/
//                                document.getElementById("bid").innerHTML="ChequeMemo No";
//                                document.getElementById("bdid").innerHTML="ChequeMemo Date";
                           	 document.getElementById("bid").innerHTML="Details";
                           //	document.getElementById("bdid").innerHTML="ChequeMemo Date";
                          	 document.getElementById("bdid").style.visibility="hidden";
                           	 document.getElementById("sanid").style.visibility="visible";
                             document.getElementById("sanamtid").style.visibility="visible";
                                document.getElementById("sanid").innerHTML="ChequeMemo No";
                                document.getElementById("sanamtid").innerHTML="Cheque Amount";
                                document.getElementById("statusid").innerHTML="ChequeMemo Date";
                             }
                          
                             var mycurrent_row=document.createElement("TR");
                           
                             cell2=document.createElement("TD");
                             cell2.style.textAlign='center'; 
                             var chcksel="";
                             checkparam=seq;
                            if(window.navigator.appName.toLowerCase().indexOf("netscape")==-1)
                            {
                            	chcksel=document.createElement("<input type='checkbox' name='chckparameter'"+seq+" onclick=chngevalue('"+checkparam+"')' id='chckparameter'"+seq+" value='"+checkparam+"' />");
                            }
                            else
                            {
                            	  var chcksel=document.createElement("input");
                                   chcksel.type="checkbox";
                                   //chcksel.checked='checked';
                                   chcksel.name="chckparameter"+seq;
                                   chcksel.id="chckparameter"+seq;
                                   chcksel.value=checkparam;
                                   chcksel.setAttribute('onclick','chngevalue('+checkparam+')');
                            	
                            }
                            cell2.appendChild(chcksel);
                            
                            var par_id=document.createElement("input");
                            par_id.type="hidden";
                            par_id.name="param"+seq;
                            par_id.id="param"+seq;
                            par_id.value='UnChecked';
                            cell2.appendChild(par_id);
                            mycurrent_row.appendChild(cell2);
                          if(Bills_list==10){
                        	  cell2=document.createElement("TD");
                              cell2.setAttribute('align','right');
                              cell2.setAttribute('colspan','2');
                              var v_id=document.createElement("input");
                              v_id.type="hidden";
                              v_id.name="billno";
                              v_id.id="billno";
                          
                              v_id.value=items[0];
                              cell2.appendChild(v_id);
                              
                              
                              
                              
                             
                              var v_date=document.createElement("input");
                              v_date.type="hidden";
                              v_date.name="billDate";
                              v_date.id="billDate";
                              v_date.value=items[1];
                              cell2.appendChild(v_date);
                              var v_a=document.createElement("a");
                              v_a.href='javascript:loadDet('+cmbAcc_UnitCode+','+cmbOffice_code+','+txtCB_Year+','+txtCB_Month+','+items[2]+')';
                            v_a.text='Details';
                              cell2.appendChild(v_a);
                              mycurrent_row.appendChild(cell2);  
                          }   else{
                            cell2=document.createElement("TD");
                            cell2.setAttribute('align','right');
                            var v_id=document.createElement("input");
                            v_id.type="hidden";
                            v_id.name="billno";
                            v_id.id="billno";
                        
                            v_id.value=items[0];
                            cell2.appendChild(v_id);
                            
                            
                            
                            
                            if(Bills_list==4) {
                            var currentText=document.createTextNode(items[0].split("-")[0]);
                            }else{
                            	  var currentText=document.createTextNode(items[0]);
                            }
                            cell2.appendChild(currentText);
                            mycurrent_row.appendChild(cell2);
                            
                            cell2=document.createElement("TD");
                            cell2.setAttribute('align','right');
                            var v_date=document.createElement("input");
                            v_date.type="hidden";
                            v_date.name="billDate";
                            v_date.id="billDate";
                            v_date.value=items[1];
                            cell2.appendChild(v_date);
                            var currentText=document.createTextNode(items[1]);
                            cell2.appendChild(currentText);
                            mycurrent_row.appendChild(cell2);
                          }
                            
                            cell2=document.createElement("TD");
                            
                            cell2.setAttribute('align','center');
                            var rem=document.createElement("input");
                            rem.type="hidden";
                            rem.name="sanno";
                            rem.id="sanno";
                            rem.value=items[2];
                            cell2.appendChild(rem);
                            var currentText=document.createTextNode(items[2]);
                            cell2.appendChild(currentText);
                            mycurrent_row.appendChild(cell2);
                            
                          
                            
                            cell2=document.createElement("TD");
                            cell2.setAttribute('align','right');
                            var tot_amt=document.createElement("input");
                            tot_amt.type="hidden";
                            tot_amt.name="sanamt";
                            tot_amt.id="sanamt";
                            tot_amt.value=items[3];
                            cell2.appendChild(tot_amt);
                            var currentText=document.createTextNode(items[3]);
                            cell2.appendChild(currentText);
                            mycurrent_row.appendChild(cell2);
                            
                            
                            
                            cell2=document.createElement("TD");
                            cell2.setAttribute('align','left');
                            var tot_amt=document.createElement("input");
                            tot_amt.type="hidden";
                            tot_amt.name="status";
                            tot_amt.id="status";
                            tot_amt.value=items[4];
                            cell2.appendChild(tot_amt);
                            var currentText=document.createTextNode(items[4]);
                            cell2.appendChild(currentText);
                            mycurrent_row.appendChild(cell2);
                            
                            tbody.appendChild(mycurrent_row);
                            seq++;
                     }
                 
               
               
        }
}
var Voucher_list_SL;
function loadDet(unitcode,offid,yr,mon,checkno)
{
//	alert('loadDet');
   
    Voucher_list_SL= window.open("../../../../../org/FAS/FAS1/CivilBills/jsps/Bill_Scrutiny_Details.jsp","ReceiptDateSearch","status=1,height=500,width=500,resizable=YES, scrollbars=yes"); 
    Voucher_list_SL.moveTo(250,250);  
    Voucher_list_SL.focus();
    
}

function cancel_live()
{
	var val=document.getElementById("Bills_list").value;
//	alert("coming heeeeee"+val);
	if(tbody.rows.length==0){
		alert("No Bill Found");
		return false;}
	
	else if(val==11){
	
	
		var cmbAcc_UnitCode=document.getElementById("cmbAcc_UnitCode").value;
		var cmbOffice_code=document.getElementById("cmbOffice_code").value;
		var txtCB_Year=document.getElementById("txtCB_Year").value;
		var txtCB_Month=document.getElementById("txtCB_Month").value;
		var TB_date="01/"+txtCB_Month+"/"+txtCB_Year;
		//alert(fromcal_dateCtrl.value+"b4url")
		
		var url="../../../../../Receipt_SL.view?Command=check_TB&TB_date="+TB_date+"&cmbAcc_UnitCode="+cmbAcc_UnitCode+"&cmbOffice_code="+cmbOffice_code;
		//alert(url);
		var req=getTransport();
		req.open("GET",url,true); 
		req.onreadystatechange=function()
		{
		//check_TB(req,fromcal_dateCtrl);

			 if(req.readyState==4)
			    {
			        if(req.status==200)
			        {  
			            var baseResponse=req.responseXML.getElementsByTagName("response")[0];
			            var tagcommand=baseResponse.getElementsByTagName("command")[0];
			            var Command=tagcommand.firstChild.nodeValue;
			            var flag=baseResponse.getElementsByTagName("flag")[0].firstChild.nodeValue;
			            alert("flag"+flag);
			            if(flag=="success")
			              {
			                 //doFunction('load_Receipt_No','null');   
			            	return true;
			              }
			             else if(flag=="failure")
			               {
			                   
			                    alert("Trial Balance Closed");//return false;//
			                    return false;
			                    			                   
			               }
			             else if(flag=="finyear")
			               {
			                          // This statement get executed when financial year ( Cash Book Control Details ) has not found for the given date 
			                 
			                    alert("Cash Book Control Not Found ");//return false;//
			                    return false;
			                    
			               }
			        }
			    }

		};   
		req.send(null);
		
	}
		
		
		

	/*var tbody=document.getElementById("tbody");
		if(tbody.rows.length==0){
		alert("No Bill Found");
		return false;
		}*/
		
}

var Voucher_list_SL;
function Show_new(unitcode,offid,yr,mon,bilno)
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

    Voucher_list_SL= window.open("../../../../../org/FAS/FAS1/PaymentEntry/jsps/MemoPayment_SubList.jsp?cmbAcc_UnitCode="+unitcode+"&cmbOffice_code="+offid+"&yr="+yr+"&mon="+mon+"&bilno="+bilno,"ReceiptDateSearch","status=1,height=500,width=500,resizable=YES, scrollbars=yes"); 
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
