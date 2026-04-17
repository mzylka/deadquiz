Deadquiz to prosta aplikacja - quiz na platformę Android.  
Dane są zaciągane z publicznego API: https://deadlock-api.com/  
Aplikacja korzysta z bibliotek: Retrofit i Room.

Funkcje:
- Quiz
- Sprawdzenie statystyk ostatnich gier

## Start

Aplikacja przy pierwszym uruchomieniu sprawdza, czy w bazie danych są już dane dotyczące pytań quiz'u, jeśli nie, to pobierane są one z API i zapisywane.

### Quiz

Po kliknięciu „Start Quiz” aplikacja pobiera dane z bazy danych i generuje 10 pytań, z których każde zawiera 4 odpowiedzi , w tym jedną prawidłową.


Kliknięcie w jedną z odpowiedzi powoduje jej sprawdzenie, jeśli prawidłowa to przycisk odpowiedzi zamienia się w kolor zielony, w przeciwnym przypadku, na czerwono, a prawidłowa odpowiedź na żółto. Po tym można przejść do następnego pytania.

Na końcu wyświetlany jest wynik

### Statystyki

Po kliknięciu w "Statistics" pobierane są wyniki z wszystkich podejść wraz z ich datami i wyświetlane w formie prostej listy.