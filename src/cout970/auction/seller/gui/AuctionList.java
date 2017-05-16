package cout970.auction.seller.gui;

import cout970.auction.seller.Auction;
import cout970.auction.seller.Seller;
import cout970.ontology.Book;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
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
    private JTable table1;

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
        seller.registerListener((e) -> updateList());

        DefaultTableModel model = (DefaultTableModel) table1.getModel();
        model.addColumn("Titulo");
        model.addColumn("Precio");
        model.addColumn("Precio min");
        model.addColumn("Compradores");
        model.addColumn("Pujas");
        model.addColumn("Primero");
        model.addColumn("Estado");
    }

    public void updateList() {
        DefaultTableModel model = (DefaultTableModel) table1.getModel();
        model.getDataVector().clear();

        for (Map.Entry<Book, Auction> entry : seller.getAuctions().entrySet()) {
            String first;

            if (entry.getValue().getInterestedBuyers().isEmpty()) {
                first = entry.getValue().getLastInterestedBuyers().isEmpty() ?
                        "Nadie" :
                        entry.getValue().getLastInterestedBuyers().get(0).getLocalName();
            } else {
                first = entry.getValue().getInterestedBuyers().get(0).getLocalName();
            }

            model.addRow(new Object[]{
                    entry.getKey().getTitle(),
                    entry.getValue().getCurrentPrize(),
                    entry.getValue().getReservationPrize(),
                    entry.getValue().getBuyers().size(),
                    entry.getValue().getInterestedBuyers().size(),
                    first,
                    entry.getValue().getState().getTranslatedName()
            });
        }
        seller.getGui().revalidate();
        seller.getGui().repaint();
    }

    public JPanel getRoot() {
        return root;
    }
}
