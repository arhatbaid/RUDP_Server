<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
         pageEncoding="ISO-8859-1" %>
<!DOCTYPE html>

<html>
<style>
    * {
        margin: 0;
        padding: 0;
        box-sizing: border-box;
    }

    .grid-container {
        display: grid;
        grid-template-columns: auto auto;
        position: relative;
        /* background-color: #2196F3; */
        padding: 0px;
        grid-row-gap: -5px;
        grid-column-gap: 0px;
        margin: auto;
    }

    .grid-item {
        /*  background-color: rgba(255, 255, 255, 0.8); */
        border: 0px solid rgba(0, 0, 0, 0);
        padding: 0px;

    }
</style>
<head>
    <meta charset="ISO-8859-1">
    <title>Phantom</title>
</head>
<body>
<script> setTimeout(function() {
        location.reload();
    }, 2000
); </script>

<div class="grid-container">
    <div class="grid-item"><img src="data:image/jpeg;base64,${screen_1}"/></div>
    <div class="grid-item"><img src="data:image/jpeg;base64,${screen_3}"/></div>
    <div class="grid-item"><img src="data:image/jpeg;base64,${screen_2}"/></div>

    <div class="grid-item"><img src="data:image/jpeg;base64,${screen_4}"/></div>
</div>
</body>
</html>