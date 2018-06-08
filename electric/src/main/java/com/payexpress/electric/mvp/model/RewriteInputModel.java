package com.payexpress.electric.mvp.model;

import com.jess.arms.di.scope.FragmentScope;
import com.jess.arms.integration.IRepositoryManager;
import com.jess.arms.mvp.BaseModel;
import com.payexpress.electric.mvp.contract.RewriteInputContract;

import javax.inject.Inject;

/**
 * Created by fengxiaozheng
 * on 2018/6/6.
 */
@FragmentScope
public class RewriteInputModel extends BaseModel implements RewriteInputContract.Model {

    @Inject
    public RewriteInputModel(IRepositoryManager repositoryManager) {
        super(repositoryManager);
    }
}
