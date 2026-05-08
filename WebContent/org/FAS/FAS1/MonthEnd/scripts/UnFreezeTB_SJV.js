function checknull()
{
    if((document.getElementById("txtCB_Year").value.length!=4)|| (document.getElementById("txtCB_Year").value.length<=0) ||(document.getElementById("txtCB_Year").value==""))
    {
        alert("Enter the correct year");
        document.getElementById("txtCB_Year").focus();
        return false;
    }
    if(document.getElementById("txtCB_Month").value=="")
    {
        alert("Select a month");
        return false;
    }
   
}
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


//function unfreezeAct(){
//	var cashbookYear=document.getElementById('txtCB_Year').value;
//	var cashbookMonth=document.getElementById('txtCB_Month').value;
//	//document.getElementById('unitiddiv').style.display="none";
//	//document.getElementById('cmbAcc_UnitCode').disabled="true";
//	var url="../../../../../TBunfreezeRaised.htm?command=loadaccount&cashbookYear="+cashbookYear+"&cashbookMonth="+cashbookMonth;
//	var req=getTransport();
//    req.open("POST",url,true);                
//    req.onreadystatechange=function(){
//    	loadCombo(req);
//    };
//    req.send(null); 
// }
// function loadCombo(req){
//	 if(req.readyState==4){ 
//         if(req.status==200){
//        	response=req.responseXML.getElementsByTagName("response")[0]; 
//        	mainCategoryCombo();
//         }
//     }
// }
// function mainCategoryCombo(){
//	 var len = response.getElementsByTagName("ACCOUNTING_UNIT_ID").length;	
//		var select=document.getElementById('cmbAcc_UnitCode');
//		if(response.getElementsByTagName("status")[0].firstChild.nodeValue=="success"){		
//			//document.getElementById('unitiddiv').style.display="block";
//			//alert("not::::");
//			//document.getElementById('cmbAcc_UnitCode').disabled="true";
//			var listOpt=document.createElement("option");
//			select.length=0;
//			select.appendChild(listOpt);
//			listOpt.text="select";
//			listOpt.value="select";	
//			for(var i=0; i<len; i++){
//				listOpt=document.createElement("option");
//				select.appendChild(listOpt);
//				listOpt.text=response.getElementsByTagName("accounting_unit_name")[i].firstChild.nodeValue;
//				listOpt.value=response.getElementsByTagName("ACCOUNTING_UNIT_ID")[i].firstChild.nodeValue;
//			}
//			//document.getElementById('cmbAcc_UnitCode').disabled="false";
//		}else if(response.getElementsByTagName("status")[0].firstChild.nodeValue=="nodata"){
//			select.length=1;
//			select.selectedIndex=0;
//			alert("No data");
//			//document.getElementById('cmbAcc_UnitCode').disabled="true";
//		}else{
//			select.length=1;
//			select.selectedIndex=0;
//		}
// }







function loadSJVUnfreeze()
{
	  var txtCB_Year=document.getElementById("txtCB_Year").value;
      var txtCB_Month=3;
      var supno=document.getElementById("txtsupplement_no").value;
    var url="../../../../../TDA_TCA_List?Command=unfreeze_sjv&txtCB_Year="+txtCB_Year+"&txtCB_Month="+txtCB_Month+"&supno="+supno;			                
    var req=getTransport();
    req.open("GET",url,true); 
    req.onreadystatechange=function()
    {
       handleResponse_sjv_one(req);
    }   
    req.send(null);
}

function handleResponse_sjv_one(req)
{ 
	    if(req.readyState==4)
	    {
		        if(req.status==200)
		        {  
			            var baseResponse=req.responseXML.getElementsByTagName("response")[0];
			            var tagcommand=baseResponse.getElementsByTagName("command")[0];
			            var Command=tagcommand.firstChild.nodeValue;
			             
			            if(Command=="unfreeze_sjv")
			            {
			            	unfreeze_sjv(baseResponse);
			            }
			            
		        }
	    }
}

function unfreeze_sjv(baseResponse)
{
    
                var flag=baseResponse.getElementsByTagName("flag")[0].firstChild.nodeValue;
                if(flag=="failure")
                {
                    
                    alert("No Record exists");
                   document.getElementById("cmbAcc_UnitCode").value="";
                }
                else
                {   
                	//document.getElementById("cmbAcc_UnitCode").value="";
                	
                	//document.getElementById("cmbAcc_UnitCode").value="";
                	
                	
                	
                	
                	
                	var cmbAcc_UnitCode = document.forms[0].cmbAcc_UnitCode;
                	
                	
                	var listOpt=document.createElement("option");
                	cmbAcc_UnitCode.length=0;
                	cmbAcc_UnitCode.appendChild(listOpt);
        			listOpt.text="select";
        			listOpt.value="select";
                	
                	
                	
                	//var cmbAcc_UnitCode = document.getElementById("cmbAcc_UnitCode");
                     var unitid = baseResponse.getElementsByTagName("unitid"); 
                     var unitname = baseResponse.getElementsByTagName("unitname");   
                     for(var i=0; i<unitid.length; i++)
                         {
                             var opt = document.createElement('option');
                             opt.value = unitid[i].firstChild.nodeValue;
                             opt.text = unitname[i].firstChild.nodeValue+"("+unitid[i].firstChild.nodeValue+")"; //instead of using textnode ,we use innerhtml
                             cmbAcc_UnitCode.appendChild(opt);
                         }
                    
                }
                
}

//function clear()
//{
//	document.getElementById("cmbAcc_UnitCode").options.length = 0;
//	
//}


function numbersonly(e)
{
    var unicode=e.charCode? e.charCode : e.keyCode;
   
    if (unicode!=8 && unicode !=9  )
    {
        if (unicode<48||unicode>57 ) 
            return false 
    }
 }
 
 
 
 
 
  /**
  *   Confirmation before Submiting Form 
  */
    

 function confirmation()
 {
 
  /** Call Cehcknull Function */
  checknull();
 // alert("confirmation");
  if(confirm("Are you sure do you want to UnFreeze Supplement Trial Balance ?"))
  {
 
     var conf=document.getElementById("frmTrialBalance");
     //alert("conf"+conf);
     conf.action="../../../../../UnFreezeTB_SJV.kv";
     conf.submit();
     return true;
  
  } 
  else 
  {
      return false;
  }  
 
 }
 