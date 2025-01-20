package warehouseLocation.models;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
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
@Table(name = "product")
public class ProductEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "product_id", nullable = false, unique = true)
  private Long productId;

//  @Column(name = "product_location_id", nullable = true, unique = true)
//  private Long ProductLocationId;

  @Column(name = "product_name", nullable = false)
  private String productName;

  @Column(name = "category_name", nullable = false)
  private String categoryName;

  @Column(name = "expired_date", nullable = false)
  private LocalDate expiredDate;

  @Column(name = "image_url", nullable = false)
  private String imageUrl;

  @Column(name = "price", nullable = false)
  private String price;

  @Column(name = "created_at", nullable = false, columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
  private LocalDateTime createdAt;

  @Column(name = "updated_at", nullable = false)
  private LocalDateTime updatedAt;

  @Column(name = "deleted_at", nullable = true)
  private LocalDateTime deletedAt;

  // Location 타입으로 변경
  @OneToMany(cascade = CascadeType.ALL)
  @JoinColumn(name = "product_id", referencedColumnName = "product_id")
  private List<Location> location;

  @Column(name = "category_id", nullable = true, unique = true)
  private Long categoryId;

  @Column(name = "user_id", nullable = true, unique = true)
  private Long userId;

  @Column(name = "status", nullable = true)
  private String status;

  @Column(name = "is_valid", nullable = false)
  private boolean isValid;
};



