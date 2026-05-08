/**
 *  Browser Indentification 
 */

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
 

function callServer_LoadSection()
{     
      url="../../../../../../GPFReport_VoucherWise.kv";
      req.open("GET",url,true);        
      req.onreadystatechange=processResponse;
      req.send(null);               
}
function monthPeriod1(id)
{
	//alert("id>>>"+id);
	if(id=='month')
		{
		//alert("inside Month");
		document.getElementById("regyr").style.display="none";
		 document.getElementById("regdate").style.display="none";
		
		 document.getElementById("monthdivid").style.display="block";
		 document.getElementById("monthdividtext").style.display="block";
		
		 document.getElementById("monthGo").style.display="block";
		 document.getElementById("dateGo").style.display="none";
		}
	else if(id=='year')
		{
		//alert("inside Year");
		document.getElementById("regyr").style.display="block";
		 document.getElementById("regdate").style.display="block";
		
		 document.getElementById("monthdivid").style.display="none";
		 document.getElementById("monthdividtext").style.display="none";
		 
		 document.getElementById("monthGo").style.display="none";
		 document.getElementById("dateGo").style.display="block";
		}
}


function monthPeriod()
{
	//alert(document.GPFVocherwise.monthType[0].checked);
	if(document.GPFVocherwise.monthType[0].checked==true)
		{
		 document.getElementById("regyr").style.display="none";
		 document.getElementById("regdate").style.display="none";
		
		 document.getElementById("monthdivid").style.display="block";
		 document.getElementById("monthdividtext").style.display="block";
		 
		 document.getElementById("dispsupno1").style.display="none";
	         document.getElementById("dispsupno2").style.display="none";
		 
		 document.getElementById("monthGo").style.display="block";
		 document.getElementById("dateGo").style.display="none";
		}
	else
		{
		 document.getElementById("regyr").style.display="block";
		 document.getElementById("regdate").style.display="block";
		
		 document.getElementById("monthdivid").style.display="none";
		 document.getElementById("monthdividtext").style.display="none";
		 
		 document.getElementById("labeliddd").style.display="none";
		 document.getElementById("reguDis").style.display="none";
		 document.getElementById("dispSJV").style.display="none";
		 
		 document.getElementById("dispsupno1").style.display="none";
	         document.getElementById("dispsupno2").style.display="none";
		 
		 document.getElementById("monthGo").style.display="none";
		 document.getElementById("dateGo").style.display="block";
		}
}

function processResponse()
{   
      if(req.readyState==4)
        {
          if(req.status==200)
          {    
              var baseResponse=req.responseXML.getElementsByTagName("response")[0];
              var tagCommand=baseResponse.getElementsByTagName("command")[0];
              var command=tagCommand.firstChild.nodeValue; 
            
              if(command=="Load_Section_Heads")
              {
                var flag=baseResponse.getElementsByTagName("flag")[0].firstChild.nodeValue;
                if(flag=="Success")
                {
              
                   var section_id=baseResponse.getElementsByTagName("section_id");
                   var section_name=baseResponse.getElementsByTagName("section_name");
          
                   var txtSection=document.getElementById("txtSectionHeads"); 
          
                  for(var i=0;i<section_id.length;i++)
                  {
                    var sec_id=section_id.item(i).firstChild.nodeValue;
                    var sec_name=sec_id +' - ' + section_name.item(i).firstChild.nodeValue;
                    var option=document.createElement("OPTION");
                    option.text=sec_name;
                    option.value=sec_id;             
                        try
                        {
                            txtSection.add(option);
                        }
                        catch(errorObject)
                        {
                            txtSection.add(option,null);
                        }
                  }
                }
                else if(flag=='Failure')
                {
                  alert("Account Head Code not exist ");
                }
              }
          }
        }
}




/**
 *  Null Check Function 
 */

function checknull()
{
    if((document.getElementById("txtCB_Year").value.length!=4)|| (document.getElementById("txtCB_Year").value.length<=0) ||(document.getElementById("txtCB_Year").value==""))
    {
        alert("Enter the Cashbook Year");
        document.getElementById("txtCB_Year").focus();
        return false;
    }   
    return true;
}



/**
 *  Number Format Checking 
 */

function numbersonly(e)
{
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

function hideType()
{
	//alert("hideType");
	document.getElementById("labeliddd").style.display="none";
	document.getElementById("reguDis").style.display="none";
	document.getElementById("dispSJV").style.display="none";
}
 
function enterSup()
{
	
	var todate=document.getElementById("txttodate").value;
	var spll=todate.split("/");
	if(spll[1]==03)
	{
		document.getElementById("dispsupno1").style.display="block";
		document.getElementById("dispsupno2").style.display="block";
	}
	else
	{
		document.getElementById("dispsupno1").style.display="none";
		document.getElementById("dispsupno2").style.display="none";
	}
	
	
}

//newly added
function FormSubmit_receipt()
{
	var GPFVocherwise_receipt=document.getElementById("GPFVocherwise_receipt");
	GPFVocherwise_receipt.method="post";
	if(document.GPFVocherwise_receipt.monthType[0].checked==true)
	{
		GPFVocherwise_receipt.action="../../../../../../GPFReport_VoucherWise.kv?command=one_receipt";
	// GPFVocherwise.submit();
	}
	if(document.GPFVocherwise_receipt.monthType[1].checked==true)
	{
		GPFVocherwise_receipt.action="../../../../../../GPFReport_VoucherWise.kv?command=morethanone_receipt"; 
	}
		
	GPFVocherwise_receipt.submit();
	
}
function FormSubmit_receipt1(Condition)
{
	//alert("FormSubmit_receipt1");
	var GPFVocherwise_receipt=document.getElementById("GPFVocherwise_receipt");
	GPFVocherwise_receipt.method="post";
	//alert("Condition>>>"+Condition)
	if (Condition=="morethanone") 
	   {
		GPFVocherwise_receipt.action="../../../../../../GPFReport_VoucherWise.kv?command=morethanone_receipt"; 
	   }
	GPFVocherwise_receipt.submit();
}

function FormSubmit_new()
{
	//alert("FormSubmit_new");
	document.getElementById("GPFVocherwise");
	
	
	
	   var GPFVocherwise=document.getElementById("GPFVocherwise");
	   GPFVocherwise.method="post";
	var txtCB_Month=document.getElementById("txtCB_Month").value;
	if(txtCB_Month==3)
		{
			 if(document.GPFVocherwise.reptype[0].checked==true)
				{
				 GPFVocherwise.action="../../../../../../GPFReport_VoucherWise.kv?command=one";
				// GPFVocherwise.submit();
			//	 alert("llll");
				}
			 else  if(document.GPFVocherwise.reptype[1].checked==true)
				{
				 var txtsupplement_no=document.getElementById("supno").value;
					if(txtsupplement_no=="")
					{
						alert("Enter Supplement No");
						return false;
					}
					 GPFVocherwise.action="../../../../../../GPFReport_VoucherWise.kv?command=one_sjv";
				// GPFVocherwise.submit();
				}
			 else
				 {
				 alert("Please choose Report type");
				 return false;
				 }
		}
	else
		{
		 GPFVocherwise.action="../../../../../../GPFReport_VoucherWise.kv?command=one";
		// GPFVocherwise.submit();
		}
	 GPFVocherwise.submit();

}

function FormSubmit(Condition)
{
	document.getElementById("GPFVocherwise");
	
   var GPFVocherwise=document.getElementById("GPFVocherwise");
   GPFVocherwise.method="post";
 
	  
		var txtsupplement_no=document.getElementById("supno").value;
		var todate=document.getElementById("txttodate").value;
		var spll=todate.split("/");
		if(spll[1]==03)
		{
			var txtsupplement_no=document.getElementById("supno").value;
			if(txtsupplement_no=="")
			{
				alert("Enter Supplement No");
				return false;
			}
			if (Condition=="morethanone") 
			   {
				//alert("hhh");
			      GPFVocherwise.action="../../../../../../GPFReport_VoucherWiseCompSec?command=morethanone_sup"; 
			   }
		}
		else
			{
			if (Condition=="morethanone") 
			   {
			      GPFVocherwise.action="../../../../../../GPFReport_VoucherWiseCompSec?command=morethanone"; 
			   } 
			}
		
	
   GPFVocherwise.submit();
   
}

var val;
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
var winAccHeadCode;
function AccHeadpopup()
{
    if (winAccHeadCode && winAccHeadCode.open && !winAccHeadCode.closed) 
    {
       winAccHeadCode.resizeTo(500,500);
       winAccHeadCode.moveTo(250,250); 
       winAccHeadCode.focus();
    }
    else
    {
        winAccHeadCode=null
    }
        
    winAccHeadCode= window.open("../../../../../../org/FAS/FAS1/AccountHeadDirectory/jsps/Acc_Head_Dir_List_InUse.jsp","AccountHeadSearch","status=1,height=500,width=500,resizable=YES, scrollbars=yes"); 
    winAccHeadCode.moveTo(250,250);  
    winAccHeadCode.focus();
    
}
function doParentAccHead(code)
{
   document.getElementById("txtAcc_HeadCode").value=code;
   doFunction('checkCode',true);
   return true;
}
function doFunction(Command,param)
{   
//alert(Command)
 var txtAcc_HeadCode=document.getElementById("txtAcc_HeadCode").value;
     if(Command=="checkCode")
        {  
           
           // var cmbAcc_UnitCode=document.getElementById("cmbAcc_UnitCode").value;
            if(txtAcc_HeadCode.length>=6)
            {
                var url="../../../../../../Receipt_SL.view?Command=checkCode&txtAcc_HeadCode="+txtAcc_HeadCode;
                //alert(url);
                var req=getTransport();
                req.open("GET",url,true); 
                req.onreadystatechange=function()
                {
                    handleResponse(req);
                }   
                        req.send(null);
            }         
        }
        else if(Command=="accdesc")
        {
        
          var url="../../../../../../GPFReport_VoucherWiseCompSec?Command=checkCode&txtAcc_HeadCode="+txtAcc_HeadCode;
                //alert(url);
                var req=getTransport();
                req.open("GET",url,true); 
                req.onreadystatechange=function()
                {
                    handleResponse(req);
                }   
                        req.send(null);
        
        
        
        }
}
function handleResponse(req)
{  
    if(req.readyState==4)
    { 
        if(req.status==200)
        {  
            var baseResponse=req.responseXML.getElementsByTagName("response")[0];
            //alert(baseResponse);
            var tagcommand=baseResponse.getElementsByTagName("command")[0];
            var Command=tagcommand.firstChild.nodeValue;
            if(Command=="checkCode")
            {
                loadcheckCode(baseResponse);
            }
            else if(Command=="Load_Head_dec")
            {
            loadAcc_des(baseResponse);
            
            }
        }
    }
}
function loadcheckCode(baseResponse)
{
        var flag=baseResponse.getElementsByTagName("flag")[0].firstChild.nodeValue;
        if(flag=="success")
        {
             //alert(flag);
             var hid=baseResponse.getElementsByTagName("hid")[0].firstChild.nodeValue;
             document.getElementById("txtAcc_HeadCode").value=hid;
             var hdesc=baseResponse.getElementsByTagName("hdesc")[0].firstChild.nodeValue;
             document.getElementById("txtAcc_HeadDesc").value=hdesc;
        }
          else if(flag=="failure")
         {
             alert("Account Head Code '"+document.getElementById("txtAcc_HeadCode").value+"' doesn't Exist");
             document.getElementById("txtAcc_HeadCode").value="";
             document.getElementById("txtAcc_HeadCode").focus();
         }
}

function loadAcc_des(baseResponse)
{

var flag=baseResponse.getElementsByTagName("flag")[0].firstChild.nodeValue;
        if(flag=="success")
        {
             //alert(flag);
             var hid=baseResponse.getElementsByTagName("code")[0].firstChild.nodeValue;
             document.getElementById("txtAcc_HeadCode").value=hid;
             var hdesc=baseResponse.getElementsByTagName("des")[0].firstChild.nodeValue;
             document.getElementById("txtAcc_HeadDesc").value=hdesc;
        }
          else if(flag=="failure")
         {
             alert("Account Head Code '"+document.getElementById("txtAcc_HeadCode").value+"' doesn't Exist");
             document.getElementById("txtAcc_HeadCode").value="";
             document.getElementById("txtAcc_HeadCode").focus();
         }


}
function sixdigit(val)
{

 if(val.length!=0)
    {
        if(val.length<6)
        {
        alert("Account Head Code shouldn't be less than 6 digit number");
        return false;
        }
    }
}
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