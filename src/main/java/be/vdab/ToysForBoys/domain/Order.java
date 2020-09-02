package be.vdab.ToysForBoys.domain;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.time.LocalDate;
import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.Set;

@Entity
@Table(name = "orders")
@NamedEntityGraph(name = "Order.MET_CUSTOMER",
        attributeNodes = @NamedAttributeNode("customer"))
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private LocalDate ordered;
    private LocalDate required;
    private LocalDate shipped;
    private int version;

    @ManyToOne(fetch = FetchType.LAZY,optional = false)
    @JoinColumn(name = "customerId")
    private Customer customer;

    @Enumerated(EnumType.STRING)
    private Status status;

    @ElementCollection
    @CollectionTable(name = "orderdetails",
            joinColumns = @JoinColumn(name = "orderId"))
    private Set<Orderdetail> orderdetailSet;

    protected Order(){}

    public Order(LocalDate ordered,  LocalDate required,
                  LocalDate shipped,  int version,
                 Customer customer, Status status) {
        this.ordered = ordered;
        this.required = required;
        this.shipped = shipped;
        this.version = version;
        this.customer=customer;
        this.status=status;
        this.orderdetailSet = new LinkedHashSet<>();
    }
    public boolean add(Orderdetail orderdetail) {
        if(orderdetail == null){
            throw new NullPointerException();
        }
        return  orderdetailSet.add(orderdetail);
    }
    public LocalDate getOrdered() {
        return ordered;
    }

    public LocalDate getRequired() {
        return required;
    }

    public LocalDate getShipped() {
        return shipped;
    }

    public int getVersion() {
        return version;
    }

    public Customer getCustomer() {
        return customer;
    }

    public int getId() {
        return id;
    }

    public Set<Orderdetail> getOrderdetailSet() {
        return Collections.unmodifiableSet(orderdetailSet);
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public void setShipped(LocalDate shipped) {
        this.shipped = shipped;
    }
    public void updateShipped(LocalDate shippedValue){
        if(shippedValue==null){
            throw new IllegalArgumentException();
        }
        shipped=shippedValue;
    }
    public void updateStatus(Status statusValue){
        if(status==null){
            throw new IllegalArgumentException();
        }
        status=statusValue;
    }
}
