package project.demo.model;

import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
@Data
public class Board {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String title;
    private String content;
    private String writer;

    @CreationTimestamp
    private Timestamp writeTime;

    private int count;
    private String uploadFileName;
    private String storeFileName;
    private int channelId;
}
