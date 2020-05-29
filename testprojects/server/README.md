This project can be used to test debugging configurations of type
"Remote Java Application". Build and start the server program on
the console:

```
testprojects/server/src$ javac server/Server.java
testprojects/server/src$ java -Xdebug -Xrunjdwp:transport=dt_socket,address=8888,server=y,suspend=y server.Server
```

Finally create a debug configuration of type "Remote Java Application":

* Connection Type: Socket Attach
* Host: localhost
* Port: 8888

Or use the provided launch config: `ServerRemoteDebugging.launch`.

You can then use the launch configuration to test the behaviour
of the Eclipse Runner Plugin.
