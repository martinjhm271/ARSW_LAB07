var app = (function () {

    var nombreJugador = "NN";

    var stompClient = null;
    var gameid = 0;


    var getUser = function () {
        
        var getPromise = $.get("/users/" + document.getElementById("playerid").value,
            function (data) {
                var info = "<div>" + "<img id=imagenjugador " + "src=" + data.photoUrl + " />" + "</div>" + "<div>" + "Nombre:" + data.name + "</div>" + "<div>" + "Ultimo Puntaje:" + data.scores[data.scores.length-1].puntaje + "</div>";
                document.getElementById("datosjugador").innerHTML = info;
                nombreJugador = data.name;
            }
        );
        getPromise.then(function () {
            console.info("usuario cargado con exito");
        }, function () {
            console.info("usuario no cargado con exito");
        });
    }
    
    
    var getAllUser = function () {
        
        var getPromise = $.get("/users",
            function (data) {
                $("#table1").find("tr:gt(0)").remove();
                anadir = function (objeto) {
                    $(document).ready(function () {
                        puntaje="";
                        for(var i=0;i<objeto.scores.length;i++){
                            puntaje+="Fecha:  " +objeto.scores[i].fechaObtencionPuntaje+" Puntaje: " + objeto.scores[i].puntaje+ "\n";
                        }
                        $('#table1').append("<tr> <th>" + "<img id=imagenjugador " + "src=" + objeto.photoUrl + " />" + "</th> <th>" + objeto.name + "</th> <th>" + puntaje + "</th> </tr>");
                    });
                };
                data.map(anadir);
            }
        );
        getPromise.then(function () {
            console.info("usuarios cargados con exito");
        }, function () {
            console.info("usuarios no cargados con exito");
        });
        return getPromise;
    }
    
    
    var logout=function(){
        if(stompClient!==null){stompClient.disconnect();}
    }
    
    
    
    
    return {
        loadWord: function () {

            gameid = $("#gameid").val();

            $.get("/hangmangames/" + gameid + "/currentword",
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
            gameid = $("#gameid").val();
            var socket = new SockJS('/stompendpoint');
            stompClient = Stomp.over(socket);
            stompClient.connect("nzrqqtiq", "6Gb8MzCbluILuE1nZDstcra944gqz-YP",function (frame) {

                console.log('Connected: ' + frame);

                stompClient.subscribe("/topic/wupdate."+gameid,function(eventbody){
                    app.loadWord();
                });
                stompClient.subscribe("/topic/winner."+gameid,function(eventbody){
                    var temp =JSON.stringify(eventbody.body);
                    var info="<div>"+"Estado: Finalizado"+"</div>"+"<div>"+"Ganador: "+ temp+" ."+"</div>"; 
                    document.getElementById("status").innerHTML=info;
                    logout();
                });

            },"nzrqqtiq");
           app.loadWord(); 
        },
        sendLetter: function () {

            var id = gameid;

            var hangmanLetterAttempt = {letter: $("#caracter").val(), username: nombreJugador};

            console.info("Gameid:" + gameid + ",Sending v2:" + JSON.stringify(hangmanLetterAttempt));


            jQuery.ajax({
                url: "/hangmangames/" + id + "/letterattempts",
                type: "POST",
                data: JSON.stringify(hangmanLetterAttempt),
                dataType: "json",
                contentType: "application/json; charset=utf-8",
                success: function () {
                    alert("envio la letra correctamente");
                }
            });


        },
        sendWord: function () {

            var hangmanWordAttempt = {word: $("#adivina").val(), username: nombreJugador};

            var id = gameid;

            jQuery.ajax({
                url: "/hangmangames/" + id + "/wordattempts",
                type: "POST",
                data: JSON.stringify(hangmanWordAttempt),
                dataType: "json",
                contentType: "application/json; charset=utf-8",
                success: function () {
                    alert("envio la palabra correctamente");
                }
            });


        },
        getUser: function () {
            getUser();
            getAllUser();
        }
        
       

    };

})();

