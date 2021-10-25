# Proiect Energy System Etapa 2

## Despre

Proiectare Orientată pe Obiecte, Seriile CA, CD
2020-2021

<https://ocw.cs.pub.ro/courses/poo-ca-cd/teme/proiect/etapa2>

Student: Maria Moșneag, 323CA

## Rulare teste

Clasa Test#main
  * rulează soluția pe testele din checker/, comparând rezultatele cu cele de
  referintă
  * rulează checkstyle

Detalii despre teste: checker/README

Biblioteci necesare pentru implementare:
* Jackson Core 
* Jackson Databind 
* Jackson Annotations

Tutorial Jackson JSON: 
<https://ocw.cs.pub.ro/courses/poo-ca-cd/laboratoare/tutorial-json-jackson>

## Implementare

### Entități

Input:

    Pentru parsarea informației primite în fișierul .json am folosit clasele din
	pachetul input:

		Input - clasa de bază care stochează toată informația primită,
			folosindu-se de celelalte clase din pachet;

		Data  - conține listele inițiale cu consumatori, distribuitori și
			producători;

		MonthlyUpdates - conține listele cu noi clienți, schimbările de cost ale
			infrastructurii unui distribuitor și modificările
			cantităților de energie oferite de producători.

Entități folosite pentru simulare:

	Informația primită prin input este apoi introdusă în „baza de date” pentru a
	putea fi ușor accesibilă în cadrul programului:

		DataBase - conține informații despre numărul de runde, consumatori,
			   distribuitori, producători și actualizările lunare ce urmează să
			   fie făcute.

	Clasele din pachetele consumer, distribuitor și producer înglobează
	informația despre cele trei entități din cadrul simulării.

	Pachetul strategies conține clase folosite pentru alegerea producătorilor
	pentru un anumit distribuitor, în funcție de strategia aleasă de acesta.

	Clasa MonthlyAction din pachetul entity conține flow-ul simulării.

Output:

	După terminarea simulării, folosesc clasele din pachetul output pentru a
	crea un obiect care să înglobeze rezultatele obținute (instanță a clasei
	Output), scriu informația în fișierul .json de ieșire și șterg datele din
	instanța DataBase pentru ca acestea să nu afecteze următoarele simulări.

### Flow

Flow-ul simulării este cuprins, după cum am menționat și mai sus, în clasa
MonthlyAction. În fiecare lună:
	
	- se actualizează datele consumatorilor și ale distribuitorilor (dacă este
		cazul) dacă nu ne aflăm în runda inițială sau se aleg producătorii și se
		calculează costurile de producție pentru fiecare distribuitor;
	- se calculează prețurile noilor oferte de contract ale distribuitorilor și
		se determină cea mai bună ofertă;
	- se elimină contractele expirate și se creează noile contracte;
	- se realizează plățile distribuitorilor (infrastructură și producție);
	- se realizează plățile contractelor curente de către consumatori;
	- (dacă nu ne aflăm în runda inițială) se modifică datele producătorilor pe
		baza input-ului, se aleg producători noi și se calculează noile costuri
		de producție pentru distribuitori și se reține starea curentă a
		relațiilor dintre distribuitori și producători;
	- se verifică statutul financiar curent al distribuitorilor.

### Design patterns

Factory Pattern

	Având câteva proprietăți comune, am ales să creez clasele care conțin date
	despre consumator, distribuitor și producător ca moștenitoare ale unei clase
	abstracte, Entity. Astfel, am putut să folosesc Factory Pattern pentru a
	facilita crearea instanțelor claselor Consumer, Distributor și Producer, la
	baza implementării acestui design pattern stând clasa EntityFactory.

	De asemenea, am folosit acest design pattern și pentru crearea instanțelor
	claselor care sunt responsabile de alegerea producătorilor pentru un anumit
	distribuitor, ținând cont de strategia acestuia.

Singleton Pattern

	Pentru clasa factory utilizată pentru obținerea instanțelor claselor
	Consumer, Distributor și Producer (EntityFactory), am ales să folosesc acest
	design pattern pentru a asigura ușurința accesului la metoda createEntity
	din orice punct al programului.

Observer Pattern

	Am folosit acest design pattern pentru a implementa mecanismul prin care un
	producător decide dacă este sau nu cazul să își aleagă din nou producătorii.
	Astfel, în cadrul clasei Producer, informația despre cantitatea de energie
	oferită unui distribuitor este reținută sub forma unei instanțe a clasei
	Energy care extinde clasa Observable și notifică observatorii în momentul în
	care sunt efectuate modificări asupra câmpului energyPerDistributor. În
	cazul acesta, observatorii sunt reprezentați de instanțe ale clasei
	ProducerUpdate care funcționează precum un flag reținut în cadrul fiecărei
	instanțe ale clasei Distributor.

Strategy Pattern

	Implementarea acestui design pattern se bazează, în mare parte, pe pachetul
	strategies și are ca scop alegerea producătorilor pentru un anumit
	distribuitor, ținând cont de strategia aleasă de acesta. Există așadar o
	interfață de bază ProducerChooser și trei clase care implementează această
	interfață și au rolul de a sorta lista de producători în funcție de
	preferințele distribuitorului.
