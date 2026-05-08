
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


function GetVoucherInfo()
{
   
    var doc=window.opener.document;            // get the parent form
    
      var unitcode=doc.frmJournal_General.cmbAcc_UnitCode.value;
      var offid=doc.frmJournal_General.cmbOffice_code.value;
      var month=doc.frmJournal_General.txtCash_Month.value;
      var year=doc.frmJournal_General.txtCash_year.value; 
      var type_MasSL=doc.frmJournal_General.cmbMas_SL_type.value;
 
          var url="../../../../../Journal_General_Pending?type_MasSL="+type_MasSL+"&year="+year+"&month="+month+"&cmbAcc_UnitCode="+unitcode+"&cmbOffice_code="+offid;
        
         
            var req=getTransport();
           req.open("GET",url,true); 
            
            req.onreadystatechange=function()
            {
               ProcessVoucherInfo(req);
            }   
                    req.send(null);
        
}

function ProcessVoucherInfo(req)
{
 if(req.readyState==4)
    {
        if(req.status==200)
        {
          //  alert(req.responseText);
             var baseResponse=req.responseXML.getElementsByTagName("response")[0];
             var flag=baseResponse.getElementsByTagName("flag")[0].firstChild.nodeValue;//alert(flag);
                if(flag=="success")
                {
                    var tbody=document.getElementById("grid_body");
                    var t=0;
                    for(t=tbody.rows.length-1;t>=0;t--)
                    {
                       tbody.deleteRow(0);
                    }
                  
                     var AHcode=baseResponse.getElementsByTagName("AHcode");
                    
                    var items=new Array();
                    //alert(AHcode.length)
                    for(var k=0;k<AHcode.length;k++)
                    {
                    items[0]=baseResponse.getElementsByTagName("AHcode")[k].firstChild.nodeValue;  //alert(items[0]); 
                    items[1]=baseResponse.getElementsByTagName("AHdesc")[k].firstChild.nodeValue;   //alert(items[1]);  
                     items[2]=baseResponse.getElementsByTagName("CR_DR_ind")[k].firstChild.nodeValue; //alert(items[2]); 
                     items[3]=baseResponse.getElementsByTagName("SL_Type")[k].firstChild.nodeValue; //alert(items[3]); 
                     if(items[3]==0)
                     items[3]="";
                     
                    items[4]=baseResponse.getElementsByTagName("SL_Desc")[k].firstChild.nodeValue; //alert(items[4]); 
                    if(items[4]=="null")
                    items[4]="";
                    
                    items[5]=baseResponse.getElementsByTagName("SL_Code")[k].firstChild.nodeValue; //alert(items[5]); 
                    if(items[5]==0)
                    items[5]="";
                    
                    items[10]=baseResponse.getElementsByTagName("Bill_NO")[k].firstChild.nodeValue; //alert(items[10]); 
                    items[11]=baseResponse.getElementsByTagName("Bill_date")[k].firstChild.nodeValue;
                    items[12]=baseResponse.getElementsByTagName("Bill_type")[k].firstChild.nodeValue;
                    items[13]=baseResponse.getElementsByTagName("Agree_No")[k].firstChild.nodeValue;
                    items[14]=baseResponse.getElementsByTagName("Agree_date")[k].firstChild.nodeValue;
                    items[15]="";
                    items[16]=baseResponse.getElementsByTagName("sub_amount")[k].firstChild.nodeValue; //alert(items[16]); 
                    items[17]=baseResponse.getElementsByTagName("sub_part")[k].firstChild.nodeValue;
                    
                   
                    items[18]=baseResponse.getElementsByTagName("VOUCHER_NO")[k].firstChild.nodeValue;  //alert(items[18]);   
                    items[19]=baseResponse.getElementsByTagName("vou_date")[k].firstChild.nodeValue;
                    items[20]=baseResponse.getElementsByTagName("JOURNAL_TYPE")[k].firstChild.nodeValue;
                    items[21]=baseResponse.getElementsByTagName("SL_NO")[k].firstChild.nodeValue;     
                    items[22]=baseResponse.getElementsByTagName("SL_name")[k].firstChild.nodeValue;     
//                   alert("items[22]"+items[22]);
//                    alert("items[3]"+items[3]);
//                     alert("items[4]"+items[4]);
                   
                   if(items[10]=="null")
                    items[10]="";
                    if(items[11]=="null")
                    items[11]="";
                     if(items[12]=="null")
                    items[12]="";
                     if(items[13]=="null")
                    items[13]="";
                     if(items[14]=="null")
                    items[14]="";
                     if(items[15]=="null")
                    items[15]="";
                     if(items[17]=="null")
                    items[17]="";
                    
                     tbody=document.getElementById("grid_body");
                   var mycurrent_row=document.createElement("TR");
                    seq=seq+1;
                    mycurrent_row.id=seq;
                   // alert("row ID"+mycurrent_row.id);
                    var cell=document.createElement("TD");
                    var vou_no=document.createElement("input");
                   vou_no.type="radio";
                   vou_no.name="vou_no";
                   vou_no.id="vou_no";
                   vou_no.value=seq;
                   cell.appendChild(vou_no);
                    mycurrent_row.appendChild(cell);
                    var i=0;
                    var cell2;
                    
                     cell2=document.createElement("TD");
                         cell2.align="CENTER";
                               var vou_type=document.createElement("input");
                              vou_type.type="hidden";
                              // vou_type.align="right";  // Not working
                              vou_type.name="vou_type";
                              vou_type.value=items[20];
                              //cell2.style.display="none";
                              cell2.appendChild(vou_type);
                               var currentText=document.createTextNode(items[20]);
                              cell2.appendChild(currentText);
                               mycurrent_row.appendChild(cell2);
                               
                  cell2=document.createElement("TD");
                              var vou_date=document.createElement("input");
                              vou_date.type="hidden";
                              vou_date.name="vou_date";
                              vou_date.value=items[19];
                              cell2.appendChild(vou_date);
                               var currentText=document.createTextNode(items[19]);
                              cell2.appendChild(currentText);
                            mycurrent_row.appendChild(cell2);
                            
                    cell2=document.createElement("TD");
                        cell2.align="CENTER";
                             var Voucher_no=document.createElement("input");
                              Voucher_no.type="hidden";
                              Voucher_no.name="Voucher_no";
                              Voucher_no.value=items[18];//alert(Voucher_no.value);
                              cell2.appendChild(Voucher_no);
                              var currentText=document.createTextNode(items[18]);
                              cell2.appendChild(currentText);
                               mycurrent_row.appendChild(cell2);
                    
                               
                    cell2=document.createElement("TD");                             // added on 27th April 07
                         cell2.align="CENTER";
                               var SL_NO=document.createElement("input");
                              SL_NO.type="hidden";
                              SL_NO.name="SL_NO";
                              SL_NO.value=items[21];
                              cell2.style.display="none";
                              cell2.appendChild(SL_NO);
                               var currentText=document.createTextNode(items[21]);
                              cell2.appendChild(currentText);
                               mycurrent_row.appendChild(cell2);
                               
                       
                            
                        
                    
                               
                        cell2=document.createElement("TD");
            
                             var H_code=document.createElement("input");
                              H_code.type="hidden";
                              H_code.name="H_code";
                              H_code.value=items[0];
                              cell2.appendChild(H_code);
                              var currentText=document.createTextNode(items[1]);
                              cell2.appendChild(currentText);
                               mycurrent_row.appendChild(cell2);
                               
                         cell2=document.createElement("TD");
                              var CR_DR_type=document.createElement("input");
                              CR_DR_type.type="hidden";
                              CR_DR_type.name="CR_DR_type";
                              CR_DR_type.value=items[2];
                              cell2.appendChild(CR_DR_type);
                               var currentText=document.createTextNode(items[2]);
                              cell2.appendChild(currentText);
                            mycurrent_row.appendChild(cell2);
                            
                         cell2=document.createElement("TD");
                               var SL_type=document.createElement("input");
                              SL_type.type="hidden";
                              SL_type.name="SL_type";
                              SL_type.value=items[3];
                              cell2.appendChild(SL_type);
                               var currentText=document.createTextNode(items[4]);
                              cell2.appendChild(currentText);
                               mycurrent_row.appendChild(cell2);
                               
                        cell2=document.createElement("TD");
                              var SL_code=document.createElement("input");
                              SL_code.type="hidden";
                              SL_code.name="SL_code";
                              SL_code.value=items[5];
                              cell2.appendChild(SL_code);
                               var currentText=document.createTextNode(items[22]);
                              cell2.appendChild(currentText);
                            mycurrent_row.appendChild(cell2);
                                               
                         cell2=document.createElement("TD");
                              var Bill_NO=document.createElement("input");
                              Bill_NO.type="hidden";
                              Bill_NO.name="Bill_NO";
                              Bill_NO.value=items[10];
                              cell2.appendChild(Bill_NO);
                                                                                                   //    var currentText=document.createTextNode(items[7]);
                                                                                                   //    cell2.appendChild(currentText);
                                                                                                    //mycurrent_row.appendChild(cell2);
                        
                                                                                                                 //cell2=document.createElement("TD");
                              var Bill_date=document.createElement("input");
                              Bill_date.type="hidden";
                              Bill_date.name="Bill_date";
                              Bill_date.value=items[11];
                              cell2.appendChild(Bill_date);
                                                                                                              // var currentText=document.createTextNode(items[8]);
                                                                                                              //cell2.appendChild(currentText);
                                                                                                                // mycurrent_row.appendChild(cell2);
                            
                            
                                                                                                            // cell2=document.createElement("TD"); 
                              var Bill_type=document.createElement("input");
                              Bill_type.type="hidden";
                              Bill_type.name="Bill_type";
                              Bill_type.value=items[12];
                              cell2.appendChild(Bill_type);
                              
                                                                                                            //  cell2=document.createElement("TD");
                              var Agree_No=document.createElement("input");
                              Agree_No.type="hidden";
                              Agree_No.name="Agree_No";
                              Agree_No.value=items[13];
                              cell2.appendChild(Agree_No);
                                
                              var Agree_date=document.createElement("input");
                              Agree_date.type="hidden";
                              Agree_date.name="Agree_date";
                              Agree_date.value=items[14];
                              cell2.appendChild(Agree_date);
                                         
                                         
                              var sub_paid=document.createElement("input");
                              sub_paid.type="hidden";
                              sub_paid.name="sub_paid";
                              sub_paid.value=items[15];
                              cell2.appendChild(sub_paid);
            
                              var sl_amt=document.createElement("input");
                              sl_amt.type="hidden";
                              sl_amt.name="sl_amt";
                              sl_amt.value=items[16];
                              cell2.appendChild(sl_amt);
            
                              var particular=document.createElement("input");           // Particulars Added to grid b4 the Amount Text Node but after  amount text box    
                              particular.type="hidden";
                              particular.name="particular";
                              particular.value=items[17];
                              cell2.appendChild(particular);
            
                              var currentText=document.createTextNode(items[16]);
                              cell2.appendChild(currentText);
                              mycurrent_row.appendChild(cell2);
                   
                                tbody.appendChild(mycurrent_row);
                     }
                }
                else if(flag=="failure")
                {
                    alert("No data found");
                    opener.doParentJournal();
                     self.close();
                    var tbody=document.getElementById("grid_body");
                    var t=0;
                    for(t=tbody.rows.length-1;t>=0;t--)
                    {
                       tbody.deleteRow(0);
                    }
                }
        }
    }
}
var winPendingbills;
function btnsubmit()
{
   
    var sele=document.getElementsByName("vou_no").length;   
   
    var val=0;
    var vouNO,vouDATE,vouTYPE,AcHeadCode,AcHeadName,CR_DB_indic,subLed_type,subLed_typeName;
    var vouSL_NO;
    
    if(sele>0)
    {
         var j=0;
        if(sele==1)
        {
                        
            if(document.frmJournal_Bills_pending.vou_no.checked==true)
            {
            	            r=document.getElementById(document.frmJournal_Bills_pending.vou_no.value);      // choose the particular row
                            rcells=r.cells;
                            
                            vouNO=rcells.item(3).firstChild.value;
                            vouSL_NO=rcells.item(4).firstChild.value;
                            vouDATE=rcells.item(2).firstChild.value;
                            vouTYPE=rcells.item(1).firstChild.value;                           
                            
                    j++;
            }
            else 
                {
                    alert("select the Journal voucher");
                    return false;
                }
        }
        else
        {    
               
                for(i=0;i<sele;i++)
                { 
                   
                    //alert(document.frmJournal_Bills_pending.choice[i].checked)
                    if(document.frmJournal_Bills_pending.vou_no[i].checked==true)
                    {
                            r=document.getElementById(document.frmJournal_Bills_pending.vou_no[i].value);
                            rcells=r.cells;//alert(r);
                            
                            vouNO=rcells.item(3).firstChild.value;//
                            vouSL_NO=rcells.item(4).firstChild.value;//alert(vouSL_NO[j]);
                            vouDATE=rcells.item(2).firstChild.value;//
                            vouTYPE=rcells.item(1).firstChild.value;
                            
                          j++;
                    }
                }
            if(j==0)
                {
                alert("select the Journal voucher");
                return false;
                }
         }
        
    }
    else 
    {
        alert("select the Journal voucher");
        return false;
    }   
    //alert(vouNO);alert(vouDATE);
 //   opener.doParentPendingbills("1","sss");
    opener.doParentPendingbills(vouNO,vouDATE);
    
    self.close();
   
}
