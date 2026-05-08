function loadOffice_new(id)
{   
    //alert("here");
    if(id=="" || id==null)
    {
        alert("Enter or (Select An Office..Then Click choose..)");
        document.UpdateOldRecForm.cmbOfficeLevel_two.focus();
    }
    else
    {
    
               //alert(" in loadOffice");
                var url="../../../../../ServletLoadOfficeDet.con?command=nil&ID="+id;            
                var req=getTransport();
                req.open("GET",url,true);         
                req.onreadystatechange=function()
                {                
                   LoadOfficeDetails_new(req);             
                }
                req.send(null); 
    
    }
}

function LoadOfficeDetails_new(req)
{
   if(req.readyState==4)
    {
          if(req.status==200)
          {                
               // alert(req.responseText);
                var response=req.responseXML.getElementsByTagName("response")[0]; 
                //alert(response);
                var flag=response.getElementsByTagName("flag")[0].firstChild.nodeValue;               
                if(flag=="success")
                {
                  //  alert("entered");
                    var name=response.getElementsByTagName("name")[0].firstChild.nodeValue;
                   //alert(name);
                   // var level=response.getElementsByTagName("level")[0].firstChild.nodeValue;
                    //var type=response.getElementsByTagName("type")[0].firstChild.nodeValue;
                    var add1=response.getElementsByTagName("address1")[0].firstChild.nodeValue;
                   //alert("a1"+add1);
                    var add2=response.getElementsByTagName("address2")[0].firstChild.nodeValue;
                   //alert("a2"+add2);
                    var cityname=response.getElementsByTagName("city")[0].firstChild.nodeValue;
                   //alert("c"+cityname); 
                    var district=response.getElementsByTagName("district")[0].firstChild.nodeValue;
                   //alert("d"+district); 
                  //txt_ExtOffice_Name
                  //txt_ExtOffice_Address1
                  //txt_ExtOffice_Address2
                  //txt_ExtOffice_City
                  //cmb_ExtDistrict
                    if(name!="null")
                    {
                        document.UpdateOldRecForm.txt_ExtOffice_Name_two.value=name;
                    }
                   
                    if(add1!="null")
                    {
                        document.UpdateOldRecForm.txt_ExtOffice_Address1_two.value=add1;
                    }
                    if(add2!="null")
                    {
                        document.UpdateOldRecForm.txt_ExtOffice_Address2_two.value=add2;
                    }
                    if(cityname!="null")
                    {
                        document.UpdateOldRecForm.txt_ExtOffice_City_two.value=cityname;
                    }
                    if(district!="null")
                    {
                      document.UpdateOldRecForm.cmb_ExtDistrict_two.value=district;
                      //alert(" in district");
                    }
                    //document.UpdateOldRecForm.txtOffice_Name.value=name.firstChild.nodeValue;
                    
                    /*if(level=="Division" || level=="Sub-Division")
                        document.frmOffice.txtOfficeType.value=type;
                    else
                        document.frmOffice.txtOfficeType.value="Office level : " + level;*/
                        
                    //document.frmOffice.txtOfficeAddress.value=add;
                    //document.frmOffice.txtNewOfficeName.focus();
                    //disabling the third part
                       // document.UpdateOldRecForm.txtOffice_Id_two.disabled=true;
                        document.UpdateOldRecForm.txt_ExtOffice_Name_two.disabled=true;
                        document.UpdateOldRecForm.txt_ExtOffice_Address1_two.disabled=true;
                        document.UpdateOldRecForm.txt_ExtOffice_Address2_two.disabled=true;
                        document.UpdateOldRecForm.txt_ExtOffice_City_two.disabled=true;
                        document.UpdateOldRecForm.cmb_ExtDistrict_two.disabled=true;
                    //setting focus to the date field
                    document.UpdateOldRecForm.txt_DateShifting.focus();
                   // document.UpdateOldRecForm.cmb_ExtDistrict_two.disabled=true;
                    
                    document.UpdateOldRecForm.cmbOfficeLevel_two.selectedIndex=0;
                    document.UpdateOldRecForm.cmbOfficeType_two.selectedIndex=0;
                    document.UpdateOldRecForm.cmbSelectOffice_two.selectedIndex=0;
                    
                }
                else
                {
                    var mess=response.getElementsByTagName("message")[0].firstChild.nodeValue;
                    alert("Error Occured : \n" + mess); 
                    
                } 
          }
    }       
}
