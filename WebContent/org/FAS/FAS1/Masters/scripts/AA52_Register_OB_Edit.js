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



function call_MinorCmb(major)
{
//alert('minor code');	
var url="../../../../../AA52_Register_OB_Edit?command=minor_code&major_code="+major;
var xmlrequest = AjaxFunction();
xmlrequest.open("GET", url, true);
xmlrequest.onreadystatechange = function() {
	cmnLoad(xmlrequest);
}

xmlrequest.send(null);

}
function loadGrid()
{
	var major_cmb=document.getElementById("major_cmb").value;
	//var minor_cmb=document.getElementById("minor_cmb").value;
	var cmbAcc_UnitCode=document.getElementById("cmbAcc_UnitCode").value;
	var cmbOffice_code=document.getElementById("cmbOffice_code").value;
	var cmbFinancialYear=document.getElementById("cmbFinancialYear").value;
	/*var url="../../../../../AA52_Register_OB_Edit?command=get&major_cmb="+major_cmb+
	"&minor_cmb="+minor_cmb+"&cmbAcc_UnitCode="+cmbAcc_UnitCode+"&cmbOffice_code="+cmbOffice_code+
	"&cmbFinancialYear="+cmbFinancialYear;*/
	var url="../../../../../AA52_Register_OB_Edit?command=get&major_cmb="+major_cmb+
	"&cmbAcc_UnitCode="+cmbAcc_UnitCode+"&cmbOffice_code="+cmbOffice_code+
	"&cmbFinancialYear="+cmbFinancialYear;
	
	var xmlrequest = AjaxFunction();
	xmlrequest.open("GET", url, true);
	xmlrequest.onreadystatechange = function() {

		cmnLoad(xmlrequest);

}
	xmlrequest.send(null);
}

function Update_OBvalue()
{
	//alert("test update");
	var major_cmb=document.getElementById("major_cmb").value;
	//var minor_cmb=document.getElementById("minor_cmb").value;
	var cmbAcc_UnitCode=document.getElementById("cmbAcc_UnitCode").value;
	var cmbOffice_code=document.getElementById("cmbOffice_code").value;
	var cmbFinancialYear=document.getElementById("cmbFinancialYear").value;
    var tbody = document.getElementById("grid_body");
    var rowCount=tbody.rows.length;
    //alert(rowCount+"rowCount");
   /* var url="../../../../../AA52_Register_OB_Edit?command=update&major_cmb="+major_cmb+
	"&minor_cmb="+minor_cmb+"&cmbAcc_UnitCode="+cmbAcc_UnitCode+"&cmbOffice_code="+cmbOffice_code+
	"&cmbFinancialYear="+cmbFinancialYear;*/
    var url="../../../../../AA52_Register_OB_Edit?command=update&major_cmb="+major_cmb+
	"&cmbAcc_UnitCode="+cmbAcc_UnitCode+"&cmbOffice_code="+cmbOffice_code+
	"&cmbFinancialYear="+cmbFinancialYear;
    for(var i=0;i<rowCount;i++)    
    {
    var assMinorCode=document.getElementById("assMinorCode"+i).value;	// alert(assMinorCode);
    var bk_val=document.getElementById("BOOKVALUE"+i).value;
   //alert(bk_val);
    var bk_val=document.getElementById("BOOKVALUE"+i).value.split(".")[0];
    var apgrnt_val=document.getElementById("APPOR_GRANT"+i).value.split(".")[0];
    var Depdepit_val=document.getElementById("DEP_DEBIT"+i).value.split(".")[0];
    var JOURNAL_NO=document.getElementById("JOURNAL_NO"+i).value;

    var JOURNAL_DATE=document.getElementById("JOURNAL_DATE"+i).value;
    var SURVEY_NO=document.getElementById("SURVEY_NO"+i).value;
    var SURVEY_DATE=document.getElementById("SURVEY_DATE"+i).value;
    var AUCTION_DATE=document.getElementById("AUCTION_DATE"+i).value;
  //  var PERSON_NAME=document.getElementById("PERSON_NAME"+i).value;
    var AUCTION_AMOUNT=document.getElementById("AUCTION_AMOUNT"+i).value.split(".")[0];
    var CB_VOUCHERNO=document.getElementById("CB_VOUCHERNO"+i).value;
    var CB_VOUCHERDATE=document.getElementById("CB_VOUCHERDATE"+i).value;
    var PROFIT=document.getElementById("PROFIT"+i).value.split(".")[0];
    var LOSS=document.getElementById("LOSS"+i).value.split(".")[0];
    var OFF_DEBIT=document.getElementById("OFF_DEBIT"+i).value.split(".")[0];
    var OFF_CREDIT=document.getElementById("OFF_CREDIT"+i).value.split(".")[0];
    var REMARKS=document.getElementById("REMARKS"+i).value;
    var ASSETCODE=document.getElementById("ASSETCODE"+i).value;
    url+="&assMinorCode="+assMinorCode+"&BOOKVALUE="+bk_val+"&APPOR_GRANT="+apgrnt_val+"&DEP_DEBIT="+Depdepit_val+
	"&JOURNAL_NO="+JOURNAL_NO+"&JOURNAL_DATE="+JOURNAL_DATE+"&SURVEY_NO="+SURVEY_NO+"&SURVEY_DATE="+SURVEY_DATE+
	"&AUCTION_DATE="+AUCTION_DATE+"&AUCTION_AMOUNT="+AUCTION_AMOUNT+"&CB_VOUCHERNO="+CB_VOUCHERNO+
	"&CB_VOUCHERDATE="+CB_VOUCHERDATE+"&PROFIT="+PROFIT+"&LOSS="+LOSS+"&OFF_DEBIT="+OFF_DEBIT+"&OFF_CREDIT="+OFF_CREDIT+"&REMARKS="+REMARKS
	+"&ASSETCODE="+ASSETCODE;
    }
   
//	alert(url);
	var xmlrequest = AjaxFunction();
	xmlrequest.open("GET", url, true);
	
	xmlrequest.onreadystatechange = function() {
		cmnLoad(xmlrequest);

}
	xmlrequest.send(null);
}


function cmnLoad(xmlrequest){
	if(xmlrequest.readyState==4){
	
		if(xmlrequest.status==200){
			var baseResponse = xmlrequest.responseXML.getElementsByTagName("response")[0];
	        var flag = baseResponse.getElementsByTagName("flag")[0].firstChild.nodeValue;
	      
			if(flag=="minorCode"){
			getCmbload(baseResponse);
			}
			if(flag=="get"){
			
				getGrid(baseResponse);
				}
			if(flag=="update"){
				
				update_val(baseResponse);
				}
			if(flag=="Get_UnUpdated")
				{
				
				getUpdateGrid(baseResponse);
				}
			if(flag=="Del_UnUpdated")
			{
			
			DeleteGrid(baseResponse);
			}
		}
	}
	

}


function DeleteGrid(baseResponse)
{

var flag = baseResponse.getElementsByTagName("flag")[0].firstChild.nodeValue;

if(flag=="Del_UnUpdated"){
	
	var result = baseResponse.getElementsByTagName("result")[0].firstChild.nodeValue;
	
	if(result=="success"){
		alert('Press Go button !!!!!');
	}else{
		alert('Not Delete');
	}
}
}


function getCmbload(baseResponse)
{
	var minor_cmb=document.getElementById("minor_cmb");
	minor_cmb.length=0;
	var count = baseResponse.getElementsByTagName("count")[0].firstChild.nodeValue;
	//alert(count);
	for(var i=0;i<count;i++){
		var code=baseResponse.getElementsByTagName("code")[i].firstChild.nodeValue;
		var desc=baseResponse.getElementsByTagName("desc")[i].firstChild.nodeValue;
	
	
		var op = document.createElement("OPTION");
		op.value = code;
		var txt = document.createTextNode(desc);
		op.appendChild(txt);
		minor_cmb.appendChild(op);
	}	

}

function delUpdate()
{
	
	
	var major_cmb=document.getElementById("major_cmb").value;
	var cmbAcc_UnitCode=document.getElementById("cmbAcc_UnitCode").value;
	var cmbOffice_code=document.getElementById("cmbOffice_code").value;
	var cmbFinancialYear=document.getElementById("cmbFinancialYear").value;
	var url="../../../../../AA52_Register_OB_Edit?command=Del_UnUpdated&major_cmb="+major_cmb+
	"&cmbAcc_UnitCode="+cmbAcc_UnitCode+"&cmbOffice_code="+cmbOffice_code+
	"&cmbFinancialYear="+cmbFinancialYear;
	
	var xmlrequest = AjaxFunction();
	xmlrequest.open("GET", url, true);
	xmlrequest.onreadystatechange = function() {

		cmnLoad(xmlrequest);

}
	xmlrequest.send(null);
}

	
	

function getUpdateGrid(baseResponse)
{
	var flag = baseResponse.getElementsByTagName("flag")[0].firstChild.nodeValue;
	if(flag=="Get_UnUpdated"){
	var tbody=document.getElementById("grid_body");
	for (t = tbody.rows.length - 1; t >= 0; t--) {
		tbody.deleteRow(0);
	}
	var seq=0;

	var count = baseResponse.getElementsByTagName("count")[0].firstChild.nodeValue;

	if(count==0){
		alert('Press Go button !!!!!');
	}
	if (count!= 0) {
		
		var r=confirm("Do You want to Update the unupdated Data !!!!!");
		if(r==false)
			{
			delUpdate();
			//loadGrid();
			}
		else{
		
		document.getElementById("row_id").style.display="inline";
		var item = new Array();     
		var codeMinor = new Array();
		var descMinor = new Array();
		for ( var k = 0; k < count; k++) {
			
			item[0] = baseResponse.getElementsByTagName("BOOKVALUE")[k].firstChild.nodeValue;
			item[1] = baseResponse.getElementsByTagName("APPOR_GRANT")[k].firstChild.nodeValue;
			item[2] = baseResponse.getElementsByTagName("DEP_DEBIT")[k].firstChild.nodeValue;
			item[3] = baseResponse.getElementsByTagName("JOURNAL_NO")[k].firstChild.nodeValue;
			item[4] = baseResponse.getElementsByTagName("JOURNAL_DATE")[k].firstChild.nodeValue;
			
			if(item[4]=="null")
			{
				item[4]="-";
			}
			item[5] = baseResponse.getElementsByTagName("SURVEY_NO")[k].firstChild.nodeValue;
			item[6] = baseResponse.getElementsByTagName("SURVEY_DATE")[k].firstChild.nodeValue;
			if(item[6]=="null")
			{
				item[6]="-";
			}
			item[7] = baseResponse.getElementsByTagName("AUCTION_DATE")[k].firstChild.nodeValue;
			if(item[7]=="null")
			{
				item[7]="-";
			}
			item[8] = baseResponse.getElementsByTagName("PERSON_NAME")[k].firstChild.nodeValue;
			item[9] = baseResponse.getElementsByTagName("AUCTION_AMOUNT")[k].firstChild.nodeValue;
			item[10] = baseResponse.getElementsByTagName("CB_VOUCHERNO")[k].firstChild.nodeValue;
			item[11] = baseResponse.getElementsByTagName("CB_VOUCHERDATE")[k].firstChild.nodeValue;
			if(item[11]=="null")
			{
				item[11]="-";
			}
			item[12] = baseResponse.getElementsByTagName("PROFIT")[k].firstChild.nodeValue;
			item[13] = baseResponse.getElementsByTagName("LOSS")[k].firstChild.nodeValue;
			item[14] = baseResponse.getElementsByTagName("OFF_DEBIT")[k].firstChild.nodeValue;
			item[15] = baseResponse.getElementsByTagName("OFF_CREDIT")[k].firstChild.nodeValue;
			item[16] = baseResponse.getElementsByTagName("REMARKS")[k].firstChild.nodeValue;
			item[17] = baseResponse.getElementsByTagName("UPDATED_BY_USERID")[k].firstChild.nodeValue;
			item[18] = baseResponse.getElementsByTagName("UPDATED_DATE")[k].firstChild.nodeValue;
			item[19] = baseResponse.getElementsByTagName("ASSET_MAJOR_CLASS_CODE")[k].firstChild.nodeValue;
			item[20] = baseResponse.getElementsByTagName("ASSET_MINOR_CLASS_CODE")[k].firstChild.nodeValue;
			item[21] = baseResponse.getElementsByTagName("ACCOUNTING_UNIT_ID")[k].firstChild.nodeValue;
			item[22] = baseResponse.getElementsByTagName("ACCOUNTING_UNIT_OFFICE_ID")[k].firstChild.nodeValue;
			item[23] = baseResponse.getElementsByTagName("FINANCIAL_YEAR")[k].firstChild.nodeValue;
	     	item[24]= baseResponse.getElementsByTagName("ASSET_CODE")[k].firstChild.nodeValue;
	    	item[25]= baseResponse.getElementsByTagName("ASSET_MINOR_CLASS_DESC")[k].firstChild.nodeValue;
		 
	    	
		 
			var mycurrent_row = document.createElement("TR");
			mycurrent_row.id = seq;
  
			/** Sl No */
			var cell1 = document.createElement("TD");
			var slno = document.createTextNode(seq + 1);
			//slno.style.fontSize="10pt";
			cell1.appendChild(slno);
			mycurrent_row.appendChild(cell1);

			
			
			  cell21=document.createElement("TD");
             // cell21.style.textAlign='left';  
              var assMinorCode;
           
            	
              if (window.navigator.appName.toLowerCase().indexOf("netscape") == -1)
              {
            	
                    try
                    {
                      assMinorCode =  document.createElement("<select name='assMinorCode' id='assMinorCode' >");
                    }
                    catch( e ) 
                    {
                      alert(e.description) ;
                    }
              }
              else
              {
            	 
                    assMinorCode=document.createElement("select");                        
                    assMinorCode.id="assMinorCode"+k;
                    assMinorCode.name="assMinorCode"+k;
                   assMinorCode.style.width="200px";
              }
              
              var assetMinorDesc = baseResponse.getElementsByTagName("min_class_details"+k);
              //alert(assetMinorDesc);
              
              var option11=document.createElement("option");    
                  option11.value="";  
                  option11.text="--Select --";
                  
             try
               {
                  assMinorCode.add(option11);
               }
            catch(e)
               {
                   assMinorCode.add(option11,null);
                         }                      
            
          //  if
             for(var y=0;y<assetMinorDesc.length;y++)
             {
                  codeMinor[y]=assetMinorDesc[y].getElementsByTagName("MINOR_CLASS_CODE")[0].firstChild.nodeValue;
                  descMinor[y]=assetMinorDesc[y].getElementsByTagName("MINOR_CLASS_DESC")[0].firstChild.nodeValue;
                  
                  var option11=document.createElement("option");    
                  
                  if (codeMinor[y] == item[20])  option11.selected=true;
                  
                  option11.value=codeMinor[y];  
                  option11.text=descMinor[y];
                  
                
                 try
                 {
                       assMinorCode.add(option11);
                 }
                 catch(e)
                 {
                        assMinorCode.add(option11,null);
                 }
             }           
              
             cell21.appendChild(assMinorCode);                                                    
             mycurrent_row.appendChild(cell21);   
		
			var cell20 = document.createElement("TD");
			var rem_ENTRY = document.createElement("input");
			rem_ENTRY.type="text";
			rem_ENTRY.name = "REMARKS"+seq;
			rem_ENTRY.id = "REMARKS"+seq ;
			rem_ENTRY.size='25px';
			rem_ENTRY.value = item[16];
			rem_ENTRY.style.fontSize="8pt";
			//remarks.style.color="blue";
			rem_ENTRY.style.fontFamily="verdana";
			
			cell20.appendChild(rem_ENTRY);
		
			mycurrent_row.appendChild(cell20);
			
			var cell2 = document.createElement("TD");
			var BOOKVALUE_ENTRY = document.createElement("input");
			BOOKVALUE_ENTRY.type="text";
			BOOKVALUE_ENTRY.name = "BOOKVALUE"+seq;
			BOOKVALUE_ENTRY.id = "BOOKVALUE"+seq ;
			BOOKVALUE_ENTRY.size='8';
			BOOKVALUE_ENTRY.style.fontSize="8pt";
			BOOKVALUE_ENTRY.value = item[0];
			cell2.appendChild(BOOKVALUE_ENTRY);
		
			mycurrent_row.appendChild(cell2);
			
			var cell3 = document.createElement("TD");
			var APPOR_GRANT_ENTRY = document.createElement("input");
			APPOR_GRANT_ENTRY.type="text";	
			APPOR_GRANT_ENTRY.name = "APPOR_GRANT"+seq ;
			APPOR_GRANT_ENTRY.id = "APPOR_GRANT"+seq ;
			APPOR_GRANT_ENTRY.size='8';
			APPOR_GRANT_ENTRY.style.fontSize="8pt";
			APPOR_GRANT_ENTRY.value = item[1];
			cell3.appendChild(APPOR_GRANT_ENTRY);
			mycurrent_row.appendChild(cell3);
			
			var cell4 = document.createElement("TD");
			var DEP_DEBIT_ENTRY = document.createElement("input");
			DEP_DEBIT_ENTRY.type="text";	
			DEP_DEBIT_ENTRY.name = "DEP_DEBIT"+seq ;
			DEP_DEBIT_ENTRY.id = "DEP_DEBIT"+seq ;
			DEP_DEBIT_ENTRY.size='8';
			DEP_DEBIT_ENTRY.style.fontSize="8pt";
			DEP_DEBIT_ENTRY.value = item[2];
			cell4.appendChild(DEP_DEBIT_ENTRY);
			mycurrent_row.appendChild(cell4);
		
			var cell5 = document.createElement("TD");
			var JOURNAL_NO_ENTRY = document.createElement("input");
			JOURNAL_NO_ENTRY.type="text";	
			JOURNAL_NO_ENTRY.name = "JOURNAL_NO"+seq ;
			JOURNAL_NO_ENTRY.id = "JOURNAL_NO"+seq ;
			JOURNAL_NO_ENTRY.style.fontSize="8pt";
			JOURNAL_NO_ENTRY.value = item[3];
			JOURNAL_NO_ENTRY.size='8';
			cell5.appendChild(JOURNAL_NO_ENTRY);
			//mycurrent_row.appendChild(cell5);
			
			//var cell6 = document.createElement("TD");
			var JOURNAL_DATE_ENTRY = document.createElement("input");
			JOURNAL_DATE_ENTRY.type="text";	
			JOURNAL_DATE_ENTRY.name = "JOURNAL_DATE"+seq ;
			JOURNAL_DATE_ENTRY.id = "JOURNAL_DATE"+seq ;
			JOURNAL_DATE_ENTRY.size='8';
			JOURNAL_DATE_ENTRY.style.fontSize="8pt";
			JOURNAL_DATE_ENTRY.value = item[4];
			//JOURNAL_DATE_ENTRY.setAttribute("onkeypress", "calins(event,"+item[4]+")");
			JOURNAL_DATE_ENTRY.setAttribute("onblur", "call_date(this)");
			cell5.appendChild(JOURNAL_DATE_ENTRY);
			mycurrent_row.appendChild(cell5);
			/* onkeypress="return calins(event,this);"
                 onblur="call_date(this);"*/
			
			
			var cell9 = document.createElement("TD");
			var AUCTION_DATE_ENTRY = document.createElement("input");
			AUCTION_DATE_ENTRY.type="text";	
			AUCTION_DATE_ENTRY.name = "AUCTION_DATE"+seq ;
			AUCTION_DATE_ENTRY.id = "AUCTION_DATE"+seq ;
			AUCTION_DATE_ENTRY.size='7';
			AUCTION_DATE_ENTRY.style.fontSize="8pt";
			AUCTION_DATE_ENTRY.setAttribute("onblur", "call_date(this)");
			AUCTION_DATE_ENTRY.value = item[7];
			cell9.appendChild(AUCTION_DATE_ENTRY);
			mycurrent_row.appendChild(cell9);
			
		/*	var cell10 = document.createElement("TD");
			var PERSON_NAME_ENTRY = document.createElement("input");
			PERSON_NAME_ENTRY.type="text";	
			PERSON_NAME_ENTRY.name = "PERSON_NAME"+seq ;
			PERSON_NAME_ENTRY.id = "PERSON_NAME"+seq ;
			PERSON_NAME_ENTRY.value = item[7];
			cell10.appendChild(PERSON_NAME_ENTRY);
			mycurrent_row.appendChild(cell10);*/
			
			var cell11 = document.createElement("TD");
			var AUCTION_AMOUNT_ENTRY = document.createElement("input");
			AUCTION_AMOUNT_ENTRY.type="text";
			AUCTION_AMOUNT_ENTRY.name = "AUCTION_AMOUNT"+seq ;
			AUCTION_AMOUNT_ENTRY.id = "AUCTION_AMOUNT"+seq ;
			AUCTION_AMOUNT_ENTRY.size='7';
			AUCTION_AMOUNT_ENTRY.style.fontSize="8pt";
			AUCTION_AMOUNT_ENTRY.value = item[9];
			cell11.appendChild(AUCTION_AMOUNT_ENTRY);
			mycurrent_row.appendChild(cell11);
			
			var cell12 = document.createElement("TD");
			var CB_VOUCHERNO_ENTRY = document.createElement("input");
			CB_VOUCHERNO_ENTRY.type="text";	
			CB_VOUCHERNO_ENTRY.name = "CB_VOUCHERNO"+seq ;
			CB_VOUCHERNO_ENTRY.id = "CB_VOUCHERNO"+seq ;
			CB_VOUCHERNO_ENTRY.size='7';
			CB_VOUCHERNO_ENTRY.style.fontSize="8pt";
			CB_VOUCHERNO_ENTRY.value = item[10];
			cell12.appendChild(CB_VOUCHERNO_ENTRY);
			mycurrent_row.appendChild(cell12);
			
			//var cell13 = document.createElement("TD");
			var CB_VOUCHERDATE_ENTRY = document.createElement("input");
			CB_VOUCHERDATE_ENTRY.type="text";	
			CB_VOUCHERDATE_ENTRY.name = "CB_VOUCHERDATE"+seq ;
			CB_VOUCHERDATE_ENTRY.id = "CB_VOUCHERDATE"+seq ;
			CB_VOUCHERDATE_ENTRY.size='7';
			CB_VOUCHERDATE_ENTRY.style.fontSize="8pt";
			CB_VOUCHERDATE_ENTRY.setAttribute("onblur", "call_date(this)");
			CB_VOUCHERDATE_ENTRY.value = item[11];
			cell12.appendChild(CB_VOUCHERDATE_ENTRY);
			mycurrent_row.appendChild(cell12);
			
			var cell14 = document.createElement("TD");
			var PROFIT_ENTRY = document.createElement("input");
			PROFIT_ENTRY.type="text";	
			PROFIT_ENTRY.name = "PROFIT"+seq ;
			PROFIT_ENTRY.id = "PROFIT"+seq ;
			PROFIT_ENTRY.size='8';
			PROFIT_ENTRY.style.fontSize="8pt";
			PROFIT_ENTRY.value = item[12];
			cell14.appendChild(PROFIT_ENTRY);
			mycurrent_row.appendChild(cell14);
			
		//	var cell15 = document.createElement("TD");
			var LOSS_ENTRY = document.createElement("input");
			LOSS_ENTRY.type="text";	
			LOSS_ENTRY.name = "LOSS"+seq ;
			LOSS_ENTRY.id = "LOSS"+seq ;
			LOSS_ENTRY.size='8';
			LOSS_ENTRY.style.fontSize="8pt";
			LOSS_ENTRY.value = item[13];
			cell14.appendChild(LOSS_ENTRY);
			mycurrent_row.appendChild(cell14);
			
			var cell16 = document.createElement("TD");
			var OFF_DEBIT_ENTRY = document.createElement("input");
			OFF_DEBIT_ENTRY.type="text";	
			OFF_DEBIT_ENTRY.name = "OFF_DEBIT"+seq ;
			OFF_DEBIT_ENTRY.id = "OFF_DEBIT"+seq ;
			OFF_DEBIT_ENTRY.size='8';
			OFF_DEBIT_ENTRY.style.fontSize="8pt";
			OFF_DEBIT_ENTRY.value = item[14];
			cell16.appendChild(OFF_DEBIT_ENTRY);
			mycurrent_row.appendChild(cell16);
			
			var cell17 = document.createElement("TD");
			var OFF_CREDIT_ENTRY = document.createElement("input");
			OFF_CREDIT_ENTRY.type="text";	
			OFF_CREDIT_ENTRY.name = "OFF_CREDIT"+seq ;
			OFF_CREDIT_ENTRY.id = "OFF_CREDIT"+seq ;
			OFF_CREDIT_ENTRY.size='8';
			OFF_CREDIT_ENTRY.style.fontSize="8pt";
			OFF_CREDIT_ENTRY.value = item[15];
			cell17.appendChild(OFF_CREDIT_ENTRY);
			mycurrent_row.appendChild(cell17);
			
			var cell7 = document.createElement("TD");
			var SURVEY_NO_ENTRY = document.createElement("input");
			SURVEY_NO_ENTRY.type="text";	
			SURVEY_NO_ENTRY.name = "SURVEY_NO"+seq ;
			SURVEY_NO_ENTRY.id = "SURVEY_NO"+seq ;
			SURVEY_NO_ENTRY.size='8';
			SURVEY_NO_ENTRY.style.fontSize="8pt";
			SURVEY_NO_ENTRY.value = item[5];
			cell7.appendChild(SURVEY_NO_ENTRY);
			mycurrent_row.appendChild(cell7);
			
			//var cell8 = document.createElement("TD");
			var SURVEY_DATE_ENTRY = document.createElement("input");
			SURVEY_DATE_ENTRY.type="text";	
			SURVEY_DATE_ENTRY.name = "SURVEY_DATE"+seq ;
			SURVEY_DATE_ENTRY.id = "SURVEY_DATE"+seq ;
			SURVEY_DATE_ENTRY.size='8';
			SURVEY_DATE_ENTRY.style.fontSize="8pt";
			SURVEY_DATE_ENTRY.setAttribute("onblur", "call_date(this)");
			SURVEY_DATE_ENTRY.value = item[6];
			cell7.appendChild(SURVEY_DATE_ENTRY);
			mycurrent_row.appendChild(cell7);
			
			
			var cell19 = document.createElement("TD");
			cell19.style.display="none";
			var ass_cde = document.createElement("input");
			ass_cde.type="text";	
			ass_cde.name = "ASSETCODE"+seq ;
			ass_cde.id = "ASSETCODE"+seq ;
			ass_cde.value = item[24];
			cell19.appendChild(ass_cde);
			mycurrent_row.appendChild(cell19);
			
			tbody.appendChild(mycurrent_row);
			seq+=1;;
			
			
		}
	}
		}
	}
	}


function chk_Update()
{
	alert('testr');
	var major_cmb=document.getElementById("major_cmb").value;
	var cmbAcc_UnitCode=document.getElementById("cmbAcc_UnitCode").value;
	var cmbOffice_code=document.getElementById("cmbOffice_code").value;
	var cmbFinancialYear=document.getElementById("cmbFinancialYear").value;
	var url="../../../../../AA52_Register_OB_Edit?command=Get_UnUpdated&major_cmb="+major_cmb+
	"&cmbAcc_UnitCode="+cmbAcc_UnitCode+"&cmbOffice_code="+cmbOffice_code+
	"&cmbFinancialYear="+cmbFinancialYear;
	var xmlrequest = AjaxFunction();
	xmlrequest.open("GET", url, true);
	xmlrequest.onreadystatechange = function() {

		cmnLoad(xmlrequest);

}
	xmlrequest.send(null);
}


function getGrid(baseResponse)
{

	var flag = baseResponse.getElementsByTagName("flag")[0].firstChild.nodeValue;//alert("!!!!!!!!"+flag);
	if(flag=="get"){
	
	var tbody=document.getElementById("grid_body");
	for (t = tbody.rows.length - 1; t >= 0; t--) {
		tbody.deleteRow(0);
	}
	var seq=0;

	var count = baseResponse.getElementsByTagName("count")[0].firstChild.nodeValue;

	if(count==0){
		alert('No data for the selected Class !!!!!');
	}



	if (count!= 0) {
		document.getElementById("row_id").style.display="inline";
		var item = new Array();     
		var codeMinor = new Array();
		var descMinor = new Array();
		for ( var k = 0; k < count; k++) {
			
			item[0] = baseResponse.getElementsByTagName("BOOKVALUE")[k].firstChild.nodeValue;
			item[1] = baseResponse.getElementsByTagName("APPOR_GRANT")[k].firstChild.nodeValue;
			item[2] = baseResponse.getElementsByTagName("DEP_DEBIT")[k].firstChild.nodeValue;
			item[3] = baseResponse.getElementsByTagName("JOURNAL_NO")[k].firstChild.nodeValue;
			item[4] = baseResponse.getElementsByTagName("JOURNAL_DATE")[k].firstChild.nodeValue;
			if(item[4]=="null")
			{
				item[4]="-";
			}
			item[5] = baseResponse.getElementsByTagName("SURVEY_NO")[k].firstChild.nodeValue;
			item[6] = baseResponse.getElementsByTagName("SURVEY_DATE")[k].firstChild.nodeValue;
			if(item[6]=="null")
			{
				item[6]="-";
			}
			item[7] = baseResponse.getElementsByTagName("AUCTION_DATE")[k].firstChild.nodeValue;
			if(item[7]=="null")
			{
				item[7]="-";
			}
			item[8] = baseResponse.getElementsByTagName("PERSON_NAME")[k].firstChild.nodeValue;
			item[9] = baseResponse.getElementsByTagName("AUCTION_AMOUNT")[k].firstChild.nodeValue;
			item[10] = baseResponse.getElementsByTagName("CB_VOUCHERNO")[k].firstChild.nodeValue;
			item[11] = baseResponse.getElementsByTagName("CB_VOUCHERDATE")[k].firstChild.nodeValue;
			if(item[11]=="null")
			{
				item[11]="-";
			}
			item[12] = baseResponse.getElementsByTagName("PROFIT")[k].firstChild.nodeValue;
			item[13] = baseResponse.getElementsByTagName("LOSS")[k].firstChild.nodeValue;
			item[14] = baseResponse.getElementsByTagName("OFF_DEBIT")[k].firstChild.nodeValue;
			item[15] = baseResponse.getElementsByTagName("OFF_CREDIT")[k].firstChild.nodeValue;
			item[16] = baseResponse.getElementsByTagName("REMARKS")[k].firstChild.nodeValue;
			item[17] = baseResponse.getElementsByTagName("UPDATED_BY_USERID")[k].firstChild.nodeValue;
			item[18] = baseResponse.getElementsByTagName("UPDATED_DATE")[k].firstChild.nodeValue;
			item[19] = baseResponse.getElementsByTagName("ASSET_MAJOR_CLASS_CODE")[k].firstChild.nodeValue;
			item[20] = baseResponse.getElementsByTagName("ASSET_MINOR_CLASS_CODE")[k].firstChild.nodeValue;
			item[21] = baseResponse.getElementsByTagName("ACCOUNTING_UNIT_ID")[k].firstChild.nodeValue;
			item[22] = baseResponse.getElementsByTagName("ACCOUNTING_UNIT_OFFICE_ID")[k].firstChild.nodeValue;
			item[23] = baseResponse.getElementsByTagName("FINANCIAL_YEAR")[k].firstChild.nodeValue;
	     	item[24]= baseResponse.getElementsByTagName("ASSET_CODE")[k].firstChild.nodeValue;
	    	item[25]= baseResponse.getElementsByTagName("ASSET_MINOR_CLASS_DESC")[k].firstChild.nodeValue;
		 
	    	
		 
			var mycurrent_row = document.createElement("TR");
			mycurrent_row.id = seq;
  
			/** Sl No */
			var cell1 = document.createElement("TD");
			var slno = document.createTextNode(seq + 1);
			//slno.style.fontSize="10pt";
			cell1.appendChild(slno);
			mycurrent_row.appendChild(cell1);

			
			
			  cell21=document.createElement("TD");
             // cell21.style.textAlign='left';  
              var assMinorCode;
           
            	
              if (window.navigator.appName.toLowerCase().indexOf("netscape") == -1)
              {
            	
                    try
                    {
                      assMinorCode =  document.createElement("<select name='assMinorCode' id='assMinorCode' >");
                    }
                    catch( e ) 
                    {
                      alert(e.description) ;
                    }
              }
              else
              {
            	 
                    assMinorCode=document.createElement("select");                        
                    assMinorCode.id="assMinorCode"+k;
                    assMinorCode.name="assMinorCode"+k;
                   assMinorCode.style.width="200px";
              }
              
              var assetMinorDesc = baseResponse.getElementsByTagName("min_class_details"+k);
              //alert(assetMinorDesc);
              
              var option11=document.createElement("option");    
                  option11.value="";  
                  option11.text="--Select --";
                  
             try
               {
                  assMinorCode.add(option11);
               }
            catch(e)
               {
                   assMinorCode.add(option11,null);
                         }                      
            
          //  if
             for(var y=0;y<assetMinorDesc.length;y++)
             {
                  codeMinor[y]=assetMinorDesc[y].getElementsByTagName("MINOR_CLASS_CODE")[0].firstChild.nodeValue;
                  descMinor[y]=assetMinorDesc[y].getElementsByTagName("MINOR_CLASS_DESC")[0].firstChild.nodeValue;
                  
                  var option11=document.createElement("option");    
                  
                  if (codeMinor[y] == item[20])  option11.selected=true;
                  
                  option11.value=codeMinor[y];  
                  option11.text=descMinor[y];
                  
                
                 try
                 {
                       assMinorCode.add(option11);
                 }
                 catch(e)
                 {
                        assMinorCode.add(option11,null);
                 }
             }           
              
             cell21.appendChild(assMinorCode);                                                    
             mycurrent_row.appendChild(cell21);   
		
			var cell20 = document.createElement("TD");
			var rem_ENTRY = document.createElement("input");
			rem_ENTRY.type="text";
			rem_ENTRY.name = "REMARKS"+seq;
			rem_ENTRY.id = "REMARKS"+seq ;
			rem_ENTRY.size='25px';
			rem_ENTRY.value = item[16];
			rem_ENTRY.style.fontSize="8pt";
			//remarks.style.color="blue";
			rem_ENTRY.style.fontFamily="verdana";
			
			cell20.appendChild(rem_ENTRY);
		
			mycurrent_row.appendChild(cell20);
			
			var cell2 = document.createElement("TD");
			var BOOKVALUE_ENTRY = document.createElement("input");
			BOOKVALUE_ENTRY.type="text";
			BOOKVALUE_ENTRY.name = "BOOKVALUE"+seq;
			BOOKVALUE_ENTRY.id = "BOOKVALUE"+seq ;
			BOOKVALUE_ENTRY.size='9';
			BOOKVALUE_ENTRY.style.fontSize="8pt";
			BOOKVALUE_ENTRY.value = item[0];
			cell2.appendChild(BOOKVALUE_ENTRY);
		
			mycurrent_row.appendChild(cell2);
			
			var cell3 = document.createElement("TD");
			var APPOR_GRANT_ENTRY = document.createElement("input");
			APPOR_GRANT_ENTRY.type="text";	
			APPOR_GRANT_ENTRY.name = "APPOR_GRANT"+seq ;
			APPOR_GRANT_ENTRY.id = "APPOR_GRANT"+seq ;
			APPOR_GRANT_ENTRY.size='9';
			APPOR_GRANT_ENTRY.style.fontSize="8pt";
			APPOR_GRANT_ENTRY.value = item[1];
			cell3.appendChild(APPOR_GRANT_ENTRY);
			mycurrent_row.appendChild(cell3);
			
			var cell4 = document.createElement("TD");
			var DEP_DEBIT_ENTRY = document.createElement("input");
			DEP_DEBIT_ENTRY.type="text";	
			DEP_DEBIT_ENTRY.name = "DEP_DEBIT"+seq ;
			DEP_DEBIT_ENTRY.id = "DEP_DEBIT"+seq ;
			DEP_DEBIT_ENTRY.size='9';
			DEP_DEBIT_ENTRY.style.fontSize="8pt";
			DEP_DEBIT_ENTRY.value = item[2];
			cell4.appendChild(DEP_DEBIT_ENTRY);
			mycurrent_row.appendChild(cell4);
		
			var cell5 = document.createElement("TD");
			var JOURNAL_NO_ENTRY = document.createElement("input");
			JOURNAL_NO_ENTRY.type="text";	
			JOURNAL_NO_ENTRY.name = "JOURNAL_NO"+seq ;
			JOURNAL_NO_ENTRY.id = "JOURNAL_NO"+seq ;
			JOURNAL_NO_ENTRY.style.fontSize="8pt";
			JOURNAL_NO_ENTRY.value = item[3];
			JOURNAL_NO_ENTRY.size='9';
			cell5.appendChild(JOURNAL_NO_ENTRY);
			mycurrent_row.appendChild(cell5);
			
			//var cell6 = document.createElement("TD");
			var JOURNAL_DATE_ENTRY = document.createElement("input");
			JOURNAL_DATE_ENTRY.type="text";	
			JOURNAL_DATE_ENTRY.name = "JOURNAL_DATE"+seq ;
			JOURNAL_DATE_ENTRY.id = "JOURNAL_DATE"+seq ;
			JOURNAL_DATE_ENTRY.size='9';
			JOURNAL_DATE_ENTRY.style.fontSize="8pt";
			JOURNAL_DATE_ENTRY.setAttribute("onblur", "call_date(this)");
			JOURNAL_DATE_ENTRY.value = item[4];
			cell5.appendChild(JOURNAL_DATE_ENTRY);
			mycurrent_row.appendChild(cell5);
			
			
			
			var cell9 = document.createElement("TD");
			var AUCTION_DATE_ENTRY = document.createElement("input");
			AUCTION_DATE_ENTRY.type="text";	
			AUCTION_DATE_ENTRY.name = "AUCTION_DATE"+seq ;
			AUCTION_DATE_ENTRY.id = "AUCTION_DATE"+seq ;
			AUCTION_DATE_ENTRY.size='9';
			AUCTION_DATE_ENTRY.style.fontSize="8pt";
			AUCTION_DATE_ENTRY.setAttribute("onblur", "call_date(this)");
			AUCTION_DATE_ENTRY.value = item[7];
			cell9.appendChild(AUCTION_DATE_ENTRY);
			mycurrent_row.appendChild(cell9);
			
		/*	var cell10 = document.createElement("TD");
			var PERSON_NAME_ENTRY = document.createElement("input");
			PERSON_NAME_ENTRY.type="text";	
			PERSON_NAME_ENTRY.name = "PERSON_NAME"+seq ;
			PERSON_NAME_ENTRY.id = "PERSON_NAME"+seq ;
			PERSON_NAME_ENTRY.value = item[7];
			cell10.appendChild(PERSON_NAME_ENTRY);
			mycurrent_row.appendChild(cell10);*/
			
			var cell11 = document.createElement("TD");
			var AUCTION_AMOUNT_ENTRY = document.createElement("input");
			AUCTION_AMOUNT_ENTRY.type="text";
			AUCTION_AMOUNT_ENTRY.name = "AUCTION_AMOUNT"+seq ;
			AUCTION_AMOUNT_ENTRY.id = "AUCTION_AMOUNT"+seq ;
			AUCTION_AMOUNT_ENTRY.size='9';
			AUCTION_AMOUNT_ENTRY.style.fontSize="8pt";
			AUCTION_AMOUNT_ENTRY.value = item[9];
			cell11.appendChild(AUCTION_AMOUNT_ENTRY);
			mycurrent_row.appendChild(cell11);
			
			var cell12 = document.createElement("TD");
			var CB_VOUCHERNO_ENTRY = document.createElement("input");
			CB_VOUCHERNO_ENTRY.type="text";	
			CB_VOUCHERNO_ENTRY.name = "CB_VOUCHERNO"+seq ;
			CB_VOUCHERNO_ENTRY.id = "CB_VOUCHERNO"+seq ;
			CB_VOUCHERNO_ENTRY.size='9';
			CB_VOUCHERNO_ENTRY.style.fontSize="8pt";
			CB_VOUCHERNO_ENTRY.value = item[10];
			cell12.appendChild(CB_VOUCHERNO_ENTRY);
			mycurrent_row.appendChild(cell12);
			
			//var cell13 = document.createElement("TD");
			var CB_VOUCHERDATE_ENTRY = document.createElement("input");
			CB_VOUCHERDATE_ENTRY.type="text";	
			CB_VOUCHERDATE_ENTRY.name = "CB_VOUCHERDATE"+seq ;
			CB_VOUCHERDATE_ENTRY.id = "CB_VOUCHERDATE"+seq ;
			CB_VOUCHERDATE_ENTRY.size='9';
			CB_VOUCHERDATE_ENTRY.style.fontSize="8pt";
			CB_VOUCHERDATE_ENTRY.setAttribute("onblur", "call_date(this)");
			CB_VOUCHERDATE_ENTRY.value = item[11];
			cell12.appendChild(CB_VOUCHERDATE_ENTRY);
			mycurrent_row.appendChild(cell12);
			
			var cell14 = document.createElement("TD");
			var PROFIT_ENTRY = document.createElement("input");
			PROFIT_ENTRY.type="text";	
			PROFIT_ENTRY.name = "PROFIT"+seq ;
			PROFIT_ENTRY.id = "PROFIT"+seq ;
			PROFIT_ENTRY.size='9';
			PROFIT_ENTRY.style.fontSize="8pt";
			PROFIT_ENTRY.value = item[12];
			cell14.appendChild(PROFIT_ENTRY);
			mycurrent_row.appendChild(cell14);
			
			//var cell15 = document.createElement("TD");
			var LOSS_ENTRY = document.createElement("input");
			LOSS_ENTRY.type="text";	
			LOSS_ENTRY.name = "LOSS"+seq ;
			LOSS_ENTRY.id = "LOSS"+seq ;
			LOSS_ENTRY.size='9';
			LOSS_ENTRY.style.fontSize="8pt";
			LOSS_ENTRY.value = item[13];
			cell14.appendChild(LOSS_ENTRY);
			mycurrent_row.appendChild(cell14);
			
			var cell16 = document.createElement("TD");
			var OFF_DEBIT_ENTRY = document.createElement("input");
			OFF_DEBIT_ENTRY.type="text";	
			OFF_DEBIT_ENTRY.name = "OFF_DEBIT"+seq ;
			OFF_DEBIT_ENTRY.id = "OFF_DEBIT"+seq ;
			OFF_DEBIT_ENTRY.size='9';
			OFF_DEBIT_ENTRY.style.fontSize="8pt";
			OFF_DEBIT_ENTRY.value = item[14];
			cell16.appendChild(OFF_DEBIT_ENTRY);
			mycurrent_row.appendChild(cell16);
			
			var cell17 = document.createElement("TD");
			var OFF_CREDIT_ENTRY = document.createElement("input");
			OFF_CREDIT_ENTRY.type="text";	
			OFF_CREDIT_ENTRY.name = "OFF_CREDIT"+seq ;
			OFF_CREDIT_ENTRY.id = "OFF_CREDIT"+seq ;
			OFF_CREDIT_ENTRY.size='9';
			OFF_CREDIT_ENTRY.style.fontSize="8pt";
			OFF_CREDIT_ENTRY.value = item[15];
			cell17.appendChild(OFF_CREDIT_ENTRY);
			mycurrent_row.appendChild(cell17);
			
			var cell7 = document.createElement("TD");
			var SURVEY_NO_ENTRY = document.createElement("input");
			SURVEY_NO_ENTRY.type="text";	
			SURVEY_NO_ENTRY.name = "SURVEY_NO"+seq ;
			SURVEY_NO_ENTRY.id = "SURVEY_NO"+seq ;
			SURVEY_NO_ENTRY.size='9';
			SURVEY_NO_ENTRY.style.fontSize="8pt";
			SURVEY_NO_ENTRY.value = item[5];
			cell7.appendChild(SURVEY_NO_ENTRY);
			mycurrent_row.appendChild(cell7);
			
			//var cell8 = document.createElement("TD");
			var SURVEY_DATE_ENTRY = document.createElement("input");
			SURVEY_DATE_ENTRY.type="text";	
			SURVEY_DATE_ENTRY.name = "SURVEY_DATE"+seq ;
			SURVEY_DATE_ENTRY.id = "SURVEY_DATE"+seq ;
			SURVEY_DATE_ENTRY.size='9';
			SURVEY_DATE_ENTRY.style.fontSize="8pt";
			SURVEY_DATE_ENTRY.setAttribute("onblur", "call_date(this)");
			SURVEY_DATE_ENTRY.value = item[6];
			cell7.appendChild(SURVEY_DATE_ENTRY);
			mycurrent_row.appendChild(cell7);
			
			/*var cell18 = document.createElement("TD");
			cell18.style.display="none";
			var rem_ENTRY = document.createElement("input");
			rem_ENTRY.type="text";	
			rem_ENTRY.name = "REMARKS"+seq ;
			rem_ENTRY.id = "REMARKS"+seq ;
			rem_ENTRY.value = item[16];
			cell18.appendChild(rem_ENTRY);
			mycurrent_row.appendChild(cell18);*/
			
			var cell19 = document.createElement("TD");
			cell19.style.display="none";
			var ass_cde = document.createElement("input");
			ass_cde.type="text";	
			ass_cde.name = "ASSETCODE"+seq ;
			ass_cde.id = "ASSETCODE"+seq ;
			ass_cde.value = item[24];
			cell19.appendChild(ass_cde);
			mycurrent_row.appendChild(cell19);
			
			tbody.appendChild(mycurrent_row);
			seq+=1;;
			
			
		}
	}
		}
	}

function update_val(baseResponse)
{
	
	var flag = baseResponse.getElementsByTagName("flag")[0].firstChild.nodeValue;
	
	if(flag=="update"){
		
		var result = baseResponse.getElementsByTagName("result")[0].firstChild.nodeValue;
		if(result=="success"){
			alert('Updated Successfullly !!!!!');
			var tbody=document.getElementById("grid_body");
			for (t = tbody.rows.length - 1; t >= 0; t--) {
				tbody.deleteRow(0);
			}
			document.getElementById("major_cmb").value="";
			document.getElementById("row_id").style.display="none";
		}else{
			alert('Not Update');
		}
	}
	
	
	
	}

function call_date(t)
{
	 // alert("test"+t);
	    if(t.value.length==0)
	        return false;
	    if(t.value.length==10  && t.value.indexOf('/',0)==2 && t.value.indexOf('/',3)==5)
	    {
	      
	       
	        // var c=t.value.replace(/-/g,'/');
	         var c=t.value;
	        try{
	        var f=DateFormat(t,c,event,true,'3');
	        }catch(e){
	        //exception  start
	        
	         t.value=c;
	            var sc=t.value.split('/');
	            var currenDay =sc[0];
	            var currentMonth=sc[1];
	            var currentYear=sc[2];
	            //alert(currentYear == getCurrentYear()  && currentMonth == getCurrentMonth() && currenDay > getCurrentDay());
	            if(currentYear<1970)
	            {
	            
	                    alert('Entered date should be greater than or equal to '+_Service_Period_Beg_Year);
	                    t.value="";
	                    t.focus();
	                    return false;
	           } 
	          
	            t.value=c;
	             if(err!=0)
	                {
	                    t.value="";
	                    return false;
	                }
	            return true;
	        
	        
	        //exception end
	        
	        }
	        if( f==true)
	        {
	            //alert(f);
	            //t.value=c.replace(/\//g,'-');
	            t.value=c;
	            var sc=t.value.split('/');
	            var currenDay =sc[0];
	            var currentMonth=sc[1];
	            var currentYear=sc[2];
	            //alert(currentYear == getCurrentYear()  && currentMonth == getCurrentMonth() && currenDay > getCurrentDay());
	         
	            if(currentYear<_Service_Period_Beg_Year)
	            {
	            
	                    alert('Entered date should be greater than or equal to '+_Service_Period_Beg_Year);
	                    t.value="";
	                    t.focus();
	                    return false;
	           } 
	                      
	            t.value=c;
	           
	            return true;
	            
	        }
	        else
	        {
	                if(err!=0)
	                {
	                    t.value="";
	                    return false;
	                }
	        }
	            
	    }
	    else
	    {
	            alert('Date format  should be (dd/mm/yyyy)');
	            t.value="";
	            //t.focus();
	            return false
	    }
	   
}

	
