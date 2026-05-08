var com_id;
var com_cmbSL_Code="";
var com_cmbSL_type="";
var seq=0;
var item1=new Array();var item2=new Array();var item3=new Array();var item4=new Array();
var item5=new Array();var item6=new Array();var item7=new Array();var item8=new Array();
var item9=new Array();var item10=new Array();var item11=new Array();var item12=new Array();
var item13=new Array();var item14=new Array();var item15=new Array();var item16=new Array();
var item17=new Array();

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

function loadAccUnit()
{
	  var trfunit=document.getElementById("txtUnitId").value;
	  //alert(trfunit);
	  url="../../../../../Post_TCA_Create?command=loadAccUnit&trfunit="+trfunit;
      req=getTransport();
      req.open("GET",url,true);        
      req.onreadystatechange=function()
      {        	  
             loadingUnit(req);
      }   
      req.send(null); 
}

function loadingUnit(req)
{
	if(req.readyState==4)
	 {
           if(req.status==200)
           {  
                   var baseResponse=req.responseXML.getElementsByTagName("response")[0];
                   var tagcommand=baseResponse.getElementsByTagName("command")[0];
                   var Command=tagcommand.firstChild.nodeValue;                                  
                   var flag=baseResponse.getElementsByTagName("flag")[0].firstChild.nodeValue;
                  
                   if(Command=="loadAccUnit")
                   {                                       
                          if(flag=="success")
                          {       
                        	  var txtUnitId=document.getElementById("cmbMas_SL_Code");  
                        	  txtUnitId.length=0;                                       
                              var items_id=new Array();
                              var items_name=new Array();                                    
                              var unitid=baseResponse.getElementsByTagName("unitid");
                              var unitname=baseResponse.getElementsByTagName("unitname");
                              for(var k=0;k<unitid.length;k++)
                              {
                                       items_id[k]=baseResponse.getElementsByTagName("unitid")[k].firstChild.nodeValue;
                                       items_name[k]=baseResponse.getElementsByTagName("unitname")[k].firstChild.nodeValue;				       	                                                  
                                       var option=document.createElement("OPTION");
                                       option.text=items_name[k];
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
                          else if(flag=="NoData")
                          {       
                        	  alert("No data found");
                          }
                          else if(flag=="failure")
                          {       
                        	  alert("failure in loading subLedger Code");
                          }
                   }
           }
	 }
}

function calClear()
{
     var tbody=document.getElementById("grid_body");
var t=0;
for(t=tbody.rows.length-1;t>=0;t--)

        tbody.deleteRow(0);
}

function loadTransferUnit()
{         
		 var cmbAcc_UnitCode=document.getElementById("cmbAcc_UnitCode").value;		 
         url="../../../../../Post_TDA_Create?command=loadTransferUnit&txtUnitId="+cmbAcc_UnitCode;
         req=getTransport();
         req.open("GET",url,true);        
         req.onreadystatechange=function()
         {        	  
                TDA_Raised_ServletResponse(req);
         }   
         req.send(null);     
}

function doFunction_voucher(param)
{
		 var cmbAcc_UnitCode=document.getElementById("cmbAcc_UnitCode").value;	
		 var cmbOffice_code=document.getElementById("cmbOffice_code").value;	
		 var txtCrea_date=document.getElementById("txtPayment_date").value;	
		 if(param=="load_voucher_no")
			 	url="../../../../../Post_TCA_Create?command=load_voucher_no&txtUnitId="+cmbAcc_UnitCode+"&cmbOffice_code="+cmbOffice_code+"&txtCrea_date="+txtCrea_date;
		 else
		 {	
			 	var voucher_no=document.getElementById("txtVoucher_No").value;
			 	url="../../../../../Post_TCA_Create?command=load_Voucher_Details&txtUnitId="+cmbAcc_UnitCode+"&cmbOffice_code="+cmbOffice_code+"&txtCrea_date="+txtCrea_date+"&voucher_no="+voucher_no;
		 }	 	
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
                                                var option=document.createElement("OPTION");
                                                option.text=items_name[k];
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
                       else if(Command=="load_voucher_no")
                       {
                    	   	   if(flag=="success")
                               {
                    	   		       document.getElementById("txtVoucher_No").length=1;  
                    	   		   	   var txtVoucher_No=document.getElementById("txtVoucher_No");  
	                    	   		   var vno=baseResponse.getElementsByTagName("VOUCHER_NO");
	                    	   		   var voucher_no="";
	                                   for(var k=0;k<vno.length;k++)
	                                   {
	                                	   		voucher_no=baseResponse.getElementsByTagName("VOUCHER_NO")[k].firstChild.nodeValue;       	                                                  
	                                            var option=document.createElement("OPTION");
	                                            option.text=voucher_no;
	                                            option.value=voucher_no;
	                                            try
	                                            {
	                                            		txtVoucher_No.add(option);
	                                            }
	                                            catch(errorObject)
	                                            {
	                                            		txtVoucher_No.add(option,null);
	                                            }
	                                   }           
                               }
                    	   	   else
                    	   		   	   alert("No Voucher Found");
                       }
                       else if(Command=="load_Voucher_Details")
                       {
	                    	   if(flag=="success")
	                	   	   {
	                	   		   	   var head_code=baseResponse.getElementsByTagName("account_head_code");   	                	   		   	  
	                	   		   	   for(var k=0;k<head_code.length;k++)
	                                   {
	                	   		   		   	    item1[k]=baseResponse.getElementsByTagName("account_head_code")[k].firstChild.nodeValue;
	                	   		   		   	    item2[k]=baseResponse.getElementsByTagName("account_no")[k].firstChild.nodeValue;
	                	   		   		   	    item3[k]=baseResponse.getElementsByTagName("bank_id")[k].firstChild.nodeValue;
	                	   		   		   	    if(item3[k]=="null")
	                	   		   		   	    	item3[k]="";
	                	   		   		   	    item4[k]=baseResponse.getElementsByTagName("branch_id")[k].firstChild.nodeValue;
	                	   		   		   	    if(item4[k]=="null")
	                	   		   		   	    	item4[k]="";	                	   		   		   	    
	                	   		   		   	    item5[k]=baseResponse.getElementsByTagName("cr_dr_indicator")[k].firstChild.nodeValue;
	                	   		   		   	    item6[k]=baseResponse.getElementsByTagName("sub_ledger_type_code")[k].firstChild.nodeValue;
	                	   		   		   	    item7[k]=baseResponse.getElementsByTagName("sub_ledger_type_desc")[k].firstChild.nodeValue;
	                	   		   		   	    item8[k]=baseResponse.getElementsByTagName("sub_ledger_code")[k].firstChild.nodeValue;
	                	   		   		   	 
	                	   		   		   	    item9[k]=baseResponse.getElementsByTagName("cname")[k].firstChild.nodeValue;
	                	   		   		   	    item10[k]=baseResponse.getElementsByTagName("cheque_or_dd")[k].firstChild.nodeValue;
	                	   		   		   	    if(item10[k]=="null")
	                	   		   		   	    	item10[k]="";
		              	   		   		   	    item11[k]=baseResponse.getElementsByTagName("cheque_dd_no")[k].firstChild.nodeValue;
				              	   		   		if(item11[k]=="null")
				              	   		   			item11[k]="";
		              	   		   		   	    item12[k]=baseResponse.getElementsByTagName("cheque_dd_date")[k].firstChild.nodeValue;
				              	   		   		if(item12[k]=="null")
				              	   		   			item12[k]="";
		              	   		   		   	    item13[k]="";
		              	   		   		   	    item14[k]=baseResponse.getElementsByTagName("account_head_desc")[k].firstChild.nodeValue;
		              	   		   		   	    item15[k]=baseResponse.getElementsByTagName("bank_name")[k].firstChild.nodeValue;
				              	   		   		if(item15[k]=="null")
				              	   		   			item15[k]="";
				              	   		   		item16[k]=baseResponse.getElementsByTagName("amount")[k].firstChild.nodeValue;
		              	   		   		   	    item17[k]=baseResponse.getElementsByTagName("particulars")[k].firstChild.nodeValue;
				              	   		   		if(item17[k]=="null")
				              	   		   			item17[k]="";
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
                    calClear();
		 for(var i=0;i<item1.length;i++)
		 {
			  var mycurrent_row=document.createElement("TR");                
	          mycurrent_row.id=seq;

	          cell2=document.createElement("TD");       
	          var H_code=document.createElement("input");
	          H_code.type="hidden";
	          H_code.name="H_code";
	          H_code.value=item1[i];
	          cell2.appendChild(H_code);
	          var currentText=document.createTextNode(item1[i]+"-"+item14[i]);
	          cell2.appendChild(currentText);
	          mycurrent_row.appendChild(cell2);
	          
	          cell2=document.createElement("TD"); 
	          var acc_no=document.createElement("input");
	          acc_no.type="hidden";
	          acc_no.name="acc_no";
	          acc_no.value=item2[i];
	          cell2.appendChild(acc_no);
	          var currentText=document.createTextNode(item2[i]);
	          cell2.appendChild(currentText);
	          mycurrent_row.appendChild(cell2);
	          
	          cell2=document.createElement("TD"); 
	          var bank_id=document.createElement("input");
	          bank_id.type="hidden";
	          bank_id.name="bank_id";
	          bank_id.value=item3[i];
	          cell2.appendChild(bank_id);
	          var branch_id=document.createElement("input");
	          branch_id.type="hidden";
	          branch_id.name="branch_id";
	          branch_id.value=item4[i];
	          cell2.appendChild(branch_id);
	          var currentText=document.createTextNode(item15[i]);
	          cell2.appendChild(currentText);
	          mycurrent_row.appendChild(cell2);
	               
	          cell2=document.createElement("TD"); 
	          var CR_DR_type=document.createElement("input");
	          CR_DR_type.type="hidden";
	          CR_DR_type.name="CR_DR_type";
	          CR_DR_type.value=item5[i];
	          cell2.appendChild(CR_DR_type);
	          var currentText=document.createTextNode(item5[i]);
	          cell2.appendChild(currentText);
	          mycurrent_row.appendChild(cell2);
	          
	          cell2=document.createElement("TD");
	          var SL_type=document.createElement("input");
	          SL_type.type="hidden";
	          SL_type.name="SL_type";
	          SL_type.value=item6[i];
	          cell2.appendChild(SL_type);
	          var currentText=document.createTextNode(item7[i]);
	          cell2.appendChild(currentText);
	          mycurrent_row.appendChild(cell2);
	         
	          cell2=document.createElement("TD");
	          var SL_code=document.createElement("input");
	          SL_code.type="hidden";
	          SL_code.name="SL_code";
	          SL_code.value=item8[i];
	          cell2.appendChild(SL_code);	
	          var paid_to=document.createElement("input");
              paid_to.type="hidden";
              paid_to.name="Paid_To";
              paid_to.value=item13[i];
              cell2.appendChild(paid_to);
	          var currentText=document.createTextNode(item9[i]);
	          cell2.appendChild(currentText);
	          mycurrent_row.appendChild(cell2);
	          
	          cell2=document.createElement("TD"); 
	          var ch_dd=document.createElement("input");
	          ch_dd.type="hidden";
	          ch_dd.name="ch_dd";
	          ch_dd.value=item10[i];
	          cell2.appendChild(ch_dd);
	          var currentText=document.createTextNode(item10[i]);
	          cell2.appendChild(currentText);
	          mycurrent_row.appendChild(cell2);
	          
	          cell2=document.createElement("TD"); 
	          var ch_no=document.createElement("input");
	          ch_no.type="hidden";
	          ch_no.name="ch_no";
	          ch_no.value=item11[i];
	          cell2.appendChild(ch_no);
	          var currentText=document.createTextNode(item11[i]);
	          cell2.appendChild(currentText);
	          mycurrent_row.appendChild(cell2);
	          
	          cell2=document.createElement("TD"); 
	          var ch_date=document.createElement("input");
	          ch_date.type="hidden";
	          ch_date.name="ch_date";
	          ch_date.value=item12[i];
	          cell2.appendChild(ch_date);
	          var currentText=document.createTextNode(item12[i]);
	          cell2.appendChild(currentText);
	          mycurrent_row.appendChild(cell2);
	         
	          cell2=document.createElement("TD"); 
	          var sl_amt=document.createElement("input");
	          sl_amt.type="hidden";
	          sl_amt.name="sl_amt";
	          sl_amt.value=item16[i];
	          cell2.appendChild(sl_amt);
	          var currentText=document.createTextNode(item16[i]);
	          cell2.appendChild(currentText);
	          mycurrent_row.appendChild(cell2);
	           
	          cell2=document.createElement("TD");                
	          var particular=document.createElement("input");
	          particular.type="hidden";
	          particular.name="sl_particular";
	          particular.value=item17[i];
	          cell2.appendChild(particular);
	          var currentText=document.createTextNode(item17[i]);
	          cell2.appendChild(currentText);
	          mycurrent_row.appendChild(cell2);
	
	          tbody.appendChild(mycurrent_row);
	          //clearall();
	          /** Increment Sequence Number */ 
	          seq=seq+1;
	          if(item1[i]==901001)
	          {	        	
	        	  document.getElementById("cmbMas_SL_type").value=item6[i];
	        	  	 document.getElementById("cmbMas_SL_type1").value=item7[i];
	        	  //     doFunction('Load_MasterSL_Code',item6[i]);
	        	      
	        	       document.getElementById("txtUnitId").value=item8[i];
	        	       document.getElementById("txtUnitId1").value=item9[i];
	        	       document.getElementById("cmbMas_SL_Code").value=item8[i];
	        	       document.getElementById("cmbMas_SL_Code1").value=item9[i];
	        	       
	        	       document.getElementById("txtTotalAmt").value=item16[i];
	        	       document.getElementById("txtParticular").value=item17[i];
	        	 //      setTimeout('document.getElementById("cmbMas_SL_Code").value=item8[i]',900); 
	        	       
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

function call_date(dateCtrl,blr_flag)                        // TB_checking 
{       if(blr_flag==1)
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
                        else 
                          {
                          		  doFunction_voucher('load_voucher_no');
                          }
                }
         }
}

/////////////////////////////////////////////   clearall() by User /////////////////////////////////////////////////////

function clrForm(param)
{
		document.getElementById("txtVoucher_No").value="";        
		document.getElementById("cmbReason").value="";        
        document.getElementById("txtUnitId").value="";       
		document.getElementById("txtDebitHead").value="901001";	
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
		document.getElementById("cmbMas_SL_type").value="";
	    document.getElementById("cmbMas_SL_Code").value="";
	    document.getElementById("txtUnitId").value="";
	    document.getElementById("txtTotalAmt").value="";
	    document.getElementById("txtParticular").value="";
	 //   document.getElementById("txtOfficeID_mas").value="";  
     //   document.getElementById("txtEmpID_mas").value="";
//		document.getElementById("cmbMas_SL_type").disabled=true;
//	    document.getElementById("cmbMas_SL_Code").disabled=true;
//	    document.getElementById("txtTotalAmt").disabled=true;
//		document.getElementById("txtDebitHead").disabled=true;
//	    document.getElementById("txtParticular").disabled=true;
		
        var tbody=document.getElementById("grid_body");
        var t=0;
        for(t=tbody.rows.length-1;t>=0;t--)
        {
                tbody.deleteRow(0);
        }
}

