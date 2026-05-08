/* Variables Declaration */
var service;
var __pagination=11;
var destid;
var totalblock=0;

var Ucode;
var Offid;
var txtCB_Year;
var txtCB_Month;
var Receipt_list_SL;


/* Browser Identification */

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






function Show(unitcode,offid,yr,mon,vocNo)
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
    Receipt_list_SL= window.open("../../../../../org/FAS/FAS1/YourSelfCheque/jsps/YourSelfCheque_ListAll_DetailPart.jsp?cmbAcc_UnitCode="+unitcode+"&cmbOffice_code="+offid+"&yr="+yr+"&mon="+mon+"&vocNo="+vocNo,"YourSelfCheque","status=1,height=500,width=500,resizable=YES, scrollbars=yes"); 
    Receipt_list_SL.moveTo(250,250);  
    Receipt_list_SL.focus();
    
}



window.onunload=function()
{
if (Receipt_list_SL && Receipt_list_SL.open && !Receipt_list_SL.closed) Receipt_list_SL.close();
}



/* Main Function */
function doFunction(Command,param)
{   
        var cmbAcc_UnitCode=document.getElementById("cmbAcc_UnitCode").value    
        var cmbOffice_code=document.getElementById("cmbOffice_code").value
        var cmbStatus=document.getElementById("cmbStatus").value;
        
        if(Command=="searchByMonth")
        {  
            var txtCB_Year=document.getElementById("txtCB_Year").value;
            var txtCB_Month=document.getElementById("txtCB_Month").value;
            if(txtCB_Year.length!=0 && txtCB_Month.length!=0)
            {
                var url="../../../../../YourSelfCheque_List.kv?Command=searchByMonth&cmbAcc_UnitCode="+cmbAcc_UnitCode+
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
                var url="../../../../../YourSelfCheque_List.kv?Command=searchByDate&cmbAcc_UnitCode="+cmbAcc_UnitCode+
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
            else if(Command=="searchByDate")
            {
                loadTable(baseResponse);
            }
        }
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
                
                    var tbody=document.getElementById("tbody");
                   
                    if(tbody.rows.length >0)
                    {       
                        if(tbody.innerText !='undefined'  && tbody.innerText !=null  )
                            tbody.innerText='';
                        else 
                            tbody.innerHTML='';                            
                    }
                 
                    service=baseResponse.getElementsByTagName("leng");
            
                    if(service)
                    {
                    
                    
                               Ucode=baseResponse.getElementsByTagName("Ucode")[0].firstChild.nodeValue;
                               Offid=baseResponse.getElementsByTagName("Offid")[0].firstChild.nodeValue;
                               txtCB_Year=baseResponse.getElementsByTagName("txtCB_Year")[0].firstChild.nodeValue;
                               txtCB_Month=baseResponse.getElementsByTagName("txtCB_Month")[0].firstChild.nodeValue;
                               
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



function changepage()
{
	var page=document.YourSelfChequeList.cmbpage.value;
	loadPage(parseInt(page));
}  




function loadPage(page)
{
            var i=0;
            var c=0;
            var p=__pagination*(page-1);
            document.YourSelfChequeList.cmbpage.selectedIndex=page-1;
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
                    sumAmount=parseFloat(sumAmount)+ parseFloat(service[i].getElementsByTagName("CHEQUE_AMOUNT")[0].firstChild.nodeValue);
                }
                
                for(i=p;i<service.length&& c<__pagination;i++)
                {
                        c++;
                        var items=new Array();
                       
                        items[0]=service[i].getElementsByTagName("VOUCHER_NO")[0].firstChild.nodeValue;
                        items[1]=service[i].getElementsByTagName("VOUCHER_DATE")[0].firstChild.nodeValue;
                        items[2]=service[i].getElementsByTagName("CHEQUE_DATE")[0].firstChild.nodeValue;
                        items[3]=service[i].getElementsByTagName("CHEQUE_NUMBER")[0].firstChild.nodeValue;
                        items[4]=service[i].getElementsByTagName("CHEQUE_AMOUNT")[0].firstChild.nodeValue;
                        items[5]=service[i].getElementsByTagName("REMARKS")[0].firstChild.nodeValue;
                        
                        
                        var tbody=document.getElementById("tbody");
                        var mycurrent_row=document.createElement("TR");
                    
                        for(j=0;j<6;j++)
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
                        
                        
                        /*
                        if(document.getElementById("cmbAcc_UnitCode").value==document.getElementById("unitid").value)
                        {
                            if(document.getElementById("cmbStatus").value=='L')
                            {
                                var cell=document.createElement("TD");
                                cell.align='CENTER';
                                var anc=document.createElement("A");
                                var url="javascript:printing('"+Ucode+"','"+Offid+"','"+txtCB_Year+"','"+txtCB_Month+"','"+items[0]+"','"+items[1]+"')";
                                anc.href=url;
                                var txtedit=document.createTextNode("PRINT");
                                anc.appendChild(txtedit);
                                cell.appendChild(anc);
                                mycurrent_row.appendChild(cell);
                            }
                            else//if(document.getElementById("cmbStatus").value=='C')
                            {
                                var cell=document.createElement("TD");
                                cell.align='CENTER';
                                var txtedit=document.createTextNode("--");
                                cell.appendChild(txtedit);
                                mycurrent_row.appendChild(cell);
                            }
                        }
                        else//if(document.getElementById("cmbStatus").value=='C')
                        {
                            var cell=document.createElement("TD");
                            cell.align='CENTER';
                            var txtedit=document.createTextNode("--");
                            cell.appendChild(txtedit);
                            mycurrent_row.appendChild(cell);
                        }
                        
                        */
                       
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



function changepagesize()
{

            __pagination=document.YourSelfChequeList.cmbpagination.value;
            var v=document.getElementsByName("sel");
            
            if(service)
            {
                            totalblock=0;                            
                            if(service.length>0)
                            {
                                    totalblock=parseInt(service.length/__pagination);
                                    if(service.length%__pagination!=0)
                                    {
                                            totalblock=totalblock+1;
                                    }
                                    
                                    
                                    var cmbpage=document.getElementById("cmbpage");
                                   
                                   try{ 
                                	   cmbpage.innerHTML="";
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



function btnsubmit()
{
	var v=document.getElementsByName("sel");
	if(v)
	{
		for(i=0;i<v.length;i++)
		{
			if(v[i].checked==true)
			{
				Minimize();
				opener.doParentCashRec_Edit(v[i].value);        
				return true;
			}
		}
	}
	else
	{
           alert('Select a Receipt Date ');
           return false; 
	}
}  




function Minimize() 
{
	window.resizeTo(0,0);
	window.screenX = screen.width;
	window.screenY = screen.height;
	opener.window.focus();
}


function btncancel()
{
	self.close();
}




function numbersonly(e)
{
        var unicode=e.charCode? e.charCode : e.keyCode;
       if(unicode==13)
        {
        
        }
        if (unicode!=8 && unicode !=9  )
        {
            if (unicode<48 || unicode>57 ) 
                return false 
        }
}





function exit()
{
	self.close();
}













