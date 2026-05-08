var com_id;
var com_cmbSL_Code="";
var com_cmbSL_type="";
var seq=0;
var job_flag;
var common_AHead_code_flag="";
var emp_flag;

/** Browser Identification */

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

function checkVoucherDate()
{
	 var book_date=document.forms[0].book_date.value;
	 var txtCrea_date=document.forms[0].txtCrea_date.value;
	 
	 if(book_date>txtCrea_date)
	 {
		 alert("M-Book Date should be less than or equal to VoucherDate");
		 document.forms[0].book_date.value="";
		 document.forms[0].book_date.focus();
	 }
	
}

function sixdigit()
{
 if( document.getElementById("txtAcc_HeadCode").value!=0)
    {
        if(( document.getElementById("txtAcc_HeadCode").value).length<6)
        {
        alert("Account Head Code Shouldn't be less than 6 digit number");
        document.getElementById("txtAcc_HeadCode").value="";
        document.getElementById("txtAcc_HeadCode").focus();
        return false;
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

function uncheckRadio()
{
	var ac_head=document.getElementById("txtAcc_HeadCode").value;
	if(document.TDA_TCA.Journal_type[0].checked==true)
	{
		if(ac_head==900108)
		{		
			if(document.TDA_TCA.rad_sub_CR_DR[0].checked==true)
			{
				
				document.TDA_TCA.rad_sub_CR_DR[1].checked=true;
				document.TDA_TCA.rad_sub_CR_DR[0].checked=false;
			}
	    }
	}
	else if(document.TDA_TCA.Journal_type[1].checked==true)
	{
		if(ac_head==901001)
		{		
			if(document.TDA_TCA.rad_sub_CR_DR[1].checked==true)
			{
				document.TDA_TCA.rad_sub_CR_DR[0].checked=true;
				document.TDA_TCA.rad_sub_CR_DR[1].checked=false;
			}
		}
	}
	
}

function clear_Combo(combo)
{       	    
         //alert("inside clear combo");
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


function filter_real(evt,item,n,pre)
{
         var charCode = (evt.which) ? evt.which : event.keyCode
         // allow "." for one time 
         if(charCode==46)
         {                
                if(item.value.indexOf(".")<0)	return (item.value.length>0)?true:false;
                else return false;
         }
         if (!(charCode > 31 && (charCode < 48 || charCode > 57)))
         {
                        // to avoid over flow
                if(item.value.indexOf(".")<0)
                {
                        //alert("Length without . ="+item.value.length); 
                        return (item.value.length<n)?true:false;
                }
                // dont allow more than 2 precision no's after the point
                if(item.value.indexOf(".")>0)
                {
                        //alert("precision count ="+item.value.split(".")[1].length);
                        if(item.value.split(".")[1].length<pre) return true;
                        else return false;
                }
                return false;
         }else
         {
                return false;
         }
}


function valid_amt(field)
{
    
         amt=field.value;
         if(amt.indexOf(".")!=amt.lastIndexOf("."))
         {
                alert("Enter a Valid Amount");
                field.value="";
                field.focus();
         }
         if(amt < 0 ) 
         {
                alert("Negative Amount Not Allowed");
                field.value="";
                field.focus();    
         }
         if(parseInt(document.getElementById("txtsub_Amount").value) > parseInt(document.getElementById("txtTotalAmt").value))
         {
                alert("Enter Correct Amount");
                document.getElementById("txtsub_Amount").value="";
                document.getElementById("txtsub_Amount").focus();
                return false
         }
         else
                return true;
    
    
    
}

function loadTransferUnit()
{       
		 var cmbAcc_UnitCode=document.getElementById("cmbAcc_UnitCode").value;		 
         url="../../../../../TDA_Raised_Create?command=loadTransferUnit&txtUnitId="+cmbAcc_UnitCode;
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
                        if(Command=="loadTransferUnit")
                        {                                       
                             
                               if(flag=="success")
                               {                                      
                                       var txtUnitId=document.getElementById("txtUnitId");  
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


/////////////////////////////////////////////   load Table() /////////////////////////////////////////////////////
function loadTable(scod)
{  
		com_id=scod;                                    // to identify in UPDATE_GRID ,which row loaded 
        //clearall();
        var r=document.getElementById(scod);
        var rcells=r.cells;
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
        		document.TDA_TCA.rad_sub_CR_DR[0].checked=true;
        else if(rcells.item(2).firstChild.value=="DR")
        		document.TDA_TCA.rad_sub_CR_DR[1].checked=true;
         
        try{document.getElementById("txtsub_Amount").value=rcells.item(5).firstChild.value;}catch(e){}
        try{document.getElementById("txtParticular").value=rcells.item(6).firstChild.value;}catch(e){}  
        try{document.getElementById("bookNo").value=rcells.item(7).firstChild.value;}catch(e){}
        try{document.getElementById("bookPageNo").value=rcells.item(8).firstChild.value;}catch(e){}  
        try{document.getElementById("book_date").value=rcells.item(9).firstChild.value;}catch(e){}
        if(scod==0)
        {
        		document.getElementById("offlist_div_trans").style.display='none';
        		document.getElementById("emplist_div_trans").style.display='none';   
	        	document.getElementById("txtAcc_HeadCode").disabled=true;
	    		document.getElementById("cmbSL_type").disabled=true;
	    		document.getElementById("cmbSL_Code").disabled=true;
	    		document.getElementById("txtsub_Amount").disabled=true;
	    		document.getElementById("cmbMas_SL_type").disabled=false;
	            document.getElementById("cmbMas_SL_Code").disabled=false;
	            document.getElementById("txtTotalAmt").disabled=false;
	            document.TDA_TCA.cmddelete.disabled=true;
	            document.TDA_TCA.cmdclear.disabled=true;
        }
        else
        {
        		document.TDA_TCA.cmddelete.disabled=false;
        		document.TDA_TCA.cmdclear.disabled=false;
        		document.getElementById("txtAcc_HeadCode").disabled=false;
	    		document.getElementById("cmbSL_type").disabled=false;
	    		document.getElementById("cmbSL_Code").disabled=false;
	    		document.getElementById("txtsub_Amount").disabled=false;
	    		document.getElementById("cmbMas_SL_type").disabled=true;
	            document.getElementById("cmbMas_SL_Code").disabled=true;
	            document.getElementById("txtTotalAmt").disabled=true;
        		
        }
        document.TDA_TCA.cmdupdate.style.display='block';        
        document.TDA_TCA.cmdadd.style.display='none';       
        //alert("AF "+com_cmbSL_type);
 	//    setTimeout('document.getElementById("cmbSL_type").value=com_cmbSL_type',900); 
}

function loadSlCode(req)
{
	if(req.readyState==4)
	 {
           if(req.status==200)
           {  
                    var baseResponse=req.responseXML.getElementsByTagName("response")[0];
                    var tagcommand=baseResponse.getElementsByTagName("command")[0];
                    var Command=tagcommand.firstChild.nodeValue;                                  
                    if(Command=="subCode")
                    {
                    	 document.forms[0].cmbSL_Code.length=0;
                    	 var office_id=baseResponse.getElementsByTagName("office_id")[0].firstChild.nodeValue;
                    	 var tttt=document.getElementById("cmbSL_Code");
                  		for(var k=0;k<office_id.length;k++)
                        {
        		   			
        		   			    var office_id=baseResponse.getElementsByTagName("office_id")[k].firstChild.nodeValue;				       	                                                  
                                 var office_name=baseResponse.getElementsByTagName("office_name")[k].firstChild.nodeValue;
                                 var option=document.createElement("OPTION");
                                 option.text=office_name;
                                 option.value=office_id;
                                 try
                                 {
                                	 tttt.add(option);
                                 }
                                 catch(errorObject)
                                 {
                                	 tttt.add(option,null);
                                 }
                        }
                    }
           }
	 }
}

///////////////////////////////////////////    TB_checking and Calender control return value handling
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
                        if(flag=="failure")
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



/////////////////////////////////////////////   ADD & UPDATE & DELETE /////////////////////////////////////////////////////
function load_grid(cmd)
{
         if(document.getElementById("txtAcc_HeadCode").value.length==0)
         {
                alert("Enter A/c Head Code");
                return false;
         }                
         var acc=document.getElementById("txtAcc_HeadCode").value;
         alert("Acc---->"+acc);
         var kk=acc.charAt(0)+acc.charAt(1);
         if(kk=="82")
         {
                if(acc !="820102"  && acc !="820103")
                {		          
                         alert("This A/C code can not be used here ");
                         document.getElementById("txtAcc_HeadCode").value="";
                         document.getElementById("txtAcc_HeadDesc").value="";
                         return false;
                }  
         }  
         if(acc == "620101"  || acc =="900301" || acc =="610101" || acc =="610102" || acc =="900201" || acc =="900202")
         {		          
                  alert("This A/C code can not be used here ");
                  document.getElementById("txtAcc_HeadCode").value="";
                  document.getElementById("txtAcc_HeadDesc").value="";
                  return false;
         }  
         
         
         if(document.getElementById("cmbSL_type").length>1 && document.getElementById("cmbSL_type").value=="")
         {
 		     
 				                alert("Select a Sub-Ledger Type");
 				                return false;
 			            
 		                 
         }
         if(document.getElementById("cmbSL_type").value!="")
         {
 	          	if(document.getElementById("cmbSL_Code").value=="")
 	            {
 			             alert("Select The Sub Ledger Code");
 			             return false;
 	            }
         }       
         if(document.getElementById("txtsub_Amount").value.length==0)
         {
                alert("Enter the Amount ");
                return false;    
         }
       
         var tbody=document.getElementById("grid_body");                            
         var t=0;            
         var items=new Array();
        
         items[0]=document.getElementById("txtAcc_HeadCode").value;
         items[1]=document.getElementById("txtAcc_HeadDesc").value;   
        
         if(document.TDA_TCA.rad_sub_CR_DR[0].checked==true)
        		items[2]=document.TDA_TCA.rad_sub_CR_DR[0].value;
         else if(document.TDA_TCA.rad_sub_CR_DR[1].checked==true)
        		items[2]=document.TDA_TCA.rad_sub_CR_DR[1].value;   
                
         items[3]=document.getElementById("cmbSL_type").value;        
         if(document.getElementById("cmbSL_type").value=="")      
                items[4]="";        
         else
                items[4]=document.getElementById("cmbSL_type").options[document.getElementById("cmbSL_type").selectedIndex].text;         
                
         items[5]=document.getElementById("cmbSL_Code").value;                
         if(document.getElementById("cmbSL_Code").value=="")        
                items[6]="";
         else
                items[6]=document.getElementById("cmbSL_Code").options[document.getElementById("cmbSL_Code").selectedIndex].text; 
               
         items[7]=document.getElementById("txtsub_Amount").value;
         items[8]=document.getElementById("txtParticular").value;
         items[9]=document.getElementById("bookNo").value;
         items[10]=document.getElementById("bookPageNo").value;
         items[11]=document.getElementById("book_date").value;
         
       
         tbody=document.getElementById("grid_body");
         if(cmd=="ADD_GRID")
         {
        	 var browserName=navigator.appName;
            // alert("browserName:::"+browserName);
                var mycurrent_row=document.createElement("TR");                
                mycurrent_row.id=seq;
                
                var cell=document.createElement("TD");
                var anc=document.createElement("A");
                var url="javascript:loadTable('"+mycurrent_row.id+"')";
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
                
                cell2=document.createElement("TD");                
                var bookno=document.createElement("input");
                bookno.type="hidden";
                bookno.name="m_bookno";
                bookno.value=items[9];
                cell2.appendChild(bookno);
                var currentText=document.createTextNode(items[9]);
                cell2.appendChild(currentText);
                mycurrent_row.appendChild(cell2);
                
                cell2=document.createElement("TD");                
                var bookpageno=document.createElement("input");
                bookpageno.type="hidden";
                bookpageno.name="m_bookpageno";
                bookpageno.value=items[10];
                cell2.appendChild(bookpageno);
                var currentText=document.createTextNode(items[10]);
                cell2.appendChild(currentText);
                mycurrent_row.appendChild(cell2);
                
                cell2=document.createElement("TD");                
                var bookdate=document.createElement("input");
                bookdate.type="hidden";
                bookdate.name="m_bookdate";
                bookdate.value=items[11];
                cell2.appendChild(bookdate);
                var currentText=document.createTextNode(items[11]);
                cell2.appendChild(currentText);
                mycurrent_row.appendChild(cell2);

                tbody.appendChild(mycurrent_row);
                clearall();
                /** Increment Sequence Number */ 
                seq=seq+1;
         }
         else
         {
                           
                var r=document.getElementById(com_id);
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
                
                try{rcells.item(7).firstChild.value=items[9];}catch(e){}
                try{rcells.item(7).lastChild.nodeValue=items[9];}catch(e){}
                
                try{rcells.item(8).firstChild.value=items[10];}catch(e){}
                try{rcells.item(8).lastChild.nodeValue=items[10];}catch(e){}
                
                try{rcells.item(9).firstChild.value=items[11];}catch(e){}
                try{rcells.item(9).lastChild.nodeValue=items[11];}catch(e){}
                
                alert("Record Updated");
                clearall();
         }
}

/////////////////////////////////////////////   clearall() by User /////////////////////////////////////////////////////

function clearall()
{
		document.getElementById("txtAcc_HeadCode").disabled=false;
		document.getElementById("cmbSL_type").disabled=false;
		document.getElementById("cmbSL_Code").disabled=false;
		document.getElementById("txtsub_Amount").disabled=false;
		document.getElementById("cmbMas_SL_type").disabled=true;
        document.getElementById("cmbMas_SL_Code").disabled=true;
        document.getElementById("txtTotalAmt").disabled=true;
        document.getElementById("txtAcc_HeadCode").value="";
        document.getElementById("txtAcc_HeadDesc").value="";
        
        document.getElementById("bookNo").value="";        
        document.getElementById("bookPageNo").value="";  
        document.getElementById("book_date").value="";  
		
	 	document.getElementById("offlist_div_trans").style.display='none';
	    document.getElementById("emplist_div_trans").style.display='none';   
        document.getElementById("txtsub_Amount").value="";
        document.getElementById("txtParticular").value="";     
        document.getElementById("txtOfficeID_trs").value="";
        document.getElementById("txtEmpID_trs").value="";     
        var cmbSL_type1=document.getElementById("cmbSL_type"); 
        clear_Combo(cmbSL_type1);   
        var cmbSL_Code1=document.getElementById("cmbSL_Code"); 
        clear_Combo(cmbSL_Code1);   
		document.TDA_TCA.cmdadd.style.display='block';
		document.TDA_TCA.cmdupdate.style.display='none';
		document.TDA_TCA.cmddelete.disabled=true;	 
		document.TDA_TCA.cmdclear.disabled=false;	
}


function clrForm(param)
{
//alert("clrForm");
		document.TDA_TCA.Journal_type[0].checked=true;
		document.TDA_TCA.rad_sub_CR_DR[1].checked=true;
                 document.getElementById("butSub").disabled=false;
		document.getElementById("cmbReason").value="";        
        document.getElementById("txtUnitId").value="";  
      
        document.getElementById("bookNo").value="";        
        document.getElementById("bookPageNo").value="";  
        document.getElementById("book_date").value="";        
        
		document.getElementById("txtDebitHead").value="900108";
		document.getElementById("txtAcc_HeadCode").value="900108";		
		if(param=="cancel")
		{
			    if(window.confirm("Do you want to clear ALL fields ?"))
				{
			                call_clr();
				}
		}
		else
				call_clr();
}

function call_clr()
{
	//	document.getElementById("cmbMas_SL_type").value="";
	    document.getElementById("cmbMas_SL_Code").value="";
	    document.getElementById("txtTotalAmt").value="";
	    document.getElementById("txtRemarks").value="";
	    document.getElementById("bookNo").value="";        
        document.getElementById("bookPageNo").value="";  
        document.getElementById("book_date").value="";   
		document.getElementById("cmbMas_SL_type").disabled=false;
	    document.getElementById("cmbMas_SL_Code").disabled=false;
	    document.getElementById("txtTotalAmt").disabled=false;
		document.getElementById("txtDebitHead").disabled=true;
		document.getElementById("txtAcc_HeadCode").disabled=true;
		document.getElementById("cmbSL_type").disabled=true;
		document.getElementById("cmbSL_Code").disabled=true;
		document.getElementById("txtsub_Amount").disabled=true;
		document.TDA_TCA.cmdclear.disabled=true;
        var tbody=document.getElementById("grid_body");
        var t=0;
        for(t=tbody.rows.length-1;t>=0;t--)
        {
                tbody.deleteRow(0);
        }
}

function delete_GRID()
{
        if(confirm("Do you want to delete ?"))
        {
        		   
                var tbody=document.getElementById("mytable");
                var r=document.getElementById(com_id);
                var ri=r.rowIndex;
                tbody.deleteRow(ri);
                clearall();
        	        
        }
}


function checkAccountHead()
{
		if(document.TDA_TCA.Journal_type[0].checked==true)
		{
				document.getElementById("txtDebitHead").value="900108";
				document.getElementById("txtAcc_HeadCode").value="900108";
				document.TDA_TCA.rad_sub_CR_DR[1].checked=true;
		}
		else
		{
				document.getElementById("txtDebitHead").value="901001";
				document.getElementById("txtAcc_HeadCode").value="901001";
				document.TDA_TCA.rad_sub_CR_DR[0].checked=true;
		}
		doFunction('checkCode','null');
		call_clr();
				
}


function loadSLType(SLCode,SLType)
{	
	
		var txtUnitId=document.getElementById("txtUnitId").value;
		var ac_head_code=document.getElementById("txtDebitHead").value;
		var cmbReason=document.getElementById("cmbReason").value;
		if((ac_head_code==900108 || ac_head_code==901001 ) && SLType==15)
		{
			
				if(txtUnitId=="")
				{
						alert("select Transfer Unit");
						document.getElementById("cmbMas_SL_type").value="";
						document.getElementById("cmbSL_type").value="";
						return false;
				}
				if(cmbReason=="")
					{
					alert("Choose Reason for TDA/TCA");
					document.getElementById("txtUnitId").value="";
					return false;
					}
                   if(document.TDA_TCA.Journal_type[0].checked==true)
                	   {
                	   if(cmbReason=="6")
                	   {
				                	   alert("comes");
				                	   
				                	   
//				                	   
//				                	   var url="../../../../../TDA_Raised_Create?command=testAsset&Option=Create&txtUnitId="+txtUnitId;   		
//				       		   		var req=getTransport();
//				       		   		req.open("GET",url,true); 
//				       		   		req.onreadystatechange=function()
//				       		   		{
//				       		   				loadProcess_Response(req,SLCode);
//				       		   		}   
//				       		   		req.send(null);
                	   }
                	   }
				document.getElementById("offlist_div_trans").style.display='none';
			    document.getElementById("emplist_div_trans").style.display='none';
			    document.getElementById("offlist_div_master").style.display='none';
			    document.getElementById("emplist_div_master").style.display='none';
		   		var url="../../../../../TDA_Raised_Create?command=loadSLType&Option=Create&txtUnitId="+txtUnitId;   		
		   		var req=getTransport();
		   		req.open("GET",url,true); 
		   		req.onreadystatechange=function()
		   		{
		   				loadProcess_Response(req,SLCode);
		   		}   
		   		req.send(null);
		}
		else
				doFunction('Load_SL_Code',SLType);
		disabledFunction();disabledFunction();
		if(document.getElementById("cmbSL_Code").disabled)
		{
			document.getElementById("cmbSL_Code").disabled = false;   /*@NK on 20jul20*/
		}
		
}
function fordcb(val)
{
	  document.getElementById("butSub").disabled=false;
	if(document.getElementById("cmbSL_type").value==14)
	{
		document.getElementById("benifici").style.display='block';
	
	}else{
		document.getElementById("benifici").style.display='none';
		loadSLType('null',val);
	}
	disabledFunction();
	
}


function disabledFunction()
{
	var flag=document.getElementById("cmbSL_Code").disabled;
	if(flag==true)
	{
		document.getElementById("cmbSL_Code").disabled = false;   /*@NK on 20jul20*/
	}
}


function call(command,param)
{
	if(command=="get")
	{
		var cmbOffice_code=document.getElementById("cmbOffice_code").value;
		var bentypeid=document.getElementById("dcb_ben_type").value;
                // alert('bentypeid::::'+bentypeid);
	    var url="../../../../../Journal_General_Create.view?Command=get&bentypeid="+bentypeid+"&cmbOffice_code="+cmbOffice_code;
	           // alert(url);
	    var req=getTransport();
	    req.open("GET",url,true); 
	    req.onreadystatechange=function()
	    {
	      check_benifi(req);
	    }  ; 
	    req.send(null);
		
	}
	else if(command=="benifi"){
	var cmbOffice_code=document.getElementById("cmbOffice_code").value;
	
    var url="../../../../../Journal_General_Create.view?Command=benifi&benficierysno="+param+"&cmbOffice_code="+cmbOffice_code;
           // alert(url);
    var req=getTransport();
    req.open("GET",url,true); 
    req.onreadystatechange=function()
    {
      check_benifi(req);
    }  ; 
    req.send(null);
	}
}


function check_benifi(req)
{
 if(req.readyState==4)
    {
        if(req.status==200)
        {  
        	 response=req.responseXML.getElementsByTagName("response")[0];
             command=response.getElementsByTagName("command")[0].firstChild.nodeValue;
             flag=response.getElementsByTagName("flag")[0].firstChild.nodeValue;

            if(command=="get")
            {
            	            	
                if(flag=='success')
                {
                	try{
                		
                		  var len=response.getElementsByTagName("beneficiarysno").length;
                		  
                		 var cmb_SL_Code=document.getElementById("cmbSL_Code");
                		 
                		
                         
                         var items_id=new Array();
                         var items_name=new Array();
                       
                            for(var i=0;i<len;i++)
                            {
                          	 
                          	items_id[i]=response.getElementsByTagName("beneficiarysno")[i].firstChild.nodeValue;
                           
                          	items_name[i]=response.getElementsByTagName("beneficiaryname")[i].firstChild.nodeValue;
                            
                            }
                            
                                                       
                            clear_Combo(cmb_SL_Code);
                            // alert('here second');
                            for(var k=0;k<len;k++)
                            {   
                            	//alert(items_name[k]);
                                  var option=document.createElement("OPTION");
                                  option.text=items_name[k];
                                  option.value=items_id[k];
                                   try
                                  {
                                	   cmb_SL_Code.add(option);
                                	  
                                  }
                                  catch(errorObject)
                                  {
                                	  cmb_SL_Code.add(option,null);
                                	 
                                     // alert('error');
                                  }
                            }
                		
                		
                		
                		
                	}catch(e){alert("Error in lat"+e);}
                	
                	if( benfiflag==1)
                	{
                		benfiflag=0;            	
                	document.getElementById("cmbSL_Code").value=bensub;
                	
                	}
                	
                }else{
                	alert('Subledger Code Not Found*****');
                }
            }
                if(command=="benifi")
                {
                	            	
                    if(flag=='success')
                    {
                    	try{
                    		
                    		document.getElementById("dcb_ben_type").value=response.getElementsByTagName("bentypeid")[0].firstChild.nodeValue;
                    		  
                    		call('get','null');	
                    	}catch(e){alert(e);}
                    }
                }
             
        }
    }
}

function loadProcess_Response(req,com_cmbSL_Code)
{
	    if(req.readyState==4)
	    {
		        if(req.status==200)
		        { 		        		
			            var baseResponse=req.responseXML.getElementsByTagName("response")[0];
			            var tagcommand=baseResponse.getElementsByTagName("command")[0];
			            var Command=tagcommand.firstChild.nodeValue;
			            var flag=baseResponse.getElementsByTagName("flag")[0].firstChild.nodeValue;	    			            
			            if(Command=="loadSLType")
				        { 
			    	    	if(flag=="success")
				   	 		{
                                                        
			    	    			
			    	    			 var cmbMas_SLCode=document.getElementById("cmbMas_SL_Code");
                                                         cmbMas_SLCode.length=0;
                                                         
                                                         
                                                        
                                                        var cmbSL_type=document.getElementById("cmbSL_type");
                                                        cmbSL_type.length=0;
                                                       // cmbSL_type.innerHTML=""; 
                                                        var option=document.createElement("OPTION");
                                                        option.text="Accounting Units";
                                                        option.value="15";
                                                        try
                                                        {
                                                            cmbSL_type.add(option);
                                                        }catch(errorObject)
                                                        {
                                                            cmbSL_type.add(option,null);
                                                        }
                                                        
			    	    			 var cmbSL_Code=document.getElementById("cmbSL_Code");
                                                         cmbSL_Code.length=0;
                                                         
			    	    			 
			    	    			 
					        	     var child=cmbSL_Code.childNodes;
					        	     for(var z=child.length-1;z>1;z--)
					        	     {							        	    	 
					        	    	 cmbSL_Code.removeChild(child[z]);
					        	     } 
					        	     
					        	     var child=cmbMas_SLCode.childNodes;
					        	     for(var j=child.length-1;j>1;j--)
					        	     {	
					        	    	 cmbMas_SLCode.removeChild(child[j]);
					        	     } 
					        	   
					        	     var count=baseResponse.getElementsByTagName("office_id");  
					                 var sl_code="";var sl_desc="";			                
					                 for(var i=0;i<count.length;i++)
					                 {
					                	 sl_code=baseResponse.getElementsByTagName("office_id")[i].firstChild.nodeValue;
					                	 sl_desc=baseResponse.getElementsByTagName("office_name")[i].firstChild.nodeValue;
					                     var opt=document.createElement("option");
					                     var opt1=document.createElement("option");
					                     opt.setAttribute("value",sl_code);					                     
					                     opt1.setAttribute("value",sl_code);
					                     var opttext=document.createTextNode(sl_desc);
					                     var opttext1=document.createTextNode(sl_desc);
					                     opt.appendChild(opttext);
					                     opt1.appendChild(opttext1);
					                     cmbSL_Code.appendChild(opt);
					                     cmbMas_SLCode.appendChild(opt1);
					                     
					                 }
					                 if(com_cmbSL_Code!='null')
					                 {					                	 
					                	 document.getElementById("cmbSL_Code").value=com_cmbSL_Code;
					                	 document.getElementById("cmbMas_SL_Code").value=com_cmbSL_Code;
					                 }
				   	 		}
				   	 		else
				   	 		{
				   	 				 alert("No Sub Ledger Type Found");
				   	 		}
				        }               
		           
		        }
		           
		}
}

function check_leng(remarks)
{	 
	    if((remarks.length)>=190)
	    {
	    		alert("Please Enter below 200 characters");
	    }	 
}

function moveSubType(sub_type)
{
                
		document.getElementById("cmbSL_type").value=sub_type;
                //document.getElementById("cmbSL_type").disabled=true;
}

function moveSubTypeCode(sub_type_code)
{
		document.getElementById("cmbSL_Code").value=sub_type_code;
		//document.getElementById("cmbSL_Code").disabled=true;
}
function moveAmount(amount)
{
		document.getElementById("txtsub_Amount").value=amount;
		//document.getElementById("txtsub_Amount").disabled=true;
}

function checkAccHead()
{
		if(document.TDA_TCA.Journal_type[0].checked==true)
		{
				if(document.getElementById("txtAcc_HeadCode").value==900108)
				{
						alert("This Account Head already have an entry ");
						document.getElementById("txtAcc_HeadCode").value="";
                                                document.getElementById("txtAcc_HeadDesc").value="";
						document.getElementById("txtAcc_HeadCode").focus();
				}
                                else if(document.getElementById("txtAcc_HeadCode").value==900109)
				{
						alert("This Account Head Code is not used Here.");
						document.getElementById("txtAcc_HeadCode").value="";
                                                document.getElementById("txtAcc_HeadDesc").value="";
						document.getElementById("txtAcc_HeadCode").focus();
				}
                                else
                                    doFunction('checkCode','null');
		}
		else
		{			
				if(document.getElementById("txtAcc_HeadCode").value==901001)
				{
						alert("This Account Head already have an entry ");
						document.getElementById("txtAcc_HeadCode").value="";
                                                document.getElementById("txtAcc_HeadDesc").value="";
						document.getElementById("txtAcc_HeadCode").focus();
				}
                                else if(document.getElementById("txtAcc_HeadCode").value==901002)
				{
						alert("This Account Head Code is not used Here.");
						document.getElementById("txtAcc_HeadCode").value="";
                                                document.getElementById("txtAcc_HeadDesc").value="";
						document.getElementById("txtAcc_HeadCode").focus();
				}
                                else
                                    doFunction('checkCode','null');
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
          	
          	document.getElementById("txtReceipt_No").value="";
               
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
        		document.getElementById("txtReceipt_No").value="";
         }
         else 
      	   {
      	    datechk.value=""; 
      	    alert("Date Value is Null");
         		datechk.focus();
         		document.getElementById("butSub").disabled=true;
         		document.getElementById("txtReceipt_No").value="";
      	   }
        }
    }
}


