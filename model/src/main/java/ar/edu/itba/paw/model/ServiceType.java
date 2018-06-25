package ar.edu.itba.paw.model;


import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "serviceTypes")
public class ServiceType {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "serviceTypes_serviceTypeId_seq")
    @SequenceGenerator(sequenceName = "serviceTypes_serviceTypeId_seq", name = "serviceTypes_serviceTypeId_seq", allocationSize = 1)
    @Column(name = "serviceTypeId")
    private int id;

    @Column(name = "serviceName", length = 256)
    private String name;

    /* package */ ServiceType(){

    }

    public ServiceType(String name) {
        this.name = name;
    }

    public int getServiceTypeId() {
        return id;
    }

    public void setServiceTypeId(int serviceTypeId) {
        this.id = serviceTypeId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ServiceType)) return false;
        ServiceType that = (ServiceType) o;
        return getServiceTypeId() == that.getServiceTypeId() && Objects.equals(getName(), that.getName());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getServiceTypeId(), getName());
    }
}
