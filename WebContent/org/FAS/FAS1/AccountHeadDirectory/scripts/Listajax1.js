
function popWindow()
  {
  var off=document.form1.selOff.value;
  var url="../jsps/pop1.jsp?office="+off;
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


         



    function popUp(strURL,strType,strHeight,strWidth)
    {
      alert("yes");
      var strOptions="";
      if (strType=="console") strOptions="resizable,height="+strHeight+",width="+strWidth;
      if (strType=="fixed") strOptions="status,height="+strHeight+",width="+strWidth;
      if (strType=="elastic") strOptions="toolbar,menubar,scrollbars,resizable,location,height="+strHeight+",width="+strWidth;
      window.open(strURL, 'newWin', strOptions);
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

/*function popUpDiv(parent,element_id)
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
*/

