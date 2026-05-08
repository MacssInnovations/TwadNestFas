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
//////////////////////////////////////////////////////////////////////////////////////////////////////////////////
function exitmethod()
{
      window.close();
}
//////////////////////////////////////////////////////////////////////////////////////////////////////////////////
/* Loading the grid value depends on the office who login in the session*/
function showDetails()
    {
        var acc_unit_code=document.getElementById("cmbAcc_UnitCode").value;
        var acc_off_code=document.getElementById("cmbOffice_code").value;
        var CivilAgree_date= document.getElementById("txtCivilAgreeDate").value;
        var url="../../../../../VerifyCivilAgreement?command=loadUnverifiedCA&acc_unit_code1="+acc_unit_code+
        "&acc_off_code1="+acc_off_code+"&CivilAgree_date1="+CivilAgree_date;
        //alert(url);
        if(CivilAgree_date.length!=0)
            {
                var req=getTransport();
                req.open("GET",url,true); 
                req.onreadystatechange=function()
                {
                   handleResponse(req);
                }   
                        req.send(null);
            }
    }
    
function handleResponse(req)
{  
    if(req.readyState==4)
    {
        if(req.status==200)
        {   
            var baseResponse=req.responseXML.getElementsByTagName("response")[0];
            var tagcommand=baseResponse.getElementsByTagName("command")[0];
            var Command=tagcommand.firstChild.nodeValue;
            if(Command=="loadUnverifiedCA")
            {
                PendingCivilAgreement(baseResponse);
            }
        }
    }
}


function PendingCivilAgreement(baseResponse)
{
  var flag=baseResponse.getElementsByTagName("flag")[0].firstChild.nodeValue;
     if(flag=="success")
     {
        var tbody=document.getElementById("grid_body");
        var t=0;
        for(t=tbody.rows.length-1;t>=0;t--)
        {
           tbody.deleteRow(0);
        }
        
        document.getElementById("details_disp").style.display="inline";
        //document.getElementById("grid_body").style.display="inline";
        var AgreementNo=baseResponse.getElementsByTagName("AgreementNo");
        
        var items=new Array();
        var Ucode=document.getElementById("cmbAcc_UnitCode").value;
        var Offid=document.getElementById("cmbOffice_code").value;
        for(var k=0;k<AgreementNo.length;k++)
        {
             items[0]=baseResponse.getElementsByTagName("AgreementNo")[k].firstChild.nodeValue;   
             items[1]=baseResponse.getElementsByTagName("AgreementDate")[k].firstChild.nodeValue;   
             items[2]=baseResponse.getElementsByTagName("NameofWork")[k].firstChild.nodeValue;
             items[3]=baseResponse.getElementsByTagName("SubLedgertypeCode")[k].firstChild.nodeValue;
             items[4]=baseResponse.getElementsByTagName("ValueofWork")[k].firstChild.nodeValue;
             items[5]=baseResponse.getElementsByTagName("WorkOrderNo")[k].firstChild.nodeValue;
             items[6]=baseResponse.getElementsByTagName("WorkOrderDate")[k].firstChild.nodeValue;
             items[7]=baseResponse.getElementsByTagName("remarks")[k].firstChild.nodeValue;
            
             if(items[7]=="null")
              items[7]="";
             tbody=document.getElementById("grid_body");
             
                var mycurrent_row=document.createElement("TR");
                
                var cell2;
                
                     /** Displaying Agreement Number */   
                   
                     cell2=document.createElement("TD");
        
                          var Agreement_No=document.createElement("input");
                          Agreement_No.type="hidden";
                          Agreement_No.name="Agreement_No";
                          Agreement_No.value=items[0];
                          cell2.appendChild(Agreement_No);
                          var currentText=document.createTextNode(items[0]);
                          cell2.appendChild(currentText);
                          mycurrent_row.appendChild(cell2);
                           
                     /** Displaying Agreement Date */      
                           
                     cell2=document.createElement("TD");
                          var Agreement_Date=document.createElement("input");
                          Agreement_Date.type="hidden";
                          Agreement_Date.name="Agreement_Date";
                          Agreement_Date.value=items[1];
                          cell2.appendChild(Agreement_Date); 
                          var currentText=document.createTextNode(items[1]);
                          cell2.appendChild(currentText);
                          mycurrent_row.appendChild(cell2);
                        
                     /** Displaying NameofWork */ 
                        
                     cell2=document.createElement("TD");
                          var Nameof_Work=document.createElement("input");
                          Nameof_Work.type="hidden";
                          Nameof_Work.name="Nameof_Work";
                          Nameof_Work.value=items[2];
                          cell2.appendChild(Nameof_Work);                       
                          var currentText=document.createTextNode(items[2]);
                          cell2.appendChild(currentText);
                          mycurrent_row.appendChild(cell2);
                          
                     
                     /** Displaying ValueofWork */ 
                     
                     cell2=document.createElement("TD");
                        cell2.align="right"
                        var Valueof_Work=document.createElement("input");
                        Valueof_Work.type="hidden";
                        Valueof_Work.name="Valueof_Work";
                        Valueof_Work.value=items[4];
                        cell2.appendChild(Valueof_Work);
                        var currentText=document.createTextNode(items[4]);
                        cell2.appendChild(currentText);
                        mycurrent_row.appendChild(cell2);
                      
                      /** Displaying WorkOrderNo */ 
                     
                     cell2=document.createElement("TD");
                        var WorkOrder_No=document.createElement("input");
                        WorkOrder_No.type="hidden";
                        WorkOrder_No.name="WorkOrder_No";
                        WorkOrder_No.value=items[5];
                        cell2.appendChild(WorkOrder_No);
                        var currentText=document.createTextNode(items[5]);
                        cell2.appendChild(currentText);
                        mycurrent_row.appendChild(cell2);
                        
                        /** Displaying WorkOrderDate */ 
                     
                     cell2=document.createElement("TD");
                        var WorkOrder_Date=document.createElement("input");
                        WorkOrder_Date.type="hidden";
                        WorkOrder_Date.name="WorkOrder_Date";
                        WorkOrder_Date.value=items[6];
                        cell2.appendChild(WorkOrder_Date);
                        var currentText=document.createTextNode(items[6]);
                        cell2.appendChild(currentText);
                        mycurrent_row.appendChild(cell2);
                        
                        /** Displaying remarks */ 
                     
                     cell2=document.createElement("TD");
                        var remarks=document.createElement("input");
                        remarks.type="hidden";
                        remarks.name="remarks";
                        remarks.value=items[7];
                        cell2.appendChild(Nameof_Work);
                        var currentText=document.createTextNode(items[7]);
                        cell2.appendChild(currentText);
                        mycurrent_row.appendChild(cell2);
                       
                     /** Displaying Check Box For Cancellation */
                     
                        var cell=document.createElement("TD");
                        cell.align='CENTER';
                        var anc=document.createElement("input");
                        anc.type="checkbox";
                        anc.value="CHECKED";
                        //anc.setAttribute("onclick", "checkDisplay()");
                        anc.id="verify_select";
                        anc.name="verify_select"; 
                        cell.appendChild(anc);
                        
                        var anc1=document.createElement("input");
                        anc1.type="hidden";
                        
                        //anc.setAttribute("onclick", "checkDisplay()");
                        anc1.id="verify_select_status";
                        anc1.name="verify_select_status"; 
                        cell.appendChild(anc1);
                        
                        mycurrent_row.appendChild(cell);                         
                     
                     
              tbody.appendChild(mycurrent_row);
        }
        
     }
    else if(flag=="failure")
     {
        alert("No Record found for Verification");
        var tbody=document.getElementById("grid_body");
        var t=0;
        for(t=tbody.rows.length-1;t>=0;t--)
        {
           tbody.deleteRow(0);
        }
     }
     else if(flag=="nodata")
     {
    	 var tbody=document.getElementById("grid_body");
         var t=0;
         for(t=tbody.rows.length-1;t>=0;t--)
         {
            tbody.deleteRow(0);
         }
        alert("No Record found for Verification");
     }
}
function checkStatus(){
    var checkbox = document.getElementsByName('verify_select');
    var chvalue=document.getElementsByName("verify_select_status");
    var ln = checkbox.length;
    //alert(ln+" "+chvalue.length);
    
	//var tby=document.getElementById("grid_body").rows[i];//.item(1).firstChild.nodeValue;	
	for(var i=0;i<ln;i++){
		//alert("looofp ");
	try{
		 // alert(ln+" "+chvalue.length);
	if(checkbox[i].checked==true){
		//alert("checked");
		checkbox[i].value="CHECKED";
		chvalue[i].value="CHECKED";
		//document.getElementByName("verify_select_status").value="CHECKED";
		//verify_select_status
		
	}else{
		//alert("unchecked");
		//document.getElementById("verify_select")[i].value="UNCHECKED";
		checkbox[i].value="UNCHECKED";
		chvalue[i].value="UNCHECKED";
		//document.getElementByName("verify_select_status").value="UNCHECKED";
		
	}
	//alert(checkbox[i].value);
	}catch(e){
		alert(e);
	}
	//alert("end");
	
	}
	//return false;
}
/*function checkDisplay(){
	alert("checkDisplay ");
	//anc.value="CHECKED";
	if(document.getElementById("verify_select").checked==true){
		alert("checked");
		document.getElementById("verify_select").value="CHECKED";
	}else{
		alert("unchecked");
		document.getElementById("verify_select").value="UNCHECKED";
	}
}*/
function call_clr()
{

        //document.getElementById("cmbAcc_UnitCode").value="";
        //document.getElementById("cmbOffice_code").value="";
        //document.getElementById("txtCivilAgreeDate").value="";
        var tbody=document.getElementById("grid_body");
        var t=0;
        for(t=tbody.rows.length-1;t>=0;t--)
        {
           tbody.deleteRow(0);
        }
}


function exit()
{
       self.close();
}


function checkNull()
{
  var tbody=document.getElementById("grid_body");
  if(tbody.rows.length==0) 
   {
    alert("No data for verification");
    //document.getElementById("txtAmount").focus();
    return false; 
   }
  return true;
}

    
    