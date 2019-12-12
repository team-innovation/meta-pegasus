/*var b=document.getElementsByTagName("svg")[0]

var mousewheelevt=(/Firefox/i.test(navigator.userAgent))? "DOMMouseScroll" : "mousewheel" //FF doesn't recognize mousewheel as of FF3.x

if (b.attachEvent)
	b.attachEvent("on"+mousewheelevt, mouseWheelHandler)
else if (b.addEventListener)
	b.addEventListener(mousewheelevt, mouseWheelHandler, false);
*/
document.getElementsByTagName('svg')[0].addEventListener("mousewheel", mouseWheelHandler, false);
document.getElementsByTagName('svg')[0].addEventListener("mousedown", mouseDownHandler, false);
document.getElementsByTagName('svg')[0].addEventListener("mouseup", mouseUpHandler, false);
document.getElementsByTagName('svg')[0].addEventListener("mousemove", mouseMoveHandler, false);

var scrollSensitivity = 0.2
var isMouseDown = 0

function mouseWheelHandler(e) {
    
    var evt = window.event || e;
    var scroll = evt.detail ? evt.detail * scrollSensitivity : (evt.wheelDelta / 120) * scrollSensitivity;
    
    var transform = document.getElementById("viewport").getAttribute("transform")
//.replace(/ /g, "");
    
    var vector = transform.substring(transform.indexOf("(")+1, transform.indexOf(")")).split(",")
    vector[0] = (+vector[0] + scroll) + '';
    vector[3] = vector[0];
    
    document.getElementById("viewport").setAttribute("transform", "matrix(" + vector.join() + ")");
    
    return true; 
}

function mouseDownHandler(e) {
 var evt = window.event || e;
 console.log("Down: "+evt.clientX+" "+evt.clientY) 
 isMouseDown = 1;
 return true;
}

function mouseUpHandler(e) {
 var evt = window.event || e;
 console.log("Up: "+evt.clientX+" "+evt.clientY) 
 isMouseDown = 0;
 return true;
}

function mouseMoveHandler(e) {
	 var evt = window.event || e;
	 if (isMouseDown) {
  		console.log("Move: "+evt.clientX+" "+evt.clientY+" | "+evt.movementX+" "+evt.movementY)  
	    var transform = document.getElementById("viewport").getAttribute("transform")
//.replace(/ /g, "");
    
    var vector = transform.substring(transform.indexOf("(")+1, transform.indexOf(")")).split(",")
    vector[4] = (+vector[4] + evt.movementX) + '';
    vector[5] = (+vector[5] + evt.movementY) + '';
    
    document.getElementById("viewport").setAttribute("transform", "matrix(" + vector.join() + ")");
    
    return true; 
	 }
}

