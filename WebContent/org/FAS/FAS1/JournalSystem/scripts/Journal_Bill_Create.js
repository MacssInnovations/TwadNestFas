var com_id;
var common_cmbSL_Code="";
var common_cmbSL_type="";
var seq=0;
var job_flag;
var common_AHead_code_flag="";
var emp_flag; 
//--------------------------------------------------------------------------------
window.onunload=function()
{
    if (winAccHeadCode && winAccHeadCode.open && !winAccHeadCode.closed) winAccHeadCode.close();
    if (winjob && winjob.open && !winjob.closed) winjob.close();
    if (winemp && winemp.open && !winemp.closed) winemp.close();

} ;

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
            //alert("Flag----->"+flag);
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




/////////////////////////////////////////////   load Table() /////////////////////////////////////////////////////

function loadTable(scod)
{
        com_id=scod;                                    // to identify in UPDATE_GRID ,which row loaded 
        clearall();
       // document.FasAcc_Headform.cmdadd.disabled=true;
       //document.getElementById("txtAcc_HeadCode").readOnly=true;                // do not change the Account Head 
       //text_field.readOnly=true;
        var r=document.getElementById(scod);
        var rcells=r.cells;
        try {document.getElementById("txtAcc_HeadCode").value=rcells.item(1).firstChild.value;}catch(e){}
          //doFunction('checkCode','null');
        doFunctionBLOCK('checkCode','null');
         try{com_cmbSL_type=rcells.item(3).firstChild.value;} catch(e){com_cmbSL_type=""}
        try{com_cmbSL_Code=rcells.item(4).firstChild.value;} catch(e){com_cmbSL_Code=""}     
        
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
              
                doFunction('Load_SL_Code',com_cmbSL_type);
                
                setTimeout('document.getElementById("cmbSL_type").value=com_cmbSL_type',900); 
        setTimeout('document.getElementById("cmbSL_Code").value=com_cmbSL_Code',900); 
        
         if(rcells.item(2).firstChild.value=="CR")
         document.frmJournal_Bill_Create.rad_sub_CR_DR[0].checked=true;
         else if(rcells.item(2).firstChild.value=="DR")
         document.frmJournal_Bill_Create.rad_sub_CR_DR[1].checked=true;
         
       //try{document.getElementById("txtsub_Recei_from").value=rcells.item(5).firstChild.value;}catch(e){}
       try{document.getElementById("txtBill_NO").value=rcells.item(5).firstChild.value;}catch(e){}
       try{document.getElementById("txtBill_date").value=rcells.item(6).firstChild.value;}catch(e){}
       
        try{document.getElementById("txtBill_type").value=rcells.item(7).firstChild.value;}catch(e){}
       
        var nex=rcells.item(7).firstChild.nextSibling  
        try{document.getElementById("txtAgree_No").value=nex.value;}catch(e){}
        nex=nex.nextSibling;
        try{document.getElementById("txtAgree_Date").value=nex.value;}catch(e){}
        nex=nex.nextSibling;
        try{document.getElementById("txtsub_Amount").value=nex.value;}catch(e){}
        nex=nex.nextSibling;
        try{document.getElementById("txtParticular").value=nex.value;}catch(e){}
       
       
    document.frmJournal_Bill_Create.cmdupdate.style.display='block';
    document.frmJournal_Bill_Create.cmddelete.disabled=false;
    document.frmJournal_Bill_Create.cmdadd.style.display='none';
}

function checkOffices(pppp)
{
var jj=document.getElementById("cmbMas_SL_type").value;
if(pppp=="550351" && jj=="84")
{
    //    doFunction('checkCode','null');
	doFunctionBLOCK('checkCode','null');
       setTimeout('meth1()',900);       
        
}
else
{
//doFunction('checkCode','null');
	doFunctionBLOCK('checkCode','null');
}
}

function meth1()
{
        document.getElementById("cmbSL_type").options[document.getElementById("cmbSL_type").selectedIndex].value=5;
        document.getElementById("cmbSL_type").options[document.getElementById("cmbSL_type").selectedIndex].text="Offices";
        document.getElementById("cmbSL_Code").options[document.getElementById("cmbSL_Code").selectedIndex].value=5000;
        document.getElementById("cmbSL_Code").options[document.getElementById("cmbSL_Code").selectedIndex].text="Head Office, Chennai";
}

/////////////////////////////////////////////   ADD & UPDATE & DELETE /////////////////////////////////////////////////////
function ADD_GRID()
{
	var inc_code=0;
        if(document.getElementById("txtAcc_HeadCode").value.length==0)
        {
        alert("Enter A/c Head Code");
        return false;
        }
          
       if(document.getElementById("txtAcc_HeadDesc").value=="")
       {
            alert("Please Wait Account Head is Loading .......................");            
            return false;        
       }  
       
       
       if ( isMan.account_head_status) 
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
       }
        else
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
       //march
     /*  var GeneralJtype=document.getElementById("cmbMas_SL_type").value;
       var GeneralSlCode=document.getElementById("cmbMas_SL_Code").value; 
       var tbody=document.getElementById("grid_body");
      // alert(tbody.rows.length);
       if(tbody.rows.length>0)
        {
		    	   var sltype=document.getElementById("cmbSL_type").value;
		           var slCode=document.getElementById("cmbSL_Code").value;
		           rows=tbody.getElementsByTagName("tr");
		           
		        for(i=0;i<rows.length;i++)
		         {
		        	//alert("len::"+rows.length);
		              var cells=rows[i].cells;
		              var gridStype=cells.item(3).firstChild.value;
		              var gridScode=cells.item(4).firstChild.value;
		              if(GeneralJtype==gridStype)
		              {
		            	  if(GeneralSlCode==gridScode)
		            	  {
		            		  var detailSltype=document.getElementById("cmbSL_type").value;
		            	       var detailSlCode=document.getElementById("cmbSL_Code").value;
		            	       if(detailSltype==GeneralJtype)
		            	       {
		            	    	   if(detailSlCode==GeneralSlCode)
		            	    	   {
				            		alert("Only One SubLedger Detail as in the 'General' can be added");
				           		    return false;
		            	    	   }
		            	       }
		            	  }
		              }
		              
		         }
           
        }  */
      
  
    
        if(document.getElementById("txtsub_Amount").value.length==0)
        {
            alert("Enter the Amount ");
            //document.getElementById("txtAmount").focus();
            return false;    
        }
        var tbody=document.getElementById("grid_body");
            //alert("CODE"+document.getElementById("txtSL_Desc").value);
            //alert("TEXT"+document.getElementById("txtSL_Desc").options[document.getElementById("txtSL_Desc").selectedIndex].text);
            //alert("AGA"+document.getElementById("txtSL_Desc").text)
        var t=0;
        var exist=document.getElementById("txtAcc_HeadCode").value;
      //  if(checkForRedundancy(exist))
      //  {
        var items=new Array();
        items[0]=document.getElementById("txtAcc_HeadCode").value;
        items[1]=document.getElementById("txtAcc_HeadDesc").value;
        if(document.frmJournal_Bill_Create.rad_sub_CR_DR[0].checked==true)
          items[2]=document.frmJournal_Bill_Create.rad_sub_CR_DR[0].value;
        else if(document.frmJournal_Bill_Create.rad_sub_CR_DR[1].checked==true)
          items[2]=document.frmJournal_Bill_Create.rad_sub_CR_DR[1].value;
        
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
        //alert("code"+items[6]+"ff");
        }
        else
        items[6]=document.getElementById("cmbSL_Code").options[document.getElementById("cmbSL_Code").selectedIndex].text; 
        
       // items[7]=document.getElementById("txtsub_Recei_from").value;
       
        items[7]=document.getElementById("txtBill_NO").value;
        items[8]=document.getElementById("txtBill_date").value;
        items[9]=document.getElementById("txtBill_type").value;
        items[10]=document.getElementById("txtAgree_No").value;
        items[11]=document.getElementById("txtAgree_Date").value;
        
        items[12]=document.getElementById("txtsub_Amount").value;
        items[13]=document.getElementById("txtParticular").value;
        
        //items[0]=document.getElementById("txtSL_code").value;
        //items[1]=document.getElementById("txtSL_Desc").options[document.getElementById("txtSL_Desc").selectedIndex].text;                
        tbody=document.getElementById("grid_body");
        var mycurrent_row=document.createElement("TR");
        seq=seq+1;
        mycurrent_row.id=seq;
        //alert("row ID"+mycurrent_row.id);
        var cell=document.createElement("TD");
        var anc=document.createElement("A");
        var url="javascript:loadTable('"+mycurrent_row.id+"')";
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
           // alert(items[3]);
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
                  var Bill_NO=document.createElement("input");
                  Bill_NO.type="hidden";
                  Bill_NO.name="Bill_NO";
                  Bill_NO.value=items[7];
                  cell2.appendChild(Bill_NO);
                   var currentText=document.createTextNode(items[7]);
                  cell2.appendChild(currentText);
                   mycurrent_row.appendChild(cell2);
            
             cell2=document.createElement("TD");
                  var Bill_date=document.createElement("input");
                  Bill_date.type="hidden";
                  Bill_date.name="Bill_date";
                  Bill_date.value=items[8];
                  cell2.appendChild(Bill_date);
                   var currentText=document.createTextNode(items[8]);
                  cell2.appendChild(currentText);
                mycurrent_row.appendChild(cell2);
                
                
             cell2=document.createElement("TD"); 
               var Bill_type=document.createElement("input");
                  Bill_type.type="hidden";
                  Bill_type.name="Bill_type";
                  Bill_type.value=items[9];
                  cell2.appendChild(Bill_type);
                  
               var Agree_No=document.createElement("input");
                  Agree_No.type="hidden";
                  Agree_No.name="Agree_No";
                  Agree_No.value=items[10];
           // Agree_No.style.display='none';
                  cell2.appendChild(Agree_No);
                    
              var Agree_date=document.createElement("input");
                  Agree_date.type="hidden";
                  Agree_date.name="Agree_date";
                  Agree_date.value=items[11];
        // Agree_date.style.display='none';
                  cell2.appendChild(Agree_date);

              var sl_amt=document.createElement("input");
                  sl_amt.type="hidden";
                  sl_amt.name="sl_amt";
                  sl_amt.value=items[12];
                  cell2.appendChild(sl_amt);

              var particular=document.createElement("input");           // Particulars Added to grid b4 the Amount Text Node but after  amount hidden box    
                  particular.type="hidden";
                  particular.name="particular";
                  particular.value=items[13];
            //    particular.style.display='none';
                  cell2.appendChild(particular);

                  var currentText=document.createTextNode(items[12]);
                  cell2.appendChild(currentText);
                  mycurrent_row.appendChild(cell2);

        tbody.appendChild(mycurrent_row);
         clear_main_fields();
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
     
        //march
      /*  var GeneralJtype=document.getElementById("cmbMas_SL_type").value;
        var GeneralSlCode=document.getElementById("cmbMas_SL_Code").value; 
        var tbody=document.getElementById("grid_body");
       // alert(tbody.rows.length);
        if(tbody.rows.length>0)
         {
 		    	   var sltype=document.getElementById("cmbSL_type").value;
 		           var slCode=document.getElementById("cmbSL_Code").value;
 		           rows=tbody.getElementsByTagName("tr");
 		           
 		        for(i=0;i<rows.length;i++)
 		         {
 		        	//alert("len::"+rows.length);
 		              var cells=rows[i].cells;
 		              var gridStype=cells.item(3).firstChild.value;
 		              var gridScode=cells.item(4).firstChild.value;
 		              if(GeneralJtype==gridStype)
 		              {
 		            	  if(GeneralSlCode==gridScode)
 		            	  {
 		            		  var detailSltype=document.getElementById("cmbSL_type").value;
 		            	       var detailSlCode=document.getElementById("cmbSL_Code").value;
 		            	       if(detailSltype==GeneralJtype)
 		            	       {
 		            	    	   if(detailSlCode==GeneralSlCode)
 		            	    	   {
 				            		alert("Only One SubLedger Detail as in the 'General' can be added");
 				           		    return false;
 		            	    	   }
 		            	       }
 		            	  }
 		              }
 		              
 		         }
            
         }  */
        
      

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
        if(document.frmJournal_Bill_Create.rad_sub_CR_DR[0].checked==true)
          items[2]=document.frmJournal_Bill_Create.rad_sub_CR_DR[0].value;
        else if(document.frmJournal_Bill_Create.rad_sub_CR_DR[1].checked==true)
          items[2]=document.frmJournal_Bill_Create.rad_sub_CR_DR[1].value;
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
        //alert("code"+items[6]+"ff");
        }
        else
        items[6]=document.getElementById("cmbSL_Code").options[document.getElementById("cmbSL_Code").selectedIndex].text; 
        
        items[7]=document.getElementById("txtBill_NO").value;
        items[8]=document.getElementById("txtBill_date").value;
        items[9]=document.getElementById("txtBill_type").value;
        items[10]=document.getElementById("txtAgree_No").value;
        items[11]=document.getElementById("txtAgree_Date").value;
        
        items[12]=document.getElementById("txtsub_Amount").value;
        items[13]=document.getElementById("txtParticular").value;
        
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
            
                //try{rcells.item(5).firstChild.value=items[7];}catch(e){}
               // try{rcells.item(5).lastChild.nodeValue=items[7];}catch(e){}
            
                try{rcells.item(5).firstChild.value=items[7];}catch(e){}
                try{rcells.item(5).lastChild.nodeValue=items[7];}catch(e){}
             
                try{rcells.item(6).firstChild.value=items[8];}catch(e){}
                try{rcells.item(6).lastChild.nodeValue=items[8];}catch(e){}
        rcells.item(7).firstChild.value=items[9];
        var nex_cell=rcells.item(7).firstChild.nextSibling;
        nex_cell.value=items[10];
        var nex_cell=nex_cell.nextSibling;
        nex_cell.value=items[11];
        var nex_cell=nex_cell.nextSibling;
        nex_cell.value=items[12];
        var nex_cell=nex_cell.nextSibling;
        nex_cell.value=items[13];
        rcells.item(7).lastChild.nodeValue=items[12];
        alert("Record Updated");
        clearall();
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
/*
function checkname()
{
    //alert(document.getElementById("cmbMas_SL_type").value)
    //alert(document.getElementById("cmbSL_type").value)
    
    if(document.getElementById("cmbMas_SL_type").value==document.getElementById("cmbSL_type").value)
    {
        //alert(document.getElementById("cmbMas_SL_Code").value+"  "+document.getElementById("cmbSL_Code").value)
        if(document.getElementById("cmbMas_SL_Code").value!=document.getElementById("cmbSL_Code").value)
        {
        alert("Selected sub-ledger code doesn't match with General sub-ledger code");
        document.getElementById("cmbSL_Code").value="";
        return false;
        }
    }
   
}  */

function clear_main_fields()
{
    
     document.getElementById("offlist_div_trans").style.display='none';
     document.getElementById("emplist_div_trans").style.display='none';
     
    document.getElementById("txtAcc_HeadCode").value="";
    //document.getElementById("txtAcc_HeadCode").readOnly=false;
    document.getElementById("txtAcc_HeadDesc").value="";
    document.frmJournal_Bill_Create.rad_sub_CR_DR[0].checked=true;
    document.getElementById("cmbSL_type").value="";
    document.getElementById("cmbSL_Code").value="";
 /*   document.getElementById("txtBill_NO").value="";
    document.getElementById("txtBill_date").value="";
    document.getElementById("txtBill_type").value="";
    document.getElementById("txtAgree_No").value="";
    document.getElementById("txtAgree_Date").value="";*/
    //document.getElementById("txtsub_Recei_from").value="";
    document.getElementById("txtsub_Amount").value="";
    document.getElementById("txtParticular").value="";
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
     document.getElementById("offlist_div_trans").style.display='none';       
            var cmbSL_Code=document.getElementById("cmbSL_Code");   
            clear_Combo(cmbSL_Code);   

     document.frmJournal_Bill_Create.cmdadd.style.display='block';
     document.frmJournal_Bill_Create.cmdupdate.style.display='none';
     document.frmJournal_Bill_Create.cmddelete.disabled=true;
}
/////////////////////////////////////////////   clearall() by User /////////////////////////////////////////////////////

function clearall()
{
    
     document.getElementById("offlist_div_trans").style.display='none';
     document.getElementById("emplist_div_trans").style.display='none';
     
    document.getElementById("txtAcc_HeadCode").value="";
    //document.getElementById("txtAcc_HeadCode").readOnly=false;
    document.getElementById("txtAcc_HeadDesc").value="";
    document.frmJournal_Bill_Create.rad_sub_CR_DR[0].checked=true;
    document.getElementById("cmbSL_type").value="";
    document.getElementById("cmbSL_Code").value="";
    document.getElementById("txtBill_NO").value="";
    document.getElementById("txtBill_date").value="";
    document.getElementById("txtBill_type").value="";
    document.getElementById("txtAgree_No").value="";
    document.getElementById("txtAgree_Date").value="";
    //document.getElementById("txtsub_Recei_from").value="";
    document.getElementById("txtsub_Amount").value="";
    document.getElementById("txtParticular").value="";
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
    document.getElementById("offlist_div_trans").style.display='none';       
            var cmbSL_Code=document.getElementById("cmbSL_Code");   
            clear_Combo(cmbSL_Code);   

 document.frmJournal_Bill_Create.cmdadd.style.display='block';
 document.frmJournal_Bill_Create.cmdupdate.style.display='none';
 document.frmJournal_Bill_Create.cmddelete.disabled=true;
}
 
function call_clr()
{
   try
   {
    // document.getElementById("txtAmount").value="";
    //document.getElementById("txtBill_NO").value="";
    //document.getElementById("txtBill_date").value="";
    clearall();
    document.getElementById("txtRemarks").value="";
    
    document.getElementById("cmbMas_SL_type").value="";
    clear_Combo(document.getElementById("cmbMas_SL_Code"));
    var tbody=document.getElementById("grid_body");
    var t=0;
    for(t=tbody.rows.length-1;t>=0;t--)
    {
       tbody.deleteRow(0);
    }
   }
   catch(e)
   {
     alert(e.description);
   }
   
    
}


function clrForm()
{
   if(window.confirm("Do you want to clear ALL fields ?"))
 {
    call_clr();
    
 }
}


/////////////////////////////////////////////   checkNull() by User /////////////////////////////////////////////////////

function checkNull()
{
var proceed=0;
var flag=0;
var cmbMas_SL_type=document.getElementById("cmbMas_SL_type").value;
var tbody=document.getElementById("grid_body");
   //alert("tbody.rows.length :"+tbody.rows.length);   
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
 if (document.getElementById("txtRemarks").value== "") {
		alert("Enter Remarks");
		// document.getElementById("txtCrea_date").focus();
		return false;
	}
/*if(document.getElementById("txtCash_Acc_code").value.length==0)
{
    alert("Enter the Cash A/c Code");
    //document.getElementById("txtCash_Acc_code").focus();
    return false;
}*/

/*if(document.getElementById("txtRecei_from").value.length==0)
{
    alert("Enter the value in Received From Field");
    //document.getElementById("txtRecei_from").focus();
    return false;    
}*/

if(document.getElementById("cmbMas_SL_type").value=="")
{
    //if(document.getElementById("cmbMas_SL_Code").value=="")
    //{
    alert("Select The Journal Type in General");
    return false;
    //}
}


if(document.getElementById("cmbMas_SL_type").value!="" )
{
    if(document.getElementById("cmbMas_SL_Code").value=="")
    {
    alert("Enter the Sub-Ledger Code");
    return false;
    }
}
/*if(document.getElementById("txtAmount").value.length==0)
{
    alert("Enter the Total Amount in General");
    //document.getElementById("txtAmount").focus();
    return false;    
}*/
if(tbody.rows.length==0)
{
    alert("Enter the Details Part");
    //document.getElementById("txtAmount").focus();
    return false; 
}



if(tbody.rows.length>0)
{
        var check_amt=0;
        var cr_amt=0;
        var db_amt=0;
       
      //  alert("cmbMas_SL_type"+cmbMas_SL_type);
        rows=tbody.getElementsByTagName("tr");
        var ccc;
        var hCodes= new Array();
        var spl11;
        var val="";
        for(i=0;i<rows.length;i++)
        {
            var cells=rows[i].cells;
         
            if(cells.item(2).lastChild.nodeValue=='CR')
            {
            	 var ac_code=cells.item(1).lastChild.nodeValue;
                 var code1= ac_code.split("-");
                 if( (code1[0]==550502) && (cmbMas_SL_type==96) )
                 {
                	 
                flag=111;
                 }
                 
            cr_amt=parseFloat(cr_amt) + parseFloat(cells.item(7).lastChild.nodeValue);
            }
            else
            {
            db_amt=parseFloat(db_amt) + parseFloat(cells.item(7).lastChild.nodeValue);
            }
          
            var generalSLTYPE=document.getElementById("cmbMas_SL_type").options[document.getElementById("cmbMas_SL_type").selectedIndex].text;
          
            if(generalSLTYPE=="TWAD CPS Journal"){
            	//pradha
            	var hc=cells.item(1).lastChild.nodeValue;
            	var tt=hc.split("-");
            	
            	if(tt[0]=="550351")
            	{
            		
            		 proceed++;
            	}
            	if(tt[0]=="550350")
            	{
            		
            		 proceed++;
            	}
            }
            else if(generalSLTYPE=="Employee Journal")
            {
              if(cells.item(3).lastChild.nodeValue=="Employees")
                {
              //  alert("4:::"+cells.item(3).firstChild.value);
                
                if(cells.item(4).firstChild.value==document.getElementById("txtEmpID_mas").value)
                {
                proceed++;
                }
                }
            }
            else if(generalSLTYPE=="Rent Journal")
            {
            	
            	if(cells.item(3).firstChild.value==17)
            	{
            		
            		if(cells.item(4).firstChild.value==document.getElementById("cmbMas_SL_Code").value)
                    {
            		//	alert("end");
                    proceed++;
                    }
            	}
            }
            else if(generalSLTYPE=="Firms")
            {
            	
            	if(cells.item(3).firstChild.value==2)
            	{
            		
            		if(cells.item(4).firstChild.value==document.getElementById("cmbMas_SL_Code").value)
                    {
            		
                    proceed++;
                    }
            	}
            }
            else{
            	
           if(generalSLTYPE==cells.item(3).lastChild.nodeValue)
            {
	        	   if(cells.item(4).firstChild.value==document.getElementById("cmbMas_SL_Code").value)
	               {
	            proceed++;
	               }
            }
          }  
         
       
        }
  
        
        
        if(parseFloat(db_amt)<=0)
        {
            alert("Debit amount must be specified");
            return false;
        }
        
        if(parseFloat(cr_amt)!=parseFloat(db_amt))
        {
        alert("Amount doesn't Tally.. Difference " +(parseFloat(cr_amt)-parseFloat(db_amt)));
        return false;
        } 
        
}
 if(proceed==0)
    {
    alert("Atleast One sub-Ledger Type should match with GeneralJournal");
    return false;
    }  
if(cmbMas_SL_type==96)
{
	if(flag==111)
	{
		 return true;
	}
	else
	{
		alert("A/c Head 550502 Should be Added as Credit Head for Rent Journal");
        return false;
	}
}
return true;
}

 
function call_mainJSP_script(fromcal_dateCtrl,blr_flag)     /////////Calender control return value handling
{
    //alert(fromcal_dateCtrl.value+"GG"+blr_flag+fromcal_dateCtrl);
    if(blr_flag==1)             // which is used to find the receipt or voucher or payment (creation) date calling field,if so check trial balance
    {
             call_clr();
             var cmbAcc_UnitCode=document.getElementById("cmbAcc_UnitCode").value;
             var cmbOffice_code=document.getElementById("cmbOffice_code").value;
             var TB_date=fromcal_dateCtrl.value;
             //alert(fromcal_dateCtrl.value+"b4url")
             if(fromcal_dateCtrl.value.length!=0)
             {
                 var url="../../../../../Receipt_SL.view?Command=check_TB_Jrnl&TB_date="+TB_date+"&cmbAcc_UnitCode="+cmbAcc_UnitCode+"&cmbOffice_code="+cmbOffice_code;
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



//function check_TB(req,dateCtrl)
//{
// if(req.readyState==4)
//    {
//        if(req.status==200)
//        {  
//            var baseResponse=req.responseXML.getElementsByTagName("response")[0];
//            var tagcommand=baseResponse.getElementsByTagName("command")[0];
//            var Command=tagcommand.firstChild.nodeValue;
//            var flag=baseResponse.getElementsByTagName("flag")[0].firstChild.nodeValue;
//            
//            if(flag=="success")
//              {
//                 //doFunction('load_Receipt_No','null');                 //return true;
//              }
//             else if(flag=="failure")
//               {
//                    dateCtrl.value="";
//                    alert("Trial Balance Closed");//return false;//
//                    dateCtrl.focus();
//                    document.getElementById("txtCrea_date").value="";
//               }
//             else if(flag=="finyear")
//               {
//                          // This statement get executed when financial year ( Cash Book Control Details ) has not found for the given date 
//                    dateCtrl.value="";
//                    alert("Cash Book Control Not Found ");//return false;//
//                    dateCtrl.focus();
//                    document.getElementById("txtCrea_date").value="";     
//               }
//             else if(flag=="success1")
//            	 {
//            	 
//            	 }
//            dateCheck(dateCtrl);  
//        }
//    }
//}









/*

function limit_amt_journal(field,e)
{
 var unicode=e.charCode? e.charCode : e.keyCode;
 //alert(unicode);
      if(field.value.length<17)
      {
        if(field.value.length==14 && field.value.indexOf('.')==-1  )
        field.value=field.value+'.';
        if (unicode!=8 && unicode !=9  )
        {
            alert(" iiii");
            //if (unicode<45 || unicode==47 || unicode>57   )        // It  allow the negative amount
             if (unicode<46 || unicode==47 || unicode>57   )       // It won't allow the negative amount
                return false 
        }
      }
      else 
      return false;
}

*/


function limit_amt_journal(field,e)
{

      var Journal_Creation_date=document.getElementById("txtCrea_date").value.split("/");;
        //  alert(Journal_Creation_date[1]);
    
      var unicode=e.charCode? e.charCode : e.keyCode;

      if(field.value.length<17)
      {
        if(field.value.length==14 && field.value.indexOf('.')==-1  )
        field.value=field.value+'.';
        if (unicode!=8 && unicode !=9  )
        {
                            
            if (Journal_Creation_date[1] <=8 && Journal_Creation_date[2]<=2007 )     
            {
             
              if (unicode<45 || unicode==47 || unicode>57   )        // It  allow the negative amount
                  return false  
             }
             else  
             {
             
              if (unicode<46 || unicode==47 || unicode>57   )       // It won't allow the negative amount   
                  return false
             }     
                  
        }
      }
      else   
      return false;  
}
function call_date(dateCtrl)                        // TB_checking 
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
             var url="../../../../../Receipt_SL.view?Command=check_TB_Jrnl&TB_date="+TB_date+"&cmbAcc_UnitCode="+cmbAcc_UnitCode+"&cmbOffice_code="+cmbOffice_code;
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
     // document.getElementById("txtReceipt_No").value="";
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
                    //document.getElementById("txtReceipt_No").value="";
               }
             else if(flag=="finyear")
               {
                          // This statement get executed when financial year ( Cash Book Control Details ) has not found for the given date 
                    dateCtrl.value="";
                    alert("Cash Book Control Not Found ");//return false;//
                    dateCtrl.focus();
                   // document.getElementById("txtReceipt_No").value="";     
               }
             else if(flag=="finyearLJVN")
             {
                        // This statement get executed when financial year ( Cash Book Control Details ) has not found for the given date 
                  dateCtrl.value="";
                  alert("Cash Book Control Not Found for Journal");//return false;//
                  dateCtrl.focus();
                 // document.getElementById("txtReceipt_No").value="";     
             }
            dateCheck(dateCtrl);
        }
    }
}


function Acc_HeadCodeValidation()
{
//	alert("checking Account heads");
	var unitid=document.getElementById("cmbAcc_UnitCode").value;
	//alert(unitid);
	if(unitid!=5){
		if(unitid!=999){

var cmbMas_SL_type = document.getElementById("cmbMas_SL_type").value;
if(cmbMas_SL_type=="")
{
alert("Choose Journal Type in General");
document.getElementById("txtAcc_HeadCode").value="";
document.getElementById("txtAcc_HeadDesc").value="";
//document.getElementById("cmbMas_SL_type").focus();
return false;
}

var date1=document.getElementById("txtCrea_date").value;
//alert("Create Date Entered***"+date1);
var Acc_HeadCode=parseInt(document.getElementById("txtAcc_HeadCode").value);
//hide on 15jun2020 alert("Acc_HeadCode***"+Acc_HeadCode);
var Acc_HeadCode1=document.getElementById("txtAcc_HeadCode").value;
var digit=parseInt(Acc_HeadCode1.substr(0, 2));  
var digit1=parseInt(Acc_HeadCode1.substr(0, 3)); 
    var spl=date1.split("/");
   //  if( ((spl[2]<2018) && (spl[0]>01 || spl[0]==01)) || ((spl[2]>2018 || spl[2]==2018) && (spl[0]<04) ) )
    //{
          if(spl[2]>=2011)
        	{
    		if(spl[0]>01 || spl[0]==01)
             {
                   
                  //alert("Date less than 01/04/2018");
               
                  if((Acc_HeadCode==900108) || (Acc_HeadCode==900109) || (Acc_HeadCode==901001)|| (Acc_HeadCode==901002)|| (Acc_HeadCode==620101)|| (Acc_HeadCode==900301)|| (Acc_HeadCode==610101)|| (Acc_HeadCode==610102)||(Acc_HeadCode==900201) ||(Acc_HeadCode==900202)|| (Acc_HeadCode==390602)) 
                  {
                   alert("Account Head Code  390602,900108,900109,901001,901002,620101,900301, 610101, 610102,900201,900202 Not Allowed ");
                  document.getElementById("txtAcc_HeadCode").value="";
                  document.getElementById("txtAcc_HeadCode").focus();
                  return false;
                  }
                  else if((Acc_HeadCode==780401) ||(Acc_HeadCode==780402) ||(Acc_HeadCode==780403) ||(Acc_HeadCode==780405) ||(Acc_HeadCode==780406) || (Acc_HeadCode==160502) || (Acc_HeadCode==782401) ||(Acc_HeadCode==782402) ||(Acc_HeadCode==782403) || (Acc_HeadCode==782404) ||(Acc_HeadCode==782405) ||(Acc_HeadCode==782406))
                  {
                	  alert("DCB Head Codes like 780401,780402,780403,780405,780406,160502,782401,782402,782403,782404,782405,782406 Are Not Allowed ");
                      document.getElementById("txtAcc_HeadCode").value="";
                      document.getElementById("txtAcc_HeadCode").focus();
                  } else if((Acc_HeadCode==390302) ||(Acc_HeadCode==390303)|| (Acc_HeadCode==390305) || (Acc_HeadCode==391002) ||(Acc_HeadCode==391003) ||(Acc_HeadCode==391302) || (Acc_HeadCode==391303) ||(Acc_HeadCode==391502) ||(Acc_HeadCode==391503) )
                  {			
                	  alert("GPF Account Head Code cannot be used here***");
                      document.getElementById("txtAcc_HeadCode").value="";
                      document.getElementById("txtAcc_HeadCode").focus();
                  }
                  else if(digit==82)
                  {
                  alert("This Account Head Code cannot be used here");
                  document.getElementById("txtAcc_HeadCode").value="";
                  document.getElementById("txtAcc_HeadCode").focus();
                  }
                  
           }
    }
    /* else if(spl[2]>=2018)
    	 {
    	 if(spl[0]>04 || spl[0]==04)  
    		 {
    		 alert("Date Greater than 01/04/2018");
    		 if((Acc_HeadCode==900108) || (Acc_HeadCode==900109) || (Acc_HeadCode==901001)|| (Acc_HeadCode==901002)|| (Acc_HeadCode==620101)|| (Acc_HeadCode==900301)|| (Acc_HeadCode==610101)|| (Acc_HeadCode==610102) || (Acc_HeadCode==390602)) 
             {
              alert("Account Head Code  390602,900108,900109,901001,901002,620101,900301, 610101, 610102 Not Allowed ");
             document.getElementById("txtAcc_HeadCode").value="";
             document.getElementById("txtAcc_HeadCode").focus();
             }
             else if((Acc_HeadCode==780401) ||(Acc_HeadCode==780402) ||(Acc_HeadCode==780403) ||(Acc_HeadCode==780405) ||(Acc_HeadCode==780406) || (Acc_HeadCode==160502) || (Acc_HeadCode==782401) ||(Acc_HeadCode==782402) ||(Acc_HeadCode==782403) || (Acc_HeadCode==782404) ||(Acc_HeadCode==782405) ||(Acc_HeadCode==782406))
             {
           	  alert("DCB Head Codes like 780401,780402,780403,780405,780406,160502,782401,782402,782403,782404,782405,782406 Are Not Allowed ");
                 document.getElementById("txtAcc_HeadCode").value="";
                 document.getElementById("txtAcc_HeadCode").focus();
             } else if((Acc_HeadCode==390302) ||(Acc_HeadCode==390303)|| (Acc_HeadCode==390305) || (Acc_HeadCode==391002) ||(Acc_HeadCode==391003) ||(Acc_HeadCode==391302) || (Acc_HeadCode==391303) ||(Acc_HeadCode==391502) ||(Acc_HeadCode==391503) )
             {			
           	  alert("GPF Account Head Code cannot be used here***");
                 document.getElementById("txtAcc_HeadCode").value="";
                 document.getElementById("txtAcc_HeadCode").focus();
             }
             else if(digit==82)
             {
             alert("This Account Head Code cannot be used here");
             document.getElementById("txtAcc_HeadCode").value="";
             document.getElementById("txtAcc_HeadCode").focus();
             }
    		//HOA check for LJV for 222 head
             else if(digit1==222)
             {
             alert("This Account Head Code cannot be used here");
             document.getElementById("txtAcc_HeadCode").value="";
             document.getElementById("txtAcc_HeadCode").focus();
             }
      		 }
    	 }
		*/}
	}
	
}

function manipulated(req) {

	if (req.readyState == 4) {
		if (req.status == 200) {
//alert("ttttttt");
			var baseResponse = req.responseXML.getElementsByTagName("response")[0];
// alert(baseResponse);
			var tagCommand = baseResponse.getElementsByTagName("command")[0];
// alert(tagCommand);
			var command = tagCommand.firstChild.nodeValue;
//                        alert(command);
			if (command == "accCheck") {
                          var flag=baseResponse.getElementsByTagName("flag")[0].firstChild.nodeValue;
                          if(flag=="failure")
                          {
                          alert("Atleast one Liability Account Head must be added");
                          }
                          else
                          {
                          alert("proceed");
                          }
			} 
		}
	}
}

function call_a52()
{
	var accunitId=document.getElementById('cmbAcc_UnitCode').value;
	var cmbOffice_code=document.getElementById('cmbOffice_code').value;
	var url="../../../../../BankPay_PendingBill_Create.view?Command=a52_verify&accunitId="+accunitId+"&cmbOffice_code="+cmbOffice_code;
		
		var req=getTransport();
		req.open("POST",url,true); 
		req.onreadystatechange=function(){
		processResponse_bk(req);
		};   
		req.send(null);
}

function processResponse_bk(req){
	 if(req.readyState==4)
   { 
       if(req.status==200)
       {  
      	 var rangeResponse=req.responseXML.getElementsByTagName("response")[0];
    	   update_bank_bal(rangeResponse);
       }
   }
}
function update_bank_bal(response){
	var flag=response.getElementsByTagName("flag")[0].firstChild.nodeValue;
     
	if(flag=="fail"){
		alert("Please Verify A52 and AA52");
		document.getElementById("newDiv").style.display="none";
		document.frmBankPay_PendingBill_create.txtCrea_date.value="";
		return false;
	}
	else
	{
		
		var asset_cleared=response.getElementsByTagName("asset_cleared")[0].firstChild.nodeValue;
		if(asset_cleared>0)
		{
			 var today= new Date(); 
             var day=today.getDate();
             var month=today.getMonth();
             month=month+1;
             
             if(day<=9 && day>=1)
             day="0"+day;
             if(month<=9 && month>=1)
             month="0"+month;
             var year=today.getYear();
             if(year < 1900) year += 1900;
             var monthArray =new Array("January", "February", "March", 
                       "April", "May", "June", "July", "August",
                       "September", "October", "November", "December");
            document.frmBankPay_PendingBill_create.txtCrea_date.value=day+"/"+month+"/"+year;
            call_date(document.frmBankPay_PendingBill_create.txtCrea_date);
            document.getElementById("newDiv").style.display="block";
		}
		else
		{
			alert("Please Check A52 and AA52 Register Entry");
			document.getElementById("newDiv").style.display="none";
			document.frmBankPay_PendingBill_create.txtCrea_date.value="";
			return false;
		}
		
	}
}