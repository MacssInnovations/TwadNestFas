var sequence=0;
var currentlyEditing=0;
var edit=false;
var amount=false;

//TO APPEND A ROW IN THE GRID
function addCashRec()
{
              
              
              /*if(document.MinorHeadAdd.MinorGrpCode.value=="" || document.MinorHeadAdd.MinorGrpDesc.value=="")
              {
                alert("Please enter the values")
              }
              else
              {*/
              
              var strSerno=document.cash_receipt.ser_no.value;
              
              var strAhc=document.cash_receipt.acct_head_code.options(document.cash_receipt.acct_head_code.selectedIndex).text;
              
              var strSLCode=document.cash_receipt.slcode.options(document.cash_receipt.slcode.selectedIndex).text;
              
              var strCD=document.cash_receipt.credit_debit.options(document.cash_receipt.credit_debit.selectedIndex).text;
             
              var strAmount=document.cash_receipt.amount.value;
              
              var strSLType=document.cash_receipt.amount.value;
             
              
              var items=new Array();
              items[0]=strSerno;
              items[1]=strAhc;
              items[2]=strSLCode;
              items[3]=strCD;
              items[4]=strAmount;
              items[5]=strSLType;
              var tbody=document.getElementById("tblCashReceipt");
              
              sequence=sequence+1;
              var mycurrent_row=document.createElement("TR");
              mycurrent_row.id=sequence;
               
               
               
              
                               
              
              var cell1=document.createElement("TD");
              var index=document.createElement("input"); 
              var hindex=document.createElement("input");// HIDDEN INPUT TO GET THE SAME VALUE OF THE SERNO INPUT
              hindex.type="hidden";
              hindex.name="HIndex";
              index.type="text";
              index.name="Index";
              index.size="2";
              index.disabled="true";
              index.value=sequence;
              hindex.value=sequence;
              cell1.appendChild(index); 
              cell1.appendChild(hindex);
              mycurrent_row.appendChild(cell1);
              
              
          //to get values from other fields of New Details in the form that r not n the grid.    
              var hsltype=document.createElement("input");// HIDDEN INPUT TO GET THE SAME VALUE OF THE SERNO INPUT
              hsltype.type="hidden";
              hsltype.name="HSLType";
              hsltype.value=sequence;
              
              var hreceivedFrom=document.createElement("input");// HIDDEN INPUT TO GET THE SAME VALUE OF THE SERNO INPUT
              hreceivedFrom.type="hidden";
              hreceivedFrom.name="HRecFrom";
              hreceivedFrom.value=items[6];;
              
              var hParticulars=document.createElement("input");// HIDDEN INPUT TO GET THE SAME VALUE OF THE SERNO INPUT
              hParticulars.type="hidden";
              hParticulars.name="HParticulars";
              hParticulars.value=sequence;
              
              
              
              
             
              cell1.appendChild(hsltype);
              cell1.appendChild(hreceivedFrom);
              cell1.appendChild(hParticulars);
              mycurrent_row.appendChild(cell1);
              
              
              
              var cell2=document.createElement("TD");
              var serno=document.createElement("input"); 
              var hserno=document.createElement("input");// HIDDEN INPUT TO GET THE SAME VALUE OF THE SERNO INPUT
              hserno.type="hidden";
              hserno.name="HSerNo";
              serno.type="text";
              serno.name="SerNo";
              serno.size="2";
              serno.disabled="true";
              serno.value=items[0];
              hserno.value=items[0];
              cell2.appendChild(serno); 
              cell2.appendChild(hserno);
              mycurrent_row.appendChild(cell2);
             

              var cell3=document.createElement("TD");    
              var ahc=document.createElement("input"); 
              var hahc=document.createElement("input");
              hahc.type="hidden";
              ahc.type="text";
              hahc.name="HAhc";
              ahc.name="Ahc";
              ahc.size="35";
              ahc.value=items[1];
              hahc.value=items[1];
              ahc.disabled="true";
              cell3.appendChild(ahc); 
              cell3.appendChild(hahc);
              mycurrent_row.appendChild(cell3);
              
             var cell4=document.createElement("TD");    
              var slc=document.createElement("input"); 
              var hslc=document.createElement("input");
              hslc.type="hidden";
              slc.type="text";
              hslc.name="HSlc";
              slc.name="Slc";
              slc.size="25";
              slc.value=items[2];
              hslc.value=items[2];
              slc.disabled="true";
              cell4.appendChild(slc); 
              cell4.appendChild(hslc);
              mycurrent_row.appendChild(cell4); 
              
              var cell5=document.createElement("TD");    
              var cd=document.createElement("input"); 
              var hcd=document.createElement("input");// HIDDEN INPUT TO GET THE SAME VALUE OF THE SLCODE INPUT
              hcd.type="hidden";
              cd.type="text";
              hcd.name="HCD";
              cd.name="CD";
              cd.size="25";
              cd.value=items[3];
              hcd.value=items[3];
              cd.disabled="true";
              cell5.appendChild(cd); 
              cell5.appendChild(hcd);
              mycurrent_row.appendChild(cell5); 
              
              var cell6=document.createElement("TD");    
              var amt=document.createElement("input"); 
              var hamt=document.createElement("input");
              hamt.type="hidden";
              amt.type="text";
              hamt.name="HAmt";  
              amt.name="Amt";
              amt.size="6";
              amt.value=items[4];
              hamt.value=items[4];
              amt.disabled="true";
              cell6.appendChild(amt);    
              cell6.appendChild(hamt);
              mycurrent_row.appendChild(cell6);   
              
              
              
              
              var cell7=document.createElement("TD"); 
              var ch1=document.createElement("<input type='checkbox' >");
              ch1.value=sequence;
              ch1.onclick=function()
               {
                    if (confirm("Are U Sure U want to Delete ?"))
                      {
                      alert("You agree");
                      deleteCashRec(this) ;
                      }
                      else
                      {
                      alert ("You do not agree")
                      ch1.checked=false;
                      }
                      
               }
                      
                                       
                      
              cell7.appendChild(ch1);
                              
              mycurrent_row.appendChild(cell7); 
                             
              cell7=document.createElement("TD"); 
              ch2=document.createElement("<input type='checkbox' >");
              ch2.value=sequence;
              ch2.onclick=function()
                  {
                      if (confirm("Are U Sure U want to Edit ?"))
                       {
                           LoadFromTable(this);
                       }  
                       
                    else
                       {
                          alert ("You do not agree")
                          ch2.checked=false;
                        }
                  }
                     
              cell7.appendChild(ch2);
              mycurrent_row.appendChild(cell7); 
              tbody.appendChild(mycurrent_row); 
              //document.MinorHeadAdd.MinorGrpDesc.disabled=true;
              document.cash_receipt.update.disabled=true;
              //document.MinorHeadAdd.MinorGrpCode.disabled=true;
      //}   
      amount=amount+(document.cash_receipt.amount.value);
      
      checkAmt(amount);
 
 }
          
   
    function LoadFromTable(id)
                  {    
                      
                       rid=id.value;
                       
                       var r=document.getElementById(rid);   
                       var rcells=r.cells;
                       var serno=rcells.item(1).firstChild.value;
                       var ahc=rcells.item(2).firstChild.value;
                       var slcode=rcells.item(3).firstChild.value;
                       var cd=rcells.item(4).firstChild.value;
                       var amt=rcells.item(5).firstChild.value;
                      
                       var sltype=document.cash_receipt.sltype.value;
                      
                       var url="../jsps/LoadCashRec.jsp?rowid=" + rid + "&Serno=" + serno + "&Ahc=" +ahc +"&SLCode="+slcode+"&CD="+cd+"&Amt="+amt+"&SLType="+sltype;
                       var  my_window= window.open(url,"mywindow2","status=1,height=750,width=750"); 
                       my_window.moveTo(100,250); 
                  }
   
   
  
   
                 
                 
                 
                 
       
       //TO DELETE THE ROW FROM THE GRID
                 
      function deleteCashRec(id) 
                  {
                     
                      rid=id.value;
                      var tbody=document.getElementById("tblCashReceipt"); 
                      var r=document.getElementById(rid); 
                      var ri=r.sectionRowIndex;
                      tbody.deleteRow(ri);   
                  
                  }
                  
      function checkAmt(amount)
      {
       var actualamt=document.cash_receipt.total_amt.value;
       if(amount==actualamt)
       {
         alert("do not enter more");
       }
       
      }
                  
                  
      
        
  
    
    
