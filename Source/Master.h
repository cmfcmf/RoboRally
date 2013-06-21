/**
 * @file Master.h
 */

void KartenEinlesen(byte roboter_nummer, int &karten[]);
void LeseKartenEin(void);
void ErrechneReihenfolge(byte zug, byte &reihenfolge[]);
void BewegeRoboterNachVorne(byte r);
void DreheRoboterNachLinks(byte r);
void DreheRoboterNachRechts(byte r);
void RundenScreen(byte runde, byte zug, byte r);
void RoboterToeten(byte r);
void RoboterImLochToeten(void);
task main();
task abortExecution();
