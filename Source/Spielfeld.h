/**
 * @file Spielfeld.h
 */

/// X-Laenge des groessten Spielfelds.
#define SPIELFELD_MAX_X 10
/// Y-Laenge des groessten Spielfelds.
#define SPIELFELD_MAX_Y 10

/**
 * @brief Makro zum errechnen des richtigen Array Indexes fuer {@link spielfeld}.
 * @code spielfeld[GET_ARRAY_INDEX(x, y)] @endcode
 */
#define GET_ARRAY_INDEX(X, Y) (((X) * spielfeld_x) + (Y))

/// Alias fuer ein Spielfeld
#define SPIELFELD "feld_lndw.txt"
#download "feld_lndw.txt"

/**
 * @name Feldtypen
 * @note Wenn weitere Felder dazukommen, muss #FELDTYP_ROBOSTART verändert werden!
 * @{
 */
#define FELDTYP_BODEN 0 ///< Normaler Hallenboden.
#define FELDTYP_LOCH 1 ///< Ein Loch.
#define FELDTYP_CHECKPOINT 2 ///< Ein Checkpoint.
#define FELDTYP_REPERATURPUNKT 3 ///< Ein Reperaturpunkt.
#define FELDTYP_FLIESSBAND_RECHTS 4 ///< Ein Fliessband, das nach rechts transportiert.
#define FELDTYP_FLIESSBAND_LINKS 5 ///< Ein Fliessband, das nach links transportiert.
#define FELDTYP_FLIESSBAND_OBEN 6 ///< Ein Fliessband, das nach oben transportiert.
#define FELDTYP_FLIESSBAND_UNTEN 7 ///< Ein Fliessband, das nach unten transportiert.

/**
 * @brief Makro fuer das Startfeld eines Roboters R.
 * @code FELDTYP_ROBOSTART(r) @endcode
 * @note Wenn bei {@link Feldtypen} weitere Felder dazukommen, muss dieses Makro verändert werden!
 */
#define FELDTYP_ROBOSTART(R) ((R)+8)
/**@}*/

/**
 * @brief Das Spielfeld als Array gespeichert.
 * @var u16 spielfeld[x][y]
 *
 * Dabei ist die Variable folgendermassen aufgeteilt:
 * - Bit 1-4: Der Feldtyp binaer abgespeichert
 * - Bit 5-8: Die Mauern (Nord, Ost, Sued, West)
 * - Bit 9-12: Die Laser (v. Nord, v. Ost, v. Sued, v. West)
 * - Bit 13-16: Die Laserstartpunkte (v. Nord, v. Ost, v. Sued, v. West)
 */
u16 spielfeld[SPIELFELD_MAX_X * SPIELFELD_MAX_Y];

/**
 * @name Laenge und Breite des derzeit geoeffneten Spielfelds
 * @{
 */
byte spielfeld_x = 0; ///< x-Laenge des Spielfelds
byte spielfeld_y = 0; ///< y-Laenge des Spielfelds
/**@}*/

unsigned int SpielfeldEinlesen(string spielfeldName);
bool KoordinatenOk(int x, int y);
char Boden(byte x, byte y);
char MauerOsten(byte x, byte y);
char MauerWesten(byte x, byte y);
char MauerNorden(byte x, byte y);
char MauerSueden(byte x, byte y);
char LaserOsten(byte x, byte y);
char LaserWesten(byte x, byte y);
char LaserNorden(byte x, byte y);
char LaserSueden(byte x, byte y);
void SpielfeldAusgeben(short zeit);
