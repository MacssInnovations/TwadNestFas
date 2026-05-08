<%@ page contentType="text/html;charset=windows-1252" session="false"%>
<%@ page import="java.util.*" %>
<%@ page import="Servlets.News.NewsJava.*" %>

<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=windows-1252"></meta>
    <META HTTP-EQUIV="CACHE-CONTROL" CONTENT=" no-store, no-cache, must-revalidate" >
<META HTTP-EQUIV="CACHE-CONTROL" CONTENT=" pre-check=0, post-check=0, max-age=0" >

    
    <title>Twad Board Intranet Services</title>
    <link href="css/index.css" rel="stylesheet" media="screen"/>
    <script type="text/javascript"     src="index.js">     </script>
    <script type="text/javascript"     src="org/Security/scripts/twad.js">     </script>
  
    <script language="javascript" type="text/javascript">
    function tbfreeze()
    {
       // alert("THE TB FREEZE OPTION WILL BE GIVEN TOMORROW");
       // return true;
    }
 
    </script>
    <style type="text/css">
    input.btn{
   color:#050;
   font-family:'trebuchet ms',helvetica,sans-serif;
   font-size:50%;
   font-weight:bold;
   background-color:#fed;
   border:1px solid;
   border-top-color:#696;
   border-left-color:#696;
   border-right-color:#363;
   border-bottom-color:#363;
   filter:progid:DXImageTransform.Microsoft.Gradient
      (GradientType=0,StartColorStr='#ffffffff',EndColorStr='#ffeeddaa');}

    input.btnhov{
   border-top-color:#c63;
   border-left-color:#c63;
   border-right-color:#930;
   border-bottom-color:#930;}


#header a:hover {color: white; 
background-color:#e76931; text-decoration: underline;} 
    </style>
    <link rel='stylesheet' media='screen' type='text/css' href='css/examples.css'>
<script type='text/javascript' src='org/Security/scripts/jquery-1.js'></script>
<script type='text/javascript' src='org/Security/scripts/jquery.js'></script>
<script type='text/javascript' src='org/Security/scripts/jquery-impromptu.js'></script>
<script type='text/javascript' src='org/Security/scripts/common.js'></script>
                                                                                                        
  </head>
  <%
response.setHeader("Cache-Control","no-cache"); //HTTP 1.1
response.setHeader("Pragma","no-cache"); //HTTP 1.0
response.setDateHeader ("Expires", 0); //prevent caching at the proxy server
%>

  <body background="images/bg.gif">
<table width="100%" border="0" cellspacing="0" cellpadding="0">
      <!--<tr>
        <td height="0" background="images/inner_top_bg.gif"></td>
      </tr>
      <tr>
        <td height="2" bgcolor="#FFFFFF"></td>
      </tr>-->
      <tr>
        <td><table width="100%" border="0" cellspacing="0" cellpadding="0">
          <tr>
            <td align="center" valign="top" background="images/banner_bg.jpg"><table width="745" border="0" cellspacing="0" cellpadding="0">
              <tr>
                <td align="left" valign="top"><img src="images/banner1_top.jpg" width="378" height="207" title="TWAD Board"></td>
                <td align="left" valign="top"><img src="images/banner2.jpg" width="367" height="207" title="TWAD Board"/></td>
                <!--<td align="right" valign="bottom" ><img height="140" src="images/newyearwish1.jpg"></td>-->
              </tr>
            </table></td>
          </tr>
          <tr>
            <td align="center" valign="top" background="images/main_bg.gif"><table width="90%" border="0" cellpadding="0" cellspacing="0" bgcolor="#FFFFFF" class="white_border">
              <tr>
                <td height="42" colspan="2" align="left" valign="middle" bgcolor="#227293"><img src="images/head1a.gif" width="490" height="21" hspace="10" />
                &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                
                
                
                
                </td>
              </tr>
              <tr>
                <td height="1" colspan="2"></td>
              </tr>
              <tr>
                <td height="3" colspan="2" bgcolor="#B5B5B5"></td>
              </tr>
              <tr>
                <td height="2" colspan="2"></td>
              </tr>
              <tr>
                <td width="208" align="left" valign="top"><table width="198" border="0" cellspacing="0" cellpadding="0">
                  <tr>
                    <td height="21" align="left" valign="middle" bgcolor="#C80000"><img src="images/members_login.gif" width="84" height="15" hspace="8" /></td>
                  </tr>
                  <tr>
                    <td height="1" bgcolor="#FFFFFF"></td>
                  </tr>
                  <tr>
                    <td align="left">
                    <table width="198" border="0" cellpadding="0" cellspacing="0" class="red_border">
                      <tr>
                        <td height="10"></td>
                      </tr>
                      
                       <!--<tr>
                        <td align="center" valign="top">
                        <form name="frmindex" id="frmindex" >
                        <table width="180" border="0" cellspacing="0" cellpadding="0">                        
                          <tr>
                            <td height="25" align="left" valign="middle" class="grey_txt">User Name</td>
                            <td align="left" valign="middle">
                            <input type="text" name="txtID" class="textbox" title="Enter UserName" maxlength="12" size="12"  ></input>
                            </td>
                          </tr>
                          <tr>
                            <td height="25" align="left" valign="middle" class="grey_txt">Password</td>
                            <td align="left" valign="middle">
                            <input type="password" name="txtPassword" class="textbox" maxlength="12" size="12" onkeypress="return buttonsubmit(event)"  title="Enter Password"></input>
                            </td>
                          </tr>
                          
                          <tr>
                            <td height="25">&nbsp;</td>
                            <td align="left" valign="bottom">
                            <a id="loginid" href="#" onkeypress="return tbfreeze();return buttonsubmit(event)">                            
                            <img src="images/button.gif" onclick="return notNull();" name="butSubmit" alt="Login" width="60" 
                                          height="18" border="0" /></a>
                                          </td>
                          </tr>
                          <tr>
                            <td height="25" colspan="2" align="center" valign="middle"><span class="black_txt">Forgot your</span> <a href="#" class="red_txt">Password? </a>
                            </td>
                            </tr>
                        </table></form></td>
                      </tr>-->
                      
                    </table></td>
                  </tr>
                </table></td>
                <td align="left" valign="top"><table width="100%" border="0" cellpadding="0" cellspacing="0" bgcolor="#FFFFFF" class="grey_border">
                  <tr>
                    <td height="10"></td>
                  </tr>
                  <tr>
                    <td align="center" valign="top"><table width="95%" border="0" cellspacing="0" cellpadding="0">
                      <tr>
                        <td align="left" valign="top" class="content"><div align="justify">The Intranet system for TWAD Board facilitates centralized data storage and retrieval besides information analysis pertaining to Project Monitoring, Financial Accounting and HR Management. This system will be made available to all the offices of TWAD Board through a network connecting the Head Office with all the Regions, Circles and Divisions.</div></td>
                      </tr>
                    </table></td>
                  </tr>
                  <tr>
                    <td height="15"></td>
                  </tr>
                  <tr>
                    <td height="1" bgcolor="#DADBDC"></td>
                  </tr>
                  <tr>
                    <td align="left" valign="top"><table width="100%" border="0" cellspacing="0" cellpadding="0">
                      <tr>
                        <td height="42" align="left" valign="middle"  ><img src="images/rainwater.gif" width="283"/>
                        
<!--<object classid="clsid:D27CDB6E-AE6D-11cf-96B8-444553540000"
codebase="http://download.macromedia.com/pub/shockwave/cabs/flash/swflash.cab#version=7,0,19,0"
width="300" height="42">
      <param name="movie" value="images/test.swf" />
      <param name="quality" value="high" />
      <embed src="images/test.swf" quality="high" 
      pluginspage="http://www.macromedia.com/go/getflashplayer" 
      type="application/x-shockwave-flash" width="300" height="42"></embed>
    </object>-->
                        
                        &nbsp;</td>
                        <td align="center" valign="middle" class="blue_text">
			Click here to access <a href="index.html"><Strong>TwadPhase2</Strong></a>
			</td>
                      </tr>
                    </table></td>
                  </tr>
                  <tr>
                    <td height="6" align="left" valign="top" bgcolor="#DDDDDD"></td>
                  </tr>
                </table></td>
              </tr>
              <tr>
                <td height="3" colspan="2"></td>
              </tr>
              <tr>
                <td align="left" valign="top"><table width="198" border="0" cellspacing="0" cellpadding="0">
                  <tr>
                    <td height="21" align="left" valign="middle" background="images/blue__tablebg.gif"><img src="images/eventsnew.gif" width="100"/></td>
                  </tr>
                 
                  
                  <tr>
                    <td align="left" valign="top">
                    
                    <table width="198" border="0" cellpadding="0" cellspacing="0" class="blue_border">
                    <tr>
                    <td height="10"></td>
                    </tr>
                    
                         <tr>
                          <td height="210" align="center" valign="middle" class="black_normal">
                                     
                                      
                                      <!--<marquee id="ieslider" onMouseover="this.scrollAmount=0" onMouseout="this.scrollAmount=2" 
                                                 scrollAmount="2" width='198' height="75"
                                                 direction="up" 
                                                 style="border:0;">-->
                                                

                                          <table  border="0"
                                                 cellspacing="0"
                                                 cellpadding="0">
                                        <!-- <tr>
                                              <td height="20" align="left"
                                                  valign="top" class="content">
                                                <a style="text-decoration:none;cursor:pointer;color:blue" href="http://218.248.23.11/ITF/payslip.aspx"
                                                   target="_blank">IT-Salary Summary</a>
                                              </td>
                                            </tr>  -->
                                                 
                                           
                                            
                                            <tr>
                                              <td height="20" align="left"
                                                  valign="top" class="content">
                                                <a style="text-decoration:none;cursor:pointer;color:blue" href="http://www.twadboard.gov.in"
                                                   target="_blank">TWAD Board Website</a>
                                              </td>
                                            </tr>
                                             <tr>
                                              <td height="20" align="left"
                                                  valign="top" class="content">
                                                <a style="text-decoration:none;cursor:pointer;color:blue" href="http://218.248.23.7/ruralws"
                                                   target="_blank">Progress Monitoring Site</a>
                                              </td>
                                            </tr>
                                             <tr>
                                              <td height="20" align="left"
                                                  valign="top" class="content">
                                                <a style="text-decoration:none;cursor:pointer;color:blue" href="http://ddws.gov.in"
                                                   target="_blank">GOI Progress Updating Site</a>
                                              </td>
                                            </tr>
                                        
                                            <tr>
                                              <td height="20" align="left"
                                                  valign="top" class="content">
                                                <a style="text-decoration:none;cursor:pointer;color:blue" href="http://mail.twadboard.gov.in"
                                                   target="_blank">TWAD Mail Server</a>
                                              </td>
                                            </tr>
                                             <tr>
                                              <td height="20" align="left"
                                                  valign="top" class="content">
                                                <a style="text-decoration:none;cursor:pointer;color:blue" href="http://www.aboutrainwaterharvesting.in"
                                                   target="_blank"> Rainwater Harvesting Website</a>
                                              </td>
                                            </tr>
                                            <tr>
                                              <td height="20" align="left"
                                                  valign="top" class="content">
                                                <a style="text-decoration:none;cursor:pointer;color:blue" href="http://www.tenders.tn.gov.in"
                                                  target="_blank">NIC Tender hosting website</a>
                                              </td>
                                            </tr>
					    <tr>
                                              <td height="20" align="left"
                                                  valign="top" class="content">
                                                <a style="text-decoration:none;cursor:pointer;color:blue" href="http://218.248.23.11/calix/edplogin.aspx"
                                                  target="_blank">TNSWAN Maintenance System</a>
                                              </td>
                                            </tr>
                                             <tr>
                                              <td height="20" align="left"
                                                  valign="top" class="content">
                                                <a style="text-decoration:none;cursor:pointer;color:blue" href="http://218.248.23.11/ltaentry/edplogin.aspx"
                                                   target="_blank">Longterm Advance Entry</a>
                                              </td>
                                            </tr>
                                           
                                            
                                             <!--<tr>
                                              <td height="25" align="left"
                                                  valign="top" class="content">
                                                <a style="text-decoration:none;cursor:pointer;color:blue" href="http://karuvoolam.tn.gov.in"
                                                   target="_blank">Health Insurance Scheme Website</a>
                                              </td>
                                            </tr>-->
                                          </table>
                                        <!--</marquee>-->
      
                          </td>
                        </tr>
                     
                      <!--<tr>
                        <td height="20" ></td>
                      </tr>-->
                    </table></td></tr>
                </table></td>
                
                <td align="left" valign="top" ><table width="100%" border="0" cellspacing="0" cellpadding="0">
                  <tr>
                    <td width="340" align="left" valign="top"><table width="340" border="0" cellspacing="0" cellpadding="0">
                      <tr>
                        <td height="21" background="images/blue__tablebg.gif"><img src="images/news.gif" width="32" height="12" hspace="8" /></td>
                      </tr>
                      <tr>
                        <td align="left" valign="top"><table width="340" border="0" cellpadding="0" cellspacing="0" class="blue_border">
                          <!--<tr>
                            <td height="85"></td>
                          </tr>-->
                          <tr>
                          <td height="220" align="center" valign="middle" class="black_normal">
                          <marquee id="ieslider" onMouseover="this.scrollAmount=0" onMouseout="this.scrollAmount=2" 
                                                 scrollAmount="2" width='340' height="190"
                                                 direction="up" 
                                                 style="border:0;">   
                           <table>
                    <%
                             
                            System.out.println("Path checking .........................");
                            boolean flag1=false;
                            
                        String path=config.getServletContext().getRealPath("news.xml");
                        System.out.println("context path is :"+config.getServletContext().getRealPath(""));;
                        //String path=request.getContextPath()+"/news.xml";
                        //System.out.println("path is :"+path);
                        //System.out.println("path length is "+path.length());
                        if(path.length()!=0)
                        {
                        NewsSaxParserBean1 saxparser1 = new NewsSaxParserBean1();
                            String str = null;
                        //    String path=config.getServletContext().getRealPath("/WEB-INF/News_Xml/news.xml");
                            //System.out.println("path is.......... "+path);
                            Collection stocks = saxparser1.parse(path);
                            Iterator ir = stocks.iterator();
                            %>
                            
                            <%
                            String capId="";
                            String attSlNo="";
                            String strcaption="";
                            String strdesc="";
                            String strlink="";
                            String ico="";
                            boolean flag=true;
                            int i=0;
                            while(ir.hasNext()) {
                            i++;
                             
                              NewsElement element = (NewsElement) ir.next();
                              System.out.println("ELEMENT VALUE  checking by ratha=====>"+element.getValue());
                              System.out.println("iteration by ratha=====>"+i);
                             
                             
                              //String tag = element.getLocalName();
                              String tag = element.getQname();
                            System.out.println("Tag value is ratha=============>"+tag);
                              
                              
                              
                            
                              if(tag.equals("capId")) { 
                                 capId=element.getValue();
                               //  System.out.println("caption is :"+capId);                
                              
                                 }
                             if(tag.equals("caption")) { 
                                 strcaption=element.getValue();
                                }     
                              if(tag.equals("icon"))  
                              {
                               ico=element.getValue();  
                               System.out.println("ico1..."+ico);
                             
                           
                            
                                
                                 //System.out.println("caption is :"+strcaption);
                            //     }
                             //  if(tag.equals("desc"))
                             //   {
                             //   strdesc=element.getValue();
                                //System.out.println("desc is :"+strdesc);
                             //   }
                             // if(tag.equals("link"))
                             //   {
                            //    strlink=request.getContextPath()+"/"+element.getValue();
                               // System.out.println("link is :"+strlink);
                              // System.out.println("capId..."+capId);
                              // System.out.println("ico..."+ico);
                             //  System.out.println("strcaption..."+strcaption);
                              
                               %>
                               <input type="hidden" name='hidLink' id='hidLink' value=<%=strlink%> ></input>
                               <input type="hidden" name='hidCap' id='hidCap' value=<%=strcaption%> ></input>
                               <input type="hidden" name='hidDesc' id='hidDesc' value=<%=strdesc%> ></input>
                               <input type="hidden" name='hidIcon' id='hidIcon' value=<%=ico%> ></input>
                               <%
                                
                                 if(ico.equals("Y"))
                                 {
                                 System.out.println("inside yes");
                               %>
                            <tr>
                              <td  valign="middle" class="content">
                                 <!--<a style="text-decoration:none;cursor:hand;color:blue" onclick='newPage()' target='_blank'><%=strcaption %></a>-->
                              <!--   dhana changes_9feb2012 -->
                              <a style="text-decoration:none;cursor:hand;color:blue" onclick="newPage2('<%=capId%>')" target='_blank'><%=strcaption %></a>
                                 <img src="images/new.gif" height="13" width="15">  
                                 <!--<br>&nbsp;&nbsp;&nbsp;&nbsp;<font size='1px'><%=strdesc%></font>-->
                              </td>
                            </tr>  
                         <% 
                         }
                         
                         else if(ico.equals("N"))
                         {
                        // System.out.println("inside no");
                         
                         %>
                         <tr>
                              <td  valign="middle" class="content">
                                 
                                 <a style="text-decoration:none;cursor:hand;color:blue" onclick="newPage2('<%=capId%>')" target='_blank'><%=strcaption %></a>
                                 
                                 
                              </td>
                            </tr>
                         <%
                         }
                         }
                        
                         }
                          }
                          else
                          {
                          System.out.println("There is no corresponding xml file....");
                          }
                         %>
                          </table>
                          </marquee>
                          </td>
                          </tr>
                        <!--  <tr>
                            <td align="center" valign="top">
                            <marquee id="ieslider" scrollAmount=1 width=323 height=75  direction=up style="border:0;">
                            <table width="95%" border="0" cellspacing="0" cellpadding="0">

                              <tr>
                                <td height="20" align="left" valign="top" class="content">
                                <a style="text-decorate:none;cursor:hand;color:blue"  onclick="javacript:loadPageInNewWindow('<%=request.getContextPath()%>/ECode_for_NMRs.pdf')">Employee Code for NMR Employees regularised from 9/2007 to 3/2008</a> 
                                </td>
                              </tr>
                                                        
                              <tr>
                                <td height="25" align="left" valign="top" class="content">
                                <a style="text-decorate:none;cursor:hand;color:blue"  onclick="javacript:loadPageInNewWindow('<%=request.getContextPath()%>/e_code_for_858_employees.pdf')">Employee Code for 858 Employees</a> 
                                </td>
                              </tr>
                              
                              <tr>
                                <td height="25" align="left" valign="top" class="content">
                                <a style="text-decorate:none;cursor:hand;color:blue"  onclick="javacript:loadPageInNewWindow('<%=request.getContextPath()%>/newly_appointed_AE_emp_codes_allocated.pdf')">Employee Code for New AEs</a>    
                                </td>
                              </tr>
                              
                              <tr>
                                <td height="25" align="left" valign="top" class="content">
                                <a style="text-decorate:none;cursor:hand;color:blue"  onclick="javacript:loadPageInNewWindow('<%=request.getContextPath()%>/emp_app_nov.pdf')">Emp.Code for Employees Appointed during Nov,2007</a>    
                                </td>
                              </tr>
                              
                            </table>
                            </marquee>
                            </td>
                          </tr>-->
                          <!--<tr>
                            <td height="20" align="right">
                          </tr>-->
                        </table></td>
                      </tr>
                    </table></td>
                    <td align="right" valign="top"><table width="98%" border="0" cellspacing="0" cellpadding="0">
                      <tr>
                        <td height="21" align="left" valign="middle" background="images/blue__tablebg.gif"><img src="images/notice_board.gif" width="69" height="11" hspace="8" /></td>
                      </tr>
                      <tr>
                        <td align="left" valign="top">
                        <table width="100%" border="0" cellpadding="0" cellspacing="0" class="blue_border">
                          <tr>
                            <td height="10"></td>
                          </tr>
                          <tr>
                            <td height="10" align="left" valign="middle" class="content">
                            <a style="text-decorate:none;cursor:hand;color:blue"
                            onclick="javacript:loadPageInNewWindow('<%=request.getContextPath()%>/viewbirthday.jsp')">Birthday Wishes-Employees</a>
                            </td>
                          </tr>
                          <tr>
                            <td height="10" align="left" valign="middle" class="content">
                            <a style="text-decorate:none;cursor:hand;color:blue"
                            onclick="javacript:loadPageInNewWindow('<%=request.getContextPath()%>/BorndayWishPensioner.jsp')">Birthday Wishes-Pensioners</a>
                            </td>
                          </tr>
                          <tr>
                          <td height="10">
                          </td>
                          </tr>
                          <tr>
                            <td height="10" align="left" valign="middle" class="content">
                            <a style="text-decorate:none;cursor:hand;color:blue"
                            onclick="javacript:loadPageInNewWindow('<%=request.getContextPath()%>/Retirement.jsp')">Employees due for Retirement this month</a>
                            </td>
                          </tr>
                          <tr>
                          <td height="3">
                          </td>
                          </tr>
                            <tr>
                            <td class="blue_text">
                               <hr color='#8fb1d6'>
                            </td>
                          </tr>
							<tr>
                            <td height="10" align="left" valign="middle" class="content">
                            <a style="text-decorate:none;cursor:hand;color:blue"
                            onclick="javacript:loadPageInNewWindow('<%=request.getContextPath()%>/TWAD_Hand_Out.pdf')">TWAD Board HandOut</a>
                            </td>
                          </tr>
			 			<tr>
                          <td height="3">
                          </td>
                          </tr>
                          <tr>
                            <td class="blue_text">
                               <hr color='#8fb1d6'>
                            </td>
                          </tr>
                          <tr>
                            <td align="center" valign="top">
                            <marquee id="ieslider" onMouseover="this.scrollAmount=0" onMouseout="this.scrollAmount=2" scrollAmount=1 width=323 height=107  direction=up style="border:0;">
                            <table width="95%" border="0" cellspacing="0" cellpadding="0">
				<tr>
                                <td height="20" align="left" valign="top" class="content">
                                <a style="text-decorate:none;cursor:hand;color:blue"  onclick="javacript:loadPageInNewWindow('<%=request.getContextPath()%>/Comp_Ground_Appoint_Order2.pdf')">Employee Code for newly appointed employees  (through compassionate ground order 2)</a> 
                                </td>
                              </tr>
                             <tr>
                                <td height="20" align="left" valign="top" class="content">
                                <a style="text-decorate:none;cursor:hand;color:blue"  onclick="javacript:loadPageInNewWindow('<%=request.getContextPath()%>/Ecode_for_NewEmp.pdf')">Employee Code for newly appointed employees (through compassionate ground)</a> 
                                </td>
                              </tr>
				<tr>
                                <td height="20" align="left" valign="top" class="content">
                                <a style="text-decorate:none;cursor:hand;color:blue"  onclick="javacript:loadPageInNewWindow('<%=request.getContextPath()%>/EMPCODE FOR NEW AEs.pdf')">Employee Code Assistant Engineers recruited in 2012</a> 
                                </td>
                              </tr>
                            <tr>
                                <td height="20" align="left" valign="top" class="content">
                                <a style="text-decorate:none;cursor:hand;color:blue"  onclick="javacript:loadPageInNewWindow('<%=request.getContextPath()%>/ECode_for_NMRs.pdf')">Employee Code for NMR Employees regularised from 9/2007 to 3/2008</a> 
                                </td>
                              </tr>
                                                        
                              <tr>
                                <td height="20" align="left" valign="top" class="content">
                                <a style="text-decorate:none;cursor:hand;color:blue"  onclick="javacript:loadPageInNewWindow('<%=request.getContextPath()%>/e_code_for_858_employees.pdf')">Employee Code for 858 Employees</a> 
                                </td>
                              </tr>
                              
                              <tr>
                                <td height="20" align="left" valign="top" class="content">
                                <a style="text-decorate:none;cursor:hand;color:blue"  onclick="javacript:loadPageInNewWindow('<%=request.getContextPath()%>/newly_appointed_AE_emp_codes_allocated.pdf')">Employee Code for New AEs</a>    
                                </td>
                              </tr>
                              
                              <tr>
                                <td height="20" align="left" valign="top" class="content">
                                <a style="text-decorate:none;cursor:hand;color:blue"  onclick="javacript:loadPageInNewWindow('<%=request.getContextPath()%>/emp_app_nov.pdf')">Emp.Code for Employees Appointed during Nov,2007</a>    
                                </td>
                              </tr>
                              
                              <tr>
                                <td height="20" align="left" valign="top" class="content">
                                <a style="text-decorate:none;cursor:hand;color:blue"  onclick="javacript:loadPageInNewWindow('<%=request.getContextPath()%>/Outstanding liability.zip')">The details for outstanding liability</a>    
                                </td>
                              </tr>
                              
                            </table>
                            </marquee>
                            </td>
                          </tr>
                          <tr>
                            <td height="10"></td>
                          </tr>
                        </table></td>
                      </tr>
                      
                    </table></td>
                  </tr>
                  
                </table></td>
              </tr>
              
            </table></td>
          </tr>
        </table></td>
      </tr>
      <!--<tr>
        <td height="1" bgcolor="#8FB1D6"></td>
      </tr>-->
      <tr>
        <td height="20" align="right" valign="middle" background="images/bg.gif" bgcolor="#BFBFBF" class="content">Designed &amp; Developed by : National Informatics Centre, Chennai. </td>
      </tr>
</table>
</body>

</html>
<%
try
{
   String message=request.getParameter("message"); 
   System.out.println("Message:"+message);
   request.setAttribute("message","");
   if(message!=null)
   {
           if(message.equals("yes"))
           {            
        //out.println("<label style='color:rgb(255,0,0);'>Invalid User name or Password</label>");    
        out.println("<script language='javascript'>alert('Invalid User name or Password.');</script>");
           }
           else if(message.equals("noprofile"))
           {            
        //out.println("<label style='color:rgb(255,0,0);'>Your profile has to\nbe updated. Please contact \nSystem Administrator.</label>");    
        out.println("<script language='javascript'>alert('Your profile has to be updated.\\n Please contact System Administrator.');</script>");
           }
           else if(message.equals("retired"))
           {            
        //out.println("<label style='color:rgb(255,0,0);'>Your profile has to\nbe updated. Please contact \nSystem Administrator.</label>");    
        out.println("<script language='javascript'>alert('Login is disabled based on Employee Retirement data.');</script>");
           }
           else if(message.equals("dbnill"))
           {            
        //out.println("<label style='color:rgb(255,0,0);'>Your profile has to\nbe updated. Please contact \nSystem Administrator.</label>");    
        out.println("<script language='javascript'>alert(\"Database Service is not available.\\n Please Contact System Administrator.\");</script>");
           }
            else if(message.equals("logindisabled"))
           {            
                out.println("<script language='javascript'>alert(\"Login Disabled.\");</script>");
           }
    }
}catch(Exception e)
{
                // while loading first
}
%>

