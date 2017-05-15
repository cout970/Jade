package cout970.auction.seller.gui;

import cout970.auction.seller.Auction;
import cout970.auction.seller.Seller;
import cout970.ontology.Book;

import javax.swing.*;
import java.awt.*;
import java.util.Collections;
import java.util.Map;

/**
 * Created by cout970 on 2017/05/11.
 */
public class AuctionList {
    private JButton backButton;
    private JScrollPane listWrapper;
    private JLabel label;
    private JPanel root;
    private JPanel list;

    private Seller seller;

    public AuctionList(Seller seller) {
        this.seller = seller;
        backButton.addActionListener(e -> {
            seller.getGui().setContentPane(new SellerGui(seller).getRoot());
            seller.getGui().pack();
            seller.getGui().revalidate();
            seller.getGui().repaint();
        });

        for (Auction auction : seller.getAuctions().values()) {
            auction.setListeners(Collections.singletonList((price) -> updateList()));
        }
        updateList();
    }

    private void updateList() {
        list.removeAll();
        list.setLayout(new FlowLayout(FlowLayout.CENTER));
        for (Map.Entry<Book, Auction> entry : seller.getAuctions().entrySet()) {
            JButton button = new JButton();

            String title = "Libro: " + entry.getKey().getTitle();
            title += ", Precio: " + String.format("%.2f", entry.getValue().getCurrentPrize());
            title += ", Compradores: " + entry.getValue().getBuyers().size();
            title += ", Pujas: " + entry.getValue().getInterestedBuyers().size();
            title += ", Precio minimo: " + entry.getValue().getReservationPrize();

            button.setText(title);
            list.add(button);
        }
        seller.getGui().pack();
        seller.getGui().revalidate();
        seller.getGui().repaint();
    }

    public JPanel getRoot() {
        return root;
    }
}
