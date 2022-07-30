package project.demo.model;

import lombok.Data;

import javax.persistence.*;

@Entity
@Data
public class Channel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String title;

    private String whether;

    private String password;

    private String code;

}
