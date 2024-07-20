package kz.shyngys.notice_board.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Advertisement {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;

    private String description;

    private Long minPrice;

    @Enumerated(EnumType.STRING)
    private AdStatus status;

    @OneToMany
    private List<Image> images;

}
