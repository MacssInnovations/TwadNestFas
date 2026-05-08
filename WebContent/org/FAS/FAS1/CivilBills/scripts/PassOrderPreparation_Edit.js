var seq=0;

function AjaxFunction()
    {
        var xmlrequest=false;
        try
            {
               xmlrequest=new ActiveXObject("Msxml2.XMLHTTP"); 
            }
         catch(e1)
          {
                 try
                 {
                     xmlrequest=new ActiveXObject("Microsoft.XMLHTTP"); 
                 }
                 catch(e2)
                 {     
                     xmlrequest=false;
                 }
          }
          if (!xmlrequest && typeof XMLHttpRequest != 'undefined') 
                {
                     xmlrequest=new XMLHttpRequest();
                }
        return xmlrequest;
    }

function clearAll()
{
	document.forms[0].checkCode.value="";
	document.forms[0].checkDesc.value="";
    document.forms[0].billmajortype.value="";
    document.forms[0].billminortype.value="";
    
    document.forms[0].onadd.disabled=false;
  	document.forms[0].onedit.disabled=true;
  	document.forms[0].ondelete.disabled=true;
}

function callBDate()
{
	
}

function callGridValues()
{
	
	var unitid=document.forms[0].cmbAcc_UnitCode.value;
    var offid=document.forms[0].cmbOffice_code.value;
    var cbyear=document.forms[0].cbyear.value;
    var cbmonth=document.forms[0].cbmonth.value;
    var passOrderNo=document.forms[0].passOrderNo.value;
        var url="../../../../../PassOrderPreparation_Edit?command=loadGrid&unitid="+unitid+"&offid="+offid+"&cbyear="
        +cbyear+"&cbmonth="+cbmonth+"&passOrderNo="+passOrderNo;
        var xmlrequest= AjaxFunction();
        xmlrequest.open("GET",url,true);              
        xmlrequest.onreadystatechange=function()
        {
            manipulate(xmlrequest);
        }
        xmlrequest.send(null);
}


function loadpassno(){
	
	//alert("loadpassno");
	var unitid=document.forms[0].cmbAcc_UnitCode.value;
    var offid=document.forms[0].cmbOffice_code.value;
	 var cbyear=document.getElementById('cbyear').value;
	 var cbmonth = document.getElementById("cbmonth").value;
	   /* var cbyear=document.forms[0].cbyear.value;
	    var cbmonth=document.forms[0].cbmonth.value;*/
	 
	url="../../../../../PassOrderPreparation_Edit?command=loadpassno&unitid="+unitid+"&offid="+offid+"&cbyear="+cbyear+"&cbmonth="+cbmonth;
	//alert(url);
    var req=getTransport();
    req.open("GET",url,true);   
    req.onreadystatechange=function(){
    	getReqId1(req);
     };   
     req.send(null);

}

function getReqId1(req){
	//alert("return ");
	if (req.readyState == 4) {
		if (req.status == 200) {
			response=req.responseXML.getElementsByTagName("response")[0];
			var command=response.getElementsByTagName("command")[0].firstChild.nodeValue;
			var flag = response.getElementsByTagName("status")[0].firstChild.nodeValue;			
				if(command=="passorder"){
					var selectdiv=document.getElementById('passOrderNo');
					var listOpt=document.createElement("option");
					selectdiv.length=0;
					selectdiv.appendChild(listOpt);
					listOpt.text="select";
					listOpt.value="select";
					if (flag == "nodata"){
						alert("Sorry! Pass order Number is Not Found for this Bill Type ");
						selectdiv.selectedIndex=0;
						selectdiv.length=1;
					}else if(flag == "success"){
						var len=response.getElementsByTagName("PASS_ORDER_NO").length;						
						for(var i=0; i<len; i++){
							listOpt=document.createElement("option");
							selectdiv.appendChild(listOpt);
							//var val=response.getElementsByTagName("PASS_ORDER_NO")[i].firstChild.nodeValue+"/"+response.getElementsByTagName("sanction_proc_no")[i].firstChild.nodeValue+"/"+response.getElementsByTagName("CASHBOOK_YEAR")[i].firstChild.nodeValue+"/"+response.getElementsByTagName("CASHBOOK_MONTH")[i].firstChild.nodeValue;
							
							var val=response.getElementsByTagName("PASS_ORDER_NO")[i].firstChild.nodeValue;
							listOpt.text=response.getElementsByTagName("PASS_ORDER_NO")[i].firstChild.nodeValue;
							listOpt.value=val;
						}
					}
				}			
			}
		}
}


function  manipulate(xmlrequest)
{
if(xmlrequest.readyState==4)
  {
      if(xmlrequest.status==200)
      {
           var baseResponse=xmlrequest.responseXML.getElementsByTagName("response")[0];  
           var tagCommand=baseResponse.getElementsByTagName("command")[0]; 
           var command=tagCommand.firstChild.nodeValue; 
          
           
         if (command == "getempname_off") {
				 //alert("manipulate");
				getempname_re(baseResponse);
			}
           else if(command=="loadGrid")
           {
        	   loadGridchecking(baseResponse);
           }
         
          
      }
  }
}


//document.getElementById("tnodebillno" + sam).value
function checkDisplay(sam) {
	var passAmt=document.getElementById("txtTotalAmt").value;
	//alert(" pass Amt "+passAmt);
	var amt = 0;
	var fg = 0;
	var fg1 = 0;
	var ii = 0;
	if (document.getElementById("verify_select" + sam).checked == false) {
		
		var tbody = document.getElementById("tbody");
		var rowcount = tbody.rows.length;
              
		for ( var i = 0; i < rowcount; i++) {
			var r = tbody.rows[i];
		
			if (document.getElementById("tnodebillno" + sam).value != "") {
                       
				/* alert("111 first  "+document.getElementById("tnodebillno" + i).value);   
                 alert("22  first "+document.getElementById("tnodebillno" + sam).value);
                 */
				if ((document.getElementById("tnodebillno" + i).value) ==
					(document.getElementById("tnodebillno" + sam).value)) {
                                              
					
					document.getElementById("verify_select" + i).checked = false;
					
					var pass1=document.getElementById("billAmt" + sam).value;
					
					passAmt=parseInt(passAmt)-parseInt(pass1);
					
					 document.forms[0].txtTotalAmt.value=passAmt;
				}
			} 
			
			
		
		}
	
	} 
	else
	{
		
		var tbody = document.getElementById("tbody");
		var rowcount = tbody.rows.length;
              
		for ( var i = 0; i < rowcount; i++) {
			var r = tbody.rows[i];
		
			if (document.getElementById("tnodebillno" + sam).value != "") {
                 /*  alert("111 "+document.getElementById("tnodebillno" + i).value);   
                   alert("22  "+document.getElementById("tnodebillno" + sam).value);*/
				if ((document.getElementById("tnodebillno" + i).value) == 
					(document.getElementById("tnodebillno" + sam).value)) {
                                              
					
					document.getElementById("verify_select" + i).checked = true;
					var pass1=document.getElementById("billAmt" + sam).value; 
					
					passAmt=parseInt(passAmt)+parseInt(pass1);
					
					 document.forms[0].txtTotalAmt.value=passAmt;
				}
			} 
			
			
		
		}
		
	}
	
	
}

function loadGridchecking(baseResponse)
{
	seq = 0;	
	var flag=baseResponse.getElementsByTagName("flag")[0].firstChild.nodeValue; 
	
	record1=new Array();record2=new Array();
    record3=new Array();record4=new Array();
    record5=new Array();record6=new Array();
    record7=new Array();record8=new Array();
    record9=new Array();record10=new Array();
    record11=new Array();record12=new Array();
    record13=new Array();    record14=new Array();    record15=new Array();
    if(flag=="success")
    {        
    	 var tbody = document.getElementById("tbody");
         
              try{tbody.innerHTML="";}
              catch(e) {tbody.innerText="";}  
              
              var display=baseResponse.getElementsByTagName("display");
              
              var PassOrderAmt=baseResponse.getElementsByTagName("PassOrderAmt")[0].firstChild.nodeValue;
              var PASS_ORDER_NO1=baseResponse.getElementsByTagName("PASS_ORDER_NO")[0].firstChild.nodeValue;
              var PASS_ORDER_DATE1=baseResponse.getElementsByTagName("PASS_ORDER_DATE")[0].firstChild.nodeValue;
              var PASS_ORDER_PREPARED_BY1=baseResponse.getElementsByTagName("PASS_ORDER_PREPARED_BY")[0].firstChild.nodeValue;
              var employeeName1=baseResponse.getElementsByTagName("employeeName")[0].firstChild.nodeValue;
              var REMARKS1=baseResponse.getElementsByTagName("REMARKS")[0].firstChild.nodeValue;
              
            //  var finalamount=display[0].getElementsByTagName("billamount")[0].firstChild.nodeValue;
           //   document.forms[0].txtTotalAmt.value=finalamount;
              
             document.getElementById("txtCrea_date").value=PASS_ORDER_DATE1;
             document.getElementById("txtPass_order_preparedBy").value=employeeName1;
             document.getElementById("txtPass_order_preparedByEmpcode").value=PASS_ORDER_PREPARED_BY1;
             document.getElementById("txtRemarks").value=REMARKS1;
             document.getElementById("txtTotalAmt").value=PassOrderAmt;
              
  
              
               for(k=0;k<display.length;k++)
              { 
            	  record1[k]=display[k].getElementsByTagName("billno")[0].firstChild.nodeValue;
                  /*record2[k]=display[k].getElementsByTagName("majorcode")[0].firstChild.nodeValue;                                
                  record3[k]=display[k].getElementsByTagName("majordesc")[0].firstChild.nodeValue;
                  record4[k]=display[k].getElementsByTagName("minorcode")[0].firstChild.nodeValue;                                
                  record5[k]=display[k].getElementsByTagName("minordesc")[0].firstChild.nodeValue;
                  record6[k]=display[k].getElementsByTagName("subcode")[0].firstChild.nodeValue;                                
                  record7[k]=display[k].getElementsByTagName("subdesc")[0].firstChild.nodeValue;
           */
                  
                  record8[k]=display[k].getElementsByTagName("billdate")[0].firstChild.nodeValue;                                
                  record9[k]=display[k].getElementsByTagName("billamount")[0].firstChild.nodeValue;
                  record10[k]=0;                                
                  record11[k]=display[k].getElementsByTagName("payto")[0].firstChild.nodeValue;
                //  record12[k]=display[k].getElementsByTagName("scrdate")[0].firstChild.nodeValue;                                
                  record13[k]=display[k].getElementsByTagName("rem")[0].firstChild.nodeValue;
                 
                  record14[k]=display[k].getElementsByTagName("SANCTION_PROC_NO")[0].firstChild.nodeValue;                                
                //  record15[k]=display[k].getElementsByTagName("PROCEEDING_RECD_DATE")[0].firstChild.nodeValue; 
                  
                  
                  var mycurrent_row=document.createElement("TR");
                  mycurrent_row.id=seq;
                  
                  /** Displaying Check Box For Cancellation */
                  
                 var cell=document.createElement("TD");
                  cell.align='CENTER';
                  var anc=document.createElement("input");
                  anc.type="checkbox";
                  //anc.value="CHECKED";
                  anc.checked=true;
                //  anc.setAttribute("onclick", "checkDisplay()");
                  anc.setAttribute('onclick', "checkDisplay(" + seq + ")");
                  anc.id="verify_select"+ seq;
                  anc.name="verify_select"; 
                  cell.appendChild(anc);
                  
                  var anc1=document.createElement("input");
                  anc1.type="hidden";
                  //anc.setAttribute("onclick", "checkDisplay()");
                  anc1.id="verify_select_status"+ seq;
                  anc1.name="verify_select_status"; 
                 // anc1.value="CHECKED";
                  anc1.checked=true;
                  cell.appendChild(anc1);
                  
                  mycurrent_row.appendChild(cell);         
                  
                  
               
                  
                  cell2=document.createElement("TD");
                  var tnodebillno=document.createElement("input");
                  tnodebillno.type="hidden";
                  tnodebillno.name="tnodebillno";
                  tnodebillno.id="tnodebillno"+ seq;
                  tnodebillno.value=record1[k];
                  cell2.appendChild(tnodebillno);
                  var currentText=document.createTextNode(record1[k]);
                  cell2.appendChild(currentText);
                  mycurrent_row.appendChild(cell2);
                  
                 /* cell2=document.createElement("TD");
                  var major=document.createElement("input");
                  major.type="hidden";
                  major.name="major1";
                  major.id="major1"+ seq;
                  major.value=record2[k];
                  cell2.appendChild(major);
                  var currentText=document.createTextNode(record3[k]);
                  cell2.appendChild(currentText);
                  mycurrent_row.appendChild(cell2);
                 
                  cell2=document.createElement("TD");
                  var minor=document.createElement("input");
                  minor.type="hidden";
                  minor.name="minor1";
                  minor.id="minor1"+ seq;
                  minor.value=record4[k];
                  cell2.appendChild(minor);
                  var currentText=document.createTextNode(record5[k]);
                  cell2.appendChild(currentText);
                  mycurrent_row.appendChild(cell2);
                  
                  cell2=document.createElement("TD");
                  var sub=document.createElement("input");
                  sub.type="hidden";
                  sub.name="sub1";
                  sub.id="sub1"+ seq;
                  sub.value=record6[k];
                  cell2.appendChild(sub);
                  var currentText=document.createTextNode(record7[k]);
                  cell2.appendChild(currentText);
                  mycurrent_row.appendChild(cell2);*/
                  
                  cell2 =document.createElement("TD"); 
                  var tnodehea1=document.createElement("input");
                  tnodehea1.type="hidden";
                  tnodehea1.name="billdatee";
                  tnodehea1.id="billdatee"+ seq;
                  tnodehea1.value=record8[k];
                  cell2.appendChild(tnodehea1);
                  var tnodbilldate=document.createTextNode(record8[k]);     
                  cell2.appendChild(tnodbilldate);       
                  mycurrent_row.appendChild(cell2);
                  
                  
                  var cell33=document.createElement("TD");
                  var billAmt=document.createElement("input");
                  billAmt.type="hidden";
                  billAmt.name="billAmt";
                  billAmt.id="billAmt"+ seq;
                  billAmt.value=record9[k];
                  cell33.appendChild(billAmt);
                  var currentText=document.createTextNode(record9[k]);
                  cell33.appendChild(currentText);
                  mycurrent_row.appendChild(cell33);
                  
//                  cell2 =document.createElement("TD");  
//                  var tnodehead1=document.createElement("input");
//                  tnodehead1.type="hidden";
//                  tnodehead1.name="tnodehead";
//                  tnodehead1.id="tnodehead"+ seq;
//                  tnodehead1.value=record10[k];
//                  cell2.appendChild(tnodehead1);
//                  var tnodehead=document.createTextNode(record10[k]);                         
//                  cell2.appendChild(tnodehead);       
//                  mycurrent_row.appendChild(cell2);
                          
                  cell2 =document.createElement("TD");   
                  var tpayable1=document.createElement("input");
                  tpayable1.type="hidden";
                  tpayable1.name="tpayable";
                  tpayable1.id="tpayable"+ seq;
                  tpayable1.value=record11[k];
                  cell2.appendChild(tpayable1);
                  var tpayable=document.createTextNode(record11[k]);                         
                  cell2.appendChild(tpayable);       
                  mycurrent_row.appendChild(cell2);
                  

                  
                /*  cell2 =document.createElement("TD"); 
                  var scrDate1=document.createElement("input");
                  scrDate1.type="hidden";
                  scrDate1.name="scrDate";
                  scrDate1.id="scrDate"+ seq;
                  scrDate1.value=record12[k];
                  cell2.appendChild(scrDate1);
                  var scrDate=document.createTextNode(record12[k]);                         
                  cell2.appendChild(scrDate);       
                  mycurrent_row.appendChild(cell2);*/
         
                 /* cell2 =document.createElement("TD");   
                  var tremarks1=document.createElement("input");
                  tremarks1.type="hidden";
                  tremarks1.name="tremarks";
                  tremarks1.id="tremarks"+ seq;
                  tremarks1.value=record13[k];
                  cell2.appendChild(tremarks1);
                  var tremarks=document.createTextNode(record13[k]);                         
                  cell2.appendChild(tremarks);       
                  mycurrent_row.appendChild(cell2);
                  */
                  
                 var cell12 =document.createElement("TD");   
                 cell12.style.display="none";
                  var tremark1=document.createElement("input");
                  tremark1.type="hidden";
                  tremark1.name="SANCTION_PROC_NO1";
                  tremark1.id="SANCTION_PROC_NO1"+ seq;
                  tremark1.value=record14[k];
                  cell12.appendChild(tremark1);
                  var tremar=document.createTextNode(record14[k]);                         
                  cell12.appendChild(tremar);       
                  mycurrent_row.appendChild(cell12);
                  
                 /* var cell20 =document.createElement("TD");  
                  cell20.style.display="none";
                  var tremars1=document.createElement("input");
                  tremars1.type="hidden";
                  tremars1.name="PROCEEDING_RECD_DATE1";
                  tremars1.id="PROCEEDING_RECD_DATE1"+ seq;
                  tremars1.value=record15[k];
                  cell20.appendChild(tremars1);
                  var trerks=document.createTextNode(record15[k]);                         
                  cell20.appendChild(trerks);       
                  mycurrent_row.appendChild(cell20);*/
                  
                 
	              /*  var cell=document.createElement("TD");
	                cell.align='CENTER';
	                var anc=document.createElement("A");
	                var Ucode=document.getElementById("cmbAcc_UnitCode").value;
	                var Offid=document.getElementById("cmbOffice_code").value;
	                var txtCB_Year=document.getElementById("cbyear").value;
	                var txtCB_Month=document.getElementById("cbmonth").value;
	                var url="javascript:Show_new('"+Ucode+"','"+Offid+"','"+txtCB_Year+"','"+txtCB_Month+"','"+record1[k]+"')";
	                anc.href=url;
	                var txtedit=document.createTextNode("DETAILS");
	                anc.appendChild(txtedit);
	                cell.appendChild(anc);
	                mycurrent_row.appendChild(cell);*/
                 
                  
                  tbody.appendChild(mycurrent_row);
                  
                  seq++;
              }
             
             
    }
    else
    {
    	alert("No Data Found");
 	   var tbody=document.getElementById("tbody");
 	   try{tbody.innerHTML="";
 	   }catch(e) {tbody.innerText="";}
    }
}

var Voucher_list_SL;

function Show_new(unitcode,offid,yr,mon,billno)
{
    if (Voucher_list_SL && Voucher_list_SL.open && !Voucher_list_SL.closed) 
    {
       Voucher_list_SL.resizeTo(500,500);
       Voucher_list_SL.moveTo(250,250); 
       Voucher_list_SL.focus();
    }
    else
    {
        Voucher_list_SL=null
    }
    Voucher_list_SL= window.open("../../../../../org/FAS/FAS1/CivilBills/jsps/memo_list.jsp?cmbAcc_UnitCode="+unitcode+"&cmbOffice_code="+offid+"&yr="+yr+"&mon="+mon+"&billno="+billno,"ReceiptDateSearch","status=1,height=500,width=500,resizable=YES, scrollbars=yes"); 
    Voucher_list_SL.moveTo(250,250);  
    Voucher_list_SL.focus();
    
}

function checkStatus(){
    var checkbox = document.getElementsByName('verify_select');
    var chvalue=document.getElementsByName("verify_select_status");
    var ln = checkbox.length;
   // alert("ln ... "+ln);
	for(var i=0;i<ln;i++){
	try{
		// alert(ln+" "+chvalue.length);
	if(checkbox[i].checked==true){
		checkbox[i].value="CHECKED";
		chvalue[i].value="CHECKED";
		
	}else{
		checkbox[i].value="UNCHECKED";
		chvalue[i].value="UNCHECKED";
	}
	}catch(e){
		alert(e);
	}

	}
}

function loadValuesFromTable(chk,major,minor)
{
	
	 clearAll();
	 checkcode=chk;
     var r=document.getElementById(checkcode);
     var rcells=r.cells;
     var tbody=document.getElementById("tblList");
     var table=document.getElementById("Existing");
     
     document.forms[0].checkCode.value=checkcode;
     document.forms[0].checkDesc.value=rcells.item(2).firstChild.nodeValue;
     document.forms[0].billmajortype.value=rcells.item(3).childNodes.item(0).value;
    
    
     listMinor(rcells.item(3).childNodes.item(0).value,rcells.item(4).childNodes.item(0).value);
   //  document.forms[0].billminortype.value=rcells.item(4).childNodes.item(0).value;
     //document.forms[0].billminortype.value=minor;
     var rad1=rcells.item(5).firstChild.nodeValue;
     var rad2=rcells.item(6).firstChild.nodeValue;
     if(rad1=="Y")
     { 
    	 document.forms[0].checkmandate[0].checked=true;
     }
     else
         document.forms[0].checkmandate[1].checked=true;
     
     if(rad2=="Y")
     {
         document.forms[0].notapply[0].checked=true;}
      else
         document.forms[0].notapply[1].checked=true;
    
     document.forms[0].onadd.disabled=true;
	 document.forms[0].onedit.disabled=false;
	 document.forms[0].ondelete.disabled=false;
   
}

// added on 16aug2011
function numbersonly(e,t)
{
         var unicode=e.charCode? e.charCode : e.keyCode;
         if(unicode==13)
         {
	          try{t.blur(); }catch(e){}
	          return true;
        
         }
         if (unicode!=8 && unicode !=9)
         {
	          if (unicode<48||unicode>57 ) 
	          {
	                return false 
	          }
         }
}
function filter_real(evt,item,n,pre)
{
         var charCode = (evt.which) ? evt.which : event.keyCode
         // allow "." for one time 
         if(charCode==46)
         {                
                if(item.value.indexOf(".")<0)	return (item.value.length>0)?true:false;
                else return false;
         }
         if (!(charCode > 31 && (charCode < 48 || charCode > 57)))
         {
                        // to avoid over flow
                if(item.value.indexOf(".")<0)
                {
                        //alert("Length without . ="+item.value.length); 
                        return (item.value.length<n)?true:false;
                }
                // dont allow more than 2 precision no's after the point
                if(item.value.indexOf(".")>0)
                {
                        //alert("precision count ="+item.value.split(".")[1].length);
                        if(item.value.split(".")[1].length<pre) return true;
                        else return false;
                }
                return false;
         }else
         {
                return false;
         }
}

function servicepopup()
{
    if (winemp && winemp.open && !winemp.closed) 
    {
       winemp.resizeTo(500,600);
       winemp.moveTo(200,200); 
       winemp.focus();
       return ;
    }
    else
    {
        winemp=null
    }
    winemp= window.open("../../../../../org/HR/HR1/EmployeeMaster/jsps/EmpServicePopup.jsp","mywindow1","status=1,height=500,width=600,resizable=YES, scrollbars=yes"); 
    winemp.moveTo(250,250);  
    winemp.focus();
}
function doParentEmp(emp)
{
                document.passorPreparation.txtPass_order_preparedByEmpcode.value=emp;
                Load_emp_details();
}
function Load_emp_details()
{
        //alert("inside the loading emp details ***");
        var emp_id=document.getElementById("txtPass_order_preparedByEmpcode").value;
        var url="";
             url="../../../../../phone_master_servlet?command=loadempdetails&emp_id="+emp_id;
             //alert(url);
             var req=getTransport();
              req.open("GET",url,true);        
              req.onreadystatechange=function()
              {
                       processResponse(req);
              }   
              req.send(null);
}
function processResponse(req)
{   
    if(req.readyState==4)   // Completed
      {
          if(req.status==200)   // No error
          {   
              var baseResponse=req.responseXML.getElementsByTagName("response")[0];
              var tagCommand=baseResponse.getElementsByTagName("command")[0];
              var command=tagCommand.firstChild.nodeValue; 
              if(command=="loadempdetails")
              {
                    LoadEmpDetails(baseResponse);
              }
          }
      }
}
function LoadEmpDetails(baseResponse)
{
                 
                  var flag=baseResponse.getElementsByTagName("flag")[0].firstChild.nodeValue; 
                  if(flag=="success")
                  {                       
                         var emp_name=baseResponse.getElementsByTagName("emp_name")[0].firstChild.nodeValue;
                         var desig_name=baseResponse.getElementsByTagName("desig_name")[0].firstChild.nodeValue;
                         var office_name=baseResponse.getElementsByTagName("office_name")[0].firstChild.nodeValue;
                                document.passorPreparation.txtPass_order_preparedBy.value=emp_name+"      "+desig_name;
                }
                else if(flag=="nodata")
                {
                        alert("Invalid Employee Id");
                }
                else
                {
                        alert("Failed to load");
                }
}
function dispDetails()
{
    
        if(document.getElementById("txtCrea_date").value=="")
        {
            alert("Pass order date prepared should not be empty");  
            return false;
          //  document.getElementById("txtCrea_date").focus();
        }
        else if(document.getElementById("txtPass_order_preparedByEmpcode").value=="")
        {
            alert("Pass order done by should not be empty");  
            return false;
          //  document.getElementById("txtPass_order_preparedByEmpcode").focus();
        }
        else if(document.getElementById("txtTotalAmt").value=="")
        {
            alert("Pass order Amount should not be empty");  
            return false;
         //   document.getElementById("txtTotalAmt").focus();
        }
        else
        {
            document.getElementById("passSeal").focus();
            var podate=document.getElementById("txtCrea_date").value;
            var poprepby=document.getElementById("txtPass_order_preparedBy").value;
            var poamt=document.getElementById("txtTotalAmt").value;
            var remk=podate+"   "+poprepby+"    "+poamt;
            document.getElementById("passSeal").value="Pass Order Prepared On  "+podate+"\n"+"Pass Order Prepared by    "+poprepby+"\n"+"Pass Order Amount   "+poamt;
        }
       // document.getElementById("txtRemarks").focus();
}
function checkNull()
{
    var billamt=0;
    var len=document.getElementById("cbyear").value.length;
    
    if(document.getElementById("cmbAcc_UnitCode").value=="")
    {
        alert("Select the Accounting unit Id");
        document.getElementById("cmbAcc_UnitCode").focus();
        return false;
    }
    else if(document.getElementById("cmbOffice_code").value=="")
    {
        alert("Select the Accounting unit Office Id");
        document.getElementById("cmbOffice_code").focus();
        return false;
    }
    else if( (document.getElementById("cbyear").value=="") || (len<4) )
    {
        alert("Enter the Cash Book Year in yyyy format ");
        document.getElementById("cbyear").focus();
        return false;
    }
    else if(document.getElementById("cbmonth").value=="")
    {
        alert("Enter the Cash Book Month");
        document.getElementById("cbmonth").focus();
        return false;
    }
    else if(document.getElementById("txtCrea_date").value=="")
    {
        alert("Select the Pass Order Prepared Date");
        document.getElementById("txtCrea_date").focus();
        return false;
    }
    else if(document.getElementById("txtPass_order_preparedByEmpcode").value=="")
    {
        alert("Select the Pass Order Prepared by");
        document.getElementById("txtPass_order_preparedByEmpcode").focus();
        return false;
    }
    else if(document.getElementById("txtTotalAmt").value=="")
    {
        alert("Enter the Total Amount");
        document.getElementById("txtTotalAmt").focus();
        return false;
    }
    
    var passamt=document.getElementById("txtTotalAmt").value;
    var tbody=document.getElementById("tbody");
    var chk=0;
    var s_billinc=0;
    checkStatus();
    dispDetails();
    if(tbody.rows.length>0)
    {
    	
            rows=tbody.getElementsByTagName("tr");
            for(i=0;i<rows.length;i++)
            {
                var cells=rows[i].cells;
               
                if(cells.item(0).firstChild.checked==true)
                {
                	
                	chk++;
                	 billamt=parseFloat(billamt)+parseFloat(cells.item(3).firstChild.value); 	
                	 var billdate_grid=cells.item(2).firstChild.value;
                	
                	 var biisp_grid=billdate_grid.split("/");
                	 var passorder_date=document.getElementById("txtCrea_date").value;
                	 
                	 var passsplit=passorder_date.split("/");
                	 if(biisp_grid[2]>passsplit[2])
                	 {
                		 s_billinc++;
                		
                	 }
                	 else if(biisp_grid[2]==passsplit[2])
                	 {
                		// alert("bi::"+biisp_grid[1]);
                		// alert("pa:"+passsplit[1]);
	                	 if(biisp_grid[1]>passsplit[1])
	                	 {
	                		 s_billinc++;
	                	 }
	                	 else if(biisp_grid[1]==passsplit[1])
	                	 {
	                		 if(biisp_grid[0]>passsplit[0])
		                	 {
		                		 s_billinc++;
		                	 } 
	                	 }
                	 }
                }
            }
            if(s_billinc>0)
            {
            	 alert("Pass Order Date should be Greater than Bill Date");
        		 document.getElementById("txtCrea_date").value="";
        		 return false;
            }
            if(chk==0)
            {
            	alert("Select the BillNo");
            	return false;
            }
            
            if(parseFloat(billamt)!=parseFloat(passamt))
            {
            	alert("BillAmount should be equal to PassOrder Amount");
            	//alert("BillAmount should be less than or equal to PassOrder Amount");
            	document.getElementById("txtTotalAmt").value="";
            	return false;
            }
            
    }
    
    return true;
}

function callemp(path)
{
	var txtEmpID_mas = document.getElementById("txtPass_order_preparedByEmpcode").value;
	var cmbOffice_code = document.getElementById("cmbOffice_code").value;
	
		var url = path+ "/Bills_Token_Register_with_SP?command=getempname_off&txtEmpID_mas="+ txtEmpID_mas+"&cmbOffice_code="+cmbOffice_code;
		// alert(url);
		var xmlrequest = AjaxFunction();
		xmlrequest.open("POST", url, true);
		xmlrequest.onreadystatechange = function() {
			manipulate(xmlrequest);
		}

		xmlrequest.send(null);

}

function getempname_re(baseResponse)
{
	var flag = baseResponse.getElementsByTagName("flag")[0].firstChild.nodeValue;
	if (flag == "success") 
	{
		var empname = baseResponse.getElementsByTagName("empname")[0].firstChild.nodeValue;
		var empid=baseResponse.getElementsByTagName("empid")[0].firstChild.nodeValue;
		var empname=baseResponse.getElementsByTagName("empname")[0].firstChild.nodeValue;
		document.getElementById("txtPass_order_preparedBy").value=empname;
		dispDetails();
		
	}
	else
	{
		alert("Enter Relevant EmployeeId For This Office");
		document.getElementById("txtPass_order_preparedBy").value="";
		document.getElementById("txtPass_order_preparedByEmpcode").value="";
	}
}

function selectAll(Opt)
{

  var len=  document.getElementById("tbody").rows.length;
   // alert("ssssssssss"+len);
  if(len==1)
  {var passAmt1=0;
          if (Opt =="ALL")
          {
        	 document.passorPreparation.verify_select.checked=true;
        	 var pass1=document.getElementById("billAmt").value; 
				passAmt1=parseInt(passAmt1)+parseInt(pass1);
				 document.forms[0].txtTotalAmt.value=passAmt1;  
          }
          else if (Opt=="UNSelect" )
          {
          document.passorPreparation.verify_select.checked=false;
          var pass1=document.getElementById("billAmt").value; 
			passAmt1=parseInt(passAmt1)-parseInt(pass1);
			 document.forms[0].txtTotalAmt.value=passAmt1;  
          }
  }
  else if(len>1)
  {
	  var passAmt1=0;
	  //alert("len "+len);
          for(var i=0;i<len;i++)
          {
        	 // alert(" i"+i);
                if ( Opt=="ALL")
                {
                    document.passorPreparation.verify_select[i].checked=true;
                    var pass1=document.getElementById("billAmt" + i).value; 
					passAmt1=parseInt(passAmt1)+parseInt(pass1);
					 document.forms[0].txtTotalAmt.value=passAmt1;  
                    
                }
                else if(Opt=="UNSelect")
                {
                	 var passAmt1=0;	
                    document.passorPreparation.verify_select[i].checked=false;
                    
                    var pass1=0; 
					passAmt1=parseInt(pass1)-parseInt(passAmt1);
					 document.forms[0].txtTotalAmt.value=passAmt1;   
                }
          }
  }

}
