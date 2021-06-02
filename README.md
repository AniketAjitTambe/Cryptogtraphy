# End to End Encryption between Client to Client interaction through Server using combination of asymmetric and symmetric key encryption

##  1.	Aim
Implement end to end encryption for client to client communication through server using Cryptography(Combination of Asymmetric and Symmetric Encryption).

##  2.	Objective
In this project, two clients:  client 0 and client 1 communicate with each other in the form of messages. Both clients are connected to a server, which handles the client and message transfer between the two messages. Due to this, all the interactions are visible and readable on server’s log. 
Thus, the objective of his project is to make the interaction between the two clients end to end encrypted in such a way that the messages are encrypted with a secret key. This secret key is jointly formed and agreed upon by both clients and exchanged with each other in an encrypted format.

##  3.	Scope

Once implemented, this project will form a system with following features
1.	End to End Encrypted with Messages readable only on the end users system and not on the server thus avoiding privacy loss in case of compromised server.
2.	Double Layer of security in the form of:
a.	Secret key for Message Encryption/Decryption
b.	Public Key/Private Key form from RSA Algorithm for Encryption/Decryption of Secret Key.
3.	New Key generation before each session, thus making it unpredictable to guess the keys.
Hence this project can be used as a base to form a system with end-to-end encryption. This system can be used for anything like Messaging system , Data Transfer over Network , Mailing etc. 
