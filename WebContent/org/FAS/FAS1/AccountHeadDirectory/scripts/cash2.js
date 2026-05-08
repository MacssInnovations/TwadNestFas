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

function callMySelect2()
    {
         
          var sel=document.form2.acct_head_code.value;//------>first combobox name
          
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
     
     
     

function call2()
        {
            var sel=document.form2.sltype.options(document.form2.sltype.selectedIndex).text;//------>first combobox name
          
          var url="../../../../../ComboServlet2.view?sltype="+sel;
          
          req.open("Get",url,true);
          req.onreadystatechange=processSelect3;
          req.send(null);
        }
        
        function processSelect3()
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
        
        
        