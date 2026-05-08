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


function ClearAll()
    {
        //document.firmsForm.cmbAcc_UnitCode.value="";
        //document.firmsForm.comOffId.selectedIndex=0;
        document.firmsForm.txtfirmId.value="";
        document.firmsForm.txtfirmName.value="";
        document.firmsForm.txtaddr.value="";
        document.firmsForm.txtcity.value="";
        document.firmsForm.txtaddr2.value="";
        document.firmsForm.txtPhone.value="";
        document.firmsForm.txtFax.value="";
        document.firmsForm.txtEmail.value="";
        document.firmsForm.txtDateReg.value="";
        document.firmsForm.txtDateLastSupply.value="";
        
        document.firmsForm.txtPincode.value="";
        document.firmsForm.txtFirmsAliasId.value="";
        var d=document.getElementById("cmdAdd");
        d.style.display="block";
    
        var d1=document.getElementById("cmdUpdate");
        d1.style.display="none";
        
        var d3=document.getElementById("cmdDelete");
        d3.style.display="none";
        
    }
  
  
var window_firms;
function ListFirms()
    {
    
     if (window_firms && window_firms.open && !window_firms.closed) 
    {
       window_firms.resizeTo(500,500);
       window_firms.moveTo(250,250); 
       window_firms.focus();
    }
    else
    {
        window_firms=null
    }
         //var cmbAcc_UnitCode=document.getElementById("cmbAcc_UnitCode").value  ;  
        // var cmbOffice_code=document.getElementById("comOffId").value;
        // window_firms= window.open("FirmListJSP.jsp?cmbAcc_UnitCode="+cmbAcc_UnitCode+"&cmbOffice_code="+cmbOffice_code,"mywindow1","resizable=YES, scrollbars=yes"); 
         window_firms= window.open("FirmListJSP.jsp?","mywindow1","resizable=YES, scrollbars=yes"); 
         window_firms.moveTo(250,250);    
    }

window.onunload=function()
{
if (window_firms && window_firms.open && !window_firms.closed) window_firms.close();
}
function ParentFirm(scod)
{       
        ClearAll();
        var cmbAcc_UnitCode=document.getElementById("cmbAcc_UnitCode").value;
        var cmbOffice_code=document.getElementById("comOffId").value;
        var url="../../../../../firmListServ.view?cmd=listedit&scod="+scod+"&cmbAcc_UnitCode="+cmbAcc_UnitCode+"&cmbOffice_code="+cmbOffice_code;;
        //alert(url);
        var req=getTransport();
        req.open("GET",url,true); 
        req.onreadystatechange=function()
        {
            handleResponse(req);
        }   
         req.send(null);
}
 

function doFunction(Command,param)
{
        var Offcode=document.firmsForm.cmbAcc_UnitCode.value;
        
        var OffCodeRen=document.firmsForm.comOffId.value;
        var SuppId=document.firmsForm.txtfirmId.value;
        
        var SuppName=document.firmsForm.txtfirmName.value;
        var addr=document.firmsForm.txtaddr.value;
        var addr2=document.firmsForm.txtaddr2.value;
        var city=document.firmsForm.txtcity.value;
        
        var Phone=document.firmsForm.txtPhone.value;
        var Fax=document.firmsForm.txtFax.value;
        var Email=document.firmsForm.txtEmail.value;
        var DateofReg=document.firmsForm.txtDateReg.value;
        var DateofLast=document.firmsForm.txtDateLastSupply.value;
        
        var pincode=document.firmsForm.txtPincode.value;
        var firmAliasid=document.firmsForm.txtFirmsAliasId.value;
       // var status=document.firmsForm.txtstatus.value;
        var flag="";
         var status  = "";
		    if(document.firmsForm.txtstatus[0].checked==true)
		    	status="L";
		    else 
		    	status="C";
    
    if(Command=="Add")
    {
        var flag=nullcheck();
        if(flag==true)
           {
            var url="../../../../../FirmsServ.view?Command=Add&txtPincode="+pincode+"&txtFirmsAliasId="+firmAliasid+"&cmbAcc_UnitCode="+Offcode+"&comOffId="+OffCodeRen+"&txtfirmName="+SuppName+"&txtaddr="+addr+"&txtPhone="+Phone+"&txtFax="+Fax+"&txtEmail="+Email+"&txtDateReg="+DateofReg+"&txtDateLastSupply="+DateofLast+"&txtaddr2="+addr2+"&txtcity="+city+"&status="+status;
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
        
       else if(Command=="geneId")
        {
    
                var url="../../../../../FirmsServ.view?Command=geneId";
            //alert(url);
                var req=getTransport();
                req.open("GET",url,true); 
                req.onreadystatechange=function()
                    {
                        handleResponse(req);
                    }   
                     req.send(null);
               // }
            } 
        
   
    
    
    else if(Command=="Cancel")
    {
    
        if(confirm("Do You Really want to Cancel it?"))
        {
            var url="../../../../../FirmsServ.view?Command=Cancel&txtfirmId="+SuppId;
            
            var req=getTransport();
            req.open("GET",url,true); 
            req.onreadystatechange=function()
            {
               handleResponse(req);
            }   
                    req.send(null);
        }
       
   }
   
   else if(Command=="Update")
   {
        var flag=nullcheck();
        if(flag==true)
         {
            var url="../../../../../FirmsServ.view?Command=Update&txtPincode="+pincode+"&txtFirmsAliasId="+firmAliasid+"&cmbAcc_UnitCode="+Offcode+"&comOffId="+OffCodeRen+"&txtfirmId="+SuppId+"&txtfirmName="+SuppName+"&txtaddr="+addr+"&txtPhone="+Phone+"&txtFax="+Fax+"&txtEmail="+Email+"&txtDateReg="+DateofReg+"&txtDateLastSupply="+DateofLast+"&txtaddr2="+addr2+"&txtcity="+city+"&status="+status;        
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
}
 
 
 


function handleResponse(req)
    {   
      if(req.readyState==4)
        {
          if(req.status==200)
          {               
              var baseResponse=req.responseXML.getElementsByTagName("response")[0];
              var tagCommand=baseResponse.getElementsByTagName("command")[0];
              var Command=tagCommand.firstChild.nodeValue; 
            if(Command=="Add")
            {
                addRow(baseResponse);
            }
            
            else if(Command=="geneId")
            {
                //alert("Hellooooooooooo");
                geneIdRow(baseResponse);
            }
            else if(Command=="loadtab")
            {
                loadtabRow(baseResponse);
            }
            else if(Command=="Cancel")
            {
                deleteRow(baseResponse);
            }
            
            else if(Command=="Update")
            {
                UpdateRow(baseResponse);
            }
            else if(Command=="listedit")
            {
                fillvalues(baseResponse);
            }
        }
    }
}
 
function fillvalues(baseResponse)               // this function uses same variable as in supplier form , but it carries the firm values...
{
     var flag=baseResponse.getElementsByTagName("flag")[0].firstChild.nodeValue;
        if(flag=="success")
        {
            
            var j=0;
            var supId=baseResponse.getElementsByTagName("firmsID");             
            var len=supId.length;
            for(j=0;j<len;j++)
            {
                    var supId=baseResponse.getElementsByTagName("firmsID");
                    var supName=baseResponse.getElementsByTagName("firmsName");
                    var supAliasName=baseResponse.getElementsByTagName("firmsAliasName");
                    var supAddr=baseResponse.getElementsByTagName("supAddr");
                    var supPhone=baseResponse.getElementsByTagName("supPhone");
                    var supFax=baseResponse.getElementsByTagName("supFax");
                    var supEmail=baseResponse.getElementsByTagName("supemail");
                    var suplierAddr1=baseResponse.getElementsByTagName("supaddr1");
                    var supCity=baseResponse.getElementsByTagName("city");
                    
                    var DoReg=baseResponse.getElementsByTagName("DOReg");
                    var DoLast_supp=baseResponse.getElementsByTagName("DOsupply");
                    var pincode=baseResponse.getElementsByTagName("pincode");
                    var status=baseResponse.getElementsByTagName("status");
                    var supId1=supId.item(j).firstChild.nodeValue;
                    var supName1=supName.item(j).firstChild.nodeValue;
                    var supAliasName1=supAliasName.item(j).firstChild.nodeValue;
                    var supAddr1=supAddr.item(j).firstChild.nodeValue;
                    
                    var supPhone1=supPhone.item(j).firstChild.nodeValue;
                    var supFax1=supFax.item(j).firstChild.nodeValue;
                    var supEmail1=supEmail.item(j).firstChild.nodeValue;
                    var supAddr11=suplierAddr1.item(j).firstChild.nodeValue;
                    var supCity1=supCity.item(j).firstChild.nodeValue;
                    var DoReg1=DoReg.item(j).firstChild.nodeValue;
                    var DoLast_supp1=DoLast_supp.item(j).firstChild.nodeValue;
                    var pincode1=pincode.item(j).firstChild.nodeValue;
                    var status1=status.item(j).firstChild.nodeValue;
                    var items=new Array();
                 //   alert(status1);
                     if(status1=="L")
			   document.firmsForm.txtstatus[0].checked=true;
			  else
		           document.firmsForm.txtstatus[1].checked=true;
                    
                    items[0]=supId1;
                    items[1]=supName1;
                    items[2]=supAddr1;
                    items[3]=supAddr11;
                    items[4]=supCity1;
                    items[5]=supEmail1;
                    items[6]=supPhone1;
                    items[7]=supFax1;
                    items[8]=DoReg1;
                    items[9]=DoLast_supp1;
                    items[10]=supAliasName1;
                    items[11]=pincode1;
                    items[12]=status1;
                    if(items[2]=="null")
                    {
                    items[2]="";
                    }
                    if(items[3]=="null")
                    {
                    items[3]="";
                    }
                    if(items[5]=="null")
                    {
                    items[5]="";
                    }
                    if(items[6]=="null")
                    {
                    items[6]="";
                    }
                    if(items[7]=="null")
                    {
                    items[7]="";
                    }
                   if(items[4]=="null")
                    {
                    items[4]="";
                    }
                    if(items[8]=="null")
                    {
                    items[8]="";
                    }
                    if(items[9]=="null")
                    {
                    items[9]="";
                    }
                    if(items[10]=="null")
                    {
                    items[10]="";
                    }
                    if(items[11]==0)
                    {
                    items[11]="";
                    }
                    
                    document.firmsForm.txtfirmId.value=items[0];
                    document.firmsForm.txtfirmName.value=items[1];
                    document.firmsForm.txtaddr.value=items[2];
                    document.firmsForm.txtaddr2.value=items[3];
                    document.firmsForm.txtcity.value=items[4];
                    document.firmsForm.txtPhone.value=items[6];
                    document.firmsForm.txtFax.value=items[7];
                    document.firmsForm.txtEmail.value=items[5];
                    document.firmsForm.txtDateReg.value=items[8];
                    document.firmsForm.txtDateLastSupply.value=items[9];
                    
                    document.firmsForm.txtFirmsAliasId.value=items[10];
                    document.firmsForm.txtPincode.value=items[11];
                    document.firmsForm.txtstatus.value=items[12];
            }     
                          
            var d=document.getElementById("cmdUpdate");
            d.style.display="block";
            
            var d2=document.getElementById("cmdDelete");
            d2.style.display="block";
            
            var d1=document.getElementById("cmdAdd");
            d1.style.display="none";  
            
            if (window_firms && window_firms.open && !window_firms.closed) window_firms.close();
        }
        else
        {
            alert("Record retrival failed");
        }
    
}


function addRow(baseResponse)
{
    var flag=baseResponse.getElementsByTagName("flag")[0].firstChild.nodeValue;
    
    if(flag=="success")
    {
        var GenId=baseResponse.getElementsByTagName("GenId")[0].firstChild.nodeValue;
        document.firmsForm.txtfirmId.value=GenId;
        alert("Firms Id...."+GenId);
        alert("Records inserted into database");
      /*
        var items=new Array();
        
        
        items[0]=GenId;
        items[1]=document.firmsForm.txtfirmName.value;
        items[2]=document.firmsForm.txtaddr.value;
        items[3]=document.firmsForm.txtaddr2.value;
        items[4]=document.firmsForm.txtcity.value;
         items[5]=document.firmsForm.txtEmail.value;
        items[6]=document.firmsForm.txtPhone.value;
        items[7]=document.firmsForm.txtFax.value;
       
        items[8]=document.firmsForm.txtDateReg.value;
        items[9]=document.firmsForm.txtDateLastSupply.value;
        
        var tbody=document.getElementById("tb");
        var mycurrent_row=document.createElement("TR");
        
        mycurrent_row.id=items[0];
        var cell=document.createElement("TD");
        var anc=document.createElement("A");
        var url="javascript:loadTable('"+items[0]+"')";
        anc.href=url;
        var txtedit=document.createTextNode("Edit");
        anc.appendChild(txtedit);
        cell.appendChild(anc);
        mycurrent_row.appendChild(cell);
        var i=0;
        var cell2;
        
        for(i=0;i<2;i++)
        {
            cell2=document.createElement("TD");
            var currentText=document.createTextNode(items[i]);
            cell2.appendChild(currentText);
            mycurrent_row.appendChild(cell2);
        }
        
        var cell3;
        cell3=document.createElement("TD");
        var break1=document.createElement('br'); 
        var addr=document.createTextNode(items[2]);
        var addr2=document.createTextNode(items[3]);
        cell3.appendChild(addr);
        cell3.appendChild(break1);
        cell3.appendChild(addr2);
            mycurrent_row.appendChild(cell3);
            
          for(i=4;i<8;i++)
        {
            cell2=document.createElement("TD");
            var currentText=document.createTextNode(items[i]);
            cell2.appendChild(currentText);
            mycurrent_row.appendChild(cell2);
        }  
        
        var cell4;
        cell4=document.createElement("TD");
        var break1=document.createElement('br'); 
        var datereg=document.createTextNode(items[8]);
        var datelast=document.createTextNode(items[9]);
        cell4.appendChild(datereg);
        cell4.appendChild(break1);
        cell4.appendChild(datelast);
            mycurrent_row.appendChild(cell4);
            
          for(i=8;i<9;i++)
        {
            cell2=document.createElement("TD");
            var currentText=document.createTextNode(items[i]);
            cell2.appendChild(currentText);
            mycurrent_row.appendChild(cell2);
        }
        
        tb.appendChild(mycurrent_row);
        
        document.firmsForm.comOffId.selectedIndex=0;
        document.firmsForm.txtfirmId.value="";
        document.firmsForm.txtfirmName.value="";
        document.firmsForm.txtaddr.value="";
        document.firmsForm.txtaddr2.value="";
        document.firmsForm.txtcity.value="";
        document.firmsForm.txtPhone.value="";
        document.firmsForm.txtFax.value="";
        document.firmsForm.txtEmail.value="";
        document.firmsForm.txtDateReg.value="";
        document.firmsForm.txtDateLastSupply.value="";
        
       */
        ClearAll();
    }
    else
    {
        alert("Records r not inserted");
    }
}

function deleteRow(baseResponse)
{
    var flag=baseResponse.getElementsByTagName("flag")[0].firstChild.nodeValue;
    
    if(flag=="success")
    {
        alert("Records Canceled from database");
        
      /*  var sc=baseResponse.getElementsByTagName("scd")[0].firstChild.nodeValue;
        
        var tbody=document.getElementById("mytable");
        var r=document.getElementById(sc);
        var ri=r.rowIndex;
        tbody.deleteRow(ri);
        
        //document.firmsForm.cmbAcc_UnitCode.value="";
        document.firmsForm.comOffId.selectedIndex=0;
        document.firmsForm.txtfirmId.value="";
        document.firmsForm.txtfirmName.value="";
        document.firmsForm.txtaddr.value="";
        document.firmsForm.txtaddr2.value="";
        document.firmsForm.txtcity.value="";
        document.firmsForm.txtPhone.value="";
        document.firmsForm.txtFax.value="";
        document.firmsForm.txtEmail.value="";
        document.firmsForm.txtDateReg.value="";
        document.firmsForm.txtDateLastSupply.value="";
        */
        ClearAll();
    }
    else
    {
        alert("Records r not Canceled");
    }
}

function UpdateRow(baseResponse)
{
    var flag=baseResponse.getElementsByTagName("flag")[0].firstChild.nodeValue;
    var items=new Array();
    
    if(flag=="success")
    {
      /*  items[0]=document.firmsForm.txtfirmId.value;
        items[1]=document.firmsForm.txtfirmName.value;
        items[2]=document.firmsForm.txtaddr.value;
        items[3]=document.firmsForm.txtaddr2.value;
        items[4]=document.firmsForm.txtcity.value;
        items[5]=document.firmsForm.txtEmail.value;
        items[6]=document.firmsForm.txtPhone.value;
        items[7]=document.firmsForm.txtFax.value;
        
        //alert(items[0]+items[1]);
        
        var r=document.getElementById(items[0]); 
       
        var rcells=r.cells;
       
        rcells.item(1).firstChild.nodeValue=items[0];
        rcells.item(2).firstChild.nodeValue=items[1];
        rcells.item(3).firstChild.nodeValue=items[2];
        rcells.item(3).lastChild.nodeValue=items[3];
        rcells.item(4).firstChild.nodeValue=items[4];
        rcells.item(5).firstChild.nodeValue=items[5];
        rcells.item(6).firstChild.nodeValue=items[6];
        rcells.item(7).firstChild.nodeValue=items[7];
       */
        
        alert("Record Updated");
        
        ClearAll();
        
    }
    else
    {
        alert("Record not Updated");
    }
}


 function numbersonly1(e,t)
    {
       var unicode=e.charCode? e.charCode : e.keyCode;
       if(unicode==13)
        {
          try{t.blur();}catch(e){}
          return true;
        
        }
        if (unicode!=8 && unicode !=9  )
        {
            if (unicode<48||unicode>57 ) 
                return false 
        }
     }
     


//This Coding for Date Validation and Checking     
function calins(e,t)
{
    var unicode=e.charCode? e.charCode : e.keyCode;
        //alert(unicode);
        //if(unicode !=8)
        
        if (unicode!=8 && unicode !=9 && unicode!=37 && unicode !=39 && unicode !=46  && unicode !=35 && unicode !=36 )
        {
            if(t.value.length==2 || t.value.length==5)
                t.value=t.value + '/';
             if (unicode<48||unicode>57 ) 
                return false 
        }
       

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


function FirmNamecheck()
{
    
    if(!isNaN(document.firmsForm.txtfirmName.value))
    {
        alert("Enter String value for Firm Name");
        document.firmsForm.txtfirmName.value="";
        document.firmsForm.txtfirmName.focus();
        return false;
    }
    else if((document.firmsForm.txtfirmName.value==null)||(document.firmsForm.txtfirmName.value.length==0))
    {
        alert("Enter Firm Name");
        document.firmsForm.txtfirmName.focus();
        return false;
    }
    return true;
}
function checkcity()
{
 if(document.firmsForm.txtcity.value.length==0)
    {
        alert("City shouldn't empty");
        document.firmsForm.txtcity.focus();
        return false;
    }
   
    return true;

}

function pincodecheck()
{
     if(isNaN(document.firmsForm.txtPincode.value))
    {
        alert("Enter Numeric Value for pincode");
        document.firmsForm.txtPincode.value="";
        document.firmsForm.txtPincode.focus();
        return false;
               
    }
       if(document.firmsForm.txtPincode.value.length <6  || document.firmsForm.txtPincode.value==0)
        {
                    alert("Pincode No. Length atleast 6.  Zero not allowed");
                    document.firmsForm.txtPincode.focus();
                    return false;
        }
 
    return true;
}


function FaxCheck()
{
       if(document.firmsForm.txtFax.value.length <6)
        {
                    alert("Fax No. Length atleast 6");
                    document.firmsForm.txtFax.focus();
                    return false;
        }
       if(isNaN(document.firmsForm.txtFax.value))
        {
            alert("Enter Numeric value for fax number");
            document.firmsForm.txtFax.value="";
            document.firmsForm.txtFax.focus();
            return false;
        }
return true;
}
function Addr1check()
{
    if(document.firmsForm.txtaddr.value.length==0)
    {
        alert("Address1 shouldn't empty");
        document.firmsForm.txtaddr.focus();
        return false;
    }
   
    return true;
}

function PhoneCheck()
{
    
    if(isNaN(document.firmsForm.txtPhone.value))
    {
        alert("Enter Numeric Value for phone number");
        document.firmsForm.txtPhone.value="";
        document.firmsForm.txtPhone.focus();
        return false;
               
    }
       if(document.firmsForm.txtPhone.value.length <6  || document.firmsForm.txtPhone.value==0)
        {
                    alert("Phone No. Length atleast 6.  Zero not allowed");
                    document.firmsForm.txtPhone.focus();
                    return false;
        }
 
    return true;
}    

function EmailCheck()
{
    if((document.firmsForm.txtEmail.value.length!=0) && !( document.firmsForm.txtEmail.value.charAt(0)==String.fromCharCode(160) && document.firmsForm.txtEmail.value.length==1  ))
    {
        var x = document.firmsForm.txtEmail.value;
	var filter  = /^([a-zA-Z0-9_\.\-])+\@(([a-zA-Z0-9\-])+\.)+([a-zA-Z0-9]{2,4})+$/;
	if (!filter.test(x))
	 {
         alert('Enter correct email address');
        document.firmsForm.txtEmail.value="";
        document.firmsForm.txtEmail.focus();
        return false;
        }
    }
return true;
}

function nullcheck()
{
 var dateReg=document.firmsForm.txtDateReg.value.length;
var dateLast=document.firmsForm.txtDateLastSupply.value.length;

 if((document.firmsForm.comOffId.value==null)||(document.firmsForm.comOffId.value.length==0))
    {
        alert("Null Value not accepted...Select Office Code to rendered");
        document.firmsForm.comOffId.focus();
        return false;
    }
    
    if(!FirmNamecheck() || !Addr1check() || !checkcity())
    {
        return false;
    }
    /*
    if(dateReg==0)
    {
    alert("Enter the Registration date");
    document.firmsForm.txtDateReg.focus();
    return false;
    }
  
   if(dateLast==0)
    {
    alert("Enter the Last supply date");
    document.firmsForm.txtDateLastSupply.focus();
    return false;
    }
  */
  if(dateReg!=0 && dateLast!=0 )
  {  
    var regdate=document.firmsForm.txtDateReg.value.split('/');
    var lastsupplydate=document.firmsForm.txtDateLastSupply.value.split('/');
    regdate=regdate[2]+"/"+regdate[1]+"/"+regdate[0];
    lastsupplydate=lastsupplydate[2]+"/"+lastsupplydate[1]+"/"+lastsupplydate[0];
    //alert(regdate+"  "+lastsupplydate)
     if(lastsupplydate<regdate)
    {
        alert("DATE OF LAST SUPPLY SHOULD BE GREATER THAN DATE OF REGISTRATION");
        document.firmsForm.txtDateLastSupply.focus();
        return false;
    }
  } 
    return true;
}

 /////////////////////////////---------------------- no need
 /*
 
function SuppAddr2check()
{
    if((document.firmsForm.txtaddr2.value==null)||(document.firmsForm.txtaddr2.value.length==0))
    {
        alert("Null Value not accepted...Enter Address2");
        document.firmsForm.txtaddr2.focus();
        return false;
    }
   
    return true;
}



function loadTable(scod)
{
    //alert("here");
    
    //document.firmsForm.comOffId.focus();
    var r=document.getElementById(scod);
    var rcells=r.cells;
    var tbody=document.getElementById("tb");
    var table=document.getElementById("mytable");
    
         var url="../../../../../FirmsServ.view?Command=loadtab&txtfirmId="+scod;
           //alert(url);
            var req=getTransport();
            req.open("GET",url,true); 
            req.onreadystatechange=function()
            {
               handleResponse(req);
            }   
                    req.send(null);
    
        
       
    
        document.firmsForm.txtfirmId.value=rcells.item(1).firstChild.nodeValue;
        document.firmsForm.txtfirmName.value=rcells.item(2).firstChild.nodeValue;
        document.firmsForm.txtaddr.value=rcells.item(3).firstChild.nodeValue;
        document.firmsForm.txtaddr2.value=rcells.item(3).lastChild.nodeValue;
        document.firmsForm.txtcity.value=rcells.item(4).firstChild.nodeValue;
        document.firmsForm.txtEmail.value=rcells.item(5).firstChild.nodeValue;
        document.firmsForm.txtFax.value=rcells.item(7).firstChild.nodeValue;
        document.firmsForm.txtPhone.value=rcells.item(6).firstChild.nodeValue;
                      
    
    var d=document.getElementById("cmdAdd");
    d.style.display="none";
    
    var d1=document.getElementById("cmdUpdate");
    d1.style.display="block";
    
    var d3=document.getElementById("cmdDelete");
    d3.style.display="block";
}




function loadtabRow(baseResponse)
{
    var flag=baseResponse.getElementsByTagName("flag")[0].firstChild.nodeValue;
    //alert(flag);
    var accId=baseResponse.getElementsByTagName("accId")[0].firstChild.nodeValue;
  //  alert(accId);
    var offId=baseResponse.getElementsByTagName("offId")[0].firstChild.nodeValue;
    //alert(offId);
    var dateReg=baseResponse.getElementsByTagName("dateReg")[0].firstChild.nodeValue;
   // alert(dateReg);
    var datelast=baseResponse.getElementsByTagName("datelast")[0].firstChild.nodeValue;
    
     var FirmsAliasId=baseResponse.getElementsByTagName("FirmsAliasId")[0].firstChild.nodeValue;
      var Pincode=baseResponse.getElementsByTagName("Pincode")[0].firstChild.nodeValue;
    //alert(datelast);
    //alert(jj);
    if(flag=="success")
    {
        
        
        document.firmsForm.txtFirmsAliasId.value=FirmsAliasId;
        document.firmsForm.txtPincode.value=Pincode;
        
        document.firmsForm.comOffId.value=offId;
        document.firmsForm.txtDateReg.value=dateReg;
        document.firmsForm.txtDateLastSupply.value=datelast;
    }
    else
    {       
    
       // document.Hrm_TransJoinForm.txtJR.value=jj;

        document.firmsForm.txtfirmId.value=jj;
       // alert("Programme ID already exists");
        
        
    }
}



function geneIdRow(baseResponse)
{
    var flag=baseResponse.getElementsByTagName("flag")[0].firstChild.nodeValue;
    var jj=baseResponse.getElementsByTagName("j")[0].firstChild.nodeValue;
    //alert(jj);
    if(flag=="success")
    {
        //document.Hrm_TransJoinForm.txtJR.value=jj;
             document.firmsForm.txtfirmId.value=jj;
       
      // alert("Join Report Id generated...."+document.firmsForm.txtSuppId.value);
    }
    else
    {       
    
       // document.Hrm_TransJoinForm.txtJR.value=jj;

        document.firmsForm.txtfirmId.value=jj;
       // alert("Programme ID already exists");
        
        
    }
}



*/