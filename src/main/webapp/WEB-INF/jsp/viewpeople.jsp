<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
        <html>

        <head>
            <title>View People</title>
            <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css" rel="stylesheet"
                integrity="sha384-1BmE4kWBq78iYhFldvKuhfTAU6auU8tT94WrHftjDbrCEXSU1oBoqyl2QvZ6jIW3"
                crossorigin="anonymous">
        </head>

        <body>

            <body>
                <header class="text-center my-3">
                    <h1>Lista de Pessoas</h1>
                </header>
                <main class="container" id="main">
                    <table class="table">
                        <thead>
                            <tr>
                                <th>Nome</th>
                                <th>Data Nascimento</th>
                                <th>Sexo</th>
                                <th>Altura</th>
                                <th>Peso</th>
                            </tr>
                        </thead>
                        <c:forEach items="${people}" var="person">
                            <tr class="text-danger" id="${person.id}">
                                <td>${person.nome}</td>
                                <td>${person.dataNasc}</td>
                                <td>${person.sexo}</td>
                                <td>${person.altura}</td>
                                <td>${person.peso}</td>
                            </tr>
                        </c:forEach>
                    </table>
                </main>
            </body>

        </html>