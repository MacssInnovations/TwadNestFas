
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
///////////////////////////////////////

/*function mas_employee(emp_id)
{
     emp_flag=true;
     doFunction('Load_MasterSL_Code',document.getElementById("cmbMas_SL_type").value);
}*/

/////////////////sub-ledger//////////////////////////////////
function trs_employee(emp_id)
{
     emp_flag=false;
     doFunction('Load_SL_Code',document.getElementById("cmbSL_type").value);
}

function employee_popup_master()
{
    emp_flag=true;
    servicepopup();
}

function employee_popup_trans()
{
    emp_flag=false;
    servicepopup();
}

var winemp;

function servicepopup()
{
    if (winemp && winemp.open && !winemp.closed) 
    {
       winemp.resizeTo(500,500);
       winemp.moveTo(250,250); 
       winemp.focus();
    }
    else
    {
        winemp=null
    }
        
    winemp= window.open("../../../../../org/HR/HR1/EmployeeMaster/jsps/EmpServicePopup.jsp","mywindow1","status=1,height=500,width=500,resizable=YES, scrollbars=yes"); 
    winemp.moveTo(250,250);  
    winemp.focus();
    
}

function doParentEmp(emp)
{
      /* if(emp_flag==true)
        {
            document.getElementById("txtEmpID_mas").value=emp;
            doFunction('Load_MasterSL_Code',document.getElementById("cmbMas_SL_type").value);
        }*/
        
        ///////////////sub ledger code///////////////////
         if(emp_flag==false)
         {
            document.getElementById("txtEmpID_trs").value=emp;
            doFunction('Load_SL_Code',document.getElementById("cmbSL_type").value);
        }
}

/////////////////////////////////////////////   AccHeadpopup  /////////////////////////////////////////////////////
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
   document.getElementById("txtAcc_HeadCode").value=code;
   doFunction('checkCode',true);
   return true;
}

//////////////   FOR DEPUTATION JOB POPUP WINDOW //////////////////////
/*function mas_office(off_id)
{
     job_flag=true;
     doFunction('Load_MasterSL_Code',document.getElementById("cmbMas_SL_type").value);
}*/
function trs_office(off_id)
{
     job_flag=false;
     doFunction('Load_SL_Code',document.getElementById("cmbSL_type").value);
}

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
            
           /* if(job_flag==true)
            {
                document.getElementById("txtOfficeID_mas").value=jobid;
                doFunction('Load_MasterSL_Code',document.getElementById("cmbMas_SL_type").value);
            }*/
            if(job_flag==false)
             {
                document.getElementById("txtOfficeID_trs").value=jobid;
                doFunction('Load_SL_Code',document.getElementById("cmbSL_type").value);
            }
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
                return false;
        }
   
    return true
}

