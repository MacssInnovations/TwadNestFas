
var service;
var __pagination=11;
var destid;
var totalblock=0;
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
 
 function clearAll()
 {  
   document.frmAssetMinorClassMaster.txtMinorCode.value="";
   document.frmAssetMinorClassMaster.cmbMajorClass.value="0";
   document.frmAssetMinorClassMaster.txtMinorDesc.value="";
   
   document.frmAssetMinorClassMaster.CmdAdd.disabled=false;
   document.frmAssetMinorClassMaster.CmdUpdate.disabled=true;
   document.frmAssetMinorClassMaster.CmdDelete.disabled=true;
   
   var tbody=document.getElementById("tbody");
    var t=0;
    
    for(t=tbody.rows.length-1;t>=0;t--)
    {
       tbody.deleteRow(0);
    }
    callServer('Get','null');
 }
 
 function Exit()
 {
    self.close();
 }
 
 
 
 //******************************Validation Checking**************************//
 function nullCheck()
        {
	 
                  if((document.frmAssetMinorClassMaster.txtMinorDesc.value=="") || (document.frmAssetMinorClassMaster.txtMinorDesc.value.length<=0))
                  { 
                       alert("Please Enter the Asset Class Description");
                       document.frmAssetMinorClassMaster.txtMinorDesc.focus();
                       return false;
                  }
                  
                  if((document.frmAssetMinorClassMaster.cmbMajorClass.value=="") ||(document.frmAssetMinorClassMaster.cmbMajorClass.value.length<=0) || (document.frmAssetMinorClassMaster.cmbMajorClass.value=="0"))
                  {
                    alert("Please Select Asset Type");
                    document.frmAssetMinorClassMaster.cmbMajorClass.focus();
                    return false;
                  }
                  return true;
        }
        
        
//******************************************** CallServer Coding *******************//


function callServer(command,param)
 {
	
	
		var asset_major_class_code=document.frmAssetMinorClassMaster.cmbMajorClass.value;
       	var asset_minor_class_code=document.frmAssetMinorClassMaster.txtMinorCode.value;	
       	var asset_minor_class_desc=document.frmAssetMinorClassMaster.txtMinorDesc.value;
        var url="";
        
       
        if(command=="Add")
        {              
        			var flag=nullCheck();
                    if(flag==true)
                    {
                    url="../../../../../Assets_Minor_Classification_Serv?command=Add&asset_major_class_code=" + asset_major_class_code+"&asset_minor_class_code="+asset_minor_class_code+"&asset_minor_class_desc="+asset_minor_class_desc;
                    var req=getTransport();
                    req.open("GET",url,true);        
                    req.onreadystatechange=function()
                    {
                       processResponse(req);
                    };   
                    req.send(null);
                    }
        }
        else if(command=="Update")
        {
                    var flag=nullCheck();
                    if(flag==true)
                    {
                    url="../../../../../Assets_Minor_Classification_Serv?command=Update&asset_major_class_code=" + asset_major_class_code+"&asset_minor_class_code="+asset_minor_class_code+"&asset_minor_class_desc="+asset_minor_class_desc;
                    var req=getTransport();
                    req.open("GET",url,true);        
                    req.onreadystatechange=function()
                    {
                       processResponse(req);
                    };   
                    req.send(null);
                    }

        }
        else if(command=="Delete")
        {  
                    url="../../../../../Assets_Minor_Classification_Serv?command=Delete&asset_major_class_code=" + asset_major_class_code+"&asset_minor_class_code="+asset_minor_class_code;
                    var req=getTransport();
                    req.open("GET",url,true);        
                    req.onreadystatechange=function()
                    {
                       processResponse(req);
                    };   
                    req.send(null);
        }
        else if(command=="Get")
        {               
            url="../../../../../Assets_Minor_Classification_Serv?command=Get&asset_major_class_code="+asset_major_class_code;
          
            var req=getTransport();
            req.open("GET",url,true); 
            req.onreadystatechange=function()
            {
               processResponse(req);
            };   
                    req.send(null);
        }
        else if(command=="LoadMajorClass")
        {               
            url="../../../../../Assets_Minor_Classification_Serv?command=LoadMajorClass";
            var req=getTransport();
            req.open("GET",url,true); 
            req.onreadystatechange=function()
            {
               processResponse(req);
            };   
                    req.send(null);
        }
}  


//********************************* CallServer Response Coding ***************************************//

function processResponse(req)
  {   
      if(req.readyState==4)
      {
          if(req.status==200)
          {    
              var baseResponse=req.responseXML.getElementsByTagName("response")[0];
              
              var tagCommand=baseResponse.getElementsByTagName("command")[0];
              
              var command=tagCommand.firstChild.nodeValue; 
              
              if(command=="Add")
              {
                  addRow(baseResponse);                 
              }
              else if(command=="Delete")
              { 
            	  deleteRow(baseResponse);
              }
              
              else if(command=="Update")
              {
            	  updateRow(baseResponse);
              }
              else if(command=="Get")
              { 
            	 
            	   getRow(baseResponse);
              }
              else if(command=="LoadMajorClass")
              { 
            	   LoadMajorCombo(baseResponse);
              }
          }
      }
  }


function addRow(baseResponse)
{
              var flag=baseResponse.getElementsByTagName("flag")[0].firstChild.nodeValue; 
               
              if(flag=="success")
              {                        
                 alert("Record Inserted Into Database successfully.");

                 var tbody=document.getElementById("tbody");
                 var table=document.getElementById("Existing");
				 var k=tbody.rows.length;
				  
				  var AssetMajorClassCode=baseResponse.getElementsByTagName("asset_major_class_code")[0].firstChild.nodeValue;
				  var AssetMajorClassDesc=document.getElementById("cmbMajorClass")[AssetMajorClassCode].text;
				  var AssetMinorClassCode=baseResponse.getElementsByTagName("asset_minor_class_code")[0].firstChild.nodeValue;
				  var AssetMinorClassDesc=baseResponse.getElementsByTagName("asset_minor_class_desc")[0].firstChild.nodeValue;
				  var status=baseResponse.getElementsByTagName("status")[0].firstChild.nodeValue;
				  
				  var mycurrent_row=document.createElement("TR");
				  mycurrent_row.id=k;
				  var cell=document.createElement("TD");
				  var anc=document.createElement("A");       
				  var url="javascript:loadValuesFromTable('" + k + "')";              
				  anc.href=url;
				  var txtedit=document.createTextNode("Edit");
				  anc.appendChild(txtedit);
				  cell.appendChild(anc);
				  mycurrent_row.appendChild(cell);
				
				  var cell1 =document.createElement("TD");    
				  var txtassetminorcode=document.createTextNode(AssetMinorClassCode);                         
				  cell1.appendChild(txtassetminorcode);
				  mycurrent_row.appendChild(cell1);
				
				  var cell2 =document.createElement("TD");    
				  var txtassetminordesc=document.createTextNode(AssetMinorClassDesc);
				  cell2.appendChild(txtassetminordesc);
				  mycurrent_row.appendChild(cell2);
				
				  var cell3 =document.createElement("TD");    
				  var txtassetmajordesc=document.createTextNode(AssetMajorClassDesc);                         
				  cell3.appendChild(txtassetmajordesc);       
				  mycurrent_row.appendChild(cell3);
				
				  var cell4 =document.createElement("TD");    
				  var txtassetmajorcode=document.createTextNode(AssetMajorClassCode);
				  cell4.style.display = 'none';
				  cell4.appendChild(txtassetmajorcode);       
				  mycurrent_row.appendChild(cell4);       
				
				  var cell1 =document.createElement("TD");    
				  var status=document.createTextNode(status);                         
				  cell1.appendChild(status);
				  mycurrent_row.appendChild(cell1);
				  
				  tbody.appendChild(mycurrent_row);
          
                document.frmAssetMinorClassMaster.CmdAdd.disabled=false;
                document.frmAssetMinorClassMaster.CmdUpdate.disabled=true;
                document.frmAssetMinorClassMaster.CmdDelete.disabled=true;     
                
                document.frmAssetMinorClassMaster.txtMinorCode.value="";
                document.frmAssetMinorClassMaster.txtMinorDesc.value="";
                document.frmAssetMinorClassMaster.cmbMajorClass.selectedIndex=0;
               }
              else
              {
            	  alert("Failed to Add");
              }
}

function updateRow(baseResponse)
    {
      var flag=baseResponse.getElementsByTagName("flag")[0].firstChild.nodeValue;
       if(flag=="success")
       {   
           alert("Record Updated Successfully.");
               var items=new Array();
               items[0]=document.frmAssetMinorClassMaster.txtMinorCode.value;
               items[1]=document.frmAssetMinorClassMaster.txtMinorDesc.value;
               items[2]=document.frmAssetMinorClassMaster.cmbMajorClass.value;
               var rowNum=document.getElementById("hidRowId").value;
               var r=document.getElementById(rowNum);    
               var rcells=r.cells;
                rcells.item(1).firstChild.nodeValue=items[0];
                rcells.item(2).firstChild.nodeValue=items[1];
                rcells.item(4).firstChild.nodeValue=items[2];
                document.frmAssetMinorClassMaster.CmdAdd.disabled=false;
                document.frmAssetMinorClassMaster.CmdUpdate.disabled=true;
                document.frmAssetMinorClassMaster.CmdDelete.disabled=true;    
                
                document.frmAssetMinorClassMaster.txtMinorCode.value="";
                document.frmAssetMinorClassMaster.txtMinorDesc.value="";
                document.frmAssetMinorClassMaster.cmbMajorClass.selectedIndex=0;
                           
       }
       else
       {
           alert("failed to update values");
       }                                  
    }



function deleteRow(baseResponse)
  {
				var flag=baseResponse.getElementsByTagName("flag")[0].firstChild.nodeValue;
                  
                  if(flag=="success")
                  {
                	  alert("Record Cancel.");
                      /*var rowNum=document.getElementById("hidRowId").value;
                      var tbody=document.getElementById("Existing");     
                      var r=document.getElementById(rowNum);    
                      var ri=r.rowIndex;               
                      tbody.deleteRow(ri);*/ 
                      
                      document.frmAssetMinorClassMaster.txtMinorCode.value="";
                      document.frmAssetMinorClassMaster.txtMinorDesc.value="";
		              document.frmAssetMinorClassMaster.cmbMajorClass.selectedIndex=0;
		              
                      document.frmAssetMinorClassMaster.CmdAdd.disabled=false;
                      document.frmAssetMinorClassMaster.CmdUpdate.disabled=true;
                      document.frmAssetMinorClassMaster.CmdDelete.disabled=true;
                      callServer('Get','');
                  }
                  else
                  {
                      alert("Unable to Delete");
                  }
  }



function getRow(baseResponse)
    {   
              var flag=baseResponse.getElementsByTagName("flag")[0].firstChild.nodeValue; 
              if(flag=="failure")
              {
                   
                  alert("No Record exists");
                  s=0;
                  var tbody=document.getElementById("tbody");
                    try{tbody.innerHTML="";}
                catch(e) {tbody.innerText="";}
                 
                  var cell=document.getElementById("divcmbpage");
                  cell.style.display="none";
                  var cell=document.getElementById("divpage");
                  cell.style.display="none";
         
                  var cell=document.getElementById("divnext");
                  cell.style.display="none";
                  var cell=document.getElementById("divpre");
                  cell.style.display="none";
              }
              else
              {   
              
            	  var tbody=document.getElementById("tbody");
                  
                  if(tbody.rows.length >0)
                  {       
                          //alert(tbody.innerText !='undefined'  && tbody.innerText !=null );
                          if(tbody.innerText !='undefined'  && tbody.innerText !=null  )
                                  tbody.innerText='';
                          else 
                              tbody.innerHTML='';
                          
                         // for(i=0;i<tbody.rows.length;i++)
                         //     tbody.deleteRows(i);
                  }
               
                   service=baseResponse.getElementsByTagName("Minor_leng");
                  
                   if(service)
                   {
                   	 // var items=new Array();
              
                	   var AssetMajorClassCode=baseResponse.getElementsByTagName("asset_major_class_code")[0].firstChild.nodeValue;
                       var AssetMajorClassDesc=baseResponse.getElementsByTagName("asset_major_class_desc")[0].firstChild.nodeValue;
                       var AssetMinorClassCode=baseResponse.getElementsByTagName("asset_minor_class_code")[0].firstChild.nodeValue;
                       var AssetMinorClassDesc=baseResponse.getElementsByTagName("asset_minor_class_desc")[0].firstChild.nodeValue;
                       var view=baseResponse.getElementsByTagName("status")[0].firstChild.nodeValue;  
                         
                        
                               var tbody=document.getElementById("tbody");
                        
                                try{tbody.innerHTML="";}
                               catch(e) {tbody.innerText="";}
                         
                               var i=0;
                     totalblock=0;
                   
                           if(service.length>0)
                           {
                                   totalblock=parseInt(service.length/__pagination);
                                 
                                   if(service.length%__pagination!=0)
                                   {
                                           totalblock=totalblock+1;
                                   }
                                   
                                   
                                   var cmbpage=document.getElementById("cmbpage");
                                   
                                  
                                  try{ cmbpage.innerHTML="";
                                  }catch(e)
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
                                           }catch(errorObject)
                                           {
                                           cmbpage.add(option,null);
                                           }
                                   } 
                           }
                           
                            loadPage(1);
           
           }
                  	   
              }
    }
            	  
function loadPage(page)
{
//    		alert("inside loadpage function"+page);
            var i=0;
            var c=0;
            var cell=document.getElementById("divcmbpage");
            cell.style.display="block";
            var p=__pagination*(page-1);
            document.frmAssetMinorClassMaster.cmbpage.selectedIndex=page-1;
            var tbody=document.getElementById("tbody");
            try{tbody.innerHTML="";}
            catch(e) {tbody.innerText="";}  
              
             if(service)
             {
              var seq=0;
            	s=0;
                var i=0;
                
                for(i=p;i<service.length&& c<__pagination;i++)
                {
                        c++;
                        var items=new Array();
                        items[0]=service[i].getElementsByTagName("asset_major_class_code")[0].firstChild.nodeValue;
                        items[1]=service[i].getElementsByTagName("asset_major_class_desc")[0].firstChild.nodeValue;
                        items[2]=service[i].getElementsByTagName("asset_minor_class_code")[0].firstChild.nodeValue;
                        items[3]=service[i].getElementsByTagName("asset_minor_class_desc")[0].firstChild.nodeValue;
                        items[4]=service[i].getElementsByTagName("status")[0].firstChild.nodeValue;
                   
                        var tbody=document.getElementById("tbody");
                        var mycurrent_row=document.createElement("TR");
                        mycurrent_row.id=seq;
                        var cell=document.createElement("TD");
                        if (items[4] == "C") {
                			//var tid = document.createTextNode("Cancel");			
                			var priceSpan = document.createElement("span");
                			priceSpan.setAttribute('style','font-weight:bold; color:green; font-family:Verdana');
                			priceSpan.appendChild(document.createTextNode("Cancel"));			
                			cell.appendChild(priceSpan);
                			mycurrent_row.appendChild(cell);
                		}else{
                			var anc=document.createElement("A");       
                           var url="javascript:loadValuesFromTable('" + seq + "')";              
                           anc.href=url;
                           var txtedit=document.createTextNode("Edit");
                           anc.appendChild(txtedit);
                           cell.appendChild(anc);
                           mycurrent_row.appendChild(cell);
                		}             
                        var cell1 =document.createElement("TD");    
                        var txtassetminorcode=document.createTextNode(items[2]);                         
                        cell1.appendChild(txtassetminorcode);
                        mycurrent_row.appendChild(cell1);

                        var cell2 =document.createElement("TD");    
                        var txtassetminordesc=document.createTextNode(items[3]);
                        cell2.appendChild(txtassetminordesc);
                        mycurrent_row.appendChild(cell2);

                        var cell3 =document.createElement("TD");    
                        var txtassetmajordesc=document.createTextNode(items[1]);                         
                        cell3.appendChild(txtassetmajordesc);       
                        mycurrent_row.appendChild(cell3);

                        var cell4 =document.createElement("TD");    
                        var txtassetmajorcode=document.createTextNode(items[0]);
                        cell4.style.display = 'none';
                        cell4.appendChild(txtassetmajorcode);       
                        mycurrent_row.appendChild(cell4);
                        
                        var cell5 =document.createElement("TD");
                        if(items[4]=="C"){
                       	 var stat=document.createTextNode("CANCEL");
                		}else{
                			var stat=document.createTextNode("LIVE");
                		}                                              
                        cell5.appendChild(stat);       
                        mycurrent_row.appendChild(cell5);

                        tbody.appendChild(mycurrent_row);
                       
                        seq = seq + 1;
                        document.getElementById("RecordCount").value = seq;
//                        alert("no of records"+seq);
                    }
            
                }          
            
                       var cell=document.getElementById("divcmbpage");
                            cell.style.display="block";
                       var cell=document.getElementById("divpage");
                            cell.style.display="block";
                           
                       if(navigator.appName.indexOf("Microsoft")!=-1)
                            cell.innerText= ' / ' +totalblock;
                       else
                            cell.innerHTML= ' / ' +totalblock;
            if(page<totalblock)
            {
                var cell=document.getElementById("divnext");
                cell.style.display="block";
                try{cell.innerHTML="";}
                  catch(e) {cell.innerText="";}
                 var anc=document.createElement("A");
                var url="javascript:loadPage("+(page+1)+")";
                anc.href=url;
                //anc.setAttribute('style','text-decoratin:none');
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
                //cell.innerText='';
                try{cell.innerHTML="";}
                  catch(e) {cell.innerText="";}
                 var anc=document.createElement("A");
                var url="javascript:loadPage("+(page-1)+")";
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


/*
function getRow(baseResponse)
    {   
              var flag=baseResponse.getElementsByTagName("flag")[0].firstChild.nodeValue; 
              
              if(flag=="success")
              {          
                       
            	   document.frmAssetMinorClassMaster.txtMinorCode.value="";
            	   document.frmAssetMinorClassMaster.txtMinorDesc.value="";

            	   document.frmAssetMinorClassMaster.CmdAdd.disabled=false;
            	   document.frmAssetMinorClassMaster.CmdUpdate.disabled=true;
            	   document.frmAssetMinorClassMaster.CmdDelete.disabled=true;

            	   var tbody=document.getElementById("tblList");
                   var table=document.getElementById("Existing");
                    var t=0;
                    for(t=tbody.rows.length-1;t>=0;t--)
                    {
                       tbody.deleteRow(0);
                    }   
                                         
                    var len=baseResponse.getElementsByTagName("asset_major_class_code").length;
                        
                    for(var k=0;k<len;k++)
                    {
                     
                     var AssetMajorClassCode=baseResponse.getElementsByTagName("asset_major_class_code")[k].firstChild.nodeValue;
                     var AssetMajorClassDesc=baseResponse.getElementsByTagName("asset_major_class_desc")[k].firstChild.nodeValue;
                     var AssetMinorClassCode=baseResponse.getElementsByTagName("asset_minor_class_code")[k].firstChild.nodeValue;
                     var AssetMinorClassDesc=baseResponse.getElementsByTagName("asset_minor_class_desc")[k].firstChild.nodeValue;
                     var view=baseResponse.getElementsByTagName("status")[k].firstChild.nodeValue;                     
                     var mycurrent_row=document.createElement("TR");
                     mycurrent_row.id=k;
                     var cell=document.createElement("TD");
                     if (view == "C") {
             			//var tid = document.createTextNode("Cancel");			
             			var priceSpan = document.createElement("span");
             			priceSpan.setAttribute('style','font-weight:bold; color:green; font-family:Verdana');
             			priceSpan.appendChild(document.createTextNode("Cancel"));			
             			cell.appendChild(priceSpan);
             			mycurrent_row.appendChild(cell);
             		}else{
             			var anc=document.createElement("A");       
                        var url="javascript:loadValuesFromTable('" + k + "')";              
                        anc.href=url;
                        var txtedit=document.createTextNode("Edit");
                        anc.appendChild(txtedit);
                        cell.appendChild(anc);
                        mycurrent_row.appendChild(cell);
             		}             
                     var cell1 =document.createElement("TD");    
                     var txtassetminorcode=document.createTextNode(AssetMinorClassCode);                         
                     cell1.appendChild(txtassetminorcode);
                     mycurrent_row.appendChild(cell1);

                     var cell2 =document.createElement("TD");    
                     var txtassetminordesc=document.createTextNode(AssetMinorClassDesc);
                     cell2.appendChild(txtassetminordesc);
                     mycurrent_row.appendChild(cell2);

                     var cell3 =document.createElement("TD");    
                     var txtassetmajordesc=document.createTextNode(AssetMajorClassDesc);                         
                     cell3.appendChild(txtassetmajordesc);       
                     mycurrent_row.appendChild(cell3);

                     var cell4 =document.createElement("TD");    
                     var txtassetmajorcode=document.createTextNode(AssetMajorClassCode);
                     cell4.style.display = 'none';
                     cell4.appendChild(txtassetmajorcode);       
                     mycurrent_row.appendChild(cell4);
                     
                     var cell5 =document.createElement("TD");
                     if(view=="C"){
                    	 var stat=document.createTextNode("CANCEL");
             		}else{
             			var stat=document.createTextNode("LIVE");
             		}                                              
                     cell5.appendChild(stat);       
                     mycurrent_row.appendChild(cell5);

                     tbody.appendChild(mycurrent_row);
                    }
                  }
                  else
                  {
                    alert("Failed to Load Values");
                  }
}
*/
function loadValuesFromTable(rid)
{      
      var r=document.getElementById(rid); 
      var rcells=r.cells;
      var tbody=document.getElementById("tbody");
      var table=document.getElementById("Existing");
      
      document.getElementById("hidRowId").value = rid;
      document.frmAssetMinorClassMaster.txtMinorCode.value=rcells.item(1).firstChild.nodeValue;
      document.frmAssetMinorClassMaster.txtMinorDesc.value=rcells.item(2).firstChild.nodeValue;
      document.frmAssetMinorClassMaster.cmbMajorClass.value=rcells.item(4).firstChild.nodeValue;
      
      document.frmAssetMinorClassMaster.CmdAdd.disabled=true;
      document.frmAssetMinorClassMaster.CmdUpdate.disabled=false;
      document.frmAssetMinorClassMaster.CmdDelete.disabled=false;
    
      document.frmAssetMinorClassMaster.CmdDelete.focus();
}


function LoadMajorCombo(baseResponse)
{
    var flag=baseResponse.getElementsByTagName("flag")[0].firstChild.nodeValue; 
    if(flag=="success")
    {          
    	var cmbMajorClass=document.getElementById("cmbMajorClass");
    	var ln = cmbMajorClass.length;
    	for(i=1; i<ln; i++)
    	{
    		cmbMajorClass.remove(1);
    	}
    	var mjrCode = baseResponse.getElementsByTagName('asset_major_class_code');
    	var len = mjrCode.length;
    	for(i=0; i<len; i++)
    	{
    		mjrCode = baseResponse.getElementsByTagName('asset_major_class_code')[i].firstChild.nodeValue;
    		var mjrDesc = baseResponse.getElementsByTagName('asset_major_class_desc')[i].firstChild.nodeValue;
    		var opt = document.createElement("option");
    		opt.value = mjrCode;
    		opt.innerHTML = mjrDesc;
    		cmbMajorClass.appendChild(opt);
    	}
    }
    else
    {
    	alert("Error fetching list of Major Classification from Database");
    }
}