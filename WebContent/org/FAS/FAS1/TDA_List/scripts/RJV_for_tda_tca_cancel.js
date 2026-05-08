var seq=0;

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



function call_mainJSP_script(fromcal_dateCtrl,blr_flag)     /////////Calender control return value handling
{
    //alert(fromcal_dateCtrl.value+"GG"+blr_flag+fromcal_dateCtrl);
    if(blr_flag==1)         // which is used to find the receipt or voucher or payment (creation) date calling field,if so check trial balance
    {
           //  call_clr();
             var cmbAcc_UnitCode=document.getElementById("cmbAcc_UnitCode").value;
             var cmbOffice_code=document.getElementById("cmbOffice_code").value;
             var TB_date=fromcal_dateCtrl.value;
             //alert(fromcal_dateCtrl.value+"b4url")
             if(fromcal_dateCtrl.value.length!=0)
             {
                 var url="../../../../../Receipt_SL.view?Command=check_TB&TB_date="+TB_date+"&cmbAcc_UnitCode="+cmbAcc_UnitCode+"&cmbOffice_code="+cmbOffice_code;
                        //alert(url);
                 var req=getTransport();
                 req.open("GET",url,true); 
                 req.onreadystatechange=function()
                 {
                   check_TB(req,fromcal_dateCtrl);
                 }   
                 req.send(null);
            }
    }
}


function check_TB(req,dateCtrl)
{
 if(req.readyState==4)
    {
        if(req.status==200)
        {  
            var baseResponse=req.responseXML.getElementsByTagName("response")[0];
            var tagcommand=baseResponse.getElementsByTagName("command")[0];
            var Command=tagcommand.firstChild.nodeValue;
            var flag=baseResponse.getElementsByTagName("flag")[0].firstChild.nodeValue;
            
            if(flag=="success")
              {
                 //doFunction('load_Receipt_No','null');                 //return true;
              }
             else if(flag=="failure")
               {
                    dateCtrl.value="";
                    alert("Trial Balance Closed");//return false;//
                    dateCtrl.focus();
                    document.getElementById("txtReceipt_No").value="";
               }
             else if(flag=="finyear")
               {
                          // This statement get executed when financial year ( Cash Book Control Details ) has not found for the given date 
                    dateCtrl.value="";
                    alert("Cash Book Control Not Found ");//return false;//
                    dateCtrl.focus();
                    document.getElementById("txtReceipt_No").value="";     
               }
        }
    }
}

function checkNull_cancel()
{
    if(window.confirm('Do you want to Cancel?'))
    {
        var tbody=document.getElementById("grid_body");
           //alert("tbody.rows.length :"+tbody.rows.length);   
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
        if(document.getElementById("txtJournalVou_No").value.length==0)
        {
            alert("Select Voucher Number");
            //document.getElementById("txtJournalVou_No").focus();
            return false;
        }
       
        return true;
    }
    else
      return false;
}
//end 
function call_date(dateCtrl)                        // TB_checking
{
  
   if(checkdt(dateCtrl))
   {
       //doFunction('check_TB',dateCtrl.value);
        var cmbAcc_UnitCode=document.getElementById("cmbAcc_UnitCode").value;
        var cmbOffice_code=document.getElementById("cmbOffice_code").value;
        var TB_date=dateCtrl.value;
      
        if(dateCtrl.value.length!=0)
        {
            var url="../../../../../Receipt_SL.view?Command=check_TB&TB_date="+TB_date+"&cmbAcc_UnitCode="+cmbAcc_UnitCode+"&cmbOffice_code="+cmbOffice_code;
            var req=getTransport();
            req.open("GET",url,true); 
            req.onreadystatechange=function()
            {
              check_TB(req,dateCtrl);
            }   
            req.send(null);
      }
     
   }
   else
   {
  
   }
}








function check_TB(req,dateCtrl)
{
if(req.readyState==4)
   {
       if(req.status==200)
       {  
           var baseResponse=req.responseXML.getElementsByTagName("response")[0];
           var tagcommand=baseResponse.getElementsByTagName("command")[0];
           var Command=tagcommand.firstChild.nodeValue;
           var flag=baseResponse.getElementsByTagName("flag")[0].firstChild.nodeValue;
           
           if(flag=="success")
             {
                //doFunction('load_Receipt_No','null');                 // return
																			// true;
             }
            else if(flag=="failure")
              {
                   dateCtrl.value="";
                   alert("Trial Balance Closed");// return false;//
                   dateCtrl.focus();
                    document.getElementById("txtCrea_date").value="";
              }
            else if(flag=="finyear")
              {
                         // This statement get executed when financial year ( Cash Book Control Details ) has not found for the given date 
                   dateCtrl.value="";
                   alert("Cash Book Control Not Found ");// return false;//
                   dateCtrl.focus();
                   document.getElementById("txtCrea_date").value="";
              }
       }
   }
}

function doFunction(Command,param)
{ 
	if(Command=="load_Voucher_No")
	 {
	var txtCrea_date= document.RJVRejected.txtCrea_date.value
    var  cmbAcc_UnitCode=document.getElementById("cmbAcc_UnitCode").value;
     var cmbOffice_code=document.getElementById("cmbOffice_code").value;
     
     if(txtCrea_date.length!=0)
     {
     var url="../../../../../RJV_Cancel?Command=load_Voucher_No&txtCrea_date="+txtCrea_date+"&cmbAcc_UnitCode="+
     cmbAcc_UnitCode+"&cmbOffice_code="+cmbOffice_code;
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
	else if(Command=="load_Voucher_Details")
    {  
       // clearGeneral_Detail();
        var cmbAcc_UnitCode=document.getElementById("cmbAcc_UnitCode").value;
        var cmbOffice_code=document.getElementById("cmbOffice_code").value;
        var txtCrea_date= document.RJVRejected.txtCrea_date.value
        var  txtJournalVou_No=document.getElementById("txtJournalVou_No").value;
        if(txtJournalVou_No!="")
        {
        	var url="../../../../../RJV_Cancel?Command=load_Voucher_Details&txtJournalVou_No="+txtJournalVou_No+"&txtCrea_date="+txtCrea_date+"&cmbAcc_UnitCode="+
        cmbAcc_UnitCode+"&cmbOffice_code="+cmbOffice_code;;
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
            if(Command=="load_Voucher_No")
            {
                load_Voucher_No(baseResponse);
            }
            else if(Command=="load_Voucher_Details")
            {
                load_Voucher_Details(baseResponse);
            }
        }
    }
}
function load_Voucher_No(baseResponse)
{
var flag=baseResponse.getElementsByTagName("flag")[0].firstChild.nodeValue;
 var txtJournalVou_No=document.getElementById("txtJournalVou_No");
  if(flag=="success")
    {
           var items_id=new Array();
           var Rec_No=baseResponse.getElementsByTagName("Rec_No");
            
            for(var k=0;k<Rec_No.length;k++)
            {
                items_id[k]=baseResponse.getElementsByTagName("Rec_No")[k].firstChild.nodeValue;
                
            }
         
            txtJournalVou_No.innerHTML="";
            var option=document.createElement("OPTION");
            option.text="--Select Voucher Number--";
            option.value="";
            try
            {
                txtJournalVou_No.add(option);
            }catch(errorObject)
            {
                txtJournalVou_No.add(option,null);
            }
            
            for(var k=0;k<items_id.length;k++)
            {   
                  var option=document.createElement("OPTION");
                  option.text=items_id[k];
                  option.value=items_id[k];
                   try
                  {
                      txtJournalVou_No.add(option);
                  }
                  catch(errorObject)
                  {
                      txtJournalVou_No.add(option,null);
                  }
            }
    }
    else if(flag=="failure")
    {
            txtJournalVou_No.innerHTML="";
            var option=document.createElement("OPTION");
            option.text="--Select Voucher Number--";
            option.value="";
            try
            {
                txtJournalVou_No.add(option);
            }catch(errorObject)
            {
                txtJournalVou_No.add(option,null);
            }
         alert("No Receipt Found");
    }
}
function load_Voucher_Details(baseResponse)
{
    var flag=baseResponse.getElementsByTagName("flag")[0].firstChild.nodeValue;
    //alert("FF");
    if(flag=="success")
    {
       var cheq_No=baseResponse.getElementsByTagName("cheq_No")[0].firstChild.nodeValue;         // here i assigned 
       var cheq_Date=baseResponse.getElementsByTagName("cheq_Date")[0].firstChild.nodeValue;
       var Remak=baseResponse.getElementsByTagName("Remak")[0].firstChild.nodeValue;
      
       var sltype =new Array(13);
       sltype[51]="TDA Journal";
       sltype[52]="TCA Journal";
       sltype[53]="General Adjustment Journal";
       sltype[54]="Pay roll Journal";
       sltype[55]="Proforma Transfer";
       sltype[56]="Cheque Cancellation";
       sltype[57]="Cheque Dishonoured";
      
       var Mas_SL_type=baseResponse.getElementsByTagName("Mas_SL_type")[0].firstChild.nodeValue;
   //    var type_code1=baseResponse.getElementsByTagName("type")[0].firstChild.nodeValue;
       
    //   alert("Mas_SL_type"+Mas_SL_type+type_code1);
       document.getElementById("cmbMas_SL_type").value=Mas_SL_type;
     //  document.getElementById("cmbMas_SL_type1").value=type_code1;
    
      if(cheq_No!="null")
      {
      document.getElementById("txtCheque_NO").value=cheq_No;
      document.getElementById("CHD").style.display='block';
      }
      else
      {
      document.getElementById("txtCheque_NO").value="";
      document.getElementById("CHD").style.display='none';
      }
      
       if(cheq_Date!="null")
       document.getElementById("txtCheque_date").value=cheq_Date;
       else
       document.getElementById("txtCheque_date").value="";
      
       //document.getElementById("txtAmount").value=Total_amt;
      /* if(Rec_From!="null")
      document.getElementById("txtRecei_from").value=Rec_From;
      else
      document.getElementById("txtRecei_from").value="";*/
      
      
       if(Remak!="null")
         document.getElementById("txtRemarks").value=Remak;
        else
        document.getElementById("txtRemarks").value="";
       
       //var miHC =baseResponse.getElementsByTagName("miHC")[0].firstChild.nodeValue;
       
       var tbody=document.getElementById("grid_body");
                        var t=0;
                        for(t=tbody.rows.length-1;t>=0;t--)
                        {
                           tbody.deleteRow(0);
                        }
                        
         //var SLCODE=baseResponse.getElementsByTagName("SLCODE");
        
         var AHcode=baseResponse.getElementsByTagName("AHcode");
        
        var items=new Array();
        for(var k=0;k<AHcode.length;k++)
        {
        items[0]=baseResponse.getElementsByTagName("AHcode")[k].firstChild.nodeValue;   
        items[1]=baseResponse.getElementsByTagName("AHdesc")[k].firstChild.nodeValue;   
         items[2]=baseResponse.getElementsByTagName("CR_DR_ind")[k].firstChild.nodeValue;
         items[3]=baseResponse.getElementsByTagName("SL_Type")[k].firstChild.nodeValue;
         if(items[3]==0)
         items[3]="";
         
        items[4]=baseResponse.getElementsByTagName("SL_Desc")[k].firstChild.nodeValue;
        if(items[4]=="null")
        items[4]="";
        
        items[5]=baseResponse.getElementsByTagName("SL_Code")[k].firstChild.nodeValue;
        if(items[5]==0)
        items[5]="";
        
        items[6]=baseResponse.getElementsByTagName("desc_type")[k].firstChild.nodeValue;
        if(items[6]=="null")
        items[6]="";
        items[7]=baseResponse.getElementsByTagName("Bill_NO")[k].firstChild.nodeValue;
        items[8]=baseResponse.getElementsByTagName("Bill_date")[k].firstChild.nodeValue;
        items[9]=baseResponse.getElementsByTagName("Bill_type")[k].firstChild.nodeValue;
        items[10]=baseResponse.getElementsByTagName("Agree_No")[k].firstChild.nodeValue;
        items[11]=baseResponse.getElementsByTagName("Agree_date")[k].firstChild.nodeValue;
        
        items[12]=baseResponse.getElementsByTagName("sub_amount")[k].firstChild.nodeValue;
        items[13]=baseResponse.getElementsByTagName("sub_part")[k].firstChild.nodeValue;
       
         if(items[7]=="null")
        items[7]="";
         if(items[8]=="null")
        items[8]="";
         if(items[9]=="null")
        items[9]="";
         if(items[10]=="null")
        items[10]="";
         if(items[11]=="null")
        items[11]="";
        if(items[13]=="null")
        items[13]="";
        
         tbody=document.getElementById("grid_body");
        var mycurrent_row=document.createElement("TR");
        seq=seq+1;
        mycurrent_row.id=seq;
        //alert("row ID"+mycurrent_row.id);
       
        var i=0;
        var cell2;
        
      
            cell2=document.createElement("TD");
           
                 
                  var currentText=document.createTextNode(items[0]+"-"+items[1]);
                  cell2.appendChild(currentText);
                   mycurrent_row.appendChild(cell2);
             cell2=document.createElement("TD"); 
                 
                   var currentText=document.createTextNode(items[2]);
                  cell2.appendChild(currentText);
                mycurrent_row.appendChild(cell2);
             
             cell2=document.createElement("TD");
                 
                   var currentText=document.createTextNode(items[4]);
                  cell2.appendChild(currentText);
                   mycurrent_row.appendChild(cell2);
            
             cell2=document.createElement("TD");
                 
                   var currentText=document.createTextNode(items[6]);
                  cell2.appendChild(currentText);
                mycurrent_row.appendChild(cell2);
            
            cell2=document.createElement("TD");
                 
                   var currentText=document.createTextNode(items[7]);
                  cell2.appendChild(currentText);
                   mycurrent_row.appendChild(cell2);
            
             cell2=document.createElement("TD");
            
                   var currentText=document.createTextNode(items[8]);
                  cell2.appendChild(currentText);
                mycurrent_row.appendChild(cell2);
                
                
             cell2=document.createElement("TD"); 
              
                  var currentText=document.createTextNode(items[12]);
                  cell2.appendChild(currentText);
                  mycurrent_row.appendChild(cell2);


        tbody.appendChild(mycurrent_row);
        }
    }
    else if(flag=="failure")
     alert("Failed to load data");
}

