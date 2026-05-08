var com_id;
var seq=0;

//////////////   FOR DEPUTATION JOB POPUP WINDOW //////////////////////
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
    winjob= window.open("../../../../../org/HR/HR1/OfficeMaster/jsps/JobPopupJSP.jsp","JobSearch","status=1,height=500,width=500,resizable=YES, scrollbars=yes"); 
    winjob.moveTo(250,250);  
    winjob.focus();
    
}


function forChildOption()
{
   
     if (winjob && winjob.open && !winjob.closed) 
             winjob.officeSelection(true,true,true,false);
}

function doParentJob(jobid,deptid)
{
   document.getElementById("txtApp_offid").value=jobid;
   doFunction('office',jobid);
   return true;
}

window.onunload=function()
{
if (winjob && winjob.open && !winjob.closed) winjob.close();
}
////////////////////////////////////////////////////
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

function doFunction(Command,param)
{
        startwaiting(document.FasAcc_Headform_create);
        if(Command=="office")
        {   
            var oid=param;
            var url="../../../../../Acc_Head_Directory_Sys.view?Command=office&oid="+oid;
            //alert(url);
            var req=getTransport();
            req.open("POST",url,true); 
            req.onreadystatechange=function()
            {
               handleResponse(req);
            }   
                    req.send(null);
                    
        }
        
        else if(Command=="loadMinor")
        {  
            var txtMajor_id=document.getElementById("txtMajor_id").value;
            if(txtMajor_id=='I')
            {document.FasAcc_Headform_create.txtBal_type[0].checked=true;}
            else if(txtMajor_id=='E')
            {document.FasAcc_Headform_create.txtBal_type[1].checked=true;}
            else
            {
            document.FasAcc_Headform_create.txtBal_type[2].checked=true;
            }
            
            var url="../../../../../Acc_Head_Directory_Sys.view?Command=loadMinor&txtMajor_id="+txtMajor_id;
            //alert(url);
            var req=getTransport();
            req.open("POST",url,true); 
            req.onreadystatechange=function()
            {
               handleResponse(req);
            }   
                    req.send(null);
        }
        else if(Command=="subgroup")
        {  
            var txtProg_id=document.getElementById("txtProg_id").value;
            var txtProg_sub_id=param;
                var url="../../../../../Acc_Head_Directory_Sys.view?Command=subgroup&txtProg_id="+txtProg_id+"&txtProg_sub_id="+txtProg_sub_id;
                var req=getTransport();
                req.open("POST",url,true); 
                req.onreadystatechange=function()
                {
                   handleResponse(req);
                }   
                        req.send(null);
        }
        else if(Command=="checkCode")
        {   
            txtAcc_HeadCode=document.getElementById("txtAcc_HeadCode").value;
            var url="../../../../../Acc_Head_Directory_Sys.view?Command=checkCode&txtAcc_HeadCode="+txtAcc_HeadCode;
            //alert(url);
            var req=getTransport();
            req.open("POST",url,true); 
            req.onreadystatechange=function()
            {
               handleResponse(req);
            }   
                    req.send(null);
        }
 }   

function handleResponse(req)
{  
    if(req.readyState==4)
    {
        if(req.status==200)
        {   
            //alert(req.responseTEXT);
            stopwaiting(document.FasAcc_Headform_create) ;
            var baseResponse=req.responseXML.getElementsByTagName("response")[0];
            var tagcommand=baseResponse.getElementsByTagName("command")[0];
            var Command=tagcommand.firstChild.nodeValue;
            if(Command=="office")
            {
                loadOffice(baseResponse);
            }
            else if(Command=="loadMinor")
            {
                loadMinor(baseResponse);
            }
            else if(Command=="subgroup")
            {
                load_subgroup(baseResponse);
            }
             else if(Command=="checkCode")
            {
                loadcheckCode(baseResponse);
            }
        }
    }
}
function load_subgroup(baseResponse)
{
 var flag=baseResponse.getElementsByTagName("flag")[0].firstChild.nodeValue;
    
    if(flag=="success")
    {   
        var Maj_id=baseResponse.getElementsByTagName("sub_id");
        var items_maj=new Array();
        var items_min=new Array();
        var items_desc=new Array();
        var min_id=document.getElementById("txtProg_sub_id");
        
        for(var k=0;k<Maj_id.length;k++)
        {
             items_min[k]=baseResponse.getElementsByTagName("sub_id")[k].firstChild.nodeValue;
             items_desc[k]=baseResponse.getElementsByTagName("sub_desc")[k].firstChild.nodeValue;
        }
        min_id.innerHTML="";
        var option=document.createElement("OPTION");
        option.text="--Select Sub Group--";
        option.value=0;
        try
        {
            min_id.add(option);
        }catch(errorObject)
        {
            min_id.add(option,null);
        }
        
        for(var k=0;k<Maj_id.length;k++)
        {   
          var option=document.createElement("OPTION");
          option.text=items_desc[k];
          option.value=items_min[k];
           try
          {
              min_id.add(option);
          }
          catch(errorObject)
          {
              min_id.add(option,null);
          }
          
        }
        var prog_subid=baseResponse.getElementsByTagName("prog_subid")[0].firstChild.nodeValue;;
        document.getElementById("txtProg_sub_id").value=prog_subid;
    }
    //else
      //alert("No data found in Minor Group");

}
function loadcheckCode(baseResponse)
{
    var flag=baseResponse.getElementsByTagName("flag")[0].firstChild.nodeValue;
    
    if(flag=="success")
    {
      var hid=baseResponse.getElementsByTagName("hid")[0].firstChild.nodeValue;
      alert("Account Head Code '"+hid+"' Already Exist");
      document.getElementById("txtAcc_HeadCode").value="";
      document.getElementById("txtAcc_HeadCode").focus();
    }
}

function loadOffice(baseResponse)
{
    var flag=baseResponse.getElementsByTagName("flag")[0].firstChild.nodeValue;
    
    if(flag=="success")
    {  
        var oid=baseResponse.getElementsByTagName("oid")[0].firstChild.nodeValue;
        var oname=baseResponse.getElementsByTagName("oname")[0].firstChild.nodeValue;
        var wing =baseResponse.getElementsByTagName("wing")[0].firstChild.nodeValue;
        
        var WNature_id =baseResponse.getElementsByTagName("WNature_id")[0].firstChild.nodeValue;
        var WNature_Desc =baseResponse.getElementsByTagName("WNature_Desc")[0].firstChild.nodeValue;
        
         if(WNature_id=="null")
         document.getElementById("txtApp_for_workid").value="";
         else
         document.getElementById("txtApp_for_workid").value=WNature_id;
         if(WNature_Desc=="null")
         document.getElementById("txtApp_for_workDesc").value="";
         else
         document.getElementById("txtApp_for_workDesc").value=WNature_Desc;
         
        document.getElementById("txtApp_offid").value=oid;
        document.getElementById("txtApp_OffName").value=oname;
        
        var min_id=document.getElementById("txtApp_wingId");            // Get the select combo id
        if(wing=="Y")
        {
            var Maj_id=baseResponse.getElementsByTagName("wid");
            //var items_maj=new Array();
            var items_min=new Array();
            var items_desc=new Array();
            
            
            for(var k=0;k<Maj_id.length;k++)
            {
                 items_min[k]=baseResponse.getElementsByTagName("wid")[k].firstChild.nodeValue;
                 items_desc[k]=baseResponse.getElementsByTagName("wname")[k].firstChild.nodeValue;
            }
            min_id.innerHTML="";                                        // makes its inner null
            var option=document.createElement("OPTION");
            option.text="--Select Wing--";
            option.value=0;
            try
            {
                min_id.add(option);
            }catch(errorObject)
            {
                min_id.add(option,null);
            }
            
            for(var k=0;k<Maj_id.length;k++)
            {   
              var option=document.createElement("OPTION");
              option.text=items_desc[k];
              option.value=items_min[k];
               try
              {
                  min_id.add(option);
              }
              catch(errorObject)
              {
                  min_id.add(option,null);
              }
              
            }
        }
        if(wing=="N" || wing=="null")
           {    
                min_id.innerHTML=""; 
                var option=document.createElement("OPTION");
                option.text="--Select Wing--";
                option.value=0;
                try
                {
                    min_id.add(option);
                }catch(errorObject)
                {
                    min_id.add(option,null);
                }
            }
    }
    else 
    {
     var oid=baseResponse.getElementsByTagName("oid")[0].firstChild.nodeValue;
     alert("Office Id '"+oid+"' doesn't Exist");
     document.getElementById("txtApp_offid").value="";
     document.getElementById("txtApp_OffName").value="";
    }
}

function loadMinor(baseResponse)
{

    var flag=baseResponse.getElementsByTagName("flag")[0].firstChild.nodeValue;
    
    if(flag=="success")
    {   
        var Maj_id=baseResponse.getElementsByTagName("Maj_id");    
        var items_maj=new Array();
        var items_min=new Array();
        var items_desc=new Array();
        var min_id=document.getElementById("txtMinor_id");
        
        for(var k=0;k<Maj_id.length;k++)
        {
             items_maj[k]=baseResponse.getElementsByTagName("Maj_id")[k].firstChild.nodeValue;   
             items_min[k]=baseResponse.getElementsByTagName("Min_id")[k].firstChild.nodeValue;
             items_desc[k]=baseResponse.getElementsByTagName("Min_desc")[k].firstChild.nodeValue;
        }
        min_id.innerHTML="";
        var option=document.createElement("OPTION");
        option.text="--Select Minor Group--";
        option.value=0;
        try
        {
            min_id.add(option);
        }catch(errorObject)
        {
            min_id.add(option,null);
        }
        
        for(var k=0;k<Maj_id.length;k++)
        {   
          var option=document.createElement("OPTION");
          option.text=items_desc[k];
          option.value=items_min[k];
           try
          {
              min_id.add(option);
          }
          catch(errorObject)
          {
              min_id.add(option,null);
          }
          
        }
    }
    else
      alert("No data found in Minor Group");
}


function checkForRedundancy(sc)
{
  try
  {
      var tbody=document.getElementById("grid_body");
      
      if(tbody.rows.length>0)
        {
         fg=true;         
        }
        else
         return true;
      if(fg)
      {
        var i;
        var found=false;
        rows=tbody.getElementsByTagName("tr");
        for(i=0;i<rows.length;i++)
        {
            var cells=rows[i].cells;
            
            if(cells.item(1).lastChild.nodeValue==sc)
            {
              return false;
              break;
            }
        }
              
      }
  }
  catch(e)
  {
  alert(e);
  }
  return true;
}

function loadSL(txtSL_Value)
{  
    document.getElementById("txtSL_code").value=txtSL_Value;
}

function enableSub_Ledger(opt)
{
    if(opt=="Y")
    {
        document.getElementById("sub_ledge_dis").style.display='block';
        document.getElementById("grid").style.display='block';
    }
    else if(opt=="N")
    {
        document.getElementById("sub_ledge_dis").style.display='none';
        document.getElementById("grid").style.display='none';
    }
}

//~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
function sub_Ledger_man(opt)
{
  if (opt=="Y")
  {
 //  document.FasAcc_Headform_create.txtsub_ledger_YN[0].checked=true;
   document.getElementById("no_applicalbe").style.display='none'
  }
 else if (opt=="N")
  { 
//   document.FasAcc_Headform_create.txtsub_ledger_YN[1].checked=true;
   document.getElementById("no_applicalbe").style.display='block'
  }

}

//~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~

function enableUsage(opt)
{
    if(opt=="Y")
    {
        document.FasAcc_Headform_create.txtlast_date.value="";
        document.FasAcc_Headform_create.txtRef_no.value="";
        document.FasAcc_Headform_create.txtRef_date.value="";
        document.FasAcc_Headform_create.txtlast_date.disabled=true;
        document.FasAcc_Headform_create.txtRef_no.disabled=true;
        document.FasAcc_Headform_create.txtRef_date.disabled=true;
    }
    else if(opt=="N")
    {
        document.FasAcc_Headform_create.txtlast_date.disabled=false;
        document.FasAcc_Headform_create.txtRef_no.disabled=false;
        document.FasAcc_Headform_create.txtRef_date.disabled=false;
    }
}

function enableOffice(opt)
{  
    if(opt=="Y")
    {
        document.FasAcc_Headform_create.txtApp_for_workid.disabled=false;
        document.FasAcc_Headform_create.txtApp_for_workDesc.disabled=false;
        document.FasAcc_Headform_create.txtApp_offid.disabled=false;
        //document.FasAcc_Headform_create.txtApp_OffName.disabled=false;
        document.FasAcc_Headform_create.txtApp_wingId.disabled=false;
        //document.FasAcc_Headform_create.txtApp_wingName.disabled=false;
    }
    else if(opt=="N")
    {
        document.FasAcc_Headform_create.txtApp_offid.value="";
        document.FasAcc_Headform_create.txtApp_OffName.value="";
        document.FasAcc_Headform_create.txtApp_wingId.value=0;
        document.FasAcc_Headform_create.txtApp_for_workid.value="";
        document.FasAcc_Headform_create.txtApp_for_workDesc.value="";
        document.FasAcc_Headform_create.txtApp_for_workid.disabled=true;
        document.FasAcc_Headform_create.txtApp_for_workDesc.disabled=true;
        document.FasAcc_Headform_create.txtApp_offid.disabled=true;
        //document.FasAcc_Headform_create.txtApp_OffName.disabled=true;
        document.FasAcc_Headform_create.txtApp_wingId.disabled=true;
        //document.FasAcc_Headform_create.txtApp_wingName.disabled=false;
    }
}
function loadTable(scod)
{
        com_id=scod;
        //document.FasAcc_Headform_create.cmdadd.disabled=true;
        var r=document.getElementById(scod);
        var rcells=r.cells;
        try {document.getElementById("txtSL_code").value=rcells.item(1).lastChild.nodeValue;}catch(e){}
        try{document.getElementById("txtSL_Desc").value=rcells.item(1).lastChild.nodeValue;}catch(e){}
      
       
    document.FasAcc_Headform_create.cmdupdate.style.display='block';
    document.FasAcc_Headform_create.cmddelete.disabled=false;
    document.FasAcc_Headform_create.cmdadd.style.display='none';
}
        
function clearall()
{
 document.getElementById("txtSL_code").value="";
 document.getElementById("txtSL_Desc").value=0;
 document.FasAcc_Headform_create.cmdadd.style.display='block';
 document.FasAcc_Headform_create.cmdupdate.style.display='none';
 document.FasAcc_Headform_create.cmddelete.disabled=true;
}

        
        
        
function ADD_GRID()
{
        if(document.getElementById("txtSL_Desc").value==0)
        {
        alert("select a Sub-Ledger type");
        return false;
        }
        var tbody=document.getElementById("grid_body");
        var t=0;
        var exist=document.getElementById("txtSL_code").value;
        if(checkForRedundancy(exist))
        {
            var x=document.getElementById("txtSL_Desc");
            var items=new Array();
            items[0]=document.getElementById("txtSL_code").value;
            items[1]=document.getElementById("txtSL_Desc").options[document.getElementById("txtSL_Desc").selectedIndex].text;                
            tbody=document.getElementById("grid_body");
            var mycurrent_row=document.createElement("TR");
            seq=seq+1;
            mycurrent_row.id=seq;
            //alert("row ID"+mycurrent_row.id);
            var cell=document.createElement("TD");
            var anc=document.createElement("A");
            var url="javascript:loadTable('"+mycurrent_row.id+"')";
            anc.href=url;
            var txtedit=document.createTextNode("EDIT");
            anc.appendChild(txtedit);
            cell.appendChild(anc);
            mycurrent_row.appendChild(cell);
            var i=0;
            var cell2;
            
            for(i=0;i<2;i++)
            {   
                cell2=document.createElement("TD");
                  if(i==0)
                  {
                      var HSL_code=document.createElement("input");// HIDDEN INPUT TO GET THE SAME VALUE OF THE SERNO INPUT
                      HSL_code.type="hidden";
                      HSL_code.name="HSL_code";
                      HSL_code.value=items[i];
                      cell2.appendChild(HSL_code);
                  }
                  if(i==1)
                  {
                      var HSL_type=document.createElement("input");// HIDDEN INPUT TO GET THE SAME VALUE OF THE SERNO INPUT
                      HSL_type.type="hidden";
                      HSL_type.name="HSL_type";
                      HSL_type.value=items[i];
                      cell2.appendChild(HSL_type);
                  }
                var currentText=document.createTextNode(items[i]);
                cell2.appendChild(currentText);
                mycurrent_row.appendChild(cell2);
            }
            tbody.appendChild(mycurrent_row);
      }
      else
        {
          alert("Subledger Type already exist...");
        }
}
function update_GRID()
{      
        if(document.getElementById("txtSL_Desc").value==0)
        {
        alert("select a Sub-Ledger type");
        return false;
        }
        var exist=document.getElementById("txtSL_code").value;
        if(checkForRedundancy(exist))
        {
             var items=new Array();
             try {items[0]=document.getElementById("txtSL_code").value}catch(e){}
             try{items[1]=document.getElementById("txtSL_Desc").options[document.getElementById("txtSL_Desc").selectedIndex].text;}catch(e){}
            var r=document.getElementById(com_id);
            var rcells=r.cells;
            for(i=0,j=1;i<2;i++,j++)
            {
             try{
                rcells.item(j).firstChild.value=items[i];           // for hidden field
                rcells.item(j).lastChild.nodeValue=items[i];
                }
                catch(e){
                }
            }
            alert("Record Updated");
            clearall();
       }
       else
        {
          alert("Subledger code already exist...");
        }
}

function delete_GRID()
{
        if(confirm("Do you want to delete ?"))
        {
        var tbody=document.getElementById("mytable");
        var r=document.getElementById(com_id);
        var ri=r.rowIndex;
        tbody.deleteRow(ri);
        clearall();
        }
}
function numbersonly(e)
{   
        var unicode=e.charCode? e.charCode : e.keyCode
        if (unicode!=8 )
        {
            if(unicode==9)
              return true
            else if (unicode<48||unicode>57) 
              return false 
             
        }
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
//        }
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

function calins(e,t)
{
    var unicode=e.charCode? e.charCode : e.keyCode;
        //alert(unicode);
        //if(unicode !=8)
        if(t.value.length==2 || t.value.length==5)
                t.value=t.value + '/';
        if (unicode!=8 && unicode !=9 && unicode!=37 && unicode !=39  )
        {
        
            
            if (unicode<48||unicode>57 ) 
                return false 
        }
       

}
    
function numbersonly1(e,t)
    {
        var unicode=e.charCode? e.charCode : e.keyCode;
       if(unicode==13)
        {
          //t.blur();
          //return true;-------------------- for taking action when press ENTER
        
        }
        if (unicode!=8 && unicode !=9  )
        {
            if (unicode<48||unicode>57 ) 
                return false 
        }
     }
function sixdigit()
{

 if(document.getElementById("txtAcc_HeadCode").value.length!=0)
    {
        if((document.getElementById("txtAcc_HeadCode").value).length<6)
        {
        alert("Account Head Code shouldn't be less than 6 digit number");
        document.getElementById("txtAcc_HeadCode").value="";
        document.getElementById("txtAcc_HeadCode").focus();
        return false;
        }
        else if((document.getElementById("txtAcc_HeadCode").value).length>6)
        {
        alert("Account Head Code shouldn't be greater than 6 digit number");
        document.getElementById("txtAcc_HeadCode").value="";
        document.getElementById("txtAcc_HeadCode").focus();
        return false;
        }
    }
}
function call_clr()
{
            document.getElementById("txtAcc_HeadCode").value="";
            document.getElementById("txtAcc_HeadDesc").value="";
            document.getElementById("txtMajor_id").value="";
            document.getElementById("txtMinor_id").value=0;
            document.getElementById("txtProg_id").value=0;
            document.getElementById("txtProg_sub_id").value=0;
            document.FasAcc_Headform_create.txtBal_type[0].checked=true;
            document.FasAcc_Headform_create.txtTB_mandatory[0].checked=true;
            document.getElementById("txtCrea_date").value="";
            document.getElementById("txtRemarks").value="";
            document.FasAcc_Headform_create.txtaccess[1].checked=true;
            document.FasAcc_Headform_create.txtApp_offid.value="";
            document.FasAcc_Headform_create.txtApp_OffName.value="";
            document.FasAcc_Headform_create.txtApp_wingId.value=0;
            document.FasAcc_Headform_create.txtApp_for_workid.value="";
            document.FasAcc_Headform_create.txtApp_for_workDesc.value="";
            document.getElementById("txtSL_code").value="";
            document.getElementById("txtSL_Desc").value=0;
            
            var min_id=document.getElementById("txtMinor_id"); 
            min_id.innerHTML="";
                var option=document.createElement("OPTION");
                option.text="--Select Minor Group--";
                option.value=0;
                try
                {
                    min_id.add(option);
                }catch(errorObject)
                {
                    min_id.add(option,null);
                }
    
            min_id=document.getElementById("txtApp_wingId");
            min_id.innerHTML="";
                var option=document.createElement("OPTION");
                option.text="--Select Wing--";
                option.value=0;
                try
                {
                    min_id.add(option);
                }catch(errorObject)
                {
                    min_id.add(option,null);
                }
                //alert("b4 0ff")
            enableOffice('N');
            document.FasAcc_Headform_create.txtsub_ledger_YN[1].checked=true;
            
            var tbody=document.getElementById("grid_body");
            var t=0;
            for(t=tbody.rows.length-1;t>=0;t--)
            {
               tbody.deleteRow(0);
            }
            
            document.getElementById("sub_ledge_dis").style.display='none';
            document.getElementById("grid").style.display='none';
}
function clrForm()
{
        if(window.confirm("Do you want to clear ALL fields ?"))
        {
           call_clr();
        }
}
/*function check_group()
{
  if(document.getElementById("txtProg_id").value==0 && document.getElementById("txtProg_sub_id").value==0)
    {
      return true;    
    }
  if(document.getElementById("txtProg_id").value==document.getElementById("txtProg_sub_id").value)
    {
        alert("Selection of Sub-Group-1 and Sub-Group-2 shouldn't be Same");
        document.getElementById("txtProg_sub_id").value=0;
        document.getElementById("txtProg_sub_id").focus();
        return false;    
    }
}*/
function checkNull()
{
if(document.getElementById("txtAcc_HeadCode").value.length==0)
{
    alert("Enter the Account Head Code");
    document.getElementById("txtAcc_HeadCode").focus();
    return false;
}
 if(document.getElementById("txtAcc_HeadDesc").value.length==0)
{
    alert("Enter the Account Head Description");
    document.getElementById("txtAcc_HeadDesc").focus();
    return false;    
}
if(document.getElementById("txtMajor_id").value=="")
{
    alert("Select the Major Group");
    document.getElementById("txtMajor_id").focus();
    return false;    
}
 if(document.getElementById("txtMinor_id").value==0)
{
    alert("Select the Minor Group");
    document.getElementById("txtMinor_id").focus();
    return false;    
}

 if(document.getElementById("txtCrea_date").value.length==0)
{
    alert("Enter the Date of Creation");
    document.getElementById("txtCrea_date").focus();
    return false;    
}
/*if(document.FasAcc_Headform_create.txtUse_status[1].checked==true)
{
    if(document.getElementById("txtlast_date").value.length==0)
    {
    alert("Enter the last used Date");
    //document.getElementById("txtlast_date").focus();
    return false;    
    }
    if(document.getElementById("txtRef_no").value.length==0)
    {
    alert("Enter the File Reference Number");
    //document.getElementById("txtRef_no").focus();
    return false;    
    }
    if(document.getElementById("txtRef_date").value.length==0)
    {
    alert("Enter the File Reference Date");
    //document.getElementById("txtRef_date").focus();
    return false;    
    }
}*/
 if(document.FasAcc_Headform_create.txtaccess[0].checked==true && document.getElementById("txtApp_offid").value.length==0)
{
    alert("Enter the Office Id,if Access Restricted");
    document.getElementById("txtApp_offid").focus();
    return false;    
}
if(document.FasAcc_Headform_create.txtsub_ledger_YN[0].checked==true)
{
    var tbody=document.getElementById("grid_body");
   // alert("tbody length:"+tbody.rows.length+" "+document.getElementById("txtsub_ledger_YN").value)
      if(tbody.rows.length==0)
        {
         alert("Add Sub-Ledger in Sub-Ledger Types ");
         return false;
        }
}
return true;
}

function check_leng(val)
{
if(val.length>=250)
return false;
}

function exit()
{
       self.close();
}