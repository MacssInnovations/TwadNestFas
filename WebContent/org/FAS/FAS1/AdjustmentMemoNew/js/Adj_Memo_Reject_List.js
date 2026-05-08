var service;
var __pagination=11;
var destid;
var totalblock=0;


  var Ucode;
  var Offid;
  var txtCB_Year;
  var txtCB_Month;

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
        var cmbStatus=document.getElementById("cmbStatus").value;
        
        if(Command=="searchByMonth")
        {  
            var txtCB_Year=document.getElementById("txtCB_Year").value;
            var txtCB_Month=document.getElementById("txtCB_Month").value;
            if(txtCB_Year.length!=0 && txtCB_Month.length!=0)
            {
	                var url="../../../../../Adjust_Memo_Reject_List?Command=searchByMonth&cmbAcc_UnitCode="+cmbAcc_UnitCode+
	                "&cmbOffice_code="+cmbOffice_code+"&txtCB_Year="+txtCB_Year+"&txtCB_Month="+txtCB_Month+"&cmbStatus="+cmbStatus;
	              
	                var req=getTransport();
	                req.open("GET",url,true); 
	                req.onreadystatechange=function()
	                {
	                   handleResponse(req);
	                }   
	                   req.send(null);
               
            }
        }
       
       else if(Command=="searchByDate")
        {  
            var txtCB_Year=document.getElementById("txtCB_Year").value;
            var txtCB_Month=document.getElementById("txtCB_Month").value;
            var txtFrom_date=document.getElementById("txtFrom_date").value;
            var txtTo_date=document.getElementById("txtTo_date").value;
           if(txtCB_Year.length!=0 && txtCB_Month.length!=0 && txtFrom_date.length!=0 && txtTo_date.length!=0)
            {
                var url="../../../../../Adjust_Memo_Reject_List?Command=searchByDate&cmbAcc_UnitCode="+cmbAcc_UnitCode+
                "&cmbOffice_code="+cmbOffice_code+"&txtCB_Year="+
                txtCB_Year+"&txtCB_Month="+txtCB_Month+"&txtFrom_date="+txtFrom_date+"&txtTo_date="+txtTo_date+"&cmbStatus="+cmbStatus;
                
                var req=getTransport();
                req.open("GET",url,true); 
                req.onreadystatechange=function()
                {
                   handleResponse(req);
                }   
                   req.send(null);
                
           }
           else
            alert("Enter the Cash Book Year/Month and From date and To date");
           
        }
}

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
            var baseRes=req.responseXML.getElementsByTagName("resp")[0];
            var tagcommand=baseRes.getElementsByTagName("command")[0];
            var Command=tagcommand.firstChild.nodeValue;
             
            if(Command=="searchByMonth")
            {            	
                loadTable(baseRes);
            }
            else if(Command=="searchByDate")
            {
                loadTable(baseRes);
            }
        }
    }
}

function loadTable(baseRes)
{
   
                var flag=baseRes.getElementsByTagName("flag")[0].firstChild.nodeValue;
              
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
               
	                    var tbody=document.getElementById("tbody");
	                   
	                    if(tbody.rows.length >0)
	                    {       
		                       if(tbody.innerText !='undefined'  && tbody.innerText !=null  )
		                            tbody.innerText='';
		                       else 
		                            tbody.innerHTML='';                            
	                    }
	                 
	                    service=baseRes.getElementsByTagName("lengcheck");
	         
	                    if(service)
	                    {
	                    		Ucode=baseRes.getElementsByTagName("Ucode")[0].firstChild.nodeValue;
                               Offid=baseRes.getElementsByTagName("Offid")[0].firstChild.nodeValue;
                               txtCB_Year=baseRes.getElementsByTagName("txtCB_Year")[0].firstChild.nodeValue;
                               txtCB_Month=baseRes.getElementsByTagName("txtCB_Month")[0].firstChild.nodeValue;
                             
                               var tbody=document.getElementById("tbody");
                         
                               try{tbody.innerHTML="";}
                               catch(e) {tbody.innerText="";}
                          
                               var i=0;
                               totalblock=0;
                           
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

function loadPage(page)
{
            var i=0;
            var c=0;
            var p=__pagination*(page-1);
            document.RejectList.cmbpage.selectedIndex=page-1;
            var tbody=document.getElementById("tbody");
                 
            try{tbody.innerHTML="";}
            catch(e) {tbody.innerText="";}  
           
            if(service)
		    {
		                s=0;
		                var i=0;
		                var sumAmount=0.0;
		                for(i=0;i<service.length&& c<__pagination;i++)   
		                {
		                    sumAmount=parseFloat(sumAmount)+ parseFloat(service[i].getElementsByTagName("TOTAL_AMOUNT")[0].firstChild.nodeValue);
		                }
		                
		                for(i=p;i<service.length&& c<__pagination;i++)
		                {
		                        c++;
		                        var items=new Array();

		                        items[0]=service[i].getElementsByTagName("VOUCHER_NO")[0].firstChild.nodeValue;
		                       
		                        items[1]=service[i].getElementsByTagName("VOUCHER_DATE")[0].firstChild.nodeValue;
		                       
		                        items[2]=service[i].getElementsByTagName("OFFICE_NAME")[0].firstChild.nodeValue;
		                       
		                        items[3]=service[i].getElementsByTagName("PARTICULARS")[0].firstChild.nodeValue;
		                       
		                        items[4]=service[i].getElementsByTagName("TOTAL_AMOUNT")[0].firstChild.nodeValue;
		                      
		                        items[5]=service[i].getElementsByTagName("LETTER_NO")[0].firstChild.nodeValue;
		                       
		                        items[6]=service[i].getElementsByTagName("LETTER_DATE")[0].firstChild.nodeValue;
		                        
		                        items[7]=service[i].getElementsByTagName("ACCEPT_DATE")[0].firstChild.nodeValue;
			                   
		                     //   items[8]=service[i].getElementsByTagName("JOUR_DATE")[0].firstChild.nodeValue;
		                        
		                        var tbody=document.getElementById("tbody");
		                        var mycurrent_row=document.createElement("TR");
		                    
		                        for(m=0;m<1;m++)
		                        {
		                            cell2=document.createElement("TD");
		                            cell2.setAttribute('align','right');
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
		                        for(j=1;j<8;j++)
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
		                       		                       
		                        tbody.appendChild(mycurrent_row);
		                        
		                }
		            
           }          
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






              
        