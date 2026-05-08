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


/// ----------------------------------------------------- for reporting purpose----------------
function printing(unitcode,offid,yr,mon,recNo)
{
    var cmbAcc_UnitCode=unitcode; 
    var cmbOffice_code=offid;
    
    var txtCB_Year=yr;
    var txtCB_Month=mon;
    //alert(recNo);
    var txtVoucher_No=recNo;  
    var txtCreat_By_Module='ByHO';  // it doesn't specify the Module like ('BPP','NP','CR') , it just specifies transfer by office or Head office
    
    var url="../../../../../Fund_Receipt_HO_print.view?Command=searchByMonth&cmbAcc_UnitCode="+cmbAcc_UnitCode+
    "&cmbOffice_code="+cmbOffice_code+"&txtCB_Year="+txtCB_Year+"&txtCB_Month="+txtCB_Month+"&txtCreat_By_Module="+txtCreat_By_Module+
    "&txtVoucher_No="+txtVoucher_No;
    
    document.frmFund_Receipt_ListAll_byHO.action=url;
    document.frmFund_Receipt_ListAll_byHO.method="POST";
    document.frmFund_Receipt_ListAll_byHO.submit();
      return true;
}
/////////-------------------------- end report

function doFunction(Command,param)
{   
        var cmbAcc_UnitCode=document.getElementById("cmbAcc_UnitCode").value    
        var cmbOffice_code=document.getElementById("cmbOffice_code").value
         var cmbStatus=document.getElementById("cmbStatus").value;
        //added on 16/12/2013 --option in (auto/manual/all) by office is inserted
        if ( document.frmFund_Receipt_ListAll_byHO.txtTypes[0].checked==true ) 
        {
            txtTypes="ALL"; 
        }
        else if (document.frmFund_Receipt_ListAll_byHO.txtTypes[1].checked==true ) 
        {
            txtTypes="AUTO"; 
        }
        else if (document.frmFund_Receipt_ListAll_byHO.txtTypes[2].checked==true ) 
        {
            txtTypes="MAN"; 
        }
//alert("check::::"+txtTypes);
        if(Command=="searchByMonth")
        {  
            var txtCB_Year=document.getElementById("txtCB_Year").value;
            var txtCB_Month=document.getElementById("txtCB_Month").value;
           
            if(txtCB_Year.length!=0 && txtCB_Month.length!=0)
            {
                var txtCreat_By_Module='';
                var url="../../../../../Fund_Receipt_ListAll_byHO.view?Command=searchByMonth&cmbAcc_UnitCode="+cmbAcc_UnitCode+
                "&cmbOffice_code="+cmbOffice_code+"&txtCB_Year="+txtCB_Year+"&txtCB_Month="+txtCB_Month+"&txtCreat_By_Module="+txtCreat_By_Module+"&cmbStatus="+cmbStatus+
                "&txtTypes="+txtTypes;
              //  alert(url);
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
                var txtCreat_By_Module='';
                var url="../../../../../Fund_Receipt_ListAll_byHO.view?Command=searchByDate&cmbAcc_UnitCode="+cmbAcc_UnitCode+
                "&cmbOffice_code="+cmbOffice_code+"&txtCB_Year="+
                txtCB_Year+"&txtCB_Month="+txtCB_Month+"&txtFrom_date="+txtFrom_date+"&txtTo_date="+txtTo_date+"&txtCreat_By_Module="+txtCreat_By_Module+"&cmbStatus="+cmbStatus+
                "&txtTypes="+txtTypes;
                //alert(url);
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
                        if(tbody.innerText !='undefined'  && tbody.innerText !=null )
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
                    /*var leng=baseResponse.getElementsByTagName("leng");
                    if(leng)
                    {
                       var i=0;
                       var Ucode=baseResponse.getElementsByTagName("Ucode")[0].firstChild.nodeValue;
                       var Offid=baseResponse.getElementsByTagName("Offid")[0].firstChild.nodeValue;
                       var txtCB_Year=baseResponse.getElementsByTagName("txtCB_Year")[0].firstChild.nodeValue;
                       var txtCB_Month=baseResponse.getElementsByTagName("txtCB_Month")[0].firstChild.nodeValue;
                       
                        var tbody=document.getElementById("tbody");
                 
                         try{tbody.innerHTML="";}
                        catch(e) {tbody.innerText="";}
                   
                         s=0;
                    for(i=0;i<leng.length;i++)
                    {
                            
                        var items=new Array();
                        items[0]=leng[i].getElementsByTagName("Rec_no")[0].firstChild.nodeValue;
                        items[1]=leng[i].getElementsByTagName("Rec_Date")[0].firstChild.nodeValue;
                        items[2]=leng[i].getElementsByTagName("Remak")[0].firstChild.nodeValue;
                        items[3]=leng[i].getElementsByTagName("Tot_Amt")[0].firstChild.nodeValue;
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
                         //--------------------------------------- for report----
                        var cell=document.createElement("TD");
                        cell.align='CENTER';
                        var anc=document.createElement("A");
                        var url="javascript:printing('"+Ucode+"','"+Offid+"','"+txtCB_Year+"','"+txtCB_Month+"','"+items[0]+"')";
                        anc.href=url;
                        var txtedit=document.createTextNode("PRINT");
                        anc.appendChild(txtedit);
                        cell.appendChild(anc);
                        mycurrent_row.appendChild(cell);
                       //---------------------------------------end report
                        tbody.appendChild(mycurrent_row);
                    }
                }*/
                
          
        }
}
function changepage()
{
var page=document.frmFund_Receipt_ListAll_byHO.cmbpage.value;
loadPage(parseInt(page));
}  


function loadPage(page)
{
            var i=0;
            var c=0;
            var p=__pagination*(page-1);
            document.frmFund_Receipt_ListAll_byHO.cmbpage.selectedIndex=page-1;
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
                        items[0]=service[i].getElementsByTagName("Rec_no")[0].firstChild.nodeValue;
                        items[1]=service[i].getElementsByTagName("Rec_Date")[0].firstChild.nodeValue;
                        items[2]=service[i].getElementsByTagName("R_type")[0].firstChild.nodeValue;
                        items[3]=service[i].getElementsByTagName("Tot_Amt")[0].firstChild.nodeValue;
                        items[4]=service[i].getElementsByTagName("BK_BR_CITY")[0].firstChild.nodeValue;
                      //  items[5]=service[i].getElementsByTagName("Remak")[0].firstChild.nodeValue;
                      if(service[i].getElementsByTagName("REF_NO")[0].firstChild == null){
						  items[5]="";
					  }else{
						  items[5]=service[i].getElementsByTagName("REF_NO")[0].firstChild.nodeValue;
					  }
				      items[6]=service[i].getElementsByTagName("ref_date")[0].firstChild.nodeValue;
                        
                        
                        var cheque_dd_no;
                        //cheque_dd_no= service[i].getElementsByTagName("che_DD_no")[0].firstChild.nodeValue;
                        
                        if(service[i].getElementsByTagName("che_DD_no")[0].firstChild == null){
							cheque_dd_no = "";
						}else{
							cheque_dd_no= service[i].getElementsByTagName("che_DD_no")[0].firstChild.nodeValue;
						}
                        
                        
                        if (cheque_dd_no == 'null') 
                        {
                          items[7]=service[i].getElementsByTagName("che_or_DD")[0].firstChild.nodeValue;
                        }
                        else
                        {
                          items[7]=service[i].getElementsByTagName("che_or_DD")[0].firstChild.nodeValue+"-"+cheque_dd_no; 
                        }
                        
                        items[8]=service[i].getElementsByTagName("che_DD_date")[0].firstChild.nodeValue;
                        
                        items[9]=service[i].getElementsByTagName("DR_ACCOUNT_HEAD_CODE")[0].firstChild.nodeValue;
                        items[10]=service[i].getElementsByTagName("CR_ACCOUNT_HEAD_CODE")[0].firstChild.nodeValue;
                   
                        items[11]=service[i].getElementsByTagName("ho_ac_no")[0].firstChild.nodeValue;
                        items[12]=service[i].getElementsByTagName("office_ac_no")[0].firstChild.nodeValue;
                        items[13]=service[i].getElementsByTagName("Rec_office")[0].firstChild.nodeValue;
                      /*// alert(items[2]);
   
                        if(items[2].trim()=="U")
                             items[2]="Unspent";
                        else if(items[2].trim()=="C") 
                            items[2]="Collection";
                        
                        alert(items[2]);
*/                        
                        
                        //alert(items[2]);
                        
                        if(items[2].trim()== 'C')
                        {
                              items[2]="Collection";
                        }else if(items[2].trim() == 'U') 
                        {
                     	   items[2]="Unspent";							
 						}else if(items[2].trim() == 'NM') {
 							 items[2]="NRDWP-Main-unspent";
 						}else if(items[2].trim() == 'NS') {
 							 items[2]="NRDWP-Support-unspent";
 						}else if(items[2].trim() == 'NC') {
 							 items[2]="NRDWP-Calamity";
 						}else if(items[2].trim() == 'UNM') {
 							 items[2]="NRDWP-Main-Int";
 						}else if(items[2].trim() == 'UNS') {
 							 items[2]="NRDWP-Support-Int";
 						}else  if(items[2].trim() == 'UNC') {
 							// items[2]="NRDWP-Calamity-Int";
 							items[2]="NRDWP-Calamity Unspent";
 						}
 						else  if(items[2].trim() == 'NCI') {
 							 items[2]="NRDWP-Calamity-Int";
 						}
                        
                        var tbody=document.getElementById("tbody");
                        var mycurrent_row=document.createElement("TR");
                    
                        for(j=0;j<14;j++)
                        {
                                cell2=document.createElement("TD");
                                if(j==2 || j==5||j==6 ||j==8|| j==9 ||j==10){
                                cell2.setAttribute('align','center');
                                } else if(j==3||j==11 || j==12){
                                	 cell2.setAttribute('align','right');
                                }
                                else {
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

                      if(document.getElementById("cmbAcc_UnitCode").value!==null)
                        {
                            if(document.getElementById("cmbStatus").value=='L')
                            {
                                var cell=document.createElement("TD");
                                cell.align='CENTER';
                                var anc=document.createElement("A");
                                var url="javascript:printing('"+Ucode+"','"+Offid+"','"+txtCB_Year+"','"+txtCB_Month+"','"+items[0]+"')";
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
}



function changepagesize()
{

           __pagination=document.frmFund_Receipt_ListAll_byHO.cmbpagination.value;
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
                             loadPage(1);
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
function getCurrentYear() {
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
      
       
        // var c=t.value.replace(/-/g,'/');
         var c=t.value;
//        try{
//        var f=DateFormat(t,c,event,true,'3');
//        }catch(e){
         
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
            //alert(f);
            //t.value=c.replace(/\//g,'-');
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
