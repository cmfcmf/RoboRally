/**
 * @file Roboter.h
 * @todo Funktionsprototypen updaten!
 */

/**
 * @name Befehle der Karten
 * @{
 */
#define BEFEHL_VOR_1 1 ///< Kartenbefehl zum 1 nach vorne fahren.
#define BEFEHL_VOR_2 2 ///< Kartenbefehl zum 2 nach vorne fahren.
#define BEFEHL_VOR_3 3 ///< Kartenbefehl zum 3 nach vorne fahren.
#define BEFEHL_RUECK 4 ///< Kartenbefehl zum 1 nach hinten fahren.
#define BEFEHL_LINKS 5 ///< Kartenbefehl zum drehen nach links.
#define BEFEHL_RECHTS 6 ///< Kartenbefehl zum drehen nach rechts.
#define BEFEHL_WENDE 7 ///< Kartenbefehl zur 180° Drehung.
/**@}*/

#define ANZAHL_LEBEN 99 ///< Anzahl Leben eines Roboters zu Beginn.
#define ANZAHL_SCHADEN 10 ///< Maximale Anzahl Schadenspunkte eines Roboters.

/**
 * @name Ausrichtungen
 * @warning Diese Defines duerfen nicht veraendert werden!
 * @{
 */
#define AUSRICHTUNG_NORD 1 ///< Ausrichtung nach Norden.
#define AUSRICHTUNG_OST 2 ///< Ausrichtung nach Osten.
#define AUSRICHTUNG_SUED 3 ///< Ausrichtung nach Sueden.
#define AUSRICHTUNG_WEST 4 ///< Ausrichtung nach Westen.
/**@}*/


/**
 * @brief Datenstruktur fuer einen Roboter.
 * @todo Sollen wir wirklich zwischen Schaden und Leben unterscheiden?
 */
typedef struct {
	byte position_x; ///< Derzeitige x-Position des Roboters.
	byte position_y; ///< Derzeitige y-Position des Roboters.
	byte checkpoint_x; ///< Die x-Position vom letzten Checkpoint.
	byte checkpoint_y; ///< Die y-Position vom letzten Checkpoint.
	byte checkpoint_ausrichtung; ///< Die Ausrichtung am letzten Checkpoint.
	byte ausrichtung; ///< Die Ausrichtung des Roboters.
	byte leben; ///< Verbleibende Leben des Roboters.
	byte schaden; ///< Schaden des Roboters.
	byte kanal; ///< Bluetooth Kommunikationskanal.
	byte zug; ///< Der Zug, indem sich der Roboter gerade befindet.
	int karten[ANZAHL_ZUEGE]; ///< Die Karten fuer die naechsten 5 Zuege.

	/**
	 * @brief Wenn true, wird der Roboter bis zum Ende der Runde uebergangen. Bei Beginn der naechsten Runde wird diese Variable bei allen Roboter automatisch wieder auf false gesetzt.
	 * @see main()
	 */
	bool pause;
} roboter;

byte RoboBefehl(byte r, byte zug);
int RoboKartennummer(byte r, byte zug);
void ResetRoboArray(void);
void RoboSetZug(byte r, byte zug);
byte RoboGetZug(byte r);
void RoboSetPause(byte r, bool pause);
bool RoboGetPause(byte r);
void RoboSetPosition(byte r, byte x, byte y);
void RoboGetPosition(byte r, byte &x, byte &y);
void RoboSetCheckpoint(byte r, byte x, byte y, byte ausrichtung);
void RoboGetCheckpoint(byte r, byte &x, byte &y, byte &ausrichtung);
void RoboSetKanal(byte r, byte kanal);
byte RoboGetKanal(byte r);
void RoboSetAusrichtung(byte r, byte ausrichtung);
byte RoboGetAusrichtung(byte r);
char RoboAufFeld(byte x, byte y);
void RoboAusrichtungUmEinsNachLinks(byte r);
void RoboAusrichtungUmEinsNachRechts(byte r);
char RoboPositionUmEinsNachVorne(byte r);
char RoboPositionUmEinsNachHinten(byte r);
