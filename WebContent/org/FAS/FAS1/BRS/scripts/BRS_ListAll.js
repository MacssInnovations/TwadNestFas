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

function loadfun()
{
	 	 var acc_unit_id=document.getElementById("acc_unit_id").value;
	 	 var office_code=document.getElementById("office_code").value;
	 	 var cash_book_yr_from=document.getElementById("cash_book_yr_from").value;
 		 var cash_book_mn_from=document.getElementById("cash_book_mn_from").value;
 		 var BankAccNo=document.getElementById("BankAccNo").value;
	 	 
	 	 var url="cmbAcc_UnitCode="+acc_unit_id+"&cmbOffice_code="+office_code+"&txtCB_Month_From="+cash_book_mn_from
	 	 +"&txtCB_Year_From="+cash_book_yr_from+"&BankAccNo="+BankAccNo;
	 	
	 		 var url="../../../../../BRS_NonTwad?command=LoadList&option=List&"+url;
               //   alert(url);       
		 var req=getTransport();
	     req.open("GET",url,true); 
	     req.onreadystatechange=function()
	     {
	    	   LoadBRSList(req);
	     }   
	     req.send(null);
}
function loadfun_ob()
{

	 	 var acc_unit_id=document.getElementById("acc_unit_id").value;
	 	
	 	 var office_code=document.getElementById("office_code").value;
	 	 var cash_book_yr_from=document.getElementById("cash_book_yr_from").value;
	 	
 		 var cash_book_mn_from=document.getElementById("cash_book_mn_from").value;
 		
 		 var cashbook_yr_to=document.getElementById("cash_book_yr_to").value;
 		
 		 var cashbook_mn_to=document.getElementById("cash_book_mn_to").value;
 		
 		 var BankAccNo=document.getElementById("BankAccNo").value;
	 	 var url="cmbAcc_UnitCode="+acc_unit_id+"&cmbOffice_code="+office_code+"&txtCB_Month_From="+cash_book_mn_from
	 	 +"&txtCB_Year_From="+cash_book_yr_from+"&BankAccNo="+BankAccNo+"&cashbook_mn_to="+cashbook_mn_to+"&cashbook_yr_to="+cashbook_yr_to;
	 	
	 	var url="../../../../../BRS_NonTwad?command=LoadListOb&option=List&"+url;
         // alert(url);  
		 var req=getTransport();
	     req.open("GET",url,true); 
	     req.onreadystatechange=function()
	     {
	    	
	    	   LoadBRSList(req,acc_unit_id,office_code);
	     }   
	     req.send(null);
}


function LoadBRSList(req)
{
	 	 if(req.readyState==4)
	     {
	           if(req.status==200)
	           { 

	      	 	 var acc_unit_id=document.getElementById("acc_unit_id").value;
	      	 	
	      	 	 var office_code=document.getElementById("office_code").value;
	             	var baseResponse=req.responseXML.getElementsByTagName("response")[0];
	             	var tagcommand=baseResponse.getElementsByTagName("command")[0].firstChild.nodeValue;
	             	
	             	var flag=baseResponse.getElementsByTagName("flag")[0].firstChild.nodeValue;
	             	if(flag=="success")
	             	{
	             		//alert(flag);
	             	var slno=1;
	             			var count=baseResponse.getElementsByTagName("acc_no");
    	             		var tbody=document.getElementById("tb");
    	             		for(var i=0;i<count.length;i++)
    	             		{
    	             				var items=new Array();
    	             			 	items[0]=baseResponse.getElementsByTagName("acc_no")[i].firstChild.nodeValue;
			                        items[1]=baseResponse.getElementsByTagName("passbook_dt")[i].firstChild.nodeValue;
			                        items[2]=baseResponse.getElementsByTagName("particulars")[i].firstChild.nodeValue;
			                        items[3]=baseResponse.getElementsByTagName("part_desc")[i].firstChild.nodeValue;  
			                        items[4]=baseResponse.getElementsByTagName("cheque_no")[i].firstChild.nodeValue;
			                        items[5]=baseResponse.getElementsByTagName("cheque_details")[i].firstChild.nodeValue;
			                        items[6]=baseResponse.getElementsByTagName("cr_amount")[i].firstChild.nodeValue;
			                        items[7]=baseResponse.getElementsByTagName("dr_amount")[i].firstChild.nodeValue;
			                        items[8]=baseResponse.getElementsByTagName("actionreq")[i].firstChild.nodeValue;
			                        items[9]=baseResponse.getElementsByTagName("slno")[i].firstChild.nodeValue;
			                        items[10]=baseResponse.getElementsByTagName("year")[i].firstChild.nodeValue;
			                        items[11]=baseResponse.getElementsByTagName("month")[i].firstChild.nodeValue;
			                        items[12]=baseResponse.getElementsByTagName("REASON")[i].firstChild.nodeValue;
			                        items[13]=baseResponse.getElementsByTagName("REASON_desc")[i].firstChild.nodeValue;
			                         
			                        //alert(items[11]);
			                        // items[12]=baseResponse.getElementsByTagName("nt")[i].firstChild.nodeValue;
			                        var mycurrent_row=document.createElement("TR");
			                        mycurrent_row.id=items[0];
			                        
			                       var cell1=document.createElement("TD");
			                       var hr=document.createElement("A");
			                       hr.href="javascript:editCtrl("+acc_unit_id+","+office_code+","+items[10]+","+items[11]+",'"+items[1]+"',"+items[12]+","+items[4]+",'"+items[5]+"',"+items[6]+","+items[7]+",'"+items[8]+"',"+items[0]+","+items[9]+")";
			                    //joe    change 6:43
			                        var accNO = document.createTextNode("Edit");
			                        hr.appendChild(accNO);
			                        cell1.appendChild(hr);
	                                mycurrent_row.appendChild(cell1);
	                                
	                                var cell1=document.createElement("TD");
			                        var accNO = document.createTextNode(items[0]);
			                        cell1.appendChild(accNO);
	                                mycurrent_row.appendChild(cell1);
	                                
	                                var cell=document.createElement("TD");
			                        var serialno = document.createTextNode(items[9]);
			                        cell.appendChild(serialno);
	                                mycurrent_row.appendChild(cell);
	                                
	                                var cell=document.createElement("TD");
			                        var serialno = document.createTextNode(items[10]);
			                        cell.appendChild(serialno);
	                                mycurrent_row.appendChild(cell);
	                                
	                                var cell=document.createElement("TD");
			                        var serialno = document.createTextNode(items[11]);
			                        cell.appendChild(serialno);
	                                mycurrent_row.appendChild(cell);
	                                
	                                var cell2=document.createElement("TD");
			                        var OB1 = document.createTextNode(items[1]);
			                        cell2.appendChild(OB1);
	                                mycurrent_row.appendChild(cell2);
	                                
	                                var cell2=document.createElement("TD");
	                                var reason_code=document.createElement("input");
	                                reason_code.type="hidden";
	                                reason_code.name="H_code";
	                                reason_code.value=items[12];
	                                cell2.appendChild(reason_code);
			                        var reason_text = document.createTextNode(items[13]);
			                        cell2.appendChild(reason_text);
	                                mycurrent_row.appendChild(cell2);
	                                
	                                var cell2=document.createElement("TD");
			                        var OB1 = document.createTextNode(items[4]);
			                        cell2.appendChild(OB1);
	                                mycurrent_row.appendChild(cell2);
	                                
	                                
	                                var cell2=document.createElement("TD");       
	                                var H_code=document.createElement("input");
	                                H_code.type="hidden";
	                                H_code.name="H_code";
	                                H_code.value=items[2];
	                                cell2.appendChild(H_code);
	                                var currentText=document.createTextNode(items[5]);
	                                cell2.appendChild(currentText);
	                                mycurrent_row.appendChild(cell2);
	                               
	                               
	                                cell2=document.createElement("TD");
	                                var SL_type=document.createElement("input");
	                                SL_type.type="hidden";
	                                SL_type.name="SL_type";
	                                SL_type.value=items[6];
	                                cell2.appendChild(SL_type);
	                                var currentText=document.createTextNode(items[6]);
	                                 cell2.setAttribute("align","right");  
	                                cell2.appendChild(currentText);
	                                mycurrent_row.appendChild(cell2);
	                                
	                                cell2=document.createElement("TD");
	                                var SL_type=document.createElement("input");
	                                SL_type.type="hidden";
	                                SL_type.name="SL_type";
	                                SL_type.value=items[7];
	                                cell2.appendChild(SL_type);
	                                var currentText=document.createTextNode(items[7]);
	                                cell2.setAttribute("align","right");  
	                                cell2.appendChild(currentText);
	                                mycurrent_row.appendChild(cell2);
	                                
	                                cell2=document.createElement("TD");
	                                var SL_type=document.createElement("input");
	                                SL_type.type="hidden";
	                                SL_type.name="SL_type";
	                                SL_type.value=items[8];
	                                cell2.appendChild(SL_type);
	                                var currentText=document.createTextNode(items[8]);
	                                cell2.appendChild(currentText);
	                                mycurrent_row.appendChild(cell2);
	                                
	           
			                        slno++;
	                               tbody.appendChild(mycurrent_row);
    	             		}
	             	}
	             	else if(flag=="failure")
	             		{
	             			alert("No recordst to list out");
	             		}
	           }
	     }
	     
}
function editCtrl(unit_id,office_id,year,month,pas_dt,reason,chk_no,details,Cr,Dr,act_req,ac_no,slno){
var url="../../../../../org/FAS/FAS1/BRS/jsps/BRS_NonTwadEdit.jsp?unit_id="+unit_id+"&office_id="+office_id+
"&year="+year+"&month="+month+"&pas_dt="+pas_dt+"&reason="+reason+"&chk_no="+chk_no+"&details="+details
+"&Cr="+Cr+"&Dr="+Dr+"&act_req="+act_req+"&ac_no="+ac_no+"&slno="+slno;
//alert('test');
	window.open(url	, '_blank', 'location=no,height=570,width=520,scrollbars=yes,status=yes');
	window.close();
}





function loadTable(scod,scod1,scod2,scod3,scod4,scod5,scod6,scod7,scod8,scod9,scod10)
{
	 Minimize();
     opener.doParentEmp(scod,scod1,scod2,scod3,scod4,scod5,scod6,scod7,scod8,scod9,scod10);
     return true;
}

function onSubmit()
{		 
	     var v=document.getElementsByName("check1");
	     if(v)
	     {
		       for(i=0;i<v.length;i++)
		       {
		                if(v[i].checked==true)
		                {		                	
		                    Minimize();
		                    opener.doParentEmp(v[i].value);
		                    return true;
		                }
		       }
	     }
}

function Minimize()
{
	     window.close();
	     opener.window.focus();
}