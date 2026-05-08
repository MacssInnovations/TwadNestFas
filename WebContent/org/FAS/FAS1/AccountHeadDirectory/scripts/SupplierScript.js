
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
        //document.supplierForm.cmbAcc_UnitCode.value="";
        //document.supplierForm.comOffId.selectedIndex=0;
        document.supplierForm.txtSuppId.value="";
        document.supplierForm.txtSuppName.value="";
        document.supplierForm.txtaddr.value="";
        document.supplierForm.txtcity.value="";
        document.supplierForm.txtaddr2.value="";
        document.supplierForm.txtPhone.value="";
        document.supplierForm.txtFax.value="";
        document.supplierForm.txtEmail.value="";
        document.supplierForm.txtDateReg.value="";
        document.supplierForm.txtDateLastSupply.value="";
        
        document.supplierForm.txtPincode.value="";
        document.supplierForm.txtSuppAliasId.value="";
        var d=document.getElementById("cmdAdd");
        d.style.display="block";
    
        var d1=document.getElementById("cmdUpdate");
        d1.style.display="none";
        
        var d3=document.getElementById("cmdDelete");
        d3.style.display="none";
        
    }
  
var window_supplier;
function ListSupplier()
    {
         if (window_supplier && window_supplier.open && !window_supplier.closed) 
            {
               window_supplier.resizeTo(500,500);
               window_supplier.moveTo(250,250); 
               window_supplier.focus();
            }
            else
            {
                window_supplier=null
            }
        // var cmbAcc_UnitCode=document.getElementById("cmbAcc_UnitCode").value  ;  
         //var cmbOffice_code=document.getElementById("comOffId").value;
         //window_supplier= window.open("SupplierListJSP.jsp?cmbAcc_UnitCode="+cmbAcc_UnitCode+"&cmbOffice_code="+cmbOffice_code,"mywindow1","resizable=YES, scrollbars=yes"); 
         window_supplier= window.open("SupplierListJSP.jsp?","mywindow1","resizable=YES, scrollbars=yes"); 
         window_supplier.moveTo(250,250);    
    }

window.onunload=function()
{
if (window_supplier && window_supplier.open && !window_supplier.closed) window_supplier.close();
}
function ParentSupplier(scod)
{       
        ClearAll();
        var cmbAcc_UnitCode=document.getElementById("cmbAcc_UnitCode").value;
        var cmbOffice_code=document.getElementById("comOffId").value;
        var url="../../../../../SupplierListServ.view?cmd=listedit&scod="+scod+"&cmbAcc_UnitCode="+cmbAcc_UnitCode+"&cmbOffice_code="+cmbOffice_code;;
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
  
        var Offcode=document.supplierForm.cmbAcc_UnitCode.value;
        var OffCodeRen=document.supplierForm.comOffId.value;
        var pincode=document.supplierForm.txtPincode.value;
        var aliasId=document.supplierForm.txtSuppAliasId.value;
        var SuppId=document.supplierForm.txtSuppId.value;
        var SuppName=document.supplierForm.txtSuppName.value;
        var addr=document.supplierForm.txtaddr.value;
        var addr2=document.supplierForm.txtaddr2.value;
        var city=document.supplierForm.txtcity.value; 
        var Phone=document.supplierForm.txtPhone.value;
        var Fax=document.supplierForm.txtFax.value;
        var Email=document.supplierForm.txtEmail.value;
        var DateofReg=document.supplierForm.txtDateReg.value;
       
        var DateofLast=document.supplierForm.txtDateLastSupply.value;
          var status  = "";
		    if(document.supplierForm.txtstatus[0].checked==true)
		    	status="L";
		    else 
		    	status="C";
    var flag="";
    
    if(Command=="Add")
    {
        var flag=nullcheck();
        if(flag==true)
           {
            var url="../../../../../SupplierServ.view?Command=Add&txtSuppAliasId="+aliasId+"&txtPincode="+pincode+
                    "&cmbAcc_UnitCode="+Offcode+"&comOffId="+OffCodeRen+"&txtSuppName="+SuppName+"&txtaddr="+addr+
                    "&txtPhone="+Phone+"&txtFax="+Fax+"&txtEmail="+Email+"&txtDateReg="+DateofReg+"&txtDateLastSupply="+DateofLast+
                    "&txtaddr2="+addr2+"&txtcity="+city+"&status="+status;
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
           
                var url="../../../../../SupplierServ.view?Command=geneId";
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
        var url="../../../../../SupplierServ.view?Command=Cancel&txtSuppId="+SuppId;
        
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
                var url="../../../../../SupplierServ.view?Command=Update&txtSuppAliasId="+aliasId+"&txtPincode="+pincode+
                "&cmbAcc_UnitCode="+Offcode+"&comOffId="+OffCodeRen+"&txtSuppId="+SuppId+"&txtSuppName="+SuppName+"&txtaddr="+addr+
                "&txtPhone="+Phone+"&txtFax="+Fax+"&txtEmail="+Email+"&txtDateReg="+DateofReg+"&txtDateLastSupply="+DateofLast+
                "&txtaddr2="+addr2+"&txtcity="+city+"&status="+status;       
                
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
            //alert(Command);
            if(Command=="Add")
            {
                addRow(baseResponse);
            }
            
            else if(Command=="geneId")
            {
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

function fillvalues(baseResponse)
{
     var flag=baseResponse.getElementsByTagName("flag")[0].firstChild.nodeValue;
        if(flag=="success")
        {

            var j=0;
            var supId=baseResponse.getElementsByTagName("supplierID");
            var len=supId.length;
            for(j=0;j<len;j++)
            {
                    var supId=baseResponse.getElementsByTagName("supplierID");
                    var supName=baseResponse.getElementsByTagName("supplierName");
                    var supAliasName=baseResponse.getElementsByTagName("supplierAliasName");
                    var supAddr=baseResponse.getElementsByTagName("supAddr");
                    var supPhone=baseResponse.getElementsByTagName("supPhone");
                    var supFax=baseResponse.getElementsByTagName("supFax");
                    var supEmail=baseResponse.getElementsByTagName("supemail");
                    var suplierAddr1=baseResponse.getElementsByTagName("supaddr1");
                    var supCity=baseResponse.getElementsByTagName("supcity");
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
                     if(status1=="L")
			   document.supplierForm.txtstatus[0].checked=true;
			  else
		           document.supplierForm.txtstatus[1].checked=true;
                    

                    
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
                     if(items[8]=="null")
                    {
                    items[8]="";
                    }
                    document.supplierForm.txtSuppId.value=items[0];
                    document.supplierForm.txtSuppName.value=items[1];
                    document.supplierForm.txtaddr.value=items[2];
                    document.supplierForm.txtaddr2.value=items[3];
                    document.supplierForm.txtcity.value=items[4];
                    document.supplierForm.txtPhone.value=items[6];
                    document.supplierForm.txtFax.value=items[7];
                    document.supplierForm.txtEmail.value=items[5];
                    document.supplierForm.txtDateReg.value=items[8];
                    document.supplierForm.txtDateLastSupply.value=items[9];
                    
                    document.supplierForm.txtSuppAliasId.value=items[10];
                    document.supplierForm.txtPincode.value=items[11];
                    document.supplierForm.txtstatus.value=items[12];


            }     
                          
            var d=document.getElementById("cmdUpdate");
            d.style.display="block";
            
            var d2=document.getElementById("cmdDelete");
            d2.style.display="block";
            
            var d1=document.getElementById("cmdAdd");
            d1.style.display="none";  
            if (window_supplier && window_supplier.open && !window_supplier.closed) window_supplier.close();
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
        document.supplierForm.txtSuppId.value=GenId
       
        alert("SUPPLIER ID...."+GenId);
         alert("Records inserted successfully");
      
      /*  var items=new Array();
        
        items[0]=GenId;
        items[1]=document.supplierForm.txtSuppName.value;
        items[2]=document.supplierForm.txtaddr.value;
        items[3]=document.supplierForm.txtaddr2.value;
        items[4]=document.supplierForm.txtcity.value;
        items[5]=document.supplierForm.txtEmail.value;
        items[6]=document.supplierForm.txtPhone.value;
        items[7]=document.supplierForm.txtFax.value;
        items[8]=document.supplierForm.txtDateReg.value;
        items[9]=document.supplierForm.txtDateLastSupply.value;
        
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
            
        tb.appendChild(mycurrent_row);
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
        
        //document.supplierForm.cmbAcc_UnitCode.value="";
        document.supplierForm.comOffId.selectedIndex=0;
        document.supplierForm.txtSuppId.value="";
        document.supplierForm.txtSuppName.value="";
        document.supplierForm.txtaddr.value="";
        document.supplierForm.txtaddr2.value="";
        document.supplierForm.txtcity.value="";
        document.supplierForm.txtPhone.value="";
        document.supplierForm.txtFax.value="";
        document.supplierForm.txtEmail.value="";
        document.supplierForm.txtDateReg.value="";
        document.supplierForm.txtDateLastSupply.value="";   */
        
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
      /*  items[0]=document.supplierForm.txtSuppId.value;
        items[1]=document.supplierForm.txtSuppName.value;
        items[2]=document.supplierForm.txtaddr.value;
        items[3]=document.supplierForm.txtaddr2.value;
        items[4]=document.supplierForm.txtcity.value;
        items[5]=document.supplierForm.txtEmail.value;
        //alert(items[5]);
        items[6]=document.supplierForm.txtPhone.value;
        items[7]=document.supplierForm.txtFax.value;
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
        rcells.item(7).firstChild.nodeValue=items[7];*/
        
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
            alert('Date format  should be (dd-mm-yyyy)');
            t.value="";
            //t.focus();
            return false
    }
    
}

function SuppNamecheck()
{
    
    if(!isNaN(document.supplierForm.txtSuppName.value))
    {
        alert("Enter Supplier name");
        document.supplierForm.txtSuppName.value="";
        document.supplierForm.txtSuppName.focus();
        return false;
    }
    else
    return true;
}

function pincodecheck()
{
     if(isNaN(document.supplierForm.txtPincode.value))
    {
        alert("Enter Numeric Value for pincode");
        document.supplierForm.txtPincode.value="";
        document.supplierForm.txtPincode.focus();
        return false;
               
    }
       if(document.supplierForm.txtPincode.value.length <6  || document.supplierForm.txtPincode.value==0)
        {
                    alert("Pincode No. Length atleast 6.  Zero not allowed");
                    document.supplierForm.txtPincode.focus();
                    return false;
        }
 
    return true;
}
function PhoneCheck()
{
    
    if(isNaN(document.supplierForm.txtPhone.value))
    {
        alert("Enter Numeric Value for phone number");
        document.supplierForm.txtPhone.value="";
        document.supplierForm.txtPhone.focus();
        return false;
               
    }
       if(document.supplierForm.txtPhone.value.length <6  || document.supplierForm.txtPhone.value==0)
        {
                    alert("Phone No. Length atleast 6.  Zero not allowed");
                    document.supplierForm.txtPhone.focus();
                    return false;
        }
 
    return true;
}    

function FaxCheck()
{
       if(document.supplierForm.txtFax.value.length <6)
        {
                    alert("Fax No. Length atleast 6");
                    document.supplierForm.txtFax.focus();
                    return false;
        }
       if(isNaN(document.supplierForm.txtFax.value))
        {
            alert("Enter Numeric value for fax number");
            document.supplierForm.txtFax.value="";
            document.supplierForm.txtFax.focus();
            return false;
        }
return true;
}

function EmailCheck()
{
    if((document.supplierForm.txtEmail.value.length!=0))
    {
        var x = document.supplierForm.txtEmail.value;
	var filter  = /^([a-zA-Z0-9_\.\-])+\@(([a-zA-Z0-9\-])+\.)+([a-zA-Z0-9]{2,4})+$/;
	if (!filter.test(x))
	 {
         alert('Enter correct email address');
        document.supplierForm.txtEmail.value="";
        document.supplierForm.txtEmail.focus();
        return false;
        }
    }
return true;
}

  
function SuppAddr1check()
{
    if(document.supplierForm.txtaddr.value.length==0)
    {
        alert("Address1 shouldn't empty");
        document.supplierForm.txtaddr.focus();
        return false;
    }
   
    return true;
}

function checkcity()
{
 if(document.supplierForm.txtcity.value.length==0)
    {
        alert("City shouldn't empty");
        document.supplierForm.txtcity.focus();
        return false;
    }
   
    return true;

}

function nullcheck()
{
 var dateReg=document.supplierForm.txtDateReg.value.length;
 var dateLast=document.supplierForm.txtDateLastSupply.value.length;

 if((document.supplierForm.comOffId.value==null)||(document.supplierForm.comOffId.value.length==0))
    {
        alert("Null Value not accepted...Select Office Code to rendered");
        document.supplierForm.comOffId.focus();
        return false;
    }
    
    if(!SuppNamecheck() || !SuppAddr1check() || !checkcity())
    {
        return false;
    }
   /* 
   if(dateReg==0)
    {
    alert("Enter the Registration date");
    document.supplierForm.txtDateReg.focus();
    return false;
    } 
   
    if(dateLast==0)
    {
    alert("Enter the Last supply date");
    document.supplierForm.txtDateLastSupply.focus();
    return false;
    }
      */
    if(dateReg!=0 && dateLast!=0 )
    {
        var regdate=document.supplierForm.txtDateReg.value.split('/');
        var lastsupplydate=document.supplierForm.txtDateLastSupply.value.split('/');
        regdate=regdate[2]+"/"+regdate[1]+"/"+regdate[0];
        lastsupplydate=lastsupplydate[2]+"/"+lastsupplydate[1]+"/"+lastsupplydate[0];
        //alert(regdate+"  "+lastsupplydate)
         if(lastsupplydate<regdate)
        {
            alert("DATE OF LAST SUPPLY SHOULD BE GREATER THAN DATE OF REGISTRATION");
            document.supplierForm.txtDateLastSupply.focus();
            return false;
        }
    }
    return true;
}


///////////////////////////--------------------- no need-------------


/*

function geneIdRow(baseResponse)
{
    var flag=baseResponse.getElementsByTagName("flag")[0].firstChild.nodeValue;
    var jj=baseResponse.getElementsByTagName("j")[0].firstChild.nodeValue;
    if(flag=="success")
    {
        //document.Hrm_TransJoinForm.txtJR.value=jj;
             document.supplierForm.txtSuppId.value=jj;
       
    }
    else
    {       
    
       // document.Hrm_TransJoinForm.txtJR.value=jj;

        document.supplierForm.txtSuppId.value=jj;
       // alert("Programme ID already exists");
        
        
    }
}




function validate_date(formName,textName)
 {
                var errMsg="", lenErr=false, dateErr=false;
                var testObj=eval('document.' + formName + '.' + textName + '.value');
                var testStr=testObj.split('/');
                if(testStr.length>3 || testStr.length<3)
                {
                    lenErr=true;
                    dateErr=true;
                    errMsg+="There is an error in the date format.";
                }
                var monthsArr = new Array("01", "02", "03", "04", "05", "06", "07", "08" ,"09", "10", "11", "12");
                var daysArr = new Array;
                for (var i=0; i<12; i++)
                {
                    if(i!=1)
                    {
                       if((i/2)==(Math.round(i/2)))
                       {
                          if(i<=6)
                          {
                              daysArr[i]="31";
                           }
                           else
                           {
                               daysArr[i]="30";
                           }
                        }
                       else
                       {
                          if(i<=6)
                          {
                                daysArr[i]="30";
                          }
                          else
                          {
                               daysArr[i]="31";
                          }
                       }
                    }
                    else
                    {
                        if((testStr[2]/4)==(Math.round(testStr[2]/4)))
                        {
                            daysArr[i]="29";
                        }
                        else
                        {
                            daysArr[i]="28";
                        }
                    }
                } 
                var monthErr=false, yearErr=false;
                if(testStr[2]<1000 && !lenErr)
                {
                    yearErr=true;
                    dateErr=true;
                    errMsg+="\nThe year \"" + testStr[2] + "\" is not correct.";
                }
                for(var i=0; i<12; i++)
                {
                    if(testStr[1]==monthsArr[i])
                    {
                      var setMonth=i;
                      break;
                    }
                }
                if(!lenErr && (setMonth==undefined))
                {
                    monthErr=true;
                    errMsg+="\nThe month \"" + testStr[1] + "\" is not correct.";
                    dateErr=true;
                }
                if(!monthErr && !yearErr && !lenErr)
                {
                    if(testStr[0]>daysArr[setMonth])
                    {
                        errMsg+=testStr[1] + ' ' + testStr[2] + ' does not have ' + testStr[0] + ' days.';
                        dateErr=true;
                    }
                }
                if(!dateErr)
                {
                    //eval('document.' + formName + '.' + 'submit()');
                }
                else
                {
                    alert(errMsg + '\n____________________________\n\nSample Date Format :\n dd/MM/yyyy');
                    eval('document.' + formName + '.' + textName + '.focus()');
                    //alert(eval);
                    eval('document.' + formName + '.' + textName + '.select()');
                    
                    return false;
                }
                
                 return true;  
                     
 }
 
 
 


function loadtabRow(baseResponse)
{
    var flag=baseResponse.getElementsByTagName("flag")[0].firstChild.nodeValue;
    var accId=baseResponse.getElementsByTagName("accId")[0].firstChild.nodeValue;
    var offId=baseResponse.getElementsByTagName("offId")[0].firstChild.nodeValue;
    var dateReg=baseResponse.getElementsByTagName("dateReg")[0].firstChild.nodeValue;
    var datelast=baseResponse.getElementsByTagName("datelast")[0].firstChild.nodeValue;
    
    var Pincode=baseResponse.getElementsByTagName("Pincode")[0].firstChild.nodeValue;
    var SuppAliasId=baseResponse.getElementsByTagName("SuppAliasId")[0].firstChild.nodeValue;
    var stremail=baseResponse.getElementsByTagName("stremail")[0].firstChild.nodeValue;
    if(document.supplierForm.txtaddr2.value==null)
    {
    document.supplierForm.txtaddr2.value="";
    }
    //alert(jj);
    if(flag=="success")
    {
        
        
       // document.supplierForm.cmbAcc_UnitCode.value=offId;
        document.supplierForm.comOffId.value=offId;
        document.supplierForm.txtDateReg.value=dateReg;
        document.supplierForm.txtDateLastSupply.value=datelast;
        
        document.supplierForm.txtSuppAliasId.value=SuppAliasId;
        document.supplierForm.txtPincode.value=Pincode;
         document.supplierForm.txtEmail.value=stremail;
    }
    else
    {       
    
       // document.Hrm_TransJoinForm.txtJR.value=jj;

        document.supplierForm.txtSuppId.value=jj;
       // alert("Programme ID already exists");
        
        
    }
}

*/