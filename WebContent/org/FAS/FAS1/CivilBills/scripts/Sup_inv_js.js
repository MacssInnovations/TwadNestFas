var seq=0;
var __pagination=5;
var totalblock=0;
function AjaxFunction()
    {
        var xmlrequest=false;
        try
            {
               xmlrequest=new ActiveXObject("Msxml2.XMLHTTP"); 
            }
         catch(e1)
          {
                 try
                 {
                     xmlrequest=new ActiveXObject("Microsoft.XMLHTTP"); 
                 }
                 catch(e2)
                 {     
                     xmlrequest=false;
                 }
          }
          if (!xmlrequest && typeof XMLHttpRequest != 'undefined') 
                {
                     xmlrequest=new XMLHttpRequest();
                }
        return xmlrequest;
    }

function clearAll()
{
	
    document.forms[0].billmajortype.value="";
    document.forms[0].billminortype.value="";
    document.forms[0].billsubtype.value="";
    document.forms[0].supportinv[0].checked=true;
    document.forms[0].sno.value="";
    document.getElementById('billminortype').selectedIndex=0;
    document.getElementById('billsubtype').selectedIndex=0;
    document.getElementById('billminortype').length=1;
    document.getElementById('billsubtype').length=1;
     document.forms[0].onadd.disabled=false;
  	 document.forms[0].onedit.disabled=true;
  	 document.forms[0].ondelete.disabled=true;
}
function nullCheck()
{
	
	var unitid=document.forms[0].cmbAcc_UnitCode.value;
    var offid=document.forms[0].cmbOffice_code.value;
    var major=document.forms[0].billmajortype.value;
    var minor=document.forms[0].billminortype.value;
	if(unitid=="")
    {
   	 	alert("Select Unit Code");
        document.forms[0].cmbAcc_UnitCode.focus();
        return false;
    }
    if(offid=="")
    {
           alert("Select Office Code");
           document.forms[0].cmbOffice_code.focus();
           return false;
    }
   if(major=="")
    {
           alert("Select BillMajorType");
           document.forms[0].billmajortype.focus();
           return false;
    } 
   if(minor=="")
   {
          alert("Enter checkDesc");
          document.forms[0].billminortype.focus();
          return false;
   }  
	 return true;
}
function callmajorType()
{
        var url="../../../../../Sup_inv_serv?command=majorType";
        var xmlrequest= AjaxFunction();
        xmlrequest.open("GET",url,true);              
        xmlrequest.onreadystatechange=function()
        {
            manipulate(xmlrequest);
        };
        xmlrequest.send(null);
}

function callminor()
{
        var major=document.forms[0].billmajortype.value;
        var url="../../../../../Sup_inv_serv?command=minorType&major="+major;
        var xmlrequest= AjaxFunction();
        xmlrequest.open("GET",url,true);              
        xmlrequest.onreadystatechange=function()
        {
            manipulate(xmlrequest);
        }
        xmlrequest.send(null);     
}
function  callsub(minor)
{
		var major=document.forms[0].billmajortype.value;
        var url="../../../../../Sup_inv_serv?command=subType&minor="+minor+"&major="+major;
        var xmlrequest= AjaxFunction();
        xmlrequest.open("GET",url,true);              
        xmlrequest.onreadystatechange=function()
        {
            manipulate(xmlrequest);
        };
        xmlrequest.send(null);     
}

function callServer(command,param)
{
	 var url="";
    if(command=="Add")
       {           
    	    var unitid=document.forms[0].cmbAcc_UnitCode.value;
    	    var offid=document.forms[0].cmbOffice_code.value;
    	    var major=document.forms[0].billmajortype.value;
    	    var minor=document.forms[0].billminortype.value;
    	    var sub=document.forms[0].billsubtype.value;
    	    var support;
    	    if(document.forms[0].supportinv[0].checked==true)
    	        {
    	    		support="Y";
    	        }
    	    else
    	        {
    	    		support="N";
    	        }
    	      var flag=nullCheck();
			  if(flag==true)
               {
            	   var xmlrequest= AjaxFunction();
                   url="../../../../../Sup_inv_serv?command=add&unitid="+unitid+"&offid="+offid+"&major="+major+"&minor="+minor+"&sub="+sub+"&support="+support;
                   xmlrequest.open("GET",url,true);              
                   xmlrequest.onreadystatechange=function()
                       {
                           manipulate(xmlrequest);
                       }
                   xmlrequest.send(null);
               }
                  
                   
       }
       else if(command=="Update")
       {
			    	var unitid=document.forms[0].cmbAcc_UnitCode.value;
			   	    var offid=document.forms[0].cmbOffice_code.value;
			   	    var major=document.forms[0].billmajortype.value;
			   	    var minor=document.forms[0].billminortype.value;
			   	    var sub=document.forms[0].billsubtype.value;
			   	    var support;
			   	    if(document.forms[0].supportinv[0].checked==true)
			   	        {
			   	    		support="Y";
			   	        }
			   	    else
			   	        {
			   	    		support="N";
			   	        }
                   var flag=nullCheck();
                   if(flag==true)
                   {
                	url="../../../../../Sup_inv_serv?command=updated&unitid="+unitid+"&offid="+offid+"&major="+major+"&minor="+minor+"&sub="+sub+"&support="+support;
                	
                   var xmlrequest= AjaxFunction();
                   xmlrequest.open("GET",url,true);              
                   xmlrequest.onreadystatechange=function()
                   {
                       manipulate(xmlrequest);
                   }
                   xmlrequest.send(null);
                   }

       }
       
       else if(command=="Delete")
       {  
	    	    var unitid=document.forms[0].cmbAcc_UnitCode.value;
		   	    var offid=document.forms[0].cmbOffice_code.value;
		   	    var major=document.forms[0].billmajortype.value;
		   	    var minor=document.forms[0].billminortype.value;
		   	    var sub=document.forms[0].billsubtype.value;
			   	url="../../../../../Sup_inv_serv?command=deleted&unitid="+unitid+"&offid="+offid+"&major="+major+"&minor="+minor+"&sub="+sub+"&support="+support;
    	   		
			   	var xmlrequest= AjaxFunction();
    	        xmlrequest.open("GET",url,true);              
    	        xmlrequest.onreadystatechange=function()
    	        {
    	            manipulate(xmlrequest);
    	        }
    	        xmlrequest.send(null);
       }   
       
}


function  manipulate(xmlrequest)
{
if(xmlrequest.readyState==4)
  {
      if(xmlrequest.status==200)
      {
           var baseResponse=xmlrequest.responseXML.getElementsByTagName("response")[0];
          
           var tagCommand=baseResponse.getElementsByTagName("command")[0]; 
        
           var command=tagCommand.firstChild.nodeValue;
           
           if(command=="add")
           {
        	   	 addRow(baseResponse);
        	  // 	 alert("Record Inserted successfully.");
        	     clearAll();
           }
           else if(command=="major")
           {
                majortypechecking(baseResponse);
           }
           else if(command=="minor")
           {
               minortypechecking(baseResponse);
           }
           else if(command=="subb")
           {
               subtypechecking(baseResponse);
           }
           else if(command=="updated")
           {
        	   updateRow(baseResponse);
        	   clearAll();
           }
           else if(command=="deleted")
           { 
        	   deleteRow1(baseResponse);
        	   clearAll();
           }
           else if(command=="Get")
           { 
        	   getRow(baseResponse);
           }
          
           else if(command=="listminor")
           { 
        	   listminorRow(baseResponse);
           }
           else if(command=="listSub")
           { 
        	   listSubRow(baseResponse);
           }
      }
  }
}
function majortypechecking(baseResponse)
{
         var billcombo = document.forms[0].billmajortype;
         var mastercode = baseResponse.getElementsByTagName("mastercode"); 
         var masterdesc = baseResponse.getElementsByTagName("masterdesc");   
         for(var i=0; i<mastercode.length; i++)
             {
                 var opt = document.createElement('option');
                 opt.value = mastercode[i].firstChild.nodeValue;
                 opt.innerHTML = masterdesc[i].firstChild.nodeValue; //instead of using textnode ,we use innerhtml
                 billcombo.appendChild(opt);
             }
}

function minortypechecking(baseResponse)
{
		 var minorcmb = document.forms[0].billminortype;
         document.forms[0].billminortype.length=0;
         var minorcode = baseResponse.getElementsByTagName("minorcode");  
         var minordesc = baseResponse.getElementsByTagName("minordesc"); 
         for(var i=0; i<minorcode.length; i++)
           {
	        	 	if(minorcode.length==1)
			       	  {
			       		 var opt1 = document.createElement('option');
		                 opt1.value = 0;
		                 opt1.innerHTML ="select"; 
		                 minorcmb.appendChild(opt1);
			       		  
			       	  }
            	     var opt1 = document.createElement('option');
                     opt1.value = minorcode[i].firstChild.nodeValue;
                     opt1.innerHTML = minordesc[i].firstChild.nodeValue; 
                     minorcmb.appendChild(opt1);
                 
           }    
}
function subtypechecking(baseResponse)
{
		  var subcmb = document.forms[0].billsubtype;
          document.forms[0].billsubtype.length=0;
          var subcode = baseResponse.getElementsByTagName("subcode"); 
          var subdesc = baseResponse.getElementsByTagName("subdesc");
          var opt1 = document.createElement('option');
          opt1.value ="select"; 
          opt1.innerHTML="select";
          subcmb.appendChild(opt1);
          for(var i=0; i<subcode.length; i++)
               {
        	         opt1 = document.createElement('option');
                     opt1.value = subcode[i].firstChild.nodeValue;
                     opt1.innerHTML = subdesc[i].firstChild.nodeValue; 
                     subcmb.appendChild(opt1);
               }  
}
function addRow(baseResponse){
	var flag=baseResponse.getElementsByTagName("flag")[0].firstChild.nodeValue;	
    if(flag=="success"){          
    	alert("Data added successfully");
    }else{
    	alert("Data Not added");
    }
    loadData();
}
function updateRow(baseResponse){
	var flag=baseResponse.getElementsByTagName("flag")[0].firstChild.nodeValue;	
   if(flag=="success"){   
	   alert("Data updated successfully");
    }else{
    	alert("Data not updated");
    }
   loadData();
}


function getRow(baseResponse)
{
	var flag=baseResponse.getElementsByTagName("flag")[0].firstChild.nodeValue; 
	record1=new Array();record2=new Array();
    record3=new Array();record4=new Array();
    record5=new Array();record6=new Array();
    record7=new Array();record8=new Array();
    //alert("afterrrrr");
    if(flag=="success")
    {          
              var display=baseResponse.getElementsByTagName("display");   
              //alert("display"+display.length);
              for(k=0;k<display.length;k++)
              {

            	  record1[k]=display[k].getElementsByTagName("majorCode")[0].firstChild.nodeValue;
                  record2[k]=display[k].getElementsByTagName("majorDesc")[0].firstChild.nodeValue;                                
                  record3[k]=display[k].getElementsByTagName("minorCode")[0].firstChild.nodeValue;
                  record4[k]=display[k].getElementsByTagName("minorDesc")[0].firstChild.nodeValue;                                
                  record5[k]=display[k].getElementsByTagName("subCode")[0].firstChild.nodeValue;
                  record6[k]=display[k].getElementsByTagName("subDesc")[0].firstChild.nodeValue;                                
                  record7[k]=display[k].getElementsByTagName("support")[0].firstChild.nodeValue;
                  record8[k]=display[k].getElementsByTagName("status")[0].firstChild.nodeValue;
            	
              }
              //alert("ffffff");
              totalblock=0;
              if(record1.length>0)
              {//alert("length");
                  totalblock=parseInt(record1.length/__pagination);
                  if(record1.length%__pagination!=0)
                  {
                      totalblock=totalblock+1;
                  }
                  var cmbpage=document.getElementById("cmbpage");
                  try
                  { 
                      cmbpage.innerHTML="";
                  }
                  catch(e){
                       cmbpage.innerText="";
                  }
                  //alert("totalblock"+totalblock);
                  for(i=1;i<=totalblock;i++)
                  {
                      var option=document.createElement("OPTION");
                      option.text=i;
                      option.value=i;
                      try
                      {
                      cmbpage.add(option);
                      }
                      catch(errorObject)
                      {
                      cmbpage.add(option,null);
                      }
                  }  
            
                  loadGrid(1);
              }
    }
    else
    {
 	   var tbody=document.getElementById("tblList");
 	   try{tbody.innerHTML="";
 	   }catch(e) {tbody.innerText="";}
    }
                
}

function deleteRow1(baseResponse)
{
                var flag=baseResponse.getElementsByTagName("flag")[0].firstChild.nodeValue;
                if(flag=="success")
                {
                 
                //var tbody=document.getElementById("Existing");     
                   var tbody=document.getElementById("tblList");
                                   
                   // var r=document.getElementById(sno); 
                   //var r=document.getElementById("sno"); 
                 
                   //var ri=r.rowIndex;               
                  // tbody.deleteRow(ri);
                   loadData();
                   alert("Cancel Successfully");
                   clearAll();
                  // changepage();
                }
                else 
                {
                    alert("Failed to delete");
                }

}
function loadValuesFromTable(seq,record1,record2,record3,record4,record5,record6,record7)
{
	 clearAll();
	 sequenceno=seq;
    var r=document.getElementById(sequenceno);
    var rcells=r.cells;
    var tbody=document.getElementById("tblList");
    var table=document.getElementById("Existing");
    document.forms[0].sno.value=seq;
    var m1=record1;
    var m2=record3;
    var m3=record5;
    document.forms[0].billmajortype.value=m1;
    
    
    listMinor(m1,m2);
    listSub(m1,m2,m3);
    var rad1=record7;
    if(rad1=="Y")
    { 
   	 document.forms[0].supportinv[0].checked=true;
    }
    else
        document.forms[0].supportinv[1].checked=true;
 
     document.forms[0].onadd.disabled=true;
	 document.forms[0].onedit.disabled=false;
	 document.forms[0].ondelete.disabled=false;
    
}

function listMinor(major,minor)
{
	var url="../../../../../Sup_inv_serv?command=listminor&major="+major+"&minor="+minor;
    var xmlrequest= AjaxFunction();
    xmlrequest.open("GET",url,true);              
    xmlrequest.onreadystatechange=function()
    {
        manipulate(xmlrequest);
    }
    xmlrequest.send(null);  
}
function listminorRow(baseResponse)
{
	 var minorcmb = document.forms[0].billminortype;
     document.forms[0].billminortype.length=0;
     var minorcode = baseResponse.getElementsByTagName("minorcode");  
     var minordesc = baseResponse.getElementsByTagName("minordesc"); 
     for(var i=0; i<minorcode.length; i++)
       {
    	 		var opt1 = document.createElement('option');
                 opt1.value = minorcode[i].firstChild.nodeValue;
                 opt1.innerHTML = minordesc[i].firstChild.nodeValue; 
                 minorcmb.appendChild(opt1);
             
       }    
}
function listSub(major,minor,sub)
{
	var url="../../../../../Sup_inv_serv?command=listSub&major="+major+"&minor="+minor+"&sub="+sub;
    var xmlrequest= AjaxFunction();
    xmlrequest.open("GET",url,true);              
    xmlrequest.onreadystatechange=function()
    {
        manipulate(xmlrequest);
    }
    xmlrequest.send(null);  
}
function listSubRow(baseResponse)
{
	 var subcmb = document.forms[0].billsubtype;
     document.forms[0].billsubtype.length=0;
     var subcode = baseResponse.getElementsByTagName("subcode");  
     var subdesc = baseResponse.getElementsByTagName("subdesc"); 
     for(var i=0; i<subcode.length; i++)
       {
    	 		var opt1 = document.createElement('option');
                 opt1.value = subcode[i].firstChild.nodeValue;
                 opt1.innerHTML = subdesc[i].firstChild.nodeValue; 
                 subcmb.appendChild(opt1);
             
       }    
}

function changepagesize()
{//alert("changepagesize");
    __pagination=document.forms[0].cmbpagination.value;
    
        totalblock=0;
        if(record1.length>0)
        {
            totalblock=parseInt(record1.length/__pagination);
            if(record1.length%__pagination!=0)
            {
                totalblock=totalblock+1;
            }
            var cmbpage=document.getElementById("cmbpage");
            try
            {
                cmbpage.innerHTML="";
            }
            catch(e)
            {
                cmbpage.innerText="";
            }
            for(i=1;i<=totalblock;i++)
            {
                var option=document.createElement("OPTION");
                option.text=i;
                option.value=i;
                try
                {
                    cmbpage.add(option);
                }
                catch(errorObject)
                {
                    cmbpage.add(option,null);
                }
            } 
        }
        loadGrid(1);
   
}

function loadData()
{//alert("load data");
	url="../../../../../Sup_inv_serv?command=Get";
	var xmlrequest= AjaxFunction();
    xmlrequest.open("GET",url,true);              
    xmlrequest.onreadystatechange=function()
    {
        manipulate(xmlrequest);
    };
    xmlrequest.send(null);
}

function changepage()
{
    var page=document.forms[0].cmbpage.value;
    loadGrid(parseInt(page));

}
function loadGrid(page)
{//alert("page"+page);
    var i=0;
    var c=0;    
    var p=__pagination*(page-1);
    var sno=0;
    var tbody=document.getElementById("tblList");
    try{tbody.innerHTML="";}
    catch(e) {tbody.innerText="";}
    document.forms[0].cmbpage.selectedIndex=page-1;
    for(i=p;i<record1.length && c<__pagination;i++)
    {
            c++;
            sno++;
            var cell2;
            seq++;

            var mycurrent_row=document.createElement("TR");
            mycurrent_row.id=seq;
            
            var cell1=document.createElement("TD"); 
            cell2=document.createElement("TD");
            if(record8[i]=='C'){                                                            	
            	var priceSpan = document.createElement("span");
    			priceSpan.setAttribute('style','font-weight:bold; color:green; font-family:Verdana');
    			priceSpan.appendChild(document.createTextNode("Cancel"));			
    			cell2.appendChild(priceSpan);                                                    			
            }else{
            	cell2.setAttribute('align','left');
                var anc=document.createElement("A");  
                var url="javascript:loadValuesFromTable('"+seq+"','"+record1[i]+"','"+record2[i]+"','"+record3[i]+"','"+record4[i]+"','"+record5[i]+"','"+record6[i]+"','"+record7[i]+"','"+record8[i]+"')";    
                anc.href=url;
                var txtedit=document.createTextNode("Edit");
                anc.appendChild(txtedit);
                cell2.appendChild(anc);
            }                                                         
            mycurrent_row.appendChild(cell2);
           
            cell2=document.createElement("TD");
            cell2.setAttribute('align','left');
            var mcode=document.createElement("input");
            mcode.type="hidden";
            mcode.name="majorcode";
            mcode.value=record1[i];
            cell2.appendChild(mcode);
            var slnos=document.createElement("input");
            slnos.type="hidden";
            slnos.name="seqNo";
            slnos.value=seq;      							//middle child
            cell2.appendChild(slnos);      
            var currentText=document.createTextNode(record2[i]);
            cell2.appendChild(currentText);
            mycurrent_row.appendChild(cell2);

            cell2=document.createElement("TD");
            cell2.setAttribute('align','left');
            var micode=document.createElement("input");
            micode.type="hidden";
            micode.name="minorcode";
            micode.value=record3[i];
            cell2.appendChild(micode);
            var currentText=document.createTextNode(record4[i]);
            cell2.appendChild(currentText);
            mycurrent_row.appendChild(cell2);
            
            cell2=document.createElement("TD");
            cell2.setAttribute('align','left');
            var scode=document.createElement("input");
            scode.type="hidden";
            scode.name="subcode";
            scode.value=record5[i];
            cell2.appendChild(scode);
            var currentText=document.createTextNode(record6[i]);
            cell2.appendChild(currentText);
            mycurrent_row.appendChild(cell2);
           
            cell2 =document.createElement("TD");  
            cell2.setAttribute('align','left');
            var notapply=document.createTextNode(record7[i]);                         
            cell2.appendChild(notapply);       
            mycurrent_row.appendChild(cell2);
            
            var td5 = document.createElement("TD");
    		if(record8[i]=="C"){
    			var tdst = document.createTextNode("CANCEL");
    		}else{
    			var tdst = document.createTextNode("LIVE");
    		}
    		td5.appendChild(tdst);
    		mycurrent_row.appendChild(td5);
            
            tbody.appendChild(mycurrent_row);

            
    }
    /*This Part Is Used To Move The Next Page Or The Previous Page In The Grid*/
    
    var cell=document.getElementById("divcmbpage");
    cell.style.display="block";
    var cell=document.getElementById("divpage");
    cell.style.display="block";
    try
    {cell.innerHTML='/'+totalblock;
    }
    catch(e){cell.innerText='/'+totalblock;
    }
    if(page<totalblock)
    {
        var cell=document.getElementById("divnext");
        cell.style.display="block";
        try
        {
            cell.innerHTML="";
        }
        catch(e)
        {
            cell.innerText="";
        }
        var anc=document.createElement("A");
        var url="javascript:loadGrid("+(page+1)+")";
        anc.href=url;
        var txtedit=document.createTextNode("<<Next>>");
        anc.appendChild(txtedit);
        cell.appendChild(anc);
    }
    else
    {
        var cell=document.getElementById("divnext");
        cell.style.display="block";
        try{cell.innerHTML="";}
        catch(e) {cell.innerText="";}
    }
    if(page>1)
    {
        var cell=document.getElementById("divpre");
        cell.style.display="block";
        try{cell.innerHTML="";}
        catch(e) {cell.innerText="";}
        var anc=document.createElement("A");
        var url="javascript:loadGrid("+(page-1)+")";
        anc.href=url;
        var txtedit=document.createTextNode("<<Previous>>");
        anc.appendChild(txtedit);
        cell.appendChild(anc);
    }
    else
    {
        var cell=document.getElementById("divpre");
        cell.style.display="block";
        try{cell.innerHTML="";}
        catch(e) {cell.innerText="";}
    }
}
