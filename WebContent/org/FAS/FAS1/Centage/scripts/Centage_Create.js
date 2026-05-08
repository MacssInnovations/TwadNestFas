var seq=0;
 var CatCode = new Array();
 var CatDesc = new Array();
 var CenRate = new Array();
                          
/**
 *  Browser Identification 
 */ 
 
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



/**
 *   Main Function 
 */ 

function  PendingJournals_11() 
{   
      
            var cmbAcc_UnitCode=document.getElementById("cmbAcc_UnitCode").value;
            var cmbOffice_code=document.getElementById("cmbOffice_code").value;
            var txtCB_Year= document.getElementById("txtCB_Year").value;
            var txtCB_Month= document.getElementById("txtCB_Month").value;
            
            var url="../../../../../Centage_Create.kv?Command=PendingJournals&cmbAcc_UnitCode="+cmbAcc_UnitCode
                    +"&cmbOffice_code="+cmbOffice_code+"&txtCB_Year="+txtCB_Year+"&txtCB_Month="+txtCB_Month;
                    
                
                var req=getTransport();
                req.open("GET",url,true); 
                req.onreadystatechange=function()
                {
                   handleResponse_PendingJournals(req);
                }   
                   req.send(null);
        
}




/**
 *  Server Response Checking Function 
 */
function handleResponse_PendingJournals(req)
{  
    if(req.readyState==4)
    {
       if(req.status==200)
        {   
            var baseResponse=req.responseXML.getElementsByTagName("response")[0];
            var tagcommand=baseResponse.getElementsByTagName("command")[0];
            var Command=tagcommand.firstChild.nodeValue;             
            if(Command=="PendingJournals")
            {     
                PendingJournals(baseResponse);
            }
        }
    }
}



/**
 *  Display All Journals in the grid 
 */

function PendingJournals(baseResponse)
{
     var flag=baseResponse.getElementsByTagName("flag")[0].firstChild.nodeValue;
     if(flag=="success")
     {
        
        /**
         *  First Clear the Grid Before displaying all the Details
         */
        var tbody=document.getElementById("grid_body");
        var t=0;
        for(t=tbody.rows.length-1;t>=0;t--)
        {
           tbody.deleteRow(0);
        }
        
        
        /**
         *  Get Voucher Number for finding the length
         */
        
        var items=new Array();    
         
        var option_count=baseResponse.getElementsByTagName("option");  
       
        var voucher_no=baseResponse.getElementsByTagName("voucher_no");
       
        var root = null;
            
        for(var i=0;i<option_count.length;i++)
        {  
           
             root = baseResponse.getElementsByTagName("option")[i];
             
             items[0]=root.getElementsByTagName("voucher_no")[0].firstChild.nodeValue;   
             items[1]=root.getElementsByTagName("voucher_date")[0].firstChild.nodeValue;   
             items[2]=root.getElementsByTagName("account_head_code")[0].firstChild.nodeValue;             
             items[3]=root.getElementsByTagName("project_sl_code")[0].firstChild.nodeValue;   
             items[4]=root.getElementsByTagName("cat_code")[0].firstChild.nodeValue;
             items[5]=root.getElementsByTagName("category_desc")[0].firstChild.nodeValue;             
             items[6]=root.getElementsByTagName("centage_rate")[0].firstChild.nodeValue;
             items[7]=root.getElementsByTagName("dr_account_head_code")[0].firstChild.nodeValue;
             
             items[8]= Math.round( ((parseFloat(root.getElementsByTagName("centage_amount")[0].firstChild.nodeValue)/ 100 ) * parseFloat(items[6]))  *Math.pow(10,2))/Math.pow(10,2);
           
             items[9]=root.getElementsByTagName("cr_account_head_code")[0].firstChild.nodeValue;             
             items[10]='Centage Created';          
             items[11]=root.getElementsByTagName("sub_ledger_type_code")[0].firstChild.nodeValue;                     
             items[12]=items[8];             
             items[13]= root.getElementsByTagName("centage_amount")[0].firstChild.nodeValue;
             
             
             items[14]= root.getElementsByTagName("account_head_code_desc")[0].firstChild.nodeValue;
             items[15]= root.getElementsByTagName("sub_ledger_type_code_desc")[0].firstChild.nodeValue;
             items[16]= root.getElementsByTagName("project_sl_code_desc")[0].firstChild.nodeValue;
             items[17]= root.getElementsByTagName("dr_account_head_code_desc")[0].firstChild.nodeValue;
             items[18]= root.getElementsByTagName("cr_account_head_code_desc")[0].firstChild.nodeValue;
             
            
              
              var mycurrent_row=document.createElement("TR");
              
                     mycurrent_row.id=seq;                               
                
                     cell2=document.createElement("TD");
                    
                     var sel="";            
                     if (window.navigator.appName.toLowerCase().indexOf("netscape") == -1)
                     {
                       // sel=document.createElement("<INPUT type='checkbox' name='sel' id='sel' checked value="+items[0]+" onclick='cal_amount("+seq+")'>");
                          sel=document.createElement("<INPUT type='checkbox' name='sel' id='sel' checked value='"+seq+"'/>" );                       
                     }
                     else
                     {    
                       sel=document.createElement("input");     
                       sel.type="checkbox";             
                       sel.name="sel";
                       sel.id="sel";
                       sel.checked=true;                    
                       sel.value=seq;
                     }
                   cell2.appendChild(sel);
                   mycurrent_row.appendChild(cell2);
                       
                       
                var cell2;
                
                      cell2=document.createElement("TD");
                          var voucher_no=document.createElement("input");
                          voucher_no.type="hidden";
                          voucher_no.name="voucher_no";
                          voucher_no.value=items[0];
                          cell2.appendChild(voucher_no);
                          var currentText=document.createTextNode(items[0]);
                          cell2.appendChild(currentText);
                          mycurrent_row.appendChild(cell2);                            
                 
                     cell2=document.createElement("TD");
                          var voucher_date=document.createElement("input");
                          voucher_date.type="hidden";
                          voucher_date.name="voucher_date";
                          voucher_date.value=items[1];
                          cell2.appendChild(voucher_date);
                          var currentText=document.createTextNode(items[1]);
                          cell2.appendChild(currentText);
                          cell2.style.color="green";
                          mycurrent_row.appendChild(cell2);
                           
                     cell2=document.createElement("TD");
                          var account_head_code=document.createElement("input");
                          account_head_code.type="hidden";
                          account_head_code.name="account_head_code";
                          account_head_code.value=items[2];
                          cell2.appendChild(account_head_code); 
                          var currentText=document.createTextNode(items[2]+"--"+items[14]);
                          cell2.appendChild(currentText);
                          cell2.style.fontSize="12px";
                          mycurrent_row.appendChild(cell2);
                        
                     cell2=document.createElement("TD");
                          var project_sl_code=document.createElement("input");
                          project_sl_code.type="hidden";
                          project_sl_code.name="project_sl_code";
                          project_sl_code.value=items[3];
                          cell2.appendChild(project_sl_code);
                          var currentText=document.createTextNode(items[16]);                          
                          cell2.appendChild(currentText);
                          cell2.style.fontSize="12px";
                          cell2.style.color="green";
                          mycurrent_row.appendChild(cell2);
                     
                 /*  cell2=document.createElement("TD");
                          var cat_code=document.createElement("input");
                          cat_code.type="hidden";
                          cat_code.name="cat_code";
                          cat_code.value=items[4];
                          cell2.appendChild(cat_code);
                          var currentText=document.createTextNode(items[4]);
                          cell2.appendChild(currentText);
                          mycurrent_row.appendChild(cell2);
                  */
                  
                  
                          /**
                           *  Dynamic Combo Creation and Loading 
                           */ 
                          
                          cell2=document.createElement("TD");
                          cell2.style.textAlign='center';  
                          var cmbCategoryCode;
                          
                          if (window.navigator.appName.toLowerCase().indexOf("netscape") == -1)
                          { try
                            {
                              alert("I am in IE");
                              cmbCategoryCode =  document.createElement("<select name='cmbCategoryCode' id='cmbCategoryCode"+seq+"' onchange='cal_centage("+seq+","+items[4]+","+items[13]+")' >");
                            }catch( e ) { alert(e.description) }
                          }
                          else
                          {
                             cmbCategoryCode=document.createElement("select");                        
                             cmbCategoryCode.id="cmbCategoryCode"+seq;
                             cmbCategoryCode.name="cmbCategoryCode";                       
                             cmbCategoryCode.setAttribute('onchange','cal_centage('+seq+','+items[4]+','+items[13]+')');        
                          }
                          
                          var cmbCategoryCodeObj = baseResponse.getElementsByTagName("mst_option");
                          var option11=document.createElement("option");    
                              option11.value="";  
                              option11.text="--Select--";
                              
                         try
                           {
                              cmbCategoryCode.add(option11);
                           }
                        catch(e)
                           {
                               cmbCategoryCode.add(option11,null);
                           }                           
                          for(var y=0;y<cmbCategoryCodeObj.length;y++)
                          {
                          
                              CatCode[y]=cmbCategoryCodeObj[y].getElementsByTagName("category_code")[0].firstChild.nodeValue;
                              CatDesc[y]=cmbCategoryCodeObj[y].getElementsByTagName("category_desc")[0].firstChild.nodeValue;
                              CenRate[y]=cmbCategoryCodeObj[y].getElementsByTagName("centage_rate")[0].firstChild.nodeValue;
                              
                              var option11=document.createElement("option");    
                              
                              if (CatCode[y] == items[4])  option11.selected=true;
                                                                                        
                              option11.value=CatCode[y];  
                              option11.text=CatCode[y];
                              
                            
                             try
                               {
                                   cmbCategoryCode.add(option11);
                               }
                             catch(e)
                               {
                                    cmbCategoryCode.add(option11,null);
                               }
                               
                         }                         
                         cell2.appendChild(cmbCategoryCode);                                                    
                         mycurrent_row.appendChild(cell2);   
                          
                  
                  
                  
                    cell2=document.createElement("TD");
                          var category_desc=document.createElement("input");
                          category_desc.type="hidden";
                          category_desc.name="category_desc";
                          category_desc.id="category_desc"+seq;
                          category_desc.value=items[5];
                          cell2.appendChild(category_desc);
                          var currentText=document.createTextNode(items[5]);                          
                          cell2.appendChild(currentText);
                          cell2.style.color="green";
                          mycurrent_row.appendChild(cell2);
                          
                                
                    cell2=document.createElement("TD");
                          cell2.style.textAlign='right';
                          var centage_rate=document.createElement("input");
                          centage_rate.type="hidden";
                          centage_rate.name="centage_rate";
                          centage_rate.id="centage_rate"+seq;
                          centage_rate.value=items[6];
                          cell2.appendChild(centage_rate);
                          var currentText=document.createTextNode(items[6]);
                          cell2.appendChild(currentText);
                          mycurrent_row.appendChild(cell2);
                          
                    cell2=document.createElement("TD");
                          var dr_account_head_code=document.createElement("input");
                          dr_account_head_code.type="hidden";
                          dr_account_head_code.name="dr_account_head_code";
                          dr_account_head_code.value=items[7];
                          cell2.appendChild(dr_account_head_code);
                          var currentText=document.createTextNode(items[7]+"--"+items[17]);                          
                          cell2.appendChild(currentText);
                          cell2.style.fontSize="12px";
                          cell2.style.color="green";
                          mycurrent_row.appendChild(cell2);
                          
                     cell2=document.createElement("TD");
                          cell2.style.textAlign='right';
                          var centage_amount=document.createElement("input");
                          centage_amount.type="hidden";
                          centage_amount.name="centage_amount";                          
                          centage_amount.id="centage_amount"+seq;                          
                          centage_amount.value=items[8];
                          cell2.appendChild(centage_amount);
                          var currentText=document.createTextNode(items[8]);
                          cell2.appendChild(currentText);
                          mycurrent_row.appendChild(cell2);
                          
                          
                     cell2=document.createElement("TD");
                          var cr_account_head_code=document.createElement("input");
                          cr_account_head_code.type="hidden";
                          cr_account_head_code.name="cr_account_head_code";
                          cr_account_head_code.value=items[9];
                          cell2.appendChild(cr_account_head_code);
                          var currentText=document.createTextNode(items[9]+"--"+items[18]);
                          cell2.appendChild(currentText);
                          cell2.style.fontSize="12px";
                          cell2.style.color="green";
                          mycurrent_row.appendChild(cell2);
                       
                     cell2=document.createElement("TD");
                          cell2.style.textAlign='right';
                          var centage_amount_2=document.createElement("input");
                          centage_amount_2.type="hidden";
                          centage_amount_2.name="centage_amount_2";                          
                          centage_amount_2.id="centage_amount_2"+seq;                                                       
                          centage_amount_2.value=items[12];
                          cell2.appendChild(centage_amount_2);
                          var currentText=document.createTextNode(items[12]);
                          cell2.appendChild(currentText);
                          mycurrent_row.appendChild(cell2);    
                          
                      cell2=document.createElement("TD");
                          cell2.style.textAlign='center';
                          var remarks=document.createElement("input");
                          remarks.type="hidden";
                          remarks.name="remarks";
                          remarks.id="remarks"+seq;    
                          remarks.value=items[10];
                          cell2.appendChild(remarks);
                          var currentText=document.createTextNode(items[10]);
                          cell2.appendChild(currentText);
                          cell2.style.fontSize="12px";
                          cell2.style.color="green";
                          mycurrent_row.appendChild(cell2);    
                          
                          
                         /**
                          *  Hidden Field. Store Sub Ledger Type Code of Transaction Table
                          */
                          
                         cell2=document.createElement("TD");
                         cell2.style.textAlign='center';
                          
                          var remarks=document.createElement("input");
                          remarks.type="hidden";
                          remarks.name="sub_ledger_type_code";
                          remarks.value=items[11];
                          
                          cell2.appendChild(remarks);
                          var currentText=document.createTextNode(items[15]);
                          cell2.appendChild(currentText);
                          mycurrent_row.appendChild(cell2);                            
                          
                         seq=parseInt(seq)+1;
                          
                          
              tbody.appendChild(mycurrent_row);
           
        }
        
        
     }
     else if(flag=="failure")
     {
        var tbody=document.getElementById("grid_body");
        var t=0;
        for(t=tbody.rows.length-1;t>=0;t--)
        {
           tbody.deleteRow(0);
        }
        
        alert("No Journals Found for Creating Centage");
     }
}



function cal_centage(getId, Org_cat_code, amt)
{
    var index=document.getElementById('cmbCategoryCode'+getId).selectedIndex;    
          
    var getrow=document.getElementById(getId);    
    var get_cells=getrow.cells;    
    
    get_cells.item(6).lastChild.nodeValue=CatDesc[index-1];  
    get_cells.item(7).lastChild.nodeValue=CenRate[index-1];  
    
    var temp =( ( parseFloat(amt) / 100) * parseFloat(CenRate[index-1]) ) ;
    
    
    get_cells.item(9).lastChild.nodeValue = Math.round(temp*Math.pow(10,2))/Math.pow(10,2);
    get_cells.item(11).lastChild.nodeValue = Math.round(temp*Math.pow(10,2))/Math.pow(10,2);
    
    document.getElementById('category_desc'+getId).value=CatDesc[index-1];
    document.getElementById('centage_rate'+getId).value=CenRate[index-1];  
    
    document.getElementById('centage_amount'+getId).value=Math.round(temp*Math.pow(10,2))/Math.pow(10,2);
    document.getElementById('centage_amount_2'+getId).value=Math.round(temp*Math.pow(10,2))/Math.pow(10,2);
      
    document.getElementById('remarks'+getId).value= 'Orginal Category '+Org_cat_code+' is changed to '+ document.getElementById('cmbCategoryCode'+getId).value;  
    get_cells.item(12).lastChild.nodeValue ='Orginal Category '+Org_cat_code+' is changed to '+ document.getElementById('cmbCategoryCode'+getId).value; 

}





function btSubmit()
{   
    var url1="../../../../../Centage_Create.kv";    
     
    document.getElementById("TotalRecords").value=seq;  
    
    document.frmIndependentCentageACHeads.action=url1;
    document.frmIndependentCentageACHeads.method="post"; 
    document.frmIndependentCentageACHeads.submit();    
}
     
