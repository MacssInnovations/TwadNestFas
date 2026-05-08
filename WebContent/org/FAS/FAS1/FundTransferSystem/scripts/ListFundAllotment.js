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
var listPopupwindow;
var window_BankAccNumber;
function ListAll()
    {
   // alert("inside listall");
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
        var Acc_UnitCode=document.frmFund_Allotment_Details.cmbAcc_UnitCode.value;
        var OffCode=document.frmFund_Allotment_Details.cmbOffice_code.value;
        var CashbookYear=document.frmFund_Allotment_Details.txtCB_Year.value;
        var CashbookMonth=document.frmFund_Allotment_Details.txtCB_Month.value;
         window_BankAccNumber= window.open("ListFundAllotmentDetails.jsp?cmbAcc_UnitCode="+Acc_UnitCode+"&cmbOffice_code="+OffCode+"&CashbookYear="+CashbookYear+"&CashbookMonth="+CashbookMonth,"mywindow1","resizable=YES, scrollbars=yes"); 
         window_BankAccNumber.moveTo(250,250);  
         window_BankAccNumber.focus();
        // Addfund()
    }

window.onunload=function()
{
if (window_BankAccNumber && window_BankAccNumber.open && !window_BankAccNumber.closed) window_BankAccNumber.close();
}


            


function checkNull()
{
//alert("inside checknull");
      
        
        if(document.getElementById("txtVoucherNo").value=="")
        {
            alert("Select the Voucher Number");
            //document.getElementById("cmbAcc_UnitCode").focus();
            
            // document.getElementById("worktype").value="";
             //showCombo();
            return false;    
        }
        if(document.getElementById("txtLetNo").value.length==0||document.getElementById("let_no_hid").value=="")
        {
            alert("Enter the Reference Number in the General Part");
            //document.getElementById("txtCrea_date").focus();
             //showCombo();
            return false;    
        }
        if(document.getElementById("txtLetterDate").value.length==0||document.getElementById("let_date_hid").value=="")
        {
            alert("Enter the Reference Date in the General Part");
             //showCombo();
            //document.getElementById("txtCash_Acc_code").focus();
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

function Addfund()
{  
var year=document.frmFund_Allotment_Details.txtCB_Year.value;
alert(year);
var month=document.frmFund_Allotment_Details.txtCB_Month.value;
alert(month);
var accunitid=document.frmFund_Allotment_Details.cmbAcc_UnitCode.value;

var offid=document.frmFund_Allotment_Details.cmbOffice_code.value;
alert(offid)
var vno=document.frmFund_Allotment_Details.txtVoucherNo.value;
alert("vno"+vno);
var remarks=document.getElementById("txtRemarks").value;
alert(remarks);
var transOffice=document.getElementById("trans_off_hid").value;
//alert(transOffice);
var transAmt=document.getElementById("alot_amt_hid").value;
//alert(transAmt);
var fundreq=document.getElementById("req_amt_hid").value;
//alert(fundreq);
var LetterDate=document.getElementById("let_date_hid").value;
//alert(LetterDate);
var OffLetterDate=document.getElementById("off_let_date_hid").value;

var fundtypeid=document.getElementById("fund_hid").value;;
//alert(fundtypeid);
var LetterNo=document.getElementById("let_no_hid").value;
//alert(LetterNo);
var OffLetterNo=document.getElementById("off_let_no_hid").value;
//alert(OffLetterNo);
var reason=document.getElementById("reason_hid").value;
//alert("a............................."+reason);
var CheqType=document.frmFund_Allotment_Details.cheq_type_hid.value;
alert(CheqType);
var CheqNo=document.frmFund_Allotment_Details.cheq_no_hid.value;
//alert(CheqNo);
var CheqDate=document.frmFund_Allotment_Details.cheq_date_hid.value;
//alert(CheqDate);
var slno=document.getElementById("sl_no_hid2").value;
var letgen=document.frmFund_Allotment_Details.letter_gen_hid.value;
//alert("slnoaddfund"+slno);
var url="../../../../../ListFundAllotmentSubmitServ2?Command=update&cmbAcc_UnitCode="+accunitid+"&txtCB_Year="+year+"&txtCB_Month="+month+"&cmbOffice_code="+offid+"&txtVoucherNo="+vno+"&remarks="+remarks+"&transOffice="+transOffice+"&transAmt="+transAmt+"&fundreq="+fundreq+"&LetterDate="+LetterDate+"&OffLetterDate="+OffLetterDate+"&tranOffice="+transOffice+"&fundtypeid="+fundtypeid+"&LetterNo="+LetterNo+"&OffLetterNo="+OffLetterNo+"&reason="+reason+"&CheqType="+CheqType+"&CheqNo="+CheqNo+"&CheqDate="+CheqDate+"&slno="+slno+"&letgen="+letgen;
            alert(url);
            var req=getTransport();
             req.open("POST",url,true); 
           
            req.onreadystatechange=function()
            {
               handleResponse(req);
            }   
                    req.send(null);

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
       // mycurrent_row.id=seq;
        //alert("row ID"+mycurrent_row.id);
      // alert(seq)
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
                //    alert("worktype"+worktype);
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
//alert(accunitid);
//var divname=document.frmFund_Allotment_Details.txtDivname.value;
//alert(divname);
var offid=document.frmFund_Allotment_Details.cmbOffice_code.value;
//alert(offid)
var url="../../../../../ListFundAllotmentServ1?Command=loadVoucher&accunit="+accunitid+"&txtyear="+year+"&txtmonth="+month+"&officeId="+offid;
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
var url="../../../../../ListFundAllotmentServ1?Command=loadDivision&accunit="+accunitid+"&txtyear="+year+"&txtmonth="+month+"&officeId="+offid+"&voucherno="+vno;
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
            alert(Command);
            if(Command=="update")
            {
                loadDvn(baseResponse);
            }
        }
    }
}

function loadDvn(baseResponse)
{
var seq=0;
var flag=baseResponse.getElementsByTagName("flag")[0].firstChild.nodeValue;

    if(flag=="success")
    {
  
         
         var items_id=new Array();
         var transamt=new Array();
         var fundtype_id=new Array();
         var items_name=new Array();
         var fundtype_name=new Array();
         var slno=baseResponse.getElementsByTagName("slno");
         var vno=new Array();
       
         var transofficeid=baseResponse.getElementsByTagName("tranOffice");
         for(var k=0;k<transofficeid.length;k++)
            {   vno_id[k]=baseResponse.getElementsByTagName("voucherno")[k].firstChild.nodeValue;
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
            /*     cell2=document.createElement("TD");
                  var slnotxt=document.createTextNode(seq);
                  cell2.appendChild(slnotxt);
                  var vno=document.createElement("input");
                  vno.type="hidden";
                  vno.name="vno_hid";
                  vno.id="vno_hid";
                  vno.readOnly=true;
                  vno.size=4;
                  vno.value=vno_id[k];
                  cell2.appendChild(vno);
                  
                   mycurrent_row.appendChild(cell2);*/
                 cell2=document.createElement("TD");
                 var trnoffid=document.createElement("input");
                  trnoffid.type="hidden";
                  trnoffid.name="trans_off_hid";
                  trnoffid.id="trans_off_hid";
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
                   letdatehid=document.createElement("<input type='text' id='let_date_hid' name='let_date_hid' size='10' value='' onkeypress='return calins(event,this)' onblur='return checkdt(this)'>");
                   }
                   else
                   {
                   var letdatehid=document.createElement("input");
                  letdatehid.type="text";
                  letdatehid.name="let_date_hid";
                  letdathid.id="let_date_hid";
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
                  offletnumhid.id="off_let_no_hid";
                  offletnumhid.size=15;
                  offletnumhid.value="";
                  cell2.appendChild(offletnumhid);
                  mycurrent_row.appendChild(cell2);
                 cell2=document.createElement("TD");
                   var offletdatehid="";
                   if (window.navigator.appName.toLowerCase().indexOf("netscape") == -1)
                   {
                   offletdatehid=document.createElement("<input type='text' id='off_let_date_hid' name='off_let_date_hid' size='10' value='' onkeypress='return calins(event,this)' onblur='return checkdt(this)'>");
                   }
                   else
                   {
                   var offletdatehid=document.createElement("input");
                  offletdatehid.type="text";
                  offletdatehid.name="off_let_date_hid";
                  offletdatehid.id="off_let_date_hid";
                  offletdatehid.size=10;
                  offletdatehid.setAttribute('onkeypress','return calins(event,this)');
                  
                  offletdatehid.value="";
                  }
                  cell2.appendChild(offletdatehid);
                  mycurrent_row.appendChild(cell2);
               
                  cell2=document.createElement("TD");
             
                 var fundtypeid=document.createElement("input");
                  fundtypeid.type="hidden";
                  fundtypeid.name="fundtype_hid";
                  fundtypeid.id="fundtype_hid";
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
                   reqamthid=document.createElement("<input type='text' name='req_amt_hid"+k+"' id='req_amt_hid"+k+"' size='15' value='"+transamt[k]+"' onkeypress='return numbersonly(event)' onchange='return amtcheck(this.value,"+k+")'>");
                  // alert("reqamthid"+reqamthid.value);
                   }
                   else
                   {
                  reqamthid=document.createElement("input");
                  reqamthid.type="text";
                  reqamthid.name="req_amt_hid";
                   reqamthid.id="req_amt_hid"+k;
                  reqamthid.size=15;
                  reqamthid.align='right';
                  reqamthid.value=transamt[k];
                // alert(reqamthid.value);                   
                  reqamthid.setAttribute('onkeypress','return numbersonly(event)');
                  reqamthid.setAttribute('onchange','return amtcheck(this.value,'+k+')');
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
                  alotamthid.name="alot_amt_hid"+k;
                  alotamthid.id="alot_amt_hid"+k;
                  reqamthid.align='right';
                  alotamthid.size=15;
                  alotamthid.readOnly=true;
                  alotamthid.value=transamt[k];
                 // alert(alotamthid.value);
                  cell2.appendChild(alotamthid);     
                 //  var currentText=document.createTextNode(alotamt);
                //  cell2.appendChild(currentText);
                   mycurrent_row.appendChild(cell2);
                   var reasonhid="";
              cell2=document.createElement("TD");
               if (window.navigator.appName.toLowerCase().indexOf("netscape") == -1){
                  reasonhid=document.createElement("<textarea name='reason_hid' id='reason_hid' cols='20' rows='2'></textarea>");
                  }
                  else
                  {
                  reasonhid=document.createElement("input");
                  reasonhid.type="text";
                  reasonhid.name="reason_hid";
                  reasonhid.id="reason_hid";
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
       // alert("letterdate front"+lettdate);
        if(remarks=='null')
        document.getElementById("txtRemarks").value="";
    else
        document.getElementById("txtRemarks").value=remarks;
  
    }
    else if(flag=="success")
    {
        alert("The data has been updated Successfully");
        
    }
    else if(flag=="AlreadyExist")
    {
        alert("Fund Allotment Details Already Exist");
        
    }
    else if(flag=="failure")
    {
        alert("No data found");
        // var cmbdivname=document.getElementById("txtDivname");
        // clear_Combo(cmbdivname);
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
        // var cmbdivname=document.getElementById("txtDivname");
        // clear_Combo(cmbdivname);
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
var url="../../../../../ListFundAllotmentServ1?Command=loadLetNo&txtyear="+year+"&txtmonth="+month+"&diviname="+divname+"&accunit="+accunitid+"&office_id="+officeid+"&voucherno="+vno;
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
       // mycurrent_row.id=seq;
        //alert("row ID"+mycurrent_row.id);
      // alert(seq)
        var i=0;
        var cell2;
                cell2=document.createElement("TD");
                 var slno=document.createElement("input");
                  slno.type="text";
                  slno.name="sl_no_hid";
                  slno.value=seq;
                  cell2.appendChild(slno);
                  // var currentText=document.createTextNode(seq);
                  // cell2.appendChild(currentText);
                   mycurrent_row.appendChild(cell2);
           //  alert('first td'+(slno.value))
             
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
                  
                mycurrent_row.appendChild(cell2);
             
             cell2=document.createElement("TD");
              var alotamthid=document.createElement("input");
                  alotamthid.type="text";
                  alotamthid.name="alot_amt_hid";
                  alotamthid.value=transamt;
                  //alert(alotamthid.value);
                  cell2.appendChild(alotamthid);     
               
                   mycurrent_row.appendChild(cell2);
         
            
                    tbody.appendChild(mycurrent_row);
   
   
   
   
  //  document.getElementById("alotAmt").value=transamt;
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
alert(accunitid);
var divname=document.frmFund_Allotment_Details.txtDivname.value;
//alert(divname);
var officeid=document.frmFund_Allotment_Details.cmbOffice_code.value;
//alert(officeid);
var url="../../../../../ListFundAllotmentServ1?Command=loadVoucherNo&txtyear="+year+"&txtmonth="+month+"&diviname="+divname+"&accunit="+accunitid+"&office_id="+officeid;
            //alert(url);
            var req=getTransport();
            req.open("GET",url,true); 
            req.onreadystatechange=function()
            {
               handleResponse(req);
            }   
                    req.send(null);
}

function  amtcheck(amtreq,k)
{
//alert("k is"+k);
//alert("amount is"+amtreq);

var amtalloted=eval(document.getElementById("alot_amt_hid"+k).value);
var amtrequired=eval(document.getElementById("req_amt_hid"+k).value);
//alert(amtalloted);
//alert(amtrequired);

if(amtrequired <= amtalloted)
{
    alert("the amount requested should not be lesser than amount allocated");
    document.getElementById("req_amt_hid"+k).value=""
    document.getElementById("req_amt_hid"+k).focus();
    
}
}

function doParentFundAllotment(slno,voucherno,transAmt,fundreq,LetterDate,OffLetterDate,tranOffice,tranOfficeName,fundtype,fundtypeid,LetterNo,OffLetterNo,reason,CheqorDD,CheqNo,CheqDate,remarks,letgen,HOREFNO,HOREFDATE)
{   

{
   
    
   var table=document.getElementById("mytable");
   var tbody=document.getElementById("grid_body");
   var mycurrent_row=document.createElement("TR");
   
      // seq=seq+1;
         var lettno=HOREFNO;
   // alert(lettno);
    var lettdate=HOREFDATE;
    //var transamt=baseResponse.getElementsByTagName("transAmt")[0].firstChild.nodeValue;
     if(lettno=='null')
        document.getElementById("txtLetNo").value="";
    else
        document.getElementById("txtLetNo").value=lettno;
    if(lettdate=='null')
        document.getElementById("txtLetterDate").value="";
    else    
        document.getElementById("txtLetterDate").value=lettdate;
        document.getElementById("txtVoucherNo").value=voucherno;
        if(remarks=='--')
        document.getElementById("txtRemarks").value="";
        else
        document.getElementById("txtRemarks").value=remarks;
        var i=0;
        var cell2;
                cell2=document.createElement("TD");
                  var slnotxt=document.createTextNode(slno);
                  cell2.appendChild(slnotxt);
                  var slnoexist=document.createElement("input");
                  slnoexist.type="hidden";
                  slnoexist.name="sl_no_hid2";
                  slnoexist.id="sl_no_hid2";
                  slnoexist.readOnly=true;
                  slnoexist.size=4;
                  slnoexist.value=slno;
                 // alert("sss"+slnoexist.value);
                  cell2.appendChild(slnoexist);
                  
                   mycurrent_row.appendChild(cell2);
             //alert('first td'+(slno.value))
             
                 cell2=document.createElement("TD");
                 var trnoffid=document.createElement("input");
                  trnoffid.type="hidden";
                  trnoffid.name="trans_off_hid";
                  trnoffid.id="trans_off_hid";
                  //trnoffid.size=10;
                  trnoffid.readOnly=true;
                  trnoffid.align="center";
                  trnoffid.value=tranOffice;
                  cell2.appendChild(trnoffid);  
                    
                  var trnoffname=document.createTextNode(tranOfficeName);
                  
                  cell2.appendChild(trnoffname);     
               
                   mycurrent_row.appendChild(cell2);
                   
                  cell2=document.createElement("TD");
                  var letnumhid=document.createElement("input");
                  letnumhid.type="text";
                  letnumhid.name="let_no_hid";
                  letnumhid.id="let_no_hid";
                  letnumhid.size=15;
                  letnumhid.value=LetterNo;
                  cell2.appendChild(letnumhid);
                  mycurrent_row.appendChild(cell2);
                 cell2=document.createElement("TD");
                   var letdatehid="";
                   if (window.navigator.appName.toLowerCase().indexOf("netscape") == -1)
                   {
                   letdatehid=document.createElement("<input type='text' id='let_date_hid' name='let_date_hid' size='10' value='"+LetterDate+"' onkeypress='return calins(event,this)' onblur='return checkdt(this)'>");
                   }
                   else
                   {
                   var letdatehid=document.createElement("input");
                  letdatehid.type="text";
                  letdatehid.name="let_date_hid";
                  letdatehid.id="let_date_hid";
                  letdatehid.size=10;
                  letdatehid.setAttribute('onkeypress','return calins(event,this)');
                  
                  letdatehid.value=LetterDate;
                  }
                  cell2.appendChild(letdatehid);
                  mycurrent_row.appendChild(cell2);
                  //--------------------------------------
                  cell2=document.createElement("TD");
                  var offletnumhid=document.createElement("input");
                  offletnumhid.type="text";
                  offletnumhid.name="off_let_no_hid";
                  offletnumhid.id="off_let_no_hid";
                  offletnumhid.size=15;
                  offletnumhid.value=OffLetterNo;
                  cell2.appendChild(offletnumhid);
                  mycurrent_row.appendChild(cell2);
                 cell2=document.createElement("TD");
                   var offletdatehid="";
                   if (window.navigator.appName.toLowerCase().indexOf("netscape") == -1)
                   {
                   offletdatehid=document.createElement("<input type='text' id='off_let_date_hid' name='off_let_date_hid' size='10' value='"+OffLetterDate+"' onkeypress='return calins(event,this)' onblur='return checkdt(this)'>");
                   }
                   else
                   {
                   var offletdatehid=document.createElement("input");
                  offletdatehid.type="text";
                  offletdatehid.name="off_let_date_hid";
                  offletdatehid.id="off_let_date_hid";
                  offletdatehid.size=10;
                  offletdatehid.setAttribute('onkeypress','return calins(event,this)');
                  
                  offletdatehid.value=OffLetterDate;
                  }
                  cell2.appendChild(offletdatehid);
                  mycurrent_row.appendChild(cell2);
                  
                  //--------------------------------------
               cell2=document.createElement("TD");
                 var fundid=document.createElement("input");
                  fundid.type="hidden";
                  fundid.name="fund_hid";
                  fundid.id="fund_hid";
                  //trnoffid.size=10;
                  fundid.readOnly=true;
                  fundid.align="center";
                  fundid.value=fundtypeid;
                  cell2.appendChild(fundid);  
                    
                  var fundname=document.createTextNode(fundtype);
                  
                  cell2.appendChild(fundname);     
               
                   mycurrent_row.appendChild(cell2);   
              
                  
                 var reqamthid = "";
             // var reqamthid=document.createTextNode(transamt[k]);
              cell2=document.createElement("TD"); 
            //   var reqamthidtxt=document.createTextNode(transamt[k]);
            //   cell2.appendChild(reqamthidtxt); 
               if (window.navigator.appName.toLowerCase().indexOf("netscape") == -1)
                   {
                   reqamthid=document.createElement("<input type='text' name='req_amt_hid' id='req_amt_hid' size='15' value='"+fundreq+"' onkeypress='return numbersonly(event)'>");
                 //  alert("reqamthid"+reqamthid.value);
                   }
                   else
                   {
                  reqamthid=document.createElement("input");
                  reqamthid.type="text";
                  reqamthid.name="req_amt_hid";
                  reqamthid.id="req_amt_hid";
                  reqamthid.size=15;
                  reqamthid.align='right';
                  reqamthid.value=fundreq;
                //  alert(reqamthid.value);                   
                  reqamthid.setAttribute('onkeypress','return numbersonly(event)');
                 // reqamthid.setAttribute('onchange','return amtcheck(this.value,'+k+')');
                  }
                  cell2.appendChild(reqamthid);    
                
                mycurrent_row.appendChild(cell2);
             
            cell2=document.createElement("TD");
             var alotamthidtxt=document.createTextNode(transAmt);
              cell2.appendChild(alotamthidtxt); 
              var alotamthid=document.createElement("input");
                  alotamthid.type="hidden";
                  alotamthid.name="alot_amt_hid";
                  alotamthid.id="alot_amt_hid";
                  reqamthid.align='right';
                  alotamthid.size=15;
                  alotamthid.readOnly=true;
                  alotamthid.value=transAmt;
                //  alert("alotamthid"+alotamthid.value);
                  cell2.appendChild(alotamthid);     
                 //  var currentText=document.createTextNode(alotamt);
                //  cell2.appendChild(currentText);
                   mycurrent_row.appendChild(cell2);
                   var reasonhid="";
              cell2=document.createElement("TD");
               if (window.navigator.appName.toLowerCase().indexOf("netscape") == -1){
                  //reasonhid=document.createElement("<textarea id='reason_hid' name='reason_hid' value='"+reason+"' cols='20' rows='2'></textarea>");
                  reasonhid=document.createElement("<input type='text' id='reason_hid' name='reason_hid' size='50' value='"+reason+"'></input>");
                  }
                  else
                  {
                  reasonhid=document.createElement("input");
                  reasonhid.type="text";
                 reasonhid.name="reason_hid";
                  reasonhid.id="reason_hid";
                  reasonhid.size="50";
                  reasonhid.value=reason;
                 // alert("reason..."+reasonhid.value);
                  }
                  
                   cell2.appendChild(reasonhid);    
                
                mycurrent_row.appendChild(cell2);
                 // cell2.appendChild(reasonhid);
                 // mycurrent_row.appendChild(cell2);
            
                    tbody.appendChild(mycurrent_row);
      
            } 
            
          
     cell2=document.createElement("TD");   
     var ChequeDate="";
                   if (window.navigator.appName.toLowerCase().indexOf("netscape") == -1)
                   {
                   ChequeDate=document.createElement("<input type='hidden' id='cheq_date_hid' name='cheq_date_hid' size='10' value='"+CheqDate+"'>");
                   }
                   else
                   {
                   var ChequeDate=document.createElement("input");
                  ChequeDate.type="hidden";
                  ChequeDate.name="cheq_date_hid";
                  ChequeDate.id="cheq_date_hid";
                  ChequeDate.size=10;
                  //ChequeDate.setAttribute('onkeypress','return calins(event,this)');
                  
                  ChequeDate.value=CheqDate;
                  } 
                  
                   cell2.appendChild(ChequeDate);    
                
                mycurrent_row.appendChild(cell2);
      cell2=document.createElement("TD");            
       var ChequeNum=document.createElement("input");
                  ChequeNum.type="hidden";
                  ChequeNum.name="cheq_no_hid";
                  ChequeNum.id="cheq_no_hid";
                  //trnoffid.size=10;
                  ChequeNum.readOnly=true;
                  ChequeNum.align="center";
                  ChequeNum.value=CheqNo;  
        cell2.appendChild(ChequeNum);    
                
                mycurrent_row.appendChild(cell2); 
                
         cell2=document.createElement("TD");         
        var ChequeType=document.createElement("input");
                  ChequeType.type="text";
                  ChequeType.name="cheq_type_hid";
                  ChequeType.id="cheq_type_hid";
                  //trnoffid.size=10;
                  ChequeType.readOnly=true;
                  ChequeType.align="center";
                  ChequeType.value=CheqorDD;          
          // alert("ChequeType.value"+ChequeType.value); 
            cell2.appendChild(ChequeType);    
                
                mycurrent_row.appendChild(cell2); 
                var LetterGen=document.createElement("input");
                  LetterGen.type="hidden";
                  LetterGen.name="letter_gen_hid";
                  LetterGen.id="letter_gen_hid";
                  //trnoffid.size=10;
                  LetterGen.readOnly=true;
                  LetterGen.align="center";
                  LetterGen.value=letgen;          
         //  alert("LetterGen.value"+LetterGen.value); 
            cell2.appendChild(LetterGen);   
            mycurrent_row.appendChild(cell2); 
      
        cell2=document.createElement("TD");         
        var LetterGen=document.createElement("input");
                  LetterGen.type="text";
                  LetterGen.name="LetterGen_hid";
                  LetterGen.id="LetterGen_hid";
                  //trnoffid.size=10;
                  LetterGen.readOnly=true;
                  LetterGen.align="center";
                  LetterGen.value=letgen;          
          // alert("ChequeType.value"+ChequeType.value); 
            cell2.appendChild(LetterGen);    
                
                mycurrent_row.appendChild(cell2); 
    }
 
  

