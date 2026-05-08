/* Variables Declaration */

var j=0;
var seq=0;

var items1;
var items2;
var items3;
var items4;
var items5;
var items6;
var items7;
var items9;
var items10;
var items11;
var items12;
var items13;
var items14;
var items15;
var items16;
var items17;

var ho_bank_ac_no = new Array();
var bankShow = new Array();



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






function doFunction(Command,param)
{  
    var cmbAcc_UnitCode=document.getElementById("cmbAcc_UnitCode").value;    
    var cmbOffice_code=document.getElementById("cmbOffice_code").value;
    var remit_type=document.getElementById("remit_type_sel").value;
   // alert("remit_type===>"+remit_type);
   
	if ( document.frmauto_Fund.displayingOrder[0].checked== true )
	{
	   displayingOrder ='RW';
	}
	else
	{
	   displayingOrder ='BW';
	}
	 
    
    var txtRegionId=document.getElementById("txtRegionId").value;
    var txtBankAccountNo=document.getElementById("txtBankAccountNo").value;
    
    
    if(Command=="searchByMonth")
    {  
    	var vdate=document.getElementById("txtCrea_date").value;
    	var mytool_array=vdate.split("/");
    	
    	
    	
        var txtCrea_date=document.getElementById("txtCrea_date").value;
        var txtCB_Year=document.getElementById("txtCB_Year").value;
        var txtCB_Month=document.getElementById("txtCB_Month").value;
       var fl=0; 
       
        if(parseInt(txtCB_Year)>parseInt(mytool_array[2]))
        {
        	alert('Receipt Date should greater than or equal to Cash Book Month and year ');
    		return false;
    		fl=1;
        }
        if(parseInt(txtCB_Year)==parseInt(mytool_array[2]))
        {
        //alert(mytool_array[1]+":::"+txtCB_Month);
        	if(mytool_array[1]<txtCB_Month)
        	{
        		alert('Receipt Date should greater than or equal to Cash Book Month and year****');
        		return false;
        		fl=1;
        	}
        }
        
        
        if(txtCB_Year.length!=0 && txtCB_Month.length!=0 && fl==0)
        {
            var url="../../../../../Auto_Fund_Receipt_Create_atHOServ?Command=searchByMonth&cmbAcc_UnitCode="+cmbAcc_UnitCode+
            "&cmbOffice_code="+cmbOffice_code+"&txtCB_Year="+txtCB_Year+"&txtCB_Month="+txtCB_Month+"&remit_type="+remit_type+            
            "&displayingOrder="+displayingOrder+"&txtRegionId="+txtRegionId+"&txtBankAccountNo="+txtBankAccountNo+"&txtCrea_date="+txtCrea_date;
            //alert(url);
            var req=getTransport();
           req.open("GET",url,true); 
            req.onreadystatechange=function()
            {
               handleResponse(req);
          }   
               req.send(null);
           
        }
    }       
    else if(Command=="searchByDate")
    {  
        var txtFrom_date=document.getElementById("txtFrom_date").value;
        var txtTo_date=document.getElementById("txtTo_date").value;
        var txtCB_Year=document.getElementById("txtCB_Year").value;
        var txtCB_Month=document.getElementById("txtCB_Month").value;

        if(txtCB_Year.length!=0 && txtCB_Month.length!=0 && txtFrom_date.length!=0 && txtTo_date.length!=0)
        {
           var from_dat=txtFrom_date.split("/");
           var from_day=from_dat[0];
           var from_mon=from_dat[1];
           var from_year=from_dat[2];
           
           var to_dat=txtTo_date.split("/");
           var to_day=to_dat[0];
           var to_mon=to_dat[1];
           var to_year=to_dat[2];
           
           if(to_year<from_year)
           {
           alert("To Date Should Be Greater Than From Date");
           document.frmauto_Fund.txtTo_date.value="";
           return false;
           }
           else if((to_year>=from_year))
           {
               
                if(to_mon<from_mon)
                {
                     alert("To Date Should Be Greater Than From Date");
                     document.frmauto_Fund.txtTo_date.value="";
                     return false;
                }
                else if((to_mon>=from_mon))
                {
                   
                    if(to_day<from_day)
                    { 
                        alert("To Date Should Be Greater Than From Date");
                       document.frmauto_Fund.txtTo_date.value="";
                        return false;
                    }
               
                }                
            }
           
            var url="../../../../../Auto_Fund_Receipt_Create_atHOServ?Command=searchByDate&cmbAcc_UnitCode="+cmbAcc_UnitCode+"&remit_type="+remit_type+
            "&cmbOffice_code="+cmbOffice_code+"&txtFrom_date="+txtFrom_date+"&txtTo_date="+txtTo_date+"&txtCB_Year="+txtCB_Year+"&txtCB_Month="+txtCB_Month+
            "&displayingOrder="+displayingOrder+"&txtRegionId="+txtRegionId+"&txtBankAccountNo="+txtBankAccountNo+"&txtCrea_date="+txtCrea_date;
         //  alert(url);
            var req=getTransport();
            req.open("GET",url,true); 
            req.onreadystatechange=function()
            {
               handleResponse(req);
            }   
               req.send(null);           
        }
    }       
}


 ////Handle Response 
function handleResponse(req)
{  
    if(req.readyState==4)
    {
        if(req.status==200)
        {  
            var baseResponse=req.responseXML.getElementsByTagName("response")[0];
            var tagcommand=baseResponse.getElementsByTagName("command")[0];
            var Command=tagcommand.firstChild.nodeValue;
            if(Command=="searchByMonth")
            {
            	//alert(Command);
                loadTable(baseResponse);
            }   
        }
    }
}

function changeCR(seqq,unit)
{
	
	
	//alert(seqq+unit);
	var crval;
	unit=document.getElementById("cmbAcc_UnitCode").value;
	var drh=document.getElementById("drHOA"+seqq).value;

	if(document.getElementById("cmbHOAccountNum").value=="")
	{
		alert('select HO A/c No .... ');
	}else{
	var accNo=(document.getElementById("cmbHOAccountNum").value).split("/")[0];
	
	//var acc_No=document.getElementById(elementId)
	//alert(drh);

	if(unit==5){
		//alert(accNo);
		if(accNo==""){
			alert('Select HO A/C No then Change DR HOA ...');
			document.getElementById("drHOA"+seqq).value="";
			document.getElementById("drHOA"+seqq).focus();
		}
		//alert('teetet'+unit);
		else if(accNo==800710210000006||accNo==800710210000011){ ///MBK@27/01/2021
			
			document.getElementById("crHOA"+seqq).value=822151;
		}
		else{

			
			if(drh.substr(4,6)=="03")
				{
				crval="06";
				document.getElementById("crHOA"+seqq).value=drh.substr(0,4)+crval;
				}
			else if(drh.substr(4,6)=="02")
				{
				crval="05";
				document.getElementById("crHOA"+seqq).value=drh.substr(0,4)+crval;
				}
			/*else {
					document.getElementById("crHOA"+seqq).value=drh;
				}*/
			else if(drh.substr(4,6)=="11")
			{
			crval="62";
			document.getElementById("crHOA"+seqq).value=drh.substr(0,4)+crval;
			}else {
				document.getElementById("crHOA"+seqq).value=drh;
			}
			
		}
	}else{
	
			if(drh.substr(4,6)=="03")
				{
				crval="06";
				document.getElementById("crHOA"+seqq).value=drh.substr(0,4)+crval;
				}
			else if(drh.substr(4,6)=="02")
			{
			crval="05";
			document.getElementById("crHOA"+seqq).value=drh.substr(0,4)+crval;
			}else if(drh.substr(4,6)=="11")
			{
				crval="62";
				document.getElementById("crHOA"+seqq).value=drh.substr(0,4)+crval;
				}else {
				document.getElementById("crHOA"+seqq).value=drh;
			}
		
	}
	}
}

function changeAcNo(seq){
	document.getElementById("drHOA"+seq).value="";
}

function  loadTable(baseResponse)
{
    var flag=baseResponse.getElementsByTagName("flag")[0].firstChild.nodeValue;
    var tbody=document.getElementById("tbody");
            try
            {
                tbody.innerHTML="";
            }
            catch(e){
                tbody.innerText="";
            }
    if(flag=="success")
    {
    	 document.getElementById("testsubmitdiv").style.display="block";
    	    document.getElementById("testexitdiv").style.display="none";
        var service=baseResponse.getElementsByTagName("details");
        if(service)
        { 
            var i=0;
            
            s=0;
            items1=new Array();
            items2=new Array();
            items3=new Array();
            items4=new Array();
            items5=new Array();
            items6=new Array();
            items7=new Array();
            items8=new Array();
            items9=new Array();
            items10=new Array();
            items11=new Array();
            items12=new Array();
            items13=new Array();
            items14=new Array();            
            items15=new Array();
            items16=new Array();
            items17=new Array();
            
            for(i=0;i<service.length;i++)
            {
            	
                var items=new Array();
                items1[i]=service[i].getElementsByTagName("accounting_unit_id")[0].firstChild.nodeValue;
                items2[i]=service[i].getElementsByTagName("accounting_for_office_id")[0].firstChild.nodeValue;
                items3[i]=service[i].getElementsByTagName("remittance_type")[0].firstChild.nodeValue;                
                items4[i]=service[i].getElementsByTagName("date_of_transfer")[0].firstChild.nodeValue;                
                items5[i]=service[i].getElementsByTagName("voucher_no")[0].firstChild.nodeValue;                
                items6[i]=service[i].getElementsByTagName("cr_account_head_code")[0].firstChild.nodeValue;                                                          
                items7[i]=service[i].getElementsByTagName("cr_account_head_code_desc")[0].firstChild.nodeValue;                
                items8[i]=service[i].getElementsByTagName("ho_account_no")[0].firstChild.nodeValue;                
                items9[i]=service[i].getElementsByTagName("total_amount")[0].firstChild.nodeValue;                
                items10[i]=service[i].getElementsByTagName("dr_account_head_code")[0].firstChild.nodeValue; 
                items11[i]=service[i].getElementsByTagName("dr_account_head_code_desc")[0].firstChild.nodeValue;                
                items12[i]=service[i].getElementsByTagName("office_account_no")[0].firstChild.nodeValue;                
                items13[i]=service[i].getElementsByTagName("accounting_unit_name")[0].firstChild.nodeValue;                
                items14[i]=service[i].getElementsByTagName("ho_account_no_desc")[0].firstChild.nodeValue;                
                items15[i]=service[i].getElementsByTagName("cheque_or_dd")[0].firstChild.nodeValue;
                items16[i]=service[i].getElementsByTagName("cashbook_month")[0].firstChild.nodeValue;
                items17[i]=service[i].getElementsByTagName("cashbook_year")[0].firstChild.nodeValue;
                
              
                if(items3[i]=='WATCHARGEREV_Hogenakkal')
                	{
                	 if(items6[i]!='820706')
                		 {
                		 alert("Credit account should be 820706");
                		 return false;
                		 }
                	}
                
                
                var tbody=document.getElementById("tbody");
                var mycurrent_row=document.createElement("TR");
                j++;
             
             
               // alert("crfff::"+items17[i]);
                
                //-------------------------- Cell2 ------------------------------------//
                
            
   var checkparam = seq ;
                
                var descell=document.createElement("TD");
                descell.style.textAlign='center'; 
                var chcksel="";
                if(window.navigator.appName.toLowerCase().indexOf("netscape")==-1)
                {
                    chcksel=document.createElement("<input type='checkbox' name='chckparameter' id='chckparameter' value='"+checkparam+"' />");
                }
                else
                {
                       var chcksel=document.createElement("input");
                       chcksel.type="checkbox";
                       chcksel.name="chckparameter";
                       chcksel.id="chckparameter";                      
                       chcksel.value= seq;
                }
                descell.appendChild(chcksel);
                mycurrent_row.appendChild(descell);
                
                                    
                // alert(checkparam)  ;                 
                //-------------------------- Cell2.5 ------------------------------------//
                
                //** Voucher Number *//*
                
                var cell2_5=document.createElement("TD");
                
                // Hidden Voucher Number 
                var Vou_No=document.createElement("input");
                Vou_No.type="hidden";
                Vou_No.name="Vou_No";
                Vou_No.id="Vou_No";
                Vou_No.value=items5[i];
                Vou_No.size="5";
                cell2_5.appendChild(Vou_No);  
               
                // Displaying Voucher Number 
                var currentText1=document.createTextNode(items5[i]);                
                var fnt=document.createElement("font");
                fnt.size="2";
                fnt.appendChild(currentText1);                
                cell2_5.appendChild(fnt);        
                
                mycurrent_row.appendChild(cell2_5);
                
                                    
                //-------------------------- Cell3 ------------------------------------//
                
               
                
                //** DR Account Head Code *//*
                var cell3=document.createElement("TD");
                var currentText1=document.createTextNode(items10[i]);                
                var fnt=document.createElement("font");
                fnt.size="2";
                fnt.appendChild(currentText1);                
                cell3.appendChild(fnt); 
                
                //creating dynamic accounthead code
               
               var cell3=document.createElement("TD");
               var drHOA;
              
	           if (window.navigator.appName.toLowerCase().indexOf("netscape") == -1)
	           {
	         	     try
	                 {
	         	    	drHOA =  document.createElement("<select name='drHOA' id='drHOA' onchange='changeCR('"+seq+","+items1[i]+");' >");
	                 }
	                 catch( e ) 
	                 {
	                   alert(e.description) ;
	                 }
	           }
	           else
	           {
	        	   drHOA=document.createElement("select");                        
	        	   drHOA.id="drHOA"+seq;
	        
	        	 
	        	   drHOA.name="drHOA"+seq;
	        	    var option11=document.createElement("option");    
	                  option11.value="";  
	                  option11.text="--Select HO A/C Num --";
	                  
	             try
	               {
	            	 drHOA.add(option11);
	               }
	            catch(e)
	               {
	            	drHOA.add(option11,null);
	               } 
	            drHOA.setAttribute("onchange","changeCR("+seq+","+items1[i]+")");
	           }
	           var arr=new Array();
	           var tt1;
	           var tt=items10[i];
	           var last=tt.substr(4,6);
	          if(last=="03")
	        	   {
	        	   tt1=tt.substr(0,4)+"02";
	        	   arr[0]=tt;
		           arr[1]=tt1;
	        	   }
	           else if(last=="02")
	        	   {
	        	   tt1=tt.substr(0,4)+"03";
	        	   arr[0]=tt;
		           arr[1]=tt1;
	        //	   alert(tt+"       "+tt1);
	        	   }else{
	        		tt1=tt;  
	        		  arr[0]=tt;
	   	         //  arr[1]=tt1;
	        	   }
	         
	            for(var y=0;y<arr.length;y++)
	             {
	            	var option11=document.createElement("option");    
	            	   if (arr[y] == items10[i])
	            		   option11.selected=true;
	                  
	                  option11.value=arr[y];
	                  option11.text=arr[y];
	                try
	                 {
	                	 drHOA.add(option11);
	                 }
	                 catch(e)
	                 {
	                	 drHOA.add(option11,null);
	                 }
	             }           
	              
	            cell3.appendChild(drHOA);                                                    
	             mycurrent_row.appendChild(cell3);   
                
                //** Hidden Off Accounting Unit ID *//*
                var Off_UnitID=document.createElement("input");
                Off_UnitID.type="hidden";
                Off_UnitID.name="Off_UnitID";
                Off_UnitID.id="Off_UnitID";
                Off_UnitID.value=items1[i];
                Off_UnitID.size="5";
                cell3.appendChild(Off_UnitID);                
                
                
                //**Hidden Off Accounting Office ID *//*
                var Off_OfficeCode=document.createElement("input");
                Off_OfficeCode.type="hidden";
                Off_OfficeCode.name="Off_OfficeCode";
                Off_OfficeCode.id="Off_OfficeCode";
                Off_OfficeCode.value=items2[i];
                Off_OfficeCode.size="5";
                cell3.appendChild(Off_OfficeCode);                
                
                
                //** Hidden Cashbook Month *//*                               
                var Off_cb_Month=document.createElement("input");
                Off_cb_Month.type="hidden";
                Off_cb_Month.name="Off_cb_Month";
                Off_cb_Month.id="Off_cb_Month";
                Off_cb_Month.value=items16[i];
                Off_cb_Month.size="5";
                cell3.appendChild(Off_cb_Month);                
                
                //** Hidden Cashbook Year *//*                 
                var Off_cb_Year=document.createElement("input");
                Off_cb_Year.type="hidden";
                Off_cb_Year.name="Off_cb_Year";
                Off_cb_Year.id="Off_cb_Year";
                Off_cb_Year.value=items17[i];
                Off_cb_Year.size="5";
                cell3.appendChild(Off_cb_Year);   
                
                mycurrent_row.appendChild(cell3);
                
             
             
                
             // *//**
              // *  Dynamic Combo Creation and Loading 
            //   *//* 
              
          //     Head Office Bank Account Number 
              cell3_5=document.createElement("TD");
              cell3_5.style.textAlign='left';  
              var cmbHOAccountNum;
           
            	
              if (window.navigator.appName.toLowerCase().indexOf("netscape") == -1)
              {
            	
                    try
                    {
                      cmbHOAccountNum =  document.createElement("<select name='cmbHOAccountNum' id='cmbHOAccountNum' >");
                    }
                    catch( e ) 
                    {
                      alert(e.description) ;
                    }
              }
              else
              {
            	 
                    cmbHOAccountNum=document.createElement("select");                        
                    cmbHOAccountNum.id="cmbHOAccountNum";
                    cmbHOAccountNum.name="cmbHOAccountNum";
              }
              
              var ho_acc_number_OJB = baseResponse.getElementsByTagName("bank_details"+i);
              
              var option11=document.createElement("option");    
                  option11.value="";  
                  option11.text="--Select HO A/C Num --";
                  
             try
               {
                  cmbHOAccountNum.add(option11);
               }
            catch(e)
               {
                   cmbHOAccountNum.add(option11,null);
               }                      
            
          //  if
             for(var y=0;y<ho_acc_number_OJB.length;y++)
             {
                  ho_bank_ac_no[y]=ho_acc_number_OJB[y].getElementsByTagName("ho_bank_ac_no")[0].firstChild.nodeValue;
                  bankShow[y]=ho_acc_number_OJB[y].getElementsByTagName("bankShow")[0].firstChild.nodeValue;
                  
                  var option11=document.createElement("option");    
                  
                  if (ho_bank_ac_no[y] == items14[i])  option11.selected=true;
                  
                  option11.value=ho_bank_ac_no[y];  
                  option11.text=bankShow[y];
                  
                
                 try
                 {
                       cmbHOAccountNum.add(option11);
                 }
                 catch(e)
                 {
                        cmbHOAccountNum.add(option11,null);
                 }
             }           
            cmbHOAccountNum.setAttribute("onchange","changeAcNo("+seq+")");
             cell3_5.appendChild(cmbHOAccountNum);                                                    
             mycurrent_row.appendChild(cell3_5);   
              
          
                
                //--------------------------cell 4 -----------------------------------//
                
             //   *//** HO Account Number  *//*
           /*     var cell4=document.createElement("TD");                
                var currentText1=document.createTextNode(items8[i]);                
                var fnt=document.createElement("font");
                fnt.size="2";
                fnt.appendChild(currentText1);                
                cell4.appendChild(fnt);                
                mycurrent_row.appendChild(cell4);*/
                
                
                //--------------------------cell 5 -----------------------------------//
                
                //** Cr Account head Code  *//*
                var cell5=document.createElement("TD");
                var crHOA=document.createElement("input");
             //   crHOA.type="readonly";
                crHOA.setAttribute('readonly','readonly');
                crHOA.name="crHOA"+seq;
                crHOA.id="crHOA"+seq;
                crHOA.value=items6[i];
                crHOA.size="5";
                
//                var currentText1=document.createTextNode(items6[i]);                
//                var fnt=document.createElement("font");
//                fnt.size="2";
//                fnt.appendChild(currentText1);                
                cell5.appendChild(crHOA);                
                mycurrent_row.appendChild(cell5);
                
                
                //--------------------------cell 6 -----------------------------------//
                
                //** Office Account Number *//*
                var cell6=document.createElement("TD");
                var currentText1=document.createTextNode(items12[i]);                
                var fnt=document.createElement("font");
                fnt.size="2";
                fnt.appendChild(currentText1);                
                cell6.appendChild(fnt);                
                mycurrent_row.appendChild(cell6);
                
                
                //--------------------------cell 6.5 -----------------------------------//
                
                //** Fund Type *//*
                var cell6_5=document.createElement("TD");
                
                // Hidden Fund Type
                var FundType=document.createElement("input");
                FundType.type="hidden";
                FundType.name="FundType";
                FundType.id="FundType";
                FundType.value=items15[i];
                FundType.size="5";
                cell6_5.appendChild(FundType);  
               
                
                var currentText1=document.createTextNode(items15[i]);                
                var fnt=document.createElement("font");
                fnt.size="1";
                fnt.appendChild(currentText1);                
                cell6_5.appendChild(fnt);                
                mycurrent_row.appendChild(cell6_5);
                
                
                
               //--------------------------cell 7 -----------------------------------//
                
               //** Total Amount *//*
                var cell7=document.createElement("TD");
                cell7.align="right";
                // Hidden Total Amount
                var TotAmt=document.createElement("input");
                TotAmt.type="hidden";
                TotAmt.name="TotAmt";
                TotAmt.id="TotAmt";
                TotAmt.value=items9[i];
                TotAmt.size="5";
                cell7.appendChild(TotAmt);  
               
               
                var currentText1=document.createTextNode(items9[i]);                
                var fnt=document.createElement("font");
                fnt.size="2";
                fnt.appendChild(currentText1);                   
                cell7.appendChild(fnt);                          
                cell7.style.textAlign='right';
                mycurrent_row.appendChild(cell7);
                
                
               //--------------------------cell 8 -----------------------------------//
                
               //** Ref Number *//*
                var cell8=document.createElement("TD");
                var RefNo=document.createElement("input");
                RefNo.type="text";
                RefNo.name="RefNo";
                RefNo.id="RefNo";
                RefNo.value="";
                RefNo.size="10";
                cell8.appendChild(RefNo);                
                mycurrent_row.appendChild(cell8);
                
                
                //--------------------------cell 9 -----------------------------------//
                
                //** Ref Date *//*
                var cell9=document.createElement("TD");
                var RefDate=document.createElement("input");
                RefDate.type="text";
                RefDate.name="RefDate";
                RefDate.id="RefDate";
                RefDate.value="";
                RefDate.setAttribute('onkeypress',"calins_new(event,this);");
                RefDate.size="10";
                cell9.appendChild(RefDate);                
                mycurrent_row.appendChild(cell9);
                
                
              
                //--------------------------cell 11 -----------------------------------//
                
                //** Accounting Unit Name - Office Side *//*
                var cell11=document.createElement("TD");
                var currentText1=document.createTextNode(items13[i]);                
                var fnt=document.createElement("font");
                fnt.size="1";
                fnt.appendChild(currentText1);                
                cell11.appendChild(fnt);                
                mycurrent_row.appendChild(cell11);
                
              
                //--------------------------cell 12 -----------------------------------//
                
                //** Date of Transfer *//*
                var cell11=document.createElement("TD");
                var currentText1=document.createTextNode(items4[i]);                
                var fnt=document.createElement("font");
                fnt.size="2";
                fnt.appendChild(currentText1);                
                cell11.appendChild(fnt);                
                mycurrent_row.appendChild(cell11);
                
              
                tbody.appendChild(mycurrent_row);
                seq++;
                
            }             
         }
     }
     else
     {
        alert("Record Doesn't Exist");
        
     }
}






function setTrue(index)
{
	items11[index]=true;    
}





function numbersonly(e)
{
       var unicode=e.charCode? e.charCode : e.keyCode;
       if(unicode==13)
        {
        
        }
        if (unicode!=8 && unicode !=9  )
        {
            if (unicode<48 || unicode>57 ) 
                return false ;
        }
}


function clearGrid()
{
	 var tbody=document.getElementById("tbody");
	 alert("clearGrid called")
     try
     {
         tbody.innerHTML="";
     }
     catch(e){
         tbody.innerText="";
     }
}


function btSubmit()
{
var tbody=document.getElementById("tbody");
if(tbody.rows.length>0)
{
     rows=tbody.getElementsByTagName("tr");
     for(i=0;i<rows.length;i++)
        {
            cell = rows[i].cells[0];
            for (j=0; j<cell.childNodes.length; j++)
            {           
                if (cell.childNodes[j].type =="checkbox")
                {
              
                      if(cell.childNodes[j].checked==true)
                      {
                     // alert("trueeeeeeee");//8 9
                      cellTest = rows[i].cells[8].firstChild.value;
                      cellTest1 = rows[i].cells[9].firstChild.value;
                      
                          if(cellTest=="")
                          {
                          alert("Enter RefNo for selected CheckBox");
                          return false;
                          }
                          else if(cellTest1=="")
                          {
                           alert("Enter RefDate for selected CheckBox");
                           return false;
                          }
                      }
                }
            }
       }

}
    var url1="../../../../../Auto_Fund_Receipt_Create_atHOServ?command=submit";
    alert("url1====>"+url1);
    if (document.getElementById("txtCrea_date").value=="") 
    {
      alert("Please Enter Receipt Date");
      return false;
    }
 //    alert(seq);
    document.getElementById("TotalRecords").value=seq;  
    document.getElementById("testsubmitdiv").style.display="none";
    document.getElementById("testexitdiv").style.display="block";
    document.frmauto_Fund.action=url1;
    document.frmauto_Fund.method="post"; 
    document.frmauto_Fund.submit(); 
   
}
     

function calins_new(e,t)
{
	
    var unicode=e.charCode? e.charCode : e.keyCode;
        //alert(unicode);
        //if(unicode !=8)
        
        if (unicode!=8 && unicode !=9 && unicode!=37 && unicode !=39 && unicode !=46  && unicode !=35 && unicode !=36 )
        {
            if(t.value.length==2 || t.value.length==5)
                t.value=t.value + '/';
             if (unicode<48||unicode>57 ) 
                return false 
        }
       

}

function isValidDate(dateStr) {
	  
	  // Checks for the following valid date formats:
	  // MM/DD/YYYY
	  // Also separates date into month, day, and year variables
	  var datePat = /^(\d{2,2})(\/)(\d{2,2})\2(\d{4}|\d{4})$/;
	  
	  var matchArray = dateStr.match(datePat); // is the format ok?
	  if (matchArray == null) {
	   alert("Date must be in MM/DD/YYYY format")
	   return false;
	  }
	  
	  month = matchArray[3]; // parse date into variables
	  day = matchArray[1];
	  year = matchArray[4];
	  if (month < 1 || month > 12) { // check month range
	   alert("Month must be between 1 and 12");
	   return false;
	  }
	  if (day < 1 || day > 31) {
	   alert("Day must be between 1 and 31");
	   return false;
	  }
	  if ((month==4 || month==6 || month==9 || month==11) && day==31) {
	   alert("Month "+month+" doesn't have 31 days!")
	   return false;
	  }
	  if (month == 2) { // check for february 29th
	   var isleap = (year % 4 == 0 && (year % 100 != 0 || year % 400 == 0));
	   if (day>29 || (day==29 && !isleap)) {
	    alert("February " + year + " doesn't have " + day + " days!");
	    return false;
	     }
	  }
	  return true;  // date is valid
	 }






     
function checkdt(t)
{  

    if(t.value.length==0)
        return false;
    if(t.value.length==10  && t.value.indexOf('/',0)==2 && t.value.indexOf('/',3)==5)
    {
      
       
        
        var c=t.value;
//        try{
//        var f=DateFormat(t,c,event,true,'3');
//        }catch(e){
        
        
      ///New code implemented on 28-03-2019  for year 2019 wrongly displayed 201 
        try{
            var f=isValidDate(c);
           }
       catch(e){
        
         t.value=c;
            var sc=t.value.split('/');
            var currenDay =sc[0];
            var currentMonth=sc[1];
            var currentYear=sc[2];
            
            if(currentYear<_Service_Period_Beg_Year)
            {
            alert('Entered date should be greater than or equal to '+_Service_Period_Beg_Year);
            t.value="";
            t.focus();
            return false;
            }
            //alert(currentYear == getCurrentYear()  && currentMonth == getCurrentMonth() && currenDay > getCurrentDay());
            if(currentYear > getCurrentYear())
            {
            
                    alert('Entered date should be less than current date ');
                    t.value="";
                    t.focus();
                    return false;
           } 
           else if(currentYear == getCurrentYear())
            {
                    if( currentMonth > getCurrentMonth())
                    {
                        alert('Entered date should be less than current date');
                        t.value="";
                        t.focus();
                        return false;
                    }
                    else if( currentMonth == getCurrentMonth())
                    {
                        if(currenDay > getCurrentDay() )
                        {
                                alert('Entered date should be less than current date ');
                                t.value="";
                                t.focus();
                                return false;
                        }
                    }
                    
            }
            
            t.value=c;
             if(err!=0)
                {
                    t.value="";
                    return false;
                }
            return true;
        
        
        //exception end
        
        }
        if( f==true)
        {
            t.value=c;
            var sc=t.value.split('/');
            var currenDay =sc[0];
            var currentMonth=sc[1];
            var currentYear=sc[2];            
             if(currentYear<_Service_Period_Beg_Year)
            {
            alert('Entered date should be greater than or equal to '+_Service_Period_Beg_Year);
            t.value="";
            t.focus();
            return false;
            }
            if(currentYear > getCurrentYear())
            {            
                    alert('Entered date should be less than current date');
                    t.value="";
                    t.focus();
                    return false;
           } 
           else if(currentYear == getCurrentYear())
            {
                    if( currentMonth > getCurrentMonth())
                    {
                         alert('Entered date should be less than current date');
                        t.value="";
                        t.focus();
                        return false;
                    }
                    else if( currentMonth == getCurrentMonth())
                    {
                        if(currenDay > getCurrentDay() )
                        {
                                alert('Entered date should be less than current date ');
                                t.value="";
                                t.focus();
                                return false;
                        }
                    }
                    
            }
            
            t.value=c;
           
            return true;
            
        }
        else
        {
                if(err!=0)
                {
                    t.value="";
                    return false;
                }
        }
            
    }
    else
    {
            alert('Date format  should be (dd/mm/yyyy)');
            t.value="";           
            return false
    }    
}





function cb_month_year(id)
{
	 
   var Regions=document.getElementById("Regions");
   var Banks=document.getElementById("Banks");
 
  if(id=="RW")
  {
	  Regions.style.display="block";
	  Banks.style.display="none";
  }
  if(id=="BW")
  {
	  
	 Banks.style.display="block";
     Regions.style.display="none";
  }
}





//** Combo Selection *//*
function selectAll(Opt)
{

  var len=  document.getElementById("tbody").rows.length;
  
  if(len==1)
  {
          if ( Opt =="ALL")
          {
             document.getElementById("chckparameter").checked=true;
          }
          else if (Opt=="UNSelect" )
          {
             document.getElementById("chckparameter").checked=false;
          }
  }
  else if(len>1)
  {
          for(var i=0;i<len;i++)
          {
                if ( Opt =="ALL")
                {
                    document.frmauto_Fund.chckparameter[i].checked=true;
                }
                else if(Opt=="UNSelect")
                {
                    document.frmauto_Fund.chckparameter[i].checked=false;
                }
          }
  }

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
