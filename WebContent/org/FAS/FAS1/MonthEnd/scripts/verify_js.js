var Tally_Status = "NONE"; 


// Part I

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




function  Ledger_Details()
{
       var accounting_unit_id = document.getElementById("cmbAcc_UnitCode").value;    
       var cashbook_month = document.getElementById("txtCB_Month").value;    
       var cashbook_year = document.getElementById("txtCB_Year").value;    
       
            var url="../../../../../SL_GL_CB_Freeze.kv?Command=Ledger_Details_superUser_role&accounting_unit_id="+accounting_unit_id+"&cashbook_month="+cashbook_month+"&cashbook_year="+cashbook_year;
            
            var req=getTransport();
            req.open("GET",url,true); 
            req.onreadystatechange=function()
            {
               handleResponse(req);
            }   
            req.send(null);
                    
}




function handleResponse(req)
{

       if(req.readyState==4)
        {
          if(req.status==200)
          {               
              var baseResponse=req.responseXML.getElementsByTagName("response")[0];             
              var tagCommand=baseResponse.getElementsByTagName("command")[0];
              var Command=tagCommand.firstChild.nodeValue; 
            
            if(Command=="Ledger_Details_superUser_role")
            {
               var flag=baseResponse.getElementsByTagName("flag")[0].firstChild.nodeValue;
                 if(flag=="Success")
                    {
                    
                      var tbody=document.getElementById("grid_body");
                      var t=0;
                      for(t=tbody.rows.length-1;t>=0;t--)
                        {
                             tbody.deleteRow(0);
                        }
        
                      var RecNo=baseResponse.getElementsByTagName("gl_account_head_code");
                      
                      var items=new Array();
           
                      for(var k=0;k<RecNo.length;k++)
                        {
                            
                         items[0]=baseResponse.getElementsByTagName("gl_account_head_code")[k].firstChild.nodeValue;  
                         
                         items[1]=baseResponse.getElementsByTagName("gl_month_closing_balance")[k].firstChild.nodeValue;   
                         items[2]=baseResponse.getElementsByTagName("gl_month_closing_bal_dr_cr_ind")[k].firstChild.nodeValue;
                         items[3]=baseResponse.getElementsByTagName("sl_account_head_code")[k].firstChild.nodeValue;
                         items[4]=baseResponse.getElementsByTagName("sl_month_closing_balance")[k].firstChild.nodeValue;   
                         items[5]=baseResponse.getElementsByTagName("sl_month_closing_bal_dr_cr_ind")[k].firstChild.nodeValue;
                         items[6]=baseResponse.getElementsByTagName("account_head_desc")[k].firstChild.nodeValue;  
                         items[7]=baseResponse.getElementsByTagName("tally_status")[k].firstChild.nodeValue;  
                        
                         if ( items[7] =='RED' ) 
                         {
                            Tally_Status ="NOTTALLY";
                         }
                         
                         if (items[0]=='null')
                           items[0]="";
                         if (items[1]=='null')
                           items[1]="";
                         if (items[2]=='null')
                           items[2]="";
                         if (items[3]=='null')
                           items[3]="";
                         if (items[4]=='null')
                           items[4]="";
                         if (items[5]=='null')
                           items[5]="";
                         if (items[6]=='null')
                           items[6]="";
                    
                     var mycurrent_row=document.createElement("TR");
                     var cell2;
                
                    cell2=document.createElement("TD");
                          cell2.style.textAlign='left';                          
                          
                          if(items[7]=='RED')
                          {
                            cell2.style.color="red";                           
                          }
                          else if (items[7]=='BLACK')
                          {
                           cell2.style.color="black";
                          }      
                          else
                          {
                           cell2.style.color="green";
                          }                          
                          
                          var currentText=document.createTextNode(items[0]);
                          cell2.appendChild(currentText);
                          mycurrent_row.appendChild(cell2);
                   
                     cell2=document.createElement("TD");
                          cell2.style.textAlign='left';  
                          if(items[7]=='RED')
                          {
                            cell2.style.color="red";
                          }
                          var currentText=document.createTextNode(items[6] );
                          cell2.appendChild(currentText);
                          mycurrent_row.appendChild(cell2);
                          
                           
                     cell2=document.createElement("TD");
                          cell2.style.textAlign='right';
                          if(items[7]=='RED')
                          {
                            cell2.style.color="red";
                          }
                          else if (items[7]=='BLACK')
                          {
                           cell2.style.color="black";
                          }      
                          else
                          {
                            cell2.style.color="green";
                          }  
                          var currentText=document.createTextNode(items[1]);
                          cell2.appendChild(currentText);
                        mycurrent_row.appendChild(cell2);
                  
                     cell2=document.createElement("TD");
                          cell2.style.textAlign='center';
                          if(items[7]=='RED')
                          {
                            cell2.style.color="red";
                          }
                          else if (items[7]=='BLACK')
                          {
                           cell2.style.color="black";
                          }      
                          else
                          {
                             cell2.style.color="green";
                          }  
                           var currentText=document.createTextNode(items[2]);
                           cell2.appendChild(currentText);
                           mycurrent_row.appendChild(cell2);
                    
                     cell2=document.createElement("TD");
                          cell2.style.textAlign='left';
                          if(items[7]=='RED')
                          {
                            cell2.style.color="red";
                          }
                          else if (items[7]=='BLACK')
                          {
                           cell2.style.color="black";
                          }      
                          else
                          {
                            cell2.style.color="green";
                          }
                           var currentText=document.createTextNode(items[3]);
                           cell2.appendChild(currentText);
                           mycurrent_row.appendChild(cell2);
                           
                     cell2=document.createElement("TD");
                          cell2.style.textAlign='right';
                          
                          if(items[7]=='RED')
                          {
                            cell2.style.color="red";
                          }
                          else if (items[7]=='BLACK')
                          {
                           cell2.style.color="black";
                          }      
                          else
                          {
                            cell2.style.color="green";
                          }
                           var currentText=document.createTextNode(items[4]);
                           cell2.appendChild(currentText);
                           mycurrent_row.appendChild(cell2);
                    
                     cell2=document.createElement("TD");
                       cell2.style.textAlign='center';
                         if(items[7]=='RED')
                          {
                            cell2.style.color="red";
                          }
                          else if (items[7]=='BLACK')
                          {
                           cell2.style.color="black";
                          }      
                          else
                          {
                           cell2.style.color="green";
                          } 
                           var currentText=document.createTextNode(items[5]);
                           cell2.appendChild(currentText);
                           mycurrent_row.appendChild(cell2);
                    
                     tbody.appendChild(mycurrent_row);
           
                    }
                      
                   Status_Check();   
                      
                  }
                 //added by sathya on 27/02/2012
                else if(flag=="NoData")
                 {
                	 alert("NO data found to load");
                	 document.getElementById("btFreezeGL").disabled=true;
                	 document.getElementById("btFreezeSL").disabled=true;
                 }
                  else if(flag=="Failure")
                    {
                      alert("Unable to Load Data");
                      document.getElementById("btFreezeGL").disabled=true;
                 	 document.getElementById("btFreezeSL").disabled=true;
                    }
                 else 
                 {
                	 alert("Unable to load Data");
                 }
                    
           } 
         } 
    }
}






function Freeze_SL()
{
if(document.getElementById("txtCB_Month").value=="0")
        {
        alert("Choose Month");
        return false;
        }
       var accounting_unit_id = document.getElementById("cmbAcc_UnitCode").value;    
       var cashbook_month = document.getElementById("txtCB_Month").value;    
       var cashbook_year = document.getElementById("txtCB_Year").value;    
     
       var url="../../../../../SL_GL_CB_Freeze.kv?Command=SL_Freeze&accounting_unit_id="+accounting_unit_id+"&cashbook_month="+cashbook_month+"&cashbook_year="+cashbook_year;
            
       var req=getTransport();
       req.open("GET",url,true); 
       req.onreadystatechange=function()
       {
          handleResponse_SL_Freeze(req);
       }   
       req.send(null);      
       
}





function handleResponse_SL_Freeze(req)
{

       if(req.readyState==4)
        {
          if(req.status==200)
          {               
              var baseResponse=req.responseXML.getElementsByTagName("response")[0];             
              var tagCommand=baseResponse.getElementsByTagName("command")[0];
              var Command=tagCommand.firstChild.nodeValue; 
            
            if(Command=="SL_Freeze")
            {
                 var flag=baseResponse.getElementsByTagName("flag")[0].firstChild.nodeValue;
                 if(flag=="Success")
                    {
                      alert("Sub Ledger Closing Balance has been Frozen Successfully");  
                    }
                 else 
                   {
                     alert("Failed to Freeze Sub Ledger Closing Balance");  
                   }
            }
          }  
        }  
}
       
        
        


function Freeze_GL()
{
        //alert(document.getElementById("txtCB_Month").value);
        if(document.getElementById("txtCB_Month").value=="0")
        {
        alert("Choose Month");
        return false;
        }
       var accounting_unit_id = document.getElementById("cmbAcc_UnitCode").value;    
       var cashbook_month = document.getElementById("txtCB_Month").value;    
       var cashbook_year = document.getElementById("txtCB_Year").value;    
     
       var url="../../../../../SL_GL_CB_Freeze.kv?Command=GL_Freeze&accounting_unit_id="+accounting_unit_id+"&cashbook_month="+cashbook_month+"&cashbook_year="+cashbook_year;
            
       var req=getTransport();
       req.open("GET",url,true); 
       req.onreadystatechange=function()
       {
          handleResponse_GL_Freeze(req);
       }   
       req.send(null);      
       
}

function handleResponse_GL_Freeze(req)
{

       if(req.readyState==4)
        {
          if(req.status==200)
          {               
              var baseResponse=req.responseXML.getElementsByTagName("response")[0];             
              var tagCommand=baseResponse.getElementsByTagName("command")[0];
              var Command=tagCommand.firstChild.nodeValue; 
            
            if(Command=="GL_Freeze")
            {
                 var flag=baseResponse.getElementsByTagName("flag")[0].firstChild.nodeValue;
                 if(flag=="Success")
                    {
                      alert("General Ledger Closing Balance has been Frozen Successfully");  
                    }
                 else 
                   {
                     alert("Failed to Freeze General Ledger Closing Balance");  
                   }
            }
          }  
        }  
}
       
        
        
        

function Status_Check()
{
       var accounting_unit_id = document.getElementById("cmbAcc_UnitCode").value;    
       var cashbook_month = document.getElementById("txtCB_Month").value;    
       var cashbook_year = document.getElementById("txtCB_Year").value;    
     
       var url="../../../../../SL_GL_CB_Freeze.kv?Command=Status_Check&accounting_unit_id="+accounting_unit_id+"&cashbook_month="+cashbook_month+"&cashbook_year="+cashbook_year;
            
       var req=getTransport();
       req.open("GET",url,true); 
       req.onreadystatechange=function()
       {
          handleResponse_Status_Check(req);
       }   
       req.send(null);      
       
}

function handleResponse_Status_Check(req)
{

       if(req.readyState==4)
        {
          if(req.status==200)
          {               
              var baseResponse=req.responseXML.getElementsByTagName("response")[0];             
              var tagCommand=baseResponse.getElementsByTagName("command")[0];
              var Command=tagCommand.firstChild.nodeValue; 
            
            if(Command=="Status_Check")
            {
                 var flag=baseResponse.getElementsByTagName("flag")[0].firstChild.nodeValue;
                 if(flag=="Success")
                    {
                     
                      var SL_Status=baseResponse.getElementsByTagName("SL_Status")[0].firstChild.nodeValue;                      
                      if (SL_Status == 'AlreadyFreeze')
                      {
                        document.getElementById("btFreezeSL").value="SL Already Freeze";
                        document.getElementById("btFreezeSL").disabled=true;                        
                      }                      
                      else 
                      {
                      
                       // alert("Tally_Status-->"+Tally_Status);
                        
                        if ( Tally_Status== "NOTTALLY" ) 
                        {
                        alert("SL and GL Doesn't TALLY");
                           document.getElementById("btFreezeSL").value="SL and GL Not Tally ";
                           document.getElementById("btFreezeSL").disabled=true;    
                           
                           //document.getElementById("btFreezeSL").disabled=true;                        
                        }
                        else 
                        {
                           document.getElementById("btFreezeSL").value="SL Freeze";
                           document.getElementById("btFreezeSL").disabled=false;                        
                        }
                        
                      }
                     
                      
                      var GL_Status=baseResponse.getElementsByTagName("GL_Status")[0].firstChild.nodeValue;      
                    
                      if (GL_Status == 'AlreadyFreeze')
                      {
                        document.getElementById("btFreezeGL").value="GL Already Freeze";                        
                        document.getElementById("btFreezeGL").disabled=true;
                      }
                      else
                      {
                     
                     if ( Tally_Status== "NOTTALLY" ) 
                     {
                      document.getElementById("btFreezeGL").value="GL and SL Not Tally ";
                       document.getElementById("btFreezeGL").disabled=true;
                     }
                     else{
                        document.getElementById("btFreezeGL").value="GL Freeze";                        
                        document.getElementById("btFreezeGL").disabled=false;
                        }
                      }
                      
                      
                      
                    }
                 else 
                   {
                     alert("Unable to Load SL and GL Freeze Status !");
                   }
            }
          }  
        }  
}
