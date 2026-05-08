var window_ebmaster;
function ListHeads()
{ 
    if (window_ebmaster && window_ebmaster.open && !window_ebmaster.closed) 
         {
        	window_ebmaster.resizeTo(500,500);
        	window_ebmaster.moveTo(250,250); 
        	window_ebmaster.focus();
         }
         else
         {
        	 window_ebmaster=null;
         }
        
        var acc_unit=document.getElementById("cmbAcc_UnitCode").value;
    	var office_id=document.getElementById("cmbOffice_code").value;
        var acc_unitname=document.getElementById("cmbAcc_UnitCode").options[document.getElementById("cmbAcc_UnitCode").selectedIndex].text;
        var office_name=document.getElementById("cmbOffice_code").options[document.getElementById("cmbOffice_code").selectedIndex].text;
              
        window_ebmaster= window.open("BRS_status_list.jsp?acc_unit="+acc_unit+"&office_id="+office_id+"&acc_unitname="+acc_unitname+"&office_name=="+office_name+"","mywindow1","resizable=YES, scrollbars=yes"); 
        window_ebmaster.moveTo(250,250);    
 
}

function showWindow1()
{
	//alert("sss");
	document.getElementById("labelonline").style.display="block";
	document.getElementById("textonline").style.display="block";
}

function hideWindow1()
{
	document.getElementById("labelonline").style.display="none";
	document.getElementById("textonline").style.display="none";
}

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


function checkMonthlyClosure()
{
	xmlhttp=getxmlhttpObject();
	 var acc_unit=document.getElementById("cmbAcc_UnitCode").value;
 	 var office_id=document.getElementById("cmbOffice_code").value;
 	 var onlinetxtCB_Year=document.getElementById("onlinetxtCB_Year").value;
 	 var onlinetxtCB_Month=document.getElementById("onlinetxtCB_Month").value;
 	var cmbBankAccNo=document.getElementById("cmbBankAccNo").value;
 	
 	

 	 var url="../../../../../BRS_status?command=closureCheck&acc_unit="+acc_unit+"&office_id="+office_id+"&onlinetxtCB_Year="+onlinetxtCB_Year+"&onlinetxtCB_Month="+onlinetxtCB_Month+"&cmbBankAccNo="+cmbBankAccNo;
 	 	xmlhttp.open("GET",url,true);
        xmlhttp.onreadystatechange=stateChanged;
        xmlhttp.send(null);
 	
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
            
            if(command=="getMax")
            {
                
            	
            	
            	if(flag=='success')
                    {
            		var year=response.getElementsByTagName("year")[0].firstChild.nodeValue;
            		var month=response.getElementsByTagName("month")[0].firstChild.nodeValue;
            		
            		var openyear=response.getElementsByTagName("open_year")[0].firstChild.nodeValue;
            		var openmonth=response.getElementsByTagName("open_month")[0].firstChild.nodeValue;
            		if(openyear !=0 && openmonth !=0 )
            		{
            			month=parseInt(openmonth); 
            			year=parseInt(openyear);
            		}
            		else
            			{
            			if(month==12)
            			  {
            				month=1;
            				//alert("after Dec:"+month);
            				year=parseInt(year)+1;
            				//alert("after Dec:"+year);
            			  }
            			else
            			  {
            				month=parseInt(month)+1;
            				//alert("after=======>:"+month);
            			  }  
            			}
            	var monthInWords;
            	
            		if (month== 1)
                        monthInWords = "January";
                    else if (month == 2)
                        monthInWords = "February";
                    else if (month == 3)
                        monthInWords = "March";
                    else if (month == 4)
                        monthInWords = "April";
                    else if (month == 5)
                        monthInWords = "May";
                    else if (month == 6)
                        monthInWords = "June";
                    else if (month == 7)
                        monthInWords = "July";
                    else if (month == 8)
                        monthInWords = "August";
                    else if (month == 9)
                        monthInWords = "September";
                    else if (month == 10)
                        monthInWords = "October";
                    else if (month == 11)
                        monthInWords = "November";
                    else if (month == 12)
                        monthInWords = "December";     
            		
            		
            		
            		//var monthmax=response.getElementsByTagName("monthmax")[0].firstChild.nodeValue;
                      
                       
//                       if(monthmax==12)
//                       {
//                    	   monthmax=12;
//                       }
//                       else
//                       {
//                    	   
//                    	   monthmax=parseInt(monthmax)+1;
//                    	   //alert("after:"+monthmax);
//                       }
                        
//                                if (monthmax== 1)
//                                    monthInWords = "January";
//                                else if (monthmax == 2)
//                                    monthInWords = "February";
//                                else if (monthmax == 3)
//                                    monthInWords = "March";
//                                else if (monthmax == 4)
//                                    monthInWords = "April";
//                                else if (monthmax == 5)
//                                    monthInWords = "May";
//                                else if (monthmax == 6)
//                                    monthInWords = "June";
//                                else if (monthmax == 7)
//                                    monthInWords = "July";
//                                else if (monthmax == 8)
//                                    monthInWords = "August";
//                                else if (monthmax == 9)
//                                    monthInWords = "September";
//                                else if (monthmax == 10)
//                                    monthInWords = "October";
//                                else if (monthmax == 11)
//                                    monthInWords = "November";
//                                else if (monthmax == 12)
//                                    monthInWords = "December";                      
            		document.getElementById("TBtxtCB_Year").value=year;
                    var mm=document.getElementById("TBtxtCB_Month");
                        
                        var t=document.createElement("option");
                        t.value=month;
                        t.text=monthInWords;
                        mm.appendChild(t);
                        
                        
                        
                    }
            	
                else
                    {
                    
                	alert("No Data Found");
                    
                    }
            	
            }
            if(command=="closureCheck")
            {
            	if(flag=='success'){
            		var year1=response.getElementsByTagName("equalyr")[0].firstChild.nodeValue;
            		if(year1=="yes")
            		{
            			var month1=response.getElementsByTagName("equalmn")[0].firstChild.nodeValue;
            			if(month1=="yes")
            			{
            				
            			}
            			else
            			{
            				alert("BRS Online Completion Month is Wrong");
                    		document.getElementById("onlinetxtCB_Year").value="";
                    		document.getElementById("onlinetxtCB_Month").value="0";
                    		return false;	
            			}
            			
            		}
            		else
            		{
            			alert("BRS Online Completion Year is Wrong");
                		document.getElementById("onlinetxtCB_Year").value="";
                		document.getElementById("onlinetxtCB_Month").value="0";
                		return false;
            		}
            	}
            	else
            	{
            		alert("No Online BRS Transactions");
            		document.getElementById("onlinetxtCB_Year").value="";
            		document.getElementById("onlinetxtCB_Month").value="0";
            		return false;
            	}
            }
            if(command=="Add")
            {
            	
                if(flag=='success'){
                	//var data=response.getElementsByTagName("data")[0].firstChild.nodeValue;
                        var stat=response.getElementsByTagName("status")[0].firstChild.nodeValue;
                	
//                        if(data=="already")
//                        {
//                            alert("Data Already exist");
//                        }
                         if(stat=="success"){
                		alert("Record inserted into database");
                	}else if(stat=="already"){
                    	
                		
                		alert("This data already Exist");
                		
                       
                	}else if(stat=="failure")
                        {
                		alert("Record not inserted into database");
                	}
                	ClearAll();
                }else{
                	alert("Record not inserted into database");
                }
                    
                   
            } if(command=="Update")
            {
            	
                if(flag=='success')
                {
                  
                	alert("Record Updated into database");
                	
                	ClearAll();
                }
                else
                    {
                    
                    }
                    
                   
            }if(command=="Delete")
            {
            	
                if(flag=='success')
                {
                  
                	alert("Record Deleted successfully");
                	 
                	ClearAll();
                }
                else
                    {
                    
                    }
                    
                   
            }
           
       }
    }
}

function load_bank(){
	
	var url="../../../../../BRS_status_report?command=load_bank";
	
	var req=getxmlhttpObject();
	req.open("GET",url,true);
	req.onreadystatechange = function() {
		bank_val(req);
	}
req.send(null);
}
function bank_val(req) {
var i;
	if (req.readyState == 4) {
		
			if(req.status==200)
		       {
		       try {
		       baseresponse=req.responseXML.getElementsByTagName("response")[0];
			var flag = baseresponse.getElementsByTagName("flag")[0].firstChild.nodeValue;
			
				var b_name = document.getElementById("txtBankName");
				b_name.innerHTML = "";
		       var len = baseresponse.getElementsByTagName("count")[0].firstChild.nodeValue;
				for (i = 0; i < len; i++) {
					var option = document.createElement("OPTION");
					var Bank_id = baseresponse.getElementsByTagName("id")[i].firstChild.nodeValue;
					var Bank_name = baseresponse.getElementsByTagName("name")[i].firstChild.nodeValue;
					option.text = Bank_name;
					option.value = Bank_id;
					try {
						b_name.add(option);
					} catch (errorObject) {
						b_name.add(option, null);
					}
				}

			}
				
			catch (err) {
				alert("Problem in Loading Office code ");
			}
				}
		}
	
}         
            
	
	



//



function callMaxMonth()
{
                  xmlhttp=getxmlhttpObject();
                var acc_unit=document.getElementById("cmbAcc_UnitCode").value;
                var TBtxtCB_Year=document.getElementById("TBtxtCB_Year").value;
                
                var url="../../../../../BRS_status?command=getMax&acc_unit="+acc_unit;
            //alert("URL===>"+url);
                
                xmlhttp.open("GET",url,true);
                xmlhttp.onreadystatechange=stateChanged;
                xmlhttp.send(null);  
}

function call(command)
{
        xmlhttp=getxmlhttpObject();
        if(xmlhttp==null)
        {
            alert("Your borwser doesnot support AJAX");
            return;
            }   
        if(command=="Add")
        { 
        	var radiobtn;
        	if(nullcheck())
                {
        	var acc_unit=document.getElementById("cmbAcc_UnitCode").value;
        	var office_id=document.getElementById("cmbOffice_code").value;
        	var brscb_year=document.getElementById("BRStxtCB_Year").value;
            var brscb_month=document.getElementById("BRStxtCB_Month").options[document.getElementById("BRStxtCB_Month").selectedIndex].text;
        	
            var brscb_month1=document.getElementById("BRStxtCB_Month").value;
                                
            var tbcb_year=document.getElementById("TBtxtCB_Year").value;
        	var tbcb_month=document.getElementById("TBtxtCB_Month").options[document.getElementById("TBtxtCB_Month").selectedIndex].text;
        	var tbcb_month1=document.getElementById("TBtxtCB_Month").value;
        	
        	var onlinetxtCB_Year=document.getElementById("onlinetxtCB_Year").value;
        	var onlinetxtCB_Month=document.getElementById("onlinetxtCB_Month").value;
        	
        	if(document.BRS_statusform.onlineopt[0].checked==true)
        	{
        		radiobtn="yes";
        	}
        	else
        	{
        		radiobtn="no";
        	}
        	
//        	var cmbBankAccNo1=document.getElementById("cmbBankAccNo").options[document.getElementById("cmbBankAccNo").selectedIndex].text;
//        	var cmbBankAccNo2=cmbBankAccNo1.split("-");
//        	var cmbBankAccNo=cmbBankAccNo2[1]+"-"+cmbBankAccNo2[2];
        	
        	var cmbBankAccNo=document.getElementById("cmbBankAccNo").value;
//        	var cmbBankAccNo2=cmbBankAccNo1.split("-");
//        	//alert(cmbBankAccNo2[1]);        	
//        	//alert(cmbBankAccNo2[2]);
//        	var cmbBankAccNo=cmbBankAccNo2[1]+"-"+cmbBankAccNo2[2];
        	//alert(cmbBankAccNo);
        	
        	  var url="../../../../../BRS_status?command=Add&acc_unit="+acc_unit+
        	  "&office_id="+office_id+"&brscb_year="+brscb_year+
        	  "&brscb_month1="+brscb_month1+"&tbcb_year="+tbcb_year+"&tbcb_month1="+tbcb_month1+
        	  "&onlinetxtCB_Year="+onlinetxtCB_Year+"&onlinetxtCB_Month="+onlinetxtCB_Month+"&radiobtn="+radiobtn+"&cmbBankAccNo="+cmbBankAccNo;
        	  url=url+"&sid="+Math.random();
               // alert(url);
              xmlhttp.open("GET",url,true);
              xmlhttp.onreadystatechange=stateChanged;
               xmlhttp.send(null);  
        	}
        	
        }
        
        else if(command=="Update")
        { 
        	if(nullcheck()){
        	var acc_unit=document.getElementById("cmbAcc_UnitCode").value;
        	var office_id=document.getElementById("cmbOffice_code").value;
        	var brscb_year=document.getElementById("BRStxtCB_Year").value;
                var brscb_month1=document.getElementById("BRStxtCB_Month").value;
                var tbcb_year=document.getElementById("TBtxtCB_Year").value;
                var tbcb_month1=document.getElementById("TBtxtCB_Month").value;
//                var cmbBankAccNo=document.getElementById("cmbBankAccNo").options[document.getElementById("cmbBankAccNo").selectedIndex].text;
                var cmbBankAccNo1=document.getElementById("cmbBankAccNo").value;
                var cmbBankAccNo=cmbBankAccNo1.split("-");
                
        	  var url="../../../../../BRS_status?command=Update&acc_unit="+acc_unit+"&office_id="+office_id+"&brscb_year="+brscb_year+"&brscb_month1="+brscb_month1+"&tbcb_year="+tbcb_year+"&tbcb_month1="+tbcb_month1+"&cmbBankAccNo="+cmbBankAccNo[0];
        	  url=url+"&sid="+Math.random();
         //         alert(url);
              xmlhttp.open("GET",url,true);
              xmlhttp.onreadystatechange=stateChanged;
              xmlhttp.send(null);  
        	}
        	
        }
        
        
        else if(command=="Delete")
        { 
        	if(confirm("Do You Really want to Delete it?"))
            {
        	var acc_unit=document.getElementById("cmbAcc_UnitCode").value;
        	var office_id=document.getElementById("cmbOffice_code").value;
        	var tbcb_year=document.getElementById("TBtxtCB_Year").value;
                var tbcb_month1=document.getElementById("TBtxtCB_Month").value;

                //var cmbBankAccNo=document.getElementById("cmbBankAccNo").options[document.getElementById("cmbBankAccNo").selectedIndex].text;
                
                var cmbBankAccNo1=document.getElementById("cmbBankAccNo").value;
                var cmbBankAccNo=cmbBankAccNo1.split("-");
                
                
                var url="../../../../../BRS_status?command=Delete&acc_unit="+acc_unit+"&office_id="+office_id+"&tbcb_year="+tbcb_year+"&tbcb_month1="+tbcb_month1+"&cmbBankAccNo="+cmbBankAccNo[0];
        	  url=url+"&sid="+Math.random();
//                alert(url);
              xmlhttp.open("GET",url,true);
              xmlhttp.onreadystatechange=stateChanged;
              xmlhttp.send(null);  
            }
        	
        }
        
        
        
        
}

function ClearAll()
{
        //alert("inside clearall");
        document.getElementById("BRStxtCB_Year").value="";
	document.getElementById("BRStxtCB_Month").value=1;
//	document.getElementById("TBtxtCB_Year").value="";
//	document.getElementById("TBtxtCB_Month").value="";
	document.getElementById("onlinetxtCB_Year").value="";
	document.getElementById("onlinetxtCB_Month").value=0;
        //document.getElementById("TBfreezedate").value="";
	      
     
//     var TBtxtCB_Month=document.getElementById("TBtxtCB_Month"); 
//     TBtxtCB_Month.innerHTML=""; 
//	                 var option=document.createElement("OPTION");
//	                // option.text="--Select Type--";
//	                 option.value="";
//	                 try
//	                 {
//	                	 TBtxtCB_Month.add(option);
//	                 }catch(errorObject)
//	                 {
//	                	 TBtxtCB_Month.add(option,null);
//	                 }
	
	
	var d=document.getElementById("cmdAdd");
        d.style.display="block";
        var d1=document.getElementById("cmdUpdate");
        d1.style.display="none";
        var d3=document.getElementById("cmdDelete");
        d3.style.display="none";
	
}



function nullcheck()
{

	if(document.getElementById("BRStxtCB_Year").value==""||document.getElementById("BRStxtCB_Year").value==0)
	{
		alert('Enter BRS CashBook Year');
		return false;
	}
	if(document.getElementById("BRStxtCB_Month").value==""||document.getElementById("BRStxtCB_Month").value==0)
	{
		alert('Select BRS CashBook Month');
		return false;
	}
	if(document.getElementById("TBtxtCB_Year").value==""||document.getElementById("TBtxtCB_Year").value==0)
	{
		alert('Enter TB CashBook Year ');
		return false;
	}
	
	if(document.getElementById("TBtxtCB_Month").value==""||document.getElementById("TBtxtCB_Month").value.length==0)
	{
		alert('Select TB CashBook Month');
		return false;
	}
	if(document.BRS_statusform.onlineopt[0].checked=="true")
	{
		if(document.getElementById("onlinetxtCB_Year").value==""||document.getElementById("onlinetxtCB_Year").value==0)
		{
			alert('Enter Online BRS CashBook Year ');
			return false;
		}
		if(document.getElementById("onlinetxtCB_Month").value=="0"||document.onlinetxtCB_Month("TBtxtCB_Month").value.length==0)
		{
			alert('Enter Online BRS CashBook Month ');
			return false;
		}
	}
	 if(document.getElementById("cmbBankAccNo").value=="t")
     {
         alert("Choose Bank Account No::");
         return false;
     }
	return true;
}

function doParentBRSMaster(recno,acc_unitid,off_id,BRS_ym,TB_ym,TB_freezedate,TB_status,ACCOUNT_NO)
{

	//alert(BRS_ym);
	
	//    alert("inside the doParentBRSMaster");
    var d=document.getElementById("cmdUpdate");
     d.style.display="block";
     var d2=document.getElementById("cmdDelete");
     d2.style.display="block";
     var d1=document.getElementById("cmdAdd");
     d1.style.display="none";    
     
     document.getElementById("cmbAcc_UnitCode").value=acc_unitid;
    // document.getElementById("serviceno").disabled=true;
    document.getElementById("cmbOffice_code").value=off_id;
    
    
    
    var brs_ym_array=BRS_ym.split("/");
    var brs_month=brs_ym_array[0];
    var brs_year=brs_ym_array[1];
    
//    alert(brs_month);
//    alert(brs_year);
    
    document.getElementById("BRStxtCB_Year").value=brs_year;
    document.getElementById("BRStxtCB_Month").value=brs_month;

    var tb_ym_array=TB_ym.split("/");
    var tb_month=tb_ym_array[0];
    var tb_year=tb_ym_array[1];
    document.getElementById("TBtxtCB_Year").value=tb_year;
    document.getElementById("TBtxtCB_Month").value=tb_month; 	
    
    
    
    
    
    
    
//    document.getElementById("cmbBankAccNo").options[document.getElementById("cmbBankAccNo").selectedIndex].value=ACCOUNT_NO;
//    var Acc_NO=document.getElementById("cmbBankAccNo").options[document.getElementById("cmbBankAccNo").selectedIndex].value;
////    alert(Acc_NO);
////    document.getElementById("cmbBankAccNo").value=Acc_NO;
//   alert(document.getElementById("cmbBankAccNo").value);
    document.getElementById("cmbBankAccNo").options[document.getElementById("cmbBankAccNo").selectedIndex].text=ACCOUNT_NO;
    document.getElementById("cmbBankAccNo").options[document.getElementById("cmbBankAccNo").selectedIndex].value=ACCOUNT_NO;
    var acc_desc=document.getElementById("cmbBankAccNo").options[document.getElementById("cmbBankAccNo").selectedIndex].text;
    var bank_ac_no2=document.getElementById("cmbBankAccNo").options[document.getElementById("cmbBankAccNo").selectedIndex].value;
    var bank_ac_no1= bank_ac_no2.split("-");
    var bank_ac_no=bank_ac_no1[0];
    
//    alert(bank_ac_no);
//    alert(acc_desc);
    
    var cmbBankAccNo=document.getElementById("cmbBankAccNo").value;
    
    var option=document.createElement("OPTION");
    option.text=acc_desc;
    option.value=bank_ac_no;
    try
    {
  	  cmbBankAccNo.add(option);
    }
    catch(errorObject)
    {
  	  cmbBankAccNo.add(option,null);
    }
    
    
    
    
    
    //document.getElementById("cmbBankAccNo").value=ACCOUNT_NO;
  //alert(document.getElementById("cmbBankAccNo").value);
  // alert("ACCOUNT_NO" +ACCOUNT_NO);
    
    //alert(document.getElementById("cmbBankAccNo").options[document.getElementById("cmbBankAccNo").selectedIndex].text);
    
    	
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


function test_one1()
{  
	
      
            var url="../../../../../../Fund_Transfer_Reconciliation_atHo?command=loadBank";
            
       
                var req=getTransport();
                
                req.open("GET",url,true); 
                
                req.onreadystatechange=function()
                { 
                   handleResponse(req);
                }   
                        req.send(null);
                   
}
function handleResponse(req)
{ 
	    if(req.readyState==4)
	    {
	    	//alert(req.readyState); 
			       if(req.status==200)
			       { 
			    	   //alert(req.status);
				            var baseResponse=req.responseXML.getElementsByTagName("response")[0];
                                      // alert("baseResponse::::"+baseResponse);
				            var tagcommand=baseResponse.getElementsByTagName("command")[0];
				            var Command=tagcommand.firstChild.nodeValue;
				            
				            if(Command=="loadBank"){
				            	load_bankName(baseResponse);
				            }
				            
			       }
	    }
}

function load_bankName(baseResponse)
{
        var bAccno =document.getElementById("txtBankAccountNo");
        bAccno.length=0;
        var bankno=baseResponse.getElementsByTagName("id");
        var bankShow = baseResponse.getElementsByTagName("name"); 
             for(var i=0; i<bankno.length; i++)
                 {
                     var opt = document.createElement('option');
                     opt.value = bankno[i].firstChild.nodeValue;
                     opt.innerHTML = bankShow[i].firstChild.nodeValue; //instead of using textnode ,we use innerhtml
                     bAccno.appendChild(opt);
                 }
        
}
