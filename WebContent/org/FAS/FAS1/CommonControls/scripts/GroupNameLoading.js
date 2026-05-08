/** Browser Identification */

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



var group_id     =new Array();
var group_name   =new Array();



/** Main Function */
function LoadGroupName()
{  
     var txtSectionId=document.getElementById("txtSectionId").value;
     var url="../../../../../GroupNameLoading.kv?txtSectionId="+txtSectionId;
     var req=getTransport();
     req.open("GET",url,true); 
     req.onreadystatechange=function()
     {
    	 LoadGroupNameRes(req);
     }   
     req.send(null);	  
}



function LoadGroupNameRes(req)
{  
  
    if(req.readyState==4)
    {
  
        if(req.status==200)
        {  
             var baseResponse=req.responseXML.getElementsByTagName("response")[0];
             var tagcommand=baseResponse.getElementsByTagName("command")[0];
             var Command=tagcommand.firstChild.nodeValue;
           
            if(Command=="LoadGroupName")
            {
                 var flag=baseResponse.getElementsByTagName("flag")[0].firstChild.nodeValue;            	
                 if(flag=="success")
            	    {
            	           
            	           /** Group id Object to find length */ 
            	           var group_id_obj=baseResponse.getElementsByTagName("group_id");
            	           
            	           /** Get Combo box Object */
            	           var txtGroupId = document.getElementById("txtGroupId");
            	           
            	            for(var k=0;k<group_id_obj.length;k++)
            	            {
            	            	group_id[k]=baseResponse.getElementsByTagName("group_id")[k].firstChild.nodeValue;
            	            	group_name[k]=baseResponse.getElementsByTagName("group_name")[k].firstChild.nodeValue;            	            	
            	            }
            	         
            	          
                            txtGroupId.innerHTML="";
                            
            	          /* var option=document.createElement("OPTION");
                            
            	            option.text="--Select Bank Acc. Number--";
            	            option.value="";
                            
                          
                          
            	            try
            	            {
            	            	txtGroupId.add(option);
            	            }catch(errorObject)
            	            {
            	            	txtGroupId.add(option,null);
            	            }
                            
                           */ 
            	            
            	            for(var k=0;k<group_id.length;k++)
            	            {   
            	                  var option=document.createElement("OPTION");
            	                  option.text=group_name[k];
            	                  option.value=group_id[k];
            	                  try
            	                  {
                                          txtGroupId.add(option);
            	                  }
            	                  catch(errorObject)
            	                  {
            	                	  txtGroupId.add(option,null);
            	                  }
            	            }
            	    }
                    else if( flag=="NoData")
                    {
                        alert("Group not found");
                        
                        /** Get Combo box Object */
            	        var txtGroupId = document.getElementById("txtGroupId");
                        
                        
           	        txtGroupId.innerHTML="";
                        var option=document.createElement("OPTION");
                        option.text="-- Select Group --";
                        option.value="";
                        try
                        {
                            txtGroupId.add(option);
                        }catch(errorObject)
                        {
                            txtGroupId.add(option,null);
                        }  
                        
                    }
                    else if( flag=="failure")
                    {
                        alert("Error Loading Group ");
                        
                        /** Get Combo box Object */
            	        var txtGroupId = document.getElementById("txtGroupId");
                        
                        
           	        txtGroupId.innerHTML="";
                        var option=document.createElement("OPTION");
                        option.text="-- Select Group  --";
                        option.value="";
                        try
                        {
                            txtGroupId.add(option);
                        }catch(errorObject)
                        {
                            txtGroupId.add(option,null);
                        }  
                    
                    }
                    
              }
        
        }
    }
}

