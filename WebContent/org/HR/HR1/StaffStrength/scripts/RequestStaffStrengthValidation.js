//alert('validate staff strength')
var winjob;
function jobpopup()
{
    if (winjob && winjob.open && !winjob.closed) 
    {
       winjob.resizeTo(500,500);
       winjob.moveTo(250,250); 
       winjob.focus();
    }
    else
    {
        winjob=null
    }
        
    winjob= window.open("../../../../../org/HR/HR1/OfficeMaster/jsps/JobPopupJSP.jsp","JobSearch","status=1,height=500,width=500,resizable=YES, scrollbars=yes"); 
    winjob.moveTo(250,250);  
    winjob.focus();
    
}

function forChildOption()
{

  if (winjob && winjob.open && !winjob.closed) 
         winjob.officeSelection(true,true,true,false);
}

function doParentJob(jobid,deptid)
{
//alert(deptid);
if(deptid=="TWAD")
{
    document.frmOffice.txtOffice_Id.value=jobid;
    document.frmOffice.txtOffice_Id1.value=jobid;
    //document.HRE_EmployeeServiceDetails.txtDept_Id.value=deptid;
    loadOffice(jobid,'nothing');
    officeid(jobid);
    
    return true
}
else
{
        alert('Please select a TWAD Office');
        if (winjob && winjob.open && !winjob.closed) 
        {
           winjob.resizeTo(500,500);
           winjob.moveTo(250,250); 
           winjob.focus();
        }
        return false
}
}

window.onunload=function()
{
//alert('hello');
//if (winemp && winemp.open && !winemp.closed) winemp.close();
if (winjob && winjob.open && !winjob.closed) winjob.close();
}


function loadfyr()
{
         var cyr, cdt,cdt1;
 	cdt=new Date();
 	cyr=cdt.getFullYear();
 	cmn=cdt.getMonth();
        //alert("cdate"+cdt);
        //alert("cmonth"+cmn);
        //alert("cyear"+cyr);
        var cmbFinancialYear=document.getElementById("cmbFinancialYear");
        cyr=cyr+1;
 	if (parseInt(cmn) <= 2)
        {
  
                document.frmOffice.cmbFinancialYear.length=5;
                cmbFinancialYear.innerHTML="";
                var option=document.createElement("OPTION");
                option.text="--Select FinancialYear--";
                option.value=0;
                try
                        {
                            cmbFinancialYear.add(option);
                        }catch(errorobject)
                        { 
                            cmbFinancialYear.add(option,null);
                        } 
                for (var i = 0 ; i < 2; i++) 
                {
         
                  //document.frmOffice.cmbFinancialYear.options[i].text=(cyr-2)+"-"+(cyr-1);
                  //document.frmOffice.cmbFinancialYear.options[i].value=(cyr-2)+"-"+(cyr-1);
                  var id=(cyr-2)+"-"+(cyr-1);
                  var option=document.createElement("OPTION");
                  option.text=id;
                  option.value=id;
                  try
                        {
                            cmbFinancialYear.add(option);
                        }catch(errorobject)
                        { 
                            cmbFinancialYear.add(option,null);
                        } 
                  
                  cyr--;
                }
 	}
 	else 
 	{
            //alert('hai');
            //alert(cmn);
           document.frmOffice.cmbFinancialYear.length=5;
           cmbFinancialYear.innerHTML="";
           var option1=document.createElement("OPTION");
           option1.text="--Select FinancialYear--";
           option1.value=0;
           try
                        {
                            cmbFinancialYear.add(option1);
                        }catch(errorobject)
                        { 
                            cmbFinancialYear.add(option1,null);
                        } 
        if(cmn>=12)
        {
            for (var i = 0 ; i < 3; i++) 
            {
                var id=(cyr-1)+"-"+(cyr);
              //document.frmOffice.cmbFinancialYear.options[i].text=id;
              //document.frmOffice.cmbFinancialYear.options[i].value=id;
              
              var option=document.createElement("OPTION");
              option.text=id;
              option.value=id;
                  try
                        {
                            cmbFinancialYear.add(option);
                        }catch(errorobject)
                        { 
                            cmbFinancialYear.add(option,null);
                        }
              cyr--;
            }
        }
        else
        {
            for (var i = 0 ; i < 2; i++) 
            {
                var id=(cyr-1)+"-"+(cyr);
              //document.frmOffice.cmbFinancialYear.options[i].text=id;
              //document.frmOffice.cmbFinancialYear.options[i].value=id;
              
              var option=document.createElement("OPTION");
              option.text=id;
              option.value=id;
                  try
                        {
                            cmbFinancialYear.add(option);
                        }catch(errorobject)
                        { 
                            cmbFinancialYear.add(option,null);
                        }
              cyr--;
            }
        }
 	}
        
}


function slnocheck()
   {
        var table=document.getElementById("tblList");
        var rows=table.rows;
        var value;
        //alert("cells1:"+rows[0].cells[1].firstChild.nodeValue);
        if(rows.length!=0)
        {
            var max=rows[0].cells[0].lastChild.value;
            for(var i=1;i<rows.length;i++)
            {
                if(max<rows[i].cells[0].lastChild.value)
                {
                    max=rows[i].cells[0].lastChild.value;
                    
                    
                }
            
            }
            return max;
        }    
    return 0;
   }
   
   
 //Integer Checking
function isInteger(param,e)
{     
       var nav4 = window.Event ? true : false;
       //alert(e.keyCode);
       if (nav4)    // Navigator 4.0x
            var whichCode = e.which
       else         // Internet Explorer 4.0x
            var whichCode = e.keyCode
      if((whichCode>=48 && whichCode<=57 )||(whichCode==189))
      {
          return true;
      }
      var str=param.value;
      param.value=str.substring(0,str.length-1);
      return false;              
}

//Checking the Values in grid

function checkIf()
    {
        //alert("* ");
        var tbody=document.getElementById("tblList");
        var rows=tbody.rows;
        var i;
        
        for(i=0;i<rows.length;i++)
        {
            var cells=rows[i].cells;
            //alert("postCategory:"+cells[3].lastChild.value);
            //alert("postRank:"+cells[2].lastChild.value);
            //alert("ServiceGroup:"+cells[1].lastChild.value);
            
            if((cells[2].lastChild.value==document.frmOffice.cmbPostRank.value)&&(cells[1].lastChild.value==document.frmOffice.cmbServiceGroup.value))
            {
                alert("Category Already Exists Choose Some Other Category");
                document.frmOffice.cmbPostRank.focus();
                return true;
            }
        
        }
     return false;   
    }

function clearAll()
{

        //document.frmOffice.txtSl_No.value="";
        document.frmOffice.cmbServiceGroup.selectedIndex=0;
        document.frmOffice.cmbPostRank.selectedIndex=0;
        //document.frmOffice.cmbPostCategory.selectedIndex=0;
        document.frmOffice.txtNoPost.value="";
        document.frmOffice.txtNoOfPostTo.value="";
        document.frmOffice.txtNoOfPostFrom.value="";
        document.frmOffice.txtTotal.value="";
        document.frmOffice.Remarks.value="";
             
        document.frmOffice.cmdAdd.style.display="block";
        document.frmOffice.cmdUpdate.style.display="none";
        document.frmOffice.cmdDelete.style.display="none";
        //var slno=slnocheck();
        //document.frmOffice.txtSl_No.value=++slno;
}

function nullCheck()
{
                
                  /*if((document.frmOffice.txtSl_No.value=="") || (document.frmOffice.txtSl_No.value.length<=0))
                  {
                    alert("Please Select OfficeId for Sl_No");
                    document.frmOffice.txtSl_No.focus();
                    return false;
                    
                  }*/
                  
                   if((document.frmOffice.cmbServiceGroup.value=="") || (document.frmOffice.cmbServiceGroup.value.length<=0) || (document.frmOffice.cmbServiceGroup.value==0))
                  {
                    alert("Please Select ServiceGroup");
                    document.frmOffice.cmbServiceGroup.focus();
                    return false;
                    
                  }
                   if((document.frmOffice.cmbPostRank.value=="") || (document.frmOffice.cmbPostRank.value.length<=0) || (document.frmOffice.cmbPostRank.value==0))
                  {
                    alert("Please Select Post Rank");
                    document.frmOffice.cmbPostRank.focus();
                    return false;
                  }
                  /*if((document.frmOffice.cmbPostCategory.value=="") || (document.frmOffice.cmbPostCategory.value.length<=0) || (document.frmOffice.cmbPostCategory.value==0))
                  {
                    alert("Please Select Post Category");
                    document.frmOffice.cmbPostCategory.focus();
                    return false;
                 } */                                                 
                  
                  if((document.frmOffice.txtNoPost.value=="") || (document.frmOffice.txtNoPost.value.length<=0))
                  {
                    alert("Please Enter No Of Post");
                    document.frmOffice.txtNoPost.focus();
                    return false;
                    
                  }
                  
                    return true;
                
}

//Checking Null Values in Grid
function nullValue()
{
    //alert(document.frmOffice.cmbFinancialYear.value);
                if((document.frmOffice.txtOffice_Id.value=="") ||(document.frmOffice.txtOffice_Id.value.length<=0))
                {
                    alert("Please Enter Office Id");
                    document.frmOffice.txtOffice_Id.focus();
                    return false;
                    
                }
                 if((document.frmOffice.cmbFinancialYear.value=="") || (document.frmOffice.cmbFinancialYear.value.length<=0) || (document.frmOffice.cmbFinancialYear.value==0))
                  {
                    alert("Please Select Financial Year");
                    document.frmOffice.cmbFinancialYear.focus();
                    return false;
                    
                  }
                  /*if((document.frmOffice.txtRequest_Date.value=="") || (document.frmOffice.txtRequest_Date.value.length<=0))
                  {
                    alert("Please Enter Status on Date in General Details");
                    //document.frmOffice.txtRequest_Date.focus();
                    return false;
                    
                  }*/
                  
                  var tbody=document.getElementById("tblList");
                  var length=tbody.rows.length;
                  if(length<=0)
                  {
                    
                    alert("There is No Values in Existing Template Grid");
                    return false;
                  
                  }
                  /*var tbodyto=document.getElementById("tblListTo");
                  var lengthto=tbodyto.rows.length;
                  if(lengthto<=0)
                  {
                    
                    alert("There is No Values in Diversion Grid");
                    return false;
                  
                  }*/

                return true;


}

/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
//This is For CallServer1 Checking

function nullCheckTo()
{
                
                  /*if((document.frmOffice.txtSl_No.value=="") || (document.frmOffice.txtSl_No.value.length<=0))
                  {
                    alert("Please Select OfficeId for Sl_No");
                    document.frmOffice.txtSl_No.focus();
                    return false;
                    
                  }*/
                  
                   if((document.frmOffice.cmbServiceGroupTo.value=="") || (document.frmOffice.cmbServiceGroupTo.value.length<=0) || (document.frmOffice.cmbServiceGroupTo.value==0))
                  {
                    alert("Please Select ServiceGroup");
                    document.frmOffice.cmbServiceGroupTo.focus();
                    return false;
                    
                  }
                   if((document.frmOffice.cmbPostRank.value=="") || (document.frmOffice.cmbPostRankTo.value.length<=0) || (document.frmOffice.cmbPostRankTo.value==0))
                  {
                    alert("Please Select Post Rank");
                    document.frmOffice.cmbPostRankTo.focus();
                    return false;
                  }
                  if((document.frmOffice.cmbPostCategoryTo.value=="") || (document.frmOffice.cmbPostCategoryTo.value.length<=0) || (document.frmOffice.cmbPostCategoryTo.value==0))
                  {
                    alert("Please Select Post Category");
                    document.frmOffice.cmbPostCategoryTo.focus();
                    return false;
                 }                                                  
                  
                  if((document.frmOffice.txtNoOfPostTo.value=="") || (document.frmOffice.txtNoOfPostTo.value.length<=0))
                  {
                    alert("Please Enter No Of Post");
                    document.frmOffice.txtNoOfPostTo.focus();
                    return false;
                    
                  }
                  if((document.frmOffice.txtNoOfPostFrom.value=="") || (document.frmOffice.txtNoOfPostFrom.value.length<=0))
                  {
                    alert("Please Enter No Of Post");
                    document.frmOffice.txtNoOfPostFrom.focus();
                    return false;
                    
                  }
                  
                    return true;
                
}

//Checking the Values in grid

function checkIfTo()
    {
        //alert("* ");
        var tbody=document.getElementById("tblListTo");
        var rows=tbody.rows;
        var i;
        
        for(i=0;i<rows.length;i++)
        {
            var cells=rows[i].cells;
            //alert("postCategory:"+cells[3].lastChild.value);
            //alert("postRank:"+cells[2].lastChild.value);
            //alert("ServiceGroup:"+cells[1].lastChild.value);
            
            if((cells[3].lastChild.value==document.frmOffice.cmbPostCategoryTo.value)&&(cells[2].lastChild.value==document.frmOffice.cmbPostRankTo.value)&&(cells[1].lastChild.value==document.frmOffice.cmbServiceGroupTo.value))
            {
                alert("PostCategory Already Exists Choose Some Other Category");
                return true;
            }
        
        }
     return false;   
    }
    
    
function clearAllTo()
{

        //document.frmOffice.txtSl_No.value="";
        document.frmOffice.cmbServiceGroupTo.selectedIndex=0;
        document.frmOffice.cmbPostRankTo.selectedIndex=0;
        document.frmOffice.cmbPostCategoryTo.selectedIndex=0;
        document.frmOffice.txtNoOfPostTo.value="";
        document.frmOffice.RemarksTo.value="";
        document.frmOffice.txtNoOfPostFrom.value="";
        document.frmOffice.RemarksFrom.value="";
             
        document.frmOffice.cmdAddTo.style.display="block";
        document.frmOffice.cmdUpdateTo.style.display="none";
        document.frmOffice.cmdDeleteTo.style.display="none";
        var slno=slnocheckTo();
        document.frmOffice.txtSl_NoTo.value=++slno;
}

function slnocheckTo()
   {
        var table=document.getElementById("tblListTo");
        var rows=table.rows;
        var value;
        //alert("cells1:"+rows[0].cells[1].firstChild.nodeValue);
        var max=rows[0].cells[1].lastChild.value;
        for(var i=1;i<rows.length;i++)
        {
            if(max<rows[i].cells[1].lastChild.value)
            {
                max=rows[i].cells[1].lastChild.value;
                
                
            }
        
        }
        return max;
            
   
   }    
   
   function noofpostcheck()
   {
    
        var postto=document.frmOffice.txtNoOfPostTo.value;
        var postfrom=document.frmOffice.txtNoOfPostFrom.value;
        //alert("postto"+postto);
        //alert("postfrom"+postfrom);
        if(parseInt(postto)<parseInt(postfrom))
        {
            alert("No Of Post From is Not Greater Than No Of Post To");
            document.frmOffice.txtNoOfPostFrom.focus();
            return true;
        
        }
        return false;
        
    
   }
   
   function numbersonly1(e,t)
    {
       var unicode=e.charCode? e.charCode : e.keyCode;
       if(unicode==13)
        {
          try{t.blur();}catch(e){}
          return true;
        
        }
        if (unicode!=8 && unicode !=9  )
        {
            if (unicode<48||unicode>57 ) 
                return false 
        }
     }
     
//This Coding for Date Validation and Checking     
function calins(e,t)
{
    var unicode=e.charCode? e.charCode : e.keyCode;
        //alert(unicode);
        //if(unicode !=8)
        
        if (unicode!=8 && unicode !=9 && unicode!=37 && unicode !=39 && unicode !=46  && unicode !=35 && unicode !=36 )
        {
            if(t.value.length==2 || t.value.length==5)
                t.value=t.value + '/';
             if (unicode<48||unicode>57 ) 
                return false 
        }
       

}


function checkdt(t)
{
  
    if(t.value.length==0)
        return false;
    if(t.value.length==10  && t.value.indexOf('/',0)==2 && t.value.indexOf('/',3)==5)
    {
      
       
        // var c=t.value.replace(/-/g,'/');
         var c=t.value;
        try{
        var f=DateFormat(t,c,event,true,'3');
        }catch(e){
        //exception  start
        
         t.value=c;
            var sc=t.value.split('/');
            var currenDay =sc[0];
            var currentMonth=sc[1];
            var currentYear=sc[2];
            //alert(currentYear == getCurrentYear()  && currentMonth == getCurrentMonth() && currenDay > getCurrentDay());
            if(currentYear<1970)
            {
            
                    alert('Entered date should be greater than or equal to 1970');
                    t.value="";
                    t.focus();
                    return false;
           } 
           /*else if(currentYear == getCurrentYear())
            {
                    if( currentMonth > getCurrentMonth())
                    {
                        alert('Entered date should be less than current date and \n year should be greater than or equal to 1970');
                        t.value="";
                        t.focus();
                        return false;
                    }
                    else if( currentMonth == getCurrentMonth())
                    {
                        if(currenDay > getCurrentDay() )
                        {
                                alert('Entered date should be less than current date and \n year should be greater than or equal to 1970');
                                t.value="";
                                t.focus();
                                return false;
                        }
                    }
                    
            }*/
            
            t.value=c;
             if(err!=0)
                {
                    t.value="";
                    return false;
                }
            return true;
        
        
        //exception end
        
        }
        if( f==true)
        {
            //alert(f);
            //t.value=c.replace(/\//g,'-');
            t.value=c;
            var sc=t.value.split('/');
            var currenDay =sc[0];
            var currentMonth=sc[1];
            var currentYear=sc[2];
            //alert(currentYear == getCurrentYear()  && currentMonth == getCurrentMonth() && currenDay > getCurrentDay());
         
            if(currentYear<1970)
            {
            
                    alert('Entered date should be greater than or equal to 1970');
                    t.value="";
                    t.focus();
                    return false;
           } 
           /*else if(currentYear == getCurrentYear())
            {
                    if( currentMonth > getCurrentMonth())
                    {
                         alert('Entered date should be greater than or equal to 1970');
                        t.value="";
                        t.focus();
                        return false;
                    }
                    else if( currentMonth == getCurrentMonth())
                    {
                        if(currenDay > getCurrentDay() )
                        {
                                alert('Entered date should be greater than or equal to 1970');
                                t.value="";
                                t.focus();
                                return false;
                        }
                    }
                    
            }*/
            
            t.value=c;
           
            return true;
            
        }
        else
        {
                if(err!=0)
                {
                    t.value="";
                    return false;
                }
        }
            
    }
    else
    {
            alert('Date format  should be (dd/mm/yyyy)');
            t.value="";
            //t.focus();
            return false
    }
    
}


function clear1()
{

var tbody=document.getElementById("tblList");
var t=0;
for(t=tbody.rows.length-1;t>=0;t--)
{
    tbody.deleteRow(0);
}

document.frmOffice.cmbSubmit.disabled=false;
}

function OfficeCheck()
{
        if((document.frmOffice.txtOffice_Id.value=="") ||(document.frmOffice.txtOffice_Id.value.length<=0))
        {
            alert("Please Enter Office Id");
            document.frmOffice.txtOffice_Id.focus();
            return false;
            
        }
        return true;
}