import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Vector;

public class MainWindow extends JFrame {
    private JPanel panel;
    private JPanel nav;
    private JList restaurantList;
    private JScrollPane scrollPane;
    private JButton create;
    private JButton remove;
    private CreateWindow createWindow;
    private Handler handler;
    private Vector<Restaurant> checkEmpty;

    public MainWindow() {
        super("Restaurants");
        this.setSize(10, 10);
        init();
        this.pack();
        this.setVisible(true);
    }

    private void init() {
        handler = new Handler();
        checkEmpty = handler.readJSON();
        if(checkEmpty.size() == 0) {
            handler.createExamles();
        }

        panel = new JPanel(new BorderLayout());
        nav = new JPanel(new BorderLayout());

        create = new JButton("Create...");
        nav.add(create, BorderLayout.LINE_END);
        remove = new JButton(("Remove"));
        nav.add(remove, BorderLayout.LINE_START);
        panel.add(nav, BorderLayout.NORTH);


        DefaultListModel model = new DefaultListModel();
        for (Restaurant restaurant : handler.getRestaurants()) {
            model.addElement(restaurant.getName() + " " + restaurant.getOpens() + " to " +
                    restaurant.getCloses());
        }

        restaurantList = new JList<>(model);

        scrollPane = new JScrollPane();
        scrollPane.setViewportView(restaurantList);
        restaurantList.setLayoutOrientation(JList.VERTICAL);

        panel.add(scrollPane);

        getContentPane().add(panel);

        create.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    MainWindow.getWindows()[1].dispose();
                    createWindow = new CreateWindow();
                    createWindow.init();
                } catch (Exception ex) {
                    createWindow = new CreateWindow();
                    createWindow.init();
                }

            }
        });

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


    public static void main(String[] args) {
        MainWindow window = new MainWindow();
    }
}
