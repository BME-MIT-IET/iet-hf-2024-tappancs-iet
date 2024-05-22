# Build keretrendszer (Maven) és CI beüzemelése

# Projekt Dokumentáció

## Projekt Név
**Drukmakori Sivatag**

## Maven Projekt Konfiguráció

A projekt egy Maven alapú Java alkalmazás, amelynek alapvető konfigurációi a `pom.xml` fájlban találhatók. rAz alábbiakban bemutatom a lényegesebb részeit:

### `pom.xml`
- **Group ID:** `org.example`
- **Artifact ID:** `drukmakori_sivatag`
- **Verzió:** `1.0-SNAPSHOT`
- **Java Verzió:** 20
- **Kódolás:** UTF-8

### Függőségek
A projekt az alábbi tesztelési függőségeket használja:
- **JUnit 4:** 4.13.2
- **JUnit Jupiter (JUnit 5 API):** 5.9.1
- **JUnit Jupiter Engine:** 5.9.1
- **AssertJ:** 3.24.2

## CI Konfiguráció

A projekt folyamatos integrációját (CI) a GitHub Actions segítségével valósítjuk meg. A fejlesztés során igyekeztünk a megfelelő karbantartani, ugyanakkor a UI teszteket nem sikerült integrálnunk az CI pipelineba. A CI konfiguráció a `maven.yml` fájlban található.

### `maven.yml`
- **Név:** Java CI with Maven
- **Értesítések:** A `main` branch-re történő push és pull request események esetén fut le a workflow.

### CI Folyamat
1. **Környezet Beállítása:** Az Ubuntu legfrissebb verzióján fut a folyamat.
2. **Kódbázis Letöltése:** A kód letöltése a `actions/checkout@v4` használatával.
3. **Java 20 Beállítása:** A `actions/setup-java@v3` segítségével a Java 20-at állítjuk be, Temurin disztribúcióval és Maven cache-eléssel.
4. **Maven Építés:** A projekt építése a `mvn -B package --file drukmakori_sivatag/pom.xml` parancs segítségével történik.

## Lépések a Futtatáshoz

1. **Kód Push:** Minden új kód push a `main` branch-re automatikusan triggereli a CI folyamatot.
2. **Pull Request:** Minden pull request a `main` branch-re szintén triggereli a CI folyamatot.
3. **Építés és Tesztelés:** A rendszer automatikusan letölti a függőségeket, építi a projektet és futtatja a teszteket.

## Cél

A fenti konfiguráció biztosítja, hogy minden kódmódosítás után a projekt automatikusan épül és tesztelésre kerül, javítva ezzel a kód minőségét és a fejlesztési folyamat hatékonyságát.

## További Információ

- Maven dokumentáció: [Maven Project Documentation](https://maven.apache.org/guides/)
- GitHub Actions dokumentáció: [GitHub Actions Documentation](https://docs.github.com/en/actions)
