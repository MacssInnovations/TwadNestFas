
var com_id;
var common_cmbSL_Code="";
var common_cmbSL_type="";
var seq=0;

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

            


function checkNull()
{
//alert("inside checknull");
      
        
        if(document.getElementById("txtDivname").value=="")
        {
            alert("Select the Division Name");
            //document.getElementById("cmbAcc_UnitCode").focus();
            
             document.getElementById("worktype").value="";
             //showCombo();
            return false;    
        }
       
        if(document.getElementById("LetNo").value.length==0)
        {
            alert("Enter the Letter Number");
            //document.getElementById("txtCrea_date").focus();
             //showCombo();
            return false;    
        }
        if(document.getElementById("LetterDate").value.length==0)
        {
            alert("Enter the Letter Date");
             //showCombo();
            //document.getElementById("txtCash_Acc_code").focus();
            return false;
        }
        
        if(document.getElementById("reqAmt").value.length==0)
        {
            alert("Enter the requested amount");
            // showCombo();
            //document.getElementById("txtRecei_from").focus();
            return false;    
        }      
        
        
       
        if(document.getElementById("alotAmt").value.length==0)
        {
            alert("Enter allotted amount ");
            // showCombo();
            //document.getElementById("txtAmount").focus();
            return false;    
        }
        
        if(document.getElementById("txtRemarks").value.length==0)
        {
            alert("Enter remarks ");
            // showCombo();
            //document.getElementById("txtAmount").focus();
            return false;    
        }
        
                           
                
         return true;           
                
                
}



function clear_Combo(combo)
{
        //alert(combo.id)
        var cmbSL_Code=document.getElementById(combo.id);   
        cmbSL_Code.innerHTML="";
        var option=document.createElement("OPTION");
        option.text="--Select--";
        option.value="";
        try
        {
            cmbSL_Code.add(option);
        }catch(errorObject)
        {
            cmbSL_Code.add(option,null);
        }
}


function loadDivision()
{
//alert("aaa");
var year=document.frmJournal_General_Edit.txtCB_Year.value;
//alert(year);
var month=document.frmJournal_General_Edit.txtCB_Month.value;
///(month);
var accunitid=document.frmJournal_General_Edit.cmbAcc_UnitCode.value;
//alert(accunitid);
var officeId=document.frmJournal_General_Edit.cmbOffice_code.value;
//alert(officeId);
var divname=document.frmJournal_General_Edit.txtDivname.value;
//alert(divname);
var url="../../../../../FundAllotmentServ?Command=loadDivision&accunit="+accunitid+"&officeId="+officeId+"&txtyear="+year+"&txtmonth="+month;
            //alert(url);
            var req=getTransport();
            req.open("GET",url,true); 
            req.onreadystatechange=function()
            {
               handleResponse(req);
            }   
                    req.send(null);
                   
}

function handleResponse(req)
{  
    if(req.readyState==4)
    {
        if(req.status==200)
        {  
            var baseResponse=req.responseXML.getElementsByTagName("response")[0];
            var tagcommand=baseResponse.getElementsByTagName("command")[0];
            var Command=tagcommand.firstChild.nodeValue;
           
            if(Command=="loadDivision")
            {
                loadDivisionName(baseResponse);
            }
            else if(Command=="loadLetNoNew")
            {
                LoadLetterNo(baseResponse);
            }
           
        }
    }
}


function loadDivisionName(baseResponse)
{

var flag=baseResponse.getElementsByTagName("flag")[0].firstChild.nodeValue;

    if(flag=="success")
    {
    var cmbdivname=document.getElementById("txtDivname");
         var items_id=new Array();
         var tot_item = baseResponse.getElementsByTagName("item");
         clear_Combo(cmbdivname);
         for(var index = 0;index < tot_item.length; index++)
            {
                items_id[0] = tot_item[index].getElementsByTagName("tranOfficeId")[0].firstChild.nodeValue;
                items_id[1] = tot_item[index].getElementsByTagName("divname")[0].firstChild.nodeValue;
                var option=document.createElement("OPTION");
                option.text = items_id[1];
                option.value = items_id[0];
                try
                {
                  cmbdivname.add(option);
                }
                catch(errorObject)
                {
                  cmbdivname.add(option,null);
                }
            }    
           
    }
    else if(flag=="failure")
    {
        alert("No data found");
         var cmbdivname=document.getElementById("txtDivname");
         clear_Combo(cmbdivname);
       //  document.getElementById("txtLetNo").value="";
      //   document.getElementById("txtLetterDate").value="";
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
 
 
function loadLetNoNew()
{ 
//alert("first");
var year=document.frmJournal_General_Edit.txtCB_Year.value;
var month=document.frmJournal_General_Edit.txtCB_Month.value;
var accunitid=document.frmJournal_General_Edit.cmbAcc_UnitCode.value;
//alert(accunitid);
var divname=document.frmJournal_General_Edit.txtDivname.value;
//alert(divname);
//var officeid=document.frmJournal_General_Edit.cmbOffice_code.value;
//var newoffice_id=document.getElementById("cmbOffice_code").value;
var officeId=document.frmJournal_General_Edit.txtDivname.value;     
//alert(officeId);
var worktype=document.frmJournal_General_Edit.worktype.value;
//alert(worktype)
var url="../../../../../FundAllotmentServ?Command=loadLetNoNew&txtyear="+year+"&txtmonth="+month+"&diviname="+divname+"&accunit="+accunitid+"&officeId="+officeId+"&worktype="+worktype;
          // alert(url);
            var req=getTransport();
            req.open("GET",url,true); 
            req.onreadystatechange=function()
            {
               handleResponse(req);
            }   
                    req.send(null);
}


function LoadLetterNo(baseResponse)
{
var flag=baseResponse.getElementsByTagName("flag")[0].firstChild.nodeValue;
    if(flag=="success")
    {
    var cmbletno=document.getElementById("cmbLetNo");
         
         var items_id=new Array();
         var letno=baseResponse.getElementsByTagName("LetterNo");
          clear_Combo(cmbletno);
         for(var k=0;k<letno.length;k++)
            {
                items_id[0]=baseResponse.getElementsByTagName("LetterNo")[k].firstChild.nodeValue;
                  var option=document.createElement("OPTION");
                  option.text = items_id[0];
                  option.value = items_id[0];
                   try
                  {
                      cmbletno.add(option);
                  }
                  catch(errorObject)
                  {
                      cmbletno.add(option,null);
                  }
            }
            
    }
    else if(flag=="failure")
    {
        alert("No data found");
         var cmbletno=document.getElementById("cmbLetNo");
         clear_Combo(cmbletno);
         document.getElementById("cmbLetNo").value="";
       //  document.getElementById("txtLetterDate").value="";
    }
    
}


function clrForm()
{
document.frmJournal_General_Edit.txtDivname.value="";
document.frmJournal_General_Edit.txtLetNo.value="";
document.frmJournal_General_Edit.txtLetterDate.value="";
document.frmJournal_General_Edit.txtRemarks.value="";
document.frmJournal_General_Edit.LetNo.value="";
document.frmJournal_General_Edit.LetterDate.value="";
document.frmJournal_General_Edit.reqAmt.value="";
document.frmJournal_General_Edit.alotAmt.value="";
document.frmJournal_General_Edit.worktype.value="";
document.getElementById("grid").style.display='none';

}