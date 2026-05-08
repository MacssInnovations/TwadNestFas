///
function popWindow()
  {
  var off=document.form1.selOff.value;
  var url="../jsps/ViewOffices.jsp?office="+off;
  var wind=window.open(url,"myWindow","status=1,height=200,width=200");
  wind.moveTo(100,250);
  }
  
 
function disableMe()
{
document.form1.AccessibleBy.value="no";
document.form1.AccessibleBy.disabled=true;
document.form1.selOff.disabled=true;

}
function enableMe()
{
document.form1.AccessibleBy.value="select office";
document.form1.AccessibleBy.disabled=false;
document.form1.selOff.disabled=false;

}

function enable_NewSL()
{
         document.form1.finalsubmit.disabled=false;
         document.form1.txt_sldesc.disabled=false;
         document.form1.update.disabled=false;
         document.form1.Cancel2.disabled=false;
         //document.form1.finalsubmit.disabled=true;
 }        
         





function enableFields()
{
document.form1.Last_date_used.disabled=false;
document.form1.File_Ref_No.disabled=false;
document.form1.File_Ref_Date.disabled=false;
}

function disableFields()
{
document.form1.Last_date_used.disabled=true;
document.form1.File_Ref_No.disabled=true;
document.form1.File_Ref_Date.disabled=true;

}




