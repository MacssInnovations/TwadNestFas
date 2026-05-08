//alert("js");
var seq = 0;
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
	
var tbody=document.getElementById("tblList");

if(document.getElementById("cmbAcc_UnitCode").value=="")
{
    alert("Select the Account Unit code");
    return false;    
}
if(document.getElementById("cmbOffice_code").value=="")
{
    alert("Select the Office Code");
   return false;
}
if(document.getElementById("txtCrea_date").value.length==0)
{
    alert("Enter the Date of Creation");
    return false;    
}
	var date_one=document.getElementById("txtCrea_date").value;
	var spl_it=date_one.split("/");
	if(spl_it[2].length!=4)
	{
	alert("Please verify Date in General");
	}
	var listtype=document.getElementById("listtype").value;
	  if(listtype=="")
		  {
		  alert("Choose CheckList Type");
		  return false;
		  }
	 // alert(listtype);
	  var grouptype=document.getElementById("grouptype").value;
	  if(grouptype=="")
		  {
		  alert("Choose CheckList Group");
		  return false;
		  }
	 // alert(grouptype);
	  var penfamily=document.getElementById("penfamily").value;
	  if(penfamily=="")
	  {
	  alert("Choose Pensioner or Family");
	  return false;
	  }


if(tbody.rows.length==0)
{
    alert("Enter the Details Part");
   return false; 
}
if(tbody.rows.length>0)
{
//	alert('test here');
    var cr_amt=0;
        var db_amt=0;
        var ttGrid =new Array();
        rows=tbody.getElementsByTagName("tr");
       
        var crtotalvalue=document.getElementById("crtotal").value; 
        var drtotalvalue=document.getElementById("drtotal").value;
       // cr_amt=parseDouble(crtotalvalue); alert("cr_amt v "+cr_amt);
      //  dr_amt=parseDouble(drtotalvalue);
    //    alert("dr_amt v "+dr_amt);
        for(var i=0;i<rows.length;i++)
        {

        	  var cells=rows[i].cells;
        	 var headCell=cells.item(2).firstChild.value;
        	// alert("headCell >> "+headCell);
         	 //var headCell=  document.getElementById("head_code"+i).value;
         //	 alert("dr_amt v ");
         //	alert(cells.item(3).lastChild.nodeValue);
        	  //  alert(headCell);]\
         	if(cells.item(3).lastChild.nodeValue!=0 && cells.item(4).lastChild.nodeValue!=0)
        	    
         		{
         		
         		if(headCell=="0" || headCell=="" || headCell==null)
        	    {
        	    	
        	    	//alert("test");
        	    	 alert("Head Code Should be valid ");
        	    	  return false;
        	    }
         		}else{
         			if(headCell!="0")
         			{
         			//	alert("Don't Enter the HeadCode , If CT Amount & CR Amount is 0");
         				cells.item(3).lastChild.nodeValue=0;
         			}
         		}
        	
        	
          
           
           
            if(cells.item(4).lastChild.nodeValue=='CR')
            {
                   
                // cr_amt=parseFloat(cr_amt) + parseFloat(cells.item(2).lastChild.nodeValue);
            }
            else
            {
                  //  db_amt=parseFloat(db_amt) + parseFloat(cells.item(2).lastChild.nodeValue);
            }
    
        }
        //alert("cr_amt:"+crtotalvalue);
        //alert("db_amt:"+drtotalvalue);
        if( (crtotalvalue=="0") && (drtotalvalue=="0") )     // Either CR or DR Zero the nno need to submit														// equal in total
        {
        alert("The total CR and totlal DR is ZERO.So no need to submit");
        document.frmpensionJournal.butGo.disabled="true";
        //document.getElementById("submitdiv").style.display="block";
        return false;
        }
        else
        	{
        	//document.getElementById("submitdiv").style.display="none";
        	 document.frmpensionJournal.butGo.disabled="false";
        	// return true;
        	}
        //alert("coming here at lasttttttttttttt");

        if(parseDouble(crtotalvalue)!=parseDouble(drtotalvalue))      // Either CR or DR must														// equal in total
        {
        alert("Amount doesn't Tally.. Difference ");
        return false;
        }
           
}

return true;

}





function att()
{
	var x = document.getElementById("listtype").selectedIndex;
	var listtype = document.getElementById("listtype").options;
	var y = document.getElementById("grouptype").selectedIndex;
	var grouptype = document.getElementById("grouptype").options;
	document.getElementById("hid_chklist").value=listtype[x].text;
	document.getElementById("hid_Grplist").value=grouptype[y].text;
	
}

function listtype()
{
  var xmlrequest = getTransport();
 
	     var url ="../../../../../PensionJournal?command=listtype";
		//alert(url);
		xmlrequest.open("POST", url, true);
		xmlrequest.onreadystatechange = function() 
		{
	  if (xmlrequest.readyState == 4) 
		{
		if (xmlrequest.status == 200) 
		{
	//alert(xmlrequest.responseText);
	
	var baseResponse = xmlrequest.responseXML.getElementsByTagName("response")[0];
	//alert(baseResponse);
	var flag3 = baseResponse.getElementsByTagName("flag3")[0].firstChild.nodeValue;
	if (flag3 == "success") {
		var len45 = baseResponse.getElementsByTagName("ListID").length;
		if (len45 != 0) {
			for ( var i = 0; i < len45; i++) {
				var ListID = baseResponse
						.getElementsByTagName("ListID")[i].firstChild.nodeValue;
				var ListDesc = baseResponse
						.getElementsByTagName("ListDesc")[i].firstChild.nodeValue;

				var se = document.getElementById("listtype");
				var op = document.createElement("OPTION");
				op.value = ListID;
				var txt = document.createTextNode(ListDesc);
				op.appendChild(txt);
				se.appendChild(op);
			}
		} else {
			alert("Budget Desc Does Not Exist");
		}
	} else {
		alert("Fail to Load Desc");
	}
 
  /* if (flag3 == "Success1") {
		var len4 = baseResponse.getElementsByTagName("ListID").length;
		if (len4 != 0) {
			for ( var i = 0; i < len4; i++) {
				var ListID1 = baseResponse
						.getElementsByTagName("ListID")[i].firstChild.nodeValue;
				var ListDesc1 = baseResponse
						.getElementsByTagName("ListDesc")[i].firstChild.nodeValue;

				var se = document.getElementById("grouptype");
				var op = document.createElement("OPTION");
				op.value = ListID1;
				var txt = document.createTextNode(ListDesc1);
				op.appendChild(txt);
				se.appendChild(op);
			}
		} else {
			alert("Budget Desc Does Not Exist");
		}
	} else {
		alert("Fail to Load Desc");
	}*/
    }
  }
};
xmlrequest.send(null);
}

function listgroup()
{
	var cmbOffice_code=document.getElementById("cmbOffice_code").value;
	
     var xmlrequest = getTransport();
	 var url ="../../../../../PensionJournal?command=listgroup&cmbOffice_code="+cmbOffice_code;
	 
	xmlrequest.open("POST", url, true);
	xmlrequest.onreadystatechange = function() 
		{
	  if (xmlrequest.readyState == 4) 
		{
		if (xmlrequest.status == 200) 
		{	
		
	var baseResponse = xmlrequest.responseXML.getElementsByTagName("response")[0];
	
	var flag3 = baseResponse.getElementsByTagName("flag3")[0].firstChild.nodeValue;		

	if (flag3 == "Success1") {
		var len4 = baseResponse.getElementsByTagName("ListID").length;
		var se = document.getElementById("grouptype");
		se.length=0;
		if (len4 != 0) {
			
			for ( var i = 0; i < len4; i++) {
				var ListID1 = baseResponse.getElementsByTagName("ListID")[i].firstChild.nodeValue;
				var ListDesc1 = baseResponse.getElementsByTagName("ListDesc")[i].firstChild.nodeValue;
				var op = document.createElement("OPTION");
				op.value = ListID1;
				var txt = document.createTextNode(ListDesc1);
				op.appendChild(txt);
				se.appendChild(op);
			}
		} else {
			alert("Budget Desc Does Not Exist");
		}
	}else {
		
		var seee=document.getElementById("grouptype");
		seee.innerHTML="";
        var option=document.createElement("OPTION");
        option.text="--select --";
        option.value="";
        try
        {
        	seee.add(option);
        }
        catch(errorObject )
        {
        	seee.add(option,null);
        }
		alert("No Data in CHKLIST GROUP");
	} 
   }
  }
};
xmlrequest.send(null);
}




function createComboBox(name, cmbId )
{	
	var option = document.createElement("option");
	option.value = "" ;
	option.text = " -- Select -- " +  name;

	try
	{
		document.getElementById( cmbId ).innerHTML = "";
	}
	catch(e)
	{
		document.getElementById( cmbId ).innerText = "";
	}

	try
	{
		document.getElementById( cmbId ).add(option);
	}
	catch(errorObject)
	{
		document.getElementById( cmbId ).add(option,null);
	}
}

function checkACC()
{
    var date1=document.getElementById("txtCrea_date").value;
    var spl=date1.split("/");
    if(spl[2]>=2011)
    {
    
      var hcode_one=document.getElementById("txtAcc_HeadCode").value;
             if(hcode_one==900108 || hcode_one==900109 ||hcode_one==901002)
             {
                         alert(hcode_one+" AccountHeadCode cannot be used Here");
                         document.getElementById("txtAcc_HeadCode").value="";
                         document.getElementById("txtAcc_HeadDesc").value="";
                         return false;
             }else if((hcode_one==390302) ||(hcode_one==390303) || (hcode_one==390305) || (hcode_one==391002) ||(hcode_one==391003) ||(hcode_one==391302) || (hcode_one==391303) ||(hcode_one==391502) ||(hcode_one==391503) )
         		
             {			
                   	  alert("GPF Account Head Code cannot be used here***");
                         document.getElementById("txtAcc_HeadCode").value="";
                         document.getElementById("txtAcc_HeadCode").focus();
                         return false;
                     }

            else{
                    if(spl[0]>01 || spl[0]==01){
                        var hcode=parseInt(document.getElementById("txtAcc_HeadCode").value)/10000;
                        var spObj=hcode.toString().split(".");
                        if(spObj[0]=="82")
                        {
                         alert("This Account HeadCode Cannot be used Here");
                         document.getElementById("txtAcc_HeadCode").value="";
                         document.getElementById("txtAcc_HeadDesc").value="";
                        }
                        else
                        {
                        	doFunctionBLOCK('checkCode','null');
                        }
                    }
                }
    }
    else
    {
    var hcode=document.getElementById("txtAcc_HeadCode").value;
     if(hcode==900108 || hcode==900109 ||hcode==901002)
     {
     alert(hcode+" AccountHeadCode cannot be used Here");
     document.getElementById("txtAcc_HeadCode").value="";
     document.getElementById("txtAcc_HeadDesc").value="";
     return false;
     }else if((hcode==390302) ||(hcode==390303) || (hcode==390305) || (hcode==391002) ||(hcode==391003) ||(hcode==391302) || (hcode==391303) ||(hcode==391502) ||(hcode==391503) )
 		
     {			
           	  alert("GPF Account Head Code cannot be used here***");
                 document.getElementById("txtAcc_HeadCode").value="";
                 document.getElementById("txtAcc_HeadCode").focus();
                 return false;
             }

     doFunctionBLOCK('checkCode','null');
    }
}



function loadpension()
{

  var xmlrequest = getTransport();
  
  var cmbOffice_code=document.getElementById("cmbOffice_code").value;
  var txtCB_Year=document.getElementById("txtCB_Year").value;
  var txtCB_Month=document.getElementById("txtCB_Month").value;
  var listtype=document.getElementById("listtype").value;
  if(listtype=="")
	  {
	  alert("Choose CheckList Type");
	  return false;
	  }
 // alert(listtype);
  var grouptype=document.getElementById("grouptype").value;
  if(grouptype=="")
	  {
	  alert("Choose CheckList Group");
	  return false;
	  }
 // alert(grouptype);
  var penfamily=document.getElementById("penfamily").value;
  if(penfamily=="")
  {
  alert("Choose Pensioner or Family");
  return false;
  }
  var url="../../../../../PensionJournal?command=loadpension&cmbOffice_code="+cmbOffice_code+"&txtCB_Year="+txtCB_Year+
            "&txtCB_Month="+txtCB_Month+"&listtype="+listtype+"&grouptype="+grouptype+"&penfamily="+penfamily;
      //alert(url);
          xmlrequest.open("POST", url, true);
		xmlrequest.onreadystatechange = function() 
		{
	  if (xmlrequest.readyState == 4) 
		{
		if (xmlrequest.status == 200) 
		{
		var baseResponse=xmlrequest.responseXML.getElementsByTagName("response")[0];
		
		var flag1=baseResponse.getElementsByTagName("flag1")[0].firstChild.nodeValue;
	
		 var tbody=document.getElementById("tblList");
		 seq = 0;
         try{tbody.innerHTML="";}
         catch(e) {tbody.innerText="";}  
		if(flag1=="Success")
		{
		alert("Pension");
			var acc_desc=null;
		var incCount=0;
		var len=baseResponse.getElementsByTagName("count")[0].firstChild.nodeValue;
		var crtotal=baseResponse.getElementsByTagName("crtotalamount")[0].firstChild.nodeValue;
		var drtotal=baseResponse.getElementsByTagName("drtotalamount")[0].firstChild.nodeValue;
		

	//	alert("len:"+len);
		
		   var items=new Array();
		for(var i=0; i <len; i++)
		{
			
			incCount++;
			items[0]=baseResponse.getElementsByTagName("remarks")[i].firstChild.nodeValue;			
			items[1]=baseResponse.getElementsByTagName("hcode")[i].firstChild.nodeValue;
			items[2]=baseResponse.getElementsByTagName("cramount")[i].firstChild.nodeValue;
			items[3]=baseResponse.getElementsByTagName("dramount")[i].firstChild.nodeValue;
			items[4]=baseResponse.getElementsByTagName("Cr_Dr_Indicator")[i].firstChild.nodeValue;
//			items[5]=baseResponse.getElementsByTagName("SUB_LEDGER_TYPE_CODE")[i].firstChild.nodeValue;
//			items[6]=baseResponse.getElementsByTagName("SUB_LEDGER_TYPE_DESC")[i].firstChild.nodeValue;
			var sl_type_desc=null;
			var sl_type=baseResponse.getElementsByTagName("SUB_LEDGER_TYPE_CODE").length;
//			alert("Sl_Type==>"+sl_type);
			
			
			
			var hcode=items[1].split("-");
		if(hcode[0]==0)
			{
			acc_desc="-";
			}
		else
			{
			acc_desc=hcode[0]+"-"+hcode[1];
			
			}
		 
	    var tbody = document.getElementById("tblList");
	    
	    			//var mycurrent_row=document.createElement("TR");
	    var mycurrent_row = document.createElement("TR");
		mycurrent_row.id = seq;
		
	    			
	    			
	    			
	    			if(items[2]==0 && items[3]==0 )	
	    			{
	    				
	    			}else{
	    			mycurrent_row.id=i;
	    			var cell2=document.createElement("TD");
			        var slno=document.createElement("input");
			        slno.type="hidden";
			        slno.name="slno" + seq;
			        slno.value=incCount;
				   cell2.appendChild(slno);
				   var currentText=document.createTextNode(incCount);
				   cell2.appendChild(currentText);
				    mycurrent_row.appendChild(cell2);
        
		           var cell3=document.createElement("TD");
		           cell3.setAttribute('align','left');
	               var rem=document.createElement("input");
	               rem.type="hidden";
	               rem.name="remarks" + seq;
	               rem.value=items[0];
	               cell3.appendChild(rem);
                   var currentText=document.createTextNode(items[0]);
                   cell3.appendChild(currentText);
                   mycurrent_row.appendChild(cell3);
                   
                 if(hcode[0]!=0)  {
                   var cell4=document.createElement("TD");
                   cell4.setAttribute('align','left');
                   var accCode=document.createElement("input");
                   accCode.type="hidden";
                   accCode.id="headCode" + seq;
                   accCode.name="headCode" + seq;
                   accCode.value=hcode[0];
                   
                   
                   cell4.appendChild(accCode); 
                   
                   
                   var currentText=document.createTextNode(acc_desc);
                   cell4.appendChild(currentText);
                   mycurrent_row.appendChild(cell4);
                 }else if(hcode[0]==0)  {
                	 
                	   var cell4=document.createElement("TD");
                       cell4.setAttribute('align','left');
                       var accCode=document.createElement("input");
                       accCode.type="text";
                       accCode.name="headCode" + seq;
                       accCode.id="headCode" + seq;
                       accCode.value=hcode[0];
                       accCode.setAttribute('onChange','chkHead(this.value,'+i+')');
                       accCode.setAttribute('onblur','doFunctionBLOCK(this.value,'+i+')');
                       cell4.appendChild(accCode); 
                   
                       mycurrent_row.appendChild(cell4);
                 }
//                 
//                 var cell5=document.createElement("TD");
//		           cell5.setAttribute('align','left');
//	               var rem=document.createElement("select");
//	               rem.type="select";
//	               rem.name="sl_type";
//	               rem.id="sl_type";
//	               rem.value="select";
//	               cell5.appendChild(rem);
//	               
//	               
//	               
//	               
////                 var currentText=document.createTextNode("option");
////                 cell5.appendChild(currentText);
//                 mycurrent_row.appendChild(cell5);
                 
                 
//     			alert("sl_type_desc==>"+sl_type_desc);
     			var cell5=document.createElement("TD");
                cell5.setAttribute('align','left');
                var selectList = document.createElement("select");
                selectList.id = "cmbSL_type" + seq;
                selectList.setAttribute('onChange','doFunction(this.value,'+seq+')');
                mycurrent_row.appendChild(selectList);
                
                var option=document.createElement("option");
                option.text="--Select Type--";
                option.value="";
                try {
                	selectList.add(option);
    			} catch (e) {
    				selectList.add(option, null);
    			}
                
                for(var k=0;k<sl_type;k++)
     			{
     			 sl_type_desc=baseResponse.getElementsByTagName("SUB_LEDGER_TYPE_DESC")[k].firstChild.nodeValue;
               
               	    var option = document.createElement("option");
               	    option.value = sl_type_desc;
               	    

               	    option.text = sl_type_desc;
               	    selectList.appendChild(option);
     			}
                 
                 
     			
                 
                 var cell6=document.createElement("TD");
		           cell6.setAttribute('align','left');
	               var rem=document.createElement("select");
	               rem.type="select";
	               rem.name="cmbSL_Code" + seq;
	               rem.id="cmbSL_Code" + seq;
	               
	               var opt=document.createElement("option");
	                opt.text="--Select Type--";
	                opt.value="";
	                try {
	                	rem.add(opt);
	    			} catch (e) {
	    				rem.add(opt, null);
	    			}
	               
	               cell6.appendChild(rem);
//               var currentText=document.createTextNode("option");
//               cell6.appendChild(currentText);
               mycurrent_row.appendChild(cell6);
               
               
                 
                 
                   if(items[2]=="null")
                	   {
                	   items[2]=0;
                	   }
                   else if ((items[3]=="null"))
                	   {
                	   items[3]=0;
                	   }
		           var cell7=document.createElement("TD");
		           cell7.setAttribute('align','right');
                   var cramt=document.createElement("input");
                   cramt.type="hidden";
                  cramt.name="cramount" + seq;
                  cramt.value=items[2];
                  cell7.appendChild(cramt); 
                   var currentText=document.createTextNode(items[2]);
                   cell7.appendChild(currentText);
                   mycurrent_row.appendChild(cell7);
                
                
	  
               var cell8=document.createElement("TD");
               cell8.setAttribute('align','right');
               var dramt=document.createElement("input");
               dramt.type="hidden";
               dramt.name="dramount" + seq;
               dramt.value=items[3];
               cell8.appendChild(dramt); 
               var currentText=document.createTextNode(items[3]);
               cell8.appendChild(currentText);
               var cr_Dr=document.createElement("input");
               cr_Dr.type="hidden";
               cr_Dr.name="cr_Dr" + seq;
               cr_Dr.value=items[4];
               cell8.appendChild(cr_Dr); 
               mycurrent_row.appendChild(cell8);
               
               
               
               tbody.appendChild(mycurrent_row);    
               seq=seq + 1;
               alert("Seq===>"+seq);
               
	    			}   
	    }
		document.getElementById("crtotal").value=crtotal;
		document.getElementById("drtotal").value=drtotal;
		
		//alert("total amount calculate555555");
      /*  var tbody1 = document.getElementById("totalvalue");
       // alert("total amount calculate");
        var mycurrent_row1=document.createElement("TR");
       
		   var cell21=document.createElement("TD");	 
		   cell21.setAttribute('colspan','3');
		   var currentText=document.createTextNode("Total");
		   cell21.appendChild(currentText);
		    mycurrent_row1.appendChild(cell21);
		    
		    var cell22=document.createElement("TD");
            cell22.setAttribute('align','right');
            cell22.setAttribute('maxlength','10');
            cell22.setAttribute('size','11');
            cell22.setAttribute('readonly','readonly');
            var cramttot=document.createElement("input");
            cramttot.type="text";
            cramttot.name="crtotalamount";
            cramttot.value=crtotal;
            cell22.appendChild(cramttot); 
           // var currentText=document.createTextNode(crtotal);
           // cell22.appendChild(currentText);
            mycurrent_row1.appendChild(cell22);
		    
		    var cell23=document.createElement("TD");
            cell23.setAttribute('align','right');
            cell23.setAttribute('maxlength','10');
            cell23.setAttribute('size','11');
            cell23.setAttribute('readonly','readonly');
            var dramttot=document.createElement("input");
            dramttot.type="text";
            dramttot.name="drtotalamount";
            dramttot.value=drtotal;
            cell23.appendChild(dramttot); 
           // var currentText=document.createTextNode(drtotal);
           // cell23.appendChild(currentText);
            mycurrent_row1.appendChild(cell23);
            
     
     
     tbody1.appendChild(mycurrent_row1);*/
		
		
	    
		}
		else if(flag1=="nodata"){
			alert("No Data");
		   }
		else {
		alert("Failed to Load Grid");
	   }
	  }
	}
  };
xmlrequest.send(null);
}

function loadHead()
{
   var xmlrequest = getTransport();
   
   var url="../../../../../PensionJournal?command=loadHead";
   xmlrequest.open("POST", url, true);
		xmlrequest.onreadystatechange = function() 
		{
	  if (xmlrequest.readyState == 4) 
		{
		if (xmlrequest.status == 200) 
		{
		var baseResponse=xmlrequest.responseXML.getElementsByTagName("response")[0];
		
		var flag1=baseResponse.getElementsByTagName("flag1")[0].firstChild.nodeValue;
		
		
		if(flag1=="Success")
		{
		
		}
		}
	}
};
xmlrequest.send(null);
}

function call_date(dateCtrl)                        // TB_checking 
{
    call_clr();
    if(checkdt(dateCtrl))
    {
        //doFunction('check_TB',dateCtrl.value);
         var cmbAcc_UnitCode=document.getElementById("cmbAcc_UnitCode").value;
         var cmbOffice_code=document.getElementById("cmbOffice_code").value;
         var TB_date=dateCtrl.value;
       
         if(dateCtrl.value.length!=0)
         {
             var url="../../../../../Receipt_SL.view?Command=check_TB&TB_date="+TB_date+"&cmbAcc_UnitCode="+cmbAcc_UnitCode+"&cmbOffice_code="+cmbOffice_code;
             var req=getTransport();
             req.open("GET",url,true); 
             req.onreadystatechange=function()
             {
               check_TB(req,dateCtrl);
             }   
             req.send(null);
       }
        //doFunction('load_Receipt_No','null');
    }
    else
    {
     // document.getElementById("txtReceipt_No").value="";
    }
}
function call_mainJSP_script(fromcal_dateCtrl,blr_flag)     /////////Calender control return value handling
{
    //alert(fromcal_dateCtrl.value+"GG"+blr_flag+fromcal_dateCtrl);
    if(blr_flag==1)             // which is used to find the receipt or voucher or payment (creation) date calling field,if so check trial balance
    {
           //  call_clr();
             var cmbAcc_UnitCode=document.getElementById("cmbAcc_UnitCode").value;
             var cmbOffice_code=document.getElementById("cmbOffice_code").value;
             var TB_date=fromcal_dateCtrl.value;
             //alert(fromcal_dateCtrl.value+"b4url")
             if(fromcal_dateCtrl.value.length!=0)
             {
                 var url="../../../../../Receipt_SL.view?Command=check_TB&TB_date="+TB_date+"&cmbAcc_UnitCode="+cmbAcc_UnitCode+"&cmbOffice_code="+cmbOffice_code;
                        //alert(url);
                 var req=getTransport();
                 req.open("GET",url,true); 
                 req.onreadystatechange=function()
                 {
                   check_TB(req,fromcal_dateCtrl);
                 }  ; 
                 req.send(null);
            }
           
    }
}
function check_TB(req,dateCtrl)
{
 if(req.readyState==4)
    {
        if(req.status==200)
        {  
            var baseResponse=req.responseXML.getElementsByTagName("response")[0];
            var tagcommand=baseResponse.getElementsByTagName("command")[0];
            var Command=tagcommand.firstChild.nodeValue;
            var flag=baseResponse.getElementsByTagName("flag")[0].firstChild.nodeValue;
            
            if(flag=="success")
              {
                 //doFunction('load_Receipt_No','null');  
            	check_withinCB();//return true;
              }
             else if(flag=="failure")
               {
                    dateCtrl.value="";
                    alert("Trial Balance Closed");//return false;//
                    dateCtrl.focus();
                    //document.getElementById("txtReceipt_No").value="";
               }
             else if(flag=="finyear")
               {
                          // This statement get executed when financial year ( Cash Book Control Details ) has not found for the given date 
                    dateCtrl.value="";
                    alert("Cash Book Control Not Found ");//return false;//
                    dateCtrl.focus();
                   // document.getElementById("txtReceipt_No").value="";     
               }
            dateCheck(dateCtrl);  
        }
    }
}

function dateCheck(datechk)
{
	//alert("WELCOME!.........");
	
	var cmbAcc_UnitCode=document.getElementById("cmbAcc_UnitCode").value;
    var cmbOffice_code=document.getElementById("cmbOffice_code").value;
    //var txtCrea_date=document.getElementById("txtCrea_date").value;
    var txtCrea_date=datechk.value;
    
    if(datechk.value.length!=0)
    {
    var url="../../../../../Receipt_SL.view?Command=check_Date&cmbAcc_UnitCode="+cmbAcc_UnitCode+"&cmbOffice_code="+cmbOffice_code+"&txtCrea_date="+txtCrea_date;
    //alert("URL===>"+url);
    var req=getTransport();
    req.open("GET",url,true); 
    req.onreadystatechange=function()
    {
      check_Date(req,datechk);
    } ;  
    req.send(null);
    }

}
function check_Date(req,datechk)
{
 if(req.readyState==4)
    {
        if(req.status==200)
        {  
            var baseResponse=req.responseXML.getElementsByTagName("response")[0];
            var tagcommand=baseResponse.getElementsByTagName("command")[0];
            var Command=tagcommand.firstChild.nodeValue;
            var flag=baseResponse.getElementsByTagName("flag")[0].firstChild.nodeValue;
            //alert("Flag----->"+flag);
            if(flag=="success")
              {
                 //doFunction('load_Receipt_No','null');                 //return true;
            	document.getElementById("butSub").disabled=false;
              }
            else if(flag=="failure")
            {
            	datechk.value=""; 
            	alert("Document Date is Less than DATE_EFFECTIVE_FROM");
            	datechk.focus();
            	document.getElementById("butSub").disabled=true;
            	
            	document.getElementById("txtReceipt_No").value="";
                 
            }
            else if(flag=="success1")
            {
               //doFunction('load_Receipt_No','null');                 //return true;
            	document.getElementById("butSub").disabled=false;
            }
           else if(flag=="failure1")
           {
        	  alert("Document Date is Greater than DATE_OF_CLOSURE");
        	  datechk.value=""; 
          		//alert("Document Date is Less than DATE_ALLOWED_UPTO date");
          		datechk.focus();
          		document.getElementById("butSub").disabled=true;
          		document.getElementById("txtReceipt_No").value="";
           }
           else 
        	   {
        	    datechk.value=""; 
        	    alert("Date Value is Null");
           		datechk.focus();
           		document.getElementById("butSub").disabled=true;
           		document.getElementById("txtReceipt_No").value="";
        	   }
        }
    }
}





function chkHead(headCode,i){
	
	
	var url="../../../../../PensionJournal?command=ValidateHead&Head_Code="+headCode;
	var xmlreq=getTransport();
	xmlreq.open('post',url,true);

	xmlreq.onreadystatechange=function(){
		
	if(xmlreq.readyState==4){
		if(xmlreq.status==200)
		{
		var br=xmlreq.responseXML.getElementsByTagName("response")[0];	
		var command=br.getElementsByTagName("command")[0].firstChild.nodeValue;
		var head=br.getElementsByTagName("head")[0].firstChild.nodeValue;
		var flag=br.getElementsByTagName("flag")[0].firstChild.nodeValue;
		if(command=="ValidateHead"){
		if(flag=="success"){
			//alert('valid HeadCode');
			}
		else if(flag=="failure"){
		//var row=document.getElementsByTagName(i);
		var row=document.getElementById(i);
		//rowcell.item(2).firstChild.value
		var rowcell=row.cells;
		
		rowcell.item(2).firstChild.value="";
		
		alert('Not a valid HeadCode');
		return false;
		}
		else alert('else');
		}
	}
	
}
	};
	xmlreq.send(null);
}
//Sathya check for entered date is within the given month and year**********
function check_withinCB()
{
	var mon ="";
		var jrnl_date = document.getElementById("txtCrea_date").value;
		var CB_Year = document.frmpensionJournal.txtCB_Year.value;
		var CB_month = document.frmpensionJournal.txtCB_Month.value;
		var jrdate = jrnl_date.split('/');
		var entered_month = jrdate[1];
		var enteedr_year = jrdate[2];
		if(CB_month<10)
			{
		 mon = "0"+CB_month;
			}
		else{
			mon=CB_month;
		}
		//alert(entered_month +" ==  "+ mon+" == "+enteedr_year +" ==" + CB_Year)
		if((entered_month == mon) && (enteedr_year == CB_Year))
			{
			
			}
		else
			{
			
			alert("select the Journal Date with in the Cash Book Year and Month");
			document.getElementById("txtCrea_date").value="";
			}
}
function doFunctionBLOCK(headCode,i)
{  
	
	try
	   {
	    
//	   alert("Welcome");
	    
	        
	        	alert("Welcome");
	           
	             isMan.account_head_status = false;
	            
	             var txtAcc_HeadCode=headCode;
	             
	             alert("txtAcc_HeadCode==>"+txtAcc_HeadCode);
	             var cmbSL_Code=document.getElementById("cmbSL_Code" + i);   
	             alert("cmbSL_Code==>"+cmbSL_Code);
	             clear_Combo(cmbSL_Code);
	           
	            try
	            {
	                       
	             Sub_Ledger_Mandatory(txtAcc_HeadCode);    
	             
	            }
	           catch(e)
	           {
	            
	           }
	         
	           
	            if(txtAcc_HeadCode.length>=6)
	            {
	                var url="../../../../../Receipt_SL.view?Command=checkCode_BLOCK&txtAcc_HeadCode="+txtAcc_HeadCode+"&cmbOffice_code="+cmbOffice_code+"&vr_date="+txtCrea_date;                
	             //  alert(url);
	                var req=getTransport();
	                req.open("GET",url,true); 
	                req.onreadystatechange=function()
	                {
	                   handleResponse(req);
	                };   
	                        req.send(null);
	            }         
	       
	   }catch(e)
	   {
	}
}

function doFunction(cmbSL_type,i)

{
	
    	var cmbSL_type=param;
   
        document.getElementById("offlist_div_trans").style.display='none';
        document.getElementById("emplist_div_trans").style.display='none';
       
        if(cmbSL_type==5)
          {
              document.getElementById("offlist_div_trans").style.display='block';
           //   clear_Combo(document.getElementById("cmbSL_Code"));
              //document.getElementById("txtOfficeID_trs").value="";
              addtional_field_value=document.getElementById("txtOfficeID_trs").value;
              //alert("USE search ICON to select the office");
              if(addtional_field_value=="")
              {
                    clear_Combo(document.getElementById("cmbSL_Code"));
                    alert("Enter or select the office code");
                    return true;
              }
          }
        else
         {
            document.getElementById("txtOfficeID_trs").value="";
         }
          
         if(cmbSL_type==7)
          {
        	
              document.getElementById("emplist_div_trans").style.display='block';
              var cmbMas_SL_type=document.getElementById("cmbMas_SL_type").value;
              //clear_Combo(document.getElementById("cmbMas_SL_Code"));
              //document.getElementById("txtOfficeID_mas").value="";
              addtional_field_value=document.getElementById("txtEmpID_trs").value;
              //alert("USE search ICON to select the office");
           //   alert(addtional_field_value);
              var month1=0;
         	  var year11=0;
              if(addtional_field_value=="")
              {
                    clear_Combo(document.getElementById("cmbSL_Code")); 
                    alert("Enter or select the employee code1");
                    return true;
              }
              if(cmbMas_SL_type ==9) // Other Department Receipt Creation
            	  {
            	  var month1=document.getElementById("txtCB_Month").value;
             	  var year11=document.getElementById("txtCB_Year").value;
             	  //return true;
            	  }
          }
        else
          {
              document.getElementById("txtEmpID_trs").value="";
          }
          
      if(cmbSL_type!="")                              // called only not equal to null
        { 
    	// alert("test");
             var cmbMas_SL_type=document.getElementById("cmbMas_SL_type").value;
         //    alert("cmbMas_SL_type"+cmbMas_SL_type);
             var other_dept_off_alias_id=document.getElementById("cmbMas_SL_Code").value;
             //Lachu 6Nov13
            // var month1=document.getElementById("txtCB_Month").value;
            	// var year11=document.getElementById("txtCB_Year").value;
          //   alert("other_dept_off_alias_id"+other_dept_off_alias_id);
             var url="../../../../../Receipt_SL.view?Command=Load_SL_Code&cmbSL_type="+cmbSL_type+"&cmbMas_SL_type="+cmbMas_SL_type+
             "&other_dept_off_alias_id="+other_dept_off_alias_id+"&cmbAcc_UnitCode="+cmbAcc_UnitCode+
             "&cmbOffice_code="+cmbOffice_code+"&addtional_field_value="+addtional_field_value+"&month1="+month1+"&year11="+year11;
        
            var req=getTransport();
            req.open("GET",url,true); 
            req.onreadystatechange=function()
            {
               handleResponse(req);
            }   
                    req.send(null);
        }
        else if(cmbSL_type=="")
        clear_Combo(document.getElementById("cmbSL_Code"));
        
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
            
            
            if(Command=="checkCode_BLOCK")
            {
                loadcheckCode(baseResponse);
            }
            else if(Command=="Load_SL_Code")
            {
                Load_SL_Code(baseResponse);
            }
        }
    }
}

function loadcheckCode(baseResponse)
{
	  
	  
	  var tbody = document.getElementById("tblList");
		var rowcount = tbody.rows.length;
            
		for ( var i = 0; i < rowcount; i++) {
			var r = tbody.rows[i];
			alert("seq value check in loadcheckCode===>"+seq);
	var flag=baseResponse.getElementsByTagName("flag")[0].firstChild.nodeValue;
    
    if(flag=="success")
    {
           	
    	var hid=baseResponse.getElementsByTagName("hid")[0].firstChild.nodeValue;
         alert("hid====>"+hid);
         document.getElementById("headCode" + seq).value=hid;
         var SL_YN =baseResponse.getElementsByTagName("SL_YN")[0].firstChild.nodeValue;
         
         var sl_man = baseResponse.getElementsByTagName("sl_man")[0].firstChild.nodeValue;
        // alert(hdesc);
         
        // document.getElementById("headCode").value=hid;
      
       var cmbSL_type=document.getElementById("cmbSL_type"  + seq);   
       alert("cmbSL_type====>"+cmbSL_type);
     try{   
     
       
       if(SL_YN=="Y")
       {
            
            
            if(sl_man == "Y" ) 
            {
              isMan.account_head_status = true;     
            }
            
            var items_SLcode=new Array();
            var items_SLdesc=new Array();
            var SLCODE=baseResponse.getElementsByTagName("SLCODE");
            var SLDESC=baseResponse.getElementsByTagName("SLDESC");
            for(var k=0;k<SLCODE.length;k++)
            {
                items_SLcode[k]=baseResponse.getElementsByTagName("SLCODE")[k].firstChild.nodeValue;
                items_SLdesc[k]=baseResponse.getElementsByTagName("SLDESC")[k].firstChild.nodeValue;
            }
            
            cmbSL_type.innerHTML="";
            var option=document.createElement("option");
            option.text="--Select Type--";
            option.value="";
            try
            {
                cmbSL_type.add(option);
            }catch(errorObject)
            {
                cmbSL_type.add(option,null);
            }
            for(var k=0;k<SLCODE.length;k++)
            {   
              var option=document.createElement("option");
              option.text=items_SLdesc[k];
              option.value=items_SLcode[k];
               try
              {
                  cmbSL_type.add(option);
              }
              catch(errorObject)
              {
                  cmbSL_type.add(option,null);
              }
            }

//            if(common_cmbSL_type=="")
//   
//                document.getElementById("cmbSL_type").value="";
//            else
//                document.getElementById("cmbSL_type").value=common_cmbSL_type;    //set from grid

       }
        
     }catch(e)
     {  
       alert(e.description);
       return false;
     }   


        if(SL_YN=="N" || SL_YN=="null")
           {    
                cmbSL_type.innerHTML=""; 
                var option=document.createElement("OPTION");
                option.text="--Select Type--";
                option.value="";
                try
                {
                    cmbSL_type.add(option);
                }catch(errorObject)
                {
                    cmbSL_type.add(option,null);
                }
            }
          
    }
     else if(flag=="failure")
     {
         alert("Account Head Code '"+document.getElementById("headCode" + seq).value+"' doesn't Exist");
         document.getElementById("headCode" + seq).value="";
         document.getElementById("headCode" + seq).focus();
     }
		
        //common_AHead_code_flag="";
        common_cmbSL_type="";
		}
		 r = tbody.rows[i];
		 alert("r==>"+r);
}
function clear_Combo(combo)
{
    alert("access restricted");    
	alert(combo.id);
        var cmbSL_Code=document.getElementById(combo.id);   
        cmbSL_Code.innerHTML="";
       var option=document.createElement("option");
        option.text="--Select Code--";
        option.value="";
        try
        {
            cmbSL_Code.add(option);
        }catch(errorObject)
        {
            cmbSL_Code.add(option,null);
        } 
}



function Load_SL_Code(baseResponse)
{
var flag=baseResponse.getElementsByTagName("flag")[0].firstChild.nodeValue;

    if(flag=="success")
    {
         var cmbSL_Code=document.getElementById("cmbSL_Code");
         
         var items_id=new Array();
         var items_name=new Array();
        // var items_init=new Array();
        
            var cid=baseResponse.getElementsByTagName("cid");
            var cname=baseResponse.getElementsByTagName("cname");
            for(var k=0;k<cid.length;k++)
            {
                items_id[k]=baseResponse.getElementsByTagName("cid")[k].firstChild.nodeValue;
              
                items_name[k]=baseResponse.getElementsByTagName("cname")[k].firstChild.nodeValue;
             //   items_init[k]=baseResponse.getElementsByTagName("init")[k].firstChild.nodeValue;
            }
           clear_Combo(cmbSL_Code);
         //   alert('here second');
            for(var k=0;k<cid.length;k++)
            {   
                  var option=document.createElement("OPTION");
                  
                  option.text=items_name[k]+"("+items_id[k]+")";
                
                  option.value=items_id[k];
                   try
                  {
                      cmbSL_Code.add(option);
                  }
                  catch(errorObject)
                  {
                      cmbSL_Code.add(option,null);
                  }
            }
             document.getElementById("cmbSL_Code").value=items_id[0];
             //alert(items_id[0]);
           if(document.getElementById("cmbSL_type").value==5)
           {
                var state=baseResponse.getElementsByTagName("state")[0].firstChild.nodeValue;
                if(state!="CR")
                alert("Office is not in working status");
           }
           
           if(document.getElementById("cmbMas_SL_type").value!=9 && document.getElementById("cmbSL_type").value==7)
           {
                var state=baseResponse.getElementsByTagName("state")[0].firstChild.nodeValue;
                if(state=="DPN")
                alert("Employee in Deputation");
           } 
           
          // document.getElementById("cmbSL_Code").value=common_cmbSL_Code;
    }
    //Lakshmi 29oct13
    else if(flag=="NotData")
    {
        alert("No sl details found");
    	//alert("Deputation OfficeId in General Should be Given in DetailPart also");
    	
        var cmbSL_Code=document.getElementById("cmbSL_Code");
        clear_Combo(cmbSL_Code);
    }
  //Lakshmi 7Nov13
    else if(flag=="NoOther")
    {
        alert("No Other sl details found");
    	//alert("Deputation OfficeId in General Should be Given in DetailPart also");
    	
        var cmbSL_Code=document.getElementById("cmbSL_Code");
        clear_Combo(cmbSL_Code);
    }
    else if(flag=="failure")
    {
       // alert("No data found");
    	alert("Deputation OfficeId in General Should be Given in DetailPart also");
    	
        var cmbSL_Code=document.getElementById("cmbSL_Code");
        clear_Combo(cmbSL_Code);
    }
    
    common_cmbSL_Code="";
}
