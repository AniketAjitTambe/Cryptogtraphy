# Cryptogtraphy
End to End Encryption between Client to Client interaction through Server using combination of asymmetric and symmetric key encryption

In this project, two clients:  client 0 and client 1 communicate with each other in the form of messages. Both clients are connected to a server, which handles the client and message transfer between the two messages. Due to this, all the interactions are visible and readable on server’s log. 

Thus, the objective of his project is to make the interaction between the two clients end to end encrypted in such a way that the messages are encrypted with a secret key(Symmetric Key Encryption). This secret key is jointly formed and agreed upon by both clients and exchanged with each other in an encrypted format(Asymmetric Key Encryption).

Steps to run the code
1. In cmd 1 , Run "rsa.java".
2. In cmd 1 , Run " Server.java"
3. In new cmd window- cmd 2 , Run "Client_0.java" and wait. Donot press ENTER on next screen!
4. In new cmd window- cmd 3 , Run "Client_1.java" and Press ENTER on next screen.
5. Go back to cmd 2 running "Client_0.java" and press enter.
6. All keys will be exchanged and program ready to run.
