//Establish the WebSocket connection and set up event handlers
var webSocket = new WebSocket("ws://" + location.hostname + ":" + location.port + "/play");
webSocket.onmessage = function (msg) {
id("body").style.backgroundColor = "blue";
updateChat(msg);
};
webSocket.onopen = function () {
    var name = prompt("Enter your name:");
    if(name){
        console.log("Name change");
        sendMessage("name:" + name);
    }
}
webSocket.onclose = function () {
NotificationManager.create("Game ended", "Lost connection to game host");
id("body").style.backgroundColor = "red";
id("question").innerHTML = "No connection to game host"
};


//Send message if enter is pressed in the input field
id("body").addEventListener("keypress", function (e) {
    if (e.keyCode === 13) { sendMessage("buzz"); }
});


id("body").addEventListener("click", function (e) {
    sendMessage("buzz");
});

id("body").addEventListener("touchstart", function (e) {
    sendMessage("buzz");
});


//Send a message if it's not empty, then clear the input field
function sendMessage(message) {
    if (message !== "") {
        webSocket.send(message);
    }
}

//Update the chat-panel, and the list of connected users
function updateChat(msg) {
    var data = JSON.parse(msg.data);
    if(data.message && data.message != ""){
        NotificationManager.clear();
        NotificationManager.create(data.sender, data.message);
    }
    if(data.question){
        id("question").innerHTML = data.question;
    } else {
        id("question").innerHTML = "No question chosen";
    }
    var turn = data.turn == data.name;
    if(turn){
        id("body").style.backgroundColor = "green";
        id("name").innerHTML = "Your turn! <br>" + data.name;
    } else {
        id("name").innerHTML = data.name;
    }
    id("score").innerHTML = "$" + data.score;
    id("score").style.height = (id("score").clientWidth - 48) + "px";
    id("score").style.lineHeight = id("score").style.height;
//    insert("chat", data.message + "<br>");

    var numConnectedUsers = data.userlist.length;
    id("usercount").innerHTML = numConnectedUsers;
}

//Helper function for inserting HTML as the first child of an element
function insert(targetId, message) {
    id(targetId).insertAdjacentHTML("afterbegin", message);
}

//Helper function for selecting element by id
function id(id) {
    return document.getElementById(id);
}