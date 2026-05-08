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

function testRegular()
{	
var mn=document.getElementById("txtCB_Month").value;
//alert("testRegular ");
if(mn==3)
	{
	
	document.getElementById("labelDivsupp").style.display="block";
	document.getElementById("textDivregular").style.display="block";
	document.getElementById("textDivsupp").style.display="block";
	//dispsupno1
	}
else
	{
	document.getElementById("labelDivsupp").style.display="block";
	document.getElementById("textDivregular").style.display="block";
	document.getElementById("textDivsupp").style.display="none";
	//dispsupno1
	}
}
function testRegular1()
{	
var mn=document.getElementById("txtCB_Month_to").value;
//alert("testRegular1 ");
if(mn==3)
	{
	
	document.getElementById("labelDivsupp").style.display="block";
	document.getElementById("textDivregular").style.display="block";
	document.getElementById("textDivsupp").style.display="block";
	}
else
	{
	document.getElementById("labelDivsupp").style.display="block";
	document.getElementById("textDivregular").style.display="block";
	document.getElementById("textDivsupp").style.display="none";
	}
}
function cb_month_year(id)
{
   var particular=document.getElementById("particular");
   var more=document.getElementById("more");
       
  if(id=="particular_cb")
  {
     particular.style.display="block";
     more.style.display="none";
     document.getElementById("labelDivsupp").style.display="block";
 	document.getElementById("textDivregular").style.display="block";
 	document.getElementById("textDivsupp").style.display="block";
 	document.frmSubLedgerReport.reporttype[0].checked=true;
  }
  if(id=="more_cb")
  {
    more.style.display="block";
    particular.style.display="none";
    document.getElementById("labelDivsupp").style.display="none";
	document.getElementById("textDivregular").style.display="none";
	document.getElementById("textDivsupp").style.display="none";
	//document.getElementById("reporttype").checked=false;
	//alert("hhhh");
	document.frmSubLedgerReport.reporttype[0].checked=true;
	document.frmSubLedgerReport.reporttype[1].checked=false;
	
	
    
  }
}

function check_code(val)
{
     var particular1=document.getElementById("code_div1");
     var particular2=document.getElementById("code_div2");
           
      if(val=="single_code")
      {
         particular1.style.display="block";
         particular2.style.display="block";
         
      }
      if(val=="all_code")
      {
       particular1.style.display="none";
        particular2.style.display="none";
      }

}

function doFunction(Command,param)
{ 
  
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
               clear_Combo();
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
          
         var name="cmbSL_Code"; 
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
        var name="cmbSL_Code";  
        var cmbSL_Code=document.getElementById(name);
        clear_Combo(cmbSL_Code);
    }
    
    common_cmbSL_Code="";
}

function clear_Combo(combo)
{
        /** Clear Sub Ledger Code */
        var code_name="cmbSL_Code"; 
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
}

function numbersonly(e)
{   
        var unicode=e.charCode? e.charCode : e.keyCode
        if(unicode==13)
        {
          //try{t.blur();}catch(e){}
          //return true;
        
        }
        if (unicode!=8 && unicode !=9  )
        {
            if (unicode<48||unicode>57 ) 
                return false ;
        }
}


function checknull()
{
        if(document.getElementById("cmbSL_type").value!="0")
        {
            if(document.getElementById("cmbSL_Code").value=="")
            {
             alert("Select The Sub Ledger Code in General");
             return false;
            }
        }
        if(document.frmSubLedgerReport.month_year[0].checked==false && document.frmSubLedgerReport.month_year[1].checked==false)
        {
        alert("Choose month");
        return false;
        }
       /* if(document.frmSubLedgerReport.txtCB_Month_to.value=="3" || document.frmSubLedgerReport.txtCB_Month.value=="3")
        {
        alert("Enter supp no");
        return false;
        }*/
        if(document.frmSubLedgerReport.singleSlCode[0].checked==false && document.frmSubLedgerReport.singleSlCode[1].checked==false)
        {
        alert("Choose Type");
        return false;
        }
        if(document.frmSubLedgerReport.reporttype[1].checked==true )
        {
        alert("Enter supp no");
        return false;
        }
        
        return true; 
       
}


