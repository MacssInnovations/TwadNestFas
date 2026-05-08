
                        var marqueewidth="150px"
                        var marqueeheight="120px"
                        var marqueespeed=1
                        var pauseit=1
                        var marqueecontent='<font face=Arial color=MAROON size=2>'
                     var content_id=document.getElementById("content_tot")
                     //alert(content_id);
                     //alert(content_id.value);
                     marqueecontent=content_id.value;
                     //  marqueecontent=marqueecontent+document.getElementById("content_tot");
                     //alert("vals  :"+document.getElementById("content_tot").value);
                      //  marqueecontent=marqueecontent +'<A href="http://www.annauniv.edu/tnea2006_new.doc" Style="text-decoration:none">Event1<BR><BR>'
                      //  marqueecontent=marqueecontent +'<A href="http://www.annauniv.edu/tnea2006_new.doc" Style="text-decoration:none">Event2<BR><BR>'
                      //  marqueecontent=marqueecontent +'<A href="http://www.annauniv.edu/tnea2006_new.doc" Style="text-decoration:none">Event3<BR><BR>'
                      //  marqueecontent=marqueecontent +'<A href="http://www.annauniv.edu/tnea2006_new.doc" Style="text-decoration:none">Event4<BR><BR>'
                        marqueecontent=marqueecontent +'</A><BR><BR>'
                        marqueecontent=marqueecontent + '</b></font>'  

                        marqueespeed=(document.all)? marqueespeed : Math.max(1, marqueespeed-1) //slow speed down by 1 for NS
                        var copyspeed=marqueespeed
                        var pausespeed=(pauseit==0)? copyspeed: 0
                        var iedom=document.all||document.getElementById
                        var actualheight=''
                        var cross_marquee, ns_marquee

function populate(){
    if (iedom){
            cross_marquee=document.getElementById? document.getElementById("iemarquee") : document.all.iemarquee
            cross_marquee.style.top=parseInt(marqueeheight)+8+"px"
            cross_marquee.innerHTML=marqueecontent
            actualheight=cross_marquee.offsetHeight
              }
    else if (document.layers){
            ns_marquee=document.ns_marquee.document.ns_marquee2
            ns_marquee.top=parseInt(marqueeheight)+8
            ns_marquee.document.write(marqueecontent)
            ns_marquee.document.close()
            actualheight=ns_marquee.document.height
              }
    lefttime=setInterval("scrollmarquee()",20)
}

window.onload=populate

function scrollmarquee(){
    if (iedom){
        if (parseInt(cross_marquee.style.top)>(actualheight*(-1)+8))
            cross_marquee.style.top=parseInt(cross_marquee.style.top)-copyspeed+"px"
        else
            cross_marquee.style.top=parseInt(marqueeheight)+8+"px"
               }
    else if (document.layers){
        if (ns_marquee.top>(actualheight*(-1)+8))
            ns_marquee.top-=copyspeed
        else
            ns_marquee.top=parseInt(marqueeheight)+8
                }
}

if (iedom||document.layers){
with (document){
if (iedom){
            write('<div style="position:relative;width:'+marqueewidth+';height:'+marqueeheight+';overflow:hidden" onMouseover="copyspeed=pausespeed" onMouseout="copyspeed=marqueespeed">')
            write('<div id="iemarquee" style="position:absolute;left:0px;top:0px;width:100%;">')
            write('</div></div>')
           }
else if (document.layers){
            write('<ilayer width='+marqueewidth+' height='+marqueeheight+' name="ns_marquee">')
            write('<layer name="ns_marquee2" width='+marqueewidth+' height='+marqueeheight+' left=0 top=0 onMouseover="copyspeed=pausespeed" onMouseout="copyspeed=marqueespeed"></layer>')
            write('</ilayer>')
            }
                }
}
