import java.util.Objects;

/**
 * @className: Card
 * @description: class of card
 * @author: Fangxu Zhou, You Peng, Yan Tong
 **/
public class Card {
    private String symbol;
    private String color;
    private int faceValue;
    private boolean isPublic;

    public Card(String symbol, String color, int faceValue, boolean isPublic) {
        this.symbol = symbol;
        this.color = color;
        this.faceValue = faceValue;
        this.isPublic = isPublic;
    }

    public Card(String symbol, String color, boolean isPublic) {
        this.symbol = symbol;
        this.color = color;
        this.isPublic = isPublic;
    }

    public Card(String symbol, String color) {
        this.symbol = symbol;
        this.color = color;
    }

    public Card(String symbol) {
        this.symbol = symbol;
    }

    public Card() {
    }

    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public int getFaceValue() {
        return faceValue;
    }

    public void setFaceValue(int faceValue) {
        this.faceValue = faceValue;
    }

    public boolean isPublic() {
        return isPublic;
    }

    public void setPublic(boolean aPublic) {
        isPublic = aPublic;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Card)) return false;
        Card card = (Card) o;
        return getSymbol().equals(card.getSymbol()) && Objects.equals(getColor(), card.getColor());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getSymbol(), getColor());
    }
}
