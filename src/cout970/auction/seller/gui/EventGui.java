package cout970.auction.seller.gui;

import cout970.auction.seller.Seller;
import cout970.auction.util.Event;

import javax.swing.*;
import java.awt.*;

/**
 * Created by cout970 on 2017/05/13.
 */
public class EventGui {
    private JPanel root;
    private JList<String> list1;
    private JButton volverButton;

    public EventGui(Seller seller) {
        volverButton.addActionListener(e -> {
            seller.getGui().setContentPane(new SellerGui(seller).getRoot());
            seller.getGui().pack();
            seller.getGui().revalidate();
            seller.getGui().repaint();
        });
        seller.registerListener((i) -> updateList(seller));
        updateList(seller);
    }

    private void updateList(Seller seller) {
        synchronized (EventGui.class) {
            ((DefaultListModel) list1.getModel()).removeAllElements();
            for (Event event : seller.getEvents()) {
                ((DefaultListModel<String>) list1.getModel()).addElement(event.getTitle() + ": " + event.getDescription());
            }
            seller.getGui().revalidate();
            seller.getGui().repaint();
        }
    }

    public JPanel getRoot() {
        return root;
    }

    private void createUIComponents() {
        list1 = new JList<String>() {
            @Override
            public void paint(Graphics g) {
                synchronized (EventGui.class) {
                    super.paint(g);
                }
            }
        };
        list1.setModel(new DefaultListModel<String>());
    }
}
