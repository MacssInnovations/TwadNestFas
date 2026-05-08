setTimeout("call('loadproject')", 350);
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

function clrForm(param)
{		
		if(param=="cancel")
		{
			    if(window.confirm("Do you want to clear ALL fields ?"))
			    {
			               resetType();
			    }
		}
		//else
			  //  resetType();
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

function projectload()
{
	setTimeout("call('loadproject')", 350);
}


function call(command)
{
	
xmlhttp=getxmlhttpObject();
if(xmlhttp==null)
{
    alert("Your borwser doesnot support AJAX");
    return;
    }  

 if(command=="get")
{ 
	 var cmbOffice_code=document.getElementById("cmbOffice_code").value;
	 var schemestatus=document.getElementById("schemestatus").value;
	
     var url="../../../../../Scheme_Mapping?command=get&schemestatus="+schemestatus+"&cmbOffice_code="+cmbOffice_code+"";
	  url=url+"&sid="+Math.random();
      xmlhttp.open("GET",url,true);
      xmlhttp.onreadystatechange=stateChanged;
      xmlhttp.send(null);  
} 
 else if(command=="getschemetypeid")
 { 
	 var cmbOffice_code=document.getElementById("cmbOffice_code").value;
	 var schemestatus=document.getElementById("schemestatus").value;
	 var schno=document.getElementById("schemepms").value;
    if(schemestatus==4){
    	document.schememapping.level[0].checked=true;
    	document.schememapping.level.disabled=true;
     var url="../../../../../Scheme_Mapping?command=getschemetypeid&schno="+schno+"&cmbOffice_code="+cmbOffice_code+"&schemestatus="+schemestatus+"";
     url=url+"&sid="+Math.random();
     xmlhttp.open("GET",url,true);
     xmlhttp.onreadystatechange=stateChanged;
     xmlhttp.send(null);  
    }
    else{
    	document.schememapping.level.disabled=false;
    	call('getcomponent');
    }
 
  } 
 else if(command=="getcomponent")
 { 
	 var schno=document.getElementById("schemepms").value;
 
 /*     var url="../../../../../Scheme_Mapping?command=getcomponent&schno="+schno+"";
 	  url=url+"&sid="+Math.random();
       xmlhttp.open("GET",url,true);
       xmlhttp.onreadystatechange=stateChanged;
       xmlhttp.send(null);  */
 } 
 else if(command=="loadproject")
{ 
	
	 var cmbOffice_code=document.getElementById("cmbOffice_code").value;
	// alert(cmbOffice_code);
 	   var url="../../../../../Scheme_Mapping?command=loadproject&cmbOffice_code="+cmbOffice_code+"";
	 
 	   url=url+"&sid="+Math.random();
       xmlhttp.open("GET",url,true);
       xmlhttp.onreadystatechange=stateChanged;
       xmlhttp.send(null);  
 } 
 
 
 else if(command=="load")
 { 
 	
	 if(document.getElementById("txtEmpId").value=="")
		{
			alert('Please Enter Payee Code');
			return false;
		}
		
		var empid=document.getElementById("txtEmpId").value;
		
 	  var url="../../../../../cheque_memo?command=load&empid="+empid+"";
 	 
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
            if(command=="get")
            {
            	
                if(flag=='success')
                {
            	
                	try{
               	
              		var len=response.getElementsByTagName("schno").length;
                 	var schemepms=document.getElementById("schemepms");
                    var items_id=new Array();
              	    var items_desc=new Array();                        
                          for(var i=0;i<len;i++)
                          {
                        	items_id[i]=response.getElementsByTagName("schno")[i].firstChild.nodeValue;
                        	items_desc[i]=response.getElementsByTagName("schname")[i].firstChild.nodeValue;  
                            // alert('minor'+items_desc[i]);
                          }
                    clear_Combo(schemepms);
                    
                          //alert('here second');
                          for(var k=0;k<len;k++)
                          {   
                          	//alert(items_name[k]);
                                var option=document.createElement("OPTION");
                                option.text=items_desc[k];
                                option.value=items_id[k];
                             try
                                {
                                	 schemepms.add(option);
                                	
                                }
                                catch(errorObject)
                                {
                                	schemepms.add(option,null);
                                }
                          }
              	
                	}catch(e){alert("Error in lat"+e);}      
                
                }
                else
                    {
                	var schemepms=document.getElementById("schemepms");
                	clear_Combo(schemepms);                                   
                    }
                 }
            if(command=="getschemetypeid")
            {
            	if(flag=='success')
                {

                	try{
                		document.getElementById("schemetypedesc").value =response.getElementsByTagName("schtypedesc")[0].firstChild.nodeValue;
                		document.getElementById("schemetypeid").value=response.getElementsByTagName("schtypeid")[0].firstChild.nodeValue;  
                		
                		}catch(e){alert("Error in lat"+e);}      
                
                }
                else
                    {
                	ocument.getElementById("schemetypedesc").value ="";
            		document.getElementById("schemetypeid").value="";
                    }
            }
            if(command=="loadproject")
            {
            	if(flag=='success')
                {
            		try{
                       	
                		  var len=response.getElementsByTagName("projectid").length;
                		  var existscheme=document.getElementById("existscheme");
                		  var existcomponent=document.getElementById("existcomponent");
                		  var items_id=new Array();
                		  var items_desc=new Array(); 
                		  var items_compo=new Array(); 
                            for(var i=0;i<len;i++)
                            {
                            	items_id[i]=response.getElementsByTagName("projectid")[i].firstChild.nodeValue;
                            	items_desc[i]=response.getElementsByTagName("projectname")[i].firstChild.nodeValue;  
                            	items_compo[i]=response.getElementsByTagName("component")[i].firstChild.nodeValue;  
                            	// alert('minor'+items_desc[i]);
                            }
                       clear_Combo(existscheme);
                      
                            //alert('here second');
                            for(var k=0;k<len;k++)
                            {   
                            	//alert(items_name[k]);
                                  var option=document.createElement("OPTION");
                                  option.text=items_desc[k];
                                  option.value=items_id[k];
                               
                                   try
                                  {
                                	   existscheme.add(option);
                                  	
                                  }
                                  catch(errorObject)
                                  {
                                	  existscheme.add(option,null);
                                	  // alert('error');
                                  }
                            }
                            
                            clear_Combo(existcomponent);  
                       if(items_compo[0]!='--')
                        {
                            for(var k=0;k<len;k++)
                            {   
                            	//alert(items_name[k]);
                                  var option=document.createElement("OPTION");
                                  option.text=items_compo[k];
                                  option.value=items_compo[k];
                             
                                   try
                                  {
                                	   existcomponent.add(option);
                                  	
                                  }
                                  catch(errorObject)
                                  {
                                	  existcomponent.add(option,null);
                      
                                  }
                            }
                	
                        }
                      
                	}catch(e){alert("Error in lat"+e);}    
                	
                }
                else
                    {
                	 var existscheme=document.getElementById("existscheme");
           		  var existcomponent=document.getElementById("existcomponent");
           		 clear_Combo(existscheme);  
           		  clear_Combo(existcomponent);  
                    }
             }
      
       }
    }
}



function changelevel()
	{
	if(document.schememapping.level[0].checked==true)
	{
		document.schememapping.schemecomponent.disabled=true;
	}
	else if(document.schememapping.level[1].checked==true)
	{
		document.schememapping.schemecomponent.disabled=false;	
	}
	}


function checkNull()
{
	 if(document.getElementById("schemestatus").value=="")
	 {
		 alert('Please Select Scheme Status');
		 return false;
	 }
	 if(document.getElementById("schemepms").value=="")
	 {
		 alert('Please Select Scheme as per PMS');
		 return false;
	 }
	return false;	
	
}







