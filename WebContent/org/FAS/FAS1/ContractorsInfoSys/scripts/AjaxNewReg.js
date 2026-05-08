var req = false;
 
var rad_val1="";
var rad_val2="";
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
 
 //function to Generate the Class 
  function getClass()
  {
  
     
      var offid=document.frmNewRegn.txtOffId.value;
      var url="";
      url="../../../../../newRegnServlet.view?command=Class&offid="+offid;
      req.open("GET",url,true);    
      req.onreadystatechange=gcRes;
      req.send(null);
   }
 // XMl response returned by servlet
  function gcRes()
  {
    
    if(req.readyState==4)
        {
          if(req.status==200)
          {              
              
              var baseResponse=req.responseXML.getElementsByTagName("response")[0];
              
              //var tagCommand=baseResponse.getElementsByTagName("command")[0];
              var nextRes=baseResponse.getElementsByTagName("offlevel")[0];
             
              var offlevel=nextRes.firstChild.nodeValue;  
              
              if(offlevel=="CL")
              {
               var id= document.getElementById("txtClass"); 
              id.innerHTML="";
              var comboVal="Select Here";
              var option=document.createElement("OPTION");
              option.text=comboVal; 
              id.add(option);
              
              var comboValue="Class-I";
              var option=document.createElement("OPTION");
              option.text=comboValue;
              option.value="1";
              id.add(option);
              var comboValue2="Class-II";
              var comboValue3="Class-III";
              var option2=document.createElement("OPTION");
              option2.text=comboValue2;
              option2.value="2";
              id.add(option2);
              var option3=document.createElement("OPTION");
              option3.text=comboValue3;
              option3.value="3";
              id.add(option3);
              
          
              }
              else
              {
              var id= document.getElementById("txtClass"); 
              id.innerHTML="";
              
              var comboVal="Select Here";
              var option=document.createElement("OPTION");
              option.text=comboVal; 
              id.add(option);
              var comboValue="Class-IV";
              var option=document.createElement("OPTION");
              option.text=comboValue;
              option.value="4";
              id.add(option);
              var comboValue2="Class-V";              
              var option2=document.createElement("OPTION");
              option2.text=comboValue2;
              option2.value="5";
              id.add(option2);
              }
              
            }
            
          }
   
  }
 //funtion to Generate the Registration Fees on Clicking the Repective class 
  function getFees()
  {
      //alert("inside getfees");
      var classid=document.frmNewRegn.txtClass.options(document.frmNewRegn.txtClass.selectedIndex).value;
      //alert(classid);
      if(document.frmNewRegn.State[0].checked)
      {
      var state=document.frmNewRegn.State[0].value;
      } 
      else
      {
       var state=document.frmNewRegn.State[1].value;
      }
      var url="";
      url="../../../../../newRegnServlet.view?command=Fees&classid="+classid+"&state="+state;
      //alert(url);
      req.open("GET",url,true);    
      req.onreadystatechange=feesRes;
      req.send(null);
      
  }
  
  function feesRes()
  {
    if(req.readyState==4)
        {
          if(req.status==200)
          {              
              
              var baseResponse=req.responseXML.getElementsByTagName("response")[0];
              //alert(req.responseText);
              //alert(baseResponse);
              var nextRes=baseResponse.getElementsByTagName("fees")[0];
              //alert(nextRes);
              var fees=nextRes.firstChild.nodeValue;  
              //alert(fees);
              document.frmNewRegn.txtRegn_Fees.value=fees;
              
               }
           }
  }
  
 
   
    
    

            
            
            
            
             
            
            
            
            
            
          
    
  
  
  
  
  
