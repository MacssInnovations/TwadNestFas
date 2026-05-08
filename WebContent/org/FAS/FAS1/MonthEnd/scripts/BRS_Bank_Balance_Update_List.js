/////////////////////////////creating AJAX object////////////////////////

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
function initialLoad(ttlcodecode)
{
	
	var spl=ttlcodecode.split(",");
        var url="../../../../../BRS_Bank_Balance_Update?command=load_table&unitcode="+spl[0]+"&year="+spl[1]+"&month="+spl[2];
        var req=getTransport();
        req.open("GET",url,true); 
        req.onreadystatechange=function()
        {
                processResponse1(req);
        };   
        req.send(null);
}
function initialLoad1(ttlcodecode)
{
	
}
function processResponse1(req)
{   
    if(req.readyState==4)   // Completed
      {
          if(req.status==200)   // No error
          {   
              var baseResponse=req.responseXML.getElementsByTagName("response")[0];
              var tagCommand=baseResponse.getElementsByTagName("command")[0];
              var command=tagCommand.firstChild.nodeValue; 
              if(command=="gett")
                      { 
            	  //alert("return tom js");
                           getRow(baseResponse);
                      }
              }
        }
    }
function  getRow(baseResponse)
    {  
	var seq=0;
	//alert("inside get row from java to js");
      var flag=baseResponse.getElementsByTagName("flag")[0].firstChild.nodeValue; 
      if(flag=="success")
        {          
            var tbody = document.getElementById("tblList");
            var table = document.getElementById("Existing");
            var t=0;
            for(t=tbody.rows.length-1;t>=0;t--)
                {
                   tbody.deleteRow(0);
                }                        
            var len=baseResponse.getElementsByTagName("count")[0].firstChild.nodeValue;  
           // alert(len);
            for(var k=0;k<len;k++)
                {
            	
            	var acunitid =baseResponse.getElementsByTagName("acunitid")[k].firstChild.nodeValue;
            	var bank_name =baseResponse.getElementsByTagName("bank_name")[k].firstChild.nodeValue;
    			var ac_type=baseResponse.getElementsByTagName("ac_type")[k].firstChild.nodeValue;
    			var ac_no =baseResponse.getElementsByTagName("ac_no")[k].firstChild.nodeValue;
    			var bank_bal=baseResponse.getElementsByTagName("bank_bal")[k].firstChild.nodeValue;			
    			var dtorcr=baseResponse.getElementsByTagName("dtorcr")[k].firstChild.nodeValue;
    			if(baseResponse.getElementsByTagName("remark")[k].firstChild!=null)
    			var remark=baseResponse.getElementsByTagName("remark")[k].firstChild.nodeValue;
    			var cb_year =baseResponse.getElementsByTagName("cb_year")[k].firstChild.nodeValue;
            	var CB_MONTH =baseResponse.getElementsByTagName("CB_MONTH")[k].firstChild.nodeValue;
            	var ps_year =baseResponse.getElementsByTagName("ps_year")[k].firstChild.nodeValue;
            	var ps_month =baseResponse.getElementsByTagName("ps_month")[k].firstChild.nodeValue;
            	var ps_date =baseResponse.getElementsByTagName("ps_date")[k].firstChild.nodeValue;
            	     
            	/* var payee_code = baseResponse.getElementsByTagName("payee_code")[k].firstChild.nodeValue;
                     var payee_desc = baseResponse.getElementsByTagName("payee_desc")[k].firstChild.nodeValue;
                     var view = baseResponse.getElementsByTagName("status")[k].firstChild.nodeValue;*/
                     //alert(acunitid+"   "+"bank_name "+bank_name);
                     var mycurrent_row=document.createElement("TR");
                     mycurrent_row.id=acunitid;  
                   //  var det=acunitid+"&acno="+ac_no;
                     var cell=document.createElement("TD");                     
                    
               			var anc=document.createElement("A");       
               			//var url="javascript:loadValuesFromTable("+billno+",'"+billdate+"','"+major+"','"+minor+"','"+sub+"')";
               			var url="javascript:loadValuesFromTable("+acunitid+","+ac_no+","+cb_year+","+CB_MONTH+")"; 
                        anc.href=url;
                        var txtedit=document.createTextNode("Edit");
                        anc.appendChild(txtedit);
                        cell.appendChild(anc);          						
               		
                     mycurrent_row.appendChild(cell);
                  
                     var cell001 = document.createElement("TD");
           			var cb_year1=document.createElement("input");
           			cb_year1.type="hidden";
           			cb_year1.name="cb_year"+seq;
           			cb_year1.id="cb_year"+seq;
           			cb_year1.value=cb_year;
           			/*var st_name_no1=document.createElement("input");
           			st_name_no1.type="hidden";
           			st_name_no1.name="st_name_no"+seq;
           			st_name_no1.id="st_name_no"+seq;
           			st_name_no1.value=st_name_no;*/
           			var cb_year = document.createTextNode(cb_year);
           			cell001.appendChild(cb_year);
           			cell001.appendChild(cb_year1);
           			//cell1.appendChild(st_name_no1);
           			mycurrent_row.appendChild(cell001);
           			
           			
           			var cell01 = document.createElement("TD");
          			var CB_MONTH1=document.createElement("input");
          			CB_MONTH1.type="hidden";
          			CB_MONTH1.name="CB_MONTH"+seq;
          			CB_MONTH1.id="CB_MONTH"+seq;
          			CB_MONTH1.value=CB_MONTH;
          			/*var st_name_no1=document.createElement("input");
          			st_name_no1.type="hidden";
          			st_name_no1.name="st_name_no"+seq;
          			st_name_no1.id="st_name_no"+seq;
          			st_name_no1.value=st_name_no;*/
          			var CB_MONTH = document.createTextNode(CB_MONTH);
          			cell01.appendChild(CB_MONTH);
          			cell01.appendChild(CB_MONTH1);
          			//cell1.appendChild(st_name_no1);
          			mycurrent_row.appendChild(cell01);
          			
          			
          		    var cell0001 = document.createElement("TD");
           			var ps_year1=document.createElement("input");
           			ps_year1.type="hidden";
           			ps_year1.name="ps_year"+seq;
           			ps_year1.id="ps_year"+seq;
           			ps_year1.value=ps_year;
           			/*var st_name_no1=document.createElement("input");
           			st_name_no1.type="hidden";
           			st_name_no1.name="st_name_no"+seq;
           			st_name_no1.id="st_name_no"+seq;
           			st_name_no1.value=st_name_no;*/
           			var ps_year = document.createTextNode(ps_year);
           			cell0001.appendChild(ps_year);
           			cell0001.appendChild(ps_year1);
           			//cell1.appendChild(st_name_no1);
           			mycurrent_row.appendChild(cell0001);
           			
           			
           			var cell00002 = document.createElement("TD");
          			var ps_month1=document.createElement("input");
          			ps_month1.type="hidden";
          			ps_month1.name="ps_month"+seq;
          			ps_month1.id="ps_month"+seq;
          			ps_month1.value=ps_month;
          			/*var st_name_no1=document.createElement("input");
          			st_name_no1.type="hidden";
          			st_name_no1.name="st_name_no"+seq;
          			st_name_no1.id="st_name_no"+seq;
          			st_name_no1.value=st_name_no;*/
          			var ps_month = document.createTextNode(ps_month);
          			cell00002.appendChild(ps_month);
          			cell00002.appendChild(ps_month1);
          			//cell1.appendChild(st_name_no1);
          			mycurrent_row.appendChild(cell00002);
          			
          			
          			var cell00003 = document.createElement("TD");
          			var ps_date1=document.createElement("input");
          			ps_date1.type="hidden";
          			ps_date1.name="ps_date"+seq;
          			ps_date1.id="ps_date"+seq;
          			ps_date1.value=ps_date;
          			/*var st_name_no1=document.createElement("input");
          			st_name_no1.type="hidden";
          			st_name_no1.name="st_name_no"+seq;
          			st_name_no1.id="st_name_no"+seq;
          			st_name_no1.value=st_name_no;*/
          			/*var ps_date1=ps_date.split(" ");          			
          			alert(ps_date1);*/
          			
          			var datePart = ps_date.match(/\d+/g),
          		  year = datePart[0]
          		  month = datePart[1], day = datePart[2];

          		var ps_date0=day+'/'+month+'/'+year;
          		  var ps_date2 = document.createTextNode(ps_date0);
          			cell00003.appendChild(ps_date2);
          			cell00003.appendChild(ps_date1);
          			//cell1.appendChild(st_name_no1);
          			mycurrent_row.appendChild(cell00003);
          			
          			
          			

         			var cell1 = document.createElement("TD");
         			var bank_name1=document.createElement("input");
         			bank_name1.type="hidden";
         			bank_name1.name="bank_name"+seq;
         			bank_name1.id="bank_name"+seq;
         			bank_name1.value=bank_name;
         			/*var st_name_no1=document.createElement("input");
         			st_name_no1.type="hidden";
         			st_name_no1.name="st_name_no"+seq;
         			st_name_no1.id="st_name_no"+seq;
         			st_name_no1.value=st_name_no;*/
         			var bankname = document.createTextNode(bank_name);
         			cell1.appendChild(bankname);
         			cell1.appendChild(bank_name1);
         			//cell1.appendChild(st_name_no1);
         			mycurrent_row.appendChild(cell1);
         			
         			var cell2 = document.createElement("TD");
         			var ac_type1=document.createElement("input");
         			ac_type1.type="hidden";
         			ac_type1.name="ac_type"+seq;
         			ac_type1.id="ac_type"+seq;
         			ac_type1.value=ac_type;
         			/*var st_group_no1=document.createElement("input");
         			st_group_no1.type="hidden";
         			st_group_no1.name="st_group_no"+seq;
         			st_group_no1.id="st_group_no"+seq;
         			st_group_no1.value=st_group_no;*/
         			var actype = document.createTextNode(ac_type);
         			cell2.appendChild(actype);
         			cell2.appendChild(ac_type1);
         			//cell2.appendChild(st_group_no1);
         			mycurrent_row.appendChild(cell2);
         			
         			var cell3 = document.createElement("TD");
         			var ac_no1=document.createElement("input");
         			ac_no1.type="hidden";
         			ac_no1.name="ac_no"+seq;
         			ac_no1.id="ac_no"+seq;
         			ac_no1.value=ac_no;
         			var acno = document.createTextNode(ac_no);
         			cell3.appendChild(acno);
         			cell3.appendChild(ac_no1);
         			mycurrent_row.appendChild(cell3);
         			
         			var cell4 = document.createElement("TD");
         			var bank_bal1=document.createElement("input");
         			bank_bal1.type="hidden";
         			bank_bal1.name="bank_bal"+seq;
         			bank_bal1.id="bank_bal"+seq;
         			bank_bal1.value=bank_bal;
         			var bank_bal2 = document.createTextNode(bank_bal);
         		    cell4.style.textAlign="right";
         			cell4.appendChild(bank_bal2);
         			cell4.appendChild(bank_bal1);
         			mycurrent_row.appendChild(cell4);
         			
         			var cell5 = document.createElement("TD");
         			var dtorcr1=document.createElement("input");
         			dtorcr1.type="hidden";
         			dtorcr1.name="dtorcr"+seq;
         			dtorcr1.id="dtorcr"+seq;
         			dtorcr1.value=dtorcr;
         			var dtorcr2 = document.createTextNode(dtorcr);
         			cell5.appendChild(dtorcr2);
         			cell5.appendChild(dtorcr1);
         			mycurrent_row.appendChild(cell5);
         			
         			var cell6 = document.createElement("TD");
         			var remark1=document.createElement("input");
         			remark1.type="hidden";
         			remark1.name="remark"+seq;
         			remark1.id="remark"+seq;
         			remark1.value=remark;			
         			var remark2 = document.createTextNode(remark);
         			cell6.appendChild(remark2);
         			cell6.appendChild(remark1);
         			mycurrent_row.appendChild(cell6);
                     
                     tbody.appendChild(mycurrent_row);
                    
                }
      }
      else
      {
        alert("No Data Found");
      }           
    }
function loadValuesFromTable(acunitid,acno,yr,mn)
    {       
	
          Minimize();
          opener.doParentEmp(acunitid,acno,yr,mn);
    }

function Minimize()
{
   window.close();
   opener.window.focus();
}