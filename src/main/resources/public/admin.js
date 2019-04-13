//Establish the WebSocket connection and set up event handlers
var buzzInterval;
var webSocket = new WebSocket("ws://" + location.hostname + ":" + location.port + "/admin");
var locked = true;
var answerTime = 6;
webSocket.onmessage = function (msg) {
    var data = msg.data;
    if(data === 'buzz'){
        document.body.style.backgroundColor = 'green';
        if (buzzInterval){
            clearInterval(buzzInterval);
        }
        var timeLeft = answerTime;
        setStatus("Buzzed in: " + timeLeft + "s");
        buzzInterval = setInterval(function(){
            timeLeft--;
            setStatus("Buzzed in: " + timeLeft + "s");
        }, 1000);
    } else if (data == 'unbuzz'){
        document.body.style.backgroundColor = 'blue';
        if (buzzInterval){
            clearInterval(buzzInterval);
        }
        setStatus('Unlocked');
    } else if (data == 'unlocked'){
        locked = false;
        document.body.style.backgroundColor = 'blue';
        if (buzzInterval){
            clearInterval(buzzInterval);
        }
        setStatus('Unlocked');
    } else if (data == 'locked'){
        locked = true;
        document.body.style.backgroundColor = 'black';
        if (buzzInterval){
          clearInterval(buzzInterval);
        }
        setStatus('Locked');
    }
};

webSocket.onopen = function () {
    document.body.style.backgroundColor = "black";
    setStatus('Locked');
};

webSocket.onclose = function () {
    document.body.style.backgroundColor = "red";
    setStatus('Disconnected');
};


//Send message if enter is pressed in the input field
document.body.addEventListener("keypress", function (e) {
    if (e.keyCode === 13) {
        toggleLock();
    }
});


document.body.addEventListener("click", function (e) {
    toggleLock();
});

// Uncomment for iPhone support (becomes janky)
//document.body.addEventListener("touchstart", function (e) {
//    toggleLock();
//});
//
//
//document.body.addEventListener("touchend", function (e) {
//    toggleLock();
//});

function setStatus(text){
    document.getElementById('status').innerHTML = text;
}

function toggleLock(){
    if (buzzInterval){
        clearInterval(buzzInterval);
    }
    if (!locked){
        sendMessage('lock');
        setStatus('Locked');
        locked = true;
        document.body.style.backgroundColor = "black";
    } else {
        sendMessage('unlock');
        setStatus('Unlocked');
        locked = false;
        document.body.style.backgroundColor = "blue";
    }
}

function sendMessage(message) {
    if (message !== "") {
        webSocket.send(message);
    }
}