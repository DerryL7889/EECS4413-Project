var stompClient = null;

function connect() {
    var socket = new SockJS('/auction');
    stompClient = Stomp.over(socket);
    stompClient.connect({}, function (frame) {
        //setConnected(true);
        console.log('Connected: ' + frame);
        //get channel info from getparams
        const url = new URL(window.location.toLocaleString());
        var channel = url.searchParams.get('channel');
        stompClient.subscribe('/updates/'+channel, function (auctionMessage) {
            //showGreeting(JSON.parse(greeting.body).content);
            var parsedMessage = JSON.parse(auctionMessage.body);
            console.log("message received");
            console.log(parsedMessage.bidder);
            console.log(parsedMessage.amount);
            updateAuction(parsedMessage.bidder, parsedMessage.amount);
        });
    });
}

function disconnect() {
    if (stompClient !== null) {
        stompClient.disconnect();
    }
    setConnected(false);
    console.log("Disconnected");
}

function updateAuction(name, price){
	var name_field = document.getElementById('name-field');
	var price_field = document.getElementById('price-field');
	name_field.innerHTML = name;
	price_field.innerHTML = "$" + price;
}