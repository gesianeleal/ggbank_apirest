package io.gesianeleal.gg.bankgg.dataaccess.model;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlRootElement;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

@Data
@NoArgsConstructor
@Accessors(chain=true)
@AllArgsConstructor
@Entity
@EqualsAndHashCode
@Table(catalog = "gg", schema = "", name = "account")
@XmlRootElement
public class Account implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Basic(optional = false)
    @Column(name = "account_id", nullable = false)
    private Integer accountId;

    @Basic(optional = false)
    @Column(name = "document_number", nullable = false)
    @NotNull
    private long documentNumber;

//    @OneToMany(cascade = CascadeType.ALL, mappedBy = "account")
//    private Collection<Transaction> transactionCollection;
//
//    @XmlTransient
//    public Collection<Transaction> getTransactionCollection() {
//        return transactionCollection;
//    }
//
//    public void setTransactionCollection(Collection<Transaction> transactionCollection) {
//        this.transactionCollection = transactionCollection;
//    }
}
