var sequence=0;
var currentlyEditing=0;
var edit=false;


//TO APPEND A ROW IN THE GRID
function addSupplierSL()
{
              
              //alert("inside add");
              if(document.SupplierSL.offcode.value=="" || document.SupplierSL.accounting_unit_code.value=="" || document.SupplierSL.supplier_id.value=="")
              {
                alert("Please enter the values")
              }
              else
              {              
                          var strSupId=document.SupplierSL.supplier_id.value;
                          var strSupName=document.SupplierSL.supplier_name.value;
                          var strSupAddr=document.SupplierSL.supplier_address.value;;
                          var strSupPhone=document.SupplierSL.supplier_phone.value;;
                          var strSupFax=document.SupplierSL.supplier_fax.value;;
                          var strSupEmail=document.SupplierSL.supplier_email_id.value;
                          
                          
                          var items=new Array();
                          items[0]=strSupId;
                          
                          items[1]=strSupName;
                       
                          items[2]=strSupAddr;
                          
                          items[3]=strSupPhone;
                          
                          items[4]=strSupFax;
                          
                          items[5]=strSupEmail;
                          //alert(items[5]);
                          
                          
                          var tbody=document.getElementById("tblSupplierSL");
                          //alert(tbody);
                          
                          sequence=sequence+1;
                          var mycurrent_row=document.createElement("TR");
                          mycurrent_row.id=sequence;
                                           
                          var cell1=document.createElement("TD");
                          cell1.align="center";
                          var serno=document.createElement("input"); 
                          var hserno=document.createElement("input");// HIDDEN INPUT TO GET THE SAME VALUE OF THE SERNO INPUT
                          hserno.type="hidden";
                          hserno.name="HSerNo";
                          serno.type="text";
                          serno.name="SerialNumber";
                          serno.size="2";
                          serno.disabled="true";
                          serno.value=sequence;
                          hserno.value=sequence;
                          cell1.appendChild(serno); 
                          cell1.appendChild(hserno);
                          mycurrent_row.appendChild(cell1);
                          
                         var cell2=document.createElement("TD");
                          var id=document.createElement("input"); 
                          var hid=document.createElement("input");// HIDDEN INPUT TO GET THE SAME VALUE OF THE SERNO INPUT
                          hid.type="hidden";
                          hid.name="HSupId";
                          id.type="text";
                          id.name="SupId";
                          id.size="6";
                          id.disabled="true";
                          id.value=items[0];
                          hid.value=items[0];
                          cell2.appendChild(id); 
                          cell2.appendChild(hid);
                          mycurrent_row.appendChild(cell2);
                          
                                                    
                          var cell3=document.createElement("TD");
                          var name=document.createElement("input"); 
                          var hname=document.createElement("input");// HIDDEN INPUT TO GET THE SAME VALUE OF THE SERNO INPUT
                          hname.type="hidden";
                          hname.name="HName";
                          name.type="text";
                          name.name="SubName";
                          name.size="25";
                          name.disabled="true";
                          name.value=items[1];
                          hname.value=items[1];
                          cell3.appendChild(name); 
                          cell3.appendChild(hname);
                          cell3.appendChild(document.createElement("br"));
                         
                           
                          
                           
                          
                         
                          //var cell4=document.createElement("TR");    
                         
                          var supaddr=document.createElement("textarea"); 
                          var hsupaddr=document.createElement("input");
                          hsupaddr.type="hidden";
                          //supaddr.type="text";
                          hsupaddr.name="HSupAddr";  
                          supaddr.name="SupAddr";
                          supaddr.cols="25";
                          supaddr.value=items[2];
                          hsupaddr.value=items[2];
                          supaddr.disabled="true";
                          cell3.appendChild(supaddr);    
                          cell3.appendChild(hsupaddr);
                          cell3.appendChild(document.createElement("br"));   
                          
                          
                          var email=document.createElement("input"); 
                          var hemail=document.createElement("input");
                          hemail.type="hidden";
                          email.type="text";
                          hemail.name="HSupEmail";  
                          email.name="SupEmail";
                          email.size="25";
                          email.value=items[5];
                          hemail.value=items[5];
                          email.disabled="true";
                          cell3.appendChild(email);    
                          cell3.appendChild(hemail);
                          mycurrent_row.appendChild(cell3);   
                          
                          
                          var cell4=document.createElement("TD"); 
                          var phone=document.createElement("input"); 
                          var hphone=document.createElement("input");
                          hphone.type="hidden";
                          phone.type="text";
                          hphone.name="HSupPhone";  
                          phone.name="SupPhone";
                          phone.size="15";
                          phone.value=items[3];
                          hphone.value=items[3];
                          phone.disabled="true";
                          cell4.appendChild(phone);    
                          cell4.appendChild(hphone);
                          mycurrent_row.appendChild(cell4);  
                          
                          
                          
                          var cell5=document.createElement("TD"); 
                          var fax=document.createElement("input"); 
                          var hfax=document.createElement("input");
                          hfax.type="hidden";
                          fax.type="text";
                          hfax.name="HSupFax";  
                          fax.name="SupFax";
                          fax.size="15";
                          fax.value=items[4];
                          hfax.value=items[4];
                          fax.disabled="true";
                          cell5.appendChild(fax);    
                          cell5.appendChild(hfax);
                          mycurrent_row.appendChild(cell5);  
                            
                          
                            
                          
                          
                          
                          
                          
                          
                          
                          var cell6=document.createElement("TD"); 
                          var ch1=document.createElement("<input type='checkbox' >");
                          ch1.value=sequence;
                          ch1.onclick=function()
                           {
                                if (confirm("Are U Sure U want to Delete ?"))
                                  {
                                  alert("You agree");
                                  deleteSL(this);
                                  }
                                  else
                                  {
                                  alert ("You do not agree")
                                  ch1.checked=false;
                                  }
                                  
                           }
                                  
                                                   
                                  
                          cell6.appendChild(ch1);
                                          
                          mycurrent_row.appendChild(cell6); 
                                         
                          cell6=document.createElement("TD"); 
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
                                 
                          cell6.appendChild(ch2);
                          mycurrent_row.appendChild(cell6); 
                         
                          tbody.appendChild(mycurrent_row); 
                          //document.SupplierSL.MinorGrpDesc.disabled=true;
                          //document.SupplierSL.add.disabled=true;
                          //document.SupplierSL.MinorGrpCode.disabled=true;
            
                         
      }        
 }
 
          
   
    function LoadFromTable(id)
                  {    
                      
                       rid=id.value;
                       
                       var r=document.getElementById(rid);   
                       var rcells=r.cells;
                       var supid=rcells.item(1).firstChild.value;
                       var supname=rcells.item(2).childNodes[0].value;
                       var supaddr=rcells.item(2).childNodes[4].value;
                       var supphone=rcells.item(3).firstChild.value;
                       var supfax=rcells.item(4).firstChild.value;
                       var supemail=rcells.item(2).childNodes[7].value;
                       
                       
                       
                       
                       var url="../jsps/LoadSupplierSL.jsp?rowid=" + rid + "&SupId=" + supid + "&SupName=" +supname+"&SupAddr="+supaddr+"&SupPhone="+supphone+"&SupFax="+supfax+"&SupEmail="+supemail ;
                       var  my_window= window.open(url,"mywindow2","status=1,height=750,width=750"); 
                       my_window.moveTo(100,250); 
                  }
   
   
  
   
                 
                 
                 
                 
       
       //TO DELETE THE ROW FROM THE GRID
                 
      function deleteSL(id) 
                  {
                     
                      rid=id.value;
                      var tbody=document.getElementById("tblSupplierSL"); 
                      var r=document.getElementById(rid); 
                      var ri=r.sectionRowIndex;
                      tbody.deleteRow(ri);   
                  
                  }
                  
                  
  function popWindow()
  {
  var off=document.SupplierSL.seloff.value;
  var url="../jsps/ViewOffices1.jsp?office="+off;
  var wind=window.open(url,"myWindow","status=1,height=200,width=200");
  wind.moveTo(100,250);
  }
                  
                  
      
        
  
    
    
