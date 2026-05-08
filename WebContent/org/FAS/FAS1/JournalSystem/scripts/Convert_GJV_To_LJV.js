/**
 *  Variables Declaration
 */
 
var service;
var __pagination=11;
var destid;
var totalblock=0;

var Ucode;
var Offid;
var txtCB_Year;
var txtCB_Month;
var Voucher_list_SL;

var seq=0;

var CatCode = new Array();
var CatDesc = new Array();

/**
 * Browser Indentification 
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
 *  Show Transaction Details 
 */
function Show(unitcode,offid,yr,mon,recNo)
{
    if (Voucher_list_SL && Voucher_list_SL.open && !Voucher_list_SL.closed) 
    {
       Voucher_list_SL.resizeTo(500,500);
       Voucher_list_SL.moveTo(250,250); 
       Voucher_list_SL.focus();
    }
    else
    {
        Voucher_list_SL=null
    }
    Voucher_list_SL= window.open("../../../../../org/FAS/FAS1/JournalSystem/jsps/Convert_GJV_To_LJV_SL.jsp?cmbAcc_UnitCode="+unitcode+"&cmbOffice_code="+offid+"&yr="+yr+"&mon="+mon+"&recNo="+recNo,"ReceiptDateSearch","status=1,height=500,width=500,resizable=YES, scrollbars=yes"); 
    Voucher_list_SL.moveTo(250,250);  
    Voucher_list_SL.focus();    
}




window.onunload=function()
{
   if (Voucher_list_SL && Voucher_list_SL.open && !Voucher_list_SL.closed) Voucher_list_SL.close();
}




/**
 *  Main Function 
 */ 
function doFunction(Command,param)
{  
	
	
        if(document.getElementById("cmbAcc_UnitCode"))     var cmbAcc_UnitCode = document.getElementById("cmbAcc_UnitCode").value    
        else{ alert("unitcode undefined");    return false;   }
        if(document.getElementById("cmbOffice_code"))     var cmbOffice_code=document.getElementById("cmbOffice_code").value
        else{ alert("office code undefined");    return false;   }
        if(document.getElementById("cmbStatus"))   var cmbStatus=document.getElementById("cmbStatus").value;
        else{ alert("unitcode undefined");    return false;   }
        if(Command=="searchByMonth")
        {  
            var txtCB_Year=document.getElementById("txtCB_Year").value;
            var txtCB_Month=document.getElementById("txtCB_Month").value;
            
            
            
           
            if(txtCB_Year.length!=0 && txtCB_Month.length!=0)
            {
            	if(txtCB_Year==2011)
            		{
            		
            		if(txtCB_Month<=5)
            			{
                var txtCreat_By_Module='GJV';
                var url="../../../../../Journal_ListAll.view?Command=searchByMonth&cmbAcc_UnitCode="+cmbAcc_UnitCode+
                "&cmbOffice_code="+cmbOffice_code+"&txtCB_Year="+txtCB_Year+"&txtCB_Month="+txtCB_Month+"&txtCreat_By_Module="+txtCreat_By_Module+"&cmbStatus="+cmbStatus+"&type=1";                
                var req=getTransport();
                req.open("GET",url,true); 
                req.onreadystatechange=function()
                {
                   handleResponse(req);
                } ;  
                        req.send(null);
            			}else{
            				alert('CashBook Year & Month Should be Less than June 2011 ...');
            			}
            		}else if(txtCB_Year<2011){

                        var txtCreat_By_Module='GJV';
                        var url="../../../../../Journal_ListAll.view?Command=searchByMonth&cmbAcc_UnitCode="+cmbAcc_UnitCode+
                        "&cmbOffice_code="+cmbOffice_code+"&txtCB_Year="+txtCB_Year+"&txtCB_Month="+txtCB_Month+"&txtCreat_By_Module="+txtCreat_By_Module+"&cmbStatus="+cmbStatus+"&type=1";                
                        var req=getTransport();
                        req.open("GET",url,true); 
                        req.onreadystatechange=function()
                        {
                           handleResponse(req);
                        }  ; 
                                req.send(null);
                    			
            		}else{
            			alert('CashBook Year & Month Should be Less than June 2011 ...');
            		}
            }
        }               
}




/**
 *   Process Respond Which is sending by Server 
 */
function handleResponse(req)
{ 
    
    
    if(req.readyState==4)
    {
    
       
        if(req.status==200)
        {  
    
          
            var baseResponse=req.responseXML.getElementsByTagName("response")[0];
            var tagcommand=baseResponse.getElementsByTagName("command")[0];
            var Command=tagcommand.firstChild.nodeValue;
             
            if(Command=="searchByMonth")
            {
                loadTable(baseResponse);
            }            
        }
    }
}





/**
 *  Load General Journal Voucher Details 
 */
function loadTable(baseResponse)
{
    
                var flag=baseResponse.getElementsByTagName("flag")[0].firstChild.nodeValue;
                if(flag=="success")
                {
                    
                        Ucode=baseResponse.getElementsByTagName("Ucode")[0].firstChild.nodeValue;
                        Offid=baseResponse.getElementsByTagName("Offid")[0].firstChild.nodeValue;
                        txtCB_Year=baseResponse.getElementsByTagName("txtCB_Year")[0].firstChild.nodeValue;
                        txtCB_Month=baseResponse.getElementsByTagName("txtCB_Month")[0].firstChild.nodeValue;
                       
                     /**
                      *  First Clear the Grid Before displaying all the Details
                      */
                      
                        if ( document.getElementById("tbody"))
                        { 
                           var tbody=document.getElementById("tbody");
                           var t=0;
                            for(t=tbody.rows.length-1;t>=0;t--)
                            {
                                tbody.deleteRow(0);
                            }
        
                        }
                        
                        
        
                       /**
                        *  Get Voucher Number for finding the length
                        */
        
                        var items=new Array();    
                        
                        option_count = baseResponse.getElementsByTagName("leng");
                        
                                               

                        for(var i=0;i<option_count.length;i++)
                        { 
                        
                          root = baseResponse.getElementsByTagName("leng")[i];
                          
                          items[0]=root.getElementsByTagName("no")[0].firstChild.nodeValue;
                          items[1]=root.getElementsByTagName("Dateof")[0].firstChild.nodeValue;
                          items[2]=root.getElementsByTagName("typeof")[0].firstChild.nodeValue;
                          items[3]=root.getElementsByTagName("Remak")[0].firstChild.nodeValue;
                          items[4]=root.getElementsByTagName("Tot_Amt")[0].firstChild.nodeValue;                          
                          items[5]=root.getElementsByTagName("typecode")[0].firstChild.nodeValue;
                          
                          
                          
                          seq=parseInt(seq)+1;
              
                          var mycurrent_row=document.createElement("TR");
                          mycurrent_row.id=seq;                               
                
                          cell2=document.createElement("TD");
                    
                    
                          var sel="";    
                          
                          
                          if (window.navigator.appName.toLowerCase().indexOf("netscape") == -1)
                          {
                              sel=document.createElement("<INPUT type='checkbox' name='sel' id='sel' checked value='"+items[0]+"' />");                       
                          }
                          
                          else
                          {    
                             sel=document.createElement("input");     
                             sel.type="checkbox";             
                             sel.name="sel";
                             sel.id="sel";
                             //sel.checked=true;
                             sel.value=items[0];                          
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
                          mycurrent_row.appendChild(cell2);
                           
                       cell2=document.createElement("TD");
                          var Journal_Type=document.createElement("input");
                          Journal_Type.type="hidden";
                          Journal_Type.name="Journal_Type";
                          Journal_Type.value=items[5];
                          cell2.appendChild(Journal_Type); 
                          var currentText=document.createTextNode(items[2]);
                          cell2.appendChild(currentText);
                          mycurrent_row.appendChild(cell2);
                          
                          
                          /**
                           *  Dynamic Combo Creation and Loading 
                           */ 
                          
                          cell2=document.createElement("TD");
                          cell2.style.textAlign='center';                            
                          var cmbCategoryCode=document.createElement("select");                        
                          cmbCategoryCode.id="cmbCategoryCode"+seq;
                          cmbCategoryCode.name="cmbCategoryCode";                                                
                          cmbCategoryCode.setAttribute('onchange','Change_SLTYPE('+seq+')');        
                          var cmbCategoryCodeObj = baseResponse.getElementsByTagName("leng_2");
                          if(cmbCategoryCodeObj.length > 0) {                          
                          var option11=document.createElement("option");    
                              option11.value="";  
                              option11.text="--Select Voucher Type --";
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
                          
                              CatCode[y]=cmbCategoryCodeObj[y].getElementsByTagName("journal_type_code")[0].firstChild.nodeValue;
                              CatDesc[y]=cmbCategoryCodeObj[y].getElementsByTagName("journal_type_desc")[0].firstChild.nodeValue;
                              
                              var option11=document.createElement("option");    
                              
                              if (CatCode[y] == items[5])  option11.selected=true;
                                                                                        
                              option11.value=CatCode[y];  
                              option11.text=CatDesc[y];                              
                            
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
                          
                        }else{  alert("No options to add"); }
                  
                          
                          
                      cell2=document.createElement("TD");
                          var New_Journal_Type_Code=document.createElement("input");
                          New_Journal_Type_Code.type="hidden";
                          New_Journal_Type_Code.name="New_Journal_Type_Code";
                          New_Journal_Type_Code.id="New_Journal_Type_Code"+seq;
                          New_Journal_Type_Code.value=items[5];
                          cell2.appendChild(New_Journal_Type_Code);
                        //  var currentText=document.createTextNode(items[5]);
                        //  cell2.appendChild(currentText);
                          mycurrent_row.appendChild(cell2);   
                          
                          
                       if ( items[3]=="null") 
                       {
                         items[3]="--";
                       }
                         
                       cell2=document.createElement("TD");
                          var Remak=document.createElement("input");
                          Remak.type="hidden";
                          Remak.name="Remak";
                          Remak.value=items[3];
                          cell2.appendChild(Remak);
                          var currentText=document.createTextNode(items[3]);
                          cell2.appendChild(currentText);
                          mycurrent_row.appendChild(cell2); 
                          
                       cell2=document.createElement("TD");
                          cell2.align='RIGHT';
                          var Tot_Amt=document.createElement("input");
                          Tot_Amt.type="hidden";
                          Tot_Amt.name="Tot_Amt";
                          Tot_Amt.value=items[4];
                          cell2.appendChild(Tot_Amt);
                          var currentText=document.createTextNode(items[4]);
                          cell2.appendChild(currentText);
                          mycurrent_row.appendChild(cell2); 
                          
                       
                      cell2=document.createElement("TD");
                        cell2.align='CENTER';
                        var anc=document.createElement("A");
                        var url="javascript:Show('"+Ucode+"','"+Offid+"','"+txtCB_Year+"','"+txtCB_Month+"','"+items[0]+"')";
                        anc.href=url;
                        var txtedit=document.createTextNode("DETAILS");
                        anc.appendChild(txtedit);
                        cell2.appendChild(anc);
                        mycurrent_row.appendChild(cell2);                                                  
                        tbody.appendChild(mycurrent_row);
                                                    
                     }   
                    
                     
                }
                else if ( flag=="failure") 
                {                   
                    var tbody=document.getElementById("tbody");
                    var t=0;
                    for(t=tbody.rows.length-1;t>=0;t--)
                    {
                        tbody.deleteRow(0);
                    }            
                    alert("No Journals Found");
                
                }             
           
 }




function Minimize() 
{
window.resizeTo(0,0);
window.screenX = screen.width;
window.screenY = screen.height;
opener.window.focus();
}


function btncancel()
{
 self.close();
}


function numbersonly(e)
    {
        var unicode=e.charCode? e.charCode : e.keyCode;
       if(unicode==13)
        {          
        }
        if (unicode!=8 && unicode !=9  )
        {
            if (unicode<48 || unicode>57 ) 
                return false 
        }
     }




/////////////////////////////////////////////   Check Date() by User /////////////////////////////////////////////////////


function getCurrentYear() {
    var year = new Date().getYear();
    if(year < 1900) year += 1900;
    return year;
  }

  function getCurrentMonth() {
    return new Date().getMonth() + 1;
  } 

  function getCurrentDay() {
    return new Date().getDate();
  }


function Change_SLTYPE(getId)
{
    
    var index=document.getElementById('cmbCategoryCode'+getId).selectedIndex;        
    document.getElementById("New_Journal_Type_Code"+getId).value=CatCode[index-1];
    
          
  /*  var getrow=document.getElementById(getId);    
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
*/
}