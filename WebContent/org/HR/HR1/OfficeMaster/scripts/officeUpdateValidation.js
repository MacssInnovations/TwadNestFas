/* NullCheck Validation */
            function nullcheckUp()
            {
               try
               {
                  if((document.frmOffice.txtOffName.value=="") || (document.frmOffice.txtOffName.value.length<=0))
                  {
                      alert("Please Enter Office Name");
                      document.frmOffice.txtOffName.focus();
                      return false;
                  }  
                  
                  if((document.frmOffice.txtShortName.value=="") || (document.frmOffice.txtShortName.value.length<=0))
                  {
                      alert("Please Enter Office Short Name");
                      document.frmOfficeOffice.txtShortName.focus();
                      return false;
                  }
                  
                  if((document.frmOffice.cmbHeadCode.value=="0") || (document.frmOffice.cmbHeadCode.selectedIndex<=0))
                  {
                      alert("Please Select a Head Cadre");
                      document.frmOffice.cmbHeadCode.focus();
                      return false;
                  }                  
                                                     
                  if((document.frmOffice.cmbLevelId.value=="0") || (document.frmOffice.cmbLevelId.selectedIndex<=0))
                  {
                      alert("Please Select a Office Level");
                      document.frmOffice.cmbLevelId.focus();
                      return false;
                  }
                  
                  var level=document.frmOffice.cmbLevelId.options[document.frmOffice.cmbLevelId.selectedIndex].text;
                  if(level!="Head Office")
                  {
                      if(level!="Region")
                      {
                          if((document.frmOffice.txtContrllingOfficeID.value=="")||(document.frmOffice.txtContrllingOfficeID.value.length<=0))
                          {
                              alert("Please Enter Controlling Office Id");
                              document.frmOffice.txtContrllingOfficeID.focus();
                              return false;
                          }
                      }                      
                      if((document.frmOffice.cmbPrimaryID.value=="0") || (document.frmOffice.cmbPrimaryID.value.length<=0))
                      {
                          alert("Please Select a Primary Work Nature");
                          document.frmOffice.cmbPrimaryID.focus();
                          return false;
                      }                      
                  }
               
                 return true; 
              }
              catch(e)
              {
                return false;
              }
            }