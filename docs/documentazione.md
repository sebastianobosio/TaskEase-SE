# Software Life Cycle
Per lo sviluppo dell'applicazione ho scelto di adottare un metodo di sviluppo agile, dato che ho svolto il progetto da solo. Questo approccio mi ha permesso di concentrarmi sull'implementazione incrementale di piccole parti del software, mantenendo il sistema sempre funzionante.

Il processo di sviluppo è stato fortemente basato sulla prototipazione. Ho costruito gradualmente un prototipo, aggiungendo funzionalitàprogressivamente, partendo da una versione iniziale e via via migliorandola e ampliandola. In questo modo, i requisiti e le conseguenze delle scelte implementative sono emersi e sono stati migliorati man mano che il lavoro procedeva.

Ogni nuova funzionalità implementata è stata sottoposta a test manuali per garantire il corretto funzionamento e l'integrazione con le funzionalità esistenti. Questo metodo di test mi ha aiutato a individuare e risolvere prontamente eventuali problemi.

# Configuration Managment
Per la gestione della configurazione del progetto, ho utilizzato la CLI di Git e ho caricato il codice sulla piattaforma GitHub. Nonostante abbia svolto il progetto in autonomia, ho creato un account secondario per simulare una separazione dei compiti e testare meglio il flusso di lavoro.

Il codice sorgente è organizzato nella cartella code, mentre la documentazione è raccolta nella cartella docs.

Per quanto riguarda i branch, ho mantenuto la seguente struttura:

Branch main: Contiene la versione finale e stabile del progetto.
    Branch dev: Utilizzato come branch principale per lo sviluppo.

Ogni volta che doveva essere implementata una nuova funzionalità, creavo un branch dedicato su cui lavorare e testare le modifiche. Una volta completata la funzionalità, aprivo un issue e una pull request per eseguire il merge nel branch dev. Questo approccio mi ha permesso di mantenere il codice organizzato e di tracciare chiaramente le modifiche e le implementazioni.
