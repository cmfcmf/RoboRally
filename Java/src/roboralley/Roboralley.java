/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package roboralley;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Locale;
import java.util.Properties;
import java.util.Scanner;
import java.util.logging.Logger;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.Timer;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

/**
 *
 * @author Christian Flach <cmfcmf.flach@gmail.com>
 */
public class Roboralley {

    private static Window window;
    /**
     *
     */
    //"C:\\\"Dokumente und Einstellungen\"\\MuLFUser\\Desktop\\NextTool.exe";
    public static String pathToNextTool = "C:\\\"Program Files (x86)\"\\BricxCC\\NextTool.exe";
    /**
     *
     */
    public static int inbox = 1;
    private static ArrayList<RoboAction> roboActions = new ArrayList<>();
    /**
     *
     */
    public static int currentRobo = 0;
    /**
     *
     */
    public static int currentMove = 0;
    /**
     *
     */
    public static int pollRate = 500;
    /**
     *
     */
    public static Timer timer;
    public static int akkuEmpty = 7000;
    private static int counter = 0;
    private static boolean isWindows = false;

    private static void fillRoboActionList() {
        roboActions.add(new RoboAction("right", "Rechts", 100, 400, 20));
        roboActions.add(new RoboAction("left", "Links", 100, 400, 20));
        roboActions.add(new RoboAction("uturn", "Wende", 200, 500, 6));
        roboActions.add(new RoboAction("forward1", "1x Vorwärts", 300, 600, 17));
        roboActions.add(new RoboAction("forward2", "2x Vorwärts", 400, 700, 14));
        roboActions.add(new RoboAction("forward3", "3x Vorwärts", 500, 800, 8));
        roboActions.add(new RoboAction("backward", "Rückwärts", 300, 600, 0/*13*/));
    }

    private static void enableButtons() {
        enableButtons(true);
    }

    private static void enableButtons(boolean enable) {
        window.btn1.setEnabled(enable);
        window.btn2.setEnabled(enable);
        window.btn3.setEnabled(enable);
        window.btn4.setEnabled(enable);
        window.btn5.setEnabled(enable);
        window.btn6.setEnabled(enable);
        window.btn7.setEnabled(enable);
        window.btn8.setEnabled(enable);
        window.btn9.setEnabled(enable);
        window.btn10.setEnabled(enable);
    }

    private static RoboAction getRandomRoboAction() {
        int maxNumber = 0;
        for (RoboAction roboAction : roboActions) {
            maxNumber += roboAction.frequency;
        }
        int randomNumber = randBetween(1, maxNumber);
        int currentNumber = 0;
        int oldNumber = 0;
        RoboAction chosenRoboAction = null;
        for (RoboAction roboAction : roboActions) {
            currentNumber += roboAction.frequency;
            if (randomNumber <= currentNumber && randomNumber > oldNumber) {
                chosenRoboAction = roboAction;
                break;
            }
            oldNumber = currentNumber;
        }
        return chosenRoboAction;
    }

    public static void randomizeButtons() {

        RoboAction roboAction = getRandomRoboAction();
        window.btn1.setText(roboAction.displayName + " (" + randBetween(roboAction.minNumber, roboAction.maxNumber) + ")");
        roboAction = getRandomRoboAction();
        window.btn2.setText(roboAction.displayName + " (" + randBetween(roboAction.minNumber, roboAction.maxNumber) + ")");
        roboAction = getRandomRoboAction();
        window.btn3.setText(roboAction.displayName + " (" + randBetween(roboAction.minNumber, roboAction.maxNumber) + ")");
        roboAction = getRandomRoboAction();
        window.btn4.setText(roboAction.displayName + " (" + randBetween(roboAction.minNumber, roboAction.maxNumber) + ")");
        roboAction = getRandomRoboAction();
        window.btn5.setText(roboAction.displayName + " (" + randBetween(roboAction.minNumber, roboAction.maxNumber) + ")");
        roboAction = getRandomRoboAction();
        window.btn6.setText(roboAction.displayName + " (" + randBetween(roboAction.minNumber, roboAction.maxNumber) + ")");
        roboAction = getRandomRoboAction();
        window.btn7.setText(roboAction.displayName + " (" + randBetween(roboAction.minNumber, roboAction.maxNumber) + ")");
        roboAction = getRandomRoboAction();
        window.btn8.setText(roboAction.displayName + " (" + randBetween(roboAction.minNumber, roboAction.maxNumber) + ")");
        roboAction = getRandomRoboAction();
        window.btn9.setText(roboAction.displayName + " (" + randBetween(roboAction.minNumber, roboAction.maxNumber) + ")");
        roboAction = getRandomRoboAction();
        window.btn10.setText(roboAction.displayName + " (" + randBetween(roboAction.minNumber, roboAction.maxNumber) + ")");
    }

    private static void disableButtons() {
        enableButtons(false);
    }

    private static int randBetween(int min, int max) {
        return (int) (int) Math.round(Math.random() * (max - min) + min);
    }

    private static void checkOS() {
        System.out.println("Hallo Welt!");
        Properties prop = System.getProperties();
        String osString = prop.getProperty("os.name");

        if (osString.toLowerCase(Locale.ENGLISH).startsWith("windows")) {
            isWindows = true;
        } else {
            isWindows = false;
        }
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException e) {
        }

        checkOS();

        // Generate window and make window visible
        window = new Window();
        window.setVisible(true);


        disableButtons();

        fillRoboActionList();

        updateConnectionInfos();

        // Start timer
        timer = new Timer(Roboralley.pollRate, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String message = receiveFromNxt();

                if (message.equals("ok")) {
                    window.textField.setText("Eingabe erfolgreich!");
                    disableButtons();
                } else if (message.startsWith("roboter")) {
                    // i.e. roboter_1
                    String[] temp = message.split("_");
                    String roboId = temp[1];
                    window.textField.setText("Roboter " + roboId + ": Bitte Karten eingeben!");
                    currentRobo = new Integer(roboId);
                    currentMove = 0;
                    randomizeButtons();
                    enableButtons();
                } else if (message.startsWith("status_")) {
                    //i.e. status_0_1_2 (Round 0, move 1, roboter 2)
                    String[] temp = message.split("_");
                    window.roundLabel.setText(String.valueOf(new Integer(temp[1]) + 1));
                    window.moveLabel.setText(String.valueOf(new Integer(temp[2]) + 1));
                    window.roboterLabel.setText(String.valueOf(new Integer(temp[3]) + 0));
                } else if (message.startsWith("battery_")) {
                    //i.e. battery_0_5674 (Roboter 0, battery 5674)
                    String[] temp = message.split("_");
                    window.slaveTable.setValueAt(volt2Str(temp[2]), new Integer(temp[1]), 2);
                    if (!temp[2].equals("0")) {
                        window.slaveTable.setValueAt(true, new Integer(temp[1]), 1);
                    } else {
                        window.slaveTable.setValueAt(false, new Integer(temp[1]), 1);
                    }
                    if (new Integer(temp[2]) <= akkuEmpty) {
                        JOptionPane.showMessageDialog(null, "Der Akku von Roboter " + temp[1] + " ist leer!", "Akku leer!", JOptionPane.ERROR_MESSAGE);
                    }
                } else if (message.equals("hideTable")) {
                    window.table.setVisible(false);
                } else if (message.equals("showTable")) {
                    window.table.setVisible(true);
                } else if (message.startsWith("lives_")) {
                    //i.e. lives_0_3_7 (Roboter 0, 3 lives, 7 damage points)
                    String temp[] = message.split("_");
                    window.slaveTable.setValueAt(temp[2], new Integer(temp[1]), 3);
                    window.slaveTable.setValueAt(temp[3], new Integer(temp[1]), 4);
                } else if (message.startsWith("msg_")) {
                    String temp[] = message.split("_");
                    JOptionPane.showMessageDialog(null, temp[1], "Achtung!", JOptionPane.INFORMATION_MESSAGE);
                }

                counter++;

                if (pollRate * counter >= 5000) {
                    updateConnectionInfos();
                    resetSleepTimer();
                    counter = 0;
                }
            }
        });
        timer.start();
    }

    private static String volt2Str(String batteryValue) {
        return String.valueOf((double) Math.round(new Float(batteryValue) / 1000 * 100) / 100) + " V";
    }

    public static void updateConnectionInfos() {
        String batteryMaster = getMasterBattery();
        String runningProgram = getRunningProgram();
        if (!batteryMaster.isEmpty()) {
            window.voltage.setText(volt2Str(batteryMaster));
            window.nxtConnected.setSelected(true);

            if (runningProgram.equals("Master.rxe")) {
                window.rightProgram.setSelected(true);
                window.startMasterProgram.setEnabled(false);
                window.stopMasterProgram.setEnabled(true);
            } else {
                window.rightProgram.setSelected(false);
                window.startMasterProgram.setEnabled(true);
                window.stopMasterProgram.setEnabled(false);
            }
        } else {
            window.voltage.setText("0 V");
            window.nxtConnected.setSelected(false);
            window.startMasterProgram.setEnabled(false);
            window.stopMasterProgram.setEnabled(false);
        }
    }

    /**
     * Sets the sleeptime of the NXT to 100 minutes.
     */
    private static void resetSleepTimer() {
        try {
            String cmd = pathToNextTool + " /COM=usb -sleep=100";
            Roboralley.executeNativeProcess(cmd);
            System.out.println("Command executed: " + cmd);
        } catch (Exception e) {
            System.out.println("Exception:" + e.getMessage());
        }
    }

    /**
     *
     * @param btn
     */
    protected static void btnPressed(JButton btn) {
        btn.setEnabled(false);
        for (RoboAction roboAction : roboActions) {
            if (btn.getText().startsWith(roboAction.displayName)) {
                String[] temp = btn.getText().split("\\(");
                String[] temp2 = temp[1].split("\\)");

                //Send command to NXT
                sendToNxt(new Integer(temp2[0]) + "_" + roboAction.command);

                //Log command in textField
                window.textField.setText(window.textField.getText() + "\n" + btn.getText() + " gewählt");

                //Add to table
                window.table.setValueAt(roboAction.displayName + " (" + temp2[0] + ")", currentRobo, currentMove + 1);

                //Increment current move
                currentMove++;
            }
        }
    }

    private static String executeNativeProcess(String command) {
        try {
            ProcessBuilder builder;

            if (isWindows) {
                builder = new ProcessBuilder("cmd", "/c", command);
            } else {
                builder = new ProcessBuilder(command);
            }
            Process p = builder.start();

            @SuppressWarnings("resource")
            Scanner s;
            s = new Scanner(p.getInputStream()).useDelimiter("\\Z");
            if (s.hasNext()) {
                String message = s.next();
                // System.out.println(message);
                return message;
            }
        } catch (IOException e) {
        }
        return "";
    }

    private static void sendToNxt(String message) {
        try {
            String cmd = pathToNextTool + " /COM=usb /Inbox=" + inbox + " -msg=\"" + message + "\"";
            executeNativeProcess(cmd);
            System.out.println("Command executed: " + cmd);
        } catch (Exception e) {
            System.out.println("Exception:" + e.getMessage());
        }
    }

    /**
     *
     */
    public static void startMasterProgram() {
        try {
            String cmd = pathToNextTool + " /COM=usb -run=Master.rxe";
            Roboralley.executeNativeProcess(cmd);
            System.out.println("Command executed: " + cmd);
        } catch (Exception e) {
            System.out.println("Exception:" + e.getMessage());
        }
    }

    /**
     *
     */
    public static void stopMasterProgram() {
        try {
            String cmd = pathToNextTool + " /COM=usb -stop";
            Roboralley.executeNativeProcess(cmd);
            System.out.println("Command executed: " + cmd);
        } catch (Exception e) {
            System.out.println("Exception:" + e.getMessage());
        }
    }

    private static String getMasterBattery() {
        String cmd = pathToNextTool + " /COM=usb -battery";
        String returnValue = executeNativeProcess(cmd);
        System.out.println("Command executed: " + cmd);

        return returnValue;
    }

    private static String getRunningProgram() {
        String cmd = pathToNextTool + " /COM=usb -runningprogram";
        String returnValue = executeNativeProcess(cmd);
        System.out.println("Command executed: " + cmd);

        return returnValue;
    }

    private static String receiveFromNxt() {
        String cmd = pathToNextTool + " /COM=usb /Empty /Inbox=" + inbox + " -readmsg=0";
        String returnValue = executeNativeProcess(cmd);
        System.out.println("Command executed: " + cmd);

        if (!returnValue.isEmpty()) {
            String chars[] = returnValue.split("\n");
            returnValue = "";
            for (int c = 0; c < chars.length - 1; c++) {
                chars[c] = chars[c].replaceAll("[_[^\\w\\däüöÄÜÖ\\+\\- ]]", "");
                returnValue += Character.toString((char) Integer.parseInt(chars[c]));
            }

            if (!returnValue.isEmpty()) {
                System.out.println("Answer: " + returnValue);
            }
        }
        return returnValue;
    }
    private static final Logger LOG = Logger.getLogger(Roboralley.class.getName());
}
