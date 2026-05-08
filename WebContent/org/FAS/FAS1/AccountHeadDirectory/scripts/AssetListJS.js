var global_assetclasscode;
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
function loadfin_year()
{
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
   
   document.AssetList.txtFinYearvalue.value=yr1+"-"+yr2;      // loading finance year
}
function doFunction(command,param)
{
       
        var doc=window.opener.document;

        var cmbAcc_UnitCode=doc.AssetForm.cmbAcc_UnitCode.value;
        var cmbOffice_code=doc.AssetForm.comOffCode.value;
        var txtFinYear=document.AssetList.txtFinYearvalue.value;
        if(txtFinYear.length!=9 || txtFinYear.length==0)
        {
            alert("Enter the Valid Financial year");
            document.AssetList.comClasAss.value="";
            document.AssetList.comOwnerShip.value="";
            document.AssetList.txtFinYearvalue.focus();
            return false;
        }
        if(command=="comClasAss")
        {
           var ClasAss=document.AssetList.comClasAss.value;
          if(ClasAss!="")
          {
                if(ClasAss=="All")
                {
                               var url="../../../../../AssetListServ.view?command=All&cmbAcc_UnitCode="+cmbAcc_UnitCode+"&cmbOffice_code="+cmbOffice_code+"&txtFinYear="+txtFinYear;
                                var req=getTransport();
                                //alert(url);
                                req.open("GET",url,true); 
                                req.onreadystatechange=function()
                                    {
                                        handleResponse(req);
                                    }   
                                     req.send(null);
                }
                else
                {
                
                               var url="../../../../../AssetListServ.view?command=AssetClass&comClasAss="+ClasAss+"&cmbAcc_UnitCode="+cmbAcc_UnitCode+"&cmbOffice_code="+cmbOffice_code+"&txtFinYear="+txtFinYear;
                                var req=getTransport();
                                //alert(url);
                                req.open("GET",url,true); 
                                req.onreadystatechange=function()
                                    {
                                        handleResponse(req);
                                    }   
                                     req.send(null);
                }
          }
        }
        else if(command=="comOwnerShip")
        {
        var OwnerShip=document.AssetList.comOwnerShip.value;
         if(OwnerShip!="")
          {
            if(OwnerShip=="All")
            {
            var url="../../../../../AssetListServ.view?command=AllOwnerShip&cmbAcc_UnitCode="+cmbAcc_UnitCode+"&cmbOffice_code="+cmbOffice_code+"&txtFinYear="+txtFinYear;
                       //alert(url);
                            var req=getTransport();
                            //alert(url);
                            req.open("GET",url,true); 
                            req.onreadystatechange=function()
                                {
                                    handleResponse(req);
                                }   
                                 req.send(null);
            }
            else
            {
            var url="../../../../../AssetListServ.view?command=ownership&comOwnerShip="+OwnerShip+"&cmbAcc_UnitCode="+cmbAcc_UnitCode+"&cmbOffice_code="+cmbOffice_code+"&txtFinYear="+txtFinYear;
                       //alert(url);
                            var req=getTransport();
                           // alert(url);
                            req.open("GET",url,true); 
                            req.onreadystatechange=function()
                                {
                                    handleResponse(req);
                                }   
                                 req.send(null);
            }
          }
        }

}

function loadTabAll(cmd)
{
   // var acID=document.AssetList.txtacID.value;
   
    var doc=window.opener.document;

        var cmbAcc_UnitCode=doc.AssetForm.cmbAcc_UnitCode.value;
        var cmbOffice_code=doc.AssetForm.comOffCode.value;
        var txtFinYear=document.AssetList.txtFinYearvalue.value;
        var url="../../../../../AssetListServ.view?command=fetch&assetcode="+cmd+"&cmbAcc_UnitCode="+cmbAcc_UnitCode+"&cmbOffice_code="+cmbOffice_code+"&txtFinYear="+txtFinYear;
           // alert(url);
                var req=getTransport();
                req.open("GET",url,true); 
                req.onreadystatechange=function()
                    {
                        handleResponse(req);
                    }   
                     req.send(null);
}



function handleResponse(req)
    {   
      if(req.readyState==4)
        {
          if(req.status==200)
          {               
              var baseResponse=req.responseXML.getElementsByTagName("response")[0];
              //alert(baseResponse);
              var tagCommand=baseResponse.getElementsByTagName("command")[0];
              var Command=tagCommand.firstChild.nodeValue; 
            
            if(Command=="fetch")
            {
                listRow(baseResponse);
            }
            else if(Command=="AssetClass")
            {
                AssetClassRow(baseResponse);
            }
            
            
}
}
}
function AssetClassRow(baseResponse)
{
var flag=baseResponse.getElementsByTagName("flag")[0].firstChild.nodeValue;
    if(flag=="success")
    {
   
    /////////////////for text box display////////////////////////
 /*  
    var AssetCode=baseResponse.getElementsByTagName("AssetCode")[0].firstChild.nodeValue;
    var month=baseResponse.getElementsByTagName("month")[0].firstChild.nodeValue;
    var year=baseResponse.getElementsByTagName("year")[0].firstChild.nodeValue;
    var Location=baseResponse.getElementsByTagName("Location")[0].firstChild.nodeValue;
    
    var Original_cost=baseResponse.getElementsByTagName("Original_cost")[0].firstChild.nodeValue;
    var curr_val=baseResponse.getElementsByTagName("curr_val")[0].firstChild.nodeValue;
   */ 
     
////////////////////////////
    
   var tbody=document.getElementById("tb");
                        var t=0;
                        
                        for(t=tbody.rows.length-1;t>=0;t--)
                        {
                           tbody.deleteRow(0);
                        }
    
    
        var j=0;
        var AssetCode=baseResponse.getElementsByTagName("AssetCode");
        
        var month=baseResponse.getElementsByTagName("month");
        var year=baseResponse.getElementsByTagName("year");
        var Location=baseResponse.getElementsByTagName("Location");
        var Original_cost=baseResponse.getElementsByTagName("Original_cost");
        var curr_val=baseResponse.getElementsByTagName("curr_val");
        var status =baseResponse.getElementsByTagName("status");
       
        var len=AssetCode.length;
     for(j=0;j<len;j++)
     {
        var tbody=document.getElementById("tb");
        
         var AssetCode=baseResponse.getElementsByTagName("AssetCode");
         var Assetdesc=baseResponse.getElementsByTagName("asset_desc");
        var month=baseResponse.getElementsByTagName("month");
        var year=baseResponse.getElementsByTagName("year");
        var Location=baseResponse.getElementsByTagName("Location");
        var Original_cost=baseResponse.getElementsByTagName("Original_cost");
        var curr_val=baseResponse.getElementsByTagName("curr_val");
        var status =baseResponse.getElementsByTagName("status");
      
        var AssetCode1=AssetCode.item(j).firstChild.nodeValue;
        var Assetdesc1=Assetdesc.item(j).firstChild.nodeValue;
        var month1=month.item(j).firstChild.nodeValue;
        var year1=year.item(j).firstChild.nodeValue;
        var Location1=Location.item(j).firstChild.nodeValue;
        var Original_cost1=Original_cost.item(j).firstChild.nodeValue;
        var curr_val1=curr_val.item(j).firstChild.nodeValue;
        var status1=status.item(j).firstChild.nodeValue;
        AssetId=AssetCode1;
        
         var items=new Array();
        
        
        items[0]=AssetCode1;
        items[1]=Assetdesc1;
        items[2]=month1;
        items[3]=year1;
        if(Location1=="null")
        items[4]="";
        else
        items[4]=Location1;
        
        items[5]=Original_cost1;
        items[6]=curr_val1;
        items[7]=status1
       
       
       
       
        var tbody=document.getElementById("tb");
                    
                     var mycurrent_row=document.createElement("TR");
                     mycurrent_row.id=AssetId;
                     var cell=document.createElement("TD");
                     
                     var anc=document.createElement("A");       
                     var url="javascript:loadTabAll('" + AssetId + "')";              
                     anc.href=url;
                     var txtedit=document.createTextNode("Edit");
                     anc.appendChild(txtedit);
                     cell.appendChild(anc);
                     mycurrent_row.appendChild(cell);
                     
                     var cell1=document.createElement("TD");  
                     var AssetCode=document.createTextNode(items[0]);
                     cell1.appendChild(AssetCode);
                     mycurrent_row.appendChild(cell1);
                     
                     var cell1=document.createElement("TD");  
                     var Assetdesc=document.createTextNode(items[1]);
                     cell1.appendChild(Assetdesc);
                     mycurrent_row.appendChild(cell1);
        
                    var cell2=document.createElement("TD");  
                     var Monthyr=document.createTextNode(items[2]+ " / " +items[3]);
                     cell2.appendChild(Monthyr);
                     mycurrent_row.appendChild(cell2);
                     
                  /*
                  //Location will bge added later  ( start on 30th nov2006)
                    
                     var cell3=document.createElement("TD");  
                     var Loc=document.createTextNode(items[3]);
                     cell3.appendChild(Loc);
                     mycurrent_row.appendChild(cell3);
                    
                    */
                     
                     
                     var cell4=document.createElement("TD");  
                     var OriCost=document.createTextNode(items[5]);
                     cell4.appendChild(OriCost);
                     mycurrent_row.appendChild(cell4);
                     
                     var cell5=document.createElement("TD");  
                     var CurrVal=document.createTextNode(items[6]);
                     cell5.appendChild(CurrVal);
                     mycurrent_row.appendChild(cell5);
        
                     var cell6=document.createElement("TD");  
                     var status=document.createTextNode(items[7]);
                     cell6.appendChild(status);
                     mycurrent_row.appendChild(cell6);
        
        
              
        tbody.appendChild(mycurrent_row);
        }
       
    }
    else
    {
     
     
        var tbody=document.getElementById("tb");
                        var t=0;
                        
                        for(t=tbody.rows.length-1;t>=0;t--)
                        {
                           tbody.deleteRow(0);
                        }
        alert("Records not found");
       
    }
    
}

function listRow(baseResponse)
{
    var flag=baseResponse.getElementsByTagName("flag")[0].firstChild.nodeValue;
   
    if(flag=="success")
    {
        alert("fetch");
        var j=0;
        var AssTypeCode=baseResponse.getElementsByTagName("AssTypeCode")[0].firstChild.nodeValue;
        var AssTypedesc=baseResponse.getElementsByTagName("AssTypedesc")[0].firstChild.nodeValue;
        var Acc_UnitCode=baseResponse.getElementsByTagName("Acc_UnitCode")[0].firstChild.nodeValue;
        var FinYear=baseResponse.getElementsByTagName("FinYear")[0].firstChild.nodeValue;
        var ClasAss=baseResponse.getElementsByTagName("ClasAss")[0].firstChild.nodeValue;
        var AssCode=baseResponse.getElementsByTagName("AssCode")[0].firstChild.nodeValue;
        var AliasCode=baseResponse.getElementsByTagName("AliasCode")[0].firstChild.nodeValue;
        var Owner=baseResponse.getElementsByTagName("Owner")[0].firstChild.nodeValue;
        var DenName=baseResponse.getElementsByTagName("DenName")[0].firstChild.nodeValue;
        var acOffId=baseResponse.getElementsByTagName("acOffId")[0].firstChild.nodeValue;
        
        var DesAsset=baseResponse.getElementsByTagName("DesAsset")[0].firstChild.nodeValue;
        var PurYear=baseResponse.getElementsByTagName("PurYear")[0].firstChild.nodeValue;
        var PurMonth=baseResponse.getElementsByTagName("PurMonth")[0].firstChild.nodeValue;
        var Fuel=baseResponse.getElementsByTagName("Fuel")[0].firstChild.nodeValue;
        
        var Office=baseResponse.getElementsByTagName("Office")[0].firstChild.nodeValue;
        var OrigCost=baseResponse.getElementsByTagName("OrigCost")[0].firstChild.nodeValue;
        var CurrVal=baseResponse.getElementsByTagName("CurrVal")[0].firstChild.nodeValue;
        var Rem=baseResponse.getElementsByTagName("Rem")[0].firstChild.nodeValue;
        
        var DepRate=baseResponse.getElementsByTagName("DepRate")[0].firstChild.nodeValue;
        var AsOndate=baseResponse.getElementsByTagName("AsOndate")[0].firstChild.nodeValue;
        var status =baseResponse.getElementsByTagName("status")[0].firstChild.nodeValue;
       // alert(status);
      if(AliasCode=="null")
      AliasCode="";
      
      if(Owner=="null")
      Owner="";
    
      var doc=window.opener.document;
      
        doc.AssetForm.comOwner.value="";
        doc.AssetForm.txtDenName.value="";
        doc.AssetForm.comFuel.value="";
        doc.AssetForm.txtlocation.value="";
        if(status=="L")
			   doc.AssetForm.txtstatus[0].checked=true;
			  else
		           doc.AssetForm.txtstatus[1].checked=true;
    
    /*      ( start on 30th nov2006)
      if(ClasAss==9 || ClasAss==10 || ClasAss==14)
      {
        //doc.AssetForm.vehicle.style.display="block";
        doc.getElementById("vehicle").style.display="block";
      }
      else
       { //doc.AssetForm.vehicle.style.display="none";
         doc.getElementById("vehicle").style.display="none";
       }
       */
      if(Office!=0)
       {
        doc.AssetForm.txtlocation.value=Office;
        doc.AssetForm.txtlocationName.value=baseResponse.getElementsByTagName("oname")[0].firstChild.nodeValue;
        
       }
     else
       {
        doc.AssetForm.txtlocation.value="";
        doc.AssetForm.txtlocationName.value="";
       }
       
       doc.AssetForm.txtDate.value=AsOndate;
       doc.AssetForm.txtFinYear.value=FinYear;
       doc.AssetForm.txtDate.readOnly=true;            // make read only
       doc.AssetForm.txtFinYear.readOnly=true;              // make read only
       doc.getElementById("calenderCTRL").style.visibility="hidden";
       
        doc.AssetForm.txtAssTypeCode.value=AssTypeCode;            
      //  doc.AssetForm.txtAssType.value=AssTypedesc;            
      
       
    //       alert("here");

  //     alert("from here");
       //global_assetclasscode=ClasAss;
       //doc.AssetForm.comClasAss.value=ClasAss;
       
       doc.AssetForm.txtAssCode.value=AssCode;
       
       doc.AssetForm.txtAliasCode.value=AliasCode;
       
       doc.AssetForm.comOwner.value=Owner;
       if(DenName=="null")
            doc.AssetForm.txtDenName.value="";
        else
            doc.AssetForm.txtDenName.value=DenName;
       
       doc.AssetForm.txtDesAsset.value=DesAsset;
       
       if(PurYear!=0)
       doc.AssetForm.txtPurchaseYear.value=PurYear;
       else
       doc.AssetForm.txtPurchaseYear.value="";
       
       doc.AssetForm.txtPurchaseMonth.selectedIndex=PurMonth;
       
       if(Fuel=="null")
           doc.AssetForm.comFuel.value="";
      else
           doc.AssetForm.comFuel.value=Fuel; 
           
       doc.AssetForm.txtOrigCost.value=OrigCost;
       doc.AssetForm.txtCurrVal.value=CurrVal;
       
       if(Rem=="null")
            doc.AssetForm.txtRem.value="";
       else
       doc.AssetForm.txtRem.value=Rem;
       
       doc.AssetForm.txtPercDep.value=DepRate;
       doc.AssetForm.txtstatus.value=status;
       var d=doc.getElementById("cmdAdd");
        d.style.display="none";
    
        var d1=doc.getElementById("cmdUpdate");
        d1.style.display="block";
    
        var d2=doc.getElementById("cmdDelete");
        d2.style.display="block";
    
              opener.load_AssetClassByChild(AssTypeCode,ClasAss);      // very immportant************** it calls the function in parent javascript
    
       //self.close();
    }
    else
    {
        alert("Records not found");
    }
    
    
}


/*
function load_AssetClassification(AssTypeCode)
{  
        var txtAssTypeCode=AssTypeCode;  //document.getElementById("txtAssTypeCode").value;
        var url="../../../../../AssetServ.view?Command=load_AssetClassification&txtAssTypeCode="+txtAssTypeCode;
        var req=getTransport();
            req.open("GET",url,true); 
            req.onreadystatechange=function()
            {
               load_AssClassification(req);
            }   
                    req.send(null);
  
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
       
         var doc=window.opener.document;                    // very immportant**************
     
        if(Command=="load_AssetClassification")
        {
            
             var AssClassCode=baseResponse.getElementsByTagName("AssClassCode");
              
            var Code=new Array();
            var Desc=new Array();
            var comClasAss=doc.getElementById("comClasAss");
            alert(comClasAss);
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
           doc.AssetForm.comClasAss.value=global_assetclasscode;
           global_assetclasscode="";
       }
    }
   }
}
*/

//This Coding for financial year checking     
function calins(e,t)
{
    var unicode=e.charCode? e.charCode : e.keyCode;
        //alert(unicode);
        //if(unicode !=8)
        
        if (unicode!=8 && unicode !=9 && unicode!=37 && unicode !=39 && unicode !=35 )
        {
            if(t.value.length==2 || t.value.length==5)
                t.value=t.value;
             if (unicode<48||unicode>57 ) 
                return false 
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
/*function  handleOutput(req)
{
if(req.readyState==4)
{
  if(req.status==200)
   {
  var i;
   var j;
   var first=doc.AssetForm.getElementById("comOwner");
   
   first.innerHTML="";
   
   var sel=req.responseXML.getElementsByTagName("select")[0];
   
   var options=sel.getElementsByTagName("option");
   var htop=doc.AssetForm.createElement("OPTION");
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
   var htoption=doc.AssetForm.createElement("OPTION");
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
}*/
