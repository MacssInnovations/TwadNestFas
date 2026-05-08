var seq=0;
var Common_branchID="";
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
function template(val)
{
//alert(val)
        if(val=="view")
            document.getElementById("temp").style.display="";
        if(val=="clr")
            document.getElementById("temp").style.display="none";
        
}
var window_BankAccNumber_module;
function ListHeads()
    {
    
     if (window_BankAccNumber_module && window_BankAccNumber_module.open && !window_BankAccNumber_module.closed) 
    {
       window_BankAccNumber_module.resizeTo(500,500);
       window_BankAccNumber_module.moveTo(250,250); 
       window_BankAccNumber_module.focus();
    }
    else
    {
        window_BankAccNumber_module=null
    }
         var cmbAcc_UnitCode=document.getElementById("cmbAcc_UnitCode").value;
         window_BankAccNumber_module= window.open("Bank_AccountHeadCode_Module_List.jsp?cmbAcc_UnitCode="+cmbAcc_UnitCode,"mywindow1","resizable=YES, scrollbars=yes"); 
         window_BankAccNumber_module.moveTo(250,250);    
    }

window.onunload=function()
{
//alert('hh');
if (window_BankAccNumber_module && window_BankAccNumber_module.open && !window_BankAccNumber_module.closed) window_BankAccNumber_module.close();
}

function view_grid()
{
 var txtOperation_mode=document.getElementById("txtOperation_mode").value;
 //alert(txtOperation_mode);
 var url = "../../../../../Bank_AccountHeadCode_Module.view?Command=viewGrid&txtOperation_mode="+ txtOperation_mode;
            var req = getTransport();
            req.open("GET", url, true);
            req.onreadystatechange = function() {
                handleResponse(req);
            }
            req.send(null); 
}

function add_grid()
{
	var flag=nullcheck();
	if(flag)
	{
		var cmbAcc_UnitCode=document.getElementById("cmbAcc_UnitCode").value;
		var cmbAcc_UnitCode_txt=document.getElementById("cmbAcc_UnitCode").options[document.getElementById("cmbAcc_UnitCode").selectedIndex].text;
		var txtBankAccountNo=document.getElementById("txtBankAccountNo").value;
		var txtBankAccountNo_txt=document.getElementById("txtBankAccountNo").options[document.getElementById("txtBankAccountNo").selectedIndex].text;
		var txtBankId=document.getElementById("txtBankId").value;
		var txtBankId_name=document.getElementById("txtBankId_name").value;
		var txtBranchId=document.getElementById("txtBranchId").value;
		var txtBranchId_name=document.getElementById("txtBranchId_name").value;
		var txtBankAcc_type=document.getElementById("txtBankAcc_type").value;
		var txtBankAcc_type_name=document.getElementById("txtBankAcc_type_name").value;
		var txtOperation_mode=document.getElementById("txtOperation_mode").value;
		var txtOperation_mode_name=document.getElementById("txtOperation_mode_name").value;
		var txtAcc_HeadCode=document.getElementById("txtAcc_HeadCode").value;
		var txtAcc_HeadDesc=document.getElementById("txtAcc_HeadDesc").value;
		var cmbModule=document.getElementById("cmbModule").value;
		var cmbModule_txt=document.getElementById("cmbModule").options[document.getElementById("cmbModule").selectedIndex].text;
		
		var tbody=document.getElementById("grid_body");
		var t=0;
		var mycurrent_row=document.createElement("TR");
		seq=seq+1;
		mycurrent_row.id=seq;
		var i=0;
		var cell2;
		cell2=document.createElement("TD");

		var cmbAcc_UnitCode=document.createElement("input");
		cmbAcc_UnitCode.type="hidden";
		cmbAcc_UnitCode.name="cmbAcc_UnitCode";
		cmbAcc_UnitCode.value=cmbAcc_UnitCode;
		cell2.appendChild(cmbAcc_UnitCode);
		var currentText=document.createTextNode(cmbAcc_UnitCode_txt);
		cell2.appendChild(currentText);
		mycurrent_row.appendChild(cell2);

		cell2=document.createElement("TD"); 

		var txtBankAccountNo=document.createElement("input");
		txtBankAccountNo.type="hidden";
		txtBankAccountNo.name="txtBankAccountNo";
		txtBankAccountNo.value=txtBankAccountNo;
		cell2.appendChild(txtBankAccountNo);
		var currentText=document.createTextNode(txtBankAccountNo_txt);
		cell2.appendChild(currentText);
		mycurrent_row.appendChild(cell2);

		cell2=document.createElement("TD"); 

		var txtBankId=document.createElement("input");
		txtBankId.type="hidden";
		txtBankId.name="txtBankId";
		txtBankId.value=txtBankId;
		cell2.appendChild(txtBankId);
		var currentText=document.createTextNode(txtBankId_name);
		cell2.appendChild(currentText);
		mycurrent_row.appendChild(cell2);

		cell2=document.createElement("TD");

		var txtBranchId=document.createElement("input");
		txtBranchId.type="hidden";
		txtBranchId.name="txtBranchId";
		txtBranchId.value=txtBranchId;
		cell2.appendChild(txtBranchId);
		var currentText=document.createTextNode(txtBranchId_name);
		cell2.appendChild(currentText);
		mycurrent_row.appendChild(cell2);

		cell2=document.createElement("TD");

		var txtBankAcc_type=document.createElement("input");
		txtBankAcc_type.type="hidden";
		txtBankAcc_type.name="txtBankAcc_type";
		txtBankAcc_type.value=txtBankAcc_type;
		cell2.appendChild(txtBankAcc_type);
		var currentText=document.createTextNode(txtBankAcc_type_name);
		cell2.appendChild(currentText);
		mycurrent_row.appendChild(cell2);

		cell2=document.createElement("TD");

		var txtOperation_mode=document.createElement("input");
		txtOperation_mode.type="hidden";
		txtOperation_mode.name="txtOperation_mode";
		txtOperation_mode.value=txtOperation_mode;
		cell2.appendChild(txtOperation_mode);
		var currentText=document.createTextNode(txtOperation_mode_name);
		cell2.appendChild(currentText);
		mycurrent_row.appendChild(cell2);

		cell2=document.createElement("TD");
		
		var txtAcc_Headode=document.createElement("input");
		txtAcc_Headode.type="hidden";
		txtAcc_Headode.name="txtAcc_HeadCode";
		txtAcc_Headode.value=txtAcc_HeadCode;
		cell2.appendChild(txtAcc_Headode);
		var currentText=document.createTextNode(txtAcc_HeadCode+" - "+txtAcc_HeadDesc);
		cell2.appendChild(currentText);
		mycurrent_row.appendChild(cell2);

		cell2=document.createElement("TD");

		var cmbModule=document.createElement("input");
		cmbModule.type="hidden";
		cmbModule.name="cmbModule";
		cmbModule.value=cmbModule;
		cell2.appendChild(cmbModule);
		var currentText=document.createTextNode(cmbModule_txt);
		cell2.appendChild(currentText);
		mycurrent_row.appendChild(cell2);

		tbody.appendChild(mycurrent_row);
		ClearAll();
	}
}
function doParentBankAccNo_Head_module(bank_AccNo,branch_id,bankid,bank_acc_type,operID,acchead,moduleID,CRDR_ind,remark,bankid_name,branch_id_name,bank_acc_type_name,operID_name,acchead_desc,defaultvalue)
{
         
            var d=document.getElementById("cmdUpdate");
            d.style.display="block";
            //var d2=document.getElementById("cmdDelete");
            //d2.style.display="block";
            var d1=document.getElementById("cmdAdd");
            d1.style.display="none";  
    
    document.getElementById("txtBankId").value=bankid;
    document.getElementById("txtBranchId").value=branch_id;
    document.getElementById("txtBankAcc_type").value=bank_acc_type;
    document.getElementById("txtOperation_mode").value=operID;
    document.getElementById("txtBankAccountNo").value=bank_AccNo;
    document.getElementById("txtAcc_HeadCode").value=acchead;
    document.getElementById("cmbModule").value=moduleID;
    if(CRDR_ind=="CR")
    document.frmBank_AccountHeadCode_Module.rad_CR_DR[0].checked=true;
    else
    document.frmBank_AccountHeadCode_Module.rad_CR_DR[1].checked=true;
    
    document.getElementById("txtRemarks").value=remark;
    
    if(defaultvalue==1)
    document.frmBank_AccountHeadCode_Module.radDefault[0].checked=true;
    else
    document.frmBank_AccountHeadCode_Module.radDefault[1].checked=true;
    if(defaultvalue==1)
    document.frmBank_AccountHeadCode_Module.radStatus[0].checked=true;
    else
    document.frmBank_AccountHeadCode_Module.radStatus[1].checked=true;
    
        document.getElementById("txtBankId_name").value=bankid_name;
        document.getElementById("txtBranchId_name").value=branch_id_name;
        document.getElementById("txtBankAcc_type_name").value=bank_acc_type_name;
        document.getElementById("txtOperation_mode_name").value=operID_name;
        document.getElementById("txtAcc_HeadDesc").value=acchead_desc;
    
    document.getElementById("txtBankAccountNo").readOnly=true;
   // document.getElementById("cmbModule").disabled=true;
    
    if (window_BankAccNumber_module && window_BankAccNumber_module.open && !window_BankAccNumber_module.closed) window_BankAccNumber_module.close();
}

function LoadBankAcc_No()
{
 document.getElementById("txtBankId").value="";
    document.getElementById("txtBankId_name").value="";
    document.getElementById("txtBranchId").value="";
    document.getElementById("txtBranchId_name").value="";
    document.getElementById("txtBankAcc_type").value="";
    document.getElementById("txtBankAcc_type_name").value="";
    document.getElementById("txtOperation_mode").value="";
    document.getElementById("txtOperation_mode_name").value="";
    document.getElementById("txtAcc_HeadCode").value="";
    document.getElementById("txtAcc_HeadDesc").value="";
    
var cmbAcc_UnitCode = document.getElementById("cmbAcc_UnitCode").value;  
    if (cmbAcc_UnitCode == "select") {
        alert("Select Accounting Unit Code in the Field");
        document.frmBank_AccountHeadCode_Module.cmbAcc_UnitCode.focus();
    }else {
            var url = "../../../../../Bank_AccountHeadCode_Module.view?Command=LoadBankAcc_No&cmbAcc_UnitCode="
                    + cmbAcc_UnitCode;
            var req = getTransport();
           // alert(url);
            req.open("POST", url, true);
            req.onreadystatechange = function() {
                handleResponse(req);
            }
            req.send(null);        
    }
}
function LoadBankAcc_No1(baseResponse)
{
document.getElementById("txtBankAccountNo").length=1;
var flag=baseResponse.getElementsByTagName("flag")[0].firstChild.nodeValue;

    if(flag=="success")
    {
    var len = baseResponse.getElementsByTagName("BANK_AC_NO").length;
		for ( var k = 0; k < len; k++) {
        var BANK_AC_NO =baseResponse.getElementsByTagName("BANK_AC_NO")[k].firstChild.nodeValue;
        var se = document.getElementById("txtBankAccountNo");
		var op = document.createElement("OPTION");
		op.value = BANK_AC_NO;
		var txt = document.createTextNode(BANK_AC_NO);
		op.appendChild(txt);
		se.appendChild(op);
        }
        }else{
        alert("Fail to Load Bank Account No");
        }
}
function LoadBankAcc_Details() {
    var cmbAcc_UnitCode = document.getElementById("cmbAcc_UnitCode").value;
    var txtBankAccountNo = document.getElementById("txtBankAccountNo").value;
    //alert(cmbAcc_UnitCode);
    if (cmbAcc_UnitCode == "select") {
        alert("Select Accounting Unit Code in the Field");
        document.frmBank_AccountHeadCode_Module.cmbAcc_UnitCode.focus();
    } else if (txtBankAccountNo == "") {
        alert("Enter Bank Account No  in the Field");
        document.frmBank_AccountHeadCode_Module.txtBankAccountNo.focus();
    } else {
            var url = "../../../../../Bank_AccountHeadCode_Module.view?Command=loadbankdeatils&cmbAcc_UnitCode="
                    + cmbAcc_UnitCode + "&txtBankAccountNo=" + txtBankAccountNo;
            var req = getTransport();
            //alert(url);
            req.open("GET", url, true);
            req.onreadystatechange = function() {
                handleResponse(req);
            }
            req.send(null);        
    }
} 

function doFunction(Command,param)
{   

    var cmbAcc_UnitCode=document.getElementById("cmbAcc_UnitCode").value;
    var txtBankId=document.getElementById("txtBankId").value;
    var txtBranchId=document.getElementById("txtBranchId").value;
    var txtSl_no=document.getElementById("txtSl_no").value;
   // alert(txtSl_no);
    var txtBankAcc_type=document.getElementById("txtBankAcc_type").value;
    var txtOperation_mode=document.getElementById("txtOperation_mode").value;
    var txtBankAccountNo=document.getElementById("txtBankAccountNo").value;
    var txtAcc_HeadCode=document.getElementById("txtAcc_HeadCode").value;
        var al= new Array() ;
        var m=0;
    var tbody = document.getElementById("grid_body");
    var rowCount=tbody.rows.length;
    if(rowCount==0)
    {
    alert("Wait..... The Grid Not Yet Loaded");
    return false;
    }

    else
    {
   // alert("else"+rowCount);
        for(var i=0;i<rowCount;i++)
        {
        var t=tbody.rows[i];
        var r=t.cells.length;
       
            for(var k=0;k<r;k++)
            {
             //al[m]=t.cells[k].lastChild.nodeValue;
            	al[m]=t.cells[k].firstChild.value;
           
    		    m++; 
            }
        }
    }
        if(Command=="Add")
        {
       
            var flag=nullcheck();
            if(flag==true)
               {
          
                var url="../../../../../Bank_AccountHeadCode_Module.view?Command=Add&cmbAcc_UnitCode="+cmbAcc_UnitCode+"&txtBankId="+txtBankId+
                        "&txtBranchId="+txtBranchId+"&txtBankAcc_type="+txtBankAcc_type+"&txtOperation_mode="+txtOperation_mode+
                        "&txtBankAccountNo="+txtBankAccountNo+"&txtAcc_HeadCode="+txtAcc_HeadCode+"&txtSl_no="+txtSl_no+"&grid="+al;
           // alert(url);
                var req=getTransport();
                req.open("GET",url,true); 
                req.onreadystatechange=function()
                {
                   handleResponse(req);
                };   
                        req.send(null);
                }
        }
        
      
}  


/////////////////////////////////////////////   handleResponse()  /////////////////////////////////////////////////////
function handleResponse(req)
{ 
    if(req.readyState==4)
    {
        if(req.status==200)
        {  
            var baseResponse=req.responseXML.getElementsByTagName("response")[0];
            var tagcommand=baseResponse.getElementsByTagName("command")[0];
            var Command=tagcommand.firstChild.nodeValue;
           
            if(Command=="loadbankdeatils")
            {
                loadbankdeatils(baseResponse);
            }
           
            else if(Command=="Add")
            {
                addRow(baseResponse);
            }
            else if(Command=="viewGrid")
            {
            viewGridChecking(baseResponse);
            }
            else if(Command=="Delete")
            {
                deleteRow(baseResponse);
            }
            
            else if(Command=="Update")
            {
                UpdateRow(baseResponse);
            }
             else if(Command=="LoadBankAcc_No")
            {
                LoadBankAcc_No1(baseResponse);
            }
            
        }
    }
}
function checkDisplay(sam) {
	
	
	if (document.getElementById("verify_select" + sam).checked == true) {
		

					document.getElementById("verify_select" + sam).checked = true;
					document.getElementById("verify_select" + sam).value="CHECKED";
					//alert("if ");
	
	} 
	else
	{
		
				document.getElementById("verify_select" + sam).checked = false;
				document.getElementById("verify_select" + sam).value="UNCHECKED";
			//	alert("Delse ");
		
		
	}
	
	//alert("document.getElementById(verify_selectsam).value "+document.getElementById("verify_select" + sam).value);
}

function selectAll(Opt)
{

  var len=  document.getElementById("grid_body").rows.length;

  if(len==1)
  {
          if (Opt =="ALL")
          {
        document.frmBank_AccountHeadCode_Module.verify_select.checked=true;
        document.getElementById("verify_select").value="CHECKED";
          }
          else if (Opt=="UNSelect" )
          {
          document.frmBank_AccountHeadCode_Module.verify_select.checked=false;
          document.getElementById("verify_select").value="UNCHECKED";
          }
  }
  else if(len>1)
  {
          for(var i=0;i<len;i++)
          {
                if ( Opt =="ALL")
                {
                    document.frmBank_AccountHeadCode_Module.verify_select[i].checked=true;
                    document.getElementById("verify_select" + i).value="CHECKED";
                }
                else if(Opt=="UNSelect")
                {
                    document.frmBank_AccountHeadCode_Module.verify_select[i].checked=false;
                    document.getElementById("verify_select" +i).value="UNCHECKED";
                }
          }
  }

}

function viewGridChecking(baseResponse)
{

var flag=baseResponse.getElementsByTagName("flag")[0].firstChild.nodeValue;
    if(flag=="success")
    {
            var service=baseResponse.getElementsByTagName("leng");
           var tbody=document.getElementById("grid_body");
                         try{tbody.innerHTML="";}
                         catch(e) {tbody.innerText="";}
            for(var i=0;i<service.length;i++)
            {
                                        var items=new Array();
		                        items[0]=service[i].getElementsByTagName("typeid")[0].firstChild.nodeValue;
                                     //   alert("etyh"+items[0]);
                                        items[1]=service[i].getElementsByTagName("modeid")[0].firstChild.nodeValue;
		                        items[2]=service[i].getElementsByTagName("moduleiddesc")[0].firstChild.nodeValue;
		                        items[3]=service[i].getElementsByTagName("cr_type")[0].firstChild.nodeValue;
                                        items[4]=service[i].getElementsByTagName("status")[0].firstChild.nodeValue;
		                        items[5]=service[i].getElementsByTagName("account_code")[0].firstChild.nodeValue;
		                        items[6]=service[i].getElementsByTagName("moduleid")[0].firstChild.nodeValue;
		                     //   alert(items[6]);
                                       var tbody=document.getElementById("grid_body");
		                        var mycurrent_row=document.createElement("TR");                
                                        mycurrent_row.id=seq;
                                                
                                        
                                        var cell0=document.createElement("TD");
                                        cell0.align='CENTER';
                                         var anc=document.createElement("input");
                                         anc.type="checkbox";
                                         anc.setAttribute('onclick', "checkDisplay("+ seq +")");
                                         anc.id="verify_select"+ seq;
                                         anc.name="verify_select"; 
                                         cell0.appendChild(anc);
                                         mycurrent_row.appendChild(cell0);
                                        
                                        
                                       var cell2=document.createElement("TD");       
                                        var typeid_one=document.createElement("input");
                                        typeid_one.type="hidden";
                                        typeid_one.name="type_id";
                                        typeid_one.value=items[0];
                                        cell2.appendChild(typeid_one);
                                        var currentText=document.createTextNode(items[0]);
                                        cell2.appendChild(currentText);
                                        mycurrent_row.appendChild(cell2);
                                       
                                        var cell3=document.createElement("TD");       
                                        var modeid_one=document.createElement("input");
                                        modeid_one.type="hidden";
                                        modeid_one.name="mode_id";
                                        modeid_one.value=items[1];
                                        cell3.appendChild(modeid_one);
                                        var currentText=document.createTextNode(items[1]);
                                        cell3.appendChild(currentText);
                                        mycurrent_row.appendChild(cell3);
                                        
                                        var cell4=document.createElement("TD");       
                                        var module_id_0ne=document.createElement("input");
                                        module_id_0ne.type="hidden";
                                        module_id_0ne.name="module_id";
                                        module_id_0ne.value=items[6];
                                        cell4.appendChild(module_id_0ne);
                                        var currentText=document.createTextNode(items[2]);//items[2]
                                        cell4.appendChild(currentText);
                                        mycurrent_row.appendChild(cell4);
                                       
                                        var cell5=document.createElement("TD");       
                                        var cr_type=document.createElement("input");
                                        cr_type.type="hidden";
                                        cr_type.name="cr_dr_type";
                                        cr_type.value=items[3];
                                        cell5.appendChild(cr_type);
                                        var currentText=document.createTextNode(items[3]);
                                        cell5.appendChild(currentText);
                                        mycurrent_row.appendChild(cell5);
                                        
                                        var cell6=document.createElement("TD");       
                                        var status_one=document.createElement("input");
                                        status_one.type="hidden";
                                        status_one.name="status_code";
                                        status_one.value=items[4];
                                        cell6.appendChild(status_one);
                                        var currentText=document.createTextNode(items[4]);
                                        cell6.appendChild(currentText);
                                        mycurrent_row.appendChild(cell6);
                                        
                                        var gcode=document.getElementById("txtAcc_HeadCode").value;
                                        var code1=gcode.substr(2,2);
                                       // alert("code1:::"+code1);
                                        
                                        var tt=items[5];
                                        var code_one=tt.substr(2,2);
                                       // alert("code_one:::"+code_one);
                                      var final1=  tt.replace(tt.substr(2,2), code1);
                                        
                                        var cell7=document.createElement("TD");       
                                        var accountCode=document.createElement("input");
                                        accountCode.type="hidden";
                                        accountCode.name="account_code";
                                        accountCode.value=final1;
                                        cell7.appendChild(accountCode);
                                        var currentText=document.createTextNode(final1);
                                        cell7.appendChild(currentText);
                                        mycurrent_row.appendChild(cell7);
		                      
		                        tbody.appendChild(mycurrent_row);
                                        seq=seq+1;
            }
    
    }
    else if(flag=="noData")
    {
         alert("No Data Found");
    }
    else
    {
         alert("Error in Loading Grid");
    }
}

function loadbankdeatils(baseResponse)
{
    
var flag=baseResponse.getElementsByTagName("flag")[0].firstChild.nodeValue;

    if(flag=="success")
    {               
     document.getElementById("accpopup").style.display="block";
        document.getElementById("txtBankId").value=baseResponse.getElementsByTagName("BANK_ID")[0].firstChild.nodeValue;
        document.getElementById("txtBankId_name").value=baseResponse.getElementsByTagName("BANK_NAME")[0].firstChild.nodeValue;
        document.getElementById("txtBranchId").value=baseResponse.getElementsByTagName("BRANCH_ID")[0].firstChild.nodeValue;
        document.getElementById("txtBranchId_name").value=baseResponse.getElementsByTagName("BRANCH_CITY")[0].firstChild.nodeValue;
        document.getElementById("txtBankAcc_type").value=baseResponse.getElementsByTagName("BANK_AC_TYPE_ID")[0].firstChild.nodeValue;
        document.getElementById("txtBankAcc_type_name").value=baseResponse.getElementsByTagName("ACCOUNT_TYPE")[0].firstChild.nodeValue;
        document.getElementById("txtOperation_mode").value=baseResponse.getElementsByTagName("AC_OPERATIONAL_MODE_ID")[0].firstChild.nodeValue;
        document.getElementById("txtOperation_mode_name").value=baseResponse.getElementsByTagName("AC_OPERATIONAL_MODE")[0].firstChild.nodeValue;
        //document.getElementById("txtBankAccountNo").value=baseResponse.getElementsByTagName("BANK_ID")[0].firstChild.nodeValue;
       
        
        var flag_mode=baseResponse.getElementsByTagName("flag_mode")[0].firstChild.nodeValue;
        if(flag_mode=="failure")
        {
            alert("There is no Account Head found for the combination of \n Bank name and Opertional mode in Bank Account Head Master");
            document.getElementById("txtBankId").value="";
            document.getElementById("txtBankId_name").value="";
            document.getElementById("txtBranchId").value="";
            document.getElementById("txtBranchId_name").value="";
            document.getElementById("txtBankAcc_type").value="";
            document.getElementById("txtBankAcc_type_name").value="";
            document.getElementById("txtOperation_mode").value="";
            document.getElementById("txtOperation_mode_name").value="";
            document.getElementById("txtAcc_HeadCode").value="";
            document.getElementById("txtAcc_HeadDesc").value="";
        }
        else
        {
              document.getElementById("txtAcc_HeadCode").value=baseResponse.getElementsByTagName("AC_HEAD_CODE")[0].firstChild.nodeValue;
              document.getElementById("txtAcc_HeadDesc").value=baseResponse.getElementsByTagName("ACCOUNT_HEAD_DESC")[0].firstChild.nodeValue;
        }
    }
    else if(flag=="failure")
    {
        alert("No such Account number");
        document.getElementById("txtBankAccountNo").value="";
        document.getElementById("txtBankId").value="";
        document.getElementById("txtBankId_name").value="";
        document.getElementById("txtBranchId").value="";
        document.getElementById("txtBranchId_name").value="";
        document.getElementById("txtBankAcc_type").value="";
        document.getElementById("txtBankAcc_type_name").value="";
        document.getElementById("txtOperation_mode").value="";
        document.getElementById("txtOperation_mode_name").value="";
        document.getElementById("txtAcc_HeadCode").value="";
        document.getElementById("txtAcc_HeadDesc").value="";
    }
   
}

function addRow(baseResponse)
{
    var flag=baseResponse.getElementsByTagName("flag")[0].firstChild.nodeValue;
    
    if(flag=="success")
    {
        alert("Record inserted into database Successfully");
        ClearAll();
        var tbody=document.getElementById("grid_body");
                         try{tbody.innerHTML="";}
                         catch(e) {tbody.innerText="";}
    }
    else
    {
        alert("These Records already inserted into database");
    }
}

function deleteRow(baseResponse)
{
    var flag=baseResponse.getElementsByTagName("flag")[0].firstChild.nodeValue;
    
    if(flag=="success")
    {
        alert("Records deleted from database");
         ClearAll();
    }
    else
    {
        alert("Record not deleted from database");
    }
}  

function UpdateRow(baseResponse)
{
    var flag=baseResponse.getElementsByTagName("flag")[0].firstChild.nodeValue;
  //  alert(flag);
    var items=new Array();
    
    if(flag=="success")
    {
        alert("Record Updated Successfully");
        ClearAll();
    }
    else
    {
        alert("Record not Updated");
    }
}
function ClearAll()
{
//    template("clr");
    //document.getElementById("cmbAcc_UnitCode").value;
	var tbody=document.getElementById("grid_body");
    try{tbody.innerHTML="";}
    catch(e) {tbody.innerText="";}
    
    document.getElementById("txtBankAccountNo").value="";
    document.getElementById("txtBankId").value="";
    document.getElementById("txtBankId_name").value="";
    document.getElementById("txtBranchId").value="";
    document.getElementById("txtBranchId_name").value="";
    document.getElementById("txtBankAcc_type").value="";
    document.getElementById("txtBankAcc_type_name").value="";
    document.getElementById("txtOperation_mode").value="";
    document.getElementById("txtOperation_mode_name").value="";
    document.getElementById("txtAcc_HeadCode").value="";
    document.getElementById("txtAcc_HeadDesc").value="";
    document.getElementById("txtSl_no").value="";
    document.getElementById("cmbModule").value="";
    document.frmBank_AccountHeadCode_Module.rad_CR_DR[0].checked=true;
    document.getElementById("txtRemarks").value="";
        
    document.getElementById("txtBankAccountNo").readOnly=false;
    document.getElementById("cmbModule").disabled=false;
        var d=document.getElementById("cmdAdd");
        d.style.display="block";
        var d1=document.getElementById("cmdUpdate");
        d1.style.display="none";
        //var d3=document.getElementById("cmdDelete");
        //d3.style.display="none";
       
}
function nullcheck()
{
    if(document.getElementById("cmbAcc_UnitCode").value.length==0)
    {
        alert("Office code not found");
        //document.getElementById("cmbAcc_UnitCode").focus();
        return false;
    }
    if(document.getElementById("txtBankAccountNo").value=="")
    {
        alert("Enter the Account number");
        document.getElementById("txtBankAccountNo").focus();
        return false;
    }
    if(document.getElementById("txtBankId").value.length==0)
    {
        alert("Bank Name not found");
       // document.getElementById("txtBankId").focus();
        return false;
    }
    if(document.getElementById("txtBranchId").value.length==0)
    {
        alert("Branch name not found");
       // document.getElementById("txtBranchId").focus();
        return false;
    }
    if(document.getElementById("txtSl_no").value.length==0)
    {
        alert("Serial No  not found");
        //document.getElementById("txtOperation_mode").focus();
        return false;
    }
    if(document.getElementById("txtBankAcc_type").value.length==0)
    {
        alert(" Account type not found");
        //document.getElementById("txtBankAcc_type").focus();
        return false;
    }
    if(document.getElementById("txtOperation_mode").value.length==0)
    {
        alert("Operational mode not found");
        //document.getElementById("txtOperation_mode").focus();
        return false;
    }
   
    return true;
}


///////////////////////////////////////  Numbers only fields
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
     
/////////////////////////////////////////////////////  Amount limitation 
function limit_amt(field,e)
{
      var unicode=e.charCode? e.charCode : e.keyCode;
      if(field.value.length<17)
      {
        if(field.value.length==14 && field.value.indexOf('.')==-1  )
        field.value=field.value+'.';
        if (unicode!=8 && unicode !=9  )
        {
            if (unicode<46 || unicode==47 || unicode>57   ) 
                return false 
        }
      }
      else 
      return false;
      
}
/*
  var x= field.value.indexOf('.');//==-1  )
        if(x!=-1)
        {
        var len=field.value.length;
        field.value=field.value.substring(0,x+2)
        }*/
///////////////////////////////////    account head limitation /////////////


/////////////////////// exit  function

function exit()
{
    self.close();
}

///////////////////////////////////////////  valid amount or not
function valid_amt(field)
{
    
    amt=field.value;
    if(amt.indexOf(".")!=amt.lastIndexOf("."))
    {
        alert("Enter a Valid Amount");
        field.value="";
        field.focus();
    }
}

function check_leng(val)
{
if(val.length>=250)
return false;
}
