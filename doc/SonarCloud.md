# Sonar Cloud alkalmazása
## Ismerkedés
A sonar termékcsaláddal már rég találkoztam, hiszen kedvenc szövegszermesztőm a VS Code, és abban a sonarlinting-et használom programozásnál. Azonban a sonarcloud-ot labor előtt nem ismertem. Nagyon hasznos és érdekes eszköznek tartom, azonban a laborral ellentétben a sonarcloud hozzárendelése a házi repo-hoz korántsem volt gyerekjáték. Végül azonban győztem, így neki tudtam állni a hibák javításának. 

## Hozzárendelés a projekthez
Abból a tényből adódóan, hogy nem alap struktúrája volt a projektünknek, így korántsem volt evidens a sonarcloud hozzárendelése a projekthez. Először is fel kellett venni egy új ős POM fájlt, amiben a pluginok voltak. A belső kódprojekthez tartozó projektbe pedig a szülő POM-ot, így elérte a sonarcloud a projektet, valamint az általunk írt tesztek is rendesen lefutottak. Azonban még egy repo secretet is létre kellett hozni a github repoban, valamint a yml fájlt is meg kellett szerkezteni, hogy a sonarcloudot kezelni tudja. A sok órát átölelő bütykölésnek meg lett azonban az eredménye, hiszen sikeresen hozzá lett adva a sonarcloud a repohoz.

## Javítás
Ha hozzáadjuk a projekthez a sonarcloudot, akkor a sonarcloud felülelten látunk statisztikákat. Itt találhatóak hibák is, amelyeket javítani tudunk. Itt kiválasztottunk pár problémát a csapattársaimmal és amiket méltónak találtunk, azokat kijavítottuk. Minden hibát külön commitba szerveztem, hogy jobban átlátható legyen a csapatnak, hogy mit, hol, hogyan változtattam, valamint a commit nevek alapján könnyen lehessen keresni egy adott változtatást. 

## Tanulság
Életemben először találkoztam egy ilyen projekt analizáló programmal, amit repo hozzárendelésével tudunk alkalmazni. Most konfiguráltam először maven config fájlokat, valamint a feladat során jobban megismerkedtem a maven projectek struktúrálásával.