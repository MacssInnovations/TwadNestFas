
/** To handle Errors **/
onerror=handleErr;
var txt="";
function handleErr(msg,url,l)
{
	txt="There was an error on this page.\n\n";
	txt+="Error: " + msg + "\n";
	txt+="URL: " + url + "\n";
	txt+="Line: " + l + "\n\n";
	txt+="Click OK to continue.\n\n";
	alert(txt);
	return true;
}

/** To create javascript request object **/
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


//This Coding for Date Validation and Checking     
function numInt(e,t)
{
    var unicode=e.charCode? e.charCode : e.keyCode;
        //if(unicode !=8)
        
        if (unicode!=8 && unicode !=9 && unicode!=37 && unicode !=39 && unicode !=35 && unicode !=36 )
        {
             if (unicode<48||unicode>57 ) 
                return false 
        }
}



function numbersonly1(e,t)
    {
       var unicode=e.charCode? e.charCode : e.keyCode;
       if(unicode==13)
        {
          try{t.blur();}catch(e){}
          return true;
        
        }
        if (unicode!=8 && unicode !=9  )
        {
            if (unicode<48||unicode>57 ) 
                return false 
        }
     }
//This function allows users to enter Numbers only (with or without decimal places)
function numFloatInt(e,t)
{
    var unicode=e.charCode? e.charCode : e.keyCode;
        //alert(unicode);
        //if(t.value.indexOf('.') != -1)alert('YES !');
        if((unicode == 8) || (unicode == 46) || (unicode == 37) || (unicode == 39)) 
        {
        	return true;
        	/*  8  --> Backspace
 			    46 --> Delete
 			    37 --> Left arrow
 			    39 --> Right arrow
        	 */
        }
        else
        {
	        var decimalPointIndex = (t.value.indexOf('.'));
	        if (unicode == 46)
	        {            
	            if (decimalPointIndex>=0)
	            return false
	        }
	        else if (unicode<48 || unicode>57)
	        {
	            return false 
	        }
        }
}


 function Exit()
 {
    self.close();
 }
 
 
 
//******************************Validation Checking**************************//
 function nullCheck()
{
     
		var accounting_unit_id=document.frmAA52_Register.cmbAcc_UnitCode;
		if(accounting_unit_id.value=="")
		{ 
		     alert("Please Select the 'Accounting Unit'");
		     accounting_unit_id.focus();
		     return false;
		}

		var accounting_unit_office_id = document.frmAA52_Register.cmbOffice_code;
		if(accounting_unit_office_id.value=="")
		{ 
		     alert("Please Select the 'Accounting Office'");
		     accounting_unit_office_id.focus();
		     return false;
		}

	/*	var appor_date=document.frmAA52_Register.txt_date;
		if(appor_date.value=="")
		{ 
		     alert("Please fill the 'Date'");
		     appor_date.focus();
		     return false;
		}*/

		var financial_year = document.frmAA52_Register.cmbFinancialYear;
		if(financial_year.value=="")
		{ 
		     alert("Please select the 'Financial Year'");
		     financial_year.focus();
		     return false;
		}
             var asset_code = document.frmAA52_Register.cmbasset;
             if(asset_code=="")
             {
             alert("Please select the asset code");
             asset_code.focus();
             return false;
             }
             
	   
		
		var remarks = document.frmAA52_Register.txtRemarks;
		if(remarks.value=="")
		{ 
		     remarks.focus();
		     return confirm("Save without 'Remarks'?");
		}

        return true;
}
        
        

//******************************************** CallServer Coding *******************//
function callServer(command){  
           var accounting_unit_id=document.frmAA52_Register.cmbAcc_UnitCode.value;
	   var accounting_unit_office_id = document.frmAA52_Register.cmbOffice_code.value;
           var txtapport_grant=document.frmAA52_Register.txtapport_grant.value;
	   var financial_year = document.frmAA52_Register.cmbFinancialYear.value;
	   var asset_code = document.frmAA52_Register.cmbasset.value;
	   var txtOB_bal = document.frmAA52_Register.txtOB_bal.value;
	   var txtdep_debit = document.frmAA52_Register.txtdep_debit.value;
	   var txtjournal_no = document.frmAA52_Register.txtjournal_no.value;
	   var txtjournal_date = document.frmAA52_Register.txtjournal_date.value;
           var txtsurvey_no = document.frmAA52_Register.txtsurvey_no.value;
	   var txtsurvey_date = document.frmAA52_Register.txtsurvey_date.value;
           var txtauction_date = document.frmAA52_Register.txtauction_date.value;
           var txtperson_name = document.frmAA52_Register.txtperson_name.value;
	   
	   var txtauction_amt = document.frmAA52_Register.txtauction_amt.value;
	   var cb_vrno = document.frmAA52_Register.cb_vrno.value;
	   var txtcb_date = document.frmAA52_Register.txtcb_date.value;
	   var txt_profit = document.frmAA52_Register.txt_profit.value;
	   var txt_loss = document.frmAA52_Register.txt_loss.value;
	   var txtoff_debit = document.frmAA52_Register.txtoff_debit.value;
	   var txtoff_credit = document.frmAA52_Register.txtoff_credit.value;
//	   var txtjournal_vno2 = document.frmAA52_Register.txtjournal_vno2.value;
//          
//	   var txtjournal_date2 = document.frmAA52_Register.txtjournal_date2.value;
           var remarks = document.frmAA52_Register.txtRemarks.value;
	   
	   
	   
       var url="";

       if(command=="Add")
        {              
    	   			var flag=nullCheck() ;
                    if(flag==true)
                    {
                    url="../../../../../AA52_Register_OB?command=Add&unit_id="+accounting_unit_id+"&office_id="+accounting_unit_office_id+"&financial_year="+financial_year+"&asset_code="+asset_code+"&txtOB_bal="+txtOB_bal+"&txtapport_grant="+txtapport_grant+
                    "&txtdep_debit="+txtdep_debit+"&txtjournal_no="+txtjournal_no+"&txtjournal_date="+txtjournal_date+"&txtsurvey_no="+txtsurvey_no+"&txtsurvey_date="+txtsurvey_date+"&txtauction_date="+txtauction_date+"&txtperson_name="+txtperson_name+"&txtauction_amt="+txtauction_amt+"&cb_vrno="+cb_vrno+
                    "&txtcb_date="+txtcb_date+"&txt_profit="+txt_profit+"&txt_loss="+txt_loss+
                    "&txtoff_debit="+txtoff_debit+"&txtoff_credit="+txtoff_credit+
                    "&remarks="+remarks;
                    alert(url);
                    var req=getTransport();
                    req.open("POST",url,true);        
                    req.onreadystatechange=function()
                    {
                       processResponse(req);
                    }   
                    req.send(null);
                    }
         }
        else if(command=="Update")
        {
                    var flag=nullCheck() ;
                    if(flag==true)
                    {
                    url="../../../../../AA52_Register_OB?command=Update&unit_id="+accounting_unit_id+"&office_id="+accounting_unit_office_id+"&financial_year="+financial_year+"&asset_code="+asset_code+"&txtOB_bal="+txtOB_bal+"&txtapport_grant="+txtapport_grant+
                    "&txtdep_debit="+txtdep_debit+"&txtjournal_no="+txtjournal_no+"&txtjournal_date="+txtjournal_date+"&txtsurvey_no="+txtsurvey_no+"&txtsurvey_date="+txtsurvey_date+"&txtauction_date="+txtauction_date+"&txtperson_name="+txtperson_name+"&txtauction_amt="+txtauction_amt+"&cb_vrno="+cb_vrno+
                    "&txtcb_date="+txtcb_date+"&txt_profit="+txt_profit+"&txt_loss="+txt_loss+
                    "&txtoff_debit="+txtoff_debit+"&txtoff_credit="+txtoff_credit+
                    "&remarks="+remarks;
                    var req=getTransport();
                    req.open("POST",url,true);        
                    req.onreadystatechange=function()
                    {
                       processResponse(req);
                    }   
                    req.send(null);
                    }
        }
        else if(command=="Delete")
        {  
        			url="../../../../../AA52_Register_OB?command=Delete&unit_id="+accounting_unit_id+"&office_id="+accounting_unit_office_id+"&financial_year="+financial_year+"&asset_code="+asset_code;
        			var req=getTransport();
                    req.open("POST",url,true);        
                    req.onreadystatechange=function()
                    {
                       processResponse(req);
                    }   
                    req.send(null);
        }
        else if(command=="Get")
        {               
        	url="../../../../../AA52_Register_OB?command=Get&unit_id="+accounting_unit_id+"&office_id="+accounting_unit_office_id+"&financial_year="+financial_year+"&asset_code="+asset_code;
        		//alert(url);	
                                var req=getTransport();
		            req.open("POST",url,true);  
		            req.onreadystatechange=function(){
		               processResponse(req);
		            };   
		            req.send(null);
        }
}  


//********************************* CallServer Response Coding ***************************************//

function processResponse(req)
{   
  if(req.readyState==4)
  {
      if(req.status==200)
      {   

    	  var baseResponse=req.responseXML.getElementsByTagName("response")[0];
          
          var tagCommand=baseResponse.getElementsByTagName("command")[0];
          
          var command=tagCommand.firstChild.nodeValue; 
          
    	  var flag=baseResponse.getElementsByTagName("flag")[0].firstChild.nodeValue; 
    	       
              if(command=="Add")
              {
            	  if(flag=="success")
            	  {                  
            		  alert("Record Inserted into Database successfully.");
            		  clearDetails();
            	  }
            	  else
                  {
            		  alert("Failed to Insert the record.");
                  }
              }
              
              else if(command=="Delete")
              { 
            	  if(flag=="success")
            	  {
            		  alert("Record Deleted from Database Successfully.");
            		  clearDetails();
            	  }
            	  else
                  {
            		  alert("Failed to Insert the record.");
                  }
              }
              
              else if(command=="Update")
              {
            	  if(flag=="success")
            	  {
            		  alert("Record Updated into Database Successfully.");
                          clearDetails();
            	  }
            	  else
                  {
            		  alert("Failed to Update the record.");
                  }
              }
              
              else if(command=="Get")
              {
            	  if(flag=="success")
            	  {
            		 var exists = baseResponse.getElementsByTagName("exists")[0].firstChild.nodeValue;
            		  if(exists == "Yes")
            		  {
                        	  document.getElementById('CmdAdd').disabled = true;
            			  document.getElementById('CmdUpdate').disabled = false;
            			  document.getElementById('CmdDelete').disabled = false;
            			
                               document.getElementById('txtapport_grant').value = baseResponse.getElementsByTagName("txtapport_grant")[0].firstChild.nodeValue;
	                       document.getElementById('txtOB_bal').value=baseResponse.getElementsByTagName("txtOB_bal")[0].firstChild.nodeValue;
                               document.getElementById('txtdep_debit').value=baseResponse.getElementsByTagName("txtdep_debit")[0].firstChild.nodeValue
                               document.getElementById('txtjournal_no').value=baseResponse.getElementsByTagName("txtjournal_no")[0].firstChild.nodeValue
                               document.getElementById('txtjournal_date').value=baseResponse.getElementsByTagName("txtjournal_date")[0].firstChild.nodeValue
                               document.getElementById('txtsurvey_no').value=baseResponse.getElementsByTagName("txtsurvey_no")[0].firstChild.nodeValue
                               document.getElementById('txtsurvey_date').value=baseResponse.getElementsByTagName("txtsurvey_date")[0].firstChild.nodeValue;
                               document.getElementById('txtauction_date').value=baseResponse.getElementsByTagName("txtauction_date")[0].firstChild.nodeValue;
                               document.getElementById('txtauction_amt').value=baseResponse.getElementsByTagName("txtauction_amt")[0].firstChild.nodeValue;
                               document.getElementById('cb_vrno').value=baseResponse.getElementsByTagName("cb_vrno")[0].firstChild.nodeValue;
                               document.getElementById('txtcb_date').value=baseResponse.getElementsByTagName("txtcb_date")[0].firstChild.nodeValue;
                               document.getElementById('txt_profit').value=baseResponse.getElementsByTagName("txt_profit")[0].firstChild.nodeValue;
                               document.getElementById('txt_loss').value=baseResponse.getElementsByTagName("txtOB_bal")[0].firstChild.nodeValue;
                               document.getElementById('txtoff_debit').value=baseResponse.getElementsByTagName("txtoff_debit")[0].firstChild.nodeValue;
                               document.getElementById('txtoff_credit').value=baseResponse.getElementsByTagName("txtoff_credit")[0].firstChild.nodeValue;
                               document.getElementById('txtperson_name').value=baseResponse.getElementsByTagName("txtperson_name")[0].firstChild.nodeValue;
                               document.getElementById('txtRemarks').value = baseResponse.getElementsByTagName("remarks")[0].firstChild.nodeValue;
            	
            	  }
                  else
            		  {
            			  clearDetails();
            			  
            			  document.getElementById('CmdAdd').disabled = false;
            			  document.getElementById('CmdUpdate').disabled = true;
            			  document.getElementById('CmdDelete').disabled = true;
            		  }
            	  }
            	  else
            	  {
            		  alert("Error Retrieving values from Database.");
            	  }
              }
         }
        }
     }   
function clearAll()
{
	  document.getElementById('cmbAcc_UnitCode').options[0].selected = "selected";
	  document.getElementById('cmbOffice_code').options[0].selected = "selected";
	  clearDetails();
}
function clearDetails()
{
	  // document.getElementById('cmbasset').value = "";
	   document.getElementById('txtOB_bal').value="";
	   document.getElementById('txtdep_debit').value="";
	   document.getElementById('txtjournal_no').value="";
	   document.getElementById('txtjournal_date').value="";
           document.getElementById('txtsurvey_no').value="";
	   document.getElementById('txtsurvey_date').value="";
           document.getElementById('txtauction_date').value="";
           document.getElementById('txtauction_amt').value="";
	   document.getElementById('cb_vrno').value="";
	   document.getElementById('txtcb_date').value="";
	   document.getElementById('txt_profit').value="";
	   document.getElementById('txt_loss').value="";
	   document.getElementById('txtoff_debit').value="";
	   document.getElementById('txtoff_credit').value="";
	   document.getElementById('txtapport_grant').value = "";
           document.getElementById('txtperson_name').value = "";
           document.getElementById('txtRemarks').value = ""
	  document.getElementById('CmdAdd').disabled = false;
	  document.getElementById('CmdUpdate').disabled = true;
	  document.getElementById('CmdDelete').disabled = true;
}


var window_Asset_ValueAC_Details;
function ListAll()
{
	var accounting_unit_id=document.frmAA52_Register.cmbAcc_UnitCode.value;
	var accounting_unit_office_id = document.frmAA52_Register.cmbOffice_code.value;
	var financial_year = document.frmAA52_Register.cmbFinancialYear.value;
	var asset_code = document.frmAA52_Register.cmbasset.value;

    if (window_Asset_ValueAC_Details && window_Asset_ValueAC_Details.open && !window_Asset_ValueAC_Details.closed) 
    {
    	window_Asset_ValueAC_Details.resizeTo(500,500);
    	window_Asset_ValueAC_Details.moveTo(250,250); 
    	window_Asset_ValueAC_Details.focus();
    }
    else
    {
    	window_Asset_ValueAC_Details=null
    }
     var cmbAcc_UnitCode=document.getElementById("cmbAcc_UnitCode").value;
     var url = "AA52_Register_OB_ListAll.jsp?" +
     			"accounting_unit_id="+accounting_unit_id + 
     			"&accounting_unit_office_id="+accounting_unit_office_id +
     			"&financial_year="+financial_year;
     window_Asset_ValueAC_Details= window.open(url,"mywindow1","resizable=YES, scrollbars=yes"); 
     window_Asset_ValueAC_Details.moveTo(250,250);    
}

window.onunload=function()
{
	if (window_Asset_ValueAC_Details && window_Asset_ValueAC_Details.open && !window_Asset_ValueAC_Details.closed) window_Asset_ValueAC_Details.close();
}

function doParent(accounting_unit_id,accounting_unit_office_id,financial_year,asset_code)
{
    document.getElementById('cmbAcc_UnitCode').value=accounting_unit_id;
    document.getElementById('cmbOffice_code').value=accounting_unit_office_id;
    document.getElementById('cmbFinancialYear').value=financial_year;
    document.getElementById('cmbasset').value=asset_code;

    callServer('Get');
}

function toCheck1()
{
if(document.frmAA52_Register.txtperson_name.value.length>0)
   {
     
         if(!isNaN(document.frmAA52_Register.txtperson_name.value))
           {
              alert("Person name must be in Character");
              document.frmAA52_Register.txtperson_name.value="";
              document.frmAA52_Register.txtperson_name.focus();
              return false;
           }
        }
   return true;
}
function toFocus()
{
 //alert("test");
  //var FirstField=document.frmAA52_Register.txtperson_name.value;
 if((document.frmAA52_Register.txtperson_name.value=="") || (document.frmAA52_Register.txtperson_name.value<=0))
  {
     alert("Please Enter Person name First");
     document.frmAA52_Register.txtperson_name.focus();
     return false;
  }
   
}

     
 
function calins(e,t)
{
    var unicode=e.charCode? e.charCode : e.keyCode;
                
        if (unicode!=8 && unicode !=9 && unicode!=37 && unicode !=39 && unicode !=46  && unicode !=35 && unicode !=36 )
        {
            if(t.value.length==2 || t.value.length==5)
                t.value=t.value + '/';
             if (unicode<48||unicode>57 ) 
                return false 
        }
       

}