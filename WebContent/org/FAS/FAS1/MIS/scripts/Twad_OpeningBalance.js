//alert('tets');
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

function Load_Function(path)
{
	var fin_year=document.getElementById("fin_year").value;
	  url = path + "/Twad_OpeningBalance?Command=viewGrid&fin_year=" + fin_year;
		var req = getTransport();
		req.open("GET", url, true);
		req.onreadystatechange = function() {
			if(req.readyState==4){
				if(req.status==200)
					{
					processResponse(req.responseXML);
					}
			}
		};
		req.send(null);
}

 function processResponse(rXml)
{
	
var baseResponse=rXml.getElementsByTagName("response")[0];
var command=baseResponse.getElementsByTagName("command")[0].firstChild.nodeValue;
var flag=baseResponse.getElementsByTagName("flag")[0].firstChild.nodeValue;

if(command=="viewGrid")
      {
	if(flag=="Success")
				     {
		var tbody=document.getElementById("tblList");
		 var t = 0,k = 1;

 			for (t = tbody.rows.length - 1; t >= 0; t--) {
 				tbody.deleteRow(0);
 			}
				var MINOR_ID=baseResponse.getElementsByTagName("MINOR_ID");
				var len=MINOR_ID.length;
seq=1;
				for(var i=0;i<len;i++)
					{
					MINOR_ID=parseFloat(baseResponse.getElementsByTagName("MINOR_ID")[i].firstChild.nodeValue);
				var	Addition=parseFloat(baseResponse.getElementsByTagName("Addition")[i].firstChild.nodeValue);
				var	Deletion=parseFloat(baseResponse.getElementsByTagName("Deletion")[i].firstChild.nodeValue);
				var	Depreciation=parseFloat(baseResponse.getElementsByTagName("Depreciation")[i].firstChild.nodeValue);
				var	Depreciation_YY=parseFloat(baseResponse.getElementsByTagName("Depreciation_YY")[i].firstChild.nodeValue);
				var	Discarder_Asset=parseFloat(baseResponse.getElementsByTagName("Discarder_Asset")[i].firstChild.nodeValue);
				var	others=parseFloat(baseResponse.getElementsByTagName("others")[i].firstChild.nodeValue);
				var	Grd_TOT=parseFloat(baseResponse.getElementsByTagName("Grd_TOT")[i].firstChild.nodeValue);
				var	Dep_upto_Date=parseFloat(baseResponse.getElementsByTagName("Dep_upto_Date")[i].firstChild.nodeValue);
				var	Dis_Asset=parseFloat(baseResponse.getElementsByTagName("Dis_Asset")[i].firstChild.nodeValue);
				var	App_Net=parseFloat(baseResponse.getElementsByTagName("App_Net")[i].firstChild.nodeValue);
				var	Minor_Dsec=baseResponse.getElementsByTagName("Minor_Dsec")[i].firstChild.nodeValue;alert(Minor_Dsec);
				var tbody=document.getElementById("tblList");
				var App_Grd=0,App_YY=0;
				var tot=Grd_TOT+Addition;
				var Grd_Total=tot-Deletion;
				var Net_Depreciation= Depreciation-Depreciation_YY;
				var Depreciaton_Cost=Dep_upto_Date+Net_Depreciation;
				var Depreciation_upto=Grd_Total-Depreciaton_Cost;
				var cur_ROW=document.createElement("TR");
				
				var cell_sno=document.createElement("TD");
				var cell_sno_val = document.createElement("input");
				cell_sno_val.type = "hidden";
				cell_sno_val.value = seq;
				var seq_Text= document.createTextNode(seq);
				cell_sno.appendChild(cell_sno_val);
				cell_sno.appendChild(seq_Text);
				cur_ROW.appendChild(cell_sno);
				
				var cell1=document.createElement("TD");
				var MINOR_ID_val = document.createElement("input");
				MINOR_ID_val.type = "hidden";
				MINOR_ID_val.id = "MINOR_ID_val" + seq;
				MINOR_ID_val.name = "MINOR_ID_val" + seq;
				MINOR_ID_val.value = MINOR_ID;
				var MINOR_ID_Text= document.createTextNode(Minor_Dsec);
				cell1.appendChild(MINOR_ID_val);
				cell1.appendChild(MINOR_ID_Text);
				cur_ROW.appendChild(cell1);
				
				var cell2=document.createElement("TD");
				var Grd_TOT_val = document.createElement("input");
				Grd_TOT_val.type = "hidden";
				Grd_TOT_val.id = "Grd_TOT" + seq;
				Grd_TOT_val.name = "Grd_TOT" + seq;
				Grd_TOT_val.value = Grd_TOT;
				var Grd_TOT_Text= document.createTextNode(Grd_TOT);
				cell2.appendChild(Grd_TOT_val);
				cell2.appendChild(Grd_TOT_Text);
				cur_ROW.appendChild(cell2);
				
				var cell3=document.createElement("TD");
				var Addition_val = document.createElement("input");
				Addition_val.type = "hidden";
				Addition_val.id = "Addition" + seq;
				Addition_val.name = "Addition" + seq;
				Addition_val.value = Addition;
				var Addition_Text= document.createTextNode(Addition);
				cell3.appendChild(Addition_val);
				cell3.appendChild(Addition_Text);
				cur_ROW.appendChild(cell3);
				
				var cell4=document.createElement("TD");
				var TOT_val = document.createElement("input");
				TOT_val.type = "hidden";
				TOT_val.id = "TOT" + seq;
				TOT_val.name = "TOT" + seq;
				TOT_val.value = tot;
				var TOT_val_Text= document.createTextNode(tot);
				cell4.appendChild(TOT_val);
				cell4.appendChild(TOT_val_Text);
				cur_ROW.appendChild(cell4);
				
				var cell4=document.createElement("TD");
				var Deletion_val = document.createElement("input");
				Deletion_val.type = "hidden";
				Deletion_val.id = "Deletion" + seq;
				Deletion_val.name = "Deletion" + seq;
				Deletion_val.value = Deletion;
				var Deletion_Text= document.createTextNode(Deletion);
				cell4.appendChild(Deletion_val);
				cell4.appendChild(Deletion_Text);
				cur_ROW.appendChild(cell4);
				
				var cell5=document.createElement("TD");
				var Grand_val = document.createElement("input");
				Grand_val.type = "hidden";
				Grand_val.id = "Grd_tot" + seq;
				Grand_val.name = "Grd_tot" + seq;
				Grand_val.value = Grd_Total;
				var Grand_val_Text= document.createTextNode(Grd_Total);
				cell5.appendChild(Grand_val);
				cell5.appendChild(Grand_val_Text);
				cur_ROW.appendChild(cell5);
				
				var cell6=document.createElement("TD");
				var Dep_upto_Date_val = document.createElement("input");
				Dep_upto_Date_val.type = "hidden";
				Dep_upto_Date_val.id = "Dep_upto_Date" + seq;
				Dep_upto_Date_val.name = "Dep_upto_Date" + seq;
				Dep_upto_Date_val.value = Dep_upto_Date;
				var Dep_upto_Date_val_Text= document.createTextNode(Dep_upto_Date);
				cell6.appendChild(Dep_upto_Date_val_Text);
				cell6.appendChild(Dep_upto_Date_val);
				cur_ROW.appendChild(cell6);
				
				var cell7=document.createElement("TD");
				var Depreciation_val = document.createElement("input");
				Depreciation_val.type = "hidden";
				Depreciation_val.id = "Depreciation" + seq;
				Depreciation_val.name = "Depreciation" + seq;
				Depreciation_val.value = Depreciation;
				var Depreciation_val_Text= document.createTextNode(Depreciation);
				cell7.appendChild(Depreciation_val_Text);
				cell7.appendChild(Depreciation_val);
				cur_ROW.appendChild(cell7);
				
				var cell7a=document.createElement("TD");
				var Dep_YY_val = document.createElement("input");
				Dep_YY_val.type = "hidden";
				Dep_YY_val.id = "Dep_YY" + seq;
				Dep_YY_val.name = "Dep_YY" + seq;
				Dep_YY_val.value = Depreciation_YY;
				var Dep_YY_Text= document.createTextNode(Depreciation_YY);
				cell7a.appendChild(Dep_YY_Text);
				cell7a.appendChild(Dep_YY_val);
				cur_ROW.appendChild(cell7a);
				
				var cell9=document.createElement("TD");
				var DepUpto_val = document.createElement("input");
				DepUpto_val.type = "hidden";
				DepUpto_val.id = "NetDep_val" + seq;
				DepUpto_val.name = "NetDep_val" + seq;
				DepUpto_val.value = Net_Depreciation;
				var DepUpto_val_Text= document.createTextNode(Net_Depreciation);
				cell9.appendChild(DepUpto_val_Text);
				cell9.appendChild(DepUpto_val);
				cur_ROW.appendChild(cell9);
				
				var cell9a=document.createElement("TD");
				var Dep_cost_val = document.createElement("input");
				Dep_cost_val.type = "hidden";
				Dep_cost_val.id = "Depupt0_cost" + seq;
				Dep_cost_val.name = "Depupt0_cost" + seq;
				Dep_cost_val.value =Depreciaton_Cost;
				var Dep_cost_val_Text= document.createTextNode(Depreciaton_Cost);
				cell9a.appendChild(Dep_cost_val_Text);
				cell9a.appendChild(Dep_cost_val);
				cur_ROW.appendChild(cell9a);
				
				var cell8=document.createElement("TD");
				var Dep_cost_val = document.createElement("input");
				Dep_cost_val.type = "hidden";
				Dep_cost_val.id = "Dep_Tot" + seq;
				Dep_cost_val.name = "Dep_Tot" + seq;
				Dep_cost_val.value =Depreciation_upto;
				var Dep_cost_val_Text= document.createTextNode(Depreciation_upto);
				cell8.appendChild(Dep_cost_val_Text);
				cell8.appendChild(Dep_cost_val);
				cur_ROW.appendChild(cell8);
				
				var cell10=document.createElement("TD");
				var Dis_Asset_val = document.createElement("input");
				Dis_Asset_val.type = "hidden";
				Dis_Asset_val.id = "openDis_Asset" + seq;
				Dis_Asset_val.name = "openDis_Asset" + seq;
				Dis_Asset_val.value =Dis_Asset;
				var Dis_Asset_Text= document.createTextNode(Dis_Asset);
				cell10.appendChild(Dis_Asset_Text);
				cell10.appendChild(Dis_Asset_val);
				cur_ROW.appendChild(cell10);
				
				var cell11=document.createElement("TD");
				var Discarder_Asset_val = document.createElement("input");
				Discarder_Asset_val.type = "hidden";
				Discarder_Asset_val.id = "Dis_Asset" + seq;
				Discarder_Asset_val.name = "Dis_Asset" + seq;
				Discarder_Asset_val.value =Discarder_Asset;
				var Discarder_Asset_Text= document.createTextNode(Discarder_Asset);
				cell11.appendChild(Discarder_Asset_Text);
				cell11.appendChild(Discarder_Asset_val);
				cur_ROW.appendChild(cell11);
				
				var cell12=document.createElement("TD");
				var Discarder_Asset_val = document.createElement("input");
				Discarder_Asset_val.type = "hidden";
				Discarder_Asset_val.id = "DisClose_Asset" + seq;
				Discarder_Asset_val.name = "DisClose_Asset" + seq;
				Discarder_Asset_val.value =Discarder_Asset+Dis_Asset;
				var Discarder_Asset_Text= document.createTextNode(Discarder_Asset+Dis_Asset);
				cell12.appendChild(Discarder_Asset_Text);
				cell12.appendChild(Discarder_Asset_val);
				cur_ROW.appendChild(cell12);
				
				var cell13=document.createElement("TD");
				var BAl_val = document.createElement("input");
				BAl_val.type = "hidden";
				BAl_val.id = "DisBAl_val_Asset" + seq;
				BAl_val.name = "DisBAl_val_Asset" + seq;
				BAl_val.value =Depreciation_upto+(Discarder_Asset+Dis_Asset);
				var BAl_val_Text= document.createTextNode(Depreciation_upto+(Discarder_Asset+Dis_Asset));
				cell13.appendChild(BAl_val_Text);
				cell13.appendChild(BAl_val);
				cur_ROW.appendChild(cell13);
				
				var cell14=document.createElement("TD");
				var AppOpen_val = document.createElement("input");
				AppOpen_val.type = "hidden";
				AppOpen_val.id = "AppOpen_val" + seq;
				AppOpen_val.name = "AppOpen_val" + seq;
				AppOpen_val.value =App_Net;
				var AppOpen_val_Text= document.createTextNode(App_Net);
				cell14.appendChild(AppOpen_val);
				cell14.appendChild(AppOpen_val_Text);
				cur_ROW.appendChild(cell14);
				
				
				var cell15=document.createElement("TD");
				var AppOpenCr_val = document.createElement("input");
				AppOpenCr_val.type = "hidden";
				AppOpenCr_val.id = "AppOpenCr_val" + seq;
				AppOpenCr_val.name = "AppOpenCr_val" + seq;
				AppOpenCr_val.value =App_Grd;
				var AppOpenCr_valText= document.createTextNode(App_Grd);
				cell15.appendChild(AppOpenCr_val);
				cell15.appendChild(AppOpenCr_valText);
				cur_ROW.appendChild(cell15);
				
				
				var cell16=document.createElement("TD");
				var AppYY_val = document.createElement("input");
				AppYY_val.type = "hidden";
				AppYY_val.id = "AppOpenYY_val" + seq;
				AppYY_val.name = "AppOpenYY_val" + seq;
				AppYY_val.value =App_YY;
				var AppYY_val_Text= document.createTextNode(App_YY);
				cell16.appendChild(AppYY_val);
				cell16.appendChild(AppYY_val_Text);
				cur_ROW.appendChild(cell16);
				
				var cell17=document.createElement("TD");
				var AppGD_Net = document.createElement("input");
				AppGD_Net.type = "hidden";
				AppGD_Net.id = "AppGD_Net" + seq;
				AppGD_Net.name = "AppGD_Net" + seq;
				AppGD_Net.value =App_Grd-App_YY;
				var AppGD_Net_Text= document.createTextNode(App_Grd-App_YY);
				cell17.appendChild(AppGD_Net);
				cell17.appendChild(AppGD_Net_Text);
				cur_ROW.appendChild(cell17);
				
				var cell18=document.createElement("TD");
				var AppGD_Upto_val = document.createElement("input");
				AppGD_Upto_val.type = "hidden";
				AppGD_Upto_val.id = "AppGD_Upto" + seq;
				AppGD_Upto_val.name = "AppGD_Upto" + seq;
				AppGD_Upto_val.value =App_Net+(App_Grd-App_YY);
				var AppGD_Upto_Text= document.createTextNode(App_Net+(App_Grd-App_YY));
				cell18.appendChild(AppGD_Upto_val);
				cell18.appendChild(AppGD_Upto_Text);
				cur_ROW.appendChild(cell18);

				var cell19=document.createElement("TD");
				var BAlance_val = document.createElement("input");
				BAlance_val.type = "hidden";
				BAlance_val.id = "AppGD_Upto" + seq;
				BAlance_val.name = "AppGD_Upto" + seq;
				BAlance_val.value =(Depreciation_upto+(Discarder_Asset+Dis_Asset))-(App_Net+(App_Grd-App_YY));
				var BAlance_val_Text= document.createTextNode((Depreciation_upto+(Discarder_Asset+Dis_Asset))-(App_Net+(App_Grd-App_YY)));
				cell19.appendChild(BAlance_val);
				cell19.appendChild(BAlance_val_Text);
				cur_ROW.appendChild(cell19);
				
				tbody.appendChild(cur_ROW);
				seq++;
				
					}
				     }
	if(flag=="Failure")
	                  {
		alert("No Records");
	                  }

      }
                    
	
}
