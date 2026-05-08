function loadTransferUnit()
{         
	unit=1;
		var cmbAcc_UnitCode=document.getElementById("cmbAcc_UnitCode").value;		 
        url="../../../../../TDA_Raised_Create?command=loadTransferUnit_TPANew&txtUnitId="+cmbAcc_UnitCode;
        req=getTransport();
        req.open("GET",url,true);        
        req.onreadystatechange=function()
        {        	  
            TPA_Raised_ServletResponse(req);
        }   ;
        req.send(null);     
}

function TPA_Raised_ServletResponse(req)
{
		if(req.readyState==4)
		{
            if(req.status==200)
            {  
            	
	            var baseResponse=req.responseXML.getElementsByTagName("response")[0];
	            var tagcommand=baseResponse.getElementsByTagName("command")[0];
	            var Command=tagcommand.firstChild.nodeValue;                                  
	            var flag=baseResponse.getElementsByTagName("flag")[0].firstChild.nodeValue;
	            var cr_amt=0.00;var dr_amt=0.00;
	            if(Command=="loadTransferUnit_tpa" ||Command=="loadTransferUnit")
                {                                       
                       if(flag=="success")
                       {     
                    	  
                    	   var txtUnitId;
                    	   if(unit==1){
                            txtUnitId=document.getElementById("TransferedID");  
                           var child=txtUnitId.childNodes;
                           for(var i=child.length-1;i>1;i--)
                           {
                        	   	  txtUnitId.removeChild(child[i]);
                           }  
                    	   }else if(unit==2)
                    	   {
                    		   txtUnitId=document.getElementById("acceptunitid");  
                               var child=txtUnitId.childNodes;
                               for(var i=child.length-1;i>1;i--)
                               {
                            	   	  txtUnitId.removeChild(child[i]);
                               }     
                    	   }
                           var items_id=new Array();
                           var items_name=new Array();                                    
                           var oid=baseResponse.getElementsByTagName("unit_id");
                           for(var k=0;k<oid.length;k++)
                           {
                                  items_id[k]=baseResponse.getElementsByTagName("unit_id")[k].firstChild.nodeValue;
                                  items_name[k]=baseResponse.getElementsByTagName("unit_name")[k].firstChild.nodeValue;				       	                                                  
                                  var option=document.createElement("OPTION");
                                  option.text=items_name[k]+"("+items_id[k]+")";
                                  option.value=items_id[k];
                                  try
                                  {
                                	  txtUnitId.add(option);
                                  }
                                  catch(errorObject)
                                  {
                                      txtUnitId.add(option,null);
                                  }
                           }
                       }
                       else
                       {                                                   
                           document.getElementById("TransferedID").value="";
                           document.getElementById("acceptunitid").value="";
                       }
               }else if(Command=="Add")
               {
            	   if(flag=="success")
                   {  
            		   alert('Authorised Successfully');
            		   clrForm();
                   } else{
                	   alert('Data Already Entered'); 
                   }      
               }else if(Command=="update")
               {
            	   if(flag=="success")
                   {  
            		   alert('Updated Successfully');
            		   document.frm_TPA_Raised_Create.butupdate.style.display = 'none';
            			document.frm_TPA_Raised_Create.butdelete.style.display= 'none';
            			document.frm_TPA_Raised_Create.butSub.style.display = 'block';
            		   clrForm();
                   } else if(flag=="Raised") {
                	   alert('TPA is already Raised.Could not Update'); 
                   }      
               }else if(Command=="delete")
               {
            	   if(flag=="success")
                   {  
            		   alert('Delete Successfully');
            		   document.frm_TPA_Raised_Create.butupdate.style.display = 'none';
           			document.frm_TPA_Raised_Create.butdelete.style.display= 'none';
           			document.frm_TPA_Raised_Create.butSub.style.display = 'block';
            		   clrForm();
                   }else if(flag=="Raised"){
                	   alert('TPA is already Raised.Could not Delete'); 
                   }      
               }
            }
		}
}

var unit=0;
function loadacceptunit()
{         
	 unit=2;
	
		var cmbAcc_UnitCode=document.getElementById("TransferedID").value;		 
        url="../../../../../TDA_Raised_Create?command=loadTransferUnit&txtUnitId="+cmbAcc_UnitCode;
        req=getTransport();
        req.open("GET",url,true);        
        req.onreadystatechange=function()
        {        	  
            TPA_Raised_ServletResponse(req);
        }   ;
        req.send(null);     
}







function clrForm()
{
	LoadAccountingUnitID('LIST_ALL_UNITS');
	setTimeout('loadTransferUnit()', 500);
	document.frm_TPA_Raised_Create.Org_CR_DR[0].checked='checked';	
	document.frm_TPA_Raised_Create.unitauthoriz[0].checked='checked';
	
	document.getElementById("effectivedate").value="";	
		
	document.getElementById("GenParticulars").value="";	

}

function storeit()
{
	if(nullcheck())
	{
		var Org_CR_DR="";
		var unitauthoriz="";
	var cmbAcc_UnitCode=document.getElementById("cmbAcc_UnitCode").value;	
	var cmbOffice_code=document.getElementById("cmbOffice_code").value;	
	
	for (var i=0; i < document.frm_TPA_Raised_Create.Org_CR_DR.length; i++)
	   {
	   if (document.frm_TPA_Raised_Create.Org_CR_DR[i].checked)
	      {
	       Org_CR_DR = document.frm_TPA_Raised_Create.Org_CR_DR[i].value;
	      }
	   }

	for (var i=0; i < document.frm_TPA_Raised_Create.unitauthoriz.length; i++)
	   {
	   if (document.frm_TPA_Raised_Create.unitauthoriz[i].checked)
	      {
	       unitauthoriz = document.frm_TPA_Raised_Create.unitauthoriz[i].value;
	      }
	   }
		
	var TransferedID=document.getElementById("TransferedID").value;	
	
	var acceptUnitId=document.getElementById("acceptunitid").value;	
	
	var effectivedate=document.getElementById("effectivedate").value;	
	
	var GenParticulars=document.getElementById("GenParticulars").value;	
	
    url="../../../../../TPA_Authorization_System?command=Add&cmbAcc_UnitCode="+cmbAcc_UnitCode+"&cmbOffice_code="+cmbOffice_code+"&Org_CR_DR="+Org_CR_DR+"&TransferedID="+TransferedID+"&effectivedate="+effectivedate+"&unitauthoriz="+unitauthoriz+"&acceptUnitId="+acceptUnitId+"&GenParticulars="+GenParticulars+"";
    req=getTransport();
    req.open("POST",url,true);        
    req.onreadystatechange=function()
    {        	  
        TPA_Raised_ServletResponse(req);
    }   ;
    req.send(null);     
	}
}

function nullcheck()
{
	
if(document.getElementById("TransferedID").value=="")
{
	alert('Please Select Accounting Unit which is to be Authorised for TPA ');
	return false;
	}
if(document.getElementById("effectivedate").value=="")
{
	alert('Please Enter Effective Date');
	return false;
	}

return true;


}
var window_ebmaster;
function ListAll()
{
	 if (window_ebmaster && window_ebmaster.open && !window_ebmaster.closed) 
     {
    	window_ebmaster.resizeTo(500,500);
    	window_ebmaster.moveTo(250,250); 
    	window_ebmaster.focus();
     }
     else
     {
    	 window_ebmaster=null;
     }
    
    var acc_unit=document.getElementById("cmbAcc_UnitCode").value;
	var office_id=document.getElementById("cmbOffice_code").value;
    
    window_ebmaster= window.open("TPA_Authorisation_System_List.jsp?acc_unit="+acc_unit+"&office_id="+office_id+"","mywindow1","resizable=YES, scrollbars=yes"); 
    window_ebmaster.moveTo(250,250);    
	
}
function doParentTPA(authunit,tpatype,reason,effectivedate,acceptunit,particulars)
{
	
	
	
       document.getElementById("pre_tpa_type").value=tpatype;
       document.getElementById("pre_authorizedunit").value=authunit;
       document.getElementById("pre_reason").value=reason;
       document.getElementById("pre_effectivedate").value=effectivedate;
      
       
	document.getElementById("TransferedID").value=authunit;
	loadacceptunit();
	document.getElementById("effectivedate").value=effectivedate;
	document.getElementById("GenParticulars").value=particulars;
	
	var restype= document.frm_TPA_Raised_Create.Org_CR_DR;
	for(i=0;i<restype.length;i++)
	{
	if(restype[i].value==tpatype)	
		restype[i].checked='checked';	
	}
	
	var res= document.frm_TPA_Raised_Create.unitauthoriz;
	for(i=0;i<res.length;i++)
	{
	if(res[i].value==reason)	
		res[i].checked='checked';	
	}
	setTimeout("callone('"+acceptunit+"')", 450); 	
	
	document.frm_TPA_Raised_Create.butupdate.style.display = 'block';
	document.frm_TPA_Raised_Create.butdelete.style.display= 'block';
	document.frm_TPA_Raised_Create.butSub.style.display = 'none';
}


function callone(scod)
{
	
     try{document.getElementById("acceptunitid").value=scod;}catch(e){}
}

function updateall()
{
	if(nullcheck())
	{
	var Org_CR_DR="";
	var unitauthoriz="";
var cmbAcc_UnitCode=document.getElementById("cmbAcc_UnitCode").value;	
var cmbOffice_code=document.getElementById("cmbOffice_code").value;	

for (var i=0; i < document.frm_TPA_Raised_Create.Org_CR_DR.length; i++)
   {
   if (document.frm_TPA_Raised_Create.Org_CR_DR[i].checked)
      {
       Org_CR_DR = document.frm_TPA_Raised_Create.Org_CR_DR[i].value;
      }
   }

for (var i=0; i < document.frm_TPA_Raised_Create.unitauthoriz.length; i++)
   {
   if (document.frm_TPA_Raised_Create.unitauthoriz[i].checked) 
      {
       unitauthoriz = document.frm_TPA_Raised_Create.unitauthoriz[i].value;
      }
   }
	
var TransferedID=document.getElementById("TransferedID").value;	

var acceptUnitId=document.getElementById("acceptunitid").value;	

var effectivedate=document.getElementById("effectivedate").value;	

var GenParticulars=document.getElementById("GenParticulars").value;	

var pre_tpa_type=document.getElementById("pre_tpa_type").value;
var pre_authorizedunit=document.getElementById("pre_authorizedunit").value;
var pre_reason=document.getElementById("pre_reason").value;
var pre_effectivedate=document.getElementById("pre_effectivedate").value;

url="../../../../../TPA_Authorization_System?command=update&pre_tpa_type="+pre_tpa_type+"&pre_reason="+pre_reason+"&pre_effectivedate="+pre_effectivedate+"&pre_authorizedunit="+pre_authorizedunit+"&cmbAcc_UnitCode="+cmbAcc_UnitCode+"&cmbOffice_code="+cmbOffice_code+"&Org_CR_DR="+Org_CR_DR+"&TransferedID="+TransferedID+"&effectivedate="+effectivedate+"&unitauthoriz="+unitauthoriz+"&acceptUnitId="+acceptUnitId+"&GenParticulars="+GenParticulars+"";
req=getTransport();
req.open("POST",url,true);        
req.onreadystatechange=function()
{        	  
    TPA_Raised_ServletResponse(req);
}   ;
req.send(null);     
}	
}


function call_mainJSP_script(fromcal_dateCtrl,blr_flag)     /////////Calender control return value handling
{
        if(blr_flag==1)         // which is used to find the receipt or voucher or payment (creation) date calling field,if so check trial balance
        {                            
                var cmbAcc_UnitCode=document.getElementById("cmbAcc_UnitCode").value;
                var cmbOffice_code=document.getElementById("cmbOffice_code").value;
                var TB_date=fromcal_dateCtrl.value;                 
                if(fromcal_dateCtrl.value.length!=0)
                {
                        var url="../../../../../Receipt_SL.view?Command=check_TB&TB_date="+TB_date+"&cmbAcc_UnitCode="+cmbAcc_UnitCode+"&cmbOffice_code="+cmbOffice_code;                         
                        var req=getTransport();
                        req.open("GET",url,true); 
                        req.onreadystatechange=function()
                        {
                                  check_TB(req,fromcal_dateCtrl);
                        }   
                        req.send(null);
                }
        }
}

function check_TB(req,dateCtrl)
{
         if(req.readyState==4)
         {
                if(req.status==200)
                {  
                        var baseResponse=req.responseXML.getElementsByTagName("response")[0];
                        var tagcommand=baseResponse.getElementsByTagName("command")[0];
                        var Command=tagcommand.firstChild.nodeValue;
                        var flag=baseResponse.getElementsByTagName("flag")[0].firstChild.nodeValue;    
                        if(flag=="success")
                        {
                           //doFunction('load_Receipt_No','null');                 //return true;
                      	
                      	
                        }
                        else if(flag=="failure")
                        {
                                  dateCtrl.value="";
                                  alert("Trial Balance Closed");//return false;//
                                  dateCtrl.focus();                                            
                        }
                        else if(flag=="finyear")
                        {
                                  // This statement get executed when financial year ( Cash Book Control Details ) has not found for the given date 
                                  dateCtrl.value="";
                                  alert("Cash Book Control Not Found ");//return false;//
                                  dateCtrl.focus();
                                  //document.getElementById("txtVoucher_No").value="";     
                        }
                        dateCheck(dateCtrl); 
                }
         }
}


function deleteall()
{
	


var pre_tpa_type=document.getElementById("pre_tpa_type").value;
var pre_authorizedunit=document.getElementById("pre_authorizedunit").value;
var pre_reason=document.getElementById("pre_reason").value;
var pre_effectivedate=document.getElementById("pre_effectivedate").value;


url="../../../../../TPA_Authorization_System?command=delete&pre_tpa_type="+pre_tpa_type+"&pre_reason="+pre_reason+"&pre_effectivedate="+pre_effectivedate+"&pre_authorizedunit="+pre_authorizedunit+"";
req=getTransport();
req.open("POST",url,true);        
req.onreadystatechange=function()
{        	  
    TPA_Raised_ServletResponse(req);
}   ;
req.send(null);     

}



function dateCheck(datechk)
{
	//alert("WELCOME!.........");
	
	var cmbAcc_UnitCode=document.getElementById("TransferedID").value;
//    var cmbOffice_code=document.getElementById("cmbOffice_code").value;
    //var txtCrea_date=document.getElementById("txtCrea_date").value;
    var txtCrea_date=datechk.value;
    
    if(datechk.value.length!=0)
    {
    var url="../../../../../Receipt_SL.view?Command=check_Date_TPA&cmbAcc_UnitCode="+cmbAcc_UnitCode+"&txtCrea_date="+txtCrea_date;
    //alert("URL===>"+url);
    var req=getTransport();
    req.open("GET",url,true); 
    req.onreadystatechange=function()
    {
      check_Date(req,datechk);
    } ;  
    req.send(null);
    }

}
function check_Date(req,datechk)
{
 if(req.readyState==4)
    {
        if(req.status==200)
        {  
            var baseResponse=req.responseXML.getElementsByTagName("response")[0];
            var tagcommand=baseResponse.getElementsByTagName("command")[0];
            var Command=tagcommand.firstChild.nodeValue;
            var flag=baseResponse.getElementsByTagName("flag")[0].firstChild.nodeValue;
            if(flag=="success")
            {
               //doFunction('load_Receipt_No','null');                 //return true;
          	document.getElementById("butSub").disabled=false;
            }
          else if(flag=="failure")
          {
          	datechk.value=""; 
          	alert("Document Date is Less than DATE_EFFECTIVE_FROM");
          	datechk.focus();
          	document.getElementById("butSub").disabled=true;
          	
//          	document.getElementById("txtReceipt_No").value="";
               
          }
          else if(flag=="success1")
          {
             //doFunction('load_Receipt_No','null');                 //return true;
          	document.getElementById("butSub").disabled=false;
          }
         else if(flag=="failure1")
         {
      	  alert("Document Date is Greater than DATE_OF_CLOSURE");
      	  datechk.value=""; 
        		//alert("Document Date is Less than DATE_ALLOWED_UPTO date");
        		datechk.focus();
        		document.getElementById("butSub").disabled=true;
//        		document.getElementById("txtReceipt_No").value="";
         }
         else 
      	   {
      	    datechk.value=""; 
      	    alert("Date Value is Null");
         		datechk.focus();
         		document.getElementById("butSub").disabled=true;
//         		document.getElementById("txtReceipt_No").value="";
      	   }
        }
    }
}