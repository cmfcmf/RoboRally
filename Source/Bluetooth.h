/**
 * @file Bluetooth.h
 */

/**
 * @name Bluetooth Befehle
 * @{
 */
#define BLUETOOTH_NACH_VORNE           1 ///< Fahre um ein Feld nach vorne.
#define BLUETOOTH_NACH_LINKS           2 ///< Drehe dich auf der Stelle nach links.
#define BLUETOOTH_NACH_RECHTS          3 ///< Drehe dich auf der Stelle nach rechts.
#define BLUETOOTH_NACH_HINTEN          4 ///< Fahre um ein Feld nach hinten.
#define BLUETOOTH_WENDE                5 ///< Drehe dich auf der Stelle um 180°.
#define BLUETOOTH_TOT                  6 ///< @todo Umbenennen, Unterscheiden zwischen wirklich tot und Leben abziehen
#define BLUETOOTH_BLINK                7 ///< Laesst den Roboter blinken.
#define BLUETOOTH_BLINK_STOP           8 ///< Der Roboter hoert auf zu blinken.
#define BLUETOOTH_NACH_VORNE_AUS_FELD  9 ///< Fahre ein Feld nach vorne, aber ohne auf Linien zu achten, da das Spielfeld zu Ende ist.
#define BLUETOOTH_NACH_HINTEN_AUS_FELD 10 ///< Fahre ein Feld nach hinten, aber ohne auf Linien zu achten, da das Spielfeld zu Ende ist.
#define BLUETOOTH_CHECKPOINT           11 ///< Spiele Checkpoint-Melodie.
#define BLUETOOTH_PIEPEN               12 ///< Fange an zu piepen.
#define BLUETOOTH_PIEPEN_STOP          13 ///< Hoere auf zu piepen.

#define BLUETOOTH_SUCCESS true ///< Roboter hat Fahrbefehl verstanden und erfolgreich ausgefuehrt.
/**
 * @brief Der Roboter sendet seine Batteriestaerke zurueck.
 * @warning NICHT MIT BluetoothBefehlSenden() VERWENDEN!! IMMER BluetoothGetBatteryLevel() BENUTZEN!
 */
#define BLUETOOTH_GET_BATTERY_LEVEL 20 ///< Slave soll seinen Batterie-Level zurueckgeben.
/**@}*/

#define MASTER_INBOX  MAILBOX2      ///< Die Mailbox, die der Master benutzt um Nachrichten zu verschicken.
#define MASTER_OUTBOX MAILBOX1      ///< Die Mailbox, die der Master benutzt um Nachrichten zu empfangen.

#define SLAVE_INBOX   MASTER_OUTBOX ///< Die Mailbox, die der Slave benutzt um Nachrichten zu verschicken.
#define SLAVE_OUTBOX  MASTER_INBOX  ///< Die Mailbox, die der Slave benutzt um Nachrichten zu empfangen.


/**
 * @name Fixes für falschen Compiler Code, einfach ignorieren!
 * @{
 */
#undef DRAW_OPT_CLEAR_EOL
#define DRAW_OPT_CLEAR_EOL (0x1000) 
 
#undef RemoteStopProgram(_conn)
#define RemoteStopProgram(_conn) asm { __bluetoothWrite(_conn, __DCStopProgramPacket, __RETVAL__) }

#undef RemoteKeepAlive(_conn)
#define RemoteKeepAlive(_conn) asm { __bluetoothWrite(_conn, __DCKeepAlivePacket, __RETVAL__) }
/**@}*/

#define UNTIL(BEFEHL, BEDINGUNG, ZEIT_BEVOR_ERROR, ERROR_FUNC, ERROR_TEXT, VERSUCHE, PROGRAMM_STOP) \
    long t = CurrentTick(); \
    bool d = false; \
    byte v = 0; \
    BEFEHL; \
    while(!(BEDINGUNG)) { \
        if (CurrentTick() - t > (ZEIT_BEVOR_ERROR)) { \
            if ( !d) { \
                v++; \
                TextOut(0, LCD_LINE1, "Bluetooth ERROR!", DRAW_OPT_CLEAR_EOL); \
                TextOut(0, LCD_LINE2, FormatNum("Versuch %d", v), DRAW_OPT_CLEAR_EOL); \
                TextOutML(0, LCD_LINE3, ERROR_TEXT); \
                ERROR_FUNC; \
                d = true; \
                d = false; \
                t = CurrentTick(); \
                BEFEHL; \
            } \
            if (v > (VERSUCHE)) { \
                ClearScreen(); \
                if (PROGRAMM_STOP) { \
                    TextOut(0, LCD_LINE1, "Prog beendet", DRAW_OPT_CLEAR_EOL); \
                    TextOutML(0, LCD_LINE2, ERROR_TEXT); \
                    Wait(5000); \
                    Stop(1); \
                } else { \
                    TextOut(0, LCD_LINE1, "Prog geht weiter", DRAW_OPT_CLEAR_EOL); \
                    TextOutML(0, LCD_LINE2, ERROR_TEXT); \
                    Wait(2000); \
                } \
            } \
        } \
    }

void BluetoothBefehlSenden(byte kanal, int befehl);
bool BluetoothVerbindungPruefen(byte kanal);
void BTWait(byte channel);
string BTNXTName(byte channel);
bool BTIsConnected(byte channel, string nxtName);
void BTConnectNXT(byte channel, string nxtName);
string BTCurrentProgram(byte channel);
void BTStartProgram(byte channel, string programName);
void BTStopProgram(byte channel);
int BTGetBatteryLevel(byte channel);
void BTKeepAlive(byte channel);
void BTEnableBluetooth();
bool BTConnect(byte channel, string nxtName);
void BTDisconnect(byte channel);
string BTProgramName2NXCProgramName(string programName);
string BTNXCProgramName2programName(string NXCProgramName);

