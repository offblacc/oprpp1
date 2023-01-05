package hr.fer.zemris.java.gui.prim;

import javax.swing.*;
import javax.swing.event.ListDataEvent;
import javax.swing.event.ListDataListener;
import java.util.ArrayList;
import java.util.List;

public class PrimListModel implements ListModel<Integer> {
    private List<Integer> list = new ArrayList<>();
    private List<ListDataListener> listeners = new ArrayList<>();
        public PrimListModel() {
        list.add(1);
    }

    public void next() {
        int next = list.size() == 0 ? 1 : list.get(list.size() - 1) + 1;
        next = nextPrime(next);
        list.add(next);
        ListDataEvent event = new ListDataEvent(this, ListDataEvent.INTERVAL_ADDED, list.size() - 1, list.size() - 1);
        listeners.forEach(listener -> listener.intervalAdded(event));
    }

    public static int nextPrime(int n) {
        if (n <= 1) return 2;
        int prime = n;
        boolean found = false;
        while (!found) {
            prime++;
            found = true;
            for (int i = 2; i <= Math.sqrt(prime); i++) {
                if (prime % i == 0) {
                    found = false;
                    break;
                }
            }
        }
        return prime;
    }

    @Override
    public int getSize() {
        return list.size();
    }

    @Override
    public Integer getElementAt(int index) {
        return list.get(index);
    }

    @Override
    public void addListDataListener(ListDataListener l) {
        listeners.add(l);
    }

    @Override
    public void removeListDataListener(ListDataListener l) {
        listeners.remove(l);
    }
}