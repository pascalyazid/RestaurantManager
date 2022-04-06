
import javax.print.attribute.standard.JobHoldUntil;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.SQLOutput;
import java.util.Vector;

public class CreateWindow extends JFrame {
    private JPanel panel;
    private JPanel submit;
    private JTextField name;
    private JTextField type;
    private JTextField address;
    private JTextField desc;
    private JLabel label;
    private JPanel dates;
    private JTextField time1, time2, time3, time4;
    private JLabel dateTitle;
    private ButtonGroup radio1;
    private ButtonGroup radio2;
    private JRadioButton halfD1;
    private JRadioButton halfD2;
    private JRadioButton halfD3;
    private JRadioButton halfD4;
    private JButton save;
    private JButton abort;
    private JLabel empty;

    /**
     * Constructor for the Create-Window
     */
    public CreateWindow() {
        super("Create");
    }

    /**
     * Function to initialize the components of the Create-Window
     * @param mainWindow
     */
    public void init(MainWindow mainWindow) {
        ImageIcon icon = new ImageIcon("icon.jpg");
        this.setIconImage(icon.getImage());
        mainWindow.setWindowCount(1);
        //Create the Inputs for the Name and Address of the restaurant
        panel = new JPanel(new GridLayout(4, 3));

        label = new JLabel("Name: ");
        panel.add(label);
        name = new JTextField();
        panel.add(name);
        empty = new JLabel("");

        label = new JLabel("Address: ");
        panel.add(label);
        address = new JTextField();
        panel.add(address);

        label = new JLabel("Type: ");
        panel.add(label);
        type = new JTextField();
        panel.add(type);
        empty = new JLabel("");

        label = new JLabel("Desc: ");
        panel.add(label);
        desc = new JTextField();
        panel.add(desc);
        empty = new JLabel("");
        getContentPane().add(panel, BorderLayout.NORTH);


        dates = new JPanel(new GridLayout(2, 7));
        dateTitle = new JLabel("Opens:");
        dates.add(dateTitle);
        time1 = new JTextField();
        dates.add(time1);
        dateTitle = new JLabel(" : ");
        dates.add(dateTitle);
        time2 = new JTextField();
        dates.add(time2);

        halfD1 = new JRadioButton("AM");
        halfD1.setActionCommand("AM");
        halfD2 = new JRadioButton("PM");
        halfD2.setActionCommand("PM");
        radio1 = new ButtonGroup();
        radio1.add(halfD1);
        radio1.add(halfD2);
        halfD1.setActionCommand("AM");
        dates.add(halfD1);
        dates.add(halfD2);


        dateTitle = new JLabel("Closes:");
        dates.add(dateTitle);
        time3 = new JTextField();
        dates.add(time3);
        dateTitle = new JLabel(" : ");
        dates.add(dateTitle);
        time4 = new JTextField();
        dates.add(time4);

        halfD3 = new JRadioButton("AM");
        halfD3.setActionCommand("AM");
        halfD4 = new JRadioButton("PM");
        halfD4.setActionCommand("PM");
        radio2 = new ButtonGroup();
        radio2.add(halfD3);
        radio2.add(halfD4);
        halfD1.setActionCommand("AM");
        dates.add(halfD3);
        dates.add(halfD4);
        getContentPane().add(dates, BorderLayout.CENTER);

        /**
         * When the window is closed, set the Create-Window counter to zero
         */
        this.setSize(300, 350);
        this.pack();
        this.setVisible(true);

        submit = new JPanel(new GridLayout(1, 4));

        save = new JButton("Save");
        save.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String openM1;
                String closeM1;
                try {
                    int openH = Integer.parseInt(time1.getText());
                    int openM = Integer.parseInt(time2.getText());
                    int closeH = Integer.parseInt(time3.getText());
                    int closeM = Integer.parseInt(time4.getText());
                    if (((openH >= 1 && openH <= 12) && (closeH >= 1 && closeH <= 12)) && ((openM >= 0 && openM <= 60) && (closeM >= 0 && closeM <= 60))) {
                        openM1 = Integer.toString(openM);
                        closeM1 = Integer.toString(closeM);
                        if (Integer.bitCount(openM) == 1) {
                            openM1 = "0" + openM;
                        }
                        if (Integer.bitCount(closeM) == 1) {
                            closeM1 = "0" + openM;
                        }

                        String restName = name.getText();
                        String restAddr = address.getText();
                        String restType = type.getText();
                        String restDesc = desc.getText();
                        String opens = openH + ":" + openM1 + radio1.getSelection().getActionCommand();
                        String closes = closeH + ":" + closeM1 + radio2.getSelection().getActionCommand();

                        Vector<Restaurant> restaurants = mainWindow.handler.readJSON();
                        restaurants.add(new Restaurant(restName, restType, restDesc, restAddr, opens, closes));
                        mainWindow.handler.addRestaurant(new Restaurant(restName, restType, restDesc, restAddr, opens, closes));
                        mainWindow.updateList(new Restaurant(restName, restType, restDesc, restAddr, opens, closes));
                        mainWindow.setWindowCount(-1);
                        CreateWindow.this.dispose();
                    }
                    else {
                        JOptionPane.showMessageDialog(getContentPane(), "Invalid time (AM/PM format)");
                    }
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(getContentPane(), "Invalid input");
                }
            }
        });
        submit.add(save);
        empty = new JLabel("");
        submit.add(empty);
        abort = new JButton("Abort");
        empty = new JLabel("");
        submit.add(empty);
        submit.add(abort);
        getContentPane().add(submit, BorderLayout.SOUTH);

        abort.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int a = JOptionPane.showConfirmDialog(getContentPane(), "Do you really want to abort?", "ABORT", JOptionPane.YES_NO_OPTION);
                if (a == 0) {
                    mainWindow.setWindowCount(-1);
                    CreateWindow.this.dispose();
                }
            }
        });
        this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
    }
}
