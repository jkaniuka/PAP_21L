# Szczegółowy opis zadań poszczególnych klas :page_with_curl:  
- poniżej opisano wszystkie klasy zaimplementowane w finalnej wersji projektu,  
- w znakomitej większości przypadków omawiana klasa (*public*) jest jedyną klasą w pliku o tej samej nazwie -> jeżeli w danym pliku występują też inne klasy zostanie do odpowiednio zaznaczone,  
- klasy opisano w porządku alfabetycznym.  

## App :iphone:  
- klasa bazowa, odpowiada za uruchomienie pierwszego okna programu,  
- inicjuje połączenie z _DB_,  
- odpowiada za wyświetlenie ekranu startowego z animacją i załadowanie formatki *.fxml* z Menu.
## BaseMenager  :card_file_box:
- klasa ta posiada statyczne pola prywatne zawierające informacje umożliwiające połączenie z bazą danych *(username, password, connection string)*,
- obsługuje również rozłączenie z bazą po wyjściu z gry,
- jej metody pozwalają na dodanie nowego gracza/zespołu/wyniku do DB jak i odczytanie tych rekordów z DB,
- obsługuje również komunikaty błędów związane z problem przy nawiązaniu połączenia z DB.

## BaseMenegerTest  :microscope: 
- klasa testująca metody obsługi DB,  
- zaimplementowano w niej testy jednostkowe z asercjami wykorzystując JUnit.

## Board (klasa wewnętrzna) :o: :x:  
- na tej klasie oparte są wszystkie rodzaje rozgrywki,  
- jej atrybutem jest *token*, czyli znak na polu planszy ( _o_ lub _x_ ),  
- metody tej klasy pozwalają na pobranie/odczytanie znaku z danego pola oraz na postawienie nowego znaku przez gracza/komputer.  


## ComputerRandom  :game_die: 
- w klasie zaimplementowano obsługę gier w trybach *Easy* oraz *Medium*,
- zadaniem tej klasy jest utworzenie obszaru gry *(GridPane)* oraz obsługa interakcji z graczem klikającym odpowiednie miejsca na planszy,
- w trybie _Easy_ komputer losowo wykonuje następny ruch, a w trybie *Medium* wykorzystuje algorytm zachłanny,
- do innych zadań klasy należy także: obsługa komunikatów informujących o rezultacie gry, czy ciągłe sprawdzanie sytuacji na planszy pod kątem ewentualnego remisu. 
## GameBasic  :game_die: 
- zadaniem klasy jest obsługa trybu _Multiplayer_,
- umożliwia dwóm osobom rozgrywkę na jednej maszynie, 
- ma identyczne zadania jak klasa _ComputerRandom_.
## GameLogic :computer:  
- klasa grupująca wszystkie metody odpowiedzialne za logikę gry w jednym miejscu,  
- powstała wyłącznie na potrzeby testów jednostkowych z JUnit i nie zawiera elementów pakietu JavaFX takich jak pokazywanie Stage'a itd. - ich obecność podczas testowania powoduje problemy z oddzielnymi wątkami wykorzystywanymi przez JavaFX
## GameLogicTest :microscope:  
- klasa testująca logikę gry,  
- zawiera proste testy jednostkowe z asercjami wykorzystujące JUnit.  
## GameStatsController  :chart_with_downwards_trend: 
- zadaniem tej klasy jest wyciągnięcie z DB odpowiednich rekordów oraz przedstawienie ich w przejrzystej dla użytkownika formie tabeli,
## HistoryStatsController  :chart_with_upwards_trend: 
- klasa odpowiada za prezentacje zapisanych historii ruchów z rozgrywek, 
- komunikuje się z DB i wyciąga potrzebne rekordy w celu ich późniejszego wyświetlenia w formie animacji *mini-planszy* z przebiegiem rozgrywki,  
## MainController :file_cabinet: 
- do zadań tej klasy należy "wstrzyknięcie" głównego _Pane'a_ w formacie *.fxml* do kontrolera,
- ładuje również ekrany podrzędne go ekranu głównego.
## MenuController :card_index_dividers:
- klasa obsługuje główne menu gry,   
- do jej zadań należą m.in. przejście do menu wybory gry, umożliwienie przejrzenia statystyk, wyświetlenie instrukcji oraz wyjście z całej aplikacji po zakończeniu rozgrywki,  
## MiniMax  :game_die:  
- zawiera już opisaną klasę wewnętrzną *Board*,  
- jej zadaniem jest określenie następnego ruchu komputera bazując na algorytmie heurystycznym,  
- tak jak inne klasy obsługujące rozgrywkę sprawdza na bieżąco sytuację na planszy i odpowiada za interakcję z graczem przez komunikaty.  
## SpeedRun  :game_die: :runner: 
- zadaniem klasy jest symulacja ruchów po stronie komputera z ograniczonym od góry limitem czasu podawanym przez użytkownika na początku rozgrywki,
- częścią struktury tego trybu gry jest również poprawna obsługa interfejsu *Slider* i przekazanie wartości limitu czasowego do konstruktora klasy *SpeedRun*.  
## StartGameController :video_game: 
- klasa zawiera metody, z których każda jest odpowiedzialna za obsłużenie konkretnego trybu rozgrywki,  
- wspomniane metody wymuszają od gracza podanie *nick'u*, oraz przygotowują odpowiedni *statement* do zapisania w DB,  
- klasa ma także za zadanie obsługę interfejsów wyboru np. _checkbox_ z wyborem poziomu trudności, _slider_ z wyborem limitu czasu na ruch, czy pole wyboru figury,  
- do jej funkcjonalności zalicza się również obsługa wielu gier, bez konieczności wielokrotnego uwierzytelniania oraz sprawdzenie, czy gracz już widnieje w DB.  
## StatsController :bar_chart:   
- umożliwia przejście do ekranu z historią rozgrywek,  
- zbiera dane z DB i wyświetla na ich podstawie wykres kołowy pokazujący wzajemny stosunek wygranych do przegranych i remisów,  
- obsługuje ładowanie formatki *.fxml* z dedykowanym ekranem do wyświetlania statystyk.
