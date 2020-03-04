Java-Tic-Tac-Toe

This Project is a XO game which has two apps (Client,Server),The Server app should be run first by pressing(Start Server Button)
in The client app you can login using your user name and password, and in case you want to register, you press on (New Player) button.

• The client app has many features:

	1-play single mode with diffrent difficulties.
	2-play with another player in your local network.
	3-stop and resume your game anytime you login again.
	4-Changable IP address and port to connect to the server
	5-Chat window to chat with your opponent player

--> Client configuration:
	- You have to create a file with the name "connection.conf" and put it in the same directory of jar file
	- the file have to contain the connection socket like: (IP:Port)


• The server app has many features:

	1-Changable username and password to connect to the database.
	2-updatable list to show you who logged in and out.


--> Server configuration:
	- You have to create a file with the name "server_config.conf" and put it in the same directory of jar file
	- The working database engine is PostgreSQL
	- the file have to contain the database url, username & password, and also the server listening port
	  like: (url::username::password::port)

This project is made by:
	-Amr Walid
	-Aya Mohamed
	-Mahmoud Abd El-Hakim
	-Mayar Hassan
	-Mostafa Abdelhalim
