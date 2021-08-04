package io.gesianeleal.gg.bankgg.dataaccess.model;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Entity
@Table(name = "operationtype")
@XmlRootElement
public class OperationType implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Basic(optional = false)
    @Column(name = "operationtype_id", nullable = false)
    private Integer operationtypeId;

    @Basic(optional = false)
    @Column(nullable = false, length = 50)
    private String description;

//    @OneToMany(cascade = CascadeType.ALL, mappedBy = "operationType")
//    private Collection<Transaction> transactionCollection;

//    @XmlTransient
//    public Collection<Transaction> getTransactionCollection() {
//        return transactionCollection;
//    }
//
//    public void setTransactionCollection(Collection<Transaction> transactionCollection) {
//        this.transactionCollection = transactionCollection;
//    }

}
