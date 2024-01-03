package com.example.avocado.service;

import com.example.avocado.domain.Product;
import com.example.avocado.domain.ProductImg;
import com.example.avocado.domain.User;
import com.example.avocado.dto.product.ProductDTO;
import com.example.avocado.dto.upload.ProductImgResultDTO;
import com.example.avocado.repository.ProductImgRepository;
import com.example.avocado.repository.ProductRepository;
import com.example.avocado.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import net.coobird.thumbnailator.Thumbnailator;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

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
    product.setWriter(user.getUsername());
    productRepository.save(product);


    if (productDTO.getFiles() != null) {
      final List<ProductImgResultDTO> list = new ArrayList<>();
//            for(int i=0; i<upload.getFiles().size();i++){
//
//            }
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

        ProductImg productImg = ProductImg.builder()
            .filename(filename)
            .uuid(uuid)
            .product(product)
            .build();

        productImgRepository.save(productImg);


        //Long fno=filModeService.register(fileModel);

        list.add(new ProductImgResultDTO().builder()
            .uuid(uuid)
            .filename(originalFileName)
            .build()
        );
      });

    }

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

