var seq=0;
var seq_gl=100;
var com_id=0;
var com_id_gl=0;
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

function checkTDA()
{
	var head=document.getElementById("txtAcc_HeadCode").value;
	if(head==900108 || head==900109)
		{
		alert("TDA Account Head is not Allowed Here");
		document.getElementById("txtAcc_HeadCode").value="";
		return false;
		}
	else if(head==901001 || head==901002)
		{
		alert("TCA Account Head is not Allowed Here");
		document.getElementById("txtAcc_HeadCode").value="";
		return false;
		}
	else
		{
		doFunction('checkCodeTPA','null');
		}
	
	if(document.frm_TPA_Raised_Create_others.Org_CR_DR[0].checked==true)
	{
			if(document.getElementById("txtAcc_HeadCode").value==620101)
			{
					alert("This Account Head already Exists ");
					document.getElementById("txtAcc_HeadCode").value="";
					document.getElementById("txtAcc_HeadDesc").value="";
					document.getElementById("txtAcc_HeadCode").focus();
					return false;
			}
                           
	}
	else
	{			
			if(document.getElementById("txtAcc_HeadCode").value==900301)
			{
					alert("This Account Head already Exists ");
					document.getElementById("txtAcc_HeadCode").value="";
					document.getElementById("txtAcc_HeadDesc").value="";
					document.getElementById("txtAcc_HeadCode").focus();
					return false;
			}
                          
	}
	
}

function loadTransferUnit()
{       
		 var cmbAcc_UnitCode=document.getElementById("cmbAcc_UnitCode").value;		 
         url="../../../../../TDA_Raised_Create?command=loadTransferUnit_tpa&txtUnitId="+cmbAcc_UnitCode;
        // alert(url);
         req=getTransport();
         req.open("GET",url,true);        
         req.onreadystatechange=function()
         {        	  
                TDA_Raised_ServletResponse(req);
         }   
         req.send(null);  
        
}
function TDA_Raised_ServletResponse(req)
{
		 
                 if(req.readyState==4)
		 {
                if(req.status==200)
                {  
                        var baseResponse=req.responseXML.getElementsByTagName("response")[0];
                      //  alert(baseResponse);
                        var tagcommand=baseResponse.getElementsByTagName("command")[0];
                        var Command=tagcommand.firstChild.nodeValue;                                  
                        var flag=baseResponse.getElementsByTagName("flag")[0].firstChild.nodeValue;
                        if(Command=="loadTransferUnit" ||Command=="loadTransferUnit_tpa")
                        {                                       
                             
                               if(flag=="success")
                               {                                      
                                       var txtUnitId=document.getElementById("TransferedID");  
                                       var child=txtUnitId.childNodes;
                                       for(var i=child.length-1;i>1;i--)
                                       {
                                    	   		txtUnitId.removeChild(child[i]);
                                       }                                              
                                       var items_id=new Array();
                                       var items_name=new Array();                                    
                                       var oid=baseResponse.getElementsByTagName("unit_id");
                                       for(var k=0;k<oid.length;k++)
                                       {
                                                items_id[k]=baseResponse.getElementsByTagName("unit_id")[k].firstChild.nodeValue;
                                                items_name[k]=baseResponse.getElementsByTagName("unit_name")[k].firstChild.nodeValue;				       	                                                  
                                               //document.getElementById("myDiv").innerHTML=txt;
                                                var option=document.createElement("OPTION");
                                                option.text=items_name[k]+"("+items_id[k]+")";
                                                option.value=items_id[k];
                                                try
                                                {
                                                        txtUnitId.add(option);
                                                }
                                                catch(errorObject)
                                                {
                                                        txtUnitId.add(option,null);
                                                }
                                       }
                               }
                               else
                               {                                                   
                                       document.getElementById("txtUnitId").value="";
                               }
                       }
              }
		 }    
}


function checkNull()
{ 
	
	var rows;
	var noSL=0,eq=0;
	  var tbody=document.getElementById("grid_body_sl");
	  if(document.getElementById("Voucher_Date").value.length==0)
        {
	            alert("Enter the date of creation");           
	            return false;    
        }
        else if(document.getElementById("TransferedID").value=="")
        {
            	alert("Select transfered office id");
            	return false;
        }
        else if(document.getElementById("Reason4Trf").value=="")
        {
            	alert("Select reason for transfer");
            	return false;
        }
        else if(document.getElementById("GenParticulars").value=="")
        {
            	alert("Enter Particulars");
            	return false;
        }
	   
       if(tbody.rows.length==0)
       {
           alert("Enter the Details Part Of SL");
           // document.getElementById("txtAmount").focus();
           return false; 
       }
       else if(tbody.rows.length>0)
       {
    	  
    	   var cr_amt=0;
    	   var db_amt=0;
    	   rows=tbody.getElementsByTagName("tr");
    	   var arr=new Array();
    	  
    	   for(i=0;i<rows.length;i++)
           {
    				   var cells=rows[i].cells;
            		 
            		   if(cells.item(2).firstChild.value=='CR')
                       {
            			 
                           cr_amt=parseFloat(cr_amt) + parseFloat(cells.item(5).lastChild.nodeValue);
                       }
            		   else
            		   {
            			   db_amt=parseFloat(db_amt) + parseFloat(cells.item(5).lastChild.nodeValue);
            		   }
            		 
            		   arr[i]=cells.item(1).firstChild.value;
           			   
           }
    	  
    	  
       }
       if(parseFloat(cr_amt)!=parseFloat(db_amt))      // Either CR or DR must
		{
		alert("Amount doesn't Tally.. Difference in SL is=" +(parseFloat(cr_amt)-parseFloat(db_amt)));
		return false;
		} 
     
      var rows_gl;
 	  var tbody_gl=document.getElementById("grid_body_gl");
 	 if(tbody_gl.rows.length==0)
     {
         alert("Enter the Details Part Of GL");
         // document.getElementById("txtAmount").focus();
         return false; 
     }
     else if(tbody_gl.rows.length>0)
     {
    	
    	 var arr_gl=new Array();
    	var amt_gl=0;
  	  // var db_amt_gl=0;
  	 rows_gl=tbody_gl.getElementsByTagName("tr");
  	
  	   for(i=0;i<rows_gl.length;i++)
         {
  				   var cells=rows_gl[i].cells;
          		 
          		   if(cells.item(2).firstChild.value=='CR')
                     {
          			 
          			 amt_gl=parseFloat(amt_gl) + parseFloat(cells.item(3).lastChild.nodeValue);
                     }
          		   else
          		   {
          			 amt_gl=parseFloat(amt_gl) + parseFloat(cells.item(3).lastChild.nodeValue);
          		   }
          		 arr_gl[i]=cells.item(1).firstChild.value; 
          		 
          		//this loop is for checking SL heads should be compulsory in GL Grid 
          		
          	  	 for(var t=1;t<arr.length;t++)//arr is sl headcode
          		 {
          	  		 	if(arr[t]==arr_gl[i])
          	  		 	{
          	  		 		eq++;
          	  		 	}
          	  		 	else
          	  		 	{
          	  		 		noSL++;
          	  		 	}
          		 } 
          	
          	  	var amt=0;
          	  	var amountCount=0;
          		for(var t=1;t<arr.length;t++)//sl
          	 	 {
          	 		if(arr[t]==arr_gl[i])//1==0
          	 		{
          	 			var tbody=document.getElementById("grid_body_sl");
          	 		 rows=tbody.getElementsByTagName("tr");
              	    
          	 		 amt=amt+parseInt(rows[t].cells.item(5).firstChild.value);
          	 		
          	 		amountCount++;
          	 			
          	 		}
          	 	 }
          		if(amountCount>0)
          		{
          			if(amt==cells.item(3).lastChild.nodeValue)
      	 			{
      	 				//alert("amount equals");
      	 			}
      	 			else
      	 			{
      	 				alert("SL and GL Amount don't Tally for The Head "+arr_gl[i]);
      	 				return false;
      	 			}
          			
          		}
         }
  	   
     }
 	
   
      if(noSL>0)
       {
    	   if(eq>=(arr.length-1))
    	   {
    		   
    	   }
    	   else{
    	   alert("All SL Heads should be in GL TAb");
    	   return false;
    	   }
       }
         if(document.getElementById("Amount").value=="")
         {
            	alert("Enter TPA General amount");
            	return false;
         }      
        
         else 
         {
             
        	 return true;
         }       
       
       		
}

function clear_Combo(combo)
{       	    
       
         var cmbSL_Code=document.getElementById(combo.id);   
         //alert(combo.id);
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
function call_mainJSP_script(fromcal_dateCtrl,blr_flag)     /////////Calender control return value handling
{
        if(blr_flag==1)         // which is used to find the receipt or voucher or payment (creation) date calling field,if so check trial balance
        {                            
                var cmbAcc_UnitCode=document.getElementById("cmbAcc_UnitCode").value;
                var cmbOffice_code=document.getElementById("cmbOffice_code").value;
                var TB_date=fromcal_dateCtrl.value;                 
                if(fromcal_dateCtrl.value.length!=0)
                {
                        var url="../../../../../Receipt_SL.view?Command=check_TB&TB_date="+TB_date+"&cmbAcc_UnitCode="+cmbAcc_UnitCode+"&cmbOffice_code="+cmbOffice_code;                         
                        var req=getTransport();
                        req.open("GET",url,true); 
                        req.onreadystatechange=function()
                        {
                                  check_TB(req,fromcal_dateCtrl);
                        }   
                        req.send(null);
                }
        }
}

function loadAccDesc()
{
	 var txtAcc_HeadCode_gl=document.getElementById("txtAcc_HeadCode_gl").value;
		if(txtAcc_HeadCode_gl>=6)
		    {
			
					if(txtAcc_HeadCode_gl==900108 || txtAcc_HeadCode_gl==900109)
					{
					alert("TDA Account Head is not Allowed Here");
					document.getElementById("txtAcc_HeadCode_gl").value="";
					return false;
					}
				else if(txtAcc_HeadCode_gl==901001 || txtAcc_HeadCode_gl==901002)
					{
					alert("TCA Account Head is not Allowed Here");
					document.getElementById("txtAcc_HeadCode_gl").value="";
					return false;
					}
					
				else{
				        var url="../../../../../Receipt_SL.view?Command=checkCode&txtAcc_HeadCode="+txtAcc_HeadCode_gl;                
				      
				        var req=getTransport();
				        req.open("GET",url,true); 
				        req.onreadystatechange=function()
				        {
				           handleResponse_gl(req);
				        }   
				                req.send(null);
				    }
		    }  
	   
	  
}

function handleResponse_gl(req)
{  
    if(req.readyState==4)
    { 
        if(req.status==200)
        {  
            
            var baseResponse=req.responseXML.getElementsByTagName("response")[0];
            var tagcommand=baseResponse.getElementsByTagName("command")[0];
            var Command=tagcommand.firstChild.nodeValue;
           
            if(Command=="checkCode")
            {
                loadcheckCode_gl(baseResponse);
            }
            
        }
    }
}


function gl_add()
{
	if(document.getElementById("txtsub_Amount_gl").value=="")
	{
		alert("Enter GLAmount");
		return false;
	}
	 tbody_gl=document.getElementById("grid_body_gl");
	 var hcgl=document.getElementById("txtAcc_HeadCode_gl").value;
	 /*	 var hcgl=document.getElementById("txtAcc_HeadCode_gl").value;
	 if(hcgl==620101)
	 {
		// document.getElementById("txtAcc_HeadCode_gl").readonly=true;
		 document.getElementById("txtAcc_HeadCode_gl").disabled=true;
	 }
	 else
	 {
		 document.getElementById("txtAcc_HeadCode_gl").disabled=false;
	 }  */
	// alert(tbody_gl.rows.length);
	 if(tbody_gl.rows.length>0)
     {
		// alert("test");
		  rows=tbody_gl.getElementsByTagName("tr");
		  for(i=0;i<rows.length;i++)
          {
   				   var cells=rows[i].cells;
           		 
   		   var hcgrid=cells.item(1).firstChild.value;
   		   if(hcgl==hcgrid)
              {
   			   alert("This Account Head is Allowed Only once");
   			   return false;
              }
          }
     }  
	 //if(tbody_gl.)
	 var items=new Array();
     
     items[0]=document.getElementById("txtAcc_HeadCode_gl").value;
     items[1]=document.getElementById("txtAcc_HeadDesc_gl").value;   
    
     if(document.frm_TPA_Raised_Create_others.rad_sub_CR_DR_gl[0].checked==true)
    		items[2]=document.frm_TPA_Raised_Create_others.rad_sub_CR_DR_gl[0].value;
     else if(document.frm_TPA_Raised_Create_others.rad_sub_CR_DR_gl[1].checked==true)
    		items[2]=document.frm_TPA_Raised_Create_others.rad_sub_CR_DR_gl[1].value;   
         
     
     items[3]=document.getElementById("txtsub_Amount_gl").value;
    // items[4]=document.getElementById("txtParticular_gl").value;
     
	 var mycurrent_row_gl=document.createElement("TR");                
     mycurrent_row_gl.id=seq_gl;
     
     var cell_gl=document.createElement("TD");
     var anc_gl=document.createElement("A");
     var url="javascript:loadTable_gl('"+mycurrent_row_gl.id+"')";
     anc_gl.href=url;
     var txtedit_gl=document.createTextNode("EDIT_GL");
     anc_gl.appendChild(txtedit_gl);
     cell_gl.appendChild(anc_gl);
     mycurrent_row_gl.appendChild(cell_gl);
     
     cell2=document.createElement("TD");       
     var H_code_gl=document.createElement("input");
     H_code_gl.type="hidden";
     H_code_gl.name="gl_H_code";
     H_code_gl.value=items[0];
     cell2.appendChild(H_code_gl);
     var currentText=document.createTextNode(items[0]+"-"+items[1]);
     cell2.appendChild(currentText);
     mycurrent_row_gl.appendChild(cell2);
           
     cell2=document.createElement("TD"); 
     var CR_DR_type_gl=document.createElement("input");
     CR_DR_type_gl.type="hidden";
     CR_DR_type_gl.name="gl_CR_DR_type";
     CR_DR_type_gl.value=items[2];
     cell2.appendChild(CR_DR_type_gl);
     var currentText=document.createTextNode(items[2]);
     cell2.appendChild(currentText);
     mycurrent_row_gl.appendChild(cell2);
      
     cell2=document.createElement("TD"); 
     var gl_amt=document.createElement("input");
     gl_amt.type="hidden";
     gl_amt.name="gl_amt";
     gl_amt.value=items[3];
     cell2.appendChild(gl_amt);
     var currentText=document.createTextNode(items[3]);
     cell2.appendChild(currentText);
     mycurrent_row_gl.appendChild(cell2);
       
//     cell2=document.createElement("TD");                
//     var gl_particular=document.createElement("input");
//     gl_particular.type="hidden";
//     gl_particular.name="gl_particular";
//     gl_particular.value=items[4];
//     cell2.appendChild(gl_particular);
//     var currentText=document.createTextNode(items[4]);
//     cell2.appendChild(currentText);
//     mycurrent_row_gl.appendChild(cell2);
     
     tbody_gl.appendChild(mycurrent_row_gl);
     clearall_gl();
     document.getElementById("txtAcc_HeadCode_gl").disabled=false;
     /** Increment Sequence Number */ 
     seq_gl=seq_gl+1;
}
function loadTable_gl(scod_gl)
{
	//alert("");
	com_id_gl=scod_gl;                                    // to identify in UPDATE_GRID ,which row loaded 
    //clearall_sl();
    var r=document.getElementById(scod_gl);
    var rcells=r.cells;
    try {document.getElementById("txtAcc_HeadCode_gl").value=rcells.item(1).firstChild.value;}catch(e){}
    loadAccDesc();
    
    if(rcells.item(2).firstChild.value=="CR")
    		document.frm_TPA_Raised_Create_others.rad_sub_CR_DR_gl[0].checked=true;
    else if(rcells.item(2).firstChild.value=="DR")
    		document.frm_TPA_Raised_Create_others.rad_sub_CR_DR_gl[1].checked=true;
     
    try{document.getElementById("txtsub_Amount_gl").value=rcells.item(3).firstChild.value;}catch(e){}
  //  try{document.getElementById("txtParticular_gl").value=rcells.item(4).firstChild.value;}catch(e){}  
   
    
    document.frm_TPA_Raised_Create_others.cmdupdate_gl.style.display='block';        
    document.frm_TPA_Raised_Create_others.cmdadd_gl.style.display='none';  
    document.frm_TPA_Raised_Create_others.cmddelete_gl.disabled=false;
}

function loadTable_sl(scod)
{
	com_id=scod;                                    // to identify in UPDATE_GRID ,which row loaded 
    //clearall_sl();
    var r=document.getElementById(scod);
    var rcells=r.cells;
    if(rcells.item(1).firstChild.value==620101)
    {
    	document.frm_TPA_Raised_Create_others.txtAcc_HeadCode.disabled=true;
    	document.frm_TPA_Raised_Create_others.cmbSL_type.disabled=true;
    	document.frm_TPA_Raised_Create_others.cmbSL_Code.disabled=true;   	
    }
    else if(rcells.item(1).firstChild.value==900301)
    {
    	document.frm_TPA_Raised_Create_others.txtAcc_HeadCode.disabled=true;
    	document.frm_TPA_Raised_Create_others.cmbSL_type.disabled=true;
    	document.frm_TPA_Raised_Create_others.cmbSL_Code.disabled=true;
    }
    else
    {
    	document.frm_TPA_Raised_Create_others.txtAcc_HeadCode.disabled=false;
    	document.frm_TPA_Raised_Create_others.cmbSL_type.disabled=false;
    	document.frm_TPA_Raised_Create_others.cmbSL_Code.disabled=false;
    }  
    try {document.getElementById("txtAcc_HeadCode").value=rcells.item(1).firstChild.value;}catch(e){}
    doFunction('checkCode','null');   
    try{com_cmbSL_type=rcells.item(3).firstChild.value;} catch(e){com_cmbSL_type=""}
    try{com_cmbSL_Code=rcells.item(4).firstChild.value;} catch(e){com_cmbSL_Code=""}     
    if(com_cmbSL_type==5)
    {        
    	document.getElementById("benifici").style.display='none';
            document.getElementById("txtOfficeID_trs").value=com_cmbSL_Code;                
            job_flag=false;             
    }
    if(com_cmbSL_type==7)
    {
    	document.getElementById("benifici").style.display='none';
            document.getElementById("txtEmpID_trs").value=com_cmbSL_Code;
            emp_flag=false;            
    } 
    if(com_cmbSL_type==14)
    {
    	alert("14");
    	document.getElementById("benifici").style.display='block';
            document.getElementById("txtEmpID_trs").value=com_cmbSL_Code;
            emp_flag=false;            
    }
    if((document.getElementById("txtAcc_HeadCode").value==900108 || document.getElementById("txtAcc_HeadCode").value==901001) && com_cmbSL_type==5)   
    {
    	document.getElementById("benifici").style.display='none';
            loadSLType(com_cmbSL_Code,com_cmbSL_type);
    }
    else if(document.getElementById("txtAcc_HeadCode").value==900108)
    {     
    	document.getElementById("benifici").style.display='none';
	        var url="../../../../../TDA_TCA_Acceptance_Create?command=subCode&SLCode="+com_cmbSL_Code;
		 	var req=getTransport();
	   		req.open("GET",url,true); 
	   		req.onreadystatechange=function()
	   		{
	   		loadSlCode(req);
	   		}   
	   		req.send(null);
    
    }
    else
    {        		
    		doFunction('Load_SL_Code',com_cmbSL_type);
    }
        
   setTimeout('document.getElementById("cmbSL_type").value=com_cmbSL_type',900); 
    setTimeout('document.getElementById("cmbSL_Code").value=com_cmbSL_Code',900); 
    if(rcells.item(2).firstChild.value=="CR")
    		document.frm_TPA_Raised_Create_others.rad_sub_CR_DR_sl[0].checked=true;
    else if(rcells.item(2).firstChild.value=="DR")
    		document.frm_TPA_Raised_Create_others.rad_sub_CR_DR_sl[1].checked=true;
     
    try{document.getElementById("txtsub_Amount_sl").value=rcells.item(5).firstChild.value;}catch(e){}
    try{document.getElementById("txtParticular_sl").value=rcells.item(6).firstChild.value;}catch(e){}  
   
    if(rcells.item(1).firstChild.value==620101)
    {
    	document.frm_TPA_Raised_Create_others.rad_sub_CR_DR_sl[0].disabled=false;
    	document.frm_TPA_Raised_Create_others.rad_sub_CR_DR_sl[1].disabled=true;
    }
    else  if(rcells.item(1).firstChild.value==900301)
    {
    	
    	document.frm_TPA_Raised_Create_others.rad_sub_CR_DR_sl[1].disabled=false;
    	document.frm_TPA_Raised_Create_others.rad_sub_CR_DR_sl[0].disabled=true;	
    }
    
    
    
    document.frm_TPA_Raised_Create_others.cmdupdate_sl.style.display='block';        
    document.frm_TPA_Raised_Create_others.cmdadd_sl.style.display='none';       
}

function sl_update()
{
	tbody=document.getElementById("grid_body_sl");
	 var items=new Array();
    
    items[0]=document.getElementById("txtAcc_HeadCode").value;
    items[1]=document.getElementById("txtAcc_HeadDesc").value;   
   
    if(document.frm_TPA_Raised_Create_others.rad_sub_CR_DR_sl[0].checked==true)
   		items[2]=document.frm_TPA_Raised_Create_others.rad_sub_CR_DR_sl[0].value;
    else if(document.frm_TPA_Raised_Create_others.rad_sub_CR_DR_sl[1].checked==true)
   		items[2]=document.frm_TPA_Raised_Create_others.rad_sub_CR_DR_sl[1].value;   
    
    
    if(document.frm_TPA_Raised_Create_others.Org_CR_DR[0].checked==true)
 	{
	
		if(document.frm_TPA_Raised_Create_others.rad_sub_CR_DR_sl[1].checked==true)
		{
		 
		 if(document.getElementById("txtAcc_HeadCode").value==900301)
		{
			alert("900301 Account Head is not Allowed Here");
			document.getElementById("txtAcc_HeadCode").value="";
			document.getElementById("txtAcc_HeadDesc").value="";
			return false;
		}
		}
 	}
	if(document.frm_TPA_Raised_Create_others.Org_CR_DR[1].checked==true)
 	{
		if(document.frm_TPA_Raised_Create_others.rad_sub_CR_DR_sl[0].checked==true)
		{
		if(document.getElementById("txtAcc_HeadCode").value==620101)
		{
			alert("620101 Account Head is not Allowed Here");
			document.getElementById("txtAcc_HeadCode").value="";
			document.getElementById("txtAcc_HeadDesc").value="";
			return false;
		}
		}
 	}
    
        
    items[3]=document.getElementById("cmbSL_type").value;        
    if(document.getElementById("cmbSL_type").value=="") 
    {
    	alert("Enter SL Type");
    return false;
    }
    else
           items[4]=document.getElementById("cmbSL_type").options[document.getElementById("cmbSL_type").selectedIndex].text;         
           
    items[5]=document.getElementById("cmbSL_Code").value;                
    if(document.getElementById("cmbSL_Code").value=="")  
    {
    	alert("Enter SL Code");
           return false;
    }
    else
           items[6]=document.getElementById("cmbSL_Code").options[document.getElementById("cmbSL_Code").selectedIndex].text; 
   
    items[7]=document.getElementById("txtsub_Amount_sl").value;
    items[8]=document.getElementById("txtParticular_sl").value;
    
    var r=document.getElementById(com_id);
  //  alert("r:::"+r);
    var rcells=r.cells;

    try{rcells.item(1).firstChild.value=items[0];}catch(e){}
    try{rcells.item(1).lastChild.nodeValue=items[0]+"-"+items[1];}catch(e){}
 
    try{rcells.item(2).firstChild.value=items[2];}catch(e){}
    try{rcells.item(2).lastChild.nodeValue=items[2];}catch(e){}
  
    try{rcells.item(3).firstChild.value=items[3];}catch(e){}
    try{rcells.item(3).lastChild.nodeValue=items[4];}catch(e){}

    try{rcells.item(4).firstChild.value=items[5];}catch(e){}
    try{rcells.item(4).lastChild.nodeValue=items[6];}catch(e){}

    try{rcells.item(5).firstChild.value=items[7];}catch(e){}
    try{rcells.item(5).lastChild.nodeValue=items[7];}catch(e){}
 
    try{rcells.item(6).firstChild.value=items[8];}catch(e){}
    try{rcells.item(6).lastChild.nodeValue=items[8];}catch(e){}
    
    alert("Record Updated for SL");
    clearall_sl();
}

function gl_update()
{
//	alert("hhh");
	tbody=document.getElementById("grid_body_gl");
	 var items=new Array();
   
   items[0]=document.getElementById("txtAcc_HeadCode_gl").value;
   items[1]=document.getElementById("txtAcc_HeadDesc_gl").value;   
  
   if(document.frm_TPA_Raised_Create_others.rad_sub_CR_DR_gl[0].checked==true)
  		items[2]=document.frm_TPA_Raised_Create_others.rad_sub_CR_DR_gl[0].value;
   else if(document.frm_TPA_Raised_Create_others.rad_sub_CR_DR_gl[1].checked==true)
  		items[2]=document.frm_TPA_Raised_Create_others.rad_sub_CR_DR_gl[1].value;   
       
  
   items[3]=document.getElementById("txtsub_Amount_gl").value;
 //  items[4]=document.getElementById("txtParticular_gl").value;
   
   var r=document.getElementById(com_id_gl);
  // alert("r:::"+r);
   var rcells=r.cells;

   try{rcells.item(1).firstChild.value=items[0];}catch(e){}
   try{rcells.item(1).lastChild.nodeValue=items[0]+"-"+items[1];}catch(e){}

   try{rcells.item(2).firstChild.value=items[2];}catch(e){}
   try{rcells.item(2).lastChild.nodeValue=items[2];}catch(e){}
 
   try{rcells.item(3).firstChild.value=items[3];}catch(e){}
   try{rcells.item(3).lastChild.nodeValue=items[3];}catch(e){}

  // try{rcells.item(4).firstChild.value=items[4];}catch(e){}
   //try{rcells.item(4).lastChild.nodeValue=items[4];}catch(e){}

   
   
   alert("Record Updated in GL");
   clearall_gl();
}







function sl_add()
{
	//alert(document.getElementById("cmbSL_Code").value);
	document.getElementById("txtAcc_HeadCode").disabled=false;
	if(document.getElementById("txtsub_Amount_sl").value=="")
	{
		alert("Enter SLAmount");
		return false;
	}
	else if(document.getElementById("TransferedID").value=="")
	{
		alert("Enter Transfered AccountingUnit");
		return false;
	}
	else if(document.getElementById("cmbSL_type").value=="")
	 {
		 alert("Enter SL Type");
		 return false;
	 }
	
	
	
	
	if(document.getElementById("txtAcc_HeadCode").value!=620101){
		if(document.getElementById("txtAcc_HeadCode").value!=900301)
		{
				if(document.getElementById("cmbSL_Code").value=="")
				 {
					 alert("Enter SL code");
					 return false;
				 }
		}
	}
	if(document.frm_TPA_Raised_Create_others.Org_CR_DR[0].checked==true)
 	{
	
		if(document.frm_TPA_Raised_Create_others.rad_sub_CR_DR_sl[1].checked==true)
		{
		 
		 if(document.getElementById("txtAcc_HeadCode").value==900301)
		{
			alert("900301 Account Head is not Allowed Here");
			document.getElementById("txtAcc_HeadCode").value="";
			document.getElementById("txtAcc_HeadDesc").value="";
			return false;
		}
		}
 	}
	if(document.frm_TPA_Raised_Create_others.Org_CR_DR[1].checked==true)
 	{
		if(document.frm_TPA_Raised_Create_others.rad_sub_CR_DR_sl[0].checked==true)
		{
		if(document.getElementById("txtAcc_HeadCode").value==620101)
		{
			alert("620101 Account Head is not Allowed Here");
			document.getElementById("txtAcc_HeadCode").value="";
			document.getElementById("txtAcc_HeadDesc").value="";
			return false;
		}
		}
 	}
	
//	else if(document.getElementById("cmbSL_Code").value=="")
//	 {
//		 alert("Enter SubLedger Code");
//		 return false;
//	 }
	 tbody=document.getElementById("grid_body_sl");
	 var row_test=tbody.getElementsByTagName("tr");
	// alert(row_test.length);
	 if(row_test.length==0)
	 {
		 if(document.frm_TPA_Raised_Create_others.Org_CR_DR[0].checked==true)
		 {
			 if(document.getElementById("txtAcc_HeadCode").value!=620101)
			 {
				 alert("Enter TPA Credit AccountHead");
				 return false;
			 }
		 }
		 else if(document.frm_TPA_Raised_Create_others.Org_CR_DR[1].checked==true)
		 {
			 if(document.getElementById("txtAcc_HeadCode").value!=900301)
			 {
				 alert("Enter TPA Debit AccountHead ");
				 return false;
			 }
		 }
		 
		 
		 
	 }
	 var items=new Array();
     
     items[0]=document.getElementById("txtAcc_HeadCode").value;
     items[1]=document.getElementById("txtAcc_HeadDesc").value;   
    
     if(document.frm_TPA_Raised_Create_others.rad_sub_CR_DR_sl[0].checked==true)
    		items[2]=document.frm_TPA_Raised_Create_others.rad_sub_CR_DR_sl[0].value;
     else if(document.frm_TPA_Raised_Create_others.rad_sub_CR_DR_sl[1].checked==true)
    		items[2]=document.frm_TPA_Raised_Create_others.rad_sub_CR_DR_sl[1].value;   
         
     items[3]=document.getElementById("cmbSL_type").value;        
     items[4]=document.getElementById("cmbSL_type").options[document.getElementById("cmbSL_type").selectedIndex].text;         
            
     if(document.getElementById("txtAcc_HeadCode").value==620101)
     {
    	 items[5]=document.getElementById("TransferedID").value; 
     }
     else if(document.getElementById("txtAcc_HeadCode").value==900301)
     {
    	 items[5]=document.getElementById("TransferedID").value; 
     }
     else
     {
     items[5]=document.getElementById("cmbSL_Code").value;                
     }  
    
     items[6]=document.getElementById("cmbSL_Code").options[document.getElementById("cmbSL_Code").selectedIndex].text;
     items[7]=document.getElementById("txtsub_Amount_sl").value;
     items[8]=document.getElementById("txtParticular_sl").value;
     
	 var mycurrent_row=document.createElement("TR");                
     mycurrent_row.id=seq;
     
     var cell=document.createElement("TD");
     var anc=document.createElement("A");
     var url="javascript:loadTable_sl('"+mycurrent_row.id+"')";
     anc.href=url;
     var txtedit=document.createTextNode("EDIT");
     anc.appendChild(txtedit);
     cell.appendChild(anc);
     mycurrent_row.appendChild(cell);
     
     cell2=document.createElement("TD");       
     var H_code=document.createElement("input");
     H_code.type="hidden";
     H_code.name="H_code";
     H_code.value=items[0];
     cell2.appendChild(H_code);
     var currentText=document.createTextNode(items[0]+"-"+items[1]);
     cell2.appendChild(currentText);
     mycurrent_row.appendChild(cell2);
           
     cell2=document.createElement("TD"); 
     var CR_DR_type=document.createElement("input");
     CR_DR_type.type="hidden";
     CR_DR_type.name="CR_DR_type";
     CR_DR_type.value=items[2];
     cell2.appendChild(CR_DR_type);
     var currentText=document.createTextNode(items[2]);
     cell2.appendChild(currentText);
     mycurrent_row.appendChild(cell2);
      
     cell2=document.createElement("TD");
     var SL_type=document.createElement("input");
     SL_type.type="hidden";
     SL_type.name="SL_type";
     SL_type.value=items[3];
     cell2.appendChild(SL_type);
     var currentText=document.createTextNode(items[4]);
     cell2.appendChild(currentText);
     mycurrent_row.appendChild(cell2);
     
     cell2=document.createElement("TD");
     var SL_code=document.createElement("input");
     SL_code.type="hidden";
     SL_code.name="SL_code";
     SL_code.value=items[5];
     cell2.appendChild(SL_code);
     var paid_to=document.createElement("input");
     paid_to.type="hidden";
     paid_to.name="Paid_To";
     paid_to.value=items[6];
     cell2.appendChild(paid_to);
     var currentText=document.createTextNode(items[6]);
     cell2.appendChild(currentText);
     mycurrent_row.appendChild(cell2);
     
     cell2=document.createElement("TD"); 
     var sl_amt=document.createElement("input");
     sl_amt.type="hidden";
     sl_amt.name="sl_amt";
     sl_amt.value=items[7];
     cell2.appendChild(sl_amt);
     var currentText=document.createTextNode(items[7]);
     cell2.appendChild(currentText);
     mycurrent_row.appendChild(cell2);
       
     cell2=document.createElement("TD");                
     var particular=document.createElement("input");
     particular.type="hidden";
     particular.name="sl_particular";
     particular.value=items[8];
     cell2.appendChild(particular);
     var currentText=document.createTextNode(items[8]);
     cell2.appendChild(currentText);
     mycurrent_row.appendChild(cell2);
     
     tbody.appendChild(mycurrent_row);
     clearall_sl();
   //  yes
     if(document.frm_TPA_Raised_Create_others.Org_CR_DR[0].checked==true)
 	{
    	document.frm_TPA_Raised_Create_others.rad_sub_CR_DR_sl[0].checked=false;
 	    document.frm_TPA_Raised_Create_others.rad_sub_CR_DR_sl[0].disabled=true;
 		document.frm_TPA_Raised_Create_others.rad_sub_CR_DR_sl[1].checked=true;
 		document.frm_TPA_Raised_Create_others.rad_sub_CR_DR_sl[1].disabled=false;	 
 	}
     else
     {
    	 document.frm_TPA_Raised_Create_others.rad_sub_CR_DR_sl[0].checked=true;
  	    document.frm_TPA_Raised_Create_others.rad_sub_CR_DR_sl[0].disabled=false;
  		document.frm_TPA_Raised_Create_others.rad_sub_CR_DR_sl[1].checked=false;
  		document.frm_TPA_Raised_Create_others.rad_sub_CR_DR_sl[1].disabled=true;	
     }
     /** Increment Sequence Number */ 
     seq=seq+1;
}


function delete_GRID_gl()
{
	
	 if(confirm("Do you want to delete ?"))
     {
     		   
             var tbody_gl=document.getElementById("grid_body_gl");
             var r=document.getElementById(com_id_gl);
             var ri=r.rowIndex;

             alert("ri:::::"+ri);
             tbody_gl.deleteRow(ri);
             clearall_gl();
     	        
     }
		
} 

function delete_GRID()
{
	
	 if(confirm("Do you want to delete ?"))
     {
     		   
             var tbody=document.getElementById("grid_body_sl");
             var r=document.getElementById(com_id);
             var ri=r.rowIndex;
             tbody.deleteRow(ri);
             clearall_sl();
     	        
     }
		
}

function clearall_gl()
{
	
	 document.getElementById("txtAcc_HeadCode_gl").value="";
     document.getElementById("txtAcc_HeadDesc_gl").value="";   
          
     document.getElementById("txtsub_Amount_gl").value="";
    // document.getElementById("txtParticular_gl").value="";
     document.frm_TPA_Raised_Create_others.cmdadd_gl.disabled=false;
     document.frm_TPA_Raised_Create_others.cmddelete_gl.disabled=true;
     document.frm_TPA_Raised_Create_others.cmdupdate_gl.style.display='none';        
     document.frm_TPA_Raised_Create_others.cmdadd_gl.style.display='block';       
}
function clearall_sl()
{
	 document.getElementById("txtAcc_HeadCode").value="";
     document.getElementById("txtAcc_HeadDesc").value="";   
          
     document.getElementById("cmbSL_type").value="";   
     document.getElementById("cmbSL_Code").value="";                
    
     document.getElementById("txtsub_Amount_sl").value="";
     document.getElementById("txtParticular_sl").value="";
	
     document.frm_TPA_Raised_Create_others.cmdadd_sl.disabled=false;
     document.frm_TPA_Raised_Create_others.cmddelete_sl.disabled=true;
     document.frm_TPA_Raised_Create_others.cmdupdate_sl.style.display='none';        
     document.frm_TPA_Raised_Create_others.cmdadd_sl.style.display='block'; 
     
     document.getElementById("txtAcc_HeadCode").disabled=false;
     document.getElementById("cmbSL_type").disabled=false;
     document.getElementById("cmbSL_Code").disabled=false;
     if(document.frm_TPA_Raised_Create_others.Org_CR_DR[0].checked==true)
     {
    	 document.frm_TPA_Raised_Create_others.rad_sub_CR_DR_sl[0].checked=false;
         document.frm_TPA_Raised_Create_others.rad_sub_CR_DR_sl[0].disabled=true;
    	 document.frm_TPA_Raised_Create_others.rad_sub_CR_DR_sl[1].checked=true;
    	 document.frm_TPA_Raised_Create_others.rad_sub_CR_DR_sl[1].disabled=false;	 
    	
     }
     else
     {
    	 document.frm_TPA_Raised_Create_others.rad_sub_CR_DR_sl[1].checked=false;
         document.frm_TPA_Raised_Create_others.rad_sub_CR_DR_sl[1].disabled=true;
    	 document.frm_TPA_Raised_Create_others.rad_sub_CR_DR_sl[0].checked=true;
    	 document.frm_TPA_Raised_Create_others.rad_sub_CR_DR_sl[0].disabled=false;	 
     }
     
     
}
function loadcheckCode_gl(baseResponse)
{
    var flag=baseResponse.getElementsByTagName("flag")[0].firstChild.nodeValue;
   
    
    if(flag=="success")
    {
         var hid=baseResponse.getElementsByTagName("hid")[0].firstChild.nodeValue;
      //   document.getElementById("txtAcc_HeadCode_gl").value=hid;
         var hdesc=baseResponse.getElementsByTagName("hdesc")[0].firstChild.nodeValue;
       
         document.getElementById("txtAcc_HeadCode_gl").value=hid;
         document.getElementById("txtAcc_HeadDesc_gl").value=hdesc;
      
      
    }
     else if(flag=="failure")
     {
         alert("Account Head Code '"+document.getElementById("txtAcc_HeadCode_gl").value+"' doesn't Exist");
         document.getElementById("txtAcc_HeadCode_gl").value="";
         document.getElementById("txtAcc_HeadCode_gl").focus();
     }
}

function clr()
{
	
	
	
	
	if(document.frm_TPA_Raised_Create_others.Org_CR_DR[0].checked==true)
	{
		document.frm_TPA_Raised_Create_others.txtAcc_HeadCode.value=620101;
	}
	else
		{
		document.frm_TPA_Raised_Create_others.txtAcc_HeadCode.value=900301;
		}


}

function loadsltype()
{
	if(document.frm_TPA_Raised_Create_others.Org_CR_DR[0].checked==true)
	{
		 var cmbSL_type=document.getElementById("cmbSL_type");
         cmbSL_type.length=0;
	   var option=document.createElement("OPTION");
		option.value="15";
		option.text="Accounting Units";
		try
        {
            cmbSL_type.add(option);
        }catch(errorObject)
        {
            cmbSL_type.add(option,null);
        }
        var generalUnits=document.getElementById("TransferedID").options[document.getElementById("TransferedID").selectedIndex].text;
       document.getElementById("cmbSL_Code").options[document.getElementById("cmbSL_Code").selectedIndex].text=generalUnits;
       
	}
	else
	{
		 var cmbSL_type=document.getElementById("cmbSL_type");
         cmbSL_type.length=0;
	   var option=document.createElement("OPTION");
		option.value="15";
		option.text="Accounting Units";
		try
        {
            cmbSL_type.add(option);
        }catch(errorObject)
        {
            cmbSL_type.add(option,null);
        }
        var generalUnits=document.getElementById("TransferedID").options[document.getElementById("TransferedID").selectedIndex].text;
       // alert(generalUnits);
      //  document.getElementById("cmbSL_Code").value=document.getElementById("TransferedID").value;
        document.getElementById("cmbSL_Code").options[document.getElementById("cmbSL_Code").selectedIndex].text=generalUnits;
	}
	var tbody=document.getElementById("grid_body_sl");
    var t=0;
    for(t=tbody.rows.length-1;t>=0;t--)
    {
       tbody.deleteRow(0);
    }
    var tbod=document.getElementById("grid_body_gl");
    var t=0;
    for(t=tbod.rows.length-1;t>=0;t--)
    {
       tbod.deleteRow(0);
    }
	

}

function fillAmount()
{
	//alert("blursss");
	if(document.frm_TPA_Raised_Create_others.Org_CR_DR[0].checked==true)
	{
		if(document.getElementById("txtAcc_HeadCode").value==620101){
		var txtsub_Amount_sl=document.getElementById("txtsub_Amount_sl").value;
		document.getElementById("Amount").value=txtsub_Amount_sl;	
		}
	}
	else
	{
		if(document.getElementById("txtAcc_HeadCode").value==900301){
			var txtsub_Amount_sl=document.getElementById("txtsub_Amount_sl").value;
			document.getElementById("Amount").value=txtsub_Amount_sl;	
			}	
	}
	
}

function loadGL()
{
	if(document.frm_TPA_Raised_Create_others.Org_CR_DR[0].checked==true)
	{
		document.frm_TPA_Raised_Create_others.txtAcc_HeadCode.value=620101;
		document.frm_TPA_Raised_Create_others.txtAcc_HeadDesc.value="TRANSFER PROFORMA CREDIT   -TRANSFER CREDIT";
		document.getElementById("txtAcc_HeadCode").disabled=true;
		document.frm_TPA_Raised_Create_others.rad_sub_CR_DR_sl[0].checked=true;
	    document.frm_TPA_Raised_Create_others.rad_sub_CR_DR_sl[0].disabled=false;
		document.frm_TPA_Raised_Create_others.rad_sub_CR_DR_sl[1].checked=false;
		document.frm_TPA_Raised_Create_others.rad_sub_CR_DR_sl[1].disabled=true;
		
		//GL
		document.frm_TPA_Raised_Create_others.rad_sub_CR_DR_gl[1].checked=true;
	    document.frm_TPA_Raised_Create_others.rad_sub_CR_DR_gl[1].disabled=false;
		document.frm_TPA_Raised_Create_others.rad_sub_CR_DR_gl[0].checked=false;
		document.frm_TPA_Raised_Create_others.rad_sub_CR_DR_gl[0].disabled=true;
		
	}
	else
	{
		document.frm_TPA_Raised_Create_others.txtAcc_HeadCode.value=900301;
		 document.getElementById("txtAcc_HeadCode").disabled=true;
		document.frm_TPA_Raised_Create_others.txtAcc_HeadDesc.value="TRANSFER PROFORMA DEBIT A/C   -T.P. DEBIT A/C";
		document.frm_TPA_Raised_Create_others.rad_sub_CR_DR_sl[0].checked=false;
		document.frm_TPA_Raised_Create_others.rad_sub_CR_DR_sl[0].disabled=true;
		document.frm_TPA_Raised_Create_others.rad_sub_CR_DR_sl[1].checked=true;
		document.frm_TPA_Raised_Create_others.rad_sub_CR_DR_sl[1].disabled=false;
		
		//GL
		document.frm_TPA_Raised_Create_others.rad_sub_CR_DR_gl[1].checked=false;
		document.frm_TPA_Raised_Create_others.rad_sub_CR_DR_gl[1].disabled=true;
		document.frm_TPA_Raised_Create_others.rad_sub_CR_DR_gl[0].checked=true;
		document.frm_TPA_Raised_Create_others.rad_sub_CR_DR_gl[0].disabled=false;
	}
	var tbody=document.getElementById("grid_body_sl");
    var t=0;
    for(t=tbody.rows.length-1;t>=0;t--)
    {
       tbody.deleteRow(0);
    }
    var tbod=document.getElementById("grid_body_gl");
    var t=0;
    for(t=tbod.rows.length-1;t>=0;t--)
    {
       tbod.deleteRow(0);
    }
}

function call_date(dateCtrl)                        // TB_checking 
{         
         if(checkdt(dateCtrl))
         {
                var cmbAcc_UnitCode=document.getElementById("cmbAcc_UnitCode").value;
                var cmbOffice_code=document.getElementById("cmbOffice_code").value;
                var TB_date=dateCtrl.value;
                if(dateCtrl.value.length!=0)
                {
                        var url="../../../../../Receipt_SL.view?Command=check_TB&TB_date="+TB_date+"&cmbAcc_UnitCode="+cmbAcc_UnitCode+"&cmbOffice_code="+cmbOffice_code;
                        var req=getTransport();
                        req.open("GET",url,true); 
                        req.onreadystatechange=function()
                        {
                                  check_TB(req,dateCtrl);
                        }   
                        req.send(null);
                }
        }
    
}



function check_TB(req,dateCtrl)
{
         if(req.readyState==4)
         {
                if(req.status==200)
                {  
                        var baseResponse=req.responseXML.getElementsByTagName("response")[0];
                        var tagcommand=baseResponse.getElementsByTagName("command")[0];
                        var Command=tagcommand.firstChild.nodeValue;
                        var flag=baseResponse.getElementsByTagName("flag")[0].firstChild.nodeValue;    
                        if(flag=="success")
                        {
                           //doFunction('load_Receipt_No','null');                 //return true;
                      	
                      	
                        }
                        else if(flag=="failure")
                        {
                                  dateCtrl.value="";
                                  alert("Trial Balance Closed");//return false;//
                                  dateCtrl.focus();                                            
                        }
                        else if(flag=="finyear")
                        {
                                  // This statement get executed when financial year ( Cash Book Control Details ) has not found for the given date 
                                  dateCtrl.value="";
                                  alert("Cash Book Control Not Found ");//return false;//
                                  dateCtrl.focus();
                                  //document.getElementById("txtVoucher_No").value="";     
                        }
                        dateCheck(dateCtrl); 
                }
         }
}


function dateCheck(datechk)
{
	//alert("WELCOME!.........");
	
	var cmbAcc_UnitCode=document.getElementById("cmbAcc_UnitCode").value;
    var cmbOffice_code=document.getElementById("cmbOffice_code").value;
    //var txtCrea_date=document.getElementById("txtCrea_date").value;
    var txtCrea_date=datechk.value;
    
    if(datechk.value.length!=0)
    {
    var url="../../../../../Receipt_SL.view?Command=check_Date&cmbAcc_UnitCode="+cmbAcc_UnitCode+"&cmbOffice_code="+cmbOffice_code+"&txtCrea_date="+txtCrea_date;
    //alert("URL===>"+url);
    var req=getTransport();
    req.open("GET",url,true); 
    req.onreadystatechange=function()
    {
      check_Date(req,datechk);
    } ;  
    req.send(null);
    }

}
function check_Date(req,datechk)
{
 if(req.readyState==4)
    {
        if(req.status==200)
        {  
            var baseResponse=req.responseXML.getElementsByTagName("response")[0];
            var tagcommand=baseResponse.getElementsByTagName("command")[0];
            var Command=tagcommand.firstChild.nodeValue;
            var flag=baseResponse.getElementsByTagName("flag")[0].firstChild.nodeValue;
            if(flag=="success")
            {
               //doFunction('load_Receipt_No','null');                 //return true;
          	document.getElementById("butSub").disabled=false;
            }
          else if(flag=="failure")
          {
          	datechk.value=""; 
          	alert("Document Date is Less than DATE_EFFECTIVE_FROM");
          	datechk.focus();
          	document.getElementById("butSub").disabled=true;
          	
//          	document.getElementById("txtReceipt_No").value="";
               
          }
          else if(flag=="success1")
          {
             //doFunction('load_Receipt_No','null');                 //return true;
          	document.getElementById("butSub").disabled=false;
          }
         else if(flag=="failure1")
         {
      	  alert("Document Date is Greater than DATE_OF_CLOSURE");
      	  datechk.value=""; 
        		//alert("Document Date is Less than DATE_ALLOWED_UPTO date");
        		datechk.focus();
        		document.getElementById("butSub").disabled=true;
//        		document.getElementById("txtReceipt_No").value="";
         }
         else 
      	   {
      	    datechk.value=""; 
      	    alert("Date Value is Null");
         		datechk.focus();
         		document.getElementById("butSub").disabled=true;
//         		document.getElementById("txtReceipt_No").value="";
      	   }
        }
    }
}
function numbersonly(e,t)
{
         var unicode=e.charCode? e.charCode : e.keyCode;
         if(unicode==13)
         {
                try{t.blur(); }catch(e){}
                return true;
        
         }
         if (unicode!=8 && unicode !=9  )
         {
                if (unicode<48||unicode>57 ) 
                {
                     return false 
                }
         }
}  

function check_withinCB()
{
	var mon ="";
		var jrnl_date = document.getElementById("Voucher_Date").value;
		var CB_Year = document.frm_TPA_Raised_Create_others.txtCB_Year.value;
		var CB_month = document.frm_TPA_Raised_Create_others.txtCB_Month.value;
		var jrdate = jrnl_date.split('/');
		var entered_month = jrdate[1];
		var enteedr_year = jrdate[2];
		if(CB_month<10)
			{
		 mon = "0"+CB_month;
			}
		else{
			mon=CB_month;
		}
		//alert(entered_month +" ==  "+ mon+" == "+enteedr_year +" ==" + CB_Year)
		if((entered_month == mon) && (enteedr_year == CB_Year))
			{
			
			}
		else
			{
			
			alert("select the Date within the Cash Book Year and Month");
			document.getElementById("Voucher_Date").value="";
			}
}
