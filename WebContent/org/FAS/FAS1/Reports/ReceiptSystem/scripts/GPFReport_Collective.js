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
      url="../../../../../../GPFReport_Collective";
      req.open("GET",url,true);        
      req.onreadystatechange=processResponse;
      req.send(null);               
}


function blockHead()
{
	if(document.miscRep.optionId[1].checked==true)
	{
		document.getElementById("head_div1").style.display="block";
		document.getElementById("head_div2").style.display="block";
	}
	else
	{
		document.getElementById("head_div1").style.display="none";
		document.getElementById("head_div2").style.display="none";
		
		
	
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
                  alert("Section does not exit ");
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
    if((document.miscRep.optionId[0].checked==false) && (document.miscRep.optionId[1].checked==false)&&(document.miscRep.optionId[2].checked==false))
    {
    	alert("Select your Option");
    	return false;
    }
    if((document.miscRep.optionId[1].checked==true)){//||(document.miscRep.optionId[2].checked==true)
    	//alert("one checked");
    	if((document.getElementById("txt_accountHead").value.length!=6)||(document.getElementById("txt_accountHead").value=="")){
    		alert("Enter Account HeadCode");
    	return false;
    	}
    	return true;
    }
    
    var t=document.getElementById("txtSection").value;
	  if(t=="")
		  {
		  alert("Select Report Type");
		  return false;
		  }
	  if(document.miscRep.reptype[1].checked==true)
	  {
  		  if(document.getElementById("txtsupplement_no").value=="")
  			  {
  			  alert("Enter Supplement No");
      		  return false;
  			  }
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




 
 