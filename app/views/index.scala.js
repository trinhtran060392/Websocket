/**
 * this file handle events todo application
 */

$(document).ready(function() {

  var WS = window['MozWebSocket'] ? window['MozWebSocket'] : WebSocket;
  // open pewpew with websocket
  var socket = new WS('@routes.Application.WebSocket().webSocketURL(request)');

  var writeMessages = function(event) {
    var ul = $("#messagewindow");
    $(ul).empty();
    var data = JSON.parse(event.data);
   
    $(data).each(function() {
      var output = "<b>" + this.ip + "</b>   <b>" + this.name + "</b>   <b>" + this.state + "</b></br>";
      $(ul).append(output);

    });

  }

  socket.onmessage = writeMessages;

});
