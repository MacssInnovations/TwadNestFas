var __pagination=10; 
 
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
	//alert("inside function!!!!!");
	if(Command=="searchByMonth")
     {  
		 var cmbAcc_UnitCode=document.getElementById("cmbAcc_UnitCode").value; 
		 //alert("cmbAcc_UnitCode>>>>"+cmbAcc_UnitCode);
	     var cmbOffice_code=document.getElementById("cmbOffice_code").value;
	     //alert("cmbOffice_code>>>>"+cmbOffice_code);
		 var txtCB_Year=document.getElementById("txtCB_Year").value;
		 //alert("txtCB_Year>>>>"+txtCB_Year);
         var txtCB_Month=document.getElementById("txtCB_Month").value;
         //alert("txtCB_Month>>>>"+txtCB_Month);
         
         if(txtCB_Year.length!=0 && txtCB_Month.length!=0)
         {
	        // alert("inside If condition!!!!!!");   
        	 var url="../../../../../ReceiptNotRemittedList?Command=searchByMonth&cmbAcc_UnitCode="+cmbAcc_UnitCode+
	                "&cmbOffice_code="+cmbOffice_code+"&txtCB_Year="+txtCB_Year+"&txtCB_Month="+txtCB_Month;
	               // alert("url>>>>"+url);
	                var req=getTransport();
	                req.open("GET",url,true); 
	                req.onreadystatechange=function()
	                {
	                   handleResponse(req);
	                };   
	                   req.send(null);
            
         }
     }
	 
}
function handleResponse(req)
{ 
	//alert(req.readyState);
	if(req.readyState==4)
    {
		//alert(req.status);
    	if(req.status==200)
        {  
          //  alert(req.responseText);
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
   
                var flag=baseResponse.getElementsByTagName("flag")[0].firstChild.nodeValue;
                //alert("flag>>>>"+flag);
                if(flag=="failure")
                {
                     
	                    alert("No Record exists");
	                    s=0;
	                    
	                    
	                     var tbody=document.getElementById("grid_body");
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
                else if(flag=="success")
                {   
                   //alert("inside Success flag........"); 
                    var tbody=document.getElementById("grid_body");
                   
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
                    // alert(service.length);
                     
                    // __pagination=document.frmCashRemit_ListAll.cmbpagination.value;
                     
                    if(service)
                    {
                    
                    
                               Ucode=baseResponse.getElementsByTagName("Ucode")[0].firstChild.nodeValue;
                              // alert("Ucode>>>"+Ucode);
                               Offid=baseResponse.getElementsByTagName("Offid")[0].firstChild.nodeValue;
                              // alert("Offid>>>"+Offid);
                               txtCB_Year=baseResponse.getElementsByTagName("txtCB_Year")[0].firstChild.nodeValue;
                              // alert("txtCB_Year>>>"+txtCB_Year);
                               txtCB_Month=baseResponse.getElementsByTagName("txtCB_Month")[0].firstChild.nodeValue;
                               //alert("txtCB_Month>>>"+txtCB_Month);
                               
                                var tbody=document.getElementById("grid_body");
                         
                                 try{tbody.innerHTML="";}
                                catch(e) {tbody.innerText="";}
                          
                                var i=0;
                                    totalblock=0;
                            //alert(parseInt(items1.length/__pagination));
                                   // alert(service.length);
                                    
                             
                            if(service.length>0)
                            {
                            	
                            	//alert(parseInt(__pagination));
                            	
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
                                     
                                   // alert("totalblock>>>");
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
                                   // alert("cmbpage>>>");
                            }
                            
                             loadPage(1);
            
                     }  
        }
}

function loadPage(page)
{
    //alert(page);       
	var i=0;
            var c=0;
            var p=__pagination*(page-1);
           // alert(p)
            document.frmCashRemit_Monitor.cmbpage.selectedIndex=page-1;
            var tbody=document.getElementById("grid_body");
                 
            try{tbody.innerHTML="";}
            catch(e) {tbody.innerText="";}  
           
            if(service)
		    {
		                s=0;
		                var i=0;
		                
		                
		                for(i=p;i<service.length&& c<__pagination;i++)
		                {
		                        c++;
		                        var items=new Array();
		                      
		                        items[0]=service[i].getElementsByTagName("account_no")[0].firstChild.nodeValue;
		                       // alert("items[0]>>>"+items[0]);
		                        items[1]=service[i].getElementsByTagName("receipt_no")[0].firstChild.nodeValue;
		                        //alert("items[1]>>>"+items[1]);
		                        items[2]=service[i].getElementsByTagName("receipt_date")[0].firstChild.nodeValue;
		                        var date1=service[i].getElementsByTagName("receipt_date")[0].firstChild.nodeValue.split("/")
		                          txtCB_Year1=date1[2];
		                         txtCB_Month1=date1[1];
		                        // alert("txtCB_Year1>>>"+txtCB_Year1+"txtCB_Month1>>>"+txtCB_Month1);
		                        items[3]=service[i].getElementsByTagName("total_amount")[0].firstChild.nodeValue;
		                        //alert("items[3]>>>"+items[3]);
		                        
		                        var tbody=document.getElementById("grid_body");
		                        var mycurrent_row=document.createElement("TR");
		                    
		                        for(m=0;m<1;m++)
		                        {
		                            cell2=document.createElement("TD");
		                            cell2.setAttribute('align','center');
		                            if(items[m]!="null")
		                            {
		                                var currentText=document.createTextNode(items[m]);
		                            }
		                            else
		                            {
		                                var currentText=document.createTextNode('');
		                            }
		                            cell2.appendChild(currentText);
		                            mycurrent_row.appendChild(cell2);
		                        }
		                        for(j=1;j<2;j++)
		                        {
		                            cell2=document.createElement("TD");
		                            cell2.setAttribute('align','center');
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
		                        for(j=2;j<3;j++)
		                        {
		                            cell2=document.createElement("TD");
		                          
		                            if(items[j]!="0")
		                            {
		                            	 cell2.setAttribute('align','center');
		                                var currentText=document.createTextNode(items[j]);
		                            }
		                            else
		                            {
		                            	cell2.setAttribute('align','center');
		                                var currentText=document.createTextNode('-');
		                            }
		                            cell2.appendChild(currentText);
		                            mycurrent_row.appendChild(cell2);
		                        }
		                        for(j=3;j<4;j++)
		                        {
		                            cell2=document.createElement("TD");
		                           
		                            if(items[j]!="null")
		                            {
		                            	 cell2.setAttribute('align','center');
		                                var currentText=document.createTextNode(items[j]);
		                            }
		                            else
		                            {
		                            	cell2.setAttribute('align','center');
		                                var currentText=document.createTextNode('-');
		                            }
		                            cell2.appendChild(currentText);
		                            mycurrent_row.appendChild(cell2);
		                        }
		                                            
		                        tbody.appendChild(mycurrent_row);
		                        
		                }
		            
           }          
           var cell=document.getElementById("divcmbpage");
                cell.style.display="block";
           var cell=document.getElementById("divpage");
                cell.style.display="block";
               
                 
          
           if(page<totalblock)
           {
                var cell=document.getElementById("divnext");
                cell.style.display="block";
                try{cell.innerHTML="";}
                catch(e) {cell.innerText="";}
                var anc=document.createElement("A");
                var url="javascript:loadPage("+(page+1)+")";
                anc.href=url;                
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

function changepage()
{
//alert("Inside changePage!...");
var page=document.frmCashRemit_Monitor.cmbpage.value;
//alert("parseInt(page)>>>"+parseInt(page));
loadPage(parseInt(page));

} 
