<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<!DOCTYPE html>
<html>
<head>
    <title>Cadastrar Evento</title>
    <style>
        body { font-family: sans-serif; padding: 20px; }
        .form-group { margin-bottom: 15px; }
        .room-box { border: 1px solid #ccc; padding: 15px; margin-bottom: 15px; border-radius: 5px; background-color: #f9f9f9; }
        .room-box h4 { margin-top: 0; }
        .btn, .btn-remove { padding: 8px 12px; border: none; border-radius: 4px; color: white; cursor: pointer; text-decoration: none; }
        .btn { background-color: #007bff; }
        .btn-remove { background-color: #dc3545; }
        input[type="text"], input[type="number"] { width: 95%; padding: 8px; border: 1px solid #ccc; border-radius: 4px; }
    </style>
</head>
<body>

<h1>Cadastrar Novo Setor</h1>

<c:if test="${not empty sucesso}"><div style="color: green;">${sucesso}</div></c:if>
<c:if test="${not empty erro}"><div style="color: red;">${erro}</div></c:if>

<form:form modelAttribute="sectorDto" action="/sector/save" method="post">
    <form:hidden path="id"/>
    <div class="form-group">
        <form:label path="name">Nome do Setor:</form:label><br/>
        <form:input path="name" type="text" required="true" style="width: 400px;"/>
    </div>

    <hr/>
    <h3>Salas do Setor</h3>

    <div id="rooms-container">
        <c:forEach items="${sectorDto.rooms}" var="room" varStatus="status">
            <div class="room-box">
                <form:hidden path="rooms[${status.index}].id"/>
                <h4>Sala ${status.index + 1}</h4>
                <div class="form-group">

                    <form:label path="rooms[${status.index}].name">Nome da Sala:</form:label><br/>
                    <form:input path="rooms[${status.index}].name" type="text" required="true" />
                </div>
                <div class="form-group">
                    <form:label path="rooms[${status.index}].price">Valor (R$):</form:label><br/>
                    <form:input path="rooms[${status.index}].price" type="number" step="0.01" required="true" />
                </div>
                <div class="form-group">
                    <form:label path="rooms[${status.index}].capacity">Capacidade (Pessoas):</form:label><br/>
                    <form:input path="rooms[${status.index}].capacity" type="number" required="true" />
                </div>
                <button type="button" class="btn-remove" onclick="removeRoom(this)">Remover</button>
            </div>
        </c:forEach>
    </div>

    <button type="button" id="add-room-btn" class="btn">Adicionar Sala</button>
    <hr/>

    <button type="submit" class="btn">Salvar Evento</button>
</form:form>
    <a href="<c:url value="${backUrl}"/>" class="btn" >Voltar</a>
<script>
    let roomCounter = ${fn:length(sectorDto.rooms)}

    const addSalaBtn = document.getElementById('add-room-btn');
    // Pega o container onde os blocos de sala serão inseridos
    const salasContainer = document.getElementById('rooms-container');

    addSalaBtn.addEventListener('click', function() {

        let salaIndex = roomCounter;

        console.log("roomCounter",roomCounter);

        const novoBloco = document.createElement('div');
        novoBloco.classList.add('room-box');

        novoBloco.innerHTML =
                '<h4>Sala ' + (salaIndex+1) + '</h4>'+
                '<input type="hidden" name="rooms['+(salaIndex)+'].id">'+
                '<div class="form-group">' +
                    '<label for="rooms'+(salaIndex)+'.name">Nome da Sala:</label><br/>' +
                    '<input id="rooms'+(salaIndex)+'.name" name="rooms['+(salaIndex)+'].name" type="text" required="true" />'
                +'</div>'+
                '<div class="form-group">'+
                    '<label for="rooms'+(salaIndex)+'.price">Valor (R$):</label><br/>'+
                    '<input id="rooms'+(salaIndex)+'.price" name="rooms['+(salaIndex)+'].price" type="number" step="0.01" required="true" />'+
                '</div>'+
                '<div class="form-group">'+
                    '<label for="rooms'+(salaIndex)+'.capacity">Capacidade (Pessoas):</label><br/>'+
                    '<input id="rooms'+(salaIndex)+'.capacity" name="rooms['+(salaIndex)+'].capacity" type="number" required="true" />'+
                '</div>'+
                '<button type="button" class="btn-remove" onclick="removeRoom(this)">Remover</button>';

        salasContainer.appendChild(novoBloco);

        roomCounter +=1;
    });

    function removeRoom(button) {
        button.closest('.room-box').remove();
    }
</script>

</body>
</html>