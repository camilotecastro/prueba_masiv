package masiv.com.prueba.model;

import java.util.Objects;

public class Floor {

    private int number;

    public Floor(int number) {
        this.number = number;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Floor floor1)) return false;
        return number == floor1.number;
    }

    @Override
    public int hashCode() {
        return Objects.hash(number);
    }

}
