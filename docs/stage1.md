# Projekt TIC-TAC-TOE - opis i założenia 
:heavy_multiplication_x: :o: :o:   
:o: :heavy_multiplication_x: :o:   
:o: :o: :heavy_multiplication_x:  


  
### **Temat projektu:** _Gra w kółko i krzyżyk "TIC-TAC-TOE" (wersja rozszerzona)_  

### **Specyfikacja, początkowe wymagania, opis tekstowy aplikacji:**  
### **Typ aplikacji : desktopowa :computer:**


- ekran startowy aplikacji będzie zawierał następujące przyciski do wyboru:
    - :arrow_forward: START
    - :information_source: POMOC
    - :arrow_backward: WYJDŹ  
(podczas przebywania w menu w tle będzie grała muzyka i będą wyświetlały się animacje - jak to zwykle bywa w ekranach startowych gier :video_game:)
- po kliknięciu START gracz zostanie poproszony o podanie imienia/nicku 
- do wyboru będą 4 tryby gry:  
1. pojedyncza partia / wiele partii
2. gracz :vs: komputer (przewidujemy 3 poziomy trudności gry ze strony komputera : Beginner, Medium, Expert -> realizacja poprzez stosowne algorytmy)
3. gracz :vs: gracz (lokalnie na jednym komputerze)
4. gracz :vs: komputer SPEEDRUN np. 1,5 sekundy na ruch z odliczaniem na ekranie :clock:  
- po przegranej/wygranej pojawi się odpowiedni komunikat z melodią i pytaniem o dalszą chęć gry
- statystyki gry w formie np. **`nick, liczba wygranych, liczba przegranych, liczba gier nierozstrzygniętych`** zostaną dodane jako rekord do **bazy danych** :pencil: (np. sqllite)  
- po zakończeniu gry możliwe będzie wyjście z aplikacji poprzedzone zapisaniem statystyk lub powrót do menu głównego w celu np. zmiany poziomu trudności w trybie gracz vs. komputer

### **Wstępna specyfikacja technologii/narzędzi :hammer:**  
- technologia : JavaFX :fire:
- narzędzia : np. ORMLite - framework zapewniający komunikację Java :left_right_arrow: Baza danych

