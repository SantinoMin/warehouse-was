package warehouseLocation.models;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.Id;

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
@Table(name = "location")
public class Location {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "location_id", nullable = false, unique = true)
  private Long locationId;

  @ManyToOne
  @JoinColumn(name = "product_id", referencedColumnName = "product_id")
  private ProductEntity product;

  @Column(name = "area")
  private String area;

  @Column(name = "rack_number")
  private Long rackNumber;

  @Column(name = "floor_height")
  private Long floorHeight;
}
