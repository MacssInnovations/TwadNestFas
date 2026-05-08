var com_id;
var com_cmbSL_Code="";
var com_cmbSL_type="";
var seq=0;
var item1=new Array();var item2=new Array();var item3=new Array();var item4=new Array();
var item5=new Array();var item6=new Array();var item7=new Array();var item8=new Array();
var item9=new Array();var item10=new Array();var item11=new Array();var item12=new Array();

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
                     return false;
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


function loadTransferUnit()
{         
		 var cmbAcc_UnitCode=document.getElementById("cmbAcc_UnitCode").value;		 
         url="../../../../../TDA_Raised_Create?command=loadTransferUnit&txtUnitId="+cmbAcc_UnitCode;
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
	//alert("welcome "+req);
		 if(req.readyState==4)
		 {
			// alert(req.readyState);
                if(req.status==200)
                {  
                	// alert(req.status);
                        var baseResponse=req.responseXML.getElementsByTagName("response")[0];
                        //alert(baseResponse);
                        var tagcommand=baseResponse.getElementsByTagName("command")[0];
                        var Command=tagcommand.firstChild.nodeValue;                                  
                        var flag=baseResponse.getElementsByTagName("flag")[0].firstChild.nodeValue;
                        //alert(Command);
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
                       else if(Command=="load_Vr_No")
                       {
	                    	   if(flag=="success")
	                           {                                      
	                                   var originated_slno=document.getElementById("originated_slno");  
	                                   var child=originated_slno.childNodes;
	                                   for(var i=child.length-1;i>1;i--)
	                                   {
	                                	   		originated_slno.removeChild(child[i]);
	                                   }                                                         
	                                   var vno=baseResponse.getElementsByTagName("voucher_no");
	                                   for(var k=0;k<vno.length;k++)
	                                   {
	                                            var voucher_no=baseResponse.getElementsByTagName("voucher_no")[k].firstChild.nodeValue;				       	                                                  
	                                            var option=document.createElement("OPTION");
	                                            option.text=voucher_no;
	                                            option.value=voucher_no;
	                                            try
	                                            {
	                                            		originated_slno.add(option);
	                                            }
	                                            catch(errorObject)
	                                            {
	                                            		originated_slno.add(option,null);
	                                            }
	                                   }
	                           }
	                           else
	                           {                                                   
	                                   alert("No Voucher Found")
	                           }
                       }
                       else if(Command=="load_Vr_Details")
                       {
                    	   	   if(flag=="success")
                    	   	   {
                    	   		   	//  alert("flag*********"+flag);
                                       var head_code=baseResponse.getElementsByTagName("head_code");      
	        	   		   		   	   var voucher_no=baseResponse.getElementsByTagName("head_code")[0].firstChild.nodeValue;				       	                       
	        	   		   		   	   var unit_code=baseResponse.getElementsByTagName("unit_code")[0].firstChild.nodeValue;
	        	   		   		   	   var reason_for_transfer=baseResponse.getElementsByTagName("reason_for_transfer")[0].firstChild.nodeValue;
	        	   		   		   	   var mst_sub_type_code=baseResponse.getElementsByTagName("mst_sub_type_code")[0].firstChild.nodeValue;
	        	   		   		   	   var mst_sub_code=baseResponse.getElementsByTagName("mst_sub_code")[0].firstChild.nodeValue;	 
	        	   		   		   	   var total_amount=baseResponse.getElementsByTagName("total_amount")[0].firstChild.nodeValue;
	        	   		   		   	   var particulars=baseResponse.getElementsByTagName("particulars")[0].firstChild.nodeValue;                    	   		   		   	   
	        	   		   		   	   document.getElementById("cmbReason").value=reason_for_transfer;
	        	   		   		   	   document.getElementById("txtUnitId").value=unit_code;
	        	   		   		   	   document.getElementById("txtDebitHead").value=voucher_no;
	        	   		   		   	   //document.getElementById("cmbAcc_UnitCode").value=voucher_no;
	        	   		   		   	   document.getElementById("cmbMas_SL_type").value=mst_sub_type_code;
	        	   		   		   	   //alert(mst_sub_code);
	        	   		   		   	   
	        	   		   		   	   document.getElementById("cmbMas_SL_Code").value=mst_sub_code;
//	        	   		   		   	   document.getElementById("txtOfficeID_mas").value=mst_sub_code;                    	   		   		   	    
//	        	   		   		       doFunction('Load_MasterSL_Code',mst_sub_type_code);
//	        	   		   		       document.getElementById("offlist_div_master").style.display="none";
	        	   		   		   	   document.getElementById("txtTotalAmt").value=total_amount;
	        	   		   		   	   document.getElementById("txtRemarks").value=particulars;
	        	   		   		   	  
                    	   		   	   for(var k=0;k<head_code.length;k++)
   	                                   {   	  
                    	   		  	 
                    	   		   		   	    item1[k]=baseResponse.getElementsByTagName("trn_acc_head")[k].firstChild.nodeValue;
                    	   		   		   	    item2[k]=baseResponse.getElementsByTagName("cr_dr_indicator")[k].firstChild.nodeValue;
                    	   		   		   	    item3[k]=baseResponse.getElementsByTagName("trn_sub_type_code")[k].firstChild.nodeValue;
                    	   		   		   	    item4[k]=baseResponse.getElementsByTagName("trn_sub_type_desc")[k].firstChild.nodeValue;
                    	   		   		   	    item5[k]=baseResponse.getElementsByTagName("trn_sub_code")[k].firstChild.nodeValue;
                    	   		   		   	    item6[k]=baseResponse.getElementsByTagName("trn_sub_desc")[k].firstChild.nodeValue;
                    	   		   		   	    item7[k]=baseResponse.getElementsByTagName("amount")[k].firstChild.nodeValue;
                    	   		   		   	    item8[k]=baseResponse.getElementsByTagName("trn_particulars")[k].firstChild.nodeValue;
                    	   		   		   	    item9[k]=baseResponse.getElementsByTagName("head_desc")[k].firstChild.nodeValue;
                    	   		   		   	    
                    	   		   		   	    item10[k]=baseResponse.getElementsByTagName("trn_bookNo")[k].firstChild.nodeValue;
                    	   		   		   	    item11[k]=baseResponse.getElementsByTagName("trn_bookPageno")[k].firstChild.nodeValue;
                    	   		   		   	    item12[k]=baseResponse.getElementsByTagName("trn_bookDate")[k].firstChild.nodeValue;
                    	   		   		   	    
	                                   }
                    	   		   	   loadGrid();
                    	   	   }
                    	   	   
                       }
              }
		 }    
}
function loadTable(id)
{}

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

function doFunction_TDA(command)
{
		 document.getElementById("cmbReason").value="";        
	     document.getElementById("txtUnitId").value="";       
		 document.getElementById("txtDebitHead").value="";	
		 call_clr();
		 var cmbAcc_UnitCode=document.getElementById("cmbAcc_UnitCode").value;	
		 var cmbOffice_code=document.getElementById("cmbOffice_code").value;
		 var txtCrea_date=document.getElementById("txtCrea_date").value;
		 var supNo=document.getElementById("supNo").value;
		 
		 
		 if(document.TDA_TCA.Journal_type[0].checked==true)
		      Journal_type="TDAO";
		 else
			  Journal_type="TCAO";
		 if(command=="load_Voucher_No") 
		 {
			  url="../../../../../TDA_Raised_Edit?command=load_Vr_No&Option=Cancel&txtUnitId="+cmbAcc_UnitCode+"&cmbOffice_code="+cmbOffice_code+"&txtCrea_date="+txtCrea_date+"&Journal_type="+Journal_type+"&supNo="+supNo;           			  
		 }
		 else if(command=="load_Voucher_Details") 
		 {
			  var originated_slno=document.getElementById("originated_slno").value;
			  url="../../../../../TDA_Raised_Edit?command=load_Vr_Details&txtUnitId="+cmbAcc_UnitCode+"&cmbOffice_code="+cmbOffice_code+"&txtCrea_date="+txtCrea_date+"&Journal_type="+Journal_type+"&originated_slno="+originated_slno+"&supNo="+supNo;           			  
		 }
		 //alert(url);
		 req=getTransport();
		 req.open("GET",url,true);        
		 req.onreadystatechange=function()
		 {   
			
              TDA_Raised_ServletResponse(req);
		 }   
		 req.send(null);  	
}

///////////////////////////////////////////    TB_checking and Calender control return value handling
function call_mainJSP_script(fromcal_dateCtrl,blr_flag)     /////////Calender control return value handling
{
        if(blr_flag==1)         // which is used to find the receipt or voucher or payment (creation) date calling field,if so check trial balance
        {
	        	document.TDA_TCA.Journal_type[0].checked=false;
	    		document.TDA_TCA.Journal_type[1].checked=false; 
	    		document.getElementById("originated_slno").length=1;
                        
                var cmbAcc_UnitCode=document.getElementById("cmbAcc_UnitCode").value;
                var cmbOffice_code=document.getElementById("cmbOffice_code").value;
                var TB_date=fromcal_dateCtrl.value;                 
                if(fromcal_dateCtrl.value.length!=0)
                {
                        var url="../../../../../Receipt_SL.view?Command=check_TB_SJV&TB_date="+TB_date+"&cmbAcc_UnitCode="+cmbAcc_UnitCode+"&cmbOffice_code="+cmbOffice_code;                         
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
                        var url="../../../../../Receipt_SL.view?Command=check_TB_SJV&TB_date="+TB_date+"&cmbAcc_UnitCode="+cmbAcc_UnitCode+"&cmbOffice_code="+cmbOffice_code;
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
                        	Check_Supplement_No();
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
                }
         }
}



function clrForm(param)
{				
		document.TDA_TCA.Journal_type[0].checked=false;
		document.TDA_TCA.Journal_type[1].checked=false; 
		document.getElementById("originated_slno").length=1;
		document.getElementById("cmbReason").value="";        
        document.getElementById("txtUnitId").value="";       
		document.getElementById("txtDebitHead").value="";	
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
	    document.getElementById("txtTotalAmt").value="";
	    document.getElementById("txtRemarks").value="";
		document.getElementById("cmbMas_SL_type").disabled=false;
	    document.getElementById("cmbMas_SL_Code").disabled=false;
	    document.getElementById("txtTotalAmt").disabled=false;
		document.getElementById("txtDebitHead").disabled=true;		
        var tbody=document.getElementById("grid_body");
        var t=0;
        for(t=tbody.rows.length-1;t>=0;t--)
        {
                tbody.deleteRow(0);
        }
}
function clearJournal()
{
		document.TDA_TCA.Journal_type[0].checked=false;
		document.TDA_TCA.Journal_type[1].checked=false; 
		document.getElementById("originated_slno").length=1;
		clrForm('clear');
}


function check_leng(remarks)
{	 
	    if((remarks.length)>=190)
	    {
	    		alert("Please Enter below 200 characters");
	    }	 
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



