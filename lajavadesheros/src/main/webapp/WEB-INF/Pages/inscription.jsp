<%@include  file="Parties/debutscript.jsp" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
    <body>
        <link rel="stylesheet" href="style.css">
        <div class="contenu margin_top">
            <div class="margin_padding center_text">
                <h2 class="margin_top titre ">Inscription</h2>
                <br>
                <c:choose>
                    <c:when test="${message == 0}">
                        <p class="message_succes">  Votre inscription a été réalisée avec succès</p>
                        <img src="https://www.louisberthelot.info/images/giphy.gif" alt="ici">
                    </c:when>
                    <c:when test="${message == 1}">
                        <p class="message_erreur"> Votre inscription a été annulée : Le pseudo ou le lieu d'habitation ou le numéro de téléphone n'est pas remplis</p>
                    </c:when>
                    <c:when test="${message == 2}">
                        <p class="message_erreur"> Votre inscription a été annulée : Le pseudo existe déjà</p>
                    </c:when>
                    <c:when test="${message == 3}">
                        <p class="message_erreur"> Votre inscription a été annulée : Il n'y pas ou trop de case(s) cochée(s)</p>
                    </c:when>
                    <c:when test="${message == 4}">
                        <p class="message_erreur"> Votre inscription a été annulée : La ville n'est pas connue.</p>
                    </c:when>
                    <c:otherwise>
                        <h3>Vous êtes un super-héros ?
                            <br> Vous voulez vous rendre utile ?
                            <br>Inscivez-vous ! </h3>
                    </c:otherwise>
                </c:choose>
                <br>
                <br>
                <form action="Inscription" method="post">
                    <input class="inscription_input_text" name="pseudo" type="text"placeholder="Pseudo">
                    <br>
                    <br>
                    <p>Merci de choisir entre 1 et 3 situations de crise que vous pouvez régler</p>
                    <input class="check" type="checkbox" name="Incendie" >Incendie
                    <br>
                    <input class="check" type="checkbox" name="Accident_routier" >Accident routier
                    <br>
                    <input class="check" type="checkbox" name="Accident_fluviale" >Accident fluviale
                    <br>
                    <input class="check" type="checkbox" name="Accident_aerien" >Accident aerien
                    <br>
                    <input class="check" type="checkbox" name="Eboulement" >Eboulement
                    <br>
                    <input class="check" type="checkbox" name="Invasion_serpent" >Invasion de serpent
                    <br>
                    <input class="check" type="checkbox" name="Fuite_gaz" >Fuite de gaz
                    <br>
                    <input class="check" type="checkbox" name="Manifestation" >Manifestation
                    <br>
                    <input class="check" type="checkbox" name="Braquage" >Braquage
                    <br>
                    <input class="check" type="checkbox" name="Evasion_prisonnier" >Evasion de prisonnier
                    <br>
                    <br>
                    <input class="inscription_input_text" type="text" name="adresse" placeholder="Ville de résidence">
                    <br>
                    <br>
                    <input class="inscription_input_text" type="tel" name="tel" pattern="[0-9]{10}"
                           required placeholder="Numéro de téléphone">
                    <br>
                    <br>
                    <button type="submit"> Inscription</button>
                </form>
            </div>
        </div>
    </body>
</html>
