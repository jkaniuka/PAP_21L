# Działająca aplikacja :video_game: 

### Spotkanie w połowie etapu: :telephone_receiver: 
Spotkanie odbyło się 29.04.2021 o godzinie 16:30 na platformie Teams.

## Projekt aplikacji: :pencil: 
  
### -  Architektura 
**Schemat poglądowy kluczowych klas w strukturze projektu:**  
![image](https://user-images.githubusercontent.com/80155305/217680950-21c6de6b-a937-4456-b346-f01eb033ac57.png)



### -  Wykorzystywane narzędzia, biblioteki, zależności :books: 

**Narzędzia:**
<img src="https://user-images.githubusercontent.com/80155305/217681001-70d7edbb-a723-4a76-8253-577f470090ca.png"  width="170" height="80">  
- **Scene Builder** - tworzenie bardziej złożonych ekranów z wieloma polami wejściowymi lub wyboru i przyciskami - metoda Drag&Drop  
- **phpMyAdmin** - tworzenie nowych tabel, zarządzanie bazą danych

**Biblioteki: :book:**
<img src="https://user-images.githubusercontent.com/80155305/217681082-15fb75f3-f484-46db-9702-9cb99696fa47.png"  width="300" height="80">  

- biblioteki **JavaFx** w wersji 16
- **mysql connector java 8.0.22.jar**
- **junit-platform-console-standalone.jar**


### -  Główne klasy: :inbox_tray: 
Struktura projektu jest rozbudowana i składa się z wielu klas. Niektóre z nich mają zbliżoną strukturę oraz zastosowanie. Z tego powodu opiszemy poniżej po jednej klasie reprezentatywnej dla kolejno : 1) bazy danych, 2) logiki gry, 3) kontrolera realizującego żądania użytkownika:
 
- **BaseMenager.java** - w tej klasie zaimplementowano metody odpowiedzialne między innymi za: połączenie/rozłączenie z DB, dodanie nowego gracza/zespołu oraz dodanie statystyk gry.
- **GameBasic.java** - klasa odpowiada za logikę gry ( konkretnie w trybie Multiplayer). Klasy takie jak _Speedrun, MiniMax_ i inne mają podobną strukturę, lecz różnią się implementacją algorytmów determinujących ruchy komputera.  
- **StartGameController.java** - obsługuje przyjmowanie _nicku_ od gracza, powrót do poprzedniego ekranu ze statystykami/instrukcją oraz wybór rodzaju gry wraz z liczbą tychże gier (bez konieczności wielokrotnego uwierzytelniania).

### -  Odniesienie do zastosowanego wzorca projektowego 
Zastosowaliśmy koncepcję **Model-Widok-Kontroler.**  
**Model** - stosowany jest do pobierania i przygotowania rekordów z bazy danych  
**Widok** -  reprezentuje to, co widzi użytkownik  
**Kontroler** - odpowiada m.in. za przetwarzanie danych pobranych za pomocą modelu i przekazanie ich użytkownikowi oraz zapisanie danych przez niego podanych (poprzez widok)


## Implementacja: :desktop_computer:

### - struktura systemu (organizacja warstw)
- **Frontend** - użytkownik może oddziaływać na grę z poziomu GUI, ma możliwość przeglądania statystyk, historii gier, wyboru liczby/trybów/czasu gier oraz może także przejść do sekcji _HowTo_ w razie wątpliwości.
- **Backend** - zapewnia działanie wielu trybów gier (multiplayer, z ograniczonym czasem i innych)
- **Data Base** - połączenie z bazą danych, pobieranie rekordów w celu wyświetlenia tabeli statystyk oraz tworzenie _statement'ów_ z nowymi osiągnięciami graczy


### - zgodność z standardami programowanie w Javie  
![alt text](https://checkstyle.org/images/header-checkstyle-logo.png)  
Każdy z członków zespołu zainstalował w Visual Studio Code plugin _Checkstyle_ i przy jego pomocy poprawione zostały wszystkie błędy leksykograficzne oraz strukturalne. Za wzorzec przyjęliśmy **Google Check** - _google_checks.xml_      

### - testy jednostkowe dla wybranych klas/metod 
Przy wykonywaniu testów jednostkowych korzystaliśmy z **JUnit5**.  
Wspomniane testy znajdują się w następujących plikach:
- _**GameLogicTest.java**_ - testy funkcji realizujących poszczególne elementy logiki aplikacji (sprawdzenie istnienia wygranej/ końca gry bez rozstrzygnięcia, sprawdzenie poprawności zapisu sekwencji ruchów)
- _**BaseMenegerTest.java**_ - testy sprawdzające poprawność komunikacji z bazą danych (połączenie/rozłączenie, zapis/pobieranie statystyk)


## Dokumentacja: :card_file_box:

### -  Wymagania środowiskowe - **Java Development Kit** w wersji 15.02:    
1) Aplikacja powstawała w środowisku Visual Studio Code.
2) Biblioteki potrzebne do uruchomienia aplikacji z poziomu IDE zostały już opisane powyżej.
3) Instrukcja uruchomienia (zarówno pliku _.jar_ jak i kodu w IDE) znajduje się w dalszej części tego sprawozdania.

### -  Instrukcja budowania kodu i uruchomienia aplikacji
### IDE (kod) :page_facing_up: 
<img src="https://user-images.githubusercontent.com/80155305/217681192-20106645-ceb6-4e58-8556-95405ee8e27c.png"  width="400" height="60"> 


      
1) Należy sprawdzić, czy wszystkie niezbędne biblioteki wymienione powyżej są dołączone w zakładce _Referenced Libraries_.
2) Uruchamiamy plik **App.java**.

### Uruchomienie przez gracza :game_die:
<img src="https://user-images.githubusercontent.com/80155305/217681219-5b5bcfea-994b-44ec-a8b5-bd5189fabb14.png"  width="60" height="60">  

  
W konsoli wpisujemy _**java --module-path c:/javafx-sdk-16/lib/ --add-modules javafx.controls,javafx.fxml -jar Game.jar**_ i rozpoczynamy rozgrywkę.  
:warning: W powyższej komendzie należy dokonać wskazania na lokalizację bibliotek JavaFX zainstalowanych lokalnie na dysku komputera oraz ewentualnie inną lokalizację pliku z aplikacją.


### -  Raport Checkstyle  
Kod pozytywnie przeszedł walidację _Checkstyle_ :white_check_mark:  - dowodem tego są liczne commity opisane jako "Zmiany leksykograficzne i strukturalne - Checkstyle". Nie dysponujemy jednak raportem, ponieważ nie wykorzystywaliśmy narzędzia _Maven_ w czasie prac nad projektem.
