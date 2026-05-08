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

var window_BankAccNumber;
function Lists()
{  
         var cmbAcc_UnitCode=document.BillSancEmp.cmbAcc_UnitCode.value;
         var cmbOffice_code=document.BillSancEmp.cmbOffice_code.value;
         if((document.BillSancEmp.fin_yr.value=="") || (document.BillSancEmp.fin_yr.value.length<=0) || (document.BillSancEmp.fin_yr.value=="0"))
 	    {
 	        alert("Select Financial Year");
 	        document.BillSancEmp.fin_yr.focus();
 	        return false;
 	    } 
         var finyr=document.getElementById("fin_yr").value; 
         window_BankAccNumber= window.open("../jsps/Bill_Sanc_Emplist.jsp?cmbAcc_UnitCode="+cmbAcc_UnitCode+"&cmbOffice_code="+cmbOffice_code+"&finyr="+finyr,"mywindow1","resizable=YES, scrollbars=yes"); 
         window_BankAccNumber.moveTo(250,250);    
         window_BankAccNumber.focus();
}

function goBack(major,minor,off,sanc)
{
         var s=minor.split("-");
         document.BillSancEmp.majorType.value = major; 
         minorType=document.getElementById("minorType");
         var option=document.createElement("OPTION");
                option.text=s[1];
                option.value=s[0];
                minorType.length=0;	
                try
                {
                        minorType.add(option);
                }
                catch(errorObject)
                {
                        minorType.add(option,null);
                }
        document.getElementById("txtOfficeID_mas").value=off;
        var s1=sanc.split("-");
        document.BillSancEmp.sanc.value = s1[0]; 
        document.BillSancEmp.sanc.text = s1[1];
        document.BillSancEmp.cmdAdd.disabled = true;
        document.BillSancEmp.cmdEdit.disabled = false;
        document.BillSancEmp.cmdDelete.disabled = false;
        document.getElementById("fin_yr").disabled = true;
        document.getElementById("majorType").disabled = true;
        document.getElementById("minorType").disabled = true;
}

function callminor()
{
        var req=getTransport();
        var majorType=document.BillSancEmp.majorType.value;
        var cmbAcc_UnitCode=document.getElementById("cmbAcc_UnitCode").value;
        var cmbOffice_code=document.getElementById("cmbOffice_code").value;
        var url="../../../../../Bill_Sanc_Emp?Command=check&cmbAcc_UnitCode="+cmbAcc_UnitCode+"&cmbOffice_code="+cmbOffice_code+"&majorType="+majorType;                
        var req=getTransport();
            req.open("GET",url,true); 
            req.onreadystatechange=function()
            {
               handleResponse(req);
            }   
                    req.send(null);
}
    
function nullcheck()
{
	 if((document.BillSancEmp.fin_yr.value=="") || (document.BillSancEmp.fin_yr.value.length<=0) || (document.BillSancEmp.fin_yr.value=="0"))
	    {
	        alert("Select Financial Year");
	        document.BillSancEmp.fin_yr.focus();
	        return false;
	    } 
      else if(document.getElementById("majorType").value==""||document.getElementById("majorType").selectedIndex==0)
        {
            alert("Enter Major Type");
            document.getElementById("majorType").focus();
            return false;
        }
      else
            return true;
}

function calling(Command)         
 {
    var cmbAcc_UnitCode=document.getElementById("cmbAcc_UnitCode").value;
    var cmbOffice_code=document.getElementById("cmbOffice_code").value;
    var finyr=document.getElementById("fin_yr").value; 
    var majorType=document.getElementById("majorType").value;
    var minorType=document.getElementById("minorType").value;
    var txtOfficeIDmas1=document.getElementById("txtOfficeID_mas").value;
    var sanc1=document.getElementById("sanc").value;
    if(Command=="Add")
    {
         
         var txtOfficeIDmas;
         if(txtOfficeIDmas1=="")
         {
             txtOfficeIDmas=0;
         }
         else
         {
             txtOfficeIDmas=txtOfficeIDmas1;                
         }         
         var sanc;
         if(sanc1=="")
         {
             sanc=0;
         }
         else
         {
             sanc=sanc1;                
         }   
         var flag=nullcheck();
         if(flag==true)
           {                
                    var url="../../../../../Bill_Sanc_Emp?Command=Add&cmbAcc_UnitCode="+cmbAcc_UnitCode+
                            "&cmbOffice_code="+cmbOffice_code+"&finyr="+finyr+
                            "&majorType="+majorType+"&minorType="+minorType+"&txtOfficeIDmas="+txtOfficeIDmas+"&sanc="+sanc;
                    var req=getTransport();
                    req.open("GET",url,true); 
                    req.onreadystatechange=function()
                    {
                       handleResponse(req);
                    }   
                            req.send(null);
          }
    }
    else if(Command=="Update")
       { 
   	    
            var txtOfficeIDmas;
            if(txtOfficeIDmas1=="")
             {
                 txtOfficeIDmas=0;
             }
             else
             {
                 txtOfficeIDmas=txtOfficeIDmas1;                
             }
             var sanc;
             if(sanc1=="")
             {
                 sanc=0;
             }
             else
             {
                 sanc=sanc1;                
             }  
            var flag=nullcheck();
            if(flag==true)
            {
                var url="../../../../../Bill_Sanc_Emp?Command=Update&cmbAcc_UnitCode="+cmbAcc_UnitCode+
                        "&cmbOffice_code="+cmbOffice_code+"&finyr="+finyr+
                        "&majorType="+majorType+"&minorType="+minorType+"&txtOfficeIDmas1="+txtOfficeIDmas+"&sanc="+sanc;
                var req=getTransport();
                req.open("GET",url,true); 
                req.onreadystatechange=function()
                {
                   handleResponse(req);
                }   
                        req.send(null);
            }
       }
    else if(Command=="Delete")
        {        
           if(confirm("Do You Really want to Delete it?"))
            {  
                var txtOfficeIDmas;
                if(txtOfficeIDmas1=="")
                 {
                     txtOfficeIDmas=0;
                 }
                 else
                 {
                     txtOfficeIDmas=txtOfficeIDmas1;                
                 }  
               var flag=nullcheck();
               if(flag==true)
               {  
                  var url="../../../../../Bill_Sanc_Emp?Command=Delete&cmbAcc_UnitCode="+cmbAcc_UnitCode+
                        "&cmbOffice_code="+cmbOffice_code+"&finyr="+finyr+
                        "&majorType="+majorType+"&minorType="+minorType;
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
}

function ClearAll()
{
        alert("clear"); 
        document.BillSancEmp.cmbAcc_UnitCode.selectedIndex=0;
        document.BillSancEmp.cmbOffice_code.selectedIndex=0;
        document.getElementById("fin_yr").value="";
        document.getElementById("majorType").value="";
        document.getElementById("minorType").value="";
        document.getElementById("txtOfficeID_mas").value="";
        document.getElementById("sanc").value="";
        document.getElementById("cmdEdit").disabled=false;
        document.getElementById("cmdDelete").disabled=false;
        document.getElementById("cmdAdd").disabled=false;
        document.getElementById("fin_yr").disabled = false;
        document.getElementById("majorType").disabled = false;
        document.getElementById("minorType").disabled = false;
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
            
            if(Command=="Add")
            {
                addRow(baseResponse);
            }
            else if(Command=="Delete")
            {
                deleteRow(baseResponse);
            }
            else if(Command=="Updated")
            {                
                UpdateRow(baseResponse);
            }
          
            else if(Command=="Disp")
            {
                DispRow(baseResponse);
            }
           }
         }
    }
    
function addRow(baseResponse)
{

    var flag=baseResponse.getElementsByTagName("flag")[0].firstChild.nodeValue;
    if(flag=="success")
    {
        alert("Record inserted into database");
        ClearAll();
    }
    else if(flag=="AlreadyExist")
       {
        alert("Record AlreadyExist.so,can't Inserted");
        document.getElementById("majorType").value="";
        document.getElementById("minorType").value="";
        document.getElementById("txtOfficeID_mas").value="";
        document.getElementById("sanc").value="";
       }
    else
    {
        alert("Record not inserted into database");
    }
}

function UpdateRow(baseResponse)
{
    var flag=baseResponse.getElementsByTagName("flag")[0].firstChild.nodeValue;
    var items=new Array();
    if(flag=="success")
    {
        alert("Record Updated");
        ClearAll();
    }
    else
    {
        alert("Record not Updated");
    }
}

function deleteRow(baseResponse)
{
    var flag=baseResponse.getElementsByTagName("flag")[0].firstChild.nodeValue;
    
    if(flag=="success")
    {
        alert("Records deleted from database");
         ClearAll();
    }
    else
    {
        alert("Record not deleted from database");
    }
} 

function numbersonly(e,t)
{
       var unicode=e.charCode? e.charCode : e.keyCode;
       if(unicode==13)
        {
          try{t.blur(); }catch(e){}
          return true;
        }
        if (unicode!=8 && unicode !=9  )
        {
            if (unicode<48||unicode>57 ) 
            {
                return false;
            }
        }
}

function DispRow(baseResponse)
{
    var flag=baseResponse.getElementsByTagName("flag")[0].firstChild.nodeValue;
    if(flag=="success")
    {
        minorType=document.getElementById("minorType");
        minorType.length=0;
        mtype=baseResponse.getElementsByTagName("mindesc");    
        for(var i=0;i<mtype.length;i++)
               {
                var items_id=baseResponse.getElementsByTagName("mincode")[i].firstChild.nodeValue;
                var items_name=baseResponse.getElementsByTagName("mindesc")[i].firstChild.nodeValue;				       	                                                  
                var option=document.createElement("OPTION");
                option.text=items_name;
                option.value=items_id;
                try
                {
                        minorType.add(option);
                }
                catch(errorObject)
                {
                        minorType.add(option,null);
                }
        }
    }
    else
       {
               alert("No records");
               document.BillSancEmp.majorType.selectedIndex="";
        }
}

function numbersonly(e,t)
{
       var unicode=e.charCode? e.charCode : e.keyCode;
       if(unicode==13)
        {
          try{t.blur(); }catch(e){}
          return true;
        }
        if (unicode!=8 && unicode !=9  )
        {
            if (unicode<48||unicode>57 ) 
            {
                return false;
            }
        }
}   

