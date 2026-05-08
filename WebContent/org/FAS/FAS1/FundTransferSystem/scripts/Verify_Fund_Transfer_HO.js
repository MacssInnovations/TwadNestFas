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

function Show(unitcode,offid,yr,recNo)
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
    Voucher_list_SL= window.open("../../../../../org/FAS/FAS1/FundTransferSystem/jsps/Verify_Fund_Transfer_SL_HO.jsp?cmbAcc_UnitCode="+unitcode+"&cmbOffice_code="+offid+"&yr="+yr+"&recNo="+recNo,"ReceiptDateSearch","status=1,height=500,width=500,resizable=YES, scrollbars=yes"); 
    Voucher_list_SL.moveTo(250,250);  
    Voucher_list_SL.focus();
    
}

window.onunload=function()
{
if (Voucher_list_SL && Voucher_list_SL.open && !Voucher_list_SL.closed) Voucher_list_SL.close();
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
    var txtCreat_By_Module='ByHO';      // it doesn't specify the Module like ('BPP','NP','CR') , it just specifies transfer by office or Head office
    var url="../../../../../Fund_Transfer_HO_print.view?Command=searchByMonth&cmbAcc_UnitCode="+cmbAcc_UnitCode+
    "&cmbOffice_code="+cmbOffice_code+"&txtCB_Year="+txtCB_Year+"&txtCB_Month="+txtCB_Month+"&txtCreat_By_Module="+txtCreat_By_Module+
    "&txtVoucher_No="+txtVoucher_No;
    
    document.frmVerify_Fund_Transfer_HO.action=url;
    document.frmVerify_Fund_Transfer_HO.method="POST";
    document.frmVerify_Fund_Transfer_HO.submit();
      return true;
}
/////////-------------------------- end report

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
                var txtCreat_By_Module='';
                
                var url="../../../../../Verify_Fund_Transfer_HO?Command=searchByMonth&cmbAcc_UnitCode="+cmbAcc_UnitCode+
                "&cmbOffice_code="+cmbOffice_code+"&txtCB_Year="+txtCB_Year+"&txtCB_Month="+txtCB_Month+"&txtCreat_By_Module="+txtCreat_By_Module+"&cmbStatus="+cmbStatus;
               //alert(url);
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
                var url="../../../../../Verify_Fund_Transfer_HO?Command=searchByDate&cmbAcc_UnitCode="+cmbAcc_UnitCode+
                "&cmbOffice_code="+cmbOffice_code+"&txtCB_Year="+
                txtCB_Year+"&txtCB_Month="+txtCB_Month+"&txtFrom_date="+txtFrom_date+"&txtTo_date="+txtTo_date+"&txtCreat_By_Module="+txtCreat_By_Module+"&cmbStatus="+cmbStatus;
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
                   // cell.style.display="none";
                    var cell=document.getElementById("divpage");
//                    cell.style.display="none";
           
                    var cell=document.getElementById("divnext");
                  //  cell.style.display="none";
                    var cell=document.getElementById("divpre");
                 //   cell.style.display="none";
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
                       var tbody=document.getElementById("tbody");
                        try{tbody.innerHTML="";}
                        catch(e) {tbody.innerText="";}
                        var i=0;
                        totalblock=0;
                         if(service.length>0)
                         {
                                totalblock=parseInt(service.length);
                                
                               
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
                                         
                                       }
                                    } 
                            }
                            loadPage(1);            
                        }                         
          
        }
}
function changepage()
{
var page=document.frmVerify_Fund_Transfer_HO.cmbpage.value;
loadPage(parseInt(page));
}  

function Updat()         
{  
	var voucher_no="";
	var voucher_date="";
	var chcksel="";
    var cmbAcc_UnitCode="";
    var cmbOffice_code="";
	
	var url1="../../../../../Verify_Fund_Transfer_HO?Command=Update";  
	
	if(document.frmVerify_Fund_Transfer_HO.chckparameter.length>0)
    {
	  for(i=0;i<document.frmVerify_Fund_Transfer_HO.chckparameter.length;i++)
       {
    	   if(document.frmVerify_Fund_Transfer_HO.chckparameter[i].checked==true)
           {
        	   voucher_no= voucher_no+document.frmVerify_Fund_Transfer_HO.voucher_no[i].value +",";//alert(voucher_no);
        	   voucher_date= voucher_date+document.frmVerify_Fund_Transfer_HO.voucher_date[i].value +",";//alert(voucher_date);
        	   chcksel= chcksel+document.frmVerify_Fund_Transfer_HO.chckparameter[i].value +",";//alert(chcksel);
         }
          }
      if(voucher_no!="")
      {
          voucher_no=voucher_no.substring(0,voucher_no.length-1); 
          url1=url1+"&voucher_no="+voucher_no;
      }
      if(voucher_date!="")
      {
    	  voucher_date=voucher_date.substring(0,voucher_date.length-1); 
          url1=url1+"&voucher_date="+voucher_date;
      }
      if(chcksel!="")
      {
          chcksel=chcksel.substring(0,chcksel.length-1); 
          url1=url1+"&chcksel="+chcksel;
      }
      cmbAcc_UnitCode=document.getElementById("cmbAcc_UnitCode").value;    
      url1=url1+"&cmbAcc_UnitCode="+cmbAcc_UnitCode;
      cmbOffice_code=document.getElementById("cmbOffice_code").value;
      url1=url1+"&cmbOffice_code="+cmbOffice_code;       
    }
	else
	{
        voucher_no= document.frmVerify_Fund_Transfer_HO.voucher_no.value;
        url1=url1+"&voucher_no="+voucher_no;
        voucher_date= document.frmVerify_Fund_Transfer_HO.voucher_date.value;
        url1=url1+"&voucher_date="+voucher_date;
        chcksel= document.frmVerify_Fund_Transfer_HO.chckparameter.value;
        url1=url1+"&chcksel="+chcksel;
        cmbAcc_UnitCode=document.getElementById("cmbAcc_UnitCode").value;  
        url1=url1+"&cmbAcc_UnitCode="+cmbAcc_UnitCode;
        cmbOffice_code=document.getElementById("cmbOffice_code").value;
        url1=url1+"&cmbOffice_code="+cmbOffice_code;	
	}
	document.frmVerify_Fund_Transfer_HO.action=url1;
    document.frmVerify_Fund_Transfer_HO.method="post"; 
    document.frmVerify_Fund_Transfer_HO.submit(); 
}

function selectAll(Opt)
{

  var len=  document.getElementById("tbody").rows.length;
  
  if(len==1)
  {
          if ( Opt =="ALL")
          {
             document.getElementById("chckparameter").checked=true;
          }
          else if (Opt=="UNSelect" )
          {
             document.getElementById("chckparameter").checked=false;
          }
  }
  else if(len>1)
  {
          for(var i=0;i<len;i++)
          {
                if ( Opt =="ALL")
                {
                    document.frmVerify_Fund_Transfer_HO.chckparameter[i].checked=true;
                }
                else if(Opt=="UNSelect")
                {
                    document.frmVerify_Fund_Transfer_HO.chckparameter[i].checked=false;
                }
          }
  }
}

function loadPage(page)
{
            var i=0;
            var c=0;
            var k=0;
            var p=__pagination*(page-1);
         //   document.frmVerify_Fund_Transfer_HO.cmbpage.selectedIndex=page-1;
            var tbody=document.getElementById("tbody");
            try{tbody.innerHTML="";}
            catch(e) {tbody.innerText="";}  
              
             if(service)
             {
                s=0;
                var i=0;
                   //Start: new added on 24th march     
                 var sumAmount=0.0;
                 for(i=0;i<service.length;i++)     // All pages total
                 {
                    sumAmount=parseFloat(sumAmount)+ parseFloat(service[i].getElementsByTagName("Tot_Amt")[0].firstChild.nodeValue);
                 }
                 //end
               
                for(i=p;i<service.length;i++)
                {
                        c++;
                         var items=new Array();
                        items[1]=service[i].getElementsByTagName("Rec_no")[0].firstChild.nodeValue;
                        items[2]=service[i].getElementsByTagName("Rec_Date")[0].firstChild.nodeValue;
                        if(items[3]=service[i].getElementsByTagName("HO_REF_NO")[0]=null)
                        items[3]=service[i].getElementsByTagName("HO_REF_NO")[0].firstChild.nodeValue;
                        items[4]=service[i].getElementsByTagName("HO_ref_date")[0].firstChild.nodeValue;
                        items[5]=service[i].getElementsByTagName("BK_BR_CITY")[0].firstChild.nodeValue;
                        if(items[6]=service[i].getElementsByTagName("Remak")[0]=null)
                        items[6]=service[i].getElementsByTagName("Remak")[0].firstChild.nodeValue;
                        items[7]=service[i].getElementsByTagName("Tot_Amt")[0].firstChild.nodeValue;
                       
                                         
                        var tbody=document.getElementById("tbody");
                        var mycurrent_row=document.createElement("TR");
                        k++;
                        
                        var cell21=document.createElement("TD");
                        cell21.setAttribute('align','left');
                        var chcksel=document.createElement("input");
                        chcksel.type="checkbox";
                        chcksel.name="chckparameter";
                        chcksel.id="chckparameter";
                        chcksel.checked=true;
                        chcksel.value=k; //alert(chcksel.value);
                        
                        var vouchno=document.createElement("input");
                        vouchno.type="hidden";
                        vouchno.name="voucher_no";
                        vouchno.id="voucher_no";
                        vouchno.value=items[1];//alert("vou"+vouchno.value);
                        
                        var vouchdate=document.createElement("input");
                        vouchdate.type="hidden";
                        vouchdate.name="voucher_date";
                        vouchdate.id="voucher_date";
                        vouchdate.value=items[2];//alert("voudate"+vouchdate.value);
                        
                        cell21.appendChild(vouchdate);
                        cell21.appendChild(vouchno);
                        cell21.appendChild(chcksel);
                        mycurrent_row.appendChild(cell21);
                    
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
                        var cell=document.createElement("TD");
                        cell.align='CENTER';
                        var anc=document.createElement("A");
                        var url="javascript:Show('"+Ucode+"','"+Offid+"','"+items[2]+"','"+items[1]+"')";
                        anc.href=url;
                        var txtedit=document.createTextNode("DETAILS");
                        anc.appendChild(txtedit);
                        cell.appendChild(anc);
                        mycurrent_row.appendChild(cell);

                
                        if(document.getElementById("cmbStatus").value=='C')
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
            
}



function changepagesize()
{

           __pagination=document.frmVerify_Fund_Transfer_HO.cmbpagination.value;
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

