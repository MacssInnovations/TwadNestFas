var service;
var slno=1;
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
    var txtCreat_By_Module='ByOffice';      // it doesn't specify the Module like ('BPP','NP','CR') , it just specifies transfer by office or Head office
    
    var url="../../../../../Fund_Transfer_HO_print.view?Command=searchByMonth&cmbAcc_UnitCode="+cmbAcc_UnitCode+
    "&cmbOffice_code="+cmbOffice_code+"&txtCB_Year="+txtCB_Year+"&txtCB_Month="+txtCB_Month+"&txtCreat_By_Module="+txtCreat_By_Module+
    "&txtVoucher_No="+txtVoucher_No;
    
    document.frmFund_Transfer_NonList.action=url;
    document.frmFund_Transfer_NonList.method="POST";
    document.frmFund_Transfer_NonList.submit();
      return true;
}
/////////-------------------------- end report

function doFunction(Command,param)
{  
        //var cmbAcc_UnitCode=document.getElementById("cmbAcc_UnitCode").value    
       // var cmbOffice_code=document.getElementById("cmbOffice_code").value
        if(Command=="searchByMonth")
        {  
            var txtCB_Year=document.getElementById("txtCB_Year").value;
            var txtCB_Month=document.getElementById("txtCB_Month").value;
           
            if(txtCB_Year.length!=0 && txtCB_Month.length!=0)
            {
                var url="../../../../../Fund_Transfer_NonListedOffices?Command=searchByMonth"+
                "&txtCB_Year="+txtCB_Year+"&txtCB_Month="+txtCB_Month;
            //   alert(url);
                var req=getTransport();
                req.open("GET",url,true); 
                req.onreadystatechange=function()
                {
                   handleResponse(req);
                };  
                        req.send(null);
               
            }
        }
       
       else if(Command=="searchByDate")
        {  
            var txtCB_Year=document.getElementById("txtCB_Year").value;
            var txtCB_Month=document.getElementById("txtCB_Month").value;
            var txtFrom_date=document.getElementById("txtFrom_date").value;
            var txtTo_date=document.getElementById("txtTo_date").value;
           
                var txtCreat_By_Module='';
                var url="../../../../../Fund_Transfer_ListAll_byOffice.view?Command=searchByDate"+
                "&txtFrom_date="+txtFrom_date+"&txtTo_date="+txtTo_date+"&txtCB_Year="+txtCB_Year+"&txtCB_Month="+txtCB_Month;
             //   alert(url);
                var req=getTransport();
                req.open("GET",url,true); 
                req.onreadystatechange=function()
                {
                   handleResponse(req);
                };   
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
                   //  alert(service.length);

                            var tbody=document.getElementById("tbody");
                            try{tbody.innerHTML="";}
                            catch(e) {tbody.innerText="";}  
                            
                                var items=new Array();
                                //items[0]=service[i].getElementsByTagName("unit_idone")[0].firstChild.nodeValue;
                               // items[1]=service[i].getElementsByTagName("unit_name")[0].firstChild.nodeValue;
                              
                           // alert(service.length)   ;
                           
                                for(var j=0;j<service.length;j++)
                                {
                                
                                unitid=baseResponse.getElementsByTagName("unit_idone")[j].firstChild.nodeValue;
                                unitname=baseResponse.getElementsByTagName("unit_name")[j].firstChild.nodeValue;
                              //  countList=baseResponse.getElementsByTagName("countList")[j].firstChild.nodeValue;
                                
                                 //      alert(unitid);
                                
                                         var mycurrent_row=document.createElement("TR");
                                    
                                     var cell2 =document.createElement("TD");    
                                     var slno_one=document.createTextNode(slno);                         
                                     cell2.appendChild(slno_one);       
                                     mycurrent_row.appendChild(cell2);  
                                    
                                        var cell2 =document.createElement("TD");    
                                     var unitid_one=document.createTextNode(unitid);                         
                                     cell2.appendChild(unitid_one);       
                                     mycurrent_row.appendChild(cell2);       
                    
                                     var cell3 =document.createElement("TD");    
                                     var unitname_one=document.createTextNode(unitname);                         
                                     cell3.appendChild(unitname_one);       
                                     mycurrent_row.appendChild(cell3);
                                     
                                  /*   var cell4 =document.createElement("TD");    
                                     var list_one=document.createTextNode(countList);                         
                                     cell4.appendChild(list_one);       
                                     mycurrent_row.appendChild(cell4);*/
                                     
                                     tbody.appendChild(mycurrent_row);
                                     slno++;   
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
