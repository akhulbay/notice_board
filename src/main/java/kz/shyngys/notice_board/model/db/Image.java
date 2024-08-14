package kz.shyngys.notice_board.model.db;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.JdbcType;
import org.hibernate.type.descriptor.jdbc.VarbinaryJdbcType;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "t_image")
public class Image {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @Column(name = "order_index")
    private Integer orderIndex;

    private String type;

    @Lob
    @JdbcType(VarbinaryJdbcType.class)
    private byte[] content;

    @ManyToOne
    private Advertisement advertisement;

}
