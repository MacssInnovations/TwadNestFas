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
	document.forms[0].checkCode.value="";
	document.forms[0].checkDesc.value="";  
	document.getElementById('billmajortype').selectedIndex=0;
    document.getElementById('billminortype').selectedIndex=0;
    
    document.forms[0].checkmandate[0].checked=true;
    document.forms[0].notapply[0].checked=true;
    document.forms[0].onadd.disabled=false;
  	 document.forms[0].onedit.disabled=true;
  	 document.forms[0].ondelete.disabled=true;
}
function callmajorType()
{
        var url="../../../../../BillScrutiny_serv?command=majorType";
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
        var major1=document.forms[0].billmajortype.value;
        var url="../../../../../BillScrutiny_serv?command=minorType&major2="+major1;
        var xmlrequest= AjaxFunction();
        xmlrequest.open("GET",url,true);              
        xmlrequest.onreadystatechange=function()
        {
            manipulate(xmlrequest);
        };
        xmlrequest.send(null);     
}

function nullCheck()
{
	
//	var unitid=document.forms[0].cmbAcc_UnitCode.value;// changed on 18-11-2017
//    var offid=document.forms[0].cmbOffice_code.value;// changed on 18-11-2017
    var checkDesc=document.forms[0].checkDesc.value;
    var major=document.forms[0].billmajortype.value;
//	if(unitid=="")
//    {
//   	 	alert("Select Unit Code");
//        document.forms[0].cmbAcc_UnitCode.focus();
//        return false;
//    }
//    if(offid=="")
//    {
//           alert("Select Office Code");
//           document.forms[0].cmbOffice_code.focus();
//           return false;
//    }
   
    if(checkDesc=="")
    {
           alert("Enter checkDesc");
           document.forms[0].checkDesc.focus();
           return false;
    }   
   if(major=="")
    {
           alert("Select BillMajorType");
           document.forms[0].billmajortype.focus();
           return false;
    } 
	 return true;
}

function callServer(command,param)
{
	 var url="";
    if(command=="Add")
       {           
//    	    var unitid=document.forms[0].cmbAcc_UnitCode.value;
//    	    var offid=document.forms[0].cmbOffice_code.value;
    	    var checkDesc=document.forms[0].checkDesc.value;
    	    var major=document.forms[0].billmajortype.value;
    	    var minor=document.forms[0].billminortype.value;
    	    var mand;
    	    if(document.forms[0].checkmandate[0].checked==true)
    	        {
    	    		mand="Y";
    	        }
    	    else
    	        {
    	    		mand="N";
    	        }
    	    var apply;
    	    if(document.forms[0].notapply[0].checked==true)
    	        {
    	    		apply="Y";
    	        }
    	    else
    	        {
    	    		apply="N";
    	        }
			   var flag=nullCheck();
			  if(flag==true)
               {
            	   var xmlrequest= AjaxFunction();
            	   //changed on 18-11-2017
            	   
//                   url="../../../../../BillScrutiny_serv?command=add&unitid="+unitid+"&offid="+offid+"&major2="+major+"&minor="+minor+"&checkDesc="+checkDesc+"&mand="+mand+"&apply="+apply;
                   url="../../../../../BillScrutiny_serv?command=add&major2="+major+"&minor="+minor+"&checkDesc="+checkDesc+"&mand="+mand+"&apply="+apply;

            	   
                   xmlrequest.open("GET",url,true);              
                   xmlrequest.onreadystatechange=function()
                       {
                           manipulate(xmlrequest);
                       };
                   xmlrequest.send(null);
               }
                  
                   
       }
       else if(command=="Update")
       {
//		    	   var unitid=document.forms[0].cmbAcc_UnitCode.value;
//		    	    var offid=document.forms[0].cmbOffice_code.value;
		    	    var checkDesc=document.forms[0].checkDesc.value;
		    	    var major=document.forms[0].billmajortype.value;
		    	    
		    	    var minor=document.forms[0].billminortype.value;
		    	    
		    	   var ck=document.forms[0].checkCode.value;
    	           
    	           var mand;
    	           if(document.forms[0].checkmandate[0].checked==true)
    	               {
    	           		mand="Y";
    	               }
    	           else
    	               {
    	           		mand="N";
    	               }
    	           var apply;
    	           if(document.forms[0].notapply[0].checked==true)
    	               {
    	           		apply="Y";
    	               }
    	           else
    	               {
    	           		apply="N";
    	               }
                   var flag=nullCheck();
                   if(flag==true)
                   {
                	   // changed on 18-11-2017
//                   url="../../../../../BillScrutiny_serv?command=updated&unitid="+unitid+"&offid="+offid+"&major2="+major+"&minor="+minor+"&checkDesc="+checkDesc+"&mand="+mand+"&apply="+apply+"&checkcode="+ck;
                   
                       url="../../../../../BillScrutiny_serv?command=updated&major2="+major+"&minor="+minor+"&checkDesc="+checkDesc+"&mand="+mand+"&apply="+apply+"&checkcode="+ck;

                	   var xmlrequest= AjaxFunction();
                   xmlrequest.open("GET",url,true);              
                   xmlrequest.onreadystatechange=function()
                   {
                       manipulate(xmlrequest);
                   };
                   xmlrequest.send(null);
                   }

       }
       
       else if(command=="Delete")
       {  
//			    	   var unitid=document.forms[0].cmbAcc_UnitCode.value;
//			   	    var offid=document.forms[0].cmbOffice_code.value;
    	   		  var ck=document.forms[0].checkCode.value;
    	   		  
    	   		  //changed on 18-11-2017
    	   		  
//    	   		  url="../../../../../BillScrutiny_serv?command=deleted&unitid="+unitid+"&offid="+offid+"&checkcode="+ck;
    	   		  url="../../../../../BillScrutiny_serv?command=deleted&checkcode="+ck;

    	   		var xmlrequest= AjaxFunction();
    	        xmlrequest.open("GET",url,true);              
    	        xmlrequest.onreadystatechange=function()
    	        {
    	            manipulate(xmlrequest);
    	        };
    	        xmlrequest.send(null);
       }
       else if(command=="Get")
       {             
		    	//  url="../../../../../BillScrutiny_serv?command=Get&unitid="+unitid+"&offid="+offid;
    	   		  url="../../../../../BillScrutiny_serv?command=Get";
		          var xmlrequest= AjaxFunction();
		          xmlrequest.open("GET",url,true);              
		          xmlrequest.onreadystatechange=function()
		          {
		              manipulate(xmlrequest);
		          };
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
        	   	alert("Record Inserted successfully.");
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
           else if(command=="updated")
           {
        	   updateRow(baseResponse);
        	   clearAll();
           }
           else if(command=="deleted")
           { 
        	   deleteRow(baseResponse);
        	   clearAll();
           }
           else if(command=="Get")
           { 
        	   getRow(baseResponse);
           }
           else if(command=="listminor")
           { 
        	   listminorRow(baseResponse);
        	//   minortypechecking(baseResponse);
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
         var opt1 = document.createElement('option');
         opt1.value ="select";
         opt1.innerHTML="select";
         minorcmb.appendChild(opt1);
         for(var i=0; i<minorcode.length; i++)
           {
            	     opt1 = document.createElement('option');
                     opt1.value = minorcode[i].firstChild.nodeValue;
                     opt1.innerHTML = minordesc[i].firstChild.nodeValue; 
                     minorcmb.appendChild(opt1);
                 
           }    
}
function addRow(baseResponse)
{
	var flag=baseResponse.getElementsByTagName("flag")[0].firstChild.nodeValue; 
	 if(flag=="success")
    {                        
       
       var checkcode=baseResponse.getElementsByTagName("checkcode")[0].firstChild.nodeValue;
       var checkDesc=baseResponse.getElementsByTagName("checkdesc")[0].firstChild.nodeValue;
       var mand=baseResponse.getElementsByTagName("mandate")[0].firstChild.nodeValue;
       var apply=baseResponse.getElementsByTagName("notapply")[0].firstChild.nodeValue;
       
       var majorCode=baseResponse.getElementsByTagName("majorCode")[0].firstChild.nodeValue;
       var majorDesc=baseResponse.getElementsByTagName("majorDesc")[0].firstChild.nodeValue;
       var minorCode=baseResponse.getElementsByTagName("minorCode")[0].firstChild.nodeValue;
       var minorDesc=baseResponse.getElementsByTagName("minorDesc")[0].firstChild.nodeValue;
       
       var cell2;
       
       var items=new Array();
       items[0]=majorCode;
       items[1]=majorDesc;
       items[2]=minorCode;
       items[3]=minorDesc;
       
       var tbody=document.getElementById("tblList");
                                  
       var mycurrent_row=document.createElement("TR");
       mycurrent_row.id=checkcode;
       cell2=document.createElement("TD");
       cell2.setAttribute('align','left');
       var anc=document.createElement("A");    
      
      // var url="javascript:loadValuesFromTable('" +checkcode+ "')";    
       var url="javascript:loadValuesFromTable("+checkcode+","+majorCode+","+minorCode+")";
       anc.href=url;
       var txtedit=document.createTextNode("Edit");
       anc.appendChild(txtedit);
       cell2.appendChild(anc);
       mycurrent_row.appendChild(cell2);
       
       cell2 =document.createElement("TD");
       cell2.setAttribute('align','right');
       var checkcode=document.createTextNode(checkcode);     
       cell2.appendChild(checkcode);       
       mycurrent_row.appendChild(cell2); 
       
       cell2 =document.createElement("TD");
       cell2.setAttribute('align','left');
       var checkDesc=document.createTextNode(checkDesc);     
       cell2.appendChild(checkDesc);       
       mycurrent_row.appendChild(cell2);
       
       cell2=document.createElement("TD");
       cell2.setAttribute('align','left');
       var mcode=document.createElement("input");
       mcode.type="hidden";
       mcode.name="majorcode";
       mcode.value=items[0];
       cell2.appendChild(mcode);
       var currentText=document.createTextNode(items[1]);
       cell2.appendChild(currentText);
       mycurrent_row.appendChild(cell2);
       
       cell2=document.createElement("TD");
       cell2.setAttribute('align','left');
       var micode=document.createElement("input");
       micode.type="hidden";
       micode.name="minorcode";
       micode.value=items[2];
       cell2.appendChild(micode);
       var currentText=document.createTextNode(items[3]);
       cell2.appendChild(currentText);
       mycurrent_row.appendChild(cell2);
       
       
       cell2 =document.createElement("TD"); 
       cell2.setAttribute('align','left');
       var checkmandate=document.createTextNode(mand);                         
       cell2.appendChild(checkmandate);       
       mycurrent_row.appendChild(cell2);

       cell2 =document.createElement("TD");  
       cell2.setAttribute('align','left');
       var notapply=document.createTextNode(apply);                         
       cell2.appendChild(notapply);       
       mycurrent_row.appendChild(cell2);
       
       cell2 =document.createElement("TD");  
       cell2.setAttribute('align','left');
       var status=document.createTextNode("LIVE");                         
       cell2.appendChild(status);       
       mycurrent_row.appendChild(cell2);
       
       tbody.appendChild(mycurrent_row);
     }
    else
    {
      alert("Failed to Load Values");
    }   
}

function updateRow(baseResponse)
{
  var flag=baseResponse.getElementsByTagName("flag")[0].firstChild.nodeValue;
   if(flag=="success")
   {   
       alert("Record Updated Successfully.");
       
       var checkcode=baseResponse.getElementsByTagName("checkcode")[0].firstChild.nodeValue;
       var checkDesc=baseResponse.getElementsByTagName("checkdesc")[0].firstChild.nodeValue;
       var mandate=baseResponse.getElementsByTagName("mandate")[0].firstChild.nodeValue;
       
       var notapply=baseResponse.getElementsByTagName("notapply")[0].firstChild.nodeValue;
       
       var majorCode=baseResponse.getElementsByTagName("majorCode")[0].firstChild.nodeValue;
       
       var majorDesc=baseResponse.getElementsByTagName("majorDesc")[0].firstChild.nodeValue;
       var minorCode=baseResponse.getElementsByTagName("minorCode")[0].firstChild.nodeValue;
       var minorDesc=baseResponse.getElementsByTagName("minorDesc")[0].firstChild.nodeValue;
       
           var items=new Array();
           
           items[0]=checkcode;
           items[1]=checkDesc;
           
           items[2]=majorCode;
           items[3]=majorDesc;
           items[4]=minorCode;
           items[5]=minorDesc;
           
         var mand;
         if(mandate=="Y")
             {
         		mand="Y";
             }
         else
             {
         		mand="N";
             }
         var apply;
         if(notapply=="Y")
             {
         		apply="Y";
             }
         else
             {
         		apply="N";
             }
           
           items[6]=mand;
           items[7]=apply;
                     
            var r=document.getElementById(checkcode);
            
            var rcells=r.cells;
            rcells.item(1).firstChild.nodeValue=items[0];
            rcells.item(2).firstChild.nodeValue=items[1];
       
            rcells.item(3).childNodes.item(0).value=items[2];
            rcells.item(3).lastChild.nodeValue=items[3];
         
            rcells.item(4).childNodes.item(0).value=items[4];
            rcells.item(4).lastChild.nodeValue=items[5];
            
            rcells.item(5).firstChild.nodeValue=items[6];
            rcells.item(6).firstChild.nodeValue=items[7];
           
             document.forms[0].onadd.disabled=false;
	       	 document.forms[0].onedit.disabled=true;
	       	 document.forms[0].ondelete.disabled=true;
	       	 
	  
    }
}


function getRow(baseResponse)
{
	var flag=baseResponse.getElementsByTagName("flag")[0].firstChild.nodeValue; 
	
    if(flag=="success")
    {          
             var tbody=document.getElementById("tblList");
             var table=document.getElementById("Existing");
              var t=0;
              for(t=tbody.rows.length-1;t>=0;t--)
              {
                 tbody.deleteRow(0);
              }
              var len=baseResponse.getElementsByTagName("checkcode").length;              
              for(var k=0;k<len;k++)
              {
            	
            	  var checkcode = baseResponse.getElementsByTagName("checkcode")[k].firstChild.nodeValue;
                  var checkdesc = baseResponse.getElementsByTagName("checkdesc")[k].firstChild.nodeValue;
                  var mandate = baseResponse.getElementsByTagName("mandate")[k].firstChild.nodeValue;
                  var apply = baseResponse.getElementsByTagName("notapply")[k].firstChild.nodeValue;
                  var majorDesc = baseResponse.getElementsByTagName("majorDesc")[k].firstChild.nodeValue;
                  var minorDesc = baseResponse.getElementsByTagName("minorDesc")[k].firstChild.nodeValue;
                  var majorCode=baseResponse.getElementsByTagName("majorCode")[k].firstChild.nodeValue;
                  var minorCode=baseResponse.getElementsByTagName("minorCode")[k].firstChild.nodeValue;
                  var view=baseResponse.getElementsByTagName("status")[k].firstChild.nodeValue;
//                  alert(view);
                  var items=new Array();
                  items[0]=majorCode;
                  items[1]=majorDesc;
                  items[2]=minorCode;
                  items[3]=minorDesc;
                  
                  var mycurrent_row=document.createElement("TR");
                  mycurrent_row.id=checkcode;
                  cell2=document.createElement("TD");                
          		if (view == "C") {
          			//var tid = document.createTextNode("Cancel");			
          			var priceSpan = document.createElement("span");
          			priceSpan.setAttribute('style','font-weight:bold; color:green; font-family:Verdana');
          			priceSpan.appendChild(document.createTextNode("Cancel"));			
          			cell2.appendChild(priceSpan);          			
          		}else{
          			cell2.setAttribute('align','left');
                    var anc=document.createElement("A");    
                     // var url="javascript:loadValuesFromTable('" +checkcode+ "')";              
                    var url="javascript:loadValuesFromTable("+checkcode+","+majorCode+","+minorCode+")";
                    anc.href=url;
                    var txtedit=document.createTextNode("Edit");
                    anc.appendChild(txtedit);
                    cell2.appendChild(anc);          						
          		}     		
                mycurrent_row.appendChild(cell2);
                  
                  cell2 =document.createElement("TD");
                  cell2.setAttribute('align','right');
                  var checkcode=document.createTextNode(checkcode);     
                  cell2.appendChild(checkcode);       
                  mycurrent_row.appendChild(cell2); 
                  
                  cell2 =document.createElement("TD");
                  cell2.setAttribute('align','left');
                  var checkDesc=document.createTextNode(checkdesc);     
                  cell2.appendChild(checkDesc);       
                  mycurrent_row.appendChild(cell2);
                  
                  cell2=document.createElement("TD");
                  cell2.setAttribute('align','left');
                  var mcode=document.createElement("input");
                  mcode.type="hidden";
                  mcode.name="majorcode";
                  mcode.value=items[0];
                  cell2.appendChild(mcode);
                  var currentText=document.createTextNode(items[1]);
                  cell2.appendChild(currentText);
                  mycurrent_row.appendChild(cell2);
                  
                  cell2=document.createElement("TD");
                  cell2.setAttribute('align','left');
                  var micode=document.createElement("input");
                  micode.type="hidden";
                  micode.name="minorcode";
                  micode.value=items[2];
                  cell2.appendChild(micode);
                  var currentText=document.createTextNode(items[3]);
                  cell2.appendChild(currentText);
                  mycurrent_row.appendChild(cell2);
                 
                  cell2 =document.createElement("TD"); 
                  cell2.setAttribute('align','left');
                  var checkmandate=document.createTextNode(mandate);                         
                  cell2.appendChild(checkmandate);       
                  mycurrent_row.appendChild(cell2);

                  cell2 =document.createElement("TD");  
                  cell2.setAttribute('align','left');
                  var notapply=document.createTextNode(apply);                         
                  cell2.appendChild(notapply);       
                  mycurrent_row.appendChild(cell2);
                  
                  var td5 = document.createElement("TD");
          		  if(view=="C"){
          			  var tdst = document.createTextNode("CANCEL");
          		  }else{
          			  var tdst = document.createTextNode("LIVE");
          		  }
//          		  alert(tdst);
          		  td5.appendChild(tdst);
          		  mycurrent_row.appendChild(td5);
                  
                  tbody.appendChild(mycurrent_row);
                  
              }

    }
}

function deleteRow(baseResponse)
{
                var flag=baseResponse.getElementsByTagName("flag")[0].firstChild.nodeValue;
                if(flag=="success")
                {
                    
                    var tbody=document.getElementById("Existing");     
                    //var r=document.getElementById(checkcode);    
                    //var ri=r.rowIndex;               
                    //tbody.deleteRow(ri);
                    callServer('Get','null');
                    alert("Cancel Successfully");
		             document.forms[0].onadd.disabled=false;
		   	       	 document.forms[0].onedit.disabled=true;
		   	       	 document.forms[0].ondelete.disabled=true;
                }
   	      else
          {
              alert("Unable to Cancel");
          }
}


function loadValuesFromTable(chk,major,minor)
{
	
	 clearAll();
	 checkcode=chk;
     var r=document.getElementById(checkcode);
     var rcells=r.cells;
     var tbody=document.getElementById("tblList");
     var table=document.getElementById("Existing");
     
     document.forms[0].checkCode.value=checkcode;
     document.forms[0].checkDesc.value=rcells.item(2).firstChild.nodeValue;
     document.forms[0].billmajortype.value=rcells.item(3).childNodes.item(0).value;
    
    
     listMinor(rcells.item(3).childNodes.item(0).value,rcells.item(4).childNodes.item(0).value);
   //  document.forms[0].billminortype.value=rcells.item(4).childNodes.item(0).value;
     //document.forms[0].billminortype.value=minor;
     var rad1=rcells.item(5).firstChild.nodeValue;
     var rad2=rcells.item(6).firstChild.nodeValue;
     if(rad1=="Y")
     { 
    	 document.forms[0].checkmandate[0].checked=true;
     }
     else
         document.forms[0].checkmandate[1].checked=true;
     
     if(rad2=="Y")
     {
         document.forms[0].notapply[0].checked=true;}
      else
         document.forms[0].notapply[1].checked=true;
    
     document.forms[0].onadd.disabled=true;
	 document.forms[0].onedit.disabled=false;
	 document.forms[0].ondelete.disabled=false;
   
}
function listMinor(major,minor)
{
	var url="../../../../../BillScrutiny_serv?command=listminor&major2="+major+"&minor="+minor;
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
