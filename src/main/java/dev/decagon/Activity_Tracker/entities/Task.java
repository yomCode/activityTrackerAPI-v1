package dev.decagon.Activity_Tracker.entities;


import com.fasterxml.jackson.annotation.JsonIgnore;
import dev.decagon.Activity_Tracker.enums.Status;
import lombok.*;

import javax.persistence.*;
import java.util.Date;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Builder
@Table(name = "tasks")
public class Task {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "title", nullable = false)
    private String title;

    @Column(name = "description", nullable = false)
    private String description;

    @Column(name = "status", nullable = false)
    @Enumerated(EnumType.STRING)
    private Status status;

    @JsonIgnore
    @Column(name = "createdAt", nullable = false)
    private Date createdAt;

    @JsonIgnore
    @Column(name = "updatedAt")
    private Date updatedAt;

    @JsonIgnore
    @Column(name = "completedAt")
    private Date completedAt;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;


    @PrePersist
    public void createdAt(){
        createdAt = new Date();
    }

    @PreUpdate
    public void updatedAt(){
        updatedAt = new Date();
    }

}
