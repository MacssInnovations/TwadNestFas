var seq=0;

/////////////////////////////////////////////   XML req  /////////////////////////////////////////////////////

/**
 *  Browser Type Detection 
 */

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






function doFunction(Command)
{   
        if(Command=="Load_TransDetails")
        {  
            var cmbAcc_UnitCode = document.getElementById("cmbAcc_UnitCode").value;
            var cmbOffice_code = document.getElementById("cmbOffice_code").value;
            var txtCB_Year = document.getElementById("txtCB_Year").value;
            var txtCB_Month = document.getElementById("txtCB_Month").value;
            var cmbBankAccNo = document.getElementById("cmbBankAccNo").value;
            var txtOprMode = document.getElementById("txtOprMode").value;
            var txtBankID = document.getElementById("txtBankID").value;
            
            var TransType='NT';
            
            var url="../../../../../BRS_Auto_Journal.kv?Command=Load_TransDetails&cmbAcc_UnitCode="+cmbAcc_UnitCode+"&cmbOffice_code="+cmbOffice_code+
                    "&txtCB_Year="+txtCB_Year+"&txtCB_Month="+txtCB_Month+"&TransType="+TransType+"&cmbBankAccNo="+cmbBankAccNo+"&txtOprMode="+txtOprMode+"&txtBankID="+txtBankID;
           
            var req=getTransport();
            req.open("GET",url,true); 
            req.onreadystatechange=function()
            {
               handleResponse(req);
            }   
            req.send(null);
        }       
}





function handleResponse(req)
{  
 
    if(req.readyState==4)
    {
        if(req.status==200)
        {  
            
             var baseResponse=req.responseXML.getElementsByTagName("response")[0];
             var tagcommand=baseResponse.getElementsByTagName("command")[0];
             var Command=tagcommand.firstChild.nodeValue;
           
            if(Command=="Load_TransDetails")
            {
            	Load_TransDetails(baseResponse);
            }
        }
    }
}




function Load_TransDetails(baseResponse)
{
      var flag=baseResponse.getElementsByTagName("flag")[0].firstChild.nodeValue;
      var TransType=baseResponse.getElementsByTagName("TransType")[0].firstChild.nodeValue;
      
      if(flag=="success")
      {
    	
    	 if ( TransType=="NT" )
    	 {
		    		 /* Load NON-TWAD Trasaction  Values */    	
		 	      	
			    	  /** Delete Existing Values from Grid */
			      	  var tbody=document.getElementById("grid_body_NONTWAD");
			          var t=0;
			          for(t=tbody.rows.length-1;t>=0;t--)
			          {
			             tbody.deleteRow(0);
			          }
			      	
			          var Sl_No=baseResponse.getElementsByTagName("Sl_No");
			          
			          var item=new Array();
			          
                                  /** Clear Total Number of Transaction */
                                  document.getElementById("txtTotTrans").value="";  
                                  
			          for(var k=0;k<Sl_No.length;k++)
				  {
			        	    item[0]=baseResponse.getElementsByTagName("Passbook_Date")[k].firstChild.nodeValue;
				          	if(item[0] == 'null' )
				          	{
				          		item[0]="";
				          	}
				          	
				          	item[1]=baseResponse.getElementsByTagName("Particulars")[k].firstChild.nodeValue;
				          	if(item[1] == 'null' )
				          	{
				          		item[1]="";
				          	}  
				          	
				          	item[2]=baseResponse.getElementsByTagName("Cheque_No")[k].firstChild.nodeValue;
				          	if(item[2] == 'null' )
				          	{
				          		item[2]="";
				          	}        	
				          	
				          	item[3]=baseResponse.getElementsByTagName("cr_amt")[k].firstChild.nodeValue;
				          	if(item[3] == 'null' )
				          	{
				          		item[3]="";
				          	}
				          	
				          	item[4]=baseResponse.getElementsByTagName("dr_amt")[k].firstChild.nodeValue;
				          	if(item[4] == 'null' )
				          	{
				          		item[4]="";
				          	}
				          	
				          	item[5]=baseResponse.getElementsByTagName("Sl_No")[k].firstChild.nodeValue;
				          	
				          	item[6]=0;
				          	//alert("tttt");
				          
				          	
				          	item[7]=baseResponse.getElementsByTagName("DR_Head_Code")[k].firstChild.nodeValue;
				          
				          	if(item[7] == 'null' )
				          	{
				          		item[7]="-";
				          	}
				          	
				          	/** Create Table Row */
				        	var mycurrent_row=document.createElement("TR");         
				        	mycurrent_row.id=seq;
				        	
				        	
				        	/** Create Check Box */
				        	   cell1=document.createElement("TD");
				               
				               var sel="";            
				               if (window.navigator.appName.toLowerCase().indexOf("netscape") == -1)
				               {
				                  sel=document.createElement("<INPUT type='checkbox' name='sel"+seq+"' id='sel' value="+item[5]+" >");
				               }
				               else
				               {    
				                 sel=document.createElement("input");     
				                 sel.type="checkbox";             
				                 sel.name="sel";
				                 sel.id="sel";
				                 sel.checked=false;                 
				                 sel.value=seq;
				               }
				               cell1.appendChild(sel);
				               cell1.align = "center";
				               mycurrent_row.appendChild(cell1);
				        	
				        	
				          	/** PassBook  Date Creation */
				        	var cell2 = document.createElement("TD");
				        	var pass_date=document.createElement("input");
				        	pass_date.type="hidden";
				        	pass_date.name="pass_date"+seq;
				        	pass_date.value=item[0];
				        	cell2.align = "left";
				        	cell2.appendChild(pass_date);
                                                
                            /** Serial Number */ 
                            var slNo=document.createElement("input");
				        	slNo.type="hidden";
				        	slNo.name="slNo";
				        	slNo.value=item[5];
				        	cell2.appendChild(slNo);
				        	cell2.align = "center";                    
				        	var currentText=document.createTextNode(item[0]);
				        	cell2.appendChild(currentText);
				        	mycurrent_row.appendChild(cell2);
				             
				              
                            /** Particulars */
				            var cell4=document.createElement("TD");
				            var Part=document.createElement("input");
				            Part.type="hidden";
				            Part.name="Part"+seq;
				            Part.value=item[1];
				            cell4.appendChild(Part);
				            cell4.align = "center";
				            var currentText=document.createTextNode(item[1]);
				            cell4.appendChild(currentText);
				            mycurrent_row.appendChild(cell4);
				        	
				        	
				        	
				              /** Cheque / DD Number */              
				              var cell5=document.createElement("TD");
				              var ccdd_no=document.createElement("input");
				              ccdd_no.type="hidden";
				              ccdd_no.name="ccdd_no"+seq;
				              ccdd_no.value=item[2];
				              cell5.appendChild(ccdd_no);
				              cell5.align = "center";
				              var currentText=document.createTextNode(item[2]);
				              cell5.appendChild(currentText);
				              mycurrent_row.appendChild(cell5);
				              
				            
				              /** CR Amount */
				              var cell6=document.createElement("TD");  
				              var cr_amount=document.createElement("input");
				              cr_amount.type="hidden";
				              cr_amount.name="cr_amount";
				              cr_amount.value=item[3];
				              cell6.appendChild(cr_amount);
				              cell6.align = "center";
				              var currentText=document.createTextNode(item[3]);
				              cell6.appendChild(currentText);
				              mycurrent_row.appendChild(cell6);
				              
				              /** DR Amount */
				              var cell7=document.createElement("TD");  
				              var dr_amount=document.createElement("input");
				              dr_amount.type="hidden";
				              dr_amount.name="dr_amount";
				              dr_amount.value=item[4];
				              cell7.appendChild(dr_amount);
				              cell7.align = "center";
				              var currentText=document.createTextNode(item[4]);
				              cell7.appendChild(currentText);
				              mycurrent_row.appendChild(cell7);       	
				              
				              /** CR Account Head */
				              var cell8=document.createElement("TD");  
				              var cr_Account_Head=document.createElement("input");
				              cr_Account_Head.type="hidden";
				              cr_Account_Head.name="cr_Account_Head";
				              cr_Account_Head.value=120201;
				              cell8.appendChild(cr_Account_Head);
				              cell8.align = "center";
				              var currentText=document.createTextNode("120201/INT. RECEIVED  -FD'S  (OTHER THAN GPF)");
				              cell8.appendChild(currentText);
				              mycurrent_row.appendChild(cell8);
				              
				              /** DR Account Head */
				              var cell9=document.createElement("TD");  
				              var dr_Account_Head=document.createElement("input");
				              dr_Account_Head.type="hidden";
				              dr_Account_Head.name="dr_Account_Head";
				              dr_Account_Head.value=item[7];
				              cell9.appendChild(dr_Account_Head);
				              cell9.align = "center";
				              var currentText=document.createTextNode(item[7]);
				              cell9.appendChild(currentText);
				              mycurrent_row.appendChild(cell9);
				              
				              tbody.appendChild(mycurrent_row);
				              
				              
				
				              /** Increment Sequence Number */ 
				              seq=seq+1;     
                                              
			        	  
				      }
    		 
    	 }		 
        
      }
      else
      {
        alert("Records not found");
      }
    
}







function checkNull()
{
   document.getElementById("txtTotTrans").value=seq;  
   alert(seq);
}


function exit()
{
	self.close();
}









///////////////////////////////////////////    TB_checking and Calender control return value handling



function call_mainJSP_script(fromcal_dateCtrl,blr_flag)    
{
    if(blr_flag==1)      
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


function call_date(dateCtrl)                        
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
    else
    {
      document.getElementById("txtCrea_date").value="";
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
                 
              }
             else if(flag=="failure")
               {
                    dateCtrl.value="";
                    alert("Trial Balance Closed");
                    dateCtrl.focus();
                    document.getElementById("txtCrea_date").value="";
               }
             else if(flag=="finyear")
               {
                    dateCtrl.value="";
                    alert("Cash Book Control Not Found ");
                    dateCtrl.focus();
                    document.getElementById("txtCrea_date").value="";     
               }
        }
    }
}

function clrForm() {
	var today = new Date();
	var day = today.getDate();
	var month = today.getMonth();
	month = month + 1;
	var year = today.getYear();
	if (year < 1900)
		year += 1900;

	LoadAccountingUnitID('LIST_ALL_UNITS');
	document.frmAuthorizationBRS.txtCB_Year.value = year;
	document.frmAuthorizationBRS.txtCB_Month.value = month;
	document.getElementById("cmbBankAccNo").value = "";
	if (day <= 9 && day >= 1)
		day = "0" + day;
	if (month <= 9 && month >= 1)
		month = "0" + month;
	var year = today.getYear();
	if (year < 1900)
		year += 1900;
	var monthArray = new Array("January", "February", "March", "April", "May",
			"June", "July", "August", "September", "October", "November",
			"December");
	document.frmAuthorizationBRS.txtCrea_date.value = day + "/" + month + "/"
			+ year;

	/** Delete Existing Values from Grid */
	var tbody = document.getElementById("grid_body_NONTWAD");
	var t = 0;
	for (t = tbody.rows.length - 1; t >= 0; t--) {
		tbody.deleteRow(0);
	}
}

