var com_id;
var com_cmbSL_Code="";
var com_cmbSL_type="";
var seq=0;
var job_flag;
var common_AHead_code_flag="";
var emp_flag;
var item1=new Array();var item2=new Array();var item3=new Array();var item4=new Array();
var item5=new Array();var item6=new Array();var item7=new Array();var item8=new Array();
var item9=new Array();
var item10=new Array();var item11=new Array();var item12=new Array();
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


function clearJournal()
{
		 document.TDA_TCA.Journal_type[0].checked=false;
		 document.TDA_TCA.Journal_type[1].checked=false; 
		 document.getElementById("accepted_slno").length=1;
}


function doFunction_TDA(command)
{   
		 
	     clrVoucherDetails();
		 var UnitCode=document.getElementById("cmbAcc_UnitCode").value;	
	     var office_code=document.getElementById("cmbOffice_code").value 
	     var Journal_type="";
	     if(document.TDA_TCA.Journal_type[0].checked==true)
		        Journal_type="TDAA";
		 else
			    Journal_type="TCAA";	  
	     var txtCrea_date= document.getElementById("txtCrea_date").value;          
         if(command=="load_Voucher_No") 
		 {
        	 	document.getElementById("linkId").style.visibility="hidden";
     			document.getElementById("accepted_slno").length=1;
        	    url="../../../../../TDA_TCA_Acceptance_Edit?command=loadVoucher&Option=Cancel&cmbAcc_UnitCode="+UnitCode+"&cmbOffice_code="+office_code+"&txtCrea_date="+txtCrea_date+"&Journal_type="+Journal_type;           			  
		 }
		 else if(command=="load_Voucher_Details") 
		 {
			    var accepted_slno=document.getElementById("accepted_slno").value;
			    url="../../../../../TDA_TCA_Acceptance_Edit?command=loadVoucherDetails&cmbAcc_UnitCode="+UnitCode+"&cmbOffice_code="+office_code+"&txtCrea_date="+txtCrea_date+"&Journal_type="+Journal_type+"&accepted_slno="+accepted_slno;           			  
		 }
         req=getTransport();
         req.open("GET",url,true);        
         req.onreadystatechange=function()
         {        	  
                Acceptance_ServletResponse(req);
         }   
         req.send(null);     
}


function changeLink()
{
		 if(document.getElementById("accepted_slno").value!="")
				document.getElementById("linkId").style.visibility="visible";
		 else
				document.getElementById("linkId").style.visibility="hidden";
}


function Acceptance_ServletResponse(req,slcode)
{
		 if(req.readyState==4)
		 {
                if(req.status==200)
                {  
                         var baseResponse=req.responseXML.getElementsByTagName("response")[0];
                         var tagcommand=baseResponse.getElementsByTagName("command")[0];
                         var Command=tagcommand.firstChild.nodeValue;                                  
                         var flag=baseResponse.getElementsByTagName("flag")[0].firstChild.nodeValue;
                         if(Command=="loadVoucher")
                         {                                       
                               if(flag=="success")
                               {                                       
                                       var accepted_slno=document.getElementById("accepted_slno");
                              	       var child=accepted_slno.childNodes;
                              	       for(var i=child.length-1;i>1;i--)
                              	       {
                              	    	   		accepted_slno.removeChild(child[i]);
                              	       }
                                       var count=baseResponse.getElementsByTagName("voucher_no");
                                       for(var k=0;k<count.length;k++)
                                       {
                                                var voucher_no=baseResponse.getElementsByTagName("voucher_no")[k].firstChild.nodeValue;				       	                                                  
                                                var option=document.createElement("OPTION");
                                                option.text=voucher_no;
                                                option.value=voucher_no;
                                                try
                                                {
                                                		accepted_slno.add(option);
                                                }
                                                catch(errorObject)
                                                {
                                                		accepted_slno.add(option,null);
                                                }
                                       }
                               }
                               else
                               {                                                   
                                       document.getElementById("accepted_slno").value="";
                                       alert("No Voucher Found");
                               }
                         }
                         else if(Command=="loadVoucherDetails")
                         {	                    	   
	                    	   if(flag=="success")
	                           {                                      
	                                   var count=baseResponse.getElementsByTagName("voucher_date");
	                                   var voucher_no=baseResponse.getElementsByTagName("voucher_no")[0].firstChild.nodeValue;
                           	   		   var voucher_date=baseResponse.getElementsByTagName("voucher_date")[0].firstChild.nodeValue;
                           	   		   var voucher_total_amount=baseResponse.getElementsByTagName("voucher_total_amount")[0].firstChild.nodeValue;                           	   		  
                           	   		   var reason_for_transfer=baseResponse.getElementsByTagName("reason_for_transfer")[0].firstChild.nodeValue;
                           	   		   var unit_id=baseResponse.getElementsByTagName("unit_id")[0].firstChild.nodeValue;
                           	   		   var unit_name=baseResponse.getElementsByTagName("unit_name")[0].firstChild.nodeValue;
                           	   		   var acc_head=baseResponse.getElementsByTagName("acc_head")[0].firstChild.nodeValue;
                        	   		   
                        	   		   if(baseResponse.getElementsByTagName("particulars")[0].firstChild == null){
											var particulars = ""  
										  }else {
											   var particulars=baseResponse.getElementsByTagName("particulars")[0].firstChild.nodeValue;
										  }
                        	   		   
                        	   		   var acceptance=baseResponse.getElementsByTagName("acceptance")[0].firstChild.nodeValue;
                        	   		   var reason=baseResponse.getElementsByTagName("reason")[0].firstChild.nodeValue;
                        	   		   document.getElementById("originated_slno").value=voucher_no;
                        	   		   document.getElementById("originated_date").value=voucher_date;
                        	   		   document.getElementById("txtUnitName").value=unit_name;
                        	   		   document.getElementById("txtUnitId").value=unit_id;
                        	   		   document.getElementById("txtDebitHead").value=acc_head;
                        	   		   document.getElementById("txtTotalAmt").value=voucher_total_amount;
                        	   		   document.getElementById("txtReason").value=reason_for_transfer;
                        	   		   document.getElementById("txtRemarks").value=particulars;   		
                        	   		                        	   			   		
	            	   			   	   for(var i=0;i<count.length;i++)
	            	   			   	   {
	                                	   		item1[i]=baseResponse.getElementsByTagName("trn_acc_head")[i].firstChild.nodeValue;
		               	   		   		   	    item2[i]=baseResponse.getElementsByTagName("cr_dr_indicator")[i].firstChild.nodeValue;
															 if (baseResponse.getElementsByTagName("trn_sub_type_code")[i].firstChild == null) {
																 item3[i] = ""
															 } else {
																 item3[i] = baseResponse.getElementsByTagName("trn_sub_type_code")[i].firstChild.nodeValue;
	
															 }
		               	   		   		   	    
		               	   		   		   	    
														 if (baseResponse.getElementsByTagName("trn_sub_type_desc")[i].firstChild == null) {
															 item4[i] = ""
														 } else {
															 item4[i] = baseResponse.getElementsByTagName("trn_sub_type_desc")[i].firstChild.nodeValue;

														 }
		               	   		   		   	 
		               	   		   		   	 
		               	   		   		   	    item5[i]=baseResponse.getElementsByTagName("trn_sub_code")[i].firstChild.nodeValue;
		               	   		   		   	    item6[i]=baseResponse.getElementsByTagName("trn_sub_desc")[i].firstChild.nodeValue;
		               	   		   		   	    item7[i]=baseResponse.getElementsByTagName("amount")[i].firstChild.nodeValue;
														 if (baseResponse.getElementsByTagName("trn_particulars")[i].firstChild == null) {
															 item8[i] = "";
														 }
														 else {
															 item8[i] = baseResponse.getElementsByTagName("trn_particulars")[i].firstChild.nodeValue;
														 }
		               	   		   		   	  //  item8[i]=baseResponse.getElementsByTagName("trn_particulars")[i].firstChild.nodeValue;
		               	   		   		   	    item9[i]=baseResponse.getElementsByTagName("head_desc")[i].firstChild.nodeValue;
				               	   		   		item10[i]=baseResponse.getElementsByTagName("trn_bookNo")[i].firstChild.nodeValue;
		            	   		   		   	    item11[i]=baseResponse.getElementsByTagName("trn_bookPageno")[i].firstChild.nodeValue;
		            	   		   		   	    item12[i]=baseResponse.getElementsByTagName("trn_bookDate")[i].firstChild.nodeValue;
	            	   			   		}
	            	   			   		loadGrid();
	            	   		   
	                           }
	                    	  
                         }
                        
              }
                
		 }    
}

function loadGrid()
{
		 tbody=document.getElementById("grid_body");
		 for(var i=0;i<item1.length;i++)
		 {
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
	          H_code.value=item1[i];
	          cell2.appendChild(H_code);
	          var currentText=document.createTextNode(item1[i]+"-"+item9[i]);
	          cell2.appendChild(currentText);
	          mycurrent_row.appendChild(cell2);
	               
	          cell2=document.createElement("TD"); 
	          var CR_DR_type=document.createElement("input");
	          CR_DR_type.type="hidden";
	          CR_DR_type.name="CR_DR_type";
	          CR_DR_type.value=item2[i];
	          cell2.appendChild(CR_DR_type);
	          var currentText=document.createTextNode(item2[i]);
	          cell2.appendChild(currentText);
	          mycurrent_row.appendChild(cell2);
	          
	          cell2=document.createElement("TD");
	          var SL_type=document.createElement("input");
	          SL_type.type="hidden";
	          SL_type.name="SL_type";
	          SL_type.value=item3[i];
	          cell2.appendChild(SL_type);
	          var currentText=document.createTextNode(item4[i]);
	          cell2.appendChild(currentText);
	          mycurrent_row.appendChild(cell2);
	         
	          cell2=document.createElement("TD");
	          var SL_code=document.createElement("input");
	          SL_code.type="hidden";
	          SL_code.name="SL_code";
	          SL_code.value=item5[i];
	          cell2.appendChild(SL_code);	
	          var paid_to=document.createElement("input");
              paid_to.type="hidden";
              paid_to.name="Paid_To";
              paid_to.value=item6[i];
              cell2.appendChild(paid_to);
	          var currentText=document.createTextNode(item6[i]);
	          cell2.appendChild(currentText);
	          mycurrent_row.appendChild(cell2);
	         
	          cell2=document.createElement("TD"); 
	          var sl_amt=document.createElement("input");
	          sl_amt.type="hidden";
	          sl_amt.name="sl_amt";
	          sl_amt.value=item7[i];
	          cell2.appendChild(sl_amt);
	          var currentText=document.createTextNode(item7[i]);
	          cell2.appendChild(currentText);
	          mycurrent_row.appendChild(cell2);
	           
	          cell2=document.createElement("TD");                
	          var particular=document.createElement("input");
	          particular.type="hidden";
	          particular.name="sl_particular";
	          particular.value=item8[i];
	          cell2.appendChild(particular);
	          var currentText=document.createTextNode(item8[i]);
	          cell2.appendChild(currentText);
	          mycurrent_row.appendChild(cell2);
	
	          cell2=document.createElement("TD");                
	          var bkNo1=document.createElement("input");
	          bkNo1.type="hidden";
	          bkNo1.name="m_bkNo";
	          bkNo1.value=item10[i];
	          cell2.appendChild(bkNo1);
	          var currentText=document.createTextNode(item10[i]);
	          cell2.appendChild(currentText);
	          mycurrent_row.appendChild(cell2);
	          
	          cell2=document.createElement("TD");                
	          var bkPageno=document.createElement("input");
	          bkPageno.type="hidden";
	          bkPageno.name="m_bkPageno";
	          bkPageno.value=item11[i];
	          cell2.appendChild(bkPageno);
	          var currentText=document.createTextNode(item11[i]);
	          cell2.appendChild(currentText);
	          mycurrent_row.appendChild(cell2);
	          
	          cell2=document.createElement("TD");                
	          var bookDated=document.createElement("input");
	          bookDated.type="hidden";
	          bookDated.name="m_bookDate";
	          bookDated.value=item12[i];
	          cell2.appendChild(bookDated);
	          var currentText=document.createTextNode(item12[i]);
	          cell2.appendChild(currentText);
	          mycurrent_row.appendChild(cell2);
	          
	          tbody.appendChild(mycurrent_row);
	          //clearall();
	          /** Increment Sequence Number */ 
	          seq=seq+1;
		 }
}


/////////////////////////////////////////////   load Table() /////////////////////////////////////////////////////
function loadTable(scod)
{	
}


///////////////////////////////////////////    TB_checking and Calender control return value handling
function call_mainJSP_script(fromcal_dateCtrl,blr_flag)     /////////Calender control return value handling
{
         if(blr_flag==1)         // which is used to find the receipt or voucher or payment (creation) date calling field,if so check trial balance
         {
        	 	document.TDA_TCA.Journal_type[0].checked=false;
	    		document.TDA_TCA.Journal_type[1].checked=false; 
	    		document.getElementById("accepted_slno").length=1;
                         
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
                }
         }
}


function clrVoucherDetails()
{
	    document.getElementById("originated_slno").value="";
		document.getElementById("originated_date").value="";
   	    document.getElementById("txtUnitId").value="";
   	    document.getElementById("txtUnitName").value="";   	 
   	    document.getElementById("txtDebitHead").value="";
   	    document.getElementById("txtTotalAmt").value="";
   	    document.getElementById("txtReason").value="";
}
function clrForm(param)
{		
		document.TDA_TCA.Journal_type[0].checked=false;
		document.TDA_TCA.Journal_type[1].checked=false; 
		document.getElementById("linkId").style.visibility="hidden";
		document.getElementById("accepted_slno").length=1;
		document.getElementById("txtRemarks").value="";
		clrVoucherDetails();
		document.getElementById("txtRemarks").value="";   
		var tbody=document.getElementById("grid_body");
		var t=0;
		for(t=tbody.rows.length-1;t>=0;t--)
		{
            tbody.deleteRow(0);
		}
}

function checkNull()
{               
        var tbody=document.getElementById("grid_body");
        if(document.getElementById("cmbAcc_UnitCode").value=="")
        {
	            alert("Select the Account Unit code");
	            return false;    
        }
        if(document.getElementById("cmbOffice_code").value=="")
        {
	            alert("Select the Office Code");           
	            return false;
        }   
        if(document.getElementById("txtCrea_date").value.length==0)
        {
	            alert("Enter the Date of Creation");           
	            return false;    
        }  
        if(document.getElementById("accepted_slno").value=="")
        {
            	alert("Select Accepting Sl.No.");
            	return false;
        }
		if(tbody.rows.length==0)
		{
				alert("Enter Details Part");
				return false;
		}
		else
        {
				return true;
	             
        }       
        
       		
}

function checkVoucherNo()
{
		var unitcode=document.getElementById("cmbAcc_UnitCode").value;	
	    var offid=document.getElementById("cmbOffice_code").value 	
	    var txtCrea_date=document.getElementById("txtCrea_date").value;  
    	dt=txtCrea_date.split("/");
 	    var mon=dt[1];   
 	    var yr=dt[2];	
	    var recNo= document.getElementById("accepted_slno").value;   
	 	var Voucher_list_SL= window.open("../../../../../org/FAS/FAS1/TDA/jsps/TDA_TCA_ListAll_SL.jsp?cmbAcc_UnitCode="+unitcode+"&cmbOffice_code="+offid+"&cashbook_yr="+yr+"&cashbook_mn="+mon+"&voucher_no="+recNo,"VoucherList","status=1,height=500,width=500,resizable=YES, scrollbars=yes"); 
	    Voucher_list_SL.moveTo(250,250);  
	    Voucher_list_SL.focus();    
}
