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

function nullcheck()
{
    if(document.getElementById("fin_yr").value==""||document.getElementById("fin_yr").value.length==0)
       {
        alert("Enter Year");
        document.getElementById("fin_yr").focus();
        return false;
       }
      else if(document.getElementById("majorType").value==""||document.getElementById("majorType").selectedIndex==0)
        {
            alert("Enter Major Type");
            document.getElementById("majorType").focus();
            return false;
        }
      else if(document.getElementById("minorType").value=="")
      {
          alert("Enter Minor Type");
          document.getElementById("minorType").focus();
          return false;
      }
   
     else if(document.getElementById("txtAcc_HeadCode").value=="")
       {
            alert("Enter Account Head Code");
            document.getElementById("txtAcc_HeadCode").focus();
            return false;
       }
     else if(document.getElementById("billsubtype").value=="")
       {  
           if(checksubcode()){
         	  alert("Enter Sub Type");
         	  document.getElementById("billsubtype").focus();
               return false; 
           }else{
        	   return true;
           }     
      }
      else
            return true;
}

function checksubcode(){
	
	
	 var majorType=document.AccHead.majorType.value;
	 var minorType=document.getElementById("minorType").value;
	 
	var url="../../../../../Bill_Account_Heads?Command=checksubcode&majorType="+majorType+"&minorType="+minorType;                
    var req=getTransport();
        req.open("GET",url,true); 
        //alert(url);
        req.onreadystatechange=function()
        {
           handleResponse(req);
        } ;  
                req.send(null);
}

function callminor()
{
        var req=getTransport();
        var majorType=document.AccHead.majorType.value;
        var url="../../../../../Bill_Account_Heads?Command=check&majorType="+majorType;                
        var req=getTransport();
            req.open("GET",url,true); 
            req.onreadystatechange=function()
            {
               handleResponse(req);
            }   
                    req.send(null);
}

function  callsub(param)
{
	    var major1=document.AccHead.majorType.value;
        var url="../../../../../Bill_Account_Heads?Command=subType&sub2="+param+"&major2="+major1;
        var req=getTransport();
        req.open("GET",url,true); 
        req.onreadystatechange=function()
        {
           handleResponse(req);
        }   
                req.send(null);     
}


function doFunction11(cmd)
{  
if(cmd=="checkCode1")
{   
	//alert(cmd);
     var txtAcc_HeadCode=document.getElementById("txtAcc_HeadCode").value;
    /* if(txtAcc_HeadCode=""){
    	 alert("Enter Account Head code");
    	 return false;
     }*/
    document.getElementById("txtAcc_HeadDesc").value="";
    if(txtAcc_HeadCode.length>=6)
    {
        var url="../../../../../Bill_Account_Heads?Command=checkCode1&txtAcc_HeadCode="+txtAcc_HeadCode;
       // alert(url);
        var req=getTransport();
        req.open("GET",url,true); 
        req.onreadystatechange=function()
        {
           handleResponse(req);
        }; 
                req.send(null);
    } else{
    	 document.getElementById("txtAcc_HeadDesc").value="";
    	 document.getElementById("txtAcc_HeadCode").value="";
    	//alert("Account Head Code should be six digit");
    	return false;
    }        
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
function calling(Command)         
 {  
	var finyr=document.getElementById("fin_yr").value;
    var majorType=document.getElementById("majorType").value;
    var minorType=document.getElementById("minorType").value;
    var txtAccHeadCode=document.getElementById("txtAcc_HeadCode").value;
    var txtRemarks=document.getElementById("txtRemarks").value;
    var anndate1=document.getElementById("ann_date1").value;
    var billsubtype=document.getElementById("billsubtype").value;
    var active;
            if(document.AccHead.radActive[0].checked)
            {
                    active = "L";
            }
            else
            {
                    active = "C";
            }
    if(Command=="Add")
        {
        // alert("add");
         var flag=nullcheck();
         if(flag==true)
           {  
                    var url="../../../../../Bill_Account_Heads?Command=Add&finyr="+finyr+
                            "&majorType="+majorType+"&minorType="+minorType+"&txtAccHeadCode="+txtAccHeadCode+"&active="+active+"&anndate1="+anndate1+"&txtRemarks="+txtRemarks+"&billsubtype="+billsubtype;
                    var req=getTransport();
                    req.open("GET",url,true); 
                    req.onreadystatechange=function()
                    {
                       handleResponse(req);
                    }   
                            req.send(null);
          }
        }
    else if(Command=="Update")
       { 
   	    alert("update");
   	 alert(finyr+" "+majorType+"  "+minorType+" "+txtAccHeadCode+" "+txtRemarks+""+anndate1+" "+billsubtype);   	    
   	    
//            var flag=nullcheck();
//            if(flag==true)
            {           
                var url="../../../../../Bill_Account_Heads?Command=Update&finyr="+finyr+
                        "&majorType="+majorType+"&minorType="+minorType+"&txtAccHeadCode="+txtAccHeadCode+"&active="+active+"&anndate1="+anndate1+"&txtRemarks="+txtRemarks+"&billsubtype="+billsubtype;
                var req=getTransport();
                req.open("GET",url,true); 
                req.onreadystatechange=function()
                {
                   handleResponse(req);
                }   
                        req.send(null);
          }
       }
    else if(Command=="Delete")
        {
        
            if(confirm("Do You Really want to Delete it?"))
            {
                
//               var flag=nullcheck();
//              if(flag==true)
               {  
                  var url="../../../../../Bill_Account_Heads?Command=Delete&finyr="+finyr+
                        "&majorType="+majorType+"&minorType="+minorType+"&txtAccHeadCode="+txtAccHeadCode+"&billsubtype="+billsubtype;
                   var req=getTransport();
                    req.open("GET",url,true); 
                    req.onreadystatechange=function()
                    {
                       handleResponse(req);
                    }   
                            req.send(null);
              }
            }
      }
}

function checklength(evt,item)
{
    var maxqty=document.AccHead.txtRemarks.value.length;
    var text =100;
    var result = true;
    if(maxqty >= text)
    {
        result = false;	
    }  
    return result;
}

function ClearAll()
{
        //alert("clear"); 
        document.getElementById("fin_yr").value="";
        document.getElementById("majorType").value="";
        document.getElementById("minorType").value="";
        document.getElementById("billsubtype").selectedIndex.text="--Select Sub Type--";
        //document.getElementById("billsubtype").selectedIndex==0;
        document.getElementById("txtAcc_HeadCode").value="";
        document.getElementById("txtAcc_HeadDesc").value="";
        document.getElementById("txtAcc_HeadCode").disabled=false;
        document.getElementById("txtRemarks").value="";
        document.getElementById("ann_date1").value="";
        
        document.getElementById("cmdAdd").disabled=false;
        document.getElementById("cmdEdit").disabled=false;
        document.getElementById("cmdDelete").disabled=false;
        document.getElementById("majorType").disabled = false;
        document.getElementById("minorType").disabled = false;
        document.getElementById("txtAcc_HeadCode").disabled = false;
        document.getElementById("billsubtype").disabled = false;
        document.getElementById("fin_yr").disabled=false;
}

function goBack(major,minor,sub,acchead,headDes,status,rem,macode1,mincode1,sucod1)
{ 
	
       //  var s=minor.split("-");
         document.AccHead.majorType.value = macode1; 
         document.AccHead.majorType.text = major; 
         
        minorType=document.getElementById("minorType");
         var option=document.createElement("OPTION");
               // option.text=s[1];
               // option.value=s[0];
         option.value=mincode1;
         option.text=minor;
                minorType.length=0;	
                try
                {
                        minorType.add(option);
                }
                catch(errorObject)
                {
                        minorType.add(option,null);
                }
                
       // var s1=sub.split("-");
        billsubtype=document.getElementById("billsubtype");
        var option=document.createElement("OPTION");
              // option.text=s1[1];
               //option.value=s1[0];
               option.value=sucod1;
               option.text=sub;
                
               billsubtype.length=0;	
               try
               {
            	   billsubtype.add(option);
               }
               catch(errorObject)
               {
            	   billsubtype.add(option,null);
               }
               
        document.getElementById("txtAcc_HeadCode").value=acchead; 
        document.getElementById("txtAcc_HeadDesc").value=headDes;
        if(status == "L")
        {
              document.AccHead.radActive[0].checked;
        }
        else
        {
        	document.AccHead.radActive[1].checked;
        }
        document.getElementById("txtRemarks").value=rem;
        document.getElementById("ann_date1").value="";
        document.AccHead.cmdAdd.disabled = true;
        document.AccHead.cmdEdit.disabled = false;
        document.AccHead.cmdDelete.disabled = false;
        document.getElementById("majorType").disabled = true;
        document.getElementById("minorType").disabled = true;
        document.getElementById("billsubtype").disabled = true;
        document.getElementById("fin_yr").disabled=true;
        document.getElementById("txtAcc_HeadCode").disabled = true;
        document.getElementById("txtAcc_HeadDesc").disabled = true;
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
            if(Command=="Add")
            {
                addRow(baseResponse);

            }
            else if(Command=="Delete")
            {
                deleteRow(baseResponse);
            }            
            else if(Command=="Updated")
            {                
                UpdateRow(baseResponse);
            }
            else if(Command=="Disp")
            {            
                DispRow(baseResponse);
            }
            else if(Command=="Display")
            {            
                DisplayRow(baseResponse);
            }
            else if(Command=="subb")
            {
                subtypechecking(baseResponse);
            }
            else if(Command=="checksubcode")
            {
            	checksubcodechecking(baseResponse);
            }
            else if(Command=="checkCode1")
            {
                loadcheckCode1(baseResponse);
            }
           }
         }
    }
function checksubcodechecking(baseResponse)
{

          
          var subtype = baseResponse.getElementsByTagName("subtype")[0].firstChild.nodeValue; 
          
         // alert("subtype=== "+subtype);
          if(subtype=="Y"){
        	  return true;
          }else{
        	 return false; 
          }
        
}
function loadcheckCode1(baseResponse)
{
    var flag=baseResponse.getElementsByTagName("flag")[0].firstChild.nodeValue;
    
    if(flag=="success")
    {
         var hid=baseResponse.getElementsByTagName("hid")[0].firstChild.nodeValue;
         document.getElementById("txtAcc_HeadCode").value=hid;
         var hdesc=baseResponse.getElementsByTagName("hdesc")[0].firstChild.nodeValue;
       
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
function subtypechecking(baseResponse)
{

          var subcmb = document.forms[0].billsubtype;
          document.forms[0].billsubtype.length=0;
          var subcode = baseResponse.getElementsByTagName("subcode"); 
          var subdesc = baseResponse.getElementsByTagName("subdesc"); 
          for(var i=0; i<subcode.length; i++)
               {
        	         var opt1 = document.createElement('option');
                     opt1.value = subcode[i].firstChild.nodeValue;
                     opt1.innerHTML = subdesc[i].firstChild.nodeValue; 
                     subcmb.appendChild(opt1);
               }  
}

function DispRow(baseResponse)
{
    var flag=baseResponse.getElementsByTagName("flag")[0].firstChild.nodeValue;
    if(flag=="success")
    {
//        minorType=document.getElementById("minorType");
//        minorType.length=0;
        mtype=baseResponse.getElementsByTagName("mindesc");  
        var len =baseResponse.getElementsByTagName("mincode").length;
	   	 if(len>0)
	   	 {
	   		var minorType = document.getElementById("minorType");
	   		minorType.length=1;
        for(var i=0;i<mtype.length;i++)
               {
                var items_id=baseResponse.getElementsByTagName("mincode")[i].firstChild.nodeValue;
                var items_name=baseResponse.getElementsByTagName("mindesc")[i].firstChild.nodeValue;				       	                                                  
                var option=document.createElement("OPTION");
                option.text=items_name;
                option.value=items_id;
                try
                {
                        minorType.add(option);
                }
                catch(errorObject)
                {
                        minorType.add(option,null);
                }}
        }
    }
    else
       {
               alert("No records");
               document.AccHead.majorType.selectedIndex="";
        }
}

function DisplayRow(baseResponse)
{
    var flag=baseResponse.getElementsByTagName("flag")[0].firstChild.nodeValue;
    if(flag=="success")
    {
        minorType=document.getElementById("minorType");
        minorType.length=0;
        mtype=baseResponse.getElementsByTagName("mindesc");    
        for(var i=0;i<mtype.length;i++)
               {
                var items_id=baseResponse.getElementsByTagName("mincode")[i].firstChild.nodeValue;
                var items_name=baseResponse.getElementsByTagName("mindesc")[i].firstChild.nodeValue;				       	                                                  
                var option=document.createElement("OPTION");
                option.text=items_name;
                option.value=items_id;
                try
                {
                        minorType.add(option);
                }
                catch(errorObject)
                {
                        minorType.add(option,null);
                }
        }
    }
    else
       {
               alert("No records");
               document.AccHead.majorType.selectedIndex="";
        }
}

function addRow(baseResponse)
{
    var flag=baseResponse.getElementsByTagName("flag")[0].firstChild.nodeValue;
    if(flag=="success")
    {
        alert("Record inserted into database");
        ClearAll();
    }
    else if(flag=="AlreadyExist")
   {
    alert("Record AlreadyExist.so,can't Inserted");
    document.getElementById("txtRemarks").value="";
    document.getElementById("ann_date1").value="";
   }
    else
    {
        alert("Record not inserted into database");
    }
}

function UpdateRow(baseResponse)
{
    var flag=baseResponse.getElementsByTagName("flag")[0].firstChild.nodeValue;
    var items=new Array();
    if(flag=="success")
    {
        alert("Record Updated");
        ClearAll();
    }
    else
    {
        alert("Record not Updated");
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

function numbersonly(e,t)
{
       var unicode=e.charCode? e.charCode : e.keyCode;
       if(unicode==13)
        {
          try{t.blur(); }catch(e){}
          return true;
        }
        if (unicode!=8 && unicode !=9  )
        {
            if (unicode<48||unicode>57 ) 
            {
                return false;
            }
        }
}   

var window_BankAccNumber;
function Lists()
{  
	   var finyr=document.getElementById("fin_yr").value;
	   var majorType=document.getElementById("majorType").value;
	   var minorType=document.getElementById("minorType").value;
		if((document.AccHead.fin_yr.value=="") || (document.AccHead.fin_yr.value.length<=0) || (document.AccHead.fin_yr.value=="0"))
	    {
	        alert("Select Financial Year");
	        document.AccHead.fin_yr.focus();
	        return false;
	    }  
      /* else if(document.getElementById("majorType").value==""||document.getElementById("majorType").selectedIndex==0)
        {
            alert("Enter Major Type");
            document.getElementById("majorType").focus();
            return false;
        }
      else if(document.getElementById("minorType").value==""||document.getElementById("minorType").selectedIndex==0)
      {
          alert("Enter Minor Type");
          document.getElementById("minorType").focus();
          return false;
      }*/
		
        window_BankAccNumber= window.open("../jsps/Bill_Acc_HeadList.jsp?finyr="+finyr+"&majorType="+majorType+"&minorType="+minorType,"mywindow1","resizable=YES, scrollbars=yes"); 
        window_BankAccNumber.moveTo(250,250);    
        window_BankAccNumber.focus();
}

function call_date(dateCtrl)                        // TB_checking
{   
     if(checkdt(dateCtrl))
          {
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
                         //callYear();
                         if(flag=="failure")
                         {
                        
                                  dateCtrl.value="";
                                  alert("Trial Balance Closed");//return false;//
                                  dateCtrl.focus();                                           
                         }
                         else if(flag=="finyear")
                         {
                        
                                  // This statement get executed when financial year ( Cash Book Control Details ) has not found for the given date
                                  dateCtrl.value="";
                                  alert("Cash Book Control Not Found ");//return false;//
                                  dateCtrl.focus();
                             
                                  //document.getElementById("txtVoucher_No").value="";    
                         }
                    }
         }
}

function call_mainJSP_script(fromcal_dateCtrl,blr_flag)   
{
        if(blr_flag==1)        
        {                             
                var cmbAcc_UnitCode=document.getElementById("cmbAcc_UnitCode").value;
                var cmbOffice_code=document.getElementById("cmbOffice_code").value;
                var TB_date=fromcal_dateCtrl.value;                
                if(fromcal_dateCtrl.value.length!=0)
                {
                        var url="../../../../../Receipt_SL.view?Command=check_TB&TB_date="+TB_date+"&cmbAcc_UnitCode="+cmbAcc_UnitCode+"&cmbOffice_code="+cmbOffice_code;                        
                        var req=getTransport();
                        req.open("GET",url,true);
                        req.onreadystatechange=function()
                        {
                                 check_TB(req,fromcal_dateCtrl);
                        }  
                        req.send(null);
                }
        }
}     
