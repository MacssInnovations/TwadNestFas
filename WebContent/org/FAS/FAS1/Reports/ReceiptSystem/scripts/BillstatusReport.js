
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
    
    var txtCB_Year=document.getElementById("txtCB_Year").value;
    var txtCB_Month=document.getElementById("txtCB_Month").value;
    
    if(txtCB_Year.length!=4 || txtCB_Month.length==0)
    {
        alert("Specify the year(4 digit) and month");
        return false;
    }if(document.frmReport.Major_Grp.value=="select")
    		{
    	 alert("Please Select Major Type code");
         return false;
    		}
   /* if(document.frmReport.Minor_Grp.value=="select")
    		{
    	 alert("Please Select Minor Type code");
         return false;
    		}
    if(document.frmReport.sub_type.value=="select")
    		{
    	 alert("Please Select Sub Type code");
         return false;
    		}*/
  
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
function loadingMinor(Command,path)

{
 
	
	if(Command=="loadMinor")
        { 
            var txtMajor_id=document.getElementById("Major_Grp").value;
            var url=path+"/BillStatusReport1?Command=loadMinor&txtMajor_id="+txtMajor_id;          
          
            var req=getTransport();
            req.open("POST",url,true); 
            req.onreadystatechange=function()
            {
               handleResponse(req);
            }; 
                    req.send(null);
                    
        }
}
function loadingSub(Command)

{

	if(Command=="loadSub")
        { 
            var txtMinor_id=document.getElementById("Minor_Grp").value;
            var url="../../../../../../BillStatusReport1?Command=loadSub&txtMinor_id="+txtMinor_id;          
       
            var req=getTransport();
            req.open("POST",url,true); 
            req.onreadystatechange=function()
            {
               handleResponse(req);
            }; 
                    req.send(null);
                    
        }
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
        	//alert(req.status);
            var baseResponse=req.responseXML.getElementsByTagName("response")[0];
            var tagcommand=baseResponse.getElementsByTagName("command")[0];
            var Command=tagcommand.firstChild.nodeValue;
            if(Command=="loadMinor")
            {
                loadMinor(baseResponse);
            }
            else if(Command=="loadSub")
           {
            loadSub(baseResponse);
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
        //alert("Maj_id"+Maj_id)
        var items_maj=new Array();
        var items_min=new Array();
        var items_desc=new Array();
        var min_id=document.getElementById("Minor_Grp");
        
        //alert(Maj_id.length);
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
       // alert(Maj_id.length);
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




function loadSub(baseResponse)
{

var flag=baseResponse.getElementsByTagName("flag")[0].firstChild.nodeValue;
    
    if(flag=="success")
    {  
    	
        var Min_id=baseResponse.getElementsByTagName("Min_id");
       // alert("Min_id"+Min_id);
        var items_maj=new Array();
        var items_min=new Array();
        var items_desc=new Array();
        var sub_id=document.getElementById("sub_type");
        
        
        for(var k=0;k<Min_id.length;k++)
        {
             items_maj[k]=baseResponse.getElementsByTagName("Maj_id")[k].firstChild.nodeValue;   
             items_min[k]=baseResponse.getElementsByTagName("Min_id")[k].firstChild.nodeValue;
             items_min[k]=baseResponse.getElementsByTagName("Sub_id")[k].firstChild.nodeValue;
             items_desc[k]=baseResponse.getElementsByTagName("Min_desc")[k].firstChild.nodeValue;
        }
        sub_id.innerHTML="";
        var option=document.createElement("OPTION");
        option.text="-------- All ---------";
        option.value="-100";
        try
        {
        	sub_id.add(option);
        }catch(errorObject)
        {
        	sub_id.add(option,null);
        }
        
        for(var k=0;k<Min_id.length;k++)
        {   
              var option=document.createElement("OPTION");
              option.text=items_desc[k];
              option.value=items_min[k];
               try
              {
            	   sub_id.add(option);
              }
              catch(errorObject)
              {
            	  sub_id.add(option,null);
              }
        }
    }
}  

















