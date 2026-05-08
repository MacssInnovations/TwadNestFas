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



function doFunction(Command,param)
{   
        var cmbAcc_UnitCode=document.getElementById("cmbAcc_UnitCode").value;   
        var cmbOffice_code=document.getElementById("cmbOffice_code").value;
          //  var cmbStatus=document.getElementById("cmbStatus").value;

        if(Command=="searchByMonth")
        {  
            var txtCB_Year=document.getElementById("txtCB_Year").value;
            var txtCB_Month=document.getElementById("txtCB_Month").value;
           
            if(txtCB_Year.length!=0 && txtCB_Month.length!=0)
            {
                var url="../../../../../ListCheque_Dishonour.view?Command=searchByMonth&cmbAcc_UnitCode="+cmbAcc_UnitCode+
                "&cmbOffice_code="+cmbOffice_code+"&txtCB_Year="+txtCB_Year+"&txtCB_Month="+txtCB_Month;
               // alert(url);
                var req=getTransport();
                req.open("GET",url,true); 
                req.onreadystatechange=function()
                {    
                   handleResponse(req);
                   
                }   
                        req.send(null);
               
            }
        }
       
     
}

function handleResponse(req)
{ 
    if(req.readyState==4)
    {
        if(req.status==200)
        {  
         //   alert("req.responseText"+req.responseText);
            var baseResponse=req.responseXML.getElementsByTagName("response")[0];
       //    alert(baseResponse);
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
  // alert("insideloadtable");
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
               //     alert("tbody"+tbody);
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
                   
                
          
        }
}
function changepage()
{
var page=document.frmCheqDishonour_List.cmbpage.value;
loadPage(parseInt(page));
}  


function loadPage(page)
{
            var i=0;
            var c=0;
            var p=__pagination*(page-1);
            document.frmCheqDishonour_List.cmbpage.selectedIndex=page-1;
            var tbody=document.getElementById("tbody");
            try{tbody.innerHTML="";}
            catch(e) {tbody.innerText="";}  
              
             if(service)
             {
                s=0;
                var i=0;
                
                   //Start: new added on 24th march     
                /* var sumAmount=0.0;
                 for(i=0;i<service.length&& c<__pagination;i++)     // All pages total
                 {
                    sumAmount=parseFloat(sumAmount)+ parseFloat(service[i].getElementsByTagName("Tot_Amt")[0].firstChild.nodeValue);
                 }*/
                 //end
               
                for(i=p;i<service.length&& c<__pagination;i++)
                {
                        c++;s++;
                         var items=new Array();
                         items[0]=s;
                        items[1]=service[i].getElementsByTagName("doc_type")[0].firstChild.nodeValue;
                        items[2]=service[i].getElementsByTagName("doc_no")[0].firstChild.nodeValue;
                        items[3]=service[i].getElementsByTagName("doc_date")[0].firstChild.nodeValue;
                        items[4]=service[i].getElementsByTagName("oldcheq_type")[0].firstChild.nodeValue;
                        items[5]=service[i].getElementsByTagName("old_cheqno")[0].firstChild.nodeValue;
                        items[6]=service[i].getElementsByTagName("old_cheqdate")[0].firstChild.nodeValue;
                        items[7]=service[i].getElementsByTagName("old_cheqamt")[0].firstChild.nodeValue;
                        items[8]=service[i].getElementsByTagName("newcheq_status")[0].firstChild.nodeValue;
                        items[9]=service[i].getElementsByTagName("new_cheqno")[0].firstChild.nodeValue;
                        items[10]=service[i].getElementsByTagName("new_cheqdate")[0].firstChild.nodeValue;
                        items[11]=service[i].getElementsByTagName("new_cheqamt")[0].firstChild.nodeValue;
                        items[12]=service[i].getElementsByTagName("remarks")[0].firstChild.nodeValue;
                        items[13]=service[i].getElementsByTagName("recieved_date")[0].firstChild.nodeValue;
                        
                        var tbody=document.getElementById("tbody");
                        var mycurrent_row=document.createElement("TR");
                    
                        for(j=0;j<14;j++)
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
                        /*var cell=document.createElement("TD");
                        cell.align='CENTER';
                        var anc=document.createElement("A");
                        var url="javascript:Show('"+Ucode+"','"+Offid+"','"+txtCB_Year+"','"+txtCB_Month+"','"+items[0]+"')";
                        anc.href=url;
                        var txtedit=document.createTextNode("DETAILS");
                        anc.appendChild(txtedit);
                        cell.appendChild(anc);
                        mycurrent_row.appendChild(cell);*/
                        
                       // document.getElementById("cmbAcc_UnitCode").value==document.getElementById("unitid").value;
                     
                        tbody.appendChild(mycurrent_row);
                    }
            
                }          
            
                       var cell=document.getElementById("divcmbpage");
                            cell.style.display="block";
                       var cell=document.getElementById("divpage");
                            cell.style.display="block";
                           
                     
              /*  if(navigator.appName.indexOf("Microsoft")!=-1)
                    cell.innerText= ' / ' +totalblock + "            Total Amount ="+sumAmount;
                else
                    cell.innerHTML= ' / ' +totalblock+ "            Total Amount ="+sumAmount;*/

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

           __pagination=document.frmCheqDishonour_List.cmbpagination.value;
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

