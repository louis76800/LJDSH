<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@include  file="Parties/debutscript.jsp" %>
<%@ page pageEncoding="UTF-8" %>
<link rel="stylesheet" href="style.css">
    <body>
        <div class="contenu margin_top">
            <div class="margin_padding center_text">
                <h2 class="margin_top titre ">Signaler un incident</h2>
                <c:choose>
                    <c:when test="${message == 1}">
                        <p class="message_erreur"> La commune n'est pas correct.</p>
                    </c:when>
                    <c:when test="${message == 2}">
                        <p class="message_erreur">L'incident n'a pas été sélectionné.</p>
                    </c:when>
                    <c:when test="${message == 3}">
                        <p class="message_erreur">La commune n'a pas été remplie.</p>
                    </c:when>
                    <c:when test="${message == 4}">
                        <p class="message_erreur">L'incident n'est pas correct.</p>
                    </c:when>
                </c:choose>
                <form action="Incident"method="post">
                    <input class="inscription_input_text" type="text"  name="ville" placeholder="Nom de la commune">
                    <br>
                    <br>
                    <select name="incident_en_question" id="incident_select">
                        <option value="">Choix d'incident</option>
                        <option value="incendie">Incendie</option>
                        <option value="accident_r">Accident routier</option>
                        <option value="accident_f">Accident fluviale</option>
                        <option value="accident_a">Accident aérien</option>
                        <option value="invasion_s">Invasion de serpent</option>
                        <option value="fuite_g">Fuite de gaz</option>
                        <option value="manifestation">Manifestation</option>
                        <option value="eboulement">Éboulement</option>
                        <option value="braquage">Braquage</option>
                        <option value="evasion_p">Évasion d'un prisonnier</option>
                    </select>
                    <br>
                    <button type="submit">Valider</button>
                </form>
            </div>
        </div>
        <c:forEach var="supers" items="${ supers }">
            <div class="contenu margin_top" style="margin-top: 25pt;" >
                <div class="margin_padding center_text" style="margin-left: 25%;margin-right: 25%;">
                    <div>
                        <p>Pseudo : <h1><c:out value="${ supers.pseudo }"/></h1></p>
                        <p>Ville de résidence : <h2><c:out value="${ supers.ville }"/></h2></p>
                        <p> Numéro de téléphone : <h3><c:out value="${ supers.telephone }"/></h3></p>
                    </div>
                </div>
            </div>
        </c:forEach>
    </body>
</html>

