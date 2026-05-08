//Twad 1st jscript /
var service;
var __pagination = 11;
var destid;
var totalblock = 0;

var seq = 0;
var c = 0;
function getTransport() {
	var req = false;
	try {
		req = new ActiveXObject("Msxml2.XMLHTTP");
	} catch (e) {
		try {
			req = new ActiveXObject("Microsoft.XMLHTTP");
		} catch (e2) {
			req = false;
		}
	}
	if (!req && typeof XMLHttpRequest != 'undefined') {
		req = new XMLHttpRequest();
	}
	return req;
}

function CommaFormatted(amount) {
	alert(amount);
	var delimiter = ","; // replace comma if desired
	var a = amount.split('.',2);
	var d = a[1];
	var i = parseInt(a[0]);
	alert("d .... "+d+" .... i ...."+i);
	if(isNaN(i))
	{ return ''; 
	}
	var minus = '';
	if(i < 0) { minus = '-'; }
	i = Math.abs(i);
	var n = new String(i);
	var a = [];
	while(n.length > 3) {
		var nn = n.substr(n.length-3);
		a.unshift(nn);
		n = n.substr(0,n.length-3);
	}
	if(n.length > 0) { a.unshift(n); }
	n = a.join(delimiter);
	if(d.length < 1) { amount = n; }
	else { amount = n + '.' + d; }
	amount = minus + amount;
	return amount;
}

function printFunc(path) {
	//document.getElementById("detail_unit").style.display="block";
	//document.getElementById("detail").style.display="none";
	var fin_year = document.getElementById("fin_year").value;
	var CmbFrom_Month = document.getElementById("CmbFrom_Month").value;
	var CmbTo_Month = document.getElementById("CmbTo_Month").value;
	/*url = path + "/TwadMIS_Report?Command=FIN_YEAR&fin_year=" + fin_year
			+ "&CmbFrom_Month=" + CmbFrom_Month + "&CmbTo_Month=" + CmbTo_Month;*/
	url = path + "/Twad_report_ser?Command=All&fin_year=" + fin_year
	+ "&CmbFrom_Month=" + CmbFrom_Month + "&CmbTo_Month=" + CmbTo_Month;
	alert(url);
	var req = getTransport();
	req.open("GET", url, true);
	req.onreadystatechange = function() {
		processResponse(req,path);
	};
	req.send(null);

}
function comma(val)
{
	val=12345;
	alert(val);
}
function printPDF(path,cmd) {
	alert(path);
	var fin_year = document.getElementById("fin_year").value;
	var CmbFrom_Month = document.getElementById("CmbFrom_Month").value;
	var CmbTo_Month = document.getElementById("CmbTo_Month").value;
	url = path + "/TwadMIS_Report?Command="+cmd+"&fin_year=" + fin_year
			+ "&CmbFrom_Month=" + CmbFrom_Month + "&CmbTo_Month=" + CmbTo_Month;
	alert(url);
	document.frmMIS_Twad.action = url;
	document.frmMIS_Twad.method = "POST";
	document.frmMIS_Twad.submit();

}

function processResponse(req,path) {
	if (req.readyState == 4) {
		if (req.status == 200) {

			var baseResponse = req.responseXML.getElementsByTagName("response")[0];
			//alert(baseResponse);
			var tagCommand = baseResponse.getElementsByTagName("command")[0];
			var command = tagCommand.firstChild.nodeValue;
			var tbody = document.getElementById("tblList");
			var t = 0,k = 1;

			for (t = tbody.rows.length - 1; t >= 0; t--) {
				tbody.deleteRow(0);
			}
			var count = baseResponse.getElementsByTagName("count");
			c = count.length;
			
			if(command=="Major_Wise"){

				var tbody = document.getElementById("tblList");
				

				for (t = tbody.rows.length - 1; t >= 0; t--) {
					tbody.deleteRow(0);
				}
				var count = baseResponse.getElementsByTagName("head_code");
				len= count.length;

				var major_code = baseResponse
				.getElementsByTagName("major_code")[0].firstChild.nodeValue;
				var major_desc = baseResponse
				.getElementsByTagName("major_desc")[0].firstChild.nodeValue;
				var sum_deb = parseFloat(baseResponse.getElementsByTagName("sum_deb")[0].firstChild.nodeValue);
				var sum_cre = parseFloat(baseResponse
						.getElementsByTagName("sum_cre")[0].firstChild.nodeValue);
				
				var mycurrent_row1 = document.createElement("TR");
				var cell1 = document.createElement("TD");
				var major_code_code = document.createElement("input");
				major_code_code.type = "hidden";
				major_code_code.name = "major_code" + seq;
				major_code_code.value = major_code;
				cell1.setAttribute("colspan", "5");
				cell1.style.color = "Red";
				cell1.appendChild(major_code_code);
				var major_code_text = document.createTextNode(major_desc); 	
				cell1.appendChild(major_code_text);
				var btn=document.createElement("input");
				btn.type="button";
				btn.value="PDF";
				btn.setAttribute("onclick","printPDF('"+path+"','"+major_code+"')");
				cell1.appendChild(btn);
				mycurrent_row1.appendChild(cell1);
				tbody.appendChild(mycurrent_row1);

			
					for ( var i = 0; i < len; i++) {
						head_code = baseResponse
								.getElementsByTagName("head_code")[i].firstChild.nodeValue;
						var Debit = baseResponse.getElementsByTagName("Debit")[i].firstChild.nodeValue;
						var Credit = baseResponse.getElementsByTagName("Credit")[i].firstChild.nodeValue;
						var NET = baseResponse.getElementsByTagName("NET")[i].firstChild.nodeValue;
						var head_desc = baseResponse
								.getElementsByTagName("head_desc")[i].firstChild.nodeValue;
						var mycurrent_row = document.createElement("TR");
						var cell1 = document.createElement("TD");
						var cell2 = document.createElement("TD");
						var cell3 = document.createElement("TD");
						var cell4 = document.createElement("TD");
						var cell5 = document.createElement("TD");

						var h_code = document.createElement("input");
						h_code.type = "hidden";
						h_code.name = "head_code" + seq;
						h_code.value = head_code;
						cell1.appendChild(h_code);
						var h_code_text = document.createTextNode(head_code);
						//var h_code_text = document.createElement("label");
						//h_code_text.fontSize=10+"pt";      
						//h_code_text.innerHTML=head_code;
					
						cell1.appendChild(h_code_text);
						mycurrent_row.appendChild(cell1);

						var h_desc = document.createElement("input");
						h_desc.type = "hidden";
						h_desc.name = "head_desc" + seq;
						h_desc.value = head_desc;
						cell2.appendChild(h_desc);
						var h_desc_text = document.createTextNode(head_desc);
						cell2.appendChild(h_desc_text);
						mycurrent_row.appendChild(cell2);

						var debit_val = document.createElement("input");
						debit_val.type = "hidden";
						debit_val.name = "Debit" + seq;
						debit_val.value = parseFloat(Debit);
						cell3.appendChild(debit_val);
						var debit_val_text = document.createTextNode(parseFloat(Debit));
						cell3.appendChild(debit_val_text);
						mycurrent_row.appendChild(cell3);

						var credit_val = document.createElement("input");
						credit_val.type = "hidden";
						credit_val.name = "Credit" + seq;
						credit_val.value = parseFloat(Credit);
						cell4.appendChild(credit_val);
						var credit_val_text = document.createTextNode(parseFloat(Credit));
						cell4.appendChild(credit_val_text);
						mycurrent_row.appendChild(cell4);

						var net_val = document.createElement("input");
						net_val.type = "hidden";
						net_val.name = "NET" + seq;
						net_val.value = parseFloat(Debit) - parseFloat(Credit);
						cell5.appendChild(net_val);
						var net_val_text = document.createTextNode(parseFloat(Debit)
								- parseFloat(Credit));
						cell5.appendChild(net_val_text);
						mycurrent_row.appendChild(cell5);
						tbody.appendChild(mycurrent_row);

						seq += 1;

					}
					var mycurrent_cat1 = document.createElement("TR");

					var cell_tot = document.createElement("TD");
					cell_tot.setAttribute("colspan", "2");
					cell_tot.style.color = "red";
					cell_tot.id = "cell_tot" + j;
					cell_tot.style.backgroundColor = "yellow";
					var to_text = document.createTextNode("Total : ");
					var span = document.createElement('span');
					span.style.height = "7px";
					span.appendChild(to_text);
					cell_tot.appendChild(span);
					mycurrent_cat1.appendChild(cell_tot);

					var debit_tot = document.createElement("TD");
					var to_debit = document.createElement("input");
					to_debit.type = "hidden";
					to_debit.name = "tot_debit";
					debit_tot.style.color = "red";
					debit_tot.style.backgroundColor = "yellow";
					debit_tot.setAttribute("align", "right");
					to_debit.value = parseFloat(sum_deb);
					debit_tot.appendChild(to_debit);
					var to_debit_text = document.createTextNode(parseFloat(sum_deb));
					var span = document.createElement('span');
					span.style.height = "7px";
					span.appendChild(to_debit_text);					
				
					debit_tot.appendChild(span);
					mycurrent_cat1.appendChild(debit_tot);

					var credit_tot = document.createElement("TD");
					var to_credit = document.createElement("input");
					to_credit.type = "hidden";
					to_credit.name = "tot_credit";
					credit_tot.style.color = "red";
					credit_tot.style.backgroundColor = "yellow";
					credit_tot.setAttribute("align", "right");
					to_credit.value = parseFloat(sum_cre);
					credit_tot.appendChild(to_credit);
					var to_credit_text = document.createTextNode(parseFloat(sum_cre));
					var span = document.createElement('span');
					span.style.height = "7px";
					span.appendChild(to_credit_text);
					credit_tot.appendChild(span);
					mycurrent_cat1.appendChild(credit_tot);

					var net_tot_val = document.createElement("TD");
					net_tot_val.style.color = "red";
					net_tot_val.style.backgroundColor = "yellow";
					net_tot_val.setAttribute("align", "right");
					var net_tot = document.createElement("input");
					net_tot.type = "hidden";
					net_tot.name = "SUM_NET";
					net_tot.value = parseFloat(sum_deb)-parseFloat(sum_cre);
					net_tot_val.appendChild(net_tot);
					var net_tot_text = document.createTextNode(parseFloat(sum_deb)-parseFloat(sum_cre));	
					var span = document.createElement('span');
					span.style.height = "7px";
					span.appendChild(net_tot_text);
					net_tot_val.appendChild(span);
					mycurrent_cat1.appendChild(net_tot_val);
					tbody.appendChild(mycurrent_cat1);
				
			
				
			}
			else if (command == "All") {
				var tbody = document.getElementById("tblList");
				

				for (t = tbody.rows.length - 1; t >= 0; t--) {
					tbody.deleteRow(0);
				}
				var count = baseResponse.getElementsByTagName("Category_ID");
				c = count.length;
//alert(c)
				for ( var j = 0; j < c; j++) {
					var tbody = document.getElementById("tblList");

					var Category_ID = baseResponse
							.getElementsByTagName("Category_ID")[j].firstChild.nodeValue;
					var Category_DESC = baseResponse
							.getElementsByTagName("Category_DESC")[j].firstChild.nodeValue;
					var sum_deb = parseFloat(baseResponse.getElementsByTagName("sum_deb")[j].firstChild.nodeValue);
					var sum_cre = parseFloat(baseResponse
							.getElementsByTagName("sum_cre")[j].firstChild.nodeValue);

					var mycurrent_cat = document.createElement("TR");
					var cell = document.createElement("TD");
					// cell.colSpan=5;
					cell.setAttribute("colspan", "5");
					cell.style.color = "Red";
	
				//	cell.style.backgroundColor = "green";
					cell.height=5;
					var Category_ID_code = document.createElement("input");
					Category_ID_code.type = "hidden";
					Category_ID_code.id = "Category_ID" + j;
					Category_ID_code.name = "Category_ID" + j;
					Category_ID_code.value = Category_ID;
					cell.appendChild(Category_ID_code);
					var Category_ID_code_text = document
							.createTextNode(Category_DESC);
				var anc=document.createElement("a");
				var url="javascript:funanc('"+Category_ID+"','"+path+"')";
				anc.href=url;
				anc.appendChild(Category_ID_code_text);
					cell.appendChild(anc);
					mycurrent_cat.appendChild(cell);
					tbody.appendChild(mycurrent_cat);
					var head_code = baseResponse
							.getElementsByTagName("head_code" + k);
					var len = head_code.length;
					//alert("len >>>> "+len+"   k ... "+k);
					for ( var i = 0; i < len; i++) {
						head_code = baseResponse
								.getElementsByTagName("head_code" + k)[i].firstChild.nodeValue;
						
						var Debit = baseResponse.getElementsByTagName("Debit"
								+ k)[i].firstChild.nodeValue;
					
						var Credit = baseResponse.getElementsByTagName("Credit"
								+ k)[i].firstChild.nodeValue;
						var NET = baseResponse.getElementsByTagName("NET" + k)[i].firstChild.nodeValue;
						var head_desc = baseResponse
								.getElementsByTagName("head_desc" + k)[i].firstChild.nodeValue;
						var mycurrent_row = document.createElement("TR");
						var cell1 = document.createElement("TD");
						cell.height=5;
						var cell2 = document.createElement("TD");
						cell.height=5;
						var cell3 = document.createElement("TD");
						cell.height=5;
						var cell4 = document.createElement("TD");
						cell.height=5;
						var cell5 = document.createElement("TD");
						cell.height=5;

						var h_code = document.createElement("input");
						h_code.type = "hidden";
						h_code.name = "head_code" + seq;
						h_code.value = head_code;
						cell1.appendChild(h_code);
						var h_code_text = document.createTextNode(head_code);
						var span = document.createElement('span');
						span.style.height = "7px";
						span.appendChild(h_code_text);						
						cell1.appendChild(span);
					
					
						mycurrent_row.appendChild(cell1);

						var h_desc = document.createElement("input");
						h_desc.type = "hidden";
						h_desc.name = "head_desc" + seq;
						h_desc.value = head_desc;
						cell2.appendChild(h_desc);
						var h_desc_text = document.createTextNode(head_desc);
						var span = document.createElement('span');
						span.style.height = "7px";
						span.appendChild(h_desc_text);
						
						cell2.appendChild(span);
						
						mycurrent_row.appendChild(cell2);

						var debit_val = document.createElement("input");
						debit_val.type = "hidden";
						debit_val.name = "Debit" + seq;
						debit_val.value = Debit;
						cell3.setAttribute("align", "right");
						cell3.appendChild(debit_val);
						var debit_val_text = document.createTextNode(parseFloat(Debit));
						var span = document.createElement('span');
						span.style.height = "7px";
						span.appendChild(debit_val_text);
						
						cell3.appendChild(span);
						mycurrent_row.appendChild(cell3);

						var credit_val = document.createElement("input");
						credit_val.type = "hidden";
						credit_val.name = "Credit" + seq;
						cell4.setAttribute("align", "right");
						credit_val.value = Credit;
						cell4.appendChild(credit_val);
						var credit_val_text = document.createTextNode(parseFloat(Credit));
						var span = document.createElement('span');
						span.style.height = "7px";
						span.appendChild(credit_val_text);
						
						cell4.appendChild(span);
						mycurrent_row.appendChild(cell4);

						var net_val = document.createElement("input");
						net_val.type = "hidden";
						net_val.name = "NET" + seq;
						cell5.setAttribute("align", "right");
						net_val.value = Debit - Credit;
						cell5.appendChild(net_val);
						var net_val_text = document.createTextNode(parseFloat(Debit)
								- parseFloat(Credit));
						var span = document.createElement('span');
						span.style.height = "7px";
						span.appendChild(net_val_text);
						
						cell5.appendChild(span);
						
						mycurrent_row.appendChild(cell5);
						tbody.appendChild(mycurrent_row);

						seq += 1;

					}
					var mycurrent_cat1 = document.createElement("TR");

					var cell_tot = document.createElement("TD");
					cell_tot.setAttribute("colspan", "2");
					cell_tot.style.color = "red";
					cell_tot.id = "cell_tot" + j;
					cell_tot.style.backgroundColor = "yellow";
					var to_text = document.createTextNode("Total : ");
					var span = document.createElement('span');
					span.style.height = "7px";
					span.appendChild(to_text);
					cell_tot.appendChild(span);
					mycurrent_cat1.appendChild(cell_tot);

					var debit_tot = document.createElement("TD");
					var to_debit = document.createElement("input");
					to_debit.type = "hidden";
					to_debit.name = "tot_debit";
					debit_tot.style.color = "red";
					debit_tot.style.backgroundColor = "yellow";
					debit_tot.setAttribute("align", "right");
					to_debit.value = sum_deb;
					debit_tot.appendChild(to_debit);
					var to_debit_text = document.createTextNode(sum_deb);
					var span = document.createElement('span');
					span.style.height = "7px";
					span.appendChild(to_debit_text);					
				
					debit_tot.appendChild(span);
					mycurrent_cat1.appendChild(debit_tot);

					var credit_tot = document.createElement("TD");
					var to_credit = document.createElement("input");
					to_credit.type = "hidden";
					to_credit.name = "tot_credit";
					credit_tot.style.color = "red";
					credit_tot.style.backgroundColor = "yellow";
					credit_tot.setAttribute("align", "right");
					to_credit.value = sum_cre;
					credit_tot.appendChild(to_credit);
					var to_credit_text = document.createTextNode(sum_cre);
					var span = document.createElement('span');
					span.style.height = "7px";
					span.appendChild(to_credit_text);
					credit_tot.appendChild(span);
					mycurrent_cat1.appendChild(credit_tot);

					var net_tot_val = document.createElement("TD");
					net_tot_val.style.color = "red";
					net_tot_val.style.backgroundColor = "yellow";
					net_tot_val.setAttribute("align", "right");
					var net_tot = document.createElement("input");
					net_tot.type = "hidden";
					net_tot.name = "SUM_NET";
					net_tot.value = sum_deb-sum_cre;
					net_tot_val.appendChild(net_tot);
					var net_tot_text = document.createTextNode(sum_deb-sum_cre);	
					var span = document.createElement('span');
					span.style.height = "7px";
					span.appendChild(net_tot_text);
					net_tot_val.appendChild(span);
					mycurrent_cat1.appendChild(net_tot_val);
					tbody.appendChild(mycurrent_cat1);
					k++;

				}

			}
			else if (command == "FIN_YEAR"){
				document.getElementById("detail").style.display="none";
				document.getElementById("detail_unit").style.display="block";
				var tbody = document.getElementById("tblList1");
				for (t = tbody.rows.length - 1; t >= 0; t--) {
					tbody.deleteRow(0);
				}
				
					 Debit = baseResponse
					.getElementsByTagName("Debit");
			var len = Debit.length;
			
			for ( var i = 0; i < len; i++) {
				
				 Credit = baseResponse.getElementsByTagName("Credit")[i].firstChild.nodeValue; 
				 Debit = baseResponse.getElementsByTagName("Debit")[i].firstChild.nodeValue; 
					var year = baseResponse.getElementsByTagName("YEAR")[i].firstChild.nodeValue;
				var month = baseResponse.getElementsByTagName("MONTH")[i].firstChild.nodeValue;
			var mth="";
				if(month=="1")mth="Jan";
				if(month=="2")mth="Feb";
				if(month=="3")mth="Mar";
				if(month=="4")mth="Apr";
				if(month=="5")mth="May";
				if(month=="6")mth="Jun";
				if(month=="7")mth="July";
				if(month=="8")mth="Aug";
				if(month=="9")mth="Sep";
				if(month=="10")mth="Oct";
				if(month=="11")mth="Nov";
				if(month=="12")mth="Dec";
				var NET = baseResponse.getElementsByTagName("NET")[i].firstChild.nodeValue;
				var mycurrent_row = document.createElement("TR");
				var cell1 = document.createElement("TD");
				var cell2 = document.createElement("TD");
				var cell3 = document.createElement("TD");
				var cell4 = document.createElement("TD");
				var cell5 = document.createElement("TD");

				  var anc=document.createElement("A");
		            var url="javascript:MonthWise('"+month+"','"+year+"','"+path+"')";
		            anc.href=url;
		        	var Category_ID_code_text = document
					.createTextNode("EDIT");
		            anc.appendChild(Category_ID_code_text);
		            cell2.appendChild(anc);
		            mycurrent_row.appendChild(cell2);
				
				var year_mnt = document.createElement("input");
				year_mnt.type = "hidden";
				year_mnt.name = "month" + seq;
				year_mnt.value = month+year;
				cell1.appendChild(year_mnt);
				var debit_val_text = document.createTextNode(mth+" / "+year);
				cell1.appendChild(debit_val_text);
				mycurrent_row.appendChild(cell1);
				
				var debit_val = document.createElement("input");
				debit_val.type = "hidden";
				debit_val.name = "Debit" + seq;
				debit_val.value = Debit;
				cell3.appendChild(debit_val);
				var debit_val_text = document.createTextNode(Debit);
				cell3.appendChild(debit_val_text);
				mycurrent_row.appendChild(cell3);

				var credit_val = document.createElement("input");
				credit_val.type = "hidden";
				credit_val.name = "Credit" + seq;
				credit_val.value = Credit;
				cell4.appendChild(credit_val);
				var credit_val_text = document.createTextNode(Credit);
				cell4.appendChild(credit_val_text);
				mycurrent_row.appendChild(cell4);

				var net_val = document.createElement("input");
				net_val.type = "hidden";
				net_val.name = "NET" + seq;
				net_val.value = Debit - Credit;
				cell5.appendChild(net_val);
				var net_val_text = document.createTextNode(Debit
						- Credit);
				cell5.appendChild(net_val_text);
				mycurrent_row.appendChild(cell5);
				tbody.appendChild(mycurrent_row);

				seq += 1;
					
			}
				}
			else if (command == "catagory_wise")
			{
				
				for ( var j = 0; j < c; j++) {
					var tbody = document.getElementById("tblList");

					var Category_ID = baseResponse
							.getElementsByTagName("cat_id")[j].firstChild.nodeValue;alert(Category_ID);
					var Category_DESC = baseResponse
							.getElementsByTagName("cat_desc")[j].firstChild.nodeValue;alert(Category_DESC);
					var sum_deb = baseResponse.getElementsByTagName("sum_deb")[j].firstChild.nodeValue;
					var sum_cre = baseResponse
							.getElementsByTagName("sum_cre")[j].firstChild.nodeValue;
					var sum_net = baseResponse.getElementsByTagName("sum_net")[j].firstChild.nodeValue;

					var mycurrent_cat1 = document.createElement("TR");
					var cell_cat = document.createElement("TD");
			      //  cell.colSpan=5;
					cell_cat.setAttribute("colspan", "5");
					cell_cat.style.color = "Red";
					// cell.setAttribute("style.font-size","12mm");
					cell_cat.style.backgroundColor = "cyan";
					var Category_ID_code = document.createElement("input");
					Category_ID_code.type = "hidden";
					Category_ID_code.name = "Category_ID" + j;
					Category_ID_code.value = Category_ID;
					cell_cat.appendChild(Category_ID_code);
					mycurrent_cat1.appendChild(cell_cat);
					tbody.appendChild(mycurrent_cat1);
					
					var Credit = baseResponse
					.getElementsByTagName("Credit" + k);
			var len = Credit.length;
			for ( var i = 0; i < len; i++) {
			
				var head_code = baseResponse
				.getElementsByTagName("head_code" + k)[i].firstChild.nodeValue;
		var Debit = baseResponse.getElementsByTagName("Debit"
				+ k)[i].firstChild.nodeValue;
		 Credit = baseResponse.getElementsByTagName("Credit"
				+ k)[i].firstChild.nodeValue;
		var NET = baseResponse.getElementsByTagName("NET" + k)[i].firstChild.nodeValue;
		var head_desc = baseResponse
				.getElementsByTagName("head_desc" + k)[i].firstChild.nodeValue;
			 
				var mycurrent_row = document.createElement("TR");
				var cell1 = document.createElement("TD");
				var cell2 = document.createElement("TD");
				var cell3 = document.createElement("TD");
				var cell4 = document.createElement("TD");
				var cell5 = document.createElement("TD");
		
				var h_code = document.createElement("input");
				h_code.type = "hidden";
				h_code.name = "head_code" + seq;
				h_code.value = head_code;
				cell1.appendChild(h_code);
				var h_code_text = document.createTextNode(head_code);
				cell1.appendChild(h_code_text);
				mycurrent_row.appendChild(cell1);

				var h_desc = document.createElement("input");
				h_desc.type = "hidden";
				h_desc.name = "head_desc" + seq;
				h_desc.value = head_desc;
				cell2.appendChild(h_desc);
				var h_desc_text = document.createTextNode(head_desc);
				cell2.appendChild(h_desc_text);
				mycurrent_row.appendChild(cell2);
				
				var debit_val = document.createElement("input");
				debit_val.type = "hidden";
				debit_val.name = "Debit" + seq;
				debit_val.value = Debit;
				cell3.appendChild(debit_val);
				var debit_val_text = document.createTextNode(Debit);
				cell3.appendChild(debit_val_text);
				mycurrent_row.appendChild(cell3);

				var credit_val = document.createElement("input");
				credit_val.type = "hidden";
				credit_val.name = "Credit" + seq;
				credit_val.value = Credit;
				cell4.appendChild(credit_val);
				var credit_val_text = document.createTextNode(Credit);
				cell4.appendChild(credit_val_text);
				mycurrent_row.appendChild(cell4);

				var net_val = document.createElement("input");
				net_val.type = "hidden";
				net_val.name = "NET" + seq;
				net_val.value = Debit - Credit;
				cell5.appendChild(net_val);
				var net_val_text = document.createTextNode(Debit
						- Credit);
				cell5.appendChild(net_val_text);
				mycurrent_row.appendChild(cell5);
				tbody.appendChild(mycurrent_row);

				seq += 1;
				

			}
			var mycurrent_cat = document.createElement("TR");

			var cell_tot = document.createElement("TD");
			cell_tot.setAttribute("colspan", "2");
			cell_tot.style.color = "red";
			cell.style.backgroundColor = "yellow";
			var to_text = document.createTextNode("Total : ");
			cell_tot.appendChild(to_text);
			mycurrent_cat.appendChild(cell_tot);

			var debit_tot = document.createElement("TD");
			var to_debit = document.createElement("input");
			to_debit.type = "hidden";
			to_debit.name = "tot_debit";
			to_debit.value = sum_deb;
			debit_tot.appendChild(to_debit);
			var to_debit_text = document.createTextNode(sum_deb);
			debit_tot.appendChild(to_debit_text);
			mycurrent_cat.appendChild(debit_tot);

			var credit_tot = document.createElement("TD");
			var to_credit = document.createElement("input");
			to_credit.type = "hidden";
			to_credit.name = "tot_credit";
			to_credit.value = sum_cre;
			credit_tot.appendChild(to_credit);
			var to_credit_text = document.createTextNode(sum_cre);
			credit_tot.appendChild(to_credit_text);
			mycurrent_cat.appendChild(credit_tot);

			var net_tot_val = document.createElement("TD");
			var net_tot = document.createElement("input");
			net_tot.type = "hidden";
			net_tot.name = "SUM_NET";
			net_tot.value = sum_deb-sum_cre;
			net_tot_val.appendChild(net_tot);
			var net_tot_text = document.createTextNode(sum_deb-sum_cre);						
			net_tot_val.appendChild(net_tot_text);
			mycurrent_cat.appendChild(net_tot_val);
			tbody.appendChild(mycurrent_cat);
			k++;
				
				}
			}
			else if (command == "All_TEST") {
				alert(c+"  dgs  >>>   "+command);
				for ( var j = 0; j < c; j++) {
					var tbody = document.getElementById("tblList1");

					var Category_ID = baseResponse
							.getElementsByTagName("Category_ID")[j].firstChild.nodeValue;
					var Category_DESC = baseResponse
							.getElementsByTagName("Category_DESC")[j].firstChild.nodeValue;
				
					var mycurrent_cat = document.createElement("TR");
					var cell = document.createElement("TD");
			      //  cell.colSpan=5;
					cell.setAttribute("colspan", "5");
					cell.style.color = "Red";
					// cell.setAttribute("style.font-size","12mm");
					cell.style.backgroundColor = "cyan";
					/*var Category_ID_code = document.createElement("input");
					Category_ID_code.type = "hidden";
					Category_ID_code.name = "Category_ID" + j;
					Category_ID_code.value = Category_ID;
					cell.appendChild(Category_ID_code);*/
					   
			            var anc=document.createElement("A");
			            var url="javascript:loadTable('"+Category_ID+"','"+Category_DESC+"','"+path+"')";
			            anc.href=url;
			        	var Category_ID_code_text = document
						.createTextNode(Category_DESC);
			            anc.appendChild(Category_ID_code_text);
			            cell.appendChild(anc);
					mycurrent_cat.appendChild(cell);
					tbody.appendChild(mycurrent_cat);
					var Credit = baseResponse
							.getElementsByTagName("Credit" + k);
					var len = Credit.length;
					for ( var i = 0; i < len; i++) {
					
						var Debit = baseResponse.getElementsByTagName("Debit"
								+ k)[i].firstChild.nodeValue;
						 Credit = baseResponse.getElementsByTagName("Credit"
								+ k)[i].firstChild.nodeValue;
						var NET = baseResponse.getElementsByTagName("NET" + k)[i].firstChild.nodeValue;
					 
						var mycurrent_row = document.createElement("TR");
						var cell3 = document.createElement("TD");
						var cell4 = document.createElement("TD");
						var cell5 = document.createElement("TD");
				

						var debit_val = document.createElement("input");
						debit_val.type = "hidden";
						debit_val.name = "Debit" + seq;
						debit_val.value = Debit;
						cell3.appendChild(debit_val);
						var debit_val_text = document.createTextNode(Debit);
						cell3.appendChild(debit_val_text);
						mycurrent_row.appendChild(cell3);

						var credit_val = document.createElement("input");
						credit_val.type = "hidden";
						credit_val.name = "Credit" + seq;
						credit_val.value = Credit;
						cell4.appendChild(credit_val);
						var credit_val_text = document.createTextNode(Credit);
						cell4.appendChild(credit_val_text);
						mycurrent_row.appendChild(cell4);

						var net_val = document.createElement("input");
						net_val.type = "hidden";
						net_val.name = "NET" + seq;
						net_val.value = Debit - Credit;
						cell5.appendChild(net_val);
						var net_val_text = document.createTextNode(Debit
								- Credit);
						cell5.appendChild(net_val_text);
						mycurrent_row.appendChild(cell5);
						tbody.appendChild(mycurrent_row);

						seq += 1;
						

					}
					k++;
			}

		}
	}
}
}
function divSel() {
	if (document.frmMIS_Twad.chk.checked == true) {
			document.frmMIS_Twad.chk1.checked=false;
		document.getElementById("td_division").style.display = "block";
		document.getElementById("td_divisionX").style.display = "block";
	}
	if (document.frmMIS_Twad.chk.checked == false) {
		document.getElementById("td_division").style.display = "none";
		document.getElementById("td_divisionX").style.display = "none";
	}
}
function headGrid() {
	if (document.getElementById("detail_unit").style.display == "none") {

		document.getElementById("detail_unit").style.display = "block";
		document.getElementById("back").style.display = "block";
		document.getElementById("onsubmit1").style.display = "none";
		document.getElementById("detail").style.display = "none";

	}
	//alert('test');
	// AccountingUnitID("LIST_ALL_UNITS");
	/*
	 * else if(document.getElementById("detail").style.display=="none"){
	 * 
	 * document.getElementById("detail").style.display="block";
	 * document.getElementById("back").style.display="none";
	 * document.getElementById("onsubmit1").style.display="block";
	 * document.getElementById("detail_unit").style.display="none"; }
	 */
}

function back_fun() {
	if (document.getElementById("detail_unit").style.display == "block") {

		document.getElementById("detail_unit").style.display = "none";
		document.getElementById("detail").style.display = "block";
		document.getElementById("back").style.display = "none";
		document.getElementById("onsubmit1").style.display = "block";
	}
}

function AccountingUnitID(COMMAND) {
	//alert("dfhh");
	command_for_office = COMMAND;
	var url = "../../../../../Load_Accounting_Unit_ID.kv?COMMAND=" + COMMAND;
	//alert("command_for_office&&&&&&&&" + url + "ssssss" + command_for_office);
	var req = getTransport();
	req.open("GET", url, true);
	req.onreadystatechange = function() {
		handle_AccountingUnitID(req);
	}
	req.send(null);

}

function handle_AccountingUnitID(req) {

	if (req.readyState == 4) {

		if (req.status == 200) {

			var baseresponse = req.responseXML.getElementsByTagName("response")[0];
			var flag = baseresponse.getElementsByTagName("flag")[0].firstChild.nodeValue;
			if (flag == "success") {
				var tbody = document.getElementById("tblList1");
				var t = 0,k = 1;

				for (t = tbody.rows.length - 1; t >= 0; t--) {
					tbody.deleteRow(0);
				}
				var option_count = baseresponse.getElementsByTagName("option");
				var root = null;
				//alert(option_count.length);
				seq = 1;
				for ( var i = 0; i < option_count.length; i++) {
					var tbody = document.getElementById("tblList1");
					root = baseresponse.getElementsByTagName("option")[i];
					var accounting_unit_id = root
							.getElementsByTagName("accounting_unit_id")[0].firstChild.nodeValue;

					var accounting_unit_name = root
							.getElementsByTagName("accounting_unit_name")[0].firstChild.nodeValue;
					var mycurrent_row = document.createElement("TR");
					var cell1 = document.createElement("TD");
					var cell2 = document.createElement("TD");

					var accounting_unit_id_code = document
							.createElement("input");
					accounting_unit_id_code.type = "hidden";
					accounting_unit_id_code.name = "accounting_unit_id" + seq;
					accounting_unit_id_code.value = accounting_unit_id;
					cell1.appendChild(accounting_unit_id_code);
					var accounting_unit_id_text = document
							.createTextNode(accounting_unit_id);
					cell1.appendChild(accounting_unit_id_text);
					mycurrent_row.appendChild(cell1);

					var accounting_unit_name_desc = document
							.createElement("input");
					accounting_unit_name_desc.type = "hidden";
					accounting_unit_name_desc.name = "accounting_unit_name"
							+ seq;
					accounting_unit_name_desc.value = accounting_unit_name;
					cell2.appendChild(accounting_unit_name_desc);
					var accounting_unit_name_text = document
							.createTextNode(accounting_unit_name);
					cell2.appendChild(accounting_unit_name_text);
					mycurrent_row.appendChild(cell2);
					tbody.appendChild(mycurrent_row);
					seq++;

				}
			}

		}

	}
}

function unitAll(path)
{
if(document.frmMIS_Twad.chk1.checked==true){
	document.frmMIS_Twad.chk.checked=false;
	document.getElementById("td_division").style.display = "none";
	document.getElementById("td_divisionX").style.display = "none";
}
	var fin_year = document.getElementById("fin_year").value;
var CmbFrom_Month = document.getElementById("CmbFrom_Month").value;
var CmbTo_Month = document.getElementById("CmbTo_Month").value;

url = path + "/TwadMIS_Report?Command=unitAll&fin_year=" + fin_year
		+ "&CmbFrom_Month=" + CmbFrom_Month + "&CmbTo_Month=" + CmbTo_Month;
	

var req = getTransport();
req.open("GET", url, true);
req.onreadystatechange = function() {
	processunit(req,path);
};
req.send(null);
	}
function unitWise(unit, path) {
	
	var fin_year = document.getElementById("fin_year").value;
	var CmbFrom_Month = document.getElementById("CmbFrom_Month").value;
	var CmbTo_Month = document.getElementById("CmbTo_Month").value;
	//var cmbAcc_UnitCode = document.getElementById("cmbAcc_UnitCode").value;
	url = path + "/TwadMIS_Report?Command=unitwise&fin_year=" + fin_year
			+ "&CmbFrom_Month=" + CmbFrom_Month + "&CmbTo_Month=" + CmbTo_Month
			+ "&unit=" + unit;
	
	var req = getTransport();
	req.open("GET", url, true);
	req.onreadystatechange = function() {
		processunit(req,path);
	};
	req.send(null);

}

function processunit(req,path) {
	if (req.readyState == 4) {
		if (req.status == 200) {

			var baseResponse = req.responseXML.getElementsByTagName("response")[0];
			alert(baseResponse+" == "+req.responseText+" >>> ");
			var tagCommand = baseResponse.getElementsByTagName("command")[0];
			var command = tagCommand.firstChild.nodeValue;
			alert(command);
			var tbody = document.getElementById("tblList");
			var t = 0,k = 1;

			for (t = tbody.rows.length - 1; t >= 0; t--) {
				tbody.deleteRow(0);
			}
		

			if (command == "All") {
				var count = baseResponse.getElementsByTagName("count");
				c = count.length;
				alert(c);
				for ( var j = 0; j < c; j++) {
					var tbody = document.getElementById("tblList");

					var Unit_ID = baseResponse.getElementsByTagName("unit_id")[j].firstChild.nodeValue;
					var UNIt_DESC = baseResponse
							.getElementsByTagName("unit_name")[j].firstChild.nodeValue;
					var sum_deb = baseResponse.getElementsByTagName("sum_deb")[j].firstChild.nodeValue;
					var sum_cre = baseResponse
							.getElementsByTagName("sum_cre")[j].firstChild.nodeValue;
					var sum_net = baseResponse
					.getElementsByTagName("sum_net")[j].firstChild.nodeValue;

alert(UNIt_DESC);
					var mycurrent_cat = document.createElement("TR");
					var cell = document.createElement("TD");
					// cell.colSpan=5;
					cell.setAttribute("colspan", "5");
					cell.style.color = "Red";
					// cell.setAttribute("style.font-size","12mm");
					cell.style.backgroundColor = "cyan";
					var Unit_ID_code = document.createElement("input");
					Unit_ID_code.type = "hidden";
					Unit_ID_code.name = "Category_ID" + j;
					Unit_ID_code.value = Unit_ID;
					cell.appendChild(Unit_ID_code);
					var Unit_ID_code_text = document.createTextNode(UNIt_DESC);

					cell.appendChild(Unit_ID_code_text);
					mycurrent_cat.appendChild(cell);
					tbody.appendChild(mycurrent_cat);
					var head_code = baseResponse
							.getElementsByTagName("head_code" + k);
					var len = head_code.length;
					alert(len +"... "+k);
					for ( var i = 0; i < len; i++) {
						head_code = baseResponse
								.getElementsByTagName("head_code" + k)[i].firstChild.nodeValue;
						var Debit = baseResponse.getElementsByTagName("Debit"
								+ k)[i].firstChild.nodeValue;
						var Credit = baseResponse.getElementsByTagName("Credit"
								+ k)[i].firstChild.nodeValue;
						var NET = baseResponse.getElementsByTagName("NET" + k)[i].firstChild.nodeValue;
						var head_desc = baseResponse
								.getElementsByTagName("head_desc" + k)[i].firstChild.nodeValue;
						var mycurrent_row = document.createElement("TR");
						var cell1 = document.createElement("TD");
						var cell2 = document.createElement("TD");
						var cell3 = document.createElement("TD");
						var cell4 = document.createElement("TD");
						var cell5 = document.createElement("TD");

						var h_code = document.createElement("input");
						h_code.type = "hidden";
						h_code.name = "head_code" + seq;
						h_code.value = head_code;
						cell1.appendChild(h_code);
						var h_code_text = document.createTextNode(head_code);
						cell1.appendChild(h_code_text);
						mycurrent_row.appendChild(cell1);

						var h_desc = document.createElement("input");
						h_desc.type = "hidden";
						h_desc.name = "head_desc" + seq;
						h_desc.value = head_desc;
						cell2.appendChild(h_desc);
						var h_desc_text = document.createTextNode(head_desc);
						cell2.appendChild(h_desc_text);
						mycurrent_row.appendChild(cell2);

						var debit_val = document.createElement("input");
						debit_val.type = "hidden";
						debit_val.name = "Debit" + seq;
						debit_val.value = Debit;
						cell3.appendChild(debit_val);
						var debit_val_text = document.createTextNode(Debit);
						cell3.appendChild(debit_val_text);
						mycurrent_row.appendChild(cell3);

						var credit_val = document.createElement("input");
						credit_val.type = "hidden";
						credit_val.name = "Credit" + seq;
						credit_val.value = Credit;
						cell4.appendChild(credit_val);
						var credit_val_text = document.createTextNode(Credit);
						cell4.appendChild(credit_val_text);
						mycurrent_row.appendChild(cell4);

						var net_val = document.createElement("input");
						net_val.type = "hidden";
						net_val.name = "NET" + seq;
						net_val.value = Debit - Credit;
						cell5.appendChild(net_val);
						var net_val_text = document.createTextNode(Debit
								- Credit);
						cell5.appendChild(net_val_text);
						mycurrent_row.appendChild(cell5);
						tbody.appendChild(mycurrent_row);

						seq += 1;

					}
					
					var mycurrent_cat = document.createElement("TR");

					var cell_tot = document.createElement("TD");
					cell_tot.setAttribute("colspan", "2");
					cell_tot.style.color = "red";
					cell.style.backgroundColor = "yellow";
					var to_text = document.createTextNode("Total : ");
					cell_tot.appendChild(to_text);
					mycurrent_cat.appendChild(cell_tot);

					var debit_tot = document.createElement("TD");
					var to_debit = document.createElement("input");
					to_debit.type = "hidden";
					to_debit.name = "tot_debit";
					to_debit.value = sum_deb;
					debit_tot.appendChild(to_debit);
					var to_debit_text = document.createTextNode(sum_deb);
					debit_tot.appendChild(to_debit_text);
					mycurrent_cat.appendChild(debit_tot);

					var credit_tot = document.createElement("TD");
					var to_credit = document.createElement("input");
					to_credit.type = "hidden";
					to_credit.name = "tot_credit";
					to_credit.value = sum_cre;
					credit_tot.appendChild(to_credit);
					var to_credit_text = document.createTextNode(sum_cre);
					credit_tot.appendChild(to_credit_text);
					mycurrent_cat.appendChild(credit_tot);

					var net_tot_val = document.createElement("TD");
					var net_tot = document.createElement("input");
					net_tot.type = "hidden";
					net_tot.name = "SUM_NET";
					net_tot.value = sum_deb-sum_cre;
					net_tot_val.appendChild(net_tot);
					var net_tot_text = document.createTextNode(sum_deb-sum_cre);						
					net_tot_val.appendChild(net_tot_text);
					mycurrent_cat.appendChild(net_tot_val);
					tbody.appendChild(mycurrent_cat);
					k++;

				}

			}  if (command == "unitwise") {
				head_code = baseResponse.getElementsByTagName("head_code");
				len = head_code.length;//alert(len);
				for ( var i = 0; i < len; i++) {
					head_code = baseResponse.getElementsByTagName("head_code")[i].firstChild.nodeValue;
					Debit = baseResponse.getElementsByTagName("Debit")[i].firstChild.nodeValue;
					Credit = baseResponse.getElementsByTagName("Credit")[i].firstChild.nodeValue;
					NET = baseResponse.getElementsByTagName("NET")[i].firstChild.nodeValue;
					head_desc = baseResponse.getElementsByTagName("head_desc")[i].firstChild.nodeValue;
					mycurrent_row1 = document.createElement("TR");
					cell1x = document.createElement("TD");
					cell2x = document.createElement("TD");
					cell3x = document.createElement("TD");
					cell4x = document.createElement("TD");
					cell5x = document.createElement("TD");

					h_code = document.createElement("input");
					h_code.type = "hidden";
					h_code.name = "head_code" + seq;
					h_code.value = head_code;
					cell1x.appendChild(h_code);
					h_code_text = document.createTextNode(head_code);
					
				
					cell1x.appendChild(h_code_text);
					mycurrent_row1.appendChild(cell1x);

					h_desc = document.createElement("input");
					h_desc.type = "hidden";
					h_desc.name = "head_desc" + seq;
					h_desc.value = head_desc;
					cell2x.appendChild(h_desc);
					h_desc_text = document.createTextNode(head_desc);
					cell2x.appendChild(h_desc_text);
					mycurrent_row1.appendChild(cell2x);

					debit_val = document.createElement("input");
					debit_val.type = "hidden";
					debit_val.name = "Debit" + seq;
					debit_val.value = Debit;
					cell3x.appendChild(debit_val);
					debit_val_text = document.createTextNode(Debit);
					cell3x.appendChild(debit_val_text);
					mycurrent_row1.appendChild(cell3x);

					credit_val = document.createElement("input");
					credit_val.type = "hidden";
					credit_val.name = "Credit" + seq;
					credit_val.value = Credit;
					cell4x.appendChild(credit_val);
					credit_val_text = document.createTextNode(Credit);
					cell4x.appendChild(credit_val_text);
					mycurrent_row1.appendChild(cell4x);

					net_val = document.createElement("input");
					net_val.type = "hidden";
					net_val.name = "NET" + seq;
					net_val.value = Debit - Credit;
					cell5x.appendChild(net_val);
					net_val_text = document.createTextNode(Debit - Credit);
					cell5x.appendChild(net_val_text);
					mycurrent_row1.appendChild(cell5x);
					tbody.appendChild(mycurrent_row1);

					seq += 1;

				}
			}

		}
	}
}
function loadTable(id,desc,path)
{
	alert(id+path);
	document.getElementById("detail_unit").style.display="none";
	document.getElementById("detail").style.display="block";
	var fin_year = document.getElementById("fin_year").value;
	var CmbFrom_Month = document.getElementById("CmbFrom_Month").value;
	var CmbTo_Month = document.getElementById("CmbTo_Month").value;
	url = path + "/TwadMIS_Report?Command=catagory_wise&fin_year=" + fin_year
			+ "&CmbFrom_Month=" + CmbFrom_Month + "&CmbTo_Month=" + CmbTo_Month+"&cat_id="+id+"&cat_desc="+desc;
	alert(url);
	var req = getTransport();
	req.open("GET", url, true);
	req.onreadystatechange = function() {
		processResponse(req,path);
	};
	req.send(null);
	}


function MonthWise(M,Y,path){
	alert(M+Y+path);
	
}
function funanc(id,path)
{
	alert(id+path);
	var fin_year = document.getElementById("fin_year").value;
	var CmbFrom_Month = document.getElementById("CmbFrom_Month").value;
	var CmbTo_Month = document.getElementById("CmbTo_Month").value;
	
	url = path + "/Twad_report_ser?Command=Major_Wise&fin_year=" + fin_year
	+ "&CmbFrom_Month=" + CmbFrom_Month + "&CmbTo_Month=" + CmbTo_Month+"&Major=" +id;
	alert(url);
	var req = getTransport();
	req.open("GET", url, true);
	req.onreadystatechange = function() {
		processResponse(req,path);
	};
	req.send(null);
}
