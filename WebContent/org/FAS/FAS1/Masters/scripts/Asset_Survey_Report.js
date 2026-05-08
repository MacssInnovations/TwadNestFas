var service;
var __pagination=11;
var destid;
var totalblock=0;
var seq=0;
var winemp=null;
var my_window;
var jobflag;
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
function clearAllFields()
{
	document.getElementById("cmbFinancialYear").value="";
	document.getElementById("txtsurveydate").value="";
    document.getElementById("txtEmployeeid").value="";
    document.getElementById("txtEmployee").value="";
    document.getElementById("cmbDesignation").value="";
    document.getElementById("Desig_Id").value="";
    document.getElementById("txtOffice_Id").value="";
    document.getElementById("txtOffice_Name").value="";
    document.getElementById("txtappdate").value="";
    document.getElementById("txtEmployeeid2").value="";
    document.getElementById("txtEmployee2").value="";
    document.getElementById("cmbDesignation2").value="";
    document.getElementById("txtOffice_Name2").value="";
    document.getElementById("Desig_Id2").value="";
    document.getElementById("txtOffice_Id2").value=""; 
    document.getElementById("txtrefno").value="";
    document.getElementById("txtrefdate").value="";
    var tbody=document.getElementById("grid_body");
    try{tbody.innerHTML="";}
    catch(e) {tbody.innerText="";}  
    
}
function addBtn()
{
	var k=0;
	 var cmbAcc_UnitCode=document.getElementById("cmbAcc_UnitCode").value;    
     var cmbOffice_code=document.getElementById("cmbOffice_code").value;
     var finYear=document.getElementById("cmbFinancialYear").value;
     var txtsurveydate=document.getElementById("txtsurveydate").value;
     var txtEmployeeid=document.getElementById("txtEmployeeid").value;
     var Desig_Id=document.getElementById("Desig_Id").value;
     var txtOffice_Id=document.getElementById("txtOffice_Id").value;
     var txtappdate=document.getElementById("txtappdate").value;
   //  alert(txtappdate);
     var txtEmployeeid2=document.getElementById("txtEmployeeid2").value;
     var Desig_Id2=document.getElementById("Desig_Id2").value;
     var txtOffice_Id2=document.getElementById("txtOffice_Id2").value; 
     var txtrefno=document.getElementById("txtrefno").value;
     var txtrefdate=document.getElementById("txtrefdate").value;
     
     var tbody = document.getElementById("grid_body");
 	 var rowcount=tbody.rows.length;
 //	alert("rowcount"+rowcount);
 	var al= new Array() ;
    for(var i=0;i<rowcount;i++)
    	{
    	   var r=tbody.rows[i];
    	   var s=r.cells.length;
    	  
	  for(var j=0;j<s;j++)
    		   {

	    		   al[k]=r.cells[j].firstChild.value;
	    		
	    		//   alert(":::::"+al[k]);
	    		    k++; 
	    		
    		   }
    	
    	}
     
     var url="../../../../../Asset_Survey_Report?Command=add&cmbAcc_UnitCode="+cmbAcc_UnitCode+
     "&cmbOffice_code="+cmbOffice_code+"&finYear="+finYear+"&txtsurveydate="+txtsurveydate+"&txtEmployeeid="+txtEmployeeid+"&Desig_Id="+Desig_Id+
     "&txtOffice_Id="+txtOffice_Id+"&txtappdate="+txtappdate+"&txtEmployeeid2="+txtEmployeeid2+"&Desig_Id2="+Desig_Id2+"&txtOffice_Id2="+txtOffice_Id2+"&txtrefno="+txtrefno+"&txtrefdate="+txtrefdate+"&grid="+al;
 // alert(url);
     var req=getTransport();
     req.open("GET",url,true); 
     req.onreadystatechange=function()
     {
        fnHandleResponse(req);
     }   
    req.send(null);
       
}

function callGridItems()
{  
        var cmbAcc_UnitCode=document.getElementById("cmbAcc_UnitCode").value;    
        var cmbOffice_code=document.getElementById("cmbOffice_code").value;
        var finYear=document.getElementById("cmbFinancialYear").value;
      
        var url="../../../../../Asset_Survey_Report?Command=goCmd&cmbAcc_UnitCode="+
        cmbAcc_UnitCode+"&cmbOffice_code="+cmbOffice_code+"&finYear="+finYear;
   //   alert(url);
        var req=getTransport();
        req.open("GET",url,true); 
        req.onreadystatechange=function()
        {
           fnHandleResponse(req);
        }   
                req.send(null);
          
}

function fnHandleResponse(req)
{ 
    if(req.readyState==4)
    {
        if(req.status==200)
        {  
            var baseResponse=req.responseXML.getElementsByTagName("response")[0];
            var tagcommand=baseResponse.getElementsByTagName("command")[0];
            var Command=tagcommand.firstChild.nodeValue;
             
            if(Command=="goCmd")
            {
                loadTable(baseResponse);
            }
            else if(Command=="Add")
            {
            	alert("Record Inserted into Database Successfully");
            	clearAllFields();
            }
        }
    }
}

function loadTable(baseResponse)
{
  
                var flag=baseResponse.getElementsByTagName("flag")[0].firstChild.nodeValue;
                if(flag=="failure")
                {
                    s=0;
                    var tbody=document.getElementById("grid_body");
                      try{tbody.innerHTML="";}
                  catch(e) {tbody.innerText="";}
                  
                     
                    alert("No Record exists");
                }
                else
                {                       
                     service=baseResponse.getElementsByTagName("leng");
                    
                    if(service)
                    { 
//                    	 acc_code=baseResponse.getElementsByTagName("acc_code")[0].firstChild.nodeValue;
//                         year_qty=baseResponse.getElementsByTagName("year_qty")[0].firstChild.nodeValue;
                                   
                        var i=0;
                        var c=0;
                        var cell2;
                        
                        var tbody=document.getElementById("grid_body");
                        try{tbody.innerHTML="";}
                        catch(e) {tbody.innerText="";}  
                        
                        
                        for(i=0;i<service.length;i++)
                        {
                       
                                c++;
                                 var items=new Array();
                                items[0]=service[i].getElementsByTagName("acc_code")[0].firstChild.nodeValue;
                                items[1]=service[i].getElementsByTagName("qty")[0].firstChild.nodeValue;
                                items[2]=service[i].getElementsByTagName("bookvalue")[0].firstChild.nodeValue;
                                items[3]=service[i].getElementsByTagName("assvalue")[0].firstChild.nodeValue;
                                items[4]=service[i].getElementsByTagName("assdate")[0].firstChild.nodeValue;
                                var tbody=document.getElementById("grid_body");
                                var mycurrent_row=document.createElement("TR");
                             //   alert(items[1]);   
                               cell2=document.createElement("TD");
                                var check="";
                       			check=document.createElement("input");
                       			check.type="checkbox";
                       			check.name="select";
                       			check.id="select";
                       			check.value="Y";
                       			check.size="22";
                                cell2.appendChild(check);
                                mycurrent_row.appendChild(cell2);
                                
                               cell2=document.createElement("TD");
                               cell2.setAttribute('align','right');
                               var a_code=document.createElement("input");
                               a_code.type="hidden";
                               a_code.name="assetCode";
                               a_code.value=items[0];
                               cell2.appendChild(a_code);
                               var currentText=document.createTextNode(items[0]);
                               cell2.appendChild(currentText);
                               mycurrent_row.appendChild(cell2);
                               
                               cell2=document.createElement("TD");
                               cell2.setAttribute('align','right');
                               var avl_qty=document.createElement("input");
                               avl_qty.type="hidden";
                               avl_qty.name="availQty";
                               avl_qty.value=items[1];
                               cell2.appendChild(avl_qty);
                               var currentText=document.createTextNode(items[1]);
                               cell2.appendChild(currentText);
                               mycurrent_row.appendChild(cell2);
                               
                               
                               cell2 = document.createElement("TD");
                               var book_value = document.createElement("input");
                               book_value.type = "text";
                               book_value.name = "bookvalue";
                               book_value.id = "bookvalue"; 
                               book_value.size="5";
                               book_value.maxLength="8";
                               cell2.appendChild(book_value);
                               mycurrent_row.appendChild(cell2);
                               
                               cell2 = document.createElement("TD");
                               var assessed_value = document.createElement("input");
                               assessed_value.type = "text";
                               assessed_value.name = "Ass_Date";
                               assessed_value.id = "Ass_Date";
                               assessed_value.size="5";
                               cell2.appendChild(assessed_value);
                               mycurrent_row.appendChild(cell2);
                               
                                cell2 = document.createElement("TD");
                               var Assed_Date = document.createElement("input");
                               Assed_Date.type = "text";
                               Assed_Date.name = "Ass_Date";
                               Assed_Date.id = "Ass_Date";
                               Assed_Date.size="5";
                               cell2.appendChild(Assed_Date);
                               mycurrent_row.appendChild(cell2);
                               
                                var cell2 = document.createElement("TD");
                               var Remarks_Officer = document.createElement('TEXTAREA','option1');
                               Remarks_Officer.name = "Remarks_Officer";
                               Remarks_Officer.id = "Remarks_Officer";
                               Remarks_Officer.setAttribute("cols", "5");
                               Remarks_Officer.style.height = "60px";
                               Remarks_Officer.style.width = "100px";
                               cell2.appendChild(Remarks_Officer);
                               mycurrent_row.appendChild(cell2);
                               
                                  var cell2 = document.createElement("TD");
                               var Remarks_DivOfficer = document.createElement('TEXTAREA','option1');
                               Remarks_DivOfficer.name = "Remarks_DivOfficer";
                               Remarks_DivOfficer.id = "Remarks_DivOfficer";
                               Remarks_DivOfficer.setAttribute("cols", "5");
                               Remarks_DivOfficer.style.height = "60px";
                               Remarks_DivOfficer.style.width = "100px";
                               cell2.appendChild(Remarks_DivOfficer);
                               mycurrent_row.appendChild(cell2);
                               
                                var cell2 = document.createElement("TD");
                               var Remarks_SE = document.createElement('TEXTAREA','option1');
                               Remarks_SE.name = "Remarks_SE";
                               Remarks_SE.id = "Remarks_SE";
                               Remarks_SE.setAttribute("cols", "5");
                               Remarks_SE.style.height = "60px";
                               Remarks_SE.style.width = "100px";
                               cell2.appendChild(Remarks_SE);
                               mycurrent_row.appendChild(cell2);
                                                          
                               cell2 = document.createElement("TD");
                               var BP_NO = document.createElement("input");
                               BP_NO.type = "text";
                               BP_NO.name = "BPNO";
                               BP_NO.id = "BPNO"; 
                               BP_NO.size="5";
                               cell2.appendChild(BP_NO);
                               mycurrent_row.appendChild(cell2);
                               
                                cell2 = document.createElement("TD");
                               var BP_Date = document.createElement("input");
                               BP_Date.type = "text";
                               BP_Date.name = "BPDate";
                               BP_Date.id = "BPDate"; 
                               BP_Date.size="5";
                               cell2.appendChild(BP_Date);
                               mycurrent_row.appendChild(cell2);
                               
                               cell2 = document.createElement("TD");
                               var proceeding_no = document.createElement("input");
                               proceeding_no.type = "text";
                               proceeding_no.name = "pro_no";
                               proceeding_no.id = "pro_no"; 
                               proceeding_no.size="5";
                               cell2.appendChild(proceeding_no);
                               mycurrent_row.appendChild(cell2);
                               
                                 cell2 = document.createElement("TD");
                               var proceeding_date = document.createElement("input");
                               proceeding_date.type = "text";
                               proceeding_date.name = "pro_date";
                               proceeding_date.id = "pro_date"; 
                               proceeding_date.size="5";
                               cell2.appendChild(proceeding_date);
                               mycurrent_row.appendChild(cell2);
                               
                               
                               var cell2 = document.createElement("TD");
                               var Remarks = document.createElement('TEXTAREA','option1');
                               Remarks.name = "Remarks";
                               Remarks.id = "Remarks";
                               Remarks.setAttribute("cols", "5");
                               Remarks.style.height = "60px";
                               Remarks.style.width = "100px";
                               cell2.appendChild(Remarks);
                               mycurrent_row.appendChild(cell2);
                              
                                tbody.appendChild(mycurrent_row);
                                seq++;
                            }
                           
                        }
                   }
}



function btncancel()
{

 self.close();
}

function numbersonly(e)
    {
        var unicode=e.charCode? e.charCode : e.keyCode;
       if(unicode==13)
        {
          //t.blur();
          //return true;-------------------- for taking action when press ENTER
        
        }
        if (unicode!=8 && unicode !=9  )
        {
            if (unicode<48 || unicode>57 ) 
                return false 
        }
     }



/////////////////////////////////////////////   Check Date() by User /////////////////////////////////////////////////////
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
      
       
        // var c=t.value.replace(/-/g,'/');
         var c=t.value;
//        try{
//        var f=DateFormat(t,c,event,true,'3');
//        }catch(e){
         
       ///New code implemented on 28-03-2019  for year 2019 wrongly displayed 201 
         try{
             var f=isValidDate(c);
            }
        catch(e){
         
        //exception  start
        
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
            //alert(f);
            //t.value=c.replace(/\//g,'-');
            t.value=c;
            var sc=t.value.split('/');
            var currenDay =sc[0];
            var currentMonth=sc[1];
            var currentYear=sc[2];
            //alert(currentYear == getCurrentYear()  && currentMonth == getCurrentMonth() && currenDay > getCurrentDay());
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
            //t.focus();
            return false;
    }
    
}
function doFunction(Command,param)
{
    //alert("command:"+Command);
   
    
    
    
    if(Command=='loademp')
    {
       var empid=document.frmsurvey_report.txtEmployeeid.value;
 
      //  var check=notNull(null);
      //  if(check )
      //  {
              //  startwaiting(document.frmsurvey_report) ;
             //   service=null;
      
                //alert('load emp');
               
                var url="../../../../../Asset_Survey_Report?Command=loademp&txtEmployeeid="+empid;
              //  alert(url);
                var req=getTransport();
              // alert(req);
                req.open("GET",url,true);
                req.onreadystatechange=function()
                   {
                     handleResponse(req);
                    }
                if(window.XMLHttpRequest)
                        req.send(null);
                else req.send();  
      //  }        
    
      }
      
      else if(Command=='loadempview')
    { 
    
       var empid=document.frmsurvey_report.txtEmployeeid2.value;
 
      //  var check=notNull(null);
       // if            //(check )
        {
              //  startwaiting(document.frmsurvey_report) ;
              //  service=null;
           var url="../../../../../Asset_Survey_Report?Command=loadempview&txtEmployeeid="+empid
               
              //  var url="../../../../../AddAdditionalCharge_New?Command=loadempview&txtEmployeeid="+empid;
                var req=getTransport();
              //  alert(req);
                req.open("GET",url,true);
                req.onreadystatechange=function()
                   {
                     handleResponse1(req);
                    }
                if(window.XMLHttpRequest)
                        req.send(null);
                else req.send();  
        }        
   
    }


 }  
    
    function servicepopup()
{
   
    if (winemp && winemp.open && !winemp.closed) 
    {
       winemp.resizeTo(500,600);
       winemp.moveTo(200,200); 
       winemp.focus();
       return ;
    }
     //   alert('test');     
    winemp= window.open("../../../../../org/HR/HR1/EmployeeMaster/jsps/EmpServicePopup.jsp","Employeesearch","status=1,height=500,width=600,resizable=YES, scrollbars=yes"); 
    winemp.moveTo(250,250);  
    winemp.focus();
    
}

function doParentEmp(emp)
{
document.frmsurvey_report.txtEmployeeid.value=emp;
//document.frmsurvey_report.txtEmpDesig.value=Designation;
//document.frmsurvey_report.reset();
//clr();
doFunction('loademp','null');

}

//////////////   FOR JOB POPUP WINDOW //////////////////////
var winjob;

function jobpopup()
{
    jobflag=true;
    if (winjob && winjob.open && !winjob.closed) 
    {
       winjob.resizeTo(500,600);
       winjob.moveTo(200,200); 
       winjob.focus();
       return;

    }
    else
    {
        winjob=null
    }
        
    winjob= window.open("../../../../../org/HR/HR1/OfficeMaster/jsps/JobPopupJSP.jsp","JobSearch_for_SR","status=1,height=500,width=600,resizable=YES, scrollbars=yes"); 
    winjob.moveTo(200,200);  
 
    winjob.focus();
     
}
 /*function handleResponse()
{

    if(req.readyState==4)
    {
        if(req.status==200)
        {
            stopwaiting(document.frmsurvey_report);
            
            var baseResponse=req.responseXML.getElementsByTagName("response")[0];
            
            var tagcommand=baseResponse.getElementsByTagName("command")[0];
             //alert('test');
            var Command=tagcommand.firstChild.nodeValue;
            if(Command=="loadempview")
            {
                loadEmp(baseResponse);
            }
          }
        }
    } */   
function handleResponse(req)
{

    if(req.readyState==4)
    {
        if(req.status==200)
        {
         //   stopwaiting(document.frmsurvey_report);
            
            var baseResponse=req.responseXML.getElementsByTagName("response")[0];
          //  alert(req.responseXML.getElementsByTagName("response")[0]);
            var tagcommand=baseResponse.getElementsByTagName("command")[0];
             //alert('test');
            var Command=tagcommand.firstChild.nodeValue;
            if(Command=="loademp")
            {
                loadEmp(baseResponse);
            }
            if(Command=="loadempview")
            {
                loadEmployee(baseResponse);
            }
         }
       } 
    }   
    function handleResponse1(req)
{
   //  alert("inside*******");
    if(req.readyState==4)
    {
        if(req.status==200)
        {
         //   stopwaiting(document.frmsurvey_report);
            
            var baseResponse=req.responseXML.getElementsByTagName("response")[0];
           // alert(req.responseXML.getElementsByTagName("response")[0]);
            var tagcommand=baseResponse.getElementsByTagName("command")[0];
             //alert('test');
            var Command=tagcommand.firstChild.nodeValue;
         //  alert(Command);
            if(Command=="loadempview")
            {
                loadEmployee(baseResponse);
            }
         }
       } 
    }   

function loadEmployee(baseResponse)
{

    var flag=baseResponse.getElementsByTagName("flag")[0].firstChild.nodeValue;
     if(flag=="success")
    {
        // alert("success22222222");
           
        //   clr();
             
            var EMPLOYEE_ID=baseResponse.getElementsByTagName("EMPLOYEE_ID")[0].firstChild.nodeValue;
            var EMPLOYEE_NAME=baseResponse.getElementsByTagName("EMPLOYEE_NAME")[0].firstChild.nodeValue;
            var OFFICE=baseResponse.getElementsByTagName("OFFICE")[0].firstChild.nodeValue;
           // alert(OFFICE);
            var OFFICE_ID=baseResponse.getElementsByTagName("OFFICE_ID")[0].firstChild.nodeValue;
            var DESIGNATION_ID=baseResponse.getElementsByTagName("DESIGNATION_ID")[0].firstChild.nodeValue;
            var DESIGNATION=baseResponse.getElementsByTagName("DESIGNATION")[0].firstChild.nodeValue;
        document.frmsurvey_report.txtEmployeeid2.value=EMPLOYEE_ID;
        document.frmsurvey_report.txtEmployee2.value=EMPLOYEE_NAME;
        document.frmsurvey_report.txtOffice_Id2.value=OFFICE_ID;
        document.frmsurvey_report.txtOffice_Name2.value=OFFICE;
        document.frmsurvey_report.cmbDesignation2.value=DESIGNATION;
        document.frmsurvey_report.Desig_Id2.value=DESIGNATION_ID;
        // document.getElementById("cmbDesignation2").options[document.getElementById("cmbjournalno").selectedIndex].text=jvno;
        //document.frmsurvey_report.cmbDesignation2.text=DESIGNATION;
        }
        
    }
   

function loadEmp(baseResponse)
{

    var flag=baseResponse.getElementsByTagName("flag")[0].firstChild.nodeValue;
     if(flag=="success")
    {
        // alert("success");
           
        //   clr();
             
            var EMPLOYEE_ID=baseResponse.getElementsByTagName("EMPLOYEE_ID")[0].firstChild.nodeValue;
            var EMPLOYEE_NAME=baseResponse.getElementsByTagName("EMPLOYEE_NAME")[0].firstChild.nodeValue;
            var OFFICE=baseResponse.getElementsByTagName("OFFICE")[0].firstChild.nodeValue;
           // alert(OFFICE);
            var OFFICE_ID=baseResponse.getElementsByTagName("OFFICE_ID")[0].firstChild.nodeValue;
          //  alert(OFFICE_ID);
            var DESIGNATION_ID=baseResponse.getElementsByTagName("DESIGNATION_ID")[0].firstChild.nodeValue;
            var DESIGNATION=baseResponse.getElementsByTagName("DESIGNATION")[0].firstChild.nodeValue;
        document.frmsurvey_report.txtEmployeeid.value=EMPLOYEE_ID;
        document.frmsurvey_report.txtEmployee.value=EMPLOYEE_NAME;
        document.frmsurvey_report.txtOffice_Id.value=OFFICE_ID;
        document.frmsurvey_report.txtOffice_Name.value=OFFICE;
        document.frmsurvey_report.cmbDesignation.value=DESIGNATION;
        document.frmsurvey_report.Desig_Id.value=DESIGNATION_ID;
        
        }
    } 
       
 function numbersonly1(e,t)
    {
        var unicode=e.charCode? e.charCode : e.keyCode;
       if(unicode==13)
        {
          try{t.blur();}catch(e){}
          //document.frmsurvey_report.txtSNo.focus();
          return true;
        
        }
        if ( unicode!=8 && unicode !=9  )
        {
            if ((unicode<48||unicode>57 ) && (unicode<35||unicode>40 ) && unicode!=46 )
                return false 
        }
     }
