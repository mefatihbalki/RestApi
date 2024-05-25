package org.ferhat.restapiproject.api;

import jakarta.validation.Valid;
import org.ferhat.restapiproject.business.abstracts.ISupplierService;
import org.ferhat.restapiproject.core.config.modelMapper.IModelMapperService;
import org.ferhat.restapiproject.core.result.Result;
import org.ferhat.restapiproject.core.result.ResultData;
import org.ferhat.restapiproject.core.utils.ResultHelper;
import org.ferhat.restapiproject.dto.request.category.CategorySaveRequest;
import org.ferhat.restapiproject.dto.request.supplier.SupplierSaveRequest;
import org.ferhat.restapiproject.dto.request.supplier.SupplierUpdateRequest;
import org.ferhat.restapiproject.dto.response.CursorResponse;
import org.ferhat.restapiproject.dto.response.category.CategoryResponse;
import org.ferhat.restapiproject.dto.response.supplier.SupplierResponse;
import org.ferhat.restapiproject.entity.Category;
import org.ferhat.restapiproject.entity.Supplier;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v1/suppliers")
public class SupplierController {
    private final ISupplierService supplierService;
    private final IModelMapperService modelMapperService;

    public SupplierController(ISupplierService supplierService, IModelMapperService modelMapperService) {
        this.supplierService = supplierService;
        this.modelMapperService = modelMapperService;
    }

    @PostMapping()
    @ResponseStatus(HttpStatus.CREATED)
    public ResultData<SupplierResponse> save(@Valid @RequestBody SupplierSaveRequest supplierSaveRequest) {
        Supplier saveSupplier = this.modelMapperService.forRequest().map(supplierSaveRequest, Supplier.class);
        this.supplierService.save(saveSupplier);
        return ResultHelper.created(this.modelMapperService.forResponse().map(saveSupplier, SupplierResponse.class));
    }

    @PutMapping()
    @ResponseStatus(HttpStatus.OK)
    public ResultData<SupplierResponse> update(@Valid @RequestBody SupplierUpdateRequest supplierUpdateRequest) {
        Supplier updateSupplier = this.modelMapperService.forRequest().map(supplierUpdateRequest, Supplier.class);
        this.supplierService.update(updateSupplier);
        return ResultHelper.created(this.modelMapperService.forResponse().map(updateSupplier, SupplierResponse.class));
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ResultData<SupplierResponse> get(@PathVariable("id") int id) {
        Supplier supplier = this.supplierService.get(id);
        SupplierResponse supplierResponse = this.modelMapperService.forResponse().map(supplier, SupplierResponse.class);
        return ResultHelper.success(supplierResponse);
    }

    @GetMapping()
    @ResponseStatus(HttpStatus.OK)
    public ResultData<CursorResponse<SupplierResponse>> cursor(
            @RequestParam(name = "page", required = false, defaultValue = "0") int page,
            @RequestParam(name = "pageSize", required = false, defaultValue = "10") int pageSize) {

        Page<Supplier> supplierPage = this.supplierService.cursor(page, pageSize);
        Page<SupplierResponse> supplierResponsePage = supplierPage
                .map(supplier -> this.modelMapperService.forResponse().map(supplier, SupplierResponse.class));

        return ResultHelper.cursor(supplierResponsePage);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Result delete(@PathVariable("id") int id) {
        this.supplierService.delete(id);
        return ResultHelper.ok();
    }
}
