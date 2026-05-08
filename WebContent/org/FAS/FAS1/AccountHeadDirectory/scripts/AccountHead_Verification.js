
var service;
var __pagination=11;
var destid;
var totalblock=0;
////////////////////////////////////--------------- For loading Minor Group-------------
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

var Acc_Head_list_SL;

function chkPage()
{
	
	var tbody=document.getElementById("tbody");
	//alert(tbody.rows.length);
	/*if(tbody.rows.length==0)
		{
		document.getElementById("divcmbpage").style.display="none";
		}*/
	if(tbody.rows.length!=0){
		if(tbody.rows.length==10)
			{
			document.getElementById("divcmbpage").style.display="none";
			}
		if(tbody.rows.length>10){
			document.getElementById("divcmbpage").style.display="block";
			var se=document.getElementById("cmbpage");
			se.length=0;
			for(var i=1;i>11;i++){
				var option =document.createElement("OPTION");
				option.id="page"+i;
				option.name="page"+i;
				option.value=i;
				option.text=i;
				
			}
			
		}
		
	}
	}


function Show(AHcode)
{
    if (Acc_Head_list_SL && Acc_Head_list_SL.open && !Acc_Head_list_SL.closed) 
    {
       Acc_Head_list_SL.resizeTo(500,500);
       Acc_Head_list_SL.moveTo(250,250); 
       Acc_Head_list_SL.focus();
    }
    else
    {
        Acc_Head_list_SL=null
    }
    Acc_Head_list_SL= window.open("../../../../../org/FAS/FAS1/AccountHeadDirectory/jsps/Acc_Head_Dir_ListAll_SL.jsp?AHcode="+AHcode,"SL_Types","status=1,height=500,width=500,resizable=YES, scrollbars=yes"); 
    Acc_Head_list_SL.moveTo(250,250);  
    Acc_Head_list_SL.focus();
    
}

window.onunload=function()
{
if (Acc_Head_list_SL && Acc_Head_list_SL.open && !Acc_Head_list_SL.closed) Acc_Head_list_SL.close();
}

function loadingMinor(Command)
{
    if(Command=="loadMinor")
        {  
            startwaiting(document.FasAcc_Head_Verification);
            var txtMajor_id=document.getElementById("Major_Grp").value;
            var url="../../../../../Acc_Head_Dir_List_InUse.view?Command=loadMinor&txtMajor_id="+txtMajor_id;
//            alert(url);
            var req=getTransport();
            req.open("POST",url,true); 
            req.onreadystatechange=function()
            {
               handleResponse(req);
            }   
                    req.send(null);
                    
        }
}
function ListBy(type,alpha)
{
   if(type=="StartingAlphabets")
   {     
     startwaiting(document.FasAcc_Head_Verification);
      var usagestatus;
        if(document.FasAcc_Head_Verification.usage[0].checked)
       {
           usagestatus="InUse";
       }
        else if(document.FasAcc_Head_Verification.usage[1].checked)
        {
           usagestatus="NotInUse";
        }
      var url="../../../../../Acc_Head_Dir_List_InUse.view?Command=StartingAlphabets&Alphabet="+alpha+"&usagestatus="+usagestatus;
      var req=getTransport();
      req.open("GET",url,true); 
            req.onreadystatechange=function()
            {
               handleResponse(req);
            }   
                    req.send(null);
   }   
   else if(type=="StartingDigit")
   {
    startwaiting(document.FasAcc_Head_Verification);
      var usagestatus;
        if(document.FasAcc_Head_Verification.usage[0].checked)
       {
           usagestatus="InUse";
       }
        else if(document.FasAcc_Head_Verification.usage[1].checked)
        {
           usagestatus="NotInUse";
        }
      var url="../../../../../Acc_Head_Dir_List_InUse.view?Command=StartingDigit&Digit="+alpha+"&usagestatus="+usagestatus;
      var req=getTransport();
      req.open("GET",url,true); 
            req.onreadystatechange=function()
            {
               handleResponse(req);
            }   
                    req.send(null);
   }   
}
function searchByMajorMinor()
{
    startwaiting(document.FasAcc_Head_Verification);
    var MajorGrp=document.FasAcc_Head_Verification.Major_Grp.value;
    var MinorGrp=document.FasAcc_Head_Verification.Minor_Grp.value;
    var usagestatus;
           usagestatus="InUse";
    //var url="../../../../../Acc_Head_Dir_List_InUse.view?Command=MajorMinor&MajorGroup="+MajorGrp+"&MinorGroup="+MinorGrp+"&usagestatus="+usagestatus;
           var url="../../../../../AccountHead_Verification?Command=MajorMinor&MajorGroup="+MajorGrp+"&MinorGroup="+MinorGrp+"&usagestatus="+usagestatus;
     var req=getTransport();
     //alert(url);
     req.open("GET",url,true); 
            req.onreadystatechange=function()
            {
               handleResponse(req);
            }   
                    req.send(null);
}

function handleResponse(req)
{  
    if(req.readyState==4)
    {
        if(req.status==200)
        {   
            //alert("here")
            stopwaiting(document.FasAcc_Head_Verification);
            var baseResponse=req.responseXML.getElementsByTagName("response")[0];
            var tagcommand=baseResponse.getElementsByTagName("command")[0];
            var Command=tagcommand.firstChild.nodeValue;
            if(Command=="loadMinor")
            {
                loadMinor(baseResponse);
            }
            else if(Command=="MajorMinor")
            {
            	
                loadTable_val(baseResponse);
            }
        }
    }
}

function loadTable_val(baseResponse)
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
                 
                     service=baseResponse.getElementsByTagName("AHCode_leng");
                    
                    if(service)
                    {
                    	 // var items=new Array();
                    	var AHCode=baseResponse.getElementsByTagName("AHCode")[0].firstChild.nodeValue;
                    	var AHDesc=baseResponse.getElementsByTagName("AHDesc")[0].firstChild.nodeValue;
                    	var Maj_id=baseResponse.getElementsByTagName("Maj_id")[0].firstChild.nodeValue;
                          var Min_id=baseResponse.getElementsByTagName("Min_id")[0].firstChild.nodeValue;
                          var Bal_type=baseResponse.getElementsByTagName("Bal_type")[0].firstChild.nodeValue;
                          
                         
                                var tbody=document.getElementById("tbody");
                         
                                 try{tbody.innerHTML="";}
                                catch(e) {tbody.innerText="";}
                          
                                var i=0;
                      totalblock=0;
                    
                            if(service.length>0)
                            {
                                    totalblock=parseInt(service.length/__pagination);
                                    alert("testb  totalblock "+totalblock);
                                    if(service.length%__pagination!=0)
                                    {
                                            totalblock=totalblock+1;
                                    }
                                    
                                    alert("totalblock >>>> "+totalblock);
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



function loadTable1(baseResponse)
{
    
                var flag=baseResponse.getElementsByTagName("flag")[0].firstChild.nodeValue;
                if(flag=="failure")
                {
                     
                    alert("No Record exists");
                    s=0;
                   var tbody=document.getElementById("tbody");
                      try
                      {
                      
                      tbody.innerHTML='';
//                      document.getElementById("divpre").innerHTML='';
//                      document.getElementById("divnext").innerHTML='';
//                      document.getElementById("divcmbpage").innerHTML='';
//                      document.getElementById("divpage").innerHTML='';
                      document.getElementById("divpre").style.display='none';
                      document.getElementById("divnext").style.display='none';
                      document.getElementById("divcmbpage").style.display='none';
                      document.getElementById("divpage").style.display='none';
                     }
                  catch(e) {
                           tbody.innerText='';}
                                                                     
                }
                else if(flag=="success")
                {   
                	var tbody=document.getElementById("tbody");
                    if(tbody.rows.length >0)
                    {   
                        if(tbody.innerText !='undefined'  && tbody.innerText !=null )
                                {
                                tbody.innerText='';
                                }
                        else 
                                tbody.innerHTML='';
                     }
                      service=baseResponse.getElementsByTagName("AHCode_leng");
//                      alert("length of row:::::"+service.length);
                    if(service)
                    {
                       var tbody=document.getElementById("tbody");
                        try{tbody.innerHTML="";}
                        catch(e) {tbody.innerText="";}
                        var i=0;var seq=0;
                       // totalblock=0;
                         if(service.length>0)
                         {
                                totalblock=parseInt(service.length/__pagination);
                               // alert("totalblock    "+totalblock);
                                if(service.length%__pagination!=0)
                                {
                                        totalblock=totalblock+1;
                                }
                               // alert("totalblock.........after if..........."+totalblock);
                                var cmbpage=document.getElementById("cmbpage");
                                try{ cmbpage.innerHTML="";
                                   }catch(e){
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
//                        	 alert("loading grid");
                        	 for(i=0;i<service.length;i++)
                             {
                                    
                                     var items=new Array();
                                     items[0]=service[i].getElementsByTagName("AHCode")[0].firstChild.nodeValue;
                                     items[1]=service[i].getElementsByTagName("AHDesc")[0].firstChild.nodeValue;
                                     items[2]=service[i].getElementsByTagName("Maj_id")[0].firstChild.nodeValue;
                                     items[3]=service[i].getElementsByTagName("Min_id")[0].firstChild.nodeValue;
                                     items[4]=service[i].getElementsByTagName("Bal_type")[0].firstChild.nodeValue;
                                     if(items[4]=="null")
                                     {
                                     	items[4]="";
                                     }
                                     else
                                     	{
                                     	items[4]=service[i].getElementsByTagName("Bal_type")[0].firstChild.nodeValue;
                                     	}
                                    
                                     var tbody=document.getElementById("tbody");
                                     var mycurrent_row=document.createElement("TR");

                                     mycurrent_row.id=seq;
                                     
                                     var cell1=document.createElement("TD");
                                     cell1.style.textAlign='center';
                                     var sel="";            
                                     if (window.navigator.appName.toLowerCase().indexOf("netscape") == -1)
                                     {
                                   	  sel=document.createElement("<INPUT type='checkbox'  name='verifyAccHead"+seq+"' id='verifyAccHead"+seq+"' value='Y' checked='true' />" );                       
                                     }
                                     else
                                     {    
                                     	
                                       sel=document.createElement("input");     
                                       sel.type="checkbox";             
                                       sel.name="verifyAccHead"+seq;
                                       sel.id="verifyAccHead"+seq;   
                                      // sel.setAttribute('onclick', "callme(" + seq + ")");
                                       sel.checked=false;
                                       sel.value="Y";                          
                                     }
                                     cell1.appendChild(sel);
                                     mycurrent_row.appendChild(cell1); 
                                     for(j=0;j<5;j++)
                                     {
                                         cell2=document.createElement("TD");
                                         cell2.setAttribute('align','left');
                                         if(items[j]!="null")
                                         {
                                             var currentText=document.createTextNode(items[j]);
                                         }
                                         else
                                         {
                                             var currentText=document.createTextNode('');
                                         }
                                         cell2.appendChild(currentText);
                                         mycurrent_row.appendChild(cell2);
                                     }
                                     //all rows in hidden fields-------------------
                                     var cell21= document.createElement("TD");
                     				var AccHeadCode = document.createElement("input");
                     				AccHeadCode.type = "hidden";
                     				AccHeadCode.name = "AccHeadCode" + seq;
                     				AccHeadCode.id = "AccHeadCode" + seq;
                     				AccHeadCode.value = items[0];
                     				cell21.appendChild(AccHeadCode);
                     				var currentText = document.createTextNode(items[0]);
                     				cell21.appendChild(currentText);
                     				mycurrent_row.appendChild(cell21);
                                     
                                     var cell31= document.createElement("TD");
                     				var AccHeadDesc = document.createElement("input");
                     				AccHeadDesc.type = "hidden";
                     				AccHeadDesc.name = "AccHeadDesc" + seq;
                     				AccHeadDesc.id = "AccHeadDesc" + seq;
                     				AccHeadDesc.value = items[1];
                     				cell31.appendChild(AccHeadDesc);
                     				var currentText = document.createTextNode(items[1]);
                     				cell31.appendChild(currentText);
                     				mycurrent_row.appendChild(cell31);
                     				
                     				var cell41= document.createElement("TD");
                     				var MajorCode = document.createElement("input");
                     				MajorCode.type = "hidden";
                     				MajorCode.name = "MajorCode" + seq;
                     				MajorCode.id = "MajorCode" + seq;
                     				MajorCode.value = items[2];
                     				cell41.appendChild(MajorCode);
                     				var currentText = document.createTextNode(items[2]);
                     				cell41.appendChild(currentText);
                     				mycurrent_row.appendChild(cell41);
                     				
                     				var cell51= document.createElement("TD");
                     				var MinorCode = document.createElement("input");
                     				MinorCode.type = "hidden";
                     				MinorCode.name = "MinorCode" + seq;
                     				MinorCode.id = "MinorCode" + seq;
                     				MinorCode.value = items[3];
                     				cell51.appendChild(MinorCode);
                     				var currentText = document.createTextNode(items[3]);
                     				cell51.appendChild(currentText);
                     				mycurrent_row.appendChild(cell51);
                     				
                     				var cell61= document.createElement("TD");
                     				var BalanceType = document.createElement("input");
                     				BalanceType.type = "hidden";
                     				BalanceType.name = "BalanceType" + seq;
                     				BalanceType.id = "BalanceType" + seq;
                     				BalanceType.value = items[4];
                     				cell61.appendChild(BalanceType);
                     				var currentText = document.createTextNode(items[4]);
                     				cell61.appendChild(currentText);
                     				mycurrent_row.appendChild(cell61);

                                        
                                     tbody.appendChild(mycurrent_row);
                                     seq = seq + 1;
                                     document.getElementById("RecordCount").value = seq;
//                                     alert("no of records"+seq);
                                 }
                            }
                         loadPage(1);
                        
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
}
 


function loadPage(page)
{
//    		alert("inside loadpage function"+page);
            var i=0;
            var c=0;
            var cell=document.getElementById("divcmbpage");
            cell.style.display="block";
            var p=__pagination*(page-1);
            document.FasAcc_Head_Verification.cmbpage.selectedIndex=page-1;
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
                        items[0]=service[i].getElementsByTagName("AHCode")[0].firstChild.nodeValue;
                        items[1]=service[i].getElementsByTagName("AHDesc")[0].firstChild.nodeValue;
                        items[2]=service[i].getElementsByTagName("Maj_id")[0].firstChild.nodeValue;
                        items[3]=service[i].getElementsByTagName("Min_id")[0].firstChild.nodeValue;
                        items[4]=service[i].getElementsByTagName("Bal_type")[0].firstChild.nodeValue;
                        if(items[4]=="null")
                        {
                        	items[4]="";
                        }
                        else
                        	{
                        	items[4]=service[i].getElementsByTagName("Bal_type")[0].firstChild.nodeValue;
                        	}
                       
                        var tbody=document.getElementById("tbody");
                        var mycurrent_row=document.createElement("TR");

                        mycurrent_row.id=seq;
                        var cell1=document.createElement("TD");
                        cell1.style.textAlign='center';
                        var sel="";            
                        if (window.navigator.appName.toLowerCase().indexOf("netscape") == -1)
                        {
                      	  sel=document.createElement("<INPUT type='checkbox'  name='verifyAccHead"+seq+"' id='verifyAccHead"+seq+"' value='Y' checked='true' />" );                       
                        }
                        else
                        {    
                        	
                          sel=document.createElement("input");     
                          sel.type="checkbox";             
                          sel.name="verifyAccHead"+seq;
                          sel.id="verifyAccHead"+seq;   
                         // sel.setAttribute('onclick', "callme(" + seq + ")");
                          sel.checked=false;
                          sel.value="Y";                          
                        }
                        cell1.appendChild(sel);
                        mycurrent_row.appendChild(cell1); 
                        /*for(j=0;j<5;j++)
                        {
                            cell2=document.createElement("TD");
                            cell2.setAttribute('align','left');
                            if(items[j]!="null")
                            {
                                var currentText=document.createTextNode(items[j]);
                            }
                            else
                            {
                                var currentText=document.createTextNode('');
                            }
                            cell2.appendChild(currentText);
                            mycurrent_row.appendChild(cell2);
                        }*/
                        //all rows in hidden fields-------------------
                        var cell21= document.createElement("TD");
        				var AccHeadCode = document.createElement("input");
        				AccHeadCode.type = "hidden";
        				AccHeadCode.name = "AccHeadCode" + seq;
        				AccHeadCode.id = "AccHeadCode" + seq;
        				AccHeadCode.value = items[0];
        				cell21.appendChild(AccHeadCode);
        				var currentText = document.createTextNode(items[0]);
        				cell21.appendChild(currentText);
        				mycurrent_row.appendChild(cell21);
                        
                        var cell31= document.createElement("TD");
        				var AccHeadDesc = document.createElement("input");
        				AccHeadDesc.type = "hidden";
        				AccHeadDesc.name = "AccHeadDesc" + seq;
        				AccHeadDesc.id = "AccHeadDesc" + seq;
        				AccHeadDesc.value = items[1];
        				cell31.appendChild(AccHeadDesc);
        				var currentText = document.createTextNode(items[1]);
        				cell31.appendChild(currentText);
        				mycurrent_row.appendChild(cell31);
        				
        				var cell41= document.createElement("TD");
        				var MajorCode = document.createElement("input");
        				MajorCode.type = "hidden";
        				MajorCode.name = "MajorCode" + seq;
        				MajorCode.id = "MajorCode" + seq;
        				MajorCode.value = items[2];
        				cell41.appendChild(MajorCode);
        				var currentText = document.createTextNode(items[2]);
        				cell41.appendChild(currentText);
        				mycurrent_row.appendChild(cell41);
        				
        				var cell51= document.createElement("TD");
        				var MinorCode = document.createElement("input");
        				MinorCode.type = "hidden";
        				MinorCode.name = "MinorCode" + seq;
        				MinorCode.id = "MinorCode" + seq;
        				MinorCode.value = items[3];
        				cell51.appendChild(MinorCode);
        				var currentText = document.createTextNode(items[3]);
        				cell51.appendChild(currentText);
        				mycurrent_row.appendChild(cell51);
        				
        				var cell61= document.createElement("TD");
        				var BalanceType = document.createElement("input");
        				BalanceType.type = "hidden";
        				BalanceType.name = "BalanceType" + seq;
        				BalanceType.id = "BalanceType" + seq;
        				BalanceType.value = items[4];
        				cell61.appendChild(BalanceType);
        				var currentText = document.createTextNode(items[4]);
        				cell61.appendChild(currentText);
        				mycurrent_row.appendChild(cell61);

                           
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

// when page option changed
function changepage()
{
var page=document.FasAcc_Head_Verification.cmbpage.value;
loadPage(parseInt(page));
} 
function changepagesize()
{

           __pagination=document.FasAcc_Head_Verification.cmbpagination.value;
            var v=document.getElementsByName("sel");
            //alert(v);
                if(service)
                {
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
                                       }catch(e){
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
                                           {cmbpage.add(option,null);}
                                     } 
                             }
                             loadPage(1);
            }
}

function loadMinor(baseResponse)
{

var flag=baseResponse.getElementsByTagName("flag")[0].firstChild.nodeValue;
    
    if(flag=="success")
    {   
        var Maj_id=baseResponse.getElementsByTagName("Maj_id");
        var items_maj=new Array();
        var items_min=new Array();
        var items_desc=new Array();
        var min_id=document.getElementById("Minor_Grp");
        
        for(var k=0;k<Maj_id.length;k++)
        {
             items_maj[k]=baseResponse.getElementsByTagName("Maj_id")[k].firstChild.nodeValue;   
             items_min[k]=baseResponse.getElementsByTagName("Min_id")[k].firstChild.nodeValue;
             items_desc[k]=baseResponse.getElementsByTagName("Min_desc")[k].firstChild.nodeValue;
        }
        min_id.innerHTML="";
        var option=document.createElement("OPTION");
        option.text="All";
        option.value="All";
        try
        {
            min_id.add(option);
        }catch(errorObject)
        {
            min_id.add(option,null);
        }
        
        for(var k=0;k<Maj_id.length;k++)
        {   
              var option=document.createElement("OPTION");
              option.text=items_desc[k];
              option.value=items_min[k];
               try
              {
                  min_id.add(option);
              }
              catch(errorObject)
              {
                  min_id.add(option,null);
              }
        }
    }
}           



// code to enable all anchors
function enableAllAnchors(divID)
{
  var div=document.getElementById(divID);  
  var anchors=div.getElementsByTagName("a");
  var i;
  for(i=0;i<anchors.length;i++)
  {
    fncEnable(anchors.item(i));
  }
}
function fncEnable(obj)
{   
   try
   {
     obj.setAttribute('href', obj.attributes['href_bak'].nodeValue);
     obj.style.color="blue";      
   }
   catch(e){}
}
//code to disable all anchors
function disableAllAnchors(divID)
{
  var div=document.getElementById(divID);  
  var anchors=div.getElementsByTagName("a");
  var i;
  for(i=0;i<anchors.length;i++)
  {
    fncDisable(anchors.item(i));
  }
}

function fncDisable(obj)
{      
      if (window.navigator.appName.toLowerCase().indexOf("microsoft") > -1) { // IE;
      if(obj.href!="")
        obj.setAttribute('href_bak', obj.attributes['href'].nodeValue);
      }
      else{
      if (window.navigator.appName.toLowerCase().indexOf("netscape") > -1) // Firefox
      {
        if(obj.attributes['href']!=null)
          obj.setAttribute('href_bak', obj.attributes['href'].nodeValue);
      }
      else
        alert("Error: This application does not support your browser.  Try again using IE or Firefox.");
    }
    obj.removeAttribute('href');
    obj.style.color="gray";
}

////////////////////////////////////////////////////////////////------------------


function MajorValue()
{
var majorgrp=document.FasAcc_Head_Verification.Major_Grp.value;
//document.FasAcc_Head_Verification.MajGrp.value=majorgrp;
}

function MinorValue()
{
var minorgrp=document.FasAcc_Head_Verification.Minor_Grp.value;
//document.FasAcc_Head_Verification.MinGrp.value=minorgrp;
}
 function callLink(type,alphabet)
{
  if(type=="A")
  {    
    ListBy('StartingAlphabets',alphabet);
  }
  else if(type=="N")
  {
    ListBy('StartingDigit',alphabet);
  }
}


function btnsubmit()
{
//alert('hai');
var v=document.getElementsByName("sel");
    if(v)
    {
        for(i=0;i<v.length;i++)
        {
            if(v[i].checked==true)
            {
               // opener.document.HRE_EmployeeServiceDetails.txtEmployeeid.value = (v[i].value);
              //  try{self.opener.doFunction('loademp','null');}catch(e){}
                Minimize();
                opener.doParentAccHead(v[i].value);
                //opener.focus();
                return true;
            }
           
        }
    }
    else
    {
               alert('Select an Employee ');
               return false; 
    }
}  
function btncancel()
{

 self.close();
}

function Minimize() 
{
window.resizeTo(0,0);
window.screenX = screen.width;
window.screenY = screen.height;
opener.window.focus();
}
function pick(t)
{
    s=t.value;
}

function numbersonly(e)
    {
        var unicode=e.charCode? e.charCode : e.keyCode;
       if(unicode==13)
        {
          //t.blur();
          //return true;-------------------- for taking action when press ENTER
        
        }
        if (unicode!=8 && unicode !=9  )
        {
            if (unicode<48 || unicode>57 ) 
                return false 
        }
     }

function sixdigit(val)
{

 if(val.length!=0)
    {
        if((val.length<6) && (val.length>6))
        {
        alert("Account Head Code shouldn't be less or greater than 6 digit number");
        return false;
        }
    }
}
//////////////////////////////////for loading the employee code and description///////////////////////////////////////
function mas_employee(emp_id)
{
     emp_flag=true;
     doFunction('Load_MasterSL_Code',document.getElementById("cmbMas_SL_type").value);
}
function employee_popup_master()
{
    emp_flag=true;
    servicepopup();
}
var winemp;

function servicepopup()
{
    if (winemp && winemp.open && !winemp.closed) 
    {
       winemp.resizeTo(500,500);
       winemp.moveTo(250,250); 
       winemp.focus();
    }
    else
    {
        winemp=null
    }
        
    winemp= window.open("../../../../../org/HR/HR1/EmployeeMaster/jsps/EmpServicePopup.jsp","mywindow1","status=1,height=500,width=500,resizable=YES, scrollbars=yes"); 
    winemp.moveTo(250,250);  
    winemp.focus();
    
}

function doParentEmp(emp)
{
       if(emp_flag==true)
        {
            document.getElementById("txtEmpID_mas").value=emp;
            doFunction('Load_MasterSL_Code',document.getElementById("cmbMas_SL_type").value);
        }
}
/////////////doFunction from Com_Function_SL_Case////////////////////////////////////
function doFunction(Command,param)
{  
   	try
   {
    var addtional_field_value;
   /* try
    {
    	var cmbAcc_UnitCode=document.getElementById("cmbAcc_UnitCode").value;
    	var cmbOffice_code=document.getElementById("cmbOffice_code").value;
    }
    catch(e1)
    {
    	alert(e1.description);
    }*/
    if(Command=="Load_MasterSL_Code")
        {
//        	alert("inside Load_MasterSL_Codeeeeeeeeeee");
    		var cmbSL_type=param;    
            document.getElementById("offlist_div_master").style.display='none';
            document.getElementById("emplist_div_master").style.display='none';
            if(cmbSL_type==7)
              {
            	 document.getElementById("emplist_div_master").style.display='block';
                  //clear_Combo(document.getElementById("cmbMas_SL_Code"));
                  //document.getElementById("txtOfficeID_mas").value="";
                  addtional_field_value=document.getElementById("txtEmpID_mas").value;
                 if(addtional_field_value=="")
                  {
                        clear_Combo(document.getElementById("cmbMas_SL_Code")); 
                        alert("Enter or select the employee code*******");
                        return true;
                  }
              }
            else
              {
                //  document.getElementById("txtEmpID_mas").value="";
              }
           if(cmbSL_type!="")                              // called only not equal to null and 5 is for office
            {
                var cmbMas_SL_type=document.getElementById("cmbMas_SL_type").value;
                var other_dept_off_alias_id=document.getElementById("cmbMas_SL_Code").value;
                
                var url="../../../../../AccountHead_Verification?Command=Load_MasterSL_Code&cmbSL_type="+cmbSL_type+"&cmbMas_SL_type="+cmbMas_SL_type+
                    "&other_dept_off_alias_id="+other_dept_off_alias_id+
                    "&addtional_field_value="+addtional_field_value;
//                alert(url);
                var req=getTransport();
                req.open("GET",url,true); 
                req.onreadystatechange=function()
                {
                   handleResponseNew(req);
                }   
                        req.send(null);
            }
            else if(cmbSL_type=="")
               clear_Combo(document.getElementById("cmbMas_SL_Code")); 
        }
    }
     catch (e) 
     {
       alert("coming to catch"+e.description);
       return false;
     }
} 
/////////////////////////////////////////////   handleResponse()  /////////////////////////////////////////////////////
function handleResponseNew(req)
{  
    if(req.readyState==4)
    { 
        if(req.status==200)
        {  
            
            var baseResponse=req.responseXML.getElementsByTagName("response")[0];
            var tagcommand=baseResponse.getElementsByTagName("command")[0];
            var Command=tagcommand.firstChild.nodeValue;
           
            if(Command=="Load_SL_Code")
            {
                Load_SL_Code(baseResponse);
            }
            else if(Command=="Load_MasterSL_Code")
            {
                Load_MasterSL_Code(baseResponse);
            }
        }
    }
}
/////////////////////////////////////////////  For MASTER Combo SL Code //////////////////////////////////

function Load_MasterSL_Code(baseResponse)
{
 var flag=baseResponse.getElementsByTagName("flag")[0].firstChild.nodeValue;
    if(flag=="success")
    {
         var cmbSL_Code=document.getElementById("cmbMas_SL_Code");      // value assigned to same local variable name
//         alert(flag);
         var items_id=new Array();
         var items_name=new Array();
        
            var cid=baseResponse.getElementsByTagName("cid");
            var cname=baseResponse.getElementsByTagName("cname");
            for(var k=0;k<cid.length;k++)
            {
                items_id[k]=baseResponse.getElementsByTagName("cid")[k].firstChild.nodeValue;
                items_name[k]=baseResponse.getElementsByTagName("cname")[k].firstChild.nodeValue;
               
            }
           
           clear_Combo(cmbSL_Code);
            for(var k=0;k<items_id.length;k++)
            {   
                  var option=document.createElement("OPTION");
                  option.text=items_name[k]+"("+items_id[k]+")";
                  option.value=items_id[k];
                   try
                  {
                      cmbSL_Code.add(option);
                  }
                  catch(errorObject)
                  {
                      cmbSL_Code.add(option,null);
                  }
            }
            
            document.getElementById("cmbMas_SL_Code").value=items_id[0];
            loadName_Mas(items_name[0]);
          if(document.getElementById("cmbMas_SL_type").value==5)
           {
                var state=baseResponse.getElementsByTagName("state")[0].firstChild.nodeValue;
                if(state!="CR")
                alert("Office is not in working status");
           }
           
          if(document.getElementById("cmbMas_SL_type").value==7)
           {
                var state=baseResponse.getElementsByTagName("state")[0].firstChild.nodeValue;
                if(state=="DPN")
                alert("Employee in Deputation");
           }
    }
    else if(flag=="failure")
    {
        alert("No data found");
        var cmbSL_Code=document.getElementById("cmbMas_SL_Code");   // value assigned to same local variable name
        clear_Combo(cmbSL_Code);
    }
}
function clear_Combo(combo)
{       
         var cmbSL_Code=document.getElementById(combo.id);   
         cmbSL_Code.innerHTML="";
         var option=document.createElement("OPTION");
         option.text="--Select Code--";
         option.value="";
         try
         {
        	 cmbSL_Code.add(option);
         }catch(errorObject)
         {
        	 cmbSL_Code.add(option,null);
         } 
}
function checkNull()
{
	var Emp_id = document.getElementById("txtEmpID_mas").value;
	//var txtCB_Month = document.getElementById("txtCB_Month").value;
	if (Emp_id == "") 
	{
		alert("Enter or select the Employee Code--");
		document.FasAcc_Head_Verification.txtEmpID_mas.focus();
		return false;
	} 
	
		var tbody = document.getElementById("tbody");
		var rowcount = tbody.rows.length;
		
		if (rowcount != 0) {
			for ( var i = 0; i < rowcount; i++) 
			{
				if (document.getElementById("verifyAccHead" + i).checked == true) 
				{
					var slno_db1 = document.createElement("input");
					slno_db1.setAttribute("type", "hidden");
					slno_db1.setAttribute("value", i);
					slno_db1.setAttribute("name", "slno_db1" + i);
					slno_db1.setAttribute("id", "slno_db1" + i);
					document.getElementById("FasAcc_Head_Verification").appendChild(slno_db1);
				} 
				else if (document.getElementById("verifyAccHead" + i).checked == false) 
				{
					var slno_db1 = document.createElement("input");
					slno_db1.setAttribute("type", "hidden");
					slno_db1.setAttribute("value", "-1");
					slno_db1.setAttribute("name", "slno_db1" + i);
					slno_db1.setAttribute("id", "slno_db1" + i);
					document.getElementById("FasAcc_Head_Verification").appendChild(slno_db1);
				}
			}
			return true;
		} else {
			alert("No Records Found to Submit...");
			return false;
		}
}
////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
function Verified_list()
{
	startwaiting(document.FasAcc_Head_Verification);
    var MajorGrp=document.FasAcc_Head_Verification.Major_Grp.value;
    var MinorGrp=document.FasAcc_Head_Verification.Minor_Grp.value;
    var fin_year = document.FasAcc_Head_Verification.cmbAccountHead_FY.value;
    winemp= window.open("VerifiedAccountHead_list.jsp?MajorGrp="+MajorGrp+"&MinorGrp="+MinorGrp+"&fin_year="+fin_year,"list1","status=1,height=500,width=600,resizable=YES,scrollbars=yes"); 
    winemp.moveTo(250,250);  
    winemp.focus();
}
function initialload()
{
	var maj_grp=document.AccHeadListForm.Maj_grp.value;
    var min_grp=document.AccHeadListForm.Min_grp.value;
    var fin_year = document.AccHeadListForm.fin_year.value;
    var url1="../../../../../AccountHead_Verification?Command=loadlist&MajorGrp="+maj_grp+"&MinorGrp="+min_grp+"&fin_year="+fin_year;
	var req=getTransport();
	req.open("GET",url1,true); 
	req.onreadystatechange=function()
	{
	        processResponse1(req);
	};   
	req.send(null);
}
function processResponse1(req)
{   
    if(req.readyState==4)   // Completed
      {
          if(req.status==200)   // No error
          {   
              var baseResponse=req.responseXML.getElementsByTagName("response")[0];
              var tagCommand=baseResponse.getElementsByTagName("command")[0];
              var command=tagCommand.firstChild.nodeValue; 
              if(command=="gett")
                      { 
                           getRow(baseResponse);
                      }
              }
        }
    }
function  getRow(baseResponse)
    {     
      var flag=baseResponse.getElementsByTagName("flag")[0].firstChild.nodeValue; 
      if(flag=="success")
        {          
            var tbody = document.getElementById("tblList");
            var table = document.getElementById("Existing");
            var t=0;
            for(t=tbody.rows.length-1;t>=0;t--)
                {
                   tbody.deleteRow(0);
                }                        
            var len=baseResponse.getElementsByTagName("accheadcode").length;  
//            alert(len);
            var seqq=0;
            for(var k=0;k<len;k++)
                {
                     var finyear = baseResponse.getElementsByTagName("finyear")[k].firstChild.nodeValue;
                     var majgroup = baseResponse.getElementsByTagName("majgroup")[k].firstChild.nodeValue;
                     var miinrgroup = baseResponse.getElementsByTagName("minrgroup")[k].firstChild.nodeValue;
                     var verified_by = baseResponse.getElementsByTagName("verified_by")[k].firstChild.nodeValue;
                     var verified_on = baseResponse.getElementsByTagName("verified_on")[k].firstChild.nodeValue;
                     var status = baseResponse.getElementsByTagName("status")[k].firstChild.nodeValue;
                     var accheadcode = baseResponse.getElementsByTagName("accheadcode")[k].firstChild.nodeValue;
                     var accheaddesc = baseResponse.getElementsByTagName("accheaddesc")[k].firstChild.nodeValue;
                     
                     
                     var mycurrent_row=document.createElement("TR");
                     mycurrent_row.id=seqq;
                     var cell=document.createElement("TD");
                     cell.setAttribute('align','right');
                     var tnodefinyear=document.createTextNode(finyear);                         
                     cell.appendChild(tnodefinyear);       
                     mycurrent_row.appendChild(cell);
                  
                     var cell1 =document.createElement("TD");    
                     cell1.setAttribute('align','left');
                     var tnodemajgroup=document.createTextNode(majgroup);                         
                     cell1.appendChild(tnodemajgroup);       
                     mycurrent_row.appendChild(cell1);
                             
                     var cell2 =document.createElement("TD");   
                     cell2.setAttribute('align','left');
                     var tnodemiinrgroup=document.createTextNode(miinrgroup);                         
                     cell2.appendChild(tnodemiinrgroup);       
                     mycurrent_row.appendChild(cell2);
                     
                     var cell2 =document.createElement("TD");   
                     cell2.setAttribute('align','left');
                     var tnodeverified_by=document.createTextNode(verified_by);                         
                     cell2.appendChild(tnodeverified_by);       
                     mycurrent_row.appendChild(cell2);
                     
                     var cell2 =document.createElement("TD");   
                     cell2.setAttribute('align','left');
                     var tnodeverified_on=document.createTextNode(verified_on);                         
                     cell2.appendChild(tnodeverified_on);       
                     mycurrent_row.appendChild(cell2);
                     
                     var cell2 =document.createElement("TD");   
                     cell2.setAttribute('align','left');
                     var tnodestatus=document.createTextNode(status);                         
                     cell2.appendChild(tnodestatus);       
                     mycurrent_row.appendChild(cell2);
                     
                     var cell2 =document.createElement("TD");   
                     cell2.setAttribute('align','left');
                     var tnodeaccheadcode=document.createTextNode(accheadcode);                         
                     cell2.appendChild(tnodeaccheadcode);       
                     mycurrent_row.appendChild(cell2);
                     
                     var cell2 =document.createElement("TD");   
                     cell2.setAttribute('align','left');
                     var tnodeaccheaddesc=document.createTextNode(accheaddesc);                         
                     cell2.appendChild(tnodeaccheaddesc);       
                     mycurrent_row.appendChild(cell2);
                                        
                     tbody.appendChild(mycurrent_row);
                     seqq++;
                }
      }
      else if (len==0 || flag=="failure")
      {
        alert("Failed to Load Values");
      } 
      else
      {
    	  alert("No Data Found");
      }
  }