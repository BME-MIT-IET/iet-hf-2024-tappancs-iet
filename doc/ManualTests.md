
# Manuális tesztelés

## A cli megismerése

A folyamat kezdetén megismerkedtünk a különböző commandokkal, amelyek segítségével teszteseteket adhattunk be a programnak és elemezhettük a kimeneteket. Ezek az alapvető commandok lehetővé tették, hogy ellenőrizzük a program egyes komponenseinek helyességét és detektáljuk az esetleges hibákat. A parancsokról a projekt dokumentációjából, a kódból és a cli help utasítása által tájékozódtunk.

## A tesztesetek előállítása

A tesztesetek előállítása során különös figyelmet fordítottunk arra, hogy a lehető legtöbb lehetséges esetet lefedjük. Összesen 37 teszteset készült, melyek a Mechanic-ok és Saboteur-ök viselkedésén kívül a pálya komponenseinek működését is széles körben lefedik. A tesztesetek felépítése:

1. **Tesztpálya inicializálása**: A szükséges pályelemek manuális legenerálása és egymásba kötése.
2. **Szereplők elhelyezése**: A szükséges játékosok manuális példányosítása és elhelyezése a tesztpályán.
3. **Forgatókönyv lejátszása**: Egy kiválasztott funkcionalitás teszteléséhez szükséges akciók végrehajtása.

Bár az 1. és 2. lépés is kimutatható hibaforrás lehet, a cél elsősorban az volt, hogy ezekben a lépésekben alkalmas környezetet alakítsunk ki a 3. lépéshez. Amikor megterveztük a tesztesetet, a játék szabályainak pontos ismeretében tudtuk, hogy mi az elvárt kimenet.
A tesztesetek előállításakor arra törekedtünk, hogy minden bemeneti fájl egyértelműen reprezentáljon egy-egy konkrét esetet, amelyet a programnak kezelnie kell.

## A tesztelés folyamata

A commandok beadása során manuálisan futtattuk a teszteseteket a programon. Minden egyes teszteset futtatása a következő lépésekből állt:

1. **Bemeneti fájl készítése**: A tesztesethez szükséges bemeneti fájlt létrehoztuk.
2. **Program futtatása**: A programot a bemeneti fájllal futtattuk. A kimenet eredménye fájlokba irányítható.
3. **Kiértékelés**: Az kimenet helyességének elemzése.

## Tanulságok

A projekt során számos fontos tanulságot vontunk le a manuális tesztelési folyamattal kapcsolatban:

1. **Alapos előkészületek**: A tesztelni kívánt funkcionalitások felsorolása kulcsfontosságú a magas lefedettséghez.
3. **Tesztesetek előállítása**: A tesztesetek megtervezésénél gondolni kell arra, hogy az egyes funkciók a program különböző állapotaiban más-más eredményt generálhatnak, így igyekezni kell a lehető legtöbb fajta környezetben próbára tenni őket.
4. **Dokumentáció fontossága**: A részletes dokumentáció és naplózás a tesztelési folyamat előrehaladásának nyomonkövetését különösen csapatmunka esetén nagyban elősegíti.
