var seq=0;
var seqNT=0;

var RsnCode = new Array();
var RsnDesc = new Array();

/** Get Browser Object */

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

/** Allows Number only */
function numbersonly1(e, t) {
	var unicode = e.charCode ? e.charCode : e.keyCode;
	if (unicode == 13) {
		try {
			t.blur();
		} catch (e) {
		}
		return true;

	}
	if (unicode != 8 && unicode != 9) {
		if (unicode < 48 || unicode > 57)
			return false
	}
}
 
var Common_branchID="";

//~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ XML req ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~

var window_BankAccNumber;

function ListHeads()
{ 
 
 
        if (window_BankAccNumber && window_BankAccNumber.open && !window_BankAccNumber.closed) 
         {
             window_BankAccNumber.resizeTo(500,500);
             window_BankAccNumber.moveTo(250,250); 
             window_BankAccNumber.focus();
         }
         else
         {
             window_BankAccNumber=null
         }
         window_BankAccNumber= window.open("BRS_Reason_Catalogue_List.jsp","mywindow1","resizable=YES, scrollbars=yes"); 
         window_BankAccNumber.moveTo(250,250);    
 
}

//~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~   
   
window.onunload=function()
{
    if (window_BankAccNumber && window_BankAccNumber.open && !window_BankAccNumber.closed) window_BankAccNumber.close();
}

//~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~


function doParentBankAccNumbers(reason_code,reason_short_desc, reason_desc )
{
            var d=document.getElementById("cmdUpdate");
            d.style.display="block";
            var d2=document.getElementById("cmdDelete");
            d2.style.display="block";
            var d1=document.getElementById("cmdAdd");
            d1.style.display="none";          
       
    document.getElementById("txtReasonCode").value=reason_code;
    document.getElementById("txtReasonShortDesc").value=reason_short_desc;
    document.getElementById("txtReasonDesc").value=reason_desc;            
}


//~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~



/**
 *   Main Function 
 */

function doFunction_brs_edit(Command,ListSeq)
{   
//   alert("inside edit js");
	var  cmbAcc_UnitCode  = document.getElementById("cmbAcc_UnitCode").value;
   var  cmbOffice_code   = document.getElementById("cmbOffice_code").value;
   var  txtCB_Year       = document.getElementById("txtCB_Year").value;   
   var  txtCB_Month      = document.getElementById("txtCB_Month").value;
   var  cmbBankAccNo      = document.getElementById("cmbBankAccNo").value;
   
            
        if(Command=="LoadTWADTransactions")
        {
//        	alert("inside");
                var url="../../../../../BRS_MainForm_Edit.kv?Command=LoadTWADTransactions&cmbAcc_UnitCode="+cmbAcc_UnitCode+"&cmbOffice_code="+cmbOffice_code+
                        "&txtCB_Year="+txtCB_Year+"&txtCB_Month="+txtCB_Month+"&cmbBankAccNo="+cmbBankAccNo+"&ListSeq="+ListSeq;            
                
                var req=getTransport();
                req.open("GET",url,true); 
                req.onreadystatechange=function()
                {
                   handleResponse_BRS_edit(req);
                };   
                   req.send(null);

        }if(Command=="LoadPassAmt"){
        	
        	  var url="../../../../../BRS_FollowUp?command=LoadPassAmt&cmbAcc_UnitCode="+cmbAcc_UnitCode+"&cmbOffice_code="+cmbOffice_code+
              "&txtCB_Year="+txtCB_Year+"&txtCB_Month="+txtCB_Month+"&cmbBankAccNo="+cmbBankAccNo;            
    
      var req=getTransport();
      req.open("POST",url,true); 
      req.onreadystatechange=function()
      {
    	 
         handleResponse_BRS_edit(req);
      };   
         req.send(null);
        }
        if(Command=="TotalTransactions")
        {
                var url="../../../../../BRS_MainForm_Edit.kv?ommand=TotalTransactions&cmbAcc_UnitCode="+cmbAcc_UnitCode+"&cmbOffice_code="+cmbOffice_code+
                        "&txtCB_Year="+txtCB_Year+"&txtCB_Month="+txtCB_Month+"&cmbBankAccNo="+cmbBankAccNo;            
                
                var req=getTransport();
                req.open("GET",url,true); 
                req.onreadystatechange=function()
                {
                   handleResponse_BRS_edit(req);
                };   
                   req.send(null);

        }
        
        if(Command=="LoadNONTWADTransactions")
        {
                var url="../../../../../BRS_MainForm_Edit.kv?Command=LoadNONTWADTransactions&cmbAcc_UnitCode="+cmbAcc_UnitCode+"&cmbOffice_code="+cmbOffice_code+
                        "&txtCB_Year="+txtCB_Year+"&txtCB_Month="+txtCB_Month+"&cmbBankAccNo="+cmbBankAccNo;            
                
                var req=getTransport();
                req.open("GET",url,true); 
                req.onreadystatechange=function()
                {
                   handleResponse_BRSNT_edit(req);
                }   
                   req.send(null);
        }

}
function handleResponse_BRSNT_edit(req)
{  
    if(req.readyState==4)
    {
        if(req.status==200)
        {  
            var baseResponse=req.responseXML.getElementsByTagName("response")[0];
            var tagcommand=baseResponse.getElementsByTagName("command")[0];
           
            var Command=tagcommand.firstChild.nodeValue;           
           
           
            if(Command=="LoadNONTWADTransactions")
            {
            	LoadNONTWADTransactions_edit(baseResponse);
            }
            
        }
    }
}

//~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~   handleResponse()   ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~

function handleResponse_BRS_edit(req)
{  
    if(req.readyState==4)
    {
    	
        if(req.status==200)
        {  
        	
            var baseResponse=req.responseXML.getElementsByTagName("response")[0];
            
            var tagcommand=baseResponse.getElementsByTagName("command")[0];
          
            var Command=tagcommand.firstChild.nodeValue;           
         
            if(Command=="LoadTWADTransactions")
            {
            	LoadTWADTransactions_edit(baseResponse);
            }      
            else if(Command=="TotalTransactions")
            {
            	TotalTransactions(baseResponse);
            } else if(Command=="LoadPassAmt") 
            {
            	//alert(baseResponse.getElementsByTagName("AMOUNT_IN_PASSBOOK")[0].firstChild.nodeValue);
            	if(baseResponse.getElementsByTagName("flag")[0].firstChild.nodeValue=="success"){
            //	document.getElementById("txtPBBalance").value=baseResponse.getElementsByTagName("AMOUNT_IN_PASSBOOK")[0].firstChild.nodeValue;
            	}
            	}
        }
    }
} 

//~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~

function LoadTWADTransactions_edit(baseResponse)
{
	//alert("bbbbbb"); 
	var  txtCB_Year       = document.getElementById("txtCB_Year").value;   
	   var  txtCB_Month      = document.getElementById("txtCB_Month").value;
	   if(txtCB_Month<10)
		   {
		   txtCB_Month="0"+txtCB_Month;
		   }
    var flag=baseResponse.getElementsByTagName("flag")[0].firstChild.nodeValue;
    
    if(flag=="success")
    {
   
    	/* Load TWAD Trasactions  Values */    	
    	
    	/** Delete Existing Values from Grid */
    	var tbody=document.getElementById("grid_body_TWAD");
        var t=0;
        for(t=tbody.rows.length-1;t>=0;t--)
        {
           tbody.deleteRow(0);
        }
    	
        /** Get DD Number Object for finding Total Number of Records */
        var r_no=baseResponse.getElementsByTagName("r_no");
        //alert("r_no.length----------"+r_no.length)
        var item=new Array();
        
        for(var k=0;k<r_no.length;k++)
        {
        	item[0]=baseResponse.getElementsByTagName("r_no")[k].firstChild.nodeValue;
        	if(item[0] == 'null' )
        	{
        		item[0]="";
        	}        	
        	
        	item[1]=baseResponse.getElementsByTagName("r_date")[k].firstChild.nodeValue;
        	if(item[1] == 'null' )
        	{
        		item[1]="";
        	}        
        	
        	item[3]=baseResponse.getElementsByTagName("r_ccdd_no")[k].firstChild.nodeValue;
        	if(item[3] == 'null' )
        	{
        		item[3]="";
        	}        	
        	
        	item[4]=baseResponse.getElementsByTagName("cr_amount")[k].firstChild.nodeValue;
        	if(item[4] == 'null' )
        	{
        		item[4]="";
        	}
        	
        	item[5]=baseResponse.getElementsByTagName("r_particulars")[k].firstChild.nodeValue;
        	
        	item[6]=baseResponse.getElementsByTagName("w_no")[k].firstChild.nodeValue;
        	if(item[6] == 'null' )
        	{
        		item[6]="";
        	}        
        	
        	item[7]=baseResponse.getElementsByTagName("w_date")[k].firstChild.nodeValue;
        	if(item[7] == 'null' )
        	{
        		item[7]="";
        	}
        	
        	item[9]=baseResponse.getElementsByTagName("w_ccdd_no")[k].firstChild.nodeValue;
        	if(item[9] == 'null' )
        	{
        		item[9]="";
        	}        	
        	
        	item[10]=baseResponse.getElementsByTagName("dr_amount")[k].firstChild.nodeValue;
        	if(item[10] == 'null' )
        	{
        		item[10]="";
        	}        	
        	
        	item[11]=baseResponse.getElementsByTagName("w_particulars")[k].firstChild.nodeValue;
        	item[12]=baseResponse.getElementsByTagName("EntryFoundInPassBook")[k].firstChild.nodeValue;
        	item[13]=baseResponse.getElementsByTagName("Entry_Date")[k].firstChild.nodeValue;        	
        	item[14]=baseResponse.getElementsByTagName("Amt_in_PassBk")[k].firstChild.nodeValue;
        	item[15]=baseResponse.getElementsByTagName("Amt_Diff")[k].firstChild.nodeValue;
        	item[16]=baseResponse.getElementsByTagName("cmbReason4Diff")[k].firstChild.nodeValue;
        	if(item[16] == 'null')
        	{        		
        		item[16] = "";        		
        	}
        	//alert(item[16]);
        	item[17]=baseResponse.getElementsByTagName("FollowUpAction")[k].firstChild.nodeValue;
        	item[18]=baseResponse.getElementsByTagName("ClearanceEntry")[k].firstChild.nodeValue;
                
                item[19]=baseResponse.getElementsByTagName("doc_no")[k].firstChild.nodeValue;    
                if(item[19] == 'null' || item[19] == '0' )
        	{
        		item[19]="";
        	}
                item[20]=baseResponse.getElementsByTagName("doc_type")[k].firstChild.nodeValue;    
                if(item[20] == 'null' || item[20] == '0' )
        	{
        		item[20]="";
        	}                                
        	
                item[21]=baseResponse.getElementsByTagName("com_doc_date")[k].firstChild.nodeValue;   
                //alert("item[20]"+item[20]);
                if(item[21] == 'null' || item[21] == '0' )
        	{
        		item[21]="";
        	}
                item[22] = baseResponse.getElementsByTagName("sl_no")[k].firstChild.nodeValue;
             //   alert(item[22]);
        	/** Create Table Row */
        	var mycurrent_row=document.createElement("TR");         
        	mycurrent_row.id=seq;
         
        	var cell = document.createElement("TD");
            var anc = document.createElement("input");
            anc.type = "checkbox";
            anc.id="check"+seq;
            anc.name="check"+seq;
            //dhana
            anc.setAttribute('onclick', "callme(" + seq + ")");
            anc.checked=true;
            anc.value=seq;
           // alert("anc.value:-->"+anc.value);
            cell.appendChild(anc);
            mycurrent_row.appendChild(cell);

        	/*var cell = document.createElement("TD");
            var anc = document.createElement("input");
            anc.type = "checkbox";
            anc.id="check"+seq;
            anc.name="check"+seq;
          //  alert("anc.name:-->"+anc.name);
           // anc.checked=true;
            if(anc.checked==true)
            {
            anc.value=seq;
            }else
            {
            	anc.value=-1;
            }
           // alert("anc.value:-->"+anc.value);
            cell.appendChild(anc);
            mycurrent_row.appendChild(cell);*/
            
            
          	/** Remittance Date Creation */
        	var cell2 = document.createElement("TD");
        	var r_date=document.createElement("input");
        	r_date.type="hidden";
        	r_date.name="r_date"+seq;
        	r_date.id="r_date"+seq;
        	r_date.value=item[1];
        	cell2.appendChild(r_date);
        	var currentText=document.createTextNode(item[1]);
        	cell2.appendChild(currentText);
        	mycurrent_row.appendChild(cell2);
             
             
            /** WithDrawl Date */
             var cell3=document.createElement("TD"); 
             var w_date=document.createElement("input");
             w_date.type="hidden";
             w_date.name="w_date"+seq;
             w_date.value=item[7];
             cell3.appendChild(w_date);
             var currentText=document.createTextNode(item[7]);
             cell3.appendChild(currentText);
             mycurrent_row.appendChild(cell3);
             
              
              /** Challan or Voucher Number Creation */
              var cell4=document.createElement("TD");
              var r_w_no=document.createElement("input");
              r_w_no.type="hidden";
              r_w_no.name="r_w_no"+seq;             
              r_w_no.value=item[0];
              cell4.appendChild(r_w_no);             
              var currentText=document.createTextNode(item[0]);
              cell4.appendChild(currentText);
              
              
              /** doc No */ 
              var doc_no=document.createElement("input");
              doc_no.type="hidden";
              doc_no.name="doc_no"+seq;
              doc_no.id="doc_no"+seq;
              doc_no.value=item[19];
              cell4.appendChild(doc_no);
              
              
              /** doc type */ 
              var doc_type=document.createElement("input");
              doc_type.type="hidden";
              doc_type.name="doc_type"+seq;
              doc_type.id="doc_type"+seq;
              doc_type.value=item[20];
              cell4.appendChild(doc_type);
              
              if ( item[20] == 'BR' || item[20] == 'CR' || item[20] == 'J' ) 
              {
                item[20]=item[20]+"("+item[19]+")";
              }
              
              var currentText=document.createTextNode(" -"+item[20] +" "  );
              cell4.appendChild(currentText);
              
              mycurrent_row.appendChild(cell4);
              
           
              /** Cheque / DD Number */              
              var cell5=document.createElement("TD");
              var ccdd_no=document.createElement("input");
              ccdd_no.type="hidden";
              ccdd_no.name="ccdd_no"+seq;
              ccdd_no.id="ccdd_no"+seq;
              ccdd_no.value=item[3];
              cell5.appendChild(ccdd_no);
              var currentText=document.createTextNode(item[3]);
              cell5.appendChild(currentText);
              mycurrent_row.appendChild(cell5);
              
            
              /** CR Amount */
              var cell6=document.createElement("TD");  
              var cr_amount=document.createElement("input");
              cr_amount.type="hidden";
              cr_amount.id="cr_amount"+seq;
              cr_amount.name="cr_amount"+seq;
              cr_amount.value=item[4];
              cell6.appendChild(cr_amount);
              var currentText=document.createTextNode(item[4]);
              cell6.appendChild(currentText);
              mycurrent_row.appendChild(cell6);
              
              
              /** DR Amount */
              var cell7=document.createElement("TD");  
              var dr_amount=document.createElement("input");
              dr_amount.type="hidden";
              dr_amount.id="dr_amount"+seq;
              dr_amount.name="dr_amount"+seq;
              dr_amount.value=item[10];
              
              cell7.appendChild(dr_amount);
              var currentText=document.createTextNode(item[10]);
              
              cell7.appendChild(currentText);
              mycurrent_row.appendChild(cell7);       
              
              
              /** Entry Found in Pass Book ? */
              var cell8=document.createElement("TD");
              cell8.style.textAlign='center';
              var sel="";            
              if (window.navigator.appName.toLowerCase().indexOf("netscape") == -1)
              {
            	  if (item[12] =='Y' ) 
                  {  
            	     sel=document.createElement("<INPUT type='checkbox' readonly='readonly' checked name='EntryFoundInPassBook"+seq+"' id='EntryFoundInPassBook"+seq+"' value='Y' />" );
                  }
            	  else
            	  {
             	     sel=document.createElement("<INPUT type='checkbox' readonly='readonly' name='EntryFoundInPassBook"+seq+"' id='EntryFoundInPassBook"+seq+"' value='Y' />" );
            	  }
              }
              else
              {   
                sel=document.createElement("input");     
                sel.type="checkbox";
                sel.setAttribute('readonly','readonly');
              //  sel.setAttribute('onclick',"notEdit("+seq+")");
                sel.name="EntryFoundInPassBook"+seq;
                sel.id="EntryFoundInPassBook"+seq;                     
                sel.value="Yy";  
                sel.checked=true;
                sel.disabled = true;
            /*    if (item[12] =='Y' ) 
                {
                  sel.checked=true;
                }
                else
                {
                  sel.checked=false;
                } 	*/
                
              }
              cell8.appendChild(sel);
              mycurrent_row.appendChild(cell8);
              
              /** Entry Date */
              var cell9=document.createElement("TD");  
              var Entry_Date=document.createElement("input");
              Entry_Date.type="Text";
              Entry_Date.name="Entry_Date"+seq;
              Entry_Date.id="Entry_Date"+seq;
             // Entry_Date.value="01/"+txtCB_Month+"/"+txtCB_Year;
              //dhana
              Entry_Date.value=item[13];
            //  alert(item[13])
              Entry_Date.setAttribute('onblur',"dateValidation("+seq+");dateValidationNEW("+seq+")");
              Entry_Date.size="10";
              Entry_Date.maxLength="10";
              cell9.appendChild(Entry_Date);
              mycurrent_row.appendChild(cell9);   
              
              /** Amount in Pass Book */
              var cell10=document.createElement("TD");  
              var Amt_in_PassBk=document.createElement("input");
              Amt_in_PassBk.type="Text";
            //  Amt_in_PassBk.readonly="true";
              Amt_in_PassBk.id="Amt_in_PassBk"+seq;
              Amt_in_PassBk.name="Amt_in_PassBk"+seq;
              Amt_in_PassBk.value=item[14];
              Amt_in_PassBk.setAttribute('readonly', 'readonly');
             // Amt_in_PassBk.setAttribute('onblur',"callDifference("+seq+"),callme("+seq+")");
             // Amt_Diff.setAttribute('onchange',"callme("+seq+")");
              Amt_in_PassBk.size="10";
              cell10.appendChild(Amt_in_PassBk);
              mycurrent_row.appendChild(cell10);  
              
              /** Amount Difference */
              var cell11=document.createElement("TD");  
              var Amt_Diff=document.createElement("input");
              Amt_Diff.type="Text";
              Amt_Diff.name="Amt_Diff"+seq;
              Amt_Diff.id="Amt_Diff"+seq;
              Amt_Diff.value=item[15];
              Amt_Diff.setAttribute('readonly', 'readonly');
            //  Amt_Diff.setAttribute('onchange',"callme("+seq+")");
              Amt_Diff.size="10";
              cell11.appendChild(Amt_Diff);
              mycurrent_row.appendChild(cell11);  
                           
                            
              /**
               *  Dynamic Combo Creation and Loading 
               */ 
              
              /* Reason for Difference */
              cell12=document.createElement("TD");
              cell12.style.textAlign='center';  
              var cmbReasonCode;
              
              if (window.navigator.appName.toLowerCase().indexOf("netscape") == -1)
              {
            	//  alert("NO1");
            	  try
                {
            	  cmbReasonCode =  document.createElement("<select name='cmbReason4Diff"+seq+"' id='cmbReason4Diff"+seq+"' >");
                }catch( e ) { alert(e.description) }
              }
              else
              {
            	  //alert("NO2");
            	  cmbReasonCode=document.createElement("select");                        
            	  cmbReasonCode.id="cmbReason4Diff"+seq;
            	  //alert("cmbReasonCode.id:-->>"+cmbReasonCode.id)
            	  cmbReasonCode.name="cmbReason4Diff"+seq;
              }
              
              var cmbReasonCodeObj = baseResponse.getElementsByTagName("reason_pair");
              var option11=document.createElement("option");    
                  option11.value="";  
                  option11.text="--Select--";
                  
             try
               {
            	 cmbReasonCode.add(option11);
               }
            catch(e)
               {
            	cmbReasonCode.add(option11,null);
               }                           
              for(var y=0;y<cmbReasonCodeObj.length;y++)
              {
              
                  RsnCode[y]=cmbReasonCodeObj[y].getElementsByTagName("reason_code")[0].firstChild.nodeValue;
                  RsnDesc[y]=cmbReasonCodeObj[y].getElementsByTagName("reason_desc")[0].firstChild.nodeValue;
                 
                 /*Load Balance as per Pass Book in General Part */
			   //   document.getElementById("txtPBBalance").value=cmbReasonCodeObj[y].getElementsByTagName("txtPBBalance")[0].firstChild.nodeValue;
			       
                  
                  var option11=document.createElement("option");    
                 
                  if (RsnDesc[y] == item[16])
                  {	  
                	 option11.selected=true;
                  }	 
                 // alert("RsnCode[y]:-->>"+RsnCode[y])
                  option11.value=RsnDesc[y];  
                  option11.text=RsnDesc[y];                  
                
                 try
                   {
                	 cmbReasonCode.add(option11);
                   }
                 catch(e)
                   {
                	 cmbReasonCode.add(option11,null);
                   }
                   
             }                         
             cell12.appendChild(cmbReasonCode);                                                    
             mycurrent_row.appendChild(cell12);   

              
      
             if(item[15] <=0)
             {
            	 cmbReasonCode.disabled = true;
             }else
             {
            	 cmbReasonCode.disabled = false;
             }
             
             /* Follow up Action Required */             
             var cell13=document.createElement("TD");
             cell13.style.textAlign='center';
             var sel="";            
             if (window.navigator.appName.toLowerCase().indexOf("netscape") == -1)
             {
            	 if (item[17] =='Y' ) 
                 {  	 
            		 sel=document.createElement("<INPUT type='checkbox' checked name='FollowUpAction"+seq+"' id='FollowUpAction"+seq+"' value='Y' />" );
                 }
            	 else
            	 {
            		 sel=document.createElement("<INPUT type='checkbox' name='FollowUpAction"+seq+"' id='FollowUpAction"+seq+"' value='Y' />" );
            	 }
            	 
             }
             else
             {    
               sel=document.createElement("input");     
               sel.type="checkbox";             
               sel.name="FollowUpAction"+seq;
               sel.id="FollowUpAction";
               sel.setAttribute('type', 'hidden');
               if (item[17] =='Y' ) 
               {
                  sel.checked=true;
               }
               else
               {
            	  sel.checked=false;
               }
               
               
               sel.value="Y";                          
             }
             cell13.appendChild(sel);
             mycurrent_row.appendChild(cell13);
          
             /* Is it a clearance entry based on follow-up */             
             var cell14=document.createElement("TD");
             cell14.style.textAlign='center';
             var sel="";            
             if (window.navigator.appName.toLowerCase().indexOf("netscape") == -1)
             {
            	if (item[18] =='Y' ) 
                { 
            	  sel=document.createElement("<INPUT type='checkbox' checked name='ClearanceEntry"+seq+"' id='ClearanceEntry"+seq+"' value='Y'/>" );
                }
            	else
            	{
                  sel=document.createElement("<INPUT type='checkbox' name='ClearanceEntry"+seq+"' id='ClearanceEntry"+seq+"' value='Y'/>" );
            	}
            	
             }
             else
             {  
               sel=document.createElement("input");     
               sel.type="checkbox";             
               sel.name="ClearanceEntry"+seq;
               sel.id="ClearanceEntry";
               sel.setAttribute('type', 'hidden');
               if (item[18] =='Y' ) 
               {
                 sel.checked = true;
               }
               else
               {
            	 sel.checked = false;
               }
               
               sel.value="Y";                          
             }
             cell14.appendChild(sel);
             mycurrent_row.appendChild(cell14);
              
             /** doc Date */ 
          	var doc_date = document.createElement("input");
          	doc_date.setAttribute("type","hidden");
          	doc_date.setAttribute("value",item[21]);
          	doc_date.setAttribute("name","doc_date"+seq);
          	doc_date.setAttribute("id","doc_date"+seq);
          	document.getElementById("frmBRSMainForm").appendChild(doc_date);     

          	/** sl No */ 
         	var slNoU = document.createElement("input");
         	slNoU.setAttribute("type","hidden");
         	slNoU.setAttribute("value",item[22]);
         	slNoU.setAttribute("name","slNoU"+seq);
         	slNoU.setAttribute("id","slNoU"+seq);
         	document.getElementById("frmBRSMainForm").appendChild(slNoU);      
         	
             tbody.appendChild(mycurrent_row);
              
             /** Increment Sequence Number */ 
             seq=seq+1;                 

        }
    	
        /** Load Non TWAD Transaction */
        doFunction_brs_edit('LoadNONTWADTransactions','0');
        
        
    }
    else
    {
        alert("No Twad Transaction Record Exits");
        
        /** Load Non TWAD Transaction */
        doFunction_brs_edit('LoadNONTWADTransactions','0');
        
    }
    
}

//~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~



function TotalTransactions(baseResponse)
{
	var flag=baseResponse.getElementsByTagName("flag")[0].firstChild.nodeValue;
    
    if(flag=="success")
    {
    	TotalTrans = baseResponse.getElementsByTagName("TotalTrans")[0].firstChild.nodeValue;    	    	
    	
    	var tbody=document.getElementById("grid_body_TotTrans");
        var t=0;
        for(t=tbody.rows.length-1;t>=0;t--)
        {
           tbody.deleteRow(0);
        }
    	
    	/** Create Table Row */
    	var mycurrent_row=document.createElement("TR");         
    	mycurrent_row.id=seq;    	
    
    	var Pagination = Math.floor(TotalTrans / 10) ;
    	
    	if ( ( TotalTrans / 10 ) > 0 )
    	{
    		Pagination = Pagination + 1; 
    	}
    	
    	/** Number Link Creation */
    	for ( var i=0 ; i < Pagination  ; i++)
    	{
    		var cell1=document.createElement("TD");
    		
    		var anc=document.createElement("A");
   			var url="javascript:loadTable('"+i+1+"')";
   			anc.href=url;   		   			
   			
   			var txtedit=document.createTextNode(i+1);   			
   			anc.appendChild(txtedit);
   			
   			cell1.appendChild(anc);	
   			mycurrent_row.appendChild(cell1);
   			
   			var cell2=document.createElement("TD");
   			cell2.innerHTML="&nbsp;&nbsp;&nbsp;";   				
   			mycurrent_row.appendChild(cell2);   			
    	}
    	
        tbody.appendChild(mycurrent_row);                             
        
    }	
}


//~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~

function loadTable(RecordsNum)
{
	doFunction_brs_edit('LoadTWADTransactions',RecordsNum);
	
}


//~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
function ClearAll()
{    
    document.getElementById("txtReasonCode").value="";
    document.getElementById("txtReasonShortDesc").value="";
    document.getElementById("txtReasonDesc").value="";    
        
        var d=document.getElementById("cmdAdd");
        d.style.display="block";
        var d1=document.getElementById("cmdUpdate");
        d1.style.display="none";
        var d3=document.getElementById("cmdDelete");
        d3.style.display="none";
}


//~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~

function nullcheck(nccommand)
{
     if(document.getElementById("txtReasonShortDesc").value.length==0)
     {
        alert("Enter Reason Short Description ");
        document.getElementById("txtReasonShortDesc").focus();
        return false;
     }
     if(document.getElementById("txtReasonDesc").value.length==0)
     {
        alert("Enter Reason Description ");
        document.getElementById("txtReasonDesc").focus();
        return false;
     }   
     return true;    
}

//~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
  
 
      
function LoadNONTWADTransactions_edit(baseResponse)
{
	alert("LoadNONTWADTransactions(baseResponse)");
                /* Load NON TWAD Trasactions Values */    	
                
                /** Get TBody Object */
                var tbody=document.getElementById("grid_body_NONTWAD");
                
                /** Delete Existing Values from Grid */
	        var t=0;
	        for(t=tbody.rows.length-1;t>=0;t--)
	        {
	           tbody.deleteRow(0);
	        }
	    	
			
			/** Get slno Number Object for finding Total Number of Records */
	        var len = baseResponse.getElementsByTagName("sl_no").length;
	        //var sl_no=baseResponse.getElementsByTagName("sl_no").length;
	        //alert("len:-->"+len);
	        var item=new Array();
	        
	        for(var k=0;k<len;k++)
	        {
	        	item[0]=baseResponse.getElementsByTagName("r_ccdd_no")[k].firstChild.nodeValue;
	        	if(item[0] == 'null' )
	        	{
	        		item[0]="";
	        	}        	
	        	//alert("item[0]:-->"+item[0]);
	        	item[1]=baseResponse.getElementsByTagName("cr_amount")[k].firstChild.nodeValue;
	        	if(item[1] == 'null' )
	        	{
	        		item[1]="";
	        	}
	        	//alert("item[1]:-->"+item[1]);
	        	item[2]=baseResponse.getElementsByTagName("r_particulars")[k].firstChild.nodeValue;
	        	//alert("item[2]:-->"+item[2]);
	        		        	
	        	item[3]=baseResponse.getElementsByTagName("w_ccdd_no")[k].firstChild.nodeValue;
	        	if(item[3] == 'null' )
	        	{
	        		item[3]="";
	        	}        	
	        	//alert("item[3]:-->"+item[3]);
	        	item[4]=baseResponse.getElementsByTagName("dr_amount")[k].firstChild.nodeValue;
	        	if(item[4] == 'null' )
	        	{
	        		item[4]="";
	        	}
	        	
	        	item[21]=baseResponse.getElementsByTagName("Details")[k].firstChild.nodeValue;    
                if(item[21] == 'null' || item[21] == '0' )
        	{
        		item[21]="";
        	}
                
                item[22]=baseResponse.getElementsByTagName("Journalized")[k].firstChild.nodeValue;    
                if(item[22] == 'null' || item[21] == '0' )
        	{
        		item[22]="";
        	}
              
                
	        	//alert("item[4]:-->"+item[4]);
	        	item[5]=baseResponse.getElementsByTagName("w_particulars")[k].firstChild.nodeValue;	        	
	        	item[6]=baseResponse.getElementsByTagName("Entry_Date")[k].firstChild.nodeValue;	        	
	        	item[7]=baseResponse.getElementsByTagName("FollowUpAction")[k].firstChild.nodeValue;
	        	item[8]=baseResponse.getElementsByTagName("ClearanceEntry")[k].firstChild.nodeValue;
	        	item[9]=baseResponse.getElementsByTagName("Trans_Type_NT")[k].firstChild.nodeValue;
	                 
                        item[10] = baseResponse.getElementsByTagName("sl_no")[k].firstChild.nodeValue;
                      /*  alert("item[5]:-->"+item[5]);
                        alert("item[6]:-->"+item[6]);
                        alert("item[7]:-->"+item[7]);
                        alert("item[8]:-->"+item[8]);
                        alert("item[9]:-->"+item[9]);
                        alert("item[10]:-->"+item[10]);*/
                        /** Create Table Row */
                        
                            var mycurrent_row=document.createElement("TR");         
                            mycurrent_row.id=seqNT;
                            
                             
                            var cell = document.createElement("TD");
                            var anc = document.createElement("input");
                            anc.type = "checkbox";
                            anc.id="check1"+seqNT;                         
                            anc.name="check1"+seqNT;
                        anc.setAttribute('onclick', 'cliChec('+seqNT+')');
                            anc.checked=false;
                            anc.value="Uncheckd";
                            //alert("anc.value:-->"+anc.value);
                            cell.appendChild(anc);
                            mycurrent_row.appendChild(cell);
                            alert(seqNT);
//                            alert("checkbox value**"+document.getElementById("check1").value);
                            
                            /** Pass Book Date */
                            var cell2 = document.createElement("TD");
                            var Entry_Date_NT=document.createElement("input");
                            Entry_Date_NT.type="text";
                            Entry_Date_NT.name="Entry_Date_NT"+seqNT;
                            Entry_Date_NT.id="Entry_Date_NT"+seqNT;
                            Entry_Date_NT.value=item[6];
                            Entry_Date_NT.maxLength="10";
                            Entry_Date_NT.setAttribute('onblur',"dateValidation_NT("+seqNT+");dateValidationNEWNT("+seqNT+")");
                            Entry_Date_NT.size="10";
                            cell2.appendChild(Entry_Date_NT);
                            
                               /** Sl_no Hidden */
                                var sl_no=document.createElement("input");
                                sl_no.type="hidden";
                                sl_no.name="sl_no"+seqNT;
                                sl_no.value=item[10];
                                sl_no.size="10";
                                cell2.appendChild(sl_no); 
                            
                            mycurrent_row.appendChild(cell2);
			
				
				/** Particulars */
			    var cell3=document.createElement("TD"); 
			    var Particualrs_NT=document.createElement("input");
			    Particualrs_NT.type="textarea";
			    Particualrs_NT.name="Particualrs_NT"+seqNT;
			    Particualrs_NT.value=item[5];
			    cell3.appendChild(Particualrs_NT);
			    mycurrent_row.appendChild(cell3);
			    
			     
			     /** Cheque No. */
			     var cell4=document.createElement("TD");
			     var ChequeNo_NT=document.createElement("input");
			     ChequeNo_NT.type="text";
			     ChequeNo_NT.name="ChequeNo_NT"+seqNT;
			     ChequeNo_NT.value=item[3];
			     ChequeNo_NT.size="10";
			     cell4.appendChild(ChequeNo_NT);
			     mycurrent_row.appendChild(cell4);
			     
			  
			     /** Details */              
			     var cell5=document.createElement("TD");
			     var Details_NT=document.createElement("input");
			     Details_NT.type="text";
			     Details_NT.name="Details_NT"+seqNT;
			     Details_NT.value=item[21];
			     cell5.appendChild(Details_NT);
			     mycurrent_row.appendChild(cell5);
			     
			   
			     /** CR Amount */
			     var cell6=document.createElement("TD");  
			     var cr_amount_NT=document.createElement("input");
			     cr_amount_NT.type="text";
			     cr_amount_NT.name="cr_amount_NT"+seqNT;
			     cr_amount_NT.id="cr_amount_NT"+seqNT;
			     cr_amount_NT.value=item[1];
			     cr_amount_NT.size="10";
			     cr_amount_NT.setAttribute("onblur","javascript:callHid('cr_amount_NT"+seqNT+"','dr_amount_NT"+seqNT+"','CR');");
			     cell6.appendChild(cr_amount_NT);
			     mycurrent_row.appendChild(cell6);
			     
			     
			     /** DR Amount */
			     var cell7=document.createElement("TD");  
			     var dr_amount_NT=document.createElement("input");
			     dr_amount_NT.type="text";
			     dr_amount_NT.name="dr_amount_NT"+seqNT;
			     dr_amount_NT.id="dr_amount_NT"+seqNT;
			     dr_amount_NT.value=item[4];
			     dr_amount_NT.size="10";
			     dr_amount_NT.setAttribute("onblur","javascript:callHid('dr_amount_NT"+seqNT+"','cr_amount_NT"+seqNT+"','DR');");
			     cell7.appendChild(dr_amount_NT);
			     mycurrent_row.appendChild(cell7);       
			    
			     
			     
			     /**
			      *  Dynamic Combo Creation and Loading 
			      */ 
			     
			     /* Reason for Difference */
			     cell12=document.createElement("TD");
			     cell12.style.textAlign='center';  
			     var Trans_Type_NT;
			     
			     if (window.navigator.appName.toLowerCase().indexOf("netscape") == -1)
			     { try
			       {
			    	 Trans_Type_NT =  document.createElement("<select name='Trans_Type_NT"+seqNT+"' id='Trans_Type_NT' >");
			       }catch( e ) { alert(e.description) }
			     }
			     else
			     {
			    	 Trans_Type_NT=document.createElement("select");                        
			    	 Trans_Type_NT.id="Trans_Type_NT";
			    	 Trans_Type_NT.name="Trans_Type_NT"+seqNT;
			     }
			     
			     var cmbCategoryCodeObj = baseResponse.getElementsByTagName("reason_pair");
			     var option11=document.createElement("option");    
			         option11.value="";  
			         option11.text="--Select--";
			         
			    try
			      {
			    	Trans_Type_NT.add(option11);
			      }
			    catch(e)
			      {
				    Trans_Type_NT.add(option11,null);
			      }     
			   
			     for(var y=0;y<cmbCategoryCodeObj.length;y++)
			     {
			     
			         RsnCode[y]=cmbCategoryCodeObj[y].getElementsByTagName("reason_code")[0].firstChild.nodeValue;
			         RsnDesc[y]=cmbCategoryCodeObj[y].getElementsByTagName("reason_desc")[0].firstChild.nodeValue;       
			         
			         /*Load Balance as per Pass Book in General Part */
			     //    document.getElementById("txtPBBalance").value=cmbCategoryCodeObj[y].getElementsByTagName("txtPBBalance")[0].firstChild.nodeValue;
			         
			         var option11=document.createElement("option");    
			         if (RsnCode[y] == item[9])
	                  {	  
	                	 option11.selected=true;
	                  }	 
			         
			         option11.value=RsnCode[y];  
			         option11.text=RsnDesc[y];
			         
			       
			        try
			          {
			        	 Trans_Type_NT.add(option11);
			          }
			        catch(e)
			          {
			        	 Trans_Type_NT.add(option11,null);
			          }
			          
			    }
			    cell12.appendChild(Trans_Type_NT);                                                    
			    mycurrent_row.appendChild(cell12);   
  
			     
			    
			    
			    /* Follow up Action Required */             
			    var cell13=document.createElement("TD");
			    cell13.style.textAlign='center';
			    var sel="";            
			    if (window.navigator.appName.toLowerCase().indexOf("netscape") == -1)
			    {
			      if( item[7] =='Y')
			      { 	  
			  	    sel=document.createElement("<INPUT type='checkbox' checked name='FollowUpAction_NT"+seqNT+"' id='FollowUpAction_NT' value='' />" );
			      }
			      else if(item[7] ==null){
			    	  sel=document.createElement("<INPUT type='checkbox' name='FollowUpAction_NT"+seqNT+"' id='FollowUpAction_NT' value='' />" );
			      }
			      else
			      {
			    	sel=document.createElement("<INPUT type='checkbox' name='FollowUpAction_NT"+seqNT+"' id='FollowUpAction_NT' value='' />" );  
			      }
			    }
			    else
			    {    
			      sel=document.createElement("input");     
			      sel.type="checkbox";             
			      sel.name="FollowUpAction_NT"+seqNT;
			      sel.id="FollowUpAction_NT";
			      if( item[7] =='Y')
			      {
			        sel.checked=true;
			      } else if(item[7] ==null){
			    	  sel.checked=false; 
			      }
			      else
			      {
			    	sel.checked=false;  
			      }	  
			      sel.value="Y";                          
			    }
			    cell13.appendChild(sel);
			    mycurrent_row.appendChild(cell13);
			 
			    
			    
			    /* Is it a clearance entry based on follow-up */             
			    var cell14=document.createElement("TD");
			    cell14.style.textAlign='center';
			    var sel="";            
			    if (window.navigator.appName.toLowerCase().indexOf("netscape") == -1)
			    {
			    	 if( item[8] =='Y')
				      { 	  	 
			  	        sel=document.createElement("<INPUT type='checkbox' checked name='ClearanceEntry_NT"+seqNT+"' id='ClearanceEntry_NT' value=''/>" );
				      }
			    	 else if(item[8] ==null){
				    	  sel.checked=false; 
				      }
			    	 else
			    	 {
			    	    sel=document.createElement("<INPUT type='checkbox' name='ClearanceEntry_NT"+seqNT+"' id='ClearanceEntry_NT' value=''/>" );
			    	 }
			    }
			    else
			    {    
			      sel=document.createElement("input");     
			      sel.type="checkbox";             
			      sel.name="ClearanceEntry_NT"+seqNT;
			      sel.id="ClearanceEntry_NT";			      
			      if( item[8] =='Y')
			      {
			        sel.checked=true;
			      } 
			      else if(item[8] ==null){
			    	  sel.checked=false; 
			      }
			      else
			      {
			    	sel.checked=false;  
			      }	  
			      sel.value="Y";                          
			    }
			    cell14.appendChild(sel);
			    mycurrent_row.appendChild(cell14);
			    
			    /* journalized */             
			    var cell15=document.createElement("TD");
			    cell15.style.textAlign='center';
			    var sel="";            
			    if (window.navigator.appName.toLowerCase().indexOf("netscape") == -1)
			    {
			    	 if( item[22] =='Y')
				      { 	  	 
			  	        sel=document.createElement("<INPUT type='checkbox' checked name='Journalized"+seqNT+"' id='Journalized' value=''/>" );
				      }
			    	 else if(item[22] ==null){
				    	  sel.checked=false; 
				      }
			    	 else
			    	 {
			    	    sel=document.createElement("<INPUT type='checkbox' name='Journalized"+seqNT+"' id='Journalized' value=''/>" );
			    	 }
			    }
			    else
			    {    
			      sel=document.createElement("input");     
			      sel.type="checkbox";             
			      sel.name="Journalized"+seqNT;
			      sel.id="Journalized";			      
			      if( item[22] =='Y')
			      {
			        sel.checked=true;
			      } 
			      else if(item[22] ==null){
			    	  sel.checked=false; 
			      }
			      else
			      {
			    	sel.checked=false;  
			      }	  
			      sel.value="Y";                          
			    }
			    cell15.appendChild(sel);
			    mycurrent_row.appendChild(cell15);
			    tbody.appendChild(mycurrent_row);    
			    
			    /** Increment Sequence Number */ 
				seqNT=seqNT+1;
				//alert("END");
	        }
	//alert("after loop");
}

function callme(sam)
{
	
	//dhana
	var fg = 0;
	var fg1 = 0;
	var ii = 0;
	var amt = 0;
	var f = parseInt(document.getElementById("Amt_Diff"+sam).value);
	if(f <0)
	  {		 
		  document.getElementById("cmbReason4Diff"+sam).disabled = false;
	  }else if(f >0)
	  {		 
		  document.getElementById("cmbReason4Diff"+sam).disabled = false;
	  }else
	  {		 
		  document.getElementById("cmbReason4Diff"+sam).disabled = true;
	  }
	if (document.getElementById("check" + sam).checked == true) {
		var tbody = document.getElementById("grid_body_TWAD");
		var rowcount = tbody.rows.length;
		for ( var i = 0; i < rowcount; i++) {
			var r = tbody.rows[i];
			
			var doctype_one=document.getElementById("doc_type" + sam).value;
			
			if(doctype_one!='CR')
			{
			
						if (document.getElementById("ccdd_no" + sam).value != "") {
							if ((document.getElementById("ccdd_no" + i).value) == (document
									.getElementById("ccdd_no" + sam).value)) {
			                                              
								fg1 = fg1 + 1;
								if (fg1 == 1) {
									ii = i;
								}
								r.bgColor = "#FFCCCC";
								document.getElementById("check" + i).checked = true;
								document.getElementById("EntryFoundInPassBook" + i).checked = true;
								document.getElementById("EntryFoundInPassBook" + i).disabled=true;
							}
							
							if (fg1 > 1) {
								fg = 1;
								amt = amt+ parseInt(document.getElementById("Amt_in_PassBk" + i).value);
							}
						} 
						else {
							document.getElementById("check" + sam).checked = true;
							document.getElementById("EntryFoundInPassBook" + i).checked = true;
							document.getElementById("EntryFoundInPassBook" + i).disabled=true;
						}
			}
			else
			{
				if (document.getElementById("r_date" + sam).value != "") 
						{
							if ((document.getElementById("r_date" + i).value) == (document
									.getElementById("r_date" + sam).value)) {
								fg1 = fg1 + 1;
								if (fg1 == 1) {
									ii = i;
								}
								r.bgColor = "#FFCCCC";
								document.getElementById("check" + i).checked = true;
								document.getElementById("EntryFoundInPassBook" + i).checked = true;
								document.getElementById("EntryFoundInPassBook" + i).disabled=true;
							}
							if (fg1 > 1) {
								fg = 1;
								amt = amt+ parseInt(document.getElementById("Amt_in_PassBk" + i).value);
							}
						}
				else {
					document.getElementById("check" + sam).checked = true;
					document.getElementById("EntryFoundInPassBook" + i).checked = true;
					document.getElementById("EntryFoundInPassBook" + i).disabled=true;
				}
			}
		}
		if (fg == 1) {
			amt = amt+ parseInt(document.getElementById("Amt_in_PassBk" + ii).value);
			alert("The Total Amount of the Cheque No"
					+ document.getElementById("ccdd_no" + sam).value + " is "
					+ amt);
		}
	}
	 else {
		
			var tbody = document.getElementById("grid_body_TWAD");
			var rowcount = tbody.rows.length;
			for ( var i = 0; i < rowcount; i++) {
				var r = tbody.rows[i];
				var doctype_one=document.getElementById("doc_type" + sam).value;
				
				if(doctype_one!='CR')
					{
				
				
							if (document.getElementById("ccdd_no" + sam).value != "") {
								if ((document.getElementById("ccdd_no" + i).value) == (document
										.getElementById("ccdd_no" + sam).value)) {
									
									fg1 = fg1 + 1;
									if (fg1 == 1) {
										ii = i;
									}
									r.bgColor = "#FFCCCC";
									document.getElementById("check" + i).checked = false;
									document.getElementById("EntryFoundInPassBook" + i).checked = true;
									document.getElementById("EntryFoundInPassBook" + i).disabled=true;
									document.getElementById("Amt_in_PassBk" + i).value=0;
									document.getElementById("Entry_Date" + i).value="";
									/* if (fg1 > 1) {
										fg = 1;
										amt = amt+ parseInt(document.getElementById("Amt_in_PassBk" + i).value);
									}  */	
								}
							
								
							} else {
								
								document.getElementById("check" + sam).checked = false;
								document.getElementById("EntryFoundInPassBook" + i).checked = false;
								document.getElementById("EntryFoundInPassBook" + i).disabled=true;
								document.getElementById("Amt_in_PassBk" + i).value=0;
								document.getElementById("Entry_Date" + i).value="";
							}
					}
				else
				{
					if (document.getElementById("r_date" + sam).value != "") 
					{
						if ((document.getElementById("r_date" + i).value) == (document
								.getElementById("r_date" + sam).value)) {
							
							document.getElementById("check" + i).checked = false;
							document.getElementById("EntryFoundInPassBook" + i).checked = true;
							document.getElementById("EntryFoundInPassBook" + i).disabled=true;
						}
					}
				}
			}
			if (fg == 1) {
				amt = amt+ parseInt(document.getElementById("Amt_in_PassBk" + ii).value);
				alert("The Total Amount of the Cheque No"
						+ document.getElementById("ccdd_no" + sam).value + " is "
						+ amt);
			}
		}
	
}

function callDifference(indexID)
{
  var RWamt   = (parseFloat(document.getElementById("dr_amount"+indexID).value) +  parseFloat(document.getElementById("cr_amount"+indexID).value)) ; 
  var Passamt = document.getElementById("Amt_in_PassBk"+indexID).value ; 
  document.getElementById("Amt_Diff"+indexID).value =  RWamt - parseFloat(Passamt) ;   
}

function dateValidation1() {
	var g = 0;
	var  txtCB_Year       = document.getElementById("txtCB_Year").value;   
	var  txtCB_Month      = document.getElementById("txtCB_Month").value;
	var txtCB_Month1 = ("0"+txtCB_Month);
	
	var tbody = document.getElementById("grid_body_TWAD");
	var rowcount = tbody.rows.length;	
	var tbody1 = document.getElementById("grid_body_NONTWAD");
	var rowcount1 = tbody1.rows.length;
	for ( var i = 0; i < rowcount; i++) {
	if(document.getElementById("EntryFoundInPassBook"+g).checked == true)
	{
	    var Entry_Date = document.getElementById("Entry_Date"+g).value; 
	    var r_date = document.getElementById("r_date"+g).value;
	    var browser = navigator.appName;

		if (browser == "Netscape") {
			var dd1 = Entry_Date.split('/');
			Entry_Date = dd1[1] + "/" + dd1[0] + "/" + dd1[2];
			var dd2 = r_date.split('/');
			r_date = dd2[1] + "/" + dd2[0] + "/" + dd2[2];
		}
		var a = Entry_Date.split('/');
		var b = r_date.split('/');
		
		var Entry_Date1 = new Date(a[2], a[0] - 1, a[1]);
		var r_date1 = new Date(b[2], b[0] - 1, b[1]);  

		/*if(document.getElementById("Entry_Date"+g).value == ""){
			alert("Enter PassBook Date in the Field");
			document.getElementById("Entry_Date"+g).focus();
			return false;
		}else*/
			
			if(Entry_Date1 < r_date1){
			alert("PassBook Date Should Be Greater Than Remitance Date");
			document.getElementById("Entry_Date"+g).value="";
			document.getElementById("Entry_Date"+g).focus();
			return false;
		}/*else if(dd1[2] != txtCB_Year){
			alert("Entry Date Year Should Be Equal To Cashbook Year");
			document.getElementById("Entry_Date"+g).focus();
			return false;
		}else if(dd1[1] != txtCB_Month1){
			alert("Entry Date Month Should Be Equal To Cashbook Month");
			document.getElementById("Entry_Date"+g).focus();
			return false;
		}*/					
	}g = g+1;
	}
	g = 0;

	for ( var i = 0; i < rowcount1; i++) {
		var Entry_Date_NT = document.getElementById("Entry_Date_NT"+g).value; 
	     var browser1 = navigator.appName;

			if (browser1 == "Netscape") {
				var dd1 = Entry_Date_NT.split('/');
				Entry_Date_NT = dd1[1] + "/" + dd1[0] + "/" + dd1[2];			
			}
			var a = Entry_Date_NT.split('/');		
			if(document.getElementById("Entry_Date_NT"+g).value == ""){
				alert("Enter Pass book Date in the Field");
				document.getElementById("Entry_Date_NT"+g).focus();
				return false;
			}/*else if(dd1[2] != txtCB_Year){
				alert("Pass book Date Year Should Be Equal To Cashbook Year");
				document.getElementById("Entry_Date_NT"+g).focus();
				return false;
			}else if(dd1[1] != txtCB_Month1){
				alert("Pass book Date Month Should Be Equal To Cashbook Month");
				document.getElementById("Entry_Date_NT"+g).focus();
				return false;
			}*/
		g = g+1;					
	}
	g = 0;
	return true;
}

function dateValidation(seq)
{
	
	
	//if (document.getElementById("check" + seq).checked == true) {
	var Entry_Datee1 = document.getElementById("Entry_Date" + seq).value;
	var tbody = document.getElementById("grid_body_TWAD");
	var rowcount = tbody.rows.length;
	for ( var i = 0; i < rowcount; i++) {
		var r = tbody.rows[i];
		
		var doctype_one=document.getElementById("doc_type" + seq).value;
		
		if(doctype_one!='CR')
		{
					if (document.getElementById("ccdd_no" + seq).value != "") {
						if ((document.getElementById("ccdd_no" + i).value) == (document
								.getElementById("ccdd_no" + seq).value)) {
							r.bgColor = "#FFCCCC";
							document.getElementById("Entry_Date" + i).value = Entry_Datee1;
						}
					}
	     }
		else
		{
			
			if (document.getElementById("r_date" + seq).value != "") 
			{
				if ((document.getElementById("r_date" + i).value) == (document
						.getElementById("r_date" + seq).value)) {
					document.getElementById("Entry_Date" + i).value = Entry_Datee1;
				}
			}
		}
	}
	
	
      var Entry_Date = document.getElementById("Entry_Date"+seq).value; 
      var r_date = document.getElementById("r_date"+seq).value;
      var  txtCB_Year       = document.getElementById("txtCB_Year").value;
      var  txtCB_Month      = document.getElementById("txtCB_Month").value;
      var txtCB_Month1 = ("0"+txtCB_Month);
      var browser = navigator.appName;

		if (browser == "Netscape") {
			var dd1 = Entry_Date.split('/');
			Entry_Date = dd1[1] + "/" + dd1[0] + "/" + dd1[2];
			var dd2 = r_date.split('/');
			r_date = dd2[1] + "/" + dd2[0] + "/" + dd2[2];
		}
		var a = Entry_Date.split('/');
		var b = r_date.split('/');
		
		var Entry_Date1 = new Date(a[2], a[0] - 1, a[1]);
		var r_date1 = new Date(b[2], b[0] - 1, b[1]);   
		if((document.getElementById("EntryFoundInPassBook"+seq).checked == true) && (document.getElementById("Entry_Date"+seq).value == "")){
			alert("Enter PassBook Date in the Field");
			document.getElementById("Entry_Date"+seq).focus();
			return false;
		}else if((document.getElementById("EntryFoundInPassBook"+seq).checked == true) && (Entry_Date1 < r_date1)){
			alert("PassBook Date Should Be Greater Than Remitance Date");
			document.getElementById("Entry_Date"+seq).value="";
			document.getElementById("Entry_Date"+seq).focus();
			return false;
		}/*else if((document.getElementById("EntryFoundInPassBook"+seq).checked == true) && (dd1[2] != txtCB_Year)){
			alert("Entry Date Year Should Be Equal To Cashbook Year");
			document.getElementById("Entry_Date"+seq).focus();
			return false;
		}else if((document.getElementById("EntryFoundInPassBook"+seq).checked == true) && (dd1[1] != txtCB_Month1)){
			alert("Entry Date Month Should Be Equal To Cashbook Month");
			document.getElementById("Entry_Date"+seq).focus();
			return false;
		}*/else{
			return true;
		}
		
		
}

function dateValidation_NT(seqNT)
{
      var Entry_Date_NT = document.getElementById("Entry_Date_NT"+seqNT).value; 

      var  txtCB_Year       = document.getElementById("txtCB_Year").value;   
      var  txtCB_Month      = document.getElementById("txtCB_Month").value;
      var txtCB_Month1 = ("0"+txtCB_Month);

      var browser = navigator.appName;

		if (browser == "Netscape") {
			var dd1 = Entry_Date_NT.split('/');
			Entry_Date_NT = dd1[1] + "/" + dd1[0] + "/" + dd1[2];			
		}
		var a = Entry_Date_NT.split('/');		
		
		if(document.getElementById("Entry_Date_NT"+seqNT).value == ""){
			alert("Enter Pass book Date in the Field");
			document.getElementById("Entry_Date_NT"+seqNT).focus();
			return false;
		}/*else if(dd1[2] != txtCB_Year){
			alert("Pass book Date Year Should Be Equal To Cashbook Year");
			document.getElementById("Entry_Date_NT"+seqNT).focus();
			return false;
		}else if(dd1[1] != txtCB_Month1){
			alert("Pass book Date Month Should Be Equal To Cashbook Month");
			document.getElementById("Entry_Date_NT"+seqNT).focus();
			return false;
		}*/else{
			return true;
		}
}

function checkNull()
{
	//alert(seq);
	//alert(seqNT);
  document.getElementById('RecordCount').value=seq;
  document.getElementById('RecordCountNT').value=seqNT;
 // document.getElementById('check1').value=check1;
  return true;
  
}

function notEdit(seq)
{
	
	
	if(document.getElementById("check"+seq).checked==true){
	document.getElementById("EntryFoundInPassBook"+seq).checked=true;
	document.getElementById("Journalized"+seq).setAttribute('readOnly','readOnly');
	}
	else
	{
		document.getElementById("EntryFoundInPassBook"+seq).checked=false;
		document.getElementById("Journalized"+seq).setAttribute('readOnly','readOnly');	
	}
	
}

function exit()
{
	self.close();
}
function clrForm(){
	document.getElementById("grid_body_TWAD").length=0;
	document.getElementById("grid_body_NONTWAD").length=0;
	document.getElementById("cmbBankAccNo").value=0;
	//document.getElementById("txtPBBalance").value="";
	
}


function callHid(id, id1, type) {
	alert("id value : "+document.getElementById(id).value);
	alert("id1 value : "+document.getElementById(id1).value);
	if (document.getElementById(id).value > 0
			|| document.getElementById(id1).value > 0) {
		
		var s = confirm("Want to Enter " + type + " Amount ");
		if (s == true) {
			alert('true****');
			document.getElementById(id1).disabled = true;
			document.getElementById(id1).value = "0";
			if (document.getElementById(id).disabled == true) {
				document.getElementById(id).disabled = false;
			}
		} else {
			if (document.getElementById(id1).disabled == true) {
				document.getElementById(id1).disabled = false;
			}
		}
	}else if (document.getElementById(id).value > 0
			&& document.getElementById(id1).value > 0) {
		
		var s = confirm("Want to Enter " + type + " Amount ");
		if (s == true) {
			alert('true****');
			document.getElementById(id1).disabled = true;
			document.getElementById(id1).value = "0";
			if (document.getElementById(id).disabled == true) {
				document.getElementById(id).disabled = false;
			}
		} else {
			document.getElementById(id1).value = "0";
			document.getElementById(id).value = "0";
			
		}
	}
	
	else{
		
		document.getElementById(id).disabled = false;
		document.getElementById(id1).disabled = false;
	}

}

function cliChec(id){
//	alert("function cliChec");
	if(document.getElementById("check1"+id).checked==true)
	document.getElementById("check1"+id).value="Checked";
	else if(document.getElementById("check1"+id).checked==false)
		document.getElementById("check1"+id).value="Uncheckd";
//	alert(document.getElementById("check1"+id).value);
}



function dateValidationNEW(seq) {
	
	//var txtCB_Year=document.getElementById("txtCB_Year").value;
	///var month=document.getElementById("txtCB_Month").value;
	var  txtCB_Month       = document.getElementById("txtCB_Month").value;   
	var  txtCB_Year      = document.getElementById("txtCB_Year").value;
	var PassBk_dte=document.getElementById("Entry_Date" + seq).value;
	//var r_cheque_dd_no1=document.getElementById("r_cheque_dd_no" + seq).value;
	//var w_challan_no1=document.getElementById("w_challan_no" + seq).value;
	
		
	//alert(txtCB_Month.length);
	if(txtCB_Month.length==1){
	 txtCB_Month = "0"+txtCB_Month;
	}
	var dateFrom = "01/"+txtCB_Month+"/"+txtCB_Year;
	var dateTo = "31/"+txtCB_Month+"/"+txtCB_Year;
	
	//alert(PassBk_dte);
	var dateCheck = PassBk_dte;
//	alert(dateFrom+"   00' "+dateTo+"   "+dateCheck);

	var d1 = dateFrom.split("/");
	var d2 = dateTo.split("/");
	var c = dateCheck.split("/");

	var from = new Date(d1[2], d1[1]-1, d1[0]);  // -1 because months are from 0 to 11
	var to   = new Date(d2[2], d2[1]-1, d2[0]);
	var check = new Date(c[2], c[1]-1, c[0]);
	/*from = Date.parse(dateFrom);
	to = Date.parse(dateTo);
	check = Date.parse(c);*/

	//alert(">>>>> :::  "+from+"  >> ' "+to+"   "+check);
	
	/*if(month.length<2){
		month="0"+month;}*/
//console.log('********************************************************'+check > from && check < to)
	if(c[2]!=txtCB_Year){
		alert(" PassBook date should be with in the Reconcilation Month  ..... ");	
		document.getElementById("Entry_Date" + seq).value="";
	}
	else if(c[1]!=txtCB_Month)
	{
		alert(" PassBook date should be with in the Reconcilation Month  ..... ");
		/*var tbody = document.getElementById("grid_body_TWAD");
		var rowcount = tbody.rows.length;
		for ( var i = 0; i < rowcount; i++) {
			var r = tbody.rows[i];
			
			
					if (document.getElementById("EntryFoundInPassBook" + i).checked == true && 
							document.getElementById("r_cheque_dd_no" + i).value==	r_cheque_dd_no1	 &&
							document.getElementById("w_challan_no" + i).value==	w_challan_no1)	{
								r.bgColor = "#FFCCCC";
								document.getElementById("Entry_Date" + i).value = "";
							}
						//}
			  }*/
		document.getElementById("Entry_Date" + seq).value="";
	}
	
	
	/*if(to>check){
		alert('test .... ');
		alert(" PassBook date should be with in the Reconcilation Month  ..... ");
		return false;	
	}*/
	//alert('test final .... ');
	return true;	
	
}

function dateValidationNEWNT(seq) {
	
	//var txtCB_Year=document.getElementById("txtCB_Year").value;
	///var month=document.getElementById("txtCB_Month").value;
	var  txtCB_Month       = document.getElementById("txtCB_Month").value;   
	var  txtCB_Year      = document.getElementById("txtCB_Year").value;
	var PassBk_dte=document.getElementById("Entry_Date_NT" + seq).value;
	//var r_cheque_dd_no1=document.getElementById("r_cheque_dd_no" + seq).value;
	//var w_challan_no1=document.getElementById("w_challan_no" + seq).value;
	
		
	//alert(txtCB_Month.length);
	if(txtCB_Month.length==1){
	 txtCB_Month = "0"+txtCB_Month;
	}
	var dateFrom = "01/"+txtCB_Month+"/"+txtCB_Year;
	var dateTo = "31/"+txtCB_Month+"/"+txtCB_Year;
	
	//alert(PassBk_dte);
	var dateCheck = PassBk_dte;
//	alert(dateFrom+"   00' "+dateTo+"   "+dateCheck);

	var d1 = dateFrom.split("/");
	var d2 = dateTo.split("/");
	var c = dateCheck.split("/");

	var from = new Date(d1[2], d1[1]-1, d1[0]);  // -1 because months are from 0 to 11
	var to   = new Date(d2[2], d2[1]-1, d2[0]);
	var check = new Date(c[2], c[1]-1, c[0]);
	/*from = Date.parse(dateFrom);
	to = Date.parse(dateTo);
	check = Date.parse(c);*/

	//alert(">>>>> :::  "+from+"  >> ' "+to+"   "+check);
	
	/*if(month.length<2){
		month="0"+month;}*/
//console.log('********************************************************'+check > from && check < to)
	if(c[2]!=txtCB_Year){
		alert(" PassBook date should be with in the Reconcilation Month  ..... ");	
		document.getElementById("Entry_Date_NT" + seq).value="";
	}
	else if(c[1]!=txtCB_Month)
	{
		alert(" PassBook date should be with in the Reconcilation Month  ..... ");
		/*var tbody = document.getElementById("grid_body_TWAD");
		var rowcount = tbody.rows.length;
		for ( var i = 0; i < rowcount; i++) {
			var r = tbody.rows[i];
			
			
					if (document.getElementById("EntryFoundInPassBook" + i).checked == true && 
							document.getElementById("r_cheque_dd_no" + i).value==	r_cheque_dd_no1	 &&
							document.getElementById("w_challan_no" + i).value==	w_challan_no1)	{
								r.bgColor = "#FFCCCC";
								document.getElementById("Entry_Date" + i).value = "";
							}
						//}
			  }*/
		document.getElementById("Entry_Date_NT" + seq).value="";
	}
	
	
	/*if(to>check){
		alert('test .... ');
		alert(" PassBook date should be with in the Reconcilation Month  ..... ");
		return false;	
	}*/
	//alert('test final .... ');
	return true;	
	
}


