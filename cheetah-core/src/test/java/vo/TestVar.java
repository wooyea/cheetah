package vo;

/**
 * @author Wooyea
 */
public class TestVar {
    public String stringField;
    public int intField;
    public long longField;
    private int privateIntFile;

    public TestVar nexLevel;

    public String getStringField() {
        return stringField;
    }

    public void setStringField(String stringField) {
        this.stringField = stringField;
    }

    public int getIntField() {
        return intField;
    }

    public void setIntField(int intField) {
        this.intField = intField;
    }

    public long getLongField() {
        return longField;
    }

    public void setLongField(long longField) {
        this.longField = longField;
    }

    public int getPrivateIntFile() {
        return privateIntFile;
    }

    public void setPrivateIntFile(int privateIntFile) {
        this.privateIntFile = privateIntFile;
    }

    public TestVar getNexLevel() {
        return nexLevel;
    }

    public void setNexLevel(TestVar nexLevel) {
        this.nexLevel = nexLevel;
    }
}
