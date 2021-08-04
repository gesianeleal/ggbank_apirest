package io.gesianeleal.gg.bankgg.dataaccess.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.xml.bind.annotation.XmlRootElement;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "transaction")
@XmlRootElement
public class Transaction implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id    
    @Basic(optional = false)
    @Column(name = "transaction_id", nullable = false)
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int transactionId;
    
    @Basic(optional = false)
    @Column(name = "account_id", nullable = false)
    private int accountId;
    
    @Basic(optional = false)
    @Column(name = "operationtype_id", nullable = false)
    private int operationtypeId;
    
    @Column(precision = 16, scale = 4)
    private BigDecimal amount;

    @Basic(optional = true)
    @Column(name = "event_datetime", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date eventDatetime;

}
