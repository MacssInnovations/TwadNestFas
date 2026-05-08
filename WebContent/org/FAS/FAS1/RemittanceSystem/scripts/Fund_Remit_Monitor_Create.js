
var seq=0;
/////////////////////////////////////////////   XML req  /////////////////////////////////////////////////////
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


function byUnitAndOfficeChange()
{
    doFunction('loadPendingRemittance','null');
}

var loadRemit_ReceiptDetails;
function Show(unitcode,offid,challanNo,challanDate)
{
    if (loadRemit_ReceiptDetails && loadRemit_ReceiptDetails.open && !loadRemit_ReceiptDetails.closed) 
    {
       loadRemit_ReceiptDetails.resizeTo(500,500);
       loadRemit_ReceiptDetails.moveTo(250,250); 
       loadRemit_ReceiptDetails.focus();
    }
    else
    {
        loadRemit_ReceiptDetails=null
    }
    loadRemit_ReceiptDetails= window.open("../../../../../org/FAS/FAS1/RemittanceSystem/jsps/RemitMonitor_ReceiptDetails_ForFund.jsp?cmbAcc_UnitCode="+unitcode+"&cmbOffice_code="+offid+"&challanNo="+challanNo+"&challanDate="+challanDate,"ReceiptDetails","status=1,height=500,width=500,resizable=YES, scrollbars=yes"); 
    loadRemit_ReceiptDetails.moveTo(250,250);  
    loadRemit_ReceiptDetails.focus();
    
}

window.onunload=function()
{
if (loadRemit_ReceiptDetails && loadRemit_ReceiptDetails.open && !loadRemit_ReceiptDetails.closed) loadRemit_ReceiptDetails.close();
}
////////////////////////////////////////

function doFunction(Command,param)
{   
 
      if(Command=="loadPendingRemittance")
        {  
            var cmbAcc_UnitCode=document.getElementById("cmbAcc_UnitCode").value;
            var cmbOffice_code=document.getElementById("cmbOffice_code").value;
            var txtCrea_date= document.getElementById("txtCrea_date").value;
            var url="../../../../../Fund_Remit_Monitor_Create.view?Command=loadPendingRemittance&cmbAcc_UnitCode="+cmbAcc_UnitCode
                    +"&cmbOffice_code="+cmbOffice_code+"&txtCrea_date="+txtCrea_date;
            if(txtCrea_date.length!=0)
            {
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

//////////////////////////

/////////////////////////////////////////////   handleResponse()  /////////////////////////////////////////////////////
function handleResponse(req)
{  
    if(req.readyState==4)
    {
        if(req.status==200)
        {   
            var baseResponse=req.responseXML.getElementsByTagName("response")[0];
            var tagcommand=baseResponse.getElementsByTagName("command")[0];
            var Command=tagcommand.firstChild.nodeValue;
             //alert(Command)
            if(Command=="loadPendingRemittance")
            {
                PendingRemittance(baseResponse);
            }
        }
    }
}
////////////////////////////////////////

function PendingRemittance(baseResponse)
{
  var flag=baseResponse.getElementsByTagName("flag")[0].firstChild.nodeValue;
     if(flag=="success")
     {
        var tbody=document.getElementById("grid_body");
        var t=0;
        for(t=tbody.rows.length-1;t>=0;t--)
        {
           tbody.deleteRow(0);
        }
        
        
          var Challan=baseResponse.getElementsByTagName("Challan_no");
        
        var items=new Array();
        var Ucode=document.getElementById("cmbAcc_UnitCode").value;
        var Offid=document.getElementById("cmbOffice_code").value;
        for(var k=0;k<Challan.length;k++)
        {
             var remarks="";
             items[0]=baseResponse.getElementsByTagName("Challan_no")[k].firstChild.nodeValue;   
             items[1]=baseResponse.getElementsByTagName("challan_date")[k].firstChild.nodeValue;   //******* emitted on is filled with challan date, bcoz both are same
              items[2]=baseResponse.getElementsByTagName("Amount")[k].firstChild.nodeValue;
    //         items[3]=baseResponse.getElementsByTagName("remarks")[k].firstChild.nodeValue;
          remarks=".";
            
             if( items[3]=="null")
              items[3]="";
             tbody=document.getElementById("grid_body");
             
                var mycurrent_row=document.createElement("TR");
                
                var cell2;
                
                    cell2=document.createElement("TD");
        
                    //alert("hello "+i+"  " + items[i])
                          var Challan_no=document.createElement("input");
                          Challan_no.type="hidden";
                          Challan_no.name="Challan_no";
                          Challan_no.value=items[0];
                          cell2.appendChild(Challan_no);
                          var currentText=document.createTextNode(items[0]);
                          cell2.appendChild(currentText);
                           mycurrent_row.appendChild(cell2);
                           
                     cell2=document.createElement("TD");
                          var challan_date=document.createElement("input");
                          challan_date.type="hidden";
                          challan_date.name="challan_date";
                          challan_date.value=items[1];
                          cell2.appendChild(challan_date); 
                           var currentText=document.createTextNode(items[1]);
                          cell2.appendChild(currentText);
                        mycurrent_row.appendChild(cell2);
                     cell2=document.createElement("TD");
                          /*var CR_DR_type=document.createElement("input");
                          CR_DR_type.type="hidden";
                          CR_DR_type.name="CR_DR_type";
                          CR_DR_type.value=items[2];
                          cell2.appendChild(CR_DR_type);*/
                           var currentText=document.createTextNode(items[2]);
                          cell2.appendChild(currentText);
                        mycurrent_row.appendChild(cell2);
                     cell2=document.createElement("TD");
                          /*var CR_DR_type=document.createElement("input");
                          CR_DR_type.type="hidden";
                          CR_DR_type.name="CR_DR_type";
                          CR_DR_type.value=items[2];
                          cell2.appendChild(CR_DR_type);*/
                           var currentText=document.createTextNode(items[3]);
                          cell2.appendChild(currentText);
                        mycurrent_row.appendChild(cell2);
                  
                     //--------------------------------------- for details----
                        var cell=document.createElement("TD");
                        cell.align='CENTER';
                        var anc=document.createElement("A");
                        var url="javascript:Show('"+Ucode+"','"+Offid+"','"+items[0]+"','"+items[1]+"')";
                        anc.href=url;
                        var txtedit=document.createTextNode("Show Details");
                        anc.appendChild(txtedit);
                        cell.appendChild(anc);
                        mycurrent_row.appendChild(cell);
                       //---------------------------------------end 
                        
              tbody.appendChild(mycurrent_row);
        }
        
     }
    else if(flag=="failure")
     {
        alert("No Remittance Record found for Verification");
        var tbody=document.getElementById("grid_body");
        var t=0;
        for(t=tbody.rows.length-1;t>=0;t--)
        {
           tbody.deleteRow(0);
        }
     }
}

function call_clr()
{
//
         var tbody=document.getElementById("grid_body");
        var t=0;
        for(t=tbody.rows.length-1;t>=0;t--)
        {
           tbody.deleteRow(0);
        }
}

function exit()
{
       self.close();
}

function checkNull()
{
var tbody=document.getElementById("grid_body");
if(tbody.rows.length==0)
{
    alert("No data for verification");
    //document.getElementById("txtAmount").focus();
    return false; 
}
return true;
}


function call_date(date_ctr)
{
  doFunction('loadPendingRemittance','null');
}



function call_mainJSP_script(date_Field,flag)
{
doFunction('loadPendingRemittance','null');
}
