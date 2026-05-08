
var req = false;
 var req2 = false;
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


function callMySelect()
    {
         
          var sel=document.cash_receipt.acct_head_code.value;//------>first combobox name
          
          var url="../../../../../ComboServlet3.view?ahc="+sel;
         
          req.open("Get",url,true);
          req.onreadystatechange=processSelect;
          req.send(null);
     }  
     
     
     
  
function processSelect()
        {
          if(req.readystate==4)
          {
            if(req.status==200)
              {
              var i;
              var first=document.getElementById("sltype");//-----> second combobox name
              first.innerHTML="";
              var sele=req.responseXML.getElementsByTagName("select")[0];
              var options=sele.getElementsByTagName("option");
             
              for(i=0;i<options.length;i++)
                  {
                  var code=options[i].getElementsByTagName("code")[0].firstChild.nodeValue;
                  
                  var desc=options[i].getElementsByTagName("desc")[0].firstChild.nodeValue; 
                
                  var htoption=document.createElement("OPTION");//--------->DOM object created*****important
                  htoption.text=desc;
                  htoption.value=code;
                  first.add(htoption);

                  }
              }
           }
        }   
     
     
     
     function call()
    {
         
         
          var sel=document.cash_receipt.sltype.options(document.cash_receipt.sltype.selectedIndex).text;//------>first combobox name
          
          var url="../../../../../ComboServlet2.view?sltype="+sel;
         
          req.open("Get",url,true);
          req.onreadystatechange=processSelect2;
          req.send(null);
     }  
     
     function processSelect2()
        {
          if(req.readystate==4)
          {
            if(req.status==200)
              {
              var i;
              var first=document.getElementById("slcode");//-----> second combobox name
              first.innerHTML="";
              var sele=req.responseXML.getElementsByTagName("select")[0];
              var options=sele.getElementsByTagName("option");
             
              for(i=0;i<options.length;i++)
                  {
                  var code=options[i].getElementsByTagName("code")[0].firstChild.nodeValue;
                  
                  var desc=options[i].getElementsByTagName("desc")[0].firstChild.nodeValue; 
                
                  var htoption=document.createElement("OPTION");//--------->DOM object created*****important
                  htoption.text=desc;
                  htoption.value=code;
                  first.add(htoption);
                  }
              }
           }
        }   
        
   
function popWindow()
  {
  var off=document.cash_receipt.selOff.value;
  var url="../jsps/office.jsp?office="+off;
  var wind=window.open(url,"myWindow","status=1,height=200,width=200");
  wind.moveTo(100,250);
  }
     
 function setNext()
 {
   document.cash_receipt.received_from.value=document.cash_receipt.slcode.options(document.cash_receipt.slcode.selectedIndex).text;
 }
        

function RecNoGeneration()
{
          var current_rec_date=document.cash_receipt.date_creation.value;//------>first combobox name
          //alert(current_rec_date);
          var month=today.getMonth()+1;
          alert(month);
          var url="../../../../../RecNoServlet.view?crd="+current_rec_date;
          alert(url);
          req.open("Get",url,true);
          req.onreadystatechange=NumGenerated;
          req.send(null);
}

function NumGenerated()
{
  if(req.readystate==4)
          {
            if(req.status==200)
              {
                 
              }
           }   
}