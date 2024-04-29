package warehouseLocation.domain.controller;

import jakarta.validation.Valid;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import warehouseLocation.domain.dto.ProductReqDto;
import warehouseLocation.domain.dto.ProductResDto;
import warehouseLocation.domain.dto.ProductResDto.Delete;
import warehouseLocation.domain.service.ProductService;

@RestController
//@Validated
@Slf4j // 응답을 기록하는 데 도움이 되며 주로 디버깅 목적
//@ResponseBody // 요거 왜 쓰고있지? // return되는 확인용으로 html이 아닌 문자열을 받기 위해.
@RequestMapping("/product")
public class ProductController {

  private final ProductService productService;

  @Autowired
  ProductController(ProductService productService) {

    this.productService = productService;
  }

  //1.1 (GET) /product/manage/search : 상품 검색

  /**
   * 값을 입력 안하고, 검색을 했을 경우 메시지 띄우려면? 메시지 아예 안 띄워도 되긴하지만,,만약에 띄운다면? 만약에 띄운다면, 1)전체 값들 보여주기 또는 2) 상품명을
   * 입력하세요 메시지 출력
   */
  @GetMapping("/manage/search")
  public ProductResDto.ProductSearch search(
      @Valid @RequestParam("productName") String productName) {
    System.out.println("productName : " + productName);
    return this.productService.search(productName);
  }

  //2.2 (GET) product/manage/search/{productId} 상품 정보
  @GetMapping("/manage/search/{productId}")
  public ProductResDto.ProductInfo productInfo(@PathVariable Long productId) {
    System.out.println("productId : " + productId);
    return this.productService.productInfo(productId);
  }

  //    3.1 (POST) /product/manage/register : 상품 등록
  @PostMapping("/manage/register")
  public ProductResDto.Register ProductRegister(@Valid @RequestBody ProductReqDto body) {

    return this.productService.productRegister(body);
  }

  // 2.2(Put) /product/manage/{productId}/edit
  @PutMapping("/manage/search/{productId}/edit")
  public ProductResDto.Edit productEdit(@PathVariable Long productId,
      @RequestBody ProductReqDto.Edit body) {
    System.out.println("producEdit = " + body);
    return this.productService.productEdit(productId, body);
  }


  //2.2(PUT) /product/manage/search/{productId}/delete : 해당 상품 삭제
  @PutMapping("/manage/search/{productId}/delete")
  public ResponseEntity<ProductResDto.Message> productDelete(@PathVariable Long productId, @RequestBody ProductReqDto body) {
    System.out.println("delete this productId = " + productId);
    return this.productService.productDelete(productId, body);
  }

  //2.2 (GET) /product/locationManagement/locationList : 위치 리스트

  /**
   * 이거 위치 3개 하나씩 나눠서 다시 만들어야 될듯.
   * swagger 수정 필요.
   */
//  @GetMapping("/locationManagement/locationList")
//  public String locationList(@RequestParam List<String> areaList, @RequestParam List<String> rackList, @RequestParam List<String> floorList) {
//    System.out.println("areaList" + areaList);
//    System.out.println("rackList" + rackList);
//    System.out.println("floorList" + floorList);
//    return this.productService.locationList(areaList, rackList,floorList);
//  }

  //2.2 (GET) /locationManagement/area : areaList 위치 리스트
  @GetMapping("/locationManagement/areaList")
  public List<ProductResDto.Area> areaList() {
    return this.productService.areaList();
  }

  //2.2 (GET) /locationManagement/area : areaList 위치 리스트
  @GetMapping("/locationManagement/rack")
  public List<ProductResDto.Rack> rackList() {
    return this.productService.rackList();
  }

  //2.2 (GET) /locationManagement/area : areaList 위치 리스트
  @GetMapping("/locationManagement/floor")
  public List<ProductResDto.Floor> floorList() {
    return this.productService.floorList();
  }




  //4 (POST) /product/location/addArea : 구역 등록
  @PostMapping("/manage/location/addArea")
  public String addArea(@RequestBody ProductReqDto body) {
    return this.productService.addArea(body);
  }

  //4.1 (POST) /product/location/addRack : 랙 추가
  @PostMapping("/manage/location/addRack")
  public String addRack(@RequestBody ProductReqDto body) {
    return this.productService.addRack(body);
  }

  //4.1 (POST) /product/location/addFloor : 층 추가
  @PostMapping("/manage/location/addFloor")
  public String addFloor(@RequestBody ProductReqDto body) {
    return this.productService.addFloor(body);
  }


};