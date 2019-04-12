var webSocket = new WebSocket("ws://" + location.hostname + ":" + location.port + "/admin");
webSocket.onmessage = function (msg) {
update(msg);
//id("body").style.backgroundColor = "blue";
};
webSocket.onclose = function () {
NotificationManager.create("Game ended", "Lost connection to game host");
//id("body").style.backgroundColor = "red";
//id("question").innerHTML = "No connection to game host"
};


function update(msg) {
    var data = JSON.parse(msg.data);
    if(data.message && data.message != ""){
        NotificationManager.clear();
        NotificationManager.create(data.sender, data.message);
    }


    if(data.question){
        showQuestion(data.question);
    } else {
        hideQuestion();
    }

    var turn = data.turn;
    if(turn){
        $("#turn").html(data.turn + " has buzzed in!");
    } else {
        $("#turn").html("");
    }
//    id("score").innerHTML = "$" + data.score;
//    id("score").style.height = (id("score").clientWidth - 48) + "px";
//    id("score").style.lineHeight = id("score").style.height;
////    insert("chat", data.message + "<br>");
//
//    var numConnectedUsers = data.userlist.length;
//    id("usercount").innerHTML = numConnectedUsers;
}


var worth = ['money-1', 'money-2', 'money-3', 'money-4', 'money-5'];
var qCard = document.getElementById('question');
var categories = ["About UPE", "CS", "Random", "Career Fair", "Jobs in CS", "ID the Lang"];


id("body").addEventListener("keypress", function (e) {
    if (e.keyCode === 27) { sendGameOver() }
    if (e.keyCode === 13) { allowAnswering() }
});



function allowAnswering(){
    var msg = {allowAnswering: true};
    webSocket.send(JSON.stringify(msg));
}

function onQuestionClick(questionID){
    var msg = {question: questionID}
    webSocket.send(JSON.stringify(msg));
}

function onPlayerCorrect(){
    var msg = {playerCorrect: true};
    webSocket.send(JSON.stringify(msg));
}


function onPlayerIncorrect(){
    var msg = {playerCorrect: false};
    webSocket.send(JSON.stringify(msg));
}


function onQuestionExpire(){
    var msg = {questionExpire: true};
    webSocket.send(JSON.stringify(msg));
}

function sendGameOver(){
    var msg = {gameOver: true};
    webSocket.send(JSON.stringify(msg));
}

for (var i = 0; i < 6; i++) {
    var element = $('#names').children().eq(i);
    var d = Math.ceil(categories[i].length / 3);
    if (d > 1) {
        var h = element.css('line-height');
        h = h.slice(0, h.length - 2);
        element.css('line-height', parseFloat(h) / Math.ceil(categories[i].length / 12) + 'px');
        element.css('font-size', 28 / d * 2.5);
        console.log(element.css('line-height'));
    }
    element.text(categories[i]);
}

$('#question a').click(function() {
    onQuestionExpire();
});

$('#correct').click(function() {
    onPlayerCorrect();
});

$('#incorrect').click(function() {
    onPlayerIncorrect();
});

$('.money div').click(function(event) {
    var category = $(this).index();
    var question = worth.indexOf(event.currentTarget.parentElement.id);

    event.currentTarget.innerHTML = "";

    onQuestionClick(question + category * 5);

});

function hideQuestion() {
    qCard.classList.remove('show');
}

function showQuestion(question) {
    $('#question-text').text(question);
    qCard.classList.add('show');
}


function id(id) {
    return document.getElementById(id);
}