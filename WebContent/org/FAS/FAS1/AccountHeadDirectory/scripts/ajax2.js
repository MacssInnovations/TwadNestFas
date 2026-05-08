////
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
  
          var sel=document.form1.major_grp_code.value;//------>first combobox name
          var url="../../../../../ComboServlet.view?mgc="+sel;
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
              var first=document.getElementById("minor_grp_code");//-----> second combobox name
              first.innerHTML="";
              //alert(req.responseText);
              var sele1=req.responseXML.getElementsByTagName("select")[0];
              //alert("sel:"+sele1);
              var options1=sele1.getElementsByTagName("option");
              //alert("options"+options1);
              for(i=0;i<options1.length;i++)
                  {
                  var code=options1[i].getElementsByTagName("code")[0].firstChild.nodeValue;
                  
                  var desc=options1[i].getElementsByTagName("desc")[0].firstChild.nodeValue; 
                
                  var htoption=document.createElement("OPTION");//--------->DOM object created*****important
                  htoption.text=desc;
                  htoption.value=code;
                  first.add(htoption);
                  }
              }
           }
        }   
     
        
function displaysubcode()
    {
      document.form1.txt_sltypeCode.value=document.form1.txt_sldesc.value;
    }


function displaysubcode2()
    {
      document.form2.txt_sltypeCode.value=document.form2.txt_sldesc.value;
    }

    function verify()
    {
              
               
               try 
               {
                     req2= new ActiveXObject("Msxml2.XMLHTTP");
               }
               catch (e) 
               {
                     try 
                     {
                          req2 = new ActiveXObject("Microsoft.XMLHTTP");
                     }
                     catch (e2) 
                     {
                          req2 = false;
                     }
               }
               if (!req2 && typeof XMLHttpRequest != 'undefined') 
               {
                     req2 = new XMLHttpRequest();
               }  
               
               var strAcct_Head_Code=document.form1.acct_head_code.value;
               
               url="../../../../../ValidationServlet.view?command=verify&code=" + strAcct_Head_Code;
               
               req2.open("GET",url,true);        
               req2.onreadystatechange=verifyResponse;
               req2.send(null);
 
 
   }

  
  function verifyResponse()
  {
       if(req2.readyState==4)
        {
          if(req2.status==200)
          {        
            
              var baseResponse=req2.responseXML.getElementsByTagName("response")[0];
              
              var tagCommand=baseResponse.getElementsByTagName("command")[0];
              
              var command=tagCommand.firstChild.nodeValue; 
              
              verifyRow(baseResponse);
              
              
              
          }
          
      }    
  }

function verifyRow(baseResponse)
{
   
   var flag=baseResponse.getElementsByTagName("flag")[0].firstChild.nodeValue;
       if(flag=="success")
       {                                        
          alert("The record already exists. Please enter a new code");
          document.form1.acct_head_code.value="";
          document.form1.acct_head_code.focus();
       } 
}