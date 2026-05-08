var seq = 0;
var __pagination=5;
var totalblock=0,seq=0;
var baseResponse=null;
var desg_name=null;
var Audit_party_id=new Array();
var Audit_type_id=new Array();
var Office_Audited=new Array();
var Audit_cover_frm=new Array();
var Audit_cover_to=new Array();
var Audit_com=new Array();
var Audit_cop=new Array();
var Audit_dis=new Array();
var Audit_rep=new Array();
var Any_mom=new Array();
var Any_res=new Array();
var remarks=new Array();
var off_name=new Array();
var inspect=new Array();



var training_centre_id=new Array();
///////////////////////////////////////////////////////AJAX FUNCTION//////////////////////////////////////////////////////////////////////////////
function AjaxFunction()
    {
        var xmlrequest=false;
        try
            {
               xmlrequest=new ActiveXObject("Msxml2.XMLHTTP"); //mozilla
            }
         catch(e1)
          {
                 try
                 {
                     xmlrequest=new ActiveXObject("Microsoft.XMLHTTP"); //IE
                 }
                 catch(e2)
                 {     
                     xmlrequest=false;
                 }
          }
          if (!xmlrequest && typeof XMLHttpRequest != 'undefined') 
                {
                     xmlrequest=new XMLHttpRequest();
                }
        return xmlrequest;
    } 

//new code



//////////////////////////////////////////////////////////////FUNCTION TO LOAD OFFICE NAME////////////////////////////////////////////////////////



///////////////////////////////////////////////FUNCTION TO CHECK WHETHER THE OFFICE ID ALREADY EXIST IN THE DB///////////////////////////////////////
/*function office_idcheck()
{
	
    var xmlrequest= AjaxFunction();
   
    office_id_audited=document.audit_para_details.office_id_audited.value;
    if(office_id_audited=="")
        {
            alert("enter OFFICE ID ID in the field");
            document.audit_para_details.office_id_audited.focus();
          
        }
    else
        {
            var url="../../../../auditparadetails_serv?command=office_idcheck&office_id_audited="+office_id_audited;
          
            xmlrequest.open("GET",url,true);    
            xmlrequest.onreadystatechange=function()
                {
            		
                    manipulate(xmlrequest);
                };
            xmlrequest.send(null);
        }
}

function office_checkingid(baseResponse)
{
      var flag=baseResponse.getElementsByTagName("flag")[0].firstChild.nodeValue; 
     
      if(flag=="failure")  
        {
            alert("tis office id dosenot exist , you cannot enter the office id again");  
            document.audit_para_details.office_id_audited.value="";
            document.audit_para_details.office_id_audited.focus();
           
        }
}
*/
////////////////////////////////////FUNCTION TO INSERT THE VALUES INTO DB///////////////////////////////////////////////
function GetoffDet()
{
	var xmlrequest= AjaxFunction();
	var off_audit=document.getElementById("office_id_audited").value;
	var oid=document.getElementById("txtOffId").value;
	var url="../../../../Gpf_create_Party_details?command=Getdetails&off_audit="+off_audit+"&oid="+oid;
    
    xmlrequest.open("GET",url,true);              
    xmlrequest.onreadystatechange=function()
    {
      handleResponse(xmlrequest);
    }
    ///call the date function here
    xmlrequest.send(null);

}

function handleResponse(xmlrequest)
{
  if(xmlrequest.readyState==4)
  {
    if(xmlrequest.status==200)
    {
      var baseResponse=xmlrequest.responseXML.getElementsByTagName("response")[0];
      var command=baseResponse.getElementsByTagName("command")[0].firstChild.nodeValue;
      
      if(command=="Getdetails")
      {      
    	  var flag=baseResponse.getElementsByTagName("flag")[0].firstChild.nodeValue;
    	  
    	    if(flag=="success")
    	    {
    	    	 var OFFICE_NAME=baseResponse.getElementsByTagName("OFFICE_NAME")[0].firstChild.nodeValue;
    	    	 var OFFICE_LEVEL_ID=baseResponse.getElementsByTagName("OFFICE_LEVEL_ID")[0].firstChild.nodeValue;
    	    	 
    	    	 var off_type=OFFICE_NAME+"-"+OFFICE_LEVEL_ID;
    	    	 document.getElementById("office_name_level").value=off_type;
    	    	 //document.getElementById("").value=OFFICE_LEVEL_ID;
    	    }
    	    
    	    else
    	    	if(flag=="Not Exist")
    	    	{
    	    		alert("This Office doesn't belonging to your office");
    	    		document.getElementById("office_id_audited").value="";
    	    		document.getElementById("office_name_level").value="";
    	    	}
         //alert("1");
      }
      
      else if(command=="Add")
      {      
    	  var flag=baseResponse.getElementsByTagName("flag")[0].firstChild.nodeValue;
    	  
    	    if(flag=="success")
    	    {
    	    	alert("Record Added Successfully");
    	    	document.getElementById("office_id_audited").disabled=false;
    			document.getElementById("off_img").style.display="block";
    	    	getdata();
    	    	clearall();
    	    }
         //alert("1");
      }
      else if(command=="Updation")
      {      
    	  var flag=baseResponse.getElementsByTagName("flag")[0].firstChild.nodeValue;
    	  
    	    if(flag=="success")
    	    {
    	    	alert("Record Updated Successfully");
    	    	getdata();
    	    	clearall();
    	    	document.getElementById("CmdUpdate").style.display="none";
    	    	document.getElementById("Cmdvalidate").style.display="none";
    	    	document.getElementById("Cmddel").style.display="none";
    			document.getElementById("CmdAdd").style.display="";
    	    	//document.audit_party_details.submit();
    			document.getElementById("office_id_audited").disabled=false;
    			document.getElementById("off_img").style.display="block";
    	    }
         //alert("1");
      }
      else if(command=="Validate")
      {
          
    	  var flag=baseResponse.getElementsByTagName("flag")[0].firstChild.nodeValue;
    	  
    	    if(flag=="success")
    	    {
    	    	alert("Record Validated Successfully");
    	    	getdata();
    	    	clearall();
    	    	document.getElementById("CmdUpdate").style.display="none";
    	    	document.getElementById("Cmdvalidate").style.display="none";
    	    	document.getElementById("Cmddel").style.display="none";
    			document.getElementById("CmdAdd").style.display="";
    			document.getElementById("office_id_audited").disabled=false;
    			document.getElementById("off_img").style.display="block";
    	    	//document.audit_party_details.submit();
    	    }
      }
      else if(command=="Delete")
      {      
    	  var flag=baseResponse.getElementsByTagName("flag")[0].firstChild.nodeValue;
    	  
    	    if(flag=="success")
    	    {
    	    	alert("Record Deleted Successfully");
    	    	getdata();
    	    	clearall();
    	    }
         //alert("1");
      }
    }
  }
}
function changepagesize() 
{
	
    pagesize = document.getElementById("cmbpagination").value;
    var len = baseResponse.getElementsByTagName("Total").length;
    
    var cmbpage = document.getElementById("cmbpage");
    try {
        cmbpage.innerHTML = "";
    } catch (e) {
        cmbpage.innerText = "";
    }

    var i = 1;
    for (i = 1; i <= ((len / pagesize) + 1); i++) 
    {
        var option = document.createElement("OPTION");
        option.text = i;
        option.value = i;
        try 
        {
            cmbpage.add(option);
        } catch (errorObject) 
        {
            cmbpage.add(option, null);
        }
    }
    changepage();

}






function grid_vacancy()
{

	  //var office_id=document.getElementById("Office_Id_Audited").value;
	  
	     var url="../../../../vacancy?command=vacants";
		
		 var req=getTransport();
	     req.open("GET",url,true); 
	     
	     req.onreadystatechange=function()
	     {
	    	 viewResponsed(req);
	     };   
	     req.send(null);
	  
	  

}

function training_programs()
{
	var year = document.getElementById("year").value;
	var url="../../../../vacancy?command=training&year="+year;
	//alert(url);
	
	 var req=getTransport();
    req.open("GET",url,true); 
    
    req.onreadystatechange=function()
    {
   	 viewResponsed(req);
    };   
    req.send(null);
}


function cadre_wise_retire()
{
	var year = document.getElementById("year").value;
	var service_group=document.getElementById("ser_group").value;
	var url="../../../../Retirement_Cadre_Wise?command=cadre_wise_chart&year="+year+"&service_group="+service_group;
	//alert(url);
	
	 var req=getTransport();
    req.open("GET",url,true); 
    
    req.onreadystatechange=function()
    {
   	 viewResponsed(req);
    };   
    req.send(null);
}

function cadre_wise_retire_sub_chart()
{
	var year = document.getElementById("year_wise").value;
	var cadre_id=document.getElementById("cadre_id").value;
	var url="../../../../Retirement_Cadre_Wise?command=cadre_wise_sub_chart&year="+year+"&cadre_id="+cadre_id;
	//alert(url);
	
	 var req=getTransport();
    req.open("GET",url,true); 
    
    req.onreadystatechange=function()
    {
   	 viewResponsed(req);
    };   
    req.send(null);
}


function grid_vacancy_circle_wise()
{
	
	var regionSno=document.getElementById("reigoin_id").value;
	var url="../../../../vacancy?command=vacants_circle_wise&region_id="+regionSno;
	//alert(url);
	 var req=getTransport();
    req.open("GET",url,true); 
    
    req.onreadystatechange=function()
    {
   	 viewResponsed(req);
    };   
    req.send(null);
}


function Training_chart_subprograms()
{
	var year=document.getElementById("year").value;
	var regionSno=document.getElementById("train_id").value;
	var url="../../../../vacancy?command=sub_programs&program_id="+regionSno+"&year="+year;
	//alert(url);
	 var req=getTransport();
    req.open("GET",url,true); 
    
    req.onreadystatechange=function()
    {
   	 viewResponsed(req);
    };   
    req.send(null);
}




function viewResponsed(xmlrequest)
{
	
	if(xmlrequest.readyState==4)
{
	if(xmlrequest.status==200)
	{
		//alert(xmlrequest.responseText);
		  baseResponse=xmlrequest.responseXML.getElementsByTagName("response")[0];
		  
		  
	      var command=baseResponse.getElementsByTagName("command")[0].firstChild.nodeValue;
	      
	      var flag=baseResponse.getElementsByTagName("flag")[0].firstChild.nodeValue;
	      
	      document.getElementById("vacance_deatils").innerHTML="";
	      
	      if(command=="cadre_wise_sub_chart")
	      {

		      if(flag=="success")
		      {
		    	  var count=baseResponse.getElementsByTagName("count")[0].firstChild.nodeValue;
		    	  var tr = document.createElement("TR");
		          tr.id = seq;
		          
		          
		          var total_training=0,total_participants=0;
		          
		          
		            var data3 = new google.visualization.DataTable();
			        data3.addColumn('string', 'Topping');
			        data3.addColumn('number', 'Slices');
			        
			        data3.addColumn({type: 'string', role: 'tooltip'});
			        var dataSet3= new Array();
			        
			        
			      
			        
			        var dataSet44=new Array();
			        dataSet44.push(['Year', 'total_retire',{ role: 'style' }]);
			        
			        
//			        var data44 = new google.visualization.DataTable();
//			        data44.addColumn('string', 'Topping');
//			        data44.addColumn('number', 'Slices');
//			        data44.addColumn({type: 'string', role: 'tooltip'});
//			        var dataset44=new Array();
		          
			        //dataset44.push(['Training Centre', '', '']);
		    	  for(var i=0;i<count;i++)
		    	  {
		    		  var cadre_name=baseResponse.getElementsByTagName("cadre_name")[i].firstChild.nodeValue;
		    		  
		    		  var cadre_id=baseResponse.getElementsByTagName("cadre_id")[i].firstChild.nodeValue;
		    		  
		    		  var Year=baseResponse.getElementsByTagName("Year")[i].firstChild.nodeValue;
		    		  
		    		  var total_retire=baseResponse.getElementsByTagName("total_retire")[i].firstChild.nodeValue;
		    		  
//		    		  var total_retire_17=baseResponse.getElementsByTagName("total_retire_17")[i].firstChild.nodeValue;
//		    		  var total_retire_15=baseResponse.getElementsByTagName("total_retire_15")[i].firstChild.nodeValue;
//		    		  var total_retire_16=baseResponse.getElementsByTagName("total_retire_16")[i].firstChild.nodeValue;
//		    		  var total_retire_18=baseResponse.getElementsByTagName("total_retire_18")[i].firstChild.nodeValue;
		    		  
		    		//  var VENUE_TRG_CENTRE_ID=baseResponse.getElementsByTagName("VENUE_TRG_CENTRE_ID")[i].firstChild.nodeValue;
		    		  
		    		 
		    		  
		    		  
		    		 
//		    		  var SANCTIONED_NO_OF_POSTS=baseResponse.getElementsByTagName("SANCTIONED_NO_OF_POSTS")[i].firstChild.nodeValue;
//		    		  var FILLEDUP_POSTS=baseResponse.getElementsByTagName("FILLEDUP_POSTS")[i].firstChild.nodeValue;
//		    		  var DIVERSION_TO_OTHER=baseResponse.getElementsByTagName("DIVERSION_TO_OTHER")[i].firstChild.nodeValue;
//		    		  var DIVERSION_FROM_OTHER=baseResponse.getElementsByTagName("DIVERSION_FROM_OTHER")[i].firstChild.nodeValue;
//		    		  var vacant_posts=baseResponse.getElementsByTagName("vacant_posts")[i].firstChild.nodeValue;
		    		  
		    	
		    		   total_training=parseInt(total_training)+parseInt(total_retire);
		    		  //total_participants=parseInt(total_participants)+parseInt(tot_emp);
		    		  
		    		  
		    		 // dataSet3.push([region_office_id,parseInt(SANCTIONED_NO_OF_POSTS),parseInt(FILLEDUP_POSTS),parseInt(DIVERSION_TO_OTHER),parseInt(DIVERSION_FROM_OTHER),parseInt(vacant_posts)]);
		    		  //dataSet3.push([cadre_name,parseInt(total_retire_14),parseInt(total_retire_15),parseInt(total_retire_16),parseInt(total_retire_17),parseInt(total_retire_18),cadre_id]);
		    		  
		    		  dataSet3.push([Year,parseInt(total_retire),cadre_id]);
		    		  
//		    		  var letters = 'ABCDE'.split('');
//		    		  var color = '#';
//		    		  
//		    		  color += letters[Math.floor(Math.random() * letters.length)];
		    		  
//		    		  dataSet44.push([Year,parseInt(total_retire),cadre_id]);
		    		  
		    		   
		    		  
		    		  
		    		  
		    		  
		    		 dataSet44.push([Year,parseInt(total_retire),parseInt(cadre_id)]);
		    		  
		    		  var tr=document.createElement("tr");
		    		  
		    		  
		    		  
		    	        var td=document.createElement("td");	    		  
		    		    var VENUE_TRG_CENTRE_ID = document.createTextNode(cadre_id);
		    	        td.appendChild(VENUE_TRG_CENTRE_ID);
		    	        td.setAttribute("align", "center");
		    	      //  td.setAttribute("style", "display:none");
		    	        tr.appendChild(td);
		    	        
		    		  
		    		  var td=document.createElement("td");
		    	        var TRG_CENTRE_NAME = document.createTextNode(cadre_name);
		    	        td.appendChild(TRG_CENTRE_NAME);
		    	        td.setAttribute("align", "center");
		    	        //td.setAttribute("colspan", "2");
		    	      //  td.setAttribute("style", "display:none");
		    	        tr.appendChild(td);
		    		  
		    		    var td=document.createElement("td");	    		  
		    		    var TOT_NO_PROGRAMMES = document.createTextNode(Year);
		    	        td.appendChild(TOT_NO_PROGRAMMES);
		    	        td.setAttribute("align", "center");
		    	        //td.setAttribute("style", "display:none");
		    	        tr.appendChild(td);
		    	        
		    	        
		    	      		    	        
		    	        var td=document.createElement("td");	    		  
		    		    var TOT_NO_PROGRAMMES = document.createTextNode(total_retire);
		    	        td.appendChild(TOT_NO_PROGRAMMES);
		    	        td.setAttribute("align", "center");
		    	      //  td.setAttribute("style", "display:none");
		    	        tr.appendChild(td);
		    	        
		    	        
//		    	        var td=document.createElement("td");	    		  
//		    		    var TOT_NO_PROGRAMMES = document.createTextNode(total_retire_16);
//		    	        td.appendChild(TOT_NO_PROGRAMMES);
//		    	        td.setAttribute("align", "center");
//		    	      //  td.setAttribute("style", "display:none");
//		    	        tr.appendChild(td);
//		    	        
//		    	        
//		    	        var td=document.createElement("td");	    		  
//		    		    var TOT_NO_PROGRAMMES = document.createTextNode(total_retire_17);
//		    	        td.appendChild(TOT_NO_PROGRAMMES);
//		    	        td.setAttribute("align", "center");
//		    	      //  td.setAttribute("style", "display:none");
//		    	        tr.appendChild(td);
//		    	        
//		    	        
//		    	        var td=document.createElement("td");	    		  
//		    		    var TOT_NO_PROGRAMMES = document.createTextNode(total_retire_18);
//		    	        td.appendChild(TOT_NO_PROGRAMMES);
//		    	        td.setAttribute("align", "center");
//		    	      //  td.setAttribute("style", "display:none");
//		    	        tr.appendChild(td);
		    	        

		    	      
//		    	        var td=document.createElement("td");
//		    	        var SANCTIONED_NO_OF_POSTS = document.createTextNode(tot_emp);
//		    	        td.appendChild(SANCTIONED_NO_OF_POSTS);
//		    	        td.setAttribute("align", "center");
//		    	      //  td.setAttribute("style", "display:none");
//		    	        tr.appendChild(td);
		    	        
		    	        
		    	        
		    	        if(i%2==0)
			    		  {
			    			  
			    		  }
			    		  else
			    		  {
			    			  tr.setAttribute("style", "background-color:lightblue;");
			    		  }
//		    	        
//		    	        var td=document.createElement("td");
//		    	        var FILLEDUP_POSTS = document.createTextNode(FILLEDUP_POSTS);
//		    	        td.appendChild(FILLEDUP_POSTS);
//		    	      //  td.setAttribute("style", "display:none");
//		    	        tr.appendChild(td);
//		    	        
//		    	        var td=document.createElement("td");
//		    	        var DIVERSION_TO_OTHER = document.createTextNode(DIVERSION_TO_OTHER);
//		    	        td.appendChild(DIVERSION_TO_OTHER);
//		    	      //  td.setAttribute("style", "display:none");
//		    	        tr.appendChild(td);
//		    	        
//		    	        var td=document.createElement("td");
//		    	        var DIVERSION_FROM_OTHER = document.createTextNode(DIVERSION_FROM_OTHER);
//		    	        td.appendChild(DIVERSION_FROM_OTHER);
//		    	      //  td.setAttribute("style", "display:none");
//		    	        tr.appendChild(td);
//		    	        
//		    	        var td=document.createElement("td");
//		    	        var vacant_posts = document.createTextNode(vacant_posts);
//		    	        td.appendChild(vacant_posts);
//		    	      //  td.setAttribute("style", "display:none");
//		    	        tr.appendChild(td);
		    	        
		    	        document.getElementById("vacance_deatils").appendChild(tr);
		    		  
		    		  
		    		  
		    	  }
		    	  
		    	  
		    	 
	    	        document.getElementById("vacance_deatils").appendChild(tr);
		    	  
		    	  
		    	  data3.addRows(dataSet3);
			        var options2 = { title : 'Retirement Cadre Wise','width':600,
			                       'height':300,sliceVisibilityThreshold: 0, is3D: true, hAxis: {minValue: 1000}};
			        var chart2 = new google.visualization.PieChart(document.getElementById('vacance_bar_programs'));
			        chart2.draw(data3, options2);
			        
			        
//			        data44.addRows(dataset44);
//			        var options22 = { title : 'Retirement Cadre Wise','width':600,
//			                       'height':300,sliceVisibilityThreshold: 0, is3D: true, hAxis: {minValue: 1000}};
//			        var chart8 = new google.visualization.PieChart(document.getElementById('vacance_bar_participates'));
//			        chart8.draw(data44, options22);
			        
//			         var data44 =google.visualization.arrayToDataTable(dataset44);  
//					 var optionsss = {
//		                     title : 'Total No of Traning Programs', 'height':400,'width':900,
//		                     vAxis: {title: "Share Amount"},
//		                     hAxis: {title: "Office Name"},sliceVisibilityThreshold: 0,
//		                     seriesType: "bars", allowHtml: true
//		                   };
//
//		                   var chartss = new google.visualization.ComboChart(document.getElementById('vacance_bar_participates'));
//		
			        
			      
			        
			        
			        var data44 =google.visualization.arrayToDataTable(dataSet44);  
			         
			        
			         
					 var optionsss = {
		                     title : 'Retirement Cadre Wise', 'height':400,'width':500,
		                     vAxis: {title: "Separate Total"},
		                     hAxis: {title: "Cadre Name"},sliceVisibilityThreshold: 0,
		                     seriesType: "bars", allowHtml: true
		                   };

		                   var chartss = new google.visualization.ComboChart(document.getElementById('vacance_bar_participates'));
		                   chartss.draw(data44, optionsss);
			        
			        function eventHandler()
			        {
			        	var strRegionSno=data3.getValue(chart2.getSelection()[0].row, 2);
			        	//var strRegionName = data3.getValue(chart2.getSelection()[0].row, 0);
			        	var year=document.getElementById("year").value;
			        	
			        	
			        	location.href="Retirement_Cadre_Wise.jsp?regionSno="+strRegionSno+"&year="+year,'RETIREMENT CADRE WISE CHART';
			        	//openFormInWindow("vacance_report_circle_wise.jsp?regionSno="+strRegionSno+"&regionName="+strRegionName+"&finYear="+getValue("finYear"),'DASHBOARD_SHAREAMT_CHART');
			        }
			        google.visualization.events.addListener(chart2, 'select', eventHandler); 
			        
			        
			        function eventHandler()
			        {
			        	var strRegionSno=data44.getValue(chartss.getSelection()[0].row, 2);
			        	//var strRegionName = data3.getValue(chart2.getSelection()[0].row, 0);
			        	
			        	var year=document.getElementById("year").value;
			        	
			        	
			        	
			        	location.href="Retirement_Cadre_Wise.jsp?regionSno="+strRegionSno+"&year="+year,'CADRE WISE RETIREMENT ';
			        	//openFormInWindow("vacance_report_circle_wise.jsp?regionSno="+strRegionSno+"&regionName="+strRegionName+"&finYear="+getValue("finYear"),'DASHBOARD_SHAREAMT_CHART');
			        }
			        google.visualization.events.addListener(chartss, 'select', eventHandler); 
					
		      }
		      
		
	      
	      
	      
	    	  
	      }
	      
	      if(command=="cadre_wise_chart")
	      {



		      if(flag=="success")
		      {
		    	  var count=baseResponse.getElementsByTagName("count")[0].firstChild.nodeValue;
		    	  var tr = document.createElement("TR");
		          tr.id = seq;
		          
		          
		          var total_training=0,total_participants=0;
		          
		          
		            var data3 = new google.visualization.DataTable();
			        data3.addColumn('string', 'Topping');
			        data3.addColumn('number', 'Slices');
			        data3.addColumn({type: 'string', role: 'tooltip'});
			        var dataSet3= new Array();
			        
			        
			      
			        
			        var dataSet44=new Array();
			        dataSet44.push(['cadre_name', 'total_retire',{ role: 'style' }]);
			        
			        
//			        var data44 = new google.visualization.DataTable();
//			        data44.addColumn('string', 'Topping');
//			        data44.addColumn('number', 'Slices');
//			        data44.addColumn({type: 'string', role: 'tooltip'});
//			        var dataset44=new Array();
		          
			        //dataset44.push(['Training Centre', '', '']);
		    	  for(var i=0;i<count;i++)
		    	  {
		    		  var cadre_name=baseResponse.getElementsByTagName("cadre_name")[i].firstChild.nodeValue;
		    		  
		    		  var cadre_id=baseResponse.getElementsByTagName("cadre_id")[i].firstChild.nodeValue;
		    		  
		    		  var total_retire=baseResponse.getElementsByTagName("total_retire")[i].firstChild.nodeValue;
		    		  
		    		//  var VENUE_TRG_CENTRE_ID=baseResponse.getElementsByTagName("VENUE_TRG_CENTRE_ID")[i].firstChild.nodeValue;
		    		  
		    		  var Year=baseResponse.getElementsByTagName("Year")[i].firstChild.nodeValue;
		    		 
//		    		  var SANCTIONED_NO_OF_POSTS=baseResponse.getElementsByTagName("SANCTIONED_NO_OF_POSTS")[i].firstChild.nodeValue;
//		    		  var FILLEDUP_POSTS=baseResponse.getElementsByTagName("FILLEDUP_POSTS")[i].firstChild.nodeValue;
//		    		  var DIVERSION_TO_OTHER=baseResponse.getElementsByTagName("DIVERSION_TO_OTHER")[i].firstChild.nodeValue;
//		    		  var DIVERSION_FROM_OTHER=baseResponse.getElementsByTagName("DIVERSION_FROM_OTHER")[i].firstChild.nodeValue;
//		    		  var vacant_posts=baseResponse.getElementsByTagName("vacant_posts")[i].firstChild.nodeValue;
		    		  
		    	
		    		   total_training=parseInt(total_training)+parseInt(total_retire);
		    		  //total_participants=parseInt(total_participants)+parseInt(tot_emp);
		    		  
		    		  
		    		 // dataSet3.push([region_office_id,parseInt(SANCTIONED_NO_OF_POSTS),parseInt(FILLEDUP_POSTS),parseInt(DIVERSION_TO_OTHER),parseInt(DIVERSION_FROM_OTHER),parseInt(vacant_posts)]);
		    		  dataSet3.push([cadre_name,parseInt(total_retire),cadre_id]);
		    		  
//		    		  var letters = 'ABCDE'.split('');
//		    		  var color = '#';
//		    		  
//		    		  color += letters[Math.floor(Math.random() * letters.length)];
		    		  
		    		  dataSet44.push([cadre_name,parseInt(total_retire),cadre_id]);
		    		  
		    		   
		    		  
		    		  
		    		  
		    		  
		    		//  dataset44.push([cadre_name,parseInt(total_retire),cadre_id]);
		    		  
		    		  var tr=document.createElement("tr");
		    		  
		    		  
		    		  
		    	        var td=document.createElement("td");	    		  
		    		    var VENUE_TRG_CENTRE_ID = document.createTextNode(cadre_id);
		    	        td.appendChild(VENUE_TRG_CENTRE_ID);
		    	        td.setAttribute("align", "center");
		    	      //  td.setAttribute("style", "display:none");
		    	        tr.appendChild(td);
		    	        
		    		  
		    		  var td=document.createElement("td");
		    	        var TRG_CENTRE_NAME = document.createTextNode(cadre_name);
		    	        td.appendChild(TRG_CENTRE_NAME);
		    	        td.setAttribute("align", "center");
		    	        td.setAttribute("colspan", "2");
		    	      //  td.setAttribute("style", "display:none");
		    	        tr.appendChild(td);
		    		  
		    		    var td=document.createElement("td");	    		  
		    		    var TOT_NO_PROGRAMMES = document.createTextNode(total_retire);
		    	        td.appendChild(TOT_NO_PROGRAMMES);
		    	        td.setAttribute("align", "center");
		    	      //  td.setAttribute("style", "display:none");
		    	        tr.appendChild(td);
		    	        
		    	      
//		    	        var td=document.createElement("td");
//		    	        var SANCTIONED_NO_OF_POSTS = document.createTextNode(tot_emp);
//		    	        td.appendChild(SANCTIONED_NO_OF_POSTS);
//		    	        td.setAttribute("align", "center");
//		    	      //  td.setAttribute("style", "display:none");
//		    	        tr.appendChild(td);
		    	        
		    	        
		    	        
		    	        if(i%2==0)
			    		  {
			    			  
			    		  }
			    		  else
			    		  {
			    			  tr.setAttribute("style", "background-color:lightblue;");
			    		  }
//		    	        
//		    	        var td=document.createElement("td");
//		    	        var FILLEDUP_POSTS = document.createTextNode(FILLEDUP_POSTS);
//		    	        td.appendChild(FILLEDUP_POSTS);
//		    	      //  td.setAttribute("style", "display:none");
//		    	        tr.appendChild(td);
//		    	        
//		    	        var td=document.createElement("td");
//		    	        var DIVERSION_TO_OTHER = document.createTextNode(DIVERSION_TO_OTHER);
//		    	        td.appendChild(DIVERSION_TO_OTHER);
//		    	      //  td.setAttribute("style", "display:none");
//		    	        tr.appendChild(td);
//		    	        
//		    	        var td=document.createElement("td");
//		    	        var DIVERSION_FROM_OTHER = document.createTextNode(DIVERSION_FROM_OTHER);
//		    	        td.appendChild(DIVERSION_FROM_OTHER);
//		    	      //  td.setAttribute("style", "display:none");
//		    	        tr.appendChild(td);
//		    	        
//		    	        var td=document.createElement("td");
//		    	        var vacant_posts = document.createTextNode(vacant_posts);
//		    	        td.appendChild(vacant_posts);
//		    	      //  td.setAttribute("style", "display:none");
//		    	        tr.appendChild(td);
		    	        
		    	        document.getElementById("vacance_deatils").appendChild(tr);
		    		  
		    		  
		    		  
		    	  }
		    	  
		    	  
		    	  var tr=document.createElement("tr");
		    	  
		    	  
		    	  var td=document.createElement("td");
	    	        var TRG_CENTRE_NAME = document.createTextNode("TOTAL");
	    	        var font=document.createElement("font");
	    	        var b=document.createElement("b");
	    	        b.appendChild(TRG_CENTRE_NAME);
	    	        font.appendChild(b);
	    	        font.setAttribute("style", "color:#FFF");
	    	        td.appendChild(font);
	    	        td.setAttribute("align", "right");
	    	        td.setAttribute("colspan", "3");
	    	      //  td.setAttribute("style", "display:none");
	    	        tr.appendChild(td);
	    		  
	    		  var td=document.createElement("td");
	    	        var TRG_CENTRE_NAME = document.createTextNode(total_training);
	    	        var font=document.createElement("font");
	    	        var b=document.createElement("b");
	    	        b.appendChild(TRG_CENTRE_NAME);
	    	        font.appendChild(b);
	    	        font.setAttribute("style", "color:#FFF");
	    	        td.appendChild(font);
	    	        td.setAttribute("align", "center");
	    	        td.setAttribute("colspan", "3");
	    	        tr.appendChild(td);
	    	        
	    	        
//	    	        var td=document.createElement("td");
//	    	        var total_training = document.createTextNode(total_training);
//	    	        var font=document.createElement("font");
//	    	        var b=document.createElement("b");
//	    	        b.appendChild(total_training);
//	    	        font.appendChild(b);
//	    	        font.setAttribute("style", "color:#FFF");
//	    	        td.appendChild(font);
//	    	        td.setAttribute("align", "center");
//	    	      //  td.setAttribute("style", "display:none");
//	    	        tr.appendChild(td);
//	    	        
	    	        
	    	        
	    	        
	    	        
//	    	        var td=document.createElement("td");
//	    	        var total_participants = document.createTextNode("");
//	    	        var font=document.createElement("font");
//	    	        var b=document.createElement("b");
//	    	        b.appendChild(total_participants);
//	    	        font.appendChild(b);
//	    	        font.setAttribute("style", "color:#FFF");
//	    	        td.appendChild(font);
//	    	        td.setAttribute("align", "center");
//	    	      //  td.setAttribute("style", "display:none");
//	    	        tr.appendChild(td);
	    	        
	    	        tr.setAttribute("style", "background-color:#006699");
	    	        
	    	        document.getElementById("vacance_deatils").appendChild(tr);
		    	  
		    	  
		    	  data3.addRows(dataSet3);
			        var options2 = { title : 'Retirement Cadre Wise','width':600,
			                       'height':300,sliceVisibilityThreshold: 0, is3D: true, hAxis: {minValue: 1000}};
			        var chart2 = new google.visualization.PieChart(document.getElementById('vacance_bar_programs'));
			        chart2.draw(data3, options2);
			        
			        
//			        data44.addRows(dataset44);
//			        var options22 = { title : 'Retirement Cadre Wise','width':600,
//			                       'height':300,sliceVisibilityThreshold: 0, is3D: true, hAxis: {minValue: 1000}};
//			        var chart8 = new google.visualization.PieChart(document.getElementById('vacance_bar_participates'));
//			        chart8.draw(data44, options22);
			        
//			         var data44 =google.visualization.arrayToDataTable(dataset44);  
//					 var optionsss = {
//		                     title : 'Total No of Traning Programs', 'height':400,'width':900,
//		                     vAxis: {title: "Share Amount"},
//		                     hAxis: {title: "Office Name"},sliceVisibilityThreshold: 0,
//		                     seriesType: "bars", allowHtml: true
//		                   };
//
//		                   var chartss = new google.visualization.ComboChart(document.getElementById('vacance_bar_participates'));
//		
			        
			      
			        
			        
			        var data44 =google.visualization.arrayToDataTable(dataSet44);  
			         
			        
			         
					 var optionsss = {
		                     title : 'Retirement Cadre Wise', 'height':400,'width':500,
		                     vAxis: {title: "Separate Total"},
		                     hAxis: {title: "Cadre Name"},sliceVisibilityThreshold: 0,
		                     seriesType: "bars", allowHtml: true
		                   };

		                   var chartss = new google.visualization.ComboChart(document.getElementById('vacance_bar_participates'));
		                   chartss.draw(data44, optionsss);
			        
			        function eventHandler()
			        {
			        	var strRegionSno=data3.getValue(chart2.getSelection()[0].row, 2);
			        	var strRegionName = data3.getValue(chart2.getSelection()[0].row, 0);
			        	var year=document.getElementById("year").value;
			        	
			        	
			        	
			        	location.href="Retirement_Cadre_Wise_Sub_Chart.jsp?regionSno="+strRegionSno+"&year="+year+"&regionName="+strRegionName,'TRAINING PROGRAMS CHART';
			        	//openFormInWindow("vacance_report_circle_wise.jsp?regionSno="+strRegionSno+"&regionName="+strRegionName+"&finYear="+getValue("finYear"),'DASHBOARD_SHAREAMT_CHART');
			        }
			        google.visualization.events.addListener(chart2, 'select', eventHandler); 
			        
			        
			        function eventHandler()
			        {
			        	var strRegionSno=data44.getValue(chartss.getSelection()[0].row, 2);
			        	//var strRegionName = data3.getValue(chart2.getSelection()[0].row, 0);
			        	
			        	var year=document.getElementById("year").value;
			        	
			        	
			        	
			        	location.href="Retirement_Cadre_Wise_Sub_Chart.jsp?regionSno="+strRegionSno+"&year="+year,'CADRE WISE RETIREMENT ';
			        	//openFormInWindow("vacance_report_circle_wise.jsp?regionSno="+strRegionSno+"&regionName="+strRegionName+"&finYear="+getValue("finYear"),'DASHBOARD_SHAREAMT_CHART');
			        }
			        google.visualization.events.addListener(chartss, 'select', eventHandler); 
					
		      }
		      
		
	      
	      
	      }
	      
	      if(command=="sub_programs")
	      {


		      if(flag=="success")
		      {
		    	  var count=baseResponse.getElementsByTagName("count")[0].firstChild.nodeValue;
		    	  var tr = document.createElement("TR");
		          tr.id = seq;
		          
		          
		          var total_training=0,total_participants=0;
		          
		          
		            var data3 = new google.visualization.DataTable();
			        data3.addColumn('string', 'Topping');
			        data3.addColumn('number', 'Slices');
			        data3.addColumn({type: 'string', role: 'tooltip'});
			        var dataSet3= new Array();
			        
			        var data44 = new google.visualization.DataTable();
			        data44.addColumn('string', 'Topping');
			        data44.addColumn('number', 'Slices');
			        data44.addColumn({type: 'string', role: 'tooltip'});
			        var dataset44=new Array();
		          
			        //dataset44.push(['Training Centre', '', '']);
		    	  for(var i=0;i<count;i++)
		    	  {
		    		  var PROG_TITLE_NAME=baseResponse.getElementsByTagName("PROG_TITLE_NAME")[i].firstChild.nodeValue;
		    		  
		    		  var tot_no_training=baseResponse.getElementsByTagName("tot_no_training")[i].firstChild.nodeValue;
		    		  
		    		  var TRG_CENTRE_NAME1=baseResponse.getElementsByTagName("TRG_CENTRE_NAME")[i].firstChild.nodeValue;
		    		  
		    		  var VENUE_TRG_CENTRE_ID=baseResponse.getElementsByTagName("VENUE_TRG_CENTRE_ID")[i].firstChild.nodeValue;
		    		  
		    		  var tot_emp=baseResponse.getElementsByTagName("tot_emp")[i].firstChild.nodeValue;
		    		 
//		    		  var SANCTIONED_NO_OF_POSTS=baseResponse.getElementsByTagName("SANCTIONED_NO_OF_POSTS")[i].firstChild.nodeValue;
//		    		  var FILLEDUP_POSTS=baseResponse.getElementsByTagName("FILLEDUP_POSTS")[i].firstChild.nodeValue;
//		    		  var DIVERSION_TO_OTHER=baseResponse.getElementsByTagName("DIVERSION_TO_OTHER")[i].firstChild.nodeValue;
//		    		  var DIVERSION_FROM_OTHER=baseResponse.getElementsByTagName("DIVERSION_FROM_OTHER")[i].firstChild.nodeValue;
//		    		  var vacant_posts=baseResponse.getElementsByTagName("vacant_posts")[i].firstChild.nodeValue;
		    		  
		    	
		    		  total_training=parseInt(total_training)+parseInt(tot_no_training);
		    		  total_participants=parseInt(total_participants)+parseInt(tot_emp);
		    		  
		    		  
		    		 // dataSet3.push([region_office_id,parseInt(SANCTIONED_NO_OF_POSTS),parseInt(FILLEDUP_POSTS),parseInt(DIVERSION_TO_OTHER),parseInt(DIVERSION_FROM_OTHER),parseInt(vacant_posts)]);
		    		  dataSet3.push([PROG_TITLE_NAME,parseInt(tot_no_training),VENUE_TRG_CENTRE_ID]);
		    		  dataset44.push([PROG_TITLE_NAME,parseInt(tot_emp),VENUE_TRG_CENTRE_ID]);
		    		  
		    		  var tr=document.createElement("tr");
		    		  
		    		  var td=document.createElement("td");
		    	        var TRG_CENTRE_NAME = document.createTextNode(PROG_TITLE_NAME);
		    	        td.appendChild(TRG_CENTRE_NAME);
		    	        td.setAttribute("align", "left");
		    	      //  td.setAttribute("style", "display:none");
		    	        tr.appendChild(td);
		    		  
		    		    var td=document.createElement("td");	    		  
		    		    var TOT_NO_PROGRAMMES = document.createTextNode(tot_no_training);
		    	        td.appendChild(TOT_NO_PROGRAMMES);
		    	        td.setAttribute("align", "center");
		    	      //  td.setAttribute("style", "display:none");
		    	        tr.appendChild(td);
		    	        
		    	        
		    	        var td=document.createElement("td");	    		  
		    		    var VENUE_TRG_CENTRE_ID = document.createTextNode(TRG_CENTRE_NAME1);
		    	        td.appendChild(VENUE_TRG_CENTRE_ID);
		    	        td.setAttribute("align", "left");
		    	      //  td.setAttribute("style", "display:none");
		    	        tr.appendChild(td);
		    	        
		    	        var td=document.createElement("td");
		    	        var SANCTIONED_NO_OF_POSTS = document.createTextNode(tot_emp);
		    	        td.appendChild(SANCTIONED_NO_OF_POSTS);
		    	        td.setAttribute("align", "center");
		    	      //  td.setAttribute("style", "display:none");
		    	        tr.appendChild(td);
		    	        
		    	        
		    	        
		    	        if(i%2==0)
			    		  {
			    			  
			    		  }
			    		  else
			    		  {
			    			  tr.setAttribute("style", "background-color:lightblue;");
			    		  }
//		    	        
//		    	        var td=document.createElement("td");
//		    	        var FILLEDUP_POSTS = document.createTextNode(FILLEDUP_POSTS);
//		    	        td.appendChild(FILLEDUP_POSTS);
//		    	      //  td.setAttribute("style", "display:none");
//		    	        tr.appendChild(td);
//		    	        
//		    	        var td=document.createElement("td");
//		    	        var DIVERSION_TO_OTHER = document.createTextNode(DIVERSION_TO_OTHER);
//		    	        td.appendChild(DIVERSION_TO_OTHER);
//		    	      //  td.setAttribute("style", "display:none");
//		    	        tr.appendChild(td);
//		    	        
//		    	        var td=document.createElement("td");
//		    	        var DIVERSION_FROM_OTHER = document.createTextNode(DIVERSION_FROM_OTHER);
//		    	        td.appendChild(DIVERSION_FROM_OTHER);
//		    	      //  td.setAttribute("style", "display:none");
//		    	        tr.appendChild(td);
//		    	        
//		    	        var td=document.createElement("td");
//		    	        var vacant_posts = document.createTextNode(vacant_posts);
//		    	        td.appendChild(vacant_posts);
//		    	      //  td.setAttribute("style", "display:none");
//		    	        tr.appendChild(td);
		    	        
		    	        document.getElementById("vacance_deatils").appendChild(tr);
		    		  
		    		  
		    		  
		    	  }
		    	  
		    	  
		    	  var tr=document.createElement("tr");
	    		  
	    		  var td=document.createElement("td");
	    	        var TRG_CENTRE_NAME = document.createTextNode("TOTAL");
	    	        var font=document.createElement("font");
	    	        var b=document.createElement("b");
	    	        b.appendChild(TRG_CENTRE_NAME);
	    	        font.appendChild(b);
	    	        font.setAttribute("style", "color:#FFF");
	    	        td.appendChild(font);
	    	        td.setAttribute("align", "right");
	    	        tr.appendChild(td);
	    	        
	    	        
	    	        var td=document.createElement("td");
	    	        var total_training = document.createTextNode(total_training);
	    	        var font=document.createElement("font");
	    	        var b=document.createElement("b");
	    	        b.appendChild(total_training);
	    	        font.appendChild(b);
	    	        font.setAttribute("style", "color:#FFF");
	    	        td.appendChild(font);
	    	        td.setAttribute("align", "center");
	    	      //  td.setAttribute("style", "display:none");
	    	        tr.appendChild(td);
	    	        
	    	        
	    	        var td=document.createElement("td");
	    	        var TRG_CENTRE_NAME = document.createTextNode("TOTAL");
	    	        var font=document.createElement("font");
	    	        var b=document.createElement("b");
	    	        b.appendChild(TRG_CENTRE_NAME);
	    	        font.appendChild(b);
	    	        font.setAttribute("style", "color:#FFF");
	    	        td.appendChild(font);
	    	        td.setAttribute("align", "right");
	    	      //  td.setAttribute("style", "display:none");
	    	        tr.appendChild(td);
	    	        
	    	        
	    	        var td=document.createElement("td");
	    	        var total_participants = document.createTextNode(total_participants);
	    	        var font=document.createElement("font");
	    	        var b=document.createElement("b");
	    	        b.appendChild(total_participants);
	    	        font.appendChild(b);
	    	        font.setAttribute("style", "color:#FFF");
	    	        td.appendChild(font);
	    	        td.setAttribute("align", "center");
	    	      //  td.setAttribute("style", "display:none");
	    	        tr.appendChild(td);
	    	        
	    	        tr.setAttribute("style", "background-color:#006699");
	    	        
	    	        document.getElementById("vacance_deatils").appendChild(tr);
		    	  
		    	  
		    	  data3.addRows(dataSet3);
			        var options2 = { title : 'Total No of Training Programmes','width':600,
			                       'height':300,sliceVisibilityThreshold: 0, is3D: true, hAxis: {minValue: 1000}};
			        var chart2 = new google.visualization.PieChart(document.getElementById('vacance_bar_programs'));
			        chart2.draw(data3, options2);
			        
			        
			        data44.addRows(dataset44);
			        var options22 = { title : 'Total No of Participants','width':600,
			                       'height':300,sliceVisibilityThreshold: 0, is3D: true, hAxis: {minValue: 1000}};
			        var chart8 = new google.visualization.PieChart(document.getElementById('vacance_bar_participates'));
			        chart8.draw(data44, options22);
			        
			        
//			         var data44 =google.visualization.arrayToDataTable(dataset44);  
//					 var optionsss = {
//		                     title : 'Total No of Traning Programs', 'height':400,'width':900,
//		                     vAxis: {title: "Share Amount"},
//		                     hAxis: {title: "Office Name"},sliceVisibilityThreshold: 0,
//		                     seriesType: "bars", allowHtml: true
//		                   };
//
//		                   var chartss = new google.visualization.ComboChart(document.getElementById('vacance_bar_participates'));
//		                   chartss.draw(data44, optionsss);
			        
			        function eventHandler()
			        {
			        	var strRegionSno=data3.getValue(chart2.getSelection()[0].row, 2);
			        	var strRegionName = data3.getValue(chart2.getSelection()[0].row, 0);
			        	var year=document.getElementById("year").value;
			        	
			        	
			        	
			        	location.href="Training_Chart_Sub_Programs.jsp?regionSno="+strRegionSno+"&year="+year+"&regionName="+strRegionName,'TRAINING PROGRAMS CHART';
			        	//openFormInWindow("vacance_report_circle_wise.jsp?regionSno="+strRegionSno+"&regionName="+strRegionName+"&finYear="+getValue("finYear"),'DASHBOARD_SHAREAMT_CHART');
			        }
			        google.visualization.events.addListener(chart2, 'select', eventHandler); 
					
		      }
		      
		
	      
	      }
	      
	      if(command=="training")
	      {

		      if(flag=="success")
		      {
		    	  var count=baseResponse.getElementsByTagName("count")[0].firstChild.nodeValue;
		    	  var tr = document.createElement("TR");
		          tr.id = seq;
		          
		          
		          var sub_training=0,sub_participants=0;
		          
		            var data3 = new google.visualization.DataTable();
			        data3.addColumn('string', 'Topping');
			        data3.addColumn('number', 'Slices');
			        data3.addColumn({type: 'string', role: 'tooltip'});
			        var dataSet3= new Array();
			        
			        
			        var data4 = new google.visualization.DataTable();
			        data4.addColumn('string', 'Topping');
			        data4.addColumn('number', 'Slices');
			        data4.addColumn({type: 'string', role: 'tooltip'});
			        var dataSet4= new Array();
			        
			        
			        var dataset44=new Array();
		          
			        //dataset44.push(['Training Centre', '', '']);
		    	  for(var i=0;i<count;i++)
		    	  {
		    		  var TRG_CENTRE_NAME=baseResponse.getElementsByTagName("TRG_CENTRE_NAME")[i].firstChild.nodeValue;
		    		  var VENUE_TRG_CENTRE_ID=baseResponse.getElementsByTagName("VENUE_TRG_CENTRE_ID")[i].firstChild.nodeValue;
		    		  var TOT_NO_PROGRAMMES=baseResponse.getElementsByTagName("TOT_NO_PROGRAMMES")[i].firstChild.nodeValue;
		    		  var year=baseResponse.getElementsByTagName("Year")[i].firstChild.nodeValue;
		    		  var total_no_participated=baseResponse.getElementsByTagName("total_emp_trained")[i].firstChild.nodeValue;
//		    		  var SANCTIONED_NO_OF_POSTS=baseResponse.getElementsByTagName("SANCTIONED_NO_OF_POSTS")[i].firstChild.nodeValue;
//		    		  var FILLEDUP_POSTS=baseResponse.getElementsByTagName("FILLEDUP_POSTS")[i].firstChild.nodeValue;
//		    		  var DIVERSION_TO_OTHER=baseResponse.getElementsByTagName("DIVERSION_TO_OTHER")[i].firstChild.nodeValue;
//		    		  var DIVERSION_FROM_OTHER=baseResponse.getElementsByTagName("DIVERSION_FROM_OTHER")[i].firstChild.nodeValue;
//		    		  var vacant_posts=baseResponse.getElementsByTagName("vacant_posts")[i].firstChild.nodeValue;
		    		  
		    		  sub_training=parseInt(sub_training)+parseInt(TOT_NO_PROGRAMMES);
		    		  sub_participants=parseInt(sub_participants)+parseInt(total_no_participated);
		    		  
		    		  //PIE CHART......
		    		 // dataSet3.push([region_office_id,parseInt(SANCTIONED_NO_OF_POSTS),parseInt(FILLEDUP_POSTS),parseInt(DIVERSION_TO_OTHER),parseInt(DIVERSION_FROM_OTHER),parseInt(vacant_posts)]);
		    		  dataSet3.push([TRG_CENTRE_NAME,parseInt(TOT_NO_PROGRAMMES),VENUE_TRG_CENTRE_ID]);
		    		  dataSet4.push([TRG_CENTRE_NAME,parseInt(total_no_participated),VENUE_TRG_CENTRE_ID]);
		    		  
		    		  
		    		  // BAR CHART......
		    		 // dataset44.push([TRG_CENTRE_NAME,parseInt(TOT_NO_PROGRAMMES),parseInt(VENUE_TRG_CENTRE_ID)]);
		    		  
		    		  training_centre_id[i]=VENUE_TRG_CENTRE_ID;
		    		  
		    		  
		    		  
		    		  

		    	        
		    	       
		    		  var tr=document.createElement("tr");
		    		  
		    		    var td=document.createElement("td");
		    	        var TRG_CENTRE_NAME = document.createTextNode(TRG_CENTRE_NAME);
		    	        var anc = document.createElement("A");
		    	        var url = "javascript:loadvalue('" + i + "')";
	    	            anc.href = url;
	    	            anc.appendChild(TRG_CENTRE_NAME);
		    	        //TRG_CENTRE_NAME.link("Training_Chart_Sub_Programs.jsp?regionSno="+strRegionSno+"&year="+year+"&regionName="+strRegionName);
		    	        td.appendChild(anc);
		    	        td.setAttribute("align", "left");
		    	      //  td.setAttribute("style", "display:none");
		    	        tr.appendChild(td);
		    		  
		    		    var td=document.createElement("td");	    		  
		    		    var TOT_NO_PROGRAMMES = document.createTextNode(TOT_NO_PROGRAMMES);
		    	        td.appendChild(TOT_NO_PROGRAMMES);
		    	        td.setAttribute("align", "center");
		    	      //  td.setAttribute("style", "display:none");
		    	        tr.appendChild(td);
		    	        
		    	        
		    	        var td=document.createElement("td");	    		  
		    		    var VENUE_TRG_CENTRE_ID = document.createTextNode(VENUE_TRG_CENTRE_ID);
		    	        td.appendChild(VENUE_TRG_CENTRE_ID);
		    	        td.setAttribute("align", "left");
		    	      //  td.setAttribute("style", "display:none");
		    	        tr.appendChild(td);
		    	        
		    	        var td=document.createElement("td");
		    	        var total_emp_trained = document.createTextNode(total_no_participated);
		    	        td.appendChild(total_emp_trained);
		    	        td.setAttribute("align", "center");
		    	      //  td.setAttribute("style", "display:none");
		    	        tr.appendChild(td);
//		    	        
		    		  
		    		  if(i%2==0)
		    		  {
		    			  
		    		  }
		    		  else
		    		  {
		    			  tr.setAttribute("style", "background-color:#B2D1E0");
		    		  }
		    		  
		    		  
		    		  
		    		
//		    	        var td=document.createElement("td");
//		    	        var FILLEDUP_POSTS = document.createTextNode(FILLEDUP_POSTS);
//		    	        td.appendChild(FILLEDUP_POSTS);
//		    	      //  td.setAttribute("style", "display:none");
//		    	        tr.appendChild(td);
//		    	        
//		    	        var td=document.createElement("td");
//		    	        var DIVERSION_TO_OTHER = document.createTextNode(DIVERSION_TO_OTHER);
//		    	        td.appendChild(DIVERSION_TO_OTHER);
//		    	      //  td.setAttribute("style", "display:none");
//		    	        tr.appendChild(td);
//		    	        
//		    	        var td=document.createElement("td");
//		    	        var DIVERSION_FROM_OTHER = document.createTextNode(DIVERSION_FROM_OTHER);
//		    	        td.appendChild(DIVERSION_FROM_OTHER);
//		    	      //  td.setAttribute("style", "display:none");
//		    	        tr.appendChild(td);
//		    	        
//		    	        var td=document.createElement("td");
//		    	        var vacant_posts = document.createTextNode(vacant_posts);
//		    	        td.appendChild(vacant_posts);
//		    	      //  td.setAttribute("style", "display:none");
//		    	        tr.appendChild(td);
		    	        
		    	        document.getElementById("vacance_deatils").appendChild(tr);
		    		  
		    		  
		    		  
		    	  }
		    	  
		    	  
		    	  
		    	  
		    	  var tr=document.createElement("tr");
	    		  
	    		  var td=document.createElement("td");
	    	        var TRG_CENTRE_NAME = document.createTextNode("TOTAL");
	    	        var font=document.createElement("font");
	    	        var b=document.createElement("b");
	    	        b.appendChild(TRG_CENTRE_NAME);
	    	        font.appendChild(b);
	    	        font.setAttribute("style", "color:#FFF");
	    	        td.appendChild(font);
	    	        td.setAttribute("align", "right");
	    	      //  td.setAttribute("style", "display:none");
	    	        tr.appendChild(td);
	    	        
	    	        
	    	        var td=document.createElement("td");
	    	        var sub_training = document.createTextNode(sub_training);
	    	        var font=document.createElement("font");
	    	        var b=document.createElement("b");
	    	        b.appendChild(sub_training);
	    	        font.appendChild(b);
	    	        
	    	        font.setAttribute("style", "color:#FFF");
	    	        td.appendChild(font);
	    	        td.setAttribute("align", "center");
	    	      //  td.setAttribute("style", "display:none");
	    	        tr.appendChild(td);
	    	        
	    	        
	    	        var td=document.createElement("td");
	    	        var TRG_CENTRE_NAME = document.createTextNode("TOTAL");
	    	        var font=document.createElement("font");
	    	        var b=document.createElement("b");
	    	        b.appendChild(TRG_CENTRE_NAME);
	    	        font.appendChild(b);
	    	        font.setAttribute("style", "color:#FFF");
	    	        td.appendChild(font);
	    	        td.setAttribute("align", "right");
	    	      //  td.setAttribute("style", "display:none");
	    	        tr.appendChild(td);
	    	        
	    	        
	    	        var td=document.createElement("td");
	    	        var sub_participants = document.createTextNode(sub_participants);
	    	        var font=document.createElement("font");
	    	        var b=document.createElement("b");
	    	        b.appendChild(sub_participants);
	    	        font.appendChild(b);
	    	        font.setAttribute("style", "color:#FFF");
	    	        //font.setAttribute("size", "6");
	    	        td.appendChild(font);
	    	        td.setAttribute("align", "center");
	    	      //  td.setAttribute("style", "display:none");
	    	        tr.appendChild(td);
	    	        
	    	        tr.setAttribute("style", "background-color:#006699");
	    	        
	    	        document.getElementById("vacance_deatils").appendChild(tr);
		    	  
		    	  data3.addRows(dataSet3);
			        var options2 = { title : 'Total No of Training Programmes','width':600,
			                       'height':300,sliceVisibilityThreshold: 0, is3D: true, hAxis: {minValue: 1000}};
			        var chart2 = new google.visualization.PieChart(document.getElementById('vacance_bar_programs'));
			        chart2.draw(data3, options2);
			        
			        
			        
			        data4.addRows(dataSet4);
			        var options5 = { title : 'Total No of Participants','width':600,
			                       'height':300,sliceVisibilityThreshold: 0, is3D: true, hAxis: {minValue: 1000}};
			        var chart5 = new google.visualization.PieChart(document.getElementById('vacance_bar_participates'));
			        chart5.draw(data4, options5);
			        
			        
			        //  PIE CHART COMMAND..............
			        
//			         var data44 =google.visualization.arrayToDataTable(dataset44);  
//					 var optionsss = {
//		                     title : 'Total No of Traning Programs', 'height':400,'width':900,
//		                     vAxis: {title: "Share Amount"},
//		                     hAxis: {title: "Office Name"},sliceVisibilityThreshold: 0,
//		                     seriesType: "bars", allowHtml: true
//		                   };
//
//		                   var chartss = new google.visualization.ComboChart(document.getElementById('vacance_bar_participates'));
//		                   chartss.draw(data44, optionsss);
			        
			        function eventHandler()
			        {
			        	var strRegionSno=data3.getValue(chart2.getSelection()[0].row, 2);
			        	var strRegionName = data3.getValue(chart2.getSelection()[0].row, 0);
			        	var year=document.getElementById("year").value;
			        	
			        	
			        	
			        	
			        	
			        	location.href="Training_Chart_Sub_Programs.jsp?regionSno="+strRegionSno+"&year="+year+"&regionName="+strRegionName,'TRAINING PROGRAMS CHART';
			        	//openFormInWindow("vacance_report_circle_wise.jsp?regionSno="+strRegionSno+"&regionName="+strRegionName+"&finYear="+getValue("finYear"),'DASHBOARD_SHAREAMT_CHART');
			        }
			        google.visualization.events.addListener(chart2, 'select', eventHandler); 
			        
			        function eventHandler()
			        {
			        	var year=document.getElementById("year").value;
			        	var centre_ID=data4.getValue(chart5.getSelection()[0].row, 2);
			        	alert(year +" is "+ centre_ID);
			        	location.href="Training_Chart_Sub_Programs.jsp?regionSno="+centre_ID+"&year="+year+"&centre_ID="+centre_ID,'TRAINING PROGRAMS CHART';
			        	//openFormInWindow("vacance_report_circle_wise.jsp?regionSno="+strRegionSno+"&regionName="+strRegionName+"&finYear="+getValue("finYear"),'DASHBOARD_SHAREAMT_CHART');
			        }
			        google.visualization.events.addListener(chart5, 'select', eventHandler); 
					
		      }
		      
		
	      }
	      
	      if(command=="vacants")
		  {
		      if(flag=="success")
		      {
		    	  var count=baseResponse.getElementsByTagName("count")[0].firstChild.nodeValue;
		    	  var tr = document.createElement("TR");
		          tr.id = seq;
		          
		          
		            var data3 = new google.visualization.DataTable();
			        data3.addColumn('string', 'Topping');
			        data3.addColumn('number', 'Slices');
			        data3.addColumn({type: 'string', role: 'tooltip'});
			        var dataSet3= new Array();
			        
			        
			        var dataset44=new Array();
		          
			        dataset44.push(['REGION OFFICE', 'sanctioned', 'office_id']);
		    	  for(var i=0;i<count;i++)
		    	  {
		    		  var office_name=baseResponse.getElementsByTagName("office_name")[i].firstChild.nodeValue;
		    		  var region_office_id=baseResponse.getElementsByTagName("region_office_id")[i].firstChild.nodeValue;
		    		  var SANCTIONED_NO_OF_POSTS=baseResponse.getElementsByTagName("SANCTIONED_NO_OF_POSTS")[i].firstChild.nodeValue;
		    		  var FILLEDUP_POSTS=baseResponse.getElementsByTagName("FILLEDUP_POSTS")[i].firstChild.nodeValue;
		    		  var DIVERSION_TO_OTHER=baseResponse.getElementsByTagName("DIVERSION_TO_OTHER")[i].firstChild.nodeValue;
		    		  var DIVERSION_FROM_OTHER=baseResponse.getElementsByTagName("DIVERSION_FROM_OTHER")[i].firstChild.nodeValue;
		    		  var vacant_posts=baseResponse.getElementsByTagName("vacant_posts")[i].firstChild.nodeValue;
		    		  
		    		  
		    		  
		    		  
		    		  
		    		 // dataSet3.push([region_office_id,parseInt(SANCTIONED_NO_OF_POSTS),parseInt(FILLEDUP_POSTS),parseInt(DIVERSION_TO_OTHER),parseInt(DIVERSION_FROM_OTHER),parseInt(vacant_posts)]);
		    		  dataSet3.push([office_name,parseInt(SANCTIONED_NO_OF_POSTS),region_office_id]);
		    		  dataset44.push([office_name,parseInt(SANCTIONED_NO_OF_POSTS),parseInt(region_office_id)]);
		    		  
		    		  var tr=document.createElement("tr");
		    		  
		    		  var td=document.createElement("td");	    		  
		    		    var office_name = document.createTextNode(office_name);
		    	        td.appendChild(office_name);
		    	      //  td.setAttribute("style", "display:none");
		    	        tr.appendChild(td);
		    		  
		    		    var td=document.createElement("td");	    		  
		    		    var region_office_id = document.createTextNode(region_office_id);
		    	        td.appendChild(region_office_id);
		    	      //  td.setAttribute("style", "display:none");
		    	        tr.appendChild(td);
		    	        
		    	        var td=document.createElement("td");
		    	        var SANCTIONED_NO_OF_POSTS = document.createTextNode(SANCTIONED_NO_OF_POSTS);
		    	        td.appendChild(SANCTIONED_NO_OF_POSTS);
		    	      //  td.setAttribute("style", "display:none");
		    	        tr.appendChild(td);
		    	        
		    	        var td=document.createElement("td");
		    	        var FILLEDUP_POSTS = document.createTextNode(FILLEDUP_POSTS);
		    	        td.appendChild(FILLEDUP_POSTS);
		    	      //  td.setAttribute("style", "display:none");
		    	        tr.appendChild(td);
		    	        
		    	        var td=document.createElement("td");
		    	        var DIVERSION_TO_OTHER = document.createTextNode(DIVERSION_TO_OTHER);
		    	        td.appendChild(DIVERSION_TO_OTHER);
		    	      //  td.setAttribute("style", "display:none");
		    	        tr.appendChild(td);
		    	        
		    	        var td=document.createElement("td");
		    	        var DIVERSION_FROM_OTHER = document.createTextNode(DIVERSION_FROM_OTHER);
		    	        td.appendChild(DIVERSION_FROM_OTHER);
		    	      //  td.setAttribute("style", "display:none");
		    	        tr.appendChild(td);
		    	        
		    	        var td=document.createElement("td");
		    	        var vacant_posts = document.createTextNode(vacant_posts);
		    	        td.appendChild(vacant_posts);
		    	      //  td.setAttribute("style", "display:none");
		    	        tr.appendChild(td);
		    	        
		    	        document.getElementById("vacance_deatils").appendChild(tr);
		    		  
		    		  
		    		  
		    	  }
		    	  
		    	  data3.addRows(dataSet3);
			        var options2 = { title : 'Region Wise Vacancy Total','width':600,
			                       'height':300,sliceVisibilityThreshold: 0, is3D: true, hAxis: {minValue: 1000}};
			        var chart2 = new google.visualization.PieChart(document.getElementById('vacance_pi'));
			        chart2.draw(data3, options2);
			        
			        
			         var data44 =google.visualization.arrayToDataTable(dataset44);  
					 var optionsss = {
		                     title : 'Region Wise Fund Program Wise Share Amount Total', 'height':400,'width':900,
		                     vAxis: {title: "Share Amount"},
		                     hAxis: {title: "Office Name"},sliceVisibilityThreshold: 0,
		                     seriesType: "bars", allowHtml: true
		                   };

		                   var chartss = new google.visualization.ComboChart(document.getElementById('vacance_bar'));
		                   chartss.draw(data44, optionsss);
			        
			        function eventHandler()
			        {
			        	var strRegionSno=data3.getValue(chart2.getSelection()[0].row, 2);
			        	var strRegionName = data3.getValue(chart2.getSelection()[0].row, 0);
			        	
			        	location.href="vacance_report_circle_wise.jsp?regionSno="+strRegionSno+"&regionName="+strRegionName,'DASHBOARD_SHAREAMT_CHART';
			        	//openFormInWindow("vacance_report_circle_wise.jsp?regionSno="+strRegionSno+"&regionName="+strRegionName+"&finYear="+getValue("finYear"),'DASHBOARD_SHAREAMT_CHART');
			        }
			        google.visualization.events.addListener(chart2, 'select', eventHandler); 
					
		      }
		      
		}
	      
	      else if(command=="vacants_circle_wise")
	      {

		      if(flag=="success")
		      {
		    	  var count=baseResponse.getElementsByTagName("count")[0].firstChild.nodeValue;
		    	  var tr = document.createElement("TR");
		          tr.id = seq;
		          
		          
		            var data3 = new google.visualization.DataTable();
			        data3.addColumn('string', 'Topping');
			        data3.addColumn('number', 'Slices');
			        data3.addColumn({type: 'string', role: 'tooltip'});
			        var dataSet3= new Array();
		          
		    	  
		    	  for(var i=0;i<count;i++)
		    	  {
		    		  var office_name=baseResponse.getElementsByTagName("office_name")[i].firstChild.nodeValue;
		    		  var region_office_id=baseResponse.getElementsByTagName("region_office_id")[i].firstChild.nodeValue;
		    		  var SANCTIONED_NO_OF_POSTS=baseResponse.getElementsByTagName("SANCTIONED_NO_OF_POSTS")[i].firstChild.nodeValue;
		    		  var FILLEDUP_POSTS=baseResponse.getElementsByTagName("FILLEDUP_POSTS")[i].firstChild.nodeValue;
		    		  var DIVERSION_TO_OTHER=baseResponse.getElementsByTagName("DIVERSION_TO_OTHER")[i].firstChild.nodeValue;
		    		  var DIVERSION_FROM_OTHER=baseResponse.getElementsByTagName("DIVERSION_FROM_OTHER")[i].firstChild.nodeValue;
		    		  var vacant_posts=baseResponse.getElementsByTagName("vacant_posts")[i].firstChild.nodeValue;
		    		  
		    		  
		    		  
		    		  
		    		  
		    		 // dataSet3.push([region_office_id,parseInt(SANCTIONED_NO_OF_POSTS),parseInt(FILLEDUP_POSTS),parseInt(DIVERSION_TO_OTHER),parseInt(DIVERSION_FROM_OTHER),parseInt(vacant_posts)]);
		    		  dataSet3.push([office_name,parseInt(SANCTIONED_NO_OF_POSTS),region_office_id]);
		    		  //dataSet3.push([region_office_id,parseInt(FILLEDUP_POSTS),region_office_id]);
		    		  
		    		  var tr=document.createElement("tr");
		    		  
		    		  var td=document.createElement("td");	    		  
		    		    var office_name = document.createTextNode(office_name);
		    	        td.appendChild(office_name);
		    	      //  td.setAttribute("style", "display:none");
		    	        tr.appendChild(td);
		    		  
		    		    var td=document.createElement("td");	    		  
		    		    var region_office_id = document.createTextNode(region_office_id);
		    	        td.appendChild(region_office_id);
		    	      //  td.setAttribute("style", "display:none");
		    	        tr.appendChild(td);
		    	        
		    	        var td=document.createElement("td");
		    	        var SANCTIONED_NO_OF_POSTS = document.createTextNode(SANCTIONED_NO_OF_POSTS);
		    	        td.appendChild(SANCTIONED_NO_OF_POSTS);
		    	      //  td.setAttribute("style", "display:none");
		    	        tr.appendChild(td);
		    	        
		    	        var td=document.createElement("td");
		    	        var FILLEDUP_POSTS = document.createTextNode(FILLEDUP_POSTS);
		    	        td.appendChild(FILLEDUP_POSTS);
		    	      //  td.setAttribute("style", "display:none");
		    	        tr.appendChild(td);
		    	        
		    	        var td=document.createElement("td");
		    	        var DIVERSION_TO_OTHER = document.createTextNode(DIVERSION_TO_OTHER);
		    	        td.appendChild(DIVERSION_TO_OTHER);
		    	      //  td.setAttribute("style", "display:none");
		    	        tr.appendChild(td);
		    	        
		    	        var td=document.createElement("td");
		    	        var DIVERSION_FROM_OTHER = document.createTextNode(DIVERSION_FROM_OTHER);
		    	        td.appendChild(DIVERSION_FROM_OTHER);
		    	      //  td.setAttribute("style", "display:none");
		    	        tr.appendChild(td);
		    	        
		    	        var td=document.createElement("td");
		    	        var vacant_posts = document.createTextNode(vacant_posts);
		    	        td.appendChild(vacant_posts);
		    	      //  td.setAttribute("style", "display:none");
		    	        tr.appendChild(td);
		    	        
		    	        document.getElementById("vacance_deatils").appendChild(tr);
		    		  
		    		  
		    		  
		    	  }
		    	  
		    	  data3.addRows(dataSet3);
			        var options2 = { title : 'Region Wise Vacancy Total','width':600,
			                       'height':300,sliceVisibilityThreshold: 0, is3D: true, hAxis: {minValue: 1000}};
			        var chart2 = new google.visualization.PieChart(document.getElementById('vacance_pi'));
			        chart2.draw(data3, options2);
			        
			        function eventHandler()
			        {
			        	var strRegionSno=data3.getValue(chart2.getSelection()[0].row, 2);
			        	var strRegionName = data3.getValue(chart2.getSelection()[0].row, 0);
			        	
			        	location.href="vacance_report_circle_wise.jsp?regionSno="+strRegionSno+"&regionName="+strRegionName,'DASHBOARD_SHAREAMT_CHART';
			        	//openFormInWindow("vacance_report_circle_wise.jsp?regionSno="+strRegionSno+"&regionName="+strRegionName+"&finYear="+getValue("finYear"),'DASHBOARD_SHAREAMT_CHART');
			        }
			        google.visualization.events.addListener(chart2, 'select', eventHandler); 
					
		      }
		      
		
	      }
	}
}
}



function changepage()
{
	
    var tlist = document.getElementById("grid");
    try {
        tlist.innerHTML = "";
    } catch (e) {
        tlist.innerText = "";
    }
    if(baseResponse.getElementsByTagName("flag")[0].firstChild.nodeValue=="success")
    {
    var len = baseResponse.getElementsByTagName("Total").length;

    var pageno = document.getElementById("cmbpage").value;

    var ul = 0, ll = 0;

    ul = pageno * pagesize;
    ll = ul - pagesize;
    
    
    
    
 // Pie Chart - Rgn  Wise
	var data1 = new google.visualization.DataTable();
    data1.addColumn('string', 'Topping');
    data1.addColumn('number', 'Slices');
    data1.addColumn({type: 'string', role: 'tooltip'});
    var dataSet1= new Array();
    
    
    
    
    

    for ( var i = 0; i < 6; i++) 
    {

   
        dataSet1.push(['REGION_OFFICE_NAME',parseInt(4),2]);
        
    }
    data1.addRows(dataSet1);
    var options1 = { title : 'Region Wise Share Amount Total','width':600,
                   'height':300,sliceVisibilityThreshold: 0, is3D: true, hAxis: {minValue: 1000}};
    var chart1 = new google.visualization.PieChart(document.getElementById('chart_div1'));
    chart1.draw(data1, options1);
    }
}
function Add()
{
	
	var val=nullcheck();
	if(val==true )
	{
    var xmlrequest= AjaxFunction();
    var audit_type=document.getElementById("audit_type").value;
    var office_id_audited=document.getElementById("office_id_audited").value;
    var Audit_Period_cover_frm=document.getElementById("Audit_Period_cover_frm").value;
    var Audit_Period_Cover_To=document.getElementById("Audit_Period_Cover_To").value;
    //var Audit_conduct_frm=document.getElementById("Audit_conduct_frm").value;
    //var Audit_conduct_to=document.getElementById("Audit_conduct_to").value;
    var Comm_Date=document.getElementById("Comm_Date").value;
    var Comp_Date=document.getElementById("Comp_Date").value;
    var Dis_Date=document.getElementById("Dis_Date").value;
    var Repo_Date=document.getElementById("Repo_Date").value;
    var Remarks=document.getElementById("Remarks").value;
   
    var mom_loss;
    if(document.audit_party_details.mom_loss[0].checked==true)
        {
    	mom_loss="Y";
        }
     else
        {
    	mom_loss="N";
        }
    var res_fix="";
    if(document.audit_party_details.res_fix[0].checked==true)
    {
    	res_fix="Y";
    }
   else
    {
	res_fix="N";
    }
    
    if(document.getElementById("annual").checked==true)
    {
    	var inspection=document.getElementById("annual").value;
    }
    else if(document.getElementById("spl").checked==true)
    {
    	var inspection=document.getElementById("spl").value;
    }
    
        var url="../../../../Gpf_create_Party_details?command=Add&audit_type="+audit_type+"&office_id_audited="+office_id_audited+"&Audit_Period_cover_frm="+Audit_Period_cover_frm+"&Audit_Period_Cover_To="+Audit_Period_Cover_To+"&Comm_Date="+Comm_Date+"&Comp_Date="+Comp_Date+"&Dis_Date="+Dis_Date+"&Repo_Date="+Repo_Date+"&mom_loss="+mom_loss+"&res_fix="+res_fix+"&Remarks="+Remarks+"&inspection="+inspection;
        
            xmlrequest.open("GET",url,true);              
            xmlrequest.onreadystatechange=function()
                {
            	handleResponse(xmlrequest);
                }
            ///call the date function here
            xmlrequest.send(null);
	}
}
function Updation()
{
	var val=nullcheck();
	if(val==true)
	{
    var xmlrequest= AjaxFunction();
    var audit_type=document.getElementById("audit_type").value;
    var cnt=document.getElementById("audit_id").value;
    var office_id_audited=document.getElementById("office_id_audited").value;
    var Audit_Period_cover_frm=document.getElementById("Audit_Period_cover_frm").value;
    var Audit_Period_Cover_To=document.getElementById("Audit_Period_Cover_To").value;
    //var Audit_conduct_frm=document.getElementById("Audit_conduct_frm").value;
    //var Audit_conduct_to=document.getElementById("Audit_conduct_to").value;
    var Comm_Date=document.getElementById("Comm_Date").value;
    var Comp_Date=document.getElementById("Comp_Date").value;
    var Dis_Date=document.getElementById("Dis_Date").value;
    var Repo_Date=document.getElementById("Repo_Date").value;
    var Remarks=document.getElementById("Remarks").value;
   
    var mom_loss;
    if(document.audit_party_details.mom_loss[0].checked==true)
        {
    	mom_loss="Y";
        }
     else
        {
    	mom_loss="N";
        }
    var res_fix="";
    if(document.audit_party_details.res_fix[0].checked==true)
    {
    	res_fix="Y";
    }
   else
    {
	res_fix="N";
    }
    
    if(document.getElementById("annual").checked==true)
    {
    	var inspection=document.getElementById("annual").value;
    }
    else if(document.getElementById("spl").checked==true)
    {
    	var inspection=document.getElementById("spl").value;
    }
    
    
        var url="../../../../Gpf_create_Party_details?command=Updation&audit_type="+audit_type+"&office_id_audited="+office_id_audited+"&Audit_Period_cover_frm="+Audit_Period_cover_frm+"&Audit_Period_Cover_To="+Audit_Period_Cover_To+"&Comm_Date="+Comm_Date+"&Comp_Date="+Comp_Date+"&Dis_Date="+Dis_Date+"&Repo_Date="+Repo_Date+"&mom_loss="+mom_loss+"&res_fix="+res_fix+"&Remarks="+Remarks+"&cnt="+cnt+"&inspection="+inspection;
        //alert("url"+url);
            xmlrequest.open("GET",url,true);              
            xmlrequest.onreadystatechange=function()
                {
            	handleResponse(xmlrequest);
                }
            ///call the date function here
            xmlrequest.send(null);
	}
}
function Validate()
{

	var val=nullcheck();
	if(val==true)
	{
    var xmlrequest= AjaxFunction();
    var audit_type=document.getElementById("audit_type").value;
    var cnt=document.getElementById("audit_id").value;
    var office_id_audited=document.getElementById("office_id_audited").value;
    var Audit_Period_cover_frm=document.getElementById("Audit_Period_cover_frm").value;
    var Audit_Period_Cover_To=document.getElementById("Audit_Period_Cover_To").value;
    //var Audit_conduct_frm=document.getElementById("Audit_conduct_frm").value;
    //var Audit_conduct_to=document.getElementById("Audit_conduct_to").value;
    var Comm_Date=document.getElementById("Comm_Date").value;
    var Comp_Date=document.getElementById("Comp_Date").value;
    var Dis_Date=document.getElementById("Dis_Date").value;
    var Repo_Date=document.getElementById("Repo_Date").value;
    var Remarks=document.getElementById("Remarks").value;
   
    var mom_loss;
    if(document.audit_party_details.mom_loss[0].checked==true)
        {
    	mom_loss="Y";
        }
     else
        {
    	mom_loss="N";
        }
    var res_fix="";
    if(document.audit_party_details.res_fix[0].checked==true)
    {
    	res_fix="Y";
    }
   else
    {
	res_fix="N";
    }
    
    
    if(document.getElementById("annual").checked==true)
    {
    	var inspection=document.getElementById("annual").value;
    }
    else if(document.getElementById("spl").checked==true)
    {
    	var inspection=document.getElementById("spl").value;
    }
        var url="../../../../Gpf_create_Party_details?command=Validate&audit_type="+audit_type+"&office_id_audited="+office_id_audited+"&Audit_Period_cover_frm="+Audit_Period_cover_frm+"&Audit_Period_Cover_To="+Audit_Period_Cover_To+"&Comm_Date="+Comm_Date+"&Comp_Date="+Comp_Date+"&Dis_Date="+Dis_Date+"&Repo_Date="+Repo_Date+"&mom_loss="+mom_loss+"&res_fix="+res_fix+"&Remarks="+Remarks+"&cnt="+cnt+"&inspection="+inspection;
        
            xmlrequest.open("GET",url,true);              
            xmlrequest.onreadystatechange=function()
                {
            	handleResponse(xmlrequest);
                }
            ///call the date function here
            xmlrequest.send(null);
	}

}
function hideInspection()
{
//	alert(document.getElementById("audit_type").value);
	if(document.getElementById("audit_type").value=="AG")
		document.getElementById("inspec").style.display='none';
	else
		document.getElementById("inspec").style.display='';
}
function Delete()
{
	 var cnt=document.getElementById("audit_id").value;
	 var xmlrequest= AjaxFunction();
	 var url="../../../../Gpf_create_Party_details?command=Delete&cnt="+cnt;
     
     xmlrequest.open("GET",url,true);              
     xmlrequest.onreadystatechange=function()
         {
     	handleResponse(xmlrequest);
         }
     ///call the date function here
     xmlrequest.send(null);
}
////////////////////////////////////////////AJAX FUNCTION TO GET THE RESPONSE FROM SERVLET////////////////////////////////////////////////////////////

function  manipulate(xmlrequest)
{
if(xmlrequest.readyState==4)
  {
      if(xmlrequest.status==200)
      {
           
            baseResponse=xmlrequest.responseXML.getElementsByTagName("response")[0];  
           var tagCommand=baseResponse.getElementsByTagName("command")[0]; 
           var command=tagCommand.firstChild.nodeValue; 
            if(command=="add")
              {
                  addRow(baseResponse);                 
              }
            if(command=="Getdetails")
            {
            	
            	 var flag=baseResponse.getElementsByTagName("flag")[0].firstChild.nodeValue; 
            	 if(flag=="success")
            	  {
            		 
            	  }
            }


            else if(command=="load1")
            {
            	loadRow1(baseResponse); 
            }
           /*else if(command=="office_idcheck")
            {
            	office_checkingid(baseResponse);
            }*/
            
            
            
      }
  }
}



function getdata()
{
	
	 var xmlrequest= AjaxFunction();
	 oid=document.audit_party_details.oid.value;
	 var url="../../../../Gpf_create_Party_details?command=getdata&oid="+oid;
	
	 xmlrequest.open("GET",url,true);              
     xmlrequest.onreadystatechange=function()
         
    {
    	viewResp(xmlrequest);
    	
    };
    	
	          
    xmlrequest.send(null); 
     
}
function viewResp(xmlrequest)
{
	
	if(xmlrequest.readyState==4)
{
	if(xmlrequest.status==200)
	{
		//alert(xmlrequest.responseText);
		  baseResponse=xmlrequest.responseXML.getElementsByTagName("response")[0];
		  
		  
	      var command=baseResponse.getElementsByTagName("command")[0].firstChild.nodeValue;
	      
	      var flag=baseResponse.getElementsByTagName("flag")[0].firstChild.nodeValue;
	      
	      changepagesize();
          changepage();
	}
}
}
function nullcheck()
{
	if(document.getElementById("audit_type").selectedIndex==0)
	{
		alert("please select Audit Type Id");
		return false;
	}
	else if(document.getElementById("office_id_audited").value=="")
	{
		alert("Please Enter Office(Id)Audited");
		document.getElementById(office_id_audited).focus();
		return false;
	}
	else if(document.getElementById("Audit_Period_cover_frm").value=="")
	{
		alert("Please Enter Audit Period Covered From Date");
		document.getElementById(Audit_Period_cover_frm).focus();
		return false;
	}
	else if(document.getElementById("Audit_Period_Cover_To").value=="")
	{
		alert("Please Enter Audit Period Covered To Date");
		document.getElementById(Audit_Period_Cover_To).focus();
		return false;
	}
	/*else if(document.getElementById("Comm_Date").value=="")
	{
		alert("Please Enter Audit Commencement Date");
		document.getElementById(Comm_Date).focus();
		return false;
	}
	else if(document.getElementById("Comp_Date").value=="")
	{
		alert("Please Enter Audit Completed Date");
		document.getElementById(Comp_Date).focus();
		return false;
	}
	else if(document.getElementById("Dis_Date").value=="")
	{
		alert("Please Enter Date of Discussion");
		document.getElementById(Dis_Date).focus();
		return false;
	}
	else if(document.getElementById("Repo_Date").value=="")
	{
		alert("Please Enter Date of Report Dispatch");
		document.getElementById(Repo_Date).focus();
		return false;
	}*/
	else if ( ( document.audit_party_details.mom_loss[0].checked == false ) && ( document.audit_party_details.mom_loss[1].checked == false ) )
	{
	alert ( "Please choose Any Momentry Loss Details" );
	return false;
	}
	else if ( ( document.audit_party_details.res_fix[0].checked == false ) && ( document.audit_party_details.res_fix[1].checked == false ) )
	{
	alert ( "Please choose Any Responsibility Fixed Details" );
	return false;
	}
	return true;
}

function loadvalue(i)
{
	
	var centre_id=training_centre_id[i];
	var year=document.getElementById("year").value;
	
	location.href="Training_Chart_Sub_Programs.jsp?regionSno="+centre_id+"&year="+year,'TRAINING PROGRAMS CHART';

}
function addRow(baseResponse)
{
  var flag=baseResponse.getElementsByTagName("flag")[0].firstChild.nodeValue; 
  if(flag=="success")
  {                       
             alert("Record Inserted Into Database successfully.");
             
            document.audit_para_details.Audit_para_id.value="";
            document.audit_para_details.audit_cat[0].checked=true;
            document.audit_para_details.audit_cat[1].checked=false;
            document.audit_para_details.Audit_Name.value="";
            document.audit_para_details.office_id_audited.value="";
            document.audit_para_details.office_name_level.value="";
            document.audit_para_details.Audit_Period_cover_frm.value="";
            document.audit_para_details.Audit_Period_Cover_To.value="";
            document.audit_para_details.Audit_conduct_frm.value="";
            document.audit_para_details.Audit_conduct_to.value="";
            document.audit_para_details.Para_No.value="";
            document.audit_para_details.Slip_Date.value="";
            document.audit_para_details.Objection_Made.value="";
            document.audit_para_details.para_type[0].checked=true;
            document.audit_para_details.para_type[1].checked=false;
            document.audit_para_details.para_type[2].checked=false;
            document.audit_para_details.Slip_No.value="";
            document.audit_para_details.Para_Gist.value="";
            document.audit_para_details.Voucher_Details.value="";
            document.audit_para_details.Scheme_Details.value="";
            document.audit_para_details.Remarks.value="";        
  }
  else
              {
                      alert("Failed to Add values");
              }
}

/////////////////////////////////////////////////FUNCTION TO ENTER ONLY NUMBERS INTO THE TEXT BOX//////////////////////////////////////////////////////

function checkIt(evt) 
{
    evt = (evt) ? evt : window.event;
    var charCode = (evt.which) ? evt.which : evt.keyCode;
    if (charCode > 31 && (charCode < 48 || charCode > 57)) 
    {
        status = "This field accepts numbers only.";
        alert("enter only number");
        return false;
    }
    status = "";
   
    return true;
}

//////////////////////////////////////////////FUNCTION TO ENTER ONLY CHARACTERS INTO THE TEXT BOX//////////////////////////////////////////////////////
function checkAlphabet(evt) 
{
    evt = (evt) ? evt : window.event;
    var charCode = (evt.which) ? evt.which : evt.keyCode;
    if (charCode > 31 && (charCode < 65 || charCode > 122)) 
    {
        status = "This field accepts alphabets only.";
        alert("enter only alphabets");
        return false;
    }
    status = "";
   
    return true;
}

///////////////////////////////////////FUNCTION TO CLEAR THE TEXT BOX VALUES////////////////////////////////////////////////////////////////////////////

function clearall()
{
	 
	document.getElementById("office_id_audited").disabled=false;
	document.getElementById("off_img").style.display="block";
     document.audit_party_details.mom_loss[0].checked=false;
     document.audit_party_details.mom_loss[1].checked=false;
     document.audit_party_details.res_fix[0].checked=false;
     document.audit_party_details.res_fix[1].checked=false;
     
     document.getElementById("annual").checked=true;
     document.getElementById("spl").checked=false;
     
     document.getElementById("audit_type").selectedIndex=0;
     document.getElementById("audit_id").value="";
     document.getElementById("office_name_level").value="";
     document.getElementById("office_id_audited").value="";
     document.getElementById("Audit_Period_cover_frm").value="";
     document.getElementById("Audit_Period_Cover_To").value="";
     //var Audit_conduct_frm=document.getElementById("Audit_conduct_frm").value;
     //var Audit_conduct_to=document.getElementById("Audit_conduct_to").value;
     document.getElementById("Comm_Date").value="";
     document.getElementById("Comp_Date").value="";
     document.getElementById("Dis_Date").value="";
     document.getElementById("Repo_Date").value="";
     document.getElementById("Remarks").value="";


}

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


function Exit()
{
    window.close();
}

function getAudit()
{
	
}
///new code for splitting -priya
function splitdate()
{
	var a1=document.audit_para_details.Audit_Period_cover_frm.value;
	var mySplitResult = a1.split('/');
	var currenDay =mySplitResult[0];
    var currentMonth=mySplitResult[1];
    var currentYear=mySplitResult[2];
    if((currenDay>31)||(currentMonth>12)||(currentYear<1970))
    {
    	alert("Invalid date");
    	document.audit_para_details.Audit_Period_cover_frm.value="";
    	document.audit_para_details.Audit_Period_cover_frm.focus();
    }
    
    
    var a2=document.audit_para_details.Audit_Period_Cover_To.value;
    var mySplitResult = a2.split('/');
	var currenDay =mySplitResult[0];
    var currentMonth=mySplitResult[1];
    var currentYear=mySplitResult[2];
    if((currenDay>31)||(currentMonth>12)||(currentYear<1970))
    {
    	alert("Invalid date");
    	document.audit_para_details.Audit_Period_Cover_To.value="";
    	document.audit_para_details.Audit_Period_Cover_To.focus();
    }
    
    
    var a3=document.audit_para_details.Audit_conduct_frm.value;
    var mySplitResult = a3.split('/');
	var currenDay =mySplitResult[0];
    var currentMonth=mySplitResult[1];
    var currentYear=mySplitResult[2];
    if((currenDay>31)||(currentMonth>12)||(currentYear<1970))
    {
    	alert("Invalid date");
    	document.audit_para_details.Audit_conduct_frm.value="";
    	document.audit_para_details.Audit_conduct_frm.focus();
    }
 
    var a4=document.audit_para_details.Audit_conduct_to.value;
    var mySplitResult = a4.split('/');
	var currenDay =mySplitResult[0];
    var currentMonth=mySplitResult[1];
    var currentYear=mySplitResult[2];
    if((currenDay>31)||(currentMonth>12)||(currentYear<1970))
    {
    	alert("Invalid date");
    	document.audit_para_details.Audit_conduct_to.value="";
    	document.audit_para_details.Audit_conduct_to.focus();
    }
    
    var a5=document.audit_para_details.Slip_Date.value;
    var mySplitResult = a5.split('/');
	var currenDay =mySplitResult[0];
    var currentMonth=mySplitResult[1];
    var currentYear=mySplitResult[2];
    if((currenDay>31)||(currentMonth>12)||(currentYear<1970))
    {
    	alert("Invalid date");
    	document.audit_para_details.Slip_Date.value="";
    	document.audit_para_details.Slip_Date.focus();
    }
}



//This Coding for Date Validation and Checking 
function calins(e,t)
{
	//alert("into this function1");
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

function checkdt(t)
{
	//alert("into this function2");
    if(t.value.length==0)
        return false;
    if(t.value.length==10  && t.value.indexOf('/',0)==2 && t.value.indexOf('/',3)==5)
    {
      
       
        // var c=t.value.replace(/-/g,'/');
         var c=t.value;
        try{
        var f=DateFormat(t,c,event,true,'3');
        }catch(e){
        //exception  start
        
         t.value=c;
            var sc=t.value.split('/');
            var currenDay =sc[0];
            var currentMonth=sc[1];
            var currentYear=sc[2];
            
            function getCurrentYear1() {
        	    var year = new Date().getYear();
        	    if(year < 1900) year += 1900;
        	    return year;
        	  }
            
            function getCurrentMonth1() {
        	    return new Date().getMonth() + 1;
        	  } 

        	  function getCurrentDay1() {
        	    return new Date().getDate();
        	  }
            
            
            //alert(currentYear == getCurrentYear()  && currentMonth == getCurrentMonth() && currenDay > getCurrentDay());
            if(currentYear > getCurrentYear1() )
            {
            
                    alert('Entered date should be less than current date');
                    t.value="";
                    t.focus();
                    return false;
           } 
           else if(currentYear == getCurrentYear1())
            {
                    if( currentMonth > getCurrentMonth1())
                    {
                        alert('Entered date should be less than current date ');
                        t.value="";
                        t.focus();
                        return false;
                    }
                    else if( currentMonth == getCurrentMonth1())
                    {
                        if(currenDay >= getCurrentDay1() )
                        {
                                alert('Entered date should be less than current date');
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
            //alert(f);
            //t.value=c.replace(/\//g,'-');
            t.value=c;
            var sc=t.value.split('/');
            var currenDay =sc[0];
            var currentMonth=sc[1];
            var currentYear=sc[2];
            //alert(currentYear == getCurrentYear()  && currentMonth == getCurrentMonth() && currenDay > getCurrentDay());
         
            if(currentYear > getCurrentYear1())
            {
            
                    alert('Entered date should be less than current date ');
                    t.value="";
                    t.focus();
                    return false;
           } 
           else if(currentYear == getCurrentYear1())
            {
                    if( currentMonth > getCurrentMonth1())
                    {
                         alert('Entered date should be less than current date');
                        t.value="";
                        t.focus();
                        return false;
                    }
                    else if( currentMonth == getCurrentMonth1())
                    {
                        if(currenDay >= getCurrentDay1() )
                        {
                                alert('Entered date should be less than current date');
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
            //t.focus();
            return false
    }
    
} 


//popup to fetch office id
var winjob;

function jobpopup()
{
    if (winjob && winjob.open && !winjob.closed) 
    {
       winjob.resizeTo(500,500);
       winjob.moveTo(250,250); 
       winjob.focus();
    }
    else
    {
        winjob=null
    }
        
    winjob= window.open("../../../../org/HR/HR1/OfficeMaster/jsps/JobPopupJSP.jsp","JobSearch","status=1,height=500,width=500,resizable=YES, scrollbars=yes"); 
    winjob.moveTo(250,250);  
    winjob.focus();
    
}

function doParentJob(jobid,deptid,name)
{
//alert("name "+name +" jobid      "+jobid);
    document.getElementById('office_id_audited').value=jobid;
    document.getElementById('office_name_level').value=name;
    doFunction('office',jobid,name);
    //document.audit_para_details.txtEmployeeid.disabled=true; 
    
}

function doFunction(Command,param)
{
	
	if(param.length<4)
	{
		
		alert("Wrong office id");
		return;
	}
	
	if(Command=="office")
	  {
		
	            var oid=param;
	            
	            var url="../../../../Create_Transfer_OrderServ?Command=office&oid="+oid;
	            //alert(url);
	            var req=getTransport();
	            req.open("GET",url,true); 
	            req.onreadystatechange=function()
	            {
	            	if(req.readyState==4)
	                {
	                  if(req.status==200)
	                  {
	            	
	            	 var baseResponse=req.responseXML.getElementsByTagName("response")[0];
	            	  var flag=baseResponse.getElementsByTagName("flag")[0].firstChild.nodeValue;
	            	  
	            	  if(flag=="success")
	            	  {
	            		  document.getElementById("office_name_level").value= baseResponse.getElementsByTagName("oname")[0].firstChild.nodeValue;

	            	  return true;
	            	  }
	            	  else
	            	  {
	            	     alert("Office Id '"+oid+"' doesn't Exists");
	            	     
	            	                document.getElementById("sanofficeid").value="";
	            	                document.getElementById("sanofficename").value="";
	            	                return false;

	            	  }
	            }
	  }

	            } ;
	                   if(window.XMLHttpRequest)
	                        req.send(null);
	                else req.send();
	                    
	   }
}





function officedesig()
{
	  var office_id=document.getElementById("Office_Id_Audited").value;
	  
	  var url="../../../../auditparadetails_serv1?command=Office&office_id="+office_id;
		//alert(url);
		 var req=getTransport();
	     req.open("GET",url,true); 
	     //alert("valate");
	     req.onreadystatechange=function()
	     {
	        processResponse(req);
	     };   
	     req.send(null);
	  
	  
}

function viewResponse(req) {
	if (req.readyState == 4) {

		if (req.status == 200) {
			response = req.responseXML.getElementsByTagName("response")[0];
			changepagesize();
			changepage();

			statusflag = true;

		}
	}
}

////////////////////////////////////BEFORE-CALENDER/////////////////////////////////////////////////////////////


function positionInfo(object) {

  var p_elm = object;

  this.getElementLeft = getElementLeft;
  function getElementLeft() {
    var x = 0;
    var elm;
    if(typeof(p_elm) == "object"){
      elm = p_elm;
    } else {
      elm = document.getElementById(p_elm);
    }
    while (elm != null) {
      x+= elm.offsetLeft;
      elm = elm.offsetParent;
    }
    return parseInt(x);
  }

  this.getElementWidth = getElementWidth;
  function getElementWidth(){
    var elm;
    if(typeof(p_elm) == "object"){
      elm = p_elm;
    } else {
      elm = document.getElementById(p_elm);
    }
    return parseInt(elm.offsetWidth);
  }

  this.getElementRight = getElementRight;
  function getElementRight(){
    return getElementLeft(p_elm) + getElementWidth(p_elm);
  }

  this.getElementTop = getElementTop;
  function getElementTop() {
    var y = 0;
    var elm;
    if(typeof(p_elm) == "object"){
      elm = p_elm;
    } else {
      elm = document.getElementById(p_elm);
    }
    while (elm != null) {
      y+= elm.offsetTop;
      elm = elm.offsetParent;
    }
    return parseInt(y);
  }

  this.getElementHeight = getElementHeight;
  function getElementHeight(){
    var elm;
    if(typeof(p_elm) == "object"){
      elm = p_elm;
    } else {
      elm = document.getElementById(p_elm);
    }
    return parseInt(elm.offsetHeight);
  }

  this.getElementBottom = getElementBottom;
  function getElementBottom(){
    return getElementTop(p_elm) + getElementHeight(p_elm);
  }
}

function CalendarControl() {

  var calendarId = 'CalendarControl';
  var currentYear = 0;
  var currentMonth = 0;
  var currentDay = 0;

  var selectedYear = 0;
  var selectedMonth = 0;
  var selectedDay = 0;

  var months = ['January','February','March','April','May','June','July','August','September','October','November','December'];
  var dateField = null;

  function getProperty(p_property){
    var p_elm = calendarId;
    var elm = null;

    if(typeof(p_elm) == "object"){
      elm = p_elm;
    } else {
      elm = document.getElementById(p_elm);
    }
    if (elm != null){
      if(elm.style){
        elm = elm.style;
        if(elm[p_property]){
          return elm[p_property];
        } else {
          return null;
        }
      } else {
        return null;
      }
    }
  }

  function setElementProperty(p_property, p_value, p_elmId){
    var p_elm = p_elmId;
    var elm = null;

    if(typeof(p_elm) == "object"){
      elm = p_elm;
    } else {
      elm = document.getElementById(p_elm);
    }
    if((elm != null) && (elm.style != null)){
      elm = elm.style;
      elm[ p_property ] = p_value;
    }
  }

  function setProperty(p_property, p_value) {
    setElementProperty(p_property, p_value, calendarId);
  }

  function getDaysInMonth(year, month) {
    return [31,((!(year % 4 ) && ( (year % 100 ) || !( year % 400 ) ))?29:28),31,30,31,30,31,31,30,31,30,31][month-1];
  }

  function getDayOfWeek(year, month, day) {
    var date = new Date(year,month-1,day)
    return date.getDay();
  }

  this.clearDate = clearDate;
  function clearDate() {
    dateField.value = '';
    hide();
  }

  this.setDate = setDate;
  function setDate(year, month, day) {
    if (dateField) {
      if (month < 10) {month = "0" + month;}
      if (day < 10) {day = "0" + day;}

      //var dateString = month+"-"+day+"-"+year;
      var dateString =day+"/"+ month+"/"+year;
      dateField.value = dateString;
      //dateField.blur();
      hide();
      check('Audit_Period_Cover_To','Audit_Period_cover_frm');	//comparision between two text boxes
      check('Audit_conduct_frm','Audit_Period_Cover_To');//comparision between two text boxes
      check('Audit_conduct_to','Audit_conduct_frm');	//comparision between two text boxes
    }
    return;
  }

  this.changeMonth = changeMonth;
  function changeMonth(change)
  {
    //change here check arrow month ; if change=0 means calender will not allow
    if(change == -1)
    {
        if(currentYear == (getCurrentYear()-39)  )
        {
             if(currentMonth == 1 )
            {
          
                    change=0;
            }
        }
    }
    else if(change == 1)
    {
        //alert(currentYear+"-curryear"+getCurrentYear()+"-"+getCurrentMonth() );
        if(currentYear == getCurrentYear()  )
        {
             if(currentMonth >= getCurrentMonth() )
            {
          
                    change=0;
            }
        }
    }
    currentMonth += change;
    currentDay = 0;
    if(currentMonth > 12) {
      currentMonth = 1;
      currentYear++;
    } else if(currentMonth < 1) {
      currentMonth = 12;
      currentYear--;
    }

    calendar = document.getElementById(calendarId);
    calendar.innerHTML = calendarDrawTable();
  }

  this.changeYear = changeYear;
  function changeYear(change)
  {
      //change here check arrow year ; if change=0 means calender will not allow
      if(change == 1)
        {
         
            if(currentYear >= getCurrentYear() )
            {
                       change=0;
                 
            }
         
        }
          if(change == -1)
        {
          
            if(currentYear <= (getCurrentYear()-39)  )
            {
                       change=0;
            }
         
           
        }
    
    
            currentYear += change;
            if(currentYear == getCurrentYear()  )
            {
                if(currentMonth > getCurrentMonth())
                    currentMonth=getCurrentMonth();
            }
            currentDay = 0;
            calendar = document.getElementById(calendarId);
            calendar.innerHTML = calendarDrawTable();
  }

  function getCurrentYear() {
    var year = new Date().getYear();
    if(year < 1900) year += 1900;
    return year;
  }

  function getCurrentMonth() {
    return new Date().getMonth() + 1;
  }

  function getCurrentDay() {
    return new Date().getDate();
  }

  function calendarDrawTable() {

    var dayOfMonth = 1;
    var validDay = 0;
    var startDayOfWeek = getDayOfWeek(currentYear, currentMonth, dayOfMonth);
    var daysInMonth = getDaysInMonth(currentYear, currentMonth);
    var css_class = null; //CSS class for each day

    var table = "<table cellspacing='0' cellpadding='0' border='0'>";
    table = table + "<tr class='header'>";
    table = table + "  <td colspan='2' class='previous'><a href='javascript:changeCalendarControlMonth(-1);'>&lt;</a> <a href='javascript:changeCalendarControlYear(-1);'>&laquo;</a></td>";
    table = table + "  <td colspan='3' class='title'>" + months[currentMonth-1] + "<br>" + currentYear + "</td>";
  
    table = table + "  <td colspan='2' class='next'><a href='javascript:changeCalendarControlYear(1);'>&raquo;</a> <a href='javascript:changeCalendarControlMonth(1);'>&gt;</a></td>";
    table = table + "</tr>";
    table = table + "<tr><th>S</th><th>M</th><th>T</th><th>W</th><th>T</th><th>F</th><th>S</th></tr>";

    for(var week=0; week < 6; week++) {
        table = table + "<tr>";
        for(var dayOfWeek=0; dayOfWeek < 7; dayOfWeek++)
        {
            if(week == 0 && startDayOfWeek == dayOfWeek)
            {
                validDay = 1;
            } else if (validDay == 1 && dayOfMonth > daysInMonth)
            {
                validDay = 0;
            }

            if(validDay)
            {
                if (dayOfMonth == selectedDay && currentYear == selectedYear && currentMonth == selectedMonth)
                {
                    css_class = 'current';
                } else if (dayOfWeek == 0 || dayOfWeek == 6)
                {
                    css_class = 'weekend';
                } else
                {
                    css_class = 'weekday';
                }
                //chage in == getCurrentMonth() to <= getCurrentMonth()  and == getCurrentYear() to <= getCurrentYear()
                if(currentYear == getCurrentYear()  && currentMonth == getCurrentMonth() && dayOfMonth < getCurrentDay() )
            	{

            		table = table + "<td><a class='"+css_class+"' href=\"javascript:setCalendarControlDate("+currentYear+","+currentMonth+","+dayOfMonth+")\">"+dayOfMonth+"</a></td>";
            	}
                else if(currentYear == getCurrentYear()  && currentMonth < getCurrentMonth())
            	{

            		table = table + "<td><a class='"+css_class+"' href=\"javascript:setCalendarControlDate("+currentYear+","+currentMonth+","+dayOfMonth+")\">"+dayOfMonth+"</a></td>";
            	}
            	else  if(currentYear < getCurrentYear()   && currentYear >= (parseInt(getCurrentYear())-39))
            	{

            		table = table + "<td><a class='"+css_class+"' href=\"javascript:setCalendarControlDate("+currentYear+","+currentMonth+","+dayOfMonth+")\">"+dayOfMonth+"</a></td>";
            	}


            	else
            	{
            		table = table + "<td><a class='"+css_class+"'>"+dayOfMonth+"</a></td>";
            	}
                //table = table + "<td><a class='"+css_class+"' href=\"javascript:setCalendarControlDate("+currentYear+","+currentMonth+","+dayOfMonth+")\">"+dayOfMonth+"</a></td>";
                dayOfMonth++;
            } else {
                table = table + "<td class='empty'>&nbsp;</td>";
            }
        }
        table = table + "</tr>";
    }

    table = table + "<tr class='header'><th colspan='7' style='padding: 3px;'><a href='javascript:clearCalendarControl();'>Clear</a> | <a href='javascript:hideCalendarControl();'>Close</a></td></tr>";
    table = table + "</table>";

    return table;
  }

  this.show = show;
  function show(field) {
    can_hide = 0;
 
    // If the calendar is visible and associated with
    // this field do not do anything.
    if (dateField == field) {
      return;
    } else {
      dateField = field;
    }
     if(dateField) {
      try {
        var dateString = new String(dateField.value);
        var dateParts = dateString.split("/");
      
        selectedDay = parseInt(dateParts[0],10);
        selectedMonth = parseInt(dateParts[1],10);
        selectedYear = parseInt(dateParts[2],10);
      } catch(e) {}
  

    }

    if (!(selectedYear && selectedMonth && selectedDay)) {
      selectedMonth = getCurrentMonth();
      selectedDay = getCurrentDay();
      selectedYear = getCurrentYear();
    }

    currentMonth = selectedMonth;
    currentDay = selectedDay;
    currentYear = selectedYear;

    if(document.getElementById){

      calendar = document.getElementById(calendarId);
      calendar.innerHTML = calendarDrawTable(currentYear, currentMonth);

      setProperty('display', 'block');


      var fieldPos = new positionInfo(dateField);
      var calendarPos = new positionInfo(calendarId);

      var x = fieldPos.getElementLeft();
      var y = fieldPos.getElementBottom();

      setProperty('left', x + "px");
      setProperty('top', y + "px");
   
      if (document.all) {
        if(navigator.userAgent.indexOf('Opera')==-1)
        setElementProperty('display', 'block', 'CalendarControlIFrame');
      
        setElementProperty('left', x + "px", 'CalendarControlIFrame');
        setElementProperty('top', y + "px", 'CalendarControlIFrame');
        setElementProperty('width', calendarPos.getElementWidth() + "px", 'CalendarControlIFrame');
        setElementProperty('height', calendarPos.getElementHeight() + "px", 'CalendarControlIFrame');
      }
    
/**/
    }
 
  }

  this.hide = hide;
  function hide() {
    if(dateField) {
      setProperty('display', 'none');
      setElementProperty('display', 'none', 'CalendarControlIFrame');
      dateField = null;
    }
  }

  this.visible = visible;
  function visible() {
    return dateField;
  }

  this.can_hide = can_hide;
  var can_hide = 0;
}

function Compare(fdate,tdate)
{
	
	var fromDate=document.getElementById(fdate).value;
	var toDate=document.getElementById(tdate).value;
	
	
	
	
	var ret=true;
	if(fromDate =="" || toDate == "")
	{
	}
	else
	{
		var fret=check_dateformat(document.getElementById(fdate));
		var tret=check_dateformat(document.getElementById(tdate));
		if(fret==true && tret==true)
		{
	var f_date =new Array();
	var t_date =new Array();
	
	f_date=fromDate.split("/");
	t_date=toDate.split("/");
	
	var fromYear=0,toYear=0,fromMonth=0,toMonth=0,fromDay=0,toDay=0;
	
	if(f_date[1]=='08' || f_date[1] =='8')
		f_date[1]="08";
	
	if(f_date[1]=='09' || f_date[1] =='9')
		f_date[1]="09";
	
	if(f_date[0]=='08' || f_date[0] =='8')
		f_date[0]="08";
	
	if(f_date[0]=='09' || f_date[0] =='9')
		f_date[0]="09";
	
	if(t_date[1]=='08' || t_date[1] =='8')
		t_date[1]="08";
	
	if(t_date[1]=='09' || t_date[1] =='9')
		t_date[1]="09";
	
	if(t_date[0]=='08' || t_date[0] =='8')
		t_date[0]="08";
	
	if(t_date[0]=='09' || t_date[0] =='9')
		t_date[0]="09";
	
	fromYear=parseInt(f_date[2],10);
	fromMonth=parseInt(f_date[1],10);
	fromDay=parseInt(f_date[0],10);
	
	toYear=parseInt(t_date[2],10);
	toMonth=parseInt(t_date[1],10);
	toDay=parseInt(t_date[0],10);
	
	if(fromYear>toYear)
	{
		ret= false;
	}
	else if(fromYear < toYear)
	{
		ret= true;
	}
	else if(fromMonth > toMonth)
	{
		ret= false;
		
	}
	else if(fromMonth < toMonth)
	{
		ret= true;
		
	}
	else if(fromDay < toDay)
	{
		ret= true;
		
	}
	else if(fromDay >= toDay)
	{
		ret= false;
		
	}
	
	}
	}
	
	if(ret==false)
	{
		alert("From Date Should be less than TO Date");
		document.getElementById(fdate).value="";
		document.getElementById(tdate).value="";
		return false;
	}
	else
	{
		return true;
	}
	
}

function check_dateformatss(field)
{
	//alert("jai r")
	var arr=new Array();
	
	var field_value=field.value;
	field_value=field_value.trim();

	if(field_value=="")
	{
	}
	else
	{

		
	arr=field_value.split("/");
	
//	alert(arr.length);
	if(arr.length==3)
	{
		var ret=check_validdate(arr[0],arr[1],arr[2]);
		if(ret==false)
		{
			alert("Invalid Date.");
			field.value="";
			return false;
		}
		else
		{
			return true;
		}
	}
	else
	{
		alert("Date format Should be DD/MM/YYYY");
		field.value="";
		return false;
	}
	
	}
	
}

function checkdats(Comm_Date,Comp_Date,Dis_Date)
{

	
	var Comm_Date=document.getElementById(Comm_Date).value;
	var Comp_Date=document.getElementById(Comp_Date).value;
	var Dis_Date=document.getElementById(Dis_Date).value;
	

	var d1 = Comm_Date.split("/");
	var d2 = Comp_Date.split("/");
	var c = Dis_Date.split("/");
	//alert(d1)
	    var ret=true;
	    var from = new Date(d1[2], d1[1]-1, d1[0],10);  // -1 because months are from 0 to 11
	    var to   = new Date(d2[2], d2[1]-1, d2[0],10);
	    var check = new Date(c[2], c[1]-1, c[0],10);
	   
	    if(check > from && check <= to)
	    {
	    	ret=true;
	    }
	    else
	    {
	    	ret=false;
	    }
	    
	    if(ret==false)
		{
			alert("Discussion Date should be between Audit Commencement Date and Audit Completed Date");
			document.getElementById("Dis_Date").value="";
			//document.getElementById(tdate).value="";
			return false;
		}
}


var calendarControl = new CalendarControl();

function showCalendarControl(textField) {
  // textField.onblur = hideCalendarControl;
  calendarControl.show(textField);
}

function clearCalendarControl() {
  calendarControl.clearDate();
}

function hideCalendarControl() {
  if (calendarControl.visible()) {
    calendarControl.hide();
  }
}

function setCalendarControlDate(year, month, day) {
  calendarControl.setDate(year, month, day);
}

function changeCalendarControlYear(change) {
  calendarControl.changeYear(change);
}

function changeCalendarControlMonth(change) {
  calendarControl.changeMonth(change);
}

document.write("<iframe id='CalendarControlIFrame' src='javascript:false;' frameBorder='0' scrolling='no'></iframe>");
document.write("<div id='CalendarControl' ></div>");
///////////////////////////////////////////////////////////////////////////////////////////////////////////////

	//date compare---saturday

	
	function dt_cmpr_by_id(dt1_id,dt2_id,proc)
	{

		var str;
		var pr=proc;
		var cuoffdt = document.getElementById(dt1_id).value;
		var new_val = document.getElementById(dt2_id).value;
		//alert("ok"+  cuoffdt+  "  "+new_val);
		var ret=fn(cuoffdt,new_val,pr);
		return ret;
	}
	function fn(val1,val2,pr)
	{	
	var retu = false;
	if(pr=='>')
	{
		//alert("now >");
		if(val2!="" && val1!="")
		{
			var str = val1.toString();	
			var frm_dte = parseInt(str.substring(0, 2), 10);
			var frm_mte = parseInt(str.substring(3, 5), 10);
			var frm_yre = parseInt(str.substring(6, 10), 10);
			var new_val_str = val2.toString();
			var to_dtk = parseInt(new_val_str.substring(0, 2), 10);
			var to_mt = parseInt(new_val_str.substring(3, 5), 10);
			var to_yr = parseInt(new_val_str.substring(6, 10), 10);
			
			var proc="yet_to";

			pastureLoop1:
				if(proc=="yet_to")
				{
					if (frm_yre >= to_yr) 
					{
						proc="exce";

						if (frm_yre > to_yr) 
						{							
							retu = true;
							break pastureLoop1;
						} else if(frm_yre == to_yr)
						{							
							if (frm_mte >= to_mt) 
							{


								if (frm_mte > to_mt) 
								{									
									retu = true;
									break pastureLoop1;
								} else if
								(frm_mte == to_mt)
								{
									

									if (frm_dte > to_dtk) 
									{
										
										retu = true;
										break pastureLoop1;
									} else 
									{
										
										retu = false;
										break pastureLoop1;
									}

								}
							}
							else
							{
								
								retu = false;
								break pastureLoop1;
							}

						}

					} else {
						proc="exce";
						
						retu = false;
					}
				}
			
		}
		
	}
	else if(pr=='<')
	{	
	//alert("here <");

		if(val2!="" && val1!="")
		{
			var str = val1.toString();	
			var frm_dte = parseInt(str.substring(0, 2), 10);
			var frm_mte = parseInt(str.substring(3, 5), 10);
			var frm_yre = parseInt(str.substring(6, 10), 10);
			var new_val_str = val2.toString();
			var to_dti = parseInt(new_val_str.substring(0, 2), 10);
			var to_mt = parseInt(new_val_str.substring(3, 5), 10);
			var to_yr = parseInt(new_val_str.substring(6, 10), 10);
			//var retu = false;
			var proc="yet_to";

			pastureLoop1:
				if(proc=="yet_to")
				{
					if (frm_yre <= to_yr) 
					{
						proc="exce";

						if (frm_yre < to_yr) 
						{							
							retu = true;
							break pastureLoop1;
						} else if(frm_yre == to_yr)
						{							
							if (frm_mte <= to_mt) 
							{


								if (frm_mte < to_mt) 
								{									
									retu = true;
									break pastureLoop1;
								} else if
								(frm_mte == to_mt)
								{	

									if (frm_dte < to_dti) 
									{										
										retu = true;
										break pastureLoop1;
									} else 
									{										
										retu = false;
										break pastureLoop1;
									}

								}
							}
							else
							{							
								retu = false;
								break pastureLoop1;
							}

						}

					} else {
						proc="exce";						
						retu = false;
					}
				}	
			
		}
		
	}
	else if(pr=='==')
	{	
	//alert("here ==");

		if(val2!="" && val1!="")
		{
			var str = val1.toString();	
			var frm_dte = parseInt(str.substring(0, 2), 10);
			var frm_mte = parseInt(str.substring(3, 5), 10);
			var frm_yre = parseInt(str.substring(6, 10), 10);
			var new_val_str = val2.toString();
			var to_dts = parseInt(new_val_str.substring(0, 2), 10);
			var to_mt = parseInt(new_val_str.substring(3, 5), 10);
			var to_yr = parseInt(new_val_str.substring(6, 10), 10);
			//var retu = false;
			var proc="yet_to";

			pastureLoop1:
				if(proc=="yet_to")
				{
					if (frm_yre == to_yr) 
					{
						proc="exce";				
							if (frm_mte == to_mt) 
							{
									if (frm_dte == to_dts) 
									{										
										retu = true;
										break pastureLoop1;
									} else 
									{										
										retu = false;
										break pastureLoop1;
									}
							}
							else
							{							
								retu = false;
								break pastureLoop1;
							}
					} else 
					{
						proc="exce";						
						retu = false;
					}
				}			
		}		
	}
	return retu;
	}

	function check(aa,bb)
	{
	
		var reth=dt_cmpr_by_id(aa,bb,'<');
		if(reth==true)
		{
			alert("YOUR ENTERED DATE SHOULD BE GREATER THAN THE DATE IN THE PREVIOUS TEXT BOX");
			//document.getElementById(bb).value="";
			document.getElementById(aa).value="";
			document.getElementById(aa).focus();
		}
		else
		{
			//alert(" no problem");
		}
		
		var reth1=dt_cmpr_by_id(aa,bb,'==');
		if(reth1==true)
		{
			alert("YOUR ENTERED DATE SHOULD BE GREATER THAN THE DATE IN THE PREVIOUS TEXT BOX");
			//document.getElementById(bb).value="";
			document.getElementById(aa).value="";
			document.getElementById(aa).focus();
		}
		else
		{
			//alert(" no problem");
		}

	}
	//end date compare
///////////////////////////////////calender- after current date///////////////////////////////////////////////////


function positionInfo_fn(object) {

var p_elm = object;

this.getElementLeft_fn = getElementLeft_fn;
function getElementLeft_fn() {
var x = 0;
var elm;
if(typeof(p_elm) == "object"){
elm = p_elm;
} else {
elm = document.getElementById(p_elm);
}
while (elm != null) {
x+= elm.offsetLeft;
elm = elm.offsetParent;
}
return parseInt(x);
}

this.getElementWidth_fn = getElementWidth_fn;
function getElementWidth_fn(){
var elm;
if(typeof(p_elm) == "object"){
elm = p_elm;
} else {
elm = document.getElementById(p_elm);
}
return parseInt(elm.offsetWidth);
}

this.getElementRight_fn = getElementRight_fn;
function getElementRight_fn(){
return getElementLeft_fn(p_elm) + getElementWidth_fn(p_elm);
}

this.getElementTop_fn = getElementTop_fn;
function getElementTop_fn() {
var y = 0;
var elm;
if(typeof(p_elm) == "object"){
elm = p_elm;
} else {
elm = document.getElementById(p_elm);
}
while (elm != null) {
y+= elm.offsetTop;
elm = elm.offsetParent;
}
return parseInt(y);
}

this.getElementHeight_fn = getElementHeight_fn;
function getElementHeight_fn(){
var elm;
if(typeof(p_elm) == "object"){
elm = p_elm;
} else {
elm = document.getElementById(p_elm);
}
return parseInt(elm.offsetHeight);
}

this.getElementBottom_fn = getElementBottom_fn;
function getElementBottom_fn(){
return getElementTop_fn(p_elm) + getElementHeight_fn(p_elm);
}
}

function CalendarControl_fn() {

var calendarId = 'CalendarControl';
var currentYear = 0;
var currentMonth = 0;
var currentDay = 0;

var selectedYear = 0;
var selectedMonth = 0;
var selectedDay = 0;

var months = ['January','February','March','April','May','June','July','August','September','October','November','December'];
var dateField_n = null;

function getProperty_fn(p_property){
var p_elm = calendarId;
var elm = null;

if(typeof(p_elm) == "object"){
elm = p_elm;
} else {
elm = document.getElementById(p_elm);
}
if (elm != null){
if(elm.style){
elm = elm.style;
if(elm[p_property]){
return elm[p_property];
} else {
return null;
}
} else {
return null;
}
}
}

function setElementProperty_fn(p_property, p_value, p_elmId){
var p_elm = p_elmId;
var elm = null;

if(typeof(p_elm) == "object"){
elm = p_elm;
} else {
elm = document.getElementById(p_elm);
}
if((elm != null) && (elm.style != null)){
elm = elm.style;
elm[ p_property ] = p_value;
}
}

function setProperty_fn(p_property, p_value) {
setElementProperty_fn(p_property, p_value, calendarId);
}

function getDaysInMonth_fn(year, month) {
return [31,((!(year % 4 ) && ( (year % 100 ) || !( year % 400 ) ))?29:28),31,30,31,30,31,31,30,31,30,31][month-1];
}

function getDayOfWeek_fn(year, month, day) {
var date = new Date(year,month-1,day);
return date.getDay();
}

this.clearDate_fn = clearDate_fn;
function clearDate_fn() {
dateField_n.value = '';
hide_fn();
}

this.setDate_fn = setDate_fn;
function setDate_fn(year, month, day) {
if (dateField_n) {
if (month < 10) {month = "0" + month;}
if (day < 10) {day = "0" + day;}

//var dateString = month+"-"+day+"-"+year;
var dateString =day+"/"+ month+"/"+year;
dateField_n.value = dateString;			//the date value is hrefed to the text box


//dateField.blur();
hide_fn();
check('Slip_Date','Audit_conduct_to');	//comparision between two text boxes
}
return;
}

this.changeMonth_fn = changeMonth_fn;
function changeMonth_fn(change) {



if(change == 1)
{

if(currentMonth == getCurrentMonth_fn() )
{
change=1;

}

}
if(change == -1)
{

if(currentMonth == getCurrentMonth_fn() )
{

// if(currentYear < getCurrentYear() )
// {
change=0;
// }
//  else
//  {
//change=1;
}
}




currentMonth += change;
currentDay = 0;
if(currentMonth > 12) {
currentMonth = 1;
currentYear++;
} else if(currentMonth < 1) {
currentMonth = 12;
currentYear--;
}

calendar = document.getElementById(calendarId);
calendar.innerHTML = calendarDrawTable_fn();
}

this.changeYear_fn = changeYear_fn;
function changeYear_fn(change) {

if(change == 1)
{

if(currentYear == getCurrentYear_fn() )
{
change=1;

}

}
if(change == -1)
{

if(currentYear == getCurrentYear_fn()  )
{
change=0;
}


}


currentYear += change;
if(currentYear == getCurrentYear_fn()  )
{
if(currentMonth > getCurrentMonth_fn())
currentMonth=getCurrentMonth_fn();
}
currentDay = 0;
calendar = document.getElementById(calendarId);
calendar.innerHTML = calendarDrawTable_fn();

}

function getCurrentYear_fn() {
var year = new Date().getYear();
if(year < 1900) year += 1900;
return year;
}

function getCurrentMonth_fn() {
return new Date().getMonth() + 1;
}

function getCurrentDay_fn() {
return new Date().getDate();
}

function calendarDrawTable_fn() {

var dayOfMonth = 1;
var validDay = 0;
var startDayOfWeek = getDayOfWeek_fn(currentYear, currentMonth, dayOfMonth);
var daysInMonth = getDaysInMonth_fn(currentYear, currentMonth);
var css_class = null; //CSS class for each day

var table = "<table cellspacing='0' cellpadding='0' border='0'>";
table = table + "<tr class='header'>";
table = table + "  <td colspan='2' class='previous'><a href='javascript:changeCalendarControlMonth_fn(-1);'>&lt;</a> <a href='javascript:changeCalendarControlYear_fn(-1);'>&laquo;</a></td>";
table = table + "  <td colspan='3' class='title'>" + months[currentMonth-1] + "<br>" + currentYear + "</td>";
table = table + "  <td colspan='2' class='next'><a href='javascript:changeCalendarControlYear_fn(1);'>&raquo;</a> <a href='javascript:changeCalendarControlMonth_fn(1);'>&gt;</a></td>";
table = table + "</tr>";
table = table + "<tr><th>S</th><th>M</th><th>T</th><th>W</th><th>T</th><th>F</th><th>S</th></tr>";

for(var week=0; week < 6; week++) {
table = table + "<tr>";
for(var dayOfWeek=0; dayOfWeek < 7; dayOfWeek++) {
if(week == 0 && startDayOfWeek == dayOfWeek) {
validDay = 1;
} else if (validDay == 1 && dayOfMonth > daysInMonth) {
validDay = 0;
}

if(validDay) {
if (dayOfMonth == selectedDay && currentYear == selectedYear && currentMonth == selectedMonth) {
css_class = 'current';
} else if (dayOfWeek == 0 || dayOfWeek == 6) {
css_class = 'weekend';
} else {
css_class = 'weekday';
}


if(currentYear == getCurrentYear_fn()  && currentMonth == getCurrentMonth_fn() && dayOfMonth < getCurrentDay_fn() )
{

table = table + "<td>"+dayOfMonth+"</td>";
// table = table + "<td><a class='"+css_class+"' href=\"javascript:setCalendarControlDate("+currentYear+","+currentMonth+","+dayOfMonth+")\">"+dayOfMonth+"</a></td>";

}
else
{

table = table + "<td><a class='"+css_class+"' href=\"javascript:setCalendarControlDate_fn("+currentYear+","+currentMonth+","+dayOfMonth+")\">"+dayOfMonth+"</a></td>";
//table = table + "<td>"+dayOfMonth+"</td>";
}


dayOfMonth++;
} else {
table = table + "<td class='empty'>&nbsp;</td>";
}
}
table = table + "</tr>";
}

table = table + "<tr class='header'><th colspan='7' style='padding: 3px;'><a href='javascript:clearCalendarControl_fn();'>Clear</a> | <a href='javascript:hideCalendarControl_fn();'>Close</a></td></tr>";
table = table + "</table>";

return table;
}

this.show_fn = show_fn;
function show_fn(field) {
can_hide = 0;

// If the calendar is visible and associated with
// this field do not do anything.
if (dateField_n == field) {
return;
} else {
dateField_n = field;
}
if(dateField_n) {
try {
var dateString = new String(dateField.value);
var dateParts = dateString.split("/");

selectedDay = parseInt(dateParts[0],10);
selectedMonth = parseInt(dateParts[1],10);
selectedYear = parseInt(dateParts[2],10);
} catch(e) {}


}

if (!(selectedYear && selectedMonth && selectedDay)) {
selectedMonth = getCurrentMonth_fn();
selectedDay = getCurrentDay_fn();
selectedYear = getCurrentYear_fn();
}

currentMonth = selectedMonth;
currentDay = selectedDay;
currentYear = selectedYear;

if(document.getElementById){

calendar = document.getElementById(calendarId);
calendar.innerHTML = calendarDrawTable_fn(currentYear, currentMonth);

setProperty_fn('display', 'block');


var fieldPos = new positionInfo_fn(dateField_n);
var calendarPos = new positionInfo_fn(calendarId);

var x = fieldPos.getElementLeft_fn();
var y = fieldPos.getElementBottom_fn();

setProperty_fn('left', x + "px");
setProperty_fn('top', y + "px");

if (document.all) {
if(navigator.userAgent.indexOf('Opera')==-1)
setElementProperty_fn('display', 'block', 'CalendarControlIFrame');

setElementProperty_fn('left', x + "px", 'CalendarControlIFrame');
setElementProperty_fn('top', y + "px", 'CalendarControlIFrame');
setElementProperty_fn('width', calendarPos.getElementWidth() + "px", 'CalendarControlIFrame');
setElementProperty_fn('height', calendarPos.getElementHeight() + "px", 'CalendarControlIFrame');
}

/**/
}

}

this.hide_fn = hide_fn;
function hide_fn() {
if(dateField_n) {
setProperty_fn('display', 'none');
setElementProperty_fn('display', 'none', 'CalendarControlIFrame');
dateField_n = null;
}
}

this.visible_fn = visible_fn;
function visible_fn() {
return dateField_n;
}

this.can_hide = can_hide;
var can_hide = 0;
}

var calendarControl_obj = new CalendarControl_fn();

function showCalendarControl_after(textField) {
// textField.onblur = hideCalendarControl;
calendarControl_obj.show_fn(textField);
}

function clearCalendarControl_fn() {
calendarControl_obj.clearDate_fn();
}

function hideCalendarControl_fn() {
if (calendarControl_obj.visible_fn()) {
calendarControl_obj.hide_fn();
}
}

function setCalendarControlDate_fn(year, month, day) {
calendarControl_obj.setDate_fn(year, month, day);
}

function changeCalendarControlYear_fn(change) {
calendarControl_obj.changeYear_fn(change);
}

function changeCalendarControlMonth_fn(change) {
calendarControl_obj.changeMonth_fn(change);
}

document.write("<iframe id='CalendarControlIFrame' src='javascript:false;' frameBorder='0' scrolling='no'></iframe>");
document.write("<div id='CalendarControl' ></div>");

////////////////////////////////////////////date comparision with current date/////////////////////////
function checkdt_aft(t)
{
	//alert("into this function2");
    if(t.value.length==0)
        return false;
    if(t.value.length==10  && t.value.indexOf('/',0)==2 && t.value.indexOf('/',3)==5)
    {
      
       
        // var c=t.value.replace(/-/g,'/');
         var c=t.value;
        try{
        var f=DateFormat(t,c,event,true,'3');
        }catch(e){
        //exception  start
        
         t.value=c;
            var sc=t.value.split('/');
            var currenDay =sc[0];
            var currentMonth=sc[1];
            var currentYear=sc[2];
            
            function getCurrentYear2() {
        	    var year = new Date().getYear();
        	    if(year < 1900) year += 1900;
        	    return year;
        	  }
            
            function getCurrentMonth2() {
        	    return new Date().getMonth() + 1;
        	  } 

        	  function getCurrentDay2() {
        	    return new Date().getDate();
        	  }
            
            
            //alert(currentYear == getCurrentYear()  && currentMonth == getCurrentMonth() && currenDay > getCurrentDay());
            if(currentYear < getCurrentYear2() )
            {
            
                    alert('Entered date should be greater than current date');
                    t.value="";
                    t.focus();
                    return false;
           } 
           else if(currentYear == getCurrentYear2())
            {
                    if( currentMonth < getCurrentMonth2())
                    {
                        alert('Entered date should be greater than current date ');
                        t.value="";
                        t.focus();
                        return false;
                    }
                    else if( currentMonth == getCurrentMonth2())
                    {
                        if(currenDay <= getCurrentDay2() )
                        {
                                alert('Entered date should be greater than current date');
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
            //alert(f);
            //t.value=c.replace(/\//g,'-');
            t.value=c;
            var sc=t.value.split('/');
            var currenDay =sc[0];
            var currentMonth=sc[1];
            var currentYear=sc[2];
            //alert(currentYear == getCurrentYear()  && currentMonth == getCurrentMonth() && currenDay > getCurrentDay());
         
            if(currentYear < getCurrentYear2())
            {
            
                    alert('Entered date should be greater than current date ');
                    t.value="";
                    t.focus();
                    return false;
           } 
           else if(currentYear == getCurrentYear2())
            {
                    if( currentMonth < getCurrentMonth1())
                    {
                         alert('Entered date should be greater than current date');
                        t.value="";
                        t.focus();
                        return false;
                    }
                    else if( currentMonth == getCurrentMonth1())
                    {
                        if(currenDay <= getCurrentDay2() )
                        {
                                alert('Entered date should be greater than current date');
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
            //t.focus();
            return false;
    }
    
} 
