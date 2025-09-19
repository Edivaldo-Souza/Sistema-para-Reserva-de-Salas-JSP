<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<!DOCTYPE html>
<html>
<head>
    <%-- ... seu CSS e outros metadados ... --%>
    <title>Agendamento de Salas - Spring MVC</title>
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
            .requests-container{
                justify-content: space-between;
            }
            .request-box{
                display: flex;
                justify-content: space-between;
                border: 1px solid #ccc;
                border-radius: 8px;
                padding: 20px;
                margin-right: 20px;
                margin-bottom: 20px;
                box-shadow: 0 2px 5px rgba(0,0,0,0.1);
            }
        </style>
</head>
<body>

<h1>Listagem de Setores e Salas para Agendamento</h1>
<a href="<c:url value='/logout'/>" style="background-color: red;"
   class="btn">
    Sair
</a>
<c:forEach items="${sectors}" var="sector">
    <div class="sector-card">
        <div class="sector-header">
            <h2>${sector.name}</h2>
            <button class="expand-btn" onclick="toggleSalas('room-from-sector-${sector.id}')">
                Ver Salas (${sector.rooms.size()})
            </button>
        </div>

        <div id="room-from-sector-${sector.id}" class="rooms-container">
            <h4>Salas Associadas:</h4>
            <c:forEach items="${sector.rooms}" var="room">
                <div class="room-item">
                    <strong>Nome:</strong> ${room.name} <br/>
                    <strong>Valor:</strong> R$ ${room.price} <br/>
                    <strong>Capacidade:</strong> ${room.capacity} pessoas
                </div>
                <div class="requests-container">
                    <h3>Suas solicitações</h3>
                    <c:forEach items="${room.requests}" var="request">
                        <div class="request-box">
                            <strong>Data e Hora: ${request.startDate} </strong>
                            <strong>Duração: ${request.duration} Hora(s)</strong>
                            <strong>Status: ${request.status} </strong>
                            <c:if test="${request.status!='CANCELADO' && request.status!='APROVADO'}">
                                <sec:authorize access="hasRole('RECEPCIONISTA')">
                                    <form action="<c:url value="/request/update/${request.id}?status=APROVADO"/>" method="post"
                                          style="display: inline">
                                        <button type="submit" class="expand-btn" style="background-color: green">Aprovar</button>
                                    </form>
                                </sec:authorize>
                                <form action="<c:url value="/request/update/${request.id}?status=CANCELADO"/>" method="post"
                                      style="display: inline"
                                      onsubmit="return confirm('Tem certeza que quer cancelar essa solicitação?')">
                                    <button type="submit" class="expand-btn" style="background-color: red">Cancelar</button>
                                </form>
                            </c:if>
                            <c:if test="${request.status=='APROVADO'}">
                                <form action="<c:url value="/request/update/${request.id}?status=FINALIZADO"/>" method="post"
                                      style="display: inline"
                                      onsubmit="return confirm('Tem certeza que quer finalizar essa solicitação?')">
                                    <button type="submit" class="expand-btn" style="background-color: red">Finalizar</button>
                                </form>
                            </c:if>
                        </div>
                    </c:forEach>
                </div>
                <form action="<c:url value='/request/save'/>" method="post">
                    <input type="hidden" name="roomId" value="${room.id}" />

                    <div>
                        <label for="dataHoraInicio-${room.id}">Data e Hora de Início:</label>
                        <input type="datetime-local" id="dataHoraInicio-${room.id}" name="startDate" required>
                    </div>

                    <div>
                        <label for="duracao-${room.id}">Duração (em horas):</label>
                        <input type="number" id="duracao-${room.id}" name="duration" min="1" required>
                    </div>

                    <button class="expand-btn" type="submit">Faze Solicitação</button>
                </form>
            </c:forEach>
        </div>
    </div>
</c:forEach>

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