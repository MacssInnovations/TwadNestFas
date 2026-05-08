/////////////////////////////////////////////   XML req  /////////////////////////////////////////////////////
var rid;
var divNum;
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
{   //alert("dofunction");
    var url="";
    
    if(Command=="Add")
    { 
   // alert("Add");
    var Acc_UnitCode=document.frmFas_holidays_List.cmbAcc_UnitCode.value;
    var OffCode=document.frmFas_holidays_List.cmbOffice_code.value;
    var CashbookYear=document.frmFas_holidays_List.txtCB_Year.value;
    var CashbookMonth=document.frmFas_holidays_List.txtCB_Month.value;
    var txtCrea_date= document.getElementById("txtCrea_date").value;
    var remarks=document.getElementById("txtRemarks").value
        
      
        url="../../../../../FAS_Holidays_List?Command=Add&cmbAcc_UnitCode="+Acc_UnitCode+"&comOffCode="+OffCode+"&txtCrea_date="+txtCrea_date+"&txtRemarks="+remarks+"&CashbookYear="+CashbookYear+"&CashbookMonth="+CashbookMonth;
      // alert(url);
        var req=getTransport();
        req.open("GET",url,true); 
        req.onreadystatechange=function()
        {
           AddRecordResponse(req);
        }   
        
        req.send(null);
       
        
    }
   else if(Command=="Update")
    {
       
        
     var Acc_UnitCode=document.frmFas_holidays_List.cmbAcc_UnitCode.value;
    var OffCode=document.frmFas_holidays_List.cmbOffice_code.value;
    var CashbookYear=document.frmFas_holidays_List.txtCB_Year.value;
    var CashbookMonth=document.frmFas_holidays_List.txtCB_Month.value;
    var txtCrea_date= document.getElementById("txtCrea_date").value;
    var remarks=document.getElementById("txtRemarks").value
        
        
       
        url="../../../../../FAS_Holidays_List?Command=Update&&&cmbAcc_UnitCode="+Acc_UnitCode+"&comOffCode="+OffCode+"&txtCrea_date="+txtCrea_date+"&remarks="+remarks+"&CashbookYear="+CashbookYear+"&CashbookMonth="+CashbookMonth;
        //  alert(url);
        var req=getTransport();
        req.open("GET",url,true); 
        req.onreadystatechange=function()
        {
           UpdateResponse(req);
        }   
        
        req.send(null);
       
        
    }
    else if(Command=="Delete")
    {
           var Acc_UnitCode=document.frmFas_holidays_List.cmbAcc_UnitCode.value;
    var OffCode=document.frmFas_holidays_List.cmbOffice_code.value;
    var CashbookYear=document.frmFas_holidays_List.txtCB_Year.value;
    var CashbookMonth=document.frmFas_holidays_List.txtCB_Month.value;
    var txtCrea_date= document.getElementById("txtCrea_date").value;
    var remarks=document.getElementById("txtRemarks").value
       
        url="../../../../../FAS_Holidays_List?Command=Delete&&&cmbAcc_UnitCode="+Acc_UnitCode+"&comOffCode="+OffCode+"&txtCrea_date="+txtCrea_date+"&remarks="+remarks+"&CashbookYear="+CashbookYear+"&CashbookMonth="+CashbookMonth;
       // alert(url);
        var req=getTransport();
        req.open("GET",url,true); 
        req.onreadystatechange=function()
        {
           DeleteRecordResponse(req);
        }   
        req.send(null);
       
    }
     else if(Command=="Get")
        {               
            url="../../../../../FAS_Holidays_List?Command=Get";
//            alert("Loading the grid");
            var req=getTransport();
            req.open("GET",url,true); 
            req.onreadystatechange=function()
            {
               processResponse(req);
            }   
                    req.send(null);
        }
        else if(Command=="Get1")
        {               
            url="../../../../../FAS_Holidays_List?Command=Get1";
//           alert("Loading the grid of common List");
            var req=getTransport();
            req.open("GET",url,true); 
            req.onreadystatechange=function()
            {
               processResponse1(req);
            }   
                    req.send(null);
        }
        else if(Command=="Load")
        {     
        var Acc_UnitCode=document.frmFas_holidays_List.cmbAcc_UnitCode.value;
        var OffCode=document.frmFas_holidays_List.cmbOffice_code.value;
        var CashbookYear=document.frmFas_holidays_List.txtCB_Year.value;
        var CashbookMonth=document.frmFas_holidays_List.txtCB_Month.value;             
            url="../../../../../FAS_Holidays_List?Command=Load&cmbAcc_UnitCode="+Acc_UnitCode+"&comOffCode="+OffCode+"&CashbookYear="+CashbookYear+"&CashbookMonth="+CashbookMonth;
          // alert(url);
            var req=getTransport();
            req.open("GET",url,true); 
            req.onreadystatechange=function()
            {
             var t=0;
             var tbody=document.getElementById("tblList");
                for(t=tbody.rows.length-1;t>=0;t--)
                {
                   tbody.deleteRow(0);
                }
                       processResponse(req);
            }   
                    req.send(null);
        }
        else if(Command=="Load1")
        {     
//        var Acc_UnitCode=document.frmFas_holidays_Common.cmbAcc_UnitCode.value;
//        var OffCode=document.frmFas_holidays_Common.cmbOffice_code.value;
        var CashbookYear=document.frmFas_holidays_Common.txtCB_Year.value;
        var CashbookMonth=document.frmFas_holidays_Common.txtCB_Month.value;             
            url="../../../../../FAS_Holidays_List?Command=Load1&CashbookYear="+CashbookYear+"&CashbookMonth="+CashbookMonth;
          // alert(url);
            var req=getTransport();
            req.open("GET",url,true); 
            req.onreadystatechange=function()
            {
             var t=0;
             var tbody=document.getElementById("tblList");
                for(t=tbody.rows.length-1;t>=0;t--)
                {
                   tbody.deleteRow(0);
                }
                       processResponse1(req);
            }   
                    req.send(null);
        }
         else if(Command=="AddCommon")
        {     
             var CashbookYear=document.frmFas_holidays_Common.txtCB_Year.value;
             var CashbookMonth=document.frmFas_holidays_Common.txtCB_Month.value;
             var txtCrea_date= document.getElementById("txtCrea_date").value;
             var remarks=document.getElementById("txtRemarks").value;
                url="../../../../../FAS_Holidays_List?Command=CommonList&CashbookYear="+CashbookYear+"&CashbookMonth="+CashbookMonth+"&txtCrea_date1="+txtCrea_date+"&remarks="+remarks;
//                alert(url);
            var req=getTransport();
            req.open("GET",url,true); 
            req.onreadystatechange=function()
            {
                    AddRecordResponse1(req);
            }
            req.send(null);                   
        }
        else if(Command=="UpdateCommon")
    {
       
        
//     var Acc_UnitCode=document.frmFas_holidays_List.cmbAcc_UnitCode.value;
//    var OffCode=document.frmFas_holidays_List.cmbOffice_code.value;
    var CashbookYear=document.frmFas_holidays_Common.txtCB_Year.value;
    var CashbookMonth=document.frmFas_holidays_Common.txtCB_Month.value;
    var txtCrea_date= document.getElementById("txtCrea_date").value;
    var remarks=document.getElementById("txtRemarks").value
        
        
       
        url="../../../../../FAS_Holidays_List?Command=UpdateCommon&&&txtCrea_date="+txtCrea_date+"&remarks="+remarks+"&CashbookYear="+CashbookYear+"&CashbookMonth="+CashbookMonth;
//          alert(url);
        var req=getTransport();
        req.open("GET",url,true); 
        req.onreadystatechange=function()
        {
           UpdateResponse1(req);
        }   
        
        req.send(null);
       
        
    }
    else if(Command=="DeleteCommon")
    {
//           var Acc_UnitCode=document.frmFas_holidays_List.cmbAcc_UnitCode.value;
//    var OffCode=document.frmFas_holidays_List.cmbOffice_code.value;
    var CashbookYear=document.frmFas_holidays_Common.txtCB_Year.value;
    var CashbookMonth=document.frmFas_holidays_Common.txtCB_Month.value;
    var txtCrea_date= document.getElementById("txtCrea_date").value;
    var remarks=document.getElementById("txtRemarks").value
       
        url="../../../../../FAS_Holidays_List?Command=DeleteCommon&&&txtCrea_date="+txtCrea_date+"&remarks="+remarks+"&CashbookYear="+CashbookYear+"&CashbookMonth="+CashbookMonth;
       // alert(url);
        var req=getTransport();
        req.open("GET",url,true); 
        req.onreadystatechange=function()
        {
           DeleteRecordResponse1(req);
        }   
        req.send(null);
       
    }
}

//added on 27/01/2012
function AddRecordResponse1(req)
{
    
    if(req.readyState==4)
    {
        if(req.status==200)
        {  
            
            var baseResponse=req.responseXML.getElementsByTagName("response")[0];
            var flag=baseResponse.getElementsByTagName("flag")[0].firstChild.nodeValue;
            if(flag=="success")
            {
                alert("Record Inserted Successfully");
                document.frmFas_holidays_Common.txtCrea_date.value="";
                document.frmFas_holidays_Common.txtRemarks.value="";
                 var tbody=document.getElementById("tblList");
                    var t=0;
                    for(t=tbody.rows.length-1;t>=0;t--)
                    {
                       tbody.deleteRow(0);
                    }
                           // removeElement(divNum)
                            doFunction('Get1','null') 
                            //document.frmFas_holidays_List.radCrDrInd[0].checked=true;
            }
            else if(flag=="failure")
            {
                alert("Record Not Inserted Successfully");
                document.frmFas_holidays_Common.txtCrea_date.value="";
                document.frmFas_holidays_Common.txtRemarks.value="";
            }
            if(flag=="AlreadyExist")
            {
                alert("Record Already Exist");
                document.frmFas_holidays_Common.txtCrea_date.value="";
                document.frmFas_holidays_Common.txtRemarks.value="";
            }
            
        }
    }
}


function AddRecordResponse(req)
{

    if(req.readyState==4)
    {
        if(req.status==200)
        {  
            
            var baseResponse=req.responseXML.getElementsByTagName("response")[0];
            var flag=baseResponse.getElementsByTagName("flag")[0].firstChild.nodeValue;
            if(flag=="success")
            {
                alert("Record Inserted Successfully");
                
               document.frmFas_holidays_List.txtCrea_date.value="";
                document.frmFas_holidays_List.txtRemarks.value="";
                 var tbody=document.getElementById("tblList");
        var t=0;
        for(t=tbody.rows.length-1;t>=0;t--)
        {
           tbody.deleteRow(0);
        }
               // removeElement(divNum)
                doFunction('Get','null') 
                
                //document.frmFas_holidays_List.radCrDrInd[0].checked=true;
                
            }
            else if(flag=="failure")
            {
                alert("Record Not Inserted Successfully");
                document.frmFas_holidays_List.txtCrea_date.value="";
                document.frmFas_holidays_List.txtRemarks.value="";
                
              
                
            }
            if(flag=="AlreadyExist")
            {
                alert("Record Already Exist");
                document.frmFas_holidays_List.txtCrea_date.value="";
                document.frmFas_holidays_List.txtRemarks.value="";
                
                
                
            }
            
        }
    }
}

function UpdateResponse(req)
{

    if(req.readyState==4)
    {
        if(req.status==200)
        {  
            
            var baseResponse=req.responseXML.getElementsByTagName("response")[0];
            var flag=baseResponse.getElementsByTagName("flag")[0].firstChild.nodeValue;
            
            if(flag=="success")
            {
                alert("Record Updated Successfully");
               document.frmFas_holidays_List.txtCrea_date.value="";
                document.frmFas_holidays_List.txtRemarks.value="";
                 var tbody=document.getElementById("tblList");
        var t=0;
        for(t=tbody.rows.length-1;t>=0;t--)
        {
           tbody.deleteRow(0);
        }
               // removeElement(divNum)
                doFunction('Get','null') 
                
                //document.frmFas_holidays_List.radCrDrInd[0].checked=true;
                var d=document.getElementById("cmdUpdate");
                d.style.display="none";
                
                var d2=document.getElementById("cmdDelete");
                d2.style.display="none";
                
                var d1=document.getElementById("cmdAdd");
                d1.style.display="block";
            }
            else
            {
                alert("Record Not Updated");
               document.frmFas_holidays_List.txtCrea_date.value="";
                document.frmFas_holidays_List.txtRemarks.value="";
                
                
                //document.frmFas_holidays_List.radCrDrInd[0].checked=true;
                var d=document.getElementById("cmdUpdate");
                d.style.display="none";
                
                var d2=document.getElementById("cmdDelete");
                d2.style.display="none";
                
                var d1=document.getElementById("cmdAdd");
                d1.style.display="block";
            }
        }
    }
}
function UpdateResponse1(req)
{

    if(req.readyState==4)
    {
        if(req.status==200)
        {  
            
            var baseResponse=req.responseXML.getElementsByTagName("response")[0];
            var flag=baseResponse.getElementsByTagName("flag")[0].firstChild.nodeValue;
            
            if(flag=="success")
            {
                alert("Record Updated Successfully");
               document.frmFas_holidays_Common.txtCrea_date.value="";
                document.frmFas_holidays_Common.txtRemarks.value="";
                 var tbody=document.getElementById("tblList");
        var t=0;
        for(t=tbody.rows.length-1;t>=0;t--)
        {
           tbody.deleteRow(0);
        }
               // removeElement(divNum)
                doFunction('Get1','null') 
                
                //document.frmFas_holidays_List.radCrDrInd[0].checked=true;
                var d=document.getElementById("cmdUpdate");
                d.style.display="none";
                
                var d2=document.getElementById("cmdDelete");
                d2.style.display="none";
                
                var d1=document.getElementById("cmdAdd");
                d1.style.display="block";
            }
            else
            {
                alert("Record Not Updated");
               document.frmFas_holidays_Common.txtCrea_date.value="";
                document.frmFas_holidays_Common.txtRemarks.value="";
                
                
                //document.frmFas_holidays_List.radCrDrInd[0].checked=true;
                var d=document.getElementById("cmdUpdate");
                d.style.display="none";
                
                var d2=document.getElementById("cmdDelete");
                d2.style.display="none";
                
                var d1=document.getElementById("cmdAdd");
                d1.style.display="block";
            }
        }
    }
}

function DeleteRecordResponse(req)
{

    if(req.readyState==4)
    {
        if(req.status==200)
        {  
            
            var baseResponse=req.responseXML.getElementsByTagName("response")[0];
            var flag=baseResponse.getElementsByTagName("flag")[0].firstChild.nodeValue;
            
            if(flag=="success")
            {
                alert("Record Deleted Successfully");
                document.frmFas_holidays_List.txtCrea_date.value="";
                document.frmFas_holidays_List.txtRemarks.value="";
                 var tbody=document.getElementById("tblList");
        var t=0;
        for(t=tbody.rows.length-1;t>=0;t--)
        {
           tbody.deleteRow(0);
        }
               // removeElement(divNum)
                doFunction('Get','null') 
                var d=document.getElementById("cmdUpdate");
                d.style.display="none";
                
                var d2=document.getElementById("cmdDelete");
                d2.style.display="none";
                
                var d1=document.getElementById("cmdAdd");
                d1.style.display="block";
            }
            else
            {
                alert("Record Not Deleted");
                document.frmFas_holidays_List.txtCrea_date.value="";
                document.frmFas_holidays_List.txtRemarks.value="";
                
                var d=document.getElementById("cmdUpdate");
                d.style.display="none";
                
                var d2=document.getElementById("cmdDelete");
                d2.style.display="none";
                
                var d1=document.getElementById("cmdAdd");
                d1.style.display="block";
            }
        }
    }
}
function DeleteRecordResponse1(req)
{

    if(req.readyState==4)
    {
        if(req.status==200)
        {  
            
            var baseResponse=req.responseXML.getElementsByTagName("response")[0];
            var flag=baseResponse.getElementsByTagName("flag")[0].firstChild.nodeValue;
            
            if(flag=="success")
            {
                alert("Record Deleted Successfully");
                document.frmFas_holidays_Common.txtCrea_date.value="";
                document.frmFas_holidays_Common.txtRemarks.value="";
                 var tbody=document.getElementById("tblList");
        var t=0;
        for(t=tbody.rows.length-1;t>=0;t--)
        {
           tbody.deleteRow(0);
        }
               // removeElement(divNum)
                doFunction('Get1','null') 
                var d=document.getElementById("cmdUpdate");
                d.style.display="none";
                
                var d2=document.getElementById("cmdDelete");
                d2.style.display="none";
                
                var d1=document.getElementById("cmdAdd");
                d1.style.display="block";
            }
            else
            {
                alert("Record Not Deleted");
                document.frmFas_holidays_Common.txtCrea_date.value="";
                document.frmFas_holidays_Common.txtRemarks.value="";
                
                var d=document.getElementById("cmdUpdate");
                d.style.display="none";
                
                var d2=document.getElementById("cmdDelete");
                d2.style.display="none";
                
                var d1=document.getElementById("cmdAdd");
                d1.style.display="block";
            }
        }
    }
}

//********************************** Numbers Only Checking *****************************//

function numbersonly(e,t)
    {
       var unicode=e.charCode? e.charCode : e.keyCode;
       if(unicode==13)
        {
          try{t.blur();}catch(e){}
          return true;
        
        }
        if (unicode!=8 && unicode !=9  )
        {
            if (unicode<48||unicode>57 ) 
                return false 
        }
     }

function ClearAll()
    {
       
       
        document.frmFas_holidays_List.txtCrea_date.value="";
        document.frmFas_holidays_List.txtRemarks.value="";
        
        var d=document.getElementById("cmdAdd");
        d.style.display="block";
    
        var d1=document.getElementById("cmdUpdate");
        d1.style.display="none";
        
        var d3=document.getElementById("cmdDelete");
        d3.style.display="none";
        
    }
    
   function ClearAll1()
    {
       
       
        document.frmFas_holidays_Common.txtCrea_date.value="";
        document.frmFas_holidays_Common.txtRemarks.value="";
        
        var d=document.getElementById("cmdAdd");
        d.style.display="block";
                
    } 
function nullcheck()
{
  //alert("nullcheck");
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
        if(document.getElementById("txtCrea_date").value.length==0)
        {
            alert("Enter the Date of Creation");
            //document.getElementById("txtCrea_date").focus();
            return false;    
        }
}



function key(e,t)
{
    var unicode=e.charCode? e.charCode : e.keyCode;
    
    if(unicode==32 || unicode==43 || unicode==45 || unicode==61 || unicode==92)
    {
        return false;
    }
    else
    {
    return true;
    }
    
}

function processResponse(req)
    { var seq=0;  
      if(req.readyState==4)
        {
          if(req.status==200)
          { var baseResponse=req.responseXML.getElementsByTagName("response")[0];
            var flag=baseResponse.getElementsByTagName("flag")[0].firstChild.nodeValue;            
             
 
              
              if(flag=="success")
              {          
              
                    
                           var items_id=new Array();
                           var holidaydate=new Array();
                           var reason=new Array();
        
         
         // cadid=baseResponse.getElementsByTagName("unitid")[0].firstChild.nodeValue;
         var unitid=baseResponse.getElementsByTagName("unitid");
         for(var k=0;k<unitid.length;k++)
            {   holidaydate[k]=baseResponse.getElementsByTagName("holi_date")[k].firstChild.nodeValue;
                reason[k]=baseResponse.getElementsByTagName("reason")[k].firstChild.nodeValue;
                
            }
             for(var k=0;k<unitid.length;k++){
             
                    var table=document.getElementById("mytable");
                     var tbody=document.getElementById("tblList");
                        var mycurrent_row=document.createElement("TR");
                         seq=seq+1;
                        var i=0;
                        var cell2;
                         var mycurrent_row=document.createElement("TR");
                 mycurrent_row.id=seq;
//                 alert("testing inside loading &&&&&"+seq);
                 var cell=document.createElement("TD");
                 var anc=document.createElement("A");       
                 var url="javascript:loadValuesFromTable('" + seq + "')";              
                 anc.href=url;
                 var txtedit=document.createTextNode("Edit");
                 anc.appendChild(txtedit);
                 cell.appendChild(anc);
                 mycurrent_row.appendChild(cell);
                
                   cell2=document.createElement("TD");
                   var holidaydatehid="";
                   if (window.navigator.appName.toLowerCase().indexOf("netscape") == -1)
                   {
                   holidaydatehid=document.createElement("<input type='text' id='holi_date_hid' name='holi_date_hid' size='10' value='"+holidaydate[k]+"' onkeypress='return calins(event,this)' onblur='return checkdt(this)' readOnly >");
                   }
                   else
                   {
                   var holidaydatehid=document.createElement("input");
                  holidaydatehid.type="text";
                  holidaydatehid.name="holi_date_hid";
                  holidaydatehid.id="holi_date_hid";
                  holidaydatehid.size=10;
                  holidaydatehid.readOnly=true;
                  holidaydatehid.setAttribute('onkeypress','return calins(event,this)');
                  
                  holidaydatehid.value=holidaydate[k];
                  }
                  cell2.appendChild(holidaydatehid);
                  mycurrent_row.appendChild(cell2);
                   
                 var reasonhid="";
              cell2=document.createElement("TD");
               if (window.navigator.appName.toLowerCase().indexOf("netscape") == -1){
                  //reasonhid=document.createElement("<textarea id='reason_hid' name='reason_hid' value='"+reason+"' cols='20' rows='2'></textarea>");
                  reasonhid=document.createElement("<input type='text' id='reason_hid' name='reason_hid' size='50' value='"+reason[k]+"' readOnly></input>");
                  }
                  else
                  {
                  reasonhid=document.createElement("input");
                  reasonhid.type="text";
                  reasonhid.name="reason_hid";
                  reasonhid.id="reason_hid";
                  reasonhid.size="50";
                  reasonhid.readOnly=true;
                  reasonhid.value=reason[k];
//                  alert("reason..."+reasonhid.value);
                  }
                  
                   cell2.appendChild(reasonhid);    
                
                mycurrent_row.appendChild(cell2);
               
                    tbody.appendChild(mycurrent_row);
      
            } 
            
                    
                        
                        }
                  }
                  else
                  {
                    alert("Failed to Load Values");
                  }
                 }
           }
           
  function loadValuesFromTable(rid1)
    {      
        rid=rid1;
          var r=document.getElementById(rid); 
          var rcells=r.cells;
          var tbody=document.getElementById("tblList");
          
          var table=document.getElementById("mytable");
          
          document.frmFas_holidays_List.txtCrea_date.value=rcells.item(1).lastChild.value;
          document.frmFas_holidays_List.txtRemarks.value=rcells.item(2).lastChild.value;
         // document.frmFas_holidays_List.txtdepreciationrates.value=rcells.item(3).firstChild.nodeValue;
          document.frmFas_holidays_List.cmdAdd.disabled=true;
        document.frmFas_holidays_List.cmdUpdate.disabled=false;
        document.frmFas_holidays_List.cmdDelete.disabled=false;
        
          document.frmFas_holidays_List.cmdDelete.focus();
      
    }
   function loadValuesFromTable1(rid1)
    {      
        rid=rid1;
          var r=document.getElementById(rid); 
          var rcells=r.cells;
          var tbody=document.getElementById("tblList");
          
          var table=document.getElementById("mytable");
          
          document.frmFas_holidays_Common.txtCrea_date.value=rcells.item(1).lastChild.value;
          document.frmFas_holidays_Common.txtRemarks.value=rcells.item(2).lastChild.value;
         // document.frmFas_holidays_List.txtdepreciationrates.value=rcells.item(3).firstChild.nodeValue;
          document.frmFas_holidays_Common.cmdAdd.disabled=true;
           document.frmFas_holidays_Common.cmdUpdate.disabled=false;
        document.frmFas_holidays_Common.cmdDelete.disabled=false;
        
          document.frmFas_holidays_Common.cmdDelete.focus();
          
    }
     
 function removeElement(divNum) {

  var d = document.getElementById("mytable");
  var olddiv = document.getElementById("tblList");
  d.removeChild(olddiv);
}
//added on 27/01/2012 for listing the General Holidays List
var window_CommonList;
function ListAll()
{    
//    alert("inside the Listall function for showing the Holidays....");     
     if (window_CommonList && window_CommonList.open && !window_CommonList.closed)
     {
       window_CommonList.resizeTo(500,500);
       window_CommonList.moveTo(250,250); 
       window_CommonList.focus();
    }
    else
    {
        window_CommonList=null;
    }
         var cmbAcc_UnitCode=document.frmFas_holidays_List.cmbAcc_UnitCode.value;
         var cmbOffice_code=document.frmFas_holidays_List.cmbOffice_code.value;
        var CB_Year=document.frmFas_holidays_List.txtCB_Year.value;
        var CB_Month=document.frmFas_holidays_List.txtCB_Month.value;
//         var financial_year = document.getElementById("cmbFinancialYear").value;
        //  var cmbassetvehicle=document.getElementById("cmbassetvehicle").value;
         window_CommonList= window.open("FAS_Holidays_commonList.jsp?cmbAcc_UnitCode="+cmbAcc_UnitCode+"&cmbOffice_code="+cmbOffice_code+"&CB_Year="+CB_Year+"&CB_Month="+CB_Month,"mywindow1","resizable=YES, scrollbars=yes"); 
         window_CommonList.moveTo(250,250);    
    }

window.onunload=function()
{
if (window_CommonList && window_CommonList.open && !window_CommonList.closed) window_CommonList.close();
};
function processResponse1(req)
    { var seq=0;  
      if(req.readyState==4)
        {
          if(req.status==200)
          { var baseResponse=req.responseXML.getElementsByTagName("response")[0];
            var flag=baseResponse.getElementsByTagName("flag")[0].firstChild.nodeValue;            
             
 
              
              if(flag=="success")
              {          
              
                    
                           var items_id=new Array();
                           var holidaydate=new Array();
                           var reason=new Array();
        
         
         // cadid=baseResponse.getElementsByTagName("unitid")[0].firstChild.nodeValue;
         var unitid=baseResponse.getElementsByTagName("holi_date");
         for(var k=0;k<unitid.length;k++)
            {   holidaydate[k]=baseResponse.getElementsByTagName("holi_date")[k].firstChild.nodeValue;
                reason[k]=baseResponse.getElementsByTagName("reason")[k].firstChild.nodeValue;
                
            }
             for(var k=0;k<unitid.length;k++){
             
                    var table=document.getElementById("mytable");
                     var tbody=document.getElementById("tblList");
                        var mycurrent_row=document.createElement("TR");
                         seq=seq+1;
                        var i=0;
                        var cell2;
                         var mycurrent_row=document.createElement("TR");
                 mycurrent_row.id=seq;
//                 alert("testing inside loading &&&&&"+seq);
                 var cell=document.createElement("TD");
                 var anc=document.createElement("A");       
                 var url="javascript:loadValuesFromTable1('" + seq + "')";              
                 anc.href=url;
                 var txtedit=document.createTextNode("Edit");
                 anc.appendChild(txtedit);
                 cell.appendChild(anc);
                 mycurrent_row.appendChild(cell);
                
                   cell2=document.createElement("TD");
                   var holidaydatehid="";
                   if (window.navigator.appName.toLowerCase().indexOf("netscape") == -1)
                   {
                   holidaydatehid=document.createElement("<input type='text' id='holi_date_hid' name='holi_date_hid' size='10' value='"+holidaydate[k]+"' onkeypress='return calins(event,this)' onblur='return checkdt(this)' readOnly >");
                   }
                   else
                   {
                   var holidaydatehid=document.createElement("input");
                  holidaydatehid.type="text";
                  holidaydatehid.name="holi_date_hid";
                  holidaydatehid.id="holi_date_hid";
                  holidaydatehid.size=10;
                  holidaydatehid.readOnly=true;
                  holidaydatehid.setAttribute('onkeypress','return calins(event,this)');
                  
                  holidaydatehid.value=holidaydate[k];
                  }
                  cell2.appendChild(holidaydatehid);
                  mycurrent_row.appendChild(cell2);
                   
                 var reasonhid="";
              cell2=document.createElement("TD");
               if (window.navigator.appName.toLowerCase().indexOf("netscape") == -1){
                  //reasonhid=document.createElement("<textarea id='reason_hid' name='reason_hid' value='"+reason+"' cols='20' rows='2'></textarea>");
                  reasonhid=document.createElement("<input type='text' id='reason_hid' name='reason_hid' size='50' value='"+reason[k]+"' readOnly></input>");
                  }
                  else
                  {
                  reasonhid=document.createElement("input");
                  reasonhid.type="text";
                  reasonhid.name="reason_hid";
                  reasonhid.id="reason_hid";
                  reasonhid.size="50";
                  reasonhid.readOnly=true;
                  reasonhid.value=reason[k];
//                  alert("reason..."+reasonhid.value);
                  }
                  
                   cell2.appendChild(reasonhid);    
                
                mycurrent_row.appendChild(cell2);
               
                    tbody.appendChild(mycurrent_row);
      
            } 
            
                    
                        
                        }
                  }
                  else
                  {
                    alert("Failed to Load Values");
                  }
                 }
           }