
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
//alert('comes here');
var other_officeid=document.getElementById("cmbsgroup2").value;
//alert(other_officeid);
document.frmOtherDeptList.action="../../../../../../OtherDepartmentOfficesServ?otheroffid="+other_officeid;
document.frmOtherDeptList.method="post";
document.frmOtherDeptList.submit();
}


function  selectoption1()
{
    if(document.frmOtherDeptList.optselect[0].checked)
    {
        var id=document.getElementById("divcadre");
        id.style.display='none';
        var desigblockid=document.getElementById("desigblock");
        desigblockid.style.display='none';
      
    }
    
    else if(document.frmOtherDeptList.optselect[1].checked)
    {
       
         var id=document.getElementById("divcadre");
        id.style.display='block';     
     
    }

//alert('selectoption end');
}


function getOffices()
{
document.getElementById("desigblock").style.display='block';
  var type=document.frmOtherDeptList.cmbsgroup2.options[document.frmOtherDeptList.cmbsgroup2.selectedIndex].value;
  if(type!=0)
  {
    loadOfficesByType(type);
  }
  else
  {
     var des=document.getElementById("cmbCadre");
     des.innerHTML='';
  }
}

function loadOfficesByType(type)
    {
//    alert("cadre");
//        alert(type);
        var type=document.frmOtherDeptList.cmbsgroup2.options[document.frmOtherDeptList.cmbsgroup2.selectedIndex].value;
       var url="../../../../../../OtherDepartmentOfficesServ?Command=OtherOffices&cmbsgroup=" + type ;
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
    if(document.frmOtherDeptList.chkcadre)
      {
      
            for(i=0;i<document.frmOtherDeptList.chkcadre.length;i++)
            {
                    document.frmOtherDeptList.chkcadre[i].checked=true;
                    
            }
        }
}

function cadreClose()
{
    
    var iframe=document.getElementById("divdesignation");
    iframe.style.visibility='hidden';
    var desigblockid=document.getElementById("desigblock");
    desigblockid.style.display='none';
}

