package cout970.auction.seller;

/**
 * Created by cout970 on 2017/05/16.
 */
public enum AuctionState {
    WAITING("Esperando"),
    RUNNING("Activa"),
    FINISHED("Finalizada");

    private String translatedName;

    AuctionState(String translatedName) {
        this.translatedName = translatedName;
    }

    public String getTranslatedName() {
        return translatedName;
    }
}
