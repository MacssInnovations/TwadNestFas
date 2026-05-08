//alert('test');
var url = "";
var seq=0,k=1;
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
	
	var delimiter = ","; 
	var a = amount.split('.',2);
	var d = a[1];
	var i = parseInt(a[0]);
	if(isNaN(i)) 
	{ return ''; }
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

function load_Grid(path) {
	
	var fin_year = document.getElementById("fin_year").value;
	var CmbFrom_Month = document.getElementById("CmbFrom_Month").value;
	var CmbTo_Month = document.getElementById("CmbTo_Month").value;
    url = path + "/TWAD_MIS_AO_ASSET?Command=All&fin_year=" + fin_year
	+ "&CmbFrom_Month=" + CmbFrom_Month + "&CmbTo_Month=" + CmbTo_Month;
	var req = getTransport();
	req.open("GET", url, true);
	document.getElementById("imgfld").style.visibility = "visible";
	req.onreadystatechange = function() {
		processResponse(req,path);
	};
	req.send(null);

}

function load_Grid_TB(path)
{
	var fin_year = document.getElementById("fin_year").value;
	var CmbFrom_Month = document.getElementById("CmbFrom_Month").value;
	var CmbTo_Month = document.getElementById("CmbTo_Month").value;
    url = path + "/TWAD_MIS_AO_ASSET?Command=TB_Detail&fin_year=" + fin_year
	+ "&CmbFrom_Month=" + CmbFrom_Month + "&CmbTo_Month=" + CmbTo_Month;
	var req = getTransport();
	req.open("GET", url, true);
	document.getElementById("imgfld").style.visibility = "visible";
	req.onreadystatechange = function() {
		processResponse(req,path);
	};
	req.send(null);
}	
	

function processResponse(req,path)
{
	if(req.readyState==4){
		if(req.status==200)
			{
			document.getElementById("imgfld").style.visibility = "hidden";
			var br= req.responseXML.getElementsByTagName("response")[0];
			var cmd=br.getElementsByTagName("command")[0].firstChild.nodeValue; 
			if(cmd=="Load_Grid"){
				var tbody = document.getElementById("tblList");
				for (t = tbody.rows.length - 1; t >= 0; t--) {
					tbody.deleteRow(0);
				}
				var count = br.getElementsByTagName("count");
				c = count.length;
				for ( var j = 0; j < c; j++) {					
					var tbody = document.getElementById("tblList");
                    var tot_cr=0,tot_dr=0,net_tot=0,OB=0;
					var minor_code = br.getElementsByTagName("minor_code")[j].firstChild.nodeValue;
					var minor_desc = br.getElementsByTagName("minor_desc")[j].firstChild.nodeValue;
					var OB_val = parseFloat(br.getElementsByTagName("OB")[j].firstChild.nodeValue);
					var sum_deb = parseFloat(br.getElementsByTagName("sum_deb")[j].firstChild.nodeValue);
					var sum_cre = parseFloat(br.getElementsByTagName("sum_cre")[j].firstChild.nodeValue);
					if(OB_val%1==0){OB=OB_val+".00";}else{OB=OB_val.toFixed(2);}
					if(sum_deb%1==0){tot_dr=sum_deb+".00";}else{tot_dr=sum_deb.toFixed(2);}
					if(sum_cre%1==0){tot_cr=sum_cre+".00";}else{tot_cr=sum_cre.toFixed(2);}
					var fin_net=(sum_deb-sum_cre).toFixed(2);
					OB=CommaFormatted(OB);
					tot_dr=CommaFormatted(tot_dr);
					tot_cr=CommaFormatted(tot_cr);
					fin_net=CommaFormatted(fin_net);
				
					var mycurrent_cat = document.createElement("TR");
					var cell = document.createElement("TD");
					cell.setAttribute("colspan", "3");
					cell.height=5;					   
		            var anc=document.createElement("A");
		            var url="javascript:loadTable('"+minor_code+"','"+minor_desc+"','"+path+"')";
		            anc.href=url;
		        	var Category_ID_code_text = document.createTextNode(minor_desc);
		            anc.appendChild(Category_ID_code_text);
		            cell.appendChild(anc);					
					mycurrent_cat.appendChild(cell);
					
					var cell1 = document.createElement("TD");
					var OB_val_cell = document.createElement("input");
					OB_val_cell.type = "hidden";
					OB_val_cell.name = "OB" + seq;
					cell1.setAttribute("align", "right");
					OB_val_cell.value = OB_val;
					cell1.appendChild(OB_val_cell);
					var OB_val_text = document.createTextNode(OB);
					cell1.appendChild(OB_val_text);
					mycurrent_cat.appendChild(cell1);					
					tbody.appendChild(mycurrent_cat);
					var head_code = br.getElementsByTagName("head_code" + k);
					var len = head_code.length;
					for ( var i = 0; i < len; i++) {
						head_code = br.getElementsByTagName("head_code" + k)[i].firstChild.nodeValue;
						var dr_amt = parseFloat(br.getElementsByTagName("Debit" + k)[i].firstChild.nodeValue);
						var cr_amt = parseFloat(br.getElementsByTagName("Credit" + k)[i].firstChild.nodeValue);
						var NET = parseFloat(br.getElementsByTagName("NET" + k)[i].firstChild.nodeValue);
						var head_desc = br.getElementsByTagName("head_desc" + k)[i].firstChild.nodeValue;
					
					if(dr_amt%1==0){dr=dr_amt+".00";}else{dr=dr_amt.toFixed(2);}
					if(cr_amt%1==0){cr=cr_amt+".00";}else{cr=cr_amt.toFixed(2);}
					if((dr_amt-cr_amt)%1==0){net_amt=(dr_amt-cr_amt)+".00";}else{net_amt=(dr_amt-cr_amt).toFixed(2);}
						dr=CommaFormatted(dr);
					    cr=CommaFormatted(cr);
				        net_amt=CommaFormatted(net_amt);
						var mycurrent_row = document.createElement("TR");
						
						
						var cell1 = document.createElement("TD");
						cell1.height=5;
						var h_code = document.createElement("input");
						h_code.type = "hidden";
						h_code.name = "head_code" + seq;
						h_code.value = head_code;
						cell1.appendChild(h_code);
						var h_code_text = document.createTextNode(head_code);
						cell1.appendChild(h_code_text);					
						mycurrent_row.appendChild(cell1);

						var cell2 = document.createElement("TD");
						cell2.height=5;
						var h_desc = document.createElement("input");
						h_desc.type = "hidden";
						h_desc.name = "head_desc" + seq;
						h_desc.value = head_desc;
						cell2.appendChild(h_desc);
						var h_desc_text = document.createTextNode(head_desc);
						cell2.appendChild(h_desc_text);						
						mycurrent_row.appendChild(cell2);
						
						var cell5 = document.createElement("TD");
						cell5.height=5;
						var net_val = document.createElement("input");
						net_val.type = "hidden";
						net_val.name = "NET" + seq;
						cell5.setAttribute("align", "right");
						net_val.value = NET;
						cell5.appendChild(net_val);
						var net_val_text = document.createTextNode(net_amt);
						cell5.appendChild(net_val_text);	
						mycurrent_row.appendChild(cell5);
						
						var cell6 = document.createElement("TD");
						cell6.height=5;
						mycurrent_row.appendChild(cell6);						
						tbody.appendChild(mycurrent_row);
						seq += 1;
					}
					var mycurrent_cat1 = document.createElement("TR");
					var cell_tot = document.createElement("TD");
					cell_tot.setAttribute("colspan", "2");
					cell_tot.style.color = "red";
					cell_tot.id = "cell_tot" + j;
					var to_text = document.createTextNode("Total : ");
					cell_tot.appendChild(to_text);
					mycurrent_cat1.appendChild(cell_tot);

					var net_tot_val = document.createElement("TD");
					net_tot_val.style.color = "red";
					net_tot_val.setAttribute("align", "right");
					var net_tot_code = document.createElement("input");
					net_tot_code.type = "hidden";
					net_tot_code.name = "SUM_NET";
					net_tot_code.value = sum_deb-sum_cre;
					net_tot_val.appendChild(net_tot_code);
					var net_tot_text = document.createTextNode(fin_net);	
					net_tot_val.appendChild(net_tot_text);
					mycurrent_cat1.appendChild(net_tot_val);
					alert(OB_val+(sum_deb-sum_cre));
					var Grant_amt=(OB_val+(sum_deb-sum_cre)).toFixed(2);	alert("   Grant_amt   >>> "+Grant_amt);
					Grant_amt=CommaFormatted(Grant_amt);
					alert(" >>> "+Grant_amt);
					var summary_val = document.createElement("TD");
					summary_val.style.color = "red";
					summary_val.setAttribute("align", "right");
					var Grant_val = document.createElement("input");
					Grant_val.type = "hidden";
					Grant_val.name = "SUM_NET";
					Grant_val.value = OB_val+( sum_deb-sum_cre);
					summary_val.appendChild(Grant_val);
					var Grant_val_text = document.createTextNode(Grant_amt);	
					summary_val.appendChild(Grant_val_text);
					mycurrent_cat1.appendChild(summary_val);
					tbody.appendChild(mycurrent_cat1);
					k++;
			}
			}
			/*else{
				alert('nodata');
			}
	}*/
}
	}
}