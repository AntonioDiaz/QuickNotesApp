<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<html>
<head>
    <link rel=stylesheet href="../css/docs.css">
    <link rel="stylesheet" href="../css/codemirror.css">
    <script src="http://ajax.googleapis.com/ajax/libs/jquery/1.11.2/jquery.min.js"></script>
    <script src="../javascript/codemirror.js"></script>
    <script src="../javascript/active-line.js"></script>
    <script src="../javascript/editor.js"></script>
    <style type="text/css">
        .CodeMirror {border-top: 1px solid #888; border-bottom: 1px solid #888;}
    </style>
</head>
<body>
    <table width="100%" border="0px" cellpadding="0px" cellspacing="0px">
        <tr>
            <td width="2%">&nbsp;</td>
            <td>
                <div style="font-weight: normal;" id='save_button'><a href="javascript: fSaveEditor()">guardar</a></div>
            </td>
        </tr>
    </table>
    <form name="myForm" action="save" method="post">
        <input id="myId" name="myId" type="hidden" value='<c:out value="${NOTE_FOUND.id}"></c:out>'>
        <input id="myText" name="myText" type="hidden">
        <input id="myHashOriginal" name="myHash" type="hidden">
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
            if (hasChangedText()) {
                //console.log('distintos ' + $('#save_button').css('font-weight', 'bold');
                $('#save_button').css('font-weight', 'bold');
            } else {
                $('#save_button').css('font-weight', 'normal');
            }
        });
        var myHash = hashCode(editor.getValue());
        $('#myHashOriginal').val(myHash);
    };

    $(window).keydown(function(event) {
        if(event.ctrlKey && event.keyCode == 83) {
            fSaveEditor();
            event.preventDefault();
        }
    });
</script>
</html>