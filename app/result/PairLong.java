
package result;

public class PairLong {

    public long key;

    public long value;

    public long getKey() {
        return key;
    }

    public void setKey(long key) {
        this.key = key;
    }

    public long getValue() {
        return value;
    }

    public void setValue(long value) {
        this.value = value;
    }

    public PairLong(long key, long value) {
        super();
        this.key = key;
        this.value = value;
    }

}
