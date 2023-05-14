package com.da.nongsan24.controller;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import com.da.nongsan24.commom.CommomDataService;
import com.da.nongsan24.entities.Favorite;
import com.da.nongsan24.entities.Product;
import com.da.nongsan24.entities.User;
import com.da.nongsan24.repository.FavoriteRepository;
import com.da.nongsan24.repository.ProductRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class ShopController extends CommomController {

	@Autowired
	ProductRepository productRepository;
	
	@Autowired
	FavoriteRepository favoriteRepository;
	
	@Autowired
	CommomDataService commomDataService;

	@GetMapping(value = "/products")
	public String shop(Model model, Pageable pageable, @RequestParam("page") Optional<Integer> page,
					   @RequestParam("size") Optional<Integer> size, User user) {

		int currentPage = page.orElse(1);
		int pageSize = size.orElse(6);

		Page<Product> productPage = findPaginated(PageRequest.of(currentPage - 1, pageSize));

		int totalPages = productPage.getTotalPages();
		if (totalPages > 0) {
			List<Integer> pageNumbers = IntStream.rangeClosed(1, totalPages).boxed().collect(Collectors.toList());
			model.addAttribute("pageNumbers", pageNumbers);
		}

		commomDataService.commomData(model, user);
		model.addAttribute("products", productPage);
		List<Product> productList = productRepository.listProductNew20();
		model.addAttribute("productList", productList);

		return "web/shop";
	}

	public Page<Product> findPaginated(Pageable pageable) {

		List<Product> productPage = productRepository.findAll();

		int pageSize = pageable.getPageSize();
		int currentPage = pageable.getPageNumber();
		int startItem = currentPage * pageSize;
		List<Product> list;

		if (productPage.size() < startItem) {
			list = Collections.emptyList();
		} else {
			int toIndex = Math.min(startItem + pageSize, productPage.size());
			list = productPage.subList(startItem, toIndex);
		}

		Page<Product> productPages = new PageImpl<Product>(list, PageRequest.of(currentPage, pageSize),productPage.size());

		return productPages;
	}

	// search product
	@GetMapping(value = "/searchProduct")
	public String showsearch(Model model, Pageable pageable, @RequestParam("keyword") String keyword,
							 @RequestParam("size") Optional<Integer> size, @RequestParam("page") Optional<Integer> page,
							 User user) {

		int currentPage = page.orElse(1);
		int pageSize = size.orElse(6);

		Page<Product> productPage = findPaginatSearch(PageRequest.of(currentPage - 1, pageSize), keyword);

		int totalPages = productPage.getTotalPages();
		if (totalPages > 0) {
			List<Integer> pageNumbers = IntStream.rangeClosed(1, totalPages).boxed().collect(Collectors.toList());
			model.addAttribute("pageNumbers", pageNumbers);
		}
		List<Product> productList = productRepository.listProductNew20();
		model.addAttribute("productList", productList);
		commomDataService.commomData(model, user);
		model.addAttribute("products", productPage);
		return "web/shop";
	}

	// search product
	public Page<Product> findPaginatSearch(Pageable pageable, @RequestParam("keyword") String keyword) {

		List<Product> productPage = productRepository.searchProduct(keyword);

		int pageSize = pageable.getPageSize();
		int currentPage = pageable.getPageNumber();
		int startItem = currentPage * pageSize;
		List<Product> list;

		if (productPage.size() < startItem) {
			list = Collections.emptyList();
		} else {
			int toIndex = Math.min(startItem + pageSize, productPage.size());
			list = productPage.subList(startItem, toIndex);
		}

		Page<Product> productPages = new PageImpl<Product>(list, PageRequest.of(currentPage, pageSize),productPage.size());

		return productPages;
	}

	// list books by category
	@GetMapping(value = "/productByCategory")
	public String listProductbyid(Model model, @RequestParam("id") Long id, User user) {
		List<Product> products = productRepository.listProductByCategory(id);

		List<Product> listProductNew = new ArrayList<>();

		for (Product product : products) {

			Product productEntity = new Product();

			BeanUtils.copyProperties(product, productEntity);

			Favorite save = favoriteRepository.selectSaves(productEntity.getProductId(), user.getUserId());

			if (save != null) {
				productEntity.favorite = true;
			} else {
				productEntity.favorite = false;
			}
			listProductNew.add(productEntity);

		}
		List<Product> productList = productRepository.listProductNew20();
		model.addAttribute("productList", productList);
		model.addAttribute("products", listProductNew);
		commomDataService.commomData(model, user);
		return "web/shop";
	}
}
