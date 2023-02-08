# Prototyp aplikacji :video_game: 

### Architektura aplikacji :construction_worker:
<img src="uploads/8d991e209e514ed7b6dc6fd31b42ecdf/image.png" width="600" height="300"> 
   
  
  
**Frontend** - przy użyciu Scene Builder stworzyliśmy ekrany menu aplikacji z przyciskami funkcyjnymi. Projektowanie odbywa się metodą _drag&drop_, a rezultat zapisywany jest w pliku o formacie ._fxml_.  

**Backend** - logika aplikacji obsługuje na ten moment dwa tryby gry:
- Gracz :vs: Komputer (komputer losowo wybiera miejsce postawienia znaku)
- Gracz1 :vs: Gracz2 (lokalnie, na jednej maszynie)

**Data Base** - w relacyjnej bazie MySQL stworzyliśmy pierwszą tabelę _users_ (przy użyciu oprogramowania _phpMyAdmin_).  
Encja w tej tabeli wygląda w następujący sposób:  
_**<id, nick, liczba wygranych, liczba przegranych, liczb remisów>**_  

Dla klasy BaseMenager() obsługującej bazę danych zrealizowaliśmy następujące funkcjonalności:  
**C**- create  
**R** - read  
**U** - update   


# Wymagania środowiskowe :computer: 
Przy tworzeniu gry korzystamy z:
- Visual Studio Code
- **JavaFX** w wersji 16
- **Java Development Kit** w wersji 15.02  
- biblioteki do obsługi bazy danych:  **mysql connector java 8.0.22.jar**

# Instrukcja zbudowania i uruchomienia aplikacji :runner: 
1) Na początku należy dołączyć biblioteki z pakietu JavaFX oraz do obsługi MySQL.  
W Visual Studio Code: _Add Referenced Libraries_  
Poprawność dołączenia bibliotek można sprawdzić w pliku _settings.json_   
2) Aplikację uruchamiamy poprzez uruchomienie pliku głównego - **App.java**


