package icu.cykuta.chest;

public interface Chest {
    void setStatus(ChestStatus value);
    void writeNbtStatus();
    boolean isUsed();
}
