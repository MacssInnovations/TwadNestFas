function checkNull_DCB()
{
var tbody=document.getElementById("grid_body");
var flag=new Boolean;
flag=true;  
 
 var sl_count=0;
 var rows=0;
					
					   //alert("tbody.rows.length :"+tbody.rows.length);   
					if(document.getElementById("cmbAcc_UnitCode").value=="")
					{
					    alert("Select the Account Unit code");
					    //document.getElementById("txtAcc_HeadDesc").focus();
					    flag=false;
					    
					}
					if(document.getElementById("cmbOffice_code").value=="")
					{
					    alert("Select the Office Code");
					    //document.getElementById("cmbOffice_code").focus();
					    flag=false;    
					    
					}
					if(document.getElementById("txtCrea_date").value.length==0)
					{
					    alert("Enter the Date of Creation");
					    //document.getElementById("txtCrea_date").focus();
					    flag=false;    
					   
					}
					
					if(document.getElementById("txtCash_Acc_code").value.length==0 || document.getElementById("txtCash_Acc_code").value==0)
					{
					    alert("Enter the Collection A/c Code");
					    //document.getElementById("txtCash_Acc_code").focus();
					    flag=false;
					     
					}
					
					if(document.getElementById("txtBankAccountNo").value.length==0 || document.getElementById("txtBankAccountNo").value==0)
					{
					    alert("Enter the Account Number");
					    //document.getElementById("txtRecei_from").focus();
					    flag=false;
					    
					}
					
					if(document.getElementById("txtBankId").value.length==0 || document.getElementById("txtBankId").value==0)
					{
					    alert("Bank Id not populated in General");
					    //document.getElementById("txtAmount").focus();
					    flag=false;
					     
					}
					
					if(document.getElementById("txtBranchId").value.length==0 || document.getElementById("txtBranchId").value==0) 
					{
					    alert("Branch Id not populated in General");
					    //document.getElementById("txtAmount").focus();
					    flag=false;  
					    alert("TEST 6")
					}
					
					if(document.getElementById("txtRecei_from").value.length==0)
					{
					    alert("Enter the value in  ' Received From Field ' in General part");
					    //document.getElementById("txtRecei_from").focus();
					    flag=false;
					    
					}
					
					if(document.getElementById("cmbMas_SL_type").value!="")
					{
					    if(document.getElementById("cmbMas_SL_Code").value=="")
					    {
					    alert("Select The Sub Ledger Code in General");
					    flag=false;
					    
					    }
					}
					if(document.getElementById("txtAmount").value.length==0)
					{
					    alert("Enter the Total Amount in General");
					    //document.getElementById("txtAmount").focus();
					    flag=false;    
					     
					}
					
					if(tbody.rows.length==0)
					{
					    alert("Enter the Amount Details under Details");
					    //document.getElementById("txtAmount").focus();
					    flag=false;    
					}
					
					for(i=0;i<rows.length;i++)
					{
						
						rows=tbody.getElementsByTagName("tr");
						 var cells=rows[i].cells;
						alert(cells.item(2).lastChild.nodeValue)
						 
					}  
					
					
						if(tbody.rows.length>0)
						{
						        var check_amt=0;
						        rows=tbody.getElementsByTagName("tr");
						        for(i=0;i<rows.length;i++)
						        {
						            var cells=rows[i].cells;
						             
						            if (cells.item(4).lastChild.nodeValue=="")
						            {
						            	sl_count=0;
						            }
						            else
						            {
						            	sl_count++;
						            }
						            if(cells.item(2).lastChild.nodeValue=='DR')
						                 check_amt=parseFloat(check_amt) - parseFloat(cells.item(7).lastChild.nodeValue);
						            else
						                 check_amt=parseFloat(check_amt) + parseFloat(cells.item(7).lastChild.nodeValue);
						        }
						        
						        rnum = Math.round( parseFloat(check_amt) * Math.pow(10,2) )  /   Math.pow(10,2) ;
						       
						        
						        
						        if( parseFloat(document.getElementById("txtAmount").value) != parseFloat(rnum)  )
						        {
						           alert("Amount doesn't Tally.. Difference " +(parseFloat(document.getElementById("txtAmount").value)-parseFloat(check_amt)))
						           flag=false;    
						        }
						}
						  
						if (parseInt(sl_count)<=0)
						{
							alert("Warning:  Select Sub Ledger Type and Sub Ledger Code.....Cannot be blank.");
						}
						if (parseInt(sl_count)>0 && flag==true)
							return true;
						else
							return false;
	
 
   

}

function checkNull_CASHDCB()
{
	
	var tbody=document.getElementById("grid_body");
	var flag=new Boolean;
	flag=true;
	 
	 var sl_count=0;
	 var rows=0;
						
						   //alert("tbody.rows.length :"+tbody.rows.length);   
						if(document.getElementById("cmbAcc_UnitCode").value=="")
						{
						    alert("Select the Account Unit code");
						    //document.getElementById("txtAcc_HeadDesc").focus();
						    flag=false;
						    
						}
						if(document.getElementById("cmbOffice_code").value=="")
						{
						    alert("Select the Office Code");
						    //document.getElementById("cmbOffice_code").focus();
						    flag=false;    
						    
						}
						if(document.getElementById("txtCrea_date").value.length==0)
						{
						    alert("Enter the Date of Creation");
						    //document.getElementById("txtCrea_date").focus();
						    flag=false;    
						   
						}
						
						if(document.getElementById("txtCash_Acc_code").value.length==0 || document.getElementById("txtCash_Acc_code").value==0)
						{
						    alert("Enter the Collection A/c Code");
						    //document.getElementById("txtCash_Acc_code").focus();
						    flag=false;
						     
						}
						if(document.getElementById("txtRecei_from").value.length==0)
						{
						    alert("Enter the value in  ' Received From Field ' in General part");
						    //document.getElementById("txtRecei_from").focus();
						    flag=false;
						    
						}
						
						if(document.getElementById("cmbMas_SL_type").value!="")
						{
						    if(document.getElementById("cmbMas_SL_Code").value=="")
						    {
						    alert("Select The Sub Ledger Code in General");
						    flag=false;
						    
						    }
						}
						if(document.getElementById("txtAmount").value.length==0)
						{
						    alert("Enter the Total Amount in General");
						    //document.getElementById("txtAmount").focus();
						    flag=false;    
						     
						}
						
						if(tbody.rows.length==0)
						{
						    alert("Enter the Amount Details under Details");
						    //document.getElementById("txtAmount").focus();
						    flag=false;    
						}
						
						for(i=0;i<rows.length;i++)
						{
							
							rows=tbody.getElementsByTagName("tr");
							 var cells=rows[i].cells;
							alert(cells.item(2).lastChild.nodeValue)
							 
						}  
						
						 
							if(tbody.rows.length>0)
							{
							        var check_amt=0;
							        rows=tbody.getElementsByTagName("tr");
							        for(i=0;i<rows.length;i++)
							        {
							            var cells=rows[i].cells;
							             
							            if (cells.item(4).lastChild.nodeValue=="")
							            {
							            	sl_count=0;
							            }
							            else
							            {
							            	sl_count++;
							            }
							            if(cells.item(2).lastChild.nodeValue=='DR')
							                 check_amt=parseFloat(check_amt) - parseFloat(cells.item(5).lastChild.nodeValue);
							            else
							                 check_amt=parseFloat(check_amt) + parseFloat(cells.item(5).lastChild.nodeValue);
							        }
							        
							          rnum = Math.round( parseFloat(check_amt) * Math.pow(10,2) )  /   Math.pow(10,2) ;
							         
							        
							        
							        if( parseFloat(document.getElementById("txtAmount").value) != parseFloat(rnum)  )
							        {
							           alert("Amount doesn't Tally.. Difference " +(parseFloat(document.getElementById("txtAmount").value)-parseFloat(check_amt)))
							           flag=false;    
							        }
							}
							  
	
							 
	if (parseInt(sl_count)<=0)
	{
		alert("Warning:  Select Sub Ledger Type and Sub Ledger Code.....Cannot be blank.");
	}
	if (parseInt(sl_count)>0 && flag==true)
		return true;  
	else
		return false;
}
