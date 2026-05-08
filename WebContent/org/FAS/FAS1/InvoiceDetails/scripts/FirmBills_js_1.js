var min_type="";var sub_type="";
function AjaxFunction()
    {
        var xmlrequest=false;
        try
            {
               xmlrequest=new ActiveXObject("Msxml2.XMLHTTP"); 
            }
         catch(e1)
          {
                 try
                 {
                     xmlrequest=new ActiveXObject("Microsoft.XMLHTTP"); 
                 }
                 catch(e2)
                 {     
                     xmlrequest=false;
                 }
          }
          if (!xmlrequest && typeof XMLHttpRequest != 'undefined') 
                {
                     xmlrequest=new XMLHttpRequest();
                }
        return xmlrequest;
    } 
function checkBillDate()
{
	 var billdate=document.forms[0].billdate.value;
	 var idate=document.forms[0].invoicedate.value;
	 var bookdate=document.forms[0].mbookdate.value;
	 if(idate>billdate)
	  {
		 alert("InvoiceDate should be less than or equal to BillDate");
		 document.forms[0].invoicedate.value="";
		 document.forms[0].invoicedate.focus();
	  }
	 else if(bookdate>billdate)
	 {
		 alert("M-BookDate should be less than or equal to BillDate");
		 document.forms[0].mbookdate.value="";
		 document.forms[0].mbookdate.focus();
	 }
	 
}

function checkRecDate()
{
	 var billdate=document.forms[0].billdate.value;
	 var idate=document.forms[0].invoicedate.value;
	 var irecdate=document.forms[0].invoicereceivedondate.value;
	 
	 if(irecdate>billdate)
	 {
		 alert("InvoiceReceivedDate should be less than or equal to BillDate");
		 document.forms[0].invoicereceivedondate.value="";
		 document.forms[0].invoicereceivedondate.focus();
	 }
	else if(irecdate<idate)
	 {
		 alert("InvoiceReceivedOnDate should be greater than or equal to InvoiceDate");
		 document.forms[0].invoicereceivedondate.value="";
		 document.forms[0].invoicereceivedondate.focus();
	 }
}


function callmajorType()
    {
            var url="../../../../../FirmBills_Serv?command=majorType";
            var xmlrequest= AjaxFunction();
            xmlrequest.open("GET",url,true);              
            xmlrequest.onreadystatechange=function()
            {
                manipulate(xmlrequest);
            }
            xmlrequest.send(null);
    }

function  callminor()
    {
            var major1=document.forms[0].billmajortype.value;
            var url="../../../../../FirmBills_Serv?command=minorType&major2="+major1;
            var xmlrequest= AjaxFunction();
            xmlrequest.open("GET",url,true);              
            xmlrequest.onreadystatechange=function()
            {
                manipulate(xmlrequest);
            }
            xmlrequest.send(null);     
    }
    
function  callsub(param)
    {
			var major1=document.forms[0].billmajortype.value;
            var url="../../../../../FirmBills_Serv?command=subType&sub2="+param+"&major2="+major1;
            var xmlrequest= AjaxFunction();
            xmlrequest.open("GET",url,true);              
            xmlrequest.onreadystatechange=function()
            {
                manipulate(xmlrequest);
            }
            xmlrequest.send(null);     
    }
    
function callidval()
    {
            var url="../../../../../FirmBills_Serv?command=oid";    
            var xmlrequest= AjaxFunction();
            xmlrequest.open("GET",url,true);              
            xmlrequest.onreadystatechange=function()
            {
                manipulate(xmlrequest);
            }
            xmlrequest.send(null);   
    }
    
// remove (-)symbol in invoiceamount field 
function callAmount()
    {
        var amount=document.forms[0].invoiceamount.value;
        if(amount<0)
        {
             document.forms[0].invoiceamount.value=""; 
             document.forms[0].invoiceamount.focus();
        }
    }
    
//codings for date validation
function numonly(e)
{
    var unicode=e.charCode?e.charCode:e.keyCode
    if(unicode!=8)
    {
        if(unicode<48||unicode>57)
        return false;
    }
}

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
var winemp;
function listpopup()
    {
        winemp= window.open("FirmBills_List.jsp","list","status=1,height=500,width=600,resizable=YES, scrollbars=yes"); 
        winemp.moveTo(250,250);  
        winemp.focus();
    }

function add()  
    {
			var unitid=document.forms[0].cmbAcc_UnitCode.value;
            var offid=document.forms[0].cmbOffice_code.value;
            var pay=document.forms[0].paymenttype.value;
            var maj=document.forms[0].billmajortype.value;
            var min=document.forms[0].billminortype.value;
            var sub=document.forms[0].billsubtype.value;
            var billdate=document.forms[0].billdate.value;
            var invoiceNo=document.forms[0].invoiceNo.value;
            var idate=document.forms[0].invoicedate.value;
            var irecdate=document.forms[0].invoicereceivedondate.value;
            var iamount=document.forms[0].invoiceamount.value;
            var par=document.forms[0].Particularsinvoice.value;
            var bookdate=document.forms[0].mbookdate.value;
            var bookno=document.forms[0].mbookno.value;
            var bookpageno=document.forms[0].mbookpageno.value;
            var isection=document.forms[0].initiatingsection.value;
            var expen=document.forms[0].txtAcc_HeadCode.value;
            var bud=document.forms[0].budgetalloted.value;
            var expincurred=document.forms[0].expenditureincurred.value;
            var balanvail=document.forms[0].balanceavailable.value;
            var agreeno=document.forms[0].agreementno.value;
            var agreedate=document.forms[0].agreementdate.value;
//            var workno=document.forms[0].workorderno.value;
            var fname=document.forms[0].firmname.value;
            var remarks=document.forms[0].remarks.value;
            if(invoiceNo=="")
	         {
            	 alert("Enter Invoice No");
                 document.forms[0].invoiceNo.focus();
	         }
            else if(idate=="")
             {
                    alert("Enter Invoicedate");
                    document.forms[0].invoicedate.focus();
             }
            
            else if(irecdate=="")
             {
                    alert("Enter Receivedate");
                    document.forms[0].invoicereceivedondate.focus();
             }   
            else if(isection=="")
             {
                    alert("Select Initiatingsection");
                    document.forms[0].initiatingsection.focus();
             } 
            else if(agreeno=="")
             {
                    alert("Enter Agreementno");
                    document.forms[0].agreementno.focus();
             }
            else if(agreedate=="")
             {
                    alert("Enter Agreementdate");
                    document.forms[0].agreementdate.focus();
             }
            else if(iamount=="")
             {
                alert("Enter Invoice Amount");
                document.forms[0].invoiceamount.focus();
             }
            else if(parseInt(balanvail) < parseInt(iamount))
             {
            	alert("Budget not available for this Head, Please get Reappropriation");
            	return false;
             }
            else
             {
                    var xmlrequest= AjaxFunction();
                    var url="../../../../../FirmBills_Serv?command=add&pay1="+pay+"&unitid="+unitid+"&offid="+offid+"&maj1="+maj+"&min1="+min+"&sub1="+sub+"&billdate1="+billdate+"&invoiceNo="+invoiceNo+"&idate1="+idate+"&irecdate1="+irecdate+"&iamount1="+iamount+"&par1="+par+"&bookdate1="+bookdate+"&bookno1="+bookno+"&bookpageno1="+bookpageno+"&isection1="+isection+"&expen1="+expen+"&agreeno1="+agreeno+"&agreedate1="+agreedate+"&remarks1="+remarks+"&fname="+fname;
                    xmlrequest.open("GET",url,true);              
                    xmlrequest.onreadystatechange=function()
                        {
                            manipulate(xmlrequest);
                        }
                    xmlrequest.send(null);
             }            
    }
   
function update()
    {
            var unitid=document.forms[0].cmbAcc_UnitCode.value;
            var offid=document.forms[0].cmbOffice_code.value;
            var billno=document.forms[0].billno.value;
            var billdate=document.forms[0].billdate.value;
            var pay=document.forms[0].paymenttype.value;
            var maj=document.forms[0].billmajortype.value;
            var min=document.forms[0].billminortype.value;
            var sub=document.forms[0].billsubtype.value;
            var invoiceNo=document.forms[0].invoiceNo.value;
            var idate=document.forms[0].invoicedate.value;
            var irecdate=document.forms[0].invoicereceivedondate.value;
            var iamount=document.forms[0].invoiceamount.value;
            var par=document.forms[0].Particularsinvoice.value;
            var bookdate=document.forms[0].mbookdate.value;
            var bookno=document.forms[0].mbookno.value;
            var bookpageno=document.forms[0].mbookpageno.value;
            var isection=document.forms[0].initiatingsection.value;
            var expen=document.forms[0].txtAcc_HeadCode.value;
        //    var bud=document.forms[0].budgetalloted.value;
        //    var expincurred=document.forms[0].expenditureincurred.value;
       //     var balanvail=document.forms[0].balanceavailable.value;
            var agreeno=document.forms[0].agreementno.value;
            var agreedate=document.forms[0].agreementdate.value;
       //     var workno=document.forms[0].workorderno.value;
      //      var fname=document.forms[0].firmname.value;
            var remarks=document.forms[0].remarks.value;
                       
            var xmlrequest= AjaxFunction();
            var url="../../../../../FirmBills_Serv?command=updated&billno="+billno+"&billdate1="+billdate+"&invoiceNo="+invoiceNo+"&pay1="+pay+"&unitid="+unitid+"&offid="+offid+"&maj1="+maj+"&min1="+min+"&sub1="+sub+"&idate1="+idate+"&irecdate1="+irecdate+"&iamount1="+iamount+"&par1="+par+"&bookdate1="+bookdate+"&bookno1="+bookno+"&bookpageno1="+bookpageno+"&isection1="+isection+"&expen1="+expen+"&agreeno1="+agreeno+"&agreedate1="+agreedate+"&remarks1="+remarks;
            xmlrequest.open("GET",url,true);              
            xmlrequest.onreadystatechange=function()
                {
                    manipulate(xmlrequest);
                }
            xmlrequest.send(null);
     }
   
function deleted()
    {
            var unitid=document.forms[0].cmbAcc_UnitCode.value;
            var offid=document.forms[0].cmbOffice_code.value;
            var billno=document.forms[0].billno.value;
            var billdate=document.forms[0].billdate.value;
            var r=confirm("Are U Sure?");
            if(r==true)
                {
                    var xmlrequest= AjaxFunction();
                    var url="../../../../../FirmBills_Serv?command=del&billno="+billno+"&unitid="+unitid+"&offid="+offid+"&billdate1="+billdate;
                    xmlrequest.open("GET",url,true);              
                    xmlrequest.onreadystatechange=function()
                        {
                            manipulate(xmlrequest);
                        }
                    xmlrequest.send(null);
              }      
               
    }
    
function financialDate()
{
	 var unitid=document.forms[0].cmbAcc_UnitCode.value;
	 var offid=document.forms[0].cmbOffice_code.value;
     var headcode=document.forms[0].txtAcc_HeadCode.value;
     var billdated=document.forms[0].billdate.value;
	 var billdt=billdated.split("/");
	 cashbookmonth=billdt[1];
	 cashbookyear=billdt[2];
	 var finYear;
	 if(cashbookmonth>4)
	 {
		 var f1=cashbookyear;
		 cashbookyear++;
		 var f2=cashbookyear;
		 finYear=f1+"-"+f2;
		 
	 }
	 else
		 {
			 var f4=cashbookyear;
			 cashbookyear--;
			 var f3=cashbookyear;
			 finYear=f3+"-"+f4;
	      }
	 var xmlrequest= AjaxFunction();
     var url="../../../../../FirmBills_Serv?command=budgetDet&unitid1="+unitid+"&offid1="+offid+"&finYear="+finYear+"&headcode="+headcode;
	 xmlrequest.open("GET",url,true);              
     xmlrequest.onreadystatechange=function()
         {
             manipulate(xmlrequest);
         }
     xmlrequest.send(null);
	 
}

function  manipulate(xmlrequest)
    {
    if(xmlrequest.readyState==4)
      {
          if(xmlrequest.status==200)
          {
               var baseResponse=xmlrequest.responseXML.getElementsByTagName("response")[0];  
               var tagCommand=baseResponse.getElementsByTagName("command")[0]; 
               var command=tagCommand.firstChild.nodeValue; 
               if(command=="add")
                  {
                     alert("record inserted into database successfully");     
                     clearAll();
                  }
                 else if(command=="deleted")
                  { 
                     alert("record deleted successfully");   
                     clearAll();
                  }
                else if(command=="major")
                  {
                       majortypechecking(baseResponse);
                  }
                else if(command=="minor")
                  {
                      minortypechecking(baseResponse);
                  }
                else if(command=="subb")
                  {
                      subtypechecking(baseResponse);
                  }
                 else if(command=="oid")
                  {
                      oidchecking(baseResponse);
                  } 
                else if(command=="retrieve")
                  {
                      retrievechecking(baseResponse);
                  }
                else if(command=="updated")
                  {
                      updatechecking(baseResponse);
                  }
                else if(command=="budgetDet")
                {
                	budgetChecking(baseResponse);
                } 
          }
      }
}   
//dynamically loading Billmajortype combo
function majortypechecking(baseResponse)
    {
             var billcombo = document.forms[0].billmajortype;
             var mastercode = baseResponse.getElementsByTagName("mastercode"); 
             var masterdesc = baseResponse.getElementsByTagName("masterdesc");   
             for(var i=0; i<mastercode.length; i++)
                 {
                     var opt = document.createElement('option');
                     opt.value = mastercode[i].firstChild.nodeValue;
                     opt.innerHTML = masterdesc[i].firstChild.nodeValue; //instead of using textnode ,we use innerhtml
                     billcombo.appendChild(opt);
                 }
    }
    
// depend on Billmajortype combo, minortype values are loaded 
function minortypechecking(baseResponse)
    {
	
			 var minorcmb = document.forms[0].billminortype;
             document.forms[0].billminortype.length=0;
             var minorcode = baseResponse.getElementsByTagName("minorcode");  
             var minordesc = baseResponse.getElementsByTagName("minordesc"); 
             for(var i=0; i<minorcode.length; i++)
                   {
	            	  if(minorcode.length==1)
	            	  {
	            		  var opt1 = document.createElement('option');
	                      opt1.value = 0;
	                      opt1.innerHTML ="select"; 
	                      minorcmb.appendChild(opt1);
	            		  
	            	  }
	            		  
	            	     var opt1 = document.createElement('option');
	                     opt1.value = minorcode[i].firstChild.nodeValue;
	                     opt1.innerHTML = minordesc[i].firstChild.nodeValue; 
	                     minorcmb.appendChild(opt1);
                     
                   }  
              
    }
    
// depend on Billmajortype and Billminortype combo,subtype values are loaded 
function subtypechecking(baseResponse)
    {
	
              var subcmb = document.forms[0].billsubtype;
              document.forms[0].billsubtype.length=0;
              var subcode = baseResponse.getElementsByTagName("subcode"); 
              var subdesc = baseResponse.getElementsByTagName("subdesc"); 
              for(var i=0; i<subcode.length; i++)
                   {
            	         var opt1 = document.createElement('option');
                         opt1.value = subcode[i].firstChild.nodeValue;
                         opt1.innerHTML = subdesc[i].firstChild.nodeValue; 
                         subcmb.appendChild(opt1);
                   }  
    }
    
function oidchecking(baseResponse)
    {
        var isection = document.forms[0].initiatingsection;
        var sectionid = baseResponse.getElementsByTagName("sectionid");  
        var sectionname = baseResponse.getElementsByTagName("sectionname"); 
        for(var i=0; i<sectionid.length; i++)
                 {
                     var opt = document.createElement('option');
                     opt.value = sectionid[i].firstChild.nodeValue;
                     opt.innerHTML = sectionname[i].firstChild.nodeValue; 
                     isection.appendChild(opt);
                 }
    }
//child to parent transferring
function doParentEmp(billno,billdate,major,minor,sub)    
    {   
    //    clearAll();
	
	 //   document.getElementById("billmajortype").options[document.getElementById("billmajortype").selectedIndex].text=major;
        document.getElementById("billminortype").options[document.getElementById("billminortype").selectedIndex].text=minor;
        document.getElementById("billsubtype").options[document.getElementById("billsubtype").selectedIndex].text=sub;
        
        document.forms[0].billno.value=billno;
        document.forms[0].billdate.value=billdate;
        var url="../../../../../FirmBills_Serv?command=retrieve&billno="+billno+"&billdate="+billdate;
        var xmlrequest= AjaxFunction();
        xmlrequest.open("GET",url,true); 
        xmlrequest.onreadystatechange=function()
            {
              manipulate(xmlrequest);
            }   
        xmlrequest.send(null);
    }
function budgetChecking(baseResponse)
{ 		
	var flag=baseResponse.getElementsByTagName("flag")[0].firstChild.nodeValue;
	if(flag=="success"){
		 document.forms[0].budgetalloted.value="";
		 document.forms[0].expenditureincurred.value="";
		 document.forms[0].balanceavailable.value="";
	   
		 
		 var budgetalloted =baseResponse.getElementsByTagName("budgetalloted");
	     var budgetspent =baseResponse.getElementsByTagName("budgetspent");
	     var a=budgetalloted[0].firstChild.nodeValue;
	     var b=budgetspent[0].firstChild.nodeValue;
	     document.forms[0].budgetalloted.value=a;
	     document.forms[0].expenditureincurred.value=b;
	     var c=a-b;
	     document.forms[0].balanceavailable.value=c;
	     var invoiceAmt=document.forms[0].invoiceamount.value;
		     if(invoiceAmt>c)
		     {
		    	 alert("Budget not available for this Head, Please get Reappropriation");
		     	document.forms[0].invoiceamount.focus();
		     }
	}
	else
		alert("Budget Not Alloted for this Head");
}

function clearAll()
    {
            document.forms[0].paymenttype.value="";
            document.forms[0].billmajortype.value="0";
//            document.forms[0].billminortype.value="0";
//            document.forms[0].billsubtype.value="0";
            
      //      document.getElementById("billmajortype").options[document.getElementById("billmajortype").selectedIndex].text="select";
            document.getElementById("billminortype").options[document.getElementById("billminortype").selectedIndex].text="select";
            document.getElementById("billsubtype").options[document.getElementById("billsubtype").selectedIndex].text="select";
                      
            document.forms[0].billno.value="";
            document.forms[0].billdate.value="";
            document.forms[0].invoiceNo.value="";
            document.forms[0].invoicedate.value="";
            document.forms[0].invoicereceivedondate.value="";
            document.forms[0].invoiceamount.value="";
            document.forms[0].Particularsinvoice.value="";
            document.forms[0].mbookdate.value="";
            document.forms[0].mbookno.value="";
            document.forms[0].mbookpageno.value="";
            document.forms[0].initiatingsection.value="";
            document.forms[0].txtAcc_HeadCode.value="";
            document.forms[0].txtAcc_HeadDesc.value="";
            document.forms[0].budgetalloted.value="";
            document.forms[0].expenditureincurred.value="";
            document.forms[0].balanceavailable.value="";
            document.forms[0].agreementno.value="";
            document.forms[0].agreementdate.value="";
            document.forms[0].workorderno.value="";
            document.forms[0].firmname.value="";
            document.forms[0].remarks.value="";    
            
            document.forms[0].onadd.disabled=false;  
            document.forms[0].onedit.disabled=true;
            document.forms[0].ondelete.disabled=true; 
    }
 //grid values are stuff it to the field 
function  retrievechecking(baseResponse) 
        {  
	      invoiceno=baseResponse.getElementsByTagName("invoiceno");
	      billdate=baseResponse.getElementsByTagName("billdate");
          paymenttype=baseResponse.getElementsByTagName("paymenttype");
          major= baseResponse.getElementsByTagName("major");
          minor= baseResponse.getElementsByTagName("minor");
          sub =  baseResponse.getElementsByTagName("sub");
          invoicedate =  baseResponse.getElementsByTagName("invoicedate");
          invoicereceiveddate =  baseResponse.getElementsByTagName("invoicereceiveddate");
          invoiceamount=  baseResponse.getElementsByTagName("invoiceamount");
          particulars =  baseResponse.getElementsByTagName("particulars");
          bookdate =  baseResponse.getElementsByTagName("bookdate");
          bookno=  baseResponse.getElementsByTagName("bookno");
          bookpageno =  baseResponse.getElementsByTagName("bookpageno");
          isection =  baseResponse.getElementsByTagName("isection");
          headac=  baseResponse.getElementsByTagName("headac");
          agreeno =  baseResponse.getElementsByTagName("agreeno");
          agreedate =  baseResponse.getElementsByTagName("agreedate");
          remarks =  baseResponse.getElementsByTagName("remarks");
          firmname =  baseResponse.getElementsByTagName("firmname");
          
          document.forms[0].paymenttype.value=paymenttype[0].firstChild.nodeValue; 
          min_type=minor[0].firstChild.nodeValue;
          sub_type=sub[0].firstChild.nodeValue;
          document.forms[0].billmajortype.value=major[0].firstChild.nodeValue;
            
	      	document.forms[0].invoiceNo.value=invoiceno[0].firstChild.nodeValue;
	        document.forms[0].billdate.value=billdate[0].firstChild.nodeValue;
	        document.forms[0].invoicedate.value=invoicedate[0].firstChild.nodeValue;
	        document.forms[0].invoicereceivedondate.value=invoicereceiveddate[0].firstChild.nodeValue;
	        document.forms[0].invoiceamount.value=invoiceamount[0].firstChild.nodeValue;
	        document.forms[0].Particularsinvoice.value=particulars[0].firstChild.nodeValue;
	        document.forms[0].mbookdate.value=bookdate[0].firstChild.nodeValue;
	        document.forms[0].mbookno.value=bookno[0].firstChild.nodeValue;
	        document.forms[0].mbookpageno.value=bookpageno[0].firstChild.nodeValue;
	        document.forms[0].initiatingsection.value=isection[0].firstChild.nodeValue;
	        document.forms[0].txtAcc_HeadCode.value=headac[0].firstChild.nodeValue;
	        doFunction('checkCode',headac[0].firstChild.nodeValue);
	        financialDate();
	        document.forms[0].agreementno.value=agreeno[0].firstChild.nodeValue;
            document.forms[0].agreementdate.value=agreedate[0].firstChild.nodeValue;
            document.forms[0].remarks.value=remarks[0].firstChild.nodeValue;
            document.forms[0].firmname.value=firmname[0].firstChild.nodeValue;
            
            document.forms[0].onadd.disabled=true;  
            document.forms[0].onedit.disabled=false;
            document.forms[0].ondelete.disabled=false; 

        }

function updatechecking(baseResponse)
    {
      var flag=baseResponse.getElementsByTagName("flag")[0].firstChild.nodeValue;
       if(flag=="success")
           {   
               alert("Record Updated Successfully.");
               clearAll();
           }
       else
           {
               alert("Failed to update values");
           }                                  
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


     