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


function nullCheck()
{
    
    if((frmCreateNews.txtEventId.value=="") || (frmCreateNews.txtEventId.value.length<=0))
    {
         alert("Please Enter Caption Id");
         frmCreateNews.txtEventId.focus();
         return false;
    } 
    
    /*
    if((frmCreateNews.txtattachFile.value=="") || (frmCreateNews.txtattachFile.value.length<=0))
    {
         alert("Please Attach the File");
         frmCreateNews.txtattachFile.focus();
         return false;
    }   */

    
    return true;
}

function numbersonly1(e,t)
{
       var unicode=e.charCode? e.charCode : e.keyCode;
       if(unicode==13)
        {
          try{t.blur();}catch(e){}
          return true;
        
        }
        if (unicode!=8 && unicode !=9  )
        {
            if (unicode<48||unicode>57 ) 
                return false 
        }
}

function callServer(command,param)
{

    var strCapId=document.frmCreateNews.txtEventId.value;
    
    
      if(command=="Existg")
       {
       //startwaiting(document.frmEmployeeProfile) ; 
          url="../../../DeleteAttachFileServ?Command=Existg&CapId=" + strCapId;
          //alert(url);
          
            var req=getTransport();
            req.open("GET",url,true); 
            req.onreadystatechange=function()
            {
               processResponse(req);
            }   
            req.send(null);
        
       }
       
       else if(command=="DelFile")
       {
         
         if(check()==true)
         {
           var selAtt="";
           
           var url="../../../DeleteAttachFileServ?Command=DelFile&CapId="+strCapId;
         
           var leng=parseInt(document.frmCreateNews.sell.length);
           
           if(leng>0)
           {
             for(i=0;i<document.frmCreateNews.sell.length;i++)
             {
               if(document.frmCreateNews.sell[i].checked==true)
               {
               selAtt=selAtt+document.frmCreateNews.sell[i].value +",";
               }
             }
             
             if(selAtt!="")
             {
              
               var selAttnew=selAtt.substring(0,selAtt.length-1); 
               url=url+"&selected="+selAttnew;
               
             }
             
             document.frmCreateNews.action=url;         
             document.frmCreateNews.method="post"; 
             document.frmCreateNews.submit();  
           
           }
           else
           {
             
             
               if(document.frmCreateNews.sell.checked==true)
               {
               selAtt=document.frmCreateNews.sell.value ;
               //alert("value of cell is"+selAtt);
               }
            
             if(selAtt!="")
             {           
              
               url=url+"&selected="+selAtt;
               
             }
             else
               {
                
                 //document.getElementById("btnRelieve").disabled=true;
               }
               
             document.frmCreateNews.action=url;          
             document.frmCreateNews.method="post"; 
             document.frmCreateNews.submit();
           
           }
         }
       
       }
       
        
}

function processResponse(req)
{   
      if(req.readyState==4)
        {
          if(req.status==200)
          {       
          //stopwaiting(document.frmEmployeeProfile) ;
             //alert('inside success');
             
             var baseResponse=req.responseXML.getElementsByTagName("response")[0];
             //alert(baseResponse);
             var tagCommand=baseResponse.getElementsByTagName("command")[0];
             var command=tagCommand.firstChild.nodeValue;
             //alert('command...'+command);
              
             if(command=="Existg")
             {
                  existRow(baseResponse);                 
             } 
              
          }
        }
}
  
 
function existRow(baseResponse)
{
              var flag=baseResponse.getElementsByTagName("flag")[0].firstChild.nodeValue; 
             // alert(flag);
              if(flag=="Success")
              { 
                 //alert("success");
                document.frmCreateNews.txtCaption.value=baseResponse.getElementsByTagName("caption")[0].firstChild.nodeValue; 
                //document.frmCreateNews.txtatt_slno.value=baseResponse.getElementsByTagName("attach_slno")[0].firstChild.nodeValue;  
                var service=baseResponse.getElementsByTagName("details");
                
                //alert('service...'+service);
                //alert('length...'+service.length);
                if(service)
                {
                   //alert('inside service');
                att_slno=new Array();
                att_name=new Array();
                var i=0;
                if(service.length>1)
                {
                   //alert('inside 1');
                  for(i=0;i<service.length;i++)
                  {
                    //alert('inside 2');
                    att_slno[i]=service[i].getElementsByTagName("attach_slno")[0].firstChild.nodeValue;
                    att_name[i]=service[i].getElementsByTagName("file_title")[0].firstChild.nodeValue;
                    
                    if(att_name[i]=='null')
                    att_name[i]="";
                    else
                    att_name[i]=att_name[i];
                  
                  } 
                
                }
                else
                {
                  //alert('inside 3');
                    att_slno[0]=service[0].getElementsByTagName("attach_slno")[0].firstChild.nodeValue;
                    att_name[0]=service[0].getElementsByTagName("file_title")[0].firstChild.nodeValue;
                    
                    if(att_name[0]=='null')
                    att_name[0]="";
                    else
                    att_name[0]=att_name[0];
                
                }
                
                
                }
                
                loadPage(baseResponse);
                
                
                
                
              }
              
              if(flag=="Failure")
              {
                alert('Please Enter the correct Caption Id');
                document.frmCreateNews.txtEventId.value="";
                document.frmCreateNews.txtEventId.focus();
                document.frmCreateNews.txtCaption.value="";
                
              }
              /*
              else
              {
                alert('No data Found');
                var tbody=document.getElementById("grid_body");
                    try
                    {tbody.innerHTML="";}
                    catch(e) 
                    {tbody.innerText="";}
              }*/
              
              /*
              else if(flag=="Failure")
              {
                alert("Given Caption Id doesnt exist");
                document.frmCreateNews.txtEventId.focus();
                document.frmCreateNews.txtEventId.value='';
              }*/
}

function loadPage(baseResponse)
{

  //alert('inside load page');
  var tbody=document.getElementById("grid_body");
  
  try
    {tbody.innerHTML="";}
    catch(e) 
    {tbody.innerText="";}
    
    //var i=0;
    var j=1;
    var i=0;
    
    if(att_slno.length>1)
    {
      //alert('inside att_slno');
      //alert('length...'+att_slno.length);
      for(i=0;i<att_slno.length;i++)
      {
         var mycurrent_row=document.createElement("TR");
          mycurrent_row.id=j;
          
          var cell1=document.createElement("TD");
          var sel="";            
          if (window.navigator.appName.toLowerCase().indexOf("netscape") == -1)
          {        
            sel=document.createElement("<INPUT type='checkbox' name='sell' id="+(i+1)+"   value="+att_slno[i]+">");
          }
          
          else
          {  
        
           sel=document.createElement("input");     // serial number generation
           sel.type="checkbox";             
          
            sel.name="sell";
           sel.id=i+1;
           sel.value=att_slno[i];        
          
         
           //sel.setAttribute('onmouseover','checkEmployee(this,'+sel.id+')');
          // sel.setAttribute('onclick','checkEmp(this,'+sel.id+')');
           
          }
          
          cell1.appendChild(sel);
          mycurrent_row.appendChild(cell1);
          
          var cell2=document.createElement("TD");
          var txtattno=document.createTextNode(att_slno[i]);
          cell2.appendChild(txtattno);
          mycurrent_row.appendChild(cell2);
          
          var cell3=document.createElement("TD");
          cell3.setAttribute('align','left');
          var txtftitle=document.createTextNode(att_name[i]);
          cell3.appendChild(txtftitle);
          mycurrent_row.appendChild(cell3);
          
          tbody.appendChild(mycurrent_row);
          j++;
      
      }
    
    }
    
    else
    {
    
   // alert('comes here ...')
      var mycurrent_row=document.createElement("TR");
          mycurrent_row.id=0;
          
          var cell1=document.createElement("TD");
          var sel="";  
          
           if (window.navigator.appName.toLowerCase().indexOf("netscape") == -1)
          {
           
           sel=document.createElement("<INPUT type='checkbox' name='sell' id=0"+"   value="+att_slno[0]+">");
          // alert("111")
          // alert("sel value "+att_slno[0])
          }
          else
          {    
          //alert("222")
           sel=document.createElement("input");     // serial number generation
           sel.type="checkbox";             
          
            sel.name="sell";
           sel.id=0;
           sel.value=att_slno[0];
           //alert("sel value "+att_slno[0])
           //sel.setAttribute('onmouseover','checkEmployee(this,'+sel.id+')');
                                    
          }
          
          cell1.appendChild(sel);
          mycurrent_row.appendChild(cell1);
          
          var cell2=document.createElement("TD");
          var txtattno=document.createTextNode(att_slno[0]);
          cell2.appendChild(txtattno);
          mycurrent_row.appendChild(cell2);
          
          var cell3=document.createElement("TD");
          cell3.setAttribute('align','left');
          var txtftitle=document.createTextNode(att_name[0]);
          cell3.appendChild(txtftitle);
          mycurrent_row.appendChild(cell3);
          
          tbody.appendChild(mycurrent_row);
          j++;
    
    }

}


function loadFileUpload()
{
//alert("aaaaaaa")
var txtattachFile=document.frmCreateNews.txtattachFile.value;
var txtEventId=document.frmCreateNews.txtEventId.value;
var txtCaption=document.frmCreateNews.txtCaption.value;
var txtatt_slno=document.frmCreateNews.txtatt_slno.value;
//alert(txtattachFile);

document.frmCreateNews.action="../../../CreateAttachFileServ?txtattachFile="+txtattachFile+"&txtEventId="+txtEventId+"&txtCaption="+txtCaption+"&txtatt_slno="+txtatt_slno;
//alert(document.frmCreateNews.action);
document.frmCreateNews.method="Post";
document.frmCreateNews.submit();
}


function check()
{

var leng1=parseInt(document.frmCreateNews.sell.length);

if(leng1>0)
{

  var TotalBoxes = 0;
  var TotalOn = 0;
  for (var i=0;i<leng1;i++)
  {
    var e = document.frmCreateNews.sell[i];
   
    if ((e.type=='checkbox'))
    {
      TotalBoxes++;
      if (e.checked)
      {
       TotalOn++;
      }
    }
  }
  if(TotalOn==0)
  {
    
   
    return false;
  }
   return true;  
 }
 else
 {
 
 var e = document.frmCreateNews.sell;
   
    if ((e.type=='checkbox'))
    {
     
      if (e.checked==false)
      {
       
       alert('Select the File to be Deleted');
       return false;
      }
    }
   
 return true;
 }
  
   
}


