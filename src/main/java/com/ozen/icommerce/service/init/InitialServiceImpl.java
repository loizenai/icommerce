package com.ozen.icommerce.service.init;

import java.util.List;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Service;

import com.ozen.icommerce.entity.product.BrandEntity;
import com.ozen.icommerce.entity.product.CategoryEntity;
import com.ozen.icommerce.entity.product.ColorEntity;
import com.ozen.icommerce.entity.product.ProductColorsEntity;
import com.ozen.icommerce.entity.product.ProductEntity;
import com.ozen.icommerce.entity.user.RoleEntity;
import com.ozen.icommerce.enums.Color;
import com.ozen.icommerce.enums.RoleName;
import com.ozen.icommerce.es.entity.ColorImage;
import com.ozen.icommerce.es.entity.EsProductEntity;
import com.ozen.icommerce.es.repository.EsProductRepository;
import com.ozen.icommerce.repository.brand.BrandRepository;
import com.ozen.icommerce.repository.category.ProductCategoryRepository;
import com.ozen.icommerce.repository.color.ColorRepository;
import com.ozen.icommerce.repository.product.ProductRepository;
import com.ozen.icommerce.repository.product_colors.ProductColorRepository;
import com.ozen.icommerce.repository.user.RoleRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class InitialServiceImpl implements InitialService, CommandLineRunner {
	private final RoleRepository roleRepository;
	private final ColorRepository colorRepository;
	private final BrandRepository brandRepository;
	private final ProductCategoryRepository productCategoryRepository;
	private final ProductRepository productRepository;
	private final ProductColorRepository productColorRepository;
	
	private final EsProductRepository esProductRepository; 
	
	private boolean isInited = false;
	
	@Override
	public void init() {
		if (!isInited) {
			initProducts();
			isInited = true;
		} 
	}
	
	private void initUsers(){
		final var roles = List.of(
				// User-Role
				RoleEntity.builder().id(Long.valueOf(1))
				.name(RoleName.USER)
				.build(),
				// PM-Role
				RoleEntity.builder().id(Long.valueOf(2))
				.name(RoleName.PM)
				.build(),
				// Admin-Role
				RoleEntity.builder().id(Long.valueOf(3))
				.name(RoleName.ADMIN)
				.build());
		roleRepository.saveAll(roles);	
	}
	
	private void initProducts() {
		// 1. >>> COLORS
		// -> RED
		final var redColor = ColorEntity.builder()
		.color(Color.RED).build();
		final var redColorEntity = colorRepository.save(redColor);
		
		// -> BLUE
		final var blueColor = ColorEntity.builder()
		.color(Color.BLUE).build();
		final var blueColorEntity = colorRepository.save(blueColor);
		
		// -> WHITE
		final var whiteColor = ColorEntity.builder()
		.color(Color.WHITE).build();
		final var whiteColorEntity = colorRepository.save(whiteColor);
		
		// 2. >>> BRANDS
		// -> Ginger & Smart
		final var gingerBrand = BrandEntity.builder()
			.name("Ginger & Smart")
			.description("Ginger & Smart uses sustainable practices not only when designing collections, sourcing fabrics")
		.build();
		
		final var gingerBrandEntity = brandRepository.save(gingerBrand);
		
		// -> FORWARD
		final var forwardBrand = BrandEntity.builder()
			.name("FORWARD")
			.description("Be the first to know about our latest designer arrivals, sales and trends")
		.build();
		
		final var forwardBrandEntity = brandRepository.save(forwardBrand);
		
		// 3. >>> Categories
		final var jeanCat = CategoryEntity.builder()
			.name("JEANS")
			.build();
		final var jeanCatEntity = productCategoryRepository.save(jeanCat);
		
		final var accessoriesCat = CategoryEntity.builder()
			.name("ACCESSORIES")
			.build();
		final var accessoriesCatEntity = productCategoryRepository.save(accessoriesCat);
		
		final var shoesCat = CategoryEntity.builder()
			.name("SHOES")
			.build();
		final var shoesCatEntity = productCategoryRepository.save(shoesCat);
		
		// 4. >>> Products 1 - Ginger & Smart
		final var productBucketBag = ProductEntity.builder()
								.name("APROPOS BUCKET BAG")
								.price(499.0)
								.sku("sku-jhuik")
								.category(accessoriesCatEntity)
								.brand(gingerBrandEntity)
								.build();
		final var productBucketBagEntity = productRepository.save(productBucketBag);
		
		// 4.1 >>> Colors & Images
		final var productBlueColorLink = ProductColorsEntity.builder()
				.color(blueColorEntity)
				.product(productBucketBagEntity)
				.imageUrl("/url-image-blue-color-bucket-bag-001.png")
				.build();
		
		final var productWhiteColorLink = ProductColorsEntity.builder()
				.color(whiteColorEntity)
				.product(productBucketBagEntity)
				.imageUrl("/url-image-white-color-bucket-bag-002.png")
				.build(); 
		
		final var productRedColorLink = ProductColorsEntity.builder()
				.color(redColorEntity)
				.product(productBucketBagEntity)
				.imageUrl("/url-image-red-color-bucket-bag-003.png")
				.build();
		
		productColorRepository.saveAll(List.of(productBlueColorLink, productWhiteColorLink, productRedColorLink));
		
		// >>> To ES - Product 1 
		final var esProductBucketBagColors = List.of(
				ColorImage.builder()
					.color(Color.BLUE.value())
					.imageUrl(productBlueColorLink.getImageUrl())
					.build(),
					
				ColorImage.builder()
					.color(Color.WHITE.value())
					.imageUrl(productWhiteColorLink.getImageUrl())
					.build(),
					
					
				ColorImage.builder()
					.color(Color.RED.value())
					.imageUrl(productRedColorLink.getImageUrl())
					.build()	
			);
		
		final var esProductEntity = EsProductEntity.builder()
										.name(productBucketBag.getName())
										.category(accessoriesCatEntity.getName())
										.brand(gingerBrandEntity.getName())
										.price(productBucketBag.getPrice())
										.colors(esProductBucketBagColors)
										.referId(productBucketBagEntity.getId())
										.build();
		
		final var esProductEntityOpt = esProductRepository.findByReferId(productBucketBagEntity.getId());
		if (esProductEntityOpt == null) {
			esProductRepository.save(esProductEntity);
		}
		
		// >>> Product 2 - Ginger & Smart
		final var productGarconJean = ProductEntity.builder()
				.name("GARCON JEAN")
				.price(329.0)
				.sku("sku-lkjhuik")
				.category(jeanCatEntity)
				.brand(gingerBrandEntity)
				.build();
		final var productGarconJeanEntity = productRepository.save(productGarconJean);
		
		final var productGarconJeanWhiteColorLink = ProductColorsEntity.builder()
				.color(whiteColorEntity)
				.product(productGarconJeanEntity)
				.imageUrl("/url-image-white-color-garcon-jean-001.png")
				.build(); 
		
		final var productGarconJeanRedColorLink = ProductColorsEntity.builder()
				.color(redColorEntity)
				.product(productGarconJeanEntity)
				.imageUrl("/url-image-red-color-garcon-jean-002.png")
				.build();
		
		productColorRepository.saveAll(List.of(productGarconJeanWhiteColorLink, productGarconJeanRedColorLink));
		
		// >>> To ES - Product 2 
		final var esProductGarconJeanColors = List.of(
				ColorImage.builder()
					.color(Color.WHITE.value())
					.imageUrl(productGarconJeanWhiteColorLink.getImageUrl())
					.build(),
					
				ColorImage.builder()
					.color(Color.RED.value())
					.imageUrl(productGarconJeanRedColorLink.getImageUrl())
					.build()
			);
		
		final var esProductGarconJean = EsProductEntity.builder()
										.name(productBucketBag.getName())
										.category(jeanCatEntity.getName())
										.brand(gingerBrandEntity.getName())
										.price(productGarconJeanEntity.getPrice())
										.colors(esProductGarconJeanColors)
										.referId(productGarconJeanEntity.getId())
										.build();
		final var esProductGarconJeanOpt = esProductRepository.findByReferId(productGarconJeanEntity.getId());
		if (esProductGarconJeanOpt == null) {
			esProductRepository.save(esProductGarconJean);
		}
		
		// >>> Product 3 - Ginger & Smart
		final var productAnnieJean = ProductEntity.builder()
				.name("ANNIE JEAN")
				.price(329.0)
				.sku("sku-looojhuik")
				.category(jeanCatEntity)
				.brand(gingerBrandEntity)
				.build();
		final var productAnnieJeanEntity = productRepository.save(productAnnieJean);
		
		final var productAnnieJeanWhiteColorLink = ProductColorsEntity.builder()
				.color(whiteColorEntity)
				.product(productAnnieJeanEntity)
				.imageUrl("/url-image-white-color-annie-jean-001.png")
				.build(); 
		
		final var productAnnieJeanBlueColorLink = ProductColorsEntity.builder()
				.color(blueColorEntity)
				.product(productAnnieJeanEntity)
				.imageUrl("/url-image-blue-color-annie-jean-002.png")
				.build();
		
		productColorRepository.saveAll(List.of(productAnnieJeanWhiteColorLink, productAnnieJeanBlueColorLink));
		
		// >>> To ES - Product 3 
		final var esProductAnnieJeanColors = List.of(
				ColorImage.builder()
					.color(Color.WHITE.value())
					.imageUrl(productAnnieJeanWhiteColorLink.getImageUrl())
					.build(),
					
				ColorImage.builder()
					.color(Color.BLUE.value())
					.imageUrl(productAnnieJeanBlueColorLink.getImageUrl())
					.build()
			);
		
		final var esProductAnnieJean = EsProductEntity.builder()
										.name(productAnnieJean.getName())
										.category(jeanCatEntity.getName())
										.brand(gingerBrandEntity.getName())
										.price(productAnnieJeanEntity.getPrice())
										.colors(esProductAnnieJeanColors)
										.referId(productAnnieJeanEntity.getId())
										.build();
		
		final var esProductAnnieJeanOpt = esProductRepository.findByReferId(productAnnieJeanEntity.getId());
		if (esProductAnnieJeanOpt == null) {
			esProductRepository.save(esProductAnnieJean);
		}
		
		// >>> Brand 2: FORWARD
		// Product 1 - Shoes Balenciaga
		final var productBalenciaga = ProductEntity.builder()
				.name("BALENCIAGA")
				.price(1300.0)
				.sku("sku-1uokik")
				.category(shoesCatEntity)
				.brand(forwardBrandEntity)
				.build();
		final var productBalenciagaEntity = productRepository.save(productBalenciaga);
		
		// 4.1 >>> Colors & Images
		final var productBalenciagaShoesBlueColorLink = ProductColorsEntity.builder()
			.color(blueColorEntity)
			.product(productBalenciagaEntity)
			.imageUrl("/url-image-blue-color-balenciaga-shoes-001.png")
			.build();
		
		final var productBalenciagaShoesWhiteColorLink = ProductColorsEntity.builder()
			.color(whiteColorEntity)
			.product(productBalenciagaEntity)
			.imageUrl("/url-image-white-color-balenciaga-shoes-002.png")
			.build(); 
		
		final var productBalenciagaShoesRedColorLink = ProductColorsEntity.builder()
			.color(redColorEntity)
			.product(productBalenciagaEntity)
			.imageUrl("/url-image-red-color-balenciaga-shoes-bucket-shoes-003.png")
			.build();
		
		productColorRepository.saveAll(List.of(productBalenciagaShoesBlueColorLink
				, productBalenciagaShoesWhiteColorLink, productBalenciagaShoesRedColorLink));
		
		// >>> To ES - Product 1 - Brand 2 
		final var esProductBalenciagaColors = List.of(
				ColorImage.builder()
					.color(Color.WHITE.value())
					.imageUrl(productAnnieJeanWhiteColorLink.getImageUrl())
					.build(),
					
				ColorImage.builder()
					.color(Color.BLUE.value())
					.imageUrl(productAnnieJeanBlueColorLink.getImageUrl())
					.build(),
					
				ColorImage.builder()
					.color(Color.RED.value())
					.imageUrl(productBalenciagaShoesRedColorLink.getImageUrl())
					.build()					
			);
		
		final var esProductBalenciaga = EsProductEntity.builder()
										.name(productBalenciaga.getName())
										.category(shoesCatEntity.getName())
										.brand(forwardBrandEntity.getName())
										.price(productBalenciagaEntity.getPrice())
										.colors(esProductBalenciagaColors)
										.referId(productBalenciagaEntity.getId())
										.build();
		
		final var esProductBalenciagaOpt = esProductRepository.findByReferId(productBalenciagaEntity.getId());
		if (esProductBalenciagaOpt == null) {
			esProductRepository.save(esProductBalenciaga);
		}
		
		// Product 2: BURBERRY - Belt
		final var productBurberryBelt = ProductEntity.builder()
				.name("BURBERRY")
				.price(500.0)
				.sku("sku-20uokik")
				.category(accessoriesCat)
				.brand(forwardBrandEntity)
				.build();
		final var productBurberryBeltEntity = productRepository.save(productBurberryBelt);
		
		// Colors
		final var productBurberryBeltBlueColorLink = ProductColorsEntity.builder()
				.color(blueColorEntity)
				.product(productBurberryBeltEntity)
				.imageUrl("/url-image-blue-color-burberry-belt-001.png")
				.build();
			
		final var productBurberryBeltRedColorLink = ProductColorsEntity.builder()
			.color(redColorEntity)
			.product(productBurberryBeltEntity)
			.imageUrl("/url-image-red-color-burberry-belt-002.png")
			.build();
		
		productColorRepository.saveAll(List.of(productBurberryBeltBlueColorLink
				, productBurberryBeltRedColorLink));
		
		// >>> To ES - Product 2 - Brand 2 
		final var esProductBurberryBeltColors = List.of(
				ColorImage.builder()
					.color(Color.BLUE.value())
					.imageUrl(productBurberryBeltBlueColorLink.getImageUrl())
					.build(),
					
				ColorImage.builder()
					.color(Color.RED.value())
					.imageUrl(productBurberryBeltRedColorLink.getImageUrl())
					.build()					
			);
		
		final var esProductBurberryBelt = EsProductEntity.builder()
										.name(productBurberryBelt.getName())
										.category(accessoriesCat.getName())
										.brand(forwardBrandEntity.getName())
										.price(productBurberryBeltEntity.getPrice())
										.colors(esProductBurberryBeltColors)
										.referId(productBurberryBeltEntity.getId())
										.build();
		
		final var esProductBurberryBeltOpt = esProductRepository.findByReferId(productBurberryBeltEntity.getId());
		if (esProductBurberryBeltOpt == null) {
			esProductRepository.save(esProductBurberryBelt);
		}
	}

	@Override
	public void run(String... args) throws Exception {
		initUsers();
	}
}
