package warehouseLocation.domain.service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import warehouseLocation.domain.dto.LocationReqDto;
import warehouseLocation.domain.dto.LocationResDto;
import warehouseLocation.domain.dto.ProductReqDto;
import warehouseLocation.domain.dto.ProductResDto;
import warehouseLocation.domain.dto.ProductResDto.CategoryList;
import warehouseLocation.domain.dto.ProductResDto.Location;
import warehouseLocation.domain.dto.ProductResDto.ProductSearch;
import warehouseLocation.domain.repository.AreaRepository;
import warehouseLocation.domain.repository.CategoryRepository;
import warehouseLocation.domain.repository.FloorRepository;
import warehouseLocation.domain.repository.ProductLocationRepository;
import warehouseLocation.domain.repository.ProductRepository;
import warehouseLocation.domain.repository.RackRepository;
import warehouseLocation.domain.repository.UserRepository;
import warehouseLocation.global.utills.response.error.CustomException;
import warehouseLocation.global.utills.response.error.ErrorMessage;
import warehouseLocation.models.AreaEntity;
import warehouseLocation.models.CategoryEntity;
import warehouseLocation.models.FloorEntity;
import warehouseLocation.models.ProductEntity;
import warehouseLocation.models.ProductLocationEntity;
import warehouseLocation.models.RackEntity;

@Service
public class ProductService {

  private final ProductRepository productRepository;
  private final AreaRepository areaRepository;
  private final RackRepository rackRepository;
  private final FloorRepository floorRepository;
  private final CategoryRepository categoryRepository;
  private final UserRepository userRepository;
  private ProductLocationRepository productLocationRepository;


  @Autowired
  ProductService(ProductRepository productRepository, AreaRepository areaRepository,
      RackRepository rackRepository, FloorRepository floorRepository,
      CategoryRepository categoryRepository, UserRepository userRepository,
      ProductLocationRepository productLocationRepository) {
    this.productRepository = productRepository;
    this.areaRepository = areaRepository;
    this.rackRepository = rackRepository;
    this.floorRepository = floorRepository;
    this.categoryRepository = categoryRepository;
    this.userRepository = userRepository;
    this.productLocationRepository = productLocationRepository;
  }

  /**
   * 상품 검색 - 더 추가 필요: (완료) 1)상품명의 2글자만 일치하여도 db에서 찾아서 보이도록 하기. -> 이 부분은 프론트에서 담당하는건가..? => 쿼리에서 %를
   * 이용해서 상품명 2자리 일치시 전부 보여주도록 하기. 2)(완료) imageUrl이 List형태로 보여지도록 해야됨.
   */
  public List<ProductResDto.ProductSearch> search(String productName) {

    /**
     * 1) 상품명이 일부라도 포함되는 경우, 전부 검색 가능 (검색: 콜라 -> 펩시 콜라, 제로 콜라, 코카 콜라 검색 가능)
     * 2) 이미지는 여러개 등록 가능
     */

    //1-1.productName을 가지고, ProductEntity에서 categoryId 가져오기
    List<ProductEntity> productList = this.productRepository.ByProductName(
        productName);
    System.out.println("productList : " + productList);

    // 만약 product 값이 없을 시, 에러 띄우기.
    if (productList.isEmpty()) {
      throw new CustomException(ErrorMessage.NOT_FOUND_PRODUCTLIST);
    }

    //1-2 CategoryIdList를 productList에서 가져오기
    List<Long> categoryIdList = productList.stream().map(ProductEntity::getCategoryId).toList();

    //카테고리 list에서 id값들이 3,4,5라면
    // 그 중에서 랜덤으로 1개만 가져온다고? 그래 그럼 3번만 가져온다면?

    //1-3 CategoryId를 CategoryIdList에서 가져오기
    //* findAny()도 Optional을 반환함 * // 이건 category가 같다는 가정하에 가능한데, 만약 카테고리가 다르다면? ("주방"으로 검색 -> 주방 세제, 주방 칼 카테고리 다를건데?)

    Long categoryId = categoryIdList.stream().findAny().orElseThrow(() ->
        new CustomException(ErrorMessage.NOT_FOUND_CATEGORY));

    //1-4 categoryId로 categoryName을 가져오기
    Optional<CategoryEntity> optCategoryNameEntity = this.categoryRepository.categoryNameByCategoryId(
        categoryId);
    CategoryEntity categoryNameEntity = optCategoryNameEntity.orElseThrow(() -> new CustomException("no data"));

    String categoryName = categoryNameEntity.getCategoryName();
    System.out.println("categoryName = " + categoryName);

    //2-1 검색한 상품명을 새로운 인스턴스 객체에 저장하고, 타입에 맞게 반환.
    List<ProductResDto.ProductSearch> productDto = new ArrayList<>();
    for (
        ProductEntity OneProduct : productList) {
      ProductResDto.ProductSearch productSearch = new ProductSearch();
      productSearch.setProductName(OneProduct.getProductName());
      //imageUrl을 List로 나타내는 게, db에서 ,콤마로 나누는 게 맞는건가?
      productSearch.setImageUrl(OneProduct.getImageUrl());
      productSearch.setPrice(OneProduct.getPrice());
      productSearch.setCategoryName(categoryName);
      productSearch.setStatus(OneProduct.getStatus());
      productDto.add(productSearch);
    }
    return productDto;
  }

  ;

  /**
   * 상품 정보 !!해결 필요 1)(완료)dto의 Location클래스를 타입으로 가져오는 법? 2)imageUrl이 Postman response에서 리스트 형태로 보여지는
   * 법(배열 형태로) -> 이거 , 콤마로 나누는 거 맞는지?
   */
  public ProductResDto.ProductInfo productInfo(@RequestParam Long productId) {

    Optional<ProductLocationEntity> optionalProductLocation = this.productLocationRepository.productLocation(
        productId);
    ProductLocationEntity productLocation = optionalProductLocation.orElseThrow(
        () -> new CustomException(ErrorMessage.NOT_FOUND_PRODUCT));

    String area = productLocation.getArea();
    String rack = productLocation.getRack();
    String floor = productLocation.getFloor();

    Location location = new Location();
    location.setArea(area + "번 구역");
    location.setRack(rack + "번 랙");
    location.setFloor(floor + "층");

    ProductEntity product = this.productRepository.productInfoByProductId(productId);

    ProductEntity findCategoryId = this.productRepository.categoryIdByProductId(productId);
    Long categoryId = findCategoryId.getCategoryId();
    System.out.println("categoryId = " + categoryId);

    Optional<CategoryEntity> optCategoryNameEntity = this.categoryRepository.categoryNameByCategoryId(
        categoryId);
    CategoryEntity categoryNameEntity = optCategoryNameEntity.orElseThrow( () -> new CustomException("no dataaa"));
    String categoryName = categoryNameEntity.getCategoryName();
    System.out.println("categoryName = " + categoryName);

    ProductResDto.ProductInfo info = new ProductResDto.ProductInfo();
//    info.setProductId(productId);
    info.setProductName(product.getProductName());
    info.setImageUrl(product.getImageUrl());
    info.setPrice(product.getPrice());
//    info.setCategoryId(product.getCategoryId());
    info.setCategoryName(categoryName);
    info.setStatus(product.getStatus());
    info.setLocation(location);

    return info;
  }

  ;


  public ProductResDto.Register productRegister(ProductReqDto body) {

    // 1. 상품 중복 확인
    Optional<ProductEntity> optRegister = this.productRepository.registerByProductName(
        body.getProductName());
    System.out.println("optRegister = " + optRegister );

    optRegister.ifPresent( name -> {
      if(name.getProductName().equals(body.getProductName())){
        throw new CustomException("해당 상품명은 이미 존재합니다.");
      }
    });

    // 2. 상품 DB에 저장
      LocalDateTime createdAt = LocalDateTime.now();
      LocalDateTime updatedAt = LocalDateTime.now();

      ProductEntity product = new ProductEntity();
      product.setProductName(body.getProductName());
      product.setExpiredDate(body.getExpiredDate());
      product.setImageUrl(body.getImageUrl());
      product.setPrice(body.getPrice());
      product.setCreatedAt(createdAt);
      product.setUpdatedAt(updatedAt);
//      product.setValid(body.isValid());
      this.productRepository.save(product);
    System.out.println("product = "+ product);

    // 3. 사용자가 카테고리 리스트에서 카테고리 선택하기 -> 이건 좀 복잡해지네. api를 따로 둬서 카테고리를 선택하는 걸로 가야될듯.

    // 4. 등록 완료 시, 등록 요청 상품 내역 반환.
    ProductResDto.Register toDto = new ProductResDto.Register();
    toDto.setProductName(product.getProductName());
    toDto.setPrice(product.getPrice());
    toDto.setCategoryName(body.getCategoryName());
    toDto.setExpiredDate(product.getExpiredDate());
    toDto.setStatus(body.getStatus());
    toDto.setImageUrl(product.getImageUrl());

    // 보여줄 필요 없는 내용들이지만, 일단 반환되는 거 확인하는 용도로 기입 해 봄
    toDto.setCreatedAt(createdAt);
    toDto.setUpdatedAt(updatedAt);
    System.out.println("toDto = "+ toDto);
    return toDto;
  }

  public ProductResDto.Edit productEdit(@PathVariable Long productId,
      @RequestBody ProductReqDto.Edit body) {
    //1. 일단 db에서 해당 productId에 대한 정보를 가져온 후
    //   productId를 검색해서, 해당 product의 정보들을 가져온 후
    //2. Repository에서 값을 update 하기.
    //3. 업데이트 한 값을 변수에 담아서, 다시 save하기.

    LocalDateTime createdAt = LocalDateTime.now();
    LocalDateTime updatedAt = LocalDateTime.now();
    LocalDate expiredDate = LocalDate.now();
//    List<Image> image = new ArrayList<>();

    //1번
    ProductEntity product = this.productRepository.findById(productId);

    product.setProductName(body.getProductName());
    product.setExpiredDate(expiredDate);
    product.setImageUrl(body.getImageUrl());
    product.setPrice(body.getPrice());
//    product.setLocation(body.getLocation());
//    product.setSort(body.getSort());
    product.setCreatedAt(createdAt);
    product.setCreatedAt(updatedAt);

    productRepository.save(product);

    ProductResDto.Edit updatedProduct = ProductResDto.Edit.builder()
        .productId(productId)
        .productName(product.getProductName())
        .expiredDate(product.getExpiredDate())
        .imageUrl(body.getImageUrl())
        .price(product.getPrice())
        .categoryId(product.getCategoryId())
//        .location(product.getLocation())
//        .createdAt(createdAt)
        .updatedAt(product.getUpdatedAt())
        .build();

    return updatedProduct;
  }

  public ResponseEntity<ProductResDto.Message> productDelete(Long productId, ProductReqDto body) {

    //1. productId로 해당 product 검색
    //2. 해당 productId를 repository에서 update로 해당 정보 비활성화(is_valid=false로 변경하기) 진행
    //3. productId repo에 저장
    //4. ResponseEntity로 ok값 반환하기.

    this.productRepository.deleteProductById(productId);

    ProductResDto.Message success = new ProductResDto.Message();
    success.setMessage("상품명 : " + body.getProductName() + " -> 삭제 완료");

    return ResponseEntity.ok(success);
  }
  public List<ProductResDto.Area> areaList() {

    //area는 구역이 몇개 안돼서, findAll로 해도 되는데,
    // 만약 userId 또는 productId였다면?
    // FindAll로 찾는건 전체 데이터를 가져오는 거라 비효율적일듯

    List<AreaEntity> areaEntities = this.areaRepository.findAll();

    List<ProductResDto.Area> areaList = new ArrayList<>();

    for (AreaEntity areaEntity : areaEntities) {

      ProductResDto.Area areaDto = new ProductResDto.Area();
      areaDto.setAreaId(areaEntity.getAreaId());

      areaList.add(areaDto);
    }

    return areaList;

  }

  public List<ProductResDto.Rack> rackList() {

    List<RackEntity> rackEntities = this.rackRepository.findAll();

    List<ProductResDto.Rack> rackList = new ArrayList<>();

    for (RackEntity rackEntity : rackEntities) {

      ProductResDto.Rack rackDto = new ProductResDto.Rack();
      rackDto.setRackId(rackEntity.getRackId());

      rackList.add(rackDto);
    }

    return rackList;
  }

  public List<ProductResDto.Floor> floorList() {

    List<FloorEntity> floorEntities = this.floorRepository.findAll();

    List<ProductResDto.Floor> rackDto = new ArrayList<>();

    for (FloorEntity floorEntity : floorEntities) {

      ProductResDto.Floor floor = new ProductResDto.Floor();
      floor.setFloorId(floorEntity.getFloor_id());

      rackDto.add(floor);
    }

    return rackDto;
  }

  public CategoryList categoryList() {

    List<CategoryEntity> categoryList = this.categoryRepository.findAll();

    List<String> categoryIdList = categoryList.stream().map(CategoryEntity::getCategoryName)
        .toList();

    ProductResDto.CategoryList categoryListDto = new ProductResDto.CategoryList();
    categoryListDto.setCategoryList(categoryIdList);

    return categoryListDto;
  }

  @Transactional
  public ResponseEntity<LocationResDto.Message> setLocation(LocationReqDto body) {

    //area, rack, floor repo에 user가 입력한 값을 저장하기.
    //중복되는 값이 이미 있는지 확인 필요할듯, 방법 2가지 일 거 같은데.
    //1) DB에 area,rack,floor값들을 미리 다 저장해두고, user는 리스트에서 선택만 해서, 위치 저장하도록.
    //2) 그냥 user가 임의로 값을 입력해서, repository에 저장 하도록.

    //1)번으로 실행하면 -> repo에 있는 각(area,rack,floor) 리스트 정보들을 보여주기만 하면 됨
    //근데 4번에서는 보여주고 선택하고 등록하는 게 아니라, 값 넣고 저장하는 걸로 등록하는 거 끝 // 2번에서 선택하고 저장하는 걸로.

    //2)번으로 실행하면 -> user가 입력한 값 그대로 db에 저장해보기 + 중복값만 없도록.
    // *중복값 찾기 + repo에 저장
    /**
     * Area 중복 확인 후, 중복 아니라면 repo에 저장하기.
     */
    AreaEntity area = new AreaEntity();
    area.setAreaId(body.getAreaId());

    Optional<AreaEntity> duplicatedArea = this.areaRepository.findByAreaId(body.getAreaId());
    duplicatedArea.ifPresent(a -> {
      throw new CustomException(ErrorMessage.DUPLICATE_AREA);
    });
    this.areaRepository.save(area);

    /**
     * Rack 중복 확인 후, 중복 아니라면 repo에 저장하기
     */
    RackEntity rack = new RackEntity();
    rack.setRackId(body.getRackId());

    Optional<RackEntity> duplicatedRack = this.rackRepository.findByRackId(body.getRackId());
    duplicatedRack.ifPresent(r -> {
      throw new CustomException(ErrorMessage.DUPLICATE_RACK);
    });
    this.rackRepository.save(rack);

    /**
     * Floor중복 확인 후, 중복 아니라면 repo에 저장하기
     */
    FloorEntity floor = new FloorEntity();
    floor.setFloor_id(body.getFloorId());
    Optional<FloorEntity> duplicatedFloor = this.floorRepository.findByFloorId(body.getFloorId());
    duplicatedFloor.ifPresent(f -> {
      throw new CustomException(ErrorMessage.DUPLICATE_FLOOR);
    });
    this.floorRepository.save(floor);

    // LocationResDto 객체 생성 및 값 설정
    LocationResDto.Message locationResDto = new LocationResDto.Message();
    locationResDto.setMessage("로케이션 : " + body.getLocationId() + " -> 등록 완료");

    return ResponseEntity.ok(locationResDto);
  }

}
