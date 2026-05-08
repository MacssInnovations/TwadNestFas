//alert('job popup');
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

var s=0;
var hier=true;
var level=true;
var city=true;
var  other=true;
var cmbOfficeType=document.getElementById("cmbOfficeType").value;
 

function btnsubmit()
{
s=0;
var type=document.frmOfficeDetailRep.cmbolevel.options[document.frmOfficeDetailRep.cmbolevel.selectedIndex].value;
if(type=='RN')
    s=document.frmOfficeDetailRep.cmbregion.options[document.frmOfficeDetailRep.cmbregion.selectedIndex].value;
if(type=='CL')
    s=document.frmOfficeDetailRep.cmbcircle.options[document.frmOfficeDetailRep.cmbcircle.selectedIndex].value;
if(type=='DN')
    s=document.frmOfficeDetailRep.cmbdivision.options[document.frmOfficeDetailRep.cmbdivision.selectedIndex].value;
if(type=='SD')
    s=document.frmOfficeDetailRep.cmbsubdiv.options[document.frmOfficeDetailRep.cmbsubdiv.selectedIndex].value;
if(type=='SN')
    s=document.frmOfficeDetailRep.cmbsection.options[document.frmOfficeDetailRep.cmbsection.selectedIndex].value;

//alert(s);
if(type=='HO')
{
        Minimize();
        opener.doParentJob(1,'TWAD');
        return true;

}
else
{
    if(s!=0 && s!=null)
    {
        Minimize();
        opener.doParentJob(s,'TWAD');
        return true;
    }
    else
    {
            var msg;
            if(type=='RN')
                msg ="Select upto Region";               
            if(type=='CL')
                msg ="Select upto Circle";  
            if(type=='DN')
                msg ="Select upto Division";  
            if(type=='SD')
                msg ="Select upto SubDivision";  
            if(type=='SN')
                msg ="Select upto Section";  

        alert(msg);
        return false;
    }
}
}

function btncancel()
{

 self.close();
}

function Minimize() 
{
window.resizeTo(0,0);
window.screenX = screen.width;
window.screenY = screen.height;
opener.window.focus();
}


/////////////////////////////////////////////////////////////////////////////////////////////////


//******************************************************************************************//
function activatespecific()
{
   
        if(document.frmOfficeDetailRep.optoffdetail[2].checked==true)
        {
               document.frmOfficeDetailRep.cmblevel.disabled=false;
              // document.frmOfficeDetailRep.optsel[0].disabled=false;
              // document.frmOfficeDetailRep.optsel[1].disabled=false;
                      
        }
        else if (document.frmOfficeDetailRep.optoffdetail[3].checked==true)
        {
               document.frmOfficeDetailRep.cmblevel.disabled=true;
              // document.frmOfficeDetailRep.optsel[0].disabled=true;
              // document.frmOfficeDetailRep.optsel[1].disabled=true;
        
        }
        else
        {
               document.frmOfficeDetailRep.cmbOfficeType.disabled=true;
}


function btnsubmit()
{
       
                document.frmOfficeDetailRep.submit();
        
}

window.onload=function()
{
document.frmOfficeDetailRep.reset();
}