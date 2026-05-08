
/* Browser Identification */

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

function btncancel()
{

	self.close();
}




function clearAll()
{
        document.forms[0].VOUCHER_NO.value="";
        document.forms[0].VOUCHER_date.value="";
        document.forms[0].VOUCHER_TYPE.value="";
        document.forms[0].PARTICULARS.value="";
        document.forms[0].TOTAL_AMOUNT.value="";
        document.forms[0].pending_AMOUNT.value="";
        
        
}

function Report()
{
	//alert("Report function called!......");    
	
	var cmbAcc_UnitCode=document.getElementById("cmbAcc_UnitCode").value;    
	var cmbOffice_code=document.getElementById("cmbOffice_code").value;
	var txtCB_Year=document.getElementById("txtCB_Year").value;
		var txtCB_Month=document.getElementById("txtCB_Month").value;
		var txtFrom_date=document.getElementById("txtFrom_date").value;
		var txtTo_date=document.getElementById("txtTo_date").value;
		
		
		var url="../../../../../LJVSPendingServlet?cmbAcc_UnitCode="+cmbAcc_UnitCode+
			"&cmbOffice_code="+cmbOffice_code+"&txtCB_Year="+txtCB_Year+"&txtCB_Month="+txtCB_Month+
			"&txtfromdate="+txtFrom_date+"&txttodate="+txtTo_date;
		//	alert("URL==>"+url);
			
			document.forms[0].action=url;
		    document.forms[0].method="POST";
		    document.forms[0].submit();
		    return true;
					
	    
	
}



/* Main Function */
function doFunction(Command,param)
{   

	var cmbAcc_UnitCode=document.getElementById("cmbAcc_UnitCode").value;    
	var cmbOffice_code=document.getElementById("cmbOffice_code").value;
	var cmbStatus=document.getElementById("cmbStatus").value;

	/*if(cmbAcc_UnitCode==5)
        {
        document.getElementById("tbody1").style.display="none";	
        document.getElementById("tbody").style.display="block";
        }
        else
        {
        	document.getElementById("tbody1").style.display="block";	
            document.getElementById("tbody").style.display="none";
        }
	 */

	if(Command=="searchByMonth")
	{  
		var txtCB_Year=document.getElementById("txtCB_Year").value;
		var txtCB_Month=document.getElementById("txtCB_Month").value;
		if(txtCB_Year.length!=0 && txtCB_Month.length!=0)
		{
			var url="../../../../../LJVSPendingServlet?Command=searchByMonth&cmbAcc_UnitCode="+cmbAcc_UnitCode+
			"&cmbOffice_code="+cmbOffice_code+"&txtCB_Year="+txtCB_Year+"&txtCB_Month="+txtCB_Month+"&cmbStatus="+cmbStatus;
			// alert(url);
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
		// alert("fdf");
		var txtCB_Year=document.getElementById("txtCB_Year").value;
		var txtCB_Month=document.getElementById("txtCB_Month").value;
		var txtFrom_date=document.getElementById("txtFrom_date").value;
		var txtTo_date=document.getElementById("txtTo_date").value;
		if(txtCB_Year.length!=0 && txtCB_Month.length!=0 && txtFrom_date.length!=0 && txtTo_date.length!=0)
		{
			var url="../../../../../LJVSPendingServlet?Command=searchByDate&cmbAcc_UnitCode="+cmbAcc_UnitCode+
			"&cmbOffice_code="+cmbOffice_code+"&txtCB_Year="+
			txtCB_Year+"&txtCB_Month="+txtCB_Month+"&txtFrom_date="+txtFrom_date+"&txtTo_date="+txtTo_date+"&cmbStatus="+cmbStatus;
			//alert(url);
			var req=getTransport();
			req.open("GET",url,true); 
			req.onreadystatechange=function()
			{
				handleResponse(req);
			}   
			req.send(null);
		}
		else
			alert("Enter the Cash Book Year/Month and From date and To date");

	}
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
			return false;
	}
}



function click_jrnl(seq)
{

	alert(seq);
	var value=document.getElementById("notcleared"+seq).value;
}

/*
function click_jrnl1(seq)
{

	alert(seq);
	var value=document.getElementById("notcleared"+seq).value;

	document.getElementById("cleared"+seq).disabled=false;
	}*/

function handleResponse(req)
{  
	if(req.readyState==4)
	{ 
		if(req.status==200)
		{  
			//alert("req.status"+req.responseText);   
			var baseResponse=req.responseXML.getElementsByTagName("response")[0];
			//alert("hhhhhhhhhhh"+baseResponse);
			var tagcommand=baseResponse.getElementsByTagName("command")[0];
			var flag=baseResponse.getElementsByTagName("flag")[0].firstChild.nodeValue;
			//alert("tagcommand"+tagcommand);
			var Command=tagcommand.firstChild.nodeValue;
			//alert("Command "+Command);
			var tbody=document.getElementById("tbody");
			//alert("tbody"+tbody);
			var t=0;seq=0;
			for(t=tbody.rows.length-1;t>=0;t--)
			{
				tbody.deleteRow(0);
			} 

			if(flag=="failure")
			{
				alert("No Record exists");

			}
			else if(Command=="searchByMonth")
			{
				var len=baseResponse.getElementsByTagName("VOUCHER_NO").length;

				var lll=1;
				var item = new Array();

				for(var k=0;k<len;k++)
				{

					item[0]=baseResponse.getElementsByTagName("VOUCHER_NO")[k].firstChild.nodeValue;
					item[1] =baseResponse.getElementsByTagName("VOUCHER_DATE")[k].firstChild.nodeValue;
					item[2] =baseResponse.getElementsByTagName("VOUCHER_TYPE")[k].firstChild.nodeValue;
					item[3] =baseResponse.getElementsByTagName("PARTICULARS")[k].firstChild.nodeValue;
					item[4]=baseResponse.getElementsByTagName("TOTAL_AMOUNT")[k].firstChild.nodeValue;
					item[8] =baseResponse.getElementsByTagName("pending_AMOUNT")[k].firstChild.nodeValue;
					item[9] =baseResponse.getElementsByTagName("amount2")[k].firstChild.nodeValue;


					var mycurrent_row=document.createElement("TR");
					mycurrent_row.id=item[0];                     
					var cell1 = document.createElement("TD");
					var dateofentry1=document.createElement("input");
					dateofentry1.type="hidden";
					dateofentry1.name="VOUCHER_NO";
					dateofentry1.id="VOUCHER_NO";
					dateofentry1.value=item[0];
					var dateofentry = document.createTextNode(item[0]);
					dateofentry.size=7;
					//cell1.appendChild(VOUCHER_NO);
					cell1.appendChild(dateofentry);
					cell1.appendChild(dateofentry1);
					mycurrent_row.appendChild(cell1);

					var cell2 = document.createElement("TD");
					var assetcode1=document.createElement("input");
					assetcode1.type="hidden";
					assetcode1.name="VOUCHER_DATE";
					assetcode1.id="VOUCHER_DATE";
					assetcode1.value=item[1];
					var assetcode = document.createTextNode(item[1]);
					assetcode.size=7;
					//cell2.appendChild(VOUCHER_DATE);
					cell2.appendChild(assetcode);
					cell2.appendChild(assetcode1);
					cell2.align="right";
					mycurrent_row.appendChild(cell2);

					var cell3 = document.createElement("TD");
					var qtyavlasondate1=document.createElement("input");
					qtyavlasondate1.type="hidden";
					qtyavlasondate1.name="VOUCHER_TYPE";
					qtyavlasondate1.id="VOUCHER_TYPE";
					qtyavlasondate1.value=item[2];
					var qtyavlasondate = document.createTextNode(item[2]);
					qtyavlasondate.size=7;
					//cell3.appendChild(VOUCHER_TYPE);
					cell3.appendChild(qtyavlasondate);
					cell3.appendChild(qtyavlasondate1);
					cell3.align="right";
					mycurrent_row.appendChild(cell3);

					var cell4 = document.createElement("TD");
					var locationdesc1=document.createElement("input");
					locationdesc1.type="hidden";
					locationdesc1.name="PARTICULARS";
					locationdesc1.id="PARTICULARS";
					locationdesc1.value=item[3];         			
					var locationdesc = document.createTextNode(item[3]);
					locationdesc.size=7;
					//cell4.appendChild(PARTICULARS);
					cell4.appendChild(locationdesc);
					cell4.appendChild(locationdesc1);
					cell4.align="left";
					mycurrent_row.appendChild(cell4);

					var cell5 = document.createElement("TD");
					var status1=document.createElement("input");
					status1.type="hidden";
					status1.name="TOTAL_AMOUNT";
					status1.id="TOTAL_AMOUNT";
					status1.value=item[4];
					var status = document.createTextNode(item[4]);
					status.size=7;
					//cell5.appendChild(TOTAL_AMOUNT);
					cell5.appendChild(status);
					cell5.appendChild(status1);
					mycurrent_row.appendChild(cell5);
					
					var cell6 = document.createElement("TD");
					var status2=document.createElement("input");
					status2.type="hidden";
					status2.name="TOTAL_AMOUNT1";
					status2.id="TOTAL_AMOUNT1";
					status2.value=item[9];
					var status3 = document.createTextNode(item[4]);
					status2.size=7;
					//cell5.appendChild(TOTAL_AMOUNT);
					cell6.appendChild(status3);
					cell6.appendChild(status2);
					mycurrent_row.appendChild(cell6);
					


					var cell7 = document.createElement("TD");
					cell7.style.textAlign = 'center';
					var	cleared = document.createElement('input');
					cleared.type = "checkbox";
					cleared.id="cleared";
					if(item[8]==0)
					{
						cleared.checked = true;
						cleared.disabled=true;
					}
					else
					{
						cleared.checked = false;
						cleared.disabled=true;
					}
					//var status2 = document.createTextNode(item[7]);
					cell7.appendChild(cleared);
					//cell6.appendChild(status2);
					mycurrent_row.appendChild(cell7);

					var cell8 = document.createElement("TD");
					cell8.style.textAlign = 'center';
					partlycleared = document.createElement('input');
					partlycleared.type = "checkbox";
					if(item[8]>0){
						partlycleared.checked = true;
						partlycleared.disabled=true;
					}
					else
					{
						partlycleared.checked = false;
						partlycleared.disabled=true;
					}
					partlycleared.id="partlycleared";
					//var status3 = document.createTextNode(item[7]);
					//cell7.appendChild(status3);
					cell8.appendChild(partlycleared);
					mycurrent_row.appendChild(cell8);




					var cell9 = document.createElement("TD");
					var remarks1=document.createElement("input");
					remarks1.type="hidden";
					remarks1.name="pending_AMOUNT";
					remarks1.id="pending_AMOUNT";
					remarks1.value=item[8];
					var remarks = document.createTextNode(item[8]);
					remarks.size=7;
					cell9.align="left";

					cell9.appendChild(remarks);
					cell9.appendChild(remarks1);
					mycurrent_row.appendChild(cell9);


					//lll++;
					tbody.appendChild(mycurrent_row);
					seq+=1;  
				}

			}
			else if(Command=="searchByDate"){
				var len=baseResponse.getElementsByTagName("VOUCHER_NO").length;

				var lll=1;
				var item = new Array();

				for(var k=0;k<len;k++)
				{
					item[0]=baseResponse.getElementsByTagName("VOUCHER_NO")[k].firstChild.nodeValue;
					item[1] =baseResponse.getElementsByTagName("VOUCHER_DATE")[k].firstChild.nodeValue;
					item[2] =baseResponse.getElementsByTagName("VOUCHER_TYPE")[k].firstChild.nodeValue;
					item[3] =baseResponse.getElementsByTagName("PARTICULARS")[k].firstChild.nodeValue;
					item[4]=baseResponse.getElementsByTagName("TOTAL_AMOUNT")[k].firstChild.nodeValue;
					item[8] =baseResponse.getElementsByTagName("pending_AMOUNT")[k].firstChild.nodeValue;
					item[9] =baseResponse.getElementsByTagName("amount2")[k].firstChild.nodeValue;


					var mycurrent_row=document.createElement("TR");
					mycurrent_row.id=item[0];                     
					var cell1 = document.createElement("TD");
					var dateofentry1=document.createElement("input");
					dateofentry1.type="hidden";
					dateofentry1.name="VOUCHER_NO";
					dateofentry1.id="VOUCHER_NO";
					dateofentry1.value=item[0];
					var dateofentry = document.createTextNode(item[0]);
					dateofentry.size=7;
					//cell1.appendChild(VOUCHER_NO);
					cell1.appendChild(dateofentry);
					cell1.appendChild(dateofentry1);
					mycurrent_row.appendChild(cell1);

					var cell2 = document.createElement("TD");
					var assetcode1=document.createElement("input");
					assetcode1.type="hidden";
					assetcode1.name="VOUCHER_DATE";
					assetcode1.id="VOUCHER_DATE";
					assetcode1.value=item[1];
					var assetcode = document.createTextNode(item[1]);
					assetcode.size=7;
					//cell2.appendChild(VOUCHER_DATE);
					cell2.appendChild(assetcode);
					cell2.appendChild(assetcode1);
					cell2.align="right";
					mycurrent_row.appendChild(cell2);

					var cell3 = document.createElement("TD");
					var qtyavlasondate1=document.createElement("input");
					qtyavlasondate1.type="hidden";
					qtyavlasondate1.name="VOUCHER_TYPE";
					qtyavlasondate1.id="VOUCHER_TYPE";
					qtyavlasondate1.value=item[2];
					var qtyavlasondate = document.createTextNode(item[2]);
					qtyavlasondate.size=7;
					//cell3.appendChild(VOUCHER_TYPE);
					cell3.appendChild(qtyavlasondate);
					cell3.appendChild(qtyavlasondate1);
					cell3.align="right";
					mycurrent_row.appendChild(cell3);

					var cell4 = document.createElement("TD");
					var locationdesc1=document.createElement("input");
					locationdesc1.type="hidden";
					locationdesc1.name="PARTICULARS";
					locationdesc1.id="PARTICULARS";
					locationdesc1.value=item[3];         			
					var locationdesc = document.createTextNode(item[3]);
					locationdesc.size=7;
					//cell4.appendChild(PARTICULARS);
					cell4.appendChild(locationdesc);
					cell4.appendChild(locationdesc1);
					cell4.align="left";
					mycurrent_row.appendChild(cell4);

					var cell5 = document.createElement("TD");
					var status1=document.createElement("input");
					status1.type="hidden";
					status1.name="TOTAL_AMOUNT";
					status1.id="TOTAL_AMOUNT";
					status1.value=item[4];
					var status = document.createTextNode(item[4]);
					status.size=7;
					//cell5.appendChild(TOTAL_AMOUNT);
					cell5.appendChild(status);
					cell5.appendChild(status1);
					mycurrent_row.appendChild(cell5);
					
					var cell6 = document.createElement("TD");
					var status2=document.createElement("input");
					status2.type="hidden";
					status2.name="TOTAL_AMOUNT1";
					status2.id="TOTAL_AMOUNT1";
					status2.value=item[9];
					var status3 = document.createTextNode(item[4]);
					status2.size=7;
					//cell5.appendChild(TOTAL_AMOUNT);
					cell6.appendChild(status3);
					cell6.appendChild(status2);
					mycurrent_row.appendChild(cell6);
					


					var cell7 = document.createElement("TD");
					cell7.style.textAlign = 'center';
					var	cleared = document.createElement('input');
					cleared.type = "checkbox";
					cleared.id="cleared";
					if(item[8]==0)
					{
						cleared.checked = true;
						cleared.disabled=true;
					}
					else
					{
						cleared.checked = false;
						cleared.disabled=true;
					}
					//var status2 = document.createTextNode(item[7]);
					cell7.appendChild(cleared);
					//cell6.appendChild(status2);
					mycurrent_row.appendChild(cell7);

					var cell8 = document.createElement("TD");
					cell8.style.textAlign = 'center';
					partlycleared = document.createElement('input');
					partlycleared.type = "checkbox";
					if(item[8]>0){
						partlycleared.checked = true;
						partlycleared.disabled=true;
					}
					else
					{
						partlycleared.checked = false;
						partlycleared.disabled=true;
					}
					partlycleared.id="partlycleared";
					//var status3 = document.createTextNode(item[7]);
					//cell7.appendChild(status3);
					cell8.appendChild(partlycleared);
					mycurrent_row.appendChild(cell8);




					var cell9 = document.createElement("TD");
					var remarks1=document.createElement("input");
					remarks1.type="hidden";
					remarks1.name="pending_AMOUNT";
					remarks1.id="pending_AMOUNT";
					remarks1.value=item[8];
					var remarks = document.createTextNode(item[8]);
					remarks.size=7;
					cell9.align="left";

					cell9.appendChild(remarks);
					cell9.appendChild(remarks1);
					mycurrent_row.appendChild(cell9);


					//lll++;
					tbody.appendChild(mycurrent_row);
					seq+=1;  }

			}
			
		}

		
	}


}











