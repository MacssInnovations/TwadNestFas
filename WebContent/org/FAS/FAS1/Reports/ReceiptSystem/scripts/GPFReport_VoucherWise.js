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
          
                   var txtSection=document.getElementById("txtAcc_HeadCode"); 
          
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
                  alert("Account Head Code not exit ");
                }
              }
          }
        }
}

 
 function GPF_doFunction(Command,param)
 {
     var txtAcc_HeadCode=document.getElementById("txtAcc_HeadCode").value;
     document.getElementById("txtAcc_HeadDesc").value="";
     if(txtAcc_HeadCode.length>=6)
     {
        var url="../../../../../../Receipt_SL.view?Command=checkCode&txtAcc_HeadCode="+txtAcc_HeadCode;     
       // alert(url);
        var req=getTransport();
        req.open("GET",url,true); 
        req.onreadystatechange=function()
        {
            GPF_handleResponse(req);
        }   
        req.send(null);
     }         
 }
 
function GPF_handleResponse(req)
{  
    if(req.readyState==4)
    { 
        if(req.status==200)
        {  
            
            var baseResponse=req.responseXML.getElementsByTagName("response")[0];
            var tagcommand=baseResponse.getElementsByTagName("command")[0];
            var Command=tagcommand.firstChild.nodeValue;
           
            if(Command=="checkCode")
            {
                        var flag=baseResponse.getElementsByTagName("flag")[0].firstChild.nodeValue;
                        
                        
                        if(flag=="success")
                        {
                            var hid=baseResponse.getElementsByTagName("hid")[0].firstChild.nodeValue;
                            document.getElementById("txtAcc_HeadCode").value=hid;
                            var hdesc=baseResponse.getElementsByTagName("hdesc")[0].firstChild.nodeValue;
                            // var BalType=baseResponse.getElementsByTagName("BalType")[0].firstChild.nodeValue;
                            var SL_YN =baseResponse.getElementsByTagName("SL_YN")[0].firstChild.nodeValue;
                            
                            var sl_man = baseResponse.getElementsByTagName("sl_man")[0].firstChild.nodeValue;
                            
                            
                            document.getElementById("txtAcc_HeadCode").value=hid;
                            document.getElementById("txtAcc_HeadDesc").value=hdesc;
                        
                        
                        }
                        else if(flag=="failure")
                        {
                            alert("Account Head Code '"+document.getElementById("txtAcc_HeadCode").value+"' doesn't Exist");
                            document.getElementById("txtAcc_HeadCode").value="";
                            document.getElementById("txtAcc_HeadCode").focus();
                        }
                        
            }
           
        }
    }
}



/////////////////////////////////////////////   AccHeadpopup  /////////////////////////////////////////////////////
var winAccHeadCode;
function GPF_AccHeadpopup()
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
    //alert(code);
    document.getElementById("txtAcc_HeadCode").value=code;
    GPF_doFunction('checkCode',true);
    return true;
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

function sixdigit()
{
    if( document.getElementById("txtAcc_HeadCode").value!=0)
    {
        if(( document.getElementById("txtAcc_HeadCode").value).length<6)
        {
            alert("Account Head Code Shouldn't be less than 6 digit number");
            document.getElementById("txtAcc_HeadCode").value="";
            document.getElementById("txtAcc_HeadDesc").value="";
            document.getElementById("txtAcc_HeadCode").focus();
            return false;
        }
    }
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

function monthPeriod()
{
	//alert(document.GPFVocherwise.monthType[0].checked);
	if(document.GPFVocherwise.monthType[0].checked==true)
		{
		 document.getElementById("reg_id").style.display="none";
		 document.getElementById("reg_date_id").style.display="none";
		
		 document.getElementById("monthdivid").style.display="block";
		 document.getElementById("monthdividtext").style.display="block";
		 
		 document.getElementById("monthGo").style.display="block";
		 document.getElementById("dateGo").style.display="none";
		}
	else
		{
		 document.getElementById("reg_id").style.display="block";
		 document.getElementById("reg_date_id").style.display="block";
		
		 document.getElementById("monthdivid").style.display="none";
		 document.getElementById("monthdividtext").style.display="none";
		 
		 document.getElementById("labeliddd").style.display="none";
		 document.getElementById("reguDis").style.display="none";
		 document.getElementById("dispSJV").style.display="none";
		 
		 document.getElementById("monthGo").style.display="none";
		 document.getElementById("dateGo").style.display="block";
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
		
		document.GPFVocherwise.reptype[0].checked=false;
		document.GPFVocherwise.reptype[1].checked=false;
	}
	else
	{
		document.getElementById("dispsupno1").style.display="none";
		document.getElementById("dispsupno2").style.display="none";
		document.GPFVocherwise.reptype[0].checked=false;
		document.GPFVocherwise.reptype[1].checked=false;
		
	}
	
	
}
 
/**
 *  Form Submit Function  
 */

function FormSubmit_new()
{
	document.getElementById("GPFVocherwise");
	
	
	
	   var GPFVocherwise=document.getElementById("GPFVocherwise");
	   GPFVocherwise.method="post";
	var txtCB_Month=document.getElementById("txtCB_Month").value;
	if(txtCB_Month==3)
		{
			 if(document.GPFVocherwise.reptype[0].checked==true)
				{
				 GPFVocherwise.action="../../../../../../GPFReport_VoucherWise.kv?command=one";
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
			// GPFVocherwise.action="../../../../../../GPFReport_VoucherWise.kv?command=morethanone_sjv";
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