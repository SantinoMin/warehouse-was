package warehouseLocation.models;


import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@Table(name = "floor")
public class FloorEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "floor_id", nullable = false, unique = true)
  private Long floor_id;

  @Column(name = "floor_number", nullable = false)
  private Long floor_number;

  @Column(name = "created_at", nullable = true)
  private LocalDateTime created_at;

  @Column(name = "updated_at", nullable = true)
  private LocalDateTime updated_at;

  @Column(name = "deleted_at", nullable = true)
  private LocalDateTime deleted_at;

  @Column(name = "is_valid", nullable = false)
  private Boolean isValid;
  };
