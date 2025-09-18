<%@ page contentType="text/html;charset=UTF-8" language="java" isErrorPage="true" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE html>
<html>
<head>
    <title>Erro no Sistema</title>
    <style>
        body { font-family: sans-serif; text-align: center; padding-top: 50px; }
        .error-container { max-width: 600px; margin: auto; }
        h1 { color: #dc3545; }
    </style>
</head>
<body>
<div class="error-container">
    <h1>Algo deu errado.</h1>
    <p>${mensagemErro}</p>
    <p><a href="<c:url value='/home'/>">Voltar para a Página Inicial</a></p>
</div>
</body>
</html>