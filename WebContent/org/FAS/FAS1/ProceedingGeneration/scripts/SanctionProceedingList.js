
var seq=0;
var com_id;
var service;
var __pagination=11;
var destid;
var totalblock=0;


  var Ucode;
  var Offid;
  var txtCB_Year;
  var txtCB_Month;

function AjaxFunction() {
	
	var xmlrequest = false;
	try {
		xmlrequest = new ActiveXObject("Msxml2.XMLHTTP");
	} catch (e1) {
		try {
			xmlrequest = new ActiveXObject("Microsoft.XMLHTTP");
		} catch (e2) {
			xmlrequest = false;
		}
	}
	if (!xmlrequest && typeof XMLHttpRequest != 'undefined') {
		xmlrequest = new XMLHttpRequest();
	}
	return xmlrequest;
}

function numbersonly(e,t)
{
         var unicode=e.charCode? e.charCode : e.keyCode;
         if(unicode==13)
         {
	          try{t.blur(); }catch(e){}
	          return true;
        
         }
         if (unicode!=8 && unicode !=9)
         {
	          if (unicode<48||unicode>57 ) 
	          {
	                return false 
	          }
         }
}


function manipulate(xmlrequest) {
	if(xmlrequest.readyState==4)
    {
        if(xmlrequest.status==200)
        {  
             var baseResponse = xmlrequest.responseXML.getElementsByTagName("response")[0];
			//alert(baseResponse);
			var tagCommand = baseResponse.getElementsByTagName("command")[0];
			//alert(tagCommand);
			var command = tagCommand.firstChild.nodeValue;
			//alert(command);
             
            if(command=="searchByMonth")
            {
                loadTable(baseResponse);
            }
            else if(command=="searchByDate")
            {
                loadTable(baseResponse);
            }
            else if(command=="details")
            {
                loadDetails(baseResponse);
            }
        }
    }
}
function doFunction(txt)
{
	
	
	var cmbAcc_UnitCode=document.getElementById("cmbAcc_UnitCode").value    
    var cmbOffice_code=document.getElementById("cmbOffice_code").value
    var cmbStatus=document.getElementById("cmbStatus").value;
	
	
	 if(txt=="searchByMonth")
     {  
		 
		
         var txtCB_Year=document.getElementById("txtCB_Year").value;
         var txtCB_Month=document.getElementById("txtCB_Month").value;
         //alert(txtCB_Month.length+"month")
         if(txtCB_Year.length!=0 && txtCB_Month.length!=0)
         {
           
             var url="../../../../../SanctionProceedingList?Command=searchByMonth&cmbAcc_UnitCode="+cmbAcc_UnitCode+
             "&cmbOffice_code="+cmbOffice_code+"&txtCB_Year="+txtCB_Year+"&txtCB_Month="+txtCB_Month+"&cmbStatus="+cmbStatus;
             //alert(url);
             var xmlrequest = AjaxFunction();
           	xmlrequest.open("POST", url, true);
           	xmlrequest.onreadystatechange = function() {
           		manipulate(xmlrequest);
           		}

           	xmlrequest.send(null);

         }
         else
         {
        	 alert("Enter Year And Month");
        	 
         }
         
     }
    
    else if(txt=="searchByDate")
     {  
    	
         var txtCB_Year=document.getElementById("txtCB_Year").value;

         var txtCB_Month=document.getElementById("txtCB_Month").value;
         var txtFrom_date=document.getElementById("txtFrom_date").value;
         var txtTo_date=document.getElementById("txtTo_date").value;
        if(txtCB_Year.length!=0 && txtCB_Month.length!=0 && txtFrom_date.length!=0 && txtTo_date.length!=0)
         {
     
             var url="../../../../../SanctionProceedingList?Command=searchByDate&cmbAcc_UnitCode="+cmbAcc_UnitCode+
             "&cmbOffice_code="+cmbOffice_code+"&txtCB_Year="+
             txtCB_Year+"&txtCB_Month="+txtCB_Month+"&txtFrom_date="+txtFrom_date+"&txtTo_date="+txtTo_date+"&cmbStatus="+cmbStatus;
             //alert(url);
             var xmlrequest = AjaxFunction();
           	xmlrequest.open("POST", url, true);
           	xmlrequest.onreadystatechange = function() {
           		manipulate(xmlrequest);
           		}

           	xmlrequest.send(null);

    	  
        }
        else
         alert("Enter the Cash Book Year/Month and From date and To date");
        
     }
	
	
	
	
}

function loadTable(baseResponse)
{
    
                var flag=baseResponse.getElementsByTagName("flag")[0].firstChild.nodeValue;
                if(flag=="failure")
                {
                     
                    alert("No Record exists");
                    s=0;
                    var tbody=document.getElementById("tbody");
                      try{tbody.innerHTML="";}
                  catch(e) {tbody.innerText="";}
                  
                     var cell=document.getElementById("divcmbpage");
                    cell.style.display="none";
                    var cell=document.getElementById("divpage");
                    cell.style.display="none";
           
                    var cell=document.getElementById("divnext");
                    cell.style.display="none";
                    var cell=document.getElementById("divpre");
                    cell.style.display="none";
                }
                else
                {   
                
             // alert('test');  
                
                  var tbody=document.getElementById("tbody");
                   
                    if(tbody.rows.length >0)
                    {       
                            //alert(tbody.innerText !='undefined'  && tbody.innerText !=null );
                            if(tbody.innerText !='undefined'  && tbody.innerText !=null  )
                                    tbody.innerText='';
                            else 
                                tbody.innerHTML='';
                            
                           // for(i=0;i<tbody.rows.length;i++)
                           //     tbody.deleteRows(i);
                    }
                 
                     service=baseResponse.getElementsByTagName("leng");
            
                    if(service)
                    {
                   // alert("length==============================================================="+service);
                   // alert("enter if"+service.length);
                    
                               Ucode=baseResponse.getElementsByTagName("Ucode")[0].firstChild.nodeValue;
                               Offid=baseResponse.getElementsByTagName("Offid")[0].firstChild.nodeValue;
                               txtCB_Year=baseResponse.getElementsByTagName("txtCB_Year")[0].firstChild.nodeValue;
                               txtCB_Month=baseResponse.getElementsByTagName("txtCB_Month")[0].firstChild.nodeValue;
                               
                                var tbody=document.getElementById("tbody");
                         
                                 try{tbody.innerHTML="";}
                                catch(e) {tbody.innerText="";}
                          
                                var i=0;
                      totalblock=0;
                     
                            //alert(parseInt(items1.length/__pagination));
                            if(service.length>0)
                            {
                            	
                            	
                                    totalblock=parseInt(service.length/__pagination);
                                    if(service.length%__pagination!=0)
                                    {
                                            totalblock=totalblock+1;
                                    }
                                    
                                    
                                    var cmbpage=document.getElementById("cmbpage");
                                   
                                   try{ cmbpage.innerHTML="";
                                   }catch(e){
                                    cmbpage.innerText="";
                                   }
                                     
                                    
                                    for(i=1;i<=totalblock;i++)
                                    {
                                            var option=document.createElement("OPTION");
                                            option.text=i;
                                            option.value=i;
                                            try
                                            {
                                                cmbpage.add(option);
                                            }catch(errorObject)
                                            {
                                            cmbpage.add(option,null);
                                            }
                                    } 
                            }
                            
                             loadPage(1);
            
            }
               
        }
}

function changepage()
{
//alert('test');
var page=document.frmSanctionProceedingsList.cmbpage.value;
//alert(page);
loadPage(parseInt(page));

}  


function loadPage(page)
{
	//alert("page"+page);
            var i=0;
            var c=0;
            var p=__pagination*(page-1);
           // alert("pValue"+p);
         // alert(page);
            document.frmSanctionProceedingsList.cmbpage.selectedIndex=page-1;
                var tbody=document.getElementById("tbody");
                 
                  try{tbody.innerHTML="";} 
                  catch(e) {tbody.innerText="";}  
                  
                  // alert(service);
             if(service)
                    {
                    ///////////////////////////////
                   
                   
                  s=0;
                  
                   var i=0;
                         
                   //Start: new added on 24th march     
                 var sumAmount=0.0;
                 for(i=0;i<service.length;i++)     // All pages total
                 {
                    sumAmount=parseFloat(sumAmount)+ parseFloat(service[i].getElementsByTagName("amount")[0].firstChild.nodeValue);
                 }
                 //end
               // alert("cValue"+c);
                
                for(i=p;i<service.length&& c<__pagination;i++)
                {
                     c++;
                     
                   // alert("enter c value"+c);
                    // ("Enter value"+i);
                     //alert("service.length"+service.length);
                         var items=new Array();
                        items[0]=service[i].getElementsByTagName("san_no")[0].firstChild.nodeValue;
                        items[1]=service[i].getElementsByTagName("san_Date")[0].firstChild.nodeValue;
                        items[2]=service[i].getElementsByTagName("remark")[0].firstChild.nodeValue;
                        items[3]=service[i].getElementsByTagName("amount")[0].firstChild.nodeValue;
                       
                       /*var Ucode=service.getElementsByTagName("Ucode")[0].firstChild.nodeValue;
                       var Offid=service.getElementsByTagName("Offid")[0].firstChild.nodeValue;
                       var txtCB_Year=service.getElementsByTagName("txtCB_Year")[0].firstChild.nodeValue;
                       var txtCB_Month=service.getElementsByTagName("txtCB_Month")[0].firstChild.nodeValue;*/
                        
                        var tbody=document.getElementById("tbody");
                        var mycurrent_row=document.createElement("TR");
                    
                      for(j=0;j<4;j++)
                        {
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
                        var cell=document.createElement("TD");
                        cell.align='CENTER';
                        var anc=document.createElement("A");
                        var url="javascript:Show('"+Ucode+"','"+Offid+"','"+txtCB_Year+"','"+txtCB_Month+"','"+items[0]+"')";
                        anc.href=url;
                        var txtedit=document.createTextNode("DETAILS");
                        anc.appendChild(txtedit);
                        cell.appendChild(anc);
                        mycurrent_row.appendChild(cell);
                        
                      
                    
                    
                        tbody.appendChild(mycurrent_row);
                       
                      
                         
                       
                }
            
                    
                    
                    
                    ///////////////////////////////
                    
                    }          
                       
                       
            // alert(page);
           // alert(page<totalblock);
           var cell=document.getElementById("divcmbpage");
                cell.style.display="block";
           var cell=document.getElementById("divpage");
                cell.style.display="block";
               
                 
                if(navigator.appName.indexOf("Microsoft")!=-1)
                    cell.innerText= ' / ' +totalblock + "            Total Amount ="+sumAmount;
                else
                    cell.innerHTML= ' / ' +totalblock+ "            Total Amount ="+sumAmount;

            if(page<totalblock)
            {
                var cell=document.getElementById("divnext");
                cell.style.display="block";
                try{cell.innerHTML="";}
                  catch(e) {cell.innerText="";}
                 var anc=document.createElement("A");
                var url="javascript:loadPage("+(page+1)+")";
                anc.href=url;
                //anc.setAttribute('style','text-decoratin:none');
                var txtedit=document.createTextNode("<<Next>>");
                anc.appendChild(txtedit);
                cell.appendChild(anc);
            }
            else
            {
                var cell=document.getElementById("divnext");
                cell.style.display="block";
                try{cell.innerHTML="";}
                  catch(e) {cell.innerText="";}
            
            }
             if(page>1)
            {
                var cell=document.getElementById("divpre");
                cell.style.display="block";
                //cell.innerText='';
                try{cell.innerHTML="";}
                  catch(e) {cell.innerText="";}
                 var anc=document.createElement("A");
                var url="javascript:loadPage("+(page-1)+")";
                anc.href=url;
                var txtedit=document.createTextNode("<<Previous>>");
                anc.appendChild(txtedit);
                cell.appendChild(anc);
            }
            else
            {
                var cell=document.getElementById("divpre");
                cell.style.display="block";
                try{cell.innerHTML="";}
                  catch(e) {cell.innerText="";}
            
            }
}



function changepagesize()
{

           __pagination=document.Bank_Rec_ListAll_Form.cmbpagination.value;
            var v=document.getElementsByName("sel");
            //alert(v);
        if(service)
        {
           
                            totalblock=0;
                            //alert(parseInt(items1.length/__pagination));
                            if(service.length>0)
                            {
                                    totalblock=parseInt(service.length/__pagination);
                                    if(service.length%__pagination!=0)
                                    {
                                            totalblock=totalblock+1;
                                    }
                                    
                                    
                                    var cmbpage=document.getElementById("cmbpage");
                                   
                                   try{ cmbpage.innerHTML="";
                                   }catch(e){
                                    cmbpage.innerText="";
                                   }
                                     
                                    
                                    for(i=1;i<=totalblock;i++)
                                    {
                                            var option=document.createElement("OPTION");
                                            option.text=i;
                                            option.value=i;
                                            try
                                            {
                                                cmbpage.add(option);
                                            }catch(errorObject)
                                            {
                                            cmbpage.add(option,null);
                                            }
                                    } 
                            }
                             loadPage(1);
            
            
            
        }
           
}


var Receipt_list_SL;

function Show(unitcode,offid,yr,mon,recNo)
{
    if (Receipt_list_SL && Receipt_list_SL.open && !Receipt_list_SL.closed) 
    {
       Receipt_list_SL.resizeTo(500,500);
       Receipt_list_SL.moveTo(250,250); 
       Receipt_list_SL.focus();
    }
    else
    {
        Receipt_list_SL=null
    }
    Receipt_list_SL= window.open("../../../../../org/FAS/FAS1/sanctionproceedings(multiplepayee)/jsp/sanctionproceedingListSL.jsp?cmbAcc_UnitCode="+unitcode+"&cmbOffice_code="+offid+"&yr="+yr+"&mon="+mon+"&sancNO="+recNo,"ReceiptDateSearch","status=1,height=500,width=500,resizable=YES, scrollbars=yes"); 
    Receipt_list_SL.moveTo(250,250);  
    Receipt_list_SL.focus();
    
}

window.onunload=function()
{
if (Receipt_list_SL && Receipt_list_SL.open && !Receipt_list_SL.closed) Receipt_list_SL.close();
}
function initialLoad(s1,s2,s3,s4,s5)
{
	//alert("enter into intialLoad");
	  var url="../../../../../SanctionProceedingList?Command=details&cmbAcc_UnitCode="+s1+
      "&cmbOffice_code="+s2+"&txtCB_Year="+
      s3+"&txtCB_Month="+s4+"&sanNo="+s5;
     // alert(url);
      var xmlrequest = AjaxFunction();
  	xmlrequest.open("POST", url, true);
  	xmlrequest.onreadystatechange = function() {
  		manipulate(xmlrequest);
  		}

  	xmlrequest.send(null);

	
	
}
function loadDetails(baseResponse)
{
	
	var flag = baseResponse.getElementsByTagName("flag")[0].firstChild.nodeValue;
	if (flag == "success") {
		

		//refresh();

		
		var len= baseResponse.getElementsByTagName("sl_no").length;
	//	alert("length"+len);
		
        for(k=0;k<len;k++)
        	{
        	//var empid = baseResponse.getElementsByTagName("empID")[k].lastChild.nodeValue;
        	var sl_no = baseResponse.getElementsByTagName("sl_no")[k].firstChild.nodeValue;
        	
    		var payeetype = baseResponse.getElementsByTagName("payeetype")[k].firstChild.nodeValue;
    		var payeename = baseResponse.getElementsByTagName("payeename")[k].firstChild.nodeValue;
    		
    		var paymentType= baseResponse.getElementsByTagName("paymentType")[k].firstChild.nodeValue;
    		 
    		var amount= baseResponse.getElementsByTagName("amount")[k].firstChild.nodeValue;
           var refno= baseResponse.getElementsByTagName("refno")[k].firstChild.nodeValue;
    		
    		var refdate= baseResponse.getElementsByTagName("refdate")[k].firstChild.nodeValue;
    		var detail= baseResponse.getElementsByTagName("details")[k].firstChild.nodeValue;

		var tbody = document.getElementById("tbody");
		var table = document.getElementById("Existing");

		var mycurrent_row = document.createElement("TR");
		mycurrent_row.id = sl_no;

		var cell1 = document.createElement("TD");
		var sl_no = document.createTextNode(sl_no);
		cell1.appendChild(sl_no);
		mycurrent_row.appendChild(cell1);

		var cell2 = document.createElement("TD");
		var payeetype = document.createTextNode(payeetype);
		cell2.appendChild(payeetype);
		mycurrent_row.appendChild(cell2);
		//--------------------------------------------------------		
		var cell3 = document.createElement("TD");
		var payeename = document.createTextNode(payeename);
		cell3.appendChild(payeename);
		mycurrent_row.appendChild(cell3);
		
		
		var cell4 = document.createElement("TD");
		var paymentType = document.createTextNode(paymentType);
		cell4.appendChild(paymentType);
		mycurrent_row.appendChild(cell4);
		
		
		var cell5 = document.createElement("TD");
		var amount = document.createTextNode(amount);
		cell5.appendChild(amount);
		mycurrent_row.appendChild(cell5);
		
		
		var cell6 = document.createElement("TD");
		var refno = document.createTextNode(refno);
		cell6.appendChild(refno);
		mycurrent_row.appendChild(cell6);
		
		
		var cell7 = document.createElement("TD");
		var refdate = document.createTextNode(refdate);
		cell7.appendChild(refdate);
		mycurrent_row.appendChild(cell7);
		
		
		
		var cell8 = document.createElement("TD");
		var detail = document.createTextNode(detail);
		cell8.appendChild(detail);
		mycurrent_row.appendChild(cell8);
		
		
		

		tbody.appendChild(mycurrent_row);
		
	}
	}else {
		alert("Failed to Add values");
	}
	
	
	
	
}


function exitfun()
{
	alert("Exit");
	window.close();
}

