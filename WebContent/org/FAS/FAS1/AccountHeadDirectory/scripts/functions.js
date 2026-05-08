var sequence=0;
var currentlyEditing=0;

//TO APPEND A ROW IN THE GRID
function addSL()
{
         var flag=checkForRedundancy(document.form1.txt_sltypeCode.value);     
         if(flag)
         {
              var strAHCode=document.form1.acct_head_code.value;
              var strSubCode=document.form1.txt_sltypeCode.value;
              var strSubType=document.form1.txt_sldesc.options(document.form1.txt_sldesc.selectedIndex).text;
             
              var items=new Array();
              items[0]=strSubCode;
              items[1]=strSubType;
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
                           LoadFromTable(this);
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
              document.form1.txt_sldesc.disabled=true;
              document.form1.update.disabled=true;
              document.form1.txt_sltypeCode.disabled=true;
        }
        else
        {
          alert("Subledger code already exists...");
        }
         
 }
          
   
   
   
   
   //TO LOAD VALUES FROM THE GRID TO THE POPUP WINDOW VIZ., LOADSUBLEDGER.JSP
   function LoadFromTable(id)
                  {    
                       //alert("inside edit");
                       rid=id.value;
                       
                       var r=document.getElementById(rid);   
                       var rcells=r.cells;
                       var SubLegTypeCode=rcells.item(1).firstChild.value;
                       
                       var SubLegTypeDesc=rcells.item(2).firstChild.value;
                       
                       acctHeadCode=document.form1.acct_head_code.value;
                       var url="../jsps/LoadSubLedger.jsp?rowid=" + rid + "&SLTCode=" + SubLegTypeCode + "&SLTDesc=" + SubLegTypeDesc+"&AHC="+acctHeadCode;
                       var  my_window= window.open(url,"mywindow2","status=1,height=200,width=750"); 
                       my_window.moveTo(100,250); 
                  }
                 
                 
                 
                 
       
       //TO DELETE THE ROW FROM THE GRID
                 
      function deleteSL(id) 
                  {
                     
                      rid=id.value;
                      var tbody=document.getElementById("tblList"); 
                      var r=document.getElementById(rid); 
                      var ri=r.sectionRowIndex;
                      tbody.deleteRow(ri);   
                  
                  }


function checkForRedundancy(sc)
{
  try
  {
      var tbody=document.getElementById("tblList");
      var rows=tbody.getElementsByTagName("tr");
      
      if(rows.length<=0)
      {
        return true;
      }
        var i;
        var found=false;
        for(i=0;i<rows.length;i++)
        {
            var cells=rows[i].cells;
            if(cells.item(1).firstChild.value==sc)
            {
              found=true;
              break;
            }
        }
        if(found==true)
        {
          return false;
        }
        else
        {
          return true;
        }        
  }
  catch(e)
  {
  alert(e);
  }
  return false;
}