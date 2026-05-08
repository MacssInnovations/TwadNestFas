 function btnsubmit()
    {
        
        var sele=document.getElementsByName("choice").length;
      // alert("sele>>>"+sele);
        var val=0;
        var accno="",bankid="",br_id="",B_name="",micr="",addr="",bid="",branchid="";
        
        if(sele>0)
        {
           
            /* if(sele==1)
            {
             
                if(document.FAS_AccNumber_Form.choice.checked==true)
                {
                 accno=document.FAS_AccNumber_Form.choice.value;
                 r=document.getElementById(accno);
                 rcells=r.cells;
                 bankid=rcells.item(1).firstChild.nodeValue;
                 B_name=rcells.item(2).firstChild.nodeValue;
                  bid=rcells.item(3).firstChild.nodeValue;
                 branchid=rcells.item(4).firstChild.nodeValue;
//                 bid=document.FAS_AccNumber_Form.hbid.value;
//                 alert("bid:::"+bid);
//                 branchid=document.FAS_AccNumber_Form.hbranchid.value
                 addr=document.FAS_AccNumber_Form.haddr.value;
                 micr=document.getElementById("hmicr").value;                             
                 br_id=rcells.item(6).firstChild.nodeValue; */
                 
               /*  
                 B_name=rcells.item(3).firstChild.nodeValue+" - "+rcells.item(5).firstChild.nodeValue;
                 micr=rcells.item(6).firstChild.nodeValue;
                 addr=rcells.item(7).firstChild.nodeValue;*/
                /* }
            }
            else
            {  */    
                for(i=0;i<sele;i++)
                { 
                  // alert("inside sele>>>"+sele);
                    if(document.FAS_AccNumber_Form.choice[i].checked==true)
                    {
                       // accno=document.FAS_AccNumber_Form.choice[i].value;
                      //  alert("inside accno>>>"+accno);
                         accno1=document.FAS_AccNumber_Form.choice[i].value;
                         r=document.getElementById(accno1);
                        // alert(r+"***");
                         rcells=r.cells;
                         
                         accno=rcells.item(1).firstChild.nodeValue;
                        // alert("accno>>>"+accno);
                        B_name=rcells.item(2).firstChild.nodeValue;
                         bankid=rcells.item(3).firstChild.nodeValue;
                         
                       //  alert("B_name>>>"+B_name);
                         bid=rcells.item(3).firstChild.nodeValue;
                         branchid=rcells.item(4).firstChild.nodeValue;
                       // alert("branchid>>>"+branchid);
                        // bid=document.getElementById("hbid").value;
                        // branchid=document.getElementById("hbranchid").value;
                        // addr=document.getElementById("haddr").value;
                          // addr=document.FAS_AccNumber_Form.haddr[i].value;
                          var j=++i;
                           addr=document.getElementById("haddr"+j).value;
                        //addr=rcells.item(5).firstChild.nodeValue;
                        // alert("addr>>>"+addr);
                         micr=document.getElementById("hmicr").value;
                         br_id=rcells.item(6).firstChild.nodeValue; 
                       
                      /* 
                         B_name=rcells.item(3).firstChild.nodeValue+"-"+rcells.item(5).firstChild.nodeValue;
                         micr=rcells.item(6).firstChild.nodeValue;
                         addr=rcells.item(7).firstChild.nodeValue;*/
                         break;
                        }
                    }
             //}
           
            
        }
            
        Minimize();
        opener.doParentAcc_NO(accno,bankid,br_id,B_name,micr,addr,bid,branchid);
       // opener.doParentAcc_NO(accno,B_name,addr,micr);
        return true;
   }
