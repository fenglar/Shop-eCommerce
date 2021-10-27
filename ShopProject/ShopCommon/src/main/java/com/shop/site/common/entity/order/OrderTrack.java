package com.shop.site.common.entity.order;


import com.shop.site.common.entity.IdBasedEntity;

import javax.persistence.*;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

@Entity
@Table(name = "order_track")
public class OrderTrack extends IdBasedEntity {
    @Column(length = 256)
    private String notes;
    private Date updatedTime;

    @Enumerated(EnumType.STRING)
    @Column(length = 45, nullable = false)
    private OrderStatus status;

    @ManyToOne
    @JoinColumn(name = "order_id")
    private Order order;

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public Date getUpdatedTime() {
        return updatedTime;
    }

    public void setUpdatedTime(Date updatedTime) {
        this.updatedTime = updatedTime;
    }

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }

    public OrderStatus getStatus() {
        return status;
    }

    public void setStatus(OrderStatus status) {
        this.status = status;
    }

    @Transient
    public String getUpdatedTimeOnForm(){
        DateFormat dateformatter = new SimpleDateFormat("yyyy-MM-dd'T'hh:mm:ss");
        return dateformatter.format(updatedTime);
    }

    public void setUpdatedTimeOnForm(String dateString){
        DateFormat dateformatter = new SimpleDateFormat("yyyy-MM-dd'T'hh:mm:ss");
        try {
            this.updatedTime = dateformatter.parse(dateString);
        } catch (ParseException e){
            e.printStackTrace();
        }

    }
}
