<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>

<!DOCTYPE html>
<html>
<head>
    <title>Cadastrar Evento</title>
    <style>
        /* Estilos para deixar o formulário mais agradável */
        body { font-family: sans-serif; padding: 20px; }
        .form-group { margin-bottom: 15px; }
        .room-box { border: 1px solid #ccc; padding: 15px; margin-bottom: 15px; border-radius: 5px; background-color: #f9f9f9; }
        .room-box h4 { margin-top: 0; }
        .btn, .btn-remover { padding: 8px 12px; border: none; border-radius: 4px; color: white; cursor: pointer; text-decoration: none; }
        .btn { background-color: #007bff; }
        .btn-remover { background-color: #dc3545; }
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
        <form:label path="name">Descrição do Evento:</form:label><br/>
        <form:input path="name" type="text" required="true" style="width: 400px;"/>
    </div>

    <hr/>
    <h3>Salas do Evento</h3>

    <%-- Container onde as salas serão adicionadas dinamicamente --%>
    <div id="rooms-container">
        <c:forEach items="${sectorDto.rooms}" var="room" varStatus="status">
            <div class="room-box">
                <form:hidden path="rooms[${status.index}].id"/>
                <h4>Sala ${status.index + 1}</h4>
                <div class="form-group">
                        <%-- A mágica está no 'path': "salas[index].campo" --%>
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
                <button type="button" class="btn-remover" onclick="removerSala(this)">Remover</button>
            </div>
        </c:forEach>
    </div>

    <%-- Botão para adicionar novas salas via JavaScript --%>
    <button type="button" id="add-room-btn" class="btn">Adicionar Sala</button>
    <hr/>

    <button type="submit" class="btn">Salvar Evento</button>
</form:form>
    <a href="<c:url value="${backUrl}"/>" class="btn" >Voltar</a>
<script>
    // Pega o botão de adicionar
    const addSalaBtn = document.getElementById('add-room-btn');
    // Pega o container onde os blocos de sala serão inseridos
    const salasContainer = document.getElementById('rooms-container');

    addSalaBtn.addEventListener('click', function() {
        // Conta quantos blocos de sala já existem para saber o próximo índice
        let roomIndex = salasContainer.getElementsByClassName('room-box').length;

        // Cria um novo div para o bloco da sala
        const novoBloco = document.createElement('div');
        novoBloco.classList.add('room-box');

        // Define o HTML interno do novo bloco. ATENÇÃO aos nomes dos inputs!
        novoBloco.innerHTML = `
                <h4>Sala ${roomIndex + 1}</h4>
                <div class="form-group">
                    <form:label path="salas${salaIndex}.nome">Nome da Sala:</form:label><br/>
                    <input id="salas${salaIndex}.nome" name="salas[${salaIndex}].nome" type="text" required="true" />
                </div>
                <div class="form-group">
                    <label for="salas${salaIndex}.valor">Valor (R$):</label><br/>
                    <input id="salas${salaIndex}.valor" name="salas[${salaIndex}].valor" type="number" step="0.01" required="true" />
                </div>
                <div class="form-group">
                    <label for="salas${salaIndex}.capacidade">Capacidade (Pessoas):</label><br/>
                    <input id="salas${salaIndex}.capacidade" name="salas[${salaIndex}].capacidade" type="number" required="true" />
                </div>
                <button type="button" class="btn-remover" onclick="removerSala(this)">Remover</button>
            `;

        // Adiciona o novo bloco ao container
        salasContainer.appendChild(novoBloco);
    });

    // Função para remover o bloco de sala
    function removerSala(button) {
        // O botão está dentro do div 'sala-bloco', então subimos na árvore do DOM para encontrá-lo e removê-lo
        button.closest('.room-box').remove();
        // Seria bom adicionar uma lógica para renumerar os títulos "Sala X" após a remoção, mas isso é um refinamento.
    }
</script>

</body>
</html>