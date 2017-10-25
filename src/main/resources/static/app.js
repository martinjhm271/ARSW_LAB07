var app = (function () {

    var nombreJugador="NN";
    
    var stompClient = null;
    var gameid = 0;

    return {

        loadWord: function () {

            gameid = $("#gameid").val();
            
            $.get("/hangmangames/" + gameid +"/currentword",
                    function (data) {
                        $("#palabra").html("<h1>" + data + "</h1>");
                    }
            ).fail(
                    function (data) {
                        alert(data["responseText"]);
                    }

            );


        }
        ,
        wsconnect: function () {

            var socket = new SockJS('/stompendpoint');
            stompClient = Stomp.over(socket);
            stompClient.connect({}, function (frame) {

                console.log('Connected: ' + frame);

                //subscriptions
            
            });

        },

        sendLetter: function () {

            var id = gameid;

            var hangmanLetterAttempt = {letter: $("#caracter").val(), username: "?????"};

            console.info("Gameid:"+gameid+",Sending v2:"+JSON.stringify(hangmanLetterAttempt));


            jQuery.ajax({
                url: "/hangmangames/" + id + "/letterattempts",
                type: "POST",
                data: JSON.stringify(hangmanLetterAttempt),
                dataType: "json",
                contentType: "application/json; charset=utf-8",
                success: function () {
                    //
                }
            });


        },

        sendWord: function () {
            
            var hangmanWordAttempt = {word: $("#adivina").val(), username: "??????"};
            
            var id = gameid;

            jQuery.ajax({
                url: "/hangmangames/" + id + "/wordattempts",
                type: "POST",
                data: JSON.stringify(hangmanWordAttempt),
                dataType: "json",
                contentType: "application/json; charset=utf-8",
                success: function () {
                    //
                }
            });

            
        }

    };

})();

