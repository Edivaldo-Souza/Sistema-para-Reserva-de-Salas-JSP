<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %> <!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Lista de Salas</title>
    <style>
        body { font-family: sans-serif; }
        table { border-collapse: collapse; width: 60%; margin: 20px auto; }
        th, td { border: 1px solid #dddddd; text-align: left; padding: 8px; }
        th { background-color: #f2f2f2; }
        h1 { text-align: center; }
    </style>
</head>
<body>

<h1>Nossas Salas</h1>

<table>
    <thead>
    <tr>
        <th>ID</th>
        <th>Nome</th>
        <th>Preço</th>
        <th>Capacidade</th>
    </tr>
    </thead>
    <tbody>
    <c:forEach var="room" items="${rooms}">
        <tr>
            <td>${room.id}</td>
            <td>${room.name}</td>
            <td>R$ ${room.price}</td>
            <td>${room.capacity}</td>
        </tr>
    </c:forEach>
    </tbody>
</table>

</body>
</html>