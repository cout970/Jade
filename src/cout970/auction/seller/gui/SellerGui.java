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
    private JTextField textIncrement;

    public SellerGui(Seller seller) {
        error.setText("");
        nuevaSubastaButton.addActionListener(e -> {
            try {
                String ISBN = textISBN.getText();
                String name = textName.getText();
                if (ISBN == null || ISBN.isEmpty()) {
                    throw new IllegalArgumentException("ISBN invalido");
                }
                if (name == null || name.isEmpty()) {
                    throw new IllegalArgumentException("Titulo invalido");
                }

                float init = Float.parseFloat(textInitialPrice.getText());
                float reserve = Float.parseFloat(textReservation.getText());
                float increment = Float.parseFloat(textIncrement.getText());
                if(init <= 0 || reserve <= 0 || increment <= 0){
                    throw new IllegalArgumentException("Valor invalido");
                }

                Book book = new Book(ISBN, name);
                seller.startAuction(book, init, reserve, increment);
                //goto lista de subastas
                AuctionList list = new AuctionList(seller);
                seller.getGui().setContentPane(list.getRoot());
                list.updateList();
            } catch (Exception e1) {
                error.setText(e1.getMessage());
            }
        });
        listaDeSubastasButton.addActionListener(e -> {
            AuctionList list = new AuctionList(seller);
            seller.getGui().setContentPane(list.getRoot());
            list.updateList();
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
        UIManager.LookAndFeelInfo[] lafi = UIManager.getInstalledLookAndFeels();
        try {
            UIManager.setLookAndFeel(lafi[1].getClassName());
        } catch (ClassNotFoundException | InstantiationException | UnsupportedLookAndFeelException | IllegalAccessException e) {
            e.printStackTrace();
        }

        JFrame frame = new JFrame(seller.getLocalName());
        frame.setContentPane(new SellerGui(seller).root);
        frame.setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
        return frame;
    }

    public JPanel getRoot() {
        return root;
    }
}
