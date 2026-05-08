 var maxtodate;
 var maxsession;
 var balValue;
//alert('start ');

//////////////   FOR EMPLOYEE POPUP WINDOW //////////////////////
var winemp;

/*
  The following function is used to display the pop up window on clicking the employee torch image.
  
  The pop up window is opened in given height, width, resizeable and scrollable attributes are given.
*/

function servicepopup()
{
    if (winemp && winemp.open && !winemp.closed) 
    {
       winemp.resizeTo(500,600);
       winemp.moveTo(200,200); 
       winemp.focus();
       return ;
    }
    else
    {
        winemp=null;
    }
        
    winemp= window.open("EmpJoiningPopup.jsp","mywindow_ESP1","status=1,height=400,width=500,resizable=YES, scrollbars=yes"); 
    winemp.moveTo(250,250);  
    winemp.focus();
    
}
//////////////   FOR JOB POPUP WINDOW //////////////////////


var winjob;

function jobpopup()
{
    if (winjob && winjob.open && !winjob.closed) 
    {
       winjob.resizeTo(500,500);
       winjob.moveTo(250,250); 
       winjob.focus();
       return;
    }
    else
    {
        winjob=null
    }
        
    winjob= window.open("../../../../../org/HR/HR1/OfficeMaster/jsps/JobPopupJSP.jsp","JobSearch","status=1,height=500,width=500,resizable=YES, scrollbars=yes"); 
    winjob.moveTo(250,250);  
    winjob.focus();
    
}

/*function doParentOthers(jobid,deptid)
{
if(deptid==null)
document.Hrm_TransJoinForm.txtOffId.value=jobid;
    
    document.Hrm_TransJoinForm.txtOffice_Id.value=jobid;
    callServer1('Load','null');
    loadOtherOffice(jobid,deptid);
    return true
}*/

function doParentJob(jobid,deptid)
{
    document.Hrm_TransJoinForm.dept_id.value=deptid;
    document.Hrm_TransJoinForm.txtOffice_Id.value=jobid;
    callServer1('Load','null');
    loadOffice(jobid);
    return true
}
/*function loadOtherOffice(id,deptid)
{
    alert(id);
    if(id=="" || id==null)
    {
        alert("Enter or (Select An Office..Then Click choose..)");
        document.Hrm_TransJoinForm.txtOffice_Id.focus();
    }
    else
    {
            startwaiting(document.Hrm_TransJoinForm) ;
            var url="../../../../../ServletLoadOfficeDetails.con?command=OtherOffice&ID="+id+"&DeptId="+deptid;      
            var req=getTransport();
            req.open("GET",url,true);         
            req.onreadystatechange=function()
            {                
               LoadOtherOfficeDetails(req);             
            }
            req.send(null);        
    }
}*/
window.onunload=function()
{
if (winjob && winjob.open && !winjob.closed) winjob.close();
if (winemp && winemp.open && !winemp.closed) winemp.close();
}

//alert("Hellooooooooooooo");

/*
   The following function is used to create new XMLHttpRequest object.
   creating two types of objects for browser version difference.
*/


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


function toExit()
{

  //window.open('','_parent','null');
   window.close();
}

function loadfun()
{
    //alert("wel");
    //document.Rws_SchPrgs.txtPrgId.focus();
   // document.first.cmdupdate.disabled;
}
function chdate()
{
if((document.Hrm_TransJoinForm.txtDOJ.value==null)||(document.Hrm_TransJoinForm.txtDOJ.value.length==0))
    {
        alert("Null Value not accepted..Enter Date of Joining");
        document.Hrm_TransJoinForm.txtDOJ.focus();
        return false;
    }
    return true;
}


function radioch()
{
if(FN.checked=true)
{
    //alert("hellooooooo");
}
}
function datCh()
{

    if((document.Hrm_TransJoinForm.txtDOJ.value==null)||(document.Hrm_TransJoinForm.txtDOJ.value.length==0))
    {
        alert("Null Value not accepted");
        document.Hrm_TransJoinForm.txtDOJ.focus();
        return false;
    }
    return true;
}



/*function checkdate()
{
//alert('check');
     if(maxtodate==null || maxtodate=='empty')
     {
            return true;
     }
     else
     {
        var fromdt=maxtodate;
        var todt=document.Hrm_TransJoinForm.txtDOC.value;
        
        var frm=fromdt.split('/');
        var to=todt.split('/');
        
        var fday=frm[0];
        var fmon=frm[1];
        var fyear=frm[2];
        
        var tday=to[0];
        var tmon=to[1];
        var tyear=to[2];
        
        if(fyear>tyear)
        {
            
            alert('Completed Date Should be greater than or equal to '+maxtodate);
            //document.Hrm_TransJoinForm.txtDateTo.focus();
            return false;
        }
        else if(fyear==tyear)
        {
                if(fmon>tmon)
                {
                     alert('Completed Date Should be greater than or equal to '+maxtodate);
                    //document.Hrm_TransJoinForm.txtDateTo.focus();
                    return false;
                }
                else if(fmon==tmon)
                {
                        if(fday>tday)
                        {
                            alert('Completed Date Should be greater than or equal to '+maxtodate);
                           // document.Hrm_TransJoinForm.txtDateTo.focus();
                            return false;
                        }
                        else (fday==tday)
                        {
                                var radvalue;
                                   if(document.Hrm_TransJoinForm.optFNAN[0].checked==true)
                                   {
                                            radvalue=document.Hrm_TransJoinForm.optFNAN[0].value;
                                   }
                                   else
                                   {
                                            radvalue=document.Hrm_TransJoinForm.optFNAN[1].value;
                                   }
                                   if(maxsession =='AN')
                                   {
                                        if(radvalue=='FN')
                                        {
                                            alert('Given Completed Date Session is not acceptable');
                                            return false;
                                        }
                                    }
                        
                        }
                        
                        
                }
        }
    }
        return true;

}


function checkdate1()
{
//alert('check');
    
        var fromdt=document.Hrm_TransJoinForm.txtDOC.value;
        var todt=document.Hrm_TransJoinForm.txtDOJ.value;
        
        var frm=fromdt.split('/');
        var to=todt.split('/');
        
        var fday=frm[0];
        var fmon=frm[1];
        var fyear=frm[2];
        
        var tday=to[0];
        var tmon=to[1];
        var tyear=to[2];
        
        if(fyear>tyear)
        {
           alert('Join Date Should be greater than or equal to Comleted Date');
            //document.Hrm_TransJoinForm.txtDateTo.focus();
            return false;
        }
        else if(fyear==tyear)
        {
                if(fmon>tmon)
                {
                    alert('Join Date Should be greater than or equal to Comleted Date');
                    //document.Hrm_TransJoinForm.txtDateTo.focus();
                    return false;
                }
                else if(fmon==tmon)
                {
                        if(fday>tday)
                        {
                            alert('Join Date Should be greater than or equal to Comleted Date');
                           // document.Hrm_TransJoinForm.txtDateTo.focus();
                            return false;
                        }
                        else (fday==tday)
                        {
                                var optvalue;
                                   if(document.Hrm_TransJoinForm.optFNAN[0].checked==true)
                                   {
                                            radvalue=document.Hrm_TransJoinForm.optFNAN[0].value;
                                   }
                                   else
                                   {
                                            radvalue=document.Hrm_TransJoinForm.optFNAN[1].value;
                                   }
                                   var radvalue;
                                   if(document.Hrm_TransJoinForm.radFNAN[0].checked==true)
                                   {
                                            radvalue=document.Hrm_TransJoinForm.radFNAN[0].value;
                                   }
                                   else
                                   {
                                            radvalue=document.Hrm_TransJoinForm.radFNAN[1].value;
                                   }

                                   if(optvalue =='AN')
                                   {
                                        if(radvalue=='FN')
                                        {
                                            alert('Given Join Date Session is not acceptable');
                                            return false;
                                        }
                                    }
                        
                        }
                        
                }
        }
     return true;

}
*/

function checkdate2()
{
//alert('check');
     if(maxtodate==null || maxtodate=='empty')
     {
            return true;
     }
     else
     {
        var fromdt=maxtodate;
        var todt=document.Hrm_TransJoinForm.txtDOJ.value;
        
        var frm=fromdt.split('/');
        var to=todt.split('/');
        
        var fday=frm[0];
        var fmon=frm[1];
        var fyear=frm[2];
        
        var tday=to[0];
        var tmon=to[1];
        var tyear=to[2];
        
        if(fyear>tyear)
        {
            alert('Join Date Should be greater than or equal to '+maxtodate);
            //document.Hrm_TransJoinForm.txtDateTo.focus();
            return false;
        }
        else if(fyear==tyear)
        {
                if(fmon>tmon)
                {
                    alert('Join Date Should be greater than  or equal to '+maxtodate);
                    //document.Hrm_TransJoinForm.txtDateTo.focus();
                    return false;
                }
                else if(fmon==tmon)
                {
                        if(fday>tday)
                        {
                             alert('Join Date Should be greater than or equal to  '+maxtodate);
                           // document.Hrm_TransJoinForm.txtDateTo.focus();
                            return false;
                        }
                        else if(fday==tday)
                        {
                                   var radvalue;
                                   if(document.Hrm_TransJoinForm.radFNAN[0].checked==true)
                                   {
                                            radvalue=document.Hrm_TransJoinForm.radFNAN[0].value;
                                   }
                                   else
                                   {
                                            radvalue=document.Hrm_TransJoinForm.radFNAN[1].value;
                                   }
                                   if(maxsession =='AN')
                                   {
                                        if(radvalue=='FN')
                                        {
                                            alert('Given Join Date Session is not acceptable');
                                            return false;
                                        }
                                    }
                      }
                        
                }
        }
    }
        return true;

}


 function nullch()
{   
     
     if((document.Hrm_TransJoinForm.txtEmpId.value==null)||(document.Hrm_TransJoinForm.txtEmpId.value.length==0))
    {
        alert("Select Employee ID");
        document.Hrm_TransJoinForm.txtEmpId.focus();
        return false;
    }
    
    
    /* else if((document.Hrm_TransJoinForm.txtDOC.value==null)||(document.Hrm_TransJoinForm.txtDOC.value.length==0))
    {
        alert("Enter Completed Date");
        document.Hrm_TransJoinForm.txtDOC.focus();
        return false;
    }
    
    var c=checkdate();
       if(c==false)
       {
           document.Hrm_TransJoinForm.txtDOC.focus();
            return false;
        }
    
    */
    else if((document.Hrm_TransJoinForm.txtDOJ.value==null)||(document.Hrm_TransJoinForm.txtDOJ.value.length==0))
    {
        alert("Enter Date Of Joining");
        document.Hrm_TransJoinForm.txtDOJ.focus();
        return false;
    }
    var empstatus=document.Hrm_TransJoinForm.txtempstatus.value;
    var c1;
  /*  if(empstatus!='WKG')
        c1=checkdate1();
    else*/
        c1=checkdate2();
       if(c1==false)
       {
           document.Hrm_TransJoinForm.txtDOJ.focus();
            return false;
        }
         //var id=document.getElementById("divsubdivision");
   if(document.Hrm_TransJoinForm.optjoin)
   {
            if(document.Hrm_TransJoinForm.optjoin[1].checked==true)
            {
               if((document.Hrm_TransJoinForm.cmbsubdivision.value==null)||(document.Hrm_TransJoinForm.cmbsubdivision.value.length==0))
                {
                    alert("Select SubDivision");
                    document.Hrm_TransJoinForm.cmbsubdivision.focus();
                    return false;
                }
            }
    }
   // alert("|"+document.Hrm_TransJoinForm.comDesign.value+"|");
    if((document.Hrm_TransJoinForm.comDesign.value=='0')||(document.Hrm_TransJoinForm.comDesign.value.length==0))
    {
        alert("Select Designation");
        try{
        document.Hrm_TransJoinForm.comDesign.focus();
        }catch(e){document.Hrm_TransJoinForm.cmbsgroup.focus();}
        return false;
    }
    
     else if((document.Hrm_TransJoinForm.comPostTow.value==null)||(document.Hrm_TransJoinForm.comPostTow.value.length==0))
    {
        alert("Select Post towards");
        document.Hrm_TransJoinForm.comPostTow.focus();
        return false;
    }
    
   /* if(document.Hrm_TransJoinForm.cmbWing)
    {
             if((document.Hrm_TransJoinForm.cmbWing.value==null)||(document.Hrm_TransJoinForm.cmbWing.value==0))
            {
                alert("Select Wing");
                document.Hrm_TransJoinForm.cmbWing.focus();
                return false;
            }
    }*/
   /* else if((document.Hrm_TransJoinForm.txtRemarks.value==null)||(document.Hrm_TransJoinForm.txtRemarks.value.length==0))
    {
        alert("Null Value not accepted");
        document.Hrm_TransJoinForm.txtRemarks.focus();
        return false;
    }*/
    
    return true;
}





function doFunction(Command,param)
{
    //alert(Command);
   //var OffCode=document.Hrm_TransJoinForm.txtOffId.value;
   
   var OffCode=document.Hrm_TransJoinForm.txtOffice_Id.value;   
   //alert(OffCode);
   OffName=document.Hrm_TransJoinForm.txtOffName.value;
   var OffAddr=document.Hrm_TransJoinForm.txtOffAddr.value;
   var EmpId=document.Hrm_TransJoinForm.txtEmpId.value;
   //alert(EmpId);
 // var Eid=document.Hrm_TransJoinForm.comEmpId[document.all.selectedIndex].value;
  //var Eid=document.Hrm_TransJoinForm.comEmpId.value;
   //alert(Eid);
   var office_id=document.getElementById("txtOffId").value;
   var DOB=document.Hrm_TransJoinForm.txtDOB.value;
  
   var GPFNO=document.Hrm_TransJoinForm.txtGpfNo.value;
   var JRId=document.Hrm_TransJoinForm.txtDOJ.value;
   var a=JRId.split("/");
   
   JRId=a[2];
  //alert(JRId);
   
   var JR=document.Hrm_TransJoinForm.txtJR.value;
   var DOJ=document.Hrm_TransJoinForm.txtDOJ.value;
   var design=document.Hrm_TransJoinForm.comDesign.value;
   
   
   var postcount=document.Hrm_TransJoinForm.comPostTow.value;
  
   var rem=document.Hrm_TransJoinForm.txtRemarks.value;
   //var jrid=document.Hrm_TransJoinForm.txtJR.value;
   var radvalue;
   if(document.Hrm_TransJoinForm.radFNAN[0].checked==true)
   {
            radvalue=document.Hrm_TransJoinForm.radFNAN[0].value;
   }
   else
   {
            radvalue=document.Hrm_TransJoinForm.radFNAN[1].value;
   }
  
    if(Command=="Add")
    {
    
                   /*
                   
                     Initially checking whether all the details are entered. If not alert is throwed.
                      Setting the destination servlet url in a variable.
                      Getting the XMLHttpRequest object in a variable.
                      Opening the object and assigning service method,url and true value.
                      true is for asynchronous calling and false is for synchronous calling.
                      Assigning method in anonymous function to send the request to destination servlet.
                    */
                    
    
        var flagg=nullch();
                
        if(flagg==true)
        {
            var url="../../../../../HrmTransJoinServ_New.view?Command=dispDesign&txtOffId="+OffCode+"&JYear="+a[2];
             //url=url+"&optjoin="+optjoin+"&currentoffice="+currentoffice;
               // alert(url);
                 var req=getTransport();
                  //alert("test");
                req.open("GET",url,true); 
                req.onreadystatechange=function()
                 {
                     handleResponse(req);
                  }   
                  if(window.XMLHttpRequest)
                        req.send(null);
                else req.send();
             
            
       }
    }
        
        
        else if(Command=="disp")
        {
            document.Hrm_TransJoinForm.txtDOB.focus();    
            var url="../../../../../HrmTransJoinServ_New.view?Command=disp&txtEmpId="+EmpId;
            var req=getTransport();
            req.open("GET",url,true); 
            req.onreadystatechange=function()
            {
               handleResponse(req);
            }   
                    req.send(null);
        }
        
        else if(Command=="dispEmp")
        {
        
           /*
             This function is used to load the details of employee like (Name, Date of Birth, GPF NO., and cadre).
           */
        
            if((document.Hrm_TransJoinForm.txtEmpId.value==null)||(document.Hrm_TransJoinForm.txtEmpId.value.length==0))
            {
                alert("Null Value not accepted...Select Employee ID");
                document.Hrm_TransJoinForm.txtEmpId.focus();
                return false;
            }
            clr();
            var url="../../../../../HrmTransJoinServ_New.view?Command=dispEmp&comEmpId="+EmpId+"&office_id="+office_id;
             //alert(url);
            var req=getTransport();
            req.open("GET",url,true); 
            req.onreadystatechange=function()
            {
               handleResponse(req);
            }   
            if(window.XMLHttpRequest)
                        req.send(null);
                else req.send();
        }
        
         else if(Command=="txtDOB")
             {
                //alert("hereeeeeeeeeeeee");
                var month=DOB.substr(5,2);
                var year=DOB.substr(0,4);
                var day=DOB.substr(8,2);
                //alert("day"+day+"mon"+month+"yr"+year);
                document.Hrm_TransJoinForm.txtDOB.value=day+"/"+month+"/"+year;
             }
             else if(Command=="dispDesign")
             {
                
                
                var url="../../../../../HrmTransJoinServ_New.view?Command=dispDesign&txtOffId="+OffCode+"&JYear="+a[2];
                //alert(url);
                 var req=getTransport();
                  //alert("test");
                req.open("GET",url,true); 
                req.onreadystatechange=function()
                 {
                     handleResponse(req);
                  }   
                      if(window.XMLHttpRequest)
                        req.send(null);
                else req.send();
             
            
             }
        
   }     
   
   
   
   
        /*else if(Command=="ra")
        {
            if(document.Hrm_TransJoinForm.radFN.checked==true)
            {
            alert("yaaaaaaaaaaaaa");
           
            document.Hrm_TransJoinForm.radAN.checked==false;
            }
            else
            {
            alert("something");
            }
        }
        else if(Command=="rad")
        {
        if(document.Hrm_TransJoinForm.radAN.checked==true)
        {
            alert("nooooooooooo");
            document.Hrm_TransJoinForm.radFN.checked==false;
         }
        }*/
  
    
  function clr()
  {
        //document.Hrm_TransJoinForm.txtEmpId.value="";
        document.Hrm_TransJoinForm.txtDOB.value="";
        document.Hrm_TransJoinForm.txtGpfNo.value="";
        document.Hrm_TransJoinForm.comEmpId.value="";
        document.Hrm_TransJoinForm.txtJRId.value="";
        //document.Hrm_TransJoinForm.txtDOC.value="";
        document.Hrm_TransJoinForm.txtDOJ.value="";
        document.Hrm_TransJoinForm.cmbsgroup.selectedIndex=0;
        document.Hrm_TransJoinForm.comDesign.selectedIndex=0;
        document.Hrm_TransJoinForm.comPostTow.selectedIndex=0;
        document.Hrm_TransJoinForm.txtRemarks.value="";
        document.Hrm_TransJoinForm.txtEmpId.focus();
  
  }
    
    
  


function handleResponse(req)
{
//alert("handle response");
   if(req.readyState==4)
    {
	   //alert("readyState=4");
         if(req.status==200)
        {
        	// alert("status=200");
            var baseResponse=req.responseXML.getElementsByTagName("response")[0];
          //  alert("****1");
            var tagcommand=baseResponse.getElementsByTagName("command")[0];
            var Command=tagcommand.firstChild.nodeValue;
          ////  alert("Command"+Command);
            if(Command=="Add")
            {
                addRow(baseResponse);
            }
            else if(Command=="sessionout")
            {
                alert('Session is closed');
                try{
                }catch(e){}
                self.close();
                return;
            }
            else if(Command=="disp")
            {
                dispRow(baseResponse);
            }
            else if(Command=="dispEmp")
            {
                dispEmpRow(baseResponse);
            }
            
            else if(Command=="dispDesign")
            {
                dispDesignRow(baseResponse);
            }
            else if(Command=="Show")
            {
                loadRow(baseResponse);      
            }
         /*   else if(Command=="Validate"){
                var result=baseResponse.getElementsByTagName("result")[0].firstChild.nodeValue;
                if(result=="Fail")
                    alert("The Corresponding Files are already validated");
                else
                    alert("Successfully Validated");
            }*/
        
            else if(Command=="AccountUnit"){
                var minorcmb = document.getElementById("unit_name");
                document.getElementById("unit_name").length=1;
                var acc_unit_id = baseResponse.getElementsByTagName("acc_unit_id"); 
                var acc_unit_name = baseResponse.getElementsByTagName("acc_unit_name");
                for(var i=0; i<acc_unit_id.length; i++){
                             var opt1 = document.createElement('option');
                             opt1.value = acc_unit_id[i].firstChild.nodeValue;
                             opt1.innerHTML = acc_unit_name[i].firstChild.nodeValue;
                             minorcmb.appendChild(opt1);
                } 
            }
          /*  else if(Command=="Error"){

            	var tagresult=baseResponse.getElementsByTagName("result")[0];
                var result=tagresult.firstChild.nodeValue;
               //	window.open('../../../../../org/HR/HR1/EmployeeMaster/jsps/GPF_ErrorList.jsp','Error List','width=300,height=200,menubar=yes,status=yes,location=yes,toolbar=yes,scrollbars=yes');
               	var winemp= window.open("GPF_ErrorList.jsp","status=1,height=500,width=600,resizable=YES, scrollbars=yes");
                winemp.moveTo(250,250); 
                winemp.focus();
        	               
               	if(result=="Valid" && balValue=="true")
               		document.getElementById("Validate").disabled = false;
               	else
               		document.getElementById("Validate").disabled = true;
            }*/
    }
}
}
//to load values from the database to the grid
 function loadRow(baseResponse)
 {
                var flag=baseResponse.getElementsByTagName("flag")[0].firstChild.nodeValue;
                if(flag=="NoRecord"){
                    alert("No Record for Corresponding Value");
                    document.getElementById("subscribe");
                }
                else if(flag=="success")
                {
                    var filetype=baseResponse.getElementsByTagName("filetype")[0].firstChild.nodeValue;
                    var tbody=document.getElementById("tblList");
                    tbody.borderColor='#ff0000';
                    var t=0;
                    for(t=tbody.rows.length-1;t>=0;t--){
                           tbody.deleteRow(0);
                    }
                    if(filetype=="Subscribe"){
                         var title=baseResponse.getElementsByTagName("title");
                         var titlen=title.length;
                         var mycurrent_row=document.createElement("TR");
                         var cell;
                         var jk=0;
                         var slno_cell=document.createElement("TH");
                         var node=document.createTextNode("Sl.Number");
                         slno_cell.appendChild(node);
                         mycurrent_row.appendChild(slno_cell);
                         for(jk=0;jk<titlen;jk++){
                              var column=baseResponse.getElementsByTagName("title")[jk].firstChild.nodeValue;
                              cell=document.createElement("TH");
                              cell.appendChild(document.createTextNode(column));
                              mycurrent_row.appendChild(cell);
                          }
                        tbody.appendChild(mycurrent_row);
                         
                        var Divn_Cd=baseResponse.getElementsByTagName("DIVN_CD");
                        var j=0;
                        var l=Divn_Cd.length;
                        var col_count=0;
                        var c1=0,c2=0,c3=0;
                        for(j=0;j<l;j++){
                                var mycurrent_row=document.createElement("TR");
                                var mycurrent_row1=document.createElement("TR");
                                var mycurrent_row_subscrib_total=document.createElement("TR");
                                var mycurrent_row2=document.createElement("TR");
                                var mycurrent_row3=document.createElement("TR");
                                var slno_cell=document.createElement("TD");
                                var node=document.createTextNode(j+1);
                                slno_cell.align="Right";
                                slno_cell.appendChild(node);
                                mycurrent_row.appendChild(slno_cell);
                               
                                for(var i=0;i<titlen;i++){
                                    var title=baseResponse.getElementsByTagName("title")[i].firstChild.nodeValue;
                                    var Val=baseResponse.getElementsByTagName(title)[j].firstChild.nodeValue;
                                    var length=Val.length;
                                    if(!isNaN(Val)){
                                        if(Val.charAt(length-1)==0 && Val.charAt(length-2)==".")
                                            Val=parseInt(Val);
                                        if(Val==0){
                                            Val="";
                                        }    
                                    }
                                    if(Val=="null")
                                        Val="";
                                  
                                   var cell=document.createElement("TD");
                                   var node=document.createTextNode(Val);
                                   cell.align="Right";
                                   cell.appendChild(node);
                                   mycurrent_row.appendChild(cell);
                               //***************for total calculation*****************************
                                var cell1=document.createElement("TD");
                               
                                var cell_sub=document.createElement("TD");
                              //***************for trial balance amount*****************************  
                                var cell_trial=document.createElement("TD");
                                cell_trial.align="Right";
                                var cell_trial1=document.createElement("TD");
                                cell1.align="Right";
                              //***************for Difference Amount***************************** 
                                var cell_diff=document.createElement("TD");
                                cell_diff.align="Right";
                                var cell_diff1=document.createElement("TD");
                                cell_diff1.align="Right";
                                
                                var node1,empty;
                                var head_code,trial_amt;
                                if(i==0){
                                   node1=document.createTextNode("Total");
                                   cell1.appendChild(node1);
                                   mycurrent_row1.appendChild(cell1);
                                   
                                   var cell2=document.createElement("TD");
                                   empty=document.createTextNode("");
                                   cell2.appendChild(empty);
                                   mycurrent_row1.appendChild(cell2);
                                   
                                   node1=document.createTextNode("As Trial Balance");
                                   cell_trial.appendChild(node1);
                                   mycurrent_row2.appendChild(cell_trial);
                                    
                                /*   empty=document.createTextNode("");
                                   cell_trial1.appendChild(empty);
                                   mycurrent_row2.appendChild(cell_trial1);*/
                                   
                                  node1=document.createTextNode("Difference Value");
                                   cell_diff.appendChild(node1);
                                   mycurrent_row3.appendChild(cell_diff);
                                    
                                 
                                }
                                   
                                    if(title=="SUB_AMOUNT"){
                                        if(Val=="")
                                        Val=0;
                                        c1=parseInt(c1)+parseInt(Val);
                                    node1=document.createTextNode(c1);
                                    Val="";
                                    cell1.appendChild(node1);
                                    }
                                   
                                    if(title=="ARR_AMOUNT"){
                                        if(Val=="")
                                        Val=0;
                                        c2=parseInt(c2)+parseInt(Val);
                                        node1=document.createTextNode(c2);
                                        Val="";
                                        cell1.bordercolor="#ff0000";
                                        cell1.appendChild(node1);
                        head_code=baseResponse.getElementsByTagName("acc_head_code")[0].firstChild.nodeValue;
                        trial_amt=baseResponse.getElementsByTagName("curr_mnth_credit")[0].firstChild.nodeValue;
                       
                        cell_sub.colSpan=2;
                        cell_sub.bordercolor='#ff0000';
                        cell_sub.align="Center";
                        node1=document.createTextNode(c1+c2);
                        cell_sub.appendChild(node1);
                        
                        cell_trial.colSpan=2;
                        cell_trial.bordercolor='#ff0000';
                        cell_trial.align="Center";
                        node1=document.createTextNode(trial_amt);
                        cell_trial.appendChild(node1);
                                    var cell_diff_val;
                                  if(j==(l-1))
                                    cell_diff_val=(c1+c2)-trial_amt;
                                    else 
                                    cell_diff_val=0;
                        cell_diff.colSpan=2;
                        cell_diff.bordercolor='#ff0000';
                        cell_diff.align="Center";
                                    node1=document.createTextNode(cell_diff_val);
                                    cell_diff.appendChild(node1);
                                   
                                    if(cell_diff_val==0)
                                   	 balValue="true";
                                   else
                                   	balValue="false";
                                    }
                                    if(title=="REF_AMOUNT"){
                                       if(Val=="")
                                        Val=0;
                                        c3=parseInt(c3)+parseInt(Val);
                                        node1=document.createTextNode(c3);
                                        Val="";
                                        cell1.appendChild(node1);
                        head_code=baseResponse.getElementsByTagName("acc_head_code")[1].firstChild.nodeValue;
                        trial_amt=baseResponse.getElementsByTagName("curr_mnth_credit")[1].firstChild.nodeValue;
                                    node1=document.createTextNode(trial_amt);
                                    cell_trial.appendChild(node1);
                                   if(j==(l-1))
                                    cell_diff_val=c3-trial_amt;
                                    else 
                                    	cell_diff_val=0;
                                    node1=document.createTextNode(cell_diff_val);
                                    cell_diff.appendChild(node1);
                                    if(cell_diff_val==0)
                                   	 balValue="true";
                                   else
                                   	balValue="false";
                                    }
                                    mycurrent_row1.appendChild(cell1);
                                    mycurrent_row_subscrib_total.appendChild(cell_sub);
                        //*******for trial balance total vale*******
                                    mycurrent_row2.appendChild(cell_trial);
                                    mycurrent_row3.appendChild(cell_diff);
                                   
                                   
                              }
                              tbody.appendChild(mycurrent_row);
                              }
                              
                                  tbody.appendChild(mycurrent_row1);
                                  tbody.appendChild(mycurrent_row_subscrib_total);
                                  tbody.appendChild(mycurrent_row2);
                                  tbody.appendChild(mycurrent_row3);
                        
                        }
                   
                    else if(filetype=="Withdrawl"){
                         var title=baseResponse.getElementsByTagName("title");
                         var titlen=title.length;
                         var mycurrent_row=document.createElement("TR");
                         var cell;
                         var jk=0;
                          var slno_cell=document.createElement("TH");
                         var node=document.createTextNode("Sl.Number");
                         slno_cell.appendChild(node);
                         mycurrent_row.appendChild(slno_cell);
                         for(jk=0;jk<titlen;jk++){
                              var column=baseResponse.getElementsByTagName("title")[jk].firstChild.nodeValue;
                              cell=document.createElement("TH");
                              cell.appendChild(document.createTextNode(column));
                              mycurrent_row.appendChild(cell);
                          }
                        tbody.appendChild(mycurrent_row);
                        var Divn_Cd=baseResponse.getElementsByTagName("DIVN_CD");
                        var j=0;
                        var l=Divn_Cd.length;
                        var c1=0,c2=0,c3=0;
                        for(j=0;j<l;j++){
                              var mycurrent_row=document.createElement("TR");
                              var mycurrent_row1=document.createElement("TR");
                              var mycurrent_row2=document.createElement("TR");
                              var mycurrent_row3=document.createElement("TR");
                              var slno_cell=document.createElement("TD");
                              var node=document.createTextNode(j+1);
                                slno_cell.align="Right";
                                slno_cell.appendChild(node);
                                mycurrent_row.appendChild(slno_cell);
                              for(var i=0;i<titlen;i++){
                                    var title=baseResponse.getElementsByTagName("title")[i].firstChild.nodeValue;
                                    var Val=baseResponse.getElementsByTagName(title)[j].firstChild.nodeValue;
                                    var length=Val.length;
                                    if(!isNaN(Val)){
                                        if(Val.charAt(length-1)==0 && Val.charAt(length-2)==".")
                                            Val=parseInt(Val);
                                        if(Val==0){
                                            Val="";
                                        }    
                                    }
                                if(Val=="null")
                                    Val="";
                                    
                                var cell=document.createElement("TD");
                                   var node=document.createTextNode(Val);
                                   cell.align="Right";
                                   cell.appendChild(node);
                                   mycurrent_row.appendChild(cell);
                               //***************for total calculation*****************************
                                var cell1=document.createElement("TD");
                                var cell2=document.createElement("TD");
                               
                                var cell_trial=document.createElement("TD");
                                cell_trial.align="Right";
                                var cell_trial1=document.createElement("TD");
                                cell1.align="Right";
                                
                                var cell_diff=document.createElement("TD");
                                cell_diff.align="Right";
                                var cell_diff1=document.createElement("TD");
                                cell_diff1.align="Right";
                                
                                var node1,empty;
                                
                                var head_code,trial_amt;
                                if(i==0){
                                   node1=document.createTextNode("Total");
                                   cell1.appendChild(node1);
                                   mycurrent_row1.appendChild(cell1);
                                   
                                   empty=document.createTextNode("");
                                   cell2.appendChild(empty);
                                   mycurrent_row1.appendChild(cell2);
                                   
                                   node1=document.createTextNode("As Trial Balance");
                                   cell_trial.appendChild(node1);
                                   mycurrent_row2.appendChild(cell_trial);
                                    
                                   empty=document.createTextNode("");
                                   cell_trial1.appendChild(empty);
                                   mycurrent_row2.appendChild(cell_trial1);
                                   
                                   node1=document.createTextNode("Difference Value");
                                   cell_diff.appendChild(node1);
                                   mycurrent_row3.appendChild(cell_diff);
                                    
                                   empty=document.createTextNode("");
                                   cell_diff1.appendChild(empty);
                                   mycurrent_row3.appendChild(cell_diff1);
                                                                   

                                }
                                   
                                    if(title=="ADV_AMT"){
                                        if(Val=="")
                                        Val=0;
                                        c1=parseInt(c1)+parseInt(Val);
                                    node1=document.createTextNode(c1);
                                    Val="";
                                    cell1.appendChild(node1);
                        head_code=baseResponse.getElementsByTagName("acc_head_code")[0].firstChild.nodeValue;
                        trial_amt=baseResponse.getElementsByTagName("curr_mnth_credit")[0].firstChild.nodeValue;
                                    node1=document.createTextNode(trial_amt);
                                    cell_trial.appendChild(node1);
                                    if(j==(l-1))
                                    cell_diff_val=c1-trial_amt;
                                    else 
                                    cell_diff_val=0;
                                 node1=document.createTextNode(cell_diff_val);
                                    cell_diff.appendChild(node1);
                                    
                                    if(cell_diff_val==0)
                                      	 balValue="true";
                                      else
                                      	balValue="false";
                                    }
                                    mycurrent_row1.appendChild(cell1);
                                    
                        //*******for trial balance total vale*******
                       
                                    mycurrent_row2.appendChild(cell_trial);
                                    mycurrent_row3.appendChild(cell_diff);
                              }
                              tbody.appendChild(mycurrent_row);
                            //  records_val++;
                              }
                                   tbody.appendChild(mycurrent_row1);
                                   tbody.appendChild(mycurrent_row2);
                                  tbody.appendChild(mycurrent_row3);
                    }      
                    }                 
                    else
                    {
                     alert("Record does not exist. Insert a new Record");
                     document.MenuForm.CmdAdd.disabled=false;
                      var tbody=document.getElementById("tblList");
                        var t=0;
                        
                        for(t=tbody.rows.length-1;t>=0;t--)
                        {
                           tbody.deleteRow(0);
                        }
                     }
 
} 

function addRow(baseResponse)
{
    var flag=baseResponse.getElementsByTagName("flag")[0].firstChild.nodeValue;
    
    /*
      If the flag is success and the data is inserted into database then displaying the data is inserted into database for 
      the corresponding employee id.
    */
    
    if(flag=="success")
    {
       /* alert("Records successfully saved");
        alert("Join Report Id generated...."+document.Hrm_TransJoinForm.txtJRId.value);
       document.Hrm_TransJoinForm.reset();
       */
       
        var b;
                var msg="Records successfully saved. \nJoin Report Id generated is "+document.Hrm_TransJoinForm.txtJRId.value;
                var head="Creation Of Joining Report Details";
       b="<form><table width=\"100%\"><tr><td><table cellspacing=\"2\" cellpadding=\"3\" border=\"1\" width=\"100%\"><tr style=\"background-color: rgb(255,204,153);\"><th align=\"center\" colspan=\"2\" > <b> "+head+"</b> </th></tr>";
       
       b=b+"<tr style=\"background-color: rgb(255,255,225);\"><td  colspan=\"2\" >"+msg+"</td></tr>";
       b=b+"<tr style=\"background-color: rgb(255,204,153);\"><td align=\"center\" colspan=\"2\"> <input type=\"button\" id=\"Back\" name=\"Back\" value=\"Back\"     onclick=\"javascript:window.location.reload( false );\"></input><input type=\"button\" id=\"Exit\" name=\"Exit\" value=\"Exit\"     onclick=\"self.close();\"></input></td></tr>";
       b=b+"</table></td></tr></table></form>";
       var bid=document.getElementById("bodyid");
        try{ bid.innerHTML=b;
       }catch(e){
        bid.innerText="";
       }
        
    }
    else if(flag=="failure1")
    {
            //alert("Unfreezed record is exist for this employee. Records r not saved");
             var b;
                var msg="Unfreezed record is exist for this employee. Records r not saved";
                var head="Creation Of Joining Report Details";
               b="<form><table width=\"100%\"><tr><td><table cellspacing=\"2\" cellpadding=\"3\" border=\"1\" width=\"100%\"><tr style=\"background-color: rgb(255,204,153);\"><th align=\"center\" colspan=\"2\" > <b> "+head+"</b> </th></tr>";
               
               b=b+"<tr style=\"background-color: rgb(255,255,225);\"><td  colspan=\"2\" >"+msg+"</td></tr>";
               b=b+"<tr style=\"background-color: rgb(255,204,153);\"><td align=\"center\" colspan=\"2\"> <input type=\"button\" id=\"Back\" name=\"Back\" value=\"Back\"     onclick=\"javascript:window.location.reload( false );\"></input><input type=\"button\" id=\"Exit\" name=\"Exit\" value=\"Exit\"     onclick=\"self.close();\"></input></td></tr>";
               b=b+"</table></td></tr></table></form>";
               var bid=document.getElementById("bodyid");
                try{ bid.innerHTML=b;
               }catch(e){
                bid.innerText="";
               }
    }
    else
    {
        //alert("Records r not saved");
         var b;
                var msg="Records are not saved";
                var head="Creation Of Joining Report Details";
       b="<form><table width=\"100%\"><tr><td><table cellspacing=\"2\" cellpadding=\"3\" border=\"1\" width=\"100%\"><tr style=\"background-color: rgb(255,204,153);\"><th align=\"center\" colspan=\"2\" > <b> "+head+"</b> </th></tr>";
       
       b=b+"<tr style=\"background-color: rgb(255,255,225);\"><td  colspan=\"2\" >"+msg+"</td></tr>";
       b=b+"<tr style=\"background-color: rgb(255,204,153);\"><td align=\"center\" colspan=\"2\"> <input type=\"button\" id=\"Back\" name=\"Back\" value=\"Back\"     onclick=\"javascript:window.location.reload( false );\"></input><input type=\"button\" id=\"Exit\" name=\"Exit\" value=\"Exit\"     onclick=\"self.close();\"></input></td></tr>";
       b=b+"</table></td></tr></table></form>";
       var bid=document.getElementById("bodyid");
        try{ bid.innerHTML=b;
       }catch(e){
        bid.innerText="";
       }
    }
}

function dispRow(baseResponse)
{
    //alert("hellooooooooo");
       var flag=baseResponse.getElementsByTagName("flag")[0].firstChild.nodeValue;
       //alert(flag);
      var sename=baseResponse.getElementsByTagName("ename")[0].firstChild.nodeValue;
      //alert(sename);
     var sdob=baseResponse.getElementsByTagName("dob")[0].firstChild.nodeValue;
     //alert(sdob);
     var sgpfno=baseResponse.getElementsByTagName("gpfno")[0].firstChild.nodeValue;



    if(flag=="success")
    {
        document.Hrm_TransJoinForm.comEmpId.selectedText=sename;
        //document.Hrm_TransJoinForm.txtDOB.value=sdob;
        document.Hrm_TransJoinForm.txtGpfNo.value=sgpfno;

        alert("Records successfully inserted into DB");
        
    }
    else
    {
        alert("Records r not inserted");
    }
}




function dispEmpRow(baseResponse)
{
    //alert("inside dispEmpRow");
       var flag=baseResponse.getElementsByTagName("flag")[0].firstChild.nodeValue;
    //alert(flag);
     
    if(flag=="success")
    {
     var sid=baseResponse.getElementsByTagName("eid")[0].firstChild.nodeValue;
     var sdob=baseResponse.getElementsByTagName("dob")[0].firstChild.nodeValue;
     var sgpfno=baseResponse.getElementsByTagName("gpfno")[0].firstChild.nodeValue;

    var name=baseResponse.getElementsByTagName("name")[0].firstChild.nodeValue;
    
    var cadre=baseResponse.getElementsByTagName("cadre")[0].firstChild.nodeValue;
    if(cadre=='null')
    cadre="";
    
    var cad_name=baseResponse.getElementsByTagName("cad_name")[0].firstChild.nodeValue;
    
    if(cad_name=='null')
    cad_name="";
    //alert(cad_name);
    var adm=baseResponse.getElementsByTagName("admin")[0].firstChild.nodeValue;
    //alert(adm);
    
    maxtodate=baseResponse.getElementsByTagName("maxtodate")[0].firstChild.nodeValue;
    maxsession=baseResponse.getElementsByTagName("maxsession")[0].firstChild.nodeValue;
   
   //alert("after adm");
   // document.Hrm_TransJoinForm.txtDOJ.value=maxtodate;
    //alert(maxtodate);
   
    var empstatus=baseResponse.getElementsByTagName("workingstatus")[0].firstChild.nodeValue;
    if(empstatus=='null')
    empstatus="";
      //alert("empstatus"+empstatus); 
    document.Hrm_TransJoinForm.txtempstatus.value=empstatus;
    /*
    document.Hrm_TransJoinForm.txtOffice_Id.value=document.Hrm_TransJoinForm.txtOffId.value;
    alert(document.Hrm_TransJoinForm.txtOffice_Id.value);
      document.Hrm_TransJoinForm.txtOfficeName.value=document.Hrm_TransJoinForm.txtOffName.value;
      alert(document.Hrm_TransJoinForm.txtOfficeName.value);
    */
   // alert('before adm. check');
   
   /*
    if(cad_name=='CE' || cad_name=='SE' || cad_name=='EE' || adm=='YES')
    {
      document.Hrm_TransJoinForm.txtOffice_Id.disabled=false;
      document.Hrm_TransJoinForm.off_img.disabled=false;   
      
    }   
   
    
    else 
    {
      document.Hrm_TransJoinForm.txtOffice_Id.disabled=true;
      document.Hrm_TransJoinForm.off_img.disabled=true;
      
      document.Hrm_TransJoinForm.txtOffice_Id.value=document.Hrm_TransJoinForm.txtOffId.value;
      document.Hrm_TransJoinForm.txtOfficeName.value=document.Hrm_TransJoinForm.txtOffName.value;      
      
    }
    */
    
   // alert("after adm. check");
    
    
    
    
  /*  if(empstatus!='WKG')
    {
           //alert(empstatus);
           var id=document.getElementById("drcompdate");
           id.style.display="block";
           var msg="";
           
            id=document.getElementById("divcompleted");
            // alert(id);
                 if(empstatus=='DPN')
                    {
                        msg="Deputation Completed Date";  
                    }
                    else  if(empstatus=='LLV')
                    {
                         msg="Long Leave Completed Date";    
                    }
                     else  if(empstatus=='SUS')
                    {
                        msg="Suspension Completed Date";     
                    }
                      else  if(empstatus=='ABS')
                    {
                          msg="Absconded Completed Date";    
                    } 
                      else  if(empstatus=='TRT')
                    {
                          msg="Transit Completed Date";    
                    } 
          
            if(id.innerText !='undefined'  && id.innerText !=null  )
                        id.innerText=msg;
                    else 
                        id.innerHTML=msg;
   
   }
    else
    {
           // alert('else part');
            var id=document.getElementById("drcompdate");
            id.style.display="none";
    }*/
    
        document.Hrm_TransJoinForm.txtEmpId.value=sid;
        //alert("name..."+document.Hrm_TransJoinForm.txtEmpId.value);
        if(sdob==0 || sdob=='Not Specified')
            sdob="";
        document.Hrm_TransJoinForm.txtDOB.value=sdob;
        if(sgpfno==0)
            sgpfno="";
        document.Hrm_TransJoinForm.txtGpfNo.value=sgpfno;
       // alert("gpfno..."+document.Hrm_TransJoinForm.txtGpfNo.value);
        document.Hrm_TransJoinForm.comEmpId.value=name;
        //alert("name..."+document.Hrm_TransJoinForm.comEmpId.value);
        //alert("cadre..."+cadre);
        document.Hrm_TransJoinForm.txtcad.value=cadre;
        
        //alert("Records successfully inserted into DB");
        
    }
    else if(flag=="failure")
    {
        alert("Invalid Employee Id");
        document.Hrm_TransJoinForm.txtEmpId.value="";
        document.Hrm_TransJoinForm.txtDOB.value="";
        document.Hrm_TransJoinForm.txtGpfNo.value="";
        document.Hrm_TransJoinForm.comEmpId.value="";
        
        document.Hrm_TransJoinForm.txtEmpId.focus();
    }
     else if(flag=="failure3_1")
    {
        alert("This Employee  is already working.\n So cann't create a new joining record for this Employee");
        document.Hrm_TransJoinForm.txtEmpId.value="";
        document.Hrm_TransJoinForm.txtDOB.value="";
        document.Hrm_TransJoinForm.txtGpfNo.value="";
        document.Hrm_TransJoinForm.comEmpId.value="";
        
        document.Hrm_TransJoinForm.txtEmpId.focus();
    }
     else if(flag=="failure3_2")
    {
        alert("This Employee  has already retired status.\n So cann't create a new joining record for this Employee");
        document.Hrm_TransJoinForm.txtEmpId.value="";
        document.Hrm_TransJoinForm.txtDOB.value="";
        document.Hrm_TransJoinForm.txtGpfNo.value="";
        document.Hrm_TransJoinForm.comEmpId.value="";
        
        document.Hrm_TransJoinForm.txtEmpId.focus();
    }
    else if(flag=="failure3_3")
    {
        alert("This Employee  has dismissed status.\n So cann't create a new joining record for this Employee");
        document.Hrm_TransJoinForm.txtEmpId.value="";
        document.Hrm_TransJoinForm.txtDOB.value="";
        document.Hrm_TransJoinForm.txtGpfNo.value="";
        document.Hrm_TransJoinForm.comEmpId.value="";
        
        document.Hrm_TransJoinForm.txtEmpId.focus();
    }
    else if(flag=="failure3_4")
    {
        alert("This Employee  already got voluntary retirement.\n So cann't create a new joining record for this Employee");
        document.Hrm_TransJoinForm.txtEmpId.value="";
        document.Hrm_TransJoinForm.txtDOB.value="";
        document.Hrm_TransJoinForm.txtGpfNo.value="";
        document.Hrm_TransJoinForm.comEmpId.value="";
        
        document.Hrm_TransJoinForm.txtEmpId.focus();
    }
    else if(flag=="failure3_5")
    {
        alert("This Employee  already relieved from TWAD Board for Deputation. So cann't create a new joining in TWAD Board ");
        document.Hrm_TransJoinForm.txtEmpId.value="";
        document.Hrm_TransJoinForm.txtDOB.value="";
        document.Hrm_TransJoinForm.txtGpfNo.value="";
        document.Hrm_TransJoinForm.comEmpId.value="";
        document.Hrm_TransJoinForm.txtEmpId.focus();
    }
    else if(flag=="failure3_6")
    {
        alert("This Employee  is on Deputation. So cann't create a new joining in TWAD Board ");
        document.Hrm_TransJoinForm.txtEmpId.value="";
        document.Hrm_TransJoinForm.txtDOB.value="";
        document.Hrm_TransJoinForm.txtGpfNo.value="";
        document.Hrm_TransJoinForm.comEmpId.value="";
        document.Hrm_TransJoinForm.txtEmpId.focus();
    }
     else if(flag=="failure3_7")
    {
        alert("This Employee  is on Study Leave. So cann't create a new joining in TWAD Board ");
        document.Hrm_TransJoinForm.txtEmpId.value="";
        document.Hrm_TransJoinForm.txtDOB.value="";
        document.Hrm_TransJoinForm.txtGpfNo.value="";
        document.Hrm_TransJoinForm.comEmpId.value="";
        document.Hrm_TransJoinForm.txtEmpId.focus();
    }
     else if(flag=="failure3_8")
    {
        alert("This Employee  already relieved from TWAD Board due to Study Leave. So cann't create a new joining in TWAD Board ");
        document.Hrm_TransJoinForm.txtEmpId.value="";
        document.Hrm_TransJoinForm.txtDOB.value="";
        document.Hrm_TransJoinForm.txtGpfNo.value="";
        document.Hrm_TransJoinForm.comEmpId.value="";
        document.Hrm_TransJoinForm.txtEmpId.focus();
    }
    
    
    else if(flag=="failure3_9")
    {
        alert("This Employee  Passed away. So cann't create a new joining in TWAD Board ");
        document.Hrm_TransJoinForm.txtEmpId.value="";
        document.Hrm_TransJoinForm.txtDOB.value="";
        document.Hrm_TransJoinForm.txtGpfNo.value="";
        document.Hrm_TransJoinForm.comEmpId.value="";
        document.Hrm_TransJoinForm.txtEmpId.focus();
    }
    else if(flag=="failure3_10")
    {
        alert("This Employee  is in Abroad. So cann't create a new joining in TWAD Board ");
        document.Hrm_TransJoinForm.txtEmpId.value="";
        document.Hrm_TransJoinForm.txtDOB.value="";
        document.Hrm_TransJoinForm.txtGpfNo.value="";
        document.Hrm_TransJoinForm.comEmpId.value="";
        document.Hrm_TransJoinForm.txtEmpId.focus();
    }
    
    else if(flag=="failure2")
    {
        alert("This Employee already has an unfrezeed joining Record.\n So cann't create a new joining record for this Employee");
        document.Hrm_TransJoinForm.txtEmpId.value="";
        document.Hrm_TransJoinForm.txtDOB.value="";
        document.Hrm_TransJoinForm.txtGpfNo.value="";
        document.Hrm_TransJoinForm.comEmpId.value="";
        
        document.Hrm_TransJoinForm.txtEmpId.focus();
    }
    else if(flag=="failure1")
    {
        alert("This Employee does not have a post.\nCan not create a new join for this employee");
        document.Hrm_TransJoinForm.txtEmpId.value="";
        document.Hrm_TransJoinForm.txtDOB.value="";
        document.Hrm_TransJoinForm.txtGpfNo.value="";
        document.Hrm_TransJoinForm.comEmpId.value="";
        
        document.Hrm_TransJoinForm.txtEmpId.focus();
    }
}

function dispDesignRow(baseResponse)
{
    var OffCode=document.Hrm_TransJoinForm.txtOffice_Id.value;
    //alert(OffCode);
   OffName=document.Hrm_TransJoinForm.txtOffName.value;
   var OffAddr=document.Hrm_TransJoinForm.txtOffAddr.value;
   var EmpId=document.Hrm_TransJoinForm.txtEmpId.value;
  var Eid=document.Hrm_TransJoinForm.comEmpId.value;
   var DOB=document.Hrm_TransJoinForm.txtDOB.value;
   var GPFNO=document.Hrm_TransJoinForm.txtGpfNo.value;
   var JRId=document.Hrm_TransJoinForm.txtDOJ.value;
   var a=JRId.split("/");
   JRId=a[2];
   var JR=document.Hrm_TransJoinForm.txtJR.value;
   var DOJ=document.Hrm_TransJoinForm.txtDOJ.value;
   var design=document.Hrm_TransJoinForm.comDesign.value;
   var postcount=document.Hrm_TransJoinForm.comPostTow.value;
   var rem=document.Hrm_TransJoinForm.txtRemarks.value;
   var user_offid=document.Hrm_TransJoinForm.txtOffId.value;
   var dept_id=document.Hrm_TransJoinForm.dept_id.value;
   if(dept_id=="")
        dept_id="TWAD";
    var wing=0;
    try{
    wing=document.Hrm_TransJoinForm.cmbWing.value;
    }catch(e){}
    
   var radvalue;
   if(document.Hrm_TransJoinForm.radFNAN[0].checked==true)
   {
            radvalue=document.Hrm_TransJoinForm.radFNAN[0].value;
   }
   else
   {
            radvalue=document.Hrm_TransJoinForm.radFNAN[1].value;
   }





    var flag=baseResponse.getElementsByTagName("flag")[0].firstChild.nodeValue;
    var jj=baseResponse.getElementsByTagName("j")[0].firstChild.nodeValue;
    DOJ=document.Hrm_TransJoinForm.txtDOJ.value;
    OffCode=document.Hrm_TransJoinForm.txtOffice_Id.value;
     var yer=DOJ.substr(6,4);
     //alert("doj.."+yer);
    if(flag=="success")
    {
        document.Hrm_TransJoinForm.txtJR.value=jj;
             document.Hrm_TransJoinForm.txtJRId.value=OffCode+"/"+yer+"/"+jj;
       var empstatus=document.Hrm_TransJoinForm.txtempstatus.value;
       /*var compdate=document.Hrm_TransJoinForm.txtDOC.value;
        var compsession;
         if(document.Hrm_TransJoinForm.optFNAN[0].checked==true)
           {
                    compsession=document.Hrm_TransJoinForm.optFNAN[0].value;
           }
           else
           {
                    compsession=document.Hrm_TransJoinForm.optFNAN[1].value;
           }*/
           
   var optjoin='O';
   var currentoffice;
   if(document.Hrm_TransJoinForm.optjoin)
   {
       if(document.Hrm_TransJoinForm.optjoin[0].checked==true)
        {
            optjoin=document.Hrm_TransJoinForm.optjoin[0].value;
            currentoffice=OffCode;
            
        }
        else
        {
                optjoin=document.Hrm_TransJoinForm.optjoin[1].value;
                currentoffice=document.Hrm_TransJoinForm.cmbsubdivision.value;
        }
    } 
    else
    {
        currentoffice=OffCode;
    
    }
    
    if(document.Hrm_TransJoinForm.Office_Grade[0].checked==true)
   {
    var grade=document.Hrm_TransJoinForm.Office_Grade[0].value;
   }
   else if(document.Hrm_TransJoinForm.Office_Grade[1].checked==true)
   {
    var grade=document.Hrm_TransJoinForm.Office_Grade[1].value;
   }
   else if(document.Hrm_TransJoinForm.Office_Grade[2].checked==true)
   {
    var grade=document.Hrm_TransJoinForm.Office_Grade[2].value; 
   }
   else if(document.Hrm_TransJoinForm.Office_Grade[3].checked==true)
   {
    var grade=document.Hrm_TransJoinForm.Office_Grade[3].value; 
   }
    

           var url="../../../../../HrmTransJoinServ_New.view?Command=Add&txtOffId="+OffCode+
              "&txtEmpId="+EmpId+"&txtJR="+jj+"&radFNAN="+radvalue+"&txtDOJ="+DOJ+"&comDesign="+design+
              "&txtgrade="+grade+"&comPostTow="+postcount+"&txtRemarks="+rem+"&JYear="+a[2]+"&empstatus="+empstatus;//+"&compdate="+compdate+"&compsession="+compsession;
           url=url+"&optjoin="+optjoin+"&currentoffice="+currentoffice+"&wing="+wing+"&useroff="+user_offid+"&dept_id="+dept_id;
           
           //alert(url);
           var req=getTransport();
            req.open("GET",url,true); 
            req.onreadystatechange=function()
            {
               handleResponse(req);
            }   
                    if(window.XMLHttpRequest)
                        req.send(null);
                else req.send();
    }
    else
    {       
    
        document.Hrm_TransJoinForm.txtJR.value=jj;

        document.Hrm_TransJoinForm.txtJRId.value=OffCode+"/"+yer+"/"+jj;
       // alert("Programme ID already exists");
        
        
    }
}





function validate_date(formName, textName)
 {
        //alert("test");
                var errMsg="", lenErr=false, dateErr=false;
                var testObj=eval('document.' + formName + '.' + textName + '.value');
                var testStr=testObj.split('/');
                if(testStr.length>3 || testStr.length<3)
                {
                    lenErr=true;
                    dateErr=true;
                    errMsg+="There is an error in the date format.";
                }
                var monthsArr = new Array("01", "02", "03", "04", "05", "06", "07", "08" ,"09", "10", "11", "12");
                var daysArr = new Array;
                for (var i=0; i<12; i++)
                {
                    if(i!=1)
                    {
                       if((i/2)==(Math.round(i/2)))
                       {
                          if(i<=6)
                          {
                              daysArr[i]="31";
                           }
                           else
                           {
                               daysArr[i]="30";
                           }
                        }
                       else
                       {
                          if(i<=6)
                          {
                                daysArr[i]="30";
                          }
                          else
                          {
                               daysArr[i]="31";
                          }
                       }
                    }
                    else
                    {
                        if((testStr[2]/4)==(Math.round(testStr[2]/4)))
                        {
                            daysArr[i]="29";
                        }
                        else
                        {
                            daysArr[i]="28";
                        }
                    }
                } 
                var monthErr=false, yearErr=false;
                if(testStr[2]<1000 && !lenErr)
                {
                    yearErr=true;
                    dateErr=true;
                    errMsg+="\nThe year \"" + testStr[2] + "\" is not correct.";
                }
                for(var i=0; i<12; i++)
                {
                    if(testStr[1]==monthsArr[i])
                    {
                      var setMonth=i;
                      break;
                    }
                }
                if(!lenErr && (setMonth==undefined))
                {
                    monthErr=true;
                    errMsg+="\nThe month \"" + testStr[1] + "\" is not correct.";
                    dateErr=true;
                }
                if(!monthErr && !yearErr && !lenErr)
                {
                    if(testStr[0]>daysArr[setMonth])
                    {
                        errMsg+=testStr[1] + ' ' + testStr[2] + ' does not have ' + testStr[0] + ' days.';
                        dateErr=true;
                    }
                }
                if(!dateErr)
                {
                    //eval('document.' + formName + '.' + 'submit()');
                }
                else
                {
                    alert(errMsg + '\n____________________________\n\nSample Date Format :\n dd/MM/yyyy');
                    eval('document.' + formName + '.' + textName + '.focus()');
                    //alert(eval);
                    eval('document.' + formName + '.' + textName + '.select()');
                    
                    return false;
                }
                
                 return true;  
                     
 }
 
 
 ///////////////////////////////////////////////////////////////////////
 
  function nullcheck()
            {
                  if((document.Hrm_TransJoinForm.txtOffId.value=="") || (document.Hrm_TransJoinForm.txtOffId.value.length<=0))
                  {
                    alert("Please Enter Office_Id");
                    document.Hrm_TransJoinForm.txtOffId.value='';
                    document.Hrm_TransJoinForm.txtOffName.value='';
                    document.Hrm_TransJoinForm.txtOffAddr.value='';
                    document.Hrm_TransJoinForm.txtOffId.focus();
                    return false;
                    
                  }
                  
                  return true;
            }
 
 function callServer1(command,param)
{
//alert("Callserver Called"+command);
    var url="";
    var Office_Id="";
    var c= nullcheck();
    if(c==true)
    if(command=="Load")
    {
        Office_Id=document.Hrm_TransJoinForm.txtOffId.value;
     
        //alert(Office_Id);
        url="../../../../../HRE_OfficeDetailServ.view?command=Load&OfficeId="+Office_Id+"&txtDept_Id="+"TWAD";
        var req=getTransport();
        req.open("GET",url,true);        
        req.onreadystatechange=function()
        {
        processResponse(req);
        }
        if(window.XMLHttpRequest)
                        req.send(null);
                else req.send();
    }
    
    
    function processResponse(req)
          {
            if(req.readyState==4)
            {
                if(req.status==200)
                {
                      //alert(req.responseText);
                    //  var OfficeName=document.getElementById("txtOfficeName");
                      var OfficeId=document.getElementById("txtOffId");
                      
                      
                      var response=req.responseXML.getElementsByTagName("response")[0];
                      var flag=response.getElementsByTagName("flag")[0].firstChild.nodeValue;
                      if(flag=="failure")
                      {
                         alert("failed to retrieve the values");
                        document.Hrm_TransJoinForm.txtOffId.value='';
                        document.Hrm_TransJoinForm.txtOffName.value='';
                        document.Hrm_TransJoinForm.txtOffAddr.value='';
                       // document.Hrm_TransJoinForm.cmbsgroup.value=0;
                       // document.Hrm_TransJoinForm.cmbsgroup.selectedIndex=0;
                        document.Hrm_TransJoinForm.txtOffId.focus();
                      }
                      else if(flag=="sessionout")
            {
                alert('Session is closed');
                try{
                //opener.document.write("<br><br><br><br><b>You need to Sign in for accessing this page,<br>please follow this link to <a href=\"index.jsp\" target='_parent'>Sign In</a></b>");
                }catch(e){}
                self.close();
                return;
            }
                      else
                      {
                       
                        document.Hrm_TransJoinForm.txtOffName.value='';
                        document.Hrm_TransJoinForm.txtOffAddr.value='';
                         var value=response.getElementsByTagName("options");
                          for(var i=0;i<value.length;i++)
                          {
                              var tmpoption=value.item(i);
                              var id=tmpoption.getElementsByTagName("id")[0].firstChild.nodeValue;
                              var name=tmpoption.getElementsByTagName("name")[0].firstChild.nodeValue;
                              var officeAddress1=tmpoption.getElementsByTagName("officeAddress1")[0].firstChild.nodeValue;
                              var officeAddress2=tmpoption.getElementsByTagName("officeAddress2")[0].firstChild.nodeValue;
                              var officeAddress3=tmpoption.getElementsByTagName("officeAddress3")[0].firstChild.nodeValue;
                             // var district=tmpoption.getElementsByTagName("District")[0].firstChild.nodeValue;
                            
                              document.Hrm_TransJoinForm.txtOffName.value=name;
                              //document.Hrm_TransJoinForm.cmbDistrict.value=district;
                              if(officeAddress1!="null")
                              {
                              document.Hrm_TransJoinForm.txtOffAddr.value=officeAddress1;
                              }
                              if(officeAddress2!="null")
                              {
                                document.Hrm_TransJoinForm.txtOffAddr.value+=" "+officeAddress2;
                              }
                              if(officeAddress3!="null")
                              {
                                   document.Hrm_TransJoinForm.txtOffAddr.value+=" "+officeAddress3;
                             }
                             
                             
                             
                          }
                          
                      }   
                     

            }
        }
    }
}

/*
This function is used to get the selected employee_id from employee pop-up window and display it in employee id 
text box.
Next calling the dispEmp function to load the other details of particular employee.
*/

function doParentEmp(emp)
{
document.Hrm_TransJoinForm.txtEmpId.value=emp;
doFunction('dispEmp','null');

}


function checkdt(t)
{

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
           //alert(currentYear == getCurrentYear()  && currentMonth == getCurrentMonth() && currenDay > getCurrentDay());
           if(currentYear<1970)
           {

                   alert('Entered date should be greater than or equal to 1970');
                   t.value="";
                   t.focus();
                   return false;
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

           if(currentYear<1970)
           {

                   alert('Entered date should be greater than or equal to 1970');
                   t.value="";
                   t.focus();
                   return false;
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


function checkEID()
{
    if(document.Hrm_TransJoinForm.txtEmpId.value.length==0)
    {
    alert("select Employee Id");
    document.Hrm_TransJoinForm.comEmpId.value="";
    document.Hrm_TransJoinForm.txtDOB.value="";
    document.Hrm_TransJoinForm.txtGpfNo.value="";
    document.Hrm_TransJoinForm.txtEmpId.focus();
    return false;
    }
  return true;
}

function postcount()
{

document.Hrm_TransJoinForm.comPostTow.value=document.Hrm_TransJoinForm.comDesign.value;
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
function checkcurdt(t)
{
  
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
            //alert(currentYear == getCurrentYear()  && currentMonth == getCurrentMonth() && currenDay > getCurrentDay());
            if(currentYear > getCurrentYear()  || currentYear<_Service_Period_Beg_Year)
            {
            
                    alert('Entered date should be less than or equal to current date and \n year should be greater than or equal to '+_Service_Period_Beg_Year);
                    t.value="";
                    t.focus();
                    return false;
           } 
           else if(currentYear == getCurrentYear())
            {
                    if( currentMonth > getCurrentMonth())
                    {
                        alert('Entered date should be less than or equal to current date and \n year should be greater than or equal to '+_Service_Period_Beg_Year);
                        t.value="";
                        t.focus();
                        return false;
                    }
                    else if( currentMonth == getCurrentMonth())
                    {
                        if(currenDay > getCurrentDay() )
                        {
                                alert('Entered date should be less than or equal to current date and \n year should be greater than or equal to '+ _Service_Period_Beg_Year);
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
         
            if(currentYear > getCurrentYear()  || currentYear<_Service_Period_Beg_Year)
            {
            
                    alert('Entered date should be less than or equal to current date and \n year should be greater than or equal to '+_Service_Period_Beg_Year);
                    t.value="";
                    t.focus();
                    return false;
           } 
           else if(currentYear == getCurrentYear())
            {
                    if( currentMonth > getCurrentMonth())
                    {
                         alert('Entered date should be less than or equal to current date and \n year should be greater than or equal to '+_Service_Period_Beg_Year);
                        t.value="";
                        t.focus();
                        return false;
                    }
                    else if( currentMonth == getCurrentMonth())
                    {
                        if(currenDay > getCurrentDay() )
                        {
                                alert('Entered date should be less than or equal to current date and \n year should be greater than or equal to '+_Service_Period_Beg_Year);
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
            alert('Date format  should be (dd-mm-yyyy)');
            t.value="";
            //t.focus();
            return false
    }
    return true;
    
}


function getDesignation1()
    {
   
        var type=document.Hrm_TransJoinForm.cmbsgroup.options[document.Hrm_TransJoinForm.cmbsgroup.selectedIndex].value;
       // alert("test" + type);
        if(type!=0)
        {
        
            var din=document.getElementById("divdes");
            din.style.visibility="visible";
            document.Hrm_TransJoinForm.comDesign.style.visibility="visible";
            loadOfficesByType1(type);
        }
        else
        {
             var din=document.getElementById("divdes");
            din.style.visibility="hidden";
            document.Hrm_TransJoinForm.comDesign.style.visibility="hidden";
        }
    }
    
    function loadOfficesByType1(type)
    {
        //alert(type);
        startwaiting(document.Hrm_TransJoinForm) ;
        var type=document.Hrm_TransJoinForm.cmbsgroup.options[document.Hrm_TransJoinForm.cmbsgroup.selectedIndex].value;
       var url="../../../../../EmpJoinigPopupServ.view?Command=SGroup&cmbsgroup=" + type ;
        var req=getTransport();
        req.open("GET",url,true);        
        req.onreadystatechange=function()
        {
            
             loadDesignation1(req);
        }
        if(window.XMLHttpRequest)
                        req.send(null);
                else req.send();
    }
    
    
    function  loadDesignation1(req)
{
     if(req.readyState==4)
        {
          if(req.status==200)
          { 
                //alert(req);
                stopwaiting(document.Hrm_TransJoinForm) ;
                var des=document.getElementById("comDesign");
                var i=0;
                var response=req.responseXML.getElementsByTagName("response")[0];
                var flag=response.getElementsByTagName("flag")[0].firstChild.nodeValue;
                des.innerHTML="";
                
                if(flag=="failure")
                {
                    alert("No Designation exists under this level");
                }
                else if(flag=="sessionout")
            {
                alert('Session is closed');
                try{
                //opener.document.write("<br><br><br><br><b>You need to Sign in for accessing this page,<br>please follow this link to <a href=\"index.jsp\" target='_parent'>Sign In</a></b>");
                }catch(e){}
                self.close();
                return;
            }
                else
                {   
                
                    var value=response.getElementsByTagName("option");
                    var option=document.createElement("OPTION");
                    option.text="--Select Designation--";
                    option.value="0";
                    try
                    {
                        des.add(option);
                    }catch(errorObject)
                    {
                        des.add(option,null);
                    }
                    for(var i=0;i<value.length;i++)
                    {
                        var tmpoption=value.item(i);
                        var id=tmpoption.getElementsByTagName("id")[0].firstChild.nodeValue;
                        var name=tmpoption.getElementsByTagName("name")[0].firstChild.nodeValue;
                        var option=document.createElement("OPTION");
                          option.text=name;
                          option.value=id;
                          //Making Browser Independent
                          try
                          {
                              des.add(option);
                          }
                          catch(errorObject)
                          {
                              des.add(option,null);
                          }
                    }
                
                }
        
        }
        
    }
}

function subdivisioncheck()
{

    if(document.Hrm_TransJoinForm.optjoin[0].checked==true)
    {
        var id=document.getElementById("divsubdivision");
        id.style.display='none';
    }
    else
    {
            var id=document.getElementById("divsubdivision");
            id.style.display='block';
    }
}

function numbersonly1(e,t)
    {
       var unicode=e.charCode? e.charCode : e.keyCode;
       //alert(unicode);
       if(unicode==13)
        {
          try{t.blur();}catch(e){}
          return true;
        
        }
        if ( unicode!=8 && unicode !=9  )
        {
            if ((unicode<48||unicode>57 ) && (unicode<35||unicode>40 ) && unicode!=46 )
                return false 
        }
       
     }
     
     
function loadOffice(id)
{   
    if(id=="" || id==null)
    {
        alert("Enter or (Select An Office..Then Click choose..)");
        document.Hrm_TransJoinForm.txtOffice_Id.focus();
    }
    else
    {
            startwaiting(document.Hrm_TransJoinForm) ;
            var dept_id=document.Hrm_TransJoinForm.dept_id.value;
            if(dept_id=="")
                dept_id="TWAD";
            if(dept_id!="TWAD")
            {
                var url="../../../../../ServletLoadOfficeDetails.con?command=OtherOffice&ID="+id+"&DeptId="+dept_id;                    
            }
            else
            {
                var url="../../../../../ServletLoadOfficeDetails.con?command=TwadOffice&ID="+id;            
            }
            var req=getTransport();
            req.open("GET",url,true);       
            req.onreadystatechange=function()
            {                
               LoadOfficeDetails(req);             
            }
            req.send(null);        
    }
}

function LoadOfficeDetails(req)
{
   if(req.readyState==4)
    {
          if(req.status==200)
          {                
                stopwaiting(document.Hrm_TransJoinForm) ;
                var response=req.responseXML.getElementsByTagName("response")[0];                
                var flag=response.getElementsByTagName("flag")[0].firstChild.nodeValue;               
                if(flag=="success")
                {
                    var off_name=response.getElementsByTagName("name")[0];
                   /* var level=response.getElementsByTagName("level")[0].firstChild.nodeValue;
                    var type=response.getElementsByTagName("type")[0].firstChild.nodeValue;
                    var add=response.getElementsByTagName("address")[0].firstChild.nodeValue;
                    var address1=response.getElementsByTagName("address1")[0].firstChild.nodeValue;
                    var address2=response.getElementsByTagName("address2")[0].firstChild.nodeValue;
                    var city=response.getElementsByTagName("address")[0].firstChild.nodeValue;
                    var district=response.getElementsByTagName("district")[0].firstChild.nodeValue;
                    var pincode=response.getElementsByTagName("pincode")[0].firstChild.nodeValue;*/
                    
                    document.Hrm_TransJoinForm.txtOfficeName.value=off_name.firstChild.nodeValue;
                    
                    //alert(level);
                    /*
                    if(level=="H.O.")
                    {
                        alert("As this Office controls some other offices,so this Office cannot be Attached");
                        document.Hrm_TransJoinForm.txtOffice_Id.value="";
                        document.Hrm_TransJoinForm.txtOfficeName.value="";
                        document.Hrm_TransJoinForm.txtOfficeType.value="";
                        document.Hrm_TransJoinForm.txtOfficeAddress.value="";
                        document.Hrm_TransJoinForm.txtExistOfficeId.value="";
                        document.Hrm_TransJoinForm.txtExistOfficeName.value="";
                        document.Hrm_TransJoinForm.txtExistOfficeAddress.value="";
                        document.Hrm_TransJoinForm.txtOffice_Id.focus();
                    }
                    else
                    {
                    document.Hrm_TransJoinForm.txtOfficeName.value=name.firstChild.nodeValue;
                    if(level=="Division" || level=="Sub-Division")
                        document.Hrm_TransJoinForm.txtOfficeType.value=type;
                    else
                        document.Hrm_TransJoinForm.txtOfficeType.value="Office level : " + level;
                    
                   if(address1!="null")
                    {
                    document.Hrm_TransJoinForm.txtOfficeAddress.value=address1+"\n";
                    }
                    else
                    {
                    //document.Hrm_TransJoinForm.txtOfficeAddress.value="";
                    }
                    if(address2!="null")
                    {
                        document.Hrm_TransJoinForm.txtOfficeAddress.value=document.Hrm_TransJoinForm.txtOfficeAddress.value+address2+"\n";
                    }
                    else
                    {
                      //  document.Hrm_TransJoinForm.txtOfficeAddress.value=document.Hrm_TransJoinForm.txtOfficeAddress.value+"\n"+"";
                    }
                    if(city!="null")
                    {
                    document.Hrm_TransJoinForm.txtOfficeAddress.value=document.Hrm_TransJoinForm.txtOfficeAddress.value+city+"\n";
                    }
                    else
                    {
                       // document.Hrm_TransJoinForm.txtOfficeAddress.value=document.Hrm_TransJoinForm.txtOfficeAddress.value+"\n"+"";
                    }
                    if(district!="null")
                    {
                        document.Hrm_TransJoinForm.txtOfficeAddress.value=document.Hrm_TransJoinForm.txtOfficeAddress.value+district+"\n";
                    }
                    else
                    {
                       // document.Hrm_TransJoinForm.txtOfficeAddress.value=document.Hrm_TransJoinForm.txtOfficeAddress.value+"\n"+"";
                    }
                    if(pincode!=0)
                    {
                        if(pincode!="null")
                        {
                            document.Hrm_TransJoinForm.txtOfficeAddress.value=document.Hrm_TransJoinForm.txtOfficeAddress.value+pincode;
                        }
                    }
                    else
                    {
                        //document.Hrm_TransJoinForm.txtOfficeAddress.value=document.Hrm_TransJoinForm.txtOfficeAddress.value;
                    }*/
                    
                    //document.Hrm_TransJoinForm.txtOfficeAddress.value=document.Hrm_TransJoinForm.txtOfficeAddress.value;
                    
                    //document.Hrm_TransJoinForm.txtAttachedOfficeID.focus();
                    //OfficeLevel();
                    }
                }
                else
                {
                    var mess=response.getElementsByTagName("message")[0].firstChild.nodeValue;
                    alert("Error Occured : \n" + mess); 
                    document.Hrm_TransJoinForm.txtOfficeName.value="";
                } 
          }
}    
/*function LoadOtherOfficeDetails(req)
{
   if(req.readyState==4)
    {
          if(req.status==200)
          {                
                stopwaiting(document.Hrm_TransJoinForm) ;
                var response=req.responseXML.getElementsByTagName("response")[0];                
                var flag=response.getElementsByTagName("flag")[0].firstChild.nodeValue;               
                if(flag=="success")
                {
                    var off_name=response.getElementsByTagName("name")[0];
                    document.Hrm_TransJoinForm.txtOfficeName.value=off_name.firstChild.nodeValue;
                }
         }
         else
         {
                var mess=response.getElementsByTagName("message")[0].firstChild.nodeValue;
                alert("Error Occured : \n" + mess); 
                document.Hrm_TransJoinForm.txtOfficeName.value="";
         } 
    }
}       
*/

function noEnter(e)
{
   
   isIE=document.all? 1:0
       
   keyEntry = !isIE? e.which:event.keyCode;
                  
   if(keyEntry=='38')
   {
     return false;
   }
}


//***************To show the values of DBF*******************
function getRecords(command)
{

    var office_id=document.getElementById("txtOffId").value;
    var Acc_unit_id=document.getElementById("unit_name").value;
    var ac_month=document.getElementById("ac_month").value;
    var ac_year=document.getElementById("ac_year").value;
   
    var file_type;
    if(command=="Show_Sub"){
        file_type="s";
        command="Show";
    }
    else if(command=="Show_Withdraw"){
        file_type="w";
        command="Show";
    }
    else
        alert("File Type NotMatch");
    var url="../../../../../GPF_IMPOUND_SERVLET?command="+command+"&txtOffId="+office_id+"&unit_name="+Acc_unit_id+"&ac_month="+ac_month+"&ac_year="+ac_year+"&file_type="+file_type;
   var req=getTransport();
    req.open("POST",url,true); 
    req.onreadystatechange=function(){
        handleResponse(req);
    }  
        req.send(null);
}

/*function getErrors(command)
{
	var office_id=document.getElementById("txtOffId").value;
    var Acc_unit_id=document.getElementById("unit_name").value;
    var ac_month=document.getElementById("ac_month").value;
    var ac_year=document.getElementById("ac_year").value;
    var file_type;
   
    if(command=="Error_Sub"){
        file_type="s";
        command="Error";
    }
    else if(command=="Error_Withdraw"){
        file_type="w";
        command="Error";
    }
    else
        alert("File Type NotMatch");
     
    var url="../../../../../GPF_ERROR_LIST?&command="+command+"&txtOffId="+office_id+"&unit_name="+Acc_unit_id+"&ac_month="+ac_month+"&ac_year="+ac_year+"&file_type="+file_type;
    var req=getTransport();
    req.open("POST",url,true); 
    req.onreadystatechange=function(){
        handleResponse(req);
    }  
    req.send(null);
}*/



//****************DBF value is inserted to oracle table************
/*function setTableValue(Val_Type){
    var office_id=document.getElementById("txtOffId").value;
    var Acc_unit_id=document.getElementById("unit_name").value;
    var ac_month=document.getElementById("ac_month").value;
    var ac_year=document.getElementById("ac_year").value;
    var url="../../../../../GPF_Validate_Both?Val_Type="+Val_Type+"&txtOffId="+office_id+"&unit_name="+Acc_unit_id+"&ac_month="+ac_month+"&ac_year="+ac_year;
    var req=getTransport();
    req.open("POST",url,true);
    req.onreadystatechange=function(){
        handleResponse(req);
    } 
    req.send(null);
}*/


function getAccountUnit(){
	//alert("Account unit");
    var finyear=document.getElementById("fin_year");
    var finyear_val=finyear.options[finyear.selectedIndex].value;
    var finyear_arr=new Array(2);
    var now=new Date();
    finyear_val=finyear_val.split("-");
    if(document.getElementById("ac_year")!=null){
    var minorcmb = document.Hrm_TransJoinForm.ac_year;
    document.Hrm_TransJoinForm.ac_year.length=1;
    for(var i=0; i<finyear_val.length; i++){
    if(finyear_val[i]<=now.getFullYear()){
            var opt1 = document.createElement('option');
            opt1.value = finyear_val[i];
            opt1.innerHTML = finyear_val[i];
            minorcmb.appendChild(opt1);
            }
    }}
        var office_lvl=document.getElementById("txtOffLevel").value;
        var office_id=document.getElementById("txtOffId").value;
        var fin_year=document.getElementById("fin_year").value;
        var url="../../../../../AccountUnitServlet?office_lvl="+office_lvl+"&fin_year="+fin_year+"&office_id="+office_id;
        var req=getTransport();
            req.open("POST",url,true);
            req.onreadystatechange=function(){
                handleResponse(req);
            } 
            req.send(null);
}

function selectActMonthByActYear(){
	
	 v=new Date();
     curr_mn=v.getMonth();
     curr_yr=v.getFullYear();
    var acyear=document.getElementById("ac_year");
    var year_combo_len=acyear.length;
    var year1=acyear.options[1].value;  
    var year2;
    if(year_combo_len>2)
    	year2=acyear.options[2].value;
    else
    	year2=0;
    var sel_year=acyear.options[acyear.selectedIndex].value;
    var start_months = new Array('APR','MAY','JUN','JUL','AUG','SEP','OCT','NOV','DEC');
    var start_months_val = new Array(4,5,6,7,8,9,10,11,12);
    var end_months = new Array('JAN','FEB','MAR');
    var end_months_val = new Array(1,2,3);
    var minorcmb = document.Hrm_TransJoinForm.ac_month;
     document.Hrm_TransJoinForm.ac_month.length=1;
   
    if(parseInt(sel_year)==parseInt(year1)){
    	if(parseInt(sel_year)==parseInt(curr_yr)){
    		for(var i=0; i<parseInt(curr_mn-3); i++)
    	    {
    	         var opt1 = document.createElement('option');
    	         opt1.value = start_months_val[i];
    	         opt1.innerHTML = start_months[i];
    	         minorcmb.appendChild(opt1);
    	    }
    	}
    	else{
        for(var i=0; i<start_months.length; i++){
            var opt1 = document.createElement('option');
            opt1.value = start_months_val[i];
            opt1.innerHTML = start_months[i];
            minorcmb.appendChild(opt1);
        }
    	}
    }
    else if(parseInt(sel_year)==parseInt(year2)){
    for(var i=0; i<end_months.length; i++){
            var opt1 = document.createElement('option');
            opt1.value = end_months_val[i];
            opt1.innerHTML = end_months[i];
            minorcmb.appendChild(opt1);
        }
    }
    else if(parseInt(sel_year)==0){
    	alert("select");
    }
}
function getAccountUnit1(office_lvl){
var office_id=document.getElementById("txtOffId").value;
var fin_year=document.getElementById("fin_year").value;
var url="../../../../../AccountUnitServlet?office_lvl="+office_lvl+"&fin_year="+fin_year+"&office_id="+office_id;
var req=getTransport();
    req.open("POST",url,true);
    req.onreadystatechange=function(){
        handleResponse(req);
    } 
    //alert("********");
    req.send(null);
}