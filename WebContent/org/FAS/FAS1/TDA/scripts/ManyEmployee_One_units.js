var com_id;
var com_cmbSL_Code="";
var com_cmbSL_type="";
var seq=0;
var item1=new Array();var item2=new Array();var item3=new Array();var item4=new Array();
var item5=new Array();var item6=new Array();var item7=new Array();var item8=new Array();
var item9=new Array();var item10=new Array();var item11=new Array();var item12=new Array();
var item13=new Array();var item14=new Array();var item15=new Array();var item16=new Array();
var item17=new Array();var item18=new Array();


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

function OfficeCheck(param1)
{
doFunction('Load_SL_Code_test',param1);
}

function checkAccHead()
{
		if(document.TCA_POST.cmbDocType.value=="P")
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
                else if(document.getElementById("txtAcc_HeadCode").value==901001)
				{
					alert("This Account Head Code Cannot be used in Payment");
					document.getElementById("txtAcc_HeadCode").value="";
                    document.getElementById("txtAcc_HeadDesc").value="";
					document.getElementById("txtAcc_HeadCode").focus();
				}
	             else if(document.getElementById("txtAcc_HeadCode").value==901002)
				{
						alert("This Account Head Code is not used in Payment.");
						document.getElementById("txtAcc_HeadCode").value="";
	                    document.getElementById("txtAcc_HeadDesc").value="";
						document.getElementById("txtAcc_HeadCode").focus();
				}
                else
                {
               
                    doFunction('checkCode','null');
                 }
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
                 else if(document.getElementById("txtAcc_HeadCode").value==900108)
 				{
 						alert("This Account Head Code is not used in Receipt.");
 						document.getElementById("txtAcc_HeadCode").value="";
                        document.getElementById("txtAcc_HeadDesc").value="";
 						document.getElementById("txtAcc_HeadCode").focus();
 				}
                 else if(document.getElementById("txtAcc_HeadCode").value==900109)
  				{
  						alert("This Account Head Code is not used in Receipt.");
  						document.getElementById("txtAcc_HeadCode").value="";
                         document.getElementById("txtAcc_HeadDesc").value="";
  						document.getElementById("txtAcc_HeadCode").focus();
  				}
                else
                {
              
                    doFunction('checkCode','null');
                }
		}

}

function checkDated()
{
        document.getElementById("txtCrea_date").value="";
        var payDate=document.getElementById("txtPayment_date").value;
        document.getElementById("txtCrea_date").value=payDate;
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
	//document.getElementById("txtVoucher_No").length=0;
	//alert("enter into Function");
		 var cmbAcc_UnitCode=document.getElementById("cmbAcc_UnitCode").value;	
		 var cmbOffice_code=document.getElementById("cmbOffice_code").value;	
		 var txtCrea_date=document.getElementById("txtPayment_date").value;	
		 var cmbDocType=document.getElementById("cmbDocType").value;	       
                 
                 
		 if(param=="load_voucher_no")
			 {
			 if(cmbDocType=="")
				 {
				 alert("Select Doc Type in the Filed");
				 document.getElementById("cmbDocType").focus();
				 }else{
			 if(cmbDocType=="R")
				 {
				 url="../../../../../ManyEmployee_One_units?command=load_voucher_no&txtUnitId="+cmbAcc_UnitCode+"&cmbOffice_code="+cmbOffice_code+"&txtCrea_date="+txtCrea_date+"&cmbDocType="+"R";
				 }else if(cmbDocType=="P")
					 {
					 url="../../../../../ManyEmployee_One_units?command=load_voucher_no&txtUnitId="+cmbAcc_UnitCode+"&cmbOffice_code="+cmbOffice_code+"&txtCrea_date="+txtCrea_date+"&cmbDocType="+"P";
					 }
				 else if(cmbDocType=="J")
				 {
					 url="../../../../../ManyEmployee_One_units?command=load_voucher_no&txtUnitId="+cmbAcc_UnitCode+"&cmbOffice_code="+cmbOffice_code+"&txtCrea_date="+txtCrea_date+"&cmbDocType="+"J";
				 }
			 
		
                        req=getTransport();
	     req.open("GET",url,true);        
	     req.onreadystatechange=function()
	     {        	  
	            TDA_Raised_ServletResponse(req);
	     }   
	     req.send(null);  
				 }
                                 
			 	
			 }
		 else
		 {              
//                  var cmbMas_SL_type=document.getElementById("cmbMas_SL_type").value;	
//		 var cmbMas_SL_Code=document.getElementById("cmbMas_SL_Code").value;	
                 
			 	var voucher_no1=document.getElementById("txtVoucher_No").value;
                                alert(voucher_no1);
//                                var voucher_no=voucher_no1.split("-");
//                                alert(":::vou"+voucher_no[0]);
                                if(cmbDocType=="")
				 {
				 alert("Select Doc Type in the Filed");
				 document.getElementById("cmbDocType").focus();
				 }else{
			 if(cmbDocType=="R")
				 {
                                 url="../../../../../ManyEmployee_One_units?command=load_Voucher_Details&txtUnitId="+cmbAcc_UnitCode+"&cmbOffice_code="+cmbOffice_code+"&txtCrea_date="+txtCrea_date+"&voucher_no="+voucher_no1+"&cmbDocType="+"R";
                                 
                 }
			 else if(cmbDocType=="P")
					 {
                                         url="../../../../../ManyEmployee_One_units?command=load_Voucher_Details&txtUnitId="+cmbAcc_UnitCode+"&cmbOffice_code="+cmbOffice_code+"&txtCrea_date="+txtCrea_date+"&voucher_no="+voucher_no1+"&cmbDocType="+"P";                               
                                          }
				 else if(cmbDocType=="J")
				 {
			 	url="../../../../../ManyEmployee_One_units?command=load_Voucher_Details&txtUnitId="+cmbAcc_UnitCode+"&cmbOffice_code="+cmbOffice_code+"&txtCrea_date="+txtCrea_date+"&voucher_no="+voucher_no1+"&cmbDocType="+"J";                               
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
                                
		 }	 	
		 
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
                        if(baseResponse.getElementsByTagName("flag")[0] == undefined)  
                        {
							 var flag="";
						}       
						else
						{
							  var flag=baseResponse.getElementsByTagName("flag")[0].firstChild.nodeValue;
						}                       
                       // var flag=baseResponse.getElementsByTagName("flag")[0].firstChild.nodeValue;
                       
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
                                                  
	                    	   		   var voucher_no="",slno="";
	                                   for(var k=0;k<vno.length;k++)
	                                   {
                                                    voucher_no=baseResponse.getElementsByTagName("VOUCHER_NO")[k].firstChild.nodeValue; 
                                                    slno=baseResponse.getElementsByTagName("slno")[k].firstChild.nodeValue;
	                                            var option=document.createElement("OPTION");
	                                            option.text=voucher_no+"-"+slno;
	                                            option.value=voucher_no+"-"+slno;
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
                    	   /** Delete Existing Values from Grid */
               			var tbody = document.getElementById("grid_body");
               			var t = 0;
               			for (t = tbody.rows.length - 1; t >= 0; t--) {
               				tbody.deleteRow(0);
               			}
	                    	   if(flag=="success")
	                	   	   {	         
	                	   		   	   var head_code=baseResponse.getElementsByTagName("account_head_code");   	                	   		   	  
	                	   		   	   for(var k=0;k<head_code.length;k++)
	                                   {
	                	   		   		   	    item1[k]=baseResponse.getElementsByTagName("account_head_code")[k].firstChild.nodeValue;
	                	   		   		   	    	                	   		   		   	    
	                	   		   		   	    item2[k]=baseResponse.getElementsByTagName("cr_dr_indicator")[k].firstChild.nodeValue;
	                	   		   		   	    item3[k]=baseResponse.getElementsByTagName("sub_ledger_type_code")[k].firstChild.nodeValue;
	                	   		   		   	    item4[k]=baseResponse.getElementsByTagName("sub_ledger_type_desc")[k].firstChild.nodeValue;
	                	   		   		   	    item5[k]=baseResponse.getElementsByTagName("sub_ledger_code")[k].firstChild.nodeValue;
	                	   		   		   	    item6[k]=baseResponse.getElementsByTagName("cname")[k].firstChild.nodeValue;
	                	   		   		   	    item18[k]=baseResponse.getElementsByTagName("offId")[k].firstChild.nodeValue;
		              	   		   		   	    item7[k]=baseResponse.getElementsByTagName("account_head_desc")[k].firstChild.nodeValue;
		              	   		   		   	  
				              	   		   		if(item7[k]=="null")
				              	   		   			item7[k]="";
				              	   		   		item8[k]=baseResponse.getElementsByTagName("amount")[k].firstChild.nodeValue;
		              	   		   		   	//    item9[k]=baseResponse.getElementsByTagName("particulars")[k].firstChild.nodeValue;
				              	   		   		if(baseResponse.getElementsByTagName("particulars")[k].firstChild == null)
				              	   		   		{
													item9[k]="";	
												}
				              	   		   			else
				              	   		   			{
														item9[k]=baseResponse.getElementsByTagName("particulars")[k].firstChild.nodeValue;
													}
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
        document.getElementById("sl_offCode").value=item18[i];
        document.getElementById("txtTotalAmt").value=item8[i];
        document.getElementById("cmbMas_SL_type").options[document.getElementById("cmbMas_SL_type").selectedIndex].value=item3[i];
        document.getElementById("cmbMas_SL_type").options[document.getElementById("cmbMas_SL_type").selectedIndex].text=item4[i];
        
        document.getElementById("cmbMas_SL_Code").options[document.getElementById("cmbMas_SL_Code").selectedIndex].value=item5[i];
        document.getElementById("cmbMas_SL_Code").options[document.getElementById("cmbMas_SL_Code").selectedIndex].text=item6[i];
        
        document.getElementById("txtAcc_HeadCode").value=item1[i];
        document.getElementById("txtAcc_HeadDesc").value=item7[i];
       
        if(item1[i]=="901001")
        {
       document.TCA_POST.rad_sub_CR_DR[0].checked=true;
        }
        else  if(item1[i]=="900108")
        {
        document.TCA_POST.rad_sub_CR_DR[1].checked=true;
        }
      //  document.getElementById("txtTotalAmt").value=item8[i];
      
      document.getElementById("cmbSL_type").options[document.getElementById("cmbSL_type").selectedIndex].value=item3[i];
        document.getElementById("cmbSL_type").options[document.getElementById("cmbSL_type").selectedIndex].text=item4[i];
        
        document.getElementById("cmbSL_Code").options[document.getElementById("cmbSL_Code").selectedIndex].value=item5[i];
        document.getElementById("cmbSL_Code").options[document.getElementById("cmbSL_Code").selectedIndex].text=item6[i];
      
        //document.getElementById("cmbSL_type").value=item8[i];
      //  document.getElementById("cmbSL_Code").value=item8[i];
        document.getElementById("txtsub_Amount").value=item8[i];
        document.getElementById("txtsub_Amount").disabled=true;
        document.getElementById("cmbSL_Code").disabled=true;
        document.getElementById("cmbSL_type").disabled=true;
        document.getElementById("txtAcc_HeadCode").disabled=true;
        document.getElementById("txtAcc_HeadDesc").disabled=true;
        document.getElementById("cmbMas_SL_Code").disabled=true;
        document.getElementById("cmbMas_SL_type").disabled=true;
        
                         
                         }   
        }

///////////////////////////////////////////    TB_checking and Calender control return value handling
function call_mainJSP_script(fromcal_dateCtrl,blr_flag)     // ///////Calender
															// control return
															// value handling
{
        if(blr_flag==1)         // which is used to find the receipt or voucher
								// or payment (creation) date calling field,if
								// so check trial balance
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
                                //check_TB(req,fromcal_dateCtrl);
                        }   
                        req.send(null);
                }
        }
}

//function call_date(dateCtrl,blr_flag)                        // TB_checking
// { if(blr_flag==1)
// {
// if(checkdt(dateCtrl))
// {
// var cmbAcc_UnitCode=document.getElementById("cmbAcc_UnitCode").value;
// var cmbOffice_code=document.getElementById("cmbOffice_code").value;
// var TB_date=dateCtrl.value;
// if(dateCtrl.value.length!=0)
// {
// var
// url="../../../../../Receipt_SL.view?Command=check_TB&TB_date="+TB_date+"&cmbAcc_UnitCode="+cmbAcc_UnitCode+"&cmbOffice_code="+cmbOffice_code;
// var req=getTransport();
// req.open("GET",url,true);
// req.onreadystatechange=function()
// {
// check_TB(req,dateCtrl);
// }
// req.send(null);
// }
// }
// }
//    
// }



// function check_TB(req,dateCtrl)
// {
// if(req.readyState==4)
// {
// if(req.status==200)
// {
// var baseResponse=req.responseXML.getElementsByTagName("response")[0];
// var tagcommand=baseResponse.getElementsByTagName("command")[0];
// var Command=tagcommand.firstChild.nodeValue;
// var flag=baseResponse.getElementsByTagName("flag")[0].firstChild.nodeValue;
// if(flag=="failure")
// {
// dateCtrl.value="";
// alert("Trial Balance Closed");//return false;//
// dateCtrl.focus();
// }
// else if(flag=="finyear")
// {
// // This statement get executed when financial year ( Cash Book Control
// Details ) has not found for the given date
// dateCtrl.value="";
// alert("Cash Book Control Not Found ");//return false;//
// dateCtrl.focus();
// //document.getElementById("txtVoucher_No").value="";
// }
//                       
// }
// }
// }

// /////////////////////////////////////////// clearall() by User
// /////////////////////////////////////////////////////

function clrForm(param)
{
		document.getElementById("txtVoucher_No").value="";        
		document.getElementById("cmbReason").value="";        
       // document.getElementById("txtUnitId").value="";
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
            document.getElementById("cmbSL_type").value="";
	    document.getElementById("cmbSL_Code").value="";
	    document.getElementById("txtAcc_HeadCode").value="";
            document.getElementById("txtAcc_HeadDesc").value="";
	//    document.getElementById("txtParticular1").value="";

	    document.getElementById("txtsub_Amount").value="";
		document.getElementById("letterNo").value="";
		document.getElementById("letterpageNo").value="";
		document.getElementById("letterDate").value="";
        document.getElementById("txtParticular1").value="";
        
        
        document.getElementById("txtsub_Amount").disabled=false;
        document.getElementById("cmbSL_Code").disabled=false;
        document.getElementById("cmbSL_type").disabled=false;
        document.getElementById("txtAcc_HeadCode").disabled=false;
        document.getElementById("txtAcc_HeadDesc").disabled=false;
        document.getElementById("cmbMas_SL_Code").disabled=false;
        document.getElementById("cmbMas_SL_type").disabled=false;
		
//        var tbody=document.getElementById("grid_body");
//        var t=0;
//        for(t=tbody.rows.length-1;t>=0;t--)
//        {
//                tbody.deleteRow(0);
//        }
}
//////////////////////////////////////////////////////////////////////////////////////////////////////

function loadTable(scod)
{
	
	//alert("enter into the load");
        com_id=scod;                                    // to identify in
        											// UPDATE_GRID ,which
       // if(rcells.item(1).firstChild.value)											// row loaded
     
        var r=document.getElementById(scod);
        var rcells=r.cells;
      //  alert(rcells.item(1).firstChild.value);	
        if(rcells.item(1).firstChild.value==901001)
        {
         document.getElementById("txtsub_Amount").value=item8[i];
        document.getElementById("txtsub_Amount").disabled=true;
        document.getElementById("cmbSL_Code").disabled=true;
        document.getElementById("cmbSL_type").disabled=true;
        document.getElementById("txtAcc_HeadCode").disabled=true;
        document.getElementById("txtAcc_HeadDesc").disabled=true;
        document.getElementById("cmbMas_SL_Code").disabled=true;
        document.getElementById("cmbMas_SL_type").disabled=true;
        }
        try {document.getElementById("txtAcc_HeadCode").value=rcells.item(1).firstChild.value;}catch(e){}
         doFunction('checkCode','null');
         try{com_cmbSL_type=rcells.item(3).firstChild.value; } catch(e){com_cmbSL_type="";}
          try{com_cmbSL_Code=rcells.item(4).firstChild.value;} catch(e){com_cmbSL_Code="";} 
        //  alert(com_cmbSL_type+":::::code"+com_cmbSL_Code);
        if(com_cmbSL_type==5)
        {
              document.getElementById("txtOfficeID_trs").value=com_cmbSL_Code;
              job_flag=false;              
        }
        
        if(com_cmbSL_type==7)
        {
              document.getElementById("txtEmpID_trs").value=com_cmbSL_Code;
              emp_flag=false;             
        }
       //  doFunction('checkCode','null');
          doFunction('Load_SL_Code',com_cmbSL_type);
              
           
        
        
        if(rcells.item(2).firstChild.value=="CR")
        	document.TCA_POST.rad_sub_CR_DR[0].checked=true;
        else if(rcells.item(2).firstChild.value=="DR")
        	document.TCA_POST.rad_sub_CR_DR[1].checked=true;
        
       
        
        try{document.getElementById("txtsub_Amount").value=rcells.item(5).firstChild.value;}catch(e){}
        try{document.getElementById("txtParticular1").value=rcells.item(9).firstChild.value;}catch(e){}
        try{document.getElementById("letterNo").value=rcells.item(6).firstChild.value;}catch(e){}
       try{document.getElementById("letterpageNo").value=rcells.item(7).firstChild.value;}catch(e){}
       try{document.getElementById("letterDate").value=rcells.item(8).firstChild.value;}catch(e){}

      
       document.getElementById("letterDate").value
       document.TCA_POST.cmdupdate.style.display='block';
	   document.TCA_POST.cmddelete.disabled=false;
	   document.TCA_POST.cmdadd.style.display='none';
         // alert(com_cmbSL_Code);
            document.getElementById("cmbSL_Code").value=com_cmbSL_Code;
	   setTimeout('document.getElementById("cmbSL_type").value=com_cmbSL_type',900); 
           setTimeout('document.getElementById("cmbSL_Code").value=com_cmbSL_Code',900);
	  
}

function load_grid(cmd)
{

        if(document.getElementById("txtAcc_HeadCode").value.length==0)
        {
                alert("Enter A/c Head Code");
                return false;
        }                
             
        if(document.getElementById("txtsub_Amount").value.length==0)
        {
                alert("Enter the Amount in Details Part ");
                return false;    
        }
    
        var tbody=document.getElementById("grid_body");                            
        var t=0;            
        var items=new Array();
        
        items[0]=document.getElementById("txtAcc_HeadCode").value;
        items[1]=document.getElementById("txtAcc_HeadDesc").value;   
        
        if(document.forms[0].rad_sub_CR_DR[0].checked==true)
        	items[2]=document.forms[0].rad_sub_CR_DR[0].value;
        else if(document.forms[0].rad_sub_CR_DR[1].checked==true)
        	items[2]=document.forms[0].rad_sub_CR_DR[1].value;  
        items[3]=document.forms[0].cmbSL_type.value;
        if(items[3]==0)
        {
        	items[4]="--";
        }
        else
        items[4]=document.getElementById("cmbSL_type").options[document.getElementById("cmbSL_type").selectedIndex].text;         
        items[5]=document.forms[0].cmbSL_Code.value;
        if(items[5]==0)
        {
        	items[6]="--";
        }
        else
        items[6]=document.getElementById("cmbSL_Code").options[document.getElementById("cmbSL_Code").selectedIndex].text; 
       
       
        items[7]=document.getElementById("txtsub_Amount").value;
        items[8]=document.getElementById("txtParticular1").value;
        items[9]=document.getElementById("letterNo").value;
        items[10]=document.getElementById("letterDate").value;
        items[11]=document.getElementById("letterpageNo").value;
       
       
       
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
                var acc_no=document.createElement("input");
                acc_no.type="hidden";
                acc_no.name="acc_no";
                acc_no.value=items[2];
                cell2.appendChild(acc_no);
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
                var let_no=document.createElement("input");
                let_no.type="hidden";
                let_no.name="letterno";
                let_no.value=items[9];
                // alert(items[8]);
                cell2.appendChild(let_no);
                var currentText=document.createTextNode(items[9]);                         
                cell2.appendChild(currentText);       
                mycurrent_row.appendChild(cell2);
                
                
                
                
                cell2=document.createElement("TD"); 
                var par_code=document.createElement("input");
                par_code.type="hidden";
                par_code.name="pageno";
                par_code.value=items[11];
               // alert(items[9]);
                cell2.appendChild(par_code);
                var currentText=document.createTextNode(items[11]);                         
                cell2.appendChild(currentText);       
                mycurrent_row.appendChild(cell2);
                
                
                
                
                cell2=document.createElement("TD"); 
                var let_date=document.createElement("input");
                let_date.type="hidden";
                let_date.name="letterdate";
                let_date.value=items[10];
              // alert(items[10]);
                cell2.appendChild(let_date);
                var currentText=document.createTextNode(items[10]);                         
                cell2.appendChild(currentText);       
                mycurrent_row.appendChild(cell2);
                
                
                
                
                
                cell2=document.createElement("TD"); 
                var par_code=document.createElement("input");
                par_code.type="hidden";
                par_code.name="remarkss";
                par_code.value=items[8];
                cell2.appendChild(par_code);
                var currentText=document.createTextNode(items[8]);                         
                cell2.appendChild(currentText);       
                mycurrent_row.appendChild(cell2);
                
                
                
                
                tbody.appendChild(mycurrent_row);
               call_clr();
                seq=seq+1;
              
        }
    
}

function up()
{

	var tbody=document.getElementById("grid_body");                            
    var t=0;            
    var items=new Array();
    
    items[0]=document.getElementById("txtAcc_HeadCode").value;
    items[1]=document.getElementById("txtAcc_HeadDesc").value;   
    
    if(document.forms[0].rad_sub_CR_DR[0].checked==true)
    	items[2]=document.forms[0].rad_sub_CR_DR[0].value;
    else if(document.forms[0].rad_sub_CR_DR[1].checked==true)
    	items[2]=document.forms[0].rad_sub_CR_DR[1].value;  
    items[3]=document.forms[0].cmbSL_type.value;
   
    if(items[3]==0)
    {
    	items[4]="--";
    }
    else
    {
    	items[4]=document.getElementById("cmbSL_type").options[document.getElementById("cmbSL_type").selectedIndex].text;         
    }
   
    items[5]=document.forms[0].cmbSL_Code.value;
    if(items[5]==0)
    {
    	items[6]="--";
    }
    else
    {
    	items[6]=document.getElementById("cmbSL_Code").options[document.getElementById("cmbSL_Code").selectedIndex].text; 
    }
 
   
  
    items[7]=document.getElementById("txtsub_Amount").value;
 
    items[8]=document.getElementById("txtParticular1").value;
  
    items[9]=document.getElementById("letterNo").value;
  
    items[10]=document.getElementById("letterDate").value;
    items[11]=document.getElementById("letterpageNo").value;
    var r=document.getElementById(com_id);
    var rcells=r.cells;
    try{rcells.item(1).firstChild.value=items[0];}catch(e){}   //firstchild for value and last for desc
    try{rcells.item(1).lastChild.nodeValue=items[0]+"-"+items[1];}catch(e){}
 
    try{rcells.item(2).firstChild.value=items[2];}catch(e){}
    try{rcells.item(2).lastChild.nodeValue=items[2];}catch(e){}
   
    try{rcells.item(3).firstChild.value=items[3];}catch(e){}
    try{rcells.item(3).lastChild.nodeValue=items[4];}catch(e){}

    try{rcells.item(4).firstChild.value=items[5];}catch(e){}
    try{rcells.item(4).lastChild.nodeValue=items[6];}catch(e){}

  
    try{rcells.item(5).firstChild.value=items[7];}catch(e){}
    try{rcells.item(5).lastChild.nodeValue=items[7];}catch(e){}

    try{rcells.item(6).firstChild.value=items[9];}catch(e){}
    try{rcells.item(6).lastChild.nodeValue=items[9];}catch(e){}
    
    try{rcells.item(7).firstChild.value=items[11];}catch(e){}
    try{rcells.item(7).lastChild.nodeValue=items[11];}catch(e){}
    
    try{rcells.item(8).firstChild.value=items[10];}catch(e){}  //rcells.item(6).childNodes.item(1).value
    try{rcells.item(8).lastChild.nodeValue=items[10];}catch(e){} 
    
    try{rcells.item(9).firstChild.value=items[8];}catch(e){}
    try{rcells.item(9).lastChild.nodeValue=items[8];}catch(e){} 
    
     document.forms[0].cmdupdate.style.display='none';        
     document.forms[0].cmdadd.style.display='block';                   
    alert("Record Updated successfully");
    call_clr();
	
} 
function delete_GRID()
{
        if(confirm("Do you want to delete ?"))
        {
	        var tbody=document.getElementById("mytable");
	        var r=document.getElementById(com_id);
	        var ri=r.rowIndex;
	        tbody.deleteRow(ri);
	      // / clearall();
        }
}
function setAccHdCode()
{

document.getElementById("txtVoucher_No").value="";


	var cmbDocType = document.getElementById("cmbDocType").value;
	if(cmbDocType=="P")
		{
		document.getElementById("txtDebitHead").value="900108";
		}else{
			document.getElementById("txtDebitHead").value="901001";
		}	
}
// added on 25/07/2011
function call_date(dateCtrl)                        // TB_checking 
{      
//	alert("call_date");
        //document.TDA_TCA.isAccept[0].checked=false;
    //document.TDA_TCA.isAccept[1].checked=false;
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
                                  document.getElementById("txtPayment_date").value="";
                                  alert("Trial Balance Closed");//return false;//
                                  dateCtrl.focus();                                            
                         }
                         else if(flag=="finyear")
                         {
                                  // This statement get executed when financial year ( Cash Book Control Details ) has not found for the given date 
                                  dateCtrl.value="";
                                  document.getElementById("txtCrea_date").value="";
                                  alert("Cash Book Control Not Found ");//return false;//
                                  dateCtrl.focus();
                                  //document.getElementById("txtVoucher_No").value="";     
                         }
                }
         }
}