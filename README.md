# BankApp

> Backend aplikacji. Wymaga zainstalowania [Java 8](https://www.java.com/pl/download/) (środowisko uruchomieniowe) oraz [gradle](https://gradle.org/install/) (pakiet manager dla Java).
Użyte technologie:
> - Java 8 (https://vuejs.org/)
> - Spring WebFlux (https://spring.io/blog/2016/07/28/reactive-programming-with-spring-5-0-m1)
> - JSON Web Token (https://jwt.io/)

## Uruchomienie

``` bash
# pobranie projektu
git clone https://github.com/Mike728/bankapp.git

# kompilacja
./gradlew bootJar

# start aplikacji na localhost:8090
java -jar build/libs/bankapp-0.0.1-SNAPSHOT.jar
```
