
var Sel_Opt="REGION";

/**
 *  Browser Indentification 
 */ 

function getTransport()
{
var req=false;
try
{
req=new ActiveXObject("Msxml2.XMLHTTP");
}catch(e1)
 {
    try{
    req=new ActiveXObject("Microsoft.XMLHTTP");
    }
    catch(e2)
    {
    req=false;
    }
 }
    if(!req && typeof XMLHttpRequest!='undefined')
        {
        req=new XMLHttpRequest();
        }
   return req;
}    


/**
 *  NEW - Main Function
 */ 
function loadAll()
{ 
  var dt=new Date();
  var dummy=dt.getTime();
  var url="../../../../../../Tree_Menu_Generation.kv";
  var req=getTransport();  
  req.open("GET",url+"?dummy="+dummy,true);       
  req.onreadystatechange=function()
  {
    procResp(req);
  }  
  req.send(null);
}




/**
 *  NEW - Response Handling
 */ 

function procResp(req)
{
try{
 if(req.readyState==4)
 {

  if(req.status==200)
  { 
     var baseResponse=req.responseXML.getElementsByTagName("response")[0];
     var tagcommand=baseResponse.getElementsByTagName("command")[0];
     var Command=tagcommand.firstChild.nodeValue;     
        if(Command=="ListAllUnits")
            {
                DrawDrillDownTree(baseResponse);
            }                     
   }
   
  } 
 }catch(e){ alert("Error "+e.description);    return false;   }
}


/**
 *   
 */
 
function showList(cur)
{
  var sub="";
  if ( cur.id=="HO_img") 
  {
   // var items=cur.parentNode.parentNode.childNodes[3];    
      var items=document.getElementById("TD_HO");
  }   
   else
   {
      var items=document.getElementById("TD_DrillDown");
      sub=document.getElementsByName("Reg_img");
   }   
   
      var img_string=cur.src;
      var tot_length =(cur.src).length;
      var img_name = (cur.src).substring(tot_length-9, tot_length);
      if(img_name=="plus1.GIF") 
        {
            items.style.display="block"; 
            cur.src="../../../../../../images/minu1.GIF";   
        }
      else if(img_name=="minu1.GIF") 
       {
           items.style.display="none";
           cur.src="../../../../../../images/plus1.GIF";  
           if(sub!="")
           for(var i=0;i<sub.length;i++)
           hide(sub[i]);

 /*           var sub=items.firstChild.childNodes;
  alert("HIDE ?? ");
   alert(sub.length);
   hide(sub[0].firstChild);
          // sub=cur.parentNode.parentNode.lastChild.firstChild.childNodes;
           for(var i=0;i<sub.length;i++)
           {alert(sub[i].firstChild.id);hide(sub[i].firstChild);}
*/
           
       }
}



/**
 *  List Head Office 
 */ 
function DrawDrillDownTree(baseResponse)
{  
      var flag=baseResponse.getElementsByTagName("flag")[0].firstChild.nodeValue;
      if(flag=="Success")
      {     
        
        /** Get tbody object */
        var tbody_HO=document.getElementById("TD_HO");    //("dtable");
        var tbody_drill=document.getElementById("TD_DrillDown");
        
        
        /** Get Head Office Count */
        var count_HO = baseResponse.getElementsByTagName("count_HO");     
        /** Get Region Offices Total Count */
        var count_region = baseResponse.getElementsByTagName("count_region");     
        
        var seq=0;
        
         /** Display All the Units in Head Office */         
         var HO_block=document.createElement("UL");
         
         //HO_block.type="none";

           //------------------------------------------------------------------------ Head Office Display Start    
           for(var x=0;x<count_HO.length;x++)
           {  

              var cell=document.createElement("LI");
     
              var root = baseResponse.getElementsByTagName("count_HO")[x];
              var accounting_unit_id=root.getElementsByTagName("HO_unit")[0].firstChild.nodeValue;
              var accounting_unit_name=root.getElementsByTagName("HO_unit_name")[0].firstChild.nodeValue;
              
              var chek="";
              if (window.navigator.appName.toLowerCase().indexOf("netscape") == -1)
              {
                   chek=document.createElement("<INPUT type='checkbox' name='HO_UNITS' id='HO_UNITS'>");
              }
              else
              { 
                   chek=document.createElement("input");
                   chek.type="checkbox";
                   chek.name="HO_UNITS";
                   chek.id="HO_UNITS";
                   chek.value=accounting_unit_id;                                            
               }
        //alert("e");       
                  var accounting_unit_name=document.createTextNode(accounting_unit_name);                 
                  cell.appendChild(chek);
                  cell.appendChild(accounting_unit_name);
                  HO_block.appendChild(cell);
                  tbody_HO.appendChild(HO_block);         
            }
           //------------------------------------------------------------------------ Head Office Display End   
             
             
         //alert("mid");
             
         /** Display All other Units - Display Regions */
         
           var drill_block=document.createElement("UL");
          // drill_block.type="none";
           
           for(var x=0;x<count_region.length;x++)
           {  
              
              var cell=document.createElement("LI");           
              var root_rn = baseResponse.getElementsByTagName("count_region")[x];
              var accounting_unit_id=root_rn.getElementsByTagName("Region_unit")[0].firstChild.nodeValue;
              var accounting_unit_name=root_rn.getElementsByTagName("Region_unit_name")[0].firstChild.nodeValue;
              
            //  -----------------------------------------
              var plus_img=""; 
              if (window.navigator.appName.toLowerCase().indexOf("netscape") == -1)
              { 
                  plus_img=document.createElement("<img src='../../../../../../images/plus1.GIF' onclick='drillDown(this)' name='Reg_img'>");
              }
              else
              { 
                  plus_img=document.createElement("img");
                  plus_img.src="../../../../../../images/plus1.GIF";
                  plus_img.id="img_"+accounting_unit_id;                                            
                  plus_img.setAttribute('onclick','drillDown(this)');
                  plus_img.name="Reg_img";
               }  
            //  ---------------------------------------
              
              var chek="";
              
              if (window.navigator.appName.toLowerCase().indexOf("netscape") == -1)
              {
                   chek=document.createElement("<INPUT type='checkbox' name='REGION' ID='REGION' size='10' onclick='RecursiveCheck(this)' >");
              }
              else
              { 
                   chek=document.createElement("input");
                   chek.type="checkbox";
                   chek.name="REGION";
                   chek.id="REGION";
                   chek.value=accounting_unit_id; 
                   chek.setAttribute('onclick','RecursiveCheck(this)');
              }
                 
                  var accounting_unit_name=document.createTextNode(accounting_unit_name);
                  cell.appendChild(plus_img);
                  cell.appendChild(chek);
                  cell.appendChild(accounting_unit_name);
                  
                  
                  
                       /* Display Circle */
                    
                       var div_circle= document.createElement("div");
                       div_circle.id="circle_div";
                       div_circle.style.display="none";              
                       var count_circle = root_rn.getElementsByTagName("count_circle");      // test try               
                       var circle_block=document.createElement("UL");
                       //circle_block.type="none";                  
                      
                       for(var y=0;y<count_circle.length;y++)
                       {  
                       
                         var cell_CL=document.createElement("LI");
                         //cell_CL.type="none";                  
                         var root_cl = root_rn.getElementsByTagName("count_circle")[y];
                         
                         var accounting_unit_id=root_cl.getElementsByTagName("Circle_unit")[0].firstChild.nodeValue;
                         var accounting_unit_name=root_cl.getElementsByTagName("Circle_unit_name")[0].firstChild.nodeValue;
              
                        // ------------------------------
                         var plus_img=""; 
                         if (window.navigator.appName.toLowerCase().indexOf("netscape") == -1)
                         { 
                             plus_img=document.createElement("<img src='../../../../../../images/plus1.GIF' onclick='drillDown(this)' >");
                         }
                         else
                         { 
                             plus_img=document.createElement("img");
                             plus_img.src="../../../../../../images/plus1.GIF";
                             plus_img.id="img_"+accounting_unit_id;                                            
                             plus_img.setAttribute('onclick','drillDown(this)');
                         }
                        // -----------------------------
              
                         var chek_CL="";
                         if (window.navigator.appName.toLowerCase().indexOf("netscape") == -1)
                         {
                          chek_CL=document.createElement("<INPUT type='checkbox' name='CIRCLE' id='CIRCLE' onclick='RecursiveCheck(this)'>");
                         }
                         else
                         { 
                          chek_CL=document.createElement("input");
                          chek_CL.type="checkbox";
                          chek_CL.name="CIRCLE";
                          chek_CL.id="CIRCLE";
                          chek_CL.value=accounting_unit_id;     
                          chek_CL.setAttribute('onclick','RecursiveCheck(this)');
                         }
                         
                         var accounting_unit_name=document.createTextNode(accounting_unit_name);
                         cell_CL.appendChild(plus_img);
                         cell_CL.appendChild(chek_CL);                         
                         cell_CL.appendChild(accounting_unit_name);                                     
                        
                        
                                // if ( document.getElementById("Sel_Opt_Unit").checked == true ) 
                                 {
                                     /** Display Units Under Circle */   
                                     var div_unit= document.createElement("div");
                                     div_unit.id="unit_div";
                                     div_unit.style.display="none";
                                     var unit_block=document.createElement("UL");
                                     //unit_block.type="none";
                                     var count_unit = root_cl.getElementsByTagName("count_unit");     
                                 
                                     for(var z=0;z<count_unit.length;z++)
                                     {  
                                      var cell_DN=document.createElement("LI");
                                      var root_dn = root_cl.getElementsByTagName("count_unit")[z];
                                      var accounting_unit_id=root_dn.getElementsByTagName("Unit_unit")[0].firstChild.nodeValue;
                                      var accounting_unit_name=root_dn.getElementsByTagName("Unit_unit_name")[0].firstChild.nodeValue;
                                      var chek_DN="";
                                      if (window.navigator.appName.toLowerCase().indexOf("netscape") == -1)
                                      {
                                       chek_DN=document.createElement("<INPUT type='checkbox' name='UNIT' id='UNIT'>");
                                      }
                                      else
                                      { 
                                       chek_DN=document.createElement("input");
                                       chek_DN.type="checkbox";
                                       chek_DN.name="UNIT";
                                       chek_DN.id="UNIT";
                                       chek_DN.value=accounting_unit_id;                                            
                                       // chek_DN.setAttribute('onclick',callserver(circle,null));
                                      }
                                      var accounting_unit_name=document.createTextNode(accounting_unit_name);
                                      cell_DN.appendChild(chek_DN);
                                      cell_DN.appendChild(accounting_unit_name);
                                      unit_block.appendChild(cell_DN);   
                                     } // Unit loop ends here..
                                     
                                    div_unit.appendChild(unit_block);                                    
                                    cell_CL.appendChild(div_unit);                             
                                    circle_block.appendChild(cell_CL);               
                                }
                                
                                
                        } // Circle loop ends here..
                    
             div_circle.appendChild(circle_block);
             cell.appendChild(div_circle); 
             drill_block.appendChild(cell);
             tbody_drill.appendChild(drill_block);
            //alert("end");
            
          } // Region loop ends here..

    }  // END if(flag=="Success")
    

}




function drillDown(cur)
{
   var img_string=cur.src;
   var tot_length =(cur.src).length;
   var img_name = (cur.src).substring(tot_length-9, tot_length);
   
   // TRY THIS!! ---  var img_name = (cur.src).substring(lastIndexOf("/",tot_length));
 
   if(img_name=="plus1.GIF") 
   { 
     var items=cur.parentNode.childNodes[3];
     items.style.display="block";
     cur.src="../../../../../../images/minu1.GIF";
   }
  
   if(img_name=="minu1.GIF") 
   {    
    hide(cur);
   } 
}



function hide(cur)
{
   if(cur.parentNode.childNodes.length<3)  
   return;
   
   var items=cur.parentNode.childNodes[3];
   items.style.display="none";
   cur.src="../../../../../../images/plus1.GIF";
   
   var sub_items=items.firstChild.childNodes;
   for(var i=0;i<sub_items.length;i++)
   hide(sub_items[i].firstChild);
}

/**
 *  Select All units
 */
function checkFirstLevel(cur)
{
    if(cur.id=="HO_ONLY")
    var chld=document.getElementsByName("HO_UNITS");
    else if(cur.id=="All_Units_Except_HO")
    var chld=document.getElementsByName("REGION");
  // alert(chld.length);
    for(var i=0;i<chld.length;i++)
    {
      chld[i].checked=cur.checked;
    //  if(chld.name=="REGION")
      RecursiveCheck(chld[i]);
    }
}

function RecursiveCheck(cur)
{
 if(cur.parentNode.childNodes.length==2)
 return;
 else
 {
   var chld=cur.parentNode.lastChild.firstChild.childNodes;
   for(var i=0;i<chld.length;i++)
   {
     var chek=chld[i].childNodes[1];
     if(chek.parentNode.childNodes.length==2)
     chek=chld[i].childNodes[0];
     chek.checked=cur.checked;
     RecursiveCheck(chek);
   }
  }
}