/**
 * Created by cout970 on 5/3/17.
 */
public class Main {

    public static void main(String[] args) {
        String[] newArgs = {
                "-gui",
                "comprador1:cout970.auction.buyer.Buyer;comprador2:cout970.auction.buyer.Buyer;vendedor:cout970.auction.seller.Seller;"
        };
        jade.Boot.main(newArgs);
    }
}
