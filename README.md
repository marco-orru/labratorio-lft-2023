# Laboratorio per il corso di Linguaggi Formali e Traduttori
## 2. P-Lang
### 2.1 Analizzatore lessicale
Gli esercizi di questa sezione riguardano l'implementazione di un analizzatore lessicale per un semplice linguaggio di
programmazione. Lo scopo di un analizzatore lessicale è di leggere un testo e di ottenere una corrispondente sequenza di
**token**, dove un token corrisponde ad un'unità lessicale, come un _numero_, un _identificatore_, un 
_operatore relazionale_, una _parola chiave_, ecc.
Nelle sezioni successive, l'analizzatore lessicale da implementare sarà poi utilizzato per fornire l'input a programmi
di analisi sintattica e di traduzione.  
I token del linguaggio sono descritti nel modo illustrato nella seguente tabella, dove la prima colonna contiene le
varie categorie di token, la seconda presenta la descrizione dei possibili lessemi dei token, e la terza descrive i nomi
dei token espressi come costanti numeriche.

| Token                     | Pattern                                             | Nome |
|:--------------------------|:----------------------------------------------------|:----:|
| Numeri                    | Costante numerica                                   | 256  |
| Identificatore            | Lettera seguita da lettere e cifre                  | 257  |
| RelOp                     | Operatore relazionale (`<`,`>`,`<=`,`>=`,`==`,`<>`) | 258  |
| Assegnamento              | `assign`                                            | 259  |
| To                        | `to`                                                | 260  |
| If                        | `if`                                                | 261  |
| Else                      | `else`                                              | 262  |
| Do                        | `do`                                                | 263  |
| For                       | `for`                                               | 264  |
| Begin                     | `begin`                                             | 265  |
| End                       | `end`                                               | 266  |
| Print                     | `print`                                             | 267  |
| Read                      | `read`                                              | 268  |
| Inizializzazione          | `:=`                                                | 269  |
| Disgiunzione              | <code>&#124;&#124;</code>                           | 270  |
| Congiunzione              | <code>&amp;&amp;</code>                             | 271  |                         
| Negazione                 | `!`                                                 |  33  |
| Parentesi tonda sinistra  | `(`                                                 |  40  |
| Parentesi tonda destra    | `)`                                                 |  41  |
| Parentesi quadra sinistra | `[`                                                 |  91  |
| Parentesi quadra destra   | `]`                                                 |  93  |
| Parentesi graffa sinistra | `{`                                                 | 123  |
| Parentesi graffa destra   | `}`                                                 | 125  |
| Somma                     | `+`                                                 |  43  |
| Sottrazione               | `-`                                                 |  45  |
| Moltiplicazione           | `*`                                                 |  42  |
| Divisione                 | `/`                                                 |  47  |
| Punto e virgola           | `;`                                                 |  59  |
| Virgola                   | `,`                                                 |  44  |
| EOF                       | Fine dell'input                                     |  -1  |

Gli identificatori corrispondono all'espressione regolare:

```text
[a-zA-Z][a-zA-Z0-9]*
```

e i numeri corrispondono all'espressione regolare

```text
0?[1-9][0-9]*
```

L'analizzatore lessicale dovrà ignorare tutti i caratteri riconosciuti come _spazi_ (incluse le tabluazioni e i ritorni
a capo), ma dovrà segnalare la presenza di caratteri illeciti, come ad esempio `#` e `@`.  
L'output dell'analizzatore lessicale dovrà avere la forma:

$$ 
\begin{aligned}
\tt &\langle token_0 \rangle\\
\tt &\langle token_1 \rangle\\
\tt &\cdots\\
\tt &\langle token_n \rangle
\end{aligned}
$$ 

Ad esempio:

+ per l'input `assign 300 to d;` l'output sarà $\langle 259,\texttt{assign}\rangle\;\langle 256,\texttt{300}\rangle\;\langle260,\texttt{to}\rangle\;\langle 257,\texttt{d}\rangle\;\langle 59\rangle\;\langle -1\rangle$;
+ per l'input `print(*{d t})` l'output sarà $\langle 256,\texttt{print}\rangle\;\langle 40\rangle\;\langle 42\rangle\;\langle123\rangle\;\langle257,\texttt d\rangle\;\langle257,\texttt t\rangle\;\langle 125\rangle\;\langle41\rangle\;\langle-1\rangle$
+ per l'input `if (> x y) assign 0 to x else print(y)` l'output sarà $\langle 261, \texttt{if} \rangle \;
\langle 40 \rangle \;
\langle 258, \texttt{>} \rangle \;
\langle 257, \texttt{x} \rangle \;
\langle 257, \texttt{y} \rangle \;
\langle 41 \rangle \;
\langle 259, \texttt{assign} \rangle \;
\langle 256, 0 \rangle \;
\langle 260, \texttt{to} \rangle \;
\langle 257, \texttt{x} \rangle \;
\langle 262, \texttt{else} \rangle \;
\langle 267, \texttt{print} \rangle \;
\langle 40 \rangle \;
\langle 257, \texttt{y} \rangle \;
\langle 41 \rangle \;
\langle -1 \rangle$
+ per l'input `for (dog := 0; dog <= printread) assign dog + 1 to dog` l'output sarà $\langle 264, \texttt{for} \rangle \;
\langle 40 \rangle \;
\langle 257, \texttt{dog} \rangle \;
\langle 269, := \rangle \;
\langle 256, 0 \rangle \;
\langle 59 \rangle \;
\langle 257, \texttt{dog} \rangle \;
\langle 258, \leq \rangle \;
\langle 257, \texttt{printread} \rangle \;
\langle 41 \rangle \;
\langle 259, \texttt{assign} \rangle \;
\langle 257, \texttt{dog} \rangle \;
\langle 43 \rangle \;
\langle 256, 1 \rangle \;
\langle 260, \texttt{to} \rangle \;
\langle 257, \texttt{dog} \rangle \;
\langle -1 \rangle
$

In generale, i token della tabella hanno un attributo: ad esempio, l'attributo del token $\langle259,\texttt{assign}\rangle$ è la stringa `assign`. Si noti, però, che alcuni token sono senza attributo: per esempio, il segno "per" (`*`) è infatti rappresentato dal token $\langle42\rangle$, e la parentesi tonda destra (`)`) è rappresentata dal token $\langle41\rangle$.  
**NOTA**: l'analizzatore lessicale non è preposto al riconoscimento della _struttura_ dei comandi del linguaggio. Pertanto, accetterà anche comandi strutturalmente errati, come:

+ `5+;)`
+ `(34+26( - (2+15-( 27`
+ `else 5 == print < end`

Altri errori invece, come eventuali simboli non previsti o sequenze illecite (come nel caso di `17&5`, oppure dell'input `|||`) devono essere rilevati.

---
Per realizzare l'analizzatore lessicale, si possono usare le seguenti classi. Definiamo una classe `Tag` utilizzando le costanti intere nella colonna **Nome** della tabella dei token per rappresentare i nomi dei token. Per i token che corrispondono ad un solo carattere (tranne per `<` e `>`, che corrispondono a "RelOp") si può utilizzare il codice ASCII del carattere: ad esempio, il nome del segno di somma `+` è 43, ossia il codice ASCII del `+`.