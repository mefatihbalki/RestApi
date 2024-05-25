package org.ferhat.restapiproject.api;

import jakarta.validation.Valid;
import org.ferhat.restapiproject.business.abstracts.ICategoryService;
import org.ferhat.restapiproject.business.abstracts.IProductService;
import org.ferhat.restapiproject.business.abstracts.ISupplierService;
import org.ferhat.restapiproject.core.config.modelMapper.IModelMapperService;
import org.ferhat.restapiproject.core.result.ResultData;
import org.ferhat.restapiproject.core.utils.ResultHelper;
import org.ferhat.restapiproject.dto.request.category.CategorySaveRequest;
import org.ferhat.restapiproject.dto.request.category.CategoryUpdateRequest;
import org.ferhat.restapiproject.dto.request.product.ProductSaveRequest;
import org.ferhat.restapiproject.dto.request.product.ProductUpdateRequest;
import org.ferhat.restapiproject.dto.response.CursorResponse;
import org.ferhat.restapiproject.dto.response.category.CategoryResponse;
import org.ferhat.restapiproject.dto.response.product.ProductResponse;
import org.ferhat.restapiproject.entity.Category;
import org.ferhat.restapiproject.entity.Product;
import org.ferhat.restapiproject.entity.Supplier;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v1/products")
public class ProductController {
    private final IProductService productService;
    private final IModelMapperService modelMapperService;
    private final ICategoryService categoryService;
    private final ISupplierService supplierService;

    public ProductController(IProductService productService,
                             IModelMapperService modelMapperService,
                             ICategoryService categoryService,
                             ISupplierService supplierService) {
        this.productService = productService;
        this.modelMapperService = modelMapperService;
        this.categoryService = categoryService;
        this.supplierService = supplierService;
    }

    @PostMapping()
    @ResponseStatus(HttpStatus.CREATED)
    public ResultData<ProductResponse> save(@Valid @RequestBody ProductSaveRequest productSaveRequest) {
        Product saveProduct = this.modelMapperService.forRequest().map(productSaveRequest, Product.class);
        Category category = this.categoryService.get(productSaveRequest.getCategoryId());
        saveProduct.setCategory(category);
        Supplier supplier = this.supplierService.get(productSaveRequest.getSupplierId());
        saveProduct.setSupplier(supplier);
        this.productService.save(saveProduct);
        return ResultHelper.created(this.modelMapperService.forResponse().map(saveProduct, ProductResponse.class));
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ResultData<ProductResponse> get(@PathVariable("id") int id) {
        Product product = this.productService.get(id);
        ProductResponse productResponse = this.modelMapperService.forResponse().map(product, ProductResponse.class);
        return ResultHelper.success(productResponse);
    }

    @GetMapping()
    @ResponseStatus(HttpStatus.OK)
    public ResultData<CursorResponse<ProductResponse>> cursor(
            @RequestParam(name = "page", required = false, defaultValue = "0") int page,
            @RequestParam(name = "pageSize", required = false, defaultValue = "10") int pageSize) {

        Page<Product> productPage = this.productService.cursor(page, pageSize);
        Page<ProductResponse> productResponsePage = productPage
                .map(product -> this.modelMapperService.forResponse().map(product, ProductResponse.class));

        return ResultHelper.cursor(productResponsePage);
    }

    @PutMapping()
    @ResponseStatus(HttpStatus.OK)
    public ResultData<ProductResponse> update(@Valid @RequestBody ProductUpdateRequest productUpdateRequest) {
        Product updateProduct = this.modelMapperService.forRequest().map(productUpdateRequest, Product.class);
        this.productService.update(updateProduct);
        return ResultHelper.success(this.modelMapperService.forResponse().map(updateProduct, ProductResponse.class));
    }

}
