package cout970.auction.seller.gui;

import cout970.auction.seller.Seller;

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

    public SellerGui(Seller seller) {
        nuevaSubastaButton.addActionListener(e -> {

        });
        listaDeSubastasButton.addActionListener(e -> {

        });
        salirButton.addActionListener(e -> {

        });
    }

    public JPanel getRoot() {
        return root;
    }
}
