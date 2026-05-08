
var common_cmbSL_Code="";
var common_cmbSL_type="";
var job_flag;
var common_AHead_code_flag="";
var emp_flag;
//-------------
var com_Acq;
var com_PAYWISE_id;
var com_PAYWISE_DETAILS_id;
var seq_toLoad_PAYWISE=100;                     // here this is set to seq_toLoad_Acq=0, seq_toLoad_PAYWISE=100 and seq_toLoad_PAYWISE_DETAILS=200, bcoz 3 grids are used here.. so identify the rows in different GRID
var seq_toLoad_PAYWISE_DETAILS=200;
var seq_toLoad_Acq=0;
var seq_PAYWISE_VrNo=0;
//---------------------------

window.onunload=function()
{
    if (winAccHeadCode && winAccHeadCode.open && !winAccHeadCode.closed) winAccHeadCode.close();
    if (winjob && winjob.open && !winjob.closed) winjob.close();
    if (winemp && winemp.open && !winemp.closed) winemp.close();
    if (winAcc_Bank_No && winAcc_Bank_No.open && !winAcc_Bank_No.closed) winAcc_Bank_No.close();

}

function enable_acq(value_acqYN)
{
    if(value_acqYN=="Y")
    {
        document.getElementById("txtPayWise_AcqSL_No").readOnly=false;
        document.getElementById("txtPayWise_AcqSL_No").value="";
    }
    else
    {
        document.getElementById("txtPayWise_AcqSL_No").readOnly=true;
        document.getElementById("txtPayWise_AcqSL_No").value=0;
    }
}
/////////////////////////////////////////////   ADD & UPDATE & DELETE PAYWISE voucher /////////////////////////////////////////////////////
function ADD_PAYWISE_GRID()
{
       
        if((document.getElementById("txtPayWise_AcqSL_No").value.length==0 || document.getElementById("txtPayWise_AcqSL_No").value==0) && document.frmSelfCheque_Create.radAcq_roll_YN[0].checked==true )
        {
            alert("Enter the Acq. Roll SL number");
            document.getElementById("txtPayWise_AcqSL_No").focus();
            return false;    
        }
        if(document.getElementById("txtPayWise_Amount").value.length==0)
        {
            alert("Enter the Amount ");
            document.getElementById("txtPayWise_Amount").focus();
            return false;    
        }
        
        
        var tbody=document.getElementById("grid_body_PAYWISE");
        seq_PAYWISE_VrNo=parseInt(seq_PAYWISE_VrNo)+1;
        
        var items=new Array();
        items[0]=document.getElementById("txtPayWise_AcqSL_No").value;
        items[1]=seq_PAYWISE_VrNo;      
        items[2]=document.getElementById("txtPayWise_Amount").value;
        items[3]=document.getElementById("txtPayWiseRemarks").value;
        
        var mycurrent_row=document.createElement("TR");
        seq_toLoad_PAYWISE=seq_toLoad_PAYWISE+1;
        
        mycurrent_row.id=seq_toLoad_PAYWISE;
        //alert("row ID"+mycurrent_row.id);
        var cell=document.createElement("TD");
        var anc=document.createElement("A");
        var url="javascript:loadTable_PAYWISE('"+mycurrent_row.id+"')";
        anc.href=url;
        var txtedit=document.createTextNode("EDIT");
        anc.appendChild(txtedit);
        cell.appendChild(anc);
        mycurrent_row.appendChild(cell);
      
        var cell2;
       // for(i=0;i<10;i++)
         // alert(items[i]);
          cell2=document.createElement("TD");

                  var paywise_AcqNumber=document.createElement("input");
                  paywise_AcqNumber.type="hidden";
                  paywise_AcqNumber.name="paywise_AcqNumber";
                  paywise_AcqNumber.value=items[0];
                  cell2.appendChild(paywise_AcqNumber);
                  var currentText=document.createTextNode(items[0]);
                  cell2.appendChild(currentText);
                  mycurrent_row.appendChild(cell2);
                   
            cell2=document.createElement("TD");

                  var paywise_vouNo=document.createElement("input");
                  paywise_vouNo.type="hidden";
                  paywise_vouNo.name="paywise_vouNo";
                  paywise_vouNo.value=items[1];
                  cell2.appendChild(paywise_vouNo);
                  var currentText=document.createTextNode(items[1]);
                  cell2.appendChild(currentText);
                  mycurrent_row.appendChild(cell2);
                  
             
            cell2=document.createElement("TD");
                  var paywise_Amount=document.createElement("input");
                  paywise_Amount.type="hidden";
                  paywise_Amount.name="paywise_Amount";
                  paywise_Amount.value=items[2];
                  cell2.appendChild(paywise_Amount);
                   var currentText=document.createTextNode(items[2]);
                  cell2.appendChild(currentText);
                mycurrent_row.appendChild(cell2);
            
            cell2=document.createElement("TD");
                  var paywise_remark=document.createElement("input");
                  paywise_remark.type="hidden";
                  paywise_remark.name="paywise_remark";
                  paywise_remark.value=items[3];
                  cell2.appendChild(paywise_remark);
                   var currentText=document.createTextNode(items[3]);
                  cell2.appendChild(currentText);
                mycurrent_row.appendChild(cell2);
                
              
        tbody.appendChild(mycurrent_row);
        clearall_PAYWISE();
}


function update_PAYWISE_GRID()
{
       
       if((document.getElementById("txtPayWise_AcqSL_No").value.length==0 || document.getElementById("txtPayWise_AcqSL_No").value==0) && document.frmSelfCheque_Create.radAcq_roll_YN[0].checked==true )
        {
            alert("Enter the Acq. Roll SL number");
            document.getElementById("txtPayWise_AcqSL_No").focus();
            return false;    
        }

        if(document.getElementById("txtPayWise_Amount").value.length==0)
        {
            alert("Enter the Amount ");
            document.getElementById("txtPayWise_Amount").focus();
            return false;    
        }
        
        var items=new Array();
        items[0]=document.getElementById("txtPayWise_AcqSL_No").value;
        items[1]=document.getElementById("txtPayWise_Voucher_No").value;
        items[2]=document.getElementById("txtPayWise_Amount").value;
        items[3]=document.getElementById("txtPayWiseRemarks").value;
        
        var r=document.getElementById(com_PAYWISE_id);
        var rcells=r.cells;
        
        rcells.item(1).firstChild.value=items[0];
        rcells.item(1).lastChild.nodeValue=items[0];
        rcells.item(2).firstChild.value=items[1];
        rcells.item(2).lastChild.nodeValue=items[1];
        rcells.item(3).firstChild.value=items[2];
        rcells.item(3).lastChild.nodeValue=items[2];
        rcells.item(4).firstChild.value=items[3];
        rcells.item(4).lastChild.nodeValue=items[3];
        
   clearall_PAYWISE();
}


function delete_PAYWISE_GRID()
{
        if(confirm("Do you want to delete ?"))
        {
            var tbody=document.getElementById("mytable");
            var r=document.getElementById(com_PAYWISE_id);
            var cell=r.cells;
            var General_Vrnumber=cell.item(1).firstChild.value;
            // checking voucher deatils part
             var tbody_PAYWISE_DETAILS=document.getElementById("grid_body_PAYWISE_DETAILS");
             var details_rows=tbody_PAYWISE_DETAILS.getElementsByTagName("tr");
             for(j=0;j<details_rows.length;j++)             // check the Voucher existness in Details
             {
                 var details_cells=details_rows[j].cells;
                
                 Details_Vrnumber=details_cells.item(1).firstChild.value;
                 if(parseInt(Details_Vrnumber)==parseInt(General_Vrnumber))
                 {
                    alert("This voucher has Entries in Details part of Voucher \n So you can't delete");
                    return false;
                 }
             }
            // checking Acquittance part
            var tbody_Acq=document.getElementById("grid_body_Acq");
            var Acq_rows=tbody_Acq.getElementsByTagName("tr");
         
                for(j=0;j<Acq_rows.length;j++)       // check the Voucher existness in Acq. Details
                {
                      var Acq_cells=Acq_rows[j].cells;
                      if(parseInt(Acq_cells.item(2).firstChild.value)==parseInt(General_Vrnumber))
                      {
                           alert("This voucher has Entries in Acquittance part \n So you can't delete");           
                           return false;
                      }
                }
             
            var ri=r.rowIndex;
            tbody.deleteRow(ri);
            //seq_PAYWISE_VrNo=parseInt(seq_PAYWISE_VrNo)-1;
            clearall_PAYWISE();
        }
}
function loadTable_PAYWISE(scod)
{
        com_PAYWISE_id=scod;                                    // to identify in UPDATE_GRID ,which row loaded 
        clearall_PAYWISE();
     
        var r=document.getElementById(scod);
        var rcells=r.cells;
        try{document.getElementById("txtPayWise_AcqSL_No").value=rcells.item(1).firstChild.value;}catch(e){}
        try{document.getElementById("txtPayWise_Voucher_No").value=rcells.item(2).firstChild.value;}catch(e){}
        try{document.getElementById("txtPayWise_Amount").value=rcells.item(3).firstChild.value;}catch(e){}
        try{document.getElementById("txtPayWiseRemarks").value=rcells.item(4).firstChild.value;}catch(e){}
        if(document.getElementById("txtPayWise_AcqSL_No").value==0)
        {
             document.frmSelfCheque_Create.radAcq_roll_YN[1].checked=true;
             document.getElementById("txtPayWise_AcqSL_No").readOnly=true;
        }
        else
         {
            document.frmSelfCheque_Create.radAcq_roll_YN[0].checked=true;
            document.getElementById("txtPayWise_AcqSL_No").readOnly=false;
         }
        
    document.frmSelfCheque_Create.cmdupdate_PAYWISE.style.display='block';
    document.frmSelfCheque_Create.cmddelete_PAYWISE.disabled=false;
    document.frmSelfCheque_Create.cmdadd_PAYWISE.style.display='none';
}

function clearall_PAYWISE()
{
    document.getElementById("txtPayWise_Voucher_No").value="";
    document.getElementById("txtPayWise_Amount").value="";
    document.getElementById("txtPayWiseRemarks").value="";
    document.getElementById("txtPayWise_AcqSL_No").value="";
    document.getElementById("txtPayWise_AcqSL_No").readOnly=false;
    document.frmSelfCheque_Create.radAcq_roll_YN[0].checked=true;
    
     document.frmSelfCheque_Create.cmdadd_PAYWISE.style.display='block';
     document.frmSelfCheque_Create.cmdupdate_PAYWISE.style.display='none';
     document.frmSelfCheque_Create.cmddelete_PAYWISE.disabled=true;

}


/////////////////////////////////////////////   ADD & UPDATE & DELETE PAYWISE Details voucher /////////////////////////////////////////////////////
function ADD_PAYWISE_DETAILS_GRID()
{
       var main_tbody=document.getElementById("grid_body_PAYWISE");
       var details_tbody=document.getElementById("grid_body_PAYWISE_DETAILS");
        
       if(document.getElementById("txtpayDetails_Voucher_No").value.length==0 || document.getElementById("txtpayDetails_Voucher_No").value==0)
        {
        alert("Enter Voucher SL. No");
        document.getElementById("txtpayDetails_Voucher_No").focus();
        return false;
        }
        if(document.getElementById("cmbSL_type").length>1 && document.getElementById("cmbSL_type").value=="")
        {
          
                alert("Select a Sub-Ledger Type");
                document.getElementById("cmbSL_type").focus();
                return false;
  
        }
        
        //alert(main_tbody.rows.length)
        if(main_tbody.rows.length<=0)
        {
            alert("Enter the General part first");
            return false;
        }
        if(main_tbody.rows.length>0)
        {
           // alert('here');
            
            var rows=main_tbody.getElementsByTagName("tr");
            var count=0;
            for(i=0;i<rows.length;i++)      // check the Voucher existness in General
            {
                //alert(i);
                //alert('here'+rows.length)
                 var cells=rows[i].cells;
                 var Details_Vrnumber=document.getElementById("txtpayDetails_Voucher_No").value;
                 //alert(cells.item(1).firstChild.value);
                 var General_Vrnumber=cells.item(2).firstChild.value;
                 if(parseInt(Details_Vrnumber)==parseInt(General_Vrnumber))
                 count=count+1;
                 
            }
            if(count==0)
             {
                alert("Voucher SL. Number doesn't exist in General part")
                document.getElementById("txtpayDetails_Voucher_No").focus();
                return false;
             }
        
        }
        if(document.getElementById("txtAcc_HeadCode").value.length==0 || document.getElementById("txtAcc_HeadCode").value==0)
        {
        alert("Enter A/c Head");
        document.getElementById("txtAcc_HeadCode").focus();
        return false;
        }
       // alert(document.getElementById("txtAcc_HeadCode").value.length);
        if(document.getElementById("txtAcc_HeadCode").value.length<6)
        {
        	alert("Enter valid Head");
        	document.getElementById("txtAcc_HeadCode").value="";
            document.getElementById("txtAcc_HeadCode").focus();
            return false;	
        }
      /*  if(document.getElementById("cmbSL_type").length>1 && document.getElementById("cmbSL_type").value=="")
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
          
        }  */
        if(document.getElementById("txtAcc_HeadCode").value=="820103" || document.getElementById("txtAcc_HeadCode").value=="820102")
        {
        	if(document.getElementById("cmbSL_type").value=="")
            {
              alert("Select Sub-Ledger Type");
              return false;
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
        if(document.getElementById("txtPayWiseDetails_Amount").value.length==0)
        {
            alert("Enter the Amount ");
            document.getElementById("txtPayWiseDetails_Amount").focus();
            return false;    
        }
       
        
        var items=new Array();
        items[0]=document.getElementById("txtpayDetails_Voucher_No").value;
        items[1]=document.getElementById("txtAcc_HeadCode").value;
        items[2]=document.getElementById("txtAcc_HeadDesc").value;
        items[3]=document.getElementById("cmbSL_type").value;
        if(document.getElementById("cmbSL_type").value=="")
        {
        //items[4]="Not Available";
        items[4]="";           
        }
        else
        items[4]=document.getElementById("cmbSL_type").options[document.getElementById("cmbSL_type").selectedIndex].text; 
        
        items[5]=document.getElementById("cmbSL_Code").value;
        if(document.getElementById("cmbSL_Code").value=="")
        {
        items[6]="";                //"Not Available";
        }
        else
        items[6]=document.getElementById("cmbSL_Code").options[document.getElementById("cmbSL_Code").selectedIndex].text; 
        
        items[7]=document.getElementById("txtPayWiseDetails_Amount").value;
        items[8]=document.getElementById("txtPayWise_DetailsParticular").value;
        
        var recoup_body=document.getElementById("recoup_body");
        var re_count=0;
        items[9]="";
        items[10]="";
        items[11]="";
        items[12]="";
        if(document.frmSelfCheque_Create.payment_type[1].checked==true)
        {
        		if(recoup_body.rows.length>1)
        		{
		        	   for(i=0;i<recoup_body.rows.length;i++)
		               {
		                      if(document.frmSelfCheque_Create.sel[i].checked==true)
		                      {
		                    	   		items[9]=document.frmSelfCheque_Create.recoup_voucher[i].value;
		                    	   		items[10]=document.frmSelfCheque_Create.recoup_voucher_dt[i].value;
		                    	   		items[11]=document.frmSelfCheque_Create.recoup_voucher_amt[i].value;
		                    	   		items[12]=document.frmSelfCheque_Create.sno[i].value;
		                    	   		re_count++;
		                      }
		               }
        		}
        		else if(recoup_body.rows.length==1)
        		{
        			   if(document.frmSelfCheque_Create.sel.checked==true)
        			   {
        				   	  items[9]=document.frmSelfCheque_Create.recoup_voucher.value;
        				   	  items[10]=document.frmSelfCheque_Create.recoup_voucher_dt.value;
        				   	  items[11]=document.frmSelfCheque_Create.recoup_voucher_amt.value;
        				   	  items[12]=document.frmSelfCheque_Create.sno.value;
        				   	  re_count++;
        				   	 
        			   }        			   
        		}
        		else
        		{
        			   alert("No Voucher Available for Recoup");
        			   return false;
        		}
        		
        		if(re_count==0)
 			   	{
 				   	   alert("Select Recoup Voucher Number");
 				   	   return false;
 			   	}
        }
        if(document.frmSelfCheque_Create.payment_type[0].checked==true)
        {
    			items[13]="N";
    			items[14]="New";
        }
        else
        {
    			items[13]="R";
    			items[14]="Recoup";
        }
        items[15]=document.getElementById("drawl_purpose").value;
        if(document.getElementById("drawl_purpose").value=="")
        {
        items[16]="--";                //"Not Available";
        }
        else
        items[16]=document.getElementById("drawl_purpose").options[document.getElementById("drawl_purpose").selectedIndex].text; 
        
        
        var mycurrent_row=document.createElement("TR");
        seq_toLoad_PAYWISE_DETAILS=seq_toLoad_PAYWISE_DETAILS+1;
        
        mycurrent_row.id=seq_toLoad_PAYWISE_DETAILS;
        //alert("row ID"+mycurrent_row.id);
        var cell=document.createElement("TD");
        var anc=document.createElement("A");
        var url="javascript:loadTable_PAYWISE_DETAILS('"+mycurrent_row.id+"')";
        anc.href=url;
        var txtedit=document.createTextNode("EDIT");
        anc.appendChild(txtedit);
        cell.appendChild(anc);
        mycurrent_row.appendChild(cell);
      
        var cell2;
      
             cell2=document.createElement("TD");
                  var paywiseDETAILS_vouNo=document.createElement("input");
                  paywiseDETAILS_vouNo.type="hidden";
                  paywiseDETAILS_vouNo.name="paywiseDETAILS_vouNo";
                  paywiseDETAILS_vouNo.value=items[0];
                  cell2.appendChild(paywiseDETAILS_vouNo);
                  var currentText=document.createTextNode(items[0]);
                  cell2.appendChild(currentText);
                  mycurrent_row.appendChild(cell2);
                  
              cell2=document.createElement("TD");

                  var paywiseDETAILS_AccHEAD=document.createElement("input");
                  paywiseDETAILS_AccHEAD.type="hidden";
                  paywiseDETAILS_AccHEAD.name="paywiseDETAILS_AccHEAD";
                  paywiseDETAILS_AccHEAD.value=items[1];
                  cell2.appendChild(paywiseDETAILS_AccHEAD);
                  var currentText=document.createTextNode(items[1]+'-'+items[2]);
                  cell2.appendChild(currentText);
                  mycurrent_row.appendChild(cell2);
            
              cell2=document.createElement("TD");
                   var SL_type_DETAILS=document.createElement("input");
                  SL_type_DETAILS.type="hidden";
                  SL_type_DETAILS.name="SL_type_DETAILS";
                  SL_type_DETAILS.value=items[3];
                  cell2.appendChild(SL_type_DETAILS);
                   var currentText=document.createTextNode(items[4]);
                  cell2.appendChild(currentText);
                   mycurrent_row.appendChild(cell2);
                   
             cell2=document.createElement("TD");
                  var SL_code_DETAILS=document.createElement("input");
                  SL_code_DETAILS.type="hidden";
                  SL_code_DETAILS.name="SL_code_DETAILS";
                  SL_code_DETAILS.value=items[5];
                  cell2.appendChild(SL_code_DETAILS);
                   var currentText=document.createTextNode(items[6]);
                  cell2.appendChild(currentText);
                mycurrent_row.appendChild(cell2);
                
            cell2=document.createElement("TD");
                  var paywiseDETAILS_Amount=document.createElement("input");
                  paywiseDETAILS_Amount.type="hidden";
                  paywiseDETAILS_Amount.name="paywiseDETAILS_Amount";
                  paywiseDETAILS_Amount.value=items[7];
                  cell2.appendChild(paywiseDETAILS_Amount);
                   var currentText=document.createTextNode(items[7]);
                  cell2.appendChild(currentText);
                mycurrent_row.appendChild(cell2);
            
            cell2=document.createElement("TD");
                  var paywiseDETAILS_parti=document.createElement("input");
                  paywiseDETAILS_parti.type="hidden";
                  paywiseDETAILS_parti.name="paywiseDETAILS_parti";
                  paywiseDETAILS_parti.value=items[8];
                  cell2.appendChild(paywiseDETAILS_parti);
                   var currentText=document.createTextNode(items[8]);
                  cell2.appendChild(currentText);
                mycurrent_row.appendChild(cell2);
                
                cell2=document.createElement("TD");
                var P_TYPE=document.createElement("input");
			       P_TYPE.type="hidden";
			       P_TYPE.name="p_type";
			       P_TYPE.id="p_type";
			       P_TYPE.value=items[13];
			       cell2.appendChild(P_TYPE);			     
			       var currentText=document.createTextNode(items[14]);
			       cell2.appendChild(currentText);
			       mycurrent_row.appendChild(cell2);
                
			       cell2=document.createElement("TD");
	                  var purpose_drawl=document.createElement("input");
	                  purpose_drawl.type="hidden";
	                  purpose_drawl.name="purposedrawl";
	                  purpose_drawl.value=items[15];
	                  cell2.appendChild(purpose_drawl);
	                   var currentText=document.createTextNode(items[16]);
	                  cell2.appendChild(currentText);
	                mycurrent_row.appendChild(cell2);
			       
                cell2=document.createElement("TD");
	              
			       var voucher_no=document.createElement("input");
			       voucher_no.type="hidden";
			       voucher_no.name="recoup_vou_no";
			       voucher_no.id="recoup_vou_no";
			       voucher_no.value=items[9];
			       cell2.appendChild(voucher_no);
			       var sno=document.createElement("input");
			       sno.type="hidden";
			       sno.name="sl_no";
			       sno.id="sl_no";
			       sno.value=items[12];
			       cell2.appendChild(sno);
			       var currentText=document.createTextNode(items[9]);
			       cell2.appendChild(currentText);
			       mycurrent_row.appendChild(cell2);
			           
			       cell2=document.createElement("TD");
			       
			       var voucher_dt=document.createElement("input");
			       voucher_dt.type="hidden";
			       voucher_dt.name="recoup_vou_dt";
			       voucher_dt.id="recoup_vou_dt";
			       voucher_dt.value=items[10];
			       cell2.appendChild(voucher_dt);
			       var voucher_amt=document.createElement("input");
			       voucher_amt.type="hidden";
			       voucher_amt.name="recoup_vou_amt";
			       voucher_amt.id="recoup_vou_amt";
			       voucher_amt.value=items[11];
			       cell2.appendChild(voucher_amt);
			       var currentText=document.createTextNode(items[10]);
			       cell2.appendChild(currentText);
			       mycurrent_row.appendChild(cell2);
             
        details_tbody.appendChild(mycurrent_row);
        clearall_PAYWISE_DETAILS();
}


function update_PAYWISE_DETAILS_GRID()
{
       var main_tbody=document.getElementById("grid_body_PAYWISE");
       var details_tbody=document.getElementById("grid_body_PAYWISE_DETAILS");
        
       if(document.getElementById("txtpayDetails_Voucher_No").value.length==0 || document.getElementById("txtpayDetails_Voucher_No").value==0)
        {
        alert("Enter Voucher SL. No");
        document.getElementById("txtpayDetails_Voucher_No").focus();
        return false;
        }
         if(document.getElementById("cmbSL_type").length>1 && document.getElementById("cmbSL_type").value=="")
        {
          
                alert("Select a Sub-Ledger Type");
                document.getElementById("cmbSL_type").focus();
                return false;
  
        }
        //alert(main_tbody.rows.length)
        if(main_tbody.rows.length<=0)
        {
            alert("Enter the General part first");
            return false;
        }
        if(main_tbody.rows.length>0)
        {
           // alert('here');
            
            var rows=main_tbody.getElementsByTagName("tr");
            var count=0;
            for(i=0;i<rows.length;i++)
            {
                
                 var cells=rows[i].cells;
                 var Details_Vrnumber=document.getElementById("txtpayDetails_Voucher_No").value;
                 //alert(cells.item(1).firstChild.value);
                 var General_Vrnumber=cells.item(2).firstChild.value;
                 if(parseInt(Details_Vrnumber)==parseInt(General_Vrnumber))
                 count=count+1;
                 
            }
            if(count==0)
             {
                alert("Voucher SL. Number doesn't exist in General part")
                document.getElementById("txtpayDetails_Voucher_No").focus();
                return false;
             }
        
        }
        if(document.getElementById("txtAcc_HeadCode").value.length==0 || document.getElementById("txtpayDetails_Voucher_No").value==0)
        {
        alert("Enter A/c Head");
        document.getElementById("txtAcc_HeadCode").focus();
        return false;
        }
       /* if(document.getElementById("cmbSL_type").length>1 && document.getElementById("cmbSL_type").value=="")
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
          
        }  */
        if(document.getElementById("txtAcc_HeadCode").value=="820103" || document.getElementById("txtAcc_HeadCode").value=="820102")
        {
        	if(document.getElementById("cmbSL_type").value=="")
            {
              alert("Select Sub-Ledger Type");
              return false;
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
        if(document.getElementById("txtPayWiseDetails_Amount").value.length==0)
        {
            alert("Enter the Amount ");
            document.getElementById("txtPayWiseDetails_Amount").focus();
            return false;    
        }
       
          var items=new Array();
        items[0]=document.getElementById("txtpayDetails_Voucher_No").value;
        items[1]=document.getElementById("txtAcc_HeadCode").value;
        items[2]=document.getElementById("txtAcc_HeadDesc").value;
        items[3]=document.getElementById("cmbSL_type").value;
        if(document.getElementById("cmbSL_type").value=="")
        {
        //items[4]="Not Available";
        items[4]="";           
        }
        else
        items[4]=document.getElementById("cmbSL_type").options[document.getElementById("cmbSL_type").selectedIndex].text; 
        
        items[5]=document.getElementById("cmbSL_Code").value;
        if(document.getElementById("cmbSL_Code").value=="")
        {
        items[6]="";                //"Not Available";
        }
        else
        items[6]=document.getElementById("cmbSL_Code").options[document.getElementById("cmbSL_Code").selectedIndex].text; 
        
        items[7]=document.getElementById("txtPayWiseDetails_Amount").value;
        items[8]=document.getElementById("txtPayWise_DetailsParticular").value;
        
        items[9]=document.getElementById("drawl_purpose").value;
        if(document.getElementById("drawl_purpose").value=="")
        {
        items[10]="--";                //"Not Available";
        }
        else
        items[10]=document.getElementById("drawl_purpose").options[document.getElementById("drawl_purpose").selectedIndex].text; 
        
        
        var r=document.getElementById(com_PAYWISE_DETAILS_id);
        var rcells=r.cells;
        
        rcells.item(1).firstChild.value=items[0];
        rcells.item(1).lastChild.nodeValue=items[0];
        rcells.item(2).firstChild.value=items[1];
        rcells.item(2).lastChild.nodeValue=items[1]+'-'+items[2];
        rcells.item(3).firstChild.value=items[3];
        rcells.item(3).lastChild.nodeValue=items[4];
        rcells.item(4).firstChild.value=items[5];
        rcells.item(4).lastChild.nodeValue=items[6];
        rcells.item(5).firstChild.value=items[7];
        rcells.item(5).lastChild.nodeValue=items[7];
        rcells.item(6).firstChild.value=items[8];
        rcells.item(6).lastChild.nodeValue=items[8];
        rcells.item(8).firstChild.value=items[9];
        rcells.item(8).lastChild.nodeValue=items[10];
   clearall_PAYWISE_DETAILS();
}

function loadTable_PAYWISE_DETAILS(scod_details)
{
        com_PAYWISE_DETAILS_id=scod_details;                                    // to identify in UPDATE_GRID ,which row loaded 
        clearall_PAYWISE_DETAILS();
     
        var r=document.getElementById(scod_details);
        var rcells=r.cells;
        try{document.getElementById("txtpayDetails_Voucher_No").value=rcells.item(1).firstChild.value;}catch(e){}
        try{document.getElementById("txtAcc_HeadCode").value=rcells.item(2).firstChild.value;}catch(e){}
        try{common_cmbSL_type=rcells.item(3).firstChild.value;} catch(e){common_cmbSL_type="";}
        //alert("U"+common_cmbSL_type+"U")
        try{common_cmbSL_Code=rcells.item(4).firstChild.value;} catch(e){common_cmbSL_Code="";} 
        
         if(common_cmbSL_type==5)
         {
              document.getElementById("txtOfficeID_trs").value=common_cmbSL_Code;
              job_flag=false;
              //doFunction('office',common_cmbSL_Code);
              //doFunction('Load_SL_Code',);
         }
         if(common_cmbSL_type==7)
         {
              document.getElementById("txtEmpID_trs").value=common_cmbSL_Code;
              emp_flag=false;
              //doFunction('office',common_cmbSL_Code);
              //doFunction('Load_SL_Code',);
         }
                doFunction('checkCode','null');
                doFunction('Load_SL_Code',common_cmbSL_type);
       
        
        try{document.getElementById("txtPayWiseDetails_Amount").value=rcells.item(5).firstChild.value;}catch(e){}
        try{document.getElementById("txtPayWise_DetailsParticular").value=rcells.item(6).firstChild.value;}catch(e){}
        
        try{document.getElementById("drawl_purpose").value=rcells.item(8).firstChild.value;}catch(e){}
       // alert(rcells.item(7).lastChild.nodeValue+"  "+rcells.item(8).childNodes.item(1).nodeValue+" "+rcells.item(8).childNodes.item(1).value+" "+rcells.item(9).lastChild.nodeValue);
        
        if(rcells.item(7).firstChild.value=="N")
    	   	 document.frmSelfCheque_Create.payment_type[0].checked=true;
        else
    	   	 document.frmSelfCheque_Create.payment_type[1].checked=true;    
	    document.frmSelfCheque_Create.cmdupdate_PAYWISE_DETAILS.style.display='block';
	    document.frmSelfCheque_Create.cmddelete_PAYWISE_DETAILS.disabled=false;
	    document.frmSelfCheque_Create.cmdadd_PAYWISE_DETAILS.style.display='none';
	    
	    if(document.frmSelfCheque_Create.payment_type[1].checked==true)
	    	   loadRecoupDetails('edit',scod_details);
	    else
	    	   loadRecoupDetails('create','null');
}


function delete_PAYWISE_DETAILS_GRID()
{
        if(confirm("Do you want to delete ?"))
        {
            var tbody=document.getElementById("mytable_details");
            var r=document.getElementById(com_PAYWISE_DETAILS_id);
            var ri=r.rowIndex;
            tbody.deleteRow(ri);
            clearall_PAYWISE_DETAILS();
        }
}
function clearall_PAYWISE_DETAILS()
{
     document.getElementById("offlist_div_trans").style.display='none';
     document.getElementById("emplist_div_trans").style.display='none';
   
    document.getElementById("txtpayDetails_Voucher_No").value="";
    document.getElementById("txtAcc_HeadCode").value="";
    document.getElementById("txtPayWiseDetails_Amount").value="";
    document.getElementById("txtPayWise_DetailsParticular").value="";

      var cmbSL_type=document.getElementById("cmbSL_type"); 
      cmbSL_type.innerHTML=""; 
                var option=document.createElement("OPTION");
                option.text="--Select Type--";
                option.value="";
                try
                {
                    cmbSL_type.add(option);
                }catch(errorObject)
                {
                    cmbSL_type.add(option,null);
                }
        var cmbSL_Code=document.getElementById("cmbSL_Code");   
        clear_Combo(cmbSL_Code);   

     document.frmSelfCheque_Create.cmdadd_PAYWISE_DETAILS.style.display='block';
     document.frmSelfCheque_Create.cmdupdate_PAYWISE_DETAILS.style.display='none';
     document.frmSelfCheque_Create.cmddelete_PAYWISE_DETAILS.disabled=true;

}

function clrForm()
{
   if(window.confirm("Do you want to clear ALL fields ?"))
 {
    call_clr();
 }
}

function call_clr()
{
        document.getElementById("txtCheque_DD_NO").value="";
        document.getElementById("txtCheque_DD_date").value="";
       
        document.getElementById("txtAmount").value="";
        document.getElementById("txtRemarks").value="";
        
        document.frmSelfCheque_Create.radAllSpec[1].checked=true;
        
        var tbody=document.getElementById("grid_body_PAYWISE");
        var t=0;
        for(t=tbody.rows.length-1;t>=0;t--)
        {
           tbody.deleteRow(0);
        }
        var tbody=document.getElementById("grid_body_PAYWISE_DETAILS");
        var t=0;
        for(t=tbody.rows.length-1;t>=0;t--)
        {
           tbody.deleteRow(0);
        }
         var tbody=document.getElementById("grid_body_Acq");
        var t=0;
        for(t=tbody.rows.length-1;t>=0;t--)
        {
           tbody.deleteRow(0);
        }
}



///////////////////////////////////////////    TB_checking and Calender control return value handling



function call_mainJSP_script(fromcal_dateCtrl,blr_flag)     /////////Calender control return value handling
{
    //alert(fromcal_dateCtrl.value+"GG"+blr_flag+fromcal_dateCtrl);
    if(blr_flag==1)         // which is used to find the receipt or voucher or payment (creation) date calling field,if so check trial balance
    {
             call_clr();
             var cmbAcc_UnitCode=document.getElementById("cmbAcc_UnitCode").value;
             var cmbOffice_code=document.getElementById("cmbOffice_code").value;
             var TB_date=fromcal_dateCtrl.value;
             //alert(fromcal_dateCtrl.value+"b4url")
             if(fromcal_dateCtrl.value.length!=0)
             {
                 var url="../../../../../Receipt_SL.view?Command=check_TB&TB_date="+TB_date+"&cmbAcc_UnitCode="+cmbAcc_UnitCode+"&cmbOffice_code="+cmbOffice_code;
                        //alert(url);
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

/*function call_date(dateCtrl)                        // TB_checking 
{
    call_clr();
    if(checkdt(dateCtrl))
    {
        //doFunction('check_TB',dateCtrl.value);
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
        //doFunction('load_Receipt_No','null');
    }
    else
    {
      document.getElementById("txtReceipt_No").value="";
    }
}
*/


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
                    document.getElementById("txtReceipt_No").value="";
               }
              else if(flag=="finyear")
               {
                          // This statement get executed when financial year ( Cash Book Control Details ) has not found for the given date 
                    dateCtrl.value="";
                    alert("Cash Book Control Not Found ");//return false;//
                    dateCtrl.focus();
                    document.getElementById("txtReceipt_No").value="";     
               }
        }
    }
}


function makeReadonly(AS_val)
{
    if(AS_val=="A")
    {
        document.getElementById("txtEmpID").readOnly=true;
        document.getElementById("txtsub_Amount").readOnly=true;
        document.getElementById("txtAuq_Particular").readOnly=true;
    }
    else
    {
        
        document.getElementById("txtEmpID").readOnly=false;
        document.getElementById("txtsub_Amount").readOnly=false;
        document.getElementById("txtAuq_Particular").readOnly=false;
    }

}

/////////////////////////////////////////////   ADD & UPDATE & DELETE PAYWISE Details voucher /////////////////////////////////////////////////////
function ADD_GRID_Acq()
{
    
       var main_tbody=document.getElementById("grid_body_PAYWISE");
       var Acq_tbody=document.getElementById("grid_body_Acq");
       
        if(document.getElementById("txtAcq_Voucher_No").value.length==0 || document.getElementById("txtAcq_Voucher_No").value==0)
        {
            alert("Enter Voucher SL. No");
            document.getElementById("txtAcq_Voucher_No").focus();
            return false;
        }
        if(document.getElementById("txtAcq_roll_No").value.length==0 || document.getElementById("txtAcq_roll_No").value==0)
        {
            alert("Enter Acq. roll number");
            document.getElementById("txtAcq_roll_No").focus();
            return false;
        }
        //alert(main_tbody.rows.length)
        if(main_tbody.rows.length<=0)
        {
            alert("Enter the General part first in voucher Details");
            return false;
        }
        if(main_tbody.rows.length>0)
        {
            //alert(main_tbody.rows.length)
            var rows=main_tbody.getElementsByTagName("tr");
            var count=0;
            //alert(rows.length);
            for(i=0;i<rows.length;i++)
            {
                //alert(i);
                //alert('here'+rows.length)
                 var cells=rows[i].cells;
                 var Acq_Vrnumber=document.getElementById("txtAcq_Voucher_No").value;
                 //alert(cells.item(1).firstChild.value);
                 var General_Vrnumber=cells.item(2).firstChild.value;
                 if(parseInt(Acq_Vrnumber)==parseInt(General_Vrnumber))
                 {
                    count=count+1;
                    //alert(cells.item(5).firstChild.value+'vv')
                    if(parseInt(cells.item(1).firstChild.value)==0)
                    {
                        alert("Acquittance roll can't create,bcoz Acq. roll is zero ");
                        document.getElementById("txtAcq_Voucher_No").value="";
                        document.getElementById("txtAcq_roll_No").value="";
                        return;
                    }
                    if(parseInt(document.getElementById("txtAcq_roll_No").value)!=parseInt(cells.item(1).firstChild.value))
                    {
                        alert("Acquittance roll number not valid");
                        document.getElementById("txtAcq_roll_No").value="";
                        return;
                    }
                 }
                 
            }
            if(count==0)
             {
                alert("Voucher SL. Number doesn't exist in General part")
                document.getElementById("txtAcq_Voucher_No").focus();
                return false;
             }
        
        }
        if(document.frmSelfCheque_Create.radAllSpec[0].checked==true)
        {
            alert("select Specific Employee option to add");
            return false;
        }
        
        if(document.getElementById("txtoffid").value.length==0 || document.getElementById("txtoffid").value==0)
        {
        alert("Enter Office code");
        document.getElementById("txtoffid").focus();
        return false;
        }
        if(document.getElementById("txtEmpID").value.length==0 || document.getElementById("txtEmpID").value==0)
        {
        alert("Enter Employee code");
        document.getElementById("txtEmpID").focus();
        return false;
        }
        if(document.getElementById("txtsub_Amount").value.length==0)
        {
            alert("Enter the Amount ");
            document.getElementById("txtsub_Amount").focus();
            return false;    
        }
        
        var items=new Array();
        
        items[0]=document.getElementById("txtAcq_roll_No").value;
        items[1]=document.getElementById("txtAcq_Voucher_No").value;
        items[2]=document.getElementById("txtoffid").value;
        items[3]=document.getElementById("txtOfficeName").value;
        items[4]=document.getElementById("txtEmpID").value;
        items[5]=document.getElementById("txtempName").value;
        items[6]=document.getElementById("txtsub_Amount").value;
        items[7]=document.getElementById("txtAuq_Particular").value;
        
        
        var mycurrent_row=document.createElement("TR");
        seq_toLoad_Acq=parseInt(seq_toLoad_Acq)+1;
        mycurrent_row.id=seq_toLoad_Acq;
        //alert("row ID"+mycurrent_row.id);
        var cell=document.createElement("TD");
        var anc=document.createElement("A");
        var url="javascript:loadTable_Acq('"+mycurrent_row.id+"')";
        anc.href=url;
        var txtedit=document.createTextNode("EDIT");
        anc.appendChild(txtedit);
        cell.appendChild(anc);
        mycurrent_row.appendChild(cell);
      
        var cell2;
      
            
                  
              cell2=document.createElement("TD");

                  var Acq_rollNo=document.createElement("input");
                  Acq_rollNo.type="hidden";
                  Acq_rollNo.name="Acq_rollNo";
                  Acq_rollNo.value=items[0];
                  cell2.appendChild(Acq_rollNo);
                  var currentText=document.createTextNode(items[0]);
                  cell2.appendChild(currentText);
                  mycurrent_row.appendChild(cell2);
           
             cell2=document.createElement("TD");

                  var Acq_vouNo=document.createElement("input");
                  Acq_vouNo.type="hidden";
                  Acq_vouNo.name="Acq_vouNo";
                  Acq_vouNo.value=items[1];
                  cell2.appendChild(Acq_vouNo);
                  var currentText=document.createTextNode(items[1]);
                  cell2.appendChild(currentText);
                  mycurrent_row.appendChild(cell2);
                  
              cell2=document.createElement("TD");
                   var Acq_offID=document.createElement("input");
                  Acq_offID.type="hidden";
                  Acq_offID.name="Acq_offID";
                  Acq_offID.value=items[2];
                  cell2.appendChild(Acq_offID);
                   var currentText=document.createTextNode(items[3]);
                  cell2.appendChild(currentText);
                   mycurrent_row.appendChild(cell2);
                   
             cell2=document.createElement("TD");
                  var Acq_empID=document.createElement("input");
                  Acq_empID.type="hidden";
                  Acq_empID.name="Acq_empID";
                  Acq_empID.value=items[4];
                  cell2.appendChild(Acq_empID);
                   var currentText=document.createTextNode(items[5]);
                  cell2.appendChild(currentText);
                mycurrent_row.appendChild(cell2);
                
             cell2=document.createElement("TD");
	              var Acq_Amount=document.createElement("input");
	              Acq_Amount.type="text";
	              Acq_Amount.name="Acq_Amount";
	              Acq_Amount.value=items[6];
	              if (window.navigator.appName.toLowerCase().indexOf("microsoft") > -1)         // IE;
	              { 
	                      Acq_Amount.onkeypress=function()
	                      {
	                         return limit_amt(this,event);
	                      }
	                      Acq_Amount.onblur=function()
	                      {
	                         valid_amt(this);
	                      }
	              }
	              else
	              {
	                  if (window.navigator.appName.toLowerCase().indexOf("netscape") > -1) // Firefox
	                  { 
	                            Acq_Amount.setAttribute("onkeypress","return limit_amt(this,event)");
	                            Acq_Amount.setAttribute("onblur","valid_amt(this)");
	                  }
	                  else
	                    alert("Error: This application does not support your browser.  Try again using IE or Firefox.");
	                }
	              cell2.appendChild(Acq_Amount);
                mycurrent_row.appendChild(cell2);
                    
                cell2=document.createElement("TD");
                var Acq_particular=document.createElement("input");
                Acq_particular.type="text";
                Acq_particular.name="Acq_particular";
                Acq_particular.value=items[7];
                if(window.navigator.appName.toLowerCase().indexOf("microsoft") > -1)         // IE;
                { 
                    Acq_particular.onkeypress=function()
                    {
                         return check_leng(this.value);
                    }
                }
                else
                {
                    if (window.navigator.appName.toLowerCase().indexOf("netscape") > -1) // Firefox
                    { 
                        Acq_particular.setAttribute("onkeypress","return check_leng(this.value)");
                    }
                    else
                        alert("Error: This application does not support your browser.  Try again using IE or Firefox.");
                }
                cell2.appendChild(Acq_particular);
                mycurrent_row.appendChild(cell2);
                
        Acq_tbody.appendChild(mycurrent_row);
        clearall_Acq_byADD();
        
       /*  var Abstract_tbody=document.getElementById("grid_body_Acq_abstract");
         if(Abstract_tbody.rows.length>0)
         {
                var total_length=Abstract_tbody.rows.length;
                for(i=0;i<)
                {
                    var Abs_rows=Abstract_tbody.rows;
                    var Abs_row_cells=Abs_rows
                }
         }
        */
}


function update_GRID_Acq()
{
      var main_tbody=document.getElementById("grid_body_PAYWISE");
       var Acq_tbody=document.getElementById("grid_body_Acq");
       
        if(document.getElementById("txtAcq_Voucher_No").value.length==0 || document.getElementById("txtAcq_Voucher_No").value==0)
        {
            alert("Enter Voucher SL. No");
            document.getElementById("txtAcq_Voucher_No").focus();
            return false;
        }
        if(document.getElementById("txtAcq_roll_No").value.length==0 || document.getElementById("txtAcq_roll_No").value==0)
        {
            alert("Enter Acq. roll number");
            document.getElementById("txtAcq_roll_No").focus();
            return false;
        }
        //alert(main_tbody.rows.length)
        if(main_tbody.rows.length<=0)
        {
            alert("Enter the General part first in voucher Details");
            return false;
        }
        if(main_tbody.rows.length>0)
        {
            //alert(main_tbody.rows.length)
            var rows=main_tbody.getElementsByTagName("tr");
            var count=0;
            //alert(rows.length);
            for(i=0;i<rows.length;i++)
            {
                //alert(i);
                //alert('here'+rows.length)
                 var cells=rows[i].cells;
                 var Acq_Vrnumber=document.getElementById("txtAcq_Voucher_No").value;
                 //alert(cells.item(1).firstChild.value);
                 var General_Vrnumber=cells.item(2).firstChild.value;
                 if(parseInt(Acq_Vrnumber)==parseInt(General_Vrnumber))
                 {
                    count=count+1;
                    //alert(cells.item(5).firstChild.value+'vv')
                    if(parseInt(cells.item(1).firstChild.value)==0)
                    {
                        alert("Acquittance roll can't create,bcoz Acq. roll is zero ");
                        document.getElementById("txtAcq_Voucher_No").value="";
                        document.getElementById("txtAcq_roll_No").value="";
                        return;
                    }
                    if(parseInt(document.getElementById("txtAcq_roll_No").value)!=parseInt(cells.item(1).firstChild.value))
                    {
                        alert("Acquittance roll number not valid");
                        document.getElementById("txtAcq_roll_No").value="";
                        return;
                    }
                 }
                 
            }
            if(count==0)
             {
                alert("Voucher SL. Number doesn't exist in General part")
                document.getElementById("txtAcq_Voucher_No").focus();
                return false;
             }
        
        }
        if(document.getElementById("txtoffid").value.length==0 || document.getElementById("txtoffid").value==0)
        {
        alert("Enter Office code");
        document.getElementById("txtoffid").focus();
        return false;
        }
        if(document.getElementById("txtEmpID").value.length==0 || document.getElementById("txtEmpID").value==0)
        {
        alert("Enter Employee code");
        document.getElementById("txtEmpID").focus();
        return false;
        }
        if(document.getElementById("txtsub_Amount").value.length==0)
        {
            alert("Enter the Amount ");
            document.getElementById("txtsub_Amount").focus();
            return false;    
        }
        
        var items=new Array();        
        items[0]=document.getElementById("txtAcq_roll_No").value;
        items[1]=document.getElementById("txtAcq_Voucher_No").value;
        items[2]=document.getElementById("txtoffid").value;
        items[3]=document.getElementById("txtOfficeName").value;
        items[4]=document.getElementById("txtEmpID").value;
        items[5]=document.getElementById("txtempName").value;
        items[6]=document.getElementById("txtsub_Amount").value;
        items[7]=document.getElementById("txtAuq_Particular").value;
        
        var r=document.getElementById(com_Acq);
        var rcells=r.cells;
        
        rcells.item(1).firstChild.value=items[0];
        rcells.item(1).lastChild.nodeValue=items[0];
        rcells.item(2).firstChild.value=items[1];
        rcells.item(2).lastChild.nodeValue=items[1];
        rcells.item(3).firstChild.value=items[2];
        rcells.item(3).lastChild.nodeValue=items[3];
        rcells.item(4).firstChild.value=items[4];
        rcells.item(4).lastChild.nodeValue=items[5];
        rcells.item(5).firstChild.value=items[6];
        rcells.item(5).lastChild.nodeValue=items[6];
        rcells.item(6).firstChild.value=items[7];
        rcells.item(6).lastChild.nodeValue=items[7];

        clearall_Acq();
}

function loadTable_Acq(scod_acq)
{
        com_Acq=scod_acq;                                    // to identify in UPDATE_GRID ,which row loaded 
        clearall_Acq();
     
        var r=document.getElementById(scod_acq);
        var rcells=r.cells;
        try{document.getElementById("txtAcq_roll_No").value=rcells.item(1).firstChild.value;}catch(e){}
        try{document.getElementById("txtAcq_Voucher_No").value=rcells.item(2).firstChild.value;}catch(e){}
        try{document.getElementById("txtoffid").value=rcells.item(3).firstChild.value;}catch(e){}
        try{document.getElementById("txtOfficeName").value=rcells.item(3).lastChild.nodeValue;}catch(e){}
        try{document.getElementById("txtEmpID").value=rcells.item(4).firstChild.value;}catch(e){}
        try{document.getElementById("txtempName").value=rcells.item(4).lastChild.nodeValue;}catch(e){}
        try{document.getElementById("txtsub_Amount").value=rcells.item(5).firstChild.value;}catch(e){}
        try{document.getElementById("txtAuq_Particular").value=rcells.item(6).firstChild.value;}catch(e){}
       
    document.frmSelfCheque_Create.cmdupdate_Acq.style.display='block';
    document.frmSelfCheque_Create.cmddelete_Acq.disabled=false;
    document.frmSelfCheque_Create.cmdadd_Acq.style.display='none';
}


function delete_GRID_Acq()
{
        if(confirm("Do you want to delete ?"))
        {
            var tbody=document.getElementById("mytable_Acq");
            var r=document.getElementById(com_Acq);
            var ri=r.rowIndex;
            tbody.deleteRow(ri);
            clearall_Acq();
        }
}
function clearall_Acq_byADD()           // in case of specific selected
{
    document.getElementById("txtEmpID").value="";
    document.getElementById("txtempName").value="";
    document.getElementById("txtsub_Amount").value="";
    document.getElementById("txtAuq_Particular").value="";
     document.frmSelfCheque_Create.cmdadd_Acq.style.display='block';
     document.frmSelfCheque_Create.cmdupdate_Acq.style.display='none';
     document.frmSelfCheque_Create.cmddelete_Acq.disabled=true;
}
function clearall_Acq()
{
    document.getElementById("txtAcq_Voucher_No").value="";
    document.getElementById("txtAcq_roll_No").value="";
    document.getElementById("txtoffid").value="";
    document.getElementById("txtOfficeName").value="";
    document.getElementById("txtEmpID").value="";
    document.getElementById("txtempName").value="";
    document.getElementById("txtsub_Amount").value="";
    document.getElementById("txtAuq_Particular").value="";

     document.frmSelfCheque_Create.cmdadd_Acq.style.display='block';
     document.frmSelfCheque_Create.cmdupdate_Acq.style.display='none';
     document.frmSelfCheque_Create.cmddelete_Acq.disabled=true;

}

//////////////////////////////////////////////////////////////////  null checking Validation

function checkNull()
{
        if(document.getElementById("cmbAcc_UnitCode").value=="")
        {
            alert("Select the Account Unit code");
            //document.getElementById("txtAcc_HeadDesc").focus();
            return false;    
        }
        if(document.getElementById("cmbOffice_code").value=="")
        {
            alert("Select the Office Code");
            //document.getElementById("cmbOffice_code").focus();
            return false;
        }
        if(document.getElementById("txtCrea_date").value.length==0)
        {
            alert("Enter the Date of Creation");
            //document.getElementById("txtCrea_date").focus();
            return false;    
        }
        if(document.getElementById("txtCash_Acc_code").value.length==0 || document.getElementById("txtCash_Acc_code").value==0)
        {
            alert("Enter the Operation  A/c Code");
            //document.getElementById("txtCash_Acc_code").focus();
            return false;
        }
        
        if(document.getElementById("txtBankId").value.length==0 || document.getElementById("txtBankId").value==0)
        {
            alert("Bank Id not populated in General");
            //document.getElementById("txtAmount").focus();
            return false;    
        }
        
        if(document.getElementById("txtBranchId").value.length==0 || document.getElementById("txtBranchId").value==0)
        {
            alert("Branch Id not populated in General");
            //document.getElementById("txtAmount").focus();
            return false;    
        }
        
        if(document.getElementById("txtBankAccountNo").value.length==0 || document.getElementById("txtBankAccountNo").value==0)
        {
            alert("Enter the Account Number");
            //document.getElementById("txtRecei_from").focus();
            return false;    
        }
        if(document.getElementById("txtCheque_DD_NO").value.length==0)
        {
             alert("Enter the Cheque  number");
             //document.getElementById("txtCheque_DD_NO").focus();
          return false;
        }
        if(document.getElementById("txtCheque_DD_date").value.length==0)
        {
             alert("Enter the Cheque  Date");
             //document.getElementById("txtCheque_DD_date").focus();
            return false;
        }
        if(document.getElementById("txtAmount").value.length==0)
        {
            alert("Enter the Total Amount in General");
            //document.getElementById("txtAmount").focus();
            return false;    
        }
        
         var tbody_PAYWISE=document.getElementById("grid_body_PAYWISE");
         var tbody_PAYWISE_DETAILS=document.getElementById("grid_body_PAYWISE_DETAILS");
         var tbody_Acq=document.getElementById("grid_body_Acq");
         //var rows=main_tbody.getElementsByTagName("tr");
         //alert(tbody_PAYWISE.rows.length);
         if(tbody_PAYWISE.rows.length<=0)
         {
            alert("General part in 'Voucher Details' tab is empty");
            return false;
         }
         if(tbody_PAYWISE_DETAILS.rows.length<=0)
         {
            alert("Details part in 'Voucher Details' tab is empty");
            return false;
         }
         if(tbody_PAYWISE.rows.length>0)
         {
            var General_Vr_amt=0;
            var rows=tbody_PAYWISE.getElementsByTagName("tr");
            //var count=0;
            for(i=0;i<rows.length;i++)
            {
                //alert(i);
                //alert('here'+rows.length)
                 var cells=rows[i].cells;
                 //var Details_Vrnumber=document.getElementById("txtpayDetails_Voucher_No").value;
                 //alert(cells.item(1).firstChild.value);
                 General_Vr_amt=parseFloat(General_Vr_amt)+parseFloat(cells.item(3).firstChild.value);
                 //if(parseInt(Details_Vrnumber)==parseInt(General_Vrnumber))
                 //count=count+1;
                 
            }
          
            if(parseFloat(document.getElementById("txtAmount").value)!=parseFloat(General_Vr_amt))
            {
                 alert("Total Amount doesn't Tally.. Difference " +(parseFloat(document.getElementById("txtAmount").value)-parseFloat(General_Vr_amt)))
                 return false;
            }
         }
         if(tbody_PAYWISE.rows.length>0)        // for Details part of Voucher
         {
            var General_Vrnumber;
            var Details_Vrnumber;
            var General_Vr_amt=0;
            var Details_Vr_amt=0;
            var rows=tbody_PAYWISE.getElementsByTagName("tr");
            //var count=0;
            for(i=0;i<rows.length;i++)
            {
                //alert(i);
                //alert('here'+rows.length)
                 var cells=rows[i].cells;
                 //var Details_Vrnumber=document.getElementById("txtpayDetails_Voucher_No").value;
                 //alert(cells.item(1).firstChild.value);
                 General_Vrnumber=cells.item(2).firstChild.value;           // Get general part Vr. number
                 General_Vr_amt=cells.item(3).firstChild.value;             // Get general part Vr. Amount
                 
                 var details_rows=tbody_PAYWISE_DETAILS.getElementsByTagName("tr");
                 for(j=0;j<details_rows.length;j++)
                 {
                     //alert(details_rows.length);
                     var details_cells=details_rows[j].cells;
                     //alert('he');
                     //alert(details_cells);
                    // alert(details_cells.item(1).firstChild.value);
                     Details_Vrnumber=details_cells.item(1).firstChild.value;               // Get details Vr. number
                     if(parseInt(Details_Vrnumber)==parseInt(General_Vrnumber))             // check general part Vr. number==details Vr. number
                     {
                        Details_Vr_amt=parseFloat(Details_Vr_amt)+parseFloat(details_cells.item(5).firstChild.value);
                     }
                 }
                 
                 if(parseFloat(General_Vr_amt)!=parseFloat(Details_Vr_amt))
                 {
                     alert("Amount doesn't Tally..Difference "+(parseFloat(General_Vr_amt)-parseFloat(Details_Vr_amt))+" for Vr No..."+General_Vrnumber+"..in Voucher Deatils " )
                     return false;
                 }
                 General_Vr_amt=0;
                 Details_Vr_amt=0;
                 //count=count+1;
                 
            }
           
         }
         
         if(tbody_PAYWISE.rows.length>0)        // for acquittance part
         {
            var General_Vrnumber;
            var Acq_Vrnumber;
            var General_Vr_amt=0;
            var Acq_Vr_amt=0;
            var rows=tbody_PAYWISE.getElementsByTagName("tr");
            //var count=0;
           /* for(i=0;i<rows.length;i++)                  // Iteration of each voucher
            {
                //alert(i);
                //alert('here'+rows.length)
                 var cells=rows[i].cells;
                 //var Acq_Vrnumber=document.getElementById("txtpayDetails_Voucher_No").value;
                 //alert(cells.item(1).firstChild.value);
                 General_Vrnumber=cells.item(2).firstChild.value;
                 
                 if(parseInt(cells.item(1).firstChild.value)>0)                     //if() gets true in case that Voucher has acquittance
                 {
                    var No_of_Acq_rolls=cells.item(5).firstChild.value;
                    var Acq_rows=tbody_Acq.getElementsByTagName("tr");
                    for(n=1;n<=No_of_Acq_rolls;n++)
                    {
                        var count_acq=0;
                        for(j=0;j<Acq_rows.length;j++)         // checking all acq. number's present or not in acq.details part for particular voucher
                        {
                              var Acq_cells=Acq_rows[j].cells;
                              if(parseInt(Acq_cells.item(1).firstChild.value)==parseInt(General_Vrnumber))
                              {
                                get_acq_No=Acq_cells.item(2).firstChild.value;
                                // if a voucher No 1  has No. of Acq.rolls 3 means , those Vr. must have the same no.of acq.roll  in acquittance details part
                                // as vr.no-1,Acq.roll no-1 and vr.no-1,Acq.roll no-2 and vr.no-1,Acq.roll no-3..
                                // This loop and if checks each pair is available or not , if available it count_acq added with 1
                                if(parseInt(get_acq_No)==parseInt(n))
                                count_acq=parseInt(count_acq)+1;                  
                              }
                        }
                        if(parseInt(count_acq)==0)      // If count_acq is 0 means that Vr has missing any one of the acq. roll no
                        {
                            alert("Acq. roll number missing in Acquittance Details part for Vr. no.."+General_Vrnumber);
                            return false;
                        }
                        count_acq=0;
                    }
                 }
            }*/
            
            for(i=0;i<rows.length;i++)
            {
                //alert(i);
                //alert('here'+rows.length)
                 var cells=rows[i].cells;
                 //var Acq_Vrnumber=document.getElementById("txtpayDetails_Voucher_No").value;
                 //alert(cells.item(1).firstChild.value);
                 General_Vrnumber=cells.item(2).firstChild.value;        // Get general part Vr. number
                 General_Vr_amt=cells.item(3).firstChild.value;          // Get general part Vr. amount
                 if(parseInt(cells.item(1).firstChild.value)>0)
                 {
                     //var count_acq=cells.item(5).firstChild.value;
                     var Acq_rows=tbody_Acq.getElementsByTagName("tr");
                     for(k=0;k<Acq_rows.length;k++)
                     {
                         var Acq_cells=Acq_rows[k].cells;
                         Acq_Vrnumber=Acq_cells.item(2).firstChild.value;
                         if(parseInt(Acq_Vrnumber)==parseInt(General_Vrnumber))      // check general part Vr. number==Acq. part Vr. number
                         {
                            Acq_Vr_amt=parseFloat(Acq_Vr_amt)+parseFloat(Acq_cells.item(5).firstChild.value);
                         }
                     }
                  
                    if(isNaN(Acq_Vr_amt))
                    {
                        alert("Some of the Amount field(s) are empty in Acq. Details grid");
                        return false;
                    }
                     if(parseFloat(General_Vr_amt)!=parseFloat(Acq_Vr_amt))
                     {
                         alert("Amount doesn't Tally..Difference "+(parseFloat(General_Vr_amt)-parseFloat(Acq_Vr_amt))+" for Vr No.."+General_Vrnumber+"..in Acq. Deatils " )
                         return false;
                     }
                     General_Vr_amt=0;
                     Acq_Vr_amt=0;
                 }
                 
                 //count=count+1;
            }
           
         }
     
     return true;      
}


function acq_valid(acq_value)
{
        var main_tbody=document.getElementById("grid_body_PAYWISE");
        if(main_tbody.rows.length<=0)
        {
            alert("Enter the General part first in voucher Details");
            return false;
        }
        if(main_tbody.rows.length>0)
        {
            //alert(main_tbody.rows.length)
            var rows=main_tbody.getElementsByTagName("tr");
            var count=0;
            //alert(rows.length);
            for(i=0;i<rows.length;i++)
            {
                //alert(i);
                //alert('here'+rows.length)
                 var cells=rows[i].cells;
                 var Acq_Vrnumber=document.getElementById("txtAcq_Voucher_No").value;
                 //alert(cells.item(1).firstChild.value);
                 var General_Vrnumber=cells.item(2).firstChild.value;
                 if(parseInt(Acq_Vrnumber)==parseInt(General_Vrnumber))
                 {
                    count=count+1;
                    //alert(cells.item(5).firstChild.value+'vv')
                    if(parseInt(cells.item(1).firstChild.value)==0)
                    {
                        alert("Acquittance roll can't create,bcoz No.of acq. roll is zero ");
                        document.getElementById("txtAcq_Voucher_No").value="";
                        document.getElementById("txtAcq_roll_No").value="";
                        return;
                    }
                    if(parseInt(document.getElementById("txtAcq_roll_No").value)!=parseInt(cells.item(1).firstChild.value))
                    {
                        alert("Acquittance roll number not valid");
                        document.getElementById("txtAcq_roll_No").value="";
                        return;
                    }
                 }
                 
            }
            if(count==0)
             {
                alert("Voucher SL. Number doesn't exist in General part")
                document.getElementById("txtAcq_Voucher_No").focus();
                return false;
             }
        
        }   
}


/////////////////////////////////////////////////  Acquittance Tab related fields


function office_emp(Command,param)
{
    
    if(Command=="loadoffice")
    {
        var Office_code=param;
        document.getElementById("txtEmpID").value="";
        if(Office_code!="")
        {
            if(document.frmSelfCheque_Create.radAllSpec[0].checked==true)
                var url="../../../../../SelfCheque_Create.view?Command=loadOfficeEmp&Office_code="+Office_code;
            else
                var url="../../../../../Receipt_SL.view?Command=loadoffice&Office_code="+Office_code;
            //alert(url);
            var req=getTransport();
            req.open("GET",url,false); 
            req.onreadystatechange=function()
            {
               handleResponse_office_emp(req);
            }   
                    req.send(null);
        }          
    }
    else if(Command=="loadEmployee")
    {
        var Office_code=document.getElementById("txtoffid").value;
        var employeeID=param;
        if(employeeID!="")
        {
            if(Office_code=="")
            {
                alert("Enter the office code ");
                document.getElementById("txtEmpID").value="";
                document.getElementById("txtoffid").focus();
                return;
            }
            var url="../../../../../Receipt_SL.view?Command=loadEmployee&Office_code="+Office_code+"&employeeID="+employeeID;
            //alert(url);
            var req=getTransport();
            req.open("GET",url,false); 
            req.onreadystatechange=function()
            {
               handleResponse_office_emp(req);
            }   
                    req.send(null);
        }   
    }
}


function hideRecoup()
{
	var acc_headCode=document.getElementById("txtAcc_HeadCode").value;
	if(acc_headCode=="820102")
	{
		var rId=document.getElementById("recoupId");
		rId.style.display="none";
		var nId=document.getElementById("newId");
		nId.style.display="block";
	}
	else
	{
		var rId=document.getElementById("recoupId");
		rId.style.display="block";
		var nId=document.getElementById("newId");
		nId.style.display="block";
	}
}

function loaddrawlPurpose(accCode)
{		
	//setTimeout("callmm("+accCode+");",900);
	
	//alert("accCode=========>"+accCode);
	if(accCode==820103)
	{
		mode="I";
	}
	else if(accCode==820102)
	{
		mode="T";
	}
 
    var drawl_purpose=document.getElementById("drawl_purpose");
    var child=drawl_purpose.childNodes;
    for(var c=child.length-1;c>1;c--)
    {
    	   drawl_purpose.removeChild(child[c]);
    }
  
   var url="../../../../../SelfCheque_Create.view?Command=loaddrawlPurpose&accCode="+mode;
  // alert("URL====>"+url);
    var req=getTransport();
    req.open("GET",url,true); 
    req.onreadystatechange=function()
    {
    	   drawlPurpose_handleResponse1(req);
    }   
    req.send(null); 
	
}

function drawlPurpose_handleResponse1(req)
{
	    if(req.readyState==4)
	    {
			       if(req.status==200)
			       {  
			    	   	   var baseResponse=req.responseXML.getElementsByTagName("response")[0];	
				          
				           var flag=baseResponse.getElementsByTagName("flag")[0].firstChild.nodeValue;
                         //  alert("flagggggggg"+flag);
				           if(flag=="success")
				           {
				        	   	   var rec=baseResponse.getElementsByTagName("wd_purpose_id");
				        	   	   var drawl_purpose=document.getElementById("drawl_purpose");
				        	   	   for(var i=0;i<rec.length;i++)
				        	   	   {
				        	   		   		var code=baseResponse.getElementsByTagName("wd_purpose_id")[i].firstChild.nodeValue;
				        	   		   		var desc=baseResponse.getElementsByTagName("wd_purpose_desc")[i].firstChild.nodeValue;
					        	   		   	var opt=document.createElement("option");
			                                opt.setAttribute("value",code);
			                                var opttext=document.createTextNode(desc);
			                                opt.appendChild(opttext);
			                                drawl_purpose.appendChild(opt);
				        	   	   }
				           }
			       }
	    }
}

function handleResponse_office_emp(req)
{
    if(req.readyState==4)
    { 
        if(req.status==200)
        {  
            var baseResponse=req.responseXML.getElementsByTagName("response")[0];
            var tagcommand=baseResponse.getElementsByTagName("command")[0];
            var Command=tagcommand.firstChild.nodeValue;
           
            if(Command=="loadoffice" || Command=="loadOfficeEmp")
            {
                loadoffice(baseResponse);
            }
            else if(Command=="loadEmployee")
            {
                loadEmployee(baseResponse);
            }
        }
    }
}
function loadoffice(baseResponse)
{
    var flag=baseResponse.getElementsByTagName("flag")[0].firstChild.nodeValue;
    
    if(flag=="success")
    {
        document.getElementById("txtOfficeName").value=baseResponse.getElementsByTagName("offname")[0].firstChild.nodeValue;
         if(document.frmSelfCheque_Create.radAllSpec[0].checked==true)
         {
            if(window.confirm("You have selected ALL option, \n so it's going to load ALL employees of the given office,\n Do you want to load it?"))
            {
                var Acq_tbody=document.getElementById("grid_body_Acq");
               
                var items=new Array();
                items[0]=document.getElementById("txtAcq_roll_No").value;
                items[1]=document.getElementById("txtAcq_Voucher_No").value;
                
                items[2]=document.getElementById("txtoffid").value;
                items[3]=document.getElementById("txtOfficeName").value;
               
                items[6]="";
                items[7]="";
                var EMPid_temp=baseResponse.getElementsByTagName("EMPid");
                for(var y=0;y<EMPid_temp.length;y++)
                {
                        items[4]=baseResponse.getElementsByTagName("EMPid")[y].firstChild.nodeValue;
                        items[5]=baseResponse.getElementsByTagName("EMPname")[y].firstChild.nodeValue;

                        var mycurrent_row=document.createElement("TR");
                        seq_toLoad_Acq=parseInt(seq_toLoad_Acq)+1;
                        mycurrent_row.id=seq_toLoad_Acq;
                        //alert("row ID"+mycurrent_row.id);
                        var cell=document.createElement("TD");
                        var anc=document.createElement("A");
                        var url="javascript:loadTable_Acq('"+mycurrent_row.id+"')";
                        anc.href=url;
                        var txtedit=document.createTextNode("EDIT");
                        anc.appendChild(txtedit);
                        cell.appendChild(anc);
                        mycurrent_row.appendChild(cell);
                      
                        var cell2;
                      
                             cell2=document.createElement("TD");
                
                                  var Acq_rollNo=document.createElement("input");
                                  Acq_rollNo.type="hidden";
                                  Acq_rollNo.name="Acq_rollNo";
                                  Acq_rollNo.value=items[0];
                                  cell2.appendChild(Acq_rollNo);
                                  var currentText=document.createTextNode(items[0]);
                                  cell2.appendChild(currentText);
                                  mycurrent_row.appendChild(cell2);
                            
                             cell2=document.createElement("TD");
                
                                  var Acq_vouNo=document.createElement("input");
                                  Acq_vouNo.type="hidden";
                                  Acq_vouNo.name="Acq_vouNo";
                                  Acq_vouNo.value=items[1];
                                  cell2.appendChild(Acq_vouNo);
                                  var currentText=document.createTextNode(items[1]);
                                  cell2.appendChild(currentText);
                                  mycurrent_row.appendChild(cell2);
                                  
                             
                              cell2=document.createElement("TD");
                                   var Acq_offID=document.createElement("input");
                                  Acq_offID.type="hidden";
                                  Acq_offID.name="Acq_offID";
                                  Acq_offID.value=items[2];
                                  cell2.appendChild(Acq_offID);
                                   var currentText=document.createTextNode(items[3]);
                                  cell2.appendChild(currentText);
                                   mycurrent_row.appendChild(cell2);
                                   
                             cell2=document.createElement("TD");
                                  var Acq_empID=document.createElement("input");
                                  Acq_empID.type="hidden";
                                  Acq_empID.name="Acq_empID";
                                  Acq_empID.value=items[4];
                                  cell2.appendChild(Acq_empID);
                                   var currentText=document.createTextNode(items[5]);
                                  cell2.appendChild(currentText);
                                mycurrent_row.appendChild(cell2);
                                
                            cell2=document.createElement("TD");
                                  var Acq_Amount=document.createElement("input");
                                  Acq_Amount.type="text";
                                  Acq_Amount.name="Acq_Amount";
                                  Acq_Amount.value=items[6];
                                  if (window.navigator.appName.toLowerCase().indexOf("microsoft") > -1)         // IE;
                                  { 
                                          Acq_Amount.onkeypress=function()
                                          {
                                             return limit_amt(this,event);
                                          }
                                          Acq_Amount.onblur=function()
                                          {
                                             valid_amt(this);
                                          }
                                  }
                                  else
                                  {
                                      if (window.navigator.appName.toLowerCase().indexOf("netscape") > -1) // Firefox
                                      { 
                                                Acq_Amount.setAttribute("onkeypress","return limit_amt(this,event)");
                                                Acq_Amount.setAttribute("onblur","valid_amt(this)");
                                      }
                                      else
                                        alert("Error: This application does not support your browser.  Try again using IE or Firefox.");
                                    }
                                  cell2.appendChild(Acq_Amount);
                                mycurrent_row.appendChild(cell2);
                            
                            cell2=document.createElement("TD");
                                  var Acq_particular=document.createElement("input");
                                  Acq_particular.type="text";
                                  Acq_particular.name="Acq_particular";
                                  Acq_particular.value=items[7];
                                  if(window.navigator.appName.toLowerCase().indexOf("microsoft") > -1)         // IE;
                                  { 
                                          Acq_particular.onkeypress=function()
                                          {
                                             return check_leng(this.value);
                                          }
                                  }
                                  else
                                  {
                                      if (window.navigator.appName.toLowerCase().indexOf("netscape") > -1) // Firefox
                                      { 
                                                Acq_particular.setAttribute("onkeypress","return check_leng(this.value)");
                                      }
                                      else
                                        alert("Error: This application does not support your browser.  Try again using IE or Firefox.");
                                    }
                                  cell2.appendChild(Acq_particular);
                                  mycurrent_row.appendChild(cell2);
                             Acq_tbody.appendChild(mycurrent_row);
                    }
                }
                
         }
    }
    else if(flag=="failure")
     {
         alert("Office Code '"+baseResponse.getElementsByTagName("offid")[0].firstChild.nodeValue+"' doesn't Exist");
         document.getElementById("txtoffid").value="";
         document.getElementById("txtOfficeName").value="";
         document.getElementById("txtoffid").focus();
     }
}

function loadEmployee(baseResponse)
{
    var flag=baseResponse.getElementsByTagName("flag")[0].firstChild.nodeValue;
    
    if(flag=="success")
    {
        document.getElementById("txtempName").value=baseResponse.getElementsByTagName("EMPname")[0].firstChild.nodeValue;
    }
    else if(flag=="failure")
     {
         alert("Employee Code '"+baseResponse.getElementsByTagName("empid")[0].firstChild.nodeValue+"' doesn't Exist under this office");
         document.getElementById("txtEmpID").value="";
         document.getElementById("txtempName").value="";
         document.getElementById("txtEmpID").focus();
     }
}


///////////////////////////////////////////////////////////////////////////////// 
// Checking Cheque/DD Number. Whether already exits or not  
////////////////////////////////////////////////////////////////////////////////

 function check_dd_cheque()
 {
      var cheque_no= document.getElementById("txtCheque_DD_NO").value;
      var cmbAcc_UnitCode=document.getElementById("cmbAcc_UnitCode").value;
      var url="../../../../../BankPay_PendingBill_Create.view?Command=check_cheque_no&cheque_no="+cheque_no+"&cmbAcc_UnitCode="+cmbAcc_UnitCode;
      var req=getTransport();
      req.open("GET",url,true); 
      req.onreadystatechange=function()
         {
            handleResponse_cheque_no(req);
         }   
      req.send(null); 
 }
 
function handleResponse_cheque_no(req) 
{ 
   
    if(req.readyState==4)
    {
       
        if(req.status==200)
        {  
            var baseResponse=req.responseXML.getElementsByTagName("response")[0];
            var tagcommand=baseResponse.getElementsByTagName("command")[0];
            var Command=tagcommand.firstChild.nodeValue;
            var flag=baseResponse.getElementsByTagName("flag")[0].firstChild.nodeValue;
            if(flag=="Success")
              {
              
                 var cheque_no = baseResponse.getElementsByTagName("cheq_no");   
                
                 var max=cheque_no.length;
               
                 if(max > 1 )
                    max--;
                  
                 my_window= window.open ("","mywindow1","height=150,width=420,scrollbars=1,resizable=1");
                 my_window.document.write('<html><head><title>Cheque Number Checking</title><body>');
                 my_window.document.write('<table border=1 width=380><tr><th align=center colspan=5>Cheque Number '+baseResponse.getElementsByTagName("cheq_no")[0].firstChild.nodeValue+' already exits '); 
                 my_window.document.write('<tr> <td align=center>Voucher No.</td> <td align=center>Amount</td>  <td align=center>Created by Module</td>  <td align=center>CB Year</td>  <td align=center>CB Month</td> </tr>');
                 my_window.document.write('<tr>');
                   
                 
                 for(var k=0;k<max;k++)
                 {
                   my_window.document.write('<tr><td align=center>'+baseResponse.getElementsByTagName("vo_no")[k].firstChild.nodeValue); 
                   my_window.document.write('<td align=center>'+baseResponse.getElementsByTagName("amount")[k].firstChild.nodeValue); 
                   my_window.document.write('<td align=center>'+baseResponse.getElementsByTagName("crm")[k].firstChild.nodeValue); 
                   my_window.document.write('<td align=center>'+baseResponse.getElementsByTagName("cbyear")[k].firstChild.nodeValue); 
                   my_window.document.write('<td align=center>'+baseResponse.getElementsByTagName("cbmonth")[k].firstChild.nodeValue); 
                 }
                 my_window.document.write('</table></body></html>');
                 
                 my_window.moveTo(200,250);
                 document.getElementById("txtCheque_DD_NO").value="";
                 
              }
       }
   }    
}


function checkRecoup()
{
		if(document.frmSelfCheque_Create.payment_type[0].checked==true)
				   document.getElementById("recoupDiv").style.display="none";
		else
				   document.getElementById("recoupDiv").style.display="block";
}

function loadRecoupDetails(command,param)
{
		if(document.getElementById("txtAcc_HeadCode").value==820103 || document.getElementById("txtAcc_HeadCode").value==820102)
		{
				document.getElementById("recoup_body").length=1;
				var cmbAcc_UnitCode=document.getElementById("cmbAcc_UnitCode").value;
				var cmbOffice_code=document.getElementById("cmbOffice_code").value;
				var txtCrea_date=document.getElementById("txtCrea_date").value;		
				var txtEmpID=document.getElementById("txtEmpID_trs").value;
				var created_by_module="SC";		
			    var url="../../../../../SelfCheque_Create.view?Command=loadRecoupDetails&Option=Create&cmbAcc_UnitCode="+cmbAcc_UnitCode+"&txtOfficeId="+cmbOffice_code+"&txtCrea_date="+txtCrea_date+"&txtEmpID="+txtEmpID+"&created_by_module="+created_by_module;	    
			    var req=getTransport();
			    req.open("GET",url,true); 
			    req.onreadystatechange=function()
			    {
			    		   process_handleResponse(req,command,param);
			    }   
			    req.send(null);      
		}
}


function process_handleResponse(req,cmd,param)
{
	    if(req.readyState==4)
	    {
			       if(req.status==200)
			       {  
				           var baseResponse=req.responseXML.getElementsByTagName("response")[0];
				           var tagcommand=baseResponse.getElementsByTagName("command")[0];
				           var Command=tagcommand.firstChild.nodeValue;
				           var flag=baseResponse.getElementsByTagName("flag")[0].firstChild.nodeValue;				          
				           var items=new Array();				          
				           if(flag=="success")
				           {
				        	   	   var voucher=baseResponse.getElementsByTagName("voucher_no");				        	   	  
				        	   	   var tbody=document.getElementById("recoup_body");
				        	   	   var tb=document.getElementById("grid_body_PAYWISE_DETAILS");
				        	   	 
				        	   	   try{tbody.innerHTML="";
				        	   	   }catch(e) {tbody.innerText="";
				        	   	   }
			        	   	   
				        	   	   for(var i=0;i<voucher.length;i++)
				        	   	   {
				        	   		   		items[0]=baseResponse.getElementsByTagName("voucher_no")[i].firstChild.nodeValue;
				        	   		   		items[1]=baseResponse.getElementsByTagName("payment_dt")[i].firstChild.nodeValue;
				        	   		   		items[2]=baseResponse.getElementsByTagName("amount")[i].firstChild.nodeValue;	
				        	   		   		items[3]=baseResponse.getElementsByTagName("slno")[i].firstChild.nodeValue;		
				        	   		   		var seq=i+1;
				        	   		   		
				        	   		   		
				        	   		   		var availability=false;
				        	   		   		////////////////////////////  checking with grid ////////////////////////////////////////						                    
						                    if(tb.rows.length>1)
				        	        		{
						                    		
								        	        	for(var j=0;j<tb.rows.length;)
								        	    	   	{
								        	    	   			   
								        	        				if(document.frmSelfCheque_Create.recoup_vou_no[j].value==items[0] && document.frmSelfCheque_Create.sl_no[j].value==items[4] && document.frmSelfCheque_Create.recoup_vou_dt[j].value==items[1])
								        	    	   			    {								        	    	   				 
								        	    	   			    		availability=true;
								        	    	   			    		j=tb.rows.length;       // if any one of the grid row is matching with recoup voucher number ,the loop exit, no need to check with next row in the grid
								        	    	   			    }
								        	    	   			    else
								        	    	   			    		j++;
								        	    	   	}
				        	        		}
				        	        		else if(tb.rows.length==1)
				        	        		{						        	        			
				        	        					if(document.frmSelfCheque_Create.recoup_vou_no.value==items[0] && document.frmSelfCheque_Create.sl_no.value==items[4] && document.frmSelfCheque_Create.recoup_vou_dt.value==items[1])
					        	    	   			 	{					        	    	   				 
						        	        						availability=true;   	    	   				 	 					        	    	   				 		
					        	    	   			 	}
				        	        		} 						                    
						                    //////////////////////////////////////  End  ////////////////////////////////////////////
				        	   		   		var mycurrent_row=document.createElement("TR");        
				        	   		   		var cell1 =document.createElement("TD"); 
						                    var hidden1="";
						                    if (window.navigator.appName.toLowerCase().indexOf("netscape") == -1)  // for internet explorer
						                    {
						                            	if(availability)
						                            			    hidden1=document.createElement("<input type='radio' name='sel' id='sel' value="+seq+" disabled='disabled'>");
						                            	else
						                            				hidden1=document.createElement("<input type='radio' name='sel' id='sel' value="+seq+">");
						                    }
						                    else   // for mizila
						                    {    
							                          	hidden1=document.createElement("input");    
							                          	hidden1.type="radio";             
							                          	hidden1.name="sel";
							                          	hidden1.id="sel";
							                          	if(availability)
							                          				hidden1.disabled="disabled";						                          	
							                          	hidden1.value=seq;
						                    }
						                    cell1.appendChild(hidden1);
						                    mycurrent_row.appendChild(cell1);
						                    					               
						                    var cell2 =document.createElement("TD"); 
						                    var r_voucher=document.createElement("input");
						                    r_voucher.type="hidden";
						                    r_voucher.name="recoup_voucher";
						                    r_voucher.id="recoup_voucher";
						                    r_voucher.value=items[0];
						 	               	cell2.appendChild(r_voucher);
						                    var hidden2=document.createTextNode(items[0]);                        
						                    cell2.appendChild(hidden2);  
						                    mycurrent_row.appendChild(cell2);  
						                    
						                    var cell2 =document.createElement("TD"); 
						                    var sno=document.createElement("input");
						                    sno.type="hidden";
						                    sno.name="sno";
						                    sno.id="sno";
						                    sno.value=items[3];
						 	               	cell2.appendChild(sno);
						                    var hidden2=document.createTextNode(items[3]);                        
						                    cell2.appendChild(hidden2);  
						                    mycurrent_row.appendChild(cell2);  
						                    
						                    var cell3 =document.createElement("TD");  
						                    var r_voucher_dt=document.createElement("input");
						                    r_voucher_dt.type="hidden";
						                    r_voucher_dt.name="recoup_voucher_dt";
						                    r_voucher_dt.id="recoup_voucher_dt";
						                    r_voucher_dt.value=items[1];
						                    cell3.appendChild(r_voucher_dt);
						                    var hidden3=document.createTextNode(items[1]);                        
						                    cell3.appendChild(hidden3);  
						                    mycurrent_row.appendChild(cell3);  
						                    
						                           
						                    var cell5 =document.createElement("TD");   
						                    var r_voucher_amt=document.createElement("input");
						                    r_voucher_amt.type="hidden";
						                    r_voucher_amt.name="recoup_voucher_amt";
						                    r_voucher_amt.id="recoup_voucher_amt";
						                    r_voucher_amt.value=items[2];
						                    cell5.appendChild(r_voucher_amt);
						                    var hidden5=document.createTextNode(items[2]);                        
						                    cell5.appendChild(hidden5);  
						                    mycurrent_row.appendChild(cell5);  
						                    
						                    tbody.appendChild(mycurrent_row); 
				        	   	   }
				        	   	   
				        	   	   if(cmd=="edit")
				        	   	   {
				        	   		   		/*var tbody=document.getElementById("recoup_body");
				        	   		   		var tb=document.getElementById("grid_body");	*/	
				        	   		   
				        	   		   
						        	        if(tb.rows.length==1)  // table body checking
						        	        {
						        	        	  	// Check whether recoup tbody have more than one row //
						        	        		if(tbody.rows.length>1)
						        	        		{
									        	        		for(var i=0;i<tbody.rows.length;i++)
									        	        	  	{						        	    	   			 	
									        	    	   			 	if(document.frmSelfCheque_Create.recoup_vou_no.value==document.frmSelfCheque_Create.recoup_voucher[i].value && document.frmSelfCheque_Create.sl_no.value==document.frmSelfCheque_Create.sno[i].value && document.frmSelfCheque_Create.recoup_vou_dt.value==document.frmSelfCheque_Create.recoup_voucher_dt[i].value)
									        	    	   			 	{
									        	    	   				 
									        	    	   				 	    document.frmSelfCheque_Create.sel[i].checked=true;	
									        	    	   				 	    document.frmSelfCheque_Create.sel[i].disabled=false;											        	    	   				 	   
									        	    	   				 		
									        	    	   			 	}
									        	        	  	}
									        	        	  	
						        	        		}
						        	        		else
						        	        		{
								        	        			if(document.frmSelfCheque_Create.recoup_vou_no.value==document.frmSelfCheque_Create.recoup_voucher.value && document.frmSelfCheque_Create.sl_no.value==document.frmSelfCheque_Create.sno.value && document.frmSelfCheque_Create.recoup_vou_dt.value==document.frmSelfCheque_Create.recoup_voucher_dt.value)
							        	    	   			 	{
							        	    	   				 
							        	    	   				 	    document.frmSelfCheque_Create.sel.checked=true;
							        	    	   				 	    document.frmSelfCheque_Create.sel.disabled=false;							        	    	   				 	    
							        	    	   				 		
							        	    	   			 	}
						        	        		}
						        	        		
						        	        	 		
						        	        }
						        	        else
						        	        {
							        	        	// Check whether recoup tbody have more than one row //
						        	        		if(tbody.rows.length>1)
						        	        		{
										        	        	for(var i=0;i<tbody.rows.length;i++)
										        	    	   	{
										        	    	   			    if(document.frmSelfCheque_Create.recoup_vou_no[param].value==document.frmSelfCheque_Create.recoup_voucher[i].value && document.frmSelfCheque_Create.sl_no[param].value==document.frmSelfCheque_Create.sno[i].value && document.frmSelfCheque_Create.recoup_vou_dt[param].value==document.frmSelfCheque_Create.recoup_voucher_dt[i].value)
										        	    	   			    {
										        	    	   				 
										        	    	   				 	    document.frmSelfCheque_Create.sel[i].checked=true
										        	    	   				 	    document.frmSelfCheque_Create.sel[i].disabled=false;											        	    	   				 	   
										        	    	   				 		
										        	    	   			    }
										        	    	   	}
						        	        		}
						        	        		else
						        	        		{
								        	        			if(document.frmSelfCheque_Create.recoup_vou_no[param].value==document.frmSelfCheque_Create.recoup_voucher.value && document.frmSelfCheque_Create.sl_no[param].value==document.frmSelfCheque_Create.sno.value && document.frmSelfCheque_Create.recoup_vou_dt[param].value==document.frmSelfCheque_Create.recoup_voucher_dt.value)
							        	    	   			 	{
							        	    	   				 
							        	    	   				 	    	document.frmSelfCheque_Create.sel.checked=true	
							        	    	   				 	    	document.frmSelfCheque_Create.sel.disabled=false;											        	    	   				 	   
							        	    	   				 		
							        	    	   			 	}
						        	        		}
							        	    	   	
						        	        }
						        	        document.getElementById("recoupDiv").style.display="block";
				        	         
				        	   	   }
				        	   	  
				           }
				           else
				           {
				        	   	   alert("No voucher number available for recoup");
				        	   	   
				           }
			      }
	    }    
}


function load_employee(emp)
{		
		if(document.getElementById("txtAcc_HeadCode").value==820103 || document.getElementById("txtAcc_HeadCode").value==820102)
				  load_EmployeeDet();
		else
				  trs_employee(emp);
		
	
}

function load_EmployeeDet()
{  
		//document.getElementById("txtPaidTo").value="";
		var emp_id=document.getElementById("txtEmpID_trs").value;
        var txtOfficeId=document.getElementById("login_office").value;
        var url="../../../../../Imprest_Account_Creation?command=Load_SL_Code&txtOfficeId="+txtOfficeId+"&txtEmpID="+emp_id;
        var req=getTransport();
        req.open("GET",url,true); 
        req.onreadystatechange=function()
        {
        		   Employee_handleResponse(req);
        }   
        req.send(null);                       
}

function Employee_handleResponse(req)
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
				       		   	   var cmbSL_Code=document.getElementById("cmbSL_Code");      // value assigned to same local variable name
				       	         
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
				       	           for(var k=0;k<items_id.length;k++)
				       	           {   
				       	        	   		var option=document.createElement("OPTION");
				       	        	   		option.text=items_name[k];
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
				       	           document.getElementById("cmbSL_Code").value=items_id[0];
				       	           loadRecoupDetails('create','null');
				                
		       		   	   }
		       		   	   else
		       		   	   {
		       		   		   	   alert("Selected Employee Id is invalid");
		       		   		   	   document.getElementById("txtEmpID_trs").value="";
		       		   		       var cmbSL_Code=document.getElementById("cmbSL_Code");
		       		   		       clear_Combo(cmbSL_Code);
		       		   		       document.getElementById("recoupDiv").style.display="none";
		       		   		       document.frmSelfCheque_Create.payment_type[0].checked=true;
		       		   		       
			       		   		   var tbody=document.getElementById("recoup_body");
				        	   	   try{tbody.innerHTML="";
				        	   	   }catch(e) {tbody.innerText="";
				        	   	   }
		       		   	   }
		       	   }
    	}    
		
}


function emp_servicepopup()
{
	    if (winemp && winemp.open && !winemp.closed) 
	    {
			       winemp.resizeTo(500,500);
			       winemp.moveTo(250,250); 
			       winemp.focus();
	    }
	    else
	    {
	    		   winemp=null
	    }	 
	    var OfficeId=document.getElementById("login_office").value;
	    winemp= window.open("../../../../../org/FAS/FAS1/CommonControls/jsps/Self_Cheque_Popup.jsp?OfficeId="+OfficeId,"Employeesearch","status=1,height=500,width=600,resizable=YES, scrollbars=yes"); 
	    winemp.moveTo(250,250);  
	    winemp.focus();
	   
    
}

function doParentEmp(emp)
{
		document.getElementById("txtEmpID_trs").value=emp;
		load_EmployeeDet();
}

function HeadCodeValidation()
{
var Acc_HeadCode1=document.getElementById("txtAcc_HeadCode").value;
    if(Acc_HeadCode1==900109 || Acc_HeadCode1==901001 || Acc_HeadCode1==901002)
    {
    alert("Tis AccountHead Code Not allowed Here");
    document.getElementById("txtAcc_HeadCode").value="";
    document.getElementById("txtAcc_HeadDesc").value="";
    return false;
    }
}

/*function Acc_HeadCodeValidation()
{
var date1=document.getElementById("txtCrea_date").value;
    var spl=date1.split("/");
    if(spl[2]==2011 && spl[1]>03)
    {
          if(spl[0]>01 || spl[0]==01)
          {
              var Acc_HeadCode1=document.getElementById("txtAcc_HeadCode").value;
              var digit=parseInt(Acc_HeadCode1.substr(0, 2));  
              if(digit==82)
              {
              alert("Acc Head Code Should Not starts with '82'");
              document.getElementById("txtAcc_HeadCode").value="";
              document.getElementById("txtAcc_HeadCode").focus();
              }
          }
    }
}  */
//////////////////////////////////////////////////////////////////////////////////////