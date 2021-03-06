# FlyPortal

![Alt text](Architecture.png "Enterprise Architecture")

##Client
Il client � un comunissimo browser web (Chrome, Firefox, Safari, ...)

## Front-End
###JSP
Permette di scrivere codice serverside e di iniettarlo direttamente nel codice html.
Inoltre � possibile invocare direttamente bean e includere il risultato della loro chiamata
nel contesto della pagina come attributo.
###  Servlet
Sono organizzate secondo la loro funzionalit�, ogni servlet ha uno specifico compito rendendole estremamente semplici.
Alcune servlet permettono la gestione dei form tramite metodo post (es. _RegistrationServlet_), altre hanno in incarico la gestione delle richeste __AJAX__
(es. _DeleteFlightServlet_).
Hanno anche la responsabilit� delle __session__
 
__Scalabilita__: Le servlet cos� strutturate possono essere deployate in qualsiasi sistema rispondendo alle richieste sempre allo stesso modo.

###Session
Permettono di mantenere informazioni sullo stato della sessione client-server in corso. La sessione viene creata al momento del _login_:

* __auth__: permette di distinguere il livello di autorizzazione (user | admin)


* __username__: nome utente


* __cart__:	struttura dati di tipo _TreeMap_ che permette di memorizzare i voli prenotati dall'utente. La struttura dati � organizzate in modo 
da memorizzare il codice del volo di tipo stringa e il numero di posti prenotati, non � infatti necessario dover inserire all'interno del carrello
(e quindi della sessione) le informazioni complete sul volo in quanto possono essere ricavate al momento del bisogno utilizzando il codice (chiave).
In questo modo si alleggerisce parecchio la sessione e di conseguenza i messaggi in transito sulla rete.

##Back-End
###Database
Si tratta di un pool di database mysql composto da un primary e uno slave secondo una configurazione
di gestione delle repliche passiva. Le query sono gestite dal primary ma � possibile
utilizzare il bean _routing_ per implementare un sistema di bilanciamento del carico tra le risorse.
Gli update vengono gestiti dal primary, un bean � dedicato all'aggiornamento delle replice e al loro
eventuale fault.

Tramite modifiche minime � possibile aggiungere, eliminare e modificare le risorse
dati a piacimento in modo da rispondere in maniera efficiente a un aumento delle richieste utente.


![Alt text](ER.png "E-R Diagram")


###Bean
Tutti i bean utilizzati sono stateless con interfaccaia remota in modo da ottimizzare scalabilit� e migrabilit�.
Per la gestione delle visite e delle risorse dati si sono utilizzati due bean di tipo singleton.
Un primo livello della logica di business ha il compito di creare
le query e ricevere le richeste dei client. Un livello aggiuntivo logicamente
collegato con i database eseguono le query ricevute dal livello precedente
e presentano i loro risultati.
Per gestire le transazione si � utilizzato un apposito bean (_BookingBeanRemote_) e permette
di con gestione di tipo _BEAN_ in modo da implementare il 2PC.

###WebSocket
Si � utilizzato un server WebSocket con approccio publisher/subscriber: 
i subscriber aprono una nuova connessione verso il server WS (_FlyPortalWS_) restando in attesa di una notifica; 
i publisher si attiveranno non appena viene eseguita una modifica nei dati, verr� quindi inviato
un dummy message che notificher� i subscriber. Ricevuta la notify i client potranno
eseguire un refresh dell'elemento. 

###Bug
![Alt text](bug.png "Glassfish bug")
A causa del bug riportato non � stato possibile gestire i database direttamente dal server glassfish.
Tuttavia � stato possibile una gestione esplicita delle risorse senza compromettere le qualit�
di scalabilit�.


<https://java.net/jira/browse/GLASSFISH-21314>

![Alt text](FlyPortal Class.png "Class Diagram")