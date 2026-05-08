//setTimeout("call('loadproject')", 550);
var seq=0;
function clear_Combo(combo)
{
        //alert(combo.id)
        var cmbSL_Code=document.getElementById(combo.id);   
        cmbSL_Code.innerHTML="";
       var option=document.createElement("OPTION");
        option.text="--Select--";
        option.value="";
        try
        {
            cmbSL_Code.add(option);
        }catch(errorObject)
        {
            cmbSL_Code.add(option,null);
        } 
}

function clrForm(param)
{		
		if(param=="cancel")
		{
			    if(window.confirm("Do you want to clear ALL fields ?"))
			    {
			               resetType();
			    }
		}
		//else
			  //  resetType();
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

function projectload()
{
	setTimeout("call('loadproject')", 350);
}


function call(command)
{

	

xmlhttp=getxmlhttpObject();
if(xmlhttp==null)
{
    alert("Your borwser doesnot support AJAX");
    return;
    }  

 if(command=="get")
{ 
	 var cmbOffice_code=document.getElementById("cmbOffice_code").value;
	 var schemestatus=document.getElementById("schemestatus").value;
	
     var url="../../../../../Scheme_Mapping?command=get&schemestatus="+schemestatus+"&cmbOffice_code="+cmbOffice_code+"";
	  url=url+"&sid="+Math.random();
      xmlhttp.open("GET",url,true);
      xmlhttp.onreadystatechange=stateChanged;
      xmlhttp.send(null);  
} 
 else if(command=="getschemetypeid")
 { 
	 var cmbOffice_code=document.getElementById("cmbOffice_code").value;
	 var schemestatus=document.getElementById("schemestatus").value;
	 var schno=document.getElementById("schemepms").value;
    if(schemestatus==4){
    	document.schememapping.level[0].checked=true;
    	document.schememapping.level.disabled=true;
     var url="../../../../../Scheme_Mapping?command=getschemetypeid&schno="+schno+"&cmbOffice_code="+cmbOffice_code+"&schemestatus="+schemestatus+"";
     url=url+"&sid="+Math.random();
     xmlhttp.open("GET",url,true);
     xmlhttp.onreadystatechange=stateChanged;
     xmlhttp.send(null);  
    }
    else{
    	document.schememapping.level.disabled=false;
    	call('getcomponent');
    }
 
  } 
 else if(command=="getcomponent")
 { 
	 var schno=document.getElementById("schemepms").value;
 
 /*     var url="../../../../../Scheme_Mapping?command=getcomponent&schno="+schno+"";
 	  url=url+"&sid="+Math.random();
       xmlhttp.open("GET",url,true);
       xmlhttp.onreadystatechange=stateChanged;
       xmlhttp.send(null);  */
 } 
 else if(command=="loadproject")
{ 
	
	 var cmbOffice_code=document.getElementById("cmbOffice_code").value;
	 alert(cmbOffice_code);
 	   var url="../../../../../Scheme_Mapping_List?command=loadproject&cmbOffice_code="+cmbOffice_code+"";
	 
 	   url=url+"&sid="+Math.random();
       xmlhttp.open("GET",url,true);
       xmlhttp.onreadystatechange=stateChanged;
       
       xmlhttp.send(null);  
 } 
 
 
 else if(command=="load")
 { 
 	
	 if(document.getElementById("txtEmpId").value=="")
		{
			alert('Please Enter Payee Code');
			return false;
		}
		
		var empid=document.getElementById("txtEmpId").value;
		
 	  var url="../../../../../cheque_memo?command=load&empid="+empid+"";
 	 
 	  url=url+"&sid="+Math.random();
       xmlhttp.open("GET",url,true);
       xmlhttp.onreadystatechange=stateChanged;
       xmlhttp.send(null);  
 
 } 

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
            	
                	try{
               	
              		var len=response.getElementsByTagName("schno").length;
                 	var schemepms=document.getElementById("schemepms");
                    var items_id=new Array();
              	    var items_desc=new Array();                        
                          for(var i=0;i<len;i++)
                          {
                        	 items_id[i]=response.getElementsByTagName("schno")[i].firstChild.nodeValue;
                        	items_desc[i]=response.getElementsByTagName("schname")[i].firstChild.nodeValue;  
                            // alert('minor'+items_desc[i]);
                          }
                    clear_Combo(schemepms);
                    
                          //alert('here second');
                          for(var k=0;k<len;k++)
                          {   
                          	//alert(items_name[k]);
                                var option=document.createElement("OPTION");
                                option.text=items_desc[k];
                                option.value=items_id[k];
                             try
                                {
                                	 schemepms.add(option);
                                	
                                }
                                catch(errorObject)
                                {
                                	schemepms.add(option,null);
                                }
                          }
              	
                	}catch(e){alert("Error in lat"+e);}      
                
                }
                else
                    {
                	var schemepms=document.getElementById("schemepms");
                	clear_Combo(schemepms);                                   
                    }
                 }
            if(command=="getschemetypeid")
            {
            	if(flag=='success')
                {

                	try{
                		
                		
                		document.getElementById("schemetypedesc").value =response.getElementsByTagName("schtypedesc")[0].firstChild.nodeValue;
                		document.getElementById("schemetypeid").value=response.getElementsByTagName("schtypeid")[0].firstChild.nodeValue;  
                		
                		}catch(e){alert("Error in lat"+e);}      
                
                }
                else
                    {
                	ocument.getElementById("schemetypedesc").value ="";
            		document.getElementById("schemetypeid").value="";
                    }
            }
            if(command=="loadproject")
            {
            	if(flag=='success')
                {
            		try{
                       	
                		  var len=response.getElementsByTagName("projectid").length;
                		 
                		
                		 
                		  var items_id=new Array();
                		  var items_desc=new Array(); 
                		  var items_compo=new Array(); 
                		  var cmbOffice_code=document.getElementById("cmbOffice_code").value;
                		  tbody = document.getElementById("grid_body");
                		  var t=0;
                	        for(t=tbody.rows.length-1;t>=0;t--)
                	        {
                	           tbody.deleteRow(0);
                	        }
                	        seq=0;
                            for(var i=0;i<len;i++)
                            {
                            	items_id[i]=response.getElementsByTagName("projectid")[i].firstChild.nodeValue;
                            	items_desc[i]=response.getElementsByTagName("projectname")[i].firstChild.nodeValue;  
                            	items_compo[i]=response.getElementsByTagName("component")[i].firstChild.nodeValue; 
                            	var areacode=response.getElementsByTagName("areacode")[i].firstChild.nodeValue; 
                            	var areadesc=response.getElementsByTagName("areadesc")[i].firstChild.nodeValue; 
                            	var statuscode=response.getElementsByTagName("statuscode")[i].firstChild.nodeValue; 
                            	var statusdesc=response.getElementsByTagName("statusdesc")[i].firstChild.nodeValue; 
                            	var typecode=response.getElementsByTagName("typecode")[i].firstChild.nodeValue; 
                            	var typedesc=response.getElementsByTagName("typedesc")[i].firstChild.nodeValue; 
                            	var sch_sno=response.getElementsByTagName("sch_sno")[i].firstChild.nodeValue; 
                            	var scheme_name=response.getElementsByTagName("schemename")[i].firstChild.nodeValue; 
                            	var compo_sno=response.getElementsByTagName("compo_sno")[i].firstChild.nodeValue; 
                            	var compo_desc=response.getElementsByTagName("compo_desc")[i].firstChild.nodeValue; 
                            	var pro_comp=response.getElementsByTagName("pro_comp")[i].firstChild.nodeValue; 
                            	
                            	areacode=dataverify(areacode);
                            	areadesc=dataverify(areadesc);
                            	statuscode=dataverify(statuscode);
                            	statusdesc=dataverify(statusdesc);
                            	typecode=dataverify(typecode);
                            	typedesc=dataverify(typedesc);
                            	sch_sno=dataverify(sch_sno);
                            	scheme_name=dataverify(scheme_name);
                            	compo_sno=dataverify(compo_sno);
                            	compo_desc=dataverify(compo_desc);
                            	pro_comp=dataverify(pro_comp);
                            	
                            	
                            	// alert('minor'+items_desc[i]);
                            	
                            	var mycurrent_row = document.createElement("TR");
                            	seq = seq + 1;
                            	mycurrent_row.id = seq;
                            	var cell2;
                            	
                            	cell2 = document.createElement("TD");
                            	var projectno=document.createElement("input");
                            	projectno.type="hidden";
                            	projectno.name="projectno";
                            	//billno.id="invoice_no";
                            	projectno.value=items_id[i];
                                cell2.appendChild(projectno);
                            	var currentText = document.createTextNode(seq);
                            	cell2.appendChild(currentText);
                            	mycurrent_row.appendChild(cell2);
                            	
                            	cell2 = document.createElement("TD");
                            	var projecname=document.createElement("input");
                            	projecname.type="hidden";
                            	projecname.name="projectname";
                            	//billno.id="invoice_no";
                            	projecname.value=items_desc[i];
                                cell2.appendChild(projecname);
                            	var currentText = document.createTextNode(items_desc[i]);
                            	cell2.appendChild(currentText);
                            	mycurrent_row.appendChild(cell2);

                            	cell2 = document.createElement("TD");
                            	var componame=document.createElement("input");
                            	componame.type="hidden";
                            	componame.name="componame";
                            	componame.value=items_compo[i];
                                cell2.appendChild(componame);
                            	var currentText = document.createTextNode(items_compo[i]);
                            	cell2.appendChild(currentText);
                            	mycurrent_row.appendChild(cell2);

                            	cell2 = document.createElement("TD");
                            	cell2.align="center";
                            	var procomp=document.createElement("select");
                            	
                            	procomp.name="procomp";
                            	procomp.id="pro"+seq;
                            	 var option11=document.createElement("option");    
                                 option11.value="";  
                                 option11.text="--Select--";
                                 try
                                 {
                                	 procomp.add(option11);
                                 }
                              catch(e)
                                 {
                            	  procomp.add(option11,null);
                                 }         

                              var option11=document.createElement("option");    
                              
                              option11.value="P";  
                              option11.text="Project";
                              if(pro_comp=="P")
                            	  option11.selected="selected";	  
                            try
                               {
                            	 procomp.add(option11);
                               }
                             catch(e)
                               {
                            	 procomp.add(option11,null);
                               }
                             
                             var option11=document.createElement("option");    
                             
                             option11.value="C";  
                             option11.text="Component";
                             if(pro_comp=="C")
                           	  option11.selected="selected";	 
                            try
                              {
                            	procomp.add(option11);
                              }
                            catch(e)
                              {
                            	procomp.add(option11,null);
                              }
                               
                                cell2.appendChild(procomp);
                          	   	mycurrent_row.appendChild(cell2);

                          	  var cell = document.createElement("TD");
                          	  var anc = document.createElement("A");
                              var url = "javascript:loadTable('" + cmbOffice_code + "','"+seq+"')";
                          	 anc.href = url;
                          	 var txtedit = document.createTextNode("Select");
                          	 anc.appendChild(txtedit);
                          	 cell.appendChild(anc);
                          	 mycurrent_row.appendChild(cell);
                    	   	
                            	cell2 = document.createElement("TD");
                            	var officestate=document.createElement("input");
                            	officestate.type="hidden";
                            	officestate.name="officestate";
                            	officestate.value=areacode;
                                cell2.appendChild(officestate);
                            	var currentText = document.createTextNode(areadesc);
                            	cell2.appendChild(currentText);
                            	mycurrent_row.appendChild(cell2);
                        	                            	                            	                            	                            	
                            	cell2 = document.createElement("TD");
                            	var schemestatus=document.createElement("input");
                            	schemestatus.type="hidden";
                            	schemestatus.name="schemestatus";
                            	schemestatus.value=statuscode;
                                cell2.appendChild(schemestatus);
                            	var currentText = document.createTextNode(statusdesc);
                            	cell2.appendChild(currentText);
                            	mycurrent_row.appendChild(cell2);

                            	cell2 = document.createElement("TD");
                            	var schemetype=document.createElement("input");
                            	schemetype.type="hidden";
                            	schemetype.name="schemetype";
                            	schemetype.value=typecode;
                                cell2.appendChild(schemetype);
                            	var currentText = document.createTextNode(typedesc);
                            	cell2.appendChild(currentText);
                            	mycurrent_row.appendChild(cell2);

                            	cell2 = document.createElement("TD");
                            	var schemeno=document.createElement("input");
                            	schemeno.type="hidden";
                            	schemeno.name="schemeno";
                            	schemeno.value=sch_sno;
                                cell2.appendChild(schemeno);
                            	var currentText = document.createTextNode(sch_sno);
                            	cell2.appendChild(currentText);
                            	mycurrent_row.appendChild(cell2);
                            	
                            	cell2 = document.createElement("TD");
                            	var schemname=document.createElement("input");
                            	schemname.type="hidden";
                            	schemname.name="schemname";
                            	schemname.value=scheme_name;
                                cell2.appendChild(schemname);
                            	var currentText = document.createTextNode(scheme_name);
                            	cell2.appendChild(currentText);
                            	mycurrent_row.appendChild(cell2);
                            	
                            	cell2 = document.createElement("TD");
                            	var compono=document.createElement("input");
                            	compono.type="hidden";
                            	compono.name="compono";
                            	compono.value=compo_sno;
                                cell2.appendChild(compono);
                            	var currentText = document.createTextNode(compo_sno);
                            	cell2.appendChild(currentText);
                            	mycurrent_row.appendChild(cell2);
                            	
                            	
                            	cell2 = document.createElement("TD");
                            	var componame2=document.createElement("input");
                            	componame2.type="hidden";
                            	componame2.name="componame2";
                            	componame2.value=compo_desc;
                                cell2.appendChild(componame2);
                            	var currentText = document.createTextNode(compo_desc);
                            	cell2.appendChild(currentText);
                            	mycurrent_row.appendChild(cell2);
                            	                            	                           	                            	
                            	tbody.appendChild(mycurrent_row);
                            
                            }
                      
                      
                	}catch(e){alert("Error in lat"+e);}    
                	
                }
                else
                    {
                	 /*var existscheme=document.getElementById("existscheme");
           		  var existcomponent=document.getElementById("existcomponent");
           		 clear_Combo(existscheme);  
           		  clear_Combo(existcomponent);  */
                    }
             }
      
       }
    }
}

var schemescreen;

function loadTable(officeid,serialno)
{
	  if (schemescreen && schemescreen.open && !schemescreen.closed) 
      {
		  schemescreen.resizeTo(500,500);
		  schemescreen.moveTo(250,250); 
		  schemescreen.focus();
      }
      else
      {
    	  schemescreen=null;
      }
	  schemescreen= window.open("Project_Schems_List.jsp?Office_code="+officeid+"&serialno="+serialno+"","mywindow1","resizable=YES, scrollbars=yes"); 
	  schemescreen.moveTo(250,250);    	

}

function doParentScheme(stateid,statusid,schemetypeid,statedesc,statusdesc,schemetypedesc,schemeno,schemename,compono,componame,serialno1)
{
	
	try{
		
	var tbody = document.getElementById("grid_body");
	
	var r=document.getElementById(serialno1);
	var rcells=r.cells;
	var serialno=parseInt(serialno1)-1;   
	
	
	 if(tbody.rows.length==1)
     {
		/* document.schememapping.officestate.value=stateid;
		 document.schememapping.schemestatus.value=statusid;
		 document.schememapping.schemetype.value=schemetypeid;
		 document.schememapping.schemeno.value=schemeno;
		 document.schememapping.schemname.value=schemename;
		 document.schememapping.compono.value=compono;
		 document.schememapping.componame2.value=componame;*/
		 
		 
 try{rcells.item(5).firstChild.value=stateid;}catch(e){}
         
         try{rcells.item(6).firstChild.value=statusid;}catch(e){}
              
         try{rcells.item(7).firstChild.value=schemetypeid;}catch(e){}
       
         try{rcells.item(8).firstChild.value=schemeno;}catch(e){}
        
         try{rcells.item(9).firstChild.value=schemename;}catch(e){}
             
         try{rcells.item(10).firstChild.value=compono;}catch(e){}
         
         try{rcells.item(11).firstChild.value=componame;}catch(e){}
		 
		 
     }else{
	/* alert(serialno);
		alert("Hey"+document.schememapping.officestate[serialno]);
    	 document.schememapping.officestate[serialno].value=stateid;
		 document.schememapping.schemestatus[serialno].value=statusid;
		 document.schememapping.schemetype[serialno].value=schemetypeid;
		 document.schememapping.schemeno[serialno].value=schemeno;
		 document.schememapping.schemname[serialno].value=schemename;
		 document.schememapping.compono[serialno].value=compono;
		 document.schememapping.componame2[serialno].value=componame;*/
    	 
    	 
    	 try{rcells.item(5).firstChild.value=stateid;}catch(e){}
         
         try{rcells.item(6).firstChild.value=statusid;}catch(e){}
              
         try{rcells.item(7).firstChild.value=schemetypeid;}catch(e){}
       
         try{rcells.item(8).firstChild.value=schemeno;}catch(e){}
        
         try{rcells.item(9).firstChild.value=schemename;}catch(e){}
             
         try{rcells.item(10).firstChild.value=compono;}catch(e){}
         
         try{rcells.item(11).firstChild.value=componame;}catch(e){}
    	 
    	 
    	 
     }
	
		try{rcells.item(5).lastChild.nodeValue=statedesc;}catch(e){}
            
        try{rcells.item(6).lastChild.nodeValue=statusdesc;}catch(e){}
             
        try{rcells.item(7).lastChild.nodeValue=schemetypedesc;}catch(e){}
      
        try{rcells.item(8).lastChild.nodeValue=schemeno;}catch(e){}
       
        try{rcells.item(9).lastChild.nodeValue=schemename;}catch(e){}
            
        try{rcells.item(10).lastChild.nodeValue=compono;}catch(e){}
        
        try{rcells.item(11).lastChild.nodeValue=componame;}catch(e){}
        
	}catch(e){alert(e);}
	
}

function dataverify(data)
{
	var emptydata="";
if(data=='--')
{
return emptydata;	
}
else
{
return data;	
}

}


function checknull()
{
	var tbody=document.getElementById("grid_body");
	if(tbody.rows.length==0)
	{
	    alert("No Data to Update");
	  	    return false; 
	}	

    var flag=0;
	var currRow = document.getElementById("grid_body").rows.length;
	for(i=0;i<currRow;i++){
	//	alert(document.getElementById("grid_body").rows[i].cells.item(6).lastChild.nodeValue.length);
	if(document.getElementById("grid_body").rows[i].cells.item(6).lastChild.nodeValue.length>0)
	{
		
		flag=1;
	    var j=i+1;
		var idd="pro"+(j);
		if(document.getElementById(idd).value=="")	
     	{
		alert('Please Select Project/Component ');
		return false;
	    }
	}
			
	}
if(flag==0)
{
alert('You are not filled any row. Please fill');
return false;
}

return true;
}