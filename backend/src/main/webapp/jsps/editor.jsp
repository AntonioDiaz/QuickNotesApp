<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<html>
<head>
<link rel=stylesheet href="../css/docs.css">
<link rel="stylesheet" href="../css/codemirror.css">
<script src="../javascript/codemirror.js"></script>
<script src="../javascript/active-line.js"></script>
<script src="http://ajax.googleapis.com/ajax/libs/jquery/1.11.2/jquery.min.js"></script>

<style type="text/css">
	.CodeMirror {border-top: 1px solid #888; border-bottom: 1px solid #888;}
</style>
<script type="text/javascript">
window.onresize = function() {
	editor.setSize('100%', getHeight()-40);
};

function getWidth() {
	var x = 0;
	if (self.innerHeight) {
		x = self.innerWidth;
	} else if (document.documentElement && document.documentElement.clientHeight) {
		x = document.documentElement.clientWidth;
	} else if (document.body) {
		x = document.body.clientWidth;
	}
	return x;
}

function getHeight() {
	var y = 0;
	if (self.innerHeight) {
		y = self.innerHeight;
	} else if (document.documentElement && document.documentElement.clientHeight) {
		y = document.documentElement.clientHeight;
	} else if (document.body) {
		y = document.body.clientHeight;
	}
	return y;
}	
var decodeEntities = (function() {
	  // this prevents any overhead from creating the object each time
	  var element = document.createElement('div');

	  function decodeHTMLEntities (str) {
	    if(str && typeof str === 'string') {
	      // strip script/html tags
	      str = str.replace(/<script[^>]*>([\S\s]*?)<\/script>/gmi, '');
	      str = str.replace(/<\/?\w(?:[^"'>]|"[^"]*"|'[^']*')*>/gmi, '');
	      element.innerHTML = str;
	      str = element.textContent;
	      element.textContent = '';
	    }

	    return str;
	  }

	  return decodeHTMLEntities;
	})();

 function hashCode(myString){
	var hash = 0;
	if (myString.length == 0) return hash;
	for (i = 0; i < myString.length; i++) {
		char = myString.charCodeAt(i);
		hash = ((hash<<5)-hash)+char;
		hash = hash & hash; // Convert to 32bit integer
	}
	return hash;
}


function fSaveEditor() {
	if (editor.getValue().length>=50000) {
		alert ('maximum size exceeded');		
	} else {
		myText.value = editor.getValue();
		document.forms['myForm'].submit();
	}
}


$( document ).ready(function() {
    console.log( "ready!" );
});


</script>
</head>
<body>
<table width="100%" border="0px" cellpadding="0px" cellspacing="0px">
	<tr>
		<td width="2%">&nbsp;</td>
		<td>
			<a href="javascript: fSaveEditor()">guardar</a>
		</td>
	</tr>
</table>

<form name="myForm" action="save" method="post">
	<input id="myId" name="myId" type="hidden" value='<c:out value="${NOTE_FOUND.id}"></c:out>'>
	<input id="myText" name="myText" type="hidden">
	<input id="myHash" name="myHash" type="hidden">

</form>
<div id="code"></div>

</body>
    <script type="text/javascript">
      CodeMirror.commands.autocomplete = function(cm) {
          CodeMirror.showHint(cm, CodeMirror.hint.html);
      }
      window.onload = function() {
        editor = CodeMirror(document.getElementById("code"), {
          mode: "text/html'd",
    	  styleActiveLine: true,
    	  lineNumbers: true,          
        });
        editor.setSize('100%', getHeight()-40);
        editor.setValue(decodeEntities("<c:out value="${NOTE_TEXT}"></c:out>"));
        editor.on("change", function(cm, change) {
            console.log( "Handler for .change() called." );
        })
      };
      $(window).keydown(function(event) {
          if(event.ctrlKey && event.keyCode == 83) {
            fSaveEditor();
            event.preventDefault();
          }
        });
    </script>
</html>