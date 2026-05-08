
var com_id;
var common_cmbSL_Code="";
var common_cmbSL_type="";
var seq=1;
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

var url="../../../../../FundAllotment_Dispatch_Status?Command=loadDivision&accunit="+accunitid+"&txtyear="+year+"&txtmonth="+month+"&officeId="+offid;
          // alert(url);
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
           // alert(Command);
            if(Command=="loadDivision")
            {
                loadDvn(baseResponse);
            }
            else if(Command=="update")
            {
                loadDvn(baseResponse);
            }
        }
    }
}



function loadDvn(baseResponse)
{

var flag=baseResponse.getElementsByTagName("flag")[0].firstChild.nodeValue;
//alert(flag);
    if(flag=="success")
    {
  var tbody=document.getElementById("grid_body");
        var t=0;
        for(t=tbody.rows.length-1;t>=0;t--)
        {
           tbody.deleteRow(0);
        }
       

         var transofficeid=baseResponse.getElementsByTagName("tranOffice");
         var sno=baseResponse.getElementsByTagName("slno");
         //alert("transofficeid"+transofficeid);
          var items=new Array();
          var generated_status="";
         for(var k=0;k<transofficeid.length;k++)
         //for(var k=0;k<sno.length;k++)
            {   
              
                items[0]=baseResponse.getElementsByTagName("slno")[k].firstChild.nodeValue;
               // alert(items[0]);
                items[1]=baseResponse.getElementsByTagName("voucherno")[k].firstChild.nodeValue;
               // alert(items[1]);
                items[2]=baseResponse.getElementsByTagName("tranOffice")[k].firstChild.nodeValue;
              //  alert(items[2]);
                items[3]=baseResponse.getElementsByTagName("tranOfficeName")[k].firstChild.nodeValue;
                items[4]=baseResponse.getElementsByTagName("fundtypeid")[k].firstChild.nodeValue;
                
                items[5]=baseResponse.getElementsByTagName("transAmt")[k].firstChild.nodeValue;
                items[6]=baseResponse.getElementsByTagName("LetterNo")[k].firstChild.nodeValue;
                items[7]=baseResponse.getElementsByTagName("LetterDate")[k].firstChild.nodeValue;  
                items[8]=baseResponse.getElementsByTagName("OffLetterNo")[k].firstChild.nodeValue;
                items[9]=baseResponse.getElementsByTagName("OffLetterDate")[k].firstChild.nodeValue;   
                items[10]=baseResponse.getElementsByTagName("reason")[k].firstChild.nodeValue;
                items[11]=baseResponse.getElementsByTagName("fundreq")[k].firstChild.nodeValue;
                items[12]=baseResponse.getElementsByTagName("fundtype")[k].firstChild.nodeValue;
          
             
         //  clear_Combo(cmbdivname);
            
          
                
   var table=document.getElementById("mytable");
var tbody=document.getElementById("grid_body");
var mycurrent_row=document.createElement("TR");
   // mycurrent_row.id=seq;     
      // seq=seq+1;
     //  alert("seq"+seq);
       //alert(letno_office[k]);
      // alert(letno[k]);
        var sel="";    
           var cell2;                
                    var mycurrent_row=document.createElement("TR");
                          mycurrent_row.id=seq;                               
                
                          cell2=document.createElement("TD");
                          
                          if (window.navigator.appName.toLowerCase().indexOf("netscape") == -1)
                          {
                              sel=document.createElement("<INPUT type='checkbox' name='sel' id='sel' checked value='"+seq+"'   />");                       
                          }
                          
                          else
                          {    
                             sel=document.createElement("input");     
                             sel.type="checkbox";             
                             sel.name="sel";
                             sel.id="sel";
                             sel.checked=true;
                             sel.value=seq;  
                           // sel.setAttribute('onclick','cal_status('+seq+')');
                             
                          }
                          cell2.appendChild(sel);
                          mycurrent_row.appendChild(cell2);
                          
        var i=0;
       
                cell2=document.createElement("TD");
                 var slnotxt=document.createTextNode(items[0]);
                  cell2.appendChild(slnotxt);
                  var slno=document.createElement("input");
                  slno.type="hidden";
                  slno.name="sl_no_hid"+(k+1);
                  slno.id="sl_no_hid"+(k+1);
                  slno.readOnly=true;
                  slno.size=4;
                  slno.value=items[0];
                  cell2.appendChild(slno);
                  mycurrent_row.appendChild(cell2);
                   
                 cell2=document.createElement("TD");
                  var vnumtxt=document.createTextNode(items[1]);
                  cell2.appendChild(vnumtxt);
                  var vnum=document.createElement("input");
                  vnum.type="hidden";
                  vnum.name="vno_hid"+(k+1);
                  vnum.id="vno_hid"+(k+1);
                  vnum.readOnly=true;
                  vnum.size=4;
                  vnum.value=items[1];
                  cell2.appendChild(vnum);
                  
                   mycurrent_row.appendChild(cell2);
                 cell2=document.createElement("TD");
                 var trnoffid=document.createElement("input");
                  trnoffid.type="hidden";
                  trnoffid.name="trans_off_hid"+(k+1);
                  trnoffid.id="trans_off_hid"+(k+1);
                  //trnoffid.size=10;
                  trnoffid.readOnly=true;
                  trnoffid.align="center";
                  trnoffid.value=items[2];
                  cell2.appendChild(trnoffid);  
                  var trnoffname=document.createTextNode(items[3]);
                  cell2.appendChild(trnoffname);     
                  mycurrent_row.appendChild(cell2);
                  
                  cell2=document.createElement("TD");
                  var Offletnumtxt=document.createTextNode(items[8]);
                  cell2.appendChild(Offletnumtxt);
                  var Offletnumhid=document.createElement("input");
                  Offletnumhid.type="hidden";
                  Offletnumhid.name="offlet_no_hid";
                  Offletnumhid.id="offlet_no_hid";
                  Offletnumhid.size=15;
                  Offletnumhid.readonly=true;
                  Offletnumhid.value=items[8];
                //  alert("Offletnumhid"+Offletnumhid.value)
                  cell2.appendChild(Offletnumhid);
                  mycurrent_row.appendChild(cell2);
                   
            
                 cell2=document.createElement("TD");
                  var Offletdatetxt=document.createTextNode(items[9]);
                  cell2.appendChild(Offletdatetxt);
                  var Offletdatehid=document.createElement("input");
                  Offletdatehid.type="hidden";
                  Offletdatehid.name="Offlet_date_hid";
                  Offletdatehid.id="Offlet_date_hid";
                  Offletdatehid.readOnly=true;
                  Offletdatehid.size=10;
                  Offletdatehid.setAttribute('onkeypress','return calins(event,this)');
                  
                  Offletdatehid.value=items[9];
                 
                  cell2.appendChild(Offletdatehid);
                  mycurrent_row.appendChild(cell2);
                  //--------------------------------------
                   cell2=document.createElement("TD");
                  var letnumtxt=document.createTextNode(items[6]);
                  cell2.appendChild(letnumtxt);
                  
                  var letnumhid=document.createElement("input");
                  letnumhid.type="hidden";
                  
                  
                  letnumhid.name="let_no_hid";
                  letnumhid.id="let_no_hid";
                  letnumhid.size=15;
                  letnumhid.readonly=true;
                  letnumhid.value=items[6];
                  cell2.appendChild(letnumhid);
                  mycurrent_row.appendChild(cell2);
                
                  cell2=document.createElement("TD");
                  var letdatetxt=document.createTextNode(items[7]);
                  cell2.appendChild(letdatetxt);
                  var letdatehid=document.createElement("input");
                  letdatehid.type="hidden";
                  letdatehid.name="let_date_hid";
                  letdatehid.id="let_date_hid";
                  letdatehid.readOnly=true;
                  letdatehid.size=10;
                 // letdatehid.setAttribute('onkeypress','return calins(event,this)');
                  
                   letdatehid.value=items[7];
                 
                  cell2.appendChild(letdatehid);
                  mycurrent_row.appendChild(cell2);
               
                  cell2=document.createElement("TD");
             
                 var fundtypeid=document.createElement("input");
                  fundtypeid.type="hidden";
                  fundtypeid.name="fundtype_hid";
                  fundtypeid.id="fundtype_hid";
                  //trnoffid.size=10;
                  fundtypeid.readOnly=true;
                  fundtypeid.align="center";
                  fundtypeid.value=items[4];
                  cell2.appendChild(fundtypeid);  
                        
                  var funtypename=document.createTextNode(items[12]);
                 
                  cell2.appendChild(funtypename);     
                 
                   mycurrent_row.appendChild(cell2);
                   
                  
                  cell2=document.createElement("TD");
                  var reqamttxt=document.createTextNode(items[11]);
                  cell2.appendChild(reqamttxt);
                  var reqamthid=document.createElement("input");
                  reqamthid.type="hidden";
                  reqamthid.name="req_amt_hid";
                  reqamthid.id="req_amt_hid";
                  reqamthid.readOnly=true;
                  reqamthid.size=15;
                  reqamthid.align='right';
                  reqamthid.value=items[11];
                // alert(reqamthid.value);                   
                  //reqamthid.setAttribute('onkeypress','return numbersonly(event)');
                //  reqamthid.setAttribute('onchange','return amtcheck(this.value,'+k+')');
                
                  cell2.appendChild(reqamthid);    
                
                mycurrent_row.appendChild(cell2);
                
             
            cell2=document.createElement("TD");
             var alotamthidtxt=document.createTextNode(items[5]);
              cell2.appendChild(alotamthidtxt); 
              var alotamthid=document.createElement("input");
                  alotamthid.type="hidden";
                  alotamthid.name="alot_amt_hid";
                  alotamthid.id="alot_amt_hid";
                  alotamthid.align='right';
                  alotamthid.size=15;
                  alotamthid.readOnly=true;
                  alotamthid.value=items[5];
                  cell2.appendChild(alotamthid);     
                  mycurrent_row.appendChild(cell2);
                   
                    cell2=document.createElement("TD");
             var reasonhidtxt=document.createTextNode(items[10]);
              cell2.appendChild(reasonhidtxt); 
              var reasonhid=document.createElement("input");
                  reasonhid.type="hidden";
                  reasonhid.name="reason_hid";
                  reasonhid.id="reason_hid";
                  reasonhid.align='right';
                  reasonhid.size=2;
                  reasonhid.readOnly=true;
                  reasonhid.value=items[10];
                  cell2.appendChild(reasonhid);     
                
                   mycurrent_row.appendChild(cell2);   
                   
            
            
                    
    
                tbody.appendChild(mycurrent_row);
            
            
                    tbody.appendChild(mycurrent_row);
                    seq++;
               }
            }
           else if(flag=="failure")
    {
        alert("No data found");
       
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
 


function clrForm()
{
document.frmFund_Allotment_Details.txtLetNo.value="";
document.frmFund_Allotment_Details.txtLetterDate.value="";
document.frmFund_Allotment_Details.txtRemarks.value="";

document.getElementById("grid").style.display='none';

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


 
  

