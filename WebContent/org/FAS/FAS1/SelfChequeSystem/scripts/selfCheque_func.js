function doFunction(Command,param)
{   
    var addtional_field_value;
    var txtAcc_HeadCode=document.getElementById("txtAcc_HeadCode").value;
    var cmbAcc_UnitCode=document.getElementById("cmbAcc_UnitCode").value;
    var cmbOffice_code=document.getElementById("cmbOffice_code").value;
    
        if(Command=="checkCode")
        {  
            //common_AHead_code_flag=param;
             var cmbSL_Code=document.getElementById("cmbSL_Code");   
             clear_Combo(cmbSL_Code);
             
            if(txtAcc_HeadCode.length>=6)
            {
                var url="../../../../../Receipt_SL.view?Command=checkCode&txtAcc_HeadCode="+txtAcc_HeadCode;
                //alert(url);
                var req=getTransport();
                req.open("GET",url,true); 
                req.onreadystatechange=function()
                {
                   handleResponse(req);
                }   
                        req.send(null);
            }         
        }
        else if(Command=="Load_SL_Code")
        {  
                var cmbSL_type=param;
                document.getElementById("offlist_div_trans").style.display='none';
                document.getElementById("emplist_div_trans").style.display='none';
                
                if(cmbSL_type==5 )
                  {
                      document.getElementById("offlist_div_trans").style.display='block';
                   //   clear_Combo(document.getElementById("cmbSL_Code"));
                      //document.getElementById("txtOfficeID_trs").value="";
                      addtional_field_value=document.getElementById("txtOfficeID_trs").value;
                      //alert("USE search ICON to select the office");
                      if(addtional_field_value=="")
                      {
                            clear_Combo(document.getElementById("cmbSL_Code"));
                            alert("Enter or select the office code");
                            return true;
                      }
                  }
                else
                 {
                    document.getElementById("txtOfficeID_trs").value="";
                 }
                  
                 if(cmbSL_type==7)
                  {
                      document.getElementById("emplist_div_trans").style.display='block';
                      addtional_field_value=document.getElementById("txtEmpID_trs").value;
                      //alert("USE search ICON to select the office");
                      if(addtional_field_value=="")
                      {
                            clear_Combo(document.getElementById("cmbSL_Code")); 
                            alert("Enter or select the employee code");
                            return true;
                      }
                  }
                else
                  {
                      document.getElementById("txtEmpID_trs").value="";
                  }
                  
              if(cmbSL_type!="")                              // called only not equal to null
                {
                    
                     var url="../../../../../Receipt_SL.view?Command=Load_SL_Code&cmbSL_type="+cmbSL_type+
                     "&cmbAcc_UnitCode="+cmbAcc_UnitCode+
                     "&cmbOffice_code="+cmbOffice_code+"&addtional_field_value="+addtional_field_value;
                    //alert(url);
                    var req=getTransport();
                    req.open("GET",url,true); 
                    req.onreadystatechange=function()
                    {
                       handleResponse(req);
                    }   
                            req.send(null);
                }
                else if(cmbSL_type=="")
                clear_Combo(document.getElementById("cmbSL_Code"));
            
        }
} 
/////////////////////////////////////////////   handleResponse()  /////////////////////////////////////////////////////
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
            else if(Command=="Load_SL_Code")
            {
                Load_SL_Code(baseResponse);
            }
        }
    }
}

/////////////////////////////////////////////////// account head code function  ------------------------------
function loadcheckCode(baseResponse)
{
    var flag=baseResponse.getElementsByTagName("flag")[0].firstChild.nodeValue;
   
    
    if(flag=="success")
    {
         var hid=baseResponse.getElementsByTagName("hid")[0].firstChild.nodeValue;
         document.getElementById("txtAcc_HeadCode").value=hid;
         var hdesc=baseResponse.getElementsByTagName("hdesc")[0].firstChild.nodeValue;
         var BalType=baseResponse.getElementsByTagName("BalType")[0].firstChild.nodeValue;
         var SL_YN =baseResponse.getElementsByTagName("SL_YN")[0].firstChild.nodeValue;
         document.getElementById("txtAcc_HeadCode").value=hid;
         document.getElementById("txtAcc_HeadDesc").value=hdesc;
      
       var cmbSL_type=document.getElementById("cmbSL_type");   
       
       if(SL_YN=="Y")
       {
        
        var items_SLcode=new Array();
        var items_SLdesc=new Array();
            var SLCODE=baseResponse.getElementsByTagName("SLCODE");
            var SLDESC=baseResponse.getElementsByTagName("SLDESC");
            for(var k=0;k<SLCODE.length;k++)
            {
                items_SLcode[k]=baseResponse.getElementsByTagName("SLCODE")[k].firstChild.nodeValue;
                items_SLdesc[k]=baseResponse.getElementsByTagName("SLDESC")[k].firstChild.nodeValue;
            }
            
            cmbSL_type.innerHTML="";
            var option=document.createElement("OPTION");
            option.text="--Select Type--";
            option.value="";
            try
            {
                cmbSL_type.add(option);
            }catch(errorObject)
            {
                cmbSL_type.add(option,null);
            }
            for(var k=0;k<SLCODE.length;k++)
            {   
              var option=document.createElement("OPTION");
              option.text=items_SLdesc[k];
              option.value=items_SLcode[k];
               try
              {
                  cmbSL_type.add(option);
              }
              catch(errorObject)
              {
                  cmbSL_type.add(option,null);
              }
            }
            
            if(common_cmbSL_type=="")
                document.getElementById("cmbSL_type").value="";
            else
                document.getElementById("cmbSL_type").value=common_cmbSL_type;    //set from grid
       }
        if(SL_YN=="N" || SL_YN=="null")
           {    
                cmbSL_type.innerHTML=""; 
                var option=document.createElement("OPTION");
                option.text="--Select Type--";
                option.value="";
                try
                {
                    cmbSL_type.add(option);
                }catch(errorObject)
                {
                    cmbSL_type.add(option,null);
                }
            }
          
    }
     else if(flag=="failure")
     {
         alert("Account Head Code '"+document.getElementById("txtAcc_HeadCode").value+"' doesn't Exist");
         document.getElementById("txtAcc_HeadCode").value="";
         document.getElementById("txtAcc_HeadCode").focus();
     }
     
        //common_AHead_code_flag="";
        common_cmbSL_type="";
}
/////////////////////////////////////////////   Load_SL_Code() by User /////////////////////////////////////////////////////

function Load_SL_Code(baseResponse)
{
var flag=baseResponse.getElementsByTagName("flag")[0].firstChild.nodeValue;

    if(flag=="success")
    {
         var cmbSL_Code=document.getElementById("cmbSL_Code");
         
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
            //alert('here second');
            for(var k=0;k<items_id.length;k++)
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
           document.getElementById("cmbSL_Code").value=items_id[0];
           if(document.getElementById("cmbSL_type").value==5)
           {
                var state=baseResponse.getElementsByTagName("state")[0].firstChild.nodeValue;
                if(state!="CR")
                alert("Office is not in working status");
           }
            //if(document.getElementById("cmbMas_SL_type").value!=9 && document.getElementById("cmbSL_type").value==7)
           if(document.getElementById("cmbSL_type").value==7)
           {
                var state=baseResponse.getElementsByTagName("state")[0].firstChild.nodeValue;
                if(state=="DPN")
                alert("Employee in Deputation");
           } 
           
    }
    else if(flag=="failure")
    {
        alert("No data found");
        var cmbSL_Code=document.getElementById("cmbSL_Code");
        clear_Combo(cmbSL_Code);
    }
    
    common_cmbSL_Code="";
}


