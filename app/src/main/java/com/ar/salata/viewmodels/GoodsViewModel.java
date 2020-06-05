package com.ar.salata.viewmodels;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.ar.salata.repositories.GoodsRepository;
import com.ar.salata.repositories.UserRepository;
import com.ar.salata.repositories.model.CategoryList;
import com.ar.salata.repositories.model.ProductList;

public class GoodsViewModel extends ViewModel {
    private GoodsRepository goodsRepository = new GoodsRepository();

    public MutableLiveData<UserRepository.APIResponse> loadCategories() {
        return goodsRepository.loadCategories();
    }

    public MutableLiveData<UserRepository.APIResponse> loadProducts() {
        return goodsRepository.loadProducts();
    }

    public MutableLiveData<CategoryList> getCategories() {
        return goodsRepository.getCategories();
    }

    public MutableLiveData<ProductList> getProducts(int categoryId) {
        return goodsRepository.getProducts(categoryId);
    }
}
