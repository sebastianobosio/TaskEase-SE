# Project Plan

## Introduzione
Lo scopo di questo progetto è sviluppare TaskEase, un'applicazione desktop per la creazione, gestione e modifica
di task ed eventi. Il progetto sarà sviluppato da un'unica persona: Sebastiano Bosio 1083815. Per simulare la
programmazione collaborativa creerò un ulteriore account github per utilizzare alcune funzionalità di github come i 
branch, gli issues e le PR. 

## Modello di processo
Per il processo di sviluppo ho deciso di optare per un approccio di tipo Agile in modo da poter sviluppare 
l'applicazione velocemente senza dovermi occupare di manterere una documentazione costantemente aggiornata.
Per iniziare verrà creato un prototipo di base che verrà migliorato tramite miglioramenti incrementali che ne
aumenteranno le funzionalità. 
Per tenere traccia dello stato del progetto, dei bug e delle funzionalità da aggiungere verranno usati gli issues 
di Github. Mano a mano le issue saranno prese in carico e chiuse.
Per simulare una suddivisione delle attività l'account @sebastianobosio si occuperà del modello e dell'interfaccia utente, mentre l'altro account (@sebastianobosioAlias) si occuperà della parte di controllo e dell'interazione con il database.

## Organizzazione del progetto
Come detto prima l'unica persona coinvolta nello sviluppo del progetto è il sottoscritto.
Per simulare una suddivisione delle attività l'account @sebastianobosio si occuperà del modello e della 
persistenza dei dati, mentre l'altro account si occuperà dell'interfaccia utente e del controller. 

## Standard, linee guida e procedure
La programmazione Java seguirà le convenzioni dettate da Oracle tramite lo sviluppo con Eclipse IDE.
L'applicazione userà il design pattern MVC per garantire una suddivisione tra dato e sua rappresentazione grafica.

## Attività di gestione
Il progetto dovrà sempre essere funzionante ogni volta che viene migliorato. Man mano esso viene sviluppato verrà 
deciso se incrementare o togliere funzionalità a seconda del tempo rimanente. Gli unici requisiti che dovranno essere
obbligatoriamente soddisfatti sono quelli essenziali per il funzionamento dell'applicazione.

## I rischi
I possibili rischi di questo progetto, oltre alla deadline che deve essere rispettata, sono:
* Non mantenere una buona qualità del software per via del prototyping
* Non implementare una buona grafica per poca cononscenza degli strumenti.

## Personale

## Metodi e tecniche
Per iniziare verrà sviluppato un veloce schema per riassumere le funzionalità dell'applicazione che verrà poi
formalizzato in un diagramma dei casi d'uso per l'ingegneria dei requisiti. 
Inoltre verrà sviluppato anche uno schema delle classi che si pensano necessarie che verrà poi aggiornato durante lo
sviluppo.
Ogni account lavorerà su branch separati per garantire un ambiente di sviluppo sicuro e stabile. 

## Garanzie di qualità
Per garantire un software di qualità sfrutterò il design pattern MVC in modo da separare le varie attività 
rendendole indipendenti. Inoltre punterò a rispettare i fattori di qualità di McCall come la correttezza, 
l'affidabilità e l'usabilità. Per rendere il codice leggibile sfrutterò il riutilizzo del codice e i commenti
oltre alle convenzioni sulla programmazione Java. 

## Pacchetti di Lavoro
Come detto prima la suddivisione del lavoro tra i due account segue dall'uso di MVC come pattern.

## Risorse
Le risorse necessarie sono un computer per sviluppatore, un'ambiente di sviluppato come Eclipse, Java e le sue
librerie, un VCS come Github, conoscenze pregresse e internet.

## Budget
L'unico budget presente per questo progetto è il tempo. Secondo le guide il progetto prevede 30-40 ore a testa, ma è
pensato per almeno 2 persone. Essendo da solo pianifico di investire circa 70 ore per la realizzazione 
dell'applicazione e la relativa documentazione.

## Cambiamenti
I cambiamenti verranno gestiti tramite il supporto interno di Github grazie ai commit e alle pull request.

## Consegna
Il prodotto sarà sempre disponibile su Github e mano a mano aggiornata e ampliata di funzionalità. La versione
finale sarà quella presente nel main branch.
