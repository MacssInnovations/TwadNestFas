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
 
 function loadGroup(value)
 {
 	var url="../../../../../../GPFReport?Command=loadGroup&sec_id="+value;
 	
 	 req.open("GET",url,true);
 	   req.onreadystatechange=processResponse;
      req.send(null);
  }
function load_Head(value){

	var sec_id=document.getElementById("txtSection").value;
	var url="../../../../../../GPFReport?Command=loadHead&sec_id="+sec_id+"&Grp_id="+value;
	req.open("GET",url,true);
	req.onreadystatechange=processResponse;
	req.send(null);
}
 
function callServer_LoadSection()
{   
	
      url="../../../../../../GPFReport?Command=load_Section";
    
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
            
              if(command=="Load_Section")
              {
                var flag=baseResponse.getElementsByTagName("flag")[0].firstChild.nodeValue;
                if(flag=="Success")
                {
              
                   var section_id=baseResponse.getElementsByTagName("section_id");
                   var section_name=baseResponse.getElementsByTagName("section_name");
       
                   var txtSection=document.getElementById("txtSection"); 
                  
                  for(var i=0;i<section_id.length;i++)
                  {
                    var sec_id=section_id.item(i).firstChild.nodeValue;
                    var sec_name=section_name.item(i).firstChild.nodeValue;
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
                  alert("Section does not exist ");
                }
              }else if(command=="loadGroup"){

                  var flag=baseResponse.getElementsByTagName("flag")[0].firstChild.nodeValue;
                  if(flag=="Success")
                  {
                
                     var GROUP_ID=baseResponse.getElementsByTagName("GROUP_ID");
                     var GROUP_NAME=baseResponse.getElementsByTagName("GROUP_NAME");
                     var txtGrp=document.getElementById("txtGrp"); 
                     
                     txtGrp.length=0;
                     if(GROUP_ID.length>0)
                     {
                     	var option=document.createElement("OPTION");
                     option.text="All";
                     option.value=0;
                     try
                     {   
                    	 txtGrp.add(option);
                     }
                     catch(errorObject)
                     {
                    	 txtGrp.add(option,null);
                     }   
                     }
                    
                    for(var i=0;i<GROUP_ID.length;i++)
                    {
                      var Grp_id=GROUP_ID.item(i).firstChild.nodeValue;
                      var Grp_name=GROUP_NAME.item(i).firstChild.nodeValue;
                      var option=document.createElement("OPTION");
                      option.text=Grp_name;
                      option.value=Grp_id;             
                          try
                          {
                        	  txtGrp.add(option);
                          }
                          catch(errorObject)
                          {
                        	  txtGrp.add(option,null);
                          }
                    }
                  }
                  else if(flag=='Failure')
                  {
                    alert("Section does not exist ");
                  }
                
              }else if(command=="loadHead"){
            	  var flag=baseResponse.getElementsByTagName("flag")[0].firstChild.nodeValue;
                  if(flag=="Success")
                  {
                
                     var account_head_code=baseResponse.getElementsByTagName("account_head_code");
                     var account_head_desc=baseResponse.getElementsByTagName("account_head_desc");
                     var txtHead=document.getElementById("txtHead"); 
                     txtHead.length=0;
                     var option=document.createElement("OPTION");
                     if(account_head_code.length>0)
                     {
                     	var option=document.createElement("OPTION");
                     option.text="All";
                     option.value=0;
                     try
                     {   
                    	 txtHead.add(option);
                     }
                     catch(errorObject)
                     {
                    	 txtHead.add(option,null);
                     }   
                     }
                    
                    for(var i=0;i<account_head_code.length;i++)
                    {
                      var head_id=account_head_code.item(i).firstChild.nodeValue;
                      var head_name=account_head_desc.item(i).firstChild.nodeValue;
                      var option=document.createElement("OPTION");
                      option.text=head_name;
                      option.value=head_id;             
                          try
                          {
                        	  txtHead.add(option);
                          }
                          catch(errorObject)
                          {
                        	  txtHead.add(option,null);
                          }
                    } 
                  }
                  else if(flag=='Failure')
                  {
                    alert("Section does not exist ");
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
	if(document.getElementById("txtsupplement_no").value=="")
	{
		 alert("Enter the Supplement No");
		return false;
	}
    if((document.getElementById("txtCB_Year").value.length!=4)|| (document.getElementById("txtCB_Year").value.length<=0) ||(document.getElementById("txtCB_Year").value==""))
    {
        alert("Enter the Cashbook Year");
        document.getElementById("txtCB_Year").focus();
        return false;
    }
    if(document.getElementById("txtSection").value=="")
    	{
    	 alert("Choose Report Type");
 		return false;
    	}
    return true;
}
function checknullNew()
{
    if((document.getElementById("txtCB_Year").value.length!=4)|| (document.getElementById("txtCB_Year").value.length<=0) ||(document.getElementById("txtCB_Year").value==""))
    {
        alert("Enter the Cashbook Year");
        document.getElementById("txtCB_Year").focus();
        return false;
    }  
    else if((document.getElementById("txtsupplement_no").value=="") && (document.miscRep.reptype[1].checked))
    {
    alert("Enter Supplement No");
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





 
 