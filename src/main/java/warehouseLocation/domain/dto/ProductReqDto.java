package warehouseLocation.domain.dto;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@ToString
@AllArgsConstructor
public class ProductReqDto {

  @NotBlank(message = "productName is required.")
  @Size(min = 2, max = 10, message = "The productName must be from 2 to 10 characters.")
  private String productName;

  @NotBlank(message = "price is required.")
  private String price;

  @NotBlank(message = "category is required.")
  private String categoryName;

  @NotNull(message = "expiredDate is required.")
  private LocalDate expiredDate;

  @NotBlank(message = "status is required.")
  private String status;

  @NotBlank(message = "productName is required.")
  private String imageUrl;

  private LocalDateTime createdAt = LocalDateTime.now();// 생성자에 추가

  private LocalDateTime updatedAt = LocalDateTime.now(); // 생성자에 추가


  @Data
  @NoArgsConstructor
  public static class Edit {
    @NotBlank(message = "productName is required.")
    @Size(min = 2, max = 10, message = "The productName must be from 2 to 10 characters.")
    private String productName;

    @NotBlank(message = "expiredDate is required.")
    private LocalDate expiredDate;

    @NotBlank(message = "productName is required.")
    private String imageUrl;

    @NotBlank(message = "price is required.")
    private String price;

    @NotBlank(message = "sort is required.")
    private String status;

    @NotBlank(message = "location is required.")
    private String location;

    @NotBlank(message = "createdAt is required.")
    LocalDateTime createdAt;

    @NotBlank(message = "updatedAt is required.")
    LocalDateTime updatedAt;


  }



  /**
   * entity -> dto로 변환하기
   */
//  public static ProductReqDto toUserReqDto(UserEntity userEntity) {
//    ProductReqDto productReqDto = new ProductReqDto();
//    productReqDto.setId(userEntity.getId());
//    productReqDto.setUserName(userEntity.getUsername());
//    productReqDto.setPhone(userEntity.getPhone());
//    return productReqDto;
  };