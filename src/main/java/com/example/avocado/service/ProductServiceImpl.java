package com.example.avocado.service;

import com.example.avocado.domain.Product;
import com.example.avocado.domain.ProductImg;
import com.example.avocado.domain.User;
import com.example.avocado.dto.product.*;
import com.example.avocado.repository.ProductImgRepository;
import com.example.avocado.repository.ProductRepository;
import com.example.avocado.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import net.coobird.thumbnailator.Thumbnailator;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.concurrent.atomic.AtomicBoolean;
@Service
@RequiredArgsConstructor
@Log4j2
public class ProductServiceImpl implements ProductService {

  @Value("${file.boardImagePath}")
  private String uploadPath;

  private final UserRepository userRepository;
  private final ProductRepository productRepository;
  private final ProductImgRepository productImgRepository;
  private final ModelMapper modelMapper;

  @Override
  public Long register(ProductDTO productDTO) {
    Product product = modelMapper.map(productDTO, Product.class);

    // Product 엔티티를 저장하고 상품 번호(pno)를 반환
    return productRepository.save(product).getPno();
  }

  @Override
  public void insert(ProductDTO productDTO) {
    User user = userRepository.findByUsername(productDTO.getUsername());
    Product product = modelMapper.map(productDTO, Product.class);
    product.setUser(user);
    product.setWriter(user.getNickname());
    productRepository.save(product);



    AtomicBoolean isFirstFile = new AtomicBoolean(true);

    if (productDTO.getFiles() != null) {
      final List<ProductImgResultDTO> list = new ArrayList<>();

      productDTO.getFiles().forEach(multipartFile -> {


        String originalFileName = multipartFile.getOriginalFilename();
        log.info(originalFileName);
        //uploadPath=uploadPath+"\\"+getFolder();
        String uuid = UUID.randomUUID().toString();
        Path savePath = Paths.get(uploadPath, uuid + "_" + originalFileName);
        String filename = uuid + "_" + originalFileName;
        try {
          multipartFile.transferTo(savePath);
            File thumbFile = new File(uploadPath, "s_" + uuid + "_" + originalFileName);
            Thumbnailator.createThumbnail(savePath.toFile(), thumbFile, 200, 200);

        } catch (IOException e) {
          e.printStackTrace();
        }

        String imgName = "";
        String imgUrl = "";
        imgUrl = "/images/product/" + filename;

        if (isFirstFile.getAndSet(false)) {
          // 첫 번째 파일 여부를 판단하고 false로 변경
          ProductImg productImg = ProductImg.builder()
                  .filename(filename)
                  .uuid(uuid)
                  .imgUrl(imgUrl)
                  .repimgYn("Y")
                  .product(product)
                  .build();
          productImgRepository.save(productImg);

        } else {ProductImg productImg = ProductImg.builder()
                .filename(filename)
                .uuid(uuid)
                .imgUrl(imgUrl)
                .repimgYn("N")
                .product(product)
                .build();
          productImgRepository.save(productImg);
        }
        //Long fno=filModeService.register(fileModel);

        list.add(new ProductImgResultDTO().builder()
            .uuid(uuid)
            .filename(originalFileName)
            .build()
        );
      });

    }

  }


  @Transactional(readOnly = true)
  public Page<MainProductDto> getMainProductPage(ProductSearchDTO productSearchDTO, Pageable pageable){
    return productRepository.getMainProductPage(productSearchDTO, pageable);
  }

  @Override
  public Page<MainProductDto> getSellerProductPage(ProductSearchDTO productSearchDTO, Pageable pageable) {
    return productRepository.getSellerProductPage(productSearchDTO, pageable);
  }

//상품 등록
//  @Override
//  public Long register(ProductDTO productDTO) {
//    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//    String nickname = authentication.getName();
//    Optional<User> seller= userRepository.findByUsername1(getUsername());
//    Product product = modelMapper.map(productDTO, Product.class);
//    product.set(seller);
//    Product savedProduct = productRepository.save(product);
//    return savedProduct.getPno();
//  }


  // 상품 조회
  @Transactional(readOnly = true)
  public ProductDTO getProductDetail(Long pno) {

    // 상품 이미지 엔티티들을 productImgDTO 객체로 변환하여 productImgDTOList 에 담음
    List<ProductImg> productImgList = productImgRepository.findByProductOrderByFnoAsc(pno);
    List<ProductImgDTO> productImgDTOList= new ArrayList<>();

    for (ProductImg productImg : productImgList) {
      ProductImgDTO productImgDTO = ProductImgDTO.of(productImg);
      productImgDTOList.add(productImgDTO);
    }

    // 상품 이미지 엔티티들을 productImgDTO 객체로 변환하여 productImgDTOList 멤버변수를 초기화
    Product product = productRepository.findById(pno).get();
    ProductDTO productDTO = ProductDTO.of(product);
    productDTO.setProductImgDtoList(productImgDTOList);

    return productDTO;

  }
  @Override
  public List<ProductDTO> getList(Pageable pageable) {
    Page<Product> result = productRepository.findAll(pageable);
    List<ProductDTO> productDTOList = result.getContent().stream().map(product ->
            modelMapper.map(product, ProductDTO.class))
        .collect(Collectors.toList());
    return productDTOList;
  }

  @Transactional
  @Override
  public ProductDTO getProduct(Long pno) {
    Optional<Product> result = productRepository.findById(pno);
    Product product = result.get();
    ProductDTO productDTO = modelMapper.map(product, ProductDTO.class);
    return productDTO;
  }

  @Override
  public Long modify(ProductDTO productDTO) {
    Optional<Product> result = productRepository.findById(productDTO.getPno());
    Product product = result.orElseThrow();
    product.change(productDTO.getPname(), productDTO.getContent(), productDTO.getPrice(),
        productDTO.getArea(), productDTO.getHope_location());
    Long pno = productRepository.save(product).getPno();
    return pno;
  }

  @Override
  public void remove(Long pno) {
    productRepository.deleteById(pno);
  }


}

