//alert("hello");
var assetclass;
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
    window.open('','_parent','');
    window.close();
 }
function loadDate()
{
   /*
    var ddate=new Date();
    var mon= ddate.getMonth();
    var yr1 = ddate.getYear();
    var yr2= ddate.getYear();
      if(yr1 < 1900) yr1 += 1900;
       if(yr2 < 1900) yr2 += 1900;
     
    mon=parseInt(mon)+1;
    if(mon<=3 && mon>=1)
        yr1=parseInt(yr1)-1;
    else if(mon>=4 && mon<=12)
        yr2=parseInt(yr2)+1;
   
   // document.AssetForm.txtFinYear.value=yr1+"-"+yr2;      // loading finance year
    
     var today= new Date(); 
         var day=today.getDate();
         var month=today.getMonth();
         month=month+1;
         
         if(day<=9 && day>=1)
         day="0"+day;
         if(month<=9 && month>=1)
         month="0"+month;
         var year=today.getYear();
         if(year < 1900) year += 1900;
         var monthArray =new Array("January", "February", "March", 
                   "April", "May", "June", "July", "August",
                   "September", "October", "November", "December");
      //  document.AssetForm.txtDate.value=day+"/"+month+"/"+year;      // Load the current date
  */
}
    
function limit_amt(field,e)
{
    
      var unicode=e.charCode? e.charCode : e.keyCode;
    //alert(unicode)
      if(field.value.length<17)
      {
        if(field.value.length==14 && field.value.indexOf('.')==-1  )
        field.value=field.value+'.';
        if (unicode!=8 && unicode !=9  )
        {
            //if (unicode<46 || unicode==47 || unicode>57   )         // before if statement without '-' 
            if (unicode<45 || unicode==47 || unicode>57   )         // 45 is allowed to enter '-' value
                return false 
        }
      }
      else 
      return false;
}

function valid_amt(field)
{
    
    amt=field.value;
    if(amt.indexOf(".")!=amt.lastIndexOf("."))
    {
        alert("Enter a Valid Amount");
        field.value="";
        field.focus();
    }
}

//////////////   FOR JOB POPUP WINDOW //////////////////////
var winjob;

function jobpopup1()
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
        //startwaiting(document.AssetForm) ;
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
    document.AssetForm.txtlocation.value=jobid;
    doFunction('office',jobid);
}

window.onunload=function()
{
if (listPopupwindow && listPopupwindow.open && !listPopupwindow.closed) listPopupwindow.close();
if (winjob && winjob.open && !winjob.closed) winjob.close();
}
var listPopupwindow;

function listall()
{
   
    //var Acc_UnitCode=document.AssetForm.cmbAcc_UnitCode.value;
    //var OffCode=document.AssetForm.comOffCode.value;
    //var FinanYr=document.AssetForm.txtFinYear.value;
    //alert(Acc_UnitCode+" " +OffCode+ "  "+ FinanYr)
  //listPopupwindow= window.open("AssetListJSP.jsp?cmbAcc_UnitCode="+Acc_UnitCode+"&cmbOffice_code="+OffCode+"&txtFinYear="+FinanYr,"mywindow1","status=1,height=400,width=500,resizable=YES, scrollbars=yes"); 
  listPopupwindow= window.open("AssetListJSP.jsp?","mywindow1","status=1,height=400,width=500,resizable=YES, scrollbars=yes"); 
  listPopupwindow.moveTo(250,250);    
}

 
function call_mainJSP_script(fromcal_dateCtrl,blr_flag)     /////////Calender control return value 
{
    finanYear();
}
function finanYear()
{
var AsOnDate=document.AssetForm.txtDate.value;
if(AsOnDate.length!=0)
{
     var a=AsOnDate.split("/");
     var mon=a[1];
     
     var yr1 = a[2];
        var yr2= a[2];
        mon=parseInt(mon);
        if(mon<=3 && mon>=1)
            yr1=parseInt(yr1)-1;
        else if(mon>=4 && mon<=12)
            yr2=parseInt(yr2)+1;
        document.AssetForm.txtFinYear.value=yr1+"-"+yr2;
        
 }   
 else
   {
   alert("Enter the date")
   }
}

function clearall()
{
//document.AssetForm.comOffCode.selectedIndex=0;
//document.AssetForm.comClasAss.selectedIndex=0;
document.AssetForm.txtAssCode.value="";
document.AssetForm.txtAssTypeCode.value="";
document.AssetForm.comClasAss.value="";
document.AssetForm.txtAliasCode.value="";


document.AssetForm.txtDesAsset.value="";
document.AssetForm.txtPurchaseYear.value="";
document.AssetForm.txtPurchaseMonth.value="";

document.AssetForm.comOwner.value="";
document.AssetForm.txtDenName.value="";
document.AssetForm.comFuel.value="";
document.AssetForm.txtlocation.value="";
document.getElementById("txtlocationName").value="";

document.AssetForm.txtOrigCost.value="";
document.AssetForm.txtCurrVal.value="";
document.AssetForm.txtRem.value="";
document.AssetForm.txtPercDep.value="";
//document.AssetForm.txtAssType.value="";

document.AssetForm.txtDate.value="";
document.AssetForm.txtFinYear.value="";
document.AssetForm.txtDate.readOnly=false;            // make read only
document.AssetForm.txtFinYear.readOnly=false;              // make read only
document.getElementById("calenderCTRL").style.visibility="visible";
 document.getElementById("vehicle").style.display="none";
 
     var comClasAss=document.getElementById("comClasAss");
    comClasAss.innerHTML="";
    var option=document.createElement("OPTION");
    option.text="--Select Asset Class--";
    option.value="";
    try
    {
        comClasAss.add(option);
    }catch(errorObject)
    {
        comClasAss.add(option,null);
    }


    var d=document.getElementById("cmdAdd");
    d.style.display="block";
    
    var d1=document.getElementById("cmdUpdate");
    d1.style.display="none";
    
    var d2=document.getElementById("cmdDelete");
    d2.style.display="none";

}

function checkYear()
{
        var yr=new Date();
        yr=yr.getYear();
          if(yr < 1900) yr += 1900;
        var PurYear=document.AssetForm.txtPurchaseYear.value;
        if(PurYear.length!=4 && PurYear.length>0)
        {
            alert("Purchase year format should be 'YYYY'");
            document.AssetForm.txtPurchaseYear.value="";
            document.AssetForm.txtPurchaseYear.focus(); 
            return false;
        }
        if(parseInt(PurYear)>parseInt(yr))
        {
            alert("Purchase Year should not greater than Current year");
            document.AssetForm.txtPurchaseYear.focus();
            document.AssetForm.txtPurchaseYear.value="";
        }
}

function purMonth()  // not used so far
{
var AsOnDate=document.AssetForm.txtDate.value;
 var PurYear=document.AssetForm.txtPurchaseYear.value;
var PurMonth=document.AssetForm.txtPurchaseMonth.value;
var a=AsOnDate.split("/");
var month=a[1];

var year=a[2];
if((PurMonth>month) &&(PurYear<year))
{
    alert("Purchase Month should not greater than As-On-Date");
}

}

function nullcheck()
{
if((document.AssetForm.comOffCode.value==null)||(document.AssetForm.comOffCode.value.length==0))
    {
        alert("Null Value not accepted...Select Acconting for Office Code");
        document.AssetForm.comOffCode.focus();
        return false;
    }
 if((document.AssetForm.txtDate.value==null)||(document.AssetForm.txtDate.value.length==0))
    {
        alert("Null Value not accepted...Enter Date");
        document.AssetForm.txtDate.focus();
        return false;
    }
    
 if((document.AssetForm.txtFinYear.value==null)||(document.AssetForm.txtFinYear.value.length==0))
    {
        alert("Null Value not accepted...Enter Financial Year");
        document.AssetForm.txtFinYear.focus();
        return false;
    }
    
 if((document.AssetForm.comClasAss.value==null)||(document.AssetForm.comClasAss.value.length==0))
    {
        alert("Null Value not accepted...Select Classification of Asset");
        document.AssetForm.comClasAss.focus();
        return false;
    }
    
/* 
  */  
 
    
 if((document.AssetForm.txtDesAsset.value==null)||(document.AssetForm.txtDesAsset.value.length==0))
    {
        alert("Null Value not accepted...Enter Description of Asset");
        document.AssetForm.txtDesAsset.focus();
        return false;
    }
    
/* ( start on 30th nov2006)
if((document.AssetForm.txtPurchaseYear.value==null)||(document.AssetForm.txtPurchaseYear.value.length==0))
    {
        alert("Null Value not accepted...Enter Purchase Year");
        document.AssetForm.txtPurchaseYear.focus();
        return false;
    }
    
 if((document.AssetForm.txtPurchaseMonth.value==null)||(document.AssetForm.txtPurchaseMonth.value.length==0))
    {
        alert("Null Value not accepted...Select Purchase Month");
        document.AssetForm.txtPurchaseMonth.focus();
        return false;
    }
    
  //( end 30th nov2006)
  */  
 if((document.AssetForm.txtOrigCost.value==null)||(document.AssetForm.txtOrigCost.value.length==0))
    {
        alert("Null Value not accepted...Enter Original Cost");
        document.AssetForm.txtOrigCost.focus();
        return false;
    }
    
 if((document.AssetForm.txtPercDep.value==null)||(document.AssetForm.txtPercDep.value.length==0))
    {
        alert("Null Value not accepted...Enter Percentage Of Depreciation");
        document.AssetForm.txtPercDep.focus();
        return false;
    }
    
 if((document.AssetForm.txtCurrVal.value==null)||(document.AssetForm.txtCurrVal.value.length==0))
    {
        alert("Null Value not accepted...Enter Current Value");
        document.AssetForm.txtCurrVal.focus();
        return false;
    }
 
  var ClasAss=document.AssetForm.comClasAss.value;
 
         /*  It will be used in next version , that's why it's hidden.
         //( start on 30th nov2006)
         
         if(ClasAss==9 || ClasAss==10 || ClasAss==14)
           {
              document.AssetForm.txtDenName.value=document.AssetForm.txtDenName.value.replace(/\s+/g,' ');  // to avoid entering more than one space
               if((document.AssetForm.comOwner.value==null)||(document.AssetForm.comOwner.value.length==0))
                {
                    alert("Null Value not accepted...Select OwnerShip");
                    document.AssetForm.comOwner.focus();
                    return false;
                } 
                 if((document.AssetForm.txtlocation.value==null)||(document.AssetForm.txtlocation.value.length==0))
                {
                    alert("Null Value not accepted...Enter Location");
                    document.AssetForm.txtlocation.focus();
                    return false;
                }
                if((document.AssetForm.comFuel.value=="")||(document.AssetForm.comFuel.value.length==0))
                {
                    alert("Null Value not accepted...Select Fuel type");
                    document.AssetForm.txtAliasCode.focus();
                    return false;
                }
                if((document.AssetForm.txtDenName.value=="")||(document.AssetForm.txtDenName.value.length==0))
                {
                    alert("Null Value not accepted...Enter Name Of the Agency");
                    document.AssetForm.txtDenName.focus();
                    return false;
                }
          }
          // end ( start on 30th nov2006)
          */
  if(document.AssetForm.txtOrigCost.value<0 || document.AssetForm.txtCurrVal.value<0)
 {
    if(window.confirm("You have entered negative value for \n original/current value of Asset,  Are you sure ?"))   
    {
        //
    }
    else 
       return false;
 }
return true;
}

function load_AssetClassByChild(AssTypeCode,ClasAss)
{
    
        var txtAssTypeCode=AssTypeCode;  //document.getElementById("txtAssTypeCode").value;
        var url="../../../../../AssetServ.view?Command=load_AssetClassification&txtAssTypeCode="+txtAssTypeCode;
        var req=getTransport();
            req.open("GET",url,true); 
            req.onreadystatechange=function()
            {
               load_AssClassByChild(req,ClasAss);
            }   
                    req.send(null);
}

function load_AssClassByChild(req,ClasAss)
{
    if(req.readyState==4)
    {
     if(req.status==200)
     {      
     
              //var baseResponse=req.responseXML.getElementsByTagName("response")[0];
        var baseResponse=req.responseXML.getElementsByTagName("response")[0];
        var tagCommand=baseResponse.getElementsByTagName("command")[0];
        var Command=tagCommand.firstChild.nodeValue; 
        if(Command=="load_AssetClassification")
        {
            
             var AssClassCode=baseResponse.getElementsByTagName("AssClassCode");
              
            var Code=new Array();
            var Desc=new Array();
            var comClasAss=document.getElementById("comClasAss");
            
            for(var k=0;k<AssClassCode.length;k++)
            {
                 Code[k]=baseResponse.getElementsByTagName("AssClassCode")[k].firstChild.nodeValue;
                 Desc[k]=baseResponse.getElementsByTagName("AssClassDesc")[k].firstChild.nodeValue;
            }
            comClasAss.innerHTML="";
          
            var option=document.createElement("OPTION");
            option.text="--Select Asset Class--";
            option.value="";
            try
            {
                comClasAss.add(option);
            }catch(errorObject)
            {
                comClasAss.add(option,null);
            }
            
            for(var k=0;k<AssClassCode.length;k++)
            {   
              var option=document.createElement("OPTION");
              option.text=Desc[k];
              option.value=Code[k];
               try
              {
                  comClasAss.add(option);
              }
              catch(errorObject)
              {
                  comClasAss.add(option,null);
              }
          
           }
           document.AssetForm.comClasAss.value=ClasAss;
       }
            if (listPopupwindow && listPopupwindow.open && !listPopupwindow.closed) listPopupwindow.close();
    }
  }
}

function load_AssetClassification()
{  
    if(document.getElementById("txtAssTypeCode").value!="")
    {
        var txtAssTypeCode=document.getElementById("txtAssTypeCode").value;
        var url="../../../../../AssetServ.view?Command=load_AssetClassification&txtAssTypeCode="+txtAssTypeCode;
        var req=getTransport();
            req.open("GET",url,true); 
            req.onreadystatechange=function()
            {
               load_AssClassification(req);
            }   
                    req.send(null);
    }
    else if(document.getElementById("txtAssTypeCode").value=="")
    {
            var comClasAss=document.getElementById("comClasAss");
            comClasAss.innerHTML="";
            var option=document.createElement("OPTION");
            option.text="--Select Asset Class--";
            option.value="";
            try
            {
                comClasAss.add(option);
            }catch(errorObject)
            {
                comClasAss.add(option,null);
            }
    }
    
    
}
function load_AssClassification(req)
{
    if(req.readyState==4)
    {
     if(req.status==200)
     {             
              //var baseResponse=req.responseXML.getElementsByTagName("response")[0];
        var baseResponse=req.responseXML.getElementsByTagName("response")[0];
        var tagCommand=baseResponse.getElementsByTagName("command")[0];
        var Command=tagCommand.firstChild.nodeValue; 
        
        if(Command=="load_AssetClassification")
        {
            
             var AssClassCode=baseResponse.getElementsByTagName("AssClassCode");
              
            var Code=new Array();
            var Desc=new Array();
            var comClasAss=document.getElementById("comClasAss");
           
            for(var k=0;k<AssClassCode.length;k++)
            {
                 Code[k]=baseResponse.getElementsByTagName("AssClassCode")[k].firstChild.nodeValue;
                 Desc[k]=baseResponse.getElementsByTagName("AssClassDesc")[k].firstChild.nodeValue;
            }
            comClasAss.innerHTML="";
          
            var option=document.createElement("OPTION");
            option.text="--Select Asset Class--";
            option.value="";
            try
            {
                comClasAss.add(option);
            }catch(errorObject)
            {
                comClasAss.add(option,null);
            }
            
            for(var k=0;k<AssClassCode.length;k++)
            {   
              var option=document.createElement("OPTION");
              option.text=Desc[k];
              option.value=Code[k];
               try
              {
                  comClasAss.add(option);
              }
              catch(errorObject)
              {
                  comClasAss.add(option,null);
              }
          
           }
       }
    }
   }
}
function doFunction(command,param)
{
    var Acc_UnitCode=document.AssetForm.cmbAcc_UnitCode.value;
    var acOffId=document.AssetForm.comOffCode.value;
    //var SuppId=document.AssetForm.txtDate.value;
    var FinYear=document.AssetForm.txtFinYear.value;
    var ClasAss=document.AssetForm.comClasAss.value;
    var AssCode=document.AssetForm.txtAssCode.value;
    var AliasCode=document.AssetForm.txtAliasCode.value;
    var Owner=document.AssetForm.comOwner.value;
    var DenName=document.AssetForm.txtDenName.value;
    var DesAsset=document.AssetForm.txtDesAsset.value;
    var PurYear=document.AssetForm.txtPurchaseYear.value;
    var PurMonth=document.AssetForm.txtPurchaseMonth.value;
    var Fuel=document.AssetForm.comFuel.value;
    var PercDep=document.AssetForm.txtPercDep.value;
    var officeLocation=document.AssetForm.txtlocation.value;
    var OrigCost=document.AssetForm.txtOrigCost.value;
    //var Offcode=document.AssetForm.txtPercDep.value;
    var CurrVal=document.AssetForm.txtCurrVal.value;
    var Rem=document.AssetForm.txtRem.value;
    var AsOnDate=document.AssetForm.txtDate.value;
    //var userId=document.AssetForm.txtuserId.value;
    if(AsOnDate.length==0 || FinYear.length==0 )
    {
        alert("Enter the Date and Financial year");
        document.AssetForm.comClasAss.value="";
        return false;
    }
            var status  = "";
		    if(document.AssetForm.txtstatus[0].checked==true)
		    	status="L";
		    else 
		    	status="C";
 
            if(command=="Add")
            {
            var flag=nullcheck();
            
                    if(flag==true)
                       {
                        var url="../../../../../AssetServ.view?Command=Add&txtPercDep="+PercDep+"&cmbAcc_UnitCode="+Acc_UnitCode+"&comOffCode="+acOffId+"&comClasAss="+ClasAss+"&txtAliasCode="+AliasCode+"&comOwner="+Owner+"&txtDenName="+DenName+"&txtDesAsset="+DesAsset+"&txtPurchaseYear="+PurYear+"&txtPurchaseMonth="+PurMonth+"&comFuel="+Fuel+"&txtlocation="+officeLocation+"&txtOrigCost="+OrigCost+"&txtCurrVal="+CurrVal+"&txtRem="+Rem+"&txtFinYear="+FinYear+"&txtDate="+AsOnDate+"&status="+status;

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
            else if(command=="Update")
            {
            var flag=nullcheck();
                    if(flag==true)
                      {
                       if((document.AssetForm.txtAssCode.value==null)||(document.AssetForm.txtAssCode.value.length==0))
                        {
                            alert("Null Value not accepted...Enter Asset code");
                            document.AssetForm.txtAssCode.focus();
                            return false;
                        }
                        var url="../../../../../AssetServ.view?Command=Update&txtPercDep="+PercDep+"&txtAssCode="+AssCode+"&cmbAcc_UnitCode="+Acc_UnitCode+"&comOffCode="+acOffId+"&comClasAss="+ClasAss+"&txtAliasCode="+AliasCode+"&comOwner="+Owner+"&txtDenName="+DenName+"&txtDesAsset="+DesAsset+"&txtPurchaseYear="+PurYear+"&txtPurchaseMonth="+PurMonth+"&comFuel="+Fuel+"&txtlocation="+officeLocation+"&txtOrigCost="+OrigCost+"&txtCurrVal="+CurrVal+"&txtRem="+Rem+"&txtFinYear="+FinYear+"&txtDate="+AsOnDate+"&status="+status;

                       // alert(url);
                        var req=getTransport();
                        req.open("GET",url,true); 
                        req.onreadystatechange=function()
                        {
                           handleResponse(req);
                        }   
                                req.send(null);
                         }
            }
            else if(command=="Cancel")
            {
            //var flag=nullcheck();
            
                    if((document.AssetForm.comOffCode.value==null)||(document.AssetForm.comOffCode.value.length==0))
                        {
                            alert("Null Value not accepted...Select Acconting for Office Code");
                            document.AssetForm.comOffCode.focus();
                            return false;
                        }
                    if((document.AssetForm.txtFinYear.value==null)||(document.AssetForm.txtFinYear.value.length==0))
                        {
                            alert("Null Value not accepted...Enter Financial Year");
                            document.AssetForm.txtFinYear.focus();
                            return false;
                        }
                     if((document.AssetForm.txtAssCode.value==null)||(document.AssetForm.txtAssCode.value.length==0))
                        {
                            alert("Null Value not accepted...Enter Asset code");
                            document.AssetForm.txtAssCode.focus();
                            return false;
                        }
                    //if(flag==true)
                      // {
                        var url="../../../../../AssetServ.view?Command=Cancel&txtAssCode="+AssCode+"&cmbAcc_UnitCode="+Acc_UnitCode+"&comOffCode="+acOffId+"&comClasAss="+ClasAss+"&txtFinYear="+FinYear;
                       // alert(url);
                        var req=getTransport();
                        req.open("GET",url,true); 
                        req.onreadystatechange=function()
                        {
                           handleResponse(req);
                        }   
                                req.send(null);
                        // }
            }
            
            else if(command=="owner")
            {
            var url="../../../../../AssetServ.view?Command=owner&comOffCode="+acOffId;
            //alert(url);
                        var req=getTransport();
                        req.open("GET",url,true); 
                        req.onreadystatechange=function()
                        {
                           handleOutput(req);
                        }   
                                req.send(null);
            
            }
          
            else if(command=="Depreciation_assetType")
            {
                        var url="../../../../../AssetServ.view?Command=Depreciation_assetType&comClasAss="+ClasAss+"&txtFinYear="+FinYear;
                        if(ClasAss!="")
                        {
                                document.AssetForm.comOwner.value="";
                                document.AssetForm.txtDenName.value="";
                                document.AssetForm.comFuel.value="";
                                document.AssetForm.txtlocation.value="";
                                document.getElementById("txtlocationName").value="";
                                
                          /*        to avoid display the of vehicles details-- ( start on 30th nov2006)
                          if(ClasAss==9 || ClasAss==10 || ClasAss==14)
                            {
                                document.getElementById("vehicle").style.display="block";
                            }
                            else
                            {
                                document.getElementById("vehicle").style.display="none";
                             }    
                             //end   30th nov2006)
                          */
                            var req=getTransport();
                            req.open("GET",url,true); 
                            req.onreadystatechange=function()
                            {
                               handleResponse(req);
                            }   
                                    req.send(null);
                        }
            
            }
         else if(command=="office")
         {   
            var oid=param;
            var url="../../../../../AssetServ.view?Command=office&oid="+oid;
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


function handleResponse(req)
    {   
      if(req.readyState==4)
        {
          if(req.status==200)
          {               
              var baseResponse=req.responseXML.getElementsByTagName("response")[0];
              var tagCommand=baseResponse.getElementsByTagName("command")[0];
              var Command=tagCommand.firstChild.nodeValue; 
              
            if(Command=="Add")
            {
                //alert("Hellooooooooooo");
                addRow(baseResponse);
            }
            else if(Command=="Cancel")
            {
                deleteRow(baseResponse);
            }
            
            else if(Command=="Update")
            {
                UpdateRow(baseResponse);
            }
            else if(Command=="Depreciation_assetType")
            {
                DepRow(baseResponse);
            }
            else if(Command=="fetchowner")
            {
                fetchownerRow(baseResponse);
            }
             else if(Command=="office")
            {
                loadOffice(baseResponse);
            }  
        }
    }
}

function loadOffice(baseResponse)
{
    var flag=baseResponse.getElementsByTagName("flag")[0].firstChild.nodeValue;
    
    if(flag=="success")
    {  
        var oid=baseResponse.getElementsByTagName("oid")[0].firstChild.nodeValue;
        var oname=baseResponse.getElementsByTagName("oname")[0].firstChild.nodeValue;
        document.getElementById("txtlocation").value=oid;
        document.getElementById("txtlocationName").value=oname;
    }
     else 
    {
     var oid=baseResponse.getElementsByTagName("oid")[0].firstChild.nodeValue;
     alert("Office Id '"+oid+"' doesn't Exist");
     document.getElementById("txtlocation").value="";
     document.getElementById("txtlocationName").value="";
    }
}

function  handleOutput(req)
{
if(req.readyState==4)
{
  if(req.status==200)
   {
    var i;
   var j;
   var first=document.getElementById("comOwner");
   first.innerHTML="";
   
   var sel=req.responseXML.getElementsByTagName("select")[0];
   
   var options=sel.getElementsByTagName("option");
   var htop=document.createElement("OPTION");
    htop.text="--Select--";
    try
    {
    first.add(htop);
    }
    catch(e)
    {
    first.add(htop,null);
    }
   for(i=0;i<options.length;i++)
   {
   
    var desc=options[i].getElementsByTagName("owner_desc")[0].firstChild.nodeValue;
   var id=options[i].getElementsByTagName("owner_code")[0].firstChild.nodeValue;
   var htoption=document.createElement("OPTION");
   htoption.text=desc;
   htoption.value=id;
   try
   {
    first.add(htoption);
   }
   catch(e)
   {
     first.add(htoption,null);
   }
}

}
}
}

function DepRow(baseResponse)
{
    var flag=baseResponse.getElementsByTagName("flag")[0].firstChild.nodeValue;
   // var flag1=baseResponse.getElementsByTagName("flag1")[0].firstChild.nodeValue;
    if(flag=="success")
    {
        var depRate=baseResponse.getElementsByTagName("dep")[0].firstChild.nodeValue;
        
        document.AssetForm.txtPercDep.value=depRate;
    }
    else
    {
        document.AssetForm.txtPercDep.value="";
        alert("No Depreciation Rate for this Asset");
    }
    
    /*
    if(flag1=="success")
    {
        var AssTypeCode=baseResponse.getElementsByTagName("assType")[0].firstChild.nodeValue;
        var assTypeDesc=baseResponse.getElementsByTagName("assTypeDesc")[0].firstChild.nodeValue;
        document.AssetForm.txtAssTypeCode.value=AssTypeCode;
        document.AssetForm.txtAssType.value=assTypeDesc;
    }
    else
    {
        document.AssetForm.txtAssTypeCode.value="";
        document.AssetForm.txtAssType.value="";
        alert("No Asset Type for this Asset");
    }
    */
}

function fetchownerRow(baseResponse)
{
    var flag=baseResponse.getElementsByTagName("flag")[0].firstChild.nodeValue;
     var owner_code=baseResponse.getElementsByTagName("owner_code")[0].firstChild.nodeValue;
    if(flag=="success")
    {
        document.AssetForm.comOwner.value=owner_code;
        document.AssetForm.txtRem.focus();
        
    }
    else
    {
        alert("Records r not inserted");
    }
}




function addRow(baseResponse)
{
    var flag=baseResponse.getElementsByTagName("flag")[0].firstChild.nodeValue;

     
    if(flag=="success")
    {   
        var strAsscode=baseResponse.getElementsByTagName("strAsscode")[0].firstChild.nodeValue;       
        alert("Note down the Asset Code: "+strAsscode);  
         alert("Records inserted into database");
         clearall();
       
    }
    else
    {
        alert("Records r not inserted");
    }
}
function deleteRow(baseResponse)
{
    var flag=baseResponse.getElementsByTagName("flag")[0].firstChild.nodeValue;
    
    if(flag=="success")
    {
        alert("Records Canceled from database");
        
        
        clearall();        
        
    }
    else
    {
        alert("Records r not Canceled");
    }
}

function UpdateRow(baseResponse)
{
    var flag=baseResponse.getElementsByTagName("flag")[0].firstChild.nodeValue;
    
    
    if(flag=="success")
    {
        
        
        alert("Record Updated");
        
        
       clearall();
        
    }
    else
    {
        alert("Record not Updated");
    }
}











//This is to allow only numbers in control
function numbersonly1(e,t)
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
     
//This Coding for Date Validation and Checking     
function calins(e,t)
{
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
      
       
        var c=t.value;
        try{
            var f=isValidDate(c);
           
            }
//        try{
//        var f=DateFormat(t,c,event,true,'3');
//        }
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

  /*
  
else if(command=="owner1")
{
var url="../../../../../AssetServ.view?Command=owner&comOffCode="+acOffId;
//alert(url);
            var req=getTransport();
            req.open("GET",url,false); 
            req.onreadystatechange=function()
            {
               handleOutput(req);
            }   
                    req.send(null);
                    
                    
var url="../../../../../AssetServ.view?Command=fetchowner&txtAssCode="+AssCode;
//alert(url);
document.AssetForm.comOwner.focus();
            var req=getTransport();
            req.open("GET",url,false); 
            req.onreadystatechange=function()
            {
               handleResponse(req);
            }   
                    req.send(null);

}
  */