/**
 * @file Global.h
 */
 
/**
 * @name Unveraenderliche Konstanten
 * @warning Werte NICHT veraendern!
 * @{
 */ 
#define NACH_RUNDE 1
#define NACH_ZUG 2
/**@}*/

#define ANZAHL_ZUEGE 5 ///< Die Anzahl der Zuege pro Runde.
#define ANZAHL_ROBOTER 3

#define LCD_ZEICHEN_PRO_ZEILE 16 ///< Anzahl Zeichen pro Zeile

/**
 * @brief Wann der Checkpoint eines Roboters aktualisiert wird.
 * Moegliche Werte: 
 * - NACH_RUNDE
 * - NACH_ZUG
 */
#define CHECKPOINT_UPDATE NACH_RUNDE

/**
 * @brief Soll das Spielfeld auf dem Display ausgegeben werden?
 */
#define SPIELFELD_AUSGEBEN 0
