//alert('comes here js2')
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

        if(document.getElementById("txtVoucherNo").value=="")
        {
            alert("Select the Voucher Number");
             return false;    
        }
        if(document.getElementById("txtLetNo").value.length==0||document.getElementById("let_no_hid").value=="")
        {
            alert("Enter the Reference Number in the General Part");
             return false;    
        }
        if(document.getElementById("txtLetterDate").value.length==0||document.getElementById("let_date_hid").value=="")
        {
            alert("Enter the Reference Date in the General Part");
            
            return false;
        }
        if(document.getElementById("let_no_hid").value.length==0||document.getElementById("let_no_hid").value=="")
        {
            alert("Enter the Letter Number");
            //document.getElementById("txtCrea_date").focus();
             //showCombo();
            return false;    
        }
        if(document.getElementById("let_date_hid").value.length==0||document.getElementById("let_date_hid").value=="")
        {
            alert("Enter the Letter Date");
             //showCombo();
            //document.getElementById("txtCash_Acc_code").focus();
            return false;
        }
        
        if(document.getElementById("req_amt_hid").value.length==0||document.getElementById("req_amt_hid").value=="")
        {
            alert("Enter the requested amount");
            // showCombo();
            //document.getElementById("txtRecei_from").focus();
            return false;    
        }      
           
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
            
                 
              var remarkshid=document.createElement("input");
                  remarkshid.type="hidden";
                  remarkshid.name="remarks_hid";
                  remarkshid.value=remarks;
                  cell2.appendChild(remarkshid);
                  
                   var currentText=document.createTextNode(worktype);
                  cell2.appendChild(currentText);
                mycurrent_row.appendChild(cell2);
            
            
                    tbody.appendChild(mycurrent_row);
                  //  alert("worktype"+worktype);
  document.getElementById("LetNo").value="";
  document.getElementById("LetterDate").value="";
  document.getElementById("reqAmt").value="";
  document.getElementById("alotAmt").value="";
  document.getElementById("worktype").value="";
  
}


}

function loadVoucher()
{
//alert("aaa");
var year=document.frmFund_Allotment_Details.txtCB_Year.value;
//alert(year);
var month=document.frmFund_Allotment_Details.txtCB_Month.value;
//alert(month);
var accunitid=document.frmFund_Allotment_Details.cmbAcc_UnitCode.value;
var offid=document.frmFund_Allotment_Details.cmbOffice_code.value;
//alert(offid)
var url="../../../../../FundAllotmentServ2?Command=loadVoucher&accunit="+accunitid+"&txtyear="+year+"&txtmonth="+month+"&officeId="+offid;
          //  alert(url);
            var req=getTransport();
            req.open("GET",url,true); 
            req.onreadystatechange=function()
            {
               handleResponse(req);
            }   
                    req.send(null);
                   
}

function loadDivision()
{
//alert("aaa");
var tbody=document.getElementById("grid_body");
try
{
tbody.innerHTML="";
}
catch(e)
{
tbody.innerText="";
}
document.getElementById("grid").style.display='block';

var year=document.frmFund_Allotment_Details.txtCB_Year.value;
//alert(year);
var month=document.frmFund_Allotment_Details.txtCB_Month.value;
//alert(month);
var accunitid=document.frmFund_Allotment_Details.cmbAcc_UnitCode.value;
//alert(accunitid);
//var divname=document.frmFund_Allotment_Details.txtDivname.value;
//alert(divname);
var offid=document.frmFund_Allotment_Details.cmbOffice_code.value;
//alert(offid)
var vno=document.frmFund_Allotment_Details.txtVoucherNo.value;
//alert(vno)
var url="../../../../../FundAllotmentServ2?Command=loadDivision&accunit="+accunitid+"&txtyear="+year+"&txtmonth="+month+"&officeId="+offid+"&voucherno="+vno;
         //   alert(url);
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
           
            if(Command=="loadVoucher")
            {
                loadVNo(baseResponse);
            }
            else if(Command=="loadDivision")
            {
                loadDvn(baseResponse);
            }
            else if(Command=="loadLetNo")
            {
                LoadLetterNo(baseResponse);
            }
           
        }
    }
}

function loadDvn(baseResponse)
{
var seq=0;
var flag=baseResponse.getElementsByTagName("flag")[0].firstChild.nodeValue;
//alert(flag)
    if(flag=="success")
    {
   // var cmbdivname=document.getElementById("txtDivname");
         
         var items_id=new Array();
         var transamt=new Array();
         var fundtype_id=new Array();
         var items_name=new Array();
         var fundtype_name=new Array();
         
         
         var transofficeid=baseResponse.getElementsByTagName("tranOffice");
         for(var k=0;k<transofficeid.length;k++)
            {
                items_id[k]=baseResponse.getElementsByTagName("tranOffice")[k].firstChild.nodeValue;
                fundtype_id[k]=baseResponse.getElementsByTagName("fundtypeid")[k].firstChild.nodeValue;
                items_name[k]=baseResponse.getElementsByTagName("tranOfficeName")[k].firstChild.nodeValue;
                transamt[k]=baseResponse.getElementsByTagName("transAmt")[k].firstChild.nodeValue;
                fundtype_name[k]=baseResponse.getElementsByTagName("fundtype")[k].firstChild.nodeValue;
            }
             
         //  clear_Combo(cmbdivname);
            
           for(var k=0;k<transofficeid.length;k++)
            {   
                
   var table=document.getElementById("mytable");
var tbody=document.getElementById("grid_body");
var mycurrent_row=document.createElement("TR");
       
       seq=seq+1;
       
        var i=0;
        var cell2;
                cell2=document.createElement("TD");
                  var slnotxt=document.createTextNode(seq);
                  cell2.appendChild(slnotxt);
                  var slno=document.createElement("input");
                  slno.type="hidden";
                  slno.name="sl_no_hid";
                  slno.readOnly=true;
                  slno.size=4;
                  slno.value=seq;
                  cell2.appendChild(slno);
                  
                   mycurrent_row.appendChild(cell2);
             //alert('first td'+(slno.value))
             
                 cell2=document.createElement("TD");
                 var trnoffid=document.createElement("input");
                  trnoffid.type="hidden";
                  trnoffid.name="trans_off_hid";
                  //trnoffid.size=10;
                  trnoffid.readOnly=true;
                  trnoffid.align="center";
                  trnoffid.value=items_id[k];
                  cell2.appendChild(trnoffid);  
                    
                  var trnoffname=document.createTextNode(items_name[k]);
                  
                  cell2.appendChild(trnoffname);     
               
                   mycurrent_row.appendChild(cell2);
                   
                  cell2=document.createElement("TD");
                  var letnumhid=document.createElement("input");
                  letnumhid.type="text";
                  letnumhid.name="let_no_hid";
                  letnumhid.size=15;
                  letnumhid.value="";
                  cell2.appendChild(letnumhid);
                  mycurrent_row.appendChild(cell2);
                 cell2=document.createElement("TD");
                   var letdatehid="";
                   if (window.navigator.appName.toLowerCase().indexOf("netscape") == -1)
                   {
                   letdatehid=document.createElement("<input type='text' name='let_date_hid' size='10' value='' onkeypress='return calins(event,this)' onblur='return checkdt(this)'>");
                   }
                   else
                   {
                   var letdatehid=document.createElement("input");
                  letdatehid.type="text";
                  letdatehid.name="let_date_hid";
                  letdatehid.size=10;
                  letdatehid.setAttribute('onkeypress','return calins(event,this)');
                  
                  letdatehid.value="";
                  }
                  cell2.appendChild(letdatehid);
                  mycurrent_row.appendChild(cell2);
                  //--------------------------------------
                  cell2=document.createElement("TD");
                  var offletnumhid=document.createElement("input");
                  offletnumhid.type="text";
                  offletnumhid.name="off_let_no_hid";
                  offletnumhid.size=15;
                  offletnumhid.value="";
                  cell2.appendChild(offletnumhid);
                  mycurrent_row.appendChild(cell2);
                 cell2=document.createElement("TD");
                   var offletdatehid="";
                   if (window.navigator.appName.toLowerCase().indexOf("netscape") == -1)
                   {
                   offletdatehid=document.createElement("<input type='text' name='off_let_date_hid' size='10' value='' onkeypress='return calins(event,this)' onblur='return checkdt(this)'>");
                   }
                   else
                   {
                   var offletdatehid=document.createElement("input");
                  offletdatehid.type="text";
                  offletdatehid.name="off_let_date_hid";
                  offletdatehid.size=10;
                  offletdatehid.setAttribute('onkeypress','return calins(event,this)');
                  
                  offletdatehid.value="";
                  }
                  cell2.appendChild(offletdatehid);
                  mycurrent_row.appendChild(cell2);
                  
                  //--------------------------------------
                  
                  cell2=document.createElement("TD");
             
                 var fundtypeid=document.createElement("input");
                  fundtypeid.type="hidden";
                  fundtypeid.name="fundtype_hid";
                  //trnoffid.size=10;
                  fundtypeid.readOnly=true;
                  fundtypeid.align="center";
                  fundtypeid.value=fundtype_id[k];
                  cell2.appendChild(fundtypeid);  
                        
                  var funtypename=document.createTextNode(fundtype_name[k]);
                 
                  cell2.appendChild(funtypename);     
                 
                   mycurrent_row.appendChild(cell2);
                  
                 var reqamthid = "";
             // var reqamthid=document.createTextNode(transamt[k]);
              cell2=document.createElement("TD"); 
            //   var reqamthidtxt=document.createTextNode(transamt[k]);
            //   cell2.appendChild(reqamthidtxt); 
               if (window.navigator.appName.toLowerCase().indexOf("netscape") == -1)
                   {
                   reqamthid=document.createElement("<input type='text' name='req_amt_hid' size='15' value='"+transamt[k]+"' onkeypress='return numbersonly(event)'>");
                  // alert("reqamthid"+reqamthid.value);
                   }
                   else
                   {
                  reqamthid=document.createElement("input");
                  reqamthid.type="text";
                  reqamthid.name="req_amt_hid";
                  reqamthid.size=15;
                  reqamthid.align='right';
                  reqamthid.value=transamt[k];
                 // alert(reqamthid.value);
                  reqamthid.setAttribute('onkeypress','return numbersonly(event)');
                  
                  }
                  cell2.appendChild(reqamthid);    
                //   var currentText=document.createTextNode(reqamt);
                // cell2.appendChild(currentText);
                mycurrent_row.appendChild(cell2);
             
             cell2=document.createElement("TD");
             var alotamthidtxt=document.createTextNode(transamt[k]);
              cell2.appendChild(alotamthidtxt); 
              var alotamthid=document.createElement("input");
                  alotamthid.type="hidden";
                  alotamthid.name="alot_amt_hid";
                  reqamthid.align='right';
                  alotamthid.size=15;
                  alotamthid.readOnly=true;
                  alotamthid.value=transamt[k];
                  //alert(alotamthid.value);
                  cell2.appendChild(alotamthid);     
                 //  var currentText=document.createTextNode(alotamt);
                //  cell2.appendChild(currentText);
                   mycurrent_row.appendChild(cell2);
                   var reasonhid="";
              cell2=document.createElement("TD");
               if (window.navigator.appName.toLowerCase().indexOf("netscape") == -1){
                  reasonhid=document.createElement("<textarea name='reason_hid' cols='20' rows='2'></textarea>");
                  }
                  else
                  {
                  reasonhid=document.createElement("input");
                  reasonhid.type="text";
                  reasonhid.name="reason_hid";
                  reasonhid.align="right";
                  reasonhid.value="";
                  }
                  cell2.appendChild(reasonhid);
                  mycurrent_row.appendChild(cell2);
            
                    tbody.appendChild(mycurrent_row);
      
            }
            
            var lettno=baseResponse.getElementsByTagName("LetterNo")[0].firstChild.nodeValue;
    //alert(lettno);
    var lettdate=baseResponse.getElementsByTagName("LetterDate")[0].firstChild.nodeValue;
    //var transamt=baseResponse.getElementsByTagName("transAmt")[0].firstChild.nodeValue;
     if(lettno=='null')
        document.getElementById("txtLetNo").value="";
    else
        document.getElementById("txtLetNo").value=lettno;
    if(lettdate=='null')
        document.getElementById("txtLetterDate").value="";
    else    
        document.getElementById("txtLetterDate").value=lettdate;
   
    }
    else if(flag=="AlreadyExist")
    {
        alert("Fund Allotment Details Already Exist");
        document.getElementById("txtVoucherNo").focus();
        return false;
        document.getElementById("butSub").style.display='block';
    }
    else if(flag=="failure")
    {
        alert("No data found");
         document.getElementById("txtLetNo").value="";
         document.getElementById("txtLetterDate").value="";
    }
}


function loadVNo(baseResponse)
{

var flag=baseResponse.getElementsByTagName("flag")[0].firstChild.nodeValue;
//alert(flag)
    if(flag=="success")
    {
    var cmbdivname=document.getElementById("txtVoucherNo");
         
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
         document.getElementById("txtLetNo").value="";
         document.getElementById("txtLetterDate").value="";
    }
}

function numbersonly(e)
{
//alert('comes here ')
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
 
function loadLetNo()
{
var year=document.frmFund_Allotment_Details.txtCB_Year.value;
var month=document.frmFund_Allotment_Details.txtCB_Month.value;
var accunitid=document.frmFund_Allotment_Details.cmbAcc_UnitCode.value;
//alert(accunitid);
var divname=document.frmFund_Allotment_Details.txtDivname.value;
//alert(divname);
var officeid=document.frmFund_Allotment_Details.cmbOffice_code.value;
//alert(officeid);
var vno=document.frmFund_Allotment_Details.txtVoucherNo.value;
//alert(vno)
var url="../../../../../FundAllotmentServ2?Command=loadLetNo&txtyear="+year+"&txtmonth="+month+"&diviname="+divname+"&accunit="+accunitid+"&office_id="+officeid+"&voucherno="+vno;
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
    var fundtype=baseResponse.getElementsByTagName("fundtype")[0].firstChild.nodeValue;
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
   
   //alert('ok')
   var table=document.getElementById("mytable");
var tbody=document.getElementById("grid_body");
var mycurrent_row=document.createElement("TR");
       
       seq=seq+1;
       
        var i=0;
        var cell2;
                cell2=document.createElement("TD");
                 var slno=document.createElement("input");
                  slno.type="text";
                  slno.name="sl_no_hid";
                  slno.value=seq;
                  cell2.appendChild(slno);
                 mycurrent_row.appendChild(cell2);
                      
                  cell2=document.createElement("TD");
                  var letnumhid=document.createElement("input");
                  letnumhid.type="text";
                  letnumhid.name="let_no_hid";
                  letnumhid.value="";
                  cell2.appendChild(letnumhid);
                  mycurrent_row.appendChild(cell2);
                  
                   cell2=document.createElement("TD");
                   var letdatehid=document.createElement("input");
                  letdatehid.type="text";
                  letdatehid.name="let_date_hid";
                  letdatehid.value="";
                  cell2.appendChild(letdatehid);
                 
                   mycurrent_row.appendChild(cell2);
                   
                    cell2=document.createElement("TD");
                   var letdatehid=document.createElement("select");
                 // letdatehid.type="text";
                  letdatehid.name="let_work_hid";
                  //letdatehid.value="";
                  
                  letdatehid.innerHTML="";
        var option=document.createElement("OPTION");
        option.text="--Select Work Type--";
        option.value="";
        try
        {
            letdatehid.add(option);
        }catch(errorObject)
        {
            letdatehid.add(option,null);
        }
        var option=document.createElement("OPTION");
        option.text="Civil";
        option.value="C";
        try
        {
            letdatehid.add(option);
        }catch(errorObject)
        {
            letdatehid.add(option,null);
        }
        var option=document.createElement("OPTION");
        option.text="Work";
        option.value="W";
        try
        {
            letdatehid.add(option);
        }catch(errorObject)
        {
            letdatehid.add(option,null);
        }
                 cell2.appendChild(letdatehid);
                  
                  mycurrent_row.appendChild(cell2);
                   
                   
             cell2=document.createElement("TD"); 
             var reqamthid=document.createElement("input");
                  reqamthid.type="text";
                  reqamthid.name="req_amt_hid";
                  reqamthid.value="";
                  cell2.appendChild(reqamthid);    
                  // var currentText=document.createTextNode(reqamt);
                  //cell2.appendChild(currentText);
                mycurrent_row.appendChild(cell2);
             
             cell2=document.createElement("TD");
              var alotamthid=document.createElement("input");
                  alotamthid.type="text";
                  alotamthid.name="alot_amt_hid";
                  alotamthid.value=transamt;
                  //alert(alotamthid.value);
                  cell2.appendChild(alotamthid);     
                 //  var currentText=document.createTextNode(alotamt);
                //  cell2.appendChild(currentText);
                   mycurrent_row.appendChild(cell2);
         
            
                    tbody.appendChild(mycurrent_row);
   
   
    }
    else if(flag=="failure")
    {
        alert("No data found");
        
    }
}


function clrForm()
{
document.frmFund_Allotment_Details.txtLetNo.value="";
document.frmFund_Allotment_Details.txtLetterDate.value="";
document.frmFund_Allotment_Details.txtRemarks.value="";

document.getElementById("grid").style.display='none';

}


function loadVoucherNo()
{
//alert('loadVoucherNo');
var year=document.frmFund_Allotment_Details.txtCB_Year.value;
var month=document.frmFund_Allotment_Details.txtCB_Month.value;
var accunitid=document.frmFund_Allotment_Details.cmbAcc_UnitCode.value;
//alert(accunitid);
var divname=document.frmFund_Allotment_Details.txtDivname.value;
//alert(divname);
var officeid=document.frmFund_Allotment_Details.cmbOffice_code.value;
//alert(officeid);
var url="../../../../../FundAllotmentServ2?Command=loadVoucherNo&txtyear="+year+"&txtmonth="+month+"&diviname="+divname+"&accunit="+accunitid+"&office_id="+officeid;
            //alert(url);
            var req=getTransport();
            req.open("GET",url,true); 
            req.onreadystatechange=function()
            {
               handleResponse(req);
            }   
                    req.send(null);
}

