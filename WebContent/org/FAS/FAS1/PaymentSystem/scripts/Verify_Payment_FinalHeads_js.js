var service;
var __pagination=11;
var destid;
var totalblock=0;
var seq=0;

function checkNull_verify()
{
	  var tbody=document.getElementById("grid_body");
	if(tbody.rows.length==0){
	alert("No Final Heads Found");
	return false;
	}
}

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

function Show(unitcode,offid,yr,mon,recNo)
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
    Voucher_list_SL= window.open("../../../../../org/FAS/FAS1/PaymentSystem/jsps/BankPay_NonBill_ListAll_SL.jsp?cmbAcc_UnitCode="+unitcode+"&cmbOffice_code="+offid+"&yr="+yr+"&mon="+mon+"&recNo="+recNo,"ReceiptDateSearch","status=1,height=500,width=500,resizable=YES, scrollbars=yes"); 
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
    var txtCreat_By_Module='BPF';
    
    var url="../../../../../Verify_Payment_FinalHeads_serv.java?Command=searchByMonth&cmbAcc_UnitCode="+cmbAcc_UnitCode+
    "&cmbOffice_code="+cmbOffice_code+"&txtCB_Year="+txtCB_Year+"&txtCB_Month="+txtCB_Month+"&txtCreat_By_Module="+txtCreat_By_Module+
    "&txtVoucher_No="+txtVoucher_No;
    
    document.finalBill_verify.action=url;
    document.finalBill_verify.method="POST";
    document.finalBill_verify.submit();
      return true;
}
/////////-------------------------- end report

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
                var txtCreat_By_Module='BPF';
                
                var url="../../../../../Verify_Payment_FinalHeads_serv?Command=searchByMonth&cmbAcc_UnitCode="+cmbAcc_UnitCode+
                "&cmbOffice_code="+cmbOffice_code+"&txtCB_Year="+txtCB_Year+"&txtCB_Month="+txtCB_Month+"&txtCreat_By_Module="+txtCreat_By_Module+"&cmbStatus="+cmbStatus;
                var req=getTransport();
                req.open("GET",url,true); 
                req.onreadystatechange=function()
                {
                   fnHandleResponse(req);
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
                var txtCreat_By_Module='BPF';
                var url="../../../../../Verify_Payment_FinalHeads_serv?Command=searchByDate&cmbAcc_UnitCode="+cmbAcc_UnitCode+
                "&cmbOffice_code="+cmbOffice_code+"&txtCB_Year="+
                txtCB_Year+"&txtCB_Month="+txtCB_Month+"&txtFrom_date="+txtFrom_date+"&txtTo_date="+txtTo_date+"&txtCreat_By_Module="+txtCreat_By_Module+"&cmbStatus="+cmbStatus;
                //alert(url);
                var req=getTransport();
                req.open("GET",url,true); 
                req.onreadystatechange=function()
                {
                   fnHandleResponse(req);
                }   
                        req.send(null);
                
           }
           else
            alert("Enter the Cash Book Year/Month and From date and To date");
           
        }
}

function fnHandleResponse(req)
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
                    s=0;
                    var tbody=document.getElementById("grid_body");
                      try{tbody.innerHTML="";}
                  catch(e) {tbody.innerText="";}
                  
                     
                    alert("No Record exists");
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
                                   
                        var i=0;
                        var c=0;
                        var cell2;
                        
                        var tbody=document.getElementById("grid_body");
                        try{tbody.innerHTML="";}
                        catch(e) {tbody.innerText="";}  
                        
                        
                        for(i=0;i<service.length;i++)
                        {
                       
                                c++;
                                 var items=new Array();
                                items[0]=service[i].getElementsByTagName("Rec_no")[0].firstChild.nodeValue;
                                
                                items[1]=service[i].getElementsByTagName("Rec_Date")[0].firstChild.nodeValue;
                                items[2]=service[i].getElementsByTagName("Remak")[0].firstChild.nodeValue;
                                if(items[2]=="null")
                                {
                             	  items[2]="--";
                                }
                                
                                items[3]=service[i].getElementsByTagName("Tot_Amt")[0].firstChild.nodeValue;
                                
                                var tbody=document.getElementById("grid_body");
                                var mycurrent_row=document.createElement("TR");
                            

                                cell2=document.createElement("TD");
                                cell2.style.textAlign='center'; 
                                var chcksel="";
                                checkparam=seq;
                               if(window.navigator.appName.toLowerCase().indexOf("netscape")==-1)
                               {
                               	chcksel=document.createElement("<input type='checkbox' name='chckparameter' id='chckparameter' value='"+checkparam+"' />");
                               }
                               else
                               {
                                      var chcksel=document.createElement("input");
                                      chcksel.type="checkbox";
                                      chcksel.checked='checked';
                                      chcksel.name="chckparameter";
                                      chcksel.value= checkparam;
                               }
                               cell2.appendChild(chcksel);
                               mycurrent_row.appendChild(cell2);
                               
                               cell2=document.createElement("TD");
                               cell2.setAttribute('align','right');
                               var v_id=document.createElement("input");
                               v_id.type="hidden";
                               v_id.name="voucherNo";
                               v_id.value=items[0];
                               cell2.appendChild(v_id);
                               var currentText=document.createTextNode(items[0]);
                               cell2.appendChild(currentText);
                               mycurrent_row.appendChild(cell2);
                               
                               cell2=document.createElement("TD");
                               cell2.setAttribute('align','right');
                               var v_date=document.createElement("input");
                               v_date.type="hidden";
                               v_date.name="voucherDate";
                               v_date.value=items[1];
                               cell2.appendChild(v_date);
                               var currentText=document.createTextNode(items[1]);
                               cell2.appendChild(currentText);
                               mycurrent_row.appendChild(cell2);
                               
                               
                               cell2=document.createElement("TD");
                               
                               cell2.setAttribute('align','center');
                               var rem=document.createElement("input");
                               rem.type="hidden";
                               rem.name="remarks";
                               rem.value=items[2];
                               cell2.appendChild(rem);
                               var currentText=document.createTextNode(items[2]);
                               cell2.appendChild(currentText);
                               mycurrent_row.appendChild(cell2);
                               
                               cell2=document.createElement("TD");
                               cell2.setAttribute('align','right');
                               var tot_amt=document.createElement("input");
                               tot_amt.type="hidden";
                               tot_amt.name="totalAmt";
                               tot_amt.value=items[3];
                               cell2.appendChild(tot_amt);
                               var currentText=document.createTextNode(items[3]);
                               cell2.appendChild(currentText);
                               mycurrent_row.appendChild(cell2);
                               
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
                                seq++;
                            }
                           
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
            return false;
    }
    
}
function selectAll(Opt)
{

  var len=  document.getElementById("grid_body").rows.length;

  if(len==1)
  {
          if (Opt =="ALL")
          {
        	 document.finalBill_verify.chckparameter.checked=true;
          
          }
          else if (Opt=="UNSelect" )
          {
          document.finalBill_verify.chckparameter.checked=false;
        
          }
  }
  else if(len>1)
  {
          for(var i=0;i<len;i++)
          {
                if ( Opt =="ALL")
                {
                    document.finalBill_verify.chckparameter[i].checked=true;
                }
                else if(Opt=="UNSelect")
                {
                    document.finalBill_verify.chckparameter[i].checked=false;
                }
          }
  }

}
