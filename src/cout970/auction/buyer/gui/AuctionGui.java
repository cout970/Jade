package cout970.auction.buyer.gui;

import cout970.auction.buyer.AuctionRef;
import cout970.auction.buyer.Buyer;
import cout970.ontology.Book;

import javax.swing.*;

/**
 * Created by cout970 on 2017/05/11.
 */
public class AuctionGui {
    private JButton backButton;
    private JLabel isbn;
    private JLabel nombre;
    private JButton pujarButton;
    private JLabel precio;
    private JPanel root;
    private JTextField textMaxBid;
    private JLabel maxBid;

    public AuctionGui(Buyer buyer, Book book) {
        backButton.addActionListener(e -> {
            buyer.getGui().setContentPane(new BuyerGui(buyer).getRoot());
            buyer.getGui().revalidate();
            buyer.getGui().repaint();
        });
        pujarButton.addActionListener(e -> {
            try {
                AuctionRef auction = buyer.getAuctions().get(book);
                if (auction == null) return;

                float maxPrice = Float.parseFloat(textMaxBid.getText());
                auction.setMaxPrice(maxPrice);

                update(buyer, book);
            } catch (Exception e1) {
                //ignore
            }
        });
        buyer.setListener((i) -> {
            AuctionRef auction = buyer.getAuctions().get(book);
            if (auction == null) {
                buyer.getGui().setContentPane(new BuyerGui(buyer).getRoot());
                buyer.getGui().revalidate();
                buyer.getGui().repaint();
            }
            update(buyer, book);
        });
        update(buyer, book);
    }

    private void update(Buyer buyer, Book book) {
        AuctionRef auction = buyer.getAuctions().get(book);
        if (auction == null) return;

        isbn.setText(book.getISBN());
        nombre.setText(book.getTitle());
        precio.setText(String.format("%.2f", auction.getLocalCurrentPrice()));
        maxBid.setText(String.format("%.2f", auction.getMaxPrice()));
    }

    public JPanel getRoot() {
        return root;
    }
}
