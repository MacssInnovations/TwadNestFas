//alert("js");


function getTransport()
{
 var req;
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
function numbersonly(e)
    {
        var unicode=e.charCode? e.charCode : e.keyCode;
       
        if (unicode!=8 && unicode !=9  )
        {
            if (unicode<48 || unicode>57 ) 
                return false;
        }
     }

function Acc_HeadCodeValidation()
{
    var date1=document.getElementById("txtCrea_date").value;
    var spl=date1.split("/");
    if(spl[2]>=2011)
    {
          if(spl[0]>01 || spl[0]==01)
          {
              var Acc_HeadCode1=document.getElementById("txtaccountheadcode").value;
              var digit=parseInt(Acc_HeadCode1.substr(0, 2));  
              if(digit==82)
              {
               alert("This Account Head Code cannot be used here");
              document.getElementById("txtaccountheadcode").value="";
              document.getElementById("txtaccountheadcode").focus();
              }
          }
    }
}


function loadcolumn()
{
   //alert("enter......");
   var req=getTransport();
   var moduleid=document.getElementById("moduleid").value;
   var url="../../../../../Journal_Integration_Heads?command=loadcolumn&moduleid="+moduleid;
   //alert(url);
   req.open("POST",url,true);
   req.onreadystatechange=function()
   {
   if(req.readyState==4)
        {
          if(req.status==200)
          { 
          var baseResponse=req.responseXML.getElementsByTagName("response")[0];
          var flag=baseResponse.getElementsByTagName("flag")[0].firstChild.nodeValue;
          if (flag == "success") {
		 var len4 = baseResponse.getElementsByTagName("colName").length;
		 if (len4 > 0) {
			for ( var i = 0; i < len4; i++) {
				var colName = baseResponse.getElementsByTagName("colName")[i].firstChild.nodeValue;

				var se = document.getElementById("cmbjournalised");
				var op = document.createElement("OPTION");
				op.value = colName;
				var txt = document.createTextNode(colName);
				op.appendChild(txt);
				se.appendChild(op);
			}
		} else {
			alert(" Column Journal Does Not Exist");
		}
	} else {
		alert("Fail to Load Column Journal");
	}
          }
          }
   };
    req.send(null);
}

function loadaccountHead()
{
  var req=getTransport();
   var txtAcc_HeadCode=document.getElementById("txtaccountheadcode").value;
   var url="../../../../../Journal_Integration_Heads?command=loadaccountHead&txtAcc_HeadCode="+txtAcc_HeadCode;
   //alert(url);
   req.open("POST",url,true);
   req.onreadystatechange=function()
   {
   if(req.readyState==4)
        {
          if(req.status==200)
          { 
          //alert(req.responseText);
          var baseResponse=req.responseXML.getElementsByTagName("response")[0];
          var flag=baseResponse.getElementsByTagName("flag")[0].firstChild.nodeValue;
          if (flag == "success") {
          var accountHead=baseResponse.getElementsByTagName("AccDesc")[0].firstChild.nodeValue;
          document.getElementById("txtAcc_HeadDesc").value=accountHead;
          }
          else
          {
          alert("Not Available");
          }
        }
      }
    };
  req.send(null);
}

function doinsert()
{ 
  
   var req=getTransport();
   var moduleid=document.getElementById("moduleid").value;
   //alert(moduleid);
   var cmbjournalised=document.getElementById("cmbjournalised").value;
    //alert(cmbjournalised);
   var txtAcc_HeadCode=document.getElementById("txtaccountheadcode").value;
  
   var txtdatewef=document.getElementById("txtCrea_date").value;
    //alert(txtdatewef);
   if(document.frmjournal_Integration.check_cr_dr[0].checked==true)
   {
   var check_cr_dr="CR";
   }
   else if(document.frmjournal_Integration.check_cr_dr[1].checked==true)
   {
   var check_cr_dr="DR";
   }
 
   var typepf=document.getElementById("typepf").value;
   ///  alert(typepf);
   var txtorerno=document.getElementById("txtorerno").value;
   // alert(txtorerno);
  // var flag=nullcheck();
  // alert("flag===="+flag);
   //if(flag==true)
  // {
   var url="../../../../../Journal_Integration_Heads?command=Add&moduleid="+moduleid+
            "&cmbjournalised="+cmbjournalised+"&txtAcc_HeadCode="+txtAcc_HeadCode+
            "&txtdatewef="+txtdatewef+"&check_cr_dr="+check_cr_dr+"&typepf="+typepf+
            "&txtorerno="+txtorerno;
            
          //  }
   //alert(url);
   req.open("POST",url,true);
   req.onreadystatechange=function()
   {
   if(req.readyState==4)
        {
       if(req.status==200)
          {
          //alert(req.responseText);
           var baseResponse=req.responseXML.getElementsByTagName("response")[0];
          var flag=baseResponse.getElementsByTagName("flag")[0].firstChild.nodeValue;
          if(flag=="success")
          {
           alert("Record Inserted Into Database successfully.");
           loadData();
          // window.location.reload();
           clearAll();
         
           
          }else if (flag == "Exist") {
		   alert("The Given Data are Already Exist");
		   clearAll();
		 }
          else {
		 clearAll();
		 alert("Failed to Add values");
		 }
         }
       }
     };
   req.send(null);
}

function nullcheck()
{ 
	//alert("nullll..........");
	
 
  var moduleid=document.getElementById("moduleid").value;
  if(moduleid=="")
  {
   alert("Please select the Module Id");
   moduleid.focus();
   return false;
  }
  var cmbjournalised=document.getElementById("cmbjournalised").value;
  if(cmbjournalised=="")
  {
  alert("please select column journnalised");
  cmbjournalised.focus();
  return false;
  }
  var txtAcc_HeadCode=document.getElementById("txtaccountheadcode").value;
  if(txtAcc_HeadCode=="")
  {
  alert("Please Enter the Account Head Code");
  txtAcc_HeadCode.focus();
  return false;
  }
  var txtCrea_date=document.getElementById("txtCrea_date").value;
  if(txtCrea_date=="")
  {
  alert("Please Enter the Date");
  txtCrea_date.focus();
  return false; 
  }
  
  if(document.frmjournal_Integration.check_cr_dr[0].checked==true || document.frmjournal_Integration.check_cr_dr[1].checked==true)
  {
  
  }else{
  alert("Please select the Cr/Dr");
  check_cr_dr.focus();
  return false; 
  }
  var typepf=document.getElementById("typepf").value;
  if(typepf=="")
  {
  alert("Please select the Type");
  typepf.focus();
  return false; 
  }
  var txtorerno=document.getElementById("txtorerno").value;
  if(txtorerno=="")
  {
  alert("Please Enter the Order No.");
  txtorerno.focus();
  return false; 
  }
  alert("nullll.fgsfgsgsg.........");
  return true;

}


function clearAll()
{
	document.getElementById("butSub3").disabled=false;
	document.getElementById("butCan1").disabled=true;
	document.getElementById("butCan2").disabled=true;
	document.getElementById("moduleid").disabled=false;
document.frmjournal_Integration.moduleid.value="";
document.getElementById("cmbjournalised").disabled=false;
document.frmjournal_Integration.cmbjournalised.value="";
document.getElementById("txtaccountheadcode").disabled=false;
document.frmjournal_Integration.txtaccountheadcode.value="";
document.frmjournal_Integration.txtCrea_date.value="";
//document.frmjournal_Integration.check_cr_dr.value="";
document.frmjournal_Integration.typepf.value="";
document.frmjournal_Integration.txtorerno.value="";
document.frmjournal_Integration.txtAcc_HeadDesc.value="";

//createComboBox(" ", "cmbjournalised" );
}

function loadData()
{
//alert("load data.....");
 var req=getTransport();
 var url="../../../../../Journal_Integration_Heads?command=loadData";
 //alert(url);
 req.open("POST",url,true);
   req.onreadystatechange=function()
   {
   if(req.readyState==4)
        {
       if(req.status==200)
          {
    //    alert(req.responseText);
         var baseResponse=req.responseXML.getElementsByTagName("response")[0];
          var flag = baseResponse.getElementsByTagName("flag")[0].firstChild.nodeValue;
	     // alert(flag);
	      if (flag == "success") {
	    	  var tbody=document.getElementById("tblList");
	    		var t = 0,k = 1;
	    		for (t = tbody.rows.length - 1; t >= 0; t--) {
	    			tbody.deleteRow(0);
	    		}
	     // alert(flag);
	    var len=baseResponse.getElementsByTagName("ModuleID").length;
	       
	          for ( var k = 0; k < len; k++) 
	          {
	        	  var rowid = baseResponse.getElementsByTagName("rowid")[k].firstChild.nodeValue;
	          var ModuleID = baseResponse.getElementsByTagName("ModuleID")[k].firstChild.nodeValue;
	          		//alert(ModuleID);
			  var ColJourname = baseResponse.getElementsByTagName("ColJourname")[k].firstChild.nodeValue;
					//alert(ColJourname);
			  var AccountHeadCode = baseResponse.getElementsByTagName("AccountHeadCode")[k].firstChild.nodeValue;
					//alert(AccountHeadCode);
			  var DateWef = baseResponse.getElementsByTagName("DateWef")[k].firstChild.nodeValue;
					//alert(DateWef);
			  var CRDR = baseResponse.getElementsByTagName("CRDR")[k].firstChild.nodeValue;
					//alert(CRDR);
			  var Type = baseResponse.getElementsByTagName("Type")[k].firstChild.nodeValue;
					//alert(Type);
			  var OrderNo = baseResponse.getElementsByTagName("OrderNo")[k].firstChild.nodeValue;
					//alert(OrderNo);
					
			var tbody = document.getElementById("tblList");
			var table = document.getElementById("Existing");
			var current_row = document.createElement("TR");
			
			var cell = document.createElement("TD");
			var anc = document.createElement("A");
			var url="javascript:Editdata('"+ModuleID+"','"+ColJourname+"',"+AccountHeadCode
			        +",'"+DateWef+"','"+CRDR+"','"+Type+"',"+OrderNo+",'"+rowid+"')";
			
			anc.href=url;
			
			var edittxt= document.createTextNode("Edit");
			anc.appendChild(edittxt);
			cell.appendChild(anc);
			current_row.appendChild(cell);
			
			var cell2 = document.createElement("TD");
			var moduleid = document.createTextNode(ModuleID);
			cell2.appendChild(moduleid);
			current_row.appendChild(cell2);
			
			var cell2= document.createElement("TD");
			var journal=document.createTextNode(ColJourname);
			cell2.appendChild(journal);
			current_row.appendChild(cell2);
			
			var cell2= document.createElement("TD");
			var accunthead=document.createTextNode(AccountHeadCode);
			cell2.appendChild(accunthead);
			current_row.appendChild(cell2);
			
			var cell2= document.createElement("TD");
			var datewef=document.createTextNode(DateWef);
			cell2.appendChild(datewef);
			current_row.appendChild(cell2);
			
			var cell2=document.createElement("TD");
			var crdr=document.createTextNode(CRDR);
			cell2.appendChild(crdr);
			current_row.appendChild(cell2);
			
			var cell2=document.createElement("TD");
			var type=document.createTextNode(Type);
			cell2.appendChild(type);
			current_row.appendChild(cell2);
			
			var cell2=document.createElement("TD");
			var orderno=document.createTextNode(OrderNo);
			cell2.appendChild(orderno);
			current_row.appendChild(cell2);
			
			
			tbody.appendChild(current_row);
	         }        
	      }else {
		     alert("Fail to Load Grid");
	       }
	      }
          }
          };
     req.send(null);
}


function Editdata(ModuleID,ColJourname,AccountHeadCode,DateWef,CRDR,Type,OrderNo,row_Id)
{
	
    document.getElementById("moduleid").disabled = true;
    document.getElementById("cmbjournalised").disabled = true;
     document.getElementById("txtaccountheadcode").disabled = true;
     
    document.getElementById("moduleid").value=ModuleID;
    document.getElementById("row_hid").value=row_Id;
    //document.getElementById("cmbjournalised").value=ColJourname;
    var se = document.getElementById("cmbjournalised");
	var op = document.createElement("OPTION")
	op.value = ColJourname;
	var txt = document.createTextNode(ColJourname);
	op.appendChild(txt);
	se.appendChild(op);

	document.getElementById("cmbjournalised").value = ColJourname;		
    var dt=DateWef.split("-");
    var txt_date=dt[0]+"/"+dt[1]+"/"+dt[2];
    //alert(txt_date);
    
    document.getElementById("txtaccountheadcode").value=AccountHeadCode;
    if(txt_date==null)
    {
    document.getElementById("txtCrea_date").value="";
    }
    else{
    document.getElementById("txtCrea_date").value=txt_date;
    }
    //alert(CRDR);
    if(CRDR=="CR")
    {
    document.frmjournal_Integration.check_cr_dr[0].checked=true;
    }
    else
    {
    document.frmjournal_Integration.check_cr_dr[1].checked=true;
    }
    document.getElementById("check_cr_dr").value=CRDR;
    document.getElementById("typepf").value=Type;
    document.getElementById("txtorerno").value=OrderNo;
    document.getElementById("butCan1").disabled=false;
    document.getElementById("butCan2").disabled=false;
    document.getElementById("butSub3").disabled=true;
}

function updatedata()
{
  //alert("update.....");
 var req=getTransport();
   var moduleid=document.getElementById("moduleid").value;
   //alert(moduleid);
   var cmbjournalised=document.getElementById("cmbjournalised").value;
    //alert(cmbjournalised);
   var txtAcc_HeadCode=document.getElementById("txtaccountheadcode").value;
   //alert(txtAcc_HeadCode);
   var txtdatewef=document.getElementById("txtCrea_date").value;
   // alert(txtdatewef);
   if(document.frmjournal_Integration.check_cr_dr[0].checked==true)
   {
   var check_cr_dr="CR";
   }
   else if(document.frmjournal_Integration.check_cr_dr[1].checked==true)
   {
   var check_cr_dr="DR";
   }
   //alert(check_cr_dr);
   var typepf=document.getElementById("typepf").value;
    // alert(typepf);
   var txtorerno=document.getElementById("txtorerno").value;
    //alert(txtorerno);
  // var flag=nullcheck();
  // alert("flag===="+flag);
  // if(flag==true)
  // {
   var url="../../../../../Journal_Integration_Heads?command=updatedata&moduleid="+moduleid+
            "&cmbjournalised="+cmbjournalised+"&txtAcc_HeadCode="+txtAcc_HeadCode+
            "&txtdatewef="+txtdatewef+"&check_cr_dr="+check_cr_dr+"&typepf="+typepf+
            "&txtorerno="+txtorerno;
            
          //  }
   //alert(url);
   req.open("POST",url,true);
   req.onreadystatechange=function()
   {
   if(req.readyState==4)
        {
       if(req.status==200)
          {
          //alert(req.responseText);
          var baseResponse=req.responseXML.getElementsByTagName("response")[0];
          var flag=baseResponse.getElementsByTagName("flag")[0].firstChild.nodeValue;
          if(flag=="success")
          {
           alert("Records updated Successfully");
           loadData();
           clearAll();
           
          }else 
            { 
            alert("Unable to Update");
            }  
          
         }
       }
     };
   req.send(null);
}

function deletedata()
{

   var req=getTransport();
   var moduleid=document.getElementById("moduleid").value;
   var row_id=document.getElementById("row_hid").value;
   //alert(moduleid);
   var cmbjournalised=document.getElementById("cmbjournalised").value;
   // alert(cmbjournalised);
   var txtAcc_HeadCode=document.getElementById("txtaccountheadcode").value;
   
   if (cmbjournalised == "" || cmbjournalised==null || cmbjournalised=='null') {
	
	   cmbjournalised="valid";}
   if (txtAcc_HeadCode == ""|| txtAcc_HeadCode==null || txtAcc_HeadCode=='null') { txtAcc_HeadCode=10;}
		
	
	 var r = confirm("Are U Sure???????");
		if (r == true) {
		var url="../../../../../Journal_Integration_Heads?command=deletedata&moduleid="+moduleid+
            "&cmbjournalised="+cmbjournalised+"&txtAcc_HeadCode="+txtAcc_HeadCode+"&row_id="+row_id;
         }
       
   req.open("POST",url,true);
   req.onreadystatechange=function()
   {
   if(req.readyState==4)
        {
       if(req.status==200)
          {
          //alert(req.responseText);
          var baseResponse=req.responseXML.getElementsByTagName("response")[0];
          var flag=baseResponse.getElementsByTagName("flag")[0].firstChild.nodeValue;
          //alert(flag);
          if (flag == "success") {
          //var ApportCode = baseResponse.getElementsByTagName("id")[0].firstChild.nodeValue;
          //alert(ApportCode);
          //var tbody = document.getElementById("Existing");
          //var r = document.getElementById(ApportCode);
          //var ri = r.rowIndex;
          //alert(ri)
          //tbody.deleteRow(ri);
          alert("Records Deleted Successfully  ");
         
      loadData();
          clearAll();
          }else 
            { 
            alert("Unable to Delete");
            }         
          }
        }
      };
   req.send(null);    
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
                 //doFunction('load_Receipt_No','null');                 // return
																			// true;
              }
             else if(flag=="failure")
               {
                    dateCtrl.value="";
                    alert("Trial Balance Closed");// return false;//
                    dateCtrl.focus();
                    // document.getElementById("txtReceipt_No").value="";
               }
             else if(flag=="finyear")
               {
                          // This statement get executed when financial year ( Cash Book Control Details ) has not found for the given date 
                    dateCtrl.value="";
                    alert("Cash Book Control Not Found ");// return false;//
                    dateCtrl.focus();
                   // document.getElementById("txtReceipt_No").value="";
               }
        }
    }
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


