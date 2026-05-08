/**
 *  Browser Identification 
 */ 
 
var jsseq=""; 
 
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



function doFunction(Command,param,seq)
{ 
  jsseq = seq;
  var cmbAcc_UnitCode=document.getElementById("cmbAcc_UnitCode").value;
  var cmbOffice_code=document.getElementById("cmbOffice_code").value;
 
  if(Command=="Load_SL_Code")
        {  
          var cmbSL_type=param;           
              
          if(cmbSL_type!="")   
            {
                 var url="../../../../../Receipt_SL.view?Command=Load_SL_Code&cmbSL_type="+cmbSL_type+"&cmbAcc_UnitCode="+cmbAcc_UnitCode+
                 "&cmbOffice_code="+cmbOffice_code ;
                var req=getTransport();
                req.open("GET",url,true); 
                req.onreadystatechange=function()
                {
                   handleResponse(req);
                }   
                        req.send(null);
            }
            else if(cmbSL_type=="")
            var name="cmbSL_Code"+jsseq; 
            clear_Combo(document.getElementById(name));
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
           
            if(Command=="Load_SL_Code")
            {
                Load_SL_Code(baseResponse);
            }            
        }
    }
}

function Load_SL_Code(baseResponse)
{
var flag=baseResponse.getElementsByTagName("flag")[0].firstChild.nodeValue;

    if(flag=="success")
    {
          
         var name="cmbSL_Code"+jsseq; 
         var cmbSL_Code=document.getElementById(name);         
         var items_id=new Array();
         var items_name=new Array();
        
            var cid=baseResponse.getElementsByTagName("cid");
            var cname=baseResponse.getElementsByTagName("cname");
            for(var k=0;k<cid.length;k++)
            {
                items_id[k]=baseResponse.getElementsByTagName("cid")[k].firstChild.nodeValue;
                items_name[k]=baseResponse.getElementsByTagName("cname")[k].firstChild.nodeValue;
            }
            
            clear_Combo(cmbSL_Code);           
            for(var k=0;k<cid.length;k++)
            {   
                  var option=document.createElement("OPTION");
                  option.text=items_name[k];
                  option.value=items_id[k];
                   try
                  {
                      cmbSL_Code.add(option);
                  }
                  catch(errorObject)
                  {
                      cmbSL_Code.add(option,null);
                  }
            }           
           
    }
    else if(flag=="failure")
    {
        alert("No data found");
        var name="cmbSL_Code"+jsseq;  
        var cmbSL_Code=document.getElementById(name);
        clear_Combo(cmbSL_Code);
    }
    
    common_cmbSL_Code="";
}

function clear_Combo(combo)
{
      
        /** Clear Sub Ledger Code */
        var code_name="cmbSL_Code"+jsseq; 
        var cmbSL_Code=document.getElementById(code_name);   
        cmbSL_Code.innerHTML="";
        var option=document.createElement("OPTION");
        option.text="--Select SL Code--";
        option.value="0";
        try
        {
            cmbSL_Code.add(option);
        }catch(errorObject)
        {
            cmbSL_Code.add(option,null);
        }
        
        
        
        /** Clear Sub Ledger Type */        
        
      /*
        var type_name="cmbSL_type"+jsseq; 
        var cmbSL_type=document.getElementById(type_name);   
        cmbSL_type.innerHTML="";
        var option=document.createElement("OPTION");
        option.text="--Select SL Type--";
        option.value="";
        try
        {
            cmbSL_type.add(option);
        }catch(errorObject)
        {
            cmbSL_type.add(option,null);
        }
     */   
     
        
}




