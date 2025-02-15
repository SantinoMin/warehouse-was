package warehouseLocation.domain.controller;

import jakarta.validation.Valid;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import warehouseLocation.domain.dto.AreaReqDto;
import warehouseLocation.domain.dto.FloorReqDto;
import warehouseLocation.domain.dto.ProductReqDto;
import warehouseLocation.domain.dto.ProductResDto;
import warehouseLocation.domain.dto.ProductResDto.Message;
import warehouseLocation.domain.dto.ProductResDto.ProductSearch;
import warehouseLocation.domain.dto.RackReqDto;
import warehouseLocation.domain.service.*;

@RestController
//@Slf4j // 응답을 기록하는 데 도움이 되며 주로 디버깅 목적
@RequiredArgsConstructor
@RequestMapping("/manage")
public class ProductController {



    private final ProductService productService;

    //2.1(GET) /manage/product : 상품 검색
    @GetMapping("/product")
    public List<ProductSearch> search(@RequestParam String productName) throws Exception {
        return this.productService.search(productName);
    }

    //  2.2 (GET) /manage/product/{product_id} : 상품 정보
    @GetMapping("/product/{productId}")
    public ProductResDto.ProductInfo productInfo(@PathVariable Long productId) throws Exception {
        return this.productService.productInfo(productId);
    }


    //3.1 (PUT) /manage/product/{productId} : 상품 정보 변경
    @PutMapping("/product/{productId}")
    public ProductResDto.Edit productEdit(@PathVariable Long productId,
                                          @RequestBody ProductReqDto.Edit body) {
        return this.productService.productEdit(productId, body);
    }


    //  //3.1(DELETE) /manage/product/{productId} : 해당 상품 삭제 (완전 삭제 대신, 업데이트로 진행)
    @DeleteMapping("/product/{productId}")
    public ResponseEntity<Message> softDeleteProduct(@PathVariable Long productId) {
        return this.productService.softDeleteProduct(productId);
    }



    //  //3.1 (POST) /manage/product/register : 상품 등록
    // todo !!해당부분 postman 테스트 필요
    // todo !! userId는 로그인에서 가져와야 되는데, 해당 부분 해결 필요->우선 임의로 넣어서 테스트
    @PostMapping("/product/register")
    public ProductResDto.Register ProductRegister(@Valid @RequestBody ProductReqDto body) {


        return this.productService.productRegister(body);
    }

    //  3.1 (GET) /manage/product/categoryList : 카테고리 리스트
    @GetMapping("/product/categoryList")
    public ProductResDto.CategoryList categoryList() {
        return this.productService.categoryList();
    }


    //2.2 (GET) /manage/location/areaList : 구역 리스트
    @GetMapping("/location/areaList")
    public List<ProductResDto.Area> areaList() {
        return this.productService.areaList();
    }

    //2.2 (GET) /manage/location/rackList : 랙 리스트
    @GetMapping("/location/rackList")
    public List<ProductResDto.Rack> rackList() {
        return this.productService.rackList();
    }

    //  //2.2 (GET) /manage/location/floorList : 층 리스트
    // todo !! floorList postman 테스트 필요
//    @GetMapping("/location/floorList")
//    public List<ProductResDto.Floor> floorList() {
//        return this.productService.floorList();
//    }

    //  //4.1 (POST) /manage/location/area : 창고 구역 생성
    // todo !! postman 등록 시, 필드값 안 보임
//    @PostMapping("/location/area")
//    public ResponseEntity<Message> addArea(@Valid @RequestBody AreaReqDto body) {
//        return this.productService.addArea(body);
//    }


  //4.1 (POST) /manage/location/rack : 창고 랙 생성
    // todo !! postman 등록 시, 필드값 안 보임
//  @PostMapping("/location/rack")
//  public ResponseEntity<Message> addRack(@Valid @RequestBody RackReqDto body) {
//    return this.productService.addRack(body);
//  }

//  //4.1 (POST) /manage/location/floor : 창고 층 생성
    // todo !! 동작 시, 에러
//  @PostMapping("/location/floor")
//  public ResponseEntity<Message> addFloor(@RequestBody FloorReqDto body) {
//    return this.productService.addFloor(body);
//  }


  //(DELETE) /manage/location/{areaId} : 창고 구역 삭제 (완전 삭제 대신, 업데이트로 진행)
    // todo !! 정보없는 로케이션의 경우 에러 나옴
//  @DeleteMapping("/location/{areaId}")
//  public ResponseEntity<Message> areaDelete(@PathVariable Long areaId) {
//    return this.productService.areaDelete(areaId);
//  }

//  //(DELETE) /manage/location/{rackId} : 창고 랙 삭제 (완전 삭제 대신, 업데이트로 진행)
// todo !! 정보없는 로케이션의 경우 에러 나옴
//  @DeleteMapping("/location/{rackId}")
//  public ResponseEntity<Message> rackDelete(@PathVariable Long rackId) {
//    return this.productService.rackDelete(rackId);
//  }

  //(DELETE) /manage/location/{floorId} : 창고 층 삭제 (완전 삭제 대신, 업데이트로 진행)
  @DeleteMapping("/location/{floorId}")
  public ResponseEntity<ProductResDto.Message> floorDelete(@PathVariable Long floorId) {
    return this.productService.floorDelete(floorId);
  }

  //(PUT) /manage/product/{productId}/location : 상품의 위치 변경
  @PutMapping("/product/{productId}/location")
  public ResponseEntity<ProductResDto.Message> locationUpdate(@PathVariable Long productId,
      @RequestParam Long rackId, Long areaId, Long floorId) {
    return this.productService.locationUpdate(productId, rackId, areaId, floorId);
  }
};