package info.mike.bankapp.domain;

import java.util.Collections;
import java.util.List;

public class History {

    private List<Transfer> transfers;

    public History() {
        this.transfers = Collections.emptyList();
    }

    public List<Transfer> getTransfers() {
        return transfers;
    }
}
