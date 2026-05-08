
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


function callMySelect()
{
  
  alert("yes");
  var sel=document.form1.major_grp_code.value;//------>first combobox name
  var url="../../../../../ComboServlet4.view?mgc="+sel;
  alert(url);
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
      var sele=req.responseXML.getElementsByTagName("select")[0];
      var options=sele.getElementsByTagName("option");
     
      for(i=0;i<options.length;i++)
          {
          var op=options[i];
          var valu=op.firstChild.nodeValue;
          var htoption=document.createElement("OPTION");//--------->DOM object created*****important
          htoption.text=valu;
          htoption.value=valu;//not required
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
