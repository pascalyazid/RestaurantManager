import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.util.Vector;

public class MainWindow extends JFrame {
    private JPanel panel;
    private JPanel nav;
    private JList restaurantList;
    private JScrollPane scrollPane;
    private JButton create;
    private JButton remove;
    private CreateWindow createWindow;
    public Handler handler;
    private Vector<Restaurant> checkEmpty;
    private DefaultListModel model;
    private int createCount;

    /**
     * Constructor for the Main Window
     */
    public MainWindow() {
        super("Restaurants");
        ImageIcon icon = new ImageIcon("icon.jpg");
        this.setIconImage(icon.getImage());
        this.setSize(10, 10);
        init();
        this.pack();
        this.setVisible(true);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    /**
     * Function to initialize the components of the Main Window
     */
    private void init() {
        createCount = 0;
        handler = new Handler();
        panel = new JPanel(new BorderLayout());
        nav = new JPanel(new BorderLayout());

        create = new JButton("Create...");
        nav.add(create, BorderLayout.LINE_END);
        remove = new JButton(("Remove"));
        nav.add(remove, BorderLayout.LINE_START);
        panel.add(nav, BorderLayout.NORTH);

        /**
         * Add the Data from the JSON to the DefaultListModel
         */
        model = new DefaultListModel();
        for (Restaurant restaurant : handler.getRestaurants()) {
            model.addElement(restaurant.getName() + " " + restaurant.getOpens() + " to " +
                    restaurant.getCloses());
        }

        restaurantList = new JList<>(model);

        scrollPane = new JScrollPane();
        scrollPane.setViewportView(restaurantList);
        restaurantList.setLayoutOrientation(JList.VERTICAL);
        /**
         * On hover on the Jlist show a tooltip containing the address of the restaurant
         */
        restaurantList.addMouseMotionListener(new MouseMotionAdapter() {
            @Override
            public void mouseMoved(MouseEvent e) {
                JList l = (JList) e.getSource();
                ListModel m = l.getModel();
                int index = l.locationToIndex(e.getPoint());
                try {
                    l.setToolTipText(handler.getRestaurants().get(index).getAddress());
                } catch (Exception ex) {

                }

            }
        });

        panel.add(scrollPane);

        getContentPane().add(panel);

        create.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    if (createCount == 0) {
                        createWindow = new CreateWindow();
                        createWindow.init(MainWindow.this);
                    }

                } catch (Exception ex) {
                    createWindow = new CreateWindow();
                    createWindow.init(MainWindow.this);
                }

            }
        });
        /**
         * ActionListener for the remove-button, user is asked in a JOptionPane if they really want to remove the entry
         */
        remove.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (!restaurantList.isSelectionEmpty()) {
                    try {
                        Object[] options = {"Yes", "No"};
                        int val = JOptionPane.showConfirmDialog(panel, "Do you really want to delete this entry?", "Delete Entry", JOptionPane.YES_NO_OPTION);
                        if (val == 0) {
                            int index = restaurantList.getSelectedIndex();
                            handler.removeRestaurant(index);
                            model.remove(index);
                            restaurantList.updateUI();
                        }
                    } catch (Exception g) {

                    }
                }


            }
        });
    }

    /**
     * Function for the CreateWindow window so the DefaultListModel is updated on creation of a new entry
     *
     * @param restaurant
     */
    public void updateList(Restaurant restaurant) {
        model.addElement(restaurant.getName() + " " + restaurant.getOpens() + " to " +
                restaurant.getCloses());
        restaurantList.updateUI();
    }

    /**
     * CreateWindow class calls this function so only one create-window can be opened at the same time
     *
     * @param count
     */
    public void setWindowCount(int count) {
        this.createCount = this.createCount + count;

    }

    public static void main(String[] args) {
        MainWindow window = new MainWindow();
    }
}
