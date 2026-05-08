
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



function report_A52()
{
	 var cmbAcc_UnitCode=document.getElementById("cmbAcc_UnitCode").value;
		var rep_type=document.getElementById("rep_type").value;
		var fin_year=document.getElementById("cmbFinancial_Year").value;
	    var year=fin_year.split("-");
		
		var cmbFinancial_Year=year[0]+"-"+year[1].substring(2);
		//alert(cmbFinancial_Year);
		    var major_cmb=document.getElementById("major_cmb").value;
		   // alert(major_cmb);
		    if(major_cmb=="")major_cmb=0;
		    var hid=document.getElementById("hid").value;
		    //alert("his >>>> "+hid);
		    if(hid==1){
		    	rep_type="All";
		    }
		    if(hid==2){
		    	rep_type="AllMajorType";
		    }
		    if(hid==""){
		    	rep_type="OneMajorType";
		    }
		    
		   /* if(major_cmb!="" && major_cmb!=0){
		    	rep_type="MajorType";}
		    if(major_cmb==""){major_cmb=0;
		    rep_type="All";}*/
		    
		 
		    var url="../../../../../Asset_6_serv?command=A52&cmbAcc_UnitCode="+cmbAcc_UnitCode+
		    "&rep_type="+rep_type+"&cmbFinancial_Year="+cmbFinancial_Year+"&major_cmb="+major_cmb;
		   // alert(url);
		    document.frmAsset52.action=url;
		    document.frmAsset52.method="POST";
		    document.frmAsset52.submit();
		      return true;
		
	}

function report_Sub() {
	
	    var cmbAcc_UnitCode=document.getElementById("cmbAcc_UnitCode").value;
	var rep_type=document.getElementById("rep_type").value;
	var fin_year=document.getElementById("cmbFinancial_Year").value;
    var year=fin_year.split("-");
	
	var cmbFinancial_Year=year[0]+"-"+year[1].substring(2);
	//alert(cmbFinancial_Year);
	    var major_cmb=document.getElementById("major_cmb").value;
	   // alert(major_cmb);
	    if(major_cmb=="")major_cmb=0;
	    var hid=document.getElementById("hid").value;
	    //alert("his >>>> "+hid);
	    if(hid==1){
	    	rep_type="All";
	    }
	    if(hid==2){
	    	rep_type="AllMajorType";
	    }
	    if(hid==""){
	    	rep_type="OneMajorType";
	    }
	    
	   /* if(major_cmb!="" && major_cmb!=0){
	    	rep_type="MajorType";}
	    if(major_cmb==""){major_cmb=0;
	    rep_type="All";}*/
	 
	    var url="../../../../../Asset_6_serv?command=A6&cmbAcc_UnitCode="+cmbAcc_UnitCode+
	    "&rep_type="+rep_type+"&cmbFinancial_Year="+cmbFinancial_Year+"&major_cmb="+major_cmb;
	   // alert(url);
	    document.frmAsset6.action=url;
	    document.frmAsset6.method="POST";
	    document.frmAsset6.submit();
	      return true;
	}
