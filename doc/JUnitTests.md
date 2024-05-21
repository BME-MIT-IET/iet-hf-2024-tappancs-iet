# Egységtesztek készítése

A feladat során jUnit egységteszteket készítettünk a kiinduló projekthez. A tesztek a model osztályokra összpontosítottak, minden egyes osztályhoz létrehozva egy teszt osztályt:

__PlayerTest__: Ebben az osztályban a Player osztály különböző metódusait teszteltük. Ide tartoznak az elemek közötti mozgás és a pályaelemekkel való interakciók, mint például a csövek törése, csövek ragadóssá tétele, játékos mozgatása.

__MechanicTest__: Ez az osztály a Mechanic metódusaihoz tartozó teszteket tartalmazza. Olyan műveleteket vizsgáltunk, mint a csövek és pumpák felvétele, elhelyezése, javítása és a pumpák átállítása.

__SaboteurTest__: Mivel egyetlen szabotőr specifikus cselekvés létezik, így ez az osztály az ehhez tartozó tesztet tartalmazza, amely nem más, mint a cső csúszóssá tételének tesztje.

__PipeTest__: Itt találhatók a Pipe osztály metódusaihoz tartozó tesztek. Ilyenek például a cső megjavítása, játékosok elfogadása attól függően, hogy mennyien tartózkodnak jelenleg a csövön, a csőben a víz áramlásának esetei, vagy hogy, csúszós csőre ne tudjon játékos lépni.

__PumpTest__: Ez az osztály a pumpák különböző működési eseteit vizsgálja. Teszteltük a pumpa átállítását, megjavítását, illetve a víz továbbításának folyamatát.

__SourceTest__: A források egyedül annak léptetését kellett tesztelni, hiszen ezek a víz áramlásának forrásai.

__DrainTest__: A ciszternák tesztelésénél a cső generálásával kapcsolatos eseteket vizsgáltuk.

# Lefedettség mérése

A lefedettség méréséhez a JaCoCo könyvtárat használtuk, de ezt jdk ütközés miatt csak lokálisan futtattuk. A futtatás során generálódik egy report, ahol megtekinthetőek az adott fájlok lefedettségeit. A különböző időpontban elvégzett mérések eredményei a "JUnit tesztek" Issue-ban láthatóak. Az első teszt futtatása után látszik, hogy viszonylag még kevés a lefedettsége az egyes osztályoknak, de második futtatás után, miután pótoltuk a hiányzó teszteseteket, már egészen jónak mondhatók az arányok.

# Tanulság, eredmények

A unit tesztelés lehetőséget biztosított arra, hogy az egyes modell osztályokhoz tartozó függvények működését alaposan átvizsgáljuk, és a felmerülő logikai hibákat észrevegyük, kijavítsuk. A tesztelési folyamat során többször is szükség volt a kód átírására, amelyeknek köszönhetően már elvárt módon működött a program. Mivel segített az apróbb hibák megtalálásában is, hozzájárult ezzel a kód minőségének javításához. Összességében tehát nagyon hasznosnak találtuk a unit tesztekkel való megismerkedést, ráadásul elengedhetetlenek a modern szoftverfejlesztésben. 
