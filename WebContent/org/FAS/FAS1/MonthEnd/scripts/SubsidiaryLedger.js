var job_flag;
/////////////////////////////////////////////   XML req  /////////////////////////////////////////////////////
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




function checknull()
{

    if((document.frmSubsidiaryLedger.cmbOffice_code.value=="") || (document.frmSubsidiaryLedger.cmbOffice_code.value.length<=0) || (document.frmSubsidiaryLedger.cmbOffice_code.value=="0"))
    {
        alert("Please Select Accounting Office Code");
        document.frmSubsidiaryLedger.cmbOffice_code.focus();
        return false;
    
    }
    if((document.getElementById("txtCB_Year").value.length!=4)|| (document.getElementById("txtCB_Year").value.length<=0) ||(document.getElementById("txtCB_Year").value==""))
    {
        alert("Enter the correct year");
        document.getElementById("txtCB_Year").focus();
        return false;
    }
     if(document.getElementById("txtCB_Month").value=="")
    {
        alert("Select a month");
        return false;
    }
    if((document.frmSubsidiaryLedger.cmbAccHeadCode.value=="") || (document.frmSubsidiaryLedger.cmbAccHeadCode.value.length<=0) || (document.frmSubsidiaryLedger.cmbAccHeadCode.value=="0"))
    {
        alert("Please Select Account Head Code");
        document.frmSubsidiaryLedger.cmbAccHeadCode.focus();
        return false;
    }
 return true;
}



//Financial Year Coding Part ///

function loadfyr()
{
         var cyr, cdt,cdt1;
 	cdt=new Date();
 	cyr=cdt.getFullYear();
 	cmn=cdt.getMonth();
        //alert("cdate"+cdt);
        //alert("cmonth"+cmn);
        //alert("cyear"+cyr);
        var cmbFinancialYear=document.getElementById("cmbFinancialYear");
        cyr=cyr+1;
 	if (parseInt(cmn) <= 2)
        {
  
                document.frmSubsidiaryLedger.cmbFinancialYear.length=5;
                cmbFinancialYear.innerHTML="";
                var option=document.createElement("OPTION");
                option.text="--Select FinancialYear--";
                option.value=0;
                try
                        {
                            cmbFinancialYear.add(option);
                        }catch(errorobject)
                        { 
                            cmbFinancialYear.add(option,null);
                        } 
                for (var i = 0 ; i < 1; i++) 
                {
         
                  //document.frmSubsidiaryLedger.cmbFinancialYear.options[i].text=(cyr-2)+"-"+(cyr-1);
                  //document.frmSubsidiaryLedger.cmbFinancialYear.options[i].value=(cyr-2)+"-"+(cyr-1);
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
                  
                  cyr--;
                }
 	}
 	else 
 	{
            //alert('hai');
            //alert(cmn);
           document.frmSubsidiaryLedger.cmbFinancialYear.length=5;
           cmbFinancialYear.innerHTML="";
           var option1=document.createElement("OPTION");
           option1.text="--Select FinancialYear--";
           option1.value=0;
           try
                        {
                            cmbFinancialYear.add(option1);
                        }catch(errorobject)
                        { 
                            cmbFinancialYear.add(option1,null);
                        } 
        if(cmn>=12)
        {
            for (var i = 0 ; i < 1; i++) 
            {
                var id=(cyr-1)+"-"+(cyr);
              //document.frmSubsidiaryLedger.cmbFinancialYear.options[i].text=id;
              //document.frmSubsidiaryLedger.cmbFinancialYear.options[i].value=id;
              
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
              cyr--;
            }
        }
        else
        {
            for (var i = 0 ; i < 1; i++) 
            {
                var id=(cyr-1)+"-"+(cyr);
              //document.frmSubsidiaryLedger.cmbFinancialYear.options[i].text=id;
              //document.frmSubsidiaryLedger.cmbFinancialYear.options[i].value=id;
              
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
              cyr--;
            }
        }
 	}
        
}


/////////////////////////////////////////////   doFunction()  /////////////////////////////////////////////////////

function doFunction(Command,param)
{   

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
              
           
           if(cmbSL_type!="0")                              // called only not equal to null and 5 is for office
            {
                cmbMas_SL_type=document.getElementById("cmbMas_SL_type").value;
                other_dept_off_alias_id=document.getElementById("cmbMas_SL_Code").value;
                var url="../../../../../SubsidiaryLedgerServlet.con?Command=Load_MasterSL_Code&cmbSL_type="+cmbSL_type+"&cmbMas_SL_type="+cmbMas_SL_type+"&other_dept_off_alias_id="+other_dept_off_alias_id+"&cmbAcc_UnitCode="+cmbAcc_UnitCode+"&cmbOffice_code="+cmbOffice_code;
                //alert(url);
                var req=getTransport();
                req.open("GET",url,true); 
                req.onreadystatechange=function()
                {
                   
                   handleResponse(req);
                }   
                   req.send(null);
            }
            else if(cmbSL_type=="0")
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
}


/////////////////////////////////////////////   handleResponse()  /////////////////////////////////////////////////////
function handleResponse(req)
{  
     
    if(req.readyState==4)
    { 
        if(req.status==200)
        {  
            
            var baseResponse=req.responseXML.getElementsByTagName("response")[0];
            var tagcommand=baseResponse.getElementsByTagName("command")[0];
            var Command=tagcommand.firstChild.nodeValue;
            if(Command=="Load_MasterSL_Code")
            {
                Load_MasterSL_Code(baseResponse);
            }
            else if(Command=="office")
            {
                loadOffice(baseResponse);
            }
        }
    }
}



/////////////////////////////////////////////  For MASTER Combo SL Code //////////////////////////////////

function Load_MasterSL_Code(baseResponse)
{
var flag=baseResponse.getElementsByTagName("flag")[0].firstChild.nodeValue;
var cmbSL_type=document.getElementById("cmbMas_SL_type").value;

    if(flag=="success")
    {
         var cmbSL_Code=document.getElementById("cmbMas_SL_Code");      // value assigned to same local variable name
         
         var items_id=new Array();
         var items_name=new Array();
         //alert("sl_type"+cmbSL_type);
         //if(cmbSL_type=="11" || cmbSL_type=="1" || cmbSL_type=="2"  || cmbSL_type=="3" || cmbSL_type=="7" )
         //{
            var cid=baseResponse.getElementsByTagName("cid");
            var cname=baseResponse.getElementsByTagName("cname");
            for(var k=0;k<cid.length;k++)
            {
                items_id[k]=baseResponse.getElementsByTagName("cid")[k].firstChild.nodeValue;
                items_name[k]=baseResponse.getElementsByTagName("cname")[k].firstChild.nodeValue;
            }
          //}  
           clear_Combo(cmbSL_Code);
            
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
    }
    else if(flag=="failure")
    {
        alert("No data found");
        var cmbSL_Code=document.getElementById("cmbMas_SL_Code");   // value assigned to same local variable name
        clear_Combo(cmbSL_Code);
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

function typedesc()
{
    var levelt=document.frmSubsidiaryLedger.cmbMas_SL_type.options[document.frmSubsidiaryLedger.cmbMas_SL_type.selectedIndex].text;
    document.frmSubsidiaryLedger.txtsubledgerdesc.value="";
    document.frmSubsidiaryLedger.txtsubledgerdesc.value=levelt;
}

//////////////   FOR DEPUTATION JOB POPUP WINDOW //////////////////////
function jobpopup_master()
{
    job_flag=true;
    jobpopup();
}

function jobpopup_trans()
{
    job_flag=false;
    jobpopup();
}

var winjob;
var wincredit;
var windebit;
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

function creditdetails()
{
    if(wincredit && wincredit.open && !wincredit.closed) 
    {
       wincredit.resizeTo(500,500);
       wincredit.moveTo(250,250); 
       wincredit.focus();
    }
    else
    {
        wincredit=null
    }
        
    wincredit= window.open("../../../../../org/FAS/FAS1/MonthEnd/jsps/ListAllCredit.jsp","ListAllCredit","status=1,height=500,width=500,resizable=YES, scrollbars=yes"); 
    wincredit.moveTo(250,250);  
    wincredit.focus();
}

function debitdetails()
{
    if(windebit && windebit.open && !windebit.closed) 
    {
       windebit.resizeTo(500,500);
       windebit.moveTo(250,250); 
       windebit.focus();
    }
    else
    {
        windebit=null
    }
        
    windebit= window.open("../../../../../org/FAS/FAS1/MonthEnd/jsps/ListAllDebit.jsp","ListAllDebit","status=1,height=500,width=500,resizable=YES, scrollbars=yes"); 
    windebit.moveTo(250,250);  
    windebit.focus();
}
window.onunload=function()
{
if (winjob && winjob.open && !winjob.closed) winjob.close();
if (wincredit && wincredit.open && !wincredit.closed) wincredit.close();
if (windebit && windebit.open && !windebit.closed) windebit.close();
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
     alert("Office Id '"+oid+"' doesn't Exist");
    }
    job_flag="";
}
