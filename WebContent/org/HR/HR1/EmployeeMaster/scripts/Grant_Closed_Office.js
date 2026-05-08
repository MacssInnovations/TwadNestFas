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
         
          //document.Hrm_TransJoinForm.sno.value=rcells.item(1).firstChild.nodeValue;
          
          document.Hrm_TransJoinForm.date.value=rcells.item(4).firstChild.nodeValue;
          
          var officeid=rcells.item(1).firstChild.nodeValue;
          var userid=rcells.item(3).firstChild.nodeValue;
         var leng= document.Hrm_TransJoinForm.defanct_office.length;
        
         var ulength=document.Hrm_TransJoinForm.userid.length;
        
          for(var i=0;i<leng;i++)
          {
        	  if(document.getElementById("defanct_office").options[i].value==officeid)
        	  {
        		  document.getElementById("defanct_office").options[i].selected='selected';  
        	  }
          }
          
         /* for(var j=0;j<ulength;j++)
          {
        	  if(document.getElementById("userid").options[j].value==userid)
        	  {
        		  document.getElementById("userid").options[j].selected='selected';  
        	  }
          }*/
          document.Hrm_TransJoinForm.userid.value=userid;
          checkName();
          document.Hrm_TransJoinForm.defanct_office.disabled=true;
          document.Hrm_TransJoinForm.userid.disabled=true;
          
          document.Hrm_TransJoinForm.add.disabled=true;
        document.Hrm_TransJoinForm.update.disabled=false;
      //  document.Hrm_TransJoinForm.delete1.disabled=false;
        
          
      
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
                     while (tlist.childNodes.length > 0) {
                    	 tlist.removeChild(tlist.firstChild);
                     }

                     
                   
                    var len=response.getElementsByTagName("office_id").length;
                  
                    for(var i=0;i<len;i++)
                    {
                    var officeid=response.getElementsByTagName("office_id")[i].firstChild.nodeValue;
                    
                    var unitid=response.getElementsByTagName("unit")[i].firstChild.nodeValue;
                    
                    var userid=response.getElementsByTagName("userid")[i].firstChild.nodeValue;
                   
                    var date=response.getElementsByTagName("date")[i].firstChild.nodeValue;
                   
                    //var sno=response.getElementsByTagName("sno")[i].firstChild.nodeValue;
                    
                   var enabled=response.getElementsByTagName("enabled")[i].firstChild.nodeValue;
                    
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
                    
                  /*  var td1=document.createElement("TD");
                    var sn=document.createTextNode(sno);
                    td1.appendChild(sn);
                    tr.appendChild(td1); */    
                    
                    var td2=document.createElement("TD");
                    var office=document.createTextNode(officeid);
                    td2.appendChild(office);
                    tr.appendChild(td2);
                    
                    var td3=document.createElement("TD");
                    var unit=document.createTextNode(unitid);
                    td3.appendChild(unit);
                    tr.appendChild(td3);
                    
                    var td4=document.createElement("TD");
                    var user=document.createTextNode(userid);
                    td4.appendChild(user);
                    tr.appendChild(td4);
               
                    var td5=document.createElement("TD");
                    var dat=document.createTextNode(date);
                    td5.appendChild(dat);
                    tr.appendChild(td5);
                    
                    var td6=document.createElement("TD");
                    var enab=document.createTextNode(enabled);
                    td6.appendChild(enab);
                    tr.appendChild(td6);
                    
                    tlist.appendChild(tr);             
                    seq++;
                    }
                    
                  
                }
                else
                    {
                       
                    }
                document.Hrm_TransJoinForm.add.disabled=false;
                document.Hrm_TransJoinForm.update.disabled=true;
                
                
            }
            else if(command=="unit"){
            	 if(flag=='success')
                 {
                     
                     var unitid=response.getElementsByTagName("office_unit")[0].firstChild.nodeValue;
                       var officelevel=response.getElementsByTagName("office_level")[0].firstChild.nodeValue;
                        var address=response.getElementsByTagName("office_address")[0].firstChild.nodeValue;
                        var address1=response.getElementsByTagName("office_address1")[0].firstChild.nodeValue;
                        var city=response.getElementsByTagName("office_city")[0].firstChild.nodeValue;
                        var dist=response.getElementsByTagName("office_district")[0].firstChild.nodeValue;
                      if(city=="city")
                      {
                     	 city="";	 
                      }
                      if(dist=="dist")
                      {
                     	 dist="";	 
                      }	 
                     document.getElementById("unitid").value=unitid;
                     document.getElementById("officelevel").value=officelevel
                     document.getElementById("txtOffAddr").value=address+"\n"+address1+"\n"+city+"\n"+dist;
                 }
            }
            
            
           else if(command=="Add")
            {
                if(flag=='success')
                {
                	 var tlist=document.getElementById("tlist");
                	 while (tlist.childNodes.length > 0) {
                    	 tlist.removeChild(tlist.firstChild);
                     }

                     
                   
                   
                    var len=response.getElementsByTagName("office_id").length;
                  
                    for(var i=0;i<len;i++)
                    {
                    var officeid=response.getElementsByTagName("office_id")[i].firstChild.nodeValue;
                    
                    var unitid=response.getElementsByTagName("unit")[i].firstChild.nodeValue;
                    
                    var userid=response.getElementsByTagName("userid")[i].firstChild.nodeValue;
                   
                    var date=response.getElementsByTagName("date")[i].firstChild.nodeValue;
                   
                   // var sno=response.getElementsByTagName("sno")[i].firstChild.nodeValue;
                    
                    var enabled=response.getElementsByTagName("enabled")[i].firstChild.nodeValue;
                    
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
                    
                    /*var td1=document.createElement("TD");
                    var sn=document.createTextNode(sno);
                    td1.appendChild(sn);
                    tr.appendChild(td1);*/     
                    
                    var td2=document.createElement("TD");
                    var office=document.createTextNode(officeid);
                    td2.appendChild(office);
                    tr.appendChild(td2);
                    
                    var td3=document.createElement("TD");
                    var unit=document.createTextNode(unitid);
                    td3.appendChild(unit);
                    tr.appendChild(td3);
                    
                    var td4=document.createElement("TD");
                    var user=document.createTextNode(userid);
                    td4.appendChild(user);
                    tr.appendChild(td4);
               
                    var td5=document.createElement("TD");
                    var dat=document.createTextNode(date);
                    td5.appendChild(dat);
                    tr.appendChild(td5);
                    
                    var td6=document.createElement("TD");
                    var enab=document.createTextNode(enabled);
                    td6.appendChild(enab);
                    tr.appendChild(td6);
                    
                    tlist.appendChild(tr);             
                    seq++;
                    }
                    
                   
                     document.Hrm_TransJoinForm.update.disabled=true;
                    // document.Hrm_TransJoinForm.delete1.disabled=true;
                     alert("Added Successfully");
                     clear1();
                }
                else
                    alert("User Id already Grant for this office ");
            }
          else  if(command=="Update")
            {
                if(flag=='success')
                {
                    var tlist=document.getElementById("tlist");
                    
                    var officeid=response.getElementsByTagName("office_id")[0].firstChild.nodeValue;
                    
                    var unitid=response.getElementsByTagName("unit")[0].firstChild.nodeValue;
                    
                    var userid=response.getElementsByTagName("userid")[0].firstChild.nodeValue;
                   
                    var date=response.getElementsByTagName("date")[0].firstChild.nodeValue;
                   
                   
                    
                    var enabled=response.getElementsByTagName("enabled")[0].firstChild.nodeValue; 
                
                  var readCell=document.getElementById(common);
                  
                    var cells=readCell.cells;
                   
                    cells.item(1).firstChild.nodeValue=officeid;
                    cells.item(2).firstChild.nodeValue=unitid;
                    cells.item(3).firstChild.nodeValue=userid;
                    cells.item(4).firstChild.nodeValue=date;
                    cells.item(5).firstChild.nodeValue=enabled;
                    alert("Updated Successfully");
                    
                     document.Hrm_TransJoinForm.add.disabled=false;
                    document.Hrm_TransJoinForm.update.disabled=true;
                   
                    clear1();
                                       
                }
                else
                    alert("Failure in Update");
            }
          else if(command=="user"){
        	  if(flag=='success')
              {   
              }else{
            	  alert('User Id Doesnot exist');
            	  document.Hrm_TransJoinForm.userid.value="";
            	  document.Hrm_TransJoinForm.userid.focus();
              }
        	  
          }
          else if(command=="Delete")
            {
                if(flag=='success')
                {   
                	                       
                    var tlist=document.getElementById("tlist");
                  
                     
                     while (tlist.childNodes.length > 0) {
                    	 tlist.removeChild(tlist.firstChild);
                     }

                     
                    
                   
                    var len=response.getElementsByTagName("office_id").length;
                  
                    for(var i=0;i<len;i++)
                    {
                    var officeid=response.getElementsByTagName("office_id")[i].firstChild.nodeValue;
                    
                    var unitid=response.getElementsByTagName("unit")[i].firstChild.nodeValue;
                    
                    var userid=response.getElementsByTagName("userid")[i].firstChild.nodeValue;
                   
                    var date=response.getElementsByTagName("date")[i].firstChild.nodeValue;
                   
                   
                    
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
                    
                 /*   var td1=document.createElement("TD");
                    var sn=document.createTextNode(sno);
                    td1.appendChild(sn);
                    tr.appendChild(td1); */     
                    
                    var td2=document.createElement("TD");
                    var office=document.createTextNode(officeid);
                    td2.appendChild(office);
                    tr.appendChild(td2);
                    
                    var td3=document.createElement("TD");
                    var unit=document.createTextNode(unitid);
                    td3.appendChild(unit);
                    tr.appendChild(td3);
                    
                    var td4=document.createElement("TD");
                    var user=document.createTextNode(userid);
                    td4.appendChild(user);
                    tr.appendChild(td4);
               
                    var td5=document.createElement("TD");
                    var dat=document.createTextNode(date);
                    td5.appendChild(dat);
                    tr.appendChild(td5);
                    
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
                    document.Hrm_TransJoinForm.update.disabled=true;
                   
                    clear1();
                }
                else
                {
                    alert("Not success in Delete");
                    }
            
        }
        
        }
    }
           
}
function clear1()
{
	
	document.Hrm_TransJoinForm.defanct_office.options[0].selected=true;
	//document.Hrm_TransJoinForm.userid.options[0].selected=true;
	document.Hrm_TransJoinForm.date.value="";
	document.Hrm_TransJoinForm.unitid.value="";
	document.Hrm_TransJoinForm.officelevel.value="";
	document.Hrm_TransJoinForm.txtOffAddr.value="";
	document.Hrm_TransJoinForm.userid.value="";
	 document.Hrm_TransJoinForm.defanct_office.disabled=false;
     document.Hrm_TransJoinForm.userid.disabled=false;
	
           document.Hrm_TransJoinForm.add.disabled=false;
        document.Hrm_TransJoinForm.update.disabled=true;
       // document.Hrm_TransJoinForm.delete1.disabled=true;
          
      
      
           
        
}


function call(command)
{
xmlhttp=getxmlhttpObject();
if(xmlhttp==null)
{
    alert("Your borwser doesnot support AJAX");
    return;
    }   
    
        
        var officeid;
        var unit;
        var userid;
        var date;
        var sno;
       
        
        var url="";
        if(command=="Add")
        { 
                if(nullCheck())
                {
                
                officeid=document.getElementById("defanct_office").value;
                unit=document.getElementById("unitid").value;
                userid=document.getElementById("userid").value;
                date=document.getElementById("date").value;
                                    
        url="../../../../../Grant_Closed_Office?command=Add&officeid="+officeid+"&unit="+unit+"&userid="+userid+"&date="+date;
        url=url+"&sid="+Math.random();
        xmlhttp.open("GET",url,true);
        xmlhttp.onreadystatechange=stateChanged;
        xmlhttp.send(null);     
        }
    }
    else if(command=="Update")
    {
                if(nullCheck())
                {
                	officeid=document.getElementById("defanct_office").value;
                    unit=document.getElementById("unitid").value;
                    userid=document.getElementById("userid").value;
                    date=document.getElementById("date").value;
                    
        url="../../../../../Grant_Closed_Office?command=Update&officeid="+officeid+"&unit="+unit+"&userid="+userid+"&date="+date;
        url=url+"&sid="+Math.random();
        xmlhttp.open("GET",url,true);
        xmlhttp.onreadystatechange=stateChanged;
        xmlhttp.send(null);   
        }
    }
    else if(command=="Delete")
    {
    	
    var	officeid=document.getElementById("defanct_office").value;
    var userid=document.getElementById("userid").value;
    	
        url="../../../../../Grant_Closed_Office?command=Delete&officeid="+officeid+"&userid="+userid;
        url=url+"&sid="+Math.random();
        xmlhttp.open("GET",url,true);
        xmlhttp.onreadystatechange=stateChanged;
        xmlhttp.send(null);      
    }
  
    else if(command=="get")
    {   
        
        
       
        url="../../../../../Grant_Closed_Office?command=get";
        url=url+"&sid="+Math.random();
        xmlhttp.open("GET",url,true);
       xmlhttp.onreadystatechange=stateChanged;
        xmlhttp.send(null); 
    }
}
function nullCheck()
{
        if((document.Hrm_TransJoinForm.defanct_office.value=="--Select office Name--"))
        {
        alert("Must Choose Office Name");
        return 0;
        }
        else if((document.Hrm_TransJoinForm.userid.value==" "))
        {
        alert("Select User Login Id");
         return 0;
        }
              
        else if(document.Hrm_TransJoinForm.date.value=="")
        {
        alert("Select Date from Calendar");
         return 0;
        }
        
              
        
    return 1;

}



function checkName()
{
xmlhttp=getxmlhttpObject();
if(xmlhttp==null)
{
    alert("Your borwser doesnot support AJAX");
    return;
    }  
    var officeid=document.getElementById("defanct_office").value;
    if(officeid!="--Select office Name--"){
        url="../../../../../Grant_Closed_Office?command=unit&officeid="+officeid;
        url=url+"&sid="+Math.random();
        xmlhttp.open("GET",url,true);
        xmlhttp.onreadystatechange=stateChanged;
        xmlhttp.send(null);   
    }
}

function checkuser()
{
	xmlhttp=getxmlhttpObject();
	if(xmlhttp==null)
	{
	    alert("Your borwser doesnot support AJAX");
	    return;
	    }  
	    var userid=document.getElementById("userid").value;
	  if(userid!=""){
	        url="../../../../../Grant_Closed_Office?command=user&userid="+userid;
	        url=url+"&sid="+Math.random();
	        xmlhttp.open("GET",url,true);
	        xmlhttp.onreadystatechange=stateChanged;
	        xmlhttp.send(null);   
	  }
	   	
}



