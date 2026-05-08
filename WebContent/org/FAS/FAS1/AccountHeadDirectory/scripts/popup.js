
    var req = false;
    var edit=false;
    var sequence=0;
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
                          
          
   
    function Load()
    {
     var tbl=document.getElementById("tblList");
         
          var i;
          for(i=tbl.rows.length;i>0;i--)
            {  
              tbl.deleteRow(0);
            }
             
             var strAcct_Head_Code=document.form1.acctHC.value;
             
             url="../../../../../ServletMaster2.view?command=Load&code=" + strAcct_Head_Code;
             
             req.open("GET",url,true);        
             req.onreadystatechange=LoadR;
             req.send(null);
             var tbody=document.getElementById("tblList");
             
      
    }
    
    function LoadR()
    {
       
        if(req.readyState==4)
        {
         
          if(req.status==200)
          { 
           
            var baseResponse=req.responseXML.getElementsByTagName("response")[0];
              
              var tagCommand=baseResponse.getElementsByTagName("command")[0];
              
              var command=tagCommand.firstChild.nodeValue; 
              
            var flag=baseResponse.getElementsByTagName("flag")[0].firstChild.nodeValue;
             
                       if(flag=="success")
                       { 
                       
                               var value=baseResponse.getElementsByTagName("value");
                               
                               
                                //var sequence=0;
                                
                                 for(j=0;j<value.length;j++)
                                {
                                    //alert("New SubLedger added successfully");
                                    var tbody=document.getElementById("tblList");
                                    var items=new Array();
                                    items[0]=value[j].getElementsByTagName("code")[0].firstChild.nodeValue;
                                    
                                    items[1]=value[j].getElementsByTagName("type")[0].firstChild.nodeValue;
              
                                    var tbody=document.getElementById("tblList");
              
              sequence=sequence+1;
              var mycurrent_row=document.createElement("TR");
              mycurrent_row.id=sequence;
                               
              var cell1=document.createElement("TD");
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
              var code=document.createElement("input"); 
              var hcode=document.createElement("input");// HIDDEN INPUT TO GET THE SAME VALUE OF THE SLCODE INPUT
              hcode.type="hidden";
              code.type="text";
              hcode.name="HSLCode";
              code.name="SLCode";
              code.size="6";
              code.value=items[0];
              hcode.value=items[0];
              code.disabled="true";
              cell2.appendChild(code); 
              cell2.appendChild(hcode);
              mycurrent_row.appendChild(cell2); 
              var cell3=document.createElement("TD");    
              var type=document.createElement("input"); 
              var htype=document.createElement("input");
              htype.type="hidden";
              type.type="text";
              htype.name="HSLType";  
              type.name="SLType";
              type.size="25";
              type.value=items[1];
              htype.value=items[1];
              type.disabled="true";
              cell3.appendChild(type);    
              cell3.appendChild(htype);
              mycurrent_row.appendChild(cell3);     
              var cell4=document.createElement("TD"); 
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
                      
                                       
                      
              cell4.appendChild(ch1);
                              
              mycurrent_row.appendChild(cell4); 
                             
              cell4=document.createElement("TD"); 
              ch2=document.createElement("<input type='checkbox' >");
              ch2.value=sequence;
                                      
                                   ch2.onclick=function()
                                      {
                                           if (confirm("Are U Sure U want to Edit ?"))
                                          {
                                               
                                               
                                               edit=true;
                                               
                                               //document.form1.submit1.disabled=false;
                                               document.form1.txt_sldesc.disabled=false;
                                               document.form1.update.disabled=false;
                                               document.form1.Cancel2.disabled=false;
                                               var pd=document.getElementById("parentt");
                                                alert(pd);
                                                pd.style.zIndex=2; 
                                                
                                                var cd=document.getElementById("subledger");
                                                alert(cd);
                                                cd.style.zIndex=1;
                                                cd.style.left='50px';
                                                cd.style.top='400px';
                                                alert("shall i show");
                                                cd.style.display="block";
                                               
                          
                          
                         
                                                  ch2.checked=false;
                                             ch2.checked=false;
                                          }  
                                          else
                                          {
                                              alert ("You do not agree")
                                              ch2.checked=false;
                                          }
                                      }
                                   
                                   cell4.appendChild(ch2);
                                    
                                   mycurrent_row.appendChild(cell4); 
                                   
                                   tbody.appendChild(mycurrent_row); 
                                   
                                         //document.form1.submit1.disabled=true;
                                         //document.form1.Cancel1.disabled=true;
                                   document.form1.txt_sldesc.disabled=true;
                                   document.form1.update.disabled=true;
                                   //document.form1.Cancel2.disabled=true;
                                   document.form1.txt_sltypeCode.disabled=true;
                         
                                }
          
                             }
        
                           else
                           {
                                 alert("failed to insert values");
                           }       
         
    
               
          
          
           
                  }
    
            }
    
  }
  

    
    function date()
    {
     var today = new Date()
     var year = today.getYear()
     if(year < 1000)
     {
      year += 1900
     }
     document.form1.date_creation.value=today.getDate() + "/" + (today.getMonth()+1) +  "/" + (year+"");
    }
    
    
    
    
   /* function changeTableContent()
    {
      alert("very good");
      alert(sequence);
      
      var ahc=document.form1.acctHC.value;
      alert(ahc);
      var rid=sequence;      
      var doc=window.opener.document;     
      var row=doc.getElementById(rid);
      row.id=rid;
      var rcells=row.cells;
      rcells.item(1).firstChild.nodeValue=document.form2.txt_sltypeCode.value;
      rcells.item(2).firstChild.nodeValue=document.form2.txt_sldesc.options[document.form2.txt_sldesc.selectedIndex].text;
      closeWindow();
    }*/
    
    
     function closeWindow()
    {
        self.close();
    }



 function popUpDiv(parent,element_id)
    {
      alert("called : " + element_id);
      var pd=document.getElementById(parent);
      pd.style.zIndex=2; 
      var cd=document.getElementById(element_id);
      cd.style.zIndex=1;
      cd.style.left='10px';
      cd.style.top='10px';
      alert("shall i show");
      cd.style.display="block";
    }
    function hideDiv(element_id)
    {
      var cd=document.getElementById(element_id);
      cd.style.display="none";
    }
    
    
   
   
   function popUp(strURL,strType,strHeight,strWidth)
    {
      alert("yes");
      var strOptions="";
      if (strType=="console") strOptions="resizable,height="+strHeight+",width="+strWidth;
      if (strType=="fixed") strOptions="status,height="+strHeight+",width="+strWidth;
      if (strType=="elastic") strOptions="toolbar,menubar,scrollbars,resizable,location,height="+strHeight+",width="+strWidth;
      window.open(strURL, 'newWin', strOptions);
} 
    
function enable_NewSL()
{
         
         //document.form1.finalsubmit.disabled=false;
         document.form1.txt_sldesc.disabled=false;
         document.form1.update.disabled=false;
         document.form1.Cancel2.disabled=false;
         //document.form1.finalsubmit.disabled=true;
 }        


