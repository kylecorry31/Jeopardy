//Establish the WebSocket connection and set up event handlers
var webSocket = new WebSocket("ws://" + location.hostname + ":" + location.port + "/play");
var buzzInterval;
webSocket.onmessage = function (msg) {
//    document.body.style.backgroundColor = "blue";
    update(msg.data);
};

webSocket.onopen = function () {
    document.body.style.backgroundColor = "black";
    setStatus("Locked");
};

webSocket.onclose = function () {
    document.body.style.backgroundColor = "red";
    setStatus("Disconnected");
};


//Send message if enter is pressed in the input field
document.body.addEventListener("keypress", function (e) {
    if (e.keyCode === 13) { sendMessage("buzz"); }
});


document.body.addEventListener("click", function (e) {
    sendMessage("buzz");
});

document.body.addEventListener("touchstart", function (e) {
    sendMessage("buzz");
});


function sendMessage(message) {
    if (message !== "") {
        webSocket.send(message);
    }
}

function setStatus(text){
    document.getElementById('status').innerHTML = text;
}

function update(msg) {
    if (msg == "unlocked"){
        document.body.style.backgroundColor = "blue";
        setStatus("Unlocked");
        if (buzzInterval){
            clearInterval(buzzInterval);
        }
    } else if (msg == "locked"){
        document.body.style.backgroundColor = "black";
        setStatus("Locked");
        if (buzzInterval){
            clearInterval(buzzInterval);
        }
    } else if (msg === "buzz"){
        if (buzzInterval){
            clearInterval(buzzInterval);
        }
        document.body.style.backgroundColor = "green";
        var timeLeft = 10;
        setStatus("Buzzed in: " + timeLeft + "s");
        buzzInterval = setInterval(function(){
            timeLeft--;
            setStatus("Buzzed in: " + timeLeft + "s");
        }, 1000);

    } else if (msg == "unbuzz"){
        document.body.style.backgroundColor = "blue";
        setStatus("Unlocked");
        if (buzzInterval){
            clearInterval(buzzInterval);
        }
    }
}

