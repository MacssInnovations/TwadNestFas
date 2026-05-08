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
 
 
 function checkType() {
	
	 if(document.control_fig.Acc_Head_Type[0].checked==true)
		 {
		 document.getElementById("txtAcc_HeadCode").value="";
		 document.getElementById("txtAcc_HeadCode").readOnly = true;
		 }
	 else
		 {
		 document.getElementById("txtAcc_HeadCode").readOnly = false;
		 }
 }
 function doFunction(Command,param)
 {  

         if(Command=="checkCode")
         {  
            // alert("hi well");
             var txtAcc_HeadCode=document.getElementById("txtAcc_HeadCode").value;
              document.getElementById("txtAcc_HeadDesc").value="";
                    
             if(txtAcc_HeadCode.length>=6)
             {
                 var url="../../../../../../control_statement?Command=checkCode&txtAcc_HeadCode="+txtAcc_HeadCode;                
               // alert(url);
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
 function handleResponse(req)
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
                 loadcheckCode(baseResponse);
             }
         }
     }
 }
 function loadcheckCode(baseResponse)
 {
     var flag=baseResponse.getElementsByTagName("flag")[0].firstChild.nodeValue;
    //alert(flag);
     
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
 function checkNull()
 {
	 var option;
	 var acHead=0;
	 var reg=document.getElementById("txtRegionId").value;
	 if(reg=="0")
		 {
		 alert("Choose Region Name");
		 return false;
		 }
	 var yearfrom=document.getElementById("txtCB_Year_From").value;
	 if(yearfrom=="")
		 {
		 alert("Enter From Year");
		 return false;
		 }
	 var yearto=document.getElementById("txtCB_Year_To").value;
	 if(yearto=="")
		 {
		 alert("Enter To Year");
		 return false;
		 }
	 var supno=document.getElementById("txtsupplement_no").value;
	 if(supno=="")
		 {
		 alert("Enter Supplement No");
		 return false;
		 }
	 if(document.control_fig.Acc_Head_Type[1].checked==true)
		 {
		 if(document.getElementById("txtAcc_HeadCode").value=="")
			 {
			 alert("Enter Account Head Code");
			 return false;
			 }
		 }
	/* if(document.control_fig.Acc_Head_Type[0].checked==true)
	 {
	 acHead=0;
	 }
	 else
		 {
		 
		 acHead=document.getElementById("txtAcc_HeadCode").value;
		 if(acHead=="")
			 {
			 alert("Enter AccountHead");
			 return false;
			 }
		 } */
	 
 }
 
 function sixdigit()
 {
  if( document.getElementById("txtAcc_HeadCode").value!=0)
     {
         if(( document.getElementById("txtAcc_HeadCode").value).length<6)
         {
         alert("Account Head Code Shouldn't be less than 6 digit number");
         document.getElementById("txtAcc_HeadCode").focus();
         return false;
         }
         else if(( document.getElementById("txtAcc_HeadCode").value).length>6)
         {
             alert("Account Head Code Shouldn't be Greater than 6 digit number");
             document.getElementById("txtAcc_HeadCode").value="";
             document.getElementById("txtAcc_HeadCode").focus();
             return false;
             }
     }
 }
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
