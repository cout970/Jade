package cout970.auction.buyer.gui;

import cout970.auction.buyer.AuctionRef;
import cout970.auction.buyer.Buyer;
import cout970.ontology.Book;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Map;

/**
 * Created by cout970 on 5/15/17.
 */
public class BuyerGui {
    private JButton salirButton;
    private JPanel root;
    private JScrollPane list;
    private JPanel panel;

    public BuyerGui(Buyer buyer) {
        salirButton.addActionListener(e -> {
            buyer.stop();
        });
        buyer.setListener((e) -> update(buyer));
        if (buyer.getGui() != null) {
            update(buyer);
        }
    }

    private void update(Buyer buyer) {
        panel.setLayout(new FlowLayout(FlowLayout.CENTER));
        panel.removeAll();
        for (Map.Entry<Book, AuctionRef> entry : buyer.getAuctions().entrySet()) {

            JButton button = new JButton();
            button.setText(entry.getKey().getTitle());
            button.setToolTipText(entry.getKey().getISBN());

            button.addActionListener(e -> {
                buyer.getGui().setContentPane(new AuctionGui(buyer, entry.getKey()).getRoot());
                buyer.getGui().revalidate();
                buyer.getGui().repaint();
            });
            panel.add(button);
        }
        buyer.getGui().revalidate();
        buyer.getGui().repaint();
    }

    public JPanel getRoot() {
        return root;
    }

    public static JFrame startGui(Buyer buyer) {
        UIManager.LookAndFeelInfo[] lafi = UIManager.getInstalledLookAndFeels();
        try {
            UIManager.setLookAndFeel(lafi[1].getClassName());
        } catch (ClassNotFoundException | InstantiationException | UnsupportedLookAndFeelException | IllegalAccessException e) {
            e.printStackTrace();
        }

        JFrame frame = new JFrame(buyer.getLocalName());
        frame.setContentPane(new BuyerGui(buyer).root);
        frame.setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
        return frame;
    }
}
