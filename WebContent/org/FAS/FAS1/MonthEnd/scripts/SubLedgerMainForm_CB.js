var common_cmbSL_Code="";
var common_cmbSL_type="";
var job_flag;
var FC_Command="";


/**
 *  Browser Indentification 
 */
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




/**
 *  Load Financial Year
 */
 /*function loadfyr()
{
        var cyr, cdt,cdt1;
 	cdt=new Date();
 	cyr=cdt.getFullYear();
 	cmn=cdt.getMonth();       
        var cmbFinancialYear=document.getElementById("cmbFinancialYear");
        cyr=cyr+1;
 	if (parseInt(cmn) <= 2)
        {
                alert("inside if");
                document.frmSubLedgerSystem.cmbFinancialYear.length=5;
                cmbFinancialYear.innerHTML="";
                var option=document.createElement("OPTION");
               
               // for (var i = 0 ; i < 3; i++) 
              //  {
         
                  
                  var id=(cyr-2)+"-"+(cyr-1);
                  var option=document.createElement("OPTION");
                  option.text=id;
                  option.value=id;
                  try
                        {
                            cmbFinancialYear.add(option);
                        }catch(errorobject)
                        { 
                            cmbFinancialYear.add(option,null);
                        } 
                  
                 // cyr--;
               // }
 	}
 	else 
 	{
           alert("inside else");
           document.frmSubLedgerSystem.cmbFinancialYear.length=5;
           cmbFinancialYear.innerHTML="";
           var option1=document.createElement("OPTION");
           try
                        {
                            cmbFinancialYear.add(option1);
                        }catch(errorobject)
                        { 
                            cmbFinancialYear.add(option1,null);
                        } 
        //if(parseInt(cmn)>=12)
        //{
            //alert("inside if");
            //for (var i = 0 ; i < 3; i++) 
            //{
              var id=(cyr-1)+"-"+(cyr);              
              var option=document.createElement("OPTION");
              option.text=id;
              option.value=id;
                  try
                        {
                            cmbFinancialYear.add(option);
                        }catch(errorobject)
                        { 
                            cmbFinancialYear.add(option,null);
                        }
             // cyr--;
           // }
       }
        else
        {
            cmbFinancialYear.innerHTML="";
            //for (var i = 0 ; i < 3; i++) 
           // {
                var id=(cyr-2)+"-"+(cyr-1);              
              var option=document.createElement("OPTION");
              option.text=id;
              option.value=id;
                  try
                        {
                            cmbFinancialYear.add(option);
                        }catch(errorobject)
                        { 
                            cmbFinancialYear.add(option,null);
                        }
             // cyr--;
           // }
        }
 	}        
}*/





var winAccHeadCode;

function AccHeadpopup()
{
    if (winAccHeadCode && winAccHeadCode.open && !winAccHeadCode.closed) 
    {
       winAccHeadCode.resizeTo(500,500);
       winAccHeadCode.moveTo(250,250); 
       winAccHeadCode.focus();
    }
    else
    {
        winAccHeadCode=null
    }
        
    winAccHeadCode= window.open("../../../../../org/FAS/FAS1/AccountHeadDirectory/jsps/Acc_Head_Dir_List_InUse.jsp","AccountHeadSearch","status=1,height=500,width=500,resizable=YES, scrollbars=yes"); 
    winAccHeadCode.moveTo(250,250);  
    winAccHeadCode.focus();
    
}


function doParentAccHead(code)
{
   document.getElementById("cmbAcHeadCode").value=code;
   doFunction('checkCode','null');
   return true;
}
window.onunload=function()
{
if (winAccHeadCode && winAccHeadCode.open && !winAccHeadCode.closed) winAccHeadCode.close();
if (listPopupwindow && listPopupwindow.open && !listPopupwindow.closed) listPopupwindow.close();
}




function clearall()
{   
        var cmbSL_Code=document.getElementById("cmbMas_SL_Code");
        clear_Combo(cmbSL_Code);
        document.frmSubLedgerSystem.cmbAcHeadCode.value="";
        document.frmSubLedgerSystem.cmbMas_SL_type.selectedIndex=0;
        document.frmSubLedgerSystem.cmbMas_SL_Code.selectedIndex=0;
        document.frmSubLedgerSystem.cmbAcHeadCode.readOnly=false;
        document.frmSubLedgerSystem.txtCloseBal.value="";
        document.frmSubLedgerSystem.radCloseBalCrDrInd[0].checked=true;
        document.frmSubLedgerSystem.txtaccountheadname.value="";
        var d=document.getElementById("cmdAdd");
        d.style.display="block";
        var d1=document.getElementById("cmdUpdate");
        d1.style.display="none";
        var d2=document.getElementById("cmdDelete");
        d2.style.display="none";
}

function sixdigit()
{
 if(document.getElementById("cmbAcHeadCode").value.length!=0)
    {
        if((document.getElementById("cmbAcHeadCode").value).length<6)
        {
        alert("Account Head Code shouldn't less than 6 digit number");
        document.getElementById("cmbAcHeadCode").focus();
        return false;
        }
    }
}




//This is to allow only numbers in control
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



function numbersonly(e)
    {
        var unicode=e.charCode? e.charCode : e.keyCode;
       
        if (unicode!=8 && unicode !=9  )
        {
            if (unicode<48||unicode>57 ) 
                return false 
        }
     }
     
     
     
function nullcheck()
{

    if(document.frmSubLedgerSystem.cmbFinancialYear.value.length!=9)
    {
        alert("Select the Finance year ");
        document.frmSubLedgerSystem.cmbFinancialYear.focus();
        return false;
    }
    if(document.frmSubLedgerSystem.cmbAcHeadCode.value.length==0)
    {
        alert("Enter the Account Head Code");
        document.frmSubLedgerSystem.cmbAcHeadCode.focus();
        return false;
    }
    
    if((document.frmSubLedgerSystem.cmbMas_SL_type.value=="0") || (document.frmSubLedgerSystem.cmbMas_SL_type.value.length<=0))
    {
        alert("Select Sub-Ledger Type");
        document.frmSubLedgerSystem.cmbMas_SL_type.focus();
        return false;
    }
    if((document.frmSubLedgerSystem.cmbMas_SL_Code.value=="0") || (document.frmSubLedgerSystem.cmbMas_SL_Code.value.length<=0))
    {
        alert("Select Sub-Ledger Code");
        document.frmSubLedgerSystem.cmbMas_SL_Code.focus();
        return false;
    }
                
             
                
    return true;
}
var listPopupwindow;

//----------------newly added---------------
var window_BankAccNumber;
function ListAll()
    {
    
     if (window_BankAccNumber && window_BankAccNumber.open && !window_BankAccNumber.closed) 
    {
       window_BankAccNumber.resizeTo(500,500);
       window_BankAccNumber.moveTo(250,250); 
       window_BankAccNumber.focus();
    }
    else
    {
        window_BankAccNumber=null
    }
         var Acc_UnitCode=document.frmSubLedgerSystem.cmbAcc_UnitCode.value;
        var OffCode=document.frmSubLedgerSystem.cmbOffice_code.value;
        var FinanYr=document.frmSubLedgerSystem.cmbFinancialYear.value;
        var CashbookYear=document.frmSubLedgerSystem.txtCB_Year.value;
        var CashbookMonth=document.frmSubLedgerSystem.txtCB_Month.value;
        var cmbAcHeadCode=document.getElementById('cmbAcHeadCode').value;
        var cmbMas_SL_type=document.getElementById('cmbMas_SL_type').value;
        var cmbMas_SL_Code=document.getElementById('cmbMas_SL_Code').value;
         window_BankAccNumber= window.open("ListofClosingSubLedger.jsp?cmbAcc_UnitCode="+Acc_UnitCode+"&cmbOffice_code="+OffCode+"&txtFinanYr="+FinanYr+"&CashbookYear="+CashbookYear+"&CashbookMonth="+CashbookMonth+"&cmbAcHeadCode="+cmbAcHeadCode+"&cmbMas_SL_type="+cmbMas_SL_type+"&cmbMas_SL_Code="+cmbMas_SL_Code,"mywindow1","resizable=YES, scrollbars=yes"); 
         window_BankAccNumber.moveTo(250,250);    
    }

window.onunload=function()
{
if (window_BankAccNumber && window_BankAccNumber.open && !window_BankAccNumber.closed) window_BankAccNumber.close();
}
function doParentClosingSubLedger(accntcode,accntdesc,SL_TYPE,SL_CODE,closebal,closebalind)
{   
    
         
            var d=document.getElementById("cmdUpdate");
            d.style.display="block";
            var d2=document.getElementById("cmdDelete");
            d2.style.display="block";
            var d1=document.getElementById("cmdAdd");
            d1.style.display="none";  
            document.getElementById("txtCB_Year").readonly=true;
           // document.getElementById("txtCB_Month").readonly=true;
   
    document.getElementById("cmbAcHeadCode").value=accntcode;
    document.getElementById("txtaccountheadname").value=accntdesc;
    document.frmSubLedgerSystem.cmbMas_SL_type.value=SL_TYPE;
    
   
    document.getElementById("txtCloseBal").value=closebal;
    if(closebalind=="CR")
    {
    	document.frmSubLedgerSystem.radCloseBalCrDrInd[0].checked=true;
    	document.frmSubLedgerSystem.radCloseBalCrDrInd[1].checked=false;
    }
    else if(closebalind=="DR")
    {
    	document.frmSubLedgerSystem.radCloseBalCrDrInd[0].checked=false;
    	document.frmSubLedgerSystem.radCloseBalCrDrInd[1].checked=true;
    }
  //  document.getElementById("radCloseBalCrDrInd").value=closebalind;
  doFunction1(SL_TYPE,SL_CODE);  
 
  

}



function doFunction1(param1,param2)
{
            cmbMas_SL_type=param1;
            cmbMas_SL_Code=param2;
          
            if(cmbMas_SL_type!="" && cmbMas_SL_Code!="")
            {
                var url="../../../../../SubLedgerMainFormServlet_CB.con?Command=loadsubledgercode&cmbSL_type="+cmbMas_SL_type+"&cmbMas_SL_Code="+cmbMas_SL_Code;
                var req=getTransport();
                req.open("GET",url,true); 
                req.onreadystatechange=function()
                {
                   handleResponse(req,param1);
                }   
                req.send(null);
            }
}


function fordcb(val)
{
	if(document.getElementById("cmbMas_SL_type").value==14)
	{
		document.getElementById("benifici").style.display='block';
	
	
	}else{
		document.getElementById("benifici").style.display='none';
	
		doFunction('Load_MasterSL_Code','null');
	}
}


function call(command,param)
{
	if(command=="get")
	{
		var cmbOffice_code=document.getElementById("cmbOffice_code").value;
		var bentypeid=document.getElementById("dcb_ben_type").value;
            //     alert('bentypeid::::'+bentypeid);
	    var url="../../../../../Journal_General_Create.view?Command=get&bentypeid="+bentypeid+"&cmbOffice_code="+cmbOffice_code;
	         //   alert(url);
	    var req=getTransport();
	    req.open("GET",url,true); 
	    req.onreadystatechange=function()
	    {
	      check_benifi(req);
	    }  ; 
	    req.send(null);
		
	}
	else if(command=="benifi"){
	var cmbOffice_code=document.getElementById("cmbOffice_code").value;
	
    var url="../../../../../Journal_General_Create.view?Command=benifi&benficierysno="+param+"&cmbOffice_code="+cmbOffice_code;
           // alert(url);
    var req=getTransport();
    req.open("GET",url,true); 
    req.onreadystatechange=function()
    {
      check_benifi(req);
    }  ; 
    req.send(null);
	}
}



function check_benifi(req)
{
 if(req.readyState==4)
    {
        if(req.status==200)
        {  
        	 response=req.responseXML.getElementsByTagName("response")[0];
             command=response.getElementsByTagName("command")[0].firstChild.nodeValue;
             flag=response.getElementsByTagName("flag")[0].firstChild.nodeValue;

            if(command=="get")
            {
            	            	
                if(flag=='success')
                {
                	try{
                		
                		  var len=response.getElementsByTagName("beneficiarysno").length;
                		  
                		 var cmb_SL_Code=document.getElementById("cmbMas_SL_Code");
                		 
                		
                         
                         var items_id=new Array();
                         var items_name=new Array();
                       
                            for(var i=0;i<len;i++)
                            {
                          	 
                          	items_id[i]=response.getElementsByTagName("beneficiarysno")[i].firstChild.nodeValue;
                           
                          	items_name[i]=response.getElementsByTagName("beneficiaryname")[i].firstChild.nodeValue;
                            
                            }
                            
                                                       
                          clear_Combo1(cmb_SL_Code);
                            // alert('here second');
                            for(var k=0;k<len;k++)
                            {   
                            	//alert(items_name[k]);
                                  var option=document.createElement("OPTION");
                                  option.text=items_name[k];
                                  option.value=items_id[k];
                                   try
                                  {
                                	   cmb_SL_Code.add(option);
                                	  
                                  }
                                  catch(errorObject)
                                  {
                                	  cmb_SL_Code.add(option,null);
                                	 
                                     // alert('error');
                                  }
                            }
                		
                		
                		
                		
                	}catch(e){alert("Error in lat"+e);}
                	
                	if( benfiflag==1)
                	{
                		benfiflag=0;            	
                	document.getElementById("cmbSL_Code").value=bensub;
                	
                	}
                	
                }else{
                	alert('Subledger Code Not Found*****');
                }
            }
                if(command=="benifi")
                {
                	            	
                    if(flag=='success')
                    {
                    	try{
                    		
                    		document.getElementById("dcb_ben_type").value=response.getElementsByTagName("bentypeid")[0].firstChild.nodeValue;
                    		  
                    		call('get','null');	
                    	}catch(e){alert(e);}
                    }
                }
             
        }
    }
}

function clear_Combo1(combo)
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

function doFunction(Command,param)
{  

    var Acc_UnitCode=document.frmSubLedgerSystem.cmbAcc_UnitCode.value;
    var OffCode=document.frmSubLedgerSystem.cmbOffice_code.value;
    var FinanYr=document.frmSubLedgerSystem.cmbFinancialYear.value;
    var cmbSL_type=document.getElementById("cmbMas_SL_type").value;
    var cmbMas_SL_Code=document.getElementById("cmbMas_SL_Code").value;
    var AcHeadCode=document.frmSubLedgerSystem.cmbAcHeadCode.value;
  
    var CloseBal=document.frmSubLedgerSystem.txtCloseBal.value;
    var CloseBalInd;
    if(document.frmSubLedgerSystem.radCloseBalCrDrInd[0].checked==true)
    {
        CloseBalInd=document.frmSubLedgerSystem.radCloseBalCrDrInd[0].value;
    }
    else
    {
        CloseBalInd=document.frmSubLedgerSystem.radCloseBalCrDrInd[1].value;
    }
    
  
    var CashbookYear=document.frmSubLedgerSystem.txtCB_Year.value;
    var CashbookMonth=document.frmSubLedgerSystem.txtCB_Month.value;
   if(Command=="Load_MasterSL_Code")
        {
            var cmbSL_type=document.getElementById("cmbMas_SL_type").value;             // input from MASTER combo *
            cmbAcc_UnitCode=document.getElementById("cmbAcc_UnitCode").value;
            cmbOffice_code=document.getElementById("cmbOffice_code").value;
            if(cmbSL_type==5)
              {
              document.getElementById("offlist_div_master").style.display='block';
              clear_Combo(document.getElementById("cmbMas_SL_Code"));
              alert("USE search ICON to select the office");
              return true;
              }
            else
              document.getElementById("offlist_div_master").style.display='none';
            if(cmbSL_type==7)
            {
                document.getElementById("emplist_div_master").style.display='block';
                //clear_Combo(document.getElementById("cmbMas_SL_Code"));
                //document.getElementById("txtOfficeID_mas").value="";
                addtional_field_value=document.getElementById("txtEmpID_mas").value;
               if(addtional_field_value=="")
                {
                      clear_Combo(document.getElementById("cmbMas_SL_Code")); 
                      alert("Enter or select the employee code*******");
                      return true;
                }
            }
          else
            {
              //  document.getElementById("txtEmpID_mas").value="";
            }
            if(cmbSL_type==89)
            {
                document.getElementById("emplist_div_master").style.display='block';
               
                 addtional_field_value=document.getElementById("txtEmpID_mas").value;
               //  alert("addtional_field_value"+addtional_field_value);
                if(addtional_field_value=="")
                {
                      clear_Combo(document.getElementById("cmbMas_SL_Code")); 
                      alert("Enter the employee code");
                      return true;
                }
            }
          else
            {
              //  document.getElementById("txtEmpID_mas").value="";
            }
           if(cmbSL_type!="")                              // called only not equal to null and 5 is for office
            {
        	   if(cmbSL_type!=7){
        		  
                cmbMas_SL_type=document.getElementById("cmbMas_SL_type").value;
                other_dept_off_alias_id=document.getElementById("cmbMas_SL_Code").value;
                var url="../../../../../SubsidiaryLedgerServlet.con?Command=Load_MasterSL_Code&cmbSL_type="+cmbSL_type+"&cmbMas_SL_type="+cmbMas_SL_type+"&other_dept_off_alias_id="+other_dept_off_alias_id+"&cmbAcc_UnitCode="+cmbAcc_UnitCode+"&cmbOffice_code="+cmbOffice_code;               
                
        	   }
        	   else
        	   {
        		   var cmbMas_SL_type=document.getElementById("cmbMas_SL_type").value;
                   var other_dept_off_alias_id=document.getElementById("cmbMas_SL_Code").value;
                   
                   var url="../../../../../Receipt_SL.view?Command=Load_MasterSL_Code&cmbSL_type="+cmbSL_type+"&cmbMas_SL_type="+cmbMas_SL_type+
                       "&other_dept_off_alias_id="+other_dept_off_alias_id+"&cmbAcc_UnitCode="+cmbAcc_UnitCode+
                       "&cmbOffice_code="+cmbOffice_code+"&addtional_field_value="+addtional_field_value;
                //   alert(url);
                   
        	   }
        	   var req=getTransport();
               req.open("GET",url,true); 
               req.onreadystatechange=function()
               {
                  
                  handleResponse(req);
               }   
                       req.send(null);
            }
            else if(cmbSL_type=="")
               clear_Combo(document.getElementById("cmbMas_SL_Code")); 
        }
      
    
    else if(Command=="office")
        {   
            var oid=param;
            var url="../../../../../SubsidiaryLedgerServlet.con?Command=office&oid="+oid;          
            var req=getTransport();
            req.open("GET",url,true); 
            req.onreadystatechange=function()
            {
               handleResponse(req);
            }   
                    req.send(null);
        }
        
        if(Command=="checkCode")
        {
            var headcode=document.frmSubLedgerSystem.cmbAcHeadCode.value;
           // alert("headcode "+headcode);
            url="../../../../../SubLedgerMainFormServlet_CB.con?Command=HeadCode&txtAcc_Head_code="+headcode;            
            var req=getTransport();
            req.open("GET",url,true); 
            req.onreadystatechange=function()
            {
               headcodeResponse(req);
            };   
                    req.send(null);
        }
        
        if(Command=="Add")
        {
            if(nullcheck())
            {
                var url="../../../../../SubLedgerMainFormServlet_CB.con?Command=Add&cmbAcc_UnitCode="+Acc_UnitCode+"&comOffCode="+OffCode+"&txtFinanYr="+FinanYr+"&cmbAcHeadCode="+AcHeadCode+"&cmbSL_type="+cmbSL_type+"&cmbMas_SL_Code="+cmbMas_SL_Code+"&CloseBal="+CloseBal+"&CloseBalInd="+CloseBalInd+"&CashbookYear="+CashbookYear+"&CashbookMonth="+CashbookMonth;
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
                if(nullcheck())
                {
                    var url="../../../../../SubLedgerMainFormServlet_CB.con?Command=Update&cmbAcc_UnitCode="+Acc_UnitCode+"&comOffCode="+OffCode+"&txtFinanYr="+FinanYr+"&cmbAcHeadCode="+AcHeadCode+"&cmbSL_type="+cmbSL_type+"&cmbMas_SL_Code="+cmbMas_SL_Code+"&CloseBal="+CloseBal+"&CloseBalInd="+CloseBalInd+"&CashbookYear="+CashbookYear+"&CashbookMonth="+CashbookMonth;
                    var req=getTransport();
                    req.open("GET",url,true); 
                    req.onreadystatechange=function()
                    {
                       handleResponse(req);
                    }   
                            req.send(null);
                }
      }
      else if(Command == "Delete")
      {
        if(confirm("Do u really want to delete the record"))
        {
                if(nullcheck())
                {
                    var url="../../../../../SubLedgerMainFormServlet_CB.con?Command=Delete&cmbAcc_UnitCode="+Acc_UnitCode+"&comOffCode="+OffCode+"&txtFinanYr="+FinanYr+"&cmbAcHeadCode="+AcHeadCode+"&CashbookYear="+CashbookYear+"&CashbookMonth="+CashbookMonth+"&cmbSL_type="+cmbSL_type+"&cmbMas_SL_Code="+cmbMas_SL_Code;
                 //  alert("url::"+url);
                    var req=getTransport();
                    req.open("GET",url,true); 
                    req.onreadystatechange=function()
                    {
                       handleResponse(req);
                    }   
                            req.send(null);
                }
         }
         else
         {
            alert("Records are not Deleted");
         }
     }
     else if(Command == "Add_FCheck") 
     {
           FC_Command ="Add";   
           var url="../../../../../SubLedgerMainFormServlet_CB.con?Command=Freeze_Check&cmbAcc_UnitCode="+Acc_UnitCode+"&comOffCode="+OffCode+"&txtFinanYr="+FinanYr+"&cmbAcHeadCode="+AcHeadCode+"&cmbSL_type="+cmbSL_type+"&cmbMas_SL_Code="+cmbMas_SL_Code+"&CloseBal="+CloseBal+"&CloseBalInd="+CloseBalInd+"&CashbookYear="+CashbookYear+"&CashbookMonth="+CashbookMonth;
           var req=getTransport();
           req.open("GET",url,true); 
           req.onreadystatechange=function()
           {
               handleResponse(req);
           }   
           req.send(null);
     }
     else if(Command == "Update_FCheck")  
     {
           FC_Command ="Update";           
           var url="../../../../../SubLedgerMainFormServlet_CB.con?Command=Freeze_Check&cmbAcc_UnitCode="+Acc_UnitCode+"&comOffCode="+OffCode+"&txtFinanYr="+FinanYr+"&cmbAcHeadCode="+AcHeadCode+"&cmbSL_type="+cmbSL_type+"&cmbMas_SL_Code="+cmbMas_SL_Code+"&CloseBal="+CloseBal+"&CloseBalInd="+CloseBalInd+"&CashbookYear="+CashbookYear+"&CashbookMonth="+CashbookMonth;
           var req=getTransport();
           req.open("GET",url,true); 
           req.onreadystatechange=function()
           {
              handleResponse(req);
           }   
           req.send(null);
           
     }
     else if(Command == "Delete_FCheck")  
     {
           FC_Command ="Delete";    
           var url="../../../../../SubLedgerMainFormServlet_CB.con?Command=Freeze_Check&cmbAcc_UnitCode="+Acc_UnitCode+"&comOffCode="+OffCode+"&txtFinanYr="+FinanYr+"&cmbAcHeadCode="+AcHeadCode+"&cmbSL_type="+cmbSL_type+"&cmbMas_SL_Code="+cmbMas_SL_Code+"&CloseBal="+CloseBal+"&CloseBalInd="+CloseBalInd+"&CashbookYear="+CashbookYear+"&CashbookMonth="+CashbookMonth;
           var req=getTransport();
           req.open("GET",url,true); 
           req.onreadystatechange=function()
           {
                handleResponse(req);
           }   
           req.send(null);
     }
     
     
     
     
     
     if(Command=="SubLedgerReturn")
     {
            var headcode=document.frmSubLedgerSystem.cmbAcHeadCode.value;
            url="../../../../../BudgetMasterServlet.con?Command=HeadCode&txtAcc_Head_code="+headcode;           
            var req=getTransport();
            req.open("GET",url,true); 
            req.onreadystatechange=function()
            {
               SubLedgerReturnResponse(req);
            }   
               req.send(null);
     }
}


function headcodeResponse(req)
{

    if(req.readyState==4)
    {
        if(req.status==200)
        {  
            
            var baseResponse=req.responseXML.getElementsByTagName("response")[0];
            
            var flag=baseResponse.getElementsByTagName("flag")[0].firstChild.nodeValue;
            
            if(flag=="success")
            {
                var headname=baseResponse.getElementsByTagName("hdesc")[0].firstChild.nodeValue;
                document.frmSubLedgerSystem.txtaccountheadname.value="";
                document.frmSubLedgerSystem.txtaccountheadname.value=headname;
                
                //Change on Date 05-jan-2007//
                
                var BalType=baseResponse.getElementsByTagName("BalType")[0].firstChild.nodeValue;
                var SL_YN =baseResponse.getElementsByTagName("SL_YN")[0].firstChild.nodeValue;
                
                var cmbSL_type=document.getElementById("cmbMas_SL_type");  
                ///--------
               // var cmbSL_type=baseResponse.getElementsByTagName("SLDesc")[0].firstChild.nodeValue;
                //var cmbMas_SL_Code=baseResponse.getElementsByTagName("SLCodeDesc")[0].firstChild.nodeValue;
               //---------- 
       if(SL_YN=="Y")
       {
        
        var items_SLcode=new Array();
        var items_SLdesc=new Array();
            var SLCODE=baseResponse.getElementsByTagName("SLCODE");
            var SLDESC=baseResponse.getElementsByTagName("SLDESC");
            for(var k=0;k<SLCODE.length;k++)
            {
                items_SLcode[k]=baseResponse.getElementsByTagName("SLCODE")[k].firstChild.nodeValue;
                items_SLdesc[k]=baseResponse.getElementsByTagName("SLDESC")[k].firstChild.nodeValue;
            }
            
            cmbSL_type.innerHTML="";
            var option=document.createElement("OPTION");
            option.text="--Select SubLedger Type--";
            option.value="0";  
            try
            {
                cmbSL_type.add(option);
            }catch(errorObject)
            {
                cmbSL_type.add(option,null);
            }
            for(var k=0;k<SLCODE.length;k++)
            {   
              var option=document.createElement("OPTION");
              option.text=items_SLdesc[k];
              option.value=items_SLcode[k];
               try
              {
                  cmbSL_type.add(option);
              }
              catch(errorObject)
              {
                  cmbSL_type.add(option,null);
              }
            }
       }
        if(SL_YN=="N" || SL_YN=="null")
           {    
                cmbSL_type.innerHTML=""; 
                var option=document.createElement("OPTION");
                option.text="--Select SubLedger Type--";
                option.value="0";
                try
                {
                    cmbSL_type.add(option);
                }catch(errorObject)
                {
                    cmbSL_type.add(option,null);
                }
            }
                
                
                
                //End Change on Date 05-jan-2007//
            }
            else
            {
                var cmbSL_type=document.getElementById("cmbMas_SL_type"); 
                document.frmSubLedgerSystem.cmbAcHeadCode.value="";
                document.frmSubLedgerSystem.txtaccountheadname.value="";
                alert("Invalid HeadCode");
                document.frmSubLedgerSystem.cmbAcHeadCode.focus();
                
                cmbSL_type.innerHTML=""; 
                var option=document.createElement("OPTION");
                option.text="--Select SubLedger Type--";
                option.value="0";
                try
                {
                    cmbSL_type.add(option);
                }catch(errorObject)
                {
                    cmbSL_type.add(option,null);
                }
            }
        }
        
    }

}




function SubLedgerReturnResponse(req)
{
    if(req.readyState==4)
    {
        if(req.status==200)
        {              
            var baseResponse=req.responseXML.getElementsByTagName("response")[0];
            
            var flag=baseResponse.getElementsByTagName("flag")[0].firstChild.nodeValue;
             var SLDESC=baseResponse.getElementsByTagName("SLDESC");
            if(flag=="success")
            {
                var headname=baseResponse.getElementsByTagName("headcode")[0].firstChild.nodeValue;
                var SLDESC=baseResponse.getElementsByTagName("headcode")[0].firstChild.nodeValue;
                document.frmSubLedgerSystem.txtaccountheadname.value="";
                document.frmSubLedgerSystem.txtaccountheadname.value=headname;              
            }
            else
            {
                var cmbSL_type=document.getElementById("cmbMas_SL_type"); 
                document.frmSubLedgerSystem.cmbAcHeadCode.value="";
                document.frmSubLedgerSystem.txtaccountheadname.value="";
                alert("Invalid HeadCode");
                document.frmSubLedgerSystem.cmbAcHeadCode.focus();
                
                cmbSL_type.innerHTML=""; 
                var option=document.createElement("OPTION");
                option.text="--Select SubLedger Type--";
                option.value="0";
                try
                {
                    cmbSL_type.add(option);
                }catch(errorObject)
                {
                    cmbSL_type.add(option,null);
                }
            }
        }
    }
}


//dhana
/////////////////////////////////////////////   handleResponse()  /////////////////////////////////////////////////////
function handleResponse(req,param)
{    
    if(req.readyState==4)
    { 
        if(req.status==200)
        {  
           var baseResponse=req.responseXML.getElementsByTagName("response")[0];
           // alert(baseResponse);
            var tagcommand=baseResponse.getElementsByTagName("command")[0];
            var Command=tagcommand.firstChild.nodeValue;
  
            if(Command=="Load_MasterSL_Code")
            {
                Load_MasterSL_Code(baseResponse,param);
            }
            if(Command=="loadsubledgercode")
            { 
                loadsubledgercode(baseResponse);
            }
           
            else if(Command=="office")
            {
                loadOffice(baseResponse);
            }
            else if(Command=="Add")
            {
                addRow(baseResponse);
            }
            else if(Command=="Update")
            {
                updateRow(baseResponse);
            }
            else if(Command=="Delete")
            {
                deleteRow(baseResponse);
            }     
            else if(Command=="Freeze_Check")
            {
                Freeze_Check(baseResponse);
            }  
         }
    }
}



function Freeze_Check(baseResponse)
{
 var flag=baseResponse.getElementsByTagName("flag")[0].firstChild.nodeValue;
    if(flag=="success")
    {   
         alert("Sub Ledger Closing Balance has been Frozen Already");
         clearall();
    } 
    else
    {
       if ( FC_Command == "Add")
         doFunction('Add','null');
       else if( FC_Command == "Update")
         doFunction('Update','null');
       else if( FC_Command == "Delete")
         doFunction('Delete','null');  
    }   
    
}




function addRow(baseResponse)
{
 var flag=baseResponse.getElementsByTagName("flag")[0].firstChild.nodeValue;
    if(flag=="success")
    {   
         alert("Record inserted successfully");
         clearall();
    }
    else if(flag=="failure")
    {
        alert("Records r not inserted");
    }
    if(flag=="AlreadyExist")
    {
        alert("Account Head Code Already Exist");
        
    }
}





function updateRow(baseResponse)
{

 var flag=baseResponse.getElementsByTagName("flag")[0].firstChild.nodeValue;
    if(flag=="success")
    {   
         alert("Record updated successfully");
         document.frmSubLedgerSystem.txtCB_Year.disabled=false;
     document.frmSubLedgerSystem.txtCB_Month.disabled=false;
         clearall();
    }
    else
    {
        alert("Record r not updated");
    }
}




function deleteRow(baseResponse)
{
 var flag=baseResponse.getElementsByTagName("flag")[0].firstChild.nodeValue;
    if(flag=="success")
    {   
         alert("Records deleted successfully");
         clearall();
    }
    else
    {
        alert("Record r not deleted");
    }

}





///////////////////////////////////// loadoffice ///////
function loadOffice(baseResponse)
{
    var flag=baseResponse.getElementsByTagName("flag")[0].firstChild.nodeValue;
    
    if(flag=="success")
    {  
        var oid=baseResponse.getElementsByTagName("oid")[0].firstChild.nodeValue;
        var oname=baseResponse.getElementsByTagName("oname")[0].firstChild.nodeValue;
       if(job_flag==true)
        var cmbSL_Code=document.getElementById("cmbMas_SL_Code");                        // Get the select combo id from master
       if(job_flag==false)
        var cmbSL_Code=document.getElementById("cmbSL_Code");                            // Get the select combo id from transaction
        
            cmbSL_Code.innerHTML="";
            var option=document.createElement("OPTION");
            option.text=oname;
            option.value=oid;
            try
            {
                cmbSL_Code.add(option);
            }catch(errorObject)
            {
                cmbSL_Code.add(option,null);
            }
    }
    else 
    {
     var oid=baseResponse.getElementsByTagName("oid")[0].firstChild.nodeValue;
     //alert("Office Id '"+oid+"' doesn't Exist");
    }
    job_flag="";
}





function Load_MasterSL_Code(baseResponse,param)
{
var flag=baseResponse.getElementsByTagName("flag")[0].firstChild.nodeValue;
var cmbSL_type=document.getElementById("cmbMas_SL_type").value;

    if(flag=="success")
    {
    //	alert("success");
         var cmbSL_Code=document.getElementById("cmbMas_SL_Code");      // value assigned to same local variable name
         
         var items_id=new Array();
         var items_name=new Array();
        
            var cid=baseResponse.getElementsByTagName("cid");
            var cname=baseResponse.getElementsByTagName("cname");
            for(var k=0;k<cid.length;k++)
            {
                items_id[k]=baseResponse.getElementsByTagName("cid")[k].firstChild.nodeValue;
                items_name[k]=baseResponse.getElementsByTagName("cname")[k].firstChild.nodeValue;
            }
         
           clear_Combo(cmbSL_Code);
            //dhana
           document.getElementById("cmbMas_SL_Code").length=0;
            for(var k=0;k<items_id.length;k++)
            {   
                  var option=document.createElement("OPTION");
                  option.text=items_name[k];
                  option.value=items_id[k];
                   try
                  {
                      cmbSL_Code.add(option);
                  }
                  catch(errorObject)
                  {
                      cmbSL_Code.add(option,null);
                  }
            }
           
            if(param!='null')
              {
            
             document.getElementById("cmbMas_SL_Code").value=param;
             }
    }
    else if(flag=="failure")
    {
        alert("No data found");
        var cmbSL_Code=document.getElementById("cmbMas_SL_Code");   // value assigned to same local variable name
        clear_Combo(cmbSL_Code);
    }
}





function loadsubledgercode(baseResponse)
{
   // alert("load");
var flag=baseResponse.getElementsByTagName("flag")[0].firstChild.nodeValue;
var cmbMas_SL_Code=document.getElementById("cmbMas_SL_Code").value;
    if(flag=="success")
    {
         var cmbMas_SL_Code=document.getElementById("cmbMas_SL_Code");
      //   document.getElementById("cmbMas_SL_Code").length=0;
         var items_id=new Array();
         var items_name=new Array();

            var cid=baseResponse.getElementsByTagName("cid");
            var cname=baseResponse.getElementsByTagName("cname");
            for(var k=0;k<cid.length;k++)
            {
                items_id[k]=baseResponse.getElementsByTagName("cid")[k].firstChild.nodeValue;
                items_name[k]=baseResponse.getElementsByTagName("cname")[k].firstChild.nodeValue;
            }
         
            clear_Combo(cmbMas_SL_Code);
            document.getElementById("cmbMas_SL_Code").length=0;
            for(var k=0;k<items_id.length;k++)
            {   
                  var option=document.createElement("OPTION");
                  option.text=items_name[k];
                  option.value=items_id[k];
                   try
                  {
                      cmbMas_SL_Code.add(option);
                  }
                  catch(errorObject)
                  {
                      cmbMas_SL_Code.add(option,null);
                  }
            }
            document.getElementById("cmbMas_SL_Code").value=common_cmbSL_Code;
            common_cmbSL_Code="";                 // it should be cleared to avoid the last value exist in this variable
            if (window_BankAccNumber && window_BankAccNumber.open && !window_BankAccNumber.closed) window_BankAccNumber.close();
    }
    else if(flag=="failure")
    {
        alert("No Subledger found");
         var cmbMas_SL_Code=document.getElementById("cmbMas_SL_Code");
         clear_Combo(cmbMas_SL_Code);
    }
}





function clear_Combo(combo)
{
        //alert(combo.id)
        var cmbSL_Code=document.getElementById(combo.id);   
        cmbSL_Code.innerHTML="";
        var option=document.createElement("OPTION");
        option.text="--Select SubLedger Code--";
        option.value="";
        try
        {
            cmbSL_Code.add(option);
        }catch(errorObject)
        {
            cmbSL_Code.add(option,null);
        }
}




//////////////   FOR DEPUTATION JOB POPUP WINDOW //////////////////////
function jobpopup_master()
{
    job_flag=true;
    jobpopup();
}
var winjob;
function jobpopup()
{
    if(winjob && winjob.open && !winjob.closed) 
    {
       winjob.resizeTo(500,500);
       winjob.moveTo(250,250); 
       winjob.focus();
    }
    else
    {
        winjob=null
    }
        
    winjob= window.open("../../../../../org/HR/HR1/OfficeMaster/jsps/JobPopupJSP.jsp","JobSearch","status=1,height=500,width=500,resizable=YES, scrollbars=yes"); 
    winjob.moveTo(250,250);  
    winjob.focus();
}
function forChildOption()
{
      if (winjob && winjob.open && !winjob.closed) 
             winjob.officeSelection(true,true,true,false);
}
function doParentJob(jobid,deptid)
{
       if(deptid=='TWAD')
        {
            doFunction('office',jobid);
        }
        else
        {
                alert('Please select an Office ');
                if (winjob && winjob.open && !winjob.closed) 
                {
                   winjob.resizeTo(500,500);
                   winjob.moveTo(250,250); 
                   winjob.focus();
                }
                return false
        }
   
    return true
}

function valid_amt(field)
{
    
    amt=field.value;
    if(amt.indexOf(".")!=amt.lastIndexOf("."))
    {
        alert("Enter a Valid Amount");
        field.value="";
        field.focus();
    }
}

function limit_amt(field,e)
{
      var unicode=e.charCode? e.charCode : e.keyCode;
      if(field.value.length<17)
      {
        if(field.value.length==14 && field.value.indexOf('.')==-1  )
        field.value=field.value+'.';
        if (unicode!=8 && unicode !=9  )
        {
            if (unicode<46 || unicode==47 || unicode>57   ) 
                return false 
        }
      }
      else 
      return false;
      
}

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

function checkdt1(t)
{
 
    if(t.value.length==0)
        return false;
    if(t.value.length==10  && t.value.indexOf('/',0)==2 && t.value.indexOf('/',3)==5)
    {
      
       
        // var c=t.value.replace(/-/g,'/');
         var c=t.value;
        try{
        var f=DateFormat(t,c,event,true,'3');
        }catch(e){
        //exception  start
      
         t.value=c;
            var sc=t.value.split('/');
            var currenDay =sc[0];
            var currentMonth=sc[1];
            var currentYear=sc[2];
            //alert(currentYear == getCurrentYear()  && currentMonth == getCurrentMonth() && currenDay > getCurrentDay());
            if(currentYear > getCurrentYear()  || currentYear<_Service_Period_Beg_Year)
            {
            
                    alert('Entered date should be less than current date and \n year should be greater than or equal to '+_Service_Period_Beg_Year);
                    t.value="";
                    t.focus();
                    return false;
           } 
           else if(currentYear == getCurrentYear())
            {
                    if( currentMonth > getCurrentMonth())
                    {
                        alert('Entered date should be less than current date and \n year should be greater than or equal to '+_Service_Period_Beg_Year);
                        t.value="";
                        t.focus();
                        return false;
                    }
                    else if( currentMonth == getCurrentMonth())
                    {
                        if(currenDay > getCurrentDay() )
                        {
                                alert('Entered date should be less than current date and \n year should be greater than or equal to '+ _Service_Period_Beg_Year);
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
         
            if(currentYear > getCurrentYear()  || currentYear<_Service_Period_Beg_Year)
            {
            
                    alert('Entered date should be less than current date and \n year should be greater than or equal to '+_Service_Period_Beg_Year);
                    t.value="";
                    t.focus();
                    return false;
           } 
           else if(currentYear == getCurrentYear())
            {
                    if( currentMonth > getCurrentMonth())
                    {
                         alert('Entered date should be less than current date and \n year should be greater than or equal to '+_Service_Period_Beg_Year);
                        t.value="";
                        t.focus();
                        return false;
                    }
                    else if( currentMonth == getCurrentMonth())
                    {
                        if(currenDay > getCurrentDay() )
                        {
                                alert('Entered date should be less than current date and \n year should be greater than or equal to '+_Service_Period_Beg_Year);
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
function Exit()
 {
    window.open('','_parent','');
    window.close();
 }
 
function loadAccountOffice(){
	var req = getTransport();
	if (req == null) {
		alert("Your borwser doesnot support AJAX");
		return;
	}
	var accOff = document.getElementById("cmbAcc_UnitCode").value;
	//alert("accOff "+accOff);
	if (accOff == "select") {
	}else{
		url = "../../../../../ChequeBookServ.view?Command=loadAccOff" + "&accOff="+accOff;		
		req.open("GET", url, true);
		req.onreadystatechange = function() {
			loadAccountHeadCode(req);
		};
		req.send(null);
	}
}
function loadAccountHeadCode(req){
	if (req.readyState == 4) {
		if (req.status == 200) {
			//alert("in added");
			response = req.responseXML.getElementsByTagName("response")[0];
			viewAccountHeadCode();
		}
	}
}
function viewAccountHeadCode(){
	var command = response.getElementsByTagName("command")[0].firstChild.nodeValue;
	var flag = response.getElementsByTagName("status")[0].firstChild.nodeValue;
	if(command=="getaccoff"){
		if(flag=="success"){
			var len=response.getElementsByTagName("ACCOUNTHEADCODE").length;
			var selectdiv=document.getElementById('cmbOffice_code');
			var listOpt=document.createElement("option");
			selectdiv.length=0;
			selectdiv.appendChild(listOpt);
			listOpt.text="select";
			listOpt.value="select";
			for(var i=0; i<len; i++){
				listOpt=document.createElement("option");
				selectdiv.appendChild(listOpt);
				listOpt.text=response.getElementsByTagName("ACCOUNTHEADNAME")[i].firstChild.nodeValue;
				listOpt.value=response.getElementsByTagName("ACCOUNTHEADCODE")[i].firstChild.nodeValue;
			}
			document.getElementById('cmbOffice_code').selectedIndex=1;
		}else{
			alert("There is no accounting for office code for this accounting unit code");
		}
	}else{
		alert("Process Failure");
	}
	
} 
 