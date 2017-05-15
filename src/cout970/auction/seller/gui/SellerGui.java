package cout970.auction.seller.gui;

import cout970.auction.seller.Seller;
import cout970.ontology.Book;

import javax.swing.*;

/**
 * Created by cout970 on 2017/05/10.
 */
public class SellerGui {
    private JButton listaDeSubastasButton;
    private JButton nuevaSubastaButton;
    private JTextField textName;
    private JTextField textISBN;
    private JButton salirButton;
    private JLabel error;
    private JPanel root;
    private JButton eventosButton;
    private JTextField textInitialPrice;
    private JTextField textReservation;

    public SellerGui(Seller seller) {
        nuevaSubastaButton.addActionListener(e -> {
            Book book = new Book("12345", "Libro 1");
            seller.startAuction(book, 50.0f, 70.0f);
        });
        listaDeSubastasButton.addActionListener(e -> {
            seller.getGui().setContentPane(new AuctionList(seller).getRoot());
            seller.getGui().revalidate();
            seller.getGui().repaint();
        });
        eventosButton.addActionListener(e -> {
            seller.getGui().setContentPane(new EventGui(seller).getRoot());
            seller.getGui().revalidate();
            seller.getGui().repaint();
        });
        salirButton.addActionListener(e -> {
            seller.stop();
        });
    }

    public static JFrame startGui(Seller seller) {
        JFrame frame = new JFrame("SellerGui");
        frame.setContentPane(new SellerGui(seller).root);
        frame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
        return frame;
    }

    public JPanel getRoot() {
        return root;
    }
}
