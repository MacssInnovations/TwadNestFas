var checkdate="";
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

function checkNull()
{
        var tbody=document.getElementById("grid_body_Acq");
      
        if(document.getElementById("cmbAcc_UnitCode").value=="")
        {
            alert("Select the Account Unit code");
            //document.getElementById("txtAcc_HeadDesc").focus();
            return false;    
        }
        if(document.getElementById("cmbOffice_code").value=="")
        {
            alert("Select the Office Code");
            //document.getElementById("cmbOffice_code").focus();
            return false;
        }
        if(document.getElementById("txtCB_Year").value.length==0)
        {
            alert("Enter the year");
            //document.getElementById("cmbOffice_code").focus();
            return false;
        }
        if(document.getElementById("txtCheque_DD_NO").value=="")
        {
            alert("Select the Cheque number");
            //document.getElementById("cmbOffice_code").focus();
            return false;
        }
        if(tbody.rows.length==0)
        {
            alert("No records found for disbursement");
            //document.getElementById("txtAmount").focus();
            return false; 
        }
       
        return true;
}

function call_clr()
{
    document.getElementById("txtCheque_DD_NO").value="";
    document.getElementById("txtDrawl_date").value="";
    document.getElementById("txtAmount").value="";
    document.getElementById("txtRemarks").value="";
    
    var tbody=document.getElementById("grid_body_Acq");
    var t=0;
    for(t=tbody.rows.length-1;t>=0;t--)
    {
       tbody.deleteRow(0);
    }
}

function clrForm()
{
   if(window.confirm("Do you want to clear ALL fields ?"))
 {
    call_clr();
 }
}

///////////////////////////////////////  Numbers only fields
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
function doFunction(Command,param)
{   
        var cmbAcc_UnitCode=document.getElementById("cmbAcc_UnitCode").value    
        var cmbOffice_code=document.getElementById("cmbOffice_code").value
       // var cmbStatus=document.getElementById("cmbStatus").value;
           var txtCB_Year=document.getElementById("txtCB_Year").value;
            var txtCB_Month=document.getElementById("txtCB_Month").value;
            //alert(txtCB_Month.length+"month")
        if(Command=='load_chequeNO')
        {
            if(txtCB_Year.length!=0 && txtCB_Month.length!=0)
            {
                var url="../../../../../UDPRegister_Create.view?Command=load_chequeNO&cmbAcc_UnitCode="+cmbAcc_UnitCode+
                "&cmbOffice_code="+cmbOffice_code+"&txtCB_Year="+txtCB_Year+"&txtCB_Month="+txtCB_Month;
                //"&cmbOffice_code="+cmbOffice_code+"&txtCB_Year="+txtCB_Year+"&txtCB_Month="+txtCB_Month+"&txtCreat_By_Module="+txtCreat_By_Module+"&cmbStatus="+cmbStatus;
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
      else if(Command=='load_cheque_details')
      {
        var txtCheque_DD_NO=document.getElementById("txtCheque_DD_NO").value;
            if(txtCB_Year.length!=0 && txtCB_Month.length!=0 && txtCheque_DD_NO!="")
            {
                var url="../../../../../UDPRegister_Create.view?Command=load_cheque_details&cmbAcc_UnitCode="+cmbAcc_UnitCode+
                "&cmbOffice_code="+cmbOffice_code+"&txtCB_Year="+txtCB_Year+"&txtCB_Month="+txtCB_Month+"&txtCheque_DD_NO="+txtCheque_DD_NO;
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
}

function handleResponse(req)
{ 
    if(req.readyState==4)
    {
        if(req.status==200)
        {   
            var baseResponse=req.responseXML.getElementsByTagName("response")[0];
            var tagcommand=baseResponse.getElementsByTagName("command")[0];
            var Command=tagcommand.firstChild.nodeValue;
                    

            if(Command=="load_chequeNO")
            { 
                load_chequeNO(baseResponse);
            }
            else if(Command=="load_cheque_details")
            {
                load_cheque_details(baseResponse);
            }
        }
    }
}

function load_chequeNO(baseResponse)
{ 
    var flag=baseResponse.getElementsByTagName("flag")[0].firstChild.nodeValue;
    if(flag=="success")
    {
        var Cmb_chequeNO=document.getElementById("txtCheque_DD_NO");
        clear_Combo_local(Cmb_chequeNO);
        
        var LengOf_cheq_no=baseResponse.getElementsByTagName("cheq_no");
        for(var i=0;i<LengOf_cheq_no.length;i++)
        {
            var cheq_no=baseResponse.getElementsByTagName("cheq_no")[i].firstChild.nodeValue;
            var option=document.createElement("OPTION");
            option.text=cheq_no;
            option.value=cheq_no;
            try
            {
                Cmb_chequeNO.add(option);
            }catch(errorObject)
            {
                Cmb_chequeNO.add(option,null);
            }
        }
    }
    else
    {
        alert('No Cheque found');
        var Cmb_chequeNO=document.getElementById("txtCheque_DD_NO");
        clear_Combo_local(Cmb_chequeNO);
       
    }
}
function load_cheque_details(baseResponse)
{
    var tbody=document.getElementById("grid_body_Acq");
    var t=0;
    for(t=tbody.rows.length-1;t>=0;t--)
    {
       tbody.deleteRow(0);
    }
    
    var flag=baseResponse.getElementsByTagName("flag")[0].firstChild.nodeValue;
    if(flag=="success")
    {
        //alert(baseResponse.getElementsByTagName("checkdate")[0].firstChild.nodeValue);
        checkdate=baseResponse.getElementsByTagName("checkdate")[0].firstChild.nodeValue;
        document.getElementById("txtDrawl_date").value=baseResponse.getElementsByTagName("date_drwal")[0].firstChild.nodeValue;
        document.getElementById("txtAmount").value=baseResponse.getElementsByTagName("tot_cheq_amt")[0].firstChild.nodeValue;
        var remark=baseResponse.getElementsByTagName("REMARKS")[0].firstChild.nodeValue;
        if(remark!="null")
            document.getElementById("txtRemarks").value=remark;
        else
            document.getElementById("txtRemarks").value="";
        
        
                var acq_Vou_NO=baseResponse.getElementsByTagName("acq_Vou_NO");
                var Acq_tbody=document.getElementById("grid_body_Acq");
                
                for(var k=0;k<acq_Vou_NO.length;k++)
                {
                        var items=new Array();
                        items[0]=baseResponse.getElementsByTagName("acq_Vou_NO")[k].firstChild.nodeValue;
                        items[1]=baseResponse.getElementsByTagName("acq_roll_NO")[k].firstChild.nodeValue;
                        items[2]=baseResponse.getElementsByTagName("disbur_off_id")[k].firstChild.nodeValue;
                        items[3]=baseResponse.getElementsByTagName("disbur_off_name")[k].firstChild.nodeValue;
                        items[4]=baseResponse.getElementsByTagName("emp_id")[k].firstChild.nodeValue;
                        items[5]=baseResponse.getElementsByTagName("emp_id")[k].firstChild.nodeValue+" - "+baseResponse.getElementsByTagName("emp_disgn")[k].firstChild.nodeValue;
                        items[6]=baseResponse.getElementsByTagName("tot_Acq_amt")[k].firstChild.nodeValue;
                          
                        var mycurrent_row=document.createElement("TR");
                        var cell2;
                      
                              cell2=document.createElement("TD");
                
                                  var Acq_rollNo=document.createElement("input");
                                  Acq_rollNo.type="hidden";
                                  Acq_rollNo.name="Acq_rollNo";
                                  Acq_rollNo.value=items[1];
                                  cell2.appendChild(Acq_rollNo);
                                  var currentText=document.createTextNode(items[1]);
                                  cell2.appendChild(currentText);
                                  mycurrent_row.appendChild(cell2);
                            
                             cell2=document.createElement("TD");
                
                                  var Acq_vouNo=document.createElement("input");
                                  Acq_vouNo.type="hidden";
                                  Acq_vouNo.name="Acq_vouNo";
                                  Acq_vouNo.value=items[0];
                                  cell2.appendChild(Acq_vouNo);
                                  var currentText=document.createTextNode(items[0]);
                                  cell2.appendChild(currentText);
                                  mycurrent_row.appendChild(cell2);
                                  
                             
                              cell2=document.createElement("TD");
                                   var Acq_offID=document.createElement("input");
                                  Acq_offID.type="hidden";
                                  Acq_offID.name="Acq_offID";
                                  Acq_offID.value=items[2];
                                  cell2.appendChild(Acq_offID);
                                   var currentText=document.createTextNode(items[3]);
                                  cell2.appendChild(currentText);
                                   mycurrent_row.appendChild(cell2);
                                   
                             cell2=document.createElement("TD");
                                  var Acq_empID=document.createElement("input");
                                  Acq_empID.type="hidden";
                                  Acq_empID.name="Acq_empID";
                                  Acq_empID.value=items[4];
                                  cell2.appendChild(Acq_empID);
                                   var currentText=document.createTextNode(items[5]);
                                  cell2.appendChild(currentText);
                                mycurrent_row.appendChild(cell2);
                                
                            cell2=document.createElement("TD");
                                  var Acq_Amount=document.createElement("input");
                                  Acq_Amount.type="hidden";
                                  Acq_Amount.name="Acq_Amount";
                                  Acq_Amount.value=items[6];
                                  cell2.appendChild(Acq_Amount);
                                   var currentText=document.createTextNode(items[6]);
                                  cell2.appendChild(currentText);
                                mycurrent_row.appendChild(cell2);
                                
                             cell2=document.createElement("TD");
                                  var DateOf_disburse=document.createElement("input");
                                  DateOf_disburse.type="text";
                                  DateOf_disburse.name="DateOf_disburse";
                                  //alert('zxsz');
                                  DateOf_disburse.onfocus=function()
                                  {
                                    vDateType=3;
                                  }
                                 
                                  if (window.navigator.appName.toLowerCase().indexOf("microsoft") > -1) 
                                  { // IE;
                                        
                                          DateOf_disburse.onkeypress=function()
                                          {
                                                    return calins(event,this);
                                              
                                          }
                                          DateOf_disburse.onblur=function()
                                          {
                                           return call_date(this);
                                          }
                                  }
                                  else
                                  {
                                      if (window.navigator.appName.toLowerCase().indexOf("netscape") > -1) // Firefox
                                      { 
                                                DateOf_disburse.setAttribute("onkeypress","return calins(event,this)");
                                                DateOf_disburse.setAttribute("onblur","return call_date(this)");
                                      }
                                      else
                                        alert("Error: This application does not support your browser.  Try again using IE or Firefox.");
                                    }
                                
                                  //onfocus="javascript:vDateType='3';"
                           //onkeypress="return calins(event,this);"
                           //onblur="call_date(this);"/>
                           		
                                   // DateOf_disburse.onkeypress=function()
                                   
                                    //DateOf_disburse.setAttribute("onkeypress","return alert('hi')");
                           
                                  DateOf_disburse.setAttribute("size","10");
                                  //DateOf_disburse.maxlength=10;
                                  cell2.appendChild(DateOf_disburse);
                                mycurrent_row.appendChild(cell2);
                            
                        Acq_tbody.appendChild(mycurrent_row);
                }
    } 
    else
    {
        document.getElementById("txtDrawl_date").value="";
        document.getElementById("txtAmount").value="";
        document.getElementById("txtRemarks").value="";
        checkdate="";
        alert("No records found for disbursement");
        
    }
}

function clear_Combo_local(combo)
{
        //alert(combo.id)
        var combo_Id=document.getElementById(combo.id);   
        combo_Id.innerHTML="";
        var option=document.createElement("OPTION");
        option.text="--Select Cheque Number--";
        option.value="";
        try
        {
            combo_Id.add(option);
        }catch(errorObject)
        {
            combo_Id.add(option,null);
        }
}



//--------------- date validation

function call_date(dateCtrl)                        // TB_checking 
{
  if(checkdt(dateCtrl))
   {
        var date_val=dateCtrl.value;
        var splited_date=date_val.split("/");
        var grid_date=new Date(splited_date[2],parseInt(splited_date[1])-1,splited_date[0]);
        //alert(grid_date);
        if(date_val>checkdate)
        {
            alert("Date of disbursement should be  \n within 3 months from drawl date");
            dateCtrl.value="";
        }
       /* var txtDrawl_date=document.getElementById("txtDrawl_date").value;
        var splited_date=txtDrawl_date.split("/");
        //var DrawDate_given=new Date(splited_date[2],(parseInt(splited_date[1])-1),splited_date[0]);
        var DrawDate_added=new Date(splited_date[2],(parseInt(splited_date[1])-1)+3,splited_date[0]);   // Added 3 months with the txtDrawl_date value
         
        var one_day=1000*60*60*24;
        if((Math.ceil((DrawDate_added.getTime()-grid_date.getTime())/(one_day)))>90 || (Math.ceil((DrawDate_added.getTime()-grid_date.getTime())/(one_day)))<0)
        {
            alert("Date of disbursement should be  \n within 3 months from drawl date");
            dateCtrl.value="";
        }*/
   }
   else
    alert('Invalid date');
 
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
            return false
    }
    
}

///////////////////////////////////////////// calender input 
function calins(e,t)
{
    if(t.value.length>=10)
        return false;
    else
    {
        var unicode=e.charCode? e.charCode : e.keyCode;
        if(t.value.length==2 || t.value.length==5)
                t.value=t.value + '/';
        if (unicode!=8 && unicode !=9 && unicode!=37 && unicode !=39  )
        {
                 
            if (unicode<48||unicode>57 ) 
                return false 
        }
    }
}

function exit()
{
//var w=window.open(window.location.href,"_self");
//w.close();
self.close();
}