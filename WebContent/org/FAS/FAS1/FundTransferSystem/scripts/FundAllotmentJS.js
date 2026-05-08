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
        
      /*  if(document.getElementById("txtRemarks").value.length==0)
        {
            alert("Enter remarks ");
            // showCombo();
            //document.getElementById("txtAmount").focus();
            return false;    
        }*/
        
                           
                
         return true;           
                
                
}

function checkGrid()
{
var tbody=document.getElementById("grid_body");
           //alert("tbody.rows.length :"+tbody.rows.length);   
 if(tbody.rows.length==0)
        {
            alert("Enter the Details Part");
            //document.getElementById("txtAmount").focus();
            return false; 
        }
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

/*function showCombo()
{
var cmbid=document.getElementById("worktype");
    cmbid.innerHTML="";
        var option=document.createElement("OPTION");
        option.text="--Select Work Type--";
        option.value="";
        try
        {
            cmbid.add(option);
        }catch(errorObject)
        {
            cmbid.add(option,null);
        }
        var option=document.createElement("OPTION");
        option.text="Civil";
        option.value="C";
        try
        {
            cmbid.add(option);
        }catch(errorObject)
        {
            cmbid.add(option,null);
        }
        var option=document.createElement("OPTION");
        option.text="Work";
        option.value="W";
        try
        {
            cmbid.add(option);
        }catch(errorObject)
        {
            cmbid.add(option,null);
        }
}*/


function showGrid()
{
if(!checkNull())
     {
     //alert("Enter all data");     
     //clear_Combo(document.getElementById("worktype"));
     document.getElementById("worktype").value="";
     }
else   
{

//alert("show");
var divname=document.getElementById("txtDivname").value;
//alert(divname);
var letno=document.getElementById("LetNo").value;
//alert(letno)
var letdate=document.getElementById("LetterDate").value;
//alert(letdate)
var reqamt=document.getElementById("reqAmt").value;
//alert(reqamt);
var alotamt=document.getElementById("alotAmt").value;
//alert(alotamt);
var worktype=document.getElementById("worktype").value;
//alert(worktype);
var remarks=document.getElementById("txtRemarks").value;
//alert(remarks);


var table=document.getElementById("mytable");
var tbody=document.getElementById("grid_body");
var mycurrent_row=document.createElement("TR");
       
       seq=seq+1;
       // mycurrent_row.id=seq;
        //alert("row ID"+mycurrent_row.id);
       
        var i=0;
        var cell2;
                cell2=document.createElement("TD");
                 var slno=document.createElement("input");
                  slno.type="hidden";
                  slno.name="sl_no_hid";
                  slno.value=seq;
                  cell2.appendChild(slno);
                   var currentText=document.createTextNode(seq);
                   cell2.appendChild(currentText);
                   mycurrent_row.appendChild(cell2);
             
                  cell2=document.createElement("TD");
                  var letnumhid=document.createElement("input");
                  letnumhid.type="hidden";
                  letnumhid.name="let_no_hid";
                  letnumhid.value=letno;
                  cell2.appendChild(letnumhid);
                  
                  var offidhid=document.createElement("input");
                  offidhid.type="hidden";
                  offidhid.name="officeid_hid";
                  offidhid.value=divname;
                  cell2.appendChild(offidhid);
                  
                  //var currentText=document.createTextNode(letnum);
                   var letdatehid=document.createElement("input");
                  letdatehid.type="hidden";
                  letdatehid.name="let_date_hid";
                  letdatehid.value=letdate;
                  cell2.appendChild(letdatehid);
                  
                  var currentText=document.createTextNode(letno+"-"+letdate);
                  cell2.appendChild(currentText);
                   mycurrent_row.appendChild(cell2);
                   
             cell2=document.createElement("TD"); 
             var reqamthid=document.createElement("input");
                  reqamthid.type="hidden";
                  reqamthid.name="req_amt_hid";
                  reqamthid.value=reqamt;
                  cell2.appendChild(reqamthid);    
                   var currentText=document.createTextNode(reqamt);
                  cell2.appendChild(currentText);
                mycurrent_row.appendChild(cell2);
             
             cell2=document.createElement("TD");
              var alotamthid=document.createElement("input");
                  alotamthid.type="hidden";
                  alotamthid.name="alot_amt_hid";
                  alotamthid.value=alotamt;
                  //alert(alotamthid.value);
                  cell2.appendChild(alotamthid);     
                   var currentText=document.createTextNode(alotamt);
                  cell2.appendChild(currentText);
                   mycurrent_row.appendChild(cell2);
            
             cell2=document.createElement("TD");
              var worktypehid=document.createElement("input");
                  worktypehid.type="hidden";
                  worktypehid.name="work_type_hid";
                  worktypehid.value=worktype;
                  cell2.appendChild(worktypehid);     
              var remarkshid=document.createElement("input");
                  remarkshid.type="hidden";
                  remarkshid.name="remarks_hid";
                  remarkshid.value=remarks;
                  cell2.appendChild(remarkshid);     
                   var currentText=document.createTextNode(worktype);
                  cell2.appendChild(currentText);
                mycurrent_row.appendChild(cell2);
            
            
                    tbody.appendChild(mycurrent_row);
document.getElementById("LetNo").value="";
document.getElementById("LetterDate").value="";
document.getElementById("reqAmt").value="";
document.getElementById("alotAmt").value="";
 document.getElementById("worktype").value="";
  
}


}

function loadDivision()
{
//alert("aaa");
var year=document.frmJournal_General_Edit.txtCB_Year.value;
//alert(year);
var month=document.frmJournal_General_Edit.txtCB_Month.value;
//alert(month);
var accunitid=document.frmJournal_General_Edit.cmbAcc_UnitCode.value;
//alert(accunitid);
var divname=document.frmJournal_General_Edit.txtDivname.value;
//alert(divname);
var url="../../../../../FundAllotmentServ?Command=loadDivision&accunit="+accunitid+"&txtyear="+year+"&txtmonth="+month;
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
            else if(Command=="loadLetNo")
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
         var transofficeid=baseResponse.getElementsByTagName("tranOfficeId");
         for(var k=0;k<transofficeid.length;k++)
            {
                items_id[k]=baseResponse.getElementsByTagName("tranOfficeId")[k].firstChild.nodeValue;
               // items_name[k]=baseResponse.getElementsByTagName("cname")[k].firstChild.nodeValue;
            }
             
           clear_Combo(cmbdivname);
            
           for(var k=0;k<transofficeid.length;k++)
            {   
                  var option=document.createElement("OPTION");
                  option.text=items_id[k];
                  option.value=items_id[k];
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
         document.getElementById("txtLetNo").value="";
         document.getElementById("txtLetterDate").value="";
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
 
function loadLetNo()
{
var year=document.frmJournal_General_Edit.txtCB_Year.value;
var month=document.frmJournal_General_Edit.txtCB_Month.value;
var accunitid=document.frmJournal_General_Edit.cmbAcc_UnitCode.value;
//alert(accunitid);
var divname=document.frmJournal_General_Edit.txtDivname.value;
//alert(divname);
var officeid=document.frmJournal_General_Edit.cmbOffice_code.value;
//alert(officeid);
var url="../../../../../FundAllotmentServ?Command=loadLetNo&txtyear="+year+"&txtmonth="+month+"&diviname="+divname+"&accunit="+accunitid+"&office_id="+officeid;
            //alert(url);
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
//alert(flag);
    if(flag=="success")
    {
    var lettno=baseResponse.getElementsByTagName("LetterNo")[0].firstChild.nodeValue;
    //alert(lettno);
    var lettdate=baseResponse.getElementsByTagName("LetterDate")[0].firstChild.nodeValue;
    var transamt=baseResponse.getElementsByTagName("transAmt")[0].firstChild.nodeValue;
    //alert(transamt);
    //alert(lettdate);
    if(lettno=='null')
        document.getElementById("txtLetNo").value="";
    else
        document.getElementById("txtLetNo").value=lettno;
    if(lettdate=='null')
        document.getElementById("txtLetterDate").value="";
    else    
        document.getElementById("txtLetterDate").value=lettdate;
    document.getElementById("alotAmt").value=transamt;
    }
    else if(flag=="failure")
    {
        alert("No data found");
        
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