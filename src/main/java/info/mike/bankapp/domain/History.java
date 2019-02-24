package info.mike.bankapp.domain;

import info.mike.bankapp.web.TransferRequest;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class History {

    private List<Transfer> transfers = new ArrayList<>();

    public History() {
        this.transfers = new ArrayList<>();
    }

    public List<Transfer> getTransfers() {
        return transfers;
    }

    public void addEntryToHistory(TransferRequest transferRequest, String accountNumber){
        this.transfers.add(new Transfer(transferRequest, accountNumber));
    }
}
