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
        var url="../../../../../PassOrderPreparation_servlet?command=loadGrid&unitid="+unitid+"&offid="+offid+"&cbyear="
        +cbyear+"&cbmonth="+cbmonth;
        var xmlrequest= AjaxFunction();
        xmlrequest.open("GET",url,true);              
        xmlrequest.onreadystatechange=function()
        {
            manipulate(xmlrequest);
        }
        xmlrequest.send(null);
}





function callServer(command,param)
{
	 var url="";
    if(command=="Add")
       {           
    	    var unitid=document.forms[0].cmbAcc_UnitCode.value;
    	    var offid=document.forms[0].cmbOffice_code.value;
    	    var checkDesc=document.forms[0].checkDesc.value;
    	    var major=document.forms[0].billmajortype.value;
    	    var minor=document.forms[0].billminortype.value;
    	    var mand;
    	    if(document.forms[0].checkmandate[0].checked==true)
    	        {
    	    		mand="Y";
    	        }
    	    else
    	        {
    	    		mand="N";
    	        }
    	    var apply;
    	    if(document.forms[0].notapply[0].checked==true)
    	        {
    	    		apply="Y";
    	        }
    	    else
    	        {
    	    		apply="N";
    	        }
			   var flag=nullCheck();
			  if(flag==true)
               {
            	   var xmlrequest= AjaxFunction();
                   url="../../../../../BillScrutiny_serv?command=add&unitid="+unitid+"&offid="+offid+"&major2="+major+"&minor="+minor+"&checkDesc="+checkDesc+"&mand="+mand+"&apply="+apply;
                   xmlrequest.open("GET",url,true);              
                   xmlrequest.onreadystatechange=function()
                       {
                           manipulate(xmlrequest);
                       }
                   xmlrequest.send(null);
               }
                  
                   
       }
       else if(command=="Update")
       {
		    	   var unitid=document.forms[0].cmbAcc_UnitCode.value;
		    	    var offid=document.forms[0].cmbOffice_code.value;
		    	    var checkDesc=document.forms[0].checkDesc.value;
		    	    var major=document.forms[0].billmajortype.value;
		    	    
		    	    var minor=document.forms[0].billminortype.value;
		    	    
		    	   var ck=document.forms[0].checkCode.value;
    	           
    	           var mand;
    	           if(document.forms[0].checkmandate[0].checked==true)
    	               {
    	           		mand="Y";
    	               }
    	           else
    	               {
    	           		mand="N";
    	               }
    	           var apply;
    	           if(document.forms[0].notapply[0].checked==true)
    	               {
    	           		apply="Y";
    	               }
    	           else
    	               {
    	           		apply="N";
    	               }
                   var flag=nullCheck();
                   if(flag==true)
                   {
                   url="../../../../../BillScrutiny_serv?command=updated&unitid="+unitid+"&offid="+offid+"&major2="+major+"&minor="+minor+"&checkDesc="+checkDesc+"&mand="+mand+"&apply="+apply+"&checkcode="+ck;
                   var xmlrequest= AjaxFunction();
                   xmlrequest.open("GET",url,true);              
                   xmlrequest.onreadystatechange=function()
                   {
                       manipulate(xmlrequest);
                   }
                   xmlrequest.send(null);
                   }

       }
       
       else if(command=="Delete")
       {  
			    	   var unitid=document.forms[0].cmbAcc_UnitCode.value;
			   	    var offid=document.forms[0].cmbOffice_code.value;
    	   		  var ck=document.forms[0].checkCode.value;
    	   		  url="../../../../../BillScrutiny_serv?command=deleted&unitid="+unitid+"&offid="+offid+"&checkcode="+ck;
    	   		var xmlrequest= AjaxFunction();
    	        xmlrequest.open("GET",url,true);              
    	        xmlrequest.onreadystatechange=function()
    	        {
    	            manipulate(xmlrequest);
    	        }
    	        xmlrequest.send(null);
       }
       else if(command=="Get")
       {             
		    	//  url="../../../../../BillScrutiny_serv?command=Get&unitid="+unitid+"&offid="+offid;
    	   		  url="../../../../../BillScrutiny_serv?command=Get";
		          var xmlrequest= AjaxFunction();
		          xmlrequest.open("GET",url,true);              
		          xmlrequest.onreadystatechange=function()
		          {
		              manipulate(xmlrequest);
		          }
		          xmlrequest.send(null);
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
           if(command=="add")
              {
        	   	 addRow(baseResponse);
        	   //	alert("Record Inserted successfully.");
        	      // clearAll();
           }
           else if(command=="getname_employee")
           {
     	   	 addRow(baseResponse);
     	   }
           
           else if (command == "getempname_off") {
				 //alert("manipulate");
				getempname_re(baseResponse);
			}
           else if(command=="loadGrid")
           {
        	   loadGridchecking(baseResponse);
           }
           else if(command=="updated")
           {
        	   updateRow(baseResponse);
        	   clearAll();
           }
           else if(command=="deleted")
           { 
        	   deleteRow(baseResponse);
        	   clearAll();
           }
           else if(command=="Get")
           { 
        	   getRow(baseResponse);
           }
           else if(command=="listminor")
           { 
        	   listminorRow(baseResponse);
        	//   minortypechecking(baseResponse);
           }
      }
  }
}
var passAmt=0;
function checkDisplay(sam) {
	
	var amt = 0;
	var fg = 0;
	var fg1 = 0;
	var ii = 0;
	if (document.getElementById("verify_select" + sam).checked == true) {
		
		var tbody = document.getElementById("tbody");
		var rowcount = tbody.rows.length;
              
		for ( var i = 0; i < rowcount; i++) {
			var r = tbody.rows[i];
		
			if (document.getElementById("tnodebillno" + sam).value != "") {
                       
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
	else
	{
		
		var tbody = document.getElementById("tbody");
		var rowcount = tbody.rows.length;
              
		for ( var i = 0; i < rowcount; i++) {
			var r = tbody.rows[i];
		
			if (document.getElementById("tnodebillno" + sam).value != "") {
                       
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
            //  var finalamount=display[0].getElementsByTagName("billamount")[0].firstChild.nodeValue;
           //   document.forms[0].txtTotalAmt.value=finalamount;
               for(k=0;k<display.length;k++)
              { 
            	  record1[k]=display[k].getElementsByTagName("billno")[0].firstChild.nodeValue;
                  record2[k]=display[k].getElementsByTagName("majorcode")[0].firstChild.nodeValue;                                
                  record3[k]=display[k].getElementsByTagName("majordesc")[0].firstChild.nodeValue;
                  record4[k]=display[k].getElementsByTagName("minorcode")[0].firstChild.nodeValue;                                
                  record5[k]=display[k].getElementsByTagName("minordesc")[0].firstChild.nodeValue;
                  record6[k]=display[k].getElementsByTagName("subcode")[0].firstChild.nodeValue;                                
                  record7[k]=display[k].getElementsByTagName("subdesc")[0].firstChild.nodeValue;
           
                  
                  record8[k]=display[k].getElementsByTagName("billdate")[0].firstChild.nodeValue;                                
                  record9[k]=display[k].getElementsByTagName("billamount")[0].firstChild.nodeValue;
                  record10[k]=0;                                
                  record11[k]=display[k].getElementsByTagName("payto")[0].firstChild.nodeValue;
                  record12[k]=display[k].getElementsByTagName("scrdate")[0].firstChild.nodeValue;   
                  if(display[k].getElementsByTagName("rem")[0].firstChild!=null)                             
                  	record13[k]=display[k].getElementsByTagName("rem")[0].firstChild.nodeValue;
                  else
                  	record13[k]='';
                 
                  record14[k]=display[k].getElementsByTagName("SANCTION_PROC_NO")[0].firstChild.nodeValue;                                
                  record15[k]=display[k].getElementsByTagName("PROCEEDING_RECD_DATE")[0].firstChild.nodeValue; 
                  
                  
                  var mycurrent_row=document.createElement("TR");
                  mycurrent_row.id=seq;
                  
                  /** Displaying Check Box For Cancellation */
                  
                 var cell=document.createElement("TD");
                  cell.align='CENTER';
                  var anc=document.createElement("input");
                  anc.type="checkbox";
                  anc.value="CHECKED";
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
                  
                  cell2=document.createElement("TD");
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
                  mycurrent_row.appendChild(cell2);
                  
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
                  

                  
                  cell2 =document.createElement("TD"); 
                  var scrDate1=document.createElement("input");
                  scrDate1.type="hidden";
                  scrDate1.name="scrDate";
                  scrDate1.id="scrDate"+ seq;
                  scrDate1.value=record12[k];
                  cell2.appendChild(scrDate1);
                  var scrDate=document.createTextNode(record12[k]);                         
                  cell2.appendChild(scrDate);       
                  mycurrent_row.appendChild(cell2);
         
                  cell2 =document.createElement("TD");   
                  var tremarks1=document.createElement("input");
                  tremarks1.type="hidden";
                  tremarks1.name="tremarks";
                  tremarks1.id="tremarks"+ seq;
                  tremarks1.value=record13[k];
                  cell2.appendChild(tremarks1);
                  var tremarks=document.createTextNode(record13[k]);                         
                  cell2.appendChild(tremarks);       
                  mycurrent_row.appendChild(cell2);
                  
                  
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
                  
                  var cell20 =document.createElement("TD");  
                  cell20.style.display="none";
                  var tremars1=document.createElement("input");
                  tremars1.type="hidden";
                  tremars1.name="PROCEEDING_RECD_DATE1";
                  tremars1.id="PROCEEDING_RECD_DATE1"+ seq;
                  tremars1.value=record15[k];
                  cell20.appendChild(tremars1);
                  var trerks=document.createTextNode(record15[k]);                         
                  cell20.appendChild(trerks);       
                  mycurrent_row.appendChild(cell20);
                  
                 
	                var cell=document.createElement("TD");
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
	                mycurrent_row.appendChild(cell);
                 
                  
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
function addRow(baseResponse)
{
  var flag=baseResponse.getElementsByTagName("flag")[0].firstChild.nodeValue;
   if(flag=="success")
   {   
       alert("Record Inserted Successfully.");
       
       var checkcode=baseResponse.getElementsByTagName("checkcode")[0].firstChild.nodeValue;
       var checkDesc=baseResponse.getElementsByTagName("checkdesc")[0].firstChild.nodeValue;
       var mandate=baseResponse.getElementsByTagName("mandate")[0].firstChild.nodeValue;
       
       var notapply=baseResponse.getElementsByTagName("notapply")[0].firstChild.nodeValue;
       
       var majorCode=baseResponse.getElementsByTagName("majorCode")[0].firstChild.nodeValue;
       
       var majorDesc=baseResponse.getElementsByTagName("majorDesc")[0].firstChild.nodeValue;
       var minorCode=baseResponse.getElementsByTagName("minorCode")[0].firstChild.nodeValue;
       var minorDesc=baseResponse.getElementsByTagName("minorDesc")[0].firstChild.nodeValue;
       
           var items=new Array();
           
           items[0]=checkcode;
           items[1]=checkDesc;
           
           items[2]=majorCode;
           items[3]=majorDesc;
           items[4]=minorCode;
           items[5]=minorDesc;
           
         var mand;
         if(mandate=="Y")
             {
         		mand="Y";
             }
         else
             {
         		mand="N";
             }
         var apply;
         if(notapply=="Y")
             {
         		apply="Y";
             }
         else
             {
         		apply="N";
             }
           
           items[6]=mand;
           items[7]=apply;
                     
            var r=document.getElementById(checkcode);
            
            var rcells=r.cells;
            rcells.item(1).firstChild.nodeValue=items[0];
            rcells.item(2).firstChild.nodeValue=items[1];
       
            rcells.item(3).childNodes.item(0).value=items[2];
            rcells.item(3).lastChild.nodeValue=items[3];
         
            rcells.item(4).childNodes.item(0).value=items[4];
            rcells.item(4).lastChild.nodeValue=items[5];
            
            rcells.item(5).firstChild.nodeValue=items[6];
            rcells.item(6).firstChild.nodeValue=items[7];
           
             document.forms[0].onadd.disabled=false;
	       	 document.forms[0].onedit.disabled=true;
	       	 document.forms[0].ondelete.disabled=true;
	       	 
	  
    }
}


function updateRow(baseResponse)
{
  var flag=baseResponse.getElementsByTagName("flag")[0].firstChild.nodeValue;
   if(flag=="success")
   {   
       alert("Record Updated Successfully.");
       
       var checkcode=baseResponse.getElementsByTagName("checkcode")[0].firstChild.nodeValue;
       var checkDesc=baseResponse.getElementsByTagName("checkdesc")[0].firstChild.nodeValue;
       var mandate=baseResponse.getElementsByTagName("mandate")[0].firstChild.nodeValue;
       
       var notapply=baseResponse.getElementsByTagName("notapply")[0].firstChild.nodeValue;
       
       var majorCode=baseResponse.getElementsByTagName("majorCode")[0].firstChild.nodeValue;
       
       var majorDesc=baseResponse.getElementsByTagName("majorDesc")[0].firstChild.nodeValue;
       var minorCode=baseResponse.getElementsByTagName("minorCode")[0].firstChild.nodeValue;
       var minorDesc=baseResponse.getElementsByTagName("minorDesc")[0].firstChild.nodeValue;
       
           var items=new Array();
           
           items[0]=checkcode;
           items[1]=checkDesc;
           
           items[2]=majorCode;
           items[3]=majorDesc;
           items[4]=minorCode;
           items[5]=minorDesc;
           
         var mand;
         if(mandate=="Y")
             {
         		mand="Y";
             }
         else
             {
         		mand="N";
             }
         var apply;
         if(notapply=="Y")
             {
         		apply="Y";
             }
         else
             {
         		apply="N";
             }
           
           items[6]=mand;
           items[7]=apply;
                     
            var r=document.getElementById(checkcode);
            
            var rcells=r.cells;
            rcells.item(1).firstChild.nodeValue=items[0];
            rcells.item(2).firstChild.nodeValue=items[1];
       
            rcells.item(3).childNodes.item(0).value=items[2];
            rcells.item(3).lastChild.nodeValue=items[3];
         
            rcells.item(4).childNodes.item(0).value=items[4];
            rcells.item(4).lastChild.nodeValue=items[5];
            
            rcells.item(5).firstChild.nodeValue=items[6];
            rcells.item(6).firstChild.nodeValue=items[7];
           
             document.forms[0].onadd.disabled=false;
	       	 document.forms[0].onedit.disabled=true;
	       	 document.forms[0].ondelete.disabled=true;
	       	 
	  
    }
}


function getRow(baseResponse)
{
	var flag=baseResponse.getElementsByTagName("flag")[0].firstChild.nodeValue; 
	
    if(flag=="success")
    {          
             var tbody=document.getElementById("tblList");
             var table=document.getElementById("Existing");
              var t=0;
              for(t=tbody.rows.length-1;t>=0;t--)
              {
                 tbody.deleteRow(0);
              }
              var len=baseResponse.getElementsByTagName("checkcode").length;              
              for(var k=0;k<len;k++)
              {
            	
            	  var checkcode = baseResponse.getElementsByTagName("checkcode")[k].firstChild.nodeValue;
                  var checkdesc = baseResponse.getElementsByTagName("checkdesc")[k].firstChild.nodeValue;
                  var mandate = baseResponse.getElementsByTagName("mandate")[k].firstChild.nodeValue;
                  var apply = baseResponse.getElementsByTagName("notapply")[k].firstChild.nodeValue;
                  var majorDesc = baseResponse.getElementsByTagName("majorDesc")[k].firstChild.nodeValue;
                  var minorDesc = baseResponse.getElementsByTagName("minorDesc")[k].firstChild.nodeValue;
                  var majorCode=baseResponse.getElementsByTagName("majorCode")[k].firstChild.nodeValue;
                  var minorCode=baseResponse.getElementsByTagName("minorCode")[k].firstChild.nodeValue;
                  
                  var items=new Array();
                  items[0]=majorCode;
                  items[1]=majorDesc;
                  items[2]=minorCode;
                  items[3]=minorDesc;
                  
                  var mycurrent_row=document.createElement("TR");
                  mycurrent_row.id=checkcode;
                  cell2=document.createElement("TD");
                  cell2.setAttribute('align','left');
                  var anc=document.createElement("A");    
                   // var url="javascript:loadValuesFromTable('" +checkcode+ "')";              
                  var url="javascript:loadValuesFromTable("+checkcode+","+majorCode+","+minorCode+")";
                  anc.href=url;
                  var txtedit=document.createTextNode("Edit");
                  anc.appendChild(txtedit);
                  cell2.appendChild(anc);
                  mycurrent_row.appendChild(cell2);
                  
                  cell2 =document.createElement("TD");
                  cell2.setAttribute('align','right');
                  var checkcode=document.createTextNode(checkcode);     
                  cell2.appendChild(checkcode);       
                  mycurrent_row.appendChild(cell2); 
                  
                  cell2 =document.createElement("TD");
                  cell2.setAttribute('align','left');
                  var checkDesc=document.createTextNode(checkdesc);     
                  cell2.appendChild(checkDesc);       
                  mycurrent_row.appendChild(cell2);
                  
                  cell2=document.createElement("TD");
                  cell2.setAttribute('align','left');
                  var mcode=document.createElement("input");
                  mcode.type="hidden";
                  mcode.name="majorcode";
                  mcode.value=items[0];
                  cell2.appendChild(mcode);
                  var currentText=document.createTextNode(items[1]);
                  cell2.appendChild(currentText);
                  mycurrent_row.appendChild(cell2);
                  
                  cell2=document.createElement("TD");
                  cell2.setAttribute('align','left');
                  var micode=document.createElement("input");
                  micode.type="hidden";
                  micode.name="minorcode";
                  micode.value=items[2];
                  cell2.appendChild(micode);
                  var currentText=document.createTextNode(items[3]);
                  cell2.appendChild(currentText);
                  mycurrent_row.appendChild(cell2);
                 
                  cell2 =document.createElement("TD"); 
                  cell2.setAttribute('align','left');
                  var checkmandate=document.createTextNode(mandate);                         
                  cell2.appendChild(checkmandate);       
                  mycurrent_row.appendChild(cell2);

                  cell2 =document.createElement("TD");  
                  cell2.setAttribute('align','left');
                  var notapply=document.createTextNode(apply);                         
                  cell2.appendChild(notapply);       
                  mycurrent_row.appendChild(cell2);
                  
                  tbody.appendChild(mycurrent_row);
                  
              }

    }
}

function deleteRow(baseResponse)
{
                var flag=baseResponse.getElementsByTagName("flag")[0].firstChild.nodeValue;
                if(flag=="success")
                {
                    
                    var tbody=document.getElementById("Existing");     
                    var r=document.getElementById(checkcode);    
                    var ri=r.rowIndex;               
                    tbody.deleteRow(ri); 
                                 
		                document.forms[0].onadd.disabled=false;
		   	       	 document.forms[0].onedit.disabled=true;
		   	       	 document.forms[0].ondelete.disabled=true;
                }
   	      else
          {
              alert("Unable to Delete");
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
function listMinor(major,minor)
{
	var url="../../../../../BillScrutiny_serv?command=listminor&major2="+major+"&minor="+minor;
    var xmlrequest= AjaxFunction();
    xmlrequest.open("GET",url,true);              
    xmlrequest.onreadystatechange=function()
    {
        manipulate(xmlrequest);
    }
    xmlrequest.send(null);  
}
function listminorRow(baseResponse)
{
	 var minorcmb = document.forms[0].billminortype;
     document.forms[0].billminortype.length=0;
     var minorcode = baseResponse.getElementsByTagName("minorcode");  
     var minordesc = baseResponse.getElementsByTagName("minordesc"); 
     for(var i=0; i<minorcode.length; i++)
       {
    	 		var opt1 = document.createElement('option');
                 opt1.value = minorcode[i].firstChild.nodeValue;
                 opt1.innerHTML = minordesc[i].firstChild.nodeValue; 
                 minorcmb.appendChild(opt1);
             
       }    
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
/*
function check_leng(param,val)
{	 
		if((val.length)>=190)
		{
			  if(param=='remarks')			  
				  	   alert("Please Enter Remarks below 200 characters");			           			  
			  else			  
				  	   alert("Please Enter Paticulars below 200 characters");
                          		  
		}
		
} */
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
        //alert("sathyaaaaaaaa");
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
    document.getElementById("butSub").disabled = true;
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
                	 billamt=parseFloat(billamt)+parseFloat(cells.item(6).firstChild.value); 	
                	 var billdate_grid=cells.item(8).firstChild.value;
                	
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
            	 alert("Pass Order Date should be Greater than Scrutiny Date");
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
function setEmp()
{
	var cmbOffice_code = document.getElementById("cmbOffice_code").value;
	var cmbAcc_UnitCode = document.getElementById("cmbAcc_UnitCode").value;
			var url = "../../../../../PassOrderPreparation_servlet?command=getname_employee&cmbOffice_code="+cmbOffice_code+"&cmbAcc_UnitCode="+cmbAcc_UnitCode;
		// alert(url);
		var xmlrequest = AjaxFunction();
		xmlrequest.open("get", url, true);
		xmlrequest.onreadystatechange = function() {
			manipulate(xmlrequest);
		}

		xmlrequest.send(null);
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
  {
          if (Opt =="ALL")
          {
        	 document.passorPreparation.checkNo.checked=true;
          
          }
          else if (Opt=="UNSelect" )
          {
          document.passorPreparation.checkNo.checked=false;
        
          }
  }
  else if(len>1)
  {
          for(var i=0;i<len;i++)
          {
                if ( Opt =="ALL")
                {
                    document.passorPreparation.checkNo[i].checked=true;
                }
                else if(Opt=="UNSelect")
                {
                    document.passorPreparation.checkNo[i].checked=false;
                }
          }
  }

}
