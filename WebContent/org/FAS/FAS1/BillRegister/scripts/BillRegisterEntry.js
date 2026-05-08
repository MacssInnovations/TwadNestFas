var type_val;
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
function callProceeding()
{
	
        var url="../../../../../BillRegisterEntry?command=proceedingno";
        var xmlrequest= AjaxFunction();
        xmlrequest.open("GET",url,true);              
        xmlrequest.onreadystatechange=function()
        {
           manipulate(xmlrequest);
        }
        xmlrequest.send(null);
}
function callProceedingDate(param)
{
        var url="../../../../../BillRegisterEntry?command=proceedingdate&proNo="+param;
        var xmlrequest= AjaxFunction();
        xmlrequest.open("GET",url,true);              
        xmlrequest.onreadystatechange=function()
        {
           manipulate(xmlrequest);
        }
        xmlrequest.send(null);
}
function callAuthority()
{
         var billprocessing=document.forms[0].txtEmpID_mas.value;       
         var xmlrequest= AjaxFunction();
         var url="../../../../../BillRegisterEntry?command=authority&billprocessing1="+billprocessing;
         xmlrequest.open("GET",url,true);              
         xmlrequest.onreadystatechange=function()
         {
            manipulate(xmlrequest);
         }
         xmlrequest.send(null);
}

function budgetAvail()
{
		document.forms[0].budgetavailable.value="";
        var budgetprovision =document.forms[0].budgetprovision.value;
        var budgetspent =document.forms[0].budgetspent.value;
        var amountdeducted =document.forms[0].amountdeducted.value;  
        var c=Number(budgetspent)+Number(amountdeducted);
      
       if(c>budgetprovision)
        {
        	alert("Budget not available for this Head, Please get Reappropriation");
        }
        else
        {
        var budgetAvail=budgetprovision-c;
        document.forms[0].budgetavailable.value=budgetAvail;
        }
}
  
// from cashbook year and month,getting financial year(2010-2011) 
function financialDate()
{
	 var unitid=document.forms[0].cmbAcc_UnitCode.value;
     var offid=document.forms[0].cmbOffice_code.value;
     var headcode=document.forms[0].txtAcc_HeadCode.value;
	 var cashbookyear=document.forms[0].cashbookyear.value;
	 var cashbookmonth=document.forms[0].cashbookmonth.value;
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
     var url="../../../../../BillRegisterEntry?command=budgetDet&unitid1="+unitid+"&offid1="+offid+"&finYear="+finYear+"&headcode="+headcode;
	 xmlrequest.open("GET",url,true);              
     xmlrequest.onreadystatechange=function()
         {
             manipulate(xmlrequest);
         }
     xmlrequest.send(null);
	 
}
 
function clearAll()
{
        document.forms[0].proceedingno.value="";
        document.forms[0].proceedingdate.value="";
        document.forms[0].sanctionedamount.value="";
        document.forms[0].paidto.value="";
        document.forms[0].billregisterno.value="";
        document.forms[0].billregisterdate.value="";
        document.forms[0].office.value="";
        document.forms[0].txtAcc_HeadCode.value="";
        document.forms[0].txtAcc_HeadDesc.value="";
        document.forms[0].cmbMas_SL_type.value="";
        document.forms[0].cmbMas_SL_Code.value="";
        document.forms[0].cmbSL_type.value="";
        document.forms[0].cmbSL_Code.value="";
        document.forms[0].txtOfficeID_trs.value="";
        document.forms[0].txtEmpID_trs.value="";
        document.forms[0].txtOfficeID_mas.value="";
        document.forms[0].txtEmpID_mas.value="";
        document.forms[0].budgetprovision.value="";
        document.forms[0].budgetspent.value="";
        document.forms[0].amountdeducted.value=""; 
        document.forms[0].budgetavailable.value="";
        document.forms[0].remarks.value=""; 
        
        document.forms[0].onadd.disabled=false;  
        document.forms[0].onupdate.disabled=true;
        document.forms[0].ondelete.disabled=true; 
}
function add()
{
            var unitid=document.forms[0].cmbAcc_UnitCode.value;
            var offid=document.forms[0].cmbOffice_code.value;
            var proceedingno=document.forms[0].proceedingno.value;
            var proceedingdate=document.forms[0].proceedingdate.value;
            var cashbookyear=document.forms[0].cashbookyear.value;
            var cashbookmonth=document.forms[0].cashbookmonth.value;
            var sanctionedamount=document.forms[0].sanctionedamount.value;
            var payeecode=document.forms[0].payeecode.value;
            var billregisterdate=document.forms[0].billregisterdate.value;
            var billprocessing=document.forms[0].txtEmpID_mas.value;
            var office=document.forms[0].office.value;
            var headcode=document.forms[0].txtAcc_HeadCode.value;
            var subledgertype=document.forms[0].cmbSL_type.value;
            var subledgercode=document.forms[0].cmbSL_Code.value;
            var budgetprovision=document.forms[0].budgetprovision.value;
            var budgetspent=document.forms[0].budgetspent.value;
            var amountdeducted=document.forms[0].amountdeducted.value;
            var budgetavailable=document.forms[0].budgetavailable.value;
            var remarks=document.forms[0].remarks.value;
            if(billregisterdate=="")
            {
                alert("enter bill Register Date");
                document.forms[0].billregisterdate.focus();
            }
           
            else if(budgetavailable=="")
            {
            	budgetAvail();
            	return false;
            }
            else
            {
                var xmlrequest= AjaxFunction();
                var url="../../../../../BillRegisterEntry?command=add&unitid1="+unitid+"&offid1="+offid+"&proceedingno1="+proceedingno+"&proceedingdate1="+proceedingdate+"&cashbookyear1="+cashbookyear+"&cashbookmonth1="+cashbookmonth+"&sanctionedamount1="+sanctionedamount+"&payeecode1="+payeecode+"&billregisterdate1="+billregisterdate+"&billprocessing1="+billprocessing+"&office1="+office+"&headcode1="+headcode+"&subledgertype1="+subledgertype+"&subledgercode1="+subledgercode+"&budgetprovision1="+budgetprovision+"&budgetspent1="+budgetspent+"&amountdeducted1="+amountdeducted+"&budgetavailable1="+budgetavailable+"&remarks1="+remarks;
                
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
        var proceedingno=document.forms[0].proceedingno.value;
        var proceedingdate=document.forms[0].proceedingdate.value;
        var cashbookyear=document.forms[0].cashbookyear.value;
        var cashbookmonth=document.forms[0].cashbookmonth.value;
        var payeecode=document.forms[0].payeecode.value;
        var billregisterno=document.forms[0].billregisterno.value;
        var billregisterdate=document.forms[0].billregisterdate.value;
        var billprocessing=document.forms[0].txtEmpID_mas.value;
        var headcode=document.forms[0].txtAcc_HeadCode.value;
        var subledgertype=document.forms[0].cmbSL_type.value;
        var subledgercode=document.forms[0].cmbSL_Code.value;
        var budgetprovision=document.forms[0].budgetprovision.value;
        var budgetspent=document.forms[0].budgetspent.value;
        var amountdeducted=document.forms[0].amountdeducted.value;
        var budgetavailable=document.forms[0].budgetavailable.value;
        var remarks=document.forms[0].remarks.value;
        
        if(subledgertype=="")
        {
        	subledgertype=0;
        }
        if(subledgercode=="")
        {
        	subledgercode=0;
        }
        var xmlrequest= AjaxFunction();
        var url="../../../../../BillRegisterEntry?command=updated&proceedingno1="+proceedingno+"&unitid1="+unitid+"&offid1="+offid+"&proceedingdate1="+proceedingdate+"&cashbookyear1="+cashbookyear+"&cashbookmonth1="+cashbookmonth+"&billregisterno1="+billregisterno+"&billregisterdate1="+billregisterdate+"&payeecode1="+payeecode+"&billprocessing1="+billprocessing+"&headcode1="+headcode+"&subledgertype1="+subledgertype+"&subledgercode1="+subledgercode+"&budgetprovision1="+budgetprovision+"&budgetspent1="+budgetspent+"&amountdeducted1="+amountdeducted+"&budgetavailable1="+budgetavailable+"&remarks1="+remarks;
        xmlrequest.open("GET",url,true);              
        xmlrequest.onreadystatechange=function()
            {
                manipulate(xmlrequest);
            }
        xmlrequest.send(null);
      
}
function deleted()
{
        var billregisterno=document.forms[0].billregisterno.value;
        var unitid=document.forms[0].cmbAcc_UnitCode.value;
        var offid=document.forms[0].cmbOffice_code.value;
        var cashbookyear=document.forms[0].cashbookyear.value;
        var cashbookmonth=document.forms[0].cashbookmonth.value;
        var r=confirm("Are U Sure?");
        if(r==true)
            {
                var xmlrequest= AjaxFunction();
                var url="../../../../../BillRegisterEntry?command=deleted&billregisterno1="+billregisterno+"&unitid1="+unitid+"&offid1="+offid+"&cashbookyear1="+cashbookyear+"&cashbookmonth1="+cashbookmonth;
                xmlrequest.open("GET",url,true);              
                xmlrequest.onreadystatechange=function()
                    {
                        manipulate(xmlrequest);
                    }
                xmlrequest.send(null);
            }   
}
function listpopup()
{
        var unit_id=document.getElementById("cmbAcc_UnitCode").value;
        var office_id=document.getElementById("cmbOffice_code").value;
        winemp= window.open("BillRegisterEntry_List.jsp?unit_id="+unit_id+"&office_id="+office_id,"list","status=1,height=500,width=600,resizable=YES, scrollbars=yes"); 
        winemp.moveTo(250,250);  
        winemp.focus();
}
function doParentBill(billno)    
{   
        clearAll();
        document.forms[0].billregisterno.value=billno;
        document.forms[0].billregisterno.disabled=true;
        var url="../../../../../BillRegisterEntry?command=retrieve&billno1="+billno;
        var xmlrequest= AjaxFunction();
        xmlrequest.open("GET",url,true); 
        xmlrequest.onreadystatechange=function()
            {
              manipulate(xmlrequest);
            } 
        xmlrequest.send(null);
              
}
function manipulate(xmlrequest)
{
        if(xmlrequest.readyState==4)
        {
           if(xmlrequest.status==200)
            {
                var baseResponse=xmlrequest.responseXML.getElementsByTagName("response")[0];
                var tagCommand=baseResponse.getElementsByTagName("command")[0];
                var command=tagCommand.firstChild.nodeValue;
                if(command=="proceedingno")
                {
                    proceedingnoChecking(baseResponse);
                }
                else if(command=="proceedingdate")
                {
                    proceedingdateChecking(baseResponse);
                }
                else if(command=="authority")
                {
                    authorityChecking(baseResponse);
                }
                else if(command=="retrieve")
                {
                  retrieveChecking(baseResponse);
                }
                else if(command=="budgetDet")
                {
                	budgetChecking(baseResponse);
                }
                else if(command=="hcodeDet")
                {
                	headCodeDesc(baseResponse);
                }
                else if(command=="ledgerType")
                {
                	ledgerTypechecking(baseResponse);
                }
                else if(command=="add")
                {
                    alert("record inserted into database successfully");     
                    clearAll();
                }
                else if(command=="deleted")
                {
                    alert("record deleted successfully");     
                    clearAll();
                }
                else if(command=="updated")
                {
                    updateChecking(baseResponse);
                }
            }
        }
}

function headCodeDesc(baseResponse)
{
	var hdesc=baseResponse.getElementsByTagName("headdesc")[0];
	document.forms[0].txtAcc_HeadDesc.value=hdesc.firstChild.nodeValue;
}
function ledgerTypechecking(baseResponse)
{
	var slCombo=document.forms[0].cmbSL_type;
    document.forms[0].cmbSL_type.length=0;
    var typecode=baseResponse.getElementsByTagName("typecode");
    var typedesc=baseResponse.getElementsByTagName("typedesc");
    var dd=typedesc[0].firstChild.nodeValue;
      for(var i=0;i<typecode.length;i++)
    {
        var opt=document.createElement('option');
        opt.value=typecode[i].firstChild.nodeValue;
        opt.innerHTML=typedesc[i].firstChild.nodeValue;
        slCombo.appendChild(opt);
    }
}

function proceedingnoChecking(baseResponse)
{
        var proceedCombo=document.forms[0].proceedingno;
        document.forms[0].proceedingno.length=1;
        var proceedingno=baseResponse.getElementsByTagName("proceedingNo");
        for(var i=0;i<proceedingno.length;i++)
        {
            var opt=document.createElement('option');
            opt.value=proceedingno[i].firstChild.nodeValue;
            opt.innerHTML=proceedingno[i].firstChild.nodeValue;
            proceedCombo.appendChild(opt);
        }
}
function proceedingdateChecking(baseResponse)
{
        var proceedingdate=baseResponse.getElementsByTagName("proceedingDate");
        var proceedingyear=baseResponse.getElementsByTagName("proceedingYear");
        var proceedingmonth=baseResponse.getElementsByTagName("proceedingMonth");
        var totalamount=baseResponse.getElementsByTagName("totalamount");
        var empName=baseResponse.getElementsByTagName("empName");
        var empid=baseResponse.getElementsByTagName("empid");
        
        document.forms[0].proceedingdate.value=proceedingdate[0].firstChild.nodeValue;
        document.forms[0].cashbookyear.value=proceedingyear[0].firstChild.nodeValue;
        document.forms[0].cashbookmonth.value=proceedingmonth[0].firstChild.nodeValue;
        document.forms[0].sanctionedamount.value=totalamount[0].firstChild.nodeValue;
        document.forms[0].paidto.value=empName[0].firstChild.nodeValue;
        document.forms[0].payeecode.value=empid[0].firstChild.nodeValue;
       
}
function authorityChecking(baseResponse)
{
         var offName=baseResponse.getElementsByTagName("offName");
         document.forms[0].office.value=offName[0].firstChild.nodeValue;
         
}
function budgetChecking(baseResponse)
{ 		
	 document.forms[0].budgetprovision.value="";
	 document.forms[0].budgetspent.value="";
	 document.forms[0].amountdeducted.value="";
     document.forms[0].budgetavailable.value="";
	 var budgetalloted =baseResponse.getElementsByTagName("budgetalloted");
     var budgetspent =baseResponse.getElementsByTagName("budgetspent");
     document.forms[0].budgetprovision.value=budgetalloted[0].firstChild.nodeValue;
     document.forms[0].budgetspent.value=budgetspent[0].firstChild.nodeValue;
}
function updateChecking(baseResponse)
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
function retrieveChecking(baseResponse)
{
         unitid = baseResponse.getElementsByTagName("unitid");
         officeid = baseResponse.getElementsByTagName("officeid");
         cashbookyear = baseResponse.getElementsByTagName("cashbookyear");
         cashbookmonth = baseResponse.getElementsByTagName("cashbookmonth");
         proceedingno = baseResponse.getElementsByTagName("proceedingno");
         proceedingdate = baseResponse.getElementsByTagName("proceedingdate");
         payeecode = baseResponse.getElementsByTagName("payeecode");
         billregisterdate = baseResponse.getElementsByTagName("billregisterdate");
         billprocessing = baseResponse.getElementsByTagName("billprocessing");
         accountcode = baseResponse.getElementsByTagName("accountcode");
         ledgertype = baseResponse.getElementsByTagName("ledgertype");
         ledgercode= baseResponse.getElementsByTagName("ledgercode");
         budgetprovision = baseResponse.getElementsByTagName("budgetprovision");
         budgetspent = baseResponse.getElementsByTagName("budgetspent");
         amountdeducted = baseResponse.getElementsByTagName("amountdeducted");
         budgetavailable = baseResponse.getElementsByTagName("budgetavailable");
         remarks= baseResponse.getElementsByTagName("remarks");
         
         
          document.forms[0].cmbAcc_UnitCode.value=unitid[0].firstChild.nodeValue;
          document.forms[0].cmbOffice_code.value=officeid[0].firstChild.nodeValue;
          document.forms[0].cashbookyear.value=cashbookyear[0].firstChild.nodeValue;
          document.forms[0].cashbookmonth.value=cashbookmonth[0].firstChild.nodeValue;
          document.forms[0].proceedingno.value=proceedingno[0].firstChild.nodeValue;
          document.forms[0].proceedingdate.value=proceedingdate[0].firstChild.nodeValue;
          var param=document.forms[0].proceedingno.value;
          callProceedingDate(param);
          document.forms[0].paidto.value=payeecode[0].firstChild.nodeValue;
          document.forms[0].billregisterdate.value=billregisterdate[0].firstChild.nodeValue;
          var empid=billprocessing[0].firstChild.nodeValue;
          document.forms[0].txtEmpID_mas.value=empid;
          
          var cmbmas=7;
          doFunction('Load_MasterSL_Code',cmbmas);   //   mas_employee(empid);
          callAuthority();
          var hcode=accountcode[0].firstChild.nodeValue;
          // items[4]=document.getElementById("cmbSL_type").options[document.getElementById("cmbSL_type").selectedIndex].text;         
          
          document.forms[0].txtAcc_HeadCode.value=hcode;
      //    doFunction('checkCode',accountcode[0].firstChild.nodeValue);
          headcodeFn(hcode);
       //   doFunction('checkCode',null);
          // items[4]=document.getElementById("cmbSL_type").options[document.getElementById("cmbSL_type").selectedIndex].text;         
          var val=ledgertype[0].firstChild.nodeValue;
          if(val==0)
          {
        	  document.forms[0].cmbSL_type.length=0;
        	  document.forms[0].cmbSL_Code.length=0;
          }
          else
          {
        	  subledgerTypeDescription(val);
         
	          if(val==7) //   alert("employee");
	          {
	             
	                document.forms[0].txtEmpID_trs.value=val;
	                doFunction('Load_SL_Code',val);
	          }
	          else if(val==5) //   alert("office");
	          {
	             
	        	  document.forms[0].txtOfficeID_trs.value=val;
	                doFunction('Load_SL_Code',val);
	          } 
	          else if(val==10)
	          {
	        	  document.forms[0].cmbSL_Code.value=val;
	        	  doFunction('Load_SL_Code',val);
	          }
	          else
	          {
	        	  doFunction('Load_SL_Code',val);
	          }
          } 
          document.forms[0].cmbSL_Code.value=ledgercode[0].firstChild.nodeValue;
          document.forms[0].budgetprovision.value=budgetprovision[0].firstChild.nodeValue;
          document.forms[0].budgetspent.value=budgetspent[0].firstChild.nodeValue;
          document.forms[0].amountdeducted.value=amountdeducted[0].firstChild.nodeValue;
          document.forms[0].budgetavailable.value=budgetavailable[0].firstChild.nodeValue;
          document.forms[0].remarks.value=remarks[0].firstChild.nodeValue;
          
          document.forms[0].onadd.disabled=true;  
          document.forms[0].onupdate.disabled=false;
          document.forms[0].ondelete.disabled=false;  
          setTimeout('document.forms[0].cmbSL_type.value=ledgertype[0].firstChild.nodeValue',900);
          var code=ledgercode[0].firstChild.nodeValue;
          setTimeout('document.forms[0].cmbSL_Code.value=ledgercode[0].firstChild.nodeValue',900);
}
//after retrieving data from list,fetch headcode desc from separate query
function headcodeFn(hcode)
{
	 var xmlrequest= AjaxFunction();
	 var url="../../../../../BillRegisterEntry?command=hcodeDet&hcode="+hcode;
	 xmlrequest.open("GET",url,true);              
     xmlrequest.onreadystatechange=function()
         {
             manipulate(xmlrequest);
         }
     xmlrequest.send(null);
	
}

// subledger type

function subledgerTypeDescription(val)
{
	 var xmlrequest= AjaxFunction();
	 var url="../../../../../BillRegisterEntry?command=ledgerType&val1="+val;
	 xmlrequest.open("GET",url,true);              
     xmlrequest.onreadystatechange=function()
        {
            manipulate(xmlrequest);
        }
     xmlrequest.send(null);
}

 function clear_Combo(combo)
{
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
//  a/c head code
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
// subledger code
 /* function loadName_Mas(value)
{
 if(document.getElementById("cmbMas_SL_Code").value=="")
    return;
value=document.getElementById("cmbMas_SL_Code").options[document.getElementById("cmbMas_SL_Code").selectedIndex].text; 
s=document.getElementById("cmbMas_SL_type").value;
if(s=="7" )
value=value.substring(0,value.indexOf("-"));

//document.getElementById("txtRecei_from").value=value;
} */