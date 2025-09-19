<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE html>
<html>
<head>
    <title>Painel de Gerenciamento</title>
    <style>
        body { font-family: sans-serif; margin: 20px; }
        .nav-buttons { margin-bottom: 20px; }
        .btn {
            padding: 10px 15px; text-decoration: none; color: white;
            background-color: #007bff; border-radius: 5px; margin-right: 10px;
        }
        .btn.active { background-color: #0056b3; font-weight: bold; }
        table { border-collapse: collapse; width: 80%; margin-top: 20px; }
        th, td { border: 1px solid #ddd; padding: 8px; text-align: left; }
        th { background-color: #f2f2f2; }
        .sector-card {
            border: 1px solid #ccc;
            border-radius: 8px;
            padding: 20px;
            margin-bottom: 20px;
            box-shadow: 0 2px 5px rgba(0,0,0,0.1);
        }
        .sector-header {
            display: flex;
            justify-content: space-between;
            align-items: center;
        }
        .sector-header h2 { margin: 0; }
        .expand-btn {
            padding: 8px 12px;
            border: none;
            border-radius: 4px;
            background-color: #007bff;
            color: white;
            cursor: pointer;
            text-decoration: none;
        }
        .rooms-container {
            /* Começa escondido por padrão */
            display: none;
            margin-top: 15px;
            padding-left: 20px;
            border-left: 3px solid #007bff;
        }
        .room-item {
            border-bottom: 1px solid #eee;
            padding: 10px 0;
        }
    </style>
</head>
<body>

<h1>Painel de Gerenciamento</h1>

<div class="nav-buttons">
    <a href="<c:url value='/home/adm?view=users'/>"
       class="btn ${currentView == 'users' ? 'active' : ''}">
        Listar Usuários
    </a>

    <a href="<c:url value='/home/adm?view=sectors'/>"
       class="btn ${currentView == 'sectors' ? 'active' : ''}">
        Listar Setores
    </a>

    <a href="<c:url value='/user/create'/>"
       class="btn">
        Criar Usuário
    </a>

    <a href="<c:url value='/sector/create'/>"
       class="btn">
        Criar Setor
    </a>

    <a href="<c:url value='/logout'/>" style="background-color: red;"
        class="btn">
        Sair
    </a>
</div>


<c:choose>
    <c:when test="${currentView == 'users'}">
        <h2>Lista de Usuários</h2>
        <c:choose>
            <c:when test="${not empty users}">
                <c:forEach items="${users}" var="user">
                    <div class="sector-card">
                        <div class="sector-header">
                            <h2>${user.name}</h2>
                            <h2>Papel: ${user.role}</h2>
                            <a class=expand-btn href="<c:url value="/user/update/${user.id}"/>">
                                Editar
                            </a>
                            <form action="<c:url value="/user/delete/${user.id}"/>" method="post"
                                  style="display: inline"
                                  onsubmit="return confirm('Tem certeza que quer deletar esse usuário?')">
                                <button type="submit" class="expand-btn" style="background-color: red">Deletar</button>
                            </form>
                        </div>
                    </div>
                </c:forEach>
            </c:when>
            <c:otherwise>
                <p>Nenhum usuário encontrado.</p>
            </c:otherwise>
        </c:choose>
    </c:when>

    <c:otherwise>
        <c:choose>
            <c:when test="${not empty sectors}">
                <c:forEach items="${sectors}" var="sector">
                    <div class="sector-card">
                        <div class="sector-header">
                            <h2>${sector.name}</h2>
                            <h2>Caixa atual: R$ ${sector.cashAmount}</h2>
                            <h2>Recepcionista: ${sector.username}</h2>
                            <button class="expand-btn" onclick="toggleSalas('room-from-sector-${sector.id}')">
                                Ver Salas (${sector.rooms.size()})
                            </button>
                            <a class=expand-btn href="<c:url value="/sector/update/${sector.id}"/>">
                                Editar
                            </a>
                            <form action="<c:url value="/sector/delete/${sector.id}"/>" method="post"
                             style="display: inline"
                             onsubmit="return confirm('Tem certeza que quer deletar esse setor?')">
                                <button type="submit" class="expand-btn" style="background-color: red">Deletar</button>
                            </form>
                        </div>

                        <div id="room-from-sector-${sector.id}" class="rooms-container">
                            <h4>Salas Associadas:</h4>
                            <c:forEach items="${sector.rooms}" var="room">
                                <div class="room-item">
                                    <strong>Nome:</strong> ${room.name} <br/>
                                    <strong>Valor:</strong> R$ ${room.price} <br/>
                                    <strong>Capacidade:</strong> ${room.capacity} pessoas
                                </div>
                            </c:forEach>
                        </div>
                    </div>
                </c:forEach>
            </c:when>
            <c:otherwise>
                <p>Nenhum setor cadastrado ainda.</p>
            </c:otherwise>
        </c:choose>
    </c:otherwise>
</c:choose>

<script>
    function toggleSalas(containerId) {
        const roomsContainer = document.getElementById(containerId);

        if (roomsContainer.style.display === 'none' || roomsContainer.style.display === '') {
            roomsContainer.style.display = 'block';
        } else {
            roomsContainer.style.display = 'none';
        }
    }
</script>

</body>
</html>