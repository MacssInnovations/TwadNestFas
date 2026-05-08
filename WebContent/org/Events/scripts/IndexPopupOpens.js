var w = 480, h = 340;
if (document.all || document.layers) {
   w = screen.availWidth;
   h = screen.availHeight;
}
var popW = 550, popH = 400;
var leftPos = (w-popW)/2, topPos = (h-popH)/2 + 50;

   function open_New_Win(target_page)
    {
        //alert("called  "+target_page);
        var url="http://"+target_page;
        
        Targ_Window1=window.open(url,"Target","resizable=1,scrollbars=yes,titlebar=yes,toolbar=no,width=" + popW + ",height=" + popH + ",top=" + topPos + ",left=" + leftPos +"");  

    }
    function openEvtWnd(eve_id)
    {
        //alert("called  "+eve_id);
        var eveId=eve_id;
        var url="EventDetails.jsp?eveId="+eveId;
        DetailsWindow=window.open(url,"Details",'width=' + popW + ',height=' + popH + ',top=' + topPos + ',left=' + leftPos);
   }
    function openNwsWnd(eve_id)
    {
        //alert("called  "+eve_id);
        var eveId=eve_id;
        var url="NewsDetails.jsp?eveId="+eveId;
        DetailsWindow=window.open(url,"Details",'width=' + popW + ',height=' + popH + ',top=' + topPos + ',left=' + leftPos);
    }
    function moreNews(val)
    {
    //alert("called :"+val);
    if(val=="more")
    {
    
    document.getElementById("bodyNews1").style.visibility="visible";
    document.getElementById("bodyNews1").value="Hide";
    document.getElementById("seemore").style.visibility="hidden";
    }
    else
    {
    document.getElementById("bodyNews").style.visibility="visible";
    document.getElementById("bodyNews").value="Hide";
    document.getElementById("bodyNews1").style.visibility="hidden";
     document.getElementById("seemore").style.visibility="visible";
    }
    }
    function opnListOfNews()
    {

        var url="EntireNewsDetails.jsp";
        Entire_news=window.open(url,"EntireNews","menubar=no,resizable=1,scrollbars=yes,titlebar=yes,toolbar=no,width=" + popW + ",height=" + popH + ",top=" + topPos + ",left=" + leftPos +"");  
    }