function requestCreation()
{
var rad_val1=false;
 var rad_val2=false;
 var rad_val3=false;
 var rad_val4=false;
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

function deleteAccountHeadCode(ahc)
{
    
    var AccountHeadCode=ahc;
    
    rid=ahc;
    

 var url="";
url="../../../../../DEServlet.view?command=Delete&AHCode="+ahc; 

var req=requestCreation();
        req.open("GET",url,true);        
        req.onreadystatechange=function()
        
        {
         processResponse(req);
        }
        req.send(null);
               

}



function processResponse(req)
    {   
   
    
      if(req.readyState==4)
        {
          if(req.status==200)
          {       
            var baseResponse=req.responseXML.getElementsByTagName("response")[0];
            
              
              var tagCommand=baseResponse.getElementsByTagName("command")[0];
              
              var command=tagCommand.firstChild.nodeValue; 
              
              if(command=="Delete")
              {
                 
                 deleteRow(baseResponse);  
                 
              }
              
           }
           
         }
         
    }   
    
    
    
 function deleteRow(baseResponse)
{

var flag=baseResponse.getElementsByTagName("flag")[0].firstChild.nodeValue;
                  if(flag=="success")
                  {
                      alert("SubLedger Deleted  successfully.");
                      var rid=baseResponse.getElementsByTagName("AHCode")[0].firstChild.nodeValue;
                      
                      var r=document.getElementById(rid); 
                      
                      var tbody=document.getElementById("tblList"); 
                      
                      var ri=r.sectionRowIndex;
                      
                      
                      tbody.deleteRow(ri);    
                    
                  }
                  else
                  {
                      alert("unable to delete");
                  }
                  
 }





function showSubLedgerTypes(ahc)
{
    
    var chk=document.getElementById("ch1");
    chk.checked=false; 
    var AccountHeadCode=ahc;
    
    
    var url="../jsps/SubLedgerGrid.jsp?AHC="+ AccountHeadCode ;
    var  my_window= window.open(url,"mywindow2","status=1,height=500,width=700"); 
    my_window.moveTo(100,100); 
    
    
}

function EditAccountHeadCode(ahc)
{
      
      //callMe();
      var AccountHeadCode=ahc;
      
      var   rid=ahc;
      

      var r=document.getElementById(rid);   
      
      /*var rcells=r.cells;
      alert(rcells);
      acctHeadCode=rcells.item(1).firstChild.nodeValue;
      alert(acctHeadCode);
      
      acctHeadDesc=rcells.item(2).firstChild.nodeValue;
      alert(acctHeadDesc);
*/
      //document.form2.txt_sltypeCode.value=rcells.item(1).firstChild.nodeValue;
      //document.form2.txt_sldesc.value=rcells.item(2).firstChild.nodeValue;
      
    var url="../jsps/EditRecord.jsp?AHC="+AccountHeadCode ;
    var  my_window= window.open(url,"mywindow2","status=1,height=1700,width=900,scrollbars,resizable"); 
    my_window.moveTo(100,100); 
     
    
    
}


