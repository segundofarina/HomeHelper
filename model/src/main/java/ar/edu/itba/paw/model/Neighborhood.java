package ar.edu.itba.paw.model;

import java.util.Objects;

public class Neighborhood {
    private int ngId;

    public Neighborhood(int ngId) {
        this.ngId = ngId;
    }

    public int getNgId() {
        return ngId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Neighborhood that = (Neighborhood) o;
        return ngId == that.ngId;
    }

    @Override
    public int hashCode() {

        return Objects.hash(ngId);
    }
}
