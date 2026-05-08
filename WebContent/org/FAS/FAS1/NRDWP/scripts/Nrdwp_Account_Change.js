//			Nrdwp_Account_Change 		//
function AjaxFunction() {
	var xmlrequest = false;
	try {
		xmlrequest = new ActiveXObject("Msxml2.XMLHTTP");
	} catch (e1) {
		try {
			xmlrequest = new ActiveXObject("Microsoft.XMLHTTP");
		} catch (e2) {
			xmlrequest = false;
		}
	}
	if (!xmlrequest && typeof XMLHttpRequest != 'undefined') {
		xmlrequest = new XMLHttpRequest();
	}
	return xmlrequest;
}
function selectAll(Opt)
{

  var len=  document.getElementById("tblList").rows.length;
//alert("lennn "+len);
  if(len==1)
  {
	 // alert("if");
          if (Opt =="ALL")
          {
        	 document.frmnrdwp_AccHeadChange.verify_select.checked=true;
        	 document.frmnrdwp_AccHeadChange.verify_select_status.checked=true;
          
          }
          else if (Opt=="UNSelect" )
          {
          document.frmnrdwp_AccHeadChange.verify_select.checked=false;
          document.frmnrdwp_AccHeadChange.verify_select_status.checked=false;
        
          }
  }
  else if(len>1)
  {
	//  alert("else ");
          for(var i=0;i<len;i++)
          {
        	  //var n="verify_select"+i;
                if ( Opt =="ALL")
                {
                   // document.frmnrdwp_AccHeadChange.n.checked=true;
                   // document.getElementById(n).checked=true;
                   // document.frmnrdwp_AccHeadChange.verify_select_status+i.checked=true;
                    
                  document.getElementById("verify_select"+i).checked=true;
                    document.getElementById("verify_select_status"+i).checked=true;
                    
                }
                else if(Opt=="UNSelect")
                {
                    //document.frmnrdwp_AccHeadChange.verify_select[i].checked=false;
                    //document.frmnrdwp_AccHeadChange.verify_select_status[i].checked=false;
                	 document.getElementById("verify_select"+i).checked=false;
                     document.getElementById("verify_select_status"+i).checked=false;
                	 //document.getElementById(n).checked=true;
                }
          }
  }

}
function manipulate(xmlrequest) {

	if (xmlrequest.readyState == 4) {
		if (xmlrequest.status == 200) {

			var baseResponse = xmlrequest.responseXML
					.getElementsByTagName("response")[0];

			var tagCommand = baseResponse.getElementsByTagName("command")[0];

			var command = tagCommand.firstChild.nodeValue;

			if (command == "save") {
				save1(baseResponse);
			} 
			else if (command == "loadAccountNo") {
				//alert("loadaaacccno");
				loadAccountNo1(baseResponse);
			} else if(command=="LoadData"){
				//alert(xmlrequest+"  command >>> "+command);
				LoadData1(baseResponse);
			}
		}
	}
}
	function  LoadData1(baseResponse)
	{  
	var seq=0;
	var flag=baseResponse.getElementsByTagName("flag")[0].firstChild.nodeValue; 
	if(flag=="success")
	{          
	 var tbody = document.getElementById("tblList");
	 
	 var table = document.getElementById("Existing");
	 var t=0;
	 for(t=tbody.rows.length-1;t>=0;t--)
	     {
	        tbody.deleteRow(0);
	     }                        
	 var len=baseResponse.getElementsByTagName("count")[0].firstChild.nodeValue;  
	 var exists=baseResponse.getElementsByTagName("exists")[0].firstChild.nodeValue;  
	// alert("len "+len);
	if(len==0){
		   if(exists=="No"){
			   //alert(exists);
			   alert("No Records");
		   }
	 }else{
		 //alert("len "+len);
	 	// var lll=1;
	 	 var item = new Array();

	        for(var k=0;k<len;k++)
	           {  
	        	
	        	

            
	     			item[0]=baseResponse.getElementsByTagName("docNo")[k].firstChild.nodeValue;
					item[1]=baseResponse.getElementsByTagName("DocDate")[k].firstChild.nodeValue;	
					item[2]=baseResponse.getElementsByTagName("DocType")[k].firstChild.nodeValue;
					item[3]=baseResponse.getElementsByTagName("oldAcHead")[k].firstChild.nodeValue;
					item[4]=baseResponse.getElementsByTagName("Amt")[k].firstChild.nodeValue;	
					item[5]=baseResponse.getElementsByTagName("CR_DR_INDICATOR")[k].firstChild.nodeValue;
					item[6]=baseResponse.getElementsByTagName("newAcHead")[k].firstChild.nodeValue;
					item[7]=baseResponse.getElementsByTagName("unitid")[k].firstChild.nodeValue;
					item[8]=baseResponse.getElementsByTagName("offid")[k].firstChild.nodeValue;	
					item[9]=baseResponse.getElementsByTagName("yr")[k].firstChild.nodeValue;
					item[10]=baseResponse.getElementsByTagName("mnth")[k].firstChild.nodeValue;
					item[11]=baseResponse.getElementsByTagName("accno")[k].firstChild.nodeValue;
					item[12]=baseResponse.getElementsByTagName("doctypp")[k].firstChild.nodeValue;
					item[13]=baseResponse.getElementsByTagName("REMARKS")[k].firstChild.nodeValue;
					item[14]=baseResponse.getElementsByTagName("oldAcHeadCr")[k].firstChild.nodeValue;
					item[15]=baseResponse.getElementsByTagName("newAcHeadCr")[k].firstChild.nodeValue;
					
	              //  var mycurrent_row=document.createElement("TR");
	               // mycurrent_row.id=seq;  
	              var scc=0;
	                if(item[3]==item[6]){
	                	//alert("not equal"+item[3]+"----"+item[6]);
	                	/*if((len==1)||(scc==0)){
	                	alert("Old Account Head Code is same as New Account Head code ");
	                	}*/
	                	if((len==1)){
		                	alert("Old Account Head Code is same as New Account Head code ");
		                	}
	                	
	                }else{
	                	scc++;
	                	var mycurrent_row=document.createElement("TR");
		                //seq=seq+1;
		                mycurrent_row.id=seq;
		                //alert("row ID"+mycurrent_row.id);
		                
		               
		                
		               /* var cell=document.createElement("TD");
		                var anc=document.createElement("A");
		                var url="javascript:loadTable('"+mycurrent_row.id+"')";
		                anc.href=url;
		                var txtedit=document.createTextNode("EDIT");
		                txtedit.size="7";
		                anc.appendChild(txtedit);
		                cell.appendChild(anc);
		                mycurrent_row.appendChild(cell);
		                */

		                var cell0 = document.createElement("TD");
		     			var sno1=document.createElement("input");
		     			sno1.type="hidden";
		     			sno1.name="docNo"+ seq;
		     			sno1.id="docNo"+ seq;
		     			sno1.value=item[0];
		     			var sno2 = document.createTextNode(item[0]);
		     			sno2.size="5";
		     			cell0.appendChild(sno2);
		     			cell0.appendChild(sno1);
		     			mycurrent_row.appendChild(cell0);
		     			
		     			
		     			var cell01 = document.createElement("TD");
		     			var sno1=document.createElement("input");
		     			sno1.type="hidden";
		     			sno1.name="DocDate"+ seq;
		     			sno1.id="DocDate"+ seq;
		     			sno1.value=item[1];
		     			var sno2 = document.createTextNode(item[1]);
		     			sno2.size="5";
		     			cell01.appendChild(sno2);
		     			//cell01.style.textAlign="right";
		     			cell01.appendChild(sno1);
		     			mycurrent_row.appendChild(cell01);
		     			
		     			/*var cell2 = document.createElement("TD");
		     			var sno1=document.createElement("input");
		     			sno1.type="hidden";
		     			sno1.name="DocType";
		     			sno1.id="DocType";
		     			sno1.value=item[2];
		     			var sno2 = document.createTextNode(item[2]);
		     			cell2.appendChild(sno2);
		     			//cell2.style.textAlign="right";
		     			cell2.appendChild(sno1);
		     			mycurrent_row.appendChild(cell2);*/
		     			
		     			 var cell1 = document.createElement("TD");
			     			var sno1=document.createElement("input");
			     			sno1.type="hidden";
			     			sno1.name="oldAcHead"+ seq;
			     			sno1.id="oldAcHead"+ seq;
			     			sno1.value=item[3];
			     			var sno2 = document.createTextNode(item[3]);
			     			cell1.appendChild(sno2);
			     			//cell1.style.textAlign="right";
			     			cell1.appendChild(sno1);
			     			mycurrent_row.appendChild(cell1);
			     			
			     			
			     			
			     			 var cell111 = document.createElement("TD");
				     			var sno1=document.createElement("input");
				     			sno1.type="hidden";
				     			sno1.name="oldAcHeadCr"+ seq;
				     			sno1.id="oldAcHeadCr"+ seq;
				     			sno1.value=item[14];
				     			var sno2 = document.createTextNode(item[14]);
				     			cell111.appendChild(sno2);
				     			//cell1.style.textAlign="right";
				     			cell111.appendChild(sno1);
				     			mycurrent_row.appendChild(cell111);
		     			
			     			
			     			var cell10 = document.createElement("TD");
			    			//cell10.style.display="none";
			    			var netdepcode1=document.createElement("input");
			    			netdepcode1.type="hidden";
			    			netdepcode1.name="accno"+ seq;
			    			netdepcode1.id="accno"+ seq;
			    			netdepcode1.value=item[11];
			    			netdepcode1.size=1;          			
			    			cell10.appendChild(netdepcode1);
			    			var netdepcode_text = document.createTextNode(item[11]);
			    			cell10.appendChild(netdepcode_text);
			    			mycurrent_row.appendChild(cell10);
		     			
		     			    var cell2 = document.createElement("TD");
			     			var sno1=document.createElement("input");
			     			sno1.type="hidden";
			     			sno1.name="Amt"+ seq;
			     			sno1.id="Amt"+ seq;
			     			sno1.value=item[4];
			     			var sno2 = document.createTextNode(item[4]);
			     			cell2.appendChild(sno2);
			     			//cell2.style.textAlign="right";
			     			cell2.appendChild(sno1);
			     			mycurrent_row.appendChild(cell2);
			     			
			     			
			     			/*
			     			var cell4 = document.createElement("TD");
			     			var sno1=document.createElement("input");
			     			sno1.type="hidden";
			     			sno1.name="CR_DR_INDICATOR"+ seq;
			     			sno1.id="CR_DR_INDICATOR"+ seq;
			     			sno1.value=item[5];
			     			var sno2 = document.createTextNode(item[5]);
			     			cell4.appendChild(sno2);
			     			//cell4.style.textAlign="right";
			     			cell4.appendChild(sno1);
			     			mycurrent_row.appendChild(cell4);*/
			     			
			     			
			     			var cell4 = document.createElement("TD");
			     			var sno1=document.createElement("input");
			     			sno1.type="hidden";
			     			sno1.name="newAcHead"+ seq;
			     			sno1.id="newAcHead"+ seq;
			     			sno1.value=item[6];
			     			var sno2 = document.createTextNode(item[6]);
			     			cell4.appendChild(sno2);
			     			//cell4.style.textAlign="right";
			     			cell4.appendChild(sno1);
			     			mycurrent_row.appendChild(cell4);
//alert(item[15]);
			     			 var cell111 = document.createElement("TD");
				     			var sno1=document.createElement("input");
				     			sno1.type="hidden";
				     			sno1.name="newAcHeadCr"+ seq;
				     			sno1.id="newAcHeadCr"+ seq;
				     			sno1.value=item[15];
				     			var sno2 = document.createTextNode(item[15]);
				     			cell111.appendChild(sno2);
				     			//cell1.style.textAlign="right";
				     			cell111.appendChild(sno1);
				     			mycurrent_row.appendChild(cell111);
				     			
			     			var cell4 = document.createElement("TD");
			     			var sno1=document.createElement("input");
			     			sno1.type="hidden";
			     			sno1.name="REMARKS"+ seq;
			     			sno1.id="REMARKS"+ seq;
			     			sno1.value=item[13];
			     			var sno2 = document.createTextNode(item[13]);
			     			cell4.appendChild(sno2);
			     			//cell4.style.textAlign="right";
			     			cell4.appendChild(sno1);
			     			mycurrent_row.appendChild(cell4);
			     			
			     			 var cell=document.createElement("TD");
			                 cell.align='CENTER';
			                 var anc=document.createElement("input");
			                 anc.type="checkbox";
		                        anc.value="CHECKED";
			               /*  if(manda=='Y')
			                 {
			                	 anc.setAttribute('checked','checked');
			                	 anc.setAttribute('disabled','disabled');
			                 }
			                 anc.value="CHECKED"; */
			                 anc.id="verify_select"+seq;
			                 anc.name="verify_select"+seq; 
			                 cell.appendChild(anc);
			                 
			                 var anc1=document.createElement("input");
			                 anc1.type="hidden";
			                 anc1.id="verify_select_status"+ seq;
			                 anc1.name="verify_select_status"+ seq;
			                 cell.appendChild(anc1);
			                 mycurrent_row.appendChild(cell);
		    			
		    			var cell10 = document.createElement("TD");
		    			cell10.style.display="none";
		    			var offcode1=document.createElement("input");
		    			offcode1.type="hidden";
		    			offcode1.name="unitid"+ seq;
		    			offcode1.id="unitid"+ seq;
		    			offcode1.value=item[7];
		    			offcode1.size=1;          			
		    			cell10.appendChild(offcode1);
		    			mycurrent_row.appendChild(cell10);
		
		    			var cell10 = document.createElement("TD");
		    			cell10.style.display="none";
		    			var netdepcode1=document.createElement("input");
		    			netdepcode1.type="hidden";
		    			netdepcode1.name="offid"+ seq;
		    			netdepcode1.id="offid"+ seq;
		    			netdepcode1.value=item[8];
		    			netdepcode1.size=1;          			
		    			cell10.appendChild(netdepcode1);
		    			mycurrent_row.appendChild(cell10);
		    			
		    			var cell10 = document.createElement("TD");
		    			cell10.style.display="none";
		    			var netdepcode1=document.createElement("input");
		    			netdepcode1.type="hidden";
		    			netdepcode1.name="yr"+ seq;
		    			netdepcode1.id="yr"+ seq;
		    			netdepcode1.value=item[9];
		    			netdepcode1.size=1;          			
		    			cell10.appendChild(netdepcode1);
		    			mycurrent_row.appendChild(cell10);
		    			
		    			
		    			var cell10 = document.createElement("TD");
		    			cell10.style.display="none";
		    			var netdepcode1=document.createElement("input");
		    			netdepcode1.type="hidden";
		    			netdepcode1.name="mnth"+ seq;
		    			netdepcode1.id="mnth"+ seq;
		    			netdepcode1.value=item[10];
		    			netdepcode1.size=1;          			
		    			cell10.appendChild(netdepcode1);
		    			mycurrent_row.appendChild(cell10);
		    			
		    			var cell4 = document.createElement("TD");
		    			cell4.style.display="none";
		     			var sno1=document.createElement("input");
		     			sno1.type="hidden";
		     			sno1.name="CR_DR_INDICATOR"+ seq;
		     			sno1.id="CR_DR_INDICATOR"+ seq;
		     			sno1.value=item[5];
		     			cell4.appendChild(sno1);
		     			mycurrent_row.appendChild(cell4);
		    			
		    			var cell10 = document.createElement("TD");
		    			cell10.style.display="none";
		    			var netdepcode1=document.createElement("input");
		    			netdepcode1.type="hidden";
		    			netdepcode1.name="doctypp"+ seq;
		    			netdepcode1.id="doctypp"+ seq;
		    			netdepcode1.value=item[12];
		    			netdepcode1.size=1;          			
		    			cell10.appendChild(netdepcode1);
		    			mycurrent_row.appendChild(cell10);
		    			
		    			
		    			
		    			var cell10 = document.createElement("TD");
		    			cell10.style.display="none";
		    			var netdepcode1=document.createElement("input");
		    			netdepcode1.type="hidden";
		    			netdepcode1.name="DocType"+ seq;
		    			netdepcode1.id="DocType"+ seq;
		    			netdepcode1.value=item[2];
		    			netdepcode1.size=1;          			
		    			cell10.appendChild(netdepcode1);
		    			mycurrent_row.appendChild(cell10);
		    			
		    			
		    			
		             tbody.appendChild(mycurrent_row);
		             seq+=1; 
	                	
	                }//else item3 not equal item6
	               
	            // lll++;
	           }//for cleose 
	        //alert("seq  111"+seq);
	        if(scc==0){
            	alert("Already changed");
            }
	        document.getElementById('RecordCount').value = seq; 
	 }
	  }
		  else
		  {
		    alert("Failed to Load Values");
		  }           
	}

	var bank_ac_no =new Array();
	var acc_desc   =new Array();
	var bank_name  = new Array();
	var branch_name = new Array();
	var bank_id  = new Array();
	var branch_id = new Array();
	var opr_mode  = new Array();

function loadAccountNo1(baseResponse){

	 var flag=baseResponse.getElementsByTagName("flag")[0].firstChild.nodeValue;
	 
	 if(flag=="success")
	    {
	      // alert("flag "+flag);   
	           
	           /** Bank Account Number Object to find length */ 
	           var acc_no=baseResponse.getElementsByTagName("acc_no");
	           
	           /** Get Combo box Object */
	           var cmbBankAccNo = document.getElementById("cmbBankAccNo");
	           //alert(acc_no.length);
	            for(var k=0;k<acc_no.length;k++)
	            {
	            	
	            	bank_ac_no[k]=baseResponse.getElementsByTagName("acc_no")[k].firstChild.nodeValue;
	            	
	            	acc_desc[k]=baseResponse.getElementsByTagName("acc_desc")[k].firstChild.nodeValue;
	            
	            	bank_name[k]=baseResponse.getElementsByTagName("bank_name")[k].firstChild.nodeValue;
	            	
	            	branch_name[k]=baseResponse.getElementsByTagName("branch_name")[k].firstChild.nodeValue;
	            	
	            	bank_id[k] =baseResponse.getElementsByTagName("bank_id")[k].firstChild.nodeValue;
	            	
	            	branch_id[k] =baseResponse.getElementsByTagName("branch_id")[k].firstChild.nodeValue;
	            	
	            	opr_mode[k] =baseResponse.getElementsByTagName("opr_mode")[k].firstChild.nodeValue;            	            	
	            
	            }
	         
	            cmbBankAccNo.innerHTML="";
	            var option=document.createElement("OPTION");
	            option.text="--Select Bank Acc. Number--";
	            option.value="";
	            try
	            {
	            	cmbBankAccNo.add(option);
	            }catch(errorObject)
	            {
	            	cmbBankAccNo.add(option,null);
	            }
	            
	            for(var k=0;k<acc_no.length;k++)
	            {   
	                  var option=document.createElement("OPTION");
	                  option.text=acc_desc[k];
	                  //alert(bank_ac_no[k]+"/"+opr_mode[k]);
	                  option.value=bank_ac_no[k]+"/"+opr_mode[k];
	                  try
	                  {
	                	  cmbBankAccNo.add(option);
	                  }
	                  catch(errorObject)
	                  {
	                	  cmbBankAccNo.add(option,null);
	                  }
	            }
	    }
}

function checkTB(){
	 var cmbAcc_UnitCode=document.getElementById("cmbAcc_UnitCode").value;
     var cmbOffice_code=document.getElementById("cmbOffice_code").value;
     var txtCB_Year1=document.getElementById("txtCB_Year").value;
     var txtCB_Month1=document.getElementById("txtCB_Month").value;
     //var TB_date=dateCtrl.value;
   
    // if(dateCtrl.value.length!=0)
     //{
         var url="../../../../../Nrdwp_Account_Change?Command=check_TB&txtCB_Year="+txtCB_Year1+"&txtCB_Month="+txtCB_Month1+"&cmbAcc_UnitCode="+cmbAcc_UnitCode+"&cmbOffice_code="+cmbOffice_code;
         var req=AjaxFunction();
         //alert("ur l"+url);
         req.open("GET",url,true); 
         req.onreadystatechange=function()
         {
        	
           check_TB(req);
         }   ;
         req.send(null);
   //}
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
            
            if(flag=="success")
              {
            	 document.frmnrdwp_AccHeadChange.CmdGo.disabled=false;
            	 document.frmnrdwp_AccHeadChange.changee.disabled=false;
            	loadAccountNo();
              }
             else if(flag=="failure")
               {
            	 document.frmnrdwp_AccHeadChange.CmdGo.disabled=true;
            	 document.frmnrdwp_AccHeadChange.changee.disabled=true;
                    alert("Trial Balance Closed  --- You can't change");//return false;//
                   
               }
           
        }
    }
}
function loadAccountNo(){
	//alert("loadAccountNo");
	var cmbAcc_UnitCode=document.getElementById("cmbAcc_UnitCode").value;
    var cmbOffice_code=document.getElementById("cmbOffice_code").value;
    var txtCB_Year1=document.getElementById("txtCB_Year").value;
    var txtCB_Month1=document.getElementById("txtCB_Month").value;
   
        var url="../../../../../Nrdwp_Account_Change?Command=loadAccountNo&txtCB_Year="+txtCB_Year1+"&txtCB_Month="+txtCB_Month1+"&cmbAcc_UnitCode="+cmbAcc_UnitCode+"&cmbOffice_code="+cmbOffice_code;
        var req=AjaxFunction();
        req.open("GET",url,true); 
       // alert("load asccc"+url);
        req.onreadystatechange=function()
        {
        	manipulate(req);
        } ;  
        req.send(null);
}
function LoadData(){
	var cmbAcc_UnitCode=document.getElementById("cmbAcc_UnitCode").value;
    var cmbOffice_code=document.getElementById("cmbOffice_code").value;
    var txtCB_Year1=document.getElementById("txtCB_Year").value;
    var txtCB_Month1=document.getElementById("txtCB_Month").value;
    var cmbBankAccNo1=document.getElementById("cmbBankAccNo").value;
    var cmbDocType1=document.getElementById("cmbDocType").value;
    
    
        var url="../../../../../Nrdwp_Account_Change?Command=LoadData&txtCB_Year="+txtCB_Year1+"&cmbDocType="+cmbDocType1+"&cmbBankAccNo="+cmbBankAccNo1+"&txtCB_Month="+txtCB_Month1+"&cmbAcc_UnitCode="+cmbAcc_UnitCode+"&cmbOffice_code="+cmbOffice_code;
        var req=AjaxFunction();
        req.open("GET",url,true); 
        req.onreadystatechange=function()
        {
        	manipulate(req);
        } ;  
        req.send(null);
	
	
	
}


function clrForm() {
	LoadAccountingUnitID('LIST_ALL_UNITS');
	//document.getElementById("cmbFinancialYear").length = 1;
	//document.getElementById("cmbFinancialYear").value = "";	

}
function numbersonly1(e, t) {
	var unicode = e.charCode ? e.charCode : e.keyCode;
	if (unicode == 13) {
		try {
			t.blur();
		} catch (e) {
		}
		return true;

	}
	if (unicode != 8 && unicode != 9) {
		if (unicode < 48 || unicode > 57)
			return false;
	}
}
function checkNull() {
  
var cmbAcc_UnitCode = document.getElementById("cmbAcc_UnitCode").value;
var cmbOffice_code = document.getElementById("cmbOffice_code").value;
var txtCB_Year = document.getElementById("txtCB_Year").value;
var txtCB_Month = document.getElementById("txtCB_Month").value;

if (txtCB_Year == "") {
	alert("Enter Cash Book Year in the Field");
	document.frmnrdwp_AccHeadChange.txtCB_Year.focus();
	return false;
} else if (txtCB_Month == "s") {
	alert("Enter Cash Book Month in the Field");
	document.frmnrdwp_AccHeadChange.txtCB_Month.focus();
	return false;
}
else{
	
	var tbody = document.getElementById("tblList");
	var rowcount = tbody.rows.length;
	 
	document.getElementById('RecordCount').value = seq;
	

    var checkbox = document.getElementsByName('verify_select');
    var chvalue=document.getElementsByName("verify_select_status");
    var ln = checkbox.length;
   // alert(ln+" "+chvalue.length);
    
	//var tby=document.getElementById("grid_body").rows[i];//.item(1).firstChild.nodeValue;	
	for(var i=0;i<ln;i++){
		
	try{
		 // alert(ln+" "+chvalue.length);
	if(checkbox[i].checked==true){
		//alert("checked");
		checkbox[i].value="CHECKED";
		chvalue[i].value="CHECKED";
		//document.getElementByName("verify_select_status").value="CHECKED";
		//verify_select_status
		
	}else{
		//alert("unchecked");
		//document.getElementById("verify_select")[i].value="UNCHECKED";
		checkbox[i].value="UNCHECKED";
		chvalue[i].value="UNCHECKED";
		//document.getElementByName("verify_select_status").value="UNCHECKED";
		
	}
	//alert(checkbox[i].value);
	}catch(e){
		alert(e);
	}
	//alert("end");
	
	}
	
	
	return true;
}

}
function exitfun() {
	window.close();
}
