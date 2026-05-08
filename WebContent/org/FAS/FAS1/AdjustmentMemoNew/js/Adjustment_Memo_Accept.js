var com_id;

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

function sixdigit()
{
	    if( document.getElementById("txtAcc_HeadCode").value!=0)
	    {
	        if((document.getElementById("txtAcc_HeadCode").value).length<6)
	        {
		        alert("Account Head Code Shouldn't be less than 6 digit number");
		        document.getElementById("txtAcc_HeadCode").focus();
		        return false;
	        }
	    }
}


function filter_real(evt,item,n,pre)
{
        var charCode = (evt.which) ? evt.which : event.keyCode;
// allow "." for one time 
        if(charCode==46){
                        //	alert("Position of . "+item.value.indexOf("."));
                        if(item.value.indexOf(".")<0)	return (item.value.length>0)?true:false;
                        else return false;
        }
        if (!(charCode > 31 && (charCode < 48 || charCode > 57))){
                // to avoid over flow
                        if(item.value.indexOf(".")<0){
        //			alert("Length without . ="+item.value.length);
                                return (item.value.length<n)?true:false;
                        }
                // dont allow more than 2 precision no's after the point
                        if(item.value.indexOf(".")>0){
                        //	alert("precision count ="+item.value.split(".")[1].length);
                                if(item.value.split(".")[1].length<pre) return true;
                                else return false;
                        }
                        return false;
        }else{
                        return false;
        }
}


function check_leng(remarks)
{	 
	    if((remarks.length)>=190)
	    {
	    	alert("Please Enter Paticulars below 200 characters");
	    }	 
}


function cr_dr()
{
	var acc_code=document.getElementById("txtAcc_HeadCode").value;
    var advice_Type=document.Adjustment_Memo_Form1.advice_type.value;
	
    
    //alert("advice_Type========>"+advice_Type);
    //alert("acc_code========>"+acc_code);
	
	if((advice_Type=="DR")  && (document.Adjustment_Memo_Form1.rad_sub_CR_DR.value=="CR") && (acc_code=="900202"))
	 {
	 //alert("welcome inside 1st part");
	 
	 return true;
	 }
   else if((advice_Type=="DR") && (document.Adjustment_Memo_Form1.rad_sub_CR_DR.value=="CR") && ( acc_code!='900202'))
	 {
	 
	// alert("welcome inside else part");
	 
	 alert("A/c Head 900202 Should be Added as Credit Head");
	 document.getElementById("txtAcc_HeadCode").value="";
	 document.getElementById("txtAcc_HeadDesc").value="";
	 return false;
	
	 }
	
	if((advice_Type=="CR")  && (document.Adjustment_Memo_Form1.rad_sub_CR_DR.value=="DR") && (acc_code=="610102"))
	 {
	// alert("welcome inside 1st part");
	 
	 return true;
	 }
  else if((advice_Type=="CR") && (document.Adjustment_Memo_Form1.rad_sub_CR_DR.value=="DR") && ( acc_code!='610102'))
	 {
	 
	// alert("welcome inside else part");
	 
	 alert("A/c Head 610102 Should be Added as Debit Head");
	 document.getElementById("txtAcc_HeadCode").value="";
	 document.getElementById("txtAcc_HeadDesc").value="";
	 return false;
	
	 }
	
	
}

function clearall()
{
     document.getElementById("txtAcc_HeadCode").disabled=false;
     
     //document.Adjustment_Memo_Form1.rad_sub_CR_DR[1].checked=true;
     var acc_code=document.getElementById("txtAcc_HeadCode").value;
     var advice_Type=document.Adjustment_Memo_Form1.advice_type.value;
     
     
     document.getElementById("cmbSL_type").length=0; 
     document.getElementById("txtEmpID_trs").value="";
     document.getElementById("txtOfficeID_trs").value="";
   //  document.getElementById("txtsub_Amount").value="";
     document.getElementById("txtParticular").value="";   
     document.getElementById("txtAcc_HeadCode").value="";
     document.getElementById("txtAcc_HeadDesc").value="";
     document.getElementById("cmbSL_Code").value="";
     
     
     
     if(advice_Type=="CR")
    	 {
    	 document.Adjustment_Memo_Form1.rad_sub_CR_DR[1].disabled=false;
    	 document.Adjustment_Memo_Form1.rad_sub_CR_DR[0].disabled=false;
    	 //document.Adjustment_Memo_Form1.rad_sub_CR_DR[0].disabled=true;
         document.Adjustment_Memo_Form1.rad_sub_CR_DR[0].checked=true;
         document.Adjustment_Memo_Form1.advice_type[0].checked=true;
        //document.Adjustment_Memo_Form1.rad_sub_CR_DR[0].disabled=false;
         
        
         
    	 }
//     if(document.Adjustment_Memo_Form1.advice_type[1].checked=true)
     else 
	 {
	 
    	 
    	 
     document.Adjustment_Memo_Form1.rad_sub_CR_DR[0].disabled=false;
     document.Adjustment_Memo_Form1.rad_sub_CR_DR[1].disabled=false;
    // document.Adjustment_Memo_Form1.rad_sub_CR_DR[1].disabled=true;
     document.Adjustment_Memo_Form1.rad_sub_CR_DR[1].checked=true;
     //document.Adjustment_Memo_Form1.rad_sub_CR_DR[1].disabled=false;
     document.Adjustment_Memo_Form1.advice_type[1].checked=true;
     
     
     
	 }
     
     
     
     //var cmbSL_Code1=document.getElementById("cmbSL_Code"); 
    // clear_Combo(cmbSL_Code1);   
	 document.Adjustment_Memo_Form1.cmdadd.style.display='block';
	 document.Adjustment_Memo_Form1.cmdupdate.style.display='none';
	 document.Adjustment_Memo_Form1.cmddelete.disabled=true;
	
}

function clrForm()
{
	    if(window.confirm("Do you want to clear ALL fields ?"))
		{
		   		call_clr();
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

function checkNull()
{
    /*by NandaKumar on 02Jul20*/
	//alert("enter");
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
        if(document.getElementById("txtDate").value=="")
        {
            alert("Enter the Date");           
            return false;    
        } 
        if(document.getElementById("txtAmount").value=="")
        {
            alert("Enter the Total Amount in General part");           
            return false;    
        }
        
        else if(document.getElementById("txtDate").value!="") {

            
            cmbAcc_UnitCode=document.getElementById("cmbAcc_UnitCode").value;
            cmbOffice_code=document.getElementById("cmbOffice_code").value;
            var TB_date=document.getElementById("txtDate").value;     
            if(document.getElementById("txtDate").value.length!=0)
            {
	                 var url="../../../../../Receipt_SL.view?Command=check_TB&TB_date="+TB_date+"&cmbAcc_UnitCode="+cmbAcc_UnitCode+"&cmbOffice_code="+cmbOffice_code;
	                 var req=getTransport();
	                 req.open("GET",url,true); 
	                 req.onreadystatechange=function()
	                 {
	                   check_TB(req,fromcal_dateCtrl);
	                 }  ; 
	                 req.send(null);
            }
    
        }         
        if(document.getElementById("cmbAdviceNO").selectedIndex==0)
        {
            alert("Enter the Advice No in General");          
            return false;    
        }
        if(tbody.rows.length==0)
        {
            alert("Enter the Details Part");         
            return false; 
        }
      
        else
        {
        	var credit_amount=0;
        	var debit_amount=0;
        	var tot_amount=0;
        	var tot_amount_gen=0;
        	 rows=tbody.getElementsByTagName("TR");       
        	// alert(rows);
             for(i=0;i<rows.length;i++)
             {
            	 //alert(rows.length);
                         var cells=rows[i].cells;  
                         tot_amount_gen=document.getElementById("txtAmount").value;
                         tot_amount=tot_amount+parseFloat(cells.item(5).lastChild.nodeValue);
                         /*@NK include on 11jul20*/
                         if(document.Adjustment_Memo_Form1.advice_type[0].checked==true)
                          {
                        	 if(cells.item(2).lastChild.nodeValue=='DR')
                        	 {
                        		 debit_amount=debit_amount+parseFloat(cells.item(5).lastChild.nodeValue);
                            	 
                            	 var drhead=cells.item(1).lastChild.nodeValue;
                            	 var dr_head=drhead.split("-");
                            	 
                            	
                            	 if(dr_head[0]!="610102")
                         		 {
                         		 alert("A/c Head 610102 Should be Added as Debit Head");
                         		 return false;
                        		 }
                            }
                        	 if(cells.item(2).lastChild.nodeValue=='CR')
                             {
                            	  credit_amount=credit_amount+parseFloat(cells.item(5).lastChild.nodeValue);
                            	  
                             }
                          }
                         
                         if(document.Adjustment_Memo_Form1.advice_type[1].checked==true)
                         {
                        	 
                        	 if(cells.item(2).lastChild.nodeValue=='CR')
                             {
                            	  credit_amount=credit_amount+parseFloat(cells.item(5).lastChild.nodeValue);
                            	  
                             	 var crhead=cells.item(1).lastChild.nodeValue;
                             	 var cr_head=crhead.split("-");
                             	 if(cr_head[0]!="900202")
                          		 {
                          		 alert("A/c Head 900202 Should be Added as Credit Head");
                          		 return false;
                          		 }
                             }
                       	 if(cells.item(2).lastChild.nodeValue=='DR')
                       	  {
                       		 debit_amount=debit_amount+parseFloat(cells.item(5).lastChild.nodeValue);
                           	
                          }
                       	 
                         }
                         /*@NK include on 11jul20*/
                           
        }
             
                 if(parseFloat(credit_amount)==0) 
             	{
             	alert("Credit amount must be specified!...");
             	return false;
             	}
             	if(parseFloat(debit_amount)==0)
             	{
             		alert("Debit amount must be specified!...");
             		return false;
             	}
            
             
                 
                 
             if(credit_amount!=debit_amount)
             {
            	 alert("Amount doesn't Tally.. Difference " +(parseFloat(credit_amount)-parseFloat(debit_amount))); 
            	 return false; 
             }
             if(credit_amount!=tot_amount_gen)
             {
            	 alert("Total Amount in General doesn't Tally with Details. Difference " +(parseFloat(credit_amount)-parseFloat(tot_amount_gen))); 
            	 /*alert("Debit Amount and Credit Amount Should Be Equal");*/
            	 return false; 
             }
             
             /*by NandaKumar on 02Jul20*/
             
             
             
        }
       
        
        
               
} 

function ADD_GRID()
{
  //  alert("Inside Add grid--->"+document.Adjustment_Memo_Form1.advice_type[0].value);    
	
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
          //  alert("You are not allow to use this Account Head Code "+acc);
            alert("This A/C code can not be used here ");
            document.getElementById("txtAcc_HeadCode").value="";
            document.getElementById("txtAcc_HeadDesc").value="";
            return false;
          }  
        }
        
        
       if(document.getElementById("txtAcc_HeadDesc").value=="")
       {
            alert("Please Wait Account Head is Loading .......................");            
            return false;        
       }       
      
     if ( isMan.account_head_status ) 
       {        
        if(document.getElementById("cmbSL_type").value=="")
        {
            alert("Select The Sub Ledger Type")       ;
            document.getElementById("cmbSL_type").focus();
            return false;        
        }
        
        if(document.getElementById("cmbSL_Code").value=="")
        {
            alert("Select The Sub Ledger Code")       ;
            document.getElementById("cmbSL_Code").focus();
            return false;        
        }     
        
       } else
       {
            if(document.getElementById("cmbSL_type").length>1 && document.getElementById("cmbSL_type").value=="")
            {
                if(window.confirm("You have not selected Sub-Ledger Type \n Do you want to select it,click 'OK'?"))
                    {
                        if(document.getElementById("cmbSL_type").value=="")
                            {
                                alert("Select a Sub-Ledger Type");
                                return false;
                            } 
                    }
                    else
                    {
                     
                    }          
            }
            if(document.getElementById("cmbSL_type").value!="")
            {
                if(document.getElementById("cmbSL_Code").value=="")
                    {
                        alert("Select The Sub Ledger Code")       ;
                        document.getElementById("cmbSL_Code").focus();
                        return false;
                    }
            }       
        }
          
        if(document.getElementById("txtsub_Amount").value.length==0)
        {
            alert("Enter the Amount ");
            //document.getElementById("txtAmount").focus();
            return false;    
        }
        var tbody=document.getElementById("grid_body");
                             
        var t=0;
        var exist=document.getElementById("txtAcc_HeadCode").value;
     
        var items=new Array();
        items[0]=document.getElementById("txtAcc_HeadCode").value;
        items[1]=document.getElementById("txtAcc_HeadDesc").value;
        if(document.Adjustment_Memo_Form1.rad_sub_CR_DR[0].checked==true)
          items[2]=document.Adjustment_Memo_Form1.rad_sub_CR_DR[0].value;
        else if(document.Adjustment_Memo_Form1.rad_sub_CR_DR[1].checked==true)
          items[2]=document.Adjustment_Memo_Form1.rad_sub_CR_DR[1].value;
        
        items[3]=document.getElementById("cmbSL_type").value;
        if(document.getElementById("cmbSL_type").value=="")
        {
        //items[4]="Not Available";
        items[4]="";//document.getElementById("cmbSL_type").options[document.getElementById("cmbSL_type").selectedIndex].text; 
        }
        else
        items[4]=document.getElementById("cmbSL_type").options[document.getElementById("cmbSL_type").selectedIndex].text; 
        
        items[5]=document.getElementById("cmbSL_Code").value;
        if(document.getElementById("cmbSL_Code").value=="")
        {
        items[6]="";//"Not Available";
        }
        else
        items[6]=document.getElementById("cmbSL_Code").options[document.getElementById("cmbSL_Code").selectedIndex].text; 
        
        //items[7]=document.getElementById("txtsub_Recei_from").value;
        items[7]=document.getElementById("txtsub_Amount").value;
        items[8]=document.getElementById("txtParticular").value;
        
        tbody=document.getElementById("grid_body");
        var mycurrent_row=document.createElement("TR");
        seq=seq+1;
        mycurrent_row.id=seq;
        //alert("row ID"+mycurrent_row.id);
        var cell=document.createElement("TD");
        var anc=document.createElement("A");
        var url="javascript:loadTablevalues('"+mycurrent_row.id+"')";
        anc.href=url;
        var txtedit=document.createTextNode("EDIT");
        anc.appendChild(txtedit);
        cell.appendChild(anc);
        mycurrent_row.appendChild(cell);
        var i=0;
        var cell2;
        
       
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
                  particular.name="particular";
                  particular.value=items[8];
                  cell2.appendChild(particular);
                  var currentText=document.createTextNode(items[8]);
                cell2.appendChild(currentText);
                mycurrent_row.appendChild(cell2);

        tbody.appendChild(mycurrent_row);
        
     //   alert("End Add grid--->"+document.Adjustment_Memo_Form1.advice_type[0].value);    
        
         clearall();
}

function update_GRID()
{      
        if(document.getElementById("txtAcc_HeadCode").value.length==0)
        {
        alert("Enter A/c Head Code");
        return false;
        }
        if(document.getElementById("cmbSL_type").length>1 && document.getElementById("cmbSL_type").value=="")
        {
          if(window.confirm("You have not selected Sub-Ledger Type \n Do you want to select it,click 'OK'?"))
          {
             if(document.getElementById("cmbSL_type").value=="")
              {
                alert("Select a Sub-Ledger Type");
                return false;
               } 
          }
          else
          {
             
          }
          
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
            document.getElementById("txtsub_Amount").focus();
            return false;    
        }
        var exist=document.getElementById("txtAcc_HeadCode").value;
        var items=new Array();
       
        items[0]=document.getElementById("txtAcc_HeadCode").value;
        items[1]=document.getElementById("txtAcc_HeadDesc").value;
        if(document.Adjustment_Memo_Form1.rad_sub_CR_DR[0].checked==true)
          items[2]=document.Adjustment_Memo_Form1.rad_sub_CR_DR[0].value;
        else if(document.Adjustment_Memo_Form1.rad_sub_CR_DR[1].checked==true)
          items[2]=document.Adjustment_Memo_Form1.rad_sub_CR_DR[1].value;
        items[3]=document.getElementById("cmbSL_type").value;
        if(document.getElementById("cmbSL_type").value=="")
        {
        //items[4]="Not Available";
        items[4]="";//document.getElementById("cmbSL_type").options[document.getElementById("cmbSL_type").selectedIndex].text; 
        //alert("code"+items[4]+"ff");
        }
        else
        items[4]=document.getElementById("cmbSL_type").options[document.getElementById("cmbSL_type").selectedIndex].text; 
        
        items[5]=document.getElementById("cmbSL_Code").value;
        if(document.getElementById("cmbSL_Code").value=="")
        {
        items[6]="";//"Not Available";
        }
        else
        items[6]=document.getElementById("cmbSL_Code").options[document.getElementById("cmbSL_Code").selectedIndex].text; 
        //items[7]=document.getElementById("txtsub_Recei_from").value;
        items[7]=document.getElementById("txtsub_Amount").value;
        items[8]=document.getElementById("txtParticular").value;
        
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
            
              //  try{rcells.item(5).firstChild.value=items[7];}catch(e){}
               // try{rcells.item(5).lastChild.nodeValue=items[7];}catch(e){}
            
                try{rcells.item(5).firstChild.value=items[7];}catch(e){}
                try{rcells.item(5).lastChild.nodeValue=items[7];}catch(e){}
             
                try{rcells.item(6).firstChild.value=items[8];}catch(e){}
                try{rcells.item(6).lastChild.nodeValue=items[8];}catch(e){}
            
            
        alert("Record Updated");
        clearall();
  }

function loadTablevalues(scod)
{
//	if(scod==1)
//	{
//	   document.getElementById("txtAcc_HeadCode").disabled=true;
//	}
//	else
//	{
//		document.getElementById("txtAcc_HeadCode").disabled=false;
//	}
	
	 com_id=scod;     
     if(com_id>0)
     {
         
    	 document.getElementById("txtAcc_HeadCode").disabled=false;
         
     }
    else
    {
       //   loadtableValues(scod);
          document.getElementById("txtAcc_HeadCode").disabled=true;
         
    }
	
	loadTable(scod);
	
}
function loadTable(scod)
{
	    com_id=scod;                                   // to identify in UPDATE_GRID ,which row loaded 
       // clearall();
        var r=document.getElementById(scod);
        var rcells=r.cells;
        try {document.getElementById("txtAcc_HeadCode").value=rcells.item(1).firstChild.value;}catch(e){}
        try{common_cmbSL_type=rcells.item(3).firstChild.value;} catch(e){common_cmbSL_type="";}
        
        try{common_cmbSL_Code=rcells.item(4).firstChild.value;} catch(e){common_cmbSL_Code="";} 
         if(common_cmbSL_type==5)   // Office
         {
              document.getElementById("txtOfficeID_trs").value=common_cmbSL_Code;
              job_flag=false;
          }
         else if(common_cmbSL_type==7)       // Employee
         {
              document.getElementById("txtEmpID_trs").value=common_cmbSL_Code;
              emp_flag=false;
         }else
        	 {
        	 var accHead=rcells.item(1).firstChild.value;
        		if(accHead=="610102")
        		{/*
        			   document.Adjustment_Memo_Form1.rad_sub_CR_DR[0].disabled=true;
        			   document.Adjustment_Memo_Form1.rad_sub_CR_DR[1].checked=true;
        			 var cmbSL_type=document.getElementById("cmbSL_type");   
        			 cmbSL_type.innerHTML="";
        	         var option=document.createElement("OPTION");
        	         option.text="Accounting Units";
        	         option.value="15";
        	       //  option.setAttribute('selected','selected');
        	         try
        	         {
        	        	 cmbSL_type.add(option);
        	         }catch(errorObject)
        	         {
        	        	 cmbSL_type.add(option,null);
        	         }*/
        		///	var sl_res=sltype_load();
        		
        		//	if(sl_res==true){
        				/*alert(document.getElementById("cmbSL_type").innerHTML+" "+rcells.item(3).firstChild.value);
        			document.getElementById("cmbSL_type").value=15;*/
        			//sltype_load();
        		doFunction('checkCodeNEW','null');
        			/* var cmbSL_type=document.getElementById("cmbSL_type");   
        				// cmbSL_type.innerHTML="";
        			  var option=document.createElement("OPTION");
        			  option.text="Accounting Units";
        			  option.value="15";
        			
        			 	 cmbSL_type.add(option);*/
        			 
        			  //alert(res_ls+" res_ls "+document.getElementById("cmbSL_type").selectedIndex);
        		
        		//	alert('Loading .. ');
        				 var res=    load_acc_unitsNewCP();
               	      if(res==true)
               	    	  {
               	    	
               	    	   document.getElementById("cmbSL_Code").value=rcells.item(4).firstChild.value;
               	    	  }
        		//	}
        			
        	         // load_acc_units();
        	        // document.getElementById("cmbSL_type")
        		}  
        	 }
         
         /*document.getElementById("cmbSL_type").value=common_cmbSL_type; 
         doFunction('Load_SL_Code',common_cmbSL_type);
         if(rcells.item(1).firstChild.value==610101)
         {  var res= doFunctioncp('Load_SL_Code',common_cmbSL_type);
               alert('Loaded');
               if(res==true){
            	
            	   document.getElementById("cmbSL_Code").value=5; 
               }
         }*/
       
       // 
         if(rcells.item(2).firstChild.value=="CR"){
        	 
         document.Adjustment_Memo_Form1.rad_sub_CR_DR[0].checked=true;
        document.Adjustment_Memo_Form1.rad_sub_CR_DR[1].disabled=true;
         }
         else if(rcells.item(2).firstChild.value=="DR"){
         document.Adjustment_Memo_Form1.rad_sub_CR_DR[1].checked=true;
         document.Adjustment_Memo_Form1.rad_sub_CR_DR[0].disabled=true;
         }
       try{document.getElementById("txtsub_Amount").value=rcells.item(5).firstChild.value;}catch(e){}
       try{document.getElementById("txtParticular").value=rcells.item(6).firstChild.value;}catch(e){}
       
    document.Adjustment_Memo_Form1.cmdupdate.style.display='block';
    document.Adjustment_Memo_Form1.cmddelete.disabled=false;
    document.Adjustment_Memo_Form1.cmdadd.style.display='none';
    document.getElementById("cmbSL_type").disabled=true;
    document.getElementById("cmbSL_Code").disabled=true;
    
}

function call_clr()
{
		
		document.getElementById("txtRemarks1").value="";
	    document.getElementById("txtAmount").value="";
	    document.getElementById("txtsub_Amount").value="";
	    document.getElementById("cmbAdviceNO").selectedIndex="";
	    document.getElementById("txtDate").value="";
	    document.getElementById("txtLetterNO").value="";
	    document.getElementById("txtLetterDate").value="";
	    document.getElementById("txtAuthority").value="";
	    document.getElementById("txtParticular").value="";
	    document.getElementById("cmbSL_Code").value="";
	    document.Adjustment_Memo_Form1.txtDate.disabled=false;
	    location.reload();
	    document.Adjustment_Memo_Form1.advice_type[0].checked=true;
	    
	    document.Adjustment_Memo_Form1.rad_sub_CR_DR[1].checked=true;
        document.Adjustment_Memo_Form1.rad_sub_CR_DR[0].disabled=true;
         document.Adjustment_Memo_Form1.rad_sub_CR_DR[1].disabled=false;
         document.getElementById("txtAcc_HeadCode").value="610102";
	    
}

function call_mainJSP_script(fromcal_dateCtrl,blr_flag)     /////////Calender control return value handling
{
	    if(blr_flag==1)                 // which is used to find the receipt or voucher or payment (creation) date calling field,if so check trial balance
	    {
	            call_clr();
	            cmbAcc_UnitCode=document.getElementById("cmbAcc_UnitCode").value;
	            cmbOffice_code=document.getElementById("cmbOffice_code").value;
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
function sltype_load()
{
	//document.Adjustment_Memo_Form1.rad_sub_CR_DR[0].disabled=true;
	//   document.Adjustment_Memo_Form1.rad_sub_CR_DR[1].checked=true;
	 var cmbSL_type=document.getElementById("cmbSL_type");   
	// cmbSL_type.innerHTML="";
  var option=document.createElement("OPTION");
  option.text="Accounting Units";
  option.value="15";
//  option.setAttribute('selected','selected');
  try
  {
 	 cmbSL_type.add(option);
  }catch(errorObject)
  {
 	 cmbSL_type.add(option,null);
  }	

  return true;
}
/*
function call_date(dateCtrl)                        // TB_checking 
{
	alert("call");
	    call_clr();
	    if(checkdt(dateCtrl))
	    {       
		         cmbAcc_UnitCode=document.getElementById("cmbAcc_UnitCode").value;
		         cmbOffice_code=document.getElementById("cmbOffice_code").value;
		         var TB_date=dateCtrl.value;
		       
		         if(dateCtrl.value.length!=0)
		         {
			             var url="../../../../../Receipt_SL.view?Command=check_TB&TB_date="+TB_date+"&cmbAcc_UnitCode="+cmbAcc_UnitCode+"&cmbOffice_code="+cmbOffice_code;
			             var req=getTransport();
			             req.open("GET",url,true); 
			             alert("url "+url);
			             req.onreadystatechange=function()
			             {
			               check_TB(req,dateCtrl);
			             }   
			             req.send(null);
		         }
	        
	    }
	    else
	    {
		         var cmbSL_Code=document.getElementById("txtReceipt_No");   
		         cmbSL_Code.innerHTML="";
		         var option=document.createElement("OPTION");
		         option.text="-- Select Receipt Number --";
		         option.value="";
		         try
		         {
		                 cmbSL_Code.add(option);
		         }catch(errorObject)
		         {
		        	 	 cmbSL_Code.add(option,null);
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
			            	 loadMemoNO(this);
			            	 	//doFunction('load_Receipt_No','null');                 //return true;
			             }
			             else if(flag=="failure")
			             {
			                    dateCtrl.value="";
			                    alert("Trial Balance Closed");//return false;//
			                    dateCtrl.focus();
			                    var cmbSL_Code=document.getElementById("txtReceipt_No");   
			                    cmbSL_Code.innerHTML="";
			                    var option=document.createElement("OPTION");
			                    option.text="-- Select Receipt Number --";
			                    option.value="";
			                    try
			                    {
			                        cmbSL_Code.add(option);
			                    }catch(errorObject)
			                    {
			                        cmbSL_Code.add(option,null);
			                    }
			               }
		        }
	    }
}*/
function loadValues()
{
	//alert("Loading Details");
	document.getElementById("txtsub_Amount").readonly=true;
	document.getElementById("txtsub_Amount").value=document.getElementById("txtAmount").value;
	
	document.getElementById("txtParticular").value=document.getElementById("txtRemarks1").value;
	var accHead=document.getElementById("txtAcc_HeadCode").value;
	if(accHead=="610102")
	{
		   document.Adjustment_Memo_Form1.rad_sub_CR_DR[0].disabled=true;
		   document.Adjustment_Memo_Form1.rad_sub_CR_DR[1].checked=true;
		 var cmbSL_type=document.getElementById("cmbSL_type");   
		 cmbSL_type.innerHTML="";
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
          load_acc_unitsNew();
         // load_acc_units();
        // document.getElementById("cmbSL_type")
        
	}  
	else if(accHead=="900202")
		{
		document.Adjustment_Memo_Form1.rad_sub_CR_DR[1].disabled=true;
		   document.Adjustment_Memo_Form1.rad_sub_CR_DR[0].checked=true;
		 var cmbSL_type=document.getElementById("cmbSL_type");   
		 cmbSL_type.innerHTML="";
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
       load_acc_unitsNew();
	         // load_acc_units();
	        // document.getElementById("cmbSL_type")
	        
		}  
		
	    document.getElementById("cmbSL_type").disabled=true;
	    document.getElementById("cmbSL_Code").disabled=true;
}
function load_acc_unitsNew()
{
	var cmbAcc_UnitCode=document.getElementById("cmbAcc_UnitCode").value;
	var url = "../../../../../GetMemoDetails?command=load_acc_units&cmbAcc_UnitCode="+cmbAcc_UnitCode;
	
	var xmlrequest = AjaxFunction();
	xmlrequest.open("POST", url, true);
	xmlrequest.onreadystatechange = function() {
		if (xmlrequest.readyState == 4) {
			if (xmlrequest.status == 200) {

				var baseResponse = xmlrequest.responseXML.getElementsByTagName("response")[0];
				
				var tagCommand = baseResponse.getElementsByTagName("command")[0];
				var command = tagCommand.firstChild.nodeValue;
				 if(command=="load_acc_units")
				{
					 var flag = baseResponse.getElementsByTagName("flag")[0].firstChild.nodeValue;
						if (flag == "success") {
							var cmbSL_Code = document.forms[0].cmbSL_Code;
				            var cid = baseResponse.getElementsByTagName("cid"); 
				            var cname = baseResponse.getElementsByTagName("cname");   
				            for(var i=0; i<cid.length; i++)
				                {
				                    var opt = document.createElement('option');
				                    opt.value = cid[i].firstChild.nodeValue;
				                    opt.innerHTML = cname[i].firstChild.nodeValue;
				                    if(cid[i].firstChild.nodeValue==5){
				                    	opt.setAttribute("selected", "selected");
				                    }
				                    cmbSL_Code.appendChild(opt);
				                }
						
						}
						 else
						 {
							 alert("Failed to Load");
						 }
				}
			}
		}
		
		};

	xmlrequest.send(null);

}function load_acc_unitsNewCP()
{
	var cmbAcc_UnitCode=document.getElementById("cmbAcc_UnitCode").value;
	var url = "../../../../../GetMemoDetails?command=load_acc_units&cmbAcc_UnitCode="+cmbAcc_UnitCode;
	
	var xmlrequest = AjaxFunction();
	xmlrequest.open("POST", url, true);
	xmlrequest.onreadystatechange = function() {
		if (xmlrequest.readyState == 4) {
			if (xmlrequest.status == 200) {

				var baseResponse = xmlrequest.responseXML.getElementsByTagName("response")[0];
				
				var tagCommand = baseResponse.getElementsByTagName("command")[0];
				var command = tagCommand.firstChild.nodeValue;
				 if(command=="load_acc_units")
				{
					 var flag = baseResponse.getElementsByTagName("flag")[0].firstChild.nodeValue;
						if (flag == "success") {
							var cmbSL_Code = document.forms[0].cmbSL_Code;
				            var cid = baseResponse.getElementsByTagName("cid"); 
				            var cname = baseResponse.getElementsByTagName("cname");   
				            for(var i=0; i<cid.length; i++)
				                {
				                    var opt = document.createElement('option');
				                    opt.value = cid[i].firstChild.nodeValue;
				                    opt.innerHTML = cname[i].firstChild.nodeValue;
				                    if(cid[i].firstChild.nodeValue==5){
				                    	opt.setAttribute("selected", "selected");
				                    }
				                    cmbSL_Code.appendChild(opt);
				                }
						
						}
						 else
						 {
							 alert("Failed to Load");
						 }
				}
			}
		}
		
		};

	xmlrequest.send(null);
return true;
}
function doFunctioncp(cmd,param){
	
	    
	    
	    var cmbAcc_UnitCode=document.getElementById("cmbAcc_UnitCode").value;
	    var cmbOffice_code=document.getElementById("cmbOffice_code").value;

    	
    	var cmbSL_type=param;
        document.getElementById("offlist_div_trans").style.display='none';
        document.getElementById("emplist_div_trans").style.display='none';
    
     /*   if(cmbSL_type==5)
          {
              document.getElementById("offlist_div_trans").style.display='block';
              addtional_field_value=document.getElementById("txtOfficeID_trs").value;
              if(addtional_field_value=="")
              {
                    clear_Combo(document.getElementById("cmbSL_Code"));
                    alert("Enter or select the office code");
                    return true;
              }
          }
        else
         {
            document.getElementById("txtOfficeID_trs").value="";
         }*/
        /*  
         if(cmbSL_type==7)
          {
              document.getElementById("emplist_div_trans").style.display='block';
           
              addtional_field_value=document.getElementById("txtEmpID_trs").value;
             
              if(addtional_field_value=="")
              {
                    clear_Combo(document.getElementById("cmbSL_Code")); 
                    alert("Enter or select the employee code");
                    return true;
              }
          }
        else
          {
              document.getElementById("txtEmpID_trs").value="";
          }*/
          
     
    	
             var cmbMas_SL_type=document.getElementById("cmbMas_SL_type").value;
     
             var other_dept_off_alias_id=document.getElementById("cmbMas_SL_Code").value;
           
             var url="../../../../../Receipt_SL.view?Command=Load_SL_Code&cmbSL_type="+cmbSL_type+"&cmbMas_SL_type="+cmbMas_SL_type+
             "&other_dept_off_alias_id="+other_dept_off_alias_id+"&cmbAcc_UnitCode="+cmbAcc_UnitCode+
             "&cmbOffice_code="+cmbOffice_code+"&addtional_field_value="+addtional_field_value;//+"&month1="+month1+"&year11="+year11;
    
            var req=getTransport();
            req.open("GET",url,true); 
            req.onreadystatechange=function()
            {
            	 if(req.readyState==4)
            	    { 
            	        if(req.status==200)
            	        {  
            	            
            	            var baseResponse=req.responseXML.getElementsByTagName("response")[0];
            	            var tagcommand=baseResponse.getElementsByTagName("command")[0];
            	            var Command=tagcommand.firstChild.nodeValue;
            	           
            	             if(Command=="Load_SL_Code")
            	            {

            	            	 var flag=baseResponse.getElementsByTagName("flag")[0].firstChild.nodeValue;

            	            	     if(flag=="success")
            	            	     {
            	            	          var cmbSL_Code=document.getElementById("cmbSL_Code");
            	            	          
            	            	          var items_id=new Array();
            	            	          var items_name=new Array();
            	            	     
            	            	         
            	            	             var cid=baseResponse.getElementsByTagName("cid");
            	            	             var cname=baseResponse.getElementsByTagName("cname");
            	            	             for(var k=0;k<cid.length;k++)
            	            	             {
            	            	                 items_id[k]=baseResponse.getElementsByTagName("cid")[k].firstChild.nodeValue;
            	            	               
            	            	                 items_name[k]=baseResponse.getElementsByTagName("cname")[k].firstChild.nodeValue;
            	            	           
            	            	             }
            	            	            clear_Combo(cmbSL_Code);
            	            	        
            	            	             for(var k=0;k<cid.length;k++)
            	            	             {   
            	            	                   var option=document.createElement("OPTION");
            	            	                   
            	            	                   option.text=items_name[k]+"("+items_id[k]+")";
            	            	                 
            	            	                   option.value=items_id[k];
            	            	                   
            	            	                    try
            	            	                   {
            	            	                       cmbSL_Code.add(option);
            	            	                   }
            	            	                   catch(errorObject)
            	            	                   {
            	            	                       cmbSL_Code.add(option,null);
            	            	                   }
            	            	             }
            	            	            //  document.getElementById("cmbSL_Code").value=items_id[0];
            	            	            
            	            	            if(document.getElementById("cmbSL_type").value==5)
            	            	            {
            	            	                 var state=baseResponse.getElementsByTagName("state")[0].firstChild.nodeValue;
            	            	                 if(state!="CR")
            	            	                 alert("Office is not in working status");
            	            	            }
            	            	            
            	            	            if(document.getElementById("cmbMas_SL_type").value!=9 && document.getElementById("cmbSL_type").value==7)
            	            	            {
            	            	                 var state=baseResponse.getElementsByTagName("state")[0].firstChild.nodeValue;
            	            	                 if(state=="DPN")
            	            	                 alert("Employee in Deputation");
            	            	            } 
            	            	            
            	            	         
            	            	     }
            	            	  
            	            	     
            	            	    

            	            
            	        }
            	    }
            	 
            } ;  
                    req.send(null);
        }
      

      return true;

}
