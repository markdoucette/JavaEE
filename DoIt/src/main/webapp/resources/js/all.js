var uri = "http://localhost:8080/DoIt/api/todos";

var http = new XMLHttpRequest();

http.open("GET", uri, false);
http.send(null);


var result = http.responseText;

function showAll() {
   var element = document.getElementById('output');
    element.innerHTML = result;

}
