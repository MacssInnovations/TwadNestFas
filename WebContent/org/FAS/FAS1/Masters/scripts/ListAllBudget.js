//XMLHttpRequest Object Coding ///

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


function LoadValues()
{
        var cmbAcc_UnitCode=opener.document.frmBudget.cmbAcc_UnitCode.value;
        var cmbOffice_Code=opener.document.frmBudget.cmbOffice_code.value;
        var cmbFinancial_Year=opener.document.frmBudget.cmbFinancialYear.value;
    var url="../../../../../BudgetMasterServlet.con?Command=List&cmbAccounting_Unit_Id="+cmbAcc_UnitCode+"&cmbOffice_Code="+cmbOffice_Code+"&cmbFinancial_Year="+cmbFinancial_Year;
    var req=getTransport();
    req.open("GET",url,true); 
   // alert(url)
    req.onreadystatechange=function()
        {
           LoadValuesResponse(req);
        }   
         req.send(null);

}

function LoadValuesResponse(req)
{

    if(req.readyState==4)
    {
       if(req.status==200)
       {    
            /*var tbody=document.getElementById("Existing");
            var t=0;
            for(t=tbody.rows.length-1;t>=0;t--)
            {
                tbody.deleteRow(0);
            }*/
            
            
              var baseResponse=req.responseXML.getElementsByTagName("response")[0];
              var tagCommand=baseResponse.getElementsByTagName("command")[0];
              var Command=tagCommand.firstChild.nodeValue; 
                    
            if(Command=="List")
            {
                var flag=baseResponse.getElementsByTagName("flag")[0].firstChild.nodeValue;
                var options=baseResponse.getElementsByTagName("accountunitid");
                
               
                for(var i=0;i<options.length;i++)
                {
            
                    var txtaccountunitid=baseResponse.getElementsByTagName("accountunitid")[i].firstChild.nodeValue;
                    var txtofficeid=baseResponse.getElementsByTagName("officecode")[i].firstChild.nodeValue;
                    var financialyear=baseResponse.getElementsByTagName("financialyear")[i].firstChild.nodeValue;
                    var accountheadcode=baseResponse.getElementsByTagName("accountheadcode")[i].firstChild.nodeValue;
                    var previousyear=baseResponse.getElementsByTagName("previousyear")[i].firstChild.nodeValue;
                    var previousyearrevised=baseResponse.getElementsByTagName("previousyearrevised")[i].firstChild.nodeValue;
                    var currentyearbudget=baseResponse.getElementsByTagName("currentyearbudget")[i].firstChild.nodeValue;
                    var currentyearrevised=baseResponse.getElementsByTagName("currentyearrevised")[i].firstChild.nodeValue;
                    var nextyear=baseResponse.getElementsByTagName("nextyear")[i].firstChild.nodeValue;
                    var referno="";
                    try
                    {referno=baseResponse.getElementsByTagName("referno")[i].firstChild.nodeValue;}catch(e){}
                    
                    var referdate="";
                    try{baseResponse.getElementsByTagName("referdate")[i].firstChild.nodeValue;}catch(e){}
                    var remarks=baseResponse.getElementsByTagName("remarks")[i].firstChild.nodeValue;
                    var accountunitname=baseResponse.getElementsByTagName("accountunitname")[i].firstChild.nodeValue;
                  //  var accountheaddesc=baseResponse.getElementsByTagName("accountheaddesc")[i].firstChild.nodeValue;
                    var budgetalloted=baseResponse.getElementsByTagName("budgetalloted")[i].firstChild.nodeValue;
                    var budgetspent=baseResponse.getElementsByTagName("budgetspent")[i].firstChild.nodeValue;
                    var allotype=baseResponse.getElementsByTagName("allotype")[i].firstChild.nodeValue;
                    
                  
                    var tbody=document.getElementById("tblList");
                    var table=document.getElementById("Existing");
                    var mycurrent_row=document.createElement("TR");
                    
                    referdate=baseResponse.getElementsByTagName("referdate")[i].firstChild.nodeValue;
                    var previousyear1="";
                    var currentyearrevised1="";
                    var currentyearbudget1="";
                    var nextyear1="";
                    var referno1="";
                    var referdate1="";
                    var remarks1="";
                    if(txtaccountunitid!=0)
                    {
                        txtaccountunitid=txtaccountunitid;
                        
                    }
                    if(txtofficeid!=0)
                    {
                        txtofficeid=txtofficeid;
                    }
                    if(financialyear!="null")
                    {
                        financialyear=financialyear;
                        
                    }
                    if(accountheadcode!="null")
                    {
                        accountheadcode=accountheadcode;
                    }
                    if(previousyear!="null")
                    {
                        if(previousyear!="0")
                        {
                        previousyear1=previousyear;
                        }
                    }
                    if(currentyearbudget!="null")
                    {
                        if(currentyearbudget!="0")
                        {
                        currentyearbudget1=currentyearbudget;
                        }
                        
                    }
                    if(currentyearrevised!="null")
                    {
                        if(currentyearrevised!="0")
                        {
                        currentyearrevised1=currentyearrevised;
                        }
                        
                    }
                    if(nextyear!="null")
                    {
                        if(nextyear!="0")
                        {
                        nextyear1=nextyear;
                        }
                    }
                    if(referno!="null")
                    {
                        referno1=referno;
                    }
                    
                    if(referdate!="Not Specified")
                    {
                        if(referdate!="")
                        {
                            
                        referdate1=referdate;
                        }
                        else
                        {
                        referdate1=referdate;
                        }
                
                    }
                    
                    if(remarks!="null")
                    {
                        remarks1=remarks;
                    }
                    if(accountunitname!="null")
                    {
                        accountunitname=accountunitname;
                    }
                   /* if(accountheaddesc!="null")
                    {
                        accountheaddesc=accountheaddesc;
                    }*/
                    mycurrent_row.id=i;
                    var cell1=document.createElement("TD");
                    var cell2=document.createElement("TD");
                    var cell3=document.createElement("TD");
                    var cell4=document.createElement("TD");
                    var cell5=document.createElement("TD");
                    var cell6=document.createElement("TD");
                    var cell7=document.createElement("TD");
                    var cell8=document.createElement("TD");
                    var cell9=document.createElement("TD");
                    var cell10=document.createElement("TD");
                    var cell11=document.createElement("TD");
                    var cell12=document.createElement("TD");
                    var cell13=document.createElement("TD");
                    var cell14=document.createElement("TD");
                    var cell15=document.createElement("TD");
                    
                    
                    var anc=document.createElement("A");       
                                    
                    var url="javascript:loadValuesFromTable('" + i + "')";              
                    anc.href=url;
                    var txtedit=document.createTextNode("Edit");
                    anc.appendChild(txtedit);
                    cell1.appendChild(anc);
                    var hidden1=document.createElement("input");
                    hidden1.type="hidden";
                    hidden1.name=i;
                    hidden1.value=i;
                    cell1.appendChild(hidden1);
                                        
                    mycurrent_row.appendChild(cell1);
                    
                    var accountunitid=document.createTextNode(accountunitname);
                    cell2.appendChild(accountunitid);
                    var hidden2=document.createElement("input");
                    hidden2.type="hidden";
                    hidden2.name="txtaccountunitid";
                    hidden2.value=txtaccountunitid;
                    cell2.appendChild(hidden2);

                    mycurrent_row.appendChild(cell2);
                    
                    var officeid=document.createTextNode(txtofficeid);
                    cell3.appendChild(officeid);
                    var hidden3=document.createElement("input");
                    hidden3.type="hidden";
                    hidden3.name="txtofficeid";
                    hidden3.value=txtofficeid;
                    cell3.appendChild(hidden3);
                    mycurrent_row.appendChild(cell3);
                    
                    
                    
                    
                    var txtfinancialyear=document.createTextNode(financialyear);
                    
                    cell4.appendChild(txtfinancialyear);
                    var hidden4=document.createElement("input");
                    hidden4.type="hidden";
                    hidden4.name="financialyear";
                    hidden4.value=financialyear;
                    cell4.appendChild(hidden4);
                    mycurrent_row.appendChild(cell4);
                    
                    
               //     var txtaccountheadcode=document.createTextNode(accountheaddesc+"("+accountheadcode+")");
                    var txtaccountheadcode=document.createTextNode(accountheadcode);
                    cell5.appendChild(txtaccountheadcode);
                    
                    var hidden5=document.createElement("input");
                    hidden5.type="hidden";
                    hidden5.name="accountheadcode";
                    hidden5.value=accountheadcode;
                    //alert(hidden6);
                    cell5.appendChild(hidden5);
                    mycurrent_row.appendChild(cell5);
                    
                    
                    
                    var txtpreviousyear=document.createTextNode(previousyear1);
                    cell6.appendChild(txtpreviousyear);
                    var hidden6=document.createElement("input");
                    hidden6.type="hidden";
                    hidden6.name="previousyear";
                    hidden6.value=previousyear1;
                    cell6.appendChild(hidden6);
                    mycurrent_row.appendChild(cell6);
                    
                    
                    var txtcurrentyearbudget=document.createTextNode(currentyearbudget1);
                    cell7.appendChild(txtcurrentyearbudget);
                    var hidden7=document.createElement("input");
                    hidden7.type="hidden";
                    hidden7.name="currentyearbudget";
                    hidden7.value=currentyearbudget1;
                    cell7.appendChild(hidden7);
                    mycurrent_row.appendChild(cell7);
                   
                    var txtcurrentyearrevised=document.createTextNode(currentyearrevised1);
                    cell8.appendChild(txtcurrentyearrevised);
                    var hidden8=document.createElement("input");
                    hidden8.type="hidden";
                    hidden8.name="currentyearrevised";
                    hidden8.value=currentyearrevised1;
                    cell8.appendChild(hidden8);
                    mycurrent_row.appendChild(cell8);
                    
                    var txtnextyear=document.createTextNode(nextyear1);
                    cell9.appendChild(txtnextyear);
                    var hidden9=document.createElement("input");
                    hidden9.type="hidden";
                    hidden9.name="nextyear";
                    hidden9.value=nextyear1;
                    cell9.appendChild(hidden9);
                    mycurrent_row.appendChild(cell9); 
                    
                    var txtreferno=document.createTextNode(referno1);
                    cell10.appendChild(txtreferno);
                    var hidden10=document.createElement("input");
                    hidden10.type="hidden";
                    hidden10.name="referno1";
                    hidden10.value=referno;
                    cell10.appendChild(hidden10);
                    mycurrent_row.appendChild(cell10); 
                    
                    var txtreferdate=document.createTextNode(referdate1);
                    cell11.appendChild(txtreferdate);
                    var hidden11=document.createElement("input");
                    hidden11.type="hidden";
                    hidden11.name="referdate1";
                    hidden11.value=referdate;
                    cell11.appendChild(hidden11);
                    mycurrent_row.appendChild(cell11); 
                    
                    var txtremarks=document.createTextNode(remarks1);
                    cell12.appendChild(txtremarks);
                    var hidden12=document.createElement("input");
                    hidden12.type="hidden";
                    hidden12.name="remarks";
                    hidden12.value=remarks1;
                    cell12.appendChild(hidden12);
                    mycurrent_row.appendChild(cell12); 
                    
                    var txtbudgetalloted=document.createTextNode(budgetalloted);
                    cell13.appendChild(txtbudgetalloted);
                    var hidden13=document.createElement("input");
                    hidden13.type="hidden";
                    hidden13.name="budgetalloted";
                    hidden13.value=budgetalloted;
                    cell13.appendChild(hidden13);
                    mycurrent_row.appendChild(cell13); 
                    
                    var txtbudgetspent=document.createTextNode(budgetspent);
                    cell14.appendChild(txtbudgetspent);
                    var hidden14=document.createElement("input");
                    hidden14.type="hidden";
                    hidden14.name="budgetspent";
                    hidden14.value=budgetspent;
                    cell14.appendChild(hidden14);
                    mycurrent_row.appendChild(cell14); 
                    
                    
                    var allotype1=document.createTextNode(allotype);
                    cell15.appendChild(allotype1);
                    var hidden15=document.createElement("input");
                    hidden15.type="hidden";
                    hidden15.name="allotype";
                    hidden15.value=allotype;
                    cell15.appendChild(hidden15);
                    mycurrent_row.appendChild(cell15); 
                       
                    
                    tbody.appendChild(mycurrent_row);
                
                }
            }
        }
    }
}


function loadValuesFromTable(id)
{
    
    Minimize();
    var r=document.getElementById(id); 
    var rcells=r.cells;
    var accountingunitid=rcells.item(1).lastChild.value;
    var accountofficecode=rcells.item(2).lastChild.value;
    var financialyear=rcells.item(3).lastChild.value;
    var headcode=rcells.item(4).lastChild.value;
    var previousyear=rcells.item(5).lastChild.value;
    var currentyearbudget=rcells.item(6).lastChild.value;
    var currentyearrevised=rcells.item(7).lastChild.value;
    var nextyear=rcells.item(8).lastChild.value;
    var referno=rcells.item(9).lastChild.value;
    var referdate=rcells.item(10).lastChild.value;
    var remarks=rcells.item(11).lastChild.value;
    var budgetalloted=rcells.item(12).lastChild.value;
    var budgetspent=rcells.item(13).lastChild.value;
    var allotype=rcells.item(14).lastChild.value;
    
  //  alert(headcode);
    /*alert(rcells.item(1).lastChild.value);
    alert(rcells.item(2).lastChild.value);
    alert(rcells.item(3).lastChild.value);
    alert(rcells.item(4).lastChild.value);
    alert(rcells.item(5).lastChild.value);
    alert(rcells.item(6).lastChild.value);
    alert(rcells.item(7).lastChild.value);
    alert(rcells.item(8).lastChild.value);
    alert(rcells.item(9).lastChild.value);
    alert(rcells.item(10).lastChild.value);
    alert(rcells.item(11).lastChild.value);*/
    opener.List(accountingunitid,accountofficecode,financialyear,headcode,previousyear,currentyearbudget,currentyearrevised,nextyear,referno,referdate,remarks,budgetalloted,budgetspent,allotype);
    self.close();
}

function Minimize() 
{
window.resizeTo(0,0);
window.screenX = screen.width;
window.screenY = screen.height;
opener.window.focus();
}