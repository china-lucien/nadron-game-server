# nadron-game-server
```
<!DOCTYPE html>
<html>
<head>
<script src="./nad-0.1.js"></script>
<script>
var config = {
        user:"user",
        pass:"pass",
        uid:"1234567",
        key:"1234567",
        picture:"",
        sex:1,
};

nad.sessionFactory("ws://localhost:18090/nadsocket", config, loginHandler);

function loginHandler(session){
	session.onmessage = messageHandler;
	session.send(nad.CNameEvent("com.bbl.app.events.CustomEvent"));
	session.send(nad.NEvent(nad.NETWORK_MESSAGE, {cmd:10, data: {name: 'test_room', maxPlayers: '5'}}));
}

function messageHandler(e){
}

</script>
</head>
<body>
</body>
</html>
```
