onerror=handleErr;
var txt="";
function handleErr(msg,url,l)
{
	txt="There was an error on this page.\n\n";
	txt+="Error: " + msg + "\n";
	txt+="URL: " + url + "\n";
	txt+="Line: " + l + "\n\n";
	txt+="Click OK to continue.\n\n";
	alert(txt);
	return true;
}

/** To create javascript request object **/
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
var Office_list_SL;
function Show(assetdesc,assetcode,finyr)
{
    if (Office_list_SL && Office_list_SL.open && !Office_list_SL.closed) 
    {
       Office_list_SL.resizeTo(500,500);
       Office_list_SL.moveTo(250,250); 
       Office_list_SL.focus();
    }
    else
    {
        Office_list_SL=null;
    }
    Office_list_SL= window.open("A52_Abstract_Circle_List_MIS.jsp?assetdesc="+assetdesc+"&assetcode="+assetcode+"&finyr="+finyr,"A52_Abstract_Circle","status=1,height=500,width=500,resizable=YES, scrollbars=yes"); 
    Office_list_SL.moveTo(250,250);  
    Office_list_SL.focus();
    
}

window.onunload=function()
{
if (Office_list_SL && Office_list_SL.open && !Office_list_SL.closed) Office_list_SL.close();
}

function checkNull_verify()
{
	  var tbody=document.getElementById("tblList");
	if(tbody.rows.length==0){
	alert("No values Found");
	return false;
	}
}

 function Exit()
 {
    self.close();
 }
 
//******************************************** CallServer Coding *******************//
function callServer(command){  
	   var financial_year = document.frmA52_Abstract_mis.cmbFinancialYear.value;  
       var url="";
        if(command=="Go")
        {  
        	
        	if(checkNull()){
        		
        			url="../../../../../A52_Abstract_MIS?command=GoGet&financial_year="+financial_year;
        			
        			var req=getTransport();
                    req.open("POST",url,true);        
                    req.onreadystatechange=function()
                    {
                       processResponse(req);
                    };   
                    req.send(null);
        		    }
        	
        }
     
}  
function checkNull(){
	
	   var financial_year = document.frmA52_Abstract_mis.cmbFinancialYear.value;  
	   if(financial_year==""){
		   alert("select Finanical year");
		   return false;
	   }
	   return true;
	
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
    	  var flag=baseResponse.getElementsByTagName("flag")[0].firstChild.nodeValue; 
    	       
              
               
              if(command=="GoGet"){
            	 // alert("goget");
            	  getRow(baseResponse);
            	  
              }

    	  }
     }
}

function  getRow(baseResponse)
{  
var seq=1;
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
        var len=baseResponse.getElementsByTagName("count")[0].firstChild.nodeValue;  
       
        if(len==0){
        	alert("No records Exists");

        }else{
        	 var lll=1;
        	 var item = new Array();
        	
               for(var k=0;k<len;k++)
                  {
            		
                 
            		item[0] =baseResponse.getElementsByTagName("asset_major_class_desc")[k].firstChild.nodeValue;
					item[1] =baseResponse.getElementsByTagName("closing_qty")[k].firstChild.nodeValue;
					item[2] =baseResponse.getElementsByTagName("closing_value")[k].firstChild.nodeValue;
					item[3] =baseResponse.getElementsByTagName("bookvalue")[k].firstChild.nodeValue;
					item[4]=baseResponse.getElementsByTagName("DEPRE_ALLOWED_YR")[k].firstChild.nodeValue;
					item[5] =baseResponse.getElementsByTagName("asset_major_class_code")[k].firstChild.nodeValue;
					item[6]=baseResponse.getElementsByTagName("balance")[k].firstChild.nodeValue;
					item[7]=baseResponse.getElementsByTagName("financial_year")[k].firstChild.nodeValue;
					
                       var mycurrent_row=document.createElement("TR");
                       mycurrent_row.id=item[0];                     
                                			
            		
            			
            			var cellpar = document.createElement("TD");
            			var particulars_set=document.createElement("label");
            			/*particulars_set.name="Particulars"+seq;
            			particulars_set.id="Particulars"+seq;*/
            			particulars_set.innerHTML=item[0];
            			particulars_set.size=7;
            			cellpar.appendChild(particulars_set);
            			cellpar.style.textAlign="left";
               			mycurrent_row.appendChild(cellpar);
               			
               			var cellpar = document.createElement("TD");
            			var particulars_set=document.createElement("label");
            			/*particulars_set.name="Particulars"+seq;
            			particulars_set.id="Particulars"+seq;*/
            			particulars_set.innerHTML=item[1];
            			particulars_set.size=7;
            			cellpar.appendChild(particulars_set);
            			cellpar.style.textAlign="right";
               			mycurrent_row.appendChild(cellpar);
               
               			var cell6 = document.createElement("TD");
           			var rec_ob_label = document.createElement("label");
           			rec_ob_label.innerHTML=item[2];	
           			rec_ob_label.style.textAlign="right";
           			cell6.appendChild(rec_ob_label);
           			mycurrent_row.appendChild(cell6);
           			
           			var cell6 = document.createElement("TD");
           			var rec_ob_label = document.createElement("label");
           			rec_ob_label.innerHTML=item[3];
           			rec_ob_label.style.textAlign="right";
           			cell6.appendChild(rec_ob_label);
           			mycurrent_row.appendChild(cell6);
           			
           			var cell=document.createElement("TD");
                    cell.align='CENTER';
                    var anc=document.createElement("A");
                    var url="javascript:Show('"+item[0]+"','"+item[5]+"','"+item[7]+"')";//,'"+txtCB_Month+"'
                    anc.href=url;
                    var txtedit=document.createTextNode("Circle Wise BreakUp");
                    anc.appendChild(txtedit);
                    cell.appendChild(anc);
                    mycurrent_row.appendChild(cell);
           			
                    tbody.appendChild(mycurrent_row);
                    seq++;  
                  }

        	
        }
         }
  else
  {
    alert("Failed to Load Values");
  }           
}

function clearAll()
{
	 
	  document.getElementById('cmbFinancialYear').value = "selected";
	
	  var tbody = document.getElementById("tblList");
      
      var table = document.getElementById("Existing");
      var t=0;
      for(t=tbody.rows.length-1;t>=0;t--)
          {
             tbody.deleteRow(0);
          } 

}


/*
function loadUnits()
{
	
	var unitid=document.getElementById("cmbAcc_UnitCode").value;
	var url ="../../../../../A52_Abstract_MIS?command=loadUnitRendering&unit_id="+unitid ;
	//alert(url);		
	
	var xmlrequest = getTransport();
	xmlrequest.open("GET", url, true);
	xmlrequest.onreadystatechange = function() {
		
		manipulate1(xmlrequest);
	};
	xmlrequest.send(null);	
}
function loadUnitsDelete() {
	 var unit_rendered = document.getElementById("cmbOffice_code");
	 unit_rendered.length=0;
	 var codeHeads = "--All Units--";
    
               var opt1 = document.createElement('option');
               opt1.value = 0;
               opt1.innerHTML = codeHeads; //instead of using textnode ,we use innerhtml
               unit_rendered.appendChild(opt1);             
               return true;
	
}*/
/*
function manipulate1(xmlrequest) {

	if (xmlrequest.readyState == 4) {
	
		if (xmlrequest.status == 200) {
			

			var baseResponse1 = xmlrequest.responseXML
			.getElementsByTagName("response")[0];
			
			
			var tagCommand = baseResponse1.getElementsByTagName("command")[0];
		

			var command = tagCommand.firstChild.nodeValue;
			
			
			 if (command=="unitLoad")
			{
				
				 var i = 0;
					var flag = baseResponse1.getElementsByTagName("flag")[0].firstChild.nodeValue;
					var count = baseResponse1.getElementsByTagName("count")[0].firstChild.nodeValue;
				    if(flag=="success"){
				    	var len4 = baseResponse1.getElementsByTagName("unit_No").length;
				    	var se = document.getElementById("cmbOffice_code");
				    	se.length=0;
				    	//alert("len4 "+len4);
						for (i=0 ; i < len4; i++) {
							var unit_No = baseResponse1.getElementsByTagName("unit_No")[i].firstChild.nodeValue;
							var desc = baseResponse1.getElementsByTagName("desc")[i].firstChild.nodeValue;
							var op = document.createElement("OPTION");
							op.value = unit_No;
							var txt = document.createTextNode(desc);
							op.appendChild(txt);
							se.appendChild(op);
						}
				    	var len4 = baseResponse1.getElementsByTagName("unit_No").length;
				    	var se = document.getElementById("cmbOffice_code");
				    	se.length=0;
				    	//alert("len4 "+len4);
						for (i=0 ; i < len4; i++) {
							var unit_No = baseResponse1
									.getElementsByTagName("RENDERING_UNIT_OFFICE_ID")[i].firstChild.nodeValue;
							var desc = baseResponse1
									.getElementsByTagName("desc")[i].firstChild.nodeValue;
							var op = document.createElement("OPTION");
							op.value = unit_No;
							var txt = document.createTextNode(desc);
							op.appendChild(txt);
							se.appendChild(op);
						}
				        }
				        else
				        {	
				        alert("No Record Exist");
				        document.frmA52_Register_OBEntry.allid[0].checked=true;
				        document.frmA52_Register_OBEntry.allid[1].checked=false;
				        }
				//unitLoad(baseResponse);
			}
			
	}
}
}

*/




function exitfun() {
	window.close();
}
