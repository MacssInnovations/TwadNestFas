//alert("hi");
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

function clear_Combo(combo)
{
        //alert(combo.id)
        var txtCB_Month=document.getElementById(combo.id);   
        txtCB_Month.innerHTML="";
        var option=document.createElement("OPTION");
        option.text="--Select--";
        option.value="";
        try
        {
            txtCB_Month.add(option);
        }catch(errorObject)
        {
            txtCB_Month.add(option,null);
        }
}

function loadCheque()
{

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

var year=document.frmRevert_Cheque_Cancel.txtCB_Year.value;
var month=document.frmRevert_Cheque_Cancel.txtCB_Month.value;
var accunitid=document.frmRevert_Cheque_Cancel.cmbAcc_UnitCode.value;
var offid=document.frmRevert_Cheque_Cancel.cmbOffice_code.value;

var url="../../../../../Revert_Cheque_Cancel_Status?Command=loadCheque&accunit="+accunitid+"&txtyear="+year+"&txtmonth="+month+"&officeId="+offid;
            var req=getTransport();
            req.open("GET",url,true); 
            req.onreadystatechange=function()
            {
               handleResponse(req);
            }   
                    req.send(null);
                   
}
function searchByDate()
{
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

var year=document.frmRevert_Cheque_Cancel.txtCB_Year.value;
var month=document.frmRevert_Cheque_Cancel.txtCB_Month.value;
var accunitid=document.frmRevert_Cheque_Cancel.cmbAcc_UnitCode.value;
var offid=document.frmRevert_Cheque_Cancel.cmbOffice_code.value;
var txtFrom_date=document.getElementById("txtFrom_date").value;
var txtTo_date=document.getElementById("txtTo_date").value;
var url="../../../../../Revert_Cheque_Cancel_Status?Command=searchByDate&accunit="+accunitid+"&txtyear="+year+"&txtmonth="+month+"&officeId="+offid+"&txtFrom_date="+txtFrom_date+"&txtTo_date="+txtTo_date;
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
            if(Command=="loadCheque")
            {
                loadDvn(baseResponse);
            }
            else if(Command=="searchByDate")
            {
                loadDvn(baseResponse);
            }
        }
    }
}

function callfunc(sam)
{
	var tbody = document.getElementById("grid_body");
	var rowcount = tbody.rows.length;
	alert(rowcount);
	//var r1 = tbody.rows;    
	for ( var i = 0; i < rowcount; i++) {
		if ((document.getElementById("old_cheqno" + i).value) == (document
				.getElementById("old_cheqno" + sam).value)) {
			
			fg1 = fg1 + 1;
			if (fg1 == 1) {
				ii = i;
			}
			r.bgColor = "#FFCCCC";
			document.getElementById("sel" + i).checked = true;
			
		}
		
	}
}


function loadDvn(baseResponse)
{

var flag=baseResponse.getElementsByTagName("flag")[0].firstChild.nodeValue;
    if(flag=="success")
    {
  var tbody=document.getElementById("grid_body");
        var t=0;
        for(t=tbody.rows.length-1;t>=0;t--)
        {
           tbody.deleteRow(0);
        }
       

         var cheqno=baseResponse.getElementsByTagName("old_cheqno");
         var sno=baseResponse.getElementsByTagName("slno");
          var items=new Array();
          var generated_status="";
          s=-1;
         for(var k=0;k<cheqno.length;k++)
         //for(var k=0;k<sno.length;k++)
            {   
              s++;
                items[0]=s;
                items[1]=baseResponse.getElementsByTagName("doc_no")[k].firstChild.nodeValue;
                items[2]=baseResponse.getElementsByTagName("doc_date")[k].firstChild.nodeValue;
                items[3]=baseResponse.getElementsByTagName("doc_type")[k].firstChild.nodeValue;
                items[4]=baseResponse.getElementsByTagName("oldcheq_type")[k].firstChild.nodeValue;
                items[5]=baseResponse.getElementsByTagName("old_cheqno")[k].firstChild.nodeValue;
                items[6]=baseResponse.getElementsByTagName("old_cheqdate")[k].firstChild.nodeValue;
                items[7]=baseResponse.getElementsByTagName("old_cheqamt")[k].firstChild.nodeValue;  
            
   var table=document.getElementById("mytable");
var tbody=document.getElementById("grid_body");
var mycurrent_row=document.createElement("TR");
   
        var sel="";    
           var cell2;                
                    var mycurrent_row=document.createElement("TR");
                          mycurrent_row.id=s;                               
                
                          cell2=document.createElement("TD");
                          
                          if (window.navigator.appName.toLowerCase().indexOf("netscape") == -1)
                          {
                              sel=document.createElement("<INPUT type='checkbox' name='sel' id='sel'   value='"+seq+"'  />");                       
                          }
                          
                          else
                          {    
                             sel=document.createElement("input");     
                             sel.type="checkbox";             
                             sel.name="sel";
                             sel.id="sel";
                             sel.value=items[0];  
                           }
                          cell2.appendChild(sel);
                          mycurrent_row.appendChild(cell2);
                          
        var i=0;
       
                cell2=document.createElement("TD");
                 var slnotxt=document.createTextNode(items[0]);
                  cell2.appendChild(slnotxt);
                  var slno=document.createElement("input");
                  slno.type="hidden";
                  slno.name="sl_no_hid";
                  slno.id="sl_no_hid";
                  slno.readOnly=true;
                //  slno.size=4;
                  slno.value=items[0];
                  cell2.appendChild(slno);
                  mycurrent_row.appendChild(cell2);
               
                 cell2=document.createElement("TD");
                  var vnumtxt=document.createTextNode(items[1]);
                  cell2.appendChild(vnumtxt);
                  var vnum=document.createElement("input");
                  vnum.type="hidden";
                  vnum.name="vno_hid";
                  vnum.id="vno_hid";
                  vnum.readOnly=true;
               //   vnum.size=4;
                  vnum.value=items[1];
                  cell2.appendChild(vnum);
                  
                   mycurrent_row.appendChild(cell2);
                   
                   cell2=document.createElement("TD");
                  var Offletdatetxt=document.createTextNode(items[2]);
                  cell2.appendChild(Offletdatetxt);
                  var Offletdatehid=document.createElement("input");
                  Offletdatehid.type="hidden";
                  Offletdatehid.name="Offlet_date_hid";
                  Offletdatehid.id="Offlet_date_hid";
                  Offletdatehid.readOnly=true;
                //  Offletdatehid.size=10;
                  Offletdatehid.setAttribute('onkeypress','return calins(event,this)');
                  
                  Offletdatehid.value=items[2];
                 mycurrent_row.appendChild(cell2);
              
              cell2=document.createElement("TD");
                 var fundtypeid=document.createElement("input");
                  fundtypeid.type="hidden";
                  fundtypeid.name="fundtype_hid";
                  fundtypeid.id="fundtype_hid";
                  fundtypeid.readOnly=true;
                  fundtypeid.align="center";
                  fundtypeid.value=items[3];
                  cell2.appendChild(fundtypeid);  
                        
                  var funtypename=document.createTextNode(items[3]);
                 
                  cell2.appendChild(funtypename);     
                 
                   mycurrent_row.appendChild(cell2);
                   
                  cell2=document.createElement("TD");
                  var reasonhidtxt=document.createTextNode(items[4]);
                  cell2.appendChild(reasonhidtxt); 
                  var reasonhid=document.createElement("input");
                  reasonhid.type="hidden";
                  reasonhid.name="reason_hid";
                  reasonhid.id="reason_hid";
                  reasonhid.align='right';
               //   reasonhid.size=2;
                  reasonhid.readOnly=true;
                  reasonhid.value=items[4];
                  cell2.appendChild(reasonhid);     
                
                   mycurrent_row.appendChild(cell2);
                   
                   
                  cell2=document.createElement("TD");
                  var Offletnumtxt=document.createTextNode(items[5]);
                  cell2.appendChild(Offletnumtxt);
                  var Offletnumhid=document.createElement("input");
                  Offletnumhid.type="hidden";
                  Offletnumhid.name="offlet_no_hid";
                  Offletnumhid.id="offlet_no_hid";
                //  Offletnumhid.size=15;
                  Offletnumhid.readonly=true;
                  Offletnumhid.value=items[5];
                  cell2.appendChild(Offletnumhid);
                  mycurrent_row.appendChild(cell2);
                
                
                  cell2=document.createElement("TD");
                  var letdatetxt=document.createTextNode(items[6]);
                  cell2.appendChild(letdatetxt);
                  var letdatehid=document.createElement("input");
                  letdatehid.type="hidden";
                  letdatehid.name="let_date_hid";
                  letdatehid.id="let_date_hid";
                  letdatehid.readOnly=true;
             //     letdatehid.size=10;
                  letdatehid.value=items[6];
                  cell2.appendChild(letdatehid);
                  mycurrent_row.appendChild(cell2);
               
                  cell2=document.createElement("TD");
                  var reqamttxt=document.createTextNode(items[7]);
                  cell2.appendChild(reqamttxt);
                  var reqamthid=document.createElement("input");
                  reqamthid.type="hidden";
                  reqamthid.name="req_amt_hid";
                  reqamthid.id="req_amt_hid";
                  reqamthid.readOnly=true;
               //   reqamthid.size=15;
                  reqamthid.align='right';
                  reqamthid.value=items[7];
                
                
                  cell2.appendChild(reqamthid);    
                
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


document.getElementById("grid").style.display='none';

}



function check_TB(req)
{
 if(req.readyState==4)
    {
        if(req.status==200)
        {  
            var baseResponse=req.responseXML.getElementsByTagName("response")[0];
            var tagcommand=baseResponse.getElementsByTagName("command")[0];
            var Command=tagcommand.firstChild.nodeValue;
            var flag=baseResponse.getElementsByTagName("flag")[0].firstChild.nodeValue;
           // alert(flag);
            if(flag=="success")
              {
                 loadCheque();                //return true;
              }
             else if(flag=="failure")
               {    
                //    dateCtrl.value="";
                    alert("Trial Balance Closed");//return false;//
                  call_clr();
               }
          
        }
    }
}

//function call_date(dateCtrl)                        // TB_checking 
function call_date()
{// alert("inside calldate");
   // call_clr();
   // if(checkdt(dateCtrl))
    {
       var year=document.frmRevert_Cheque_Cancel.txtCB_Year.value;
       var month=document.frmRevert_Cheque_Cancel.txtCB_Month.value;
       var accunitid=document.frmRevert_Cheque_Cancel.cmbAcc_UnitCode.value;
       var offid=document.frmRevert_Cheque_Cancel.cmbOffice_code.value;

         {
             var url="../../../../../Revert_Cheque_Cancel_Status?Command=check_TB&accunit="+accunitid+"&txtyear="+year+"&txtmonth="+month+"&officeId="+offid;
         //   alert(url);
             var req=getTransport();
             req.open("GET",url,true); 
             req.onreadystatechange=function()
             {
               check_TB(req);
             }   
             req.send(null);
       }
       
    }
  
} 
  

function call_clr()
{
    
    document.getElementById("txtCB_Year").value="";
   //clear_Combo(document.getElementById("txtCB_Month"));
    document.getElementById("txtCB_Month").value="";
    document.getElementById("txtFrom_date").value="";
    document.getElementById("txtTo_date").value="";
    //clearall();
    var tbody=document.getElementById("grid_body");
    var t=0;
    for(t=tbody.rows.length-1;t>=0;t--)
    {
       tbody.deleteRow(0);
    }
}

 
  

