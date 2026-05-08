var xmlhttp;var seq=0;var common="",mn,yr,v;
function Exit()
{
    window.close();
}




 function loadvalue(empid)
    {      
          common=empid;
          var emp_id=document.getElementById(empid); 
          var rcells=emp_id.cells;

          document.Hrm_TransJoinForm.w_id.value=rcells.item(1).firstChild.nodeValue;
          document.Hrm_TransJoinForm.w_desc.value=rcells.item(2).firstChild.nodeValue;
          
          document.Hrm_TransJoinForm.add.disabled=true;
        document.Hrm_TransJoinForm.update.disabled=false;
        document.Hrm_TransJoinForm.delete1.disabled=false;
        
          
      
    }
 
function getxmlhttpObject()
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
function stateChanged()
{
    var flag,command,response;
   
    if(xmlhttp.readyState==4)
    {
       if(xmlhttp.status==200)
       {
            response=xmlhttp.responseXML.getElementsByTagName("response")[0];
            command=response.getElementsByTagName("command")[0].firstChild.nodeValue;
            flag=response.getElementsByTagName("flag")[0].firstChild.nodeValue;
            if(command=="get")
            {
                if(flag=='success')
                {
                    var tlist=document.getElementById("tlist");
                     try{
                //tlist.innerText="";
                tlist.innerHTML="";
            
                      }
                  catch(e)
                  {
                tlist.innerText="";
                //tlist.innerHTML="";
               
                  }
                    
                       
                    var len=response.getElementsByTagName("w_id").length;
                   
                    for(var i=0;i<len;i++)
                    {
                    var w_id=response.getElementsByTagName("w_id")[i].firstChild.nodeValue;
                     var w_desc=response.getElementsByTagName("w_desc")[i].firstChild.nodeValue;
                                                    
                   var tr=document.createElement("TR");
                    tr.id=seq;
                    var td=document.createElement("TD");
                    var anc=document.createElement("A");
                    var url="javascript:loadvalue('"+seq+"')";
                    anc.href=url;
                    var edit=document.createTextNode("Edit");
                    anc.appendChild(edit);
                    td.appendChild(anc);
                    tr.appendChild(td);                    
         
                    var td1=document.createElement("TD");
                    var wid=document.createTextNode(w_id);
                    td1.appendChild(wid);
                    tr.appendChild(td1);
                    
                    var td2=document.createElement("TD");
                    var wdesc=document.createTextNode(w_desc);
                    td2.appendChild(wdesc);
                    tr.appendChild(td2);
                   
                 
                    tlist.appendChild(tr);             
                    seq++;
                    }
                }
                else
                    {
                     
                    document.getElementById("w_id").value="";
                    document.getElementById("w_desc").value="";
                    alert("Failure in loading values");
                    }
                    
                   
            }
           else if(command=="Add")
            {
                if(flag=='success')
                {
                   
                    var tlist=document.getElementById("tlist");
                   var w_id=response.getElementsByTagName("w_id")[0].firstChild.nodeValue;
                     var w_desc=response.getElementsByTagName("w_desc")[0].firstChild.nodeValue;
                   
                   var tr=document.createElement("TR");
                    tr.id=seq;
                    var td=document.createElement("TD");
                    var anc=document.createElement("A");
                    var url="javascript:loadvalue('"+seq+"')";
                    anc.href=url;
                    var edit=document.createTextNode("Edit");
                    anc.appendChild(edit);
                    td.appendChild(anc);
                    tr.appendChild(td);                    
         
                    var td1=document.createElement("TD");
                    var wid=document.createTextNode(w_id);
                    td1.appendChild(wid);
                    tr.appendChild(td1);
                    
                    var td2=document.createElement("TD");
                    var wdesc=document.createTextNode(w_desc);
                    td2.appendChild(wdesc);
                    tr.appendChild(td2);
                 
                    tlist.appendChild(tr);             
                    seq++;
                    
                   
                     document.Hrm_TransJoinForm.update.disabled=true;
                     document.Hrm_TransJoinForm.delete1.disabled=true;
                     alert("Added Successfully");
                      clear1();
                    
                }
                else if(flag=='same')
                      alert("Same data wont be allowed");
                    else
                      alert("Record not Saved");
                    
            }
          else  if(command=="Update")
            {
                if(flag=='success')
                {  
                     var tlist=document.getElementById("tlist");
                   var w_id=response.getElementsByTagName("w_id")[0].firstChild.nodeValue;
                     var w_desc=response.getElementsByTagName("w_desc")[0].firstChild.nodeValue;
                   
                    var readCell=document.getElementById(common);
                    var cells=readCell.cells;
                 
                    cells.item(1).firstChild.nodeValue=w_id;
                     cells.item(2).firstChild.nodeValue=w_desc;
                              
                    
                    alert("Updated Successfully");
                     clear1();
                    
                     document.Hrm_TransJoinForm.add.disabled=false;
                    document.Hrm_TransJoinForm.update.disabled=true;
                    document.Hrm_TransJoinForm.delete1.disabled=true;
                   
                                       
                }
                else
                    alert("Failure in Update");
            }
          else if(command=="Delete")
            {
                if(flag=='success')
                {   
                
                
                  
                    var tlist=document.getElementById("tlist");
                     try{
                          tist.innerHTML="";
                        }
                     catch(e)
                          {
                           tlist.innerText="";
                         }      
                    var len=response.getElementsByTagName("w_id").length;
                   
                    for(var i=0;i<len;i++)
                    {
                    var w_id=response.getElementsByTagName("w_id")[i].firstChild.nodeValue;
                     var w_desc=response.getElementsByTagName("w_desc")[i].firstChild.nodeValue;
                   
                   
                    var tr=document.createElement("TR");
                    tr.id=seq;
                    var td=document.createElement("TD");
                    var anc=document.createElement("A");
                    var url="javascript:loadvalue('"+seq+"')";
                    anc.href=url;
                    var edit=document.createTextNode("Edit");
                    anc.appendChild(edit);
                    td.appendChild(anc);
                    tr.appendChild(td);                    
         
                    var td1=document.createElement("TD");
                    var wid=document.createTextNode(w_id);
                    td1.appendChild(wid);
                    tr.appendChild(td1);
                    
                    var td2=document.createElement("TD");
                    var wdesc=document.createTextNode(w_desc);
                    td2.appendChild(wdesc);
                    tr.appendChild(td2);
                 
                    tlist.appendChild(tr);             
                    seq++;
                    }
                    
                    
                  /*  var tlist=document.getElementById("tlist");
                   
                    alert(common);
                    if(common==0)
                    {
                    var field=document.getElementById(common);
                    }
                    else
                    {
                    var field=document.getElementById(common-1);
                    }
                    var index=field.rowIndex;
                    tlist.deleteRow(index);         */
                    alert("Deleted Successfully");
                     clear1();
                    document.Hrm_TransJoinForm.update.disabled=true;
                    document.Hrm_TransJoinForm.delete1.disabled=true;   
                     document.Hrm_TransJoinForm.add.disabled=false;   
                    
                }
                else
                {
                    alert("Not success in Delete");
                    }
            
        }
        else if(command=="unit")
            {
                if(flag=='success')
                {
                   
                   var unit_id=response.getElementsByTagName("unit_id")[0].firstChild.nodeValue;
                                     
                    document.getElementById("oldcode").value=unit_id;
                    
                   
                }
                else
                {
                  
                    alert("problem in unit_id");
                }
            
        }
        }
    }
           
}
function clear1()
{
         document.Hrm_TransJoinForm.w_id.value="";
        document.Hrm_TransJoinForm.w_desc.value="";
        document.Hrm_TransJoinForm.w_id.focus();
         
      
}

function clearGPF()
{
          
        document.Hrm_TransJoinForm.w_id.value="";
        document.Hrm_TransJoinForm.w_desc.value="";
       
        document.Hrm_TransJoinForm.w_id.focus();
      
    /*    var tlist=document.getElementById("tlist");
        try{
                //tlist.innerText="";
                tlist.innerHTML="";
            
       }
    catch(e)
       {
                tlist.innerText="";
                //tlist.innerHTML="";
               
       }*/
       
        document.Hrm_TransJoinForm.add.disabled=false;
        document.Hrm_TransJoinForm.update.disabled=true;
        document.Hrm_TransJoinForm.delete1.disabled=true;
        
}
function call(command)
{
xmlhttp=getxmlhttpObject();
if(xmlhttp==null)
{
    alert("Your borwser doesnot support AJAX");
    return;
    }   
    
        
        var w_id,w_desc;
        
        var url="";
        if(command=="Add")
        { 
                nullCheck();

                w_id=document.getElementById("w_id").value;
                w_desc=document.getElementById("w_desc").value;
               
           
          url="../../../../../GPF_Withdrawl_Type.view?command=Add&w_id="+w_id+"&w_desc="+w_desc;    
        url=url+"&sid="+Math.random();
        xmlhttp.open("GET",url,true);
        xmlhttp.onreadystatechange=stateChanged;
        xmlhttp.send(null);       
    }
    else if(command=="Update")
    { 
                             w_id=document.getElementById("w_id").value;
                w_desc=document.getElementById("w_desc").value;

            
          url="../../../../../GPF_Withdrawl_Type.view?command=Update&w_id="+w_id+"&w_desc="+w_desc;    
        url=url+"&sid="+Math.random();
        xmlhttp.open("GET",url,true);
        xmlhttp.onreadystatechange=stateChanged;
        xmlhttp.send(null); 
    }
    else if(command=="Delete")
    {                            w_id=document.getElementById("w_id").value;
                w_desc=document.getElementById("w_desc").value;

            
          url="../../../../../GPF_Withdrawl_Type.view?command=Delete&w_id="+w_id;    
        url=url+"&sid="+Math.random();
        xmlhttp.open("GET",url,true);
        xmlhttp.onreadystatechange=stateChanged;
        xmlhttp.send(null); 
    }
   
    else if(command=="get")
    {   
            document.Hrm_TransJoinForm.w_id.focus();
        url="../../../../../GPF_Withdrawl_Type.view?command=get";
        url=url+"&sid="+Math.random();
        xmlhttp.open("GET",url,true);
       xmlhttp.onreadystatechange=stateChanged;
        xmlhttp.send(null); 
    }
}
function nullCheck()
{
if((document.Hrm_TransJoinForm.w_id.value==""))
{
alert("Enter the Withdrawl Id ");
}

else if(document.Hrm_TransJoinForm.w_desc.value=="")
{
alert("Enter the withdrawl description");
}

}


function checkName()
{
xmlhttp=getxmlhttpObject();
if(xmlhttp==null)
{
    alert("Your borwser doesnot support AJAX");
    return;
    }  
    var unit_name=document.getElementById("unit_name").value;
    
        url="../../../../../GPF_Subscription.view?command=unit&unit_name="+unit_name;
        url=url+"&sid="+Math.random();
        xmlhttp.open("GET",url,true);
        xmlhttp.onreadystatechange=stateChanged;
        xmlhttp.send(null);   
}