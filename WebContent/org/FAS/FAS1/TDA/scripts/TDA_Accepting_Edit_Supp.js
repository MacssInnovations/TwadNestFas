
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
var item13=new Array();var item14=new Array();var item15=new Array();
var item16=new Array();
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


function fordcb(val)
{
	if(document.getElementById("cmbSL_type").value==14)
	{
		document.getElementById("benifici").style.display='block';
	
	}else{
		document.getElementById("benifici").style.display='none';
		loadSLType('null',val);
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
		 
		 call_clr();
		 var UnitCode=document.getElementById("cmbAcc_UnitCode").value;	
	     var office_code=document.getElementById("cmbOffice_code").value 
	     var Journal_type="";
	     var txtCrea_date= document.getElementById("txtCrea_date").value;    
	     var supNo=document.getElementById("supNo").value;
	     if(document.TDA_TCA.Journal_type[0].checked==true)
		       {
                       Journal_type="TDAAS";
                       }
		 else
                 {
			    Journal_type="TCAAS";	  
                }
         if(command=="load_Voucher_No") 
		 {
        	    url="../../../../../TDA_Accepting_Edit_Supp?command=loadVr_sup&Option=Edit&cmbAcc_UnitCode="+UnitCode+"&cmbOffice_code="+office_code+"&txtCrea_date="+txtCrea_date+"&Journal_type="+Journal_type+"&supNo="+supNo;         			  
		// alert("URL===>"+url);
		 }
		 else if(command=="load_Voucher_Details") 
		 {
			    var accepted_slno=document.getElementById("accepted_slno").value;
			    url="../../../../../TDA_Accepting_Edit_Supp?command=loadVrDetails_sup&cmbAcc_UnitCode="+UnitCode+"&cmbOffice_code="+office_code+"&txtCrea_date="+txtCrea_date+"&Journal_type="+Journal_type+"&accepted_slno="+accepted_slno+"&supNo="+supNo;         			  
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

function loadSLType(SLCode,SLType)
{		 
		 var txtUnitId=document.getElementById("cmbAcc_UnitCode").value;
		 var ac_head_code=document.getElementById("txtAcc_HeadCode").value;
		 if((ac_head_code==900109 || ac_head_code==901002) && SLType==5)
		 {
				if(txtUnitId=="")
				{
						 alert("select Transfer Unit");
						 document.getElementById("cmbSL_type").value="";
						 return false;
				}
				document.getElementById("offlist_div_trans").style.display="none";
			    document.getElementById("emplist_div_trans").style.display="none";   
		   		var url="../../../../../TDA_TCA_Acceptance_Edit?command=loadSLType&txtUnitId="+txtUnitId;		   		
		   		var req=getTransport();
		   		req.open("GET",url,true); 
		   		req.onreadystatechange=function()
		   		{
		   				 Acceptance_ServletResponse(req,SLCode);
		   		}   
		   		req.send(null);
		 }
		 else
		 {			 	
			 	doFunction('Load_SL_Code',SLType);
		 }
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
                         if(Command=="loadVr_sup")
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
                                       document.getElementById("accepted_slno").length=1;
                                       alert("No Voucher Found");
                               }
                         }
                         else if(Command=="loadVrDetails_sup")
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
                        	   		   var particulars=baseResponse.getElementsByTagName("particulars")[0].firstChild.nodeValue;
                        	   		   var acceptance=baseResponse.getElementsByTagName("acceptance")[0].firstChild.nodeValue;
                        	   		   var reason=baseResponse.getElementsByTagName("reason")[0].firstChild.nodeValue;
                        	   		   document.getElementById("originated_slno").value=voucher_no;
                        	   		   document.getElementById("originated_date").value=voucher_date;
                        	   		   document.getElementById("txtUnitName").value=unit_name;
                        	   		   document.getElementById("txtUnitId").value=unit_id;
                        	   		   document.getElementById("txtDebitHead").value=acc_head;
                        	   		   document.getElementById("txtTotalAmt").value=voucher_total_amount;
                        	   		   document.getElementById("txtReason").value=reason_for_transfer;
                        	   		  // document.getElementById("txtRemarks").value=particulars;   		
                        	   		   if(particulars=="null")
	        	   		   		   	   {
	        	   		   		   		   document.getElementById("txtRemarks").value="";
	        	   		   		   	   }
	        	   		   		   	   else
	        	   		   		   	   document.getElementById("txtRemarks").value=particulars;           	   			   		
                	   			   	   for(var i=0;i<count.length;i++)
                	   			   	   {
	                                	   		item1[i]=baseResponse.getElementsByTagName("trn_acc_head")[i].firstChild.nodeValue;
		               	   		   		   	    item2[i]=baseResponse.getElementsByTagName("cr_dr_indicator")[i].firstChild.nodeValue;
		               	   		   		   	    
				               	   		   		if(baseResponse.getElementsByTagName("trn_sub_type_code")[i].firstChild.nodeValue=='0')
		            	   		   		   	    {
		            	   		   		   	    	item3[i]="0";
		            	   		   		   	    	item4[i]='--';
		            	   		   		   	    }
		            	   		   		   	    else{
		            	   		   		   	    item3[i]=baseResponse.getElementsByTagName("trn_sub_type_code")[i].firstChild.nodeValue;
		            	   		   		   	    item4[i]=baseResponse.getElementsByTagName("trn_sub_type_desc")[i].firstChild.nodeValue;
		            	   		   		   	    }
		               	   		   		   	  //  item3[i]=baseResponse.getElementsByTagName("trn_sub_type_code")[i].firstChild.nodeValue;
		               	   		   		   	  //  item4[i]=baseResponse.getElementsByTagName("trn_sub_type_desc")[i].firstChild.nodeValue;
						               	   		  if(baseResponse.getElementsByTagName("trn_sub_code")[i].firstChild.nodeValue=='0')
			              	   		   		   	    {
			              	   		   		   	    	item5[i]="0";
			              	   		   		   	    	item6[i]="--";
			              	   		   		   	    }
			              	   		   		   	    else{
			              	   		   		   	    item5[i]=baseResponse.getElementsByTagName("trn_sub_code")[i].firstChild.nodeValue;
			              	   		   		   	    item6[i]=baseResponse.getElementsByTagName("trn_sub_desc")[i].firstChild.nodeValue;
			              	   		   		   	    }
//		               	   		   		   	    item5[i]=baseResponse.getElementsByTagName("trn_sub_code")[i].firstChild.nodeValue;
//		               	   		   		   	    item6[i]=baseResponse.getElementsByTagName("trn_sub_desc")[i].firstChild.nodeValue;
		               	   		   		   	    item7[i]=baseResponse.getElementsByTagName("amount")[i].firstChild.nodeValue;
				               	   		   		if(baseResponse.getElementsByTagName("trn_particulars")[i].firstChild.nodeValue=="null")
		            	   		   		   	    {
		            	   		   		   	    	item8[i]='--';
		            	   		   		   	    	item13[i]="null";
		            	   		   		   	    }
		            	   		   		   	    else
		            	   		   		   	    {
		            	   		   		   	    item8[i]=baseResponse.getElementsByTagName("trn_particulars")[i].firstChild.nodeValue;
		            	   		   		   	    item13[i]=baseResponse.getElementsByTagName("trn_particulars")[i].firstChild.nodeValue;
		            	   		   		   	    }
		               	   		   		   	  //  item8[i]=baseResponse.getElementsByTagName("trn_particulars")[i].firstChild.nodeValue;
		               	   		   		   	    item9[i]=baseResponse.getElementsByTagName("head_desc")[i].firstChild.nodeValue;
				               	   		   		if(baseResponse.getElementsByTagName("trn_bookNo")[i].firstChild.nodeValue=="0")
		            	   		   		   	    {
		            	   		   		   	    	item10[i]='--';
		            	   		   		   	    	item14[i]='0';
		            	   		   		   	    }
		            	   		   		   	    else
		            	   		   		   	    {
		            	   		   		   	    item10[i]=baseResponse.getElementsByTagName("trn_bookNo")[i].firstChild.nodeValue;
		            	   		   		   	    item14[i]=baseResponse.getElementsByTagName("trn_bookNo")[i].firstChild.nodeValue;
		            	   		   		   	    }
						               	   		 if(baseResponse.getElementsByTagName("trn_bookPageno")[i].firstChild.nodeValue=="0")
		              	   		   		   	    {
		              	   		   		   	    	item11[i]='--';
		              	   		   		   	    	item15[i]='0';
		              	   		   		   	    }
		              	   		   		   	    else
		              	   		   		   	    {
		              	   		   		   	    item11[i]=baseResponse.getElementsByTagName("trn_bookPageno")[i].firstChild.nodeValue;
		              	   		   		   	    item15[i]=baseResponse.getElementsByTagName("trn_bookPageno")[i].firstChild.nodeValue;
		              	   		   		   	    }
				                    	   		 if(baseResponse.getElementsByTagName("trn_bookDate")[i].firstChild.nodeValue=="null")
		              	   		   		   	    {
		              	   		   		   	    	item12[i]='--';
		              	   		   		   	    	item16[i]="null";
		              	   		   		   	    }
		              	   		   		   	    else
		              	   		   		   	    {
		              	   		   		   	    item12[i]=baseResponse.getElementsByTagName("trn_bookDate")[i].firstChild.nodeValue;
		              	   		   		   	    item16[i]=baseResponse.getElementsByTagName("trn_bookDate")[i].firstChild.nodeValue;
		              	   		   		   	    }
				               	   		   	//	item10[i]=baseResponse.getElementsByTagName("trn_bookNo")[i].firstChild.nodeValue;
//		            	   		   		   	    item11[i]=baseResponse.getElementsByTagName("trn_bookPageno")[i].firstChild.nodeValue;
//		            	   		   		   	    item12[i]=baseResponse.getElementsByTagName("trn_bookDate")[i].firstChild.nodeValue;
                             
		               	   		   		   	    
		               	   		   		   	    
                	   			   	   }
                	   			   	   loadGrid();
                        	   		   
	                           }
	                    	  
                         }
                         else if(Command=="loadSLType")
                         {          			    	   
         		    	       if(flag=="success")
         		    	       {         		    	    			 
         		    	    		   var cmbSL_Code=document.getElementById("cmbSL_Code");
         				        	   var child=cmbSL_Code.childNodes;
         				        	   for(var i=child.length-1;i>1;i--)
         				        	   {
         				        	    	 
         				        	    	    cmbSL_Code.removeChild(child[i]);
         				        	   } 
         				        	       
         				        	   var count=baseResponse.getElementsByTagName("office_id");           				        	   
         				               var sl_code="";var sl_desc="";			                
         				               for(var i=0;i<count.length;i++)
         				               {
	         				                	sl_code=baseResponse.getElementsByTagName("office_id")[i].firstChild.nodeValue;
	         				                	sl_desc=baseResponse.getElementsByTagName("office_name")[i].firstChild.nodeValue;
	         				                    var opt=document.createElement("option");
	         				                    opt.setAttribute("value",sl_code);
	         				                    var opttext=document.createTextNode(sl_desc);
	         				                    opt.appendChild(opttext);
	         				                    cmbSL_Code.appendChild(opt);
         				               }
         				               if(slcode!=null)
         				                	 	document.getElementById("cmbSL_Code").value=slcode;
         				               document.getElementById("offlist_div_trans").style.display="none";
         							   document.getElementById("emplist_div_trans").style.display="none";   
         			   	 	   }
         			   	 	   else
         			   	 	   {
         			   	 			   alert("No Sub Ledger Type Found");
         			   	 	   }
         		         }            
              }
                
		 }    
}

function loadGrid()
{
		 tbody=document.getElementById("grid_body");
         var t=0;
         for(t=tbody.rows.length-1;t>=0;t--)
         {
              tbody.deleteRow(0);
         }
		 clearall();
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
	          particular.value=item13[i];
	          cell2.appendChild(particular);
	          var currentText=document.createTextNode(item8[i]);
	          cell2.appendChild(currentText);
	          mycurrent_row.appendChild(cell2);
	          
	          cell2=document.createElement("TD");                
	          var bkNo1=document.createElement("input");
	          bkNo1.type="hidden";
	          bkNo1.name="m_bkNo";
	          bkNo1.value=item14[i];
	          cell2.appendChild(bkNo1);
	          var currentText=document.createTextNode(item10[i]);
	          cell2.appendChild(currentText);
	          mycurrent_row.appendChild(cell2);
	          
	          cell2=document.createElement("TD");                
	          var bkPageno=document.createElement("input");
	          bkPageno.type="hidden";
	          bkPageno.name="m_bkPageno";
	          bkPageno.value=item15[i];
	          cell2.appendChild(bkPageno);
	          var currentText=document.createTextNode(item11[i]);
	          cell2.appendChild(currentText);
	          mycurrent_row.appendChild(cell2);
	          
	          cell2=document.createElement("TD");                
	          var bookDated=document.createElement("input");
	          bookDated.type="hidden";
	          bookDated.name="m_bookDate";
	          bookDated.value=item16[i];
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
        com_id=scod;     
        var r=document.getElementById(scod);
        var rcells=r.cells;
        try {document.getElementById("txtAcc_HeadCode").value=rcells.item(1).firstChild.value;}catch(e){}
        doFunction('checkCode','null');   
        try{com_cmbSL_type=rcells.item(3).firstChild.value;} catch(e){cmbMas_SL_type=""}
        try{com_cmbSL_Code=rcells.item(4).firstChild.value;} catch(e){cmbMas_SL_code=""}
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
        	//alert("14");
        	document.getElementById("benifici").style.display='block';
                document.getElementById("txtEmpID_trs").value=com_cmbSL_Code;
                emp_flag=false;            
        }
        if((document.getElementById("txtAcc_HeadCode").value==900109 || document.getElementById("txtAcc_HeadCode").value==901002) && com_cmbSL_type==5)   
        {         
        	document.getElementById("benifici").style.display='none';
	            loadSLType(com_cmbSL_Code,com_cmbSL_type);
        }
        else if(document.getElementById("txtAcc_HeadCode").value==900109)
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
            
       
        if(rcells.item(2).firstChild.value=="CR")
        		document.TDA_TCA.rad_sub_CR_DR[0].checked=true;
        else if(rcells.item(2).firstChild.value=="DR")
        		document.TDA_TCA.rad_sub_CR_DR[1].checked=true;
         
        try{document.getElementById("txtsub_Amount").value=rcells.item(5).firstChild.value;}catch(e){}
        try{document.getElementById("txtParticular").value=rcells.item(6).firstChild.value;}catch(e){}   
        try{document.getElementById("bookNo").value=rcells.item(7).firstChild.value;}catch(e){}
        try{document.getElementById("bookPageNo").value=rcells.item(8).firstChild.value;}catch(e){}  
        try{document.getElementById("book_date").value=rcells.item(9).firstChild.value;}catch(e){}
 	    setTimeout('document.getElementById("cmbSL_type").value=com_cmbSL_type',900); 
 	    if(scod==0)
        {
	        	document.getElementById("txtAcc_HeadCode").disabled=true;
	            document.getElementById("cmbSL_type").disabled=true;
	            document.getElementById("cmbSL_Code").disabled=true;
	            document.getElementById("txtsub_Amount").disabled=true;
	            document.TDA_TCA.cmddelete.disabled=true;
	            document.getElementById("bookNo").disabled=true;
	            document.getElementById("bookPageNo").disabled=true;
	            document.getElementById("book_date").disabled=true;
        }
        else
        {
	        	document.getElementById("txtAcc_HeadCode").disabled=false;
	            document.getElementById("cmbSL_type").disabled=false;
	            document.getElementById("cmbSL_Code").disabled=false;
	            document.getElementById("txtsub_Amount").disabled=false;
	            document.TDA_TCA.cmddelete.disabled=false;
	            document.getElementById("bookNo").disabled=false;
	            document.getElementById("bookPageNo").disabled=false;
	            document.getElementById("book_date").disabled=false;
        }
 	    document.TDA_TCA.cmdupdate.style.display='block';
        document.TDA_TCA.cmdadd.style.display='none';    
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
//        	 	document.TDA_TCA.Journal_type[0].checked=false;
//	    		document.TDA_TCA.Journal_type[1].checked=false; 
//	    		document.getElementById("accepted_slno").length=1;
                         
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



/////////////////////////////////////////////   ADD & UPDATE & DELETE /////////////////////////////////////////////////////
function load_grid(cmd)
{
        if(document.getElementById("txtAcc_HeadCode").value.length==0)
        {
                alert("Enter A/c Head Code");
                return false;
        }                
        var acc=document.getElementById("txtAcc_HeadCode").value;
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
        if(items[8]=="null" || items[8]=='--' || items[8]=="")
        {
       	 items[8]="--";
       	 items[15]="null";
        }
        else
        {
       	 items[8]=document.getElementById("txtParticular").value;
       	 items[15]=document.getElementById("txtParticular").value;
        }
        items[9]=document.getElementById("bookNo").value;
        if(items[9]=='0' || items[9]=="--" || items[9]=="")
        {
       	 items[9]='--';
       	 items[14]="0";
       	
        }
        else
        {
       	 items[9]=document.getElementById("bookNo").value;
       	 items[14]=document.getElementById("bookNo").value;
        }
        items[10]=document.getElementById("bookPageNo").value;
        if(items[10]=='0' || items[10]=="--" || items[10]=="")
        {
       	 items[10]='--';
       	 items[13]="0";
       	
        }
        else
        {
       	 items[10]=document.getElementById("bookPageNo").value;
       	 items[13]=document.getElementById("bookPageNo").value;
        }
        items[11]=document.getElementById("book_date").value;
        if(items[11]=="null" || items[11]=="--" || items[11]=="")
        {
       	 items[11]='--';
       	 items[12]="null";
       	
        }
        else
        {
       	 items[11]=document.getElementById("book_date").value;
       	 items[12]=document.getElementById("book_date").value;
        }
        
        tbody=document.getElementById("grid_body");
        if(cmd=="ADD_GRID")
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
                particular.value=items[15];
                cell2.appendChild(particular);
                var currentText=document.createTextNode(items[8]);
                cell2.appendChild(currentText);
                mycurrent_row.appendChild(cell2);
                
                cell2=document.createElement("TD");                
    	          var bkNo1=document.createElement("input");
    	          bkNo1.type="hidden";
    	          bkNo1.name="m_bkNo";
    	          bkNo1.value=items[14];
    	          cell2.appendChild(bkNo1);
    	          var currentText=document.createTextNode(items[9]);
    	          cell2.appendChild(currentText);
    	          mycurrent_row.appendChild(cell2);
    	          
    	          cell2=document.createElement("TD");                
    	          var bkPageno=document.createElement("input");
    	          bkPageno.type="hidden";
    	          bkPageno.name="m_bkPageno";
    	          bkPageno.value=items[13];
    	          cell2.appendChild(bkPageno);
    	          var currentText=document.createTextNode(items[10]);
    	          cell2.appendChild(currentText);
    	          mycurrent_row.appendChild(cell2);
    	          
    	          cell2=document.createElement("TD");                
    	          var bookDated=document.createElement("input");
    	          bookDated.type="hidden";
    	          bookDated.name="m_bookDate";
    	          bookDated.value=items[12];
    	          cell2.appendChild(bookDated);
    	          var currentText=document.createTextNode(items[11]);
    	          cell2.appendChild(currentText);
    	          mycurrent_row.appendChild(cell2);

                tbody.appendChild(mycurrent_row);
                clearall();
                // Increment Sequence Number 
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
             
                try{rcells.item(6).firstChild.value=items[15];}catch(e){}
                try{rcells.item(6).lastChild.nodeValue=items[8];}catch(e){}
                
                try{rcells.item(7).firstChild.value=items[14];}catch(e){}
                try{rcells.item(7).lastChild.nodeValue=items[9];}catch(e){}
                
                try{rcells.item(8).firstChild.value=items[13];}catch(e){}
                try{rcells.item(8).lastChild.nodeValue=items[10];}catch(e){}
                
                try{rcells.item(9).firstChild.value=items[12];}catch(e){}
                try{rcells.item(9).lastChild.nodeValue=items[11];}catch(e){}
                
                alert("Record Updated");
                clearall();
        }
}

/////////////////////////////////////////////   clearall() by User /////////////////////////////////////////////////////

function clearall()
{
		document.getElementById("txtAcc_HeadCode").value="";
		document.getElementById("txtAcc_HeadDesc").value="";
	 	document.getElementById("offlist_div_trans").style.display='none';
	    document.getElementById("emplist_div_trans").style.display='none';   
        document.getElementById("txtsub_Amount").value="";
        document.getElementById("txtParticular").value="";     
        document.getElementById("txtOfficeID_trs").value="";
        document.getElementById("txtEmpID_trs").value=""; 
        document.getElementById("bookNo").value="";        
        document.getElementById("bookPageNo").value="";  
        document.getElementById("book_date").value=""; 
        
        var cmbSL_Code1=document.getElementById("cmbSL_Code"); 
        clear_Combo(cmbSL_Code1);   
		document.TDA_TCA.cmdadd.style.display='block';
		document.TDA_TCA.cmdupdate.style.display='none';
		
		document.getElementById("txtAcc_HeadCode").disabled=false;
        document.getElementById("cmbSL_type").disabled=false;
        document.getElementById("cmbSL_Code").disabled=false;
        document.getElementById("txtsub_Amount").disabled=false;
        
        document.TDA_TCA.cmddelete.disabled=true;	 
		document.TDA_TCA.cmdclear.disabled=false;
}

function clrVoucherDetails()
{
		document.getElementById("originated_date").value="";
   	    document.getElementById("txtUnitId").value="";
   	    document.getElementById("txtUnitName").value="";   	    
   	    document.getElementById("txtTotalAmt").value="";
   	    document.getElementById("txtReason").value="";
   	    document.getElementById("txtDebitHead").value="";
   	    document.getElementById("originated_slno").value="";
}
function clrForm(param)
{		
		document.TDA_TCA.Journal_type[0].checked=false;
		document.TDA_TCA.Journal_type[1].checked=false; 
		document.getElementById("linkId").style.visibility="hidden";
		document.getElementById("accepted_slno").length=1;
		document.getElementById("txtReason").value="";        
	    document.getElementById("txtUnitId").value="";      
	    document.getElementById("txtUnitName").value=""; 
		document.getElementById("txtDebitHead").value="";
		document.getElementById("txtAcc_HeadCode").value="";
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
		
        clrVoucherDetails();
        document.getElementById("txtRemarks").value="";      
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
        	       
        	       
                /* var grid_body=document.getElementById("grid_body");
                seq=0;
                for(var i=0;i<grid_body.rows.length;i++)
                {
                                alert(" Before"+grid_body.rows[i].id);
                                /*grid_body.rows[i].id=seq;
                                alert(" After"+grid_body.rows[i].id);
                                
                                var gr=document.getElementById(grid_body.rows[i].id);
                                var rcells=gr.cells;  
                                alert(grid_body.rows[i].cells[0]);
                                alert(grid_body.rows[i].cells[0].childNodes.length);
                                // theGridView.rows(rowCount).cells(9).children
                                
                            /*var gr=document.getElementById(grid_body.rows[i].id);
                                var rcells=gr.cells;  
                                alert(grid_body.rows[i].cells[0].value);
                                var anc=document.createElement("A");
                                var url="javascript:loadTable('"+seq+"')";
                                anc.href=url;
                                var txtedit=document.createTextNode("EDIT");
                                var txtedit="asd";
                                alert(rcells.item(0).lastChild.nodeValue+" , "+rcells.item(0).firstChild.value+" , "+rcells.item(0).firstChild.nodeValue);
                                rcells.item(0)="";
                                seq=seq+1;
                                //grid_body.rows[i].id=seq;
                                //alert(" After"+grid_body.rows[i].id);
                                seq++;
                }*/
                    
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
        if(document.getElementById("accepted_slno").value=="")
        {
            	alert("Select Accepted Sl.No.");
            	return false;
        }
        if(document.getElementById("txtCrea_date").value.length==0)
        {
	            alert("Enter the Date of Creation");           
	            return false;    
        }
		if(tbody.rows.length==0)
		{
				alert("Enter Details Part");
				return false;
		}
		else
        {
                var dr_check_amt=0;var cr_check_amt=0;var count=0;
                rows=tbody.getElementsByTagName("TR");                           
                for(i=0;i<rows.length;i++)
                {
                            var cells=rows[i].cells;                                              
                            if(cells.item(2).lastChild.nodeValue=='CR')
                            {
                                    dr_check_amt=parseFloat(dr_check_amt) + parseFloat(cells.item(5).lastChild.nodeValue);
                                    if(document.TDA_TCA.Journal_type[0].checked==true)
               	                	{
               	                		  if(cells.item(1).firstChild.value==900109)
               	                			  count++;
               	                	}                                      
                            } 
                            else
       	                	{
                            		cr_check_amt=parseFloat(cr_check_amt) + parseFloat(cells.item(5).lastChild.nodeValue);       
       	                		  	if(cells.item(1).firstChild.value==901002)
       	                		  		  count++;
       	                	}
                            
                      
                } 
                
                if(dr_check_amt!=cr_check_amt)
                {
                            alert("Total Amount of DR & CR should be equal");
                            return false; 
                }
                else if(cr_check_amt!=document.getElementById("txtTotalAmt").value)
                {
                            alert("Total Amount of CR & TDA Total Debit Amount should be equal");
                            return false;
                }
                else
                {
    	                	if(count==0)
                            {
                                 	if(document.TDA_TCA.Journal_type[0].checked==true)
         	            				  alert("Detail should have at least one CR Account Head 900108");
         	            			else
         	            				  alert("Detail should have at least one DR Account Head 901001");
         	            			return false;
                                     
                            }
    	                	else
    	                			return true;
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
function checkVoucherNo()
{
		var unitcode=document.getElementById("cmbAcc_UnitCode").value;	
	    var offid=document.getElementById("cmbOffice_code").value;	
	    var dt=document.TDA_TCA.txtCrea_date.value;
	    var dat=dt.split("/");		
        var yr=dat[2]; 		
        var mon=dat[1]; 
	    var recNo= document.getElementById("accepted_slno").value;  
	    var pp1="originated_slno";
	 	var Voucher_list_SL= window.open("../../../../../org/FAS/FAS1/TDA/jsps/TDA_TCA_ListAll_SL.jsp?cmbAcc_UnitCode="+unitcode+"&cmbOffice_code="+offid+"&cashbook_yr="+yr+"&cashbook_mn="+mon+"&voucher_no="+recNo+"&param="+pp1,"VoucherList","status=1,height=500,width=500,resizable=YES, scrollbars=yes"); 
	    Voucher_list_SL.moveTo(250,250);  
	    Voucher_list_SL.focus();    
}

function checkAccHead()
{
		if(document.TDA_TCA.Journal_type[0].checked==true)
		{
				if(document.getElementById("txtAcc_HeadCode").value==900109)
				{
						alert("This Account Head already have an entry ");
						document.getElementById("txtAcc_HeadCode").value="";
						document.getElementById("txtAcc_HeadCode").focus();
				}
		}
		else
		{			
				if(document.getElementById("txtAcc_HeadCode").value==901002)
				{
						alert("This Account Head already have an entry ");
						document.getElementById("txtAcc_HeadCode").value="";
						document.getElementById("txtAcc_HeadCode").focus();
				}
		}

}

function Show(unitcode,offid,yr,mon,recNo)
{
		var unitcode=document.getElementById("txtUnitId").value;	
	    var offid=document.getElementById("cmbOffice_code").value 	    
	    var yr= document.getElementById("txtCB_Year").value;   
	    var mon= document.getElementById("txtCB_Month").value;		
	    var recNo= document.getElementById("originated_slno").value;  
	 	var Voucher_list_SL= window.open("../../../../../org/FAS/FAS1/TDA/jsps/TDA_TCA_ListAll_SL.jsp?cmbAcc_UnitCode="+unitcode+"&cmbOffice_code="+offid+"&cashbook_yr="+yr+"&cashbook_mn="+mon+"&voucher_no="+recNo,"VoucherList","status=1,height=500,width=500,resizable=YES, scrollbars=yes"); 
	    Voucher_list_SL.moveTo(250,250);  
	    Voucher_list_SL.focus();
    
}


function Check_Supplement_No()
{
      var txtCrea_date=document.getElementById("txtCrea_date").value;
     
       var url="../../../../../Supplement_Journal_Create.kv?Command=Check_Supplement_No&txtCrea_date="+txtCrea_date;
      // alert(url);
       var req=getTransport();
       req.open("GET",url,true); 
       req.onreadystatechange=function()
       {
           Check_Supplement_No_Response(req);
       }   
       req.send(null);
}

function Check_Supplement_No_Response(req)
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
                var suppl_error=baseResponse.getElementsByTagName("suppl_error")[0].firstChild.nodeValue;
                alert(suppl_error);  
                //document.getElementById("txtCrea_date").value="";
              }
              else if(flag=="success")
              {
               var supNo1 = document.forms[0].supNo;
                 var supno = baseResponse.getElementsByTagName("supno"); 
                 for(var i=0; i<supno.length; i++)
                     {
                         var opt = document.createElement('option');
                         opt.value = supno[i].firstChild.nodeValue;
                         opt.innerHTML = supno[i].firstChild.nodeValue; //instead of using textnode ,we use innerhtml
                         supNo1.appendChild(opt);
                     }
              
              
              }

       }
  }
}