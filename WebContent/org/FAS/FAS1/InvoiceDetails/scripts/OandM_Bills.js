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


function loadAgreeNo()
{
	 var unitid=document.forms[0].cmbAcc_UnitCode.value;
	 var officeid=document.forms[0].cmbOffice_code.value;
	
    var url="../../../../../OandM_Bills_Serv?command=loadAgreeNo&unitid="+unitid+"&officeid="+officeid;
    var xmlrequest= AjaxFunction();
   // alert(url);
    xmlrequest.open("GET",url,true);              
    xmlrequest.onreadystatechange=function()
    {
        manipulate(xmlrequest);
    };
    xmlrequest.send(null);
}
function loadAgreeNoDetail()
{
	 var unitid=document.forms[0].cmbAcc_UnitCode.value;
	 var officeid=document.forms[0].cmbOffice_code.value;
	 var agreementno=document.forms[0].agreementno.value;
	 
	
    var url="../../../../../FirmBills_Serv?command=loadAgreeNoDetail&unitid="+unitid+"&officeid="+officeid+"&agreementno="+agreementno;
   // alert(url);
    var xmlrequest= AjaxFunction();
    xmlrequest.open("GET",url,true);              
    xmlrequest.onreadystatechange=function()
    {
        manipulate(xmlrequest);
    };
    xmlrequest.send(null);
}


function callmajorType()
    {
            
	//alert("Welcome callmajorType function!...");
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
   // alert("Welcome");        
	var major1=document.forms[0].billmajortype.value;
            var url="../../../../../FirmBills_Serv?command=minorType&major2="+major1;
           // alert("URL===>"+url);
            var xmlrequest= AjaxFunction();
            xmlrequest.open("GET",url,true);              
            xmlrequest.onreadystatechange=function()
            {
                manipulate(xmlrequest);
            }
            xmlrequest.send(null);     
    }

function  callnature()
{
//alert("Welcome");        
var major1=document.forms[0].billmajortype.value;
        var url="../../../../../FirmBills_Serv?command=billNature&major2="+major1;
      //  alert("URL===>"+url);
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
    //alert("Amount checking!!!!!!!!!!!!!!");    
	
	var amount=document.forms[0].invoiceamount.value;
       // alert(amount);
        var bill_amount=document.forms[0].billamount.value;
       // alert(bill_amount);
        if(amount<0)
        {
             document.forms[0].invoiceamount.value=""; 
             document.forms[0].invoiceamount.focus();
        }
        if(amount!=bill_amount)
        	{
        	alert("Invoice Amount should not be less than BillAmount");
        	document.forms[0].invoiceamount.value=""; 
            document.forms[0].invoiceamount.focus();
        	return false;
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
	var unitid=document.forms[0].cmbAcc_UnitCode.value;
    var offid=document.forms[0].cmbOffice_code.value;
        winemp= window.open("FirmBills_List.jsp?unit_id="+unitid+"&office_id="+offid,"list","status=1,height=500,width=600,resizable=YES, scrollbars=yes"); 
        winemp.moveTo(250,250);  
        winemp.focus();
    }

function add()  
    {
			var unitid=document.forms[0].cmbAcc_UnitCode.value;
            var offid=document.forms[0].cmbOffice_code.value;
            var doc_date=document.forms[0].txtCrea_date.value;  
            var sch_code=document.forms[0].sch_code.value;              
            var pay=document.forms[0].paymenttype.value;
            var maj=document.forms[0].billmajortype.value;
            var min=document.forms[0].billminortype.value;
            var sub=document.forms[0].billsubtype.value;
            var billdate=document.forms[0].billdate.value;
            var billamount=document.forms[0].billamount.value;            
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
          //  var workno=document.forms[0].workorderno.value;
            var fname=document.forms[0].firmname.value;
            var remarks=document.forms[0].remarks.value;
            var cmbMas_SL_Code1=document.forms[0].cmbMas_SL_Code1.value;
            
//            if(invoiceNo=="")
//	         {
//            	 alert("Enter Invoice No");
//                 document.forms[0].invoiceNo.focus();
//	         }
//            else if(idate=="")
//             {
//                    alert("Enter Invoicedate");
//                    document.forms[0].invoicedate.focus();
//             }
//            
//            else if(irecdate=="")
//             {
//                    alert("Enter Receivedate");
//                    document.forms[0].invoicereceivedondate.focus();
//             }   
//            else if(isection=="")
//             {
//                    alert("Select Initiatingsection");
//                    document.forms[0].initiatingsection.focus();
//             } 
//            else if(agreeno=="")
//             {
//                    alert("Enter Agreementno");
//                    document.forms[0].agreementno.focus();
//             }
//            else if(agreedate=="")
//             {
//                    alert("Enter Agreementdate");
//                    document.forms[0].agreementdate.focus();
//             }
//            else if(iamount=="")
//             {
//                alert("Enter Invoice Amount");
//                document.forms[0].invoiceamount.focus();
//             }
            if(doc_date=="")
            	{
            	alert("Enter the Doc Date");
            	document.forms[0].txtCrea_date.focus();
            	}
            else if(sch_code=="")
            	{
            	alert("Please Select the scheme");
            	document.forms[0].sch_code.focus();
            	
            	}
            else if(cmbMas_SL_Code1=="")
            	{
            	alert("Enter the Name of the office submitting the bill");
            	document.forms[0].cmbMas_SL_Code1.focus();
            	}
            else if(billdate=="")
            	{
            	alert("Please Enter the Bill Date");
            	document.forms[0].billdate.focus();
            	}
            else if(billamount=="")
        	{
        	alert("Please Enter the Bill billamount");
        	document.forms[0].billamount.focus();
        	}
            	
         /* else if(parseInt(balanvail) < parseInt(iamount))
             {
            	alert("Budget not available for this Head, Please get Reappropriation");
            	//return true;
             }*/
            else
             {
            		var xmlrequest= AjaxFunction();
                    var url="../../../../../FirmBills_Serv?command=add&pay1="+pay+"&unitid="+unitid+"&offid="+offid+"&maj1="+maj+"&min1="+min+"&sub1="+sub+"&billdate1="+billdate+"&invoiceNo="+invoiceNo+"&idate1="+idate+"&irecdate1="+irecdate+"&iamount1="+iamount+"&par1="+par+"&bookdate1="+bookdate+"&bookno1="+bookno+"&bookpageno1="+bookpageno+"&isection1="+isection+"&expen1="+expen+"&agreeno1="+agreeno+"&agreedate1="+agreedate+"&remarks1="+remarks+"&fname="+fname;
                    xmlrequest.open("GET",url,true);              
                    xmlrequest.onreadystatechange=function()
                        {
                            manipulate(xmlrequest);
                        };
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
            //  alert("&maj1="+maj+"&min1="+min+"&sub1="+sub+"&idate1="+idate);         
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
function doFunction11(cmd,headcod)
{  
if(cmd=="checkCode1")
{   
   //  var txtAcc_HeadCode=document.getElementById("txtAcc_HeadCode").value;
   // document.getElementById("txtAcc_HeadDesc").value="";
     
     //var cmbOffice_code=document.getElementById("cmbOffice_code").value;
   
  
   // if(txtAcc_HeadCode.length>=6)
   // {
        var url="../../../../../CivilAgreement?command=checkCode1&txtAcc_HeadCode="+headcod;//+"&cmbOffice_code="+cmbOffice_code; 
       var xmlrequest=AjaxFunction();
   	xmlrequest.open("GET",url,true);
   xmlrequest.onreadystatechange=function()
   {
       manipulate(xmlrequest);
   };
   xmlrequest.send(null); 
   // }         
}
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
         	   addchecking(baseResponse);
                 // alert("record inserted into database successfully");     
                 // clearAll();
               }
              else if(command=="deleted")
               { 
             	 delchecking(baseResponse);
                 // alert("record deleted successfully");   
                 // clearAll();
               }
                 else if(command=="loadAgreeNo"){
                 	loadAgreeNoloading(baseResponse);          	
                 }
                 else if(command=="loadAgreeNoDetail"){
                 	loadAgreeNoDetailloading(baseResponse);          	
                 }
                else if(command=="major")
                  {
                       majortypechecking(baseResponse);
                  }
                else if(command=="minor")
                  {
                      minortypechecking(baseResponse);
                  }
                else if(command=="nature")
                {
                    naturechecking(baseResponse);
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
                else if(command=="checkCode1")
                {
                    loadcheckCode1(baseResponse);
                }
                else if (command == "getOffice") {
    				getOffice1(baseResponse);
    			}
                else if (command == "getBillMajorType") {

           		firstLoad(baseResponse);
           	} 
                else if (command == "bill_sub_office")
               {
                	bill_sub_office_Details(baseResponse);
               }
                
              
          }
      }
}  
function loadcheckCode1(baseResponse)
{
    var flag=baseResponse.getElementsByTagName("flag")[0].firstChild.nodeValue;
    
    if(flag=="success")
    {
         var hid=baseResponse.getElementsByTagName("hid")[0].firstChild.nodeValue;
     //    document.getElementById("txtAcc_HeadCode").value=hid;
         var hdesc=baseResponse.getElementsByTagName("hdesc")[0].firstChild.nodeValue;
       
        // document.getElementById("txtAcc_HeadCode").value=hid;
         document.getElementById("txtAcc_HeadDesc").value=hdesc;
  
    }
     else if(flag=="failure")
     {
         alert("Account Head Code '"+document.getElementById("txtAcc_HeadCode").value+"' doesn't Exist");
         document.getElementById("txtAcc_HeadCode").value="";
         document.getElementById("txtAcc_HeadCode").focus();
     }

}
function loadAgreeNoDetailloading(baseResponse)
{
        
        
         var agreeno =  baseResponse.getElementsByTagName("agreement_no")[0].firstChild.nodeValue;
         var agreedate =  baseResponse.getElementsByTagName("AGREEMENT_DATE")[0].firstChild.nodeValue;
         var workno =  baseResponse.getElementsByTagName("work_or_supply_order_no")[0].firstChild.nodeValue;
         var firmno =  baseResponse.getElementsByTagName("sub_ledger_code")[0].firstChild.nodeValue;
         var firmdesc =  baseResponse.getElementsByTagName("firmname")[0].firstChild.nodeValue;
         var accheaddesc =  baseResponse.getElementsByTagName("accheaddesc")[0].firstChild.nodeValue;
         var debit_achead =  baseResponse.getElementsByTagName("debit_achead")[0].firstChild.nodeValue;
         document.forms[0].agreementdate.value=agreedate;
         document.forms[0].workorderno.value=workno;
         document.forms[0].firmname.value=firmdesc;
         document.forms[0].txtAcc_HeadCode.value=debit_achead;
         document.forms[0].txtAcc_HeadDesc.value=accheaddesc;
         financialDate();
        /* for(var i=0; i<agreecode.length; i++)
             {
                 var opt = document.createElement('option');
                 opt.value = agreecode[i].firstChild.nodeValue;
                 opt.innerHTML = agreecode[i].firstChild.nodeValue; //instead of using textnode ,we use innerhtml
                 agreecombo.appendChild(opt);
             }*/
}

function loadAgreeNoloading(baseResponse)
{
         var agreecombo = document.forms[0].agreementno;
         agreecombo.length=0;
         var agreecode = baseResponse.getElementsByTagName("agreement_no"); 
          
         for(var i=0; i<agreecode.length; i++)
             {
                 var opt = document.createElement('option');
                 opt.value = agreecode[i].firstChild.nodeValue;
                 opt.innerHTML = agreecode[i].firstChild.nodeValue; //instead of using textnode ,we use innerhtml
                 agreecombo.appendChild(opt);
             }
}
//dynamically loading Billmajortype combo
function majortypechecking(baseResponse)
    {
             var billcombo = document.getElementById("billmajortype");
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
function naturechecking(baseResponse)
{
	
	 var nature = document.forms[0].billnature;
    document.forms[0].billnature.length=0;
    var minorcode = baseResponse.getElementsByTagName("minorcode");  
    var minordesc = baseResponse.getElementsByTagName("minordesc"); 
    for(var i=0; i<minorcode.length; i++)
          {
       	  if(minorcode.length==1)
       	  {
       		  var opt1 = document.createElement('option');
                 opt1.value = 0;
                 opt1.innerHTML ="select"; 
                 nature.appendChild(opt1);
       		  
       	  }
       		  
       	     var opt1 = document.createElement('option');
                opt1.value = minorcode[i].firstChild.nodeValue;
                opt1.innerHTML = minordesc[i].firstChild.nodeValue; 
                nature.appendChild(opt1);
            
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
function doParentEmp(billno1,billdate1,major1,minor1,sub1,unitid1,offid1)    
    {   
       // clearAll();
	//alert("billno,billdate,major,minor,sub  "+billno1+" "+billdate1+"  "+major1+"  "+minor1+"  "+sub1);
	    document.getElementById("billmajortype").options[document.getElementById("billmajortype").selectedIndex].text=major1;
       document.getElementById("billminortype").options[document.getElementById("billminortype").selectedIndex].text=minor1;
        document.getElementById("billsubtype").options[document.getElementById("billsubtype").selectedIndex].text=sub1;
        document.forms[0].billno.value=billno1;
        document.forms[0].billdate.value=billdate1;
        var url="../../../../../FirmBills_Serv?command=retrieve&billno="+billno1+"&billdate="+billdate1+"&unitid="+unitid1+"&officeid="+offid1;
      //  alert(url);
        var xmlrequest= AjaxFunction();
        xmlrequest.open("GET",url,true); 
        xmlrequest.onreadystatechange=function()
            {
              manipulate(xmlrequest);
            } ;  
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
           document.getElementById("billmajortype").options[document.getElementById("billmajortype").selectedIndex].text="select";
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
            document.forms[0].agreementno.length=0;
         //   document.getElementById("agreementno").options[document.getElementById("agreementno").selectedIndex].text="select";
            document.forms[0].agreementdate.value="";
            document.forms[0].workorderno.value="";
            document.forms[0].firmname.value="";
            document.forms[0].remarks.value=" ";    
            document.forms[0].onadd.disabled=false;  
            document.forms[0].onedit.disabled=true;
            document.forms[0].ondelete.disabled=true; 
    }
 //grid values are stuff it to the field 
function  retrievechecking(baseResponse) 
        {  
	//alert("retrieve ");
	
          unitidd=baseResponse.getElementsByTagName("ACCOUNTING_UNIT_ID");
    	  offidd=baseResponse.getElementsByTagName("ACCOUNTING_FOR_OFFICE_ID");
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
          headac=  baseResponse.getElementsByTagName("headac")[0].firstChild.nodeValue;
          agreeno =  baseResponse.getElementsByTagName("agreeno")[0].firstChild.nodeValue;
          agreedate =  baseResponse.getElementsByTagName("agreedate");
          remarks =  baseResponse.getElementsByTagName("remarks");
          firmname =  baseResponse.getElementsByTagName("firmname");
          
          //alert(agreeno);
          document.forms[0].cmbAcc_UnitCode.value=unitidd[0].firstChild.nodeValue; 
          document.forms[0].cmbOffice_code.value=offidd[0].firstChild.nodeValue; 
          document.forms[0].paymenttype.value=paymenttype[0].firstChild.nodeValue; 
          min_type=minor[0].firstChild.nodeValue;
          sub_type=sub[0].firstChild.nodeValue;
          document.forms[0].billmajortype.value=major[0].firstChild.nodeValue;
          document.forms[0].billminortype.options[document.forms[0].billminortype.selectedIndex].value=minor[0].firstChild.nodeValue;
          document.forms[0].billsubtype.options[document.forms[0].billsubtype.selectedIndex].value=sub[0].firstChild.nodeValue;
            
	      	document.forms[0].invoiceNo.value=invoiceno[0].firstChild.nodeValue;
	        document.forms[0].billdate.value=billdate[0].firstChild.nodeValue;
	        document.forms[0].invoicedate.value=invoicedate[0].firstChild.nodeValue;
	        document.forms[0].invoicereceivedondate.value=invoicereceiveddate[0].firstChild.nodeValue;
	        document.forms[0].invoiceamount.value=invoiceamount[0].firstChild.nodeValue;
	        document.forms[0].Particularsinvoice.value=particulars[0].firstChild.nodeValue;
	        document.forms[0].mbookdate.value=bookdate[0].firstChild.nodeValue;
	        document.forms[0].mbookno.value=bookno[0].firstChild.nodeValue;
	        doFunction11("checkCode1",headac);
	        document.forms[0].mbookpageno.value=bookpageno[0].firstChild.nodeValue;
	        document.forms[0].initiatingsection.value=isection[0].firstChild.nodeValue;
	        document.forms[0].txtAcc_HeadCode.value=headac;
	     
	       // doFunction('checkCode',headac[0].firstChild.nodeValue);
	        financialDate();
	        //document.forms[0].agreementno.value=agreeno[0].firstChild.nodeValue;
	        document.forms[0].agreementno.options[document.forms[0].agreementno.selectedIndex].value=agreeno;
	        document.forms[0].agreementno.options[document.forms[0].agreementno.selectedIndex].text=agreeno;
	        //document.getElementById("agreementno").options[document.getElementById("agreementno").selectedIndex].value=agreeno[0].firstChild.nodeValue;
	       // document.getElementById("agreementno").options[document.getElementById("agreementno").selectedIndex].text=agreeno[0].firstChild.nodeValue;
	       // document.forms[0].agreementno.text=agreeno[0].firstChild.nodeValue;
	        
            
            document.forms[0].agreementdate.value=agreedate[0].firstChild.nodeValue;
            document.forms[0].remarks.value=remarks[0].firstChild.nodeValue;
            document.forms[0].firmname.value=firmname[0].firstChild.nodeValue;
            
            document.forms[0].onadd.disabled=true;  
            document.forms[0].onedit.disabled=false;
            document.forms[0].ondelete.disabled=false; 

        }

function addchecking(baseResponse)
    {
      var flag=baseResponse.getElementsByTagName("flag")[0].firstChild.nodeValue;
       if(flag=="success")
           {   
               alert("Record Inserted Successfully.");
               clearAll();
           }
       else
           {
               alert("Failed to insert values");
           }                                  
    }
function delchecking(baseResponse)
{
  var flag=baseResponse.getElementsByTagName("flag")[0].firstChild.nodeValue;
   if(flag=="success")
       {   
           alert("Record Deleted Successfully.");
           clearAll();
       }
   else
       {
           alert("Failed to delete values");
       }                                  
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
                return false ;
        }
     }

   function callemp(path)
   {
   	var txtEmpID_mas = document.getElementById("txtEmpID_mas2").value;
   	alert("txtEmpID_mas====>"+txtEmpID_mas);
   	var cmbOffice_code = document.getElementById("cmbOffice_code").value;
   	
   	
   	
   		var url = path+ "/Bills_Token_Register_with_SP?command=getempname_off&txtEmpID_mas="+ txtEmpID_mas+"&cmbOffice_code="+cmbOffice_code+"&cmbMas_SL_Code="+cmbMas_SL_Code;
   		// alert(url);
   		var req = getTransport();
   		req.open("POST", url, true);
   		req.onreadystatechange = function() {
   			manipulate1(req);
   		};

   		req.send(null);

   }
   function manipulate1(req) {

		if (req.readyState == 4) {
			if (req.status == 200) {

				var baseResponse = req.responseXML
						.getElementsByTagName("response")[0];

				var tagCommand = baseResponse.getElementsByTagName("command")[0];

				var command = tagCommand.firstChild.nodeValue;
				
				 if (command == "getempname_off") {
					 //alert("manipulate");
					getempname_re(baseResponse); }
				
				}
		}
	}
   function getempname_re(baseResponse)
   {
   	var flag = baseResponse.getElementsByTagName("flag")[0].firstChild.nodeValue;
   	if (flag == "success") {
   	var empname = baseResponse.getElementsByTagName("empname")[0].firstChild.nodeValue;
   	
   	document.getElementById("cmbMas_SL_Code2").length = 0;
   	var len1 = baseResponse.getElementsByTagName("empname").length;
   	for ( var i = 0; i < len1; i++) {
   		var empid = baseResponse.getElementsByTagName("empid")[i].firstChild.nodeValue;
   		var empname = baseResponse.getElementsByTagName("empname")[i].firstChild.nodeValue;
   		var se = document.getElementById("cmbMas_SL_Code2");
   		var op = document.createElement("OPTION");
   		op.value = empid;
   		var txt = document.createTextNode(empname);
   		op.appendChild(txt);
   		se.appendChild(op);
   	
   	}
   	}
   	else
   	{
   		alert("Enter Relevant EmployeeId For This Office");
   		document.getElementById("cmbMas_SL_Code2").length = 0;
   		document.getElementById("txtEmpID_mas2").value="";
   	}
   }
   
   function getOffice(path) {
		var txtEmpID_mas = document.getElementById("txtEmpID_mas1").value;
		var url = path
				+ "/Bills_Token_Register_without_SP_GPF?command=getOffice&txtEmpID_mas="
				+ txtEmpID_mas;
		// alert(url);
		var xmlrequest = AjaxFunction();
		xmlrequest.open("POST", url, true);
		xmlrequest.onreadystatechange = function() {
			manipulate(xmlrequest);
		}

		xmlrequest.send(null);
	}
   function getOffice1(baseResponse) {
		document.getElementById("cboOffice").length = 0;
		document.getElementById("cmbMas_SL_Code1").length=0;
		var flag = baseResponse.getElementsByTagName("flag")[0].firstChild.nodeValue;
		if (flag == "success") {

			var len6 = baseResponse.getElementsByTagName("OfficeID").length;
			for ( var i = 0; i < len6; i++)
			{
				var OfficeID = baseResponse.getElementsByTagName("OfficeID")[i].firstChild.nodeValue;
				var OfficeName = baseResponse.getElementsByTagName("OfficeName")[i].firstChild.nodeValue;
				var se = document.getElementById("cboOffice");
				var op = document.createElement("OPTION");
				op.value = OfficeID;
				var txt = document.createTextNode(OfficeName);
				op.appendChild(txt);
				se.appendChild(op);
				document.billform.cboOffice.value = OfficeID;
			}
			
			var lenths = baseResponse.getElementsByTagName("EMPLOYEE_ID").length;
			for ( var i = 0; i < lenths; i++)
			{
				var empid = baseResponse.getElementsByTagName("EMPLOYEE_ID")[i].firstChild.nodeValue;
				var empname = baseResponse.getElementsByTagName("EMPLOYEE_NAME")[i].firstChild.nodeValue;
				var se = document.getElementById("cmbMas_SL_Code1");
				var op = document.createElement("OPTION");
				op.value = empid;
				var txt = document.createTextNode(empname);
				op.appendChild(txt);
				se.appendChild(op);
				document.billform.cmbMas_SL_Code1.value = empid;
			}
			
		} else {
			alert("Fail to Load Bill Major Type");
		}
	}
   
   function initialLoad(path) {
		//alert(path);

		var url = path
				+ "/Bills_Token_Register_without_SP_GPF?command=getBillMajorType";
		// alert(url);
		var xmlrequest = AjaxFunction();
		xmlrequest.open("POST", url, true);
		xmlrequest.onreadystatechange = function() {
			manipulate(xmlrequest);
		};

		xmlrequest.send(null);

	}
   
   
   
   function firstLoad(baseResponse) {
		 //alert("RKsbg");
		
		
		var len1 = baseResponse.getElementsByTagName("empName").length;
		for ( var i = 0; i < len1; i++) {
			var empName = baseResponse.getElementsByTagName("empName")[i].firstChild.nodeValue;
			var empid = baseResponse.getElementsByTagName("empid")[0].firstChild.nodeValue;
			// alert(empName);
			var se = document.getElementById("cmbMas_SL_Code1");
			var op = document.createElement("OPTION");
			op.value = empid;
			var empvalue=empName+"("+empid+")";
			var txt = document.createTextNode(empvalue);
			op.appendChild(txt);
			se.appendChild(op);

		}
		document.billform.txtEmpID_mas1.value = empid;

		

		var flag = baseResponse.getElementsByTagName("flag")[0].firstChild.nodeValue;
		if (flag == "success") {

			var len4 = baseResponse.getElementsByTagName("billMajorTypeCode").length;
			for ( var i = 0; i < len4; i++) {
				var billMajorTypeCode = baseResponse
						.getElementsByTagName("billMajorTypeCode")[i].firstChild.nodeValue;
				var billMajorTypeDesc = baseResponse
						.getElementsByTagName("billMajorTypeDesc")[i].firstChild.nodeValue;

				var se = document.getElementById("cboBillMajorType");
				var op = document.createElement("OPTION");
				op.value = billMajorTypeCode;
				var txt = document.createTextNode(billMajorTypeDesc);
				op.appendChild(txt);
				se.appendChild(op);
			}

			var len6 = baseResponse.getElementsByTagName("OfficeID").length;
			for ( var i = 0; i < len6; i++) {
				var OfficeID = baseResponse.getElementsByTagName("OfficeID")[i].firstChild.nodeValue;
				var OfficeName = baseResponse.getElementsByTagName("OfficeName")[i].firstChild.nodeValue;
				var se = document.getElementById("cboOffice");
				var op = document.createElement("OPTION");
				op.value = OfficeID;
				var txt = document.createTextNode(OfficeName);
				op.appendChild(txt);
				se.appendChild(op);
				document.billform.cboOffice.value = OfficeID;
			}
			
			
			document.getElementById("txtEmpID_mas1").value= baseResponse.getElementsByTagName("empid")[0].firstChild.nodeValue;
			for ( var i = 0; i < len6; i++) {
				var empid = baseResponse.getElementsByTagName("empid")[i].firstChild.nodeValue;
				var empname = baseResponse.getElementsByTagName("empname")[i].firstChild.nodeValue;
				var se = document.getElementById("cmbMas_SL_Code1");
				var op = document.createElement("OPTION");
				op.value = empid;
				var txt = document.createTextNode(empname);
				op.appendChild(txt);
				se.appendChild(op);
			//	document.frm_BillTokenRegisterEntry_WithoutProceeding_GPF.cboOffice.value = OfficeID;
			}

		} else {
			alert("Fail to Load Bill Major Type");
		}
	}
 function bill_sub_office_Details(baseResponse)
 {
		 
//alert("bill_sub_office_Details");
document.getElementById("sub_office").length=0;
//alert(document.getElementById("sub_office").length);
		var len6 = baseResponse.getElementsByTagName("SUBDIVISION_OFFICE_ID").length;
		// alert("Length--->"+len6)
		for ( var i = 0; i < len6; i++)
		{
			var OfficeID = baseResponse.getElementsByTagName("SUBDIVISION_OFFICE_ID")[i].firstChild.nodeValue;
			var OfficeName = baseResponse.getElementsByTagName("SUBDIVISION_OFFICE_NAME")[i].firstChild.nodeValue;
			var se = document.getElementById("sub_office");
			var op = document.createElement("OPTION");
			op.value = OfficeID;
			var txt = document.createTextNode(OfficeName);
			op.appendChild(txt);
			se.appendChild(op);
			document.billform.sub_office.value = OfficeID;
		}
		
	 
 }
   
   
   function bill_sub_office()
   {
	   
	   	   var office_id=document.getElementById("cmbOffice_code").value;
	 //  alert("Office_id===>"+office_id);
       var url="../../../../../OandM_Bills_Serv?command=bill_sub_office&Officeid="+office_id;
       var xmlrequest= AjaxFunction();
       xmlrequest.open("GET",url,true);              
       xmlrequest.onreadystatechange=function()
       {
           manipulate(xmlrequest);
       }
       xmlrequest.send(null);       
	   
   }
   
   function OandMListSub(command,param)
   {
   	if(command=="loadSchdebitcode")
       {
   //	alert("Welcome to ListSub function ")
   		var uni=document.getElementById("cmbAcc_UnitCode").value;
   	var offi=document.getElementById("cmbOffice_code").value;
   	var schNo=document.getElementById("sch_code").value;
   	var txtCrea_date=document.getElementById("txtCrea_date").value;
   	var url= "../../../../../OandM_Bills_Serv?command=loadSchdebitcode&schno="+schNo+"&cmbAcc_UnitCode="+uni+"&cmbOffice_code="+offi+"&txtCrea_date="+txtCrea_date;
   		

   	  var xmlrequest=getTransport();
   		    xmlrequest.open("GET",url,true); 
   		    xmlrequest.onreadystatechange=function()
   		    
   		    {
   		    	
   		    	 if(xmlrequest.readyState==4)
   { 
   	
       if(xmlrequest.status==200)
       {  

      	 
       	   var baseResponse=xmlrequest.responseXML.getElementsByTagName("response")[0];
       	   
              var tagcommand=baseResponse.getElementsByTagName("command")[0];
              var Command=tagcommand.firstChild.nodeValue;
              var flag=baseResponse.getElementsByTagName("flag")[0].firstChild.nodeValue;
              
              var tbody=document.getElementById("grid_body2");
       	   	  try{tbody.innerHTML="";}
       	      catch(e) {tbody.innerText="";}
       	      
       	      var tbody=document.getElementById("grid_body");
       	      try{tbody.innerHTML="";}
       	      catch(e) {tbody.innerText="";}
       	      
               if(flag=="success")
               	{
    	   
            	   var DR_CR = baseResponse.getElementsByTagName("DR_CR")[0].firstChild.nodeValue;
            	   alert("DR_CR====>"+DR_CR);
            	   var leng = baseResponse.getElementsByTagName("ACCOUNT_HEAD_CODE").length;
            	   alert("leng===>"+leng);
            	   
            	   if(DR_CR=="DR")
            		   {
            	   
            	   
            	   
            	   
            	   
            	   
            	   lll=1;
            	   seq=0;
            	   tbody=document.getElementById("grid_body2");
            	   var item = new Array();

            	   for(var k=0;k<leng;k++)
            	   {

            		   item[1]=baseResponse.getElementsByTagName("ACCOUNT_HEAD_CODE")[k].firstChild.nodeValue;
          				
          				item[2] =baseResponse.getElementsByTagName("ACCOUNT_HEAD_DESC")[k].firstChild.nodeValue;
          				
          				item[3] =baseResponse.getElementsByTagName("cmbAcc_UnitCode")[0].firstChild.nodeValue;
          			
          				item[4] =baseResponse.getElementsByTagName("cmbOffice_code")[0].firstChild.nodeValue;
          			
          				item[5] =baseResponse.getElementsByTagName("SUB_LEDGER_TYPE")[k].firstChild.nodeValue;
          			
          				item[6] =baseResponse.getElementsByTagName("sub_ledger_name")[k].firstChild.nodeValue;
          				
          				item[7] =baseResponse.getElementsByTagName("SUB_LEDGER_TYPE_CODE")[k].firstChild.nodeValue;
          			
          				item[8] =baseResponse.getElementsByTagName("sub_ledger_type_desc")[k].firstChild.nodeValue;
          			
          				
          				document.getElementById("sch_desc").value=item[8];
          	            
           			var	mycurrent_row=document.createElement("TR");
          				mycurrent_row.id=item[1];                     
          				var cell1 = document.createElement("TD");
          				var dateofentry1=document.createElement("input");
          				dateofentry1.type="hidden";
          				dateofentry1.name="seq";
          				dateofentry1.id=seq;
          				dateofentry1.value=seq;
          				var dateofentry = document.createTextNode(seq+1);
          				dateofentry.size=7;
          				
          				cell1.appendChild(dateofentry);
          				cell1.appendChild(dateofentry1);
          				mycurrent_row.appendChild(cell1);

          				var cell2 = document.createElement("TD");
          				var assetcode1=document.createElement("input");
          				assetcode1.type="hidden";
          				assetcode1.name="H_code";
          				assetcode1.id="H_code";
          				assetcode1.value=item[1];
          				var assetcode = document.createTextNode(item[1]+'-'+item[2]);
          				assetcode.size=7;
          				cell2.appendChild(assetcode);
          				cell2.appendChild(assetcode1);
          				mycurrent_row.appendChild(cell2);
          				
          				
          				var cell2 = document.createElement("TD");
          				cell2.setAttribute('style', 'display:none;');
          				var assetcode1=document.createElement("input");
          				assetcode1.type="hidden";
          				assetcode1.name="CR_DR_type";
          				assetcode1.id="CR_DR_type";
          				assetcode1.value="DR";
          				cell2.appendChild(assetcode1);
          				mycurrent_row.appendChild(cell2);
          			
          				var cell2 = document.createElement("TD");
          				var sel=document.createElement("select");
          				sel.id="SLtype"+seq;
          				sel.name="SLtype"+seq;
          				var option=document.createElement("OPTION");
          			
          				option.value=item[5];
            	  		option.text=item[6];
            	  		sel.appendChild(option); 	
          				
          				
          				
          				cell2.appendChild(sel);
          				mycurrent_row.appendChild(cell2);
          				
          				
          				
          				var cell2 = document.createElement("TD");
          				var sel=document.createElement("select");
          				sel.id="SLtypecode"+seq;
          				sel.name="SLtypecode"+seq;
          				sel.setAttribute('style','width:400px');

          				var option=document.createElement("OPTION");
          				option.value=item[7];
          				option.text=item[8];
          				sel.appendChild(option);
          			
          				
          				cell2.appendChild(sel);
          				
          				mycurrent_row.appendChild(cell2);
          				
          		
          				
          				var cell2 = document.createElement("TD");
          				var assetcode1=document.createElement("input");
          				assetcode1.type="text";
          				assetcode1.name="sl_amt";
          				assetcode1.id="sl_amt"+lll;
          				assetcode.size=7;
          				cell2.appendChild(assetcode1);
          				cell2.align="right";
          				mycurrent_row.appendChild(cell2);
          				var cell2 = document.createElement("TD");
          				var assetcode1=document.createElement("input");
          				assetcode1.type="text";
          				assetcode1.name="particular";
          				assetcode1.id="particular";
          				
          				var assetcode = document.createTextNode("");
          				assetcode.size=7;
          				cell2.appendChild(assetcode);
          				cell2.appendChild(assetcode1);
          				cell2.align="right";
          				mycurrent_row.appendChild(cell2);
          				
          				
          				tbody.appendChild(mycurrent_row);
          				lll++;
          				seq++;
	
		
		}   
            		   }
            	   else
            		   {
            		   
//                       var leng = baseResponse.getElementsByTagName("ACCOUNT_HEAD_CODE").length;
                     //  alert("leng=cr==>"+leng);
               	       
               			lll=1;
               			seq=0;
               		    tbody=document.getElementById("grid_body");
               			var item = new Array();

               			for(var k=0;k<leng;k++)
               			{

               				item[1]=baseResponse.getElementsByTagName("ACCOUNT_HEAD_CODE")[k].firstChild.nodeValue;
               				
               				item[2] =baseResponse.getElementsByTagName("ACCOUNT_HEAD_DESC")[k].firstChild.nodeValue;
               				
               				item[3] =baseResponse.getElementsByTagName("cmbAcc_UnitCode")[0].firstChild.nodeValue;
               			
               				item[4] =baseResponse.getElementsByTagName("cmbOffice_code")[0].firstChild.nodeValue;
               			
               				item[5] =baseResponse.getElementsByTagName("SUB_LEDGER_TYPE")[k].firstChild.nodeValue;
               			
               				item[6] =baseResponse.getElementsByTagName("sub_ledger_name")[k].firstChild.nodeValue;
               				
               				item[7] =baseResponse.getElementsByTagName("SUB_LEDGER_TYPE_CODE")[k].firstChild.nodeValue;
               			
               				item[8] =baseResponse.getElementsByTagName("sub_ledger_type_desc")[k].firstChild.nodeValue;
               			
               				
               				document.getElementById("sch_desc").value=item[8];
               	            
                			var	mycurrent_row=document.createElement("TR");
               				mycurrent_row.id=item[1];                     
               				var cell1 = document.createElement("TD");
               				var dateofentry1=document.createElement("input");
               				dateofentry1.type="hidden";
               				dateofentry1.name="seq";
               				dateofentry1.id=seq;
               				dateofentry1.value=seq;
               				var dateofentry = document.createTextNode(seq+1);
               				dateofentry.size=7;
               				
               				cell1.appendChild(dateofentry);
               				cell1.appendChild(dateofentry1);
               				mycurrent_row.appendChild(cell1);

               				var cell2 = document.createElement("TD");
               				var assetcode1=document.createElement("input");
               				assetcode1.type="hidden";
               				assetcode1.name="H_code";
               				assetcode1.id="H_code";
               				assetcode1.value=item[1];
               				var assetcode = document.createTextNode(item[1]+'-'+item[2]);
               				assetcode.size=7;
               				cell2.appendChild(assetcode);
               				cell2.appendChild(assetcode1);
               				mycurrent_row.appendChild(cell2);
               				
               				
               				var cell2 = document.createElement("TD");
               				cell2.setAttribute('style', 'display:none;');
               				var assetcode1=document.createElement("input");
               				assetcode1.type="hidden";
               				assetcode1.name="CR_DR_type";
               				assetcode1.id="CR_DR_type";
               				assetcode1.value="CR";
               				cell2.appendChild(assetcode1);
               				mycurrent_row.appendChild(cell2);
               			
               				var cell2 = document.createElement("TD");
               				var sel=document.createElement("select");
               				sel.id="SLtype"+seq;
               				sel.name="SLtype"+seq;
               				var option=document.createElement("OPTION");
               			
               				option.value=item[5];
                 	  			option.text=item[6];
                 	  					sel.appendChild(option); 	
               				
               				
               				
               				cell2.appendChild(sel);
               				mycurrent_row.appendChild(cell2);
               				
               				
               				
               				var cell2 = document.createElement("TD");
               				var sel=document.createElement("select");
               				sel.id="SLtypecode"+seq;
               				sel.name="SLtypecode"+seq;
               				sel.setAttribute('style','width:400px');

               				var option=document.createElement("OPTION");
               				option.value=item[7];
               				option.text=item[8];
               				sel.appendChild(option);
               			
               				
               				cell2.appendChild(sel);
               				
               				mycurrent_row.appendChild(cell2);
               				
               		
               				
               				var cell2 = document.createElement("TD");
               				var assetcode1=document.createElement("input");
               				assetcode1.type="text";
               				assetcode1.name="sl_amt";
               				assetcode1.id="sl_amt"+lll;
               				assetcode.size=7;
               				cell2.appendChild(assetcode1);
               				cell2.align="right";
               				mycurrent_row.appendChild(cell2);
               				var cell2 = document.createElement("TD");
               				var assetcode1=document.createElement("input");
               				assetcode1.type="text";
               				assetcode1.name="particular";
               				assetcode1.id="particular";
               				
               				var assetcode = document.createTextNode("");
               				assetcode.size=7;
               				cell2.appendChild(assetcode);
               				cell2.appendChild(assetcode1);
               				cell2.align="right";
               				mycurrent_row.appendChild(cell2);
               				
               				
               				tbody.appendChild(mycurrent_row);
               				lll++;
               				 seq++;
               		
                   		
                   		}  
            		   }
               	}
               else if(flag=="failure1")
               	{
               	alert("Account Head Code doesn't Exist");
               	}
               else
               	{
               	alert("No Data found");
               	}
               	

   	
   		
       }
   	
   }
   		    };
   		   
   		    xmlrequest.send(null);
   	
       }

   }
  
   function clear_Combo(combo)
   {
           //alert(combo.id)
           var cmbSL_Code=document.getElementById(combo.id);   
           cmbSL_Code.innerHTML="";
          var option=document.createElement("OPTION");
           option.text="--Select Code--";
           option.value="";
           try
           {
               cmbSL_Code.add(option);
           }catch(errorObject)
           {
               cmbSL_Code.add(option,null);
           } 
   }
   
   function loadscpage()
   {

   	var cmbAcc_UnitCode=document.getElementById("cmbAcc_UnitCode").value;
   	 var cmbOffice_code=document.getElementById("cmbOffice_code").value;
   	 winemp= window.open("../../../../../org/FAS/FAS1/InvoiceDetails/jsps/new_contract_List_OandMBills.jsp?cmbAcc_UnitCode="+cmbAcc_UnitCode+"&cmbOffice_code="+cmbOffice_code,"Scheme List","status=1,height=1000,width=1000,resizable=YES, scrollbars=yes"); 
   	 winemp.moveTo(250,250);  
   	 winemp.focus();
   }
   function loadsch(un,off){
		
		 var cmbAcc_UnitCode=un;
		 var cmbOffice_code=off;
		 var url= "../../../../../OandM_Bills_Serv?command=loadSchEME&cmbAcc_UnitCode="+cmbAcc_UnitCode+"&cmbOffice_code="+cmbOffice_code;
			  
			    var xmlrequest=getTransport();
	   		    xmlrequest.open("GET",url,true); 
	   		    xmlrequest.onreadystatechange=function()
	   		    {
		    	LoadBankAccountNumberRes(xmlrequest);
		    };
		   
		    xmlrequest.send(null);	
   }
   function  LoadBankAccountNumberRes(xmlrequest)
   {  

       if(xmlrequest.readyState==4)
       { 
       	
           if(xmlrequest.status==200)
           {  
           	// subledgercode1(11);
           		

            var baseResponse=xmlrequest.responseXML.getElementsByTagName("response")[0];
     
               var tagcommand=baseResponse.getElementsByTagName("command")[0];
               var Command=tagcommand.firstChild.nodeValue;
      
               
           
            var flag = baseResponse.getElementsByTagName("flag")[0].firstChild.nodeValue;
         // alert(flag);
            if(flag=="success"){
           	 //alert("idnames"+idnames);
            if(Command=="loadAccDesc"){
           	   var tbody=document.getElementById("grid_body2");
           	 var leng = baseResponse.getElementsByTagName("ACCOUNT_HEAD_CODE").length;
            	
                //alert("length"+leng);
      				lll=1;
      				seq=1;
      				var item = new Array();

      				for(var k=0;k<leng;k++)
      				{

      					item[1]=baseResponse.getElementsByTagName("ACCOUNT_HEAD_CODE")[k].firstChild.nodeValue;
      					item[2] =baseResponse.getElementsByTagName("ACCOUNT_HEAD_DESC")[k].firstChild.nodeValue;
      					
      				var	mycurrent_row=document.createElement("TR");
      					mycurrent_row.id=item[1];                     
      					var cell1 = document.createElement("TD");
      					var dateofentry1=document.createElement("input");
      					dateofentry1.type="hidden";
      					dateofentry1.name="seq";
      					dateofentry1.id=seq;
      					dateofentry1.value=seq;
      					var dateofentry = document.createTextNode(seq);
      					dateofentry.size=7;
      					
      					cell1.appendChild(dateofentry);
      					cell1.appendChild(dateofentry1);
      					mycurrent_row.appendChild(cell1);

      					var cell2 = document.createElement("TD");
      					var assetcode1=document.createElement("input");
      					assetcode1.type="hidden";
      					assetcode1.name="ACCOUNT_HEAD_code";
      					assetcode1.id="ACCOUNT_HEAD_code";
      					assetcode1.value=item[1];
      					var assetcode = document.createTextNode(item[2]);
      					assetcode.size=7;
      					cell2.appendChild(assetcode);
      					cell2.appendChild(assetcode1);
      					cell2.align="right";
      					mycurrent_row.appendChild(cell2);
      					
      					var cell2 = document.createElement("TD");
      					var assetcode1=document.createElement("input");
      					assetcode1.type="hidden";
      					assetcode1.name="CR_DR_type1";
      					assetcode1.id="CR_DR_type1";
      					assetcode1.value="DR";
      					var assetcode = document.createTextNode("DR");
      					assetcode.size=7;
      					cell2.appendChild(assetcode);
      					cell2.appendChild(assetcode1);
      					cell2.align="right";
      					mycurrent_row.appendChild(cell2);
      					
      					var cell2 = document.createElement("TD");
      					var assetcode1=document.createElement("input");
      					assetcode1.type="hidden";
      					assetcode1.name="SLtype1";
      					assetcode1.id="SLtype1";
      					assetcode1.value="10";
      					var assetcode = document.createTextNode("Project");
      					assetcode.size=7;
      					cell2.appendChild(assetcode);
      					cell2.appendChild(assetcode1);
      					cell2.align="right";
      					mycurrent_row.appendChild(cell2);
      					
      					
      					var cell2 = document.createElement("TD");
      					var assetcode1=document.createElement("input");
      					assetcode1.type="hidden";
      					assetcode1.name="SL_code1";
      					assetcode1.id="SL_code1";
      					assetcode1.value="";
      					var assetcode = document.createTextNode("");
      					assetcode.size=7;
      					cell2.appendChild(assetcode);
      					cell2.appendChild(assetcode1);
      					cell2.align="right";
      					mycurrent_row.appendChild(cell2);
      					
      					
      					
      					
      					var cell2 = document.createElement("TD");
      				
      					cell2.appendChild(assetcode111);
      					cell2.align="right";
      					mycurrent_row.appendChild(cell2);
      					
      					var cell2 = document.createElement("TD");
      					var assetcode1=document.createElement("input");
      					assetcode1.type="text";
      					assetcode1.name="sl_amt1";
      					assetcode1.id="sl_amt1";
      					assetcode.size=7;
      					cell2.appendChild(assetcode1);
      					cell2.align="right";
      					mycurrent_row.appendChild(cell2);
      					
      					var cell2 = document.createElement("TD");
      					var assetcode1=document.createElement("input");
      					assetcode1.type="text";
      					assetcode1.name="particular1";
      					assetcode1.id="particular1";
      					
      					var assetcode = document.createTextNode("");
      					assetcode.size=7;
      					cell2.appendChild(assetcode);
      					cell2.appendChild(assetcode1);
      					cell2.align="right";
      					mycurrent_row.appendChild(cell2);
      					
      					
      					tbody.appendChild(mycurrent_row);
      					lll++;
      					 seq++;
      			
               		
               		}         

          	} 
            //Joan Changes
            
            else if(Command=="loadSchdebitcode")
          		{
           	 
          	 
           	   var tbody=document.getElementById("grid_body2");
                  try{tbody.innerHTML="";}
              catch(e) {tbody.innerText="";}
          	 var leng = baseResponse.getElementsByTagName("ACCOUNT_HEAD_CODE").length;
           	
               //alert("length"+leng);
     				lll=1;
     				seq=0;
     			   tbody=document.getElementById("grid_body2");
     				var item = new Array();

     				for(var k=0;k<leng;k++)
     				{

     					item[1]=baseResponse.getElementsByTagName("ACCOUNT_HEAD_CODE")[k].firstChild.nodeValue;
     					item[2] =baseResponse.getElementsByTagName("ACCOUNT_HEAD_DESC")[k].firstChild.nodeValue;
     					item[3] =baseResponse.getElementsByTagName("cmbAcc_UnitCode")[0].firstChild.nodeValue;
     					item[4] =baseResponse.getElementsByTagName("cmbOffice_code")[0].firstChild.nodeValue;
     					item[5] =baseResponse.getElementsByTagName("SUB_LEDGER_TYPE")[0].firstChild.nodeValue;
     					item[6] =baseResponse.getElementsByTagName("sub_ledger_name")[0].firstChild.nodeValue;
     					item[7] =baseResponse.getElementsByTagName("SUB_LEDGER_TYPE_CODE")[0].firstChild.nodeValue;
     					item[8] =baseResponse.getElementsByTagName("sub_ledger_type_desc")[0].firstChild.nodeValue;
     					//item[3] =baseResponse.getElementsByTagName("slType")[k].firstChild.nodeValue;
     				var	mycurrent_row=document.createElement("TR");
     					mycurrent_row.id=item[1];                     
     					var cell1 = document.createElement("TD");
     					var dateofentry1=document.createElement("input");
     					dateofentry1.type="hidden";
     					dateofentry1.name="seq";
     					dateofentry1.id=seq;
     					dateofentry1.value=seq;
     					var dateofentry = document.createTextNode(seq+1);
     					dateofentry.size=7;
     					
     					cell1.appendChild(dateofentry);
     					cell1.appendChild(dateofentry1);
     					mycurrent_row.appendChild(cell1);

     					var cell2 = document.createElement("TD");
     					var assetcode1=document.createElement("input");
     					assetcode1.type="hidden";
     					assetcode1.name="ACCOUNT_HEAD_code";
     					assetcode1.id="ACCOUNT_HEAD_code";
     					assetcode1.value=item[1];
     					var assetcode = document.createTextNode(item[1]+'-'+item[2]);
     					assetcode.size=7;
     					cell2.appendChild(assetcode);
     					cell2.appendChild(assetcode1);
     					//..cell2.align="right";
     					mycurrent_row.appendChild(cell2);
     					
     					var cell2 = document.createElement("TD");
     					var assetcode1=document.createElement("input");
     					assetcode1.type="hidden";
     					assetcode1.name="CR_DR_type1";
     					assetcode1.id="CR_DR_type1";
     					assetcode1.value="DR";
     					var assetcode = document.createTextNode("DR");
     					assetcode.size=7;
     					cell2.appendChild(assetcode);
     					cell2.appendChild(assetcode1);
     					//cell2.align="right";
     					mycurrent_row.appendChild(cell2);
     					
     				/*	var cell2 = document.createElement("TD");
     					var assetcode1=document.createElement("input");
     					assetcode1.type="hidden";
     					assetcode1.name="SLtype1";
     					assetcode1.id="SLtype1";
     					assetcode1.value="10";
     					var assetcode = document.createTextNode("Project");
     					assetcode.size=7;
     					cell2.appendChild(assetcode);
     					cell2.appendChild(assetcode1);
     					cell2.align="right";
     					mycurrent_row.appendChild(cell2);*/
     					
     					
     					
     				
     					
     					
     					var cell2 = document.createElement("TD");
     					var sel=document.createElement("select");
     					sel.id="sel_debtype"+seq;
     					sel.name="sel_debtype"+seq;
     					
     				//	sel.setAttribute('onclick','javascript:chgeSlcode(this.value,'+seq+','+item[3]+','+item[4]+')');
     				/*	var option=document.createElement("OPTION");
     					option.value="";
     					option.text="--Select--";
     					sel.appendChild(option);*/
     				/*	var slcodelen =baseResponse.getElementsByTagName("SUB_LEDGER_TYPE_CODE"+k).length;
     				
     					for(var jj=0;jj<slcodelen;jj++)
     						{
     						var option=document.createElement("OPTION");
     	  					option.value=baseResponse.getElementsByTagName("SUB_LEDGER_TYPE_CODE"+k)[jj].firstChild.nodeValue;
     	  			//	alert(baseResponse.getElementsByTagName("sub_ledger_type_desc"+k)[jj].firstChild.nodeValue);
     	  					option.text=baseResponse.getElementsByTagName("sub_ledger_type_desc"+k)[jj].firstChild.nodeValue;
     	  					sel.appendChild(option); 
     						}*/
     					option.value=item[5];
    	  	  			option.text=item[6];
     	  	  					sel.appendChild(option); 	
     					
     					
     					
     					cell2.appendChild(sel);
     					mycurrent_row.appendChild(cell2);
     					
     					
     					
     					var cell2 = document.createElement("TD");
     					var sel=document.createElement("select");
     					sel.id="SLtypecode1"+seq;
     					sel.name="SLtypecode1"+seq;
     					sel.setAttribute('style','width:400px');

     					var option=document.createElement("OPTION");
     					option.value=item[7];
     					option.text=item[8];
     					sel.appendChild(option);
     				
     					
     					cell2.appendChild(sel);
     					
     					mycurrent_row.appendChild(cell2);
     					
     			
     					
     					var cell2 = document.createElement("TD");
     					var assetcode1=document.createElement("input");
     					assetcode1.type="text";
     					assetcode1.name="sl_amt1";
     					assetcode1.id="sl_amt1"+lll;
     					
     					//assetcode1.size=7;
     					assetcode1.maxlength="15";
     					cell2.appendChild(assetcode1);
     					cell2.align="right";
     					mycurrent_row.appendChild(cell2);
     					
     					var cell2 = document.createElement("TD");
     					var assetcode1=document.createElement("input");
     					assetcode1.type="text";
     					assetcode1.name="particular1";
     					assetcode1.id="particular1";
     					
     					var assetcode = document.createTextNode("");
     					assetcode.size=7;
     					cell2.appendChild(assetcode);
     					cell2.appendChild(assetcode1);
     					cell2.align="right";
     					mycurrent_row.appendChild(cell2);
     					
     					
     					tbody.appendChild(mycurrent_row);
     					lll++;
     					 seq++;
     			
              		
              		}         

         	
          		}
            else if (Command=="loadsubDesc"){
          	 var leng = baseResponse.getElementsByTagName("SUB_LEDGER_CODE").length;
          	// alert(baseResponse.getElementsByTagName("sel")[0].firstChild.nodeValue)
           select = document.getElementById("SL_code"+baseResponse.getElementsByTagName("sel")[0].firstChild.nodeValue);
          		for(var k = 0;k<leng ;k++ ){
          	      var option = document.createElement( 'option' );
          	        option.value =baseResponse.getElementsByTagName("SUB_LEDGER_CODE")[k].firstChild.nodeValue; 
          	        	option.text = baseResponse.getElementsByTagName("project_name")[k].firstChild.nodeValue;
          	        select.appendChild( option );
          	       
          			
        	}
          		
          		}  else if (Command=="loadSchEME"){
          			document.getElementById("grid_bodylist").length=0;
          		/*  var tbdy = document.getElementById("grid_bodylist");
          			var t=0;
          			for(t=){
          				
          			}*/
          			var len=baseResponse.getElementsByTagName("sch_sno").length;
          			for(var i=0;i<len;i++)
          				{
          				
          			    var scNo=baseResponse.getElementsByTagName("sch_sno")[i].firstChild.nodeValue; 
       	        	var scName= baseResponse.getElementsByTagName("project_name")[i].firstChild.nodeValue;
          		  var tbdy = document.getElementById("grid_bodylist");
          		var	mycurrent_row=document.createElement("TR");
   				mycurrent_row.id=i+1;  
   				
   				var cell1 = document.createElement("TD");
   				var dateofentry1=document.createElement("input");
   				dateofentry1.type="radio";
   				dateofentry1.name="sccheck";
   				dateofentry1.id="sccheck";
   				
   			
   				cell1.appendChild(dateofentry1);
   			
   				
   				
   				
   				var dateofentry1=document.createElement("input");
   				dateofentry1.type="hidden";
   				dateofentry1.name="scNo"+i;
   				dateofentry1.id="scNo"+i;
   				dateofentry1.value=scNo;
   			
   				cell1.appendChild(dateofentry1);
   				mycurrent_row.appendChild(cell1);
   				
   				
   				var cell1 = document.createElement("TD");
   				var dateofentry2=document.createElement("input");
   				dateofentry2.type="hidden";
   				dateofentry2.name="scName"+i;
   				dateofentry2.id="scName"+i;
   				dateofentry2.value=scName;
   				var dateofentry1 = document.createTextNode(scNo+"  -  "+scName);
   				cell1.appendChild(dateofentry1);
   				cell1.appendChild(dateofentry2);
   				mycurrent_row.appendChild(cell1);
   				
   				tbdy.appendChild(mycurrent_row);
   				
   				
          	/*	 var option = document.createElement( 'option' );
          		 option.value ="";
          		option.text ="--Select--";
          	    select1.appendChild( option );
                 	 var leng = baseResponse.getElementsByTagName("sch_sno").length;
                 //	 alert('len'+leng)
                    select = document.getElementById("Schemename");
                   		for(var k = 0;k<leng ;k++ ){
                   	      var option = document.createElement( 'option' );*/
                   	      /*  option.value =baseResponse.getElementsByTagName("sch_sno")[k].firstChild.nodeValue; 
                   	        	option.text = baseResponse.getElementsByTagName("project_name")[k].firstChild.nodeValue;
                   	        select.appendChild( option );
                   	       */
                   			
                 	}
                   		
                   		        }
          	else if (Command=="loadsubdebitDesc"){
             	 var leng = baseResponse.getElementsByTagName("SUB_LEDGER_CODE").length;
             	// alert(leng);
            for(var k = 0;k<leng ;k++ ){
           		
                
                var select =   document.getElementById("subcode1");
                    var option = document.createElement('option');
                    
                  option.value = baseResponse.getElementsByTagName("SUB_LEDGER_CODE")[k].firstChild.nodeValue ;
                  option.text = baseResponse.getElementsByTagName("project_name")[k].firstChild.nodeValue ;
                    select.appendChild(option);
                   
                      
     	}
            
          	}
          	else if(flag="failure")
          		{
          		
          		}
              
           }
       }
   }


   }
   
   function btnListSub() 
   {
   	var schnum="";
   	var schNO=new Array();
   	var schNAME=new Array();
   	hid_Unit=document.getElementById("hid_Unit").value;
   	hid_Office=document.getElementById("hid_Office").value;
   	sele=document.getElementsByName("sccheck").length;
   	
   	if(sele==1){
   		  schNO[0]=document.getElementById("scNo"+0).value;
            
               schNAME[0]=document.getElementById("scName"+0).value;
   	}
   	 	//var tbody=document.getElementById("grid_bodylist"); 
   	else{
   		for(var i=0;i<sele;i++)
       { 
   		
            if(document.frmLjvlist.sccheck[i].checked==true)
         {
          //vouNO[0]=document.frmPending_Bills_revised.vou_no.value;
           	// scod=i+1;
                       
                    //     var r=document.getElementById(scod);
                      //   alert(r)
                        // var rcells=r.cells;
                   //      r=document.getElementById(document.frmLjvlist.scName[i].value);
                        // alert("cccc "+document.frmLjvlist.scName[i].value)// choose the particular row
                               //var rcells=r[i].cells;
                        // rcells=r.cells;
                     //    schNO[0]=document.getElementById("scNo"+i).value;
           	 schnum=schnum+document.getElementById("scNo"+i).value+",";
                        
                         schNAME[i]=document.getElementById("scName"+i).value;
         }
       }  
       schNO=schnum.substring(0, schnum.length-1);
   }
   	Minimize();
       opener.dofunc(schNO,hid_Unit,hid_Office);
//       window.history.go();
       return true;
//       self.close();
   }
   function dofunc(schNO,hid_Unit,hid_Office)
   {
   	
   document.getElementById("sch_code").value=schNO;
   document.getElementById("cmbAcc_UnitCode").value=hid_Unit;
   document.getElementById("cmbOffice_code").value=hid_Office;

   OandMListSub('loadSchdebitcode','null');

   }
	   
   function Minimize() 
   {
   window.resizeTo(0,0);
   window.screenX = screen.width;
   window.screenY = screen.height;
   opener.window.focus();
   } 
   function checkNull()
   {
	   var unitid=document.forms[0].cmbAcc_UnitCode.value;
       var offid=document.forms[0].cmbOffice_code.value;
       var year=document.forms[0].txtCB_Year.value;
       var month=document.forms[0].txtCB_Month.value;
       var doc_date=document.forms[0].txtCrea_date.value;
       var sch_name=document.forms[0].sch_code.value;
       var sub_office=document.forms[0].sub_office.value;
       var billdate=document.forms[0].billdate.value;
       var recivdate=document.forms[0].recivdate.value;
       var billamount=document.forms[0].billamount.value;
       var majortype=document.forms[0].billmajortype.value;
       var minortype=document.forms[0].billminortype.value;
       var subtype=document.forms[0].billsubtype.value;
       var naturetype=document.forms[0].billnature.value;
       var drawing_off=document.forms[0].cmbMas_SL_Code2.value;
       var sl_type=document.forms[0].cmbMas_SL_type.value;
       var sl_code=document.forms[0].cmbMas_SL_Code.value;
       var pay_type=document.forms[0].paymenttype.value;
       var agree_no=document.forms[0].Agreementno.value;
       var agree_date=document.forms[0].agreementdate.value;
       var workorderno=document.forms[0].workorderno.value;
       var wrkorder_dt=document.forms[0].supp_date.value;
       var bud=document.forms[0].budgetalloted.value;
       var expincurred=document.forms[0].expenditureincurred.value;
       var balanvail=document.forms[0].balanceavailable.value;
       var invoiceNo=document.forms[0].invoiceNo.value;
       var idate=document.forms[0].invoicedate.value;
       var iamount=document.forms[0].invoiceamount.value;
       try
       {
    	   if(unitid=="")
	         {
          	 alert("Please select the Accounting unit id");
               document.forms[0].unitid.focus();
               return false;
	         }
    	   else if(offid=="")
    		   {
    		   alert("Please select the Office id");
    		   document.forms[0].offid.focus();
    		   return false;
    		   }
    	   else if(year=="")
    		   {
    		   alert("Please Enter the cashbook year");
    		   document.forms[0].year.focus();
    		   return false;
    		   }
    	   else if(month=="")
    		   {
    		   alert("Please select the Cashbook month");
    		   document.forms[0].month.focus();
    		   }
    	   else if(doc_date=="")
    		   {
    		   alert("Please select the doc_date");
    		   document.forms[0].doc_date.value;
    		   return false;
    		   }
    	   else if(sch_name=="")
    		   {
    		   alert("Please select the scheme name ");
    		   document.forms[0].sch_name.value;
    		   return false;
    		   }
    	   else if(sub_office=="")
    		   {
    		   alert("Please select Name of the office submitting the bill");
    		   document.forms[0].sub_office.value;
    		   return false;
    		   }
    	   else if(billdate=="")
    		   {
    		   alert("Please select the Date of the Bill Submitted");
    		   document.forms[0].billdate.value;
    		   return false;
    		   }
    	   else if(billamount=="")
    		   {
    		   alert("Please Enter the Bill Amount");
    		   document.forms[0].billamount.value;
    		   return false;
    		   }
    	   else
           {
    	   if(parseInt(balanvail) < parseInt(iamount))
           {
          	alert("Budget not available for this Head, Please get Reappropriation");
          	//return true;
          	return false;
           }
           }
    	   try
    	   {
    		   var v=document.frmJournal_Bill_Create1.sl_amt.length;
    		   var total=0;
    		   for(var i=0;i<v;i++)
    			{
    			   if(document.getElementById("sl_amt"+i).value==""){
    				   sum=0;
    					  total=parseFloat(total)+ parseFloat(sum);
    			   }
    			   else if(document.getElementById("sl_amt"+i).value!="")
    			    {
    			    var sum=document.getElementById("sl_amt"+i).value;
    			   
    			    total=parseFloat(total)+ parseFloat(sum);
    			  
    			    }
    			}
    		   
    	   }
    	   catch(e)
    	   {
    		   alert("Exception in credit part----->"+e);
    	   }
    	   try
    		{

    		var v1=document.frmJournal_Bill_Create1.sl_amt1.length;
    		var total1=0;
    		for(var j=0;j<v1;j++)
    		{
    			var jj=j+1;
    			   var dramt=document.getElementById("sl_amt1"+jj).value;
    			   if(dramt==""){
    			    	  var su=0;
    			    	   
    			   	    total1=parseFloat(total1)+ parseFloat(su);
    			      }
    			   else if(dramt!="")
    			    {
    				   var su=dramt;
    				   
    				    total1=parseFloat(total1)+ parseFloat(su);
    				    
    				    }
    		 }
    		}
    	   catch(e)
    	   {
    		   alert("Exception in debit part---->"+e);
    	   }
    	   try
    	   {
    		   if(parseFloat(total)==parseFloat(total1))
    			{
    	   }
    	   return true;
       }
       catch(e)
       {
    	   alert("Exception in checknull---->"+e);
    	   return false;
       }
       
   }
   