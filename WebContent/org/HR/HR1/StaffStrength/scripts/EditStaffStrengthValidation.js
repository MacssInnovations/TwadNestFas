function clearAll()
{

        //document.frmStaffStrength.txtSl_No.value="";
        document.frmStaffStrength.cmbServiceGroup.selectedIndex=0;
        document.frmStaffStrength.cmbPostRank.selectedIndex=0;
        //document.frmStaffStrength.cmbPostCategory.selectedIndex=0;
        document.frmStaffStrength.txtNoPost.value="";
        document.frmStaffStrength.Remarks.value="";
             
        document.frmStaffStrength.cmdAdd.style.display="block";
        document.frmStaffStrength.cmdUpdate.style.display="none";
        document.frmStaffStrength.cmdDelete.style.display="none";
}


function nullCheck()
{
                
                  /*if((document.frmStaffStrength.txtSl_No.value=="") || (document.frmStaffStrength.txtSl_No.value.length<=0))
                  {
                    alert("Please Select OfficeId for Sl_No");
                    document.frmStaffStrength.txtSl_No.focus();
                    return false;
                    
                  }*/
                  
                   if((document.frmStaffStrength.cmbServiceGroup.value=="") || (document.frmStaffStrength.cmbServiceGroup.value.length<=0) || (document.frmStaffStrength.cmbServiceGroup.value==0))
                  {
                    alert("Please Select ServiceGroup");
                    document.frmStaffStrength.cmbServiceGroup.focus();
                    return false;
                    
                  }
                   if((document.frmStaffStrength.cmbPostRank.value=="") || (document.frmStaffStrength.cmbPostRank.value.length<=0) || (document.frmStaffStrength.cmbPostRank.value==0))
                  {
                    alert("Please Select Post Rank");
                    document.frmStaffStrength.cmbPostRank.focus();
                    return false;
                  }
                  /*if((document.frmStaffStrength.cmbPostCategory.value=="") || (document.frmStaffStrength.cmbPostCategory.value.length<=0) || (document.frmStaffStrength.cmbPostCategory.value==0))
                  {
                    alert("Please Select Post Category");
                    document.frmStaffStrength.cmbPostCategory.focus();
                    return false;
                 }*/                                                  
                  
                  if((document.frmStaffStrength.txtNoPost.value=="") || (document.frmStaffStrength.txtNoPost.value.length<=0))
                  {
                    alert("Please Enter No Of Post");
                    document.frmStaffStrength.txtNoPost.focus();
                    return false;
                    
                  }
                  
                    return true;
                
}

//Checking Null Values in Grid
function nullGrid()
{
                 if((document.frmStaffStrength.cmbOfficeLevel.value=="") || (document.frmStaffStrength.cmbOfficeLevel.value.length<=0) || (document.frmStaffStrength.cmbOfficeLevel.value==0))
                  {
                    alert("Please Select OfficeLevel");
                    document.frmStaffStrength.cmbOfficeLevel.focus();
                    return false;
                    
                  }
                  /*if((document.frmStaffStrength.txtTemplate_Name.value=="") || (document.frmStaffStrength.txtTemplate_Name.value.length<=0))
                  {
                    alert("Please Enter TemplateName");
                    document.frmStaffStrength.txtTemplate_Name.focus();
                    return false;
                    
                  }*/
                  
                  var tbody=document.getElementById("tblList");
                  var length=tbody.rows.length;
                  if(length<=0)
                  {
                    
                    alert("There is No Values in Grid");
                    return false;
                  
                  }

                return true;


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
            
            if((cells[2].lastChild.value==document.frmStaffStrength.cmbPostRank.value)&&(cells[1].lastChild.value==document.frmStaffStrength.cmbServiceGroup.value))
            {
                alert("Category Already Exists Choose Some Other Category");
                document.frmStaffStrength.cmbPostRank.focus();
                return true;
            }
        
        }
     return false;   
    }
    
    function slnocheck()
   {
        var table=document.getElementById("tblList");
        var rows=table.rows;
        var value;
        //alert("cells1:"+rows[0].cells[1].firstChild.nodeValue);
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
     
  function staffCheck()
  {
     if((document.frmStaffStrength.cmbOfficeLevel.value=="") || (document.frmStaffStrength.cmbOfficeLevel.value.length<=0) || (document.frmStaffStrength.cmbOfficeLevel.value==0))
      {
        alert("Please Select OfficeLevel");
        document.frmStaffStrength.cmbOfficeLevel.focus();
        return false;
        
      }
 }