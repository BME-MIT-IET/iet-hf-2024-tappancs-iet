# Egységtesztek készítése

A feladat során jUnit egységteszteket készítettünk a kiinduló projekthez. A tesztek a model könyvtár osztályaira összpontosítottak, minden egyes model osztályhoz létrehozva egy teszt osztályt:

__PlayerTest__: Ebben az osztályban a Player osztály különböző metódusait teszteltük. Ide tartoztak az elemek közötti mozgás és a pályaelemekkel való interakciók, például a csövek törése és csúszóssá tétele.

__MechanicTest__: Ez az osztály a Mechanic metódusainak tesztelésére szolgál. Olyan műveleteket vizsgáltunk, mint a csövek és pumpák felvétele, elhelyezése, javítása és beállítása.

__SaboteurTest__: A Saboteur specifikus cselekvései közül elsősorban a csövek csúszóssá tételét teszteltük, mivel ez az egyik kevés jellemző akciója.

__PipeTest__: A csövek működését különböző helyzetekben teszteltük. Vizsgáltuk, hogyan működnek, ha mindkét végük csatlakoztatva van, vagy csak az egyik, valamint mi történik lyukas állapotban.

__PumpTest__: Ez az osztály a pumpák különböző működési eseteit vizsgálja. Teszteltük a csövek csatlakoztatását, a pumpa beállításait és a víz megfelelő továbbítását.

__SourceTest__: A források esetében az egységtesztek biztosították, hogy a víztovábbítás helyesen működjön.

__DrainTest__: A ciszternák tesztelésénél azt ellenőriztük, hogy generálódik-e cső, illetve, hogy sikerül-e felvenni a generált csövet különböző esetekben.

# Lefedettség mérése

A lefedettség méréséhez JaCoCo könyvtárat használtuk, de ezt jdk ütközés miatt csak lokálisan futtattuk. A futtatás során generálódik egy report, ahol megtekinthetőek az adott fájlok lefedettségei. A különböző időpontban elvégzett mérések eredményei a "JUnit tesztek" Issue-ban láthatóak.
