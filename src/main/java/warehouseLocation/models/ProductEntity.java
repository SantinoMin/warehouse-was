package warehouseLocation.models;


import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import java.time.LocalDateTime;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import warehouseLocation.domain.dto.ProductReqDto;
import warehouseLocation.domain.dto.ProductResDto;
import warehouseLocation.domain.dto.ProductResDto.Location;

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

  @Column(name = "category_id", nullable = true, unique = true)
  private Long categoryId;

  @Column(name = "user_id", nullable = true, unique = true)
  private Long userId;

  @Column(name = "product_name", nullable = false)
  private String productName;

  @Column(name = "expired_date", nullable = false)
  private LocalDateTime expiredDate;

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

//  @OneToMany
//  @JoinColumn(name = "product_location_id", nullable = false)

  //nullable false로 해아함 나중에.
  @Setter
  @Column(name = "location", nullable = true)
  private String location;

  @Column(name = "status", nullable = true)
  private String status;
};
//
//@Entity
//@Getter
//@Setter
//public class Location {
//
//  @Id
//  @GeneratedValue(strategy = GenerationType.IDENTITY)
//  private Long id;
//
//  @Column(nullable = false)
//  private String area;
//
//  @Column(nullable = false)
//  private Long rackNumber;
//
//  @Column(nullable = false)
//  private Long floorHeight;
//}

