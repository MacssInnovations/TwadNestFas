
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
 *  Null Check Validation 
 */
function nullcheck()
{
    if((document.frmReport.cmbAcc_UnitCode.value=="") || (document.frmReport.cmbAcc_UnitCode.value.length<=0) || (document.frmReport.cmbAcc_UnitCode.value=="0"))
    {
        alert("Please Select Accounting Unit");
        document.frmReport.cmbAcc_UnitCode.focus();
        return false;
    }
    if((document.frmReport.cmbOffice_code.value=="") || (document.frmReport.cmbOffice_code.value.length<=0) || (document.frmReport.cmbOffice_code.value=="0"))
    {
        alert("Please Select Accounting Office Code");
        document.frmReport.cmbOffice_code.focus();
        return false;
    
    }
    if((document.frmReport.cmbAccHeadCode.value=="") || (document.frmReport.cmbAccHeadCode.value.length<=0) || (document.frmReport.cmbAccHeadCode.value=="0"))
    {
        alert("Please Select Account Head Code");
        document.frmReport.cmbAccHeadCode.focus();
        return false;
    }
    var txtCB_Year=document.getElementById("txtCB_Year").value;
    var txtCB_Month=document.getElementById("txtCB_Month").value;
    
    if(txtCB_Year.length!=4 || txtCB_Month.length==0)
    {
        alert("Specify the year(4 digit) and month");
        return false;
    }
  
  return true;
}




var winAccHeadCode;
function AccHeadpopup()
{
    var MajorGrp=document.frmReport.Major_Grp.value;
    var MinorGrp=document.frmReport.Minor_Grp.value;
    var AccHead=document.frmReport.cmbAccHeadCode.value
    var url="../../../../../../Acc_Head_Dir_List_InUse.view?Command=MajorMinor&MajorGroup="+MajorGrp+"&MinorGroup="+MinorGrp+"&AHCode="+AHcode;
  
    var req=getTransport();
    req.open("GET",url,true); 
    req.onreadystatechange=function()
    {
       handleResponse(req);
    }   
       req.send(null);    
}




function doParentAccHead(code)
{
   document.frmReport.cmbAccHeadCode.value=code;
   doFunction('checkCode','null');
   return true;
}
window.onunload=function()
{
   if (winAccHeadCode && winAccHeadCode.open && !winAccHeadCode.closed) winAccHeadCode.close();
}



/**
 *  Number Checking 
 */
function numbersonly(e)
{
        var unicode=e.charCode? e.charCode : e.keyCode;
       if(unicode==13)
        {
        
        }
        if (unicode!=8 && unicode !=9  )
        {
            if (unicode<48 || unicode>57 ) 
                return false 
        }
}
     
     

/**
 *  Load Minor Group Heads 
 */
function loadingMinor(Command)
{
    if(Command=="loadMinor")
        { 
            var txtMajor_id=document.getElementById("Major_Grp").value;
            var url="../../../../../../Acc_Head_Dir_List_InUse.view?Command=loadMinor&txtMajor_id="+txtMajor_id;          
            var req=getTransport();
            req.open("POST",url,true); 
            req.onreadystatechange=function()
            {
               handleResponse(req);
            }   
                    req.send(null);
                    
        }
}



/**
 *  Load Account Head Code corresponding to Major and Minor group 
 */

function searchByMajorMinor()
{  
    var MajorGrp=document.frmReport.Major_Grp.value;
    var MinorGrp=document.frmReport.Minor_Grp.value;
    var AHCode=document.frmReport.cmbAccHeadCode.value
    var url="../../../../../../Acc_Head_Dir_List_InUse.view?Command=MajorMinor&MajorGroup="+MajorGrp+"&MinorGroup="+MinorGrp;
    var req=getTransport();
    req.open("GET",url,true); 
            req.onreadystatechange=function()
            {
               handleResponse(req);
            }   
            req.send(null);
}     



/** 
 *  Response Handle Function 
 */
function handleResponse(req)
{  
    if(req.readyState==4)
    {
        if(req.status==200)
        {   
            var baseResponse=req.responseXML.getElementsByTagName("response")[0];
            var tagcommand=baseResponse.getElementsByTagName("command")[0];
            var Command=tagcommand.firstChild.nodeValue;
            if(Command=="loadMinor")
            {
                loadMinor(baseResponse);
            }
            else if(Command=="MajorMinor")
            {
                loadAcHode(baseResponse)
            }
            else if(Command=="AHcode")
           {
            loadAcHode(baseResponse);
           }
      }
   } 
}



/**
 *   Load Minor Account Heads 
 */

function loadMinor(baseResponse)
{

var flag=baseResponse.getElementsByTagName("flag")[0].firstChild.nodeValue;
    
    if(flag=="success")
    {   
        var Maj_id=baseResponse.getElementsByTagName("Maj_id");
        var items_maj=new Array();
        var items_min=new Array();
        var items_desc=new Array();
        var min_id=document.getElementById("Minor_Grp");
        
        
        for(var k=0;k<Maj_id.length;k++)
        {
             items_maj[k]=baseResponse.getElementsByTagName("Maj_id")[k].firstChild.nodeValue;   
             items_min[k]=baseResponse.getElementsByTagName("Min_id")[k].firstChild.nodeValue;
             items_desc[k]=baseResponse.getElementsByTagName("Min_desc")[k].firstChild.nodeValue;
        }
        min_id.innerHTML="";
        var option=document.createElement("OPTION");
        option.text="-------- All ---------";
        option.value="-100";
        try
        {
            min_id.add(option);
        }catch(errorObject)
        {
            min_id.add(option,null);
        }
        
        for(var k=0;k<Maj_id.length;k++)
        {   
              var option=document.createElement("OPTION");
              option.text=items_desc[k];
              option.value=items_min[k];
               try
              {
                  min_id.add(option);
              }
              catch(errorObject)
              {
                  min_id.add(option,null);
              }
        }
    }
}  



/**
 * Load Account Head code -- Result 
 */
function loadAcHode(baseResponse)
{ 
    var flag=baseResponse.getElementsByTagName("flag")[0].firstChild.nodeValue;
    
    if(flag=="success")
    {   
        var Maj_id=baseResponse.getElementsByTagName("Maj_id");
        var items_maj=new Array();
        var items_min=new Array();
        var items_desc=new Array();
        var items_acthead=new Array();
        var items_actdesc=new Array();
        var min_id=document.getElementById("Minor_Grp");
        var act_code=document.getElementById("cmbAccHeadCode");
        for(var k=0;k<Maj_id.length;k++)
        {
             items_acthead[k]=baseResponse.getElementsByTagName("AHCode")[k].firstChild.nodeValue;
             items_actdesc[k]=baseResponse.getElementsByTagName("AHDesc")[k].firstChild.nodeValue
        }
        act_code.innerHTML="";
        var option=document.createElement("OPTION");
        option.text="-------- All ---------";
        option.value="-100";
        try
        {
            act_code.add(option);
        }catch(errorObject)
        {
            act_code.add(option,null);
        }
        
        for(var k=0;k<Maj_id.length;k++)
        {   
              var option=document.createElement("OPTION");
              option.text=items_actdesc[k];
              option.value=items_acthead[k];
               try
              {
                  act_code.add(option);
              }
              catch(errorObject)
              {
                  act_code.add(option,null);
              }
        }
    }
}  

