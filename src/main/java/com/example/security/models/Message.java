package com.example.security.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToOne;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Message {

    @Id
    @GeneratedValue
    private Long id;

    private String text;

    @OneToOne
    private User to;

    public Message(String text, User to) {
        this.text = text;
        this.to = to;
    }
}
