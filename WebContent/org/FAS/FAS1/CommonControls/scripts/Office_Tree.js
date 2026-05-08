function constructTree()
{
   
   var t_body=document.getElementById("OfficeTree");
   
   /** Create Table Row */
   var curr_row=document.createElement("TR");
   curr_row.id="kone";
   
   /** Create Table column */
   var col = document.createElement("TD");
   
   var sel="Kong";
   
   if (window.navigator.appName.toLowerCase().indexOf("netscape") == -1)
    {                  
    
      sel=document.createElement("<INPUT type='checkbox' name='sel' id='sel' checked value="+items[0]+" onclick='cal_amount("+seq+")'>");
    }
   else
    {    
      sel=document.createElement("input");     // serial number generation
      sel.type="checkbox";             
      sel.name="sel";
      sel.id="sel";
      sel.checked=true;
      // sel.setAttribute('onclick','cal_amount('+seq+')');
      sel.value="T1";                          
    }
    col.appendChild(sel);
    
    var sel1="Mam";    
        
    sel1=document.createElement("ul");
   // sel2=document.createElement("li Coffee /li>");
   // sel3=document.createElement("<li> Tea  </li>");
    sel4=document.createElement("ul");
    
    curr_row.appendChild(sel1);       
   // curr_row.appendChild(sel2);       
   // curr_row.appendChild(sel3);       
    curr_row.appendChild(sel4);       
    
    curr_row.appendChild(col);       
    t_body.appendChild(curr_row);
}