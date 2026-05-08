//alert("inside Load");
function LoadRecord()
{
 var iframe=document.getElementsByTagName("iframe")[0];
 alert(iframe);
    if(iframe)
    {
        iframe.src="../../../../../ServletLoad.view?command=" +iframe;
    }

}

