var wind=null;
function loadPage(url)
{    
    document.location=url;
 }



function loadPageInNewWindow(url)
{  

	
  var dt=new Date();
  var st="";
  st=dt.getHours()+"_"+dt.getMinutes()+"_"+dt.getSeconds();
 wind=window.open(url,"winName","status=no,location=no,menubar=no,resizable=1,scrollbars=yes,titlebar=yes,toolbar=no");  
  wind.focus();
 
}


window.onunload=function()
{
	//alert(" Please View A52 Register Report(Detail & Abstract)/Edit & Freeze,Please Refer FAS-Notice Board as on or before 19/07/2013");
	//alert("1. Asset register  data both A52 and AA52 for the year 2011-12 is  uploaded from EXCEL Sheet sent to  Head Office. \n"+
	 //		  "2.  Both Value Verification and Numerical Verification to be done  on or before 19.7.2013 \n"+
	//		  "3. Please View A52 and AA52 Register Report (Detail and Abstract) and freeze. \n"+
		//	  "4. Please Refer to FAS NOTICE BOARD for details");
if(window.confirm("Do You confirm to close?"))
{
        // alert('hai yes');
         window.frames[0].myclose();
         if (wind && wind.open && !wind.closed) wind.close();
         window.open('Logout.jsp',"_parent","status=no,location=no,menubar=no,resizable=1,scrollbars=yes,titlebar=yes,toolbar=no");  
         self.close();
         return true;
}
else{
       // alert('hai no');
       
         return false;
         /*
         window.frames[0].myclose();
         if (wind && wind.open && !wind.closed) wind.close();
         window.open('Logout.jsp',"_parent","status=no,location=no,menubar=no,resizable=1,scrollbars=yes,titlebar=yes,toolbar=no");  
         self.close();
         return true;
         */
}

}

