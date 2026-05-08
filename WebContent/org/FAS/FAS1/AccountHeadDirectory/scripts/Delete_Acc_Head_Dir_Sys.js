//alert("hello")
var com_id;
var seq=0;

//////////////   FOR DEPUTATION JOB POPUP WINDOW //////////////////////
var winAccHeadCode;

function AccHeadpopup()
{
    if (winAccHeadCode && winAccHeadCode.open && !winAccHeadCode.closed) 
    {
       winAccHeadCode.resizeTo(500,500);
       winAccHeadCode.moveTo(250,250); 
       winAccHeadCode.focus();
    }
    else
    {
        winAccHeadCode=null
    }
        
    winAccHeadCode= window.open("../../../../../org/FAS/FAS1/AccountHeadDirectory/jsps/Acc_Head_Dir_List_InUse.jsp","AccountHeadSearch","status=1,height=500,width=500,resizable=YES, scrollbars=yes"); 
    winAccHeadCode.moveTo(250,250);  
    winAccHeadCode.focus();
    
}

function doParentAccHead(code)
{
   document.getElementById("txtAcc_HeadCode").value=code;
   doFunction('checkCode','null');
   return true;
}
window.onunload=function()
{
if (winAccHeadCode && winAccHeadCode.open && !winAccHeadCode.closed) winAccHeadCode.close();
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
         
         if(Command=="checkCode")
         {  
            txtAcc_HeadCode=document.getElementById("txtAcc_HeadCode").value;
            
            if(txtAcc_HeadCode.length>=6)
            {
            startwaiting(document.FasAcc_Headform_Delete);
            var url="../../../../../Delete_Acc_Head_Dir_Sys.view?Command=checkCode&txtAcc_HeadCode="+txtAcc_HeadCode;
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
        else if(Command=="checkCode1")
         {  
            txtAcc_HeadCode=document.getElementById("txtAcc_HeadCode").value;
//            alert(txtAcc_HeadCode);
            if(txtAcc_HeadCode.length>=6)
            {
            startwaiting(document.FasAcc_Headform_Delete);
            var url="../../../../../Delete_Acc_Head_Dir_Sys.view?Command=checkCode1&txtAcc_HeadCode="+txtAcc_HeadCode;
//            alert(url);
            var req=getTransport();
            req.open("POST",url,true); 
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
            stopwaiting(document.FasAcc_Headform_Delete);
            var baseResponse=req.responseXML.getElementsByTagName("response")[0];
            var tagcommand=baseResponse.getElementsByTagName("command")[0];
            var Command=tagcommand.firstChild.nodeValue;
           
            if(Command=="checkCode") 
            {
//                alert("Command::::::"+Command);
                loadcheckCode(baseResponse);
            }
            else if(Command=="checkCode1")
            {
//                alert("Command::::::"+Command);
                loadcheckCode(baseResponse);
            }
           
            
        }
    }
}

function loadcheckCode(baseResponse)
{
    var flag=baseResponse.getElementsByTagName("flag")[0].firstChild.nodeValue;
    //alert("FF");
    if(flag=="success")
    {
      var hid=baseResponse.getElementsByTagName("hid")[0].firstChild.nodeValue;
      //alert("Account Head Code '"+hid+"' Already Exist");
      document.getElementById("txtAcc_HeadCode").value=hid;
      //document.getElementById("txtAcc_HeadCode").focus();
       var hdesc=baseResponse.getElementsByTagName("hdesc")[0].firstChild.nodeValue;
       var mjHC=baseResponse.getElementsByTagName("mjHC")[0].firstChild.nodeValue;
       var miHC =baseResponse.getElementsByTagName("miHC")[0].firstChild.nodeValue;
       var SG1=baseResponse.getElementsByTagName("SG1")[0].firstChild.nodeValue;
       var SG2=baseResponse.getElementsByTagName("SG2")[0].firstChild.nodeValue;
       var DOC =baseResponse.getElementsByTagName("DOC")[0].firstChild.nodeValue;
       var BalType=baseResponse.getElementsByTagName("BalType")[0].firstChild.nodeValue;
       var Nature=baseResponse.getElementsByTagName("Nature")[0].firstChild.nodeValue;
       var inUse =baseResponse.getElementsByTagName("inUse")[0].firstChild.nodeValue;
       var LastUse=baseResponse.getElementsByTagName("LastUse")[0].firstChild.nodeValue;
       var FRN=baseResponse.getElementsByTagName("FRN")[0].firstChild.nodeValue;
       var FRD =baseResponse.getElementsByTagName("FRD")[0].firstChild.nodeValue;
       var TB=baseResponse.getElementsByTagName("TB")[0].firstChild.nodeValue;
       var AccRes=baseResponse.getElementsByTagName("AccRes")[0].firstChild.nodeValue;
       var WN_id =baseResponse.getElementsByTagName("WN_id")[0].firstChild.nodeValue;
       var oid=baseResponse.getElementsByTagName("oid")[0].firstChild.nodeValue;
       var wingid=baseResponse.getElementsByTagName("wingid")[0].firstChild.nodeValue;
       var SL_YN =baseResponse.getElementsByTagName("SL_YN")[0].firstChild.nodeValue;
       var rmk =baseResponse.getElementsByTagName("rmk")[0].firstChild.nodeValue;
       document.getElementById("txtAcc_HeadCode").value=hid;
       document.getElementById("txtAcc_HeadDesc").value=hdesc;
       document.getElementById("txtMajor_id").value=mjHC;
       document.getElementById("txtMinor_id").value=miHC;
       if(SG1!=0)
       document.getElementById("txtProg_id").value=SG1;
       else
       document.getElementById("txtProg_id").value="";
       if(SG2!=0)
       document.getElementById("txtProg_sub_id").value=SG2;
       else
       document.getElementById("txtProg_sub_id").value="";
        var m=DOC.split('-');
            //alert(m[0]+"U"+m[1]+"U"+m[2])
            DOC=m[2]+"/"+m[1]+"/"+m[0];
       document.getElementById("txtCrea_date").value=DOC;
      
       if(BalType=="CR")
             { document.FasAcc_Headform_Delete.txtBal_type[0].checked=true;}
       else if(BalType=="DR")
       {
            document.FasAcc_Headform_Delete.txtBal_type[1].checked=true;
            }
       else if(BalType=="null")
           {document.FasAcc_Headform_Delete.txtBal_type[2].checked=true;}
        
       document.getElementById("txtNature").value=Nature;
      if(Nature=="C")
              { document.FasAcc_Headform_Delete.txtNature[0].checked=true; }       // Nature Not Needed
       else if(Nature=="O")
            { document.FasAcc_Headform_Delete.txtNature[1].checked=true; }
       else if(Nature=="R")
           { document.FasAcc_Headform_Delete.txtNature[2].checked=true; }
           
      document.getElementById("txtUse_status").value=inUse;
       if(inUse=="Y")
            {
                document.FasAcc_Headform_Delete.txtUse_status[0].checked=true;
                enableUsage(inUse);
            }
        else if(inUse=="N")
            {
                document.FasAcc_Headform_Delete.txtUse_status[1].checked=true;
                enableUsage(inUse);
                if(LastUse!="null")
                {
                 var m=LastUse.split('-');
                //alert(m[0]+"U"+m[1]+"U"+m[2])
                LastUse=m[2]+"/"+m[1]+"/"+m[0];
                 document.getElementById("txtlast_date").value=LastUse;
                 }
                 else
                 document.getElementById("txtlast_date").value="";
                if(FRN!="null")
                document.getElementById("txtRef_no").value=FRN;
                else
                document.getElementById("txtRef_no").value="";
                if(FRD!="null")
                {
                m=FRD.split('-');
                //alert(m[0]+"U"+m[1]+"U"+m[2])
                FRD=m[2]+"/"+m[1]+"/"+m[0];
                document.getElementById("txtRef_date").value=FRD;
                }
                else
                 document.getElementById("txtRef_date").value="";
            }
        
       if(TB=="Y")
            {
            document.FasAcc_Headform_Delete.txtTB_mandatory[0].checked=true;
            }
        else if(TB=="N")
            {
            document.FasAcc_Headform_Delete.txtTB_mandatory[1].checked=true;
            }
       document.getElementById("txtTB_mandatory").value=TB;
       if(AccRes=="Y")
            {
                document.FasAcc_Headform_Delete.txtaccess[0].checked=true;
                enableOffice(AccRes);
                if(WN_id!="null")
                 document.getElementById("txtApp_for_workid").value=WN_id;
                 else
                 document.getElementById("txtApp_for_workid").value="";
                if(oid!=0)
                {   
                  document.getElementById("txtApp_offid").value=oid;
                 }
                else
                    document.getElementById("txtApp_offid").value="";
               if(wingid!=0)
                document.getElementById("txtApp_wingId").value=wingid;
                else
                document.getElementById("txtApp_wingId").value="";
            }
        else if(AccRes=="N")
            {
                document.FasAcc_Headform_Delete.txtaccess[1].checked=true;
                enableOffice(AccRes);
                document.getElementById("txtApp_wingId").value="";
                
            }
       document.getElementById("txtaccess").value=AccRes;
       
       if(rmk!="null")
       document.getElementById("txtRemarks").value=rmk;
       else
       document.getElementById("txtRemarks").value="";
          //alert("SL"+SL_YN+"DD")
       if(SL_YN=="Y")
       {
        document.FasAcc_Headform_Delete.txtsub_ledger_YN[0].checked=true;
        enableSub_Ledger(SL_YN);
        var tbody=document.getElementById("grid_body");
                        var t=0;
                        for(t=tbody.rows.length-1;t>=0;t--)
                        {
                           tbody.deleteRow(0);
                        }
        var SLCODE=baseResponse.getElementsByTagName("SLCODE");
        
        var items=new Array();
        for(var k=0;k<SLCODE.length;k++)
        {
        items[0]=baseResponse.getElementsByTagName("SLCODE")[k].firstChild.nodeValue;   
        items[1]=baseResponse.getElementsByTagName("SLDESC")[k].firstChild.nodeValue;   
        tbody=document.getElementById("grid_body");
        var mycurrent_row=document.createElement("TR");
        var i=0;
        var cell2;
        
        for(i=0;i<2;i++)
        {   
            cell2=document.createElement("TD");
            //Hcell=document.createElement("Textbox");
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
        
       }
       else if(SL_YN=="N")
       {
       document.FasAcc_Headform_Delete.txtsub_ledger_YN[1].checked=true;
       enableSub_Ledger(SL_YN);
       }
       
      
    }
    else if(flag=="failure")
     {
         alert("Account Head Code '"+document.getElementById("txtAcc_HeadCode").value+"' doesn't Exist");
         document.getElementById("txtAcc_HeadCode").value="";
         document.getElementById("txtAcc_HeadCode").focus();
     }
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
        //alert("sd")
       // document.getElementById("gd").focus();
        //document.getElementById("txtSL_Desc").focus();
    }
    else if(opt=="N")
    {
        document.getElementById("sub_ledge_dis").style.display='none';
        document.getElementById("grid").style.display='none';
    }
}


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


function enableUsage(opt)
{
    if(opt=="Y")
    {
        document.FasAcc_Headform_Delete.txtlast_date.value="";
        document.FasAcc_Headform_Delete.txtRef_no.value="";
        document.FasAcc_Headform_Delete.txtRef_date.value="";
        //document.FasAcc_Headform_Delete.txtlast_date.disabled=true;
       // document.FasAcc_Headform_Delete.txtRef_no.disabled=true;
       // document.FasAcc_Headform_Delete.txtRef_date.disabled=true;
        
    }
    else if(opt=="N")
    {
        document.FasAcc_Headform_Delete.txtlast_date.disabled=false;
        document.FasAcc_Headform_Delete.txtRef_no.disabled=false;
        document.FasAcc_Headform_Delete.txtRef_date.disabled=false;
        
    }
}

function enableOffice(opt)
{  
    if(opt=="Y")
    {
        
        document.FasAcc_Headform_Delete.txtApp_for_workid.disabled=false;
        document.FasAcc_Headform_Delete.txtApp_offid.disabled=false;
        //document.FasAcc_Headform_Delete.txtApp_OffName.disabled=false;
        document.FasAcc_Headform_Delete.txtApp_wingId.disabled=false;
        //document.FasAcc_Headform_Delete.txtApp_wingName.disabled=false;
        
        
    }
    else if(opt=="N")
    {
        document.FasAcc_Headform_Delete.txtApp_for_workid.value="";
        document.FasAcc_Headform_Delete.txtApp_offid.value="";
        //document.FasAcc_Headform_Delete.txtApp_OffName.value="";
        document.FasAcc_Headform_Delete.txtApp_wingId.value="";
        document.FasAcc_Headform_Delete.txtApp_for_workid.disabled=true;
        document.FasAcc_Headform_Delete.txtApp_offid.disabled=true;
        //document.FasAcc_Headform_Delete.txtApp_OffName.disabled=true;
        document.FasAcc_Headform_Delete.txtApp_wingId.disabled=true;
        //document.FasAcc_Headform_Delete.txtApp_wingName.disabled=false;
    }
}
function loadTable(scod)
{
        com_id=scod;
       // document.FasAcc_Headform_Delete.cmdadd.disabled=true;
        var r=document.getElementById(scod);
        var rcells=r.cells;
        try {document.getElementById("txtSL_code").value=rcells.item(1).lastChild.nodeValue;}catch(e){}
        try{document.getElementById("txtSL_Desc").value=rcells.item(1).lastChild.nodeValue;}catch(e){}
      
       
    document.FasAcc_Headform_Delete.cmdupdate.style.display='block';
    document.FasAcc_Headform_Delete.cmddelete.disabled=false;
    document.FasAcc_Headform_Delete.cmdadd.style.display='none';
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

        
        
        

function numbersonly(e)
{   
        var unicode=e.charCode? e.charCode : e.keyCode
         if(unicode==13)
        {
          //try{t.blur();}catch(e){}
          //return true;
        
        }
        if (unicode!=8 && unicode !=9  )
        {
            if (unicode<48||unicode>57 ) 
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
function sixdigit()
{
 if(document.FasAcc_Headform_Delete.txtAcc_HeadCode.value!=0)
    {
        if((document.FasAcc_Headform_Delete.txtAcc_HeadCode.value).length<6)
        {
        alert("Account Head Code shouldn't less than 6 digit number");
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
function checkNull()
{
    if(window.confirm('Do you want to delete?'))
    {
        if(document.getElementById("txtAcc_HeadCode").value.length==0)
        {
            alert("Enter the Account Head Code");
            document.getElementById("txtAcc_HeadCode").focus();
            return false;
        }
        if(document.getElementById("txtlast_date").value.length==0)
        {
        alert("Enter Ineffective from date");
        document.getElementById("txtlast_date").focus();
        return false;    
        }
        if(document.getElementById("txtRef_no").value.length==0)
        {
        alert("Enter the File Reference Number");
        document.getElementById("txtRef_no").focus();
        return false;    
        }
        if(document.getElementById("txtRef_date").value.length==0)
        {
        alert("Enter the File Reference Date");
        document.getElementById("txtRef_date").focus();
        return false;    
        }
        return true;
    }
    else 
     return false;
}

        
function call_clr()
{
 document.FasAcc_Headform_Delete.txtAcc_HeadCode.value="";
        document.getElementById("txtAcc_HeadDesc").value="";
        document.getElementById("txtMajor_id").value="";
        document.getElementById("txtMinor_id").value="";
        document.getElementById("txtProg_id").value="";
        document.getElementById("txtProg_sub_id").value="";
        document.FasAcc_Headform_Delete.txtBal_type[0].checked=true;
        document.FasAcc_Headform_Delete.txtTB_mandatory[0].checked=true;
        document.getElementById("txtCrea_date").value="";
        document.getElementById("txtlast_date").value="";
        document.getElementById("txtRef_no").value="";
        document.getElementById("txtRef_date").value="";
        document.FasAcc_Headform_Delete.txtaccess[1].checked=true;
        document.FasAcc_Headform_Delete.txtApp_offid.value="";
        
        document.FasAcc_Headform_Delete.txtApp_wingId.value="";
        document.FasAcc_Headform_Delete.txtApp_for_workid.value="";
        
        document.FasAcc_Headform_Delete.txtsub_ledger_YN[1].checked=true;
        document.getElementById("txtRemarks").value="";
        document.getElementById("txtApp_wingId").value="";
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

function exit()
{
       self.close();
}