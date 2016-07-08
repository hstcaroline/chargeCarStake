package com.sjtu.ist.charge.Model;

import static javax.persistence.GenerationType.IDENTITY;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 * Order entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "order", catalog = "charge")
public class Order implements java.io.Serializable {

    // Fields

    private Integer id;

    private User user;

    private Stake stake;

    private Timestamp startTime;

    private Timestamp endTime;

    private Integer type;

    // Constructors

    /** default constructor */
    public Order() {
    }

    /** full constructor */
    public Order(User user, Stake stake, Timestamp startTime, Timestamp endTime, Integer type) {
        this.user = user;
        this.stake = stake;
        this.startTime = startTime;
        this.endTime = endTime;
        this.type = type;
    }

    // Property accessors
    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "id", unique = true, nullable = false)
    public Integer getId() {
        return this.id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    public User getUser() {
        return this.user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "stake_id", nullable = false)
    public Stake getStake() {
        return this.stake;
    }

    public void setStake(Stake stake) {
        this.stake = stake;
    }

    @Column(name = "start_time", nullable = false, length = 19)
    public Timestamp getStartTime() {
        return this.startTime;
    }

    public void setStartTime(Timestamp startTime) {
        this.startTime = startTime;
    }

    @Column(name = "end_time", nullable = false, length = 19)
    public Timestamp getEndTime() {
        return this.endTime;
    }

    public void setEndTime(Timestamp endTime) {
        this.endTime = endTime;
    }

    @Column(name = "type", nullable = false)
    public Integer getType() {
        return this.type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

}