var service;
var __pagination=11;
var destid;
var totalblock=0;

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
var Voucher_list_SL;

function Show(unitcode,offid,yr,mon,vou_no)
{
	     if (Voucher_list_SL && Voucher_list_SL.open && !Voucher_list_SL.closed) 
	     {
		       Voucher_list_SL.resizeTo(500,500);
		       Voucher_list_SL.moveTo(250,250); 
		       Voucher_list_SL.focus();
	     }
	     else
	     {
	    	   Voucher_list_SL=null
	     }
	     if(document.getElementById("cmbJournal_type").value!=64 && document.getElementById("cmbJournal_type").value!=67)
         {
	    	   Voucher_list_SL= window.open("../../../../../org/FAS/FAS1/TDA/jsps/TDA_TCA_ListAll_SL.jsp?cmbAcc_UnitCode="+unitcode+"&cmbOffice_code="+offid+"&cashbook_yr="+yr+"&cashbook_mn="+mon+"&voucher_no="+vou_no,"ReceiptDateSearch","status=1,height=500,width=500,resizable=YES, scrollbars=yes");
         }
	     else
	     {
	    	   
	    	   Voucher_list_SL= window.open("../../../../../org/FAS/FAS1/JournalSystem/jsps/Journal_General_ListAll_SL.jsp?cmbAcc_UnitCode="+unitcode+"&cmbOffice_code="+offid+"&yr="+yr+"&mon="+mon+"&recNo="+vou_no,"ReceiptDateSearch","status=1,height=500,width=500,resizable=YES, scrollbars=yes");
	     }
	     Voucher_list_SL.moveTo(250,250);  
	     Voucher_list_SL.focus();
     
}

window.onunload=function()
{
		 if (Voucher_list_SL && Voucher_list_SL.open && !Voucher_list_SL.closed) Voucher_list_SL.close();
}

function showGrid()
{
 
		 if(document.getElementById("cmbJournal_type").value==62 ||document.getElementById("cmbJournal_type").value==65)
		 {	         	   
              document.getElementById("firstDiv").style.display="block";
	         	   document.getElementById("secondDiv").style.display="none";
                           var tbody=document.getElementById("tbody");
                         try{tbody.innerHTML="";}
                         catch(e) {tbody.innerText="";}
                    }
	     else
	     {        
          
                           document.getElementById("firstDiv").style.display="none";
	         	   document.getElementById("secondDiv").style.display="block";
                           var tbody=document.getElementById("tbody_one");
                                 try{tbody.innerHTML="";}
                                 catch(e) {tbody.innerText="";}
	     }
		 
}

/// ----------------------------------------------------- for reporting purpose----------------
/*
function printing(unitcode,offid,yr,mon,recNo)
{
	     var cmbAcc_UnitCode=unitcode; 
	     var cmbOffice_code=offid;
	    
	     var txtCB_Year=yr;
	     var txtCB_Month=mon;
	     //alert(recNo);
	     var txtVoucher_No=recNo;  
	     var txtCreat_By_Module='BPF';
	    
	     var url="../../../../../Payment_print.view?Command=searchByMonth&cmbAcc_UnitCode="+cmbAcc_UnitCode+
	     "&cmbOffice_code="+cmbOffice_code+"&txtCB_Year="+txtCB_Year+"&txtCB_Month="+txtCB_Month+"&txtCreat_By_Module="+txtCreat_By_Module+
	     "&txtVoucher_No="+txtVoucher_No;
	    
	     document.frmBankPay_FinalBill.action=url;
	     document.frmBankPay_FinalBill.method="POST";
	     document.frmBankPay_FinalBill.submit();
	     return true;
}*/
/////////-------------------------- end report

function doFunction(Command,param)
{   
        var cmbAcc_UnitCode=document.getElementById("cmbAcc_UnitCode").value;    
        var cmbOffice_code=document.getElementById("cmbOffice_code").value;
        var cmbStatus=document.getElementById("cmbStatus").value;
        var cmbJournal_type=document.getElementById("cmbJournal_type").value;
        var supNo=document.getElementById("supNo").value;
        if(cmbJournal_type=="")
        {
        	alert("select Journal Type");
        }
        else if(supNo=="")
        	{
        	alert("Enter Supplement No");
        	return false;
        	}
        else
        {
        if(cmbJournal_type==62)
        	cmbJournal_type="TDAO";
        else if(cmbJournal_type==63)
        	cmbJournal_type="TDAA";
//        else if(cmbJournal_type==64)
//        	cmbJournal_type="TDAR";
        else if(cmbJournal_type==65)
        	cmbJournal_type="TCAO";
        else if(cmbJournal_type==66)
        	cmbJournal_type="TCAA";
//        else if(cmbJournal_type==67)
//        	cmbJournal_type="TCAR";
        var txtCB_Year=document.getElementById("txtCB_Year").value;
        var txtCB_Month=document.getElementById("txtCB_Month").value;
//        var txtFrom_date=document.getElementById("txtFrom_date").value;
//        var txtTo_date=document.getElementById("txtTo_date").value;
       
        var tbody=document.getElementById("tbody");
        try{tbody.innerHTML="";}
        catch(e) {tbody.innerText="";}
        if(Command=="searchByMonth")
        {   
        
          document.frmBankPay_FinalBill.paghide.value="searchByMonth";
                
		           if(txtCB_Year.length!=0 && txtCB_Month.length!=0)
		           {
		        	   		var txtCreat_By_Module='GJV';
			                var url="../../../../../TDA_TCA_List_supp?Command=searchByMonth&cmbAcc_UnitCode="+cmbAcc_UnitCode+
			                "&cmbOffice_code="+cmbOffice_code+"&txtCB_Year="+txtCB_Year+"&txtCB_Month="+txtCB_Month+
			                "&cmbStatus="+cmbStatus+"&journal_type="+cmbJournal_type+"&txtCreat_By_Module="+txtCreat_By_Module+"&supNo="+supNo;			                
			                var req=getTransport();
			                req.open("GET",url,true); 
			                req.onreadystatechange=function()
			                {
			                   handleResponse(req);
			                }   
			                req.send(null);
		               
		           }
		           else
			            	alert("Enter the Cash Book Year/Month");
        }
       
       else if(Command=="searchByDate")
        {  
      
    	   var txtFrom_date=document.getElementById("txtFrom_date").value;
    	   var txtTo_date=document.getElementById("txtTo_date").value;
    	   document.frmBankPay_FinalBill.paghide.value="searchByDate";
		           if(txtCB_Year.length!=0 && txtTo_date.length!=0)
		           {
			                var url="../../../../../TDA_TCA_List_supp?Command=searchByDate&cmbAcc_UnitCode="+cmbAcc_UnitCode+
			                "&cmbOffice_code="+cmbOffice_code+"&txtCB_Year="+
			                txtCB_Year+"&txtCB_Month="+txtCB_Month+"&txtFrom_date="+txtFrom_date+"&txtTo_date="+txtTo_date+
			                "&cmbStatus="+cmbStatus+"&journal_type="+cmbJournal_type+"&txtCreat_By_Module="+txtCreat_By_Module+"&supNo="+supNo;
			                var req=getTransport();
			                req.open("GET",url,true); 
			                req.onreadystatechange=function()
			                {
			                   handleResponse(req);
			                }   
		                    req.send(null);
		                
		           }
		           else
		        	   		alert("Enter the Cash Book From date and To date");
           
        }
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
			                loadTable(baseResponse,'searchByMonth');
			            }
			            else if(Command=="searchByDate")
			            {
			                loadTable(baseResponse,'searchByDate');
			            }
		        }
	    }
}

function loadTable(baseResponse,t)
{
   
        var flag=baseResponse.getElementsByTagName("flag")[0].firstChild.nodeValue;
      //  alert(flag);
        var jtype=baseResponse.getElementsByTagName("jtype")[0].firstChild.nodeValue;
       // alert("jtype::"+jtype);
        if(flag=="failure")
        {
       
            if(jtype=="TDAO" || jtype=="TCAO")
            {
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
                    alert("No Record exists");
            }
            else
            {
                     s=0;
                    var tbody=document.getElementById("tbody_one");
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
                    alert("No Record exists");
            }
        }
        else
        { 
         
                service=baseResponse.getElementsByTagName("leng");
                if(service)
                {
	                       Ucode=baseResponse.getElementsByTagName("Ucode")[0].firstChild.nodeValue;
	                       Offid=baseResponse.getElementsByTagName("Offid")[0].firstChild.nodeValue;
	                       txtCB_Year=baseResponse.getElementsByTagName("txtCB_Year")[0].firstChild.nodeValue;
	                       txtCB_Month=baseResponse.getElementsByTagName("txtCB_Month")[0].firstChild.nodeValue;	                       
                    	      if(jtype=="TDAO" || jtype=="TCAO")
                                {
                                    var tbody=document.getElementById("tbody");                    
                               }
                               else
                               {
                               var tbody=document.getElementById("tbody_one");
                               }
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
                                if(jtype=="TDAO" || jtype=="TCAO")
                                {
	                       loadPage(1,t);
                               }
                               else
                               {
                                loadPage_two(1,t);
                               }
	            
                 }
               
               
        }
}
function changepage()
{
		var page=document.frmBankPay_FinalBill.cmbpage.value;
                
               var t=document.frmBankPay_FinalBill.paghide.value;
                
                var jjtype=document.frmBankPay_FinalBill.cmbJournal_type.value;
                if(jjtype==62 || jjtype==65)
                {
             
                loadPage(parseInt(page),t);
                }
                else
                {
                loadPage_two(parseInt(page));
                }
                
		
}  

function loadPage_two(page)
{

                var i=0;
                var c=0;
                var p=__pagination*(page-1);
                document.frmBankPay_FinalBill.cmbpage.selectedIndex=page-1;
                var tbody=document.getElementById("tbody_one");
                try{tbody.innerHTML="";}
                catch(e) {tbody.innerText="";}  
            
                if(service)
                {
                                s=0;
                                var i=0;
                    
                        //Start: new added on 24th march     
                                var sumAmount=0.0;
                                for(i=0;i<service.length&& c<__pagination;i++)     // All pages total
                                {
                                        sumAmount=parseFloat(sumAmount)+ parseFloat(service[i].getElementsByTagName("Tot_Amt")[0].firstChild.nodeValue);
                                }
                         
                        for(i=p;i<service.length&& c<__pagination;i++)
                                    {
                                                c++;
                                                var items=new Array();
                                                items[0]=service[i].getElementsByTagName("vou_no")[0].firstChild.nodeValue;
                                                items[1]=service[i].getElementsByTagName("vou_date")[0].firstChild.nodeValue;
                                                items[2]=service[i].getElementsByTagName("unitName")[0].firstChild.nodeValue;
                                                items[3]=service[i].getElementsByTagName("Tot_Amt")[0].firstChild.nodeValue;                        
                                                var tbody=document.getElementById("tbody_one");
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
                                               
                                                        acc_adNo=service[i].getElementsByTagName("acc_adNo")[0].firstChild.nodeValue;
                                                        acc_adDate=service[i].getElementsByTagName("acc_adDate")[0].firstChild.nodeValue;
                                                        var cell=document.createElement("TD");
                                                        cell.align='CENTER';
                                                        var txtedit=document.createTextNode(acc_adNo);
                                                        cell.appendChild(txtedit);
                                                        mycurrent_row.appendChild(cell);
                                                      
                                                        var cel2=document.createElement("TD");
                                                        cel2.align='CENTER';
                                                        var txtedit=document.createTextNode(acc_adDate);
                                                        cel2.appendChild(txtedit);
                                                        mycurrent_row.appendChild(cel2);
                                               
                                                 //   --after applying Cancelled receipts
                                               //---------------------------------------end report    
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
                                var url="javascript:loadPage_two("+(page+1)+")";
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
                                var url="javascript:loadPage_two("+(page-1)+")";
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
function loadPage(page,t)
{
//alert("11111111111"+t);
//        if(jtype=="TDAO" || jtype=="TCAO")
//            {
                var i=0;
                var c=0;
                var p=__pagination*(page-1);
                document.frmBankPay_FinalBill.cmbpage.selectedIndex=page-1;
                var tbody=document.getElementById("tbody");
                try{tbody.innerHTML="";}
                catch(e) {tbody.innerText="";}  
                  
                if(service)
                {
                                s=0;
                                var i=0;
                    
                        //Start: new added on 24th march     
                                var sumAmount=0.0;
                                for(i=0;i<service.length&& c<__pagination;i++)     // All pages total
                                {
                                        sumAmount=parseFloat(sumAmount)+ parseFloat(service[i].getElementsByTagName("Tot_Amt")[0].firstChild.nodeValue);
                                }
                                //end
                   
                        for(i=p;i<service.length&& c<__pagination;i++)
                                    {
                                                c++;
                                                var items=new Array();
                                                items[0]=service[i].getElementsByTagName("vou_no")[0].firstChild.nodeValue;
                                                items[1]=service[i].getElementsByTagName("vou_date")[0].firstChild.nodeValue;
                                                items[2]=service[i].getElementsByTagName("Remak")[0].firstChild.nodeValue;
                                                items[3]=service[i].getElementsByTagName("Tot_Amt")[0].firstChild.nodeValue;                        
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
                                                if(t=="searchByMonth")
                                                {
                                                 var url="javascript:Show('"+Ucode+"','"+Offid+"','"+txtCB_Year+"','"+txtCB_Month+"','"+items[0]+"')";
                                                }
                                                else if(t=="searchByDate")
                                                 {
                                                 var spdate=items[1].split("/");
                                                var url="javascript:Show('"+Ucode+"','"+Offid+"','"+spdate[2]+"','"+spdate[1]+"','"+items[0]+"')";
                                               
                                                 }
                                                anc.href=url;
                                                var txtedit=document.createTextNode("DETAILS");
                                                anc.appendChild(txtedit);
                                                cell.appendChild(anc);
                                                mycurrent_row.appendChild(cell);
                                                if(document.getElementById("cmbJournal_type").value!=64 && document.getElementById("cmbJournal_type").value!=67)
                                                {
                                                                GJV_No=service[i].getElementsByTagName("GJV_No")[0].firstChild.nodeValue;
                                                            GJV_Date=service[i].getElementsByTagName("GJV_Date")[0].firstChild.nodeValue;
                                                            var cell=document.createElement("TD");
                                                        cell.align='CENTER';
                                                        var txtedit=document.createTextNode(GJV_No);
                                                        cell.appendChild(txtedit);
                                                        mycurrent_row.appendChild(cell);
                                                      
                                                        var cel2=document.createElement("TD");
                                                        cel2.align='CENTER';
                                                        var txtedit=document.createTextNode(GJV_Date);
                                                        cel2.appendChild(txtedit);
                                                        mycurrent_row.appendChild(cel2);
                                                }
                                                else
                                                {
                                                                journal=service[i].getElementsByTagName("journal_type")[0].firstChild.nodeValue;
                                                                var cell=document.createElement("TD");
                                                        cell.align='CENTER';
                                                        var txtedit=document.createTextNode(journal);
                                                        cell.appendChild(txtedit);
                                                        mycurrent_row.appendChild(cell);
                                                }
                                          
                                               
                                               
                                                 //   --after applying Cancelled receipts
                                               //---------------------------------------end report    
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
        //}
      
}



function changepagesize()
{
alert("changepagesize");
           __pagination=document.frmBankPay_FinalBill.cmbpagination.value;
            var v=document.getElementsByName("sel");
            //alert(v);
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
                                           {cmbpage.add(option,null);}
                                     } 
                             }
                             var jjtype=document.frmBankPay_FinalBill.cmbJournal_type.value;
                            if(jjtype==62 || jjtype==65)
                            {
                            loadPage(1);
                            }
                            else
                            {
                            loadPage_two(1);
                            }
                         
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
          //t.blur();
          //return true;-------------------- for taking action when press ENTER
        
        }
        if (unicode!=8 && unicode !=9  )
        {
            if (unicode<48 || unicode>57 ) 
                return false 
        }
     }



/////////////////////////////////////////////   Check Date() by User /////////////////////////////////////////////////////
function getCurrentYear() 
{
    var year = new Date().getYear();
    if(year < 1900) year += 1900;
    return year;
}

function getCurrentMonth() {
    return new Date().getMonth() + 1;
} 

function getCurrentDay() {
    return new Date().getDate();
}

function isValidDate(dateStr) {
	  
	  // Checks for the following valid date formats:
	  // MM/DD/YYYY
	  // Also separates date into month, day, and year variables
	  var datePat = /^(\d{2,2})(\/)(\d{2,2})\2(\d{4}|\d{4})$/;
	  
	  var matchArray = dateStr.match(datePat); // is the format ok?
	  if (matchArray == null) {
	   alert("Date must be in MM/DD/YYYY format")
	   return false;
	  }
	  
	  month = matchArray[3]; // parse date into variables
	  day = matchArray[1];
	  year = matchArray[4];
	  if (month < 1 || month > 12) { // check month range
	   alert("Month must be between 1 and 12");
	   return false;
	  }
	  if (day < 1 || day > 31) {
	   alert("Day must be between 1 and 31");
	   return false;
	  }
	  if ((month==4 || month==6 || month==9 || month==11) && day==31) {
	   alert("Month "+month+" doesn't have 31 days!")
	   return false;
	  }
	  if (month == 2) { // check for february 29th
	   var isleap = (year % 4 == 0 && (year % 100 != 0 || year % 400 == 0));
	   if (day>29 || (day==29 && !isleap)) {
	    alert("February " + year + " doesn't have " + day + " days!");
	    return false;
	     }
	  }
	  return true;  // date is valid
	 }



function checkdt(t)
{
	  
	    if(t.value.length==0)
	    	return false;
	    if(t.value.length==10  && t.value.indexOf('/',0)==2 && t.value.indexOf('/',3)==5)
	    {
	      
            var c=t.value;
//	        try{
//	        var f=DateFormat(t,c,event,true,'3');
//	        }catch(e){
            
          ///New code implemented on 28-03-2019  for year 2019 wrongly displayed 201 
            try{
                var f=isValidDate(c);
               }
           catch(e){
            
	        //exception  start
	        
	        t.value=c;
            var sc=t.value.split('/');
            var currenDay =sc[0];
            var currentMonth=sc[1];
            var currentYear=sc[2];
            
            if(currentYear<_Service_Period_Beg_Year)
            {
            alert('Entered date should be greater than or equal to '+_Service_Period_Beg_Year);
            t.value="";
            t.focus();
            return false;
            }
            //alert(currentYear == getCurrentYear()  && currentMonth == getCurrentMonth() && currenDay > getCurrentDay());
            if(currentYear > getCurrentYear())
            {
            
                    alert('Entered date should be less than current date ');
                    t.value="";
                    t.focus();
                    return false;
           } 
           else if(currentYear == getCurrentYear())
           {
                    if( currentMonth > getCurrentMonth())
                    {
                        alert('Entered date should be less than current date');
                        t.value="";
                        t.focus();
                        return false;
                    }
                    else if( currentMonth == getCurrentMonth())
                    {
                        if(currenDay > getCurrentDay() )
                        {
                                alert('Entered date should be less than current date ');
                                t.value="";
                                t.focus();
                                return false;
                        }
                    }
                    
            }
	            
            t.value=c;
            if(err!=0)
            {
                    t.value="";
                    return false;
            }
            return true;
	        
	        
	        //exception end
	        
	        }
	        if( f==true)
		    {
		           
		            t.value=c;
		            var sc=t.value.split('/');
		            var currenDay =sc[0];
		            var currentMonth=sc[1];
		            var currentYear=sc[2];
		            //alert(currentYear == getCurrentYear()  && currentMonth == getCurrentMonth() && currenDay > getCurrentDay());
		             if(currentYear<_Service_Period_Beg_Year)
		            {
		            alert('Entered date should be greater than or equal to '+_Service_Period_Beg_Year);
		            t.value="";
		            t.focus();
		            return false;
		            }
		            if(currentYear > getCurrentYear())
		            {
		            
		                    alert('Entered date should be less than current date');
		                    t.value="";
		                    t.focus();
		                    return false;
		           } 
		           else if(currentYear == getCurrentYear())
		            {
		                    if( currentMonth > getCurrentMonth())
		                    {
		                        alert('Entered date should be less than current date');
		                        t.value="";
		                        t.focus();
		                        return false;
		                    }
		                    else if( currentMonth == getCurrentMonth())
		                    {
		                        if(currenDay > getCurrentDay() )
		                        {
		                                alert('Entered date should be less than current date ');
		                                t.value="";
		                                t.focus();
		                                return false;
		                        }
		                    }
		                    
		            }
		            
		            t.value=c;
		           
		            return true;
		            
		    }
	        else
	        {
	                if(err!=0)
	                {
	                    t.value="";
	                    return false;
	                }
	        }
	            
	    }
	    else
	    {
	            alert('Date format  should be (dd/mm/yyyy)');
	            t.value="";
	            //t.focus();
	            return false
	    }
	    
}
function Check_Supplement_No()
{
      var txtCash_year=document.getElementById("txtCB_Year").value;
      var txtCash_Month_hid=document.getElementById("txtCB_Month").value;
     
       var url="../../../../../Supplement_Journal_Create.kv?Command=Check_Supplement_No1&txtCash_year="+txtCash_year+"&txtCash_Month_hid="+txtCash_Month_hid;
      // alert(url);
       var req=getTransport();
       req.open("GET",url,true); 
       req.onreadystatechange=function()
       {
           Check_Supplement_No_Response(req);
       }   
       req.send(null);
}

function Check_Supplement_No_Response(req)
{
  if(req.readyState==4)
    {
        if(req.status==200)
        {  
            var baseResponse=req.responseXML.getElementsByTagName("response")[0];
            var tagcommand=baseResponse.getElementsByTagName("command")[0];
            var Command=tagcommand.firstChild.nodeValue;
            var flag=baseResponse.getElementsByTagName("flag")[0].firstChild.nodeValue;

         if(flag=="failure")
              {
                var suppl_error=baseResponse.getElementsByTagName("suppl_error")[0].firstChild.nodeValue;
                alert(suppl_error);  
                //document.getElementById("txtCB_Year").value="";
                document.getElementById("supNo").options.length = 0;
              
              }
              else if(flag=="success")
              {
               var supNo1 = document.forms[0].supNo;
                 var supno = baseResponse.getElementsByTagName("supno"); 
                 for(var i=0; i<supno.length; i++)
                     {
                         var opt = document.createElement('option');
                         opt.value = supno[i].firstChild.nodeValue;
                         opt.innerHTML = supno[i].firstChild.nodeValue; //instead of using textnode ,we use innerhtml
                         supNo1.appendChild(opt);
                     }
              
              
              }

       }
  }
}
