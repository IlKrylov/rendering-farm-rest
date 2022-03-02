package com.krylov.renderfarm.entity;

import com.krylov.renderfarm.entity.enums.TaskStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "task_states")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TaskState {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private TaskStatus taskStatus;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "created", nullable = false)
    private Date created;

    @ManyToOne
    @JoinColumn(name = "task_id", nullable = false)
    private Task task;

}
