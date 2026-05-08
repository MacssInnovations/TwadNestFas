//alert("call me");
//alert('call me new');

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


function loadYears()
{

var opt1;
var yr=document.getElementById("cmbyear").value;
var endyear=parseInt(yr)+5;
var cmbyearid=document.getElementById("cmbyear");
  cmbyearid.innerHTML="";
 
for(var i=yr;i<endyear;i++)
  {
  
  
                opt1=document.createElement("option");
                opt1.text=i;
                opt1.value=i;
                 try
                {
                    cmbyearid.add(opt1);
                }catch(errorObject)
                {
                    cmbyearid.add(opt1,null);
                }
  }
}


function displayCadreReport()
{
//var cadrename=document.getElementById("cmbCadreName").value;

var year=document.getElementById("cmbyear").value;
//alert(cadrename +" "+year);
//alert(year);
document.frmRetirementList.action="../../../../../../RetirementView?year="+year;
document.frmRetirementList.method="post";
document.frmRetirementList.submit();
}


function  selectoption1()
{
//alert('test');

    if(document.frmRetirementList.optselect[0].checked)
    {
        var id=document.getElementById("divcadre");
        id.style.display='none';
        var desigblockid=document.getElementById("desigblock");
        desigblockid.style.display='none';
      
    }
    
    else if(document.frmRetirementList.optselect[1].checked)
    {
       
         var id=document.getElementById("divcadre");
        id.style.display='block';     
     
    }

//alert('selectoption end');
}


function getDesignation2()
{
document.getElementById("desigblock").style.display='block';
  var type=document.frmRetirementList.cmbsgroup2.options[document.frmRetirementList.cmbsgroup2.selectedIndex].value;
  if(type!=0)
  {
    loadOfficesByType2(type);
  }
  else
  {
     var des=document.getElementById("cmbCadre");
     des.innerHTML='';
  }
}

function loadOfficesByType2(type)
    {
//    alert("cadre");
//        alert(type);
        var type=document.frmRetirementList.cmbsgroup2.options[document.frmRetirementList.cmbsgroup2.selectedIndex].value;
       var url="../../../../../../DesigCadRankServ_New?Command=Cadre&cmbsgroup=" + type ;
 //      alert(url);
        var req=getTransport();
        req.open("GET",url,false);        
        req.send(null);
            var divdes=document.getElementById("divdesignation");
            divdes.style.visibility="visible";
           if(navigator.appName.indexOf('Microsoft')!=-1)
                divdes.innerHTML=req.responseText;
            else
                divdes.innerHTML=req.responseText;
       
    }

function cadreselectAll()
{
    if(document.frmRetirementList.chkcadre)
      {
      
            for(i=0;i<document.frmRetirementList.chkcadre.length;i++)
            {
                    document.frmRetirementList.chkcadre[i].checked=true;
                    
            }
        }
}

function designationclose()
{
    
    var iframe=document.getElementById("divdesignation");
    iframe.style.visibility='hidden';
    var desigblockid=document.getElementById("desigblock");
    desigblockid.style.display='none';
}
/*function showBand()
{
alert("called");
if(document.frmRetirementList.optGroup[1].checked)
    document.getElementById("cmbSpecificCadre").style.display='block';
alert("after");
var type=document.frmValidationSummaryRep.cmbsgroup1.options[document.frmValidationSummaryRep.cmbsgroup1.selectedIndex].value;
  //alert(type)     
  if(type!=0)
  {
    loadOfficesByType1(type);
  }
}



function loadOfficesByType1(type)
    {
        //alert(type);
        var type=document.frmValidationSummaryRep.cmbsgroup1.options[document.frmValidationSummaryRep.cmbsgroup1.selectedIndex].value;
       var url="../../../../../../DesigCadRankServ_New?Command=Cadre&cmbsgroup=" + type ;
       alert(url);
        var req=getTransport();
        req.open("GET",url,false);        
        req.send(null);
            var divdes=document.getElementById("divdesignation");
            divdes.style.visibility="visible";
            
           if(navigator.appName.indexOf('Microsoft')!=-1)
                divdes.innerHTML=req.responseText;
            else
                divdes.innerHTML=req.responseText;
             //alert("show");
            }
           */