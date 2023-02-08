# Działająca aplikacja :video_game:   
# (uwzględniająca uwagi Prowadzącego z 3-go etapu)   
### :arrow_right:  Termin wykonania :date:  do 14.06.2021  
### :arrow_right:  Maksymalna liczba punktów do zdobycia -> 10p

## Implementacja :notebook: :pencil2:  
- wprowadzenie poprawek wskazanych przez Prowadzącego:  
Na koniec trzeciego etapu Prowadzący zasugerował następujące modyfikacje:  
![image](uploads/71588d1dd9496ecdf9ed21c2b581a85e/image.png)  


### Wdrożenie zasugerowanych zmian :new: 
- Z pomocą narzędzia **Maven** dokonaliśmy uporządkowania plików w pakiety i stosowne katalogi :open_file_folder:  
**TicTacToe** to główny katalog z projektem zbudowanym przez Maven'a, wewnątrz niego zostały wydzielone między innymi katalogi:  
    - `/src`- który wprowadza następującą strukturę plików: `main/java` - główne klasy aplikacji, `main/resources` - formatki _.fxml_ , pliki ze stylami _.css_, oraz czcionkami _.ttf_, `test/java` - klasy testujące. 
    -  `/.idea` - określa używane w projekcie biblioteki, które pobierane są z repozytorium *Maven'a*

- Rozszerzyliśmy dotychczasowy opis klas czyniąc go bardziej szczegółowym, sprecyzowaliśmy zadania/funkcjonalności poszczególnych klas :pencil:. Aby wspomniane zadania przestawić w możliwie przejrzysty sposób dodaliśmy dodatkową zakładkę w Wiki - [_**Opis klas**_](https://gitlab-stud.elka.pw.edu.pl/pkrasnod/pap21l-z12/-/wikis/Opis-klas) . 



## Dokumentacja końcowa :card_box:   
### Krytyczna analiza rozwiązania: :thumbsup: :heavy_plus_sign: :thumbsdown:   
**- znane ograniczenia :construction:**  
1) Aplikacja niestety nie jest cross-platformowa.
2) Może potencjalnie zajmować dużo miejsca na dysku (szczególnie w formie tzw. _fatjar_)
3) W przypadku aktualizacji to użytkownicy muszą ręcznie zainstalować _update_.
4) W przypadku aplikacji webowych :earth_americas: wymagania systemowe nie są istotne, ale w naszej aplikacji desktopowej już tak.
  
  
**- rozwiązane problemy :white_check_mark:**  
- Dodaliśmy możliwość odtwarzania przebiegu gry. Początkowo planowaliśmy dodanie interfejsu, w którym podaje się numer rozgrywki, a następnie otwiera się nowe okno z animacją. Stwierdziliśmy jednak, że nie jest to interfejs przyjazdy użytkownikowi i niepotrzebnie otwierane są kolejne, nowe okna. Problem rozwiązała funkcjonalność JavaFX pozwalająca na wykonanie akcji po najechaniu na np. obszar tekstu - `text.hoverProperty().addListener()`.  
- W całym projekcie intensywnie korzystaliśmy z podobnych możliwości oferowanych przez JavaFX - np. `setOnCloseRequest()` pozwalało nam wykonać dodatkowe działania takie jak przerwanie konkretnego wątku przy zamykaniu któregoś z okien. Pozwoliły nam one rozwiązać problemy podobne do opisanego powyżej.
  
   
**- możliwości dalszego rozwoju aplikacji  :computer: :tools:**  
- Uważamy, że aplikacja ma duży potencjał na ewentualny rozwój w przyszłości. Sami zaczęliśmy implementację od podstawowej wersji kółko i krzyżyk :x: :o:, ale udało nam się stworzyć jej  modyfikacje. Tryby takie jak _Speedrun_ :runner:, czy tryb z algorytmem heurystycznym po stronie komputera to jedne z wielu możliwości. Kreatywność nie zna granic, więc w grze można zaimplementować inne ciekawe modyfikacje podstawowej wersji gry.  
  
- Nasza aplikacja jest desktopowa, więc gra w trybie *multiplayer* odbywa się na jednym urządzeniu. Potencjalnie można zmodyfikować ten tryb i umożliwić grę w dwie osoby na dwóch różnych maszynach. Wtedy jedna z maszyn byłaby klientem, a druga serwerem.
  
  
**- inne wnioski/spostrzeżenia :thinking:**  
**(Kwestia dystrybucji i udostępniania aplikacji użytkownikom)**
- W naszej aplikacji wykorzystujemy biblioteki pakietu **JavaFX 16**, bibliotekę obsługującą driver do MySQL DB oraz bibliotekę dołączającą _framework_ do testowania. Podczas pracy nad projektem powyższe biblioteki mieliśmy pobrane oraz dołączone w VS Code w zakładce *Referenced Libraries*. Jesteśmy jednak świadomi, że użytkownik chcący uruchomić grę nie będzie chciał tego robić przez IDE :game_die: (nie musi być w szczególności programistą :rofl:). Gra powinna dać się uruchomić z poziomu pulpitu. Z tego względu zdecydowaliśmy się utworzyć plik **JAR**. Minusem takiego rozwiązania jest konieczność wskazania na lokalizację bibliotek JavaFX podczas uruchamiania programu ( komenda poniżej):  
`java --module-path c:/javafx-sdk-16/lib/ --add-modules javafx.controls,javafx.fxml -jar Game.jar`  
Wymaga to od każdego użytkownika lokalnego zainstalowania bibliotek JavaFX, a według nas 99% potencjalnych graczy nie ma zainstalowanych wspomnianych bibliotek u siebie na komputerze :books:.

- W trakcie projektu nie korzystaliśmy z narzędzi automatyzujących budowę oprogramowania na platformę Java. Wynika to z faktu, iż dowiedzieliśmy się o nich z wykładu w momencie gdy projekt był już w zaawansowanej fazie. Mimo to pod koniec projektu wykorzystaliśmy narzędzie _**Maven**_. Pomogło nam ono uporządkować strukturę plików w katalogi. Najprawdopodobniej gdybyśmy wcześniej wiedzieli o narzędziach takich jak właśnie *Maven*, czy *Gradle* to użylibyśmy ich w projekcie :tools:.

  
  

:warning: W trakcie pracy nad projektem tworzyliśmy dokumentację w postaci kolejnych stron w zakładce **Wiki** naszego repozytorium. Pod koniec 4-go etapu całość zawartości Wiki została przekonwertowana do pliku *.pdf* i umieszczona na czacie MS Teams, którego członkami są Prowadzący oraz studenci wykonujący projekt. 


## Prezentacja :loud_sound:  
Prezentacja wprowadzonych zmian odbyła się na platformie _**MS Teams**_ :computer: :telephone_receiver: 

## Elementy dodatkowe :star2:  
- Stworzona przez nas gra jest aplikacją desktopową, więc nie było (z definicji) możliwe jej wdrożenie w środowisku chmurowym :cloud: (np. Heroku). 
- Aby zwiększyć faktyczną użyteczność naszej aplikacji postanowiliśmy dodatkowo stworzyć plik **JAR** pozwalający uruchomić grę z poziomu pulpitu/ linii poleceń, a nie tylko przy użyciu IDE takiego jak VS Code.  
<img src="uploads/0f899f03733ed50d025c633733d68613/image.png"  width="60" height="60">