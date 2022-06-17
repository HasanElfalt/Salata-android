package com.ar.salata.viewmodels;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.ar.salata.repositories.GoodsRepository;
import com.ar.salata.repositories.UserRepository;
import com.ar.salata.repositories.model.APIToken;
import com.ar.salata.repositories.model.CategoryList;
import com.ar.salata.repositories.model.ProductList;
import com.ar.salata.repositories.model.StockProductList;

public class GoodsViewModel extends ViewModel {
    private GoodsRepository goodsRepository = new GoodsRepository();

    public MutableLiveData<UserRepository.APIResponse> loadCategories() {
        return goodsRepository.loadCategories();
    }

    public MutableLiveData<UserRepository.APIResponse> loadProducts() {
        return goodsRepository.loadProducts();
    }

    public MutableLiveData<UserRepository.APIResponse> loadProducts(APIToken token, int addressId) {
        return goodsRepository.loadProducts(token, addressId);
    }

    public MutableLiveData<StockProductList> loadProductsWithCategory(APIToken token, int addressId, int catID, int page) {
        return goodsRepository.loadProductsWithCategory(token, addressId, catID, page);
    }

    public MutableLiveData<CategoryList> getCategories() {
        return goodsRepository.getCategories();
    }

    public MutableLiveData<StockProductList> getProducts(int categoryId, int addressId) {
        return goodsRepository.getProducts(categoryId, addressId);
    }

    public MutableLiveData<ProductList> getProducts(int categoryId) {
        return goodsRepository.getProducts(categoryId);
    }
}
