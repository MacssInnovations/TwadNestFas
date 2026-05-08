function initiateAutoComplete(param)
    {        
        /*var i=0;
        for(i=0;i<10;i++)
        {
            temp[i]=param+i;            
        }*/
        //alert("hai");
        var value=param;
        callServer("Load",value);
        
        
    }
    
    
    
    function optionHover(evt)
    {       
        if (!evt) var evt = window.event;
        var eventSrcID=0;
        if(evt.target)        
           eventSrcID=evt.target.id;
        else if(evt.srcElement)
           eventSrcID=evt.srcElement.id;         
        document.getElementById(eventSrcID).style.backgroundColor="rgb(210,206,189)";
    }
    
    function optionNormal(evt)
    {
        if (!evt) var evt = window.event;
        var eventSrcID=0;
        if(evt.target)        
           eventSrcID=evt.target.id;
        else if(evt.srcElement)
           eventSrcID=evt.srcElement.id;
        document.getElementById(eventSrcID).style.backgroundColor="rgb(255,247,214)";
    }
    
    function optionSelected(evt)
    {        
        if (!evt) var evt = window.event;
        var eventSrcID=0;
        if(evt.target)        
           eventSrcID=evt.target.id;
        else if(evt.srcElement)
           eventSrcID=evt.srcElement.id;
        document.frmOfficeContact.txtOffice_Id.value=eventSrcID;        
        document.frmOfficeContact.txtOffice_Name.value=document.getElementById(eventSrcID).firstChild.nodeValue;
        destroyAutoComplete();
    }
    
    function destroyAutoComplete()
    {
        temp=new Array();
        var pd=document.getElementById("popup");          
        pd.innerHTML="";
        pd.style.zIndex=1;           
        pd.style.display="none";
    }
    
    function testKeyListener(e)
    {
       var nav4 = window.Event ? true : false;
       if (nav4)  
       {// Navigator 4.0x
            var whichCode = e.which
       }       
       else        // Internet Explorer 4.0x
       {    
            var whichCode = e.keyCode
       }
       
       if(whichCode==27)
       {
          destroyAutoComplete();
       }
       
    }
    
    function divposition()
    {
    var pd=document.getElementById("popup");
    pd.style.top="95px";
    pd.style.left="580px";
    
    }
   
   function popupdisable()
   {
       var pd=document.getElementById("popup");
       //alert(pd.style.display);
       if(pd.style.display=="block")
       {
        pd.style.display="none";
       }
   }