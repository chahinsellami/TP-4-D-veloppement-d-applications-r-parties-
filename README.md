# TP4 - Sockets UDP en mode non connecte

## Etape 1 : Preparation de l'espace de travail

Le projet est organise dans le repertoire suivant :

```
.votreNomPrenom_LSI3_DevAppReparties/
├── README.md
├── src/
│   ├── clientPackage/
│   │   └── Client.java
│   └── serverPackage/
│       └── Server.java
└── out/
```

## Etape 2 : Chat unidirectionnel avec un seul client

### Objectif
Le serveur ecoute sur le port 1234 et reçoit des datagrammes UDP envoyés par un client. Le client lit un nom d'utilisateur et un message texte, puis envoie le message au serveur sous forme de paquet UDP.

### Fonctionnement
- Le serveur écoute sur le port 1234 via `DatagramSocket` et `InetSocketAddress`.
- Le client demande le nom d'utilisateur.
- Le client envoie un message au format : `[NomUtilisateur] : texte`.
- Le serveur affiche l'adresse IP et le port de l'expéditeur ainsi que le contenu du message.

### Compilation

```powershell
javac -d out src\serverPackage\Server.java src\clientPackage\Client.java
```

### Execution

Terminal 1 :

```powershell
java -cp out serverPackage.Server
```

Terminal 2 :

```powershell
java -cp out clientPackage.Client
```

Le client peut ensuite saisir un nom d'utilisateur et des messages pour les envoyer au serveur.

## Etape 3 : Chat bidirectionnel avec plusieurs clients

Le serveur garde les adresses des clients qui ont envoyé un message. Quand un message arrive, il l'envoie à tous les autres clients.

Le client utilise un thread pour recevoir les messages pendant que le programme principal lit les messages à envoyer. Pour tester cette étape, lancez le serveur puis plusieurs clients dans des terminaux différents.
