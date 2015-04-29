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

function hasChangedText(){
    var oldHash = $('#myHashOriginal').val();
    var newHash = hashCode(editor.getValue());
    return oldHash!=newHash;
}


/*
$(document).ready(function() {
    console.log( "ready!!!!!" + editor);
});
*/
