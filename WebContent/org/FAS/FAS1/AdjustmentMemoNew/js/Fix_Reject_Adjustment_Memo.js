function getxmlhttpObject()
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



function clear_Combo(combo)
{
        //alert(combo.id)
        var cmbSL_Code=document.getElementById(combo.id);   
        cmbSL_Code.innerHTML="";
       var option=document.createElement("OPTION");
        option.text="--Select--";
        option.value="";
        try
        {
            cmbSL_Code.add(option);
        }catch(errorObject)
        {
            cmbSL_Code.add(option,null);
        } 
}









function call(command)
{
	
xmlhttp=getxmlhttpObject();
if(xmlhttp==null)
{
    alert("Your borwser doesnot support AJAX");
    return;
    }  

 if(command=="loadvoucher")
{ 
	
	 var cmbAcc_UnitCode=document.getElementById("cmbAcc_UnitCode").value;
		 var cmbOffice_code=document.getElementById("cmbOffice_code").value;
	var cashyear=document.getElementById("cashyear").value;
	var cashmonth=document.getElementById("cashmonth").value;	
	
	  var url="../../../../../Fix_Reject_Adjustment_Memo?command=loadvoucher&cmbAcc_UnitCode="+cmbAcc_UnitCode+"&cmbOffice_code="+cmbOffice_code+"&cashyear="+cashyear+"&cashmonth="+cashmonth+"";
	 
	  url=url+"&sid="+Math.random();
      xmlhttp.open("GET",url,true);
      xmlhttp.onreadystatechange=stateChanged;
      xmlhttp.send(null);  
    
	
} 
 else if(command=="loaddetails")
 { 
 	
 	
 	var cashyear=document.getElementById("cashyear").value;
 	var cashmonth=document.getElementById("cashmonth").value;	
	 var forunitid=document.getElementById("forunitid").value;
 	var voucherno=(document.getElementById("voucherno").value).split("-");	
 	  var url="../../../../../Fix_Reject_Adjustment_Memo?command=loaddetails&forunitid="+forunitid+"&cashyear="+
 	  cashyear+"&cashmonth="+cashmonth+"&voucherno="+voucherno[0]+"&slno="+voucherno[1];
 	 
 	  url=url+"&sid="+Math.random();
       xmlhttp.open("GET",url,true);
       xmlhttp.onreadystatechange=stateChanged;
       xmlhttp.send(null);  
     
 	
 } 
 else if(command=="loadamountreasondetails")
 { 
 	
 	
 	var cashyear=document.getElementById("cashyear").value;
 	var cashmonth=document.getElementById("cashmonth").value;	
 	var forunitid=document.getElementById("forunitid").value;	
 	
 	  var url="../../../../../Fix_Reject_Adjustment_Memo?command=loadamountreasondetails&cashyear="+cashyear+"&cashmonth="+cashmonth+"&forunitid="+forunitid+"";
 	 
 	  url=url+"&sid="+Math.random();
       xmlhttp.open("GET",url,true);
       xmlhttp.onreadystatechange=stateChanged;
       xmlhttp.send(null);  
     
 	
 } 
 
}





function stateChanged()
{
    var flag,command,response;
   
    if(xmlhttp.readyState==4)
    {
    	
       if(xmlhttp.status==200)
       {
            response=xmlhttp.responseXML.getElementsByTagName("response")[0];
            command=response.getElementsByTagName("command")[0].firstChild.nodeValue;
            flag=response.getElementsByTagName("flag")[0].firstChild.nodeValue;
            if(command=="loadvoucher")
            {
            	
            	
                if(flag=='success')
                {
                	
                	try{
                		
              		  var len=response.getElementsByTagName("unitid").length;
                 	var billno=document.getElementById("forunitid");
                 
              	 var items_id=new Array();
              	 var items_desc=new Array();                        
                          for(var i=0;i<len;i++)
                          {
                       	 items_id[i]=response.getElementsByTagName("unitid")[i].firstChild.nodeValue;
                       	items_desc[i]=response.getElementsByTagName("unitname")[i].firstChild.nodeValue;  
                      
                          }
                     clear_Combo(billno);
                    
                          
                          for(var k=0;k<len;k++)
                          {   
                          	
                                var option=document.createElement("OPTION");
                                option.text=items_desc[k];
                                option.value=items_id[k];
                             
                                 try
                                {
                                	 billno.add(option);
                                	
                                }
                                catch(errorObject)
                                {
                                	billno.add(option,null);
                                	
                                }
                          }
              	
              	}catch(e){alert("Error in lat"+e);}      
                
                }
                else
                    {
                	alert('Voucher Not Found');
                	cls();
                                     
                    }
                 }else   if(command=="loaddetails")
                 {
                 	
                     if(flag=='success')
                     {
                    	 	 
                    	 try{
                    		 document.getElementById("amount").value= response.getElementsByTagName("amount")[0].firstChild.nodeValue;
                        	 document.getElementById("reason").value= response.getElementsByTagName("reason")[0].firstChild.nodeValue;
                     	
                     	}catch(e){alert("Error in amount"+e);}      
                    	
                    
                     }
                 }else   if(command=="loadamountreasondetails")
                 {
                 	
                     if(flag=='success')
                     {
                    	  var txtUnitId=document.getElementById("voucherno");  
                    	 var items_id=new Array();
                         var items_name=new Array(); 
                    	  var vno=response.getElementsByTagName("vno");
                    	  for(var k=0;k<vno.length;k++)
                          {
                        	  items_id[k]=response.getElementsByTagName("vno")[k].firstChild.nodeValue;
                              items_name[k]=response.getElementsByTagName("slno")[k].firstChild.nodeValue;				       	                                                  
                          
                              var option=document.createElement("OPTION");
                              option.text=items_id[k]+"-"+items_name[k];
                              option.value=items_id[k]+"-"+items_name[k];
                              try
                              {
                                      txtUnitId.add(option);
                              }
                              catch(errorObject)
                              {
                                      txtUnitId.add(option,null);
                              }
                          }
                    	 
                    	
                     }
                 }
       }
    }
}




function displaydate(val)
{
	if(val=="E")
	{
		document.getElementById("mytable").style.display='block';
	}else
	{
		document.getElementById("mytable").style.display='none';
	}
	
	}


function cls()
{
	document.getElementById("mytable").style.display='none';
	var billno=document.getElementById("forunitid");
	 clear_Combo(billno);
	 	
		 billno=document.getElementById("voucherno");
		 clear_Combo(billno);	
		 document.Fix_Reject_Adjustment_Memo.extend_close[1].checked=true; 
	document.getElementById("amount").value="";
	document.getElementById("reason").value="";
	document.getElementById("txtCrea_date").value="";
	

}


function call_mainJSP_script(fromcal_dateCtrl,blr_flag)     /////////Calender control return value handling
{
//alert(fromcal_dateCtrl.value+"GG"+blr_flag+fromcal_dateCtrl);
if(blr_flag==1)         // which is used to find the receipt or voucher or payment (creation) date calling field,if so check trial balance
{
//call_clr();
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
}   
req.send(null);
}
}
}

function checknull()
{

	if(document.getElementById("voucherno").value=="")
	{
		alert('Please Select Voucher No');
		return false;
	}
	if(document.getElementById("forunitid").value=="")
	{
		alert('Please Select For UnitId');
		return false;
	}

	/*if( document.Fix_Reject_Adjustment_Memo.extend_close[0].checked==true)
	{
		if(document.getElementById("txtCrea_date").value=="")
		alert('Please Enter Date Please');
		return false;
	}*/
	return true;

}


function check_TB(req,fromcal_dateCtrl)
{}




