var com_id;
var common_cmbSL_Code="";
var common_cmbSL_type="";
var seq=0;
var job_flag;
var common_AHead_code_flag="";
var emp_flag;


function checkACC()
{
    var date1=document.getElementById("txtCrea_date").value;
    var spl=date1.split("/");
    if(spl[2]>=2011)
    {
     var hcode_one=document.getElementById("txtAcc_HeadCode").value;
             if(hcode_one==900108 || hcode_one==900109 ||hcode_one==901002)
             {
                         alert(hcode_one+" AccountHeadCode cannot be used Here");
                         document.getElementById("txtAcc_HeadCode").value="";
                         document.getElementById("txtAcc_HeadDesc").value="";
                         return false;
             }else if((hcode_one==390302) ||(hcode_one==390303) || (hcode_one==391002) ||(hcode_one==391003) ||(hcode_one==391302) || (hcode_one==391303) ||(hcode_one==391502) ||(hcode_one==391503) )
     		
             {			
                   	  alert("GPF Account Head Code cannot be used here***");
                         document.getElementById("txtAcc_HeadCode").value="";
                         document.getElementById("txtAcc_HeadCode").focus();
                         return false;
                     }
            else
            {
                    if(spl[0]>01 || spl[0]==01){
                        var hcode=parseInt(document.getElementById("txtAcc_HeadCode").value)/10000;
                        var spObj=hcode.toString().split(".");
                        if(spObj[0]=="82")
                        {
                         alert("This Account HeadCode cannot be used here");
                         document.getElementById("txtAcc_HeadCode").value="";
                         document.getElementById("txtAcc_HeadDesc").value="";
                        }
                        else
                        {
                      //  doFunction('checkCode','null');
                        	 doFunctionBLOCK('checkCode','null');
                        }
                    }
            }
    }
    else
    {
    var hcode=document.getElementById("txtAcc_HeadCode").value;
     if(hcode==900108 || hcode==900109 ||hcode==901002)
     {
     alert(hcode+" AccountHeadCode cannot be used Here");
     document.getElementById("txtAcc_HeadCode").value="";
     document.getElementById("txtAcc_HeadDesc").value="";
     return false;
     }else if((hcode==390302) ||(hcode==390303) || (hcode==391002) ||(hcode==391003) ||(hcode==391302) || (hcode==391303) ||(hcode==391502) ||(hcode==391503) )
     {			
           	  alert("GPF Account Head Code cannot be used here***");
                 document.getElementById("txtAcc_HeadCode").value="";
                 document.getElementById("txtAcc_HeadCode").focus();
                 return false;
             }
   //doFunction('checkCode','null');
     doFunctionBLOCK('checkCode','null');
    }
}

function byUnitAndOfficeChange()
{
    doFunction_voucher('load_Receipt_No','null');
}
window.onunload=function()
{
    if (winAccHeadCode && winAccHeadCode.open && !winAccHeadCode.closed) winAccHeadCode.close();
    if (winjob && winjob.open && !winjob.closed) winjob.close();
    if (winemp && winemp.open && !winemp.closed) winemp.close();
}

function doFunction_voucher(Command,param)
{   
       var cmbAcc_UnitCode=document.getElementById("cmbAcc_UnitCode").value;
       var cmbOffice_code=document.getElementById("cmbOffice_code").value;
       var txtCrea_date= document.getElementById("txtCrea_date").value
       
       if(Command=="load_Receipt_No")
        {  
           clearGeneral_Detail();
            if(txtCrea_date.length!=0)
            {
            var url="../../../../../Cash_Receipt_Edition.view?Command=load_Receipt_No&txtCrea_date="+txtCrea_date+"&cmbAcc_UnitCode="+
            cmbAcc_UnitCode+"&cmbOffice_code="+cmbOffice_code;
            //alert(url)
            var req=getTransport();
            req.open("GET",url,true); 
            req.onreadystatechange=function()
            {
               handleResponse_voucher(req);
            }   
                    req.send(null);
            }         
        }
        else if(Command=="load_Receipt_Details")
        {  
            clearGeneral_Detail();
            var  txtReceipt_No=document.getElementById("txtReceipt_No").value;
           
            if(txtReceipt_No!="")
            {
            var url="../../../../../Cash_Receipt_Edition.view?Command=load_Receipt_Details&txtReceipt_No="+txtReceipt_No+"&txtCrea_date="+txtCrea_date+"&cmbAcc_UnitCode="+
            cmbAcc_UnitCode+"&cmbOffice_code="+cmbOffice_code;;
            var req=getTransport();
            req.open("GET",url,true); 
            req.onreadystatechange=function()
            {
               handleResponse_voucher(req);
            }   
                    req.send(null);
            }         
        }
        

}
/////////////////////////////////////////////   handleResponse()  /////////////////////////////////////////////////////
function handleResponse_voucher(req)
{  
    if(req.readyState==4)
    {
        if(req.status==200)
        {  
            var baseResponse=req.responseXML.getElementsByTagName("response")[0];
            var tagcommand=baseResponse.getElementsByTagName("command")[0];
            var Command=tagcommand.firstChild.nodeValue;
             
            if(Command=="load_Receipt_No")
            {
                load_Receipt_No(baseResponse);
            }
            else if(Command=="load_Receipt_Details")
            {
                load_Receipt_Details(baseResponse);
            }
            
        }
    }
}


/////////////////////////////////////////////   load Table() /////////////////////////////////////////////////////

function loadTable(scod)
{
        com_id=scod;                                    // to identify in UPDATE_GRID ,which row loaded 
        clearall();
        var r=document.getElementById(scod);
        var rcells=r.cells;
        try {document.getElementById("txtAcc_HeadCode").value=rcells.item(1).firstChild.value;}catch(e){}
      //   doFunction('checkCode','null');
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
               // alert(com_cmbSL_type);
        setTimeout('document.getElementById("cmbSL_type").value=com_cmbSL_type',900); 
        setTimeout('document.getElementById("cmbSL_Code").value=com_cmbSL_Code',900); 
        
         if(rcells.item(2).firstChild.value=="CR")
         document.Cash_Receipt_Edit_Form.rad_sub_CR_DR[0].checked=true;
         else if(rcells.item(2).firstChild.value=="DR")
         document.Cash_Receipt_Edit_Form.rad_sub_CR_DR[1].checked=true;
         
      //try{document.getElementById("cmbSL_Code").value=rcells.item(4).firstChild.value;}catch(e){}
       
       //try{document.getElementById("txtsub_Recei_from").value=rcells.item(5).firstChild.value;}catch(e){}
       try{document.getElementById("txtsub_Amount").value=rcells.item(5).firstChild.value;}catch(e){}
       try{document.getElementById("txtParticular").value=rcells.item(6).firstChild.value;}catch(e){}
       
    document.Cash_Receipt_Edit_Form.cmdupdate.style.display='block';
    document.Cash_Receipt_Edit_Form.cmddelete.disabled=true;
    document.Cash_Receipt_Edit_Form.cmdadd.style.display='none';
}


/////////////////////////////////////////////   ADD & UPDATE & DELETE /////////////////////////////////////////////////////
function ADD_GRID()
{
        if(document.getElementById("txtAcc_HeadCode").value.length==0)
        {
        alert("Enter A/c Head Code");
        return false;
        }
       /* if(document.Cash_Receipt_Edit_Form.rad_sub_CR_DR[1].checked==true)
        {
            alert("Debit amount not allowed");
            return false;
        }*/
      /*  
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
        */
        /*
            if(document.getElementById("txtsub_Recei_from").value.length==0)
            {
            alert("Enter the value in ' Received From ' Field");
            document.getElementById("txtsub_Recei_from").focus();
            return false;    
            }
        */
        
     //~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
        if(document.getElementById("txtAcc_HeadDesc").value=="")
       {
            alert("Please Wait Account Head is Loading .......................");            
            return false;        
       }       
       
       var tbody=document.getElementById("grid_body");
           if(tbody.rows.length>0)
            {
             rows=tbody.getElementsByTagName("tr");
             var txtAcc_HeadCode= document.getElementById("txtAcc_HeadCode").value;
              for(i=0;i<rows.length;i++)
               {
                    var cells=rows[i].cells;
                  
                
                    var ac_code=cells.item(1).lastChild.nodeValue;
                    var units=cells.item(3).lastChild.nodeValue;
                  //  alert("units"+units);
                    var code1= ac_code.split("-");   
                 //  alert(code1[0]);
                    if(code1[0]==900108)
                    {
                        if(units=="Accounting Units")
                        {
                        if(code1[0]==txtAcc_HeadCode){
                            var offcode=cells.item(4).lastChild.nodeValue;
                           var chkslcode= document.getElementById("cmbSL_Code").options[document.getElementById("cmbSL_Code").selectedIndex].text;
                       
                           if(offcode==chkslcode)
                            {
                            alert("Already Added For this A/c unit. Choose Different Unit For 900108");
                             return false;
                            }
                            }
                        }
                    }
                }
       }
       
//       if ( isMan.account_head_status) 
//       {
//        
//        if(document.getElementById("cmbSL_type").value=="")
//        {
//            alert("Select The Sub Ledger Type")       ;
//            document.getElementById("cmbSL_type").focus();
//            return false;        
//        }
//        
//        if(document.getElementById("cmbSL_Code").value=="")
//        {
//            alert("Select The Sub Ledger Code")       ;
//            document.getElementById("cmbSL_Code").focus();
//            return false;        
//        }        
//       } else
//       {
//            if(document.getElementById("cmbSL_type").length>1 && document.getElementById("cmbSL_type").value=="")
//            {
//               
//                                alert("Select a Sub-Ledger Type");
//                                return false;
//                           
//            }
//            if(document.getElementById("cmbSL_type").value!="")
//            {
//                if(document.getElementById("cmbSL_Code").value=="")
//                    {
//                        alert("Select The Sub Ledger Code")       ;
//                        document.getElementById("cmbSL_Code").focus();
//                        return false;
//                    }
//            }       
//        }  
        
    //~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~      
        
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
        if(document.Cash_Receipt_Edit_Form.rad_sub_CR_DR[0].checked==true)
          items[2]=document.Cash_Receipt_Edit_Form.rad_sub_CR_DR[0].value;
        else if(document.Cash_Receipt_Edit_Form.rad_sub_CR_DR[1].checked==true)
          items[2]=document.Cash_Receipt_Edit_Form.rad_sub_CR_DR[1].value;
        
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
        alert("row ID"+mycurrent_row.id);
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
            
             cell2=document.createElement("TD");
                  var SL_code=document.createElement("input");
                  SL_code.type="hidden";
                  SL_code.name="SL_code";
                  SL_code.value=items[5];
                  cell2.appendChild(SL_code);
                   var currentText=document.createTextNode(items[6]);
                  cell2.appendChild(currentText);
                mycurrent_row.appendChild(cell2);
            
           /*  cell2=document.createElement("TD");  
             
                  var rec_from=document.createElement("input");
                  rec_from.type="hidden";
                  rec_from.name="rec_from";
                  rec_from.value=items[7];
                  cell2.appendChild(rec_from);
                  var currentText=document.createTextNode(items[7]);
                cell2.appendChild(currentText);
                mycurrent_row.appendChild(cell2);
             */
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
         clearall();
}


function update_GRID()
{      
        if(document.getElementById("txtAcc_HeadCode").value.length==0)
        {
        alert("Enter A/c Head Code");
        return false;
        }
        
        var tbody=document.getElementById("grid_body");
           if(tbody.rows.length>0)
            {
             rows=tbody.getElementsByTagName("tr");
             var txtAcc_HeadCode= document.getElementById("txtAcc_HeadCode").value;
              for(i=0;i<rows.length;i++)
               {
                    var cells=rows[i].cells;
                  
                
                    var ac_code=cells.item(1).lastChild.nodeValue;
                    var units=cells.item(3).lastChild.nodeValue;
                  //  alert("units"+units);
                    var code1= ac_code.split("-");   
                 //  alert(code1[0]);
                    if(code1[0]==900108)
                    {
                        if(units=="Accounting Units")
                        {
                        if(code1[0]==txtAcc_HeadCode){
                            var offcode=cells.item(4).lastChild.nodeValue;
                           var chkslcode= document.getElementById("cmbSL_Code").options[document.getElementById("cmbSL_Code").selectedIndex].text;
                       
                           if(offcode==chkslcode)
                            {
                            alert("Already Added For this A/c unit. Choose Different Unit For 900108");
                             return false;
                            }
                            }
                        }
                    }
                }
       }
       
        /*if(document.Cash_Receipt_Edit_Form.rad_sub_CR_DR[1].checked==true)
        {
            alert("Debit amount not allowed");
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
            alert("Select The Sub Ledger Code")       ;
            return false;
            }
        }
        /*
            if(document.getElementById("txtsub_Recei_from").value.length==0)
            {
            alert("Enter the value in ' Received From ' Field");
            document.getElementById("txtsub_Recei_from").focus();
            return false;    
            }
        */

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
        if(document.Cash_Receipt_Edit_Form.rad_sub_CR_DR[0].checked==true)
          items[2]=document.Cash_Receipt_Edit_Form.rad_sub_CR_DR[0].value;
        else if(document.Cash_Receipt_Edit_Form.rad_sub_CR_DR[1].checked==true)
          items[2]=document.Cash_Receipt_Edit_Form.rad_sub_CR_DR[1].value;
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

/////////////////////////////////////////////   clearall() by User /////////////////////////////////////////////////////

function clearall()
{
     document.getElementById("offlist_div_trans").style.display='none';
     document.getElementById("emplist_div_trans").style.display='none';
     
    document.getElementById("txtAcc_HeadCode").value="";
    //document.getElementById("txtAcc_HeadCode").readOnly=false;
    document.getElementById("txtAcc_HeadDesc").value="";
    document.Cash_Receipt_Edit_Form.rad_sub_CR_DR[0].checked=true;
    document.getElementById("cmbSL_type").value="";
    document.getElementById("cmbSL_Code").value="";
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

 document.Cash_Receipt_Edit_Form.cmdadd.style.display='block';
 document.Cash_Receipt_Edit_Form.cmdupdate.style.display='none';
 document.Cash_Receipt_Edit_Form.cmddelete.disabled=true;
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
    document.getElementById("txtReceipt_No").value="";  
    clearGeneral_Detail();
}

function clearGeneral_Detail()
{
    document.getElementById("txtRecei_from").value="";
    document.getElementById("txtAmount").value="";  
    //document.getElementById("txtReceipt_No").value="";  
    document.getElementById("txtRef_no").value="";
    document.getElementById("txtRef_date").value="";
    document.getElementById("txtRemarks").value="";
    /*
    //Here Modification has taken out of form , b'coz it has separate interface
    document.getElementById("txtAuth_By").value="";
    document.getElementById("Auth_By").value="";
    document.getElementById("txtReferNO_edit").value="";
    document.getElementById("txtReferDate_edit").value="";
    document.getElementById("txtRemak_edit").value=""; 
    */
    document.getElementById("cmbMas_SL_type").value=""; 
    clear_Combo(document.getElementById("cmbMas_SL_Code"));
    var tbody=document.getElementById("grid_body");
    var t=0;
    for(t=tbody.rows.length-1;t>=0;t--)
    {
       tbody.deleteRow(0);
    }
}

////////////////////

function load_Receipt_Details(baseResponse)
{
    var flag=baseResponse.getElementsByTagName("flag")[0].firstChild.nodeValue;
   // alert(flag);
    if(flag=="success")
    {
		if(baseResponse.getElementsByTagName("Ref_No")[0].firstChild!=undefined)
      var Ref_No=baseResponse.getElementsByTagName("Ref_No")[0].firstChild.nodeValue;
    
     var Ref_Date=baseResponse.getElementsByTagName("Ref_Date")[0].firstChild.nodeValue;
       var Total_amt=baseResponse.getElementsByTagName("Total_amt")[0].firstChild.nodeValue;
       var Rec_From=baseResponse.getElementsByTagName("Rec_From")[0].firstChild.nodeValue;
    	if(baseResponse.getElementsByTagName("Remak")[0].firstChild!=undefined)
       var Remak=baseResponse.getElementsByTagName("Remak")[0].firstChild.nodeValue;
      
       var Mas_SL_type=baseResponse.getElementsByTagName("Mas_SL_type")[0].firstChild.nodeValue;
      var Mas_SL_code=baseResponse.getElementsByTagName("Mas_SL_code")[0].firstChild.nodeValue;
     // alert("lll");
       
       
       if(Mas_SL_type!=0)
       document.getElementById("cmbMas_SL_type").value=Mas_SL_type;
       
      if(Mas_SL_type!=0)
      {
     
            if(Mas_SL_type==5)
             {
                  document.getElementById("offlist_div_master").style.display='block';
                  document.getElementById("txtOfficeID_mas").value=Mas_SL_code;
                  //alert("USE search ICON to select the office");
             }
             else
              document.getElementById("offlist_div_master").style.display='none';
            
            if(Mas_SL_type==7)
             {
                  document.getElementById("emplist_div_master").style.display='block';
                  document.getElementById("txtEmpID_mas").value=Mas_SL_code;
                  //alert("USE search ICON to select the office");
             }
             else
              document.getElementById("emplist_div_master").style.display='none';
              
            var items_SLcode=new Array();
            var items_SLdesc=new Array();
            var Mas_SLCODE=baseResponse.getElementsByTagName("cid");
                  
            for(var k=0;k<Mas_SLCODE.length;k++)
            {
                items_SLcode[k]=baseResponse.getElementsByTagName("cid")[k].firstChild.nodeValue;
                items_SLdesc[k]=baseResponse.getElementsByTagName("cname")[k].firstChild.nodeValue;
            }
            cmbSL_type=document.getElementById("cmbMas_SL_Code")
            cmbSL_type.innerHTML="";
            var option=document.createElement("OPTION");
            option.text="--Select Code--";
            option.value="";
            try
            {
                cmbSL_type.add(option);
            }catch(errorObject)
            {
                cmbSL_type.add(option,null);
            }
            for(var k=0;k<Mas_SLCODE.length;k++)
            {   
              var option=document.createElement("OPTION");
              option.text=items_SLdesc[k];
              option.value=items_SLcode[k];
               try
              {
                  cmbSL_type.add(option);
              }
              catch(errorObject)
              {
                  cmbSL_type.add(option,null);
              }
            }
          
            document.getElementById("cmbMas_SL_Code").value=Mas_SL_code;    //set from grid
            
      }

      if(Ref_No!="null")
      document.getElementById("txtRef_no").value=Ref_No;
      else
      document.getElementById("txtRef_no").value="";
      
       if(Ref_Date!="null")
       document.getElementById("txtRef_date").value=Ref_Date;
       else
       document.getElementById("txtRef_date").value="";
      
       document.getElementById("txtAmount").value=Total_amt;
            
    /*---------------------------------------------------------------------------------*/            
       /*
        * Do you want to reclassify this voucher after remitting into bank ?    
        */
         
       var MO_creation=baseResponse.getElementsByTagName("MO_creation")[0].firstChild.nodeValue;
          
       if(MO_creation=='S')
       {
        document.getElementById("txtAmount").readOnly=true;
        document.Cash_Receipt_Edit_Form.rad_ReClass[0].checked=true;
       } 
       else
       {
     
        document.getElementById("txtAmount").readOnly=false;
      //  document.Cash_Receipt_Edit_Form.rad_ReClass[1].checked=true;
       } 
      
          
       var rem_in_curr_month=baseResponse.getElementsByTagName("rem_in_curr_month")[0].firstChild.nodeValue;
       if(rem_in_curr_month=='Y')
        document.Cash_Receipt_Edit_Form.rem_current_month[0].checked=true;
       else
        document.Cash_Receipt_Edit_Form.rem_current_month[1].checked=true;
        
     /*---------------------------------------------------------------------------------*/           
      if(Rec_From!="null")
      document.getElementById("txtRecei_from").value=Rec_From;
      else
      document.getElementById("txtRecei_from").value="";
      
       if(Remak!="null")
         document.getElementById("txtRemarks").value=Remak;
        else
        document.getElementById("txtRemarks").value="";
       
       //var miHC =baseResponse.getElementsByTagName("miHC")[0].firstChild.nodeValue;
       
       var tbody=document.getElementById("grid_body");
                        var t=0;
                        for(t=tbody.rows.length-1;t>=0;t--)
                        {
                           tbody.deleteRow(0);
                        }
                        
         //var SLCODE=baseResponse.getElementsByTagName("SLCODE");
        
         var AHcode=baseResponse.getElementsByTagName("AHcode");
        var items=new Array();
        for(var k=0;k<AHcode.length;k++)
        {
        
        items[0]=baseResponse.getElementsByTagName("AHcode")[k].firstChild.nodeValue;   
        items[1]=baseResponse.getElementsByTagName("AHdesc")[k].firstChild.nodeValue;   
         items[2]=baseResponse.getElementsByTagName("CR_DR_ind")[k].firstChild.nodeValue;
         items[3]=baseResponse.getElementsByTagName("SL_Type")[k].firstChild.nodeValue;
         if(items[3]==0)
         items[3]="";
        items[4]=baseResponse.getElementsByTagName("SL_Desc")[k].firstChild.nodeValue;
        if(items[4]=="null")
        items[4]="";
        items[5]=baseResponse.getElementsByTagName("SL_Code")[k].firstChild.nodeValue;
        if(items[5]==0)
        items[5]="";
        items[6]=baseResponse.getElementsByTagName("desc_type")[k].firstChild.nodeValue;
        if(items[6]=="null")
        items[6]="";
        items[7]=baseResponse.getElementsByTagName("sub_rec_frm")[k].firstChild.nodeValue;
        if(items[7]=="null")
        items[7]="";
        items[8]=baseResponse.getElementsByTagName("sub_amount")[k].firstChild.nodeValue;
        items[9]=baseResponse.getElementsByTagName("sub_part")[k].firstChild.nodeValue;
        if(items[9]=="null")
        items[9]="";
        if(items[10]=baseResponse.getElementsByTagName("CB_REF_NO")[k]=null)
        items[10]=baseResponse.getElementsByTagName("CB_REF_NO")[k].firstChild.nodeValue;
        if(items[10]=="null")
            items[10]="";
        //alert("10>>CB_REF_NO>>"+k+">>"+ baseResponse.getElementsByTagName("CB_REF_NO")[k].firstChild.nodeValue);
        if (items[11]=baseResponse.getElementsByTagName("CB_REF_DATE")[k]=null)
        items[11]=baseResponse.getElementsByTagName("CB_REF_DATE")[k].firstChild.nodeValue;   
        if(items[11]=="null")
            items[11]="";
       // alert("11>>CB_REF_DATE>>"+k+">>"+ baseResponse.getElementsByTagName("CB_REF_DATE")[k].firstChild.nodeValue);
       if(items[12]=baseResponse.getElementsByTagName("MULTIPLE_PVRS")[k]=null)
        items[12]=baseResponse.getElementsByTagName("MULTIPLE_PVRS")[k].firstChild.nodeValue;
        if(items[12]=="null")
            items[12]="";
        //alert("12>>MULTIPLE_PVRS>>"+k+">>"+ baseResponse.getElementsByTagName("MULTIPLE_PVRS")[k].firstChild.nodeValue);
        if(items[13]=baseResponse.getElementsByTagName("MULTIPLE_PVR_DETAILS")[k]=null)
        items[13]=baseResponse.getElementsByTagName("MULTIPLE_PVR_DETAILS")[k].firstChild.nodeValue;
        if(items[13]=="null")
            items[13]="";
        //alert("13>>MULTIPLE_PVR_DETAILS>>"+k+">>"+ baseResponse.getElementsByTagName("MULTIPLE_PVR_DETAILS")[k].firstChild.nodeValue);
        
        document.getElementById("CB_REF_NO").value=items[10];   
        //alert(document.getElementById("CB_REF_NO").value);
        document.getElementById("CB_REF_DATE").value=items[11];   
        //alert(document.getElementById("CB_REF_DATE").value);
        document.getElementById("MULTIPLE_PVRS").value=items[12];
        //alert(document.getElementById("MULTIPLE_PVRS").value);
        document.getElementById("MULTIPLE_PVR_DETAILS").value=items[13];
        //alert(document.getElementById("MULTIPLE_PVR_DETAILS").value);
        
        
        
         tbody=document.getElementById("grid_body");
        var mycurrent_row=document.createElement("TR");
        seq=seq+1;
        mycurrent_row.id=seq;
        //alert("row ID"+mycurrent_row.id);
       // alert("CB_Ref_NO>>>>"+items[10]);
        if (items[10]==0||items[10]==null) {
        
        var cell=document.createElement("TD");
        var anc=document.createElement("A");
        var url="javascript:loadTable('"+mycurrent_row.id+"')";
        anc.href=url;
        var txtedit=document.createTextNode("EDIT");
        anc.appendChild(txtedit);
        cell.appendChild(anc);
        mycurrent_row.appendChild(cell);
        }
        else if (items[10]!=0)
        	{
        	var cell=document.createElement("TD");
            var anc=document.createElement("A");
            var url="javascript:loadTable('"+mycurrent_row.id+"')";
            var txtedit=document.createTextNode("EDIT");
            anc.appendChild(txtedit);
            cell.appendChild(anc);
            mycurrent_row.appendChild(cell);
        	
        	}
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
          /*   cell2=document.createElement("TD");  
             
                  var rec_from=document.createElement("input");
                  rec_from.type="hidden";
                  rec_from.name="rec_from";
                  rec_from.value=items[7];
                  cell2.appendChild(rec_from);
                  var currentText=document.createTextNode(items[7]);
                cell2.appendChild(currentText);
                mycurrent_row.appendChild(cell2);
            */ 
             cell2=document.createElement("TD"); 
                  var sl_amt=document.createElement("input");
                  sl_amt.type="hidden";
                  sl_amt.name="sl_amt";
                  sl_amt.value=items[8];
                  cell2.appendChild(sl_amt);
                  var currentText=document.createTextNode(items[8]);
                cell2.appendChild(currentText);
                mycurrent_row.appendChild(cell2);
              cell2=document.createElement("TD");
             
                  var particular=document.createElement("input");
                  particular.type="hidden";
                  particular.name="particular";
                  particular.value=items[9];
                  cell2.appendChild(particular);
                  var currentText=document.createTextNode(items[9]);
                   cell2.appendChild(currentText);
                mycurrent_row.appendChild(cell2);
                
                //cell2=document.createElement("TD");
                var CB_REF_NO=document.createElement("input");
                CB_REF_NO.type="hidden";
                CB_REF_NO.name="CB_REF_NO";
                CB_REF_NO.value=items[10];
                cell2.appendChild(CB_REF_NO);
                
                //alert("CB_REF_NO=**********=>"+CB_REF_NO.value);
//                 
                // cell2=document.createElement("TD");
                 var CB_REF_DATE=document.createElement("input");
                 CB_REF_DATE.type="hidden";
                 CB_REF_DATE.name="CB_REF_DATE";
                 CB_REF_DATE.value=items[11];
                 cell2.appendChild(CB_REF_DATE);
                
                 //alert("CB_REF_DATE=**********=>"+items[11]);
                
                 //cell2=document.createElement("TD");
                 var MULTIPLE_PVRS=document.createElement("input");
                 MULTIPLE_PVRS.type="hidden";
                 MULTIPLE_PVRS.name="MULTIPLE_PVRS";
                 MULTIPLE_PVRS.value=items[12];
                 cell2.appendChild(MULTIPLE_PVRS);
                
                 
                // cell2=document.createElement("TD");
                 var MULTIPLE_PVR_DETAILS=document.createElement("input");
                 MULTIPLE_PVR_DETAILS.type="hidden";
                 MULTIPLE_PVR_DETAILS.name="MULTIPLE_PVR_DETAILS";
                 MULTIPLE_PVR_DETAILS.value=items[13];
                 cell2.appendChild(MULTIPLE_PVR_DETAILS);
                
                
                
        tbody.appendChild(mycurrent_row);
        }
    }
    else if(flag=="failure")
     alert("Failed to load data");
}


function load_Receipt_No(baseResponse)
{
var flag=baseResponse.getElementsByTagName("flag")[0].firstChild.nodeValue;
 var txtReceipt_No=document.getElementById("txtReceipt_No");
  if(flag=="success")
    {
           var items_id=new Array();
           var Rec_No=baseResponse.getElementsByTagName("Rec_No");
            
            for(var k=0;k<Rec_No.length;k++)
            {
                items_id[k]=baseResponse.getElementsByTagName("Rec_No")[k].firstChild.nodeValue;
                
            }
         
            txtReceipt_No.innerHTML="";
            var option=document.createElement("OPTION");
            option.text="--Select Receipt Number--";
            option.value="";
            try
            {
                txtReceipt_No.add(option);
            }catch(errorObject)
            {
                txtReceipt_No.add(option,null);
            }
            
            for(var k=0;k<items_id.length;k++)
            {   
                  var option=document.createElement("OPTION");
                  option.text=items_id[k];
                  option.value=items_id[k];
                   try
                  {
                      txtReceipt_No.add(option);
                  }
                  catch(errorObject)
                  {
                      txtReceipt_No.add(option,null);
                  }
            }
    }
    else if(flag=="failure")
    {
            txtReceipt_No.innerHTML="";
            var option=document.createElement("OPTION");
            option.text="--Select Receipt Number--";
            option.value="";
            try
            {
                txtReceipt_No.add(option);
            }catch(errorObject)
            {
                txtReceipt_No.add(option,null);
            }
         alert("No Receipt Found");
    }
}

/////////////////////////////////////////////   checkNull() by User /////////////////////////////////////////////////////

function checkNull()
        {
        var tbody=document.getElementById("grid_body");
           //alert("tbody.rows.length :"+tbody.rows.length);   
        //alert(document.getElementById("txtReferNO_edit").value.length)
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
        if(document.getElementById("txtCash_Acc_code").value.length==0)
        {
            alert("Enter the Cash A/c Code");
            //document.getElementById("txtCash_Acc_code").focus();
            return false;
        }
        
        if(document.getElementById("txtReceipt_No").value=="")
        {
            alert("Select the Receipt Number");
            //document.getElementById("txtReceipt_No").focus();
            return false;    
        }
        
        if(document.getElementById("txtRecei_from").value.length==0)
        {
            alert("Enter the value in  ' Received From Field ' in General part");
            //document.getElementById("txtRecei_from").focus();
            return false;    
        }
        
        if(document.getElementById("cmbMas_SL_type").value!="")
        {
            if(document.getElementById("cmbMas_SL_Code").value=="")
            {
            alert("Select The Sub Ledger Code in General");
            return false;
            }
        }
         if(document.getElementById("txtAmount").value.length==0)
        {
            alert("Enter the Total Amount in General");
            //document.getElementById("txtAmount").focus();
            return false;    
        }
        if(tbody.rows.length==0)
        {
            alert("Enter the Amount Details under Details");
            //document.getElementById("txtAmount").focus();
            return false; 
        }
        /*  //Here Modification has taken out of form , b'coz it has separate interface
        if(document.getElementById("txtAuth_By").value.length==0)
        {
            alert("Enter Name of the Authorized person under Modification Details");
            //document.getElementById("txtReferNO_edit").focus();
            return false;    
        }
        */
        //alert(document.getElementById("txtReferNO_edit").value.length);
        /*if(document.getElementById("txtReferNO_edit").value.length==0)
        {
            alert("Enter the Reference Number in Modification Details");
            //document.getElementById("txtReferNO_edit").focus();
            return false;    
        }
        if(document.getElementById("txtReferDate_edit").value.length==0)
        {
            alert("Enter the Reference Date in Modification Details");
            //document.getElementById("txtReferDate_edit").focus();
            return false;
        }
        
         if(document.getElementById("txtRemak_edit").value.length==0)
        {
            alert("Enter the Remarks in Modification Details");
           // document.getElementById("txtRemak_edit").focus();
            return false;
        }*/
        if(tbody.rows.length>0)
        {
                var check_amt=0;
                rows=tbody.getElementsByTagName("tr");
                for(i=0;i<rows.length;i++)
                {
                    var cells=rows[i].cells;
                    //alert(cells.item(2).lastChild.nodeValue);
                  if(cells.item(2).lastChild.nodeValue=='DR')
                      check_amt=parseFloat(check_amt) - parseFloat(cells.item(5).firstChild.value);
                  else
                      check_amt=parseFloat(check_amt) + parseFloat(cells.item(5).firstChild.value);
                                 //alert(check_amt);
                }
                //alert(check_amt);
                check_amt=Math.abs(check_amt);
                //alert(document.getElementById("txtAmount").value+"  "+check_amt);
                if(parseFloat(document.getElementById("txtAmount").value)!=parseFloat(check_amt))
                {
                alert("Amount doesn't Tally.. Difference " +(parseFloat(document.getElementById("txtAmount").value)-parseFloat(check_amt)))
                return false;
                }
        }
        
        return true;
}


///////////////////////////////////////////    TB_checking and Calender control return value handling

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
                 doFunction_voucher('load_Receipt_No','null');                 //return true;
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
}


function loadName_Mas(value)
{
 if(document.getElementById("cmbMas_SL_Code").value=="")
    return;
value=document.getElementById("cmbMas_SL_Code").options[document.getElementById("cmbMas_SL_Code").selectedIndex].text; 
s=document.getElementById("cmbMas_SL_type").value;
if(s=="7" )
value=value.substring(0,value.indexOf("-"));

document.getElementById("txtRecei_from").value=value;
}


//~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~

function check_leng(remarks,param)
{
  if(param=='remarks')
  {
    if((remarks.length)>=240)
    {
    alert("Please Enter Remarks below 250 characters");
    }
  }
  
  if(param=='received_from') 
  {
    if((remarks.length)>=45)
    {
    alert("Please Enter Received From name below 50 characters");
    }
  }
  
  if(param=='particulars') 
  {
    if((remarks.length)>=190)
    {
    alert("Please Enter Paticulars below 200 characters");
    }
  }
}

//~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
 
 function checkSubLedgerMandatory()
{
var Option="test";
var txtUnitId=0;
var txtAcc_HeadCode = document.getElementById("txtAcc_HeadCode").value;
	url = "../../../../../TDA_Raised_Create?command=checkSubLedgerMandatory&txtAcc_HeadCode="
			+ txtAcc_HeadCode + "&Option="+ Option + "&txtUnitId="+ txtUnitId;                      
	var req = getTransport();
	req.open("GET", url, true);
	req.onreadystatechange = function() {
		Mandatory(req);
	}
	req.send(null);

}

function Mandatory(req) {

	if (req.readyState == 4) {
		if (req.status == 200) {
			var baseResponse = req.responseXML.getElementsByTagName("response")[0];
			var tagcommand = baseResponse.getElementsByTagName("command")[0];
			var Command = tagcommand.firstChild.nodeValue;
			var flag = baseResponse.getElementsByTagName("flag")[0].firstChild.nodeValue;
			if (Command == "checkSubLedgerMandatory") {
				if (flag == "success") {
					var M_flag = baseResponse.getElementsByTagName("M_flag")[0].firstChild.nodeValue;
                                       
					if(M_flag=="Madatory")
                                        {
                                      
                                       ADD_GRID();
					}
                                        else if(M_flag=="Not_Madatory")
                                        {
                                         if(document.getElementById("cmbSL_type").value=="")
                                        {
                                        alert("Select a Sub-Ledger Type");
		                        return false;
                                        }
                                        if(document.getElementById("cmbSL_Code").value=="")
                                        {
                                        alert("Select a Sub-Ledger Code");
		                        return false;
                                        }
                                        else
                                        {
                                       ADD_GRID();
                                        }
                                        }
				}
			}
		}
	}
}


function checkSubLedgerMandatory1()
{
var Option="test";
var txtUnitId=0;
var txtAcc_HeadCode = document.getElementById("txtAcc_HeadCode").value;
	url = "../../../../../TDA_Raised_Create?command=checkSubLedgerMandatory&txtAcc_HeadCode="
			+ txtAcc_HeadCode + "&Option="+ Option + "&txtUnitId="+ txtUnitId;
	var req = getTransport();
	req.open("GET", url, true);
	req.onreadystatechange = function() {
		Mandatory1(req);
	}
	req.send(null);

}

function Mandatory1(req) {

	if (req.readyState == 4) {
		if (req.status == 200) {
			var baseResponse = req.responseXML.getElementsByTagName("response")[0];
			var tagcommand = baseResponse.getElementsByTagName("command")[0];
			var Command = tagcommand.firstChild.nodeValue;
			var flag = baseResponse.getElementsByTagName("flag")[0].firstChild.nodeValue;
			if (Command == "checkSubLedgerMandatory") {
				if (flag == "success") {
					var M_flag = baseResponse.getElementsByTagName("M_flag")[0].firstChild.nodeValue;
                                       
					if(M_flag=="Madatory")
                                        {
                                      
                                        update_GRID();
                                        //acheadcodecheck('2');
					}
                                        else if(M_flag=="Not_Madatory")
                                        {
                                         if(document.getElementById("cmbSL_type").value=="")
                                        {
                                        alert("Select a Sub-Ledger Type");
		                        return false;
                                        }
                                        if(document.getElementById("cmbSL_Code").value=="")
                                        {
                                        alert("Select a Sub-Ledger Code");
		                        return false;
                                        }
                                        else
                                        {
                                       update_GRID();
                                        }
                                        }
				}
			}
		}
	}
}

